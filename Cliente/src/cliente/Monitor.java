package cliente;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
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
    ListaTareas taskList;                             //Clase para monitoreo de tareas
    ArrayList<Tarea> tasks;

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            //Se creará un archivo para guardar los datos de la sesión
            try {
                String fileName = BuscarServidor.configuracion.getNombre() + "_" + Sesion.usr;       //String con el que será el nombre del archivo
                RandomAccessFile file = new RandomAccessFile(fileName, "rw");
                //Obtiene lista de tareas
                taskList.escribirLista();
                ArrayList<Tarea> newTasks = taskList.getLista();

                StringBuffer webPages = web.getReport();                         //Obtiene historial web

                if (file.length() != 0) {                                         //Posicionamieto del puntero    
                    file.seek(file.length() - 1);
                } else {
                    String cabecera
                            = Sesion.usr + "|"
                            + Sesion.Entrada.toString() + "|"
                            + Sesion.salida.toString() + "\r\n";
                    file.writeBytes(cabecera);
                }

                //Agregar tareas al archivo
                ArrayList<Tarea> toWrite = getNewTasks(newTasks);
                if (toWrite != null) {
                    for (Tarea t : toWrite) {
                        file.writeBytes("-" + t.toString() + "\r\n");
                        tasks.add(t);
                    }
                }
                //Agregar páginas al archivo
                file.writeBytes(webPages.toString());

            } catch (FileNotFoundException ex) {
                Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };

    public Monitor(String ip, Long t) {

        sendReportTime = t;
        ipTarget = ip;

        web = new MonitorWeb(ip);
        timer.schedule(task, sendReportTime);
    }

    public ArrayList getNewTasks(ArrayList<Tarea> newT) {
        ArrayList<Tarea> array = new ArrayList<>();
        for (Tarea t : newT) {
            if (!tasks.contains(t)) {
                newT.add(t);
                array.add(t);
            }
        }
        return array;
    }
}
