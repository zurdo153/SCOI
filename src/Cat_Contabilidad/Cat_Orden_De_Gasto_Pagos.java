package Cat_Contabilidad;

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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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

import com.toedter.calendar.JDateChooser;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Cargar_Combo;
import Conexiones_SQL.Connexion;
import Conexiones_SQL.Generacion_Reportes;
import Conexiones_SQL.GuardarSQL;
import Obj_Contabilidad.Obj_Orden_De_Gasto;
import Obj_Contabilidad.Obj_Saldo_Banco_Interno;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_tabla;

@SuppressWarnings("serial")
public class Cat_Orden_De_Gasto_Pagos extends JFrame{

	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	Obj_Orden_De_Gasto gasto = new Obj_Orden_De_Gasto();
	Obj_tabla  ObjTab = new Obj_tabla();
	Obj_Saldo_Banco_Interno banco_interno= new Obj_Saldo_Banco_Interno();	
	
	int columnasog = 4,checkbox=-1;
	public void init_tabla(){
    	this.tablaog.getColumnModel().getColumn(0).setMinWidth(277);	
    	this.tablaog.getColumnModel().getColumn(1).setMinWidth(80);
    	this.tablaog.getColumnModel().getColumn(2).setMinWidth(80);
    	this.tablaog.getColumnModel().getColumn(3).setMinWidth(80);
    	
		String comando="Select '' as Descripcion, 0 P_Unitario,0 as Cantidad,0 as Importe" ;
		String basedatos="26",pintar="si";
		ObjTab.Obj_Refrescar(tablaog,modelog, columnasog, comando, basedatos,pintar,checkbox);
		modelog.setRowCount(0);
    }
	
	@SuppressWarnings("rawtypes")
	public Class[] basemovimientos (){
		Class[] types = new Class[columnasog];
		for(int i = 0; i<columnasog; i++){types[i]= java.lang.Object.class;}
		 return types;
	}
	
	public DefaultTableModel modelog = new DefaultTableModel(null, new String[]{"Descripcion","Cantidad","P.Unitario","Importe"}){
		 @SuppressWarnings("rawtypes")
			Class[] types = basemovimientos();
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
			public boolean isCellEditable(int fila, int columna){ return false;}
	};
	
	JTable tablaog = new JTable(modelog);
	public JScrollPane scroll_tabla = new JScrollPane(tablaog);
	
	JTextField txtBeneficiario   = new Componentes().text(new JCTextField()  ,"Beneficiario"              ,250  ,"String");
	JTextField txtSaldo          = new Componentes().text(new JCTextField()  ,"Saldo"                     ,30   ,"String");
	JTextField txtSaldo_Nuevo    = new Componentes().text(new JCTextField()  ,"Nuevo Saldo"               ,30   ,"String");
	JTextField txtFolio          = new Componentes().text(new JCTextField()  ,"Folio"                     ,30   ,"String");
	JTextField txtFolioSolicitud = new Componentes().text(new JCTextField()  ,"Folio Solicitud"           ,30   ,"String");
	JTextField txtProveedor      = new Componentes().text(new JCTextField()  ,"Nombre Proveedor"          ,250  ,"String");
	JTextField txtSolicitante    = new Componentes().text(new JCTextField()  ,"Solicit�"                  ,300  ,"String");
	JTextField txtFechaSolicito  = new Componentes().text(new JCTextField()  ,"Fecha Solicit�"            ,60   ,"String");
	JTextField txtautorizo       = new Componentes().text(new JCTextField()  ,"Autoriz�"                  ,300  ,"String");
	JTextField txtFechaAutorizo  = new Componentes().text(new JCTextField()  ,"Fecha Autoriz�"            ,60   ,"String");
	JTextField txtCantidad       = new Componentes().text(new JCTextField()  ,"Cantidad"                  ,30   ,"Double");
	JTextField txtTipoMovimiento = new Componentes().text(new JCTextField()  ,"Gasto/Compra"              ,30   ,"String");
	JTextField txtNumeroCheque   = new Componentes().text(new JCTextField()  ,"Numero Cheque"             ,20   ,"String");
	
    JTextArea  txtaUso           = new Componentes().textArea(new JTextArea(), "Uso De La Mercancia"      ,300);
	JScrollPane txtUso           = new JScrollPane(txtaUso);
	
	JDateChooser fhFecha 	     = new Componentes().jchooser(new JDateChooser()  ,"",0);
	
