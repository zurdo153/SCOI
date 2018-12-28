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

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
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
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import Conexiones_SQL.Connexion;
import Conexiones_SQL.Generacion_Reportes;
import Conexiones_SQL.GuardarSQL;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Contabilidad.Obj_Orden_De_Gasto;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_tabla;

@SuppressWarnings("serial")
public class Cat_Validacion_De_Ordenes_De_Gasto extends JFrame {
	    String aceptar_negar="";
		Container cont = getContentPane();
		JLayeredPane campo = new JLayeredPane();
		Connexion con = new Connexion();
		Obj_tabla ObjTab= new Obj_tabla();
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
		
		int Cantidad_Real_De_Columnas=21,checkboxindex=1;
		public void init_tabla_principal(){
			this.tabla.getColumnModel().getColumn( 0).setMinWidth(20);
			this.tabla.getColumnModel().getColumn( 0).setMaxWidth(20);
			this.tabla.getColumnModel().getColumn( 1).setMinWidth(50);
	    	this.tabla.getColumnModel().getColumn( 1).setMaxWidth(50);
	    	this.tabla.getColumnModel().getColumn( 2).setMinWidth(330);
	    	this.tabla.getColumnModel().getColumn( 3).setMinWidth(60);
	    	this.tabla.getColumnModel().getColumn( 4).setMinWidth(400);
	    	this.tabla.getColumnModel().getColumn( 5).setMinWidth(110);
	    	this.tabla.getColumnModel().getColumn( 6).setMinWidth(80);
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
	    	
			String comando = "orden_de_gasto_autorizacion_filtro '"+cmb_status.getSelectedItem().toString().trim()+"'";
			String basedatos="26",pintar="si";
			ObjTab.Obj_Refrescar(tabla,modelo, Cantidad_Real_De_Columnas, comando, basedatos,pintar,checkboxindex);
	    }
		
	 public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"S","Folio","Proveedor","Concepto","Descripcion Gasto","Establecimiento","Importe Total","Usuario Solicita", "Fecha","Estatus","Fecha Autorizacion","Usuario Autorizo","Tipo Provedor","Folio Servicio", "Servicio", "Tipo","Usuario Valida", "Folio Pago", "Usuario Realizo Pago", "Observaciones Del Pago", "Folio Corte Caja Chica"}){
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
		String status[] ={"EN VALIDACION","PENDIENTE"};
		@SuppressWarnings({ "unchecked", "rawtypes" })
		JComboBox cmb_status = new JComboBox(status);
		
		JTextField txtFolio      = new Componentes().text(new JCTextField()  ,"Folio"   ,30   ,"String");
		JTextField txtPedientes  = new Componentes().text(new JCTextField(), "Cant. Pendientes", 150, "String");
		JTextField txtFiltro     = new Componentes().textfiltro(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<",300 , "String",tabla,Cantidad_Real_De_Columnas );
		JTextField txtTotal      = new Componentes().text(new JCTextField()  ,"Total"                     ,30   ,"String");
		
		JCButton btnActualizar   = new JCButton("Actualizar"  ,"Actualizar.png"  ,"Azul" );	
		
		public Cat_Validacion_De_Ordenes_De_Gasto()	{
			int ancho = Toolkit.getDefaultToolkit().getScreenSize().width;
			int alto = Toolkit.getDefaultToolkit().getScreenSize().height;
			setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds()); 
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Dialog-stop-hand64.png"));
			this.setTitle("Validación De Ordenes De Gasto");
			this.campo.setBorder(BorderFactory.createTitledBorder("Seleccione Las Solicitudes Que Desea Aceptar o Negar"));
            int x=15 ,y=20 ,width=115 ,height=20;

			campo.add(txtFiltro).setBounds    (x      ,y     ,400      ,height  );
			campo.add(cmb_status).setBounds   (x+=420 ,y     ,width    ,height  );
			campo.add(btnActualizar).setBounds(x+=130 ,y     ,width    ,height  );
			campo.add(scroll_tabla).setBounds (x=15   ,y+=25 , ancho-25,alto-125);
			cmb_status.setSelectedItem("EN VALIDACION");
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
                     new Cat_Solicitud_De_Orden_De_Gasto_Validacion(Integer.valueOf(tabla.getValueAt(fila, 1).toString())).setVisible(true);
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
				
					System.out.println(tabla.getSelectedRow());
						int fila = tabla.getSelectedRow();
						
	                     new Cat_Solicitud_De_Orden_De_Gasto_Validacion(Integer.valueOf(tabla.getValueAt(fila, 1).toString())).setVisible(true);
					}
				}
				public void keyReleased(KeyEvent e)   {}
				public void keyTyped   (KeyEvent e)   {}
			});
	    }
	    
