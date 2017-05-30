package servidor;

import cliente.SesionCliente;
import cliente.Uso;
import entity.Pc;
import entity.Sesion;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.HibernateException;

class GuardarSesion implements Runnable {

    private Socket socket;

    public GuardarSesion(Socket s) {
        this.socket = s;
    }

    @Override
    public void run() {
        DataInputStream dis = null;
        byte buffer[];
        try {

            System.out.println("Guardando Datos");
            dis = new DataInputStream(socket.getInputStream());
            //resivimos el nombre del archivo
            String nombre = dis.readUTF();
            //obtenemos el tamaño del archivo
            long tamaño = dis.readLong();
            //preparamos el array para recivir el objeto
            buffer = new byte[(int) tamaño];
            //leemos el objeto entrante
            dis.read(buffer);

            if (!nombre.contains("uso")) {
                if (!hibernate.HibernateUtil.isConnected()) {               //Si no se detecta conexion con BD
                    Archivos.guardarSesion(nombre, buffer);                     //LA sesión se guarda en un archivo.

                } else {                                                    //Se detecta conexion con BD
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
                            break;
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
            }else{
                    //preparamos la entrada de datos del array
                    ByteArrayInputStream bs = new ByteArrayInputStream(buffer);
                    //preparamos la entrada para obtener el objeto "Sesion"
                    ObjectInputStream ois = new ObjectInputStream(bs);
                    //obtenemos el objeto "Uso"
                    Uso uso = (Uso) ois.readObject();
                    String nombreEquipo = nombre.substring(0, nombre.indexOf("-"));
                    Pc pc= null;
                    for (Pc equipo : BuscarGrupo.equipos) {
                        if (equipo.getNombre().equals(nombreEquipo)) {
                            pc = equipo;
                            break;
                        }
                    }
                    if(pc!= null && hibernate.HibernateUtil.isConnected()){
                            bdUtil dataBase = new bdUtil();
                            dataBase.saveUsoPc(pc, uso.getFin(), uso.getFin());
                    }
                    
            }

            dis.close();
            socket.close();
        } catch (IOException ex) {
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
