package Cat_Cuadrantes;

import javax.swing.JFrame;
import javax.swing.UIManager;

import Obj_Administracion_del_Sistema.Obj_Usuario;

import java.awt.Color;
import java.awt.Container;
import java.awt.Event;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import Conexiones_SQL.BuscarSQL;
import Obj_Planeacion.Obj_Actividades_De_Una_Planeacion;
import Obj_Planeacion.Obj_Frecuencia_De_Actividades;
import Obj_Planeacion.Obj_Opciones_De_Respuesta;
import Obj_Planeacion.Obj_Prioridad_Y_Ponderacion;
import Obj_Planeacion.Obj_Seleccion_De_Usuarios;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCSpinner;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_Filtro_Dinamico_Plus;
import Obj_Principal.Obj_tabla;

@SuppressWarnings("serial")
public class Cat_Asignacion_De_Actividades_A_Cuadrantes_Por_Nivel_Jerarquico extends JFrame{
	Runtime R = Runtime.getRuntime();
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	JTextArea  txa_detalle_de_la_actividad = new Componentes().textArea(new JTextArea(), "Detalle De La Actividad", 250);
	JTextField txtFolio  = new Componentes().text(new JCTextField() ,"Folio"    ,16    ,"String" );
	
	JScrollPane JPActividad = new JScrollPane(txa_detalle_de_la_actividad);
	
	JToolBar menu_toolbar          = new JToolBar();
	JCButton btnFiltro             = new JCButton("Filtro"                                ,"Filter-List-icon16.png","Azul");
	JCButton btnNuevo              = new JCButton("Nuevo"                                 ,"Nuevo.png","Azul");
	JCButton btnEditar             = new JCButton("Editar"                                ,"editara.png","Azul");
	JCButton btnAprovar            = new JCButton("Guardar"                               ,"Guardar.png","Azul");
	JCButton btnDeshacer           = new JCButton("Deshacer"                              ,"deshacer16.png","Azul");
	JCButton btnOpcionesRespuesta  = new JCButton("Opciones De Respuesta"    ,"ver-info-boton-icono-8754-32.png"              ,"Azul"); 
	JCButton btnPrioridad          = new JCButton("Prioridad y Ponderación"  ,"favoritos-ver-boton-icono-8318-32.png"         ,"Azul"); 
	JCButton btnFrecuencia         = new JCButton("Programación,Frecuencia"  ,"tiempo-de-botones-icono-4873-32.png"           ,"Azul"); 
	JCButton btnUsuarios           = new JCButton("Asignación De Usuarios"   ,"ayudar-a-ver-el-boton-icono-4900-32.png"       ,"Azul"); 
	
	JLabel lblGrupoOrdenActividad= new JLabel("");

	JSpinner  jspHoraInicio  = new JCSpinner().JCSpinnerHoras(0  ,0 ); 
	JSpinner  jspHorafinal   = new JCSpinner().JCSpinnerHoras(23 ,59); 
	
	JDateChooser fecha_de_la_actividad = new Componentes().jchooser(new JDateChooser()  ,"",0);  
	  
	JCheckBox chbLunes     = new JCheckBox("Lunes");
	JCheckBox chbMartes    = new JCheckBox("Martes");
	JCheckBox chbMiercoles = new JCheckBox("Miercoles");
	JCheckBox chbJueves    = new JCheckBox("Jueves");
	JCheckBox chbViernes   = new JCheckBox("Viernes");
	JCheckBox chbSabado    = new JCheckBox("Sabado");
	JCheckBox chbDomingo   = new JCheckBox("Domingo"); 
	ButtonGroup Grupodias  = new ButtonGroup();
		  
	Obj_Usuario usuario = new Obj_Usuario().LeerSession();
	Obj_Opciones_De_Respuesta OpRespuesta= new Obj_Opciones_De_Respuesta();
	Obj_Prioridad_Y_Ponderacion OpPonderacion= new Obj_Prioridad_Y_Ponderacion();
	Obj_Seleccion_De_Usuarios usuarios= new Obj_Seleccion_De_Usuarios();
	Obj_Frecuencia_De_Actividades frecuencia = new Obj_Frecuencia_De_Actividades();
	Obj_Actividades_De_Una_Planeacion Actividad_plan = new Obj_Actividades_De_Una_Planeacion();
	Obj_tabla ObjTab =new Obj_tabla();
	
