package Cat_Contabilidad;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Cat_Contabilidad.Cat_Autorizacion_De_Ordenes_De_Compra_Interna.Cat_Ordenes_De_Compra_Interna_Detalle;
import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Connexion;
import Conexiones_SQL.Generacion_Reportes;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Compras.Obj_Ubicaciones_De_Productos;
import Obj_Contabilidad.Obj_Alimentacion_De_Ordenes_De_Compra_Interna;
import Obj_Contabilidad.Obj_Orden_De_Gasto;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_tabla;
import Obj_Servicios.Obj_Servicios;
import Obj_Xml.CrearXmlString;

@SuppressWarnings("serial")
public class Cat_Surtir_De_Ordenes_De_Compra_Interna extends JFrame {
		Container cont = getContentPane();
		JLayeredPane campo = new JLayeredPane();
		Connexion con = new Connexion();
		Obj_tabla ObjTab= new Obj_tabla();
		Obj_Servicios servicios_solicitud = new Obj_Servicios();
		Obj_Usuario usuario = new Obj_Usuario().LeerSession();
		
		@SuppressWarnings("rawtypes")
		public Class[] tipos(){
			Class[] tip = new Class[Cantidad_Real_De_Columnas];
			
			for(int i =0; i<Cantidad_Real_De_Columnas; i++){
				if(i==checkboxindex-1){
					tip[i]=java.lang.Boolean.class;
				}else{
					tip[i]=java.lang.Object.class;
				}
			}
			return tip;
		}
		
		int Cantidad_Real_De_Columnas=9,checkboxindex=-1;
		public void init_tabla_principal(String status){
			this.tablaP.getColumnModel().getColumn( 0).setMinWidth(50);
			this.tablaP.getColumnModel().getColumn( 0).setMaxWidth(50);
			this.tablaP.getColumnModel().getColumn( 1).setMinWidth(350);
			this.tablaP.getColumnModel().getColumn( 2).setMinWidth(140);
			this.tablaP.getColumnModel().getColumn( 3).setMinWidth(200);
			this.tablaP.getColumnModel().getColumn( 4).setMinWidth(160);
			this.tablaP.getColumnModel().getColumn( 5).setMinWidth(230);
			this.tablaP.getColumnModel().getColumn( 6).setMinWidth(100);
			this.tablaP.getColumnModel().getColumn( 7).setMinWidth(130);
			this.tablaP.getColumnModel().getColumn( 8).setMinWidth(130);
			
			String comandob = "orden_de_compra_interna_filtro '"+status+"'";
			String basedatos="26",pintar="si";
			ObjTab.Obj_Refrescar(tablaP,modeloP, Cantidad_Real_De_Columnas, comandob, basedatos,pintar,checkboxindex);
		}
		
	 public DefaultTableModel modeloP = new DefaultTableModel(null, new String[]{"Folio","Uso De Mercancia","Tipo Solicitante","Nombre De Solicitante","Fecha","Establecimiento","Status","Fecha Autorizacion","Usuario Autorizo"}){
		 @SuppressWarnings("rawtypes")
			Class[] types = tipos();
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {
	         return types[columnIndex];
	     }
			public boolean isCellEditable(int fila, int columna){
				if(columna==0)
					return true; return false;
			}
	    };
	    JTable tablaP = new JTable(modeloP);
		public JScrollPane scroll_tablaP = new JScrollPane(tablaP);
		
