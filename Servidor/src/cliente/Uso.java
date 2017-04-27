package cliente;

import java.util.Date;

public class Uso {
    
    private String pcName;
    private Date inicio;
    private Date fin;

    public Uso(String name) {
        pcName= name;
        this.inicio = new Date();
    }

    public String getPcName() {
        return pcName;
    }

    public void setPcName(String pcName) {
        this.pcName = pcName;
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getFin() {
        return fin;
    }

    public void setFin() {
        this.fin = new Date();
    }
    
    
}
