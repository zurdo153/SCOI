package Cat_Auditoria;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;

import Conexiones_SQL.BuscarSQL;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;

@SuppressWarnings("serial")
public class Cat_Cancelar_Trabajo_De_Cortes extends JFrame{
	
	JLayeredPane panel = new JLayeredPane();
	Container cont = getContentPane();
	
	JTextField txtFolioTrabajo = new Componentes().text(new JCTextField(), "Folio De Trabajo", 7, "Int");
	JCButton btnCancelarTrabajo = new JCButton("Cancelar", "", "Azul");

	Border blackline;
	
	public Cat_Cancelar_Trabajo_De_Cortes() {
		this.setSize(270,80);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/usuario-grupo-icono-5183-64.png"));
		this.setTitle("Cancelación De Trabajo");	

		int x=15, y=15,  ancho=80;
		panel.add(txtFolioTrabajo).setBounds(x, y, ancho+50, 20);
		panel.add(btnCancelarTrabajo).setBounds(x+ancho+50, y, ancho+20, 20);
		
		btnCancelarTrabajo.addActionListener(opCancelarTrabajo);
		
		cont.add(panel);
	}
	
	ActionListener opCancelarTrabajo = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			
			if(!txtFolioTrabajo.getText().trim().equals("")){
				
				String folioTrabajo = txtFolioTrabajo.getText().trim();
				if(JOptionPane.showConfirmDialog(null, "El Trabajo De Corte Con Folio: "+folioTrabajo+" Sera Cancelado Para Su Correccion, ¿Desea Continuar?") == 0){
					
						String respuesta = new BuscarSQL().cancelar_trabajo_de_corte(Integer.valueOf(txtFolioTrabajo.getText().toString().trim()));
						
						if(respuesta.equals("El Trabajo Se Cancelo Correctamente.")){
							txtFolioTrabajo.setText("");
							JOptionPane.showMessageDialog(null, respuesta,"Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
							return;
						}else{
							JOptionPane.showMessageDialog(null, respuesta,"Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
							return;
						}
						
				 }else{
					 return;
				 }
				
			}else{
				JOptionPane.showMessageDialog(null, "Es Necesario Ingresar Un Folio De Trabajo","Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}
		}
	};

	public static void main(String[] args) {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Cancelar_Trabajo_De_Cortes().setVisible(true);
		}catch(Exception e){	}
	}

}
