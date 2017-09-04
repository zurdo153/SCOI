package Cat_Compras;

import java.awt.Component;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
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
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Generacion_Reportes;
import Conexiones_SQL.GuardarSQL;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_Filtro_Dinamico_Plus;
import Obj_Principal.Obj_tabla;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Asignacion_De_Pedido_Por_Zona extends JDialog {
		
	Container cont = getContentPane();
	JLayeredPane campo = new JLayeredPane();
		
	
	JTextField txtPedido = new Componentes().text(new JCTextField(), "Folio De Pedido", 250, "String");
	JTextField txtEstablecimientoOrigen 	= new Componentes().text(new JCTextField(), "Establecimiento Origen", 250, "String");
	JTextField txtEstablecimientoDestino 	= new Componentes().text(new JCTextField(), "Establecimiento Destino", 250, "String");
	JTextField txtZonas 	= new Componentes().text(new JCTextField(), "No Zonas", 250, "String");
	
	JCButton btnLimpiar 			= new JCButton("Limpiar", "deshacer16.png", "Azul");
	JCButton btnGuardar 			= new JCButton("Guardar", "Guardar.png", "Azul");
	JButton btnSurtidor 			= new JCButton("Surtidor", "", "Azul");
	
	JButton btnReporte_General 		= new JCButton("Reporte General", "", "Azul");
	
	int checkbox=6;
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
		
	 public DefaultTableModel modeloAsignacion = new DefaultTableModel(null, new String[]{"Zona", "Cant_Prod", "PZ´s","Folio Emp.","Empleado Asignado","Status","*","Limpiar"}){
		 @SuppressWarnings("rawtypes")
			Class[] types = tiposAsignacion(this.getColumnCount());
			
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {
	         return types[columnIndex];
	     }
			
	     public boolean isCellEditable(int fila, int columna){
	    	 
	    	 switch(columna){
		    	 case 6: return (modeloAsignacion.getValueAt(fila, 5).toString().trim().equals("VIGENTE") || modeloAsignacion.getValueAt(fila, 5).toString().trim().equals("ASIGNADO"))?true:false;
//		    	 case 6: return modeloAsignacion.getValueAt(fila, 5).toString().trim().equals("SURTIDO")?true:false;
		    	 case 7:return true;
		    	 default: return false; 
	    	 }
//		    	 if(columna >=5){
//		    		 
//		    		 return true;
//		    	 }else{
//		    		 return false;
//		    	 }
			}
	    };
	    
	    JTable tablaAsignacion = new JTable(modeloAsignacion);
		public JScrollPane scroll_tabla_Asignacion = new JScrollPane(tablaAsignacion);
	
		JButton button        = new JButton("");
		
		@SuppressWarnings("rawtypes")
		public Class[] totalesPorSurtidor(int columnas){
			Class[] tip = new Class[columnas];
			
			for(int i =0; i<columnas; i++){
					tip[i]=java.lang.Object.class;
			}
			return tip;
		}
		
		 public DefaultTableModel modeloTotales = new DefaultTableModel(null, new String[]{"Folio Emp.","Surtidor","Zonas","Cant_Prod", "PZ´s","Reprorte"}){
			 @SuppressWarnings("rawtypes")
				Class[] types = tiposAsignacion(this.getColumnCount());
				
				@SuppressWarnings({ "unchecked", "rawtypes" })
				public Class getColumnClass(int columnIndex) {
		         return types[columnIndex];
		     }
				
		     public boolean isCellEditable(int fila, int columna){
		    	 if(columna == 5)
						return true;
						return false;
				}
		    };
		    
		    JTable tablaTotales = new JTable(modeloTotales);
			public JScrollPane scroll_tabla_totales = new JScrollPane(tablaTotales);
		
//	String estab = "";
	int paginas = 0;
	public Cat_Asignacion_De_Pedido_Por_Zona(Object[] reg){
		
		this.setModal(true);
		
		txtPedido.setText(reg[0].toString());
		txtEstablecimientoOrigen.setText(reg[1].toString());
		txtEstablecimientoDestino.setText(reg[2].toString());
		txtZonas.setText(reg[3].toString());
		
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("imagen/articulo-icono-9036-32-mas.png"));
		this.setTitle("Asignacion De Pedidos");
		campo.setBorder(BorderFactory.createTitledBorder("Asignacion De Pedidos"));
		
		int y = 20;
		campo.add(new JLabel("Folio Pedido:")).setBounds(10,y,80,20);
		campo.add(txtPedido).setBounds(80,y,100,20);
		
		campo.add(new JLabel("Zonas:")).setBounds(560,y,80,20);
		campo.add(txtZonas).setBounds(600,y,50,20);
		
		campo.add(new JLabel("Estab.Origen:")).setBounds(10,y+=25,100,20);
		campo.add(txtEstablecimientoOrigen).setBounds(80,y,230,20);
		
		campo.add(new JLabel("Estab. Destino:")).setBounds(340,y,100,20);
		campo.add(txtEstablecimientoDestino).setBounds(420,y,230,20);
		
		campo.add(btnLimpiar).setBounds(715,y,100,20);
		campo.add(scroll_tabla_Asignacion).setBounds(10,y+=25,835,250);
		
		campo.add(scroll_tabla_totales).setBounds(10, y+=260, 660, 110);
		
		campo.add(btnSurtidor).setBounds(710,y,135,40);
		campo.add(btnGuardar).setBounds(710,y+=45,135,40);
		
		campo.add(btnReporte_General).setBounds(535,y+70,135,25);
		
		
		cont.add(campo);
		
		txtPedido.setEditable(false);
		txtEstablecimientoOrigen.setEditable(false);
		txtEstablecimientoDestino.setEditable(false);
		txtZonas.setEditable(false);
		
		cargar_tabla();
		calcularTotales();
		tamanioColumnas();
		botonesEnTablas();
		
//		render();
		
		btnLimpiar.addActionListener(opLimpiarTodo);
		button.addActionListener(opButton);
		btnReporte_General.addActionListener(opButton);
		
		btnSurtidor.addActionListener(opSurtidor);
		btnGuardar.addActionListener(opGuardar);
		
		this.setSize(870,510);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	public void cargar_tabla(){
		
//		String basedatos="26",pintar="si";
//		String comando = "";
//		new Obj_tabla().Obj_Refrescar(tablaAsignacion,modeloAsignacion, modeloAsignacion.getColumnCount()-1, null, basedatos,pintar,checkbox);
		modeloAsignacion.setRowCount(0);
		Object[][] datos = new BuscarSQL().Consultar_Zonas_Para_Asignacion_De_Surtidores(txtPedido.getText().toString().trim(),Integer.valueOf(txtZonas.getText().toString().trim()));
		for(Object[] dt: datos){
			modeloAsignacion.addRow(dt);
		}
	}
	
	public void calcularTotales(){
		
		modeloTotales.setRowCount(0);
		
		if(modeloAsignacion.getRowCount()>0){
			for(int i = 0 ; i<modeloAsignacion.getRowCount(); i++){
				Object[] reg = new Object[6];
				reg[0]=modeloAsignacion.getValueAt(i, 3);
				reg[1]=modeloAsignacion.getValueAt(i, 4);
				reg[2]=modeloAsignacion.getValueAt(i, 0);
				reg[3]=modeloAsignacion.getValueAt(i, 1);
				reg[4]=modeloAsignacion.getValueAt(i, 2);
				reg[5]="Reporte";
				
				if(i==0){
					modeloTotales.addRow(reg);
				}else{
					boolean existe_surtidor = false;
					int filaRepetida = 0;
					for(int j=0; j<modeloTotales.getRowCount(); j++){
						if(reg[0].toString().trim().equals(modeloTotales.getValueAt(j, 0).toString().trim())){
							existe_surtidor=true;
							filaRepetida = j;
							break;
						}
					}
					
					if(existe_surtidor){
						modeloTotales.setValueAt(modeloTotales.getValueAt(filaRepetida, 2).toString()+","+reg[2].toString(), filaRepetida, 2);
						modeloTotales.setValueAt((Float.valueOf(modeloTotales.getValueAt(filaRepetida, 3).toString())+Float.valueOf(reg[3].toString())), filaRepetida, 3);
						modeloTotales.setValueAt((Float.valueOf(modeloTotales.getValueAt(filaRepetida, 4).toString())+Float.valueOf(reg[4].toString())), filaRepetida, 4);
					}else{
						modeloTotales.addRow(reg);
					}
				}
			}
		}
	}
	
//	ActionListener opFiltroReporteDeAsignaciones = new ActionListener() {
//		public void actionPerformed(ActionEvent e) {
//			new Cat_Reportes_De_Pedidos_Asignados().setVisible(true);
//		}
//	};
	
	public void tamanioColumnas(){
		
		tablaAsignacion.getTableHeader().setReorderingAllowed(false) ;
    	tablaAsignacion.setAutoResizeMode(JTable.AUTO_RESIZE_OFF );
    	
    	this.tablaAsignacion.getColumnModel().getColumn(0).setMinWidth(50);		
    	this.tablaAsignacion.getColumnModel().getColumn(0).setMaxWidth(50);		
    	
    	this.tablaAsignacion.getColumnModel().getColumn(1).setMinWidth(80);
    	this.tablaAsignacion.getColumnModel().getColumn(1).setMaxWidth(80);	
    	
    	this.tablaAsignacion.getColumnModel().getColumn(2).setMinWidth(60);
    	this.tablaAsignacion.getColumnModel().getColumn(2).setMaxWidth(60);	
    	
    	this.tablaAsignacion.getColumnModel().getColumn(3).setMinWidth(60);
    	this.tablaAsignacion.getColumnModel().getColumn(3).setMaxWidth(60);	
    	
    	this.tablaAsignacion.getColumnModel().getColumn(4).setMinWidth(350);
    	this.tablaAsignacion.getColumnModel().getColumn(4).setMaxWidth(350);	
    	
    	this.tablaAsignacion.getColumnModel().getColumn(5).setMinWidth(105);
    	this.tablaAsignacion.getColumnModel().getColumn(5).setMaxWidth(105);	
    	
    	this.tablaAsignacion.getColumnModel().getColumn(6).setMinWidth(30);
    	this.tablaAsignacion.getColumnModel().getColumn(6).setMaxWidth(30);	
    	
    	this.tablaAsignacion.getColumnModel().getColumn(7).setMinWidth(80);
    	this.tablaAsignacion.getColumnModel().getColumn(7).setMaxWidth(80);	
    	
		tablaAsignacion.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",12));
		tablaAsignacion.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",12));
		tablaAsignacion.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",12));
		tablaAsignacion.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",12));
		tablaAsignacion.getColumnModel().getColumn(4).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",12));
		tablaAsignacion.getColumnModel().getColumn(5).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",12));
		tablaAsignacion.getColumnModel().getColumn(6).setCellRenderer(new tablaRenderer("CHB","centro","Arial","negrita",12));
//		tablaAsignacion.getColumnModel().getColumn(6).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",12));
		
		tablaTotales.getTableHeader().setReorderingAllowed(false) ;
		tablaTotales.setAutoResizeMode(JTable.AUTO_RESIZE_OFF );
    	
		this.tablaTotales.getColumnModel().getColumn(0).setMinWidth(40);		
    	this.tablaTotales.getColumnModel().getColumn(0).setMaxWidth(40);
    	
    	this.tablaTotales.getColumnModel().getColumn(1).setMinWidth(260);		
    	this.tablaTotales.getColumnModel().getColumn(1).setMaxWidth(260);		
    	
    	this.tablaTotales.getColumnModel().getColumn(2).setMinWidth(120);
    	this.tablaTotales.getColumnModel().getColumn(2).setMaxWidth(120);	
    	
    	this.tablaTotales.getColumnModel().getColumn(3).setMinWidth(70);
    	this.tablaTotales.getColumnModel().getColumn(3).setMaxWidth(70);	
    	
    	this.tablaTotales.getColumnModel().getColumn(4).setMinWidth(70);
    	this.tablaTotales.getColumnModel().getColumn(4).setMaxWidth(70);
    	
    	this.tablaTotales.getColumnModel().getColumn(5).setMinWidth(80);
    	this.tablaTotales.getColumnModel().getColumn(5).setMaxWidth(80);
    	
    	tablaTotales.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",12));
    	tablaTotales.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",12));
    	tablaTotales.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",12));
    	tablaTotales.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","derecha","Arial","negrita",12));
    	tablaTotales.getColumnModel().getColumn(4).setCellRenderer(new tablaRenderer("texto","derecha","Arial","negrita",12));
	}
	
	ActionListener opButton = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			
			String ActionButton = e.getActionCommand().toString().trim();
			if(ActionButton.equals("Limpiar")){
				limpiarFila();
			}else{
				//--------------------------------------------------------------------------------------------------------------------------------------------
				if(ActionButton.equals("Reporte General")){
					generarReporte("-1");
				}else{
					if(modeloTotales.getValueAt(tablaTotales.getSelectedRow(),0).equals("")){
//						generarReporte("0");
						JOptionPane.showMessageDialog(null, "No Se Puede Generar Reporte De Las Zonas Sin Asignar", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
						return;
					}else{
						generarReporte(tablaTotales.getValueAt(tablaTotales.getSelectedRow(), 0).toString());
					}
				}
				//--------------------------------------------------------------------------------------------------------------------------------------------
			}
			
		}
	};
	
	public void limpiarFila(){
		int filaSeleccionada = tablaAsignacion.getSelectedRow();
		
		if(modeloAsignacion.getValueAt(filaSeleccionada, 5).toString().trim().equals("VIGENTE") || modeloAsignacion.getValueAt(filaSeleccionada, 5).toString().trim().equals("ASIGNADO")){
			modeloAsignacion.setValueAt("", filaSeleccionada, 3);
			modeloAsignacion.setValueAt("", filaSeleccionada, 4);
			calcularTotales();
		}else{
			JOptionPane.showMessageDialog(null, "Solo Se Pueden Limpiar Los Registros Con Estatus VIGENTE", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
			return;
		}
	}
	
	public void generarReporte(String surtidor){
		
		String basedatos="2.26";
		String vista_previa_reporte="no";
		int vista_previa_de_ventana=0;
		
		//surtidor = 0 (zonas sin asignar)
		//surtidor > 0 (zonas asignadas al surtidor seleccionado)
		//surtidor = -1 (reporte general)
//		String surtidor = boton_presionado.equals("Reporte General")?"-1":tablaTotales.getValueAt(tablaTotales.getSelectedRow(), 0).toString().equals("")?"0":tablaTotales.getValueAt(tablaTotales.getSelectedRow(), 0).toString();
			
			String comando="exec sp_reporte_de_gestion_de_pedidos_por_zona '"+txtPedido.getText().toString().trim()+"','"+surtidor+"'";
			String reporte = "Obj_Reporte_De_Pedidos_Asignados_General.jrxml";
			new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
	}
	
	
	
	
	ActionListener opLimpiarTodo = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
//			int filaSeleccionada = tablaAsignacion.getSelectedRow();
//			tablaAsignacion.setValueAt("", filaSeleccionada, 3);
//			calcularTotales();
			
			for(int i = 0; i<modeloAsignacion.getRowCount(); i++){
				if(modeloAsignacion.getValueAt(i, 5).toString().trim().equals("VIGENTE") || modeloAsignacion.getValueAt(i, 5).toString().trim().equals("ASIGNADO")){
					modeloAsignacion.setValueAt("", i, 3);
					modeloAsignacion.setValueAt("", i, 4);
				}
			}
//			modeloAsignacion.setValueAt("VIGENTE", 1, 4);
			calcularTotales();
			JOptionPane.showMessageDialog(null, "Solo Se Limpiaron Los Registros Con Estatus VIGENTE", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
			return;
		}
	};
	
	ActionListener opSurtidor = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			
			boolean chb_Seleccionados = false;
			for(int i = 0; i<modeloAsignacion.getRowCount(); i++){
				if(modeloAsignacion.getValueAt(i, 6).toString().trim().equals("true")){
					chb_Seleccionados = true;
					break;
				}
			}
			
			if(chb_Seleccionados){
				new Cat_Seleccion_De_Colaborador(txtEstablecimientoDestino.getText().trim()).setVisible(true);
			}else{
				JOptionPane.showMessageDialog(null, "Se Requiere Seleccionar las Zonas Que Asignara", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}
		}
	};
	
	public void botonesEnTablas(){
//    	AGREGAR BOTON A LA TABLA Asignacion
		tablaAsignacion.getColumn("Limpiar").setCellRenderer(new ButtonRenderer());
		tablaAsignacion.getColumn("Limpiar").setCellEditor(new ButtonEditor(new JCheckBox()));
		
//    	AGREGAR BOTON A LA TABLA Totales
		tablaTotales.getColumn("Reprorte").setCellRenderer(new ButtonRenderer());
		tablaTotales.getColumn("Reprorte").setCellEditor(new ButtonEditor(new JCheckBox()));
	}
	
