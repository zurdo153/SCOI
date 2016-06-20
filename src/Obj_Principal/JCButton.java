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
    
    private Color CA    =   new Color(77,135,237);
    private Color CB    =   new Color(110,160,239);
    private Color CD    =   new Color(25,92,198);
    
    public JCButton(String Texto, String Imagen, String color){
        super();
        if(color.equals("Azul")){
            CA    =   new Color(77,135,237);
            CB    =   new Color(110,160,239);
            CD    =   new Color(25,92,198);
            setForeground(new Color(255, 255, 255));
            setBackground(CA);  
        }
        
        if(color.equals("AzulC")){
            CA    =   new Color(5,184,255);
            CB    =   new Color(1,213,255);
            CD    =   new Color(1,42,113);
            setForeground(new Color(255, 255, 255));
            setBackground(CA);  
        }
        
        if(color.equals("Rojo")){
            CA    =   new Color(254,4,13);
            CB    =   new Color(247,66,72);
            CD    =   new Color(137,2,7);
            setBackground(CA);  
            setForeground(new Color(255, 255, 255));
        }
        
        if(color.equals("Naranja")){
            CA    =   new Color(253,136,11);
            CB    =   new Color(253,197,134);
            CD    =   new Color(137,2,7);
            setForeground(new Color(255, 255, 255));
            setBackground(CA);  
        }
        
        if(color.equals("Cafe")){
            CA    =   new Color(135 , 65 , 5);
            CB    =   new Color(207 ,113 ,36);
            CD    =   new Color(72  , 34 , 3);
            setForeground(new Color(255, 255, 255));
            setBackground(CA);  
        }
        
              
        setContentAreaFilled(false);
        setOpaque(false);
        setBorderPainted(false);
        setFont(new Font("Tahoma", 1, 12));
        setText(Texto);
        setIcon(new ImageIcon("Imagen/"+Imagen));
    }
    
    @Override
    protected void paintComponent(Graphics g) {
       Graphics2D g2 = (Graphics2D) g;
       g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
       
       GradientPaint gGradientPaint = new GradientPaint(0, 0, CA, 0, getHeight()/2, CB, true);

        if (getModel().isPressed()) {
            gGradientPaint = new GradientPaint(0, 0, CD, 0, getHeight()/2, CA, true);
        } 
        g2.setPaint(gGradientPaint);
        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 10, 10));
        super.paintComponent(g2);
    }
}//JCButton:end