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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
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
import javax.swing.SwingConstants;
import javax.swing.UIManager;
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
import Obj_Renders.CaveceraTablaRenderer;
import Obj_Renders.tablaRenderer;

import com.toedter.calendar.JDateChooser;


@SuppressWarnings("serial")
public class Cat_Reportes_De_Movimientos_En_Cuentas_De_Ahorro extends JFrame {
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JDateChooser c_inicio = new JDateChooser();
	JDateChooser c_final = new JDateChooser();
	
	String establecimiento[] = new Obj_Establecimiento().Combo_Establecimiento();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbEstablecimiento = new JComboBox(establecimiento);

	JTextField txtFolioClientes = new Componentes().text(new JCTextField(), "Folio", 10, "Int");
	JTextField txtClientes = new Componentes().text(new JCTextField(), "Cliente", 200, "String");
	
	JCButton btn_generar = new JCButton  ("Generar","contrato-de-acuerdo-de-acuerdo-de-la-mano-encuentros-socio-icono-7428-32.png","Azul");
	
	JCButton btn_filtro_cliente = new JCButton  ("","buscar.png","Azul");
	JCButton btn_limpiar_cliente = new JCButton  ("","deshacer16.png","Azul");
	
	JLabel JLBlinicio= new JLabel(new ImageIcon("Imagen/iniciar-icono-4628-16.png") );
	JLabel JLBfin= new JLabel(new ImageIcon("Imagen/acabado-icono-7912-16.png") );
	JLabel JLBestablecimiento= new JLabel(new ImageIcon("Imagen/folder-home-home-icone-5663-16.png") );
	
	Obj_Usuario usuario = new Obj_Usuario().LeerSession();
	
	public Cat_Reportes_De_Movimientos_En_Cuentas_De_Ahorro(){
		this.setSize(560,155);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/contrato-de-acuerdo-de-acuerdo-de-la-mano-encuentros-socio-icono-7428-48.png"));
		this.setTitle("Reporte De Movimientos En Cuentas De Ahorro");
		this.panel.setBorder(BorderFactory.createTitledBorder("Movimientos En Cuentas De Ahorro"));
		
		this.panel.add(new JLabel("Fecha Inicio:")).setBounds(15,25,100,20);
		this.panel.add(JLBlinicio).setBounds(75,25,20,20);
		this.panel.add(c_inicio).setBounds(95,25,100,20);
		this.panel.add(new JLabel("Fecha Final:")).setBounds(15,55,100,20);
		this.panel.add(JLBfin).setBounds(75,55,20,20);
		this.panel.add(c_final).setBounds(95,55,100,20);
	    this.panel.add(new JLabel("Establecimiento:")).setBounds(200,25,150,20);
	    this.panel.add(JLBestablecimiento).setBounds(280,25,20,20);
		this.panel.add(cmbEstablecimiento).setBounds(300,25,235,20);
		
		this.panel.add(txtFolioClientes).setBounds(200,55,50,20);
		this.panel.add(txtClientes).setBounds(250,55,230,20);
		this.panel.add(btn_filtro_cliente).setBounds(480,55,25,20);
		this.panel.add(btn_limpiar_cliente).setBounds(510,55,25,20);
		
		this.panel.add(btn_generar).setBounds(160,90,270,25);
		
		txtFolioClientes.setEditable(false);
		txtClientes.setEditable(false);
		
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
			txtFolioClientes.setText("");
			txtClientes.setText("");
		}
	};
	
	ActionListener op_Filtro = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			new Cat_Filtro_Clientes().setVisible(true);
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
				
				String Establecimiento = cmbEstablecimiento.getSelectedItem().toString();
				String folio_cliente =  txtFolioClientes.getText().trim();
		