	Border linea;
	String GuardarActualizar ="";
	public Cat_Asignacion_De_Actividades_A_Cuadrantes_Por_Nivel_Jerarquico(){
		this.setSize(710, 350);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/reinicio-pelota-cute-icono-7443-64.png"));
		this.setTitle("Asignacion De Actividades Por Nivel Jerarquico");
		this.panel.setBorder(BorderFactory.createTitledBorder("Selecciona Los Datos Deseados De Respuesta"));
		this.linea = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
		this.lblGrupoOrdenActividad.setBorder(BorderFactory.createTitledBorder(linea,"Orden De La Actividad Por Hora"));
		
		this.menu_toolbar.add(btnNuevo    );
//	    this.menu_toolbar.addSeparator(   );
//	    this.menu_toolbar.addSeparator(   );
//		this.menu_toolbar.add(btnFiltro   );
//	    this.menu_toolbar.addSeparator(   );
//	    this.menu_toolbar.addSeparator(   );
//		this.menu_toolbar.add(btnEditar   );
		this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.add(btnDeshacer );
	    this.menu_toolbar.addSeparator(   );
	    this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.add(btnAprovar  );
		this.menu_toolbar.setFloatable(false);
		
		int x=15,y=20,width=220,height=20;
		panel.add(menu_toolbar).setBounds                                      (x    ,y     ,600      ,height     );
		panel.add(new JLabel("Folio:")).setBounds                              (x    ,y+=25 ,width    ,height     );
		panel.add(txtFolio).setBounds                                          (x+30 ,y     ,120      ,height     );
		panel.add(new JLabel("Detalle De La Actividad:")).setBounds            (x    ,y+=20 ,width    ,height     );
		panel.add(JPActividad).setBounds                                       (x    ,y+=20 ,355      ,height*6+10);
		panel.add(lblGrupoOrdenActividad).setBounds                            (x    ,y+=145,355      ,height+35  );
		panel.add(new JLabel("Inicia:")).setBounds                             (x+10 ,y+=20 ,width    ,height     );
		panel.add(jspHoraInicio).setBounds                                     (x+50 ,y     ,90       ,height     );
		panel.add(new JLabel("Termina:")).setBounds                            (x+210,y     ,width    ,height     );
		panel.add(jspHorafinal).setBounds                                      (x+260,y     ,90       ,height     );
		panel.add(btnUsuarios).setBounds                                       (x=400,y=85  ,width    ,height=40  );
		panel.add(btnFrecuencia).setBounds                                     (x    ,y+=51 ,width    ,height     );
		panel.add(btnOpcionesRespuesta).setBounds                              (x    ,y+=51 ,width    ,height     );
	    panel.add(btnPrioridad).setBounds                                      (x    ,y+=51 ,width    ,height     );
		
		btnAprovar.addActionListener(opAprovar);
 	    btnNuevo.addActionListener(nuevo);
		btnDeshacer.addActionListener(deshacer);
        btnOpcionesRespuesta.addActionListener(CatOpciones_Repuesta);
        btnPrioridad.addActionListener(CatOpciones_PrioridadyPonderacion);
        btnUsuarios.addActionListener(CatUsuarios);
        btnFrecuencia.addActionListener(CatFrecuencia);
        
        horasdefault();
        componentes(false);
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_G,Event.CTRL_MASK),"guardar");
        getRootPane().getActionMap().put("guardar", new AbstractAction(){ public void actionPerformed(ActionEvent e){ btnAprovar.doClick();  }  });
 	   
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
 	    getRootPane().getActionMap().put("escape", new AbstractAction(){  public void actionPerformed(ActionEvent e){ btnDeshacer.doClick(); }  });
 	    
 	    cont.add(panel);
 		txtFolio.setEditable(false);
	}
	
	public void componentes (boolean booleano) {
	txa_detalle_de_la_actividad.setEditable(booleano);
	btnUsuarios.setEnabled(booleano);
	btnPrioridad.setEnabled(booleano);
	btnOpcionesRespuesta.setEnabled(booleano);
	btnFrecuencia.setEnabled(booleano);
	jspHoraInicio.setEnabled(booleano);
	jspHorafinal.setEnabled(booleano);	
	if(booleano) {txa_detalle_de_la_actividad.setBackground(new Color(255,255,255));}else { txa_detalle_de_la_actividad.setBackground(new Color(Integer.parseInt("EBEBEB",16))); }
	}
	
	@SuppressWarnings("deprecation")
	public void horasdefault(){
		String[] inicioDefault ="0:00:00".split (":");
		String[] finDefault    ="23:59:59".split(":");
		jspHoraInicio.setValue(new Time(Integer.parseInt(inicioDefault[0]) ,Integer.parseInt(inicioDefault[1]) ,Integer.parseInt(inicioDefault[2]) ));
		jspHorafinal.setValue( new Time(Integer.parseInt(finDefault[0])    ,Integer.parseInt(finDefault[1])    ,Integer.parseInt(finDefault[2])    ));
	}
	
	ActionListener nuevo = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			String folio="";
			GuardarActualizar="N";
			txa_detalle_de_la_actividad.setText("");
			try {folio= new BuscarSQL().folio_siguiente(64+"");} catch (SQLException e1) {	e1.printStackTrace();}
			txtFolio.setText(folio);
			horasdefault();
			componentes(true);
			btnUsuarios.doClick();
			btnAprovar.setEnabled(true);
		 }
	};
	
	ActionListener deshacer = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			txa_detalle_de_la_actividad.setText("");
			 OpRespuesta= new Obj_Opciones_De_Respuesta();
			 OpPonderacion= new Obj_Prioridad_Y_Ponderacion();
			 usuarios= new Obj_Seleccion_De_Usuarios();
			 frecuencia = new Obj_Frecuencia_De_Actividades();
			 Actividad_plan = new Obj_Actividades_De_Una_Planeacion();
			 txa_detalle_de_la_actividad.setText("");
			 horasdefault();
			 componentes (false);
		 }
	};
		
	ActionListener CatOpciones_Repuesta = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			new Cat_Opciones_De_Respuesta_De_La_Actividad().setVisible(true);;
	       }
    	};
	
    ActionListener CatOpciones_PrioridadyPonderacion = new ActionListener(){
    	public void actionPerformed(ActionEvent e){
    		new Cat_Prioridad_Y_Ponderacion_De_Una_Actividad().setVisible(true);;
           }
       	};
       	
     ActionListener CatUsuarios = new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		new Cat_Seleccion_Del_Usuario().setVisible(true);;
               }
           	};
    
     ActionListener CatFrecuencia = new ActionListener(){
          	public void actionPerformed(ActionEvent e){
           		new Cat_Frecuencia_De_Actividades().setVisible(true);;
                }
          	};       

	ActionListener opAprovar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(txa_detalle_de_la_actividad.getText().toString().equals("")){
				JOptionPane.showMessageDialog(null, "Necesitas Escribir El Detalle De La Actividad", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}else{				
				Actividad_plan.setEstatus_Actividad("V");
				Actividad_plan.setDescripcion_de_la_actividad(txa_detalle_de_la_actividad.getText().toString().trim());
				Actividad_plan.setHora_inicia(new SimpleDateFormat("HH:mm:ss").format(jspHoraInicio.getValue()));
				Actividad_plan.setHora_termina(new SimpleDateFormat("HH:mm:ss").format(jspHorafinal.getValue()));
				Actividad_plan.setGuardar_actualizar(GuardarActualizar);
				
				if(Actividad_plan.guardar_cuadrantes_actividad_jerarquico(OpRespuesta,OpPonderacion,usuarios,frecuencia, usuario.getFolio())){
	                btnDeshacer.doClick();
					JOptionPane.showMessageDialog(null, "Se Guardo Correctamente", "Aviso", JOptionPane.OK_OPTION,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));

				}else{
					JOptionPane.showMessageDialog(null, "Error Al Guardar La Actividad", "Avisa Al Administrador Del Sistema", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-icono-eliminar5252-64.png"));
					return;
			}
		}
	  }
	};
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////TODO Ventana De Opcion de Respuesta/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public class Cat_Opciones_De_Respuesta_De_La_Actividad extends JDialog{
		Container cont = getContentPane();
		JLayeredPane panel = new JLayeredPane();
		
		JTextArea txa_detalle_de_la_actividad = new Componentes().textArea(new JTextArea(), "Concepto", 135);
		JScrollPane Concepto = new JScrollPane(txa_detalle_de_la_actividad);
		
		JLabel lblLineaContestacion      = new JLabel("");
		JRadioButton rbResuelto		     = new JRadioButton("Resuelta(Si)");
		JRadioButton rbNo 		         = new JRadioButton("Incumplida(No)");
		JRadioButton rbPendiente         = new JRadioButton("No Aplica (NA)");
		
		JLabel lblLineaEvidencia   	     = new JLabel("");
		JRadioButton rbSinEvidencia      = new JRadioButton("No Exige Evidencia");
		JRadioButton rbExigeEvidencia    = new JRadioButton("Exige Evidencia");
		ButtonGroup GrupoExigeEvidencia  = new ButtonGroup();
		
		JLabel lblLineaObservacion   	 = new JLabel("");
		JRadioButton rbSinObservacion    = new JRadioButton("No Exige Observacion");
		JRadioButton rbExigeObservacion  = new JRadioButton("Exige Observacion");
		ButtonGroup GrupoExigeObservacion= new ButtonGroup();
		
		JCButton btnAprovar   = new JCButton("Aplicar"   ,"Aplicar.png"       ,"Azul"); 
		JCButton btnDeshacer  = new JCButton("Deshacer"  ,"deshacer16.png"    ,"Azul"); 
		Border linea;
		
		public Cat_Opciones_De_Respuesta_De_La_Actividad(){
			this.setSize(380, 360);
			this.setLocationRelativeTo(null);
			this.setResizable(false);
			this.setModal(true);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/ver-info-boton-icono-8754-64.png"));
			this.setTitle("Configuracion De La Respuesta");
			this.panel.setBorder(BorderFactory.createTitledBorder("Seleccione Las Opciones De Respuesta"));
			this.linea = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
			this.lblLineaContestacion.setBorder(BorderFactory.createTitledBorder(linea,"Opciones Para Contestación"));
			this.lblLineaEvidencia.setBorder(BorderFactory.createTitledBorder(linea,"Opciones Para Evidencia"));
			this.lblLineaObservacion.setBorder(BorderFactory.createTitledBorder(linea,"Opciones Para Observación"));
			int x=15,y=20,width=150,height=20,i=18;
			
			panel.add(btnDeshacer).setBounds                (x    ,y     ,110     ,height);
			panel.add(btnAprovar).setBounds                 (x+120,y     ,110     ,height);
			
			panel.add(lblLineaContestacion).setBounds       (x-5 ,y+=25 ,width+22, 110);
			panel.add(rbResuelto).setBounds                 (x   ,y+=15 ,width   , height);
			panel.add(rbNo).setBounds                       (x   ,y+=i  ,width   , height);
			panel.add(rbPendiente).setBounds                (x   ,y+=i  ,width   , height);
			x=200; 
			panel.add(lblLineaEvidencia).setBounds          (x-5 ,y=45   ,width+20, 57);
			panel.add(rbSinEvidencia).setBounds             (x   ,y+=15  ,width   , height);
			panel.add(rbExigeEvidencia).setBounds           (x   ,y+=i  ,width   , height);
			GrupoExigeEvidencia.add(rbSinEvidencia);
			GrupoExigeEvidencia.add(rbExigeEvidencia);
			x=200;
			panel.add(lblLineaObservacion).setBounds        (x-5 ,y=98 ,width+20, 57);
			panel.add(rbSinObservacion).setBounds           (x   ,y+=15,width   , height);
			panel.add(rbExigeObservacion).setBounds         (x   ,y+=i ,width   , height);
			GrupoExigeObservacion.add(rbSinObservacion);
			GrupoExigeObservacion.add(rbExigeObservacion);
			
			x=10;
			panel.add(new JLabel("     Detalle De La Configuracion Seleccionada:")).setBounds(x    ,y+=25 ,width*3 ,height);
			panel.add(Concepto  ).setBounds                                                  (x    ,y+=20 ,355     ,142);

			rbResuelto.setSelected(Boolean.valueOf(OpRespuesta.getResuelta()));
			rbNo.setSelected(Boolean.valueOf(OpRespuesta.getIncumplida()));
			rbPendiente.setSelected(Boolean.valueOf(OpRespuesta.getPendiente()));
			rbSinEvidencia.setSelected(Boolean.valueOf(OpRespuesta.getNo_Exige_Evidencia()));
			rbSinObservacion.setSelected(Boolean.valueOf(OpRespuesta.getNoExigeObservacion()));
			rbExigeEvidencia.setSelected(Boolean.valueOf(OpRespuesta.getExige_Evidencia()));
			rbExigeObservacion.setSelected(Boolean.valueOf(OpRespuesta.getExigeObservacion()));
			
			rbResuelto.setEnabled(false);
			rbNo.setEnabled(false);
			rbNo.setSelected(true);
			txa_detalle_de_la_actividad.setEditable(false);
			txa_detalle_de_la_actividad.setLineWrap(true); 
			txa_detalle_de_la_actividad.setWrapStyleWord(true);
			
			btnAprovar.addActionListener(opAprovar_opcion_Respuesta);
			btnDeshacer.addActionListener(deshacer_opcion_respuesta);
			
			rbResuelto.addActionListener(Comentario);
			rbNo.addActionListener(Comentario);
			rbPendiente.addActionListener(Comentario);
			rbExigeEvidencia.addActionListener(Comentario);
			rbSinEvidencia.addActionListener(Comentario);
			rbExigeObservacion.addActionListener(Comentario);
			rbSinObservacion.addActionListener(Comentario);
			comentarios();

			///guardar con control+A
	        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_G,Event.CTRL_MASK),"guardar");
	        getRootPane().getActionMap().put("guardar", new AbstractAction(){  public void actionPerformed(ActionEvent e) {    btnAprovar.doClick();    }     });

            getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
	 	                   getRootPane().getActionMap().put("escape", new AbstractAction(){
	 	                  public void actionPerformed(ActionEvent e)
	 	                  {                btnDeshacer.doClick();           	    }
	 	              });
	 	    cont.add(panel);
		}
		
		ActionListener deshacer_opcion_respuesta = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				txa_detalle_de_la_actividad.setText("");
				rbResuelto.setSelected(true);
				
				rbPendiente.setSelected(true);
				rbSinEvidencia.setSelected(true);
				rbSinObservacion.setSelected(true);
				comentarios();
			 }
			};
		
		ActionListener opAprovar_opcion_Respuesta = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OpRespuesta.setResuelta(String.valueOf(rbResuelto.isSelected()));
				OpRespuesta.setIncumplida(String.valueOf(rbNo.isSelected()));
				OpRespuesta.setPendiente(String.valueOf(rbPendiente.isSelected()));
				OpRespuesta.setExige_Evidencia(String.valueOf(rbExigeEvidencia.isSelected()));
				OpRespuesta.setNo_Exige_Evidencia(String.valueOf(rbSinEvidencia.isSelected()));
				OpRespuesta.setExigeObservacion(String.valueOf(rbExigeObservacion.isSelected()));
				OpRespuesta.setNoExigeObservacion(String.valueOf(rbSinObservacion.isSelected()));
				dispose();
			}
		};
		ActionListener Comentario = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				comentarios();
			 }
		};
		
		public void comentarios(){
			  txa_detalle_de_la_actividad.setText("");
			  String comentario =">>>Esta Actividad Podrá Contestarse Con Las Opciones De Respuesta:";
			  String comentariofinal ="\n>>>No Tendrá La Opcion De Contestar La Actividad Como:";
			  String comentariofinaleo ="";
			  Integer testigofinal =0;
			
		  if(Boolean.valueOf(rbResuelto.isSelected())){
			  	 comentario=comentario+"\n   *Resuelta";
			   }else{
				   comentariofinal=comentariofinal+"\n   -Resuelta";
				   testigofinal = 1 ;
			}
		  if(Boolean.valueOf(rbNo.isSelected())){
				 comentario=comentario+"\n   *Incumplida";
			   }else{
				   comentariofinal=comentariofinal+"\n   -Incumplida";
				   testigofinal = 1 ;
			}
		  if(Boolean.valueOf(rbPendiente.isSelected())){
				 comentario=comentario+"\n   *Pendiente";
			   }else{
				   comentariofinal=comentariofinal+"\n   -Pendiente";
				   testigofinal = 1 ;
			}
		  if(Boolean.valueOf(rbExigeEvidencia.isSelected())){
			  comentariofinaleo=comentariofinaleo+"\n>>>*Exigirá Evidencia";
			   }else{
				   comentariofinaleo=comentariofinaleo+"\n>>>No Será Exigible La Evidencia";
			}
		  if(Boolean.valueOf(rbExigeObservacion.isSelected())){
			  comentariofinaleo=comentariofinaleo+"\n>>>*Exigirá Observación";
			   }else{
				   comentariofinaleo=comentariofinaleo+"\n>>>No Será Exigible La Observación";
			}
			txa_detalle_de_la_actividad.setText(comentario+(testigofinal==1?comentariofinal:"")+"\n"+comentariofinaleo);
		}	
		
	}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////TODO Ventana De Prioridad y Ponderacion/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public class Cat_Prioridad_Y_Ponderacion_De_Una_Actividad extends JDialog{
		Container cont = getContentPane();
		JLayeredPane panel = new JLayeredPane();
		
		JTextArea txa_detalle_de_la_actividad = new Componentes().textArea(new JTextArea(), "Observaciones De La Configuracion", 135);
		JScrollPane Concepto = new JScrollPane(txa_detalle_de_la_actividad);
		
		JLabel lblLineaPrioridad        = new JLabel("");
		JRadioButton rbpImportante		= new JRadioButton("Importante");
		JRadioButton rbpUrgente		    = new JRadioButton("Urgente");
		JRadioButton rbPreventivo       = new JRadioButton("Preventivo");
		JRadioButton rbNormal           = new JRadioButton("Normal");
		ButtonGroup GrupPrioridad       = new ButtonGroup();
		
		JLabel lblGrupPonderacion       = new JLabel("");
		JRadioButton rb1		        = new JRadioButton("1>");
		JRadioButton rb2		        = new JRadioButton("2>>");
		JRadioButton rb3                = new JRadioButton("3>>>");
		JRadioButton rb4                = new JRadioButton("4>>>>");
		JRadioButton rb5                = new JRadioButton("5>>>>>");
		ButtonGroup GrupoPonderacion    = new ButtonGroup();
		
		JCButton btnAprovar   = new JCButton("Aplicar"   ,"Aplicar.png"       ,"Azul"); 
		JCButton btnDeshacer  = new JCButton("Deshacer"  ,"deshacer16.png"    ,"Azul"); 
		Border linea;
		
		int valor_de_seleccionado=0;
		
		public Cat_Prioridad_Y_Ponderacion_De_Una_Actividad(){
			this.setSize(315, 309);
			this.setLocationRelativeTo(null);
			this.setResizable(false);
			this.setModal(true);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/favoritos-ver-boton-icono-8318-64.png"));
			this.setTitle("Prioridad y Ponderacion");
			this.panel.setBorder(BorderFactory.createTitledBorder("Selecciona La Prioridad y Ponderacion"));
			this.linea = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
			this.lblLineaPrioridad.setBorder(BorderFactory.createTitledBorder(linea,"Prioridad"));
			this.lblGrupPonderacion.setBorder(BorderFactory.createTitledBorder(linea,"Ponderacion"));
			
			this.GrupPrioridad.add(rbpImportante);
			this.GrupPrioridad.add(rbpUrgente);
			this.GrupPrioridad.add(rbPreventivo);
			this.GrupPrioridad.add(rbNormal);
			
			this.GrupoPonderacion.add(rb1);
			this.GrupoPonderacion.add(rb2);
			this.GrupoPonderacion.add(rb3);
			this.GrupoPonderacion.add(rb4);
			this.GrupoPonderacion.add(rb5);
			
			int x=10,y=15,width=80,height=20,i=18;
			this.panel.add(btnDeshacer).setBounds                                                 (x    ,y     ,110     ,height  );
			this.panel.add(btnAprovar).setBounds                                                  (x+120,y     ,110     ,height  );
			this.panel.add(lblLineaPrioridad).setBounds                                           (x-5  ,y+=25 ,width+10, 95     );
			this.panel.add(rbNormal).setBounds                                                    (x    ,y+=10 ,width   , height );
			this.panel.add(rbPreventivo).setBounds                                                (x    ,y+=i  ,width   , height );
			this.panel.add(rbpUrgente).setBounds                                                  (x    ,y+=i  ,width   , height );
			this.panel.add(rbpImportante).setBounds                                               (x    ,y+=i  ,width   , height );
	        x=220;
	        this.panel.add(lblGrupPonderacion).setBounds                                          (x-5  ,y=40  ,width+10, 110    );
			this.panel.add(rb1).setBounds                                                         (x    ,y+=10 ,width   , height );
			this.panel.add(rb2).setBounds                                                         (x    ,y+=i  ,width   , height );
			this.panel.add(rb3).setBounds                                                         (x    ,y+=i  ,width   , height );
			this.panel.add(rb4).setBounds                                                         (x    ,y+=i  ,width   , height );
			this.panel.add(rb5).setBounds                                                         (x    ,y+=i  ,width   , height );
			y=105;
			this.panel.add(new JLabel("Detalle De La Configuracion Seleccionada:")).setBounds     (x=5  ,y+=45 ,220     ,height  );
			this.panel.add(Concepto  ).setBounds                                                  (x    ,y+=20 ,297     ,height*5);

			this.txa_detalle_de_la_actividad.setEditable(false);
			this.txa_detalle_de_la_actividad.setLineWrap(true); 
			this.txa_detalle_de_la_actividad.setWrapStyleWord(true);
			this.rbNormal.setSelected(Boolean.valueOf(OpPonderacion.getNormal()));
			this.rbPreventivo.setSelected(Boolean.valueOf(OpPonderacion.getPreventivo()));
			this.rbpUrgente.setSelected(Boolean.valueOf(OpPonderacion.getUrgente()));
			this.rbpImportante.setSelected(Boolean.valueOf(OpPonderacion.getImportante()));
			
			switch(Integer.valueOf(OpPonderacion.getPonderacion())){
				case 1:
					rb1.setSelected(true);
					 break;
				case 2:
					rb2.setSelected(true);
					 break;
				case 3:
					rb3.setSelected(true);
					 break;
				case 4:
					rb4.setSelected(true);
					 break;
				case 5:
					rb5.setSelected(true);	
					 break;
				default: 
					 rb1.setSelected(true);
				    break;
		     }
			
			this.btnAprovar.addActionListener(opAprovar);
			this.btnDeshacer.addActionListener(deshacer);
			this.rbNormal.addActionListener(Comentario_prioridadyponderacion);
			this.rbPreventivo.addActionListener(Comentario_prioridadyponderacion);
			this.rbpUrgente.addActionListener(Comentario_prioridadyponderacion);
			this.rbpImportante.addActionListener(Comentario_prioridadyponderacion);
			this.rb1.addActionListener(Comentario_prioridadyponderacion);
			this.rb2.addActionListener(Comentario_prioridadyponderacion);
			this.rb3.addActionListener(Comentario_prioridadyponderacion);
			this.rb4.addActionListener(Comentario_prioridadyponderacion);
			this.rb5.addActionListener(Comentario_prioridadyponderacion);
			comentarios();
			
	        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A,Event.CTRL_MASK),"guardar");
	        getRootPane().getActionMap().put("guardar", new AbstractAction(){ public void actionPerformed(ActionEvent e){   btnAprovar.doClick();           	    }        });

	        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
	 	    getRootPane().getActionMap().put("escape", new AbstractAction(){  public void actionPerformed(ActionEvent e){   btnDeshacer.doClick();           	    }        });
	 	    cont.add(panel);
		}
		
		ActionListener Comentario_prioridadyponderacion = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				comentarios();
			 }
		};
		
		public void comentarios(){
			  txa_detalle_de_la_actividad.setText("");
			  String comentario =">>>Esta Actividad Se Clasifica Con Prioridad :";
			  String comentariofinal ="\n>>>Con Un Nivel De Ponderación De: \n   *";
			  String ponderacion="";
			
		  if(Boolean.valueOf(rbNormal.isSelected())){
			  	                                     comentario=comentario+"\n   *Normal";
		     }else{ if(Boolean.valueOf(rbPreventivo.isSelected())){			   
				                                                   comentario=comentario+"\n   *Preventivo";
			           }else{ if(Boolean.valueOf(rbpUrgente.isSelected())){
				                                                           comentario=comentario+"\n   *Urgente";
			                     }else{ if(Boolean.valueOf(rbpImportante.isSelected())){
			                    	                                                    comentario=comentario+"\n   *Importante";
			                          }
			                }
			      }               
		     };                 	                                                    
			                    	                                                    
		     if(rb1.isSelected()){
					             ponderacion="1";
					}else{ if(rb2.isSelected()){
					                           ponderacion="2";
					       }else{ if(rb3.isSelected()){
					                                    ponderacion="3";
					                }else{ if(rb4.isSelected()){
					                                           ponderacion="4";
					                         }else{if(rb5.isSelected()){
					                                                   ponderacion="5";
					                                 }
					                       }
					                }
					       }
					}                    	                                                    
			                    	                                                    
			txa_detalle_de_la_actividad.setText(comentario+comentariofinal+ponderacion);
		}	
		
		ActionListener deshacer = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				txa_detalle_de_la_actividad.setText("");
				rbNormal.setSelected(true);
				rb1.setSelected(true);
				comentarios();
			 }
			};
		
		ActionListener opAprovar = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 int ponderacion=0;
				if(rb1.isSelected()){
					                  ponderacion=1;
				       }else{ if(rb2.isSelected()){
				    	                            ponderacion=2;
				                }else{ if(rb3.isSelected()){
	                                                         ponderacion=3;
	                                     }else{ if(rb4.isSelected()){
	                                                                ponderacion=4;
	                                              }else{if(rb5.isSelected()){
	                                                                        ponderacion=5;
	                                                      }
	                                            }
	                                     }
				                }
				       }

				OpPonderacion.setImportante(String.valueOf(rbpImportante.isSelected()));
				OpPonderacion.setUrgente(String.valueOf(rbpUrgente.isSelected()));
				OpPonderacion.setPreventivo(String.valueOf(rbPreventivo.isSelected()));
				OpPonderacion.setNormal(String.valueOf(rbNormal.isSelected()));
				OpPonderacion.setPonderacion(ponderacion);
	            dispose(); 
			}
		};
	}	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////TODO Ventana De Seleccion Del Usuario/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public class Cat_Seleccion_Del_Usuario extends JDialog{
		Container cont = getContentPane();
		JLayeredPane panel = new JLayeredPane();

		int columnasb = 6,checkbox=6;
		public void init_tablafu(){
			this.tablaus.getColumnModel().getColumn(0).setMaxWidth(40);
			this.tablaus.getColumnModel().getColumn(0).setMinWidth(40);
			this.tablaus.getColumnModel().getColumn(1).setMinWidth(267);
			this.tablaus.getColumnModel().getColumn(2).setMinWidth(120);
			this.tablaus.getColumnModel().getColumn(3).setMinWidth(120);
			this.tablaus.getColumnModel().getColumn(4).setMinWidth(230);
			this.tablaus.getColumnModel().getColumn(5).setMinWidth(20);
			this.tablaus.getColumnModel().getColumn(5).setMaxWidth(20);
			String basedatos="98",pintar="si";
			
			if(!(usuarios.getUsuarios_nombres()==null)){
				String comandob="Select 0,'','','','','' ";
				ObjTab.Obj_Refrescar(tablaus,modelous, columnasb, comandob, basedatos,pintar,checkbox);
				modelous.setRowCount(0);
				ObjTab.llenado_de_modelo_desde_datos_tabla_precargados(usuarios.getUsuarios_nombres(),tablaus);
				funcion_agregar();
			}else{
			  String comandob="exec cuadrantes_actividades_asignacion_jerarquica_filtro_empleado "+usuario.getFolio();
			  ObjTab.Obj_Refrescar(tablaus,modelous, columnasb, comandob, basedatos,pintar,checkbox);
			}
	    }
		
		DefaultTableModel modelous = new DefaultTableModel(null, new String[]{"Folio", "Nombre Completo","Establecimiento","Departamento","Puesto", ""}
				){
		     @SuppressWarnings("rawtypes")
			Class[] types = new Class[]{
		    	java.lang.Integer.class,
		    	java.lang.String.class,
		    	java.lang.String.class,
		    	java.lang.String.class,
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
	        	 	case 2 : return false; 
	        	 	case 3 : return false; 
	        	 	case 4 : return false; 
	        	 	case 5 : return true; 
	        	 } 				
	 			return false;
	 		}
			
		};
		JTable tablaus = new JTable(modelous);
	    JScrollPane scroll = new JScrollPane(tablaus);
		
		JTextArea txa_Resultado_Seleccion = new Componentes().textArea(new JTextArea(), "Concepto", 135);
		JScrollPane Resultado = new JScrollPane(txa_Resultado_Seleccion);
		JTextField	txtFiltro           = new Componentes().textfiltro(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<",500 , "String",tablaus,columnasb );
		JCButton btnAgregar   = new JCButton("Aplicar"   ,"Aplicar.png"       ,"Azul"); 
		JCButton btnDeshacer  = new JCButton("Deshacer"  ,"deshacer16.png"    ,"Azul"); 
		
		public Cat_Seleccion_Del_Usuario()	{
			this.setSize(855,530);
			this.setLocationRelativeTo(null);
			this.setResizable(false);
			this.setModal(true);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/ayudar-a-ver-el-boton-icono-4900-64.png"));
			this.setTitle("Filtro De Seleccion De Colaboradores");
		    this.panel.setBorder(BorderFactory.createTitledBorder("Selecciona (El/Los) Colaborador(es) Que Aplique(n) A La Actividad"));
			this.txa_Resultado_Seleccion.setEditable(false);
			this.txa_Resultado_Seleccion.setLineWrap(true); 
			this.txa_Resultado_Seleccion.setWrapStyleWord(true);
			
			this.addWindowListener(op_cerrar);
			
			int x=10,y=20,width=100,height=20;
			this.panel.add(btnDeshacer).setBounds                                                 (x     ,y     ,110      ,height);
			this.panel.add(btnAgregar).setBounds                                                  (x+130 ,y     ,110      ,height);
			
			this.panel.add(txtFiltro).setBounds                                                   (x     ,y+=30 ,width*8+30 ,height);
			this.panel.add(scroll).setBounds                                                      (x     ,y+=20 ,width*8+30 ,width*2);
			this.panel.add(new JLabel("Colaboradores A Los Que Se Les Agregara La Actividad:")).setBounds(x     ,y+=200,width*3    ,height);
			this.panel.add(Resultado  ).setBounds                                                 (x     ,y+=20 ,width*8+30 ,width*2);

			
			this.cont.add(panel);
			this.init_tablafu();
			this.agregar(tablaus);
			this.txtFiltro.addKeyListener(Buscar_Datos_en_tabla);
			this.btnAgregar.addActionListener(Agregar);
			this.btnDeshacer.addActionListener(deshacer);
			
	        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_G,Event.CTRL_MASK),"guardar");
	        getRootPane().getActionMap().put("guardar", new AbstractAction(){ public void actionPerformed(ActionEvent e){   btnAgregar.doClick();   }  });

	 	    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
	 	    getRootPane().getActionMap().put("escape", new AbstractAction(){  public void actionPerformed(ActionEvent e){   btnDeshacer.doClick();  }  });
	 	    txtFiltro.requestFocus();

		}
		
		KeyListener Buscar_Datos_en_tabla = new KeyListener() {
			public void keyTyped(KeyEvent e){}
			public void keyReleased(KeyEvent e) {}
			public void keyPressed(KeyEvent e) {
				funcion_agregar();
			}
		};
		
		WindowListener op_cerrar = new WindowListener() {
			public void windowOpened(WindowEvent e) {}
			public void windowIconified(WindowEvent e) {}
			public void windowDeiconified(WindowEvent e) {}
			public void windowDeactivated(WindowEvent e) {}
			public void windowClosing(WindowEvent e) {
				
			}
			public void windowClosed(WindowEvent e) {
				aplicar("No");
			}
			public void windowActivated(WindowEvent e) {}
		};
		
		ActionListener Agregar = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				aplicar("Si") ; 
			}
		};
		
		public void aplicar(String cerrar) {
			  if(tablaus.isEditing()){tablaus.getCellEditor().stopCellEditing();}
			    int cant=0;
				for(int i=0;i<tablaus.getRowCount();i++) {
					if(tablaus.getValueAt(i, 5).toString().equals("true")){
						cant=cant+1;
				     }
				}
			if(cant>0){
				if(!txtFiltro.getText().equals("")){
					JOptionPane.showMessageDialog(null, "No se Puede Aplicar En Medio De Una Busqueda, Por Que Podrian Haber Mas Colaboradores Seleccionados "
							                          + "\nA Continuación Se Borrará El Filtro Para Mostrarle Todos Los Colaboradores y Se Aplicará De Nuevo"
                                                    , "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
					txtFiltro.setText("");
					int[] columnas = {0,1,2,3,4};
					new Obj_Filtro_Dinamico_Plus(tablaus,"", columnas);
					btnAgregar.doClick();
					return;
				 }
				usuarios.setUsuarios_nombres(ObjTab.tabla_guardar(tablaus));
				dispose();
			}else {
				JOptionPane.showMessageDialog(null, "Es Requerido Seleccione por lo menos un  colaborador" , "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
				if(cerrar.equals("No")) {
					new Cat_Seleccion_Del_Usuario().setVisible(true);
				}else {				
				return;
				}
			}	
		}
		
		ActionListener deshacer = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				txa_Resultado_Seleccion.setText("");
				usuarios= new Obj_Seleccion_De_Usuarios();
				init_tablafu();
			 }
		};
		
		private void agregar(final JTable tbl) {
			tbl.addMouseListener(new MouseListener() {
				public void mouseReleased(MouseEvent e) {
			 	 if(e.getClickCount()>0){funcion_agregar();}
				}
				public void mousePressed(MouseEvent e) {
				 if(e.getClickCount()>0){funcion_agregar();}	
				}
				public void mouseExited(MouseEvent e)  {
				 if(e.getClickCount()>0){funcion_agregar();}	
				}
				public void mouseEntered(MouseEvent e) {
				 if(e.getClickCount()>0){funcion_agregar();}	
				}
				public void mouseClicked(MouseEvent e) {
				 if(e.getClickCount()>0){funcion_agregar();}	
				}
			});
			
			tbl.addKeyListener(new KeyListener() {
				public void keyPressed(KeyEvent e)  {
						funcion_agregar();	
				}
				public void keyReleased(KeyEvent e)   {
					funcion_agregar();	
				}
				public void keyTyped   (KeyEvent e)   {
					funcion_agregar();	
				}
			});
	    }

		 public void funcion_agregar() {
		  txa_Resultado_Seleccion.setText("");
			
		  String Comentario_colaboradores="";
		  if(txtFiltro.getText().toString().equals("")) {
			for(int i = 0; i < tablaus.getRowCount(); i++){
				if(Boolean.valueOf(tablaus.getValueAt(i, 5)+""  ) ) {
				 Comentario_colaboradores=Comentario_colaboradores+tablaus.getValueAt(i, 1)+"\n";
				}
			}
		  }else {
			  Comentario_colaboradores="En Medio De La Busqueda No Se Muestra La Seleccion";
		  }		  
			txa_Resultado_Seleccion.setText(Comentario_colaboradores);
	    }

	}
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////TODO Ventana De Frecuencia De La Actividad/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public class Cat_Frecuencia_De_Actividades extends JDialog{
		Container cont = getContentPane();
		JLayeredPane panel = new JLayeredPane();
		
	//tipo de programacion	
		String[] tipo_de_programacion = {"UNA VEZ","PERIODICA"};
		@SuppressWarnings({ "rawtypes", "unchecked" })
		JComboBox cmbTipoDeProgramacion = new JComboBox(tipo_de_programacion);
		
		JRadioButton rbHastaQueSeCumpla = new JRadioButton("Hasta Que Se Cumpla");
		JRadioButton rbEnLaFechaIndicada = new JRadioButton("En La Fecha Indicada");
		ButtonGroup grupoTipoDeProgramacion = new ButtonGroup();
		
	//unica repeticion
		JLabel lblUnicarepeticion = new JLabel("Desde");
		JDateChooser fh_unica_repeticion = new JDateChooser();
		
		JCheckBox chbConHora = new JCheckBox();
		
		SpinnerDateModel sdmUnicaRepeticion =  new SpinnerDateModel();
		  JSpinner spHoraUnicaRepeticion = new JSpinner(sdmUnicaRepeticion);                                         
		  JSpinner.DateEditor spDHoraUnicaRepeticion = new JSpinner.DateEditor(spHoraUnicaRepeticion,"HH:mm:ss"); 
		
	//frecuencia  -----------------------------------------------------------------------------
