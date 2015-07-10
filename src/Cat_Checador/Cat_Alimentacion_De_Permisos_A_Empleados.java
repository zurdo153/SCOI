package Cat_Checador;

import java.awt.Component;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
import javax.swing.RowFilter;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;



import Conexiones_SQL.Connexion;
import Obj_Checador.Obj_Alimentacion_De_Permisos_A_Empleados;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.Componentes;

import com.toedter.calendar.JDateChooser;

@SuppressWarnings("serial")
public class Cat_Alimentacion_De_Permisos_A_Empleados extends JFrame {
	
	Container cont  = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JLabel lblFolio = new JLabel("Folio:");
	JLabel lblFolioEmpleado = new JLabel("Folio de Empleado:");
	JLabel lblFecha = new JLabel("Fecha de Permiso");
	JLabel lblMotivo = new JLabel("Motivo:");
	
	JLabel lblUsuario = new JLabel("Usuario: ");
	JLabel lblEmpleado = new JLabel("Empleado: ");
	
	JTextField txtFolio = new Componentes().text(new JTextField(), "Folio Permiso", 8, "String");
	JTextField txtFolioEmpleado = new JTextField();
	
	JDateChooser txtFechaPermiso = new JDateChooser();
	
	JCheckBox chb_status = new JCheckBox("status",true);
	
	JLabel lblSolicito = new JLabel();
	JRadioButton rbEmpleado = new JRadioButton("Empleado");
	JRadioButton rbEmpresa = new JRadioButton("Empresa");	
	ButtonGroup gr_solicito = new ButtonGroup();
	
	
	JCheckBox chbP_trabajarCorrido = new JCheckBox("1.- Permiso Para Trabajar Corrido");
	JCheckBox chbP_salirTemprano = new JCheckBox("2.- Permiso Para Salir Temprano");
	JCheckBox chbP_entrarTarde = new JCheckBox("3.- Permiso Para Entrar Tarde");
	JCheckBox chbP_noAsistir = new JCheckBox("4.- Permiso Para No Asistir (con goce de sueldo)");
	JCheckBox chbP_noAsistir2 = new JCheckBox("5.- Permiso Para No Asistir (sin goce de sueldo)");
	
	JCheckBox chbP_cambiodescanso = new JCheckBox("6.- Permiso para trabajar");
	JLabel lblP_cambiodescanso = new JLabel("como el dia: ");
	
	JCheckBox chbP_doblarExtra = new JCheckBox("7.- Permiso para doblar extra");
	
