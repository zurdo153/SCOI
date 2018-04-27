package Cat_Checador;

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
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SpinnerDateModel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Conexiones_SQL.Connexion;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Checador.Obj_Alimentacion_De_Permisos_A_Empleados;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_Filtro_Dinamico;
import Obj_Principal.Obj_Filtro_Dinamico_Plus;
import Obj_Renders.tablaRenderer;

import com.toedter.calendar.JDateChooser;

@SuppressWarnings("serial")
public class Cat_Alimentacion_De_Permisos_A_Colaboradores_Por_Nivel_Jerarquico extends JFrame {
	
	Container cont  = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JLabel lblSolicito       = new JLabel();
	JLabel lblPermisos       = new JLabel();
	JLabel lblFolio          = new JLabel("Folio:");
	JLabel lblMotivo         = new JLabel("Motivo:");
	JLabel lblStatus         = new JLabel("Estatus:");
	JLabel lblUsuario        = new JLabel("Usuario: ");
	JLabel lblFecha          = new JLabel("Fecha de Permiso");
	JLabel lblEmpleado       = new JLabel("Colaborador: ");
	JLabel lblSelecionaFecha = new JLabel("Seleccione La Fecha En Que Aplica El Permiso:");

	JTextField txtFolio = new Componentes().text(new JCTextField(), "Folio Permiso", 8, "String");
	JTextField txtFolioEmpleado = new Componentes().text(new JCTextField(), "Colaborador", 8, "Int");
	
	JDateChooser txtFechaPermiso = new JDateChooser();
	
	JRadioButton rbEmpleado = new JRadioButton("Colaborador");
	JRadioButton rbEmpresa = new JRadioButton("Empresa");	
	ButtonGroup gr_solicito = new ButtonGroup();
	
	JCheckBox chbP_trabajarCorrido = new JCheckBox("1.- Permiso Para Trabajar Corrido");
	JCheckBox chbP_salirTemprano = new JCheckBox("2.- Permiso Para Salir Temprano");
	JCheckBox chbP_entrarTarde = new JCheckBox("3.- Permiso Para Entrar Tarde");
	JCheckBox chbP_noAsistir = new JCheckBox("4.- Permiso Para No Asistir (con goce de sueldo)");
	JCheckBox chbP_noAsistir2 = new JCheckBox("5.- Permiso Para No Asistir (sin goce de sueldo)");
	JCheckBox chbP_cambiodescanso = new JCheckBox("6.- Permiso para trabajar como el dia:");
	JCheckBox chbP_doblarExtra = new JCheckBox("7.- Permiso para doblar extra");
	JCheckBox chbP_tiempoComida = new JCheckBox("8.- Permiso para cambiar tiempo de comida a:");
	JCheckBox chbP_permisochecarOtroEstab = new JCheckBox("9.- Permiso para checar en el establecimiento:");

	ButtonGroup grupo = new ButtonGroup();
	
