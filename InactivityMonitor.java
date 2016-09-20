package tester;

import java.awt.MouseInfo;
import java.awt.Point;

/**
 *
 * @author Daniel
 */
public class InactivityMonitor extends Thread{
    
    int cont;                                       //Contador de tiempo inactivo
    final int TVerif=10;                            //Límite de inactividad
    Point cursorLoc= new Point();                   //Coordenadas del puntero
    
    public InactivityMonitor() {
        cursorLoc = MouseInfo.getPointerInfo().getLocation();       //Obtiene coordenadas del puntero
    }
    
    @Override
    public void run() {                             //Sobreescribe el método heredado de Thread, run()
                cont++;                             //Suma contador
                if(cont==TVerif){                   //Si el contador es igual al límite
                    Point pAux= MouseInfo.getPointerInfo().getLocation();       //Se optienen las coordenadas y se guardan en el auxiliar
                    if(cursorLoc.equals(pAux)){                     //Si las cordenadas obtenidas son iguales a las anteriores
                        System.out.println("inactividad");          //Se avisa de la inactividad (línea a cambiar)---------------------------------------
                        cont=0;                                     //Y se reinicia el contador
                    }
                    else{                                       //De lo contrario
                        cont=0;                                 //Se reinicia el contador
                        cursorLoc=pAux;                         //Las coordenadas obtenidas reemplazan a las anteriores    
                        System.out.println("actividad detectada");          //Avisa de la detección (Linea a cambiar)------------------------------------
                    }
                }
            }
}
