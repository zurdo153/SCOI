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
	
	int columnas2 = 5,checkbox2=5;
	public void init_tabla_egresos(){
    	this.tablaegresos.getColumnModel().getColumn(1).setMinWidth(300);
    	this.tablaegresos.getColumnModel().getColumn(2).setMinWidth(150);
    	this.tablaegresos.getColumnModel().getColumn(3).setMinWidth(130);
    	this.tablaegresos.getColumnModel().getColumn(3).setMaxWidth(130);
    	this.tablaegresos.getColumnModel().getColumn(4).setMinWidth(20);
    	this.tablaegresos.getColumnModel().getColumn(4).setMaxWidth(20);
    	
    	String comando="banco_interno_relacion_de_cortes_de_caja_chica_para_reposicion_de_efectivo " ;
		String basedatos="26",pintar="si";
		ObjTab.Obj_Refrescar(tablaegresos,modelo_egresos, columnas2, comando, basedatos,pintar,checkbox2);
    }
	
	@SuppressWarnings("rawtypes")
	public Class[] basemovimientos_egresos (){
		Class[] types = new Class[columnas2];
		for(int i = 0; i<columnas2; i++){
		if(i==4) {types[i]=java.lang.Boolean.class;}else {types[i]= java.lang.Object.class;  }
    	
		}
		 return types;
	}
	public DefaultTableModel modelo_egresos = new DefaultTableModel(null, new String[]{"Folio Corte","Usuario Realizo Corte","Fecha","Cantidad",""}){
		 @SuppressWarnings("rawtypes")
			Class[] types = basemovimientos_egresos();
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
			public boolean isCellEditable(int fila, int columna){  if(columna==4) {return true;}else { return false;}}
	};
	JTable tablaegresos = new JTable(modelo_egresos);
	public JScrollPane scroll_tablaegresos = new JScrollPane(tablaegresos);
	
	JTextField txtFolio             = new Componentes().text(new JCTextField()  ,"Folio"                        ,30    ,"Int");
	JTextField txtFolio_impresion   = new Componentes().text(new JCTextField()  ,"Teclee El Folio Para Imprimir",30    ,"Int");
	JTextField txtFoliosolicit      = new Componentes().text(new JCTextField()  ,"Folio Solicita"               ,30    ,"Int");
	JTextField txtSolicitante       = new Componentes().text(new JCTextField()  ,"Solicitante"                  ,300   ,"String");
	JTextField txtFecha             = new Componentes().text(new JCTextField()  ,"Fecha"                        ,60    ,"String");
	JTextField txtSaldo_Actual      = new Componentes().text(new JCTextField()  ,"Saldo"                        ,60    ,"Double");
	JTextField txttotalImporte_BI   = new Componentes().text(new JCTextField()  ,"Importe Total"                ,30    ,"Double");
	JTextField txtSaldoNuevo        = new Componentes().text(new JCTextField()  ,"Saldo Nuevo"                  ,30    ,"Double");
	JTextField txtFolio_Beneficiario= new Componentes().text(new JCTextField()  ,"Folio B"                      ,30   ,"String");
	JTextField txtBeneficiario      = new Componentes().text(new JCTextField()  ,"Beneficiario"                 ,250  ,"String");
	
    JTextArea txaObservaciones      = new Componentes().textArea(new JTextArea(), "Observaciones", 160);
    JScrollPane Observaciones       = new JScrollPane(txaObservaciones);

	JCButton btnIngreso    = new JCButton("Recibir"      ,"ingresos_32.png"                               ,"Azul");
	JCButton btnEgreso     = new JCButton("Transferir"   ,"Egreso32.png"                                  ,"Azul"); 
	JCButton btnEgresoDlsVa= new JCButton("Egreso Dls y Vales" ,"Egreso32.png"                            ,"Azul"); 
	JCButton btnReporte    = new JCButton("Reportes"     ,"comprobar-la-lista-de-tareas-icono-7647-32.png","Azul");
	JCButton btnSolicitante= new JCButton(""             ,"Usuario.png"                                   ,"Azul");	
	JCButton btnBuscar     = new JCButton("Buscar"       ,"Filter-List-icon16.png"                        ,"Azul"); 
	JCButton btnNuevo      = new JCButton("Nuevo"        ,"Nuevo.png"                                     ,"Azul");
	JCButton btnGuardar    = new JCButton("Guardar"      ,"Guardar.png"                                   ,"Azul");
	JCButton btnDeshacer   = new JCButton("Deshacer"     ,"deshacer16.png"                                ,"Azul");
	JCButton btnImprimir   = new JCButton("Imprimir"     ,"imprimir-16.png"                               ,"Azul");
	JCButton btnMenuPrincip= new JCButton("Menu"         ,"folder-home-home-icone-5663-16.png"            ,"Azul");
	
	String status[] = {"TRASFERIDO","RECIBIDO","CANCELADO"};
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmb_status = new JComboBox(status);
	
	String cuentas[] =  banco_interno.Combo_Cuentas("cuentas");
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbcuenta_bancaria = new JComboBox(cuentas);
	
	Border blackline, etched, raisedbevel, loweredbevel, empty;

    String guardar_actualizar="", tipo_movimiento="";
   public  Cat_Banco_Interno(){
	    this.cont.add(panel);
		this.setSize(250,310);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("Banco Interno");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/bank128.png"));
		this.blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
		this.panel.setBorder(BorderFactory.createTitledBorder(blackline,"Seleccione La Opcion Deseada"));
		int x=30,y=35;
		this.panel.add(btnIngreso).setBounds    (x, y     , 180, 34 );
		this.panel.add(btnEgreso).setBounds     (x, y+=60 , 180, 34 );
		this.panel.add(btnEgresoDlsVa).setBounds(x, y+=60 , 180, 34 );
		this.panel.add(btnReporte).setBounds    (x, y+=60 , 180, 34 );
		
		btnIngreso.addActionListener(opSeleccion);
		btnEgreso.addActionListener (opSeleccion);
		btnEgresoDlsVa.addActionListener(opSeleccion);
		btnReporte.addActionListener(opReportes);
		
    }
   
   @SuppressWarnings("unchecked")
