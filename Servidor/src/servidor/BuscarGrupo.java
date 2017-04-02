package servidor;

import interfaz.AppSystemTray;
import interfaz.BDConfig;
import interfaz.Principal;
import interfaz.Tareas;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Ricardo
 */
public class BuscarGrupo extends Principal {
    InetAddress miIp;
    String nombre;
    MulticastSocket puerto;
    Thread escucha,con;
    static boolean libre=false;
    InetAddress ia;
    Timer t=new Timer();
    static ArchivoConf conf = new ArchivoConf();
    static ArrayList<Clientes> cliente=new ArrayList<>();
    DatagramPacket pregunta;
    static Tareas tareas=null;
    int ip=1;
    String p="?,";
    ThreadPoolExecutor pool;

    
    
    public BuscarGrupo()
    {
        try {
            miIp=InetAddress.getLocalHost();
            puerto=new MulticastSocket(1000);
            escucha = new Thread(r);
            con = new Thread(conexion);
        } catch (IOException ex) {
            Logger.getLogger(BuscarGrupo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //obtiene la configuracion
    public ArchivoConf getConf() {
        return conf;
    }

    //guarda cambios en la configuracion del servidor
    public void setConf(ArchivoConf conf) {
        BuscarGrupo.conf = conf;
    }
    //metodo que se llama para iniciar el seridor
    public void iniciarServidor()
    {
        cliente=new Clientes().cargarClientes();
        if(conf.CargarConf())
        {
            try {
                AppSystemTray.mostrarMensaje("Cargando configuración",AppSystemTray.INFORMATION_MESSAGE);
                puerto.joinGroup(InetAddress.getByName(conf.getGrupo()));
                this.inicarHilos();
                confPrincipal=this.getConf();
            } catch (IOException ex) {
                System.out.println("Error");
            }
        }
        else
        {
            AppSystemTray.mostrarMensaje("no se encontro configuración", AppSystemTray.INFORMATION_MESSAGE);
            this.buscarGrupo();
            confPrincipal=this.getConf();
        }
    }
    /*metodo que inicia los hilos de busqueda y escuha
    para buscar un grupo multicast libre*/
    public void inicarHilos()
    {
            escucha.start();
            con.start();
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
                conf.setGrupo(ia.getHostAddress());
                AppSystemTray.mostrarMensaje("Uniendose al grupo 224.0.0."+ip,AppSystemTray.INFORMATION_MESSAGE);
                new BDConfig(conf).setVisible(true);
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
        nombre=JOptionPane.showInputDialog(null, "Escriba un nombre para el equipo");
        conf.setNombreServ(nombre);
    }
    Runnable conexion=new Runnable(){
        @Override
        public void run() {
            try {
                ServerSocket ss=new ServerSocket(4500);
                while(true)
                {
                    Socket s=ss.accept();
                    s.close();
                }
            } catch (Exception e) {
            }
        }
    };
    
    Runnable r=new Runnable() {
            @Override
            public void run() {
                
                ExecutorService executor=Executors.newCachedThreadPool();
                pool=(ThreadPoolExecutor)executor;
                try {
                    byte buf[];
                    DatagramPacket dp;
                    while(true)
                    {
                        buf=new byte[500000];
                        dp=new DatagramPacket(buf,buf.length );
                        puerto.receive(dp);
                        executor.submit(new HiloCliente(dp,ia,puerto));
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        };
    
    
}
