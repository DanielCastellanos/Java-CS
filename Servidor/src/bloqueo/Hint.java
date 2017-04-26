package bloqueo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.plaf.basic.BasicPasswordFieldUI;
import javax.swing.plaf.basic.BasicTextFieldUI;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Ricar
 */
public class Hint extends BasicTextFieldUI implements KeyListener {

    private String hint;
    private Color hintColor=Color.gray;
    private boolean visible=true;

    public Hint(String hint)
    {
        this.hint=hint;
    }
    
    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
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
        if(getComponent().getText().length() == 0)
        {
            visible=true;
        }
        else
        {
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
class PassHint extends BasicPasswordFieldUI implements KeyListener
{
    private String hint;
    private Color hintColor=Color.gray;
    private boolean visible=true;

    public PassHint(String hint)
    {
        this.hint=hint;
    }
    
    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    
    @Override
    protected void paintSafely(Graphics g) {
        super.paintSafely(g);
        JTextComponent comp=getComponent();
        if(visible)
        {
            g.setColor(hintColor);
            comp.setBackground(Color.WHITE);
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
            visible=true;
        }
        else
        {
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
