package interfaz;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.ImageIcon;


public class Background extends javax.swing.JPanel {
    String imagen;
    public Background(int w,int h,String foto){    
        this.setSize(w,h);
        imagen = foto;
    }
        
    @Override
    public void paint(Graphics g){
        Dimension tamanio = getSize();
        ImageIcon imagenFondo = new ImageIcon(imagen);        
        g.drawImage(imagenFondo.getImage(),0,0,tamanio.width, tamanio.height, null);        
        setOpaque(false);
        super.paintComponent(g);
    }
}
