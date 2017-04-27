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
import java.util.Scanner;
import org.apache.derby.client.am.SqlException;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.TransactionException;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;

public class bdUtil {
    
    Session sesionBD;

    /*Este método crea y retorna un objeto Sesion*/
    public Sesion buildSesionObject(SesionCliente sesionCliente, Pc pc) {
        
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
            sesion.setPCidPC(pc);
            /*----bloque en construcción*/
            
        } catch (HibernateException he) {
            System.err.println("******Error al construir sesion********");
//            he.printStackTrace();
            try{
                if(sesionBD.getTransaction().isActive())
                sesionBD.getTransaction().rollback();
            }catch(TransactionException te){
                System.err.println("**********No se pudo hacer rollback "+te.toString());
            }
        } catch (NullPointerException npe) {
            System.out.println("***********Error, estado de conexion: " + hibernate.HibernateUtil.isConnected());
            System.err.println(npe.toString());
//            npe.printStackTrace();
        } finally {
            /*De cualquier manera se cierra la sesión*/
            hibernate.HibernateUtil.closeSessionAndUnbindFromThread();
            /**/
        }
        
        return sesion;
    }
    
    private Collection<Pagina> getPages(ArrayList<String> webReport) throws HibernateException {
        
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
    
    private Collection<Programa> getProgramas(ArrayList<Tarea> taskReport) throws HibernateException {
        List<Programa> programas = new ArrayList<>();                                  //Lista para guardar los programas
        sesionBD.beginTransaction();                                                    //Inicia transaccion
        Query query = sesionBD.createQuery("from Programa where proceso = :name and nombre = :frame");   //Consulta a realizar con parámetro :name

        for (Tarea task : taskReport) {                            //Loop a travez del arreglo de Tarea con la info de los programas
            System.out.println(task.getNombreImagen());
            query.setParameter("name", task.getNombreImagen());                      //Asigna nombre a buscar en parámetro
            query.setParameter("frame", task.getTituloVentana());                      //Asigna nombre a buscar en parámetro
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
    private Usuario getUsrByName(String usr) throws HibernateException{
        
        Usuario objetoUsuario;                                                      //Declara un nuevo Usuario
        sesionBD.beginTransaction();                                                //Inicia transaccion en la sesion de hibernate
        Query query = sesionBD.createQuery("from Usuario where codigo = " + usr);      //Declaro una consulta HQL 
        objetoUsuario = (Usuario) query.uniqueResult();
        
        sesionBD.getTransaction().commit();
        
        return objetoUsuario;
    }
    
    public Pc getPcByMac(String mac) throws HibernateException{
        hibernate.HibernateUtil.openSessionAndBindToThread();
        sesionBD = hibernate.HibernateUtil.getSessionFactory().getCurrentSession();
        sesionBD.beginTransaction();
        
        Query q = sesionBD.createQuery("from Pc where mac = '" + mac + "'");
        Pc returnedPc = (Pc) q.uniqueResult();
        
        sesionBD.getTransaction().commit();
        hibernate.HibernateUtil.closeSessionAndUnbindFromThread();
        
        return returnedPc;
    }
    
    public int savePc(Pc pc) {
        
        try {
            hibernate.HibernateUtil.openSessionAndBindToThread();
            sesionBD = hibernate.HibernateUtil.getSessionFactory().getCurrentSession();
            
            sesionBD.beginTransaction();
            
            sesionBD.save(pc);
            sesionBD.getTransaction().commit();
            
        } catch (HibernateException ex) {
            System.err.println("Error al persistir datos de pc" + ex.toString());
            pc = null;
            exception();
        } finally {
            if (hibernate.HibernateUtil.isConnected()) {
                hibernate.HibernateUtil.closeSessionAndUnbindFromThread();
            }            
        }
        pc = getPcByMac(pc.getMac());
        int id = -1;
        id = pc.getIdPC();
        System.out.println("-----> " + id);
        return id;
    }
    
    private Usuario createUsuario(String cod) throws HibernateException{
        
        Usuario nuevo = new Usuario(Integer.parseInt(cod), true);        //Crea objeto usuario
        sesionBD.beginTransaction();                        //Inicia transacción en hibernate
        sesionBD.save(nuevo);                               //Guarda el objeto en la BD
        sesionBD.getTransaction().commit();                 //Realiza el commit de los cambios

        return nuevo;
    }
    
    private void exception() {
        hibernate.HibernateUtil.closeSessionFactory();
        Configuracion conf= BuscarGrupo.conf;
        new interfaz.BDConfig(conf, conf.getURLBD(), conf.getUserBD(), conf.getPassBD()).setVisible(true);
    }
    
    public void saveSesion(Sesion s) {
        try {
            /*Abre y obtiene la sesión*/
            hibernate.HibernateUtil.openSessionAndBindToThread();
            sesionBD = hibernate.HibernateUtil.getSessionFactory().getCurrentSession();
            /**/

 /*Inicia la transacción*/
            sesionBD.beginTransaction();
            /*Guarda la sesión*/
            sesionBD.save(s);
            /*Realiza el commit de los cambios*/
            sesionBD.getTransaction().commit();
            
        } catch (HibernateException ex) {
            System.err.println("Error al registrar sesión ");
//            ex.printStackTrace();
//            exception();
        } finally {
            if (hibernate.HibernateUtil.isConnected()) {
                hibernate.HibernateUtil.closeSessionAndUnbindFromThread();
            }
        }
    }
    
    public boolean logginAdmin(String usr, String pass) {
        
        boolean success = false;
        
        try {
            Admin admin = getAdmin(usr);
            if (admin != null) {
                if (admin.getPass().equals(pass) && admin.getStatus()) {
                    success = true;
                }
            }
        } catch (HibernateException ex) {
            System.err.println("****************Error al obtener datos de Administrador**************\n "
            + ex.toString());
        }
        /*Retorna la bandera*/
        return success;
    }
    
    public Admin getAdmin(String username) throws HibernateException{
        /*Abre sesión*/
        hibernate.HibernateUtil.openSessionAndBindToThread();
        sesionBD = hibernate.HibernateUtil.getSessionFactory().getCurrentSession();
        /*Construye consulta*/
        Query query = sesionBD.createQuery("from Admin where usrName = '" + username + "'");
        /*Inicia transacción*/
        sesionBD.beginTransaction();
        /*Ejecuta consulta, resultado único*/
        Admin admin = (Admin) query.uniqueResult();
        /*Realiza commit y cierra sesión*/
        sesionBD.getTransaction().commit();
        hibernate.HibernateUtil.closeSessionAndUnbindFromThread();
        /*Retorna objeto entonctrado, null si no entonctró nada*/
        return admin;
    }

    /*Guarda un registro en BD recibiendo una Pc y hora de entrada y salida*/
    public void saveUsoPc(Pc pc, Date entrada, Date salida) {
        try {
            
            UsoPc nuevoUso = new UsoPc();
            nuevoUso.setEncendido(entrada);
            nuevoUso.setApagado(salida);
            nuevoUso.setPCidPC(pc);

            /*Percistencia con hibernate*/
            hibernate.HibernateUtil.openSessionAndBindToThread();
            sesionBD = hibernate.HibernateUtil.getSessionFactory().getCurrentSession();
            sesionBD.beginTransaction();
            sesionBD.saveOrUpdate(pc);
            sesionBD.save(nuevoUso);
            sesionBD.getTransaction().commit();
            /*-----------------------------*/
            
        } catch (HibernateException ex) {
            System.err.println("*****************Error al registrar uso \n" + ex.toString());
            
        }finally{
            hibernate.HibernateUtil.closeSessionAndUnbindFromThread();
        }
    }

    /*Pare pruebas*/
    public static void prueba1() {

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
        
        Pc pc = new Pc(1);
        if (hibernate.HibernateUtil.isConnected()) {
            Sesion nueva = new bdUtil().buildSesionObject(sc, pc);

            /*Fragmento de código de prueba------para la máquina*/
//        hibernate.HibernateUtil.openSessionAndBindToThread();
//        Session sess = hibernate.HibernateUtil.getSessionFactory().getCurrentSession();
//        sess.beginTransaction();
//        
//        
//        Pc maquina = new Pc();
//        maquina.setIdPC(1);
//        maquina.setModelo("miNueva maquina");
//        maquina.setOs("Windows 30000");
//        sess.saveOrUpdate(maquina);
//        nueva.setPCidPC(maquina);
//        sess.getTransaction().commit();
//        hibernate.HibernateUtil.closeSessionAndUnbindFromThread();
            /*Fragmento de código de prueba*/
            new bdUtil().saveSesion(nueva);
        }
        System.out.println("Termina");
//        new bdUtil().saveUsoPc(maquina, new Date(), new Date());

    }

    /**/
    
    public static void prueba2() {
        hibernate.HibernateUtil.buildSessionFactory("localhost:3306/javacs_bd", "root", "");
        Pc pc = new Pc();
        pc.setMac("54354");
        pc.setModelo("Aspire E5");
        pc.setOs("Guidos equispe");
        Pc p = new bdUtil().getPcByMac(pc.getMac());
        if (p == null) {
            pc.setIdPC(new bdUtil().savePc(pc));
        } else {
            pc.setIdPC(p.getIdPC());
        }
        
        hibernate.HibernateUtil.openSessionAndBindToThread();
        Session sess = hibernate.HibernateUtil.getSessionFactory().getCurrentSession();
        sess.beginTransaction();

        /*Construcción de sesionCliente de prueba*/
        SesionCliente sc = new SesionCliente();
        sc.setEntrada(new Date());
        sc.setSalida(new Date());
        sc.setUsr("210437652");
        
        ArrayList<Tarea> taskHistory = new ArrayList();
        for (int i = 0; i < 4; i++) {
            Tarea t = new Tarea();
            t.setNombreImagen("nomnre");
            t.setPID("12");
            t.setTituloVentana("titulo ");
            t.setUsoMemoria("128736");
            taskHistory.add(t);
        }
        sc.setTaskHistory(taskHistory);
        StringBuffer sb = new StringBuffer();
        sb.append("facebook.com|es.wikipedia.org|hackstore.net|cinepremiere.com.mx");
        sc.addWebHistory(sb);
        /*-------------------------------------------------------------*/
        
        sess.getTransaction().commit();
        hibernate.HibernateUtil.closeSessionAndUnbindFromThread();
        Sesion s = new bdUtil().buildSesionObject(sc, pc);
        new bdUtil().saveSesion(s);
    }
    
    public static void main(String[] args) {
        /*                                              IP      Port DB name     User   Pass*/        
        hibernate.HibernateUtil.buildSessionFactory("localhost:3306/javacs_bd", "root", "");

//        System.out.println("User");
        Scanner sc= new Scanner(System.in);
//        String usr= sc.next();
//        System.out.println("Pass");
//        String pass= sc.next();
//        if(new bdUtil().logginAdmin(usr, pass)){
//            System.out.println("entra");
//        }else
//        System.out.println("rechazado!");
        for (int i = 0; i < 10; i++) {
            prueba1();
            sc.next();
        }
    }
}
