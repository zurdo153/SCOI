package Cat_Servicios;

import java.awt.Container;
import java.awt.Event;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
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

import Cat_Principal.EmailSenderService;
import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Connexion;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Lista_de_Raya.Obj_Departamento;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_tabla;
import Obj_Servicios.Obj_Catalogo_Servicios;
import Obj_Servicios.Obj_Servicios;

@SuppressWarnings("serial")
public class Cat_Solicitud_De_Servicios extends JFrame{
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	Obj_Usuario usuario = new Obj_Usuario().LeerSession();
	Obj_Catalogo_Servicios servicios  = new Obj_Catalogo_Servicios();
	Obj_Servicios servicios_solicitud = new Obj_Servicios();
	
	String Departamento= servicios.buscar_Departamento(usuario.getFolio()).getDepartamento();
	String Establecimiento= servicios.buscar_Departamento(usuario.getFolio()).getEstablecimiento();
	String DepartamentoConsulta=Departamento;
	int folio_selecionado=0;
	
	JTextField txtFiltro = new Componentes().text(new JCTextField(), "Teclea Aqui Para Buscar En La Tabla El Servicio Deseado", 500, "String");
	Connexion con = new Connexion();
	Obj_tabla ObjTab =new Obj_tabla();
	int columnas = 12,checkbox=-1;
	public void init_tabla(){
    	this.tabla.getColumnModel().getColumn(0).setMaxWidth(40);	
    	this.tabla.getColumnModel().getColumn(1).setMinWidth(350);
    	this.tabla.getColumnModel().getColumn(2).setMinWidth(500);
    	this.tabla.getColumnModel().getColumn(3).setMinWidth(100);
    	this.tabla.getColumnModel().getColumn(6).setMinWidth(100);
    	this.tabla.getColumnModel().getColumn(8).setMinWidth(120);
    	this.tabla.getColumnModel().getColumn(9).setMinWidth(250);
    	this.tabla.getColumnModel().getColumn(10).setMinWidth(120);
    	this.tabla.getColumnModel().getColumn(11).setMinWidth(250);
		String comando="sp_select_servicios_catalogo '"+DepartamentoConsulta+"'";
		String basedatos="26",pintar="si";
		ObjTab.Obj_Refrescar(tabla,modelo, columnas, comando, basedatos,pintar,checkbox);
    }
	
	@SuppressWarnings("rawtypes")
	public Class[] base (){
		Class[] types = new Class[columnas];
		for(int i = 0; i<columnas; i++){types[i]= java.lang.Object.class;}
		return types;
	}
	
