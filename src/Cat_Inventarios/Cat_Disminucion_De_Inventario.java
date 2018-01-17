package Cat_Inventarios;

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
import java.util.Vector;

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
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Cargar_Combo;
import Conexiones_SQL.Connexion;
import Conexiones_SQL.GuardarSQL;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_Filtro_Dinamico_Plus;
import Obj_Principal.Obj_tabla;
import Obj_Xml.CrearXmlString;

@SuppressWarnings("serial")
public class Cat_Disminucion_De_Inventario extends JFrame{
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	Connexion con = new Connexion();
	JToolBar menu_toolbar       = new JToolBar();
	
	Obj_tabla  ObjTab = new Obj_tabla();

	int columnas = 9,checkbox=-1;
	public void init_tabla(){
    	this.tabla.getColumnModel().getColumn(0).setMinWidth(90);	
    	this.tabla.getColumnModel().getColumn(1).setMinWidth(380);
    	this.tabla.getColumnModel().getColumn(2).setMinWidth(100);
    	this.tabla.getColumnModel().getColumn(3).setMinWidth(100);
    	this.tabla.getColumnModel().getColumn(4).setMinWidth(100);
    	this.tabla.getColumnModel().getColumn(5).setMinWidth(100);
    	this.tabla.getColumnModel().getColumn(6).setMinWidth(100);
    	
		String comando="Select '' as Cod_Prod, '' as Descripcion, 0 as  Existencia, 0 as  Disminucion, 0 as  Existencia_Total, '' as Unidad ,'' as Codigo_Principal, 0 as Costo, 0 as Precio_De_Venta " ;
		String basedatos="26",pintar="si";
		ObjTab.Obj_Refrescar(tabla,modelo, columnas, comando, basedatos,pintar,checkbox);
    }
	
	public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"Cod_Prod","Descripcion", "Existencia", "Disminucion", "Existencia_Total", "Unidad"		   ,"Codigo_Principal"		   ,"Costo"			,"Precio_De_Venta" }){
	
		@SuppressWarnings("rawtypes")
			Class[] types = new Class[]{
					java.lang.Object.class,
					java.lang.Object.class,
					java.lang.Object.class,
					java.lang.Object.class,
					java.lang.Object.class,
					java.lang.Object.class,
					java.lang.Object.class,
					java.lang.Object.class,
					java.lang.Object.class,
			};
			
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {
		         return types[columnIndex];
		    }
			public boolean isCellEditable(int fila, int columna){
				if(columna ==3)
					return true; return false;
			}
	    };
	    
	    JTable tabla = new JTable(modelo);
	    public JScrollPane scroll_tabla = new JScrollPane(tabla);
		   
		int columnas2 = 6;
		public void init_tabla_filtro_productos(){
	    	this.tabla2.getColumnModel().getColumn(0).setMinWidth(90);	
	    	this.tabla2.getColumnModel().getColumn(1).setMinWidth(400);
	    	this.tabla2.getColumnModel().getColumn(2).setMinWidth(150);
	    	this.tabla2.getColumnModel().getColumn(3).setMinWidth(150);
	    	this.tabla2.getColumnModel().getColumn(4).setMinWidth(100);
	    	this.tabla2.getColumnModel().getColumn(5).setMinWidth(100);
	    	
			String comando="exec productos_de_insumos" ;
			String basedatos="26",pintar="si";
		
			ObjTab.Obj_Refrescar(tabla2, modelo2, columnas2, comando, basedatos,pintar,checkbox);
		}
