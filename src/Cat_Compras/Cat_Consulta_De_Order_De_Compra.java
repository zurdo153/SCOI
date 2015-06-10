package Cat_Compras;

import java.awt.Container;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

import Obj_Compras.Obj_Consulta_De_Orden_De_Compra;

@SuppressWarnings("serial")
public class Cat_Consulta_De_Order_De_Compra extends JFrame{
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JTextField txtUsuario = new JTextField();
	
//	se consultan los empleados en la misma funcion del objeto, mandandole el folio del departamento en el que esta dado de alta
	String[] compras = new Obj_Consulta_De_Orden_De_Compra().Combo_Grupo_De_Personal(14);
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbCompras = new JComboBox(compras);
	
	String[] contabilidad = new Obj_Consulta_De_Orden_De_Compra().Combo_Grupo_De_Personal(17);
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbContabilidad = new JComboBox(contabilidad);
	
	String[] auditoria = new Obj_Consulta_De_Orden_De_Compra().Combo_Grupo_De_Personal(31);
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbAuditoria = new JComboBox(auditoria);
	
	public Cat_Consulta_De_Order_De_Compra(){
		this.setTitle("Titulo");
		
		int x=15,y=20,ancho=280;
		panel.add(new JLabel("Usuario: ")).setBounds(x,y,80,20);
		panel.add(txtUsuario).setBounds(x+90,y,ancho,20);
		
		panel.add(new JLabel("Compras: ")).setBounds(x,y+=25,80,20);
		panel.add(cmbCompras).setBounds(x+90,y,ancho,20);
		
		panel.add(new JLabel("Contabilidad: ")).setBounds(x,y+=25,80,20);
		panel.add(cmbContabilidad).setBounds(x+90,y,ancho,20);
		
		panel.add(new JLabel("Auditoria: ")).setBounds(x,y+=25,80,20);
		panel.add(cmbAuditoria).setBounds(x+90,y,ancho,20);
		
		cont.add(panel);
		
		txtUsuario.setEditable(false);
		
		this.setSize(430,250);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
	}
	
	
	public static void main(String[] args) {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Consulta_De_Order_De_Compra().setVisible(true);
		}catch(Exception e){	}
	}
}
