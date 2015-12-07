package Biblioteca;
import java.awt.Container;
import java.awt.Event;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.util.Arrays;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.border.Border;
import Obj_Planeacion.Obj_Prioridad_Y_Ponderacion;
import Obj_Principal.Componentes;

@SuppressWarnings("serial")
public class Cat_Ponderacion extends JFrame{
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	ImageIcon defaultIcon = new ImageIcon("imagen/Estrella_Level.png");
    ImageProducer icono = defaultIcon.getImage().getSource();
    
    ////colorido
//    List<ImageIcon> list = Arrays.asList(
//        makeStarImageIcon(icono, .2f, .5f, .5f),
//        makeStarImageIcon(icono,  0f,  1f, .2f),
//        makeStarImageIcon(icono,  1f,  1f, .2f),
//        makeStarImageIcon(icono, .8f, .4f, .2f),
//        makeStarImageIcon(icono,  1f, .1f, .1f));
  ////amarillo  
    ImageIcon amarillo = makeStarImageIcon(icono, 1f, 1f, 0f);
    List<ImageIcon> list = Arrays.asList(amarillo, amarillo, amarillo, amarillo, amarillo);
    
    private static ImageIcon makeStarImageIcon(ImageProducer icono, float r, float g, float b) {
        return new ImageIcon(Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(icono, new SelectedImageFilter(r,g,b )
        		)));
           
    }
	
	JTextArea txa_Resultado_Configuracion = new Componentes().textArea(new JTextArea(), "Observaciones De La Configuracion", 135);
	JScrollPane Concepto = new JScrollPane(txa_Resultado_Configuracion);
	
	JLabel lblLineaPonderacion     = new JLabel("");
	JRadioButton rbpImportante		= new JRadioButton("Importante");
	JRadioButton rbpUrgente		    = new JRadioButton("Urgente");
	JRadioButton rbPreventivo       = new JRadioButton("Preventivo");
	JRadioButton rbNormal           = new JRadioButton("Normal");
	ButtonGroup Ponderacion= new ButtonGroup();
	
	JButton btnAprovar = new JButton("Aplicar",new ImageIcon("imagen/Aplicar.png"));
	JButton btnDeshacer = new JButton("Deshacer",new ImageIcon("imagen/deshacer16.png"));
	Border linea;
	
	int valor_de_seleccionado=0;
	public Cat_Ponderacion(){
		this.setSize(380, 430);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/tiempo-de-botones-icono-4873-64.png"));
		this.setTitle("Prioridad y Ponderacion");
		this.panel.setBorder(BorderFactory.createTitledBorder("Selecciona La Prioridad y Ponderacion"));
		this.linea = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
		this.lblLineaPonderacion.setBorder(BorderFactory.createTitledBorder(linea,"Prioridad"));
		Ponderacion.add(rbpImportante);
		Ponderacion.add(rbpUrgente);
		Ponderacion.add(rbPreventivo);
		Ponderacion.add(rbNormal);
		
		int x=15,y=30,width=80,height=20,i=18;
		
		panel.add(lblLineaPonderacion).setBounds               (x-5 ,y-10 ,width+10, 95);
		panel.add(rbpImportante).setBounds                     (x   ,y+=5 ,width   , height);
		panel.add(rbpUrgente).setBounds                        (x   ,y+=i ,width   , height);
		panel.add(rbPreventivo).setBounds                      (x   ,y+=i ,width   , height);
		panel.add(rbNormal).setBounds                          (x   ,y+=i ,width   , height);

        x=100;y=30;
		panel.add(new JLabel("   Selecciona El Valor Deseado:")).setBounds(x    ,y ,width*3 ,height);
		
		 add( new LevelBar(defaultIcon, list) {
					     @Override protected void repaintIcon(int index) {
				                       for (int i = 0; i < labelList.size(); i++) {
				                    	   System.out.println(i);
				                     	  labelList.get(i).setIcon(i <= index ? iconList.get(index) : defaultIcon);
				                       }
				                     repaint();
			                      }
					     
                    }).setBounds(x+=25, y+=25, 120, 25);
	        
		x=10; y=100;
		panel.add(new JLabel("     Detalle De La Configuracion Seleccionada:")).setBounds(x    ,y+=25 ,width*3 ,height);
		panel.add(Concepto  ).setBounds                                                  (x    ,y+=20 ,355     ,height*11+5);
		panel.add(btnDeshacer).setBounds                                                 (x    ,y+=230 ,100    ,height);
		panel.add(btnAprovar).setBounds                                                  (x+255,y     ,100     ,height);
	        
		rbNormal.setSelected(true);
		
		txa_Resultado_Configuracion.setEditable(false);
		txa_Resultado_Configuracion.setLineWrap(true); 
		txa_Resultado_Configuracion.setWrapStyleWord(true);
		
		btnAprovar.addActionListener(opAprovar);
		btnDeshacer.addActionListener(deshacer);
		
		///guardar con control+A
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A,Event.CTRL_MASK),"guardar");
             getRootPane().getActionMap().put("guardar", new AbstractAction(){
                 public void actionPerformed(ActionEvent e)
                 {                 	    btnAprovar.doClick();           	    }
            });
 	     //deshacer con escape
 	                 getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
 	                   getRootPane().getActionMap().put("escape", new AbstractAction(){
 	                  public void actionPerformed(ActionEvent e)
 	                  {                btnDeshacer.doClick();           	    }
 	              });
 	    cont.add(panel);
	}
	
	
	ActionListener deshacer = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			txa_Resultado_Configuracion.setText("");
			rbNormal.setSelected(true);
		 }
		};
	
	ActionListener opAprovar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			Obj_Prioridad_Y_Ponderacion OpRespuesta= new Obj_Prioridad_Y_Ponderacion();
			OpRespuesta.setImportante(String.valueOf(rbpImportante.isSelected()));
			OpRespuesta.setUrgente(String.valueOf(rbpUrgente.isSelected()));
			OpRespuesta.setPreventivo(String.valueOf(rbPreventivo.isSelected()));
			OpRespuesta.setNormal(String.valueOf(rbNormal.isSelected()));
			System.out.println("prueba"+OpRespuesta.getPonderacion());
