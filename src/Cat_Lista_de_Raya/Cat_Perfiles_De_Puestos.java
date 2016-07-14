package Cat_Lista_de_Raya;

import java.awt.AWTException;
import java.awt.Container;
import java.awt.Event;
import java.awt.Font;
import java.awt.Robot;
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

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
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
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Cat_Checador.Cat_Horarios;
import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Connexion;
import Conexiones_SQL.Generacion_Reportes;
import Obj_Checador.Obj_Horario_Empleado;
import Obj_Lista_de_Raya.Obj_Bono_Complemento_Sueldo;
import Obj_Lista_de_Raya.Obj_Bono_Puntualidad_Y_Asistencia;
import Obj_Lista_de_Raya.Obj_Departamento;
import Obj_Lista_de_Raya.Obj_Empleados;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Lista_de_Raya.Obj_Puestos;
import Obj_Lista_de_Raya.Obj_Rango_De_Prestamos;
import Obj_Lista_de_Raya.Obj_Sueldos;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_Filtro_Dinamico;
import Obj_Principal.Obj_Filtro_Dinamico_Plus;
import Obj_Renders.tablaRenderer;

@SuppressWarnings({ "serial", "unchecked" })
public class Cat_Perfiles_De_Puestos extends JFrame{
	
	Runtime R = Runtime.getRuntime();
	
	Container cont = getContentPane();
	
	JTabbedPane pestanas = new JTabbedPane();
	
	JLayeredPane panel = new JLayeredPane();
	JLayeredPane panelReporte = new JLayeredPane();
	
	JLabel lblHorarios = new JLabel();
	JLabel lblFolioHorario1 = new JLabel("");
	JLabel lblFolioHorario2 = new JLabel("");
	JLabel lblFolioHorario3 = new JLabel("");
	
	JTextField txtFolioPerfil = new Componentes().text( new JCTextField(), "Folio De Perfil", 9, "Int");
	JTextField txtPerfil = new Componentes().text( new JTextField(), "Nombre De Perfil", 70, "String");

	String edad[] = new BuscarSQL().getTablaRangoDeEdadesParaPerfiles();
	@SuppressWarnings("rawtypes")
	JComboBox cmbEdad = new JComboBox(edad);
	
	String sexo[] = {"SELECCIONE UN GENERO","MASCULINO","FEMENINO","INDISTINTO"};
	@SuppressWarnings("rawtypes")
	JComboBox cmbSexo = new JComboBox(sexo);
	
	JTextField txtPuestoReporta = new Componentes().text(new JTextField(), "Puesto Al Que Reporta", 100, "String");
	JCButton btnFiltroPuestoReporta = new JCButton("", "buscar.png", "Azul");
	
	JTextField txtDescanso               = new Componentes().text(new JCTextField(),"Dia De Descanso", 100, "String");
	JTextField txtDobla                  = new Componentes().text(new JCTextField(),"Dia Dobla", 100, "String");
	JTextField txtHorario                = new Componentes().text(new JCTextField(), "Horario Principal Asignado",300, "String");
	JTextField txtHorario2               = new Componentes().text(new JCTextField(), "Horario Secundario Asignado",300, "String");
	JTextField txtHorario3               = new Componentes().text(new JCTextField(), "Horario Terciario Asignado",300, "String");
	
	JTextField txtSalarioDiario          = new Componentes().text(new JCTextField(), "Salario Diario"          , 15, "Double");
	JTextField txtSalarioDiarioIntegrado = new Componentes().text(new JCTextField(), "Salario Diario Integrado", 15, "Double");
	
	String establecimiento[] = new Obj_Establecimiento().Combo_Establecimiento();
	@SuppressWarnings("rawtypes")
	JComboBox cmbEstablecimiento = new JComboBox(establecimiento);
	
	String Departamentos[] = new Obj_Departamento().Combo_Departamento();
	@SuppressWarnings("rawtypes")
	JComboBox cmbDepartamento = new JComboBox(Departamentos);  
	
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
	
	String nivel_de_puesto[] = new Obj_Puestos().Combo_Nivel_De_Puesto();
	@SuppressWarnings("rawtypes")
	JComboBox cmbNivelDePuesto = new JComboBox(nivel_de_puesto);

	JCheckBox chbGafete = new JCheckBox("Gafete");
	
