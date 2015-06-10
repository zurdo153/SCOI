package Cat_Compras;

import java.awt.Container;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Connexion;
import Obj_Renders.tablaRenderer;
import Obj_Reportes.Obj_Reportes_De_Ventas;

import com.toedter.calendar.JDateChooser;

@SuppressWarnings("serial")
public class Cat_Analisis_De_Precios_De_Competencia extends JFrame {
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	Connexion con = new Connexion();
	
	JDateChooser c_inicio = new JDateChooser();
	
	String operadorProducto[] = {"Todos","Igual","Esta en lista","Menor que","Mayor que","Diferente"};
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbOperador_Productos = new JComboBox(operadorProducto);
	
	String operadorClase[] = {"Todos","Igual","Esta en lista","Menor que","Mayor que","Diferente"};
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbOperador_Clase = new JComboBox(operadorClase);

	String operadorCategoria[] = {"Todos","Igual","Esta en lista","Menor que","Mayor que","Diferente"};
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbOperador_Categoria= new JComboBox(operadorCategoria);
	
	String operadorFamilia[] = {"Todos","Igual","Esta en lista","Menor que","Mayor que","Diferente"};
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbOperador_Familia = new JComboBox(operadorFamilia);
	
	String operadorLinea[] = {"Todos","Igual","Esta en lista","Menor que","Mayor que","Diferente"};
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbOperador_Linea = new JComboBox(operadorLinea);
	
	
//	JButton btnBuscar_Producto = new JButton("",new ImageIcon("imagen/Filter-List-icon16.png"));
	JButton btnFiltroProducto = new JButton(new ImageIcon("Imagen/Filter-List-icon16.png"));
	JButton btnLimpiarFiltroProducto = new JButton(new ImageIcon("Imagen/clear-brush-broom-sweeping-change-icone-7230-16.png"));
	
//	JButton btnBuscar_Clase = new JButton("",new ImageIcon("imagen/Filter-List-icon16.png"));
	JButton btnFiltroClase = new JButton(new ImageIcon("Imagen/Filter-List-icon16.png"));
	JButton btnLimpiarFiltroClase= new JButton(new ImageIcon("Imagen/clear-brush-broom-sweeping-change-icone-7230-16.png"));
	
//	JButton btnBuscar_Categoria = new JButton("",new ImageIcon("imagen/Filter-List-icon16.png"));
	JButton btnFiltroCategoria = new JButton(new ImageIcon("Imagen/Filter-List-icon16.png"));
	JButton btnLimpiarFiltroCategoria = new JButton(new ImageIcon("Imagen/clear-brush-broom-sweeping-change-icone-7230-16.png"));
	
//	JButton btnBuscar_Familia = new JButton("",new ImageIcon("imagen/Filter-List-icon16.png"));
	JButton btnFiltroFamilia = new JButton(new ImageIcon("Imagen/Filter-List-icon16.png"));
	JButton btnLimpiarFiltroFamilia = new JButton(new ImageIcon("Imagen/clear-brush-broom-sweeping-change-icone-7230-16.png"));
	
//	JButton btnBuscar_Linea = new JButton("",new ImageIcon("imagen/Filter-List-icon16.png"));
	JButton btnFiltroLinea = new JButton(new ImageIcon("Imagen/Filter-List-icon16.png"));
	JButton btnLimpiarFiltroLinea = new JButton(new ImageIcon("Imagen/clear-brush-broom-sweeping-change-icone-7230-16.png"));
	
	JButton btn_buscar = new JButton  ("Buscar",new ImageIcon("imagen/buscar.png"));
	
	JLabel JLBlinicio= new JLabel(new ImageIcon("Imagen/iniciar-icono-4628-16.png") );
	JLabel JLBestablecimiento= new JLabel(new ImageIcon("Imagen/folder-home-home-icone-5663-16.png") );
	JLabel JLBTipoPrecio= new JLabel(new ImageIcon("Imagen/folder-home-home-icone-5663-16.png") );
	
//	JTextField txtcod_prod = new Componentes().text(new JTextField(), "Codigo del Producto", 15, "String");
	JTextField txtFiltroProducto = new JTextField("");
	JTextField txtFiltroClase = new JTextField("");
	JTextField txtFiltroCategoria = new JTextField("");
	JTextField txtFiltroFamilia = new JTextField("");
	JTextField txtFiltroLinea = new JTextField("");
	
