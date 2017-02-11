package cliente;

import bloqueo.FrameBlocked;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Ordenes {
    int contTiempo,tiempo;
Timer bloqueo=new Timer();
TimerTask tareaBloqueo=new TimerTask(){
    @Override
    public void run() {
        contTiempo++;
        if(tiempo==contTiempo)
        {
            contTiempo=0;
            pantallaInicio.login();
            
            bloqueo.cancel();
        }
    }
};
FrameBlocked pantallaInicio=new FrameBlocked();

    public Ordenes() {
        
    }
    
    public void nuveoArchivo(String mensaje,byte info[])
    {
        try {
            RandomAccessFile zip=new RandomAccessFile("MyZip.zip", "rw");
            String t=mensaje.substring(0,mensaje.indexOf(","));
            int tamaño=Integer.parseInt(t);
            String aux="archivo,"+t+",";
            System.out.println(tamaño);
            String dat=mensaje.substring(mensaje.indexOf(",")+1);
            System.out.println("*_*_*_*_*_"+dat);
            zip.write(info,aux.length(),tamaño);
            zip.close();
        }catch (IOException ex) {
            Logger.getLogger(Ordenes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void apagar(){
        try {
            Process proceso = Runtime.getRuntime().exec("shutdown /f");
        } catch (IOException e) {
            System.out.println("Excepción: ");
            e.printStackTrace();
        }
    }
    
    public void apagarAutomatico(int minutos){
        try {
            Process proceso = Runtime.getRuntime().exec("shutdown -s -t "+minutos);
        } catch (IOException e) {
            System.out.println("Excepción: ");
            e.printStackTrace();
        }
    }
    
    public void reiniciar(){
        try {
            Process proceso = Runtime.getRuntime().exec("shutdown /r");
        } catch (IOException e) {
            System.out.println("Excepción: ");
            e.printStackTrace();
        }
    }
    public void cerrar(String pid){
        try {
            Process proceso = Runtime.getRuntime().exec("taskkill /pid "+pid);
        } catch (IOException e) {
            System.out.println("Excepción: ");
            e.printStackTrace();
        }
    }
            
    
    public void bloquear(int tiempo){
        if(!(pantallaInicio.isVisible()))
        {
        pantallaInicio.bloqueoCompleto();
        this.tiempo=tiempo;
        this.bloqueo.schedule(tareaBloqueo,1000,1000);//poner 60000 para medir en minutos
        }
    }
    
    public void desbloquear(){
        pantallaInicio.login();
    }
    
    public void login(){
        if(!(pantallaInicio.isVisible()))
        {
         pantallaInicio.login();
        }
    }
    
    public void archivo(){
        
    }
}
