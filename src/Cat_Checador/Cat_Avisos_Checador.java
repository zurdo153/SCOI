package Cat_Checador;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.KeyStroke;

@SuppressWarnings("serial")
public class Cat_Avisos_Checador extends JComponent {
	
		private Image background;
		JLabel lblAviso = new JLabel();
		JLabel JLBMensaje= new JLabel();
		
		public Cat_Avisos_Checador(final JDialog frame,String ruta,String mensaje, String color) {
			frame.setLayout(new BorderLayout( ));
			String fileFoto = System.getProperty("user.dir")+ruta;
			ImageIcon tmpIconAuxFoto = new ImageIcon(fileFoto);
			
			
			System.out.println(mensaje);
			
			frame.setModal(true);
			frame.add(lblAviso).setBounds(0, 0, 500, 400);
			Icon iconoFoto = new ImageIcon(tmpIconAuxFoto.getImage().getScaledInstance(lblAviso.getWidth(), lblAviso.getHeight(), Image.SCALE_DEFAULT));
            lblAviso.setIcon(iconoFoto);
            
            lblAviso.setHorizontalTextPosition(JLabel.CENTER);
            lblAviso.setVerticalTextPosition(JLabel.CENTER);

            frame.add(JLBMensaje).setBounds(100,100,300,400);
			frame.getContentPane( ).add("Center",this);
			frame.pack( );
			updateBackground( );
			
			
			lblAviso.setText("<html> <FONT FACE="+"arial"+" SIZE=6 COLOR="+color+"><CENTER><b><p>"+mensaje+"</p></b></CENTER></FONT></html>");
			
			frame.setSize(500,450);
			frame.setLocationRelativeTo(null);
			
		    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "foco");
		    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "foco");
		    getRootPane().getActionMap().put("foco", new AbstractAction(){
																        @Override
																        public void actionPerformed(ActionEvent e)
																        {	frame.dispose();   }
																    });
		}
		
		public void updateBackground( ) {
		try {
		Robot rbt = new Robot( );
		Toolkit tk = Toolkit.getDefaultToolkit( );
		Dimension dim = tk.getScreenSize( );
		background = rbt.createScreenCapture(
		new Rectangle(0,0,(int)dim.getWidth( ),
		(int)dim.getHeight( )));
		} catch (Exception ex) {
		ex.printStackTrace( );
		}
		}
		public void paintComponent(Graphics g) {
		Point pos = this.getLocationOnScreen( );
		Point offset = new Point(-pos.x,-pos.y);
		g.drawImage(background,offset.x,offset.y,null);
		repaint();
		}
		
	}