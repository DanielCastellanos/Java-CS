package servidor;

import entity.Pc;
import entity.Sesion;

/**
 *
 * @author Ricardo
 */
public class Servidor {

    
    static BuscarGrupo comunicacion = new BuscarGrupo();
    public static entity.Sesion sesion= new Sesion();
    public static entity.Pc pc= new Pc();
    
    public static void main(String[] args) {
        
        comunicacion.iniciarServidor();
        Listeners ls= new Listeners();
        ls.beginListeners();
    }
    
}
