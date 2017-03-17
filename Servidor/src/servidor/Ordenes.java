package servidor;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Ordenes {
    MulticastSocket puerto;
    DatagramPacket orden;
    public Ordenes(){
        try {
            puerto=new MulticastSocket();
        } catch (IOException ex) {
            Logger.getLogger(Ordenes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void matar(InetAddress hostName,String pid)
    {
        try {
            byte mensaje[]=("cerrar,"+pid).getBytes();
            orden=new DatagramPacket(mensaje,mensaje.length,hostName, 1001);
            puerto.send(orden);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Ordenes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Ordenes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void pedirProcesos(String hostName)
    {
        try {
            InetAddress direccion=InetAddress.getByName(hostName);
            byte mensaje[]=("procesos,").getBytes();
            orden=new DatagramPacket(mensaje,mensaje.length,direccion, 1001);
            puerto.send(orden);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Ordenes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Ordenes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void enviarArchivo(String dir,String hostname)
    {
        try {
            if(InetAddress.getByName(hostname).isMulticastAddress())
            {
                String miIp=InetAddress.getLocalHost().getHostAddress();
                String aux=miIp.substring(0, miIp.lastIndexOf(".")+1);
                for (int i = 0; i < 254; i++) {
                    new Thread(new Enviar(dir, aux+i)).start();
                }
            }
            else
            {
                new Thread(new Enviar(dir, hostname)).start();
            }
        } catch (UnknownHostException ex) {
            Logger.getLogger(Ordenes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void apagar(String hostName)
    {
        try {
            InetAddress direccion=InetAddress.getByName(hostName);
            byte mensaje[]=("apagar,").getBytes();
            orden=new DatagramPacket(mensaje,mensaje.length,direccion, 1001);
            puerto.send(orden);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Ordenes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Ordenes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void autoApagado(String hostName,String tiempo)
    {
        try {
            InetAddress direccion=InetAddress.getByName(hostName);
            byte mensaje[]=("apagarAuto,"+tiempo).getBytes();
            orden=new DatagramPacket(mensaje,mensaje.length,direccion, 1001);
            puerto.send(orden);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Ordenes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Ordenes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void reiniciar(String hostName)
    {
        try {
            InetAddress direccion=InetAddress.getByName(hostName);
            byte mensaje[]=("reinicar,").getBytes();
            orden=new DatagramPacket(mensaje,mensaje.length,direccion, 1001);
            puerto.send(orden);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Ordenes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Ordenes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void login(String hostName)
    {
        try {
            InetAddress direccion=InetAddress.getByName(hostName);
            byte mensaje[]=("login,").getBytes();
            orden=new DatagramPacket(mensaje,mensaje.length,direccion, 1001);
            puerto.send(orden);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Ordenes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Ordenes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void bloqueo(String hostName,String tiempo)
    {
        try {
            InetAddress direccion=InetAddress.getByName(hostName);
            byte mensaje[]=("bloqueo,"+tiempo).getBytes();
            orden=new DatagramPacket(mensaje,mensaje.length,direccion, 1001);
            puerto.send(orden);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Ordenes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Ordenes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void desbloqueo(String hostName)
    {
        try {
            InetAddress direccion=InetAddress.getByName(hostName);
            byte mensaje[]=("desbloqueo,").getBytes();
            orden=new DatagramPacket(mensaje,mensaje.length,direccion, 1001);
            puerto.send(orden);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Ordenes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Ordenes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void enviarPagina(String hostName,String pagina)
    {
        try {
            InetAddress direccion=InetAddress.getByName(hostName);
            byte mensaje[]=("CPagina,"+pagina).getBytes();
            orden=new DatagramPacket(mensaje,mensaje.length,direccion, 1001);
            puerto.send(orden);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Ordenes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Ordenes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    class Enviar implements Runnable
            {

        private String dir,hostname;
        public Enviar(String dir,String hostname)
        {
            this.dir=dir;
            this.hostname=hostname;
        }
        @Override
        public void run()
        {
            enviarArhivo();
        }
        public void enviarArhivo()
    {
        try
          {
              System.out.println(hostname);
            // Creamos la direccion IP de la maquina que recibira el archivo
            InetAddress ip = InetAddress.getByName(hostname);
         
            // Creamos el Socket con la direccion y elpuerto de comunicacion
            Socket socket = new Socket( ip, 4400 );
         
            // Creamos el archivo que vamos a enviar
            File archivo = new File( dir );
         
            // Obtenemos el tamaño del archivo
            int tamañoArchivo = ( int )archivo.length();
         
            // Creamos el flujo de salida, este tipo de flujo nos permite 
            // hacer la escritura de diferentes tipos de datos tales como
            // Strings, boolean, caracteres y la familia de enteros, etc.
            DataOutputStream dos = new DataOutputStream( socket.getOutputStream() );
         
            System.out.println( "Enviando Archivo: "+archivo.getName() );
         
            // Enviamos el nombre del archivo 
            dos.writeUTF( archivo.getName() );
         
            // Enviamos el tamaño del archivo
            dos.writeInt( tamañoArchivo );
         
            // Creamos flujo de entrada para realizar la lectura del archivo en bytes
            RandomAccessFile salida=new RandomAccessFile(dir,"rw");
         
            // Creamos un array de tipo byte con el tamaño del archivo 
            byte[] buffer = new byte[ tamañoArchivo ];
         
            // Leemos el archivo y lo introducimos en el array de bytes 
            salida.readFully(buffer,0,buffer.length); 
         
            // Realizamos el envio de los bytes que conforman el archivo
            dos.write(buffer,0,buffer.length);
         
            System.out.println( "Archivo Enviado: "+archivo.getName() );
            // Cerramos socket y flujos
            socket.close(); 
          }
          catch( Exception e )
          {
            System.out.println( e.toString() );
          }
    }
                
            }
}
