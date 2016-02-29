package Obj_Principal;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.border.AbstractBorder;

@SuppressWarnings("serial")
public class CLabel extends JLabel{
    
   private AbstractBorder circleBorder  =   new CircleBorder();       
   private int lineBorder               =   1; 
   private Color lineColor              =   new Color(225,225,225);
   private Dimension dimension          =   new Dimension(100,100);
   
    /** Constructor de clase */
     public CLabel()
     {        
        setSize(dimension);
        setPreferredSize(dimension);       
        setText("CLabel");
        setOpaque(true);
        setHorizontalAlignment(CENTER);       
        setVisible(true);       
        setBorder(circleBorder); 
     }
     
    //Color de borde
    public Color getLineColor() {
        return lineColor;
    }

    public void setLineColor(Color color) {
        circleBorder = new CircleBorder(color, lineBorder);
        lineColor = color;
        setBorder(circleBorder); 
    }

    //Grosor de borde
    public int getLineBorder() {
        return lineBorder;        
    }

    public void setLineBorder(int lineBorder) {
        circleBorder = new CircleBorder(lineColor, lineBorder);
        this.lineBorder = lineBorder;        
        setBorder(circleBorder); 
    }
    
}
