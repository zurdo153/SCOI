package Cat_Marketing;

import java.awt.Container;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

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

import Cat_Filtros_IZAGAR.Cat_Filtro_De_Busqueda_De_Productos;
import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Connexion;
import Obj_Marketing.Obj_Configuracion_Meta_Mensual_De_Ventas;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.Obj_Filtro_Dinamico;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Configuracion_Meta_Mensual_De_Ventas extends JFrame {
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	Connexion con = new Connexion();
	
	JTextField txtNombre = new Componentes().text(new JTextField(), "Nombre De La Configuracion", 250, "String");
	
	@SuppressWarnings("rawtypes")
	Vector tipo_de_reporte = new BuscarSQL().buscarTipoDeReporteDeMetaMensualDeVentas();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbTipoDeReporte = new JComboBox(tipo_de_reporte);
	
	String operador[] = {"Todos","Igual","Esta en lista","Menor que","Mayor que","Diferente"};
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbEstablecimiento = new JComboBox(operador);
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbOperador_Productos = new JComboBox(operador);
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbOperador_Clase = new JComboBox(operador);
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbOperador_Categoria= new JComboBox(operador);
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbOperador_Familia = new JComboBox(operador);
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbOperador_Linea = new JComboBox(operador);
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbOperador_Talla = new JComboBox(operador);
	
	JButton btnFiltroEstablecimiento = new JButton(new ImageIcon("Imagen/Filter-List-icon16.png"));
	JButton btnLimpiarFiltroEstablecimiento = new JButton(new ImageIcon("Imagen/clear-brush-broom-sweeping-change-icone-7230-16.png"));
	
	JButton btnFiltroProducto = new JButton(new ImageIcon("Imagen/Filter-List-icon16.png"));
	JButton btnLimpiarFiltroProducto = new JButton(new ImageIcon("Imagen/clear-brush-broom-sweeping-change-icone-7230-16.png"));
	
	JButton btnFiltroClase = new JButton(new ImageIcon("Imagen/Filter-List-icon16.png"));
	JButton btnLimpiarFiltroClase= new JButton(new ImageIcon("Imagen/clear-brush-broom-sweeping-change-icone-7230-16.png"));
	
	JButton btnFiltroCategoria = new JButton(new ImageIcon("Imagen/Filter-List-icon16.png"));
	JButton btnLimpiarFiltroCategoria = new JButton(new ImageIcon("Imagen/clear-brush-broom-sweeping-change-icone-7230-16.png"));
	
	JButton btnFiltroFamilia = new JButton(new ImageIcon("Imagen/Filter-List-icon16.png"));
	JButton btnLimpiarFiltroFamilia = new JButton(new ImageIcon("Imagen/clear-brush-broom-sweeping-change-icone-7230-16.png"));
	
	JButton btnFiltroLinea = new JButton(new ImageIcon("Imagen/Filter-List-icon16.png"));
	JButton btnLimpiarFiltroLinea = new JButton(new ImageIcon("Imagen/clear-brush-broom-sweeping-change-icone-7230-16.png"));
	
	JButton btnFiltroTalla = new JButton(new ImageIcon("Imagen/Filter-List-icon16.png"));
	JButton btnLimpiarFiltroTalla = new JButton(new ImageIcon("Imagen/clear-brush-broom-sweeping-change-icone-7230-16.png"));
	
	JButton btnGuardar = new JCButton  ("Guardar","guardar.png","Azul");
	JButton btnCancelar = new JCButton  ("Cancelar","eliminar.png","Azul");
	
	JLabel JLBestablecimiento= new JLabel(new ImageIcon("Imagen/folder-home-home-icone-5663-16.png") );
	JLabel JLBTipoPrecio= new JLabel(new ImageIcon("Imagen/precio-marcado-icono-6652-16.png") );
	JLabel JLBPresentado= new JLabel(new ImageIcon("Imagen/las-preferencias-de-tema-de-escritorio-icono-8603-16.png") );
	
	JTextField txtFiltroEstablecimiento = new JTextField("");
	JTextField txtFiltroProducto = new JTextField("");
	JTextField txtFiltroClase = new JTextField("");
	JTextField txtFiltroCategoria = new JTextField("");
	JTextField txtFiltroFamilia = new JTextField("");
	JTextField txtFiltroLinea = new JTextField("");
	JTextField txtFiltroTalla = new JTextField("");
	
	DefaultTableModel modelo = new DefaultTableModel(null,
            new String[]{"Folio"
							,"Nombre"
							,"Establecimiento"
							,"Tipo De Reporte"
							,"Filtro Producto"
							,"Filtro Clase"
							,"Filtro Categoria"
							,"Filtro Familia"
							,"Filtro Linea"
							,"Filtro Talla"
							,"Status"
							,"Usuario"
							,"Fecha Guardado"
							,"Usuario Cancelo"
							,"Fecha Cancelacion"}
			){
	     @SuppressWarnings("rawtypes")
		Class[] types = new Class[]{
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class };
	     
	     @SuppressWarnings({ "rawtypes", "unchecked" })
		public Class getColumnClass(int columnIndex) {
             return types[columnIndex];
         }
         public boolean isCellEditable(int fila, int columna){
        	 switch(columna){
        	 	case 0  : return false; 
        	 	case 1  : return false; 
        	 	case 2  : return false; 
        	 	case 3  : return false; 
        	 	case 4  : return false;
        	 	case 5  : return false;
        	 	case 6  : return false;
        	 	case 7  : return false; 
        	 	case 8  : return false; 
        	 	case 9  : return false; 
        	 	case 10 : return false; 
        	 	case 11 : return false;
        	 	case 12 : return false;
        	 	case 13 : return false;
        	 	case 14 : return false; 
        	 } 				
 			return false;
 		}
	};
    JTable tabla = new JTable(modelo);
    JScrollPane scrollExist_Estab = new JScrollPane(tabla);
    
	Border blackline, etched, raisedbevel, loweredbevel, empty;
	
	String parametroGeneral = "";
	String Lista="";
	
	String Nombre_Catalogo_Para_Filtro = "";
	
	public Cat_Configuracion_Meta_Mensual_De_Ventas(String parametro, String operador){
		
		int ancho = Toolkit.getDefaultToolkit().getScreenSize().width;
		int alto = Toolkit.getDefaultToolkit().getScreenSize().height;
		setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds()); 
		cont.add(panel);
		setResizable(false);
		setLocationRelativeTo(null);
		setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Sales-by-payment-method-icon-64.png"));
		setTitle("Configuracion De Meta Mensual De Ventas");
		panel.setBorder(BorderFactory.createTitledBorder("Configuracion De Meta Mensual De Ventas"));
		
		int x=100 ;
		int y=20 ;
		int l=100;
		int a=20;

		panel.add(new JLabel("Filtro De Productos:")).setBounds(x-85,y,l+50,a);
		panel.add(cmbOperador_Productos				).setBounds(x+80,y,l-12,a);
        panel.add(txtFiltroProducto					).setBounds(x+170,y,l*4+20,a);
        panel.add(btnFiltroProducto					).setBounds(x+590,y,a,a);
        panel.add(btnLimpiarFiltroProducto			).setBounds(x+613,y,a,a);
        
		panel.add(new JLabel("Establecimiento:")).setBounds(x+650,y,l+50,a);
	    panel.add(JLBestablecimiento).setBounds(x+740,y,a,a);
		panel.add(cmbEstablecimiento).setBounds(x+760,y,l-12,a);
		panel.add(txtFiltroEstablecimiento					 ).setBounds(x+850,y,l*4+20,a);  
        panel.add(btnFiltroEstablecimiento					 ).setBounds(x+l*4+870,y,a,a);    
        panel.add(btnLimpiarFiltroEstablecimiento			 ).setBounds(x+l*4+893,y,a,a);

        
		panel.add(new JLabel("Filtro De Clase De Productos:")).setBounds(x-85,y+=30,l+50,a); 
		panel.add(cmbOperador_Clase							 ).setBounds(x+80,y,l-12,a);  
        panel.add(txtFiltroClase							 ).setBounds(x+170,y,l*4+20,a);  
        panel.add(btnFiltroClase							 ).setBounds(x+590,y,a,a);    
        panel.add(btnLimpiarFiltroClase						 ).setBounds(x+613,y,a,a);
        
        panel.add(new JLabel("Tipo De Reporte:")).setBounds(x+650,y,l+50,a);
	    panel.add(JLBPresentado).setBounds(x+740,y,a,a);
		panel.add(cmbTipoDeReporte).setBounds(x+760,y,l*5+10,a);
        
		panel.add(new JLabel("Filtro De Categoria De Productos:")).setBounds(x-85,y+=30,l+70,a); 
		panel.add(cmbOperador_Categoria							 ).setBounds(x+80,y,l-12,a);  
        panel.add(txtFiltroCategoria							 ).setBounds(x+170,y,l*4+20,a);  
        panel.add(btnFiltroCategoria							 ).setBounds(x+590,y,a,a);    
        panel.add(btnLimpiarFiltroCategoria						 ).setBounds(x+613,y,a,a);  
        
		panel.add(new JLabel("Guardar Como:"	   )).setBounds(x+650,y,l+50,a);
		panel.add(txtNombre							).setBounds(x+760,y,l*5+10,a);

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
        
        panel.add(btnGuardar).setBounds(x+650,y,l,a);
        
        panel.add(new JLabel("Filtro De Talla De Productos:")).setBounds(x-85,y+=30,l+50,a); 
		panel.add(cmbOperador_Talla							 ).setBounds(x+80,y,l-12,a);  
        panel.add(txtFiltroTalla							 ).setBounds(x+170,y,l*4+20,a);  
        panel.add(btnFiltroTalla							 ).setBounds(x+590,y,a,a);    
        panel.add(btnLimpiarFiltroTalla						 ).setBounds(x+613,y,a,a); 
        
        panel.add(btnCancelar).setBounds(x+650,y,l,a);
        
        panel.add(Tabla()).setBounds(10,y+=30,ancho-30,alto-y-75);
        
        Nombre_Catalogo_Para_Filtro=this.getClass().getSimpleName();
        
        render_tabla();
        
        bucar_configuraciones();
        
        txtFiltroEstablecimiento.setEditable(false);
        
        txtFiltroProducto.setEditable(false); 
        txtFiltroClase.setEditable(false);
        txtFiltroCategoria.setEditable(false);
        txtFiltroFamilia.setEditable(false);
        txtFiltroLinea.setEditable(false);
        txtFiltroTalla.setEditable(false);

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
        	btnLimpiarFiltroProducto.setEnabled(true);
        	btnLimpiarFiltroClase.setEnabled(true);
        	
        }
        
        btnFiltroEstablecimiento.addActionListener(op_filtro_establecimientos);
        btnFiltroProducto.addActionListener(op_filtro_productos);
        btnFiltroClase.addActionListener(op_filtro_clases);
        btnFiltroCategoria.addActionListener(op_filtro_categorias);
        btnFiltroFamilia.addActionListener(op_filtro_familias);
        btnFiltroLinea.addActionListener(op_filtro_lineas);
        btnFiltroTalla.addActionListener(op_filtro_talla);
		
        btnLimpiarFiltroEstablecimiento.addActionListener(limpiar_filtro_establecimiento);
		btnLimpiarFiltroProducto.addActionListener(limpiar_filtro_productos);
		btnLimpiarFiltroClase.addActionListener(limpiar_filtro_claces);
        btnLimpiarFiltroCategoria.addActionListener(limpiar_filtro_categorias);
        btnLimpiarFiltroFamilia.addActionListener(limpiar_filtro_familias);
        btnLimpiarFiltroLinea.addActionListener(limpiar_filtro_lineas);
        btnLimpiarFiltroTalla.addActionListener(limpiar_filtro_talla);
        
        btnGuardar.addActionListener(opGuardar);
        btnCancelar.addActionListener(opCancelar);
		
	}
	
	
	public void render_tabla(){
//		tabla.getColumnModel().getColumn(# columna).setCellRenderer(new CellRenderer("tipo_de_valor","alineacion","tipo_de_letra","negrita",# tamanio_fuente));
				
			tabla.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","centro","Arial","normal",12)); 
			tabla.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","centro","Arial","normal",12));
			tabla.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12));
			tabla.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
			tabla.getColumnModel().getColumn(4).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12));
			tabla.getColumnModel().getColumn(5).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12));
			tabla.getColumnModel().getColumn(6).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
			
			tabla.getColumnModel().getColumn(7).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
			tabla.getColumnModel().getColumn(8).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
			tabla.getColumnModel().getColumn(9).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
			tabla.getColumnModel().getColumn(10).setCellRenderer(new tablaRenderer("texto","centro","Arial","normal",12));
			tabla.getColumnModel().getColumn(11).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12));
			tabla.getColumnModel().getColumn(12).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12));
			tabla.getColumnModel().getColumn(13).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12));
			
			tabla.getColumnModel().getColumn(14).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12)); 
	}
	
	