	int cantidad_de_columnas =  (new Obj_Reportes_De_Ventas().cantidad_de_competidores()+6);
	DefaultTableModel model = new DefaultTableModel(0,cantidad_de_columnas){
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Class getColumnClass(int columnIndex) {
	             return listacolumnas(cantidad_de_columnas)[columnIndex];
	         }   
		public boolean isCellEditable(int fila, int columna){
			if(columna == 0)
				return true;
			return false;
		}
	};
	
	@SuppressWarnings("rawtypes")
	public Class[] listacolumnas(int Columnas){
	Class[] lista = new Class[Columnas];
	for (int i = 0; i<lista.length; i++){
			lista[i] =(String.class);
	 }
	 return lista;
   };
	
	JTable tabla = new JTable(model);
	private JScrollPane getPanelTabla()	{		
		int a=90,b=300;
		
		tabla.getColumnModel().getColumn(0).setHeaderValue("Cod_Prod");
		tabla.getColumnModel().getColumn(0).setMaxWidth(a);
		tabla.getColumnModel().getColumn(0).setMinWidth(a);
		tabla.getColumnModel().getColumn(1).setHeaderValue("Descripcion");
		tabla.getColumnModel().getColumn(1).setMaxWidth(b*2+a);
		tabla.getColumnModel().getColumn(1).setMinWidth(b+a);
		tabla.getColumnModel().getColumn(2).setHeaderValue("Ultimo Costo");
		tabla.getColumnModel().getColumn(2).setMaxWidth(a);
		tabla.getColumnModel().getColumn(2).setMinWidth(a);
		tabla.getColumnModel().getColumn(3).setHeaderValue("Costo Promedio");
		tabla.getColumnModel().getColumn(3).setMaxWidth(a);
		tabla.getColumnModel().getColumn(3).setMinWidth(a);
		tabla.getColumnModel().getColumn(4).setHeaderValue("Precio De Venta");
		tabla.getColumnModel().getColumn(4).setMaxWidth(a);
		tabla.getColumnModel().getColumn(4).setMinWidth(a);
		tabla.getColumnModel().getColumn(5).setHeaderValue("Markup");
		tabla.getColumnModel().getColumn(5).setMaxWidth(a);
		tabla.getColumnModel().getColumn(5).setMinWidth(a);
		
		try {
			String[] competidor = new Obj_Reportes_De_Ventas().lista_de_competidores();
			for(int i=6; i<cantidad_de_columnas; i++){
				tabla.getColumnModel().getColumn(i).setHeaderValue(competidor[(i-6)].toString());
				tabla.getColumnModel().getColumn(i).setMaxWidth(a);
				tabla.getColumnModel().getColumn(i).setMinWidth(a);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

    	tabla.getTableHeader().setReorderingAllowed(false) ;
    	tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		 JScrollPane scrol = new JScrollPane(tabla);
		   
	    return scrol; 
	}
    
	Border blackline, etched, raisedbevel, loweredbevel, empty;
	
	String parametroGeneral = "";
	String Lista="";
	
	public Cat_Analisis_De_Precios_De_Competencia(String parametro, String operador){
		int ancho = Toolkit.getDefaultToolkit().getScreenSize().width;
		int alto = Toolkit.getDefaultToolkit().getScreenSize().height;
		
		setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds()); 
		
		cont.add(panel);
		setResizable(false);
		setLocationRelativeTo(null);
		setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Sales-by-payment-method-icon-64.png"));
		setTitle("Analisis De Precios De Competencia");
		panel.setBorder(BorderFactory.createTitledBorder("Analisis De Precios De Competencia"));
		
		int x=15 ;
		int y=20 ;
		int l=100;
		int a=20;

		panel.add(new JLabel("Fecha Inicio:")).setBounds(x,y,l,a);
		panel.add(JLBlinicio).setBounds(x+=60,y,a,a);
		panel.add(c_inicio).setBounds(x+=20,y,l-10,a);
		
		x=100;
		panel.add(new JLabel("Filtro De Productos:")).setBounds(x-85,y+=30,l+50,a);
		panel.add(cmbOperador_Productos				).setBounds(x+80,y,l-12,a);
		
        panel.add(txtFiltroProducto					).setBounds(x+170,y,l*4+20,a);
        panel.add(btnFiltroProducto					).setBounds(x+590,y,a,a);
        panel.add(btnLimpiarFiltroProducto			).setBounds(x+613,y,a,a);
        
		panel.add(new JLabel("Filtro De Clase De Productos:")).setBounds(x-85,y+=30,l+50,a); 
		panel.add(cmbOperador_Clase							 ).setBounds(x+80,y,l-12,a);  
		                                                                                  
        panel.add(txtFiltroClase							 ).setBounds(x+170,y,l*4+20,a);  
        panel.add(btnFiltroClase							 ).setBounds(x+590,y,a,a);    
        panel.add(btnLimpiarFiltroClase						 ).setBounds(x+613,y,a,a);    
        
		panel.add(new JLabel("Filtro De Categoria De Productos:")).setBounds(x-85,y+=30,l+70,a); 
		panel.add(cmbOperador_Categoria							 ).setBounds(x+80,y,l-12,a);  
		                                                                                      
        panel.add(txtFiltroCategoria							 ).setBounds(x+170,y,l*4+20,a);  
        panel.add(btnFiltroCategoria							 ).setBounds(x+590,y,a,a);    
        panel.add(btnLimpiarFiltroCategoria						 ).setBounds(x+613,y,a,a);    
        
		panel.add(new JLabel("Filtro Familia De Productos:")).setBounds(x-85,y+=30,l+50,a); 
		panel.add(cmbOperador_Familia						).setBounds(x+80,y,l-12,a);  
		                                                                                 
        panel.add(txtFiltroFamilia							).setBounds(x+170,y,l*4+20,a);  
        panel.add(btnFiltroFamilia							).setBounds(x+590,y,a,a);    
        panel.add(btnLimpiarFiltroFamilia					).setBounds(x+613,y,a,a);    
        
		panel.add(new JLabel("Filtro De Linea De Productos:")).setBounds(x-85,y+=30,l+50,a); 
		panel.add(cmbOperador_Linea					 ).setBounds(x+80,y,l-12,a);  
		                                                                                  
        panel.add(txtFiltroLinea							 ).setBounds(x+170,y,l*4+20,a);  
        panel.add(btnFiltroLinea							 ).setBounds(x+590,y,a,a);    
        panel.add(btnLimpiarFiltroLinea						 ).setBounds(x+613,y,a,a);    
       
        panel.add(btn_buscar).setBounds(x+810,y,l,a);
        
        panel.add(getPanelTabla()).setBounds(10,y+=50,ancho-30,alto-y-75);
        
        c_inicio.setDate(cargar_fechas(0));
        render_tabla();
        
        txtFiltroProducto.setEditable(false); 
        txtFiltroClase.setEditable(false);
        txtFiltroCategoria.setEditable(false);
        txtFiltroFamilia.setEditable(false);
        txtFiltroLinea.setEditable(false);

        String operador_simbolo = "";
        
        if(!parametro.equals("")){
        	
            switch(operador){
	    		case "Igual"		:operador_simbolo=" = "; break;
	    		case "Esta en lista":operador_simbolo=" in "; break;
	    		case "Menor que"	:operador_simbolo=" < "; break;
	    		case "Mayor que"	:operador_simbolo=" > "; break;
	    		case "Diferente"	:operador_simbolo=" <> "; break;
    		}
        	txtFiltroProducto.setText(operador_simbolo+parametro);
        	cmbOperador_Productos.setSelectedItem(operador);
        	
        	panelEnableFalse();
//        	btnFiltroProducto.setEnabled(true);
        	btnLimpiarFiltroProducto.setEnabled(true);
        	btnLimpiarFiltroClase.setEnabled(true);
        	
        }
        
        btnFiltroProducto.addActionListener(op_filtro_productos);
        btnFiltroClase.addActionListener(op_filtro_clases);
        btnFiltroCategoria.addActionListener(op_filtro_categorias);
        btnFiltroFamilia.addActionListener(op_filtro_familias);
        btnFiltroLinea.addActionListener(op_filtro_lineas);
        
		
		btnLimpiarFiltroProducto.addActionListener(limpiar_filtro_productos);
		btnLimpiarFiltroClase.addActionListener(limpiar_filtro_claces);
        btnLimpiarFiltroCategoria.addActionListener(limpiar_filtro_categorias);
        btnLimpiarFiltroFamilia.addActionListener(limpiar_filtro_familias);
        btnLimpiarFiltroLinea.addActionListener(limpiar_filtro_lineas);
	        
		btn_buscar.addActionListener(op_generar);
	}
	
	
	public void render_tabla(){
		for(int i = 0; i < cantidad_de_columnas; i++){
			if(i<=1){
				tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
			}else{
				tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12));
			}
		}
	}

	public void filtroProductos(String cadena){
		txtFiltroProducto.setText(cadena);
	}

	public String validar_fechas(){
		String error = "";
		String fechainicioNull = c_inicio.getDate()+"";
	    if(fechainicioNull.equals("null"))error+= "Fecha  inicio\n";
		
		return error;
	}
	
	public Date cargar_fechas(int dias){
		Date date = null;
				  try {
					date = new SimpleDateFormat("dd/MM/yyyy").parse(new BuscarSQL().fecha(dias));
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		return date;
	};
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void Reporte_de_Asistencia_completo(String fecha_inicio, String fecha_final,String Establecimiento,String Departamento,String folios_empleados){
			
			String query = "exec sp_Reporte_De_Asistencia_Por_Establecimiento '"+fecha_inicio+"','"+fecha_final+"','"+Establecimiento+"','"+Departamento+"','"+folios_empleados+"'";
			Statement stmt = null;
				try {
					
					stmt =  new Connexion().conexion().createStatement();
				    ResultSet rs = stmt.executeQuery(query);
				    
					JasperReport report = JasperCompileManager.compileReport(System.getProperty("user.dir")+"\\src\\Obj_Reportes\\Obj_Reporte_De_Asistencia_Por_Establecimiento.jrxml");
					JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
					JasperPrint print = JasperFillManager.fillReport(report, new HashMap(), resultSetDataSource);
					JasperViewer.viewReport(print, false);
				} catch (Exception e1) {
					System.out.println(e1.getMessage());
					JOptionPane.showMessageDialog(null, "Error En Cat_Reporte_General_de_Asistencia_Por_Establecimiento ", "Error !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
		}
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void Reporte_de_Asistencia_establecimiento(String fecha_inicio, String fecha_final,String Establecimiento,String Departamento,String folios_empleados){
		String query = "exec sp_Reporte_General_de_Asistencia_Por_Establecimiento '"+fecha_inicio+"','"+fecha_final+"','"+Establecimiento+"','"+Departamento+"','"+folios_empleados+"'";
		Statement stmt = null;
		try {
			
			stmt =  new Connexion().conexion().createStatement();
		    ResultSet rs = stmt.executeQuery(query);
		    
			JasperReport report = JasperCompileManager.compileReport(System.getProperty("user.dir")+"\\src\\Obj_Reportes\\Obj_Reporte_de_Asistencia_Por_Establecimiento_Sin_Observaciones.jrxml");
			JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
			JasperPrint print = JasperFillManager.fillReport(report, new HashMap(), resultSetDataSource);
			JasperViewer.viewReport(print, false);
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
			JOptionPane.showMessageDialog(null, "Error En Cat_Reporte_General_de_Asistencia_Por_Establecimiento ", "Error !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
	}
	}
	
	ActionListener op_filtro_productos = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(!cmbOperador_Productos.getSelectedItem().toString().equals("Todos")){
				dispose();
				new Cat_Filtro_De_Busqueda_De_Productos("Reporte_De_Analisis_De_Precios_De_Competencia",cmbOperador_Productos.getSelectedItem().toString()).setVisible(true);
			}else{
				JOptionPane.showMessageDialog(null, "El Operador Para Este Filtro Es ( Todos ) Por Lo Que No Es Necesario Abrir El Filtro", "Aviso !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
				return;
			}
		}
	};
	ActionListener op_filtro_clases = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(!cmbOperador_Clase.getSelectedItem().toString().equals("Todos")){
				new Cat_Filtro_Dinamico(cmbOperador_Clase.getSelectedItem().toString(),"clases_productos","clase_producto").setVisible(true);
			}else{
				JOptionPane.showMessageDialog(null, "El Operador Para Este Filtro Es ( Todos ) Por Lo Que No Es Necesario Abrir El Filtro", "Aviso !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
				return;
			}
		}
	};
	ActionListener op_filtro_categorias = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(!cmbOperador_Categoria.getSelectedItem().toString().equals("Todos")){
				new Cat_Filtro_Dinamico(cmbOperador_Categoria.getSelectedItem().toString(),"categorias","categoria").setVisible(true);
			}else{
				JOptionPane.showMessageDialog(null, "El Operador Para Este Filtro Es ( Todos ) Por Lo Que No Es Necesario Abrir El Filtro", "Aviso !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
				return;
			}
		}
	};
	ActionListener op_filtro_familias = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(!cmbOperador_Familia.getSelectedItem().toString().equals("Todos")){
				new Cat_Filtro_Dinamico(cmbOperador_Familia.getSelectedItem().toString(),"familias","familia").setVisible(true);
			}else{
				JOptionPane.showMessageDialog(null, "El Operador Para Este Filtro Es ( Todos ) Por Lo Que No Es Necesario Abrir El Filtro", "Aviso !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
				return;
			}
		}
	};
	ActionListener op_filtro_lineas = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(!cmbOperador_Linea.getSelectedItem().toString().equals("Todos")){
				new Cat_Filtro_Dinamico(cmbOperador_Linea.getSelectedItem().toString(),"lineas_productos","linea_producto").setVisible(true);
			}else{
				JOptionPane.showMessageDialog(null, "El Operador Para Este Filtro Es ( Todos ) Por Lo Que No Es Necesario Abrir El Filtro", "Aviso !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
				return;
			}
		}
	};
	
