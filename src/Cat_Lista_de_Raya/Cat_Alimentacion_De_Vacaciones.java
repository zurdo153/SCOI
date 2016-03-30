package Cat_Lista_de_Raya;

import java.awt.Container;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Conexiones_SQL.BuscarTablasModel;
import Conexiones_SQL.Connexion;
import Obj_Lista_de_Raya.Obj_Alimentacion_De_Vacaciones;
import Obj_Principal.Componentes;
import Obj_Renders.tablaRenderer;

import com.toedter.calendar.JDateChooser;

@SuppressWarnings("serial")
public class Cat_Alimentacion_De_Vacaciones extends JFrame {
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
//	campos de datos de enpleado	
	JLabel lblMargenEmpleado = new JLabel();
	JTextField txtFolioVacaciones = new JTextField();
	JTextField txtFechaIngreso = new JTextField();
	JTextField txtFechaIngresoIMSS = new JTextField();
	
	JButton btnBuscar = new JButton(new ImageIcon("Iconos/zoom_icon&16.png"));
	JButton btnNuevo = new JButton("Nuevo");
	
	JTextField txtFolioEmpleado = new JTextField();
	JTextField txtEmpleado = new JTextField();
	JTextField txtEstablecimiento = new JTextField();
	JTextField txtPuesto = new JTextField();
	JTextField txtSalarioDiarioIn= new JTextField();
	JTextField txtGrupoDeVacaciones = new JTextField();
	JTextField txtProximasVacaciones = new JTextField();
	
	JButton btnFoto = new JButton("");
	
	String fileFoto = System.getProperty("user.dir")+"/Iconos/Un.JPG";
    ImageIcon tmpIconAuxFoto = new ImageIcon(fileFoto);
    
//	campo de vacaciones de empleados
	JLabel lblMargenVacaciones = new JLabel();
	
	JCheckBox chbStatus = new JCheckBox("Status",true);
	
	JDateChooser fechaInicio = new JDateChooser();
	JDateChooser fechaFin = new JDateChooser();
	JTextField txtDiasPendientesDePago = new Componentes().text( new JTextField(), "Dias Pendientes De Pago", 15, "Int");
	
	JTextField txtCuotaDiaria = new Componentes().text( new JTextField(), "Cuota Diaria", 15, "Double");
	
	JTextField txtSueldo = new Componentes().text( new JTextField(), "Sueldo", 15, "Double");
	JTextField txtVacaciones = new Componentes().text( new JTextField(), "Vacaciones", 15, "Double");
	JTextField txtDescansoPagado = new Componentes().text( new JTextField(), "Descanso Pagado", 15, "Double");
	JTextField txtPrimaVacacional = new Componentes().text( new JTextField(), "Prima Vacacional", 15, "Double");
	
	JTextField txtPercepciones = new Componentes().text( new JTextField(), "Percepciones", 15, "Double");
	
	JTextField txtPrestamo = new Componentes().text( new JTextField(), "Prestamo", 15, "Double");
	JTextField txtInfonavit = new Componentes().text( new JTextField(), "Infonavit", 15, "Double");
	JTextField txtInfonacot = new Componentes().text( new JTextField(), "Infonacot", 15, "Double");
	JTextField txtPension = new Componentes().text( new JTextField(), "Pension Alimenticia", 15, "Double");
	JTextField txtFSodas = new Componentes().text( new JTextField(), "Fuente de Sodas", 15, "Double");
	JTextField txtCorteCaja = new Componentes().text( new JTextField(), "Corte Caja", 15, "Double");
	JTextField txtOtros = new Componentes().text( new JTextField(), "Otros", 15, "Double");
	JTextField txtCheques = new Componentes().text( new JTextField(), "Cheques", 15, "Double");
	
	JTextField txtTotal_A_Pagar = new Componentes().text( new JTextField(), "Total a Pagar", 15, "Double");
	
	JLabel lblDiasVacaciones = new JLabel("Dias De Vacaciones: ");
	JLabel lblDiasVac = new JLabel("0");
	JLabel lblDias_De_Descanso_Pagados = new JLabel("Dias De Descansos Pagados: ");
	JLabel lblDescansos_Pagados = new JLabel("0");
	
	JLabel lblMargenVacacionesC = new JLabel();
	
	JTextField txtCuotaDiariaC = new Componentes().text( new JTextField(), "Cuota Diaria", 15, "Double");
	
	JTextField txtSueldoC = new Componentes().text( new JTextField(), "Sueldo", 15, "Double");
	JTextField txtVacacionesC = new Componentes().text( new JTextField(), "Vacaciones", 15, "Double");
	JTextField txtDescansoPagadoC = new Componentes().text( new JTextField(), "Descanso Pagado", 15, "Double");
	JTextField txtPrimaVacacionalC = new Componentes().text( new JTextField(), "Prima Vacacional", 15, "Double");
	
	JTextField txtPercepcionesC = new Componentes().text( new JTextField(), "Percepciones", 15, "Double");
	
	JTextField txtInfonavitC = new Componentes().text( new JTextField(), "Infonavit C.", 15, "Double");
	JTextField txtInfonacotC = new Componentes().text( new JTextField(), "Infonacot C.", 15, "Double");
	JTextField txtPrestamoC = new Componentes().text( new JTextField(), "Prestamo C.", 15, "Double");
	JTextField txtPensionC = new Componentes().text( new JTextField(), "Pension Alimenticia C.", 15, "Double");
	
	JTextField txtPrimaDominical = new Componentes().text( new JTextField(), "Prima Dominical", 15, "Double");
	JTextField txtBonoDespensa = new Componentes().text( new JTextField(), "Bono Despensa", 15, "Double");
	JTextField txtPremioPorPuntualidad = new Componentes().text( new JTextField(), "Premio Por Puntualidad", 15, "Double");
	JTextField txtPremioPorAsitencia = new Componentes().text( new JTextField(), "Premio Por Asitencia", 15, "Double");
	JTextField txtSubsidio = new Componentes().text( new JTextField(), "Subsidio", 15, "Double");
	
	JButton btnDeshacer = new JButton("Deshacer");
	JButton btnReporte = new JButton("Generar Reporte");
	JButton btnCalcular = new JButton("Calcular");
	JButton btnGuardar = new JButton("Guardar");
	
//	campo de tabla de vacaciones
	JLabel lblMargenTabla = new JLabel();
	
	DecimalFormat DF = new DecimalFormat("#0.00");
	
