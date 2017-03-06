package cliente;
/**
 *
 * @author Ricardo
 */
public class Cliente {
    public static void main(String[] args) {
        BuscarServidor b=new BuscarServidor();
        System.out.println("Iniciando cliente");
        b.iniciarCliente();
    }
}
