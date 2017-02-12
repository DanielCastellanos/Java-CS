package servidor;

import interfaz.Principal;
import interfaz.Tareas;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ricardo
 */
public class BuscarGrupo extends Principal {
    InetAddress miIp;
    String nombre;
    MulticastSocket puerto;
    Thread escucha;
    boolean libre=false;
    InetAddress ia;
    Timer t=new Timer();
    static ArchivoConf conf = new ArchivoConf();
    ArrayList<Clientes> cliente=new ArrayList<>();
    DatagramPacket pregunta;
    int ip=1;
    String p="?,";

    
    
    public BuscarGrupo()
    {
        try {
            miIp=InetAddress.getLocalHost();
            puerto=new MulticastSocket(1000);
            escucha = new Thread(r);
        
        } catch (IOException ex) {
            Logger.getLogger(BuscarGrupo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public ArchivoConf getConf() {
        return conf;
    }

    public void setConf(ArchivoConf conf) {
        BuscarGrupo.conf = conf;
    }
    public void iniciarServidor()
    {
        cliente=new Clientes().cargarClientes();
        if(conf.CargarConf())
        {
            try {
                puerto.joinGroup(InetAddress.getByName(conf.getGrupo()));
                System.out.println("estamos en el grupo ------>"+conf.getGrupo() );
                this.inicarHilos();
                confPrincipal=this.getConf();
            } catch (IOException ex) {
                Logger.getLogger(BuscarGrupo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            this.buscarGrupo();
            confPrincipal=this.getConf();
        }
    }
    public void inicarHilos()
    {
            escucha.start();
            System.out.println("Se creo el hilo correctamente");
    }
    public void buscarGrupo()
    {
        nombreServ();
        inicarHilos();
        t.schedule(tarea,0,1000);
    }
    TimerTask tarea=new TimerTask() {
        @Override
        public void run() {
            if(libre)
            {
                t.cancel();
//                escucha.stop(); //comentado para hacer pruebas
//                puerto.close();
                conf.setGrupo(ia.getHostAddress());
                System.out.println("Servidor:"+nombre);
                System.out.println("\033[32m****** grupo Libre -> "+"224.0.0."+ip);
                conf.nuevoArchivo();
            }
            else
            {
                try {
                    if(ip>1)
                    {
                        puerto.leaveGroup(InetAddress.getByName("224.0.0."+ip));
                    }
                    ip++;
                    System.err.println("Preguntando a la direccion-->224.0.0."+ip);
                ia=InetAddress.getByName("224.0.0."+ip);
                puerto.joinGroup(ia);
                byte mensaje[]=p.getBytes();
                pregunta=new DatagramPacket(mensaje, mensaje.length,ia,1000);
                puerto.send(pregunta);
                
                } catch (IOException ex) {
                   ex.printStackTrace();
                }
            }
        }
    };
    public void nombreServ()
    {
        Scanner leer=new Scanner(System.in);
        System.out.println("ingresa el nombre del servidor");
        nombre=leer.next();
        conf.setNombreServ(nombre);
    }
    public void contestar()
    {
        try {
            byte buf[]="servidor,".getBytes();
            DatagramPacket info=new DatagramPacket(buf, buf.length,ia,1000);
            puerto.send(info);
        } catch (IOException ex) {
            Logger.getLogger(BuscarGrupo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public void contestarCliente(InetAddress direccion)
    {
        try {
            System.out.println(direccion.getHostAddress());
            byte buf[]=("serv,"+conf.getNombreServ()+","+conf.getGrupo()).getBytes();
            DatagramPacket info=new DatagramPacket(buf, buf.length,direccion,1001);
            puerto.send(info);
        } catch (IOException ex) {
            Logger.getLogger(BuscarGrupo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    Runnable r=new Runnable() {
            @Override
            public void run() {
                try {
                    byte buf[];
                    byte auxbuf[]=new byte[255];
                    DatagramPacket dp;
                    DatagramPacket auxdp=new DatagramPacket(auxbuf,auxbuf.length);
                    while(true)
                    {
                        buf=auxbuf;
                        dp=new DatagramPacket(buf,buf.length );
                        System.out.println("esperando respuesta");
                        puerto.receive(dp);
                        if(!(dp.getAddress().getHostAddress().equals(miIp.getHostAddress())))
                        {   
                            String mensaje=new String(dp.getData());
                            mensaje=mensaje.trim();
                            String aux=mensaje.substring(0,mensaje.indexOf(","));
                            System.out.println(mensaje);
                            switch(aux)
                            {
                                case "?":
                                    contestar();
                                    break;
                                case "servidor":
                                    libre=false;
                                    System.err.println("****** grupo Ocupado ->"+InetAddress.getByName("224.0.0."+ip));
                                    System.err.println("Grupo ocupado por la direccion ip: "+dp.getAddress().getHostAddress());
                                    break;
                                case "info":
                                    contestarCliente(dp.getAddress());
                                    break;
                                case "cliente":
                                    System.out.println("Nuevo usuario en el servidor :D");
                                    guardarCliente(mensaje);
                                    break;
                                case "Tareas":
                                    System.out.println("entro a Procesos");
                                    String tareas=mensaje.substring(mensaje.indexOf(",")+1,mensaje.length());
                                    new Tareas(separarTareas(tareas),dp.getAddress());
                                    break;
                                default:
                                   
                                    break;
                            } 
                        }
                        else
                        {
                            System.err.println("Entro al else");
                            libre=true; 
                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(BuscarGrupo.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
    private void guardarCliente(String mensaje)
    {
        StringTokenizer token=new StringTokenizer(mensaje,",");
        int pos=0;
        String datos[]=new String[4];
        while(token.hasMoreTokens())
        {
            datos[pos]=token.nextToken();
            pos++;
        }
        Clientes c=new Clientes();
        c.setNombre(datos[1]);
        c.setDireccion(datos[2]);
        c.setHostname(datos[3]);
        if(!verificarCliente(datos[3]))
        {
            cliente.add(c);
            c.crearArchivoLista(cliente);
            agregaEquipo(c);
        }
        else{
            System.out.println("El usuario ya esta registrado");
    }
    }
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
}