    public static DefaultTableModel tabla_model = new DefaultTableModel(
    		null,	new String[]{"Folio Vacaciones","Fecha Inicio","Fecha Final","Años"}){
                    @SuppressWarnings("rawtypes")
                    Class[] types = new Class[]{
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
                                    case 0  : return false; 
                                    case 1  : return false; 
                                    case 2  : return false; 
                                    case 3  : return false; 
                            }
                             return false;
                     }
            };
            
        	JTable tabla_vacaciones_disfrutadas = new JTable(tabla_model);
        	JScrollPane panelScroll = new JScrollPane(tabla_vacaciones_disfrutadas);

    Border blackline;
    
	public Cat_Alimentacion_De_Vacaciones(){
		
//		efectos de bordes
		blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
		
		lblDiasVacaciones.setFont(new Font("arial", Font.BOLD, 12));
		lblDiasVac.setFont(new Font("arial", Font.BOLD, 12));
		
		lblDias_De_Descanso_Pagados.setFont(new Font("arial", Font.BOLD, 12));
		lblDescansos_Pagados.setFont(new Font("arial", Font.BOLD, 12));
		
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/sun_icon&16.png"));
		this.setTitle("Alimentacion De Vacaciones");
		
		this.lblMargenEmpleado.setBorder(BorderFactory.createTitledBorder(blackline,"Datos Del Empleado"));
		this.lblMargenVacaciones.setBorder(BorderFactory.createTitledBorder(blackline,"Vacaciones NC."));
		this.lblMargenVacacionesC.setBorder(BorderFactory.createTitledBorder(blackline,"Vacaciones C."));
		this.lblMargenTabla.setBorder(BorderFactory.createTitledBorder(blackline,"Tabla De Vacaciones Pasadas"));
		
		int y=15;
//		campos de datos de empleado
		panel.add(lblMargenEmpleado).setBounds(3, 0, 675, 167);
		panel.add(new JLabel("Folio Vacaciones: ")).setBounds(20, y, 110, 20);
		panel.add(txtFolioVacaciones).setBounds(130, y, 60, 20);
		
		panel.add(btnBuscar).setBounds(200, y, 30, 20);
		panel.add(btnNuevo).setBounds(240, y, 80, 20);
		panel.add(btnDeshacer).setBounds(330, y, 80, 20);
		
		
		panel.add(new JLabel("Empleado: ")).setBounds(20, y+=25, 110, 20);
		panel.add(txtFolioEmpleado).setBounds(130, y, 50, 20);
		panel.add(txtEmpleado).setBounds(181, y, 319, 20);
		
		panel.add(new JLabel("Establecimiento: ")).setBounds(20, y+=25, 110, 20);
		panel.add(txtEstablecimiento).setBounds(130, y, 190, 20);
		panel.add(chbStatus).setBounds(360, y, 80, 20);
		
		panel.add(new JLabel("Puesto: ")).setBounds(20, y+=25, 60, 20);
		panel.add(txtPuesto).setBounds(130, y, 370, 20);
		
		panel.add(new JLabel("Fecha Ingreso: ")).setBounds(20, y+=25, 130, 20);
		panel.add(txtFechaIngreso).setBounds(125, y, 100, 20);
		
		panel.add(new JLabel("Grupo De Vacaciones: ")).setBounds(290, y, 140, 20);
		panel.add(txtGrupoDeVacaciones).setBounds(400, y, 100, 20);
		
		panel.add(new JLabel("Fecha Ingreso IMSS: ")).setBounds(20, y+=25, 120, 20);
		panel.add(txtFechaIngresoIMSS).setBounds(125, y, 100, 20);
		
		panel.add(new JLabel("Proximas Vacaciones: ")).setBounds(290, y, 140, 20);
		panel.add(txtProximasVacaciones).setBounds(400, y, 100, 20);
		
		panel.add(btnFoto).setBounds(523, 11, 150, 150);

		panel.add(new JLabel("Fecha Inicial: ")).setBounds(7, y+=30, 140, 20);
		panel.add(fechaInicio).setBounds(70, y, 100, 20);
		
		panel.add(new JLabel("Fecha Final: ")).setBounds(175, y, 100, 20);
		panel.add(fechaFin).setBounds(235, y, 100, 20);
		
		panel.add(new JLabel("Dias Pendientes De Pago: ")).setBounds(345, y, 150, 20);
		panel.add(txtDiasPendientesDePago).setBounds(470, y, 100, 20);
		
//		campo de vacaciones no contables de empleados
		panel.add(lblMargenVacaciones).setBounds(115, 190, 120, 410);
		
		panel.add(new JLabel("Cuota Diaria: ")).setBounds(20, y+=35, 140, 20);
		panel.add(txtCuotaDiaria).setBounds(125, y, 100, 20);
		
		panel.add(new JLabel("Sueldo Semanal: ")).setBounds(20, y+=35, 140, 20);
		panel.add(txtSueldo).setBounds(125, y, 100, 20);

		panel.add(new JLabel("Vacaciones: ")).setBounds(20, y+=25, 140, 20);
		panel.add(txtVacaciones).setBounds(125, y, 100, 20);
		
		panel.add(new JLabel("Descanso Pagado: ")).setBounds(20, y+=25, 140, 20);
		panel.add(txtDescansoPagado).setBounds(125, y, 100, 20);
		
		panel.add(new JLabel("Prima Vacacional: ")).setBounds(20, y+=25, 140, 20);
		panel.add(txtPrimaVacacional).setBounds(125, y, 100, 20);
		
		panel.add(new JLabel("Total Percepciones: ")).setBounds(20, y+=25, 140, 20);
		panel.add(txtPercepciones).setBounds(125, y, 100, 20);
		
		panel.add(new JLabel("Prestamo: ")).setBounds(20, y+=25, 140, 20);
		panel.add(txtPrestamo).setBounds(125, y, 100, 20);
		
		panel.add(new JLabel("Infonavit: ")).setBounds(20, y+=25, 100, 20);
		panel.add(txtInfonavit).setBounds(125, y, 100, 20);
		
		panel.add(new JLabel("Infonacot: ")).setBounds(20, y+=25, 100, 20);
		panel.add(txtInfonacot).setBounds(125, y, 100, 20);
		
		panel.add(new JLabel("Pension Alimenticia: ")).setBounds(20, y+=25, 140, 20);
		panel.add(txtPension).setBounds(125, y, 100, 20);
		
		panel.add(new JLabel("Fuente De Sodas: ")).setBounds(20, y+=25, 120, 20);
		panel.add(txtFSodas).setBounds(125, y, 100, 20);
		
		panel.add(new JLabel("Corte De Caja: ")).setBounds(20, y+=25, 140, 20);
		panel.add(txtCorteCaja).setBounds(125, y, 100, 20);
		
		panel.add(new JLabel("Otros: ")).setBounds(20, y+=25, 140, 20);
		panel.add(txtOtros).setBounds(125, y, 100, 20);
		
		panel.add(new JLabel("Cheque: ")).setBounds(20, y+=25, 140, 20);
		panel.add(txtCheques).setBounds(125, y, 100, 20);
		
		panel.add(new JLabel("Total A Pagar: ")).setBounds(20, y+=25, 140, 20);
		panel.add(txtTotal_A_Pagar).setBounds(125, y, 100, 20);
		
//		campo de vacaciones contables de empleados
		y=205;
		panel.add(lblMargenVacacionesC).setBounds(240, 190, 440, 280);
		
		panel.add(txtCuotaDiariaC).setBounds(250, y, 100, 20);
		panel.add(txtSueldoC).setBounds(250, y+=35, 100, 20);
		panel.add(txtVacacionesC).setBounds(250, y+=25, 100, 20);
		panel.add(txtDescansoPagadoC).setBounds(250, y+=25, 100, 20);
		panel.add(txtPrimaVacacionalC).setBounds(250, y+=25, 100, 20);
		
		panel.add(txtPercepcionesC).setBounds(250, y+=25, 100, 20);
		
		panel.add(txtPrestamoC).setBounds(250, y+=25, 100, 20);
		panel.add(txtInfonavitC).setBounds(250, y+=25, 100, 20);
		panel.add(txtInfonacotC).setBounds(250, y+=25, 100, 20);
		panel.add(txtPensionC).setBounds(250, y+=25, 100, 20);
		
		y=200;
		
		panel.add(lblDiasVacaciones).setBounds(390, y, 220, 20);
		panel.add(lblDiasVac).setBounds(530, y, 50, 20);
		
		panel.add(lblDias_De_Descanso_Pagados).setBounds(390, y+=20, 220, 20);
		panel.add(lblDescansos_Pagados).setBounds(580, y, 50, 20);
		
		panel.add(new JLabel("Prima Dominical: ")).setBounds(390, y+=45, 140, 20);
		panel.add(txtPrimaDominical).setBounds(520, y, 100, 20);
		
		panel.add(new JLabel("Bono Despensa: ")).setBounds(390, y+=25, 140, 20);
		panel.add(txtBonoDespensa).setBounds(520, y, 100, 20);

		panel.add(new JLabel("Premio Por Puntualidad: ")).setBounds(390, y+=25, 140, 20);
		panel.add(txtPremioPorPuntualidad).setBounds(520, y, 100, 20);
		
		panel.add(new JLabel("Premio Por Asistencia: ")).setBounds(390, y+=25, 140, 20);
		panel.add(txtPremioPorAsitencia).setBounds(520, y, 100, 20);
		
		panel.add(new JLabel("Subsidio: ")).setBounds(390, y+=25, 140, 20);
		panel.add(txtSubsidio).setBounds(520, y, 100, 20);
		
//		campo de tabla de vacaciones
//		panel.add(lblMargenTabla).setBounds(3, 435, 675, 125);
		panel.add(panelScroll).setBounds(242, y+=105, 435, 100);
		
//		panel.add(btnCalcular).setBounds(20, y+=14, 90, 20);
		panel.add(btnGuardar).setBounds(242, y+=105, 90, 20);
//		panel.add(btnReporte).setBounds(235, y, 140, 20);
		
        Icon iconoFoto = new ImageIcon(tmpIconAuxFoto.getImage().getScaledInstance(btnFoto.getWidth(), btnFoto.getHeight(), Image.SCALE_DEFAULT));
        btnFoto.setIcon(iconoFoto);
		
        render(tabla_vacaciones_disfrutadas);
        
        btnCalcular.setEnabled(false);
        btnGuardar.setEnabled(false);
        
        camposBloqueadosDefault();
        activar_descactivar_campos(false);
        
		
		btnReporte.setEnabled(false);
        
        btnBuscar.addActionListener(opBuscar);
        btnNuevo.addActionListener(opNuevo);
        btnDeshacer.addActionListener(opDeshacer);
//        btnGuardar.addActionListener(opGuardar);
//        btnReporte.addActionListener(opReporte);
        
        txtDiasPendientesDePago.addKeyListener(opRecalcular);
        txtPrimaDominical.addKeyListener(opRecalcular);
        txtBonoDespensa.addKeyListener(opRecalcular);
        txtPremioPorPuntualidad.addKeyListener(opRecalcular);
        txtPremioPorAsitencia.addKeyListener(opRecalcular);
        txtSubsidio.addKeyListener(opRecalcular);
        txtOtros.addKeyListener(opRecalcular);
        
        
//    	FUNCION PARA AGREGAR UNA ACCION AL SELECCIONAR UNA FECHA
        fechaInicio.getDateEditor().addPropertyChangeListener(opCalcularAutomaticoConFechaIn);

		cont.add(panel);
		this.setSize(700,660);
		this.setLocationRelativeTo(null);
	
	}
	
	public void camposBloqueadosDefault(){
		
        txtFolioVacaciones.setEditable(false);
        txtFolioEmpleado.setEditable(false);
        txtEmpleado.setEditable(false);
        txtEstablecimiento.setEditable(false);
        txtPuesto.setEditable(false);
        txtFechaIngreso.setEditable(false);
        txtFechaIngresoIMSS.setEditable(false);
        txtSalarioDiarioIn.setEditable(false);
        txtGrupoDeVacaciones.setEditable(false);
        txtProximasVacaciones.setEditable(false);
		
		fechaFin.setEnabled(false);
        
        txtCuotaDiaria.setEditable(false);
        
        txtVacaciones.setEditable(false);
        txtDescansoPagado.setEditable(false);
		txtPrimaVacacional.setEditable(false);
		txtPercepciones.setEditable(false);
		txtInfonavit.setEditable(false);
		txtInfonacot.setEditable(false);
		txtSueldo.setEditable(false);
		txtPrestamo.setEditable(false);
		txtPension.setEditable(false);
        txtFSodas.setEditable(false);
        txtCorteCaja.setEditable(false);
        txtCheques.setEditable(false);
        txtTotal_A_Pagar.setEditable(false);
		
        txtCuotaDiariaC.setEditable(false);
        
		txtVacacionesC.setEditable(false);
		txtDescansoPagadoC.setEditable(false);
		txtPrimaVacacionalC.setEditable(false);
		txtPercepcionesC.setEditable(false);
		txtInfonavitC.setEditable(false);
		txtInfonacotC.setEditable(false);
		txtSueldoC.setEditable(false);
		txtPrestamoC.setEditable(false);
		txtPensionC.setEditable(false);
		
	}
	
	public void activar_descactivar_campos(boolean activ){
		fechaInicio.setEnabled(activ);
		txtDiasPendientesDePago.setEditable(activ);
		txtOtros.setEditable(activ);
		txtPrimaDominical.setEditable(activ);
		txtBonoDespensa.setEditable(activ);
		txtPremioPorPuntualidad.setEditable(activ);
		txtPremioPorAsitencia.setEditable(activ);
		txtSubsidio.setEditable(activ);
		
		btnDeshacer.setEnabled(activ);
		btnGuardar.setEnabled(activ);
	}
	
	
	public void render(final JTable tb){
		tb.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",10));
		for(int i = 1; i<tb.getColumnCount(); i++){
			switch(i){
					case 0: tb.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","izquierda"	,"Arial","negrita",11)); break;
					case 1: tb.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","derecha","Arial","negrita",11) ); break;
					default:tb.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","centro"	,"Arial","negrita",11)); break;
				}
		}
		
		tb.getTableHeader().setReorderingAllowed(false) ;
    	tb.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    	
    	int x=140;
    	
    	tb.getColumnModel().getColumn(0 ).setMaxWidth(x-50);   
    	tb.getColumnModel().getColumn(0 ).setMinWidth(x-50);   
    	tb.getColumnModel().getColumn(1 ).setMaxWidth(x);
    	tb.getColumnModel().getColumn(1 ).setMinWidth(x);
    	tb.getColumnModel().getColumn(2 ).setMaxWidth(x); 
    	tb.getColumnModel().getColumn(2 ).setMinWidth(x); 
    	                                          
    	tb.getColumnModel().getColumn(3 ).setMaxWidth(50);
    	tb.getColumnModel().getColumn(3 ).setMinWidth(50);		
	}
	
	ActionListener opNuevo = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			new Filtro_Empleado_Para_Vacaciones_Nuevas().setVisible(true);
		}
	};
	
	ActionListener opBuscar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			new Filtro_Ultimas_Vacaciones_De_Empleado().setVisible(true);
		}
	};
	
