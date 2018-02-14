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
import javax.swing.JDialog;
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
import Obj_Lista_de_Raya.Obj_Establecimiento;
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
	
	JCButton btnDeshacer       = new JCButton("Deshacer"        ,"deshacer16.png"            ,"Azul" );
	JCButton btnMisSolicitudes = new JCButton("Mis Solicitudes" ,"Lista.png"                 ,"Azul" );
	JCButton btnNuevo          = new JCButton("Nuevo"           ,"Nuevo.png"                 ,"Azul" );

	JLabel lblUsuario          = new JLabel("");
	JLabel lblDepartamento     = new JLabel("");
	JLabel lblTiempoEstimado   = new JLabel("");
	JLabel lblFolio            = new JLabel("");
	
	JTextArea txaDetallenuevo       = new Componentes().textArea(new JTextArea(), "", 500);
	JScrollPane scrollDetallen  = new JScrollPane(txaDetallenuevo);
	
	JTextField txtServicio     = new Componentes().text(new JCTextField(), "Descripción Corta Del Servicio", 150, "String");
	
	String[] status = {"SOLICITADO","CANCELADO"};
	  @SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbEstatus = new JComboBox(status);
	
	String establecimientoScoi[] = new Obj_Establecimiento().Combo_Establecimiento();
		@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbEstablecimientof = new JComboBox(establecimientoScoi);
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbEstablecimiento = new JComboBox(establecimientoScoi);	
		
	 String Departamentos[] = new Obj_Departamento().Combo_Departamentoservicio();
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbDepartamento = new JComboBox(Departamentos);  
	 
	 String Prioridades[] = new Obj_Catalogo_Servicios().Prioridad();
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbPrioridades = new JComboBox(Prioridades);  
	 
	 String EstatusEquipo[] = {"NO APLICA","FALLA PERO SE PUEDE SEGUIR TRABAJANDO CON EL EQUIPO","NO SE PUEDE SEGUIR TRABAJANDO CON EL EQUIPO"};
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbEstatus_Equipo = new JComboBox(EstatusEquipo);
	 
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Cat_Solicitud_De_Servicios(){
		this.setSize(1024,560);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/utilidades-agt-icono-6387-32.png"));
		this.panel.setBorder(BorderFactory.createTitledBorder("Servicios"));
		this.setTitle("Solicitud De Servicios");
		
		trsfiltro = new TableRowSorter(modelo); 
		tabla.setRowSorter(trsfiltro);
		 int x = 15, y=15, width=100, height=20, sep=100;		

		this.panel.add(new JLabel("Atender En El Establecimiento:")).setBounds(x      ,y      ,width+sep,height   );	
		this.panel.add(cmbEstablecimientof).setBounds                          (x+150  ,y      ,width+65 ,height   );
		this.panel.add(new JLabel("Solicitante:")).setBounds                  (x+330  ,y      ,width    ,height   );
		this.panel.add(lblUsuario).setBounds                                  (x+390  ,y      ,width+170,height   );
		this.panel.add(new JLabel("Del Departamento De:")).setBounds          (x+650  ,y      ,width+sep,height   );	
		this.panel.add(lblDepartamento).setBounds                             (x+760  ,y      ,width    ,height   );
		this.panel.add(cmbEstatus).setBounds                                  (x+885  ,y      ,width    ,height   );
		this.panel.add(new JLabel("Solicitar Al Departamento De:")).setBounds (x      ,y+=25  ,width+sep,height   );
		this.panel.add(cmbDepartamento).setBounds                             (x+150  ,y      ,width+65 ,height   );
		this.panel.add(new JLabel("Servicio Selecionado:")).setBounds         (x+330  ,y      ,width+sep,height   );
		this.panel.add(txtServicio).setBounds                                 (x+440  ,y      ,width+445,height   );
		this.panel.add(new JLabel("Detalle:")).setBounds                      (x      ,y+=25  ,width    ,height   );
		this.panel.add(new JLabel("Tiempo Estimado:")).setBounds              (x+750  ,y      ,width    ,height   );
		this.panel.add(lblTiempoEstimado).setBounds                           (x+850  ,y      ,width*2  ,height   );
		this.panel.add(scrollDetallen).setBounds                              (x      ,y+=15  ,985      ,height*4 ); 
		this.panel.add(txtFiltro).setBounds                                   (x      ,y+=90  ,985      ,height  );
		this.panel.add(scroll_tabla).setBounds                                (x      ,y+20   ,985      ,width*3 );
		this.panel.add(btnNuevo).setBounds                                    (x      ,y+=330 ,width+65 ,height   );
		this.panel.add(btnDeshacer).setBounds                                 (x+195  ,y      ,width+65 ,height   );
		this.panel.add(btnMisSolicitudes).setBounds                           (x+380  ,y      ,width+65 ,height   );
		this.panel.add(lblFolio).setBounds                                    (x+650  ,y      ,width*3  ,height   );
		
		lblUsuario.setText(usuario.getNombre_completo());
		lblDepartamento.setText(Departamento);
		cmbEstablecimientof.setSelectedItem(Establecimiento);
		
		cmbEstablecimientof.addActionListener(seleccion_establecimiento);
		cmbDepartamento.addActionListener(actualizar_tabla);
		btnMisSolicitudes.addActionListener(mis_pendientes);
		
		agregar(tabla);
		tabla.addKeyListener(opbusquedateclado);
		panelEnabledFalse();
		
		
		btnDeshacer.addActionListener(deshacer);
		btnNuevo.addActionListener(nuevo);
		
		cmbDepartamento.setEnabled(true);
		txaDetallenuevo.setEditable(false);
		txtServicio.setEditable(false);
		cmbEstatus.setEnabled(false);
		txtFiltro.addKeyListener(opFiltroNombre);
		cont.add(panel);
		
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened( WindowEvent e ){
           	cmbEstablecimientof.requestFocus();
           	cmbEstablecimientof.showPopup();
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
	         
	}
	
	KeyListener opFiltroNombre = new KeyListener(){
		public void keyReleased(KeyEvent arg0) {
			ObjTab.Obj_Filtro(tabla, txtFiltro.getText().toUpperCase(), columnas);
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}		
	};
	
	KeyListener opbusquedateclado = new KeyListener(){
		@SuppressWarnings("static-access")
		public void keyReleased(KeyEvent e) {
			char caracter = e.getKeyChar();
			if(caracter==e.VK_ENTER){
				btnNuevo.doClick();
				return;
			}
			if(servicios.getGuardar_actualizar().equals("E")){	
    			if(JOptionPane.showConfirmDialog(null, "Esta Editando Un Registro y No A Guardado Los Datos, \n"
    					                             + "Al Dar Si Se Cargaran Los Datos Del Registro Seleccionado Sin Guardar La Edicion \n ¿Desea Continuar? " ) == 0){
	        		int fila = tabla.getSelectedRow();
	        		   folio_selecionado=Integer.valueOf(tabla.getValueAt(fila,0)+"");
						txtServicio.setText(tabla.getValueAt(fila,1)+"");
						txaDetallenuevo.setText(tabla.getValueAt(fila,2)+"");
						cmbDepartamento.setEnabled(false);
						cmbEstatus.setEnabled(false);
    				return;
    			}
    		}else{		
    		int fila = tabla.getSelectedRow();
    		    btnNuevo.setEnabled(true);
    		    folio_selecionado=Integer.valueOf(tabla.getValueAt(fila,0)+"");
				txtServicio.setText(tabla.getValueAt(fila,1)+"");
				txaDetallenuevo.setText(tabla.getValueAt(fila,2)+"");
				lblTiempoEstimado.setText(tabla.getValueAt(fila,4)+" Dia(s)"+tabla.getValueAt(fila,5)+" Hora(s)");
				cmbDepartamento.setEnabled(false);
				cmbEstatus.setEnabled(false);
				return;
    	 }
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
	    						txaDetallenuevo.setText(tabla.getValueAt(fila,2)+"");
	    						cmbDepartamento.setEnabled(false);
	    						cmbEstatus.setEnabled(false);
	        				return;
	        			}
	        		}else{		
	        		int fila = tabla.getSelectedRow();
	        		    btnNuevo.setEnabled(true);
	        		    folio_selecionado=Integer.valueOf(tabla.getValueAt(fila,0)+"");
						txtServicio.setText(tabla.getValueAt(fila,1)+"");
						txaDetallenuevo.setText(tabla.getValueAt(fila,2)+"");
						lblTiempoEstimado.setText(tabla.getValueAt(fila,4)+" Dia(s)"+tabla.getValueAt(fila,5)+" Hora(s)");
						cmbDepartamento.setEnabled(false);
						cmbEstatus.setEnabled(false);
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
	
	ActionListener actualizar_tabla = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			txtServicio.setText("");
			cmbEstatus.setSelectedIndex(0);
			DepartamentoConsulta=cmbDepartamento.getSelectedItem().toString();
			init_tabla();
			btnNuevo.setEnabled(false);
		}		
	};
	
	ActionListener nuevo = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			new Cat_Descripcion_Solicitud().setVisible(true);
		}
	};
	
	ActionListener seleccion_establecimiento = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			cmbDepartamento.requestFocus();
			cmbDepartamento.showPopup();
		}		
	};
	
	ActionListener deshacer = new ActionListener(){
		public void actionPerformed(ActionEvent e){	
			panelLimpiar();
			panelEnabledFalse();
			txtFiltro.requestFocus();
			cmbDepartamento.setEnabled(true);
			modelo.setRowCount(0);
			cmbDepartamento.showPopup();
		}
	};
	
	public void panelEnabledFalse(){	
		txtServicio.setEditable(false);
		txaDetallenuevo.setEditable(false);
		cmbEstatus.setEnabled(false);
		cmbDepartamento.setEnabled(false);
		btnNuevo.setEnabled(false);
	}		
	
	public void panelLimpiar(){	
		txtServicio.setText("");
		txaDetallenuevo.setText("");
		txtFiltro.setText("");
		cmbDepartamento.setSelectedIndex(0);
//		servicios.setGuardar_actualizar("");//guardar
	}
	
	//TODO DESCRIPCION DEL SERVICIO SOLICITADO	
	public class Cat_Descripcion_Solicitud extends JDialog{
		Container contfb = getContentPane();
		JLayeredPane panelfb = new JLayeredPane();
		
		JTextField txtFiltro = new Componentes().text(new JCTextField(), "Teclea Aqui Para Buscar En La Tabla El Servicio Deseado", 500, "String");
		Connexion con = new Connexion();
		
		JCButton btnGuardar        = new JCButton("Guardar"         ,"Guardar.png"               ,"Azul" );
		JCButton btnAdjuntar       = new JCButton("Adjuntar Archivo","adjuntar-icono-7764-16.png","AzulC");
		
		JTextField txtFolio        = new Componentes().text(new JCTextField(), "Folio", 9, "Int");
		JTextArea txaDetalle       = new Componentes().textArea(new JTextArea(), "", 500);
		JScrollPane scrollDetalle  = new JScrollPane(txaDetalle);
		JLabel lblArchivoAdjunto   = new JLabel("");
		
		JTextField txtBuscarb  = new Componentes().text(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<", 500, "String");
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Cat_Descripcion_Solicitud(){
			this.setSize(590,280);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			this.setModal(true);
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Filter-List-icon32.png"));
			this.panelfb.setBorder(BorderFactory.createTitledBorder("Selecione La Prioridad y El Estatus Del Equipo Luego Describa El Detalle"));
			this.setTitle("Descripcion De La Solicitud Del Servicio");
			trsfiltro = new TableRowSorter(modelo); 
			tabla.setRowSorter(trsfiltro);
			
			 int x = 15, y=15, width=100, height=20, sep=100;	
			this.panelfb.add(new JLabel("Folio:")).setBounds               (x      ,y      ,width     ,height  );
			this.panelfb.add(txtFolio).setBounds                           (x+30   ,y      ,width     ,height  );
			this.panelfb.add(new JLabel("Atender En:")).setBounds          (x+140  ,y      ,width     ,height  );
			this.panelfb.add(cmbEstablecimiento).setBounds                 (x+200  ,y      ,width+75  ,height  );
			this.panelfb.add(cmbEstatus).setBounds                         (x+380  ,y      ,width+75  ,height  );
			this.panelfb.add(new JLabel("Servicio Selecionado:")).setBounds(x      ,y+=25  ,width+sep ,height  );
			this.panelfb.add(txtServicio).setBounds                        (x+sep  ,y      ,width+355 ,height  );
			this.panelfb.add(cmbPrioridades).setBounds                     (x      ,y+=25  ,width+75  ,height  );
			this.panelfb.add(cmbEstatus_Equipo).setBounds                  (x+190  ,y      ,width+265 ,height  );
			this.panelfb.add(new JLabel("Describa El Detalle Del Servicio Solicitado:")).setBounds
			                                                               (x      ,y+=25  ,width*3   ,height  );
			this.panelfb.add(scrollDetalle).setBounds                      (x      ,y+=15  ,555       ,height*4);
			this.panelfb.add(btnAdjuntar).setBounds                        (x      ,y+=90  ,width+80  ,height  );
			this.panelfb.add(lblArchivoAdjunto).setBounds                  (x+185  ,y      ,width*8   ,height  );
			this.panelfb.add(btnGuardar).setBounds                         (x      ,y+=25  ,width+80  ,height  );
			
			int valor =servicios.buscar_nuevo_servicio();
			if(valor != 0){
				txtFolio.setText(valor+1+"");
			}else{
				txtFolio.setText(1+"");
			}
			lblArchivoAdjunto.setText("");
			txtFiltro.setText("");
			cmbEstatus.setEnabled(false);
			txaDetalle.setEditable(true);
			txaDetalle.setText("");
			servicios_solicitud.setGuardar_actualizar("S");//guardar
			cmbEstablecimiento.setSelectedItem(cmbEstablecimientof.getSelectedItem().toString());
			
			this.txtFolio.setEditable(false);
			
	        this.addWindowListener(new WindowAdapter() {
	            public void windowOpened( WindowEvent e ){
	           	cmbPrioridades.requestFocus();
	           	cmbPrioridades.showPopup();
	           }
	        });
			
			cmbPrioridades.addActionListener(seleccion_prioridad);
			cmbEstatus_Equipo.addActionListener(seleccion_Estatus_Equipo);
			btnGuardar.addActionListener(guardar);
			btnAdjuntar.addActionListener(AdjuntarArchivo);
			contfb.add(panelfb);
		}
		
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
		
		  ActionListener guardar = new ActionListener(){
				public void actionPerformed(ActionEvent e){
					int folio_servicio_solicitado=0;
					if(ValidaCampos()!=""){
						txtFiltro.setText("");
						JOptionPane.showMessageDialog(null, "Los Siguientes Campos Son Requeridos:\n"+ValidaCampos(), "Aviso", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
						return;
					}else{
						servicios_solicitud.setFolio(Integer.valueOf(txtFolio.getText().trim()+"" ));
						servicios_solicitud.setServicio(txtServicio.getText().toUpperCase().trim());
						servicios_solicitud.setPrioridad(cmbPrioridades.getSelectedItem().toString().trim());
						servicios_solicitud.setDepartamento_solicito(cmbDepartamento.getSelectedItem().toString().trim());
						servicios_solicitud.setEstablecimiento_solicito(cmbEstablecimiento.getSelectedItem().toString().trim());
						servicios_solicitud.setDetalle(txaDetalle.getText().toUpperCase().trim());
						servicios_solicitud.setEstatus_equipo(cmbEstatus_Equipo.getSelectedItem().toString());
						servicios_solicitud.setEstatus(cmbEstatus.getSelectedItem().toString().trim());
						servicios_solicitud.setEquipo("SELECIONA UN EQUIPO");
						servicios_solicitud.setFolio_usuario_solicito(usuario.getFolio());
						servicios_solicitud.setFolio_usuario_modifico(usuario.getFolio());
						servicios_solicitud.setAdjunto(lblArchivoAdjunto.getText().toString().trim());
						
						String statusequipo= cmbEstatus_Equipo.getSelectedItem().toString().trim().toLowerCase().equals("no aplica")?"" :" El estatus del Equipo menciona que esta:"+cmbEstatus_Equipo.getSelectedItem().toString().trim().toLowerCase();
								
						String Mensaje= "El usuario:"+lblUsuario.getText().toString()+" Solicita:"+txaDetalle.getText().toUpperCase().trim().toLowerCase()+statusequipo+" << Que Sea Atendido En El Establecimiento "+cmbEstablecimiento.getSelectedItem().toString().trim().toLowerCase()+" Con Una Prioridad de "+cmbPrioridades.getSelectedItem().toString().trim().toLowerCase() ;
						
								folio_servicio_solicitado=servicios_solicitud.GuardarActualizar();
								if(folio_servicio_solicitado>0){
									try {
										servicios_solicitud = new BuscarSQL().correos_del_departamento_de_servicios(cmbDepartamento.getSelectedItem().toString().trim());
									} catch (SQLException e1) {
										e1.printStackTrace();
									}
									
									new EmailSenderService().enviarcorreo(servicios_solicitud.getCorreos(),servicios_solicitud.getCantidad_de_correos(),Mensaje, "Solicitud De: "+txtServicio.getText().toString().toLowerCase()+" "+servicios_solicitud.getFecha_guardado(),"Servicios");
									JOptionPane.showMessageDialog(null,"La Solicitud De Servicio Se Guardó y se ha Enviado un Correo al Departamento Que Solicito el Servicio!\nCon el Folio:"+folio_servicio_solicitado+"","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/aplicara-el-dialogo-icono-6256-32.png"));
									lblFolio.setText("<html> <FONT FACE= arial SIZE=5 COLOR=GREEN><CENTER><b><p>"+"ULTIMO FOLIO GUARDADO:"+folio_servicio_solicitado+""+"</p></b></CENTER></FONT></html>");
									cmbDepartamento.requestFocus();
									cmbDepartamento.showPopup();
									dispose();
								}else{
									JOptionPane.showMessageDialog(null,"Error Al Guardar Avise al Administrador del Sistema","Aviso",JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-icono-eliminar5252-64.png"));
									return;
								}
						}
					}
			};
			
			public String ValidaCampos(){
			String error ="";
			if(txaDetalle.getText().equals("")) 		error+= "Detalle\n";
			if(cmbEstablecimiento.getSelectedIndex()==0)error+= "Establecimiento\n";
			if(cmbPrioridades.getSelectedIndex()==0)	error+= "Prioridad\n";
			if(cmbDepartamento.getSelectedIndex()==0)	error+= "Departamento\n";
			return error;
		}
		
	}
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Solicitud_De_Servicios().setVisible(true);
		}catch(Exception e){	}
	}
	
}