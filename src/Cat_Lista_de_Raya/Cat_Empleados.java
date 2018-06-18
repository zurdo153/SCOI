package Cat_Lista_de_Raya;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Container;
import java.awt.Event;
import java.awt.Font;
import java.awt.Image;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Cat_Checador.Cat_Horarios;
import Cat_Reportes.Cat_Reporte_De_Ausentismo_En_Lista_De_Raya;
import Cat_Reportes.Cat_Reportes_De_Altas_y_Bajas_En_Un_Rango_De_Fechas;
import Cat_Reportes.Cat_Reportes_De_Cortes_De_Lista_De_Raya_Actual;
import Cat_Reportes.Cat_Reporte_De_Cumpleanios_Del_Mes;
import Cat_Reportes.Cat_Reportes_Datos_Checador;
import Cat_Reportes.Cat_Personal_Con_Horario;
import Cat_Reportes.Cat_Reporte_De_Asistencia_Por_Empleado;
import Cat_Reportes.Cat_Reportes_De_Contratacion_Por_Empleado;
import Cat_Reportes.Cat_Reportes_De_Horarios;
import Cat_Reportes.Cat_Reportes_De_Informacion_De_Movimientos_De_Colaboradores;
import Conexiones_SQL.ActualizarSQL;
import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.BuscarTablasModel;
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
import Obj_Lista_de_Raya.Obj_Perfil_De_Puestos;
import Obj_Lista_de_Raya.Obj_Puestos;
import Obj_Lista_de_Raya.Obj_Rango_De_Prestamos;
import Obj_Lista_de_Raya.Obj_Sueldos;
import Obj_Lista_de_Raya.Obj_Tipo_De_Bancos;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_Filtro_Dinamico;
import Obj_Principal.Obj_Filtro_Dinamico_Plus;
import Obj_Principal.Obj_tabla;
import Obj_Renders.tablaRenderer;

import com.digitalpersona.onetouch.DPFPDataPurpose;
import com.digitalpersona.onetouch.DPFPFeatureSet;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPSample;
import com.digitalpersona.onetouch.DPFPTemplate;
import com.digitalpersona.onetouch.capture.DPFPCapture;
import com.digitalpersona.onetouch.capture.event.DPFPDataAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPDataEvent;
import com.digitalpersona.onetouch.capture.event.DPFPErrorAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPErrorEvent;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusEvent;
import com.digitalpersona.onetouch.capture.event.DPFPSensorAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPSensorEvent;
import com.digitalpersona.onetouch.processing.DPFPEnrollment;
import com.digitalpersona.onetouch.processing.DPFPFeatureExtraction;
import com.digitalpersona.onetouch.processing.DPFPImageQualityException;
import com.digitalpersona.onetouch.verification.DPFPVerification;
import com.digitalpersona.onetouch.verification.DPFPVerificationResult;
import com.toedter.calendar.JDateChooser;

import Cat_Camaras.Cat_Camara;

@SuppressWarnings({ "serial", "unchecked" })
public class Cat_Empleados extends JFrame{
	
	byte[] imagB = null;
	
	Object[][] tablacompleta = null;
	
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
	
	JButton btnLimpiarPerfil  = new JButton(".");
	JButton btnAgregarPerfil   = new JButton(".");
	JLabel lblFolioPerfil = new JLabel("");
	JTextField txtPerfil = new Componentes().text(new JTextField(), "Perfil De Puesto", 250, "String");
	
	JPasswordField txtChecador = new Componentes().textPassword(new JPasswordField(), "Contraseña del Checador", 100);

	JTextField txtFolioEmpleado = new Componentes().text( new JCTextField(), "Folio Colaborador", 9, "Int");
	JTextField txtNombre = new Componentes().text( new JTextField(), "Nombre de Empleado", 70, "String");
	JTextField txtApPaterno = new Componentes().text( new JTextField(), "Apellido Paterno", 20, "String");
	JTextField txtApMaterno = new Componentes().text( new JTextField(), "Apellido Materno", 20, "String");
	JTextField txtFechaActualizacion = new JTextField(new SimpleDateFormat("dd/MM/yyyy").format((new Date())));
	
	JTextField txtTelefono_Familiar = new Componentes().text( new JTextField(), "Teléfono Familiar", 10, "Int");
	JTextField txtTelefono_Propio = new Componentes().text( new JTextField(), "Teléfono Propio", 10, "Int");
	JTextField txtTelefono_Cuadrante = new JTextField();  
	JTextField txtPoblacion = new Componentes().text( new JTextField(), "Población", 20, "String");
	JTextField txtRFC = new Componentes().text(new JTextField(), "RFC", 25, "String");
	JTextField txtCurp = new Componentes().text(new JTextField(), "Curp", 25, "String");  
	JTextField txtBaja = new JTextField();
	JTextField txtColonia = new Componentes().text( new JTextField(), "Colonia", 30, "String");
	JTextField txtCalle = new Componentes().text( new JTextField(), "Calle", 30, "String");
	
	JTextField txtemailEmpresa           = new Componentes().text(new JCTextField(),"@email empresa", 150, "String");
	JTextField txtemailPersonal          = new Componentes().text(new JCTextField(),"@email personal", 150, "String");
	JTextField txtDescanso               = new Componentes().text(new JCTextField(),"Dia De Descanso", 100, "String");
	JTextField txtDobla                  = new Componentes().text(new JCTextField(),"Dia Dobla", 100, "String");
	JTextField txtFechaUltimasVacaciones = new Componentes().text(new JCTextField(),"Ultimas Vaciones", 100, "String");
	JTextField txtFechaIncapacidad       = new JTextField();
	
	JTextField txtImss                   = new Componentes().text(new JCTextField(),"Número de Seguro Social", 11, "Int");
	JTextField txtNumeroInfonavit        = new Componentes().text(new JCTextField(), "Número de Infonavit", 15, "Int");
	JTextField txtHorario                = new Componentes().text(new JCTextField(), "Horario Principal Asignado",300, "String");
	JTextField txtHorario2               = new Componentes().text(new JCTextField(), "Horario Secundario Asignado",300, "String");
	JTextField txtHorario3               = new Componentes().text(new JCTextField(), "Horario Terciario Asignado",300, "String");
	
	JTextField txtSalarioDiario          = new Componentes().text(new JCTextField(), "Salario Diario"          , 15, "Double");
	JTextField txtSalarioDiarioIntegrado = new Componentes().text(new JCTextField(), "Salario Diario Integrado", 15, "Double");
	
	JTextField txtInfonavit              = new Componentes().text(new JCTextField(), "Descuento a Infonavit"   , 15, "Double");
	JTextField txtDInfonacot             = new Componentes().text(new JCTextField(), "Infonacot"               , 15, "Double");
	JTextField txtPensionAli             = new Componentes().text(new JCTextField(), "Pension Alimenticia"     , 15, "Double");
	JTextField txtTarjetaNomina          = new Componentes().text(new JCTextField(), "Cuenta de Nómina"       , 19, "Int"   );
	JTextField txtultimousuariomod       = new Componentes().text(new JCTextField(), "Último Usuario Actualizó",300, "String");
	JTextField txtFormaDePago            = new Componentes().text(new JCTextField(), "Forma de Pago"           , 15, "String");
	
//	JToggleButton btnTrueFoto = new JToggleButton("Para actualizar la foto Presiona aquí !!!");
	JToggleButton btnTrueFoto = new JToggleButton("Actualizar Foto !!!");
	
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
	
	JCButton btnBuscar    = new JCButton("Buscar","buscar.png","Azul");
	JCButton btnFiltro    = new JCButton("Filtro","Filter-List-icon16.png","Azul");
	JCButton btnNuevo     = new JCButton("Nuevo","Nuevo.png","Azul");
	JCButton btnEditar    = new JCButton("Editar","editara.png","Azul");
	JCButton btnSalir     = new JCButton("Salir","salir16.png","Azul");
	JCButton btnGuardar   = new JCButton("Guardar","Guardar.png","Azul");
	JCButton btnDeshacer  = new JCButton("Deshacer","deshacer16.png","Azul");
	JCButton btnVerificar = new JCButton("Verificar Nombre Del Nuevo Colaborador","","AzulC");
	
	JCButton btnHorario    = new JCButton("","buscar.png","Azul");
	JCButton btnHorario2   = new JCButton("","buscar.png","Azul");
	JCButton btnHorario3   = new JCButton("","buscar.png","Azul");
	JCButton btnHorarioNew = new JCButton("","Filter-List-icon16.png","AzulO");
	
	JCButton btnLimpiarH2   = new JCButton("","goma-de-borrar.png","AzulO");
	JCButton btnLimpiarH3 = new JCButton("","goma-de-borrar.png","AzulO");
	
	JButton btnFechaUltimasVacaciones = new JButton();
	JButton btnFechaIncapacidad = new JButton();
	JButton btnFiniquito  = new JButton();
	
	JButton btnFoto = new JButton();
	JButton btnStatus = new JButton();
//	JButton btnExaminar = new JButton("Examinar");
	JCButton btnCamara = new JCButton("320 x 240","camara_icon&16.png","Azul");

	JCButton btnContratacion        = new JCButton("Contratacion","contrato-de-acuerdo-de-acuerdo-de-la-mano-encuentros-socio-icono-7428-16.png","AzulO");
	JCButton btnDocumentacion       = new JCButton("Documentación","carpeta-de-correo-icono-4002-16.png","AzulO");
	JCButton btnEncuentaDeSalida    = new JCButton("Encuesta De Salida","Lista.png","AzulO");
	JCButton btnBeneficiario        = new JCButton("Beneficiario","Usuario.png","AzulO");
	
	JCButton btnIncontratables      = new JCButton("No Contratables","tarjeta-de-informacion-del-usuario-icono-7370-32.png","Azul");
	JCButton btnLicencias           = new JCButton("Licencias","camion-icono-6226-32.png","Azul");
	JCButton btnCumpleaños_del_Mes  = new JCButton("Cumpleaños","pastel-icono-5242-32.png","Azul");
	JCButton btnAusentismo		    = new JCButton("Ausentismo","inasistencia32.png","Azul");
	JCButton btn_Adeudo        		= new JCButton("Adeudos","deuda.png","Azul");
	JCButton btnReporteSalida		= new JCButton("Encuesta De Salida","mail-replylist-icono-7882-32.png","Azul");
	JCButton btnReporteRenuncia		= new JCButton("Renuncia","baja32.png","Azul");
	JCButton btnImp_Datos_Completos = new JCButton("Datos Colaborador","informacion-del-usuario-icono-8370-32.png","Azul");
	
	JCButton btnAltasBajas          = new JCButton("Rotacion","arrow1_405291532.png","Azul");
	JCButton btnAsistencia_Empleado = new JCButton("Asistencia","archivo-icono-8809-16.png","Azul"); 
	JCButton btnCortes              = new JCButton("Cortes","dinero-icono-8797-32.png","Azul"); 
	JCButton btn_plantilla          = new JCButton("Plantilla","plan-icono-5073-16.png","Azul");
	JCButton btn_R_horarios         = new JCButton("Horarios","reloj-icono-9211-32.png","Azul");
	JCButton btn_Huellas         	= new JCButton("Datos Checador","huella_cargada_32.png","Azul");
	
	JCButton btn_movimientos        = new JCButton("Percepcion y Deducciones","percepciones_y_deducciones32.png","Azul");
	
	JTextArea txaObservaciones = new Componentes().textArea(new JTextArea(), "Observaciones", 980);
	JScrollPane Observasiones = new JScrollPane(txaObservaciones);
	
	JDateChooser txtFechaNacimiento = new Componentes().jchooser(new JDateChooser()  ,"",0);
	JDateChooser txtIngreso = new Componentes().jchooser(new JDateChooser()  ,"",0);
	JDateChooser txtIngresoImss = new Componentes().jchooser(new JDateChooser()  ,"",0);
	JDateChooser txtVencimientoLicencia = new Componentes().jchooser(new JDateChooser()  ,"",0);
	

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
	 
	String statusChecador[] = {"NORMAL","LIBRE","CHECADOR BLOQUEADO","EXCLUSIVO RUTA"};
	@SuppressWarnings("rawtypes")
	JComboBox cmbStatusChecador = new JComboBox(statusChecador);
	
	String checaCon[] = {"SELECCIONA UNA FORMA","GAFETE","GAFETE Y HUELLA"};
	@SuppressWarnings("rawtypes")
	JComboBox cmbChecaCon = new JComboBox(checaCon);
	
	//declaracion de Bordes
	Border blackline, etched, raisedbevel, loweredbevel, empty;
//	TitledBorder title4;
	
	int seleccion_de_asignacion_de_Horario1Horario2Horario3;
	
	JCButton btnHuella = new JCButton("Cargar Huella", "", "Rojo");
	
	ByteArrayInputStream datosHuella = null;
	ByteArrayInputStream datosHuella2 = null;
	int tamañoHuella = 0;
	int tamañoHuella2 = 0;
	
	
	Obj_Empleados re = new Obj_Empleados();
	
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
		
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/usuario-grupo-icono-5183-64.png"));
		this.setTitle("Alta de Empleados");
		
		this.panel.setBorder(BorderFactory.createTitledBorder(blackline, "Datos del Colaborador"));
		this.panelReporte.setBorder(BorderFactory.createTitledBorder(blackline, "Reportes de Colaboradores"));
		
//		asignacion de bordes
		this.lblDatosPersonales.setBorder(BorderFactory.createTitledBorder(blackline,"Datos Personales"));
		this.btnFoto.setBorder(blackline);
		this.lblLaboral.setBorder(BorderFactory.createTitledBorder(blackline, "Laboral"));
		this.lblPercepciones.setBorder(BorderFactory.createTitledBorder(blackline,"Percepciones y Deducciones"));
		
		this.btnVerificar.setToolTipText("Verificar Nombre");
		
		this.btnLimpiarPerfil.setToolTipText("Limpiar Perfil");
		this.btnAgregarPerfil.setToolTipText("Agregar Perfil");
		
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
		
		int x = 11, y=20, ancho=140,width=190,height=35,sep=202;
		
		panelReporte.add(btnAsistencia_Empleado).setBounds  (x	   ,y    ,width,height);
	    panelReporte.add(btn_R_horarios).setBounds          (x+=sep,y    ,width,height);
		panelReporte.add(btn_movimientos).setBounds  		(x+=sep,y    ,width,height);
		panelReporte.add(btn_Adeudo).setBounds           	(x+=sep,y    ,width,height);
		panelReporte.add(btnCortes).setBounds               (x+=sep,y    ,width,height);

		x=10;
		panelReporte.add(btnImp_Datos_Completos).setBounds  (x     ,y+=45,width,height);
		panelReporte.add(btn_plantilla).setBounds         	(x+=sep,y    ,width,height);
		panelReporte.add(btnIncontratables).setBounds       (x+=sep,y    ,width,height);
		panelReporte.add(btnLicencias).setBounds           	(x+=sep,y    ,width,height);
		panelReporte.add(btnCumpleaños_del_Mes).setBounds	(x+=sep,y    ,width,height);
		
