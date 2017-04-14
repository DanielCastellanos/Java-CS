package cliente;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SesionCliente implements Serializable{
    
    private static final long serialVersionUID = 2173L;
    private String usr;
    private  Date Entrada;
    private Date salida;
    
    private ArrayList<Tarea> taskHistory=new ArrayList<>();
    private ArrayList<String> webHistory= new ArrayList<>();
    
    public SesionCliente() {
    }

//    public SesionCliente(String usr) {
//        this.usr = usr;
//        this.Entrada = new Date();
//        try {
//            Cliente.monitor=new Monitor(InetAddress.getLocalHost().getHostAddress(),5000L);
//        } catch (UnknownHostException ex) {
//            Logger.getLogger(SesionCliente.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
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

    public ArrayList<String> getWebHistory() {
        return webHistory;
    }
    
    public void addWebHistory(StringBuffer buffer) {
        StringTokenizer line= new StringTokenizer(buffer.toString(), "|");
        
        while (line.hasMoreTokens()) {            
            String token= line.nextToken();
            if(this.webHistory.indexOf(token) == -1){
                this.webHistory.add(token);
            }
        }
        
    }
   
    public void saveNewTasks(ArrayList<Tarea> newT) {        
        for (Tarea t : newT) {
            if (!taskHistory.contains(t)) {
                taskHistory.add(t);
            }
        }
    }    

//    public void cerrarSesion(){
//        try {
//            System.out.println("cerrando...");
//            this.setSalida(new Date());
//            Cliente.monitor.detenerMonitoreoWeb();
//            Monitor.guardarSesion(this, BuscarServidor.configuracion.getNombre());
//            if(BuscarServidor.connectionStatus() == true){
//                File file= new File(BuscarServidor.configuracion.getNombre()+"-"+this.usr);
//                Monitor.enviarSesion(file);
//            }
//        } catch (IOException ex) {
//            System.err.println("Error: "+ ex.toString());
//            AppSystemTray.mostrarMensaje("Error al cerrar sesión", AppSystemTray.ERROR_MESSAGE);
//        }
//    }
}
