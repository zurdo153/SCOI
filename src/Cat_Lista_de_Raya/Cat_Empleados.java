package Cat_Lista_de_Raya;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Event;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.media.Buffer;
import javax.media.CannotRealizeException;
import javax.media.CaptureDeviceInfo;
import javax.media.Format;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.NoPlayerException;
import javax.media.Player;
import javax.media.cdm.CaptureDeviceManager;
import javax.media.control.FrameGrabbingControl;
import javax.media.format.RGBFormat;
import javax.media.format.VideoFormat;
import javax.media.format.YUVFormat;
import javax.media.util.BufferToImage;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;
import javax.swing.LayoutStyle;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

import Cat_Checador.Cat_Horarios;
import Cat_Reportes.Cat_Reporte_De_Ausentismo_En_Lista_De_Raya;
import Cat_Reportes.Cat_Reportes_De_Altas_y_Bajas_En_Un_Rango_De_Fechas;
import Cat_Reportes.Cat_Reportes_De_Cortes_De_Lista_De_Raya_Actual;
//import Cat_Reportes.Cat_Reporte_De_Altas_y_Bajas_En_Un_Rango_De_Fechas;
import Cat_Reportes.Cat_Reporte_De_Cumpleanios_Del_Mes;
import Cat_Reportes.Cat_Personal_Con_Horario;
import Cat_Reportes.Cat_Reporte_De_Asistencia_Por_Empleado;
import Cat_Reportes.Cat_Reportes_De_Contratacion_Por_Empleado;
import Cat_Reportes.Cat_Reportes_De_Horarios;
import Cat_Reportes.Cat_Reportes_De_Informacion_De_Movimientos_De_Colaboradores;
import Conexiones_SQL.ActualizarSQL;
import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Connexion;
import Conexiones_SQL.Generacion_Reportes;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Checador.Obj_Horario_Empleado;
import Obj_Lista_de_Raya.Obj_Autorizacion_Auditoria;
import Obj_Lista_de_Raya.Obj_Autorizacion_Finanzas;
import Obj_Lista_de_Raya.Obj_Bono_Complemento_Sueldo;
import Obj_Lista_de_Raya.Obj_Bono_Puntualidad_Y_Asistencia;
import Obj_Lista_de_Raya.Obj_Departamento;
import Obj_Lista_de_Raya.Obj_Empleados;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Lista_de_Raya.Obj_Puestos;
import Obj_Lista_de_Raya.Obj_Rango_De_Prestamos;
import Obj_Lista_de_Raya.Obj_Sueldos;
import Obj_Lista_de_Raya.Obj_Tipo_De_Bancos;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_Filtro_Dinamico;
import Obj_Renders.tablaRenderer;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

import com.toedter.calendar.JDateChooser;

@SuppressWarnings({ "serial", "unchecked" })
public class Cat_Empleados extends JFrame{
	
	Runtime R = Runtime.getRuntime();
	
	Container cont = getContentPane();
	
	JTabbedPane pestanas = new JTabbedPane();
	
	JLayeredPane panel = new JLayeredPane();
	JLayeredPane panelReporte = new JLayeredPane();
	
	JLabel lblDatosPersonales = new JLabel();
	JLabel lblLaboral = new JLabel();
	JLabel lblPercepciones = new JLabel();
	JLabel lblFolioHorario1 = new JLabel("");
	JLabel lblFolioHorario2 = new JLabel("");
	JLabel lblFolioHorario3 = new JLabel("");
	
	JPasswordField txtChecador = new Componentes().textPassword(new JPasswordField(), "Contrase�a del Checador", 100);
	
	JTextField txtFolioEmpleado = new Componentes().text( new JCTextField(), "Folio Colaborador", 9, "Int");
	JTextField txtNombre = new Componentes().text( new JTextField(), "Nombre de Empleado", 70, "String");
	JTextField txtApPaterno = new Componentes().text( new JTextField(), "Apellido Paterno", 20, "String");
	JTextField txtApMaterno = new Componentes().text( new JTextField(), "Apellido Materno", 20, "String");
	JTextField txtFechaActualizacion = new JTextField(new SimpleDateFormat("dd/MM/yyyy").format((new Date())));
	
	JTextField txtTelefono_Familiar = new Componentes().text( new JTextField(), "Tel�fono Familiar", 10, "Int");
	JTextField txtTelefono_Propio = new Componentes().text( new JTextField(), "Tel�fono Propio", 10, "Int");
	JTextField txtTelefono_Cuadrante = new JTextField();  
	JTextField txtPoblacion = new Componentes().text( new JTextField(), "Poblaci�n", 20, "String");
	JTextField txtRFC = new Componentes().text(new JTextField(), "RFC", 25, "String");
	JTextField txtCurp = new Componentes().text(new JTextField(), "Curp", 25, "String");  
	JTextField txtBaja = new JTextField();
	JTextField txtColonia = new Componentes().text( new JTextField(), "Colonia", 30, "String");
	JTextField txtCalle = new Componentes().text( new JTextField(), "Calle", 30, "String");
	
	JTextField txtDescanso               = new Componentes().text(new JCTextField(),"Dia De Descanso", 100, "String");
	JTextField txtDobla                  = new Componentes().text(new JCTextField(),"Dia Dobla", 100, "String");
	JTextField txtFechaUltimasVacaciones  = new Componentes().text(new JCTextField(),"Ultimas Vaciones", 100, "String");
	JTextField txtFechaIncapacidad = new JTextField();
	
	JTextField txtImss                   = new Componentes().text(new JCTextField(),"N�mero de Seguro Social", 11, "Int");
	JTextField txtNumeroInfonavit        = new Componentes().text(new JCTextField(), "N�mero de Infonavit", 15, "Int");
	JTextField txtHorario                = new Componentes().text(new JCTextField(), "Horario Principal Asignado",300, "String");
	JTextField txtHorario2               = new Componentes().text(new JCTextField(), "Horario Secundario Asignado",300, "String");
	JTextField txtHorario3               = new Componentes().text(new JCTextField(), "Horario Terciario Asignado",300, "String");
	
	JTextField txtSalarioDiario          = new Componentes().text(new JCTextField(), "Salario Diario"          , 15, "Double");
	JTextField txtSalarioDiarioIntegrado = new Componentes().text(new JCTextField(), "Salario Diario Integrado", 15, "Double");
	
	JTextField txtInfonavit              = new Componentes().text(new JCTextField(), "Descuento a Infonavit"   , 15, "Double");
	JTextField txtDInfonacot             = new Componentes().text(new JCTextField(), "Infonacot"               , 15, "Double");
	JTextField txtPensionAli             = new Componentes().text(new JCTextField(), "Pension Alimenticia"     , 15, "Double");
	JTextField txtTarjetaNomina          = new Componentes().text(new JCTextField(), "Tarjeta de N�mina"       , 19, "Int"   );
	JTextField txtultimousuariomod       = new Componentes().text(new JCTextField(), "�ltimo Usuario Actualiz�",300, "String");
	JTextField txtFormaDePago            = new Componentes().text(new JCTextField(), "Forma de Pago"           , 15, "String");
	
	JToggleButton btnTrueFoto = new JToggleButton("Para actualizar la foto Presiona aqu� !!!");
	
	String Departamentos[] = new Obj_Departamento().Combo_Departamento();
	@SuppressWarnings("rawtypes")
	JComboBox cmbDepartamento = new JComboBox(Departamentos);  
	
	String establecimiento[] = new Obj_Establecimiento().Combo_Establecimiento();
	@SuppressWarnings("rawtypes")
	JComboBox cmbEstablecimiento = new JComboBox(establecimiento);
	
	String puesto[] = new Obj_Puestos().Combo_Puesto();
	@SuppressWarnings("rawtypes")
	JComboBox cmbPuesto = new JComboBox(puesto);
	
	String sueldo[] = new Obj_Sueldos().Combo_Sueldo();
	@SuppressWarnings("rawtypes")
	JComboBox cmbSueldo = new JComboBox(sueldo);
	
	String bono[] = new Obj_Bono_Complemento_Sueldo().Combo_Bono();
	@SuppressWarnings("rawtypes")
	JComboBox cmbBono = new JComboBox(bono);
	
	String bonopuntualidad[] = new Obj_Bono_Puntualidad_Y_Asistencia().Combo_Bono();
	@SuppressWarnings("rawtypes")
	JComboBox cmbBonopuntualidad = new JComboBox(bonopuntualidad);
	
	@SuppressWarnings("rawtypes")
	JComboBox cmbBonoAsistencia = new JComboBox(bonopuntualidad);
	
	String rango_prestamo[] = new Obj_Rango_De_Prestamos().Combo_Prestamos();
	@SuppressWarnings("rawtypes")
	JComboBox cmbPrestamos = new JComboBox(rango_prestamo);

	String TipoBanco[] = new Obj_Tipo_De_Bancos().Combo_Tipo_Banco_Empleado();
	@SuppressWarnings("rawtypes")
	JComboBox cmbTipoBancos = new JComboBox(TipoBanco);
	
	JCheckBox chbFuente_Sodas = new JCheckBox("Fnt de Sodas");
	JCheckBox chbGafete = new JCheckBox("Gafete");
	
	String status[] = {"Vigente","Vacaciones","Incapacidad","Baja","No Contratable","Provicional Checador","Renuncia"};
	@SuppressWarnings("rawtypes")
	JComboBox cmbStatus = new JComboBox(status);
	
	String activo_inactivo[] = {"Activo (IMSS)","Inactivo (IMSS)"};
	@SuppressWarnings("rawtypes")
	JComboBox cmbActivo_Inactivo = new JComboBox(activo_inactivo);
	
	JCheckBox chb_cuadrante_parcial = new JCheckBox("Permite Cuadrante Parcial",false);
	
	JCButton btnBuscar   = new JCButton("Buscar","buscar.png","Azul");
	JCButton btnFiltro   = new JCButton("Filtro","Filter-List-icon16.png","Azul");
	JCButton btnNuevo    = new JCButton("Nuevo","Nuevo.png","Azul");
	JCButton btnEditar   = new JCButton("Editar","editara.png","Azul");
	JCButton btnSalir    = new JCButton("Salir","salir16.png","Azul");
	JCButton btnGuardar  = new JCButton("Guardar","Guardar.png","Azul");
	JCButton btnDeshacer = new JCButton("Deshacer","deshacer16.png","Azul");
	JCButton btnVerificar= new JCButton("Verificar Nombre Del Nuevo Colaborador","","AzulC");
	JButton btnHorario   = new JButton(".");
	JButton btnHorario2 = new JButton(".");
	JButton btnHorario3 = new JButton(".");
	JButton btnHorarioNew = new JButton("new");
	
	JButton btnFechaUltimasVacaciones = new JButton();
	JButton btnFechaIncapacidad = new JButton();
	JButton btnFiniquito  = new JButton();
	
	JButton btnFoto = new JButton();
	JButton btnStatus = new JButton();
	JButton btnExaminar = new JButton("Examinar");
	JButton btnCamara = new JButton(new ImageIcon("Iconos/camara_icon&16.png"));
	

	JCButton btnContratacion        = new JCButton("Contratacion","contrato-de-acuerdo-de-acuerdo-de-la-mano-encuentros-socio-icono-7428-16.png","AzulC");
	JCButton btnDocumentacion       = new JCButton("Documentaci�n","carpeta-de-correo-icono-4002-16.png","AzulC");
	JCButton btnEncuentaDeSalida    = new JCButton("Encuesta De Salida","Lista.png","AzulC");

	JCButton btnIncontratables      = new JCButton("No Contratables","tarjeta-de-informacion-del-usuario-icono-7370-16.png","Azul");

	JCButton btnLicencias           = new JCButton("Licencias","truck-icon.png","Azul");
	JCButton btnCumplea�os_del_Mes  = new JCButton("Cumplea�os","cookies-tarta-de-cumpleanos-icono-9840-16.png","Azul");
	JCButton btnAusentismo		    = new JCButton("Ausentismo","reloj.png","Azul");
	JCButton btn_Adeudo        		= new JCButton("Adeudos","detective-icono-5257-16.png","Azul");
	JCButton btnReporteSalida		= new JCButton("Encuesta De Salida","baja16.png","Azul");
	JCButton btnReporteRenuncia		= new JCButton("Renuncia","baja16.png","Azul");
	

	JCButton btnImp_Datos_Completos = new JCButton("Datos Colaborador","informacion-del-usuario-icono-8370-16.png","Azul");
	
	JCButton btnAltasBajas          = new JCButton("Rotacion","bajas_altas_16p.png","Azul");
	JCButton btnAsistencia_Empleado = new JCButton("Asistencia","archivo-icono-8809-16.png","Azul"); 
	JCButton btnCortes              = new JCButton("Cortes","dinero-icono-8797-16.png","Azul"); 
	JCButton btn_plantilla          = new JCButton("Plantilla","plan-icono-5073-16.png","Azul");
	JCButton btn_R_horarios         = new JCButton("Horarios","horas-de-reloj-de-alarma-icono-5601-16.png","Azul");
	
	JCButton btn_movimientos        = new JCButton("Percepciones y Deducciones","detective-icono-5257-16.png","Azul");


	
	JTextArea txaObservaciones = new Componentes().textArea(new JTextArea(), "Observaciones", 980);
	JScrollPane Observasiones = new JScrollPane(txaObservaciones);
	
	JDateChooser txtFechaNacimiento = new JDateChooser();
	JDateChooser txtIngreso = new JDateChooser();
	JDateChooser txtIngresoImss = new JDateChooser();
	JDateChooser txtVencimientoLicencia = new JDateChooser();

	String sexo[] = {"SELECCIONE UN GENERO","MASCULINO","FEMENINO"};
	@SuppressWarnings("rawtypes")
	JComboBox cmbSexo = new JComboBox(sexo);
	
	String estado_civil[] = new Obj_Empleados().Combo_Estado_Civil();
	@SuppressWarnings("rawtypes")
	JComboBox cmbEstadoCivil = new JComboBox(estado_civil);
	
	String tipo_de_sangre[] = new Obj_Empleados().Combo_Tipo_Sangre();
	@SuppressWarnings("rawtypes")
	JComboBox cmbTipoDeSangre = new JComboBox(tipo_de_sangre);
	
	String escolaridad[] = new Obj_Empleados().Combo_Escolaridad();
	@SuppressWarnings("rawtypes")
	JComboBox cmbEscolaridad = new JComboBox(escolaridad);
	
	String presencia_fisica[] = {"Selecciona Un Status","Aplica","No Aplica"};
	@SuppressWarnings("rawtypes")
	JComboBox cmbPresenciaFisica = new JComboBox(presencia_fisica);
	
	String contratacion[] = {"","30 DIAS","60 DIAS","90 DIAS","120 DIAS","150 DIAS","INDETERMINADO"};
	@SuppressWarnings("rawtypes")
	JComboBox cmbContratacion = new JComboBox(contratacion);
	
	 private ButtonGroup bgHorarios = new ButtonGroup();
	 private JRadioButton rbHorario = new JRadioButton("",true);
	 private JRadioButton rbHorario2 = new JRadioButton("",false);
	 private JRadioButton rbHorario3 = new JRadioButton("",false);
	 
	 String[] horarioRotativo = { "Sin Horario rotativo ", "2 Horarios", "3 Horarios" };
	 @SuppressWarnings("rawtypes")
	private JComboBox cmbHorarioRotativo = new JComboBox(horarioRotativo);
	 
	String statusChecador[] = {"NORMAL","LIBRE"};
	@SuppressWarnings("rawtypes")
	JComboBox cmbStatusChecador = new JComboBox(statusChecador);
	
	//declaracion de Bordes
	Border blackline, etched, raisedbevel, loweredbevel, empty;
//	TitledBorder title4;
	
	int seleccion_de_asignacion_de_Horario1Horario2Horario3;
	
	public Cat_Empleados() {
		getContenedor();
	}
	
