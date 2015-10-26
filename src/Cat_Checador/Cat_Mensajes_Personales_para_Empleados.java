package Cat_Checador;

import java.awt.Container;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Connexion;


import Obj_Checador.Obj_Mensaje_Personal;
import Obj_Evaluaciones.Obj_Empleados_En_Cuadrantes;
import Obj_Principal.Componentes;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_Filtro_Dinamico_Plus;
import Obj_Renders.tablaRenderer;

import com.toedter.calendar.JDateChooser;

@SuppressWarnings("serial")
public class Cat_Mensajes_Personales_para_Empleados extends JFrame {

	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JTextField txtFolioMsj = new Componentes().text(new JCTextField(), "Folio", 15, "Int");
	JTextField txtAsunto = new Componentes().text(new JCTextField(), "Teclee El Asunto Del Mensaje", 100, "String");
	
	JDateChooser txtFechaInicio = new JDateChooser();
	JDateChooser txtFechaFin = new JDateChooser();
	
	JButton btnFiltroMSJ = new JButton("Filtro",new ImageIcon("imagen/Filter-List-icon16.png"));
	JButton btnSiguiente = new JButton("Siguiente");
	JButton btnAnterior = new JButton("Anterior");
	
	JTextArea txaMensaje = new Componentes().textArea(new JTextArea(),"Mensaje",150);
	JScrollPane Mensaje = new JScrollPane(txaMensaje);
	
	String status[] = {"VIGENTE","CANCELADO"};
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmb_status = new JComboBox(status);
	
	String color[] = {"Black","Blue","Fuchsia","Gray","Green","Lime","Maroon","Navy","Olive","Orange","Purple","Red","Silver","Teal","White","Yellow","Aqua"};
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmb_color = new JComboBox(color);
	
	String fondo[] = {"Aviso_Importante","Aviso_Importante_2","Cambio_De_Horario","Colgante_Madera","Letrero_Madera","Mochila","Navidad_2","Navidad_IZAGAR"};
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmb_fondo = new JComboBox(fondo);
	
	JButton btnBuscar = new JButton(new ImageIcon("imagen/buscar.png"));
	JButton btnFiltro = new JButton("",new ImageIcon("imagen/Filter-List-icon16.png"));
	JButton btnSubir = new JButton(new ImageIcon("imagen/Up.png"));
	JButton btnBajar = new JButton(new ImageIcon("imagen/Down.png"));
	
	JButton btnEmpleado = new JButton("Empleado",new ImageIcon("imagen/Usuario.png") );
	JButton btnSalir = new JButton("Salir",new ImageIcon("imagen/salir16.png"));
	JButton btnDeshacer = new JButton("Deshacer",new ImageIcon("imagen/deshacer16.png"));
	JButton btnGuardar = new JButton("Guardar",new ImageIcon("imagen/Guardar.png"));
	JButton btnVista_Previa = new JButton("Vista Previa Del Aviso",new ImageIcon("imagen/vista-previa-del-ojo-icono-7248-16.png"));
		
	JButton btnEditar = new JButton("Editar",new ImageIcon("imagen/editara.png"));
	JButton btnNuevo = new JButton("Nuevo",new ImageIcon("imagen/Nuevo.png"));
	JButton btnRemover = new JButton("Quitar",new ImageIcon("imagen/eliminar-bala-icono-7773-32.png"));
    
	JLabel lblMuestraTexto = new JLabel();

	 public static DefaultTableModel modelo = new DefaultTableModel(null,new String[]{"Folio","Nombre"}){
         @SuppressWarnings("rawtypes")
         Class[] types = new Class[]{
                    java.lang.Object.class,
                    java.lang.Object.class   
     };
         @SuppressWarnings({ "rawtypes", "unchecked" })
         public Class getColumnClass(int columnIndex) {
                 return types[columnIndex];
         }
         
    public boolean isCellEditable(int fila, int columna){
	                 switch(columna){
	                         case 0  : return false; 
	                         case 1  : return false; 
	                 }
	                  return false;
	          }
	  };

