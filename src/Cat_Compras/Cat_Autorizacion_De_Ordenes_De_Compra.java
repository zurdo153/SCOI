package Cat_Compras;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

import Cat_Inventarios.Cat_Consulta_De_Cotizacion;
import Cat_Principal.EmailSenderService;
import Conexiones_SQL.Connexion;
import Conexiones_SQL.Generacion_Reportes;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Compras.Obj_Orden_De_Compra;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_tabla;
import Obj_Servicios.Obj_Correos;

@SuppressWarnings("serial")
public class Cat_Autorizacion_De_Ordenes_De_Compra extends JFrame{
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();

	Object[][] tabla_usuario_valido = null;
	
	Obj_Usuario usuario                 = new Obj_Usuario().LeerSession();
	Obj_Orden_De_Compra orden_de_compra = new Obj_Orden_De_Compra      ();
	Obj_tabla ObjTab                    = new Obj_tabla                ();	
	
	int columnaspo = 17,checkbox=-1;
	public void init_tablaordenes(){
	 	this.tablafilordenes.getColumnModel().getColumn(0).setMinWidth (70 );	
	 	this.tablafilordenes.getColumnModel().getColumn(0).setMaxWidth (70 );	
    	this.tablafilordenes.getColumnModel().getColumn(1).setMinWidth (120);		
    	this.tablafilordenes.getColumnModel().getColumn(2).setMinWidth (120);
    	this.tablafilordenes.getColumnModel().getColumn(2).setMaxWidth (120);

    	this.tablafilordenes.getColumnModel().getColumn(3).setMinWidth (150);
    	this.tablafilordenes.getColumnModel().getColumn(3).setMaxWidth (150);
    	this.tablafilordenes.getColumnModel().getColumn(4).setMinWidth (150);
    	this.tablafilordenes.getColumnModel().getColumn(4).setMaxWidth (150);
    	this.tablafilordenes.getColumnModel().getColumn(5).setMinWidth (320);
    	this.tablafilordenes.getColumnModel().getColumn(6).setMinWidth (120);
    	this.tablafilordenes.getColumnModel().getColumn(7).setMinWidth (200);
    	this.tablafilordenes.getColumnModel().getColumn(8).setMinWidth (120);
    	this.tablafilordenes.getColumnModel().getColumn(9).setMinWidth (200);
    	this.tablafilordenes.getColumnModel().getColumn(10).setMinWidth (80 );
    	this.tablafilordenes.getColumnModel().getColumn(11).setMinWidth(80 );
    	this.tablafilordenes.getColumnModel().getColumn(12).setMinWidth(80 );
    	this.tablafilordenes.getColumnModel().getColumn(13).setMinWidth(80 );
    	this.tablafilordenes.getColumnModel().getColumn(14).setMinWidth(80);
    	this.tablafilordenes.getColumnModel().getColumn(15).setMinWidth(350);
    	
		String comandof="exec orden_de_compra_filtro_autorizacion ";
    	
		String basedatos="26",pintar="si";
		modeloor_filtro.setRowCount(0);
		ObjTab.Obj_Refrescar(tablafilordenes,modeloor_filtro, columnaspo, comandof, basedatos,pintar,checkbox);
    }
	
	@SuppressWarnings("rawtypes")
	public Class[] baseOr (){
		Class[] types = new Class[columnaspo];
		for(int i = 0; i<columnaspo; i++){  
			if(i==0){
				types[i]=java.lang.Boolean.class;
			}else{
				types[i]=java.lang.Object.class;
			}
		}
		 return types;
	}
	
	public DefaultTableModel modeloor_filtro = new DefaultTableModel(null, new String[]{"Folio Orden","Establecimiento","Estatus Orden","Estatus Surtido","Estatus Tiempo","Proveedor","Fecha Elaboracion","Elaboro","Fecha Autorizacion","Autorizo","Total","Condicion Pago","Plazo", "Fecha Expiracion", "Cantidad  Prod.","Notas","Cod. Proveedor"}){
		 @SuppressWarnings("rawtypes")
			Class[] types = baseOr();
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
			public boolean isCellEditable(int fila, int columnaspo){return false;}
	};
	
