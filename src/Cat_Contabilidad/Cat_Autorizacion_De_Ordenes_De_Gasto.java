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

import Cat_Principal.EmailSenderService;
import Conexiones_SQL.ActualizarSQL;
import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Connexion;
import Conexiones_SQL.Generacion_Reportes;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Contabilidad.Obj_Orden_De_Gasto;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_tabla;
import Obj_Servicios.Obj_Servicios;

@SuppressWarnings("serial")
public class Cat_Autorizacion_De_Ordenes_De_Gasto extends JFrame {
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
		
		int Cantidad_Real_De_Columnas=13,checkboxindex=1;
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
			String comando = "orden_de_gasto_autorizacion_filtro '"+cmb_status.getSelectedItem().toString().trim()+"'";
			String basedatos="26",pintar="si";
			ObjTab.Obj_Refrescar(tabla,modelo, Cantidad_Real_De_Columnas, comando, basedatos,pintar,checkboxindex);
	    }
		
	 public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"S","Folio","Proveedor","Concepto","Descripcion Gasto","Establecimiento","Importe Total","Usuario Solicita", "Fecha","Estatus","Fecha Autorizacion","Usuario Autorizo","Tipo Provedor"}){
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
		
		
		String status[] = {"PENDIENTE","AUTORIZADO","CANCELADO","FINALIZADO","NEGADO","TODOS"};
		@SuppressWarnings({ "unchecked", "rawtypes" })
		JComboBox cmb_status = new JComboBox(status);
		
		JTextField txtFolio      = new Componentes().text(new JCTextField()  ,"Folio"   ,30   ,"String");
		JTextField txtPedientes  = new Componentes().text(new JCTextField(), "Cant. Pendientes", 150, "String");
		JTextField txtFiltro     = new Componentes().textfiltro(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<",300 , "String",tabla,Cantidad_Real_De_Columnas );
		JTextField txtTotal      = new Componentes().text(new JCTextField()  ,"Total"                     ,30   ,"String");
		
		Obj_Orden_De_Gasto gasto = new Obj_Orden_De_Gasto();
	    String aceptar_negar="";
		JToolBar menu_toolbar       = new JToolBar();
		public Cat_Autorizacion_De_Ordenes_De_Gasto()	{
			this.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds()); 
			int ancho = Toolkit.getDefaultToolkit().getScreenSize().width;
			int alto  = Toolkit.getDefaultToolkit().getScreenSize().height;
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/asistencia.png"));
			this.setTitle("Autorizacion De Ordenes de Gasto");
			campo.setBorder(BorderFactory.createTitledBorder("Seleccione Las Ordenes De Gasto Que Desea Autorizar o Negar"));

			int x=15,y=15,height=20;	
			this.campo.add(new JLabel("Registros:")).setBounds(x      ,y      ,150    ,height );
			this.campo.add(txtPedientes).setBounds            (x+=50  ,y      ,100    ,height );
			this.campo.add(new JLabel("Busqueda Por Estatus:")).setBounds(x+=150,y,110  ,height   );
			this.campo.add(cmb_status).setBounds              (x+=120 ,y      ,120    ,height   );
			this.campo.add(txtFiltro).setBounds               (x=15   ,y+=25  ,ancho-40,height  );
			this.campo.add(scroll_tabla).setBounds            (x      ,y+=20  ,ancho-40,alto-150);
			agregar(tabla);
			init_tabla_principal();
			cantidad();
			
			txtTotal.setEditable(false);
			txtPedientes.setEditable(false);
			cont.add(campo);
			cmb_status.addActionListener(buscar_con_combo);
			txtFiltro.requestFocus();
	        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
	        getRootPane().getActionMap().put("escape", new AbstractAction(){ public void actionPerformed(ActionEvent e)
	            {    txtFiltro.setText("");             	 txtFiltro.requestFocus();    	    }     });
		}
		
		public void cantidad() {
			txtPedientes.setText(tabla.getRowCount()+"");
		}
		
	   ActionListener opImprimir_Reporte = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String basedatos="2.26";
				String vista_previa_reporte="no";
				int vista_previa_de_ventana=0;
				String comando="orden_de_gasto_reporte '"+txtFolio.getText().toString()+"'";
				String reporte = "Obj_Reporte_De_Orden_De_Gasto.jrxml";
		  	    new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
			}
	  	};
		
		ActionListener buscar_con_combo = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				init_tabla_principal();
				cantidad();
				txtFiltro.requestFocus();
			}
		};
		
		private void agregar(final JTable tbl) {
			tbl.addMouseListener(new MouseListener() {
				public void mouseReleased(MouseEvent e) {
			 	 if(e.getClickCount() == 1){
	    				int fila = tabla.getSelectedRow();
                     new Cat_Solicitud_De_Orden_De_Gasto_Autorizacion(Integer.valueOf(tabla.getValueAt(fila, 1).toString())).setVisible(true);
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
	                     new Cat_Solicitud_De_Orden_De_Gasto_Autorizacion(Integer.valueOf(tabla.getValueAt(fila, 1).toString())).setVisible(true);
					}
				}
				public void keyReleased(KeyEvent e)   {}
				public void keyTyped   (KeyEvent e)   {}
			});
	    }
		
