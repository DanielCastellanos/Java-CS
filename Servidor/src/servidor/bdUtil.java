/*Esta clase continene métodos 
**de utilidad para la creación de objetos 
**para almacenar en la base de datos.
 */
package servidor;

import cliente.SesionCliente;
import cliente.Tarea;
import entity.Admin;
import entity.Pagina;
import entity.Pc;
import entity.Programa;
import entity.Sesion;
import entity.UsoPc;
import entity.Usuario;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

public class bdUtil {

    Session sesionBD;

    /*Este método crea y retorna un objeto Sesion*/
    public Sesion buildSesionObject(SesionCliente sesionCliente) {

        Sesion sesion = new Sesion();
        try {
            /*Inicio de sesión de hibernate*/
            hibernate.HibernateUtil.openSessionAndBindToThread();
            sesionBD = hibernate.HibernateUtil.getSessionFactory().getCurrentSession();
            /*Inicio de sesión de hibernate*/
            
            /*Crear el objeto sesión*/
            sesion.setEntrada(sesionCliente.getEntrada());          //Se le escribe la hora de entrada
            if (sesionCliente.getSalida() != null) {                //Escribe hora de salida si existe
                sesion.setSalida(sesionCliente.getSalida());
            }
            //Se agrega el usuario al objeto sesión
            sesion.setUsuarioidUsuario(getUsrByName(sesionCliente.getUsr()));
            if (sesion.getUsuarioidUsuario() == null) {
                sesion.setUsuarioidUsuario(createUsuario(sesionCliente.getUsr()));
            }
            
            /*Colleciones de páginas y grogramas*/
            sesion.setPaginaCollection(getPages(sesionCliente.getWebHistory()));
            /*Se agrega el collection de Pagina a partir
                                                    el arrayList que contiene los nombres de las páginas en sesionCliente*/
            //Posteriormente se realiza lo mismo con los programas.
            sesion.setProgramaCollection(getProgramas(sesionCliente.getTaskHistory()));
            /*Colleciones de páginas y grogramas*/
            
            /*Agregar referencia de pc en sesion*/            
//            sesion.setPCidPC(pc);
            

        } catch (HibernateException he) {
            System.out.println(he.toString());
        } finally {
            /*De cualquier manera se cierra la sesión*/
            hibernate.HibernateUtil.closeSessionAndUnbindFromThread();
            /**/
        }

        return sesion;
    }

    private Collection<Pagina> getPages(ArrayList<String> webReport) {

        List<Pagina> pages = new ArrayList<>();                                  //Lista para guardar las páginas
        sesionBD.beginTransaction();                                            //Inicia transaccion
        Query query = sesionBD.createQuery("from Pagina where nombrePagina = :name");    //Consulta a realizar con parámetro :name

        for (String nameP : webReport) {                            //Loop a travez del arreglo de String con los nombres de las páginas
            
            query.setParameter("name", nameP);                      //Asigna nombre a buscar en parámetro
            Pagina p = (Pagina) query.uniqueResult();                 //Ejecuta la consulta con el parámetro y obtiene un solo resultado

            if (p == null) {                                          //Si la consulta no arrojó resultado la página no existe en bd
                p = new Pagina();                                    //Creamos una nueva instancia de Pagina para guardar
                p.setNombrePagina(nameP);                           //Y le asignamos el nombre
                sesionBD.save(p);
                p = (Pagina) query.uniqueResult();
            }

            pages.add(p);                                           //Guardamos la página en una lista
        }
        sesionBD.getTransaction().commit();                         //Se realiza el commit

        Collection c = pages;                                       //Se guarda la lista en un Collection
        return c;                                                   //Y se retorna
    }

    private Collection<Programa> getProgramas(ArrayList<Tarea> taskReport){
        List<Programa> programas = new ArrayList<>();                                  //Lista para guardar los programas
        sesionBD.beginTransaction();                                                    //Inicia transaccion
        Query query = sesionBD.createQuery("from Programa where proceso = :name");   //Consulta a realizar con parámetro :name

        for (Tarea task : taskReport) {                            //Loop a travez del arreglo de Tarea con la info de los programas
            System.out.println(task.getNombreImagen());
            query.setParameter("name", task.getNombreImagen());                      //Asigna nombre a buscar en parámetro
            Programa p = (Programa) query.uniqueResult();                 //Ejecuta la consulta con el parámetro y obtiene un solo resultado

            if (p == null) {                                          //Si la consulta no arrojó resultado el programa no existe en bd
                p = new Programa();                                    //Creamos una nueva instancia de Programa para guardar
                p.setNombre(task.getTituloVentana());
                p.setProceso(task.getNombreImagen());
                sesionBD.save(p);
                p = (Programa) query.uniqueResult();
            }

            programas.add(p);                                           //Guardamos el programa en una lista
        }
        sesionBD.getTransaction().commit();                         //Se realiza el commit

        Collection c = programas;                                       //Se guarda la lista en un Collection
        return c;  
    }
    
