package cliente;
/**
 *
 * @author Ricardo
 */
public class Cliente {
    public static AppSystemTray notIcon= new AppSystemTray();
    
    public static void main(String[] args) {
        BuscarServidor b=new BuscarServidor();
        System.out.println("Iniciando cliente");
        AppSystemTray.mostrarMensaje("Iniciando cliente", 1);
        b.iniciarCliente();
    }
}
