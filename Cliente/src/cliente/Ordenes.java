package cliente;

import bloqueo.FrameBlocked;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
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
    
    public void info(){
        String[] datos=new String [4];
        
        try {
            datos[0]=System.getProperty("user.name");
            datos[1]=System.getProperty("os.name");
            datos[2]=Inet4Address.getLocalHost().getHostName();
            datos[3]=Inet4Address.getLocalHost().getHostAddress();
            
            System.out.println("Datos obtenidos");
            
            //Falta envío de información
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void nuevoArchivo(String mensaje,byte info[])
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
        try {
            ServerSocket ss=new ServerSocket(4400);
            Socket socket;
            socket=ss.accept();
            DataInputStream archivo=new DataInputStream(socket.getInputStream());
            String nombre=archivo.readUTF();
            int tamaño=archivo.readInt();
            byte buffer[]=new byte[tamaño];
            archivo.read(buffer, 0, buffer.length);
            RandomAccessFile salida=new RandomAccessFile(nombre,"rw");
            salida.write(buffer, 0, buffer.length);
            salida.close();
            socket.close();
            ss.close();
        } catch (Exception e) {
        }
    }
}