	  JTable tablafilordenes = new JTable(modeloor_filtro){
     	 public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
     	        Component componente = super.prepareRenderer(renderer, row, col);
     	        if(col==2){
     	        	 Color c = Color.orange;
                  if(tablafilordenes.getValueAt(row,2).toString().trim().equals("EN REVISION")) {   
                	  componente.setBackground(c);
                  }
     	        }
    	     return componente;
     	 }
     };
     
     
	public JScrollPane scroll_tablafp = new JScrollPane(tablafilordenes);
     @SuppressWarnings({ "rawtypes", "unused" })
    private TableRowSorter trsfiltro;
	 
	JTextField txtBuscar         = new Componentes().textfiltro(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<", 500, "String",tablafilordenes,columnaspo );
	JTextField txtPedientes      = new Componentes().text      (new JCTextField(), "Cant. Pendientes"                                       , 150, "String");
	JTextField txtFolioEmpleado  = new Componentes().text      (new JCTextField(), "Folio Colaborador"                                      , 150, "String");
	JTextField txtNombreEmpleado = new Componentes().text      (new JCTextField(), "Nombre Colaborador"                                     , 150, "String");
	JTextField txtFoliousuariobms= new Componentes().text      (new JCTextField(), "Folio Usuario BMS"                                      , 150, "String");
	JTextField txtNombrebms      = new Componentes().text      (new JCTextField(), "Nombre Usuario BMS"                                     , 150, "String");
	
	JCButton btnActualizar     = new JCButton("Actualizar"       ,"Actions-view-refresh-icon.png","Azul"); 

	JLabel fondo = new JLabel();
	JToolBar menu_toolbar       = new JToolBar();
	
	String nombre_usuario=usuario.getNombre_completo();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Cat_Autorizacion_De_Ordenes_De_Compra(){
		this.setTitle("Consulta De Orden De Compra");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/orden_de_compra.png"));
		this.panel.setBorder(BorderFactory.createTitledBorder("De Click A La Orden De Compra Que Desea Revisar "));
		this.trsfiltro = new TableRowSorter(modeloor_filtro); 

		int ancho = Toolkit.getDefaultToolkit().getScreenSize().width;
		int alto = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds()); 
		
		try {
			tabla_usuario_valido=orden_de_compra.buscar_usuario_valido(usuario.getFolio());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		if(String.valueOf(tabla_usuario_valido[0][0].toString()).equals("0")) {
		  JOptionPane.showMessageDialog(null, " El Usuario "+nombre_usuario+""
		  		                            + "\nNo Se encontro En BMS, Es Requerido Se Alimente En La Opcion De Usuarios De BMS\n"
                                            + " Su Folio De SCOI Para Poder Utilizar Esta Opcion","Aviso", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));        

		  this.panel.add(fondo).setBounds(200,0,1024,600);
	      ImageIcon imagen_estatus = new ImageIcon(System.getProperty("user.dir")+"/Imagen/Logotipo_IZAGAR.jpg");
	      Icon icono_estatus = new ImageIcon(imagen_estatus.getImage().getScaledInstance(550, 550, Image.SCALE_DEFAULT));
	      fondo.setIcon(icono_estatus);
		  cont.add(panel);
	     return;
		}else{
	    this.menu_toolbar.add(btnActualizar  );
	    this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.addSeparator(   );
//	    this.menu_toolbar.add(btnModificar);
		this.menu_toolbar.setFloatable(false);
				
		int x=10,y=20,width=220,height=20;
		this.panel.add(menu_toolbar).setBounds                    (x      ,y     ,width,height );
		this.panel.add(txtFolioEmpleado).setBounds                (x      ,y+=30 ,80   ,height );
		this.panel.add(txtNombreEmpleado).setBounds               (x+80   ,y     ,320  ,height );
		this.panel.add(txtFoliousuariobms).setBounds              (x+400  ,y     ,80   ,height );
		this.panel.add(txtNombrebms).setBounds                    (x+480  ,y     ,320  ,height );
		this.panel.add(new JLabel("Ordenes Pendientes: ")).setBounds(x+835,y     ,width,height );
		this.panel.add(txtPedientes).setBounds                    (x+935  ,y     ,60   ,height );
		this.panel.add(txtBuscar).setBounds                       (x      ,y+=30 ,ancho-30,height   );
		this.panel.add(scroll_tablafp).setBounds                  (x      ,y+=20 ,ancho-30,alto-y-75);
		this.agregar(tablafilordenes);
		
		init_tablaordenes();
		
		this.txtPedientes.setText(modeloor_filtro.getRowCount()+"");
		this.txtFolioEmpleado.setText(usuario.getFolio()+"");
		this.txtNombreEmpleado.setText(nombre_usuario+"");
		this.txtFoliousuariobms.setText(tabla_usuario_valido[0][0].toString());
		this.txtNombrebms.setText(tabla_usuario_valido[0][1].toString());
		
		this.txtPedientes.setEditable(false);
		this.txtFolioEmpleado.setEditable(false);
		this.txtNombreEmpleado.setEditable(false);
		this.txtFoliousuariobms.setEditable(false);
		this.txtNombrebms.setEditable(false);
		this.btnActualizar.addActionListener(op_actualizar);
		cont.add(panel);
        this.addWindowListener(new WindowAdapter(){public void windowOpened( WindowEvent e ){txtBuscar.requestFocus();}});
		}
		
		
	}

		ActionListener op_actualizar = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
             init_tablaordenes();
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
      		int fila = tablafilordenes.getSelectedRow();
