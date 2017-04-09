package cliente;

/*Esta clase se encarga de crear el ícono de la barra de notificaciones*/


import java.awt.AWTException;           //Ecepción a capturar en caso de que la construcción del SystemTray falle
import java.awt.Image;
import java.awt.MenuItem;               //Para crear los objetos de cada entrada del menú desplegable
import java.awt.PopupMenu;              //Para crear el menu desplegable del ícono
import java.awt.SystemTray;             //Para aplicación de bandeja del escritorio
import java.awt.TrayIcon;               //Para el ícono de la app
import java.awt.event.ActionEvent;      //Para capturar evento
import java.awt.event.ActionListener;   //Para crear lista de acciones
import javax.swing.ImageIcon;

/**
 *
 * @author Daniel
 */
public class AppSystemTray {

    SystemTray st;                                          //variable de la bandeja de notificaciones
    static TrayIcon icon;                                          //variable para la canstrucción del icono
    static Image imagen = new ImageIcon("src/images/logo.png").getImage();    //Imagen icono a mostrar
    public final static byte 
                PLAIN_MESSAGE=1,
                INFORMATION_MESSAGE=2,
                WARNING_MESSAGE=3,
                ERROR_MESSAGE=4;
   public AppSystemTray (){
       //imagen= ;
        if(SystemTray.isSupported()){           //Si es soportado por el sistema
           
           st= SystemTray.getSystemTray();                  /*Se declara una variable para obtener la instancia de SystemTray (Espacio virtual para
                                                            el ícono dentro de la bandeja de notificaciones*/
           
           try {     
               
                                          // Menu----------
                PopupMenu menu= new PopupMenu();                                         //variable de popup menu para el icono
                
                //Declara variables para items que entraran en el menu.
                MenuItem itemCerrarSesion= new MenuItem("Cerrar Sesión");                    
                itemCerrarSesion.addActionListener(cerrarSesión);
                menu.add(itemCerrarSesion);
                

                
                                              //Configuración del ícono----------------------------
                icon = new TrayIcon(imagen ,"Java CS");        /*Se instancia el objeto icon con los parámetros 
                                                                    correspondientes, la imagen (objeto Image), la etiqueta a mostrar (String)
                                                                    */
                icon.setImageAutoSize(true);            //Se activa el autoajuste de tamaño en la imagen del ícono
                icon.addActionListener(cerrarSesión);
                icon.setPopupMenu(menu);                //Se agrega el menú popup
                st.add(icon);                           //Se agrega el ícono configurado al SystemTray
                
                
           } catch (AWTException ex) {
               System.out.println("Error "+ex.toString()+"\n");
           }
       }else{
            System.out.println("Error: El sistema no soporta iconos de bandeja");
        }
   }
   
   public void addPopup(PopupMenu menu){
       this.icon.setPopupMenu(menu);
   }
   
   public static void mostrarMensaje(String m, int tipo){
       switch(tipo){
           case 1:
                AppSystemTray.icon.displayMessage("JavaCS", m, TrayIcon.MessageType.NONE);
                break;
           case 2:
               AppSystemTray.icon.displayMessage("JavaCS", m, TrayIcon.MessageType.INFO);
               break;
           case 3:
               AppSystemTray.icon.displayMessage("JavaCS", m, TrayIcon.MessageType.WARNING);
               break;
           case 4:
               AppSystemTray.icon.displayMessage("JavaCS", m, TrayIcon.MessageType.ERROR);
               break;
           default:
               System.out.println("no existe la opción");
               break;
       }
   }
   
   public void setImage(Image img){
       this.icon.setImage(img);
   }
   
   /////////////////Acciones 
    
        ActionListener cerrarSesión= new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent ae) {
            Cliente.sesion.cerrarSesion();
            BuscarServidor.orden.login();
        }
            
        };
   
        //Restaurar aplicacion
       ActionListener actionExit= new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent ae) {
            System.exit(0);
        }
    };
       
       
}