    //Obtiene un usuario de la bd según un código
    private Usuario getUsrByName(String usr) {

        Usuario objetoUsuario;                                                      //Declara un nuevo Usuario
        sesionBD.beginTransaction();                                                //Inicia transaccion en la sesion de hibernate
        Query query = sesionBD.createQuery("from Usuario where codigo = " + usr);      //Declaro una consulta HQL 
        objetoUsuario = (Usuario) query.uniqueResult();

        sesionBD.getTransaction().commit();

        return objetoUsuario;
    }

    private Usuario createUsuario(String cod) {
        Usuario nuevo = new Usuario(Integer.parseInt(cod), true);        //Crea objeto usuario
        sesionBD.beginTransaction();                        //Inicia transacción en hibernate
        sesionBD.save(nuevo);                               //Guarda el objeto en la BD
        sesionBD.getTransaction().commit();                 //Realiza el commit de los cambios

        return nuevo;
    }

    public void saveSesion(Sesion s){
        try{
            /*Abre y obtiene la sesión*/
            hibernate.HibernateUtil.openSessionAndBindToThread();
            sesionBD= hibernate.HibernateUtil.getSessionFactory().getCurrentSession();
            /**/
            
            /*Inicia la transacción*/
            sesionBD.beginTransaction();
            /*Guarda la sesión*/
            sesionBD.save(s);
            /*Realiza el commit de los cambios*/
            sesionBD.getTransaction().commit();
            
        }catch(HibernateException ex){
            System.err.println(ex.toString());
        }finally{
            hibernate.HibernateUtil.closeSessionAndUnbindFromThread();
        }
    }
    
    public Admin logginAdmin(String usr, String pass){
        
        /*Abre sesión*/
        hibernate.HibernateUtil.closeSessionAndUnbindFromThread();
        sesionBD.getSessionFactory().getCurrentSession();
        /*Construye consulta*/
        Query query= sesionBD.createQuery("from Admin where usrName = "+usr+" and pass = "+pass);
        /*Inicia transacción*/
        sesionBD.beginTransaction();
        /*Ejecuta consulta, resultado único*/
        Admin admin= (Admin)query.uniqueResult();
        /*Realiza commit y cierra sesión*/
        sesionBD.getTransaction().commit();
        hibernate.HibernateUtil.closeSessionAndUnbindFromThread();
        /*Retorna objeto entonctrado, null si no entonctró nada*/
        return admin;
    }
    
    /*Guarda un registro en BD recibiendo una Pc y hora de entrada y salida*/
    public void saveUsoPc(Pc pc, Date entrada, Date salida){
        try{
            
            UsoPc nuevoUso= new UsoPc();
            nuevoUso.setEncendido(entrada);
            nuevoUso.setApagado(salida);
            nuevoUso.setPCidPC(pc);
            
            /*Percistencia con hibernate*/
            hibernate.HibernateUtil.openSessionAndBindToThread();
            sesionBD= hibernate.HibernateUtil.getSessionFactory().getCurrentSession();
            sesionBD.beginTransaction();
            sesionBD.saveOrUpdate(pc);
            sesionBD.save(nuevoUso);
            sesionBD.getTransaction().commit();
            /*-----------------------------*/
            
        }catch(HibernateException ex){
            System.err.println("Error al registrar uso \n"+ex.toString());
        }
    }
    
//    public boolean adminLogin(String usr, String pass){
//        
//    }
    public static void main(String[] args) {

        /*                                              IP      Port DB name     User   Pass*/                                        
        hibernate.HibernateUtil.buildSessionFactory("localhost:3306/javacs_bd", "root", "");

        /**/
        /*Construcción de sesionCliente de prueba*/
        SesionCliente sc = new SesionCliente();
        sc.setEntrada(new Date());
        sc.setSalida(new Date());
        sc.setUsr("210437652");

        ArrayList<Tarea> taskHistory = new ArrayList();
        for (int i = 0; i < 4; i++) {
            Tarea t = new Tarea();
            t.setNombreImagen("nomnre" + i);
            t.setPID(i + "12" + i);
            t.setTituloVentana("titulo " + i);
            t.setUsoMemoria("128736");
            taskHistory.add(t);
        }
        sc.setTaskHistory(taskHistory);
        StringBuffer sb = new StringBuffer();
        sb.append("facebook.com|es.wikipedia.org|hackstore.net|cinepremiere.com.mx");
        sc.addWebHistory(sb);
        /*-------------------------------------------------------------*/
        
        Sesion nueva = new bdUtil().buildSesionObject(sc);
        
        
        /*Fragmento de código de prueba------para la máquina*/
        hibernate.HibernateUtil.openSessionAndBindToThread();
        Session sess = hibernate.HibernateUtil.getSessionFactory().getCurrentSession();
        sess.beginTransaction();
        
        
        Pc maquina = new Pc();
        maquina.setIdPC(8);
        maquina.setModelo("miNueva maquina");
        maquina.setOs("Windows 30000");
        sess.saveOrUpdate(maquina);
        nueva.setPCidPC(maquina);
        sess.getTransaction().commit();
        hibernate.HibernateUtil.closeSessionAndUnbindFromThread();
        /*Fragmento de código de prueba*/
        
        new bdUtil().saveSesion(nueva);
        new bdUtil().saveUsoPc(maquina, new Date(), new Date());
    }
}