	 String[] dias = { "Seleccione un dia", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo" };
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	 private JComboBox cmbDias = new JComboBox(dias);
	 
	JCheckBox chbP_tiempoComida = new JCheckBox("8.- Permiso para cambiar tiempo de");
	JLabel lblP_tiempoComida = new JLabel("comida a: ");
	 
	ButtonGroup grupo = new ButtonGroup();
	
	//declarar variable en hora 00:00:00
	//crear spinner y asignarlo con la fecha y hora actual
	//en el constructor cambiamos la fecha con hora = 00:00:00
    //y despues mostramos solo la hora
	String[] comida ="0:00:00".split(":");
	SpinnerDateModel scom =  new SpinnerDateModel();
	  JSpinner spComida = new JSpinner(scom);                                         
	  	JSpinner.DateEditor  com = new JSpinner.DateEditor(spComida,"H:mm"); 
	  	
		JCheckBox chbCambio_turno = new JCheckBox("9.- Permiso para cambiar turno");
		JLabel lblCambio_turno = new JLabel("con el empleado: ");
		JButton btnFiltroEmpleadoCambio = new JButton(new ImageIcon("Iconos/zoom_icon&16.png"));
		JTextField txtFolioEmpleadoCambio = new JTextField();
		JTextField txtEmpleadoCambio = new JTextField();
		JTextField txtEmpleadoHorario= new JTextField();

	 
	JTextArea txaMotivo = new Componentes().textArea(new JTextArea(), "Motivo", 400);
	JScrollPane Observasiones = new JScrollPane(txaMotivo);
	
	JButton btnBuscar = new JButton(new ImageIcon("Iconos/zoom_icon&16.png"));
	JButton btnFiltro = new JButton(new ImageIcon("Iconos/filter_iconBlue&16.png"));
	JButton btnFiltroEmpleado = new JButton(new ImageIcon("Iconos/users_icon&16.png"));
	JButton btnNuevo = new JButton("Nuevo");//new ImageIcon("Iconos/generar_icon&16.png")
	JButton btnGuardar = new JButton("Guardar");
	JButton btnLimpiar = new JButton("Limpiar");
	JButton btnEditar = new JButton("Editar");
	JButton btnSalir = new JButton("Salir");
	
	
	
	Border border = LineBorder.createGrayLineBorder();
	
//	almacena el numero de permisos que se le asignara al empleado
	int permiso=0;
//	almacena el numero de dia de descanso que se le asignara al empleado
	int descanso=0;
	
//	almacena el folio del usuario que entro al sistema para mandarsela guardar a la tabla de permisos
	int folio_usuario=0;
	
//	cuando entra al filtro trae un valor de parametro y se almacena en esta para ver si asignara empleado al permiso
//	o si lo asignara al campo de empleado del turno
	int tipo_filtro_empleado=0;
	
// se utiliza cuando es un permiso 7  para los dias dobla
	int tiene_dia_dobla=0;
	
	@SuppressWarnings("deprecation")
	public void getConstructor(){

		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/cuadrante_user_icon&16.png"));
		panel.setBorder(BorderFactory.createTitledBorder("Permisos Checador"));
		this.setTitle("Permisos Checador");
		
		lblSolicito.setBorder(BorderFactory.createTitledBorder("Solicit�: "));
		
		txaMotivo.setBorder(border);
		
		this.txtFechaPermiso.setIcon(new ImageIcon("Iconos/calendar_icon&16.png"));
		
		btnBuscar.setToolTipText("Buscar Empleado");
		btnFiltro.setToolTipText("Filtro de Empleado");
		btnNuevo.setToolTipText("Crear Permiso");
		
		int y=20;
		
		panel.add(lblUsuario).setBounds(20,y,400,20);
		
		panel.add(lblFolio).setBounds(10,y+=35,30,20);
		panel.add(txtFolio).setBounds(40,y,60,20);
		panel.add(btnBuscar).setBounds(100,y,20,20);
		panel.add(btnFiltro).setBounds(120,y,20,20);
		
		panel.add(lblFolioEmpleado).setBounds(150,y,110,20);
		panel.add(txtFolioEmpleado).setBounds(240,y,60,20);
		panel.add(btnFiltroEmpleado).setBounds(300,y,20,20);
		
		panel.add(txtFechaPermiso).setBounds(340,y,100,20);
		panel.add(chb_status).setBounds(450,y,80,20);
		
		panel.add(lblSolicito).setBounds(530,y-13,190,40);
		panel.add(rbEmpleado).setBounds(550,y,80,20);
		panel.add(rbEmpresa).setBounds(630,y,80,20);
		
		panel.add(lblEmpleado).setBounds(20,y+=35,400,20);
		
		panel.add(chbP_trabajarCorrido).setBounds(20,y+=35,200,20);
		panel.add(chbP_salirTemprano).setBounds(20,y+=35,200,20);
		panel.add(chbP_entrarTarde).setBounds(20,y+=35,200,20);
		panel.add(chbP_noAsistir).setBounds(20,y+=35,260,20);
		panel.add(chbP_noAsistir2).setBounds(20,y+=35,260,20);
		
		panel.add(btnNuevo).setBounds(30,y+=100,80,20);
		panel.add(btnGuardar).setBounds(120,y,80,20);
		panel.add(btnEditar).setBounds(210,y,80,20);
		panel.add(btnLimpiar).setBounds(300,y,80,20);
		panel.add(btnSalir).setBounds(390,y,80,20);
		
		y=125;
		panel.add(chbP_cambiodescanso).setBounds(285,y,210,20);
		panel.add(lblP_cambiodescanso).setBounds(305,y+=35,70,20);
		panel.add(cmbDias).setBounds(370,y,110,20);
		
		panel.add(chbP_doblarExtra).setBounds(285,y+=35,210,20);
		
		panel.add(chbP_tiempoComida).setBounds(285,y+=35,210,20);
		panel.add(lblP_tiempoComida).setBounds(305,y+=35,70,20);
		panel.add(spComida).setBounds(360,y,60,20);
		
		y=125;
//		panel.add(chbCambio_turno).setBounds(500,y,250,20);
//		panel.add(lblCambio_turno).setBounds(520,y+=20,90,20);
//		panel.add(txtFolioEmpleadoCambio).setBounds(610,y,70,20);
//		panel.add(btnFiltroEmpleadoCambio).setBounds(680,y,20,20);
//		panel.add(txtEmpleadoCambio).setBounds(500,y+=22,260,20);
//		panel.add(txtEmpleadoHorario).setBounds(500,y+=22,260,20);
		
		panel.add(lblMotivo).setBounds(500,y+=40,80,20);
		panel.add(Observasiones).setBounds(500,y+=20,260,140);
		
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
		
		grupo.add(chbP_doblarExtra);
		grupo.add(chbP_tiempoComida);
		
		grupo.add(chbCambio_turno);

		txtFolio.addKeyListener(validaNumerico);
		
		btnGuardar.addActionListener(guardar);
		btnBuscar.addActionListener(opBuscar);
		btnNuevo.addActionListener(opNuevo);
		btnSalir.addActionListener(opSalir);
		btnLimpiar.addActionListener(opLimpiar);
		btnEditar.addActionListener(opEditar);
		btnFiltro.addActionListener(opFiltro);
		btnFiltroEmpleado.addActionListener(opFiltroEmpleados);
		btnFiltroEmpleadoCambio.addActionListener(opFiltroEmpleadosTurno);
		
		//pa
		chbP_cambiodescanso.addActionListener(funcion_chbs);
		chbP_entrarTarde.addActionListener(funcion_chbs);
        chbP_noAsistir.addActionListener(funcion_chbs);
        chbP_noAsistir2.addActionListener(funcion_chbs);
		chbP_salirTemprano.addActionListener(funcion_chbs);
		chbP_trabajarCorrido.addActionListener(funcion_chbs);
		
		chbP_doblarExtra.addActionListener(funcion_chbs);
		chbP_tiempoComida.addActionListener(funcion_chbs);
		
		chbCambio_turno.addActionListener(funcion_chbs);
		
		txtFolio.addKeyListener(buscaAction);
		
		Campos_False();
		txtFolioEmpleado.setEditable(false);
		txtFolio.setEditable(true);
		btnGuardar.setEnabled(false);
		btnEditar.setEnabled(false);
		CargarCajero();
		
		cont.add(panel);
		this.setSize(800,440);
		
		this.setLocationRelativeTo(null);
		
//      abre el filtro del permiso guardado
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
           KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), "filtro");
        
