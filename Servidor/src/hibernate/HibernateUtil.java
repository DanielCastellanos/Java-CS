/*Esta clase sirve para albergar métodos relacionados con la persistencia de datos
con el framework hibernate.
 */
package hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.context.internal.ThreadLocalSessionContext;
import org.hibernate.exception.JDBCConnectionException;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {

    private static SessionFactory sessionFactory;
    
    public static boolean isConnected(){
        return (sessionFactory != null && !sessionFactory.isClosed());
    }
    
    public static synchronized void buildSessionFactory(String url, String usr, String pass) {

        //Verifica que el objeto sessionFactory no haya sido inicializado 
        if (sessionFactory == null) {

            //Se crea un objeto configuración que utilizará el archivo hibernate.cfg.xml
            //para realizar los ajustes de hibernate 
            Configuration config = new Configuration();
            config.configure();
            /*Se agregan algunas propiedades ingresadas por el usuario
            La ruta de la base de datos, el usuario y la contraseña de la misma
            si esta se ha definido.
             */
            config.setProperty("hibernate.connection.url", "jdbc:mysql://"+url+"?autoReconnect=true");
            config.setProperty("hibernate.connection.username", usr);
            if (pass != null) {
                config.setProperty("hibernate.connection.password", pass);
            }
            //Una vez creado el objeto de configuración procedemos a construir nuestro service registry
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
            try{
            sessionFactory = config.buildSessionFactory(serviceRegistry);
            }catch(JDBCConnectionException conn){
                System.out.println("***************Falla en driver, estado de conexion: "
                        +hibernate.HibernateUtil.isConnected());
                
            }
        }
    }

    public static void openSessionAndBindToThread() {
        if(sessionFactory != null){
        Session session = sessionFactory.openSession();
        ThreadLocalSessionContext.bind(session);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void closeSessionAndUnbindFromThread() {
        Session session = ThreadLocalSessionContext.unbind(sessionFactory);
        if (session != null) {
            session.close();
        }
    }

    public static void closeSessionFactory() {
        if ((sessionFactory != null) && (sessionFactory.isClosed() == false)) {
            sessionFactory.close();
        }
    }
}
