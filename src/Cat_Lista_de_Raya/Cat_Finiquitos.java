package Cat_Lista_de_Raya;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.BuscarTablasModel;
import Conexiones_SQL.Generacion_Reportes;
import Obj_Lista_de_Raya.Obj_Finiquitos;
import Obj_Principal.Componentes;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_Filtro_Dinamico;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Finiquitos extends JDialog{

	Container cont_quitados = getContentPane();
	JLayeredPane panel_quitados = new JLayeredPane();
	
	JButton btnGenerar = new JButton("Generar", new ImageIcon("imagen/flecha-naranja-alerta-de-descarga-de-la-actualizacion-icono-8872-16.png"));
	
	JTextField txtFolioScoi 	= new Componentes().text(new JTextField(), "Folio De Empleado En Scoi", 120, "String");
	JTextField txtEmpleadoScoi 	= new Componentes().text(new JTextField(), "Nombre De Empleado Scoi", 120, "String");
	JTextField txtFolioBms 		= new Componentes().text(new JTextField(), "Folio De Empleado En Bms", 120, "String");
	JTextField txtEmpleadoBms 	= new Componentes().text(new JTextField(), "Nombre De Empleado Bms", 120, "String");
	
	JButton btnLimpiarEmpleadoBms = new JButton("Limpiar");
	
	JTextField txtFiltroAsignacion = new Componentes().text(new JCTextField(), ">> Teclee El Nombre Del Empleado <<", 120, "String");
	
	 public DefaultTableModel tabla_model_filtro_scoi = new DefaultTableModel(null, new String[]{"Folio","Empleado", "Establecimiento", "Puesto", "Status"} ){
         
			@SuppressWarnings({ "rawtypes" })
			Class[] types = new Class[]{
	                   java.lang.Object.class, 
	                   java.lang.Object.class, 
	                   java.lang.Object.class, 
	                   java.lang.Object.class,
	                   java.lang.Object.class
	                    
	    };
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {
	                return types[columnIndex];
	        }
	    public boolean isCellEditable(int fila, int columna){
	                switch(columna){
	                		case 0	: return false;
	                        case 1  : return false; 
	                        case 2  : return false; 
	                        case 3  : return false; 
	                        case 4  : return false; 
	                }
	                 return false;
	         }
	    };
		
	    JTable tabla_filtro_scoi = new JTable(tabla_model_filtro_scoi);
		JScrollPane scroll_filtro_scoi = new JScrollPane(tabla_filtro_scoi,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
	    public DefaultTableModel tabla_model_filtro_bnns = new DefaultTableModel(null, new String[]{"Folio","Empleado", "Establecimiento", "Puesto", "Status"} ){
            
			@SuppressWarnings({ "rawtypes" })
			Class[] types = new Class[]{
	                   java.lang.Object.class, 
	                   java.lang.Object.class, 
	                   java.lang.Object.class, 
	                   java.lang.Object.class,
	                   java.lang.Object.class
	                    
	    };
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {
	                return types[columnIndex];
	        }
	    public boolean isCellEditable(int fila, int columna){
	                switch(columna){
	                		case 0	: return false;
	                        case 1  : return false; 
	                        case 2  : return false; 
	                        case 3  : return false; 
	                        case 4  : return false; 
	                }
	                 return false;
	         }
	    };
		
	    JTable tabla_filtro_bnns = new JTable(tabla_model_filtro_bnns);
		JScrollPane scroll_filtro_bnns = new JScrollPane(tabla_filtro_bnns,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		DecimalFormat df = new DecimalFormat("#0.00");
		
//	cadenaCortesQuitados
	public Cat_Finiquitos(){
		this.setModal(true);
		this.setTitle("Filtro De Empleados Para Finiquitos");
		this.panel_quitados.setBorder(BorderFactory.createTitledBorder( "Filtro De Empleados Para Finiquitos"));
		
		panel_quitados.add(txtFiltroAsignacion).setBounds(70, 30, 300, 20);
		panel_quitados.add(scroll_filtro_scoi).setBounds(20, 50, 745, 200);
		panel_quitados.add(scroll_filtro_bnns).setBounds(20, 280, 745, 200);
		
		
		panel_quitados.add(new JLabel("Empleado Scoi:")).setBounds(20, 490, 120, 20);
		panel_quitados.add(txtFolioScoi 	    ).setBounds(120, 490, 50, 20);
		panel_quitados.add(txtEmpleadoScoi     ).setBounds(170, 490, 300, 20);
		
		panel_quitados.add(new JLabel("Empleado Bms:")).setBounds(20, 515, 120, 20);
		panel_quitados.add(txtFolioBms 		).setBounds(120, 515, 50, 20);
		panel_quitados.add(txtEmpleadoBms      ).setBounds(170, 515, 300, 20);
		panel_quitados.add(btnLimpiarEmpleadoBms).setBounds(470, 515, 30, 20);
		
		panel_quitados.add(btnGenerar).setBounds(650, 515, 115, 20);
		
		cont_quitados.add(panel_quitados);
		
		llenar_tabla_filtro(tabla_model_filtro_scoi,"SCOI");
		llenar_tabla_filtro(tabla_model_filtro_bnns,"BNNS");
		
		render_filtro(tabla_filtro_scoi);
		render_filtro(tabla_filtro_bnns);
		
		filtro(tabla_filtro_scoi);
		filtro(tabla_filtro_bnns);
		
		seleccionEmpleado(tabla_filtro_scoi, "SCOI");
		seleccionEmpleado(tabla_filtro_bnns, "Bnns");
		
		btnLimpiarEmpleadoBms.addActionListener(opLimpiar);
		btnGenerar.addActionListener(opGenerar);
		
		txtFolioScoi.setEditable(false);
		txtEmpleadoScoi.setEditable(false);
		txtFolioBms.setEditable(false);
		txtEmpleadoBms.setEditable(false);
		
		this.setSize(790,580);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
	}
	
	
	
	ActionListener opGenerar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(!txtFolioScoi.getText().equals("")){
				new Cat_Alimentacion_De_Finiquitos(txtFolioScoi.getText(), txtEmpleadoScoi.getText(), tabla_filtro_scoi.getValueAt(tabla_filtro_scoi.getSelectedRow(), 2).toString().trim(), txtFolioBms.getText()).setVisible(true);
			}else{
				System.out.println("Eviso # seleccione un empleado cuando cuando menos de la tabla de scoi #");
			}
		}
	};
	
	ActionListener opLimpiar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			txtFolioBms.setText("");
			txtEmpleadoBms.setText("");
		}
	};
	
	public void seleccionEmpleado(final JTable tb, final String nomTabla){
		tb.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
				
				if(nomTabla.equals("SCOI")){
					txtFolioScoi.setText(tb.getValueAt(tb.getSelectedRow(), 0).toString());
					txtEmpleadoScoi.setText(tb.getValueAt(tb.getSelectedRow(), 1).toString());
				}else{
					txtFolioBms.setText(tb.getValueAt(tb.getSelectedRow(), 0).toString());
					txtEmpleadoBms.setText(tb.getValueAt(tb.getSelectedRow(), 1).toString());
				}
			}
			public void mousePressed(MouseEvent e) {			}
			public void mouseExited(MouseEvent e) {			}
			public void mouseEntered(MouseEvent e) {			}
			public void mouseClicked(MouseEvent e) {			}
		});
	}

	
	public void render_filtro(final JTable tb){
		tb.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",10));
		for(int i = 1; i<tb.getColumnCount(); i++){
			switch(i){
					case 0: tb.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","centro"	,"Arial","negrita",11)); break;
					case 1: tb.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",9) ); break;
					case 2: tb.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",10)); break;
					case 3: tb.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",9) ); break;
					case 4: tb.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",10)); break;
					default:tb.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","derecha"	,"Arial","negrita",11)); break;
				}
		}
		
		tb.getTableHeader().setReorderingAllowed(false) ;
    	tb.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    	
    	int x=50;
    	
    	tb.getColumnModel().getColumn(0 ).setMaxWidth(x);   
    	tb.getColumnModel().getColumn(0 ).setMinWidth(x);   
    	tb.getColumnModel().getColumn(1 ).setMaxWidth(x*6);
    	tb.getColumnModel().getColumn(1 ).setMinWidth(x*6);
    	tb.getColumnModel().getColumn(2 ).setMaxWidth(x*3); 
    	tb.getColumnModel().getColumn(2 ).setMinWidth(x*3); 
    	                                          
    	tb.getColumnModel().getColumn(3 ).setMaxWidth(x*3);
    	tb.getColumnModel().getColumn(3 ).setMinWidth(x*3);		
    	tb.getColumnModel().getColumn(4 ).setMaxWidth(x*2);
    	tb.getColumnModel().getColumn(4 ).setMinWidth(x+25);
	}
	
	public void filtro(final JTable tb){
		txtFiltroAsignacion.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent arg0) {}
			public void keyReleased(KeyEvent arg0){
				new Obj_Filtro_Dinamico(tb,"Empleado", txtFiltroAsignacion.getText().toUpperCase(),"","", "", "", "", "");
			}
			public void keyPressed(KeyEvent arg0) {}
		});
	}
	
	public void llenar_tabla_filtro(final DefaultTableModel modelo,String baseDatos){
		
		modelo.setRowCount(0);
		
		String[][] matriz = new BuscarTablasModel().filtro_empleado_finiquito(baseDatos);
		for(String[] fila: matriz){
			modelo.addRow(fila);
		}
	}
	
	public static void main(String [] arg){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Finiquitos().setVisible(true);
		}catch(Exception e){
			System.err.println("Error :"+ e.getMessage());
		}
	}
	
	public class Cat_Alimentacion_De_Finiquitos extends JDialog{

		Container cont_quitados = getContentPane();
		JLayeredPane panel_quitados = new JLayeredPane();
		
		String folio_empleado_bms = "";
		JTextField   txtFolioEmpleado	= new Componentes().text(new JTextField(), "Folio Empleado", 10, "Int");
		JTextField   txtEmpleado		= new Componentes().text(new JTextField(), "Empleado", 155, "String");
		JTextField   txtEstablecimiento	= new Componentes().text(new JTextField(), "Establecimiento", 80, "String");
		
		
//		componentes De bms
		JDateChooser fchIngresoBnns 					= new JDateChooser();
		JDateChooser fchBajaBnns 						= new JDateChooser();
		
		JTextField   txtDiasTrabajadosBnns 				= new Componentes().text(new JTextField(), "Dias Trabajados", 10, "Int");
		JTextField   txtaniosTrabajadosBnns 			= new Componentes().text(new JTextField(), "A�os Trabajados", 15, "Double");
		JTextField   txtDiasPendienteDePagoAguinaldoBnns= new Componentes().text(new JTextField(), "Dias Pendientes De Pago De Aguinaldo", 10, "Int");
		JTextField   txtDiasPendienteDePagoSemanaBnns	= new Componentes().text(new JTextField(), "Dias Pendientes De Pago Semana", 10, "Int");
		JTextField   txtDiasCuotaDiarioBnns				= new Componentes().text(new JTextField(), "Cuota Diaria", 15, "Double");
		JTextField   txtSDIBnns							= new Componentes().text(new JTextField(), "SDI", 15, "Double");
		             
		JTextField   txtSueldoBnns						= new Componentes().text(new JTextField(), "Sueldo",  15, "Double");
		JTextField   txtAguinaldoBnns					= new Componentes().text(new JTextField(), "Aguinaldo",  15, "Double");
		JTextField   txtVacacionesPendienteBnns			= new Componentes().text(new JTextField(), "Vacaciones Pendientes",  15, "Double");
		JTextField   txtVacacionesBnns					= new Componentes().text(new JTextField(), "Vacaciones",  15, "Double");
		JTextField   txtPrimaVacacionalBnns				= new Componentes().text(new JTextField(), "Prima Vacacional",  15, "Double");
		             
		JTextField   txtGratificacionBnns				= new Componentes().text(new JTextField(), "Gratificacion", 15, "Double");
		JTextField   txtTiempoExtraBnns					= new Componentes().text(new JTextField(), "Tiempo Extra",  15, "Double");
		JTextField   txtTotalPercepcionesBnns			= new Componentes().text(new JTextField(), "Total Percepciones",  15, "Double");
		             
		JTextField   txtPrestamoBnns					= new Componentes().text(new JTextField(), "Prestamo",  15, "Double");
		JTextField   txtCortesBnns						= new Componentes().text(new JTextField(), "Cortes",  15, "Double");
		JTextField   txtInfonavitBnns					= new Componentes().text(new JTextField(), "Infonavit",  15, "Double");
		JTextField   txtFuenteSodasBnns					= new Componentes().text(new JTextField(), "Fuente De Sodas",  15, "Double");
		JTextField   txtOtrasDeduccionesBnns			= new Componentes().text(new JTextField(), "Otras Deducciones",  15, "Double");
		             
		JTextField   txtTotalAPagarBnns					= new Componentes().text(new JTextField(), "Total A Pagar",  15, "Double");
			
		
//		componentes De scoi
		JDateChooser fchIngresoSCOI 					= new JDateChooser();
		JDateChooser fchBajaSCOI 						= new JDateChooser();
		
		JTextField   txtDiasTrabajadosSCOI 				= new Componentes().text(new JTextField(), "Dias Trabajados", 10, "Int");
		JTextField   txtaniosTrabajadosSCOI 			= new Componentes().text(new JTextField(), "A�os Trabajados", 15, "Double");
		JTextField   txtDiasPendienteDePagoAguinaldoSCOI= new Componentes().text(new JTextField(), "Dias Pendientes De Pago De Aguinaldo", 10, "Int");
		JTextField   txtDiasPendienteDePagoSemanaSCOI	= new Componentes().text(new JTextField(), "Dias Pendientes De Pago Semana", 1, "Int");
		JTextField   txtDiasCuotaDiarioSCOI				= new Componentes().text(new JTextField(), "Cuota Diaria", 15, "Double");
		             
		JTextField   txtSueldoSCOI						= new Componentes().text(new JTextField(), "Sueldo",  15, "Double");
		JTextField   txtAguinaldoSCOI					= new Componentes().text(new JTextField(), "Aguinaldo",  15, "Double");
		JTextField   txtVacacionesPendientesSCOI		= new Componentes().text(new JTextField(), "Vacaciones Pendientes",  15, "Double");
		JTextField   txtVacacionesSCOI					= new Componentes().text(new JTextField(), "Vacaciones",  15, "Double");
		JTextField   txtPrimaVacacionalSCOI				= new Componentes().text(new JTextField(), "Prima Vacacional",  15, "Double");
		             
		JTextField   txtTotalPercepcionesSCOI			= new Componentes().text(new JTextField(), "Total Percepciones",  15, "Double");
		             
//	diferencias (GRATIFICACION)
		 JTextField   txtSueldoDiferencia				= new Componentes().text(new JTextField(), "Sueldo",  15, "Double");          
		 JTextField   txtAguinaldoDiferencia			= new Componentes().text(new JTextField(), "Aguinaldo",  15, "Double");   
		 JTextField   txtVacacionesPendientesDiferencia	= new Componentes().text(new JTextField(), "Vacaciones Pendientes",  15, "Double"); 
		 JTextField   txtVacacionesDiferencia			= new Componentes().text(new JTextField(), "Vacaciones",  15, "Double");      
		 JTextField   txtPrimaVacacionalDiferencia		= new Componentes().text(new JTextField(), "Prima Vacacional",  15, "Double");
		 JTextField   txtTotalPercepcionesDiferencia	= new Componentes().text(new JTextField(), "Total Percepciones",  15, "Double");

		
		JButton btnLimpiarEmpleadoBms = new JButton("Limpiar");
		JButton btnGuardar = new JButton("Guardar", new ImageIcon("imagen/flecha-naranja-alerta-de-descarga-de-la-actualizacion-icono-8872-16.png"));
		
		
		public Cat_Alimentacion_De_Finiquitos(String folio_emp_scoi, String nombre_scoi, String establecimiento, String folio_emp_bms){
			this.setModal(true);
			this.setTitle("Calculo De Finiquitos");
			this.panel_quitados.setBorder(BorderFactory.createTitledBorder( "Calculo De Finiquitos"));
			this.setSize(610,620);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			
			folio_empleado_bms = folio_emp_bms;
			txtFolioEmpleado.setText(folio_emp_scoi);
			txtEmpleado.setText(nombre_scoi);
			txtEstablecimiento.setText(establecimiento);
			
			int x=15, x2=210, x3=340, x4=470, y=20, ancho=190, ancho2=120;
			
			panel_quitados.add(new JLabel("Empleado:")		 ).setBounds(x, y, 80, 20);
			panel_quitados.add(txtFolioEmpleado   			 ).setBounds(x+80, y, 60, 20);
			panel_quitados.add(txtEmpleado					 ).setBounds(x+140, y, 320, 20);
			panel_quitados.add(new JLabel("Establecimiento:")).setBounds(x, y+=25, 80, 20);
			panel_quitados.add(txtEstablecimiento  			 ).setBounds(x+80, y, 200, 20);
			
																													panel_quitados.add(new JLabel("BMS")	   				).setBounds(x2, y+=25, ancho2, 20);panel_quitados.add(new JLabel("SCOI")				).setBounds(x3, y, ancho2, 20);  panel_quitados.add(new JLabel("GRATIFICACION")		).setBounds(x4, y, ancho2, 20);    
			panel_quitados.add(new JLabel("Fecha De Ingreso:")   				).setBounds(x, y+=20, ancho, 20);   panel_quitados.add(fchIngresoBnns 					   	).setBounds(x2, y, ancho2, 20);   panel_quitados.add(fchIngresoSCOI 					).setBounds(x3, y, ancho2, 20);    
			panel_quitados.add(new JLabel("Fecha De Baja:")		   				).setBounds(x, y+=20, ancho, 20);   panel_quitados.add(fchBajaBnns 						   	).setBounds(x2, y, ancho2, 20);   panel_quitados.add(fchBajaSCOI 						).setBounds(x3, y, ancho2, 20);
                                                                                                                                                                                                                                                                                
			panel_quitados.add(new JLabel("Dias Trabajados:")					).setBounds(x, y+=25, ancho, 20);   panel_quitados.add(txtDiasTrabajadosBnns 				).setBounds(x2, y, ancho2, 20);   panel_quitados.add(txtDiasTrabajadosSCOI 				).setBounds(x3, y, ancho2, 20);
			panel_quitados.add(new JLabel("A�os Trabajados:")					).setBounds(x, y+=20, ancho, 20);   panel_quitados.add(txtaniosTrabajadosBnns 			   	).setBounds(x2, y, ancho2, 20);   panel_quitados.add(txtaniosTrabajadosSCOI 			).setBounds(x3, y, ancho2, 20);
			panel_quitados.add(new JLabel("Dias Pendiente De Pago De Aguinaldo:")).setBounds(x,y+=20, ancho,20);    panel_quitados.add(txtDiasPendienteDePagoAguinaldoBnns  ).setBounds(x2, y, ancho2, 20);   panel_quitados.add(txtDiasPendienteDePagoAguinaldoSCOI).setBounds(x3, y, ancho2, 20);
			panel_quitados.add(new JLabel("Dias Pendiente De Pago De La Semana:")).setBounds(x,y+=20, ancho,20);    panel_quitados.add(txtDiasPendienteDePagoSemanaBnns		).setBounds(x2, y, ancho2, 20);   panel_quitados.add(txtDiasPendienteDePagoSemanaSCOI	).setBounds(x3, y, ancho2, 20);
			panel_quitados.add(new JLabel("Cuota Diaria:")						).setBounds(x, y+=20, ancho, 20);   panel_quitados.add(txtDiasCuotaDiarioBnns				).setBounds(x2, y, ancho2, 20);   panel_quitados.add(txtDiasCuotaDiarioSCOI				).setBounds(x3, y, ancho2, 20);
			panel_quitados.add(new JLabel("S.D.I:")								).setBounds(x, y+=20, ancho, 20);   panel_quitados.add(txtSDIBnns							).setBounds(x2, y, ancho2, 20);   
                                                                                                                                                                                                                                                                                 
			panel_quitados.add(new JLabel("Sueldo:")							).setBounds(x, y+=25, ancho, 20);   panel_quitados.add(txtSueldoBnns						).setBounds(x2, y, ancho2, 20);   panel_quitados.add(txtSueldoSCOI						).setBounds(x3, y, ancho2, 20);   panel_quitados.add(txtSueldoDiferencia			).setBounds(x4, y, ancho2, 20);   
			panel_quitados.add(new JLabel("Aguinaldo:")							).setBounds(x, y+=20, ancho, 20);   panel_quitados.add(txtAguinaldoBnns					 	).setBounds(x2, y, ancho2, 20);   panel_quitados.add(txtAguinaldoSCOI					).setBounds(x3, y, ancho2, 20);   panel_quitados.add(txtAguinaldoDiferencia			).setBounds(x4, y, ancho2, 20);   
			panel_quitados.add(new JLabel("Vacaciones Pendientes:")				).setBounds(x, y+=20, ancho, 20);   panel_quitados.add(txtVacacionesPendienteBnns			).setBounds(x2, y, ancho2, 20);   panel_quitados.add(txtVacacionesPendientesSCOI		).setBounds(x3, y, ancho2, 20);   panel_quitados.add(txtVacacionesPendientesDiferencia).setBounds(x4, y, ancho2, 20);   
			panel_quitados.add(new JLabel("Vacaciones:")						).setBounds(x, y+=20, ancho, 20);   panel_quitados.add(txtVacacionesBnns					).setBounds(x2, y, ancho2, 20);   panel_quitados.add(txtVacacionesSCOI					).setBounds(x3, y, ancho2, 20);   panel_quitados.add(txtVacacionesDiferencia		).setBounds(x4, y, ancho2, 20);   
			panel_quitados.add(new JLabel("Prima Vacacional:")					).setBounds(x, y+=20, ancho, 20);   panel_quitados.add(txtPrimaVacacionalBnns				).setBounds(x2, y, ancho2, 20);   panel_quitados.add(txtPrimaVacacionalSCOI				).setBounds(x3, y, ancho2, 20);   panel_quitados.add(txtPrimaVacacionalDiferencia	).setBounds(x4, y, ancho2, 20);   
                                                                                                                                                                                                                                                                                
			panel_quitados.add(new JLabel("Gratificacion:")						).setBounds(x, y+=20, ancho, 20);   panel_quitados.add(txtGratificacionBnns				   	).setBounds(x2, y, ancho2, 20);   
			panel_quitados.add(new JLabel("Tiempo Extra:")						).setBounds(x, y+=20, ancho, 20);   panel_quitados.add(txtTiempoExtraBnns					).setBounds(x2, y, ancho2, 20);   
			panel_quitados.add(new JLabel("Percepciones:")		   				).setBounds(x, y+=20, ancho, 20);   panel_quitados.add(txtTotalPercepcionesBnns			   	).setBounds(x2, y, ancho2, 20);   panel_quitados.add(txtTotalPercepcionesSCOI			).setBounds(x3, y, ancho2, 20);	 panel_quitados.add(txtTotalPercepcionesDiferencia	).setBounds(x4, y, ancho2, 20);
                                                                                                                                                                                                    
			panel_quitados.add(new JLabel("DEDUCCIONES")   						).setBounds(x, y+=20, ancho, 20);                                                                                   
			panel_quitados.add(new JLabel("Prestamo:")			   				).setBounds(x, y+=25, ancho, 20);   panel_quitados.add(txtPrestamoBnns					   	).setBounds(x2, y, ancho2, 20);   
			panel_quitados.add(new JLabel("Cortes:")							).setBounds(x, y+=20, ancho, 20);   panel_quitados.add(txtCortesBnns						).setBounds(x2, y, ancho2, 20);   
			panel_quitados.add(new JLabel("Infonavit:")			   				).setBounds(x, y+=20, ancho, 20);   panel_quitados.add(txtInfonavitBnns					   	).setBounds(x2, y, ancho2, 20);   
			panel_quitados.add(new JLabel("Fuente De Sodas:")					).setBounds(x, y+=20, ancho, 20);   panel_quitados.add(txtFuenteSodasBnns					).setBounds(x2, y, ancho2, 20);   
			panel_quitados.add(new JLabel("Otras Deducciones:")	   				).setBounds(x, y+=20, ancho, 20);   panel_quitados.add(txtOtrasDeduccionesBnns			   	).setBounds(x2, y, ancho2, 20);   panel_quitados.add(btnGuardar							).setBounds(x3, y, ancho2*2, 45);   
                                                                                                                                                                                                            
			panel_quitados.add(new JLabel("Total A Pagar:")						).setBounds(x, y+=25, ancho, 20);   panel_quitados.add(txtTotalAPagarBnns					).setBounds(x2, y, ancho2, 20);   
			
			cont_quitados.add(panel_quitados);
			
			
			txtFolioEmpleado	.setEditable(false);                                                                                    
			txtEmpleado			.setEditable(false);                                                                           
			txtEstablecimiento	.setEditable(false);                                                                           
			                                                                                                                                                                                                
			                                                                                                                                                                                                
			fchIngresoBnns 						.setEnabled(false);                                       
//			fchBajaBnns 						.setEnabled(false);                                       

			txtDiasTrabajadosBnns 				.setEditable(false);                                       
			txtaniosTrabajadosBnns 				.setEditable(false);                                       
			txtDiasPendienteDePagoAguinaldoBnns.setEditable(false);                                       
			txtDiasPendienteDePagoSemanaBnns	.setEditable(false);                                       
			txtDiasCuotaDiarioBnns				.setEditable(false);                                       
			txtSDIBnns							.setEditable(false);                                       

			txtSueldoBnns						.setEditable(false);                                       
			txtAguinaldoBnns					.setEditable(false);                                       
			txtVacacionesBnns					.setEditable(false);                                       
			txtPrimaVacacionalBnns				.setEditable(false);                                       

			txtGratificacionBnns				.setEditable(false);                                       
//			txtTiempoExtraBnns					.setEditable(false);                                       
			txtTotalPercepcionesBnns			.setEditable(false);                                       

			txtPrestamoBnns						.setEditable(false);                                       
			txtCortesBnns						.setEditable(false);                                       
			txtInfonavitBnns					.setEditable(false);                                       
			txtFuenteSodasBnns					.setEditable(false);                                       
//			txtOtrasDeduccionesBnns				.setEditable(false);                                       

			txtTotalAPagarBnns					.setEditable(false);                                       

			fchIngresoSCOI 						.setEnabled(false);                                       
//			fchBajaSCOI 						.setEnabled(false);                                       

			txtDiasTrabajadosSCOI 				.setEditable(false);                                       
			txtaniosTrabajadosSCOI 				.setEditable(false);                                       
			txtDiasPendienteDePagoAguinaldoSCOI	.setEditable(false);                                       
//			txtDiasPendienteDePagoSemanaSCOI	.setEditable(false);                                       
			txtDiasCuotaDiarioSCOI				.setEditable(false);                                       

			txtSueldoSCOI						.setEditable(false);                                       
			txtAguinaldoSCOI					.setEditable(false);                                       
			txtVacacionesSCOI					.setEditable(false);                                       
			txtPrimaVacacionalSCOI				.setEditable(false);                                       

			txtTotalPercepcionesSCOI			.setEditable(false);                                       

			txtSueldoDiferencia					.setEditable(false);                                       
			txtAguinaldoDiferencia				.setEditable(false);  
			txtVacacionesPendientesDiferencia	.setEnabled(false);
			txtVacacionesDiferencia				.setEditable(false);                                       
			txtPrimaVacacionalDiferencia		.setEditable(false);                                       
			txtTotalPercepcionesDiferencia		.setEditable(false);                                       
			
			fchBajaBnns.setDate(cargar_fechas_de_baja(0));
			fchBajaSCOI.setDate(cargar_fechas_de_baja(0));
			
			buscar_finiquito();
			
			txtDiasPendienteDePagoSemanaSCOI.addKeyListener(opTeclearDias);
			txtTiempoExtraBnns.addKeyListener(opTeclear);
			txtOtrasDeduccionesBnns.addKeyListener(opTeclear);
			
			txtVacacionesPendienteBnns.addKeyListener(opTeclear);
			txtVacacionesPendientesSCOI.addKeyListener(opTeclear);
			
			fchBajaBnns.addPropertyChangeListener(opCalcularAutomaticoConFecha);
			fchBajaSCOI.addPropertyChangeListener(opCalcularAutomaticoConFecha);
			
			btnGuardar.addActionListener(opGenerar);
			
		}
		
		ActionListener opGenerar = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new Cat_Seleccion_De_Status().setVisible(true);
				
			}
		};
		
		public Date cargar_fechas_de_baja(int dias){
			Date date1 = null;
					  try {
						date1 = new SimpleDateFormat("dd/MM/yyyy").parse(new BuscarSQL().fecha(dias));
					} catch (ParseException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					  return (date1);
		};
		
		KeyListener opTeclearDias = new KeyListener() {
			public void keyTyped(KeyEvent e) {	}
			public void keyReleased(KeyEvent e) {
				
				if(!folio_empleado_bms.equals("")){
					txtDiasPendienteDePagoSemanaBnns.setText(txtDiasPendienteDePagoSemanaSCOI.getText());
				}
				recalcular_finiquito();
				
			}
			public void keyPressed(KeyEvent e){}
		};
		
		KeyListener opTeclear = new KeyListener() {
			public void keyTyped(KeyEvent e) {	}
			public void keyReleased(KeyEvent e) {
				diferencias();
			}
			public void keyPressed(KeyEvent e){}
		};
		
		public void recalcular_finiquito(){
			
//			scoi--------------------------------------------------------------------------------------------
			txtSueldoSCOI.setText(df.format(
					 Double.valueOf(txtDiasCuotaDiarioSCOI.getText().toString().trim())
					 * Double.valueOf(txtDiasPendienteDePagoSemanaSCOI.getText().toString().trim().equals("")?"0":txtDiasPendienteDePagoSemanaSCOI.getText().toString().trim()) 
				 ) +"" );
			
//			txtSueldoSCOI.setText(Math.rint(Double.valueOf(df.format(Double.valueOf(txtDiasCuotaDiarioSCOI.getText().toString().trim())
//																	* Double.valueOf(txtDiasPendienteDePagoSemanaSCOI.getText().toString().trim().equals("")?"0":txtDiasPendienteDePagoSemanaSCOI.getText().toString().trim()) 
//																	) 
//														  )
//											*100)/100 +"");
			
//			txtAguinaldoSCOI.setText("");
//			txtVacacionesSCOI.setText("");
//			txtPrimaVacacionalSCOI.setText("");
			
			txtTotalPercepcionesSCOI.setText(df.format(
												 Double.valueOf(txtSueldoSCOI.getText().toString().trim())
												 +Double.valueOf(txtAguinaldoSCOI.getText().toString().trim()) 
												 +Double.valueOf(txtVacacionesSCOI.getText().toString().trim()) 
												 +Double.valueOf(txtPrimaVacacionalSCOI.getText().toString().trim()) 
											 ) +"" );
			
			
//			bms--------------------------------------------------------------------------------------------
//			txtSueldoBnns.setText(Math.rint(Double.valueOf(df.format(Double.valueOf(txtDiasCuotaDiarioBnns.getText().toString().trim())
//																					* Double.valueOf(txtDiasPendienteDePagoSemanaBnns.getText().toString().trim().equals("")?"0":txtDiasPendienteDePagoSemanaBnns.getText().toString().trim()) 
//																					) 
//																		  )
//																*100)/100 +"");
			
			txtSueldoBnns.setText(df.format(
					 Double.valueOf(txtDiasCuotaDiarioBnns.getText().toString().trim())
					 * Double.valueOf(txtDiasPendienteDePagoSemanaBnns.getText().toString().trim().equals("")?"0":txtDiasPendienteDePagoSemanaBnns.getText().toString().trim()) 
				 ) +"" );
			
//			txtAguinaldoBnns.setText("");
//			txtVacacionesBnns.setText("");
//			txtPrimaVacacionalBnns.setText("");
			
			txtTotalPercepcionesBnns.setText(df.format(
												 Double.valueOf(txtSueldoBnns.getText().toString().trim())
												 +Double.valueOf(txtAguinaldoBnns.getText().toString().trim()) 
												 +Double.valueOf(txtVacacionesBnns.getText().toString().trim()) 
												 +Double.valueOf(txtPrimaVacacionalBnns.getText().toString().trim()) 
											 ) +"" );
			
//			txtTotalPercepcionesSCOI.setText("");
			
			 diferencias();
		}
		
		PropertyChangeListener opCalcularAutomaticoConFecha = new PropertyChangeListener() {
		  	  public void propertyChange(PropertyChangeEvent e) {
		  	            if ("date".equals(e.getPropertyName())) {
		  	            	
			  	            	if(fchBajaBnns.getDate() != null && fchBajaSCOI.getDate() != null){
			  	            		buscar_finiquito();
			  	            	}else{
			  	            		
			  	            	}
		  	            }
		  	   }
		};
		
		
		public void buscar_finiquito(){
			
			String fechaBms = new SimpleDateFormat("dd/MM/yyyy").format(fchBajaBnns.getDate());
			String fechaSCOI = new SimpleDateFormat("dd/MM/yyyy").format(fchBajaSCOI.getDate());
			
			Obj_Finiquitos finiquito = new Obj_Finiquitos().buscar_finiquito(folio_empleado_bms, Integer.valueOf(txtFolioScoi.getText().trim()), fechaSCOI, fechaBms);
			
			try {
				fchIngresoSCOI.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(finiquito.getFecha_ingreso_SCOI()));
				fchBajaSCOI.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(finiquito.getFecha_baja_SCOI()));
				
			} catch (ParseException e) {
				e.printStackTrace();
			}

