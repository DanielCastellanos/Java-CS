package servidor;

import hibernate.HibernateUtil;
import interfaz.BDConfig;
import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.hibernate.HibernateException;

public class ArchivoConf {

    //variable del archivo de configuracion
    File configuracion;
    //variables configurables
    public String nombreServ;           //Nombre del servidor
    public String grupo;                //Grupo multicast usado por este Admin
    public String URLBD;                //Dirección de la base de datos
    public String userBD;               //Usuario de la BD
    public String passBD;               //Contraseña de la BD
    //Objeto RandomAcccessFile para tratar archivo de configuración
    RandomAccessFile escribir;
    //Icono de dialogos
    Image img = new ImageIcon("src/iconos/logo chico.png").getImage();
    ImageIcon ico = new ImageIcon(img.getScaledInstance(30, 30, Image.SCALE_SMOOTH));

    public String getNombreServ() {
        return nombreServ;
    }

    public void setNombreServ(String nombreServ) {
        this.nombreServ = nombreServ;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getURLBD() {
        return URLBD;
    }

    public void setURLBD(String URLBD) {
        this.URLBD = URLBD;
    }

    public String getUserBD() {
        return userBD;
    }

    public void setUserBD(String userBD) {
        this.userBD = userBD;
    }

    public String getPassBD() {
        return passBD;
    }

    public void setPassBD(String passBD) {
        if (passBD != null) {
            this.passBD = passBD;
        }
    }

    public void nuevoArchivo() {
        try {
            configuracion = new File("Configuracion.txt");
            if (configuracion.exists()) {
                configuracion.delete();
            }
            escribir = new RandomAccessFile(configuracion, "rw");
            escribir.write(StandardCharsets.UTF_8.encode(
                    "#nombre=" + nombreServ
                    + "\r\n#Grupo=" + grupo
                    + "\r\n#URL_BD=" + URLBD
                    + "\r\n#Usuario_BD=" + userBD
                    + "\r\n#Contraseña_BD=" + passBD).array());
            escribir.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ArchivoConf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ArchivoConf.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean CargarConf() {
        boolean existe = false;
        try {
            escribir = new RandomAccessFile("Configuracion.txt", "rw");
            long tamaño;
            tamaño = escribir.length();

            if (tamaño > 0) {
                existe = true;
                /*
                Primero se carga la configuración básica para la conexión
                con otros equipos en un grupo
                 */
                //Lee y carga el nombre de este equipo desde el archivo
                String linea = escribir.readLine();
                nombreServ = linea.substring(linea.indexOf("=") + 1);
                //Lee y carga el grupo multicast desde el archivo
                linea = escribir.readLine();
                grupo = linea.substring(linea.indexOf("=") + 1);

                /*
                Posteriormente se carga la configuración para la conexión con la base de datos.
                 */
                //Lee y carga la url de la base de datos
                linea = escribir.readLine();
                URLBD = linea.substring(linea.indexOf("=") + 1);
                //Lee y carga el usuario de la base de datos
                linea = escribir.readLine();
                userBD = linea.substring(linea.indexOf("=") + 1);
                //Lee y carga la contraseña de la base de datos
                linea = escribir.readLine();
                passBD = linea.substring(linea.indexOf("=") + 1);

                /*
                Después de cargar las propiedades de la conexión con la BD
                se intentará realizar dicha conexión
                 */
                try {
                    HibernateUtil.buildSessionFactory(URLBD, userBD, passBD);

                } catch (HibernateException ex) {
                    /* De generarse un error 
                                                        Se preguntará al usuario si desea verificar los datos de conexión
                     */
                    int opc = JOptionPane.showConfirmDialog(null, "No se pudo realizar la consexión con la base de datos\n¿Desea revisar los ajustes?",
                            "Desconectado de BD", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, ico);
                    if (opc == 0) {
                        new BDConfig(this, URLBD, userBD, passBD).setVisible(true);
                    }
                }
                //Impresión en consola de la carga
                System.out.println("Nombre Serv: " + nombreServ + " Grupo: " + grupo);
                System.out.println("Base de datos en: " + URLBD + " Usuario " + userBD + "," + passBD);
            } else {
                existe = false;
            }
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
            Logger.getLogger(ArchivoConf.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                escribir.close();
            } catch (IOException ex) {
                Logger.getLogger(ArchivoConf.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return existe;
    }
}