///////////TODO  FILTRO DE  SOLICITUD SELECCIONADA
public class Cat_Solicitud_De_Orden_De_Gasto_Autorizacion extends JDialog{
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
	JTextField txtSolicitante     = new Componentes().text(new JCTextField()  ,"Autoriza"                  ,300  ,"String");
	JTextField txtFecha           = new Componentes().text(new JCTextField()  ,"Fecha"                     ,60   ,"String");
	JTextField txtTotal           = new Componentes().text(new JCTextField()  ,"Total"                     ,30   ,"String");
	JTextField txtEstatus         = new Componentes().text(new JCTextField()  ,"Estatus"                   ,50   ,"String");
	JTextField txtEstablecimiento = new Componentes().text(new JCTextField()  ,"Establecimiento"           ,90   ,"String");
	JTextField txtConcepto        = new Componentes().text(new JCTextField()  ,"Concepto"                  ,90   ,"String");
	JTextField txtSolicito        = new Componentes().text(new JCTextField()  ,"Solicito"                  ,300  ,"String");
	JTextField txtFolioservici    = new Componentes().text(new JCTextField()  ,"Folio Servicio"            ,30   ,"String");
	JTextField txtDetalleServi    = new Componentes().text(new JCTextField()  ,"Detalle Servicio"          ,350  ,"String");
	JTextField txtTipo            = new Componentes().text(new JCTextField()  ,"Tipo"                      ,35   ,"String");
	
    JTextArea txaUso       = new Componentes().textArea(new JTextArea(), "Uso De La Mercancia", 300);
	JScrollPane Uso        = new JScrollPane(txaUso);

	JCButton btnAceptar  = new JCButton("Autorizar"   ,"Aplicar.png"                ,"Azul"); 
	JCButton btnNegar    = new JCButton("Negar"       ,"Delete.png"                 ,"Azul"); 
	JCButton btnCancelar = new JCButton("Cancelar"    ,"cancelar-icono-4961-16.png" ,"Azul"); 
	JCButton btnImprimir = new JCButton("Imprimir"    ,"imprimir-16.png"            ,"Azul");
	
	JRadioButton rbProveedorCont = new JRadioButton("Proveedor Contado");
	JRadioButton rbProveedor     = new JRadioButton("Proveedor");
	JRadioButton rbColaborador   = new JRadioButton("Colaborador");
	ButtonGroup  grupo           = new ButtonGroup();
	
	Border blackline, etched, raisedbevel, loweredbevel, empty;

	String[][] tablaprecargadaordenes;
    Object[] vector = new Object[7];

