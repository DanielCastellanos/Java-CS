package cliente;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;
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
    StringBuffer webPages;                                  //Para guardar temporalmente las páginas
    Timer timer= new Timer();                               //Para controlar el tiempo de envío de historial al admin.
    long sendReportTime;                                    /*Variable para guardar el tiempo en que se va a ejecutar la tarea de 
                                                            envío del registro de trafico en el buffer en segundos*/
    long seconds;                                           //Contador de tiempo transcurrido desde el envío anterior en segundos
    /*Tarea para el timer que realizará el guardado de la información en el String buffer webPages*/
    TimerTask task= new TimerTask(){                        
        @Override
        public void run() {
            if(!BuscarServidor.connectionStatus()){                                 //Si la conexión con admin está abajo
                writeInTemporalFile();
            }
            else{
                sendInfo();
            }
            seconds= 0;
        }  
    };
    
    //Constructor de la clase
    public MonitorWeb(String ip, long t) {
        
        ipTarget= ip;                       //Recibe una cadena como parámetro que representa la ip de la interfaz a monitorear
        sendReportTime = t;                 //Recibe un long que será el tiempo entre cada guardado de historial
        initMonitor();                      //Realiza configuraciones iniciales
        
    }
    
    private void initMonitor(){
        webPages= new StringBuffer();
        timer.schedule(task, sendReportTime);
        if(!findDevices()){
            System.err.printf("No se pudo obtener lista de interfaces %s", errorBuffer
            .toString());
            AppSystemTray.mostrarMensaje("Error al obtener interfaces de red", 4);
        }else{
            int indexDev= pickDevice(ipTarget);
            if(indexDev == -1){
                System.out.println("No hay dispositivos viables");
                AppSystemTray.mostrarMensaje("No hay dispositivos viables", AppSystemTray.ERROR_MESSAGE);
            }else{
                openDevice(indexDev);
                pcap.loop(-1, handler,"Paquete capturado");
            }
        }
    }
    /*Éste método encuentra las interfaces de red de las cuales se puede escuchar el tráfico. se retorna false si hay algún error, sino retorna true*/
    private boolean findDevices(){
        
        int r = Pcap.findAllDevs(this.allDevs, this.errorBuffer);
        return !(r == Pcap.NOT_OK || allDevs.isEmpty());
    }
    
    /*
        El método pickDevice nos ayuda a buscar la interfaz con la ip que se le pasó al constructor
    */
    private int pickDevice(String ip){          
        
        for (PcapIf dev : allDevs) {                    /*Se inicia un bucle que recorre las intercafes del arreglo 
                                                            obtiene las direcciones de cada una y verifica si la ip objetivo se encuentara entre ellas.
                                                        */
            String devAddr= dev.getAddresses().toString();
            System.out.println(devAddr+"=="+ip+"?");
                                                        //Si la contiene
            if(devAddr.contains(ip)){    
                return allDevs.indexOf(dev);            //el métodod retorna el indice de dicha interfaz dentro de la lista allDevs.
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
            if(webPages.indexOf(page) == -1){
                webPages.append(page).append("\r\n");
                System.out.println(page);
            }
        }
    }
    
    
    /*Si el programa cliente está corriendo sin conexión con la máquina admin
    El historial web se almacenará en un archivo.
    */
    private void writeInTemporalFile(){
        
            try {                       
                                                                                //Declaramos un RandomAccessFile del archivo temp.wt
                RandomAccessFile temp= new RandomAccessFile("temp.wt", "rw");           //Y colocamos el puntero en donde vamos a escribir
                if(temp.length()!=0){
                    temp.seek(temp.length()-1);
                }
                                                                                //Vaciamos el contenido de webPages en el archivo temporal
                temp.writeBytes(webPages.toString());                           //Y borramos su contenido.
                webPages.setLength(0);
                
            } catch (FileNotFoundException ex) {
                Logger.getLogger(MonitorWeb.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(MonitorWeb.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    /*De haber conexión se enviará la información al equipo administrador dek grupo
    para su análisis y persistencia*/
    private void sendInfo(){
        
    }
    
    //Manejador de paquetes recibidos.
    PcapPacketHandler<String> handler = new PcapPacketHandler<String>(){
        
        @Override
        public void nextPacket(PcapPacket packet, String user){
        
        //Se declaran las clases de protocolos TCP y Http
        Tcp tcp= new Tcp();
        Http http= new Http();
        
        if (packet.hasHeader(tcp)) {                //Si el paquete recivido tiene cabecera tcp
                
                if (tcp.destination() == 443) {                                         /*Si el destino del paquete es el puerto 443 (https)
                                                                                        la información no se leerá de manera directa así que 
                                                                                        se tendrá que trabajar con la carga. Así que guardamos en un
                                                                                        JBuffer dicha carga en hexdump textual
                                                                                        */
                    int payloadstart = tcp.getOffset() + tcp.size();
                    JBuffer buffer = new JBuffer(64 * 1024);
                    buffer.peer(packet, payloadstart, packet.size() - payloadstart);
                    String payload = buffer.toHexdump(packet.size(), false, true, true);
                                                                                      /*Encontramos el dominio de la página para filtrar la información
                                                                                        de entre todo el contenido de la cargar*/
                   for (String b : DOMAIN) {
                        if (payload.contains(b)) {
                            String page= getPageInPacket(payload, b);                   //Guardamos la página obtenida por el método getPageInPacket
                            pageRecieved(page);                                         //Y llamamos al método para guardala en el arreglo temporal
                            
                        }
                    }
                }
                else if(packet.hasHeader(http)){                                        //De lo contrario verificamos si el paquete tiene cabecera http
                    String page= http.fieldValue(Http.Request.Host);                    //guardamos la cadema obtenida por http.fieldValue que corresponde al host donde va la petición
                    System.out.println(page);                                           
                    pageRecieved(page);                                                 //y la mandamos a pageRecieved para su almacenamiento temporal.
                }
            }
        }
};
}