//		  String[] sucede = {"DIARIA","SEMANAL","MENSUAL"};
		  String[] sucede = {"SEMANAL"};
			@SuppressWarnings({ "rawtypes", "unchecked" })
			JComboBox cmbSucede = new JComboBox(sucede);
			
			JRadioButton rbDiaDelMes = new JRadioButton("El Dia");
			JRadioButton rbDiaDeLaSemana = new JRadioButton("EL");
			ButtonGroup grupoDias = new ButtonGroup();
			
			JLabel lblSeRepiteCada = new JLabel("Se Repite Cada: ");
			JSpinner spDiasARepetir = new JSpinner(new SpinnerNumberModel( 1, 1, 31, 1 )); 
			JLabel lblDias_Semana = new JLabel("Dias");
			
			JCheckBox chbLunes = new JCheckBox("Lunes");
			JCheckBox chbMartes = new JCheckBox("Martes");
			JCheckBox chbMiercoles = new JCheckBox("Miercoles");
			JCheckBox chbJueves = new JCheckBox("Jueves");
			JCheckBox chbViernes = new JCheckBox("Viernes");
			JCheckBox chbSabado = new JCheckBox("Sabado");
			JCheckBox chbDomingo = new JCheckBox("Domingo");
			
			JSpinner spMeses = new JSpinner(new SpinnerNumberModel( 1, 1, 12, 1 )); 
			JLabel lblMeses = new JLabel("Meses");
			
