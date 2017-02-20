/*Genera un archivo .reg y lo ejecuta con el propósito de insertar la entrada
en el directorio run, para que se ejecute de inicio en segundo plano, al final,
borra el archivo .reg ya que no se requerirá de nuevo*/
package tester;

import java.io.File;                //Para borrar el archivo
import java.io.IOException;         //Para atrapar errores en el trabajo con archivos en caso de darse
import java.io.RandomAccessFile;    //Para crear el archivo .reg y trabajar con él

/**
 *
 * @author Daniel
 */
public class EntradaRegistro {
    
    final String directorio="[HKEY_LOCAL_MACHINE\\SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Run]";          //Donde se coloca el registro
    final String nombre="Monitoreo-java";                                                                       //Nombre de la entrada
    final String version= "Windows Registry Editor Version 5.00";                                               //Version del .reg
    RandomAccessFile entradaReg;                                                                                //Para crear el archivo
    String direccion="";                                                            //Dirección del programa que se agrega al registro 
    
    public void insertarRegistro(String direccion){                 //Inserta la entrada de registro respectiva
        this.direccion= direccion;                                  //Asigna la dirección
        try{
            /* Se construye el archivo .REG necesario para insertar el registro del programa
            Usando la sintaxis predefinida por Windows*/
            entradaReg= new RandomAccessFile("entrada.reg","rw");
            entradaReg.writeBytes(version+"\r\n\r\n"
                                            +directorio+"\r\n"
                                            +'"'+nombre+'"'
                                            +"="
                                            +'"'+"\\"+'"'
                                            +this.direccion+'"'
                                            +'"');
            
            //ya creado se manda ejecutar con regedit desde cmd
            Process p= Runtime.getRuntime().exec("cmd /c regedit -s entrada.reg");
            p.waitFor();                        //Detiene las instrucciones de la clas hasta q termine el proceso
            
            /*Se cierra la variable de entradaReg que estaba leyendo el archivo creado
            para posteriormente leerse con la clase File y borrarse del almacenamiento*/
            entradaReg.close();
            File f= new File(".","entrada.reg");
            f.delete();
            
            //Excepciones
        }catch(IOException e){
            System.out.println("Error de archivo REG");
        } catch (InterruptedException ex) {
            System.out.println("Error al esperar proceso");
        }
    }
    
}
