package Cat_Compras;

import java.awt.Component;
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

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import Conexiones_SQL.ActualizarSQL;
import Conexiones_SQL.Generacion_Reportes;
import Conexiones_SQL.GuardarSQL;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_Filtro_Dinamico_Plus;
import Obj_Principal.Obj_Refrescar;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Asignacion_De_Pedido extends JDialog {
		
		Container cont = getContentPane();
		JLayeredPane campo = new JLayeredPane();
		
		int checkbox=-1;
		@SuppressWarnings("rawtypes")
		public Class[] tipos(int columnas){
			Class[] tip = new Class[columnas];
			
			for(int i =0; i<columnas; i++){
				if(i==checkbox){
					tip[i]=java.lang.Boolean.class;
				}else{
					tip[i]=java.lang.Object.class;
				}
				
			}
			return tip;
		}
		
	 public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"Pedido", "Origen", "Destino","Nombre Surtidor","Cant. Hojas"}){
		 @SuppressWarnings("rawtypes")
			Class[] types = tipos(this.getColumnCount());
			
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {
	         return types[columnIndex];
	     }
			
	     public boolean isCellEditable(int fila, int columna){
	    	  return false;
			}
	    };
	    
	    JTable tabla = new JTable(modelo);
		public JScrollPane scroll_tabla = new JScrollPane(tabla);
		
	JTextField txtNombre_Completo2 = new Componentes().text(new JTextField(), "Fitrar", 250, "String");
	
	JTextField txtPedido = new Componentes().text(new JCTextField(), "Folio De Pedido", 250, "String");
	JTextField txtEstablecimiento 	= new Componentes().text(new JCTextField(), "Establecimiento", 250, "String");
	JButton btnActualizar 			= new JCButton("Actualizar", "", "Azul");
	JCButton btnDeshacer 			= new JCButton("Deshacer", "deshacer16.png", "Azul");
	JCButton btnGuardar 			= new JCButton("Guardar", "Guardar.png", "Azul");
	JButton btnReporte 				= new JCButton("Reporte", "Report.png", "Azul");
	@SuppressWarnings("rawtypes")
	public Class[] tiposAsignacion(int columnas){
		Class[] tip = new Class[columnas];
		
		for(int i =0; i<columnas; i++){
			if(i==checkbox){
				tip[i]=java.lang.Boolean.class;
			}else{
				tip[i]=java.lang.Object.class;
			}
			
		}
		return tip;
	}
	 public DefaultTableModel modeloAsignacion = new DefaultTableModel(null, new String[]{"Hoja", "Filtro", "Folio Empleado","Empleado"}){
		 @SuppressWarnings("rawtypes")
			Class[] types = tiposAsignacion(this.getColumnCount());
			
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {
	         return types[columnIndex];
	     }
			
	     public boolean isCellEditable(int fila, int columna){
	    	 if(columna ==1)
					return true;
					return false;
			}
	    };
	    
	    JTable tablaAsignacion = new JTable(modeloAsignacion);
		public JScrollPane scroll_tabla_Asignacion = new JScrollPane(tablaAsignacion);
	
		JButton button        = new JButton("");
		
	int paginas = 0;
	public Cat_Asignacion_De_Pedido(){
		
		this.setModal(true);
		
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("imagen/articulo-icono-9036-32-mas.png"));
		this.setTitle("Asignacion De Pedidos");
		campo.setBorder(BorderFactory.createTitledBorder("Asignacion De Pedidos"));
		
		campo.add(txtNombre_Completo2).setBounds(10,20,300,20);
		campo.add(btnActualizar).setBounds(670,20,110,20);
		campo.add(scroll_tabla).setBounds(10,42,775,200);
		
		campo.add(txtPedido).setBounds(10,250,100,20);
		campo.add(txtEstablecimiento).setBounds(112,250,230,20);
		campo.add(btnDeshacer).setBounds(670,250,110,20);
		campo.add(btnGuardar).setBounds(650,530,135,40);
		
		campo.add(scroll_tabla_Asignacion).setBounds(10,275,775,250);
		
		campo.add(btnReporte).setBounds(10, 530, 135, 40);
		
		cont.add(campo);
		
		btnReporte.addActionListener(opFiltroReporteDeAsignaciones);
		
		txtPedido.setEditable(false);
		txtEstablecimiento.setEditable(false);
		
		
		init_tabla();
		tamanioColumnas();