private JScrollPane Tabla()	{		
	
	this.tabla.getTableHeader().setReorderingAllowed(false) ;
	this.tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
	int largo=100;
	tabla.getColumnModel().getColumn(0).setMinWidth(largo/2);
	tabla.getColumnModel().getColumn(1).setMinWidth(largo*3);
	tabla.getColumnModel().getColumn(2).setMinWidth(largo*2);
	tabla.getColumnModel().getColumn(3).setMinWidth(largo);
	tabla.getColumnModel().getColumn(4).setMinWidth(largo);
	tabla.getColumnModel().getColumn(5).setMinWidth(largo);
	tabla.getColumnModel().getColumn(6).setMinWidth(largo);
	tabla.getColumnModel().getColumn(7).setMinWidth(largo);
	tabla.getColumnModel().getColumn(8).setMinWidth(largo);
	tabla.getColumnModel().getColumn(9).setMinWidth(largo);
	tabla.getColumnModel().getColumn(10).setMinWidth(largo);
	tabla.getColumnModel().getColumn(11).setMinWidth(largo);
	tabla.getColumnModel().getColumn(12).setMinWidth(largo);
	tabla.getColumnModel().getColumn(13).setMinWidth(largo);
	tabla.getColumnModel().getColumn(14).setMinWidth(largo);
	
		JScrollPane scrol = new JScrollPane(tabla);
	    return scrol; 
}

