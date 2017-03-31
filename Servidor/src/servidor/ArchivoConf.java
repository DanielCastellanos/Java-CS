package servidor;

import interfaz.BDConfig;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;

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
        this.passBD = passBD;
    }
    
    public void nuevoArchivo()
    {
        try {
            configuracion=new File("Configuracion.txt");
            if (configuracion.exists()) {
                configuracion.delete();
            }
             escribir=new RandomAccessFile(configuracion, "rw");
                escribir.write(StandardCharsets.UTF_8.encode("#nombre="+nombreServ+
                                  "\r\n#Grupo="+grupo).array());
                
                ///Configuración de conexion con BD.
                
                System.out.println("continua");
            escribir.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ArchivoConf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ArchivoConf.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean CargarConf()
    {
        boolean existe=false;
        try {
            escribir=new RandomAccessFile("Configuracion.txt","rw");
            long tamaño;
                tamaño = escribir.length();
            
            if(tamaño>0)
            {
                existe=true;
                String linea=escribir.readLine();
                nombreServ=linea.substring(linea.indexOf("=")+1);
                linea=escribir.readLine();
                grupo=linea.substring(linea.indexOf("=")+1);
                System.out.println("Nombre Serv: "+nombreServ+" Grupo: "+grupo);
            }
            else
            {
                existe=false;
            }
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
            Logger.getLogger(ArchivoConf.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            try {
                escribir.close();
            } catch (IOException ex) {
                Logger.getLogger(ArchivoConf.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return existe;
    }
}
