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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Connexion;
import Conexiones_SQL.Generacion_Reportes;
import Conexiones_SQL.GuardarSQL;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Contabilidad.Obj_Orden_De_Gasto;
import Obj_Contabilidad.Obj_Saldo_Banco_Interno;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_tabla;

@SuppressWarnings("serial")
public class Cat_Clasificacion_De_Ordenes_De_Gasto extends JFrame {
	    String aceptar_negar="";
		Container cont = getContentPane();
		JLayeredPane campo = new JLayeredPane();
		Connexion con = new Connexion();
		Obj_tabla ObjTab= new Obj_tabla();
		Obj_Usuario usuario = new Obj_Usuario().LeerSession();
		Obj_Orden_De_Gasto gasto = new Obj_Orden_De_Gasto();
		Obj_Saldo_Banco_Interno banco_interno= new Obj_Saldo_Banco_Interno();	
		
		@SuppressWarnings("rawtypes")
		public Class[] tipos(){
			Class[] tip = new Class[Cantidad_Real_De_Columnas];
			for(int i =0; i<Cantidad_Real_De_Columnas; i++){
					tip[i]=java.lang.Object.class;
			}
			return tip;
		}
		
		int Cantidad_Real_De_Columnas=21,checkboxindex=-1;
		public void init_tabla_principal(){
			this.tabla.getColumnModel().getColumn( 0).setMinWidth(50);
			this.tabla.getColumnModel().getColumn( 0).setMaxWidth(50);
			this.tabla.getColumnModel().getColumn( 1).setMinWidth(320);
	    	this.tabla.getColumnModel().getColumn( 2).setMinWidth(80);
	    	this.tabla.getColumnModel().getColumn( 3).setMinWidth(400);
	    	this.tabla.getColumnModel().getColumn( 4).setMinWidth(160);
	    	this.tabla.getColumnModel().getColumn( 5).setMinWidth(80);
	    	this.tabla.getColumnModel().getColumn( 6).setMinWidth(250);
	    	this.tabla.getColumnModel().getColumn( 7).setMinWidth(230);
	    	this.tabla.getColumnModel().getColumn( 8).setMinWidth(120);
	    	this.tabla.getColumnModel().getColumn( 9).setMinWidth(80);
	    	this.tabla.getColumnModel().getColumn(10).setMinWidth(120);
	    	this.tabla.getColumnModel().getColumn(11).setMinWidth(200);
	    	this.tabla.getColumnModel().getColumn(12).setMinWidth(140);
	    	this.tabla.getColumnModel().getColumn(13).setMinWidth(90);
	    	this.tabla.getColumnModel().getColumn(14).setMinWidth(150);
	    	this.tabla.getColumnModel().getColumn(15).setMinWidth(100);
	    	this.tabla.getColumnModel().getColumn(16).setMinWidth(200);
	    	this.tabla.getColumnModel().getColumn(18).setMinWidth(200);
	    	this.tabla.getColumnModel().getColumn(19).setMinWidth(400);
	    	
			String comando = "orden_de_gasto_clasificacion_filtro '"+cmb_status.getSelectedItem().toString().trim()+"'";
			String basedatos="26",pintar="si";
			ObjTab.Obj_Refrescar(tabla,modelo, Cantidad_Real_De_Columnas, comando, basedatos,pintar,checkboxindex);
	    }
		