//LIMPIAR ----------------------------------------------------------------------------------------------------------------------------	
	ActionListener limpiar_filtro_productos = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
            txtFiltroProducto.setText("");
            txtFiltroClase.setText("");
            txtFiltroCategoria.setText("");
            txtFiltroFamilia.setText("");
            txtFiltroLinea.setText("");
        	panelEnableTrue();
        	Lista="";
        	limpiar_vacios();
		}
	};
	
	ActionListener limpiar_filtro_claces = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
            txtFiltroProducto.setText("");
            txtFiltroClase.setText("");
            txtFiltroCategoria.setText("");
            txtFiltroFamilia.setText("");
            txtFiltroLinea.setText("");
        	panelEnableTrue();
        	Lista="";
        	limpiar_vacios();
		}
	};
	
	ActionListener limpiar_filtro_categorias = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
            
			panelEnableFalse();
			txtFiltroCategoria.setText("");
			limpiar_vacios();
		}
	};
	
	ActionListener limpiar_filtro_familias = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
           
			panelEnableFalse();
			txtFiltroFamilia.setText("");
			limpiar_vacios();
		}
	};
	
	ActionListener limpiar_filtro_lineas = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			panelEnableFalse();
			txtFiltroLinea.setText("");
			limpiar_vacios();
		}
	};
	
	public void limpiar_vacios(){
		panelEnableFalse();
		txtFiltroLinea.setText("");
        
        btnFiltroLinea.setEnabled(true);
        btnLimpiarFiltroLinea.setEnabled(true);
        
        btnLimpiarFiltroFamilia.setEnabled(true);
        
        	if(txtFiltroFamilia.getText().equals("")){
        		btnFiltroFamilia.setEnabled(true);
        		
        		if(txtFiltroCategoria.getText().equals("")){
            		btnFiltroCategoria.setEnabled(true);
            		btnLimpiarFiltroCategoria.setEnabled(true);
            		
            		if(txtFiltroClase.getText().equals("")){
	            		btnFiltroClase.setEnabled(true);
	            		btnLimpiarFiltroClase.setEnabled(true);
	            		
	            		btnFiltroProducto.setEnabled(true);
	            	}
            		btnLimpiarFiltroClase.setEnabled(true);
            	}
        		btnLimpiarFiltroCategoria.setEnabled(true);
        	}
        	btnLimpiarFiltroFamilia.setEnabled(true);
        	
        	
        	cmbOperador_Productos.setSelectedIndex(0);
        	cmbOperador_Clase.setSelectedIndex(0);
        	cmbOperador_Categoria.setSelectedIndex(0);
        	cmbOperador_Familia.setSelectedIndex(0);
        	cmbOperador_Linea.setSelectedIndex(0);
        	
        	Lista="";
	}
	
