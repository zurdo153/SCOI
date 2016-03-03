package Obj_Principal;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

import javax.swing.ImageIcon;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class JCButton extends JButton{
    
    private Color gBLue         =   new Color(77,135,237);
    private Color gBlueLight    =   new Color(110,160,239);
    private Color gBlueDark     =   new Color(25,92,198);
    
    /** Constructor de clase */
    public JCButton(String Texto, String Imagen){
        super();
        setBackground(gBLue);        
        setContentAreaFilled(false);
        setOpaque(false);
        setBorderPainted(false);
        setFont(new Font("Tahoma", 1, 12));
        setForeground(new Color(255, 255, 255));
        setText(Texto);
        setIcon(new ImageIcon("Imagen/"+Imagen));
    }
    
    @Override
    protected void paintComponent(Graphics g) {
       Graphics2D g2 = (Graphics2D) g;
       g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
       
       GradientPaint gGradientPaint = new GradientPaint(0, 0, gBLue, 0, getHeight()/2, gBlueLight, true);

        if (getModel().isPressed()) {
            gGradientPaint = new GradientPaint(0, 0, gBlueDark, 0, getHeight()/2, gBLue, true);
        } 
        g2.setPaint(gGradientPaint);
        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 10, 10));
        super.paintComponent(g2);
    }
}//JCButton:end