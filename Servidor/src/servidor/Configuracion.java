package servidor;

import java.io.File;
import java.io.Serializable;

public class Configuracion implements Serializable{

    private static final long serialVersionUID=2180L;
    //variable del archivo de configuracion
    private File configuracion;
    //variables configurables
    private String nombreServ;           //Nombre del servidor
    private String grupo;                //Grupo multicast usado por este Admin
    private String URLBD="null";                //Dirección de la base de datos
    private String userBD="null";               //Usuario de la BD
    private String passBD="null";               //Contraseña de la BD

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
}