//			configuracion por dias del mes
			String[] numeroDeDias = {"Primer","Segundo","Tercer","Cuarto","Ultimo"};
			@SuppressWarnings({ "rawtypes", "unchecked" })
			JComboBox cmbNivelDeDias = new JComboBox(numeroDeDias);
			
			String[] diaDeLaSemana = {"Lunes","Martes","Miercoles","Jueves","Viernes","Sabado","Domingo"};
			@SuppressWarnings({ "rawtypes", "unchecked" })
			JComboBox cmbDiaDeLaSemana = new JComboBox(diaDeLaSemana);
					
			JLabel lblDeCada = new JLabel("De Cada");
			JSpinner spMeses2 = new JSpinner(new SpinnerNumberModel( 1, 1, 12, 1 )); 
			JLabel lblMeses2 = new JLabel("Meses");
			
	//frecuencia diaria
			JCheckBox chAsignarHora = new JCheckBox("Asignar Hora");
			
			SpinnerDateModel sdmFrecuenciaDiaria =  new SpinnerDateModel();
			  JSpinner spHoraFrecuenciaDiaria = new JSpinner(sdmFrecuenciaDiaria);                                         
			  JSpinner.DateEditor spDHoraFrecuenciaDiaria = new JSpinner.DateEditor(spHoraFrecuenciaDiaria,"H:mm:ss"); 
		
	//Duracion
			JRadioButton rbFechaDeFinalizacion = new JRadioButton("Fecha De Finalizacion:");
			JRadioButton rbSinFechaDeFinalizacion = new JRadioButton("Sin Fecha De Finalizacion");
			ButtonGroup grupoFechaDeFinalizacion = new ButtonGroup();
			
			JDateChooser fh_inicial_de_duracion = new JDateChooser();
			JDateChooser fh_final_de_duracion = new JDateChooser();

	//resumen
		JTextArea txaDescripcion = new Componentes().textArea(new JTextArea(), "Observaciones", 980);
		JScrollPane scrollDescripcion = new JScrollPane(txaDescripcion);
		
		JCButton btnAceptar   = new JCButton("Aplicar"   ,"Aplicar.png"       ,"Azul"); 
		JCButton btnDeshacerFrecuencia  = new JCButton("Deshacer"  ,"deshacer16.png"    ,"Azul"); 
		
		Border blackline;
		
		public Cat_Frecuencia_De_Actividades() {
			this.setSize(660,550);
			this.setLocationRelativeTo(null);
			this.setResizable(false);
			this.setModal(true);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/tiempo-de-botones-icono-4873-64.png"));
			this.setTitle("Programacion De La Frecuencia De Una Actividad");
		    this.panel.setBorder(BorderFactory.createTitledBorder("Selecciona La Frecuencia Que Consideres Apropiada"));
			
			this.grupoTipoDeProgramacion.add(rbHastaQueSeCumpla);
			this.grupoTipoDeProgramacion.add(rbEnLaFechaIndicada);
			this.grupoFechaDeFinalizacion.add(rbFechaDeFinalizacion);
			this.grupoFechaDeFinalizacion.add(rbSinFechaDeFinalizacion);
			this.grupoDias.add(rbDiaDelMes);
			this.grupoDias.add(rbDiaDeLaSemana);
			
			this.txaDescripcion.setBorder(BorderFactory.createTitledBorder(blackline));
			this.txaDescripcion.setLineWrap(true); 
			this.txaDescripcion.setWrapStyleWord(true);
			this.txaDescripcion.setEditable(false);
			
			int x=50,y=15,ancho=80;
			
			panel.add(new JLabel("Tipo De Programación: ")).setBounds(x    ,y    ,ancho+50  ,20);
			panel.add(cmbTipoDeProgramacion).setBounds               (x+140,y    ,ancho+20  ,20);
			panel.add(new JSeparator()).setBounds                    (x-30 ,y+28 ,ancho+530 ,20);
			
		//unica repeticion
			panel.add(rbHastaQueSeCumpla).setBounds                  (x    ,y+=35,ancho*2   ,20);
			panel.add(lblUnicarepeticion).setBounds                  (x+200,y    ,ancho     ,20);
			panel.add(fh_unica_repeticion).setBounds                 (x+270,y    ,ancho+40  ,20);
			panel.add(rbEnLaFechaIndicada).setBounds                 (x    ,y+=25,ancho*2   ,20);
			
		//frecuencia
			panel.add(new JLabel("Frecuencia")).setBounds(x-40, y+=25, ancho, 20);
			panel.add(new JSeparator()).setBounds     (x+ancho-50, y+11, ancho*7-10, 20);
			
			panel.add(new JLabel("Sucede: ")).setBounds(x, y+=25, ancho, 20);
			panel.add(cmbSucede).setBounds             (x+90, y, ancho, 20);
			panel.add(rbDiaDelMes).setBounds   (x, y+=25, 60, 20);
			
			panel.add(lblSeRepiteCada).setBounds(x, y, ancho+30, 20);
			panel.add(spDiasARepetir).setBounds(x+90, y, ancho, 20);
			panel.add(lblDias_Semana).setBounds(x+ancho*2+20, y, ancho, 20);
			
			panel.add(spMeses).setBounds(x+ancho*3+20, y, ancho, 20);
			panel.add(lblMeses).setBounds(x+ancho*4+40, y, ancho, 20);
			
//			semana------
			panel.add(chbLunes).setBounds       (x+20  ,y+=25,ancho ,20);
			panel.add(chbMartes).setBounds      (x+110 ,y    ,ancho ,20);
			panel.add(chbMiercoles).setBounds   (x+210 ,y    ,ancho ,20);
			panel.add(chbJueves).setBounds      (x+310 ,y    ,ancho ,20);
			panel.add(chbViernes).setBounds     (x+410 ,y    ,ancho ,20);
			panel.add(chbSabado).setBounds      (x+510 ,y    ,ancho ,20);
			panel.add(chbDomingo).setBounds     (x+510 ,y+=25,ancho ,20);
			
//			Duracion
			panel.add(new JLabel("Duracion")).setBounds         (x-40, y+=25, ancho, 20);
			panel.add(new JSeparator()).setBounds               (x+ancho-60, y+11, ancho*7, 20);
			panel.add(new JLabel("Fecha De Inicio: ")).setBounds(x, y+=25, ancho, 20);
			panel.add(fh_inicial_de_duracion).setBounds         (x+90, y, ancho+40, 20);
			panel.add(rbFechaDeFinalizacion).setBounds          (x+220, y, ancho+60, 20);
			panel.add(fh_final_de_duracion).setBounds           (x+360, y, ancho+40, 20);
			panel.add(rbSinFechaDeFinalizacion).setBounds       (x+220, y+=25, ancho*2, 20);
			
//			resumen
			panel.add(new JLabel("Resumen")).setBounds(x-40, y+=25, ancho, 20);
			panel.add(new JSeparator()).setBounds(x+ancho-60, y+11, ancho*7, 20);
			panel.add(scrollDescripcion).setBounds(x, y+=25, ancho*7+20, 100);

			ancho=100;
			panel.add(btnDeshacerFrecuencia).setBounds          (x    , y+=115, ancho+30, 20);
			panel.add(btnAceptar).setBounds                     (x+480, y , ancho, 20);
			
			leerObjeto();
			programacion();
//			tiempodefault();
			txaDescripcion.setText(	DescripcionDeConfiguaracionDeFrecuencia());
			
			cmbTipoDeProgramacion.addActionListener(opProgramacion);
			
			rbHastaQueSeCumpla.addActionListener(opRbTipoPRogramacion);
			rbEnLaFechaIndicada.addActionListener(opRbTipoPRogramacion);
			
			chbConHora.addActionListener(opConHora);
			spHoraUnicaRepeticion.addChangeListener(opDescripcion);
			
			rbDiaDeLaSemana.addActionListener(opRbFrecuencia);
			rbDiaDelMes.addActionListener(opRbFrecuencia);
			
			cmbSucede.addActionListener(opFrecuenciaSuceso);
			
			rbFechaDeFinalizacion.addActionListener(opRbDuracion);
			rbSinFechaDeFinalizacion.addActionListener(opRbDuracion);
			
			chAsignarHora.addActionListener(opFrecuenciaDiaria);
			
			spDiasARepetir.addChangeListener(opDescripcion);
			spHoraFrecuenciaDiaria.addChangeListener(opDescripcion);
			spMeses.addChangeListener(opDescripcion);
			spMeses2.addChangeListener(opDescripcion);
			
			fh_inicial_de_duracion.addPropertyChangeListener(opDescripcion2);
			fh_final_de_duracion.addPropertyChangeListener(opDescripcion2);
			
			chbDomingo.addActionListener(opDescripcion3);
			chbLunes.addActionListener(opDescripcion3);
			chbMartes.addActionListener(opDescripcion3);
			chbMiercoles.addActionListener(opDescripcion3);
			chbJueves.addActionListener(opDescripcion3);
			chbViernes.addActionListener(opDescripcion3);
			chbSabado.addActionListener(opDescripcion3);
			
			cmbNivelDeDias.addActionListener(opDescripcion3);
			cmbDiaDeLaSemana.addActionListener(opDescripcion3);

			btnAceptar.addActionListener(opGuardar);
			btnDeshacerFrecuencia.addActionListener(opDeshacerFrecuencia);
			cont.add(panel);
		}
		
		ActionListener opGuardar = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				llenarObjeto();
				
			}
		};
		
		ActionListener opDeshacerFrecuencia = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cmbTipoDeProgramacion.setSelectedItem("UNA VEZ");
				leerObjeto();
			}
		};
		
		ActionListener opProgramacion = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				programacion();
				txaDescripcion.setText(	DescripcionDeConfiguaracionDeFrecuencia());
			}
		};
		
		ActionListener opRbFrecuencia = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				diasDeFrecuenciaPorMes();
				txaDescripcion.setText(	DescripcionDeConfiguaracionDeFrecuencia());
			}
		};
		
		ActionListener opFrecuenciaSuceso = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frecuenciaSucede();
				txaDescripcion.setText(	DescripcionDeConfiguaracionDeFrecuencia());
			}
		};
		
		ActionListener opRbDuracion = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fechaDuracion();
				txaDescripcion.setText(	DescripcionDeConfiguaracionDeFrecuencia());
			}
		};
		
		ActionListener opFrecuenciaDiaria = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frecuenciaDiaria();
				txaDescripcion.setText(	DescripcionDeConfiguaracionDeFrecuencia());
			}
		};
		
		ActionListener opRbTipoPRogramacion = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rbTipoPRogramacion();
				txaDescripcion.setText(	DescripcionDeConfiguaracionDeFrecuencia());
			}
		};
		
		ActionListener opConHora = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 conHora();
				 txaDescripcion.setText(	DescripcionDeConfiguaracionDeFrecuencia());
			}
		};
		
		ActionListener opDescripcion3 = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 txaDescripcion.setText(	DescripcionDeConfiguaracionDeFrecuencia());
			}
		};
		
		ChangeListener opDescripcion = new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				txaDescripcion.setText(	DescripcionDeConfiguaracionDeFrecuencia());
			}
		};
		PropertyChangeListener opDescripcion2 = new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				txaDescripcion.setText(	DescripcionDeConfiguaracionDeFrecuencia());
			}
		};
		
		public void programacion(){
			if(cmbTipoDeProgramacion.getSelectedItem().toString().equals("UNA VEZ")){
				  
				  rbHastaQueSeCumpla.setEnabled(true);
				  rbEnLaFechaIndicada.setEnabled(true);
				  fh_unica_repeticion.setEnabled(true);
				  chbConHora.setEnabled(true);
				  spHoraUnicaRepeticion.setEnabled(true);
				  
				  cmbSucede.setSelectedIndex(0);
				  cmbSucede					.setEnabled(false);	
				  rbDiaDelMes					.setEnabled(false);
				  spDiasARepetir				.setEnabled(false);
				  spMeses						.setEnabled(false);
				  rbDiaDeLaSemana				.setEnabled(false);
				  cmbNivelDeDias				.setEnabled(false);
				  cmbDiaDeLaSemana			.setEnabled(false);	
				  spMeses2					.setEnabled(false);	
				  chbLunes					.setEnabled(false);	
				  chbMartes					.setEnabled(false);	
				  chbMiercoles				.setEnabled(false);	
				  chbJueves					.setEnabled(false);	
				  chbViernes					.setEnabled(false);
				  chbSabado					.setEnabled(false);	
				  chbDomingo					.setEnabled(false);
				  chAsignarHora				.setEnabled(false);	
				  spHoraFrecuenciaDiaria		.setEnabled(false);
				  fh_inicial_de_duracion		.setEnabled(false);
				  rbFechaDeFinalizacion		.setEnabled(false);	
				  fh_final_de_duracion		.setEnabled(false);	
				  rbSinFechaDeFinalizacion	.setEnabled(false);
					
				  conHora();
				  frecuenciaSucede();
				  
			  }else{
			    rbHastaQueSeCumpla.setEnabled(false);
			    rbEnLaFechaIndicada.setEnabled(false);
			    fh_unica_repeticion.setEnabled(false);
			    chbConHora.setEnabled(false);
			    spHoraUnicaRepeticion.setEnabled(false);
				  
				cmbSucede					.setEnabled(true);	
				rbDiaDelMes					.setEnabled(true);
				spDiasARepetir				.setEnabled(true);
				spMeses						.setEnabled(true);
				rbDiaDeLaSemana				.setEnabled(true);
				cmbNivelDeDias				.setEnabled(true);
				cmbDiaDeLaSemana			.setEnabled(true);	
				spMeses2					.setEnabled(true);	
				chbLunes					.setEnabled(true);	
				chbMartes					.setEnabled(true);	
				chbMiercoles				.setEnabled(true);	
				chbJueves					.setEnabled(true);	
				chbViernes					.setEnabled(true);
				chbSabado					.setEnabled(true);	
				chbDomingo					.setEnabled(true);
				chAsignarHora				.setEnabled(true);	
				spHoraFrecuenciaDiaria		.setEnabled(true);
				fh_inicial_de_duracion		.setEnabled(true);
				rbFechaDeFinalizacion		.setEnabled(true);	
				fh_final_de_duracion		.setEnabled(true);	
				rbSinFechaDeFinalizacion	.setEnabled(true);
				  
					rbTipoPRogramacion();
					conHora();
					frecuenciaSucede();
					frecuenciaDiaria();
					fechaDuracion();
			  }
		}
		
		public void frecuenciaSucede(){
			switch(cmbSucede.getSelectedItem().toString()){
				case "DIARIA":
					
					lblSeRepiteCada.setVisible(true);
					spDiasARepetir.setVisible(true);
				    lblDias_Semana.setVisible(true);
				    
					spMeses.setVisible(false);
					lblMeses.setVisible(false);
					
					chbLunes.setVisible(false);
					chbMartes.setVisible(false);
					chbMiercoles.setVisible(false);
					chbJueves.setVisible(false);
					chbViernes.setVisible(false);
					chbSabado.setVisible(false);
					chbDomingo.setVisible(false); 
					
					rbDiaDelMes.setVisible(false);
					rbDiaDeLaSemana.setVisible(false);
					
					cmbNivelDeDias.setVisible(false);
					cmbDiaDeLaSemana.setVisible(false);
					lblDeCada.setVisible(false);
					spMeses2.setVisible(false);
					lblMeses2.setVisible(false);
					
					lblDias_Semana.setText("Dia(s)");
					
				break;
				case "SEMANAL":
					
					lblSeRepiteCada.setVisible(true);
					spDiasARepetir.setVisible(true);
				    lblDias_Semana.setVisible(true);
							
					spMeses.setVisible(false);
					lblMeses.setVisible(false);
					
					chbLunes.setVisible(true);
					chbMartes.setVisible(true);
					chbMiercoles.setVisible(true);
					chbJueves.setVisible(true);
					chbViernes.setVisible(true);
					chbSabado.setVisible(true);
					chbDomingo.setVisible(true); 
					
					rbDiaDelMes.setVisible(false);
					rbDiaDeLaSemana.setVisible(false);
					
					cmbNivelDeDias.setVisible(false);
					cmbDiaDeLaSemana.setVisible(false);
					lblDeCada.setVisible(false);
					spMeses2.setVisible(false);
					lblMeses2.setVisible(false);
					
					lblDias_Semana.setText("Semana(s), el");
					
				break;
				case "MENSUAL":
					
					lblSeRepiteCada.setVisible(false);
					spDiasARepetir.setVisible(true);
				    lblDias_Semana.setVisible(true);
					
					spMeses.setVisible(true);
					lblMeses.setVisible(true);
					
					chbLunes.setVisible(false);
					chbMartes.setVisible(false);
					chbMiercoles.setVisible(false);
					chbJueves.setVisible(false);
					chbViernes.setVisible(false);
					chbSabado.setVisible(false);
					chbDomingo.setVisible(false); 
					
					rbDiaDelMes.setVisible(true);
					rbDiaDeLaSemana.setVisible(true);
					
					cmbNivelDeDias.setVisible(true);
					cmbDiaDeLaSemana.setVisible(true);
					lblDeCada.setVisible(true);
					spMeses2.setVisible(true);
					lblMeses2.setVisible(true);
					
					lblDias_Semana.setText("De Cada");
					
				break;
			}
			rbDiaDelMes.doClick();
		}
		
		public void diasDeFrecuenciaPorMes(){
			
			if(rbDiaDelMes.isSelected()){
				spDiasARepetir.setEnabled(true);
				spMeses.setEnabled(true);
				cmbNivelDeDias.setEnabled(false);
				cmbDiaDeLaSemana.setEnabled(false);
				spMeses2.setEnabled(false);
			}else{
				spDiasARepetir.setEnabled(false);
				spMeses.setEnabled(false);
				cmbNivelDeDias.setEnabled(true);
				cmbDiaDeLaSemana.setEnabled(true);
				spMeses2.setEnabled(true);
			}
		}
		
		public void fechaDuracion(){
			if(rbFechaDeFinalizacion.isSelected()){
				fh_final_de_duracion.setEnabled(true);
				try {
					fh_final_de_duracion.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(frecuencia.cargar_fechas(-30)));
				} catch (ParseException e) {
					e.printStackTrace();	}
			}else{
				fh_final_de_duracion.setEnabled(false);
				try {
					fh_final_de_duracion.setDate(new SimpleDateFormat("dd/MM/yyyy").parse("16/11/2094"));
				} catch (ParseException e) {
					e.printStackTrace();	}
			}
		}
		
		public void frecuenciaDiaria(){
			if(chAsignarHora.isSelected()){
				spHoraFrecuenciaDiaria.setEnabled(true);
			}else{
				spHoraFrecuenciaDiaria.setEnabled(false);
			}
		}
		
		public void rbTipoPRogramacion(){
			if(rbHastaQueSeCumpla.isSelected()){
				lblUnicarepeticion.setText("Desde");
			}else{
				lblUnicarepeticion.setText("Sucede El Dia");
			}
		}
		
		public void conHora(){
			if(chbConHora.isSelected()){
				spHoraUnicaRepeticion.setEnabled(true);
			}else{
				spHoraUnicaRepeticion.setEnabled(false);
			}
		}
		
		public String DescripcionDeConfiguaracionDeFrecuencia(){
			String cadena = "";
			if(cmbTipoDeProgramacion.getSelectedItem().equals("UNA VEZ")){
				cadena += rbHastaQueSeCumpla.isSelected()?
						("Sucede Desde El "+(new SimpleDateFormat("dd/MM/yyyy").format(fh_unica_repeticion.getDate()))+" A Las "+(chbConHora.isSelected()?
																																			(new SimpleDateFormat("HH:mm:ss").format(spHoraUnicaRepeticion.getValue())):
																																			"00:00:00") +" Hasta Que Se Cumpla."):
							("Sucede El "+(new SimpleDateFormat("dd/MM/yyyy").format(fh_unica_repeticion.getDate()))+" Hasta Antes De Las "+(chbConHora.isSelected()?
																																						(new SimpleDateFormat("HH:mm:ss").format(spHoraUnicaRepeticion.getValue())):
																																						"23:59:00.") );
			}else{
				
				if(cmbSucede.getSelectedItem().toString().equals("DIARIA")){
					cadena += "Sucede Cada "+(Integer.valueOf(spDiasARepetir.getValue().toString())==1?
							"Dia":
							spDiasARepetir.getValue()+" Dias")+(chAsignarHora.isSelected()?" A Las "+(new SimpleDateFormat("HH:mm:ss").format(spHoraFrecuenciaDiaria.getValue()))+".":" Sin Importar Hora.");
				}
				if(cmbSucede.getSelectedItem().toString().equals("SEMANAL")){
					String dias = "";
					if(chbDomingo.isSelected()){dias+=" Domingo,";};
					if(chbLunes.isSelected()){dias+=" Lunes,";};
					if(chbMartes.isSelected()){dias+=" Martes,";};
					if(chbMiercoles.isSelected()){dias+=" Miercoles,";};
					if(chbJueves.isSelected()){dias+=" Jueves,";};
					if(chbViernes.isSelected()){dias+=" Viernes,";};
					if(chbSabado.isSelected()){dias+=" Sabado,";};
					
					cadena += "Sucede Cada "+(Integer.valueOf(spDiasARepetir.getValue().toString())==1?
							"Semana":
							spDiasARepetir.getValue()+" Semanas")+(dias.equals("")?"":" Los Dias "+dias.substring(0, dias.length()-1))+(chAsignarHora.isSelected()?" A Las "+(new SimpleDateFormat("HH:mm:ss").format(spHoraFrecuenciaDiaria.getValue()))+".":" Sin Importar Hora.");
				}
				if(cmbSucede.getSelectedItem().toString().equals("MENSUAL")){
					
					cadena +=rbDiaDelMes.isSelected()?(
							"Sucede El Dia "+(spDiasARepetir.getValue().toString())+
												(Integer.valueOf(spMeses.getValue().toString())==1 ? " De Cada Mes"	: " Cada "+spMeses.getValue()+" Meses")+
																																		(chAsignarHora.isSelected() ? " A Las "+(new SimpleDateFormat("HH:mm:ss").format(spHoraFrecuenciaDiaria.getValue()))+"." : ".")
							):
					"Sucede Cada "+(cmbNivelDeDias.getSelectedItem())+" "+(cmbDiaDeLaSemana.getSelectedItem())+
																		( Integer.valueOf(spMeses2.getValue().toString())==1 ? " De Cada Mes"+( chAsignarHora.isSelected() ? " A Las "+(new SimpleDateFormat("HH:mm:ss").format(spHoraFrecuenciaDiaria.getValue())+".") : ".")
																															: " De Cada "+spMeses2.getValue()+" Meses"+(chAsignarHora.isSelected() ? " A Las "+(new SimpleDateFormat("HH:mm:ss").format(spHoraFrecuenciaDiaria.getValue())+".") : ".")
							);
				}
				
				cadena += " La frecuencia De La Actividad Inicia A Partir Del Dia "+(new SimpleDateFormat("dd/MM/yyyy").format(fh_inicial_de_duracion.getDate()))+(rbFechaDeFinalizacion.isSelected()?
																																										" Y Termina El Dia "+(new SimpleDateFormat("dd/MM/yyyy").format(fh_final_de_duracion.getDate()))+".":
																																										/*" Hasta Que Se Cumpla."*/".");
			}
			return cadena;
		}

		Date fechaInicialDefault = null;
		public void llenarObjeto(){
			
			String llenar_objeto = "no";
			if(cmbTipoDeProgramacion.getSelectedItem().toString().equals("UNA VEZ")){
				if(fh_unica_repeticion.getDate().before(fechaInicialDefault)){
					llenar_objeto = "no";
				}else{
					llenar_objeto = "si";
				}
			}else{
				if(fh_inicial_de_duracion.getDate().before(fechaInicialDefault)){
					llenar_objeto = "no";
				}else{
					
					if(rbFechaDeFinalizacion.isSelected()==true){
						
						if(fh_final_de_duracion.getDate().before(fh_inicial_de_duracion.getDate())){
							llenar_objeto = "no(fechas invertidas)";
						}else{
							llenar_objeto = "si";
						}
					}else{
						llenar_objeto = "si";
					}
					
				}
			}
			if(cmbTipoDeProgramacion.getSelectedItem().toString().equals("PERIODICA")){
				 if(cmbSucede.getSelectedItem().toString().equals("SEMANAL")){	
			        if(((chbLunes.isSelected()==true?1:0)+(chbMartes.isSelected()==true?1:0)+(chbMiercoles.isSelected()==true?1:0)+(chbJueves.isSelected()==true?1:0)+(chbViernes.isSelected()==true?1:0)+(chbSabado.isSelected()==true?1:0)+(chbDomingo.isSelected()==true?1:0) )>0?false:true ){
				       JOptionPane.showMessageDialog(null, "Si Selecciona Un Periodo Semanal Es Necesario \n Que Seleccione Un Dia De La Semana Antes De Aplicar", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
					  return;
			       }
			    }
			}
			
			if(llenar_objeto.equals("no(fechas invertidas)")){
				JOptionPane.showMessageDialog(null, "No Se Puede Guardar La Configuración Con Las Fecha De Duracion Invertidas"
                        , "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
					return;
			}
			if(llenar_objeto.equals("no")){
				JOptionPane.showMessageDialog(null, "No Se Puede Guardar La Configuración Con La Fecha "+(cmbTipoDeProgramacion.getSelectedItem().toString().equals("UNA VEZ")?"De Unica Repetición  Menor Al Dia Actual":"Inicial De Duracion  Menor Al Dia Actual")+" Menor Al Dia Actual"
                        , "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
					return;
			}else{
			
					frecuencia.setTipo_de_frecuencia(cmbTipoDeProgramacion.getSelectedItem().toString());
					frecuencia.setSeleccion_hasta_que_se_cumpla(rbHastaQueSeCumpla.isSelected());
					frecuencia.setSeleccion_en_la_fecha_indicada(rbEnLaFechaIndicada.isSelected());
					
//				unica repeticion
					frecuencia.setFh_unica_repeticion(new SimpleDateFormat("dd/MM/yyyy").format(fh_unica_repeticion.getDate()));
					frecuencia.setSeleccion_con_hora(chbConHora.isSelected());
					frecuencia.setHora_unica_repeticion(new SimpleDateFormat("HH:mm:ss").format(spHoraUnicaRepeticion.getValue()));
					
//				frecuencia
					frecuencia.setSucede(cmbSucede.getSelectedItem().toString());
					frecuencia.setSelecciona_dia_del_mes(rbDiaDelMes.isSelected());
					frecuencia.setSe_repite_cada(Integer.valueOf(spDiasARepetir.getValue().toString()) );
					frecuencia.setMes1(Integer.valueOf(spMeses.getValue().toString()));
					frecuencia.setSelecciona_dia_de_la_semana(rbDiaDeLaSemana.isSelected());
					frecuencia.setNivel_de_dias(cmbNivelDeDias.getSelectedItem().toString());
					frecuencia.setDia_de_la_semana(Integer.valueOf(cmbDiaDeLaSemana.getSelectedIndex()));
					frecuencia.setMes2(Integer.valueOf(spMeses2.getValue().toString()));
		       
   //			semana
					frecuencia.setDomingo(chbDomingo.isSelected());
					frecuencia.setLunes(chbLunes.isSelected());
					frecuencia.setMartes(chbMartes.isSelected());
					frecuencia.setMiercoles(chbMiercoles.isSelected());
					frecuencia.setJueves(chbJueves.isSelected());
					frecuencia.setViernes(chbViernes.isSelected());
					frecuencia.setSabado(chbSabado.isSelected());
					 
   //			frecuencia diaria
					frecuencia.setSeleccion_asignar_hora(chAsignarHora.isSelected());
					frecuencia.setHora_frecuencia_diaria(new SimpleDateFormat("HH:mm:ss").format(spHoraFrecuenciaDiaria.getValue()));
					
   //			Duracion
					frecuencia.setFecha_inicio_duracion(new SimpleDateFormat("dd/MM/yyyy").format(fh_inicial_de_duracion.getDate()));
					frecuencia.setSeleccion_fecha_finaliza(rbFechaDeFinalizacion.isSelected());
					frecuencia.setFecha_final_duracion(rbFechaDeFinalizacion.isSelected()?(new SimpleDateFormat("dd/MM/yyyy").format(fh_final_de_duracion.getDate())):"16/11/2094");
					frecuencia.setSeleccion_sin_fecha_final(rbSinFechaDeFinalizacion.isSelected());
					
					if(rbHastaQueSeCumpla.isSelected()&&cmbTipoDeProgramacion.getSelectedItem().toString().equals("UNA VEZ")){
						frecuencia.setFecha_inicio_duracion(new SimpleDateFormat("dd/MM/yyyy").format(fh_unica_repeticion.getDate()));
					}
					
					
					dispose();
			}
		}
		
		@SuppressWarnings("deprecation")
		public void leerObjeto(){
			cmbTipoDeProgramacion.setSelectedItem(frecuencia.getTipo_de_frecuencia().toString());
			rbHastaQueSeCumpla.setSelected(frecuencia.isSeleccion_hasta_que_se_cumpla());
			rbEnLaFechaIndicada.setSelected(frecuencia.isSeleccion_en_la_fecha_indicada());
//			unica repeticion
			try {
				fechaInicialDefault = new SimpleDateFormat("dd/MM/yyyy").parse(frecuencia.cargar_fechas(0));
				fh_unica_repeticion.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(frecuencia.getFh_unica_repeticion()));
			} catch (ParseException e) {// Auto-generated catch block
				e.printStackTrace();	}
			
			chbConHora.setSelected(frecuencia.isSeleccion_con_hora());

			String[] horaunicaRepeticion = frecuencia.getHora_unica_repeticion().split(":");
			spHoraUnicaRepeticion.setValue(new Time(Integer.parseInt(horaunicaRepeticion[0]),Integer.parseInt(horaunicaRepeticion[1]),Integer.parseInt(horaunicaRepeticion[2])));
			spHoraUnicaRepeticion.setEditor(spDHoraUnicaRepeticion);
			
//			frecuencia
			cmbSucede.setSelectedItem(frecuencia.getSucede().toString());
			rbDiaDelMes.setSelected(frecuencia.isSelecciona_dia_del_mes());
			spDiasARepetir.setValue(frecuencia.getDias_a_repetir_por_suceso_de_dias()+frecuencia.getDias_a_repetir_por_suceso_de_semanas()+frecuencia.getDias_a_repetir_por_suceso_de_meses());
			spMeses.setValue(frecuencia.getMes1());
			
			rbDiaDeLaSemana.setSelected(frecuencia.isSelecciona_dia_de_la_semana());
			cmbNivelDeDias.setSelectedItem(frecuencia.getNivel_de_dias().toString());
			cmbDiaDeLaSemana.setSelectedIndex(Integer.valueOf(frecuencia.getDia_de_la_semana().toString()));
			spMeses2.setValue(frecuencia.getMes2());
			
//			semana------
			chbDomingo.setSelected(frecuencia.isDomingo());
			chbLunes.setSelected(frecuencia.isLunes());
			chbMartes.setSelected(frecuencia.isMartes());
			chbMiercoles.setSelected(frecuencia.isMiercoles());
			chbJueves.setSelected(frecuencia.isJueves());
			chbViernes.setSelected(frecuencia.isViernes());
			chbSabado.setSelected(frecuencia.isSabado());
			
//			frecuencia diaria
			chAsignarHora.setSelected(frecuencia.isSeleccion_asignar_hora());
			
			String[] horafrecuencia_diaria = frecuencia.getHora_frecuencia_diaria().split(":");
			spHoraFrecuenciaDiaria.setValue(new Time(Integer.parseInt(horafrecuencia_diaria[0]),Integer.parseInt(horafrecuencia_diaria[1]),Integer.parseInt(horafrecuencia_diaria[2])));
			spHoraFrecuenciaDiaria.setEditor(spDHoraFrecuenciaDiaria);
			
//			Duracion
			try {
				fh_inicial_de_duracion.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(frecuencia.getFecha_inicio_duracion()));
			} catch (ParseException e) {
				e.printStackTrace();	}
			rbFechaDeFinalizacion.setSelected(frecuencia.isSeleccion_fecha_finaliza());
			
			try {
				fh_final_de_duracion.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(frecuencia.getFecha_final_duracion()));
			} catch (ParseException e) {
				e.printStackTrace();	}
			
			
			rbSinFechaDeFinalizacion.setSelected(frecuencia.isSeleccion_sin_fecha_final());
		}
		
	}

	public static void main(String[] args) {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Asignacion_De_Actividades_A_Cuadrantes_Por_Nivel_Jerarquico().setVisible(true);
		}catch(Exception e){	}	
	}
}
