package Cat_Lista_de_Raya;

import java.awt.Color;
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
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
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
import Obj_Principal.JCButton;
import Obj_Principal.JCTextArea;
import Obj_Principal.JCTextField;
import Obj_Renders.tablaRenderer;

import com.toedter.calendar.JDateChooser;

@SuppressWarnings("serial")
public class Cat_Alimentacion_De_Vacaciones extends JFrame {
	
	double sd_nc = 0;
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
//	campos de datos de enpleado	
	JLabel lblMargenEmpleado = new JLabel();
	JTextField txtFolioVacaciones = new Componentes().text(new JCTextField(), "Folio Vacaciones", 10, "Int");
	JTextField txtFechaIngreso = new Componentes().text(new JCTextField(), "Fecha De Ingreso", 20, "Int");
	JTextField txtFechaIngresoIMSS = new Componentes().text(new JCTextField(), "Fecha De Ingreso IMSS", 20, "Int");
	
	JCButton btnBuscar = new JCButton("","buscar.png","Azul");
	JCButton btnNuevo = new JCButton("Nuevo","","Azul");
	
	JTextField txtFolioEmpleado = new Componentes().text(new JCTextField(), "Folio Empleado", 20, "Int");
	JTextField txtEmpleado = new Componentes().text(new JCTextField(), "Empleado", 20, "Int");
	JTextField txtEstablecimiento = new Componentes().text(new JCTextField(), "Establecimiento", 20, "Int");
	JTextField txtPuesto = new Componentes().text(new JCTextField(), "Puesto", 20, "Int");
//	JTextField txtSalarioDiarioIn = new Componentes().text(new JCTextField(), "Salario Diario Integrado", 20, "Int");
	JTextField txtGrupoDeVacaciones = new Componentes().text(new JCTextField(), "Grupo De Vacaciones", 20, "Int");
	JTextField txtProximasVacaciones = new Componentes().text(new JCTextField(), "Proximas Vacaciones", 20, "Int");
	
	JCButton btnFoto = new JCButton("","","Azul");
	
	String fileFoto = System.getProperty("user.dir")+"/Iconos/Un.JPG";
    ImageIcon tmpIconAuxFoto = new ImageIcon(fileFoto);
    
    JTextField txtSaldoRestanteDePrestamo = new Componentes().text( new JCTextField(), "Deuda Pendiete", 15, "Double");
    
//	campo de vacaciones de empleados
	JLabel lblMargenVacaciones = new JLabel();
	
//	JCheckBox chbStatus = new JCheckBox("Status",true);
	
	JDateChooser fechaInicioVacaciones = new Componentes().jchooser(new JDateChooser()  ,"",0);
	JDateChooser fechaRegreso = new Componentes().jchooser(new JDateChooser()  ,"",0);
	JTextField txtDiasPendientesDePago = new Componentes().text( new JCTextField(), "Dias Pendientes De Pago", 15, "Int");
	
	JTextField txtMensualidad = new Componentes().text( new JCTextField(), "Mendualidad", 15, "Double");
	JTextField txtMensualidadDia = new Componentes().text( new JCTextField(), "Mens. Dia", 15, "Double");
	
	JTextField txtCuotaDiaria = new Componentes().text( new JCTextField(), "Cuota Diaria", 15, "Double");
	
	JTextField txtSueldo = new Componentes().text( new JCTextField(), "Sueldo", 15, "Double");
	JTextField txtVacaciones = new Componentes().text( new JCTextField(), "Vacaciones", 15, "Double");
	JTextField txtDescansoPagado = new Componentes().text( new JCTextField(), "Descanso Pagado", 15, "Double");
	JTextField txtPrimaVacacional = new Componentes().text( new JCTextField(), "Prima Vacacional", 15, "Double");
	
	JTextField txtPercepciones = new Componentes().text( new JCTextField(), "Percepciones", 15, "Double");
	
	JTextField txtPrestamo = new Componentes().text( new JCTextField(), "Prestamo", 15, "Double");
	JTextField txtInfonavit = new Componentes().text( new JCTextField(), "Infonavit", 15, "Double");
	JTextField txtInfonacot = new Componentes().text( new JCTextField(), "Infonacot", 15, "Double");
	JTextField txtPension = new Componentes().text( new JCTextField(), "Pension Alimenticia", 15, "Double");
//	JTextField txtFSodas = new Componentes().text( new JCTextField(), "Fuente de Sodas", 15, "Double");
//	JTextField txtCorteCaja = new Componentes().text( new JCTextField(), "Corte Caja", 15, "Double");
	
	JTextField txtImss = new Componentes().text( new JCTextField(), "IMSS", 15, "Double");
	JTextField txtIspt = new Componentes().text( new JCTextField(), "ISPT", 15, "Double");
	
	JTextField txtOtrasDeducciones = new Componentes().text( new JCTextField(), "Otras Deducciones", 15, "Double");
	JTextField txtOtrasPercepciones = new Componentes().text( new JCTextField(), "Otras Percepciones", 15, "Double");
	JTextField txtCheques = new Componentes().text( new JCTextField(), "Cheques", 15, "Double");
	
	JTextField txtEfectivo = new Componentes().text( new JCTextField(), "Total a Pagar", 15, "Double");
	
	JLabel lblDiasVacaciones = new JLabel("Dias De Vacaciones: ");
	JLabel lblDiasVac = new JLabel("0");
	JLabel lblDias_De_Descanso_Pagados = new JLabel("Dias De Descansos Pagados: ");
	JLabel lblDescansos_Pagados = new JLabel("0");
	
	JLabel lblMargenVacacionesC = new JLabel();
	
	JTextField txtCuotaDiariaC = new Componentes().text( new JCTextField(), "Cuota Diaria", 15, "Double");
	JTextField txtSDI = new Componentes().text( new JCTextField(), "S.D.I.", 15, "Double");
	
	JTextField txtSueldoC = new Componentes().text( new JCTextField(), "Sueldo", 15, "Double");
	JTextField txtVacacionesC = new Componentes().text( new JCTextField(), "Vacaciones", 15, "Double");
	JTextField txtDescansoPagadoC = new Componentes().text( new JCTextField(), "Descanso Pagado", 15, "Double");
	JTextField txtPrimaVacacionalC = new Componentes().text( new JCTextField(), "Prima Vacacional", 15, "Double");
	
	JTextField txtPercepcionesC = new Componentes().text( new JCTextField(), "Percepciones", 15, "Double");
	
//	JTextField txtInfonavitC = new Componentes().text( new JCTextField(), "Infonavit C.", 15, "Double");
//	JTextField txtInfonacotC = new Componentes().text( new JCTextField(), "Infonacot C.", 15, "Double");
//	JTextField txtPrestamoC = new Componentes().text( new JCTextField(), "Prestamo C.", 15, "Double");
//	JTextField txtPensionC = new Componentes().text( new JCTextField(), "Pension Alimenticia C.", 15, "Double");
	
