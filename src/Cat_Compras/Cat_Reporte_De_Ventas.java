package Cat_Compras;

import java.awt.Color;
import java.awt.Container;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
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
import java.sql.Time;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SpinnerDateModel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Connexion;
import Obj_Compras.Obj_Cotizaciones_De_Un_Producto;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.Componentes;
import Obj_Principal.Obj_Filtro_Dinamico;
import Obj_Renders.tablaRenderer;
import Obj_Reportes.Obj_Reportes_De_Ventas;

import com.toedter.calendar.JDateChooser;

@SuppressWarnings("serial")
public class Cat_Reporte_De_Ventas extends JFrame {
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	Connexion con = new Connexion();
	
	JDateChooser c_inicio = new JDateChooser();
	JDateChooser c_final = new JDateChooser();
	
	String[] inicioDefault ="1:00:00".split(":");
	String[] finDefault ="23:59:59".split(":");
	
	SpinnerDateModel TiempoInicio =  new SpinnerDateModel();
	  JSpinner sphora_inicio = new JSpinner(TiempoInicio);                                         
	  JSpinner.DateEditor datoini = new JSpinner.DateEditor(sphora_inicio,"H:mm");
	SpinnerDateModel TiempoFin =  new SpinnerDateModel();
	  JSpinner sphora_fin = new JSpinner(TiempoFin);                                         
	  JSpinner.DateEditor datofin = new JSpinner.DateEditor(sphora_fin,"H:mm");
	  
	String establecimiento[] = new Obj_Establecimiento().Combo_Establecimiento201();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbEstablecimiento = new JComboBox(establecimiento);
	
	String tipoDePrecio[] = {"Todos","Normal","Oferta"};
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbTipoDePrecio = new JComboBox(tipoDePrecio);
	
	String presentado[] = {"Ticket","Producto"};
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbPresentado = new JComboBox(presentado);
	
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
	
	String operadorTalla[] = {"Todos","Igual","Esta en lista","Menor que","Mayor que","Diferente"};
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbOperador_Talla = new JComboBox(operadorTalla);
	
	String prodPed[] = {"Producto","Pedido"};
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbProdPed = new JComboBox(prodPed);	
	
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
	
	JButton btn_buscar = new JButton  ("Buscar",new ImageIcon("imagen/buscar.png"));
	
	JLabel JLBlinicio= new JLabel(new ImageIcon("Imagen/iniciar-icono-4628-16.png") );
	JLabel JLBfin= new JLabel(new ImageIcon("Imagen/acabado-icono-7912-16.png") );
	JLabel JLBrelog= new JLabel(new ImageIcon("Imagen/horas-reloj-icono-9852-16.png") );
	JLabel JLBrelog2= new JLabel(new ImageIcon("Imagen/horas-reloj-icono-9852-16.png") );
	JLabel JLBestablecimiento= new JLabel(new ImageIcon("Imagen/folder-home-home-icone-5663-16.png") );
	JLabel JLBTipoPrecio= new JLabel(new ImageIcon("Imagen/precio-marcado-icono-6652-16.png") );
	JLabel JLBPresentado= new JLabel(new ImageIcon("Imagen/las-preferencias-de-tema-de-escritorio-icono-8603-16.png") );
	
	JTextField txtFiltroProducto = new JTextField("");
	JTextField txtcod_prod = new Componentes().text(new JTextField(), "Codigo del Producto", 15, "String");
	JTextField txtFiltroClase = new JTextField("");
	JTextField txtFiltroCategoria = new JTextField("");
	JTextField txtFiltroFamilia = new JTextField("");
	JTextField txtFiltroLinea = new JTextField("");
	JTextField txtFiltroTalla = new JTextField("");
	