	String establecimientoScoi[] = new Obj_Establecimiento().Combo_Establecimiento();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbEstablecimiento = new JComboBox(establecimientoScoi);
	
	public String[] concepto(){try {return new Cargar_Combo().conceptos_de_ordenes_de_pago();} catch (SQLException e) {e.printStackTrace();}return null;}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbConcepto = new JComboBox(concepto());
	
	String cuentas[] =  banco_interno.Combo_Cuentaspago();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbcuenta_bancaria = new JComboBox(cuentas);
	
	JTextArea txaConcepto = new Componentes().textArea(new JTextArea(), "Concepto", 135);
	JScrollPane Concepto = new JScrollPane(txaConcepto);
	
	public String[] autorizados(){
		try {
			return new Cargar_Combo().autorizados_para_pago_efectivo();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; 
	}
	
	JLabel lblPersona 	     = new JLabel("");
	JRadioButton rbProveedor = new JRadioButton("Proveedor");
	JRadioButton rbEmpleado  = new JRadioButton("Empleado");
	ButtonGroup grupo        = new ButtonGroup();
	
	JButton btnBuscar       = new JCButton("Buscar"       ,"Filter-List-icon16.png","Azul"); 
	JButton btnGuardar      = new JCButton("Guardar","Guardar.png"    ,"Azul");
	JButton btnDeshacer     = new JCButton("Deshacer","deshacer16.png","Azul");
	JButton btnImprimir     = new JCButton("Imprimir","Print.png"      ,"Azul");
	JButton btnBuscarBen    = new JCButton("","Filter-List-icon16.png","Azul");
	JButton btnAltaproveedor= new JCButton("Nuevo Proveedor","tarjeta-de-informacion-del-usuario-icono-7370-16.png","Azul");
	Border borderline;
	
	JToolBar menu_toolbar       = new JToolBar();
	int folioBeneficiario = 0;
	public Cat_Orden_De_Gasto_Pagos(){
		this.setSize(565, 590);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Pay-Per-Click-icon64px.png"));
		this.setTitle("Orden De Pago");
		panel.setBorder(BorderFactory.createTitledBorder("Llena o Selecciona Los Datos Correspondientes"));
		lblPersona.setBorder(BorderFactory.createTitledBorder(borderline,"Beneficiario"));
		grupo.add(rbProveedor);
		grupo.add(rbEmpleado);
		rbProveedor.setSelected(true);
		
		this.menu_toolbar.add(btnBuscar    );
		this.menu_toolbar.addSeparator(    );
		this.menu_toolbar.addSeparator(    );
		this.menu_toolbar.add(btnDeshacer  );
		this.menu_toolbar.addSeparator(    );
		this.menu_toolbar.addSeparator(    );
		this.menu_toolbar.add(btnGuardar   );
		this.menu_toolbar.addSeparator(    );
		this.menu_toolbar.addSeparator(    );
		this.menu_toolbar.add(btnImprimir   );
		this.menu_toolbar.addSeparator(    );
		this.menu_toolbar.addSeparator(    );
		this.menu_toolbar.add(btnAltaproveedor);
		
		this.menu_toolbar.setFloatable(false);
		
		int x=15,y=20,width=100,height=20,sep=60;
		this.panel.add(menu_toolbar).setBounds                       (x      ,y     ,500     ,height);
		
		this.panel.add(new JLabel("Cuenta:")).setBounds              (x      ,y+=25 ,width   ,height);
		this.panel.add(cmbcuenta_bancaria).setBounds                 (x+=sep ,y     ,width   ,height);
		this.panel.add(new JLabel("Fecha de Pago:")).setBounds       (x+=110 ,y     ,width   ,height);
		this.panel.add(fhFecha).setBounds                            (x+=75  ,y     ,width   ,height);
		this.panel.add(new JLabel("Tipo:")).setBounds                (x+=110 ,y     ,width   ,height);
		this.panel.add(txtTipoMovimiento).setBounds                  (x+=30  ,y     ,135     ,height);	
		
		this.panel.add(new JLabel("Folio:")).setBounds               (x=15   ,y+=25 ,width   ,height);
		this.panel.add(txtFolio).setBounds                           (x+=sep ,y     ,width   ,height);
		this.panel.add(new JLabel("Folio O. Gasto")).setBounds       (x+=110 ,y     ,width   ,height);
		this.panel.add(txtFolioSolicitud).setBounds                  (x+=75  ,y     ,width   ,height);
		this.panel.add(cmbEstablecimiento  ).setBounds               (x+=110 ,y     ,165     ,height);
		
		this.panel.add(new JLabel("Proveedor:")).setBounds           (x=15   ,y+=25 ,width   ,height);
		this.panel.add(txtProveedor).setBounds                       (x+=sep  ,y    ,460     ,height);
		
		this.panel.add(new JLabel("Solicit�:")).setBounds            (x=15   ,y+=25 ,width   ,height);
		this.panel.add(txtSolicitante).setBounds                     (x+=sep ,y     ,300     ,height);
		this.panel.add(txtFechaSolicito).setBounds                   (x+=310 ,y     ,150     ,height);
		
		this.panel.add(new JLabel("Autoriz�:")).setBounds            (x=15   ,y+=25 ,width   ,height);
		this.panel.add(txtautorizo).setBounds                        (x+=sep ,y     ,300     ,height);
		this.panel.add(txtFechaAutorizo).setBounds                   (x+=310 ,y     ,150     ,height);
		
		this.panel.add(new JLabel("Descripcion Del Gasto:")).setBounds(x=15  ,y+=25 ,300     ,height);
		this.panel.add(txtUso).setBounds                             (x      ,y+=15 ,520     ,60    );
		
		this.panel.add(scroll_tabla).setBounds                       (x      ,y+=65 ,520     ,100   );
		
		this.panel.add(new JLabel("Clasificacion:")).setBounds       (x=15   ,y+=115,width   ,height);
		this.panel.add(cmbConcepto).setBounds                        (x+=sep ,y     ,275     ,height);
		this.panel.add(new JLabel("Num. Cheque:")).setBounds         (x+=280 ,y     ,width   ,height);
		this.panel.add(txtNumeroCheque).setBounds                    (x+=80  ,y     ,width   ,height);
		
		this.panel.add(new JLabel("Cantidad:$")).setBounds           (x=15   ,y+=25 ,width   ,height);
		this.panel.add(txtCantidad).setBounds                        (x+=sep ,y     ,130     ,height);
		this.panel.add(new JLabel("Saldo:$")).setBounds              (x+=135 ,y     ,width   ,height);
		this.panel.add(txtSaldo).setBounds                           (x+=40  ,y     ,width   ,height);
		this.panel.add(new JLabel("Nuevo Saldo:$")).setBounds        (x+=105 ,y     ,width   ,height);
		this.panel.add(txtSaldo_Nuevo).setBounds                     (x+=80  ,y     ,width   ,height);
		
		this.panel.add(new JLabel("Beneficiario:")).setBounds        (x=15   ,y+=25 ,70      ,height);
		this.panel.add(txtBeneficiario).setBounds                    (x+=sep ,y     ,300     ,height);
		this.panel.add(btnBuscarBen).setBounds                       (x+=300 ,y     ,30      ,height);
		
		this.panel.add(rbProveedor).setBounds                        (x=430  ,y     ,90      ,height);
		this.panel.add(rbEmpleado).setBounds                         (x      ,y+=20 ,90      ,height);
		
		this.panel.add(new JLabel("Detalle:")).setBounds             (x=15   ,y+=5 ,70       ,height);
		this.panel.add(Concepto  ).setBounds                         (x      ,y+=18 ,520     ,80    );

		txaConcepto.setLineWrap(true); 
		txaConcepto.setWrapStyleWord(true);
		txtaUso.setLineWrap(true); 
		txtaUso.setWrapStyleWord(true);
		
		init_tabla();
		
		try {
			fhFecha.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(new BuscarSQL().fecha(0)));
		} catch (ParseException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		txtFolio.setEditable(false);
		txtBeneficiario.setEditable(false);
		txtFolioSolicitud.setEditable(false);
		txtProveedor.setEditable(false);
		txtSolicitante.setEditable(false);
		txtFechaSolicito.setEditable(false);
		txtautorizo.setEditable(false);
		txtFechaAutorizo.setEditable(false);
		txtaUso.setEditable(false);
		txtCantidad.setEditable(false);
		txtSaldo.setEditable(false);
		txtSaldo_Nuevo.setEditable(false);
		txtTipoMovimiento.setEditable(false);
		cmbEstablecimiento.setEnabled(false);
		cmbConcepto.setEnabled(false);
		txtNumeroCheque.setEditable(false);
		
		cont.add(panel);
		
		btnBuscar.addActionListener(buscarorden);
		rbProveedor.addActionListener(opRButton);
		rbEmpleado.addActionListener(opRButton);
		btnBuscarBen.addActionListener(opFiltro);
		btnGuardar.addActionListener(opGuardar);
		btnDeshacer.addActionListener(deshacer);
		btnImprimir.addActionListener(opReporte);
		btnAltaproveedor.addActionListener(opproveedor);
		cmbcuenta_bancaria.addActionListener(cambio_de_cuenta);
		cmbConcepto.addKeyListener(enterpasaratexareaConcepto);
		txtautorizo.addKeyListener(enterpasaraBeneficiario);
		rbEmpleado.addKeyListener(enterpasarafiltroBeneficiario);
		rbProveedor.addKeyListener(enterpasarafiltroBeneficiario);
		
		btnDeshacer.doClick();
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), "buscarbtn");
         getRootPane().getActionMap().put("buscarbtn", new AbstractAction(){public void actionPerformed(ActionEvent e)
             {                 	    btnBuscar.doClick();                   	    }	                 });
         
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0), "referencia");
        getRootPane().getActionMap().put("referencia", new AbstractAction(){public void actionPerformed(ActionEvent e) {   btnBuscarBen.doClick();         }     });
         
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_G,Event.CTRL_MASK),"guardar");
        getRootPane().getActionMap().put("guardar", new AbstractAction(){   public void actionPerformed(ActionEvent e) {   btnGuardar.doClick();           }     });
             
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_P,Event.CTRL_MASK),"proveedor");
        getRootPane().getActionMap().put("proveedor", new AbstractAction(){ public void actionPerformed(ActionEvent e) {  btnAltaproveedor.doClick();      }     });
        
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0), "guardar");
        getRootPane().getActionMap().put("guardar", new AbstractAction(){   public void actionPerformed(ActionEvent e){    btnGuardar.doClick();   	       }     });
                 
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
        getRootPane().getActionMap().put("escape", new AbstractAction(){    public void actionPerformed(ActionEvent e)  {    btnDeshacer.doClick();        }     });
        
	    this.addWindowListener(new WindowAdapter() {  public void windowOpened( WindowEvent e ){ cmbcuenta_bancaria.requestFocus(); cmbcuenta_bancaria.showPopup(); }  });    
	}
	
	public void cargar_datos_iniciales() {
		try {
			txtFolio.setText(new BuscarSQL().folio_siguiente(85+""));
		} catch (SQLException e1) {
			e1.printStackTrace();
		} 
		saldo() ;
	};
	
	
	public void saldo() {
		float saldo=new BuscarSQL().saldo_actual_para_pagos_en_efectivo(cmbcuenta_bancaria.getSelectedItem().toString().trim());
		float cantidad=txtCantidad.getText().equals("")?0:Float.valueOf(txtCantidad.getText());
		txtSaldo.setText(saldo+"");
	    txtSaldo_Nuevo.setText( (saldo-cantidad)+"");
	}
	
	ActionListener cambio_de_cuenta = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			saldo() ;
		 }
	};
	
	ActionListener buscarorden = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			new Cat_Filtro_Buscar_Orden_De_Gasto().setVisible(true);
		 }
	};
		
	ActionListener deshacer = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			modelog.setRowCount(0);
			txtFolio.setText("");
			txtBeneficiario.setText("");
			txtFolioSolicitud.setText("");
			txtProveedor.setText("");
			txtSolicitante.setText("");
			txtFechaSolicito.setText("");
			txtautorizo.setText("");
			txtFechaAutorizo.setText("");
			txtaUso.setText("");
			txtCantidad.setText("");
			txtSaldo.setText("");
			txtSaldo_Nuevo.setText("");
			txaConcepto.setText("");
			txtTipoMovimiento.setText("");
			txtNumeroCheque.setText("");
			
			cmbEstablecimiento.setSelectedIndex(0);
			cmbConcepto.setSelectedIndex(0);
			rbProveedor.setSelected(true);
			btnAltaproveedor.setSelected(true);
    		btnBuscarBen.setEnabled(false);
    		txtNumeroCheque.setEditable(false);
    		
    		rbProveedor.setEnabled(false);
    		rbEmpleado.setEnabled(false);    		
    		txaConcepto.setEditable(false);
    		fhFecha.setEnabled(false);
			txaConcepto.setBackground(new Color(Integer.parseInt("EBEBEB",16)));
			txtCantidad.requestFocus();
			cargar_datos_iniciales(); 
			Guardar_actualizar="";
			cmbcuenta_bancaria.setSelectedIndex(0);
			cmbcuenta_bancaria.setEnabled(true);
            cmbcuenta_bancaria.requestFocus();
		 }
		};
		
	 ActionListener opRButton = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			folioBeneficiario = 0;
			txtBeneficiario.setText("");
			if(rbEmpleado.isSelected()){
				btnAltaproveedor.setEnabled(false);
			}else{
				btnAltaproveedor.setEnabled(true);
			}
		}
	};
	
	 ActionListener opFiltro = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			try {
				new  Cat_Filtro_Referencia().setVisible(true);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	};
	
	 ActionListener opproveedor = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			new  Cat_Proveedores().setVisible(true);
		}
	};
	
	 ActionListener opReporte = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
				new Cat_Reimpresion_De_Pagos_De_Caja_Chica().setVisible(true);
		}
	};
	
	String Guardar_actualizar="";
	ActionListener opGuardar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(!validaCampos().equals("")){
				JOptionPane.showMessageDialog(null, "Los Siguiente Campos Son Requeridos:\n"+validaCampos(),"Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
				return;
			}else{
               //		int folio_orden_de_gasto                               ,float cantidad                                         , String fecha                                               , String observaciones                     , String tipoBeneficiario        , int folioBeneficiario, String Concepto,String Guardar_actualizar){
			if(new GuardarSQL().Guardar_Orden_De_Gasto_Pago_En_Efectivo(Integer.valueOf(txtFolioSolicitud.getText().toString()), Float.valueOf(txtCantidad.getText().toString().trim()),new SimpleDateFormat("dd/MM/yyyy").format(fhFecha.getDate()),txaConcepto.getText().toUpperCase().trim(),rbProveedor.isSelected()?"P":"E",folioBeneficiario,cmbConcepto.getSelectedItem().toString(),Guardar_actualizar,cmbcuenta_bancaria.getSelectedItem().toString().trim(), txtNumeroCheque.getText().trim() )){
//				           Obj_Correos correos = new Obj_Correos().buscar_correos(85, "");	
//				           String productos="\nDescripcion / Cantidad / Importe\n";
//						   for(int i=0;i<tablaog.getRowCount();i++) {
//							   productos=productos+tablaog.getValueAt(i, 0)+"  / "+tablaog.getValueAt(i, 1)+"  /$"+tablaog.getValueAt(i, 3)+" \n";
//						   }
						   
//				           String Mensaje= "El usuario:"+txtSolicitante.getText().toString()+" solicit� el dia "+txtFechaSolicito.getText().toString()+" con folio:"+txtFolioSolicitud.getText().toString()
//							  		      +"\nUn pago con un valor total de:$ "+txtCantidad.getText().toString()
//										  +"\nDescripcion del gasto/compra: "+txtaUso.getText().toString()
//										  +"\n"+productos
//										  +"\nPara el establecimiento: "+cmbEstablecimiento.getSelectedItem().toString().trim()
//										  +"\nBeneficiario: "+txtProveedor.getText().trim()
//										  +"\nAutoriz� pago: "+txtautorizo.getText().toString().trim()+" el dia "+txtFechaAutorizo.getText().toString().trim()
//										  +"\nRecibio efectivo: "+txtBeneficiario.getText().trim();
//
//						  new EmailSenderService().enviarcorreo(correos.getCorreos(),correos.getCantidad_de_correos(),Mensaje,"A.I.� Pago por un total de $:"+txtCantidad.getText().toString()+" Folio:"+txtFolio.getText().toString()+ " A "+txtProveedor.getText().trim(),"Gastos");

						        btnDeshacer.doClick();
								Guardar_actualizar="";
								JOptionPane.showMessageDialog(null, "Se Guardo Correctamente", "Aviso", JOptionPane.OK_OPTION,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
								imprimir_ultimo_guardado(Integer.valueOf(txtFolio.getText())-1);
							}else
								{
								JOptionPane.showMessageDialog(null, "Ocurri� Un Error Al Intentar Guardar","Error",JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen//usuario-icono-eliminar5252-64.png"));
								return;
							}
			}
			
		}
	};
	
	KeyListener enterpasaratexareaConcepto = new KeyListener() {
		public void keyTyped(KeyEvent e){}
		public void keyReleased(KeyEvent e) {}
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				txaConcepto.requestFocus();
			}
		}
	};
	
	KeyListener enterpasaraBeneficiario = new KeyListener() {
		public void keyTyped(KeyEvent e){}
		public void keyReleased(KeyEvent e) {}
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				rbProveedor.requestFocus();
			}
		}
	};
	
	KeyListener enterpasarafiltroBeneficiario = new KeyListener() {
		public void keyTyped(KeyEvent e){}
		public void keyReleased(KeyEvent e) {}
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				btnBuscarBen.doClick();
			}
		}
	};
	
	
	private String validaCampos(){
		String error="";
		String fecha = "";
		fecha = fhFecha.getDate()+"";
		if(fecha.equals("null"))error+= "Fecha\n";
		if(txaConcepto.getText().equals(""))error+= "Detalle\n";
		if(txtBeneficiario.getText().equals(""))error+= "Beneficiario\n";
		if(txtNumeroCheque.getText().equals(""))error+= "Numero de Cheque\n";		
		return error;
	}
	
	public void imprimir_ultimo_guardado(int folio_impesion){
		String basedatos="2.26";
		String vista_previa_reporte="no";
		int vista_previa_de_ventana=0;
		String comando="exec orden_de_gasto_reporte_de_pago_en_efectivo "+folio_impesion ;
		String reporte = "Obj_Reporte_De_Orden_De_Gasto_Pago_En_Efectivo.jrxml";
		new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
	} ;

	//TODO inicia filtro_Buscar ORDEN DE GASTO
			public class Cat_Filtro_Buscar_Orden_De_Gasto extends JDialog{
				Container contfb = getContentPane();
				JLayeredPane panelfb = new JLayeredPane();
				Connexion con = new Connexion();
				Obj_tabla ObjTab =new Obj_tabla();
				int columnasb = 13,checkbox=-1;
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
			    	
					String comandob = "orden_de_gasto_filtro_pago '"+cmbcuenta_bancaria.getSelectedItem().toString().trim()+"'";
			    	String basedatos="26",pintar="si";
					ObjTab.Obj_Refrescar(tablab,modelob, columnasb, comandob, basedatos,pintar,checkbox);
			    }
				
				@SuppressWarnings("rawtypes")
				public Class[] base (){
					Class[] types = new Class[columnasb];
					for(int i = 0; i<columnasb; i++){types[i]= java.lang.Object.class;}
					 return types;
				}
				
				public DefaultTableModel modelob = new DefaultTableModel(null, new String[]{"Folio","Proveedor","Tipo Provedor","Descripcion Gasto","Establecimiento","Importe Total","Usuario Solicita", "Fecha","Estatus","Fecha Autorizacion","Usuario Autorizo","Concepto","Forma de Pago"}){
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
					
					contfb.add(panelfb);
					
		            getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
		            getRootPane().getActionMap().put("escape", new AbstractAction(){ public void actionPerformed(ActionEvent e)  {    txtBuscarb.requestFocus();       	    }             });      
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
				   int fila = tablab.getSelectedRow();
				   modelog.setRowCount(0);
	        		String[][] tablacompleta=gasto.consulta_orden_de_gasto(Integer.valueOf(tablab.getValueAt(fila,0)+""));
	        	    Object[]   vectortabla = new Object[3];
	        		for(int i=0;i<tablacompleta.length;i++){
	        				for(int j=0;j<3;j++){
	        				  vectortabla[j] = tablacompleta[i][j].toString();
	        				}
	        				modelog.addRow(vectortabla);
	        		}
	        			for(int i=0;i<tablaog.getRowCount();i++) {
	        				tablaog.setValueAt(Float.valueOf(tablaog.getValueAt(i, 1)+"") * Float.valueOf(tablaog.getValueAt(i, 2)+"")   , i, 3);
	        			}
	        		
	        		
	        		if(tablab.getValueAt(fila,  8).toString().equals("AUTORIZADO")){
	        			if(tablacompleta[0][17].toString().equals("COMPRA")){
	        			  cmbConcepto.setSelectedItem("COMPRAS A PROVEEDORES");	
	        			 }else {
	        			  cmbConcepto.setSelectedItem("GASTO SIN CLASIFICAR");		 
	        			 }
	        				
	        			txtFolioSolicitud.setText         (tablab.getValueAt(fila,  0).toString());
		        		txtProveedor.setText              (tablab.getValueAt(fila,  1).toString());
		        		txtaUso.setText                   (tablab.getValueAt(fila,  3).toString());
		        		cmbEstablecimiento.setSelectedItem(tablab.getValueAt(fila,  4).toString());
		        		txtCantidad.setText               (tablab.getValueAt(fila,  5).toString());
		        		txtSolicitante.setText            (tablab.getValueAt(fila,  6).toString());
		        		txtFechaSolicito.setText          (tablab.getValueAt(fila,  7).toString());
		        		txtFechaAutorizo.setText          (tablab.getValueAt(fila,  9).toString());
		        		txtautorizo.setText               (tablab.getValueAt(fila, 10).toString());	
		        		txtTipoMovimiento.setText         (tablab.getValueAt(fila, 11).toString());	

		        		cargar_datos_iniciales();
		        		Guardar_actualizar="N";
		        		txaConcepto.setBackground(new Color(254,254,254));
		        		txaConcepto.setEditable(true);
		        		btnBuscarBen.setEnabled(true);
		        		rbProveedor.setEnabled(true);
		        		fhFecha.setEnabled(true);
		        		rbEmpleado.setEnabled(true);    		
		        		cmbcuenta_bancaria.setEnabled(false);
		        		if(tablab.getValueAt(fila, 12).toString().equals("CHEQUE")) {
		        			txtNumeroCheque.setText("");
		        			txtNumeroCheque.setEditable(true);
		        			txtNumeroCheque.requestFocus();
		        		}else {
		        			txtNumeroCheque.setText("0");
		        			txtNumeroCheque.setEditable(false);
		        		}
		        		
		        		dispose();
	        		}else{
						JOptionPane.showMessageDialog(null, "Para Que Un Registro Sea Agregado Para Pago Es Requerido Que Este Haya Sido Autorizado\nEl Que Esta Seleccionando Tiene Estatus De "+tablab.getValueAt(fila,  8).toString(),"Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
	        		 return;
	        		}
	       		
			    }
				 
			}
			
