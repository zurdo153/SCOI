package Cat_Contabilidad;

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
import Obj_Contabilidad.Obj_Saldo_Banco_Interno;
import Obj_Contabilidad.Obj_Transpaso_A_Banco_Interno;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_tabla;

@SuppressWarnings("serial")
public class Cat_Banco_Interno extends JFrame{
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	Connexion con = new Connexion();
	JToolBar menu_toolbar       = new JToolBar();
	
	Obj_Saldo_Banco_Interno banco_interno= new Obj_Saldo_Banco_Interno();	
	Obj_Usuario usuario = new Obj_Usuario().LeerSession();
	Obj_tabla  ObjTab = new Obj_tabla();

	int columnas = 10,checkbox=9;
	public void init_tabla(){
		this.tabla.getColumnModel().getColumn(0).setMinWidth(40);	
    	this.tabla.getColumnModel().getColumn(0).setMaxWidth(40);	
    	this.tabla.getColumnModel().getColumn(1).setMinWidth(100);
    	this.tabla.getColumnModel().getColumn(2).setMinWidth(60);
    	this.tabla.getColumnModel().getColumn(2).setMaxWidth(60);
    	this.tabla.getColumnModel().getColumn(3).setMinWidth(130);
    	this.tabla.getColumnModel().getColumn(3).setMaxWidth(130);
    	this.tabla.getColumnModel().getColumn(4).setMinWidth(70);
    	
    	this.tabla.getColumnModel().getColumn(5).setMinWidth(90);
    	this.tabla.getColumnModel().getColumn(6).setMinWidth(100);
    	this.tabla.getColumnModel().getColumn(8).setMaxWidth(20);
    	this.tabla.getColumnModel().getColumn(9).setMaxWidth(160);
    	
		String comando="select 0 as nucta,'' as cuenta,0 as folio,'' as concepto,0 as importe,'' as observaciones,'' as usuario_transpaso,''as fecha,'false' as booleano ,0 as folio_concentrado" ;
		String basedatos="26",pintar="si";
		ObjTab.Obj_Refrescar(tabla,modelo, columnas, comando, basedatos,pintar,checkbox);
		modelo.setRowCount(0);
    }
	
	public void refrescar(){
		this.tabla.getColumnModel().getColumn(0).setMinWidth(40);	
    	this.tabla.getColumnModel().getColumn(0).setMaxWidth(40);	
    	this.tabla.getColumnModel().getColumn(1).setMinWidth(100);
    	this.tabla.getColumnModel().getColumn(2).setMinWidth(60);
    	this.tabla.getColumnModel().getColumn(2).setMaxWidth(60);
    	this.tabla.getColumnModel().getColumn(3).setMinWidth(130);
    	this.tabla.getColumnModel().getColumn(3).setMaxWidth(130);
    	this.tabla.getColumnModel().getColumn(4).setMinWidth(70);
    	
    	this.tabla.getColumnModel().getColumn(5).setMinWidth(90);
    	this.tabla.getColumnModel().getColumn(6).setMinWidth(100);
    	this.tabla.getColumnModel().getColumn(8).setMaxWidth(20);
    	this.tabla.getColumnModel().getColumn(9).setMaxWidth(160);
		String comando="banco_interno_ingreso_de_transferencias '"+cmbcuenta_bancaria.getSelectedItem().toString().trim()+"'" ;
		String basedatos="26",pintar="si";
		ObjTab.Obj_Refrescar(tabla,modelo, columnas, comando, basedatos,pintar,checkbox);
    }
	