//			scoi
			txtDiasTrabajadosSCOI.setText(finiquito.getDias_trabajados_SCOI()+"");
			txtaniosTrabajadosSCOI.setText(df.format(finiquito.getAnios_trabajados_SCOI()));
			txtDiasPendienteDePagoAguinaldoSCOI.setText(finiquito.getDias_pendientes_de_pago_de_aguinaldo_SCOI()+"");
			txtDiasPendienteDePagoSemanaSCOI.setText(finiquito.getDias_pendientes_de_pago_de_semana_SCOI()+"");
			txtDiasCuotaDiarioSCOI.setText(finiquito.getCuota_diario_SCOI()+"");
			
			txtSueldoSCOI.setText(finiquito.getSueldo_SCOI()+"");
			txtAguinaldoSCOI.setText(df.format(Math.round(finiquito.getAguinaldo_SCOI())));
			txtVacacionesSCOI.setText(df.format(Math.round(finiquito.getVacaciones_SCOI())));
			txtPrimaVacacionalSCOI.setText(df.format(finiquito.getPrima_vacacional_SCOI()));
			txtTotalPercepcionesSCOI.setText(finiquito.getPercepciones_SCOI()+"");
			
			if(!folio_empleado_bms.equals("")){
				
				try {
					fchIngresoBnns.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(finiquito.getFecha_ingreso_BMS()));
					fchBajaBnns.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(finiquito.getFecha_baja_BMS()));
					
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
	
