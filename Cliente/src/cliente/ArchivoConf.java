package cliente;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ArchivoConf 
{
    private String nombre,
            grupo,
            serverHost;

    public String getServerHost() {
        return serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }
    File configuracion;
    RandomAccessFile escribir;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }
    
    public void archivoNuevo()
    {
        try {
            configuracion=new File("Configuracion.txt");
            if(configuracion.exists())
            {
                configuracion.delete();
            }
            escribir=new RandomAccessFile(configuracion,"rw");
            escribir.write(("nombre="+nombre+"\r\n"
                    + "grupo="+grupo
                    +"\r\n server hostname="+serverHost).getBytes());
            escribir.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ArchivoConf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ArchivoConf.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public boolean cargarConfiguracion()
    {
        boolean existe=false;
        
        try {
            escribir=new RandomAccessFile("Configuracion.txt","rw");
            long tamaño=escribir.length();
            if(tamaño>0)
            {
                existe=true;
                String linea=escribir.readLine();
                nombre=linea.substring(linea.indexOf("=")+1,linea.indexOf("#"));
                linea=escribir.readLine();
                grupo=linea.substring(linea.indexOf("=")+1,linea.length());
            }
            escribir.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ArchivoConf.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ArchivoConf.class.getName()).log(Level.SEVERE, null, ex);
        }
        return existe;
    }
    
}
