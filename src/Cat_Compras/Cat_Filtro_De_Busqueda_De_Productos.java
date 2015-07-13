package Cat_Compras;

import java.awt.Color;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import Conexiones_SQL.Connexion;
import Obj_Principal.Componentes;
import Obj_Principal.Obj_Filtro_Dinamico;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Filtro_De_Busqueda_De_Productos extends JDialog {
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	String operador_ventas = "";

	Object[][] Matriz_Productos ;
	DefaultTableModel Tabla_Productos= new DefaultTableModel(null,new String[]{"Codigo", "Descripcion","Clase Producto","Categoria","*"}
			){
		@SuppressWarnings("rawtypes")
		Class[] types = new Class[]{
	    	java.lang.Object.class,
	    	java.lang.Object.class, 
	    	java.lang.Object.class,
	    	java.lang.Object.class,
	    	java.lang.Boolean.class
         };
	     
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Class getColumnClass(int columnIndex) {
			return types[columnIndex];
		}
        public boolean isCellEditable(int fila, int columna){
        	switch(columna){
        		case 0 : return false; 
        	 	case 1 : return false; 
        	 	case 2 : return false; 
        	 	case 3 : return false; 
        	 	
        	 	case 4 : if(operador_ventas.equals(""))
        	 				return false;
        	 			return true;
        	 } 				
 			return false;
 		}
        
        @Override
        public void setValueAt(Object value, int row, int col) {
            super.setValueAt(value, row, col);
            	switch(operador_ventas){
	            	case "Igual"	: if (col == 4 && value.equals(Boolean.TRUE)) deselectValues(row, col); break;
	            	case "Menor que": if (col == 4 && value.equals(Boolean.TRUE)) deselectValues(row, col); break;
	            	case "Mayor que": if (col == 4 && value.equals(Boolean.TRUE)) deselectValues(row, col); break;
	            	case "Diferente": if (col == 4 && value.equals(Boolean.TRUE)) deselectValues(row, col); break;
            	}
        }

        private void deselectValues(int selectedRow, int col) {
            for (int row = 0; row < getRowCount(); row++) {
                if (getValueAt(row, col).equals(Boolean.TRUE)
                        && row != selectedRow) {
                    setValueAt(Boolean.FALSE, row, col);
                    fireTableCellUpdated(row, col);
                }
            }
        }
	};
	
	public JTable tabla = new JTable(Tabla_Productos);
	public JScrollPane scroll_tabla;
	
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	public TableRowSorter trsfiltro = new TableRowSorter(Tabla_Productos); 
	Border blackline, etched, raisedbevel, loweredbevel, empty;
	
	String valor_catalogo="";
	int bandera_filtro_familia=0;
	JTextField txtFolio ;
	JTextField txtProductoDescripcion;
	JTextField txtClase_Producto;
	JTextField txtTallaProducto ;
	
	public Cat_Filtro_De_Busqueda_De_Productos(String bandera_origen_consulta_filro, String operador){
		  setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Filter-List-icon32.png"));
		
		UIManager.put("nimbusBase", new Color(255,255,255));
		UIManager.put("nimbusBlueGrey", new Color(255,250,250));
		UIManager.put("control", new Color(255,250,250));

		
		
		try {
			
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
			    if ("Nimbus".equals(info.getName())) {
			        UIManager.setLookAndFeel(info.getClassName());
			        break;}}
//			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
	      scroll_tabla = new JScrollPane(tabla);
		  JButton btnCargar = new JButton("Cargar", new ImageIcon("imagen/Aplicar.png"));
		  txtFolio = new Componentes().text(new JTextField(),"Busqueda Por Codigo del Producto",25, "Int");
		  txtProductoDescripcion = new Componentes().text(new JTextField(),"Busqueda Por Descripcion del Producto",300, "String");
		  txtClase_Producto = new Componentes().text(new JTextField(),"Busqueda Por Clase De Producto",300, "String");
		  txtTallaProducto = new Componentes().text(new JTextField(),"Busqueda Por Categoria Del Producto",300, "String");
		
		  valor_catalogo=bandera_origen_consulta_filro;
		
		this.setModal(true);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/Filter-List-icon16.png"));
		this.setTitle("Filtro de Productos");
		blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
		this.panel.setBorder(BorderFactory.createTitledBorder(blackline,"Busqueda y Seleccion De Un Producto"));
		
		this.panel.add(txtFolio).setBounds(10,20,59,25);
		this.panel.add(txtProductoDescripcion).setBounds(70,20,450,25);
		this.panel.add(btnCargar).setBounds(920,20,90,25);
		this.panel.add(scroll_tabla).setBounds(10,47,997,511);
		
		this.render();
		this.init_tabla();
		
		operador_ventas = operador;
		
		if(bandera_origen_consulta_filro.equals("Reporte_De_Ventas") || bandera_origen_consulta_filro.equals("Reporte_De_Analisis_De_Precios_De_Competencia")){
			btnCargar.setVisible(true);
			tabla.removeMouseListener(opAgregar);
			tabla.removeKeyListener(op_agregar_productoconteclado);
		}else{
			btnCargar.setVisible(false);
			this.tabla.addMouseListener(opAgregar);
			this.tabla.addKeyListener(op_agregar_productoconteclado);
		}

		txtFolio.addKeyListener(op_filtro_cod_Prod);
		txtProductoDescripcion.addKeyListener(opFiltroDinamico);
		txtClase_Producto.addKeyListener(op_filtro_Familia);
		txtTallaProducto.addKeyListener(op_filtro_Talla);
		
//		txtProductoDescripcion.addKeyListener(op_pasar_a_tabla);
		
		btnCargar.addActionListener(opCargar);
		
		this.cont.add(panel);
		this.setSize(1024,600);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.addWindowListener(op_cerrar);
		
//      pone el foco en el txtProductoDescripcion al presionar la tecla scape y limpia lo buscado
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
           KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "foco");
        getRootPane().getActionMap().put("foco", new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e)
            {   txtProductoDescripcion.setText("");
                txtProductoDescripcion.requestFocus(); }
        });
        