	 public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"Folio","Proveedor","Concepto","Descripcion Gasto","Establecimiento","Importe Total","Cuenta Edo. Resultados","Usuario Solicita", "Fecha","Estatus","Fecha Autorizacion","Usuario Autorizo","Tipo Provedor","Folio Servicio", "Servicio", "Tipo","Usuario Valida", "Folio Pago", "Usuario Realizo Pago", "Observaciones Del Pago", "Folio Corte Caja Chica"}){
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
	    JTable tabla = new JTable(modelo);
		public JScrollPane scroll_tabla = new JScrollPane(tabla);
		
//		String status[] = new Obj_Orden_De_Gasto().Combo_Cuentas();
		String status[] ={"	PENDIENTE", "TERMINADO"};
		@SuppressWarnings({ "unchecked", "rawtypes" })
		JComboBox cmb_status = new JComboBox(status);
		
		JTextField txtFolio      = new Componentes().text(new JCTextField()  ,"Folio"   ,30   ,"String");
		JTextField txtPedientes  = new Componentes().text(new JCTextField(), "Cant. Pendientes", 150, "String");
		JTextField txtFiltro     = new Componentes().textfiltro(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<",300 , "String",tabla,Cantidad_Real_De_Columnas );
		JTextField txtTotal      = new Componentes().text(new JCTextField()  ,"Total"                     ,30   ,"String");
		
		JCButton btnActualizar   = new JCButton("Actualizar"  ,"Actualizar.png"  ,"Azul" );	
		
		public Cat_Clasificacion_De_Ordenes_De_Gasto()	{
			int ancho = Toolkit.getDefaultToolkit().getScreenSize().width;
			int alto = Toolkit.getDefaultToolkit().getScreenSize().height;
			setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds()); 
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/memory_card.png"));
			this.setTitle("Clasificacion De Ordenes De Gasto");
			this.campo.setBorder(BorderFactory.createTitledBorder("Seleccione Las Solicitudes Que Desea Clasificar"));
            int x=15 ,y=20 ,width=115 ,height=20;

			campo.add(txtFiltro).setBounds    (x      ,y     ,400      ,height  );
			campo.add(cmb_status).setBounds   (x+=420 ,y     ,width    ,height  );
			campo.add(btnActualizar).setBounds(x+=130 ,y     ,width    ,height  );
			campo.add(scroll_tabla).setBounds (x=15   ,y+=25 , ancho-25,alto-125);
			cmb_status.setSelectedItem("AUTORIZADO");
			agregar(tabla);
			
			init_tabla_principal();			
			cont.add(campo);
			this.btnActualizar.addActionListener(OpActualizar);
			this.cmb_status.addActionListener(OpActualizar);
		}
		
	    ActionListener OpActualizar = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				init_tabla_principal();
			 }
	    };
	    
	    private void agregar(final JTable tbl) {
			tbl.addMouseListener(new MouseListener() {
				public void mouseReleased(MouseEvent e) {
			 	 if(e.getClickCount() == 1){
	    				int fila = tabla.getSelectedRow();
                     new Cat_Clasificacion_De_Orden_De_Pago_En_Concepto(Integer.valueOf(tabla.getValueAt(fila, 0).toString())).setVisible(true);
			 	 }
				}
				public void mousePressed(MouseEvent e) {}
				public void mouseExited(MouseEvent e)  {}
				public void mouseEntered(MouseEvent e) {}
				public void mouseClicked(MouseEvent e) {}
			});
			tbl.addKeyListener(new KeyListener() {
				public void keyPressed(KeyEvent e)  {
					if(e.getKeyCode()==KeyEvent.VK_ENTER){
						int fila = tabla.getSelectedRow();
	                     new Cat_Clasificacion_De_Orden_De_Pago_En_Concepto(Integer.valueOf(tabla.getValueAt(fila, 0).toString())).setVisible(true);
					}
				}
				public void keyReleased(KeyEvent e)   {}
				public void keyTyped   (KeyEvent e)   {}
			});
	    }
	    
///////////TODO  FILTRO DE  SOLICITUD SELECCIONADA
public class Cat_Clasificacion_De_Orden_De_Pago_En_Concepto extends JDialog{
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	Connexion con = new Connexion();
	JToolBar menu_toolbar       = new JToolBar();

	
	int columnasgo = 4,checkbox=-1;
	public void init_tabla(){
  	this.tablago.getColumnModel().getColumn(0).setMinWidth(500);	
  	this.tablago.getColumnModel().getColumn(1).setMinWidth(90);
  	this.tablago.getColumnModel().getColumn(2).setMinWidth(90);
  	this.tablago.getColumnModel().getColumn(3).setMinWidth(98);

  	String comando="Select '' as Descripcion, 0 P_Unitario,0 as Cantidad,0 as Importe" ;
		String basedatos="26",pintar="si";
		ObjTab.Obj_Refrescar(tablago,modelogo, columnasgo, comando, basedatos,pintar,checkbox);
		modelogo.setRowCount(0);
  }
	
