package Cat_Seguridad;

import java.awt.Component;
import java.awt.Container;
import java.awt.Event;
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
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
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

import Cat_Checador.Cat_Reloj_Sincronizado_Servidor;
import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Connexion;
import Obj_Compras.Obj_Ubicaciones_De_Productos;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_Filtro_Dinamico_Plus;
import Obj_Principal.Obj_tabla;
import Obj_Seguridad.Obj_Autorizacion_Acceso_Proveedores;
import Obj_Seguridad.Obj_Registro_Proveedores;

@SuppressWarnings("serial")
public class Cat_Registro_De_Entrada_y_Salida_De_Proveedores extends JFrame{
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	Connexion con = new Connexion();
	
	Obj_tabla ObjTab =new Obj_tabla();
	Obj_Registro_Proveedores proveedores = new Obj_Registro_Proveedores();
	Obj_Autorizacion_Acceso_Proveedores autorizacion = new 	Obj_Autorizacion_Acceso_Proveedores();
	
	Cat_Reloj_Sincronizado_Servidor trae_hora = new Cat_Reloj_Sincronizado_Servidor();
	 
	String[][] tablaprecargadaproductos;
	int columnas = 3,checkbox=-1;
	@SuppressWarnings("rawtypes")
	public Class[] basemovimientos (){
		Class[] types = new Class[columnas];
		for(int i = 0; i<columnas; i++){types[i]= java.lang.Object.class;}
		 return types;
	}
	public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"Movimiento","Hora","Observaciones"}){
		 @SuppressWarnings("rawtypes")
			Class[] types = basemovimientos();
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
			public boolean isCellEditable(int fila, int columna){if(columna>1){return true;}else{ return false;}}
	};
	JTable tablaEntrada_Salida = new JTable(modelo);
	public JScrollPane Scroll_tablaEntrada_Salida = new JScrollPane(tablaEntrada_Salida);
	
 	@SuppressWarnings("rawtypes")
 	public Class[] baseorden (){
 		Class[] types = new Class[columnas];
 		for(int i = 0; i<columnas; i++){types[i]= java.lang.Object.class;}
 		 return types;
 	}
 	public DefaultTableModel modeloorden = new DefaultTableModel(null, new String[]{"Factura","Importe","Oden De Compra","Importe","Diferencia","Tipo Proveedor"}){
 		 @SuppressWarnings("rawtypes")
 			Class[] types = baseorden();
 			@SuppressWarnings({ "rawtypes", "unchecked" })
 			public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
 			public boolean isCellEditable(int fila, int columna){
 				return false;
 				}
 	};
   	JTable tablaOrden = new JTable(modeloorden);
   	public JScrollPane Scroll_tablaOrden = new JScrollPane(tablaOrden);
        @SuppressWarnings({ "rawtypes", "unused" })
       private TableRowSorter trsfiltroOrden;
     
        
       int columnasdev=5;        
        @SuppressWarnings("rawtypes")
    public Class[] baseproductodev (){
     		Class[] types = new Class[columnasdev];
     		for(int i = 0; i<columnasdev; i++){types[i]= java.lang.Object.class;}
     		 return types;
     	}
     	public DefaultTableModel modelodev = new DefaultTableModel(null, new String[]{"Codigo","Producto","Cantidad","Razon","Observaciones"}){
     		 @SuppressWarnings("rawtypes")
     			Class[] types = baseproductodev();
     			@SuppressWarnings({ "rawtypes", "unchecked" })
     			public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
     			public boolean isCellEditable(int fila, int columna){
     				if(columna>1)return true;
     				return false;}
     	};
       	JTable tablaDevolucion = new JTable(modelodev);
       	public JScrollPane Scroll_tablaDevolucion = new JScrollPane(tablaDevolucion);
            @SuppressWarnings({ "rawtypes", "unused" })
           private TableRowSorter trsfiltroDevolucion;
            
	JTextField txtFolio           = new Componentes().text(new JCTextField(), "Folio", 10                                    ,"Int"   );
	JTextField txtFolio_solicitud = new Componentes().text(new JCTextField(), "Solicitud", 10                                ,"Int"   );
	JTextField txtProveedor       = new Componentes().text(new JCTextField(), "Proveedor", 200                               ,"String");
	JTextField txtEjecutivo       = new Componentes().text(new JCTextField(), "Nombre Del Chofer Del Proveedor"  ,200        ,"String");
	JTextField txtFechaGuardo     = new Componentes().text(new JCTextField(), "Fecha Guardo"                     ,200        ,"String");
	JTextField txtcodigo_prod     = new Componentes().text(new JCTextField(), "Codigo del Producto"              ,15         ,"String");
	
	JPasswordField PtxtClave   = new Componentes().textPassword(new JPasswordField(), "Clave", 100);
	
	String status[] = {"Vigente","Cancelado"};
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmb_status = new JComboBox(status);
	
	String establecimientoScoi[] = new Obj_Establecimiento().Combo_Establecimiento201();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbEstablecimiento = new JComboBox(establecimientoScoi);
	
	@SuppressWarnings("rawtypes")
	JComboBox cmbRespuesta = new JComboBox();
	
	JToolBar menu_toolbar      = new JToolBar();
	JCButton btnBuscar         = new JCButton("Buscar"    ,"Filter-List-icon16.png"      ,"Azul"); 
	JCButton btnModificar      = new JCButton("Modificar" ,"Modify.png"                  ,"Azul");
	JCButton btnNuevo          = new JCButton("Nuevo"     ,"Nuevo.png"                   ,"Azul");
	JCButton btnGuardar        = new JCButton("Guardar"   ,"Guardar.png"                 ,"Azul");
	JCButton btnDeshacer       = new JCButton("Deshacer"  ,"deshacer16.png"              ,"Azul");
	JCButton btnderecha        = new JCButton(""          ,"adelante.png"                ,"Azul");
	JCButton btnizquierda      = new JCButton(""          ,"atras.png"                   ,"Azul");
	JCButton btnfilproveedor   = new JCButton(""        ,"Filter-List-icon16.png"        ,"Azul");
	JCButton btnFiltroProducto = new JCButton("Agregar Producto","Filter-List-icon16.png","Azul" );
	JCButton btnEljProducto    = new JCButton("Eliminar Producto"        ,"eliminar-bala-icono-7773-32.png" ,"Azul" );
	
	JCButton btnSolicitarAcceso= new JCButton("Solicitar Acceso"  ,"investigacion-icono-9565-48.png" ,"Gris");
	JCButton btnValidar        = new JCButton("Validar Autorizado" ,"yellow-check-icone-4881-48.png" ,"Gris");
	JCButton btnAutorizado     = new JCButton("Acceso Autorizado" ,"green-check-icone-4881-48.png"   ,"Verde");
	
	JToolBar toolbarEntradaSalida= new JToolBar();
	JCButton btnAgregMov         = new JCButton("Agregar Movimiento","double-arrow-icone-3883-16.png"  ,"Azul" );
	JCButton btnEljLunes         = new JCButton("Eliminar","eliminar-bala-icono-7773-32.png","Azul" );
	
	JToolBar toolbarOden    = new JToolBar();
	JCButton btnAgregFactura= new JCButton("Agregar Factura" ,"double-arrow-icone-3883-16.png"  ,"Azul" );
	JCButton btnEljOrden    = new JCButton("Eliminar Factura"        ,"eliminar-bala-icono-7773-32.png" ,"Azul" );
	
	JLabel lblHora   = new JLabel();
	JLabel lblFecha  = new JLabel();
	JLabel lblNombre = new JLabel();
	JLabel lblFolio  = new JLabel();
	
	JTextArea  txaObservaciones  = new Componentes().textArea(new JTextArea(), "Observaciones Generales"       ,500);
	JScrollPane scrollobjet      = new JScrollPane(txaObservaciones);
	
	String NuevoModifica ="";
	String FProductosCargado ="";
	
	String[][] tablaproveedores;
	String[][] tablaordenes_compra;
	Object[]   vector = new Object[3];
	public Cat_Registro_De_Entrada_y_Salida_De_Proveedores(){
		this.setSize(819,600);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/asistencia.png"));
		this.setTitle("Registro De Entrada y Salida De Proveedores");
		this.panel.setBorder(BorderFactory.createTitledBorder("Registro De Entrada y Salida De Proveedores"));
		
		this.menu_toolbar.add(btnBuscar   );
//	    this.menu_toolbar.addSeparator(   );
//	    this.menu_toolbar.addSeparator(   );
//	    this.menu_toolbar.add(btnModificar);
	    this.menu_toolbar.addSeparator(   );
	    this.menu_toolbar.addSeparator(   );
	    this.menu_toolbar.add(btnNuevo    );
	    this.menu_toolbar.addSeparator(   );
	    this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.add(btnDeshacer );
		this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.add(btnGuardar  );
		this.menu_toolbar.setFloatable(false);
		
		trae_hora.lblHora.setFont(new java.awt.Font("Arial",0,40));
		lblFecha.setFont(new java.awt.Font("Arial",0,40));
		
		 int x=15, y=0,width=133,height=20,sep=75;
		this.panel.add(lblFecha).setBounds                      (x+480 ,y      ,width*2    ,height*3);
		this.panel.add(trae_hora.lblHora).setBounds             (x+690 ,y      ,width*2    ,height*3); 
		this.panel.add(menu_toolbar).setBounds                  (x     ,y+=20  ,width*4    ,height );
		this.panel.add(new JLabel("Folio:")).setBounds          (x     ,y+=40  ,width      ,height );
		this.panel.add(txtFolio).setBounds                      (x+=sep,y      ,width      ,height );
        this.panel.add(btnizquierda).setBounds                  (x+=135,y      ,height     ,height );
        this.panel.add(btnderecha).setBounds                    (x+=25 ,y      ,height     ,height );
		this.panel.add(new JLabel("Estatus:")).setBounds        (x+=55 ,y      ,width      ,height );
		this.panel.add(cmb_status).setBounds                    (x+=45 ,y      ,138      ,height );
		this.panel.add(new JLabel("Pase El Gafete:")).setBounds (x=15  ,y+=30  ,width      ,height ); 
		this.panel.add(PtxtClave).setBounds                     (x+sep ,y      ,width      ,height );
		this.panel.add(lblFolio).setBounds                      (x+210 ,y      ,width      ,height ); 
		this.panel.add(lblNombre).setBounds                     (x+250 ,y      ,width*4    ,height ); 
		this.panel.add(new JLabel("Establecimiento:")).setBounds(x     ,y+=30  ,width*2    ,height );
		this.panel.add(cmbEstablecimiento).setBounds            (x+sep ,y      ,width*3    ,height );
		this.panel.add(new JLabel("Proveedor:")).setBounds      (x     ,y+=30  ,width      ,height );
		this.panel.add(txtProveedor).setBounds                  (x+sep ,y      ,width*3-20 ,height );
		this.panel.add(btnfilproveedor).setBounds               (x+455 ,y      ,height     ,height );	
		this.panel.add(new JLabel("Ejecutivo:")).setBounds      (x     ,y+=30  ,width      ,height );
		this.panel.add(txtEjecutivo).setBounds                  (x+sep ,y      ,width*3    ,height );
		this.panel.add(btnSolicitarAcceso).setBounds            (x     ,y+=30  ,197        ,48     );
		this.panel.add(txtFolio_solicitud).setBounds            (x+207 ,y      ,60         ,height );
		this.panel.add(btnAutorizado     ).setBounds            (x+273 ,y      ,200        ,48     );
		this.panel.add(btnValidar        ).setBounds            (x+273 ,y      ,200        ,48     );
		this.panel.add(toolbarOden).setBounds                   (x     ,y+=60  ,335        ,height );
		this.panel.add(txtFechaGuardo).setBounds                (x+337 ,y      ,width      ,height );
		this.panel.add(Scroll_tablaOrden).setBounds             (x     ,y+=25  ,785        ,115    );
		this.panel.add(toolbarEntradaSalida).setBounds          (x=500 ,y=60   ,width=300  ,height );
		this.panel.add(Scroll_tablaEntrada_Salida).setBounds    (x     ,y+=25  ,width      ,115    );
		this.panel.add(new JLabel("Observaciones:")).setBounds  (x     ,y+=120 ,width      ,height );
		this.panel.add(scrollobjet).setBounds                   (x     ,y+=15  ,width      ,70     );

		this.panel.add(txtcodigo_prod).setBounds                (x=15  ,y+=205 ,130        ,height );
		this.panel.add(btnFiltroProducto).setBounds             (x+160  ,y     ,170        ,height );
		this.panel.add(btnEljProducto).setBounds                (x+360 ,y      ,178        ,height );
		
		this.panel.add(Scroll_tablaDevolucion).setBounds        (x     ,y+=25  ,785      ,115    );
		
		ObjTab.tabla_precargada(tablaEntrada_Salida);
		ObjTab.tabla_precargada(tablaOrden         );
		ObjTab.tabla_precargada(tablaDevolucion    );
		
		    this.toolbarEntradaSalida.add(btnAgregMov);
			this.toolbarEntradaSalida.addSeparator(    );
			this.toolbarEntradaSalida.addSeparator(    );
		    this.toolbarEntradaSalida.add(btnEljLunes  );
			this.toolbarEntradaSalida.setFloatable(false);

		    this.toolbarOden.add(btnAgregFactura);
			this.toolbarOden.addSeparator(      );
			this.toolbarOden.addSeparator(      );
		    this.toolbarOden.add(btnEljOrden    );
			this.toolbarOden.setFloatable(false );
		
			this.tablaEntrada_Salida.getColumnModel().getColumn(2).setMinWidth(148);
			this.tablaOrden.getColumnModel().getColumn(0).setMinWidth(x=130);
			this.tablaOrden.getColumnModel().getColumn(1).setMinWidth(x    );
			this.tablaOrden.getColumnModel().getColumn(2).setMinWidth(x    );
			this.tablaOrden.getColumnModel().getColumn(3).setMinWidth(x    );
			this.tablaOrden.getColumnModel().getColumn(4).setMinWidth(x    );
			this.tablaOrden.getColumnModel().getColumn(5).setMinWidth(x+3  );
			this.tablaDevolucion.getColumnModel().getColumn(1).setMinWidth(300  );
			this.tablaDevolucion.getColumnModel().getColumn(3).setMinWidth(150  );
			this.tablaDevolucion.getColumnModel().getColumn(4).setMinWidth(182  );
			
			panelEnabledFalse();
			
		this.btnAgregFactura.addActionListener(opBuscarOrden_Compra);
		this.btnEljLunes.addActionListener(new opEliminarfila(tablaEntrada_Salida));
		this.btnAgregMov.addActionListener(new opAgregar_Movimiento(tablaEntrada_Salida));
		this.btnEljOrden.addActionListener(new opEliminarfila_selecionada(tablaOrden));
		this.btnEljProducto.addActionListener(new opEliminarfila_selecionada(tablaDevolucion));
		this.btnNuevo.addActionListener          (nuevo                   );		
		this.btnfilproveedor.addActionListener   (opBuscarProveedor       );
		this.btnGuardar.addActionListener        (guardar                 );
		this.btnDeshacer.addActionListener       (deshacer                );
		this.btnBuscar.addActionListener         (buscar                  );
		this.btnderecha.addActionListener        (opDerecha               );
		this.btnizquierda.addActionListener      (opIzquierda             );
		this.btnValidar.addActionListener        (valida_autorizado       );
		this.btnSolicitarAcceso.addActionListener(solicitar_autorizacion  );
		this.cmbEstablecimiento.addActionListener(seleccionestablecimiento);
		this.btnFiltroProducto.addActionListener (buscar_producto         );
		this.PtxtClave.addKeyListener            (busqueda_datos          );
		this.txtcodigo_prod.addKeyListener       (Buscar_Datos_Producto   );
		Seleccionar_Razon(tablaDevolucion);
		tablaDevolucion.addKeyListener(op_validanumero_en_celda);
		
		cont.add(panel);
		
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape"                );
        getRootPane().getActionMap().put("escape", new AbstractAction(){ public void actionPerformed(ActionEvent e){ btnDeshacer.doClick(); }  });
         
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F9, 0), "nuevo"                     );
        getRootPane().getActionMap().put("nuevo", new AbstractAction(){ public void actionPerformed(ActionEvent e){ btnNuevo.doClick();     }  });
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_N,Event.CTRL_MASK),"nuevo"          );
        getRootPane().getActionMap().put("nuevo", new AbstractAction(){ public void actionPerformed(ActionEvent e) { btnNuevo.doClick();    }  });
         
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_G,Event.CTRL_MASK),"guardar"        );
        getRootPane().getActionMap().put("guardar", new AbstractAction(){public void actionPerformed(ActionEvent e){btnGuardar.doClick();   }  });
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0), "guardar"                  );
        getRootPane().getActionMap().put("guardar", new AbstractAction(){public void actionPerformed(ActionEvent e) { btnGuardar.doClick(); }  });    
        
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), "buscar"                );
        getRootPane().getActionMap().put("buscar", new AbstractAction(){ public void actionPerformed(ActionEvent e){ btnBuscar.doClick(); }  });
		tablaproveedores = proveedores.refrescar_tablas();
		lblFecha.setText(tablaproveedores[0][2].toString());
		txtFechaGuardo.setText(tablaproveedores[0][2].toString());
