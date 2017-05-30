
package cliente;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class HiloTCP implements Runnable{
    private Socket socket;
    public HiloTCP(Socket socket)
    {
        this.socket=socket;
    }
    private void cambioNombre()
    {
        try {
            String nuevo="";
            String nombre=BuscarServidor.configuracion.getNombre();
            do
            {
                nuevo=JOptionPane.showInputDialog(null,"Nombre \""+nombre+"\" duplicado en el Admin\n"
                                                     + "Ingresa un nuevo nombre","Nombre de Usuario repetido",JOptionPane.WARNING_MESSAGE);
            }while(nuevo.equals(nombre) || nuevo.contains(",") || nuevo.isEmpty());
            BuscarServidor.configuracion.setNombre(nuevo);
            byte mensaje[]=("nuevoNombre,"+nuevo).getBytes();
            DatagramPacket dp=new DatagramPacket(mensaje, mensaje.length,InetAddress.getByName(BuscarServidor.configuracion.getGrupo()),1000);
            BuscarServidor.puerto.send(dp);
        } catch (UnknownHostException ex) {
            Logger.getLogger(BuscarServidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HiloTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        try {
            DataInputStream dis=new DataInputStream(socket.getInputStream());
            String tipo=dis.readUTF();
            switch(tipo)
            {
                case "nombre":
                    boolean valido=dis.readBoolean();
                    if(valido)
                    {
                        BuscarServidor.orden.login();
                    }
                    else
                    {
                        cambioNombre();
                    }
                    break;
                default :
                    dis.close();
                    socket.close();
                    break;
            }
            dis.close();
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(HiloTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

