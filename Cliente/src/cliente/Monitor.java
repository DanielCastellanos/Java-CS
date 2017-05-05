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
    Timer timer2 = new Timer();
    long sendReportTime;
    String ipTarget;                                //Ip de la interfaz a monitorear
    MonitorWeb web;                                 //Clase para monitoreo de tráfico
    ListaTareas taskList = new ListaTareas();                             //Clase para monitoreo de tareas
    static long tRegUso = 1 * 10000;
    static int numUso;
    static String path = "src/temp";
    static Uso usoPc = nuevoUso();

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
    TimerTask task2 = new TimerTask() {                          //TimerTask del monitoreo de tiempo de uso de la máquina

        @Override
        public void run() {

            Monitor.usoPc.setFin();
            Monitor.guardarUso();
        }
    };

    public Monitor(String ip, Long t) {

        sendReportTime = t;
        ipTarget = ip;

        web = new MonitorWeb(ip);
        web.initMonitor();
        timer.schedule(task, 0, sendReportTime);
        if (usoPc != null) {
            timer.schedule(task2, tRegUso, tRegUso);
        }
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

    private static Uso nuevoUso() {

        File file = new File(path);                                      //Declaramos un File con el directorio temporal

        if (file.exists()) {                                              //Si el directorio existe.

            FilenameFilter filter = new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.contains("uso");                                //el nombre contiene la cadena 'uso'?
                }
            };      //Se crea un filtro de nombre 
            File usoFiles[] = file.listFiles(filter);                        //Obtenemos un arreglo de archivos con los resultador del filtro de nombre
            numUso = usoFiles.length + 1;                                      //obtenemos el consecutivo que nombraremos al archivo de uso actual.

            if (BuscarServidor.connectionStatus()) //Si hay conexion con el Admin de grupo
            {
                for (File usoFile : usoFiles) {             //iteramos sobre el arreglo usoFiles
                    
                    try {
                        enviarSesion(usoFile);                                              //Intentamos enviar los elementos del arreglo.
                        numUso--;
                    } catch (IOException ex) {
                        System.err.println("Error al enviar archivo\n"+ ex.toString());
                    }

                }                                           //Fin del loop sobre uso Files
            }

        } else {
            System.err.println("*************El directorio temporal no existe");
            return null;
        }

        return new Uso(BuscarServidor.configuracion.getNombre());
    }

    public static void guardarUso() {
        try {
            System.out.println("------>?" + tRegUso + "----->" + numUso);
            RandomAccessFile raf = new RandomAccessFile(BuscarServidor.configuracion.getNombre() + "-" + "uso_" + usoPc.getInicio(), "rw");
            byte buffer[];
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(bs);
            os.writeObject(Monitor.usoPc);
            buffer = bs.toByteArray();
            raf.write(buffer);
            raf.close();
            bs.close();
            os.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Ordenes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void detenerMonitoreoWeb() {
        web.stop();
    }
}