//		txtFolio_solicitud.setText("14");	btnValidar.setEnabled(true);
		
	}
	
	KeyListener Buscar_Datos_Producto = new KeyListener() {
		public void keyTyped(KeyEvent e){}
		public void keyReleased(KeyEvent e) {}
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
		
				if(cmbEstablecimiento.getSelectedIndex()==0){
					      JOptionPane.showMessageDialog(null,"Debe de Seleccionar Primero Un Establecimiento: ","Aviso!", JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
					      cmbEstablecimiento.requestFocus();
						  cmbEstablecimiento.showPopup();
					   	 return;		
					}else{
						String codigo=txtcodigo_prod .getText().toUpperCase().trim();
						String cod_prod=new BuscarSQL().cod_prod_principal_bms(codigo);	
						
						if(!cod_prod.equals("false no existe") ){
						  Obj_Ubicaciones_De_Productos  Datos_Producto= new Obj_Ubicaciones_De_Productos().buscardatos_producto(cod_prod,cmbEstablecimiento.getSelectedItem().toString().trim().toUpperCase()+"");
						  String[] vector_producto = new String[5];
						    vector_producto[0]=Datos_Producto.getCod_Prod().toString().trim();
							vector_producto[1]=Datos_Producto.getDescripcion_Prod().toString().trim();
							vector_producto[2]="0";
							vector_producto[3]="Seleciona Una Razon";
							vector_producto[4]= ""; 
							 modelodev.addRow(vector_producto);
							txtcodigo_prod.requestFocus();
						 }else{
								JOptionPane.showMessageDialog(null, "El Codigo "+cod_prod+" Esta Mal Escrito o El Producto No Existe" , "Aviso", JOptionPane.CANCEL_OPTION,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
								txtcodigo_prod.requestFocus();
								cmbEstablecimiento.setEnabled(true);
								return;
		                }
				}	 
			}
		}
	};
	
	KeyListener op_validanumero_en_celda = new KeyListener() {
		public void keyTyped(KeyEvent e) {}
		public void keyReleased(KeyEvent e) {
			int fila;
			fila=tablaDevolucion.getSelectedRow();
			if(fila==-1)fila=fila+1;
			if(ObjTab.validacelda(tablaDevolucion,"decimal", fila, 1)){
				  if(ObjTab.RecorridoFocotabla(tablaDevolucion, fila, 1, "x").equals("si")){
						btnFiltroProducto.requestFocus();
					};
			}
		}
		public void keyPressed(KeyEvent e) {}
	};
	
	private void Seleccionar_Razon(final JTable tbl) {
	    tbl.addMouseListener(new java.awt.event.MouseAdapter() {
			@SuppressWarnings("deprecation")
			public void mousePressed(MouseEvent e) {
	        	if(e.getClickCount()==1){
                     if(tbl.getSelectedColumn()==3){
                	        tbl.getColumnModel().getColumn(3).setCellEditor(new javax.swing.DefaultCellEditor(cmbRespuesta));
                		    tbl.getColumn("Razon").setCellEditor(new CargaDatosDelCombo());
					       return;
                     } else{
					        tbl.lostFocus(null, null);
					        tbl.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
					        tbl.getSelectionModel().clearSelection();
                    	 return;
                     }	
	        	}
	        }
	    });
	}
	
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
				String respuestas="Caducado/Exceso/Faltante/Merma/Proximo A Caducar";
				String tipo_de_respuestas[] = respuestas.split("/");
				for(int i=0; i<tipo_de_respuestas.length; i++)  combo.addItem(tipo_de_respuestas[i]);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
            return combo;          
        }
    }
	
	ActionListener valida_autorizado = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			Obj_Autorizacion_Acceso_Proveedores autorizacion = new 	Obj_Autorizacion_Acceso_Proveedores().Valida_autorizacion(txtFolio_solicitud.getText().toString().trim());
			 String status=autorizacion.getEstatus().toString();
			 
			if(status.equals("NEGADO")||status.equals("CANCELADO")) {
			   JOptionPane.showMessageDialog(null, "El Acceso Fue: "+status+" \nMotivo: "+autorizacion.getMotivo_negado_acceso().toString()+"\nObservacion: "+autorizacion.getObservaciones().toString() , "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/Dialog-stop-hand64.png"));		    	
		       btnValidar.setText(status);
		       btnValidar.setIcon(new ImageIcon("Imagen/Dialog-stop-hand64.png"));
			}else {
				if(status.equals("SOLICITADO")) {
					   JOptionPane.showMessageDialog(null, "Su Solicitud Esta Siendo Revisada \nEn Caso De Tardanza Comunicarse a Seguridad Monitores", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/telefono-celular-hp-ipaq-2-de-telefonia-movil-icono-6906-64.png"));		    	
					}else {
						if(status.equals("AUTORIZADO")||status.equals("REVISAR")) {
							 panelEnabledTrue();
							btnValidar.setVisible(false);
							btnAutorizado.setVisible(true);
							btnAutorizado.setEnabled(true);
							btnGuardar.setEnabled(true);
						}else {
							   JOptionPane.showMessageDialog(null,"revisar sistemas "+status, "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/Dialog-stop-hand64.png"));		    	
						}
					}
			}
		}
	};
	
	public String Valida_solicitud_autorizacion(){
	    String Mensaje ="Para Poder Solicitar Autorización Es Requerido Alimente:";
	    if(lblFolio.getText().equals("")){Mensaje+="\nEl La Persona Que Recibe Al Proveedor"; }
	    if(cmbEstablecimiento.getSelectedIndex()==0){Mensaje+="\nEl Establecimiento";         }
	    if(txtProveedor.getText().equals("")){Mensaje+="\nEl Nombre Del Proveedor";           }
	    if(txtEjecutivo.getText().equals("")){Mensaje+="\nEl Nombre Ejecutivo Del Proveedor"; }
		return Mensaje;
	}	
	
	ActionListener seleccionestablecimiento = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(cmbEstablecimiento.getSelectedIndex()!=0) {
				btnfilproveedor.doClick();
			}else {
              cmbEstablecimiento.requestFocus();
			}
		}
	};
	
	ActionListener buscar_producto = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			new Cat_Filtro_De_Productos().setVisible(true);
		}
	};
	
	ActionListener solicitar_autorizacion = new ActionListener(){
		public void actionPerformed(ActionEvent e){
		    String Mensaje = Valida_solicitud_autorizacion();
			if(!Mensaje.equals("Para Poder Solicitar Autorización Es Requerido Alimente:")){
				JOptionPane.showMessageDialog(null, Mensaje, "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
			}else{
				autorizacion.setUsuario_solicita(Integer.valueOf(lblFolio.getText().toString()));
				autorizacion.setEstablecimiento(cmbEstablecimiento.getSelectedItem().toString()); 					
				autorizacion.setProveedor(txtProveedor.getText().toString().trim());
				autorizacion.setChofer(txtEjecutivo.getText().toString().trim());
				autorizacion.setEstatus("S");
				autorizacion.setEstatus(cmb_status.getSelectedItem().toString().trim());
				autorizacion.setNuevoModifica("N");
				if(autorizacion.Guardar_Captura()){
					txtFolio_solicitud.setText(autorizacion.getFolio()+"");
				    cmbEstablecimiento.setEnabled(false); 
					btnfilproveedor.setEnabled(false);
					txtEjecutivo.setEditable(false);
				    btnValidar.setEnabled(true);
				    btnSolicitarAcceso.setEnabled(false);
					return;
				}else{
					JOptionPane.showMessageDialog(null,"Error Al Guardar Avise al Administrador del Sistema","Aviso",JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-icono-eliminar5252-64.png"));
					return;
				}	
			}
		}
	};
	
	 KeyListener busqueda_datos = new KeyListener(){
			public void keyReleased(KeyEvent evento) {
				if(evento.getKeyCode()==KeyEvent.VK_ENTER){
				     @SuppressWarnings("deprecation")
					     String clave_checador=PtxtClave.getText().toString().toUpperCase().trim();
					     int posicionC = clave_checador.indexOf('C');
					     lblNombre.setText("");
					     lblFolio.setText("");
				 	if(posicionC>0){
				 		if(isNumeric(clave_checador.substring(0, posicionC))){
							Obj_Registro_Proveedores provedorvalida =new Obj_Registro_Proveedores().Valida_existe_colaborador(clave_checador);
							if(provedorvalida.getExiste().equals("true")){
								 PtxtClave.setEditable(false);
								 cmbEstablecimiento.setSelectedItem(provedorvalida.getEstablecimiento());
								 lblFolio.setText(provedorvalida.getFolio_colaborador_recibe()+"");
								 lblNombre.setText(provedorvalida.getNombre_recibe());
                                 txtEjecutivo.setEditable(true);
                                 btnfilproveedor.setEnabled(true);
								 cmbEstablecimiento.requestFocus();
							     cmbEstablecimiento.showPopup();
							     cmbEstablecimiento.setEnabled(true);
								  return;
							}else{
							   JOptionPane.showMessageDialog(null, "El Colaborador Tiene Un Estatus No Valido", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));		    	
							   PtxtClave.setText("");
							   return;
							}	
				 		}else{
						  JOptionPane.showMessageDialog(null, "La Clave No Corresponde A Ningun Trabajador", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));		    	
						  PtxtClave.setText("");
						  return;
				 		}
				 	}else{
					  JOptionPane.showMessageDialog(null, "La Clave No Corresponde A Ningun Trabajador", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));		    	
					  PtxtClave.setText("");
					  return;
				 	}
				}	 	
			}
			public void keyTyped(KeyEvent arg0) {}
			public void keyPressed(KeyEvent arg0) {}		
	};	
		
	private static boolean isNumeric(String cadena){
	     	try {
	     		Integer.parseInt(cadena);
	     		return true;
	     	} catch (NumberFormatException nfe){
	     		return false;
	     	}
	}

	class opAgregar_Movimiento implements ActionListener{   
		JTable tablaparametro;
	    public opAgregar_Movimiento(final JTable tblp){
	    	tablaparametro = tblp;
	    }
	    public void actionPerformed(ActionEvent evt){
	    	DefaultTableModel modeloparametro= (DefaultTableModel) tablaparametro.getModel();
	    	Object[]   vectorPar = new Object[5];
	    	int filas =tablaparametro.getRowCount();
                  if(filas%2==0)vectorPar[0] ="Entrada" ;
				   else vectorPar[0] ="Salida" ; 
                  vectorPar[1] =trae_hora.lblHora.getText().toString().trim();
                  vectorPar[2] ="" ;
    	    		modeloparametro.addRow(vectorPar);
    				return;
	    }
	};
	
	class opEliminarfila implements ActionListener{   
		JTable tablaparametro;
	    public opEliminarfila(final JTable tblp){
	    	tablaparametro = tblp;
	    }
		public void actionPerformed(ActionEvent evt){
	    	DefaultTableModel modeloparametro= (DefaultTableModel) tablaparametro.getModel();
	    	if(!tablaparametro.isRowSelected(tablaparametro.getSelectedRow())){
				JOptionPane.showMessageDialog(null, "Es Requerido El Selecionar Una Fila Para Poder Eliminarla ", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;	
	    	}
	    	if(tablaparametro.getSelectedRow()==0){
				JOptionPane.showMessageDialog(null, "El Primer Registro De Entrada No Se Puede Eliminar", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;		
	    	}
	    	if(tablaparametro.getRowCount()>0){
					Object primeraColum     = modeloparametro.getValueAt(tablaparametro.getSelectedRow(),1);
					modeloparametro.setValueAt(primeraColum,tablaparametro.getSelectedRow()    ,1);
		    		modeloparametro.removeRow(tablaparametro.getSelectedRow());
					
						tablaparametro.setRowSelectionInterval(tablaparametro.getSelectedRow()+1,tablaparametro.getSelectedRow()+1);
						for(int i =0; i<tablaparametro.getRowCount(); i++){
							if(i%2==0)modeloparametro.setValueAt("Entrada",i,0);
							   else modeloparametro.setValueAt("Salida",i,0);
				    	  };
	    	}else{
				JOptionPane.showMessageDialog(null, "Es Requerido Que Seleccione Una Fila De La Tabla Para Eliminar", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
	    	}
	    }
	};
	
	class opEliminarfila_selecionada implements ActionListener{   
		JTable tablaparametro;
	    public opEliminarfila_selecionada(final JTable tblp){
	    	tablaparametro = tblp;
	    }
		public void actionPerformed(ActionEvent evt){
	    	DefaultTableModel modeloparametro= (DefaultTableModel) tablaparametro.getModel();
	    	if(!tablaparametro.isRowSelected(tablaparametro.getSelectedRow())){
				JOptionPane.showMessageDialog(null, "Es Requerido El Selecionar Una Fila Para Poder Eliminarla ", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;	
	    	}
	    	if(tablaparametro.getRowCount()>0){
					Object primeraColum     = modeloparametro.getValueAt(tablaparametro.getSelectedRow(),1);
					modeloparametro.setValueAt(primeraColum,tablaparametro.getSelectedRow()    ,1);
		    		modeloparametro.removeRow(tablaparametro.getSelectedRow());
	    	}else{
				JOptionPane.showMessageDialog(null, "Es Requerido Que Seleccione Una Fila De La Tabla Para Eliminar", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
	    	}
	    }
	};
	
	ActionListener opBuscarProveedor = new ActionListener(){
		public void actionPerformed(ActionEvent e){
         new Cat_Filtro_Proveedores().setVisible(true);
		}
	};
	
	ActionListener opBuscarOrden_Compra = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(cmbEstablecimiento.getSelectedIndex()==0){
				JOptionPane.showMessageDialog(null, "Es Requerido Que Seleccione Un Establecimiento Para Buscar La Orden De Compra", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}
			tablaordenes_compra=proveedores.refrescar_tablas_ordenes_de_compra(cmbEstablecimiento.getSelectedItem().toString().trim());
         new Cat_Filtro_Ordenes_de_Compra().setVisible(true);
     	
		}
	};
	
	ActionListener seleccionar_estab = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			 btnfilproveedor.doClick();
		}		
	};

	ActionListener deshacer = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			panelLimpiar();
			panelEnabledFalse();
			btnNuevo.setEnabled(true);
			NuevoModifica="";
		}
	};
	
	ActionListener nuevo = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			panelLimpiar();
			txtFolio.setText(new Obj_Registro_Proveedores().Nuevo()+"");
			NuevoModifica="N";
			PtxtClave.setEditable(true);
            PtxtClave.requestFocus();   
    		cmb_status.setEnabled(false);
    		vector[0] ="Entrada" ;
    		vector[1] =trae_hora.lblHora.getText().toString().trim();
    		vector[2] ="" ;
    		modelo.addRow(vector);
    		btnModificar.setEnabled(false);
			return;
		}
	};
		
	public void cargar_datos_tablas(int folio){
			  Obj_Registro_Proveedores proveedores = new Obj_Registro_Proveedores().refrescar_tablas(folio);
			  String [][] Tabla_facturas     = proveedores.getTabla_facturas(); 
			  String [][] Tabla_registros    = proveedores.getTabla_registros(); 
			  String [][] Tabla_devoluciones = proveedores.getTabla_devolucion(); 
			  
			  Object []   vectorfac = new Object[6];
			  Object []   vectorreg = new Object[3];
			  Object []   vectordev = new Object[5];

			  txtFolio.setText                  (proveedores.getFolio()+""                 );
			  cmb_status.setSelectedItem        (proveedores.getEstatus()                  );
			  lblFolio.setText                  (proveedores.getFolio_colaborador_recibe()+"");
			  lblNombre.setText                 (proveedores.getNombre_recibe()            );
			  cmbEstablecimiento.setSelectedItem(proveedores.getEstablecimiento().trim()   );
			  txaObservaciones.setText          (proveedores.getObservaciones().toString() );
			  txtProveedor.setText              (proveedores.getProveedor().toString()     );
			  txtEjecutivo.setText              (proveedores.getChofer().toString()        );
			  txtFechaGuardo.setText            (proveedores.getFecha().toString()         );
			  txtFolio_solicitud.setText        (proveedores.getFolio_solicitud()+""       );
			  
			for(int i=0;i<Tabla_facturas.length;i++){
				for(int j=0;j<6;j++){
					vectorfac[j] = Tabla_facturas[i][j].toString();
				}
					modeloorden.addRow(vectorfac);
			 }	
			
			for(int i=0;i<Tabla_registros.length;i++){
				for(int j=0;j<3;j++){
					vectorreg[j] = Tabla_registros[i][j].toString();
				}
					modelo.addRow(vectorreg);
			 }	
			
				for(int i=0;i<Tabla_devoluciones.length;i++){
					for(int j=0;j<5;j++){
						vectordev[j] = Tabla_devoluciones[i][j].toString();
					}
						modelodev.addRow(vectordev);
			     }
	}
	
	ActionListener opDerecha = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			int Folio=0;
			if(!txtFolio.getText().equals("")){
				Folio=Integer.valueOf(txtFolio.getText().trim());
			}
			panelLimpiar();
			panelEnabledFalse();
			cargar_datos_tablas(Folio+1);
		}
	};
	
	ActionListener opIzquierda = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			int Folio=0;
			if(!txtFolio.getText().equals("")){
				Folio=Integer.valueOf(txtFolio.getText().trim());
			}
			panelLimpiar();
			panelEnabledFalse();
			cargar_datos_tablas(Folio-1);
		}
	};
	
	ActionListener buscar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			new Cat_Filtro_Buscar_Facturas().setVisible(true);
		}
	};
	
    ActionListener guardar = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				    String Mensaje =Valida();
					if(!Mensaje.equals("Para Poder Guardar Es Requerido Alimente:")){
						JOptionPane.showMessageDialog(null, Mensaje, "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
					}else{
						proveedores.setFolio(Integer.valueOf(txtFolio.getText().toString().trim()));
						proveedores.setFolio_colaborador_recibe(Integer.valueOf(lblFolio.getText().toString()));
						proveedores.setEstablecimiento(cmbEstablecimiento.getSelectedItem().toString()); 					
						proveedores.setProveedor(txtProveedor.getText().toString().trim());
						proveedores.setChofer(txtEjecutivo.getText().toString().trim());
						proveedores.setObservaciones(txaObservaciones.getText().toString().trim());		
						proveedores.setEstatus(cmb_status.getSelectedItem().toString().trim());
						proveedores.setNuevoModifica(NuevoModifica);
						proveedores.setFolio_solicitud(Integer.valueOf(txtFolio_solicitud.getText().toString().trim()));
						proveedores.setTabla_registros(TablaGuardado());
						proveedores.setTabla_facturas(TablaGuardadotb2());
						proveedores.setTabla_devolucion(TablaGuardadoProductos());
						
					if(proveedores.Guardar_Captura()){
    					JOptionPane.showMessageDialog(null,"El Registro Se Guardó Correctamente!","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/aplicara-el-dialogo-icono-6256-32.png"));
    					btnDeshacer.doClick();
    					return;
    				}else{
    					JOptionPane.showMessageDialog(null,"Error Al Guardar Avise al Administrador del Sistema","Aviso",JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-icono-eliminar5252-64.png"));
    					return;
    				}		
				}
		 }
	 };
	
 	 public String[][] TablaGuardado(){
			int rengloneslunes     = tablaEntrada_Salida.getRowCount()    ;
			int filas = rengloneslunes;
			int fila  = 0;
			int i     = 0;
			String[][] tablas = new String[filas][3];	
			while(rengloneslunes > 0){
					tablas[i][0] = modelo.getValueAt(fila, 0)+"";
					tablas[i][1] = modelo.getValueAt(fila, 1)+"";
					tablas[i][2] = modelo.getValueAt(fila, 2)+"";
					i+=1;
					fila+=1;
					rengloneslunes--;
			}
			return tablas;
		}
 	 
 	 public String[][] TablaGuardadotb2(){
			int rengloneslunes     = tablaOrden.getRowCount()    ;
			int filas = rengloneslunes;
			int fila  = 0;
			int i     = 0;
			String[][] tablas = new String[filas][6];	
			while(rengloneslunes > 0){
					tablas[i][0] = modeloorden.getValueAt(fila, 0)+"";
					tablas[i][1] = modeloorden.getValueAt(fila, 1)+"";
					tablas[i][2] = modeloorden.getValueAt(fila, 2)+"";
					tablas[i][3] = modeloorden.getValueAt(fila, 3)+"";
					tablas[i][4] = modeloorden.getValueAt(fila, 4)+"";
					tablas[i][5] = modeloorden.getValueAt(fila, 5)+"";
					i+=1;
					fila+=1;
					rengloneslunes--;
			}
			return tablas;
		}
 
 	 public String[][] TablaGuardadotabla_Productos(){
			int renglonesp     = tablaDevolucion.getRowCount()    ;
			int filas = renglonesp;
			int fila  = 0;
			int i     = 0;
			String[][] tablas = new String[filas][5];	
			while(renglonesp > 0){
					tablas[i][0] = modelodev.getValueAt(fila, 0)+"";
					tablas[i][1] = modelodev.getValueAt(fila, 1)+"";
					tablas[i][2] = modelodev.getValueAt(fila, 2)+"";
					tablas[i][3] = modelodev.getValueAt(fila, 3)+"";
					tablas[i][4] = modelodev.getValueAt(fila, 4)+"";
					i+=1;
					fila+=1;
					renglonesp--;
			}
			return tablas;
		}
 	 
 	 public String[][] TablaGuardadoProductos(){
			int renglones     = tablaDevolucion.getRowCount()    ;
			int filas = renglones;
			int fila  = 0;
			int i     = 0;
			String[][] tablasp = new String[filas][5];	
			while(renglones > 0){
					tablasp[i][0] = modelodev.getValueAt(fila, 0)+"";
					tablasp[i][1] = modelodev.getValueAt(fila, 1)+"";
					tablasp[i][2] = modelodev.getValueAt(fila, 2)+"";
					tablasp[i][3] = modelodev.getValueAt(fila, 3)+"";
					tablasp[i][4] = modelodev.getValueAt(fila, 4)+"";
					i+=1;
					fila+=1;
					renglones--;
			}
			return tablasp;
		}
 	 
		
	public String Valida(){
	    String Mensaje ="Para Poder Guardar Es Requerido Alimente:";
	    if(lblFolio.getText().equals("")){Mensaje+="\nEl La Persona Que Recibe Al Proveedor"; }
	    if(cmbEstablecimiento.getSelectedIndex()==0){Mensaje+="\nEl Establecimiento";         }
	    if(txtProveedor.getText().equals("")){Mensaje+="\nEl Nombre Del Proveedor";              }
	    if(txtEjecutivo.getText().equals("")){Mensaje+="\nEl Nombre Ejecutivo Del Proveedor"; }
	    if(modeloorden.getRowCount()==0){Mensaje+="\nEs Requerido Alimente La Factura Que Se Recibe"; }
	    if(tablaDevolucion.getRowCount()>0) {
	    	for(int i=0;i<tablaDevolucion.getRowCount();i++){
	    		if(Double.valueOf(tablaDevolucion.getValueAt(i, 2).toString())==0){
	    			Mensaje+="\nEs Requerido Alimente Cantidad Mayor Que 0 En Los Productos Con Incidencia ";
	    		}
	    		if(tablaDevolucion.getValueAt(i, 3).toString().equals("Seleciona Una Razon")){
	    			Mensaje+="\nEs Requerido Seleccione Una Razon De La Incidencia Del Producto "+tablaDevolucion.getValueAt(i, 1).toString();
	    		}
	    	}
	    }
		return Mensaje;
	}	
	
	public void panelLimpiar(){	
		modelo.setRowCount(0);
		modeloorden.setRowCount(0);
		modelodev.setRowCount(0);
		lblFolio.setText("");
		lblNombre.setText("");
		txtFolio.setText("");
		txtProveedor.setText("");
		txtEjecutivo.setText("");
		txaObservaciones.setText("");
		txtFolio_solicitud.setText("");
		btnValidar.setText("Validar Autorizado");
		btnValidar.setIcon(new ImageIcon("Imagen/yellow-check-icone-4881-48.png"));
		PtxtClave.setText("");
		cmb_status.setSelectedIndex(0);
		cmbEstablecimiento.setSelectedIndex(0);
		ObjTab.Obj_Filtro(tablaEntrada_Salida    , "", columnas);
	}	
	
	public void panelEnabledFalse(){
		PtxtClave.setEditable(false);
		txtcodigo_prod.setEditable(false);
		txtFolio.setEditable  (false);
		txtFolio_solicitud.setEditable(false);
		txtProveedor.setEditable(false);
		txtEjecutivo.setEditable(false);
		txaObservaciones.setEditable(false);
        btnGuardar.setEnabled (false);
        btnfilproveedor.setEnabled(false);
        btnAgregMov.setEnabled(false);
        btnEljLunes.setEnabled(false);
        btnAgregFactura.setEnabled(false);
        btnEljOrden.setEnabled(false);
        btnModificar.setEnabled(false);
        btnSolicitarAcceso.setEnabled(false);
		btnValidar.setEnabled(false);
		btnAutorizado.setEnabled(false);
		btnValidar.setVisible(true);
		btnAutorizado.setVisible(false);
        btnEljProducto.setEnabled(false);
		txtFechaGuardo.setEditable(false);
		cmb_status.setEnabled (false);
		cmbEstablecimiento.setEnabled(false);
		btnFiltroProducto.setEnabled(false);
	}		
	
	public void panelEnabledTrue(){	
        btnAgregMov.setEnabled(true);
        btnEljLunes.setEnabled(true);
        btnAgregFactura.setEnabled(true);
        btnEljOrden.setEnabled(true);  
        btnEljProducto.setEnabled(true);
        txtcodigo_prod.setEditable(true);
		txaObservaciones.setEditable(true);
		btnFiltroProducto.setEnabled(true);
	}