		x=10;
		panelReporte.add(btnAusentismo).setBounds           (x     ,y+=45,width,height);
		panelReporte.add(btnReporteSalida).setBounds   		(x+=sep,y    ,width,height);
		panelReporte.add(btnReporteRenuncia).setBounds      (x+=sep,y    ,width,height);
		panelReporte.add(btnAltasBajas).setBounds       	(x+=sep,y    ,width,height);
		panelReporte.add(btn_Huellas).setBounds	(x+=sep,y    ,width,height);
		
		x = 10;y = 20;height=20;width=170;sep=190;
		panel.add(btnContratacion).setBounds                (x	   ,y    ,width,height);
		panel.add(btnDocumentacion).setBounds               (x+=sep,y    ,width,height);
		panel.add(btnEncuentaDeSalida).setBounds            (x+=sep,y    ,width,height);
		panel.add(btnBeneficiario).setBounds                (x+=sep,y    ,width,height);		
		
		x=20; y=y+=38;sep=202;
//Datos personales ----------------------------------------------------------------------------------------------------------------------------		
		panel.add(lblDatosPersonales).setBounds(10,y-15,997,190);
		panel.add(new JLabel("Folio:")).setBounds(x,y,ancho,20);
		panel.add(txtFolioEmpleado).setBounds(ancho-60,y,ancho-15,20);
		
		panel.add(btnBuscar).setBounds(ancho*2-67,y,100,20);
		panel.add(btnFiltro).setBounds(ancho*2+35,y,100,20);
	
		panel.add(btnFoto).setBounds(ancho*6,y,ancho+15,120);
		panel.add(btnTrueFoto).setBounds(ancho*6, y+120,ancho+15,20);
		panel.add(btnCamara).setBounds(ancho*6, y+140,ancho+15,20);
		
		panel.add(new JLabel("Nombre:")).setBounds(x,y+=25,ancho,20);
		panel.add(txtNombre).setBounds(ancho-60,y,ancho-15,20);
		panel.add(new JLabel("Ap. Paterno:")).setBounds(220,y,ancho,20);
		panel.add(txtApPaterno).setBounds((ancho*2)+10,y,ancho-15,20);
		panel.add(new JLabel("Ap. Materno:")).setBounds(430,y,ancho,20);
		panel.add(txtApMaterno).setBounds((ancho*3)+90,y,ancho-15,20);
				
		panel.add(new JLabel("Sexo: ")).setBounds(670,y,ancho,20);
		panel.add(cmbSexo).setBounds((ancho*5)+10,y,ancho-15,20);
		
		panel.add(btnVerificar).setBounds(x,y+=25, 335, 20);
		panel.add(new JLabel("F. Nacimiento:")).setBounds(430,y,ancho,20);
		panel.add(txtFechaNacimiento).setBounds((ancho*3)+90,y,ancho-15,20);

		panel.add(new JLabel("Estado Civil: ")).setBounds(650,y,ancho,20);
		panel.add(cmbEstadoCivil).setBounds((ancho*5)+10,y,ancho-15,20);
		
		panel.add(new JLabel("Calle y N°:")).setBounds(x,y+=25,ancho,20);
		panel.add(txtCalle).setBounds(ancho-60,y,ancho-15,20);
		
		panel.add(new JLabel("Colonia:")).setBounds(220,y,ancho,20);
		panel.add(txtColonia).setBounds((ancho*2)+10,y,ancho-15,20);
		
		panel.add(new JLabel("Poblacion:")).setBounds(430,y,ancho,20);
		panel.add(txtPoblacion).setBounds((ancho*3)+90,y,ancho-15,20);
		
		panel.add(new JLabel("T. Sangre: ")).setBounds(650,y,ancho,20);
		panel.add(cmbTipoDeSangre).setBounds((ancho*5)+10,y,ancho-15,20);

		panel.add(new JLabel("Tel. Familiar:")).setBounds(x,y+=25,ancho,20);
		panel.add(txtTelefono_Familiar).setBounds(ancho-60,y,ancho-15,20);
		panel.add(new JLabel("Tel. Propio:")).setBounds(220,y,ancho,20);
		panel.add(txtTelefono_Propio).setBounds((ancho*2)+10,y,ancho-15,20);
		panel.add(new JLabel("Tel. Cuadrante:")).setBounds(430,y,ancho,20);
		panel.add(txtTelefono_Cuadrante).setBounds((ancho*3)+90,y,ancho-15,20);
				
		panel.add(new JLabel("Escolaridad: ")).setBounds(650,y,ancho,20);
		panel.add(cmbEscolaridad).setBounds((ancho*5)+10,y,ancho-15,20);
		
		panel.add(new JLabel("RFC:")).setBounds(x,y+=25,ancho,20);
		panel.add(txtRFC).setBounds(ancho-60,y,ancho-15,20);
		
		panel.add(new JLabel("Curp:")).setBounds(220,y,ancho,20);
		panel.add(txtCurp).setBounds((ancho*2)+10,y,ancho-15,20);
		
		panel.add(new JLabel("Email Personal:")).setBounds      (x+=410      ,y     ,width   ,height );
		panel.add(txtemailPersonal).setBounds                   (x+=80       ,y     ,width+25,height );		
		panel.add(btnHuella).setBounds                          ((ancho*5)+10,y     ,ancho-15,height );
		panel.add(new JLabel("Perfil:")).setBounds              (x=20        ,y+=25 ,width   ,height );
		panel.add(btnLimpiarPerfil).setBounds                   (x+=30       ,y     ,height  ,height );
		panel.add(btnAgregarPerfil).setBounds                   (x+=20       ,y     ,height  ,height );
		panel.add(lblFolioPerfil).setBounds                     (x+=20       ,y     ,height  ,height );
		panel.add(txtPerfil).setBounds                          (x+=25       ,y     ,300	 ,height );
		panel.add(new JLabel("Email Empresa:")).setBounds       (x=430       ,y     ,width   ,height );
		panel.add(txtemailEmpresa).setBounds                    (x+=80       ,y     ,width+25,height );
//TODO Laboral ------------------------------------------------------------------------------------------------------------------------------------------		
		x=17 ;y=230;width=340;sep=120;
		panel.add(lblLaboral).setBounds                         (x-7         ,y     ,997      ,245    );
		panel.add(new JLabel("Horario:")).setBounds             (x           ,y+=15 ,width    ,height );
		panel.add(btnHorarioNew).setBounds                      (x+50        ,y     ,height   ,height );
		panel.add(btnHorario).setBounds                         (x+70        ,y     ,height   ,height );
		panel.add(lblFolioHorario1).setBounds                   (x+sep-30    ,y     ,height   ,height );
		panel.add(txtHorario).setBounds                         (x+sep       ,y     ,width    ,height );
		panel.add(rbHorario).setBounds                          (x+460       ,y     ,height   ,height );
	    panel.add(new JLabel("Horario 2:")).setBounds           (x           ,y+=25 ,width    ,height );
	    
	    panel.add(btnLimpiarH2).setBounds                       (x+sep-70    ,y     ,height   ,height );
		panel.add(btnHorario2).setBounds                        (x+sep-50    ,y     ,height   ,height );
		panel.add(lblFolioHorario2).setBounds                   (x+sep-30    ,y     ,height   ,height );
		panel.add(txtHorario2).setBounds                        (x+sep       ,y     ,width    ,height );
		panel.add(rbHorario2).setBounds                         (x+460       ,y     ,height   ,height );
	    panel.add(new JLabel("Horario 3:")).setBounds           (x           ,y+=25 ,width    ,height );
	    panel.add(btnLimpiarH3).setBounds                       (x+50        ,y     ,height   ,height );
		panel.add(btnHorario3).setBounds                        (x+70        ,y     ,height   ,height );
		panel.add(lblFolioHorario3).setBounds                   (x+sep-30    ,y     ,height   ,height );
		panel.add(txtHorario3).setBounds                        (x+sep       ,y     ,width    ,height );
		panel.add(rbHorario3).setBounds                         (x+460       ,y     ,height   ,height );
		panel.add(new JLabel("Establecimiento:")).setBounds     (x           ,y+=25 ,width    ,height );
		panel.add(cmbEstablecimiento).setBounds                 (x+sep       ,y     ,width    ,height );
		panel.add(new JLabel("Departamento:")).setBounds        (x           ,y+=25 ,width    ,height );
		panel.add(cmbDepartamento).setBounds                    (x+sep       ,y     ,width    ,height );
		panel.add(new JLabel("Puesto:")).setBounds              (x           ,y+=25 ,width    ,height );
		panel.add(cmbPuesto).setBounds                          (x+sep       ,y     ,width    ,height );
		panel.add(new JLabel("N° Seguro Social:")).setBounds    (x           ,y+=25 ,width/2  ,height );
		panel.add(txtImss).setBounds                            (x+sep       ,y     ,width/2  ,height );
		panel.add(cmbActivo_Inactivo).setBounds                 (x+290       ,y     ,width/2  ,height );
		panel.add(new JLabel("N° Infonavit:")).setBounds        (x           ,y+=25 ,width    ,height );
		panel.add(txtNumeroInfonavit).setBounds                 (x+sep       ,y     ,width/2  ,height );
		panel.add(new JLabel("Últ.Vacaciones:")).setBounds      (x+290       ,y     ,width/2  ,height );
		panel.add(txtFechaUltimasVacaciones).setBounds          (x+365       ,y     ,95       ,height );
		panel.add(btnFechaUltimasVacaciones).setBounds          (x+462       ,y     ,height   ,height );
		
		sep=100;
		panel.add(new JLabel("Tipo de horario:")).setBounds     (x=515       ,y=245 ,width=150,height );
		panel.add(cmbHorarioRotativo).setBounds                 (x+sep       ,y     ,width    ,height );
		panel.add(new JLabel("Descanso:")).setBounds            (x           ,y+=25 ,width    ,height );
		panel.add(txtDescanso).setBounds                        (x+sep       ,y     ,width    ,height );
		panel.add(new JLabel("Día Dobla:")).setBounds           (x           ,y+=25 ,width    ,height );
		panel.add(txtDobla).setBounds                           (x+sep       ,y     ,width    ,height );
		panel.add(new JLabel("Vence La Licencia:")).setBounds   (x           ,y+=25 ,width    ,height );
		panel.add(txtVencimientoLicencia).setBounds             (x+sep       ,y     ,width    ,height );
		panel.add(new JLabel("Fecha Ingreso:")).setBounds       (x           ,y+=25 ,width    ,height );
		panel.add(txtIngreso).setBounds                         (x+sep       ,y     ,width    ,height );
		panel.add(new JLabel("Fecha Baja:")).setBounds          (x           ,y+=25 ,width    ,height );
		panel.add(txtBaja).setBounds                            (x+sep       ,y     ,width-20 ,height );
		panel.add(btnFiniquito).setBounds                       (x+sep+130   ,y     ,height   ,height );
		panel.add(new JLabel("Ingreso IMSS:")).setBounds        (x           ,y+=25 ,width    ,height );
		panel.add(txtIngresoImss).setBounds                     (x+sep       ,y     ,width    ,height );
		panel.add(new JLabel("Última incapacidad:")).setBounds  (x           ,y+=25 ,width    ,height );
		panel.add(txtFechaIncapacidad).setBounds                (x+sep       ,y     ,width-20 ,height );
		panel.add(btnFechaIncapacidad).setBounds                (x+sep+130   ,y     ,height   ,height );
		
		sep=60;
		panel.add(new JLabel("Checador:")).setBounds            (x=800       ,y=245 ,width=130,height );
		panel.add(cmbStatusChecador).setBounds                  (x+sep       ,y     ,width    ,height );
		panel.add(new JLabel("Checa con:")).setBounds           (x       	 ,y+=25 ,width    ,height );
		panel.add(cmbChecaCon).setBounds               		    (x+sep       ,y     ,width    ,height );
		panel.add(btnStatus).setBounds                          (x+sep       ,y+=22 ,width    ,125    );
		panel.add(new JLabel("Estatus:")).setBounds             (x           ,y+=128,width    ,height );
		panel.add(cmbStatus).setBounds                          (x+sep       ,y     ,width    ,height );
		panel.add(new JLabel("Contrato:")).setBounds            (x           ,y+=25 ,width    ,height );
		panel.add(cmbContratacion).setBounds                    (x+sep       ,y     ,width    ,height );
		
//TODO Percepciones y Deducciones ------------------------------------------------------------------------------------------------------------------------------------------		
		x=17 ;sep=87;
		panel.add(lblPercepciones).setBounds                    (x-7         ,y=475 ,700      ,190    );
		panel.add(new JLabel("Salario Diario:")).setBounds      (x           ,y+=15 ,width=150,height );
		panel.add(txtSalarioDiario).setBounds                   (x+sep       ,y     ,width    ,height );
		panel.add(new JLabel("Salario D.I:")).setBounds         (x           ,y+=25 ,width    ,height );
		panel.add(txtSalarioDiarioIntegrado).setBounds          (x+sep       ,y     ,width    ,height );
		panel.add(new JLabel("Sueldo:")).setBounds              (x           ,y+=25 ,width    ,height );
		panel.add(cmbSueldo).setBounds                          (x+sep       ,y     ,width    ,height );
		panel.add(new JLabel("B.Complemento:")).setBounds       (x           ,y+=25 ,width    ,height );
		panel.add(cmbBono).setBounds                            (x+sep       ,y     ,width    ,height );
		panel.add(new JLabel("B.Asistencia:")).setBounds        (x           ,y+=25 ,width    ,height );
		panel.add(cmbBonoAsistencia).setBounds                  (x+sep       ,y     ,width    ,height );
		panel.add(new JLabel("B.Puntualidad:")).setBounds       (x           ,y+=25 ,width    ,height );
		panel.add(cmbBonopuntualidad).setBounds                 (x+sep       ,y     ,width    ,height );
		panel.add(new JLabel("Presencia Fisica:")).setBounds    (x           ,y+=25 ,width    ,height );
		panel.add(cmbPresenciaFisica).setBounds                 (x+sep       ,y     ,width    ,height );
		
		sep=97;
		panel.add(new JLabel("Infonavit:")).setBounds           (x=270       ,y=490 ,width    ,height );
		panel.add(txtInfonavit).setBounds                       (x+sep       ,y     ,width    ,height );
		panel.add(new JLabel("Infonacot:")).setBounds           (x           ,y+=25 ,width    ,height );
		panel.add(txtDInfonacot).setBounds                      (x+sep       ,y     ,width    ,height );
		panel.add(new JLabel("Pensión Alimenticia:")).setBounds (x           ,y+=25 ,width    ,height );
		panel.add(txtPensionAli).setBounds                      (x+sep       ,y     ,width    ,height );
		panel.add(new JLabel("Rango de Prestamo:")).setBounds   (x           ,y+=25 ,width    ,height );
		panel.add(cmbPrestamos).setBounds                       (x+sep       ,y     ,width    ,height );
		panel.add(new JLabel("Tipo de Bancos:")).setBounds      (x           ,y+=25 ,width    ,height );
		panel.add(cmbTipoBancos).setBounds                      (x+sep       ,y     ,width    ,height );
		panel.add(new JLabel("Cuenta de Nómina:")).setBounds    (x           ,y+=25 ,width    ,height );
		panel.add(txtTarjetaNomina).setBounds                   (x+sep       ,y     ,width    ,height );
		panel.add(new JLabel("Forma de Pago:")).setBounds       (x           ,y+=25 ,width    ,height );
		panel.add(txtFormaDePago).setBounds                     (x+sep       ,y     ,width    ,height );		
		
