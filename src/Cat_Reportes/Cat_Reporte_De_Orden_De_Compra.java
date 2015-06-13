package Cat_Reportes;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

import Conexiones_SQL.Generacion_Reportes;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Compras.Obj_Consulta_De_Orden_De_Compra;
import Obj_Principal.Componentes;

@SuppressWarnings("serial")
public class Cat_Reporte_De_Orden_De_Compra extends JFrame{
	
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
	
	JTextField txtFolio = new Componentes().text(new JTextField(), "Folio de la Orden de Compra", 11, "String");
	JButton btngenerar = new JButton("Generar",new ImageIcon("imagen/buscar.png"));
	
	public Cat_Reporte_De_Orden_De_Compra(){
		this.setSize(350,250);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setTitle("Titulo");
		setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/cesta-de-la-compra-verde-icono-9705-64.png"));
		panel.setBorder(BorderFactory.createTitledBorder("Seleccione el Tipo de Reporte"));
		
		Obj_Usuario usuario = new Obj_Usuario().LeerSession();
		txtUsuario.setText(usuario.getNombre_completo());
		cmbAuditoria.setSelectedItem("MARIBEL SALOMON TORRES");
		
		int x=15,y=20,ancho=230;
		
		panel.add(new JLabel("Consulto: ")).setBounds(x,y,80,20);
		panel.add(txtUsuario).setBounds(x+80,y,ancho,20);
		
		panel.add(new JLabel("Compras: ")).setBounds(x,y+=25,80,20);
		panel.add(cmbCompras).setBounds(x+80,y,ancho,20);
		
		panel.add(new JLabel("Contabilidad: ")).setBounds(x,y+=25,80,20);
		panel.add(cmbContabilidad).setBounds(x+80,y,ancho,20);
		
		panel.add(new JLabel("Auditoria: ")).setBounds(x,y+=25,80,20);
		panel.add(cmbAuditoria).setBounds(x+80,y,ancho,20);
		
		panel.add(new JLabel("Folio: ")).setBounds(x,y+=25,80,20);
		panel.add(txtFolio).setBounds(x+80,y,ancho,20);
		panel.add(btngenerar).setBounds(x+150,y+=35,100,20);
		
		cont.add(panel);
		txtUsuario.setEditable(false);
		btngenerar.addActionListener(opGenerar);
		
//      asigna el foco al JTextField del nombre deseado al arrancar la ventana
        this.addWindowListener(new WindowAdapter() {
                public void windowOpened( WindowEvent e ){  	txtFolio.requestFocus(); }
        });
	}
	
	ActionListener opGenerar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String basedatos="2.200";
			String vista_previa_reporte="no";
			int vista_previa_de_ventana=0;
			
			if(!txtFolio.getText().equals("")){
				String reporte = "Obj_Reporte_IZAGAR_Consulta_De_Orden_De_Compra.jrxml";
				String comando = "exec sp_consulta_orden_de_compra '"+txtFolio.getText().toUpperCase()+"','"+txtUsuario.getText()+"','"+cmbCompras.getSelectedItem().toString()+"','"+cmbContabilidad.getSelectedItem().toString()+"','"+cmbAuditoria.getSelectedItem().toString()+"'";
		   	    new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
				}else{
		  	  JOptionPane.showMessageDialog(null, "El Campo Folio No Debe De Estar Vacio","Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
	          return;
			}
	 	}
	   };
	
	
	public static void main(String[] args) {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Reporte_De_Orden_De_Compra().setVisible(true);
		}catch(Exception e){	}
	}
}