//			String establecimiento80="";
			
		 public DefaultTableModel modelo2 = new DefaultTableModel(null, new String[]{"Cod_Prod","Descripcion","Unidad","Codigo Principal","Costo","Precio De Venta"}){
				 @SuppressWarnings("rawtypes")
		 		Class[] types = new Class[]{
						java.lang.Object.class,
						java.lang.Object.class,
						java.lang.Object.class,
		  			    java.lang.Object.class,
						java.lang.Object.class,
						java.lang.Object.class,
			    };
						
				@SuppressWarnings({ "unchecked", "rawtypes" })
				public Class getColumnClass(int columnIndex) {
				         return types[columnIndex];
		        }
				
				public boolean isCellEditable(int fila, int columna2){
					if(columna2 ==3)
					return true; return false;
				}
	      };
				    
		  JTable tabla2 = new JTable(modelo2);
		  public JScrollPane scroll_tabla_filtro = new JScrollPane(tabla2);

	JTextField txtcod_prod = new Componentes().text(new JTextField(), "Codigo Del Producto", 25, "String");
	JTextField txtFiltro   = new Componentes().text(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<", 300, "String");
	
	JCButton btnQuitarfila = new JCButton("Eliminar Fila","boton-rojo-menos-icono-5393-16.png","Azul");
	JCButton btnProducto   = new JCButton("Productos"    ,"Filter-List-icon16.png","Azul");
	JCButton btnGuardar    = new JCButton("Guardar"      ,"Guardar.png","Azul");
	JCButton btnDeshacer   = new JCButton("Deshacer"     ,"deshacer16.png","Azul");
	
	String establecimiento[] = new Obj_Establecimiento().Combo_Establecimiento();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbEstablecimiento = new JComboBox(establecimiento);
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbEstablecimientoSurte = new JComboBox(establecimiento);
	
	String razones[] = new Cargar_Combo().razonesDeMovimientoDeInventario("D");
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbRazones = new JComboBox(razones);
	
	JTextField txtFolioEmpleado = new Componentes().text(new JCTextField(), "Folio", 6, "Int");
	JTextField txtEmpleado = new Componentes().text(new JCTextField(), "Nombre De Empleado", 6, "Int");
	JCButton btnFiltroEmpledo = new JCButton("", "buscar.png", "Azul");
	
	Border blackline, etched, raisedbevel, loweredbevel, empty;
	int fila=0;
    int columna=0;

	String FActividadesCargado ="";
	String[][] tablaprecargadaproductos;
	
    JTextArea txaNota 	= new Componentes().textArea(new JTextArea(), "Nota", 500);
	JScrollPane Nota = new JScrollPane(txaNota);
    
   public  Cat_Disminucion_De_Inventario(){
	   this.cont.add(panel);
		this.setSize(1024,730);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		this.setTitle("Disminución De Inventario");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/articulo-icono-9036-48.png"));
		blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
		panel.setBorder(BorderFactory.createTitledBorder(blackline,"Captura De Disminución De Inventarios"));
        cont.setBackground(new java.awt.Color(255, 255, 255));
   	    tabla.getTableHeader().setReorderingAllowed(false) ;
   	
		this.menu_toolbar.add(btnDeshacer );
		this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.add(btnGuardar  );
		
		this.menu_toolbar.setFloatable(false);
   	 
		int x=20, y=20,width=122,height=20, sep=130;
		panel.add(menu_toolbar).setBounds                  (x         ,y      ,170     ,height );
		
		panel.add(new JLabel("Establecimiento Surte:")).setBounds (x+=680	  ,y	  ,140	   , height);
		panel.add(cmbEstablecimientoSurte).setBounds       (x+=120	  ,y	  ,width+50,height );
		
		panel.add(new JLabel("Recibe:")).setBounds         (x=20	  ,y+=30  ,60	   ,height );
		panel.add(txtFolioEmpleado).setBounds              (x+=50	  ,y	  ,45	   ,height );
		panel.add(txtEmpleado).setBounds                   (x+=45	  ,y	  ,width*2 ,height );
		panel.add(btnFiltroEmpledo).setBounds              (x+=245	  ,y	  ,30	   ,height );
		
		panel.add(cmbEstablecimiento).setBounds            (x+=40	  ,y	  ,width+50,height );
		
		panel.add(new JLabel("Nota:")).setBounds           (x+200     ,y-15   ,50      ,height );
		panel.add(Nota).setBounds                          (x+200     ,y      ,390     ,50     );
		
		panel.add(txtcod_prod).setBounds                   (x=20	  ,y+=30  ,width   ,height );
		panel.add(btnProducto).setBounds                   (x+=sep-8 ,y      ,width-5   ,height );
		
		panel.add(new JLabel("Razon:")).setBounds          (x+=213	  ,y	  ,60	   ,height );
		panel.add(cmbRazones).setBounds              	   (x+=45	  ,y	  ,width+50,height );

		panel.add(txtFiltro).setBounds   		           (x=20      ,y+=27  ,840     ,height );
		panel.add(btnQuitarfila).setBounds                 (x+847     ,y      ,width   ,height ); 
		panel.add(scroll_tabla).setBounds                  (x         ,y+=23  ,972     ,555    );
		
//		JComboBox cmbEstablecimiento = new JComboBox(establecimiento);
//		JComboBox cmbRazones = new JComboBox(razones);
//		JTextField txtFolioEmpleado = new Componentes().text(new JCTextField(), "Folio", 6, "Int");
//		JTextField txtEmpleado = new Componentes().text(new JCTextField(), "Nombre De Empleado", 6, "Int");
//		JCButton btnFiltroEmpledo = new JCButton("", "buscar.png", "Azul");
		
		txtFolioEmpleado.setEditable(false);
		txtEmpleado.setEditable(false);
		
		txaNota.setLineWrap(true); 
		txaNota.setWrapStyleWord(true);
		
		init_tabla();
		modelo.setRowCount(0);
		btnDeshacer.setToolTipText("<ESC> Tecla Directa");
		btnGuardar.setToolTipText("<CTRL+G> Tecla Directa");
		
		txtFiltro.addKeyListener(opFiltrotabla);
		txtcod_prod.addKeyListener(Buscar_Datos_Producto);
		tabla.addKeyListener(op_validanumero_en_celda);
		
		btnDeshacer.addActionListener(deshacer);
		btnProducto.addActionListener(filtro_productos);
		btnGuardar.addActionListener(guardar);
		btnQuitarfila.addActionListener(opQuitarfila);
		btnFiltroEmpledo.addActionListener(filtroEmpleado);
		
		agregar(tabla);
		
		this.addWindowListener(new WindowAdapter() {
			public void windowOpened( WindowEvent e ){
				txtcod_prod.requestFocus();
			}
		});
		
		
		///FILTRO con F2
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), "producto");
            getRootPane().getActionMap().put("producto", new AbstractAction(){
                public void actionPerformed(ActionEvent e){                 	    
                	 btnProducto.doClick();
       	    	}
            });
    	///deshacer con escape
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
         	getRootPane().getActionMap().put("escape", new AbstractAction(){
            public void actionPerformed(ActionEvent e){                 	    
            	btnDeshacer.doClick();
      	    }
        });
    	///guardar con control+G
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_G,Event.CTRL_MASK),"guardar");
          getRootPane().getActionMap().put("guardar", new AbstractAction(){
             public void actionPerformed(ActionEvent e){                 	    
            	 btnGuardar.doClick();
       	    }
         });
        ///guardar con F12
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0), "guardar");
              getRootPane().getActionMap().put("guardar", new AbstractAction(){
                  public void actionPerformed(ActionEvent e){                 	    
                	  btnGuardar.doClick();
              }
         });
	                 
    }
   
	KeyListener Buscar_Datos_Producto = new KeyListener() {
		public void keyTyped(KeyEvent e){}
		public void keyReleased(KeyEvent e) {}
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				buscar_producto(txtcod_prod.getText().trim());
			}
		}
	};
	
	KeyListener op_validanumero_en_celda = new KeyListener() {
		public void keyTyped(KeyEvent e) {}
		public void keyReleased(KeyEvent e) {
			fila=tabla.getSelectedRow();
			if(ObjTab.validacelda(tabla,"decimal", fila, columna)){
				  RecorridoFoco(fila,"x"); 
				  calculo_diferencia(fila);
			}
		}
		public void keyPressed(KeyEvent e) {}
	};
	
	KeyListener opFiltrotabla = new KeyListener(){
	public void keyReleased(KeyEvent arg0) {
		int[] columnas ={0,1,2,3,4,5,6};
		new Obj_Filtro_Dinamico_Plus(tabla , txtFiltro.getText().toString().trim().toUpperCase(), columnas  );
	}
	public void keyTyped(KeyEvent arg0) {}
	public void keyPressed(KeyEvent arg0) {}	
    };
	
	public void calculo_diferencia(int fila){
  		  double existencia=Double.valueOf(tabla.getValueAt(fila,2).toString());
		  double disminucion=Double.valueOf(tabla.getValueAt(fila,3).toString());
		  double valor=existencia - disminucion;
		 tabla.setValueAt(valor, fila, 4);
	 }
	
	
	ActionListener filtroEmpleado = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			new Cat_Seleccion_De_Usuario().setVisible(true);
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
	
	ActionListener filtro_productos = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			new Cat_Filtro_De_Productos().setVisible(true);
		}
	};
	
	ActionListener deshacer = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(tabla.getRowCount()>0){
				if(JOptionPane.showConfirmDialog(null, "Hay Datos Capturados y No Han Sido Guardados, ¿Desea Borrar Todo?", "Aviso", JOptionPane.INFORMATION_MESSAGE,0, new ImageIcon("Imagen/usuario-icono-noes_usuario9131-64.png") )== 0){
					deshacer();
					return;
			     }else{
              		return;
			     }
		}else{
			deshacer();
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
				 String camposRequeridos = validaCampos();
				 
				 if(camposRequeridos.equals("")){
					 
					 int folioEmpleadoRecibe = Integer.valueOf(txtFolioEmpleado.getText().trim());
					 String estabRecibe = cmbEstablecimiento.getSelectedItem().toString().trim();
					 String estabSurte = cmbEstablecimientoSurte.getSelectedItem().toString().trim();
					 String razon = cmbRazones.getSelectedItem().toString().trim();
					 
						 int[] ignorarColumnas = {1,2,4,5,6};
						 String xml = new CrearXmlString().CadenaXML(tabla, ignorarColumnas);
//					 	 System.out.println(xml);
						 
						  if(new GuardarSQL().Entrada_De_Insumos(xml,txaNota.getText().toString().trim(),estabRecibe,folioEmpleadoRecibe,razon,estabSurte,"disminucion")){
				                JOptionPane.showMessageDialog(null, "Los Insumos Se Guardaron Correctamente", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
				            	 deshacer();
					      }else{
							JOptionPane.showMessageDialog(null, "El Registro No Pudo Ser Guardado", "Avise Al Administrador Del Sistema !!!",JOptionPane.ERROR_MESSAGE, new ImageIcon("Imagen/usuario-icono-eliminar5252-64.png"));
					    	return;
					      }
					  
				 }else{
					 JOptionPane.showMessageDialog(null, "Los Siguientes Campos Son Requeridos:\n"+camposRequeridos, "Avise Al Administrador Del Sistema !!!",JOptionPane.ERROR_MESSAGE, new ImageIcon("Imagen/usuario-icono-eliminar5252-64.png"));
				    	return;
				 }
				 
			 }
		}			
    };
    
    public String validaCampos(){
    	String error = "";
    	
    	error+= cmbEstablecimientoSurte.getSelectedIndex()==0?"Establecimiento Surte\n":"";
    	error+=cmbEstablecimiento.getSelectedIndex()==0?"Establecimiento\n":"";
    	error+=txtFolioEmpleado.getText().equals("")?"Recibe\n":"";
    	error+=cmbRazones.getSelectedIndex()==0?"Razon\n":"";
    	
    	return error;
    }
    
	public void deshacer(){
		txaNota.setText("");
		txtcod_prod.setText("");
		modelo.setRowCount(0);
		btnQuitarfila.setEnabled(false);
		cmbEstablecimientoSurte.setEnabled(true);
	}
	
	@SuppressWarnings("rawtypes")
	Vector vector = new Vector();
	public void buscar_producto(String folio){
		
		if(cmbEstablecimientoSurte.getSelectedIndex()!=0){
			int testigo=0;
		     if(!txtcod_prod.getText().equals("")){
		    	 txtFiltro.setText("");
		    	 
		    	int[] columnas ={0,1,2,3,4,5};
		    	new Obj_Filtro_Dinamico_Plus(tabla , txtFiltro.getText().toString().trim().toUpperCase(), columnas  );
		    	 
				if(new BuscarSQL().existe_Insumo(folio.trim()+"")){
					vector=new BuscarSQL().getBuscarProductoDeInsumos(folio.toString().trim(),cmbEstablecimientoSurte.getSelectedItem().toString().trim());
					
					for(int i=0; i<tabla.getRowCount(); i++){
						if(tabla.getValueAt(i, 0).toString().trim().equals(vector.get(0).toString().trim())){
							testigo=1;
				         	 JOptionPane.showMessageDialog(null, "El Producto Ya Existe En La Captura", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				         	   fila=i+1;
						 };							
					  }
					
					  if(testigo==0){
						  
					  		cmbEstablecimientoSurte.setEnabled(false);
						  	  modelo.addRow(vector);
					 		  txtcod_prod.setText("");
					 		  fila=tabla.getRowCount();
			 		  
					 		  getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
						  	       KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "BUSCA");
					 		  
							  getRootPane().getActionMap().put("BUSCA", new AbstractAction(){
							        @Override
							        public void actionPerformed(ActionEvent e){
							        	  columna=3;
							              RecorridoFoco(fila, "no");
					                 }
							   });
					      }
				}else{
					fila=1;
					JOptionPane.showMessageDialog(null, "El Codigo Esta Mal Escrito o El Producto No Existe" ,"Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
					return;
				}
			}else{
				fila=1;
				JOptionPane.showMessageDialog(null, "Es Requerido Teclear Un Codigo " ,"Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				txtcod_prod.requestFocus();
				return;
			}
	     
		}else{
			JOptionPane.showMessageDialog(null, "Es Necesario Seleccionar El Establecimiento Surtidor", "Aviso",JOptionPane.ERROR_MESSAGE, new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
	    	return;
		}
	}
	
	private void agregar(final JTable tbl) {
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
	        public void mouseClicked(MouseEvent e) {
	        	if(e.getClickCount() == 1){
	        		columna=3;
	        		RecorridoFoco(tbl.getSelectedRow()-1,"x");
	        	}
	        }
        });
    }
	
	public void RecorridoFoco(int filap,String parametrosacarfoco){
		if(ObjTab.RecorridoFocotabla(tabla, filap, 3, parametrosacarfoco).equals("si")){
			txtcod_prod.requestFocus();
		};
	}
	
	//TODO Filtro De productos
	public class Cat_Filtro_De_Productos extends JDialog{
		  Container cont = getContentPane();
		  JLayeredPane panel = new JLayeredPane();
		  Connexion con = new Connexion();
		  Runtime R = Runtime.getRuntime();
		@SuppressWarnings("rawtypes")
		private TableRowSorter trsfiltro;
		
		JTextField txtFiltrop = new Componentes().text(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<", 300, "String");
		Border blackline, etched, raisedbevel, loweredbevel, empty;
	    
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Cat_Filtro_De_Productos(){
			int ancho = 1024;
			int alto = Toolkit.getDefaultToolkit().getScreenSize().height-50;
			this.setSize(ancho, alto);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setTitle("Filtro De Busqueda De Productos");
			this.setModal(true);
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/lista-icono-7220-32.png"));
			blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
			panel.setBorder(BorderFactory.createTitledBorder(blackline,"Doble Click A El Producto Deseado"));
			this.cont.add(panel);

			trsfiltro = new TableRowSorter(modelo2); 
			tabla2.setRowSorter(trsfiltro);
			txtFiltrop.setToolTipText("Filtro Por Producto");
			txtFiltrop.addKeyListener(opFiltro);

			int y = 20;
			panel.add(txtFiltrop).setBounds(15,y,500,20);
			panel.add(scroll_tabla_filtro).setBounds(15,y+=20,ancho-30,alto-80);
	        
			if(FActividadesCargado.equals("S")){
				datos_tabla_precargados();
			}else{
				init_tabla_filtro_productos();
				tablaprecargadaproductos= ObjTab.tabla_guardar(tabla2);
			  FActividadesCargado="S";
			}
			
			agregar(tabla2);
		}

		public void datos_tabla_precargados(){
			 modelo2.setRowCount(0);
			 String[][] tablacompleta =tablaprecargadaproductos;
			 Object[] vector = new Object[columnas2];
			 
			for(int i=0;i<tablacompleta.length;i++){
			   for(int j=0;j<columnas2;j++){
				   vector[j] = tablacompleta[i][j].toString();
				}
				modelo2.addRow(vector);
			}
		}

		KeyListener opFiltro = new KeyListener(){
			@SuppressWarnings("unchecked")
			public void keyReleased(KeyEvent arg0) {
				int[] columnas ={0,1,2,3,4,5};
				new Obj_Filtro_Dinamico_Plus(tabla2 , txtFiltrop.getText().toString().trim().toUpperCase(), columnas  );
				trsfiltro.setRowFilter(RowFilter.regexFilter(txtFiltrop.getText(), 0));
			}
			public void keyTyped(KeyEvent arg0) {}
			public void keyPressed(KeyEvent arg0) {}	
	    };
		
		private void agregar(final JTable tbl) {
			tbl.addMouseListener(new MouseListener() {
				public void mouseReleased(MouseEvent e) {
						if(e.getClickCount() == 2){
							int fila_Select = tabla2.getSelectedRow();
			    			String folio =  tabla2.getValueAt(fila_Select, 0).toString().trim();
			    			
			    			dispose();
			    			txtcod_prod.setText(folio.trim());
			    			buscar_producto(folio.trim());
			    			
			    			RecorridoFoco(tabla.getRowCount(), "no");
						}
				}
				public void mousePressed(MouseEvent e) {}
				public void mouseExited(MouseEvent e) {}
				public void mouseEntered(MouseEvent e) {}
				public void mouseClicked(MouseEvent e) {}
			});
		}
	}
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Disminucion_De_Inventario().setVisible(true);
		}catch(Exception e){}
	}
	
