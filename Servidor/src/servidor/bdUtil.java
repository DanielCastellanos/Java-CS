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
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.TransactionException;

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

        } catch (HibernateException he) {
            System.err.println("******Error al construir sesion********");
            he.printStackTrace();
            try {
                if (sesionBD.getTransaction().isActive()) {
                    sesionBD.getTransaction().rollback();
                }
            } catch (TransactionException te) {
                System.err.println("**********No se pudo hacer rollback " + te.toString());
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

    public Collection<Pagina> getPages(ArrayList<String> webReport) throws HibernateException {

        boolean nullSesion = sesionBD == null;
        if (nullSesion) {
            hibernate.HibernateUtil.openSessionAndBindToThread();
            sesionBD = hibernate.HibernateUtil.getSessionFactory().getCurrentSession();
        }

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
        if (nullSesion) {
            hibernate.HibernateUtil.closeSessionAndUnbindFromThread();
            sesionBD = null;
        }

        Collection c = pages;                                       //Se guarda la lista en un Collection
        return c;                                                   //Y se retorna
    }

    public Collection<Programa> getProgramas(ArrayList<Tarea> taskReport) throws HibernateException {

        boolean nullSesion = sesionBD == null;
        if (nullSesion) {
            hibernate.HibernateUtil.openSessionAndBindToThread();
            sesionBD = hibernate.HibernateUtil.getSessionFactory().getCurrentSession();
        }

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
        if (nullSesion) {
            hibernate.HibernateUtil.closeSessionAndUnbindFromThread();
            sesionBD = null;
        }
        Collection c = programas;                                       //Se guarda la lista en un Collection
        return c;
    }

    //Obtiene un usuario de la bd según un código
    private Usuario getUsrByName(String usr) throws HibernateException {

        Usuario objetoUsuario;                                                      //Declara un nuevo Usuario
        sesionBD.beginTransaction();                                                //Inicia transaccion en la sesion de hibernate
        Query query = sesionBD.createQuery("from Usuario where codigo = " + usr);      //Declaro una consulta HQL 
        objetoUsuario = (Usuario) query.uniqueResult();

        sesionBD.getTransaction().commit();

        return objetoUsuario;
    }

    public Pc getPcByMac(String mac) throws HibernateException {
        hibernate.HibernateUtil.openSessionAndBindToThread();
        sesionBD = hibernate.HibernateUtil.getSessionFactory().getCurrentSession();
        sesionBD.beginTransaction();

        Query q = sesionBD.createQuery("from Pc where mac = '" + mac + "'");
        Pc returnedPc = (Pc) q.uniqueResult();

        sesionBD.getTransaction().commit();
        hibernate.HibernateUtil.closeSessionAndUnbindFromThread();

        return returnedPc;
    }

    public Pc getPcById(int id) {
        if (!sesionBD.getTransaction().isActive()) {
            sesionBD.beginTransaction();
        }
        Query select = sesionBD.createQuery("from Pc where idPC = " + id);

        Pc pc = (Pc) select.uniqueResult();
        sesionBD.getTransaction().commit();

        return pc;
    }

    public int savePc(Pc pc) {

        try {
            System.out.println("entra a try save");
            hibernate.HibernateUtil.openSessionAndBindToThread();
            sesionBD = hibernate.HibernateUtil.getSessionFactory().getCurrentSession();
            System.out.println("obtiene seción");
            sesionBD.beginTransaction();
            System.out.println("Sesion "+sesionBD.getTransaction().isActive());
            sesionBD.save(pc);
            System.out.println("Pasa por save");
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
        if (pc != null) {
            try {
                pc = getPcByMac(pc.getMac());
            } catch (HibernateException e) {
                System.err.println("error al buscar id de Pc");
                return -1;
            }
            if (pc != null) {
                int id = pc.getIdPC();
                System.out.println("-----> " + id);
                return id;
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }

    private Usuario createUsuario(String cod) throws HibernateException {

        Usuario nuevo = new Usuario(Integer.parseInt(cod), true);        //Crea objeto usuario
        sesionBD.beginTransaction();                        //Inicia transacción en hibernate
        sesionBD.save(nuevo);                               //Guarda el objeto en la BD
        sesionBD.getTransaction().commit();                 //Realiza el commit de los cambios

        return nuevo;
    }

    private void exception() {
        hibernate.HibernateUtil.closeSessionFactory();
        Configuracion conf = BuscarGrupo.conf;
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
            sesionBD.saveOrUpdate(s);
            /*Realiza el commit de los cambios*/
            sesionBD.getTransaction().commit();

        } catch (HibernateException ex) {
            System.err.println("***************************Error al registrar sesión **********************");
            ex.printStackTrace();
//            exception();
        } finally {
            if (hibernate.HibernateUtil.isConnected()) {
                hibernate.HibernateUtil.closeSessionAndUnbindFromThread();
            }
        }
    }

    public void getListOfAdmins() {
        File fileAdmin = new File("data/loginData");
        try {
            RandomAccessFile afAdmin = new RandomAccessFile(fileAdmin, "rw");
            hibernate.HibernateUtil.openSessionAndBindToThread();
            sesionBD = hibernate.HibernateUtil.getSessionFactory().getCurrentSession();

            System.out.println("Getting admins");

            Query q = sesionBD.createQuery("from Admin");
            sesionBD.beginTransaction();
            List<Admin> listAdmin = q.list();
            sesionBD.getTransaction().commit();
            hibernate.HibernateUtil.closeSessionAndUnbindFromThread();

            for (Admin admin : listAdmin) {
                System.out.println(admin.toString());
            }

            byte buffer[];
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(bs);
            os.writeObject(listAdmin);
            buffer = bs.toByteArray();
            afAdmin.write(buffer);

            afAdmin.close();
            bs.close();
            os.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(bdUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(bdUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public Admin logginAdmin(String usr, String pass) {
        System.out.println("Llega a logginAdmin");
        File fileAdmin = new File("data/loginData");
        try {
            if (hibernate.HibernateUtil.isConnected()) {
                System.out.println("Obtener lista de admins...");
                getListOfAdmins();
            }

            RandomAccessFile afAdmin = new RandomAccessFile(fileAdmin, "rw");
            if (afAdmin.length() > 0) {
                System.out.println("Entra a leer archivo");
                byte[] bytes = new byte[(int) afAdmin.length()];                                //Declara arreglo del tamaño del archivo
                System.out.println(afAdmin.length());
                afAdmin.read(bytes);                                           //Guarda el archivo en el arreglo

                //preparamos la entrada de datos del array
                ByteArrayInputStream bs = new ByteArrayInputStream(bytes);
                //preparamos la entrada para obtener el objeto "Sesion"
                ObjectInputStream ois = new ObjectInputStream(bs);
                //obtenemos el objeto "Uso"
                servidor.Servidor.admins = (List<Admin>) ois.readObject();
                //Se cierra el Random Access File
                afAdmin.close();
            } else {
                //JOptionPane.showMessageDialog(null, "No se pudieron comprobar credenciales");
            }

            //Comprobación
            System.out.println("Comprueba... " + servidor.Servidor.admins.size() + " Admins en lista");
            for (Admin admin : servidor.Servidor.admins) {
                System.out.println(admin.getUsrName() + " == " + usr + "?\n"
                        + admin.getPass() + " == " + pass);
                if (usr.equals(admin.getUsrName()) && pass.equals(admin.getPass())) {
                    System.out.println("Encontrado: " + admin.getUsrName());
                    return admin;
                }
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(bdUtil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(bdUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public Admin getAdmin(String username) throws HibernateException {
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
            sesionBD.saveOrUpdate(nuevoUso);
            sesionBD.getTransaction().commit();
            /*-----------------------------*/

        } catch (HibernateException ex) {
            System.err.println("*****************Error al registrar uso \n" + ex.toString());
            ex.printStackTrace();
        } finally {
            hibernate.HibernateUtil.closeSessionAndUnbindFromThread();
        }
    }

    public static void main(String[] args) {
        /*                                              IP      Port DB name     User   Pass*/
        hibernate.HibernateUtil.buildSessionFactory("localhost:3306/javacs_bd", "root", "");

        Scanner sc = new Scanner(System.in);
        System.out.println("Tell me what to try ;)");
        int opc = sc.nextByte();
        while (opc != 0) {

            switch (opc) {

                case 1:

                    System.out.println(Servidor.usoPc.toString());
                    Servidor.guardarUso();
                    break;
                case 2:
                    System.out.println("echale");
                    String usr = sc.next();
                    System.out.println("ahora pass");
                    String pass = sc.next();
                    Admin a = new bdUtil().logginAdmin(usr, pass);
                    if (a != null) {
                        System.out.println("Entra con el admin " + a.getIdAdmin() + " de nombre " + a.getUsrName());
                    } else {
                        System.out.println("Devolvió null");
                    }
                    break;
                case 0:
                    System.out.println("Salir");
                    System.exit(0);
                    break;

            }

            opc = sc.nextByte();
        }
    }
}
