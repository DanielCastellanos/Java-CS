package cliente;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Monitor {

    Timer timer = new Timer();
    long sendReportTime;
    String ipTarget;                                //Ip de la interfaz a monitorear
    MonitorWeb web;                                 //Clase para monitoreo de tráfico
    ListaTareas taskList = new ListaTareas();                             //Clase para monitoreo de tareas


    TimerTask task = new TimerTask() {
        @Override
        public void run() {

            //Obtiene lista de tareas
            taskList.escribirLista();
            ArrayList<Tarea> newTasks = taskList.getLista();

            /*El método saveNewTasks compara el arreglo de tareas que entra, 
            y agrega estas tareas nuevas al historial de 
            las sesion.
             */
            Cliente.sesion.saveNewTasks(newTasks);

            //Obtiene historial web y las agrega al historial de sesión
            ArrayList<String> webPages = web.getReport();
            Cliente.sesion.addWebHistory(webPages);

        }
    };          //TmerTask del monitoreo de procesos y tráfico web

    public Monitor(String ip, Long t) {

        sendReportTime = t;
        ipTarget = ip;

        web = new MonitorWeb(ip);
        web.initMonitor();
        timer.schedule(task, 0, sendReportTime);
        
    }

    public static void guardarSesion(SesionCliente sesion, String nombreCliente) throws IOException {
        try {
            System.out.println("------>?" + sesion.getWebHistory().size());
            RandomAccessFile raf = new RandomAccessFile(nombreCliente + "-" + sesion.getUsr(), "rw");
            byte buffer[];
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(bs);
            os.writeObject(sesion);
            buffer = bs.toByteArray();
            raf.write(buffer);
            raf.close();
            bs.close();
            os.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Ordenes.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void enviarSesion(File archivo) throws IOException {

        //iniciamos el socket
        Socket socket = new Socket(BuscarServidor.configuracion.getServerHost(), 4600);
        //preparamos el arreglo que almacenara el archivo
        byte buffer[];
        //preparamos el paquete para el envio
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        //enviamos el nombre del archivo
        dos.writeUTF(archivo.getName());
        //enviamos la longitud del archivo
        dos.writeLong(archivo.length());
        //preparamos el archivo para la lectura
        RandomAccessFile raf = new RandomAccessFile(archivo, "r");
        //inicializamos el buffer
        buffer = new byte[(int) archivo.length()];
        //leemos el archivo
        raf.readFully(buffer);
        //enviamos el archivo
        dos.write(buffer);
        //cerramos la salida de datos
        raf.close();
        socket.close();
        dos.close();
        //Borramos el archivo de la sesión enviada
        archivo.delete();

    }
    
    public void detenerMonitoreoWeb() {
        web.stop();
        timer.cancel();
        timer= new Timer();
    }
}