		y=y-=150;
		panel.add(chbGafete).setBounds                          (x=535       ,y     ,width    ,height );
		panel.add(chbFuente_Sodas).setBounds                    (x           ,y+=25 ,width    ,height );
		panel.add(new JLabel("Última Actualización:")).setBounds(x           ,y+=25 ,width    ,height );
		panel.add(txtFechaActualizacion).setBounds              (x           ,y+=25 ,width    ,height );
		panel.add(new JLabel("Último Usuario Actualizó:")).setBounds(x       ,y+=25 ,width    ,height );
		panel.add(txtultimousuariomod).setBounds                (x           ,y+=25 ,width    ,height );
		panel.add(chb_cuadrante_parcial).setBounds              (x           ,y+=25 ,width    ,height );
		panel.add(Observasiones).setBounds                      (x+180       ,y-158 ,290      ,183    );
		
		sep=218;
		panel.add(btnNuevo).setBounds                           (x=17        ,y=668 ,width=110,height );
		panel.add(btnEditar).setBounds                          (x+=sep      ,y     ,width    ,height );
		panel.add(btnGuardar).setBounds                         (x+=sep      ,y     ,width    ,height );
		panel.add(btnDeshacer).setBounds                        (x+=sep      ,y     ,width    ,height );
		panel.add(btnSalir).setBounds                           (x+=sep      ,y     ,width    ,height );
		
		btnHuella.setEnabled(false);
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
		btn_Huellas.addActionListener(Reporte_Datos_Checador);
		
		btnLicencias.addActionListener(Reporte_de_Vigencia_Licencias);
		btnCumpleaños_del_Mes.addActionListener(Reporte_De_Cumpleanios_Del_Mes);
		btnAltasBajas.addActionListener(Reporte_De_Altas_y_Bajas);
		btnDocumentacion.addActionListener(opDocumentacion);
		
		btn_Adeudo.addActionListener(opRAdeudo);
		btnAusentismo.addActionListener(opRAusentismo);
		btnReporteRenuncia.addActionListener(opRRenuncia);
		btnEncuentaDeSalida.addActionListener(opEncuentaSalida);
		btnBeneficiario.addActionListener(opBeneficiario);
		btnReporteSalida.addActionListener(opReporteDeEncuesta);

//		btnExaminar.addActionListener(opExaminar);
		btnLimpiarPerfil.addActionListener(opLimpiarPerfil);
		btnAgregarPerfil.addActionListener(opPerfil);
		btnHorarioNew.addActionListener(opGenerarHorairo);
		btnHorario.addActionListener(opFiltroHorairo);
		btnHorario2.addActionListener(opFiltroHorairo2);
		btnHorario3.addActionListener(opFiltroHorairo3);
		
		btnLimpiarH2.addActionListener(opLimpiarH2);
		btnLimpiarH3.addActionListener(opLimpiarH3);
		
		txtTarjetaNomina.addKeyListener(txtlogns);
		
		rbHorario.addActionListener(opRButton);
		rbHorario2.addActionListener(opRButton);
		rbHorario3.addActionListener(opRButton);
		
		txtFolioEmpleado.requestFocus();
		txtFolioEmpleado.addKeyListener(buscar_action);
		
		cmbHorarioRotativo.addActionListener(opCmbHorarioRotarivo);
		txtSalarioDiario.addKeyListener(validaNumericoSD);
		txtSalarioDiarioIntegrado.addKeyListener(validaNumericoSDI);
		
		btnHuella.addActionListener(opCapturarHuella);
		
		cont.add(pestanas);
		
//		btnExaminar.setEnabled(false);
		btnCamara.setEnabled(false);
		
		txtPerfil.setEnabled(false);
		
		btnHorario.setEnabled(false);
		btnHorario2.setEnabled(false);
		btnHorario3.setEnabled(false);
		
		btnLimpiarH2.setEnabled(false);
		btnLimpiarH3.setEnabled(false);
		
		btnHorarioNew.setEnabled(false);
		txtHorario.setEditable(false);
		txtHorario2.setEditable(false);
		txtHorario3.setEditable(false);
		
		cmbHorarioRotativo.setEnabled(false);
		
		cmbEstablecimiento.setEnabled(false);
		cmbDepartamento.setEnabled(false);
		cmbPuesto.setEnabled(false);
		
		txtDescanso.setEnabled(false);
		txtemailEmpresa.setEditable(false);
		txtemailPersonal.setEditable(false);
		txtDobla.setEnabled(false);
		txtChecador.setEnabled(false);
		txtChecador.setVisible(false);
		txtFechaActualizacion.setEnabled(false);
		
		panelEnabledFalse();
		txtFolioEmpleado.setEditable(true);
		txtFolioEmpleado.setEnabled(true);
		txtTelefono_Cuadrante.setEnabled(false);
		txtultimousuariomod.setEditable(false);
		
		try {
			imagB  = Files.readAllBytes(Paths.get(System.getProperty("user.dir")+"/Iconos/Un.JPG")); 
			btnFoto.setIcon(crearIcon(imagB));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
         
		 ImageIcon file_status = new ImageIcon(System.getProperty("user.dir")+"/Iconos/Vigente.png");
         Icon iconoStatus = new ImageIcon(file_status.getImage().getScaledInstance(btnStatus.getWidth(), btnStatus.getHeight(), Image.SCALE_DEFAULT));
         btnStatus.setIcon(iconoStatus);
        
         this.addWindowListener(new WindowAdapter() {
                 public void windowOpened( WindowEvent e ){	 txtFolioEmpleado.requestFocus();  }
         });
		
	    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put( KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), "foco");	    
	    getRootPane().getActionMap().put("foco", new AbstractAction(){    @Override
	        public void actionPerformed(ActionEvent e) {	        	btnFiltro.doClick();    	     }
	    });
	    
	    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put( KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0), "horario");
	    getRootPane().getActionMap().put("horario", new AbstractAction(){ @Override
	        public void actionPerformed(ActionEvent e) {    	        	btnHorarioNew.doClick();          }
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
						                  
//                  btnFoto.addMouseWheelListener(buscarConRueda);//añadimos el MouseWheelListener al spinner
                  
	  	}
	
	ActionListener opCapturarHuella = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			new Cat_Huellas_Personalizado().setVisible(true);
		}
	};
	
	public Icon crearIcon(byte[] bs){
		ImageIcon tmpIconDefault= new ImageIcon(bs);
		
//			int anchoRealDeImagen = tmpIconDefault.getIconWidth();
//			int altoRealDeImg = tmpIconDefault.getIconHeight();
			int anchoDeImagenEscala = (btnFoto.getWidth());
			int altoDeImgagenEscala = (btnFoto.getHeight());
		 
			return new ImageIcon(tmpIconDefault.getImage().getScaledInstance(anchoDeImagenEscala, altoDeImgagenEscala, Image.SCALE_DEFAULT));
	}
	
	 int valor = 0;
//	MouseWheelListener buscarConRueda = new MouseWheelListener() {
//	    public void mouseWheelMoved(MouseWheelEvent e) {
//	    	
//	    	if(!txtNombre.isEnabled()){
//	    		
//			        int rueda = e.getWheelRotation();//tomamos el valor de la rueda al girar
//			       
//			        
//			        if (rueda < 0) {//si el valor es mas pequeño de 0 quiere decir que estamos subiendo
//			        	
//			            valor = Integer.valueOf(txtFolioEmpleado.getText().equals("")?"0":txtFolioEmpleado.getText());//tomamos el valor del txt
//			            valor++;//aumentamos el valor  
//			            
//		                txtFolioEmpleado.setText(valor+"");//lo añadimos de nuevo al txt
//		                buscarEmpleado("rueda+"); //llamamos al metodo buscar
//			        } else {//Si es mayor de 0 es que estamos bajando
//			          
//			        	valor = Integer.valueOf(txtFolioEmpleado.getText().equals("")?"0":txtFolioEmpleado.getText());
//			        	
//			            if (valor!=0){//En este caso no dejamos que baje de 0 el txt
//			                valor--;//Disminuimos el valor
//			            }
//			            txtFolioEmpleado.setText(valor==0?"":valor+"");//Y lo añadimos el txt (si es cero lo dejamos como vacio)
//			            buscarEmpleado("rueda-"); //llamamos al metodo buscar
//			        }
//			    }
//	 	  }
//	};
	
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
	
	ActionListener opLimpiarPerfil = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			lblFolioPerfil.setText("");
			txtPerfil.setText("");
		}
	};
	
	ActionListener opPerfil = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
//			fitro de Perfiles
			new Cat_Filtro_Perfiles_Seleccion().setVisible(true);
		}
	};
	
	ActionListener opCargarPerfil = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			
			
