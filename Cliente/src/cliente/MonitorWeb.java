package cliente;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jnetpcap.*;
import org.jnetpcap.nio.JBuffer;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;
import org.jnetpcap.protocol.tcpip.Http;
import org.jnetpcap.protocol.tcpip.Tcp;

public class MonitorWeb {

    //Variables de clase
    static final String DOMAIN[]= {"com", "org", "net", "info", "biz", "edu", "gob"};   //cadenas de dominios a buscar en paquetes que salen por el puerto 443.
    StringBuilder errorBuffer = new StringBuilder();        //Buffer para registrar errores que puedan ocurrir
    List<PcapIf> allDevs = new ArrayList<>();               //Lista para almacenar las interfaces
    Pcap pcap;                                              //Variable Pcap para capturar los paquetes entrantes
    String ipTarget;                                        //Cadena correspondiente a la ip de la interfaz que se va a escuchar
    ArrayList<String> webPages;                             //Para guardar temporalmente las páginas
    Timer time= new Timer();                                //Para controlar el tiempo de envío de historial al admin.
    //Constructor de la clase
    public MonitorWeb(String ip) {
        ipTarget= ip;                       //Recibe una cadena como parámetro que representa la ip de la interfaz a monitorear
        initMonitor();                      //Realiza configuraciones iniciales
        
    }
    
    private void initMonitor(){
        webPages= new ArrayList<>();
        if(!findDevices()){
            System.err.printf("No se pudo obtener lista de interfaces %s", errorBuffer
            .toString());
            AppSystemTray.mostrarMensaje("Error al obtener interfaces de red", 4);
        }else{
            int indexDev= pickDevice(ipTarget);
            if(indexDev == -1){
                System.out.println("No hay dispositivos viables");
                AppSystemTray.mostrarMensaje("No hay dispositivos viables", 4);
            }else{
                openDevice(indexDev);
                pcap.loop(-1, handler,"Paquete capturado");
            }
        }
    }
    
    private boolean findDevices(){
        
        int r = Pcap.findAllDevs(this.allDevs, this.errorBuffer);
        return !(r == Pcap.NOT_OK || allDevs.isEmpty());
    }
    
    private int pickDevice(String ip){
        
        for (PcapIf dev : allDevs) {
            
            String devAddr= dev.getAddresses().toString();
            devAddr= devAddr.substring(devAddr.indexOf(":")+1, devAddr.indexOf(",")-1);
            System.out.println(devAddr+"=="+ip+"?");
            if(ip.equals(devAddr)){
                
                return allDevs.indexOf(dev);
            }
        }
        
        return -1;
    }
    
    public void openDevice(int indexDevice){
        int snaplen = 64 * 1024;           // Capture all packets, no trucation  
        int flags = Pcap.MODE_PROMISCUOUS; // capture all packets  
        int timeout = 10 * 1000;           // 10 seconds in millis  
        
        pcap= Pcap.openLive(allDevs.get(indexDevice).getName(), snaplen, flags, timeout, errorBuffer);
    }
    
    //Trabaja con la estructura del paquete para extraer solamente el dominio del host destino
    public static String getPageInPacket(String payload, String domain) {
        
        payload= payload.substring(0,payload.indexOf(domain)+domain.length());
        StringTokenizer token= new StringTokenizer(payload,"\n");
        String wPage;
        String temp;
        payload="";
        while(token.hasMoreTokens()){
            temp=token.nextToken();
            payload+=temp.substring(temp.indexOf("    ")+4, temp.length());
        }
        wPage= payload.substring(payload.lastIndexOf("..")+2,payload.length());
        return wPage;
    }
    
    //Verifica si la página ya está registrada en el arreglo temporal, si no, la registra
    private void pageRecieved(String page){
        if(page!=null){
            if(!webPages.contains(page)){
                webPages.add(page);
                System.out.println(page);
            }
        }
    }
    
    private void cleanList(){
        
    }
    
    private void writeTemp(){
        if(BuscarServidor.connectionStatus()){
            try {
                RandomAccessFile temp= new RandomAccessFile("temp.wt", "rw");
                if(temp.length()!=0){
                    temp.seek(temp.length()-1);
                }
                
                pcap.breakloop();
                for (String page : webPages) {
                    temp.writeBytes(page+"\n");
                }
                webPages.clear();
                pcap.loop(-1, handler,"Paquete capturado");
                
            } catch (FileNotFoundException ex) {
                Logger.getLogger(MonitorWeb.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(MonitorWeb.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    //Manejador de paquetes recibidos.
    PcapPacketHandler<String> handler = new PcapPacketHandler<String>(){
        
        @Override
        public void nextPacket(PcapPacket packet, String user){
        Tcp tcp= new Tcp();
        Http http= new Http();
        if (packet.hasHeader(tcp)) {
                
                if (tcp.destination() == 443) {
                    int payloadstart = tcp.getOffset() + tcp.size();
                    JBuffer buffer = new JBuffer(64 * 1024);
                    buffer.peer(packet, payloadstart, packet.size() - payloadstart);
                    String payload = buffer.toHexdump(packet.size(), false, true, true);
//                  
                   for (String b : DOMAIN) {
                        if (payload.contains(b)) {
                            String page= getPageInPacket(payload, b);
                            pageRecieved(page);
                            
                        }
                    }
                }
                else if(packet.hasHeader(http)){    
                    String page= http.fieldValue(Http.Request.Host);
                    System.out.println(page);
                }
            }
        }
};
}