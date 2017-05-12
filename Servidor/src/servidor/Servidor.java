package servidor;

import bloqueo.FrameBlocked;
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
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Ricardo
 */
public class Servidor {

    static BuscarGrupo comunicacion = new BuscarGrupo();
    public static entity.Sesion sesion = null;
    public static FrameBlocked bloqueo= new FrameBlocked();
    public static Monitor monitor;

    /**
     * *************************************
     */
    //  Monitorear tiempo de uso, variables:
    /**
     * *************************************
     */
    static long tRegUso = 1 * 10000;
    static String path = "./";
    static entity.UsoPc usoPc;
    static Timer timerUso = new Timer();

    static TimerTask updateUsage = new TimerTask() {                          //TimerTask del monitoreo de tiempo de uso de la máquina

        @Override
        public void run() {

            usoPc.setApagado(new Date());
            guardarUso();
        }
    };

    public static void main(String[] args) {

        comunicacion.iniciarServidor();
        Listeners ls = new Listeners();
        ls.beginListeners();
        usoPc= nuevoUso();
        bloqueo.setVisible(true);
    }

    private static UsoPc nuevoUso() {

        if(registrarUsos())
            System.out.println("Registró Usos");
        else
            System.out.println("No registró");
        
        UsoPc uso = new UsoPc();
        uso.setEncendido(new Date());
        uso.setPCidPC(BuscarGrupo.conf.getPcServidor());

        return uso;
    }

    public static void guardarUso() {
        try {
            System.out.println("------>?" + tRegUso + "----->");
            String fecha = usoPc.getEncendido().toString().replace(":", "-").replace(" ", "_");
            RandomAccessFile raf = new RandomAccessFile("uso." + fecha+".jacs", "rw");
            byte buffer[];
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(bs);
            os.writeObject(usoPc);
            buffer = bs.toByteArray();
            raf.write(buffer);
            raf.close();
            bs.close();
            os.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static boolean registrarUsos() {
        File file = new File(path);                                      //Declaramos un File con el directorio temporal

        if (file.exists()) {                                              //Si el directorio existe.
            
            FilenameFilter filter = new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.contains("uso");                                //el nombre contiene la cadena 'uso'?
                }
            };      //Se crea un filtro de nombre 
            File usoFiles[] = file.listFiles(filter);                        //Obtenemos un arreglo de archivos con los resultador del filtro de nombre
for (File usoFile : usoFiles) {
                System.out.println(usoFile.getName());
            }
            if (hibernate.HibernateUtil.isConnected()) //Si hay conexion con la base de datos
            {
                
                bdUtil bd= new bdUtil();
                int cont=-1;
                while (++cont < usoFiles.length) {             //iteramos sobre el arreglo usoFiles
                    try {
                        
                        //Aquí se persistirán los archivos de Uso que pudieron ser guardados de manera local.
                        RandomAccessFile raf = new RandomAccessFile(usoFiles[cont].getName(), "rw");                    //Declara RAF para leer todo el archivo 
                        byte[] bytes = new byte[(int) raf.length()];                                //Declara arreglo del tamaño del archivo
                        System.out.println(raf.length());
                        raf.read(bytes);                                           //Guarda el archivo en el arreglo
                        
                        //preparamos la entrada de datos del array
                        ByteArrayInputStream bs = new ByteArrayInputStream(bytes);
                        //preparamos la entrada para obtener el objeto "Sesion"
                        ObjectInputStream ois = new ObjectInputStream(bs);
                        //obtenemos el objeto "Uso"
                        UsoPc uso = (UsoPc) ois.readObject();
                        System.out.println(uso.getPCidPC().getIdPC()+"-"+uso.getEncendido().toString());
                        //Guarda el uso en la base de datos
                        System.out.println("pc"+BuscarGrupo.conf.getPcServidor().getIdPC());
                        bd.saveUsoPc(BuscarGrupo.conf.getPcServidor(), uso.getEncendido(), uso.getApagado());
                        //Se cierra el Random Access File
                        raf.close();                     
                        if(usoFiles[cont].delete()){
                            System.out.println("borrao");
                        }else{
                            System.out.println("No se borró archivo "+usoFiles[cont].getName()+"tras persistir datos");
                        }
                    } catch (IOException ex) {
                        System.err.println("Error al leer objeto\n" + ex.toString());
                        ex.printStackTrace();
                        return false;
                    } catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                        return false;
                    }

                }//Fin del loop sobre uso Files
            }else{
                return false;
            }

        } else {
            System.err.println("*************El directorio temporal no existe");
            return false;
        }
        return true;
    }
    
    public void iniciarSesion(){
        sesion= new Sesion();
        sesion.setAdminidAdmin();
    }
}
