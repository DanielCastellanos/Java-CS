package cliente;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.DatagramPacket;
import java.net.InetAddress;
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
import javax.swing.JOptionPane;


public class BuscarServidor 
{
  MulticastSocket puerto;
  int ip=2;
  String nombre,dir,hostname,grupo;
  byte infServ[]="info,".getBytes();
  InetAddress direccion;
  Thread hilo,nuevoArchivo;
  ArchivoConf configuracion=new ArchivoConf();
  Ordenes orden=new Ordenes();
  Timer t=new Timer();
  private ArrayList<Servidor_Inf> servidores=new ArrayList<>();
  public BuscarServidor()
  {
      try {
          Listeners ls= new Listeners();
          ls.beginListeners(orden);
          puerto=new MulticastSocket(1001);
          hilo=new Thread(escucha);
          nuevoArchivo=new Thread(archivo);
      } catch (IOException ex) {
          Logger.getLogger(BuscarServidor.class.getName()).log(Level.SEVERE, null, ex);
      }
  }
  public void iniciarCliente()
  {
      if(configuracion.cargarConfiguracion())
      {
          try {
              puerto.joinGroup(InetAddress.getByName(configuracion.getGrupo()));
              this.iniciarHilo();
          } catch (IOException ex) {
              Logger.getLogger(BuscarServidor.class.getName()).log(Level.SEVERE, null, ex);
          }
      }
      else
      {
          this.buscarServidor();
      }
  }
  public void iniciarHilo()
  {
          hilo.start();
          nuevoArchivo.start();
  }
  
  public void buscarServidor()
  {
      
      datos();//*****Buscar donde va XD
      t.schedule(tt,0,50);
      iniciarHilo();
      
      
  }
  private void datos()
  {
      try {
          nombre=JOptionPane.showInputDialog(null,"Ingresa el nombre de Usuario", "Inicio", JOptionPane.INFORMATION_MESSAGE);
          dir=InetAddress.getLocalHost().getHostAddress();
          hostname=InetAddress.getLocalHost().getHostName();
      } catch (UnknownHostException ex) {
          Logger.getLogger(BuscarServidor.class.getName()).log(Level.SEVERE, null, ex);
      }
  }
  TimerTask tt=new TimerTask()
  {
      @Override
      public void run(){
          try {
              if(ip<254)
              {
                  System.out.println("asd");
              direccion=InetAddress.getByName("224.0.0."+ip);
              puerto.joinGroup(direccion);
              DatagramPacket dp=new DatagramPacket(infServ, infServ.length,direccion,1000);
              puerto.send(dp);
              ip++;
              puerto.leaveGroup(direccion);
              }
              else
              {
                  t.cancel();
                  System.out.println("busqueda finalizada");
                  mostrarServidores();
                  enviarInfo(0);
              }
          } catch (IOException ex) {
              Logger.getLogger(BuscarServidor.class.getName()).log(Level.SEVERE, null, ex);
          }   
      }
  };
  Runnable escucha=new Runnable() {
      @Override
      public void run() {
          byte entrada[];
          DatagramPacket dp;
          System.out.println("se inicio el hilo");
          while(true)
          {
              
              try {
                  entrada=new byte[2097152];
                  dp=new DatagramPacket(entrada,entrada.length);
                  puerto.receive(dp);
                  String mensaje=new String(dp.getData());
                  mensaje=mensaje.trim();
                  String aux=mensaje.substring(0,mensaje.indexOf(","));
                  System.out.println(mensaje+"********");
                  System.out.println(aux);
                  int tiempo;
                  switch(aux)
                  {
                      case "serv":
                          extDatosServ(mensaje);
                          break;
                      case "apagar":
                          System.out.println("El sistema se apagará");
                          //orden.apagar();
                          break;
                      case "archivo":
                              String archivo=mensaje.substring(mensaje.indexOf(",")+1, mensaje.length());
                              orden.nuevoArchivo(archivo,dp.getData());
                          break;
                      case "apagarAuto":
                          tiempo=obtenerTiempo(mensaje);
                          System.out.println("El sistema se apagará en "+tiempo);
                          //orden.apagarAutomatico(tiempo);
                          break;
                      case "reiniciar":
                          System.out.println("El sistema se reiniciaráO");
                          //orden.reiniciar();
                          break;
                      case "login":
                          orden.login();
                          break;
                      case "bloqueo":
                          tiempo=obtenerTiempo(mensaje);
                          orden.bloquear(tiempo);
                          break;
                      case "desbloqueo":
                          orden.desbloquear();
                          break;
                      case "procesos":
                          sendProcesos(dp.getAddress());
                          break;
                      case "cerrar":
                          
                          orden.cerrar(obtenerTiempo(mensaje)+"");
                          break;
                  }
              } catch (IOException ex) {
                  Logger.getLogger(BuscarServidor.class.getName()).log(Level.SEVERE, null, ex);
              }
          }
      }
  };
  
