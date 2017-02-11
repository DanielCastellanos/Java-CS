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
            lista= new ArrayList<>();
            p= Runtime.getRuntime().exec(command);
            s= new BufferedReader(new InputStreamReader(p.getInputStream()));
            //se= new BufferedReader(new InputStreamReader(p.getErrorStream()));
            
            String aux= s.readLine();
            StringTokenizer tokens;
            
            while(aux!=null){                   //mientras que la variable aux no sea una cadena vacía
                int pos=0;
                
                String[] filas= new String[9];
                
                while(aux.length()>1){             /*Si la cadena auxiliar que contiene la linea leída sea mayor que 1
                                                Esto para omitir las lineas leídas que solo contienen un salto de linea
                                                y que separan cada objeto*/
                
                        tokens=new StringTokenizer(aux,":");        //El objeto tokens parte la cadena aux usando ';' de delimitador
                        tokens.nextToken();                         //obtiene el nombre del dato, pero no lo almacena
//                        String line=tokens.nextToken();             //Y pide el siguiente token, que corresponde al valor del atributo
//                  
                    
                    filas[pos]= tokens.nextToken().trim();
                    
                    while(tokens.hasMoreElements()){
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
    
    private Tarea crearElemento(String[] datos){
        
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
    
    public void imprimirTareas(){
        for(Tarea t: lista){
            t.imprimir();
            System.out.println("");
        }
    }
    
    public String getListaToString(){
        
        char delim='#';
        String l = "";
        for(Tarea t: this.lista){
            l+= delim+t.toString();
        }
        return l;
    }
    
    private void filtrarTareas(){
        for(int i=0; i<lista.size(); i++){
            //System.out.println("revisando elemento "+lista.indexOf(lista.get(i)));
            for (int j = 0; j < filtroNombre.length; j++) {
                
                if(lista.get(i).getNombreImagen().equals(filtroNombre[j])){
                    //System.out.println("----------------encontrado");
                    lista.remove(lista.get(i));
                }
            }
        }
    }
    
    public ArrayList getLista(){
        return this.lista;
    }
}
