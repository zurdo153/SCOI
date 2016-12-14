package Cat_Checador;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JLayeredPane;
import Obj_Principal.JCButton;

@SuppressWarnings("serial")
public class Cat_Menu_Checador extends JDialog  {
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JCButton btnSalidaDeEmbarque = new JCButton("Salida De Embarque", "", "");
	JCButton btnLlegadaDeEmbarque = new JCButton("Llegada De Embarque", "", "");
	
	public Cat_Menu_Checador(){
		this.setModal(true);
		this.setSize(275, 125);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setTitle("Menu");
		
		panel.add(btnSalidaDeEmbarque).setBounds(30,20,210,25) ;
		panel.add(btnLlegadaDeEmbarque).setBounds(30,50,210,25) ;
		
		btnSalidaDeEmbarque.addActionListener(opValidarEncargado);
		btnLlegadaDeEmbarque.addActionListener(opValidarEncargado);
		
		cont.add(panel);
		cont.setBackground(Color.ORANGE);
	}
	
	ActionListener opValidarEncargado = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			if(e.getActionCommand().equals("Salida De Embarque")){
				new Cat_Validar_Encargado_Para_Tranferencia().setVisible(true);
			}
			if(e.getActionCommand().equals("Llegada De Embarque")){
				new Cat_Validar_Llegada_De_Chofer_Con_Tranferencia().setVisible(true);
			}
		}
	};
	
	public static void main(String[] args) {
		new Cat_Menu_Checador().setVisible(true);
	}
}