	DefaultTableModel modelo_ventas = new DefaultTableModel(null,
            new String[]{"Ticket","Establecimiento","Cod_Prod","Descripcion","Venta Unidades","Venta Piezas","Clase Producto","Categoria","Familias","Lineas Productos","Fecha Agotado","Importe","Imp. s/IVA","Costo","Porcentaje SCosto","Dias De Venta","Fecha De Venta","Tipo Precio Venta","Forma De Pago","Cond. De Pago"}
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
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class
                                    };
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
        	 	case 15 : return false; 
        	 	case 16 : return false; 
        	 	case 17 : return false; 
        	 	case 18 : return false;
        	 	case 19 : return false;
        	 	case 20 : return false;
        	 } 				
 			return false;
 		}
	};
    JTable tabla = new JTable(modelo_ventas);
    JScrollPane scrollExist_Estab = new JScrollPane(tabla);
    
	Border blackline, etched, raisedbevel, loweredbevel, empty;
	
	String parametroGeneral = "";
	String Lista="";
	JLabel JLBdescripcion= new JLabel();
	JLabel lblmarco= new JLabel();
	
    static JLabel lblSemaforoRojo = new JLabel("");
    static JLabel lblSemaforoVerde = new JLabel("");
    Icon iconoSemaforoR;
	String semaforoR = System.getProperty("user.dir")+"/Imagen/semaforo_rojo_chica.png";
    ImageIcon tmpIconSemR = new ImageIcon(semaforoR);
    
    Icon iconoSemaforoV;
    String semaforoV = System.getProperty("user.dir")+"/Imagen/semaforo_verde_chica.png";
    ImageIcon tmpIconSemV = new ImageIcon(semaforoV);
	
	public Cat_Reporte_De_Ventas(String parametro, String operador){
		
		int ancho = Toolkit.getDefaultToolkit().getScreenSize().width;
		int alto = Toolkit.getDefaultToolkit().getScreenSize().height;
		setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds()); 
		cont.add(panel);
		setResizable(false);
		setLocationRelativeTo(null);
		setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Sales-by-payment-method-icon-64.png"));
		setTitle("Reportes de  Ventas");
		panel.setBorder(BorderFactory.createTitledBorder("Reportes de Venta"));
		lblmarco.setBorder(BorderFactory.createTitledBorder(""));
		
		txtcod_prod.setBackground(Color.lightGray);
		txtcod_prod.setBorder(BorderFactory.createTitledBorder(""));
		txtcod_prod.setHorizontalAlignment(4);
		//      asigna el foco al JTextField deseado al arrancar la ventana
        this.addWindowListener(new WindowAdapter() {
                public void windowOpened( WindowEvent e ){
                	txtcod_prod.requestFocus();
             }
        });
        
		int x=15 ;
		int y=20 ;
		int l=100;
		int a=20;

		panel.add(new JLabel("Fecha Inicio:")).setBounds(x,y,l,a);
		panel.add(JLBlinicio).setBounds(x+=60,y,a,a);
		panel.add(c_inicio).setBounds(x+=20,y,l-10,a);
		panel.add(sphora_inicio).setBounds(x+=95,y,l-50,a);
		panel.add(JLBrelog).setBounds(x+=50,y,a,a);
		panel.add(new JLabel("Fecha Final:")).setBounds(x+=40,y,l,a);
		panel.add(JLBfin).setBounds(x+=60,y,a,a);
		panel.add(c_final).setBounds(x+=20,y,l-10,a);
		panel.add(sphora_fin).setBounds(x+=95,y,l-50,a);
		panel.add(JLBrelog2).setBounds(x+=50,y,a,a);
		
		panel.add(cmbProdPed).setBounds(x+=40,y,l-30,a);
        panel.add(txtcod_prod).setBounds(x+=70,y,75,a);
		
		x=100;
		panel.add(new JLabel("Filtro De Productos:")).setBounds(x-85,y+=30,l+50,a);
		panel.add(cmbOperador_Productos				).setBounds(x+80,y,l-12,a);
        panel.add(txtFiltroProducto					).setBounds(x+170,y,l*4+20,a);

        panel.add(btnFiltroProducto					).setBounds(x+590,y,a,a);
        panel.add(btnLimpiarFiltroProducto			).setBounds(x+613,y,a,a);
        
        panel.add(JLBdescripcion).setBounds(x+650,y,l+350,a);
        
		panel.add(new JLabel("Filtro De Clase De Productos:")).setBounds(x-85,y+=30,l+50,a); 
		panel.add(cmbOperador_Clase							 ).setBounds(x+80,y,l-12,a);  
        panel.add(txtFiltroClase							 ).setBounds(x+170,y,l*4+20,a);  
        panel.add(btnFiltroClase							 ).setBounds(x+590,y,a,a);    
        panel.add(btnLimpiarFiltroClase						 ).setBounds(x+613,y,a,a);
        
		panel.add(new JLabel("Establecimiento:")).setBounds(x+650,y,l+50,a);
	    panel.add(JLBestablecimiento).setBounds(x+720,y,a,a);
		panel.add(cmbEstablecimiento).setBounds(x+740,y,l+70,a);
		
		panel.add(new JLabel("Filtro De Categoria De Productos:")).setBounds(x-85,y+=30,l+70,a); 
		panel.add(cmbOperador_Categoria							 ).setBounds(x+80,y,l-12,a);  
        panel.add(txtFiltroCategoria							 ).setBounds(x+170,y,l*4+20,a);  
        panel.add(btnFiltroCategoria							 ).setBounds(x+590,y,a,a);    
        panel.add(btnLimpiarFiltroCategoria						 ).setBounds(x+613,y,a,a);   
        
		panel.add(new JLabel("Tipo De Precio:")).setBounds(x+650,y,l+50,a);
	    panel.add(JLBTipoPrecio).setBounds(x+720,y,a,a);
		panel.add(cmbTipoDePrecio).setBounds(x+740,y,l+70,a);
        
      	panel.add(new JLabel("Filtro Familia De Productos:")).setBounds(x-85,y+=30,l+50,a); 
		panel.add(cmbOperador_Familia						).setBounds(x+80,y,l-12,a);  
		panel.add(txtFiltroFamilia							).setBounds(x+170,y,l*4+20,a);  
        panel.add(btnFiltroFamilia							).setBounds(x+590,y,a,a);    
        panel.add(btnLimpiarFiltroFamilia					).setBounds(x+613,y,a,a);
        
		panel.add(new JLabel("Presentado Por:")).setBounds(x+650,y,l+50,a);
	    panel.add(JLBPresentado).setBounds(x+720,y,a,a);
		panel.add(cmbPresentado).setBounds(x+740,y,l+70,a);
        
		panel.add(new JLabel("Filtro De Linea De Productos:")).setBounds(x-85,y+=30,l+50,a); 
		panel.add(cmbOperador_Linea					 ).setBounds(x+80,y,l-12,a);  
        panel.add(txtFiltroLinea							 ).setBounds(x+170,y,l*4+20,a);  
        panel.add(btnFiltroLinea							 ).setBounds(x+590,y,a,a);    
        panel.add(btnLimpiarFiltroLinea						 ).setBounds(x+613,y,a,a); 
        
        panel.add(lblmarco									 ).setBounds(x+800,y-5,a+93,a+45); 
        panel.add(lblSemaforoVerde							 ).setBounds(x+823,y,a+10,a+10); 
        panel.add(lblSemaforoRojo							 ).setBounds(x+860,y,a+10,a+10); 
        
        
        panel.add(new JLabel("Filtro De Talla De Productos:")).setBounds(x-85,y+=30,l+50,a); 
		panel.add(cmbOperador_Talla							 ).setBounds(x+80,y,l-12,a);  
        panel.add(txtFiltroTalla							 ).setBounds(x+170,y,l*4+20,a);  
        panel.add(btnFiltroTalla							 ).setBounds(x+590,y,a,a);    
        panel.add(btnLimpiarFiltroTalla						 ).setBounds(x+613,y,a,a); 
        
        panel.add(btn_buscar).setBounds(x+807,y,l,a);
        
        panel.add(Tabla()).setBounds(10,y+=50,ancho-30,alto-y-75);
        
        cargar_fechas();
        render_tabla();
        
        iconoSemaforoR = new ImageIcon(tmpIconSemR.getImage().getScaledInstance(lblSemaforoRojo.getWidth(), lblSemaforoRojo.getHeight(), Image.SCALE_DEFAULT));
        lblSemaforoRojo.setIcon(iconoSemaforoR);
        iconoSemaforoV = new ImageIcon(tmpIconSemV.getImage().getScaledInstance(lblSemaforoVerde.getWidth(), lblSemaforoVerde.getHeight(), Image.SCALE_DEFAULT));
        lblSemaforoVerde.setIcon(iconoSemaforoV);
        
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
        
        cmbProdPed.addActionListener(op_cmb);
        
        btnFiltroProducto.addActionListener(op_filtro_productos);
        btnFiltroClase.addActionListener(op_filtro_clases);
        btnFiltroCategoria.addActionListener(op_filtro_categorias);
        btnFiltroFamilia.addActionListener(op_filtro_familias);
        btnFiltroLinea.addActionListener(op_filtro_lineas);
        btnFiltroTalla.addActionListener(op_filtro_talla);
        
		
		btnLimpiarFiltroProducto.addActionListener(limpiar_filtro_productos);
		btnLimpiarFiltroClase.addActionListener(limpiar_filtro_claces);
        btnLimpiarFiltroCategoria.addActionListener(limpiar_filtro_categorias);
        btnLimpiarFiltroFamilia.addActionListener(limpiar_filtro_familias);
        btnLimpiarFiltroLinea.addActionListener(limpiar_filtro_lineas);
        btnLimpiarFiltroTalla.addActionListener(limpiar_filtro_talla);
        
        txtcod_prod.addKeyListener(Buscar_Datos_Producto);
	        
		btn_buscar.addActionListener(op_generar);
		
		tiempodefault();
		semaforo();
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
			tabla.getColumnModel().getColumn(15).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12));
			tabla.getColumnModel().getColumn(16).setCellRenderer(new tablaRenderer("texto","centro","Arial","normal",12));
			tabla.getColumnModel().getColumn(17).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
			tabla.getColumnModel().getColumn(18).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
			tabla.getColumnModel().getColumn(19).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
			
	}
	
	
