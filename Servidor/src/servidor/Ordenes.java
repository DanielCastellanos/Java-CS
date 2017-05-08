package servidor;

import interfaz.Pc_info;
import interfaz.Principal;
import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.ComputerSystem;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HWDiskStore;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;

public class Ordenes {
    MulticastSocket puerto;  //declaramos el puerto multicast
    DatagramPacket orden;    //datagraam para enviar los mensajes
    
    //constructor
    public Ordenes(){
        try {
            //inicializamos el constructor
            puerto=new MulticastSocket();
        } catch (IOException ex) {
            Logger.getLogger(Ordenes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //metodo para enviar orden de cierre de una tarea
    public void matar(String hostname,String pid)
    {
            //estructuramos el mensaje con (la orden,id de proceso) y lo convertimos a arreglo de bits 
            byte mensaje[]=("cerrar,"+pid).getBytes();
            enviarOrden(hostname,mensaje);
    }
    
    //metodo para pedir tareas al cliente
    public void pedirProcesos(String hostname)
    {       //preparamos el mensaje
            byte mensaje[]=("procesos,").getBytes();
            enviarOrden(hostname,mensaje);
    }
    //metodo para enviar un apagado remoto 
    public void apagar(String hostname)
    {       //preparamos el mensaje
            byte mensaje[]=("apagar,").getBytes();
            enviarOrden(hostname,mensaje);
            
    }
    
    //metodo para enviar un apagado remoto en determinado tiempo
    public void autoApagado(String hostname,String tiempo)
    {       
            //preparamos el mensaje
            byte mensaje[]=("apagarAuto,"+tiempo).getBytes();
            enviarOrden(hostname,mensaje);
    }
    public void avisoNombre(String direccion,boolean valido)
    {
        try {
            byte mensaje[]=("nombre,"+valido).getBytes();
            Socket socket=new Socket(InetAddress.getByName(direccion), 4401);
            DataOutputStream dos=new DataOutputStream(socket.getOutputStream());
            dos.writeUTF("nombre");
            dos.writeBoolean(valido);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Ordenes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Ordenes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //metodo para enviar un reinicio
    public void reiniciar(String hostname)
    {       //se prepara el mensaje
            byte mensaje[]=("reinicar,").getBytes();
            enviarOrden(hostname,mensaje);
    }
    
    //metodo para enviar un bloqueo con login
    public void login(String hostname)
    {       //preparamos elmensaje
            byte mensaje[]=("login,").getBytes();
            enviarOrden(hostname,mensaje);
    }
    
    //metodo para enviar un bloqueo sin login
    public void bloqueo(String hostname,String tiempo)
    {       //preparamos el mensaje
            byte mensaje[]=("bloqueo,"+tiempo).getBytes();
            enviarOrden(hostname,mensaje);
    }
    public void desbloqueo(String hostname)
    {       //preparamos el mensaje
            byte mensaje[]=("desbloqueo,").getBytes();
            enviarOrden(hostname,mensaje);
    }
    
    //metodo para enviar el desbloqueo total del equipo
    public void desbloqueoTotal (String hostname)
    {
            byte mensaje[]=("desbloqueoTotal,").getBytes();
            enviarOrden(hostname,mensaje);
    }
    public void enviarPagina(String hostname,String pagina)
    {
            byte mensaje[]=("CPagina,"+pagina).getBytes();
            enviarOrden(hostname,mensaje);
    }
    public void WakeOnLAN(String mac,String grupo)
    {
        try {
            //Temporal
            grupo=grupo.substring(0,grupo.lastIndexOf(".")+1);
            grupo= grupo+"255";
            System.out.println(mac);
            ArrayList<byte[]> byteMAC=getMacBytes(mac);
            for (byte[] bs : byteMAC) {
            // El paquete WakeOnLAN se manda por puertos udp 255.255.255.0:40000.
            // El paquete WakeOnLAN contiene un trailer de 6-bytes y 16 veces una secuencia de 6-bytes que contienen la direccion MAC de destino.
            //preparamos el arreglo de bytes que conformara el paquete
            byte[] packet = new byte[17 * 6];
            DatagramPacket data = new DatagramPacket(packet, packet.length,InetAddress.getByName(grupo),40000);
            DatagramSocket client=new DatagramSocket();
            // Trailer de 6 veces 0xFF o FF en Hexadecimal.
            for (int i = 0; i < 6; i++)
            {
                packet[i] =(byte)0xff;
            }
            // Cuerpo del paquete magico que contiene 16 veces la direccion MAC.
            for (int i = 1; i <= 16; i++)
            {
                for (int j = 0; j < 6; j++)
                {
                    packet[i * 6 + j] = bs[j];
                }
            }
            // Enviamos el paquete WOL(WakeOnLAN).
            client.send(data);
            }
            System.out.println("Orden enviada a las direcciones mac: "+mac);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Ordenes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SocketException ex) {
            Logger.getLogger(Ordenes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Ordenes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private ArrayList<byte[]> getMacBytes(String mac)
    {
        ArrayList<byte []> MAC=new ArrayList<>();
        StringTokenizer token=new StringTokenizer(mac,"|");
        String macs[]=mac.split("|");
        while(token.hasMoreTokens()) {
            String mac1=token.nextToken();
            mac1=mac1.substring(1, mac1.length()-1);
            System.out.println(mac1+"<<<<<");
            String aux[]=mac1.split("-");
            if(aux.length == 6)
            {
            byte[] macBytes=new byte[6];
            for (int i=0 ; i < macBytes.length ; i++) {
                macBytes[i]=(byte) Integer.parseInt(aux[i],16);
            }
            MAC.add(macBytes);
            }
        }
        return MAC;
    }
    public Runnable getInfoPc=new Runnable() {
        

        @Override
        public void run() {
            //falta agregar la direccion MAC
            SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        OperatingSystem os = si.getOperatingSystem();
        CentralProcessor cp = hal.getProcessor();
        ComputerSystem cs = hal.getComputerSystem();
        GlobalMemory gm = hal.getMemory();
        HWDiskStore disks[] = hal.getDiskStores();
        String marca=cs.getManufacturer();
        String modelo=cs.getModel();
        String nSerie=cs.getSerialNumber();
        String hdd=null;
        for (int i = 0; i < disks.length; i++) {
            if (disks[i].getModel().contains("SATA")) {
                hdd += (i > 0 ? "," : "") + (((disks[i].getSize()/1024)/1024)/1024)+" GB";
            }
        }
        String ram=((((gm.getTotal()/1024)/1024)/1024)+1)+" GB";
        String procesador=cp.getName();
        String sistema=os.getFamily()+" "+os.getVersion().getVersion();
        String inf=marca+"|"+modelo+"|"+nSerie+"|"+hdd+"|"+ram+"|"+procesador+"|"+sistema;
        }
    };
    public void enviarOrden(String hostname,byte mensaje[])
    {
        try {
            InetAddress direccion=InetAddress.getByName(hostname);
            orden=new DatagramPacket(mensaje,mensaje.length,direccion, 1001);
            puerto.send(orden);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Ordenes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Ordenes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //metodo para envar archivo de manera simultanea o individual
    public void enviarArchivoSimultaneo(String dir,String hostname)
    {
        try {
            //verificamos si la direccion es multicast o es una ip individual
            if(InetAddress.getByName(hostname).isMulticastAddress())
            {
                //ciclo para enviar el archivo a todas las maquinas
                for (int i = 0; i < BuscarGrupo.equipos.size(); i++) {
                    //iniciamos un nuevo hilo que se encarga del envio 
                    new Thread(new EnviarSimultaneo(dir, BuscarGrupo.equipos.get(i).getHostname(),Principal.paneles.get(i))).start();
                }
            }
            else
            {
                //si la direccion no es multicast buscamos la direccion del cliente al que se enviara
                for (int i=0;i<BuscarGrupo.equipos.size();i++) {
                    if(BuscarGrupo.equipos.get(i).getHostname().equals(hostname))
                    {
                        new Thread(new EnviarSimultaneo(dir, hostname,Principal.paneles.get(i))).start();
                    }
                }
            }
        } catch (UnknownHostException ex) {
            Logger.getLogger(Ordenes.class.getName()).log(Level.SEVERE, null, ex);
        }catch (IOException ex) {
            Logger.getLogger(Ordenes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //envio secuencial de archivo a todos los usuarios
    public void envioArchivoSecuencial(String archivo)
    {
        //iniciamos el hilo que se encargara del envio
        new Thread(new EnviarSecuencial(archivo)).start();
    }
    private void enviarArhivo(String dir,String hostname,Pc_info panel) {
            byte arreglo[];
            int in;
            try {
                //creamos la direccion
                InetAddress direccion = InetAddress.getByName(hostname);
                //creamos socket con el que nos conectaremos al destino
                Socket s = new Socket(direccion, 4400);
                //obtenemos el archivo
                File archivo = new File(dir);
                //preparamos la informacion del archivo
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                //preparamos el Stream para leer el archivo
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(archivo));
                //preparamos el stream para enviar el archivo
                BufferedOutputStream bos = new BufferedOutputStream(s.getOutputStream());
                //enviamos el nombre del archivo
                dos.writeUTF(archivo.getName());
                //arreglo de 4 KB para envio de datos
                arreglo = new byte[4096];
                //hacemos la barra de progreso del panel
                panel.barEnvio.setVisible(true);
                //ponemos el valor maximo a la barra
                panel.barEnvio.setMaximum((int)(archivo.length()/100));
                //ponemos su valor minimo
                panel.barEnvio.setMinimum(0);
                //hablitamos el string del porcentaje 
                panel.barEnvio.setStringPainted(true);
                //cambiamos el color de la barra a verde
                panel.barEnvio.setForeground(Color.GREEN);
                //bloqueamos la opcion de enviar archivo al panel 
                panel.bloquearEnviar();
                //preparamos las variables
                long leido=0;
                long tamañoArch=archivo.length();
                //ciclo para la lectura y envio del archivo
                while (leido < tamañoArch) {
                    //verificamos si aun se pueden enviar 4 KB
                    if ((leido + 4096) < tamañoArch) {
                        //si aun se pueden enviar
                        
                        //leemos los 4 KB
                        bis.read(arreglo);
                        //lo agregamos a la variable de progreso
                        leido += 4096;
                        
                    } else {
                        //de lo contrario calculamos el resto del archivo
                        int resto = (int) (tamañoArch - leido);
                        arreglo = new byte[resto];
                        //leemos el resto del archivo
                        bis.read(arreglo);
                        //agregamos el valor a la variable de progreso
                        leido += resto;
                    }
                    //enviamos los bytes leidos
                    bos.write(arreglo);
                    //agregamos el progreso a la barra
                    panel.barEnvio.setValue((int) (leido / 100));
                    //ponemos el string del progreso
                    panel.barEnvio.setString(((int)(panel.barEnvio.getPercentComplete()*100))+"% Enviado");
                }
                //terminado el envio hacemos invisible la barra de progreso
                panel.barEnvio.setVisible(false);
                //y desbloqueamos el envio de archivos
                panel.desBloqEnviar();
                //cerramos BufferedInputStream
                bis.close();
                //cerramos BufferedOutputStream
                bos.close();
                //cerramos DataOutputStream
                dos.close();
                //cerramos el Socket
                s.close();
            } catch (IOException e) {
                //En caso de error ponemos invisible la barra de progreso
                panel.barEnvio.setVisible(false);
                //habilitamos el envio de archivos nuevamente
                panel.desBloqEnviar();
                System.err.println(e.getLocalizedMessage());
            }
        }
    
    private class EnviarSimultaneo implements Runnable {

        private String dir, hostname;
        private Pc_info panel;

        public EnviarSimultaneo(String dir, String hostname,Pc_info panel) {
            this.dir = dir;
            this.hostname = hostname;
            this.panel=panel;
        }

        @Override
        public void run() {
            enviarArhivo(dir,hostname,panel);
        }

        

    }
    private class EnviarSecuencial implements Runnable{
        String archivo;
        public EnviarSecuencial(String archivo)
        {
            this.archivo=archivo;
        }
        @Override
        public void run() {
            
            for (int i = 0; i < BuscarGrupo.equipos.size(); i++) {
                if(Principal.paneles.get(i).conexion())
                {
                enviarArhivo(archivo,BuscarGrupo.equipos.get(i).getHostname(),Principal.paneles.get(i));
                }
            }
        }
        
    }
}
