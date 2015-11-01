package Cat_Auditoria;

import java.awt.Container;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import Obj_Principal.Componentes;

@SuppressWarnings("serial")
public class Cat_Notas extends JDialog{
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JButton btnAgregar = new JButton("Agregar");
	
	JTextArea txaNota 	= new Componentes().textArea(new JTextArea(), "Nota", 500);
	JScrollPane Nota = new JScrollPane(txaNota);
	
	public Cat_Notas(){
		this.setModal(true);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/cajas-de-cajas-de-embalaje-de-envio-de-un-archivo-tar-icono-4009-64.png"));
		this.setTitle("Nota");
		panel.setBorder(BorderFactory.createTitledBorder("Escribir Nota"));	
		
		int x=15,y=20;
		
		panel.add(new JLabel("Nota:")).setBounds(x,y,50,20);
		panel.add(btnAgregar  ).setBounds(x+415,y,85,20);
		panel.add(Nota).setBounds(x,y+25,500,200);
		
		cont.add(panel);
		
		txaNota.setLineWrap(true); 
		txaNota.setWrapStyleWord(true);
		
		this.setSize(545, 300);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
	}
	
	public static void main(String[] args) {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Notas().setVisible(true);
		}catch(Exception e){	}		
	}
}