	 public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"Folio","Servicio","Detalle","Prioridad","Dias","Tiempo","Departamento","Estatus","Fecha","Usuario","Fecha Modificacion","Usuario Modifico"}){
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
	JTextField txtServicio     = new Componentes().text(new JCTextField(), "Descripción Corta Del Servicio", 150, "String");
	
	JCButton btnDeshacer       = new JCButton("Deshacer"        ,"deshacer16.png"            ,"Azul" );
	JCButton btnGuardar        = new JCButton("Guardar"         ,"Guardar.png"               ,"Azul" );
	JCButton btnEditar         = new JCButton("Editar"          ,"editara.png"               ,"Azul" );
	JCButton btnNuevo          = new JCButton("Nuevo"           ,"Nuevo.png"                 ,"Azul" );
	JCButton btnAdjuntar       = new JCButton("Adjuntar Archivo","adjuntar-icono-7764-16.png","AzulC");
	JCButton btnMisSolicitudes = new JCButton("Mis Solicitudes" ,"Lista.png"                 ,"Azul" );
	
	JLabel lblUsuario          = new JLabel("");
	JLabel lblDepartamento     = new JLabel("");
	JLabel lblEstablecimiento  = new JLabel("");
	JLabel lblTiempoEstimado   = new JLabel("");
	JLabel lblArchivoAdjunto   = new JLabel("");
	
	JTextArea txaDetalle       = new Componentes().textArea(new JTextArea(), "", 500);
	JScrollPane scrollDetalle  = new JScrollPane(txaDetalle);
	
	String[] status = {"SOLICITADO","CANCELADO"};
	  @SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbEstatus = new JComboBox(status);
	
	 String Departamentos[] = new Obj_Departamento().Combo_Departamentoservicio();
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbDepartamento = new JComboBox(Departamentos);  
	 
	 String Prioridades[] = new Obj_Catalogo_Servicios().Prioridad();
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbPrioridades = new JComboBox(Prioridades);  
	 
	 String EstatusEquipo[] = {"NO APLICA","FALLA PERO SE PUEDE SEGUIR TRABAJANDO CON EL EQUIPO","NO SE PUEDE SEGUIR TRABAJANDO CON EL EQUIPO"};
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbEstatus_Equipo = new JComboBox(EstatusEquipo);
	 
	String[] equipo = {"SELECCIONA UN EQUIPO"};
	  @SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbEquipo = new JComboBox(equipo);
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Cat_Solicitud_De_Servicios(){
		this.setSize(1024,620);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/utilidades-agt-icono-6387-32.png"));
		this.panel.setBorder(BorderFactory.createTitledBorder("Servicios"));
		this.setTitle("Solicitud De Servicios");
		
		trsfiltro = new TableRowSorter(modelo); 
		tabla.setRowSorter(trsfiltro);
		 int x = 15, y=15, width=100, height=20, sep=100;		
		this.panel.add(new JLabel("Folio:")).setBounds      (x      ,y      ,width    ,height   );
		this.panel.add(txtFolio).setBounds                  (x+=95  ,y      ,width+65 ,height   );
		
		this.panel.add(lblUsuario).setBounds                (x+=180 ,y      ,width+170,height   );
		this.panel.add(lblDepartamento).setBounds           (x+=260 ,y      ,width*2  ,height   );	
		this.panel.add(lblEstablecimiento).setBounds        (x+=150 ,y      ,width*2  ,height   );	
		this.panel.add(cmbEstatus).setBounds                (x+=200 ,y      ,width    ,height   );
		
		x=15;
		this.panel.add(txtFiltro).setBounds                 (x      ,y+=30  ,width+885 ,height  );
		this.panel.add(scroll_tabla).setBounds              (x      ,y+20   ,width+885 ,width*3 );
		
		x=15;
		this.panel.add(new JLabel("Solicitar al")).setBounds(x      ,y+=330  ,width    ,height   );
		this.panel.add(new JLabel("Departamento:")).setBounds(x     ,y+=10  ,width    ,height   );
			
		x=15;
		this.panel.add(cmbDepartamento).setBounds           (x+=95  ,y-=5   ,width+65 ,height   );
		this.panel.add(txtServicio).setBounds               (x+=180 ,y      ,width+350,height   );
		this.panel.add(new JLabel("Tiempo Estimado:")).setBounds(x+=460,y   ,width    ,height   );
		this.panel.add(lblTiempoEstimado).setBounds         (x+=90  ,y      ,width+100,height   );
			
		x=15;
		this.panel.add(btnNuevo).setBounds                  (x      ,y+=25 ,width-10 ,height   );
		this.panel.add(cmbPrioridades).setBounds            (x+=95  ,y      ,width+75 ,height   );
		this.panel.add(cmbEstatus_Equipo).setBounds         (x+=185 ,y      ,width+245,height   );
		this.panel.add(cmbEquipo).setBounds                 (x+=360 ,y      ,width+245,height   );
		
		x=15;
		this.panel.add(new JLabel("Detalle:")).setBounds    (x      ,y+=25  ,width    ,height   );
		this.panel.add(scrollDetalle).setBounds             (x      ,y+=15  ,985      ,height*4 ); 
		this.panel.add(btnAdjuntar).setBounds               (x      ,y+=90 ,width+80    ,height );
		this.panel.add(new JLabel("Adjunto:")).setBounds    (x+=200 ,y     ,width       ,height );
		this.panel.add(lblArchivoAdjunto).setBounds         (x+50   ,y     ,width*8     ,height );
 	    
		x=15;width=120;sep=152;
		this.panel.add(btnMisSolicitudes).setBounds         (x+=383 ,y+=25 ,width+25    ,height );
		this.panel.add(btnDeshacer).setBounds               (x+=175 ,y     ,width       ,height );
		this.panel.add(btnEditar).setBounds                 (x+=sep ,y     ,width       ,height );
		this.panel.add(btnGuardar).setBounds                (x+=sep ,y     ,width       ,height );

		lblUsuario.setText(usuario.getNombre_completo());
		lblDepartamento.setText(Departamento);
		lblEstablecimiento.setText(Establecimiento);
		
		cmbDepartamento.addActionListener(actualizar_tabla);
		cmbPrioridades.addActionListener(seleccion_prioridad);
		cmbEstatus_Equipo.addActionListener(seleccion_Estatus_Equipo);
		btnMisSolicitudes.addActionListener(mis_pendientes);
		
		agregar(tabla);
		panelEnabledFalse();
		
		btnGuardar.addActionListener(guardar);
		btnDeshacer.addActionListener(deshacer);
		btnNuevo.addActionListener(nuevo);
		btnEditar.addActionListener(editar);
		btnAdjuntar.addActionListener(AdjuntarArchivo);
		cmbDepartamento.setEnabled(true);
		cmbEstatus_Equipo.setEnabled(false);
		btnEditar.setEnabled(false);
		btnGuardar.setEnabled(false);
		btnAdjuntar.setEnabled(false);
		txtFolio.setEditable(false);
		txtServicio.setEditable(false);
		cmbEstatus.setEnabled(false);
		cmbEquipo.setEnabled(false);
		
		txtFiltro.addKeyListener(opFiltroNombre);
		cont.add(panel);
		
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened( WindowEvent e ){
           	cmbDepartamento.requestFocus();
           	cmbDepartamento.showPopup();
           }
        });
        
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
        getRootPane().getActionMap().put("escape", new AbstractAction(){
            public void actionPerformed(ActionEvent e)
            {         	    btnDeshacer.doClick();    	    }
        });
	
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F9, 0), "nuevo");
        getRootPane().getActionMap().put("nuevo", new AbstractAction(){
            public void actionPerformed(ActionEvent e)
            {          	    btnNuevo.doClick();        	    }
        });
                    
	    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_N,Event.CTRL_MASK),"nuevo");
	         getRootPane().getActionMap().put("nuevo", new AbstractAction(){
	             public void actionPerformed(ActionEvent e)
	             {          btnNuevo.doClick();        	    }
	       });
	         
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F10, 0), "editar");
            getRootPane().getActionMap().put("editar", new AbstractAction(){
                public void actionPerformed(ActionEvent e)
                {      	    btnEditar.doClick();       	    }
           });

            getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_E,Event.CTRL_MASK), "editar");
            getRootPane().getActionMap().put("editar", new AbstractAction(){
                public void actionPerformed(ActionEvent e)
                {                 	    btnEditar.doClick();
                  	    }
           });
	}
	
	KeyListener opFiltroNombre = new KeyListener(){
		public void keyReleased(KeyEvent arg0) {
			ObjTab.Obj_Filtro(tabla, txtFiltro.getText().toUpperCase(), columnas);
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}		
	};
	
	private void agregar(final JTable tbl) {
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
	        	if(e.getClickCount()==1){
	        		if(servicios.getGuardar_actualizar().equals("E")){	
	        			if(JOptionPane.showConfirmDialog(null, "Esta Editando Un Registro y No A Guardado Los Datos, \n"
	        					                             + "Al Dar Si Se Cargaran Los Datos Del Registro Seleccionado Sin Guardar La Edicion \n ¿Desea Continuar? " ) == 0){
	    	        		int fila = tabla.getSelectedRow();
	    	        		   folio_selecionado=Integer.valueOf(tabla.getValueAt(fila,0)+"");
	    						txtServicio.setText(tabla.getValueAt(fila,1)+"");
	    						txaDetalle.setText(tabla.getValueAt(fila,2)+"");
	    						cmbPrioridades.removeActionListener(seleccion_prioridad);
	    						cmbPrioridades.setSelectedItem(tabla.getValueAt(fila,3)+"");
	    						cmbPrioridades.addActionListener(seleccion_prioridad);
	    						cmbDepartamento.setEnabled(false);
	    						cmbEstatus_Equipo.setEnabled(false);
	    						cmbPrioridades.setEnabled(false);
	    						btnGuardar.setEnabled(false);
	    						btnAdjuntar.setEnabled(false);
	    						lblArchivoAdjunto.setText("");
	    						cmbEstatus.setEnabled(false);
	    						txaDetalle.setEditable(false);
//	    						servicios.setGuardar_actualizar("");
	        				return;
	        			}
	        		}else{		
	        		int fila = tabla.getSelectedRow();
	        		    btnNuevo.setEnabled(true);
	        		    folio_selecionado=Integer.valueOf(tabla.getValueAt(fila,0)+"");
						txtServicio.setText(tabla.getValueAt(fila,1)+"");
						txaDetalle.setText(tabla.getValueAt(fila,2)+"");
						lblTiempoEstimado.setText(tabla.getValueAt(fila,4)+" Dia(s)"+tabla.getValueAt(fila,5)+" Hora(s)");
						cmbPrioridades.removeActionListener(seleccion_prioridad);
						cmbPrioridades.setSelectedItem(tabla.getValueAt(fila,3)+"");
						cmbPrioridades.addActionListener(seleccion_prioridad);
						cmbDepartamento.setEnabled(false);
						cmbEstatus_Equipo.setEnabled(false);
						cmbPrioridades.setEnabled(false);
						btnGuardar.setEnabled(false);
						btnAdjuntar.setEnabled(false);
						lblArchivoAdjunto.setText("");
						cmbEstatus.setEnabled(false);
						txaDetalle.setEditable(false);
						return;
	        	 }
	        	}
	        }
        });
    };
	
	ActionListener mis_pendientes = new ActionListener(){
		public void actionPerformed(ActionEvent e){
             new Cat_Mis_Solicitudes_De_Servicios().setVisible(true);
		}		
	};
	
    ActionListener AdjuntarArchivo  = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
            	JFileChooser elegir = new JFileChooser();
            	int opcion = elegir.showOpenDialog(btnAdjuntar);
                if(opcion == JFileChooser.APPROVE_OPTION){
                	
                	String pathArchivo = elegir.getSelectedFile().getPath();
                	  File archivo = new File ( pathArchivo );
	                    double tamano_bytes = archivo.length ( );
	                    double tamano_megas = tamano_bytes/(1024*1024);
	                    
	                    if(tamano_megas>2){
	                    	lblArchivoAdjunto.setText("");
	                    	JOptionPane.showMessageDialog(null, "El Archivo Que Intenta Agregar Es Mas Grande De Lo Permitido,\nEl Archivo Debe Medir Maximo 2 MB", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
	        				return;
	                    }else{
	                    	lblArchivoAdjunto.setText(pathArchivo);
	                    	return;
	                    }
                 }
		}
	};
	
	
	ActionListener actualizar_tabla = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			txtFolio.setText("");
			txtServicio.setText("");
			txaDetalle.setText("");
			cmbEstatus.setSelectedIndex(0);
			
			cmbEstatus_Equipo.removeActionListener(seleccion_Estatus_Equipo);
			cmbEstatus_Equipo.setSelectedIndex(0);
			cmbEstatus_Equipo.addActionListener(seleccion_Estatus_Equipo);
			
			cmbPrioridades.removeActionListener(seleccion_prioridad);
			cmbPrioridades.setSelectedIndex(0);
			cmbPrioridades.addActionListener(seleccion_prioridad);
		
			DepartamentoConsulta=cmbDepartamento.getSelectedItem().toString();
			init_tabla();
			btnNuevo.setEnabled(false);
		}		
	};
	
	ActionListener nuevo = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			int valor =servicios.buscar_nuevo();
			if(valor != 0){
				txtFolio.setText(valor+1+"");
			}else{
				txtFolio.setText(1+"");
			}
			cmbDepartamento.setEnabled(false);
			cmbEstatus_Equipo.setEnabled(true);
			cmbPrioridades.setEnabled(true);
			btnGuardar.setEnabled(true);
			btnAdjuntar.setEnabled(true);
			lblArchivoAdjunto.setText("");
			txtFiltro.setText("");
			cmbEstatus.setEnabled(false);
			txaDetalle.setEditable(true);
			txaDetalle.setText("");
			cmbPrioridades.requestFocus();
			cmbPrioridades.showPopup();
			servicios_solicitud.setGuardar_actualizar("S");//guardar
			
		}
	};
	
	ActionListener seleccion_prioridad = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			cmbEstatus_Equipo.requestFocus();
			cmbEstatus_Equipo.showPopup();
		}		
	};
	
	ActionListener seleccion_Estatus_Equipo = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			txaDetalle.requestFocus();
		}		
	};
	
	ActionListener deshacer = new ActionListener(){
		public void actionPerformed(ActionEvent e){	
			
			panelLimpiar();
			panelEnabledFalse();
			txtFiltro.requestFocus();
			btnEditar.setEnabled(false);
			btnAdjuntar.setEnabled(false);
			cmbDepartamento.setEnabled(true);
			modelo.setRowCount(0);
		}
	};
	