		JTextField txtFolio      = new Componentes().text(new JCTextField()  ,"Folio"   ,30   ,"String");
		JTextField txtFiltro     = new Componentes().textfiltro(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<",300 , "String",tablaP,Cantidad_Real_De_Columnas );
		JTextField txtTotal      = new Componentes().text(new JCTextField()  ,"Total"                     ,30   ,"String");
		
		JCButton btnActualizar   = new JCButton("Actualizar"           ,"Actualizar.png","Azul");
		
		String status[] = {"AUTORIZADO","EN VALIDACION","SURTIDO","CANCELADO","TODOS"};
		@SuppressWarnings({ "unchecked", "rawtypes" })
		JComboBox cmb_status = new JComboBox(status);
		
		Obj_Orden_De_Gasto gasto = new Obj_Orden_De_Gasto();
	    String aceptar_negar="";
		JToolBar menu_toolbar       = new JToolBar();
		public Cat_Surtir_De_Ordenes_De_Compra_Interna()	{
			this.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds()); 
			int ancho = Toolkit.getDefaultToolkit().getScreenSize().width;
			int alto  = Toolkit.getDefaultToolkit().getScreenSize().height;
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/asistencia.png"));
			this.setTitle("Autorizacion De Ordenes de Compra Interna");
			campo.setBorder(BorderFactory.createTitledBorder("Seleccione Las Ordenes De Compra Interna Que Desea Autorizar o Negar"));

			int x=15,y=15,height=20,width=127;	
			this.campo.add(btnActualizar).setBounds           (x	 ,y      ,width    ,height );
			this.campo.add(cmb_status).setBounds           	  (x+1120,y      ,width-20 ,height );
			this.campo.add(txtFiltro).setBounds               (x=15  ,y+=25  ,ancho-40 ,height);
			this.campo.add(scroll_tablaP).setBounds           (x     ,y+=20  ,ancho-40 ,alto-150);
			agregar(tablaP);
			init_tabla_principal(cmb_status.getSelectedItem().toString().trim());
			btnActualizar.addActionListener(OpActualizar);
			cmb_status.addActionListener(actualizartabla);
			
			txtTotal.setEditable(false);
			cont.add(campo);
			txtFiltro.requestFocus();
	        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
	        getRootPane().getActionMap().put("escape", new AbstractAction(){ public void actionPerformed(ActionEvent e)
	            {    txtFiltro.setText("");             	 txtFiltro.requestFocus();    	    }     });
		}
		
	   ActionListener OpActualizar = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
				init_tabla_principal(cmb_status.getSelectedItem().toString().trim());
			txtFiltro.requestFocus();
		 }
	   };
	   
	   ActionListener actualizartabla = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				init_tabla_principal(cmb_status.getSelectedItem().toString().trim());
				txtFiltro.requestFocus();
			}
		};
	   