	@SuppressWarnings("rawtypes")
	public Class[] basemovimientos (){
		Class[] types = new Class[columnas];
		for(int i = 0; i<columnas; i++){
		if(i==8) {types[i]=java.lang.Boolean.class;}else {types[i]= java.lang.Object.class;  }
    	
		}
		 return types;
	}
	public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"NoCta","Cuenta","Folio B.I.","Concepto","Importe","Observaciones","Usuario Traspaso","Fecha","","Folio Trabajo"}){
		 @SuppressWarnings("rawtypes")
			Class[] types = basemovimientos();
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
			public boolean isCellEditable(int fila, int columna){  if(columna==8) {return true;}else { return false;}}
	};
	JTable tabla = new JTable(modelo);
	public JScrollPane scroll_tabla = new JScrollPane(tabla);
	
	JTextField txtFolio             = new Componentes().text(new JCTextField()  ,"Folio"                        ,30    ,"String");
	JTextField txtFolio_impresion   = new Componentes().text(new JCTextField()  ,"Teclee El Folio Para Imprimir",30    ,"String");
	JTextField txtFoliosolicit      = new Componentes().text(new JCTextField()  ,"Folio Solicita"               ,30    ,"String");
	JTextField txtSolicitante       = new Componentes().text(new JCTextField()  ,"Solicitante"                  ,300   ,"String");
	JTextField txtFecha             = new Componentes().text(new JCTextField()  ,"Fecha"                        ,60    ,"String");
	JTextField txttotalImporte_BI   = new Componentes().text(new JCTextField()  ,"Importe Total"                ,30    ,"String");
	
    JTextArea txaObservaciones      = new Componentes().textArea(new JTextArea(), "Observaciones", 160);
    JScrollPane Observaciones       = new JScrollPane(txaObservaciones);

	JCButton btnIngreso    = new JCButton("Recibir"      ,"ingresos_32.png"        ,"Azul");
	JCButton btnEgreso     = new JCButton("Transferir"   ,"Egreso32.png"           ,"Azul"); 
	JCButton btnBuscar     = new JCButton("Buscar"       ,"Filter-List-icon16.png" ,"Azul"); 
	JCButton btnNuevo      = new JCButton("Nuevo"        ,"Nuevo.png"              ,"Azul");
	JCButton btnGuardar    = new JCButton("Guardar"      ,"Guardar.png"            ,"Azul");
	JCButton btnDeshacer   = new JCButton("Deshacer"     ,"deshacer16.png"         ,"Azul");
	JCButton btnImprimir   = new JCButton("Imprimir"     ,"imprimir-16.png"        ,"Azul");
	
	String status[] = {"TRASFERIDO","RECIBIDO","CANCELADO"};
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmb_status = new JComboBox(status);
	
	String cuentas[] =  banco_interno.Combo_Cuentas();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbcuenta_bancaria = new JComboBox(cuentas);
	
	Border blackline, etched, raisedbevel, loweredbevel, empty;

    String guardar_actualizar="";
   public  Cat_Banco_Interno(){
	    this.cont.add(panel);
		this.setSize(250,190);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("Banco Interno");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/bank128.png"));
		this.blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
		this.panel.setBorder(BorderFactory.createTitledBorder(blackline,"Seleccione La Opcion Deseada"));
		
		this.panel.add(btnIngreso).setBounds (40, 35, 160, 34);
		this.panel.add(btnEgreso).setBounds  (40, 95, 160, 34);
		
		btnIngreso.addActionListener(opSeleccion);
		btnEgreso.addActionListener(opSeleccion);
    }
   
   public void constructor(String tipo) {
	        this.cont.add(panel);
	 		this.setSize(745,540);
	 		this.setResizable(false);
	 		this.setLocationRelativeTo(null);
	 		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	 		this.btnIngreso.setVisible(false);
	 		this.btnEgreso.setVisible(false);
	 		
	 		if(tipo.equals("Recibir")){
	 			this.cmb_status.setSelectedItem("RECIBIDO");
		 		this.setTitle("Recepcion De Ingreso a Banco Interno");
		 		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/ingresos_32.png"));
		 		this.blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
		 		this.panel.setBorder(BorderFactory.createTitledBorder(blackline,"Recepcion De Ingreso A Banco Interno"));
		    		
		        this.menu_toolbar.add(btnNuevo    );
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
		    	 
		 		int x=20, y=20,width=100,height=20, sep=110;
		 		this.panel.add(menu_toolbar).setBounds                          (x         ,y      ,500     ,height );
		 		this.panel.add(txtFolio_impresion).setBounds                    (x+530     ,y      ,170     ,height );
		 		this.panel.add(txtFolio).setBounds                              (x         ,y+=27  ,width   ,height );
		 		this.panel.add(cmb_status).setBounds                            (x+=sep    ,y      ,width   ,height );
		 		this.panel.add(txtFoliosolicit).setBounds                       (x+=sep    ,y      ,60      ,height );		
		 		this.panel.add(txtSolicitante).setBounds                        (x+=60     ,y      ,290     ,height );
		 		this.panel.add(txtFecha).setBounds                              (x+290     ,y      ,130     ,height );
		 		this.panel.add(cmbcuenta_bancaria).setBounds                    (x=20      ,y+=27  ,170     ,height );
		 		this.panel.add(scroll_tabla).setBounds                          (x         ,y+=27  ,700     ,270    );
		 		this.panel.add(new JLabel("Importe Total A Recibir:")).setBounds(x=500     ,y+=270 ,170     ,height );
		 		this.panel.add(txttotalImporte_BI).setBounds                    (x+120     ,y      ,width   ,height );
		 		this.panel.add(new JLabel("Observaciones:")).setBounds          (x=20      ,y+=10  ,width   ,height );
		 		this.panel.add(Observaciones).setBounds                         (x         ,y+=15  ,700     ,40     );
	
		 		panel_booleano(false);
		 		init_tabla();
		 		tabla.setEnabled(false);
		 		txtFecha.setEditable(false);
		 		txtFoliosolicit.setEditable(false);
		 		txtSolicitante.setEditable(false);
				txttotalImporte_BI.setEditable(false); 
				
		 		tabla.addMouseListener              (opAgregarCalculoClick);
		 		btnNuevo.addActionListener          (nuevo);
		 		btnDeshacer.addActionListener       (deshacer);
		 		cmbcuenta_bancaria.addActionListener(opSeleccionCuenta);

		 		
		 		
		 		btnGuardar.addActionListener        (guardar);
		 		btnBuscar.addActionListener         (opFiltroBuscar_orden_pago  );
		 		btnImprimir.addActionListener       (opImprimir_Reporte         );   
		 		btnDeshacer.setToolTipText("<ESC> Tecla Directa");
		 		btnGuardar.setToolTipText("<CTRL+G> Tecla Directa");
	 		}
	 		
	 		try { txtFecha.setText(new BuscarSQL().fecha(0).toString());} catch (SQLException e1) {e1.printStackTrace();}
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
   
   ActionListener opSeleccion = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			constructor(e.getActionCommand());
		}
	};
	
  ActionListener nuevo = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			guardar_actualizar="N";
			panel_limpiar();
			String folio="";
			try {folio= new BuscarSQL().folio_siguiente(45+"");
			} catch (SQLException e1) {	e1.printStackTrace();}

			txtFolio_impresion.setEditable(false);
			txtFolio.setText(folio);
			txtFolio.setEditable(false);
			btnBuscar.setEnabled(false);
			btnNuevo.setEnabled(false);
			btnImprimir.setEnabled(false);
			
            txaObservaciones.setEditable(true);		
			btnGuardar.setEnabled(true);
   		    tabla.setEnabled(true );
			cmbcuenta_bancaria.setEnabled(true);
			cmbcuenta_bancaria.requestFocus();
			cmbcuenta_bancaria.showPopup();
		}
	};
	
	ActionListener opSeleccionCuenta = new ActionListener(){
			public void actionPerformed(ActionEvent e){
			refrescar();
			tabla.setEnabled(true);
			}
	};
   
	ActionListener deshacer = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(guardar_actualizar.equals("N")){
				if(JOptionPane.showConfirmDialog(null, "Hay Datos Capturados y No Han Sido Guardados, ¿Desea Borrar Todo?", "Aviso", JOptionPane.INFORMATION_MESSAGE,0, new ImageIcon("Imagen/usuario-icono-noes_usuario9131-64.png") )== 0){
					panel_limpiar();
					panel_booleano(false);
					btnNuevo.setEnabled(true);
					txtFolio_impresion.setEditable(true);
					btnImprimir.setEnabled(true);
					guardar_actualizar="";
					return;
			     }else{
              		return;
			}
		}else{
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

	MouseListener opAgregarCalculoClick = new MouseListener() {
		@Override
		public void mouseReleased(MouseEvent e){calculo();}
		@Override
		public void mousePressed(MouseEvent e) {calculo();}
		@Override
		public void mouseExited(MouseEvent e)  {}
		@Override
		public void mouseEntered(MouseEvent e) {}
		@Override
		public void mouseClicked(MouseEvent e) {calculo();}
	};
	
    public void panel_booleano(boolean boleano){
		cmb_status.setEnabled(boleano);
		cmbcuenta_bancaria.setEnabled(boleano);
		txaObservaciones.setLineWrap(true); 
		txaObservaciones.setWrapStyleWord(true);
		txaObservaciones.setEditable(boleano);
		txtFolio.setEditable(false);
		btnGuardar.setEnabled(boleano);
    }
   
    public void panel_limpiar(){
		btnBuscar.setEnabled(true);
		cmbcuenta_bancaria.setSelectedIndex(0);;
	    txttotalImporte_BI.setText(""); 
		txtFolio_impresion.setText("");
		txtFolio.setText("");
		txaObservaciones.setText("");
		modelo.setRowCount(0);
		try { txtFecha.setText(new BuscarSQL().fecha(0).toString());} catch (SQLException e1) {e1.printStackTrace();}
		txtSolicitante.setText(usuario.getNombre_completo());
		txtFoliosolicit.setText(usuario.getFolio()+"");
    }
	
	public void calculo() {
		float importe=0;
		for(int i=0;i<tabla.getRowCount();i++) {
			if(tabla.getValueAt(i, 8).toString().equals("true")) {
			importe=importe+Float.valueOf(tabla.getValueAt(i, 4)+"");
			}
		}
		txttotalImporte_BI.setText(importe+"");
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
	  			String comando="banco_interno_reporte_de_movimiento_de_saldo "+folio;
	  			String reporte = "Obj_Reporte_De_Banco_Interno_Movimiento_De_Saldo.jrxml";
	  		    new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
			} 
  		}
  	};
	
	ActionListener guardar = new ActionListener(){
	public void actionPerformed(ActionEvent e){
			 if(txttotalImporte_BI.getText().equals("")||Float.valueOf(txttotalImporte_BI.getText())==0){
				 JOptionPane.showMessageDialog(null, "Es Requerido Seleccione Registros Para Poder Guardar","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
				 return;
			 }else{	
				   calculo();
				   String[][] tabla_guardado = ObjTab.tabla_guardar(tabla);
				   banco_interno.setFolio(Integer.valueOf(txtFolio.getText().toString().trim()));
				   banco_interno.setObservaciones(txaObservaciones.getText().toString().trim());				   
				   banco_interno.setFolio_usuario(Integer.valueOf(txtFoliosolicit.getText().toString().trim()));
				   banco_interno.setImporte(Float.valueOf(txttotalImporte_BI.getText().toString().trim()));
				   banco_interno.setCuenta(cmbcuenta_bancaria.getSelectedItem().toString().trim());
				   banco_interno.setEstatus(cmb_status.getSelectedItem().toString().trim());
				   banco_interno.setGuardar_actualizar(guardar_actualizar);
				   banco_interno.setTabla(tabla_guardado);
				   
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

    
    //TODO inicia filtro_Buscar cortes de caja Verde sin uso
//	public class Cat_Filtro_Buscar_Corte_Caja_Verde extends JDialog{
//			Container contfb = getContentPane();
//			JLayeredPane panelfb = new JLayeredPane();
//			Connexion con = new Connexion();
//			Obj_tabla ObjTab =new Obj_tabla();
//			int columnasb = 16,checkbox=-1;
//		public void init_tablafp(String cadena){
//	    	this.tablab.getColumnModel().getColumn( 0).setMinWidth(100);
//	    	this.tablab.getColumnModel().getColumn( 1).setMinWidth(140);
//	    	this.tablab.getColumnModel().getColumn( 2).setMinWidth(90);
//	    	this.tablab.getColumnModel().getColumn( 3).setMinWidth(90);
//	    	this.tablab.getColumnModel().getColumn( 4).setMinWidth(90);
//	    	this.tablab.getColumnModel().getColumn( 5).setMinWidth(90);
//	    	this.tablab.getColumnModel().getColumn( 6).setMinWidth(90);
//	    	this.tablab.getColumnModel().getColumn( 7).setMinWidth(130);
//	    	this.tablab.getColumnModel().getColumn( 8).setMinWidth(130);
//	    	this.tablab.getColumnModel().getColumn( 9).setMinWidth(130);
//	    	this.tablab.getColumnModel().getColumn(10).setMinWidth(90);
//	    	
//			 String comandob="banco_interno_filtro_cortes_cajas_verdes_de_trabajos '"+cadena+"'" ;
//			String basedatos="98",pintar="si";
//			ObjTab.Obj_Refrescar(tablab,modelob, columnasb, comandob, basedatos,pintar,checkbox);
//	    }
//		
//		@SuppressWarnings("rawtypes")
//		public Class[] base (){
//			Class[] types = new Class[columnasb];
//			for(int i = 0; i<columnasb; i++){types[i]= java.lang.Object.class;}
//			 return types;
//		}
//		
//		public DefaultTableModel modelob = new DefaultTableModel(null, new String[]{"Folio Trabajo","Concentrado","Banco Interno","Fecha Trabajo","Gastos","Dolares","Vales","Diferencia Cortes","Otros Faltantes","Otros Sobrantes","Caja Verde", "Total","Sobrante Finanzas","Total Final", "Deposito", "Comentario"}){
//			 @SuppressWarnings("rawtypes")
//				Class[] types = base();
//				@SuppressWarnings({ "rawtypes", "unchecked" })
//				public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
//				public boolean isCellEditable(int fila, int columna){return false;}
//		};
//		
//		JTable tablab = new JTable(modelob);
//		public JScrollPane scroll_tablab = new JScrollPane(tablab);
//	     @SuppressWarnings({ "rawtypes" })
//	    private TableRowSorter trsfiltro;
//		     
//		JTextField txtBuscarb  = new Componentes().text(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<", 500, "String");
//		@SuppressWarnings({ "rawtypes", "unchecked" })
//		public Cat_Filtro_Buscar_Corte_Caja_Verde(){
//			this.setSize(800,450);
//			this.setResizable(false);
//			this.setLocationRelativeTo(null);
//			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//			this.setModal(true);
//			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Filter-List-icon32.png"));
//			this.panelfb.setBorder(BorderFactory.createTitledBorder("Selecione Un Registro Con Doble Click"));
//			this.setTitle("Filtro De Cajas Verdes De Concentrados");
//			trsfiltro = new TableRowSorter(modelob); 
//			tablab.setRowSorter(trsfiltro);
//			this.panelfb.add(txtBuscarb).setBounds      (10 ,20 ,780 , 20 );
//			this.panelfb.add(scroll_tablab).setBounds   (10 ,40 ,780 ,370 );
//			this.init_tablafp(parametro_cadena_de_concentrados());
//			this.agregar(tablab);
//			this.txtBuscarb.addKeyListener  (opFiltro );
//			this.txtBuscarb.addKeyListener  (PasarATabla );
//			this.tablab.addKeyListener(agregarcon_enter);
//			contfb.add(panelfb);
//		}
//		
//		KeyListener PasarATabla = new KeyListener() {
//			public void keyTyped(KeyEvent e){}
//			public void keyReleased(KeyEvent e) {}
//			public void keyPressed(KeyEvent e) {
//				if(e.getKeyCode()==KeyEvent.VK_DOWN){
//					tablab.requestFocus();
//					tablab.getSelectionModel().setSelectionInterval(0,0);;
//				}
//			}
//		};
//		
//		KeyListener agregarcon_enter = new KeyListener() {
//			public void keyTyped(KeyEvent e){}
//			public void keyReleased(KeyEvent e) {}
//			public void keyPressed(KeyEvent e) {
//				if(e.getKeyCode()==KeyEvent.VK_ENTER){
//					int fila = tablab.getSelectedRow();
////	        		txtFolioTrabajo.setText (tablab.getValueAt(fila,0)+"");
////	        		txtConcentrado.setText (tablab.getValueAt(fila,1)+"");
////	        		txtImporte_Concentrado.setText (tablab.getValueAt(fila,2)+"");
////	        		txtImporte_Banco_Interno.setText (tablab.getValueAt(fila,2)+"");
////	        		txtFechaConcentrado.setText (tablab.getValueAt(fila,3)+"");
////	        		txtImporte_Banco_Interno.setEditable(true);
////	        		btnAgregar.setEnabled(true);
////	        		calculo_cheque();
////	        		dispose();
////	        		txtImporte_Banco_Interno.requestFocus();	
//				}
//			}
//		};
//			
//			
//		public String parametro_cadena_de_concentrados(){
//			 int rengloneslunes     = tabla.getRowCount()    ;
//			 int fila  = 0;
//			 String lista = "(";	
//			while(rengloneslunes > 0){
//				 lista =lista+"''"+modelo.getValueAt(fila, 1).toString().trim()+"'',";
//					fila+=1;
//					rengloneslunes--;
//			}
//			
//			lista=lista+"&";
//			String[] parts=lista.split(",&");
//			lista=parts[0]+")";
//			
//			if(lista.equals("(&)")){lista="('''')";};
//			return lista;
//		};
//		
//		private void agregar(final JTable tbl) {
//	        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
//				public void mouseClicked(MouseEvent e) {
//		        	if(e.getClickCount()==1){
//		        		int fila = tablab.getSelectedRow();
////		        		txtFolioTrabajo.setText (tablab.getValueAt(fila,0)+"");
////		        		txtConcentrado.setText (tablab.getValueAt(fila,1)+"");
////		        		txtImporte_Concentrado.setText (tablab.getValueAt(fila,2)+"");
////		        		txtImporte_Banco_Interno.setText (tablab.getValueAt(fila,2)+"");
////		        		txtFechaConcentrado.setText (tablab.getValueAt(fila,3)+"");
////		        		txtImporte_Banco_Interno.setEditable(true);
////		        		btnAgregar.setEnabled(true);
////		        		calculo_cheque();
////		        		dispose();
////		        		txtImporte_Banco_Interno.requestFocus();
//		        	}
//		        }
//	        });
//	    }
//		
//        private KeyListener opFiltro = new KeyListener(){
//			public void keyReleased(KeyEvent arg0) {
//				ObjTab.Obj_Filtro(tablab, txtBuscarb.getText().toUpperCase(), columnasb);
//			}
//			public void keyTyped(KeyEvent arg0) {}
//			public void keyPressed(KeyEvent arg0) {}		
//		};
//	}
	
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
//			        		String[][] tablacompleta=banco_interno.consulta_movimiento_banco(Integer.valueOf(tablab.getValueAt(fila,0)+""));
//			        	    Object[]   vectortabla = new Object[7];
//			        		for(int i=0;i<tablacompleta.length;i++){
//			        				for(int j=0;j<7;j++){
//			        				  vectortabla[j] = tablacompleta[i][j].toString();
//			        				}
//			        				modelo.addRow(vectortabla);
//			        		}
			        			 
//			        		txtFolio.setText             (tablacompleta[0][ 7].toString());
//			        		cmb_status.setSelectedItem   (tablacompleta[0][ 8].toString());
//			        		txtFolio_Beneficiario.setText(tablacompleta[0][ 9].toString());
//			        		txtBeneficiario.setText      (tablacompleta[0][10].toString());
//			        		txaObservaciones.setText     (tablacompleta[0][11].toString());
//			         		txtFoliosolicit.setText      (tablacompleta[0][12].toString());
//			        		txtSolicitante.setText       (tablacompleta[0][13].toString());
//			        		txtFecha.setText             (tablacompleta[0][14].toString());
//			        		
//			        		cmbEstablecimiento.setSelectedItem(tablacompleta[0][6].toString());
//							calculo();
//							dispose();
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
		        		
//						int fila = tablab.getSelectedRow();
//		        		String[][] tablacompleta=banco_interno.consulta_movimiento_banco(Integer.valueOf(tablab.getValueAt(fila,0)+""));
//		        	    Object[]   vectortabla = new Object[7];
//		        		for(int i=0;i<tablacompleta.length;i++){
//		        				for(int j=0;j<7;j++){
//		        				  vectortabla[j] = tablacompleta[i][j].toString();
//		        				}
//		        				modelo.addRow(vectortabla);
//		        		}
		        			 
//		        		txtFolio.setText             (tablacompleta[0][ 7].toString());
//		        		cmb_status.setSelectedItem   (tablacompleta[0][ 8].toString());
//		        		txtFolio_Beneficiario.setText(tablacompleta[0][ 9].toString());
//		        		txtBeneficiario.setText      (tablacompleta[0][10].toString());
//		        		txaObservaciones.setText     (tablacompleta[0][11].toString());
//		         		txtFoliosolicit.setText      (tablacompleta[0][12].toString());
//		        		txtSolicitante.setText       (tablacompleta[0][13].toString());
//		        		txtFecha.setText             (tablacompleta[0][14].toString());
//		        		
//		        		cmbEstablecimiento.setSelectedItem(tablacompleta[0][6].toString());
//						calculo();
//						dispose();
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
			new Cat_Banco_Interno().setVisible(true);
		}catch(Exception e){	}
	}
};
	
