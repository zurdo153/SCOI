package Cat_Contabilidad;

import java.awt.Container;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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

import Conexiones_SQL.Connexion;
import Conexiones_SQL.Generacion_Reportes;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Contabilidad.Obj_Alimentacion_De_Ordenes_De_Compra_Interna;
import Obj_Contabilidad.Obj_Orden_De_Gasto;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_tabla;
import Obj_Servicios.Obj_Servicios;

@SuppressWarnings("serial")
public class Cat_Autorizacion_De_Ordenes_De_Compra_Interna extends JFrame {
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
		public void init_tabla_principal(){
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
			
			String comandob = "orden_de_compra_interna_filtro 'AUTORIZACION'";
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
		
		
		Obj_Orden_De_Gasto gasto = new Obj_Orden_De_Gasto();
	    String aceptar_negar="";
		JToolBar menu_toolbar       = new JToolBar();
		public Cat_Autorizacion_De_Ordenes_De_Compra_Interna()	{
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
			this.campo.add(txtFiltro).setBounds               (x=15  ,y+=25  ,ancho-40 ,height);
			this.campo.add(scroll_tablaP).setBounds           (x     ,y+=20  ,ancho-40 ,alto-150);
			agregar(tablaP);
			init_tabla_principal();
			btnActualizar.addActionListener(OpActualizar);
			
			txtTotal.setEditable(false);
			cont.add(campo);
			txtFiltro.requestFocus();
	        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
	        getRootPane().getActionMap().put("escape", new AbstractAction(){ public void actionPerformed(ActionEvent e)
	            {    txtFiltro.setText("");             	 txtFiltro.requestFocus();    	    }     });
		}
		
	   ActionListener OpActualizar = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
				init_tabla_principal();
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
					
			 	 if(e.getClickCount() == 1){
			 		 
			 		if(status_orden.equals("EN VALIDACION")){
	                     	new Cat_Ordenes_De_Compra_Interna_Detalle(Integer.valueOf(tablaP.getValueAt(fila, 0).toString())).setVisible(true);
		 			}else{
		 				
		 				JOptionPane.showMessageDialog(null,  "El Status De La Orden De Compra Interna Seleccionada Es:"+status_orden+"\n "
		 													+"Solo Se Pueden Modificar Las Ordenes De Compra Interna Con Status[EN VALIDACION]","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
		 				return;
		 			}
	    				
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

			int columnas = 3,checkbox=-1;
			public void init_tabla(){
		    	this.tabla.getColumnModel().getColumn(0).setMinWidth(400);	
		    	this.tabla.getColumnModel().getColumn(1).setMinWidth(80);
		    	this.tabla.getColumnModel().getColumn(2).setMinWidth(120);
		    	
				String comando="Select '' as Descripcion, 0 Cantidad, '' as Unidad" ;
				String basedatos="26",pintar="si";
				ObjTab.Obj_Refrescar(tabla,modelo, columnas, comando, basedatos,pintar,checkbox);
				modelo.setRowCount(0);
		    }
			
			JCButton btnAceptar  = new JCButton("Autorizar"   ,"Aplicar.png"                ,"Azul"); 
//			JCButton btnNegar    = new JCButton("Negar"       ,"Delete.png"                 ,"Azul"); 
			JCButton btnCancelar = new JCButton("Cancelar"    ,"cancelar-icono-4961-16.png" ,"Azul"); 
			JCButton btnImprimir = new JCButton("Imprimir"    ,"imprimir-16.png"            ,"Azul");
			
			@SuppressWarnings("rawtypes")
			public Class[] basemovimientos (){
				Class[] types = new Class[columnas];
				for(int i = 0; i<columnas; i++){types[i]= java.lang.Object.class;}
				 return types;
			}
			public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"Descripcion","Cantidad","Unidad"}){
				 @SuppressWarnings("rawtypes")
					Class[] types = basemovimientos();
					@SuppressWarnings({ "rawtypes", "unchecked" })
					public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
					public boolean isCellEditable(int fila, int columna){if(columna>0){return true;}else{ return false;}}
			};
			
			JTable tabla = new JTable(modelo);
			public JScrollPane scroll_tabla = new JScrollPane(tabla);
			
			JTextField txtFolio       = new Componentes().text(new JCTextField()  ,"Folio"						,30   ,"String");
			JTextField txtDescripcion = new Componentes().text(new JCTextField()  ,"Descripcion Del Producto"	,30   ,"String");
			JTextField txtFolioSolic = new Componentes().text(new JCTextField()  ,"Folio Solicita"				,30   ,"String");
			JTextField txtSolicitante = new Componentes().text(new JCTextField()  ,"Persona Que Solicita"		,30   ,"String");
			JTextField txtFolioservici= new Componentes().text(new JCTextField()  ,"Folio Servicio"				,30   ,"String");
			JTextField txtDetalleServi= new Componentes().text(new JCTextField()  ,"Detalle Servicio"			,350  ,"String");
			
		    JTextArea txaUso       = new Componentes().textArea(new JTextArea(), "Uso De La Mercancia", 300);
			JScrollPane Uso        = new JScrollPane(txaUso);

			JTextField txtStatus = new Componentes().text(new JCTextField()  ,"Status"			,350  ,"String");
			JTextField txtEstablecimiento = new Componentes().text(new JCTextField()  ,"Establecimiento"			,350  ,"String");
			
			@SuppressWarnings("rawtypes")
			JComboBox cmbUnidades = new JComboBox();

			JRadioButton rbProveedor = new JRadioButton("Proveedor");
			JRadioButton rbEmpleado  = new JRadioButton("Empleado");
			ButtonGroup  grupo       = new ButtonGroup();
			
			Border blackline, etched, raisedbevel, loweredbevel, empty;

			String[][] tablaprecargadaordenes;
		    Object[] vector = new Object[7];

		   public  Cat_Ordenes_De_Compra_Interna_Detalle(int folio_orden_de_compra_interna){
			   
			    this.setModal(true);
			    this.cont.add(panel);
				this.setSize(650,530);
				this.setResizable(false);
				this.setLocationRelativeTo(null);
				this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				this.setTitle("Alimentacion De Ordenes De Compra Interna");
				this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/articulo-icono-9036-48.png"));
				this.blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
				this.panel.setBorder(BorderFactory.createTitledBorder(blackline,"Alimentacion De Ordenes De Compra Interna"));
		   	
		   		this.grupo.add(rbProveedor);
		   		this.grupo.add(rbEmpleado);
		   		this.rbEmpleado.setSelected(true);
		   		
		   	    this.menu_toolbar.add(btnAceptar    );
				this.menu_toolbar.addSeparator(   );
				this.menu_toolbar.addSeparator(   );
//				this.menu_toolbar.add(btnNegar   );
//				this.menu_toolbar.addSeparator(   );
//				this.menu_toolbar.addSeparator(   );
				this.menu_toolbar.add(btnCancelar );
				this.menu_toolbar.addSeparator(   );
				this.menu_toolbar.addSeparator(   );
				this.menu_toolbar.add(btnImprimir   );
				this.menu_toolbar.setFloatable(false);
				
				int x=20, y=20,width=122,height=20, sep=130;
				panel.add(menu_toolbar).setBounds                       (x         ,y      ,400     ,height );
				panel.add(txtFolio).setBounds                           (x         ,y+=30  ,width   ,height );
				panel.add(txtStatus).setBounds                         (x+=sep    ,y      ,width   ,height );
				panel.add(new JLabel("Destino De Mercancia:")).setBounds(x+=sep    ,y      ,width   ,height );
				panel.add(txtEstablecimiento).setBounds                 (x+=110    ,y      ,233     ,height );
				panel.add(txtFolioSolic).setBounds                     	(x=20      ,y+=25  ,50     ,height );
				panel.add(txtSolicitante).setBounds                     (x=70      ,y	   ,370     ,height );
				panel.add(rbEmpleado).setBounds                         (x+390     ,y      ,90      ,height );		
				panel.add(rbProveedor).setBounds                        (x+480     ,y      ,90      ,height );  
				
				this.panel.add(txtFolioservici).setBounds               (x=20      ,y+=25  ,50      ,height );
				this.panel.add(txtDetalleServi).setBounds               (x=70      ,y      ,515     ,height );
				
				panel.add(new JLabel("Uso De La Mercancia:")).setBounds (x=20      ,y+=20  ,width   ,height );
				panel.add(Uso).setBounds                                (x         ,y+=15  ,602     ,50     );
				panel.add(txtDescripcion).setBounds                     (x         ,y+=55  ,390     ,height );
				panel.add(scroll_tabla).setBounds                       (x         ,y+=22  ,602     ,250    );

				panel_false();
				init_tabla();
				llenarDatos(folio_orden_de_compra_interna);
				
				btnAceptar.addActionListener(opMov);
//				btnNegar.addActionListener(opMov);
				btnCancelar.addActionListener(opMov);
				btnImprimir.addActionListener(opMov);
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
					for(Object[] reg: productos){
						modelo.addRow(reg);
					}
		   }
		   
		    public void panel_false(){
		    	txtFolio.setEditable(false);
				txtStatus.setEditable(false);
				txtEstablecimiento.setEditable(false);
			    txtDescripcion.setEditable(false); 
				txaUso.setLineWrap(true); 
				txaUso.setWrapStyleWord(true);
				txaUso.setEditable(false);
				txtFolioSolic.setEditable(false);
				txtSolicitante.setEditable(false);
				rbEmpleado.setEnabled(false);
				rbProveedor.setEnabled(false);
				
				txtFolioservici.setEditable(false);
				txtDetalleServi.setEditable(false);
				tabla.setEnabled(false);
		    }
		    
		    ActionListener opMov = new ActionListener(){
		    	public void actionPerformed(ActionEvent e){
		    		
		    		Obj_Alimentacion_De_Ordenes_De_Compra_Interna ordenCI = new Obj_Alimentacion_De_Ordenes_De_Compra_Interna();
		    		String btn = e.getActionCommand();
		    		
		    		if(!btn.equals("Imprimir")){
//		    			System.out.println("Modificar Status De Orden De Compra Interna a:"+btn);
			    			if(ordenCI.autorizacion(Integer.valueOf(txtFolio.getText().toString().trim()), btn)){
			    				
				    				//atualizar tabla de autorizaciones
			    					init_tabla_principal();
				    				dispose();
					                JOptionPane.showMessageDialog(null, "Se Actualizo Correctamente La Orden De Compra Interna", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
			        			
			    			}else{
			    				JOptionPane.showMessageDialog(null, "La Orden De Compra Interna No Se Actualizo", "Avise Al Administrador Del Sistema !!!",JOptionPane.ERROR_MESSAGE, new ImageIcon("Imagen/usuario-icono-eliminar5252-64.png"));
						    	return;
			    			}
		    			
		    		}else{
		    			reporte();
		    		}
		    	}
		    };
	
		    public void reporte(){
//		    	System.out.println("Generar Reprote De Orden De Compra Interna(PENDIENTE DE DESARROLLAR)");
					String basedatos="2.26";
					String vista_previa_reporte="no";
					int vista_previa_de_ventana=0;
					String comando="declare @folio int"
							+ " set @folio = '"+txtFolio.getText().toString()+"' "
							+ " select RIGHT('0000000000'+convert(varchar(10),ci.folio),10) as folio "
							+ "		,case ci.status when 'E' then 'EN VALIDACION' "
							+ " 					when 'A' then 'AUTORIZADO' "
							+ " 					when 'S' then 'SURTIDO'"
							+ "						when 'C' then 'CANCELADO' end status "
							+ "		,convert(varchar(20),case ci.status when 'E' then ci.fecha_guardado "
							+ " 										when 'A' then ci.fecha_autorizado "
							+ "											when 'S' then ci.fecha_surtido "
							+ "											when 'C' then ci.fecha_ultima_modificacion end,103) as fecha "
							+ "		,'' as surte "
							+ "		,estab_dest.establecimiento as destino "
							+ "		,ci.uso_de_mercancia "
							+ "		,case ci.status when 'S' then pci.cantidad_surtida "
							+ "						else pci.cantidad_solicitada end cantidad "
							+ "		,case ci.status when 'S' then "
							+ "										case pci.unidad_surtida when 'B' then 'BOLSA' "
							+ "																when 'L' then 'LITROS' "
							+ "																when 'M' then 'METROS' "
							+ "																when 'P' then 'PIEZAS' end "
							+ "						else			case pci.unidad_solicitada when 'B' then 'BOLSA' "
							+ "																	when 'L' then 'LITROS' "
							+ "																	when 'M' then 'METROS' "
							+ "																	when 'P' then 'PIEZAS' end "
							+ "						end unidad "
							+ "		,pci.descripcion_de_producto+case ci.status when 'S' then ' ('+pci.cod_prod+')' "
							+ "						else '' end producto "
							+ "from orden_de_compra_interna ci "
							+ "inner join tb_establecimientosBMS_por_servidor estab_dest on estab_dest.cod_estab = ci.cod_estab_destino "
							+ "inner join productos_en_orden_de_compra_interna pci on pci.folio_orden_de_compra_interna = ci.folio "
							+ "where ci.folio = @folio";
							
					String reporte = "Obj_Reporte_De_Orden_De_Compra_Interna.jrxml";
					 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
				
		    }
		    
		   
//			public static void main(String args[]){
//				try{
//					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//					int folio_orden_de_compra_interna = 4;
//					new Cat_Ordenes_De_Compra_Interna_Detalle(folio_orden_de_compra_interna).setVisible(true);
//				}catch(Exception e){	}
//			}
		};
		
//(FIN)AUTORIZACION DE ORDEN DE COMPRA INTERNA ---------------------------------------------------------------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		public static void main(String args[]){
			try{
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				new Cat_Autorizacion_De_Ordenes_De_Compra_Interna().setVisible(true);
			}catch(Exception e){	}
		}
	}