//	   ActionListener opImprimir_Reporte = new ActionListener(){
//			public void actionPerformed(ActionEvent e){
//				String basedatos="2.26";
//				String vista_previa_reporte="no";
//				int vista_previa_de_ventana=0;
//				String comando="orden_de_gasto_reporte '"+txtFolio.getText().toString()+"'";
//				String reporte = "Obj_Reporte_De_Orden_De_Gasto.jrxml";
//		  	    new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
//			}
//	  	};
		
		private void agregar(final JTable tbl) {
			tbl.addMouseListener(new MouseListener() {
				public void mouseReleased(MouseEvent e) {
				
					int fila = tablaP.getSelectedRow();
					String status_orden = tablaP.getValueAt(fila, 6).toString().trim();
//				 	 if(e.getClickCount() == 1){
//				 		String status[] = {"AUTORIZADO","EN VALIDACION","SURTIDO","CANCELADO","TODOS"};
//				 		
//	                 	new Cat_Ordenes_De_Compra_Interna_Detalle(Integer.valueOf(tablaP.getValueAt(fila, 0).toString())).setVisible(true);
//				 	 }
				 	 
				 	if(status_orden.equals("AUTORIZADO")){
                     	new Cat_Ordenes_De_Compra_Interna_Detalle(Integer.valueOf(tablaP.getValueAt(fila, 0).toString())).setVisible(true);
		 			}else{
		 				
		 				JOptionPane.showMessageDialog(null,  "El Status De La Orden De Compra Interna Seleccionada Es:"+status_orden+"\n "
		 													+"Solo Se Pueden Modificar Las Ordenes De Compra Interna Con Status[AUTORIZADO]","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
		 				return;
		 			}
				 	
				}
				public void mousePressed(MouseEvent e) {}
				public void mouseExited(MouseEvent e)  {}
				public void mouseEntered(MouseEvent e) {}
				public void mouseClicked(MouseEvent e) {}
			});
			tbl.addKeyListener(new KeyListener() {
				public void keyPressed(KeyEvent e)  {}
				public void keyReleased(KeyEvent e)   {}
				public void keyTyped   (KeyEvent e)   {}
			});
	    }
		
//(INICIO)AUTORIZACION DE ORDEN DE COMPRA INTERNA ---------------------------------------------------------------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

		public class Cat_Ordenes_De_Compra_Interna_Detalle extends JDialog{
			
			Container cont = getContentPane();
			JLayeredPane panel = new JLayeredPane();
			Connexion con = new Connexion();
			JToolBar menu_toolbar       = new JToolBar();
			
			Obj_tabla  ObjTab = new Obj_tabla();

			int columnas = 10,checkbox=-1;
			public void init_tabla(){
		    	this.tabla.getColumnModel().getColumn(0).setMinWidth(80);	
		    	this.tabla.getColumnModel().getColumn(1).setMinWidth(80);
		    	this.tabla.getColumnModel().getColumn(2).setMinWidth(80);
		    	this.tabla.getColumnModel().getColumn(3).setMinWidth(310);
		    	this.tabla.getColumnModel().getColumn(4).setMinWidth(80);
		    	this.tabla.getColumnModel().getColumn(5).setMinWidth(310);
		    	
		    	this.tabla.getColumnModel().getColumn(6).setMinWidth(80);
		    	this.tabla.getColumnModel().getColumn(7).setMinWidth(80);
		    	this.tabla.getColumnModel().getColumn(8).setMinWidth(80);
		    	this.tabla.getColumnModel().getColumn(9).setMinWidth(80);
				String comando="Select '' as Codigo, 0 Cantidad_Surtida, 0 as Cantidad_Solicitada, '' as Descripcion, '' as Unidad, '' as Producto, 0 as Ultimo_Cost, 0 as Costo_Prom, 0 as Precio_Venta, 0 as Existencia_Total" ;
				String basedatos="26",pintar="si";
				ObjTab.Obj_Refrescar(tabla,modelo, columnas, comando, basedatos,pintar,checkbox);
				modelo.setRowCount(0);
		    }
			
			JCButton btnGuardar  = new JCButton("Guardar"   ,"Guardar.png"                ,"Azul"); 
//			JCButton btnNegar    = new JCButton("Negar"       ,"Delete.png"                 ,"Azul"); 
//			JCButton btnCancelar = new JCButton("Cancelar"    ,"cancelar-icono-4961-16.png" ,"Azul"); 
//			JCButton btnImprimir = new JCButton("Imprimir"    ,"imprimir-16.png"            ,"Azul");
			
			@SuppressWarnings("rawtypes")
			public Class[] basemovimientos (){
				Class[] types = new Class[columnas];
				for(int i = 0; i<columnas; i++){types[i]= java.lang.Object.class;}
				 return types;
			}
//			String comando="Select '' as Codigo, 0 Cantidad_Surtida, 0 as Cantidad_Solicitada, '' as Descripcion, '' as Unidad, '' as Producto, 0 as Ultimo_Cost, 0 as Costo_Prom, 0 as Precio_Venta, 0 as Existencia_Total" ;

			public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"Codigo","C.Surtida","C.Solicitada","Descripcion","Unidad","Producto","Ultimo_Cost.","Costo_Prom.","Precio_Venta","Existencia_Total"}){
				 @SuppressWarnings("rawtypes")
					Class[] types = basemovimientos();
					@SuppressWarnings({ "rawtypes", "unchecked" })
					public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
					public boolean isCellEditable(int fila, int columna){
						if(columna<2){
							return cmbEstablecimientoSurte.getSelectedIndex()==0?false:true;
						}else{ 
							return false;
						}
					}
			};
			
			JTable tabla = new JTable(modelo);
			public JScrollPane scroll_tabla = new JScrollPane(tabla);
			
			JTextField txtFolio       = new Componentes().text(new JCTextField()  ,"Folio"						,30   ,"String");
			JTextField txtFolioSolic  = new Componentes().text(new JCTextField()  ,"Folio Solicita"				,30   ,"String");
			JTextField txtSolicitante = new Componentes().text(new JCTextField()  ,"Persona Que Solicita"		,30   ,"String");
			JTextField txtFolioservici= new Componentes().text(new JCTextField()  ,"Folio Servicio"				,30   ,"String");
			JTextField txtDetalleServi= new Componentes().text(new JCTextField()  ,"Detalle Servicio"			,350  ,"String");
			
		    JTextArea txaUso       = new Componentes().textArea(new JTextArea(), "Uso De La Mercancia", 300);
			JScrollPane Uso        = new JScrollPane(txaUso);
			
			JTextArea txaObservacion       = new Componentes().textArea(new JTextArea(), "Observacion De Surtído", 300);
			JScrollPane scrollObservacion        = new JScrollPane(txaObservacion);
			
			String establecimientoScoi[] = new Obj_Establecimiento().Combo_Establecimiento201();
			@SuppressWarnings({ "unchecked", "rawtypes" })
			JComboBox cmbEstablecimientoSurte = new JComboBox(establecimientoScoi);
			
			JTextField txtFolioRecibe  = new Componentes().text(new JCTextField()   ,"Folio"	,30   ,"String");
			JTextField txtRecibe = new Componentes().text(new JCTextField()  		,"Recibe"	,200   ,"String");
			JCButton btnRecibe = new JCButton(""    ,"Filter-List-icon16.png"            ,"Azul");
			
			JTextField txtStatus = new Componentes().text(new JCTextField()  ,"Status"			,350  ,"String");
			JTextField txtEstablecimiento = new Componentes().text(new JCTextField()  ,"Establecimiento"			,350  ,"String");
			
			@SuppressWarnings("rawtypes")
			JComboBox cmbUnidades = new JComboBox();

			JRadioButton rbProveedor = new JRadioButton("Proveedor");
			JRadioButton rbEmpleado  = new JRadioButton("Empleado");
			ButtonGroup  grupo       = new ButtonGroup();
			
			JRadioButton rbProveedorR = new JRadioButton("Proveedor");
			JRadioButton rbEmpleadoR  = new JRadioButton("Empleado");
			ButtonGroup  grupoR       = new ButtonGroup();
			
			Border blackline, etched, raisedbevel, loweredbevel, empty;

