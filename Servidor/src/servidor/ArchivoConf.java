package servidor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ArchivoConf {
    File configuracion;
    public String nombreServ;
    public String grupo;
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
