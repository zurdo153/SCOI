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

import Cat_Principal.EmailSenderService;
import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Connexion;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_tabla;

@SuppressWarnings("serial")
public class Cat_Solicitud_De_Orden_De_Gasto extends JFrame{
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	Connexion con = new Connexion();
	JToolBar menu_toolbar       = new JToolBar();
	
	Obj_Usuario usuario = new Obj_Usuario().LeerSession();
	Obj_tabla  ObjTab = new Obj_tabla();

	int columnas = 4,checkbox=-1;
	public void init_tabla(){
    	this.tabla.getColumnModel().getColumn(0).setMinWidth(500);	
    	this.tabla.getColumnModel().getColumn(1).setMinWidth(90);
    	this.tabla.getColumnModel().getColumn(2).setMinWidth(90);
    	this.tabla.getColumnModel().getColumn(3).setMinWidth(98);
    	
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
	public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"Descripcion","P.Unitario","Cantidad","Importe"}){
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
	JTextField txtProveedor   = new Componentes().text(new JCTextField()  ,"Proveedor"                 ,300  ,"String");
	JTextField txtSolicitante = new Componentes().text(new JCTextField()  ,"Solicitante"               ,300  ,"String");
	JTextField txtFecha       = new Componentes().text(new JCTextField()  ,"Fecha"                     ,60   ,"String");
	JTextField txtTotal       = new Componentes().text(new JCTextField()  ,"Total"                     ,30   ,"String");
	
    JTextArea txaUso       = new Componentes().textArea(new JTextArea(), "Uso De La Mercancia", 300);
	JScrollPane Uso        = new JScrollPane(txaUso);

	JCButton btnBuscar     = new JCButton("Buscar"       ,"Filter-List-icon16.png","Azul"); 
	JCButton btnNuevo      = new JCButton("Nuevo"        ,"Nuevo.png"             ,"Azul");
	JCButton btnGuardar    = new JCButton("Guardar"      ,"Guardar.png"           ,"Azul");
	JCButton btnDeshacer   = new JCButton("Deshacer"     ,"deshacer16.png"        ,"Azul");
	JCButton btnSolicitante= new JCButton(""             ,"Usuario.png"           ,"Azul");	
	JCButton btnQuitarfila = new JCButton("Eliminar"     ,"boton-rojo-menos-icono-5393-16.png","Azul");
	JCButton btnAgregar    = new JCButton("Agregar"      ,"double-arrow-icone-3883-16.png"    ,"Azul");
	
	String status[] = {"Vigente","Cancelado"};
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmb_status = new JComboBox(status);
	
	String establecimientoScoi[] = new Obj_Establecimiento().Combo_Establecimiento201();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbEstablecimiento = new JComboBox(establecimientoScoi);

	JRadioButton rbProveedorCont = new JRadioButton("Proveedor Contado");
	JRadioButton rbProveedor     = new JRadioButton("Proveedor");
	ButtonGroup  grupo           = new ButtonGroup();
	
	Border blackline, etched, raisedbevel, loweredbevel, empty;

	String[][] tablaprecargadaordenes;
    Object[] vector = new Object[7];

   public  Cat_Solicitud_De_Orden_De_Gasto(){
	    this.cont.add(panel);
		this.setSize(825,600);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("Solicitud De Orden De Gasto");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/articulo-icono-9036-48.png"));
		this.blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
		this.panel.setBorder(BorderFactory.createTitledBorder(blackline,"Solicitud De Orden De Gasto"));
   	
   		this.grupo.add(rbProveedor);
   		this.grupo.add(rbProveedorCont);
   		this.rbProveedor.setSelected(true);
   		
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
		this.menu_toolbar.setFloatable(false);
   	 
		int x=20, y=20,width=122,height=20, sep=130;
		this.panel.add(menu_toolbar).setBounds                        (x         ,y      ,330     ,height );
		this.panel.add(txtSolicitante).setBounds                      (x+360     ,y      ,420     ,height );
		this.panel.add(txtFolio).setBounds                            (x         ,y+=30  ,width   ,height );
		this.panel.add(cmb_status).setBounds                          (x+=sep    ,y      ,width   ,height );
		this.panel.add(new JLabel("Sucursal Solicitante:")).setBounds (x+=sep    ,y      ,width   ,height );
		this.panel.add(cmbEstablecimiento).setBounds                  (x+=100    ,y      ,250     ,height );
		this.panel.add(new JLabel("Fecha Actual:")).setBounds         (x+=270    ,y      ,width   ,height );
		this.panel.add(txtFecha).setBounds                            (x+=72     ,y      ,77      ,height );
		this.panel.add(txtFolio_prv).setBounds                        (x=20      ,y+=25  ,50      ,height );
		this.panel.add(txtProveedor).setBounds                        (x+=50     ,y      ,450     ,height );
		this.panel.add(btnSolicitante).setBounds                      (x+450     ,y      ,40      ,height );
		this.panel.add(rbProveedor).setBounds                         (x+500     ,y      ,90      ,height );		
		this.panel.add(rbProveedorCont).setBounds                     (x+600     ,y      ,130     ,height );  
		
		this.panel.add(new JLabel("Descripcion Del Gasto:")).setBounds(x=20      ,y+=20  ,width   ,height );
		this.panel.add(Uso).setBounds                                 (x         ,y+=15  ,780     ,50     );
		this.panel.add(txtDescripcion).setBounds                      (x         ,y+=55  ,560     ,height );
		this.panel.add(btnAgregar).setBounds                          (x+=565    ,y      ,102     ,height ); 
		this.panel.add(btnQuitarfila).setBounds                       (x+115     ,y      ,99      ,height ); 
		this.panel.add(scroll_tabla).setBounds                        (x=20      ,y+=22  ,780     ,270    );
		this.panel.add(txtTotal).setBounds                            (x+682     ,y+=270 ,98      ,height );

		panel_booleano(false);
		init_tabla();
		
		btnNuevo.addActionListener(nuevo);
		btnDeshacer.addActionListener(deshacer);
		btnGuardar.addActionListener(guardar);
		cmbEstablecimiento.addActionListener(opSeleccionEstablecimiento);
		btnSolicitante.addActionListener    (opFiltroBuscarSolicitante );
		btnAgregar.addActionListener        (opAgregarProducto         );
		btnQuitarfila.addActionListener     (opQuitarfila              );
		
        txtDescripcion.addKeyListener       (opAgregarConEnter         );
		tabla.addKeyListener                (op_validanumero_en_celda  );
		
		btnDeshacer.setToolTipText("<ESC> Tecla Directa");
		btnGuardar.setToolTipText("<CTRL+G> Tecla Directa");

		try { txtFecha.setText(new BuscarSQL().fecha(0).toString());} catch (SQLException e1) {e1.printStackTrace();}
		txtFecha.setEditable(false);
		txtSolicitante.setEditable(false);
		txtFolio_prv.setEditable(false);
		txtTotal.setEditable(false);
		txtSolicitante.setText(usuario.getNombre_completo());
		
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
	    txtDescripcion.setEditable(boleano); 
		txaUso.setLineWrap(true); 
		txaUso.setWrapStyleWord(true);
		txaUso.setEditable(boleano);
		txtProveedor.setEditable(boleano);
		btnSolicitante.setEnabled(boleano);
		btnGuardar.setEnabled(boleano);
		btnAgregar.setEnabled(boleano);
		btnQuitarfila.setEnabled(boleano);
		rbProveedorCont.setEnabled(boleano);
		rbProveedor.setEnabled(boleano);
    }
   
    public void panel_limpiar(){
		cmb_status.setSelectedIndex(0);
		cmbEstablecimiento.setSelectedIndex(0);;
		txtFolio.setText("");
	    txtDescripcion.setText(""); 
	    txtProveedor.setText("");
	    txtFolio_prv.setText("");
		txaUso.setText("");
		txtTotal.setText("");
		rbProveedorCont.setSelected(true);
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
				calculo(fila);
						  if(ObjTab.RecorridoFocotabla_con_evento(tabla, fila,columna, "x",e).equals("si")){
								txtDescripcion.requestFocus();
						  };
			}	
		}
		public void keyPressed(KeyEvent e) {}
	};
	
	public void calculo(int filap) {
		tabla.setValueAt(Integer.valueOf(tabla.getValueAt(filap, 1)+"") * Integer.valueOf(tabla.getValueAt(filap, 2)+"")   , filap, 3);
		
		float importe=0;
		for(int i=0;i<tabla.getRowCount();i++) {
			importe=importe+Integer.valueOf(tabla.getValueAt(i, 3)+"");
			
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
						calculo(0);
					}else {
						txtTotal.setText("");
					}
					
				}
			}
		}
	};
	
	ActionListener nuevo = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			panel_limpiar();
			String folio_inventario="";
			try {
				folio_inventario= new BuscarSQL().folio_siguiente(84+"");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			txtFolio.setText(folio_inventario);
			txtFolio.setEditable(false);
			btnGuardar.setEnabled(true);
			btnBuscar.setEnabled(false);
			btnNuevo.setEnabled(false);
			cmbEstablecimiento.setEnabled(true);
			cmbEstablecimiento.requestFocus();
			cmbEstablecimiento.showPopup();
		}
	};
	
    ActionListener opSeleccionEstablecimiento = new ActionListener(){
 	  public void actionPerformed(ActionEvent e){
				   if(cmbEstablecimiento.getSelectedIndex()!=0) {
					   btnSolicitante.setEnabled(true);
					   rbProveedorCont.setEnabled(true);
					   rbProveedor.setEnabled(true);
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
			if(tabla.getRowCount()>0){
				if(JOptionPane.showConfirmDialog(null, "Hay Datos Capturados y No Han Sido Guardados, �Desea Borrar Todo?", "Aviso", JOptionPane.INFORMATION_MESSAGE,0, new ImageIcon("Imagen/usuario-icono-noes_usuario9131-64.png") )== 0){
					panel_limpiar();
					panel_booleano(false);
					txtFolio.requestFocus();
					btnNuevo.setEnabled(true);
					return;
			     }else{
              				return;
			}
		}else{
			panel_limpiar();
			panel_booleano(false);;
			txtFolio.requestFocus();
			btnNuevo.setEnabled(true);
			return;
		}
		}
	};
	
	ActionListener guardar = new ActionListener(){
	public void actionPerformed(ActionEvent e){
			 String[][] tabla_guardado = ObjTab.tabla_guardar(tabla);
			 if(tabla_guardado.length==0){
				 return;
			 }else{		 
				 
				 
//					new EmailSenderService().enviarcorreo(servicios_solicitud.getCorreos(),servicios_solicitud.getCantidad_de_correos(),Mensaje, "cUERPO dEL MENSAJE");
					
//				 Obj_Alimentacion_De_Inventarios_Parciales Inventarios_Parciales= new Obj_Alimentacion_De_Inventarios_Parciales();
//				  Inventarios_Parciales.setTabla_obj(tabla_guardado);
////				  Inventarios_Parciales.setUsos    (txaUso.getText().toString().trim()+" ");
//				  Inventarios_Parciales.setStatus("V");
//				  
//				  if(Inventarios_Parciales.Guardar_inventario_parcial()){
//		                JOptionPane.showMessageDialog(null, "Se Guardo Correctamente el inventario parcial", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
////		           	btnReporte.doClick();
//			      }else{
//					JOptionPane.showMessageDialog(null, "El Registro No Se Guardo", "Avise Al Administrador Del Sistema !!!",JOptionPane.ERROR_MESSAGE, new ImageIcon("Imagen/usuario-icono-eliminar5252-64.png"));
//			    	return;
//			      }
			 }
	  }			
    };
	
	//TODO inicia filtro_Buscar	
	public class Cat_Filtro_Buscar_Proveedor extends JDialog{
		Container contfb = getContentPane();
		JLayeredPane panelfb = new JLayeredPane();
		Connexion con = new Connexion();
		Obj_tabla ObjTab =new Obj_tabla();
		int columnasb = 2,checkbox=-1;
		public void init_tablafp(){
	    	this.tablab.getColumnModel().getColumn( 0).setMinWidth(55);
	    	this.tablab.getColumnModel().getColumn( 1).setMinWidth(350);
			 String ref =rbProveedor.isSelected()?"Proveedor":"Proveedor Contado";
			 String comandob=" " ;
			switch(ref){
			    case "Proveedor Contado":comandob = "select folio_proveedor,nombre+' '+ap_paterno+' '+ap_materno as nombre from tb_proveedores where status=1  order by nombre"; break;
		    	case "Proveedor": 		 comandob = " SELECT cod_prv,rtrim(ltrim(upper(razon_social))) from bmsizagar.dbo.proveedores order by razon_social "; break;
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
		     
		JTextField txtBuscarb  = new Componentes().text(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<", 500, "String");
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
	        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
		        	if(e.getClickCount()==1){
		        		int fila = tablab.getSelectedRow();
		        		txtFolio_prv.setText (tablab.getValueAt(fila,0)+"");
		        		txtProveedor.setText (tablab.getValueAt(fila,1)+"");
						txtDescripcion.setEditable(true);
						btnAgregar.setEnabled(true);
						btnQuitarfila.setEnabled(true);
						txaUso.setEditable(true);
						txaUso.requestFocus();
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
		
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Solicitud_De_Orden_De_Gasto().setVisible(true);
		}catch(Exception e){	}
	}
};
	