	JTextField txtPrimaDominical = new Componentes().text( new JCTextField(), "Prima Dominical", 15, "Double");
	JTextField txtBonoDespensa = new Componentes().text( new JCTextField(), "Bono Despensa", 15, "Double");
	JTextField txtPremioPorPuntualidad = new Componentes().text( new JCTextField(), "Premio Por Puntualidad", 15, "Double");
	JTextField txtPremioPorAsitencia = new Componentes().text( new JCTextField(), "Premio Por Asistencia", 15, "Double");
	JTextField txtSubsidio = new Componentes().text( new JCTextField(), "Subsidio", 15, "Double");
	
	JCButton btnDeshacer = new JCButton("Deshacer","deshacer16.png","Azul");
	JCButton btnReporte = new JCButton("Generar Reporte","","Azul");
	JCButton btnCalcular = new JCButton("Calcular","","Azul");
	JCButton btnGuardar = new JCButton("Guardar","","Azul");
	
//	campo de tabla de vacaciones
	JLabel lblMargenTabla = new JLabel();
	
	DecimalFormat DF = new DecimalFormat("#0.00");
	
    DefaultTableModel tabla_model = new DefaultTableModel(
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
                             return false;
                     }
            };
            
        	JTable tabla_vacaciones_disfrutadas = new JTable(tabla_model);
        	JScrollPane panelScroll = new JScrollPane(tabla_vacaciones_disfrutadas);
        	
        	JTextArea txaObservacion = new Componentes().textArea(new JCTextArea(), "Observaciones", 400);
        	JScrollPane scrollObservacion = new JScrollPane(txaObservacion);

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
		
		int x=15,y=15;
//		campos de datos de empleado
		panel.add(lblMargenEmpleado).setBounds(3, 0, 675, 167);
		panel.add(new JLabel("Folio Vacaciones: ")).setBounds(20, y, 110, 20);
		panel.add(txtFolioVacaciones).setBounds(130, y, 60, 20);
		
		panel.add(btnBuscar).setBounds(190, y, 30, 20);
		panel.add(btnNuevo).setBounds(223, y, 80, 20);
		panel.add(btnDeshacer).setBounds(306, y, 110, 20);
		panel.add(btnGuardar).setBounds(419, y, 90, 20);
		
		
		panel.add(new JLabel("Empleado: ")).setBounds(20, y+=25, 110, 20);
		panel.add(txtFolioEmpleado).setBounds(130, y, 50, 20);
		panel.add(txtEmpleado).setBounds(181, y, 329, 20);
		
		panel.add(new JLabel("Establecimiento: ")).setBounds(20, y+=25, 110, 20);
		panel.add(txtEstablecimiento).setBounds(130, y, 190, 20);
//		panel.add(chbStatus).setBounds(360, y, 80, 20);
		
		panel.add(new JLabel("Puesto: ")).setBounds(20, y+=25, 60, 20);
		panel.add(txtPuesto).setBounds(130, y, 380, 20);
		
		panel.add(new JLabel("Fecha Ingreso: ")).setBounds(20, y+=25, 130, 20);
		panel.add(txtFechaIngreso).setBounds(125, y, 140, 20);
		
		panel.add(new JLabel("Grupo De Vacaciones: ")).setBounds(290, y, 140, 20);
		panel.add(txtGrupoDeVacaciones).setBounds(400, y, 110, 20);
		
		panel.add(new JLabel("Fecha Ingreso IMSS: ")).setBounds(20, y+=25, 120, 20);
		panel.add(txtFechaIngresoIMSS).setBounds(125, y, 140, 20);
		
		panel.add(new JLabel("Proximas Vacaciones: ")).setBounds(290, y, 140, 20);
		panel.add(txtProximasVacaciones).setBounds(400, y, 110, 20);
		
		panel.add(btnFoto).setBounds(523, 11, 150, 150);

		panel.add(new JLabel("Fecha Inicial: ")).setBounds(7, y+=30, 140, 20);
		panel.add(fechaInicioVacaciones).setBounds(70, y, 100, 20);
		
		panel.add(new JLabel("Regresa el: ")).setBounds(175, y, 100, 20);
		panel.add(fechaRegreso).setBounds(235, y, 100, 20);
		
		panel.add(new JLabel("Dias Pendientes De Pago: ")).setBounds(345, y, 150, 20);
		panel.add(txtDiasPendientesDePago).setBounds(470, y, 100, 20);
		
		panel.add(new JLabel("Mensualidad: ")).setBounds(7, y+=25, 140, 20);
		panel.add(txtMensualidad).setBounds(70, y, 75, 20);
		panel.add(new JLabel("Mensualidad Dia: ")).setBounds(x+135, y, 140, 20);
		panel.add(txtMensualidadDia).setBounds(x+220, y, 75, 20);
		
		panel.add(new JLabel("Prestamo pendiente: ")).setBounds(x+335, y, 140, 20);
		panel.add(txtSaldoRestanteDePrestamo).setBounds(x+455, y, 100, 20);
		
//		campo de vacaciones no contables de empleados
		panel.add(lblMargenVacaciones).setBounds(130, 215, 120, 410);
		
		panel.add(new JLabel("Cuota Diaria: ")).setBounds(x, y+=35, 140, 20);
		panel.add(txtCuotaDiaria).setBounds(x+125, y, 100, 20);
		
		panel.add(new JLabel("SDI : ")).setBounds(x, y+=25, 140, 20);
		
		panel.add(new JLabel("(+) Sueldo : ")).setBounds(x, y+=35, 140, 20);
		panel.add(txtSueldo).setBounds(x+125, y, 100, 20);

		panel.add(new JLabel("(+) Vacaciones: ")).setBounds(x, y+=25, 140, 20);
		panel.add(txtVacaciones).setBounds(x+125, y, 100, 20);
		
		panel.add(new JLabel("(+) Descanso Pagado: ")).setBounds(x, y+=25, 140, 20);
		panel.add(txtDescansoPagado).setBounds(x+125, y, 100, 20);
		
		panel.add(new JLabel("(+) Prima Vacacional: ")).setBounds(x, y+=25, 140, 20);
		panel.add(txtPrimaVacacional).setBounds(x+125, y, 100, 20);
		
		panel.add(new JLabel("(=) Total Percepciones: ")).setBounds(x, y+=25, 140, 20);
		panel.add(txtPercepciones).setBounds(x+125, y, 100, 20);
		
		panel.add(new JLabel("(-)  Prestamo: ")).setBounds(x, y+=25, 140, 20);
		panel.add(txtPrestamo).setBounds(x+125, y, 100, 20);
		
		panel.add(new JLabel("(-)  Infonavit: ")).setBounds(x, y+=25, 100, 20);
		panel.add(txtInfonavit).setBounds(x+125, y, 100, 20);
		
		panel.add(new JLabel("(-)  Infonacot: ")).setBounds(x, y+=25, 100, 20);
		panel.add(txtInfonacot).setBounds(x+125, y, 100, 20);
		
		panel.add(new JLabel("(-)  Pension Alimenticia: ")).setBounds(x, y+=25, 140, 20);
		panel.add(txtPension).setBounds(x+125, y, 100, 20);
		
//		panel.add(new JLabel("Fuente De Sodas: ")).setBounds(x, y+=25, 120, 20);
//		panel.add(txtFSodas).setBounds(x+125, y, 100, 20);
		
