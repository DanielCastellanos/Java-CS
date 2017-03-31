/*Esta clase sirve para albergar métodos relacionados con la persistencia de datos
con el framework hibernate.
*/

package hibernate;


import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    
    private static SessionFactory sessionFactory;
    
    public static synchronized void buildSessionFactory(){
        
        //Verifica que el objeto sessionFactory no haya sido inicializado 
        if(sessionFactory == null){
            
            //Se crea un objeto configuración que utilizará el archivo hibernate.cfg.xml
            //para realizar los ajustes de hibernate 
            Configuration config= new Configuration();
            config.configure();         
            //Se agregan algunas propiedades a la configuración actual
            
        }
            
    }
}