//                String Usuario =  usuario.getNombre_completo();
				
				
                
                if(fecha1.before(fecha2)){
    					Reporte_de_mov(fecha_inicio,fecha_final,Establecimiento,folio_cliente
    							
    							);
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
	
	public void Reporte_de_mov(String fecha_inicio, String fecha_final,String Establecimiento,String cliente){
		 reporte = "Obj_Reporte_De_Cuentas_De_Ahorro_Con_Movimiento_Historico.jrxml";
		 comando = "exec sp_select_cuentas_de_ahorro_con_movimiento_historico '"+fecha_inicio+"','"+fecha_final+"','"+Establecimiento+"','"+cliente+"'";
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
	public class Cat_Filtro_Clientes extends JDialog{
		
		Container cont = getContentPane();
		JLayeredPane campo = new JLayeredPane();
		
		Connexion con = new Connexion();
		
		DefaultTableModel model = new DefaultTableModel(0,3){
			public boolean isCellEditable(int fila, int columna){
				if(columna < 0)
					return true;
				return false;
			}
		};
		
		JTable tabla = new JTable(model);
		
		@SuppressWarnings("rawtypes")
		private TableRowSorter trsfiltro;
		
		JLabel lblBuscar = new JLabel("BUSCAR : ");
		JTextField txtBuscar = new Componentes().text(new JTextField(), "Buscar", 130, "String");
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Cat_Filtro_Clientes(){
			this.setModal(true);
			this.setTitle("Filtro Clientes");
			
//			cont.setBackground(new Color(0,17 ,255));
//			lblBuscar.setForeground(Color.white);
			
			txtBuscar.addKeyListener(new KeyAdapter() { 
				public void keyReleased(final KeyEvent e) { 
	                filtro(); 
	            } 
	        });
		
			trsfiltro = new TableRowSorter(model); 
			tabla.setRowSorter(trsfiltro);  
			
			campo.add(getPanelTabla()).setBounds(10,70,365,450);
			
			agregar(tabla);
			
			campo.add(lblBuscar).setBounds(30,30,70,20);
			campo.add(txtBuscar).setBounds(95,30,215,20);
			
			cont.add(campo);
			
			this.setSize(390,570);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			
			llamar_render();
		}
		
		public void llamar_render(){
			//		tipo de valor = imagen,chb,texto
//			tabla.getColumnModel().getColumn(# columna).setCellRenderer(new CellRenderer("tipo_de_valor","alineacion","tipo_de_letra","negrita",# tamanio_fuente));
	    
			Color fondoEncabezado = new Color(255,171,0);
			Color textoEncabezado = Color.black;
			
			for(int i = 0; i<tabla.getColumnCount(); i++){
				tabla.getColumnModel().getColumn(i).setHeaderRenderer(new CaveceraTablaRenderer(fondoEncabezado,textoEncabezado,"centro","Arial","negrita",16));
				tabla.getColumnModel().getColumn(i ).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",12));
			}
		}
		
		private void agregar(final JTable tbl) {
	        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
		        public void mouseClicked(MouseEvent e) {
		        	if(e.getClickCount() == 2){
		        		
		        		
		        		int fila = tabla.getSelectedRow();
		    			String folio =  tabla.getValueAt(fila, 0).toString().trim();
		    			String nombre =  tabla.getValueAt(fila, 1).toString().trim();
		    			dispose();
		    			
		    			txtFolioClientes.setText(folio);
		    			txtClientes.setText(nombre);

		        	}
		        }
	        });
	    }
		
		@SuppressWarnings("unchecked")
		public void filtro() { 
				trsfiltro.setRowFilter(RowFilter.regexFilter(txtBuscar.getText().toUpperCase().trim(), 1));
		}  
		
		private JScrollPane getPanelTabla()	{		
			new Connexion();
			
			DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
			tcr.setHorizontalAlignment(SwingConstants.CENTER);
			
			tabla.getColumnModel().getColumn(0).setCellRenderer(tcr);
			
			tabla.getColumnModel().getColumn(0).setHeaderValue("Folio");
			tabla.getColumnModel().getColumn(0).setMaxWidth(70);
			tabla.getColumnModel().getColumn(0).setMinWidth(70);
			tabla.getColumnModel().getColumn(1).setHeaderValue("Cliente");
			tabla.getColumnModel().getColumn(1).setMaxWidth(185);
			tabla.getColumnModel().getColumn(1).setMinWidth(185);
			tabla.getColumnModel().getColumn(2).setHeaderValue("Direccion");
			tabla.getColumnModel().getColumn(2).setMaxWidth(100);
			tabla.getColumnModel().getColumn(2).setMinWidth(100);
			
			Statement s;
			ResultSet rs;
			try {
				s = con.conexion().createStatement();
				rs = s.executeQuery("select folio_cliente as folio_cliente, " +
									"nombre+' '+ap_paterno+' '+ap_materno as cliente, " +
									"direccion as direccion " +
									"from tb_clientes" );
				
				while (rs.next())
				{ 
				   String [] fila = new String[3];
				   fila[0] = rs.getString(1).trim();
				   fila[1] = rs.getString(2).trim();
				   fila[2] = rs.getString(3).trim();
				   
				   model.addRow(fila); 
				}	
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			 JScrollPane scrol = new JScrollPane(tabla);
			   
		    return scrol; 
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
			new Cat_Reportes_De_Movimientos_En_Cuentas_De_Ahorro().setVisible(true);
		}catch(Exception e){	}
	}

}