///////////TODO  FILTRO DE  SOLICITUD SELECCIONADA
public class Cat_Solicitud_De_Orden_De_Gasto_Validacion extends JDialog{
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	Connexion con = new Connexion();
	JToolBar menu_toolbar       = new JToolBar();
	Obj_Usuario usuario = new Obj_Usuario().LeerSession();
	Obj_tabla  ObjTab = new Obj_tabla();
	Obj_Orden_De_Gasto gasto = new Obj_Orden_De_Gasto();

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
	
	JTextField txtFolio           = new Componentes().text(new JCTextField()  ,"Folio"                     ,30   ,"String");
	JTextField txtFolio_prv       = new Componentes().text(new JCTextField()  ,"Folio Prv"                 ,30   ,"String");
	JTextField txtProveedor       = new Componentes().text(new JCTextField()  ,"Proveedor"                 ,250  ,"String");
	JTextField txtFoliosolicit    = new Componentes().text(new JCTextField()  ,"Folio Solicita"            ,30   ,"String");
	JTextField txtValida          = new Componentes().text(new JCTextField()  ,"Valida"                    ,30   ,"String");
	JTextField txtFechaValida     = new Componentes().text(new JCTextField()  ,"Fecha Valida"              ,30   ,"String");
	JTextField txtAutoriza        = new Componentes().text(new JCTextField()  ,"Autorizó"                  ,300  ,"String");
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
	
    JTextArea txaUso       = new Componentes().textArea(new JTextArea(), "Uso De La Mercancia", 300);
	JScrollPane Uso        = new JScrollPane(txaUso);

	JCButton btnAceptar  = new JCButton("Autorizar"   ,"Aplicar.png"                ,"Azul"); 
	JCButton btnNegar    = new JCButton("Negar"       ,"Delete.png"                 ,"Azul"); 
	JCButton btnImprimir     = new JCButton("Imprimir"    ,"imprimir-16.png" ,"Azul" );
	
	JRadioButton rbProveedorCont = new JRadioButton("Proveedor Contado");
	JRadioButton rbProveedor     = new JRadioButton("Proveedor");
	JRadioButton rbColaborador   = new JRadioButton("Colaborador");
	ButtonGroup  grupo           = new ButtonGroup();
	
	Border blackline, etched, raisedbevel, loweredbevel, empty;

	String[][] tablaprecargadaordenes;
  Object[] vector = new Object[7];

