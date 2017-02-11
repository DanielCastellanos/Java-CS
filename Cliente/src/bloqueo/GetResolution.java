package bloqueo;

import java.awt.Dimension;
import java.awt.Toolkit;

public class GetResolution {

    public void tamaño() {
        //Obtiene el tamaño de la pantalla
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        
        FrameBlocked.ancho = d.width ;
        FrameBlocked.alto = d.height;
    }
}
