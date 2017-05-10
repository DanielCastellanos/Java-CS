package cliente;

import static cliente.Monitor.enviarSesion;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ricardo
 */
public class Cliente {

    public static AppSystemTray notIcon = new AppSystemTray();               //Icono de bandeja del sistema
    public static SesionCliente sesion = null;                               //Manejo de datos de sesión
    public static Monitor monitor;

    static Timer UpdateUsage = new Timer();
    static long tRegUso = 1 * 10000;
    static String path = "./";
    static Uso usoPc = nuevoUso();
    
    static TimerTask task2 = new TimerTask() {                          //TimerTask del monitoreo de tiempo de uso de la máquina

        @Override
        public void run() {

            Cliente.usoPc.setFin();
            Cliente.guardarUso();
        }
    };

    public static void main(String[] args) {

        BuscarServidor b = new BuscarServidor();
        AppSystemTray.mostrarMensaje("Iniciando cliente", AppSystemTray.INFORMATION_MESSAGE);
        b.iniciarCliente();

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
            

            if (BuscarServidor.connectionStatus()) //Si hay conexion con el Admin de grupo
            {
                int cont= -1;
                while(++cont < usoFiles.length){             //iteramos sobre el arreglo usoFiles
                    
                    try {
                        enviarSesion(usoFiles[cont]);                                              //Intentamos enviar los elementos del arreglo.
                        if(usoFiles[cont].delete()){
                            cont--;
                        }else{
                            System.out.println("No se eliminó archivo "+usoFiles[cont].getName());
                        }
            
                    } catch (IOException ex) {
                        System.err.println("Error al enviar archivo\n"+ ex.toString());
                    }

                }//Fin de ciclo sobre files
            }

        } else {
            System.err.println("*************El directorio temporal no existe");
            return null;
        }

        return new Uso(BuscarServidor.configuracion.getNombre());
    }

    public static void guardarUso() {
        try {
            System.out.println("------>?" + tRegUso + "----->");
            String fecha= usoPc.getInicio().toString().replace(":", "-").replace(" ", "_");
            RandomAccessFile raf = new RandomAccessFile(BuscarServidor.configuracion.getNombre() + "-" + "uso." + fecha+".jacs", "rw");
            byte buffer[];
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(bs);
            os.writeObject(Cliente.usoPc);
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


}
