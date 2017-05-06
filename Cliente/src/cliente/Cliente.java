package cliente;

import java.io.File;

/**
 *
 * @author Ricardo
 */
public class Cliente {
    
    public static AppSystemTray notIcon= new AppSystemTray();               //Icono de bandeja del sistema
    public static SesionCliente sesion= null;                               //Manejo de datos de sesión
    public static Monitor monitor;      
    
    public static void main(String[] args) {
        
        BuscarServidor b=new BuscarServidor();
        AppSystemTray.mostrarMensaje("Iniciando cliente", AppSystemTray.INFORMATION_MESSAGE);    
        b.iniciarCliente();
        
    }
}
