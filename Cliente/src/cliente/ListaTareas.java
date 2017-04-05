package cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * @author Daniel
 */
public class ListaTareas {
    //Declaración de variables de clase
    private final String command="cmd /c TASKLIST /V /FI \"USERNAME ne NT AUTHORITY\\SYSTEM\" /FI \"STATUS eq running\" /FO LIST";
    private ArrayList<Tarea> lista;
    private Process p;
    private BufferedReader s;
    private final String[] filtroNombre={"explorer.exe", "conhost.exe",
                            "unsecapp.exe", "csrss.exe", "taskhostex.exe"};
    
    public ArrayList<Tarea> escribirLista(){
        
        try{
            
            lista= new ArrayList<>();                       //Se inicializa la lista de tareas
            p= Runtime.getRuntime().exec(command);          //Y se ejecuta el comando tasklist en consola de windows
            s= new BufferedReader(new InputStreamReader(p.getInputStream()));               //Se captura el resultado en el buffer
            //se= new BufferedReader(new InputStreamReader(p.getErrorStream()));
            
            String aux= s.readLine();                                   //leemos la primera linea
            StringTokenizer tokens;                                     //Se declara un objeto StringTokenizer
            
            while(aux!=null){                   //mientras que la variable aux no sea una cadena vacía
                
                int pos=0;
                
                String[] filas= new String[9];
                
                while(aux.length()>1){             /*Si la cadena auxiliar que contiene la linea leída sea mayor que 1
                                                Esto para omitir las lineas leídas que solo contienen un salto de linea
                                                y que separan cada objeto*/
                
                        tokens=new StringTokenizer(aux,":");        //El objeto tokens parte la cadena aux usando ':' de delimitador
                        tokens.nextToken();                         //obtiene el nombre del dato, pero no lo almacena
                    
                    filas[pos]= tokens.nextToken().trim();          //Obtiene el valor y lo almacena en una posición del arreglo filas
                    
                    while(tokens.hasMoreElements()){                //Mientras el token tenga mas elementos
                        filas[pos]+=":"+tokens.nextToken();
                    }
                    pos++;
                    aux= s.readLine();
                    
                    if(aux==null)
                        break;
                }
                aux= s.readLine();
                
                if(filas!=null){
                Tarea nueva= crearElemento(filas);
                    if(nueva!=null){
                        lista.add(nueva);
                    }
                }
                
                
//                System.out.println(cont2);
            }
        }catch(IOException e){
            System.out.println(e.toString());
        }
       filtrarTareas();
        return lista;
    }
    
    private Tarea crearElemento(String[] datos){        /*Ese método crea los elementos Tarea, asignando los datos correspondientes a dicho objeto*/
        
        Tarea nElemento = new Tarea();
        if(datos[0]!= null && datos.length==9){
            nElemento.setNombreImagen(datos[0]);
            nElemento.setPID(datos[1]);
            nElemento.setUsoMemoria(datos[4]);
            //nElemento.setTiempoCPU(datos[7]);
            nElemento.setTituloVentana(datos[8]);
            
            return nElemento;
        }
        return null;
    }
    
    public void imprimirTareas(){       /*Método para imprimir los procesos en consola, usado solo para pruebas*/
        for(Tarea t: lista){
            t.imprimir();
            System.out.println("");
        }
    }
    
    public String getListaToString(){           /*Devuelve un String organizado con delimitadores que contiene información
                                                de los procesos del sistema*/
        
        char delim='#';
        String l = "";
        for(Tarea t: this.lista){
            l+= delim+t.toString();
        }
        return l;
    }
    
    private void filtrarTareas(){
        
        for(int i=0; i<lista.size(); i++){                  //Itera a travéz de las posiciones de la lista
            
            for (int j = 0; j < filtroNombre.length; j++) {     /*Itera los elementos de la lista ade filtrado, se filtrará por el nombre
                                                                 de imagen del proceso*/
                if(lista.get(i).getNombreImagen().equals(filtroNombre[j])){     /*Si el nombre en el arreglo de filtrado es igual
                                                                                al nombre de imagen del proceso...*/
                    lista.remove(lista.get(i));                 /*remueve el elemento de la lista de procesos que se mostrarán*/
                    i=(i==0)?i:i-1;                             /*Se decrementa el contrador de la lista después de eliminar un elemento para
                                                                mantener la congruencia en el ciclo, pero solo si i no tiene el valor de 0*/
                }else if(lista.get(i).getTituloVentana().equals("N/D")){    /*Si el título de ventana del proceso no existe 
                                                                            muestra la cadena N/D, asi que si el proceso tiene esta cadena 
                                                                            tampoco lo mostraremos*/
                    lista.remove(lista.get(i));                     /*Elimina de el elemento aquí y posteriormente se vuelve a decrementar el
                                                                    contador si no vale 0 actualmente*/
                    i=(i==0)?i:i-1;
                }
            }
        }
    }
    
    public ArrayList getLista(){
        return this.lista;
    }
}
