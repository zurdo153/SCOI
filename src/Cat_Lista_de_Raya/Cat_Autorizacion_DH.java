package Cat_Lista_de_Raya;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;


import javax.swing.UIManager;

import Conexiones_SQL.ActualizarSQL;
import Conexiones_SQL.BuscarSQL;

@SuppressWarnings("serial")
public class Cat_Autorizacion_DH extends JFrame{
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	String status_funcion  =	new BuscarSQL().Op_status_consideracion().toString().trim();
	boolean autorizar = false;
	JLabel lblAutorizar = new JLabel(new ImageIcon("imagen/ok-firma-icono-6722-64.png"));
	// DECLARAMOS EL OBJETO RUNTIME PARA EJECUTAR APLICACIONES DE WINDOWS
		Runtime R = Runtime.getRuntime();
	
	public Cat_Autorizacion_DH(){
		this.setSize(320,200);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("Autorización Desarrollo Humano");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Aplicar.png"));
		panel.setBorder(BorderFactory.createTitledBorder("-"));
		panel.add(new JLabel("Bloquear Consideracion Por Nivel Jerarquico")).setBounds(40,30,300,25);
		panel.add(lblAutorizar).setBounds(120,80,65,65);
		
		if(status_funcion.equals("V")){
			autorizar = true;
		}else{
			autorizar = false; };
		
	  	lblAutorizar.setEnabled(autorizar);
		lblAutorizar.addMouseListener(opAutorizar);
		cont.add(panel);

		
	}
	
	MouseListener opAutorizar = new MouseListener() {
		public void mouseReleased(MouseEvent arg0) {
		}
		public void mousePressed(MouseEvent arg0) {
			ActualizarSQL actualizar_status = new ActualizarSQL();
			
			if(autorizar){
				if(JOptionPane.showConfirmDialog(null, "¿Desea Bloquear las Consideraciones Por Nivel Jerarquico") == 0){
 				       actualizar_status.Autorizar_Status_Consideraciones("F");
						autorizar = false;
						lblAutorizar.setEnabled(false);
				}else{
					return;
				}
			}else{
				if(JOptionPane.showConfirmDialog(null, "¿Desea Desbloquear Las Consideraciones Por Nivel Jerarquico") == 0){
					 actualizar_status.Autorizar_Status_Consideraciones("V");
					autorizar = true;
					lblAutorizar.setEnabled(true);
				}else{
					return;
				}
			}
				
		}
		public void mouseExited(MouseEvent arg0) {
		}
		public void mouseEntered(MouseEvent arg0) {
		}
		public void mouseClicked(MouseEvent arg0) {
		}
	};
	
	
	public static void main(String [] arg){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Autorizacion_DH().setVisible(true);
		}catch(Exception e){
			System.err.println("Error :"+ e.getMessage());
		}
	}

}
