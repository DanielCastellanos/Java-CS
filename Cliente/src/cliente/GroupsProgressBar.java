package cliente;

import java.awt.BorderLayout;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JProgressBar;

public class GroupsProgressBar extends JFrame{

    //Componentes
    int ancho=400, alto= 60;
    JProgressBar bar= new JProgressBar(0, 252);
    int progress;
    public static Image logo=new ImageIcon(new ImageIcon("src/images/logo chico.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)).getImage();
    public GroupsProgressBar() {
        
        //Configuraciones de aspecto de la ventana
        this.setIconImage(logo);
        this.setSize(ancho, alto);                 //tamaño
        this.setLocationRelativeTo(null);       //posición en la pantalla (centro)
        this.setAlwaysOnTop(true);              //Siempre al frente
        this.setResizable(false);               //No redimensionable
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        initComp();
    }
    
    private void initComp(){
        
        bar.setString("Buscando Grupos...");
        bar.setStringPainted(true);
        this.add(bar, BorderLayout.CENTER);
        
    }
    
    public void updatebar(){
        this.bar.setValue(++progress);
        if(progress == 252){
            this.dispose();
        }
    }
}
