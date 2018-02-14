package Cat_Contabilidad;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

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
import Obj_Contabilidad.Obj_Indicadores;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.Obj_Filtro_Dinamico_Plus;
import Obj_Renders.tablaRenderer;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

import com.toedter.calendar.JDateChooser;

@SuppressWarnings("serial")
public class Cat_Indicadores extends JFrame {
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	Connexion con = new Connexion();
	
	static JDateChooser c_inicio = new JDateChooser();
	static JDateChooser c_final = new JDateChooser();
	
	String operador[] =new Obj_Indicadores().Combo_Indicadores();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbConcepto = new JComboBox(operador);
	
	String establecimiento[] = new Obj_Establecimiento().Combo_Establecimientos("R");
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbEstablecimiento = new JComboBox(establecimiento);
	
	JCButton btn_buscar = new JCButton  ("Generar Indicadores","buscar-buscar-ampliar-icono-6234-32.png","Azul");
	JCButton btn_Origen = new JCButton  ("Generar Reporte","encontrar-busqueda-lupa-de-la-ventana-de-zoom-icono-4008-16.png","Naranja");
	JLabel JLBlinicio= new JLabel(new ImageIcon("Imagen/iniciar-icono-4628-16.png") );
	JLabel JLBfin= new JLabel(new ImageIcon("Imagen/acabado-icono-7912-16.png") );
	JLabel JLBFactor= new JLabel();
	
