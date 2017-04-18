package servidor;

import cliente.SesionCliente;
import entity.Pc;
import hibernate.HibernateUtil;
import interfaz.BDConfig;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.hibernate.HibernateException;

/**
 *
 * @author Ricar
 */
public class Archivos {
    public static void guardarConf(Configuracion conf)
    {
        try {
            File configuracion = new File("Configuracion.txt");
            if (configuracion.exists()) {
                configuracion.delete();
            }
            RandomAccessFile escribir = new RandomAccessFile(configuracion, "rw");
            ByteArrayOutputStream bos=new ByteArrayOutputStream();
            ObjectOutputStream os=new ObjectOutputStream(bos);
            os.writeObject(conf);
            byte salida[]=new Cifrado().cifrar(bos.toByteArray());
            escribir.write(salida);
            escribir.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Configuracion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Configuracion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static Configuracion cargarConf()
    {
        Configuracion conf=null;
        try {
            File configuracion = new File("Configuracion.txt");
            if(configuracion.length()>0)
            {
            RandomAccessFile leer = new RandomAccessFile(configuracion, "rw");
            byte buffer[]=new byte[(int)leer.length()];
            leer.readFully(buffer);
            buffer=new Cifrado().descifrar(buffer);
            ByteArrayInputStream bis=new ByteArrayInputStream(buffer);
            ObjectInputStream ois=new ObjectInputStream(bis);
            conf=(Configuracion)ois.readObject();
            leer.close();
            bis.close();
            ois.close();
             try {
                    HibernateUtil.buildSessionFactory(conf.getURLBD(), conf.getUserBD(), conf.getPassBD());

                } catch (HibernateException ex) {
                    /* De generarse un error 
                                                        Se preguntará al usuario si desea verificar los datos de conexión
                     */
                    int opc = JOptionPane.showConfirmDialog(null, "No se pudo realizar la consexión con la base de datos\n¿Desea revisar los ajustes?",
                            "Desconectado de BD", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                    if (opc == 0) {
                        new BDConfig(conf, conf.getURLBD(), conf.getUserBD(), conf.getPassBD()).setVisible(true);
                    }
                }
                //Impresión en consola de la carga
                System.out.println("Nombre Serv: " + conf.getNombreServ() + " Grupo: " + conf.getGrupo());
                System.out.println("Base de datos en: " + conf.getURLBD() + " Usuario " + conf.getUserBD() + "," + conf.getPassBD());
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Configuracion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Configuracion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Archivos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conf;
    }
    public static void guardarListaClientes(ArrayList<Pc> lista)
    {
        try {
            File archivo=new File("ListaClientes.txt");
            if (archivo.exists()) {
                archivo.delete();
            }
            RandomAccessFile escribir=new RandomAccessFile(archivo,"rw");
            ByteArrayOutputStream bos=new ByteArrayOutputStream();
            ObjectOutputStream os=new ObjectOutputStream(bos);
            os.writeObject(lista);
            byte salida[]=Cifrado.cifrar(bos.toByteArray());
            escribir.write(salida);
            escribir.close();
            bos.close();
            os.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Archivos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Archivos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static ArrayList<Pc> cargarListaClientes()
    {
        File archivo=new File("ListaClientes.txt");
        ArrayList<Pc> lista=new ArrayList<>();
        try {
            RandomAccessFile leer=new RandomAccessFile(archivo,"r");
            byte clientes[]=new byte[(int)leer.length()];
            leer.readFully(clientes);
            clientes=Cifrado.descifrar(clientes);
            ByteArrayInputStream bis=new ByteArrayInputStream(clientes);
            ObjectInputStream is=new ObjectInputStream(bis);
            lista=(ArrayList<Pc>)is.readObject();
            leer.close();
            bis.close();
            is.close();
        } catch (FileNotFoundException ex) {
            System.err.println("Lista Clientes inexistente");
        } catch (IOException ex) {
            Logger.getLogger(Archivos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Archivos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }
    public static void guardarSesion(String nombre,byte sesion[])
    {
        ObjectInputStream ois=null;
        try {
            //preparamos la entrada de datos del array
            ByteArrayInputStream bs=new ByteArrayInputStream(sesion);
            //preparamos la entrada para obtener el objeto "Sesion"
            ois = new ObjectInputStream(bs);
            //obtenemos el objeto "Sesion"
            SesionCliente s=(SesionCliente)ois.readObject();
            //lo añadimo a la lista se sesiones
            BuscarGrupo.listaSesiones.add(s);
            //preparamos el archivo para escribir el objeto
            RandomAccessFile archivo=new RandomAccessFile(nombre,"rw");
            //cerramos toda entrada y salida de datos 
            archivo.write(sesion);
            archivo.close();
            ois.close();
            bs.close();
        } catch (IOException ex) {
            Logger.getLogger(Archivos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Archivos.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ois.close();
            } catch (IOException ex) {
                Logger.getLogger(Archivos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public static SesionCliente CargarSesion(File archivo)
    {
        SesionCliente sesion=null;
        try {
            RandomAccessFile leer=new RandomAccessFile(archivo,"r");
            byte buffer[]=new byte[(int)leer.length()];
            leer.readFully(buffer);
            buffer=Cifrado.descifrar(buffer);
            ByteArrayInputStream bis=new ByteArrayInputStream(buffer);
            ObjectInputStream is=new ObjectInputStream(bis);
            sesion=(SesionCliente)is.readObject();
            leer.close();
            bis.close();
            is.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Archivos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Archivos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Archivos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sesion;
    }
}