	JCButton btnBuscar   = new JCButton("","buscar.png","Azul");
	JCButton btnFiltro   = new JCButton("","Filter-List-icon16.png","Azul");
	JCButton btnNuevo    = new JCButton("","Nuevo.png","Azul");
	JCButton btnEditar   = new JCButton("","editara.png","Azul");
	JCButton btnDeshacer = new JCButton("","deshacer16.png","Azul");
	
	JCButton btnSalir    = new JCButton("Salir","salir16.png","Azul");
	JCButton btnGuardar  = new JCButton("Guardar","Guardar.png","Azul");
	
	JButton btnHorario   = new JButton(".");
	JButton btnHorario2 = new JButton(".");
	JButton btnHorario3 = new JButton(".");
	JButton btnHorarioNew = new JButton("new");
	
	JTextArea txaObservaciones = new Componentes().textArea(new JTextArea(), "Observaciones", 980);
	JScrollPane Observasiones = new JScrollPane(txaObservaciones);
	
	 private ButtonGroup bgHorarios = new ButtonGroup();
	 private JRadioButton rbHorario = new JRadioButton("",true);
	 private JRadioButton rbHorario2 = new JRadioButton("",false);
	 private JRadioButton rbHorario3 = new JRadioButton("",false);
	 
	 String[] horarioRotativo = { "Sin Horario rotativo ", "2 Horarios", "3 Horarios" };
	 @SuppressWarnings("rawtypes")
	private JComboBox cmbHorarioRotativo = new JComboBox(horarioRotativo);
	 
	//declaracion de Bordes
	Border blackline, etched, raisedbevel, loweredbevel, empty;
//	TitledBorder title4;
	
	int seleccion_de_asignacion_de_Horario1Horario2Horario3;
	
	public Cat_Perfiles_De_Puestos() {
		getContenedor();
	}
	