//      		String folio ,String Establecimiento ,String Cod_Prov ,String Nombre_Proveedor ,String Fecha_Elaboracion ,String Elaboro ,String Total ,String Condicion ,String Plazo,String Fecha_Expira
		    new Cat_Filtro_Detalle_Orden_de_Compra(tablafilordenes.getValueAt(fila,0 )+"" 
		    		                              ,tablafilordenes.getValueAt(fila,1 )+""
		    		                              ,tablafilordenes.getValueAt(fila,16)+"" 
		    		                              ,tablafilordenes.getValueAt(fila,5 )+"" 
		    		                              ,tablafilordenes.getValueAt(fila,6 )+""
		    		                              ,tablafilordenes.getValueAt(fila,7 )+""
		    		                              ,tablafilordenes.getValueAt(fila,10)+""
		    		                              ,tablafilordenes.getValueAt(fila,11)+""
		    		                              ,tablafilordenes.getValueAt(fila,12)+""
		    		                              ,tablafilordenes.getValueAt(fila,13)+""
		    		                              ,tablafilordenes.getValueAt(fila,15)+""
		    		                              ,tablafilordenes.getValueAt(fila,2)+""
		    	                                  ).setVisible(true);		
		    		
        };
 
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////TODO inicia filtro_detalle_orden_de_compra	
    	public class Cat_Filtro_Detalle_Orden_de_Compra extends JDialog{
    		Container contf = getContentPane();
    		JLayeredPane panelf = new JLayeredPane();
    		Connexion con = new Connexion();
    		Obj_tabla ObjTab =new Obj_tabla();
    			int columnasp2 = 18,checkbox2=-1;
    			public void init_tablafp2(String Folio_ordencompra){
    		    	this.tablafp2.getColumnModel().getColumn(0).setMinWidth(55);
    		    	this.tablafp2.getColumnModel().getColumn(1).setMinWidth(375);
    		    	this.tablafp2.getColumnModel().getColumn(14).setMinWidth(230);
    		    	this.tablafp2.getColumnModel().getColumn(15).setMinWidth(500);
    		    	
    		    	String comandof="exec sp_orden_de_compra_a_detalle '"+Folio_ordencompra+"'" ;
    				String basedatos="200",pintar="si";
    				ObjTab.Obj_Refrescar(tablafp2,modelof2, columnasp2, comandof, basedatos,pintar,checkbox2);
    		    }
    			
    			@SuppressWarnings("rawtypes")
    			public Class[] base2 (){
    				Class[] types = new Class[columnasp2];
    				for(int i = 0; i<columnasp2; i++){types[i]= java.lang.Object.class;}
    				 return types;
    			}
    			
    			public DefaultTableModel modelof2 = new DefaultTableModel(null, new String[]{"Codigo","Descripcion","Cantidad","Precio Orden Compra","Costo Promedio","Precio Venta", "Margen" ,"M.Familia","Precios Por Volumen","Descuentos","Descuento", "Existencia","Venta Ultimos 90 Dias", "Importe Compra","Razon","Observaciones","Autorizo","Ultimo Costo"}){
    				 @SuppressWarnings("rawtypes")
    					Class[] types = base2();
    					@SuppressWarnings({ "rawtypes", "unchecked" })
    					public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
    					public boolean isCellEditable(int fila, int columna){ if(columna>13 && columna<16) {return true;}else{return false;}     }
    			};
    			
               String Valida="Si";
    		   JTable tablafp2 = new JTable(modelof2){
                	 public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
                	        Component componente = super.prepareRenderer(renderer, row, col);
                	        if(col==6){
                              float margen_ordeno = Float.valueOf(tablafp2.getValueAt(row,6).toString().trim());
                              float margen_meta   = Float.valueOf(tablafp2.getValueAt(row,7).toString().trim());
                              Color c = Color.green;
                                 if(margen_meta>margen_ordeno) {                          
                                	 c = new java.awt.Color(255,0,0); 
                                 }
		                         componente.setBackground(c);
                	        }
                	        
                	        float precio_orden = Float.valueOf(tablafp2.getValueAt(row,3).toString().trim());
                            float precio_venta = Float.valueOf(tablafp2.getValueAt(row,5).toString().trim());
                	        if(col==3 && precio_orden>precio_venta){                                
                                Color c= Color.magenta;                                   
  		                        componente.setBackground(c);
                  	        }
                	                   	        
               	     return componente;
                	 }
                };
                
    			public JScrollPane scroll_tablafp2 = new JScrollPane(tablafp2);
    		     @SuppressWarnings({ "rawtypes" })
    		    private TableRowSorter trsfiltro2;

    		 	JTextField txtFolio            = new Componentes().text(new JCTextField(), "Folio Orden"       ,12  ,"String");
    		 	JTextField txtEstablecimiento  = new Componentes().text(new JCTextField(), "Establecimiento"   ,250 ,"String");
    			JTextField txtCod_Prov         = new Componentes().text(new JCTextField(), "Cod. Prv"          ,12  ,"String");
    		 	JTextField txtProveedor        = new Componentes().text(new JCTextField(), "Nombre Proveedor"  ,250 ,"String");
    		 	JTextField txtFechaElaboracion = new Componentes().text(new JCTextField(), "Fecha Elaboración" ,120 ,"String");
    			JTextField txtTotal            = new Componentes().text(new JCTextField(), "Total"             ,12  ,"String");
    			JTextField txtCondicion        = new Componentes().text(new JCTextField(), "Condición"         ,12  ,"String");
    			JTextField txtPlazo            = new Componentes().text(new JCTextField(), "Plazo"             ,20  ,"String");
    			JTextField txtFechaExpira      = new Componentes().text(new JCTextField(), "Fecha Expira"      ,120 ,"String");
    			JTextField txtNotas            = new Componentes().text(new JCTextField(), "Notas"             ,500 ,"String");
        		JTextField txtBuscarfp         = new Componentes().textfiltro(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<", 300, "String",tablafp2,columnasp2);
        		
    			JTextArea txaNotas             = new Componentes().textArea(new JTextArea()                    ,""  ,500     );
    			JScrollPane scrollDetalle      = new JScrollPane(txaNotas);
    			
    		 	JButton btnAutorizar           = new JCButton("Autorizar"                                         ,"Aplicar.png"                            ,"Azul" );
    		 	JButton btnSolicitar           = new JCButton("Solicitar Benchmarking"                            ,"investigacion-icono-9565-16.png"        ,"Azul" );	
    		 	JButton btnSolicitarRevision   = new JCButton("Solicitar Autorización Por Condiciones De Precio"  ,"vista-previa-del-ojo-icono-7248-16.png" ,"Azul" );	
    		 	JButton btngenerarSAut         = new JCButton("Imprimir Pre Orden De Compra"                      ,"imprimir-16.png"                        ,"Azul" );

    		 	JButton btnPrecioCompetencia   = new JCButton("Precio De Competencia"                                 ,"Aplicar.png"                            ,"Azul" );

    		 	
    			JToolBar menu_toolbarfiltro    = new JToolBar();
    			
	    		@SuppressWarnings("rawtypes")
				JComboBox cmbRazon = new JComboBox();
	    		String productos="Codigo/ Descripcion                    / Cantidad /$Costo OrdenC. /$Costo Promedio /$Precio Venta /%Margen /%M.Familia \n";
    		@SuppressWarnings({ "rawtypes", "unchecked" })
    		public Cat_Filtro_Detalle_Orden_de_Compra(String folio ,String Establecimiento ,String Cod_Prov ,String Nombre_Proveedor ,String Fecha_Elaboracion ,String Elaboro ,String Total ,String Condicion ,String Plazo,String Fecha_Expira,String Notas, String StatusOrden){
    			this.setResizable(false);
    			this.setLocationRelativeTo(null);
    			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    			this.setModal(true);
    			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Filter-List-icon32.png"));
    			this.panelf.setBorder(BorderFactory.createTitledBorder("Orden De Compra"));
    			this.setTitle("Detalle De Orden De Compra Seleccionada");
    			this.trsfiltro2 = new TableRowSorter(modelof2); 
    			this.tablafp2.setRowSorter(trsfiltro2);
    			int x=10,y=20,width=220,height=20;
    			
    			int ancho = Toolkit.getDefaultToolkit().getScreenSize().width;
    			int alto = Toolkit.getDefaultToolkit().getScreenSize().height;
    			this.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds()); 
    			
    			this.menu_toolbarfiltro.add(btnPrecioCompetencia );
      		    this.menu_toolbarfiltro.addSeparator(   );
      			this.menu_toolbarfiltro.addSeparator(   );
    		    this.menu_toolbarfiltro.add(btngenerarSAut  );
    		    this.menu_toolbarfiltro.addSeparator(   );
    			this.menu_toolbarfiltro.addSeparator(   );
    		    this.menu_toolbarfiltro.add(btnSolicitar);
    		    this.menu_toolbarfiltro.addSeparator(   );
    			this.menu_toolbarfiltro.addSeparator(   );
    			this.menu_toolbarfiltro.add(btnSolicitarRevision);
    		    this.menu_toolbarfiltro.addSeparator(   );
    			this.menu_toolbarfiltro.addSeparator(   );
    		    this.menu_toolbarfiltro.add(btnAutorizar);
    			this.menu_toolbarfiltro.setFloatable(false);
    			
    			this.panelf.add(menu_toolbarfiltro).setBounds               (x      ,y     ,ancho ,height );    			
    			this.panelf.add(new JLabel("Folio Orden: ")).setBounds      (x      ,y+=20 ,width ,height );
    			this.panelf.add(new JLabel("Establecimiento: ")).setBounds  (x+=85  ,y     ,width ,height );
    			this.panelf.add(new JLabel("Cod.Provr: ")).setBounds        (x+=100 ,y     ,width ,height );
    			this.panelf.add(new JLabel("Nombre Proveedor: ")).setBounds (x+=60  ,y     ,width ,height );
    			this.panelf.add(new JLabel("Total Orden: ")).setBounds      (x+=353 ,y     ,width ,height );
    			this.panelf.add(new JLabel("Cond.Pago: ")).setBounds        (x+=70  ,y     ,width ,height );
    			this.panelf.add(new JLabel("Dias Plazo: ")).setBounds       (x+=65  ,y     ,width ,height );
    			this.panelf.add(new JLabel("F.Elaboracion:")).setBounds     (x+=60  ,y     ,width ,height );
    			this.panelf.add(new JLabel("Expira Orden:")).setBounds      (x+=130 ,y     ,width ,height );
    			    			
    			this.panelf.add(txtFolio).setBounds                         (x=10   ,y+=15 ,70    ,height );
    			this.panelf.add(txtEstablecimiento).setBounds               (x+=70  ,y     ,112   ,height );
    			this.panelf.add(txtCod_Prov).setBounds                      (x+=112 ,y     ,60    ,height );
    			this.panelf.add(txtProveedor).setBounds                     (x+=60  ,y     ,353   ,height );
    			this.panelf.add(txtTotal).setBounds                         (x+=353 ,y     ,70    ,height );
    			this.panelf.add(txtCondicion).setBounds                     (x+=70  ,y     ,70    ,height );
    			this.panelf.add(txtPlazo).setBounds                         (x+=70  ,y     ,50    ,height );
    			this.panelf.add(txtFechaElaboracion).setBounds              (x+=50  ,y     ,130   ,height );
    			this.panelf.add(txtFechaExpira).setBounds                   (x+=130 ,y     ,80    ,height );
    			this.panelf.add(txtNotas).setBounds                         (x=10   ,y+=20 ,995   ,height );
    			
    			this.panelf.add(txtBuscarfp).setBounds                      (x      ,y+=30 ,ancho-30,height );
    			this.panelf.add(scroll_tablafp2).setBounds                  (x      ,y+=20 ,ancho-30,alto-y-75);
    			
    		    txtFolio           .setText(folio            );
    		    txtEstablecimiento .setText(Establecimiento  );
    		    txtCod_Prov        .setText(Cod_Prov         );
    		    txtProveedor       .setText(Nombre_Proveedor );
    		    txtFechaElaboracion.setText(Fecha_Elaboracion);
    		    txtTotal           .setText(Total            );
    		    txtCondicion       .setText(Condicion        );
    		    txtPlazo           .setText(Plazo            );
    		    txtFechaExpira     .setText(Fecha_Expira     );
    		    txtNotas           .setText(Notas +">>Elaboro:"+Elaboro);
    		    
    		    txtFolio           .setEditable(false);
    		    txtEstablecimiento .setEditable(false);
    		    txtCod_Prov        .setEditable(false);
    		    txtProveedor       .setEditable(false);
    		    txtFechaElaboracion.setEditable(false);
    		    txtTotal           .setEditable(false);
    		    txtCondicion       .setEditable(false);
    		    txtPlazo           .setEditable(false);
    		    txtFechaExpira     .setEditable(false);
    		    txtNotas           .setEditable(false);
    		    
    			this.btngenerarSAut.addActionListener(new opGenerar("POC"));
    			this.init_tablafp2(folio);
    			
    			Seleccionar_Respuesta(tablafp2);
    			btnSolicitar.setEnabled(false);
    			btnSolicitarRevision.setEnabled(false);
    			
                for(int i=0;i<tablafp2.getRowCount();i++) {
	                  if(Float.valueOf(tablafp2.getValueAt(i,7).toString().trim())>Float.valueOf(tablafp2.getValueAt(i,6).toString().trim()) ) {
                    	  btnAutorizar.setEnabled(false);
                    	  btnSolicitarRevision.setEnabled(true);
                    	  btnSolicitar.setEnabled(true);  
                    	  Valida="No";   
                      }
                      if(Float.valueOf(tablafp2.getValueAt(i,7).toString().trim())>Float.valueOf(tablafp2.getValueAt(i,6).toString().trim()) &&  tablafp2.getValueAt(i,6).toString().trim().equals("") ) {
                    	  tablafp2.setValueAt("Seleccione Una Razon ", i, 14);
                      }
                 }        
                
                if((StatusOrden).equals("EN REVISION")){
                	 btnAutorizar.setEnabled(false);
               	     btnSolicitar.setEnabled(false);
               	     tablafp2.setEnabled(false);
    			 JOptionPane.showMessageDialog(null,"Esta Orden De Compra Está En Revisión Por Lo Que Solo Podrá Ver Sus Datos !","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/aplicara-el-dialogo-icono-6256-32.png"));
         		}
                
                btnSolicitar.addActionListener(op_solicitar_revision_de_margenes);
                btnSolicitarRevision.addActionListener(op_Solicitar_Autorizacion_Por_Condiciones_De_Precio);
                btnAutorizar.addActionListener(op_autorizar);
                btnPrecioCompetencia.addActionListener(op_precioCompetencia);
                contf.add(panelf);
    		}

    		ActionListener op_solicitar_revision_de_margenes = new ActionListener() {
    			@Override
    			public void actionPerformed(ActionEvent e) {
    				
	    			 for(int i=0;i<tablafp2.getRowCount();i++) {
	    				 if(Float.valueOf(tablafp2.getValueAt(i,7).toString().trim())>Float.valueOf(tablafp2.getValueAt(i,6).toString().trim()) && !tablafp2.getValueAt(i,14).toString().trim().equals("Benchmarking")  ) {
	       	    	  	  JOptionPane.showMessageDialog(null, "Debe De Seleccionar La Razón De Benchmarking En Todos Los Productos Con Incidencia en el Margen \nPara Poder Utilizar Esta Opción","Aviso", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));  
	       	    	  	  return;
	                       }
	    				 
	                    if(Float.valueOf(tablafp2.getValueAt(i,7).toString().trim())>Float.valueOf(tablafp2.getValueAt(i,6).toString().trim()) && tablafp2.getValueAt(i,15).toString().trim().equals("")  ) {
	    	    	  	  JOptionPane.showMessageDialog(null, "Debe De Capturar Una Observación Para El Benchmarking","Aviso", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));  
	    	    	  	  return;
	                    }
	               
	  	                if(Float.valueOf(tablafp2.getValueAt(i,7).toString().trim())>Float.valueOf(tablafp2.getValueAt(i,6).toString().trim()) ) {
	      				  productos=productos+tablafp2.getValueAt(i, 0)+" / "+tablafp2.getValueAt(i, 1)+" /CANT."+tablafp2.getValueAt(i, 2)+"  /C.O.$"+tablafp2.getValueAt(i, 3)+"  /C.P.$"+tablafp2.getValueAt(i, 4)+"  /P.V.$"+tablafp2.getValueAt(i, 5)+"  /%"+tablafp2.getValueAt(i, 6)+"  /%"+tablafp2.getValueAt(i, 7)+" \n"+tablafp2.getValueAt(i, 14)+" "+tablafp2.getValueAt(i, 15)+" \n";
	  	                }
	                 }
    			 
		                 Obj_Correos correos = new Obj_Correos().buscar_correos(63, "");
						 String Mensaje= "El usuario:"+usuario.getNombre_completo().toString()+"\nsolicita la revision de precios de los siguientes productos\n"+productos;
		  				 new EmailSenderService().enviarcorreo(correos.getCorreos(),correos.getCantidad_de_correos(),Mensaje,"Solicitud Revision Precios Orden De Compra F."+txtFolio.getText().toString(),"benchmarking");
		  				  
		  			     orden_de_compra.setCod_prv(txtCod_Prov.getText().toString());
		 	    	     orden_de_compra.setFolio_orden_de_compra(txtFolio.getText().toString().trim());
		 	    	     orden_de_compra.setFolio_usuario_bms(txtFoliousuariobms.getText().toString().trim());
		 	    	     orden_de_compra.setTabla_productos(ObjTab.tabla_guardar(tablafp2) );
			    	   if(orden_de_compra.Guardar_Orden_solicitud_benchmaking()){
			  				 JOptionPane.showMessageDialog(null,"Se Envio Correctamente La Solicitud De Benchmarking!","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/aplicara-el-dialogo-icono-6256-32.png"));    	   
							init_tablaordenes();
							dispose();
					    	return;
					   }else{
							JOptionPane.showMessageDialog(null,"Error Al Guardar Avise al Administrador del Sistema","Aviso",JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-icono-eliminar5252-64.png"));
							return;
					   }
   
             	}
    		};
       
    		ActionListener op_Solicitar_Autorizacion_Por_Condiciones_De_Precio = new ActionListener() {
    			@Override
    			public void actionPerformed(ActionEvent e) {
    			 for(int i=0;i<tablafp2.getRowCount();i++) {
                    if(Float.valueOf(tablafp2.getValueAt(i,7).toString().trim())>Float.valueOf(tablafp2.getValueAt(i,6).toString().trim()) && tablafp2.getValueAt(i,15).toString().trim().equals("")  ) {
    	    	  	  JOptionPane.showMessageDialog(null, "Debe De Capturar Una Observación Para la condicion Del Precio Pactado","Aviso", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));  
    	    	  	  return;
                    }
                 }        	
		  			     orden_de_compra.setCod_prv(txtCod_Prov.getText().toString());
		 	    	     orden_de_compra.setFolio_orden_de_compra(txtFolio.getText().toString().trim());
		 	    	     orden_de_compra.setFolio_usuario_bms(txtFoliousuariobms.getText().toString().trim());
		 	    	     orden_de_compra.setTabla_productos(ObjTab.tabla_guardar(tablafp2) );
			    	   if(orden_de_compra.Guardar_Orden_solicitud_benchmaking()){
			  				 JOptionPane.showMessageDialog(null,"Se Envio Correctamente La Solicitud De La Revisión Del Precio!","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/aplicara-el-dialogo-icono-6256-32.png"));    	   
							init_tablaordenes();
							dispose();
					    	return;
					    }else{
							JOptionPane.showMessageDialog(null,"Error Al Guardar Avise al Administrador del Sistema","Aviso",JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-icono-eliminar5252-64.png"));
							return;
						}
             	}
    		};
    		
    		
      		ActionListener op_autorizar = new ActionListener() {
    			@Override
    			public void actionPerformed(ActionEvent e) {
    	    	   orden_de_compra.setCod_prv(txtCod_Prov.getText().toString());
    	    	   orden_de_compra.setFolio_orden_de_compra(txtFolio.getText().toString().trim());
    	    	   orden_de_compra.setFolio_usuario_bms(txtFoliousuariobms.getText().toString().trim());
    	    	   orden_de_compra.setTabla_productos(ObjTab.tabla_guardar(tablafp2) );
    	    	   
	    	   if(orden_de_compra.Guardar_Orden()){
					JOptionPane.showMessageDialog(null,"Se Autorizo La Orden De Compra Correctamente!","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/aplicara-el-dialogo-icono-6256-32.png"));
					dispose();
					init_tablaordenes();
			    	return;
				   }else{
					JOptionPane.showMessageDialog(null,"Error Al Guardar Avise al Administrador del Sistema","Aviso",JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-icono-eliminar5252-64.png"));
					return;
				   }		
    			}
    		};
    		
    		ActionListener op_precioCompetencia = new ActionListener() {
    			@Override
    			public void actionPerformed(ActionEvent e) {
    				
    				int filaSeleccionada = tablafp2.getSelectedRow();
    				
    				float costoProm 		= Float.valueOf(tablafp2.getValueAt(filaSeleccionada, 4).toString().equals("")?"0":tablafp2.getValueAt(filaSeleccionada, 4).toString());
    				float PrecioVenta 		= Float.valueOf(tablafp2.getValueAt(filaSeleccionada, 5).toString().equals("")?"0":tablafp2.getValueAt(filaSeleccionada, 5).toString());
    				float Margen 			= Float.valueOf(tablafp2.getValueAt(filaSeleccionada, 6).toString().equals("")?"0":tablafp2.getValueAt(filaSeleccionada, 6).toString());
    				float MFamilia 			= Float.valueOf(tablafp2.getValueAt(filaSeleccionada, 7).toString().equals("")?"0":tablafp2.getValueAt(filaSeleccionada, 7).toString());
    				float PrecioPorVolumen 	= Float.valueOf(tablafp2.getValueAt(filaSeleccionada, 8).toString().equals("")?"0":tablafp2.getValueAt(filaSeleccionada, 8).toString());
    				
    				if(filaSeleccionada>=0){
    					String codProd = tablafp2.getValueAt(filaSeleccionada, 0).toString();
    					new Cat_Consulta_De_Cotizacion(codProd, costoProm, PrecioVenta, Margen, MFamilia, PrecioPorVolumen).setVisible(true);
    				}else{
    					JOptionPane.showMessageDialog(null,"Se Requiere Seleccionar Un Producto De La Tabla","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/aplicara-el-dialogo-icono-6256-32.png"));
    					return;
    				}
    				
    			}
    		};
    		
    		class opGenerar implements ActionListener{   
    			String parametro;
    		    public opGenerar(final String parametrop){
    		    	parametro = parametrop;
    		    }
    		    public void actionPerformed(ActionEvent evt){
    		    	String basedatos="2.200";
    				String vista_previa_reporte="no";
    				int vista_previa_de_ventana=0;
    				String reporte ="";
    				String comando = "";
    				 
    				if(!txtFolio.getText().equals("")){
    					if(parametro.equals("POC")) {
    						 reporte = "Obj_Reporte_De_Orden_De_Compra_Sin_Autorizar.jrxml";
    						 comando = "exec sp_consulta_orden_de_compra_autorizada '"+txtFolio.getText().toUpperCase()+"','"+nombre_usuario+"'";
    						   new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
    						   return;
    						}
    					}else{
	    			  	  JOptionPane.showMessageDialog(null, "Debe De Capturar Un FolioDe Orden De Compra","Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
	    			  	  txtFolio.requestFocus();
	    		          return;
    				}
    		    }
    		};
    	
    		private void Seleccionar_Respuesta(final JTable tbl) {
			    tbl.addMouseListener(new java.awt.event.MouseAdapter() {
					@SuppressWarnings("deprecation")
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub
						if(e.getClickCount()!=0){
//	                         if(tbl.getSelectedColumn()==14){
	                    	        tbl.getColumnModel().getColumn(14).setCellEditor(new javax.swing.DefaultCellEditor(cmbRazon));
	                    		    tbl.getColumn("Razon").setCellEditor(new CargaDatosDelCombo());
							       return;
//	                         } else{
//							        tbl.lostFocus(null, null);
//							        tbl.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
//							        tbl.getSelectionModel().clearSelection();
//	                        	 return;
//	                         }	
			        	}else{
					        tbl.lostFocus(null, null);
					        tbl.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
					        tbl.getSelectionModel().clearSelection();
			        		return;
			        	}
					}
//					public void mousePressed(MouseEvent e) {
//			        	if(e.getClickCount()!=0){
//	                         if(tbl.getSelectedColumn()==14){
//	                    	        tbl.getColumnModel().getColumn(14).setCellEditor(new javax.swing.DefaultCellEditor(cmbRazon));
//	                    		    tbl.getColumn("Razon").setCellEditor(new CargaDatosDelCombo());
//							       return;
//	                         } else{
//							        tbl.lostFocus(null, null);
//							        tbl.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
//							        tbl.getSelectionModel().clearSelection();
//	                        	 return;
//	                         }	
//			        	}else{
//					        tbl.lostFocus(null, null);
//					        tbl.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
//					        tbl.getSelectionModel().clearSelection();
//			        		return;
//			        	}
//			        }
			    });
			}
    		
