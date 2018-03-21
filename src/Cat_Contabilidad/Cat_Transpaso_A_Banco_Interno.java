package Cat_Contabilidad;

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
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
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
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Connexion;
import Conexiones_SQL.Generacion_Reportes;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Contabilidad.Obj_Transpaso_A_Banco_Interno;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_tabla;

@SuppressWarnings("serial")
public class Cat_Transpaso_A_Banco_Interno extends JFrame{
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	Connexion con = new Connexion();
	JToolBar menu_toolbar       = new JToolBar();
	
	Obj_Transpaso_A_Banco_Interno banco_interno= new Obj_Transpaso_A_Banco_Interno();	
	Obj_Usuario usuario = new Obj_Usuario().LeerSession();
	Obj_tabla  ObjTab = new Obj_tabla();

	int columnas = 7,checkbox=-1;
	public void init_tabla(){
    	this.tabla.getColumnModel().getColumn(0).setMinWidth(110);	
    	this.tabla.getColumnModel().getColumn(1).setMinWidth(80);
    	this.tabla.getColumnModel().getColumn(2).setMinWidth(110);
    	this.tabla.getColumnModel().getColumn(3).setMinWidth(68);
    	this.tabla.getColumnModel().getColumn(3).setMaxWidth(68);
    	this.tabla.getColumnModel().getColumn(4).setMinWidth(110);
    	this.tabla.getColumnModel().getColumn(5).setMinWidth(110);
    	this.tabla.getColumnModel().getColumn(6).setMinWidth(110);
    	
		String comando="Select '' as Establecimiento,'' as FolioConcentrado,'' as Concentrado,'' as fecha, 0 Concentrado,0 as BancoInterno,0 as Cheque" ;
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
	public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"Establecimiento","Folio Trabajo","Concentrado","Fecha","Imp.Concentrado","Imp.Banco Interno","Importe Cheque"}){
		 @SuppressWarnings("rawtypes")
			Class[] types = basemovimientos();
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
			public boolean isCellEditable(int fila, int columna){ return false;}
	};
	JTable tabla = new JTable(modelo);
	public JScrollPane scroll_tabla = new JScrollPane(tabla);
	
	JTextField txtFolio             = new Componentes().text(new JCTextField() ,"Folio"                     ,30   ,"String");
	JTextField txtFolio_impresion   = new Componentes().text(new JCTextField() ,"Teclee El Folio Para Imprimir",30   ,"String");
	JTextField txtFolioTrabajo      = new Componentes().text(new JCTextField() ,"Folio Trabajo"             ,50   ,"String");
	JTextField txtConcentrado       = new Componentes().text(new JCTextField() ,"Concentrado"               ,50   ,"String");
	JTextField txtFechaConcentrado  = new Componentes().text(new JCTextField() ,"Fecha Concentrado"         ,60   ,"String");
	JTextField txtImporte_Concentrado  = new Componentes().text(new JCTextField(),"Importe Concentrado"     ,10   ,"Double");	
	JTextField txtImporte_Banco_Interno= new Componentes().text(new JCTextField(),"Importe Banco Interno"   ,15   ,"Double");
	JTextField txtImporte_Cheque    = new Componentes().text(new JCTextField()  ,"Importe Cheque"           ,50   ,"String");
	JTextField txtFolio_Beneficiario= new Componentes().text(new JCTextField()  ,"Folio B"                  ,30   ,"String");
	JTextField txtBeneficiario      = new Componentes().text(new JCTextField()  ,"Beneficiario"             ,250  ,"String");
	JTextField txtFoliosolicit      = new Componentes().text(new JCTextField()  ,"Folio Solicita"           ,30   ,"String");
	JTextField txtSolicitante       = new Componentes().text(new JCTextField()  ,"Solicitante"              ,300  ,"String");
	JTextField txtFecha             = new Componentes().text(new JCTextField()  ,"Fecha"                    ,60   ,"String");
	JTextField txtTotal             = new Componentes().text(new JCTextField()  ,"Total"                    ,30   ,"String");
    JTextArea txaObservaciones      = new Componentes().textArea(new JTextArea(), "Observaciones", 160);
	
    JScrollPane Observaciones        = new JScrollPane(txaObservaciones);

	JCButton btnBuscar     = new JCButton("Buscar"       ,"Filter-List-icon16.png","Azul"); 
	JCButton btnNuevo      = new JCButton("Nuevo"        ,"Nuevo.png"             ,"Azul");
	JCButton btnGuardar    = new JCButton("Guardar"      ,"Guardar.png"           ,"Azul");
	JCButton btnDeshacer   = new JCButton("Deshacer"     ,"deshacer16.png"        ,"Azul");
	JCButton btnSolicitante= new JCButton(""             ,"Usuario.png"           ,"Azul");	
	JCButton btnQuitarfila = new JCButton("Eliminar"     ,"boton-rojo-menos-icono-5393-16.png","Azul");
	JCButton btnAgregar    = new JCButton("Agregar"      ,"double-arrow-icone-3883-16.png"    ,"Azul");
	JCButton btnImprimir   = new JCButton("Imprimir"     ,"imprimir-16.png"       ,"Azul");
	JCButton btnModificar  = new JCButton("Modificar"    ,"Modify.png"            ,"Azul");
	JCButton btnBuscarCorte= new JCButton("Buscar Concentrado","Filter-List-icon16.png","Azul"); 
	
	String status[] = {"TRASFERIDO","RECIBIDO","CANCELADO"};
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmb_status = new JComboBox(status);
	
	String cuentas[] =  banco_interno.Combo_Cuentas();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbcuenta_bancaria = new JComboBox(cuentas);
	
	String establecimientoScoi[] = new Obj_Establecimiento().Combo_Establecimientos("S");
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbEstablecimiento = new JComboBox(establecimientoScoi);
	
	Border blackline, etched, raisedbevel, loweredbevel, empty;

	String[][] tablaprecargadaordenes;
    Object[] vector = new Object[7];

    String guardar_actualizar="";
   	String grupoestablecimientos ="";
   	
   public  Cat_Transpaso_A_Banco_Interno(){
	    this.cont.add(panel);
		this.setSize(745,540);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("Traspaso De Efectivo A Banco Interno");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/articulo-icono-9036-48.png"));
		this.blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
		this.panel.setBorder(BorderFactory.createTitledBorder(blackline,"Traspaso De Efectivo A Banco Interno"));
   		
   	    this.menu_toolbar.add(btnNuevo    );
		this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.addSeparator(   );
//      this.menu_toolbar.add(btnModificar);
//		this.menu_toolbar.addSeparator(   );
//		this.menu_toolbar.addSeparator(   );
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
   	 
		int x=20, y=20,width=100,height=20, sep=110;
		this.panel.add(menu_toolbar).setBounds                        (x         ,y      ,500     ,height );
		this.panel.add(txtFolio_impresion).setBounds                  (x+530     ,y      ,170     ,height );
		this.panel.add(txtFolio).setBounds                            (x         ,y+=27  ,width   ,height );
		this.panel.add(cmb_status).setBounds                          (x+=sep    ,y      ,width   ,height );
		this.panel.add(txtFolio_Beneficiario).setBounds               (x+=sep    ,y      ,50      ,height );
		this.panel.add(txtBeneficiario).setBounds                     (x+=50     ,y      ,380     ,height );
		this.panel.add(btnSolicitante).setBounds                      (x+=380    ,y      ,50      ,height );
		
		this.panel.add(new JLabel("Cuenta:")).setBounds               (x=20      ,y+=27  ,width   ,height );
		this.panel.add(cmbcuenta_bancaria).setBounds                  (x+=45     ,y      ,130     ,height );
		this.panel.add(new JLabel("Fecha:")).setBounds                (x+=178    ,y      ,width   ,height );
		this.panel.add(txtFecha).setBounds                            (x+=40     ,y      ,130     ,height );
		
		this.panel.add(new JLabel("Observaciones:")).setBounds        (x=20      ,y+=20  ,width   ,height );
		this.panel.add(Observaciones).setBounds                       (x         ,y+=15  ,700     ,40     );
		this.panel.add(cmbEstablecimiento).setBounds                  (x         ,y+=47  ,170     ,height );
		this.panel.add(btnBuscarCorte).setBounds                      (x+=176    ,y      ,180     ,height );

		this.panel.add(txtFolioTrabajo ).setBounds                    (x+=200    ,y      ,80      ,height );
		this.panel.add(txtConcentrado ).setBounds                     (x+=80     ,y      ,125     ,height );
		this.panel.add(txtFechaConcentrado).setBounds                 (x+=130    ,y      ,108     ,height );
		this.panel.add(txtImporte_Concentrado).setBounds              (x=20      ,y+=27  ,125     ,height );
		this.panel.add(new JLabel("Efectivo:")).setBounds             (x+=135    ,y      ,width   ,height );
		this.panel.add(txtImporte_Banco_Interno).setBounds            (x+=45     ,y      ,130     ,height );
		this.panel.add(new JLabel("Cheque:")).setBounds               (x+=145    ,y      ,width   ,height );
		this.panel.add(txtImporte_Cheque).setBounds                   (x+=45     ,y      ,width   ,height );
		this.panel.add(btnAgregar).setBounds                          (x=500     ,y      ,105     ,height ); 
		this.panel.add(btnQuitarfila).setBounds                       (x+115     ,y      ,105     ,height ); 
		this.panel.add(scroll_tabla).setBounds                        (x=20      ,y+=27  ,700     ,270    );
		this.panel.add(txtFoliosolicit).setBounds                     (x         ,y+=270 ,60      ,height );		
		this.panel.add(txtSolicitante).setBounds                      (x+60      ,y      ,320     ,height );
		this.panel.add(new JLabel("Total A Transferir A Banco Interno:")).setBounds(x+430,y,320   ,height );
		this.panel.add(txtTotal).setBounds                            (x+600     ,y      ,width   ,height );

		panel_booleano(false);
		init_tabla();
		
		btnNuevo.addActionListener(nuevo);
		btnDeshacer.addActionListener(deshacer);
		btnGuardar.addActionListener(guardar);
		btnModificar.addActionListener(opEditar);
		
		cmbEstablecimiento.addActionListener(opSeleccionEstablecimiento  );
		btnSolicitante.addActionListener    (opFiltroBuscarSolicitante   );
		btnAgregar.addActionListener        (opAgregarProducto           );
		btnQuitarfila.addActionListener     (opQuitarfila                );
		btnBuscar.addActionListener         (opFiltroBuscar_orden_pago   );
		btnImprimir.addActionListener       (opImprimir_Reporte          );   
		btnBuscarCorte.addActionListener    (opFiltroBuscarcortecajaverd );
		cmbcuenta_bancaria.addActionListener(opFiltroTipoDeCuenta        );
		
        txtImporte_Banco_Interno.addKeyListener(opAgregarConEnter        );
		
		btnDeshacer.setToolTipText("<ESC> Tecla Directa");
		btnGuardar.setToolTipText("<CTRL+G> Tecla Directa");

		try { txtFecha.setText(new BuscarSQL().fecha(0).toString());} catch (SQLException e1) {e1.printStackTrace();}
		txtSolicitante.setText(usuario.getNombre_completo());
		txtFoliosolicit.setText(usuario.getFolio()+"");
		
		txtFecha.setEditable(false);
		txtFoliosolicit.setEditable(false);
		txtSolicitante.setEditable(false);
		txtFolio_Beneficiario.setEditable(false);
		txtTotal.setEditable(false);
		txtConcentrado.setEditable(false);
		txtImporte_Concentrado.setEditable(false);
		txtFolioTrabajo.setEditable(false);
		txtImporte_Cheque.setEditable(false);
		txtFechaConcentrado.setEditable(false);
		
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
          getRootPane().getActionMap().put("escape", new AbstractAction(){public void actionPerformed(ActionEvent e){  btnDeshacer.doClick(); } });

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_G,Event.CTRL_MASK),"guardar");
          getRootPane().getActionMap().put("guardar", new AbstractAction(){public void actionPerformed(ActionEvent e){ btnGuardar.doClick(); } });

	    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0), "guardar");
	      getRootPane().getActionMap().put("guardar", new AbstractAction(){public void actionPerformed(ActionEvent e){ btnGuardar.doClick(); } });
	             
	    this.addWindowListener(new WindowAdapter() {  public void windowOpened( WindowEvent e ){ txtFolio.requestFocus();  }  });                
    }
	
    public void panel_booleano(boolean boleano){
    	cmbcuenta_bancaria.setEnabled(boleano);
		cmb_status.setEnabled(boleano);
		cmbEstablecimiento.setEnabled(boleano);
	    txtImporte_Banco_Interno.setEditable(boleano); 
		txaObservaciones.setLineWrap(true); 
		txaObservaciones.setWrapStyleWord(true);
		txaObservaciones.setEditable(boleano);
		btnModificar.setEnabled(false);
		txtFolio.setEditable(false);
		txtBeneficiario.setEditable(boleano);
		btnSolicitante.setEnabled(boleano);
		btnGuardar.setEnabled(boleano);
		btnBuscarCorte.setEnabled(boleano);
		btnAgregar.setEnabled(boleano);
		btnQuitarfila.setEnabled(boleano);
    }
   
    public void panel_limpiar(){
    	cmbEstablecimiento.removeActionListener(opSeleccionEstablecimiento);
    	cmbcuenta_bancaria.removeActionListener(opFiltroTipoDeCuenta);
		btnBuscar.setEnabled(true);
		cmb_status.setSelectedIndex(0);
		cmbEstablecimiento.setSelectedIndex(0);
		cmbcuenta_bancaria.setSelectedIndex(0);
		txtImporte_Concentrado.setText("");
		txtFolioTrabajo.setText("");
		txtImporte_Cheque.setText("");
	    txtImporte_Banco_Interno.setText(""); 
	    txtConcentrado.setText("");
	    txtFechaConcentrado.setText("");
		txtTotal.setText("");
		txtFolio_impresion.setText("");
		txtFolio.setText("");
	    txtBeneficiario.setText("");
	    txtFolio_Beneficiario.setText("");
		txaObservaciones.setText("");
		modelo.setRowCount(0);
		try { txtFecha.setText(new BuscarSQL().fecha(0).toString());} catch (SQLException e1) {e1.printStackTrace();}
		txtSolicitante.setText(usuario.getNombre_completo());
		txtFoliosolicit.setText(usuario.getFolio()+"");
    	cmbEstablecimiento.addActionListener(opSeleccionEstablecimiento);
    	cmbcuenta_bancaria.addActionListener(opFiltroTipoDeCuenta);
    }
	
	public void calculo_cheque(){
		 txtImporte_Cheque.setText( (Float.valueOf(txtImporte_Concentrado.getText().toString())-Float.valueOf(txtImporte_Banco_Interno.getText().toString())+"")   );
	}
	
	public void calculo() {
		float importe=0;
		for(int i=0;i<tabla.getRowCount();i++) {
			importe=importe+Float.valueOf(tabla.getValueAt(i, 5)+"");
		}
		txtTotal.setText(importe+"");
	};
   
	KeyListener opAgregarConEnter = new KeyListener() {
		public void keyTyped(KeyEvent e) {
			if(!txtImporte_Banco_Interno.getText().toString().equals("")) {
			calculo_cheque();
			}else {
				return;
			}
		}
		
		public void keyReleased(KeyEvent e) {
			if(!txtFolioTrabajo.getText().toString().equals("")) {
				if(!txtImporte_Banco_Interno.getText().toString().equals("")) { 
					if(e.getKeyCode()==KeyEvent.VK_ENTER){
						calculo_cheque();
						btnAgregar.doClick();
					}
				}
			}
		}
		
		public void keyPressed(KeyEvent e) {
			if(!txtImporte_Banco_Interno.getText().toString().equals("")) {
			calculo_cheque();
			}else {
				return;
			}
		}
    };
    
    ActionListener opFiltroBuscarcortecajaverd = new ActionListener(){
 		public void actionPerformed(ActionEvent e){
 			if(cmbEstablecimiento.getSelectedIndex()==0) {
					JOptionPane.showMessageDialog(null, "Es Requerido Seleccione Un Establecimiento","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
					cmbEstablecimiento.requestFocus();
					cmbEstablecimiento.showPopup();
 			}else {
 			new Cat_Filtro_Buscar_Corte_Caja_Verde().setVisible(true);
 			}
 		}
 	};	

    ActionListener opFiltroTipoDeCuenta = new ActionListener(){
 		@SuppressWarnings("unchecked")
		public void actionPerformed(ActionEvent e){
 			cmbcuenta_bancaria.setEnabled(false);
 			cmbEstablecimiento.setEnabled(true);
 			cmbEstablecimiento.removeActionListener(opSeleccionEstablecimiento);
 			grupoestablecimientos=cmbcuenta_bancaria.getSelectedItem().toString().trim().substring(0,1);
 			cmbEstablecimiento.removeAllItems();
	 		 String establecimientoScoi[] = new Obj_Establecimiento().Combo_Establecimientos(grupoestablecimientos);
	 		  for(int i=0;i<establecimientoScoi.length;i++){
	 		 	 cmbEstablecimiento.addItem(establecimientoScoi[i].toString().trim());
	 		  }
 			cmbEstablecimiento.addActionListener(opSeleccionEstablecimiento);  
 			cmbEstablecimiento.requestFocus();
 			cmbEstablecimiento.showPopup();
 			btnSolicitante.setEnabled(false);
 		}
 	};	
	
 	ActionListener opAgregarProducto = new ActionListener(){
 		public void actionPerformed(ActionEvent e){
 				if(txtImporte_Banco_Interno.getText().equals("")){
 					JOptionPane.showMessageDialog(null, "Es Requerido Que El Banco Interno Tenga Importe","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
 					return;
 				}else {
 					calculo_cheque();
 					Object[] Vector_Producto = new Object[7];
 					Vector_Producto[0]=cmbEstablecimiento.getSelectedItem().toString();	
 					Vector_Producto[1]=txtFolioTrabajo.getText().toString();		
 					Vector_Producto[2]=txtConcentrado.getText().toString();		
 					Vector_Producto[3]=txtFechaConcentrado.getText().toString();		
 					Vector_Producto[4]=txtImporte_Concentrado.getText().toString().trim();		
 					Vector_Producto[5]=txtImporte_Banco_Interno.getText().toString().trim();	
 					Vector_Producto[6]=txtImporte_Cheque.getText().toString().trim();	
 					
 					modelo.addRow(Vector_Producto);

 					txtFolioTrabajo.setText("");
 					txtConcentrado.setText("");
 					txtFechaConcentrado.setText("");
 					txtImporte_Concentrado.setText("");
 					txtImporte_Banco_Interno.setText("");
 					txtImporte_Cheque.setText("");
 					txtImporte_Banco_Interno.setEditable(false);
 					btnAgregar.setEnabled(false);
 					calculo();
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
 	
    ActionListener opEditar = new ActionListener(){
 		public void actionPerformed(ActionEvent e){
 			guardar_actualizar="A";
			txtSolicitante.setText(usuario.getNombre_completo());
			txtFoliosolicit.setText(usuario.getFolio()+"");
			btnGuardar.setEnabled(true);
			btnBuscar.setEnabled(false);
			btnNuevo.setEnabled(false);
			btnImprimir.setEnabled(false);
			txtFolio_impresion.setText("");
			txtFolio_impresion.setEditable(false);
			btnAgregar.setEnabled(true);
			btnQuitarfila.setEnabled(true);
			txtImporte_Banco_Interno.setEditable(true);
			cmbEstablecimiento.setEnabled(true);
			cmbEstablecimiento.requestFocus();
			cmbEstablecimiento.showPopup();
    		tabla.setEnabled(true );
 		}
 	};
    
    ActionListener opFiltroBuscar_orden_pago = new ActionListener(){
 		public void actionPerformed(ActionEvent e){
 			new Cat_Filtro_Buscar_Orden_De_Gasto().setVisible(true);
 		}
 	};
 	
 	 ActionListener opImprimir_Reporte = new ActionListener(){
  		public void actionPerformed(ActionEvent e){
  			String folio="";
			if(txtFolio_impresion.getText().equals("")){
				folio=txtFolio.getText().toString().trim();
			}else {
				folio=txtFolio_impresion.getText().toString().trim();
			}
			if(folio.equals("")){
				JOptionPane.showMessageDialog(null, "Es Requerido Que teclee un folio para Generar su reporte","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
				txtFolio_impresion.requestFocus();
			}else {
	  			String basedatos="2.26";
	  			String vista_previa_reporte="no";
	  			int vista_previa_de_ventana=0;
	  			String comando="banco_interno_reporte_de_transferencia "+folio;
	  			String reporte = "Obj_Reporte_De_Banco_Interno_Movimiento.jrxml";
	  			 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
			} 
  		}
  	};
	
	ActionListener nuevo = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			guardar_actualizar="N";
			panel_limpiar();
			String folio_inventario="";
			try {
				folio_inventario= new BuscarSQL().folio_siguiente(87+"");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			txtSolicitante.setText(usuario.getNombre_completo());
			txtFoliosolicit.setText(usuario.getFolio()+"");
			
			txtFolio_impresion.setText("");
			txtFolio_impresion.setEditable(false);
			txtFolio.setText(folio_inventario);
			txtFolio.setEditable(false);
			btnGuardar.setEnabled(true);
			btnBuscar.setEnabled(false);
			btnNuevo.setEnabled(false);
			btnImprimir.setEnabled(false);
			btnSolicitante.setEnabled(true);
			btnSolicitante.doClick();
    		tabla.setEnabled(true );
		}
	};
	
    ActionListener opSeleccionEstablecimiento = new ActionListener(){
 	  public void actionPerformed(ActionEvent e){
				   if(cmbEstablecimiento.getSelectedIndex()!=0) {
					   btnBuscarCorte.setEnabled(true);
					   txtFolioTrabajo.setText("");
					   txtImporte_Concentrado.setText("");
					   txtImporte_Banco_Interno.setText("");
					   txtImporte_Cheque.setText("");
					   btnBuscarCorte.doClick();
				   }
				return;
			}
    };
		
   ActionListener opFiltroBuscarSolicitante = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			new Cat_Filtro_Buscar_Colaborador().setVisible(true);
		}
	};	
	
	ActionListener deshacer = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			
			if(guardar_actualizar.equals("N")){
				if(JOptionPane.showConfirmDialog(null, "Hay Datos Capturados y No Han Sido Guardados, ¿Desea Borrar Todo?", "Aviso", JOptionPane.INFORMATION_MESSAGE,0, new ImageIcon("Imagen/usuario-icono-noes_usuario9131-64.png") )== 0){
					panel_limpiar();
					panel_booleano(false);
					btnModificar.setEnabled(false);
					btnNuevo.setEnabled(true);
					txtFolio_impresion.setEditable(true);
					btnImprimir.setEnabled(true);
					guardar_actualizar="";
					return;
			     }else{
              		return;
			}
		}else{
			btnModificar.setEnabled(false);
			panel_limpiar();
			panel_booleano(false);;
			btnNuevo.setEnabled(true);
			btnImprimir.setEnabled(true);
			txtFolio_impresion.setEditable(true);
			guardar_actualizar="";
			return;
		}
		}
	};
	
	ActionListener guardar = new ActionListener(){
	public void actionPerformed(ActionEvent e){
				calculo();
				
			 if(txtTotal.getText().equals("")||Float.valueOf(txtTotal.getText())==0){
					JOptionPane.showMessageDialog(null, "Es Requerido Alimente Importe al Banco Interno Para Poder Guardar","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
				 return;
			 }else{	
				   String[][] tabla_guardado = ObjTab.tabla_guardar(tabla);
				   banco_interno.setFolio(Integer.valueOf(txtFolio.getText().toString().trim()));
				   banco_interno.setFolio_empleado_destinatario(Integer.valueOf(txtFolio_Beneficiario.getText().toString().trim()));
				   banco_interno.setObservaciones(txaObservaciones.getText().toString().trim());
				   banco_interno.setUsuario_realiza_transpaso(Integer.valueOf(txtFoliosolicit.getText().toString().trim()));
				   banco_interno.setEstatus(cmb_status.getSelectedItem().toString().trim());
				   banco_interno.setGuardar_actualizar(guardar_actualizar);
				   banco_interno.setCuenta(cmbcuenta_bancaria.getSelectedItem().toString().trim());
				   banco_interno.setDatos(tabla_guardado);
				if(banco_interno.GuardarActualizar().getFolio()>0){
					guardar_actualizar="";
					txtFolio.setText(banco_interno.getFolio()+"" );
					btnImprimir.setEnabled(true);
					btnImprimir.doClick();
					btnDeshacer.doClick();
				}else{
				  	JOptionPane.showMessageDialog(null,"Error Al Guardar Avise al Administrador del Sistema","Aviso",JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-icono-eliminar5252-64.png"));
					return;
				}
			 }
	  }			
    };
	
	//TODO inicia filtro_Buscar BENEFICIARIO empleado	
	public class Cat_Filtro_Buscar_Colaborador extends JDialog{
		Container contfb = getContentPane();
		JLayeredPane panelfb = new JLayeredPane();
		Connexion con = new Connexion();
		Obj_tabla ObjTab =new Obj_tabla();
		int columnasb = 2,checkbox=-1;
		public void init_tablafp(){
	    	this.tablab.getColumnModel().getColumn( 0).setMinWidth(55);
	    	this.tablab.getColumnModel().getColumn( 1).setMinWidth(350);
			 String comandob="select folio,dbo.nombre_empleado(folio)as nombre from tb_empleado where status in(1,2,3,6)  and folio=88 union all select folio,dbo.nombre_empleado(folio)as nombre from tb_empleado where status in(1,2,3,6) " ;
			String basedatos="98",pintar="si";
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
		public Cat_Filtro_Buscar_Colaborador(){
			this.setSize(475,450);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			this.setModal(true);
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Filter-List-icon32.png"));
			this.panelfb.setBorder(BorderFactory.createTitledBorder("Selecione Un Registro Con Doble Click"));
			this.setTitle("Filtro De Colaborador");
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
		        		txtFolio_Beneficiario.setText (tablab.getValueAt(fila,0)+"");
		        		txtBeneficiario.setText (tablab.getValueAt(fila,1)+"");
						btnAgregar.setEnabled(true);
						btnQuitarfila.setEnabled(true);
						cmbcuenta_bancaria.setEnabled(true);
						txaObservaciones.setEditable(true);
						cmbcuenta_bancaria.requestFocus();
						cmbcuenta_bancaria.showPopup();
						dispose();
		        	}
		        }
	        });
	    }
		
        private KeyListener opFiltropuestos = new KeyListener(){
			public void keyReleased(KeyEvent arg0) {
				ObjTab.Obj_Filtro(tablab, txtBuscarb.getText().toUpperCase(), columnasb);
			}
			public void keyTyped(KeyEvent arg0) {}
			public void keyPressed(KeyEvent arg0) {}		
		};
	}

	//TODO inicia filtro_Buscar cortes de caja Verde
	public class Cat_Filtro_Buscar_Corte_Caja_Verde extends JDialog{
			Container contfb = getContentPane();
			JLayeredPane panelfb = new JLayeredPane();
			Connexion con = new Connexion();
			Obj_tabla ObjTab =new Obj_tabla();
			int columnasb = 16,checkbox=-1;
		public void init_tablafp(String cadena){
	    	this.tablab.getColumnModel().getColumn( 0).setMinWidth(100);
	    	this.tablab.getColumnModel().getColumn( 1).setMinWidth(140);
	    	this.tablab.getColumnModel().getColumn( 2).setMinWidth(90);
	    	this.tablab.getColumnModel().getColumn( 3).setMinWidth(90);
	    	this.tablab.getColumnModel().getColumn( 4).setMinWidth(90);
	    	this.tablab.getColumnModel().getColumn( 5).setMinWidth(90);
	    	this.tablab.getColumnModel().getColumn( 6).setMinWidth(90);
	    	this.tablab.getColumnModel().getColumn( 7).setMinWidth(130);
	    	this.tablab.getColumnModel().getColumn( 8).setMinWidth(130);
	    	this.tablab.getColumnModel().getColumn( 9).setMinWidth(130);
	    	this.tablab.getColumnModel().getColumn(10).setMinWidth(90);
	    	
			String comandob="banco_interno_filtro_cortes_cajas_verdes_de_trabajos '"+cadena+"','"+cmbEstablecimiento.getSelectedItem().toString()+"','"+cmbcuenta_bancaria.getSelectedItem().toString().trim()+"'"  ;
			String basedatos="98",pintar="si";
			ObjTab.Obj_Refrescar(tablab,modelob, columnasb, comandob, basedatos,pintar,checkbox);
	    }
		
		@SuppressWarnings("rawtypes")
		public Class[] base (){
			Class[] types = new Class[columnasb];
			for(int i = 0; i<columnasb; i++){types[i]= java.lang.Object.class;}
			 return types;
		}
		
		public DefaultTableModel modelob = new DefaultTableModel(null, new String[]{"Folio Trabajo","Concentrado","Banco Interno","Fecha Trabajo","Gastos","Dolares","Vales","Diferencia Cortes","Otros Faltantes","Otros Sobrantes","Caja Verde", "Total","Sobrante Finanzas","Total Final", "Deposito", "Comentario"}){
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
		public Cat_Filtro_Buscar_Corte_Caja_Verde(){
			this.setSize(800,450);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			this.setModal(true);
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Filter-List-icon32.png"));
			this.panelfb.setBorder(BorderFactory.createTitledBorder("Selecione Un Registro Con Doble Click"));
			this.setTitle("Filtro De Cajas Verdes De Concentrados");
			trsfiltro = new TableRowSorter(modelob); 
			tablab.setRowSorter(trsfiltro);
			this.panelfb.add(txtBuscarb).setBounds      (10 ,20 ,780 , 20 );
			this.panelfb.add(scroll_tablab).setBounds   (10 ,40 ,780 ,370 );
			this.init_tablafp(parametro_cadena_de_concentrados());
			this.agregar(tablab);
			this.txtBuscarb.addKeyListener  (opFiltro );
			this.txtBuscarb.addKeyListener  (PasarATabla );
			this.tablab.addKeyListener(agregarcon_enter);
			contfb.add(panelfb);
		}
		
		KeyListener PasarATabla = new KeyListener() {
			public void keyTyped(KeyEvent e){}
			public void keyReleased(KeyEvent e) {}
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_DOWN){
					tablab.requestFocus();
					tablab.getSelectionModel().setSelectionInterval(0,0);;
				}
			}
		};
		
		KeyListener agregarcon_enter = new KeyListener() {
			public void keyTyped(KeyEvent e){}
			public void keyReleased(KeyEvent e) {}
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					int fila = tablab.getSelectedRow();
	        		txtFolioTrabajo.setText (tablab.getValueAt(fila,0)+"");
	        		txtConcentrado.setText (tablab.getValueAt(fila,1)+"");
	        		txtImporte_Concentrado.setText (tablab.getValueAt(fila,2)+"");
	        		txtImporte_Banco_Interno.setText (tablab.getValueAt(fila,2)+"");
	        		txtFechaConcentrado.setText (tablab.getValueAt(fila,3)+"");
	        		txtImporte_Banco_Interno.setEditable(true);
	        		btnAgregar.setEnabled(true);
	        		calculo_cheque();
	        		dispose();
	        		txtImporte_Banco_Interno.requestFocus();	
				}
			}
		};
			
		public String parametro_cadena_de_concentrados(){
			 int rengloneslunes     = tabla.getRowCount()    ;
			 int fila  = 0;
			 String lista = "(";	
			while(rengloneslunes > 0){
				 lista =lista+"''"+modelo.getValueAt(fila, 1).toString().trim()+"'',";
					fila+=1;
					rengloneslunes--;
			}
			
			lista=lista+"&";
			String[] parts=lista.split(",&");
			lista=parts[0]+")";
			
			if(lista.equals("(&)")){lista="('''')";};
			return lista;
		};
		
		private void agregar(final JTable tbl) {
	        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
		        	if(e.getClickCount()==1){
		        		int fila = tablab.getSelectedRow();
		        		txtFolioTrabajo.setText (tablab.getValueAt(fila,0)+"");
		        		txtConcentrado.setText (tablab.getValueAt(fila,1)+"");
		        		txtFechaConcentrado.setText (tablab.getValueAt(fila,3)+"");
		        		
		        		if(grupoestablecimientos.equals("S")||grupoestablecimientos.equals("F")) {
		        			txtImporte_Banco_Interno.setEditable(true);
		        			txtImporte_Concentrado.setText (tablab.getValueAt(fila,2)+"");
			        		txtImporte_Banco_Interno.setText (tablab.getValueAt(fila,2)+"");
			        		txtImporte_Banco_Interno.setEditable(true);
			        		btnAgregar.setEnabled(true);
			        		calculo_cheque();
		        		}else {
		        			if(grupoestablecimientos.equals("V")) {
			        			    txtImporte_Concentrado.setText (tablab.getValueAt(fila,6)+"");
				        		    txtImporte_Banco_Interno.setText (tablab.getValueAt(fila,6)+"");
			        				txtImporte_Banco_Interno.setEditable(false);
					        		btnAgregar.setEnabled(true);
					        		calculo_cheque();
					        		btnAgregar.doClick();
		        			}else {
		        				if(grupoestablecimientos.equals("D")) {
				        			txtImporte_Concentrado.setText (tablab.getValueAt(fila,5)+"");
					        		txtImporte_Banco_Interno.setText (tablab.getValueAt(fila,5)+"");
			        				txtImporte_Banco_Interno.setEditable(false);
					        		btnAgregar.setEnabled(true);
					        		calculo_cheque();
					        		btnAgregar.doClick();
		        				}
		        			}	
		        		}
		        		dispose();
		        		txtImporte_Banco_Interno.requestFocus();
		        	}
		        }
	        });
	    }
		
        private KeyListener opFiltro = new KeyListener(){
			public void keyReleased(KeyEvent arg0) {
				ObjTab.Obj_Filtro(tablab, txtBuscarb.getText().toUpperCase(), columnasb);
			}
			public void keyTyped(KeyEvent arg0) {}
			public void keyPressed(KeyEvent arg0) {}		
		};
	}
	
	//TODO inicia filtro_Buscar transpaso
		public class Cat_Filtro_Buscar_Orden_De_Gasto extends JDialog{
			Container contfb = getContentPane();
			JLayeredPane panelfb = new JLayeredPane();
			Connexion con = new Connexion();
			Obj_tabla ObjTab =new Obj_tabla();
			int columnasb = 12,checkbox=-1;
			public void init_tablafp(){
		    	this.tablab.getColumnModel().getColumn( 0).setMinWidth(50);
		    	this.tablab.getColumnModel().getColumn( 0).setMaxWidth(50);
		    	this.tablab.getColumnModel().getColumn( 1).setMinWidth(100);
		    	this.tablab.getColumnModel().getColumn( 2).setMinWidth(200);
		    	this.tablab.getColumnModel().getColumn( 3).setMinWidth(200);
		    	this.tablab.getColumnModel().getColumn( 4).setMinWidth(120);
		    	this.tablab.getColumnModel().getColumn( 5).setMinWidth(130);
		    	this.tablab.getColumnModel().getColumn( 6).setMinWidth(120);
		    	this.tablab.getColumnModel().getColumn( 7).setMinWidth(120);
		    	this.tablab.getColumnModel().getColumn( 8).setMinWidth(200);
		    	this.tablab.getColumnModel().getColumn( 9).setMinWidth(150);
		    	this.tablab.getColumnModel().getColumn(10).setMinWidth(150);
		    	this.tablab.getColumnModel().getColumn(11).setMinWidth(350);
		    	
				String comandob = "banco_interno_traspasos_filtro";
		    	String basedatos="26",pintar="si";
				ObjTab.Obj_Refrescar(tablab,modelob, columnasb, comandob, basedatos,pintar,checkbox);
		    }
			
			@SuppressWarnings("rawtypes")
			public Class[] base (){
				Class[] types = new Class[columnasb];
				for(int i = 0; i<columnasb; i++){types[i]= java.lang.Object.class;}
				 return types;
			}
			
			public DefaultTableModel modelob = new DefaultTableModel(null, new String[]{ "Folio","Fecha Traspaso","Destinatario","Usuario T.","Importe Concentrado","Importe Ingreso", "Importe Egreso", "Importe Cheque", "Recibe", "Fecha Recibe", "Estatus", "Observaciones"}){
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
			public Cat_Filtro_Buscar_Orden_De_Gasto(){
				this.setSize(825,400);
				this.setResizable(false);
				this.setLocationRelativeTo(null);
				this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				this.setModal(true);
				this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Filter-List-icon32.png"));
				this.panelfb.setBorder(BorderFactory.createTitledBorder("Selecione Un Registro Con Doble Click"));
				this.setTitle("Filtro De Movimientos En Banco Interno");
				trsfiltro = new TableRowSorter(modelob); 
				tablab.setRowSorter(trsfiltro);
				this.panelfb.add(txtBuscarb).setBounds      (10 ,20 ,800 , 20 );
				this.panelfb.add(scroll_tablab).setBounds   (10 ,40 ,800 ,300 );
				this.init_tablafp();
				this.agregar(tablab);
				this.txtBuscarb.addKeyListener  (opFiltropuestos );
				this.txtBuscarb.addKeyListener(PasarATabla);
				this.tablab.addKeyListener(agregarcon_enter);
				contfb.add(panelfb);
			}
			
			KeyListener PasarATabla = new KeyListener() {
				public void keyTyped(KeyEvent e){}
				public void keyReleased(KeyEvent e) {}
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode()==KeyEvent.VK_DOWN){
						tablab.requestFocus();
						tablab.getSelectionModel().setSelectionInterval(0,0);;
					}
				}
			};
	 	     
			private void agregar(final JTable tbl) {
		        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
			        	if(e.getClickCount()==1){
			        		panel_limpiar();
			        		modelo.setRowCount(0);
			        		
							int fila = tablab.getSelectedRow();
			        		String[][] tablacompleta=banco_interno.consulta_movimiento_banco(Integer.valueOf(tablab.getValueAt(fila,0)+""));
			        	    Object[]   vectortabla = new Object[7];
			        		for(int i=0;i<tablacompleta.length;i++){
			        				for(int j=0;j<7;j++){
			        				  vectortabla[j] = tablacompleta[i][j].toString();
			        				}
			        				modelo.addRow(vectortabla);
			        		}
			        			 
			        		txtFolio.setText             (tablacompleta[0][ 7].toString());
			        		cmb_status.setSelectedItem   (tablacompleta[0][ 8].toString());
			        		txtFolio_Beneficiario.setText(tablacompleta[0][ 9].toString());
			        		txtBeneficiario.setText      (tablacompleta[0][10].toString());
			        		txaObservaciones.setText     (tablacompleta[0][11].toString());
			         		txtFoliosolicit.setText      (tablacompleta[0][12].toString());
			        		txtSolicitante.setText       (tablacompleta[0][13].toString());
			        		txtFecha.setText             (tablacompleta[0][14].toString());
			        		
			        		cmbEstablecimiento.removeActionListener(opSeleccionEstablecimiento);
			        		cmbcuenta_bancaria.removeActionListener(opFiltroTipoDeCuenta);
			        		
			        		cmbcuenta_bancaria.setSelectedItem(tablacompleta[0][15].toString());
			        		cmbEstablecimiento.setSelectedItem(tablacompleta[0][6].toString());
			        		
							calculo();
							dispose();
			        	}
			        }
		        });
		    }
			
			KeyListener agregarcon_enter = new KeyListener() {
				public void keyTyped(KeyEvent e){}
				public void keyReleased(KeyEvent e) {}
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode()==KeyEvent.VK_ENTER){
						panel_limpiar();
		        		modelo.setRowCount(0);
		        		
						int fila = tablab.getSelectedRow();
		        		String[][] tablacompleta=banco_interno.consulta_movimiento_banco(Integer.valueOf(tablab.getValueAt(fila,0)+""));
		        	    Object[]   vectortabla = new Object[7];
		        		for(int i=0;i<tablacompleta.length;i++){
		        				for(int j=0;j<7;j++){
		        				  vectortabla[j] = tablacompleta[i][j].toString();
		        				}
		        				modelo.addRow(vectortabla);
		        		}
		        			 
		        		txtFolio.setText             (tablacompleta[0][ 7].toString());
		        		cmb_status.setSelectedItem   (tablacompleta[0][ 8].toString());
		        		txtFolio_Beneficiario.setText(tablacompleta[0][ 9].toString());
		        		txtBeneficiario.setText      (tablacompleta[0][10].toString());
		        		txaObservaciones.setText     (tablacompleta[0][11].toString());
		         		txtFoliosolicit.setText      (tablacompleta[0][12].toString());
		        		txtSolicitante.setText       (tablacompleta[0][13].toString());
		        		txtFecha.setText             (tablacompleta[0][14].toString());
		        		
		        		cmbEstablecimiento.removeActionListener(opSeleccionEstablecimiento);
		        		cmbcuenta_bancaria.removeActionListener(opFiltroTipoDeCuenta);
		        		cmbEstablecimiento.setSelectedItem(tablacompleta[0][6].toString());
						calculo();
						dispose();
					}
				}
			};
			
			
	        private KeyListener opFiltropuestos = new KeyListener(){
				public void keyReleased(KeyEvent arg0) {
					ObjTab.Obj_Filtro(tablab, txtBuscarb.getText().toUpperCase(), columnasb);
				}
				public void keyTyped(KeyEvent arg0) {}
				public void keyPressed(KeyEvent arg0) {}		
			};
		    }
		
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Transpaso_A_Banco_Interno().setVisible(true);
		}catch(Exception e){	}
	}
};
	