    public void sendProcesos(InetAddress ip){
        ListaTareas t = new ListaTareas();
        t.escribirLista();

        byte[] cadena;
        cadena = ("Tareas,"+t.getListaToString()).getBytes();
      try {
          DatagramPacket dp = new DatagramPacket(cadena, cadena.length,ip,1000);
          puerto.send(dp);
      } catch (UnknownHostException ex) {
          Logger.getLogger(BuscarServidor.class.getName()).log(Level.SEVERE, null, ex);
      } catch (IOException ex) {
          Logger.getLogger(BuscarServidor.class.getName()).log(Level.SEVERE, null, ex);
      }
    }

  private int obtenerTiempo(String mensaje)
  {
      String tiempo=mensaje.substring(mensaje.indexOf(",")+1,mensaje.length());
      tiempo=tiempo.trim();
      int t=Integer.parseInt(tiempo);
      return t;
  }
  private void extDatosServ(String mensaje)
  {
      int cont=0;
      Servidor_Inf serv=new Servidor_Inf();
      String datos[]=new String[3];
      StringTokenizer token=new StringTokenizer(mensaje,",");
      while(token.hasMoreTokens())
      {
          datos[cont]=token.nextToken();
          cont++;
      }
      serv.setNombre(datos[1]);
      serv.setGrupo(datos[2]);
      servidores.add(serv);
  }
  private void mostrarServidores()
  {
      for (Servidor_Inf servidor:servidores) 
      {
          System.out.println(servidor.toString()+"--->"+servidores.indexOf(servidor));
      }
  }
  private void enviarInfo(int pos)
  {
      try {
          Servidor_Inf servidor=servidores.get(pos);
          InetAddress ia=InetAddress.getByName(servidor.getGrupo());
          System.out.println(ia);
          puerto.joinGroup(ia);
          byte registro[]=("cliente,"+nombre+","+dir+","+hostname).getBytes();
          DatagramPacket dp=new DatagramPacket(registro, registro.length,ia, 1000);
          puerto.send(dp);
          grupo=servidor.getGrupo();
          configuracion.setNombre(nombre);
          configuracion.setGrupo(grupo);
          configuracion.archivoNuevo();
      } catch (UnknownHostException ex) {
          Logger.getLogger(BuscarServidor.class.getName()).log(Level.SEVERE, null, ex);
      } catch (IOException ex) {
          Logger.getLogger(BuscarServidor.class.getName()).log(Level.SEVERE, null, ex);
      }
  }
  Runnable archivo=new Runnable() {

      @Override
      public void run() {
          try {
          System.out.println("Esperando Archivo");
            ServerSocket ss=new ServerSocket(4400);
          while(true)
          {
          
            Socket socket=ss.accept();
            DataInputStream archivo=new DataInputStream(socket.getInputStream());
            String nombre=archivo.readUTF();
            int tamaño=archivo.readInt();
              System.out.println(nombre+"----"+tamaño);
            byte buffer[]=new byte[tamaño];
            archivo.readFully(buffer, 0, buffer.length);
            RandomAccessFile salida=new RandomAccessFile(nombre,"rw");
            salida.write(buffer, 0, buffer.length);
            salida.close();
            socket.close();
              System.out.println("archivo recibido");
          }
        } catch (Exception e) {
        }
      }
  };
}