//			buscar perfil y llenar objeto
//			Obj_Perfil_De_Puestos perfil = new Obj_Perfil_De_Puestos().buscar(folio)
//			modificar estab,depto y puesto
			if(JOptionPane.showConfirmDialog(null, "Desea Asignar Los Sueldos Y Bonos Del Perfil Al Empleado?") == 0){
//				cambiar los campos de sueldos y bonos
			}
		
		}
	};
	
	ActionListener opReporteDeEncuesta = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			
			if(!txtNombre.getText().trim().equals("")){
				String basedatos="2.26";
				String vista_previa_reporte="si";
				int vista_previa_de_ventana=1;
				
				String comando="exec sp_select_encuensta_de_salida "+txtFolioEmpleado.getText()+"";
				String reporte = "Obj_Reporte_De_Encuenta_Y_Motivos_De_Salida.jrxml";
				 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
			}else{
				JOptionPane.showMessageDialog(null, "Es Necesario Que Primero Seleccione Un Colaborador", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}
		}
	};
	
	ActionListener opEncuentaSalida = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			if(!txtNombre.getText().trim().equals("")){
				String existe = new BuscarSQL().existe_encuentas_de_salida_del_colaborador(txtFolioEmpleado.getText());
				if(existe.equals("N")){
					dispose();
					new Cat_Motivos_De_Renuncia(txtFolioEmpleado.getText(), txtNombre.getText().trim()+" "+txtApPaterno.getText().trim()+" "+txtApMaterno.getText().trim(), cmbEstablecimiento.getSelectedItem().toString(), cmbDepartamento.getSelectedItem().toString(), cmbPuesto.getSelectedItem().toString()).setVisible(true);
				}else{
					if(JOptionPane.showConfirmDialog(null, existe+", \n"
                      + "Al Dar Si Se Cargaran Los Datos Del Registro Seleccionado \n ¿Desea Continuar? " ) == 0){
						 dispose();
						new Cat_Motivos_De_Renuncia(txtFolioEmpleado.getText(), txtNombre.getText().trim()+" "+txtApPaterno.getText().trim()+" "+txtApMaterno.getText().trim(), cmbEstablecimiento.getSelectedItem().toString(), cmbDepartamento.getSelectedItem().toString(), cmbPuesto.getSelectedItem().toString()).setVisible(true);
			        }else{
					 return;
			        }
				}
				return;
			}else{
				JOptionPane.showMessageDialog(null, "Es Necesario Que Primero Seleccione Un Colaborador", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}
		}
	};

    ActionListener opBeneficiario = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			if(!txtNombre.getText().trim().equals("")){
				new Cat_Alimentacion_De_Beneficiario_Del_Colaborador().setVisible(true);
			}else{
				JOptionPane.showMessageDialog(null, "Es Necesario Que Primero Seleccione Un Colaborador", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}
		}
	};

	ActionListener opCmbHorarioRotarivo = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			
			switch(cmbHorarioRotativo.getSelectedIndex()){
				case 0: 
					btnHorario.setEnabled(true);
						btnHorario2.setEnabled(false);
						btnHorario3.setEnabled(false);
						
						btnLimpiarH2.setEnabled(false);
						btnLimpiarH3.setEnabled(false);
						
						rbHorario.setEnabled(true);
						rbHorario2.setEnabled(false);
						rbHorario3.setEnabled(false);
						
						rbHorario.setSelected(true);
						break;
						
				case 1: 
					btnHorario.setEnabled(true);
						btnHorario2.setEnabled(true);
						btnHorario3.setEnabled(false);
						
						btnLimpiarH2.setEnabled(true);
						btnLimpiarH3.setEnabled(false);
						
						rbHorario.setEnabled(true);
						rbHorario2.setEnabled(true);
						rbHorario3.setEnabled(false);
						break;
						
				case 2: 
					btnHorario.setEnabled(true);
						btnHorario2.setEnabled(true);
						btnHorario3.setEnabled(true);
						
						btnLimpiarH2.setEnabled(true);
						btnLimpiarH3.setEnabled(true);
						
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
//				btnExaminar.setEnabled(true);
				btnCamara.setEnabled(true);
			}else{
//				btnExaminar.setEnabled(false);
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
	
//	ActionListener opExaminar = new ActionListener(){
//		public void actionPerformed(ActionEvent e) {
//			FileDialog file = new FileDialog(new Frame());
//			
//			file.setTitle("Selecciona una Imagen");
//			file.setMode(FileDialog.LOAD);
//			file.setVisible(true);
//			
//			if(file.getDirectory() != null){
//
//					try {
//						String rootPicture = file.getDirectory()+file.getFile();
//						
//						File foto = new File(rootPicture);
//						File destino = new File(System.getProperty("user.dir")+"/tmp/tmp.jpg");
//				    	
//				    	InputStream in = new FileInputStream(foto);
//						OutputStream out = new FileOutputStream(destino);
//						
//					    byte[] buf = new byte[1024];
//					    int len;
//	
//					    while ((len = in.read(buf)) > 0) {
//					    	out.write(buf, 0, len);
//					    }
//					    
//					    in.close();
//					    out.close();
//						
//						File foto1 = new File(rootPicture);
//						File destino1 = new File(System.getProperty("user.dir")+"/tmp/tmp_update/tmp.jpg");
//				    	
//				    	InputStream in1 = new FileInputStream(foto1);
//						OutputStream out1 = new FileOutputStream(destino1);
//						
//					    byte[] buf1 = new byte[1024];
//					    int len1;
//	
//					    while ((len1 = in1.read(buf1)) > 0) {
//					    	out1.write(buf1, 0, len1);
//					    }
//					    
//					    in1.close();
//					    out1.close();
//						
//					    ImageIcon tmpIconDefault = new ImageIcon(System.getProperty("user.dir")+"/tmp/tmp.jpg");
//				         Icon iconoDefault = new ImageIcon(tmpIconDefault.getImage().getScaledInstance(btnFoto.getWidth(), btnFoto.getHeight(), Image.SCALE_DEFAULT));
//				         btnFoto.setIcon(iconoDefault);
//				         
//					} catch (IOException e1) {
//						e1.printStackTrace();
//					}
//				
//			}else{
//				JOptionPane.showMessageDialog(null,"No ha seleccionado ninguna imagen","Aviso",JOptionPane.WARNING_MESSAGE);
//				return;
//			}
//		}
//	};
	
	ActionListener opVerificar = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			if(txtNombre.getText().length() == 0 || txtApPaterno.getText().length() == 0 || txtApMaterno.getText().length() == 0){
				btnHuella.setEnabled(false);
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
					
					PerfilesActivos();
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
					new Llamar_Camara().setVisible(true);
				}catch(Exception ee){
					JOptionPane.showMessageDialog(null, "Verifique si está conectada y configurada la camara", "Error!", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	};
	
	ActionListener opDocumentacion = new ActionListener() {
		public void actionPerformed(ActionEvent e){
			
				if(!txtApPaterno.getText().equals("")){
					new Cat_Documentacion_De_Empleado(txtFolioEmpleado.getText(),txtNombre.getText()+" "+txtApPaterno.getText()+" "+txtApMaterno.getText(),cmbEstablecimiento.getSelectedItem()+"",cmbPuesto.getSelectedItem()+"").setVisible(true);
				}else{
					JOptionPane.showMessageDialog(null,"Necesita Seleccionar Primero Un Colaborador", "Mensaje!",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
					txtFolioEmpleado.requestFocus();
					return;
				}
			}
		};
		
	public Date fecha(String fechap) {
		Date fecha = null;
		try {if(fechap.equals("")){	fecha=null;	}else {	fecha=new SimpleDateFormat("dd/MM/yyyy").parse(fechap);	}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return fecha;
	} 
		
	ActionListener buscar = new ActionListener() {
		public void actionPerformed(ActionEvent e){
			if(txtFolioEmpleado.getText().equals("")){
				JOptionPane.showMessageDialog(null,"Necesita Seleccionar Primero Un Colaborador", "Mensaje!",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}else{
			txtHorario.setFont(new Font("ARIAL", Font.ITALIC, 9));
			txtHorario2.setFont(new Font("ARIAL", Font.ITALIC, 9));
			txtHorario3.setFont(new Font("ARIAL", Font.ITALIC, 9));
			txtultimousuariomod.setFont(new Font("ARIAL", Font.TRUETYPE_FONT, 8));
		
			try {
				tablacompleta = re.empleado_buscar_datos(txtFolioEmpleado.getText().toString().trim());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			//datos_personales
			txtNombre.setText                 (tablacompleta[0][1].toString()         );
			txtApPaterno.setText              (tablacompleta[0][2].toString()         );
			txtApMaterno.setText              (tablacompleta[0][3].toString()         );
			cmbSexo.setSelectedItem           (tablacompleta[0][4].toString()         );
			txtFechaNacimiento.setDate        (fecha(tablacompleta[0][5].toString())  );
            cmbEstadoCivil.setSelectedItem    (tablacompleta[0][6].toString()         );
            txtCalle.setText                  (tablacompleta[0][7].toString()         );
			txtColonia.setText                (tablacompleta[0][8].toString()         );
			txtPoblacion.setText              (tablacompleta[0][9].toString()         );
			cmbTipoDeSangre.setSelectedItem   (tablacompleta[0][10].toString()        );
			txtTelefono_Familiar.setText      (tablacompleta[0][11].toString()        );
            txtTelefono_Propio.setText        (tablacompleta[0][12].toString()        );
            txtTelefono_Cuadrante.setText     (tablacompleta[0][13].toString()        );
			cmbEscolaridad.setSelectedItem    (tablacompleta[0][14].toString()        );
            txtRFC.setText                    (tablacompleta[0][15].toString()        );
            txtCurp.setText                   (tablacompleta[0][16].toString()        );
            txtemailPersonal.setText          (tablacompleta[0][17].toString()        );
            if(Boolean.valueOf                (tablacompleta[0][18].toString()       )){
            btnHuella.setBackground(Color.GREEN);btnHuella.setForeground(Color.BLACK);}
            else{btnHuella.setBackground(Color.RED);	btnHuella.setForeground(Color.WHITE);}
            btnHuella.setOpaque(true);
            //datos_laborales
            lblFolioPerfil.setText            (tablacompleta[0][19].toString()        );
            txtPerfil.setText                 (tablacompleta[0][20].toString()        );
            txtemailEmpresa.setText           (tablacompleta[0][21].toString()        );
            lblFolioHorario1.setText          (tablacompleta[0][22].toString()        );
            txtHorario.setText                (tablacompleta[0][23].toString()        );
            rbHorario.setSelected(Boolean.valueOf(tablacompleta[0][24].toString())    );
            cmbHorarioRotativo.setSelectedItem(tablacompleta[0][25].toString()        );
            cmbStatusChecador.setSelectedItem (tablacompleta[0][26].toString()        );
            lblFolioHorario2.setText          (tablacompleta[0][27].toString()        );
            txtHorario2.setText               (tablacompleta[0][28].toString()        );
            rbHorario2.setSelected(Boolean.valueOf(tablacompleta[0][29].toString())   );
            txtDescanso.setText               (tablacompleta[0][30].toString()        );
            cmbChecaCon.setSelectedItem       (tablacompleta[0][31].toString()        );
            lblFolioHorario3.setText          (tablacompleta[0][32].toString()        );
            txtHorario3.setText               (tablacompleta[0][33].toString()        );
            rbHorario3.setSelected(Boolean.valueOf(tablacompleta[0][34].toString())   );
            txtDobla.setText                  (tablacompleta[0][35].toString()        );
            cmbEstablecimiento.setSelectedItem(tablacompleta[0][36].toString()        );
            txtVencimientoLicencia.setDate    (fecha(tablacompleta[0][37].toString()) );
            cmbDepartamento.setSelectedItem   (tablacompleta[0][38].toString()        );     
            txtIngreso.setDate                (fecha(tablacompleta[0][39].toString()) );
            cmbPuesto.setSelectedItem         (tablacompleta[0][40].toString()        );
            txtBaja.setText                   (tablacompleta[0][41].toString()        );
            txtImss.setText                   (tablacompleta[0][42].toString()        );
            cmbActivo_Inactivo.setSelectedItem(tablacompleta[0][43].toString()        );
            txtIngresoImss.setDate            (fecha(tablacompleta[0][44].toString()) );        
             txtNumeroInfonavit.setText       (tablacompleta[0][45].toString()        );
            txtFechaUltimasVacaciones.setText (tablacompleta[0][46].toString()        );
            txtFechaIncapacidad.setText       (tablacompleta[0][47].toString()        );
            cmbStatus.setSelectedItem         (tablacompleta[0][48].toString()        );
            cmbContratacion.setSelectedItem   (tablacompleta[0][49].toString()        );
//            //percepciones y deducciones
            txtSalarioDiario.setText          (tablacompleta[0][50].toString()        );
            txtInfonavit.setText              (tablacompleta[0][51].toString()        );
            chbGafete.setSelected    (Boolean.valueOf(tablacompleta[0][52].toString()));
            txaObservaciones.setText          (tablacompleta[0][53].toString()        );
            txtSalarioDiarioIntegrado.setText (tablacompleta[0][54].toString()        );
            txtDInfonacot.setText             (tablacompleta[0][55].toString()        );
            chbFuente_Sodas.setSelected(Boolean.valueOf(tablacompleta[0][56].toString()));
            cmbSueldo.setSelectedItem         (tablacompleta[0][57].toString()        );
            txtPensionAli.setText             (tablacompleta[0][58].toString()        );
            cmbBono.setSelectedItem           (tablacompleta[0][59].toString()        );
            cmbPrestamos.setSelectedItem      (tablacompleta[0][60].toString()        );
            txtFechaActualizacion.setText     (tablacompleta[0][61].toString()        );
            cmbBonoAsistencia.setSelectedItem (tablacompleta[0][62].toString()        );
            cmbTipoBancos.setSelectedItem     (tablacompleta[0][63].toString()        );
            cmbBonopuntualidad.setSelectedItem(tablacompleta[0][64].toString()        );
            txtTarjetaNomina.setText          (tablacompleta[0][65].toString()        );
            txtultimousuariomod.setText       (tablacompleta[0][66].toString()        );          
            cmbPresenciaFisica.setSelectedItem(tablacompleta[0][67].toString()        );   
            txtFormaDePago.setText            (tablacompleta[0][68].toString()        );   
            chb_cuadrante_parcial.setSelected(Boolean.valueOf(tablacompleta[0][69].toString()));
            txtChecador.setText               (tablacompleta[0][78].toString()        );

            imagB = (byte[]) tablacompleta[0][70];
			btnFoto.setIcon(crearIcon(imagB));
			
//             ImageIcon tmpIconDefault = new ImageIcon(System.getProperty("user.dir")+"/tmp/tmp.jpg");
//	         Icon iconoDefault = new ImageIcon(tmpIconDefault.getImage().getScaledInstance(btnFoto.getWidth(), btnFoto.getHeight(), Image.SCALE_DEFAULT));
//	         btnFoto.setIcon(iconoDefault);
	   
	         ImageIcon imagen_estatus = new ImageIcon(System.getProperty("user.dir")+"/"+tablacompleta[0][71].toString());
	         Icon icono_estatus = new ImageIcon(imagen_estatus.getImage().getScaledInstance(btnStatus.getWidth(), btnStatus.getHeight(), Image.SCALE_DEFAULT));
	         btnStatus.setIcon(icono_estatus);

			btnEditar.setEnabled(true);
			txtFolioEmpleado.setEnabled(false);
//          // extras
//			Matriz[i][72] = rs.getString(73);//huella_1
//			Matriz[i][73] = rs.getString(74);//huella_2
			}
		}
	};
	
	

//	public void buscarEmpleado(String funcion){
//					if(funcion.equals("rueda+")){
//						valor++;
//						txtFolioEmpleado.setText(valor+"");
//						buscarEmpleado("rueda+");
//						
//					}
//					
//					if(funcion.equals("rueda-")){
//						valor--;
//						txtFolioEmpleado.setText(valor+"");
//						buscarEmpleado("rueda-");
//					}
//					
//					if(funcion.equals("")){
//						JOptionPane.showMessageDialog(null, "El Registro no existe","Error",JOptionPane.WARNING_MESSAGE);
//						panelEnabledFalse();
//						txtFolioEmpleado.setEditable(true);
//						txtFolioEmpleado.requestFocus();
//						panelLimpiar();
//					}
	
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
		
			int horario=0,horario2=0,horario3=0;
			 horario=lblFolioHorario1.getText().equals("")?0:Integer.valueOf(lblFolioHorario1.getText());
			 horario2=lblFolioHorario2.getText().equals("")?0:Integer.valueOf(lblFolioHorario2.getText());
			 horario3=lblFolioHorario3.getText().equals("")?0:Integer.valueOf(lblFolioHorario3.getText());
			
			if(new BuscarSQL().existe_horario_con_otro_empleado(horario,horario2,horario3, Integer.valueOf(txtFolioEmpleado.getText().toString()) )){
				JOptionPane.showMessageDialog(null, "No Se Puede Asignar Un Mismo Horario A Mas De Un Colaborador Con Estatus\nVigente, Incapacitado, De Vacaciones o Provicional Checador", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/error-de-reloj-icono-5961-32.png"));
				return;
			}else{
				
			if(cmbStatus.getSelectedItem().toString().trim().equals("Baja") || cmbStatus.getSelectedItem().toString().trim().equals("No Contratable") || cmbStatus.getSelectedItem().toString().trim().equals("Renuncia")){
				if(new BuscarSQL().baja_en_catalogo_empleado()){
						guardar_modificar_Empleado();
				}else{
					JOptionPane.showMessageDialog(null, "No Se Puede Guardar Un Colaborador Con Status De Renuncia, Baja o No Contratable Es Necesario Hacer La Encuesta De Renuncia y El Finiquito.", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
					return;
				}
			}else{
				    guardar_modificar_Empleado();
			}
			
			
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
					       +" Hasta Que Se Genere Por D.H o Se Desautorize por Finanzas o Auditoria <<Al dar Click en Aceptar SCOI se Cerrará>>","Aviso",JOptionPane.WARNING_MESSAGE);
					try {	R.exec("taskkill /f /im javaw.exe"); } catch (IOException e1) {	e1.printStackTrace(); }		
				}else{
				
				if(txtFolioEmpleado.getText().equals("")){
					JOptionPane.showMessageDialog(null, "El Folio Es Requerido \n","Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				}
				if(validaIMSS()>0 && validaIMSS()<3){
					
					JOptionPane.showMessageDialog(null, "Para poder guardar o actualizar es necesario que los siguientes campos esten llenos o totalemente vacios:\n- No Seguro Social\n- Activo(IMSS) ó Inactivo (IMSS)\n- Ingreso IMSS (Fecha)", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
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
						if(JOptionPane.showConfirmDialog(null, "El registro existe, ¿desea actualizarlo?") == 0){
							if(validaCampos()!="") {
								JOptionPane.showMessageDialog(null, "Los Siguientes Campos Son Requeridos Para Poder Guardar El Registro:\n"+validaCampos(), "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
								return;
							}else{
								
		//				datos personales	
								txtFechaActualizacion.setText(new SimpleDateFormat("dd/MM/yyyy").format((new Date())));
								empleado.setNo_checador(txtChecador.getText());
								empleado.setNombre(procesa_texto(txtNombre.getText()));
								empleado.setAp_paterno(procesa_texto(txtApPaterno.getText()));
								empleado.setAp_materno(txtApMaterno.getText().length() == 0 ? "" : procesa_texto(txtApMaterno.getText()) );
								
								empleado.setFecha_nacimiento(new SimpleDateFormat("dd/MM/yyyy").format(txtFechaNacimiento.getDate()));
								empleado.setCalle(txtCalle.getText());
								empleado.setColonia(txtColonia.getText());
								empleado.setPoblacion(txtPoblacion.getText());
								empleado.setTelefono_familiar(txtTelefono_Familiar.getText()+"");
								empleado.setTelefono_propio(txtTelefono_Propio.getText()+"");
								empleado.setRfc(txtRFC.getText());
								empleado.setCurp(txtCurp.getText());
								
								empleado.setSexo(cmbSexo.getSelectedItem().equals("MASCULINO")?0:1);
								
								empleado.setEstado_civil(cmbEstadoCivil.getSelectedItem().toString());
								empleado.setTipo_sangre(cmbTipoDeSangre.getSelectedItem().toString());
								empleado.setEscolaridad(cmbEscolaridad.getSelectedItem().toString());
								empleado.setEmailEmpresa(txtemailEmpresa.getText().toString().toLowerCase().trim());
								empleado.setEmailPersonal(txtemailPersonal.getText().toString().toLowerCase().trim());
								
//								imagB = dpr.getOrganigramaB();
//        						lblImage.setIcon(crearIcon(imagB));
								empleado.setFotoB(imagB);
								empleado.setPerfil(lblFolioPerfil.getText().trim().equals("")?0:Integer.valueOf(lblFolioPerfil.getText().trim()));
		//				laboral
								empleado.setHorario(lblFolioHorario1.getText().equals("")?0:Integer.valueOf(lblFolioHorario1.getText()));
								empleado.setHorario2(lblFolioHorario2.getText().equals("")?0:Integer.valueOf(lblFolioHorario2.getText()));
								empleado.setHorario3(lblFolioHorario3.getText().equals("")?0:Integer.valueOf(lblFolioHorario3.getText()));
								
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

								empleado.setStatus_rotativo(cmbHorarioRotativo.getSelectedIndex());
								empleado.setContrato(cmbContratacion.getSelectedItem().toString().contains(" ")?Integer.valueOf(cmbContratacion.getSelectedItem().toString().substring(0, cmbContratacion.getSelectedItem().toString().indexOf(" "))):0);
								
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
								
								empleado.setFecha_ingreso_imss(txtIngresoImss.getDate()==null?"1/01/1900":new SimpleDateFormat("dd/MM/yyyy").format(txtIngresoImss.getDate()));
								empleado.setFecha_vencimiento_licencia(txtVencimientoLicencia.getDate()==null?"1/01/1900":new SimpleDateFormat("dd/MM/yyyy").format(txtVencimientoLicencia.getDate()));
								empleado.setStatus_checador(cmbStatusChecador.getSelectedItem().toString()/*.equals("NORMAL")?"N":"L"*/);
								empleado.setForma_de_checar(cmbChecaCon.getSelectedItem().toString());
		//				percepciones y deducciones
						
								empleado.setSalario_diario(txtSalarioDiario.getText().equals("")?Float.parseFloat(0.0+""):Float.parseFloat(txtSalarioDiario.getText()));	
								empleado.setSalario_diario_integrado(txtSalarioDiarioIntegrado.getText().equals("")?Float.parseFloat(0.0+""):Float.parseFloat(txtSalarioDiarioIntegrado.getText()));
								empleado.setForma_pago(txtFormaDePago.getText()+"");
								empleado.setSueldo(Float.valueOf(cmbSueldo.getSelectedItem().toString()));
								
								Obj_Bono_Complemento_Sueldo bono = new Obj_Bono_Complemento_Sueldo().buscarValor(Float.parseFloat(cmbBono.getSelectedItem()+""));
								empleado.setBono(bono.getFolio());
								
								empleado.setPrestamo(cmbPrestamos.getSelectedIndex());
								
								empleado.setPension_alimenticia(txtPensionAli.getText().length()==0 ? Float.parseFloat(0.0+"") : Float.parseFloat(txtPensionAli.getText()));
								empleado.setInfonavit(txtInfonavit.getText().length() == 0 ? Float.parseFloat(0.0+"") : Float.parseFloat(txtInfonavit.getText()));
								empleado.setBono_asistencia(cmbBonoAsistencia.getSelectedItem().toString().trim().equals("Selecciona un Bono") ? Float.parseFloat(0.0+"") : Float.valueOf(cmbBonoAsistencia.getSelectedItem().toString()));
								empleado.setBono_puntualidad(cmbBonopuntualidad.getSelectedItem().toString().trim().equals("Selecciona un Bono") ? Float.parseFloat(0.0+"") : Float.valueOf(cmbBonopuntualidad.getSelectedItem().toString()));
								
								empleado.setInfonacot(Float.valueOf(txtDInfonacot.getText().equals("")?"0":txtDInfonacot.getText()));
								empleado.setTargeta_nomina(txtTarjetaNomina.getText()+"");
								empleado.setTipo_banco(cmbTipoBancos.getSelectedIndex());
								empleado.setPresencia_fisica(cmbPresenciaFisica.getSelectedItem().toString().equals("Aplica")?1:0);
								empleado.setGafete(chbGafete.isSelected());
								empleado.setFuente_sodas(chbFuente_Sodas.isSelected());
								empleado.setObservasiones(txaObservaciones.getText()+"");
								empleado.setFecha_actualizacion(txtFechaActualizacion.getText());
								
								
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
							  
							//TODO (Actualizar)
								if(empleado.actualizar(Integer.parseInt(txtFolioEmpleado.getText()) , datosHuella, datosHuella2, tamañoHuella, tamañoHuella2 )){
									panelLimpiar();
									panelEnabledFalse();
									rbHorario2.setEnabled(false);
									rbHorario3.setEnabled(false);
									txtFolioEmpleado.setEnabled(true);
									txtFolioEmpleado.setEditable(true);
									txtFolioEmpleado.requestFocus();
									btnTrueFoto.setSelected(false);
//									btnExaminar.setEnabled(false);
									btnCamara.setEnabled(false);
//									txtHorario.setEnabled(false);
									btnBuscar.setEnabled(true);
									btnFiltro.setEnabled(true);
									btnNuevo.setEnabled(true);
									JOptionPane.showMessageDialog(null, "El Colaborador Se Actualizo Correctamente","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
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
							empleado.setNo_checador(txtChecador.getText());
							empleado.setNombre(procesa_texto(txtNombre.getText()));
							empleado.setAp_paterno(procesa_texto(txtApPaterno.getText()));
							empleado.setAp_materno(txtApMaterno.getText().length() == 0 ? "" : procesa_texto(txtApMaterno.getText()) );
							
							empleado.setFecha_nacimiento(new SimpleDateFormat("dd/MM/yyyy").format(txtFechaNacimiento.getDate()));
							empleado.setCalle(txtCalle.getText());
							empleado.setColonia(txtColonia.getText());
							empleado.setPoblacion(txtPoblacion.getText());
							empleado.setTelefono_familiar(txtTelefono_Familiar.getText()+"");
							empleado.setTelefono_propio(txtTelefono_Propio.getText()+"");
							empleado.setRfc(txtRFC.getText());
							empleado.setCurp(txtCurp.getText());
							
							empleado.setSexo(cmbSexo.getSelectedItem().equals("MASCULINO")?0:1);
							
							empleado.setEstado_civil(cmbEstadoCivil.getSelectedItem().toString());
							empleado.setTipo_sangre(cmbTipoDeSangre.getSelectedItem().toString());
							empleado.setEscolaridad(cmbEscolaridad.getSelectedItem().toString());
							empleado.setEmailEmpresa(txtemailEmpresa.getText().toString().toLowerCase().trim());
							empleado.setEmailPersonal(txtemailPersonal.getText().toString().toLowerCase().trim());
							
							empleado.setFotoB(imagB);
//							empleado.setFoto(new File(System.getProperty("user.dir")+(btnTrueFoto.isSelected()?"/tmp/tmp_update/tmp.jpg":"/tmp/tmp.jpg") ));
							empleado.setPerfil(lblFolioPerfil.getText().trim().equals("")?0:Integer.valueOf(lblFolioPerfil.getText().trim()));
	//				laboral
							empleado.setHorario(lblFolioHorario1.getText().equals("")?0:Integer.valueOf(lblFolioHorario1.getText()));
							empleado.setHorario2(lblFolioHorario2.getText().equals("")?0:Integer.valueOf(lblFolioHorario2.getText()));
							empleado.setHorario3(lblFolioHorario3.getText().equals("")?0:Integer.valueOf(lblFolioHorario3.getText()));
							
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
							
//							switch(cmbHorarioRotativo.getSelectedIndex()){
//								case 0: empleado.setStatus_rotativo(0); break;
//								case 1: empleado.setStatus_rotativo(1); break;
//								case 2: empleado.setStatus_rotativo(2); break;
//							}
							empleado.setStatus_rotativo(cmbHorarioRotativo.getSelectedIndex());
							empleado.setContrato(cmbContratacion.getSelectedItem().toString().contains(" ")?Integer.valueOf(cmbContratacion.getSelectedItem().toString().substring(0, cmbContratacion.getSelectedItem().toString().indexOf(" "))):0);
							
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
							
							empleado.setFecha_ingreso_imss(txtIngresoImss.getDate()==null?"1/01/1900":new SimpleDateFormat("dd/MM/yyyy").format(txtIngresoImss.getDate()));
							empleado.setFecha_vencimiento_licencia(txtVencimientoLicencia.getDate()==null?"1/01/1900":new SimpleDateFormat("dd/MM/yyyy").format(txtVencimientoLicencia.getDate()));
							empleado.setStatus_checador(cmbStatusChecador.getSelectedItem().toString()/*.equals("NORMAL")?"N":"L"*/);
							empleado.setForma_de_checar(cmbChecaCon.getSelectedItem().toString());
	//				percepciones y deducciones
					
							empleado.setSalario_diario(txtSalarioDiario.getText().equals("")?Float.parseFloat(0.0+""):Float.parseFloat(txtSalarioDiario.getText()));	
							empleado.setSalario_diario_integrado(txtSalarioDiarioIntegrado.getText().equals("")?Float.parseFloat(0.0+""):Float.parseFloat(txtSalarioDiarioIntegrado.getText()));
							empleado.setForma_pago(txtFormaDePago.getText()+"");
							empleado.setSueldo(Float.valueOf(cmbSueldo.getSelectedItem().toString()));
							
							Obj_Bono_Complemento_Sueldo bono = new Obj_Bono_Complemento_Sueldo().buscarValor(Float.parseFloat(cmbBono.getSelectedItem()+""));
							empleado.setBono(bono.getFolio());
							
							empleado.setPrestamo(cmbPrestamos.getSelectedIndex());
							
							empleado.setPension_alimenticia(txtPensionAli.getText().length()==0 ? Float.parseFloat(0.0+"") : Float.parseFloat(txtPensionAli.getText()));
							empleado.setInfonavit(txtInfonavit.getText().length() == 0 ? Float.parseFloat(0.0+"") : Float.parseFloat(txtInfonavit.getText()));
							empleado.setBono_asistencia(cmbBonoAsistencia.getSelectedItem().toString().trim().equals("Selecciona un Bono") ? Float.parseFloat(0.0+"") : Float.valueOf(cmbBonoAsistencia.getSelectedItem().toString()));
							empleado.setBono_puntualidad(cmbBonopuntualidad.getSelectedItem().toString().trim().equals("Selecciona un Bono") ? Float.parseFloat(0.0+"") : Float.valueOf(cmbBonopuntualidad.getSelectedItem().toString()));
							
							empleado.setInfonacot(Float.valueOf(txtDInfonacot.getText().equals("")?"0":txtDInfonacot.getText()));
							empleado.setTargeta_nomina(txtTarjetaNomina.getText()+"");
							empleado.setTipo_banco(cmbTipoBancos.getSelectedIndex());
							empleado.setPresencia_fisica(cmbPresenciaFisica.getSelectedItem().toString().equals("Aplica")?1:0);
							empleado.setGafete(chbGafete.isSelected());
							empleado.setFuente_sodas(chbFuente_Sodas.isSelected());
							empleado.setObservasiones(txaObservaciones.getText()+"");
							empleado.setFecha_actualizacion(txtFechaActualizacion.getText());
							
//							System.out.println("Guaradado-------------------");
//							System.out.println(datosHuella);
//							  System.out.println(datosHuella2);
//							  System.out.println(tamañoHuella);
//							  System.out.println(tamañoHuella2);
							  
							//TODO (Guardar)
							if(empleado.guardar(datosHuella, datosHuella2, tamañoHuella, tamañoHuella2)){
								panelLimpiar();
								panelEnabledFalse();
								rbHorario2.setEnabled(false);
								rbHorario3.setEnabled(false);
								txtFolioEmpleado.setEnabled(true);
								txtFolioEmpleado.setEditable(true);
								txtFolioEmpleado.requestFocus();
								btnTrueFoto.setSelected(false);
//								btnExaminar.setEnabled(false);
								btnCamara.setEnabled(false);
//								txtHorario.setEnabled(false);
								btnBuscar.setEnabled(true);
								btnFiltro.setEnabled(true);
								btnNuevo.setEnabled(true);
								JOptionPane.showMessageDialog(null, "El Colaborardor Se Guardo Correctamente","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
							}else{
								JOptionPane.showMessageDialog(null, "Ocurrió un problema al almacenar el Colaborador", "Error", JOptionPane.ERROR_MESSAGE);
							}
						}
					}
				}			
			}
   }
	
	ActionListener filtro = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			btnBuscar.setEnabled(true);
			btnDeshacer.doClick();
			new Cat_Filtro_Empleado().setVisible(true);
		}
	};
	
	ActionListener editar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
		  if(cmbStatus.getSelectedItem().equals("No Contratable")) {
				JOptionPane.showMessageDialog(null, "Esta Restringido El Acceso a Modificar Ex-Empleados Con Este Estatus, \nSolicite Autorizacion De Direccion y Presentela A Gerencia De Sistemas Para Un Cambio","Aviso de Restricción",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("imagen/usuario-busquedaicono-4661-64.png"));
				return;  
		  }else {
			Obj_Empleados empleado = new Obj_Empleados().buscar(Integer.parseInt(txtFolioEmpleado.getText()));
				
				switch(empleado.getStatus_rotativo()){
				
					case 0: panelEnabledTrue();
							cmbHorarioRotativo.setSelectedIndex(0);
							rbHorario.setSelected(true);
							break;
							
					case 1: panelEnabledTrue();
							cmbHorarioRotativo.setSelectedIndex(1);
							rbHorario2.setEnabled(true);
							break;
							
					case 2: panelEnabledTrue();
							cmbHorarioRotativo.setSelectedIndex(2);
							rbHorario3.setEnabled(true);
							break;
				}
				txtFolioEmpleado.setEditable(false);
				btnEditar.setEnabled(false);
				btnNuevo.setEnabled(true);
				PerfilesActivos();
		  }		
		}		
	};
	
	public void PerfilesActivos(){
		
		if(new BuscarSQL().PerfilDeColaboradorActivo()){
			cmbEstablecimiento.setEnabled(true);
			cmbDepartamento.setEnabled(true);
			cmbPuesto.setEnabled(true);
			
			btnLimpiarPerfil.setEnabled(false);
			btnAgregarPerfil.setEnabled(false);
			
			cmbHorarioRotativo.setEnabled(true);
			
			btnHorarioNew.setEnabled(true);			
		}else{
			btnLimpiarPerfil.setEnabled(true);
			btnAgregarPerfil.setEnabled(true);
			
			btnHorario.setEnabled(false);
			btnHorarioNew.setEnabled(false);
		}
		
		
	}
	
	public void panelEnabledTrue(){	
		txtNombre.setEnabled(true);
		txtApPaterno.setEnabled(true);
		txtApMaterno.setEnabled(true);
		txtPensionAli.setEnabled(true);
//		cmbEstablecimiento.setEnabled(true);
//		cmbPuesto.setEnabled(true);
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
		cmbChecaCon.setEnabled(true);
		
		txtIngresoImss.setEnabled(true);
		txtVencimientoLicencia.setEnabled(true);
		txtemailEmpresa.setEditable(true);
		txtemailPersonal.setEditable(true);
		
		txtCalle.setEnabled(true);
		txtColonia.setEnabled(true);
		txtPoblacion.setEnabled(true);
		txtTelefono_Propio.setEnabled(true);
		txtRFC.setEnabled(true);
		txtCurp.setEnabled(true);
		
		cmbSexo.setEnabled(true);
		
		rbHorario.setEnabled(true);
		rbHorario2.setEnabled(true);
//		cmbDepartamento.setEnabled(true);
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
		btnHuella.setEnabled(true);
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
		cmbChecaCon.setEnabled(false);
		                                                                                                   
		txtIngresoImss.setEnabled(false);                                                                  
		txtVencimientoLicencia.setEnabled(false);   
		txtemailEmpresa.setEditable(false);
		txtemailPersonal.setEditable(false);
		                                                                                                   
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
//		btnExaminar.setEnabled(false);
		btnCamara.setEnabled(false);
		
		cmbEstadoCivil.setEnabled(false);
		cmbTipoDeSangre.setEnabled(false);
		cmbEscolaridad.setEnabled(false);
		cmbContratacion.setEnabled(false);
		cmbPresenciaFisica.setEnabled(false);
		
		btnLimpiarPerfil.setEnabled(false);
		btnAgregarPerfil.setEnabled(false);
		
		cmbHorarioRotativo.setEnabled(false);
		btnHuella.setEnabled(false);
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
	    
	    txtemailEmpresa.setText("");
	    txtemailPersonal.setText("");
	    lblFolioPerfil.setText("");
		txtPerfil.setText("");
	    
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
	    
		try {
			imagB  = Files.readAllBytes(Paths.get(System.getProperty("user.dir")+"/Iconos/Un.JPG")); 
			btnFoto.setIcon(crearIcon(imagB));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
//		 ImageIcon tmpIconDefault = new ImageIcon(System.getProperty("user.dir")+"/Iconos/Un.JPG");
//         Icon iconoDefault = new ImageIcon(tmpIconDefault.getImage().getScaledInstance(btnFoto.getWidth(), btnFoto.getHeight(), Image.SCALE_DEFAULT));
//         btnFoto.setIcon(iconoDefault);
         
		 ImageIcon file_status = new ImageIcon(System.getProperty("user.dir")+"/Iconos/Vigente.png");
         Icon iconoStatus = new ImageIcon(file_status.getImage().getScaledInstance(btnStatus.getWidth(), btnStatus.getHeight(), Image.SCALE_DEFAULT));
         btnStatus.setIcon(iconoStatus);
         
         btnHuella.setBackground(Color.RED);
         btnHuella.setForeground(Color.WHITE);
         btnHuella.setOpaque(true);
         
         datosHuella = null;
     	 datosHuella2 = null;
     	 tamañoHuella = 0;
     	 tamañoHuella2 = 0;
     	 
     	 cmbChecaCon.setSelectedIndex(0);
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
					
					try {
						imagB  = Files.readAllBytes(Paths.get(System.getProperty("user.dir")+"/Iconos/Un.JPG")); 
						btnFoto.setIcon(crearIcon(imagB));
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
//					 ImageIcon tmpIconDefault = new ImageIcon(System.getProperty("user.dir")+"/Iconos/Un.JPG");
//			         Icon iconoDefault = new ImageIcon(tmpIconDefault.getImage().getScaledInstance(btnFoto.getWidth(), btnFoto.getHeight(), Image.SCALE_DEFAULT));
//			         btnFoto.setIcon(iconoDefault);
			         
			         
//	copiar archivo de un directorio a otro --------------------------------------------------------------------------------------------------------------------
			         try{
			        	 FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"/Iconos/Un.JPG"); //inFile -> Archivo a copiar
			        	 FileOutputStream fos = new FileOutputStream(System.getProperty("user.dir")+"/tmp/tmp.jpg"); //outFile -> Copia del archivo
			        	 FileChannel inChannel = fis.getChannel(); 
			        	 FileChannel outChannel = fos.getChannel(); 
			        	 inChannel.transferTo(0, inChannel.size(), outChannel); 
			        	 fis.close(); 
			        	 fos.close();
			        	 }catch (IOException ioe) {
			        	 System.err.println("Error al Generar Copia");
			        	 }
//	fin de copiar archivo -------------------------------------------------------------------------------------------------------------------------------------
			         
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
			rbHorario.setSelected(true);
			panelLimpiar();
			rbHorario2.setEnabled(false);
			panelEnabledFalse();
			txtFolioEmpleado.setEditable(true);
			txtFolioEmpleado.setEnabled(true);
			txtFolioEmpleado.requestFocus();
			btnEditar.setEnabled(false);
			btnNuevo.setEnabled(true);
//			txtHorario.setEnabled(false);
			btnHorario.setEnabled(false);
			btnHorario2.setEnabled(false);
			btnHorario3.setEnabled(false);
			
			btnLimpiarH2.setEnabled(false);
			btnLimpiarH3.setEnabled(false);
			
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
	   
	ActionListener Reporte_Datos_Checador = new ActionListener(){
		public void actionPerformed(ActionEvent e){
				new Cat_Reportes_Datos_Checador().setVisible(true);
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
	
	ActionListener opLimpiarH2 = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			lblFolioHorario2.setText("");
			txtHorario2.setText("");
		}
	};
	
	ActionListener opLimpiarH3 = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			lblFolioHorario3.setText("");
			txtHorario3.setText("");
		}
	};
	
	ActionListener opGenerarHorairo = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(txtFolioEmpleado.getText().equals("")){
				JOptionPane.showMessageDialog(null,"Necesita Seleccionar Primero Un Colaborador", "Mensaje!",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}else{
				if(lblFolioHorario1.getText().equals("")){
					new Cat_Horarios().setVisible(true);
				}else{
			     new Cat_Horarios(Integer.valueOf(lblFolioHorario1.getText().toString()),"NO").setVisible(true);
				}
			}
		}
	};
	
	ActionListener opGenerarInformacionDeColaboradores = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(txtFolioEmpleado.getText().equals("")){
				JOptionPane.showMessageDialog(null,"Necesita Seleccionar Primero Un Colaborador", "Mensaje!",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}else{
			new Cat_Reportes_De_Informacion_De_Movimientos_De_Colaboradores(Integer.valueOf(txtFolioEmpleado.getText().toString()),txtNombre.getText()+" "+txtApPaterno.getText()+" "+txtApMaterno.getText()).setVisible(true);
			}
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
		
		
		if(txtPerfil.getText().trim().equals("")){
			if(!new BuscarSQL().PerfilDeColaboradorActivo()){
				error +="Perfil\n";
			}
		}
		
		switch(cmbHorarioRotativo.getSelectedIndex()){
		case 0:	if(txtHorario.getText().equals("")) 		error+= "-Horario\n"; break;
		case 1: if(txtHorario.getText().equals("")) 		error+= "-Horario 2\n";
				if(txtHorario2.getText().equals("")) 		error+= "-Horario 3\n";break;
		case 2: if(txtHorario.getText().equals("")) 		error+= "-Horario\n";
				if(txtHorario2.getText().equals("")) 		error+= "-Horario 2\n";
				if(txtHorario3.getText().equals("")) 		error+= "-Horario 3\n";break;
		}
		if(cmbChecaCon.getSelectedIndex()==0)	error+= "-Checa Con: (SELECCIONA UNA FORMA)\n";
		
		if(cmbContratacion.getSelectedIndex()==0)	error+= "-Contrato\n";
		if(cmbSueldo.getSelectedItem().equals("Selecciona un Sueldo")) error += "-Sueldo\n";
		if(cmbBono.getSelectedItem().equals("Selecciona un Bono")) error += "-Bono\n";
		if(cmbPrestamos.getSelectedItem().equals("Selecciona un Rango de Prestamo")) error += "-Rango de Prestamo\n";
		if(fechaNull.equals("null"))error+= "-Fecha de Nacimiento\n";	
		if(fechaIngresoNull.equals("null"))error += "-Fecha de ingreso\n";
		if(cmbPresenciaFisica.getSelectedIndex()==0)	error+= "-Presencia Fisica\n";
		return error;
	}
	
	public class Llamar_Camara extends Cat_Camara{
		
		public Llamar_Camara(){
			
			btnCapturar.addActionListener(opFoto);
		}
		
		boolean imagenCargada = false ;
		String rutaFoto = "";
		ActionListener opFoto = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == btnCapturar){
					
					rutaFoto = System.getProperty("user.dir")+"/tmp/tmp_update/";
					File folder = new File(rutaFoto);
	            	folder.mkdirs();
	            	
					BufferedImage image = webcam.getImage();
					try {
						ImageIO.write(image, "JPG", new File(rutaFoto+"tmp.jpg"));
						imagenCargada = true ;
						System.out.println(rutaFoto);
						
						try {
							imagB  = Files.readAllBytes(Paths.get(rutaFoto+"tmp.jpg"));
							btnFoto.setIcon(crearIcon(imagB));
						} catch (IOException e1) {
							e1.printStackTrace();
						}
     					
//						ImageIcon tmpIconDefault = new ImageIcon(rutaFoto+"tmp.jpg");
//				        Icon btnIconoDefault = new ImageIcon(tmpIconDefault.getImage().getScaledInstance(btnFoto.getWidth(), btnFoto.getHeight(), Image.SCALE_DEFAULT));
//				        btnFoto.setIcon(btnIconoDefault);
				        
//				        Icon lblIconoDefault = new ImageIcon(tmpIconDefault.getImage().getScaledInstance(lblVistaPrevia.getWidth(), lblVistaPrevia.getHeight(), Image.SCALE_DEFAULT));
				        lblVistaPrevia.setIcon(crearIcon(imagB));
						
					} catch (Exception ex) {
						imagenCargada = false ;
					}	
				}	
			}
		};
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
			
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Filter-List-icon32.png"));
			panel.setBorder(BorderFactory.createTitledBorder("Filtro Horario"));	
			this.setModal(true);
			panel.add(getPanelTabla()).setBounds(20,50,800,400);
			panel.add(txtFolioHorario).setBounds(20,20,80,20);
			panel.add(txtNombre).setBounds(100,20,720,20);
			
			cont.add(panel);
			txtNombre.setToolTipText("Filtro De Seleccion De Horario");
			
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
			tabla.getTableHeader().setReorderingAllowed(false) ;
			tabla.getColumnModel().getColumn(0).setHeaderValue("Folio");
			tabla.getColumnModel().getColumn(0).setMinWidth(80);
			tabla.getColumnModel().getColumn(1).setHeaderValue("Nombre");
			tabla.getColumnModel().getColumn(1).setMinWidth(720);
			
			tabla.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 	
			tabla.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
			Statement s;
			ResultSet rs;
			try {
				s = new Connexion().conexion().createStatement();
				rs = s.executeQuery( " select tb_horarios.folio,tb_horarios.nombre from tb_horarios "
						            +"   where folio not in(select horario from tb_empleado with (nolock) where status in(1,2,3,6) "
						            +" union all select horario2 from tb_empleado with (nolock) where status in(1,2,3,6) and status_h2=1 )or folio=11 order by nombre asc");
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
//TODO filtro Busqueda de Colaborador
	public class Cat_Filtro_Empleado extends JDialog {
		Container cont = getContentPane();
		JLayeredPane campo = new JLayeredPane();
		Connexion con = new Connexion();
		Obj_tabla ObjTabf =new Obj_tabla();
		int columnas = 9,checkbox=-1;
		public void init_tablaf(){
	    	this.tablaf.getColumnModel().getColumn(0).setMinWidth(50);
	    	this.tablaf.getColumnModel().getColumn(0).setMaxWidth(50);
	    	this.tablaf.getColumnModel().getColumn(1).setMinWidth(270);
	    	this.tablaf.getColumnModel().getColumn(2).setMinWidth(120);
	    	this.tablaf.getColumnModel().getColumn(3).setMinWidth(250);
			String comandof="exec sp_filtro_empleado";
			String basedatos="26",pintar="si";
			ObjTabf.Obj_Refrescar(tablaf,modelf, columnas, comandof, basedatos,pintar,checkbox);
	    }
		
		@SuppressWarnings("rawtypes")
		public Class[] base (){
			Class[] types = new Class[columnas];
			for(int i = 0; i<columnas; i++){types[i]= java.lang.Object.class;}
			return types;
		}
		
		 public DefaultTableModel modelf = new DefaultTableModel(null, new String[]{"Folio","Colaborador","Establecimiento","Puesto","Sueldo","Bono","Estatus","Fuente Sodas","Gafete"}){
			 @SuppressWarnings("rawtypes")
				Class[] types = base();
				@SuppressWarnings("rawtypes")
				public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
				public boolean isCellEditable(int fila, int columna){return false;}
		    };
		    JTable tablaf = new JTable(modelf);
			public JScrollPane scroll_tablaf = new JScrollPane(tablaf);
		     @SuppressWarnings("rawtypes")
		    private TableRowSorter trsfiltro;
		
		JTextField txtFolioFiltroEmpleado  = new Componentes().text(new JCTextField(), "Teclea Aqui Para Buscar En La Tabla", 500, "String");
		
		String establecimientos[] = new Obj_Establecimiento().Combo_Establecimiento();
		@SuppressWarnings("rawtypes")
		JComboBox cmbEstablecimientos = new JComboBox(establecimientos);

		@SuppressWarnings("rawtypes")
		public Cat_Filtro_Empleado(){
			this.setSize(1040,650);
			this.setModal(true);
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/filter_icon&16.png"));
			this.setTitle("Filtro de Empleados");
			campo.setBorder(BorderFactory.createTitledBorder("Filtro De Empleado"));
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
			trsfiltro = new TableRowSorter(modelf); 
			tablaf.setRowSorter(trsfiltro); 
			
			campo.add(scroll_tablaf).setBounds(15,42,1000,565);
			campo.add(txtFolioFiltroEmpleado).setBounds(15,20,300,20);
			campo.add(cmbEstablecimientos).setBounds(315,20, 180, 20);
			
			init_tablaf();
			agregar(tablaf);
			cont.add(campo);
			txtFolioFiltroEmpleado.addKeyListener(opFiltrof);
			cmbEstablecimientos.addActionListener(opFiltro);
//          asigna el foco al JTextField del nombre deseado al arrancar la ventana
            this.addWindowListener(new WindowAdapter() {
                    public void windowOpened( WindowEvent e ){
                    	txtFolioFiltroEmpleado.requestFocus();
                 }
            });
              
//         pone el foco en el txtFolio al presionar la tecla scape
              getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                 KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "foco");
              
              getRootPane().getActionMap().put("foco", new AbstractAction(){
                  @Override
                  public void actionPerformed(ActionEvent e)
                  {
                	  txtFolioFiltroEmpleado.setText("");
                	  txtFolioFiltroEmpleado.requestFocus();
                  }
              });
              
              tablaf.addKeyListener(seleccionEmpleadoconteclado);
              txtFolioFiltroEmpleado.addKeyListener(PasarATabla);
              
//            pone el foco en la tabla al presionar f4
              getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                 KeyStroke.getKeyStroke(KeyEvent.VK_F4 , 0), "dtabla");
              
              getRootPane().getActionMap().put("dtabla", new AbstractAction(){
                  @Override
                  public void actionPerformed(ActionEvent e)
                  {
                	tablaf.requestFocus();
                	iniciarSeleccionConTeclado();
                  }
              });
              
				KeyStroke tab = KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0);
				tablaf.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(tab, "TAB");
				
				tablaf.getActionMap().put("TAB", new AbstractAction(){
	                 public void actionPerformed(ActionEvent e)
	                 {
	                	iniciarSeleccionConTeclado();
	                 }
	            });
		}
		
		KeyListener PasarATabla = new KeyListener() {
			public void keyTyped(KeyEvent e){}
			public void keyReleased(KeyEvent e) {}
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_DOWN){
					tablaf.requestFocus();
					tablaf.getSelectionModel().setSelectionInterval(0,0);;
				}
			}
		};
		
		private void agregar(final JTable tbl) {
	        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
		        public void mouseClicked(MouseEvent e) {
		        	
		        	if(e.getClickCount() == 1){
		        		iniciarSeleccionConTeclado();
		        	}
		        	if(e.getClickCount() == 2){
		    			fila = tablaf.getSelectedRow();
		    			Object folio =  tablaf.getValueAt(fila, 0).toString().trim();
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
				tablaf.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(enter, "Enter");
				
				tablaf.getActionMap().put("Enter", new AbstractAction(){
	                 public void actionPerformed(ActionEvent e)
	                 {
	                	iniciarSeleccionConTeclado();
	                	
	                	fila=tablaf.getSelectedRow();
	     				String folio = tablaf.getValueAt(fila,0).toString().trim();
	     					
	     				txtFolioEmpleado.setText(folio);
	     				btnBuscar.doClick();
	     				dispose();
	                 }
	            });
			}
			public void keyPressed(KeyEvent e){}
			public void keyReleased(KeyEvent e){}
		};
		
		
		KeyListener opFiltrof = new KeyListener(){
			public void keyReleased(KeyEvent arg0) {
				ObjTabf.Obj_Filtro(tablaf, txtFolioFiltroEmpleado.getText().toUpperCase(), columnas,tablaf);
			}
			public void keyTyped(KeyEvent arg0) {}
			public void keyPressed(KeyEvent arg0) {}		
		};
		
		
		ActionListener opFiltro = new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				new Obj_Filtro_Dinamico(tablaf,"Nombre Completo", txtFolioFiltroEmpleado.getText().toUpperCase(),"Establecimiento",cmbEstablecimientos.getSelectedItem()+"", "", "", "", "");
			}
		};
		
	}

	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Empleados().setVisible(true);
		}catch(Exception e){	}
	}