private JScrollPane Tabla()	{		
	
	this.tabla.getTableHeader().setReorderingAllowed(false) ;
	this.tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
	int largo=100;
	tabla.getColumnModel().getColumn(0).setMaxWidth(largo);
	tabla.getColumnModel().getColumn(0).setMinWidth(largo);
	tabla.getColumnModel().getColumn(1).setMaxWidth(largo+50);
	tabla.getColumnModel().getColumn(1).setMinWidth(largo+50);
	tabla.getColumnModel().getColumn(2).setMaxWidth(largo-20);
	tabla.getColumnModel().getColumn(2).setMinWidth(largo-20);
	tabla.getColumnModel().getColumn(3).setMaxWidth(largo*5);
	tabla.getColumnModel().getColumn(3).setMinWidth(largo*3);
	tabla.getColumnModel().getColumn(4).setMaxWidth(largo-10);
	tabla.getColumnModel().getColumn(4).setMinWidth(largo-10);
	tabla.getColumnModel().getColumn(5).setMaxWidth(largo-20);
	tabla.getColumnModel().getColumn(5).setMinWidth(largo-20);
	tabla.getColumnModel().getColumn(6).setMaxWidth(largo);
	tabla.getColumnModel().getColumn(6).setMinWidth(largo);
	tabla.getColumnModel().getColumn(7).setMaxWidth(largo+30);
	tabla.getColumnModel().getColumn(7).setMinWidth(largo+10);
	tabla.getColumnModel().getColumn(8).setMaxWidth(largo+100);
	tabla.getColumnModel().getColumn(8).setMinWidth(largo+20);
	tabla.getColumnModel().getColumn(9).setMaxWidth(largo+10);
	tabla.getColumnModel().getColumn(9).setMinWidth(largo+10);
	tabla.getColumnModel().getColumn(10).setMaxWidth(largo+40);
	tabla.getColumnModel().getColumn(10).setMinWidth(largo+40);
	tabla.getColumnModel().getColumn(11).setMaxWidth(50);
	tabla.getColumnModel().getColumn(11).setMinWidth(50);
	tabla.getColumnModel().getColumn(12).setMaxWidth(largo-20);
	tabla.getColumnModel().getColumn(12).setMinWidth(largo-20);
	
	tabla.getColumnModel().getColumn(13).setMaxWidth(largo-20);
	tabla.getColumnModel().getColumn(13).setMinWidth(largo-20);
	tabla.getColumnModel().getColumn(14).setMaxWidth(largo);
	tabla.getColumnModel().getColumn(14).setMinWidth(largo);
	tabla.getColumnModel().getColumn(15).setMaxWidth(largo-20);
	tabla.getColumnModel().getColumn(15).setMinWidth(largo-20);
	tabla.getColumnModel().getColumn(16).setMaxWidth(largo+20);
	tabla.getColumnModel().getColumn(16).setMinWidth(largo+20);
	
	tabla.getColumnModel().getColumn(17).setMaxWidth(largo);
	tabla.getColumnModel().getColumn(17).setMinWidth(largo);
	tabla.getColumnModel().getColumn(18).setMaxWidth(largo);
	tabla.getColumnModel().getColumn(18).setMinWidth(largo);
	tabla.getColumnModel().getColumn(19).setMaxWidth(largo);
	tabla.getColumnModel().getColumn(19).setMinWidth(largo);
	
		
		JScrollPane scrol = new JScrollPane(tabla);
	    return scrol; 
}

