package Cat_Punto_De_Venta;

import java.awt.Color;
import java.awt.Container;
import java.awt.Event;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.sql.SQLException;

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
import javax.swing.JPasswordField;
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

import Cat_Principal.EmailSenderService;
import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Connexion;
import Obj_Administracion_del_Sistema.Obj_MD5;
import Obj_Compras.Obj_Ubicaciones_De_Productos;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_tabla;
import Obj_Punto_De_Venta.Obj_Ventas_Express;
import Obj_Servicios.Obj_Correos;

@SuppressWarnings("serial")
public class Cat_Ventas_Express extends JFrame{
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	String[][] tablavendedores = null;
	String[][] tablasupervisores = null;
	Obj_tabla ObjTab =new Obj_tabla();
	Obj_Ventas_Express Venta_Express = new Obj_Ventas_Express(); 
	
	int columnas = 6,checkbox=-1;
	
	@SuppressWarnings("rawtypes")
	public Class[] base(){
		Class[] types = new Class[columnas];
		for(int i = 0; i<columnas; i++){types[i]= java.lang.Object.class;}
		 return types;
	}
	public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"Codigo","Descripcion","Precio","Cantidad","Importe","Costo"}){
		 @SuppressWarnings("rawtypes")
			Class[] types = base();
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
			public boolean isCellEditable(int fila, int columna){ if(columna==3) return true; return false;}
	};
	JTable tabla = new JTable(modelo);
	public JScrollPane Scroll_Tabla = new JScrollPane(tabla);
     @SuppressWarnings({ "rawtypes", "unused" })
    private TableRowSorter trsfiltro;
     
     public void init_tabla_venta(){
	    	this.tabla.getColumnModel().getColumn(0).setMinWidth(90);	
	    	this.tabla.getColumnModel().getColumn(1).setMinWidth(410);
	    	this.tabla.getColumnModel().getColumn(2).setMinWidth(100);
	    	this.tabla.getColumnModel().getColumn(3).setMinWidth(93);
	    	this.tabla.getColumnModel().getColumn(4).setMinWidth(100);
	    	this.tabla.getColumnModel().getColumn(5).setMinWidth(200);
			String comando="select 0,' ',0 ,0, 0, 0" ;
			String basedatos="98",pintar="si";
			ObjTab.Obj_Refrescar(tabla, modelo, columnas, comando, basedatos,pintar,checkbox);
			modelo.setRowCount(0);
		}
	
 	String FActividadesCargado ="N";
	String[][] tablaprecargadaproductos;
     int columnas2 = 11;
		public void init_tabla_filtro_productos(){
	    	this.tabla2.getColumnModel().getColumn(0).setMinWidth(90);	
	    	this.tabla2.getColumnModel().getColumn(1).setMinWidth(410);
	    	this.tabla2.getColumnModel().getColumn(2).setMinWidth(150);
	    	this.tabla2.getColumnModel().getColumn(3).setMinWidth(190);
	    	this.tabla2.getColumnModel().getColumn(4).setMinWidth(190);
	    	this.tabla2.getColumnModel().getColumn(5).setMinWidth(190);
	    	
			String comando="exec ventas_express_catalogo_de_productos '"+cmbEstablecimiento.getSelectedItem().toString().trim()+"'" ;
			String basedatos="98",pintar="si";
		
			ObjTab.Obj_Refrescar(tabla2, modelo2, columnas2, comando, basedatos,pintar,checkbox);
		}
			
		 public DefaultTableModel modelo2 = new DefaultTableModel(null, new String[]{"Codigo Producto","Descripcion","Clase Producto","Categoria","Familia","Marca","Lozalizacion","Zona","Pasillo","Rack","Nivel"}){
				 @SuppressWarnings("rawtypes")
		 		Class[] types = new Class[]{
		 				java.lang.Object.class,
		 				java.lang.Object.class,
		 				java.lang.Object.class,
		 				java.lang.Object.class,
		 				java.lang.Object.class,
		 				java.lang.Object.class,
		 				java.lang.Object.class,
		 				java.lang.Object.class,
		 				java.lang.Object.class,
		 				java.lang.Object.class,
		 				java.lang.Object.class,
				 };
				@SuppressWarnings({ "unchecked", "rawtypes" })
				public Class getColumnClass(int columnIndex) {  return types[columnIndex];  }
				public boolean isCellEditable(int fila, int columna2){return false;}
			    };
				    
			   JTable tabla2 = new JTable(modelo2);
				public JScrollPane scroll_tabla_filtro = new JScrollPane(tabla2);
				
	String establecimiento[] = Venta_Express.Combo_Establecimientos();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbEstablecimiento  = new JComboBox(establecimiento);
	
	String status[] = {"Vigente","Cancelado","Surtido","Abono","Liquidado"};
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmb_status = new JComboBox(status);
	
    JTextArea txtNota       = new Componentes().textArea(new JTextArea(), "Descripcion del Producto", 500);
	JScrollPane Notas        = new JScrollPane(txtNota);

	JToolBar menu_toolbar   = new JToolBar();
	JCButton btnBuscar      = new JCButton("Buscar"    ,"Filter-List-icon16.png"              ,"Azul"); 
	JCButton btnNuevo       = new JCButton("Nuevo"     ,"Nuevo.png"                           ,"Azul");
	JCButton btnGuardar     = new JCButton("Guardar"   ,"Guardar.png"                         ,"Azul");
	JCButton btnDeshacer    = new JCButton("Deshacer"  ,"deshacer16.png"                      ,"Azul");
	JCButton btnProducto    = new JCButton("Productos" ,"Filter-List-icon16.png"              ,"Azul");
	JCButton btnBuscarCte   = new JCButton("Buscar Cliente" ,"Usuario.png"                    ,"Azul");	
	JCButton btnQuitarfila  = new JCButton("Eliminar"  ,"boton-rojo-menos-icono-5393-16.png"  ,"Azul");
	JCButton btnVendedor    = new JCButton("Vendedor"  ,"clave.png"                           ,"Azul");
	
	JTextField txtFolioVendedor  = new Componentes().text(new JCTextField() ,"Folio Vendedor"                ,16    ,"String" );
	JTextField txtVendedor       = new Componentes().text(new JCTextField() ,"Vendedor"                      ,16    ,"String" );	
	JTextField txtcodigo_prod    = new Componentes().text(new JCTextField() ,"Teclea El Codigo del Producto" ,16    ,"String" );
	JTextField txtFolio          = new Componentes().text(new JCTextField() ,"Folio"                         ,30    ,"Int"    );
	JTextField txtFolio_cliente  = new Componentes().text(new JCTextField() ,"Cliente"                       ,30    ,"Int"    );
	JTextField txtNombre_cliente = new Componentes().text(new JCTextField() ,"Nombre Del Cliente"            ,250   ,"String" );
	JTextField txtTotalImporte   = new Componentes().text(new JCTextField() , "Total"                        ,16    ,"String" );
	
	JRadioButton rbCliente_SCOI  = new JRadioButton("Cliente SCOI");
	JRadioButton rbCliente       = new JRadioButton("Cliente");
	ButtonGroup  grupo           = new ButtonGroup();
    String guardar_actualizar="";
    
	public Cat_Ventas_Express(){
		setSize(820,385);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Ventas Express");
		setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/articulo-icono-9036-48.png"));
		panel.setBorder(BorderFactory.createTitledBorder("Seleccione Establecimiento,Seleccione el Cliente y Capture Los Productos"));
		
		this.menu_toolbar.add(btnNuevo    );
	    this.menu_toolbar.addSeparator(   );
	    this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.add(btnBuscar   );
	    this.menu_toolbar.addSeparator(   );
	    this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.add(btnDeshacer );
		this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.add(btnGuardar  );
		
		this.menu_toolbar.setFloatable(false);
		
		this.grupo.add(rbCliente_SCOI);
   		this.grupo.add(rbCliente);
   		this.rbCliente.setSelected(true);
   		
		int x=10, y=25, width=115,height=20,sep=120;
		panel.add(menu_toolbar).setBounds                                     (x      ,y        ,500     ,height  );
		panel.add(cmb_status).setBounds                                       (x+540  ,y        ,70      ,height  );
		panel.add(rbCliente).setBounds                                        (x+620  ,y        ,90      ,height  );
		panel.add(rbCliente_SCOI).setBounds                                   (x+710  ,y        ,90      ,height  );	
		panel.add(txtFolio).setBounds                                         (x=10   ,y+=25    ,width   ,height  );
		panel.add(cmbEstablecimiento).setBounds                               (x+=sep ,y        ,width   ,height  );
		panel.add(txtFolio_cliente).setBounds                                 (x+=sep ,y        ,80      ,height  );
		panel.add(txtNombre_cliente).setBounds                                (x+=80  ,y        ,335     ,height  );
		panel.add(btnBuscarCte).setBounds                                     (x+=335 ,y        ,140     ,height  );
		panel.add(new JLabel("Notas:")).setBounds                         	  (x=10   ,y+=20    ,width   ,height  );
		panel.add(Notas).setBounds                                   	      (x      ,y+=15    ,795     ,60      );
	    panel.add(txtcodigo_prod).setBounds                                   (x=10   ,y+=65    ,180     ,height  );
		panel.add(btnProducto).setBounds                                      (x+=180 ,y        ,width   ,height  );
		panel.add(btnQuitarfila).setBounds                                    (x+=515 ,y        ,100     ,height  );
		
		panel.add(Scroll_Tabla).setBounds                                     (x=10   ,y+=20    ,795     ,150     );
		
		panel.add(btnVendedor).setBounds                                      (x      ,y+=150   ,width   ,height  );
		panel.add(txtFolioVendedor).setBounds                                 (x+=115 ,y        ,60      ,height  );
		panel.add(txtVendedor).setBounds                                      (x+=60  ,y        ,370     ,height  );
		
		panel.add(new JLabel("Total De La Venta:")).setBounds              	  (x=600  ,y        ,width   ,height  );
		panel.add(txtTotalImporte).setBounds                          	      (x+=90  ,y        ,width   ,20      );
		
		init_tabla_venta();
		panel(false);
		
		Scroll_Tabla.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		txtFolioVendedor.setEditable(false);
		txtVendedor.setEditable(false);
		txtFolio_cliente.setEditable(false);
		txtNombre_cliente.setEditable(false);
		txtTotalImporte.setEditable(false);
		cmb_status.setEnabled(false);
		cont.add(panel);
		
		btnBuscar.addActionListener     (opBuscarVenta_Express    );
		btnDeshacer.addActionListener   (opdeshacer               );
		btnGuardar.addActionListener    (opguardar     			  );
		btnNuevo.addActionListener      (opnuevo                  );
		btnQuitarfila.addActionListener (opQuitarfila             );
		tabla.addKeyListener            (op_validanumero_en_celda );
		btnProducto.addActionListener   (filtro_producto          );
		btnBuscarCte.addActionListener  (filtro_buscar_cliente    );
		btnVendedor.addActionListener   (Clave_vendedor           );
		txtcodigo_prod.addKeyListener   (Buscar_Datos_Producto    );
		rbCliente.addActionListener     (opCambio_de_Cliente      );
		rbCliente_SCOI.addActionListener(opCambio_de_Cliente      );
		
		try {
			tablavendedores = Venta_Express.vededores_express(cmbEstablecimiento.getSelectedItem().toString().trim());
			tablasupervisores = Venta_Express.supervisores_express(cmbEstablecimiento.getSelectedItem().toString().trim());
		} catch (IOException e1) {
			e1.printStackTrace();
		};

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
        getRootPane().getActionMap().put("escape", new AbstractAction(){ public void actionPerformed(ActionEvent e){ btnDeshacer.doClick();} });

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_N,Event.CTRL_MASK),"nuevo");
        getRootPane().getActionMap().put("nuevo", new AbstractAction(){ public void actionPerformed(ActionEvent e){btnNuevo.doClick();}});
        
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_G,Event.CTRL_MASK),"guardar");
        getRootPane().getActionMap().put("guardar", new AbstractAction(){ public void actionPerformed(ActionEvent e){btnGuardar.doClick();}});

	    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put( KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0), "cliente");	    
	    getRootPane().getActionMap().put("cliente", new AbstractAction(){ @Override public void actionPerformed(ActionEvent e) {btnBuscarCte.doClick(); }});
	    
	    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put( KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), "foco");	    
	    getRootPane().getActionMap().put("foco", new AbstractAction(){ @Override public void actionPerformed(ActionEvent e) {btnProducto.doClick(); }});
                 
	    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put( KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0), "vendedor");	    
	    getRootPane().getActionMap().put("vendedor", new AbstractAction(){ @Override public void actionPerformed(ActionEvent e) {btnVendedor.doClick(); }});
	    
	}
	
	public void panel(boolean boleano) {
		cmbEstablecimiento.setEnabled(boleano);
		rbCliente.setEnabled(boleano);
		rbCliente_SCOI.setEnabled(boleano);
		rbCliente.setSelected(true);
		btnProducto.setEnabled(boleano);
		btnBuscarCte.setEnabled(boleano);
		btnQuitarfila.setEnabled(boleano);
		txtNota.setEditable(boleano);
		txtFolio.setEditable(boleano);
		txtcodigo_prod.setEditable(boleano);
		btnGuardar.setEnabled(boleano);
		btnVendedor.setEnabled(boleano);
	};
  	
  	ActionListener opCambio_de_Cliente = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		  txtFolio_cliente.setText("");
		  txtNombre_cliente.setText("");
		}
	};
	
	ActionListener opBuscarVenta_Express = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		  new Cat_Filtro_Buscar_Venta_Express().setVisible(true);
		}
	};
  	
	ActionListener Clave_vendedor = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		  new Cat_Vendedor().setVisible(true);
		}
	};
	
	ActionListener filtro_producto = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		  new Cat_Filtro_De_Productos().setVisible(true);
		}
	};
	
	ActionListener opguardar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			calculo();
		 if(txtTotalImporte.getText().equals("")||Float.valueOf(txtTotalImporte.getText())==0){	
			JOptionPane.showMessageDialog(null, "Es Requerido Alimente Productos Con Cantidad y Precio","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
			txtcodigo_prod.requestFocus();
		   return;
		 }else{	 
			if(txtFolioVendedor.getText().toString().equals("")) {
				JOptionPane.showMessageDialog(null, "Es Requerido Alimente El Vendedor","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
				btnVendedor.doClick();
			  return;
			} else {			 
		      new Cat_Valida_Supervisor().setVisible(true);
			}
		 }
		}
	};
	
	ActionListener filtro_buscar_cliente = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		  new Cat_Filtro_Buscar_Cliente().setVisible(true);
		}
	};
	
	ActionListener opnuevo = new ActionListener() {
		@SuppressWarnings("deprecation")
		public void actionPerformed(ActionEvent e) {
			btnDeshacer.doClick();
			String folio = "";
			guardar_actualizar="N";
			try {folio= new BuscarSQL().folio_siguiente(67+"");} catch (SQLException e1) {	e1.printStackTrace();}
			txtFolio.setText(folio);
			panel(true);
			txtFolio.setEditable(false);
			txtNota.setEditable(false);
			txtFolio.setEditable(false);
			btnProducto.setEnabled(false);
			btnQuitarfila.setEnabled(false);
			txtcodigo_prod.setEditable(false);
			txtcodigo_prod.requestFocus();
     		tabla.enable(true);
		}
	};
	
	ActionListener opdeshacer = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			panel(false);
			txtNota.setText           ("");
			txtFolio.setText          ("");
			txtFolio_cliente.setText  ("");
			txtNombre_cliente.setText ("");
			txtcodigo_prod.setText    ("");
			txtTotalImporte.setText   ("");
			txtFolioVendedor.setText  ("");
			txtVendedor.setText       ("");
			guardar_actualizar="";
			txtNota.setBackground(new Color(Integer.parseInt("EBEBEB",16)));
			modelo.setRowCount(0);
		   return;	
		}
	};
	
	ActionListener opQuitarfila = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			int seleccion = tabla.getSelectedRow();
			if(seleccion<0){
				JOptionPane.showMessageDialog(null, "Debe seleccionar la fila que desea quitar","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
				return;
			}else{
				if(seleccion < tabla.getRowCount()){
					modelo.removeRow(seleccion);
					tabla.getSelectionModel().setSelectionInterval(seleccion, seleccion);
					if(tabla.getRowCount()>0) {
						calculo();
					}else {
						txtTotalImporte.setText("");
					}
				}
			}
		}
	};
	
	KeyListener Buscar_Datos_Producto = new KeyListener() {
		public void keyTyped(KeyEvent e){}
		public void keyReleased(KeyEvent e) {}
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				llenar_datos_producto();
			}
		}
	};
	
	KeyListener op_validanumero_en_celda = new KeyListener() {
		public void keyTyped(KeyEvent e) {}
		public void keyReleased(KeyEvent e) {
			int fila=tabla.getSelectedRow();
			int columna=3; //tabla.getSelectedColumn();
			if(fila==-1)fila=fila+1;
			
			if(ObjTab.validacelda(tabla,"decimal", fila,columna)){
				calculo();
						  if(ObjTab.RecorridoFocotabla_con_evento(tabla, fila,columna, "x",e).equals("si")){
								txtcodigo_prod.requestFocus();
						  };
			}	
		}
		public void keyPressed(KeyEvent e) {}
	};
	
	public void llenar_datos_producto() {
		String codigo=txtcodigo_prod .getText().toUpperCase().trim();
		String cod_prod=new BuscarSQL().cod_prod_principal_bms_por_establecimiento(codigo,cmbEstablecimiento.getSelectedItem().toString().trim());
				if(!cod_prod.equals("false no existe") ){
				  Obj_Ubicaciones_De_Productos  Datos_Producto= new Obj_Ubicaciones_De_Productos().buscardatos_producto(cod_prod,cmbEstablecimiento.getSelectedItem().toString().trim().toUpperCase()+"");
				  Object[] Vector_Producto = new Object[6];
					Vector_Producto[0]=Datos_Producto.getCod_Prod().trim();		
					Vector_Producto[1]=Datos_Producto.getDescripcion_Prod().trim();		
					Vector_Producto[2]=Datos_Producto.getPrecio_de_venta()+"";		
					Vector_Producto[3]="1";
					Vector_Producto[4]="0";
					Vector_Producto[5]=Datos_Producto.getUltimo_Costo()+"";
					
					modelo.addRow(Vector_Producto);
					txtcodigo_prod.setText("");
					calculo();
					ObjTab.RecorridoFocotabla(tabla, modelo.getRowCount()-1, 3, "x");
					tabla.setRowSelectionInterval(modelo.getRowCount()-1, modelo.getRowCount()-1);
				   return;	
				 }else{
						JOptionPane.showMessageDialog(null, "El Codigo "+codigo+" Presenta uno de los Siguientes Problemas: \n-Esta Mal Escrito \n-El Producto No Existe \n-No Tiene Costo En el Establecimiento" , "Aviso", JOptionPane.CANCEL_OPTION,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
						txtcodigo_prod.requestFocus();
						return;
		         }	 
	};
	
	public void calculo() {
		double Importe_Total=0;
		for(int i=0;i<tabla.getRowCount();i++) {
			 double Importe=0;
			
			if(ObjTab.validacelda(tabla,"decimal", i,3)){
			  Importe=( Double.valueOf(tabla.getValueAt(i, 2).toString())* Double.valueOf(tabla.getValueAt(i, 3).toString()));
			  Importe_Total=Importe_Total+Importe;
			  tabla.setValueAt(Importe+"", i, 4);
			}
		}
		txtTotalImporte.setText(Importe_Total+"");
	}
	
////////////////////////////////////////////////////////////TODO FILTRO DE PRODUCTOS	
		public class Cat_Filtro_De_Productos extends JDialog{
			  Container cont = getContentPane();
			  JLayeredPane panel = new JLayeredPane();
			  Connexion con = new Connexion();
			  Runtime R = Runtime.getRuntime();
			@SuppressWarnings("rawtypes")
			private TableRowSorter trsfiltro;
			JTextField txtFiltrop = new Componentes().textfiltro(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<", 500, "String",tabla2,columnas2 );
			
			Border blackline, etched, raisedbevel, loweredbevel, empty;
			JCButton btnActualizar  = new JCButton("Actualizar"  ,"refrescar-volver-a-cargar-las-flechas-icono-4094-16.png"  , "Azul");

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

				int y = 20;
				panel.add(txtFiltrop).setBounds(15,y,500,20);
				panel.add(btnActualizar).setBounds(600, y, 150, 20);;
				panel.add(scroll_tabla_filtro).setBounds(15,y+=20,ancho-30,alto-70);
		        
				
				if(FActividadesCargado.equals("S")){
				     ObjTab.llenado_de_modelo_desde_datos_tabla_precargados(tablaprecargadaproductos, tabla2);
				}else{
					init_tabla_filtro_productos();
					tablaprecargadaproductos= ObjTab.tabla_guardar(tabla2);
				  FActividadesCargado="S";
				}
				agregar(tabla2);
				btnActualizar.addActionListener(Op_Actualizar);
			}
			
			ActionListener Op_Actualizar = new ActionListener() {
				public void actionPerformed(ActionEvent e){
					init_tabla_filtro_productos();
					tablaprecargadaproductos= ObjTab.tabla_guardar(tabla2);
				  FActividadesCargado="S";
				}
			};
			
			private void agregar(final JTable tbl) {
				tbl.addMouseListener(new MouseListener() {
					public void mouseReleased(MouseEvent e) {
							if(e.getClickCount() == 2){
								agregar();
							}
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

		  public void	agregar() {
		    int fila_Select = tabla2.getSelectedRow();
  			 String folio =  tabla2.getValueAt(fila_Select, 0).toString().trim();
  			 txtcodigo_prod.setText(folio);
  			 txtcodigo_prod.requestFocus();
  			 dispose();
		  };
		};	
		
/////////TODO inicia filtro_Buscar CLIENTE///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		public class Cat_Filtro_Buscar_Cliente extends JDialog{
			Container contfb = getContentPane();
			JLayeredPane panelfb = new JLayeredPane();
			Connexion con = new Connexion();
			Obj_tabla ObjTab =new Obj_tabla();
			int columnasb = 2,checkbox=-1;
			public void init_tablafp(){
		    	this.tablab.getColumnModel().getColumn( 0).setMinWidth(55);
		    	this.tablab.getColumnModel().getColumn( 1).setMinWidth(430);
				 String ref =rbCliente.isSelected()?"Cliente":"Cliente_SCOI";
				 String comandob=" " ;
				switch(ref){
				    case "Cliente"     :comandob = "select cod_cte,razon_social from bmsizagar.dbo.clientes order by razon_social"; break;
			    	case "Cliente_SCOI":comandob = "exec ventas_express_clientes_filtro "; break;
			        }
				String basedatos="98",pintar="si";
				ObjTab.Obj_Refrescar(tablab,modelob,columnasb, comandob, basedatos,pintar,checkbox);
		    }
			
			@SuppressWarnings("rawtypes")
			public Class[] base (){
				Class[] types = new Class[columnasb];
				for(int i = 0; i<columnasb; i++){types[i]= java.lang.Object.class;}
				 return types;
			}
			
			public DefaultTableModel modelob = new DefaultTableModel(null, new String[]{"Folio","Cliente"}){
				 @SuppressWarnings("rawtypes")
					Class[] types = base();
					@SuppressWarnings({ "rawtypes", "unchecked" })
					public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
					public boolean isCellEditable(int fila, int columna){return false;}
			};
			
			JTable tablab = new JTable(modelob);
			public JScrollPane scroll_tablab = new JScrollPane(tablab);

			JTextField txtBuscarb  = new Componentes().textfiltro(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<", 500, "String",tablab,columnasb );
			
			public Cat_Filtro_Buscar_Cliente(){
				this.setSize(550,450);
				this.setResizable(false);
				this.setLocationRelativeTo(null);
				this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				this.setModal(true);
				this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Filter-List-icon32.png"));
				this.panelfb.setBorder(BorderFactory.createTitledBorder("Selecione Un Registro Con Doble Click"));
				this.setTitle("Filtro De Cliente");
				this.panelfb.add(txtBuscarb).setBounds      (10 ,20 ,525 ,20 );
				this.panelfb.add(scroll_tablab).setBounds   (10 ,40 ,525 ,370);
				this.init_tablafp();
				this.agregar(tablab);
				contfb.add(panelfb);
			}
			
			private void agregar(final JTable tbl) {
				tbl.addMouseListener(new MouseListener() {
					public void mouseReleased(MouseEvent e) {
				 	 if(e.getClickCount() == 2){funcion_agregar();}
					}
					public void mousePressed(MouseEvent e) {}
					public void mouseExited(MouseEvent e)  {}
					public void mouseEntered(MouseEvent e) {}
					public void mouseClicked(MouseEvent e) {}
				});
				
				tbl.addKeyListener(new KeyListener() {
					public void keyPressed(KeyEvent e)  {
						if(e.getKeyCode()==KeyEvent.VK_ENTER){
							funcion_agregar();	
						}
					}
					public void keyReleased(KeyEvent e)   {}
					public void keyTyped   (KeyEvent e)   {}
				});
		    }

		    public void funcion_agregar() {
        		int fila = tablab.getSelectedRow();
        		txtFolio_cliente.setText (tablab.getValueAt(fila,0)+"");
        		txtNombre_cliente.setText (tablab.getValueAt(fila,1)+"");
				txtNota.setEditable(true);
				txtcodigo_prod.setEditable(true);
				btnProducto.setEnabled(true);
				btnQuitarfila.setEnabled(true);
				txtNota.requestFocus();
				txtNota.setBackground(new Color(254,254,254));
				dispose();
		    };
		}
		
		

/////////TODO inicia Validacion Vendedor///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		public class Cat_Vendedor extends JDialog{
			Container contfb = getContentPane();
			JLayeredPane panelfb = new JLayeredPane();
			Connexion con = new Connexion();
			JPasswordField txtClaveVendedor  = new JPasswordField(15);
			public Cat_Vendedor(){
				this.setSize(200,100);
				this.setResizable(false);
				this.setLocationRelativeTo(null);
				this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				this.setModal(true);
				this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Filter-List-icon32.png"));
				this.panelfb.setBorder(BorderFactory.createTitledBorder("Teclee La Clave Del Vendedor"));
				this.setTitle("Vendedor Servicio Express");
				this.panelfb.add(txtClaveVendedor).setBounds      (10 ,20 ,180 ,20 );
				this.txtClaveVendedor.addKeyListener  (opvalida_clave );
				contfb.add(panelfb);
				
			}
			
	        private KeyListener opvalida_clave = new KeyListener(){
				public void keyReleased(KeyEvent e) {
					if(e.getKeyCode()==KeyEvent.VK_ENTER){
						boolean existe=true;
						@SuppressWarnings("deprecation")
						String clavetecleada_vendedor=txtClaveVendedor.getText().toString();
						for(int i=0;i<tablavendedores.length;i++) {
							if(clavetecleada_vendedor.equals(tablavendedores[i][2].toString().trim())) {
								existe=false;
								txtFolioVendedor.setText(tablavendedores[i][0].toString().trim());
								txtVendedor.setText(tablavendedores[i][1].toString().trim());
								dispose();
								return;
							}
						}
						if(existe){
							JOptionPane.showMessageDialog(null, "La Clave Tecleada Es Inválida En El Establecimiento\n"+cmbEstablecimiento.getSelectedItem().toString().trim() , "Aviso", JOptionPane.CANCEL_OPTION,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
							txtClaveVendedor.setText("");
							txtClaveVendedor.requestFocus();
							return;
						}
					}
				}
				public void keyTyped(KeyEvent e) {}
				public void keyPressed(KeyEvent e) {}		
			};
		}
/////////fin Validacion Vendedor///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////TODO inicia_validacion_guardado_supervisor///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		public class Cat_Valida_Supervisor extends JDialog{
			Container contfb = getContentPane();
			JLayeredPane panelfb = new JLayeredPane();
			Connexion con = new Connexion();
			Obj_tabla ObjTab =new Obj_tabla();
			int columnasb = 2,checkbox=-1;
			public void init_tablafp(){
		    	this.tablag.getColumnModel().getColumn( 0).setMinWidth(55);
		    	this.tablag.getColumnModel().getColumn( 1).setMinWidth(430);
				 String comandob=" SELECT cod_prv,rtrim(ltrim(upper(razon_social))) from bmsizagar.dbo.proveedores order by razon_social  " ;
				String basedatos="98",pintar="si";
				ObjTab.Obj_Refrescar(tablag,modelog, columnasb, comandob, basedatos,pintar,checkbox);
		    }
			
			@SuppressWarnings("rawtypes")
			public Class[] base (){
				Class[] types = new Class[columnasb];
				for(int i = 0; i<columnasb; i++){types[i]= java.lang.Object.class;}
				 return types;
			}
			
			public DefaultTableModel modelog = new DefaultTableModel(null, new String[]{"Folio","Cliente"}){
				 @SuppressWarnings("rawtypes")
					Class[] types = base();
					@SuppressWarnings({ "rawtypes", "unchecked" })
					public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
					public boolean isCellEditable(int fila, int columna){return false;}
			};
			
			JTable tablag = new JTable(modelog);
			public JScrollPane scroll_tablab = new JScrollPane(tablag);
			JPasswordField   txtpaswordsupervisor = new JPasswordField();
			JTextField       txtcod_prv           = new Componentes().text(new JCTextField(), "Codigo"                                                       ,100 , "String"                  ); 			     
			JTextField       txtNombre_Prv        = new Componentes().text(new JCTextField(), "Nombre Proveedor"                                             ,200 , "String"                  ); 
			JTextField       txtBuscarb           = new Componentes().textfiltro(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<",500 , "String",tablag,columnasb );

			public Cat_Valida_Supervisor(){
				this.setSize(550,350);
				this.setResizable(false);
				this.setLocationRelativeTo(null);
				this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				this.setModal(true);
				this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Filter-List-icon32.png"));
				this.panelfb.setBorder(BorderFactory.createTitledBorder("Selecione Un Proveedor,Teecle La Contraseña y presione Enter Para Guardar"));
				this.setTitle("Autorización Servicio Express");
				int x=10,y=20,width=525,height=20;
				this.panelfb.add(txtBuscarb).setBounds                                                (x    ,y      ,width ,height  );
				this.panelfb.add(scroll_tablab).setBounds                                             (x    ,y+=20  ,width ,193     );
				this.panelfb.add(txtcod_prv).setBounds                                                (x    ,y+=210 ,60    ,height  );
				this.panelfb.add(txtNombre_Prv).setBounds                                             (x+60 ,y      ,465   ,height  );
				this.panelfb.add(new JLabel("Teclee La Clave del Supervisor Que Autoriza:")).setBounds(x    ,y+=20  ,width ,height  );
				this.panelfb.add(txtpaswordsupervisor).setBounds                                      (x    ,y+=25  ,width ,height  ); 
				
				this.txtcod_prv.setEditable(false);
				this.txtNombre_Prv.setEditable(false);
				this.txtpaswordsupervisor.setEditable(false);
				this.init_tablafp();
				this.agregar(tablag);
				contfb.add(panelfb);
				this.txtpaswordsupervisor.addKeyListener(opvalida_clave_supervisor);
			}
			
			  private KeyListener opvalida_clave_supervisor = new KeyListener(){
					@SuppressWarnings({ "static-access", "deprecation" })
					public void keyReleased(KeyEvent e) {
						if(e.getKeyCode()==KeyEvent.VK_ENTER){
							boolean existe=true;
							Obj_MD5 algoritmo = new Obj_MD5();
							for(int i=0;i<tablasupervisores.length;i++) {
								if(algoritmo.cryptMD5(txtpaswordsupervisor.getText().toString(), "izagar").trim().equals( tablasupervisores [i][2].toString().trim())){
									 String[][] tabla_guardado = ObjTab.tabla_guardar(tabla);
									
									  Venta_Express.setFolio(Integer.valueOf(txtFolio.getText().toString().trim()));	
									  Venta_Express.setEstablecimiento(cmbEstablecimiento.getSelectedItem().toString().trim());
									  Venta_Express.setTipo_de_cliente(rbCliente.isSelected()?"B":"S");
									  Venta_Express.setFolio_cliente(txtFolio_cliente.getText().toString().trim());
									  Venta_Express.setNotas(txtNota.getText().toString());		
									  Venta_Express.setFolio_vendedor(txtFolioVendedor.getText().toString().trim());
									  Venta_Express.setTotal_venta(Double.valueOf(txtTotalImporte.getText().toString().trim()));
									  Venta_Express.setFolio_proveedor(txtcod_prv.getText().toString().trim());
									  Venta_Express.setFolio_supervisor_autoriza(tablasupervisores [i][0].toString().trim());	
									  Venta_Express.setTabla_prodcutos(tabla_guardado ); 
									  Venta_Express.setGuardar_actualizar(guardar_actualizar);
									  Venta_Express.setEstatus(cmb_status.getSelectedItem().toString().trim());
									  
									  if(Venta_Express.GuardarActualizar().getFolio()>0){
										  String productos="\nDescripcion\n";
										   for(int i2=0;i2<tabla.getRowCount();i2++) {
											   productos=productos+tabla.getValueAt(i2, 1)+"  /Precio Venta:$"+tabla.getValueAt(i2, 2)+"  /Cnt.:"+tabla.getValueAt(i2, 3)+"  /Importe$"+tabla.getValueAt(i2, 4)+"/U.Costo:$"+tabla.getValueAt(i2, 5) +" \n";
										   }
										   
							                Obj_Correos correos = new Obj_Correos().buscar_correos(67, "");
											String Mensaje= "El Vendedor:"+txtVendedor.getText().toString()+"\n En El Establecimiento:"+cmbEstablecimiento.getSelectedItem().toString().trim()+
															"\n Realizo Una Venta Express con los siguientes productos\n"+productos+"\nCon un valor total de:$"+txtTotalImporte.getText().toString()+"\n "+
											                  "\n Para El Cliente:"+txtNombre_cliente.getText()+"\nEncargado Que Reviso y Autorizo: "+tablasupervisores [i][1].toString().trim()
											                  +"\nProveedor Que Surtirá: "+txtNombre_Prv.getText().toString().trim();
											new EmailSenderService().enviarcorreo(correos.getCorreos(),correos.getCantidad_de_correos(),Mensaje,"Venta Express Folio:"+Venta_Express.getFolio()+" cliente "+txtNombre_cliente.getText().toString(),"express");
											btnDeshacer.doClick();
											guardar_actualizar="";
											JOptionPane.showMessageDialog(null, "Se Guardo Correctamente", "Aviso", JOptionPane.OK_OPTION,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
											existe=false;
											dispose();
											return;
										}else{
											JOptionPane.showMessageDialog(null,"Error Al Guardar Avise al Administrador del Sistema","Aviso",JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-icono-eliminar5252-64.png"));
											return;
										}
								}
							}
							if(existe){
								JOptionPane.showMessageDialog(null, "La Clave Tecleada Es Inválida Para El Establecimiento\n"+cmbEstablecimiento.getSelectedItem().toString().trim() , "Aviso", JOptionPane.CANCEL_OPTION,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
								txtpaswordsupervisor.setText("");
								txtpaswordsupervisor.requestFocus();
								return;
							}
						}
					}
					public void keyTyped(KeyEvent e) {}
					public void keyPressed(KeyEvent e) {}		
				};
			
			private void agregar(final JTable tbl) {
				tbl.addMouseListener(new MouseListener() {
					public void mouseReleased(MouseEvent e) {
				 	 if(e.getClickCount() == 1){funcion_agregar();}
					}
					public void mousePressed(MouseEvent e) {}
					public void mouseExited(MouseEvent e)  {}
					public void mouseEntered(MouseEvent e) {}
					public void mouseClicked(MouseEvent e) {}
				});
				
				tbl.addKeyListener(new KeyListener() {
					public void keyPressed(KeyEvent e)  {
						if(e.getKeyCode()==KeyEvent.VK_ENTER){
							funcion_agregar();	
						}
					}
					public void keyReleased(KeyEvent e)   {}
					public void keyTyped   (KeyEvent e)   {}
				});
		    }

			 public void funcion_agregar() {
			   int fila = tablag.getSelectedRow();
			   	 txtcod_prv.setText (tablag.getValueAt(fila,0)+"");
			     txtNombre_Prv.setText (tablag.getValueAt(fila,1)+"");
			     txtpaswordsupervisor.setEditable(true);
			     txtpaswordsupervisor.requestFocus();
		    }
		}		
 
//////////////////////TODO inicia filtro_Buscar Venta Express
		public class Cat_Filtro_Buscar_Venta_Express extends JDialog{
			Container contfb = getContentPane();
			JLayeredPane panelfb = new JLayeredPane();
			Connexion con = new Connexion();
			Obj_tabla ObjTab =new Obj_tabla();
			int columnasb = 16,checkbox=-1;
			public void init_tablafp(){
		    	this.tablab.getColumnModel().getColumn( 0).setMinWidth(50);
		    	this.tablab.getColumnModel().getColumn( 0).setMaxWidth(50);
		    	this.tablab.getColumnModel().getColumn( 1).setMinWidth(50);
		    	this.tablab.getColumnModel().getColumn( 1).setMaxWidth(50);
		    	this.tablab.getColumnModel().getColumn( 2).setMinWidth(250);
		    	this.tablab.getColumnModel().getColumn( 3).setMinWidth(50);		    	
		    	this.tablab.getColumnModel().getColumn( 3).setMaxWidth(50);
		    	this.tablab.getColumnModel().getColumn( 4).setMinWidth(250);
		    	this.tablab.getColumnModel().getColumn( 5).setMinWidth(50);
		    	this.tablab.getColumnModel().getColumn( 5).setMaxWidth(50);
		    	this.tablab.getColumnModel().getColumn( 6).setMinWidth(250);
		    	this.tablab.getColumnModel().getColumn( 7).setMinWidth(50);
		    	this.tablab.getColumnModel().getColumn( 7).setMaxWidth(50);
		    	this.tablab.getColumnModel().getColumn( 8).setMinWidth(250);
		    	this.tablab.getColumnModel().getColumn( 9).setMinWidth(50);
		    	this.tablab.getColumnModel().getColumn( 9).setMaxWidth(50);
		    	this.tablab.getColumnModel().getColumn(10).setMinWidth(130);
		    	this.tablab.getColumnModel().getColumn(11).setMinWidth(200);
		    	this.tablab.getColumnModel().getColumn(12).setMinWidth(80);
		    	this.tablab.getColumnModel().getColumn(12).setMaxWidth(80);
		    	this.tablab.getColumnModel().getColumn(13).setMinWidth(130);
		    	this.tablab.getColumnModel().getColumn(14).setMinWidth(100);
		    	this.tablab.getColumnModel().getColumn(15).setMinWidth(100);
		    	
				String comandob = "ventas_express_filtro_select";
		    	String basedatos="98",pintar="si";
				ObjTab.Obj_Refrescar(tablab,modelob, columnasb, comandob, basedatos,pintar,checkbox);
		    }
			
			@SuppressWarnings("rawtypes")
			public Class[] base (){
				Class[] types = new Class[columnasb];
				for(int i = 0; i<columnasb; i++){types[i]= java.lang.Object.class;}
				 return types;
			}
			
			public DefaultTableModel modelob = new DefaultTableModel(null, new String[]{  "Folio","Folio C.", "Nombre Cliente", "Folio V.", "Vendedor", "Folio P.","Proveedor","Folio S.","Nombre Autorizó","Folio E.", "Establecimiento", "Notas", "Total Venta", "Fecha", "Tipo Cliente", "Estatus"}){
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
			     
			JTextField txtBuscarb  = new Componentes().textfiltro(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<", 500, "String",tablab,columnasb);
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Cat_Filtro_Buscar_Venta_Express(){
				this.setSize(825,400);
				this.setResizable(false);
				this.setLocationRelativeTo(null);
				this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				this.setModal(true);
				this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Filter-List-icon32.png"));
				this.panelfb.setBorder(BorderFactory.createTitledBorder("Selecione Un Registro Con Doble Click"));
				this.setTitle("Filtro De Ordenes De Pago");
				trsfiltro = new TableRowSorter(modelob); 
				tablab.setRowSorter(trsfiltro);
				this.panelfb.add(txtBuscarb).setBounds      (10 ,20 ,800 , 20 );
				this.panelfb.add(scroll_tablab).setBounds   (10 ,40 ,800 ,300 );
				this.init_tablafp();
				this.agregar(tablab);
				contfb.add(panelfb);
			}

			private void agregar(final JTable tbl) {
				tbl.addMouseListener(new MouseListener() {
					public void mouseReleased(MouseEvent e) {
				 	 if(e.getClickCount() == 1){funcion_agregar();}
					}
					public void mousePressed(MouseEvent e) {}
					public void mouseExited(MouseEvent e)  {}
					public void mouseEntered(MouseEvent e) {}
					public void mouseClicked(MouseEvent e) {}
				});
				
				tbl.addKeyListener(new KeyListener() {
					public void keyPressed(KeyEvent e)  {
						if(e.getKeyCode()==KeyEvent.VK_ENTER){
							funcion_agregar();	
						}
					}
					public void keyReleased(KeyEvent e)   {}
					public void keyTyped   (KeyEvent e)   {}
				});
		    }

			 @SuppressWarnings("deprecation")
			public void funcion_agregar() {
			   int fila = tablab.getSelectedRow();
			    modelo.setRowCount(0);

	       		String[][] tablacompleta=Venta_Express.consulta_venta_express(Integer.valueOf(tablab.getValueAt(fila,0)+""));
	       	    Object[]   vectortabla = new Object[4];
	       		for(int i=0;i<tablacompleta.length;i++){
	       				for(int j=0;j<4;j++){
	       				  vectortabla[j] = tablacompleta[i][j].toString();
	       				}
	       				modelo.addRow(vectortabla);
	       			}
       		
              txtFolio.setText(tablacompleta[0][4].toString());
              cmbEstablecimiento.setSelectedItem(tablacompleta[0][6].toString().trim());
              if(tablacompleta[0][7].toString().equals("BMS")) {rbCliente.setSelected(true);}else{rbCliente_SCOI.setSelected(true);};
              txtFolio_cliente.setText  (tablacompleta[0][8].toString().trim()        );
              txtNombre_cliente.setText (tablacompleta[0][9].toString().trim()        );
              txtNota.setText           (tablacompleta[0][10].toString().trim()       );
              txtFolioVendedor.setText  (tablacompleta[0][11].toString().trim()       );
              txtVendedor.setText       (tablacompleta[0][12].toString().trim()       );
              cmb_status.setSelectedItem(tablacompleta[0][19].toString().trim()       );
              
       		  panel(false);
       		  tabla.enable(false);
       		calculo();
       		dispose();
       		
		    }
		    }
		
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Ventas_Express().setVisible(true);
		}catch(Exception e){	}
	}
}