	JTable tabla = new JTable(modelo);
	JScrollPane scrollAsignado = new JScrollPane(tabla);
	@SuppressWarnings("rawtypes")
	private TableRowSorter trsfiltro;
	String color_muestra="Black";	
	String Lista ="";
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Cat_Mensajes_Personales_para_Empleados(){
		this.setSize(1024,460);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("imagen/anadir-un-mensaje-icono-7984-48.png"));
		panel.setBorder(BorderFactory.createTitledBorder("Mensajes a Colaboradores"));	
		this.setTitle("Mensaje Personales");
		lblMuestraTexto.setText("<html> <FONT FACE="+"arial"+" SIZE=6 COLOR="+cmb_color.getSelectedItem().toString()+"><CENTER><b><p>Muestra Del Color</p></b></CENTER></FONT></html>");
		
		Font font = new Font("Verdana", Font.BOLD, 25);
		txaMensaje.setFont(font);
		txaMensaje.setLineWrap(true); 
		txaMensaje.setWrapStyleWord(true);
		
		trsfiltro = new TableRowSorter(modelo); 
		tabla.setRowSorter(trsfiltro);

		int x=30,y=25,width=100,height=20;
		this.panel.add(new JLabel("Folio:")).setBounds(x, y, width, height);
		this.panel.add(txtFolioMsj).setBounds(x+=60,y,width,height);
		this.panel.add(btnBuscar).setBounds(x+=100,y,height,height);
		this.panel.add(btnFiltro).setBounds(x+=25,y,height,height);
		this.panel.add(btnBajar).setBounds(x+=50,y,height,height);
		this.panel.add(btnSubir).setBounds(x+=25,y,height,height);
		this.panel.add(btnNuevo).setBounds(x+=80,y,width,height);
		this.panel.add(btnEmpleado).setBounds                 (x+=140,y     ,width,height);
		this.panel.add(btnRemover).setBounds                  (x+=150,y     ,width,height);
		this.panel.add(btnDeshacer).setBounds                 (x+=230,y     ,width,height);
		
		x=30;
		this.panel.add(new JLabel("Asunto:")).setBounds        (x     ,y+=25,width,height);
		this.panel.add(txtAsunto).setBounds                    (90    ,y    ,width*3+80,height);
		this.panel.add(getPanelTabla()).setBounds              (x+=480,y    ,480,295);
		
		x=30;	
		this.panel.add(new JLabel("Fecha Inicio:")).setBounds  (x     ,y+=25,width,height);
		this.panel.add(txtFechaInicio).setBounds               (x+=60 ,y    ,width,height);
		this.panel.add(new JLabel("Fecha Final:")).setBounds   (x+=110,y    ,width,height);
		this.panel.add(txtFechaFin).setBounds                  (x+=60 ,y    ,width,height);
		this.panel.add(cmb_status).setBounds                   (x+=110,y    ,width,height);

		x=30;
		this.panel.add(Mensaje).setBounds(x,y+=25,440,245);
		this.panel.add(cmb_fondo).setBounds                   (x     ,y+=250,width+50,height);
		this.panel.add(btnVista_Previa).setBounds             (x+=480,y     ,width+90,height);
		this.panel.add(btnGuardar).setBounds                  (x+=240,y     ,width,height);
		this.panel.add(btnSalir).setBounds                    (x+=140,y     ,width,height);
		
		x=30;
		this.panel.add(cmb_color).setBounds                   (x     ,y+=25,width+50,height);
		this.panel.add(lblMuestraTexto).setBounds             (x+=170 ,y    , width+200, height);
		
		txtAsunto.setEnabled(false);
		cmb_status.setEnabled(false);
		cmb_fondo.setEnabled(false);
		cmb_color.setEnabled(false);
		txtFechaFin.setEnabled(false);
		txtFechaInicio.setEnabled(false);
		txaMensaje.setEnabled(false);
		
