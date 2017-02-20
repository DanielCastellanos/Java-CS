package cliente;

import bloqueo.FrameBlocked;

/**
 *
 * @author Ricardo
 */
public class Cliente {
    public static void main(String[] args) {
        ArchivoConf configuracion=new ArchivoConf();
        System.out.println("iniciar listener");
        BuscarServidor b=new BuscarServidor();
        System.out.println("Iniciar cliente");
        b.iniciarCliente();
        
        
    }
    
}
