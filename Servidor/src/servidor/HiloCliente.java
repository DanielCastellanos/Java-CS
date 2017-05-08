package servidor;

import entity.Pc;
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
public class HiloCliente implements Runnable {

    private DatagramPacket dp;  //paquete para envio de mensajes
    private InetAddress miIp, //ip de este despositivo
            multicast; //direccion multicast del grupo
    MulticastSocket puerto;  //puertopara los mensajes multicast

    //Constructor del hilo 
    public HiloCliente(DatagramPacket dp, InetAddress multicast, MulticastSocket puerto) {
        try {
            this.miIp = InetAddress.getLocalHost();
            this.dp = dp;
            this.multicast = multicast;
            this.puerto = puerto;
        } catch (UnknownHostException ex) {
            Logger.getLogger(HiloCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //metodo para contestar cuando otro servidor pregunta si el grupo esta libre
    public void contestar() {
        try {
            byte buf[] = "servidor,".getBytes();//preapra el mensaje 
            DatagramPacket info = new DatagramPacket(buf, buf.length, multicast, 1000); // lo agrega al paquete
            puerto.send(info);//lo envia
        } catch (IOException ex) {
            Logger.getLogger(BuscarGrupo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /*metodo que contesta a usuarios que solicitan informacion 
    parametros ip del usuario que solicito la informacion*/
    public void contestarCliente(InetAddress direccion) {
        try {
            System.out.println(direccion.getHostAddress());
            byte buf[] = ("serv," + conf.getNombreServ() + "," + conf.getGrupo()).getBytes();
            DatagramPacket info = new DatagramPacket(buf, buf.length, direccion, 1001);
            puerto.send(info);
        } catch (IOException ex) {
            System.out.println("asd");
        }
    }

    private void guardarCliente(String mensaje)//**
    {
        StringTokenizer token = new StringTokenizer(mensaje, ",");
        int pos = 0;
        String datos[] = new String[12];
        //separamos los datos recibidos
        while (token.hasMoreTokens()) {
            datos[pos] = token.nextToken();
            pos++;
        }
        // creamos un nuevo objeto cliente
        Pc pc = new Pc();
        pc.setNombre(datos[1]);
        pc.setDireccion(datos[2]);
        pc.setHostname(datos[3]);
        pc.setMarca(datos[4]);
        pc.setModelo(datos[5]);
        pc.setNoSerie(datos[6]);
        pc.setMac(datos[7]);
        pc.setProcesador(datos[8]);
        pc.setDiscoDuro(datos[9]);
        pc.setRam(datos[10]);
        pc.setOs(datos[11]);
        if (!verificarCliente(pc.getMac()))//verificamos que el usuario no este ya registrado
        {
            if(!nombreRepetido(pc.getNombre()))
            {
            /*Buscar pc en la BD*/
            if (hibernate.HibernateUtil.isConnected()) {
                bdUtil bd= new bdUtil();
                Pc aux = bd.getPcByMac(pc.getMac());       //Uso la mac para verificar si existe la máquina en bd
                if (aux == null) {
                    pc.setIdPC(bd.savePc(pc));
                }else{
                    pc=aux;
                }
            }
            System.out.println(pc.getIdPC());
            /**/
            equipos.add(pc);
            Archivos.guardarListaClientes(equipos);
            Principal.agregaEquipo(pc);
            new Ordenes().avisoNombre(pc.getDireccion(),true);
            AppSystemTray.mostrarMensaje("Nuevo Cliente", AppSystemTray.PLAIN_MESSAGE);
            }
            else
            {
                ePendientes.add(pc);
                new Ordenes().avisoNombre(pc.getDireccion(),false);
            }
        } else {
            System.out.println("El usuario ya esta registrado");
        }
    }

    //veficacion del cliente si existe o no
    private boolean verificarCliente(String Mac) {
        boolean existe = false;
        for (Pc clientes : equipos) {
            if (Mac.equalsIgnoreCase(clientes.getMac())) {
                existe = true;
            }
        }
        return (existe);
    }

    //separa las tareas enviadas por el usuario
    private ArrayList<String[]> separarTareas(String tareas) {
        ArrayList<String[]> lista = new ArrayList<>();
        StringTokenizer registro = new StringTokenizer(tareas, "#");
        StringTokenizer datos;
        String info[];
        while (registro.hasMoreTokens()) {
            datos = new StringTokenizer(registro.nextToken(), "|");
            int cont = 0;
            info = new String[4];
            while (datos.hasMoreTokens()) {
                info[cont] = datos.nextToken();
                cont++;
            }
            lista.add(info);
        }
        return lista;
    }
    
    private boolean nombreRepetido(String nombre)
    {
        boolean encontrado=false;
        for (Pc equipo : equipos){
            if(nombre.equals(equipo.getNombre()))
            {
                encontrado=true;
            }
        }
        return encontrado;
    }

    private String nombreCliente(InetAddress ia) {
        String nombre = null;
        for (Pc clientes : equipos) {
            if (ia.getHostName().equalsIgnoreCase(clientes.getHostname())) {
                nombre = clientes.getNombre();
                break;
            }
        }
        return nombre;
    }

    private void nuevoCliente(InetAddress direccion,String host,String nombre)
    {
        System.out.println("Nuevo Cliente:---->"+direccion.toString()+"::::"+host);
        String auxDir=direccion.toString();
        Pc pc=new Pc();
        for (Pc ePendiente : ePendientes) {
                System.out.println(auxDir+"-----"+ePendiente.getDireccion()+"/"+ePendiente.getHostname());
                if(auxDir.equalsIgnoreCase(ePendiente.getHostname()+"/"+ePendiente.getDireccion()))
                {
                    pc=ePendiente;
                    System.out.println(ePendiente.getDireccion());
                    System.out.println(ePendiente.getHostname());
                    System.out.println(ePendiente.getMarca());
                    System.out.println(ePendiente.getNombre());
                    System.out.println(ePendiente.getProcesador());
                    System.out.println(ePendiente.getMac());
                }
            }
        if(pc!=null)
        {
        pc.setNombre(nombre);
        /*Buscar pc en la BD*/
            if (hibernate.HibernateUtil.isConnected()) {
                bdUtil bd= new bdUtil();
                Pc aux = bd.getPcByMac(pc.getMac());       //Uso la mac para verificar si existe la máquina en bd
                if (aux == null) {
                    int id= bd.savePc(pc);
                    if(id != -1){
                        pc.setIdPC(id);
                    }
                }else{
                    pc.setIdPC(aux.getIdPC());
                }
            }
            System.out.println(pc.getIdPC());
            System.out.println(pc.getHostname());
            System.out.println(pc.getNombre());
            /**/
            
            equipos.add(pc);
            Archivos.guardarListaClientes(equipos);
            Principal.agregaEquipo(pc);
            new Ordenes().avisoNombre(direccion.getHostAddress(),true);
            AppSystemTray.mostrarMensaje("Nuevo Cliente", AppSystemTray.PLAIN_MESSAGE);
        }
        else
            System.err.println("..::::No se encontro el usaurio en la lista de quipos pendientes::::..");
    }
    @Override
    public void run() {
        if (!(dp.getAddress().getHostAddress().equals(miIp.getHostAddress())))//evita la respuesta de nuestra misma pc
        {

            String mensaje = new String(dp.getData());
            mensaje = mensaje.trim();
            String aux = mensaje.substring(0, mensaje.indexOf(","));
            InetAddress direccion = dp.getAddress();
            System.out.println(mensaje);
            switch (aux) {
                case "?"://Cuando otro servidor pregunta por si el grupo esta libre
                    contestar();
                    break;
                case "servidor"://resive respuesta del servdor obtiene nombre y direccion
                    BuscarGrupo.libre = false;
                    System.err.println("Grupo ocupado por la direccion ip: " + dp.getAddress().getHostAddress());
                    break;
                case "info"://cuando el cliente solicita informacion del servidor (IP y nombre)
                    contestarCliente(dp.getAddress());
                    break;
                case "cliente"://cuando un nuevo usuario se une al grupo
                    System.out.println("Guardando Cliente");
                    guardarCliente(mensaje);
                    break;
                case "nuevoNombre":
                    String nombre=mensaje.substring(mensaje.indexOf(",")+1, mensaje.length());
                    if(!nombreRepetido(nombre))
                    {
                        nuevoCliente(dp.getAddress(),dp.getAddress().getHostName(),nombre);
                    }
                    else
                    {
                        new Ordenes().avisoNombre(dp.getAddress().getHostAddress(),false);
                    }
                    break;
                case "Tareas"://cuando se resiven las tareas solicitadas al cliente
                    String tarea = mensaje.substring(mensaje.indexOf(",") + 1, mensaje.length());
                    String nomCliente = nombreCliente(direccion);
                    if (BuscarGrupo.tareas == null) {
                        BuscarGrupo.tareas = new Tareas();
                        BuscarGrupo.tareas.agregar(separarTareas(tarea), direccion, nomCliente);
                    } else {
                        tareas.setVisible(true);
                        if (!tareas.revisar(nomCliente)) {
                            BuscarGrupo.tareas.agregar(separarTareas(tarea), dp.getAddress(), nomCliente);
                        }
                    }
                    break;
            }
        } else {
            BuscarGrupo.libre = true;
        }
    }
}
