package servidor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

public class Sesion implements Serializable{
    
    private static final long serialVersionUID = 2173L;
    private String usr;
    private  Date Entrada;
    private Date salida;
    
    private ArrayList<Tarea> taskHistory;
    private StringBuffer webHistory;
    
    public Sesion() {
    }

    public Sesion(String usr, Date Entrada) {
        this.usr = usr;
        this.Entrada = Entrada;
    }
    
    public String getUsr() {
        return usr;
    }

    public void setUsr(String usr) {
        this.usr = usr;
    }

    public Date getEntrada() {
        return Entrada;
    }

    public void setEntrada(Date Entrada) {
        
        this.Entrada = Entrada;
    }

    public Date getSalida() {
        return salida;
    }

    public void setSalida(Date salida) {
        this.salida = salida;
    }

    public ArrayList<Tarea> getTaskHistory() {
        return taskHistory;
    }

    public void setTaskHistory(ArrayList<Tarea> taskHistory) {
        this.taskHistory = taskHistory;
    }

    public StringBuffer getWebHistory() {
        return webHistory;
    }

    
    public void addWebHistory(StringBuffer buffer) {
        StringTokenizer line= new StringTokenizer(buffer.toString(), "|");
        
        while (line.hasMoreTokens()) {            
            String token= line.nextToken();
            if(this.webHistory.indexOf(token) != -1){
                this.webHistory.append(token);
            }
        }
        
    }
   
    public ArrayList getNewTasks(ArrayList<Tarea> newT) {
        ArrayList<Tarea> array = new ArrayList<>();
        for (Tarea t : newT) {
            if (!taskHistory.contains(t)) {
                newT.add(t);
                array.add(t);
            }
        }
        return array;
    }    
    
}
