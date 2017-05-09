package servidor;

import cliente.Tarea;
import cliente.Uso;
import entity.Sesion;
import entity.UsoPc;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Date;
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
            Servidor.sesion.saveNewTasks(newTasks);

            //Obtiene historial web y las agrega al historial de sesión
            ArrayList<String> webPages = web.getReport();
            Servidor.sesion.addWebHistory(webPages);

        }
    };          //TmerTask del monitoreo de procesos y tráfico web

    public Monitor(String ip, Long t) {

        sendReportTime = t;
        ipTarget = ip;

        web = new MonitorWeb(ip);
        web.initMonitor();
        timer.schedule(task, 0, sendReportTime);
    }
    public static void guardarSesion(Sesion sesion, String nombreCliente) throws IOException {
        try {
            //System.out.println("------>?" + sesion.getWebHistory().size());
            RandomAccessFile raf = new RandomAccessFile(sesion.getAdminidAdmin().getUsrName(), "rw");
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
    
    public void detenerMonitoreoWeb() {
        web.stop();
    }
}