//		render();
		agregar(tabla);
		
		button.addActionListener(opButton);
		btnDeshacer.addActionListener(opDeshacer);
		btnGuardar.addActionListener(opGuardar);
		txtNombre_Completo2.addKeyListener(op_filtro);
		btnActualizar.addActionListener(opActualizar);
		
		this.setSize(800,600);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
	//      asigna el foco al JTextField del nombre deseado al arrancar la ventana
	    this.addWindowListener(new WindowAdapter() {
	            public void windowOpened( WindowEvent e ){
	            	txtNombre_Completo2.requestFocus();
	         }
	    });
	      
	//     pone el foco en el txtFolio al presionar la tecla scape
	      getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
	         KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "foco");
	      
	      getRootPane().getActionMap().put("foco", new AbstractAction(){
	          @Override
	          public void actionPerformed(ActionEvent e)
	          {
	        	  txtNombre_Completo2.setText("");
	              txtNombre_Completo2.requestFocus();
	          }
	      });
	      
	//        pone el foco en la tabla al presionar f4
	      getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
	         KeyStroke.getKeyStroke(KeyEvent.VK_F4 , 0), "dtabla");
	      
	      getRootPane().getActionMap().put("dtabla", new AbstractAction(){
	          @Override
	          public void actionPerformed(ActionEvent e)
	          {
	        	tabla.requestFocus();
	          }
	      });
		 
		
	}
	
	ActionListener opActualizar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			init_tabla();
		}
	};
	
	ActionListener opFiltroReporteDeAsignaciones = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			new Cat_Reportes_De_Pedidos_Asignados().setVisible(true);
		}
	};
	
	public void tamanioColumnas(){
		
		this.tabla.getColumnModel().getColumn(0).setMinWidth(50);		
    	this.tabla.getColumnModel().getColumn(1).setMinWidth(170);
    	this.tabla.getColumnModel().getColumn(2).setMinWidth(170);
    	this.tabla.getColumnModel().getColumn(3).setMinWidth(280);
    	this.tabla.getColumnModel().getColumn(4).setMinWidth(50);
    	
		tablaAsignacion.getTableHeader().setReorderingAllowed(false) ;
    	tablaAsignacion.setAutoResizeMode(JTable.AUTO_RESIZE_OFF );
    	
    	this.tablaAsignacion.getColumnModel().getColumn(0).setMinWidth(70);		
    	this.tablaAsignacion.getColumnModel().getColumn(1).setMinWidth(110);
    	this.tablaAsignacion.getColumnModel().getColumn(2).setMinWidth(100);
    	this.tablaAsignacion.getColumnModel().getColumn(3).setMinWidth(470);
    	
		tablaAsignacion.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",12));
		tablaAsignacion.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",12));
		tablaAsignacion.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",12));
		tablaAsignacion.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",12));
	}
	
//	public void render(){
//		tablaAsignacion.getTableHeader().setReorderingAllowed(false) ;
//    	tablaAsignacion.setAutoResizeMode(JTable.AUTO_RESIZE_OFF );
//    	
//		tablaAsignacion.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",12));
////		tablaAsignacion.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",12));
//		tablaAsignacion.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",12));
//		tablaAsignacion.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",12));
//	}
	
	public void init_tabla(){	
    	int columnas = modelo.getColumnCount();
			String comando="exec sp_select_pedidos_por_asignar ";
			String basedatos="26",pintar="si";
			new Obj_Refrescar(tabla,modelo, columnas, comando, basedatos,pintar,checkbox);
    }
	
	private void agregar(final JTable tbl) {
		tbl.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
					if(e.getClickCount() == 1){
		        		
			        		if(txtPedido.getText().toString().trim().equals("")){
				        			int fila = tabla.getSelectedRow();
					    			Object folio =  tabla.getValueAt(fila, 0).toString().trim();
					    			Object estab =  tabla.getValueAt(fila, 2).toString().trim();
					    			txtPedido.setText(folio.toString());
					    			txtEstablecimiento.setText(estab.toString().trim());
					    			paginas= Integer.valueOf(tabla.getValueAt(fila, 4).toString());
					    			
					    			modeloAsignacion.setRowCount(0);
					    			Object[] reg = new Object[4];
					    			for(int i=0; i<paginas; i++){
					    				reg[0]=i+1;
					    				reg[1]="";
					    				reg[2]="";
					    				reg[3]="";
					    				modeloAsignacion.addRow(reg);
					    			}
					    			
					    			tablaAsignacion.getColumn("Filtro").setCellRenderer(new ButtonRenderer());
					    			tablaAsignacion.getColumn("Filtro").setCellEditor(new ButtonEditor(new JCheckBox()));
					    			
					    			tablaAsignacion.setRowSelectionInterval(0, 0);
				    			
			        		}else{
				        			JOptionPane.showMessageDialog(null, "Ya Hay Un Pedido Seleccionado, Para Cambiar De Folio Guardelo O Seleccione En Deshacer", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
									return;
			        		}
		        	}
			}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {}
		});
		
	}
	
	ActionListener opButton = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
				
			int filaSeleccionada = tablaAsignacion.getSelectedRow();