//			String[][] tablaprecargadaordenes;
//		    Object[] vector = new Object[7];
//			Obj_tabla  Objetotabla = new Obj_tabla();
			
		   public  Cat_Ordenes_De_Compra_Interna_Detalle(int folio_orden_de_compra_interna){
			   
			    this.setModal(true);
			    this.cont.add(panel);
				this.setSize(1000,640);
				this.setResizable(false);
				this.setLocationRelativeTo(null);
				this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				this.setTitle("Surtir Ordenes De Compra Interna");
				this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/articulo-icono-9036-48.png"));
				this.blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
				this.panel.setBorder(BorderFactory.createTitledBorder(blackline,"Surtir Ordenes De Compra Interna"));
		   	
		   		this.grupo.add(rbProveedor);
		   		this.grupo.add(rbEmpleado);
		   		this.rbEmpleado.setSelected(true);
		   		
		   		this.grupoR.add(rbProveedorR);
		   		this.grupoR.add(rbEmpleadoR);
		   		this.rbEmpleadoR.setSelected(true);
		   		
		   	    this.menu_toolbar.add(btnGuardar    );
//				this.menu_toolbar.addSeparator(   );
//				this.menu_toolbar.addSeparator(   );
				this.menu_toolbar.setFloatable(false);
				
				int x=20, y=20,width=122,height=20, sep=130;
				panel.add(menu_toolbar).setBounds           (x    ,y      ,400     ,height );
				
				panel.add(new JLabel("Folio:")).setBounds	(x	  		,y+=25  	,width   ,height );
				panel.add(txtFolio).setBounds               (x+40 		,y  		,50   ,height );
				panel.add(new JLabel("Status:")).setBounds	(x+=sep-22	,y      	,50   ,height );
				panel.add(txtStatus).setBounds              (x+=40    	,y      	,width   ,height );
				
				panel.add(new JLabel("Destino De Mercancia:")).setBounds(x+=sep+15 ,y      ,width   ,height );
				panel.add(txtEstablecimiento).setBounds                 (x+=120    ,y      ,235     ,height );
				
				panel.add(new JLabel("Solicito:")).setBounds	(x=20	 ,y+=25 ,width  ,height );
				panel.add(txtFolioSolic).setBounds              (x+40    ,y  	,50     ,height );
				panel.add(txtSolicitante).setBounds             (x=70+40 ,y	   	,370    ,height );
				panel.add(rbEmpleado).setBounds                 (x+390   ,y     ,90     ,height );		
				panel.add(rbProveedor).setBounds                (x+480   ,y     ,90     ,height );  
				
				panel.add(new JLabel("Servicio:")).setBounds	(x=20	 ,y+=25  ,width ,height );
				this.panel.add(txtFolioservici).setBounds       (x=20+40 ,y  	 ,50	,height );
				this.panel.add(txtDetalleServi).setBounds       (x=70+40 ,y      ,555   ,height );
				
				panel.add(new JLabel("Uso De La Mercancia:")).setBounds (x=20  ,y+=20  ,width   ,height );
				panel.add(Uso).setBounds                                (x     ,y+=15  ,642     ,50     );
				
				panel.add(new JLabel("Surte:")).setBounds	(x	  ,y+=65  ,width   ,height );
				panel.add(cmbEstablecimientoSurte).setBounds(x+40 ,y      ,233     ,height );
				
				panel.add(new JLabel("Recibe:")).setBounds	(x+=width*2+50	  ,y  ,width   ,height );
				panel.add(txtFolioRecibe).setBounds         (x+40 ,y      ,50      ,height );
				panel.add(txtRecibe).setBounds           	(x+90 ,y      ,370     ,height );
				panel.add(btnRecibe).setBounds           	(x+460,y      ,30      ,height );
				
				panel.add(rbEmpleadoR).setBounds                 (x+490   ,y     ,90     ,height );		
				panel.add(rbProveedorR).setBounds                (x+580   ,y     ,90     ,height ); 
				
				panel.add(scroll_tabla).setBounds           (x=20     ,y+=22  ,952     ,300    );
				
				panel.add(new JLabel("Observación:")).setBounds (x  ,y=520  ,width   ,height );
				panel.add(scrollObservacion).setBounds          (x  ,y+=25  ,952     ,50     );
				
				txaObservacion.setBackground(new Color(Integer.parseInt("FFFFFF",16)));
				panel_false();
				init_tabla();
				llenarDatos(folio_orden_de_compra_interna);
				agregar(tabla);
				btnRecibe.addActionListener(opRecibe);
				btnGuardar.addActionListener(opGuardar);

				tabla.addKeyListener(op_validanumero_en_celda);
				
				this.addWindowListener(new WindowAdapter() {
		            public void windowOpened( WindowEvent e ){
		           	cmbEstablecimientoSurte.requestFocus();
		           	cmbEstablecimientoSurte.showPopup();
		           }
		        });
		    }
		   
		   public void llenarDatos(int folio){
			   Obj_Alimentacion_De_Ordenes_De_Compra_Interna obj = new Obj_Alimentacion_De_Ordenes_De_Compra_Interna().buscar(folio);
				
				txtFolio.setText(obj.getFolio()+"");
				txtStatus.setText(obj.getStatus());
				txtEstablecimiento.setText(obj.getEstab_destino());
				txtFolioSolic.setText(obj.getFolio_persona_solicita()+"");
				txtSolicitante.setText(obj.getPersona_solicita());
				txtFolioservici.setText(obj.getFolio_servicio()+"");
				txtDetalleServi.setText(obj.getServicio());
				txaUso.setText(obj.getUso_de_mercancia());
				
					if(obj.getTipo_de_solicitante().equals("EMPLEADO")){
						rbEmpleado.setSelected(true);
					}else{
						rbProveedor.setSelected(true);
					}
					
					Object[][] productos = obj.getArreglo_de_productos();
					Object[] reg = new Object[10];
					
					for(int i=0; i<productos.length; i++){
						reg[0]="";
						reg[1]="";
						reg[2]=productos[i][1];
						reg[3]=productos[i][0];
						reg[4]=productos[i][2];
						reg[5]="";
						reg[6]="";
						reg[7]="";
						reg[8]="";
						reg[9]="";
						modelo.addRow(reg);
					}
		   }
		   
		    public void panel_false(){
		    	
		    	txtFolioRecibe.setEditable(false);
		    	txtRecibe.setEditable(false);
		    	txtFolio.setEditable(false);
				txtStatus.setEditable(false);
				txtEstablecimiento.setEditable(false);
				txaUso.setLineWrap(true); 
				txaUso.setWrapStyleWord(true);
				txaUso.setEditable(false);
				txtFolioSolic.setEditable(false);
				txtSolicitante.setEditable(false);
				rbEmpleado.setEnabled(false);
				rbProveedor.setEnabled(false);
				txtFolioservici.setEditable(false);
				txtDetalleServi.setEditable(false);
		    }
		    
		    ActionListener opRecibe = new ActionListener(){
		    	public void actionPerformed(ActionEvent e){
		    		new Cat_Filtro_Buscar_Proveedor().setVisible(true);
		    	}
		    };
		    
		    ActionListener opGuardar = new ActionListener(){
		    	public void actionPerformed(ActionEvent e){
		    		
		    		if(tabla.isEditing()){
		    			tabla.getCellEditor().stopCellEditing();
		    		}
		    		
		    		if(cmbEstablecimientoSurte.getSelectedIndex()>0){
		    			
			    			if(!txtFolioRecibe.getText().trim().equals("")){
			    				
			    				String validarTabla = validaTabla();
					    		
					    		String guardar="NO";
					    		if(!validarTabla.equals("")){
					    			if(JOptionPane.showConfirmDialog(null, "Los Siguientes Productos No Han Sido Completados:\n"+validarTabla+"\nDesea Continuar?") == 0){
					    				guardar="SI";
					    			}
					    		}else{
					    			guardar="SI";
					    		}
					    		
						    		if(guardar=="SI"){
						    			
						    			Obj_Alimentacion_De_Ordenes_De_Compra_Interna OCI = new Obj_Alimentacion_De_Ordenes_De_Compra_Interna();
						    			
							    			if(!validarTabla.equals("")){
							    				rellenarVacios();
							    			}
							    			
//							    			int folio_orden_CI = Integer.valueOf(txtFolio.getText().trim());
//					    					String establecimiento_Surte = cmbEstablecimientoSurte.getSelectedItem().toString().trim();
//								    		int folio_chofer = Integer.valueOf(txtFolioRecibe.getText().trim());
//								    		String ObservacionSurtido = txaObservacion.getText().toString().trim();
//								    		String tipo_chofer_recibe =rbProveedorR.isSelected()?"Proveedor":"Empleado";
								    			
								    		OCI.setFolio(Integer.valueOf(txtFolio.getText().trim()));
								    		OCI.setEstab_surte(cmbEstablecimientoSurte.getSelectedItem().toString().trim());
								    		OCI.setFolio_chofer(Integer.valueOf(txtFolioRecibe.getText().trim()));
								    		OCI.setObservacionSurte(txaObservacion.getText().toString().trim());
								    		OCI.setTipo_de_chofer(rbProveedorR.isSelected()?"Proveedor":"Empleado");
								    		OCI.setStatus("S");
								    		
								    		int[] col = {2,4,5,9};
								    		String xml = new CrearXmlString().CadenaXML(tabla, col);
								    		
								    		OCI.setLista_de_productos(xml);
								    		
								    		String RespuestaSurtido = OCI.surtir();
											  if(RespuestaSurtido!="SIN FOLIO GENERADO"){
												  
												  init_tabla_principal(cmb_status.getSelectedItem().toString().trim());
												  reporte(RespuestaSurtido);
												  dispose();
//								                JOptionPane.showMessageDialog(null, "Se Guardo Correctamente La Orden De Compra Interna\nCon El Folio: "+RespuestaSurtido, "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
//								                return;
											  }else{
								    			 JOptionPane.showMessageDialog(null, "No Se Pudo Guardar La Orden De Compra Interna", "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
									   			 return;
								    		} 
						    		}
					    		
			    			}else{
								JOptionPane.showMessageDialog(null, "Se Requiere Seleccionar La Persona Que Recibe La Mercancia", "Aviso !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
			    			}
		    			
		    		}else{
						JOptionPane.showMessageDialog(null, "Deve Seleccionar El Establecimiento Que Está Surtiendo", "Aviso !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
		    		}
		    	}
		    };
		    
		    public void reporte(String folioBMS){
				
						String basedatos="2.98";
						String vista_previa_reporte="no";
						int vista_previa_de_ventana=0;
						
						String reporte = "Obj_Reporte_De_Orden_De_Compra_Interna2.jrxml";				
						String consulta = "exec orden_de_compra_interna_reporte_surtido '"+folioBMS+"'";
						  
						new Generacion_Reportes().Reporte(reporte, consulta, basedatos, vista_previa_reporte,vista_previa_de_ventana);
				
			}
		    
		    public String validaTabla(){
		    	String error = "";
		    		for(int i=0; i<tabla.getRowCount(); i++){
		    			if(tabla.getValueAt(i, 0).toString().trim().equals("") || tabla.getValueAt(i, 1).toString().trim().equals("")){
		    				error += "- "+tabla.getValueAt(i, 3).toString().trim()+"\n";
		    			}
		    		}
		    	return error;
		    }
		    
		    public void rellenarVacios(){
	    		for(int i=0; i<tabla.getRowCount(); i++){
	    			if(tabla.getValueAt(i, 0).toString().trim().equals("") || tabla.getValueAt(i, 1).toString().trim().equals("")){
	    				tabla.setValueAt("SIN CODIGO", i, 0);	//codigo
	    				tabla.setValueAt("0", i, 1);			//cantidad surtida
	    				tabla.setValueAt("0", i, 6);			//ultimo costo
	    				tabla.setValueAt("0", i, 7);			//costo promedio
	    				tabla.setValueAt("0", i, 8);			//precio venta
	    			}
	    		}
		    }
		    
		    int fila = 0 ;
		    int columna = 0;
		    private void agregar(final JTable tbl) {
		        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
			        public void mouseClicked(MouseEvent e) {
			        	if(e.getClickCount() == 1){
			        		if(cmbEstablecimientoSurte.getSelectedIndex()>0){
			        			fila = tbl.getSelectedRow();
				        		columna = 0;
				        		editarCelda();
			        		}else{
								JOptionPane.showMessageDialog(null, "Deve Seleccionar El Establecimiento Que Está Surtiendo", "Aviso !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
			        		}
			        	}	
			        }
		        });
		    }
		    
		    KeyListener op_validanumero_en_celda = new KeyListener() {
				public void keyTyped(KeyEvent e) {}
				public void keyReleased(KeyEvent e) {

					if(columna==0){
						
						String codigo=tabla.getValueAt(fila, columna).toString().trim();
							if(!codigo.equals("")){
								
									String cod_prod=new BuscarSQL().cod_prod_principal_bms(codigo);// si el codigo es vacion retorna el cod_prod = 01491
									if(!cod_prod.equals("false no existe")){
										
											Obj_Ubicaciones_De_Productos  Datos_Producto= new Obj_Ubicaciones_De_Productos().buscardatos_producto(cod_prod,cmbEstablecimientoSurte.getSelectedItem().toString().trim().toUpperCase()+"");
											
											if(Datos_Producto.getCosto_Promedio() > 0 && Datos_Producto.getUltimo_Costo()>0){
												tabla.setValueAt(Datos_Producto.getCod_Prod().trim(), fila, 0);
												tabla.setValueAt(Datos_Producto.getDescripcion_Prod().trim(), fila, 5);
												
												tabla.setValueAt(Datos_Producto.getUltimo_Costo(), fila, 6);
												tabla.setValueAt(Datos_Producto.getCosto_Promedio(), fila, 7);
												tabla.setValueAt(Datos_Producto.getPrecio_de_venta(), fila, 8);
												tabla.setValueAt(Datos_Producto.getExistencia_Total(), fila, 9);
											}else{
												cod_prod="false no existe"; // esto se hace para que si no tiene costos se regrese a la celda del codigo del producto
												tabla.setValueAt("", fila, 0);
												tabla.setValueAt("", fila, 5);
												JOptionPane.showMessageDialog(null, "El Codigo De Producto ["+Datos_Producto.getCod_Prod().trim()+"] No Cuenta Con Consto Promedio O Ultimo Costo", "Avise Al Administrador Del Sistema !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
											}
									}else{
										tabla.setValueAt("", fila, 0);
										tabla.setValueAt("", fila, 5);
										JOptionPane.showMessageDialog(null, "El Codigo De Producto No Fue Encontrado", "Avise Al Administrador Del Sistema !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
									}
									
									fila= tabla.getSelectedRow();
									columna = cod_prod.equals("false no existe")?0:1;
							}else{
								columna = 0;
							}
					}else{
						boolean resp = validaCantidad(tabla.getValueAt(fila, columna).toString());
						fila= fila+(resp?1:0);
						columna = resp?0:1;
					}
					
//					if(columna==0){
//						fila= tabla.getSelectedRow();
//						columna = codigoEncontrado?1:0;
//					}else{
//						boolean resp = validaCantidad(tabla.getValueAt(fila, columna).toString());
////						tabla.setValueAt("", fila, columna);
//						fila= fila+(resp?1:0);
//						columna = resp?0:1;
//					}
					editarCelda();
				}
				public void keyPressed(KeyEvent e) {}
			};
			
			public boolean validaCantidad(String dato){
				boolean respuesta;
				try {
					Double.parseDouble(dato);
					respuesta = true;
				} catch (Exception e) {
					respuesta = false;
				}
				return respuesta;
			}
			
			String sacarFocoDeTabla = "no";
			@SuppressWarnings("deprecation")
			public void editarCelda(){
				
				if(sacarFocoDeTabla.equals("si")){
					tabla.lostFocus(null, null);
					tabla.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
					tabla.getSelectionModel().clearSelection();
					sacarFocoDeTabla = "no";
					columna=0;
					txaObservacion.requestFocus();
				}else{
					tabla.setRowSelectionInterval(fila, fila);
					tabla.editCellAt(fila, columna);
					Component aComp=tabla.getEditorComponent();
					aComp.requestFocus();
					
					int cantidadDeFilas = tabla.getRowCount();
					if(fila == cantidadDeFilas-1 && columna == 1){
							sacarFocoDeTabla="si";
					}else{
						sacarFocoDeTabla = "no";
					}
				}
			}
			
			//TODO inicia filtro_Buscar	
			public class Cat_Filtro_Buscar_Proveedor extends JDialog{
				Container contfb = getContentPane();
				JLayeredPane panelfb = new JLayeredPane();
				Connexion con = new Connexion();
				Obj_tabla ObjTab =new Obj_tabla();
				int columnasb = 2,checkbox=-1;
				public void init_tablafp(){
			    	this.tablab.getColumnModel().getColumn( 0).setMinWidth(55);
			    	this.tablab.getColumnModel().getColumn( 1).setMinWidth(350);
					 String ref =rbProveedorR.isSelected()?"Proveedor":"Empleado";
					 String comandob=" " ;
					switch(ref){
					    case "Empleado": 		comandob = "select folio,nombre+' '+ap_paterno+' '+ap_materno as nombre from tb_empleado order by nombre"; break;
				    	case "Proveedor": 		comandob = "select folio_proveedor,nombre+' '+ap_paterno+' '+ap_materno as nombre from tb_proveedores where status=1  order by nombre"; break;
				        }
					String basedatos="201",pintar="si";
					ObjTab.Obj_Refrescar(tablab,modelob, columnasb, comandob, basedatos,pintar,checkbox);
			    }
				
				@SuppressWarnings("rawtypes")
				public Class[] base (){
					Class[] types = new Class[columnasb];
					for(int i = 0; i<columnasb; i++){types[i]= java.lang.Object.class;}
					 return types;
				}
				
				public DefaultTableModel modelob = new DefaultTableModel(null, new String[]{"Folio","Solicitante"}){
					 @SuppressWarnings("rawtypes")
						Class[] types = base();
						@SuppressWarnings({ "rawtypes", "unchecked" })
						public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
						public boolean isCellEditable(int fila, int columna){return false;}
				};
				
				JTable tablab = new JTable(modelob);
				public JScrollPane scroll_tablab = new JScrollPane(tablab);
			     @SuppressWarnings({ "rawtypes" })
			    private TableRowSorter trsfiltro;
				     
				JTextField txtBuscarb  = new Componentes().text(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<", 500, "String");
				@SuppressWarnings({ "rawtypes", "unchecked" })
				public Cat_Filtro_Buscar_Proveedor(){
					this.setSize(475,450);
					this.setResizable(false);
					this.setLocationRelativeTo(null);
					this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
					this.setModal(true);
					this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Filter-List-icon32.png"));
					this.panelfb.setBorder(BorderFactory.createTitledBorder("Selecione Un Registro Con Doble Click"));
					this.setTitle("Filtro De Solicitante");
					trsfiltro = new TableRowSorter(modelob); 
					tablab.setRowSorter(trsfiltro);
					this.panelfb.add(txtBuscarb).setBounds      (10 ,20 ,450 , 20 );
					this.panelfb.add(scroll_tablab).setBounds   (10 ,40 ,450 ,370 );
					this.init_tablafp();
					this.agregar(tablab);
					this.txtBuscarb.addKeyListener  (opFiltropuestos );
					contfb.add(panelfb);
				}
				
				private void agregar(final JTable tbl) {
			        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
						public void mouseClicked(MouseEvent e) {
				        	if(e.getClickCount()==1){
				        		int fila = tablab.getSelectedRow();
				        		txtFolioRecibe.setText   (tablab.getValueAt(fila,0)+"");
								txtRecibe.setText   (tablab.getValueAt(fila,1)+"");
								dispose();
				        	}
				        }
			        });
			    }
				
		        private KeyListener opFiltropuestos = new KeyListener(){
					public void keyReleased(KeyEvent arg0) {
						ObjTab.Obj_Filtro(tablab, txtBuscarb.getText().toUpperCase(), columnasb,txtBuscarb);
					}
					public void keyTyped(KeyEvent arg0) {}
					public void keyPressed(KeyEvent arg0) {}		
				};
			}
		};
		
//(FIN)AUTORIZACION DE ORDEN DE COMPRA INTERNA ---------------------------------------------------------------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		public static void main(String args[]){
			try{
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				new Cat_Surtir_De_Ordenes_De_Compra_Interna().setVisible(true);
			}catch(Exception e){	}
		}
	}

