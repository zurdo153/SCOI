package Obj_Principal;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

import javax.swing.JComponent;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.jdesktop.core.animation.timing.Animator;
import org.jdesktop.core.animation.timing.interpolators.SplineInterpolator;
import org.jdesktop.swing.animation.timing.sources.SwingTimerTimingSource;

import Obj_Efectos.MaterialColor;
import Obj_Efectos.SafePropertySetter;


@SuppressWarnings("serial")
public class JCTextArea extends JTextArea{
    private String placeholder = "";
    private Color phColor= new Color(0,0,0);
    private boolean band = true;
    private final Line line = new Line(this);
    
    public JCTextArea( ) {
        super();
        setVisible(true);
        setMargin( new Insets(3,3,3,6));
        getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void removeUpdate(DocumentEvent e) {
                band = (getText().length()>0) ? false:true ;
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                band = false;
            }

            @Override
            public void changedUpdate(DocumentEvent de) {}

        });
    }
        

    public void setPlaceholder(String placeholder)
    {
        this.placeholder=placeholder;
    }

    public String getPlaceholder()
    {
        return placeholder;
    }

    public Color getPhColor() {
        return phColor;
    }

    public void setPhColor(Color phColor) {
        this.phColor = phColor;
    }    

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //color de placeholder 
        g.setColor( new Color(phColor.getRed(),phColor.getGreen(),phColor.getBlue(),90));
        //dibuja texto
        g.drawString((band)?placeholder:"",   getMargin().left,      20    );
                        
        //dibuja linea
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(MaterialColor.BLUEA_400);
        g2.fillRect((int) ((getWidth()+1 - line.getWidth()) / 2), getHeight()-3 , (int) line.getWidth(), 2);        
      }
    
    
    public String getTextProcesa(String texto) {
		StringTokenizer tokens = new StringTokenizer(texto);
	    texto = "";
	    while(tokens.hasMoreTokens())
	    	texto += " "+tokens.nextToken();

	    return texto.toString().trim().toUpperCase();
	}
    
    @Override
    protected void processFocusEvent(FocusEvent e) {
        super.processFocusEvent(e);
        selectAll();
        line.update();
    }
    
    public static class Line {
        private final SwingTimerTimingSource timer;
        private final JComponent target;
        private Animator animator;
        private final SafePropertySetter.Property<Double> width;

        Line(JComponent target) {
            this.target = target;
            this.timer = new SwingTimerTimingSource();
            timer.init();
            width = SafePropertySetter.animatableProperty(target, 0d);
        }

        void update() {
            if (animator != null) {
                animator.stop();
            }
            animator = new Animator.Builder(timer)
                    .setDuration(200, TimeUnit.MILLISECONDS)
                    .setEndBehavior(Animator.EndBehavior.HOLD)
                    .setInterpolator(new SplineInterpolator(0.4, 0, 0.2, 1))
                    .addTarget(SafePropertySetter.getTarget(width, width.getValue(), target.isFocusOwner() ? (double) target.getWidth() + 1 : 0d))
                    .build();
            animator.start();
        }

        public double getWidth() {
            return width.getValue();
        }
    }

}


