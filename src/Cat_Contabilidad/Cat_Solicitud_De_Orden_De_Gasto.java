package Cat_Contabilidad;

import java.awt.Color;
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
import java.sql.SQLException;
import java.util.Vector;

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
import javax.swing.text.JTextComponent;

import Cat_Principal.EmailSenderService;
import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Connexion;
import Conexiones_SQL.Generacion_Reportes;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Contabilidad.Obj_Orden_De_Gasto;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextArea;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_tabla;
import Obj_Servicios.Obj_Correos;

@SuppressWarnings("serial")
public class Cat_Solicitud_De_Orden_De_Gasto extends JFrame{
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	Connexion con = new Connexion();
	JToolBar menu_toolbar       = new JToolBar();
	
	Obj_Usuario usuario = new Obj_Usuario().LeerSession();
	Obj_tabla  ObjTab = new Obj_tabla();
	Obj_Orden_De_Gasto gasto = new Obj_Orden_De_Gasto();

	int columnas = 4,checkbox=-1;
  	int fila=0,columna = 0;
	public void init_tabla(){
    	this.tabla.getColumnModel().getColumn(0).setMinWidth(540);	
    	this.tabla.getColumnModel().getColumn(1).setMinWidth(90 );
    	this.tabla.getColumnModel().getColumn(2).setMinWidth(93 );
    	this.tabla.getColumnModel().getColumn(3).setMinWidth(100);
    	
		String comando="Select '' as Descripcion, 0 P_Unitario,0 as Cantidad,0 as Importe" ;
		String basedatos="26",pintar="si";
		ObjTab.Obj_Refrescar(tabla,modelo, columnas, comando, basedatos,pintar,checkbox);
		modelo.setRowCount(0);
    }
	
