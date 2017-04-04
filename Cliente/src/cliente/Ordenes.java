package cliente;

import bloqueo.FrameBlocked;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
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

    public void apagar() {
        try {
            Process proceso = Runtime.getRuntime().exec("shutdown /p");
        } catch (IOException e) {
            System.out.println("Excepción: ");
            e.printStackTrace();
        }
    }

    public void apagarAutomatico(int minutos) {
        try {
            Process proceso = Runtime.getRuntime().exec("shutdown -s -t " + minutos);
        } catch (IOException e) {
            System.out.println("Excepción: ");
            e.printStackTrace();
        }
    }

    public void reiniciar() {
        try {
            Process proceso = Runtime.getRuntime().exec("shutdown /r");
        } catch (IOException e) {
            System.out.println("Excepción: ");
            e.printStackTrace();
        }
    }

    public void cerrar(String pid) {
        try {
            Process proceso = Runtime.getRuntime().exec("taskkill /pid " + pid);
        } catch (IOException e) {
            System.out.println("Excepción: ");
            e.printStackTrace();
        }
    }

    public void bloquear(int tiempo) {
        TimerTask t;
        if (!(pantallaInicio.isVisible())) {
            pantallaInicio.bloqueoCompleto();
            this.tiempo = tiempo;
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
            //agregar codigo de Sesion 
        }
    }

    public void login() {
        if (!(pantallaInicio.isVisible())) {
            pantallaInicio.login();
        }
    }
    public Runnable getInfoPc = new Runnable() {

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
            String marca = cs.getManufacturer();
            String modelo = cs.getModel();
            String nSerie = cs.getSerialNumber();
            String hdd = null;
            for (int i = 0; i < disks.length; i++) {
                if (disks[i].getModel().contains("SATA")) {
                    hdd += (i > 0 ? "," : "") + (((disks[i].getSize() / 1024) / 1024) / 1024) + " GB";
                }
            }
            String ram = ((((gm.getTotal() / 1024) / 1024) / 1024) + 1) + " GB";
            String procesador = cp.getName();
            String sistema = os.getFamily() + " " + os.getVersion().getVersion();
            String inf = marca + "|" + modelo + "|" + nSerie + "|" + hdd + "|" + ram + "|" + procesador + "|" + sistema;
        }
    };

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