    String guardar_actualizar="";
   public  Cat_Solicitud_De_Orden_De_Gasto_Autorizacion(int folio){
	    this.cont.add(panel);
		this.setSize(825,400);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setModal(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("Autorizacion De Orden De Gasto");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/articulo-icono-9036-48.png"));
		this.blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
		this.panel.setBorder(BorderFactory.createTitledBorder(blackline,"Autorizacion De Orden De Gasto"));

		this.menu_toolbar.add(btnAceptar  );
	 	this.menu_toolbar.addSeparator(   );
	    this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.add(btnCancelar );
		this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.add(btnNegar    );
		this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.add(btnImprimir );
		this.menu_toolbar.setFloatable(false);
   	 
		int x=20, y=20,width=122,height=20, sep=130;
		this.panel.add(menu_toolbar).setBounds                        (x         ,y      ,400     ,height );
		this.panel.add(txtSolicito).setBounds                         (x+440     ,y      ,300     ,height );
		this.panel.add(txtTipo).setBounds                             (x+740     ,y      ,40      ,height );		
		
		this.panel.add(txtFolio).setBounds                            (x         ,y+=30  ,width   ,height );
		this.panel.add(txtConcepto).setBounds                         (x+=sep    ,y      ,width   ,height );
		this.panel.add(txtEstatus).setBounds                          (x+=sep    ,y      ,width   ,height );
		this.panel.add(txtEstablecimiento).setBounds                  (x+=sep    ,y      ,160     ,height );
		this.panel.add(new JLabel("Fecha Solicitud:")).setBounds      (x+=170    ,y      ,width   ,height );
		this.panel.add(txtFecha).setBounds                            (x+=80     ,y      ,140     ,height );
		
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
		this.panel.add(txtSolicitante).setBounds                      (x+60      ,y      ,320     ,height );
		this.panel.add(txtTotal).setBounds                            (x+682     ,y      ,98      ,height );
		this.init_tabla();
		this.Buscar(folio);

		this.btnImprimir.addActionListener (opImprimir_Reporte        );   
		this.btnAceptar.addActionListener(opaceptar);
		this.btnNegar.addActionListener(opnegar);
		this.btnCancelar.addActionListener(opacancelar);
		this.txtSolicitante.setText(usuario.getNombre_completo());
		this.txtFoliosolicit.setText(usuario.getFolio()+"");
		
		if(cmb_status.getSelectedItem().equals("PENDIENTE")||cmb_status.getSelectedItem().equals("AUTORIZADO")){
			this.btnAceptar.setEnabled(true);btnCancelar.setEnabled(true);btnNegar.setEnabled(true);	
		}else{
			this.btnAceptar.setEnabled(false);btnCancelar.setEnabled(false);btnNegar.setEnabled(false);	
		}
    }
   
	ActionListener opacancelar = new ActionListener() {
	   	public void actionPerformed(ActionEvent e) {
			aceptar_negar="CANCELADA";
			Guardar();
		}
	};
		
	ActionListener opaceptar = new ActionListener() {
    	public void actionPerformed(ActionEvent e) {
			aceptar_negar="AUTORIZADA";
			Guardar();
		}
	};
	
	ActionListener opnegar = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			aceptar_negar="NEGADA";
			Guardar();
		}
	};
	
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
		txtConcepto.setText(tablacompleta[0][17].toString());
		
		System.out.println(tablacompleta[0][9].toString() );
		System.out.println(tablacompleta[0][19].toString());
		System.out.println(tablacompleta[0][20].toString());
		
		
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
		txtSolicitante.setEditable(false);
		txtFolio_prv.setEditable(false);
		txtTotal.setEditable(false);
		txtSolicito.setEditable(false);
		txtFolioservici.setEditable(false);
		txtDetalleServi.setEditable(false);
		txtTipo.setEditable(false);
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
	
	public void Guardar(){
	             if(tabla.isEditing()){tabla.getCellEditor().stopCellEditing();}
	  
				 String[][] tabla_guardado = ObjTab.tabla_guardar(tabla);
				  if(new ActualizarSQL().Guardar_Autorizacion_De_Orden_De_Gasto(txtFolio.getText().toString(),aceptar_negar.substring(0, 1))){
						   try {servicios_solicitud = new BuscarSQL().correo_informa_estatus_solicitud(Integer.valueOf(txtFolio.getText().toString().trim()),84);
							} catch (SQLException e1) {e1.printStackTrace();}
						   if(!servicios_solicitud.getCorreos().toString().trim().equals("NO TIENE")){
						      String Mensaje= "Hola "+tabla_guardado[0][7].toString()+" Tu Solicitud De Orden De Gasto Folio:"+tabla_guardado[0][1].toString()+" a Nombre De "+tabla_guardado[0][2].toString()+" Fue "+aceptar_negar+" Por "+usuario.getNombre_completo()   ;
				      							  new EmailSenderService().enviarcorreo(servicios_solicitud.getCorreos(),servicios_solicitud.getCantidad_de_correos(),Mensaje, "INFORME DE SOLICITUD "+aceptar_negar+" DE LA ORDEN DE GASTO FOLIO:"+tabla_guardado[0][1].toString(),"Gastos");
						   }
						   
			        	init_tabla_principal();
			        	txtTotal.setText("");
						dispose();
						txtFiltro.requestFocus();
						return;
					}else{
						JOptionPane.showMessageDialog(null, "Error Al Actualizar", "Avise al Administrador Del Sistema", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
						return;
					}
	};
	
 	 ActionListener opImprimir_Reporte = new ActionListener(){
  		public void actionPerformed(ActionEvent e){
  			String basedatos="2.26";
  			String vista_previa_reporte="no";
  			int vista_previa_de_ventana=0;
  			String comando="orden_de_gasto_reporte '"+txtFolio.getText().toString()+"'";
  			String reporte = "Obj_Reporte_De_Orden_De_Gasto.jrxml";
  			 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
  		}
  	};

  }
		
		public static void main(String args[]){
			try{
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				new Cat_Autorizacion_De_Ordenes_De_Gasto().setVisible(true);
			}catch(Exception e){	}
		}
	}