	@SuppressWarnings("rawtypes")
	public Class[] basemovimientos (){
		Class[] types = new Class[columnasgo];
		for(int i = 0; i<columnasgo; i++){types[i]= java.lang.Object.class;}
		 return types;
	}
	public DefaultTableModel modelogo = new DefaultTableModel(null, new String[]{"Descripcion","Cantidad","P.Unitario","Importe"}){
		 @SuppressWarnings("rawtypes")
			Class[] types = basemovimientos();
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
			public boolean isCellEditable(int fila, int columna){return false;}
	};
	JTable tablago = new JTable(modelogo);
	public JScrollPane scroll_tabla = new JScrollPane(tablago);
	
	JTextField txtFolio           = new Componentes().text(new JCTextField()  ,"Folio Gasto"               ,30   ,"String");
	JTextField txtFolioPago       = new Componentes().text(new JCTextField()  ,"Folio Pago"                ,30   ,"String");
	
	JTextField txtFolio_prv       = new Componentes().text(new JCTextField()  ,"Folio Prv"                 ,30   ,"String");
	JTextField txtProveedor       = new Componentes().text(new JCTextField()  ,"Proveedor"                 ,250  ,"String");
	JTextField txtFoliosolicit    = new Componentes().text(new JCTextField()  ,"Folio Solicita"            ,30   ,"String");
	JTextField txtValida          = new Componentes().text(new JCTextField()  ,"Valida"                    ,30   ,"String");
	JTextField txtFechaValida     = new Componentes().text(new JCTextField()  ,"Fecha Valida"              ,30   ,"String");
	JTextField txtAutoriza        = new Componentes().text(new JCTextField()  ,"Autorizó"                  ,300  ,"String");
	JTextField txtFechaAutoriza   = new Componentes().text(new JCTextField()  ,"Fecha Autoriza"            ,30   ,"String");
	JTextField txtFecha           = new Componentes().text(new JCTextField()  ,"Fecha"                     ,60   ,"String");
	JTextField txtTotal           = new Componentes().text(new JCTextField()  ,"Total"                     ,30   ,"String");
	JTextField txtEstatus         = new Componentes().text(new JCTextField()  ,"Estatus"                   ,50   ,"String");
	JTextField txtEstablecimiento = new Componentes().text(new JCTextField()  ,"Establecimiento"           ,90   ,"String");
	JTextField txtConcepto        = new Componentes().text(new JCTextField()  ,"Concepto"                  ,90   ,"String");
	JTextField txtSolicito        = new Componentes().text(new JCTextField()  ,"Solicito"                  ,300  ,"String");
	JTextField txtFolioservici    = new Componentes().text(new JCTextField()  ,"Folio Servicio"            ,30   ,"String");
	JTextField txtDetalleServi    = new Componentes().text(new JCTextField()  ,"Detalle Servicio"          ,350  ,"String");
	JTextField txtTipo            = new Componentes().text(new JCTextField()  ,"Tipo"                      ,35   ,"String");
	JTextField txtForma_pago      = new Componentes().text(new JCTextField()  ,"Forma Pago "               ,35   ,"String");
	JTextField txtPlazo           = new Componentes().text(new JCTextField()  ,"Plazo"                     ,35   ,"String");
	JTextField txtCuenta          = new Componentes().text(new JCTextField()  ,"Cuenta Bancaria"           ,35   ,"String");
	
    JTextArea txaUso       = new Componentes().textArea(new JTextArea(), "Uso De La Mercancia", 300);
	JScrollPane Uso        = new JScrollPane(txaUso);

	JCButton btnClasificar   = new JCButton("Clasificar"              ,"memory_card16.png" ,"Azul" ); 
	JCButton btnImprimir     = new JCButton("Imprimir Orden De Gasto" ,"imprimir-16.png"   ,"Azul" );
	JCButton btnImprimirpago = new JCButton("Imprimir Orden De Pago"  ,"imprimir-16.png"   ,"Azul" );
	JCButton btnbuscarcuenta = new JCButton(""                        ,"check-vcard-icone-9025-16.png"   ,"Azul" );
	
