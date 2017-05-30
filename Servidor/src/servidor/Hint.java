package servidor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.plaf.basic.BasicTextFieldUI;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Ricar
 */
public class Hint extends BasicTextFieldUI implements KeyListener{

    String hint;    //texto que aparecera como pista
    Color hintColor=Color.gray; //color de la pista/Hint
    boolean visible=true;   //booleando para saber cuando hacer visible la psta
    
    //contructor simple 
    public Hint(String hint)
    {
        this.hint=hint;
    }
    public Hint(String hint,Color hintColor)
    {
        this.hint=hint;
        this.hintColor=hintColor;
    }
    @Override
    protected void paintSafely(Graphics g) {
        super.paintSafely(g);
        JTextComponent comp=getComponent();
        if(visible)
        {
            g.setColor(hintColor);
        int padding=(comp.getHeight()-comp.getFont().getSize())/2;
        g.drawString(hint, 2,comp.getHeight()-padding-1);
        }
        
    }   

    @Override
    public void keyTyped(KeyEvent ke) {
        
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        //verificamos si el campo esta vacio
        if(getComponent().getText().length() == 0)
        {
            //si esta vacio hacemos la pista visible
            visible=true;
        }
        else
        {
            //si no lo esta hacemos invisible la pista
            visible=false;
        }
        //repintamos el componente
        getComponent().repaint();
    }

    @Override
    protected void installListeners() {
        //insatalamos los listeners al campo de texto
        super.installListeners();
        getComponent().addKeyListener(this);
    }
    

   

}