//TODO motivos de renuncia	
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
		
	}
	
	public class Cat_Filtro_Perfiles_Seleccion extends JDialog {
		
		Container cont = getContentPane();
		JLayeredPane campo = new JLayeredPane();
		
	   	Object[][] arreglo = new BuscarTablasModel().filtro_de_perfiles_de_puestos(Integer.valueOf(txtFolioEmpleado.getText().trim()));
	    public DefaultTableModel model = new DefaultTableModel(arreglo, new String[]{"Folio","Perfil","Establecimiento","Departamento", "Puesto"} ){
            
			@SuppressWarnings({ "rawtypes" })
			Class[] types = new Class[]{
	                   java.lang.Object.class, 
	                   java.lang.Object.class, 
	                   java.lang.Object.class,
	                   java.lang.Object.class,
	                   java.lang.Object.class
	                    
	    };
			@SuppressWarnings({ "rawtypes" })
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
		
	    JTable tabla = new JTable(model);
		JScrollPane scroll = new JScrollPane(tabla,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		@SuppressWarnings("rawtypes")
		private TableRowSorter trsfiltro;
		
		JTextField txtFiltroPuestoReporta = new JTextField();
		
		@SuppressWarnings("rawtypes")
		public Cat_Filtro_Perfiles_Seleccion(){
			
			this.setModal(true);
			
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/filter_icon&16.png"));
			this.setTitle("Filtro De Perfiles");
			campo.setBorder(BorderFactory.createTitledBorder("Selecciones Un Perfil"));
			trsfiltro = new TableRowSorter(model); 
			tabla.setRowSorter(trsfiltro);  
			
			campo.add(txtFiltroPuestoReporta).setBounds(15,20,329,20);
			campo.add(scroll).setBounds(15,42,760,510);
			
			render();
			agregar(tabla);
			
			cont.add(campo);
			
			txtFiltroPuestoReporta.addKeyListener(opFiltro);
			
			this.setSize(800,600);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			
//          asigna el foco al JTextField del nombre deseado al arrancar la ventana
            this.addWindowListener(new WindowAdapter() {
                    public void windowOpened( WindowEvent e ){
                    	txtFiltroPuestoReporta.requestFocus();
                 }
            });
              
//         pone el foco en el txtFolio al presionar la tecla scape
              getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                 KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "foco");
              
              getRootPane().getActionMap().put("foco", new AbstractAction(){
                  @Override
                  public void actionPerformed(ActionEvent e)
                  {
                	  txtFiltroPuestoReporta.setText("");
                      txtFiltroPuestoReporta.requestFocus();
                  }
              });
              
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
 	     
		KeyListener opFiltro = new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {
				int[] columnas = {0,1,2,3,4};
				new Obj_Filtro_Dinamico_Plus(tabla,txtFiltroPuestoReporta.getText().toUpperCase(),columnas);
			}
			public void keyPressed(KeyEvent e) {}
		};
		
		private void agregar(final JTable tbl) {
	        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
		        public void mouseClicked(MouseEvent e) {
		        	
		        	if(e.getClickCount() == 2){
		        		
		        		int fila = tabla.getSelectedRow();
		    			int folio =  Integer.valueOf(tabla.getValueAt(fila, 0).toString().trim());
		    			dispose();
		    			
		    			Obj_Perfil_De_Puestos perfil = new Obj_Perfil_De_Puestos().buscar(folio);
			    			
		    			lblFolioPerfil.setText(perfil.getFolio()+"");
		    			txtPerfil.setText(perfil.getPerfil()+"");
		    			cmbEstablecimiento.setSelectedItem(perfil.getEstablecimiento());
		    			cmbDepartamento.setSelectedItem(perfil.getDepartameto());
		    			cmbPuesto.setSelectedItem(perfil.getPuesto());
		    			
		    			if(JOptionPane.showConfirmDialog(null, "Desea Asignar Los Sueldos Y Bonos Del Perfil Al Colaborador?") == 0){
		    				
							txtSalarioDiario.setText(perfil.getSalario_diario()+"");
							txtSalarioDiarioIntegrado.setText(perfil.getSalario_diario_integrado()+"");
							cmbSueldo.setSelectedItem(perfil.getSueldo()+"");
							cmbBono.setSelectedItem(perfil.getBonocomplemento()+"");
							cmbBonopuntualidad.setSelectedItem(perfil.getBono_asistencia()+"");
							cmbBonoAsistencia.setSelectedItem(perfil.getBono_puntualidad()+"");
							cmbPrestamos.setSelectedIndex(perfil.getPrestamo());

		    			}
		    			
		        	}
		        }
	        });
	    }
		
	   	private void render(){		
	   		this.tabla.getTableHeader().setReorderingAllowed(false) ;
			DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
			tcr.setHorizontalAlignment(SwingConstants.CENTER);
			

			tabla.getColumnModel().getColumn(0).setMinWidth(50);
			tabla.getColumnModel().getColumn(1).setMinWidth(320);
			tabla.getColumnModel().getColumn(2).setMinWidth(120);
			tabla.getColumnModel().getColumn(3).setMinWidth(120);
			tabla.getColumnModel().getColumn(4).setMinWidth(120);

			tabla.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12)); 
			tabla.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
			tabla.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
			tabla.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
			tabla.getColumnModel().getColumn(4).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
		}
	   	
	}
	