	JRadioButton rbProveedorCont = new JRadioButton("Proveedor Contado");
	JRadioButton rbProveedor     = new JRadioButton("Proveedor");
	JRadioButton rbColaborador   = new JRadioButton("Colaborador");
	ButtonGroup  grupo           = new ButtonGroup();
	
	Border blackline, etched, raisedbevel, loweredbevel, empty;

	String[][] tablaprecargadaordenes;
    Object[] vector = new Object[7];
  
    String cuentas[] =  banco_interno.Combo_Cuentaspago();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbcuenta_bancaria = new JComboBox(cuentas);
	
	 String concepto[] = gasto.Cuentas_Gastos_Edo_Resultados();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbCuenta_Edo_Res = new JComboBox(concepto);
	
  String guardar_actualizar="";
 public  Cat_Clasificacion_De_Orden_De_Pago_En_Concepto(int folio){
	    this.cont.add(panel);
		this.setSize(825,500);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setModal(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("Validacion De Orden De Gasto");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/memory_card.png"));
		this.blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
		this.panel.setBorder(BorderFactory.createTitledBorder(blackline,"Validacion De Orden De Gasto"));

		this.menu_toolbar.add(btnClasificar  );
		this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.add(btnImprimir );
		this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.add(btnImprimirpago );
		this.menu_toolbar.setFloatable(false);
 	 
		int x=20, y=20,width=122,height=20;
		this.panel.add(menu_toolbar).setBounds                        (x         ,y      ,600     ,height );
		this.panel.add(new JLabel("Cuenta Edo.:")).setBounds          (x=20      ,y+=30  ,width   ,height );
		this.panel.add(cmbCuenta_Edo_Res).setBounds                   (x+=60     ,y      ,330     ,height );
		this.panel.add(btnbuscarcuenta).setBounds                     (x+=330    ,y      ,20      ,height );
		
		this.panel.add(new JLabel("Tipo:")).setBounds                 (x+=30     ,y      ,width   ,height );
		this.panel.add(txtTipo).setBounds                             (x+=30     ,y      ,40      ,height );	
		this.panel.add(new JLabel("Plazo:")).setBounds                (x+=50     ,y      ,width   ,height );
		this.panel.add(txtPlazo).setBounds                            (x+=30     ,y      ,42      ,height );	
		this.panel.add(new JLabel("Cuenta B.I.:")).setBounds          (x+=50     ,y      ,width   ,height );
		this.panel.add(txtCuenta).setBounds                           (x+=60     ,y      ,width   ,height );
		
		this.panel.add(new JLabel("Folio Gasto:")).setBounds          (x=20      ,y+=25  ,width   ,height );
		this.panel.add(txtFolio).setBounds                            (x+=60     ,y      ,120     ,height );
		this.panel.add(new JLabel("Folio Pago:")).setBounds           (x+=125    ,y      ,width   ,height );
		this.panel.add(txtFolioPago).setBounds                        (x+=55     ,y      ,120     ,height );
		
		this.panel.add(new JLabel("Establecimiento:")).setBounds      (x+=130    ,y      ,width   ,height );
		this.panel.add(txtEstablecimiento).setBounds                  (x+=80     ,y      ,312     ,height );
		
		this.panel.add(new JLabel("Solicita:")).setBounds             (x=20      ,y+=30  ,width   ,height );
		this.panel.add(txtSolicito).setBounds                         (x+=60     ,y      ,300     ,height );
		this.panel.add(new JLabel("Fecha Solicitud:")).setBounds      (x+=310    ,y      ,width   ,height );
		this.panel.add(txtFecha).setBounds                            (x+=80     ,y      ,130     ,height );
		this.panel.add(new JLabel("Concepto:")).setBounds             (x+=140    ,y      ,width   ,height );
		this.panel.add(txtConcepto).setBounds                         (x+=50     ,y      ,width   ,height );
		
		this.panel.add(new JLabel("Valida:")).setBounds               (x=20      ,y+=25  ,width   ,height );
		this.panel.add(txtValida).setBounds                           (x+=60     ,y      ,300     ,height );
		this.panel.add(new JLabel("Fecha Valida:")).setBounds         (x+=310    ,y      ,width   ,height );
		this.panel.add(txtFechaValida).setBounds                      (x+=80     ,y      ,130     ,height );
		this.panel.add(new JLabel("For.Pago:")).setBounds             (x+=140    ,y      ,width   ,height );
		this.panel.add(txtForma_pago).setBounds                       (x+=50     ,y      ,width   ,height );
		
		this.panel.add(new JLabel("Autoriza:")).setBounds             (x=20      ,y+=25  ,width   ,height );
		this.panel.add(txtAutoriza).setBounds                         (x+=60     ,y      ,300     ,height );
		this.panel.add(new JLabel("Fecha Autoriza:")).setBounds       (x+=310    ,y      ,width   ,height );
		this.panel.add(txtFechaAutoriza).setBounds                    (x+=80     ,y      ,130     ,height );
		this.panel.add(new JLabel("Estatus:")).setBounds              (x+=140    ,y      ,width   ,height );
		this.panel.add(txtEstatus).setBounds                          (x+=50     ,y      ,width   ,height );
		
		this.panel.add(txtFolio_prv).setBounds                        (x=20      ,y+=25  ,60      ,height );
		this.panel.add(txtProveedor).setBounds                        (x+=60     ,y      ,490     ,height );
		this.panel.add(rbProveedor).setBounds                         (x+500     ,y      ,90      ,height );		
		this.panel.add(rbProveedorCont).setBounds                     (x+600     ,y      ,130     ,height );  
		
		this.panel.add(txtFolioservici).setBounds                     (x=20      ,y+=25  ,60      ,height );	
		this.panel.add(txtDetalleServi).setBounds                     (x+=60     ,y      ,490     ,height );	
		this.panel.add(rbColaborador).setBounds                       (x+500     ,y      ,90      ,height );
		
		this.panel.add(new JLabel("Descripcion Del Gasto:")).setBounds(x=20      ,y+=20  ,width   ,height );
		this.panel.add(Uso).setBounds                                 (x         ,y+=15  ,780     ,50     );
		this.panel.add(scroll_tabla).setBounds                        (x=20      ,y+=55  ,780     ,150    );
		this.panel.add(txtFoliosolicit).setBounds                     (x         ,y+=150 ,60      ,height );		
		this.panel.add(txtTotal).setBounds                            (702       ,y      ,98      ,height );
		this.init_tabla();
		this.Buscar(folio);

		this.btnClasificar.addActionListener(opaceptar);
		this.txtFoliosolicit.setText(usuario.getFolio()+"");
		this.btnImprimir.addActionListener(opImprimir_Reporte);
		this.btnImprimirpago.addActionListener(opImprimir_Reportepago);
		btnbuscarcuenta.addActionListener(opfiltrocuenta);
		
  }
		
	public void Buscar(int folio_orden_de_gasto) {
		String[][] tablacompleta=gasto.consulta_orden_de_gasto(folio_orden_de_gasto);
	    Object[]   vectortabla = new Object[3];
		for(int i=0;i<tablacompleta.length;i++){
				for(int j=0;j<3;j++){
				  vectortabla[j] = tablacompleta[i][j].toString();
				}
				modelogo.addRow(vectortabla);
			}
		txtFolio.setText(tablacompleta[0][3].toString());
		txaUso.setText(tablacompleta[0][4].toString());
		txtEstablecimiento.setText(tablacompleta[0][6].toString());
		txtFolio_prv.setText (tablacompleta[0][7].toString());
		txtProveedor.setText (tablacompleta[0][8].toString());
		txtFoliosolicit.setText (tablacompleta[0][11].toString());
		txtFecha.setText(tablacompleta[0][12].toString());
		txtSolicito.setText (tablacompleta[0][14].toString());
		txtEstatus.setText(tablacompleta[0][15].toString());
		txtAutoriza.setText(tablacompleta[0][16].toString());
		
		txtFechaAutoriza.setText(tablacompleta[0][13].toString());
		txtConcepto.setText(tablacompleta[0][17].toString());
		
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
		txtTipo.setText(tablacompleta[0][20].toString());
		txtForma_pago.setText(tablacompleta[0][21].toString());
		txtPlazo.setText(tablacompleta[0][23].toString());
		txtValida.setText(tablacompleta[0][24].toString());
		txtFechaValida.setText(tablacompleta[0][25].toString());
		txtFolioPago.setText(tablacompleta[0][26].toString());
		txtCuenta.setText(tablacompleta[0][27].toString());
		cmbCuenta_Edo_Res.setSelectedItem(tablacompleta[0][28].toString());
		
		txtEstatus.setEditable(false);
		txtEstablecimiento.setEditable(false);
	    txtConcepto.setEditable(false);
		txaUso.setEditable(false);
		txtFolio.setEditable(false);
		txtProveedor.setEditable(false);
		rbProveedorCont.setEnabled(false);
		rbProveedor.setEnabled(false);
		rbColaborador.setEnabled(false);
		txtFecha.setEditable(false);
		txtFoliosolicit.setEditable(false);
		txtAutoriza.setEditable(false);
		txtFolio_prv.setEditable(false);
		txtTotal.setEditable(false);
		txtSolicito.setEditable(false);
		txtFolioservici.setEditable(false);
		txtDetalleServi.setEditable(false);
		txtTipo.setEditable(false);
		txtForma_pago.setEditable(false);
		txtValida.setEditable(false);
		txtFechaValida.setEditable(false);
		txtPlazo.setEditable(false);
		txtFechaAutoriza.setEditable(false);
		txtCuenta.setEditable(false);
		txtFolioPago.setEditable(false);
		calculo();

	}
	
	public void calculo() {
		float importe=0;
		for(int i=0;i<tablago.getRowCount();i++) {
			tablago.setValueAt(Float.valueOf(tablago.getValueAt(i, 1)+"") * Float.valueOf(tablago.getValueAt(i, 2)+""), i, 3);
			importe=importe+Float.valueOf(tablago.getValueAt(i, 3)+"");
		}
		txtTotal.setText(importe+"");
	};
	
	ActionListener opaceptar = new ActionListener() {
    	public void actionPerformed(ActionEvent arg0) {
    		Object[][] arreglo_guardado= new Object[tabla.getRowCount()][3];
    		for (int i=0;i<1;i++){
    			 arreglo_guardado[i][0]=txtFolioPago.getText().trim(); //folio_pago	
    	       	 arreglo_guardado[i][1]=cmbCuenta_Edo_Res.getSelectedItem().toString().trim();//cuenta edo resultados
    	         arreglo_guardado[i][2]=usuario.getFolio();//usuario_clasifica
    	    }
    		
    		if(new GuardarSQL().Clasificacion_De_Pagos_En_Efectivo_Gastos(arreglo_guardado)){
    			dispose();
    			init_tabla_principal();
    		}else{
    			JOptionPane.showMessageDialog(null, "Error Al Actualizar", "Avise al Administrador Del Sistema", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
    		}
		}
	};
	
	ActionListener opfiltrocuenta = new ActionListener() {
    	public void actionPerformed(ActionEvent arg0) {
				new Cat_Filtro_Clasificador().setVisible(true);
		}
	};
	
	public void imprimir(String tipo) {
		@SuppressWarnings("unused")
		int fila;
		fila = tabla.getSelectedRow();
		String basedatos="2.26";
		String vista_previa_reporte="no";
		int vista_previa_de_ventana=0;
		String comando="";
		String reporte ="";
		if(tipo.equals("gasto")) {
		  comando="orden_de_gasto_reporte "+txtFolio.getText().toString().trim();
		  reporte = "Obj_Reporte_De_Orden_De_Gasto.jrxml";
		}
		
		if(tipo.equals("pago")) {
		  comando="exec orden_de_gasto_reporte_de_pago_en_efectivo "+txtFolioPago.getText().toString().trim();
		  reporte = "Obj_Reporte_De_Orden_De_Gasto_Pago_En_Efectivo.jrxml";
		}
				
  	    new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
	}
	
	 ActionListener opImprimir_Reporte = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(cmb_status.getSelectedItem().toString().trim().equals("EN VALIDACION")){
					JOptionPane.showMessageDialog(null, "Para Imprimir Es Requerido Sea Validada","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
					return;
				}else {
					imprimir("gasto");
			    }
		   }
	  	};
		
	    ActionListener opImprimir_Reportepago = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(cmb_status.getSelectedItem().toString().trim().equals("EN VALIDACION")){
					JOptionPane.showMessageDialog(null, "Para Imprimir Es Requerido Sea Validada","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
					return;
				}else {
					imprimir("pago");
			    }
		   }
	  	};
	