//	----------------------------------------------------------------------------------------------------------------------------------------
	
	ActionListener op_generar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(validar_fechas().equals("")){
				
				String fecha_inicio = new SimpleDateFormat("dd/MM/yyyy").format(c_inicio.getDate());//+" "+new SimpleDateFormat("hh:mm:ss").format(sphora_inicio.getValue())+" "+sphora_inicio.getValue()+":00";
				
				String productos 	= txtFiltroProducto.getText();
				String clases 		= txtFiltroClase.getText();
				String categorias 	= txtFiltroCategoria.getText();
				String familias 	= txtFiltroFamilia.getText();
				String lineas 		= txtFiltroLinea.getText();
				
				if((productos+clases+categorias+familias+lineas).length() > 0 ){
					
						Obj_Reportes_De_Ventas ventas = new Obj_Reportes_De_Ventas();
						
						ventas.setFecha_inicio(fecha_inicio);
						ventas.setProductos(productos);
						ventas.setClases(clases);
						ventas.setCategorias(categorias);
						ventas.setFamilias(familias);
						ventas.setLineas(lineas);
						
						try {
							while(tabla.getRowCount()>0){model.removeRow(0);}
							
							Object[][] matriz_reporte_de_ventas = ventas.reporte_de_competencias(cantidad_de_columnas);
							
							if(matriz_reporte_de_ventas.length==0){
								JOptionPane.showMessageDialog(null, "No se encontraron registros con las condiciones de busqueda proporcionada","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
								return;
							}else{
								
								 String[] fila = new String[cantidad_de_columnas];
								 
									for(int i=0; i<matriz_reporte_de_ventas.length; i++){
										for(int j=0; j<cantidad_de_columnas; j++){
											fila[j] = matriz_reporte_de_ventas[i][j ]+"";
										}
										model.addRow(fila);
									}
							}
							
						} catch (SQLException e2) {
							e2.printStackTrace();
						}
					} else{
						JOptionPane.showMessageDialog(null, "Para visualizar el reporte es necesario agregar un filtro","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
						return;
					}	
								
			}else{
				JOptionPane.showMessageDialog(null,"Los siguientes campos están vacíos: "+validar_fechas(),"Aviso!", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
	};
	
	public void panelEnableFalse(){
		btnFiltroProducto.setEnabled(false);
		btnFiltroClase.setEnabled(false);
    	btnFiltroCategoria.setEnabled(false);
    	btnFiltroFamilia.setEnabled(false);
    	btnFiltroLinea.setEnabled(false);
    	
    	btnLimpiarFiltroClase.setEnabled(false);
    	btnLimpiarFiltroCategoria.setEnabled(false);
    	btnLimpiarFiltroFamilia.setEnabled(false);
    	btnLimpiarFiltroLinea.setEnabled(false);
	}
	
	public void panelEnableTrue(){
		btnFiltroProducto.setEnabled(true);
		btnFiltroClase.setEnabled(true);
    	btnFiltroCategoria.setEnabled(true);
    	btnFiltroFamilia.setEnabled(true);
    	btnFiltroLinea.setEnabled(true);
    	
    	btnLimpiarFiltroClase.setEnabled(true);
    	btnLimpiarFiltroCategoria.setEnabled(true);
    	btnLimpiarFiltroFamilia.setEnabled(true);
    	btnLimpiarFiltroLinea.setEnabled(true);
	}
	
// FILTRO
		 	public class Cat_Filtro_Dinamico extends JDialog {
				
				Container cont = getContentPane();
				JLayeredPane campo = new JLayeredPane();
				
				Object[][] MatrizFiltro ;
				
				String Operador = "";
				
				DefaultTableModel modeloFiltro = new DefaultTableModel(null,
			            new String[]{"Folio", "Nombre",""}
						){
				     @SuppressWarnings("rawtypes")
					Class[] types = new Class[]{
				    	java.lang.Integer.class,
				    	java.lang.String.class,
				    	java.lang.Boolean.class
			         };
				     @SuppressWarnings({ "rawtypes", "unchecked" })
					public Class getColumnClass(int columnIndex) {
			             return types[columnIndex];
			         }
			         public boolean isCellEditable(int fila, int columna){
			        	 switch(columna){
			        	 	case 0 : return false; 
			        	 	case 1 : return false; 
			        	 	case 2 : return true;
			        	 		
			        	 } 				
			 			return false;
			 		}
			         
			            @Override
			            public void setValueAt(Object value, int row, int col) {
			                super.setValueAt(value, row, col);
			                if(!Operador.equals("Esta en lista")){
			                	if (col == 2 && value.equals(Boolean.TRUE))
			                    deselectValues(row, col);
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
				
				JTable tablaFiltro = new JTable(modeloFiltro);
			    JScrollPane scroll = new JScrollPane(tablaFiltro);
				
				@SuppressWarnings("rawtypes")
				private TableRowSorter trsfiltro;
				
				JTextField txtFolio = new JTextField();
				JTextField txtNombre_Completo = new JTextField();
				
				JButton btnAgregar = new JButton(new ImageIcon("Iconos/agregar.png"));
				
				String folio_columna = "";
				@SuppressWarnings({ "unchecked", "rawtypes" })
				public Cat_Filtro_Dinamico(String operad, String nombre_de_tabla,String folio_colum){
					
					this.setModal(true);
					setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/filter_icon&16.png"));
					setTitle("Filtro de "+nombre_de_tabla);
					campo.setBorder(BorderFactory.createTitledBorder("Seleccion "+folio_colum));
					trsfiltro = new TableRowSorter(modeloFiltro); 
					tablaFiltro.setRowSorter(trsfiltro);  
					
					Operador = operad;
					folio_columna=folio_colum;

					btnAgregar.setToolTipText("Agregar");
					
					campo.add(scroll).setBounds(15,43,364,360);
					
					campo.add(txtFolio).setBounds(15,20,40,20);
					campo.add(txtNombre_Completo).setBounds(56,20,280,20);
					campo.add(btnAgregar).setBounds(340,20,50,20);
					
					cont.add(campo);
					
					while(tablaFiltro.getRowCount()>0){
							modeloFiltro.removeRow(0);
	                }
                
					Object[][] getTablaFiltro = getTablaFiltro(operad,nombre_de_tabla);
					String[] fila = new String[3];
                        for(int i=0; i<getTablaFiltro.length; i++){
                                fila[0] = getTablaFiltro[i][0]+"";
                                fila[1] = getTablaFiltro[i][1]+"";
                                fila[2] = "false";
                                modeloFiltro.addRow(fila);
                        }
					
					llamar_render();
					
					txtFolio.addKeyListener(opFiltroFolio);
					txtNombre_Completo.addKeyListener(opFiltroNombre);
					
					btnAgregar.addActionListener(opAgregar);
					
					setSize(405,450);
					setResizable(false);
					setLocationRelativeTo(null);
				}
				
				public void llamar_render(){
					
					tablaFiltro.getColumnModel().getColumn(0).setMaxWidth(40);
					tablaFiltro.getColumnModel().getColumn(0).setMinWidth(40);
					tablaFiltro.getColumnModel().getColumn(1).setMaxWidth(280);
					tablaFiltro.getColumnModel().getColumn(1).setMinWidth(280);
					tablaFiltro.getColumnModel().getColumn(2).setMaxWidth(30);
					tablaFiltro.getColumnModel().getColumn(2).setMinWidth(30);
					
					tablaFiltro.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",10)); 
					tablaFiltro.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
					tablaFiltro.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("CHB","centro","Arial","normal",12));
				}
				
				ActionListener opAgregar = new ActionListener() {
					@SuppressWarnings({ "unchecked" })
					public void actionPerformed(ActionEvent arg0) {
						
						if(tablaFiltro.isEditing()){
				 			tablaFiltro.getCellEditor().stopCellEditing();
						}
						trsfiltro.setRowFilter(RowFilter.regexFilter("", 0));
						trsfiltro.setRowFilter(RowFilter.regexFilter("", 1));
						
						txtFolio.setText("");
						txtNombre_Completo.setText("");
						
						int contador=0;
				 		 Lista="('";	
				 			for(int i=0; i<tablaFiltro.getRowCount(); i++){
				 				if(Boolean.parseBoolean(modeloFiltro.getValueAt(i, 2).toString()) == true){
				 					String posicion = modeloFiltro.getValueAt(i, 0).toString().trim();
				 					
				 					contador=contador+=1;
				 							if(contador == 1){
				 								Lista=Lista+"'"+posicion+"'";
						 					}else{
						 						Lista=Lista+"',''"+posicion+"'";
						 					}
//				 						}
//				 					}
				 				}
				 			}
				 			
				 			Lista=Lista+"')";

				 			if(Lista.equals("('')")){
				 				JOptionPane.showMessageDialog(null, "Es necesario seleccionar un argunemto", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
	 							return;
				 			}else{
//				 				txtComparacionEmpleado.setText(ListaEmpleados);
//					 			actionAplicar();
				 				
				 		        String operador_simbolo = "";
				 		        
				 		        	
				 		            switch(Operador){
				 			    		case "Igual"		:operador_simbolo=" = "; 
				 			    		panelEnableFalse();
				 			    		parametroGeneral=Lista;
				 			    		
				 			    		break;
				 			    		case "Esta en lista":operador_simbolo=" in "; 
				 			    		panelEnableFalse();
				 			    		
				 			    		break;
				 			    		case "Menor que"	:operador_simbolo=" < "; 
				 			    		panelEnableFalse();
				 			    		
				 			    		break;
				 			    		case "Mayor que"	:operador_simbolo=" > "; 
				 			    		panelEnableFalse();
				 			    		
				 			    		break;
				 			    		case "Diferente"	:operador_simbolo=" <> "; 
				 			    		panelEnableFalse();
				 			    		
				 			    		break;
				 		    		}
				 				Lista=operador_simbolo+Lista;
				 				switch(folio_columna){
				 				           
							 				case "clase_producto":	txtFiltroClase.setText(Lista)		;
							 										if(Operador.equals("Igual")||Operador.equals("Esta en lista")){
							 											btnFiltroCategoria.setEnabled(true);
							 											btnLimpiarFiltroCategoria.setEnabled(true);
							 										}
							 										
							 										btnLimpiarFiltroClase.setEnabled(true);
							 										btnFiltroProducto.setEnabled(false);
							 				break;
							 				
							 				case "categoria":		txtFiltroCategoria.setText(Lista)	;
													 				if(Operador.equals("Igual")||Operador.equals("Esta en lista")){
							 											btnFiltroFamilia.setEnabled(true);
							 											btnLimpiarFiltroFamilia.setEnabled(true);
							 										}
													 				btnLimpiarFiltroCategoria.setEnabled(true);
													 				btnFiltroProducto.setEnabled(false);
							 				break;
							 				
							 				
							 				case "familia":			txtFiltroFamilia.setText(Lista)		;
													 				if(Operador.equals("Igual")||Operador.equals("Esta en lista")){
							 											btnFiltroLinea.setEnabled(true);
							 											btnLimpiarFiltroLinea.setEnabled(true);
							 										}
													 				btnLimpiarFiltroFamilia.setEnabled(true);
													 				btnFiltroProducto.setEnabled(false);
							 				break;
							 				
							 				case "linea_producto":	txtFiltroLinea.setText(Lista)		;
							 				
							 										btnLimpiarFiltroLinea.setEnabled(true);
													 				btnFiltroProducto.setEnabled(false);
	 										break;
				 				}
					 			dispose();
				 			}
					}
				};
				
				
				KeyListener opFiltroFolio = new KeyListener(){
					@SuppressWarnings("unchecked")
					public void keyReleased(KeyEvent arg0) {
						trsfiltro.setRowFilter(RowFilter.regexFilter(txtFolio.getText(), 0));
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
				
				KeyListener opFiltroNombre = new KeyListener(){
					@SuppressWarnings("unchecked")
					public void keyReleased(KeyEvent arg0) {
						trsfiltro.setRowFilter(RowFilter.regexFilter(txtNombre_Completo.getText().toUpperCase().trim(), 1));
					}
					public void keyTyped(KeyEvent arg0) {}
					public void keyPressed(KeyEvent arg0) {}		
				};
				
				
			   	public Object[][] getTablaFiltro(String operador, String nombre_de_tabla){
			   		String condicion = "";
			   		
			   		if(!Lista.equals("")){
			   			
			   			condicion = " where jerarquia "+Lista.replace("''","'");
			   		}
			   		
			   		
					String todos = "select "+folio_columna+" as folio,nombre from "+nombre_de_tabla+condicion+" order by nombre";
					
					System.out.println(todos);
					Statement s;
					ResultSet rs;
					try {
						s = new Connexion().conexion_IZAGAR().createStatement();
						rs = s.executeQuery(todos);
						
						MatrizFiltro = new Object[getFilas(todos)][3];
						int i=0;
						while(rs.next()){
							String folio = rs.getString(1);
							MatrizFiltro[i][0] = folio+"  ";
							MatrizFiltro[i][1] = "   "+rs.getString(2).trim();
							MatrizFiltro[i][2] = false;
							i++;
						}
						Lista="";
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				    return MatrizFiltro; 
				}
			   	
			   	public int getFilas(String qry){
					int filas=0;
					Statement stmt = null;
					try {
						stmt = new Connexion().conexion_IZAGAR().createStatement();
						ResultSet rs = stmt.executeQuery(qry);
						while(rs.next()){
							filas++;
						}
						
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					return filas;
				}	

				KeyListener validaCantidad = new KeyListener() {
					@Override
					public void keyTyped(KeyEvent e){
						char caracter = e.getKeyChar();				
						if(((caracter < '0') ||	
						    	(caracter > '9')) && 
						    	(caracter != '.' )){
						    	e.consume();
						    	}
					}
					@Override
					public void keyReleased(KeyEvent e) {	
					}
					@Override
					public void keyPressed(KeyEvent arg0) {
					}	
				};
				
				KeyListener validaNumericoConPunto = new KeyListener() {
					@Override
					public void keyTyped(KeyEvent e) {
						char caracter = e.getKeyChar();
						
					    if(((caracter < '0') ||	
					    	(caracter > '9')) && 
					    	(caracter != '.')){
					    	e.consume();
					    	}
					}
					@Override
					public void keyPressed(KeyEvent e){}
					@Override
					public void keyReleased(KeyEvent e){}
											
				};
				
			}
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Analisis_De_Precios_De_Competencia("","Todos").setVisible(true);
		}catch(Exception e){	}
	}

}