	public void getContenedor(){
		this.setSize(1024,748);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		pestanas.addTab("Empleados", panel);
		pestanas.addTab("Reportes", panelReporte);
		
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
		this.lblHorarios.setBorder(BorderFactory.createTitledBorder(blackline,"Configuracion De Horario"));
		
		this.btnHorarioNew.setToolTipText("Generar Horario");
		this.btnHorario.setToolTipText("Asignar Horario");
		this.btnHorario2.setToolTipText("Asignar Segundo Horario");
		this.btnHorario3.setToolTipText("Asignar Tercer Horario");
		
		this.txaObservaciones.setBorder(BorderFactory.createTitledBorder(blackline));
		
		this.bgHorarios.add(rbHorario);
		this.bgHorarios.add(rbHorario2);
		this.bgHorarios.add(rbHorario3);
		
		btnBuscar.setToolTipText("Buscar");
		btnFiltro.setToolTipText("Filtro De Perfiles");
		btnEditar.setToolTipText("Editar PErfil");
		btnDeshacer.setToolTipText("Deshacer");
		btnNuevo.setToolTipText("Nuevo Perfil");
		int x = 20, y=30, ancho=140,width=190,height=30,sep=202;

//		x=20; y=y+=58;height=20;width=175;
		
//Datos personales ----------------------------------------------------------------------------------------------------------------------------		
		panel.add(new JLabel("Folio:")).setBounds(x,y,ancho,20);
		panel.add(txtFolioPerfil).setBounds(x+ancho-40,y,ancho-15,20);
		
		panel.add(btnBuscar).setBounds  	(x+ancho+ancho-52,y,30,20);
		panel.add(btnFiltro).setBounds  	(x+ancho+ancho-20,y,30,20);
		panel.add(btnEditar).setBounds   	(x+ancho+ancho+12,y,30,20);
		panel.add(btnDeshacer).setBounds  	(x+ancho+ancho+44,y,30,20);
		panel.add(btnNuevo).setBounds		(x+ancho+ancho+76,y,30,20);
		
	y=30;
		panel.add(new JLabel("Nombre:")).setBounds(x,y+=25,ancho,20);
		panel.add(txtPerfil).setBounds(x+ancho-40,y,ancho*2+55,20);
		
		panel.add(new JLabel("Edad: ")).setBounds(x,y+=25,ancho,20);
		panel.add(cmbEdad).setBounds(x+ancho-40,y,ancho-40,20);
		
		panel.add(new JLabel("Sexo: ")).setBounds(x+=ancho+100,y,ancho,20);
		panel.add(cmbSexo).setBounds(x+ancho-70,y,ancho-15,20);
		
	x=20;
		panel.add(new JLabel("Reporta A:")).setBounds(x,y+=25,ancho,20);
		panel.add(txtPuestoReporta).setBounds(x+ancho-40,y,ancho*2+25,20);
		panel.add(btnFiltroPuestoReporta).setBounds(x+ancho*3-15,y,30,20);
		
		
//TODO Laboral ------------------------------------------------------------------------------------------------------------------------------------------		
		x=17 ;y=150;width=340;height=20;sep=120;
		panel.add(lblHorarios).setBounds(10,y-15,997,100);
		panel.add(new JLabel("Horario:")).setBounds          (x           ,y+=5 ,width   ,height );
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
		
		x=520 ;y=155;width=150;sep=120;
		panel.add(new JLabel("Tipo de horario:")).setBounds  (x           ,y     ,width   ,height );
		panel.add(cmbHorarioRotativo).setBounds              (x+sep       ,y     ,width   ,height );
		panel.add(new JLabel("Descanso:")).setBounds         (x           ,y+=25 ,width   ,height );
		panel.add(txtDescanso).setBounds                     (x+sep       ,y     ,width   ,height );
		panel.add(new JLabel("Día Dobla:")).setBounds        (x           ,y+=25 ,width   ,height );
		panel.add(txtDobla).setBounds                        (x+sep       ,y     ,width   ,height );
		
//TODO Percepciones y Deducciones ------------------------------------------------------------------------------------------------------------------------------------------		
		x=17 ;y=250;sep=80;
		panel.add(new JLabel("Salario Diario:")).setBounds     (x    ,y     ,width,height );
		panel.add(txtSalarioDiario).setBounds                  (x+sep,y     ,width,height );
		panel.add(new JLabel("Salario D.I:")).setBounds        (x    ,y+=25 ,width,height );
		panel.add(txtSalarioDiarioIntegrado).setBounds         (x+sep,y     ,width,height );
		
		x=17 ;y=250;
		panel.add(new JLabel("Sueldo:")).setBounds             (x+=(sep*3),y,width,height );
		panel.add(cmbSueldo).setBounds                         (x+sep,y     ,width,height );
		panel.add(new JLabel("B.Complemento:")).setBounds      (x    ,y+=25 ,width,height );
		panel.add(cmbBono).setBounds                           (x+sep,y     ,width,height );
		
		y=250;
		panel.add(new JLabel("B.Asistencia:")).setBounds       (x+=(sep*3),y,width,height );
		panel.add(cmbBonoAsistencia).setBounds                 (x+sep,y     ,width,height );
		panel.add(new JLabel("B.Puntualidad:")).setBounds      (x    ,y+=25 ,width,height );
		panel.add(cmbBonopuntualidad).setBounds                (x+sep,y     ,width,height );
		
		y=250;
		panel.add(new JLabel("Rango de Prestamo:")).setBounds  (x+=(sep*3)  ,y		,width,height );
		panel.add(cmbPrestamos).setBounds                      (x+sep+35    ,y		,width,height );
		panel.add(chbGafete).setBounds                         (x+sep+35	,y+=25  ,width,height );
		
		x=17; sep=80; width=465; height=95;
		
		panel.add(new JLabel("Rango de Prestamo:")).setBounds  (x, y+=35 ,width ,20 	);
		panel.add(Observasiones).setBounds                     (x, y+=15 ,width ,height );
		
		
		x=520 ;y=30;width=340;height=20;sep=120;
		panel.add(new JLabel("Establecimiento:")).setBounds    (x           ,y	   ,width   ,height );
		panel.add(cmbEstablecimiento).setBounds                (x+sep       ,y     ,width   ,height );
		panel.add(new JLabel("Departamento:")).setBounds       (x           ,y+=25 ,width   ,height );
		panel.add(cmbDepartamento).setBounds                   (x+sep       ,y     ,width   ,height );
		panel.add(new JLabel("Puesto:")).setBounds  		   (x           ,y+=25 ,width   ,height );
		panel.add(cmbPuesto).setBounds              		   (x+sep       ,y     ,width   ,height );
		panel.add(new JLabel("Nivel De Puesto:")).setBounds    (x           ,y+=25 ,width   ,height );
		panel.add(cmbNivelDePuesto).setBounds          		   (x+sep       ,y     ,width   ,height );
		
		
		
		x=17 ;y=668;width=110;sep=218;
		panel.add(btnGuardar).setBounds                        (x+=sep,y    ,width,height );		
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
		
		btnHorarioNew.addActionListener(opGenerarHorairo);
		btnHorario.addActionListener(opFiltroHorairo);
		btnHorario2.addActionListener(opFiltroHorairo2);
		btnHorario3.addActionListener(opFiltroHorairo3);
		
		rbHorario.addActionListener(opRButton);
		rbHorario2.addActionListener(opRButton);
		rbHorario3.addActionListener(opRButton);
		
		txtFolioPerfil.requestFocus();
		txtFolioPerfil.addKeyListener(buscar_action);
		
		cmbHorarioRotativo.addActionListener(opCmbHorarioRotarivo);
		
		cont.add(pestanas);
		
		btnHorario.setEnabled(false);
		btnHorario2.setEnabled(false);
		btnHorario3.setEnabled(false);
		
		txtDescanso.setEnabled(false);
		txtDobla.setEnabled(false);
		txtHorario.setEnabled(false);
		txtHorario2.setEnabled(false);
		txtHorario3.setEnabled(false);
		
		panelEnabled(false);
		txtFolioPerfil.setEditable(true);
		txtFolioPerfil.setEnabled(true);
		
//       asigna el foco al JTextField deseado al arrancar la ventana
         this.addWindowListener(new WindowAdapter() {
                 public void windowOpened( WindowEvent e ){
                	 txtFolioPerfil.requestFocus();
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
						                                    txtFolioPerfil.requestFocus(); 
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
	  	}
	
	 int valor = 0;
	
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
	
	ActionListener buscar = new ActionListener() {
		public void actionPerformed(ActionEvent e){
			txtHorario.setFont(new Font("ARIAL", Font.ITALIC, 9));
			txtHorario2.setFont(new Font("ARIAL", Font.ITALIC, 9));
			txtHorario3.setFont(new Font("ARIAL", Font.ITALIC, 9));
			buscarEmpleado("");
		}
	};
			
	public void buscarEmpleado(String funcion){
			
			if(txtFolioPerfil.getText().equals("")){
				JOptionPane.showMessageDialog(null,"Necesita Seleccionar Primero Un Empleado", "Mensaje!",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}else{
				
				Obj_Empleados re = new Obj_Empleados().buscar(Integer.parseInt(txtFolioPerfil.getText()));
				if(re.getFolio() != 0){			
					txtFolioPerfil.setText(re.getFolio()+"");
					txtPerfil.setText(re.getNombre()+"");
					
					if(re.getSexo()==0){
						cmbSexo.setSelectedItem("MASCULINO");
					}else{
						cmbSexo.setSelectedItem("FEMENINO");
					}
					
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
					
					
					Obj_Departamento depart = new Obj_Departamento().buscar(re.getDepartameto());
					cmbDepartamento.setSelectedItem(depart.getDepartamento());
					
					Obj_Establecimiento comboNombreEsta = new Obj_Establecimiento().buscar_estab(re.getEstablecimiento());
					cmbEstablecimiento.setSelectedItem(comboNombreEsta.getEstablecimiento());
					
					Obj_Puestos comboNombrePues = new Obj_Puestos().buscar_pues(re.getPuesto());
					cmbPuesto.setSelectedItem(comboNombrePues.getPuesto());
					
					txtSalarioDiario.setText(re.getSalario_diario()+"");
					txtSalarioDiarioIntegrado.setText(re.getSalario_diario_integrado()+"");
					cmbSueldo.setSelectedItem(re.getSueldo()+"");
					cmbBonopuntualidad.setSelectedItem(re.getBono_asistencia()+"");
					cmbBonoAsistencia.setSelectedItem(re.getBono_puntualidad()+"");
					
					Obj_Bono_Complemento_Sueldo bono = new Obj_Bono_Complemento_Sueldo().buscar(re.getBono());
					cmbBono.setSelectedItem(bono.getBono()+"");

					cmbPrestamos.setSelectedIndex(re.getPrestamo());
					
					if(re.isGafete() == true){chbGafete.setSelected(true);}
					else{chbGafete.setSelected(false);}
					
					txaObservaciones.setText(re.getObservasiones());
					
				    btnNuevo.setEnabled(false);
				    panelEnabled(false);
					txtFolioPerfil.setEditable(true);
					txtFolioPerfil.requestFocus();
					btnEditar.setEnabled(true);
					txtFolioPerfil.setEditable(false);
					btnBuscar.setEnabled(false);
					btnFiltro.setEnabled(true);
					btnHorario.setEnabled(false);
					cmbHorarioRotativo.setEnabled(false);
				}else{
					
					
					if(funcion.equals("rueda+")){
						valor++;
						txtFolioPerfil.setText(valor+"");
						buscarEmpleado("rueda+");
						
					}
					
					if(funcion.equals("rueda-")){
						valor--;
						txtFolioPerfil.setText(valor+"");
						buscarEmpleado("rueda-");
					}
					
					if(funcion.equals("")){
						JOptionPane.showMessageDialog(null, "El Registro no existe","Error",JOptionPane.WARNING_MESSAGE);
						panelEnabled(true);
						txtFolioPerfil.setEditable(true);
						txtFolioPerfil.requestFocus();
						panelLimpiar();
					}
					
					
					
					return;
				}
		}
	}
	
   public double tiene_deuda_fuente_sodas(){
  	  String query ="select isnull(sum(importe),0) from tb_captura_fuente_sodas where folio_empleado="+txtFolioPerfil.getText().toString()+" and ticket in(select ticket from tb_fuente_sodas_rh where status=1)";
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
			
			if(new BuscarSQL().existe_horario_con_otro_empleado(horario,horario2,horario3, Integer.valueOf(txtFolioPerfil.getText().toString()) )){
				System.out.println(horario+" 2"+horario2+" 3"+horario3);
				JOptionPane.showMessageDialog(null, "No Se Puede Asignar Un Mismo Horario A Mas De Un Colaborador Con Estatus\nVigente, Incapacitado, De Vacaciones o Provicional Checador", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/error-de-reloj-icono-5961-32.png"));
				return;
			}else{
				    guardar_modificar_Empleado();
			}	
			
		}	
	};
	
public void guardar_modificar_Empleado(){

				if(txtFolioPerfil.getText().equals("")){
					JOptionPane.showMessageDialog(null, "El Folio Es Requerido \n","Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				}
				
					Obj_Empleados empleado = new Obj_Empleados().buscar(Integer.parseInt(txtFolioPerfil.getText()));
					
					if(!(cmbBonoAsistencia.getSelectedItem().toString().trim().equals("Selecciona un Bono"))){
						if(!(cmbBonopuntualidad.getSelectedItem().toString().trim().equals("Selecciona un Bono"))){
							if(!(cmbBono.getSelectedItem().toString().trim().equals("Selecciona un Bono"))){							
								if( !(empleado.getBonocomplemento()==Double.valueOf(cmbBono.getSelectedItem().toString().trim())) || !(empleado.getBono_asistencia()==Double.valueOf(cmbBonoAsistencia.getSelectedItem().toString().trim()))
								    || !(empleado.getBono_puntualidad()==Double.valueOf(cmbBonopuntualidad.getSelectedItem().toString().trim())) || !(empleado.getSueldo()==Double.valueOf(cmbSueldo.getSelectedItem().toString().trim()))){
									if(new BuscarSQL().validar_cambio_de_sueldo_o_bono(txtFolioPerfil.getText().toString().trim())){
										JOptionPane.showMessageDialog(null, "El Usuario Ya Cuenta Con Un Cambio De Sueldo o Bono Pendiente de Autorizar \nEs Necesario Que Lo Niegen o Acepten Antes De Solicitar Otro Cambio  :\n"+validaCampos(), "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
										return;						
									}
								}
							}
					    }
					}
					
					
					if(empleado.getFolio() == Integer.parseInt(txtFolioPerfil.getText())){
						if(JOptionPane.showConfirmDialog(null, "El registro existe, ¿desea actualizarlo?") == 0){
							if(validaCampos()!="") {
								JOptionPane.showMessageDialog(null, "Los Siguientes Campos Son Requeridos Para Poder Guardar El Registro:\n"+validaCampos(), "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
								return;
							}else{
								
								
		//				datos personales	
								empleado.setNombre(txtPerfil.getText());
								
								if(cmbSexo.getSelectedItem().equals("MASCULINO")){
									empleado.setSexo(0);
								}else{
									empleado.setSexo(1);
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
		
								Obj_Establecimiento comboFolioEsta = new Obj_Establecimiento().buscar_estab(cmbEstablecimiento.getSelectedItem()+"");
								empleado.setEstablecimiento(comboFolioEsta.getFolio());
								
								Obj_Departamento depart = new Obj_Departamento().buscar_departamento(cmbDepartamento.getSelectedItem()+"");
								empleado.setDepartameto(depart.getFolio());
								
								Obj_Puestos comboFolioPues = new Obj_Puestos().buscar_pues(cmbPuesto.getSelectedItem()+"");
								empleado.setPuesto(comboFolioPues.getFolio());
								
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
								
								empleado.setSueldo(Float.valueOf(cmbSueldo.getSelectedItem().toString()));
								
								Obj_Bono_Complemento_Sueldo bono = new Obj_Bono_Complemento_Sueldo().buscarValor(Float.parseFloat(cmbBono.getSelectedItem()+""));
								empleado.setBono(bono.getFolio());
								
								empleado.setPrestamo(cmbPrestamos.getSelectedIndex());
								
								
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
								
								empleado.setGafete(chbGafete.isSelected());
								empleado.setObservasiones(txaObservaciones.getText()+"");
								
								if(empleado.actualizar(Integer.parseInt(txtFolioPerfil.getText()))){
									panelLimpiar();
									panelEnabled(false);
									rbHorario2.setEnabled(false);
									rbHorario3.setEnabled(false);
									txtFolioPerfil.setEnabled(true);
									txtFolioPerfil.setEditable(true);
									txtFolioPerfil.requestFocus();
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
							empleado.setNo_checador(txtFolioPerfil.getText());
							empleado.setNombre(txtPerfil.getText());
							
							if(cmbSexo.getSelectedItem().equals("MASCULINO")){
								empleado.setSexo(0);
							}else{
								empleado.setSexo(1);
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
							
							Obj_Establecimiento comboFolioEsta = new Obj_Establecimiento().buscar_estab(cmbEstablecimiento.getSelectedItem()+"");
							empleado.setEstablecimiento(comboFolioEsta.getFolio());
							
							Obj_Departamento depart = new Obj_Departamento().buscar_departamento(cmbDepartamento.getSelectedItem()+"");
							empleado.setDepartameto(depart.getFolio());
							
							Obj_Puestos comboFolioPues = new Obj_Puestos().buscar_pues(cmbPuesto.getSelectedItem()+"");
							empleado.setPuesto(comboFolioPues.getFolio());
							
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
							
							empleado.setSueldo(Float.valueOf(cmbSueldo.getSelectedItem().toString()));
							
							Obj_Bono_Complemento_Sueldo bono = new Obj_Bono_Complemento_Sueldo().buscarValor(Float.parseFloat(cmbBono.getSelectedItem()+""));
							empleado.setBono(bono.getFolio());
							
							empleado.setPrestamo(cmbPrestamos.getSelectedIndex());
							
//							empleado.setBono_asistencia(Float.valueOf(cmbBonoAsistencia.getSelectedItem().toString())); 
//							empleado.setBono_puntualidad(Float.valueOf(cmbBonopuntualidad.getSelectedItem().toString()));
							
							empleado.setGafete(chbGafete.isSelected());
							empleado.setObservasiones(txaObservaciones.getText()+"");
							
							if(empleado.guardar()){
								panelLimpiar();
								panelEnabled(false);
								rbHorario2.setEnabled(false);
								rbHorario3.setEnabled(false);
								txtFolioPerfil.setEnabled(true);
								txtFolioPerfil.setEditable(true);
								txtFolioPerfil.requestFocus();
								txtHorario.setEnabled(false);
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
	
	ActionListener filtro = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			btnBuscar.setEnabled(true);
			new Cat_Filtro_Puestos().setVisible(true);
		}
	};
	
	ActionListener editar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			Obj_Empleados empleado = new Obj_Empleados().buscar(Integer.parseInt(txtFolioPerfil.getText()));
			if(empleado.getFolio() != 0){
				
				switch(empleado.getStatus_rotativo()){
				
					case 0: panelEnabled(true);
							cmbHorarioRotativo.setSelectedIndex(0);
							cmbHorarioRotativo.setEnabled(true);
							rbHorario.setSelected(true);
							break;
							
					case 1: panelEnabled(true);
							cmbHorarioRotativo.setSelectedIndex(1);
							cmbHorarioRotativo.setEnabled(true);
							rbHorario2.setEnabled(true);
							break;
							
					case 2: panelEnabled(true);
							cmbHorarioRotativo.setSelectedIndex(2);
							cmbHorarioRotativo.setEnabled(true);
							rbHorario3.setEnabled(true);
							break;
				}
				
				txtFolioPerfil.setEditable(false);
				btnEditar.setEnabled(false);
				btnNuevo.setEnabled(true);
			}else{
				JOptionPane.showMessageDialog(null,"El Registró Que Desea Editar no Existe","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}
		}		
	};
	
	public void panelEnabled(Boolean value){	
		txtPerfil.setEnabled(value);
		cmbEstablecimiento.setEnabled(value);
		cmbPuesto.setEnabled(value);
		cmbSueldo.setEnabled(value);
		cmbBono.setEnabled(value);
		cmbPrestamos.setEnabled(value);
		chbGafete.setEnabled(value);
		txaObservaciones.setEnabled(value);
		
		cmbEdad.setEnabled(value);
		cmbSexo.setEnabled(value);
		
		rbHorario.setEnabled(value);
		rbHorario2.setEnabled(value);
		cmbDepartamento.setEnabled(value);
		
		txtSalarioDiario.setEnabled(value);
		txtSalarioDiarioIntegrado.setEnabled(value);
		cmbBonopuntualidad.setEnabled(value);
		cmbBonoAsistencia.setEnabled(value);
	}
	
	///boton deshacer
	public void panelLimpiar(){	
		txtFolioPerfil.setText("");
		txtPerfil.setText("");
		cmbEstablecimiento.setSelectedIndex(0);
		cmbPuesto.setSelectedIndex(0);
		txtHorario.setText("");
		txtHorario2.setText("");
		txtHorario3.setText("");
		cmbSueldo.setSelectedIndex(0);
		cmbBono.setSelectedIndex(0);
		cmbPrestamos.setSelectedIndex(0);
		chbGafete.setSelected(false);
		txaObservaciones.setText("");
	    
		txtDescanso.setText("");
		txtDobla.setText("");
		
		cmbDepartamento.setSelectedIndex(0);
		
		txtSalarioDiario.setText("");
		txtSalarioDiarioIntegrado.setText("");
		cmbBonopuntualidad.setSelectedIndex(0);
		cmbBonoAsistencia.setSelectedIndex(0);

		lblFolioHorario1.setText("");
		lblFolioHorario2.setText("");
		lblFolioHorario3.setText("");
		
		cmbHorarioRotativo.setSelectedIndex(0);
		
		cmbSexo.setSelectedIndex(0);
	    
	}
	
	ActionListener nuevo = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			try {
				Obj_Empleados empleado = new Obj_Empleados().buscar_nuevo();
				if(empleado.getFolio() != 0){
					panelLimpiar();
					panelEnabled(false);
					txtPerfil.setEnabled(true);
					txtFolioPerfil.setText(empleado.getFolio()+1+"");
					txtPerfil.requestFocus();
					
						btnFiltro.setEnabled(false); 
						btnBuscar.setEnabled(false); 
						btnNuevo.setEnabled(false);
				}else{
					panelEnabled(true);
					txtFolioPerfil.setText(1+"");
					txtFolioPerfil.setEnabled(false);
					txtPerfil.requestFocus();
					
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
			panelEnabled(false);
			txtFolioPerfil.setEditable(true);
			txtFolioPerfil.setEnabled(true);
			txtFolioPerfil.requestFocus();
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
			if(txtFolioPerfil.getText().equals("")){
				JOptionPane.showMessageDialog(null,"Necesita Seleccionar Primero Un Colaborador", "Mensaje!",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}else{
			new Cat_Horarios(Integer.valueOf(lblFolioHorario1.getText().toString())).setVisible(true);
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
	
	private String validaCampos(){
		String error="";

		if(txtFolioPerfil.getText().equals("")) 		error+= "Folio\n";
		if(txtPerfil.getText().equals("")) 		error+= "Perfil\n";
		if(cmbSexo.getSelectedIndex()==0)	error+= "Sexo\n";
		if(cmbEstablecimiento.getSelectedItem().equals("Selecciona un Establecimiento")) error += "Establecimiento\n";
		if(cmbDepartamento.getSelectedItem().equals("Selecciona un Departamento"))	error+= "Departamento\n";
		if(cmbPuesto.getSelectedItem().equals("Selecciona un Puesto")) error += "Puesto\n";
		
		switch(cmbHorarioRotativo.getSelectedIndex()){
		case 0:	if(txtHorario.getText().equals("")) 		error+= "Horario\n"; break;
		case 1: if(txtHorario.getText().equals("")) 		error+= "Horario 2\n";
				if(txtHorario2.getText().equals("")) 		error+= "Horario 3\n";break;
		case 2: if(txtHorario.getText().equals("")) 		error+= "Horario\n";
				if(txtHorario2.getText().equals("")) 		error+= "Horario 2\n";
				if(txtHorario3.getText().equals("")) 		error+= "Horario 3\n";break;
		}
		if(cmbSueldo.getSelectedItem().equals("Selecciona un Sueldo")) error += "Sueldo\n";
		if(cmbBono.getSelectedItem().equals("Selecciona un Bono")) error += "Bono\n";
		if(cmbPrestamos.getSelectedItem().equals("Selecciona un Rango de Prestamo")) error += "Rango de Prestamo\n";
		return error;
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

	public class Cat_Filtro_Puestos extends JDialog {
		
		Container cont = getContentPane();
		JLayeredPane campo = new JLayeredPane();
		
		DefaultTableModel model = new DefaultTableModel(0,3){
			public boolean isCellEditable(int fila, int columna){
				if(columna ==2)
					return true;
				return false;
			}
			
			@SuppressWarnings("rawtypes")
			Class[] types = new Class[]{
                    java.lang.Object.class,
                    java.lang.Object.class, 
                    java.lang.Boolean.class    
     };
         @SuppressWarnings({ "rawtypes" })
         public Class getColumnClass(int columnIndex) {
                 return types[columnIndex];
         }
		};
		
		JTable tabla = new JTable(model);
		
		@SuppressWarnings("rawtypes")
		private TableRowSorter trsfiltro;
		
		JTextField txtFiltroPuestoReporta = new JTextField();
		JCButton btnAgregar = new JCButton("","buscar.png","Azul");
		
		@SuppressWarnings("rawtypes")
		public Cat_Filtro_Puestos(){
			
			this.setModal(true);
			
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/filter_icon&16.png"));
			this.setTitle("Filtro de Puestos A Quien Reporta");
			campo.setBorder(BorderFactory.createTitledBorder("Puestos A Quien Reporta"));
			trsfiltro = new TableRowSorter(model); 
			tabla.setRowSorter(trsfiltro);  
			
			campo.add(txtFiltroPuestoReporta).setBounds(15,20,289,20);
			campo.add(btnAgregar).setBounds(305,20,40,20);
			campo.add(getPanelTabla()).setBounds(15,42,330,565);
			
			agregar(tabla);
			
			cont.add(campo);
			
			txtFiltroPuestoReporta.addKeyListener(opFiltro);
			
			this.setSize(365,650);
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
		    			txtFolioPerfil.setText(folio+"");
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
	     					
	     				txtFolioPerfil.setText(folio);
	     				btnBuscar.doClick();
	     				dispose();
	                 }
	            });
			}
			public void keyPressed(KeyEvent e){}
			public void keyReleased(KeyEvent e){}
		};
		
		KeyListener opFiltro = new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {
				int[] columnas = {0,1};
				new Obj_Filtro_Dinamico_Plus(tabla,txtFiltroPuestoReporta.getText().toUpperCase(),columnas);
			}
			public void keyPressed(KeyEvent e) {}
		};
		
	   	private JScrollPane getPanelTabla()	{		
	   		this.tabla.getTableHeader().setReorderingAllowed(false) ;
			DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
			tcr.setHorizontalAlignment(SwingConstants.CENTER);

			tabla.getColumnModel().getColumn(0).setHeaderValue("Folio");
			tabla.getColumnModel().getColumn(0).setMaxWidth(50);
			tabla.getColumnModel().getColumn(0).setMinWidth(50);
			tabla.getColumnModel().getColumn(1).setHeaderValue("Puesto");
			tabla.getColumnModel().getColumn(1).setMaxWidth(230);
			tabla.getColumnModel().getColumn(1).setMinWidth(230);
			tabla.getColumnModel().getColumn(2).setHeaderValue("*");
			tabla.getColumnModel().getColumn(2).setMaxWidth(30);
			tabla.getColumnModel().getColumn(2).setMinWidth(30);
			
			tabla.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12)); 
			tabla.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
			tabla.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("CHB","centro","Arial","normal",12)); 
			
			Statement s;
			ResultSet rs;
			try {
				s = new Connexion().conexion().createStatement();
				rs = s.executeQuery("select folio,nombre as puesto,'false' as selector from tb_puesto where status = 1");
				
				while (rs.next())
				{ 
				   Object[] fila = new Object[3];
				   fila[0] = rs.getString(1)+"  ";
				   fila[1] = "   "+rs.getString(2);
				   fila[2] = rs.getBoolean(3);
				
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
	
	public static void main(String args[]){
		
//		String basedatos="2.26";
//		String vista_previa_reporte="no";
//		int vista_previa_de_ventana=0;
//		String comando="select top 10 folio,puesto_id from tb_empleado";
//		String reporte = "pruebaGrafica.jrxml";
				
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Perfiles_De_Puestos().setVisible(true);
//			 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
		}catch(Exception e){	}
		

	}
}