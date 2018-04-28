package Cat_Lista_de_Raya;

import java.awt.Container;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.Componentes;

@SuppressWarnings("serial")
public class Cat_Root extends JFrame {
	
	public Container cont = getContentPane();
	public JLayeredPane panel = new JLayeredPane();
	
	public JToolBar menu_toolbar = new JToolBar();
	
	public JTextField txtFolio = new Componentes().text(new JTextField(), "Folio", 10, "int");
	public JTextField txtNombre_Completo = new Componentes().text(new JTextField(), "Folio", 10, "String");
	
	public String establecimientos[] = new Obj_Establecimiento().Combo_Establecimiento();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public JComboBox cmbEstablecimientos = new JComboBox(establecimientos);
	
	public JButton btn_guardar = new JButton("Guardar",new ImageIcon("Imagen/Guardar.png"));
	public JButton btn_refrescar = new JButton(new ImageIcon("Imagen/refrescar-volver-a-cargar-las-flechas-icono-4094-16.png"));
	
	public Cat_Root(){
		
		this.panel.add(menu_toolbar).setBounds(25,0,150,25);
		
		this.panel.add(txtFolio).setBounds(30,35,69,20);
		this.panel.add(txtNombre_Completo).setBounds(101,35,359,20);
		
		this.menu_toolbar.add(btn_guardar);
		this.menu_toolbar.add(btn_refrescar);
		this.menu_toolbar.setEnabled(false);
		
		this.btn_guardar.setToolTipText("Guardar");
		
	}
	
}