	public void getContenedor(){
		this.setSize(1024,748);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		pestanas.addTab("Empleados", panel);
		pestanas.addTab("Reportes", panelReporte);
		
//		pendientes en funcionalidad----------------------------------------------------
		txtFechaUltimasVacaciones.setEnabled(false);
		btnFechaUltimasVacaciones.setEnabled(false);
		txtFechaIncapacidad.setEnabled(false);
		btnFechaIncapacidad.setEnabled(false);
		btnFiniquito.setEnabled(false);
//		------------------------------------------------------------------------------
//		efectos de bordes
		blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
//		etched = BorderFactory.createEtchedBorder();
//		raisedbevel = BorderFactory.createRaisedBevelBorder();
//		loweredbevel = BorderFactory.createLoweredBevelBorder();
//		empty = BorderFactory.createEmptyBorder();
		
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/de-configuracion-de-usuario-icono-7374-32.png"));
		this.setTitle("Alta de Empleados");
		
		this.panel.setBorder(BorderFactory.createTitledBorder(blackline, "Datos del Colaborador"));
		this.panelReporte.setBorder(BorderFactory.createTitledBorder(blackline, "Reportes de Colaboradores"));
		
//		asignacion de bordes
		this.lblDatosPersonales.setBorder(BorderFactory.createTitledBorder(blackline,"Datos Personales"));
		this.btnFoto.setBorder(blackline);
		this.lblLaboral.setBorder(BorderFactory.createTitledBorder(blackline, "Laboral"));
		this.lblPercepciones.setBorder(BorderFactory.createTitledBorder(blackline,"Percepciones y Deducciones"));
		
		this.btnVerificar.setToolTipText("Verificar Nombre");
		
		this.btnHorarioNew.setToolTipText("Generar Horario");
		this.btnHorario.setToolTipText("Asignar Horario");
		this.btnHorario2.setToolTipText("Asignar Segundo Horario");
		this.btnHorario3.setToolTipText("Asignar Tercer Horario");
		
		this.txtFechaUltimasVacaciones.setToolTipText("Fecha de vacaciones");
		this.txtFechaIncapacidad.setToolTipText("Fecha de incapacidad");
		
		this.btnFechaUltimasVacaciones.setToolTipText("Alimentacion de vacaciones");
		this.btnFechaIncapacidad.setToolTipText("Alimentacion de incapacidad");
			
		this.txaObservaciones.setBorder(BorderFactory.createTitledBorder(blackline));
		
		this.bgHorarios.add(rbHorario);
		this.bgHorarios.add(rbHorario2);
		this.bgHorarios.add(rbHorario3);
		
		this.txtFechaNacimiento.setIcon(new ImageIcon("Iconos/calendar_icon&16.png"));
		this.txtIngreso.setIcon(new ImageIcon("Iconos/calendar_icon&16.png"));
		this.txtIngresoImss.setIcon(new ImageIcon("Iconos/calendar_icon&16.png"));
		this.txtVencimientoLicencia.setIcon(new ImageIcon("Iconos/calendar_icon&16.png"));
		
		int x = 11, y=20, ancho=140,width=190,height=30,sep=202;
		
		panelReporte.add(btnAsistencia_Empleado).setBounds  (x	   ,y    ,width,height);
	    panelReporte.add(btn_R_horarios).setBounds          (x+=sep,y    ,width,height);
		panelReporte.add(btn_movimientos).setBounds  		(x+=sep,y    ,width,height);
		panelReporte.add(btn_Adeudo).setBounds           	(x+=sep,y    ,width,height);
		panelReporte.add(btnCortes).setBounds               (x+=sep,y    ,width,height);

		x=10;
		panelReporte.add(btnImp_Datos_Completos).setBounds  (x     ,y+=40,width,height);
		panelReporte.add(btn_plantilla).setBounds         	(x+=sep,y    ,width,height);
		panelReporte.add(btnIncontratables).setBounds       (x+=sep,y    ,width,height);
		panelReporte.add(btnLicencias).setBounds           	(x+=sep,y    ,width,height);
		panelReporte.add(btnCumplea�os_del_Mes).setBounds	(x+=sep,y    ,width,height);
		
		x=10;
		panelReporte.add(btnAusentismo).setBounds           (x     ,y+=40,width,height);
		panelReporte.add(btnReporteSalida).setBounds   		(x+=sep,y    ,width,height);
		panelReporte.add(btnReporteRenuncia).setBounds      (x+=sep,y    ,width,height);
		panelReporte.add(btnAltasBajas).setBounds       	(x+=sep,y    ,width,height);

		
		x = 10;y = 20;height=20;width=175;
		panel.add(btnContratacion).setBounds         (x	     ,y    ,width,height);
		panel.add(btnDocumentacion).setBounds        (x+=sep,y    ,width,height);
		panel.add(btnEncuentaDeSalida).setBounds     (x+=sep,y    ,width,height);
		
		x=20; y=y+=38;
//Datos personales ----------------------------------------------------------------------------------------------------------------------------		
		panel.add(lblDatosPersonales).setBounds(10,y-15,997,215);
		panel.add(new JLabel("Folio:")).setBounds(x,y,ancho,20);
		panel.add(txtFolioEmpleado).setBounds(x+ancho-40,y,ancho-15,20);
		
		panel.add(btnBuscar).setBounds(x+ancho+ancho-47,y,100,20);
		panel.add(btnFiltro).setBounds(x+ancho+ancho+55,y,100,20);
	
		panel.add(btnFoto).setBounds(x*2+ancho*5,y-5,ancho+55,160);
		
		panel.add(btnTrueFoto).setBounds(x*2+ancho*5-10, y+155,220,20);
		
		panel.add(btnExaminar).setBounds(x*2+ancho*5-10, y+175,80,20);		
		panel.add(new JLabel("320 x 240")).setBounds(x*2+ancho*5+76, y+175,60,20);
		panel.add(btnCamara).setBounds(x*2+ancho*5+130, y+175,80,20);
		
		panel.add(new JLabel("Clave Checador")).setBounds(x+450,y,ancho,20);
		panel.add(txtChecador).setBounds(x+(ancho*3)+110,y,ancho-15,20);
		
		panel.add(new JLabel("Nombre:")).setBounds(x,y+=25,ancho,20);
		panel.add(txtNombre).setBounds(x+ancho-40,y,ancho-15,20);
			panel.add(new JLabel("Ap. Paterno:")).setBounds(x+240,y,ancho,20);
			panel.add(txtApPaterno).setBounds(x+(ancho*2)+30,y,ancho-15,20);
				panel.add(new JLabel("Ap. Materno:")).setBounds(x+450,y,ancho,20);
				panel.add(txtApMaterno).setBounds(x+(ancho*3)+110,y,ancho-15,20);
		
		panel.add(btnVerificar).setBounds(x+ancho-40,y+=25, 335, 20);
		panel.add(new JLabel("F. Nacimiento:")).setBounds(x+450,y,ancho,20);
		panel.add(txtFechaNacimiento).setBounds(x+(ancho*3)+110,y,ancho-15,20);

		panel.add(new JLabel("Calle y N�:")).setBounds(x,y+=25,ancho,20);
		panel.add(txtCalle).setBounds(x+ancho-40,y,ancho-15,20);
		
		panel.add(new JLabel("Colonia:")).setBounds(x+240,y,ancho,20);
		panel.add(txtColonia).setBounds(x+(ancho*2)+30,y,ancho-15,20);
		
		panel.add(new JLabel("Poblacion:")).setBounds(x+450,y,ancho,20);
		panel.add(txtPoblacion).setBounds(x+(ancho*3)+110,y,ancho-15,20);

		panel.add(new JLabel("Tel. Familiar:")).setBounds(x,y+=25,ancho,20);
		panel.add(txtTelefono_Familiar).setBounds(x+ancho-40,y,ancho-15,20);
			panel.add(new JLabel("Tel. Propio:")).setBounds(x+240,y,ancho,20);
			panel.add(txtTelefono_Propio).setBounds(x+(ancho*2)+30,y,ancho-15,20);
				panel.add(new JLabel("Tel. Cuadrante:")).setBounds(x+450,y,ancho,20);
				panel.add(txtTelefono_Cuadrante).setBounds(x+(ancho*3)+110,y,ancho-15,20);
				
		panel.add(new JLabel("RFC:")).setBounds(x,y+=25,ancho,20);
		panel.add(txtRFC).setBounds(x+ancho-40,y,ancho-15,20);
		
		panel.add(new JLabel("Sexo: ")).setBounds(x+240,y,ancho,20);
		panel.add(cmbSexo).setBounds(x+(ancho*2)+30,y,ancho-15,20);
		
		panel.add(new JLabel("Estado Civil: ")).setBounds(x+450,y,ancho,20);
		panel.add(cmbEstadoCivil).setBounds(x+(ancho*3)+110,y,ancho-15,20);
		
		panel.add(new JLabel("Curp:")).setBounds(x,y+=25,ancho,20);
		panel.add(txtCurp).setBounds(x+ancho-40,y,ancho-15,20);
		
		panel.add(new JLabel("T. De Sangre: ")).setBounds(x+240,y,ancho,20);
		panel.add(cmbTipoDeSangre).setBounds(x+(ancho*2)+30,y,ancho-15,20);
		
		panel.add(new JLabel("Escolaridad: ")).setBounds(x+450,y,ancho,20);
		panel.add(cmbEscolaridad).setBounds(x+(ancho*3)+110,y,ancho-15,20);
		
//TODO Laboral ------------------------------------------------------------------------------------------------------------------------------------------		
		x=17 ;y=255;width=340;height=20;sep=120;
		panel.add(lblLaboral).setBounds                      (x-7         ,y     ,997     ,220    );
		panel.add(new JLabel("Horario:")).setBounds          (x           ,y+=15 ,width   ,height );
		panel.add(btnHorarioNew).setBounds                   (x+50        ,y     ,height  ,height );
		panel.add(btnHorario).setBounds                      (x+70        ,y     ,height  ,height );
		panel.add(lblFolioHorario1).setBounds                (x+sep-30    ,y     ,height  ,height );
		panel.add(txtHorario).setBounds                      (x+sep       ,y     ,width   ,height );
		panel.add(rbHorario).setBounds                       (x+460       ,y     ,height  ,height );
	    panel.add(new JLabel("Horario 2:")).setBounds        (x           ,y+=25 ,width   ,height );
		panel.add(btnHorario2).setBounds                     (x+sep-50    ,y     ,height  ,height );
		panel.add(lblFolioHorario2).setBounds                (x+sep-30    ,y     ,height  ,height );
		panel.add(txtHorario2).setBounds                     (x+sep       ,y     ,width   ,height );
		panel.add(rbHorario2).setBounds                      (x+460       ,y     ,height  ,height );
	    panel.add(new JLabel("Horario 3:")).setBounds        (x           ,y+=25 ,width   ,height );
		panel.add(btnHorario3).setBounds                     (x+70        ,y     ,height  ,height );
		panel.add(lblFolioHorario3).setBounds                (x+sep-30    ,y     ,height  ,height );
		panel.add(txtHorario3).setBounds                     (x+sep       ,y     ,width   ,height );
		panel.add(rbHorario3).setBounds                      (x+460       ,y     ,height  ,height );
		panel.add(new JLabel("Establecimiento:")).setBounds           (x           ,y+=25 ,width   ,height );
		panel.add(cmbEstablecimiento).setBounds                       (x+sep       ,y     ,width   ,height );
		panel.add(new JLabel("Departamento:")).setBounds     (x           ,y+=25 ,width   ,height );
		panel.add(cmbDepartamento).setBounds                 (x+sep       ,y     ,width   ,height );
		panel.add(new JLabel("Puesto:")).setBounds  (x           ,y+=25 ,width   ,height );
		panel.add(cmbPuesto).setBounds              (x+sep       ,y     ,width   ,height );
		panel.add(new JLabel("N� Seguro Social:")).setBounds (x           ,y+=25 ,width/2 ,height );
		panel.add(txtImss).setBounds                         (x+sep       ,y     ,width/2 ,height );
		panel.add(cmbActivo_Inactivo).setBounds              (x+290       ,y     ,width/2 ,height );
		panel.add(new JLabel("N� Infonavit:")).setBounds     (x           ,y+=25 ,width   ,height );
		panel.add(txtNumeroInfonavit).setBounds              (x+sep       ,y     ,width/2 ,height );
		panel.add(new JLabel("�lt.Vacaciones:")).setBounds   (x+290       ,y     ,width/2 ,height );
		panel.add(txtFechaUltimasVacaciones).setBounds       (x+365       ,y     ,95      ,height );
		panel.add(btnFechaUltimasVacaciones).setBounds       (x+462       ,y     ,height  ,height );
		
		x=515 ;y=270;width=150;sep=100;
		panel.add(new JLabel("Tipo de horario:")).setBounds  (x           ,y     ,width   ,height );
		panel.add(cmbHorarioRotativo).setBounds              (x+sep       ,y     ,width   ,height );
		panel.add(new JLabel("Descanso:")).setBounds         (x           ,y+=25 ,width   ,height );
		panel.add(txtDescanso).setBounds                     (x+sep       ,y     ,width   ,height );
		panel.add(new JLabel("D�a Dobla:")).setBounds        (x           ,y+=25 ,width   ,height );
		panel.add(txtDobla).setBounds                        (x+sep       ,y     ,width   ,height );
		panel.add(new JLabel("Vence La Licencia:")).setBounds(x           ,y+=25 ,width   ,height );
		panel.add(txtVencimientoLicencia).setBounds          (x+sep       ,y     ,width   ,height );
		panel.add(new JLabel("Fecha Ingreso:")).setBounds    (x           ,y+=25 ,width   ,height );
		panel.add(txtIngreso).setBounds                      (x+sep       ,y     ,width   ,height );
		panel.add(new JLabel("Fecha Baja:")).setBounds       (x           ,y+=25 ,width   ,height );
		panel.add(txtBaja).setBounds                         (x+sep       ,y     ,width-20,height );
		panel.add(btnFiniquito).setBounds                    (x+sep+130   ,y     ,height  ,height );
		panel.add(new JLabel("Ingreso IMSS:")).setBounds     (x           ,y+=25 ,width   ,height );
		panel.add(txtIngresoImss).setBounds                  (x+sep       ,y     ,width   ,height );
		panel.add(new JLabel("�ltima incapacidad:")).setBounds(x          ,y+=25 ,width   ,height );
		panel.add(txtFechaIncapacidad).setBounds             (x+sep       ,y     ,width-20,height );
		panel.add(btnFechaIncapacidad).setBounds             (x+sep+130   ,y     ,height  ,height );
		
		x=800;y=270;sep=45;
		
		panel.add(new JLabel("Checador:")).setBounds         (x        	  ,y     ,130   ,20     );
		panel.add(cmbStatusChecador).setBounds               (x+sep+15    ,y     ,130   ,20     );
		
		panel.add(btnStatus).setBounds                       (x+60        ,y+=22 ,130   ,125    );
		panel.add(new JLabel("Estatus:")).setBounds          (x           ,y+=128,130   ,height );
		panel.add(cmbStatus).setBounds                       (x+sep+15    ,y     ,130   ,height );
		panel.add(new JLabel("Contrato:")).setBounds         (x           ,y+=25 ,130   ,height );
		panel.add(cmbContratacion).setBounds                 (x+sep+15    ,y     ,130   ,height );
		
//TODO Percepciones y Deducciones ------------------------------------------------------------------------------------------------------------------------------------------		
		x=17 ;y=475;sep=87;
		panel.add(lblPercepciones).setBounds                   (x-7  ,y     ,700  ,190    );
		panel.add(new JLabel("Salario Diario:")).setBounds     (x    ,y+=15 ,width,height );
		panel.add(txtSalarioDiario).setBounds                  (x+sep,y     ,width,height );
		panel.add(new JLabel("Salario D.I:")).setBounds        (x    ,y+=25 ,width,height );
		panel.add(txtSalarioDiarioIntegrado).setBounds         (x+sep,y     ,width,height );
		panel.add(new JLabel("Sueldo:")).setBounds             (x    ,y+=25 ,width,height );
		panel.add(cmbSueldo).setBounds                         (x+sep,y     ,width,height );
		panel.add(new JLabel("B.Complemento:")).setBounds               (x    ,y+=25 ,width,height );
		panel.add(cmbBono).setBounds                           (x+sep,y     ,width,height );
		panel.add(new JLabel("B.Asistencia:")).setBounds         (x    ,y+=25 ,width,height );
		panel.add(cmbBonopuntualidad).setBounds                    (x+sep,y     ,width,height );
		panel.add(new JLabel("B.Puntualidad:")).setBounds        (x    ,y+=25 ,width,height );
		panel.add(cmbBonoAsistencia).setBounds                   (x+sep,y     ,width,height );
		panel.add(new JLabel("Presencia Fisica:")).setBounds   (x    ,y+=25 ,width,height );
		panel.add(cmbPresenciaFisica).setBounds                (x+sep,y     ,width,height );
		
		x=270; y=490; sep=97;
		panel.add(new JLabel("Infonavit:")).setBounds          (x    ,y     ,width,height );
		panel.add(txtInfonavit).setBounds                      (x+sep,y     ,width,height );
		panel.add(new JLabel("Infonacot:")).setBounds          (x    ,y+=25 ,width,height );
		panel.add(txtDInfonacot).setBounds                     (x+sep,y     ,width,height );
		panel.add(new JLabel("Pensi�n Alimenticia:")).setBounds(x    ,y+=25 ,width,height );
		panel.add(txtPensionAli).setBounds                     (x+sep,y     ,width,height );
		panel.add(new JLabel("Rango de Prestamo:")).setBounds  (x    ,y+=25 ,width,height );
		panel.add(cmbPrestamos).setBounds                      (x+sep,y     ,width,height );
		panel.add(new JLabel("Tipo de Bancos:")).setBounds     (x    ,y+=25 ,width,height );
		panel.add(cmbTipoBancos).setBounds                     (x+sep,y     ,width,height );
		panel.add(new JLabel("Tarjeta de N�mina:")).setBounds  (x    ,y+=25 ,width,height );
		panel.add(txtTarjetaNomina).setBounds                  (x+sep,y     ,width,height );
		panel.add(new JLabel("Forma de Pago:")).setBounds      (x    ,y+=25 ,width,height );
		panel.add(txtFormaDePago).setBounds                    (x+sep,y     ,width,height );		
		
		x=535; y=y-=150;sep=95;
		panel.add(chbGafete).setBounds                         (x    ,y     ,width,height );
		panel.add(chbFuente_Sodas).setBounds                   (x    ,y+=25 ,width,height );
		panel.add(new JLabel("�ltima Actualizaci�n:")).setBounds(x   ,y+=25 ,width,height );
		panel.add(txtFechaActualizacion).setBounds              (x   ,y+=25 ,width,height );
		panel.add(new JLabel("�ltimo Usuario Actualiz�:")).setBounds(x,y+=25,width,height );
		panel.add(txtultimousuariomod).setBounds               (x    ,y+=25 ,width,height );
		panel.add(chb_cuadrante_parcial).setBounds             (x    ,y+=25 ,width,height );
		panel.add(Observasiones).setBounds                     (x+180,y-158 ,290  ,183    );
		
		x=17 ;y=668;width=110;sep=218;
		panel.add(btnNuevo).setBounds                          (x     ,y    ,width,height );
		panel.add(btnEditar).setBounds                         (x+=sep,y    ,width,height );
		panel.add(btnGuardar).setBounds                        (x+=sep,y    ,width,height );
		panel.add(btnDeshacer).setBounds                       (x+=sep,y    ,width,height );
		panel.add(btnSalir).setBounds                          (x+=sep,y    ,width,height );
		
		btnEditar.setEnabled(false);
		txaObservaciones.setLineWrap(true); 
		txaObservaciones.setWrapStyleWord(true);
	    cont.setBackground(new java.awt.Color(255, 255, 255));
	       
		btnEditar.addActionListener(editar);
		btnBuscar.addActionListener(buscar);
		btnGuardar.addActionListener(guardar);
		btnSalir.addActionListener(salir);
		btnNuevo.addActionListener(nuevo);
		btnDeshacer.addActionListener(deshacer);
		btnFiltro.addActionListener(filtro);
		btnCamara.addActionListener(opFoto);
		btnVerificar.addActionListener(opVerificar);
		btnTrueFoto.addActionListener(opPresionFoto);
		
        btnImp_Datos_Completos.addActionListener(opImprimir_Datos);
		btnContratacion.addActionListener(opContratacion);
		btnAsistencia_Empleado.addActionListener(opAsistenciaEmpleado);	
		btnCortes.addActionListener(Reporte_Cortes_Por_empleado);
		btnIncontratables.addActionListener(Reporte_de_Empleados_No_Contratables);
		btn_R_horarios.addActionListener(opHorarioProvisional);
		btn_plantilla.addActionListener(opPlantilla);
		btn_movimientos.addActionListener(opGenerarInformacionDeColaboradores);
		
		btnLicencias.addActionListener(Reporte_de_Vigencia_Licencias);
		btnCumplea�os_del_Mes.addActionListener(Reporte_De_Cumpleanios_Del_Mes);
		btnAltasBajas.addActionListener(Reporte_De_Altas_y_Bajas);
		btnDocumentacion.addActionListener(opDocumentacion);
		
		btn_Adeudo.addActionListener(opRAdeudo);
		btnAusentismo.addActionListener(opRAusentismo);
		btnReporteRenuncia.addActionListener(opRRenuncia);
		btnEncuentaDeSalida.addActionListener(opEncuentaSalida);
		btnReporteSalida.addActionListener(opReporteDeEncuesta);

		btnExaminar.addActionListener(opExaminar);
		btnHorarioNew.addActionListener(opGenerarHorairo);
		btnHorario.addActionListener(opFiltroHorairo);
		btnHorario2.addActionListener(opFiltroHorairo2);
		btnHorario3.addActionListener(opFiltroHorairo3);
		
		txtTarjetaNomina.addKeyListener(txtlogns);
		
		rbHorario.addActionListener(opRButton);
		rbHorario2.addActionListener(opRButton);
		rbHorario3.addActionListener(opRButton);
		
		txtFolioEmpleado.requestFocus();
		txtFolioEmpleado.addKeyListener(buscar_action);
		
		cmbHorarioRotativo.addActionListener(opCmbHorarioRotarivo);
		txtSalarioDiario.addKeyListener(validaNumericoSD);
		txtSalarioDiarioIntegrado.addKeyListener(validaNumericoSDI);
		
		cont.add(pestanas);
		
		btnExaminar.setEnabled(false);
		btnCamara.setEnabled(false);
		
		btnHorario.setEnabled(false);
		btnHorario2.setEnabled(false);
		btnHorario3.setEnabled(false);
		
		txtDescanso.setEnabled(false);
		txtDobla.setEnabled(false);
		txtChecador.setEnabled(false);
		txtHorario.setEnabled(false);
		txtHorario2.setEnabled(false);
		txtHorario3.setEnabled(false);
		txtFechaActualizacion.setEnabled(false);
		
		panelEnabledFalse();
		txtFolioEmpleado.setEditable(true);
		txtFolioEmpleado.setEnabled(true);
		txtTelefono_Cuadrante.setEnabled(false);
		txtultimousuariomod.setEditable(false);
		
		 ImageIcon tmpIconDefault = new ImageIcon(System.getProperty("user.dir")+"/Iconos/Un.JPG");
         Icon iconoDefault = new ImageIcon(tmpIconDefault.getImage().getScaledInstance(btnFoto.getWidth(), btnFoto.getHeight(), Image.SCALE_DEFAULT));
         btnFoto.setIcon(iconoDefault);
         
		 ImageIcon file_status = new ImageIcon(System.getProperty("user.dir")+"/Iconos/Vigente.png");
         Icon iconoStatus = new ImageIcon(file_status.getImage().getScaledInstance(btnStatus.getWidth(), btnStatus.getHeight(), Image.SCALE_DEFAULT));
         btnStatus.setIcon(iconoStatus);
        
//       asigna el foco al JTextField deseado al arrancar la ventana
         this.addWindowListener(new WindowAdapter() {
                 public void windowOpened( WindowEvent e ){
                	 txtFolioEmpleado.requestFocus();
              }
         });
		
	//  abre el filtro de busqueda de empleado al presionar la tecla f2
	    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
	       KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), "foco");
	    
	    getRootPane().getActionMap().put("foco", new AbstractAction(){
	        @Override
	        public void actionPerformed(ActionEvent e)
	        {	        	btnFiltro.doClick();    	     }
	    });
	    
	    
		//  abre el filtro de busqueda de Horario 
	    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
	       KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0), "horario");
	    
	    getRootPane().getActionMap().put("horario", new AbstractAction(){
	        @Override
	        public void actionPerformed(ActionEvent e)
	        {    	        	btnHorarioNew.doClick();          }
	    });
						  ///deshacer con escape
						        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
						        getRootPane().getActionMap().put("escape", new AbstractAction(){
						            public void actionPerformed(ActionEvent e)
						            {                 	    btnDeshacer.doClick();
						                                    txtFolioEmpleado.requestFocus(); 
						          	    }
						        });
						///guardar con control+G
						        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_G,Event.CTRL_MASK),"guardar");
						             getRootPane().getActionMap().put("guardar", new AbstractAction(){
						                 public void actionPerformed(ActionEvent e)
						                 {                 	    btnGuardar.doClick();
						               	    }
						            });
						///guardar con F12
						             getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0), "guardar");
						                 getRootPane().getActionMap().put("guardar", new AbstractAction(){
						                     public void actionPerformed(ActionEvent e)
						                     {                 	    btnGuardar.doClick();
							                    	    }
						                });
						             
						///nuevo con F9
						         getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F9, 0), "nuevo");
						             getRootPane().getActionMap().put("nuevo", new AbstractAction(){
						                 public void actionPerformed(ActionEvent e)
						                 {                 	    btnNuevo.doClick();
						                   	    }
						            });
						             
						///nuevo con control+N
						         getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_N,Event.CTRL_MASK),"nuevo");
						              getRootPane().getActionMap().put("nuevo", new AbstractAction(){
						                  public void actionPerformed(ActionEvent e)
						                  {                 	    btnNuevo.doClick();
						                   	    }
						            });
						              
						///editar con F10
						              getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F10, 0), "editar");
						                  getRootPane().getActionMap().put("editar", new AbstractAction(){
						                      public void actionPerformed(ActionEvent e)
						                      {                 	    btnEditar.doClick();
							                    	    }
						                 });
					    ///editar con Ctrl+E
						              getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_E,Event.CTRL_MASK), "editar");
						                  getRootPane().getActionMap().put("editar", new AbstractAction(){
						                      public void actionPerformed(ActionEvent e)
						                      {                 	    btnEditar.doClick();
							                    	    }
						                 });
						                  
                  btnFoto.addMouseWheelListener(buscarConRueda);//a�adimos el MouseWheelListener al spinner
                  
	  	}
	
	 int valor = 0;
	MouseWheelListener buscarConRueda = new MouseWheelListener() {
	    public void mouseWheelMoved(MouseWheelEvent e) {
	    	
	    	if(!txtNombre.isEnabled()){
	    		
			        int rueda = e.getWheelRotation();//tomamos el valor de la rueda al girar
			       
			        
			        if (rueda < 0) {//si el valor es mas peque�o de 0 quiere decir que estamos subiendo
			        	
			            valor = Integer.valueOf(txtFolioEmpleado.getText().equals("")?"0":txtFolioEmpleado.getText());//tomamos el valor del txt
			            valor++;//aumentamos el valor  
			            
		                txtFolioEmpleado.setText(valor+"");//lo a�adimos de nuevo al txt
		                buscarEmpleado("rueda+"); //llamamos al metodo buscar
			        } else {//Si es mayor de 0 es que estamos bajando
			          
			        	valor = Integer.valueOf(txtFolioEmpleado.getText().equals("")?"0":txtFolioEmpleado.getText());
			        	
			            if (valor!=0){//En este caso no dejamos que baje de 0 el txt
			                valor--;//Disminuimos el valor
			            }
			            txtFolioEmpleado.setText(valor==0?"":valor+"");//Y lo a�adimos el txt (si es cero lo dejamos como vacio)
			            buscarEmpleado("rueda-"); //llamamos al metodo buscar
			        }
			    }
	 	  }
	};
	
	ActionListener opRAdeudo = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			if(txtFolioEmpleado.getText().equals("")){
				JOptionPane.showMessageDialog(null,"Necesita Seleccionar Primero Un Colaborador", "Mensaje!",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}else{
				String comando="exec sp_select_deducciones_de_empleado_pendientes "+txtFolioEmpleado.getText()+",'"+new Obj_Usuario().LeerSession().getNombre_completo().toString().trim()+"'";
				String reporte = "Obj_Reporte_De_Deuda_Por_Empleado.jrxml";
							 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
						
			}
		}
	};
	
	ActionListener opRAusentismo = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			new Cat_Reporte_De_Ausentismo_En_Lista_De_Raya().setVisible(true);
		}
	};
	
	ActionListener opRRenuncia = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			new Cat_Reporte_De_Motivos_De_renuncia().setVisible(true);
		}
	};
	
	ActionListener opReporteDeEncuesta = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			
			if(!txtNombre.getText().trim().equals("")){
				String basedatos="2.26";
				String vista_previa_reporte="no";
				int vista_previa_de_ventana=0;
				
				String comando="exec sp_select_encuensta_de_salida "+txtFolioEmpleado.getText()+"";
				String reporte = "Obj_Reporte_De_Encuenta_Y_Motivos_De_Salida.jrxml";
				 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
			}else{
				JOptionPane.showMessageDialog(null, "Es Necesario Que Primero Seleccione Un Empleado", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}
		}
	};
	
	ActionListener opEncuentaSalida = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			if(!txtNombre.getText().trim().equals("")){
				new Cat_Motivos_De_Renuncia(txtFolioEmpleado.getText(), txtNombre.getText().trim()+" "+txtApPaterno.getText().trim()+" "+txtApMaterno.getText().trim(), cmbEstablecimiento.getSelectedItem().toString(), cmbDepartamento.getSelectedItem().toString(), cmbPuesto.getSelectedItem().toString()).setVisible(true);
			}else{
				JOptionPane.showMessageDialog(null, "Es Necesario Que Primero Seleccione Un Empleado", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}
		}
	};


	ActionListener opCmbHorarioRotarivo = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			
			switch(cmbHorarioRotativo.getSelectedIndex()){
				case 0: btnHorario.setEnabled(true);
						btnHorario2.setEnabled(false);
						btnHorario3.setEnabled(false);
						
						rbHorario.setEnabled(true);
						rbHorario2.setEnabled(false);
						rbHorario3.setEnabled(false);
						break;
						
				case 1: btnHorario.setEnabled(true);
						btnHorario2.setEnabled(true);
						btnHorario3.setEnabled(false);
						
						rbHorario.setEnabled(true);
						rbHorario2.setEnabled(true);
						rbHorario3.setEnabled(false);
						break;
						
				case 2: btnHorario.setEnabled(true);
						btnHorario2.setEnabled(true);
						btnHorario3.setEnabled(true);
						
						rbHorario.setEnabled(true);
						rbHorario2.setEnabled(true);
						rbHorario3.setEnabled(true);
						break;
			}
		}
	};
	
	ActionListener opPresionFoto = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			if(btnTrueFoto.isSelected()){
				btnExaminar.setEnabled(true);
				btnCamara.setEnabled(true);
			}else{
				btnExaminar.setEnabled(false);
				btnCamara.setEnabled(false);
			}
	
		}
	};
	
	ActionListener opRButton = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			
			if(rbHorario.isSelected()==true){
//				buscar horario 1 y asignar dia de descanso y dobla
				Obj_Horario_Empleado descanso = new Obj_Horario_Empleado().buscar_tur(txtHorario.getText());
				txtDescanso.setText(descanso.getDescanso());
				txtDobla.setText(descanso.getDobla());
			}
			if(rbHorario2.isSelected()==true){
//				buscar horario 2 y asignar dia de descanso y dobla
				Obj_Horario_Empleado descanso = new Obj_Horario_Empleado().buscar_tur(txtHorario2.getText());
				txtDescanso.setText(descanso.getDescanso());
				txtDobla.setText(descanso.getDobla());
			}
			if(rbHorario3.isSelected()==true){
//				buscar horario 3 y asignar dia de descanso y dobla
				Obj_Horario_Empleado descanso = new Obj_Horario_Empleado().buscar_tur(txtHorario3.getText());
				txtDescanso.setText(descanso.getDescanso());
				txtDobla.setText(descanso.getDobla());
			}
		}
	};
	
	ActionListener opExaminar = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			FileDialog file = new FileDialog(new Frame());
			
			file.setTitle("Selecciona una Imagen");
			file.setMode(FileDialog.LOAD);
			file.setVisible(true);
			
			if(file.getDirectory() != null){

					try {
						String rootPicture = file.getDirectory()+file.getFile();
						
						File foto = new File(rootPicture);
						File destino = new File(System.getProperty("user.dir")+"/tmp/tmp.jpg");
				    	
				    	InputStream in = new FileInputStream(foto);
						OutputStream out = new FileOutputStream(destino);
						
					    byte[] buf = new byte[1024];
					    int len;
	
					    while ((len = in.read(buf)) > 0) {
					    	out.write(buf, 0, len);
					    }
					    
					    in.close();
					    out.close();
						
						File foto1 = new File(rootPicture);
						File destino1 = new File(System.getProperty("user.dir")+"/tmp/tmp_update/tmp.jpg");
				    	
				    	InputStream in1 = new FileInputStream(foto1);
						OutputStream out1 = new FileOutputStream(destino1);
						
					    byte[] buf1 = new byte[1024];
					    int len1;
	
					    while ((len1 = in1.read(buf1)) > 0) {
					    	out1.write(buf1, 0, len1);
					    }
					    
					    in1.close();
					    out1.close();
						
					    ImageIcon tmpIconDefault = new ImageIcon(System.getProperty("user.dir")+"/tmp/tmp.jpg");
				         Icon iconoDefault = new ImageIcon(tmpIconDefault.getImage().getScaledInstance(btnFoto.getWidth(), btnFoto.getHeight(), Image.SCALE_DEFAULT));
				         btnFoto.setIcon(iconoDefault);
				         
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				
			}else{
				JOptionPane.showMessageDialog(null,"No ha seleccionado ninguna imagen","Aviso",JOptionPane.WARNING_MESSAGE);
				return;
			}
		}
	};
	
	ActionListener opVerificar = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			if(txtNombre.getText().length() == 0 || txtApPaterno.getText().length() == 0 || txtApMaterno.getText().length() == 0){
				JOptionPane.showMessageDialog(null,"Los campos nombre y apellidos deben tener texto","Aviso!", JOptionPane.WARNING_MESSAGE);
				return;
			}else{
				String nombre = procesa_texto(txtNombre.getText()) + " " + procesa_texto(txtApPaterno.getText()) + " " + procesa_texto(txtApMaterno.getText());
				if(new Obj_Empleados().nombre_disponible(nombre)){
					btnVerificar.setBackground(Color.red);
					panelEnabledFalse();
					txtNombre.setEnabled(true);
					txtApPaterno.setEnabled(true);
					txtApMaterno.setEnabled(true);
					rbHorario.setEnabled(true);
					cmbSueldo.setSelectedIndex(0) ;
					cmbBono.setSelectedIndex(0);
					JOptionPane.showMessageDialog(null,"El Colaborador Ya Existe Busquelo \n En el Filtro y Modifiquelo Para Que No lo Duplique","Aviso!", JOptionPane.WARNING_MESSAGE, new ImageIcon("imagen/circulo-rojo-icono-9411-128.png"));
					
				}else{
					btnVerificar.setBackground(Color.blue);
					panelEnabledTrue();
					cmbSueldo.setSelectedItem("0.0");
					cmbBono.setSelectedItem("0.0");
					cmbBono.setEnabled(false);
					cmbSueldo.setEnabled(false);
					cmbBonoAsistencia.setEnabled(false);
					cmbBonopuntualidad.setEnabled(false);
					cmbActivo_Inactivo.setSelectedIndex(1);
				}
			}
		}
	};
	
    public String procesa_texto(String texto) {
        StringTokenizer tokens = new StringTokenizer(texto);
        texto = "";
        while(tokens.hasMoreTokens()){
            texto += " "+tokens.nextToken();
        }
        texto = texto.toString();
        texto = texto.trim().toUpperCase();
        return texto;
    }
	
	ActionListener opFoto = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(txtFolioEmpleado.getText().length() == 0){
				JOptionPane.showMessageDialog(null, "Cree un nuevo empleado, que contenga un folio.","Aviso", JOptionPane.WARNING_MESSAGE);
				return;
			}else{
				try{
					new MainCamara("tmp.jpg").setVisible(true);
				}catch(Exception ee){
					JOptionPane.showMessageDialog(null, "Verifique si est� conectada y configurada la camara", "Error!", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	};
	
	ActionListener opDocumentacion = new ActionListener() {
		public void actionPerformed(ActionEvent e){
			
				if(!txtApPaterno.getText().equals("")){
					new Cat_Documentacion_De_Empleado(txtFolioEmpleado.getText(),txtNombre.getText()+" "+txtApPaterno.getText()+" "+txtApMaterno.getText(),cmbEstablecimiento.getSelectedItem()+"",cmbPuesto.getSelectedItem()+"").setVisible(true);
				}else{
					JOptionPane.showMessageDialog(null,"Necesita Seleccionar Primero Un Empleado", "Mensaje!",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
					txtFolioEmpleado.requestFocus();
					return;
				}
			}
		};
	
	ActionListener buscar = new ActionListener() {
		public void actionPerformed(ActionEvent e){
			txtHorario.setFont(new Font("ARIAL", Font.ITALIC, 9));
			txtHorario2.setFont(new Font("ARIAL", Font.ITALIC, 9));
			txtHorario3.setFont(new Font("ARIAL", Font.ITALIC, 9));
			txtultimousuariomod.setFont(new Font("ARIAL", Font.TRUETYPE_FONT, 8));
			buscarEmpleado("");
		}
	};
			
	public void buscarEmpleado(String funcion){
			
			if(txtFolioEmpleado.getText().equals("")){
				JOptionPane.showMessageDialog(null,"Necesita Seleccionar Primero Un Empleado", "Mensaje!",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}else{
				
				Obj_Empleados re = new Obj_Empleados().buscar(Integer.parseInt(txtFolioEmpleado.getText()));
				if(re.getFolio() != 0){			
					txtFolioEmpleado.setText(re.getFolio()+"");
					txtChecador.setText(re.getNo_checador()+"");
					txtNombre.setText(re.getNombre()+"");
					txtApPaterno.setText(re.getAp_paterno()+"");
					txtApMaterno.setText(re.getAp_materno()+"");
					
					try {
						Date date = new SimpleDateFormat("dd/MM/yyyy").parse(re.getFecha_nacimiento());
						Date date_ingreso = new SimpleDateFormat("dd/MM/yyyy").parse(re.getFecha_ingreso());
						
						Date date_ingreso_imss = new SimpleDateFormat("dd/MM/yyyy").parse(re.getFecha_ingreso_imss());
						Date date_vencimiento_licencia = new SimpleDateFormat("dd/MM/yyyy").parse(re.getFecha_vencimiento_licencia());
						
						Date date_ingreso_imss_comparacion = new SimpleDateFormat("dd/MM/yyyy").parse("1/01/1900");
						Date date_vencimiento_licencia_comparacion = new SimpleDateFormat("dd/MM/yyyy").parse("1/01/1900");
						
						txtFechaNacimiento.setDate(date);
						txtIngreso.setDate(date_ingreso);
						
						if(date_ingreso_imss_comparacion.before(date_ingreso_imss)){
							txtIngresoImss.setDate(date_ingreso_imss);
						}else{
							txtIngresoImss.setDate(null);
						}
						
						if(date_vencimiento_licencia_comparacion.before(date_vencimiento_licencia)){
							txtVencimientoLicencia.setDate(date_vencimiento_licencia);
						}else{
							txtVencimientoLicencia.setDate(null);
						}
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
					
					txtCalle.setText(re.getCalle()+"");
					txtColonia.setText(re.getColonia()+"");
					txtPoblacion.setText(re.getPoblacion()+"");
					txtTelefono_Familiar.setText(re.getTelefono_familiar()+"");
					txtTelefono_Propio.setText(re.getTelefono_propio()+"");
					
					if(re.getTelefono_cuadrante()==null){
						txtTelefono_Cuadrante.setText("");
					}else{
						txtTelefono_Cuadrante.setText(re.getTelefono_cuadrante()+"");
					}
					
					txtRFC.setText(re.getRfc()+"");
					txtCurp.setText(re.getCurp()+"");
					if(re.getSexo()==0){
						cmbSexo.setSelectedItem("MASCULINO");
					}else{
						cmbSexo.setSelectedItem("FEMENINO");
					}
					
					if(re.getEstado_civil().equals("0")){	cmbEstadoCivil.setSelectedIndex(0);		}else{	cmbEstadoCivil.setSelectedItem(re.getEstado_civil());	}
					if(re.getTipo_sangre().equals("0")){	cmbTipoDeSangre.setSelectedIndex(0);	}else{	cmbTipoDeSangre.setSelectedItem(re.getTipo_sangre());	}
					if(re.getEscolaridad().equals("0")){	cmbEscolaridad.setSelectedIndex(0);		}else{	cmbEscolaridad.setSelectedItem(re.getEscolaridad());	}
					if(re.getContrato() == 0){	cmbContratacion.setSelectedItem("INDETERMINADO");	 }else{	cmbContratacion.setSelectedItem(re.getContrato()+" DIAS");	}
					if(re.getPresencia_fisica() == 1){	cmbPresenciaFisica.setSelectedItem("Aplica");}else{	cmbPresenciaFisica.setSelectedItem("No Aplica");	}
					
					ImageIcon tmpIconDefault = new ImageIcon(System.getProperty("user.dir")+"/tmp/tmp.jpg");
			         Icon iconoDefault = new ImageIcon(tmpIconDefault.getImage().getScaledInstance(btnFoto.getWidth(), btnFoto.getHeight(), Image.SCALE_DEFAULT));
			         btnFoto.setIcon(iconoDefault);
					
					Obj_Horario_Empleado comboFolioHorario = new Obj_Horario_Empleado().buscar_tur(re.getHorario());
					if(re.getHorario()>0){
						lblFolioHorario1.setText(re.getHorario()+"");
					}else{
						lblFolioHorario1.setText("");
					}
					txtHorario.setText(comboFolioHorario.getNombre());
					
					Obj_Horario_Empleado comboFolioHorario2 = new Obj_Horario_Empleado().buscar_tur2(re.getHorario2());
					if(re.getHorario2()>0){
						lblFolioHorario2.setText(re.getHorario2()+"");
					}else{
						lblFolioHorario2.setText("");
					}
					txtHorario2.setText(comboFolioHorario2.getNombre());
					
					
					Obj_Horario_Empleado comboFolioHorario3 = new Obj_Horario_Empleado().buscar_tur3(re.getHorario3());
					if(re.getHorario3()>0){
						lblFolioHorario3.setText(re.getHorario3()+"");
					}else{
						lblFolioHorario3.setText("");
					}
					txtHorario3.setText(comboFolioHorario3.getNombre());

					
					if(re.getStatus_h1()==1){
						rbHorario.setSelected(true);
					}
					if(re.getStatus_h2()==1){
						rbHorario2.setSelected(true);
					}
					if(re.getStatus_h3()==1){
						rbHorario3.setSelected(true);
					}
										
					txtHorario.setToolTipText(comboFolioHorario.getNombre());
					txtHorario2.setToolTipText(comboFolioHorario2.getNombre());
					
					txtDescanso.setText(re.getDescanso()+"");
					txtDobla.setText(re.getDobla()+"");
					
					switch(re.getStatus_rotativo()){
						case 0: cmbHorarioRotativo.setSelectedIndex(0); break;
						case 1: cmbHorarioRotativo.setSelectedIndex(1); break;
						case 2: cmbHorarioRotativo.setSelectedIndex(2); break;
					}
					
					cmbStatus.setSelectedIndex(re.getStatus()-1);
					txtBaja.setText(re.getFecha_baja()+"");
					chb_cuadrante_parcial.setSelected(re.isCuadrante_parcial());
					
					Obj_Departamento depart = new Obj_Departamento().buscar(re.getDepartameto());
					cmbDepartamento.setSelectedItem(depart.getDepartamento());
					
					txtImss.setText(re.getImss()+"");
					cmbActivo_Inactivo.setSelectedIndex(re.getStatus_imss());
					txtNumeroInfonavit.setText(re.getNumero_infonavit()+"");
					
					Obj_Establecimiento comboNombreEsta = new Obj_Establecimiento().buscar_estab(re.getEstablecimiento());
					cmbEstablecimiento.setSelectedItem(comboNombreEsta.getEstablecimiento());
					
					Obj_Puestos comboNombrePues = new Obj_Puestos().buscar_pues(re.getPuesto());
					cmbPuesto.setSelectedItem(comboNombrePues.getPuesto());
					
					cmbStatusChecador.setSelectedItem(re.getStatus_checador());
					
					txtSalarioDiario.setText(re.getSalario_diario()+"");
					txtSalarioDiarioIntegrado.setText(re.getSalario_diario_integrado()+"");
					txtFormaDePago.setText(re.getForma_pago()+"");
					cmbSueldo.setSelectedItem(re.getSueldo()+"");
					cmbBonopuntualidad.setSelectedItem(re.getBono_asistencia()+"");
					cmbBonoAsistencia.setSelectedItem(re.getBono_puntualidad()+"");
					
					txtDInfonacot.setText(re.getInfonacot()+"");
					txtultimousuariomod.setText(re.getUltimo_usuario_modifico());
					
					Obj_Bono_Complemento_Sueldo bono = new Obj_Bono_Complemento_Sueldo().buscar(re.getBono());
					cmbBono.setSelectedItem(bono.getBono()+"");

					cmbPrestamos.setSelectedIndex(re.getPrestamo());
					txtPensionAli.setText(re.getPension_alimenticia()+"");
					txtInfonavit.setText(re.getInfonavit()+"");	
					txtTarjetaNomina.setText(re.getTargeta_nomina()+"");
					
					Obj_Tipo_De_Bancos comboNombreBanco = new Obj_Tipo_De_Bancos().buscar_pues(re.getTipo_banco());
					cmbTipoBancos.setSelectedItem(comboNombreBanco.getBanco());
					
					if(re.isGafete() == true){chbGafete.setSelected(true);}
					else{chbGafete.setSelected(false);}
					
					if(re.isFuente_sodas() == true){chbFuente_Sodas.setSelected(true);}
					else{chbFuente_Sodas.setSelected(false);}
					
//					txtFechaActualizacion.setText(new SimpleDateFormat("dd/MM/yyyy").format((Date.parse(re.getFecha_actualizacion()))));
					txtFechaActualizacion.setText(re.getFecha_actualizacion());
					txaObservaciones.setText(re.getObservasiones());
					
					switch(cmbStatus.getSelectedIndex()+1){
						case 1:btnStatus.setIcon(new ImageIcon("Imagen/vigente.png")); 
							   btnEditar.setVisible(true);
							   break;
						case 2:btnStatus.setIcon(new ImageIcon("Imagen/vacaciones.png"));
							   btnEditar.setVisible(true);
							   break;
						case 3:btnStatus.setIcon(new ImageIcon("Imagen/incapacidad.png"));
							   btnEditar.setVisible(true);
							   break;
						case 4:btnStatus.setIcon(new ImageIcon("Imagen/baja.png")); 
							   btnEditar.setVisible(true);
							   break;
						case 5:btnStatus.setIcon(new ImageIcon("Imagen/baja.png")); 
							   btnEditar.setVisible(false); 
							   break;
						case 6:btnStatus.setIcon(new ImageIcon("Imagen/vigente.png")); 
							   btnEditar.setVisible(true);
							   break;
						case 7:btnStatus.setIcon(new ImageIcon("Imagen/baja.png")); 
							   btnEditar.setVisible(true);
							   break;
							   
					}
						
				    btnNuevo.setEnabled(false);
					panelEnabledFalse();
					txtFolioEmpleado.setEditable(true);
					txtFolioEmpleado.requestFocus();
					btnEditar.setEnabled(true);
					btnVerificar.setEnabled(false);
					txtFolioEmpleado.setEditable(false);
					btnBuscar.setEnabled(false);
					btnFiltro.setEnabled(true);
					btnHorario.setEnabled(false);
					cmbHorarioRotativo.setEnabled(false);
				}else{
					
					
					if(funcion.equals("rueda+")){
						valor++;
						txtFolioEmpleado.setText(valor+"");
						buscarEmpleado("rueda+");
						
					}
					
					if(funcion.equals("rueda-")){
						valor--;
						txtFolioEmpleado.setText(valor+"");
						buscarEmpleado("rueda-");
					}
					
					if(funcion.equals("")){
						JOptionPane.showMessageDialog(null, "El Registro no existe","Error",JOptionPane.WARNING_MESSAGE);
						panelEnabledFalse();
						txtFolioEmpleado.setEditable(true);
						txtFolioEmpleado.requestFocus();
						panelLimpiar();
					}
					
					
					
					return;
				}
		}
	}
	
	public int validaIMSS(){
		int valorIMSS = 0;
		
		if(txtIngresoImss.getDate()!=null){
			valorIMSS++;
		}
		if(!txtImss.getText().equals("")){
			valorIMSS++;
		}
		if(cmbActivo_Inactivo.getSelectedIndex()==0){
			valorIMSS++;
		}
		
		return valorIMSS;
	}
	
   public double tiene_deuda_fuente_sodas(){
  	  String query ="select isnull(sum(importe),0) from tb_captura_fuente_sodas where folio_empleado="+txtFolioEmpleado.getText().toString()+" and ticket in(select ticket from tb_fuente_sodas_rh where status=1)";
		double importe_fuente_sodas = 0;
		try { Connexion con = new Connexion();
			  Statement s = con.conexion().createStatement();
			  ResultSet rs = s.executeQuery(query);
			while(rs.next()){
				importe_fuente_sodas = Double.valueOf(rs.getDouble(1));
			      }
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en la funcion tiene_deuda_fuente_sodas \n SQLException: "+e1.getMessage()+query, "Avisa al Administrador del Sistema", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
		}
      return importe_fuente_sodas;
	}
	
   ActionListener guardar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			
			if(cmbStatus.getSelectedItem().toString().trim().equals("Baja") || cmbStatus.getSelectedItem().toString().trim().equals("No Contratable") || cmbStatus.getSelectedItem().toString().trim().equals("Renuncia")){
				if(new BuscarSQL().baja_en_catalogo_empleado()){
					guardar_modificar_Empleado();
				}else{
					JOptionPane.showMessageDialog(null, "No Se Puede Guardar Un Empleado Con Status De Renuncia, Baja o No Contratable Es Necesario Hacer La Encuesta De Renuncia y El Finiquito.", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
					return;
				}
			}else{
				    guardar_modificar_Empleado();
			}
			
			
			
			
		}	
	};
	
   @SuppressWarnings("deprecation")
public void guardar_modificar_Empleado(){

				Obj_Autorizacion_Auditoria auditoria = new Obj_Autorizacion_Auditoria().buscar();
				Obj_Autorizacion_Finanzas finanzas = new Obj_Autorizacion_Finanzas().buscar();
				
				boolean auditoriaBoolean = auditoria.isAutorizar();
				boolean finanzasBoolean = finanzas.isAutorizar();
				
				if((auditoriaBoolean == true)  || (finanzasBoolean == true)){
					JOptionPane.showMessageDialog(null, "La Lista De Raya Fue Autorizada No Puede Ser Modificado Ningun Empleado .."
					       +" Hasta Que Se Genere Por D.H o Se Desautorize por Finanzas o Auditoria <<Al dar Click en Aceptar SCOI se Cerrar�>>","Aviso",JOptionPane.WARNING_MESSAGE);
					try {	R.exec("taskkill /f /im javaw.exe"); } catch (IOException e1) {	e1.printStackTrace(); }		
				}else{
				
				if(txtFolioEmpleado.getText().equals("")){
					JOptionPane.showMessageDialog(null, "El Folio Es Requerido \n","Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				}
				if(validaIMSS()>0 && validaIMSS()<3){
					
					JOptionPane.showMessageDialog(null, "Para poder guardar o actualizar es necesario que los siguientes campos esten llenos o totalemente vacios:\n- No Seguro Social\n- Activo(IMSS) � Inactivo (IMSS)\n- Ingreso IMSS (Fecha)", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
					return;
					
				}else{			
					Obj_Empleados empleado = new Obj_Empleados().buscar(Integer.parseInt(txtFolioEmpleado.getText()));
					
					if(!(cmbBonoAsistencia.getSelectedItem().toString().trim().equals("Selecciona un Bono"))){
						if(!(cmbBonopuntualidad.getSelectedItem().toString().trim().equals("Selecciona un Bono"))){
							if(!(cmbBono.getSelectedItem().toString().trim().equals("Selecciona un Bono"))){							
								if( !(empleado.getBonocomplemento()==Double.valueOf(cmbBono.getSelectedItem().toString().trim())) || !(empleado.getBono_asistencia()==Double.valueOf(cmbBonoAsistencia.getSelectedItem().toString().trim()))
								    || !(empleado.getBono_puntualidad()==Double.valueOf(cmbBonopuntualidad.getSelectedItem().toString().trim())) || !(empleado.getSueldo()==Double.valueOf(cmbSueldo.getSelectedItem().toString().trim()))){
									if(new BuscarSQL().validar_cambio_de_sueldo_o_bono(txtFolioEmpleado.getText().toString().trim())){
										JOptionPane.showMessageDialog(null, "El Usuario Ya Cuenta Con Un Cambio De Sueldo o Bono Pendiente de Autorizar \nEs Necesario Que Lo Niegen o Acepten Antes De Solicitar Otro Cambio  :\n"+validaCampos(), "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
										return;						
									}
								}
							}
					    }
					}
					
					
					if(empleado.getFolio() == Integer.parseInt(txtFolioEmpleado.getText())){
						if(JOptionPane.showConfirmDialog(null, "El registro existe, �desea actualizarlo?") == 0){
							if(validaCampos()!="") {
								JOptionPane.showMessageDialog(null, "Los Siguientes Campos Son Requeridos Para Poder Guardar El Registro:\n"+validaCampos(), "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
								return;
							}else{
								
								
		//				datos personales	
								txtFechaActualizacion.setText(new SimpleDateFormat("dd/MM/yyyy").format((new Date())));
								empleado.setNo_checador(txtChecador.getText());
								empleado.setNombre(procesa_texto(txtNombre.getText()));
								empleado.setAp_paterno(procesa_texto(txtApPaterno.getText()));
								
								if(txtApMaterno.getText().length() != 0){
									empleado.setAp_materno(procesa_texto(txtApMaterno.getText()));
								}else{
									empleado.setAp_materno("");
								}
								
								empleado.setFecha_nacimiento(new SimpleDateFormat("dd/MM/yyyy").format(txtFechaNacimiento.getDate()));
								empleado.setCalle(txtCalle.getText());
								empleado.setColonia(txtColonia.getText());
								empleado.setPoblacion(txtPoblacion.getText());
								empleado.setTelefono_familiar(txtTelefono_Familiar.getText()+"");
								empleado.setTelefono_propio(txtTelefono_Propio.getText()+"");
								empleado.setRfc(txtRFC.getText());
								empleado.setCurp(txtCurp.getText());
								
								if(cmbSexo.getSelectedItem().equals("MASCULINO")){
									empleado.setSexo(0);
								}else{
									empleado.setSexo(1);
								}
								
								empleado.setEstado_civil(cmbEstadoCivil.getSelectedItem().toString());
								empleado.setTipo_sangre(cmbTipoDeSangre.getSelectedItem().toString());
								empleado.setEscolaridad(cmbEscolaridad.getSelectedItem().toString());
								
								if(btnTrueFoto.isSelected()){
									empleado.setFoto(new File(System.getProperty("user.dir")+"/tmp/tmp_update/tmp.jpg"));
								}else{
									empleado.setFoto(new File(System.getProperty("user.dir")+"/tmp/tmp.jpg"));
								}
		
		//				laboral
								if(lblFolioHorario1.getText().equals("")){
									empleado.setHorario(0);
								}else{
									empleado.setHorario(Integer.valueOf(lblFolioHorario1.getText()));
								}
								
								if(lblFolioHorario2.getText().equals("")){
									empleado.setHorario2(0);
								}else{
									empleado.setHorario2(Integer.valueOf(lblFolioHorario2.getText()));
								}
								
								if(lblFolioHorario3.getText().equals("")){
									empleado.setHorario3(0);
								}else{
									empleado.setHorario3(Integer.valueOf(lblFolioHorario3.getText()));
								}
								
								if(rbHorario.isSelected()==true){
									empleado.setStatus_h1(1);
									empleado.setStatus_h2(0);
									empleado.setStatus_h3(0);
								}
								if(rbHorario2.isSelected()==true){
									empleado.setStatus_h1(0);
									empleado.setStatus_h2(1);
									empleado.setStatus_h3(0);
								}
								if(rbHorario3.isSelected()==true){
									empleado.setStatus_h1(0);
									empleado.setStatus_h2(0);
									empleado.setStatus_h3(1);
								}
								
								switch(cmbHorarioRotativo.getSelectedIndex()){
									case 0: empleado.setStatus_rotativo(0); break;
									case 1: empleado.setStatus_rotativo(1); break;
									case 2: empleado.setStatus_rotativo(2); break;
								}
		
								if(cmbContratacion.getSelectedItem().toString().contains(" ")){
									empleado.setContrato(Integer.valueOf(cmbContratacion.getSelectedItem().toString().substring(0, cmbContratacion.getSelectedItem().toString().indexOf(" "))));
								}else{
									empleado.setContrato(0);
								}
								
								empleado.setFecha_ingreso(new SimpleDateFormat("dd/MM/yyyy").format(txtIngreso.getDate()));
								empleado.setStatus(cmbStatus.getSelectedIndex()+1);
								empleado.setCuadrante_parcial(chb_cuadrante_parcial.isSelected());
								
								Obj_Departamento depart = new Obj_Departamento().buscar_departamento(cmbDepartamento.getSelectedItem()+"");
								empleado.setDepartameto(depart.getFolio());
								
								empleado.setImss(txtImss.getText());
								empleado.setStatus_imss(cmbActivo_Inactivo.getSelectedIndex());
								empleado.setNumero_infonavit(txtNumeroInfonavit.getText()+"");
								
								
								Obj_Establecimiento comboFolioEsta = new Obj_Establecimiento().buscar_estab(cmbEstablecimiento.getSelectedItem()+"");
								empleado.setEstablecimiento(comboFolioEsta.getFolio());
		
								
								Obj_Puestos comboFolioPues = new Obj_Puestos().buscar_pues(cmbPuesto.getSelectedItem()+"");
								empleado.setPuesto(comboFolioPues.getFolio());
								
								if(txtIngresoImss.getDate()==null){
									empleado.setFecha_ingreso_imss("1/01/1900");
								}else{
									empleado.setFecha_ingreso_imss(new SimpleDateFormat("dd/MM/yyyy").format(txtIngresoImss.getDate()));
								}
								
								if(txtVencimientoLicencia.getDate()==null){
									empleado.setFecha_vencimiento_licencia("1/01/1900");
								}else{
									empleado.setFecha_vencimiento_licencia(new SimpleDateFormat("dd/MM/yyyy").format(txtVencimientoLicencia.getDate()));
								}
								
								empleado.setStatus_checador(cmbStatusChecador.getSelectedItem().toString().equals("NORMAL")?"N":"L");
								
		//				percepciones y deducciones
						
								if(!txtSalarioDiario.getText().equals("")){
									empleado.setSalario_diario(Float.parseFloat(txtSalarioDiario.getText())) ;
								}else{
									empleado.setSalario_diario(Float.parseFloat(0.0+"")); }
							
								if(!txtSalarioDiarioIntegrado.getText().equals("")){
									empleado.setSalario_diario_integrado(Float.parseFloat(txtSalarioDiarioIntegrado.getText()));
								}else{
									empleado.setSalario_diario_integrado(Float.parseFloat(0.0+""));
								}
								
								empleado.setForma_pago(txtFormaDePago.getText()+"");
		
								empleado.setSueldo(Float.valueOf(cmbSueldo.getSelectedItem().toString()));
								
								Obj_Bono_Complemento_Sueldo bono = new Obj_Bono_Complemento_Sueldo().buscarValor(Float.parseFloat(cmbBono.getSelectedItem()+""));
								empleado.setBono(bono.getFolio());
								
								empleado.setPrestamo(cmbPrestamos.getSelectedIndex());
								
								if(txtPensionAli.getText().length() != 0){
									empleado.setPension_alimenticia(Float.parseFloat(txtPensionAli.getText()));
								}else{
									empleado.setPension_alimenticia(Float.parseFloat(0.0+""));
								}
								if(txtInfonavit.getText().length() != 0){
									empleado.setInfonavit(Float.parseFloat(txtInfonavit.getText()));
								}else{
									empleado.setInfonavit(Float.parseFloat(0.0+""));
								}
								
								if((cmbBonoAsistencia.getSelectedItem().toString().trim().equals("Selecciona un Bono"))){
									empleado.setBono_asistencia(Float.parseFloat(0.0+""));
								 }else{
								   	empleado.setBono_asistencia(Float.valueOf(cmbBonoAsistencia.getSelectedItem().toString())); 	
								 }
								
								if((cmbBonopuntualidad.getSelectedItem().toString().trim().equals("Selecciona un Bono"))){
									empleado.setBono_puntualidad(Float.parseFloat(0.0+""));
								 }else{
									empleado.setBono_puntualidad(Float.valueOf(cmbBonopuntualidad.getSelectedItem().toString()));
								 }
								
								empleado.setInfonacot(Float.valueOf(txtDInfonacot.getText()));
								empleado.setTargeta_nomina(txtTarjetaNomina.getText()+"");
								empleado.setTipo_banco(cmbTipoBancos.getSelectedIndex());
								empleado.setPresencia_fisica(cmbPresenciaFisica.getSelectedItem().toString().equals("Aplica")?1:0);
								empleado.setGafete(chbGafete.isSelected());
								empleado.setFuente_sodas(chbFuente_Sodas.isSelected());
								empleado.setObservasiones(txaObservaciones.getText()+"");
								empleado.setFecha_actualizacion(txtFechaActualizacion.getText());
								
								//TODO 
							  if(!cmbStatus.getSelectedItem().toString().equals("Vigente")){
								double importe_fuente_sodas =tiene_deuda_fuente_sodas();
								  if(importe_fuente_sodas>0){
									if(JOptionPane.showConfirmDialog(null,"El Colaborador Tiene Una Deuda De Fuente De Sodas "
											                              +"\nDe:$"+importe_fuente_sodas
											                              +"\nQue Hiba Ser Cobrada En La Lista De Raya Actual "
											                              +"\nDesea Que Se Active De Nuevo La Fuente Sodas?","Aviso", JOptionPane.INFORMATION_MESSAGE,seleccion_de_asignacion_de_Horario1Horario2Horario3, new ImageIcon("imagen/fast-food-icon32.png")) == 0){
									
										if(new ActualizarSQL().devolver_fuente_de_sodas_por_cambio_de_estatus_en_el_empleado(txtFolioEmpleado.getText().toString())){
											JOptionPane.showMessageDialog(null, "Se Ha Devuelto A Pendiente De Cobro La Fuente De Sodas Del Colaborador Correctamente","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
										}
									 }else{
										 return;
									 }
									}
								}
								
								if(empleado.actualizar(Integer.parseInt(txtFolioEmpleado.getText()))){
									panelLimpiar();
									panelEnabledFalse();
									rbHorario2.setEnabled(false);
									rbHorario3.setEnabled(false);
									txtFolioEmpleado.setEnabled(true);
									txtFolioEmpleado.setEditable(true);
									txtFolioEmpleado.requestFocus();
									btnTrueFoto.setSelected(false);
									btnExaminar.setEnabled(false);
									btnCamara.setEnabled(false);
									txtHorario.setEnabled(false);
									btnBuscar.setEnabled(true);
									btnFiltro.setEnabled(true);
									btnNuevo.setEnabled(true);
									JOptionPane.showMessageDialog(null, "El Empleado Se Actualizo Correctamente","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
								}else{
									JOptionPane.showMessageDialog(null,"Error al intentar actualizar los datos","Aviso",JOptionPane.ERROR_MESSAGE);
								}
							}
						}else{
							return;
						}
					}else{
						if(validaCampos()!="") {
							JOptionPane.showMessageDialog(null, "Los Siguientes Campos Son Requeridos:\n "+validaCampos(), "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
							return;
						}else{
							
		//			datos personales	
							txtFechaActualizacion.setText(new SimpleDateFormat("dd/MM/yyyy").format((new Date())));
							empleado.setNo_checador(txtFolioEmpleado.getText());
							empleado.setNombre(procesa_texto(txtNombre.getText()));
							empleado.setAp_paterno(procesa_texto(txtApPaterno.getText()));
							
							if(txtApMaterno.getText().length() != 0){
								empleado.setAp_materno(procesa_texto(txtApMaterno.getText()));
							}else{
								empleado.setAp_materno("");
							}
							
							empleado.setFecha_nacimiento(new SimpleDateFormat("dd/MM/yyyy").format(txtFechaNacimiento.getDate()));
							empleado.setCalle(txtCalle.getText());
							empleado.setColonia(txtColonia.getText());
							empleado.setPoblacion(txtPoblacion.getText());
							empleado.setTelefono_familiar(txtTelefono_Familiar.getText()+"");
							empleado.setTelefono_propio(txtTelefono_Propio.getText()+"");
							empleado.setRfc(txtRFC.getText());
							empleado.setCurp(txtCurp.getText());
							
							if(cmbSexo.getSelectedItem().equals("MASCULINO")){
								empleado.setSexo(0);
							}else{
								empleado.setSexo(1);
							}
							
							empleado.setEstado_civil(cmbEstadoCivil.getSelectedItem().toString());
							empleado.setTipo_sangre(cmbTipoDeSangre.getSelectedItem().toString());
							empleado.setEscolaridad(cmbEscolaridad.getSelectedItem().toString());
							
							if(btnTrueFoto.isSelected()){
								empleado.setFoto(new File(System.getProperty("user.dir")+"/tmp/tmp_update/tmp.jpg"));
							}else{
								empleado.setFoto(new File(System.getProperty("user.dir")+"/Iconos/Un.jpg"));
							}
		
		//			laboral
							
							if(lblFolioHorario1.getText().equals("")){
								empleado.setHorario(0);
							}else{
								empleado.setHorario(Integer.valueOf(lblFolioHorario1.getText()));
							}
							
							if(lblFolioHorario2.getText().equals("")){
								empleado.setHorario2(0);
							}else{
								empleado.setHorario2(Integer.valueOf(lblFolioHorario2.getText()));
							}
							
							if(lblFolioHorario3.getText().equals("")){
								empleado.setHorario3(0);
							}else{
								empleado.setHorario3(Integer.valueOf(lblFolioHorario3.getText()));
							}
							
							if(rbHorario.isSelected()==true){
								empleado.setStatus_h1(1);
								empleado.setStatus_h2(0);
								empleado.setStatus_h3(0);
							}
							if(rbHorario2.isSelected()==true){
								empleado.setStatus_h1(0);
								empleado.setStatus_h2(1);
								empleado.setStatus_h3(0);
							}
							if(rbHorario3.isSelected()==true){
								empleado.setStatus_h1(0);
								empleado.setStatus_h2(0);
								empleado.setStatus_h3(1);
							}
							
							switch(cmbHorarioRotativo.getSelectedIndex()){
								case 0: empleado.setStatus_rotativo(0); break;
								case 1: empleado.setStatus_rotativo(1); break;
								case 2: empleado.setStatus_rotativo(2); break;
							}
							
							if(cmbContratacion.getSelectedItem().toString().contains(" ")){
								empleado.setContrato(Integer.valueOf(cmbContratacion.getSelectedItem().toString().substring(0, cmbContratacion.getSelectedItem().toString().indexOf(" "))));
							}else{
								empleado.setContrato(0);
							}
							
							empleado.setFecha_ingreso(new SimpleDateFormat("dd/MM/yyyy").format(txtIngreso.getDate()));
							empleado.setStatus(cmbStatus.getSelectedIndex()+1);
							empleado.setCuadrante_parcial(chb_cuadrante_parcial.isSelected());
		
							Obj_Departamento depart = new Obj_Departamento().buscar_departamento(cmbDepartamento.getSelectedItem()+"");
							empleado.setDepartameto(depart.getFolio());
							
							empleado.setImss(txtImss.getText());
							empleado.setStatus_imss(cmbActivo_Inactivo.getSelectedIndex());
							empleado.setNumero_infonavit(txtNumeroInfonavit.getText()+"");
							
							Obj_Establecimiento comboFolioEsta = new Obj_Establecimiento().buscar_estab(cmbEstablecimiento.getSelectedItem()+"");
							empleado.setEstablecimiento(comboFolioEsta.getFolio());
							
							Obj_Puestos comboFolioPues = new Obj_Puestos().buscar_pues(cmbPuesto.getSelectedItem()+"");
							empleado.setPuesto(comboFolioPues.getFolio());
							
							if(txtIngresoImss.getDate()==null){
								empleado.setFecha_ingreso_imss("1/01/1900");
							}else{
								empleado.setFecha_ingreso_imss(new SimpleDateFormat("dd/MM/yyyy").format(txtIngresoImss.getDate()));
							}
							
							if(txtVencimientoLicencia.getDate()==null){
								empleado.setFecha_vencimiento_licencia("1/01/1900");
							}else{
								empleado.setFecha_vencimiento_licencia(new SimpleDateFormat("dd/MM/yyyy").format(txtVencimientoLicencia.getDate()));
							}
							
							empleado.setStatus_checador(cmbStatusChecador.getSelectedItem().toString().equals("NORMAL")?"N":"L");
		
		//			percepciones y deducciones
							if(!txtSalarioDiario.getText().equals("")){
								empleado.setSalario_diario(Float.parseFloat(txtSalarioDiario.getText())) ;
							}else{
								empleado.setSalario_diario(Float.parseFloat(0.0+"")); }
						
							if(!txtSalarioDiarioIntegrado.getText().equals("")){
								empleado.setSalario_diario_integrado(Float.parseFloat(txtSalarioDiarioIntegrado.getText()));
							}else{
								empleado.setSalario_diario_integrado(Float.parseFloat(0.0+""));
							}
							
							empleado.setForma_pago(txtFormaDePago.getText()+"");
							empleado.setSueldo(Float.valueOf(cmbSueldo.getSelectedItem().toString()));
							
							Obj_Bono_Complemento_Sueldo bono = new Obj_Bono_Complemento_Sueldo().buscarValor(Float.parseFloat(cmbBono.getSelectedItem()+""));
							empleado.setBono(bono.getFolio());
							
							empleado.setPrestamo(cmbPrestamos.getSelectedIndex());
							
							if(txtPensionAli.getText().length() != 0){
								empleado.setPension_alimenticia(Float.parseFloat(txtPensionAli.getText()));
							}else{
								empleado.setPension_alimenticia(Float.parseFloat(0.0+""));
							}
							if(txtInfonavit.getText().length() != 0){
								empleado.setInfonavit(Float.parseFloat(txtInfonavit.getText()));
							}else{
								empleado.setInfonavit(Float.parseFloat(0.0+""));
							}
							
							empleado.setTargeta_nomina(txtTarjetaNomina.getText()+"");
							empleado.setTipo_banco(cmbTipoBancos.getSelectedIndex());
							
//							empleado.setBono_asistencia(Float.valueOf(cmbBonoAsistencia.getSelectedItem().toString())); 
//							empleado.setBono_puntualidad(Float.valueOf(cmbBonopuntualidad.getSelectedItem().toString()));
							
							if(txtInfonavit.getText().length() != 0){
								empleado.setInfonavit(Float.parseFloat(txtInfonavit.getText()));
							}else{
								empleado.setInfonavit(Float.parseFloat(0.0+""));
							}
							
							if(txtDInfonacot.getText().length() != 0){
								empleado.setInfonacot(Float.parseFloat(txtDInfonacot.getText()));
							}else{
								empleado.setInfonacot(Float.parseFloat(0.0+""));
							}

							empleado.setPresencia_fisica(cmbPresenciaFisica.getSelectedItem().toString().equals("Aplica")?1:0);
							
							empleado.setGafete(chbGafete.isSelected());
							empleado.setFuente_sodas(chbFuente_Sodas.isSelected());
							empleado.setObservasiones(txaObservaciones.getText()+"");
							
							empleado.setFecha_actualizacion(txtFechaActualizacion.getText());
							
							if(empleado.guardar()){
								panelLimpiar();
								panelEnabledFalse();
								rbHorario2.setEnabled(false);
								rbHorario3.setEnabled(false);
								txtFolioEmpleado.setEnabled(true);
								txtFolioEmpleado.setEditable(true);
								txtFolioEmpleado.requestFocus();
								btnTrueFoto.setSelected(false);
								btnExaminar.setEnabled(false);
								btnCamara.setEnabled(false);
								txtHorario.setEnabled(false);
								btnBuscar.setEnabled(true);
								btnFiltro.setEnabled(true);
								btnNuevo.setEnabled(true);
								JOptionPane.showMessageDialog(null, "El Colaborardor Se Guardo Correctamente","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
							}else{
								JOptionPane.showMessageDialog(null, "Ocurri� un problema al almacenar el Colaborador", "Error", JOptionPane.ERROR_MESSAGE);
							}
						}
					}
				}			
			}
   }
	
	ActionListener filtro = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			btnBuscar.setEnabled(true);
			new Cat_Filtro_Empleado().setVisible(true);
		}
	};
	
	ActionListener editar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			Obj_Empleados empleado = new Obj_Empleados().buscar(Integer.parseInt(txtFolioEmpleado.getText()));
			if(empleado.getFolio() != 0){
				
				switch(empleado.getStatus_rotativo()){
				
					case 0: panelEnabledTrue();
							cmbHorarioRotativo.setSelectedIndex(0);
							cmbHorarioRotativo.setEnabled(true);
							rbHorario.setSelected(true);
							break;
							
					case 1: panelEnabledTrue();
							cmbHorarioRotativo.setSelectedIndex(1);
							cmbHorarioRotativo.setEnabled(true);
							rbHorario2.setEnabled(true);
							break;
							
					case 2: panelEnabledTrue();
							cmbHorarioRotativo.setSelectedIndex(2);
							cmbHorarioRotativo.setEnabled(true);
							rbHorario3.setEnabled(true);
							break;
				}
				
				txtFolioEmpleado.setEditable(false);
				btnEditar.setEnabled(false);
				btnNuevo.setEnabled(true);
			}else{
				JOptionPane.showMessageDialog(null,"El Registr� Que Desea Editar no Existe","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}
		}		
	};
	
	public void panelEnabledTrue(){	
		txtNombre.setEnabled(true);
		txtApPaterno.setEnabled(true);
		txtApMaterno.setEnabled(true);
		txtPensionAli.setEnabled(true);
		cmbEstablecimiento.setEnabled(true);
		cmbPuesto.setEnabled(true);
		cmbSueldo.setEnabled(true);
		cmbBono.setEnabled(true);
		cmbPrestamos.setEnabled(true);
		txtInfonavit.setEnabled(true);
		txtDInfonacot.setEnabled(true);
		txtTarjetaNomina.setEnabled(true);
		cmbTipoBancos.setEnabled(true);
		txtImss.setEnabled(true);
		chbFuente_Sodas.setEnabled(true);
		chbGafete.setEnabled(true);
		cmbStatus.setEnabled(true);
		txaObservaciones.setEnabled(true);
		txtFechaNacimiento.setEnabled(true);
		cmbActivo_Inactivo.setEnabled(true);
		txtIngreso.setEnabled(true);
		txtTelefono_Familiar.setEnabled(true);
		chb_cuadrante_parcial.setEnabled(true);
		cmbStatusChecador.setEnabled(true);
		
		txtIngresoImss.setEnabled(true);
		txtVencimientoLicencia.setEnabled(true);
		
		txtCalle.setEnabled(true);
		txtColonia.setEnabled(true);
		txtPoblacion.setEnabled(true);
		txtTelefono_Propio.setEnabled(true);
		txtRFC.setEnabled(true);
		txtCurp.setEnabled(true);
		
		cmbSexo.setEnabled(true);
		
		rbHorario.setEnabled(true);
		rbHorario2.setEnabled(true);
		cmbDepartamento.setEnabled(true);
		txtNumeroInfonavit.setEnabled(true);
		
		txtSalarioDiario.setEnabled(true);
		txtSalarioDiarioIntegrado.setEnabled(true);
		cmbBonopuntualidad.setEnabled(true);
		cmbBonoAsistencia.setEnabled(true);
		txtFormaDePago.setEnabled(true);
		
		cmbEstadoCivil.setEnabled(true);
		cmbTipoDeSangre.setEnabled(true);
		cmbEscolaridad.setEnabled(true);
		cmbContratacion.setEnabled(true);
		cmbPresenciaFisica.setEnabled(true);
	}
	
	public void panelEnabledFalse(){	
		txtFolioEmpleado.setEnabled(false);
		txtNombre.setEnabled(false);                                                                       
		txtApPaterno.setEnabled(false);                                                                    
		txtApMaterno.setEnabled(false);                                                                    
		txtPensionAli.setEnabled(false);                                                                   
		cmbEstablecimiento.setEnabled(false);                                                              
		cmbPuesto.setEnabled(false);                                                                       
		cmbSueldo.setEnabled(false);                                                                       
		cmbBono.setEnabled(false);                                                                         
		cmbPrestamos.setEnabled(false);                                                                    
		txtInfonavit.setEnabled(false);    
		txtDInfonacot.setEnabled(false);
		txtTarjetaNomina.setEnabled(false);                                                                
		cmbTipoBancos.setEnabled(false);                                                                   
		txtImss.setEnabled(false);                                                                         
		chbFuente_Sodas.setEnabled(false);                                                                 
		chbGafete.setEnabled(false);                                                                       
		cmbStatus.setEnabled(false);                                                                       
		txaObservaciones.setEnabled(false);                                                                
		txtFechaNacimiento.setEnabled(false);                                                                   
		cmbActivo_Inactivo.setEnabled(false);                                                              
		txtIngreso.setEnabled(false);                                                                      
		txtTelefono_Familiar.setEnabled(false);                                                            
		chb_cuadrante_parcial.setEnabled(false);
		cmbStatusChecador.setEnabled(false);
		                                                                                                   
		txtIngresoImss.setEnabled(false);                                                                  
		txtVencimientoLicencia.setEnabled(false);                                                          
		                                                                                                   
		txtCalle.setEnabled(false);                                                                        
		txtColonia.setEnabled(false);                                                                      
		txtPoblacion.setEnabled(false);                                                                    
		txtTelefono_Propio.setEnabled(false);                                                              
		txtRFC.setEnabled(false);                                                                          
		txtCurp.setEnabled(false);                                                                         
		                                                                                                   
		cmbSexo.setEnabled(false);                                                                     
		                                                                                                   
		rbHorario.setEnabled(false);                                                                       
		rbHorario2.setEnabled(false);                                                                      
		rbHorario3.setEnabled(false);                                                                      
		                                                                                                   
		txtBaja.setEnabled(false);                                                                         
		cmbDepartamento.setEnabled(false);                                                                 
		txtNumeroInfonavit.setEnabled(false);                                                              
		                                                                                                   
		txtSalarioDiario.setEnabled(false);
		txtSalarioDiarioIntegrado.setEnabled(false);
		cmbBonopuntualidad.setEnabled(false);
		cmbBonoAsistencia.setEnabled(false);
		txtFormaDePago.setEnabled(false);
		
		btnTrueFoto.setSelected(false);
		btnExaminar.setEnabled(false);
		btnCamara.setEnabled(false);
		
		cmbEstadoCivil.setEnabled(false);
		cmbTipoDeSangre.setEnabled(false);
		cmbEscolaridad.setEnabled(false);
		cmbContratacion.setEnabled(false);
		cmbPresenciaFisica.setEnabled(false);
	}

	///boton deshacer
	public void panelLimpiar(){	
		txtFolioEmpleado.setText("");
		txtChecador.setText("");
		txtNombre.setText("");
		txtApPaterno.setText("");
		txtApMaterno.setText("");
		txtPensionAli.setText("");
		cmbEstablecimiento.setSelectedIndex(0);
		cmbPuesto.setSelectedIndex(0);
		txtHorario.setText("");
		txtHorario2.setText("");
		txtHorario3.setText("");
		cmbSueldo.setSelectedIndex(0);
		cmbBono.setSelectedIndex(0);
		cmbPrestamos.setSelectedIndex(0);
		txtInfonavit.setText("");
		txtDInfonacot.setText("");
		txtultimousuariomod.setToolTipText("");
		txtTarjetaNomina.setText("");
		cmbTipoBancos.setSelectedIndex(0);
		txtImss.setText("");
		chbFuente_Sodas.setSelected(false);
		chbGafete.setSelected(false);
		cmbStatus.setSelectedIndex(0);
		txaObservaciones.setText("");
	    btnFoto.setIcon(new ImageIcon(""));	
	    btnStatus.setIcon(new ImageIcon(""));
	    cmbActivo_Inactivo.setSelectedIndex(0);
	    txtTelefono_Familiar.setText("");
	    txtTelefono_Cuadrante.setText("");
	    chb_cuadrante_parcial.setSelected(false);
	    txtFechaNacimiento.setDate(null);
	    cmbStatusChecador.setSelectedIndex(0);
	    
		txtCalle.setText("");
		txtColonia.setText("");
		txtPoblacion.setText("");
		txtTelefono_Propio.setText("");
		txtRFC.setText("");
		txtCurp.setText("");
		txtIngreso.setDate(null);
		
		txtIngresoImss.setDate(null);
		txtVencimientoLicencia.setDate(null);
		
		txtDescanso.setText("");
		txtDobla.setText("");
		
		txtBaja.setText("");
		cmbDepartamento.setSelectedIndex(0);
		txtNumeroInfonavit.setText("");
		
		txtSalarioDiario.setText("");
		txtSalarioDiarioIntegrado.setText("");
		cmbBonopuntualidad.setSelectedIndex(0);
		cmbBonoAsistencia.setSelectedIndex(0);

		txtFormaDePago.setText("");
		txtFechaActualizacion.setText("");
		
		lblFolioHorario1.setText("");
		lblFolioHorario2.setText("");
		lblFolioHorario3.setText("");
		
		cmbHorarioRotativo.setSelectedIndex(0);
		
		txtultimousuariomod.setText("");
		cmbSexo.setSelectedIndex(0);
		cmbEstadoCivil.setSelectedIndex(0);
		cmbTipoDeSangre.setSelectedIndex(0);
		cmbEscolaridad.setSelectedIndex(0);
		cmbContratacion.setSelectedIndex(0);
		cmbPresenciaFisica.setSelectedIndex(0);
	    
		 ImageIcon tmpIconDefault = new ImageIcon(System.getProperty("user.dir")+"/Iconos/Un.JPG");
         Icon iconoDefault = new ImageIcon(tmpIconDefault.getImage().getScaledInstance(btnFoto.getWidth(), btnFoto.getHeight(), Image.SCALE_DEFAULT));
         btnFoto.setIcon(iconoDefault);
         
		 ImageIcon file_status = new ImageIcon(System.getProperty("user.dir")+"/Iconos/Vigente.png");
         Icon iconoStatus = new ImageIcon(file_status.getImage().getScaledInstance(btnStatus.getWidth(), btnStatus.getHeight(), Image.SCALE_DEFAULT));
         btnStatus.setIcon(iconoStatus);
	}
	
	ActionListener nuevo = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			try {
				Obj_Empleados empleado = new Obj_Empleados().buscar_nuevo();
				if(empleado.getFolio() != 0){
					panelLimpiar();
					panelEnabledFalse();
					txtNombre.setEnabled(true);
					txtApPaterno.setEnabled(true);
					txtApMaterno.setEnabled(true);
					txtFolioEmpleado.setText(empleado.getFolio()+1+"");
					txtNombre.requestFocus();
					txtFechaActualizacion.setText(new SimpleDateFormat("dd/MM/yyyy").format((new Date())));
					
					 ImageIcon tmpIconDefault = new ImageIcon(System.getProperty("user.dir")+"/Iconos/Un.JPG");
			         Icon iconoDefault = new ImageIcon(tmpIconDefault.getImage().getScaledInstance(btnFoto.getWidth(), btnFoto.getHeight(), Image.SCALE_DEFAULT));
			         btnFoto.setIcon(iconoDefault);
			         
					 ImageIcon file_status = new ImageIcon(System.getProperty("user.dir")+"/Iconos/Vigente.png");
			         Icon iconoStatus = new ImageIcon(file_status.getImage().getScaledInstance(btnStatus.getWidth(), btnStatus.getHeight(), Image.SCALE_DEFAULT));
			         btnStatus.setIcon(iconoStatus);
						btnFiltro.setEnabled(false); 
						btnBuscar.setEnabled(false); 
						btnNuevo.setEnabled(false);
						btnVerificar.setEnabled(true);
				}else{
					panelEnabledTrue();
					txtFolioEmpleado.setText(1+"");
					txtFolioEmpleado.setEnabled(false);
					txtNombre.requestFocus();
					txtFechaActualizacion.setText(new SimpleDateFormat("dd/MM/yyyy").format((new Date())));
					
					String file = System.getProperty("user.dir")+"/Iconos/Un.JPG";
					ImageIcon tmpIconAux = new ImageIcon(file);
					btnFoto.setIcon(new ImageIcon(tmpIconAux.getImage().getScaledInstance(230, 195, Image.SCALE_DEFAULT)));	
					
					String file_status = System.getProperty("user.dir")+"/Iconos/Vigente.png";
					ImageIcon tmpIconAux_status = new ImageIcon(file_status);
					btnStatus.setIcon(new ImageIcon(tmpIconAux_status.getImage().getScaledInstance(230, 195, Image.SCALE_DEFAULT)));

				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	};
	
	ActionListener deshacer = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			panelLimpiar();
			rbHorario2.setEnabled(false);
			panelEnabledFalse();
			txtFolioEmpleado.setEditable(true);
			txtFolioEmpleado.setEnabled(true);
			txtFolioEmpleado.requestFocus();
			btnEditar.setEnabled(false);
			btnNuevo.setEnabled(true);
			txtHorario.setEnabled(false);
			
			btnBuscar.setEnabled(true);
			btnFiltro.setEnabled(true);
			
		}
	};

	String basedatos="2.26";
	String vista_previa_reporte="no";
	int vista_previa_de_ventana=0;
	
	ActionListener opContratacion = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(txtFolioEmpleado.getText().equals("")){
				JOptionPane.showMessageDialog(null,"Necesita Seleccionar Primero Un Empleado", "Mensaje!",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}else{
			String Sexo ="", NombreUsuario="";
			String Edad = "";
			
				Sexo=cmbSexo.getSelectedItem()+"";
			
				Obj_Usuario usuario = new Obj_Usuario().LeerSession();
				NombreUsuario=usuario.getNombre_completo();
							
				String fecha_nacimiento = new SimpleDateFormat("dd/MM/yyyy").format(txtFechaNacimiento.getDate())+" 00:00:00";
			     try {
				Edad = (new BuscarSQL().edad(fecha_nacimiento));
				  } catch (SQLException e1) {e1.printStackTrace();}
			int dias_contrato =0; 
					
					switch ((cmbContratacion.getSelectedIndex())) {
					case 1:
						dias_contrato =30;
						break;
					case 2:
						dias_contrato =60;
						break;
					case 3:
						dias_contrato =90;
						break;
					case 4:
						dias_contrato =120;
						break;
					case 5:
						dias_contrato =150;
						break;	
					default:
						dias_contrato =0;
						break;
					}
			
			
			new Cat_Reportes_De_Contratacion_Por_Empleado(txtFolioEmpleado.getText(), txtNombre.getText()+" "+txtApPaterno.getText()+" "+txtApMaterno.getText(), cmbEstablecimiento.getSelectedItem().toString()
					                                      ,cmbDepartamento.getSelectedItem().toString(),cmbPuesto.getSelectedItem().toString(), Sexo,cmbEstadoCivil.getSelectedItem().toString().trim(), Edad, txtCalle.getText()+", COL. "+txtColonia.getText()+", "+txtPoblacion.getText()
					                                      ,cmbSueldo.getSelectedItem().toString(), NombreUsuario, txtHorario.getText(),new SimpleDateFormat("dd/MM/yyyy").format(txtIngreso.getDate()),dias_contrato).setVisible(true);

			}
			}
	};
	
	
	ActionListener opImprimir_Datos = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			if(txtFolioEmpleado.getText().equals("")){
				JOptionPane.showMessageDialog(null,"Necesita Seleccionar Primero Un Colaborador", "Mensaje!",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}else{
				String comando="exec sp_select_datos_completos_empleado "+txtFolioEmpleado.getText()+"";
				String reporte = "Obj_Reporte_De_Empleado_Datos_Completos.jrxml";
							 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
						
			}
						
	 	}
	   };
	   
	ActionListener opAsistenciaEmpleado = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(txtFolioEmpleado.getText().equals("")){
				JOptionPane.showMessageDialog(null,"Necesita Seleccionar Primero Un Colaborador", "Mensaje!",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}else{
			new Cat_Reporte_De_Asistencia_Por_Empleado(txtFolioEmpleado.getText(),txtNombre.getText()+" "+txtApPaterno.getText(),cmbEstablecimiento.getSelectedItem().toString(),cmbDepartamento.getSelectedItem().toString()).setVisible(true);
			}
			}
	};
	
	ActionListener Reporte_Cortes_Por_empleado = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(txtFolioEmpleado.getText().equals("")){
				JOptionPane.showMessageDialog(null,"Necesita Seleccionar Primero Un Colaborador", "Mensaje!",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}else{
				new Cat_Reportes_De_Cortes_De_Lista_De_Raya_Actual(txtFolioEmpleado.getText()).setVisible(true);
			}
		}
	};
	
	ActionListener opPlantilla = new ActionListener(){
		public void actionPerformed(ActionEvent e){
				new Cat_Personal_Con_Horario().setVisible(true);
		}
	};
	
	ActionListener opHorarioProvisional = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			new Cat_Reportes_De_Horarios().setVisible(true);
		}
	};
	

	
	ActionListener Reporte_de_Empleados_No_Contratables = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			String comando="exec Sp_Reporte_De_Empleado_No_Contratables";
			String reporte = "Obj_Reporte_De_Empleados_No_Contratables.jrxml";
					 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
		}
	};
	
	ActionListener Reporte_de_Vigencia_Licencias = new ActionListener() {
		public void actionPerformed(ActionEvent e) {

				String comando="exec sp_select_vencimiento_de_licencia ";
				String reporte = "Obj_Reporte_De_Empleados_Vigencia_De_Licencia_De_Choferes.jrxml";
							 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
	 	}
	   };
	   
	ActionListener Reporte_De_Cumpleanios_Del_Mes = new ActionListener(){
			public void actionPerformed(ActionEvent e){
					new Cat_Reporte_De_Cumpleanios_Del_Mes().setVisible(true);
			}
		};
	   
	ActionListener Reporte_De_Altas_y_Bajas = new ActionListener(){
			public void actionPerformed(ActionEvent e){
					new Cat_Reportes_De_Altas_y_Bajas_En_Un_Rango_De_Fechas().setVisible(true);
			}
		};
	   
	
	ActionListener salir = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			dispose();
		}
	};
	
	ActionListener opFiltroHorairo = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			seleccion_de_asignacion_de_Horario1Horario2Horario3=1;
			new Filtro_Horario_Empleado().setVisible(true);
		}
	};
	ActionListener opFiltroHorairo2 = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			seleccion_de_asignacion_de_Horario1Horario2Horario3=2;
			new Filtro_Horario_Empleado().setVisible(true);
		}
	};
	ActionListener opFiltroHorairo3 = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			seleccion_de_asignacion_de_Horario1Horario2Horario3=3;
			new Filtro_Horario_Empleado().setVisible(true);
		}
	};
	
	ActionListener opGenerarHorairo = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(txtFolioEmpleado.getText().equals("")){
				JOptionPane.showMessageDialog(null,"Necesita Seleccionar Primero Un Colaborador", "Mensaje!",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}else{
			new Cat_Horarios(Integer.valueOf(lblFolioHorario1.getText().toString())).setVisible(true);
			}
		}
	};
	
	ActionListener opGenerarInformacionDeColaboradores = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			new Cat_Reportes_De_Informacion_De_Movimientos_De_Colaboradores().setVisible(true);
		}
	};
	
	KeyListener buscar_action = new KeyListener() {
		@Override
		public void keyTyped(KeyEvent e){
		}
		@Override
		public void keyReleased(KeyEvent e) {	
		}
		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				btnBuscar.doClick();
			}
		}
	};
	
	KeyListener txtlogns = new KeyListener() {
		public void keyTyped(KeyEvent e) {
			char caracter = e.getKeyChar();
			if((caracter != KeyEvent.VK_BACK_SPACE) && (caracter != KeyEvent.VK_DELETE)){
				int longitud = txtTarjetaNomina.getText().length();
				String copas = txtTarjetaNomina.getText();
				switch(longitud){
					case 4 : txtTarjetaNomina.setText(copas+"-"); break;
					case 9 : txtTarjetaNomina.setText(copas+"-"); break;
					case 14 : txtTarjetaNomina.setText(copas+"-"); break;
				}
				if(((caracter < '0') ||
						(caracter > '9'))){
						e.consume(); 
				}				
			}
		}
		@Override
		public void keyPressed(KeyEvent arg0) {}
		@Override
		public void keyReleased(KeyEvent arg0) {}
	};
	
	KeyListener validaNumericoSD = new KeyListener() {
		@Override
		public void keyTyped(KeyEvent e) {
			char caracter = e.getKeyChar();
			
		    if(((caracter < '0') ||	
		    	(caracter > '9')) && 
		    	(caracter != '.' )){
		    	e.consume();
		    	}
		    	
		   if (caracter==KeyEvent.VK_PERIOD){
		    	String texto = txtSalarioDiario.getText().toString();
				if (texto.indexOf(".")>-1) e.consume();
			}
		}
		@Override
		public void keyPressed(KeyEvent e){}
		@Override
		public void keyReleased(KeyEvent e){}
	};
	
	KeyListener validaNumericoSDI = new KeyListener() {
		@Override
		public void keyTyped(KeyEvent e) {
			char caracter = e.getKeyChar();
			
		    if(((caracter < '0') ||	
		    	(caracter > '9')) && 
		    	(caracter != '.' )){
		    	e.consume();
		    	}
		    	
		   if (caracter==KeyEvent.VK_PERIOD){
		    	String texto = txtSalarioDiarioIntegrado.getText().toString();
				if (texto.indexOf(".")>-1) e.consume();
			}
		}
		@Override
		public void keyPressed(KeyEvent e){}
		@Override
		public void keyReleased(KeyEvent e){}
	};
	
	private String validaCampos(){
		String error="";
		String fechaNull = txtFechaNacimiento.getDate()+"";
		String fechaIngresoNull = txtIngreso.getDate()+"";

		if(txtFolioEmpleado.getText().equals("")) 		error+= "Folio\n";
		if(txtNombre.getText().equals("")) 		error+= "Nombre\n";
		if(txtApPaterno.getText().equals(""))	error+= "Ap Paterno\n";
		if(txtCalle.getText().equals(""))	error+= "Calle\n";
		if(txtColonia.getText().equals(""))	error+= "Colonia\n";
		if(txtPoblacion.getText().equals(""))	error+= "Poblacion\n";
		if(txtTelefono_Familiar.getText().equals(""))	error+= "Telefono Familiar\n";
		if(txtTelefono_Propio.getText().equals(""))	error+= "Telefono Propio\n";
		if(cmbSexo.getSelectedIndex()==0)	error+= "Sexo\n";
		if(cmbEstadoCivil.getSelectedIndex()==0)	error+= "Estado Civil\n";
		if(cmbTipoDeSangre.getSelectedIndex()==0)	error+= "Tipo De Sangre\n";
		if(cmbEscolaridad.getSelectedIndex()==0)	error+= "Escolaridad\n";
		if(cmbDepartamento.getSelectedItem().equals("Selecciona un Departamento"))	error+= "Departamento\n";
		if(cmbEstablecimiento.getSelectedItem().equals("Selecciona un Establecimiento")) error += "Establecimiento\n";
		if(cmbPuesto.getSelectedItem().equals("Selecciona un Puesto")) error += "Puesto\n";
		
		switch(cmbHorarioRotativo.getSelectedIndex()){
		case 0:	if(txtHorario.getText().equals("")) 		error+= "Horario\n"; break;
		case 1: if(txtHorario.getText().equals("")) 		error+= "Horario 2\n";
				if(txtHorario2.getText().equals("")) 		error+= "Horario 3\n";break;
		case 2: if(txtHorario.getText().equals("")) 		error+= "Horario\n";
				if(txtHorario2.getText().equals("")) 		error+= "Horario 2\n";
				if(txtHorario3.getText().equals("")) 		error+= "Horario 3\n";break;
		}
		if(cmbContratacion.getSelectedIndex()==0)	error+= "Contrato\n";
		if(cmbSueldo.getSelectedItem().equals("Selecciona un Sueldo")) error += "Sueldo\n";
		if(cmbBono.getSelectedItem().equals("Selecciona un Bono")) error += "Bono\n";
		if(cmbPrestamos.getSelectedItem().equals("Selecciona un Rango de Prestamo")) error += "Rango de Prestamo\n";
		if(fechaNull.equals("null"))error+= "Fecha de Nacimiento\n";	
		if(fechaIngresoNull.equals("null"))error += "Fecha de ingreso\n";
		if(cmbPresenciaFisica.getSelectedIndex()==0)	error+= "Presencia Fisica\n";
		return error;
	}
	
	public class MainCamara extends javax.swing.JFrame {
		
		
	    private Dispositivos misDispositivos;
	    String nombre;
	    JButton btnGuardar = new JButton("Guardar");
	    JButton btnSalir = new JButton("Salir");
	    JPanel jPWebCam = new JPanel();
	    JPanel jPanel1 = new JPanel();
	    JScrollPane jScrollPane1 = new JScrollPane();
	    JTextArea txtInfo = new JTextArea();
	    JTextField txtNombre = new JTextField();
	    
	    public MainCamara(String folio) {
	    	nombre=folio;
	        initComponents();
	        misDispositivos= new Dispositivos(this);
	        btnGuardar.setEnabled(false);
	        setLocationRelativeTo(null);
	        ver();
	    }

	    private void initComponents() {
	    	this.setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/camara_icon&16.png"));
	        this.setTitle("Captura Foto");
	 
	        jPWebCam.setBorder(javax.swing.BorderFactory.createTitledBorder("Wisky!"));
	        jPWebCam.setLayout(new java.awt.BorderLayout());
	        
	        getContentPane().add(jPWebCam, java.awt.BorderLayout.CENTER);

	        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
	        jPanel1.setPreferredSize(new java.awt.Dimension(397, 160));

	        txtInfo.setColumns(20);
	        txtInfo.setRows(5);
	        jScrollPane1.setViewportView(txtInfo);
	        
	        btnSalir.addActionListener(new ActionListener(){
	        	public void actionPerformed(ActionEvent evt){
	        		misDispositivos.salir();
	        		dispose();
	        	}
	        });
	        btnGuardar.addActionListener(new ActionListener(){
	            public void actionPerformed(ActionEvent evt){
	            	 misDispositivos.CapturaFoto(nombre);
	            }
	        });
	        
	        addWindowListener( new java.awt.event.WindowAdapter() {
	        	public void windowClosing(java.awt.event.WindowEvent e ) { 
	        		misDispositivos.salir();
	        		dispose();
	        	} 
	        });

	        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
	        
	        jPanel1.setLayout(jPanel1Layout);
	        jPanel1Layout.setHorizontalGroup(
	        		jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
	            .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
	                .addGap(71, 71, 71)
	                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 76, Short.MAX_VALUE)
	                .addComponent(btnGuardar)
	                .addGap(20, 20, 20)
	                .addComponent(btnSalir)
	                .addGap(10, 10, 10))
	            .addGroup(jPanel1Layout.createSequentialGroup()
	                .addContainerGap()
	                .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE)
	                .addContainerGap()));
	        
	        jPanel1Layout.setVerticalGroup(
	            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
	            .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
	                .addContainerGap()
	                .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE)
	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
	                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
	                    .addComponent(btnGuardar)
	                    .addComponent(btnSalir))
	                .addContainerGap()));
	      
	        getContentPane().add(jPanel1, java.awt.BorderLayout.NORTH);

	        setSize(new java.awt.Dimension(430, 513));
	    }

	    public void ver(){
	    	btnGuardar.setEnabled(true);
	    	infoDispositivo();
	    	try {
				misDispositivos.MuestraWebCam(jPWebCam,"vfw:Microsoft WDM Image Capture (Win32):0","yuv");
			} catch (CannotRealizeException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	    
	    private void infoDispositivo() {
	        txtInfo.setText(misDispositivos.verInfoDispositivos());
	    }
	 
	}
	
	public class Dispositivos {

	    private MainCamara padre;
		private Player player;
		
		public Dispositivos(){
			
		}
		
		public Dispositivos(MainCamara padre){
	        this.padre=padre;
	    }

	   
		@SuppressWarnings("rawtypes")
		public String verInfoDispositivos()
	    {
	      String rpta="";
	      Vector listaDispositivos = null;
	      
	     listaDispositivos = CaptureDeviceManager.getDeviceList();
	     Iterator it = listaDispositivos.iterator();
	      while (it.hasNext())
	      {
	        CaptureDeviceInfo cdi = (CaptureDeviceInfo)it.next();
	        rpta+=cdi.getName()+"\n";
	      }
	      
	      System.out.println(rpta);
	      if(rpta.compareTo("")!=0)
	          rpta="Dispositivos detectados:\n\n"+rpta;
	      else
	          rpta="Sin Dispositivos Detectados";
	      
	      return rpta;
	    }
		public void salir(){
			player.close();
		}
		
		@SuppressWarnings("rawtypes")
		public void detectarDispositivos(JMenu dispositivos)
	    {
	      Vector listaDispositivos = null;
	      listaDispositivos = CaptureDeviceManager.getDeviceList();
	      Iterator it = listaDispositivos.iterator();

	      String nombre="";
	      while (it.hasNext())
	      {
	          CaptureDeviceInfo cdi = (CaptureDeviceInfo)it.next();
	          nombre=cdi.getName(); //cdi.getName() --> Obtiene el nombre del Dispositivo Detectado
	          
	          if(nombre.indexOf("Image")!=-1)
	          {
	              JMenu menuFormato=new JMenu(nombre);
	              JMenuFormato tamanios=null;
	              CaptureDeviceInfo dev = CaptureDeviceManager.getDevice(nombre);
	              Format[] cfmts = dev.getFormats();

	              for(int i=0; i<cfmts.length;i++)
	              {
	                  if(cfmts[i].getEncoding().compareTo("yuv")==0)
	                  {tamanios=new JMenuFormato(cfmts[i].getEncoding()+" "+
	                          ((YUVFormat)cfmts[i]).getSize().width+"x"+
	                          ((YUVFormat)cfmts[i]).getSize().height,
	                          ((YUVFormat)cfmts[i]).getSize().width,
	                          ((YUVFormat)cfmts[i]).getSize().height,
	                          padre,
	                          padre.jPWebCam);
	                  }
	                  else if(cfmts[i].getEncoding().compareTo("rgb")==0)
	                  {tamanios=new JMenuFormato(cfmts[i].getEncoding()+" "+
	                          ((RGBFormat)cfmts[i]).getSize().width+"x"+
	                          ((RGBFormat)cfmts[i]).getSize().height,
	                          ((RGBFormat)cfmts[i]).getSize().width,
	                          ((RGBFormat)cfmts[i]).getSize().height,
	                          padre,
	                          padre.jPWebCam);
	                  }
	                  menuFormato.add(tamanios);
	              }
	              dispositivos.add(menuFormato);
	          }
	      }
	    }

		public void MuestraWebCam(JPanel panelCam,String dispositivo,String FormatoColor) throws IOException, CannotRealizeException {
			if(player != null)
	            return;
	        
	        CaptureDeviceInfo dev = CaptureDeviceManager.getDevice(dispositivo);
	        MediaLocator loc = dev.getLocator();
	        try {
	                player = Manager.createRealizedPlayer(loc);
	                System.out.println(player);
	               
	            } catch (IOException ex) {
	            	System.out.println("Ponga la camara 0");
	            } catch (NoPlayerException ex) {
	            	System.out.println("Ponga la camara 1");
	            } catch (CannotRealizeException ex) { 
	            	System.out.println("Ponga la camara 3");
	            }
	          
	    
	        player.start();
	           
	        try {
	        	
	            Thread.sleep(1000);
	        } catch (InterruptedException ex) { }

	        Component comp;

	        if ((comp = player.getVisualComponent())!= null) {
	          panelCam.add(comp,BorderLayout.CENTER);
	          padre.pack();
	        }
	    }
	    
		public void CapturaFoto(String nombre) {
	    	Image img=null;
	        FrameGrabbingControl fgc = (FrameGrabbingControl)
	        player.getControl("javax.media.control.FrameGrabbingControl");
	        Buffer buf = fgc.grabFrame();
	        BufferToImage btoi = new BufferToImage((VideoFormat)buf.getFormat());
	        img = btoi.createImage(buf);

	        if (img != null) {
	            Integer i = new Integer(JFileChooser.APPROVE_OPTION);
	            if (i != null){
	            	File folder = new File(System.getProperty("user.dir")+"/tmp/tmp_update");
	            	folder.mkdirs();
					String imagen = System.getProperty("user.dir")+"/tmp/tmp_update/"+nombre;
					File imagenArch = new File(imagen);
					String formato = "JPG";
					player.close();
					padre.dispose();
					
					try{
						ImageIO.write((RenderedImage) img,formato,imagenArch);
						ImageIcon tmpIconAux = new ImageIcon(System.getProperty("user.dir")+"/tmp/tmp_update/tmp.jpg");
					    btnFoto.setIcon(new ImageIcon(tmpIconAux.getImage().getScaledInstance(230, 195, Image.SCALE_DEFAULT)));	
					}catch(IOException ioe){
						JOptionPane.showMessageDialog(null,"Error al guardar la imagen", "Error!",JOptionPane.ERROR_MESSAGE);
					}
	            }
	        }
	        else
	        {
	            javax.swing.JOptionPane.showMessageDialog(padre, "A ocurrido un error!!");
	        }
	        img=null;
	    }
	}

//	filtro de horario para asignarcelo al un empleado
	public class Filtro_Horario_Empleado extends JDialog
	{
		Container cont = getContentPane();
		JLayeredPane panel = new JLayeredPane();
		
		JTextField txtNombre = new JTextField();
		JTextField txtFolioHorario = new Componentes().text(new JTextField(), "Folio de Horario", 9, "Int");

		DefaultTableModel modelo = new DefaultTableModel(0,2)	{
			public boolean isCellEditable(int fila, int columna){
				if(columna < 0)
					return true;
				return false;
			}
		};
		
		JTable tabla = new JTable(modelo);
		
		@SuppressWarnings("rawtypes")
		private TableRowSorter trsfiltro;
		
		@SuppressWarnings({ "rawtypes" })
		public Filtro_Horario_Empleado()
		{
			
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/nivG.png"));
			panel.setBorder(BorderFactory.createTitledBorder("Filtro Horario"));	
			
			panel.add(getPanelTabla()).setBounds(20,50,800,400);
			panel.add(txtFolioHorario).setBounds(20,20,80,20);
			panel.add(txtNombre).setBounds(100,20,720,20);
			
			cont.add(panel);
			txtNombre.setToolTipText("Filtro");
			
			trsfiltro = new TableRowSorter(modelo); 
			tabla.setRowSorter(trsfiltro);
			
			txtNombre.addKeyListener(opFiltroNombre);
			txtFolioHorario.addKeyListener(opFiltroFolio);
			
			agregar(tabla);
			
			this.setTitle("Filtro Horario");
			this.setSize(845,500);
			this.setLocationRelativeTo(null);
			this.setResizable(false);
		}
		int x=2;
		private void agregar(final JTable tbl) {
	        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
		        public void mouseClicked(MouseEvent e) {
		        	if(e.getClickCount() == 2){
		        		
		    			int fila = tabla.getSelectedRow();
		    			Object folio =  tabla.getValueAt(fila, 0);
		    			Object horario =  tabla.getValueAt(fila, 1);
		    			
		    			
		    			switch(seleccion_de_asignacion_de_Horario1Horario2Horario3){
			    			case 1: txtHorario.setText(horario+"");
		    						lblFolioHorario1.setText(folio+"");
		    				
		    						txtHorario.setToolTipText(horario+"");
		    						rbHorario.doClick();
		    						break;
			    			case 2: txtHorario2.setText(horario+"");
				    				lblFolioHorario2.setText(folio+"");
				    				
					    			txtHorario2.setToolTipText(horario+"");
					    			rbHorario2.setEnabled(true);
					    			break;
			    			case 3: txtHorario3.setText(horario+"");
				    				lblFolioHorario3.setText(folio+"");
				    				
					    			txtHorario3.setToolTipText(horario+"");
					    			rbHorario3.setEnabled(true);
					    			break;
		    			}
		    			dispose();
		        	}
		        }
	        });
	    }
		
		KeyListener opFiltroFolio = new KeyListener(){
			public void keyReleased(KeyEvent arg0) {
				trsfiltro.setRowFilter(RowFilter.regexFilter(txtFolioHorario.getText(), 0));
			}
			@Override
			public void keyPressed(KeyEvent arg0) {}
			@Override
			public void keyTyped(KeyEvent arg0) {}
		};
		
		KeyListener opFiltroNombre = new KeyListener(){
			public void keyReleased(KeyEvent arg0) {
				trsfiltro.setRowFilter(RowFilter.regexFilter(txtNombre.getText().toUpperCase().trim(), 1));
			}
			public void keyTyped(KeyEvent arg0) {}
			public void keyPressed(KeyEvent arg0) {}		
		};
		
	   	private JScrollPane getPanelTabla()	{		
			new Connexion();
			
			DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
			tcr.setHorizontalAlignment(SwingConstants.CENTER);
			
			tabla.getColumnModel().getColumn(0).setCellRenderer(tcr);
			tabla.getColumnModel().getColumn(1).setCellRenderer(tcr);
			
			// Creamos las columnas.
			tabla.getColumnModel().getColumn(0).setHeaderValue("Folio");
			tabla.getColumnModel().getColumn(0).setMinWidth(80);
			tabla.getColumnModel().getColumn(0).setMinWidth(80);
			tabla.getColumnModel().getColumn(1).setHeaderValue("Nombre");
			tabla.getColumnModel().getColumn(1).setMinWidth(720);
			tabla.getColumnModel().getColumn(1).setMaxWidth(720);
			
			TableCellRenderer render = new TableCellRenderer() 
			{ 
				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, 
				boolean hasFocus, int row, int column) { 
					JLabel lbl = new JLabel(value == null? "": value.toString());
					if(row%2==0){
							lbl.setOpaque(true); 
							lbl.setBackground(new java.awt.Color(177,177,177));
					} 
				return lbl; 
				} 
			}; 
			tabla.getColumnModel().getColumn(0).setCellRenderer(render); 
			tabla.getColumnModel().getColumn(1).setCellRenderer(render); 

			Statement s;
			ResultSet rs;
			try {
				s = new Connexion().conexion().createStatement();
				rs = s.executeQuery( "  select tb_horarios.folio,tb_horarios.nombre from tb_horarios");
				int folio;
				String nombre;
				
				while (rs.next())
				{ 
					folio= rs.getInt(1);
					nombre= rs.getString(2).trim();
					
				   String [] fila = new String[2];
				   fila[0] = folio+"";
				   fila[1] = nombre;
				   
				   modelo.addRow(fila); 
				}	
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			 JScrollPane scrol = new JScrollPane(tabla);
			   
		    return scrol; 
		}
	}

	public class Cat_Filtro_Empleado extends JDialog {
		
		Container cont = getContentPane();
		JLayeredPane campo = new JLayeredPane();
		
		DefaultTableModel model = new DefaultTableModel(0,9){
			public boolean isCellEditable(int fila, int columna){
				if(columna < 0)
					return true;
				return false;
			}
		};
		
		JTable tabla = new JTable(model);
		
		@SuppressWarnings("rawtypes")
		private TableRowSorter trsfiltro;
		
		JTextField txtFolioFiltroEmpleado = new JTextField();
		JTextField txtNombre_Completo = new JTextField();
		
		String establecimientos[] = new Obj_Establecimiento().Combo_Establecimiento();
		@SuppressWarnings("rawtypes")
		JComboBox cmbEstablecimientos = new JComboBox(establecimientos);

		@SuppressWarnings("rawtypes")
		public Cat_Filtro_Empleado(){
			
			this.setModal(true);
			
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/filter_icon&16.png"));
			this.setTitle("Filtro de Empleados");
			campo.setBorder(BorderFactory.createTitledBorder("Filtro De Empleado"));
			trsfiltro = new TableRowSorter(model); 
			tabla.setRowSorter(trsfiltro);  
			
			campo.add(getPanelTabla()).setBounds(15,42,1000,565);
			
			campo.add(txtFolioFiltroEmpleado).setBounds(15,20,48,20);
			campo.add(txtNombre_Completo).setBounds(64,20,229,20);
			campo.add(cmbEstablecimientos).setBounds(295,20, 148, 20);
			
			agregar(tabla);
			
			cont.add(campo);
			
			txtFolioFiltroEmpleado.addKeyListener(opFiltroFolio);
			txtNombre_Completo.addKeyListener(opFiltroLoco);
			cmbEstablecimientos.addActionListener(opFiltro);
			
			this.setSize(1040,650);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			
//          asigna el foco al JTextField del nombre deseado al arrancar la ventana
            this.addWindowListener(new WindowAdapter() {
                    public void windowOpened( WindowEvent e ){
                    	txtNombre_Completo.requestFocus();
                 }
            });
              
//         pone el foco en el txtFolio al presionar la tecla scape
              getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                 KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "foco");
              
              getRootPane().getActionMap().put("foco", new AbstractAction(){
                  @Override
                  public void actionPerformed(ActionEvent e)
                  {
                	  txtNombre_Completo.setText("");
                      txtNombre_Completo.requestFocus();
                  }
              });
              
              tabla.addKeyListener(seleccionEmpleadoconteclado);
              
//            pone el foco en la tabla al presionar f4
              getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                 KeyStroke.getKeyStroke(KeyEvent.VK_F4 , 0), "dtabla");
              
              getRootPane().getActionMap().put("dtabla", new AbstractAction(){
                  @Override
                  public void actionPerformed(ActionEvent e)
                  {
                	tabla.requestFocus();
                	iniciarSeleccionConTeclado();
                  }
              });
              
              
				KeyStroke tab = KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0);
				tabla.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(tab, "TAB");
				
				tabla.getActionMap().put("TAB", new AbstractAction(){
	                 public void actionPerformed(ActionEvent e)
	                 {
	                	iniciarSeleccionConTeclado();
	                	
	                 }
	            });
			 
			
		}
		private void agregar(final JTable tbl) {
	        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
		        public void mouseClicked(MouseEvent e) {
		        	
		        	if(e.getClickCount() == 1){
		        		iniciarSeleccionConTeclado();
		        	}
		        	if(e.getClickCount() == 2){
		    			fila = tabla.getSelectedRow();
		    			Object folio =  tabla.getValueAt(fila, 0).toString().trim();
		    			dispose();
		    			txtFolioEmpleado.setText(folio+"");
		    			btnBuscar.doClick();
		        	}
		        }
	        });
	    }
		
		int fila=0;
		public void iniciarSeleccionConTeclado(){
			Robot robot;
			try {
	            robot = new Robot();
	            robot.keyPress(KeyEvent.VK_A);
	            robot.keyRelease(KeyEvent.VK_A);
	        } catch (AWTException e) {
	            e.printStackTrace();
	        }
 	     };
 	     
		KeyListener seleccionEmpleadoconteclado = new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				
				KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
				tabla.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(enter, "Enter");
				
				tabla.getActionMap().put("Enter", new AbstractAction(){
	                 public void actionPerformed(ActionEvent e)
	                 {
	                	iniciarSeleccionConTeclado();
	                	
	                	fila=tabla.getSelectedRow();
	     				String folio = tabla.getValueAt(fila,0).toString().trim();
	     					
	     				txtFolioEmpleado.setText(folio);
	     				btnBuscar.doClick();
	     				dispose();
	                 }
	            });
			}
			public void keyPressed(KeyEvent e){}
			public void keyReleased(KeyEvent e){}
		};
		
		KeyListener opFiltroFolio = new KeyListener(){
			public void keyReleased(KeyEvent arg0) {
				trsfiltro.setRowFilter(RowFilter.regexFilter(txtFolioFiltroEmpleado.getText(), 0));
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
		
		KeyListener opFiltroLoco = new KeyListener(){
			public void keyReleased(KeyEvent arg0) {
				
				new Obj_Filtro_Dinamico(tabla,"Nombre Completo", txtNombre_Completo.getText().toUpperCase(),"Establecimiento",cmbEstablecimientos.getSelectedItem()+"", "", "", "", "");
			}
			public void keyTyped(KeyEvent arg0) {}
			public void keyPressed(KeyEvent arg0) {}		
		};
		
		ActionListener opFiltro = new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				new Obj_Filtro_Dinamico(tabla,"Nombre Completo", txtNombre_Completo.getText().toUpperCase(),"Establecimiento",cmbEstablecimientos.getSelectedItem()+"", "", "", "", "");
			}
		};
		
	   	private JScrollPane getPanelTabla()	{		
	   		this.tabla.getTableHeader().setReorderingAllowed(false) ;
			DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
			tcr.setHorizontalAlignment(SwingConstants.CENTER);

			tabla.getColumnModel().getColumn(0).setHeaderValue("Folio");
			tabla.getColumnModel().getColumn(0).setMaxWidth(50);
			tabla.getColumnModel().getColumn(0).setMinWidth(50);
			tabla.getColumnModel().getColumn(1).setHeaderValue("Nombre Completo");
			tabla.getColumnModel().getColumn(1).setMaxWidth(230);
			tabla.getColumnModel().getColumn(1).setMinWidth(230);
			tabla.getColumnModel().getColumn(2).setHeaderValue("Establecimiento");
			tabla.getColumnModel().getColumn(2).setMaxWidth(150);
			tabla.getColumnModel().getColumn(2).setMinWidth(150);
			tabla.getColumnModel().getColumn(3).setHeaderValue("Puesto");
			tabla.getColumnModel().getColumn(3).setMaxWidth(180);
			tabla.getColumnModel().getColumn(3).setMinWidth(180);
			tabla.getColumnModel().getColumn(4).setHeaderValue("Sueldo");
			tabla.getColumnModel().getColumn(4).setMaxWidth(70);
			tabla.getColumnModel().getColumn(4).setMinWidth(70);
			tabla.getColumnModel().getColumn(5).setHeaderValue("Bono");
			tabla.getColumnModel().getColumn(5).setMaxWidth(70);
			tabla.getColumnModel().getColumn(5).setMinWidth(70);
			tabla.getColumnModel().getColumn(6).setHeaderValue("Status");
			tabla.getColumnModel().getColumn(6).setMaxWidth(120);
			tabla.getColumnModel().getColumn(6).setMinWidth(120);
			tabla.getColumnModel().getColumn(7).setHeaderValue("F Sodas");
			tabla.getColumnModel().getColumn(7).setMaxWidth(50);
			tabla.getColumnModel().getColumn(7).setMinWidth(50);
			tabla.getColumnModel().getColumn(8).setHeaderValue("Gafete");
			tabla.getColumnModel().getColumn(8).setMaxWidth(50);
			tabla.getColumnModel().getColumn(8).setMinWidth(50);
			
			tabla.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12)); 
			tabla.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
			tabla.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
			tabla.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
			tabla.getColumnModel().getColumn(4).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12));
			tabla.getColumnModel().getColumn(5).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12));
			tabla.getColumnModel().getColumn(6).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
			tabla.getColumnModel().getColumn(7).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12));
			tabla.getColumnModel().getColumn(8).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
			
			
			Statement s;
			ResultSet rs;
			try {
				s = new Connexion().conexion().createStatement();
				rs = s.executeQuery("exec sp_filtro_empleado");
				
				while (rs.next())
				{ 
				   String [] fila = new String[9];
				   fila[0] = rs.getString(1)+"  ";
				   fila[1] = "   "+rs.getString(2);
				   fila[2] = "   "+rs.getString(3).trim();
				   fila[3] = "   "+rs.getString(4).trim();
				   fila[4] = rs.getString(5).trim();
				   fila[5] = rs.getString(6).trim();
				   fila[6] = "   "+rs.getString(7).trim();
				   fila[7] = rs.getString(8).trim();
				   fila[8] = rs.getString(9).trim();
				
				   model.addRow(fila); 
				}	
			} catch (SQLException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null,"Error Al Abrir El Filtro De El Empleado Error en El Procedimiento sp_filtro_empleado"+e1, "Avisa Al Administrador Del Sistema!",JOptionPane.ERROR_MESSAGE);
			}
			 JScrollPane scrol = new JScrollPane(tabla);
			   
		    return scrol; 
		}
	}

	
	public class JMenuFormato extends JMenuItem implements ActionListener{
	    private int ancho;
	    private int alto;
	    @SuppressWarnings("unused")
		private JPanel modificable;
	    private MainCamara padre;

	    public JMenuFormato(String etiqueta,int ancho,int alto,MainCamara Padre,JPanel modificable)
	    {
	        super(etiqueta);
	        this.modificable=modificable;
	        this.ancho=ancho;
	        this.alto=alto;
	        this.addActionListener(this);
	        this.padre=Padre;
	    }

	    public void actionPerformed(ActionEvent e) {
	        padre.setSize(ancho, alto+200);
	    }
	}
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Empleados().setVisible(true);
		}catch(Exception e){	}
	}
	
	public class Cat_Reporte_De_Motivos_De_renuncia extends JFrame{
		
		Container cont = getContentPane();
		JLayeredPane panel = new JLayeredPane();
		
		JButton btnMotivosDeRenuncia = new JCButton("Concentrado De Renuncias", "buscar.png", "Azul");
		JButton btnObservacionDeMotivos = new JCButton("Observacion De Motivos De Renuncia", "buscar.png", "Azul");
		
		JDateChooser fchInicial = new JDateChooser();
		JDateChooser fchFinal = new JDateChooser();
		
		JButton btngenerar = new JCButton("Generar", "buscar.png", "Azul");
//				new JButton("*Generar",new ImageIcon("imagen/buscar.png"));
		
		public Cat_Reporte_De_Motivos_De_renuncia(){
			this.setTitle("Consulta De Motivos De Renuncia");
			this.setSize(360,200);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			this.setResizable(false);
			setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/cesta-de-la-compra-verde-icono-9705-64.png"));
			panel.setBorder(BorderFactory.createTitledBorder("Seleccione El Tipo De Reporte Que Desee Generar"));
			
			int x=15,y=20;
			
			panel.add(btnMotivosDeRenuncia).setBounds(x,y,330,20);
			panel.add(btnObservacionDeMotivos).setBounds(x,y+=25,330,20);
			
			panel.add(new JLabel("Del: ")).setBounds(x+10,y+=25,80,20);
			panel.add(fchInicial).setBounds(x+55,y,110,20);
			
			panel.add(new JLabel("Al: ")).setBounds(x+185,y,80,20);
			panel.add(fchFinal).setBounds(x+210,y,110,20);
			
			panel.add(btngenerar).setBounds(x+125,y+=35,100,20);
			
			cont.add(panel);
			btngenerar.addActionListener(opGenerar);
			
			fchInicial.setDate(cargar_fechas(7));
			fchFinal.setDate(cargar_fechas(0));
			
			btnMotivosDeRenuncia.addActionListener(opMotivos);
			btnObservacionDeMotivos.addActionListener(opObservaciones);
			
		}
		
		String bandera = "";
		ActionListener opMotivos = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bandera = "MOTIVOS";
				btnMotivosDeRenuncia.setEnabled(false);
				btnObservacionDeMotivos.setEnabled(true);
			}
		};
		
		ActionListener opObservaciones = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bandera = "OBSERVACIONES";
				btnMotivosDeRenuncia.setEnabled(true);
				btnObservacionDeMotivos.setEnabled(false);
			}
		};
		
		ActionListener opGenerar = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				String basedatos="2.26";
				String vista_previa_reporte="no";
				int vista_previa_de_ventana=0;
				
				String comando="";
				String reporte = "";
				
				String fechaIn = new SimpleDateFormat("dd/MM/yyyy").format(fchInicial.getDate())+" 00:00:00";
				String fechaFin = new SimpleDateFormat("dd/MM/yyyy").format(fchFinal.getDate())+"  23:59:00";
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm"); 
				
				  Date fecha1 = sdf.parse(fechaIn , new ParsePosition(0));
				  Date fecha2 = sdf.parse(fechaFin , new ParsePosition(0));
				
				if(!bandera.equals("")){
					if(bandera.equals("MOTIVOS")){
						comando="exec sp_select_concentrado_de_motivos_de_renuncia '"+fechaIn+"','"+fechaFin+"','"+new Obj_Usuario().LeerSession().getNombre_completo()+"'";
						reporte = "Obj_Reporte_De_Motivos_De_Renuncia.jrxml";
					}else{
						comando="exec sp_select_Observacion_de_motivos_de_renuncia '"+fechaIn+"','"+fechaFin+"','"+new Obj_Usuario().LeerSession().getNombre_completo()+"'";
						reporte = "Obj_Motivos_De_Renuncia.jrxml";
					}
					
					try {
						
						if(fecha1.before(fecha2)){
							new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
						}else{
							JOptionPane.showMessageDialog(null,"El Rango de Fechas Esta Invertido","Aviso!", JOptionPane.WARNING_MESSAGE);
							return;
						}
					 
					} catch (Exception e1) {
						System.out.println(e1.getMessage());
						JOptionPane.showMessageDialog(null, "Error En Cat_Reporte_De_Motivos_De_renuncia ", "Error !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
					}
					
					
				}else{
			  	  	JOptionPane.showMessageDialog(null, "Es Necesario Que Seleccione Un Tipo De Reporte","Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
			  	  	return;
				}
				
//					          return;
//							}else{
////								Cat_Reporte_De_Ausentismo(anio+"",mes+"");
////								Cat_Reporte_De_Valor_De_Nomina(anio+"",mes+"");
//							}
//				}else{
//			  	  	JOptionPane.showMessageDialog(null, "Solo Se Pueden Consultar El Reporte De Ausentismo A Partir del Mes De Marzo Del 2016","Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
//			  	  	return;
//				}
		 	}
		};
		
		public Date cargar_fechas(int dias){
			Date date1 = null;
					  try {
						date1 = new SimpleDateFormat("dd/MM/yyyy").parse(new BuscarSQL().fecha(dias));
					} catch (ParseException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				
			return date1;
		};
		   
//		@SuppressWarnings({ "rawtypes", "unchecked" })
//		public static void Cat_Reporte_De_Ausentismo(String anio,String mes) {
//				
//			try {
//				JasperReport report = JasperCompileManager.compileReport(System.getProperty("user.dir")+"\\src\\Obj_Reportes\\Obj_Reporte_De_Ausentismo_En_Lista_De_Raya.jrxml");
//				
//				Map parametro = new HashMap();
//				parametro.put("anio", anio);
//				parametro.put("mes", mes);
//				
//				JasperPrint print = JasperFillManager.fillReport(report, parametro, new Connexion().conexion());
//				JasperViewer.viewReport(print, false);
//			} catch (Exception e) {
//				System.out.println(e.getMessage());
//				JOptionPane.showMessageDialog(null, "Error En Cat_Reporte_De_Corte_De_Caja ", "Error !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
//			}
//		}
		
		
//		public static void Cat_Reporte_De_Valor_De_Nomina(String anio,String mes) {
//				
//			try {
//				
//				String basedatos="2.26";
//				String vista_previa_reporte="no";
//				int vista_previa_de_ventana=0;
//				
//				String comando="exec sp_select_valor_de_nomina '"+anio+"','"+mes+"'";
//				String reporte = "Obj_Reporte_De_Valor_De_Nomina.jrxml";
//				new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
//				 
//			} catch (Exception e) {
//				System.out.println(e.getMessage());
//				JOptionPane.showMessageDialog(null, "Error En Cat_Reporte_De_Corte_De_Caja ", "Error !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
//			}
//		}
	}

	
}