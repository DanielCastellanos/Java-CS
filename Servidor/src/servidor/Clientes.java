package servidor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Clientes {

    String nombre = null, direccion = null, hostname = null;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    @Override
    public String toString() {
        return "Cliente: " + nombre + " Direccion: " + direccion + " Nombre de la pc: " + hostname;
    }
    private boolean revizarHost()
    {
        boolean host=false;
        try {
            if(hostname.equals(InetAddress.getByName(direccion).getHostName())){
                host=true;
            }
        } catch (UnknownHostException ex) {
            Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
        }
        return host;
    }
    public String conexion()
    {
        String con="off";
        try {
            System.out.println(hostname);
            if(InetAddress.getByName(hostname).isReachable(3000))
            {
                con="on";
            }
        } catch (UnknownHostException ex) {
            System.out.println("Usuario no encontrado");
        } catch (IOException ex) {
            System.out.println("Usuario no encontrado");
        }
        return con;
    }
    public void crearArchivoLista(ArrayList<Clientes> clientes) {
        try {
            File lista = new File("ListClientes.txt");
            if (lista.exists()) {
                lista.delete();
            }
            RandomAccessFile escribir = new RandomAccessFile(lista, "rw");
            for (Clientes cliente : clientes) {
                escribir.write(("Nombre=" + cliente.getNombre() + ", Direccion=" + cliente.getDireccion() + ", Nombre-Pc=" + cliente.getHostname()+"#").getBytes());
            }
            escribir.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ArrayList<Clientes> cargarClientes()
    {
        ArrayList<Clientes> lista=new ArrayList<>();
        try {    
            RandomAccessFile clientesLista=new RandomAccessFile("ListClientes.txt", "r");
            String aux=clientesLista.readLine();
            StringTokenizer registro=new StringTokenizer(aux,"#");
            StringTokenizer cliente;
            String datos[]=new String[3];
            Clientes c;
            int pos=0;
            while(registro.hasMoreTokens())
            {
                c=new Clientes();
                String cli=registro.nextToken();
                cliente=new StringTokenizer(cli, ",");
                while(cliente.hasMoreTokens())
                {
                    String auxDato=cliente.nextToken();
                    datos[pos]=auxDato.substring(auxDato.indexOf("=")+1,auxDato.length());
                    pos++;
                }
                pos=0;
                c.setNombre(datos[0]);
                c.setDireccion(datos[1]);
                c.setHostname(datos[2]);
                lista.add(c);
            }
        } catch (FileNotFoundException ex) {
            System.err.println("Archivo no encontrado LINEA 121");
        } catch (IOException ex) {
             System.err.println("IOException LINEA 123");
        }
        return lista;
    }
}