ActionListener guardar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(ValidaCampos()!=""){
				txtFiltro.setText("");
				JOptionPane.showMessageDialog(null, "Los Siguientes Campos Son Requeridos:\n"+ValidaCampos(), "Aviso", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}else{
				servicios_solicitud.setFolio(Integer.valueOf(txtFolio.getText().trim()+"" ));
				servicios_solicitud.setServicio(txtServicio.getText().toUpperCase().trim());
				servicios_solicitud.setPrioridad(cmbPrioridades.getSelectedItem().toString().trim());
				servicios_solicitud.setDepartamento_solicito(cmbDepartamento.getSelectedItem().toString().trim());
				servicios_solicitud.setEstablecimiento_solicito(lblEstablecimiento.getText().trim());
				servicios_solicitud.setDetalle(txaDetalle.getText().toUpperCase().trim());
				servicios_solicitud.setEstatus_equipo(cmbEstatus_Equipo.getSelectedItem().toString());
				servicios_solicitud.setEstatus(cmbEstatus.getSelectedItem().toString().trim());
				servicios_solicitud.setEquipo(cmbEquipo.getSelectedItem().toString().trim());
				servicios_solicitud.setFolio_usuario_solicito(usuario.getFolio());
				servicios_solicitud.setFolio_usuario_modifico(usuario.getFolio());
				servicios_solicitud.setAdjunto(lblArchivoAdjunto.getText().toString().trim());
				
				String statusequipo= cmbEstatus_Equipo.getSelectedItem().toString().trim().toLowerCase().equals("no aplica")?"" :" El estatus del Equipo menciona que esta:"+cmbEstatus_Equipo.getSelectedItem().toString().trim().toLowerCase();
						
				String Mensaje= "El usuario:"+lblUsuario.getText().toString()+" ,Del Establecimiento:"+lblEstablecimiento.getText().trim().toLowerCase()+" ,Del Departamento:"+lblDepartamento.getText().trim().toLowerCase()
						       +" ,Solicito el Servicio de:"+txtServicio.getText().toString().toLowerCase()+" Con Una Prioridad de:"+cmbPrioridades.getSelectedItem().toString().trim().toLowerCase()
						       +statusequipo+"   Detalle:"+txaDetalle.getText().toUpperCase().trim().toLowerCase();
				
				if(servicios_solicitud.getGuardar_actualizar().equals("E")){
						if(JOptionPane.showConfirmDialog(null, "El Registro Ya Existe, ¿Desea Cambiarlo?") == 0){
							if(servicios_solicitud.GuardarActualizar()){
								
								init_tabla();
								  btnDeshacer.doClick();
								JOptionPane.showMessageDialog(null,"El Registró Se Guardó Correctamente!","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/aplicara-el-dialogo-icono-6256-32.png"));
								cmbDepartamento.requestFocus();
								cmbDepartamento.showPopup();
								return;
							}else{
								JOptionPane.showMessageDialog(null,"Error Al Guardar Avise al Administrador del Sistema","Aviso",JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-icono-eliminar5252-64.png"));
								return;
							}
						}
				 }else{
						if(servicios_solicitud.GuardarActualizar()){
							
							try {
								servicios_solicitud = new BuscarSQL().correos_del_departamento_de_servicios(cmbDepartamento.getSelectedItem().toString().trim());
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
							
							new EmailSenderService().enviarcorreo(servicios_solicitud.getCorreos(),servicios_solicitud.getCantidad_de_correos(),Mensaje, "Solicitud Nueva De Servicio "+servicios_solicitud.getFecha_guardado());
							
							init_tabla();
							btnDeshacer.doClick();
							JOptionPane.showMessageDialog(null,"La Solicitud Se Guardó y se ha enviado un correo al departamento de que solicito el servicio!","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/aplicara-el-dialogo-icono-6256-32.png"));
							cmbDepartamento.requestFocus();
							cmbDepartamento.showPopup();
							return;
						}else{
							JOptionPane.showMessageDialog(null,"Error Al Guardar Avise al Administrador del Sistema","Aviso",JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-icono-eliminar5252-64.png"));
							return;
						}
				}
			}
		}
	};
	
	ActionListener editar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			servicios.setGuardar_actualizar("E");//ACTUALIZAR
			panelEnabledTrue();
			btnEditar.setEnabled(false);
			btnNuevo.setEnabled(true);
		}		
	};
	
	public void panelEnabledFalse(){	
		txtServicio.setEditable(false);
		txaDetalle.setEditable(false);
		cmbEstatus.setEnabled(false);
		cmbPrioridades.setEnabled(false);
		cmbDepartamento.setEnabled(false);
		cmbEstatus_Equipo.setEnabled(false);
		btnNuevo.setEnabled(false);
	}		
	
	public void panelEnabledTrue(){	
		txaDetalle.setEditable(true);
		cmbPrioridades.setEnabled(true);
	}
	
	public void panelLimpiar(){	
		txtFolio.setText("");
		txtServicio.setText("");
		txaDetalle.setText("");
		txtFiltro.setText("");
		lblArchivoAdjunto.setText("");
		cmbEstatus.setSelectedIndex(0);
		cmbPrioridades.setSelectedIndex(0);
		cmbEstatus_Equipo.setSelectedIndex(0);
		cmbDepartamento.setSelectedIndex(0);
//		servicios.setGuardar_actualizar("");//guardar
	}
	
	public String ValidaCampos(){
		String error ="";
		
		if(txtServicio.getText().equals("")) 
			error+= "Servicio\n";
		if(txaDetalle.getText().equals("")) 
			error+= "Detalle\n";
		
		if(cmbPrioridades.getSelectedIndex()==0) 
			error+= "Prioridad\n";
		
		if(cmbDepartamento.getSelectedIndex()==0) 
			error+= "Departamento\n";

		return error;
	}
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Solicitud_De_Servicios().setVisible(true);
		}catch(Exception e){	}
	}
	
}