package bloqueo;

import entity.Admin;
import entity.Sesion;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.HibernateException;
import servidor.Monitor;
import servidor.Servidor;
import servidor.BuscarGrupo;

public class Login {

    public boolean login(String cod, String nip) {

        System.out.println("Ejecuta login");
        boolean log = false;
        servidor.bdUtil bdutl = new servidor.bdUtil();

        try {
            Admin admin = bdutl.logginAdmin(cod, nip);
            if (admin != null) {
                log = true;

                //Construir sesion aquí
                Sesion sesion= Servidor.sesion= new Sesion();
                sesion.setAdminidAdmin(admin);
                sesion.setPCidPC(BuscarGrupo.conf.getPcServidor());
                sesion.setEntrada(new Date());
                
                try {
                    servidor.Servidor.monitor = new Monitor(InetAddress.getLocalHost().getHostAddress(), 5000L);
                } catch (UnknownHostException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                }
                /**
                 * *********************
                 */
            }
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
        return log;
    }
}
