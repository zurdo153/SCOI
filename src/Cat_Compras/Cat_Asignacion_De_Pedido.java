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
import java.sql.SQLException;

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

import Conexiones_SQL.BuscarSQL;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.Obj_Filtro_Dinamico_Plus;
import Obj_Principal.Obj_tabla;

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
		
	 public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"Pedido", "Origen", "Destino"/*,"Status"*/,"Zonas"}){
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
	
	JButton btnActualizar 			= new JCButton("Actualizar", "", "Azul");
	JButton btnReporte 				= new JCButton("Reporte", "Report.png", "Azul");

	JButton button        = new JButton("");
		
	String estab = "";
//	int paginas = 0;
	public Cat_Asignacion_De_Pedido(String establecimiento){
		
		this.setModal(true);
		
		estab = establecimiento;
		
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("imagen/articulo-icono-9036-32-mas.png"));
		this.setTitle("Asignacion De Pedidos");
		campo.setBorder(BorderFactory.createTitledBorder("Asignacion De Pedidos"));
		
		campo.add(txtNombre_Completo2).setBounds(10,20,300,20);
		campo.add(btnActualizar).setBounds(390,20,110,20);
		campo.add(scroll_tabla).setBounds(10,42,515,300);
		
		campo.add(btnReporte).setBounds(10, 345, 135, 40);
		
		cont.add(campo);
		
		btnReporte.addActionListener(opFiltroReporteDeAsignaciones);
		
		
//		TODO (llenar tabla de pedido con cantidad de paginas)
		init_tabla(estab);
		
		tamanioColumnas();
//		render();
		
//		TODO (seleccionar pedido y llenar segunda tabla con la cantidad de paginas indicadas en la primer tabla)
		agregar(tabla);
		
//		button.addActionListener(opButton);
		txtNombre_Completo2.addKeyListener(op_filtro);
		btnActualizar.addActionListener(opActualizar);
		
		this.setSize(540,420);
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
			
			init_tabla(estab);
		}
	};
	
	ActionListener opFiltroReporteDeAsignaciones = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			new Cat_Reportes_De_Pedidos_Asignados().setVisible(true);
		}
	};
	
	public void tamanioColumnas(){
		
		this.tabla.getColumnModel().getColumn(0).setMinWidth(90);
		this.tabla.getColumnModel().getColumn(0).setMaxWidth(90);
		
		this.tabla.getColumnModel().getColumn(1).setMinWidth(180);
    	this.tabla.getColumnModel().getColumn(1).setMaxWidth(180);
    	
    	this.tabla.getColumnModel().getColumn(2).setMinWidth(180);
    	this.tabla.getColumnModel().getColumn(2).setMaxWidth(180);
    	
//    	this.tabla.getColumnModel().getColumn(3).setMinWidth(260);
//    	this.tabla.getColumnModel().getColumn(3).setMaxWidth(260);
    	
    	this.tabla.getColumnModel().getColumn(3).setMaxWidth(45);
	}
	
	public void init_tabla(String establecimiento){	
    	int columnas = modelo.getColumnCount();
			String comando="exec sp_select_pedidos_por_asignar '"+establecimiento+"'";
			String basedatos="26",pintar="si";
			new Obj_tabla().Obj_Refrescar(tabla,modelo, columnas, comando, basedatos,pintar,checkbox);
    }
	
	private void agregar(final JTable tbl) {
		tbl.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
					if(e.getClickCount() == 2){
							int fila = tabla.getSelectedRow();
							//buscar zonas disponible(si el >0 modificarlo en la tabla)
							int zonas_disponibles = 0;
							try {
								zonas_disponibles = new BuscarSQL().zonas_disponible_en_el_pedido(tabla.getValueAt(fila, 0).toString().trim());
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
							
						if(zonas_disponibles>0){		
								tabla.setValueAt(zonas_disponibles, fila, 3);
								
								Object[] reg = new Object[4];
								reg[0]= tabla.getValueAt(fila, 0).toString().trim();
								reg[1]= tabla.getValueAt(fila, 1).toString().trim();
								reg[2]= tabla.getValueAt(fila, 2).toString().trim();
		//						reg[3]= tabla.getValueAt(fila, 3).toString().trim();
								reg[3]= tabla.getValueAt(fila, 3);
								
				        		new Cat_Asignacion_De_Pedido_Por_Zona(reg).setVisible(true);
		        		
						}else{
							init_tabla(estab);
							JOptionPane.showMessageDialog(null, "El Pedido Ya Fue Recibido En Su Totalidad", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
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
	
//	ActionListener opButton = new ActionListener(){
//		public void actionPerformed(ActionEvent e) {
//			int filaSeleccionada = tablaAsignacion.getSelectedRow();
////			columnaSeleccionada = tablaAsignacion.getSelectedColumn();
//			new Cat_Seleccion_De_Colaborador(txtEstablecimiento.getText().trim(),filaSeleccionada).setVisible(true);
//		}
//	};
	
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
	
	
	
//	TODO (valida si todas las hojas fueron asignadas antes de guardar)
	
//	public Object[][] asignacion_de_paginas(){
//		Object[][] pag = new Object[modeloAsignacion.getRowCount()][modeloAsignacion.getColumnCount()];
//		
//		for(int i=0; i<modeloAsignacion.getRowCount(); i++){
//			for(int j=0; j<modeloAsignacion.getColumnCount(); j++){
//				pag[i][j] = modeloAsignacion.getValueAt(i, j);
//			}
//		}
//		
//		return pag;
//	}
	
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
			new Cat_Asignacion_De_Pedido("CEDIS").setVisible(true);
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
				new Obj_tabla().Obj_Refrescar(tabla,modelo, columnas, comando, basedatos,pintar,checkbox);
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
//		    			int fila = tabla.getSelectedRow();
//		    			String folio =  tabla.getValueAt(fila, 0).toString().trim();
//		    			String nombre =  tabla.getValueAt(fila, 1).toString().trim();
		    			
//		    			tablaAsignacion.setValueAt(folio, row, 2);
//		    			tablaAsignacion.setValueAt(nombre, row, 3);
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