//				bms
				txtDiasTrabajadosBnns.setText(finiquito.getDias_trabajados_BMS()+"");
				txtaniosTrabajadosBnns.setText(df.format(finiquito.getAnios_trabajados_BMS()));
				txtDiasPendienteDePagoAguinaldoBnns.setText(finiquito.getDias_pendientes_de_pago_de_aguinaldo_BMS()+"");
				txtDiasPendienteDePagoSemanaBnns.setText(finiquito.getDias_pendientes_de_pago_de_semana_BMS()+"");
				txtDiasCuotaDiarioBnns.setText(finiquito.getCuota_diario_BMS()+"");
				
				txtSDIBnns.setText(finiquito.getSDI_BMS()+"");
				
				txtSueldoBnns.setText(finiquito.getSueldo_BMS()+"");
				txtAguinaldoBnns.setText(df.format(Math.round(finiquito.getAguinaldo_BMS())));
				txtVacacionesBnns.setText(df.format(Math.round(finiquito.getVacaciones_BMS())));
				txtPrimaVacacionalBnns.setText(df.format(finiquito.getPrima_vacacional_BMS()));
//				txtTotalPercepcionesBnns.setText(finiquito.getPercepciones_BMS()+"");
			
			   txtPrestamoBnns.setText(finiquito.getPretamo()+"");
			   txtCortesBnns.setText(finiquito.getCortes()+"");
			   txtInfonavitBnns.setText(finiquito.getInfonavit()+"");
			   txtFuenteSodasBnns.setText(finiquito.getFuente_sodas()+"");
			   
			   diferencias();
		}
		
		public void diferencias(){
			txtSueldoDiferencia.setText( df.format(Double.valueOf(txtSueldoSCOI.getText().toString().trim())-Double.valueOf(txtSueldoBnns.getText().toString().trim()) ) +"");			
			txtAguinaldoDiferencia.setText( df.format(Double.valueOf(txtAguinaldoSCOI.getText().toString().trim())-Double.valueOf(txtAguinaldoBnns.getText().toString().trim()) ) +"");		
			txtVacacionesDiferencia.setText( df.format(Double.valueOf(txtVacacionesSCOI.getText().toString().trim())-Double.valueOf(txtVacacionesBnns.getText().toString().trim()) ) +"");
			txtPrimaVacacionalDiferencia.setText( df.format(Double.valueOf(txtPrimaVacacionalSCOI.getText().toString().trim())-Double.valueOf(txtPrimaVacacionalBnns.getText().toString().trim()) ) +"");
			
			txtVacacionesPendientesDiferencia.setText(
					df.format(
								Double.valueOf(txtVacacionesPendientesSCOI.getText().trim().equals("")?"0":txtVacacionesPendientesSCOI.getText().trim())
								- Double.valueOf(txtVacacionesPendienteBnns.getText().trim().equals("")?"0":txtVacacionesPendienteBnns.getText().trim())
							)
					);
			
			
			txtGratificacionBnns.setText(
					 df.format(
								 Double.valueOf(txtSueldoDiferencia.getText().toString().trim())
								 +Double.valueOf(txtAguinaldoDiferencia.getText().toString().trim()) 
								 +Double.valueOf(txtVacacionesPendientesDiferencia.getText().toString().trim()) 
								 +Double.valueOf(txtVacacionesDiferencia.getText().toString().trim()) 
								 +Double.valueOf(txtPrimaVacacionalDiferencia.getText().toString().trim()) 
							 ) +"" );
			
			
			txtTotalPercepcionesDiferencia.setText(df.format(
													 Double.valueOf(txtSueldoDiferencia.getText().toString().trim())
													 +Double.valueOf(txtAguinaldoDiferencia.getText().toString().trim()) 
													 +Double.valueOf(txtVacacionesPendientesDiferencia.getText().toString().trim()) 
													 +Double.valueOf(txtVacacionesDiferencia.getText().toString().trim()) 
													 +Double.valueOf(txtPrimaVacacionalDiferencia.getText().toString().trim()) 
												 ) +"" );
			
			txtTotalPercepcionesBnns.setText(df.format(
											 Double.valueOf(txtSueldoBnns.getText().toString().trim())
											 +Double.valueOf(txtAguinaldoBnns.getText().toString().trim()) 
											 +Double.valueOf(txtVacacionesPendientesDiferencia.getText().toString().trim().equals("")?"0":txtVacacionesPendientesDiferencia.getText().toString().trim()) 
											 +Double.valueOf(txtVacacionesBnns.getText().toString().trim()) 
											 +Double.valueOf(txtPrimaVacacionalBnns.getText().toString().trim()) 
											 +Double.valueOf(txtGratificacionBnns.getText().toString().trim()) 
											 +Double.valueOf(txtTiempoExtraBnns.getText().toString().trim().equals("")?"0":txtTiempoExtraBnns.getText().toString().trim()) 
										 ) +"" );
			
			
			txtTotalAPagarBnns.setText(df.format(
											 Double.valueOf(txtTotalPercepcionesBnns.getText().toString().trim())
											 -Double.valueOf(txtPrestamoBnns.getText().toString().trim()) 
											 -Double.valueOf(txtCortesBnns.getText().toString().trim()) 
											 -Double.valueOf(txtInfonavitBnns.getText().toString().trim()) 
											 -Double.valueOf(txtFuenteSodasBnns.getText().toString().trim()) 
											 -Double.valueOf(txtOtrasDeduccionesBnns.getText().toString().trim().equals("")?"0":txtOtrasDeduccionesBnns.getText().toString().trim()) 
										 ) +"" );
		}
	
	
	public class Cat_Seleccion_De_Status extends JDialog{
		
		Container cont_confirm = getContentPane();
		JLayeredPane panel_confirm = new JLayeredPane();
		
		String status[] = {"Seleccione Un Status","Baja","No Contratable"};
		@SuppressWarnings({ "rawtypes", "unchecked" })
		JComboBox cmbStatus = new JComboBox(status);
		
		JTextArea txaObservaciones = new Componentes().textArea(new JTextArea(), "Observaciones", 200);
		JScrollPane Observasiones = new JScrollPane(txaObservaciones);
		
		JButton btnAceptar = new JButton("Aceptar", new ImageIcon("imagen/flecha-naranja-alerta-de-descarga-de-la-actualizacion-icono-8872-16.png"));
		JButton btnCancelar = new JButton("Cancelar");
	
		public Cat_Seleccion_De_Status(){
			this.setModal(true);
			this.setTitle("Confirmar Finiquito");
			this.panel_confirm.setBorder(BorderFactory.createTitledBorder("Seleccione Un Status Para El Empleado"));
			this.setSize(330,200);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			
			txaObservaciones.setLineWrap(true); 
			txaObservaciones.setWrapStyleWord(true);
			
			int x=15, y=20;
			
			panel_confirm.add(new JLabel("Statua De Empleado:")		 ).setBounds(x, y, 130, 20);
			panel_confirm.add(cmbStatus   			 ).setBounds(x+130, y, 160, 20);
			
			panel_confirm.add(Observasiones   		 ).setBounds(x, y+=25, 290, 90);
			
			panel_confirm.add(btnCancelar  			 ).setBounds(x, y+=100, 100, 20);
			panel_confirm.add(btnAceptar  			 ).setBounds(x+190, y, 100, 20);
			
			cont_confirm.add(panel_confirm);
			
			btnAceptar.addActionListener(opAceptar);
		}
		
		ActionListener opAceptar = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(cmbStatus.getSelectedIndex()!=0){
					
						if(cmbStatus.getSelectedItem().toString().equals("No Contratable")){
							if(txaObservaciones.getText().trim().equals("")){
								JOptionPane.showMessageDialog(null, "El Status De No Comtratable Requiere Observaci�n", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
								return;
							}else{
								guardar_finiquito();
							}
						}else{
							guardar_finiquito();
						}
					
				}else{
					JOptionPane.showMessageDialog(null, "Es Necesario Que Seleccione Un Status Para El Empleado", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
					return;
				}
			}
		};

		public void guardar_finiquito(){
					Obj_Finiquitos finiquito = new Obj_Finiquitos();
					
					finiquito.setFolio_empleado_scoi(Integer.valueOf(txtFolioEmpleado.getText().trim()));
					finiquito.setFolio_empleado_bms(folio_empleado_bms);
					finiquito.setEstablecimiento(txtEstablecimiento.getText().trim());
					
		//		componentes De bms
					
					finiquito.setFecha_ingreso_BMS((fchIngresoBnns.getDate()+"").equals("null")?"01/01/1900":new SimpleDateFormat("dd/MM/yyyy").format(fchIngresoBnns.getDate()));
					finiquito.setFecha_baja_BMS((fchIngresoBnns.getDate()+"").equals("null")?"01/01/1900":new SimpleDateFormat("dd/MM/yyyy").format(fchBajaBnns.getDate()));
					
					finiquito.setDias_trabajados_BMS(Integer.valueOf(txtDiasTrabajadosBnns.getText().trim()));
					finiquito.setAnios_trabajados_BMS(Double.valueOf(txtaniosTrabajadosBnns.getText().trim()));
					finiquito.setDias_pendientes_de_pago_de_aguinaldo_BMS(Integer.valueOf(txtDiasPendienteDePagoAguinaldoBnns.getText().trim()));
					finiquito.setDias_pendientes_de_pago_de_semana_BMS(Integer.valueOf(txtDiasPendienteDePagoSemanaBnns.getText().trim()));
					finiquito.setCuota_diario_BMS(Double.valueOf(txtDiasCuotaDiarioBnns.getText().trim()));
					
					finiquito.setSDI_BMS(Double.valueOf(txtSDIBnns.getText().trim()));
					
					finiquito.setSueldo_BMS(Double.valueOf(txtSueldoBnns.getText().trim()));
					finiquito.setAguinaldo_BMS(Double.valueOf(txtAguinaldoBnns.getText().trim()));
					
					finiquito.setVacaciones_pendientes_BMS(Double.valueOf(txtVacacionesPendienteBnns.getText().trim().equals("")?"0":txtVacacionesPendienteBnns.getText().trim()));
					finiquito.setVacaciones_BMS(Double.valueOf(txtVacacionesBnns.getText().trim()));
					finiquito.setPrima_vacacional_BMS(Double.valueOf(txtPrimaVacacionalBnns.getText().trim()));
					
					finiquito.setGratificacion_BMS(Double.valueOf(txtGratificacionBnns.getText().trim()));
					finiquito.setTiempo_extra_BMS(Double.valueOf(txtTiempoExtraBnns.getText().trim().equals("")?"0":txtTiempoExtraBnns.getText().trim()));
					finiquito.setPercepciones_BMS(Double.valueOf(txtTotalPercepcionesBnns.getText().trim()));
					
		//		componentes De scoi
					finiquito.setFecha_ingreso_SCOI(new SimpleDateFormat("dd/MM/yyyy").format(fchIngresoSCOI.getDate()));
					finiquito.setFecha_baja_SCOI(new SimpleDateFormat("dd/MM/yyyy").format(fchBajaSCOI.getDate()));
					
					finiquito.setDias_trabajados_SCOI(Integer.valueOf(txtDiasTrabajadosSCOI.getText().trim()));
					finiquito.setAnios_trabajados_SCOI(Double.valueOf(txtaniosTrabajadosSCOI.getText().trim()));
					finiquito.setDias_pendientes_de_pago_de_aguinaldo_SCOI(Integer.valueOf(txtDiasPendienteDePagoAguinaldoSCOI.getText().trim()));
					finiquito.setDias_pendientes_de_pago_de_semana_SCOI(Integer.valueOf(txtDiasPendienteDePagoSemanaSCOI.getText().trim()));
					finiquito.setCuota_diario_SCOI(Double.valueOf(txtDiasCuotaDiarioSCOI.getText().trim()));
					
					
					finiquito.setSueldo_SCOI(Double.valueOf(txtSueldoSCOI.getText().trim()));
					finiquito.setAguinaldo_SCOI(Double.valueOf(txtAguinaldoSCOI.getText().trim()));
					
					finiquito.setVacaciones_pendientes_SCOI(Double.valueOf(txtVacacionesPendientesSCOI.getText().trim().equals("")?"0":txtVacacionesPendientesSCOI.getText().trim()));
					finiquito.setVacaciones_SCOI(Double.valueOf(txtVacacionesSCOI.getText().trim()));
					finiquito.setPrima_vacacional_SCOI(Double.valueOf(txtPrimaVacacionalSCOI.getText().trim()));
					
					finiquito.setPercepciones_SCOI(Double.valueOf(txtTotalPercepcionesSCOI.getText().trim()));
					
		//		diferencias (GRATIFICACION)
					finiquito.setSueldo_gratif(Double.valueOf(txtSueldoDiferencia.getText().trim()));
					finiquito.setAguinaldo_gratif(Double.valueOf(txtAguinaldoDiferencia.getText().trim()));
					finiquito.setVacaciones_pendientes_gratif(Double.valueOf(txtVacacionesPendientesDiferencia.getText().trim().equals("")?"0":txtVacacionesPendientesDiferencia.getText().trim()));
					finiquito.setVacaciones_gratif(Double.valueOf(txtVacacionesDiferencia.getText().trim()));
					finiquito.setPrima_vacacional_gratif(Double.valueOf(txtPrimaVacacionalDiferencia.getText().trim()));
					finiquito.setPercepciones_gratif(Double.valueOf(txtTotalPercepcionesDiferencia.getText().trim()));
					
		//		Deducciones
					finiquito.setPretamo(Double.valueOf(txtPrestamoBnns.getText().trim()));
					finiquito.setCortes(Double.valueOf(txtCortesBnns.getText().trim()));
					finiquito.setInfonavit(Double.valueOf(txtInfonavitBnns.getText().trim()));
					finiquito.setFuente_sodas(Double.valueOf(txtFuenteSodasBnns.getText().trim()));
					finiquito.setOtras_deducciones(Double.valueOf(txtOtrasDeduccionesBnns.getText().trim().equals("")?"0":txtOtrasDeduccionesBnns.getText().trim()));
					
					finiquito.setTotal_a_pagar(Double.valueOf(txtTotalAPagarBnns.getText().trim()));
		
					if(finiquito.guardar(cmbStatus.getSelectedItem().toString(),txaObservaciones.getText().toUpperCase().trim())){
						
						String basedatos="2.26";
						String vista_previa_reporte="no";
						int vista_previa_de_ventana=0;
						String comando="";
						String reporte = "";
						
						 comando = "exec sp_select_reporte_de_finiquito";
						 reporte="Obj_Finiquito.jrxml";
						 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
						 
						 
//						JOptionPane.showMessageDialog(null, "El Finiquito Se Guardo Correctamente","Aviso", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/aplicara-el-dialogo-icono-6256-32.png"));
//						return;
					}else{
						JOptionPane.showMessageDialog(null, "No Se Han Podido Procesar El Finiquito", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
						return;
					}
					
			}
		}
	}
}
