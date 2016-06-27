package Cat_Reportes;

import java.awt.Color;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Connexion;
import Conexiones_SQL.Generacion_Reportes;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_Filtro_Dinamico_Plus;
import Obj_Principal.Obj_Refrescar;
import Obj_Renders.CaveceraTablaRenderer;
import Obj_Renders.tablaRenderer;

import com.toedter.calendar.JDateChooser;

import Cat_Compras.Cat_Venta_De_Cascos_A_Proveedores;
import Cat_Compras.Cat_Venta_De_Cascos_A_Proveedores_Filtro;


@SuppressWarnings("serial")
public class Cat_Reportes_De_Bonificacion_De_Proveedores extends JFrame {
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JDateChooser c_inicio = new JDateChooser();
	JDateChooser c_final = new JDateChooser();
	
//	String establecimiento[] = new Obj_Establecimiento().Combo_Establecimiento();
	String status[] = {"Selecciona Un Status","VIGENTE"};
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbStatus = new JComboBox(status);

	JTextField txtFolioProveedor = new Componentes().text(new JCTextField(), "Folio", 10, "Int");
	JTextField txtProveedor = new Componentes().text(new JCTextField(), "Proveedor", 200, "String");
	
	JCButton btn_generar = new JCButton  ("Generar","contrato-de-acuerdo-de-acuerdo-de-la-mano-encuentros-socio-icono-7428-32.png","Azul");
	
	JCButton btn_filtro_cliente = new JCButton  ("","buscar.png","Azul");
	JCButton btn_limpiar_cliente = new JCButton  ("","deshacer16.png","Azul");
	
	JLabel JLBlinicio= new JLabel(new ImageIcon("Imagen/iniciar-icono-4628-16.png") );
	JLabel JLBfin= new JLabel(new ImageIcon("Imagen/acabado-icono-7912-16.png") );
	JLabel JLBestablecimiento= new JLabel(new ImageIcon("Imagen/folder-home-home-icone-5663-16.png") );
	
	Obj_Usuario usuario = new Obj_Usuario().LeerSession();
	
	public Cat_Reportes_De_Bonificacion_De_Proveedores(){
		this.setSize(560,155);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/contrato-de-acuerdo-de-acuerdo-de-la-mano-encuentros-socio-icono-7428-48.png"));
		this.setTitle("Reporte De Bonificacion De Cascos Por Fecha");
		this.panel.setBorder(BorderFactory.createTitledBorder("Bonificacion De Cascos Por Fecha"));
		
		this.panel.add(new JLabel("Fecha Inicio:")).setBounds(15,25,100,20);
		this.panel.add(JLBlinicio).setBounds(75,25,20,20);
		this.panel.add(c_inicio).setBounds(95,25,100,20);
		this.panel.add(new JLabel("Fecha Final:")).setBounds(15,55,100,20);
		this.panel.add(JLBfin).setBounds(75,55,20,20);
		this.panel.add(c_final).setBounds(95,55,100,20);
	    this.panel.add(new JLabel("Status:")).setBounds(200,25,150,20);
	    this.panel.add(JLBestablecimiento).setBounds(280,25,20,20);
		this.panel.add(cmbStatus).setBounds(300,25,235,20);
		
		this.panel.add(txtFolioProveedor).setBounds(200,55,50,20);
		this.panel.add(txtProveedor).setBounds(250,55,230,20);
		this.panel.add(btn_filtro_cliente).setBounds(480,55,25,20);
		this.panel.add(btn_limpiar_cliente).setBounds(510,55,25,20);
		
		this.panel.add(btn_generar).setBounds(160,90,270,25);
		
		txtFolioProveedor.setEditable(false);
		txtProveedor.setEditable(false);
		
		this.cont.add(panel);
		cargar_fechas();
		 
		btn_limpiar_cliente.addActionListener(op_limpiar);
		btn_filtro_cliente.addActionListener(op_Filtro);
		btn_generar.addActionListener(op_generar);
		
	}
	
