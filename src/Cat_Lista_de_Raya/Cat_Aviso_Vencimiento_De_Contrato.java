package Cat_Lista_de_Raya;

import java.awt.Color;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Conexiones_SQL.Connexion;
import Obj_Principal.Componentes;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_tabla;

@SuppressWarnings("serial")
public class Cat_Aviso_Vencimiento_De_Contrato extends JDialog{
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JTextField txtFiltrof = new Componentes().text(new JCTextField(), "Teclea Aqui Para Buscar En La Tabla", 500, "String");
	Connexion con = new Connexion();
	Obj_tabla ObjTabf =new Obj_tabla();
	int columnas = 9,checkbox=-1;
	public void init_tabla(){
    	this.tabla.getColumnModel().getColumn(0).setMinWidth(50);
    	this.tabla.getColumnModel().getColumn(0).setMaxWidth(50);
    	this.tabla.getColumnModel().getColumn(1).setMinWidth(320);
    	this.tabla.getColumnModel().getColumn(2).setMinWidth(100);
    	this.tabla.getColumnModel().getColumn(3).setMinWidth(80);
    	this.tabla.getColumnModel().getColumn(4).setMinWidth(100);
    	this.tabla.getColumnModel().getColumn(5).setMinWidth(95);
    	this.tabla.getColumnModel().getColumn(6).setMinWidth(200);
    	this.tabla.getColumnModel().getColumn(7).setMinWidth(200);
    	this.tabla.getColumnModel().getColumn(8).setMinWidth(350);
		String comandof="exec sp_select_contratos_proximos_a_terminar";
		String basedatos="26",pintar="si";
		ObjTabf.Obj_Refrescar(tabla,modelo, columnas, comandof, basedatos,pintar,checkbox);
    }
	
	@SuppressWarnings("rawtypes")
	public Class[] base (){
		Class[] types = new Class[columnas];
		for(int i = 0; i<columnas; i++){types[i]= java.lang.Object.class;}
		return types;
	}
	
	 public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"Folio", "Empleado", "Fecha Ingreso","Contrato","Finaliza Contrato","Dias Trabajados","Establecimiento","Departamento","Puesto"}){
		 @SuppressWarnings("rawtypes")
			Class[] types = base();
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
			public boolean isCellEditable(int fila, int columna){return false;}
	    };
	    JTable tabla = new JTable(modelo);
		public JScrollPane scroll_tabla = new JScrollPane(tabla);
	     @SuppressWarnings({ "rawtypes", "unused" })
	    private TableRowSorter trsfiltro;
	
	public Cat_Aviso_Vencimiento_De_Contrato(){
		int ancho = Toolkit.getDefaultToolkit().getScreenSize().width;
		this.setSize(ancho,600);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setModal(true);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("imagen/list-icon-1440-32px.png"));
		this.panel.setBorder(BorderFactory.createTitledBorder("Contratos Proximos A Terminar"));
		this.setTitle("Colaboradores Con Contrato Proximo A Terminar");
		
		this.panel.add(txtFiltrof).setBounds  (5 ,15 ,ancho-15,20 ); 
		this.panel.add(scroll_tabla).setBounds(5 ,35 ,ancho-15,530);
		
		init_tabla();
		
		cont.setBackground(Color.white);
		txtFiltrof.addKeyListener(opFiltroNombre);
	
		cont.add(panel);
		
	}
	
	KeyListener opFiltroNombre = new KeyListener(){
		public void keyReleased(KeyEvent arg0) {
			ObjTabf.Obj_Filtro(tabla, txtFiltrof.getText().toUpperCase(), columnas);
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}		
	};
	
	public static void main(String[] args) {
		new Cat_Aviso_Vencimiento_De_Contrato().setVisible(true);
	}

}