//			columnaSeleccionada = tablaAsignacion.getSelectedColumn();
			
			System.out.println(filaSeleccionada);
			
			new Cat_Seleccion_De_Colaborador(txtEstablecimiento.getText().trim(),filaSeleccionada).setVisible(true);
			
		}
	};
	
//	boton en tabla --------------------------------------------------------------------------------------
	class ButtonRenderer extends JButton implements TableCellRenderer {
		public ButtonRenderer() {
			setOpaque(true);
		}
		
		public Component getTableCellRendererComponent(JTable table, Object value,
		boolean isSelected, boolean hasFocus, int row, int column) {
//			setText((value == null) ? "" : value.toString());
			setText("Buscar");
			return this;
		}
	}
	class ButtonEditor extends DefaultCellEditor {
		private String label;
		public ButtonEditor(JCheckBox checkBox) {
			super(checkBox);
		}
		
		public Component getTableCellEditorComponent(JTable table, Object value,
		boolean isSelected, int row, int column) {
//			label = (value == null) ? "" : value.toString();
			label = "Buscar";
			button.setText(label);
			
			return button;
		}
		
		public Object getCellEditorValue() {
			return new String(label);
		}
	}
	
	ActionListener opDeshacer = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			limpiarAsignacion();
		}
	};
	
	public void limpiarAsignacion(){
		tabla.requestFocus();
		txtPedido.setText("");
		txtEstablecimiento.setText("");
		modeloAsignacion.setRowCount(0);
		paginas= 0;
	}
	
	ActionListener opGuardar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			
			if(modeloAsignacion.getRowCount()>0){
				
				String hoja_sin_asignar = "no";
				for(int i=0; i<modeloAsignacion.getRowCount(); i++){
					if(modeloAsignacion.getValueAt(i, 2).toString().equals("")){
						hoja_sin_asignar = "si";
						break;
					}
				}
				
					if(hoja_sin_asignar.equals("no")){
						
						String folio_pedido = txtPedido.getText().trim(); 
						
							if(new GuardarSQL().Guardar_Asignacion_De_Pedido(asignacion_de_paginas(),folio_pedido)){
								
//									modificar gestion de pedido con status = 'A' 
									if(new ActualizarSQL().cambiar_status_de_pedido_a_asignado(folio_pedido)){
			
										limpiarAsignacion();
										modelo.setRowCount(0);
										init_tabla();
										
											String basedatos="2.26";
											String vista_previa_reporte="no";
											int vista_previa_de_ventana=0;
										
											String comando="exec sp_reporte_de_gestion_de_pedidos '"+folio_pedido+"','no'";
											String reporte = "Obj_Reporte_De_Pedidos_Asignados.jrxml";
											new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
													
//										JOptionPane.showMessageDialog(null, "guardado y actualizado", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
//										return;
									}
								
							}else{
								JOptionPane.showMessageDialog(null, "No Se Ha Podido Asignar El Pedido, Avise Al Administrador Del Sistema", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
								return;
							}
						
					}else{
						JOptionPane.showMessageDialog(null, "No Se Puede Guardar Un Pedido Con Hojas Sin Asignar", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
						return;
					}
				
			}else{
				JOptionPane.showMessageDialog(null, "No Se Puede Guardar Un Pedido Vacio", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}
		}
	};
	
	public Object[][] asignacion_de_paginas(){
		Object[][] pag = new Object[modeloAsignacion.getRowCount()][modeloAsignacion.getColumnCount()];
		
		for(int i=0; i<modeloAsignacion.getRowCount(); i++){
			for(int j=0; j<modeloAsignacion.getColumnCount(); j++){
				pag[i][j] = modeloAsignacion.getValueAt(i, j);
			}
		}
		
		return pag;
	}
	
	KeyListener op_filtro = new KeyListener(){
		public void keyReleased(KeyEvent arg0) {
			int[] columnas ={0,1,2};
			new Obj_Filtro_Dinamico_Plus(tabla, txtNombre_Completo2.getText().toString().trim().toUpperCase(), columnas);
		}
		public void keyTyped(KeyEvent arg0)   {}
		public void keyPressed(KeyEvent arg0) {}		
	};
	
	public static void main(String[] args) {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Asignacion_De_Pedido().setVisible(true);
		}catch(Exception e){
			System.err.println("Error :"+ e.getMessage());
		}
	}
	
	
	
