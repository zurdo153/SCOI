package Cat_Reportes;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;


import Obj_Principal.Componentes;

@SuppressWarnings("serial")
public class Cat_Reportes_De_Cortes extends JDialog{
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	JTextField txtFolio = new Componentes().text(new JTextField(), "Folio del Corte", 10, "String");
	JButton btngenerar = new JButton("Generar",new ImageIcon("imagen/buscar.png"));
	
	public Cat_Reportes_De_Cortes(){

		panel.setBorder(BorderFactory.createTitledBorder("Reporte De Cortes"));
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/archivo-icono-8809-32.png"));
		this.setTitle("Reportes de Cortes");
		
		panel.add(new JLabel("Folio Corte:")).setBounds(80,20,80,20);
		panel.add(txtFolio).setBounds(60,40,100,20);
		
		panel.add(btngenerar).setBounds(60, 70, 100, 20);

		cont.add(panel);
		btngenerar.addActionListener(opGenerar);
		
		this.setSize(220, 130);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	ActionListener opGenerar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			if(!txtFolio.getText().equals("")){
			 new Cat_Reporte_De_Corte_De_Caja(txtFolio.getText()+"");
			 	}else{
				JOptionPane.showMessageDialog(null,"Debe de Teclear un Folio: ","Aviso!", JOptionPane.WARNING_MESSAGE);
				return;
			}
			 
			 
		}
	};
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Reportes_De_Cortes().setVisible(true);
		}catch(Exception e){	}
	}
}
