package servidor;

import cliente.SesionCliente;
import entity.Pc;
import entity.Sesion;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.HibernateException;

public class GuardarSesion implements Runnable {

    private Socket socket;

    public GuardarSesion(Socket s) {
        this.socket = s;
    }

    @Override
    public void run() {
        DataInputStream dis = null;
        byte buffer[];
        try {

            System.out.println("Guardando Sesion");
            dis = new DataInputStream(socket.getInputStream());
            //resivimos el nombre del archivo
            String nombre = dis.readUTF();
            //obtenemos el tamaño del archivo
            long tamaño = dis.readLong();
            //preparamos el array para recivir el objeto
            buffer = new byte[(int)tamaño];
            //leemos el objeto entrante
            BufferedInputStream bis=new BufferedInputStream(socket.getInputStream());
            bis.read(buffer);
            dis.close();
            bis.close();
            socket.close();
            if (!hibernate.HibernateUtil.isConnected()) {
                Archivos.guardarSesion(nombre, buffer);
            } else {
                //preparamos la entrada de datos del array
                ByteArrayInputStream bs = new ByteArrayInputStream(buffer);
                //preparamos la entrada para obtener el objeto "Sesion"
                ObjectInputStream ois = new ObjectInputStream(bs);
                //obtenemos el objeto "Sesion"
                SesionCliente s = (SesionCliente) ois.readObject();
                /*Despúes de obtener el objeto queda obtener la pc usada*/
                BuscarGrupo.listaSesiones.add(s);
                Pc pc = null;
                String nombreEquipo = nombre.substring(0, nombre.indexOf("-"));
                System.out.println(nombreEquipo);
                for (Pc equipo : BuscarGrupo.equipos) {
                    if (equipo.getNombre().equals(nombreEquipo)) {
                        pc = equipo;
                    }
                }
                /*-----------------------------------------------------------*/
                
                if (pc != null) {

                    try {
                        bdUtil dataBase = new bdUtil();
                        Sesion sesion = dataBase.buildSesionObject(s, pc);
                dataBase.saveSesion(sesion);
                    } catch (HibernateException ex) {
                        System.out.println("Error en persistencia " + ex.toString());
                }
                } else {
                    System.err.println("No se pudo guardar sesión, no se encontró equipo");
            }
            }
        }catch (IOException ex) {
            Logger.getLogger(GuardarSesion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GuardarSesion.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                dis.close();
            } catch (IOException ex) {
                Logger.getLogger(GuardarSesion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}

