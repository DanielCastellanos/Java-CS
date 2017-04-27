package cliente;

import bloqueo.FrameBlocked;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;
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

    int contTiempo, tiempo;
    boolean activo=false;
    Timer bloqueo = new Timer();
    FrameBlocked pantallaInicio = new FrameBlocked();

    public Ordenes() {
    }

    public void info() {
        String[] datos = new String[4];

        try {
            datos[0] = System.getProperty("user.name");
            datos[1] = System.getProperty("os.name");
            datos[2] = Inet4Address.getLocalHost().getHostName();
            datos[3] = Inet4Address.getLocalHost().getHostAddress();

            System.out.println("Datos obtenidos");

            //Falta envío de información
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void nuevoArchivo(String mensaje, byte info[]) {
        try {
            RandomAccessFile zip = new RandomAccessFile("MyZip.zip", "rw");
            String t = mensaje.substring(0, mensaje.indexOf(","));
            int tamaño = Integer.parseInt(t);
            String aux = "archivo," + t + ",";
            System.out.println(tamaño);
            String dat = mensaje.substring(mensaje.indexOf(",") + 1);
            System.out.println("*_*_*_*_*_" + dat);
            zip.write(info, aux.length(), tamaño);
            zip.close();
        } catch (IOException ex) {
            Logger.getLogger(Ordenes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*Apagar equipo inmediatamente*/
    public void apagar() {
        try {
            Cliente.sesion.cerrarSesion();                              //Cerrar sesión
            Process proceso = Runtime.getRuntime().exec("shutdown /p"); //Enviar comando de apagado
            System.exit(0);
        } catch (IOException e) {
            System.out.println("Excepción: ");
            e.printStackTrace();
        }
    }

    /*Para apagar equipo dentro de un tiempo determinado*/
    public void apagarAutomatico(int minutos) {
        
        Timer apagado= new Timer();                 //Se declara un timer;
        
        TimerTask shutdownTask= new TimerTask() {   //Tarea de apagado
            @Override
            public void run() {
                apagar();                           //Método para apagar el equipo
            }
        };
        apagado.schedule(shutdownTask, minutos);     //Se programa la tarea
    }

    /*Método para mandar la orden de reiniciar el equipo*/
    public void reiniciar() {
        try {
            Cliente.sesion.cerrarSesion();          //Cierra sesión de uso
            Process proceso = Runtime.getRuntime().exec("shutdown /r");     //Envía orden de reinicio a consola
        } catch (IOException e) {
            System.out.println("Excepción: ");
            e.printStackTrace();
        }
    }

//    Método para cerrar una tarea
    public void cerrar(String pid) {
        try {
            Process proceso = Runtime.getRuntime().exec("taskkill /pid " + pid);
        } catch (IOException e) {
            System.out.println("Excepción: ");
            e.printStackTrace();
        }
    }

    public void bloquear(int tiempo) {
        
        if (!(pantallaInicio.isVisible())) {
            pantallaInicio.bloqueoCompleto();
            this.tiempo = tiempo;
            this.bloqueo=new Timer();
            this.bloqueo.schedule(new Task(), 1000, 1000);//poner 60000 para medir en minutos
        } else if (pantallaInicio.isVisible() && pantallaInicio.estaBloqueado()) {
            pantallaInicio.bloqueoCompleto();
            this.tiempo = tiempo;
            this.bloqueo=new Timer();
            this.bloqueo.schedule(new Task(), 1000, 1000);//poner 60000 para medir en minutos   
        }
    }

    public void desbloquear() {
        if ((pantallaInicio.isVisible()) && !(pantallaInicio.estaBloqueado())) {
            pantallaInicio.login();
        }
    }

    public void desbloquearCompleto() {
        if (pantallaInicio.isVisible()) {
            pantallaInicio.dispose();
            Listeners.secs=0;
            //Sesion de invitado
            Cliente.sesion= new SesionCliente(
                    BuscarServidor.configuracion.getGrupo()+
                    "Invitado"+
                    BuscarServidor.configuracion.getNombre());
            ////////////////
        }
    }

    public void login() {
        if (!(pantallaInicio.isVisible())) {
            pantallaInicio.login();
        }
    }
    
    public String getInfoPc(){
        String info=null;
            //inicializamos la variable de informacion del sistema
            SystemInfo si = new SystemInfo();
            //inicializamos la variable para la informacion del Hardware
            HardwareAbstractionLayer hal = si.getHardware();
            //iniciamos la variable para la informacion del Sistema Operativo
            OperatingSystem os = si.getOperatingSystem();
            //obtenemos la informacion del Procesador
            CentralProcessor cp = hal.getProcessor();
            //Obtenemos la informacion general del PC
            ComputerSystem cs = hal.getComputerSystem();
            //Obtenemos la memoria RAM total 
            GlobalMemory gm = hal.getMemory();
            //Obtenemos los discos
            HWDiskStore disks[] = hal.getDiskStores();
            //Obtenemos la marca de la pc
            String marca = cs.getManufacturer();
            //Obtenemos el modelo de la PC
            String modelo = cs.getModel();
            //Obtenemos el Numero de serie
            String nSerie = cs.getSerialNumber();
            //Obtenemos los discos duros del sistema
            String hdd ="";
            File[] discos=File.listRoots();
        for (File disco : discos) {
            if(disco.getPath().contains("C:\\"))
            {
                hdd=(((disco.getTotalSpace()/ 1024) / 1024) / 1024) + " GB";
            }
        }
            //Calculamos la memoria RAM y la comvertimos en GB
            String ram = ((((gm.getTotal() / 1024) / 1024) / 1024) + 1) + " GB";
            //Obtenemos el nombre del procesador
            String procesador = cp.getName();
            //Obtenemos el nombre del S.O
            String sistema = os.getFamily() + " " + os.getVersion().getVersion();
            //Obtenemos las direcciones mac de las Tarjetas de red
            String MAC=new Interfaces().getMAC();
            //preparamos la informacion para su envio
            info = marca + "," + modelo + "," + nSerie + ","+ MAC +","+ procesador +","+hdd+ "," + ram + "," + sistema;
            
            System.out.println("Propiedades Enviadas");
        return info;
    }

    public void archivo() {
        try {
            ServerSocket ss = new ServerSocket(4400);
            Socket socket;
            socket = ss.accept();
            DataInputStream archivo = new DataInputStream(socket.getInputStream());
            String nombre = archivo.readUTF();
            int tamaño = archivo.readInt();
            byte buffer[] = new byte[tamaño];
            archivo.read(buffer, 0, buffer.length);
            RandomAccessFile salida = new RandomAccessFile(nombre, "rw");
            salida.write(buffer, 0, buffer.length);
            salida.close();
            socket.close();
            ss.close();
        } catch (Exception e) {
        }
    }
    
    private class Task extends TimerTask
{

        @Override
        public void run() {
            contTiempo++;
            if (tiempo == contTiempo) {
                contTiempo = 0;
                pantallaInicio.login();
               bloqueo.cancel();
            }
        }
    
}
}

