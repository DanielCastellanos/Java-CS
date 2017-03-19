package cliente;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import org.jnetpcap.*;
import org.jnetpcap.nio.JBuffer;
import org.jnetpcap.packet.PcapPacket;
import org.jnetpcap.packet.PcapPacketHandler;
import org.jnetpcap.protocol.tcpip.Http;
import org.jnetpcap.protocol.tcpip.Tcp;

public class MonitorWeb {

    //Variables de clase
    static final String DOMAIN[]= {"com", "org", "net", "info", "biz", "edu", "gob"};
    StringBuilder errorBuffer = new StringBuilder();
    List<PcapIf> allDevs = new ArrayList<>();
    Pcap pcap;
    String ipTarget;

    public MonitorWeb(String ip) {
        ipTarget= ip;
        initMonitor();
        
    }
    private void initMonitor(){
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
        if (r == Pcap.NOT_OK || allDevs.isEmpty()) {
            return false;
        }
        return true;
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
    
    public static void getPageInPacket(String payload, String domain) {
        
        payload= payload.substring(0,payload.indexOf(domain)+domain.length());
        StringTokenizer token= new StringTokenizer(payload,"\n");
        String pagina;
        String aux;
        payload="";
        while(token.hasMoreTokens()){
            aux=token.nextToken();
            payload+=aux.substring(aux.indexOf("    ")+4, aux.length());
        }
        pagina= payload.substring(payload.lastIndexOf("..")+2,payload.length());
        System.err.println(pagina);
    }
    
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
                            getPageInPacket(payload, b);
                        }
                    }
                }
                else if(packet.hasHeader(http)){    
                    System.out.println(http.fieldValue(Http.Request.Host));
                }
            }
        }
};
}