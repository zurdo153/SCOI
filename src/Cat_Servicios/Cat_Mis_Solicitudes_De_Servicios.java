package Cat_Servicios;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Connexion;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Lista_de_Raya.Obj_Departamento;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_tabla;
import Obj_Servicios.Obj_Catalogo_Servicios;
import Obj_Servicios.Obj_Servicios;

@SuppressWarnings("serial")
public class Cat_Mis_Solicitudes_De_Servicios extends JFrame{
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	Obj_Usuario usuario = new Obj_Usuario().LeerSession();
	Obj_Catalogo_Servicios servicios	=new Obj_Catalogo_Servicios();
	Obj_Servicios servicios_solicitud = new Obj_Servicios();
	
	String Departamento= servicios.buscar_Departamento(usuario.getFolio()).getDepartamento();
	
	JTextField txtFiltro = new Componentes().text(new JCTextField(), "Teclea Aqui Para Buscar En La Tabla", 500, "String");
	Connexion con = new Connexion();
	Obj_tabla ObjTab =new Obj_tabla();
	int columnas = 18,checkbox=-1;
 public void init_tabla(String status_pedidos){
    	this.tabla.getColumnModel().getColumn(0).setMinWidth(45);
    	this.tabla.getColumnModel().getColumn(0).setMaxWidth(45);
    	this.tabla.getColumnModel().getColumn(1).setMinWidth(400);
    	this.tabla.getColumnModel().getColumn(2).setMinWidth(80);
    	this.tabla.getColumnModel().getColumn(3).setMinWidth(100);
    	this.tabla.getColumnModel().getColumn(4).setMinWidth(150);
    	this.tabla.getColumnModel().getColumn(5).setMinWidth(160);
    	this.tabla.getColumnModel().getColumn(6).setMinWidth(250);
    	this.tabla.getColumnModel().getColumn(7).setMinWidth(200);
    	this.tabla.getColumnModel().getColumn(8).setMinWidth(120);
    	this.tabla.getColumnModel().getColumn(9).setMinWidth(80);
    	this.tabla.getColumnModel().getColumn(10).setMinWidth(120);
    	
     	this.tabla.getColumnModel().getColumn(11).setMinWidth(120);
    	this.tabla.getColumnModel().getColumn(12).setMinWidth(250);
    	this.tabla.getColumnModel().getColumn(13).setMinWidth(400);
    	this.tabla.getColumnModel().getColumn(14).setMinWidth(100);
    	this.tabla.getColumnModel().getColumn(15).setMinWidth(100);
    	this.tabla.getColumnModel().getColumn(16).setMinWidth(150);
    	this.tabla.getColumnModel().getColumn(17).setMinWidth(250);
		String comando="exec sp_select_servicios_por_colaborador "+String.valueOf(usuario.getFolio()+"")+",'"+status_pedidos+"',''";
		
		String basedatos="26",pintar="si";
		ObjTab.Obj_Refrescar(tabla,modelo, columnas, comando, basedatos,pintar,checkbox);
    }
	
	@SuppressWarnings("rawtypes")
  public Class[] base (){
		Class[] types = new Class[columnas];
		for(int i = 0; i<columnas; i++){types[i]= java.lang.Object.class;}
		return types;
	}
	
