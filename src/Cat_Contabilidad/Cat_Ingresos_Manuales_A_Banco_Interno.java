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
import Obj_Contabilidad.Obj_Saldo_Banco_Interno;
import Obj_Contabilidad.Obj_Transpaso_A_Banco_Interno;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_tabla;

@SuppressWarnings("serial")
public class Cat_Ingresos_Manuales_A_Banco_Interno extends JFrame{
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	Connexion con = new Connexion();
	JToolBar menu_toolbar       = new JToolBar();
	
	Obj_Usuario usuario = new Obj_Usuario().LeerSession();
	Obj_tabla  ObjTab = new Obj_tabla();
	Obj_Saldo_Banco_Interno banco_interno= new Obj_Saldo_Banco_Interno();	
	Obj_Transpaso_A_Banco_Interno banco_interno_traspaso= new Obj_Transpaso_A_Banco_Interno();	
	
	int columnas = 2,checkbox=-1;
	public void init_tabla(){
    	this.tabla.getColumnModel().getColumn(0).setMinWidth(425);	
    	this.tabla.getColumnModel().getColumn(1).setMinWidth(90);
		String comando="Select '' as Descripcion, 0 Importe" ;
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
	public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"Descripcion","Importe"}){
		 @SuppressWarnings("rawtypes")
			Class[] types = basemovimientos();
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
			public boolean isCellEditable(int fila, int columna){if(columna>0){return true;}else{ return false;}}
	};
	JTable tabla = new JTable(modelo);
	public JScrollPane scroll_tabla = new JScrollPane(tabla);
	
	JTextField txtFolio       = new Componentes().text(new JCTextField()  ,"Folio"                     ,30   ,"String");
	JTextField txtDescripcion = new Componentes().text(new JCTextField()  ,"Descripcion Del Motivo"    ,350  ,"String");
	JTextField txtFoliosolicit= new Componentes().text(new JCTextField()  ,"Folio Solicita"            ,30   ,"String");
	JTextField txtSolicitante = new Componentes().text(new JCTextField()  ,"Solicitante"               ,300  ,"String");
	JTextField txtTotal       = new Componentes().text(new JCTextField()  ,"Total"                     ,30   ,"String");
	
    JTextArea txaUso       = new Componentes().textArea(new JTextArea(), "Uso De La Mercancia", 300);
	JScrollPane Uso        = new JScrollPane(txaUso);

	JCButton btnBuscar     = new JCButton("Buscar"       ,"Filter-List-icon16.png","Azul"); 
	JCButton btnNuevo      = new JCButton("Nuevo"        ,"Nuevo.png"             ,"Azul");
	JCButton btnGuardar    = new JCButton("Guardar"      ,"Guardar.png"           ,"Azul");
	JCButton btnDeshacer   = new JCButton("Deshacer"     ,"deshacer16.png"        ,"Azul");
	JCButton btnQuitarfila = new JCButton("Eliminar"     ,"boton-rojo-menos-icono-5393-16.png","Azul");
	JCButton btnAgregar    = new JCButton("Agregar"      ,"double-arrow-icone-3883-16.png"    ,"Azul");
	JCButton btnImprimir   = new JCButton("Imprimir"     ,"imprimir-16.png"       ,"Azul");
	JCButton btnModificar = new JCButton("Modificar" ,"Modify.png"                ,"Azul");
	
	String status[] = {"PENDIENTE","AUTORIZADO","CANCELADO","FINALIZADO","NEGADO"};
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmb_status = new JComboBox(status);
	
	String cuentas[] =  banco_interno.Combo_Cuentas("cuentas");
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbNombreCuenta = new JComboBox(cuentas);
	
	Border blackline, etched, raisedbevel, loweredbevel, empty;

	String[][] tablaprecargadaordenes;
    Object[] vector = new Object[7];

    String guardar_actualizar="";
   public  Cat_Ingresos_Manuales_A_Banco_Interno(){
	    this.cont.add(panel);
		this.setSize(540,430);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("Ingreso Manual A Banco Interno");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Egreso32.png"));
		this.blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
		this.panel.setBorder(BorderFactory.createTitledBorder(blackline,"Ingreso Manual A Banco Interno"));
   		
   	    this.menu_toolbar.add(btnNuevo    );
		this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.addSeparator(   );
//   	    this.menu_toolbar.add(btnModificar);
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
   	 
		int x=10, y=20,width=122,height=20, sep=130;
		this.panel.add(menu_toolbar).setBounds                        (x         ,y      ,515     ,height );
		this.panel.add(txtFolio).setBounds                            (x         ,y+=30  ,width   ,height );
		this.panel.add(cmb_status).setBounds                          (x+=sep    ,y      ,width   ,height );
		this.panel.add(new JLabel("Nombre De La Cuenta:")).setBounds  (x+=sep    ,y      ,width   ,height );
		this.panel.add(cmbNombreCuenta).setBounds                     (x+=120    ,y      ,135     ,height );
		this.panel.add(txtDescripcion).setBounds                      (x=10      ,y+=30  ,300     ,height );
		this.panel.add(btnAgregar).setBounds                          (x+=305    ,y      ,102     ,height ); 
		this.panel.add(btnQuitarfila).setBounds                       (x+110     ,y      ,99      ,height ); 
		this.panel.add(scroll_tabla).setBounds                        (x=10      ,y+=20  ,515     ,200    );
		this.panel.add(new JLabel("Observaciones:")).setBounds        (x=10      ,y+=200 ,width   ,height );
		this.panel.add(Uso).setBounds                                 (x         ,y+=15  ,515     ,50     );
		
		this.panel.add(txtFoliosolicit).setBounds                     (x         ,y+=55  ,60      ,height );		
		this.panel.add(txtSolicitante).setBounds                      (x+=60     ,y      ,320     ,height );
		this.panel.add(txtTotal).setBounds                            (x+=333    ,y      ,width   ,height );

		panel_booleano(false);
		init_tabla();
		
		btnNuevo.addActionListener(nuevo);
		btnDeshacer.addActionListener(deshacer);
		btnGuardar.addActionListener(guardar);
		btnModificar.addActionListener(opEditar);
		
		cmbNombreCuenta.addActionListener   (opSeleccionEstablecimiento);
		btnAgregar.addActionListener        (opAgregarProducto         );
		btnQuitarfila.addActionListener     (opQuitarfila              );
		btnBuscar.addActionListener         (opFiltroBuscar_orden_pago );
		btnImprimir.addActionListener       (opImprimir_Reporte        );   
        txtDescripcion.addKeyListener       (opAgregarConEnter         );
		tabla.addKeyListener                (op_validanumero_en_celda  );
		
		btnDeshacer.setToolTipText("<ESC> Tecla Directa");
		btnGuardar.setToolTipText("<CTRL+G> Tecla Directa");

		txtFoliosolicit.setEditable(false);
		txtSolicitante.setEditable(false);
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
		cmbNombreCuenta.setEnabled(boleano);
	    txtDescripcion.setEditable(boleano); 
		txaUso.setLineWrap(true); 
		txaUso.setWrapStyleWord(true);
		txaUso.setEditable(boleano);
		btnModificar.setEnabled(false);
		txtFolio.setEditable(false);
		btnGuardar.setEnabled(boleano);
		btnAgregar.setEnabled(boleano);
		btnQuitarfila.setEnabled(boleano);
    }
   
    public void panel_limpiar(){
		btnBuscar.setEnabled(true);
		cmb_status.setSelectedIndex(0);
		cmbNombreCuenta.setSelectedIndex(0);;
		txtFolio.setText("");
	    txtDescripcion.setText(""); 
		txaUso.setText("");
		txtTotal.setText("");
		modelo.setRowCount(0);
    }
	
	KeyListener op_validanumero_en_celda = new KeyListener() {
		public void keyTyped(KeyEvent e) {}
		public void keyReleased(KeyEvent e) {
			  int fila=tabla.getSelectedRow();
			  int columna=tabla.getSelectedColumn();
			if(fila==-1)fila=fila+1;
			if(columna<1 )columna=1;

			if(e.getKeyCode()==9&&columna>1) {
				columna=1;
			};
			
			if(ObjTab.validacelda(tabla,"decimal", fila,columna)){
				calculo();
			   if(ObjTab.RecorridoFocotabla_con_evento(tabla, fila,columna, "x",e).equals("si")){
						txtDescripcion.requestFocus();
			   };
			}	
		}
		public void keyPressed(KeyEvent e) {}
	};
	
	public void calculo() {
		float importe=0;
		for(int i=0;i<tabla.getRowCount();i++) {
			importe=importe+Float.valueOf(tabla.getValueAt(i, 1)+"");
		}
		txtTotal.setText(importe+"");
	};
   
	KeyListener opAgregarConEnter = new KeyListener() {
	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_ENTER){
			btnAgregar.doClick();
		}
	}
	public void keyPressed(KeyEvent e) {}
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
			txtDescripcion.setEditable(true);
			txaUso.setEditable(true);
			cmbNombreCuenta.setEnabled(true);
			cmbNombreCuenta.requestFocus();
			cmbNombreCuenta.showPopup();
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
  			if(txtFolio.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Es Requerido Busque Un Folio Guardado y lo Selecciones Para Poder Imprimir","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
				btnBuscar.doClick();
				return;
  			}else {
	  			String basedatos="2.26";
	  			String vista_previa_reporte="no";
	  			int vista_previa_de_ventana=0;
	  			String comando="banco_interno_reporte_de_transferencia "+txtFolio.getText().toString();
	  			String reporte = "Obj_Reporte_De_Banco_Interno_Movimiento.jrxml";
	  			 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
	  			return;
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
					modelo.addRow(Vector_Producto);
					txtDescripcion.setText("");
					ObjTab.RecorridoFocotabla(tabla, modelo.getRowCount()-1, 1, "x");
					tabla.setRowSelectionInterval(modelo.getRowCount()-1, modelo.getRowCount()-1);
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
				folio_inventario= new BuscarSQL().folio_siguiente(46+"");
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
			cmbNombreCuenta.setEnabled(true);
    		tabla.setEnabled(true );
		}
	};
	
    ActionListener opSeleccionEstablecimiento = new ActionListener(){
 	  public void actionPerformed(ActionEvent e){
                txtDescripcion.setEditable(true);
                btnAgregar.setEnabled(true);
                btnQuitarfila.setEnabled(true);
                txaUso.setEditable(true);
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
	
	public  String[][] tabla_guardar(){ 
		String[][] matriz = new String[modelo.getRowCount()][7] ;
		if(tabla.isEditing()){tabla.getCellEditor().stopCellEditing();}
		
		if(tabla.getRowCount()==0){
			 JOptionPane.showMessageDialog(null, "No hay Datos que Guardar", "Aviso", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
			 matriz = new String[0][0];
		}else{ 
			for(int i=0; i<tabla.getRowCount(); i++){
				matriz[i][0] =  ("Trasp.Manual F.:"); 
				matriz[i][1] =  (txtFolio.getText().toString().trim());
				matriz[i][2] =  (modelo.getValueAt(i,0).toString().trim()); 
				matriz[i][3] =  ("01/01/1900 00:00:00"); 
				matriz[i][4] =  (modelo.getValueAt(i,1).toString().trim() ); 
				matriz[i][5] =  (modelo.getValueAt(i,1).toString().trim()); 
				matriz[i][6] =  ("0" ); 
			}
		}	
		return matriz;
	}
	
	//TODO GUARDAR
	ActionListener guardar = new ActionListener(){
	public void actionPerformed(ActionEvent e){
			calculo();				   
		    String[][] tabla_guardado = tabla_guardar();
				   
			 if(txtTotal.getText().equals("")||Float.valueOf(txtTotal.getText())==0){
					JOptionPane.showMessageDialog(null, "Es Requerido Alimente Productos Con Cantidad y Precio","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
				 return;
			 }else{		 
   // @folio int ,@folio_empleado_destinatario int ,@observaciones varchar(160) ,@usuario_realiza_traspaso int ,@estatus char(1) ,@Guardar_actualizar char(1) ,@nombre_grupo varchar(100) 
						   banco_interno_traspaso.setFolio(Integer.valueOf(txtFolio.getText().toString().trim()));
						   banco_interno_traspaso.setFolio_empleado_destinatario(Integer.valueOf(txtFoliosolicit.getText().toString().trim()));
						   banco_interno_traspaso.setObservaciones(txaUso.getText().toString().trim());
						   banco_interno_traspaso.setUsuario_realiza_transpaso(Integer.valueOf(txtFoliosolicit.getText().toString().trim()));
						   banco_interno_traspaso.setEstatus(cmb_status.getSelectedItem().toString().trim());
						   banco_interno_traspaso.setGuardar_actualizar(guardar_actualizar);
						   banco_interno_traspaso.setCuenta(cmbNombreCuenta.getSelectedItem().toString().trim());
						   banco_interno_traspaso.setDatos(tabla_guardado);
						   banco_interno_traspaso.setTransaccion("46");
						if(banco_interno_traspaso.GuardarActualizar().getFolio()>0){
							txtFolio.setText(banco_interno_traspaso.getFolio()+"" );
							guardar_actualizar="";
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
	
	//TODO inicia filtro_Buscar Ingreso guardado
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
		    	
				String comandob = "banco_interno_traspasos_filtro 46";
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
				this.setTitle("Filtro De Ordenes De Pago");
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
			        		modelo.setRowCount(0);
			        		int fila = tablab.getSelectedRow();
                            txtFolio.setText(tablab.getValueAt(fila, 0).toString());  
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
						modelo.setRowCount(0);
		        		int fila = tablab.getSelectedRow();
                        txtFolio.setText(tablab.getValueAt(fila, 0).toString());  
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
			new Cat_Ingresos_Manuales_A_Banco_Interno().setVisible(true);
		}catch(Exception e){	}
	}
};
	
