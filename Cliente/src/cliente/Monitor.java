package cliente;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Monitor {

    Timer timer = new Timer();
    long sendReportTime;
    String ipTarget;                                //Ip de la interfaz a monitorear
    MonitorWeb web;                                 //Clase para monitoreo de tráfico
    ListaTareas taskList;                             //Clase para monitoreo de tareas

    TimerTask task = new TimerTask() {
        @Override
        public void run() {

            //Obtiene lista de tareas
            taskList.escribirLista();
            ArrayList<Tarea> newTasks = taskList.getLista();

            /*El método getNewTasks compara el arreglo de tareas que entra, devuelve uno nuevo
            que se está guardando en el Array "toWrite" y agrega estas tareas al historial de 
            las sesion.
            */
            ArrayList<Tarea> toWrite = Cliente.sesion.getNewTasks(newTasks);
            //Agrega las nuevas tareas al historial de la sesion
            if (toWrite != null) {
                for (Tarea t : toWrite) {
                    Cliente.sesion.getTaskHistory().add(t);
                }
            }
            
            //Obtiene historial web y las agrega al historial de sesión
            StringBuffer webPages = web.getReport();
            Cliente.sesion.addWebHistory(webPages);
            
        }
    };

    public Monitor(String ip, Long t) {

        sendReportTime = t;
        ipTarget = ip;

        web = new MonitorWeb(ip);
        timer.schedule(task, sendReportTime);
    }

}
