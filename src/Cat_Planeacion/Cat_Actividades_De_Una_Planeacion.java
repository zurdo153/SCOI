package Cat_Planeacion;

import java.awt.Container;
import java.awt.Event;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Vector;

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
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import com.toedter.calendar.JDateChooser;

import Conexiones_SQL.Connexion;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Planeacion.Obj_Actividades_De_Una_Planeacion;
import Obj_Planeacion.Obj_Frecuencia_De_Actividades;
import Obj_Planeacion.Obj_Opciones_De_Respuesta;
import Obj_Planeacion.Obj_Prioridad_Y_Ponderacion;
import Obj_Planeacion.Obj_Seleccion_De_Usuarios;
import Obj_Principal.Componentes;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_Filtro_Dinamico_Plus;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Actividades_De_Una_Planeacion extends JFrame{
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	JTextArea txa_Resultado_Configuracion = new Componentes().textArea(new JTextArea(), "Detalle De La Actividad", 250);
	JScrollPane JPActividad = new JScrollPane(txa_Resultado_Configuracion);
	
	JButton btnAprovar = new JButton("Aplicar",new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
	JButton btnDeshacer = new JButton("Deshacer",new ImageIcon("imagen/deshacer-icono-4321-32.png"));
	
	JButton btnOpcionesRespuesta = new JButton("Opciones De Respuesta",new ImageIcon("imagen/ver-info-boton-icono-8754-32.png"));
	JButton btnPrioridad         = new JButton("Prioridad y Ponderación",new ImageIcon("imagen/favoritos-ver-boton-icono-8318-32.png"));
	JButton btnFrecuencia        = new JButton("Programación,Frecuencia",new ImageIcon("imagen/tiempo-de-botones-icono-4873-32.png"));
	JButton btnUsuarios          = new JButton("Asignación De Usuarios",new ImageIcon("imagen/ayudar-a-ver-el-boton-icono-4900-32.png"));
	
	Obj_Usuario usuario = new Obj_Usuario().LeerSession();
	Obj_Opciones_De_Respuesta OpRespuesta= new Obj_Opciones_De_Respuesta();
	Obj_Prioridad_Y_Ponderacion OpPonderacion= new Obj_Prioridad_Y_Ponderacion();
	Obj_Seleccion_De_Usuarios usuarios= new Obj_Seleccion_De_Usuarios();
	Obj_Frecuencia_De_Actividades frecuencia = new Obj_Frecuencia_De_Actividades();
	
	public Cat_Actividades_De_Una_Planeacion(){
		this.setSize(610, 280);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/reinicio-pelota-cute-icono-7443-64.png"));
		this.setTitle("Actividades De Una Planeacion");
		this.panel.setBorder(BorderFactory.createTitledBorder("Selecciona Los Datos Deseados De Respuesta"));
		
		int x=15,y=20,width=150,height=20;
		
		panel.add(new JLabel("Detalle De La Actividad:")).setBounds                (x    ,y     ,width*3 ,height     );
		panel.add(JPActividad).setBounds                                           (x    ,y+=20 ,355     ,height*6+10);
		panel.add(btnDeshacer).setBounds                                           (x    ,y+=135,width   ,height*2   );
		panel.add(btnAprovar).setBounds                                            (x+205,y     ,width   ,height*2   );

		x=400;y=40;height=40; 
		panel.add(btnOpcionesRespuesta).setBounds                                  (x    ,y     ,width+40,height     );
		panel.add(btnPrioridad).setBounds                                          (x    ,y+=45 ,width+40,height     );
		panel.add(btnUsuarios).setBounds                                           (x    ,y+=45 ,width+40,height     );
		panel.add(btnFrecuencia).setBounds                                         (x    ,y+=45 ,width+40,height     );
		
		txa_Resultado_Configuracion.setLineWrap(true); 
		txa_Resultado_Configuracion.setWrapStyleWord(true);
		
		btnAprovar.addActionListener(opAprovar);
		btnDeshacer.addActionListener(deshacer);
        btnOpcionesRespuesta.addActionListener(CatOpciones_Repuesta);
        btnPrioridad.addActionListener(CatOpciones_PrioridadyPonderacion);
        btnUsuarios.addActionListener(CatUsuarios);
        btnFrecuencia.addActionListener(CatFrecuencia);
        
		///guardar con control+A
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A,Event.CTRL_MASK),"guardar");
             getRootPane().getActionMap().put("guardar", new AbstractAction(){
                 public void actionPerformed(ActionEvent e)
                 {                 	    btnAprovar.doClick();           	    }
            });
 	     //deshacer con escape
 	                 getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
 	                   getRootPane().getActionMap().put("escape", new AbstractAction(){
 	                  public void actionPerformed(ActionEvent e)
 	                  {                btnDeshacer.doClick();           	    }
 	              });
 	    this.addWindowListener(new WindowAdapter() {
 	                     public void windowOpened( WindowEvent e ){
 	                    	 txa_Resultado_Configuracion.requestFocus();
 	                  }
 	             });
 	    cont.add(panel);
	}
	
	ActionListener deshacer = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			txa_Resultado_Configuracion.setText("");
			//falta reiniciar_los_objetos
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
			if(txa_Resultado_Configuracion.getText().toString().equals("")){
				JOptionPane.showMessageDialog(null, "Necesitas Escribir El Detalle De La Actividad", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}else{
				if((usuarios.getUsuarios_nombres()==null)){
					String[][] matriz =new String[1][2];
					           matriz[0][0]= String.valueOf(usuario.getFolio());
					           matriz[0][1]= usuario.getNombre_completo();
					usuarios.setUsuarios_nombres(matriz);
				}
				
				Obj_Actividades_De_Una_Planeacion Actividad_plan = new Obj_Actividades_De_Una_Planeacion();
				Actividad_plan.setDescripcion_de_la_actividad(txa_Resultado_Configuracion.getText().toString().trim());
				if(Actividad_plan.guardar(OpRespuesta,OpPonderacion,usuarios,frecuencia, usuario.getFolio())){
					dispose();
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
		
		JTextArea txa_Resultado_Configuracion = new Componentes().textArea(new JTextArea(), "Concepto", 135);
		JScrollPane Concepto = new JScrollPane(txa_Resultado_Configuracion);
		
		JLabel lblLineaContestacion      = new JLabel("");
		JRadioButton rbResuelto		     = new JRadioButton("Resuelta");
		JRadioButton rbNo 		         = new JRadioButton("Incumplida");
		JRadioButton rbPendiente         = new JRadioButton("Pendiente");
		JRadioButton rbEnProceso         = new JRadioButton("En Proceso");
		JRadioButton rbPasoAOtroDep      = new JRadioButton("Paso A Otro Departamento");
		
		JLabel lblLineaEvidencia   	     = new JLabel("");
		JRadioButton rbSinEvidencia      = new JRadioButton("No Exige Evidencia");
		JRadioButton rbExigeEvidencia    = new JRadioButton("Exige Evidencia");
		ButtonGroup GrupoExigeEvidencia  = new ButtonGroup();
		
		JLabel lblLineaObservacion   	 = new JLabel("");
		JRadioButton rbSinObservacion    = new JRadioButton("No Exige Observacion");
		JRadioButton rbExigeObservacion  = new JRadioButton("Exige Observacion");
		ButtonGroup GrupoExigeObservacion= new ButtonGroup();
		
		JButton btnAprovar = new JButton("Aplicar",new ImageIcon("imagen/Aplicar.png"));
		JButton btnDeshacer = new JButton("Deshacer",new ImageIcon("imagen/deshacer16.png"));
		Border linea;
		
		public Cat_Opciones_De_Respuesta_De_La_Actividad(){
			this.setSize(380, 437);
			this.setLocationRelativeTo(null);
			this.setResizable(false);
			this.setModal(true);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/ver-info-boton-icono-8754-64.png"));
			this.setTitle("Configuracion De La Respuesta");
			this.panel.setBorder(BorderFactory.createTitledBorder("Teclea El Detalle De La Actividad y Selecciona La configuracion Deseada"));
			this.linea = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
			this.lblLineaContestacion.setBorder(BorderFactory.createTitledBorder(linea,"Opciones Para Contestación"));
			this.lblLineaEvidencia.setBorder(BorderFactory.createTitledBorder(linea,"Opciones Para Evidencia"));
			this.lblLineaObservacion.setBorder(BorderFactory.createTitledBorder(linea,"Opciones Para Evidencia"));
			int x=15,y=30,width=150,height=20,i=18;
			
			panel.add(lblLineaContestacion).setBounds       (x-5 ,y-10 ,width+22, 110);
			panel.add(rbResuelto).setBounds                 (x   ,y+=5 ,width   , height);
			panel.add(rbNo).setBounds                       (x   ,y+=i ,width   , height);
			panel.add(rbPendiente).setBounds                (x   ,y+=i ,width   , height);
			panel.add(rbEnProceso).setBounds                (x   ,y+=i ,width   , height);
			panel.add(rbPasoAOtroDep).setBounds             (x   ,y+=i ,width+10, height);
			
			x=200; y=30;
			panel.add(lblLineaEvidencia).setBounds          (x-5 ,y-10 ,width+20, 57);
			panel.add(rbSinEvidencia).setBounds             (x   ,y+=5 ,width   , height);
			panel.add(rbExigeEvidencia).setBounds           (x   ,y+=i ,width   , height);
			GrupoExigeEvidencia.add(rbSinEvidencia);
			GrupoExigeEvidencia.add(rbExigeEvidencia);
			
			x=200; y=85;
			panel.add(lblLineaObservacion).setBounds        (x-5 ,y-10 ,width+20, 57);
			panel.add(rbSinObservacion).setBounds           (x   ,y+=5 ,width   , height);
			panel.add(rbExigeObservacion).setBounds         (x   ,y+=i ,width   , height);
			GrupoExigeObservacion.add(rbSinObservacion);
			GrupoExigeObservacion.add(rbExigeObservacion);
			
			x=10;
			panel.add(new JLabel("     Detalle De La Configuracion Seleccionada:")).setBounds(x    ,y+=25 ,width*3 ,height);
			panel.add(Concepto  ).setBounds                                                  (x    ,y+=20 ,355     ,height*11+5);
			panel.add(btnDeshacer).setBounds                                                 (x    ,y+=230,100     ,height);
			panel.add(btnAprovar).setBounds                                                  (x+255,y     ,100     ,height);

			rbResuelto.setSelected(Boolean.valueOf(OpRespuesta.getResuelta()));
			rbNo.setSelected(Boolean.valueOf(OpRespuesta.getIncumplida()));
			rbPendiente.setSelected(Boolean.valueOf(OpRespuesta.getPendiente()));
			rbEnProceso.setSelected(Boolean.valueOf(OpRespuesta.getEnProceso()));
			rbPasoAOtroDep.setSelected(Boolean.valueOf(OpRespuesta.getPasoAOtroDepartamento()));
			rbSinEvidencia.setSelected(Boolean.valueOf(OpRespuesta.getNo_Exige_Evidencia()));
			rbSinObservacion.setSelected(Boolean.valueOf(OpRespuesta.getNoExigeObservacion()));
			rbExigeEvidencia.setSelected(Boolean.valueOf(OpRespuesta.getExige_Evidencia()));
			rbExigeObservacion.setSelected(Boolean.valueOf(OpRespuesta.getExigeObservacion()));
			
			rbResuelto.setEnabled(false);
			txa_Resultado_Configuracion.setEditable(false);
			txa_Resultado_Configuracion.setLineWrap(true); 
			txa_Resultado_Configuracion.setWrapStyleWord(true);
			
			btnAprovar.addActionListener(opAprovar_opcion_Respuesta);
			btnDeshacer.addActionListener(deshacer_opcion_respuesta);
			
			rbResuelto.addActionListener(Comentario);
			rbNo.addActionListener(Comentario);
			rbPendiente.addActionListener(Comentario);
			rbEnProceso.addActionListener(Comentario);
			rbPasoAOtroDep.addActionListener(Comentario);
			rbExigeEvidencia.addActionListener(Comentario);
			rbSinEvidencia.addActionListener(Comentario);
			rbExigeObservacion.addActionListener(Comentario);
			rbSinObservacion.addActionListener(Comentario);
			comentarios();

			///guardar con control+A
	        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A,Event.CTRL_MASK),"guardar");
	             getRootPane().getActionMap().put("guardar", new AbstractAction(){
	                 public void actionPerformed(ActionEvent e)
	                 {                 	    btnAprovar.doClick();           	    }
	            });
	 	     //deshacer con escape
	 	                 getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
	 	                   getRootPane().getActionMap().put("escape", new AbstractAction(){
	 	                  public void actionPerformed(ActionEvent e)
	 	                  {                btnDeshacer.doClick();           	    }
	 	              });
	 	    cont.add(panel);
		}
		
		ActionListener deshacer_opcion_respuesta = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				txa_Resultado_Configuracion.setText("");
				rbResuelto.setSelected(true);
				rbNo.setSelected(true);
				rbPendiente.setSelected(true);
				rbEnProceso.setSelected(true);
				rbPasoAOtroDep.setSelected(true);
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
				OpRespuesta.setEnProceso(String.valueOf(rbEnProceso.isSelected()));
				OpRespuesta.setPasoAOtroDepartamento(String.valueOf(rbPasoAOtroDep.isSelected()));
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
			  txa_Resultado_Configuracion.setText("");
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
		  if(Boolean.valueOf(rbEnProceso.isSelected())){
				 comentario=comentario+"\n   *En Proceso";
			   }else{
				   comentariofinal=comentariofinal+"\n   -En Proceso";
				   testigofinal = 1 ;
			}
		  if(Boolean.valueOf(rbPasoAOtroDep.isSelected())){
				 comentario=comentario+"\n   *Se Paso a Otro Departamento";
			   }else{
				   comentariofinal=comentariofinal+"\n   -Se Paso a Otro Departamento";
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
			txa_Resultado_Configuracion.setText(comentario+(testigofinal==1?comentariofinal:"")+"\n"+comentariofinaleo);
		}	
		
	}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////TODO Ventana De Prioridad y Ponderacion/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public class Cat_Prioridad_Y_Ponderacion_De_Una_Actividad extends JDialog{
		Container cont = getContentPane();
		JLayeredPane panel = new JLayeredPane();
		
		JTextArea txa_Resultado_Configuracion = new Componentes().textArea(new JTextArea(), "Observaciones De La Configuracion", 135);
		JScrollPane Concepto = new JScrollPane(txa_Resultado_Configuracion);
		
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
		
		JButton btnAprovar = new JButton("Aplicar",new ImageIcon("imagen/Aplicar.png"));
		JButton btnDeshacer = new JButton("Deshacer",new ImageIcon("imagen/deshacer16.png"));
		Border linea;
		
		int valor_de_seleccionado=0;

		
		public Cat_Prioridad_Y_Ponderacion_De_Una_Actividad(){
			this.setSize(380, 309);
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
			
			int x=15,y=30,width=80,height=20,i=18;
			
			this.panel.add(lblLineaPrioridad).setBounds                                           (x-5 ,y-10 ,width+10, 95    );
			this.panel.add(rbNormal).setBounds                                                    (x   ,y+=5 ,width   , height);
			this.panel.add(rbPreventivo).setBounds                                                (x   ,y+=i ,width   , height);
			this.panel.add(rbpUrgente).setBounds                                                  (x   ,y+=i ,width   , height);
			this.panel.add(rbpImportante).setBounds                                               (x   ,y+=i ,width   , height);
			
	        x=200;y=30;
	        this.panel.add(lblGrupPonderacion).setBounds                                          (x-5 ,y-10 ,width+10, 110   );
			this.panel.add(rb1).setBounds                                                         (x   ,y+=5 ,width   , height);
			this.panel.add(rb2).setBounds                                                         (x   ,y+=i ,width   , height);
			this.panel.add(rb3).setBounds                                                         (x   ,y+=i ,width   , height);
			this.panel.add(rb4).setBounds                                                         (x   ,y+=i ,width   , height);
			this.panel.add(rb5).setBounds                                                         (x   ,y+=i ,width   , height);
		        
			x=10; y=105;
			this.panel.add(new JLabel("     Detalle De La Configuracion Seleccionada:")).setBounds(x    ,y+=25 ,width*3 ,height);
			this.panel.add(Concepto  ).setBounds                                                  (x    ,y+=20 ,355     ,height*5);
			this.panel.add(btnDeshacer).setBounds                                                 (x    ,y+=105 ,100    ,height);
			this.panel.add(btnAprovar).setBounds                                                  (x+255,y     ,100     ,height);
			
			this.txa_Resultado_Configuracion.setEditable(false);
			this.txa_Resultado_Configuracion.setLineWrap(true); 
			this.txa_Resultado_Configuracion.setWrapStyleWord(true);

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
			
			///guardar con control+A
	        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A,Event.CTRL_MASK),"guardar");
	             getRootPane().getActionMap().put("guardar", new AbstractAction(){
	                 public void actionPerformed(ActionEvent e)
	                 {                 	    btnAprovar.doClick();           	    }
	            });
	 	     //deshacer con escape
	 	                 getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
	 	                   getRootPane().getActionMap().put("escape", new AbstractAction(){
	 	                  public void actionPerformed(ActionEvent e)
	 	                  {                btnDeshacer.doClick();           	    }
	 	              });
	 	    cont.add(panel);
		}
		
		ActionListener Comentario_prioridadyponderacion = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				comentarios();
			 }
		};
		
		public void comentarios(){
			  txa_Resultado_Configuracion.setText("");
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
			                    	                                                    
			txa_Resultado_Configuracion.setText(comentario+comentariofinal+ponderacion);
		}	
		
		ActionListener deshacer = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				txa_Resultado_Configuracion.setText("");
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
		
		JTextArea txa_Resultado_Seleccion = new Componentes().textArea(new JTextArea(), "Concepto", 135);
		JScrollPane Resultado = new JScrollPane(txa_Resultado_Seleccion);
		
		DefaultTableModel model = new DefaultTableModel(null, new String[]{"Folio", "Nombre Completo","Establecimiento","Departamento","Puesto", " *"}
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
		
		JTable tabla = new JTable(model);
	    JScrollPane scroll = new JScrollPane(tabla);
		
		@SuppressWarnings("rawtypes")
		private TableRowSorter trsfiltro;
		JTextField txtFiltro= new Componentes().text(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<", 300, "String");
		
		JButton btnAgregar = new JButton("Aplicar",new ImageIcon("imagen/Aplicar.png"));
		JButton btnDeshacer = new JButton("Deshacer",new ImageIcon("imagen/deshacer16.png"));
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Cat_Seleccion_Del_Usuario()	{
			this.setSize(1024,740);
			this.setLocationRelativeTo(null);
			this.setResizable(false);
			this.setModal(true);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/ayudar-a-ver-el-boton-icono-4900-64.png"));
			this.setTitle("Filtro De Seleccion De Colaboradores");
		    this.panel.setBorder(BorderFactory.createTitledBorder("Selecciona A El (Los) Colaborador(es) A El (Los) Que Aplicara La Actividad"));
			this.trsfiltro = new TableRowSorter(model); 
			this.tabla.setRowSorter(trsfiltro);  
			this.txa_Resultado_Seleccion.setEditable(false);
			this.txa_Resultado_Seleccion.setLineWrap(true); 
			this.txa_Resultado_Seleccion.setWrapStyleWord(true);
			
			int x=15,y=20,width=100,height=20;

			this.panel.add(txtFiltro).setBounds                                                   (x     ,y     ,width*9+40 ,height);
			this.panel.add(scroll).setBounds                                                      (x     ,y+=20 ,width*10-10,width*3);
			
			x=15;
			this.panel.add(new JLabel("     Detalle De La Configuracion Seleccionada:")).setBounds(x     ,y+=320,width*3    ,height);
			this.panel.add(Resultado  ).setBounds                                                 (x     ,y+=20 ,width*10-10,width*3);
			this.panel.add(btnDeshacer).setBounds                                                 (x     ,y+=305,width      ,height);
			this.panel.add(btnAgregar).setBounds                                                  (x+892 ,y     ,width      ,height);
			
			this.cont.add(panel);
			this.init_tabla();
			comentario();
			this.tabla.addMouseListener(opcomentario);
			
			this.tabla.addKeyListener(opseleccioncontecladocomentario);
			this.txtFiltro.addKeyListener(opFiltroFolio);
			
			this.btnAgregar.addActionListener(Agregar);
			this.btnDeshacer.addActionListener(deshacer);
			
			///guardar con control+A
	        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A,Event.CTRL_MASK),"guardar");
	             getRootPane().getActionMap().put("guardar", new AbstractAction(){
	                 public void actionPerformed(ActionEvent e)
	                 {                 	    btnAgregar.doClick();           	    }
	            });
	 	     //deshacer con escape
	 	                 getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
	 	                   getRootPane().getActionMap().put("escape", new AbstractAction(){
	 	                  public void actionPerformed(ActionEvent e)
	 	                  {                btnDeshacer.doClick();           	    }
	 	              });

		}
		
		ActionListener Agregar = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(tabla.isEditing()){
					tabla.getCellEditor().stopCellEditing();
				}
				if(!txtFiltro.getText().equals("")){
					JOptionPane.showMessageDialog(null, "No se Puede Aplicar En Medio De Una Busqueda, Por Que Podrian Haber Mas Colaboradores Seleccionados "
							                          + "\nA Continuación Se Borrará El Filtro Para Mostrarle Todos Los Colaboradores y Podrá Aplicar De Nuevo"
                                                      , "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
					txtFiltro.setText("");
					int[] columnas = {0,1,2,3,4};
					new Obj_Filtro_Dinamico_Plus(tabla,txtFiltro.getText(), columnas);
					comentario();
					return;
				}
				usuarios.setUsuarios_nombres(tabla_folio_y_nombre_completo());
									dispose();
			}
		};
		
		ActionListener deshacer = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				txa_Resultado_Seleccion.setText("");
				refrestabla();
				comentario();
			 }
			};
		
		MouseListener opcomentario = new MouseListener() {
			public void mouseReleased(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseClicked(MouseEvent arg0) {
				comentario();
			}
		};
		
		KeyListener opseleccioncontecladocomentario = new KeyListener(){
			public void keyReleased(KeyEvent arg0) {
				comentario();
			}
			public void keyTyped(KeyEvent arg0)   {}
			public void keyPressed(KeyEvent arg0) {}		
		};
		
		KeyListener opFiltroFolio = new KeyListener(){
			public void keyReleased(KeyEvent arg0) {
				int[] columnas = {0,1,2,3,4};
				new Obj_Filtro_Dinamico_Plus(tabla, txtFiltro.getText().toUpperCase(), columnas);
				comentario();
			}
			public void keyTyped(KeyEvent arg0)   {}
			public void keyPressed(KeyEvent arg0) {}		
		};
		
		public void comentario(){
			txa_Resultado_Seleccion.setText("");
			String Comentario_colaboradores="";
			int testigo=0;
			 if(!txtFiltro.getText().toString().equals("")){
				 Comentario_colaboradores="Esta Filtrando La Tabla No Se Pueden Mostrar Los Empleado Seleccionados:";
				 testigo=1;
			 }else{
				Object[][] colaboradores = tabla_folio_y_nombre_completo();
    			 Comentario_colaboradores="Esta actividad Aplica Para Los Siguientes Colaboradores:";
    			for(int i=0; i<colaboradores.length; i++){
    				Comentario_colaboradores=Comentario_colaboradores+"\n  *"+colaboradores[i][1].toString().trim();
    				testigo=1;
    			}
			 }	
    			
    		if(testigo>0){
    			txa_Resultado_Seleccion.setText(Comentario_colaboradores);
    			}else{
    				tabla_seleccion_default_usuario();
    				txa_Resultado_Seleccion.setText(Comentario_colaboradores+"\n  *"+usuario.getNombre_completo());
    			}
		}
		
		public void tabla_seleccion_default_usuario(){
			for(int i=0; i<tabla.getRowCount(); i++){
				 if(Integer.valueOf(tabla.getValueAt(i,0).toString().trim())==(usuario.getFolio())){
					  model.setValueAt("true", i, 5);
			     }
			}
		}
		
		public void Carga_Desde_El_Objeto(){
			Object[][] colaboradores=usuarios.getUsuarios_nombres();
				for(int i2=0; i2<colaboradores.length; i2++){
					for(int i=0; i<tabla.getRowCount(); i++){
					   if( (tabla.getValueAt(i,1).toString().trim()).equals(colaboradores[i2][1].toString().trim())){
					  model.setValueAt("true", i, 5);
			      }
				}
			}
		};
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public String[][] tabla_folio_y_nombre_completo(){
			int cantidad_de_columnas_matriz=2;
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
	
		
		@SuppressWarnings("unchecked")
		public void init_tabla(){
			this.tabla.getTableHeader().setReorderingAllowed(false) ;
			this.tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

			this.tabla.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",12));
			this.tabla.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",12));
			this.tabla.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",12));
			this.tabla.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",12));
			this.tabla.getColumnModel().getColumn(4).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",12));
			this.tabla.getColumnModel().getColumn(5).setCellRenderer(new tablaRenderer("CHB","centro","Arial","negrita",12));
			
			this.tabla.getColumnModel().getColumn(0).setMaxWidth(40);
			this.tabla.getColumnModel().getColumn(0).setMinWidth(40);
			this.tabla.getColumnModel().getColumn(1).setMinWidth(335);
			this.tabla.getColumnModel().getColumn(1).setMaxWidth(335);
			this.tabla.getColumnModel().getColumn(2).setMinWidth(140);
			this.tabla.getColumnModel().getColumn(2).setMaxWidth(140);
			this.tabla.getColumnModel().getColumn(3).setMinWidth(185);
			this.tabla.getColumnModel().getColumn(3).setMaxWidth(500);
			this.tabla.getColumnModel().getColumn(4).setMinWidth(240);
			this.tabla.getColumnModel().getColumn(4).setMaxWidth(500);
			this.tabla.getColumnModel().getColumn(5).setMinWidth(20);
			this.tabla.getColumnModel().getColumn(5).setMaxWidth(30);
			this.tabla.setRowSorter(trsfiltro);  
			refrestabla();
			
			if(!(usuarios.getUsuarios_nombres()==null)){
				Carga_Desde_El_Objeto();
			}
		}
		
		private void refrestabla(){
			model.setRowCount(0);
			Statement s;
			ResultSet rs;
			try {
				Connexion con = new Connexion();
				s = con.conexion().createStatement();
				rs = s.executeQuery("exec sp_filtro_empleado_actividades_status_vigente "+usuario.getFolio());
				while (rs.next())
				{  String [] fila = new String[6];
				   fila[0] = rs.getString(1).trim();
				   fila[1] = rs.getString(2).trim();
				   fila[2] = rs.getString(3).trim(); 
				   fila[3] = rs.getString(4).trim(); 
				   fila[4] = rs.getString(5).trim(); 
				   fila[5] = "false";
				   model.addRow(fila); 
				}	
			} catch (SQLException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en la Subclase Cat_Seleccion_Del_Ususario SQLException: "+e1.getMessage(), "Avisa al Administrador Del Sistema", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			}
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
		  String[] secede = {"DIARIA","SEMANAL","MENSUAL"};
			@SuppressWarnings({ "rawtypes", "unchecked" })
			JComboBox cmbSucede = new JComboBox(secede);
			
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
		
		JButton btnAceptar = new JButton("Aplicar",new ImageIcon("imagen/Aplicar.png"));
		JButton btnDeshacerFrecuencia = new JButton("Deshacer",new ImageIcon("imagen/deshacer16.png"));
		Border blackline;
		
		public Cat_Frecuencia_De_Actividades() {
			this.setSize(800,625);
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
			
			panel.add(new JLabel("Tipo De Programación: ")).setBounds(x, y, ancho+50, 20);
			panel.add(cmbTipoDeProgramacion).setBounds(x+ancho+60, y, ancho*2, 20);
			
			panel.add(rbHastaQueSeCumpla).setBounds(x+ancho*4+60, y, ancho*2, 20);
			panel.add(rbEnLaFechaIndicada).setBounds(x+ancho*6+60, y, ancho*2, 20);
					
		//unica repeticion
			panel.add(lblUnicarepeticion).setBounds(x-40, y+=25, ancho, 20);
			panel.add(new JSeparator()).setBounds(x+ancho-60, y+11, ancho*8+70, 20);
			
			panel.add(new JLabel("Fecha: ")).setBounds(x, y+=25, ancho, 20);
			panel.add(fh_unica_repeticion).setBounds(x+ancho-10, y, ancho+40, 20);
			panel.add(new JLabel("Hora: ")).setBounds(x+ancho*3, y, ancho, 20);
			panel.add(chbConHora).setBounds(x+ancho*4-40, y, 30, 20);
			panel.add(spHoraUnicaRepeticion).setBounds(x+ancho*4-10, y, ancho+20, 20);
			
		//frecuencia
			panel.add(new JLabel("Frecuencia")).setBounds(x-40, y+=25, ancho, 20);
			panel.add(new JSeparator()).setBounds(x+ancho-50, y+11, ancho*8+60, 20);
			
			panel.add(new JLabel("Sucede: ")).setBounds(x, y+=25, ancho, 20);
			panel.add(cmbSucede).setBounds(x+ancho, y, ancho+30, 20);
			
			panel.add(rbDiaDelMes).setBounds(x-40, y+=25, 60, 20);
			panel.add(lblSeRepiteCada).setBounds(x, y, ancho+30, 20);
			panel.add(spDiasARepetir).setBounds(x+ancho+30, y, ancho, 20);
			panel.add(lblDias_Semana).setBounds(x+ancho*2+50, y, ancho, 20);
			
			panel.add(spMeses).setBounds(x+ancho*3+20, y, ancho, 20);
			panel.add(lblMeses).setBounds(x+ancho*4+40, y, ancho, 20);
			
			panel.add(rbDiaDeLaSemana).setBounds(x-40, y+=25, 60, 20);
			
			panel.add(cmbNivelDeDias).setBounds(x+110, y, ancho, 20);
			panel.add(cmbDiaDeLaSemana).setBounds(x+210, y, ancho, 20);
			panel.add(lblDeCada).setBounds(x+300, y, ancho, 20);
			panel.add(spMeses2).setBounds(x+350, y, ancho, 20);
			panel.add(lblMeses2).setBounds(x+450, y, ancho, 20);
			
//			semana------
			panel.add(chbLunes).setBounds(x+110, y, ancho, 20);
			panel.add(chbMartes).setBounds(x+210, y, ancho, 20);
			panel.add(chbMiercoles).setBounds(x+310, y, ancho, 20);
			panel.add(chbJueves).setBounds(x+410, y, ancho, 20);
			
			panel.add(chbViernes).setBounds(x+110, y+=25, ancho, 20);
			panel.add(chbSabado).setBounds(x+210, y, ancho, 20);
			panel.add(chbDomingo).setBounds(x+310, y, ancho, 20);
			
//			frecuencia diaria
			panel.add(new JLabel("Frecuencia Diaria")).setBounds(x-40, y+=25, ancho+20, 20);
			panel.add(new JSeparator()).setBounds(x+ancho-20, y+11, ancho*8+30, 20);
			
			panel.add(chAsignarHora).setBounds(x, y+=25, ancho+40, 20);
			panel.add(spHoraFrecuenciaDiaria).setBounds(x+ancho+40, y, ancho+20, 20);
			
//			Duracion
			panel.add(new JLabel("Duracion")).setBounds(x-40, y+=25, ancho, 20);
			panel.add(new JSeparator()).setBounds(x+ancho-60, y+11, ancho*8+70, 20);
			
			panel.add(new JLabel("Fecha De Inicio: ")).setBounds(x+30, y+=25, ancho, 20);
			panel.add(fh_inicial_de_duracion).setBounds(x+ancho+40, y, ancho+40, 20);
			panel.add(rbFechaDeFinalizacion).setBounds(x+ancho*3+30, y, ancho+60, 20);
			panel.add(fh_final_de_duracion).setBounds(x+ancho*5+10, y, ancho+40, 20);
			panel.add(rbSinFechaDeFinalizacion).setBounds(x+ancho*3+30, y+=25, ancho*2, 20);
			
//			resumen
			panel.add(new JLabel("Resumen")).setBounds(x-40, y+=25, ancho, 20);
			panel.add(new JSeparator()).setBounds(x+ancho-60, y+11, ancho*8+70, 20);
			panel.add(scrollDescripcion).setBounds(x, y+=25, ancho*8+50, 100);

			ancho=100;
			panel.add(btnDeshacerFrecuencia).setBounds(x, y+=105, ancho, 20);
			panel.add(btnAceptar).setBounds(x+590, y, ancho, 20);
			
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
				dispose();
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
					
					lblDias_Semana.setText("Dias");
					
				break;
				case "SEMANAL":
					
					lblSeRepiteCada.setVisible(true);
					
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
					
					lblDias_Semana.setText("Semanas, el");
					
				break;
				case "MENSUAL":
					
					lblSeRepiteCada.setVisible(false);
					
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
			}else{
				fh_final_de_duracion.setEnabled(false);
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
				lblUnicarepeticion.setText("Antes de");
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

		public void llenarObjeto(){
			
			frecuencia.setTipo_de_frecuencia(cmbTipoDeProgramacion.getSelectedItem().toString());
			frecuencia.setSeleccion_hasta_que_se_cumpla(rbHastaQueSeCumpla.isSelected());
			frecuencia.setSeleccion_en_la_fecha_indicada(rbEnLaFechaIndicada.isSelected());
			
		//unica repeticion
			frecuencia.setFh_unica_repeticion(new SimpleDateFormat("dd/MM/yyyy").format(fh_unica_repeticion.getDate()));
			frecuencia.setSeleccion_con_hora(chbConHora.isSelected());
			frecuencia.setHora_unica_repeticion(new SimpleDateFormat("HH:mm:ss").format(spHoraUnicaRepeticion.getValue()));
			
		//frecuencia
			frecuencia.setSucede(cmbSucede.getSelectedItem().toString());
			frecuencia.setSelecciona_dia_del_mes(rbDiaDelMes.isSelected());
			frecuencia.setDias_a_repetir_por_suceso_de_dias(cmbSucede.getSelectedItem().toString().equals("DIARIA")?Integer.valueOf(spDiasARepetir.getValue().toString()):0);
			frecuencia.setDias_a_repetir_por_suceso_de_semanas(cmbSucede.getSelectedItem().toString().equals("SEMANAL")?Integer.valueOf(spDiasARepetir.getValue().toString()):0);
			frecuencia.setDias_a_repetir_por_suceso_de_meses(cmbSucede.getSelectedItem().toString().equals("MENSUAL")?Integer.valueOf(spDiasARepetir.getValue().toString()):0);
			frecuencia.setMes1(Integer.valueOf(spMeses.getValue().toString()));
			frecuencia.setSelecciona_dia_de_la_semana(rbDiaDeLaSemana.isSelected());
			frecuencia.setNivel_de_dias(cmbNivelDeDias.getSelectedItem().toString());
			frecuencia.setDia_de_la_semana(Integer.valueOf(cmbDiaDeLaSemana.getSelectedIndex()));
			frecuencia.setMes2(Integer.valueOf(spMeses2.getValue().toString()));
       
	   //semana
			frecuencia.setDomingo(chbDomingo.isSelected());
			frecuencia.setLunes(chbLunes.isSelected());
			frecuencia.setMartes(chbMartes.isSelected());
			frecuencia.setMiercoles(chbMiercoles.isSelected());
			frecuencia.setJueves(chbJueves.isSelected());
			frecuencia.setViernes(chbViernes.isSelected());
			frecuencia.setSabado(chbSabado.isSelected());
			 
       //frecuencia diaria
			frecuencia.setSeleccion_asignar_hora(chAsignarHora.isSelected());
			frecuencia.setHora_frecuencia_diaria(new SimpleDateFormat("HH:mm:ss").format(spHoraFrecuenciaDiaria.getValue()));
			
       //Duracion
			frecuencia.setFecha_inicio_duracion(new SimpleDateFormat("dd/MM/yyyy").format(fh_inicial_de_duracion.getDate()));
			
			frecuencia.setSeleccion_fecha_finaliza(rbFechaDeFinalizacion.isSelected());
			frecuencia.setFecha_final_duracion(rbFechaDeFinalizacion.isSelected()?(new SimpleDateFormat("dd/MM/yyyy").format(fh_final_de_duracion.getDate())):"16/11/2094");
			frecuencia.setSeleccion_sin_fecha_final(rbSinFechaDeFinalizacion.isSelected());
		}
		
		@SuppressWarnings("deprecation")
		public void leerObjeto(){
			cmbTipoDeProgramacion.setSelectedItem(frecuencia.getTipo_de_frecuencia().toString());
			rbHastaQueSeCumpla.setSelected(frecuencia.isSeleccion_hasta_que_se_cumpla());
			rbEnLaFechaIndicada.setSelected(frecuencia.isSeleccion_en_la_fecha_indicada());
			
		//unica repeticion
			try {
				fh_unica_repeticion.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(frecuencia.getFh_unica_repeticion()));
			} catch (ParseException e) {// Auto-generated catch block
				e.printStackTrace();	}
			
			chbConHora.setSelected(frecuencia.isSeleccion_con_hora());

			String[] horaunicaRepeticion = frecuencia.getHora_unica_repeticion().split(":");
			spHoraUnicaRepeticion.setValue(new Time(Integer.parseInt(horaunicaRepeticion[0]),Integer.parseInt(horaunicaRepeticion[1]),Integer.parseInt(horaunicaRepeticion[2])));
			spHoraUnicaRepeticion.setEditor(spDHoraUnicaRepeticion);
			
		//frecuencia
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
			} catch (ParseException e) {// Auto-generated catch block
				e.printStackTrace();	}
			rbFechaDeFinalizacion.setSelected(frecuencia.isSeleccion_fecha_finaliza());
			try {
				fh_final_de_duracion.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(frecuencia.getFecha_final_duracion()));
			} catch (ParseException e) {// Auto-generated catch block
				e.printStackTrace();	}
			rbSinFechaDeFinalizacion.setSelected(frecuencia.isSeleccion_sin_fecha_final());
		}
	}
	
	
		
	public static void main(String[] args) {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Actividades_De_Una_Planeacion().setVisible(true);
		}catch(Exception e){	}	
	}
}