//    		MauseListener opClick = new MouseListener() {
//				public void mouseReleased(MouseEvent arg0) {
//					// TODO Auto-generated method stub
//					
//				}
//				public void mousePressed(MouseEvent arg0) {
//					// TODO Auto-generated method stub
//					
//				}
//				public void mouseExited(MouseEvent arg0) {
//					// TODO Auto-generated method stub
//					
//				}
//				public void mouseEntered(MouseEvent arg0) {
//					// TODO Auto-generated method stub
//					
//				}
//				public void mouseClicked(MouseEvent arg0) {
//					// TODO Auto-generated method stub
//					
//				}
//			};
    		
    	    private class CargaDatosDelCombo extends DefaultCellEditor{
    	        @SuppressWarnings("rawtypes")
    			public CargaDatosDelCombo(){
    	        	super(new JComboBox());
    	        }
    	        @SuppressWarnings({ "rawtypes", "unchecked" })
    			public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
    	        	JComboBox combo = (JComboBox)getComponent();
    	        	combo.removeAllItems();
    				try {
    					String respuestas= "Benchmarking/Promocion De Proveedor/Sin Existencia Con Proveedor Principal";
    					String tipo_de_respuestas[] = respuestas.split("/");
    					for(int i=0; i<tipo_de_respuestas.length; i++)  combo.addItem(tipo_de_respuestas[i]);
    				} catch (NumberFormatException e) {
    					e.printStackTrace();
    				}
    	            return combo;          
    	        }
    	    }
    	}
	
	public static void main(String[] args) {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Autorizacion_De_Ordenes_De_Compra().setVisible(true);
		}catch(Exception e){	}
	}
}