public void constructor_Ingreso(String tipo) {
	        this.cont.add(panel);
	 		this.setResizable(false);
	 		this.setLocationRelativeTo(null);
	 		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	 		this.btnIngreso.setVisible(false);
	 		this.btnEgreso.setVisible(false);
	 		this.btnEgresoDlsVa.setVisible(false);
	 		
	 		this.menu_toolbar.add(btnNuevo      );
			this.menu_toolbar.addSeparator(     );
			this.menu_toolbar.addSeparator(     );
			this.menu_toolbar.add(btnDeshacer   );
			this.menu_toolbar.addSeparator(     );
			this.menu_toolbar.addSeparator(     );
			this.menu_toolbar.add(btnBuscar     );
			this.menu_toolbar.addSeparator(     );
			this.menu_toolbar.addSeparator(     );
			this.menu_toolbar.add(btnGuardar    );
			this.menu_toolbar.addSeparator(     );
	 		this.menu_toolbar.addSeparator(     );
	 		this.menu_toolbar.add(btnImprimir   );
			this.menu_toolbar.addSeparator(     );
	 		this.menu_toolbar.addSeparator(     );
	 		this.menu_toolbar.add(btnMenuPrincip);
	 		this.menu_toolbar.setFloatable(false);
	 		
	 		this.panel_booleano(false);
	 		this.txtFecha.setEditable(false);
	 		this.txtFoliosolicit.setEditable(false);
	 		this.txtSolicitante.setEditable(false);
	 		this.txttotalImporte_BI.setEditable(false); 
	 		this.txtSaldo_Actual.setEditable(false);
	 		this.txtSaldoNuevo.setEditable(false);
	 		
			try { txtFecha.setText(new BuscarSQL().fecha(0).toString());} catch (SQLException e1) {e1.printStackTrace();}
	 		txtSolicitante.setText(usuario.getNombre_completo());
	 		txtFoliosolicit.setText(usuario.getFolio()+"");
	 		
	 	    btnMenuPrincip.addActionListener    (opMenu_Principal          );
	 		btnImprimir.addActionListener       (opImprimir_Reporte        );  
	 		btnBuscar.addActionListener         (opFiltroBuscar_orden_pago );
	 		
		 	btnDeshacer.setToolTipText          ("<ESC> Tecla Directa");
		 	btnGuardar.setToolTipText           ("<CTRL+G> Tecla Directa");
	 		
	        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
	           getRootPane().getActionMap().put("escape", new AbstractAction(){public void actionPerformed(ActionEvent e){  btnDeshacer.doClick(); } });

	        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_G,Event.CTRL_MASK),"guardar");
	           getRootPane().getActionMap().put("guardar", new AbstractAction(){public void actionPerformed(ActionEvent e){ btnGuardar.doClick(); } });

	 	    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0), "guardar");
	 	      getRootPane().getActionMap().put("guardar", new AbstractAction(){public void actionPerformed(ActionEvent e){ btnGuardar.doClick(); } });
	 	    
	 		if(tipo.equals("Transferir")){
	 			this.setSize(745,480);
	 			this.cmb_status.setSelectedItem("RECIBIDO");
		 		this.setTitle("Transferencia A Caja Chica De Banco Interno");
		 		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Egreso32.png"));
		 		this.blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
		 		this.panel.setBorder(BorderFactory.createTitledBorder(blackline,"Transferencia a Caja Chica De Banco Interno"));
		 		
	 			int x=20, y=20,width=100,height=20, sep=110;
		 		this.panel.add(menu_toolbar).setBounds                          (x         ,y      ,500     ,height );
		 		this.panel.add(txtFolio_impresion).setBounds                    (x+530     ,y      ,170     ,height );
		 		this.panel.add(txtFolio).setBounds                              (x         ,y+=27  ,width   ,height );
		 		this.panel.add(cmb_status).setBounds                            (x+=sep    ,y      ,width   ,height );
		 		this.panel.add(txtFoliosolicit).setBounds                       (x+=sep    ,y      ,60      ,height );		
		 		this.panel.add(txtSolicitante).setBounds                        (x+=60     ,y      ,290     ,height );
		 		this.panel.add(txtFecha).setBounds                              (x+290     ,y      ,130     ,height );
		 		this.panel.add(cmbcuenta_bancaria).setBounds                    (x=20      ,y+=27  ,130     ,height );
		 		this.panel.add(new JLabel("Saldo Actual:")).setBounds           (x+=135    ,y      ,170     ,height );		 		
		 		this.panel.add(txtSaldo_Actual).setBounds                       (x+=65     ,y      ,97      ,height );
		 		this.panel.add(new JLabel("Importe Total A Transferir:")).setBounds(x+=105 ,y      ,170     ,height );
		 		this.panel.add(txttotalImporte_BI).setBounds                    (x+=130    ,y      ,95      ,height );
		 		this.panel.add(new JLabel("Saldo Nuevo:")).setBounds            (x+=100    ,y      ,170     ,height );
		 		this.panel.add(txtSaldoNuevo).setBounds                         (x+65      ,y      ,97      ,height );
		 		
		 		this.panel.add(scroll_tablaegresos).setBounds                   (x=20      ,y+=27  ,700     ,270    );
		 		this.panel.add(new JLabel("Observaciones:")).setBounds          (x=20      ,y+=270 ,width   ,height );
		 		this.panel.add(Observaciones).setBounds                         (x         ,y+=15  ,700     ,40     );
		 		
		 		cmbcuenta_bancaria.removeAllItems();
		 		String cuentas[] =  banco_interno.Combo_Cuentas("reposicioncajachica");
		 	    for(int i=0;i<cuentas.length;i++){
		 	    	cmbcuenta_bancaria.addItem(cuentas[i].toString().trim());
		 		  }
		 	    
		 		init_tabla_egresos();
		 		tablaegresos.addMouseListener(opAgregarCalculoClickEgreso);
		 		tablaegresos.setEnabled(false);
		 		btnNuevo.addActionListener(nuevo_egreso);
		 		btnGuardar.addActionListener(guardar_egreso);
		 		btnDeshacer.addActionListener(deshacer_egreso);
		 		cmbcuenta_bancaria.addActionListener(opSeleccionCuenta_egreso);
		 		tipo_movimiento="E";
	 		}
	 		
	 		if(tipo.equals("Recibir")){
	 			this.setSize(745,480);
	 			this.cmb_status.setSelectedItem("RECIBIDO");
		 		this.setTitle("Recepcion De Ingreso a Banco Interno");
		 		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/ingresos_32.png"));
		 		this.blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
		 		this.panel.setBorder(BorderFactory.createTitledBorder(blackline,"Recepcion De Ingreso A Banco Interno"));
		    	 
		 		int x=20, y=20,width=100,height=20, sep=110;
		 		this.panel.add(menu_toolbar).setBounds                          (x         ,y      ,500     ,height );
		 		this.panel.add(txtFolio_impresion).setBounds                    (x+530     ,y      ,170     ,height );
		 		this.panel.add(txtFolio).setBounds                              (x         ,y+=27  ,width   ,height );
		 		this.panel.add(cmb_status).setBounds                            (x+=sep    ,y      ,width   ,height );
		 		this.panel.add(txtFoliosolicit).setBounds                       (x+=sep    ,y      ,60      ,height );		
		 		this.panel.add(txtSolicitante).setBounds                        (x+=60     ,y      ,290     ,height );
		 		this.panel.add(txtFecha).setBounds                              (x+290     ,y      ,130     ,height );
		 		this.panel.add(cmbcuenta_bancaria).setBounds                    (x=20      ,y+=27  ,130     ,height );
		 		this.panel.add(new JLabel("Saldo Actual:")).setBounds           (x+=140    ,y      ,170     ,height );		 		
		 		this.panel.add(txtSaldo_Actual).setBounds                       (x+=65     ,y      ,width   ,height );
		 		this.panel.add(new JLabel("Importe Total A Recibir:")).setBounds(x+=110    ,y      ,170     ,height );
		 		this.panel.add(txttotalImporte_BI).setBounds                    (x+140     ,y      ,width   ,height );
		 		this.panel.add(scroll_tabla).setBounds                          (x =20     ,y+=27  ,700     ,270    );
		 		this.panel.add(new JLabel("Observaciones:")).setBounds          (x=20      ,y+=270 ,width   ,height );
		 		this.panel.add(Observaciones).setBounds                         (x         ,y+=15  ,700     ,40     );
				
		 		init_tabla();
		 		tabla.setEnabled(false);

		 		tabla.addMouseListener              (opAgregarCalculoClick      );
		 		btnNuevo.addActionListener          (nuevo                      );
		 		btnDeshacer.addActionListener       (deshacer                   );
		 		btnGuardar.addActionListener        (guardar                    );
		 		cmbcuenta_bancaria.addActionListener(opSeleccionCuenta          );
		 		tipo_movimiento="T";
	 		}
	 		
	 		
	 		if(tipo.equals("Egreso Dls y Vales")){
	 			this.setSize(745,230);
	 			this.cmb_status.setSelectedItem("RECIBIDO");
		 		this.setTitle("Transferencia De Dolares y Vales");
		 		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Egreso32.png"));
		 		this.blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
		 		this.panel.setBorder(BorderFactory.createTitledBorder(blackline,"Transferencia De Dolares y Vales"));
		 		
	 			int x=20, y=20,width=100,height=20, sep=110;
		 		this.panel.add(menu_toolbar).setBounds                          (x         ,y      ,500     ,height );
		 		this.panel.add(txtFolio_impresion).setBounds                    (x+530     ,y      ,170     ,height );
		 		this.panel.add(txtFolio).setBounds                              (x         ,y+=27  ,width   ,height );
		 		this.panel.add(cmb_status).setBounds                            (x+=sep    ,y      ,width   ,height );
		 		this.panel.add(txtFoliosolicit).setBounds                       (x+=sep    ,y      ,60      ,height );		
		 		this.panel.add(txtSolicitante).setBounds                        (x+=60     ,y      ,290     ,height );
		 		this.panel.add(txtFecha).setBounds                              (x+290     ,y      ,130     ,height );
		 		
		 		this.panel.add(new JLabel("Persona A La Que Se Trasfiere:")).setBounds(x=20,y+=27  ,170     ,height );
				this.panel.add(txtFolio_Beneficiario).setBounds                 (x+=155    ,y      ,80      ,height );
				this.panel.add(txtBeneficiario).setBounds                       (x+=80     ,y      ,420     ,height );
				this.panel.add(btnSolicitante).setBounds                        (x+=420    ,y      ,45      ,height );
        		
		 		this.panel.add(cmbcuenta_bancaria).setBounds                    (x=20      ,y+=27  ,130     ,height );
		 		this.panel.add(new JLabel("Saldo Actual:")).setBounds           (x+=135    ,y      ,170     ,height );		 		
		 		this.panel.add(txtSaldo_Actual).setBounds                       (x+=65     ,y      ,97      ,height );
		 		this.panel.add(new JLabel("Importe Total A Transferir:")).setBounds(x+=105 ,y      ,170     ,height );
		 		this.panel.add(txttotalImporte_BI).setBounds                    (x+=130    ,y      ,95      ,height );
		 		this.panel.add(new JLabel("Saldo Nuevo:")).setBounds            (x+=100    ,y      ,170     ,height );
		 		this.panel.add(txtSaldoNuevo).setBounds                         (x+65      ,y      ,97      ,height );
		 		
		 		this.panel.add(new JLabel("Observaciones:")).setBounds          (x=20      ,y+=27  ,width   ,height );
		 		this.panel.add(Observaciones).setBounds                         (x         ,y+=15  ,700     ,40     );
		 		
		 		txtFolio_Beneficiario.setEditable(false);
		 		txtBeneficiario.setEditable(false);
		 		btnSolicitante.setEnabled(false);
		 		
		 		cmbcuenta_bancaria.removeAllItems();
		 		String cuentas[] =  banco_interno.Combo_Cuentas("valedola");
		 	    for(int i=0;i<cuentas.length;i++){
		 	    	cmbcuenta_bancaria.addItem(cuentas[i].toString().trim());
		 		  }
		 	    
		 		btnNuevo.addActionListener(nuevo_egresovaledolares);
		 		btnGuardar.addActionListener(guardar_valedolares);
		 		btnDeshacer.addActionListener(deshacer_egreso);
		 		cmbcuenta_bancaria.addActionListener(opSeleccionCuenta_ValeDolares);
		 		btnSolicitante.addActionListener(opFiltroBuscarSolicitante);
		 		
		 		txttotalImporte_BI.addKeyListener(valorEgreso);
		 		tipo_movimiento="D";
	 		}
	 		
	 		
	 		
   }
   
   KeyListener valorEgreso = new KeyListener() {
		public void keyTyped(KeyEvent e){
		 txtSaldoNuevo.setText( (Double.valueOf(txtSaldo_Actual.getText().toString())-Double.valueOf(txttotalImporte_BI.getText().toString().equals("")?"0":txttotalImporte_BI.getText().toString() ) )+"");
		}
		public void keyReleased(KeyEvent e) {
		 txtSaldoNuevo.setText( (Double.valueOf(txtSaldo_Actual.getText().toString())-Double.valueOf(txttotalImporte_BI.getText().toString().equals("")?"0":txttotalImporte_BI.getText().toString() ) )+"");
		}
		public void keyPressed(KeyEvent e) {
		 txtSaldoNuevo.setText( (Double.valueOf(txtSaldo_Actual.getText().toString())-Double.valueOf(txttotalImporte_BI.getText().toString().equals("")?"0":txttotalImporte_BI.getText().toString() ) )+"");
		}
	};
		
    ActionListener opReportes = new ActionListener(){
		public void actionPerformed(ActionEvent e){
					new Cat_Reportes_De_Movimientos_De_Banco_Interno().setVisible(true);
			}
	};	
	   
   ActionListener opFiltroBuscarSolicitante = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				new Cat_Filtro_Buscar_Colaborador().setVisible(true);
			}
   };	
		
   ActionListener opSeleccion = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			constructor_Ingreso(e.getActionCommand());
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
	
	ActionListener nuevo_egreso = new ActionListener(){
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
				cmbcuenta_bancaria.setEnabled(true);
				cmbcuenta_bancaria.requestFocus();
				cmbcuenta_bancaria.showPopup();
		 		tablaegresos.setEnabled(true);
		 		float saldo=new BuscarSQL().saldo_banco_interno_por_cuenta(cmbcuenta_bancaria.getSelectedItem().toString().trim());
		 		txtSaldo_Actual.setText(saldo+"");
			}
	};
	
	ActionListener nuevo_egresovaledolares = new ActionListener(){
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
			cmbcuenta_bancaria.setEnabled(true);
	 		float saldo=new BuscarSQL().saldo_banco_interno_por_cuenta(cmbcuenta_bancaria.getSelectedItem().toString().trim());
	 		txtSaldo_Actual.setText(saldo+"");
	 		
	 		btnSolicitante.setEnabled(true);
	 		btnSolicitante.doClick();
		}
   };

	ActionListener opSeleccionCuenta = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			refrescar();
	 		float saldo=new BuscarSQL().saldo_banco_interno_por_cuenta(cmbcuenta_bancaria.getSelectedItem().toString().trim());
	 		txtSaldo_Actual.setText(saldo+"");
			tabla.setEnabled(true);
		}
	};
   
	ActionListener opSeleccionCuenta_egreso = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			float saldo=new BuscarSQL().saldo_banco_interno_por_cuenta(cmbcuenta_bancaria.getSelectedItem().toString().trim());
	 		txtSaldo_Actual.setText(saldo+"");
			tablaegresos.setEnabled(true);
		}
	};
	
	ActionListener opSeleccionCuenta_ValeDolares = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			float saldo=new BuscarSQL().saldo_banco_interno_por_cuenta(cmbcuenta_bancaria.getSelectedItem().toString().trim());
	 		txtSaldo_Actual.setText(saldo+"");
	 		txttotalImporte_BI.setText("");
	 		txtSaldoNuevo.setText("");	 		
	 		txttotalImporte_BI.setEditable(true); 
	 		txttotalImporte_BI.requestFocus();
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

	ActionListener deshacer_egreso = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(guardar_actualizar.equals("N")){
				if(JOptionPane.showConfirmDialog(null, "Hay Datos Capturados y No Han Sido Guardados, ¿Desea Borrar Todo?", "Aviso", JOptionPane.INFORMATION_MESSAGE,0, new ImageIcon("Imagen/usuario-icono-noes_usuario9131-64.png") )== 0){
					panel_limpiar();
					panel_booleano(false);
					btnNuevo.setEnabled(true);
					txtFolio_impresion.setEditable(true);
					btnImprimir.setEnabled(true);
					init_tabla_egresos();
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
			init_tabla_egresos();
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
		public void mouseExited(MouseEvent e)  {calculo();}
		@Override
		public void mouseEntered(MouseEvent e) {}
		@Override
		public void mouseClicked(MouseEvent e) {calculo();}
	};
	
	MouseListener opAgregarCalculoClickEgreso = new MouseListener() {
		@Override
		public void mouseReleased(MouseEvent e){calculoegreso();}
		@Override
		public void mousePressed(MouseEvent e) {calculoegreso();}
		@Override
		public void mouseExited(MouseEvent e)  {calculoegreso();}
		@Override
		public void mouseEntered(MouseEvent e) {}
		@Override
		public void mouseClicked(MouseEvent e) {calculoegreso();}
	};
	
    public void panel_booleano(boolean boleano){
		cmb_status.setEnabled(boleano);
		cmbcuenta_bancaria.setEnabled(boleano);
		txaObservaciones.setLineWrap(true); 
		txaObservaciones.setWrapStyleWord(true);
		txaObservaciones.setEditable(boleano);
		txttotalImporte_BI.setEditable(false);
		txtFolio.setEditable(false);
		btnGuardar.setEnabled(boleano);
    }
   
    public void panel_limpiar(){
		btnBuscar.setEnabled(true);
		btnSolicitante.setEnabled(false);
		cmbcuenta_bancaria.setSelectedIndex(0);;
	    txttotalImporte_BI.setText(""); 
		txtFolio_impresion.setText("");
		txtSaldo_Actual.setText("");
		txtFolio.setText("");
		txaObservaciones.setText("");
		modelo.setRowCount(0);
		try { txtFecha.setText(new BuscarSQL().fecha(0).toString());} catch (SQLException e1) {e1.printStackTrace();}
		txtSolicitante.setText(usuario.getNombre_completo());
		txtFoliosolicit.setText(usuario.getFolio()+"");
		txtBeneficiario.setText("");
		txtFolio_Beneficiario.setText("");
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
	
	public void calculoegreso() {
		float importe=0;
		float saldoactual=0;
		
		if(!txtSaldo_Actual.getText().toString().equals("")) {
		  saldoactual= Float.valueOf(txtSaldo_Actual.getText().toString());
		}
		
		for(int i=0;i<tablaegresos.getRowCount();i++) {
			if(tablaegresos.getValueAt(i, 4).toString().equals("true")) {
			importe=importe+Float.valueOf(tablaegresos.getValueAt(i, 3)+"");
			}
		}
		txttotalImporte_BI.setText(importe+"");
		txtSaldoNuevo.setText(saldoactual-importe+"");
		
	};
    
    ActionListener opFiltroBuscar_orden_pago = new ActionListener(){
 		public void actionPerformed(ActionEvent e){
 			new Cat_Filtro_Buscar_Orden_De_Gasto().setVisible(true);
 		}
 	};
 	
 	ActionListener opMenu_Principal = new ActionListener(){
 		public void actionPerformed(ActionEvent e){
 			dispose();
 			new Cat_Banco_Interno().setVisible(true);
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
	  			String comando="banco_interno_reporte_de_movimiento_de_saldo "+folio+",'"+tipo_movimiento+"'"     ;
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

	ActionListener guardar_egreso = new ActionListener(){
	public void actionPerformed(ActionEvent e){
			 if(txttotalImporte_BI.getText().equals("")||Float.valueOf(txttotalImporte_BI.getText())==0){
				 JOptionPane.showMessageDialog(null, "Es Requerido Seleccione Registros Para Poder Guardar","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
				 return;
			 }else{	
				   calculoegreso();
				   String[][] tabla_guardado = ObjTab.tabla_guardar(tablaegresos);
				   
				   banco_interno.setFolio(Integer.valueOf(txtFolio.getText().toString().trim()));
				   banco_interno.setFolio_usuario(Integer.valueOf(txtFoliosolicit.getText().toString().trim()));
				   banco_interno.setObservaciones(txaObservaciones.getText().toString().trim());				   
	               banco_interno.setCuenta(cmbcuenta_bancaria.getSelectedItem().toString().trim());
	               banco_interno.setEstatus(cmb_status.getSelectedItem().toString().trim());
	               banco_interno.setGuardar_actualizar(guardar_actualizar);
	               banco_interno.setTipo_movimiento("Egreso");
				   banco_interno.setImporte(Float.valueOf(txttotalImporte_BI.getText().toString().trim()));			   
				   
				   banco_interno.setTabla(tabla_guardado);
				   
				if(banco_interno.GuardarActualizar_Egreso().getFolio()>0){
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
	
	ActionListener guardar_valedolares = new ActionListener(){
	@SuppressWarnings("null")
	public void actionPerformed(ActionEvent e){
			 if(txttotalImporte_BI.getText().equals("")||Float.valueOf(txttotalImporte_BI.getText())==0){
				 JOptionPane.showMessageDialog(null, "Es Requerido Seleccione Registros Para Poder Guardar","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
				 return;
			 }else{	
				   String[][] tabla_guardado = {{ "0","","",txttotalImporte_BI.getText().toString().trim(),"true"}};
				   banco_interno.setFolio(Integer.valueOf(txtFolio.getText().toString().trim()));
				   banco_interno.setFolio_usuario(Integer.valueOf(txtFolio_Beneficiario.getText().toString().trim()));				   
				   banco_interno.setObservaciones(txaObservaciones.getText().toString().trim());				
				   banco_interno.setCuenta(cmbcuenta_bancaria.getSelectedItem().toString().trim());			   
                   banco_interno.setEstatus(cmb_status.getSelectedItem().toString().trim());
				   banco_interno.setGuardar_actualizar(guardar_actualizar);
				   banco_interno.setImporte(Float.valueOf(txttotalImporte_BI.getText().toString().trim()));
 				   banco_interno.setTabla(tabla_guardado);
 				   banco_interno.setTipo_movimiento("DolaresVale");
				if(banco_interno.GuardarActualizar_Egreso().getFolio()>0){
					guardar_actualizar="";
					txtFolio.setText(banco_interno.getFolio()+"");
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
	
	//TODO inicia filtro_Buscar traspaso
		public class Cat_Filtro_Buscar_Orden_De_Gasto extends JDialog{
			Container contfb = getContentPane();
			JLayeredPane panelfb = new JLayeredPane();
			Connexion con = new Connexion();
			Obj_tabla ObjTab =new Obj_tabla();
			int columnasb = 9,checkbox=-1;
			public void init_tablafp(){
		    	this.tablab.getColumnModel().getColumn( 0).setMinWidth(50);
		    	this.tablab.getColumnModel().getColumn( 0).setMaxWidth(50);
		    	this.tablab.getColumnModel().getColumn( 1).setMinWidth(60);
		    	this.tablab.getColumnModel().getColumn( 2).setMinWidth(100);
		    	this.tablab.getColumnModel().getColumn( 3).setMinWidth(90);
		    	this.tablab.getColumnModel().getColumn( 4).setMinWidth(230);
		    	this.tablab.getColumnModel().getColumn( 5).setMinWidth(120);
		    	this.tablab.getColumnModel().getColumn( 6).setMinWidth(100);
		    	this.tablab.getColumnModel().getColumn( 7).setMinWidth(70);
		    	this.tablab.getColumnModel().getColumn( 8).setMinWidth(60);
				String comandob = "banco_interno_filtro_de_movientos_de_saldo";
		    	String basedatos="26",pintar="si";
				ObjTab.Obj_Refrescar(tablab,modelob, columnasb, comandob, basedatos,pintar,checkbox);
		    }
			
			@SuppressWarnings("rawtypes")
			public Class[] base (){
				Class[] types = new Class[columnasb];
				for(int i = 0; i<columnasb; i++){types[i]= java.lang.Object.class;}
				 return types;
			}
			
			public DefaultTableModel modelob = new DefaultTableModel(null, new String[]{"Folio" ,"Fecha" ,"Importe Ingreso" ,"Importe Egreso" ,"Usuario" ,"Nombre De Cuenta"  ,"Tipo Movimiento"  ,"Saldo" ,"Estatus"}){
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
							txtFolio_impresion.setText(tablab.getValueAt(fila, 0).toString());
							dispose();
							btnImprimir.doClick();
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
						txtFolio_impresion.setText(tablab.getValueAt(fila, 0).toString());
						dispose();
						btnImprimir.doClick();
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
	