  public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"Folio","Detalle","Estatus","Prioridad","Departamento Solicito","Establecimiento Solicito","Usuario Solicito","Servicio Solicitado","Fecha Solicitud","Archivo","Fecha Atendio","Folio Atendio","Usuario Atendio","Notas ","Costo ","Evaluacion","Archivo Respuesta","Comentario Evaluacion"}){
		 @SuppressWarnings("rawtypes")
			Class[] types = base();
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
			public boolean isCellEditable(int fila, int columna){return false;}
	    };
	    JTable tabla = new JTable(modelo);
		public JScrollPane scroll_tabla = new JScrollPane(tabla);
	     @SuppressWarnings("rawtypes")
	    private TableRowSorter trsfiltro;
	     
	JTextField txtFolio        = new Componentes().text(new JCTextField(), "Folio", 9, "Int");
	JTextField txtCosto        = new Componentes().text(new JCTextField(), "Costo", 9, "Double");
	JTextField txtServicio     = new Componentes().text(new JCTextField(), "Descripci�n Del Servicio", 150, "String");
	JTextField txtUsuario      = new Componentes().text(new JCTextField(), "Usuario Solicito", 300, "String");
	JTextField txtFAtendio     = new Componentes().text(new JCTextField(), "", 10, "Int");
	JTextField txtAtendio      = new Componentes().text(new JCTextField(), "Usuario Atendio", 300, "String");
	JTextField txtFcSolicito   = new Componentes().text(new JCTextField(), "Fecha Solicito", 30, "String");
	JTextField txtFcAtendio    = new Componentes().text(new JCTextField(), "Fecha Atendio" , 30, "String");
	JTextField txtFcActual     = new Componentes().text(new JCTextField(), "Fecha Actual"  , 30, "String");
	
	JCButton btnGuardar        = new JCButton("Guardar"        ,"Guardar.png"      ,"Azul");
	JCButton btnDescAdjunto    = new JCButton("No Contiene Archivos Adjuntos"      ,"arrow-descarga-unidad-de-disco-duro-hdd-icono-9640-16.png","Gris");
	JCButton btnNuevaSolicitud = new JCButton("Nueva Solicitud","Lista.png"        ,"Azul");
	
	JLabel lblUsuario          = new JLabel("");
	JLabel lblDepartamento     = new JLabel("");
	JLabel lblCalificacion     = new JLabel("10");
	
	JTextArea txaDetalle       = new Componentes().textArea(new JTextArea(), "", 500);
	JTextArea txaNotas         = new Componentes().textArea(new JTextArea(), "", 500);
	JTextArea txaComentario    = new Componentes().textArea(new JTextArea(), "", 500);
	JScrollPane scrollDetalle  = new JScrollPane(txaDetalle);
	JScrollPane scrollNotas    = new JScrollPane(txaNotas);
	JScrollPane scrollComentario= new JScrollPane(txaComentario);
	
	String[] status = {"TODOS","SOLICITADO Y EN PROCESO","SOLICITADO","EN PROCESO","TERMINADO","CANCELADO","NEGADO"};
	  @SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbEstatus = new JComboBox(status);
	
	  @SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbEstatusCo = new JComboBox(status);
	  
	  @SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbEstatusFiltrado = new JComboBox(status);
		  
	 String Departamentos[] = new Obj_Departamento().Combo_Departamento();
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbDepartamento = new JComboBox(Departamentos);  
	 
	 String Prioridades[] = new Obj_Catalogo_Servicios().Prioridad();
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbPrioridades = new JComboBox(Prioridades);  
	 
	 String establecimiento[] = new Obj_Establecimiento().Combo_Establecimiento();
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbEstablecimiento = new JComboBox(establecimiento);
	 
	 String evaluacion[] = {"","EXCELENTE","BIEN","REGULAR","DEFICIENTE","PESIMO"};
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbEvaluacionServicio = new JComboBox(evaluacion);
	 
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Cat_Mis_Solicitudes_De_Servicios(){
		this.setSize(1024,735);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/articulo-icono-9036-32-mas.png"));
		this.panel.setBorder(BorderFactory.createTitledBorder("Seleccione El Servicio De Que Desea Evaluar"));
		this.setTitle("Mis Servicios Solicitados");
		
		trsfiltro = new TableRowSorter(modelo); 
		tabla.setRowSorter(trsfiltro);
		int x = 15, y=10, width=100, height=20;		

		this.panel.add(new JLabel("Usuario:")).setBounds     (x       ,y      ,width    ,height   );
		this.panel.add(lblUsuario).setBounds                 (x+=45   ,y      ,width*2  ,height   );
		this.panel.add(lblDepartamento).setBounds            (x+=250  ,y      ,width*2  ,height   );	
		this.panel.add(cmbEstatusFiltrado).setBounds         (x+=390  ,y      ,width+70 ,height   );
		this.panel.add(txtFcActual).setBounds                (x+=215  ,y      ,width    ,height   );
		
		x=15;
		this.panel.add(txtFiltro).setBounds                  (x       ,y+=19  ,width*10 ,height   );
		this.panel.add(scroll_tabla).setBounds               (x       ,y+20   ,width*10,width+250);
		
		x=15;
		this.panel.add(new JLabel("Folio:")).setBounds       (x       ,y+=375 ,width    ,height   );
		this.panel.add(txtFolio).setBounds                   (x+=40   ,y      ,width    ,height   );  
		this.panel.add(new JLabel("Servicio:")).setBounds    (x+=110  ,y      ,width    ,height   );
		this.panel.add(txtServicio).setBounds                (x+=45   ,y      ,width+325,height   );
		this.panel.add(new JLabel("Estatus:")).setBounds     (x+=435  ,y      ,width    ,height   );
		this.panel.add(cmbEstatus).setBounds                 (x+=45   ,y      ,width-5  ,height   );
		this.panel.add(new JLabel("Prioridad:")).setBounds   (x+=105  ,y      ,width    ,height   );
		this.panel.add(cmbPrioridades).setBounds             (x+=45   ,y      ,width+75 ,height   );
		
		x=15;
		this.panel.add(new JLabel("Solicito:")).setBounds    (x       ,y+=30  ,width    ,height   );
		this.panel.add(txtUsuario).setBounds                 (x+=40   ,y      ,width+300,height   );
		this.panel.add(new JLabel("Establecimiento:")).setBounds(x+=410,y     ,width    ,height   );
		this.panel.add(cmbEstablecimiento).setBounds          (x+=80  ,y      ,width+85 ,height   );
		this.panel.add(new JLabel("Departamento:")).setBounds(x+=195  ,y      ,width    ,height   );
		this.panel.add(cmbDepartamento).setBounds            (x+=75   ,y      ,width*2  ,height   );

		x=15;
		this.panel.add(new JLabel("Detalle:")).setBounds     (x       ,y+=30  ,width    ,height   );
		this.panel.add(scrollDetalle).setBounds              (x+=40   ,y      ,958      ,height*3 ); 
		
		x=15;
		this.panel.add(new JLabel("Fecha S:")).setBounds     (x       ,y+=70  ,width    ,height   );  
		this.panel.add(txtFcSolicito).setBounds              (x+=40   ,y      ,width+28 ,height   );  
		this.panel.add(btnDescAdjunto).setBounds             (x+=135  ,y      ,width+150,height   );
		this.panel.add(new JLabel("Estatus Resuelto:")).setBounds(x+=260,y    ,width    ,height   );  
		this.panel.add(cmbEstatusCo).setBounds               (x+=85   ,y      ,width+30 ,height   );
		this.panel.add(new JLabel("Costo:")).setBounds       (x+=144  ,y      ,width    ,height   ); 
		this.panel.add(txtCosto).setBounds                   (x+=34   ,y      ,width-10 ,height   ); 
		this.panel.add(new JLabel("Fecha Atendio:")).setBounds(x+=95  ,y      ,width    ,height   ); 
		this.panel.add(txtFcAtendio).setBounds                (x+=75  ,y      ,width+28 ,height   ); 
		
		x=15;
		this.panel.add(new JLabel("Notas:")).setBounds       (x       ,y+=30  ,width    ,height   );
		this.panel.add(scrollNotas).setBounds                (x+=40   ,y      ,958      ,height*3 ); 

		x=15;
		this.panel.add(new JLabel("Atendio:")).setBounds     (x       ,y+=70  ,width    ,height   ); 
		this.panel.add(txtFAtendio).setBounds                (x+=40   ,y      ,50       ,height   );
		this.panel.add(txtAtendio).setBounds                 (x+=50   ,y      ,width+200,height   ); 
		this.panel.add(new JLabel("Evaluacion:")).setBounds  (x+=330  ,y      ,width    ,height   );  
		this.panel.add(cmbEvaluacionServicio).setBounds      (x+=75   ,y      ,width+28 ,height   );
		this.panel.add(lblCalificacion).setBounds            (x+=140  ,y      ,width*2  ,height   );
		this.panel.add(btnNuevaSolicitud).setBounds          (x+=200  ,y      ,width+50 ,height   );

		
		x=15;	
		this.panel.add(new JLabel("Comentario")).setBounds   (x       ,y+=30  ,width    ,height   ); 
		this.panel.add(scrollComentario).setBounds           (x+=60   ,y      ,820      ,height*2 );
		this.panel.add(new JLabel("Evaluacion:")).setBounds  (x-=60   ,y+=15  ,width    ,height   ); 
		this.panel.add(btnGuardar).setBounds                 (x+=893  ,y      ,width    ,height   );
		
		
		lblUsuario.setText(usuario.getNombre_completo());
		lblDepartamento.setText(Departamento);
		
		cmbEstatusFiltrado.setSelectedItem("SOLICITADO Y EN PROCESO");
		
		init_tabla(cmbEstatusFiltrado.getSelectedItem().toString().trim());
		agregar(tabla);
		panelEnabledFalse();
		
		lblCalificacion.setText("<html> <FONT FACE="+"arial"+" SIZE=5 COLOR=GREEN><CENTER><b><p>Calificacion:10</p></b></CENTER></FONT></html>");
		btnDescAdjunto.addActionListener(opDescargarArchivoSolicitud);
		btnGuardar.addActionListener(guardar);
		cmbEstatusFiltrado.addActionListener(actualizartabla);
		cmbEvaluacionServicio.addActionListener(calificacion);
		btnNuevaSolicitud.addActionListener(nuevasolicidtud);
		txtFiltro.addKeyListener(opFiltroNombre);
		
		try {
			txtFcActual.setText(new BuscarSQL().fecha(0));
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		cont.add(panel);
		
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened( WindowEvent e ){
            	cmbEstatusFiltrado.requestFocus();
            	cmbEstatusFiltrado.showPopup();
           }
        });
	}
	
	KeyListener opFiltroNombre = new KeyListener(){
		public void keyReleased(KeyEvent arg0) {
			ObjTab.Obj_Filtro(tabla, txtFiltro.getText().toUpperCase(), columnas,txtFiltro);
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}		
	};
	
	private void agregar(final JTable tbl) {
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
	        	if(e.getClickCount()==1){
	        		panelEnabledFalse();
	        		int fila = tabla.getSelectedRow();
	        		if(fila<0){fila=0;}
	        		
						txtFolio.setText(tabla.getValueAt(fila,0)+"");
						txaDetalle.setText(tabla.getValueAt(fila,1)+"");
						cmbEstatus.setSelectedItem(tabla.getValueAt(fila,2)+"");
						cmbEstatusCo.setSelectedItem(tabla.getValueAt(fila,2)+"");
						cmbPrioridades.setSelectedItem(tabla.getValueAt(fila,3)+"");
	                    cmbDepartamento.setSelectedItem(tabla.getValueAt(fila,4)+"");
	                    cmbEstablecimiento.setSelectedItem(tabla.getValueAt(fila,5)+"");
						txtUsuario.setText(tabla.getValueAt(fila,6)+"");
						txtServicio.setText(tabla.getValueAt(fila,7)+"");
						txtFAtendio.setText(tabla.getValueAt(fila,11)+"");
	                    txtAtendio.setText(tabla.getValueAt(fila,12)+"");
	                    txaNotas.setText(tabla.getValueAt(fila,13)+"");
	                    txtCosto.setText(tabla.getValueAt(fila,14)+"");
	                    cmbEvaluacionServicio.setSelectedItem(tabla.getValueAt(fila,15)+"");
	                    txtFcSolicito.setText(tabla.getValueAt(fila,8)+"");
						txtFcAtendio.setText(tabla.getValueAt(fila,10)+"");
						txaComentario.setText(tabla.getValueAt(fila,17)+"");
						
	                    //validacion del estatus de la actividad
	                    if(tabla.getValueAt(fila,2).toString().equals("TERMINADO")||tabla.getValueAt(fila,2).toString().equals("CANCELADO")){
	                    	btnGuardar.setEnabled(false);
	                    	
	                         	if(!tabla.getValueAt(fila,10).toString().equals("")){	
	                         		//validacion fecha
			                    	Date fechaterminado_masuno=null;
			                    	Date fechaactual=null;
			                    	try {
			                    		Date fechaterminado = new SimpleDateFormat("dd/MM/yyyy").parse( tabla.getValueAt(fila,10)+"");
			                    		fechaactual = new SimpleDateFormat("dd/MM/yyyy").parse(txtFcActual.getText().toString().trim());
			                    		fechaterminado_masuno=sumarRestarDiasFecha(fechaterminado,2);
									} catch (ParseException e1) {
										e1.printStackTrace();
									}
			                    	
			                    	if(fechaactual.before(fechaterminado_masuno) ){
				                    	//validacion si es el usuario creador para que clasifique la evaluacion
					                    if(tabla.getValueAt(fila,6).toString().equals(lblUsuario.getText())){
					                    	cmbEvaluacionServicio.setEnabled(true);
					                    	btnGuardar.setEnabled(true);
					                    	txaComentario.setEditable(true);
					                    	cmbEvaluacionServicio.requestFocus();
					                    	cmbEvaluacionServicio.showPopup();
					                    }else{
					                    	cmbEvaluacionServicio.setEnabled(false);
					                    	
					                    }
			                    	}
			                   	}	
	                    }
						
						
						if(tabla.getValueAt(fila,9).toString().equals("")){
							btnDescAdjunto.setText("No Contiene Archivos Adjuntos");
							btnDescAdjunto.setEnabled(false);
						}else{
							btnDescAdjunto.setText("Descargar Archivo Adjunto");
							btnDescAdjunto.setEnabled(true);
						}
						return;
	        	}
	        }
        });
    }
	
	public Date sumarRestarDiasFecha(Date fecha, int dias){
		      Calendar calendar = Calendar.getInstance();
		      calendar.setTime(fecha); 
		      calendar.add(Calendar.DAY_OF_YEAR, dias);
		      return calendar.getTime(); 
		 }
	
	ActionListener calificacion = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			
			 if(cmbEvaluacionServicio.getSelectedItem().toString().equals("EXCELENTE")||cmbEvaluacionServicio.getSelectedItem().toString().equals("EXCELENTE")){
				 lblCalificacion.setText("<html> <FONT FACE="+"arial"+" SIZE=5 COLOR=GREEN><CENTER><b><p>Calificacion:10</p></b></CENTER></FONT></html>"); 
			 }
			 if(cmbEvaluacionServicio.getSelectedItem().toString().equals("BIEN")){
				 lblCalificacion.setText("<html> <FONT FACE="+"arial"+" SIZE=5 COLOR=BLUE><CENTER><b><p>Calificacion:8</p></b></CENTER></FONT></html>"); 
			 }
			 
			 if(cmbEvaluacionServicio.getSelectedItem().toString().equals("REGULAR")){
				 lblCalificacion.setText("<html> <FONT FACE="+"arial"+" SIZE=5 COLOR=ORANGE><CENTER><b><p>Calificacion:6</p></b></CENTER></FONT></html>"); 
			 }
			 
			 if(cmbEvaluacionServicio.getSelectedItem().toString().equals("DEFICIENTE")){
				 lblCalificacion.setText("<html> <FONT FACE="+"arial"+" SIZE=5 COLOR=RED><CENTER><b><p>Calificacion:4</p></b></CENTER></FONT></html>"); 
			 }
			 
			 if(cmbEvaluacionServicio.getSelectedItem().toString().equals("PESIMO")){
				 lblCalificacion.setText("<html> <FONT FACE="+"arial"+" SIZE=5 COLOR=RED><CENTER><b><p>Calificacion:2</p></b></CENTER></FONT></html>"); 
			 }
			 
		}
	};
	
	ActionListener nuevasolicidtud = new ActionListener(){
		public void actionPerformed(ActionEvent e){
            new Cat_Solicitud_De_Servicios().setVisible(true);
            dispose();
		}
	};
	
	ActionListener actualizartabla = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			panelLimpiar();
			panelEnabledFalse();
			init_tabla(cmbEstatusFiltrado.getSelectedItem().toString().trim());
			txtFiltro.requestFocus();
		}
	};
	
	ActionListener opDescargarArchivoSolicitud = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			int cantidadDeRegistrosGenerados =0;
			try {
				 cantidadDeRegistrosGenerados = new BuscarSQL().Archivos_Seguimiento_De_Servicios(Integer.valueOf(txtFolio.getText().toString()));
			} catch (NumberFormatException e1) {
				e1.printStackTrace();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			if(cantidadDeRegistrosGenerados > 0){
				JOptionPane.showMessageDialog(null, "Se Genero el Archivo En La Ruta:\n C:\\REPORTES SCOI\\SERVICIOS\\","Aviso",JOptionPane.INFORMATION_MESSAGE);
				return;
			}else{
				JOptionPane.showMessageDialog(null, "No Se Pudo Generar el Archivo Avise Al Administrador Del Sistema","Aviso",JOptionPane.WARNING_MESSAGE);
				return;
			}
		}
	};
	
	
    ActionListener guardar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			int folio_servicio_solicitado=0;
			if(ValidaCampos()!=""){
				JOptionPane.showMessageDialog(null, "Los Siguientes Campos Son Requeridos:\n"+ValidaCampos(), "Aviso", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}else{
				servicios_solicitud.setServicio(txtServicio.getText().toUpperCase().trim());
				servicios_solicitud.setPrioridad(cmbPrioridades.getSelectedItem().toString().trim());
				servicios_solicitud.setDepartamento_solicito(lblDepartamento.getText().toString().trim());
				servicios_solicitud.setEstablecimiento_solicito(cmbEstablecimiento.getSelectedItem().toString().trim());
				servicios_solicitud.setDetalle(txaDetalle.getText().toUpperCase().trim());
				servicios_solicitud.setEquipo("PENDIENTE");
				servicios_solicitud.setEstatus_equipo("NO APLICA");
				servicios_solicitud.setEstatus(cmbEstatusCo.getSelectedItem().toString().trim());
				servicios_solicitud.setFolio_usuario_solicito(usuario.getFolio());
				servicios_solicitud.setEvaluacion(cmbEvaluacionServicio.getSelectedItem().toString().trim());
				servicios_solicitud.setFolio(Integer.valueOf(txtFolio.getText().trim()+"" ));
				servicios_solicitud.setCosto(Float.valueOf(txtCosto.getText().toString().trim()));
				servicios_solicitud.setNotas_servicio(txaNotas.getText().toUpperCase().trim());
				servicios_solicitud.setUsuario_realizo_servicio(Integer.valueOf(txtFAtendio.getText().trim()));
				servicios_solicitud.setGuardar_actualizar("A");
				servicios_solicitud.setFolio_usuario_modifico(usuario.getFolio());
				servicios_solicitud.setComentario_evaluacion(txaComentario.getText().toString().trim());
				
				folio_servicio_solicitado=servicios_solicitud.GuardarActualizar();
				if(folio_servicio_solicitado>0){
					  init_tabla(cmbEstatusFiltrado.getSelectedItem().toString().trim());
					  panelLimpiar();
					  panelEnabledFalse();
					  txtFiltro.requestFocus();
					JOptionPane.showMessageDialog(null,"El Registr� Se Guard� Correctamente!","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/aplicara-el-dialogo-icono-6256-32.png"));
					return;
				}else{
					JOptionPane.showMessageDialog(null,"Error Al Guardar Avise al Administrador del Sistema","Aviso",JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-icono-eliminar5252-64.png"));
					return;
				}
			}
		}
	};
	
	public String ValidaCampos(){
		String error ="";
		if(cmbEvaluacionServicio.getSelectedIndex()==0) 
			error+= "Es Requerido Que Evalue el Servicio Solicitado, Que fue Marcado Como Terminado\n";
		if(txaComentario.getText().equals(""))
			error+= "<<Comentario Evaluacion:>>Con El Fin De Mejorar El Servicio Es Requerido, Su Valioso Comentario\n";
		return error;
	}
	
	public void panelEnabledFalse(){	
		txtFolio.setEditable(false);
		txtUsuario.setEditable(false);
		txtServicio.setEditable(false);
		txaDetalle.setEditable(false);
		cmbEstatus.setEnabled(false);
		cmbPrioridades.setEnabled(false);
		cmbDepartamento.setEnabled(false);
		cmbEstablecimiento.setEnabled(false);
		txtFcAtendio.setEditable(false);
		txtFcSolicito.setEditable(false);
		btnDescAdjunto.setEnabled(false);
		txtFAtendio.setEditable(false);
		txtAtendio.setEditable(false);
		txtFcActual.setEditable(false);
    	cmbEstatusCo.setEnabled(false);
    	cmbEvaluacionServicio.setEnabled(false);
    	txtCosto.setEditable(false);
    	txaNotas.setEditable(false);
    	txaComentario.setEditable(false);
	}		
	
	public void panelLimpiar(){	
		txtFolio.setText("");
		txtServicio.setText("");
		txtCosto.setText("");
		txaDetalle.setText("");
		txaNotas.setText("");
		txtUsuario.setText("");
		txtAtendio.setText("");
		txtFAtendio.setText("");
		txtFcAtendio.setText("");
		txaComentario.setText("");
		
		cmbEstatus.setSelectedIndex(0);
		cmbEstatusCo.setSelectedIndex(0);
		cmbPrioridades.setSelectedIndex(0);
		cmbDepartamento.setSelectedIndex(0);
		cmbEstablecimiento.setSelectedIndex(0);
		cmbEvaluacionServicio.setSelectedItem(0);
		servicios.setGuardar_actualizar("");
	}

	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Mis_Solicitudes_De_Servicios().setVisible(true);
		}catch(Exception e){	}
	}
	
}