package Cat_Lista_de_Raya;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.Method;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Conexiones_SQL.Connexion;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.Componentes;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_tabla;

@SuppressWarnings({ "serial", "unchecked" })
public class Cat_Traspaso_A_Cobro_De_Fuente_De_Sodas_DH extends JFrame {
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	Connexion con = new Connexion();

	Obj_tabla ObjTab =new Obj_tabla();
	int columnas = 5,checkbox=-1;
	public void init_tablaf(){
    	this.tabla.getColumnModel().getColumn(0).setMinWidth(50);
    	this.tabla.getColumnModel().getColumn(1).setMinWidth(280);
    	this.tabla.getColumnModel().getColumn(2).setMinWidth(150);
    	this.tabla.getColumnModel().getColumn(3).setMinWidth(200);
    	this.tabla.getColumnModel().getColumn(4).setMinWidth(55);
		String comandof="sp_select_filtro_empleados_con_pendiente_en_fuente_de_sodas_dh";
		String basedatos="26",pintar="si";
		ObjTab.Obj_Refrescar(tabla,tabla_model, columnas, comandof, basedatos,pintar,checkbox);
    }
	
	@SuppressWarnings("rawtypes")
	public Class[] base (){
		Class[] types = new Class[columnas];
		for(int i = 0; i<columnas; i++){types[i]= java.lang.Object.class;}
		 return types;
	}
	
	public DefaultTableModel tabla_model = new DefaultTableModel(null, new String[]{"Folio",	"Nombre Completo", "Establecimiento", "Puesto", "Saldo"}){
		 @SuppressWarnings("rawtypes")
			Class[] types = base();
			@SuppressWarnings({ "rawtypes" })
			public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
			public boolean isCellEditable(int fila, int columna){return false;}
	};
	
	JTable tabla = new JTable(tabla_model);
	public JScrollPane scroll_tabla = new JScrollPane(tabla);
     @SuppressWarnings({ "rawtypes" })
    private TableRowSorter trsfiltro;
	     
	JTextField txtBuscar  = new Componentes().text(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<", 300, "String");
	

	String establecimientos[] = new Obj_Establecimiento().Combo_Establecimiento();
	@SuppressWarnings("rawtypes")
	JComboBox cmbEstablecimientos = new JComboBox(establecimientos);
	
	@SuppressWarnings({ "rawtypes" })
	public Cat_Traspaso_A_Cobro_De_Fuente_De_Sodas_DH()	{
		this.setSize(850,415);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/fast-food-icon32.png"));
		this.setTitle("Filtro de empleados con Saldo en fuente de sodas (Desarrollo Humano)");
		panel.setBorder(BorderFactory.createTitledBorder("Transpaso a Cobro De Fuente De Sodas D.H."));

		this.init_tablaf();
		
		trsfiltro = new TableRowSorter(tabla_model); 
		tabla.setRowSorter(trsfiltro);  
		
		panel.add(scroll_tabla).setBounds(15,42,800,327);
		
		panel.add(txtBuscar).setBounds(15,20,800,20);
		panel.add(cmbEstablecimientos).setBounds(387,20, 180, 20);
		
		agregar(tabla);
		
		cont.add(panel);
		
		txtBuscar.addKeyListener(opFiltroFolio);
		
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened( WindowEvent e ){
            	txtBuscar.requestFocus();
         }
       });
        
		
	}
	
	private void agregar(final JTable tbl) {
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
	        public void mouseClicked(MouseEvent e) {
	        	if(e.getClickCount() == 2){
	    			
	        		int fila = tabla.getSelectedRow();
	    			int folio =  Integer.parseInt(tabla.getValueAt(fila, 0)+"");
	    			Object empleado =  tabla.getValueAt(fila, 1);
	    			
	    			new Cat_Filtro_Ticket_Fuente_Sodas_DH(folio,empleado+"").setVisible(true);
	        	}
	        }
        });
    }
	
	KeyListener opFiltroFolio = new KeyListener(){
		public void keyReleased(KeyEvent arg0) {
			ObjTab.Obj_Filtro(tabla, txtBuscar.getText().toUpperCase(), columnas);
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}		
	};

	public static void main(String [] args){
		try{
			UIManager.setLookAndFeel(
					UIManager.getSystemLookAndFeelClassName());
		}catch(Exception e){}
		
		Cat_Traspaso_A_Cobro_De_Fuente_De_Sodas_DH thisClass = new Cat_Traspaso_A_Cobro_De_Fuente_De_Sodas_DH();
		thisClass.setVisible(true);

		//utilizacion del AWTUtilities con el metodo opaque
		try {
			   @SuppressWarnings("rawtypes")
			Class clazz =  Class.forName("com.sun.awt.AWTUtilities");
			   Method method = clazz.getMethod("setWindowOpaque", java.awt.Window.class, Boolean.TYPE);
			   method.invoke(clazz,thisClass , false);
			   } catch (Exception e) 
			   { }	
	}
}