	int cantidad_de_columnas =  (new BuscarSQL().Numero_De_Establecimientos_edo_Resultados()+3);
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
		int a=100,b=300;
		tabla.getColumnModel().getColumn(0).setHeaderValue(".");
		tabla.getColumnModel().getColumn(0).setMaxWidth(2);
		tabla.getColumnModel().getColumn(0).setMinWidth(2);
		tabla.getColumnModel().getColumn(1).setHeaderValue("INDICADOR");
		tabla.getColumnModel().getColumn(1).setMaxWidth(b*2);
		tabla.getColumnModel().getColumn(1).setMinWidth(b);
		try {
			String[] competidor = new BuscarSQL().Vector_De_Establecimientos_Edo_Resultados();
			for(int i=2; i<cantidad_de_columnas-1; i++){
				tabla.getColumnModel().getColumn(i).setHeaderValue(competidor[(i-2)].toString());
				tabla.getColumnModel().getColumn(i).setMinWidth(a);
				tabla.getColumnModel().getColumn(i).setMaxWidth(a+b);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		tabla.getColumnModel().getColumn(cantidad_de_columnas-1).setHeaderValue("TOTAL");
		tabla.getColumnModel().getColumn(cantidad_de_columnas-1).setMaxWidth(a);
		tabla.getColumnModel().getColumn(cantidad_de_columnas-1).setMinWidth(a+b);
		
    	tabla.getTableHeader().setReorderingAllowed(false) ;
    	tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		 JScrollPane scrol = new JScrollPane(tabla);
	    return scrol; 
	}
    
	Border blackline, etched, raisedbevel, loweredbevel, empty;	
	String parametroGeneral = "";
	String Lista="";
	String factor="";
	Obj_Usuario usuario = new Obj_Usuario().LeerSession();
	public Cat_Indicadores(){
		int ancho = Toolkit.getDefaultToolkit().getScreenSize().width;
		this.setSize(ancho,590);
		cont.add(panel);
		setResizable(false);
		setLocationRelativeTo(null);
		setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/diferiencia_de_sueldos_entre_listas_de_raya2_64.png"));
		setTitle("Indicadores");
		panel.setBorder(BorderFactory.createTitledBorder("Seleccione Las Fechas Y De Click a Buscar"));
		
		int x=15,y=25,l=100,a=20;

		this.panel.add(new JLabel("Fecha Inicial:")).setBounds(x   ,y    ,l  ,a);
		this.panel.add(JLBlinicio).setBounds                  (x+60,y    ,a  ,a);
		this.panel.add(c_inicio).setBounds                    (x+80,y    ,l  ,a);
		
		this.panel.add(JLBFactor).setBounds                   (x+200,y-15 ,l*5  ,a);
		this.panel.add(btn_buscar).setBounds                  (x+200,y+10,200,a*2);
		
		this.panel.add(cmbConcepto).setBounds                 (x+500,y-15 ,350,a);
		this.panel.add(cmbEstablecimiento).setBounds          (x+500,y+10 ,350,a);
		this.panel.add(btn_Origen  ).setBounds                (x+500,y+35 ,350,a);
		
		this.panel.add(new JLabel("Fecha Final:")).setBounds  (x   ,y+=30,l  ,a);
		this.panel.add(JLBfin).setBounds                      (x+60,y    ,a  ,a);
		this.panel.add(c_final).setBounds                     (x+80,y    ,l  ,a);

		panel.add(getPanelTabla()).setBounds(10,y+=30,ancho-30,460);
        
        c_inicio.setDate(cargar_fechas(1));
        c_final.setDate(cargar_fechas(0));
        render_tabla();
        
        
        btn_buscar.setEnabled(false);
        
        
		btn_buscar.addActionListener(op_generar);
		btn_Origen.addActionListener(opGenerarReporte_de_concepto);
//		c_inicio.getDateEditor().addPropertyChangeListener(opfactorytasa);
		cmbConcepto.addActionListener(opValidaIndicador);
		   
        this.addWindowListener(new WindowAdapter() { public void windowOpened( WindowEvent e ){
            	c_inicio.requestFocus();
                      } });
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
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String[][] calculo_fila_final(){
		int cantidad_de_columnas_matriz=(cantidad_de_columnas-1);
		Vector vector = new Vector();
		for(int i=0; i<tabla.getRowCount(); i++){
			 if(Boolean.valueOf(tabla.getValueAt(i,5).toString().trim())){
				  vector.add(model.getValueAt(i,0).toString().trim());
				  vector.add(model.getValueAt(i,1).toString().trim());
		     }
		}
			String[][] matriz = new String[vector.size()/cantidad_de_columnas_matriz][cantidad_de_columnas_matriz];
			 int i=0,j =0,columnafor=0;
			while(i<vector.size()){
				columnafor=0;
			      for(int f =0;  f<cantidad_de_columnas_matriz;  f++,columnafor++,i++  ){	
			  matriz[j][columnafor] = vector.get(i).toString();
			  }
			  j++;
		}
		return matriz;
	}

	public String validar_fechas(){
		String error = "";
		String fechainicioNull = c_inicio.getDate()+"";
	    if(fechainicioNull.equals("null"))error+= "Fecha  inicio\n";		
		return error;
	}
	
//	PropertyChangeListener opfactorytasa = new PropertyChangeListener() {
// 	     public void propertyChange(PropertyChangeEvent e) {
// 	    	 if ("date".equals(e.getPropertyName())) {
// 	    		if(c_inicio.getDate() != null){
// 	    			cargar_factor();	
// 	    		}
// 	    	 }
// 	      }
// 	  };
 	  
	  	  
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
	
	ActionListener op_generar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
		  if(validar_fechas().equals("")){
				  String fecha_inicio = new SimpleDateFormat("dd/MM/yyyy").format(c_inicio.getDate())+" 00:00:00";
				  String fecha_final  = new SimpleDateFormat("dd/MM/yyyy").format(c_final.getDate())+"  23:59:00";
				  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm"); 
				
				  Date fecha1 = sdf.parse(fecha_inicio , new ParsePosition(0));
				  Date fecha2 = sdf.parse(fecha_final , new ParsePosition(0));

				if(fecha1.before(fecha2)){
//				  BuscarSQL  matriz_resultados = new BuscarSQL();		
//							while(tabla.getRowCount()>0){model.removeRow(0);}
//							Object[][] matriz_tabla;
//							try {
//								matriz_tabla = matriz_resultados.Reporte_De_Estado_de_Resultados(cantidad_de_columnas,fecha_inicio,fecha_final);
//								if(matriz_tabla.length==0){
//									JOptionPane.showMessageDialog(null, "No se encontraron registros con las condiciones de busqueda proporcionada","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
//									return;
//								}else{
//									 String[] fila = new String[cantidad_de_columnas];
//										for(int i=0; i<matriz_tabla.length; i++){
//											for(int j=0; j<cantidad_de_columnas; j++){
//												fila[j] = matriz_tabla[i][j ]+"";
//											}
//											model.addRow(fila);
//										}
										
											tabla.moveColumn(tabla.getColumnModel().getColumnIndex("SUPER_I"),               2);
											tabla.moveColumn(tabla.getColumnModel().getColumnIndex("SUPER_II"),              3);
											tabla.moveColumn(tabla.getColumnModel().getColumnIndex("SUPER_III"),             4);
											tabla.moveColumn(tabla.getColumnModel().getColumnIndex("SUPER_IV"),              5);
											tabla.moveColumn(tabla.getColumnModel().getColumnIndex("SUPER_V"),               6);
											tabla.moveColumn(tabla.getColumnModel().getColumnIndex("REFACCIONARIA"),         7);
											tabla.moveColumn(tabla.getColumnModel().getColumnIndex("FERRETERIA"),            8);
											tabla.moveColumn(tabla.getColumnModel().getColumnIndex("FIESTILANDIA"),          9);
											tabla.moveColumn(tabla.getColumnModel().getColumnIndex("PAPER_CITY"),           10);
											tabla.moveColumn(tabla.getColumnModel().getColumnIndex("PAPER_CITY_II"),        11);
											tabla.moveColumn(tabla.getColumnModel().getColumnIndex("ESPACIO_35"),           12);
											tabla.moveColumn(tabla.getColumnModel().getColumnIndex("IZAGAR_FOOD_SUPER_II"), 13);
											tabla.moveColumn(tabla.getColumnModel().getColumnIndex("IZAGAR_FOOD_SUPER_IV"), 14);
											tabla.moveColumn(tabla.getColumnModel().getColumnIndex("NAPOLITANA"),           15);
											tabla.moveColumn(tabla.getColumnModel().getColumnIndex("DEPOSITO_II"),          16);
											tabla.moveColumn(tabla.getColumnModel().getColumnIndex("IZACEL"),               17);
											tabla.moveColumn(tabla.getColumnModel().getColumnIndex("CEDIS"),                18);
//								}	
//							} catch (SQLException e1) {
//								e1.printStackTrace();
//							}
				}else{
							JOptionPane.showMessageDialog(null,"El Rango de Fechas Esta Invertido","Aviso!", JOptionPane.WARNING_MESSAGE);
							return;
				}
			}
		}
	};
	