	  //TODO Filtro de Calsificacion ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		public class Cat_Filtro_Clasificador extends JDialog{
			Container cont = getContentPane();
			JLayeredPane campo = new JLayeredPane();
	      Obj_tabla  Objetotabla = new Obj_tabla();
			
			int columnasft = 2,checkbox=-1;
			public void init_tabla(){
				String comando= "select folio_concepto,concepto_orden_de_pago,tipo_movimiento_estado_de_resultados from tb_conceptos_de_orden_de_pago where status='V' order by concepto_orden_de_pago";
		    	this.tabla_Filtro_Ref.getColumnModel().getColumn(1).setMinWidth(375);
				String basedatos="26",pintar="si";
				Objetotabla.Obj_Refrescar(tabla_Filtro_Ref,modelo_Filtro_Ref, columnasft, comando, basedatos,pintar,checkbox);
		    }
			
			DefaultTableModel modelo_Filtro_Ref = new DefaultTableModel(null,new String[]{"Folio", "Cuenta"}){
			     @SuppressWarnings("rawtypes")
				Class[] types = new Class[]{
			    	java.lang.String.class,
			    	java.lang.String.class
			    	                       };
			     @SuppressWarnings({ "rawtypes", "unchecked" })
				public Class getColumnClass(int columnIndex) {
		             return types[columnIndex];
		         }
		         public boolean isCellEditable(int fila, int columna){
		        	 switch(columna){
			        	 	case 0 : return false; 
			        	 	case 1 : return false; 
		        	 	} 				
		 			return false;
		 		}
			};
			JTable tabla_Filtro_Ref = new JTable(modelo_Filtro_Ref);
		    JScrollPane scroll_Filtro_Ref = new JScrollPane(tabla_Filtro_Ref);
			