		btnFiltro.addActionListener(opBuscarMensaje);
		btnSalir.addActionListener(opSalir);
		btnDeshacer.addActionListener(opLimpiar);
		btnNuevo.addActionListener(opNuevo);
		btnEmpleado.addActionListener(opBuscarEmpleado);
		btnBuscar.addActionListener(opBuscar);
//		btnSubir.addActionListener(opMover);
//		btnBajar.addActionListener(opMover);
		btnRemover.addActionListener(opQuitar);
		txtFolioMsj.addKeyListener(buscaAction);
		btnGuardar.addActionListener(guardar);
		btnVista_Previa.addActionListener(opVistaPrevia);
		cmb_color.addActionListener(opCambioColor);
		
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
          getRootPane().getActionMap().put("escape", new AbstractAction(){
         public void actionPerformed(ActionEvent e)
         {                 	    btnDeshacer.doClick();       	    }
         });
		
		cont.add(panel);
	}
	
	private JScrollPane getPanelTabla()	{	
		tabla.getTableHeader().setReorderingAllowed(false) ;
		tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tabla.getColumnModel().getColumn(0).setMinWidth(75);
		tabla.getColumnModel().getColumn(0).setMinWidth(75);
		tabla.getColumnModel().getColumn(1).setMinWidth(390);
		tabla.getColumnModel().getColumn(1).setMaxWidth(390);
	    
	    tabla.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","centro","Arial","normal",12)); 	
	    tabla.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
		
	    refrestabla(0);
		JScrollPane scrol = new JScrollPane(tabla);
 	    return scrol; 
    }
	
	private void tabla_Empleados_Agregados(){
		 Lista="('";	
		 int contador=0, auxiliar=tabla.getRowCount();
		 if (auxiliar==0){
			 Lista="(0)";
		 }else{		 
		      for(int i=0; i< auxiliar; i++){
					String folio_empleado = modelo.getValueAt(i, 0).toString().trim();
					contador=contador+=1;
						if(contador < auxiliar){ Lista=Lista+folio_empleado+"','";}
						 else{Lista=Lista+folio_empleado+"')";}
				}
		 }
	}
			
	
	private void refrestabla(int folio){
		Statement s;
		ResultSet rs;
		try {
			Connexion con = new Connexion();
			s = con.conexion().createStatement();
			rs = s.executeQuery("exec sp_select_empleado_mensaje "+folio);
			while (rs.next())
			{ 
			   String [] fila = new String[2];
			   fila[0] = rs.getString(1).trim();
			   fila[1] = rs.getString(2).trim();
			   modelo.addRow(fila); 
			}	
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en la funcion refrestabla  SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	ActionListener opBuscar = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			if(txtFolioMsj.getText().equals("")){
				JOptionPane.showMessageDialog(null, "Ingrese un folio para poder realizar la busqueda","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
				txtFolioMsj.requestFocus();
				return;
			}else {
				Obj_Mensaje_Personal MsjPresonal = new Obj_Mensaje_Personal().buscar(Integer.parseInt(txtFolioMsj.getText()));
				if(MsjPresonal.getAsunto().equals("")){
					JOptionPane.showMessageDialog(null, "No Existe El Registro Con El Folio: "+txtFolioMsj.getText()+"","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
					txtFolioMsj.setText("");
					txtFolioMsj.requestFocus();
					return;
				}else{
					txtAsunto.setText(MsjPresonal.getAsunto());
					txaMensaje.setText(MsjPresonal.getMensaje());
						try {
							Date date_inicial = new SimpleDateFormat("dd/MM/yyyy").parse(MsjPresonal.getFechaInicial());
							Date date_fin = new SimpleDateFormat("dd/MM/yyyy").parse(MsjPresonal.getFechaFin());
							txtFechaInicio.setDate(date_inicial);
							txtFechaFin.setDate(date_fin);
						} catch (ParseException e1) {
							e1.printStackTrace();
						}
					cmb_status.setSelectedItem(MsjPresonal.getStatus()==1?"VIGENTE":"CANCELADO");
					cmb_color.setSelectedItem(MsjPresonal.getColor_fuente().toString());
					
					///falta corregir la busqueda para que solo ponga el nombre y elimine el resto de la ruta
					cmb_fondo.setSelectedItem(MsjPresonal.getRuta_imagen_mensaje().toString());
					System.out.println(MsjPresonal.getRuta_imagen_mensaje().toString());
   				    modelo.setRowCount(0);
					refrestabla(Integer.parseInt(txtFolioMsj.getText()));	
					txtFolioMsj.setEnabled(false);
					txtAsunto.setEnabled(false);
				}
			}
		}
	};

	
	ActionListener guardar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(txtFolioMsj.getText().equals("")){
				JOptionPane.showMessageDialog(null, "El Folio Es Requerido", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}else{
				if(validacampos().equals("")){
					Obj_Mensaje_Personal MSJ = new Obj_Mensaje_Personal().buscar(Integer.parseInt(txtFolioMsj.getText()));
					if(modelo.getRowCount()>0){
						if(MSJ.getFolioMensaje() == Integer.parseInt(txtFolioMsj.getText())){
							if(JOptionPane.showConfirmDialog(null, "El registro ya existe, ¿desea cambiarlo?") == 0)
							{
								MSJ.setFolioMensaje(Integer.parseInt(txtFolioMsj.getText()));
								MSJ.setFechaInicial(new SimpleDateFormat("dd/MM/yyyy").format(txtFechaInicio.getDate()));
								MSJ.setFechaFin(new SimpleDateFormat("dd/MM/yyyy").format(txtFechaFin.getDate()));
								MSJ.setAsunto(txtAsunto.getText().trim());
								MSJ.setRuta_imagen_mensaje("/Imagen/avisos/"+cmb_fondo.getSelectedItem().toString()+".png");
								MSJ.setColor_fuente(cmb_color.getSelectedItem().toString().trim());
								MSJ.setMensaje(txaMensaje.getText());
								MSJ.setStatus(cmb_status.getSelectedIndex());
								
								if(MSJ.actualizar(Integer.parseInt(txtFolioMsj.getText()))){
										MSJ.actualizar2(listadatos());
											JOptionPane.showMessageDialog(null,"El Registro Se Actualizo Exitosamente!","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
											btnDeshacer.doClick();
											return;
								}else{
									JOptionPane.showMessageDialog(null,"Error Al Intentar Actualizar El Registro!","Avisa Al Administrador Del Sistema",JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
									return;
								}
							}	}else{
									MSJ.setFolioMensaje(Integer.parseInt(txtFolioMsj.getText()));
									MSJ.setFechaInicial(new SimpleDateFormat("dd/MM/yyyy").format(txtFechaInicio.getDate()));
									MSJ.setFechaFin(new SimpleDateFormat("dd/MM/yyyy").format(txtFechaFin.getDate()));
									MSJ.setAsunto(txtAsunto.getText().trim());
									MSJ.setRuta_imagen_mensaje("/Imagen/avisos/"+cmb_fondo.getSelectedItem().toString()+".png");
									MSJ.setColor_fuente(cmb_color.getSelectedItem().toString().trim());
									MSJ.setMensaje(txaMensaje.getText());
									MSJ.setStatus(cmb_status.getSelectedItem().toString().equals("VIGENTE")?1:0);
							 	if(MSJ.guardar_mensaje()){
											MSJ.guardar_Empleado_Mensaje(listadatos());
												JOptionPane.showMessageDialog(null,"El Registro Se Guardo Exitosamente!","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
												btnDeshacer.doClick();
												return;
									}else{
										JOptionPane.showMessageDialog(null,"Error Al Intentar Guardar El Registro!","Avisa Al Administrador Del Sistema",JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
										return;
									}
								}
					}else{
						JOptionPane.showMessageDialog(null,"No Se Puede Guardar Sin Asignar Un Empleado Al Mensaje!","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
						return;
					}
				 }else{
					JOptionPane.showMessageDialog(null, "Los Siguientes Campos Son Requeridos: \n"+validacampos(),"Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
					return;
				}
			}
		}
	};
	
	ActionListener opNuevo = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			txaMensaje.setEnabled(true);
			txtFolioMsj.setText(new Obj_Empleados_En_Cuadrantes().nuevoEmpleadoCuadrante()+"");
			txtFolioMsj.setText(new Obj_Mensaje_Personal().nuevoMensaje()+"");
			txtFolioMsj.setEnabled(false);
			txtFechaFin.setEnabled(true);
			txtFechaInicio.setEnabled(true);
		    cmb_status.setSelectedIndex(0);
		    cmb_status.setEnabled(true);
		    cmb_fondo.setSelectedIndex(0);
		    cmb_fondo.setEnabled(true);
			cmb_color.setEnabled(true);
			txtAsunto.setEnabled(true);
			txtAsunto.requestFocus();
			txtFechaInicio.setDate(cargar_fecha_Sugerida(0));
			txtFechaFin.setDate(cargar_fecha_Sugerida(-7));
			modelo.setRowCount(0);
		}
	};
	
	ActionListener opLimpiar = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) 
		{
			txtAsunto.setEnabled(false);
			txtFolioMsj.setText("");
			txtAsunto.setText("");
			cmb_status.setEnabled(false);
			txtFechaInicio.setDate(null);
			txtFechaFin.setDate(null);
			txtFechaFin.setEnabled(false);
			txtFechaInicio.setEnabled(false);
			cmb_status.setSelectedIndex(0);
			cmb_status.setEnabled(false);
			cmb_fondo.setEnabled(false);
			cmb_color.setEnabled(false);
			modelo.setRowCount(0);
			txtFolioMsj.requestFocus();
			txtFolioMsj.setEnabled(true);
			txaMensaje.setEnabled(false);
			txaMensaje.setText("");
		}
	};
	
	ActionListener opBuscarEmpleado = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			new Cat_Filtro_Empleado_Mensajes().setVisible(true);
		}
	};
	
	ActionListener opCambioColor = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			lblMuestraTexto.setText("<html> <FONT FACE="+"arial"+" SIZE=6 COLOR="+cmb_color.getSelectedItem().toString()+"><CENTER><b><p>Muestra Del Color</p></b></CENTER></FONT></html>");
		}
	};
	
	ActionListener opVistaPrevia = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
	         JDialog frame = new JDialog();
             String ruta="/Imagen/avisos/"+cmb_fondo.getSelectedItem().toString()+".png";
     		 frame.setUndecorated(true);
     		 new Cat_Avisos_Checador(frame,ruta,txaMensaje.getText().toString(),cmb_color.getSelectedItem().toString().trim());
     		 frame.setVisible(true);
		}
	};
	
	ActionListener opBuscarMensaje = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			new Cat_Filtro_Mensaje().setVisible(true);
		}
	};
	
	ActionListener opSalir = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) 
		{
			dispose();
		}
	};
	
	ActionListener opQuitar = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			if(tabla.getRowCount()>0){
				if(tabla.isRowSelected(tabla.getSelectedRow())){
					modelo.removeRow(tabla.getSelectedRow());
				}else{
					JOptionPane.showMessageDialog(null,"No Esta Seleccionada Ninguna Fila De La Tabla De Empleados!","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
					return;
				}
			}else{
				JOptionPane.showMessageDialog(null,"No Hay Filas Que Remover!","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}
		}
	};
	
//	ActionListener opMover = new ActionListener() {
//		public void actionPerformed(ActionEvent arg0) {
//				if(arg0.getSource().equals(btnSubir)){
//					if(txtFolioMsj.getText().equals("")){
//						JOptionPane.showMessageDialog(null, "Ingrese el folio para poder realizar la busqueda","Error",JOptionPane.WARNING_MESSAGE);
//						return;
//					}else {
//						Obj_Mensaje_Personal MsjPresonal = new Obj_Mensaje_Personal().buscar(Integer.parseInt(txtFolioMsj.getText())+1);
//						if(MsjPresonal.getAsunto().equals("")){
//							JOptionPane.showMessageDialog(null, "No existe el registro con el folio: "+(Integer.parseInt(txtFolioMsj.getText())+1)+"","Error",JOptionPane.WARNING_MESSAGE);
//							return;
//						}else{
//							txtFolioMsj.setText((Integer.parseInt(txtFolioMsj.getText())+1)+"");
//							txtAsunto.setText(MsjPresonal.getAsunto());
//							txaMensaje.setText(MsjPresonal.getMensaje());
//							
//								try {
//									Date date_inicial = new SimpleDateFormat("dd/MM/yyyy").parse(MsjPresonal.getFechaInicial());
//									Date date_fin = new SimpleDateFormat("dd/MM/yyyy").parse(MsjPresonal.getFechaFin());
//									txtFechaInicio.setDate(date_inicial);
//									txtFechaFin.setDate(date_fin);
//								} catch (ParseException e1) {
//									e1.printStackTrace();
//								}
//							
////								cmb_status.setSelected(MsjPresonal.getStatus());
//							
//							////////////////  limpia la tabla antes de acer otra busqueda   ////////////////
//						                  modelo.setRowCount(0);		
//								   		 refrestabla(Integer.parseInt(txtFolioMsj.getText()));			
//						}
//					}
//							
//				}
//				if(arg0.getSource().equals(btnBajar)){
//					if(txtFolioMsj.getText().equals("")){
//						JOptionPane.showMessageDialog(null, "Ingrese el folio para poder realizar la busqueda","Error",JOptionPane.WARNING_MESSAGE);
//						return;
//					}else {
//						Obj_Mensaje_Personal MsjPresonal = new Obj_Mensaje_Personal().buscar(Integer.parseInt(txtFolioMsj.getText())-1);
//						if(MsjPresonal.getAsunto().equals("")){
//							JOptionPane.showMessageDialog(null, "No existe el registro con el folio: "+(Integer.parseInt(txtFolioMsj.getText())-1)+"","Error",JOptionPane.WARNING_MESSAGE);
//							return;
//						}else{
//							txtFolioMsj.setText((Integer.parseInt(txtFolioMsj.getText())-1)+"");
//							txtAsunto.setText(MsjPresonal.getAsunto());
//							txaMensaje.setText(MsjPresonal.getMensaje());
//							
//								try {
//									Date date_inicial = new SimpleDateFormat("dd/MM/yyyy").parse(MsjPresonal.getFechaInicial());
//									Date date_fin = new SimpleDateFormat("dd/MM/yyyy").parse(MsjPresonal.getFechaFin());
//									txtFechaInicio.setDate(date_inicial);
//									txtFechaFin.setDate(date_fin);
//								} catch (ParseException e1) {
//									e1.printStackTrace();
//								}
//							
////							chStatus.setSelected(MsjPresonal.getStatus());
//							
//							////////////////  limpia la tabla antes de acer otra busqueda   ////////////////
//								 modelo.setRowCount(0);
//							     refrestabla(Integer.parseInt(txtFolioMsj.getText()));			
//							
//						}
//					}
//				}
//			txtFolioMsj.setEnabled(false);
//			txtAsunto.setEditable(true);
//		}
//	};
	
	public  String[] listadatos()
	{
		String[] matriz=new String[tabla.getRowCount()];
		for (int i = 0; i < tabla.getRowCount(); i++) {
				matriz[i]=modelo.getValueAt(i,0).toString();
		}
		return matriz;
	}
	
	public Date cargar_fecha_Sugerida(Integer dias){
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
	
///////////////////////TODO filtro empleado	
	class Cat_Filtro_Empleado_Mensajes extends JFrame {
		Container cont = getContentPane();
		JLayeredPane campo = new JLayeredPane();
		Object[][] Matriz ;
		Object[][] Tabla = getTabla();
		DefaultTableModel model1 = new DefaultTableModel(Tabla,
	            new String[]{"Folio", "Nombre Completo", "Selección"}
				){
		     @SuppressWarnings("rawtypes")
			Class[] types = new Class[]{
		    	java.lang.Integer.class,
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
	        	 	case 2 : return true; 
	        	 } 				
	 			return false;
	 		}
		};
		
		JTable tabla1 = new JTable(model1);
	    JScrollPane scroll = new JScrollPane(tabla1);
		
		@SuppressWarnings("rawtypes")
		private TableRowSorter trsfiltro;
		
		JTextField txtNombre_Completo = new JTextField();
		
		JButton btnAgregar = new JButton("Agregar");
		@SuppressWarnings({ "rawtypes", "unchecked" })

		public Cat_Filtro_Empleado_Mensajes()	{
			setSize(425,450);
			setResizable(false);
			setLocationRelativeTo(null);
			setIconImage(Toolkit.getDefaultToolkit().getImage("imagen/Filter-List-icon32.png"));
			setTitle("Filtro de Empleados");
			campo.setBorder(BorderFactory.createTitledBorder("Filtro De Empleado"));
			trsfiltro = new TableRowSorter(model1); 
			tabla1.setRowSorter(trsfiltro);  
			
			campo.add(scroll).setBounds(15,42,390,360);
			campo.add(txtNombre_Completo).setBounds(15,20,259,20);
			campo.add(btnAgregar).setBounds(324,20, 80, 20);
			cont.add(campo);
			
			tabla1.getColumnModel().getColumn(0).setMaxWidth(50);
			tabla1.getColumnModel().getColumn(0).setMinWidth(50);
			tabla1.getColumnModel().getColumn(1).setMaxWidth(280);
			tabla1.getColumnModel().getColumn(1).setMinWidth(280);
			tabla1.getColumnModel().getColumn(2).setMaxWidth(30);
			tabla1.getColumnModel().getColumn(2).setMinWidth(30);

			tabla1.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","centro","Arial","normal",12)); 	
		    tabla1.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
			tabla1.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("CHB","izquierda","Arial","normal",12)); 
			    
			txtNombre_Completo.addKeyListener(opFiltroNombre);
			btnAgregar.addActionListener(Agregar);
		}
		
		ActionListener Agregar = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(tabla.isEditing()){
					tabla.getCellEditor().stopCellEditing();
				}
				txtNombre_Completo.setText("");
				
				int[] columnas = {0,1,2};
				new Obj_Filtro_Dinamico_Plus(tabla1, txtNombre_Completo.getText().toUpperCase(), columnas);
				
				for(int i=0; i<tabla1.getRowCount(); i++){
					if(Boolean.parseBoolean(model1.getValueAt(i, 2).toString()) == true){
						String[] arreglo = new String[2];
						
						arreglo[0] = model1.getValueAt(i,0).toString();
						arreglo[1] = model1.getValueAt(i,1).toString();
						
						modelo.addRow(arreglo);
						dispose();
					}
				}
			}
		};
		
		KeyListener opFiltroNombre = new KeyListener(){
			public void keyReleased(KeyEvent arg0) {
				int[] columnas = {0,1,2};
				new Obj_Filtro_Dinamico_Plus(tabla1, txtNombre_Completo.getText().toUpperCase(), columnas);
			}
			public void keyTyped(KeyEvent arg0) {}
			public void keyPressed(KeyEvent arg0) {}		
		};
		
	   	public Object[][] getTabla(){
	   		tabla_Empleados_Agregados();
			String todos = "select folio, nombre+' '+ap_paterno+' '+ap_materno from tb_empleado where status=1 and folio not in"+Lista;
			Statement s;
			ResultSet rs;
			try {
				s = new Connexion().conexion().createStatement();
				rs = s.executeQuery(todos);
				Matriz = new Object[getFilas(todos)][10];
				int i=0;
				while(rs.next()){
					Matriz[i][0] = rs.getString(1)+"  ";
					Matriz[i][1] = "   "+rs.getString(2);
					Matriz[i][2] = false;
					i++;
				}
				Lista="";
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		    return Matriz; 
		}
	   	
	   	public int getFilas(String qry){
			int filas=0;
			Statement stmt = null;
			try {
				stmt = new Connexion().conexion().createStatement();
				ResultSet rs = stmt.executeQuery(qry);
				while(rs.next()){
					filas++;
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return filas;
		}	
	}
	
	class Cat_Filtro_Mensaje extends JFrame {
		Container cont = getContentPane();
		JLayeredPane campo = new JLayeredPane();
		Object[][] Matriz ;
		Object[][] Tabla = getTabla();
		DefaultTableModel model2 = new DefaultTableModel(Tabla,
	            new String[]{"Folio", "Asunto"}
				){
		     @SuppressWarnings("rawtypes")
			Class[] types = new Class[]{
		    	java.lang.Integer.class,
		    	java.lang.String.class  	
	         };
		     @SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int columnIndex) {
	             return types[columnIndex];
	         }
	         public boolean isCellEditable(int fila, int columna){
	        	 switch(columna){
	        	 	case 0 : return false; 
	        	 	case 1 : return false; 
	        	 } 				
	 			return false;
	 		}
		};
		
		JTable tabla2 = new JTable(model2);
	    JScrollPane scroll = new JScrollPane(tabla2);
		
		@SuppressWarnings("rawtypes")
		private TableRowSorter trsfiltro;
		JTextField txtNombre_Completo = new JTextField();
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Cat_Filtro_Mensaje()	{
			setIconImage(Toolkit.getDefaultToolkit().getImage("imagen/Filter-List-icon32.png"));
			setTitle("Filtro de Mensajes");
			campo.setBorder(BorderFactory.createTitledBorder("Filtro De Mensajes"));
			trsfiltro = new TableRowSorter(model2); 
			tabla2.setRowSorter(trsfiltro);  
			campo.add(scroll).setBounds(15,42,390,360);
			campo.add(txtNombre_Completo).setBounds(15,20,345,20);
			
			cont.add(campo);
			
			tabla2.getColumnModel().getColumn(0).setMaxWidth(50);
			tabla2.getColumnModel().getColumn(0).setMinWidth(50);
			tabla2.getColumnModel().getColumn(1).setMaxWidth(340);
			tabla2.getColumnModel().getColumn(1).setMinWidth(340);
			
			tabla2.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","centro","Arial","normal",12)); 	
		    tabla2.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
			agregar(tabla2);
			txtNombre_Completo.addKeyListener(opFiltroNombre);
			setSize(425,450);
			setResizable(false);
			setLocationRelativeTo(null);
			
		}
		
		KeyListener opFiltroNombre = new KeyListener(){
			public void keyReleased(KeyEvent arg0) {
				int[] columnas = {0,1,};
				new Obj_Filtro_Dinamico_Plus(tabla2, txtNombre_Completo.getText().toUpperCase(), columnas);
			}
			public void keyTyped(KeyEvent arg0) {}
			public void keyPressed(KeyEvent arg0) {}		
		};
		
		
	   	public Object[][] getTabla(){
			String todos = "select folio_mensaje as folio_mensaje," +
							" asunto as asunto " +
							"from tb_mensaje_personal";
			Statement s;
			ResultSet rs;
			try {
				s = new Connexion().conexion().createStatement();
				rs = s.executeQuery(todos);
				Matriz = new Object[getFilas(todos)][2];
				int i=0;
				while(rs.next()){
					Matriz[i][0] = rs.getString(1)+"  ";
					Matriz[i][1] = "   "+rs.getString(2);
					i++;
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		    return Matriz; 
		}
	   	
	   	public int getFilas(String qry){
			int filas=0;
			Statement stmt = null;
			try {
				stmt = new Connexion().conexion().createStatement();
				ResultSet rs = stmt.executeQuery(qry);
				while(rs.next()){
					filas++;
				}
				
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return filas;
		}	
	
		private void agregar(final JTable tbl) {
	        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
		        public void mouseClicked(MouseEvent e) {
		        	if(e.getClickCount() == 2){

		        		int fila = tabla2.getSelectedRow();
		    			Object folio =  tabla2.getValueAt(fila, 0);
		    			dispose();
		    			txtFolioMsj.setText(folio.toString().trim());
		    			txtFolioMsj.setEnabled(false);
		    			txtAsunto.setEditable(true);
		    			btnBuscar.doClick();
		        	}
		        }
	        });
	    }
	}
	
	public String validacampos(){
		String error="";
		String fechaNullInicio = txtFechaInicio.getDate()+"";
		String fechaNullFin = txtFechaInicio.getDate()+"";
		
		if (txtAsunto.getText().equals("")){error+="Asunto\n";}
		if(fechaNullInicio.equals("null"))error+= "Fecha de Inicio\n";	
		if(fechaNullFin.equals("null"))error += "Fecha de Fin\n";
		if (txaMensaje.getText().equals("")){error+="Mensaje\n";}
		return error;
	}
	
	public static void main(String[] args) {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Mensajes_Personales_para_Empleados().setVisible(true);
		}catch(Exception e){	}
	}
	
}