	@SuppressWarnings("rawtypes")
	public Class[] basemovimientos (){
		Class[] types = new Class[columnas];
		for(int i = 0; i<columnas; i++){types[i]= java.lang.Object.class;}
		 return types;
	}
	public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"Descripcion","Cantidad","P.Unitario","Importe"}){
		 @SuppressWarnings("rawtypes")
			Class[] types = basemovimientos();
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
			public boolean isCellEditable(int fila, int columna){if(columna>0&&columna<3){return true;}else{ return false;}}
	};
	JTable tabla = new JTable(modelo);
	public JScrollPane scroll_tabla = new JScrollPane(tabla);
	
	JTextField txtFolio       = new Componentes().text(new JCTextField()  ,"Folio"                     ,30   ,"String");
	JTextField txtDescripcion = new Componentes().text(new JCTextField()  ,"Descripcion Del Producto"  ,350  ,"String");
	JTextField txtFolio_prv   = new Componentes().text(new JCTextField()  ,"Folio Prv"                 ,30   ,"String");
	JTextField txtProveedor   = new Componentes().text(new JCTextField()  ,"Proveedor"                 ,250  ,"String");
	JTextField txtFoliosolicit= new Componentes().text(new JCTextField()  ,"Folio Solicita"            ,30   ,"String");
	JTextField txtSolicitante = new Componentes().text(new JCTextField()  ,"Solicitante"               ,300  ,"String");
	JTextField txtFecha       = new Componentes().text(new JCTextField()  ,"Fecha"                     ,60   ,"String");
	JTextField txtTotal       = new Componentes().text(new JCTextField()  ,"Total"                     ,30   ,"String");
	JTextField txtFolioservici= new Componentes().text(new JCTextField()  ,"Folio Servicio"            ,30   ,"String");
	JTextField txtDetalleServi= new Componentes().text(new JCTextField()  ,"Detalle Servicio"          ,350  ,"String");
	JTextField txtFolioCheque = new Componentes().text(new JCTextField()  ,"Folio Cheque"              ,20   ,"String");
	JTextField txtPlazo       = new Componentes().text(new JCTextField()  ,"Plazo"                     ,2    ,"Int"   );
	
    JTextArea txaUso       = new Componentes().textArea(new JCTextArea(), "Uso De La Mercancia", 300);
	JScrollPane Uso        = new JScrollPane(txaUso);

	JCButton btnBuscar     = new JCButton("Buscar"       ,"Filter-List-icon16.png"            ,"Azul"); 
	JCButton btnNuevo      = new JCButton("Nuevo"        ,"Nuevo.png"                         ,"Azul");
	JCButton btnGuardar    = new JCButton("Guardar"      ,"Guardar.png"                       ,"Azul");
	JCButton btnDeshacer   = new JCButton("Deshacer"     ,"deshacer16.png"                    ,"Azul");
	JCButton btnSolicitante= new JCButton(""             ,"Usuario.png"                       ,"Azul");	
	JCButton btnQuitarfila = new JCButton("Eliminar"     ,"boton-rojo-menos-icono-5393-16.png","Azul");
	JCButton btnAgregar    = new JCButton("Agregar"      ,"double-arrow-icone-3883-16.png"    ,"Azul");
	JCButton btnImprimir   = new JCButton("Imprimir"     ,"imprimir-16.png"                   ,"Azul");
	JCButton btnModificar  = new JCButton("Modificar"    ,"Modify.png"                        ,"Azul");
	JCButton btnServicio   = new JCButton(""             ,"los-parametros-de-las-herramientas-de-icono-8319-16.png"    ,"Azul");
	
	String status[] = new Obj_Orden_De_Gasto().Combo_Cuentas();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmb_status = new JComboBox(status);
	
	String conceptos[] = {"GASTO","COMPRA"};
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmb_concepto = new JComboBox(conceptos);
	
	String tipo[] = {"NC","C"};
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmb_tipo = new JComboBox(tipo);
	
	String forma_pago[] = {"EFECTIVO","CHEQUE","TRANSFERENCIA"};
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmb_forma_pago = new JComboBox(forma_pago);
	
	String establecimientoScoi[] = new Obj_Establecimiento().Combo_Establecimiento();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbEstablecimiento = new JComboBox(establecimientoScoi);

	JRadioButton rbProveedorCont = new JRadioButton("Proveedor NC");
	JRadioButton rbProveedor     = new JRadioButton("Proveedor"   );
	JRadioButton rbColaborador   = new JRadioButton("Colaborador" );
	ButtonGroup  grupo           = new ButtonGroup (              );
	
	Border blackline, etched, raisedbevel, loweredbevel, empty;

	String[][] tablaprecargadaordenes;
    Object[] vector = new Object[7];

    String guardar_actualizar="";
    String Servicio="";
   public  Cat_Solicitud_De_Orden_De_Gasto(){
	    this.cont.add(panel);
		this.setSize(870,580);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("Solicitud De Orden De Gasto");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/articulo-icono-9036-48.png"));
		this.blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
		this.panel.setBorder(BorderFactory.createTitledBorder(blackline,"Solicitud De Orden De Gasto"));
   	
   		this.grupo.add(rbProveedor);
   		this.grupo.add(rbProveedorCont);
   		this.grupo.add(rbColaborador);
   		this.rbProveedorCont.setSelected(true);
   		
   	    this.menu_toolbar.add(btnNuevo    );
		this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.addSeparator(   );
   	    this.menu_toolbar.add(btnModificar);
		this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.add(btnDeshacer );
		this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.add(btnBuscar   );
		this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.add(btnGuardar  );
		this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.add(btnImprimir );
		this.menu_toolbar.setFloatable(false);
   	 
		int x=20, y=20,width=122,height=20, sep=130;
		this.panel.add(menu_toolbar).setBounds                        (x         ,y      ,470     ,height );
		this.panel.add(new JLabel("Tipo:")).setBounds                 (x+=495    ,y      ,width   ,height );
		this.panel.add(cmb_tipo).setBounds                            (x+=30     ,y      ,50      ,height );
		this.panel.add(new JLabel("Forma Pago:")).setBounds           (x+=60     ,y      ,width   ,height );
		this.panel.add(cmb_forma_pago).setBounds                      (x+=65     ,y      ,89      ,height );
		this.panel.add(new JLabel("Plazo:")).setBounds                (x+=100    ,y      ,width   ,height );
		this.panel.add(txtPlazo).setBounds                            (x+=32     ,y      ,40      ,height );
		
		this.panel.add(txtFolio).setBounds                            (x=20      ,y+=30  ,width   ,height );
		this.panel.add(new JLabel("Estatus:")).setBounds              (x+=sep    ,y      ,width   ,height );
		this.panel.add(cmb_status).setBounds                          (x+=45     ,y      ,width   ,height );
		this.panel.add(new JLabel("Concepto:")).setBounds             (x+=145    ,y      ,width   ,height );
		this.panel.add(cmb_concepto).setBounds                        (x+=55     ,y      ,77      ,height );
		this.panel.add(new JLabel("Sucursal Solicitante:")).setBounds (x+=100    ,y      ,width   ,height );
		this.panel.add(cmbEstablecimiento).setBounds                  (x+=100    ,y      ,250     ,height );
		
		this.panel.add(txtFolio_prv).setBounds                        (x=20      ,y+=25  ,50      ,height );
		this.panel.add(txtProveedor).setBounds                        (x+=50     ,y      ,468     ,height );
		this.panel.add(btnSolicitante).setBounds                      (x+=468    ,y      ,40      ,height );
		this.panel.add(rbColaborador).setBounds                       (x+=50     ,y      ,90      ,height );	
		this.panel.add(rbProveedor).setBounds                         (x+=90     ,y      ,80      ,height );		
		this.panel.add(rbProveedorCont).setBounds                     (x+=80     ,y      ,100     ,height );  
		
		this.panel.add(txtFolioservici).setBounds                     (x=20      ,y+=25  ,50      ,height );
		this.panel.add(txtDetalleServi).setBounds                     (x+=50     ,y      ,735     ,height );
		this.panel.add(btnServicio).setBounds                         (x+=735    ,y      ,40      ,height );
		
		this.panel.add(new JLabel("Descripcion Del Gasto:")).setBounds(x=20      ,y+=20  ,width   ,height );
		this.panel.add(Uso).setBounds                                 (x         ,y+=15  ,825     ,90     );
		this.panel.add(txtDescripcion).setBounds                      (x         ,y+=95  ,605     ,height );
		this.panel.add(btnAgregar).setBounds                          (x+=609    ,y      ,102     ,height ); 
		this.panel.add(btnQuitarfila).setBounds                       (x+115     ,y      ,99      ,height ); 
		
		this.panel.add(scroll_tabla).setBounds                        (x=20      ,y+=20  ,825     ,270    );
		this.panel.add(txtFoliosolicit).setBounds                     (x         ,y+=270 ,60      ,height );		
		this.panel.add(txtSolicitante).setBounds                      (x+60      ,y      ,320     ,height );
		this.panel.add(txtFolioCheque).setBounds                      (x+=459    ,y      ,93      ,height );

		this.panel.add(new JLabel("Fecha Actual:")).setBounds         (x+=108    ,y      ,width   ,height );
		this.panel.add(txtFecha).setBounds                            (x+=70     ,y      ,77      ,height );
	
		this.panel.add(txtTotal).setBounds                            (x+=90     ,y      ,98      ,height );

		panel_booleano(false);
		init_tabla();
		
		btnNuevo.addActionListener(nuevo);
		btnDeshacer.addActionListener(deshacer);
		btnGuardar.addActionListener(guardar);
		btnModificar.addActionListener(opEditar);
		btnServicio.addActionListener(opFiltroServicios);
		
		cmb_concepto.addActionListener      (opConcepto                );
		cmbEstablecimiento.addActionListener(opSeleccionEstablecimiento);
		cmb_tipo.addActionListener          (opTipo                    );
		
		btnSolicitante.addActionListener    (opFiltroBuscarSolicitante );
		btnAgregar.addActionListener        (opAgregarProducto         );
		btnQuitarfila.addActionListener     (opQuitarfila              );
		btnBuscar.addActionListener         (opFiltroBuscar_orden_pago );
		btnImprimir.addActionListener       (opImprimir_Reporte        );   
        txtDescripcion.addKeyListener       (opAgregarConEnter         );
		tabla.addKeyListener                (opKeyTable  );
		
		btnDeshacer.setToolTipText("<ESC> Tecla Directa");
		btnGuardar.setToolTipText("<CTRL+G> Tecla Directa");

		try { txtFecha.setText(new BuscarSQL().fecha(0).toString());} catch (SQLException e1) {e1.printStackTrace();}
		txtFecha.setEditable(false);
		txtFoliosolicit.setEditable(false);
		txtSolicitante.setEditable(false);
		txtFolio_prv.setEditable(false);
		txtTotal.setEditable(false);
		txtSolicitante.setText(usuario.getNombre_completo());
		txtFoliosolicit.setText(usuario.getFolio()+"");
		
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
          getRootPane().getActionMap().put("escape", new AbstractAction(){public void actionPerformed(ActionEvent e){  btnDeshacer.doClick(); } });

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_G,Event.CTRL_MASK),"guardar");
          getRootPane().getActionMap().put("guardar", new AbstractAction(){public void actionPerformed(ActionEvent e){ btnGuardar.doClick(); } });

	    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0), "guardar");
	      getRootPane().getActionMap().put("guardar", new AbstractAction(){public void actionPerformed(ActionEvent e){ btnGuardar.doClick(); } });
	             
	    this.addWindowListener(new WindowAdapter() {  public void windowOpened( WindowEvent e ){ txtFolio.requestFocus();  }  });                
    }
	
    public void panel_booleano(boolean boleano){
		cmb_status.setEnabled(boleano);
		cmbEstablecimiento.setEnabled(boleano);
		cmb_forma_pago.setEnabled(boleano);
	    txtDescripcion.setEditable(boleano); 
	    cmb_concepto.setEnabled(boleano);
		cmb_tipo.setEnabled(boleano);
		txaUso.setEditable(boleano);
		btnModificar.setEnabled(false);
		txtFolio.setEditable(false);
		txtFolioCheque.setEditable(boleano);
		txtProveedor.setEditable(boleano);
		txtFolioservici.setEditable(boleano);
		txtDetalleServi.setEditable(boleano);
		btnServicio.setEnabled(boleano);
		btnSolicitante.setEnabled(boleano);
		btnGuardar.setEnabled(boleano);
		btnAgregar.setEnabled(boleano);
		btnQuitarfila.setEnabled(boleano);
		rbProveedorCont.setEnabled(boleano);
		rbProveedor.setEnabled(boleano);
		rbColaborador.setEnabled(boleano);
		txtPlazo.setEditable(boleano);
    }  
   
    public void panel_limpiar(){
		btnBuscar.setEnabled(true);
		cmb_status.setSelectedIndex(0);
		cmbEstablecimiento.setSelectedIndex(0);
		cmb_tipo.setSelectedIndex(0);
		cmb_forma_pago.setSelectedIndex(0);
		txtFolio.setText("");
		txtDetalleServi.setText("");
	    txtDescripcion.setText(""); 
	    txtProveedor.setText("");
	    txtFolioCheque.setText("");
	    txtFolio_prv.setText("");
		txtProveedor.setText("");
		txtFolioservici.setText("");
		txaUso.setText("");
		txtTotal.setText("");
		txtPlazo.setText("");
		rbProveedorCont.setSelected(true);
		modelo.setRowCount(0);
		txaUso.setBackground(new Color(Integer.parseInt("EBEBEB",16)));
		Servicio="";
    }
	
	KeyListener opAgregarConEnter = new KeyListener() {
	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_ENTER){
			btnAgregar.doClick();
		}
	}
	public void keyPressed(KeyEvent e) {}
    };
 
    ActionListener opConcepto = new ActionListener(){
 		public void actionPerformed(ActionEvent e){
			cmbEstablecimiento.setEnabled(true);
			cmbEstablecimiento.requestFocus();
			cmbEstablecimiento.showPopup();
		  if(cmb_concepto.getSelectedItem().toString().equals("COMPRA")&& cmb_tipo.getSelectedItem().toString().equals("C")) {
			JOptionPane.showMessageDialog(null, "La Orden de Gasto Para Compra Tiene Que Ser Tipo NC","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
			cmb_tipo.setSelectedItem("NC");
			return;
		  }
 		}
 	};	
 	
 	 
    ActionListener opTipo = new ActionListener(){
 		public void actionPerformed(ActionEvent e){

		  if(cmb_concepto.getSelectedItem().toString().equals("COMPRA")&& cmb_tipo.getSelectedItem().toString().equals("C")) {
			JOptionPane.showMessageDialog(null, "La Orden de Gasto Para Compra Tiene Que Ser Tipo NC","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
			cmb_tipo.setSelectedItem("NC");
			return;
		  }
 		}
 	};	
 	
    ActionListener opFiltroServicios = new ActionListener(){
 		public void actionPerformed(ActionEvent e){
 			new Cat_Filtro_Buscar_Servicio().setVisible(true);
 		}
 	};	
 	
    ActionListener opEditar = new ActionListener(){
 		public void actionPerformed(ActionEvent e){
 			guardar_actualizar="A";
			txtSolicitante.setText(usuario.getNombre_completo());
			txtFoliosolicit.setText(usuario.getFolio()+"");
			btnGuardar.setEnabled(true);
			btnBuscar.setEnabled(false);
			btnNuevo.setEnabled(false);
			btnImprimir.setEnabled(false);
			btnAgregar.setEnabled(true);
			btnQuitarfila.setEnabled(true);
			txaUso.setEditable(true);
			cmb_concepto.setEnabled(true);
			cmb_tipo.setEnabled(true);
			cmb_forma_pago.setEnabled(true);
			cmbEstablecimiento.setEnabled(true);
			cmbEstablecimiento.requestFocus();
			cmbEstablecimiento.showPopup();
    		tabla.setEnabled(true );
    		txtPlazo.setEditable(true);
    		
 		}
 	};
    
    ActionListener opFiltroBuscar_orden_pago = new ActionListener(){
 		public void actionPerformed(ActionEvent e){
 			new Cat_Filtro_Buscar_Orden_De_Gasto().setVisible(true);
 		}
 	};
 	
 	 ActionListener opImprimir_Reporte = new ActionListener(){
  		public void actionPerformed(ActionEvent e){
  			if(cmb_status.getSelectedItem().toString().trim().equals("EN VALIDACION")){
				JOptionPane.showMessageDialog(null, "Las Ordenes De Gastos Que Se Originan De Un Servicio Es Requerido Sea Validada Para Poder Imprimir","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
				return;
			}else {
	  			String basedatos="2.26";
	  			String vista_previa_reporte="no";
	  			int vista_previa_de_ventana=0;
	  			String comando="orden_de_gasto_reporte '"+txtFolio.getText().toString()+"'";
	  			String reporte = "Obj_Reporte_De_Orden_De_Gasto.jrxml";
	  			 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
			}
  		}
  	};
  	
    ActionListener opAgregarProducto = new ActionListener(){
		public void actionPerformed(ActionEvent e){
				if(txtDescripcion.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Es Requerido Teclee La Descripcion Del Producto Para Poder Agregar","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
					txtDescripcion.requestFocus();
					return;
				}else {
					Object[] Vector_Producto = new Object[4];
					Vector_Producto[0]=txtDescripcion.getText().toUpperCase().trim();		
					Vector_Producto[1]="0";		
					Vector_Producto[2]="0";		
					Vector_Producto[3]="0";		
					modelo.addRow(Vector_Producto);
					txtDescripcion.setText("");
					fila = modelo.getRowCount()-1;
					columna = 1;
					RecorridoFoco("click");
				   return;	
				}
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
						txtTotal.setText("");
					}
				}
			}
		}
	};
	
	ActionListener nuevo = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			guardar_actualizar="N";
			panel_limpiar();
			String folio_inventario="";
			try {
				folio_inventario= new BuscarSQL().folio_siguiente(84+"");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			txtSolicitante.setText(usuario.getNombre_completo());
			txtFoliosolicit.setText(usuario.getFolio()+"");
			txtFolio.setText(folio_inventario);
			txtFolio.setEditable(false);
			btnGuardar.setEnabled(true);
			btnBuscar.setEnabled(false);
			btnNuevo.setEnabled(false);
			btnImprimir.setEnabled(false);
			cmb_tipo.setEnabled(true);
			cmbEstablecimiento.setEnabled(true);
			cmb_concepto.setEnabled(true);
			txtPlazo.setEditable(true);
			cmb_concepto.requestFocus();
			cmb_concepto.showPopup();
			cmb_forma_pago.setEnabled(true);
    		tabla.setEnabled(true );
		}
	};
	
    ActionListener opSeleccionEstablecimiento = new ActionListener(){
 	  public void actionPerformed(ActionEvent e){
				   if(cmbEstablecimiento.getSelectedIndex()!=0) {
					   btnSolicitante.setEnabled(true);
					   rbProveedorCont.setEnabled(true);
					   rbProveedor.setEnabled(true);
					   rbColaborador.setEnabled(true);
				   }
				return;
			}
    };
		
   ActionListener opFiltroBuscarSolicitante = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			new Cat_Filtro_Buscar_Proveedor().setVisible(true);
		}
	};	
	
	ActionListener deshacer = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			guardar_actualizar="";
			if(guardar_actualizar.equals("N")){
				if(JOptionPane.showConfirmDialog(null, "Hay Datos Capturados y No Han Sido Guardados, ¿Desea Borrar Todo?", "Aviso", JOptionPane.INFORMATION_MESSAGE,0, new ImageIcon("Imagen/usuario-icono-noes_usuario9131-64.png") )== 0){
					panel_limpiar();
					panel_booleano(false);
					btnModificar.setEnabled(false);
					txtFolio.requestFocus();
					btnNuevo.setEnabled(true);
					return;
			     }else{
              				return;
			}
		}else{
			btnModificar.setEnabled(false);
			panel_limpiar();
			panel_booleano(false);;
			txtFolio.requestFocus();
			btnNuevo.setEnabled(true);
			return;
		}
		}
	};
	
	//TODO GUARDAR
	ActionListener guardar = new ActionListener(){
	public void actionPerformed(ActionEvent e){
			 String[][] tabla_guardado = ObjTab.tabla_guardar(tabla);
			 
			 if(cmb_concepto.getSelectedItem().toString().equals("COMPRA")&& cmb_tipo.getSelectedItem().toString().equals("C")) {
				JOptionPane.showMessageDialog(null, "La Orden de Gasto Para Compra Tiene Que Ser Tipo NC","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
				cmb_tipo.setSelectedItem("NC");
				return;
			 }
				
			 if(tabla.isEditing()){	tabla.getCellEditor().stopCellEditing();}
				calculo();
			 if(txtTotal.getText().equals("")||Float.valueOf(txtTotal.getText())==0){
				JOptionPane.showMessageDialog(null, "Es Requerido Alimente Productos Con Cantidad y Precio","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
				return;
			 }else{		 
				   String productos="Descripcion / Cantidad / Importe\n";
				   for(int i=0;i<tabla.getRowCount();i++) {
					   productos=productos+tabla.getValueAt(i, 0)+"  / "+tabla.getValueAt(i, 1)+"  /$"+tabla.getValueAt(i, 3)+" \n";
				   }

				    if(gasto.validacion_existe(txtFolio.getText().toString())||guardar_actualizar.equals('N')){
				    	
					   String folio_servicio= (txtFolioservici.getText().toString().trim().equals(""))?"0":txtFolioservici.getText().toString().trim() ;
					   gasto.setFolio(Integer.valueOf(txtFolio.getText().toString()));
	                   gasto.setFolio_usuario_solicito(Integer.valueOf(txtFoliosolicit.getText()));
	                   gasto.setTotal_gasto(Float.valueOf(txtTotal.getText()));
	                   gasto.setEstablecimiento_solicito(cmbEstablecimiento.getSelectedItem().toString());
	                   gasto.setCod_prv(txtFolio_prv.getText().toString());
	                   gasto.setTipo_proveedor(rbProveedor.isSelected()?"P":rbProveedorCont.isSelected()?"C":"E"); 
	                   gasto.setDescripcion_gasto(txaUso.getText().trim());
	                   gasto.setGuardar_actualizar(guardar_actualizar);
	                   gasto.setTabla_obj(tabla_guardado);
	                   gasto.setFolio_servicio(Integer.valueOf(folio_servicio));
	                   gasto.setConcepto_gasto(cmb_concepto.getSelectedItem().toString());
	                   gasto.setTipo(cmb_tipo.getSelectedItem().toString());   
	                   gasto.setForma_de_pago(cmb_forma_pago.getSelectedItem().toString().trim());
	                   gasto.setPlazo(txtPlazo.getText().toString());
	                   gasto.setCheque(txtFolioCheque.getText().toString().trim());
					if(gasto.GuardarActualizar().getFolio()>0){
		               Obj_Correos correos = new Obj_Correos().buscar_correos(84, "");
					   String Mensaje= "El usuario:"+txtSolicitante.getText().toString()+"\nSolicita los siguientes productos\n"+productos+"Con un valor total de:$"+txtTotal.getText().toString()+"\n "
						                  +"\nPara El Establecimiento:"+cmbEstablecimiento.getSelectedItem().toString().trim()+"\nBeneficiario:"+txtProveedor.getText()+"\nMotivo del(a) Gasto/Compra:"+txaUso.getText();
						new EmailSenderService().enviarcorreo(correos.getCorreos(),correos.getCantidad_de_correos(),Mensaje,"A.I. Solicitud De "+cmb_concepto.getSelectedItem().toString()+" Folio:§"+gasto.getFolio()+" Por Un Total De:"+txtTotal.getText().toString(),"Gastos");
						guardar_actualizar="";
						
						if(gasto.getFolio_servicio()==0) {
						  if(!cmb_status.getSelectedItem().toString().equals("EN VALIDACION")) {	
							btnImprimir.setEnabled(true);
							btnImprimir.doClick();
							btnDeshacer.doClick();
						  }	
						}
						JOptionPane.showMessageDialog(null, "Se Guardo Correctamente", "Aviso", JOptionPane.OK_OPTION,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
						btnDeshacer.doClick();
				        return;
					}else{
						JOptionPane.showMessageDialog(null,"Error Al Guardar Avise al Administrador del Sistema","Aviso",JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-icono-eliminar5252-64.png"));
						return;
					}
				   }else {
						JOptionPane.showMessageDialog(null, "El Folio Ya Fue Usado a Continuacion Se Actualizará El Folio y Podra Dar De Nuevo A Guardar", "Aviso", JOptionPane.OK_OPTION,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
						String folio_inventario="";
						try {
							folio_inventario= new BuscarSQL().folio_siguiente(84+"");
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						txtFolio.setText(folio_inventario);
						return;
				   }
			 }
	  }			
    };
    
	KeyListener opKeyTable = new KeyListener() {
		public void keyTyped(KeyEvent e) {		}
		@SuppressWarnings({ "static-access", "deprecation" })
		public void keyReleased(KeyEvent e) {
			
			if(e.getKeyCode()==e.VK_ESCAPE){
				tabla.lostFocus(null, null);
				txtDescripcion.requestFocus();
				tabla.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
				tabla.getSelectionModel().clearSelection();
			}
			if(e.getKeyCode()==e.VK_ENTER){				
				RecorridoFoco("darecha");
			}
			if(e.getKeyCode()==e.VK_DOWN){
				RecorridoFoco("bajar");
			}
			if(e.getKeyCode()==e.VK_UP){
				RecorridoFoco("subir");
			}
		}
		public void keyPressed(KeyEvent e) {	}
	};
	
	int indiceSeleccionado=0;
	int[] columnasEditables = columnasEdit(tabla);
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int[] columnasEdit(final JTable tbl){
		Vector col = new Vector();
		for(int i=0; i<tbl.getColumnCount(); i++){
			if(tbl.isCellEditable(0, i)){
				col.addElement(i);
			}
		}
		
		int[] columnasE = new int[col.size()];
		
		for(int i=0; i<col.size(); i++){
			columnasE[i]=(int) col.get(i);
		}
		return columnasE;
	}
	
	public void CeldaSiguiente(final JTable tbl){
		if(columna==columnasEditables[columnasEditables.length-1]){
			indiceSeleccionado=0;
			columna=columnasEditables[indiceSeleccionado];
			fila = fila<tbl.getRowCount()-1 ? fila+1: fila ;
		}else{
			indiceSeleccionado++;
			columna=columnasEditables[indiceSeleccionado];
		}
	}
	
	@SuppressWarnings("deprecation")
	public void RecorridoFoco(String mover){
		if(ObjTab.validacelda(tabla,"decimal", fila,columna)){
			calculo();
			
			int cantidadDeFilas = tabla.getRowCount();
			switch(mover){
				case "bajar": fila= (fila==cantidadDeFilas-1)?cantidadDeFilas-1:fila+1; break;
				case "subir": fila= (fila==0)?0:fila-1; break;
				case "izquierda": columna--; break;
				case "darecha": CeldaSiguiente(tabla); break;
				case "inicio": fila=0; break;
				case "fin": fila=tabla.getRowCount()-1; break;
			}
			
			String sacarFocoDeTabla = "no";
			if(fila == cantidadDeFilas-1){
				sacarFocoDeTabla="si";
			}
			else{
				sacarFocoDeTabla = "no";
			}
			tabla.getSelectionModel().setSelectionInterval(fila, fila);
			tabla.editCellAt(fila, columna);
			  Component accion=tabla.getEditorComponent();
			final JTextComponent jtc = (JTextComponent)accion;
			  jtc.requestFocus();
			  jtc.selectAll();	
			
			if(sacarFocoDeTabla.equals("si")){
				tabla.lostFocus(null, null);
				tabla.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
				tabla.getSelectionModel().clearSelection();
			}
		}

	};    
	
	public void calculo() {
		float importe=0;
		for(int i=0;i<tabla.getRowCount();i++) {
			tabla.setValueAt(Float.valueOf(tabla.getValueAt(i, 1)+"") * Float.valueOf(tabla.getValueAt(i, 2)+""), i, 3);
			importe=importe+Float.valueOf(tabla.getValueAt(i, 3)+"");
		}
		txtTotal.setText(importe+"");
	};
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
///////TODO inicia filtro_Buscar PROVEEDOR////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	public class Cat_Filtro_Buscar_Proveedor extends JDialog{
		Container contfb = getContentPane();
		JLayeredPane panelfb = new JLayeredPane();
		Connexion con = new Connexion();
		Obj_tabla ObjTab =new Obj_tabla();
		int columnasb = 2,checkbox=-1;
		public void init_tablafp(){
	    	this.tablab.getColumnModel().getColumn( 0).setMinWidth(55);
	    	this.tablab.getColumnModel().getColumn( 1).setMinWidth(350);
			 String ref =rbProveedor.isSelected()?"Proveedor":rbProveedorCont.isSelected()?"Proveedor NC":"Colaborador";
			 String comandob=" " ;
			switch(ref){
			    case "Proveedor NC": comandob = "select folio_proveedor,nombre+' '+ap_paterno+' '+ap_materno as nombre from tb_proveedores where status=1  order by nombre"; break;
		    	case "Proveedor": 	 comandob = " SELECT cod_prv,rtrim(ltrim(upper(razon_social))) from bmsizagar.dbo.proveedores order by razon_social "; break;
		    	case "Colaborador":  comandob = " select folio,dbo.nombre_empleado(folio) as nombre from tb_empleado with(nolock) order by nombre "; break;
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
		
		JTextField txtBuscarb         = new Componentes().textfiltro(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<", 500, "String" ,tablab ,columnasb );
		
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
		  tbl.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e){
			 if(e.getClickCount() == 2){agregarf(); }
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
					agregarf();	
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {}
			@Override
			public void keyTyped(KeyEvent e)    {}
		});
      }
	    
		private void agregarf() {
		   rbProveedor.setEnabled(false);
		   rbProveedorCont.setEnabled(false);
		   rbColaborador.setEnabled(false);
		   int fila = tablab.getSelectedRow();
		   btnServicio.setEnabled(true);
		   txtFolio_prv.setText (tablab.getValueAt(fila,0)+"");
		   txtProveedor.setText (tablab.getValueAt(fila,1)+"");
		   txtDescripcion.setEditable(true);
		   btnAgregar.setEnabled(true);
		   btnQuitarfila.setEnabled(true);
		   txaUso.setEditable(true);
		   txaUso.requestFocus();
		   txaUso.setBackground(new Color(Integer.parseInt("FFFFFF",16)));
		   dispose();
	    }
		
        private KeyListener opFiltropuestos = new KeyListener(){
			public void keyReleased(KeyEvent arg0) {
				ObjTab.Obj_Filtro(tablab, txtBuscarb.getText().toUpperCase(), columnasb,txtBuscarb);
			}
			public void keyTyped(KeyEvent arg0) {}
			public void keyPressed(KeyEvent arg0) {}		
		};
	}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
//////TODO inicia filtro_Buscar ORDEN DE GASTO////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		public class Cat_Filtro_Buscar_Orden_De_Gasto extends JDialog{
			Container contfb = getContentPane();
			JLayeredPane panelfb = new JLayeredPane();
			Connexion con = new Connexion();
			Obj_tabla ObjTab =new Obj_tabla();
			int columnasb = 11,checkbox=-1;
			public void init_tablafp(){
		    	this.tablab.getColumnModel().getColumn( 0).setMinWidth(50);
		    	this.tablab.getColumnModel().getColumn( 0).setMaxWidth(50);
		    	this.tablab.getColumnModel().getColumn( 1).setMinWidth(350);
		    	this.tablab.getColumnModel().getColumn( 2).setMinWidth(140);
		    	this.tablab.getColumnModel().getColumn( 3).setMinWidth(200);
		    	this.tablab.getColumnModel().getColumn( 4).setMinWidth(160);
		    	this.tablab.getColumnModel().getColumn( 5).setMinWidth(100);
		    	this.tablab.getColumnModel().getColumn( 6).setMinWidth(230);
		    	this.tablab.getColumnModel().getColumn( 7).setMinWidth(130);
		    	this.tablab.getColumnModel().getColumn( 8).setMinWidth(130);
		    	this.tablab.getColumnModel().getColumn( 9).setMinWidth(130);
		    	
				String comandob = "orden_de_gasto_filtro";
		    	String basedatos="26",pintar="si";
				ObjTab.Obj_Refrescar(tablab,modelob, columnasb, comandob, basedatos,pintar,checkbox);
		    }
			
			@SuppressWarnings("rawtypes")
			public Class[] base (){
				Class[] types = new Class[columnasb];
				for(int i = 0; i<columnasb; i++){types[i]= java.lang.Object.class;}
				 return types;
			}
			
			public DefaultTableModel modelob = new DefaultTableModel(null, new String[]{"Folio","Proveedor","Tipo Provedor","Descripcion Gasto","Establecimiento","Importe Total","Usuario Solicita", "Fecha","Estatus","Fecha Autorizacion","Usuario Autorizo"}){
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
			     
			JTextField txtBuscarb  = new Componentes().textfiltro(new JCTextField() ,">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<" ,500 ,"String" ,tablab ,columnasb );
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Cat_Filtro_Buscar_Orden_De_Gasto(){
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
				this.txtBuscarb.addKeyListener  (opFiltropuestos );
				contfb.add(panelfb);
			}
			
		    private void agregar(final JTable tbl) {
				  tbl.addMouseListener(new MouseListener() {
					public void mouseReleased(MouseEvent e){
					 if(e.getClickCount() == 2){agregarf(); }
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
							agregarf();	
						}
					}
					@Override
					public void keyReleased(KeyEvent e) {}
					@Override
					public void keyTyped(KeyEvent e)    {}
				});
		      }
			    
				private void agregarf() {
					modelo.setRowCount(0);
	        		int fila = tablab.getSelectedRow();
	        		String[][] tablacompleta=gasto.consulta_orden_de_gasto(Integer.valueOf(tablab.getValueAt(fila,0)+""));
	        	    Object[]   vectortabla = new Object[3];
	        		for(int i=0;i<tablacompleta.length;i++){
	        				for(int j=0;j<3;j++){
	        				  vectortabla[j] = tablacompleta[i][j].toString();
	        				}
	        				modelo.addRow(vectortabla);
	        			}
	        			 
	        		txtFolio.setText(tablacompleta[0][3].toString());
	        		txaUso.setText(tablacompleta[0][4].toString());
	        		cmbEstablecimiento.setSelectedItem(tablacompleta[0][6].toString());
	        		txtFolio_prv.setText (tablacompleta[0][7].toString());
	        		txtProveedor.setText (tablacompleta[0][8].toString());
	         		txtFoliosolicit.setText (tablacompleta[0][11].toString());
	        		txtSolicitante.setText (tablacompleta[0][14].toString());
	        		cmb_status.setSelectedItem(tablacompleta[0][15].toString());
	        		
	        		if(tablacompleta[0][9].toString().equals("PROVEEDOR")) {
	        			rbProveedor.setSelected(true); 
	        		}else {        
	        			 if(tablacompleta[0][9].toString().equals("COLABORADOR")) {
	        			   rbColaborador.setSelected(true);
	        			 }else {
	        			   rbProveedorCont.setSelected(true); };
	        		}	 
	        		
	        		txtFolioservici.setText(tablacompleta[0][18].toString());
	        		txtDetalleServi.setText(tablacompleta[0][19].toString());
	        		cmb_tipo.setSelectedItem(tablacompleta[0][20].toString());
	        		cmb_forma_pago.setSelectedItem(tablacompleta[0][21].toString());
	        		txtFolioCheque.setText(tablacompleta[0][22].toString());
	        		txtPlazo.setText(tablacompleta[0][23].toString());
	        		
	        		tabla.setEnabled(false );
	        		panel_booleano(false);
	        		
	        		if(tablacompleta[0][15].toString().equals("PENDIENTE")||tablacompleta[0][15].toString().equals("EN VALIDACION")){
	        			btnModificar.setEnabled(true);
	        		}else{
	        		btnModificar.setEnabled(false);}
					btnImprimir.setEnabled(true);
					txtFolio.setEditable(false);
					calculo();
					dispose();
			    }
			
	        private KeyListener opFiltropuestos = new KeyListener(){
				public void keyReleased(KeyEvent arg0) {
					ObjTab.Obj_Filtro(tablab, txtBuscarb.getText().toUpperCase(), columnasb,txtBuscarb);
				}
				public void keyTyped(KeyEvent arg0) {}
				public void keyPressed(KeyEvent arg0) {}		
			};
		    }

	///////TODO inicia filtro_Buscar SERVICIO////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
		public class Cat_Filtro_Buscar_Servicio extends JDialog{
			Container contfb = getContentPane();
			JLayeredPane panelfb = new JLayeredPane();
			Connexion con = new Connexion();
			Obj_tabla ObjTab =new Obj_tabla();
			int columnasc = 4,checkbox=-1;
			public void init_tablafp(){
		    	this.tablac.getColumnModel().getColumn( 0).setMinWidth(55);
		    	this.tablac.getColumnModel().getColumn( 1).setMinWidth(450);
		    	this.tablac.getColumnModel().getColumn( 2).setMinWidth(250);
				String comandob="orden_de_gasto_filtro_seleccion_de_servicio '"+cmbEstablecimiento.getSelectedItem().toString().trim()+"'" ;
				String basedatos="98",pintar="si";
				ObjTab.Obj_Refrescar(tablac,modeloc, columnasc, comandob, basedatos,pintar,checkbox);
		    }
			
			@SuppressWarnings("rawtypes")
			public Class[] base (){
				Class[] types = new Class[columnasc];
				for(int i = 0; i<columnasc; i++){types[i]= java.lang.Object.class;}
				 return types;
			}
			
			public DefaultTableModel modeloc = new DefaultTableModel(null, new String[]{"Folio","Servicio","Solicito","Fecha"}){
				 @SuppressWarnings("rawtypes")
					Class[] types = base();
					@SuppressWarnings({ "rawtypes", "unchecked" })
					public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
					public boolean isCellEditable(int fila, int columna){return false;}
			};
			
			JTable tablac = new JTable(modeloc);
			public JScrollPane scroll_tablab = new JScrollPane(tablac);
		     @SuppressWarnings({ "rawtypes" })
		    private TableRowSorter trsfiltro;
			
			JTextField txtBuscarb         = new Componentes().textfiltro(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<", 500, "String" ,tablac ,columnasc );
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Cat_Filtro_Buscar_Servicio(){
				this.setSize(800,450);
				this.setResizable(false);
				this.setLocationRelativeTo(null);
				this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				this.setModal(true);
				this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Filter-List-icon32.png"));
				this.panelfb.setBorder(BorderFactory.createTitledBorder("Selecione Un Registro Con Doble Click"));
				this.setTitle("Filtro De Solicitante");
				trsfiltro = new TableRowSorter(modeloc); 
				tablac.setRowSorter(trsfiltro);
				this.panelfb.add(txtBuscarb).setBounds      (10 ,20 ,750 , 20 );
				this.panelfb.add(scroll_tablab).setBounds   (10 ,40 ,750 ,370 );
				this.init_tablafp();
				this.agregar(tablac);
				this.txtBuscarb.addKeyListener  (opFiltropuestos );
				contfb.add(panelfb);
			}
			
		    private void agregar(final JTable tbl) {
			  tbl.addMouseListener(new MouseListener() {
				public void mouseReleased(MouseEvent e){
				 if(e.getClickCount() == 2){agregarf(); }
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
						agregarf();	
					}
				}
				@Override
				public void keyReleased(KeyEvent e) {}
				@Override
				public void keyTyped(KeyEvent e)    {}
			});
	      }
		    
			private void agregarf() {
			   rbProveedor.setEnabled(false);
			   rbProveedorCont.setEnabled(false);
			   int fila = tablac.getSelectedRow();
			   btnServicio.setEnabled(true);
			   txtFolioservici.setText (tablac.getValueAt(fila,0)+"");
			   txtDetalleServi.setText (tablac.getValueAt(fila,1)+"");
			   txtDescripcion.setEditable(true);
			   Servicio=txaUso.getText().toString().trim()+"  >>Servicio:"+tablac.getValueAt(fila,1)+" >>Solicitado Por:"+tablac.getValueAt(fila,2)+" El Dia "+tablac.getValueAt(fila,3);
			   btnAgregar.setEnabled(true);
			   btnQuitarfila.setEnabled(true);
			   txaUso.setEditable(true);
			   txaUso.requestFocus();
			   txaUso.setBackground(new Color(Integer.parseInt("FFFFFF",16)));
			   dispose();
		    }
			
	        private KeyListener opFiltropuestos = new KeyListener(){
				public void keyReleased(KeyEvent arg0) {
					ObjTab.Obj_Filtro(tablac, txtBuscarb.getText().toUpperCase(), columnasc,txtBuscarb);
				}
				public void keyTyped(KeyEvent arg0) {}
				public void keyPressed(KeyEvent arg0) {}		
			};
		}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
		
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Solicitud_De_Orden_De_Gasto().setVisible(true);
		}catch(Exception e){	}
	}
};
	
