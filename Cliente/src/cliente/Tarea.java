package cliente;

import java.io.Serializable;

/**
 *
 * @author Danniel
 */
public class Tarea implements Serializable{
    private static final long serialVersionUID = 2174L;
    private String nombreImagen,
                   PID,
                   usoMemoria,
                   tituloVentana;

    public void setNombreImagen(String nombreImagen) {
        this.nombreImagen = nombreImagen;
    }

    public void setPID(String PID) {
        this.PID = PID;
    }

    public void setUsoMemoria(String usoMemoria) {
        this.usoMemoria = usoMemoria;
    }

    public void setTituloVentana(String tituloVentana) {
        this.tituloVentana = tituloVentana;
    }

    public String getNombreImagen() {
        return nombreImagen;
    }

    public String getPID() {
        return PID;
    }

    public String getUsoMemoria() {
        return usoMemoria;
    }

    public String getTituloVentana() {
        return tituloVentana;
    }
    public void imprimir(){
        System.out.println(this.nombreImagen);
        System.out.println(this.PID);
        System.out.println(this.usoMemoria);
        System.out.println(this.tituloVentana);
    }
    @Override
    public String toString(){
        String cadena="";
            cadena+=this.nombreImagen+'|'+
                    this.PID+'|'+
                    this.usoMemoria+'|'+
                    this.tituloVentana;
        return cadena;
    }
}
