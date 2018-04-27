package Cat_Contabilidad;

import java.awt.Component;
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
import javax.swing.DefaultCellEditor;
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

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Connexion;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_tabla;

@SuppressWarnings("serial")
public class Cat_Alimentacion_De_Ordenes_De_Compra_Interna extends JFrame{
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	Connexion con = new Connexion();
	JToolBar menu_toolbar       = new JToolBar();
	
	Obj_tabla  ObjTab = new Obj_tabla();

	int columnas = 3,checkbox=-1;
	public void init_tabla(){
    	this.tabla.getColumnModel().getColumn(0).setMinWidth(400);	
    	this.tabla.getColumnModel().getColumn(1).setMinWidth(80);
    	this.tabla.getColumnModel().getColumn(2).setMinWidth(120);
    	
		String comando="Select '' as Descripcion, 0 Cantidad, '' as Unidad" ;
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
	public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"Descripcion","Cantidad","Unidad"}){
		 @SuppressWarnings("rawtypes")
			Class[] types = basemovimientos();
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
			public boolean isCellEditable(int fila, int columna){if(columna>0){return true;}else{ return false;}}
	};
	JTable tabla = new JTable(modelo);
	public JScrollPane scroll_tabla = new JScrollPane(tabla);
	
	JTextField txtFolio       = new Componentes().text(new JCTextField()  ,"Folio"                        ,30   ,"String");
	JTextField txtDescripcion = new Componentes().text(new JCTextField()  ,"Descripcion Del Producto"     ,30   ,"String");
	JTextField txtSolicitante = new Componentes().text(new JCTextField()  ,"Persona Que Solicita"         ,30   ,"String");
	
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
	
	@SuppressWarnings("rawtypes")
	JComboBox cmbUnidades = new JComboBox();

	JRadioButton rbProveedor = new JRadioButton("Proveedor");
	JRadioButton rbEmpleado  = new JRadioButton("Empleado");
	ButtonGroup  grupo       = new ButtonGroup();
	
	Border blackline, etched, raisedbevel, loweredbevel, empty;

	String[][] tablaprecargadaordenes;
    Object[] vector = new Object[7];

   public  Cat_Alimentacion_De_Ordenes_De_Compra_Interna(){
	    this.cont.add(panel);
		this.setSize(650,500);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("Alimentacion De Ordenes De Compra Interna");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/articulo-icono-9036-48.png"));
		this.blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
		this.panel.setBorder(BorderFactory.createTitledBorder(blackline,"Alimentacion De Ordenes De Compra Interna"));
   	
   		this.grupo.add(rbProveedor);
   		this.grupo.add(rbEmpleado);
   		this.rbEmpleado.setSelected(true);
   		
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
		panel.add(menu_toolbar).setBounds                       (x         ,y      ,400     ,height );
		panel.add(txtFolio).setBounds                           (x         ,y+=30  ,width   ,height );
		panel.add(cmb_status).setBounds                         (x+=sep    ,y      ,width   ,height );
		panel.add(new JLabel("Destino De Mercancia:")).setBounds(x+=sep    ,y      ,width   ,height );
		panel.add(cmbEstablecimiento).setBounds                 (x+=110    ,y      ,233     ,height );
		panel.add(txtSolicitante).setBounds                     (x=20      ,y+=25  ,370     ,height );
		panel.add(btnSolicitante).setBounds                     (x+370     ,y      ,20      ,height );
		panel.add(rbEmpleado).setBounds                         (x+400     ,y      ,90      ,height );		
		panel.add(rbProveedor).setBounds                        (x+500     ,y      ,90      ,height );  
		
		panel.add(new JLabel("Uso De La Mercancia:")).setBounds (x         ,y+=20  ,width   ,height );
		panel.add(Uso).setBounds                                (x         ,y+=15  ,602     ,50     );
		panel.add(txtDescripcion).setBounds                     (x         ,y+=55  ,390     ,height );
		panel.add(btnAgregar).setBounds                         (x+395     ,y      ,102     ,height ); 
		panel.add(btnQuitarfila).setBounds                      (x+503     ,y      ,99      ,height ); 
		panel.add(scroll_tabla).setBounds                       (x         ,y+=22  ,602     ,270    );

		panel_false();
		init_tabla();
		
		Seleccionar(tabla);
		
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

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
          getRootPane().getActionMap().put("escape", new AbstractAction(){public void actionPerformed(ActionEvent e){  btnDeshacer.doClick(); } });

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_G,Event.CTRL_MASK),"guardar");
          getRootPane().getActionMap().put("guardar", new AbstractAction(){public void actionPerformed(ActionEvent e){ btnGuardar.doClick(); } });

	    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0), "guardar");
	      getRootPane().getActionMap().put("guardar", new AbstractAction(){public void actionPerformed(ActionEvent e){ btnGuardar.doClick(); } });
	             
	    this.addWindowListener(new WindowAdapter() {  public void windowOpened( WindowEvent e ){ txtFolio.requestFocus();  }  });                
    }
	
    public void panel_false(){
		cmb_status.setEnabled(false);
		cmbEstablecimiento.setEnabled(false);
	    txtDescripcion.setEditable(false); 
		txaUso.setLineWrap(true); 
		txaUso.setWrapStyleWord(true);
		txaUso.setEditable(false);
		txtSolicitante.setEditable(false);
		btnSolicitante.setEnabled(false);
		btnGuardar.setEnabled(false);
		btnAgregar.setEnabled(false);
		btnQuitarfila.setEnabled(false);
		rbEmpleado.setEnabled(false);
		rbProveedor.setEnabled(false);
    }
   
    public void panel_limpiar(){
		cmb_status.setSelectedIndex(0);
		cmbEstablecimiento.setSelectedIndex(0);;
		txtFolio.setText("");
	    txtDescripcion.setText(""); 
	    txtSolicitante.setText("");
		txaUso.setText("");
		rbEmpleado.setSelected(true);
		modelo.setRowCount(0);
    }
    
	public void RecorridoFoco(int filap,String parametrosacarfoco){
			if(ObjTab.RecorridoFocotabla(tabla, filap, 1, parametrosacarfoco).equals("si")){
				txtDescripcion.requestFocus();
			};
		}
	
	private void Seleccionar(final JTable tbl) {
	    tbl.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(MouseEvent e) {
	        	if(e.getClickCount()==1){
                     if(tbl.getSelectedColumn()==2){
                	        tbl.getColumnModel().getColumn(2).setCellEditor(new javax.swing.DefaultCellEditor(cmbUnidades));
                		    tbl.getColumn("Unidad").setCellEditor(new CargaDatosDelCombo());
					       return;
                     } else{
                    	  if(e.getClickCount() == 1 && tbl.getSelectedColumn()<2 ){
         	        		RecorridoFoco(tbl.getSelectedRow()-1,"x");
         	        	   }
                    	 return;
                     }	
	        	}
	        }
	    });
	}
	
	private class CargaDatosDelCombo extends DefaultCellEditor{
        @SuppressWarnings("rawtypes")
		public CargaDatosDelCombo(){
        	super(new JComboBox());
        }
        @SuppressWarnings({ "rawtypes", "unchecked" })
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        	JComboBox combo = (JComboBox)getComponent();
        	combo.removeAllItems();
			try {
				String unidades="BOLSA/LITROS/METROS/PIEZAS";
				String tipo_de_respuestas[] = unidades.split("/");
				for(int i=0; i<tipo_de_respuestas.length; i++)  combo.addItem(tipo_de_respuestas[i]);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
            return combo;          
        }
    }
	
	KeyListener op_validanumero_en_celda = new KeyListener() {
		public void keyTyped(KeyEvent e) {}
		public void keyReleased(KeyEvent e) {
			int fila=tabla.getSelectedRow();
			if(fila==-1)fila=fila+1;
			if(ObjTab.validacelda(tabla,"decimal", fila, 1)){
				  if(ObjTab.RecorridoFocotabla(tabla, fila, 1, "x").equals("si")){
						txtDescripcion.requestFocus();
					};
			}
		}
		public void keyPressed(KeyEvent e) {}
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
    
	ActionListener nuevo = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			panel_limpiar();
			String folio_inventario="";
			try {
				folio_inventario= new BuscarSQL().folio_siguiente(79+"");
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
					   rbEmpleado.setEnabled(true);
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
	
   ActionListener opAgregarProducto = new ActionListener(){
		public void actionPerformed(ActionEvent e){
				if(txtDescripcion.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Es Requerido Teclee La Descripcion Del Producto Para Poder Agregar","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
					txtDescripcion.requestFocus();
					return;
				}else {
					Object[] Vector_Producto = new Object[3];
					Vector_Producto[0]=txtDescripcion.getText().toUpperCase().trim();		
					Vector_Producto[1]="0";		
					Vector_Producto[2]="PIEZAS";		
					modelo.addRow(Vector_Producto);
					txtDescripcion.setText("");
					ObjTab.RecorridoFocotabla(tabla, modelo.getRowCount()-1, 1, "x");
					tabla.setRowSelectionInterval(modelo.getRowCount()-1, modelo.getRowCount()-1);
					
					tabla.getColumnModel().getColumn(2).setCellEditor(new javax.swing.DefaultCellEditor(cmbUnidades));
					tabla.getColumn("Unidad").setCellEditor(new CargaDatosDelCombo());
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
				}
			}
		}
	};
	
	ActionListener deshacer = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(tabla.getRowCount()>0){
				if(JOptionPane.showConfirmDialog(null, "Hay Datos Capturados y No Han Sido Guardados, �Desea Borrar Todo?", "Aviso", JOptionPane.INFORMATION_MESSAGE,0, new ImageIcon("Imagen/usuario-icono-noes_usuario9131-64.png") )== 0){
					panel_limpiar();
					panel_false();
					txtFolio.requestFocus();
					btnNuevo.setEnabled(true);
					return;
			     }else{
              				return;
			}
		}else{
			panel_limpiar();
			panel_false();
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
			 String ref =rbProveedor.isSelected()?"Proveedor":"Empleado";
			 String comandob=" " ;
			switch(ref){
			    case "Empleado": 		comandob = "select folio,nombre+' '+ap_paterno+' '+ap_materno as nombre from tb_empleado order by nombre"; break;
		    	case "Proveedor": 		comandob = "select folio_proveedor,nombre+' '+ap_paterno+' '+ap_materno as nombre from tb_proveedores where status=1  order by nombre"; break;
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
						txtSolicitante.setText   (tablab.getValueAt(fila,1)+"");
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
				ObjTab.Obj_Filtro(tablab, txtBuscarb.getText().toUpperCase(), columnasb,txtBuscarb);
			}
			public void keyTyped(KeyEvent arg0) {}
			public void keyPressed(KeyEvent arg0) {}		
		};
	}
		
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Alimentacion_De_Ordenes_De_Compra_Interna().setVisible(true);
		}catch(Exception e){	}
	}
};
	