public void bucar_configuraciones(){
	
	modelo.setRowCount(0);
	String[][] config_metas = new Obj_Configuracion_Meta_Mensual_De_Ventas().configuraciones_de_meta_mensual_de_ventas();
	
	for(String[] conf: config_metas){
		modelo.addRow(conf);
	}
}

public void filtroProductos(String cadena){
	txtFiltroProducto.setText(cadena);
}

	ActionListener op_filtro_establecimientos = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(!cmbEstablecimiento.getSelectedItem().toString().equals("Todos")){
				new Cat_Filtro_Dinamico(cmbEstablecimiento.getSelectedItem().toString(),"establecimientos","cod_estab").setVisible(true);			
			}else{
				JOptionPane.showMessageDialog(null, "El Operador Para Este Filtro Es ( Todos ) Por Lo Que No Es Necesario Abrir El Filtro", "Aviso !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}
		}
	};

	ActionListener op_filtro_productos = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(!cmbOperador_Productos.getSelectedItem().toString().equals("Todos")){
				dispose();
				new Cat_Filtro_De_Busqueda_De_Productos("Reporte_De_Ventas",cmbOperador_Productos.getSelectedItem().toString(),"",null).setVisible(true);
			}else{
				JOptionPane.showMessageDialog(null, "El Operador Para Este Filtro Es ( Todos ) Por Lo Que No Es Necesario Abrir El Filtro", "Aviso !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}
		}
	};
	
	ActionListener op_filtro_clases = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(!cmbOperador_Clase.getSelectedItem().toString().equals("Todos")){
				Lista="";
				new Cat_Filtro_Dinamico(cmbOperador_Clase.getSelectedItem().toString(),"clases_productos","clase_producto").setVisible(true);
			}else{
				JOptionPane.showMessageDialog(null, "El Operador Para Este Filtro Es ( Todos ) Por Lo Que No Es Necesario Abrir El Filtro", "Aviso !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}
		}
	};
	ActionListener op_filtro_categorias = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(!cmbOperador_Categoria.getSelectedItem().toString().equals("Todos")){
				new Cat_Filtro_Dinamico(cmbOperador_Categoria.getSelectedItem().toString(),"categorias","categoria").setVisible(true);
			}else{
				JOptionPane.showMessageDialog(null, "El Operador Para Este Filtro Es ( Todos ) Por Lo Que No Es Necesario Abrir El Filtro", "Aviso !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}
		}
	};
	ActionListener op_filtro_familias = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(!cmbOperador_Familia.getSelectedItem().toString().equals("Todos")){
				new Cat_Filtro_Dinamico(cmbOperador_Familia.getSelectedItem().toString(),"familias","familia").setVisible(true);
			}else{
				JOptionPane.showMessageDialog(null, "El Operador Para Este Filtro Es ( Todos ) Por Lo Que No Es Necesario Abrir El Filtro", "Aviso !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}
		}
	};
	
	ActionListener op_filtro_lineas = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(!cmbOperador_Linea.getSelectedItem().toString().equals("Todos")){
				new Cat_Filtro_Dinamico(cmbOperador_Linea.getSelectedItem().toString(),"lineas_productos","linea_producto").setVisible(true);
			}else{
				JOptionPane.showMessageDialog(null, "El Operador Para Este Filtro Es ( Todos ) Por Lo Que No Es Necesario Abrir El Filtro", "Aviso !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}
		}
	};
	
	ActionListener op_filtro_talla = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(!cmbOperador_Talla.getSelectedItem().toString().equals("Todos")){
				new Cat_Filtro_Dinamico(cmbOperador_Talla.getSelectedItem().toString(),"tallas","talla").setVisible(true);
			}else{
				JOptionPane.showMessageDialog(null, "El Operador Para Este Filtro Es ( Todos ) Por Lo Que No Es Necesario Abrir El Filtro", "Aviso !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}
		}
	};
	