    String[] dias = { "Seleccione un dia", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo" };
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	private JComboBox cmbDias = new JComboBox(dias);
	 
	String establecimiento[] = new Obj_Establecimiento().Combo_Establecimiento();
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbEstablecimiento = new JComboBox(establecimiento);
	
	String status[] = {"Vigente","Cancelado"};
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbStatus = new JComboBox(status);

	//declarar variable en hora 00:00:00
	//crear spinner y asignarlo con la fecha y hora actual
	//en el constructor cambiamos la fecha con hora = 00:00:00
    //y despues mostramos solo la hora
	String[] comida ="0:00:00".split(":");
	SpinnerDateModel scom =  new SpinnerDateModel();
	  JSpinner spComida = new JSpinner(scom);                                         
	  	JSpinner.DateEditor  com = new JSpinner.DateEditor(spComida,"H:mm"); 
	  	
		JButton btnFiltroEmpleadoCambio = new JButton(new ImageIcon("Iconos/zoom_icon&16.png"));
		JTextField txtFolioEmpleadoCambio = new JTextField();
		JTextField txtEmpleadoCambio = new JTextField();
		JTextField txtEmpleadoHorario= new JTextField();

	 
	JTextArea txaMotivo = new Componentes().textArea(new JTextArea(), "Motivo", 400);
	JScrollPane Observasiones = new JScrollPane(txaMotivo);

	JCButton btnFiltroEmpleado = new JCButton("Colaborador"        ,"asistencia-comunitaria-icono-9465-16.png","Azul");
	JCButton btnBuscar         = new JCButton(""        ,"buscar.png"            ,"Azul");
	JCButton btnFiltro         = new JCButton("Permiso" ,"Filter-List-icon16.png","Azul");
	JCButton btnSalir          = new JCButton("Salir"   ,"salir16.png"      ,"Azul");
	JCButton btnDeshacer       = new JCButton("Deshacer","deshacer16.png","Azul");
	JCButton btnGuardar        = new JCButton("Guardar","Guardar.png"    ,"Azul");
	JCButton btnEditar         = new JCButton("Editar","editara.png"     ,"Azul");
	JCButton btnNuevo          = new JCButton("Nuevo","Nuevo.png"        ,"Azul");
	
	
	Border blackline, border = LineBorder.createGrayLineBorder();

//	almacena el numero de permisos que se le asignara al empleado
	int permiso=0;
//	almacena el numero de dia de descanso que se le asignara al empleado
	int descanso=0;
	
//	cuando entra al filtro trae un valor de parametro y se almacena en esta para ver si asignara empleado al permiso
//	o si lo asignara al campo de empleado del turno
	int tipo_filtro_empleado=0;
	
// se utiliza cuando es un permiso 7  para los dias dobla
	int tiene_dia_dobla=0;
	
	Obj_Usuario usuario = new Obj_Usuario().LeerSession();
	
	@SuppressWarnings("deprecation")
	public void getConstructor(){
		this.setSize(755,500);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/checklistbtn.png"));
		this.panel.setBorder(BorderFactory.createTitledBorder("Permisos Checador"));
		this.setTitle("Permisos A Colaboradores Por Nivel Jerarquico");
//		blackline = BorderFactory.createLineBorder(new java.awt.Color(174,219,255));
		this.blackline = BorderFactory.createEtchedBorder();
		this.lblSolicito.setBorder(BorderFactory.createTitledBorder(blackline,"Solicitó: "));
		this.lblPermisos.setBorder(BorderFactory.createTitledBorder(blackline,"Seleccione El Permiso: "));
		
		txaMotivo.setBorder(border);
		int x=20,sep=143, y=20,ya=30, width=100,height=20 ;
		
		panel.add(txtFolio).setBounds                   (x         ,y    ,width    ,height  );
		panel.add(btnFiltro).setBounds                  (x+=width  ,y    ,width+27  ,height  );
		panel.add(lblUsuario).setBounds                 (x+=sep    ,y    ,width*3  ,height  );
		
		sep=290;width=84;
		panel.add(lblSolicito).setBounds                (x+=sep    ,y-13 ,165      ,height*2);
		panel.add(rbEmpleado).setBounds                 (x+=3      ,y    ,width    ,height  );
		panel.add(rbEmpresa).setBounds                  (x+=83     ,y    ,width    ,height  );
		
		x=20;sep=144;width=100;
		panel.add(txtFolioEmpleado).setBounds           (x         ,y+=ya,width    ,height  );
		panel.add(btnFiltroEmpleado).setBounds          (x+=width  ,y    ,width+27 ,height  );
		panel.add(lblEmpleado).setBounds                (x+=sep    ,y    ,width*3  ,height  );
		panel.add(lblStatus).setBounds                  (x+=width*3,y    ,width    ,height  );
		panel.add(cmbStatus).setBounds                  (x+50      ,y    ,width    ,height  );
		
		x=265;sep=290;
		panel.add(lblSelecionaFecha).setBounds          (x         ,y+=ya,width*4  ,height  );
		panel.add(txtFechaPermiso).setBounds            (x+sep     ,y    ,160      ,height  );
		
		x=20;width=260;
		panel.add(lblPermisos).setBounds                (x         ,y+=20,703      ,170     );
		
		panel.add(chbP_trabajarCorrido).setBounds       (x+=5      ,y+=15,width    ,height  );
		panel.add(chbP_salirTemprano).setBounds         (x         ,y+=ya,width    ,height  );
		panel.add(chbP_entrarTarde).setBounds           (x         ,y+=ya,width    ,height  );
		panel.add(chbP_noAsistir).setBounds             (x         ,y+=ya,width    ,height  );
		panel.add(chbP_noAsistir2).setBounds            (x         ,y+=ya,width    ,height  );
		
		x=310;y=115;width=247;
		panel.add(chbP_cambiodescanso).setBounds        (x         ,y    ,width    ,height  );
		panel.add(chbP_doblarExtra).setBounds           (x         ,y+=ya,width    ,height  );
		panel.add(chbP_tiempoComida).setBounds          (x         ,y+=ya,width    ,height  );
		panel.add(chbP_permisochecarOtroEstab).setBounds(x         ,y+=ya,width    ,height  );
		
		x=x+=247;y=115;width=155;
		panel.add(cmbDias).setBounds                    (x         ,y      ,width  ,height  );
		panel.add(spComida).setBounds                   (x         ,y+=ya*2,width/2,height  );
		panel.add(cmbEstablecimiento).setBounds         (x         ,y+=ya  ,width  ,height  );
		
		x=20;
		panel.add(lblMotivo).setBounds                  (x        ,y+=75   ,80     ,height  );
		panel.add(Observasiones).setBounds              (x        ,y+=20   ,700    ,80      );
		
		x=20;width=120;sep=145;height=25;
		panel.add(btnNuevo).setBounds                   (x        ,y+=90   ,width  ,height  );
		panel.add(btnGuardar).setBounds                 (x+=sep   ,y       ,width  ,height  );
		panel.add(btnEditar).setBounds                  (x+=sep   ,y       ,width  ,height  );
		panel.add(btnDeshacer).setBounds                (x+=sep   ,y       ,width  ,height  );
		panel.add(btnSalir).setBounds                   (x+=sep   ,y       ,width  ,height  );
		
//		se asigna hora cero al spinner y se le indica ke muestre solo la hora 
		spComida.setValue(new Time(Integer.parseInt(comida[0]),Integer.parseInt(comida[1]),Integer.parseInt(comida[2])));
		spComida.setEditor(com);
		
		txaMotivo.setLineWrap(true);
		
		gr_solicito.add(rbEmpleado);
		gr_solicito.add(rbEmpresa);
		
		grupo.add(chbP_trabajarCorrido);
		grupo.add(chbP_salirTemprano);
		grupo.add(chbP_entrarTarde);
		grupo.add(chbP_noAsistir);
		grupo.add(chbP_noAsistir2);
		grupo.add(chbP_cambiodescanso);
		grupo.add(chbP_permisochecarOtroEstab);
		grupo.add(chbP_doblarExtra);
		grupo.add(chbP_tiempoComida);

		
		btnGuardar.addActionListener(guardar);
		btnBuscar.addActionListener(opBuscar);
		btnNuevo.addActionListener(opNuevo);
		btnSalir.addActionListener(opSalir);
		btnDeshacer.addActionListener(opLimpiar);
		btnEditar.addActionListener(opEditar);
		btnFiltro.addActionListener(opFiltro);
		btnFiltroEmpleado.addActionListener(opFiltroEmpleados);
		btnFiltroEmpleadoCambio.addActionListener(opFiltroEmpleadosTurno);
		
		chbP_cambiodescanso.addActionListener(funcion_chbs);
		chbP_entrarTarde.addActionListener(funcion_chbs);
        chbP_noAsistir.addActionListener(funcion_chbs);
        chbP_noAsistir2.addActionListener(funcion_chbs);
		chbP_salirTemprano.addActionListener(funcion_chbs);
		chbP_trabajarCorrido.addActionListener(funcion_chbs);
		chbP_doblarExtra.addActionListener(funcion_chbs);
		chbP_tiempoComida.addActionListener(funcion_chbs);
		chbP_permisochecarOtroEstab.addActionListener(funcion_chbs);
		
		Campos_False();
		txtFolioEmpleado.setEditable(false);
		btnGuardar.setEnabled(false);
		btnEditar.setEnabled(false);
		lblUsuario.setText("Usuario: "+usuario.getNombre_completo());
		
		cont.add(panel);
		
	    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
	       KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), "foco");	  
	    