//	boton en tabla --------------------------------------------------------------------------------------
	class ButtonRenderer extends JButton implements TableCellRenderer {
		public ButtonRenderer() {
			setOpaque(true);
		}
		
		public Component getTableCellRendererComponent(JTable table, Object value,
		boolean isSelected, boolean hasFocus, int row, int column) {
			setText((value == null) ? "" : value.toString());
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
			label = (value == null) ? "" : value.toString();
			button.setText(label);
			
			return button;
		}
		
		public Object getCellEditorValue() {
			return new String(label);
		}
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void limpiarAsignacion(){
		txtPedido.setText("");
		txtEstablecimientoOrigen.setText("");
		txtEstablecimientoDestino.setText("");
		txtZonas.setText("");
		modeloAsignacion.setRowCount(0);
		paginas= 0;
	}
	
//	TODO (valida si todas las hojas fueron asignadas antes de guardar)
	ActionListener opGuardar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			

//			int fila = tabla.getSelectedRow();
			//buscar zonas disponible(si el >0 modificarlo en la tabla)
			int zonas_disponibles = 0;
			try {
				zonas_disponibles = new BuscarSQL().zonas_disponible_en_el_pedido(txtPedido.getText().toString().trim());
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			if(zonas_disponibles==tablaAsignacion.getRowCount()){		
					
					if(modeloAsignacion.getRowCount()>0){
						
									if(new GuardarSQL().Guardar_Asignacion_De_Pedido(DatosDeAsignacion(),txtPedido.getText().toString().trim())){
												cargar_tabla();
												calcularTotales();			
												JOptionPane.showMessageDialog(null, "Las Zonas Se Han Asignado Correctamente", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
												return;
										
									}else{
										JOptionPane.showMessageDialog(null, "No Se Ha Podido Asignar El Pedido, Avise Al Administrador Del Sistema", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
										return;
									}
								
					}else{
						JOptionPane.showMessageDialog(null, "No Se Puede Guardar Un Pedido Vacio", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
						return;
					}
			
			}else{
				txtZonas.setText(zonas_disponibles+"");
				cargar_tabla();
				calcularTotales();
				JOptionPane.showMessageDialog(null, "Alguna De Las Zonas Pudieron Presentar Cambios,/nPor Lo Que Fue Necesario Actualizar Los Datos,/nVuelva A Intentarlo", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}
			

		}
	};
	
	public Object[][] DatosDeAsignacion(){
		Object[][] pag = new Object[modeloAsignacion.getRowCount()][3];
		
		for(int i=0; i<modeloAsignacion.getRowCount(); i++){
				pag[i][0] = modeloAsignacion.getValueAt(i, 0);
				pag[i][1] = modeloAsignacion.getValueAt(i, 3);
				pag[i][2] = modeloAsignacion.getValueAt(i, 5);
		}
		
		return pag;
	}
	
	ActionListener opReportePedido = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			
			reporte(e.getActionCommand().toString().trim().equals("Reporte De Productos Surtidos")?"no":"si");
		}
	};
	
	public void reporte(String status_negado){
		String basedatos="2.26";
		String vista_previa_reporte="no";
		int vista_previa_de_ventana=0;
		
//		if(tablaTotales.getSelectedRow()<0){
//			JOptionPane.showMessageDialog(null,"Necesita Seleccionar Un Pedido", "Mensaje!",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
//			return;
//		}else{
			
			String comando="exec sp_reporte_de_gestion_de_pedidos '"+tablaTotales.getValueAt(tablaTotales.getSelectedRow(), 0)+"','"+status_negado+"'";
			String reporte = "Obj_Reporte_De_Pedidos_Asignados.jrxml";
			new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
//		}
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
			
			public Cat_Seleccion_De_Colaborador(String establecimeinto){
				
				this.setModal(true);
				
				this.setIconImage(Toolkit.getDefaultToolkit().getImage("imagen/usuario-busquedaicono-4661-64.png"));
				this.setTitle("Filtro de Empleados");
				campo.setBorder(BorderFactory.createTitledBorder("Filtro De Empleado"));
				
				campo.add(txtNombre_Completo2).setBounds(15,20,300,20);
				campo.add(scroll_tabla).setBounds(15,42,450,565);
				
				cont.add(campo);
				
				init_tabla(establecimeinto);
				agregar(tabla);
				
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
			
			private void agregar(final JTable tbl) {
			    tbl.addMouseListener(new java.awt.event.MouseAdapter() {
			        public void mouseClicked(MouseEvent e) {
			        	if(e.getClickCount() == 2){
			    			int fila = tabla.getSelectedRow();
			    			String folio =  tabla.getValueAt(fila, 0).toString().trim();
			    			String nombre =  tabla.getValueAt(fila, 1).toString().trim();
			    			
			    			for(int i=0; i<modeloAsignacion.getRowCount(); i++){
			    				if(modeloAsignacion.getValueAt(i, 6).toString().trim().equals("true")){
			    					modeloAsignacion.setValueAt(folio, i, 3);
			    					modeloAsignacion.setValueAt(nombre, i, 4);
			    					modeloAsignacion.setValueAt(false, i, 6);
			    				}
			    			}
			    			calcularTotales();
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