//	filtro de Empleados Para Surtir Pedido --------------------------------------------------------------------------------
		public class Cat_Seleccion_De_Colaborador extends JDialog {
			
			Container cont = getContentPane();
			JLayeredPane campo = new JLayeredPane();
			
			int checkbox=-1;
			@SuppressWarnings("rawtypes")
			public Class[] tipos(int columnas){
				Class[] tip = new Class[columnas];
				
				for(int i =0; i<columnas; i++){
					if(i==checkbox){
						tip[i]=java.lang.Boolean.class;
					}else{
						tip[i]=java.lang.Object.class;
					}
					
				}
				return tip;
			}
			
			public void init_tabla(String estab){
		    	this.tabla.getColumnModel().getColumn(0).setMinWidth(30);		
		    	this.tabla.getColumnModel().getColumn(1).setMinWidth(300);
		    	this.tabla.getColumnModel().getColumn(2).setMinWidth(410);
		    	
		    	int columnas = modelo.getColumnCount();
		    	
				String comando="SELECT tb_empleado.folio as folio "
						+ "	   ,tb_empleado.nombre+' '+tb_empleado.ap_paterno+' '+tb_empleado.ap_materno as usuario "
						+ "	   ,tb_establecimiento.nombre as establecimiento "
						+ " FROM tb_empleado "
						+ " inner join tb_establecimiento on tb_establecimiento.folio = tb_empleado.establecimiento_id and tb_establecimiento.nombre = '"+estab+"' "
						+ " where tb_empleado.status in (1) "
						+ " order by tb_empleado.nombre,tb_empleado.ap_paterno,tb_empleado.ap_materno";//condicionar estab
				String basedatos="26",pintar="si";
				new Obj_Refrescar(tabla,modelo, columnas, comando, basedatos,pintar,checkbox);
		    }
			
		 public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"Folio", "Nombre De Colaborador", "Establecimiento"}){
			 @SuppressWarnings("rawtypes")
				Class[] types = tipos(this.getColumnCount());
				
				@SuppressWarnings({ "unchecked", "rawtypes" })
				public Class getColumnClass(int columnIndex) {
		         return types[columnIndex];
		     }
				
		     public boolean isCellEditable(int fila, int columna){
		    	 if(columna ==checkbox)
						return true; return false;
				}
		    };
		    
		    JTable tabla = new JTable(modelo);
			public JScrollPane scroll_tabla = new JScrollPane(tabla);
			
		JTextField txtNombre_Completo2 = new Componentes().text(new JTextField(), "Buscar", 250, "String");
		
		public Cat_Seleccion_De_Colaborador(String establecimeinto,int row){
			
			this.setModal(true);
			
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("imagen/usuario-busquedaicono-4661-64.png"));
			this.setTitle("Filtro de Empleados");
			campo.setBorder(BorderFactory.createTitledBorder("Filtro De Empleado"));
			
			campo.add(txtNombre_Completo2).setBounds(15,20,300,20);
			campo.add(scroll_tabla).setBounds(15,42,450,565);
			
			cont.add(campo);
			
			init_tabla(establecimeinto);
			agregar(tabla,row);
			
			txtNombre_Completo2.addKeyListener(op_filtro);
			
			this.setSize(490,650);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			
		//      asigna el foco al JTextField del nombre deseado al arrancar la ventana
		    this.addWindowListener(new WindowAdapter() {
		            public void windowOpened( WindowEvent e ){
		            	txtNombre_Completo2.requestFocus();
		         }
		    });
		      
		//     pone el foco en el txtFolio al presionar la tecla scape
		      getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
		         KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "foco");
		      
		      getRootPane().getActionMap().put("foco", new AbstractAction(){
		          @Override
		          public void actionPerformed(ActionEvent e)
		          {
		        	  txtNombre_Completo2.setText("");
		              txtNombre_Completo2.requestFocus();
		          }
		      });
		      
		//        pone el foco en la tabla al presionar f4
		      getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
		         KeyStroke.getKeyStroke(KeyEvent.VK_F4 , 0), "dtabla");
		      
		      getRootPane().getActionMap().put("dtabla", new AbstractAction(){
		          @Override
		          public void actionPerformed(ActionEvent e)
		          {
		        	tabla.requestFocus();
		          }
		      });
			 
			
		}
		
		private void agregar(final JTable tbl, int row) {
		    tbl.addMouseListener(new java.awt.event.MouseAdapter() {
		        public void mouseClicked(MouseEvent e) {
		        	if(e.getClickCount() == 2){
		    			int fila = tabla.getSelectedRow();
		    			String folio =  tabla.getValueAt(fila, 0).toString().trim();
		    			String nombre =  tabla.getValueAt(fila, 1).toString().trim();
		    			
		    			tablaAsignacion.setValueAt(folio, row, 2);
		    			tablaAsignacion.setValueAt(nombre, row, 3);
		    			dispose();
		        	}
		        }
		    });
		}
		
		KeyListener op_filtro = new KeyListener(){
			public void keyReleased(KeyEvent arg0) {
				int[] columnas ={0,1,2};
				new Obj_Filtro_Dinamico_Plus(tabla, txtNombre_Completo2.getText().toString().trim().toUpperCase(), columnas);
			}
			public void keyTyped(KeyEvent arg0)   {}
			public void keyPressed(KeyEvent arg0) {}		
		};
		
}
	
}

