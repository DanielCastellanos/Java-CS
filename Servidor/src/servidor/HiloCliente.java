package servidor;

import interfaz.AppSystemTray;
import interfaz.Principal;
import interfaz.Tareas;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import static servidor.BuscarGrupo.*;


/**
 *
 * @author Ricardo
 */
public class HiloCliente implements Runnable{
    private DatagramPacket dp;  //paquete para envio de mensajes
    private InetAddress miIp,   //ip de este despositivo
                        multicast; //direccion multicast del grupo
    MulticastSocket puerto;  //puertopara los mensajes multicast
    
    //Constructor del hilo 
    public HiloCliente(DatagramPacket dp,InetAddress multicast,MulticastSocket puerto)
    {
        try {
            this.miIp=InetAddress.getLocalHost();
            this.dp=dp;
            this.multicast=multicast;
            this.puerto=puerto;
        } catch (UnknownHostException ex) {
            Logger.getLogger(HiloCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //metodo para contestar cuando otro servidor pregunta si el grupo esta libre
    public void contestar()
    {
        try {
            byte buf[]="servidor,".getBytes();//preapra el mensaje 
            DatagramPacket info=new DatagramPacket(buf, buf.length,multicast,1000); // lo agrega al paquete
            puerto.send(info);//lo envia
        } catch (IOException ex) {
            Logger.getLogger(BuscarGrupo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    /*metodo que contesta a usuarios que solicitan informacion 
    parametros ip del usuario que solicito la informacion*/
    public void contestarCliente(InetAddress direccion)
    {
        try {
            System.out.println(direccion.getHostAddress());
            byte buf[]=("serv,"+conf.getNombreServ()+","+conf.getGrupo()).getBytes();
            DatagramPacket info=new DatagramPacket(buf, buf.length,direccion,1001);
            puerto.send(info);
        } catch (IOException ex) {
            System.out.println("asd");
        }
    }
    private void guardarCliente(String mensaje)//**
    {
        StringTokenizer token=new StringTokenizer(mensaje,",");
        int pos=0;
        String datos[]=new String[4];
        //separamos los datos resibidos
        while(token.hasMoreTokens())
        {
            datos[pos]=token.nextToken();
            pos++;
        }
        // creamos un nuevo objeto cliente
        Clientes c=new Clientes();
        c.setNombre(datos[1]);
        c.setDireccion(datos[2]);
        c.setHostname(datos[3]);
        if(!verificarCliente(datos[3]))//verificamos que el usuario no este ya registrado
        {
            cliente.add(c);
            c.crearArchivoLista(cliente);
            Principal.agregaEquipo(c);
            AppSystemTray.mostrarMensaje("Nuevo Cliente",AppSystemTray.PLAIN_MESSAGE);
        }
        else{
            System.out.println("El usuario ya esta registrado");
    }
    }
    
    //veficacion del cliente si existe o no
    private boolean verificarCliente(String host)
    {
        boolean existe=false;
        for (Clientes clientes : cliente) {
            if(clientes.getHostname().equals(host))
            {
                existe=true;
            }
        }
        return (existe);
    }
    //separa las tareas enviadas por el usuario
    private ArrayList<String []> separarTareas(String tareas)
    {
        ArrayList<String []> lista=new ArrayList<>();
       StringTokenizer registro=new StringTokenizer(tareas,"#");
       StringTokenizer datos;
       String info[];
       while(registro.hasMoreTokens())
       {
           datos=new StringTokenizer(registro.nextToken(), "|");
           int cont=0;
           info=new String[4];
           while(datos.hasMoreTokens()){
               info[cont]=datos.nextToken();
               cont++;
           }
           lista.add(info);
       }
       return lista;
    }
    private String nombreCliente(InetAddress ia)
    {
        String nombre=null;
        for (Clientes clientes : cliente) {
            if(ia.getHostName().equalsIgnoreCase(clientes.hostname))
            {
                nombre=clientes.nombre;
                break;
            }
        }
        return nombre;
    }
    @Override
    public void run() {
        if(!(dp.getAddress().getHostAddress().equals(miIp.getHostAddress())))//evita la respuesta de nuestra misma pc
                        {   
            
                            String mensaje=new String(dp.getData());
                            mensaje=mensaje.trim();
                            String aux=mensaje.substring(0,mensaje.indexOf(","));
                            InetAddress direccion=dp.getAddress();
                            System.out.println(mensaje);
                            switch(aux)
                            {
                                case "?"://Cuando otro servidor pregunta por si el grupo esta libre
                                    contestar();
                                    break;
                                case "servidor"://resive respuesta del servdor obtiene nombre y direccion
                                    BuscarGrupo.libre=false;
                                    System.err.println("Grupo ocupado por la direccion ip: "+dp.getAddress().getHostAddress());
                                    break;
                                case "info"://cuando el cliente solicita informacion del servidor (IP y nombre)
                                    contestarCliente(dp.getAddress());
                                    break;
                                case "cliente"://cuando un nuevo usuario se une al grupo
                                    
                                    guardarCliente(mensaje);
                                    break;
                                case "Tareas"://cuando se resiven las tareas solicitadas al cliente
                                    String tarea=mensaje.substring(mensaje.indexOf(",")+1,mensaje.length());
                                    String nomCliente=nombreCliente(direccion);
                                    if(BuscarGrupo.tareas==null)
                                    {
                                        BuscarGrupo.tareas=new Tareas();
                                        BuscarGrupo.tareas.agregar(separarTareas(tarea),direccion,nomCliente);
                                    }
                                    else
                                    {
                                        tareas.setVisible(true);
                                        if(!tareas.revisar(nomCliente))
                                        {
                                        BuscarGrupo.tareas.agregar(separarTareas(tarea),dp.getAddress(),nomCliente);
                                        }
                                    }
                                    break;
                            } 
                        }
                        else
                        {
                            BuscarGrupo.libre=true; 
                        }
    }
}
