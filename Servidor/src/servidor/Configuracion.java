package servidor;

import entity.Pc;
import java.io.Serializable;

public class Configuracion implements Serializable{

    private static final long serialVersionUID=2180L;
    //variables configurables
    private Pc pcServidor=new Pc();
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
    public Pc getPcServidor() {
        return pcServidor;
    }

    public void setPcServidor(Pc pcServidor) {
        this.pcServidor = pcServidor;
    }
    public void pcToString()
    {
        System.out.println("**************************************\n"+
                pcServidor.getNombre()+"\n"+
                pcServidor.getHostname()+"\n"+
                pcServidor.getMarca()+"\n"+
                pcServidor.getModelo()+"\n"+
                pcServidor.getNoSerie()+"\n"+
                pcServidor.getDiscoDuro()+"\n"+
                pcServidor.getMac()+"\n"+
                pcServidor.getOs()+"\n"+
                pcServidor.getProcesador()+"\n"+
                pcServidor.getRam()+"\n"
        +"**************************************");
    }
}