	    getRootPane().getActionMap().put("foco", new AbstractAction(){
	        @Override
	        public void actionPerformed(ActionEvent e)
	        {	        	btnFiltroEmpleado.doClick();    	     }
	    });
	    
	    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
	       KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0), "horario");
	    
	    getRootPane().getActionMap().put("horario", new AbstractAction(){
	        @Override
	        public void actionPerformed(ActionEvent e)
	        {    	        	btnFiltro.doClick();          }
	    });
	       getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
	        getRootPane().getActionMap().put("escape", new AbstractAction(){
	          public void actionPerformed(ActionEvent e) {  btnDeshacer.doClick();
						                                    txtFolio.requestFocus();   }
						        });
						        
	}					        
						        
	
	public Cat_Alimentacion_De_Permisos_A_Colaboradores_Por_Nivel_Jerarquico(){
		 getConstructor();
	}
	
	
	public String ValidaCampos(){
		String error ="";
		String fechaNull= txtFechaPermiso.getDate()+"";
		
		if(txtFolio.getText().equals("")) 
			error+= "Folio\n";
		
		if(txtFolioEmpleado.getText().equals("")) 
			error+= "Empleado\n";
		
		if(fechaNull.equals("null"))
			error+= "Fecha de Permiso\n";
		
		if(!rbEmpleado.isSelected() && !rbEmpresa.isSelected()){
			error+= "Solicito\n";
		}
		
		if(chbP_trabajarCorrido.isSelected()==false && chbP_salirTemprano.isSelected()==false 
				&& chbP_entrarTarde.isSelected()==false && chbP_noAsistir.isSelected()==false 
				&& chbP_noAsistir2.isSelected()==false && chbP_cambiodescanso.isSelected()==false
				&& chbP_doblarExtra.isSelected()==false && chbP_tiempoComida.isSelected()==false
				&& chbP_permisochecarOtroEstab.isSelected()==false ) //pb despues del ultimo par &&
			error+="Seleccione un Permiso\n";
		
		if(txaMotivo.getText().equals("")) 
			error+= "Motivo\n";

		return error;
	}
	
	public void permisoChecador(){
		if(chbP_trabajarCorrido.isSelected()){permiso=1;}
		if(chbP_salirTemprano.isSelected()){permiso=2;}
		if(chbP_entrarTarde.isSelected()){permiso=3;}
		if(chbP_noAsistir.isSelected()){permiso=4;}
		if(chbP_noAsistir2.isSelected()){permiso=5;}
		if(chbP_cambiodescanso.isSelected()){permiso=6;}
		if(chbP_doblarExtra.isSelected()){permiso=7;}
		if(chbP_tiempoComida.isSelected()){permiso=8;}
		if(chbP_permisochecarOtroEstab.isSelected()){permiso=9;}
	}
	public void dia_selecionado_p_descanso(){
		
		switch(cmbDias.getSelectedItem()+""){
			case "Lunes":		descanso=1		;break;
			case "Martes":		descanso=2		;break;
			case "Miercoles":	descanso=3		;break;
			case "Jueves":		descanso=4		;break;
			case "Viernes":		descanso=5		;break;
			case "Sabado":		descanso=6		;break;
			case "Domingo":		descanso=7		;break;
			default:			descanso=0		;break;
		}
	}
	
	 
	 
	 public void resetPermisos(){
		 
			grupo.remove(chbP_trabajarCorrido         );
			grupo.remove(chbP_salirTemprano           );
			grupo.remove(chbP_entrarTarde 	          );
			grupo.remove(chbP_noAsistir 	          );
			grupo.remove(chbP_noAsistir2 	          );
			grupo.remove(chbP_cambiodescanso          );
			grupo.remove(chbP_doblarExtra 	          );
			grupo.remove(chbP_tiempoComida 	          );
			grupo.remove(chbP_permisochecarOtroEstab  );
			
			chbP_trabajarCorrido        .setSelected(false);
			chbP_salirTemprano          .setSelected(false);
			chbP_entrarTarde 	        .setSelected(false);
			chbP_noAsistir 	            .setSelected(false);
			chbP_noAsistir2 	        .setSelected(false);
			chbP_cambiodescanso         .setSelected(false);
            chbP_doblarExtra 	        .setSelected(false);
			chbP_tiempoComida     	    .setSelected(false);
			chbP_permisochecarOtroEstab .setSelected(false);
			
			grupo.add(chbP_trabajarCorrido);
			grupo.add(chbP_salirTemprano  );
			grupo.add(chbP_entrarTarde 	  );
			grupo.add(chbP_noAsistir 	  );
			grupo.add(chbP_noAsistir2 	  );
			grupo.add(chbP_cambiodescanso );
			grupo.add(chbP_doblarExtra 	  );
			grupo.add(chbP_tiempoComida   );
			grupo.add(chbP_permisochecarOtroEstab   );
			
	 }
	 
	 public void resetSolicitantes(){
		 	gr_solicito.remove(rbEmpleado);
			gr_solicito.remove(rbEmpresa);
			
			rbEmpleado.setSelected(false);
			rbEmpresa.setSelected(false);
			
			gr_solicito.add(rbEmpleado);
			gr_solicito.add(rbEmpresa);
	 }

	ActionListener guardar = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
		        Obj_Alimentacion_De_Permisos_A_Empleados Permiso = new Obj_Alimentacion_De_Permisos_A_Empleados().buscar(Integer.parseInt(txtFolio.getText()));
		        
		        
		        if (tiene_dia_dobla==0){
					
				if(txtFolio.getText().equals("")){
					JOptionPane.showMessageDialog(null, "El Folio Es Requerido", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
					return;
				}else{
					if(ValidaCampos().equals("")){
						
						Obj_Alimentacion_De_Permisos_A_Empleados conpararFecha = new Obj_Alimentacion_De_Permisos_A_Empleados().ComparacionFecha(new SimpleDateFormat("dd/MM/yyyy").format(txtFechaPermiso.getDate()));
						
						if(conpararFecha.getFecha().trim().equals("FECHA_PASADA")){
							JOptionPane.showMessageDialog(null, "No Puede Asignar Permiso A Una Fecha Que Ya Paso", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
							return;
						}else{
								permisoChecador();
								 dia_selecionado_p_descanso();
								 if(chbP_cambiodescanso.isSelected() && descanso==0){
									  cmbDias.requestFocus();
									  cmbDias.showPopup();
									 JOptionPane.showMessageDialog(null,"Seleccione dia como el que trabajara", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
									 return;
								 }
									 
									 if(Permiso.getFolio() == Integer.parseInt(txtFolio.getText())){
											if(JOptionPane.showConfirmDialog(null, "El registro ya existe, ¿desea cambiarlo?") == 0){
												
												Permiso.setFolio(Integer.parseInt(txtFolio.getText()));
												Permiso.setFolio_empleado(Integer.parseInt(txtFolioEmpleado.getText()));
												Permiso.setFolio_usuario(usuario.getFolio());
												Permiso.setFecha(new SimpleDateFormat("dd/MM/yyyy").format(txtFechaPermiso.getDate()));
												Permiso.setTipo_de_permiso(permiso);
												Permiso.setDescanso(descanso);
												SimpleDateFormat sdf = new SimpleDateFormat ("H:mm");
												Permiso.setTiempo_comida(sdf.format ((Date) spComida.getValue()));
												Permiso.setEstablecimiento(cmbEstablecimiento.getSelectedItem().toString().trim());
												Permiso.setStatus( Boolean.valueOf(cmbStatus.getSelectedIndex()==0?"true":"false"));
												Permiso.setMotivo(txaMotivo.getText().toUpperCase());
												
												Permiso.setSolicito(rbEmpresa.isSelected()?1:0);
												
												if(Permiso.actualizar(Integer.parseInt(txtFolio.getText()))){
													
													resetSolicitantes();
													resetPermisos();
													
													cmbDias.setSelectedIndex(0);
													
													lblEmpleado.setText("Empleado:");
													btnGuardar.setEnabled(false);
													btnEditar.setEnabled(true);
													txtFolio.setText("");
													txtFolioEmpleado.setText("");
													txtFechaPermiso.setDate(null);
													cmbEstablecimiento.setSelectedIndex(0);
													txaMotivo.setText("");
													
													Campos_False();
													tiene_dia_dobla=0;
													txtFolio.setEditable(true);
													txtFolio.requestFocus();
														JOptionPane.showMessageDialog(null,"El Registro se actualizo Exitosamente!","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
														return;
												}else{
													JOptionPane.showMessageDialog(null,"El Registro no se a actualizado!","Error",JOptionPane.ERROR_MESSAGE);
													return;
												}
											}
									}else{
										if(permiso!=7){
													  guardar();
													  return;
										              } else{
//																if(permiso==7){
																		if(new Obj_Alimentacion_De_Permisos_A_Empleados().buscar_doblada(Integer.parseInt(txtFolioEmpleado.getText()))==3){
																			
																				JOptionPane.showMessageDialog(null,"A El Colaborador Solo Se Le Pueden Asignar 2 Dias De Doblar Extra!", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
																				return;
																				}else{
																			
																							if(new Obj_Alimentacion_De_Permisos_A_Empleados().b_doblada(Integer.parseInt(txtFolioEmpleado.getText()))){
																						//////////guardado dia dobla extra copiado del dia que dobla
																								tiene_dia_dobla=1;
																								guardar();
																							}else{	
																								  new Cat_SeleccionDeDobla().setVisible(true); 
																							     }
										              }
										
									   }
								}
							}
					}else{
						JOptionPane.showMessageDialog(null, "los siguientes campos son requeridos: \n"+ValidaCampos(), "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
						return;
					}
				}
				}else{
					//////////guardado dia dobla extra seleccionado de boton
					guardar();
					
	          }
	        }     
		 };
		 
		 @SuppressWarnings("deprecation")
		public void guardar(){
		 
	        Obj_Alimentacion_De_Permisos_A_Empleados Permiso = new Obj_Alimentacion_De_Permisos_A_Empleados().buscar(Integer.parseInt(txtFolio.getText()));
			Permiso.setFolio(Integer.parseInt(txtFolio.getText()));
			Permiso.setFolio_empleado(Integer.parseInt(txtFolioEmpleado.getText()));
			Permiso.setFolio_usuario(usuario.getFolio());
			Permiso.setFecha(new SimpleDateFormat("dd/MM/yyyy").format(txtFechaPermiso.getDate()));
			Permiso.setTipo_de_permiso(permiso);
			Permiso.setStatus( Boolean.valueOf(cmbStatus.getSelectedIndex()==0?"true":"false"));
			Permiso.setDescanso(descanso);
			Permiso.setMotivo(txaMotivo.getText().toUpperCase());
			SimpleDateFormat sdf = new SimpleDateFormat ("H:mm");
			Permiso.setTiempo_comida(sdf.format ((Date) spComida.getValue()));
			Permiso.setEstablecimiento(cmbEstablecimiento.getSelectedItem().toString().trim());
			Permiso.setSolicito(rbEmpresa.isSelected()?1:0);
			
			if(Permiso.guardar_permiso(tiene_dia_dobla)){
				
				resetSolicitantes();
				resetPermisos();
				btnDeshacer.doClick();
				cmbDias.setSelectedIndex(0);
				spComida.setValue(new Time(Integer.parseInt(comida[0]),Integer.parseInt(comida[1]),Integer.parseInt(comida[2])));
				lblEmpleado.setText("Empleado:");
				btnGuardar.setEnabled(false);
				btnEditar.setEnabled(true);
				Campos_False();
				txtFolio.setText("");
				txtFolioEmpleado.setText("");
				txtFechaPermiso.setDate(null);
				cmbEstablecimiento.setSelectedIndex(0);
				txaMotivo.setText("");
				tiene_dia_dobla=0;
				
				btnFiltro.setEnabled(true);
				btnBuscar.setEnabled(true);
				btnNuevo.requestFocus();
						JOptionPane.showMessageDialog(null,"El Permiso Se Guardo Exitosamente!","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
						return;
					}else{
				JOptionPane.showMessageDialog(null,"El Registro no se a guardado avise al Administrador del Sistema!","Error",JOptionPane.ERROR_MESSAGE);
				return;
			}

		 }
		 
	
	ActionListener opBuscar = new ActionListener() {
		@SuppressWarnings("deprecation")
		public void actionPerformed(ActionEvent arg0) {
			
			Campos_False();
			if(txtFolio.getText().equals("")){
				JOptionPane.showMessageDialog(null, "Ingrese el folio para poder realizar la busqueda","Error",JOptionPane.WARNING_MESSAGE);
				return;
			}else {
				Obj_Alimentacion_De_Permisos_A_Empleados permisoEmp = new Obj_Alimentacion_De_Permisos_A_Empleados().buscar(Integer.parseInt(txtFolio.getText()));
				if(permisoEmp.getFecha().equals("")){
					JOptionPane.showMessageDialog(null, "No existe el registro con el folio: "+txtFolio.getText()+"","Error",JOptionPane.WARNING_MESSAGE);
					return;
				}else{
					
					txtFolio.setText(permisoEmp.getFolio()+"");
					txtFolioEmpleado.setText(permisoEmp.getFolio_empleado()+"");
					
					if(permisoEmp.getSolicito()==1){
						rbEmpresa.setSelected(true);
					}else{
						rbEmpleado.setSelected(true);
					}				
					cmbStatus.setSelectedIndex( Integer.valueOf(String.valueOf(permisoEmp.isStatus()+"").equals("true")?0:1) );
					
					switch(permisoEmp.getTipo_de_permiso()){
						case 1:chbP_trabajarCorrido.setSelected(true);break;
						case 2:chbP_salirTemprano.setSelected(true);break;
						case 3:chbP_entrarTarde.setSelected(true);break;
						case 4:chbP_noAsistir.setSelected(true);break;
						case 5:chbP_noAsistir2.setSelected(true);break;
						case 6:chbP_cambiodescanso.setSelected(true);break;
						case 7:chbP_doblarExtra.setSelected(true);break;
						case 8:chbP_tiempoComida.setSelected(true);break;
						case 9:	chbP_permisochecarOtroEstab.setSelected(true);	
						         cmbEstablecimiento.setSelectedItem(permisoEmp.getEstablecimiento());
								break;
					}
					
					cmbDias.setSelectedIndex(permisoEmp.getDescanso());
					
					try {
						Date date_permiso = new SimpleDateFormat("dd/MM/yyyy").parse(permisoEmp.getFecha());
						txtFechaPermiso.setDate(date_permiso);
					} catch (ParseException e) {
						e.printStackTrace();
					}
					
					String[] tiempo = permisoEmp.getTiempo_comida().split(":");
					spComida.setValue(new Time(Integer.parseInt(tiempo[0]),Integer.parseInt(tiempo[1]),Integer.parseInt(tiempo[2])));
					
					txaMotivo.setText(permisoEmp.getMotivo());
					cmbStatus.setSelectedItem(0);
					lblEmpleado.setText("Empleado: "+permisoEmp.getNombre_empleado());
					
					btnEditar.setEnabled(true);
					txaMotivo.requestFocus();
				}
			}
		}
	};
	
	@SuppressWarnings("rawtypes")
	public void buscar_empleado_a_copiarle_turno(int folio){
		 Vector fila_vector=new Obj_Alimentacion_De_Permisos_A_Empleados().Obj_Mensaje_respuesta(folio);
		 
		 txtFolioEmpleadoCambio.setText(fila_vector.get(0).toString());
		 txtEmpleadoCambio.setText(fila_vector.get(1).toString());
		 txtEmpleadoHorario.setText(fila_vector.get(2).toString());
	}
	
	ActionListener opEditar = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			
			Obj_Alimentacion_De_Permisos_A_Empleados conpararFecha = new Obj_Alimentacion_De_Permisos_A_Empleados().ComparacionFecha(new SimpleDateFormat("dd/MM/yyyy").format(txtFechaPermiso.getDate()));

			if(conpararFecha.getFecha().trim().equals("FECHA_PASADA")){
				JOptionPane.showMessageDialog(null, "           Solo Puede Editar Un Permiso \n             Con Fecha Actual o Futura", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
				return;
			}else{
				Campos_True();
				btnGuardar.setEnabled(true);
				btnEditar.setEnabled(false);
				txaMotivo.requestFocus();
				btnFiltroEmpleado.setEnabled(false);
				if(chbP_cambiodescanso.isSelected()){cmbDias.setEnabled(true);}
				if(chbP_tiempoComida.isSelected()){spComida.setEnabled(true);}
				
			}
		}
	};
	
	ActionListener opFiltro = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			btnDeshacer.doClick();
			new Filtro_Permisos_Checador().setVisible(true);
		}
	};
	
	ActionListener opFiltroEmpleados = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			tipo_filtro_empleado=1;
			new Filtro_Permiso_Empleado().setVisible(true);
			
		}
	};
	
	ActionListener opFiltroEmpleadosTurno = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			tipo_filtro_empleado=2;
			new Filtro_Permiso_Empleado().setVisible(true);
		}
	};
	
	ActionListener opNuevo = new ActionListener() {
		@SuppressWarnings("deprecation")
		public void actionPerformed(ActionEvent arg0) {
			btnDeshacer.doClick();
			txtFolio.setText(new Obj_Alimentacion_De_Permisos_A_Empleados().nuevoPermiso()+"");
			btnBuscar.setEnabled(false);
			btnFiltro.setEnabled(false);
			
			spComida.setValue(new Time(Integer.parseInt(comida[0]),Integer.parseInt(comida[1]),Integer.parseInt(comida[2])));
			
			Campos_True();
			cmbStatus.setEnabled(false);
			cmbStatus.setSelectedIndex(0);
			txtFolio.setEditable(false);
			btnFiltroEmpleado.doClick();
		}
	};
	
	ActionListener funcion_chbs = new ActionListener() {
		@SuppressWarnings("deprecation")
		public void actionPerformed(ActionEvent arg0) {
			
			if(chbP_cambiodescanso.isSelected()){
				 cmbDias.setEnabled(true);
			 }else{
				 cmbDias.setSelectedIndex(0);
				 cmbDias.setEnabled(false);
			 }	
			
			if(chbP_tiempoComida.isSelected()){
				 spComida.setEnabled(true);
			 }else{
				 spComida.setValue(new Time(Integer.parseInt(comida[0]),Integer.parseInt(comida[1]),Integer.parseInt(comida[2])));
				 spComida.setEditor(com);
				 spComida.setEnabled(false);
			 }
			
			
			if(chbP_permisochecarOtroEstab.isSelected()){
				cmbEstablecimiento.setEnabled(true);
			}else{
				cmbEstablecimiento.setSelectedIndex(0);
				cmbEstablecimiento.setEnabled(false);
			}
				
		}
	};
	
	ActionListener opSalir = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			dispose();
		}
	};
	
	ActionListener opLimpiar = new ActionListener() {
		@SuppressWarnings("deprecation")
		public void actionPerformed(ActionEvent arg0) {

			lblEmpleado.setText("Empleado:");
			txtFolio.setText("");
			txtFolioEmpleado.setText("");
			txtFechaPermiso.setDate(null);
			txaMotivo.setText("");
			txtFolioEmpleadoCambio.setText("");
			txtEmpleadoCambio.setText("");
			txtEmpleadoHorario.setText("");
			
			gr_solicito.remove(rbEmpleado);
			gr_solicito.remove(rbEmpresa);
			rbEmpleado.setSelected(false);
			rbEmpresa.setSelected(false);
			gr_solicito.add(rbEmpleado);
			gr_solicito.add(rbEmpresa);
			
			
			resetPermisos();
			Campos_False();
			btnBuscar.setEnabled(true);
			btnFiltro.setEnabled(true);
			txtFechaPermiso.requestFocus();
			cmbDias.setSelectedIndex(0);
			spComida.setValue(new Time(Integer.parseInt(comida[0]),Integer.parseInt(comida[1]),Integer.parseInt(comida[2])));
			cmbStatus.setSelectedItem(0);
		}
	};
	
	public void Campos_False()
	{
		txtFolio.setEditable(false);
		btnFiltroEmpleado.setEnabled(false);
		txtFechaPermiso.setEnabled(false);
		cmbStatus.setEnabled(false);
		
		rbEmpleado.setEnabled(false);
		rbEmpresa.setEnabled(false);
		
		txaMotivo.setEditable(false);
		
		chbP_trabajarCorrido.setEnabled(false);
		chbP_salirTemprano.setEnabled(false);
		chbP_entrarTarde.setEnabled(false);
		chbP_noAsistir.setEnabled(false);
		chbP_noAsistir2.setEnabled(false);
		chbP_permisochecarOtroEstab.setEnabled(false);
		chbP_doblarExtra.setEnabled(false);
		chbP_tiempoComida.setEnabled(false);
		chbP_cambiodescanso.setEnabled(false);
		
		cmbDias.setEnabled(false);
		cmbEstablecimiento.setEnabled(false);
		
		spComida.setEnabled(false);
		txtFolioEmpleadoCambio.setEnabled(false);
		txtEmpleadoCambio.setEnabled(false);
		txtEmpleadoHorario.setEnabled(false);
		btnFiltroEmpleadoCambio.setEnabled(false);
		
		btnGuardar.setEnabled(false);
		btnEditar.setEnabled(false);
	}
	
	public void Campos_True()
	{
		btnFiltroEmpleado.setEnabled(true);
		txtFechaPermiso.setEnabled(true);
		cmbStatus.setEnabled(true);
		
		rbEmpleado.setEnabled(true);
		rbEmpresa.setEnabled(true);
		
		txaMotivo.setEditable(true);
		
		chbP_trabajarCorrido.setEnabled(true);
		chbP_salirTemprano.setEnabled(true);
		chbP_entrarTarde.setEnabled(true);
		chbP_noAsistir.setEnabled(true);
		chbP_noAsistir2.setEnabled(true);
		chbP_cambiodescanso.setEnabled(true);
		chbP_doblarExtra.setEnabled(true);
		chbP_tiempoComida.setEnabled(true);
		chbP_permisochecarOtroEstab.setEnabled(true);
		
		btnGuardar.setEnabled(true);
		txaMotivo.setEditable(true);
	}
	
