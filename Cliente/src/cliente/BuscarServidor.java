package cliente;

import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

public class BuscarServidor {

    MulticastSocket puerto;
    int ip = 2;
    String nombre,
            dir,
            hostname,
            grupo;
    public static String serverHost;
    byte infServ[] = "info,".getBytes();
    InetAddress direccion;
    Thread hilo, nuevoArchivo, con;
    static ArchivoConf configuracion = new ArchivoConf();
    static Ordenes orden = new Ordenes();
    Timer t = new Timer();
    private ArrayList<Servidor_Inf> servidores = new ArrayList<>();
    GroupsProgressBar gi;
    Icon ico= new ImageIcon(AppSystemTray.imagen.getScaledInstance(30, 30, Image.SCALE_DEFAULT));
    
    public static boolean connectionStatus(){//codigo en pruebas
    boolean flag= false;
        try {
            Socket socket=new Socket();
            socket.connect(new InetSocketAddress(serverHost,4500), 500);
            socket.close();
            flag=true;
        } catch (Exception e){
            System.out.println("Error al conectar");
        }
    return flag;
}
    
    public BuscarServidor() {
        try {
            Listeners ls = new Listeners();
            AppSystemTray.mostrarMensaje("Iniciando listeners", 2);
            ls.beginListeners(orden);
            puerto = new MulticastSocket(1001);
            hilo = new Thread(escucha);
            nuevoArchivo = new Thread(archivo);
            con = new Thread(conexion);
        } catch (IOException ex) {
            Logger.getLogger(BuscarServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void iniciarCliente() {
        if (configuracion.cargarConfiguracion()) {
            try {
                puerto.joinGroup(InetAddress.getByName(configuracion.getGrupo()));
                serverHost=configuracion.getServerHost();
                /*Verificar si hay datos de sesión y enviarlos al admin si así es*/
                this.iniciarHilo();
                enviarSesiones();
                orden.login();
            } catch (IOException ex) {
                Logger.getLogger(BuscarServidor.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            AppSystemTray.mostrarMensaje("No se encontró configuración previa", 2);
            this.buscarServidor();
        }
    }

    public void iniciarHilo() {
        hilo.start();
        nuevoArchivo.start();
        con.start();
    }

    public void buscarServidor() {
        gi = new GroupsProgressBar();
        datos();//*****Buscar donde va XD
        gi.setVisible(true);
        t.schedule(tt, 0, 50);
        iniciarHilo();
    }

    private void datos() {
        try {
            nombre = JOptionPane.showInputDialog(null, "Ingresa el nombre de Usuario", "Inicio", JOptionPane.INFORMATION_MESSAGE);
            dir = InetAddress.getLocalHost().getHostAddress();
            hostname = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException ex) {
            Logger.getLogger(BuscarServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    TimerTask tt = new TimerTask() {
        @Override
        public void run() {
            try {

                if (ip < 254) {
                    gi.updatebar();
                    direccion = InetAddress.getByName("224.0.0." + ip);
                    puerto.joinGroup(direccion);
                    DatagramPacket dp = new DatagramPacket(infServ, infServ.length, direccion, 1000);
                    puerto.send(dp);
                    ip++;
                    puerto.leaveGroup(direccion);
                } else {
                    System.out.println("busqueda finalizada");
                    mostrarServidores();
                    ///////////////////////// Usuario selecciona grupo
                    if (!servidores.isEmpty()) {                            //Si la lista de servidores encontrados no está vacía
                        JComboBox cb = new JComboBox();                              //Crea ComboBox para mostrar la lista de grupos
                        cb.addItem("Grupos...");
                        for (Servidor_Inf serv : servidores) {                    //itera en los elementos encontrados
                            cb.addItem("Equipo Admin " + serv.getNombre() + ", " + "en grupo " + serv.getGrupo());    //Agregandolos al comboBox
                        }
                        int groupIndex = 0;                   //Declaro la variable q guardara la eleccion del usuario
                        while (groupIndex == 0) {             //mientras q el valor por default (valor inválido) no cambie
                            JOptionPane.showMessageDialog(null, cb, "Elige un grupo para unirte", JOptionPane.QUESTION_MESSAGE, ico);    //Se despliega el dialogo
                            groupIndex = cb.getSelectedIndex();          //Tomo la opción seleccionada
                        }
                        t.cancel();                                 //Cancelamos la ejecución del timer
                        enviarInfo(groupIndex - 1);                   //Invoca método para enviar la información

                    } else {                                //De lo contrario informará q no encontró servidores
                        AppSystemTray.mostrarMensaje("No se encontraron grupos activos", AppSystemTray.ERROR_MESSAGE);
                        int opc;
                        do {                                       //Pregunta si desea buscar de nuevo
                            opc = JOptionPane.showConfirmDialog(null, "¿Desea realizar la búsqueda de nuevo?", "No se enccontró servidor", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, ico);

                        } while (opc == -1);

                        if (opc == 0) {                            //Si la respuesta es si llama al metodo de busqueda
                            gi = new GroupsProgressBar();
                            gi.setVisible(true);
                            ip = 2;
                        } else {                                    //De lo contrario la aplicación se detiene
                            System.exit(0);
                        }
                    }//*/
                    //if(indexGroup != -1){

                    //}else
                    /////////////////////
                }
            } catch (IOException ex) {
                Logger.getLogger(BuscarServidor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };
    
    Runnable escucha = new Runnable() {
        @Override
        public void run() {
            byte entrada[];
            DatagramPacket dp;
            System.out.println("se inicio el hilo");
            while (true) {

                try {
                    entrada = new byte[2097152];
                    dp = new DatagramPacket(entrada, entrada.length);
                    puerto.receive(dp);
                    String mensaje = new String(dp.getData());
                    mensaje = mensaje.trim();
                    String aux = mensaje.substring(0, mensaje.indexOf(","));
                    System.out.println(mensaje + "********");
                    System.out.println(aux);
                    int tiempo;
                    switch (aux) {
                        case "serv":
                            extDatosServ(mensaje,dp.getAddress().getHostName());
                            break;
                        case "apagar":
                            System.out.println("El sistema se apagará");
                            orden.apagar();
                            break;
                        case "archivo":
                            String archivo = mensaje.substring(mensaje.indexOf(",") + 1, mensaje.length());
                            orden.nuevoArchivo(archivo, dp.getData());
                            break;
                        case "apagarAuto":
                            tiempo = obtenerTiempo(mensaje);
                            System.out.println("El sistema se apagará en " + tiempo);
                            orden.apagarAutomatico(tiempo);
                            break;
                        case "reiniciar":
                            System.out.println("El sistema se reiniciará");
                            orden.reiniciar();
                            break;
                        case "login":
                            orden.login();
                            break;
                        case "bloqueo":
                            tiempo = obtenerTiempo(mensaje);
                            orden.bloquear(tiempo);
                            break;
                        case "desbloqueo":
                            orden.desbloquear();
                            break;
                        case "desbloqueoTotal":
                            orden.desbloquearCompleto();
                            break;
                        case "procesos":
                            sendProcesos(dp.getAddress());
                            break;
                        case "propiedades":
                            orden.getInfoPc();
                            break;
                        case "CPagina":
                            String pagina = mensaje.substring(mensaje.indexOf(",") + 1, mensaje.length());
                            abrirPagina(pagina);
                            break;
                        case "cerrar":
                            orden.cerrar(obtenerTiempo(mensaje) + "");
                            break;
                    }
                } catch (IOException ex) {
                    Logger.getLogger(BuscarServidor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    };

    public void abrirPagina(String pagina) {

        try {
            java.awt.Desktop.getDesktop().browse(java.net.URI.create(pagina));
        } catch (Exception e) {

            try {
                pagina = pagina.replaceAll(" ", "+");
                java.awt.Desktop.getDesktop().browse(java.net.URI.create("https://www.google.com.mx/webhp?sourceid=chrome-instant&ion=1&espv=2&ie=UTF-8#q=" + pagina + "&*"));
            } catch (IOException ex) {
                AppSystemTray.mostrarMensaje("Error al abrir pàgina", 1);
            }
        }

    }

    public void sendProcesos(InetAddress ip) {
        ListaTareas t = new ListaTareas();
        t.escribirLista();

        byte[] cadena;
        cadena = ("Tareas," + t.getListaToString()).getBytes();
        try {
            DatagramPacket dp = new DatagramPacket(cadena, cadena.length, ip, 1000);
            puerto.send(dp);
        } catch (UnknownHostException ex) {
            Logger.getLogger(BuscarServidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BuscarServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private int obtenerTiempo(String mensaje) {
        String tiempo = mensaje.substring(mensaje.indexOf(",") + 1, mensaje.length());
        tiempo = tiempo.trim();
        int t = Integer.parseInt(tiempo);
        return t;
    }

    private void extDatosServ(String mensaje,String hostName) {
        int cont = 0;
        Servidor_Inf serv = new Servidor_Inf();
        String datos[] = new String[3];
        StringTokenizer token = new StringTokenizer(mensaje, ",");
        while (token.hasMoreTokens()) {
            datos[cont] = token.nextToken();
            cont++;
        }
        serv.setNombre(datos[1]);
        serv.setGrupo(datos[2]);
        serv.setHostName(hostName);
        servidores.add(serv);
    }

    private void mostrarServidores() {
        for (Servidor_Inf servidor : servidores) {
            System.out.println(servidor.toString() + "--->" + servidores.indexOf(servidor));
        }
    }

    private void enviarInfo(int pos) {
        try {
            Servidor_Inf servidor = servidores.get(pos);
            InetAddress ia = InetAddress.getByName(servidor.getGrupo());
            System.out.println(ia);
            puerto.joinGroup(ia);
            grupo = servidor.getGrupo();
            serverHost=servidor.getHostName();
            configuracion.setNombre(nombre);
            configuracion.setGrupo(grupo);
            configuracion.setServerHost(serverHost);
            configuracion.archivoNuevo();
            /*Verificar si existen datos de sesión guardados de manera local y enviarlos al admin*/
            enviarSesiones();
            System.out.println(orden.getInfoPc());
            byte registro[] = ("cliente," + nombre + "," + dir + "," + hostname+","+orden.getInfoPc()).getBytes();
            DatagramPacket dp = new DatagramPacket(registro, registro.length, ia, 1000);
            puerto.send(dp);
            orden.login();
        } catch (UnknownHostException ex) {
            Logger.getLogger(BuscarServidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BuscarServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    Runnable archivo = new Runnable() {

        @Override
        public void run() {
            try {
                System.out.println("Esperando Archivo");
                ServerSocket ss = new ServerSocket(4400);
                Socket s;
                byte receivedData[];
                BufferedInputStream bis;
                BufferedOutputStream bos;
                int in;
                while (true) {
                    System.out.println("*******Esperando archivo");
                    //Aceptar conexiones
                    s = ss.accept();
                    //Buffer de 1024 bytes
                    receivedData = new byte[1024];
                    bis = new BufferedInputStream(s.getInputStream());
                    DataInputStream dis = new DataInputStream(s.getInputStream());
                    //Recibimos el nombre del fichero
                    String file = dis.readUTF();
                    //Para guardar fichero recibido
                    bos = new BufferedOutputStream(new FileOutputStream(file));
                    while ((in = bis.read(receivedData)) != -1) {
                        bos.write(receivedData, 0, in);
                    }
                    bos.close();
                    dis.close();
                    AppSystemTray.mostrarMensaje("Archivo \""+file+"\"",AppSystemTray.INFORMATION_MESSAGE);
                }
            } catch (Exception e) {
                if(nuevoArchivo.isAlive())
                {
                    System.out.println("El hilo ah muerto");
                }
            }
        }
    };

    Runnable conexion = new Runnable() {

        @Override
        public void run() {
            try {
                ServerSocket ss = new ServerSocket(4401);
                while (true) {
                    Socket socket = ss.accept();
                    socket.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(BuscarServidor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };
    
    public void enviarSesiones(){
        String path= "src";
        File file= new File(path);
        if(file.exists()){
            File[] files= file.listFiles();
            
            for (File file1 : files) {
                if(file1.getName().contains(BuscarServidor.configuracion.getNombre())){
                    try {
                        Monitor.enviarSesion(file1);
                    } catch (IOException ex) {
                        Logger.getLogger(BuscarServidor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            
        }else{
            System.err.println("No hay sesiones");
        }
    }
}
