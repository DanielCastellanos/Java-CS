package servidor;
/**
 *
 * @author Ricardo
 */
public class Servidor {

    
    static BuscarGrupo comunicacion = new BuscarGrupo();

    public static void main(String[] args) {
        comunicacion.iniciarServidor();
    }
    
}
