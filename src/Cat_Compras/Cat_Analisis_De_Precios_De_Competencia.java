package Cat_Compras;

import java.awt.Color;
import java.awt.Component;
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
import java.text.SimpleDateFormat;

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
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

import Cat_Filtros_IZAGAR.Cat_Filtro_De_Busqueda_De_Productos;
import Conexiones_SQL.Connexion;
import Obj_Compras.Obj_Cotizaciones_De_Un_Producto;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_Filtro_Dinamico_Plus;
import Obj_Renders.tablaRenderer;
import Obj_Reportes.Obj_Reportes_De_Ventas;

import com.toedter.calendar.JDateChooser;

@SuppressWarnings("serial")
public class Cat_Analisis_De_Precios_De_Competencia extends JFrame {
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	Connexion con = new Connexion();
	
	JDateChooser c_inicio = new Componentes().jchooser(new JDateChooser()  ,"",0);
	
	String operadorgeneral[] = {"Todos","Igual","Esta en lista","Menor que","Mayor que","No Este en Lista"};
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbOperador_Productos = new JComboBox(operadorgeneral);
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbOperador_Clase = new JComboBox    (operadorgeneral);

	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbOperador_Categoria= new JComboBox (operadorgeneral);
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbOperador_Familia = new JComboBox  (operadorgeneral);
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbOperador_Linea = new JComboBox    (operadorgeneral);
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbOperador_Talla = new JComboBox    (operadorgeneral);
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbOperador_Localizacion = new JComboBox(operadorgeneral);
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbOperador_Pasillo = new JComboBox  (operadorgeneral);
	
	
	String establecimientosbms[] = new Obj_Establecimiento().Combo_Establecimiento201();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbEstablecimiento = new JComboBox(establecimientosbms);
	
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
	
	JButton btnFiltroLocalizaciones = new JButton(new ImageIcon("Imagen/Filter-List-icon16.png"));
	JButton btnLimpiarFiltroLocalizaciones = new JButton(new ImageIcon("Imagen/clear-brush-broom-sweeping-change-icone-7230-16.png"));
	
	JButton btnFiltroPasillo = new JButton(new ImageIcon("Imagen/Filter-List-icon16.png"));
	JButton btnLimpiarFiltroPasillo = new JButton(new ImageIcon("Imagen/clear-brush-broom-sweeping-change-icone-7230-16.png"));
	
	JCButton btn_buscar             = new JCButton("Buscar Productos"  ,"buscar.png"                         ,"Azul");
	JCButton btn_buscar_ultimos_mov = new JCButton(""  ,"editar-sustituir-la-busqueda-icono-8072-16.png"     ,"Azul");
	
	JLabel JLBlinicio= new JLabel(new ImageIcon("Imagen/iniciar-icono-4628-16.png") );
	JLabel JLBestablecimiento= new JLabel(new ImageIcon("Imagen/folder-home-home-icone-5663-16.png") );
	JLabel JLBTipoPrecio= new JLabel(new ImageIcon("Imagen/folder-home-home-icone-5663-16.png") );
	JLabel JLBdescripcion= new JLabel();
	
	JTextField txtcod_prod     = new Componentes().text(new JCTextField()  , "Codigo del Producto"    , 20      , "String");
	
	JTextField txtFiltroProducto     = new Componentes().text(new JCTextField()  , "Filtro Producto"    , 500      , "String");
	JTextField txtFiltroClase        = new Componentes().text(new JCTextField()  , "Filtro Clase   "    , 500      , "String");
	JTextField txtFiltroCategoria    = new Componentes().text(new JCTextField()  , "Filtro Categoria"   , 500      , "String");
	JTextField txtFiltroFamilia      = new Componentes().text(new JCTextField()  , "Filtro Familia"     , 500      , "String");
	JTextField txtFiltroTalla        = new Componentes().text(new JCTextField()  , "Filtro Talla"       , 500      , "String");
	JTextField txtFiltroLinea        = new Componentes().text(new JCTextField()  , "Filtro Linea"       , 500      , "String");
	JTextField txtFiltroLocalizacion = new Componentes().text(new JCTextField()  , "Filtro Localizacion", 500      , "String");
	JTextField txtFiltroPasillo      = new Componentes().text(new JCTextField()  , "Filtro Pasillo"     , 500      , "String");
	