//		panel.add(new JLabel("Corte De Caja: ")).setBounds(x, y+=25, 140, 20);
//		panel.add(txtCorteCaja).setBounds(x+125, y, 100, 20);
		
		panel.add(new JLabel("(-)  Otras Deducciones: ")).setBounds(x, y+=25, 140, 20);
		panel.add(txtOtrasDeducciones).setBounds(x+125, y, 100, 20);
		
		panel.add(new JLabel("(+) Otras Percepciones: ")).setBounds(x, y+=25, 140, 20);
		panel.add(txtOtrasPercepciones).setBounds(x+125, y, 100, 20);
		
		panel.add(new JLabel("Cheque: ")).setBounds(x, y+=25, 140, 20);
		panel.add(txtCheques).setBounds(x+125, y, 100, 20);
		
		panel.add(new JLabel("Efectivo: ")).setBounds(x, y+=25, 140, 20);
		panel.add(txtEfectivo).setBounds(x+125, y, 100, 20);
		
//		campo de vacaciones contables de empleados
		x=255; y=205;
		panel.add(lblMargenVacacionesC).setBounds(x, 215, 425, 255);
		
		panel.add(txtCuotaDiariaC).setBounds(x+=10, y+=25, 100, 20);
		panel.add(txtSDI).setBounds(x, y+=25, 100, 20);
		panel.add(txtSueldoC).setBounds(x, y+=35, 100, 20);
		panel.add(txtVacacionesC).setBounds(x, y+=25, 100, 20);
		panel.add(txtDescansoPagadoC).setBounds(x, y+=25, 100, 20);
		panel.add(txtPrimaVacacionalC).setBounds(x, y+=25, 100, 20);
		
		panel.add(txtPercepcionesC).setBounds(x, y+=25, 100, 20);
		
		panel.add(panelScroll).setBounds(x-8, y+=80, 420, 85);
		
		panel.add(scrollObservacion).setBounds(x-8, y+=90, 420, 63);
		
//		panel.add(txtPrestamoC).setBounds(250, y+=25, 100, 20);
//		panel.add(txtInfonavitC).setBounds(250, y+=25, 100, 20);
//		panel.add(txtInfonacotC).setBounds(250, y+=25, 100, 20);
//		panel.add(txtPensionC).setBounds(250, y+=25, 100, 20);
		
		x=530;
		y=200;
		
		panel.add(lblDiasVacaciones).setBounds(390, y+=25, 220, 20);
		panel.add(lblDiasVac).setBounds(x, y, 50, 20);
		
		panel.add(lblDias_De_Descanso_Pagados).setBounds(390, y+=20, 220, 20);
		panel.add(lblDescansos_Pagados).setBounds(580, y, 50, 20);
		
		panel.add(new JLabel("(+) Prima Dominical: ")).setBounds(390, y+=45, 140, 20);
		panel.add(txtPrimaDominical).setBounds(x, y, 100, 20);
		
		panel.add(new JLabel("(+) Bono Despensa: ")).setBounds(390, y+=25, 140, 20);
		panel.add(txtBonoDespensa).setBounds(x, y, 100, 20);

		panel.add(new JLabel("(+) Premio Por Puntualidad: ")).setBounds(390, y+=25, 140, 20);
		panel.add(txtPremioPorPuntualidad).setBounds(x, y, 100, 20);
		
		panel.add(new JLabel("(+) Premio Por Asistencia: ")).setBounds(390, y+=25, 140, 20);
		panel.add(txtPremioPorAsitencia).setBounds(x, y, 100, 20);
		
		panel.add(new JLabel("(+) Subsidio: ")).setBounds(390, y+=25, 140, 20);
		panel.add(txtSubsidio).setBounds(x, y, 100, 20);
		
		panel.add(new JLabel("(-) Imss: ")).setBounds(390, y+=25, 140, 20);
		panel.add(txtImss).setBounds(x, y, 100, 20);
		panel.add(new JLabel("(-) Ispt: ")).setBounds(390, y+=25, 140, 20);
		panel.add(txtIspt).setBounds(x, y, 100, 20);
		
//		panel.add(btnCalcular).setBounds(20, y+=14, 90, 20);
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
        btnGuardar.addActionListener(opGuardar);
//        btnReporte.addActionListener(opReporte);
        
        txtDiasPendientesDePago.addKeyListener(opRecalcular);
        txtMensualidad.addKeyListener(opRecalcular);
        txtPrimaDominical.addKeyListener(opRecalcular);
        txtBonoDespensa.addKeyListener(opRecalcular);
        txtPremioPorPuntualidad.addKeyListener(opRecalcular);
        txtPremioPorAsitencia.addKeyListener(opRecalcular);
        txtSubsidio.addKeyListener(opRecalcular);
        txtImss.addKeyListener(opRecalcular);
        txtIspt.addKeyListener(opRecalcular);
        txtOtrasDeducciones.addKeyListener(opRecalcular);
        txtOtrasPercepciones.addKeyListener(opRecalcular);
        
        
//    	FUNCION PARA AGREGAR UNA ACCION AL SELECCIONAR UNA FECHA
        fechaInicioVacaciones.getDateEditor().addPropertyChangeListener(opCalcularAutomaticoConFechaIn);

		cont.add(panel);
		this.setSize(700,670);
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
//        txtSalarioDiarioIn.setEditable(false);
        txtGrupoDeVacaciones.setEditable(false);
        txtProximasVacaciones.setEditable(false);
		
        fechaRegreso.setEnabled(false);
        
        txtMensualidadDia.setEditable(false);
        txtSaldoRestanteDePrestamo.setEditable(false);
        
        txtCuotaDiaria.setEditable(false);
        txtSDI.setEditable(false);
        
        txtVacaciones.setEditable(false);
        txtDescansoPagado.setEditable(false);
		txtPrimaVacacional.setEditable(false);
		txtPercepciones.setEditable(false);
		txtInfonavit.setEditable(false);
		txtInfonacot.setEditable(false);
		txtSueldo.setEditable(false);
		txtPrestamo.setEditable(false);
		txtPension.setEditable(false);
//        txtFSodas.setEditable(false);
//        txtCorteCaja.setEditable(false);
        txtCheques.setEditable(false);
        txtEfectivo.setEditable(false);
		
        txtCuotaDiariaC.setEditable(false);
        
		txtVacacionesC.setEditable(false);
		txtDescansoPagadoC.setEditable(false);
		txtPrimaVacacionalC.setEditable(false);
		txtPercepcionesC.setEditable(false);
//		txtInfonavitC.setEditable(false);
//		txtInfonacotC.setEditable(false);
		txtSueldoC.setEditable(false);
