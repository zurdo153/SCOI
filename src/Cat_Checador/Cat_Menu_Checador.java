package Cat_Checador;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLayeredPane;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import Cat_Compras.Cat_Registrar_Zona_Completada;
import Obj_Principal.JCButton;

@SuppressWarnings("serial")
public class Cat_Menu_Checador extends JDialog  {
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JCButton btnRecibirPedido = new JCButton("Recibir Pedido Por Zona", "", "Azul");
	JCButton btnSalidaDeEmbarque = new JCButton("Salida De Embarque", "", "AzulC");
	JCButton btnLlegadaDeEmbarque = new JCButton("Llegada De Embarque", "", "AzulC");
		
	Border thickBorder = new LineBorder(Color.BLACK, 12);
	Border bl = BorderFactory.createLineBorder(Color.BLACK);
		
	public Cat_Menu_Checador(){
		this.setModal(true);
		this.setSize(275, 155);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setTitle("Menu");
		
		panel.add(btnRecibirPedido).setBounds(30,20,210,25) ;
		panel.add(btnSalidaDeEmbarque).setBounds(30,50,210,25) ;
		panel.add(btnLlegadaDeEmbarque).setBounds(30,80,210,25) ;
		
		btnRecibirPedido.addActionListener(opValidarEncargado);
		btnSalidaDeEmbarque.addActionListener(opValidarEncargado);
		btnLlegadaDeEmbarque.addActionListener(opValidarEncargado);

		cont.add(panel);
		cont.setBackground(Color.ORANGE);
	}
	
	ActionListener opValidarEncargado = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			if(e.getActionCommand().equals("Recibir Pedido Por Zona")){
				new Cat_Registrar_Zona_Completada().setVisible(true);
			}
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