	public void cargar_fechas(){
		Date date1 = null;
				  try {
					date1 = new SimpleDateFormat("dd/MM/yyyy").parse(new BuscarSQL().fecha(1));
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		c_inicio.setDate(date1);
	    Date date2 = null;
					  try {
						date2 = new SimpleDateFormat("dd/MM/yyyy").parse(new BuscarSQL().fecha(1));
					} catch (ParseException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					}
		c_final.setDate(date2);
	};
	
	String basedatos="2.26";
	String vista_previa_reporte="si";
	int vista_previa_de_ventana=0;
	String comando="";
	String reporte = "";
	
	ActionListener op_limpiar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			txtFolioProveedor.setText("");
			txtProveedor.setText("");
		}
	};
	
	ActionListener op_Filtro = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			new Cat_Venta_De_Cascos_A_Proveedores_Filtro().setVisible(true);
		}
	};
	
	ActionListener op_generar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(validar_fechas().equals("")){
				String fecha_inicio = new SimpleDateFormat("dd/MM/yyyy").format(c_inicio.getDate())+" 00:00:00";
				String fecha_final = new SimpleDateFormat("dd/MM/yyyy").format(c_final.getDate())+" 23:59:58";
			    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm"); 
  			    Date fecha1 = sdf.parse(fecha_inicio , new ParsePosition(0));
				Date fecha2 = sdf.parse(fecha_final , new ParsePosition(0));
				
				String estatus = cmbStatus.getSelectedItem().toString().equals("Selecciona Un Status")?"":cmbStatus.getSelectedItem().toString().substring(0,1);
				String folio_proveedor =  txtFolioProveedor.getText().trim();
		
                if(fecha1.before(fecha2)){
    					Reporte_de_mov(fecha_inicio,fecha_final,estatus,folio_proveedor);
                }else{
					  JOptionPane.showMessageDialog(null, "El Rango De Fechas Esta Invertido","Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
                      return;
				}
			}else{
				  JOptionPane.showMessageDialog(null, "Los Siguientes Campos Estan Vacios y Se Necesitan Para La Consulta:\n "+validar_fechas(),"Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
                  return;
			}
		}
	};
	
	public void Reporte_de_mov(String fecha_inicio, String fecha_final,String status,String proveedor){
		 reporte = "Obj_Reporte_De_Bonificaciones_De_Proveedores_Por_Rango_De_Fechas.jrxml";
		 comando = "exec sp_select_bonificacion_de_cascos_por_proveedor_por_rango_de_fechas '"+fecha_inicio+"','"+fecha_final+"','"+status+"','"+proveedor+"'";
		System.out.println(comando);
		 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
	}
	
	public String validar_fechas(){
		String error = "";
		String fechainicioNull = c_inicio.getDate()+"";
		String fechafinalNull = c_final.getDate()+"";
	    if(fechainicioNull.equals("null"))error+= "Fecha inicio\n";
		if(fechafinalNull.equals("null"))error+= "Fecha Final\n";
		return error;
	}
	
	//Filtro Clientes
	public class Cat_Venta_De_Cascos_A_Proveedores_Filtro extends JDialog {
		Container cont = getContentPane();
		JLayeredPane panel = new JLayeredPane();
		public JTextField txtFiltro = new Componentes().text(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla <<<", 300, "String");

		@SuppressWarnings("rawtypes")
		public Class[] tipos(){
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
		
		int columnas = 3,checkbox=-1;
		public void init_tabla(){
	    	this.tabla.getColumnModel().getColumn(0).setMinWidth(30);		
	    	this.tabla.getColumnModel().getColumn(1).setMinWidth(300);
	    	this.tabla.getColumnModel().getColumn(2).setMinWidth(410);
	    	
			String comando="select cod_prv as folio,razon_social as proveedor,calle+' No. EXTERIOR:'+num_exterior+' '+colonia+' C.P:'+cod_postal+' '+pobmunedo+' TELS:'+tel1+' FAX:'+fax as Domicilio from proveedores where status_proveedor =1 order by proveedor asc";
			String basedatos="200",pintar="si";
			new Obj_Refrescar(tabla,modelo, columnas, comando, basedatos,pintar,checkbox);
	    }
		
		
	 public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"Folio", "Nombre de Proveedor", "Descripción"}){
		 @SuppressWarnings("rawtypes")
			Class[] types = tipos();
			
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
	////////////////////////////////////////////////////////////////	
		
		Border blackline, etched, raisedbevel, loweredbevel, empty;
		public Cat_Venta_De_Cascos_A_Proveedores_Filtro(){
			
			this.setModal(true);
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Filter-List-icon16.png"));
			this.setTitle("Filtro de Proveedores");
			blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
			this.panel.setBorder(BorderFactory.createTitledBorder(blackline,"Busqueda y Seleccion del Proveedor"));
			
			this.panel.add(txtFiltro).setBounds(20,20,590,20);
			this.panel.add(scroll_tabla).setBounds(20,40,820,365);
			
			this.init_tabla();
			this.tabla.addMouseListener(opAgregar);
			this.tabla.addKeyListener(seleccionEmpleadoconteclado);
			this.txtFiltro.addKeyListener(op_filtro);
			this.cont.add(panel);
			this.setSize(870,450);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			
//	      pone el foco en el txtproveedor al presionar la tecla scape y limpia lo buscado
	        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "foco");
	        getRootPane().getActionMap().put("foco", new AbstractAction(){
	            @Override
	            public void actionPerformed(ActionEvent e)
	            {   txtFiltro.setText("");
	                txtFiltro.requestFocus(); }
	        });
//	      asigna el foco al JTextField del nombre deseado al arrancar la ventana
	        this.addWindowListener(new WindowAdapter() {
	                public void windowOpened( WindowEvent e ){  	txtFiltro.requestFocus();   }
	        });
	        
		}
		
		MouseListener opAgregar = new MouseListener() {
			public void mouseReleased(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseClicked(MouseEvent arg0) {
				if(arg0.getClickCount() == 2){
	    			int fila = tabla.getSelectedRow();
	    			String folio =  tabla.getValueAt(fila,0).toString().trim();
	    			String Proveedor = tabla.getValueAt(fila,1).toString().trim();
	    			
	    			txtFolioProveedor.setText(folio);
	    			txtProveedor.setText(Proveedor);
	    			dispose();
	        	}
			}
		};
		
		KeyListener op_filtro = new KeyListener(){
			public void keyReleased(KeyEvent arg0) {
				int[] columnas ={0,1,2};
				new Obj_Filtro_Dinamico_Plus(tabla, txtFiltro.getText().toUpperCase(), columnas);
			}
			public void keyTyped(KeyEvent arg0)   {}
			public void keyPressed(KeyEvent arg0) {}		
		};
			
			KeyListener seleccionEmpleadoconteclado = new KeyListener() {
				@SuppressWarnings("static-access")
				@Override
				public void keyTyped(KeyEvent e) {
					char caracter = e.getKeyChar();
						if(caracter==e.VK_ENTER){
					int fila=tabla.getSelectedRow()-1;
					String folio = tabla.getValueAt(fila,0).toString().trim();
					String proveedor = tabla.getValueAt(fila,1).toString().trim();
					
					new Cat_Venta_De_Cascos_A_Proveedores(folio, proveedor).setVisible(true);
					dispose();
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
			new Cat_Reportes_De_Bonificacion_De_Proveedores().setVisible(true);
		}catch(Exception e){	}
	}

}