	int cantidad_de_columnas =  (new Obj_Reportes_De_Ventas().cantidad_de_competidores()+13);
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
	
//	JTable tabla = new JTable(model);
	
	 JTable tabla = new JTable(model){
    	 public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
    	        Component componente = super.prepareRenderer(renderer, row, col);
    	        if(col==6){
                  float margen_ordeno = Float.valueOf(tabla.getValueAt(row,6).toString().trim());
                  float margen_meta   = Float.valueOf(tabla.getValueAt(row,7).toString().trim());
                  Color c = Color.green;
                     if(margen_meta>margen_ordeno) {                          
                    	 c = new java.awt.Color(255,0,0); 
                     }
                     componente.setBackground(c);
    	        }
    	        
    	        if(col==12){
                    float venta = Float.valueOf(tabla.getValueAt(row,12).toString().trim());
                       if(venta<=0) {                          
                    	   Color c = Color.lightGray;
                      	  componente.setBackground(c);
                       }
      	        }
    	        
    	     return componente;
    	 }
    };
    
	private JScrollPane getPanelTabla()	{		
		int a=95,b=300;
		tabla.getColumnModel().getColumn(0).setHeaderValue("Familia");
		tabla.getColumnModel().getColumn(0).setMinWidth(150);
		tabla.getColumnModel().getColumn(1).setHeaderValue("Cod_Prod");
		tabla.getColumnModel().getColumn(1).setMaxWidth(a);
		tabla.getColumnModel().getColumn(1).setMinWidth(a);
		tabla.getColumnModel().getColumn(2).setHeaderValue("Descripcion");
		tabla.getColumnModel().getColumn(2).setMaxWidth(b*2+a);
		tabla.getColumnModel().getColumn(2).setMinWidth(b+a);
		tabla.getColumnModel().getColumn(3).setHeaderValue("Costo Promedio Actual");
		tabla.getColumnModel().getColumn(3).setMaxWidth(a);
		tabla.getColumnModel().getColumn(3).setMinWidth(a);
		tabla.getColumnModel().getColumn(4).setHeaderValue("Ultimo Costo Actual");
		tabla.getColumnModel().getColumn(4).setMaxWidth(a);
		tabla.getColumnModel().getColumn(4).setMinWidth(a);
		tabla.getColumnModel().getColumn(5).setHeaderValue("Precio De Venta Actual");
		tabla.getColumnModel().getColumn(5).setMaxWidth(a);
		tabla.getColumnModel().getColumn(5).setMinWidth(a);
		tabla.getColumnModel().getColumn(6).setHeaderValue("Margen");
		tabla.getColumnModel().getColumn(6).setMaxWidth(a);
		tabla.getColumnModel().getColumn(6).setMinWidth(a);
		tabla.getColumnModel().getColumn(7).setHeaderValue("Margen Meta Familia");
		tabla.getColumnModel().getColumn(7).setMinWidth(a);
		tabla.getColumnModel().getColumn(8).setHeaderValue("Precio De Oferta Actual");
		tabla.getColumnModel().getColumn(8).setMaxWidth(a);
		tabla.getColumnModel().getColumn(8).setMinWidth(a);
		tabla.getColumnModel().getColumn(9).setHeaderValue("Localización");
		tabla.getColumnModel().getColumn(9).setMinWidth(a);
		tabla.getColumnModel().getColumn(10).setHeaderValue("Pasillo");
		tabla.getColumnModel().getColumn(10).setMinWidth(a);
		tabla.getColumnModel().getColumn(11).setHeaderValue("Precio de Venta Captura");
		tabla.getColumnModel().getColumn(11).setMinWidth(a);
		tabla.getColumnModel().getColumn(12).setHeaderValue("Venta Ult. 90/dias");
		tabla.getColumnModel().getColumn(12).setMinWidth(a);
		
		try {
			String[] competidor = new Obj_Reportes_De_Ventas().lista_de_competidores();
			for(int i=13; i<cantidad_de_columnas; i++){
				tabla.getColumnModel().getColumn(i).setHeaderValue(competidor[(i-13)].toString());
				tabla.getColumnModel().getColumn(i).setMaxWidth(a+20);
				tabla.getColumnModel().getColumn(i).setMinWidth(a+20);
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
	@SuppressWarnings("rawtypes")
	private TableRowSorter trsfiltro;
	String parametroGeneral = "";
	String Lista="";
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
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
		
		this.trsfiltro = new TableRowSorter(model); 
		this.tabla.setRowSorter(trsfiltro);
		
		btn_buscar_ultimos_mov.setText(	"<html><FONT FACE=arial black SIZE=3 COLOR=WHITE><CENTER><p>Buscar Ultimas Cotizaciones Del Producto</p></CENTER></FONT></html>"); 
		
		int x=15 ;
		int y=20 ;
		int l=100;
		int a=20;

		panel.add(new JLabel("Fecha De Inicio De Busqueda De Datos:")).setBounds  (x     ,y ,200 ,a);
		panel.add(c_inicio).setBounds                                             (x+=185,y ,90  ,a);
		panel.add(txtcod_prod ).setBounds                                         (x+=105,y ,120 ,a);
		panel.add(JLBdescripcion).setBounds                                       (x+130 ,y ,450 ,a);
		
		x=100;
		panel.add(new JLabel("Filtro De Productos:")             ).setBounds(x-85,y+=30,l+50,a);
		panel.add(cmbOperador_Productos				             ).setBounds(x+80,y,l-12,a);
        panel.add(btnFiltroProducto					             ).setBounds(x+170,y,a,a);		
        panel.add(txtFiltroProducto			            		 ).setBounds(x+190,y,l*4+20,a);
        panel.add(btnLimpiarFiltroProducto			             ).setBounds(x+613,y,a,a);
        
		panel.add(new JLabel("Filtro De Clase De Productos:")    ).setBounds(x-85,y+=30,l+50,a); 
		panel.add(cmbOperador_Clase							     ).setBounds(x+80,y,l-12,a);  
        panel.add(btnFiltroClase							     ).setBounds(x+170,y,a,a);                                                                                      
        panel.add(txtFiltroClase							     ).setBounds(x+190,y,l*4+20,a);  
        panel.add(btnLimpiarFiltroClase						     ).setBounds(x+613,y,a,a);    
        
		panel.add(new JLabel("Filtro De Categoria De Productos:")).setBounds(x-85,y+=30,l+70,a); 
		panel.add(cmbOperador_Categoria							 ).setBounds(x+80,y,l-12,a);  
        panel.add(btnFiltroCategoria							 ).setBounds(x+170,y,a,a);    	                                                                                      
        panel.add(txtFiltroCategoria							 ).setBounds(x+190,y,l*4+20,a);  
        panel.add(btnLimpiarFiltroCategoria						 ).setBounds(x+613,y,a,a);    
        
		panel.add(new JLabel("Filtro Familia De Productos:")     ).setBounds(x-85,y+=30,l+50,a); 
		panel.add(cmbOperador_Familia						     ).setBounds(x+80,y,l-12,a);  
        panel.add(btnFiltroFamilia						 	     ).setBounds(x+170,y,a,a);                                                                                   
        panel.add(txtFiltroFamilia							     ).setBounds(x+190,y,l*4+20,a);  
        panel.add(btnLimpiarFiltroFamilia					     ).setBounds(x+613,y,a,a);    
        
		panel.add(new JLabel("Filtro De Linea De Productos:")    ).setBounds(x-85,y+=30,l+50,a); 
		panel.add(cmbOperador_Linea					             ).setBounds(x+80,y,l-12,a);  
        panel.add(btnFiltroLinea							     ).setBounds(x+170,y,a,a);  	                                                                                  
        panel.add(txtFiltroLinea							     ).setBounds(x+190,y,l*4+20,a);  
        panel.add(btnLimpiarFiltroLinea						     ).setBounds(x+613,y,a,a);    
        
        panel.add(new JLabel("Filtro De Talla De Productos:")    ).setBounds(x-85,y+=30,l+50,a); 
		panel.add(cmbOperador_Talla							     ).setBounds(x+80,y,l-12,a);  
        panel.add(btnFiltroTalla							     ).setBounds(x+170,y,a,a); 
        panel.add(txtFiltroTalla							     ).setBounds(x+190,y,l*4+20,a);  
        panel.add(btnLimpiarFiltroTalla						     ).setBounds(x+613,y,a,a); 
        
        panel.add(new JLabel("Filtro De Localizaciones:")        ).setBounds(x-85,y+=30,l+50,a); 
		panel.add(cmbOperador_Localizacion						 ).setBounds(x+80,y,l-12,a);  
        panel.add(btnFiltroLocalizaciones						 ).setBounds(x+170,y,a,a); 
        panel.add(txtFiltroLocalizacion							 ).setBounds(x+190,y,l*4+20,a);  
        panel.add(btnLimpiarFiltroLocalizaciones    			 ).setBounds(x+613,y,a,a); 
 
        panel.add(new JLabel("Filtro De Pasillos:")              ).setBounds(x-85,y+=30,l+50,a); 
		panel.add(cmbOperador_Pasillo							 ).setBounds(x+80,y,l-12,a); 
        panel.add(btnFiltroPasillo							     ).setBounds(x+170,y,a,a); 
        panel.add(txtFiltroPasillo 							     ).setBounds(x+190,y,l*4+20,a);  
        panel.add(btnLimpiarFiltroPasillo					     ).setBounds(x+613,y,a,a); 
        
        panel.add(getPanelTabla()).setBounds             (10,y+=50,ancho-30,alto-y-75);
        
        panel.add(btn_buscar_ultimos_mov).setBounds      (x+650 ,y=50  ,200 ,50);
        panel.add(cmbEstablecimiento).setBounds          (x+650 ,y+=70 ,200 ,a );      
        panel.add(btn_buscar).setBounds                  (x+650 ,y+=50 ,200 ,a );
        
        render_tabla();
        
        txtFiltroProducto.setEditable(false); 
        txtFiltroClase.setEditable(false);
        txtFiltroCategoria.setEditable(false);
        txtFiltroFamilia.setEditable(false);
        txtFiltroLinea.setEditable(false);
        txtFiltroTalla.setEditable(false);
        txtFiltroLocalizacion.setEditable(false);
        txtFiltroPasillo.setEditable(false);
        
    	btn_buscar_ultimos_mov.setEnabled(false);

        String operador_simbolo = "";
        
        if(!parametro.equals("")){
            switch(operador){
	    		case "Igual"		:operador_simbolo=" = "; break;
	    		case "Esta en lista":operador_simbolo=" in "; break;
	    		case "Menor que"	:operador_simbolo=" < "; break;
	    		case "Mayor que"	:operador_simbolo=" > "; break;
	    		case "No Este en Lista"	:operador_simbolo=" not in "; break;
    		}
        	txtFiltroProducto.setText(operador_simbolo+parametro);
        	cmbOperador_Productos.setSelectedItem(operador);
        	
        	panelEnableFalse();

        	btnLimpiarFiltroProducto.setEnabled(true);
        	btnLimpiarFiltroClase.setEnabled(true);
        	
        }
        txtcod_prod.addKeyListener(Buscar_Datos_Producto);
        
        btnFiltroProducto.addActionListener(op_filtro_productos);
        btnFiltroClase.addActionListener(op_filtro_clases);
        btnFiltroCategoria.addActionListener(op_filtro_categorias);
        btnFiltroFamilia.addActionListener(op_filtro_familias);
        btnFiltroLinea.addActionListener(op_filtro_lineas);
        btnFiltroTalla.addActionListener(op_filtro_talla);
        btnFiltroLocalizaciones.addActionListener(op_filtro_localizacion);
        btnFiltroPasillo.addActionListener(op_filtro_pasillo);
                
		btnLimpiarFiltroProducto.addActionListener(limpiar_filtro_productos);
		btnLimpiarFiltroClase.addActionListener(limpiar_filtro_claces);
        btnLimpiarFiltroCategoria.addActionListener(limpiar_filtro_categorias);
        btnLimpiarFiltroFamilia.addActionListener(limpiar_filtro_familias);
        btnLimpiarFiltroLinea.addActionListener(limpiar_filtro_lineas);
        btnLimpiarFiltroTalla.addActionListener(limpiar_filtro_talla);
        btnLimpiarFiltroLocalizaciones.addActionListener(limpiar_filtro_productos);
        btnLimpiarFiltroPasillo.addActionListener(limpiar_filtro_productos);
        
		btn_buscar.addActionListener(op_generar);
		btn_buscar_ultimos_mov.addActionListener(op_generar);
		
        this.addWindowListener(new WindowAdapter() { public void windowOpened( WindowEvent e ){
            	txtcod_prod.requestFocus();
                      } });
	}
	
	
	public void render_tabla(){
		for(int i = 0; i < cantidad_de_columnas; i++){
			if(i<=2){
				tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
			}else{
				tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12));
			}
		}
	}

	public void filtroProductos(String cadena){
		txtFiltroProducto.setText(cadena);
	}

	public String validar(){
		String error = "";
		String fechainicioNull = c_inicio.getDate()+"";
	    if(fechainicioNull.equals("null"))error+= "Fecha  inicio\n";
	    if(cmbEstablecimiento.getSelectedIndex()==0)error+= "Establecimiento\n";
		return error;
	}
	
	ActionListener op_filtro_productos = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(!cmbOperador_Productos.getSelectedItem().toString().equals("Todos")){
				dispose();
				new Cat_Filtro_De_Busqueda_De_Productos("Reporte_De_Analisis_De_Precios_De_Competencia",cmbOperador_Productos.getSelectedItem().toString(),"",null).setVisible(true);
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
	
	ActionListener op_filtro_localizacion = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(cmbEstablecimiento.getSelectedIndex()==0) {			
				JOptionPane.showMessageDialog(null, "Es Requerido Seleccionar Un Establecimiento", "Aviso !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
			return;
			}
			if(!cmbOperador_Localizacion.getSelectedItem().toString().equals("Todos")){
				new Cat_Filtro_Dinamico(cmbOperador_Localizacion.getSelectedItem().toString(),"localizacion","localizacion").setVisible(true);
			}else{
				JOptionPane.showMessageDialog(null, "El Operador Para Este Filtro Es ( Todos ) Por Lo Que No Es Necesario Abrir El Filtro", "Aviso !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}
		}
	};
	
	ActionListener op_filtro_pasillo = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(cmbEstablecimiento.getSelectedIndex()==0) {			
				JOptionPane.showMessageDialog(null, "Es Requerido Seleccionar Un Establecimiento", "Aviso !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
			return;
			}
			if(!cmbOperador_Pasillo.getSelectedItem().toString().equals("Todos")){
				new Cat_Filtro_Dinamico(cmbOperador_Pasillo.getSelectedItem().toString(),"pasillo","pasillo").setVisible(true);
			}else{
				JOptionPane.showMessageDialog(null, "El Operador Para Este Filtro Es ( Todos ) Por Lo Que No Es Necesario Abrir El Filtro", "Aviso !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
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
            txtFiltroTalla.setText("");
            txtFiltroLocalizacion.setText("");
            txtFiltroPasillo.setText("");
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
            txtFiltroLocalizacion.setText("");
            txtFiltroPasillo.setText("");
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
	
	KeyListener Buscar_Datos_Producto = new KeyListener() {
		public void keyTyped(KeyEvent e){}
		public void keyReleased(KeyEvent e) {}
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				try {
						if(new Obj_Cotizaciones_De_Un_Producto().Existe_Producto(txtcod_prod.getText().toUpperCase().trim())){
						       Obj_Cotizaciones_De_Un_Producto  Datos_Producto= new Obj_Cotizaciones_De_Un_Producto().buscardatos_producto(txtcod_prod.getText().trim().toUpperCase()+"");
								 if(!Datos_Producto.getDescripcion_Prod().toString().trim().equals("") || !txtcod_prod.getText().equals("")){
										cmbOperador_Productos.setSelectedItem("Igual");
								 }else{
									 	cmbOperador_Productos.setSelectedIndex(0);
								 }
								txtFiltroProducto.setText("=(''"+Datos_Producto.getCod_Prod().toString().trim()+"'')");
								JLBdescripcion.setText("<html> <FONT FACE="+"arial"+" SIZE=3 COLOR=BLUE><CENTER><b><p>"+Datos_Producto.getDescripcion_Prod().toString().trim()+"</p></b></CENTER></FONT></html>");
							}else{
								JLBdescripcion.setText("");
								JOptionPane.showMessageDialog(null, "El Codigo Esta Mal Escrito o El Producto No Existe" , "Aviso", JOptionPane.CANCEL_OPTION,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
		                    }
				    } catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Error en Cat_Cotizaciones_De_Un_Producto_En_Proveedores  en la funcion existe_Producto \n SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
					e1.printStackTrace();
				}
				txtcod_prod.setText("");
				panelEnableFalse();
				btn_buscar_ultimos_mov.setEnabled(true);
			}
		}
	};
	
	public void limpiar_vacios(String boton){
		if(!boton.equals("boton talla")){
			panelEnableFalse();
		}
		
		txtFiltroLinea.setText("");
    	JLBdescripcion.setText("");
        
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
        	cmbOperador_Talla.setSelectedIndex(0);
        	cmbOperador_Localizacion.setSelectedIndex(0);
        	cmbOperador_Pasillo.setSelectedIndex(0);
        	
        	Lista="";
	}
	
//	----------------------------------------------------------------------------------------------------------------------------------------
	
	ActionListener op_generar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			model.setRowCount(0);
			if(validar().equals("")){
				model.setRowCount(0);
				String fecha_inicio  = new SimpleDateFormat("dd/MM/yyyy").format(c_inicio.getDate());//+" "+new SimpleDateFormat("hh:mm:ss").format(sphora_inicio.getValue())+" "+sphora_inicio.getValue()+":00";
				String productos 	 = txtFiltroProducto.getText();
				String clases 		 = txtFiltroClase.getText();
				String categorias 	 = txtFiltroCategoria.getText();
				String familias 	 = txtFiltroFamilia.getText();
				String lineas 		 = txtFiltroLinea.getText();
				String tallas 		 = txtFiltroTalla.getText();
				String localizaciones= txtFiltroLocalizacion.getText();
				String pasillos  	 = txtFiltroPasillo.getText();
				
				int tipo =0;
				if(e.getActionCommand().toString().trim().equals("<html><FONT FACE=arial black SIZE=3 COLOR=WHITE><CENTER><p>Buscar Ultimas Cotizaciones Del Producto</p></CENTER></FONT></html>")){
					tipo=1;
				}
				
				if((productos+clases+categorias+familias+lineas+tallas+localizaciones+pasillos).length() > 0 ){
						Obj_Reportes_De_Ventas ventas = new Obj_Reportes_De_Ventas();
						
						ventas.setFecha_inicio(fecha_inicio);
						ventas.setProductos(productos);
						ventas.setClases(clases);
						ventas.setCategorias(categorias);
						ventas.setFamilias(familias);
						ventas.setLineas(lineas);
						ventas.setTallas(tallas);
						ventas.setLocalizaciones(localizaciones);
                        ventas.setPasillos(pasillos);  						
						try {
							Object[][] matriz_reporte_de_ventas = ventas.reporte_de_competencias(cantidad_de_columnas, tipo,cmbEstablecimiento.getSelectedItem().toString().trim());
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
				JOptionPane.showMessageDialog(null,"Los siguientes campos están vacíos: "+validar(),"Aviso!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
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
    	btn_buscar_ultimos_mov.setEnabled(false);
    	
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
    	btnFiltroTalla.setEnabled(true);
    	
    	btnLimpiarFiltroClase.setEnabled(true);
    	btnLimpiarFiltroCategoria.setEnabled(true);
    	btnLimpiarFiltroFamilia.setEnabled(true);
    	btnLimpiarFiltroLinea.setEnabled(true);
    	btnLimpiarFiltroTalla.setEnabled(true);
	}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
///////////////////////////TODO FILTRO DE  CLASIFICADORES/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
			                if(!(Operador.equals("Esta en lista")||Operador.equals("No Este en Lista"))   ){
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
				
				JTextField txtNombre_Completo      = new Componentes().textfiltro(new JCTextField(), "Teclea Aqui Para Realizar La Busqueda En La Tabla"    ,350 , "String",tablaFiltro    ,2 );
				
			 	JCButton btnAgregar  = new JCButton("Agregar"    ,"Aplicar.png"            ,"Azul"); 

			 	String folio_columna = "";
				@SuppressWarnings({ "unchecked", "rawtypes" })
				public Cat_Filtro_Dinamico(String operad, String nombre_de_tabla,String folio_colum){
					setSize(425,450);
					setResizable(false);
					setLocationRelativeTo(null);
					this.setModal(true);
					setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Filter-List-icon32.png"));
					setTitle("Filtro de "+nombre_de_tabla);
					campo.setBorder(BorderFactory.createTitledBorder("Seleccion "+folio_colum));
					trsfiltro = new TableRowSorter(modeloFiltro); 
					tablaFiltro.setRowSorter(trsfiltro);  
					
					Operador = operad;
					folio_columna=folio_colum;

					btnAgregar.setToolTipText("Agregar");
					
					campo.add(txtNombre_Completo).setBounds(10  ,20 ,296 ,20);
					campo.add(btnAgregar).setBounds        (305 ,20 ,105 ,20);
					campo.add(scroll).setBounds            (10  ,41 ,400 ,360);
					
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
					btnAgregar.addActionListener(opAgregar);
				}
				
				public void llamar_render(){
					tablaFiltro.getColumnModel().getColumn(0).setMinWidth(40);
					tablaFiltro.getColumnModel().getColumn(1).setMinWidth(310);
					tablaFiltro.getColumnModel().getColumn(2).setMaxWidth(30);
					tablaFiltro.getColumnModel().getColumn(2).setMinWidth(30);
					
					tablaFiltro.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",10)); 
					tablaFiltro.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
					tablaFiltro.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("CHB","centro","Arial","normal",12));
				}
				
				ActionListener opAgregar = new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						txtNombre_Completo.setText("");
						int[] columnas = {0,1};
						new Obj_Filtro_Dinamico_Plus(tablaFiltro,"", columnas);

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
				 			    		case "No Este en Lista"	:operador_simbolo=" not in "; 
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
							 				
							 				case "talla":			txtFiltroTalla.setText(Lista)		;
											 					if(!txtFiltroTalla.getText().equals("")){
											 						txtcod_prod.setText("");
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
	 										
							 				case "localizacion":			txtFiltroLocalizacion.setText(Lista)		;
						 					if(!txtFiltroLocalizacion.getText().equals("")){
						 						txtcod_prod.setText("");
						 						txtFiltroProducto.setText("");
						 						txtFiltroCategoria.setText("");
						 						txtFiltroClase.setText("");
						 						txtFiltroFamilia.setText("");
						 						txtFiltroLinea.setText("");		 										
		 										btnLimpiarFiltroLocalizaciones.setEnabled(true);
							                      }
						 					break;
						 					
							 				case "pasillo":			txtFiltroPasillo.setText(Lista)		;
						 					if(!txtFiltroPasillo.getText().equals("")){
						 						txtcod_prod.setText("");
						 						txtFiltroProducto.setText("");
						 						txtFiltroCategoria.setText("");
						 						txtFiltroClase.setText("");
						 						txtFiltroFamilia.setText("");
						 						txtFiltroLinea.setText("");
		 										btnLimpiarFiltroPasillo.setEnabled(true);
		 										}
							                break;
							
				 				}
					 			dispose();
				 			}
					}
				};
				
				
			   	public Object[][] getTablaFiltro(String operador, String nombre_de_tabla){
			   		String condicion = "";
			   		String todos ="";
			   		if(!Lista.equals("")){
			   			condicion = " where jerarquia "+Lista.replace("''","'");
			   		}
			   		
			   		if(nombre_de_tabla.equals("localizacion")) {
			   		 todos = "	   select DISTINCT upper(localizacion),upper(localizacion)as nombre from [BMSIZAGAR].dbo.localizaciones_surtido_productos where cod_estab=(select cod_estab from [BMSIZAGAR].dbo.establecimientos where nombre ='"+cmbEstablecimiento.getSelectedItem().toString()+"') order by nombre";	
			   		}else {
			   			
			   			if(nombre_de_tabla.equals("pasillo")) {
					   		 todos = "	   select DISTINCT isnull(SUBSTRING(upper(localizacion),3,3),0)as pasillo,upper(localizacion)as nombre from [BMSIZAGAR].dbo.localizaciones_surtido_productos where cod_estab=(select cod_estab from [BMSIZAGAR].dbo.establecimientos where nombre ='"+cmbEstablecimiento.getSelectedItem().toString()+"') order by pasillo";	
					   		}else {
					          todos = "select "+folio_columna+" as folio,upper(nombre) from "+nombre_de_tabla+condicion+" order by nombre";
					   		}
			   		}
			   		
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
				
			}
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Analisis_De_Precios_De_Competencia("","Todos").setVisible(true);
		}catch(Exception e){	}
	}

}
