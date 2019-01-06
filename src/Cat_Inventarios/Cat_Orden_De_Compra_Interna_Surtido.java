package Cat_Inventarios;

import java.awt.Color;
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
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

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
import Obj_Principal.Obj_Filtro_Dinamico_Plus;
import Obj_Principal.Obj_tabla;
import Obj_Servicios.Obj_Servicios;
import Obj_Xml.CrearXmlString;

@SuppressWarnings("serial")
public class Cat_Orden_De_Compra_Interna_Surtido extends JFrame {
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
		
		int Cantidad_Real_De_Columnas=13,checkboxindex=-1;
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
			this.tablaP.getColumnModel().getColumn( 9).setMinWidth(130);
			this.tablaP.getColumnModel().getColumn(10).setMinWidth(130);
			this.tablaP.getColumnModel().getColumn(11).setMinWidth(130);
			this.tablaP.getColumnModel().getColumn(12).setMinWidth(130);
			
			String comandob = "orden_de_compra_interna_filtro_surtido '"+status+"'";
			String basedatos="26",pintar="si";
			ObjTab.Obj_Refrescar(tablaP,modeloP, Cantidad_Real_De_Columnas, comandob, basedatos,pintar,checkboxindex);
		}
		
	    public DefaultTableModel modeloP = new DefaultTableModel(null, new String[]{"Folio","Uso De Mercancia","Tipo","Nombre De Solicitante","Fecha","Establecimiento","Status","Fecha Autorizacion","Usuario Autorizo","Tipo Solicitante","Folio Disminución BMS","Establecimiento Surte","Surtió"}){
		 @SuppressWarnings("rawtypes")
			Class[] types = tipos();
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {
	         return types[columnIndex];
	     }
	     public boolean isCellEditable(int fila, int columna){
		 	 return false;
	  	 }
	    };
	    JTable tablaP = new JTable(modeloP);
		public JScrollPane scroll_tablaP = new JScrollPane(tablaP);
		
		JTextField txtFolio      = new Componentes().text(new JCTextField()  ,"Folio"   ,30   ,"String");
		JTextField txtFiltro     = new Componentes().textfiltro(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<",300 , "String",tablaP,Cantidad_Real_De_Columnas );
		JTextField txtTotal      = new Componentes().text(new JCTextField()  ,"Total"                     ,30   ,"String");
		
		JCButton btnActualizar   = new JCButton("Actualizar"  ,"Actualizar.png","Azul");
		JCButton btnImprimir     = new JCButton("Imprimir"    ,"imprimir-16.png"  ,"Azul");
		
		String status[] = {"AUTORIZADO","EN VALIDACION","SURTIDO","CANCELADO","TODOS"};
		@SuppressWarnings({ "unchecked", "rawtypes" })
		JComboBox cmb_status = new JComboBox(status);
		
		Obj_Orden_De_Gasto gasto = new Obj_Orden_De_Gasto();
	    String aceptar_negar="";
		JToolBar menu_toolbar       = new JToolBar();
		
		
//		productos (para filtro)---------------------------------------------------------------------------------------------------------------------------------------
		
		String FActividadesCargado ="";
		String[][] tablaprecargadaproductos;
		
	     int columnas2 = 7;
			public void init_tabla_filtro_productos(){
		    	this.tabla2.getColumnModel().getColumn(0).setMinWidth(90);	
		    	this.tabla2.getColumnModel().getColumn(1).setMinWidth(410);
		    	this.tabla2.getColumnModel().getColumn(2).setMinWidth(150);
		    	this.tabla2.getColumnModel().getColumn(3).setMinWidth(190);
		    	this.tabla2.getColumnModel().getColumn(4).setMinWidth(100);
		    	this.tabla2.getColumnModel().getColumn(5).setMinWidth(100);
		    	
				String comando="exec inventarios_filtro_catalogo_de_productos_con_80_20 ''" ;
				String basedatos="200",pintar="si";
			
				ObjTab.Obj_Refrescar(tabla2, modelo2, columnas2, comando, basedatos,pintar,checkboxindex);
			}
				String establecimiento80="";
				
			 public DefaultTableModel modelo2 = new DefaultTableModel(null, new String[]{"Codigo Producto","Descripcion","Clase Producto","Categoria","Familia","Marca","80/20 De "+establecimiento80}){
					 @SuppressWarnings("rawtypes")
			 		Class[] types = new Class[]{
							java.lang.Object.class,
							java.lang.Object.class,
							java.lang.Object.class,
			  			    java.lang.Object.class,
							java.lang.Object.class,
							java.lang.Object.class,
					    	java.lang.Object.class,
					    };
							
					@SuppressWarnings({ "unchecked", "rawtypes" })
					public Class getColumnClass(int columnIndex) {
					         return types[columnIndex];
			        }
					
					public boolean isCellEditable(int fila, int columna2){
								if(columna2 ==3)
								return true; return false;
							}
				    };
					    
				   JTable tabla2 = new JTable(modelo2);
					public JScrollPane scroll_tabla_filtro = new JScrollPane(tabla2);
//		--------------------------------------------------------------------------------------------------------------------------------------------------------------
		
					
					
		public Cat_Orden_De_Compra_Interna_Surtido()	{
			this.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds()); 
			int ancho = Toolkit.getDefaultToolkit().getScreenSize().width;
			int alto  = Toolkit.getDefaultToolkit().getScreenSize().height;
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/asistencia.png"));
			this.setTitle("Surtido De Ordenes de Compra Interna");
			campo.setBorder(BorderFactory.createTitledBorder("Seleccione La Ordens De Compra Interna Que Desea Surtir Con Doble Click"));

			int x=15,y=15,height=20,width=127;	
			this.campo.add(btnActualizar).setBounds        (x	 ,y      ,width    ,height  );
			this.campo.add(btnImprimir).setBounds          (x+150, y      ,width    ,height  );
			this.campo.add(cmb_status).setBounds       	   (x+350,y      ,width-20 ,height  );
			this.campo.add(txtFiltro).setBounds            (x=15 ,y+=25  ,ancho-40 ,height  );
			this.campo.add(scroll_tablaP).setBounds        (x    ,y+=20  ,ancho-40 ,alto-150);
			
			agregar(tablaP);
			init_tabla_principal(cmb_status.getSelectedItem().toString().trim());
			btnActualizar.addActionListener(OpActualizar);
			btnImprimir.addActionListener(OpImprimir);
			cmb_status.addActionListener(actualizartabla);
			
			txtTotal.setEditable(false);
			cont.add(campo);
			txtFiltro.requestFocus();
	        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
	        getRootPane().getActionMap().put("escape", new AbstractAction(){ public void actionPerformed(ActionEvent e)
	            {    txtFiltro.setText("");             	 txtFiltro.requestFocus();    	    }     });
		}
		
		
	   
	  ActionListener OpImprimir = new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					int fila = tablaP.getSelectedRow();
					if(fila<0) {
					  JOptionPane.showMessageDialog(null,"Es Requerido Seleccione Una Orden De Compra Interna De La Tabla","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
					}else {
						   if(tablaP.getValueAt(fila, 6).toString().trim().equals("SURTIDO")||tablaP.getValueAt(fila, 6).toString().trim().equals("CANCELADO")){	
							   reporte("",tablaP.getValueAt(fila, 0).toString().trim());
					       }else {
							 JOptionPane.showMessageDialog(null,"Solo Es Posible Imprimir Ordenes Surtidas o Canceladas","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
						   }
						}
				 }
					
					
	   };
			   
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
		
		  private void agregar(final JTable tbl) {
			  tbl.addMouseListener(new MouseListener() {
				public void mouseReleased(MouseEvent e){
				 if(e.getClickCount() == 2){agregar(); }
				}
				public void mousePressed(MouseEvent e) {}
				public void mouseExited(MouseEvent e)  {}
				public void mouseEntered(MouseEvent e) {}
				public void mouseClicked(MouseEvent e) {}
			});
			  
			tbl.addKeyListener(new KeyListener() {
				@Override
				public void keyPressed(KeyEvent e)  {
					if(e.getKeyCode()==KeyEvent.VK_ENTER){
						agregar();	
					}
				}
				@Override
				public void keyReleased(KeyEvent e) {}
				@Override
				public void keyTyped(KeyEvent e)    {}
			});
	      }
		
		private void agregar() {
			int fila = tablaP.getSelectedRow();
			String status_orden = tablaP.getValueAt(fila, 6).toString().trim();
		 	 
		 	if(status_orden.equals("AUTORIZADO")){
             	new Cat_Ordenes_De_Compra_Interna_Detalle(Integer.valueOf(tablaP.getValueAt(fila, 0).toString())).setVisible(true);
 			}else{
 				
 				JOptionPane.showMessageDialog(null,  "El Status De La Orden De Compra Interna Seleccionada Es:"+status_orden+"\n "
 													+"Solo Se Pueden Surtir Las Ordenes De Compra Interna Con Status[AUTORIZADO]","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
 				return;
 		    }
	    };
		
//TODO (INICIO)SURTIDO DE ORDEN DE COMPRA INTERNA ---------------------------------------------------------------------------------------------------------------------------------------------
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
			
			@SuppressWarnings("rawtypes")
			public Class[] basemovimientos (){
				Class[] types = new Class[columnas];
				for(int i = 0; i<columnas; i++){types[i]= java.lang.Object.class;}
				 return types;
			}

			public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"Codigo","C.Surtida","C.Solicitada","Descripcion","Unidad","Producto","Ultimo_Cost.","Costo_Prom.","Precio_Venta","Existencia_Total"}){
				 @SuppressWarnings("rawtypes")
					Class[] types = basemovimientos();
					@SuppressWarnings({ "rawtypes", "unchecked" })
					public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
					public boolean isCellEditable(int fila, int columna){
						if(columna==1){
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
			
		   public  Cat_Ordenes_De_Compra_Interna_Detalle(int folio_orden_de_compra_interna){
			    this.setModal(true);
			    this.cont.add(panel);
				this.setSize(1000,640);
				this.setResizable(false);
				this.setLocationRelativeTo(null);
				this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				this.setTitle("Surtir Orden De Compra Interna");
				this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/articulo-icono-9036-48.png"));
				this.blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
				this.panel.setBorder(BorderFactory.createTitledBorder(blackline,"Surtir Orden De Compra Interna"));
		   	
		   		this.grupo.add(rbProveedor);
		   		this.grupo.add(rbEmpleado);
		   		this.rbEmpleado.setSelected(true);
		   		
		   		this.grupoR.add(rbProveedorR);
		   		this.grupoR.add(rbEmpleadoR);
		   		this.rbEmpleadoR.setSelected(true);
		   		
		   	    this.menu_toolbar.add(btnGuardar    );
				this.menu_toolbar.setFloatable(false);
				
				int x=20, y=20,width=122,height=20, sep=130;
				this.panel.add(menu_toolbar).setBounds                       (x         ,y      ,400     ,height );
				this.panel.add(new JLabel("Folio:")).setBounds             	 (x	  	    ,y+=25  ,width   ,height );
				this.panel.add(txtFolio).setBounds                           (x+40 	    ,y  	,50      ,height );
				this.panel.add(new JLabel("Status:")).setBounds              (x+=sep-22 ,y      ,50      ,height );
				this.panel.add(txtStatus).setBounds                          (x+=40     ,y      ,width   ,height );
				this.panel.add(new JLabel("Destino De Mercancia:")).setBounds(x+=sep+15 ,y      ,width   ,height );
				this.panel.add(txtEstablecimiento).setBounds                 (x+=120    ,y      ,235     ,height );
				this.panel.add(new JLabel("Solicito:")).setBounds	         (x=20	    ,y+=25  ,width   ,height );
				this.panel.add(txtFolioSolic).setBounds                      (x+40      ,y  	,50      ,height );
				this.panel.add(txtSolicitante).setBounds                     (x=70+40   ,y	    ,370     ,height );
				this.panel.add(rbEmpleado).setBounds                         (x+390     ,y      ,90      ,height );		
				this.panel.add(rbProveedor).setBounds                        (x+480     ,y      ,90      ,height );  
				this.panel.add(new JLabel("Servicio:")).setBounds	         (x=20	    ,y+=25  ,width   ,height );
				this.panel.add(txtFolioservici).setBounds                    (x=20+40   ,y  	,50  	 ,height );
				this.panel.add(txtDetalleServi).setBounds                    (x=70+40   ,y      ,555     ,height );
				this.panel.add(new JLabel("Uso De La Mercancia:")).setBounds (x=20      ,y+=20  ,width   ,height );
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
                cmbEstablecimientoSurte.addActionListener(OpEstablecimiento);
				tabla.addKeyListener(op_validanumero_en_celda);
				
				this.addWindowListener(new WindowAdapter() {
		            public void windowOpened( WindowEvent e ){
		           	cmbEstablecimientoSurte.requestFocus();
		           	cmbEstablecimientoSurte.showPopup();
		           }
		        });
				
		    }
		   int Bandera=0;
		   ActionListener OpEstablecimiento = new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					 if(Bandera==1 && JOptionPane.showConfirmDialog(null, "El Cambio de Establecimiento Reinicia la tabla de productos, ¿desea actualizarlo?") == 0){
						for(int i=0; i<tabla.getRowCount(); i++){
							tabla.setValueAt("",i, 0);
							tabla.setValueAt("",i, 1);
							tabla.setValueAt("",i, 1);
							tabla.setValueAt("",i, 5);
							tabla.setValueAt("",i, 6);
							tabla.setValueAt("",i, 7);
							tabla.setValueAt("",i, 8);
							tabla.setValueAt("",i, 9);
			    		}
					 }
					 Bandera=1;
					 return;
				 }
		   };
			   
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
			    			if(validasurtidoTabla()) {
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
								    		OCI.setFolio(Integer.valueOf(txtFolio.getText().trim()));
								    		OCI.setEstab_surte(cmbEstablecimientoSurte.getSelectedItem().toString().trim());
								    		OCI.setFolio_chofer(Integer.valueOf(txtFolioRecibe.getText().trim()));
								    		OCI.setObservacionSurte(txaObservacion.getText().toString().trim()+" Surtio:"+usuario.getFolio()+"-"+usuario.getNombre_completo());
								    		OCI.setTipo_de_chofer(rbProveedorR.isSelected()?"Proveedor":"Empleado");
								    		OCI.setStatus("S");
								    		int[] col = {2,4,5,9};
								    		String xml = new CrearXmlString().CadenaXML(tabla, col);
								    		
								    		OCI.setLista_de_productos(xml);
								    		
								    		String RespuestaSurtido = OCI.surtir();
											  if(RespuestaSurtido!="SIN FOLIO GENERADO"){
												  reporte("",OCI.getFolio()+"");
												  init_tabla_principal(cmb_status.getSelectedItem().toString().trim());
												  dispose();
												  Bandera=0;
											  }else{
								    			 JOptionPane.showMessageDialog(null, "No Se Pudo Guardar La Orden De Compra Interna", "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
									   			 return;
								    		} 
						    		}
			    			}
			    			  return;
			    			}else{
								JOptionPane.showMessageDialog(null, "Se Requiere Seleccionar La Persona Que Recibe La Mercancia", "Aviso !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
								return;
			    			}
		    			
		    		}else{
						JOptionPane.showMessageDialog(null, "Debe Seleccionar El Establecimiento Que Está Surtiendo", "Aviso !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
						return;
		    		}
		    	}
		    };
		    
		    public String validaTabla(){
		    	String error = "";
		    		for(int i=0; i<tabla.getRowCount(); i++){
			    			if(tabla.getValueAt(i, 0).toString().trim().equals("") || tabla.getValueAt(i, 1).toString().trim().equals("")){
			    				error += "- "+tabla.getValueAt(i, 3).toString().trim()+"\n";
			    			}
		    		}
		    	return error;
		    }
		    
		    public boolean validasurtidoTabla(){
		    	boolean cumple=true;
			    for(int i=0; i<tabla.getRowCount(); i++){
	    			if(ObjTab.validacelda(tabla,"decimal", i,1)){
			    			if(Float.valueOf(tabla.getValueAt(i, 1).toString().trim())>Float.valueOf(tabla.getValueAt(i, 2).toString().trim())){
								JOptionPane.showMessageDialog(null, "Para Poder Guardar Es Requerido Que El Surtido Sea Igual O Menor Al Solicitado \nCorrija:"+tabla.getValueAt(i, 3).toString().trim(), "Aviso !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
								cumple= false; 			    			
			    			}	
	    			}else {
						JOptionPane.showMessageDialog(null, "Es Requerido que la cantidad del surtido sea un numero válido,  corrija la cantidad de la fila:"+(i+1), "Aviso !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
						cumple= false;
	    			}
			    }
				return cumple;
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
		    
		    int fila = -1 ;
		    int fila_anterior_seleccionada = -1;
		    int columna = 1;
		    private void agregar(final JTable tbl) {
		        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
			        public void mouseClicked(MouseEvent e) {
			        	if(e.getClickCount() == 1){
			        		if(cmbEstablecimientoSurte.getSelectedIndex()>0){
			        			
			        			fila_anterior_seleccionada = fila_anterior_seleccionada==-1?tbl.getSelectedRow():fila;
			        			fila = tbl.getSelectedRow();
			        			
			        			double  surtida=Double.valueOf(tabla.getValueAt(fila_anterior_seleccionada, 1).toString().equals("")?"0":tabla.getValueAt(fila_anterior_seleccionada, 1).toString());
								double  solicitada=Double.valueOf(tabla.getValueAt(fila_anterior_seleccionada, 2).toString());
								if(surtida>solicitada) {
									fila = fila_anterior_seleccionada;
									tabla.setValueAt(0,fila_anterior_seleccionada, 1);
									JOptionPane.showMessageDialog(null, "La Cantidad Tecleada:"+surtida+"\nEs Mayor A La Solicitada:"+solicitada, "Avise Al Administrador Del Sistema !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
								}
								tabla.getSelectionModel().setSelectionInterval(fila, fila);
								new Cat_Cargar_Producto(tabla.getValueAt(fila, 0).toString().trim()).setVisible(true);
								tabla.getSelectionModel().setSelectionInterval(fila, fila);
			        		}else{
								JOptionPane.showMessageDialog(null, "Debe Seleccionar El Establecimiento Que Está Surtiendo", "Aviso !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
								return;
			        		}
			        	}	
			        }
		        });
		    }
		    
		    KeyListener op_validanumero_en_celda = new KeyListener() {
				public void keyTyped(KeyEvent e) {}
				public void keyReleased(KeyEvent e) {
					
					if(ObjTab.validacelda(tabla,"decimal", fila,columna)){
						
						double  surtida=Double.valueOf(tabla.getValueAt(fila, 1).toString().equals("")?"0":tabla.getValueAt(fila, 1).toString());
						double  solicitada=Double.valueOf(tabla.getValueAt(fila, 2).toString());
						if(surtida>solicitada) {
							fila = fila_anterior_seleccionada;
							JOptionPane.showMessageDialog(null, "La Cantidad Tecleada:"+surtida+"\nEs Mayor A La Solicitada:"+solicitada, "Avise Al Administrador Del Sistema !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
							tabla.setValueAt(0,fila_anterior_seleccionada, 1);
							return;
						}else{
							fila= fila==tabla.getRowCount()-1 ? 0 : fila+1;
						}
					}
					
					tabla.getSelectionModel().setSelectionInterval(fila, fila);
					new Cat_Cargar_Producto(tabla.getValueAt(fila, 0).toString().trim()).setVisible(true);
					tabla.getSelectionModel().setSelectionInterval(fila, fila);
					
				}
				public void keyPressed(KeyEvent e) {}
			};
			
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
			
			 public class Cat_Cargar_Producto extends JDialog{
				 
				 Container contProd = getContentPane();
				 JLayeredPane panelProd = new JLayeredPane();
				 
				 JTextField txtCodigo = new Componentes().text(new JCTextField(), "Codigo Producto", 20, "String");
				 JCButton btnBuscar = new JCButton("", "buscar.png", "Azul");
				 
				 Border blackline, etched, raisedbevel, loweredbevel, empty;
				 
				 public Cat_Cargar_Producto(String CodProd){
					 this.setModal(true);
					    this.contProd.add(panelProd);
						this.setSize(270,100);
						this.setResizable(false);
						this.setLocationRelativeTo(null);
						this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
						this.setTitle("Buscar Codigo De Producto");
						this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/articulo-icono-9036-48.png"));
						this.blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
						this.panelProd.setBorder(BorderFactory.createTitledBorder(blackline,"Presione F2 Para Abrir Filtro De Productos"));
				   	
						int x=20, y=30,width=80,height=20;
						this.panelProd.add(new JLabel("Cod. Prod:")).setBounds  (x	  	     ,y	,width   ,height );
						this.panelProd.add(txtCodigo).setBounds             	(x+width   	 ,y	,width+20,height );
						this.panelProd.add(btnBuscar).setBounds             	(x+width*2+20,y	,30		 ,height );
						
						txtCodigo.setText(CodProd);
						btnBuscar.addActionListener(opCargarProducto);
						txtCodigo.addActionListener(opCargarProducto);
						
						///filtro de Productos
				        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), "FiltroProd");
				          getRootPane().getActionMap().put("FiltroProd", new AbstractAction(){
						         public void actionPerformed(ActionEvent e){         
						        	 dispose();
						        	 new Cat_Filtro_De_Productos().setVisible(true);
					       	    }
						     });
				 }
				 
				 ActionListener opCargarProducto = new ActionListener() {
					 public void actionPerformed(ActionEvent arg0) {
				
							String codigo=txtCodigo.getText().toString().trim();
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
											    columna=ObjTab.RecorridoFocotabla_horizontal_x_columnas(tabla, fila, columna,1,"no","continuar");
											    
												tabla.setValueAt(txtCodigo.getText().toString().trim(), fila, columna);
												dispose();
												
											}else{
										    // esto se hace para que si no tiene costos se regrese a la celda del codigo del producto
												tabla.setValueAt("", fila, 0);
												tabla.setValueAt("", fila, 5);
												columna=ObjTab.RecorridoFocotabla_horizontal_x_columnas(tabla, fila, columna,0,"no", "no");
												JOptionPane.showMessageDialog(null, "El Codigo De Producto ["+Datos_Producto.getCod_Prod().trim()+"]\nNo es Posible Disminuirlo del Establecimiento Seleccionado \nYa Que Cuenta Con Costo Promedio 0 y Ultimo Costo 0  \nVerifique El Codigo del Producto y/o El Establecimiento Seleccionado", "Avise Al Administrador Del Sistema !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
												return;
											}
									}else{
										JOptionPane.showMessageDialog(null, "El Codigo De Producto Es Requerido", "Avise Al Administrador Del Sistema !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
										return;
									}
							}else {
								dispose();
								 new Cat_Filtro_De_Productos().setVisible(true);
							}
					}
				}; 
				 
			 }
			 
			//TODO FILTRO DE PRODUCTOS	
				public class Cat_Filtro_De_Productos extends JDialog{
					  Container cont = getContentPane();
					  JLayeredPane panel = new JLayeredPane();
					  Connexion con = new Connexion();
					  Runtime R = Runtime.getRuntime();
					@SuppressWarnings("rawtypes")
					private TableRowSorter trsfiltro;
					
					JTextField txtFiltrop = new Componentes().text(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<", 300, "String");
					Border blackline, etched, raisedbevel, loweredbevel, empty;
				    
					@SuppressWarnings({ "unchecked", "rawtypes" })
					public Cat_Filtro_De_Productos(){
						int ancho = 1024;
						int alto = Toolkit.getDefaultToolkit().getScreenSize().height-50;
						this.setSize(ancho, alto);
						this.setResizable(false);
						this.setLocationRelativeTo(null);
						this.setTitle("Filtro De Busqueda De Productos");
						this.setModal(true);
						this.setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/lista-icono-7220-32.png"));
						blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
						panel.setBorder(BorderFactory.createTitledBorder(blackline,"Doble Click A El Producto Deseado"));
						this.cont.add(panel);

						trsfiltro = new TableRowSorter(modelo2); 
						tabla2.setRowSorter(trsfiltro);
						txtFiltrop.setToolTipText("Filtro Por Producto");
						txtFiltrop.addKeyListener(opFiltro);

						int y = 20;
						panel.add(txtFiltrop).setBounds(15,y,500,20);
						panel.add(scroll_tabla_filtro).setBounds(15,y+=20,ancho-30,alto-70);
				        
						if(FActividadesCargado.equals("S")){
							datos_tabla_precargados();
						}else{
							init_tabla_filtro_productos();
							tablaprecargadaproductos= ObjTab.tabla_guardar(tabla2);
						  FActividadesCargado="S";
						}
						agregar(tabla2);
					}

					public void datos_tabla_precargados(){
						 modelo2.setRowCount(0);
						 String[][] tablacompleta =tablaprecargadaproductos;
						 Object[] vector = new Object[columnas2];
						for(int i=0;i<tablacompleta.length;i++){
							   for(int j=0;j<columnas2;j++){
								vector[j] = tablacompleta[i][j].toString();
								}
								modelo2.addRow(vector);
						}
					}

					
					KeyListener opFiltro = new KeyListener(){
						@SuppressWarnings("unchecked")
						public void keyReleased(KeyEvent arg0) {
							int[] columnas ={0,1,2,3,4,5};
							new Obj_Filtro_Dinamico_Plus(tabla2 , txtFiltrop.getText().toString().trim().toUpperCase(), columnas  );
							trsfiltro.setRowFilter(RowFilter.regexFilter(txtFiltrop.getText(), 0));
						}
						public void keyTyped(KeyEvent arg0) {}
						public void keyPressed(KeyEvent arg0) {}	
					    };
					
					private void agregar(final JTable tbl) {
						tbl.addMouseListener(new MouseListener() {
							public void mouseReleased(MouseEvent e) {
									if(e.getClickCount() == 2){
										int fila_Select = tabla2.getSelectedRow();
						    			String folio =  tabla2.getValueAt(fila_Select, 0).toString().trim();
						    			columna=ObjTab.RecorridoFocotabla_horizontal_x_columnas(tabla, fila, columna,1,"no","continuar");

										String cod_prod=new BuscarSQL().cod_prod_principal_bms(folio);// si el codigo es vacion retorna el cod_prod = 01491
										if(!cod_prod.equals("false no existe")){
												Obj_Ubicaciones_De_Productos  Datos_Producto= new Obj_Ubicaciones_De_Productos().buscardatos_producto(cod_prod,cmbEstablecimientoSurte.getSelectedItem().toString().trim().toUpperCase()+"");
												if(Datos_Producto.getCosto_Promedio() > 0 && Datos_Producto.getUltimo_Costo()>0){
													tabla.setValueAt(Datos_Producto.getCod_Prod().trim(), fila, 0);
													tabla.setValueAt(Datos_Producto.getDescripcion_Prod().trim(), fila, 5);
													tabla.setValueAt(Datos_Producto.getUltimo_Costo(), fila, 6);
													tabla.setValueAt(Datos_Producto.getCosto_Promedio(), fila, 7);
													tabla.setValueAt(Datos_Producto.getPrecio_de_venta(), fila, 8);
													tabla.setValueAt(Datos_Producto.getExistencia_Total(), fila, 9);
												    columna=ObjTab.RecorridoFocotabla_horizontal_x_columnas(tabla, fila, columna,1,"no","continuar");
												    
													tabla.setValueAt(folio.toString().trim(), fila, columna);
													dispose();
													
												}else{
											    // esto se hace para que si no tiene costos se regrese a la celda del codigo del producto
													tabla.setValueAt("", fila, 0);
													tabla.setValueAt("", fila, 5);
													columna=ObjTab.RecorridoFocotabla_horizontal_x_columnas(tabla, fila, columna,0,"no", "no");
													JOptionPane.showMessageDialog(null, "El Codigo De Producto ["+Datos_Producto.getCod_Prod().trim()+"]\nNo es Posible Disminuirlo del Establecimiento Seleccionado \nYa Que Cuenta Con Costo Promedio 0 y Ultimo Costo 0  \nVerifique El Codigo del Producto y/o El Establecimiento Seleccionado", "Avise Al Administrador Del Sistema !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
													return;
												}
										}else{
											JOptionPane.showMessageDialog(null, "El Codigo De Producto Es Requerido", "Avise Al Administrador Del Sistema !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
											return;
										}
								
									}
							}
							public void mousePressed(MouseEvent e) {}
							public void mouseExited(MouseEvent e) {}
							public void mouseEntered(MouseEvent e) {}
							public void mouseClicked(MouseEvent e) {}
						});
					}
				}
		}
		
//(FIN)AUTORIZACION DE ORDEN DE COMPRA INTERNA ---------------------------------------------------------------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		 public void reporte(String folioBMS, String folio_scoi){
				String basedatos="2.98";
				String vista_previa_reporte="no";
				int vista_previa_de_ventana=0;
				String reporte = "Obj_Reporte_De_Orden_De_Compra_Interna.jrxml";				
				String consulta = "exec orden_de_compra_interna_reporte_surtido '"+folioBMS+"','"+folio_scoi+"'";
				
				new Generacion_Reportes().Reporte(reporte, consulta, basedatos, vista_previa_reporte,vista_previa_de_ventana);
	    }
		 
		public static void main(String args[]){
			try{
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				new Cat_Orden_De_Compra_Interna_Surtido().setVisible(true);
			}catch(Exception e){	}
		}
	}

