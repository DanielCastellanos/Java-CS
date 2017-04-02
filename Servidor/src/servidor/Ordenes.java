package servidor;

import interfaz.Pc_info;
import interfaz.Principal;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.net.UnknownHostException;
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
                for (int i = 0; i < BuscarGrupo.cliente.size(); i++) {
                    new Thread(new Enviar(dir, BuscarGrupo.cliente.get(i).direccion,Principal.paneles.get(i))).start();
                }
            }
            else
            {
                for (int i=0;i<BuscarGrupo.cliente.size();i++) {
                    if(BuscarGrupo.cliente.get(i).direccion.equals(hostname))
                    {
                        new Thread(new Enviar(dir, hostname,Principal.paneles.get(i))).start();
                    }
                }
            }
        } catch (UnknownHostException ex) {
            Logger.getLogger(Ordenes.class.getName()).log(Level.SEVERE, null, ex);
        }catch (IOException ex) {
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
    public void desbloqueoTotal (String hostName)
    {
        try {
            InetAddress direccion=InetAddress.getByName(hostName);
            byte mensaje[]=("desbloqueoTotal,").getBytes();
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
    class Enviar implements Runnable {

        private String dir, hostname;
        private Pc_info panel;

        public Enviar(String dir, String hostname,Pc_info panel) {
            this.dir = dir;
            this.hostname = hostname;
            this.panel=panel;
        }

        @Override
        public void run() {
            enviarArhivo();
        }

        public void enviarArhivo() {
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
                //empezamos el envio de los datos
                panel.barEnvio.setVisible(true);
                panel.barEnvio.setMaximum((int)(archivo.length()/100));
                panel.barEnvio.setMinimum(0);
                panel.barEnvio.setValue(0);
                panel.barEnvio.setStringPainted(true);
                long leido=0;
                long tamañoArch=archivo.length();
                while (leido < tamañoArch) {
                    if ((leido + 4096) < tamañoArch) {
                        bis.read(arreglo);
                        leido += 4096;
                    } else {
                        int resto = (int) (tamañoArch - leido);
                        arreglo = new byte[resto];
                        bis.read(arreglo);
                        leido += resto;
                    }
                    bos.write(arreglo);
                    panel.barEnvio.setValue((int) (leido / 100));
                }
                panel.barEnvio.setVisible(false);
                //cerramos BufferedInputStream
                bis.close();
                //cerramos BufferedOutputStream
                bos.close();
                //cerramos DataOutputStream
                dos.close();
                //cerramos el Socket
                s.close();
            } catch (IOException e) {
                System.err.println(e.getLocalizedMessage());
            }
        }

    }
}