//TODO inicia filtro Proveedor	
		public class Cat_Filtro_Proveedores extends JDialog{
			Container contf = getContentPane();
			JLayeredPane panelf = new JLayeredPane();
			Connexion con = new Connexion();
			int columnasp = 3,checkbox=-1;
			
			@SuppressWarnings("rawtypes")
			public Class[] base (){
				Class[] types = new Class[columnasp];
				for(int i = 0; i<columnasp; i++){types[i]= java.lang.Object.class;}
				 return types;
			}
			
			public DefaultTableModel modeloprv = new DefaultTableModel(null, new String[]{"Folio", "Nombre de Proveedor", "Domicilio"}){
				 @SuppressWarnings("rawtypes")
					Class[] types = base();
					@SuppressWarnings({ "rawtypes", "unchecked" })
					public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
					public boolean isCellEditable(int fila, int columna){return false;}
			};
			
			JTable tablafprv = new JTable(modeloprv);
			public JScrollPane scroll_tablafp = new JScrollPane(tablafprv);
		     @SuppressWarnings({ "rawtypes" })
		    private TableRowSorter trsfiltro;
			
		    JCButton btnActualizar  = new JCButton("Actualizar"    ,"Actualizar.png"     ,"Azul");  
			JTextField txtBuscarfp  = new Componentes().text(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<", 300, "String");
			
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Cat_Filtro_Proveedores(){
				this.setSize(870,470);
				this.setResizable(false);
				this.setLocationRelativeTo(null);
				this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				this.setModal(true);
				this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Filter-List-icon32.png"));
				this.setTitle("Filtro De Proveedores");
				this.panelf.setBorder(BorderFactory.createTitledBorder("Selecione Un Proveedor Con Doble Click"));
				trsfiltro = new TableRowSorter(modeloprv); 
				tablafprv.setRowSorter(trsfiltro);
				
				ObjTab.tabla_precargada(tablafprv);
				
		    	this.tablafprv.getColumnModel().getColumn(0).setMinWidth(30);		
		    	this.tablafprv.getColumnModel().getColumn(1).setMinWidth(300);
		    	this.tablafprv.getColumnModel().getColumn(2).setMinWidth(458);
		    	
				  Object[]   vectorFiltro = new Object[3];
				for(int i=0;i<tablaproveedores.length;i++){
					  for(int j=0;j<3;j++){
						  vectorFiltro[j] = tablaproveedores[i][j].toString();
					}
					  modeloprv.addRow(vectorFiltro);
				 }	
		
				this.panelf.add(btnActualizar).setBounds    (738,10 ,120 , 20 );
				this.panelf.add(txtBuscarfp).setBounds      (5  ,35 ,852 , 20 );
				this.panelf.add(scroll_tablafp).setBounds   (5  ,55 ,852 ,383);
				this.agregar(tablafprv);
				this.txtBuscarfp.addKeyListener  (opFiltroproveedor);
				this.btnActualizar.addActionListener(actualizar);
				contf.add(panelf);
				
				 this.addWindowListener(new WindowAdapter() {public void windowOpened( WindowEvent e ){
		                	txtBuscarfp.requestFocus();}});
			}
			
			private void agregar(final JTable tbl) {
		        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
			        	if(e.getClickCount()==1){
			        		int fila = tablafprv.getSelectedRow();
			        		txtProveedor.setText  (tablafprv.getValueAt(fila,1)+"");
			                btnSolicitarAcceso.setEnabled(true);
			                txtEjecutivo.setEditable(true);
			                txtEjecutivo.requestFocus();
							dispose();
			        	}
			        }
		        });
		    }

			ActionListener actualizar = new ActionListener(){
				public void actionPerformed(ActionEvent e){
					  tablaproveedores= proveedores.refrescar_tablas();
				      Object[]   vectorActualizar = new Object[3];
					for(int i=0;i<tablaproveedores.length;i++){
						  for(int j=0;j<3;j++){
							  vectorActualizar[j] = tablaproveedores[i][j].toString();
						}
						  modeloprv.addRow(vectorActualizar);
					 }	
				}		
				};
				
	        private KeyListener opFiltroproveedor = new KeyListener(){
				public void keyReleased(KeyEvent arg0) {
					ObjTab.Obj_Filtro(tablafprv, txtBuscarfp.getText().toUpperCase(), columnasp);
				}
				public void keyTyped(KeyEvent arg0) {}
				public void keyPressed(KeyEvent arg0) {}		
			};
		}
		//termina filtro proveedor

		//TODO inicia filtro_factura	
		public class Cat_Filtro_Ordenes_de_Compra extends JDialog{
			Container contf = getContentPane();
			JLayeredPane panelf = new JLayeredPane();
			Connexion con = new Connexion();
			int columnaspo = 11,checkbox=-1;
			
			@SuppressWarnings("rawtypes")
			public Class[] base (){
				Class[] types = new Class[columnaspo];
				for(int i = 0; i<columnaspo; i++){types[i]= java.lang.Object.class;}
				 return types;
			}
            
			public DefaultTableModel modeloor_filtro = new DefaultTableModel(null, new String[]{"Folio","Establecimiento","Proveedor","Productos","Total","Fecha Elaboracion","Fecha Expiracion","Condicion Pago","Plazo","Fecha Autorizacion","Notas"}){
				 @SuppressWarnings("rawtypes")
					Class[] types = base();
					@SuppressWarnings({ "rawtypes", "unchecked" })
					public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
					public boolean isCellEditable(int fila, int columna){return false;}
			};
			
			JTable tablafilordenes = new JTable(modeloor_filtro);
			public JScrollPane scroll_tablafp = new JScrollPane(tablafilordenes);
		     @SuppressWarnings({ "rawtypes" })
		    private TableRowSorter trsfiltro;
		     
		    JCButton btnAceptar          = new JCButton("Aceptar"    ,"double-arrow-icone-3883-16.png"  ,"Azul");  
		    
			JTextField txtBuscarfp       = new Componentes().text(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<", 300, "String");
			JTextField txtFolioFactura   = new Componentes().text(new JCTextField(), "Folio Factura"                , 18, "String");
			JTextField txtImporteFactura = new Componentes().text(new JCTextField(), "Importe Total Factura"        , 10, "Double");
			JTextField txtFolioOrden     = new Componentes().text(new JCTextField(), "Folio Orden De Compra"        , 18, "String");
			JTextField txtImporteorden   = new Componentes().text(new JCTextField(), "Importe Total Orden De Compra", 10, "Double");
			JLabel     JlProveedor         = new JLabel();    
			
			String TipoPoveedor[] = {"Compra","Gasto","Visita"};
			@SuppressWarnings({ "unchecked", "rawtypes" })
			JComboBox cmb_tipo_Proveedor = new JComboBox(TipoPoveedor);
			
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Cat_Filtro_Ordenes_de_Compra(){
				this.setSize(800,380);
				this.setResizable(false);
				this.setLocationRelativeTo(null);
				this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				this.setModal(true);
				this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Filter-List-icon32.png"));
				this.setTitle("Filtro De Ordenes De Compra");
				this.panelf.setBorder(BorderFactory.createTitledBorder("Teclee El Folio De La Factura, Importes y Selecione Una Orden De Compra"));
				trsfiltro = new TableRowSorter(modeloor_filtro); 
				tablafilordenes.setRowSorter(trsfiltro);
				
	            int x=10,y=20,width=780, height=20;
	            this.panelf.add(new JLabel("Folio Factura:")).setBounds    (x     ,y     ,width ,height );
	            this.panelf.add(txtFolioFactura).setBounds                 (x+80  ,y     ,170   ,height ); 
	            this.panelf.add(new JLabel("Importe Factura:")).setBounds  (x+270 ,y     ,width ,height );
	            this.panelf.add(txtImporteFactura).setBounds               (x+360 ,y     ,170   ,height );
	            
	            this.panelf.add(new JLabel("Tipo Proveedor:")).setBounds   (x+550 ,y     ,width ,height );
				this.panelf.add(cmb_tipo_Proveedor).setBounds              (x+630 ,y     ,150   ,height );
				
	            this.panelf.add(new JLabel("Folio Orden C.:")).setBounds   (x     ,y+=25 ,width ,height );
	            this.panelf.add(txtFolioOrden).setBounds                   (x+80  ,y     ,170   ,height ); 
	            this.panelf.add(new JLabel("Importe Orden C.:")).setBounds (x+270 ,y     ,width ,height );
	            this.panelf.add(txtImporteorden).setBounds                 (x+360 ,y     ,170   ,height ); 
				this.panelf.add(btnAceptar).setBounds                      (x+630 ,y+15  ,150   ,height );
	            
				 this.panelf.add(new JLabel("Proveedor:")).setBounds       (x     ,y+=20 ,width ,height );
				this.panelf.add(JlProveedor).setBounds                     (x+80  ,y     ,width ,height );
				this.panelf.add(txtBuscarfp).setBounds                     (x     ,y+=18 ,width ,height );
				this.panelf.add(scroll_tablafp).setBounds                  (x     ,y+=20 ,width ,245    );
				
				ObjTab.tabla_precargada(tablafilordenes);
		    	this.tablafilordenes.getColumnModel().getColumn(0).setMinWidth(70);		
		    	this.tablafilordenes.getColumnModel().getColumn(1).setMinWidth(160);
		    	this.tablafilordenes.getColumnModel().getColumn(2).setMinWidth(390);
		    	this.tablafilordenes.getColumnModel().getColumn(3).setMinWidth(80);
		    	this.tablafilordenes.getColumnModel().getColumn(4).setMinWidth(80);
		    	this.tablafilordenes.getColumnModel().getColumn(5).setMinWidth(130);
		    	this.tablafilordenes.getColumnModel().getColumn(6).setMinWidth(130);
		    	this.tablafilordenes.getColumnModel().getColumn(7).setMinWidth(130);
		    	this.tablafilordenes.getColumnModel().getColumn(9).setMinWidth(130);
		    	this.tablafilordenes.getColumnModel().getColumn(10).setMinWidth(300);
		    	
		    	txtFolioOrden.setEditable(false);
		    	txtImporteorden.setEditable(false);
		        txtBuscarfp.setText(txtProveedor.getText().toString().trim());
		        
				ObjTab.Obj_Filtro(tablafilordenes, txtProveedor.getText().toString().trim().toUpperCase(), columnaspo);
				
				  Object[]   vectorOrdenes = new Object[11];
				for(int i=0;i<tablaordenes_compra.length;i++){
					  for(int j=0;j<11;j++){
						  vectorOrdenes[j] = tablaordenes_compra[i][j].toString();
					}
					  modeloor_filtro.addRow(vectorOrdenes);
				 }	
				
				this.agregar(tablafilordenes);
				this.txtBuscarfp.addKeyListener  (opFiltropuestos );
				this.btnAceptar.addActionListener(aceptar);
				contf.add(panelf);
				 this.addWindowListener(new WindowAdapter() {public void windowOpened( WindowEvent e ){
	                	txtFolioFactura.requestFocus();}});
			}

			ActionListener aceptar = new ActionListener(){
				public void actionPerformed(ActionEvent e){
					Object[]   vectorAgregarFactura = new Object[6];
					 String Mensaje =Validaprov();
						if(!Mensaje.equals("Para Poder Aceptar Es Requerido Alimente:")){
							   JOptionPane.showMessageDialog(null,Mensaje, "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));		    	
						}else{
							if(txtFolioOrden.getText().toString().toUpperCase().trim().equals("")){
								if(JOptionPane.showConfirmDialog(null, "Falta Selecionar Una Orden De compra De La Lista"+"\n ¿Desea Continuar? " ) == 0){
									vectorAgregarFactura[0]=txtFolioFactura.getText().toString().toUpperCase().trim();
									vectorAgregarFactura[1]=txtImporteFactura.getText().toString().toUpperCase().trim();
									vectorAgregarFactura[2]="";
									vectorAgregarFactura[3]=0;
									vectorAgregarFactura[4]= Double.valueOf(txtImporteFactura.getText().toString())- 0; 
									vectorAgregarFactura[5]= cmb_tipo_Proveedor.getSelectedItem().toString().trim();
								      modeloorden.addRow(vectorAgregarFactura);
									  dispose();
//									  return;
								}else{
									return;
								}
							}else{						
								vectorAgregarFactura[0]=txtFolioFactura.getText().toString().toUpperCase().trim();
								vectorAgregarFactura[1]=txtImporteFactura.getText().toString().toUpperCase().trim();
								vectorAgregarFactura[2]=txtFolioOrden.getText().toString().toUpperCase().trim();
								vectorAgregarFactura[3]=txtImporteorden.getText().toString().toUpperCase().trim();
								vectorAgregarFactura[4]= Double.valueOf(txtImporteFactura.getText().toString())- Double.valueOf(txtImporteorden.getText().toString()); 
								vectorAgregarFactura[5]= cmb_tipo_Proveedor.getSelectedItem().toString().trim();
						      modeloorden.addRow(vectorAgregarFactura);
							  dispose();
//							  return;
					        }		
					}
						
						
				}	
			};
			
			public String Validaprov(){
			    String Mensaje ="Para Poder Aceptar Es Requerido Alimente:";
			    if(txtFolioFactura.getText().equals("")){Mensaje+="\nEl Folio De La Factura"; }
			    if(txtImporteFactura.getText().equals("")){Mensaje+="\nEl Importe De La Factura"; }
				return Mensaje;
			}	
			
			private void agregar(final JTable tbl) {
		        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
			        	if(e.getClickCount()==1){
			        		int fila = tablafilordenes.getSelectedRow();
			        			txtFolioOrden.setText  (tablafilordenes.getValueAt(fila,0)+"");	
			        			txtImporteorden.setText  (tablafilordenes.getValueAt(fila,4)+"");	
			        			JlProveedor.setText(tablafilordenes.getValueAt(fila,2)+"");
			        	}
			        }
		        });
		    }
			
	        private KeyListener opFiltropuestos = new KeyListener(){
				public void keyReleased(KeyEvent arg0) {
					ObjTab.Obj_Filtro(tablafilordenes, txtBuscarfp.getText().toUpperCase(), columnaspo);
				}
				public void keyTyped(KeyEvent arg0) {}
				public void keyPressed(KeyEvent arg0) {}		
			};
		}
		
	//TODO inicia filtro_Buscar	
		public class Cat_Filtro_Buscar_Facturas extends JDialog{
			Container contfb = getContentPane();
			JLayeredPane panelfb = new JLayeredPane();
			Connexion con = new Connexion();
			Obj_tabla ObjTab =new Obj_tabla();
			int columnasb = 7,checkbox=-1;
			public void init_tablafp(){
		    	this.tablab.getColumnModel().getColumn( 0).setMinWidth(55);
		    	this.tablab.getColumnModel().getColumn( 1).setMinWidth(60);
		    	this.tablab.getColumnModel().getColumn( 2).setMinWidth(250);
		    	this.tablab.getColumnModel().getColumn( 3).setMinWidth(150);
		    	this.tablab.getColumnModel().getColumn( 4).setMinWidth(200);
		    	this.tablab.getColumnModel().getColumn( 5).setMinWidth(100);
		    	this.tablab.getColumnModel().getColumn( 6).setMinWidth(120);
		    	String comandof=" exec proveedores_registro_de_entradas_y_salidas_filtro";
				String basedatos="26",pintar="si";
				ObjTab.Obj_Refrescar(tablab,modelob, columnasb, comandof, basedatos,pintar,checkbox);
		    }
			
			@SuppressWarnings("rawtypes")
			public Class[] base (){
				Class[] types = new Class[columnasb];
				for(int i = 0; i<columnasb; i++){types[i]= java.lang.Object.class;}
				 return types;
			}
			
			public DefaultTableModel modelob = new DefaultTableModel(null, new String[]{"Folio","Cod Proveedor","Proveedor","Establecimiento","Recibio","Fecha","Observaciones"}){
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
			public Cat_Filtro_Buscar_Facturas(){
				this.setSize(780,350);
				this.setResizable(false);
				this.setLocationRelativeTo(null);
				this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				this.setModal(true);
				this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Filter-List-icon32.png"));
				this.panelfb.setBorder(BorderFactory.createTitledBorder("Selecione Un Registro Con Doble Click"));
				this.setTitle("Filtro De Registro De Entrada y Salida De Proveedores");
				trsfiltro = new TableRowSorter(modelob); 
				tablab.setRowSorter(trsfiltro);
				this.panelfb.add(txtBuscarb).setBounds      (10 ,20 ,750 , 20 );
				this.panelfb.add(scroll_tablab).setBounds   (10 ,40 ,750 ,280 );
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
			        		    panelLimpiar();
			        		    panelEnabledFalse();
							    txtFolio.setText   (tablab.getValueAt(fila,1)+"");
			        		   cargar_datos_tablas(Integer.valueOf(tablab.getValueAt(fila,0).toString()));
							dispose();
			        	}
			        }
		        });
		    }
			
	        private KeyListener opFiltropuestos = new KeyListener(){
				public void keyReleased(KeyEvent arg0) {
					ObjTab.Obj_Filtro(tablab, txtBuscarb.getText(), columnasb);
				}
				public void keyTyped(KeyEvent arg0) {}
				public void keyPressed(KeyEvent arg0) {}		
			};
		}
		
 //TODO incia filtro de productos
		public class Cat_Filtro_De_Productos extends JDialog{
			  Container cont_productos = getContentPane();
			  JLayeredPane panel_productos = new JLayeredPane();
			@SuppressWarnings("rawtypes")
			private TableRowSorter trsfiltro;
			int columnasprod = 7;
			public void carga_inicial_tabla_filtro_productos(){
				String comando="exec inventarios_filtro_catalogo_de_productos_con_80_20 '"+cmbEstablecimiento.getSelectedItem().toString().trim()+"'" ;
				String basedatos="200",pintar="si";
				ObjTab.Obj_Refrescar(tabla_productos, modelo_productos, columnasprod, comando, basedatos,pintar,checkbox);
			}
		    		
			@SuppressWarnings("rawtypes")
			public Class[] baseproductos (){
				Class[] types = new Class[columnasprod];
				for(int i = 0; i<columnasprod; i++){types[i]= java.lang.Object.class;}
				 return types;
			}
			public DefaultTableModel modelo_productos = new DefaultTableModel(null, new String[]{"Codigo Producto","Descripcion","Clase Producto","Categoria","Familia","Marca","80/20"}){
				 @SuppressWarnings("rawtypes")
				Class[] types = baseproductos();
				@SuppressWarnings({ "rawtypes", "unchecked" })
		        public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
				public boolean isCellEditable(int fila, int columna){ return false;}
			};
			
             JTable tabla_productos = new JTable(modelo_productos);
             public JScrollPane scroll_tabla_filtro = new JScrollPane(tabla_productos);

			JTextField txtFiltro_Productos = new Componentes().text(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<", 300, "String");
			Border blackline, etched, raisedbevel, loweredbevel, empty;

			String[] vector_producto = new String[5];
			
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Cat_Filtro_De_Productos(){
				int ancho = 1024;
				int alto = Toolkit.getDefaultToolkit().getScreenSize().height-50;
				this.setSize(ancho, alto);
				this.setResizable(false);
				this.setLocationRelativeTo(null);
				this.setModal(true);
				this.setTitle("Filtro De Busqueda De Productos");
				this.setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/lista-icono-7220-32.png"));
				this.blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
				this.panel_productos.setBorder(BorderFactory.createTitledBorder(blackline,"Doble Click A El Producto Deseado"));
				this.cont_productos.add(panel_productos);
				this.trsfiltro = new TableRowSorter(modelo_productos); 
				tabla_productos.setRowSorter(trsfiltro);
				
				this.agregardev(tabla_productos);
				this.txtFiltro_Productos.addKeyListener(opFiltro);
				
				panel_productos.add(txtFiltro_Productos).setBounds         (15,20 ,500     ,20);
				panel_productos.add(scroll_tabla_filtro).setBounds(15,40 ,ancho-30,alto-70);
				
				if(FProductosCargado.equals("S")){
					ObjTab.tabla_precargada(tabla_productos);
					this.datos_tabla_precargados();
				}else{
					carga_inicial_tabla_filtro_productos();
					tablaprecargadaproductos= ObjTab.tabla_guardar(tabla_productos);
					FProductosCargado="S";
				}
				
		    	this.tabla_productos.getColumnModel().getColumn(0).setMinWidth(90);	
		    	this.tabla_productos.getColumnModel().getColumn(1).setMinWidth(410);
		    	this.tabla_productos.getColumnModel().getColumn(2).setMinWidth(150);
		    	this.tabla_productos.getColumnModel().getColumn(3).setMinWidth(190);
		    	this.tabla_productos.getColumnModel().getColumn(4).setMinWidth(100);
		    	this.tabla_productos.getColumnModel().getColumn(5).setMinWidth(100);
			}

			public void datos_tabla_precargados(){
				modelo_productos.setRowCount(0);
				 String[][] tablacompleta =tablaprecargadaproductos;
				 Object[] vectorp = new Object[columnasprod];
				for(int i=0;i<tablacompleta.length;i++){
					   for(int j=0;j<columnasprod;j++){
						vectorp[j] = tablacompleta[i][j].toString();
						}
					   modelo_productos.addRow(vectorp);
				}
			}
			
			KeyListener opFiltro = new KeyListener(){
				@SuppressWarnings("unchecked")
				public void keyReleased(KeyEvent arg0) {
					int[] columnas_prod ={0,1,2,3,4,5};
					new Obj_Filtro_Dinamico_Plus(tabla_productos , txtFiltro_Productos.getText().toString().trim().toUpperCase(), columnas_prod  );
					trsfiltro.setRowFilter(RowFilter.regexFilter(txtFiltro_Productos.getText(), 0));
				}
				public void keyTyped(KeyEvent arg0) {}
				public void keyPressed(KeyEvent arg0) {}	
			    };
			
			public void agregardev(final JTable tbldev) {
				tbldev.addMouseListener(new MouseListener() {
					public void mouseReleased(MouseEvent e) {
							if(e.getClickCount() == 2){
								int fila_Selec = tbldev.getSelectedRow();
				    			
								vector_producto[0]=tbldev.getValueAt(fila_Selec, 0).toString().trim();
								vector_producto[1]=tbldev.getValueAt(fila_Selec, 1).toString().trim();
								vector_producto[2]="0";
								vector_producto[3]="Seleciona Una Razon";
								vector_producto[4]= ""; 
								 modelodev.addRow(vector_producto);
								 
								 tablaDevolucion.getColumnModel().getColumn(3).setCellEditor(new javax.swing.DefaultCellEditor(cmbRespuesta));
								 tablaDevolucion.getColumn("Razon").setCellEditor(new CargaDatosDelCombo());
				    			dispose();
							}
					}
					public void mousePressed(MouseEvent e) {}
					public void mouseExited(MouseEvent e) {}
					public void mouseEntered(MouseEvent e) {}
					public void mouseClicked(MouseEvent e) {}
				});
			}
		}

		
	public static void main(String args[]){
		try{UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Registro_De_Entrada_y_Salida_De_Proveedores().setVisible(true);
		}catch(Exception e){System.err.println("Error en Main: "+e.getMessage());
		}
	}
}