//      asigna el foco al JTextField del nombre deseado al arrancar la ventana
        this.addWindowListener(new WindowAdapter() {
                public void windowOpened( WindowEvent e ){  	txtProductoDescripcion.requestFocus();   }
        });
        
	}
	WindowListener op_cerrar = new WindowListener() {
		public void windowOpened(WindowEvent e) {}
		public void windowIconified(WindowEvent e) {}
		public void windowDeiconified(WindowEvent e) {}
		public void windowDeactivated(WindowEvent e) {}
		public void windowClosing(WindowEvent e) {
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InstantiationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (UnsupportedLookAndFeelException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		public void windowClosed(WindowEvent e) {}
		public void windowActivated(WindowEvent e) {}
	};
	
	
	ActionListener opCargar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			int contador=0;
	 		String Lista="('";	
//	 		trsfiltro.setRowFilter(RowFilter.regexFilter("", 0));
//	 		trsfiltro.setRowFilter(RowFilter.regexFilter("", 1));
//	 		trsfiltro.setRowFilter(RowFilter.regexFilter("", 2));
	 		txtProductoDescripcion.setText("");
	 		new Obj_Filtro_Dinamico(tabla, "Descripcion", "", "", "", "", "", "", "");
	 		
	 		if(tabla.isEditing()){
				tabla.getCellEditor().stopCellEditing();
			}
	 		
	 			for(int i=0; i<tabla.getRowCount(); i++){
	 				if(Boolean.parseBoolean(Tabla_Productos.getValueAt(i, 4).toString()) == true){
	 					String posicion = Tabla_Productos.getValueAt(i, 0).toString().trim();
	 					
	 					contador=contador+=1;
	 							if(contador == 1){
	 								Lista=Lista+"'"+posicion+"'";
			 					}else{
			 						Lista=Lista+"',''"+posicion+"'";
			 					}
	 				}
	 			}
	 			
	 			Lista=Lista+"')";

	 			if(Lista.equals("('')")){
	 				JOptionPane.showMessageDialog(null, "Es necesario seleccionar un argunemto", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
						return;
	 			}else{
	 				try {
						UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InstantiationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IllegalAccessException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (UnsupportedLookAndFeelException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	 				
 					txtFolio.setText("");
 					txtProductoDescripcion.setText("");
	 				if(valor_catalogo.equals("Reporte_De_Analisis_De_Precios_De_Competencia")){
	 					new Cat_Analisis_De_Precios_De_Competencia(Lista,operador_ventas).setVisible(true);
	 				}else{
	 					new Cat_Reporte_De_Ventas(Lista,operador_ventas).setVisible(true);
	 				}
	 				dispose();
	 			}
		}
	};
	
	
	MouseListener opAgregar = new MouseListener() {
		public void mouseReleased(MouseEvent arg0) {}
		public void mousePressed(MouseEvent arg0) {}
		public void mouseExited(MouseEvent arg0) {}
		public void mouseEntered(MouseEvent arg0) {}
		public void mouseClicked(MouseEvent arg0) {
			if(arg0.getClickCount() == 2){
    			int fila = tabla.getSelectedRow();
    			Object folio =  tabla.getValueAt(fila, 0);
    			dispose();
    			
    			
    			switch(valor_catalogo){
				case "Cat_Cotizaciones_De_Un_Producto_En_Proveedores":		
					try {
						UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InstantiationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IllegalAccessException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (UnsupportedLookAndFeelException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
					new Cat_Cotizaciones_De_Un_Producto_En_Proveedores(folio.toString().trim()).setVisible(true);
			           	dispose();
				break;
				case "Cat_Alimentacion_De_Precios_De_Competencia":		
					try {
						UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InstantiationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IllegalAccessException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (UnsupportedLookAndFeelException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					
					new Cat_Alimentacion_De_Precios_De_Competencia(folio.toString().trim()).setVisible(true);
			           	dispose();
				break;
				
				case "125":		System.out.println("prueba");
	           	dispose();
		           break;
		           
		          default: 	 JOptionPane.showMessageDialog(null, "Error Avise al Administrador del Sistema"+valor_catalogo, "Aviso", JOptionPane.ERROR_MESSAGE);
				    break;
    			
    			
    			}
        	}
		}
	};
	
public void init_tabla(){
/////////////////LLENADO DE TABLAS/////////////////////////////////////////////////////////////////////////////
	while(Tabla_Productos.getRowCount()>0){	Tabla_Productos.removeRow(0);	}
		Object[][] getTablaNomina = llenarTablaProductos();
		Object[] fila = new Object[5];
		 for(int i=0; i<getTablaNomina.length; i++){
		         fila[0] = getTablaNomina[i][0]+"";
		         fila[1] = getTablaNomina[i][1]+"";
		         fila[2] = getTablaNomina[i][2]+"";
		         fila[3] = getTablaNomina[i][3]+"";
		         fila[4] = getTablaNomina[i][4].toString();
		         Tabla_Productos.addRow(fila); 
		  }
    }
	
	public void render(){
	    this.tabla.getTableHeader().setReorderingAllowed(false) ;
	    this.tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//	    this.tabla.setRowSorter(trsfiltro);  
    	this.tabla.getColumnModel().getColumn(0).setMaxWidth(60);
    	this.tabla.getColumnModel().getColumn(0).setMinWidth(60);		
    	this.tabla.getColumnModel().getColumn(1).setMaxWidth(1500);
    	this.tabla.getColumnModel().getColumn(1).setMinWidth(450);
    	this.tabla.getColumnModel().getColumn(2).setMaxWidth(1400);
    	this.tabla.getColumnModel().getColumn(2).setMinWidth(200);
    	this.tabla.getColumnModel().getColumn(3).setMaxWidth(1500);
    	this.tabla.getColumnModel().getColumn(3).setMinWidth(240);
    	this.tabla.getColumnModel().getColumn(4).setMaxWidth(30);
    	this.tabla.getColumnModel().getColumn(4).setMinWidth(30);
		tabla.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
		tabla.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
		tabla.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
		tabla.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("fecha","izquierda","Arial","normal",12));
		tabla.getColumnModel().getColumn(4).setCellRenderer(new tablaRenderer("CHB","centro","Arial","normal",12));
	}
	
	
   	public Object[][] llenarTablaProductos(){
		String todos = "select productos.cod_prod,productos.descripcion+' '+productos.codigo_barras_pieza as descripcion,"
				+ "            case when clases_productos.nombre is null then '' else clases_productos.nombre end as clase_producto,"
				+ "               case when categorias.nombre is null then '' else categorias.nombre end as categoria"
				+ " 				  from productos with (nolock)"
				+ "  left outer join clases_productos on clases_productos.clase_producto= productos.clase_producto"
				+ "  left outer join categorias on categorias.categoria=productos.categoria"
				+ "				     order by descripcion,clases_productos.nombre,categorias.nombre  ";
		Statement s;
		ResultSet rs2;
		try {
			s = new Connexion().conexion_IZAGAR().createStatement();
			rs2 = s.executeQuery(todos);
			Matriz_Productos = new Object[getFilasProveedores(todos)][5];
			int i=0;
			while(rs2.next()){
				Matriz_Productos[i][0] = "   "+rs2.getString(1).trim();
				Matriz_Productos[i][1] = "   "+rs2.getString(2).trim();
				Matriz_Productos[i][2] = "   "+rs2.getString(3).trim();
				Matriz_Productos[i][3] = "   "+rs2.getString(4).trim();
				Matriz_Productos[i][4] = "false";
								i++;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en Cat_Filtro_De_Busqueda_De_Productos  en la funcion llenarTablaProductos  SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
	    return Matriz_Productos; 
	}
   	
	public int getFilasProveedores(String qry){
		int filas=0;
		Statement stmt = null;
		try {stmt = new Connexion().conexion_IZAGAR().createStatement();
			ResultSet rs3 = stmt.executeQuery(qry);
			while(rs3.next()){filas++;}
		} catch (SQLException e1) {	e1.printStackTrace();}
		return filas;
	}
	
	 KeyListener op_filtro_cod_Prod = new KeyListener(){
			public void keyReleased(KeyEvent arg0) {
//				trsfiltro.setRowFilter(RowFilter.regexFilter(txtFolio.getText(), 0));
			}
			public void keyTyped(KeyEvent arg0) {
				char caracter = arg0.getKeyChar();
				if(((caracter < '0') ||
					(caracter > '9')) &&
				    (caracter != KeyEvent.VK_BACK_SPACE)){
					arg0.consume(); 
				}	
			}
			public void keyPressed(KeyEvent arg0) {}
		};
		
		KeyListener op_agregar_productoconteclado = new KeyListener() {
			@SuppressWarnings("static-access")
			@Override
			public void keyTyped(KeyEvent e) {
				char caracter = e.getKeyChar();
					if(caracter==e.VK_ENTER){
				int fila=tabla.getSelectedRow()-1;
				String folio = tabla.getValueAt(fila,0).toString().trim();
				switch(valor_catalogo){
				case "Cat_Cotizaciones_De_Un_Producto_En_Proveedores":	
					
					try {
						UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InstantiationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IllegalAccessException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (UnsupportedLookAndFeelException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					new Cat_Cotizaciones_De_Un_Producto_En_Proveedores(folio.toString().trim()).setVisible(true);
			           	dispose();
				break;
				case "Cat_Alimentacion_De_Precios_De_Competencia":	
					try {
						UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InstantiationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IllegalAccessException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (UnsupportedLookAndFeelException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					new Cat_Alimentacion_De_Precios_De_Competencia(folio.toString().trim()).setVisible(true);
			           	dispose();
				break;
				
				case "125":		System.out.println("prueba");
	           	dispose();
		           break;
		           
		          default: 	 JOptionPane.showMessageDialog(null, "Error Avise al Administrador del Sistema No Se Enuentra el Catalogo:"+valor_catalogo, "Aviso", JOptionPane.ERROR_MESSAGE);
				    break;
	 			}
				}
			}
			@Override
			public void keyPressed(KeyEvent e){}
			@Override
			public void keyReleased(KeyEvent e){}
									
		};
		
		KeyListener opFiltroDinamico = new KeyListener(){
			public void keyReleased(KeyEvent arg0) {
				new Obj_Filtro_Dinamico(tabla, "Descripcion", txtProductoDescripcion.getText().toUpperCase(), "", "", "", "", "", "");
			}
			public void keyTyped(KeyEvent arg0) {}
			public void keyPressed(KeyEvent arg0) {}		
		};
		
		KeyListener op_filtro_Familia = new KeyListener(){
			public void keyReleased(KeyEvent arg0) {
				
//				trsfiltro.setRowFilter(RowFilter.regexFilter(txtClase_Producto.getText().toUpperCase().trim(), 2));
//				bandera_filtro_familia=1;
			}
			public void keyTyped(KeyEvent arg0) {}
			public void keyPressed(KeyEvent arg0) {}		
		};
		
		KeyListener op_filtro_Talla = new KeyListener(){
//			@SuppressWarnings("unchecked")
			public void keyReleased(KeyEvent arg0) {
//				trsfiltro.setRowFilter(RowFilter.regexFilter(txtTallaProducto.getText().toUpperCase().trim(), 3));
			}
			public void keyTyped(KeyEvent arg0) {}
			public void keyPressed(KeyEvent arg0) {}		
		};
		
		
		
		public static void main(String args[]){
			try{
				new Cat_Filtro_De_Busqueda_De_Productos("Reporte_De_Ventas","Igual").setVisible(true);
			}catch(Exception e){	}
		}
	
}
