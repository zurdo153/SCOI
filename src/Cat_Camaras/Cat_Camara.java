package Cat_Camaras;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Image;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.lang.Thread.UncaughtExceptionHandler;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.border.Border;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamDiscoveryEvent;
import com.github.sarxos.webcam.WebcamDiscoveryListener;
import com.github.sarxos.webcam.WebcamEvent;
import com.github.sarxos.webcam.WebcamListener;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamPicker;
import com.github.sarxos.webcam.WebcamResolution;

import Obj_Principal.JCButton;

public class Cat_Camara extends JDialog{

	Container cont = getContentPane();
	public JLayeredPane panelFoto = new JLayeredPane();
	public JCButton btnCapturar = new JCButton("Tomar Foto", "", "Verde");
	
	public JLabel lblVistaPrevia = new JLabel();
	public JLabel lblEtiquetaVistaPrevia = new JLabel("<html>"
														+ "	<body>"
														+ "		<center>"
														+ "			<h1 text='green'>"+ "Vista Previa" + "</h1>"
														+ "		</center>"
														+ "	</body>"
													+ "</html>");
	
	static final long serialVersionUID = 1L;

	public Webcam webcam = null;
	WebcamPanel panelCam = null;
	WebcamPicker picker = null;
	
	//declaracion de Bordes
	Border blackline;
		
//	public WebcamViewerExample(){
	public Cat_Camara(){	
		this.setModal(true);
		this.setTitle("Captura Webcam");
//		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(850, 540);
		this.setLocationRelativeTo(null);
		
//		efectos de bordes
		blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
		lblVistaPrevia.setBorder(blackline);
		
		picker = new WebcamPicker();
		picker.addItemListener(opItemList);

		webcam = picker.getSelectedWebcam();
		if (webcam == null) {
			System.out.println("No Se Encontraron WebCams...");
			System.exit(1);
		}
		panelCam = new WebcamPanel(webcam, false);
		panelCam.setFPSDisplayed(true);
		
		int x=10, y= 10, ancho= 400;
		panelFoto.add(picker).setBounds(x, y, ancho, 30);
		panelFoto.add(btnCapturar).setBounds(x+ancho+50, y, ancho/4+50, 30);
		panelFoto.add(panelCam).setBounds(x, y+=35, ancho+200, ancho+50);
		panelFoto.add(lblVistaPrevia).setBounds(x+(ancho+210), y, 206, 155);
		panelFoto.add(lblEtiquetaVistaPrevia).setBounds(x+(ancho+250), y+155, 206, 20);
		
//		cont.setBackground(new Color(167, 231, 255));
		cont.setBackground(Color.black);
		
		cont.add(panelFoto);
		
		ImageIcon tmpIconDefault = new ImageIcon(System.getProperty("user.dir")+"/Imagen/merma_default.jpg");
        Icon iconoDefault = new ImageIcon(tmpIconDefault.getImage().getScaledInstance(lblVistaPrevia.getWidth(), lblVistaPrevia.getHeight(), Image.SCALE_DEFAULT));
        lblVistaPrevia.setIcon(iconoDefault);

//		btnCapturar.addActionListener(opFoto);
		Webcam.addDiscoveryListener(opPiker);
		addWindowListener(actionWindowList);

		webcam.setViewSize(WebcamResolution.VGA.getSize());
		webcam.addWebcamListener(actionCam);

		panelCam.start();
//		try {
//			IniciarCamara();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
	
//    private void IniciarCamara() throws Exception {
//        SwingUtilities.invokeAndWait(new Runnable() {
//        	public void run() {
//        		Thread t = new Thread() {
//
//        			@Override
//        			public void run() {
//        				panelCam.start();
//        			}
//        		};
//        		t.setName("starter");
//        		t.setDaemon(true);
//        		t.setUncaughtExceptionHandler(opUnchaug);
//        		t.start();
//        	}
//            });
//    }

	public static void main(String[] args) {
//		SwingUtilities.invokeLater(new Cat_Camara());
		new Cat_Camara().setVisible(true);
	}

	WebcamListener actionCam = new WebcamListener() {
		@Override
		public void webcamOpen(WebcamEvent arg0) {
			System.out.println("Abrir WebCam");
		}
		public void webcamImageObtained(WebcamEvent arg0) {	}
		public void webcamDisposed(WebcamEvent arg0) {
			System.out.println("Terminar Aplicacion WebCam");			
		}
		
		public void webcamClosed(WebcamEvent arg0) {
			System.out.println("Cerrar WebCam");			
		}
	};

	WindowListener actionWindowList = new WindowListener() {
		
		public void windowOpened(WindowEvent e) {}
		
		public void windowIconified(WindowEvent e) {
			System.out.println("webcam viewer paused");
			panelCam.pause();
		}
		
		public void windowDeiconified(WindowEvent e) {
			System.out.println("webcam viewer resumed");
			panelCam.resume();
		}
		
		public void windowDeactivated(WindowEvent e) {}
		public void windowClosing(WindowEvent e) {
			System.out.println("Closing");
//			System.exit(1);
			webcam.close();
			dispose();
		}
		public void windowClosed(WindowEvent e) {
			System.out.println("Closed");
			webcam.close();
		}	
		public void windowActivated(WindowEvent e) {}
	};

	UncaughtExceptionHandler opUnchaug = new UncaughtExceptionHandler() {
		public void uncaughtException(Thread t, Throwable e) {
			System.err.println(String.format("Exception in thread %s", t.getName()));
			e.printStackTrace();
		}
	};

	ItemListener opItemList = new ItemListener() {
		@Override
		public void itemStateChanged(ItemEvent e) {
			if (e.getItem() != webcam) {
				if (webcam != null) {
	
					panelCam.stop();
	
					remove(panelCam);
	
					webcam.removeWebcamListener(actionCam);
					webcam.close();
	
					webcam = (Webcam) e.getItem();
					webcam.setViewSize(WebcamResolution.VGA.getSize());
					webcam.addWebcamListener(actionCam);
	
					System.out.println("Seleccionar " + webcam.getName());
	
					panelCam = new WebcamPanel(webcam, false);
					panelCam.setFPSDisplayed(true);
	
					add(panelCam, BorderLayout.CENTER);
					pack();
	
//					Thread t = new Thread() {
//	
//						@Override
//						public void run() {
							panelCam.start();
						}
//					};
//					t.setName("stoper");
//					t.setDaemon(true);
//					t.setUncaughtExceptionHandler(opUnchaug);
//					t.start();
//				}
			}
		}
	};
	
	WebcamDiscoveryListener opPiker = new WebcamDiscoveryListener() {

		public void webcamGone(WebcamDiscoveryEvent arg0) {
			if (picker != null) {
				picker.removeItem(arg0.getWebcam());
			}
		}
		
		@SuppressWarnings("unchecked")
		public void webcamFound(WebcamDiscoveryEvent arg0) {
			if (picker != null) {
				picker.addItem(arg0.getWebcam());
			}
		}
	};

//	ActionListener opFoto = new ActionListener() {
//		public void actionPerformed(ActionEvent e) {
//			if(e.getSource() == btnCapturar){
//				
//				BufferedImage image = webcam.getImage();
//				try {
//					ImageIO.write(image, "JPG", new File("test.jpg"));
//				} catch (Exception ex) {
//					// TODO: handle exception
//				}	
//			}	
//		}
//	};
	
}