//TODO Filtro Permisos Asignados
public class Filtro_Permisos_Checador extends JFrame{
	
	Container cont = getContentPane();
	JLayeredPane campo = new JLayeredPane();
	
	Connexion con = new Connexion();
	
	DefaultTableModel model = new DefaultTableModel(0,4){
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

	JTextField txtBuscar =  new Componentes().text(new JCTextField(), "Teclea El Nombre De La Persona Para Buscar En La Tabla", 200, "String");
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Filtro_Permisos_Checador()	{
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Filter-List-icon32.png"));
		this.setTitle("Filtro Permisos");
		this.setSize(600,570);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		txtBuscar.addKeyListener(new KeyAdapter() { 
			public void keyReleased(final KeyEvent e) { 
				new Obj_Filtro_Dinamico(tabla, "Empleado", txtBuscar.getText().toString().trim().toUpperCase(), "", "", "", "", "", "");
            } 
        });
	
		trsfiltro = new TableRowSorter(model); 
		tabla.setRowSorter(trsfiltro);  
		campo.add(getPanelTabla()).setBounds(10,70,570,450);
		agregar(tabla);
		campo.add(lblBuscar).setBounds(10,30,70,20);
		campo.add(txtBuscar).setBounds(65,30,350,20);
		cont.add(campo);
	}
	
	private void agregar(final JTable tbl) {
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
	        public void mouseClicked(MouseEvent e) {
	        	if(e.getClickCount() == 2){
	        		int fila = tabla.getSelectedRow();
	    			Object folio =  tabla.getValueAt(fila, 0).toString().trim();
	    			dispose();
	    			txtFolio.setText(folio+"");
	    			
	    			btnBuscar.doClick();
	        	}
	        }
        });
    }
	
	private JScrollPane getPanelTabla()	{		
		new Connexion();
		tabla.getTableHeader().setReorderingAllowed(false) ;
		tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		tabla.getColumnModel().getColumn(0).setHeaderValue("Folio");
		tabla.getColumnModel().getColumn(0).setMinWidth(40);
		tabla.getColumnModel().getColumn(1).setHeaderValue("Empleado");
		tabla.getColumnModel().getColumn(1).setMinWidth(230);
		tabla.getColumnModel().getColumn(2).setHeaderValue("Fecha de Permiso");
		tabla.getColumnModel().getColumn(2).setMinWidth(100);
		tabla.getColumnModel().getColumn(3).setHeaderValue("Capturo Permiso");
		tabla.getColumnModel().getColumn(3).setMinWidth(200);
		
	    tabla.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",9)); 	
	    tabla.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
	    tabla.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
	    tabla.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
	    
		Statement s;
		ResultSet rs;
		try {
			s = con.conexion().createStatement();
			rs = s.executeQuery("sp_select_permisos_checador_filtro_nivel_jerarquico "+usuario.getFolio()  );
			
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
}
	
//TODO Filtro COLABORADOR
public class Filtro_Permiso_Empleado extends JDialog{
	
	Container cont = getContentPane();
	JLayeredPane campo = new JLayeredPane();
	Connexion con = new Connexion();
	
	DefaultTableModel model = new DefaultTableModel(0,5){
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
	JTextField txtBuscar = new Componentes().text(new JCTextField(), "Teclee Aqui Para Buscar En La Tabla", 250, "String");
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Filtro_Permiso_Empleado()	{
		this.setModal(true);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Filter-List-icon32.png"));
		this.setTitle("Filtro Colaboradores Por Nivel Jerarquico");
		this.setSize(700,470);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		txtBuscar.addKeyListener(new KeyAdapter() {
			
			
		public void keyReleased(final KeyEvent e) { 
				int[] columnas = {0,1,2,3,4};
				new Obj_Filtro_Dinamico_Plus(tabla, txtBuscar.getText().toUpperCase(), columnas);
            } 
        });
	
		trsfiltro = new TableRowSorter(model); 
		tabla.setRowSorter(trsfiltro); 
		campo.add(txtBuscar).setBounds      (10,20,418,20);
		campo.add(getPanelTabla()).setBounds(10,40,680,390);

		
		cont.add(campo);
		agregar(tabla);
		tabla.addKeyListener(seleccionEmpleadoconteclado);
	}
	
	private void agregar(final JTable tbl) {
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
	        	if(e.getClickCount() == 2){
	        		int fila = tabla.getSelectedRow();
	    			String folio =  tabla.getValueAt(fila, 0).toString().trim();
	    			String nombre =  tabla.getValueAt(fila, 1).toString().trim();
	    			if(tipo_filtro_empleado==1){
	        			txtFolioEmpleado.setText(folio);
	        			lblEmpleado.setText("Empleado: "+nombre);
	        		
	        		}
	        		if(tipo_filtro_empleado==2){
	        			buscar_empleado_a_copiarle_turno(Integer.valueOf(folio));
	        		}
	    			dispose();
	    			txtFechaPermiso.requestFocus();
	        	}
	        }
        });
    }
	
	private JScrollPane getPanelTabla()	{		
		new Connexion();
		tabla.getTableHeader().setReorderingAllowed(false) ;
		tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tabla.getColumnModel().getColumn(0).setHeaderValue("Folio");
		tabla.getColumnModel().getColumn(0).setMaxWidth(45);
		tabla.getColumnModel().getColumn(1).setHeaderValue("Colaborador");
		tabla.getColumnModel().getColumn(1).setMinWidth(280);
		tabla.getColumnModel().getColumn(2).setHeaderValue("Establecimiento");
		tabla.getColumnModel().getColumn(2).setMinWidth(110);
		tabla.getColumnModel().getColumn(3).setHeaderValue("Departamento");
		tabla.getColumnModel().getColumn(3).setMinWidth(100);
		tabla.getColumnModel().getColumn(4).setHeaderValue("Puesto");
		tabla.getColumnModel().getColumn(4).setMinWidth(200);
		
	    tabla.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",9)); 	
	    tabla.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
	    tabla.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
	    tabla.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
	    tabla.getColumnModel().getColumn(4).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
	    
	    Statement s;
		ResultSet rs;
		try {
			s = con.conexion().createStatement();
			rs = s.executeQuery("exec sp_filtro_empleado_actividades_status_vigente "+usuario.getFolio() );
			
			while (rs.next())
			{ 
			   String [] fila = new String[5];
			   fila[0] = rs.getString(1).trim();
			   fila[1] = rs.getString(2).trim();
			   fila[2] = rs.getString(3).trim();
			   fila[3] = rs.getString(4).trim();
			   fila[4] = rs.getString(5).trim();
			   
			   model.addRow(fila); 
			}	
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		 JScrollPane scrol = new JScrollPane(tabla);
		   
	    return scrol; 
	}
	
	KeyListener seleccionEmpleadoconteclado = new KeyListener() {
		@SuppressWarnings("static-access")
		@Override
		public void keyTyped(KeyEvent e) {
			char caracter = e.getKeyChar();
			
			if(caracter==e.VK_ENTER){
			int fila=tabla.getSelectedRow()-1;
			String folio = tabla.getValueAt(fila,0).toString().trim();
				
			txtFolioEmpleado.setText(folio);
			btnBuscar.doClick();
			dispose();
			txtFechaPermiso.requestFocus();
			}
		}
		@Override
		public void keyPressed(KeyEvent e){}
		@Override
		public void keyReleased(KeyEvent e){}
								
	};
}

	public static void main(String [] args){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Alimentacion_De_Permisos_A_Colaboradores_Por_Nivel_Jerarquico().setVisible(true);
		}catch(Exception e){
			System.err.println("Error :"+ e.getMessage());
		}
	}
	
	public class Cat_SeleccionDeDobla extends JDialog {
		Container cont = getContentPane();
		JLayeredPane panel = new JLayeredPane();
		
		String establecimiento[] = new Obj_Establecimiento().Combo_Establecimiento();
		@SuppressWarnings({ "unchecked", "rawtypes" })
		JComboBox cmbEstablecimiento = new JComboBox(establecimiento);
		
		JButton btnDobla1 = new JButton();
		JButton btnDobla2 = new JButton();
		
		String filtro_establecimiento ="";
		
		public Cat_SeleccionDeDobla(){
			this.setModal(true);
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/user_icon&16.png"));
			this.setTitle("Seleccion de Tiempo de Dobla por No Tener Dia Que Dobla");
			panel.setBorder(BorderFactory.createTitledBorder("Seleccion de Opcion de Tiempo"));

			btnDobla1.setSelected(true);
			btnDobla1.setText(	"<html> <FONT FACE="+"arial"+" SIZE=4 COLOR=BLACk>" +
											"		<p>DOBLARA DE 7:00 A 23:00</p>" +
											"		<p>CON 2 HORAS DE COMIDA Y</p>" +
											"		<p>UN RECESO DE 15 MINUTOS.</p></FONT>" +
											"</html>"); 
			
			btnDobla2.setSelected(true);
			btnDobla2.setText(	"<html> <FONT FACE="+"arial"+" SIZE=4 COLOR=BLACk>" +
											"		<p>DOBLARA DE 6:00 A 23:00</p>" +
											"		<p>CON 3 HORAS DE COMIDA Y</p>" +
											"		<p>UN RECESO DE 15 MINUTOS.</p></FONT>" +
											"</html>"); 
			
			panel.add(btnDobla1).setBounds(20, 50, 300, 75);
			panel.add(btnDobla2).setBounds(140, 135, 300, 75);
			
			this.btnDobla1.addActionListener(op1);
			this.btnDobla2.addActionListener(op2);
			
			cont.add(panel);
			this.setSize(470, 300);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
		}
		
		ActionListener op1 = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				tiene_dia_dobla=2;
				btnGuardar.doClick();
				dispose();
			}
		};
		
		ActionListener op2 = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				tiene_dia_dobla=3;
				btnGuardar.doClick();
				dispose();
			}
		};
	}
}