//		txtPrestamoC.setEditable(false);
//		txtPensionC.setEditable(false);
	}
	
	public void activar_descactivar_campos(boolean activ){
		fechaInicioVacaciones.setEnabled(activ);
		txtMensualidad.setEditable(activ);
		txtDiasPendientesDePago.setEditable(activ);
		txtOtrasDeducciones.setEditable(activ);
		txtOtrasPercepciones.setEditable(activ);
		txtPrimaDominical.setEditable(activ);
		txtBonoDespensa.setEditable(activ);
		txtPremioPorPuntualidad.setEditable(activ);
		txtPremioPorAsitencia.setEditable(activ);
		txtSubsidio.setEditable(activ);
		txtImss.setEditable(activ);
		txtIspt.setEditable(activ);
		btnDeshacer.setEnabled(activ);
		btnGuardar.setEnabled(activ);
		
		txaObservacion.setEnabled(activ);
		String color = activ?"FFFFFF":"EBEBEB";
		txaObservacion.setBackground(new Color(Integer.parseInt(color,16)));
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
    	
    	int x=100;
    	
    	tb.getColumnModel().getColumn(0 ).setMaxWidth(x);   
    	tb.getColumnModel().getColumn(0 ).setMinWidth(x);   
    	tb.getColumnModel().getColumn(1 ).setMaxWidth(x+20);
    	tb.getColumnModel().getColumn(1 ).setMinWidth(x+20);
    	tb.getColumnModel().getColumn(2 ).setMaxWidth(x+20); 
    	tb.getColumnModel().getColumn(2 ).setMinWidth(x+20); 
    	                                          
    	tb.getColumnModel().getColumn(3 ).setMaxWidth(60);
    	tb.getColumnModel().getColumn(3 ).setMinWidth(60);		
	}
	
	ActionListener opNuevo = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			movimiento="GUARDAR";
			new Filtro_Empleado_Para_Vacaciones_Nuevas().setVisible(true);
		}
	};
	
	ActionListener opBuscar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			movimiento="MODIFICAR";
			new Filtro_Ultimas_Vacaciones_De_Empleado().setVisible(true);
		}
	};
	
	PropertyChangeListener opCalcularAutomaticoConFechaIn = new PropertyChangeListener() {
  	  public void propertyChange(PropertyChangeEvent e) {
  	            if ("date".equals(e.getPropertyName())) {
  	            	
	  	            	if(fechaInicioVacaciones.getDate() != null){
	  	            		
//  	                System.out.println(e.getPropertyName()+ ": " + (Date) e.getNewValue());
//  calcula rango de fecha-------------------------------------------------------------------------------------------------
		  	                int rangoEnDias = Integer.valueOf(lblDiasVac.getText())+Integer.valueOf(lblDescansos_Pagados.getText()); //dias totales de vacaciones
		  	                int tiempo =(24 * 60 * 60 * 1000); 			// 1 dia en milisegundos
		  	                long diasVacaciones = rangoEnDias * tiempo; // dia de vacaciones en milisegundos
		  	                
		  	                long tiempoInicioDeVacaciones = fechaInicioVacaciones.getDate().getTime(); 		// obtener fecha de inicio de vacaciones
		  	                Date fechaLimite = new Date(tiempoInicioDeVacaciones + diasVacaciones); //fecha de regreso a laborar
		  	                
		  	              fechaRegreso.setDate(fechaLimite);	// asignar fecha a calendario
// -------------------------------------------------------------------------------------------------------------------------
		  	              txtDiasPendientesDePago.requestFocus();
	  	            	}
  	            }
  	  		}
  	    };
  	    
  	    float prestamoInicial = 0;
  	    public void calcular(){
  	    	
  	    	float prestamoCalculado = prestamoInicial==0 ? 0:(
												  				(
											  						(
										  								( prestamoInicial / (Integer.valueOf(lblDiasVac.getText())+Integer.valueOf(lblDescansos_Pagados.getText()))  )
													  				)*
													  				( Integer.valueOf(lblDiasVac.getText())+
												  					  Integer.valueOf(lblDescansos_Pagados.getText())+
												  					  Integer.valueOf(txtDiasPendientesDePago.getText().equals("")?"0":txtDiasPendientesDePago.getText())  )
												  				)
													  		);
  	    	
  	    	if(prestamoCalculado>Float.valueOf(txtSaldoRestanteDePrestamo.getText())){
  	    		JOptionPane.showMessageDialog(null, "El Descuento De Prestamo Será Corregido (El Descuento Sugerido Fué: $"+DF.format(prestamoCalculado)+"\nCuando Su Deuda Total Es De: $"+txtSaldoRestanteDePrestamo.getText()+" ).","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
  	    		txtPrestamo.setText(txtSaldoRestanteDePrestamo.getText());
  	    		
  	    	}else{
  	    		txtPrestamo.setText(DF.format(prestamoCalculado));
  	    	}
	  	  	
	  	  	
  	    	txtMensualidadDia.setText(DF.format(Float.valueOf(txtMensualidad.getText().trim().equals("")?"0":txtMensualidad.getText())/30)+"");
  	    	txtCuotaDiaria.setText(DF.format(sd_nc+(Float.valueOf(txtMensualidadDia.getText().trim())))+"");
  	    	
  	    	txtSueldo.setText(DF.format(Float.valueOf(txtCuotaDiaria.getText()) * Float.valueOf(txtDiasPendientesDePago.getText().equals("")?"0":txtDiasPendientesDePago.getText())));
              txtVacaciones.setText(DF.format(Float.valueOf(txtCuotaDiaria.getText()) * Float.valueOf(lblDiasVac.getText())));	
              txtDescansoPagado.setText(DF.format(Float.valueOf(txtCuotaDiaria.getText()) * Float.valueOf(lblDescansos_Pagados.getText())));	
              txtPrimaVacacional.setText(DF.format(Float.valueOf(txtVacaciones.getText()) / 4));
              
              txtPercepciones.setText(DF.format(Float.valueOf(txtSueldo.getText()) +
						  	            		Float.valueOf(txtVacaciones.getText()) +
						  	            		Float.valueOf(txtDescansoPagado.getText()) +
						  	            		Float.valueOf(txtPrimaVacacional.getText())  ));
              
              txtSueldoC.setText(DF.format(Float.valueOf(txtCuotaDiariaC.getText()) * Float.valueOf(txtDiasPendientesDePago.getText().equals("")?"0":txtDiasPendientesDePago.getText())));
              txtVacacionesC.setText(DF.format(Float.valueOf(txtCuotaDiariaC.getText()) * Float.valueOf(lblDiasVac.getText())));	
              txtDescansoPagadoC.setText(DF.format(Float.valueOf(txtCuotaDiariaC.getText()) * Float.valueOf(lblDescansos_Pagados.getText())));	
              txtPrimaVacacionalC.setText(DF.format(Float.valueOf(txtVacacionesC.getText()) / 4));
              
              txtPercepcionesC.setText(DF.format(Float.valueOf(txtSueldoC.getText()) 
							  	            		+ Float.valueOf(txtVacacionesC.getText()) 
							  	            		+ Float.valueOf(txtDescansoPagadoC.getText()) 
							  	            		+ Float.valueOf(txtPrimaVacacionalC.getText())  
							  	            		
							  	            		+ Float.valueOf(txtPrimaDominical.getText().equals("")?"0":txtPrimaDominical.getText())
							            			+ Float.valueOf(txtBonoDespensa.getText().equals("")?"0":txtBonoDespensa.getText())
							            			+ Float.valueOf(txtPremioPorPuntualidad.getText().equals("")?"0":txtPremioPorPuntualidad.getText())
							            			+ Float.valueOf(txtPremioPorAsitencia.getText().equals("")?"0":txtPremioPorAsitencia.getText())
							            			+ Float.valueOf(txtSubsidio.getText().equals("")?"0":txtSubsidio.getText())
            		  							)
            		  				);
              
              txtCheques.setText(DF.format(	Float.valueOf(txtPercepcionesC.getText())<=	0	?	0	:
            	  								Float.valueOf(txtPercepcionesC.getText())
						            			- Float.valueOf(txtPrestamo.getText())
						            			- Float.valueOf(txtPension.getText())
						            			- Float.valueOf(txtInfonavit.getText())
						            			- Float.valueOf(txtInfonacot.getText())
						            			- Float.valueOf(txtImss.getText().equals("")?"0":txtImss.getText())
						            			- Float.valueOf(txtIspt.getText().equals("")?"0":txtIspt.getText())
						            	   )
            		  			 );

			txtEfectivo.setText(DF.format(Float.valueOf(txtPercepciones.getText())
										+ Float.valueOf(txtOtrasPercepciones.getText().equals("")?"0":txtOtrasPercepciones.getText())
										- Float.valueOf(txtPrestamo.getText())
										- Float.valueOf(txtInfonavit.getText())
										- Float.valueOf(txtInfonacot.getText())
										- Float.valueOf(txtPension.getText())
//													- Float.valueOf(txtFSodas.getText())
//													- Float.valueOf(txtCorteCaja.getText())
										- Float.valueOf(txtOtrasDeducciones.getText().equals("")?"0":txtOtrasDeducciones.getText())
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
  		
  		public String validaCamposAlGuardar(){
  			String camposRequeridos = "";
  			
  			String f_in = fechaInicioVacaciones.getDate()+"";
  			System.out.println(f_in);
  			
  			camposRequeridos += f_in.equals("null")?"Fecha Inicio\n":"";
  			camposRequeridos += txtDiasPendientesDePago.getText().trim().equals("")?"Dias Pendientes De Pago\n":"";
  			camposRequeridos += txtPrimaDominical.getText().trim().equals("")?"Prima Dominical\n":"";
  			camposRequeridos += txtBonoDespensa.getText().trim().equals("")?"Bono De Despensa\n":"";
  			camposRequeridos += txtPremioPorPuntualidad.getText().trim().equals("")?"Premio Por Puntualidad\n":"";
  			camposRequeridos += txtPremioPorAsitencia.getText().trim().equals("")?"Premio Por Asistencia\n":"";
  			camposRequeridos += txtSubsidio.getText().trim().equals("")?"Subsidio\n":"";
  			camposRequeridos += txtImss.getText().trim().equals("")?"Imss\n":"";
  			camposRequeridos += txtIspt.getText().trim().equals("")?"Ispt\n":"";
  			camposRequeridos += txtOtrasDeducciones.getText().trim().equals("")?"Otras Deducciones\n":"";
  			camposRequeridos += txtOtrasPercepciones.getText().trim().equals("")?"Otras Percepciones\n":"";
  			
  			
  			return camposRequeridos;
  		}
  		
  		String movimiento = "";
  		ActionListener opGuardar = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String validacionDeCampos = validaCamposAlGuardar();
				
				if(validacionDeCampos.equals("")){
					Obj_Alimentacion_De_Vacaciones vacaciones = new Obj_Alimentacion_De_Vacaciones();
					
					vacaciones.setFolio_vacaciones(Integer.valueOf(txtFolioVacaciones.getText().trim()));
					vacaciones.setFolio_empleado(Integer.valueOf(txtFolioEmpleado.getText().trim()));
					vacaciones.setGrupo_vacacional(txtGrupoDeVacaciones.getText());
					vacaciones.setProximas_vacaciones(Integer.valueOf(txtProximasVacaciones.getText().trim()));
					
					vacaciones.setFecha_inicio(new SimpleDateFormat("dd/MM/yyyy").format(fechaInicioVacaciones.getDate()));
					vacaciones.setFecha_final(new SimpleDateFormat("dd/MM/yyyy").format(fechaRegreso.getDate()));
					vacaciones.setDias_trabajados_de_la_ultima_semana(Integer.valueOf(txtDiasPendientesDePago.getText().trim()));
					vacaciones.setDias_de_vacaciones(Integer.valueOf(lblDiasVac.getText().trim()));
					vacaciones.setDias_de_descanso_pagados(Integer.valueOf(lblDescansos_Pagados.getText().trim()));
					
					vacaciones.setMensualidad(Float.valueOf(txtMensualidad.getText().trim().equals("")?"0":txtMensualidad.getText().trim()));
	//------------------------------------------------------------------------------------------------------------------------				
					vacaciones.setSd_nc(Float.valueOf(txtCuotaDiaria.getText()));
					vacaciones.setSueldo_nc(Float.valueOf(txtSueldo.getText()));
					vacaciones.setVacaciones_nc(Float.valueOf(txtVacaciones.getText().trim()));
					vacaciones.setDescansos_pagados_nc(Float.valueOf(txtDescansoPagado.getText().trim()));
					vacaciones.setPrima_vacacional_nc(Float.valueOf(txtPrimaVacacional.getText().trim()));
					vacaciones.setTotal_percepciones_nc(Float.valueOf(txtPercepciones.getText().trim()));
	//------------------------------------------------------------------------------------------------------------------------
					vacaciones.setSd_c(Float.valueOf(txtCuotaDiariaC.getText()));
					vacaciones.setSDI_c(Float.valueOf(txtSDI.getText()));
					vacaciones.setSueldo_c(Float.valueOf(txtSueldoC.getText()));
					vacaciones.setVacaciones_c(Float.valueOf(txtVacacionesC.getText().trim()));
					vacaciones.setDescansos_pagados_c(Float.valueOf(txtDescansoPagadoC.getText().trim()));
					vacaciones.setPrima_vacacional_c(Float.valueOf(txtPrimaVacacionalC.getText().trim()));
					vacaciones.setTotal_percepciones_c(Float.valueOf(txtPercepcionesC.getText().trim()));
	//------------------------------------------------------------------------------------------------------------------------				
					
					vacaciones.setPrima_dominical_c(Float.valueOf(txtPrimaDominical.getText().trim()));
					vacaciones.setBono_despensa_c(Float.valueOf(txtBonoDespensa.getText().trim()));
					vacaciones.setPremio_por_puntualidad_c(Float.valueOf(txtPremioPorPuntualidad.getText().trim()));
					vacaciones.setPremio_por_asistencia_c(Float.valueOf(txtPremioPorAsitencia.getText().trim()));
					vacaciones.setSubsidio_c(Float.valueOf(txtSubsidio.getText().trim()));
					vacaciones.setImss_c(Float.valueOf(txtImss.getText().trim()));
					vacaciones.setIspt_c(Float.valueOf(txtIspt.getText().trim()));
					
					vacaciones.setPrestamo_nc(Float.valueOf(txtPrestamo.getText().trim()));
					vacaciones.setInfonavit_nc(Float.valueOf(txtInfonavit.getText().trim()));
					vacaciones.setInfonacot_nc(Float.valueOf(txtInfonacot.getText().trim()));
					vacaciones.setPension_alimenticia_nc(Float.valueOf(txtPension.getText().trim()));
					vacaciones.setOtras_deducciones(Float.valueOf(txtOtrasDeducciones.getText().trim()));
					vacaciones.setOtras_percepciones(Float.valueOf(txtOtrasPercepciones.getText().trim()));
					
					vacaciones.setCheque_nc(Float.valueOf(txtCheques.getText().trim()));
					vacaciones.setEfectivo_nc(Float.valueOf(txtEfectivo.getText().trim()));
					
					vacaciones.setObservacion_vacaciones(txaObservacion.getText().trim().toUpperCase());
					
					if(movimiento.equals("GUARDAR")){
						if(vacaciones.guardar_vacaciones_calculadas(movimiento)){
							JOptionPane.showMessageDialog(null, "El Registro Se Guardo Correctamente","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
						}else{
							JOptionPane.showMessageDialog(null, "Ocurrió un problema al almacenar las vacaciones", "Error", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-icono-eliminar5252-64.png"));
						}
					}else{
						if(JOptionPane.showConfirmDialog(null, "El registro existe, ¿desea actualizarlo?") == 0){
							if(vacaciones.guardar_vacaciones_calculadas(movimiento)){
								JOptionPane.showMessageDialog(null, "El Registro Se Actualizo Correctamente","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
							}else{
								JOptionPane.showMessageDialog(null, "Ocurrió un problema al actualizar las vacaciones", "Error", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-icono-eliminar5252-64.png"));
							}
						}
					}
				}else{
					JOptionPane.showMessageDialog(null, "Los Siguientes Campos Son Requeridos:\n"+validacionDeCampos, "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
   					return;
				}
				
			}
  		};
  		
//  		ActionListener opGuardar2 = new ActionListener() {
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
  			String fechaInNull = fechaInicioVacaciones.getDate()+"";
  			String fechaFinNull = fechaRegreso.getDate()+"";
  			
  			if(fechaInNull.equals("null"))error+= "Fecha Inicial\n";	
  			if(fechaFinNull.equals("null"))error += "Fecha Final\n";
  			
  			if(txtVacaciones.getText().equals("")) 	error+= "Vacaciones\n";
  			if(txtPrimaVacacional.getText().equals("")) 		error+= "Prima Vacacional\n";
  			if(txtInfonavit.getText().equals(""))	error+= "Ifonavit\n";
  			if(txtSueldo.getText().equals(""))error+= "Sueldo Semana\n";
//  			if(txtCorteCaja.getText().equals(""))	error+= "Corte De Caja\n";
//  			if(txtFSodas.getText().equals("")) 		error+= "Fuente De Sodas\n";
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
//  	        txtSalarioDiarioIn.setText("");
  	        txtGrupoDeVacaciones.setText("");
  	        txtProximasVacaciones.setText("");
  	        
  			Icon iconoFoto = new ImageIcon(tmpIconAuxFoto.getImage().getScaledInstance(btnFoto.getWidth(), btnFoto.getHeight(), Image.SCALE_DEFAULT));
  	        btnFoto.setIcon(iconoFoto);
  			
  	        fechaRegreso.setDate(null);
	          
	        txtCuotaDiaria.setText("");
	        txtSDI.setText("");
	         
	        txtVacaciones.setText("");
	        txtDescansoPagado.setText("");
	  		txtPrimaVacacional.setText("");
	  		txtPercepciones.setText("");
	  		txtInfonavit.setText("");
	  		txtInfonacot.setText("");
	  		txtSueldo.setText("");
	  		txtPrestamo.setText("");
	  		txtPension.setText("");
//	        txtFSodas.setText("");
//	        txtCorteCaja.setText("");
	        txtCheques.setText("");
	        txtEfectivo.setText("");
	  		
	        txtCuotaDiariaC.setText("");
	          
	  		txtVacacionesC.setText("");
	  		txtDescansoPagadoC.setText("");
	  		txtPrimaVacacionalC.setText("");
	  		txtPercepcionesC.setText("");
//	  		txtInfonavitC.setText("");
//	  		txtInfonacotC.setText("");
	  		txtSueldoC.setText("");
//	  		txtPrestamoC.setText("");
//	  		txtPensionC.setText("");
	  		
	  		fechaInicioVacaciones.setDate(null);
	  		txtDiasPendientesDePago.setText("");
	  		txtOtrasDeducciones.setText("");
	  		txtOtrasPercepciones.setText("");
	  		txtPrimaDominical.setText("");
	  		txtBonoDespensa.setText("");
	  		txtPremioPorPuntualidad.setText("");
	  		txtPremioPorAsitencia.setText("");
	  		txtSubsidio.setText("");
	  		txtImss.setText("");
	  		txtIspt.setText("");
            
            lblDiasVac.setText("0");
            lblDescansos_Pagados.setText("0");
            
            txaObservacion.setText("");
            
            tabla_model.setRowCount(0);
  		}
  		
 //Filtro Empleado para buscar y editar sus ultimas vacacioenes----------------------------------------------------------------------------
  		public class Filtro_Ultimas_Vacaciones_De_Empleado extends JDialog{
  			
  			Container cont = getContentPane();
  			JLayeredPane campo = new JLayeredPane();
  			
  			Connexion con = new Connexion();
  			
  			DefaultTableModel model = new DefaultTableModel(0,4){
  				public boolean isCellEditable(int fila, int columna){
//  					if(columna < 0)
//  						return true;
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
  			
  			public void agregar(final JTable tbl) {
  		        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
  			        public void mouseClicked(MouseEvent e) {
  			        	if(e.getClickCount() == 2){
  			        		
  			        		int fila = tabla.getSelectedRow();
  			    			String folio =  tabla.getValueAt(fila, 0).toString().trim();
  			    			dispose();
  			    			
  			    			llenarDatosEmpleado(Integer.valueOf(folio));
  			    			fechaInicioVacaciones.transferFocus();
  			        	}
  			        }
  		        });
  		        
  		    }
  			
//  			public void llenarDatosEmpleado(int folio){
//  				
//				try {
//						fechaInicioVacaciones.setEnabled(true);
//	  				 
//  						Obj_Alimentacion_De_Vacaciones vacaciones = new Obj_Alimentacion_De_Vacaciones().buscar_vacaciones_guardadas(folio);
//  						tabla_model.setRowCount(0);
//  						Object [][] lista_tabla = new BuscarTablasModel().tabla_de_vacaciones_disfrutadas(folio);
//	  	                for(Object[] reg: lista_tabla){
//	  	                        tabla_model.addRow(reg);
//	  	                }
//		  	                
//		  				txtFolioVacaciones.setText(vacaciones.getFolio_vacaciones()+"");
//		  				txtFolioEmpleado.setText(vacaciones.getFolio_empleado()+"");
//		  				txtEmpleado.setText(vacaciones.getEmpleado());
//		  				txtEstablecimiento.setText(vacaciones.getEstablecimiento());
//		  				txtPuesto.setText(vacaciones.getPuesto());
//		  				txtFechaIngreso.setText(vacaciones.getFecha_ingreso());
//		  				txtFechaIngresoIMSS.setText(vacaciones.getFecha_ingreso_imss());
////		  				txtSalarioDiarioIn.setText(vacaciones.getSalario_diario_integrado()+"");
//		  				txtGrupoDeVacaciones.setText(vacaciones.getGrupo_vacacional());
//		  				txtProximasVacaciones.setText(vacaciones.getProximas_vacaciones()+"");
//		  				
//					Date dateIn = new SimpleDateFormat("dd/MM/yyyy").parse(vacaciones.getFecha_inicio());
//					fechaInicioVacaciones.setDate(dateIn);
//					
//	  				Date dateFin = new SimpleDateFormat("dd/MM/yyyy").parse(vacaciones.getFecha_regresa());
//	  				fechaRegreso.setDate(dateFin);
//	  				
//	  				ImageIcon tmpIconDefault = new ImageIcon(System.getProperty("user.dir")+"/tmp/tmp.jpg");
//	  		        Icon iconoDefault = new ImageIcon(tmpIconDefault.getImage().getScaledInstance(btnFoto.getWidth(), btnFoto.getHeight(), Image.SCALE_DEFAULT));
//	  		        btnFoto.setIcon(iconoDefault);
//	  				
//				} catch (ParseException e) {
//					e.printStackTrace();
//				}
//  				
//  		         btnBuscar.setEnabled(false);
//  		         btnNuevo.setEnabled(false);
//  			}
  			
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
  		    	
  		    	tb.getColumnModel().getColumn(0 ).setMaxWidth(x-60);   
  		    	tb.getColumnModel().getColumn(0 ).setMinWidth(x-60);   
  		    	tb.getColumnModel().getColumn(1 ).setMaxWidth(x*3-30);
  		    	tb.getColumnModel().getColumn(1 ).setMinWidth(x*3-30);
  		    	tb.getColumnModel().getColumn(2 ).setMaxWidth(x*2-50); 
  		    	tb.getColumnModel().getColumn(2 ).setMinWidth(x*2-50); 
  		    	tb.getColumnModel().getColumn(3 ).setMaxWidth(x); 
  		    	tb.getColumnModel().getColumn(3 ).setMinWidth(x); 
  		    	                                          
  			}
  			
  			private JScrollPane getPanelTabla()	{	
  				
  				tabla.getColumnModel().getColumn(0).setHeaderValue("Folio");
  				tabla.getColumnModel().getColumn(1).setHeaderValue("Empleado");
  				tabla.getColumnModel().getColumn(2).setHeaderValue("Establecimiento");
  				tabla.getColumnModel().getColumn(3).setHeaderValue("Status");
  				
  				Statement s;
  				ResultSet rs;
  				try {
  					s = con.conexion().createStatement();
  					rs = s.executeQuery("exec sp_select_ultimas_vacaciones_por_empleado" );
  					
  					while (rs.next())
  					{ 
  					   String [] fila = new String[4];
  					   fila[0] = rs.getString(1).trim();
  					   fila[1] = rs.getString(2).trim();
  					   fila[2] = rs.getString(3).trim();
  					   fila[3] = rs.getString(4).trim();
  					   
  					   model.addRow(fila); 
  					}	
  				} catch (SQLException e1) {
  					e1.printStackTrace();
  				}
  				 JScrollPane scrol = new JScrollPane(tabla);
  				   
  			    return scrol; 
  			}
  			
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
		
		JTextField txtFiltroColaboradores  = new Componentes().textfiltro(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<", 300, "String",tabla,3);
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Filtro_Empleado_Para_Vacaciones_Nuevas(){
			this.setModal(true);
			this.setTitle("Filtro Empleados");
		
			trsfiltro = new TableRowSorter(model); 
			tabla.setRowSorter(trsfiltro);  
			
			campo.add(txtFiltroColaboradores).setBounds(10,30,570,20);
			campo.add(getPanelTabla()).setBounds(10,50,570,410);
			
			cont.add(campo);
			agregar(tabla);
			
			render_filtro(tabla);
			
			this.setSize(600,530);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			
			tabla.addKeyListener(seleccionEmpleadoconteclado);
			
		}
		public void agregar(final JTable tbl) {
	        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
		        public void mouseClicked(MouseEvent e) {
		        	if(e.getClickCount() == 2){
		        		
		        		int fila = tabla.getSelectedRow();
		    			String folio =  tabla.getValueAt(fila, 0).toString().trim();
		    			dispose();
		    			
		    			llenarDatosEmpleado(Integer.valueOf(folio));
		    			fechaInicioVacaciones.transferFocus();
		        	}
		        }
	        });
	        
	    }
//		public void llenarDatosEmpleado(int folio){
//			
//				fechaInicioVacaciones.setEnabled(true);
//				 btnDeshacer.setEnabled(true);
//				 activar_descactivar_campos(true);
// 				 
//	  	        tabla_model.setRowCount(0);
//	  	        Object [][] lista_tabla = new BuscarTablasModel().tabla_de_vacaciones_disfrutadas(folio);
//                for(Object[] reg: lista_tabla){
//                        tabla_model.addRow(reg);
//                }
//	  	                
//                Obj_Alimentacion_De_Vacaciones vacaciones = new Obj_Alimentacion_De_Vacaciones().buscar(folio);
//                
//	  				txtFolioVacaciones.setText(vacaciones.getFolio_vacaciones()+"");
//	  				txtFolioEmpleado.setText(vacaciones.getFolio_empleado()+"");
//	  				txtEmpleado.setText(vacaciones.getEmpleado());
//	  				txtEstablecimiento.setText(vacaciones.getEstablecimiento());
//	  				txtPuesto.setText(vacaciones.getPuesto());
//	  				txtFechaIngreso.setText(vacaciones.getFecha_ingreso());
//	  				txtFechaIngresoIMSS.setText(vacaciones.getFecha_ingreso_imss());
//	  				txtGrupoDeVacaciones.setText(vacaciones.getGrupo_vacacional());
//	  				txtProximasVacaciones.setText(vacaciones.getProximas_vacaciones()+"");
//	  				
//	  				txtMensualidadDia.setText("0");
//	  				
//	  				btnFoto.setIcon(crearIcon(vacaciones.getImagen()));
//	  				
//	  				lblDiasVac.setText(vacaciones.getDias_de_vacaciones()+"");
//	  				lblDescansos_Pagados.setText(vacaciones.getDias_de_descanso_pagados()+"");
//					
//	  				sd_nc = vacaciones.getSd_nc();
//	  				txtCuotaDiaria.setText(sd_nc+"");
//	  				txtSueldo.setText(vacaciones.getSueldo_nc()+"");
//	  				txtVacaciones.setText(vacaciones.getVacaciones_nc()+"");
//	  				txtDescansoPagado.setText(vacaciones.getDescansos_pagados_nc()+"");
//	  				txtPrimaVacacional.setText(vacaciones.getPrima_vacacional_nc()+"");
//	  				
//	  				txtPercepciones.setText(vacaciones.getTotal_percepciones_nc()+"");
//	  				
//	  				txtPrestamo.setText(vacaciones.getPrestamo_nc()+"");
//	  				txtPension.setText(vacaciones.getPension_alimenticia_nc()+"");
//	  				txtInfonavit.setText(vacaciones.getInfonavit_nc()+"");
//	  				txtInfonacot.setText(vacaciones.getInfonacot_nc()+"");
//	  				
//	  				txtCuotaDiariaC.setText(vacaciones.getSd_c()+"");
//	  				txtSDI.setText(vacaciones.getSDI_c()+"");
//	  				
//	  				txtSueldoC.setText(vacaciones.getSueldo_c()+"");
//	  				txtVacacionesC.setText(vacaciones.getVacaciones_c()+"");
//	  				txtDescansoPagadoC.setText(vacaciones.getDescansos_pagados_c()+"");
//	  				txtPrimaVacacionalC.setText(vacaciones.getPrima_vacacional_c()+"");
//	  				txtPercepcionesC.setText(vacaciones.getTotal_percepciones_c()+"");
//	  				
//	  				txtCheques.setText(vacaciones.getCheque_nc()+"");
//	  				txtEfectivo.setText(vacaciones.getEfectivo_nc()+"");
//	  				
//	  				txtDiasPendientesDePago.setText("");
//	  				txtPrimaDominical.setText("");
//	  				txtBonoDespensa.setText("");
//	  				txtPremioPorPuntualidad.setText("");
//	  				txtPremioPorAsitencia.setText("");
//	  				txtSubsidio.setText("");
//	  				txtOtrasDeducciones.setText("");
//	  				txtOtrasPercepciones.setText("");
//	  				
//	  				calcular();
//	  				
//	         btnBuscar.setEnabled(false);
//	         btnNuevo.setEnabled(false);
//		}
		
//		public Icon crearIcon(byte[] bs){
//			ImageIcon tmpIconDefault= new ImageIcon(bs);
//			return new ImageIcon(tmpIconDefault.getImage().getScaledInstance(btnFoto.getWidth(), btnFoto.getHeight(), Image.SCALE_DEFAULT));
//		}
		
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
				
			Statement s;
			ResultSet rs;
			try {
				s = con.conexion().createStatement();
				rs = s.executeQuery("exec filtro_nuevas_vacaciones" );
				
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
	
	public void llenarDatosEmpleado(int folio){
		
		fechaInicioVacaciones.setEnabled(true);
		btnDeshacer.setEnabled(true);
		activar_descactivar_campos(true);
			 
        tabla_model.setRowCount(0);
        Object [][] lista_tabla = new BuscarTablasModel().tabla_de_vacaciones_disfrutadas(folio);
        for(Object[] reg: lista_tabla){
                tabla_model.addRow(reg);
        }
	                
        Obj_Alimentacion_De_Vacaciones vac = new Obj_Alimentacion_De_Vacaciones().buscar(folio,movimiento);
        
				txtFolioVacaciones.setText(vac.getFolio_vacaciones()+"");
				txtFolioEmpleado.setText(vac.getFolio_empleado()+"");
				txtEmpleado.setText(vac.getEmpleado());
				txtEstablecimiento.setText(vac.getEstablecimiento());
				txtPuesto.setText(vac.getPuesto());
				txtFechaIngreso.setText(vac.getFecha_ingreso());
				txtFechaIngresoIMSS.setText(vac.getFecha_ingreso_imss());
				txtGrupoDeVacaciones.setText(vac.getGrupo_vacacional());
				txtProximasVacaciones.setText(vac.getProximas_vacaciones()+"");
				
				txtMensualidadDia.setText("0");
				txtSaldoRestanteDePrestamo.setText(vac.getSaldo_restante_de_prestamo()+"");
				
				btnFoto.setIcon(crearIcon(vac.getImagen()));
				
				lblDiasVac.setText(vac.getDias_de_vacaciones()+"");
				lblDescansos_Pagados.setText(vac.getDias_de_descanso_pagados()+"");
			
				sd_nc = vac.getSd_nc();
				txtCuotaDiaria.setText(sd_nc+"");
				txtSueldo.setText(vac.getSueldo_nc()+"");
				txtVacaciones.setText(vac.getVacaciones_nc()+"");
				txtDescansoPagado.setText(vac.getDescansos_pagados_nc()+"");
				txtPrimaVacacional.setText(vac.getPrima_vacacional_nc()+"");
				
				txtPercepciones.setText(vac.getTotal_percepciones_nc()+"");
				
				prestamoInicial=vac.getPrestamo_nc();
				txtPrestamo.setText(prestamoInicial+"");
				txtPension.setText(vac.getPension_alimenticia_nc()+"");
				txtInfonavit.setText(vac.getInfonavit_nc()+"");
				txtInfonacot.setText(vac.getInfonacot_nc()+"");
				
				txtCuotaDiariaC.setText(vac.getSd_c()+"");
				txtSDI.setText(vac.getSDI_c()+"");
				
				txtSueldoC.setText(vac.getSueldo_c()+"");
				txtVacacionesC.setText(vac.getVacaciones_c()+"");
				txtDescansoPagadoC.setText(vac.getDescansos_pagados_c()+"");
				txtPrimaVacacionalC.setText(vac.getPrima_vacacional_c()+"");
				txtPercepcionesC.setText(vac.getTotal_percepciones_c()+"");
				
				txtCheques.setText(vac.getCheque_nc()+"");
				txtEfectivo.setText(vac.getEfectivo_nc()+"");
				
				txtDiasPendientesDePago.setText("");
				txtPrimaDominical.setText("");
				txtBonoDespensa.setText("");
				txtPremioPorPuntualidad.setText("");
				txtPremioPorAsitencia.setText("");
				txtSubsidio.setText("");
				txtOtrasDeducciones.setText("");
				txtOtrasPercepciones.setText("");
				
				if(movimiento.equals("MODIFICAR")){
					
					Date fecha_i;
					Date fecha_r;
					try {
						fecha_i = new SimpleDateFormat("dd/MM/yyyy").parse(vac.getFecha_inicio());
						fecha_r = new SimpleDateFormat("dd/MM/yyyy").parse(vac.getFecha_regresa());
						fechaInicioVacaciones.setDate(fecha_i);
						fechaRegreso.setDate(fecha_r);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					
					txtDiasPendientesDePago.setText(vac.getDias_trabajados_de_la_ultima_semana()+"");
					txtMensualidad.setText(vac.getMensualidad()+"");
					txtOtrasDeducciones.setText(vac.getOtras_deducciones()+"");
					txtOtrasPercepciones.setText(vac.getOtras_percepciones()+"");
					
					txtPrimaDominical.setText(vac.getPrima_dominical_c()+"");
					txtBonoDespensa.setText(vac.getBono_despensa_c()+"");
					txtPremioPorPuntualidad.setText(vac.getPremio_por_puntualidad_c()+"");
					txtPremioPorAsitencia.setText(vac.getPremio_por_asistencia_c()+"");
					txtSubsidio.setText(vac.getSubsidio_c()+"");
					txtImss.setText(vac.getImss_c()+"");
					txtIspt.setText(vac.getIspt_c()+"");
					
					txaObservacion.setText(vac.getObservacion_vacaciones());
					
					
				}
				calcular();
				
	     btnBuscar.setEnabled(false);
	     btnNuevo.setEnabled(false);
	}
	
	public Icon crearIcon(byte[] bs){
		ImageIcon tmpIconDefault= new ImageIcon(bs);
		return new ImageIcon(tmpIconDefault.getImage().getScaledInstance(btnFoto.getWidth(), btnFoto.getHeight(), Image.SCALE_DEFAULT));
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
