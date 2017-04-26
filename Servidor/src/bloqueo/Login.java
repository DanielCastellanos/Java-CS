package bloqueo;

import org.hibernate.HibernateException;

public class Login {

    public boolean login(String cod, String nip) {
        
        boolean log= false;                         
        servidor.bdUtil bdutl= new servidor.bdUtil();
        
        try {
            if (bdutl.logginAdmin(cod, nip)) {
                log = true; 
            }
            }catch(HibernateException ex){
                ex.printStackTrace();
            }
        return log;
    }
}
