package servidor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicTextFieldUI;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Ricar
 */
public class Hint extends BasicTextFieldUI implements KeyListener{

    String hint;
    String auxHint;
    Color color;
    Color hintColor=Color.gray;
    boolean visible=true;
    public Hint(String hint)
    {
        this.hint=hint;
        this.auxHint=this.hint;
    }
    @Override
    protected void paintSafely(Graphics g) {
        super.paintSafely(g);
        JTextComponent comp=getComponent();
        if(visible)
        {
        if(hint.equals(""))
        {
            g.setColor(color);
        }
        else
        {
            g.setColor(hintColor);
        }
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
        if(getComponent().getText().length() == 0)
        {
            this.hint=this.auxHint;
            visible=true;
        }
        else
        {
            this.hint="";
            visible=false;
        }
        getComponent().repaint();
    }

    @Override
    protected void installListeners() {
        super.installListeners(); //To change body of generated methods, choose Tools | Templates.
        getComponent().addKeyListener(this);
    }
    

   

}