	boolean bandera = false;
	ActionListener opValidaIndicador = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(cmbConcepto.getSelectedItem().toString().trim().equals("Ausentismo") || cmbConcepto.getSelectedItem().toString().trim().equals("Valor De Nomina") || cmbConcepto.getSelectedItem().toString().trim().equals("Surtido De Pedido En Tiempo")){
				
				bandera = true;
				
				cmbEstablecimiento.setSelectedIndex(0);
				cmbEstablecimiento.setEnabled(false);
				
				c_final.setEnabled(cmbConcepto.getSelectedItem().toString().trim().equals("Surtido De Pedido En Tiempo")?true:false);
				
				
			}else{
				
				bandera = false;
				
				if(cmbConcepto.getSelectedItem().toString().trim().equals("Plantilla De Personal")){
					cmbEstablecimiento.setSelectedIndex(0);
					cmbEstablecimiento.setEnabled(false);
					c_inicio.setEnabled(false);
					c_final.setEnabled(false);
					new Cat_Filtro_Lista_de_Raya_Pasadas().setVisible(true);
				}else{
					cmbEstablecimiento.setSelectedIndex(0);
					cmbEstablecimiento.setEnabled(true);
					c_inicio.setEnabled(true);
					c_final.setEnabled(true);
				}
				
			}
		}
	};
	
	ActionListener opGenerarReporte_de_concepto = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			if(bandera){
				
				int anio = Integer.valueOf(new SimpleDateFormat("yyyy").format(c_inicio.getDate()));
				int mes = Integer.valueOf(new SimpleDateFormat("MM").format(c_inicio.getDate()));
				if(anio>=2016){
					
				if((anio==2016 && mes<=2)){
					JOptionPane.showMessageDialog(null, "Solo Se Pueden Consultar El Reporte De Ausentismo A Partir del Mes De Marzo Del 2016","Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
			          return;
					}else{
						
						switch(cmbConcepto.getSelectedItem().toString().trim()){
							case "Ausentismo": Cat_Reporte_De_Ausentismo(anio+"",mes+""); break;
							case "Valor De Nomina": Cat_Reporte_De_Valor_De_Nomina(anio+"",mes+""); break;
							case "Surtido De Pedido En Tiempo": Cat_Reporte_De_Surtido_De_Pedido_En_Tiempo(); break;
							default: JOptionPane.showMessageDialog(null, "No Se Encontro El Indicador","Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png")); break;
						}
						
					}
					
				}else{
		  	  JOptionPane.showMessageDialog(null, "Solo Se Pueden Consultar El Reporte De Ausentismo A Partir del Mes De Marzo Del 2016","Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
	          return;
			}
				
			}else{
						 if(validar_fechas().equals("")){
							  String fecha_inicio = new SimpleDateFormat("dd/MM/yyyy").format(c_inicio.getDate())+" 00:00:00";
							  String fecha_final  = new SimpleDateFormat("dd/MM/yyyy").format(c_final.getDate())+"  23:59:00";
							  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm"); 
							  Date fecha1 = sdf.parse(fecha_inicio , new ParsePosition(0));
							  Date fecha2 = sdf.parse(fecha_final , new ParsePosition(0));
							  if(fecha1.before(fecha2)){		  
								  	if(cmbConcepto.getSelectedItem().toString().trim().equals("Selecciona Un Concepto")){
								  		JOptionPane.showMessageDialog(null,"Es Necesario Seleccionar Un Concepto Para Poder Generar El Reporte Del Concepto", "Aviso!",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));	
								  		return;
								  	}else{
//									if(cmbEstablecimiento.getSelectedItem().toString().trim().equals("Selecciona un Establecimiento")){	
//										  JOptionPane.showMessageDialog(null,"Es Necesario Seleccionar Un Establecimiento Para Poder Generar El Reporte Del Concepto ", "Aviso!",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));	
//										  return;
//									}else{
										int testigo=0;
										String basedatos="2.26";
										String vista_previa_reporte="no";
										int vista_previa_de_ventana=0;
										String comando="";
										String reporte ="";
										
										if(!cmbConcepto.getSelectedItem().toString().trim().equals("Selecciona Un Indicador")){
											Obj_Indicadores indicador = new Obj_Indicadores().buscar(cmbConcepto.getSelectedItem().toString().trim());
											comando=indicador.getProcedimiento_almacenado()+" '"+fecha_inicio+"','"+fecha_final+"','"+usuario.getNombre_completo()+"','"+cmbEstablecimiento.getSelectedItem().toString().trim()+"'";
											System.out.println(comando);
											
											reporte =indicador.getReporte();
											testigo=1;
										}
										
										if(cmbConcepto.getSelectedItem().toString().trim().equals("Plantilla De Personal")){
											Obj_Indicadores indicador = new Obj_Indicadores().buscar(cmbConcepto.getSelectedItem().toString().trim());
											comando=indicador.getProcedimiento_almacenado()+" '"+fecha_inicio+"'";
											System.out.println(comando);
											
											reporte =indicador.getReporte();
											testigo=1;
										}
										
										if(cmbConcepto.getSelectedItem().toString().trim().equals("Indice De Rotacion De Personal Colaboradores Por Lista De Raya")||cmbConcepto.getSelectedItem().toString().trim().equals("Indice De Rotacion De Personal Colaboradores Por Lista De Raya Totales")){
											Obj_Indicadores indicador = new Obj_Indicadores().buscar(cmbConcepto.getSelectedItem().toString().trim());
											comando=indicador.getProcedimiento_almacenado()+" '"+fecha_inicio+"','"+fecha_final+"','"+usuario.getNombre_completo()+"','"+cmbEstablecimiento.getSelectedItem().toString().trim()+"','"+cmbConcepto.getSelectedItem().toString().trim()+"'";
											
											reporte =indicador.getReporte();
											testigo=1;
										}
										
									    if(testigo==1){
											new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
									    }else{
													JOptionPane.showMessageDialog(null,"Error Concepto No Identificado","Avisa Al Adimistrador Del Sistema!", JOptionPane.ERROR_MESSAGE);
													return;
											 }
				//					}
								}		
				 	 }else{
						JOptionPane.showMessageDialog(null,"El Rango de Fechas Esta Invertido","Aviso!", JOptionPane.WARNING_MESSAGE);
						return;
						}
					}
			}
		}
	};
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void Cat_Reporte_De_Ausentismo(String anio,String mes) {
		
		try {
			JasperReport report = JasperCompileManager.compileReport(System.getProperty("user.dir")+"\\src\\Obj_Reportes\\Obj_Reporte_De_Ausentismo_En_Lista_De_Raya.jrxml");
			
			Map parametro = new HashMap();
			parametro.put("anio", anio);
			parametro.put("mes", mes);
			
			JasperPrint print = JasperFillManager.fillReport(report, parametro, new Connexion().conexion());
			JasperViewer.viewReport(print, false);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(null, "Error En Cat_Reporte_De_Corte_De_Caja ", "Error !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
		}
	}
	
	public static void Cat_Reporte_De_Surtido_De_Pedido_En_Tiempo() {
		
		try {
			String basedatos="2.26";
			String vista_previa_reporte="no";
			int vista_previa_de_ventana=0;

			String comando="exec sp_select_pedidos_en_tiempo_especifico '"+(new SimpleDateFormat("dd/MM/yyyy").format(c_inicio.getDate()))+"','"+(new SimpleDateFormat("dd/MM/yyyy").format(c_final.getDate()))+"'";
			String reporte = "Obj_Reporte_De_Surtido_De_Pedido_En_Tiempo_Especifico.jrxml";
			new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(null, "Error En Cat_Reporte_De_Corte_De_Caja ", "Error !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
		}
	}
	
	public static void Cat_Reporte_De_Valor_De_Nomina(String anio,String mes) {
		
		try {
			String basedatos="2.26";
			String vista_previa_reporte="no";
			int vista_previa_de_ventana=0;
			
			String comando="exec sp_select_valor_de_nomina '"+anio+"','"+mes+"'";
			String reporte = "Obj_Reporte_De_Valor_De_Nomina.jrxml";
			new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
			 
		} catch (Exception e) {
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(null, "Error En Cat_Reporte_De_Corte_De_Caja ", "Error !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
		}
	}
	
	public class Cat_Filtro_Lista_de_Raya_Pasadas extends JDialog {
		
		Container cont = getContentPane();
		JLayeredPane campo = new JLayeredPane();
		
		Connexion con = new Connexion();
		
		DefaultTableModel model = new DefaultTableModel(0,2){
			public boolean isCellEditable(int fila, int columna){
				if(columna < 0)
					return true;
				return false;
			}
		};
		
		JTable tabla = new JTable(model);
		
		@SuppressWarnings("rawtypes")
		private TableRowSorter trsfiltro;
		
		JTextField txtFiltro = new Componentes().text(new JTextField(), "Teclee Folio o Fecha De Lista De Raya", 20, "String");
	    
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Cat_Filtro_Lista_de_Raya_Pasadas()	{
			this.setModal(true);
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/filter_icon&16.png"));
			this.setTitle("Consulta de Listas de Raya Pasadas");

			campo.setBorder(BorderFactory.createTitledBorder("Listas de Rayas Pasadas"));
			trsfiltro = new TableRowSorter(model); 
			tabla.setRowSorter(trsfiltro);  
			
			campo.add(getPanelTabla()).setBounds(15,42,200,367);
			
			campo.add(txtFiltro).setBounds(15,20,200,20);
			
			agregar(tabla);
			
			cont.add(campo);
			
			txtFiltro.addKeyListener(opFiltro);
			
			this.setSize(240,450);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			
		}
		
		private void agregar(final JTable tbl) {
	        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
		        public void mouseClicked(MouseEvent e) {
		        	if(e.getClickCount() == 2){
		        		int fila = tabla.getSelectedRow();
		    			try {
		    					Date fecha = new SimpleDateFormat("dd/MM/yyyy").parse(tabla.getValueAt(fila, 1).toString().trim());
		    					c_inicio.setDate(fecha);
		    					
		    					dispose();
		    					
						} catch (ParseException e1) {
							e1.printStackTrace();
						}
		        	}
		        }
	        });
	    }
		
		KeyListener opFiltro = new KeyListener(){
			public void keyReleased(KeyEvent arg0) {
				
				int[] columnas = {1,2};
				new Obj_Filtro_Dinamico_Plus(tabla, txtFiltro.getText().toString().toLowerCase(), columnas);
			}
			public void keyTyped(KeyEvent arg0) {}	
			public void keyPressed(KeyEvent arg0) {}		
		};
		
	   	@SuppressWarnings("unused")
		private JScrollPane getPanelTabla()	{		
			
			DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
			tcr.setHorizontalAlignment(SwingConstants.CENTER);
			
			tabla.getColumnModel().getColumn(0).setHeaderValue("Número Lista");
			tabla.getColumnModel().getColumn(0).setMaxWidth(60);
			tabla.getColumnModel().getColumn(0).setMinWidth(60);
			tabla.getColumnModel().getColumn(1).setHeaderValue("Fecha Creada");
			tabla.getColumnModel().getColumn(1).setMaxWidth(120);
			tabla.getColumnModel().getColumn(1).setMinWidth(120);
			
			tabla.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12));
			tabla.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","centro","Arial","normal",12));
			
			Statement s;
			ResultSet rs;
			try {
				s = con.conexion().createStatement();
				rs = s.executeQuery("select numero_lista,fecha "
						+ " from tb_lista_raya "
						+ " where numero_lista >= 156 "
						+ " group by numero_lista,fecha "
						+ " order by numero_lista desc");
				String [] fila = new String[2];
				DecimalFormat format =  new DecimalFormat("#0.00");
				while (rs.next()) {
				   fila[0] = rs.getString(1)+"  ";
				   fila[1] = "   "+rs.getString(2);
				   
			  	   model.addRow(fila); 
				}	
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			 JScrollPane scrol = new JScrollPane(tabla);
			   
		    return scrol; 
		}
		
	}
	
	
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Indicadores().setVisible(true);
		}catch(Exception e){	}
	}

}