//	int dias_de_vacaciones = 0;
//    int dias_de_descanso_pagados = 0;
	PropertyChangeListener opCalcularAutomaticoConFechaIn = new PropertyChangeListener() {
  	  public void propertyChange(PropertyChangeEvent e) {
  	            if ("date".equals(e.getPropertyName())) {
  	            	
	  	            	if(fechaInicio.getDate() != null){
	  	            		
	  	            		activar_descactivar_campos(true);
	  	            		
		  	            	Obj_Alimentacion_De_Vacaciones calculo = new Obj_Alimentacion_De_Vacaciones().buscar_vacaciones(Integer.valueOf(txtFolioEmpleado.getText()),fechaInicio.getDate(),Integer.valueOf(txtProximasVacaciones.getText()));
		
//  	                System.out.println(e.getPropertyName()+ ": " + (Date) e.getNewValue());
//  calcula rango de fecha-------------------------------------------------------------------------------------------------
//		  	                int rangoEnDias = Integer.valueOf(calculo.getFecha_final());
//		  	                int tiempo =(24 * 60 * 60 * 1000);
//		  	                long dias = rangoEnDias * tiempo;
//		  	                
//		  	                long tiempoActual = fechaInicio.getDate().getTime();
//		  	                Date fechaLimite = new Date(tiempoActual + dias);
// -------------------------------------------------------------------------------------------------------------------------
////		  					alimentacion de vacaciones
//		  					alimentacion_vacaciones.setPrestamo_nc(rs.getFloat("desc_prestamo"));
//		  					alimentacion_vacaciones.setPension_alimenticia_nc(rs.getFloat("pension_alimenticia"));
//		  					alimentacion_vacaciones.setInfonacot_nc(rs.getFloat("infonavit"));
//		  					alimentacion_vacaciones.setInfonacot_nc(rs.getFloat("infonacot"));
////		  					System.out.println(rs.getFloat(""));
//		  					alimentacion_vacaciones.setFuente_de_sodas_nc(rs.getFloat("descuento_de_fuente_de_sodas"));
//		  					alimentacion_vacaciones.setCorte_de_caja_nc(rs.getFloat("corte_de_caja"));
//		  					
		  					
		  	                try {
								fechaFin.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(calculo.getFecha_final()));
							} catch (ParseException e1) {
								e1.printStackTrace();
							}
		  	                
		  	                lblDiasVac.setText(calculo.getDias_de_vacaciones()+"");
		  	                lblDescansos_Pagados.setText(calculo.getDias_de_descanso_pagados()+"");
			  	            
		  	                txtDiasPendientesDePago.setText(calculo.getDias_trabajados_de_la_ultima_semana()+"");
		  	                txtCuotaDiaria.setText(DF.format(calculo.getCuota_diaria_nc()));
		  	                
		  	                txtCuotaDiariaC.setText(DF.format(calculo.getCuota_diaria_c()));
		  	                txtSalarioDiarioIn.setText(DF.format(calculo.getSDI_c()));
		  	                
			  	            txtPrestamo.setText(DF.format(calculo.getPrestamo_nc()));
			  	            txtPension.setText(DF.format(calculo.getPension_alimenticia_nc()));
			  	            txtInfonavit.setText(DF.format(calculo.getInfonavit_nc()));
			  	           	txtInfonacot.setText(DF.format(calculo.getInfonacot_nc()));
			  	           	txtFSodas.setText(DF.format(calculo.getFuente_de_sodas_nc()));
			  	           	txtCorteCaja.setText(DF.format(calculo.getCorte_de_caja_nc()));
			  	           	
			  	          txtPrestamoC.setText(DF.format(calculo.getPrestamo_nc()));
			  	            txtPensionC.setText(DF.format(calculo.getPension_alimenticia_nc()));
			  	            txtInfonavitC.setText(DF.format(calculo.getInfonavit_nc()));
			  	           	txtInfonacotC.setText(DF.format(calculo.getInfonacot_nc()));
		  	                
		  	              calcular();

		  	              txtDiasPendientesDePago.requestFocus();
	  	            	}
  	            }
  	  		}
  	    };
  	    
  	    public void calcular(){
  	    	
  	    	txtSueldo.setText(DF.format(Float.valueOf(txtCuotaDiaria.getText()) * Float.valueOf(txtDiasPendientesDePago.getText().equals("")?"0":txtDiasPendientesDePago.getText())));
              txtVacaciones.setText(DF.format(Float.valueOf(txtCuotaDiaria.getText()) * Float.valueOf(lblDiasVac.getText())));	
              txtDescansoPagado.setText(DF.format(Float.valueOf(txtCuotaDiaria.getText()) * Float.valueOf(lblDescansos_Pagados.getText())));	
              txtPrimaVacacional.setText(DF.format(Float.valueOf(txtVacaciones.getText()) * 0.25));
              
              txtPercepciones.setText(DF.format(Float.valueOf(txtSueldo.getText()) +
						  	            		Float.valueOf(txtVacaciones.getText()) +
						  	            		Float.valueOf(txtDescansoPagado.getText()) +
						  	            		Float.valueOf(txtPrimaVacacional.getText())  ));
              
              txtSueldoC.setText(DF.format(Float.valueOf(txtCuotaDiariaC.getText()) * Float.valueOf(txtDiasPendientesDePago.getText().equals("")?"0":txtDiasPendientesDePago.getText())));
              txtVacacionesC.setText(DF.format(Float.valueOf(txtCuotaDiariaC.getText()) * Float.valueOf(lblDiasVac.getText())));	
              txtDescansoPagadoC.setText(DF.format(Float.valueOf(txtCuotaDiariaC.getText()) * Float.valueOf(lblDescansos_Pagados.getText())));	
              txtPrimaVacacionalC.setText(DF.format(Float.valueOf(txtVacacionesC.getText()) * 0.25));
              
              txtPercepcionesC.setText(DF.format(Float.valueOf(txtSueldoC.getText()) +
						  	            		Float.valueOf(txtVacacionesC.getText()) +
						  	            		Float.valueOf(txtDescansoPagadoC.getText()) +
						  	            		Float.valueOf(txtPrimaVacacionalC.getText())  ));
              
              txtCheques.setText(DF.format(Float.valueOf(txtPercepcionesC.getText())
						            			
						            			+ Float.valueOf(txtPrimaDominical.getText().equals("")?"0":txtPrimaDominical.getText())
						            			+ Float.valueOf(txtBonoDespensa.getText().equals("")?"0":txtBonoDespensa.getText())
						            			+ Float.valueOf(txtPremioPorPuntualidad.getText().equals("")?"0":txtPremioPorPuntualidad.getText())
						            			+ Float.valueOf(txtPremioPorAsitencia.getText().equals("")?"0":txtPremioPorAsitencia.getText())
						            			+ Float.valueOf(txtSubsidio.getText().equals("")?"0":txtSubsidio.getText())
						            			
						            			- Float.valueOf(txtPrestamoC.getText())
						            			- Float.valueOf(txtPensionC.getText())
						            			- Float.valueOf(txtInfonavitC.getText())
						            			- Float.valueOf(txtInfonacotC.getText())
						            	   )
            		  			 );

				txtTotal_A_Pagar.setText(DF.format(Float.valueOf(txtPercepciones.getText())
													- Float.valueOf(txtPrestamo.getText())
													- Float.valueOf(txtInfonavit.getText())
													- Float.valueOf(txtInfonacot.getText())
													- Float.valueOf(txtPension.getText())
													- Float.valueOf(txtFSodas.getText())
													- Float.valueOf(txtCorteCaja.getText())
													- Float.valueOf(txtOtros.getText().equals("")?"0":txtOtros.getText())
													- Float.valueOf(txtCheques.getText())
												   )
										   );
              
  	    }
  	    
  	    KeyListener opRecalcular = new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {
				calcular();
			}
			public void keyPressed(KeyEvent e) {}
		};
	
  		ActionListener opDeshacer = new ActionListener() {
  			public void actionPerformed(ActionEvent arg0) {
  				
  				btnBuscar.setEnabled(true);
  				btnNuevo.setEnabled(true);
  				btnDeshacer.setEnabled(false);
  		        activar_descactivar_campos(false);
  		        limpiarPantalla();
  			}
  		};
  		