//	filtro de Empleados con usuario en SCOI y con status vigente--------------------------------------------------------------------------------
		public class Cat_Seleccion_De_Usuario extends JDialog {
			
			Container cont = getContentPane();
			JLayeredPane campo = new JLayeredPane();
			
			int checkbox=-1;
			@SuppressWarnings("rawtypes")
			public Class[] tipos(int columnas){
				Class[] tip = new Class[columnas];
				
				for(int i =0; i<columnas; i++){
					if(i==checkbox){
						tip[i]=java.lang.Boolean.class;
					}else{
						tip[i]=java.lang.Object.class;
					}
					
				}
				return tip;
			}
			
			public void init_tabla(){
		    	this.tabla.getColumnModel().getColumn(0).setMinWidth(30);		
		    	this.tabla.getColumnModel().getColumn(1).setMinWidth(300);
		    	this.tabla.getColumnModel().getColumn(2).setMinWidth(410);
		    	
		    	int columnas = modelo.getColumnCount();
		    	
				String comando="exec select_empleados_para_entrega_de_insumos";
				String basedatos="26",pintar="si";
				new Obj_tabla().Obj_Refrescar(tabla,modelo, columnas, comando, basedatos,pintar,checkbox);
		    }
			
		 public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"Folio", "Nombre De Colaborador", "Establecimiento"}){
			 @SuppressWarnings("rawtypes")
				Class[] types = tipos(this.getColumnCount());
				
				@SuppressWarnings({ "unchecked", "rawtypes" })
				public Class getColumnClass(int columnIndex) {
		         return types[columnIndex];
		     }
				
		     public boolean isCellEditable(int fila, int columna){
		    	 if(columna ==checkbox)
						return true; return false;
				}
		    };
		    
		    JTable tabla = new JTable(modelo);
			public JScrollPane scroll_tabla = new JScrollPane(tabla);
			
		JTextField txtNombre_Completo2 = new Componentes().text(new JTextField(), "Buscar", 250, "String");
		
		public Cat_Seleccion_De_Usuario(){
			
			this.setModal(true);
			
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("imagen/usuario-busquedaicono-4661-64.png"));
			this.setTitle("Filtro de Empleados");
			campo.setBorder(BorderFactory.createTitledBorder("Filtro De Empleado"));
			
			campo.add(txtNombre_Completo2).setBounds(15,20,300,20);
			campo.add(scroll_tabla).setBounds(15,42,450,565);
			
			cont.add(campo);
			
			init_tabla();
			agregar(tabla);
			
			txtNombre_Completo2.addKeyListener(op_filtro);
			
			this.setSize(490,650);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			
			tabla.addKeyListener(seleccionEmpleadoconteclado);
			
		//      asigna el foco al JTextField del nombre deseado al arrancar la ventana
		    this.addWindowListener(new WindowAdapter() {
		            public void windowOpened( WindowEvent e ){
		            	txtNombre_Completo2.requestFocus();
		         }
		    });
		      
		//     pone el foco en el txtFolio al presionar la tecla scape
		      getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
		         KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "foco");
		      
		      getRootPane().getActionMap().put("foco", new AbstractAction(){
		          @Override
		          public void actionPerformed(ActionEvent e)
		          {
		        	  txtNombre_Completo2.setText("");
		              txtNombre_Completo2.requestFocus();
		          }
		      });
		      
		//        pone el foco en la tabla al presionar f4
		      getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
		         KeyStroke.getKeyStroke(KeyEvent.VK_F4 , 0), "dtabla");
		      
		      getRootPane().getActionMap().put("dtabla", new AbstractAction(){
		          @Override
		          public void actionPerformed(ActionEvent e)
		          {
		        	tabla.requestFocus();
		          }
		      });
			 
			
		}
		
		private void agregar(final JTable tbl) {
		    tbl.addMouseListener(new java.awt.event.MouseAdapter() {
		        public void mouseClicked(MouseEvent e) {
		        	if(e.getClickCount() == 2){
		    			int fila = tabla.getSelectedRow();
		    			Object folio =  tabla.getValueAt(fila, 0).toString().trim();
		    			Object nombre =  tabla.getValueAt(fila, 1).toString().trim();
		    			dispose();
		    			txtFolioEmpleado.setText(folio+"");
		    			txtEmpleado.setText(nombre+"");
		        	}
		        }
		    });
		}
		
		KeyListener op_filtro = new KeyListener(){
			public void keyReleased(KeyEvent arg0) {
				int[] columnas ={0,1,2};
				new Obj_Filtro_Dinamico_Plus(tabla, txtNombre_Completo2.getText().toString().trim().toUpperCase(), columnas);
			}
			public void keyTyped(KeyEvent arg0)   {}
			public void keyPressed(KeyEvent arg0) {}		
		};
		
		KeyListener seleccionEmpleadoconteclado = new KeyListener() {
			@SuppressWarnings("static-access")
			@Override
			public void keyTyped(KeyEvent e) {
				char caracter = e.getKeyChar();
				
				if(caracter==e.VK_ENTER){
				int fila=tabla.getSelectedRow()-1;
				String folio = tabla.getValueAt(fila,0).toString().trim();
				String nombre = tabla.getValueAt(fila,1).toString().trim();
					
				txtFolioEmpleado.setText(folio);
				txtEmpleado.setText(nombre);
				dispose();
				}
			}
			@Override
			public void keyPressed(KeyEvent e){}
			@Override
			public void keyReleased(KeyEvent e){}
									
		};
		
}
		
};
	
