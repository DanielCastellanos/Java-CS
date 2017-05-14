package servidor;

import cliente.Tarea;
import entity.Sesion;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Monitor {

    Timer timer = new Timer();
    long sendReportTime;
    String ipTarget;                                //Ip de la interfaz a monitorear
    MonitorWeb web;                                 //Clase para monitoreo de tráfico
    ListaTareas taskList = new ListaTareas();                             //Clase para monitoreo de tareas
    public static ArrayList<String> webReport;
    public static ArrayList<Tarea> procesos;

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
            updateProcesos(newTasks);

            //Obtiene historial web y las agrega al historial de sesión
            ArrayList<String> webPages = web.getReport();
            updateWebReport(webPages);

        }
    };          //TmerTask del monitoreo de procesos y tráfico web

    public void updateProcesos(ArrayList<Tarea> newT){
        if(newT != procesos){
            for (Tarea tarea : newT) {
                if(!procesos.contains(tarea)){
                    procesos.add(tarea);
                }
            }
        }
    }
    
    public void updateWebReport(ArrayList<String> webPages){
        if(webReport.size() < webPages.size()){
            webReport= webPages;
        }
    }
    
    public Monitor(String ip, Long t) {

        webReport = new ArrayList<>();
        procesos = new ArrayList<>();
        
        sendReportTime = t;
        ipTarget = ip;
        
        System.out.println(ip);
        web = new MonitorWeb(ip);
        web.initMonitor();
        timer.schedule(task, 0, sendReportTime);
    }

    public static void guardarSesion(Sesion sesion) throws IOException {

            //Creamps un file en la carpeta data con el nombre del admin en sesión
            File file= new File("data/"+sesion.getAdminidAdmin().getUsrName()+sesion.getEntrada());
            //Creamos el archivo
            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            
            //Procederemos a es cribir los datos de sesión en él
            byte buffer[];                                          //Para guardar el archivo
            ByteArrayOutputStream bs = new ByteArrayOutputStream(); //Para generar el stream de salida
            ObjectOutputStream os = new ObjectOutputStream(bs);     //Para generar el stream de salida de objeto
            os.writeObject(sesion);                                 //Se escribe el objeto en el stream de salida
            buffer = bs.toByteArray();                              //se guarda en arreglo de bytes
            raf.write(buffer);                                      //Se escribe en archivo
            
            //Cerramos los archivos cerrables
            raf.close();
            bs.close();
            os.close();
            /*******************************/

    }

    public void detenerMonitoreoWeb() {
        web.stop();
        timer.cancel();
        timer = new Timer();
    }
}