//			dispose();
		}
	};
	
	public static void main(String[] args) {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Ponderacion().setVisible(true);
		}catch(Exception e){	}	
	}
}

@SuppressWarnings("serial")
 class LevelBar extends JPanel {
     List<ImageIcon> iconList;
     List<JLabel> labelList = Arrays.asList(
    		 new JLabel(), new JLabel(), new JLabel(), new JLabel(), new JLabel()
      );
     ImageIcon defaultIcon;
     int clicked = -1;
     
    public LevelBar(ImageIcon defaultIcon, List<ImageIcon> list ) {
        this.defaultIcon = defaultIcon;
        this.iconList = list;
        for (JLabel l: labelList) {
            l.setIcon(defaultIcon);
            add(l); }
        agregarMouseClicklistener();
        agregarMouseMovimientoListener();
    }
    
    private int getSelectedIconIndex(Point p) {
        for (int i = 0; i < labelList.size(); i++) {
            Rectangle r = labelList.get(i).getBounds();
            r.grow(1, 1);
            if (r.contains(p)) {
                return i;
            }
        }
        return -1;
    }
    
    protected void repaintIcon(int index) {
        repaint();
    }
    
    private void agregarMouseMovimientoListener() {
        addMouseMotionListener(new java.awt.event.MouseAdapter() {
        	@Override public void mouseMoved(MouseEvent e) {
                repaintIcon(getSelectedIconIndex(e.getPoint()));
            }
            @Override public void mouseDragged(MouseEvent e)  { /* not needed */ }
        });
    }
    
    private void agregarMouseClicklistener() {
        addMouseListener(new java.awt.event.MouseAdapter() {
            	 @Override public void mouseEntered(MouseEvent e) {
            	        repaintIcon(getSelectedIconIndex(e.getPoint()));
            	    }
            	    @Override public void mouseClicked(MouseEvent e) {
            	        clicked = getSelectedIconIndex(e.getPoint());
            	    }
            	    @Override public void mouseExited(MouseEvent e) {
            	        repaintIcon(clicked);
            	    }
            	    @Override public void mousePressed(MouseEvent e)  { /* not needed */ }
            	    @Override public void mouseReleased(MouseEvent e) { /* not needed */ }
        });
    }
    
}


class SelectedImageFilter extends RGBImageFilter {
    private final float rf, gf, bf;
    public SelectedImageFilter(float r, float g, float b) {
        this.rf = Math.min(1f, r);
        this.gf = Math.min(1f, g);
        this.bf = Math.min(1f, b);
        canFilterIndexColorModel = false;
    }
    @Override public int filterRGB(int x, int y, int argb) {
        int r = (int) (((argb >> 16) & 0xff) * rf);
        int g = (int) (((argb >>  8) & 0xff) * gf);
        int b = (int) (((argb)       & 0xff) * bf);
        return (argb & 0xff000000) | (r << 16) | (g << 8) | (b);
    }
}