        getRootPane().getActionMap().put("filtro", new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                            btnFiltro.doClick();
            }
        });
        
//      abre el filtro de busqueda de el empleado
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
           KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0), "filtroempleado");
        
        getRootPane().getActionMap().put("filtroempleado", new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
            	btnFiltroEmpleado.doClick();
            }
        });
	}
	
	public Cat_Alimentacion_De_Permisos_A_Empleados(){
		 getConstructor();
	}
	
	KeyListener buscaAction = new KeyListener() {
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
	
 	KeyListener validaNumerico = new KeyListener() {
		public void keyTyped(KeyEvent e){
			char caracter = e.getKeyChar();

			if(((caracter < '0') ||
		        (caracter > '9')) &&
		        (caracter != KeyEvent.VK_BACK_SPACE)){
		    	e.consume(); 
		    }
		}
		public void keyReleased(KeyEvent e) {	
		}
		public void keyPressed(KeyEvent e) {}
	};
	
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
				&& chbCambio_turno.isSelected()==false) //pb despues del ultimo par &&
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
		if(chbCambio_turno.isSelected()){permiso=9;}
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
	
//	ActionListener guardar = new ActionListener(){
//		@SuppressWarnings("deprecation")
//		public void actionPerformed(ActionEvent e){
//			
//			if(txtFolio.getText().equals("")){
//				JOptionPane.showMessageDialog(null, "El Folio Es Requerido", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
//				return;
//			}else{
//				if(ValidaCampos().equals("")){
//					
//					Obj_Alimentacion_De_Permisos_A_Empleados conpararFecha = new Obj_Alimentacion_De_Permisos_A_Empleados().ComparacionFecha(new SimpleDateFormat("dd/MM/yyyy").format(txtFechaPermiso.getDate()));
//					
//					if(conpararFecha.getFecha().trim().equals("FECHA_PASADA")){
//						JOptionPane.showMessageDialog(null, "No Puede Asignar Permiso A Una Fecha Que Ya Paso", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
//						return;
//					}else{
//
//						Obj_Alimentacion_De_Permisos_A_Empleados Permiso = new Obj_Alimentacion_De_Permisos_A_Empleados().buscar(Integer.parseInt(txtFolio.getText()));
//							
//							permisoChecador();
//							 dia_selecionado_p_descanso();
//							 if(chbP_cambiodescanso.isSelected() && descanso==0){
//								 JOptionPane.showMessageDialog(null,"Seleccione dia como el que trabajara","Aviso",JOptionPane.WARNING_MESSAGE);
//								 return;
//							 }
//							 if(chbCambio_turno.isSelected() && txtFolioEmpleadoCambio.getText().equals("")){
//								 JOptionPane.showMessageDialog(null,"Seleccione el empleado del cual optendra el turno","Aviso",JOptionPane.WARNING_MESSAGE);
//								 return;
//							 }else{
//								 
//								 if(Permiso.getFolio() == Integer.parseInt(txtFolio.getText())){
//										if(JOptionPane.showConfirmDialog(null, "El registro ya existe, �desea cambiarlo?") == 0){
//											
//											Permiso.setFolio(Integer.parseInt(txtFolio.getText()));
//											Permiso.setFolio_empleado(Integer.parseInt(txtFolioEmpleado.getText()));
//											Permiso.setFolio_usuario(folio_usuario);
//											Permiso.setFecha(new SimpleDateFormat("dd/MM/yyyy").format(txtFechaPermiso.getDate()));
//											
//											Permiso.setTipo_de_permiso(permiso);
//											Permiso.setDescanso(descanso);
//											
//											SimpleDateFormat sdf = new SimpleDateFormat ("H:mm");
//											Permiso.setTiempo_comida(sdf.format ((Date) spComida.getValue()));
//											
//											Permiso.setFolio_empleado_optener_turno(Integer.valueOf(txtFolioEmpleadoCambio.getText()));
//											
//											Permiso.setStatus(chb_status.isSelected());
//											Permiso.setMotivo(txaMotivo.getText().toUpperCase());
//				
//											if(Permiso.actualizar(Integer.parseInt(txtFolio.getText()))){
//												
//												cmbDias.setSelectedIndex(0);
//												spComida.setValue(new Time(Integer.parseInt(comida[0]),Integer.parseInt(comida[1]),Integer.parseInt(comida[2])));
//												
//												lblEmpleado.setText("Empleado:");
//												btnGuardar.setEnabled(false);
//												btnEditar.setEnabled(true);
//												txtFolio.setText("");
//												txtFolioEmpleado.setText("");
//												txtFechaPermiso.setDate(null);
//												txaMotivo.setText("");
//												
//												Campos_False();
//												txtFolio.setEditable(true);
//												txtFolio.requestFocus();
//													JOptionPane.showMessageDialog(null,"El Registro se actualizo Exitosamente!","Aviso",JOptionPane.INFORMATION_MESSAGE);
//													return;
//											}else{
//												JOptionPane.showMessageDialog(null,"El Registro no se a actualizado!","Error",JOptionPane.ERROR_MESSAGE);
//												return;
//											}
//										}
//								}else{
//									
//									Permiso.setFolio(Integer.parseInt(txtFolio.getText()));
//									Permiso.setFolio_empleado(Integer.parseInt(txtFolioEmpleado.getText()));
//									Permiso.setFolio_usuario(folio_usuario);
//									Permiso.setFecha(new SimpleDateFormat("dd/MM/yyyy").format(txtFechaPermiso.getDate()));
//									
//									Permiso.setTipo_de_permiso(permiso);
//									Permiso.setStatus(chb_status.isSelected());
//									Permiso.setDescanso(descanso);
//									
//									Permiso.setMotivo(txaMotivo.getText().toUpperCase());
//									
//									SimpleDateFormat sdf = new SimpleDateFormat ("H:mm");
//									Permiso.setTiempo_comida(sdf.format ((Date) spComida.getValue()));
//									
//									Permiso.setFolio_empleado_optener_turno(Integer.valueOf(txtFolioEmpleadoCambio.getText()));
//
//									if(Permiso.guardar_permiso(tiene_dia_dobla)){
//										
//										cmbDias.setSelectedIndex(0);
//										spComida.setValue(new Time(Integer.parseInt(comida[0]),Integer.parseInt(comida[1]),Integer.parseInt(comida[2])));
//										
//										lblEmpleado.setText("Empleado:");
//										btnGuardar.setEnabled(false);
//										btnEditar.setEnabled(true);
//										Campos_False();
//										txtFolio.setText("");
//										txtFolioEmpleado.setText("");
//										txtFechaPermiso.setDate(null);
//										txaMotivo.setText("");
//										txtFolio.setEditable(true);
//										txtFolio.requestFocus();
//												JOptionPane.showMessageDialog(null,"El Registro se guardo Exitosamente!","Aviso",JOptionPane.INFORMATION_MESSAGE);
//												return;
//											}else{
//										JOptionPane.showMessageDialog(null,"El Registro no se a guardado!","Error",JOptionPane.ERROR_MESSAGE);
//										return;
//									}
//								}
//							}
//						}
//				}else{
//					JOptionPane.showMessageDialog(null, "los siguientes campos son requeridos: \n"+ValidaCampos(),"Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
//					return;
//				}
// 			}
//		}
//	};
//	
//	ActionListener guardar = new ActionListener() {
//		public void actionPerformed(ActionEvent arg0) {
//			System.out.println("entro");
//			if(permiso==7){
//				System.out.println("permiso 7");
//				if(new Obj_Alimentacion_De_Permisos_A_Empleados().buscar_doblada(Integer.parseInt(txtFolioEmpleado.getText()))==2){
//					
//						JOptionPane.showMessageDialog(null,"El Empleado Ya Uso Todos Sus Dias De Dobla!","Aviso",JOptionPane.WARNING_MESSAGE); 
//						return;
//					
//				}else{
//					
//						if(new Obj_Alimentacion_De_Permisos_A_Empleados().b_doblada(Integer.parseInt(txtFolioEmpleado.getText()))){
//							System.out.println("guardar con 1");
//							guardado(1);
//						}else{
//							System.out.println("abrir catalogo");
//							new Cat_SeleccionDeDobla().setVisible(true);
//						}
//					
//				}
//			}
//		}
//	};
//	
//	 @SuppressWarnings("deprecation")
//	public void guardado(int dia_dobla){
//			
//			if(txtFolio.getText().equals("")){
//				JOptionPane.showMessageDialog(null, "El Folio Es Requerido", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
//				return;
//			}else{
//				if(ValidaCampos().equals("")){
//					
//					Obj_Alimentacion_De_Permisos_A_Empleados conpararFecha = new Obj_Alimentacion_De_Permisos_A_Empleados().ComparacionFecha(new SimpleDateFormat("dd/MM/yyyy").format(txtFechaPermiso.getDate()));
//					
//					if(conpararFecha.getFecha().trim().equals("FECHA_PASADA")){
//						JOptionPane.showMessageDialog(null, "No Puede Asignar Permiso A Una Fecha Que Ya Paso", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
//						return;
//					}else{
//
//						Obj_Alimentacion_De_Permisos_A_Empleados Permiso = new Obj_Alimentacion_De_Permisos_A_Empleados().buscar(Integer.parseInt(txtFolio.getText()));
//							
//							permisoChecador();
//							 dia_selecionado_p_descanso();
//							 if(chbP_cambiodescanso.isSelected() && descanso==0){
//								 JOptionPane.showMessageDialog(null,"Seleccione dia como el que trabajara","Aviso",JOptionPane.WARNING_MESSAGE);
//								 return;
//							 }
//							 if(chbCambio_turno.isSelected() && txtFolioEmpleadoCambio.getText().equals("")){
//								 JOptionPane.showMessageDialog(null,"Seleccione el empleado del cual optendra el turno","Aviso",JOptionPane.WARNING_MESSAGE);
//								 return;
//							 }else{
//								 
//								 if(Permiso.getFolio() == Integer.parseInt(txtFolio.getText())){
//										if(JOptionPane.showConfirmDialog(null, "El registro ya existe, �desea cambiarlo?") == 0){
//											
//											Permiso.setFolio(Integer.parseInt(txtFolio.getText()));
//											Permiso.setFolio_empleado(Integer.parseInt(txtFolioEmpleado.getText()));
//											Permiso.setFolio_usuario(folio_usuario);
//											Permiso.setFecha(new SimpleDateFormat("dd/MM/yyyy").format(txtFechaPermiso.getDate()));
//											
//											Permiso.setTipo_de_permiso(permiso);
//											Permiso.setDescanso(descanso);
//											
//											SimpleDateFormat sdf = new SimpleDateFormat ("H:mm");
//											Permiso.setTiempo_comida(sdf.format ((Date) spComida.getValue()));
//											
//											Permiso.setFolio_empleado_optener_turno(Integer.valueOf(txtFolioEmpleadoCambio.getText()));
//											
//											Permiso.setStatus(chb_status.isSelected());
//											Permiso.setMotivo(txaMotivo.getText().toUpperCase());
//				
//											if(Permiso.actualizar(Integer.parseInt(txtFolio.getText()))){
//												
//												cmbDias.setSelectedIndex(0);
//												
//												lblEmpleado.setText("Empleado:");
//												btnGuardar.setEnabled(false);
//												btnEditar.setEnabled(true);
//												txtFolio.setText("");
//												txtFolioEmpleado.setText("");
//												txtFechaPermiso.setDate(null);
//												txaMotivo.setText("");
//												
//												Campos_False();
//												txtFolio.setEditable(true);
//												txtFolio.requestFocus();
//													JOptionPane.showMessageDialog(null,"El Registro se actualizo Exitosamente!","Aviso",JOptionPane.INFORMATION_MESSAGE);
//													return;
//											}else{
//												JOptionPane.showMessageDialog(null,"El Registro no se a actualizado!","Error",JOptionPane.ERROR_MESSAGE);
//												return;
//											}
//										}
//								}else{
//									
//									Permiso.setFolio(Integer.parseInt(txtFolio.getText()));
//									Permiso.setFolio_empleado(Integer.parseInt(txtFolioEmpleado.getText()));
//									Permiso.setFolio_usuario(folio_usuario);
//									Permiso.setFecha(new SimpleDateFormat("dd/MM/yyyy").format(txtFechaPermiso.getDate()));
//									
//									Permiso.setTipo_de_permiso(permiso);
//									Permiso.setStatus(chb_status.isSelected());
//									Permiso.setDescanso(descanso);
//									
//									Permiso.setMotivo(txaMotivo.getText().toUpperCase());
//									
//									SimpleDateFormat sdf = new SimpleDateFormat ("H:mm");
//									Permiso.setTiempo_comida(sdf.format ((Date) spComida.getValue()));
//									
//									Permiso.setFolio_empleado_optener_turno(Integer.valueOf(txtFolioEmpleadoCambio.getText()));
//
//									if(Permiso.guardar_permiso(dia_dobla)){
//										
//										cmbDias.setSelectedIndex(0);
//										spComida.setValue(new Time(Integer.parseInt(comida[0]),Integer.parseInt(comida[1]),Integer.parseInt(comida[2])));
//										
//										lblEmpleado.setText("Empleado:");
//										btnGuardar.setEnabled(false);
//										btnEditar.setEnabled(true);
//										Campos_False();
//										txtFolio.setText("");
//										txtFolioEmpleado.setText("");
//										txtFechaPermiso.setDate(null);
//										txaMotivo.setText("");
//										txtFolio.setEditable(true);
//										txtFolio.requestFocus();
//												JOptionPane.showMessageDialog(null,"El Registro se guardo Exitosamente!","Aviso",JOptionPane.INFORMATION_MESSAGE);
//												return;
//											}else{
//										JOptionPane.showMessageDialog(null,"El Registro no se a guardado!","Error",JOptionPane.ERROR_MESSAGE);
//										return;
//									}
//								}
//							}
//						}
//				}else{
//					JOptionPane.showMessageDialog(null, "los siguientes campos son requeridos: \n"+ValidaCampos(),"Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
//					return;
//				}
//			}
//		
//	 }
	 
	 
	 public void resetPermisos(){
		 
			grupo.remove(chbP_trabajarCorrido);
			grupo.remove(chbP_salirTemprano  );
			grupo.remove(chbP_entrarTarde 	 );
			grupo.remove(chbP_noAsistir 	 );
			grupo.remove(chbP_noAsistir2 	 );
			grupo.remove(chbP_cambiodescanso );
			grupo.remove(chbP_doblarExtra 	 );
			grupo.remove(chbP_tiempoComida 	 );
			
			chbP_trabajarCorrido.setSelected(false);
			chbP_salirTemprano  .setSelected(false);
			chbP_entrarTarde 	.setSelected(false);
			chbP_noAsistir 	    .setSelected(false);
			chbP_noAsistir2 	.setSelected(false);
			chbP_cambiodescanso .setSelected(false);
            chbP_doblarExtra 	.setSelected(false);
			chbP_tiempoComida 	.setSelected(false);
			
			grupo.add(chbP_trabajarCorrido);
			grupo.add(chbP_salirTemprano  );
			grupo.add(chbP_entrarTarde 	  );
			grupo.add(chbP_noAsistir 	  );
			grupo.add(chbP_noAsistir2 	  );
			grupo.add(chbP_cambiodescanso );
			grupo.add(chbP_doblarExtra 	  );
			grupo.add(chbP_tiempoComida   );
			
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
					JOptionPane.showMessageDialog(null, "El Folio Es Requerido", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
					return;
				}else{
					if(ValidaCampos().equals("")){
						
						Obj_Alimentacion_De_Permisos_A_Empleados conpararFecha = new Obj_Alimentacion_De_Permisos_A_Empleados().ComparacionFecha(new SimpleDateFormat("dd/MM/yyyy").format(txtFechaPermiso.getDate()));
						
						if(conpararFecha.getFecha().trim().equals("FECHA_PASADA")){
							JOptionPane.showMessageDialog(null, "No Puede Asignar Permiso A Una Fecha Que Ya Paso", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
							return;
						}else{
								permisoChecador();
								 dia_selecionado_p_descanso();
								 if(chbP_cambiodescanso.isSelected() && descanso==0){
									 JOptionPane.showMessageDialog(null,"Seleccione dia como el que trabajara","Aviso",JOptionPane.WARNING_MESSAGE);
									 return;
								 }
								 if(chbCambio_turno.isSelected() && txtFolioEmpleadoCambio.getText().equals("")){
									 JOptionPane.showMessageDialog(null,"Seleccione el empleado del cual optendra el turno","Aviso",JOptionPane.WARNING_MESSAGE);
									 return;
								 }else{
									 
									 if(Permiso.getFolio() == Integer.parseInt(txtFolio.getText())){
											if(JOptionPane.showConfirmDialog(null, "El registro ya existe, �desea cambiarlo?") == 0){
												
												Permiso.setFolio(Integer.parseInt(txtFolio.getText()));
												Permiso.setFolio_empleado(Integer.parseInt(txtFolioEmpleado.getText()));
												Permiso.setFolio_usuario(folio_usuario);
												Permiso.setFecha(new SimpleDateFormat("dd/MM/yyyy").format(txtFechaPermiso.getDate()));
												Permiso.setTipo_de_permiso(permiso);
												Permiso.setDescanso(descanso);
												SimpleDateFormat sdf = new SimpleDateFormat ("H:mm");
												Permiso.setTiempo_comida(sdf.format ((Date) spComida.getValue()));
												if (permiso==9){
													Permiso.setFolio_empleado_optener_turno(Integer.valueOf(txtFolioEmpleadoCambio.getText()));
													}
												Permiso.setStatus(chb_status.isSelected());
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
													txaMotivo.setText("");
													
													Campos_False();
													tiene_dia_dobla=0;
													txtFolio.setEditable(true);
													txtFolio.requestFocus();
														JOptionPane.showMessageDialog(null,"El Registro se actualizo Exitosamente!","Aviso",JOptionPane.INFORMATION_MESSAGE);
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
																			
																				JOptionPane.showMessageDialog(null,"El Empleado Ya Uso Sus 2 Dias De Dobla Extra!","Aviso",JOptionPane.WARNING_MESSAGE); 
																				return;
																				}else{
																			
																							if(new Obj_Alimentacion_De_Permisos_A_Empleados().b_doblada(Integer.parseInt(txtFolioEmpleado.getText()))){
																						//////////guardado dia dobla extra copiado del dia que dobla
																								tiene_dia_dobla=1;
																								guardar();
																							}else{	
																								  new Cat_SeleccionDeDobla().setVisible(true); 
																							     }
//																                     }
																              }
										              }
										
									   }
								}
							}
					}else{
						JOptionPane.showMessageDialog(null, "los siguientes campos son requeridos: \n"+ValidaCampos(),"Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
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
			Permiso.setFolio_usuario(folio_usuario);
			Permiso.setFecha(new SimpleDateFormat("dd/MM/yyyy").format(txtFechaPermiso.getDate()));
			Permiso.setTipo_de_permiso(permiso);
			Permiso.setStatus(chb_status.isSelected());
			Permiso.setDescanso(descanso);
			Permiso.setMotivo(txaMotivo.getText().toUpperCase());
			SimpleDateFormat sdf = new SimpleDateFormat ("H:mm");
			Permiso.setTiempo_comida(sdf.format ((Date) spComida.getValue()));
			if (permiso==9){
			Permiso.setFolio_empleado_optener_turno(Integer.valueOf(txtFolioEmpleadoCambio.getText()));
			}
			Permiso.setSolicito(rbEmpresa.isSelected()?1:0);
			
			if(Permiso.guardar_permiso(tiene_dia_dobla)){
				
				resetSolicitantes();
				resetPermisos();
				
				cmbDias.setSelectedIndex(0);
				spComida.setValue(new Time(Integer.parseInt(comida[0]),Integer.parseInt(comida[1]),Integer.parseInt(comida[2])));
				lblEmpleado.setText("Empleado:");
				btnGuardar.setEnabled(false);
				btnEditar.setEnabled(true);
				Campos_False();
				txtFolio.setText("");
				txtFolioEmpleado.setText("");
				txtFechaPermiso.setDate(null);
				txaMotivo.setText("");
				tiene_dia_dobla=0;
				txtFolio.setEditable(true);
				txtFolio.requestFocus();
				btnFiltro.setEnabled(true);
				btnBuscar.setEnabled(true);
						JOptionPane.showMessageDialog(null,"El Permiso se guardo Exitosamente!","Aviso",JOptionPane.INFORMATION_MESSAGE);
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
					
					switch(permisoEmp.getTipo_de_permiso()){
						case 1:chbP_trabajarCorrido.setSelected(true);break;
						case 2:chbP_salirTemprano.setSelected(true);break;
						case 3:chbP_entrarTarde.setSelected(true);break;
						case 4:chbP_noAsistir.setSelected(true);break;
						
						case 5:chbP_noAsistir2.setSelected(true);break;
						case 6:chbP_cambiodescanso.setSelected(true);break;
						case 7:chbP_doblarExtra.setSelected(true);break;
						case 8:chbP_tiempoComida.setSelected(true);break;
						
						case 9:	chbCambio_turno.setSelected(true);	
								txtFolioEmpleadoCambio.setText(permisoEmp.getFolio_empleado_optener_turno()+"");
								buscar_empleado_a_copiarle_turno(permisoEmp.getFolio_empleado_optener_turno());
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
					chb_status.setSelected(permisoEmp.isStatus());
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
			
			txtFolio.setText(new Obj_Alimentacion_De_Permisos_A_Empleados().nuevoPermiso()+"");
			btnBuscar.setEnabled(false);
			btnFiltro.setEnabled(false);
			
			spComida.setValue(new Time(Integer.parseInt(comida[0]),Integer.parseInt(comida[1]),Integer.parseInt(comida[2])));
			
			Campos_True();
			txtFolio.setEditable(false);
		}
	};
	
	ActionListener funcion_chbs = new ActionListener() {
		@SuppressWarnings("deprecation")
		public void actionPerformed(ActionEvent arg0) {
			
			if(chbP_cambiodescanso.isSelected()){
				lblP_cambiodescanso.setEnabled(true);
				 cmbDias.setEnabled(true);
			 }else{
				 cmbDias.setSelectedIndex(0);
				 lblP_cambiodescanso.setEnabled(false);
				 cmbDias.setEnabled(false);
			 }	
			
			if(chbP_tiempoComida.isSelected()){
				lblP_tiempoComida.setEnabled(true);
				 spComida.setEnabled(true);
			 }else{
				 spComida.setValue(new Time(Integer.parseInt(comida[0]),Integer.parseInt(comida[1]),Integer.parseInt(comida[2])));
				 spComida.setEditor(com);
				 lblP_tiempoComida.setEnabled(false);
				 spComida.setEnabled(false);
			 }
			
			if(chbCambio_turno.isSelected()){
				lblCambio_turno.setEnabled(true);
				btnFiltroEmpleadoCambio.setEnabled(true);
			 }else{
				 lblCambio_turno.setEnabled(false);
				 txtFolioEmpleadoCambio.setText("");
				 txtEmpleadoCambio.setText("");
				 txtEmpleadoHorario.setText("");
				 
				 btnFiltroEmpleadoCambio.setEnabled(false);
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
			
			Campos_False();
			
			btnBuscar.setEnabled(true);
			btnFiltro.setEnabled(true);
			txtFolio.setEditable(true);
			txtFolio.requestFocus();
			
			cmbDias.setSelectedIndex(0);
			spComida.setValue(new Time(Integer.parseInt(comida[0]),Integer.parseInt(comida[1]),Integer.parseInt(comida[2])));
		}
	};
	
	public void Campos_False()
	{
		txtFolio.setEditable(false);
		btnFiltroEmpleado.setEnabled(false);
		txtFechaPermiso.setEnabled(false);
		chb_status.setEnabled(false);
		
		rbEmpleado.setEnabled(false);
		rbEmpresa.setEnabled(false);
		
		txaMotivo.setEditable(false);
		chbP_trabajarCorrido.setEnabled(false);
		chbP_salirTemprano.setEnabled(false);
		chbP_entrarTarde.setEnabled(false);
		chbP_noAsistir.setEnabled(false);
		chbP_noAsistir2.setEnabled(false);

		chbP_cambiodescanso.setEnabled(false);
		
		lblP_cambiodescanso.setEnabled(false);
		cmbDias.setEnabled(false);
		
		chbP_doblarExtra.setEnabled(false);
		
		chbP_tiempoComida.setEnabled(false);
		lblP_tiempoComida.setEnabled(false);
		spComida.setEnabled(false);
		
		chbCambio_turno.setEnabled(false);
		lblCambio_turno.setEnabled(false);
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
		chb_status.setEnabled(true);
		
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
		
		chbCambio_turno.setEnabled(true);
		
		btnGuardar.setEnabled(true);
		txaMotivo.setEditable(true);
	}
	
	public void CargarCajero()
	{
		  File archivo = null;
 	      FileReader fr = null;
 	      BufferedReader br = null;
		 try {
 	         archivo = new File ("Config/users");
 	         fr = new FileReader (archivo);
 	         br = new BufferedReader(fr);
 	         String linea;
 	         
 	        folio_usuario=Integer.parseInt(br.readLine());
 	         while((linea=br.readLine())!=null){
 	        	lblUsuario.setText("Usuario: "+linea);
 	         }
 	      }
 	      catch(Exception e){
 	         e.printStackTrace();
 	      }finally{
 	         try{                   
 	            if( null != fr ){  
 	               fr.close();    
 	            }                 
 	         }catch (Exception e2){
 	            e2.printStackTrace();
 	         }
 	      }
	}
	
//Filtro Permisos Asignados
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

	JTextField txtBuscar =  new Componentes().text(new JTextField(), "Buscar", 100, "String");
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Filtro_Permisos_Checador()	{
		this.setTitle("Filtro Permisos");
		
		txtBuscar.addKeyListener(new KeyAdapter() { 
			public void keyReleased(final KeyEvent e) { 
                filtro(); 
            } 
        });
	
		trsfiltro = new TableRowSorter(model); 
		tabla.setRowSorter(trsfiltro);  
		
		campo.add(getPanelTabla()).setBounds(10,70,570,450);
		
		agregar(tabla);
		
		campo.add(lblBuscar).setBounds(30,30,70,20);
		campo.add(txtBuscar).setBounds(95,30,215,20);
		
		cont.add(campo);
		
		this.setSize(600,570);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
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
	
	@SuppressWarnings("unchecked")
	public void filtro() { 
			trsfiltro.setRowFilter(RowFilter.regexFilter(txtBuscar.getText().toUpperCase().trim(), 1));
	}  
	private JScrollPane getPanelTabla()	{		
		new Connexion();
		
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
		tcr.setHorizontalAlignment(SwingConstants.CENTER);
		
		tabla.getColumnModel().getColumn(0).setCellRenderer(tcr);
		
		tabla.getColumnModel().getColumn(0).setHeaderValue("Folio");
		tabla.getColumnModel().getColumn(0).setMaxWidth(40);
		tabla.getColumnModel().getColumn(0).setMinWidth(40);
		tabla.getColumnModel().getColumn(1).setHeaderValue("Empleado");
		tabla.getColumnModel().getColumn(1).setMaxWidth(230);
		tabla.getColumnModel().getColumn(1).setMinWidth(230);
		tabla.getColumnModel().getColumn(2).setHeaderValue("Fecha de Permiso");
		tabla.getColumnModel().getColumn(2).setMaxWidth(100);
		tabla.getColumnModel().getColumn(2).setMinWidth(100);
		tabla.getColumnModel().getColumn(3).setHeaderValue("Capturo Permiso");
		tabla.getColumnModel().getColumn(3).setMaxWidth(200);
		tabla.getColumnModel().getColumn(3).setMinWidth(200);
		
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
						tabla.getColumnModel().getColumn(2).setCellRenderer(render); 
						tabla.getColumnModel().getColumn(3).setCellRenderer(render); 
		
		Statement s;
		ResultSet rs;
		try {
			s = con.conexion().createStatement();
			rs = s.executeQuery("sp_select_permiso_checador_filtro" );
			
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
}
	
//Filtro Empleado
public class Filtro_Permiso_Empleado extends JFrame{
	
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
	JTextField txtBuscar = new Componentes().text(new JTextField(), "Buscar", 130, "String");
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Filtro_Permiso_Empleado()	{
		
		this.setTitle("Filtro Empleados");
		
		txtBuscar.addKeyListener(new KeyAdapter() { 
			public void keyReleased(final KeyEvent e) { 
                filtro(); 
            } 
        });
	
		trsfiltro = new TableRowSorter(model); 
		tabla.setRowSorter(trsfiltro);  
		
		campo.add(getPanelTabla()).setBounds(10,70,365,450);
		
		agregar(tabla);
		
		campo.add(lblBuscar).setBounds(30,30,70,20);
		campo.add(txtBuscar).setBounds(95,30,215,20);
		
		cont.add(campo);
		
		this.setSize(390,570);
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
	    			String nombre =  tabla.getValueAt(fila, 1).toString().trim();
	    			
	        		if(tipo_filtro_empleado==1){
	        			txtFolioEmpleado.setText(folio);
	        			lblEmpleado.setText("Empleado: "+nombre);
	        		}
	        		if(tipo_filtro_empleado==2){
	        			buscar_empleado_a_copiarle_turno(Integer.valueOf(folio));
	        		}
	    			dispose();
	        	}
	        }
        });
    }
	
	@SuppressWarnings("unchecked")
	public void filtro() { 
			trsfiltro.setRowFilter(RowFilter.regexFilter(txtBuscar.getText().toUpperCase().trim(), 1));
	}  
	private JScrollPane getPanelTabla()	{		
		new Connexion();
		
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
		tcr.setHorizontalAlignment(SwingConstants.CENTER);
		
		tabla.getColumnModel().getColumn(0).setCellRenderer(tcr);
		

		
		tabla.getColumnModel().getColumn(0).setHeaderValue("Folio");
		tabla.getColumnModel().getColumn(0).setMaxWidth(70);
		tabla.getColumnModel().getColumn(0).setMinWidth(70);
		tabla.getColumnModel().getColumn(1).setHeaderValue("Empleado");
		tabla.getColumnModel().getColumn(1).setMaxWidth(185);
		tabla.getColumnModel().getColumn(1).setMinWidth(185);
		tabla.getColumnModel().getColumn(2).setHeaderValue("Establecimiento");
		tabla.getColumnModel().getColumn(2).setMaxWidth(100);
		tabla.getColumnModel().getColumn(2).setMinWidth(100);
		
		TableCellRenderer render = new TableCellRenderer() 
		{ 
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, 
			boolean hasFocus, int row, int column) { 
				Component componente = null;
				
				componente = new JLabel(value == null? "": value.toString());
		
				if(row %2 == 0){
					((JComponent) componente).setOpaque(true); 
					componente.setBackground(new java.awt.Color(177,177,177));	
				}
				
				if(table.getSelectedRow() == row){
					((JComponent) componente).setOpaque(true); 
					componente.setBackground(new java.awt.Color(186,143,73));
				}	
			return componente; 
			} 
		}; 
						tabla.getColumnModel().getColumn(0).setCellRenderer(render); 
						tabla.getColumnModel().getColumn(1).setCellRenderer(render); 
						tabla.getColumnModel().getColumn(2).setCellRenderer(render); 
		
		Statement s;
		ResultSet rs;
		try {
			s = con.conexion().createStatement();
			rs = s.executeQuery("sp_select_permiso_checador_filtro_empleados" );
			
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
			new Cat_Alimentacion_De_Permisos_A_Empleados().setVisible(true);
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
