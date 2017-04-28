/*
* 
 */
package servidor;

import bloqueo.FrameBlocked;
import java.awt.Color;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseListener;
import org.jnativehook.mouse.NativeMouseMotionListener;

/**
 *
 * @author Daniel
 */
public class Listeners implements NativeKeyListener, NativeMouseListener, NativeMouseMotionListener{

    static int secs=0;
    private static final Timer temp= new Timer();
    private static int limite = 300;     //Default 7200
    private static int tiempoLogin=100;   //Default 30
    private static aviso av = new aviso();
    static FrameBlocked bloqueo= new FrameBlocked();
    // private Ordenes orden= new Ordenes();
    
    // Aquí se escribe la variable task
    static TimerTask task= new TimerTask() {
        @Override
        public void run() {                 //método run
            Listeners.secs++;               //Se aumenta la variable secs de la clase Listeners
            //System.out.println("****"+secs);
            if(Listeners.secs >= Listeners.limite){                 //si se alcanza el limite
                //orden.apagar();
                System.out.println("apagado");
            }else if(Listeners.secs == Listeners.limite-Listeners.tiempoLogin){         //si falta un tiempo
                if(!bloqueo.isVisible()){
                    bloqueo.login();
                }
                av.dispose();
                System.out.println("login");
            }else if(Listeners.secs == Listeners.limite - Listeners.tiempoLogin - 30){   //antes de eso
                System.out.println("esta entrando");
                av.setVisible(true);
            }
        }
    };
    
    public void beginListeners(){
        
        try{
        cleanLog();
        /*Lo anterior con el objetivo de desabilitar los mensajes del logger
        hacia la consola del programa, ya que estos vienen activados por default en la 
        clase GlobalScreen, con información de las entradas, lo cual en este caso no 
        tendrá utilidad*/

        /*Para registrarl el NativeHook*/
        GlobalScreen.registerNativeHook();
        //comentario
        
        /*Aquí se agrega un key listener para escuchar los eventos del teclado*/
        GlobalScreen.addNativeKeyListener(this);
        
        /*Aquí se agrega un mouse listener para escuchar la actividad del mouse
        y se sobreescriben los m,étodos respectivos*/
        GlobalScreen.addNativeMouseListener(this);
        
        /*Se agrega un listener para el movimiento del mouse*/
        GlobalScreen.addNativeMouseMotionListener(this);
        
        }catch(Exception e){
            System.err.println(e.toString());
        }
        
        temp.schedule(task, 0, 1000);
    }
    
    private static void cleanLog(){
         // Limplio la configuracion del Log
        LogManager.getLogManager().reset();

        // Se obtiene el logger de "org.jnativehook" y se configura el level en off
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);
        
    }

    
    /*
        Adelante se sobre-escriben los métodos abstractos que se implementan con 
        la librería JNativeHook, y que realizan el monitoreo
        de la actividad del teclado y mouse.
    */
    
    //Métodos abstractos para escuchar teclado
    @Override
    public void nativeKeyPressed(NativeKeyEvent nke) {
        Listeners.secs=0;
        if(Listeners.av.isActive())
            Listeners.av.dispose();
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nke) {}

    @Override
    public void nativeKeyTyped(NativeKeyEvent nke) {}


    ///Métodos abstractos para escuchar botones del mouse
    @Override
    public void nativeMouseClicked(NativeMouseEvent nme) {}

    @Override
    public void nativeMousePressed(NativeMouseEvent nme) {
        Listeners.secs=0;
        if(Listeners.av.isActive())
            Listeners.av.dispose();
    }

    @Override
    public void nativeMouseReleased(NativeMouseEvent nme) {}

    
///Métodos abstractos para escuchar movimiento del mouse
    @Override
    public void nativeMouseMoved(NativeMouseEvent nme) {
        Listeners.secs=0;
        if(Listeners.av.isActive())
            Listeners.av.dispose();
    }

    @Override
    public void nativeMouseDragged(NativeMouseEvent nme) {}

}


            /////Esta clase es la ventana de notificación
class aviso extends javax.swing.JFrame{             //se extiende de JFrame
    
    
    JLabel label= new JLabel();                 //Etiqueta de texto
    ImageIcon img = new ImageIcon("images/Java CS.png");
    //Constructor
    public aviso(){                             
        
        getContentPane().setBackground(Color.LIGHT_GRAY);
        this.setAlwaysOnTop(true);
        this.setUndecorated(true);
        this.setVisible(false);             //visible en falso por defecto
        this.setSize(300, 150);             //tamaño de la ventana
        this.setLocationRelativeTo(null);   //posiciona la ventana al centro de la pantalla

        label.setHorizontalAlignment((int) CENTER_ALIGNMENT);       //Alineado al centro
        this.add(label);                                            //se agrega al frame
        label.setText("El equipo se bloqueará pronto");                      //asigna mensaje
        
        this.setIconImage(img.getImage());
    }
    
}