//  		ActionListener opReporte = new ActionListener() {
//  			
//  			public void actionPerformed(ActionEvent e) {
//  				String query = "exec sp_Reporte_De_Impresion_De_Vacaciones "+Integer.valueOf(txtFolioVacaciones.getText());
//  					Statement stmt = null;
//  					try {
//  						stmt =  new Connexion().conexion().createStatement();
//  					    ResultSet rs = stmt.executeQuery(query);
//					btnBuscar.setEnabled(true);
//  	  		        btnNuevo.setEnabled(true);
//  	  		        btnReporte.setEnabled(false);
//  	  		       
//  	  		        limpiarPantalla();
//  					    
//  						JasperReport report = JasperCompileManager.compileReport(System.getProperty("user.dir")+"\\src\\Obj_Reportes\\Obj_Reporte_De_Impresion_De_Vacaciones.jrxml");
//  						JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
//  						@SuppressWarnings({ "rawtypes", "unchecked" })
//  						JasperPrint print = JasperFillManager.fillReport(report, new HashMap(), resultSetDataSource);
//  						JasperViewer.viewReport(print, false);
//  					} catch (Exception e1) {
//  						System.out.println(e1.getMessage());
//  						JOptionPane.showMessageDialog(null, "Error en Cat_Alimentacion_De_Vacaciones  en la funcion opReporte en el procedimiento sp_Reporte_De_Impresion_De_Vacaciones SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
//  					}
//  				}
//  				
//  			
//  		};
  		

  		
//  		ActionListener opGuardar = new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				
//				if(txtFolioVacaciones.getText().equals("")){
//					JOptionPane.showMessageDialog(null, "El folio es requerido \n", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
//				}else{		
//					
//					Obj_Alimentacion_De_Vacaciones vacaciones = new Obj_Alimentacion_De_Vacaciones();
//					
////					checar busqueda, se le deve estar mandando el folio de las vacaciones
//					if(new Obj_Alimentacion_De_Vacaciones().buscar_vacaciones_para_update(Integer.valueOf(txtFolioVacaciones.getText()))==true){
//						
//						if(JOptionPane.showConfirmDialog(null, "El registro existe, ¿desea actualizarlo?") == 0){
//							if(validaCamposAlGuardar()!="") {
//								btnCalcular.setEnabled(true);
//								btnGuardar.setEnabled(false);
//								JOptionPane.showMessageDialog(null, "Los siguientes campos fueron modificados despues de calcularlos:\n "+validaCamposAlGuardar()+"Para guardar las vacaciones primero debe calcularlas", "Error al guardar registro", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
//								return;
//							}else{
//
//								vacaciones.setFecha_inicio(new SimpleDateFormat("dd/MM/yyyy").format(fechaInicio.getDate()));
//								vacaciones.setFecha_final(new SimpleDateFormat("dd/MM/yyyy").format(fechaFin.getDate()));
//								
//								vacaciones.setVacaciones(Float.valueOf(txtVacaciones.getText()));
//								vacaciones.setPrima_vacacional(Float.valueOf(txtPrimaVacacional.getText()));
//								vacaciones.setInfonavit(Float.valueOf(txtInfonavit.getText()));
//								vacaciones.setSueldo_semana(Float.valueOf(txtSueldo.getText()));
//								vacaciones.setCorte_de_caja(Float.valueOf(txtCorteCaja.getText()));
//								vacaciones.setFuente_de_sodas(Float.valueOf(txtFSodas.getText()));
//								vacaciones.setPrestamo(Float.valueOf(txtPrestamo.getText()));
//								vacaciones.setPension_alimenticia(Float.valueOf(txtPension.getText()));
//								vacaciones.setDias_descanso_vacaciones(Float.valueOf(txtDescansoPagado.getText()));
//								vacaciones.setTotal(Float.valueOf(lblTotal.getText()));
//								vacaciones.setStatus(chbStatus.isSelected());
//								vacaciones.setVacaciones_c(Float.valueOf(txtVacacionesC.getText()));
//								vacaciones.setPrima_vacacional_c(Float.valueOf(txtPrimaVacacionalC.getText()));
//								vacaciones.setSueldo_semana_c(Float.valueOf(txtSueldoC.getText()));
//								
//				  	             try {
//									Date fecha_hoy = new SimpleDateFormat("dd/MM/yyyy").parse(new BuscarSQL().trae_fecha());
//									
//									 int tiempo =(24 * 60 * 60 * 1000);
//									long tiempoDB = fecha_hoy.getTime();
//					  	            long tiempocalendario = fechaInicio.getDate().getTime();
//					  	              
//					  	            long diferienciaEnDias = tiempoDB-tiempocalendario;
//					  	            long margen_en_dias = diferienciaEnDias/tiempo;
//					  	            
//					  	            if(margen_en_dias <= 15){
//					  	            	if(vacaciones.actualizar(Integer.valueOf(txtFolioVacaciones.getText()))){
//					  	            	
//											btnBuscar.setEnabled(true);
//									        btnNuevo.setEnabled(true);
//									        btnGuardar.setEnabled(false);
//									        btnReporte.setEnabled(true);
//											JOptionPane.showMessageDialog(null,"El registró se actualizó de forma segura","Aviso",JOptionPane.INFORMATION_MESSAGE);
//											return;
//										}else{
//											limpiarPantalla();
//											btnBuscar.setEnabled(true);
//									        btnNuevo.setEnabled(true);
//									        btnGuardar.setEnabled(false);
//											JOptionPane.showMessageDialog(null,"Error al intentar actualizar los datos","Aviso",JOptionPane.ERROR_MESSAGE);
//											return;
//										}
//					  	            }else{
//					  	            	limpiarPantalla();
//					  	            	JOptionPane.showMessageDialog(null,"El registro no se puede modificar,\nya que pasaron 15 dias de haberse\ngenerado estas vacaciones","Aviso",JOptionPane.ERROR_MESSAGE);
//										return;
//					  	            }
//								} catch (ParseException e) {
//									e.printStackTrace();
//								}
//							}
//						}else{
//							return;
//						}
//					}else{
//						
//						if(validaCamposAlGuardar()!="") {
//							btnCalcular.setEnabled(true);
//							btnGuardar.setEnabled(false);
//							
//							JOptionPane.showMessageDialog(null, "Los siguientes campos fueron modificados despues de calcularlos:\n "+validaCamposAlGuardar()+"Para guardar las vacaciones primero debe calcularlas", "Error al guardar registro", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
//							return;
//						}else{
//							
//							vacaciones.setFolio_empleado(Integer.valueOf(txtFolioEmpleado.getText()));
//							vacaciones.setFecha_inicio(new SimpleDateFormat("dd/MM/yyyy").format(fechaInicio.getDate()));
//							vacaciones.setFecha_final(new SimpleDateFormat("dd/MM/yyyy").format(fechaFin.getDate()));
//							vacaciones.setAnios_a_disfrutar(Integer.valueOf(txtProximasVacaciones.getText()));
//							vacaciones.setVacaciones(Float.valueOf(txtVacaciones.getText()));
//							vacaciones.setPrima_vacacional(Float.valueOf(txtPrimaVacacional.getText()));
//							vacaciones.setInfonavit(Float.valueOf(txtInfonavit.getText()));
//							vacaciones.setSueldo_semana(Float.valueOf(txtSueldo.getText()));
//							vacaciones.setCorte_de_caja(Float.valueOf(txtCorteCaja.getText()));
//							vacaciones.setFuente_de_sodas(Float.valueOf(txtFSodas.getText()));
//							vacaciones.setPrestamo(Float.valueOf(txtPrestamo.getText()));
//							vacaciones.setPension_alimenticia(Float.valueOf(txtPension.getText()));
//							vacaciones.setDias_descanso_vacaciones(Float.valueOf(txtDescansoPagado.getText()));
//							vacaciones.setTotal(Float.valueOf(lblTotal.getText()));
//							vacaciones.setStatus(chbStatus.isSelected());
//							vacaciones.setVacaciones_c(Float.valueOf(txtVacacionesC.getText()));
//							vacaciones.setPrima_vacacional_c(Float.valueOf(txtPrimaVacacionalC.getText()));
//							vacaciones.setSueldo_semana_c(Float.valueOf(txtSueldoC.getText()));
//							
//							if(vacaciones.guardar_vacaciones_calculadas()){
//					
//								btnBuscar.setEnabled(true);
//						        btnNuevo.setEnabled(true);
//						        btnGuardar.setEnabled(false);
//						        btnReporte.setEnabled(true);
//								JOptionPane.showMessageDialog(null,"El registro se guardó de forma segura","Aviso",JOptionPane.INFORMATION_MESSAGE);
//								return;
//							}else{
//								limpiarPantalla();
//								btnBuscar.setEnabled(true);
//						        btnNuevo.setEnabled(true);
//						        btnGuardar.setEnabled(false);
//								JOptionPane.showMessageDialog(null, "Ocurrió un problema al almacenar las Vacaciones", "Error", JOptionPane.ERROR_MESSAGE);
//								return;
//							}
//						}
//					}
//				}	
//			}
//		};
		
  		@SuppressWarnings("unused")
		private String validaCampos(){
  			String error="";
  			String fechaInNull = fechaInicio.getDate()+"";
  			String fechaFinNull = fechaFin.getDate()+"";
  			
  			if(fechaInNull.equals("null"))error+= "Fecha Inicial\n";	
  			if(fechaFinNull.equals("null"))error += "Fecha Final\n";
  			
  			if(txtVacaciones.getText().equals("")) 	error+= "Vacaciones\n";
  			if(txtPrimaVacacional.getText().equals("")) 		error+= "Prima Vacacional\n";
  			if(txtInfonavit.getText().equals(""))	error+= "Ifonavit\n";
  			if(txtSueldo.getText().equals(""))error+= "Sueldo Semana\n";
  			if(txtCorteCaja.getText().equals(""))	error+= "Corte De Caja\n";
  			if(txtFSodas.getText().equals("")) 		error+= "Fuente De Sodas\n";
  			if(txtPrestamo.getText().equals("")) 	error+= "Prestamo\n";
  			if(txtPension.getText().equals("")) 	error+= "Pension Alimenticia\n";
  			
  			return error;
  		}
  		
  		public void limpiarPantalla(){
  			
  	        txtFolioVacaciones.setText("");
  	        txtFolioEmpleado.setText("");
  	        txtEmpleado.setText("");
  	        txtEstablecimiento.setText("");
  	        txtPuesto.setText("");
  	        txtFechaIngreso.setText("");
  	        txtFechaIngresoIMSS.setText("");
  	        txtSalarioDiarioIn.setText("");
  	        txtGrupoDeVacaciones.setText("");
  	        txtProximasVacaciones.setText("");
  	        
  			Icon iconoFoto = new ImageIcon(tmpIconAuxFoto.getImage().getScaledInstance(btnFoto.getWidth(), btnFoto.getHeight(), Image.SCALE_DEFAULT));
  	        btnFoto.setIcon(iconoFoto);
  			
	  		fechaFin.setDate(null);
	          
	        txtCuotaDiaria.setText("");
	         
	        txtVacaciones.setText("");
	        txtDescansoPagado.setText("");
	  		txtPrimaVacacional.setText("");
	  		txtPercepciones.setText("");
	  		txtInfonavit.setText("");
	  		txtInfonacot.setText("");
	  		txtSueldo.setText("");
	  		txtPrestamo.setText("");
	  		txtPension.setText("");
	        txtFSodas.setText("");
	        txtCorteCaja.setText("");
	        txtCheques.setText("");
	        txtTotal_A_Pagar.setText("");
	  		
	        txtCuotaDiariaC.setText("");
	          
	  		txtVacacionesC.setText("");
	  		txtDescansoPagadoC.setText("");
	  		txtPrimaVacacionalC.setText("");
	  		txtPercepcionesC.setText("");
	  		txtInfonavitC.setText("");
	  		txtInfonacotC.setText("");
	  		txtSueldoC.setText("");
	  		txtPrestamoC.setText("");
	  		txtPensionC.setText("");
	  		
	  		fechaInicio.setDate(null);
	  		txtDiasPendientesDePago.setText("");
	  		txtOtros.setText("");
	  		txtPrimaDominical.setText("");
	  		txtBonoDespensa.setText("");
	  		txtPremioPorPuntualidad.setText("");
	  		txtPremioPorAsitencia.setText("");
	  		txtSubsidio.setText("");
            
            lblDiasVac.setText("0");
            
            while(tabla_vacaciones_disfrutadas.getRowCount()>0){
                tabla_model.removeRow(0);
            }
  		}
  		
 //Filtro Empleado para buscar y editar sus ultimas vacacioenes----------------------------------------------------------------------------
  		public class Filtro_Ultimas_Vacaciones_De_Empleado extends JDialog{
  			
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
  			JTextField txtBuscar = new JTextField();
  			
  			@SuppressWarnings({ "rawtypes", "unchecked" })
  			public Filtro_Ultimas_Vacaciones_De_Empleado()	{
  				this.setModal(true);
  				this.setTitle("Filtro de Vacaciones de Empleados");
  				txtBuscar.addKeyListener(new KeyAdapter() { 
  					public void keyReleased(final KeyEvent e) { 
  		                filtro(); 
  		            } 
  		        });
  			
  				trsfiltro = new TableRowSorter(model); 
  				tabla.setRowSorter(trsfiltro);  
  				
  				campo.add(lblBuscar).setBounds(30,30,70,20);
  				campo.add(txtBuscar).setBounds(95,30,215,20);
  				campo.add(getPanelTabla()).setBounds(10,70,570,410);
  				
  				cont.add(campo);
  				agregar(tabla);
  				
  				render_filtro(tabla);
  				
  				this.setSize(600,530);
  				this.setResizable(false);
  				this.setLocationRelativeTo(null);
  				this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
  				
  				tabla.addKeyListener(seleccionEmpleadoconteclado);
  				
  			}
  			private void agregar(final JTable tbl) {
  		        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
  			        public void mouseClicked(MouseEvent e) {
  			        	if(e.getClickCount() == 2){
  			        		
  			        		int fila = tabla.getSelectedRow();
  			    			String folio =  tabla.getValueAt(fila, 0).toString().trim();
  			    			btnReporte.setEnabled(true);
  			    			dispose();
  			    			
  			    			llenarDatosEmpleado(Integer.valueOf(folio));
  			        	}
  			        }
  		        });
  		    }

  			public void llenarDatosEmpleado(int folio){
  				
				try {
					 fechaInicio.setEnabled(true);
	  				 
		  				Obj_Alimentacion_De_Vacaciones vacaciones = new Obj_Alimentacion_De_Vacaciones().buscar_vacaciones_guardadas(folio);
						
		  	            while(tabla_vacaciones_disfrutadas.getRowCount()>0){
		  	                tabla_model.removeRow(0);
		  	        }
		  	        
		  	        Object [][] lista_tabla = new BuscarTablasModel().tabla_de_vacaciones_disfrutadas(folio);
		  	        String[] fila = new String[4];
		  	                for(int i=0; i<lista_tabla.length; i++){
		  	                        fila[0] = lista_tabla[i][0]+"";
		  	                        fila[1] = lista_tabla[i][1]+"";
		  	                        fila[2] = lista_tabla[i][2]+"";
		  	                        fila[3] = lista_tabla[i][3]+"";
		  	                        tabla_model.addRow(fila);
		  	                }
		  	                
		  				txtFolioVacaciones.setText(vacaciones.getFolio_vacaciones()+"");
		  				txtFolioEmpleado.setText(vacaciones.getFolio_empleado()+"");
		  				txtEmpleado.setText(vacaciones.getEmpleado());
		  				txtEstablecimiento.setText(vacaciones.getEstablecimiento());
		  				txtPuesto.setText(vacaciones.getPuesto());
		  				txtFechaIngreso.setText(vacaciones.getFecha_ingreso());
		  				txtFechaIngresoIMSS.setText(vacaciones.getFecha_ingreso_imss());
		  				txtSalarioDiarioIn.setText(vacaciones.getSalario_diario_integrado()+"");
		  				txtGrupoDeVacaciones.setText(vacaciones.getGrupo_vacacional());
		  				txtProximasVacaciones.setText(vacaciones.getProximas_vacaciones()+"");
		  				
		  				
					Date dateIn = new SimpleDateFormat("dd/MM/yyyy").parse(vacaciones.getFecha_inicio());
					fechaInicio.setDate(dateIn);
					
	  				Date dateFin = new SimpleDateFormat("dd/MM/yyyy").parse(vacaciones.getFecha_final());
	  				fechaFin.setDate(dateFin);
	  				
//	  				txtVacaciones.setText(vacaciones.getVacaciones()+"");
//	  				txtPrimaVacacional.setText(vacaciones.getPrima_vacacional()+"");
//	  				txtInfonavit.setText(vacaciones.getInfonavit()+"");
//	  				txtSueldo.setText(vacaciones.getSueldo_semana()+"");
//	  				txtCorteCaja.setText(vacaciones.getCorte_de_caja()+"");
//	  				txtFSodas.setText(vacaciones.getFuente_de_sodas()+"");
//	  				txtPrestamo.setText(vacaciones.getPrestamo()+"");
//	  				txtPension.setText(vacaciones.getPension_alimenticia()+"");
//	  				txtDescansoPagado.setText(vacaciones.getDias_descanso_vacaciones()+"");
//	  				lblTotal.setText(vacaciones.getTotal()+"");
//	  				chbStatus.setSelected(vacaciones.isStatus());
//	  				txtVacacionesC.setText(vacaciones.getVacaciones_c()+"");
//	  				txtPrimaVacacionalC.setText(vacaciones.getPrima_vacacional_c()+"");
//	  				txtInfonavitC.setText(vacaciones.getInfonavit()+"");
//	  				txtSueldoC.setText(vacaciones.getSueldo_semana_c()+"");
//	  				txtPrestamoC.setText(vacaciones.getPrestamo()+"");
//	  				txtPensionC.setText(vacaciones.getPension_alimenticia()+"");
//	  				txtVacacionesC.setText(vacaciones.getVacaciones_c()+"");
//	  				txtPrimaVacacionalC.setText(vacaciones.getPrima_vacacional_c()+"");
//	  				txtSueldoC.setText(vacaciones.getSueldo_semana_c()+"");
	  				
	  				ImageIcon tmpIconDefault = new ImageIcon(System.getProperty("user.dir")+"/tmp/tmp.jpg");
	  		        Icon iconoDefault = new ImageIcon(tmpIconDefault.getImage().getScaledInstance(btnFoto.getWidth(), btnFoto.getHeight(), Image.SCALE_DEFAULT));
	  		        btnFoto.setIcon(iconoDefault);
	  				
				} catch (ParseException e) {
					e.printStackTrace();
				}
  				
  		         btnBuscar.setEnabled(false);
  		         btnNuevo.setEnabled(false);
  		          
  			}
  			
  			@SuppressWarnings("unchecked")
  			public void filtro() { 
  					trsfiltro.setRowFilter(RowFilter.regexFilter(txtBuscar.getText().toUpperCase().trim(), 1));
  			}  
  			
  			public void render_filtro(final JTable tb){
  				tb.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",10));
  				for(int i = 1; i<tb.getColumnCount(); i++){
  					switch(i){
  							case 0: tb.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","derecha","Arial","negrita",11)); break;
  							case 1: tb.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",11) ); break;
  							default:tb.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","centro"	,"Arial","negrita",11)); break;
  						}
  				}
  				
  				tb.getTableHeader().setReorderingAllowed(false) ;
  		    	tb.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
  		    	
  		    	int x=100;
  		    	
  		    	tb.getColumnModel().getColumn(0 ).setMaxWidth(x-50);   
  		    	tb.getColumnModel().getColumn(0 ).setMinWidth(x-50);   
  		    	tb.getColumnModel().getColumn(1 ).setMaxWidth(x*3);
  		    	tb.getColumnModel().getColumn(1 ).setMinWidth(x*3);
  		    	tb.getColumnModel().getColumn(2 ).setMaxWidth(x*2); 
  		    	tb.getColumnModel().getColumn(2 ).setMinWidth(x*2); 
  		    	                                          
  			}
  			
  			private JScrollPane getPanelTabla()	{	
  				
  				tabla.getColumnModel().getColumn(0).setHeaderValue("Folio");
  				tabla.getColumnModel().getColumn(1).setHeaderValue("Empleado");
  				tabla.getColumnModel().getColumn(2).setHeaderValue("Establecimiento");
  				
  				new Connexion();
  				
  				Statement s;
  				ResultSet rs;
  				try {
  					s = con.conexion().createStatement();
  					rs = s.executeQuery("exec sp_select_ultimas_vacaciones_por_empleado" );
  					
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
  			
  			KeyListener seleccionEmpleadoconteclado = new KeyListener() {
  				@SuppressWarnings({ "static-access", "unused" })
  				@Override
  				public void keyTyped(KeyEvent e) {
  					char caracter = e.getKeyChar();
  					
  					if(caracter==e.VK_ENTER){
  					int fila=tabla.getSelectedRow()-1;
  					String folio = tabla.getValueAt(fila,0).toString().trim();
  						
  					btnBuscar.doClick();
  					dispose();
  					
  					}
  				}
  				@Override
  				public void keyPressed(KeyEvent e){}
  				@Override
  				public void keyReleased(KeyEvent e){}
  										
  			};
  			
  			
  		}
		
//Filtro Empleado----------------------------------------------------------------------------
	public class Filtro_Empleado_Para_Vacaciones_Nuevas extends JDialog{
		
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
		JTextField txtBuscar = new JTextField();
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Filtro_Empleado_Para_Vacaciones_Nuevas(){
			this.setModal(true);
			this.setTitle("Filtro Empleados");
			txtBuscar.addKeyListener(new KeyAdapter() { 
				public void keyReleased(final KeyEvent e) { 
	                filtro(); 
	            } 
	        });
		
			trsfiltro = new TableRowSorter(model); 
			tabla.setRowSorter(trsfiltro);  
			
			campo.add(lblBuscar).setBounds(30,30,70,20);
			campo.add(txtBuscar).setBounds(95,30,215,20);
			campo.add(getPanelTabla()).setBounds(10,70,570,410);
			
			cont.add(campo);
			agregar(tabla);
			
			render_filtro(tabla);
			
			this.setSize(600,530);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			
			tabla.addKeyListener(seleccionEmpleadoconteclado);
			
		}
		private void agregar(final JTable tbl) {
	        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
		        public void mouseClicked(MouseEvent e) {
		        	if(e.getClickCount() == 2){
		        		
		        		int fila = tabla.getSelectedRow();
		    			String folio =  tabla.getValueAt(fila, 0).toString().trim();
		    			dispose();
		    			
		    			llenarDatosEmpleado(Integer.valueOf(folio));
		    			fechaInicio.transferFocus();
		        	}
		        }
	        });
	        
	    }
		public void llenarDatosEmpleado(int folio){
			
				 fechaInicio.setEnabled(true);
				 btnDeshacer.setEnabled(true);
 				 
	  				Obj_Alimentacion_De_Vacaciones vacaciones = new Obj_Alimentacion_De_Vacaciones().buscar(folio);
					
	  	            while(tabla_vacaciones_disfrutadas.getRowCount()>0){
	  	                tabla_model.removeRow(0);
	  	        }
	  	        
	  	        Object [][] lista_tabla = new BuscarTablasModel().tabla_de_vacaciones_disfrutadas(folio);
	  	        String[] fila = new String[4];
	  	                for(int i=0; i<lista_tabla.length; i++){
	  	                        fila[0] = lista_tabla[i][0]+"";
	  	                        fila[1] = lista_tabla[i][1]+"";
	  	                        fila[2] = lista_tabla[i][2]+"";
	  	                        fila[3] = lista_tabla[i][3]+"";
	  	                        tabla_model.addRow(fila);
	  	                }
	  	                
	  				txtFolioVacaciones.setText(vacaciones.getFolio_vacaciones()+"");
	  				txtFolioEmpleado.setText(vacaciones.getFolio_empleado()+"");
	  				txtEmpleado.setText(vacaciones.getEmpleado());
	  				txtEstablecimiento.setText(vacaciones.getEstablecimiento());
	  				txtPuesto.setText(vacaciones.getPuesto());
	  				txtFechaIngreso.setText(vacaciones.getFecha_ingreso());
	  				txtFechaIngresoIMSS.setText(vacaciones.getFecha_ingreso_imss());
	  				txtSalarioDiarioIn.setText(vacaciones.getSalario_diario_integrado()+"");
	  				txtGrupoDeVacaciones.setText(vacaciones.getGrupo_vacacional());
	  				txtProximasVacaciones.setText(vacaciones.getProximas_vacaciones()+"");
	  				
 				ImageIcon tmpIconDefault = new ImageIcon(System.getProperty("user.dir")+"/tmp/tmp.jpg");
 		         Icon iconoDefault = new ImageIcon(tmpIconDefault.getImage().getScaledInstance(btnFoto.getWidth(), btnFoto.getHeight(), Image.SCALE_DEFAULT));
 		         btnFoto.setIcon(iconoDefault);
 				
	         btnBuscar.setEnabled(false);
	         btnNuevo.setEnabled(false);
		}
		
		public void render_filtro(final JTable tb){
				tb.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",10));
				for(int i = 1; i<tb.getColumnCount(); i++){
					switch(i){
							case 0: tb.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","derecha","Arial","negrita",11)); break;
							case 1: tb.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",11) ); break;
							default:tb.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","centro"	,"Arial","negrita",11)); break;
						}
				}
				
				tb.getTableHeader().setReorderingAllowed(false) ;
		    	tb.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		    	
		    	int x=100;
		    	
		    	tb.getColumnModel().getColumn(0 ).setMaxWidth(x-50);   
		    	tb.getColumnModel().getColumn(0 ).setMinWidth(x-50);   
		    	tb.getColumnModel().getColumn(1 ).setMaxWidth(x*3);
		    	tb.getColumnModel().getColumn(1 ).setMinWidth(x*3);
		    	tb.getColumnModel().getColumn(2 ).setMaxWidth(x*2); 
		    	tb.getColumnModel().getColumn(2 ).setMinWidth(x*2); 
		    	                                          
			}
		
		@SuppressWarnings("unchecked")
		public void filtro() { 
				trsfiltro.setRowFilter(RowFilter.regexFilter(txtBuscar.getText().toUpperCase().trim(), 1));
		}  
		
		private JScrollPane getPanelTabla()	{
			
				tabla.getColumnModel().getColumn(0).setHeaderValue("Folio");
				tabla.getColumnModel().getColumn(1).setHeaderValue("Empleado");
				tabla.getColumnModel().getColumn(2).setHeaderValue("Establecimiento");
				
				
			new Connexion();
			
			Statement s;
			ResultSet rs;
			try {
				s = con.conexion().createStatement();
				rs = s.executeQuery("exec sp_select_permiso_checador_filtro_empleados" );
				
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
		
		KeyListener seleccionEmpleadoconteclado = new KeyListener() {
			@SuppressWarnings({ "static-access", "unused" })
			@Override
			public void keyTyped(KeyEvent e) {
				char caracter = e.getKeyChar();
				
				if(caracter==e.VK_ENTER){
				int fila=tabla.getSelectedRow()-1;
				String folio = tabla.getValueAt(fila,0).toString().trim();
					
				btnBuscar.doClick();
				dispose();
				}
			}
			@Override
			public void keyPressed(KeyEvent e){}
			@Override
			public void keyReleased(KeyEvent e){}
									
		};
	}
	
	public static void main(String [] arg){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Alimentacion_De_Vacaciones().setVisible(true);
		}catch(Exception e){
			System.err.println("Error :"+ e.getMessage());
		}
	}
}