/////termina filtro busqueda de orden de gasto		
//TODO Filtro de Referencia ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public class Cat_Filtro_Referencia extends JDialog{
		Container cont = getContentPane();
		JLayeredPane campo = new JLayeredPane();
        Obj_tabla  Objetotabla = new Obj_tabla();
		
		int columnas = 2,checkbox=-1;
		public void init_tabla(){
			 String ref =rbProveedor.isSelected()?"Proveedor":"Empleado";
			 String comando=" " ;
			switch(ref){
			    case "Empleado": 		comando = "select folio,nombre+' '+ap_paterno+' '+ap_materno as nombre from tb_empleado order by nombre"; break;
		    	case "Proveedor": 		comando = "select folio_proveedor,nombre+' '+ap_paterno+' '+ap_materno as nombre from tb_proveedores where status=1  order by nombre"; break;
		        }
	    	this.tabla_Filtro_Ref.getColumnModel().getColumn(1).setMinWidth(375);
			String basedatos="26",pintar="si";
			Objetotabla.Obj_Refrescar(tabla_Filtro_Ref,modelo_Filtro_Ref, columnas, comando, basedatos,pintar,checkbox);
	    }
		
		DefaultTableModel modelo_Filtro_Ref = new DefaultTableModel(null,new String[]{"Folio", "Nombre"}){
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
		
		JTextField txtDescripcion  = new Componentes().textfiltro(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<", 500, "String",tabla_Filtro_Ref,columnas);
		public Cat_Filtro_Referencia() throws SQLException{
			this.setSize(510,650);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			this.setModal(true);
			
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/filter_icon&16.png"));
			this.setTitle("Filtro De Beneficiario De La Orden De Pago En Efectivo");
			campo.setBorder(BorderFactory.createTitledBorder("Selecciona El Beneficiario"));
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
		   fila = tabla_Filtro_Ref.getSelectedRow();
			int folio =  Integer.valueOf(tabla_Filtro_Ref.getValueAt(fila, 0).toString().trim());
			folioBeneficiario= folio;
			txtBeneficiario.setText(tabla_Filtro_Ref.getValueAt(fila, 1).toString());
			txaConcepto.requestFocus();
   		    dispose();
	    }
	}
	
	public static void main(String[] args) {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Orden_De_Gasto_Pagos().setVisible(true);
		}catch(Exception e){	}	
	}
}
