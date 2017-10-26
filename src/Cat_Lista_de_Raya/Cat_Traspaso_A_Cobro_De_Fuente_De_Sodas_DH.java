package Cat_Lista_de_Raya;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.Method;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Conexiones_SQL.Connexion;
import Obj_Lista_de_Raya.Obj_Captura_Fuente_Sodas;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Lista_de_Raya.Obj_Filtro_Ticket_Fuente_Sodas;
import Obj_Principal.Componentes;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_tabla;
import Obj_Renders.tablaRenderer;

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
	
	public class Cat_Filtro_Ticket_Fuente_Sodas_DH extends JFrame {
		
		Container cont = getContentPane();
		JLayeredPane campo = new JLayeredPane();
		
		int folio_empleado=0;
		
		int bandera_capturado_auxf;
		DefaultTableModel modeloFiltro = new DefaultTableModel(null,
	            new String[]{"Ticket", "Importe","Fecha","DH","AF"}
				){
		     @SuppressWarnings("rawtypes")
			Class[] types = new Class[]{
		    	java.lang.Integer.class,
		    	java.lang.String.class,
		    	java.lang.String.class,
		    	java.lang.Boolean.class,
		    	java.lang.Boolean.class
	         };
		     @SuppressWarnings({ "rawtypes" })
			public Class getColumnClass(int columnIndex) {
	             return types[columnIndex];
	         }
	         public boolean isCellEditable(int fila, int columna){
	        	 switch(columna){
	        	 	case 0 : return false; 
	        	 	case 1 : return false; 
	        	 	case 2 : return false; 
	        	 	case 3 : if(Boolean.valueOf(tablaFiltro.getValueAt(fila, 4).toString())!=true){return false;}else{return true;}
	        	 	case 4 : return false;
	        	 		
	        	 } 				
	 			return false;
	 		}
		};
		
		
		JTable tablaFiltro = new JTable(modeloFiltro);
	    JScrollPane scroll = new JScrollPane(tablaFiltro);
		
		JTextField txtFolio = new JTextField();
		JTextField txtNombre_Completo = new JTextField();
		
		JButton btnAgregar = new JButton(new ImageIcon("Iconos/agregar.png"));
		
		public Cat_Filtro_Ticket_Fuente_Sodas_DH(int folio,String empleado) {
			
//			this.setModal(true);
			setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/filter_icon&16.png"));
			setTitle("Tabla De Ticket Por Empleado (Desarrollo humano)");
			campo.setBorder(BorderFactory.createTitledBorder("Seleccion De Ticket Por Empleado"));
			
			this.txtFolio.setEditable(false);
			this.txtNombre_Completo.setEditable(false);
			
			folio_empleado=folio;
			
			txtFolio.setText(folio_empleado+"");
			txtNombre_Completo.setText(empleado);
			
			buscar_tabla(folio_empleado);
			
			btnAgregar.setToolTipText("Agregar");
			
			campo.add(scroll).setBounds(15,43,414,360);
			
			campo.add(txtFolio).setBounds(15,20,40,20);
			campo.add(txtNombre_Completo).setBounds(56,20,280,20);
			campo.add(btnAgregar).setBounds(340,20,50,20);
			
			cont.add(campo);
			
			configuracionTabla();
			
			btnAgregar.addActionListener(opAgregar);
			
			setSize(455,450);
			setResizable(false);
			setLocationRelativeTo(null);
		}
		
		public void configuracionTabla(){
			this.tablaFiltro.getTableHeader().setReorderingAllowed(false) ;
	   		this.tablaFiltro.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	   		
			tablaFiltro.getColumnModel().getColumn(0).setMaxWidth(100);
			tablaFiltro.getColumnModel().getColumn(0).setMinWidth(100);
			tablaFiltro.getColumnModel().getColumn(1).setMaxWidth(150);
			tablaFiltro.getColumnModel().getColumn(1).setMinWidth(150);
			tablaFiltro.getColumnModel().getColumn(2).setMaxWidth(80);
			tablaFiltro.getColumnModel().getColumn(2).setMinWidth(80);
			tablaFiltro.getColumnModel().getColumn(3).setMaxWidth(40);
			tablaFiltro.getColumnModel().getColumn(3).setMinWidth(40);
			tablaFiltro.getColumnModel().getColumn(4).setMaxWidth(40);
			tablaFiltro.getColumnModel().getColumn(4).setMinWidth(40);
			
			
			tablaFiltro.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 	
			tablaFiltro.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
			tablaFiltro.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","centro","Arial","normal",12)); 	
			tablaFiltro.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("CHB","centro","Arial","normal",12));
			tablaFiltro.getColumnModel().getColumn(4).setCellRenderer(new tablaRenderer("CHB","centro","Arial","normal",12));
		}
		
		ActionListener opAgregar = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
					if(tablaFiltro.isEditing()){
			 			tablaFiltro.getCellEditor().stopCellEditing();
					}
				
				if(new Obj_Filtro_Ticket_Fuente_Sodas().guardar_dh(tabla_guardar(), Integer.parseInt(txtFolio.getText()), txtNombre_Completo.getText())){

					//tabla de tickets--------------------------------
					while(tablaFiltro.getRowCount()>0){
						modeloFiltro.removeRow(0);
				    }
					buscar_tabla(folio_empleado);
					//------------------------------------------------

					if(tablaFiltro.getRowCount()==0){
						dispose();
						tabla_model.setRowCount(0);
						init_tablaf();
					}
				}else{
					JOptionPane.showMessageDialog(null, "Error al guardar", "Error al guardar registro", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
					return;
				}
			}
		};
		
		public void buscar_tabla(int folio_empleado){
			
			try {
				String[][] tabla = new Obj_Captura_Fuente_Sodas().tabla_dh(folio_empleado);
									
				for(int i=0; i<tabla.length; i++){
					 		Object[] dom = new Object[5];
					 		
					 		dom[0] = tabla[i][0]+"   ";
					 		dom[1] = "   "+tabla[i][1];                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
					 		dom[2] = tabla[i][2];
					 		dom[3] = "";
					 		dom[4]=Integer.valueOf(tabla[i][3])==1?false:true;
					 		modeloFiltro.addRow(dom);
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		private Object[][] tabla_guardar(){

			Object[][] matriz = new Object[tablaFiltro.getRowCount()][4];
			for(int i=0; i<tablaFiltro.getRowCount(); i++){
					
					matriz[i][0] = modeloFiltro.getValueAt(i,0).toString().trim();
					matriz[i][1] = modeloFiltro.getValueAt(i,1).toString().trim();
					matriz[i][2] = modeloFiltro.getValueAt(i,2).toString().trim();
					matriz[i][3] = Boolean.parseBoolean(modeloFiltro.getValueAt(i,3).toString().trim());
			}
			return matriz;
		}
}
	
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


