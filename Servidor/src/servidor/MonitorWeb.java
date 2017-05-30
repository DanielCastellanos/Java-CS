package servidor;

import interfaz.AppSystemTray;
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
    static final String DOMAIN[] = {"com", "org", "net", "info", "biz", "edu", "gob"};   //cadenas de dominios a buscar en paquetes que salen por el puerto 443.
    StringBuilder errorBuffer = new StringBuilder();        //Buffer para registrar errores que puedan ocurrir
    List<PcapIf> allDevs = new ArrayList<>();               //Lista para almacenar las interfaces
    Pcap pcap;                                              //Variable Pcap para capturar los paquetes entrantes
    String ipTarget;                                        //Cadena correspondiente a la ip de la interfaz que se va a escuchar
    ArrayList<String> webPages = new ArrayList<>();                                  //Para guardar temporalmente las páginas

    //Constructor de la clase
    public MonitorWeb(String ip) {
        ipTarget = ip;                       //Recibe una cadena como parámetro que representa la ip de la interfaz a monitorear

    }

    public void initMonitor() {
        if (!findDevices()) {
            System.err.printf("No se pudo obtener lista de interfaces %s", errorBuffer
                    .toString());
            AppSystemTray.mostrarMensaje("Error al obtener interfaces de red", AppSystemTray.ERROR_MESSAGE);
        } else {
            int indexDev = pickDevice(ipTarget);
            if (indexDev == -1) {
                System.out.println("No hay dispositivos viables");
                AppSystemTray.mostrarMensaje("No hay dispositivos viables", AppSystemTray.ERROR_MESSAGE);
            } else {
                openDevice(indexDev);
                new Thread(escucha).start();
            }
        }
    }

    Runnable escucha = new Runnable() {

        @Override
        public void run() {
            pcap.loop(-1, handler, "Paquete capturado");
        }

    };

    /*Éste método encuentra las interfaces de red de las cuales se puede escuchar el tráfico. se retorna false si hay algún error, sino retorna true*/
    private boolean findDevices() {

        int r = Pcap.findAllDevs(this.allDevs, this.errorBuffer);
        return !(r == Pcap.NOT_OK || allDevs.isEmpty());
    }

    /*
        El método pickDevice nos ayuda a buscar la interfaz con la ip que se le pasó al constructor
     */
    private int pickDevice(String ip) {

        for (PcapIf dev : allDevs) {
            /*Se inicia un bucle que recorre las intercafes del arreglo 
                                                            obtiene las direcciones de cada una y verifica si la ip objetivo se encuentara entre ellas.
             */
            String devAddr = dev.getAddresses().toString();
            System.out.println(devAddr + "==" + ip + "?");
            //Si la contiene
            if (devAddr.contains(ip)) {
                return allDevs.indexOf(dev);            //el métodod retorna el indice de dicha interfaz dentro de la lista allDevs.
            }
        }

        return -1;
    }

    public void openDevice(int indexDevice) {
        int snaplen = 64 * 1024;           // Capture all packets, no trucation  
        int flags = Pcap.MODE_PROMISCUOUS; // capture all packets  
        int timeout = 10 * 1000;           // 10 seconds in millis  

        pcap = Pcap.openLive(allDevs.get(indexDevice).getName(), snaplen, flags, timeout, errorBuffer);
    }

    //Trabaja con la estructura del paquete para extraer solamente el dominio del host destino
    public static String getPageInPacket(String payload, String domain) {

        payload = payload.substring(0, payload.indexOf(domain) + domain.length());
        StringTokenizer token = new StringTokenizer(payload, "\n");
        String wPage;
        String temp;
        payload = "";
        while (token.hasMoreTokens()) {
            temp = token.nextToken();
            payload += temp.substring(temp.indexOf("    ") + 4, temp.length());
        }
        wPage = payload.substring(payload.lastIndexOf("..") + 2, payload.length());
        return wPage;
    }

    //Verifica si la página ya está registrada en el arreglo temporal, si no, la registra
    private void pageRecieved(String page) {
        if (page != null) {
            if (webPages.indexOf(page) == -1) {
                webPages.add(page);
                System.out.println(page);
            }
        }
    }

    public ArrayList<String> getReport() {
        return webPages;
    }

    /*De haber conexión se enviará la información al equipo administrador dek grupo
    para su análisis y persistencia*/

    public void stop() {
        pcap.breakloop();
    }
    //Manejador de paquetes recibidos.
    PcapPacketHandler<String> handler = new PcapPacketHandler<String>() {

        @Override
        public void nextPacket(PcapPacket packet, String user) {

            //Se declaran las clases de protocolos TCP y Http
            Tcp tcp = new Tcp();
            Http http = new Http();
            try {
                if (packet.hasHeader(tcp)) {                //Si el paquete recivido tiene cabecera tcp

                    if (tcp.destination() == 443) {
                        /*Si el destino del paquete es el puerto 443 (https)
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
                                String page = getPageInPacket(payload, b);                   //Guardamos la página obtenida por el método getPageInPacket
                                pageRecieved(page);                                         //Y llamamos al método para guardala en el arreglo temporal

                            }
                        }
                    } else if (packet.hasHeader(http)) {                                        //De lo contrario verificamos si el paquete tiene cabecera http
                        String page = http.fieldValue(Http.Request.Host);                    //guardamos la cadema obtenida por http.fieldValue que corresponde al host donde va la petición
//                    System.out.println(page);                                           
                        pageRecieved(page);                                                 //y la mandamos a pageRecieved para su almacenamiento temporal.
                    }
                }
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
    };
}