  String guardar_actualizar="";
 public  Cat_Solicitud_De_Orden_De_Gasto_Validacion(int folio){
	    this.cont.add(panel);
		this.setSize(825,450);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setModal(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("Validacion De Orden De Gasto");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Dialog-stop-hand64.png"));
		this.blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
		this.panel.setBorder(BorderFactory.createTitledBorder(blackline,"Validacion De Orden De Gasto"));

		this.menu_toolbar.add(btnAceptar  );
	 	this.menu_toolbar.addSeparator(   );
	    this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.add(btnNegar    );
		this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.add(btnImprimir );
		this.menu_toolbar.setFloatable(false);
 	 
		int x=20, y=20,width=122,height=20, sep=130;
		this.panel.add(menu_toolbar).setBounds                        (x         ,y      ,400     ,height );

		this.panel.add(new JLabel("Solicita:")).setBounds             (x=20      ,y+=30  ,width   ,height );
		this.panel.add(txtSolicito).setBounds                         (x+=40     ,y      ,300     ,height );
		this.panel.add(new JLabel("Fecha Solicitud:")).setBounds      (x+=310    ,y      ,width   ,height );
		this.panel.add(txtFecha).setBounds                            (x+=80     ,y      ,130     ,height );
		this.panel.add(new JLabel("Concepto:")).setBounds             (x+=140    ,y      ,width   ,height );
		this.panel.add(txtConcepto).setBounds                         (x+=50     ,y      ,width   ,height );
		
		
		this.panel.add(new JLabel("Valida:")).setBounds               (x=20      ,y+=25  ,width   ,height );
		this.panel.add(txtValida).setBounds                           (x+=40     ,y      ,300     ,height );
		this.panel.add(new JLabel("Fecha Valida:")).setBounds         (x+=310    ,y      ,width   ,height );
		this.panel.add(txtFechaValida).setBounds                      (x+=80     ,y      ,130     ,height );
		this.panel.add(new JLabel("Estatus:")).setBounds              (x+=140    ,y      ,width   ,height );
		this.panel.add(txtEstatus).setBounds                          (x+=50     ,y      ,width   ,height );
		
		this.panel.add(new JLabel("Folio:")).setBounds                (x=20      ,y+=25  ,width   ,height );
		this.panel.add(txtFolio).setBounds                            (x+=40     ,y      ,width   ,height );
		this.panel.add(txtEstablecimiento).setBounds                  (x+=sep    ,y      ,170     ,height );
		this.panel.add(new JLabel("Forma de Pago:")).setBounds        (x+=180    ,y      ,width   ,height );
		this.panel.add(txtForma_pago).setBounds                       (x+=80     ,y      ,130     ,height );
		this.panel.add(new JLabel("Tipo:")).setBounds                 (x+=140    ,y      ,width   ,height );
		this.panel.add(txtTipo).setBounds                             (x+=40     ,y      ,40      ,height );	
		this.panel.add(new JLabel("Plazo:")).setBounds                (x+=50     ,y      ,width   ,height );
		this.panel.add(txtPlazo).setBounds                            (x+=40     ,y      ,42      ,height );	
		
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
		this.panel.add(txtAutoriza).setBounds                         (x+=60     ,y      ,320     ,height );
		
		this.panel.add(txtTotal).setBounds                            (702       ,y      ,98      ,height );
		this.init_tabla();
		this.Buscar(folio);

		this.btnAceptar.addActionListener(opaceptar);
		this.btnNegar.addActionListener(opnegar);
		this.txtFoliosolicit.setText(usuario.getFolio()+"");
		this.btnImprimir.addActionListener(opImprimir_Reporte);
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
			aceptar_negar="A";
			actualizar();
		}
	};
	
	ActionListener opnegar = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			aceptar_negar="N";
			actualizar();
		}
	};
	
	
	public void actualizar(){
	Object[][] arreglo_guardado= new Object[tabla.getRowCount()][3];
	for (int i=0;i<tabla.getRowCount();i++){
		 arreglo_guardado[i][0]= "true"; //selecionado	
       	 arreglo_guardado[i][1]=txtFolio.getText().toString().trim();//folio_solicitud
         arreglo_guardado[i][2]=aceptar_negar;//usuario_valida
    }
	
	if(new GuardarSQL().Aceptar_Negar_Solicitudes_De_Orden_De_Gasto_De_Servicios(arreglo_guardado)){
		imprimir();
		dispose();
		init_tabla_principal();
	}else{
		JOptionPane.showMessageDialog(null, "Error Al Actualizar", "Avise al Administrador Del Sistema", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
	}
	
	}

}
	    ActionListener opImprimir_Reporte = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(cmb_status.getSelectedItem().toString().trim().equals("EN VALIDACION")){
					JOptionPane.showMessageDialog(null, "Para Imprimir Es Requerido Sea Validada","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
					return;
				}else {
					imprimir();
			    }
		   }
	  	};
		  	
		public void imprimir() {
			int fila;
			fila = tabla.getSelectedRow();
			String basedatos="2.26";
			String vista_previa_reporte="no";
			int vista_previa_de_ventana=0;
			String comando="orden_de_gasto_reporte '"+tabla.getValueAt(fila, 1)+"'";
			String reporte = "Obj_Reporte_De_Orden_De_Gasto.jrxml";
	  	    new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
		}
		
		public static void main(String args[]){
			try{
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				new Cat_Validacion_De_Ordenes_De_Gasto().setVisible(true);
			}catch(Exception e){	}
		}
	}