//TODO alimentacion_de_beneficiario
	public class Cat_Alimentacion_De_Beneficiario_Del_Colaborador extends JDialog{
		Container cont = getContentPane();
		JLayeredPane panel = new JLayeredPane();
		
		JTextField txtFoliocolaborador   = new Componentes().text(new JCTextField(), "Folio Colaborador"      ,   6, "Int"   );
		JTextField txtNombreColaborador  = new Componentes().text(new JCTextField(), "Nombre del Colaborador" , 350, "String");
		JTextField txtNombreBeneficiario = new Componentes().text(new JCTextField(), "Nombre del Beneficiario", 350, "String");
		JTextField txtRFCBeneficiario    = new Componentes().text(new JCTextField(), "RFC del Beneficiario"   ,  15, "String");
		String parentesco[] = new Obj_Empleados().Combo_Parentesco();
		  @SuppressWarnings({ "rawtypes" })
		JComboBox cmbParentesco=new JComboBox(parentesco);
		
		JDateChooser FechaNacimiento = new JDateChooser();
		JButton btnGuardarBen = new JCButton("Guardar","Guardar.png","Azul");
		JButton btnEditarBen  = new JCButton("Editar","editara.png","Azul");
		
//		String FolioC,String NombreC, String BeneficiarioC,String RFCBeneficiarioC,String Parentesco,String FechaNacimientoC
		public Cat_Alimentacion_De_Beneficiario_Del_Colaborador(){
			this.setSize(450,200);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			this.setTitle("Alimentacion De Datos Del Beneficiario Del Colaborador");
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/de-configuracion-de-usuario-icono-7374-32.png"));
			this.panel.setBorder(BorderFactory.createTitledBorder("Alimente Los Datos Del Beneficiario"));
			this.setModal(true);
			
			int x=10,y=15,width=110,height=20;
			panel.add(new JLabel("Folio Colaborador: ")).setBounds(x       ,y     ,width    ,height   );
			panel.add(txtFoliocolaborador).setBounds              (x+90    ,y     ,width    ,height   );
			panel.add(new JLabel("Colaborador: ")).setBounds      (x       ,y+=25 ,width    ,height   );
			panel.add(txtNombreColaborador).setBounds             (x+90    ,y     ,width*3  ,height   );
			panel.add(new JLabel("Beneficiario: ")).setBounds     (x       ,y+=25 ,width    ,height   );
			panel.add(txtNombreBeneficiario).setBounds            (x+90    ,y     ,width*3  ,height   );
			panel.add(new JLabel("RFC Beneficiario: ")).setBounds (x       ,y+=25 ,width    ,height   );
			panel.add(txtRFCBeneficiario).setBounds               (x+90    ,y     ,width*3  ,height   );
			panel.add(new JLabel("Parentesco: ")).setBounds       (x       ,y+=25 ,width    ,height   );
			panel.add(cmbParentesco).setBounds                    (x+90    ,y     ,width*3  ,height   );
			panel.add(new JLabel("Fecha Nacimiento:")).setBounds  (x       ,y+=25 ,width    ,height   );
			panel.add(FechaNacimiento).setBounds                  (x+90    ,y     ,width    ,height   );
			panel.add(btnEditarBen).setBounds                     (215     ,y     ,width-5  ,height   );
			panel.add(btnGuardarBen).setBounds                    (325     ,y     ,width-5  ,height   );
			
			txtFoliocolaborador.setEditable(false);
			txtNombreColaborador.setEditable(false);
			
			txtNombreBeneficiario.setEditable(false);
			txtRFCBeneficiario.setEditable(false);
			cmbParentesco.setEnabled(false);
			FechaNacimiento.setEnabled(false);
			btnGuardarBen.setEnabled(false);
			
			txtFoliocolaborador.setText(txtFolioEmpleado.getText() );
			txtNombreColaborador.setText(txtNombre.getText().trim()+" "+txtApPaterno.getText().trim()+" "+txtApMaterno.getText().trim());
			txtNombreBeneficiario.setText(tablacompleta[0][74].toString());
			txtRFCBeneficiario.setText(tablacompleta[0][75].toString());
			cmbParentesco.setSelectedItem(tablacompleta[0][76].toString());
			FechaNacimiento.setDate(fecha(tablacompleta[0][77].toString()) );
			btnGuardarBen.addActionListener(opGuardar);
			btnEditarBen.addActionListener(opEditarBen);
			cont.add(panel);
		}
		
		ActionListener opEditarBen= new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtNombreBeneficiario.setEditable(true);
				txtRFCBeneficiario.setEditable(true);
				cmbParentesco.setEnabled(true);
				FechaNacimiento.setEnabled(true);
				btnGuardarBen.setEnabled(true);
				txtNombreBeneficiario.requestFocus();
			}
		};
		
		ActionListener opGuardar = new ActionListener(){
				public void actionPerformed(ActionEvent e){
							   if(validaCampos()!="") {
									JOptionPane.showMessageDialog(null, validaCampos(), "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
									return;
							   }else{
								   re.setFolio(Integer.valueOf(txtFolioEmpleado.getText().toString()));
								   re.setNombre_beneficiario(txtNombreBeneficiario.getText().toUpperCase().trim());
								   re.setRfc_beneficiario(txtRFCBeneficiario.getText().toUpperCase().trim());
								   re.setParentesco_beneficiario(cmbParentesco.getSelectedItem().toString());
								   re.setFecha_nacimiento_beneficiario(new SimpleDateFormat("dd/MM/yyyy").format(FechaNacimiento.getDate()));
								   
								   if(re.actualizarbeneficiario()){
										JOptionPane.showMessageDialog(null, "Se Actualizaron Los Datos Del Beneficiario Correctamente","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
										btnBuscar.doClick();
										dispose();
										txtFoliocolaborador.requestFocus();
										return;
								   }
										return;
							   }
				}
		};
			
			private String validaCampos(){
				String fechanacimientoNull = FechaNacimiento.getDate()+"";		
				String error="";
				if(txtNombreBeneficiario.getText().equals(""))error += "Es Requerido El Nombre Del Beneficiario\n";
				if(txtRFCBeneficiario.getText()   .equals(""))error += "Es Requerido El RFC Del Beneficiario\n";
				if(cmbParentesco.getSelectedIndex()==0)error += "Es Requerido Seleccionar Un Parentesco\n";
				if(fechanacimientoNull.equals("null"))error += "Es Requerido Seleccionar una Fecha De Nacimiento\n";	
				return error;
			}
	}
	
	//--TODO(Captura De Huella Digital(inicio))	--------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public class Cat_Huellas_Personalizado extends JDialog{
		
		Container cont = getContentPane();
		JLayeredPane panel = new JLayeredPane();
		
	    private JLabel lblMarcoHuella = new JLabel("");
	    
	    private JTextArea txtArea = new JTextArea();
	    private JScrollPane jScrollPane1 = new JScrollPane();
	    
	    private JCButton btnGuardar = new JCButton("Guardar","guardar.png","Azul");
	    private JCButton btnVerificar = new JCButton("Verificar","buscar.png","Azul");
	    
		//Varible que permite iniciar el dispositivo de lector de huella conectado
		// con sus distintos metodos.
		private DPFPCapture Lector = DPFPGlobal.getCaptureFactory().createCapture();
	//------------------------------------------------------------------------------------------------------------------------	
		//Varible que permite establecer las capturas de la huellas, para determina sus caracteristicas
		// y poder estimar la creacion de un template de la huella para luego poder guardarla
		private DPFPEnrollment Reclutador = DPFPGlobal.getEnrollmentFactory().createEnrollment();
		
		//Variable que para crear el template de la huella luego de que se hallan creado las caracteriticas
		// necesarias de la huella si no ha ocurrido ningun problema
		private DPFPTemplate template;
	//------------------------------------------------------------------------------------------------------------------------	
		//Varible que permite establecer las capturas de la huellas, para determina sus caracteristicas
		// y poder estimar la creacion de un template de la huella para luego poder guardarla
		private DPFPEnrollment Reclutador2 = DPFPGlobal.getEnrollmentFactory().createEnrollment();
		
		//Variable que para crear el template de la huella luego de que se hallan creado las caracteriticas
		// necesarias de la huella si no ha ocurrido ningun problema
		private DPFPTemplate template2;
	//------------------------------------------------------------------------------------------------------------------------	
		public String TEMPLATE_PROPERTY = "template";
			
		//Esta variable tambien captura una huella del lector y crea sus caracteristcas para auntetificarla
		// o verificarla con alguna guarda en la BD
		private DPFPVerification Verificador = DPFPGlobal.getVerificationFactory().createVerification();
		
		
		//declaracion de Bordes
		Border blackline;
		
		int index_huella = 1;
		
		JLabel lblImageHuella1 = new JLabel("");
		JLabel lblImageHuella2 = new JLabel("");
		
		JLabel lblHuella1 = new JLabel("Huella 1");
		JLabel lblHuella2 = new JLabel("Huella 2");
		
		 ImageIcon tmphuellaRoja = new ImageIcon(System.getProperty("user.dir")+"/Imagen/huella_faltante.png");
		 ImageIcon tmphuellaVerde = new ImageIcon(System.getProperty("user.dir")+"/Imagen/huella_cargada.png");
		 
		public Cat_Huellas_Personalizado() {
			this.setModal(true);
			lblMarcoHuella.setBorder(BorderFactory.createTitledBorder(blackline, ""));
			lblMarcoHuella.setBackground(Color.WHITE);
			lblMarcoHuella.setOpaque(true);
			
			lblHuella1.setFont(new java.awt.Font("Algerian",0,20));
			lblHuella2.setFont(new java.awt.Font("Algerian",0,20));
			
			txtArea.setLineWrap(true); 
			txtArea.setWrapStyleWord(true);
			
	        this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/encuesta.png"));
			panel.setBorder(BorderFactory.createTitledBorder("huella"));
			
			this.setTitle("Captura de huella del personal");
			this.setSize(800,415);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			
			jScrollPane1.setViewportView(txtArea);
			
			panel.add(lblMarcoHuella).setBounds(15, 15, 350, 320);
			panel.add(btnVerificar).setBounds(15, 345, 120, 30);
			panel.add(btnGuardar).setBounds(240, 345, 120, 30);
			
			panel.add(lblImageHuella1).setBounds(470, 15, 50, 50);
			panel.add(lblHuella1).setBounds(450, 65, 100, 30);
			panel.add(lblImageHuella2).setBounds(620, 15, 50, 50);
			panel.add(lblHuella2).setBounds(600, 65, 100, 30);
			panel.add(jScrollPane1).setBounds(380, 100, 400, 230);
			
			cont.add(panel);
			
			banderaHuellas(tmphuellaRoja.getImage(), lblImageHuella1);
			banderaHuellas(tmphuellaRoja.getImage(), lblImageHuella2);
			
			btnGuardar.addActionListener(opGuardar);
			btnVerificar.addActionListener(opVerificar);

	        this.addWindowListener(new java.awt.event.WindowAdapter() {
	            public void windowClosing(java.awt.event.WindowEvent evt) {
	                formWindowClosing(evt);
	            }
	            public void windowOpened(java.awt.event.WindowEvent evt) {
	                formWindowOpened(evt);
	            }
	        });
		}

		private void formWindowOpened(java.awt.event.WindowEvent evt){
	        Iniciar();
	        start();
	        EstadoHuellas();
	        btnGuardar.setEnabled(false);
	        //btnIdentificar.setEnabled(false);
	        btnVerificar.setEnabled(false);
	        //btnSalir.grabFocus();

	    }

	    private void formWindowClosing(java.awt.event.WindowEvent evt) {
	        stop();
	    }
	    
	    public  void stop(){
	        Lector.stopCapture();
	        EnviarTexto("No se está usando el Lector de Huella Dactilar ");
		}
	    
	    protected void Iniciar(){
	    	
	    	   Lector.addDataListener(new DPFPDataAdapter() {
		    	    @Override 
		    	    public void dataAcquired(final DPFPDataEvent e) {
			    	    SwingUtilities.invokeLater(new Runnable() {	
			    	    	public void run() {
			    	    		EnviarTexto("La Huella Digital ha sido Capturada");
			    	    		ProcesarCaptura(e.getSample());
			    	    	}
				    	});
		    	    }
	    	   });

	    	   Lector.addReaderStatusListener(new DPFPReaderStatusAdapter() {
		    	    @Override 
		    	    public void readerConnected(final DPFPReaderStatusEvent e) {
			    	    SwingUtilities.invokeLater(new Runnable() {	
				    	    	public void run() {
				    	    		EnviarTexto("El Sensor de Huella Digital esta Activado o Conectado");
				    	    	}
			    	    	});
		    	    }
	    	    
		    	    @Override 
		    	    public void readerDisconnected(final DPFPReaderStatusEvent e) {
			    	    SwingUtilities.invokeLater(new Runnable() {	
			    	    	public void run() {
			    	    		EnviarTexto("El Sensor de Huella Digital esta Desactivado o no Conectado");
			    	    	}
			    	    });
		    	    }
	    	   });

	    	   Lector.addSensorListener(new DPFPSensorAdapter() {
	    		   @Override 
		    	    public void fingerTouched(final DPFPSensorEvent e) {
			    	    SwingUtilities.invokeLater(new Runnable() {	
			    	    	public void run() {
			    	    			EnviarTexto("El dedo ha sido colocado sobre el Lector de Huella");
			    	    	}
			    	    });
		    	    }
	    		   
		    	    @Override 
		    	    public void fingerGone(final DPFPSensorEvent e) {
			    	    SwingUtilities.invokeLater(new Runnable() {	
			    	    	public void run() {
			    	    		EnviarTexto("El dedo ha sido quitado del Lector de Huella");
				    	    }
		    	    	});
		    	    }
	    	   });

	    	   Lector.addErrorListener(new DPFPErrorAdapter(){
	    		   @SuppressWarnings("unused")
					public void errorReader(final DPFPErrorEvent e){
			    	    SwingUtilities.invokeLater(new Runnable() {  
			    	    	public void run() {
			    	    		EnviarTexto("Error: "+e.getError());
				    	    }
				    	});
		    	    }
	    	   });
	    	}
	    
	    public  void start(){
	    	Lector.startCapture();
	    	EnviarTexto("Utilizando el Lector de Huella Dactilar ");
	    }
	    
	    public void EnviarTexto(String string) {
	        txtArea.append("* "+string + "\n");
	    }
	    
	    public DPFPFeatureSet featuresinscripcion;
	    public DPFPFeatureSet featuresverificacion;
	    @SuppressWarnings("incomplete-switch")
		public  void ProcesarCaptura(DPFPSample sample){
	     // Procesar la muestra de la huella y crear un conjunto de características con el propósito de inscripción.
	     featuresinscripcion = extraerCaracteristicas(sample, DPFPDataPurpose.DATA_PURPOSE_ENROLLMENT);

	     // Procesar la muestra de la huella y crear un conjunto de características con el propósito de verificacion.
	     featuresverificacion = extraerCaracteristicas(sample, DPFPDataPurpose.DATA_PURPOSE_VERIFICATION);

	     // Comprobar la calidad de la muestra de la huella y lo añade a su reclutador si es bueno
	     if (featuresinscripcion != null)
		         try{
			         System.out.println("Las Caracteristicas de la Huella han sido creada");
			         
			         
			         if(index_huella==1){
			        	 Reclutador.addFeatures(featuresinscripcion);// Agregar las caracteristicas de la huella a la plantilla a crear
			         }else{
			        	 Reclutador2.addFeatures(featuresinscripcion);// Agregar las caracteristicas de la huella a la plantilla a crear
			         }
			
			         // Dibuja la huella dactilar capturada.
			         Image image=CrearImagenHuella(sample);
			         DibujarHuella(image);
			
			         btnVerificar.setEnabled(true);
			         //btnIdentificar.setEnabled(true);
		
		         }catch (DPFPImageQualityException ex) {
		        	 System.err.println("Error: "+ex.getMessage());
		         }finally {
		        	 
		        	 DPFPEnrollment Reclutador_testigo = (index_huella == 1)?Reclutador:Reclutador2;
		        	 
		        	 if(index_huella == 1){
		        		 EstadoHuellas();
		        	 }else{
		        		 EstadoHuellas2();
		        	 }

		        	 
	         // Comprueba si la plantilla se ha creado.
		        	 switch(Reclutador_testigo.getTemplateStatus()){
		        	 	case TEMPLATE_STATUS_READY:	// informe de éxito y detiene  la captura de huellas
		        	 				
	                	
					                if(index_huella == 1){
					                	setTemplate(Reclutador_testigo.getTemplate());
					                	index_huella=2;
					                	
					                	 EnviarTexto("Huella Capturada Correctamente, Favor De Ingresar La Siguiente Huella");
								    	    //btnIdentificar.setEnabled(false);
								                btnVerificar.setEnabled(true);
//								                btnGuardar.setEnabled(true);
								                btnGuardar.grabFocus();
								        		banderaHuellas(tmphuellaVerde.getImage(), lblImageHuella1);
									        	JOptionPane.showMessageDialog(null, "Huella Capturada Correctamente, Favor De Ingresar La Siguiente Huella", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
					                }else{
					                	stop();
					                	setTemplate2(Reclutador_testigo.getTemplate());
					                	
					                	 EnviarTexto("Listo, Las Huellas Han Sido Capturada Correctamente Ahora Puede Guardarla");
								    	    //btnIdentificar.setEnabled(false);
//								                btnVerificar.setEnabled(true);
								                btnGuardar.setEnabled(true);
								                btnGuardar.grabFocus();
								        		banderaHuellas(tmphuellaVerde.getImage(), lblImageHuella2);
								        		guardarHuella();
												Reclutador.clear();
												Reclutador2.clear();
												banderaHuellas(tmphuellaRoja.getImage(), lblImageHuella1);
												banderaHuellas(tmphuellaRoja.getImage(), lblImageHuella2);
												lblMarcoHuella.setIcon(null);
									        	JOptionPane.showMessageDialog(null, "Huellas Capturadas Correctamente", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
												dispose();
//												System.exit(0);	
					                }
		                break;

			    	    case TEMPLATE_STATUS_FAILED: // informe de fallas y reiniciar la captura de huellas
				    	    	Reclutador.clear();
				    	    	Reclutador2.clear();
				    	    	index_huella = 1;
				    			banderaHuellas(tmphuellaRoja.getImage(), lblImageHuella1);
				    			banderaHuellas(tmphuellaRoja.getImage(), lblImageHuella2);
				                stop();
					    	    EstadoHuellas();
					    	    setTemplate(null);//"CapturaHuella.this"
					    	    JOptionPane.showMessageDialog(null, "La Plantilla de la Huella no pudo ser creada, Repita el Proceso", "Inscripcion de Huellas Dactilares", JOptionPane.ERROR_MESSAGE);
					    	    start();
			    	    break;
		        	 }
	    	     }
	    }
	    
	    public DPFPTemplate getTemplate() {
	        return template;
	    }

	    public void setTemplate(DPFPTemplate template) {
	        DPFPTemplate old = this.template;
	        this.template = template;
	        firePropertyChange(TEMPLATE_PROPERTY, old, template);
	    }
	    
	    public DPFPTemplate getTemplate2() {
	        return template2;
	    }
	    
	    public void setTemplate2(DPFPTemplate template) {
	        DPFPTemplate old = this.template;
	        this.template2 = template;
	        firePropertyChange(TEMPLATE_PROPERTY, old, template);
	    }

	    
	    public  DPFPFeatureSet extraerCaracteristicas(DPFPSample sample, DPFPDataPurpose purpose){
	        DPFPFeatureExtraction extractor = DPFPGlobal.getFeatureExtractionFactory().createFeatureExtraction();
	        try {
	         return extractor.createFeatureSet(sample, purpose);
	        } catch (DPFPImageQualityException e) {
	         return null;
	        }
	   }
	    
	    public  Image CrearImagenHuella(DPFPSample sample) {
	    	return DPFPGlobal.getSampleConversionFactory().createImage(sample);
	    }

	      public void DibujarHuella(Image image) {
	    	  	lblMarcoHuella.setIcon(new ImageIcon(
	            image.getScaledInstance(lblMarcoHuella.getWidth(), lblMarcoHuella.getHeight(), Image.SCALE_DEFAULT)));
	            repaint();
	     }
	      
	      public void banderaHuellas(Image image,JLabel lbl) {
	    	  lbl.setIcon(new ImageIcon(
	          image.getScaledInstance(lbl.getWidth(), lbl.getHeight(), Image.SCALE_DEFAULT)));
	          repaint();
	      }

	    public  void EstadoHuellas(){
	    	EnviarTexto("Huella 1:Muestra Restantes para Guardar "+ Reclutador.getFeaturesNeeded());
	    	System.out.println(Reclutador.getTemplateStatus());
	    	System.out.println(Reclutador.getTemplate());
	    }
	    
	    public  void EstadoHuellas2(){
	    	EnviarTexto("Huella 2:Muestra Restantes para Guardar "+ Reclutador2.getFeaturesNeeded());
	    	System.out.println(Reclutador2.getTemplateStatus());
	    	System.out.println(Reclutador2.getTemplate());
	    }
	    
	    ActionListener opGuardar = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guardarHuella();
				Reclutador.clear();
				Reclutador2.clear();
				banderaHuellas(tmphuellaRoja.getImage(), lblImageHuella1);
				banderaHuellas(tmphuellaRoja.getImage(), lblImageHuella2);
				lblMarcoHuella.setIcon(null);
				System.exit(0);			
			}
		};
		
		 ActionListener opVerificar = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int folio = Integer.valueOf(JOptionPane.showInputDialog("Folio del personal a verificar") );
				  try {
					verificarHuella(folio);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				  Reclutador.clear();
				  Reclutador2.clear();
				}
			};
		
	    public void guardarHuella(){
	        datosHuella = new ByteArrayInputStream(template.serialize());
	        datosHuella2 = new ByteArrayInputStream(template2.serialize());
	        tamañoHuella=template.serialize().length;
	        tamañoHuella2=template2.serialize().length;
	    }
	    
	     public void verificarHuella(int folio) throws SQLException {
	    	 Object[][] datos = new BuscarSQL().Buscar_Huella(folio);
	    	 
	        //Lee la plantilla de la base de datos
	        byte[] templateBuffer1 = (byte[]) datos[0][1];
	        //Crea una nueva plantilla a partir de la guardada en la base de datos
	        DPFPTemplate referenceTemplate1 = DPFPGlobal.getTemplateFactory().createTemplate(templateBuffer1);
	        //Envia la plantilla creada al objeto contendor de Template del componente de huella digital
	        setTemplate(referenceTemplate1);
	        
	        //Lee la plantilla de la base de datos
	        byte[] templateBuffer2 = (byte[]) datos[0][2];
	        //Crea una nueva plantilla a partir de la guardada en la base de datos
	        DPFPTemplate referenceTemplate2 = DPFPGlobal.getTemplateFactory().createTemplate(templateBuffer2);
	        //Envia la plantilla creada al objeto contendor de Template del componente de huella digital
	        setTemplate2(referenceTemplate2);

	        // Compara las caracteriticas de la huella recientemente capturda con la
	        // plantilla guardada al usuario especifico en la base de datos
	        DPFPVerificationResult result = Verificador.verify(featuresverificacion, getTemplate());
	        DPFPVerificationResult result2 = Verificador.verify(featuresverificacion, getTemplate2());

	        //compara las plantilas (actual vs bd)
	        if (result.isVerified() || result2.isVerified()){
	        	JOptionPane.showMessageDialog(null, "Las huella capturada coinciden con la de: ["+folio+"]","Verificacion de Huella", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
	        }else{
	        	JOptionPane.showMessageDialog(null, "No corresponde la huella con: ["+folio+"]", "Verificacion de Huella", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
	        }
	     }
	    
//		public static void main(String [] arg){
//			try{
//				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//				new Cat_Huellas_Personalizado().setVisible(true);
//			}catch(Exception e){	}
//		}
	}
//--TODO(Captura De Huella Digital(fin))	--------------------------------------------------------------------------------------------------------------------------------------------------------------------

}