public void filtroProductos(String cadena){
	txtFiltroProducto.setText(cadena);
}

	@SuppressWarnings("deprecation")
	public void tiempodefault(){
		sphora_inicio.setValue(new Time(Integer.parseInt(inicioDefault[0]),Integer.parseInt(inicioDefault[1]),Integer.parseInt(inicioDefault[2])));
		sphora_inicio.setEditor(datoini);
		sphora_fin.setValue(new Time(Integer.parseInt(finDefault[0]),Integer.parseInt(finDefault[1]),Integer.parseInt(finDefault[2])));
		sphora_fin.setEditor(datofin);
	}
	
	ActionListener op_cmb = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
				txtcod_prod.requestFocus();
		}
	};
	
	ActionListener op_filtro_productos = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(!cmbOperador_Productos.getSelectedItem().toString().equals("Todos")){
				dispose();
				new Cat_Filtro_De_Busqueda_De_Productos("Reporte_De_Ventas",cmbOperador_Productos.getSelectedItem().toString()).setVisible(true);
			}else{
				JOptionPane.showMessageDialog(null, "El Operador Para Este Filtro Es ( Todos ) Por Lo Que No Es Necesario Abrir El Filtro", "Aviso !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}
		}
	};
	
	ActionListener op_filtro_clases = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(!cmbOperador_Clase.getSelectedItem().toString().equals("Todos")){
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
	
	ActionListener op_generar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			semaforo();
			
			if(validar_fechas().equals("")){
				
				String fecha_inicio = new SimpleDateFormat("dd/MM/yyyy").format(c_inicio.getDate())+" "+new SimpleDateFormat("hh:mm:ss").format(sphora_inicio.getValue());
				String fecha_final = new SimpleDateFormat("dd/MM/yyyy").format(c_final.getDate())+" "+new SimpleDateFormat("hh:mm:ss").format(sphora_fin.getValue());
				
				String Establecimiento = "''"+cmbEstablecimiento.getSelectedItem().toString()+"''";
				String tipo_de_precio  ="''"+cmbTipoDePrecio.getSelectedItem().toString()+"''";
				String presentado      ="'"+cmbPresentado.getSelectedIndex()+"'";
				
				String productos 	= txtFiltroProducto.getText();
				String clases 		= txtFiltroClase.getText();
				String categorias 	= txtFiltroCategoria.getText();
				String familias 	= txtFiltroFamilia.getText();
				String lineas 		= txtFiltroLinea.getText();
				String tallas 		= txtFiltroTalla.getText();
				
				
				  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm"); 

				  Date fecha1 = sdf.parse(fecha_inicio , new ParsePosition(0));
				  Date fecha2 = sdf.parse(fecha_final , new ParsePosition(0));

				if(fecha1.before(fecha2)){
//					Reporte_de_Asistencia_establecimiento(fecha_inicio,fecha_final,Establecimiento,Departamento,folios_empleados);
					Obj_Reportes_De_Ventas ventas = new Obj_Reportes_De_Ventas();
					
					ventas.setFecha_inicio(fecha_inicio);
					ventas.setFecha_final(fecha_final);
					ventas.setEstablecimiento(Establecimiento);
					ventas.setTipo_de_precio(tipo_de_precio);
					ventas.setProductos(productos);
					ventas.setClases(clases);
					ventas.setCategorias(categorias);
					ventas.setFamilias(familias);
					ventas.setLineas(lineas);
					ventas.setPresentado(presentado);
					ventas.setTallas(tallas);
					
					try {
						while(tabla.getRowCount()>0){modelo_ventas.removeRow(0);}
						
						String[][] matriz_reporte_de_ventas = ventas.reporte_de_ventas();
						
						if(matriz_reporte_de_ventas.length==0){
							JOptionPane.showMessageDialog(null, "No se encontraron registros con las condiciones de busqueda proporcionada","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
							return;
						}else{
							
							 String[] fila = new String[20];
							 
								for(int i=0; i<matriz_reporte_de_ventas.length; i++){
									for(int j=0; j<20; j++){
										fila[j] = matriz_reporte_de_ventas[i][j ]+"";
									}
									modelo_ventas.addRow(fila);
								}
						}
						
					} catch (SQLException e2) {
						e2.printStackTrace();
					}
					
				}else{
					JOptionPane.showMessageDialog(null,"El Rango de Fechas Esta Invertido","Aviso!", JOptionPane.WARNING_MESSAGE);
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
	
	public String validar_fechas(){
		String error = "";
		String fechainicioNull = c_inicio.getDate()+"";
		String fechafinalNull = c_final.getDate()+"";
	    if(fechainicioNull.equals("null"))error+= "Fecha  inicio\n";
		if(fechafinalNull.equals("null"))error+= "Fecha Final\n";
		
		return error;
	}
	
	public void cargar_fechas(){
		Date date1 = null;
				  try {
					date1 = new SimpleDateFormat("dd/MM/yyyy").parse(new BuscarSQL().fecha(0));
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		c_inicio.setDate(date1);
					
	    Date date2 = null;
					  try {
						date2 = new SimpleDateFormat("dd/MM/yyyy").parse(new BuscarSQL().fecha(0));
					} catch (ParseException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					}
		c_final.setDate(date2);
	};
	
	public void semaforo(){
		if(new BuscarSQL().semaforo_rptVentas()){
			lblSemaforoRojo.setEnabled(false);
			lblSemaforoVerde.setEnabled(true);
		}else{
			lblSemaforoRojo.setEnabled(true);
			lblSemaforoVerde.setEnabled(false);
		}
	}
	
	KeyListener Buscar_Datos_Producto = new KeyListener() {
		public void keyTyped(KeyEvent e){}
		public void keyReleased(KeyEvent e) {}
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				try {
					if(cmbProdPed.getSelectedIndex()==0){
						
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
								JOptionPane.showMessageDialog(null, "El Codigo Esta Mal Escrito o El Producto No Existe" , "Aviso", JOptionPane.CANCEL_OPTION);
		                    }
						
					}else{
						JLBdescripcion.setText("");
						
						if(new Obj_Cotizaciones_De_Un_Producto().Existe_Pedido(txtcod_prod.getText().toUpperCase().trim())){
							
						      String  Datos_Producto= new Obj_Cotizaciones_De_Un_Producto().buscardatos_productos_en_pedidos(txtcod_prod.getText().trim().toUpperCase());
					            
							 if(!Datos_Producto.equals("") || !txtcod_prod.getText().equals("")){
								 	cmbOperador_Productos.setSelectedItem("Esta en lista");
							 }else{
								 	cmbOperador_Productos.setSelectedIndex(0);
							 }
							txtFiltroProducto.setText("in("+Datos_Producto+")");
						}else{
							JOptionPane.showMessageDialog(null, "El Codigo Esta Mal Escrito o El Pedido No Existe" , "Aviso", JOptionPane.CANCEL_OPTION);
	                    }
					}
					
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Error en Cat_Cotizaciones_De_Un_Producto_En_Proveedores  en la funcion existe_Producto \n SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
				
				txtcod_prod.setText("");
			}
		}
	};
	
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
			   			
			   			condicion = " where jerarquia "+Lista.replace("''","'");
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
			new Cat_Reporte_De_Ventas("","Todos").setVisible(true);
		}catch(Exception e){	}
	}

}