//LIMPIAR ----------------------------------------------------------------------------------------------------------------------------	
	ActionListener limpiar_filtro_establecimiento = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
            txtFiltroEstablecimiento.setText("");
            cmbEstablecimiento.setSelectedIndex(0);
		}
	};
	
	ActionListener limpiar_filtro_productos = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
            txtFiltroProducto.setText("");
            txtFiltroClase.setText("");
            txtFiltroCategoria.setText("");
            txtFiltroFamilia.setText("");
            txtFiltroLinea.setText("");
        	panelEnableTrue();
        	Lista="";
        	limpiar_vacios("");
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
        	limpiar_vacios("");
		}
	};
	
	ActionListener limpiar_filtro_categorias = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
            
			panelEnableFalse();
			txtFiltroCategoria.setText("");
			limpiar_vacios("");
		}
	};
	
	ActionListener limpiar_filtro_familias = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
           
			panelEnableFalse();
			txtFiltroFamilia.setText("");
			limpiar_vacios("");
		}
	};
	
	ActionListener limpiar_filtro_lineas = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			panelEnableFalse();
			txtFiltroLinea.setText("");
			limpiar_vacios("");
            	
		}
	};
	
	ActionListener limpiar_filtro_talla = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			panelEnableFalse();
			txtFiltroTalla.setText("");
			limpiar_vacios("boton talla");
			
            	
		}
	};
	
	public void limpiar_vacios(String boton){
		
		if(!boton.equals("boton talla")){
			panelEnableFalse();
		}
		
		
		txtFiltroLinea.setText("");
        btnFiltroLinea.setEnabled(true);
        btnLimpiarFiltroLinea.setEnabled(true);
        btnLimpiarFiltroFamilia.setEnabled(true);
        
        btnFiltroTalla.setEnabled(true);
        btnLimpiarFiltroTalla.setEnabled(true);
        
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
	
	ActionListener opGuardar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
				String guardarComo = txtNombre.getText().toUpperCase().trim();
				String establecimiento = cmbEstablecimiento.getSelectedItem().toString().toUpperCase().equals("TODOS")?"TODOS":txtFiltroEstablecimiento.getText();
				String tipoReporte = cmbTipoDeReporte.getSelectedItem().toString();
				
				System.out.println("!"+tipoReporte+"!");
				
				String productos 	= txtFiltroProducto.getText().trim();
				String clases 		= txtFiltroClase.getText().trim();
				String categorias 	= txtFiltroCategoria.getText().trim();
				String familias 	= txtFiltroFamilia.getText().trim();
				String lineas 		= txtFiltroLinea.getText().trim();
				String tallas 		= txtFiltroTalla.getText().trim();
				
				Obj_Configuracion_Meta_Mensual_De_Ventas conf = new Obj_Configuracion_Meta_Mensual_De_Ventas();
				
//				sin nombre
				if(!txtNombre.getText().trim().equals("")){
//					nombre repetido
					if(conf.existe_nombre(txtNombre.getText().trim().toUpperCase())){
							if((productos+clases+categorias+familias+lineas+tallas).length()>0){
								conf.setNombre(guardarComo);
								conf.setEstablecimiento(establecimiento);
								conf.setTipo_de_reporte(tipoReporte);
								
								conf.setProductos(productos);
								conf.setClases(clases);
								conf.setCategorias(categorias);
								conf.setFamilias(familias);
								conf.setLineas(lineas);
								conf.setTallas(tallas);
								
								if(conf.guardar_configuracion_de_meta_mensual_de_ventas("GUARDAR",0)){
									bucar_configuraciones();
								}else{
									JOptionPane.showMessageDialog(null, "No Se Puede Gaurdar La Configuracion De La Meta Mensual De Ventas", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
									return;
								}
								
							}else{
								JOptionPane.showMessageDialog(null, "Es Necesario Capturar Cuandomenos Un Clasificador Para Poder Guardar", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
								return;
							}
					
					}else{
						JOptionPane.showMessageDialog(null, "Ya Existe Una Configuracion Guardada Con Este Nombre", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
						return;
					}
				}else{
					JOptionPane.showMessageDialog(null, "Favor De Poner Un Nombre En: [Guardar Como]", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
					return;
				}

		}
	};
	
	ActionListener opCancelar = new ActionListener(){
		public void actionPerformed(ActionEvent arg0){
			
			Obj_Configuracion_Meta_Mensual_De_Ventas conf = new Obj_Configuracion_Meta_Mensual_De_Ventas();
			
			if(tabla.getSelectedRow()>=0){
				
//				mark.setFolio(Integer.valueOf(tabla.getValueAt(tabla.getSelectedRow(), 0).toString().trim()));
				
				if(JOptionPane.showConfirmDialog(null, "Estas Seguro De Cancelar La Configuracion De La Meta Mensual De Venta Seleccionada?") == 0){
					if(conf.guardar_configuracion_de_meta_mensual_de_ventas("CANCELAR", Integer.valueOf(tabla.getValueAt(tabla.getSelectedRow(), 0).toString().trim()))){
						
						bucar_configuraciones();
						
						JOptionPane.showMessageDialog(null, "La Configuracion De La Meta Mensual Se Cancelo Correctamente","Aviso", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/aplicara-el-dialogo-icono-6256-32.png"));
						return;
					}else{
						JOptionPane.showMessageDialog(null, "No Se A Podido Cancelar La Configuracion De La Meta Mensual", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
						return;
					} 
				}else{
					return;
				}
				
			}else{
				JOptionPane.showMessageDialog(null, "Para Cancelar Una Configuracion Es Necesario Seleccionarla De La Tabla", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
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
    	btnLimpiarFiltroTalla.setEnabled(false);
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
    	btnLimpiarFiltroTalla.setEnabled(true);
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
					
					modeloFiltro.setRowCount(0);
                
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
					
					 this.addWindowListener(new WindowAdapter() {
		                    public void windowOpened( WindowEvent e ){
		                    	txtNombre_Completo.requestFocus();
		                 }
		            });
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
						txtNombre_Completo.setText("");
				 		new Obj_Filtro_Dinamico(tablaFiltro, "Nombre", "", "", "", "", "", "", "");
				 		
						if(tablaFiltro.isEditing()){
				 			tablaFiltro.getCellEditor().stopCellEditing();
						}
						trsfiltro.setRowFilter(RowFilter.regexFilter("", 0));
						txtFolio.setText("");
						
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
				 				}
				 			}
				 			Lista=Lista+"')";

				 			if(Lista.equals("('')")){
				 				JOptionPane.showMessageDialog(null, "Es necesario seleccionar un argunemto", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
	 							return;
				 			}else{
				 		        String operador_simbolo = "";
				 		        
				 		       if(!folio_columna.equals("cod_estab")){
				 		        	 panelEnableFalse();
				 		        }
				 		        	
				 		            switch(Operador){
				 			    		case "Igual"		:operador_simbolo=" = "; 
				 			    		parametroGeneral=Lista;
				 			    		
				 			    		break;
				 			    		case "Esta en lista":operador_simbolo=" in "; 
				 			    		
				 			    		break;
				 			    		case "Menor que"	:operador_simbolo=" < "; 
				 			    		
				 			    		break;
				 			    		case "Mayor que"	:operador_simbolo=" > "; 
				 			    		
				 			    		break;
				 			    		case "Diferente"	:operador_simbolo=" <> "; 
				 			    		
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
	 										
							 				case "talla":			txtFiltroTalla.setText(Lista)		;
												 					if(!txtFiltroTalla.getText().equals("")){
												 						txtFiltroProducto.setText("");
												 						txtFiltroCategoria.setText("");
												 						txtFiltroClase.setText("");
												 						txtFiltroFamilia.setText("");
												 						txtFiltroLinea.setText("");
												 						
												 						btnFiltroProducto.setEnabled(false);
												 						btnFiltroCategoria.setEnabled(false);
							 											btnFiltroClase.setEnabled(false);
							 											btnFiltroFamilia.setEnabled(false);
								 										btnFiltroLinea.setEnabled(false);
								 										btnFiltroTalla.setEnabled(false);
								 										
								 										btnLimpiarFiltroTalla.setEnabled(true);
							 										}
											break;
											
							 				case "cod_estab":			txtFiltroEstablecimiento.setText(Lista)		; break;
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
					public void keyReleased(KeyEvent arg0) {
						new Obj_Filtro_Dinamico(tablaFiltro,"Nombre", txtNombre_Completo.getText().toUpperCase(),"","", "", "", "", "");
					}
					public void keyTyped(KeyEvent arg0) {}
					public void keyPressed(KeyEvent arg0) {}		
				};
				
				
			   	public Object[][] getTablaFiltro(String operador, String nombre_de_tabla){
			   		String condicion = "";
			   		
			   		if(!Lista.equals("")){
			   			if(nombre_de_tabla.equals("establecimientos")){
				   			condicion = " where cod_estab not "+Lista.replace("''","'");
				   		}else{
				   			condicion = " where jerarquia "+Lista.replace("''","'");
				   		}
			   			
			   		}
			   		
					String todos = "select "+folio_columna+" as folio,upper(nombre) from "+nombre_de_tabla+condicion+" order by nombre";
					
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
			new Cat_Configuracion_Meta_Mensual_De_Ventas("","Todos").setVisible(true);
		}catch(Exception e){	}
	}

}