			JTextField txtDescripcion  = new Componentes().textfiltro(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<", 500, "String",tabla_Filtro_Ref,columnasft);
			public Cat_Filtro_Clasificador(){
				this.setSize(510,650);
				this.setResizable(false);
				this.setLocationRelativeTo(null);
				this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				this.setModal(true);
				
				this.setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/filter_icon&16.png"));
				this.setTitle("Filtro De Clasificacion De La Orden De Pago");
				campo.setBorder(BorderFactory.createTitledBorder("Selecciona La Clasificacion con Click o Enter"));
				campo.add(scroll_Filtro_Ref).setBounds(15,42,470,565);
				campo.add(txtDescripcion).setBounds(15,20,470,20);
				
				init_tabla();
				agregar(tabla_Filtro_Ref);
				cont.add(campo);
	          this.addWindowListener(new WindowAdapter() {
	                  public void windowOpened( WindowEvent e ){
	                  	txtDescripcion.requestFocus();
	               }
	          });
	          
	          getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
	          getRootPane().getActionMap().put("escape", new AbstractAction(){ public void actionPerformed(ActionEvent e)  {    txtDescripcion.requestFocus();       	    }             });            
			}
			
			public Object[][] Filtro_Cuentas( ){
				try {
					return new BuscarSQL().Filtro_De_Cuentas_polizas();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return null;
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

			public void funcion_agregar() {
			   int fila = tabla_Filtro_Ref.getSelectedRow();
				cmbCuenta_Edo_Res.setSelectedItem(tabla_Filtro_Ref.getValueAt(fila, 1).toString());
				cmbCuenta_Edo_Res.requestFocus();
	 		    dispose();
		    }
		}
}
		

		public static void main(String args[]){
			try{
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				new Cat_Clasificacion_De_Ordenes_De_Gasto().setVisible(true);
			}catch(Exception e){	}
		}
	}

