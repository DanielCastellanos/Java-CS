/*Esta clase se encarga de crear el ícono de la barra de notificaciones*/
package tester;

import java.awt.AWTException;       //Ecepción a capturar en caso de que la construcción del SystemTray falle
import java.awt.Image;
import java.awt.MenuItem;           //Para crear los objetos de cada entrada del menú desplegable
import java.awt.PopupMenu;          //Para crear el menu desplegable del ícono
import java.awt.SystemTray;         //Para aplicación de bandeja del escritorio
import java.awt.TrayIcon;           //Para el ícono de la app
import java.awt.event.ActionEvent;  //Para capturar evento
import java.awt.event.ActionListener;   //Para crear lista de acciones

/**
 *
 * @author Daniel
 */
public class AppSystemTray {

    SystemTray st;                                          //variable de la bandeja de notificaciones
    PopupMenu menu;                                         //variable de popup menu para el icono
    TrayIcon icon;                                          //variable para la canstrucción del icono
    MenuItem m1;                                            //Item del menu
    Image imagen;
    
   public AppSystemTray (Image ico){
       imagen=ico;
        if(SystemTray.isSupported()){           //Si es soportado por el sistema
           
           st= SystemTray.getSystemTray();                  /*Se declara una variable para obtener la instancia de SystemTray (Espacio virtual para
                                                            el ícono dentro de la bandeja de notificaciones*/
           
           try {                      

                                          // Menu----------
                menu = new PopupMenu();                         //Declara variable de menu
                m1= new MenuItem("Salir");                    //Declara variables para items que entraran en el menu.
           
                                                // Items---------
                
                m1.addActionListener(actionExit);
                menu.add(m1);
                                              //Configuración del ícono----------------------------
                icon = new TrayIcon(imagen ,"Monitor",menu);        /*Se instancia el objeto icon con los parámetros 
                                                                    correspondientes, la imagen (objeto Image), la etiqueta a mostrar (String)
                                                                    y el popup menu*/
                icon.setImageAutoSize(true);            //Se activa el autoajuste de tamaño en la imagen del ícono
                st.add(icon);                           //Se agrega el ícono configurado al SystemTray
                
           } catch (AWTException ex) {
               System.out.println("Error "+ex.toString()+"\n");
           }
       }
   }
   
   /////////////////Acciones 
   
       ActionListener actionExit= new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent ae) {
            System.exit(0);
        }
        
    };
}
