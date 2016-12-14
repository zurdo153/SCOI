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
import javax.swing.KeyStroke;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Connexion;
import Conexiones_SQL.Generacion_Reportes;
import Obj_Compras.Obj_Alimentacion_De_Inventarios_Parciales;
import Obj_Compras.Obj_Cotizaciones_De_Un_Producto;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_Filtro_Dinamico_Plus;
import Obj_Principal.Obj_tabla;

@SuppressWarnings("serial")
public class Cat_Alimentacion_De_Inventarios_Parciales extends JFrame{
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	Connexion con = new Connexion();
	 Obj_tabla  Objetotabla = new Obj_tabla();
	
	int columnas = 7,checkbox=-1;
	public void init_tabla(){
    	this.tabla.getColumnModel().getColumn(0).setMinWidth(90);	
    	this.tabla.getColumnModel().getColumn(1).setMinWidth(380);
    	this.tabla.getColumnModel().getColumn(2).setMinWidth(100);
    	this.tabla.getColumnModel().getColumn(3).setMinWidth(100);
    	this.tabla.getColumnModel().getColumn(4).setMinWidth(100);
    	this.tabla.getColumnModel().getColumn(5).setMinWidth(100);
    	this.tabla.getColumnModel().getColumn(6).setMinWidth(100);
    	
		String comando="Select '' as Codigo_Producto, '' as Descripcion, 0 as  Existencia, 0 as Existencia_Fisica ,0 as Diferencia, 0 as Ultimo_Costo, 0 as Costo_Promedio " ;
		String basedatos="26",pintar="si";
		Objetotabla.Obj_Refrescar(tabla,modelo, columnas, comando, basedatos,pintar,checkbox);
    }
	
  public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"Codigo Producto","Descripcion","Existencia","Existencia Fisica","Diferencia","Ultimo_Costo","Costo_Promedio" }){
	 @SuppressWarnings("rawtypes")
		Class[] types = new Class[]{
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
	
	JTextField txtFolio = new Componentes().text(new JCTextField(), "Folio Del Inventario", 30, "String");
	JTextField txtcod_prod =new Componentes().text(new JTextField(), "Codigo Del Producto", 25, "String");
	JTextField txtFiltro = new Componentes().text(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<", 300, "String");
	
	JCButton btnQuitarfila= new JCButton("Eliminar Fila","boton-rojo-menos-icono-5393-16.png","Azul");
	JCButton btnProducto  = new JCButton("Productos"    ,"Filter-List-icon16.png","Azul");
	JCButton btnReporte   = new JCButton("Reporte"      ,"Lista.png","Azul");
	JCButton btnBuscar    = new JCButton("Inventarios"  ,"Filter-List-icon16.png","Azul"); 
	JCButton btnNuevo     = new JCButton("Nuevo"        ,"Nuevo.png","Azul");
	JCButton btnEditar    = new JCButton("Editar"       ,"editara.png","Azul");
	JCButton btnGuardar   = new JCButton("Guardar"      ,"Guardar.png","Azul");
	JCButton btnDeshacer  = new JCButton("Deshacer"     ,"deshacer16.png","Azul");
	
	
	String establecimiento[] = new Obj_Establecimiento().Combo_Establecimiento201();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbEstablecimiento = new JComboBox(establecimiento);
	
	Border blackline, etched, raisedbevel, loweredbevel, empty;
	int fila=0;
    int columna=0;
    Object[] vector = new Object[7];
    
    JTextArea txaNota 	= new Componentes().textArea(new JTextArea(), "Nota", 500);
	JScrollPane Nota = new JScrollPane(txaNota);
    
   public  Cat_Alimentacion_De_Inventarios_Parciales(){
	   this.cont.add(panel);
		this.setSize(1024,768);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		this.setTitle("Alimentacion De Inventarios Parciales");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/articulo-icono-9036-48.png"));
		blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
		panel.setBorder(BorderFactory.createTitledBorder(blackline,"Alimentacion De Inventarios Parciales Fisicos De Establecimientos "));
        cont.setBackground(new java.awt.Color(255, 255, 255));
   	    tabla.getTableHeader().setReorderingAllowed(false) ;
   	
		int x=20, y=20,width=122,height=20, sep=135;
		panel.add(txtFolio).setBounds                      (x         ,y      ,width   ,height );
		panel.add(btnBuscar).setBounds                     (x+=sep    ,y      ,width   ,height );
		panel.add(cmbEstablecimiento).setBounds            (x+=sep+30 ,y      ,width+60,height );
		panel.add(new JLabel("Nota:")).setBounds           (x+sep+60  ,y-15   ,50      ,height );
		panel.add(Nota).setBounds                          (x+sep+60  ,y      ,480     ,50     );
		
		x=20;
		panel.add(txtcod_prod).setBounds                   (x         ,y+=30  ,width   ,height );
		panel.add(btnProducto).setBounds                   (x+=sep    ,y      ,width   ,height );
		
		x=20;
		panel.add(txtFiltro).setBounds   		           (x         ,y+=27  ,800     ,height );
		panel.add(btnQuitarfila).setBounds                 (x+847     ,y      ,width   ,height ); 
		panel.add(scroll_tabla).setBounds                  (x         ,y+=23  ,972     ,580    );
		panel.add(btnNuevo  ).setBounds                    (x         ,y+=600 ,width   ,height );
		panel.add(btnDeshacer).setBounds                   (x+=sep    ,y      ,width   ,height );
		panel.add(btnGuardar).setBounds                    (x+=sep    ,y      ,width   ,height );
		panel.add(btnReporte).setBounds                    (x+=sep    ,y      ,width   ,height );
//		panel.add(btnEditar).setBounds                     (x+=sep    ,y    ,width   ,height );
		
		txaNota.setLineWrap(true); 
		txaNota.setWrapStyleWord(true);
		txaNota.setEditable(false);
		
		txtcod_prod.setEnabled(false);
		btnGuardar.setEnabled(false);
		btnQuitarfila.setEnabled(false);
		btnProducto.setEnabled(false);
		cmbEstablecimiento.setEnabled(false);
		
		init_tabla();
		modelo.setRowCount(0);
		btnDeshacer.setToolTipText("<ESC> Tecla Directa");
		btnGuardar.setToolTipText("<CTRL+G> Tecla Directa");
		
		txtFiltro.addKeyListener(opFiltrotabla);
		txtcod_prod.addKeyListener(Buscar_Datos_Producto);
		tabla.addKeyListener(op_validanumero_en_celda);
		
		cmbEstablecimiento.addActionListener(Establecimiento);
		btnNuevo.addActionListener(nuevo);
		btnDeshacer.addActionListener(deshacer);
		btnProducto.addActionListener(filtro_productos);
		btnBuscar.addActionListener(filtro_inventarios);
		btnGuardar.addActionListener(guardar);
		btnQuitarfila.addActionListener(opQuitarfila);
		btnReporte.addActionListener(opGenerar);
		
		agregar(tabla);
		
		
		 this.addWindowListener(new WindowAdapter() {
	            public void windowOpened( WindowEvent e ){
	                txtFolio.requestFocus();
	          }
	         });
				///FILTRO con F2
		         getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), "producto");
		             getRootPane().getActionMap().put("producto", new AbstractAction(){
		                 public void actionPerformed(ActionEvent e)
		                 {                 	    btnProducto.doClick();
		                   	    }
		            });
            	///deshacer con escape
               getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
                 getRootPane().getActionMap().put("escape", new AbstractAction(){
	                public void actionPerformed(ActionEvent e)
	                {                 	    btnDeshacer.doClick();
	              	    }
	                });
            	///guardar con control+G
                getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_G,Event.CTRL_MASK),"guardar");
                  getRootPane().getActionMap().put("guardar", new AbstractAction(){
                     public void actionPerformed(ActionEvent e)
                     {                 	    btnGuardar.doClick();
                   	    }
                     });
              ///guardar con F12
	              getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0), "guardar");
	                  getRootPane().getActionMap().put("guardar", new AbstractAction(){
	                      public void actionPerformed(ActionEvent e)
	                      {                 	    btnGuardar.doClick();
		                    	    }
	                 });
	                 
    }
   
	KeyListener Buscar_Datos_Producto = new KeyListener() {
		public void keyTyped(KeyEvent e){}
		public void keyReleased(KeyEvent e) {}
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				buscar_producto();
			}
		}
	};
	
	KeyListener op_validanumero_en_celda = new KeyListener() {
		public void keyTyped(KeyEvent e) {}
		public void keyReleased(KeyEvent e) {
			fila=tabla.getSelectedRow();
			if(Objetotabla.validacelda(tabla,"decimal", fila, columna)){
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
		  double existenciafisica=Double.valueOf(tabla.getValueAt(fila,3).toString());
		  double valor=existenciafisica-existencia;
		 tabla.setValueAt(valor, fila, 4);
	 }
	
	ActionListener Establecimiento = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String s = cmbEstablecimiento.getSelectedItem().toString().trim();
            switch (s) {
                case "Selecciona un Establecimiento":
                	 JOptionPane.showMessageDialog(null, "Se Requiere Seleccionar Un Establecimiento", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
         			 cmbEstablecimiento.setEnabled(true);
        			 cmbEstablecimiento.requestFocus();
        			 cmbEstablecimiento.showPopup();
                	 break;
                  default:
                	 cmbEstablecimiento.setEnabled(false);
                	 btnProducto.setEnabled(true);
                	 txtcod_prod.setEnabled(true);
                	 txtcod_prod.requestFocus();
                     break;
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
	
	ActionListener nuevo = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			String folio_inventario="";
			try {
				folio_inventario= new BuscarSQL().Folio_Siguiente_alimentacion_inventarios_parciales();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			txtFolio.setText(folio_inventario);
			txtFolio.setEditable(false);
            modelo.setRowCount(0);
			btnGuardar.setEnabled(true);
			btnQuitarfila.setEnabled(true);
			btnBuscar.setEnabled(false);
			btnReporte.setEnabled(false);
			btnNuevo.setEnabled(false);
			txaNota.setEditable(true);
			cmbEstablecimiento.setEnabled(true);
			cmbEstablecimiento.requestFocus();
			cmbEstablecimiento.showPopup();
		}
	};
	
	ActionListener filtro_productos = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			new Cat_Filtro_De_Productos(cmbEstablecimiento.getSelectedItem().toString()).setVisible(true);
		}
	};
	
	ActionListener filtro_inventarios = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			new Cat_Filtro_De_Inventarios_Parciales().setVisible(true);
		}
	};
	
	ActionListener deshacer = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(tabla.getRowCount()>0){
				if(JOptionPane.showConfirmDialog(null, "Hay Datos Capturados y No Han Sido Guardados, �Desea Borrar Todo?", "Aviso", JOptionPane.INFORMATION_MESSAGE,0, new ImageIcon("Imagen/usuario-icono-noes_usuario9131-64.png") )== 0){
					deshacer();
					txtFolio.setText("");
					txtFolio.requestFocus();
					return;
			     }else{
              				return;
			}
		}else{
			deshacer();
			txtFolio.setText("");
			txtFolio.requestFocus();
			return;
		}
		}
	};
	
	
	ActionListener guardar = new ActionListener(){
	public void actionPerformed(ActionEvent e){
			 String[][] tabla_guardado = Objetotabla.tabla_guardar(tabla);
			 if(tabla_guardado.length==0){
				 return;
			 }else{		 
				 Obj_Alimentacion_De_Inventarios_Parciales Inventarios_Parciales= new Obj_Alimentacion_De_Inventarios_Parciales();
				  Inventarios_Parciales.setTabla_obj(tabla_guardado);
				  Inventarios_Parciales.setEstablecimiento(cmbEstablecimiento.getSelectedItem().toString().trim());
				  Inventarios_Parciales.setNotas    (txaNota.getText().toString().trim()+" ");
				  Inventarios_Parciales.setStatus("V");
				  
				  if(Inventarios_Parciales.Guardar_inventario_parcial()){
		                JOptionPane.showMessageDialog(null, "Se Guardo Correctamente el inventario parcial", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
		            	 deshacer();
//		           	btnReporte.doClick();
			      }else{
					JOptionPane.showMessageDialog(null, "El Registro No Se Guardo", "Avise Al Administrador Del Sistema !!!",JOptionPane.ERROR_MESSAGE, new ImageIcon("Imagen/usuario-icono-eliminar5252-64.png"));
			    	return;
			      }
			 }
	  }			
    };
    
    
    ActionListener opGenerar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
	   		 if(!txtFolio.getText().equals("")){
	 			String basedatos="2.26";
				String vista_previa_reporte="no";
				int vista_previa_de_ventana=0;
				String comando="exec sp_select_datos_inventario_fisico_parcial  "+Integer.valueOf(txtFolio.getText().toUpperCase().trim());
				String reporte = "Obj_Reporte_De_Inventario_Parcial.jrxml";
    			 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
		       }else{
		    	   JOptionPane.showMessageDialog(null,"Necesita Teclear Un Folio de Inventario Fisico Parcial para Generar El Reporte","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
		    	   txtFolio.requestFocus();
		    	   return;
		       }
		}
	};
	
	
	public void deshacer(){
		txaNota.setText("");
		txtcod_prod.setText("");
		txtcod_prod.setEnabled(false);
		cmbEstablecimiento.removeActionListener(Establecimiento);
		cmbEstablecimiento.setSelectedIndex(0);
		cmbEstablecimiento.setEnabled(false);
		cmbEstablecimiento.addActionListener(Establecimiento);
		txtFolio.setEditable(true);
		modelo.setRowCount(0);
		btnGuardar.setEnabled(false);
		btnNuevo.setEnabled(true);
		btnProducto.setEnabled(false);
		btnQuitarfila.setEnabled(false);
		btnBuscar.setEnabled(true);
		btnReporte.setEnabled(true);
	}
	
	
	public void buscar_producto(){
		int testigo=0;
	     if(!txtcod_prod.getText().equals("")){
	    	 txtFiltro.setText("");
	    	 
	    	int[] columnas ={0,1,2,3,4,5};
	    	new Obj_Filtro_Dinamico_Plus(tabla , txtFiltro.getText().toString().trim().toUpperCase(), columnas  );
	    	 
			try {
				if(new Obj_Cotizaciones_De_Un_Producto().Existe_Producto(txtcod_prod.getText().trim().toUpperCase()+"")){
			      Obj_Alimentacion_De_Inventarios_Parciales  Datos_Producto= new Obj_Alimentacion_De_Inventarios_Parciales().buscardatos_producto(txtcod_prod.getText().trim().toUpperCase()+"", cmbEstablecimiento.getSelectedItem().toString().trim());
					for(int i=0; i<tabla.getRowCount(); i++){
						if(tabla.getValueAt(i, 0).toString().equals(Datos_Producto.getCod_Prod())){
							testigo=1;
				         	 JOptionPane.showMessageDialog(null, "El Producto Ya Existe En La Captura", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				         	   fila=i+1;
						 };							
					  }
					
					  if(testigo==0){
				 		  vector[0] = Datos_Producto.getCod_Prod() ;
				 		  vector[1] = Datos_Producto.getDescripcion_Prod();
				 		  vector[2] = Datos_Producto.getExistencia();
				 		  vector[3] = 0;
				 		  vector[4] = 0;
				 		  vector[5] = Datos_Producto.getUltimo_Costo();
				 		  vector[6] = Datos_Producto.getCosto_Promedio();

				 		  modelo.addRow(vector);
	 			 		  txtcod_prod.setText("");
	 			 		  fila=tabla.getRowCount();
					      }
					  	 getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
						  	       KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "BUSCA");
							        getRootPane().getActionMap().put("BUSCA", new AbstractAction(){
							        @Override
							     public void actionPerformed(ActionEvent e){
							        	  columna=3;
							                    RecorridoFoco(fila, "no");
							                    }
							    });
				}else{
					fila=1;
					JOptionPane.showMessageDialog(null, "El Codigo Esta Mal Escrito o El Producto No Existe" ,"Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				 return;
	            }
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, "Error en Cat_Cotizaciones_De_Un_Producto_En_Proveedores  en la funcion existe_Producto \n SQLException: "+e1.getMessage(),"Avise Al Adiministrador",JOptionPane.ERROR_MESSAGE, new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
				e1.printStackTrace();
			}
		}else{
			fila=1;
			JOptionPane.showMessageDialog(null, "Es Requerido Teclear Un Codigo " ,"Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
			txtcod_prod.requestFocus();
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
			if(Objetotabla.RecorridoFocotabla(tabla, filap, 3, parametrosacarfoco).equals("si")){
				txtcod_prod.requestFocus();
			};
		}	
	
	//TODO Filtro De productos
	public class Cat_Filtro_De_Productos extends JDialog{
		  Container cont = getContentPane();
		  JLayeredPane panel = new JLayeredPane();
		  Connexion con = new Connexion();
		  Runtime R = Runtime.getRuntime();
		 Obj_tabla  Objetotabla = new Obj_tabla();
			
			int columnas = 6,checkbox=-1;
			public void init_tabla(){
		    	this.tabla2.getColumnModel().getColumn(0).setMinWidth(90);	
		    	this.tabla2.getColumnModel().getColumn(1).setMinWidth(410);
		    	this.tabla2.getColumnModel().getColumn(2).setMinWidth(150);
		    	this.tabla2.getColumnModel().getColumn(3).setMinWidth(190);
		    	this.tabla2.getColumnModel().getColumn(4).setMinWidth(100);
		    	this.tabla2.getColumnModel().getColumn(5).setMinWidth(100);
		    	
				String comando="select productos.cod_prod "
						+ "           ,productos.descripcion"
						+ "           ,isnull(upper(clases_productos.nombre),'Sin Clasificacion') as clase_producto "
						+ "           ,isnull(upper(categorias.nombre),'Sin Clasificacion') as categorias"
						+ "	          ,isnull(upper(familias.nombre),'Sin Clasificacion') as familias   "
						+ "           ,isnull(upper(marcas_productos.nombre),'')  as marca"
						+ "       from productos with (nolock)"
						+ "   left outer join clases_productos with (nolock) on clases_productos.clase_producto=productos.clase_producto"
						+ "   left outer join categorias with (nolock) on categorias.categoria=productos.categoria"
						+ "   left outer join familias with (nolock) on familias.familia=productos.familia"
						+ "   left outer join marcas_productos with (nolock) on marcas_productos.marca=productos.marca"
						+ "      order by  productos.descripcion " ;
				String basedatos="200",pintar="si";
				Objetotabla.Obj_Refrescar(tabla2,modelo2, columnas, comando, basedatos,pintar,checkbox);
		    }
			
		  public DefaultTableModel modelo2 = new DefaultTableModel(null, new String[]{"Codigo Producto","Descripcion","Clase Producto","Categoria","Familia","Marca"}){
			 @SuppressWarnings("rawtypes")
				Class[] types = new Class[]{
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
		    
		    JTable tabla2 = new JTable(modelo2);
			public JScrollPane scroll_tabla = new JScrollPane(tabla2);
	    JScrollPane scrollAsignado = new JScrollPane(tabla2,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    
		@SuppressWarnings("rawtypes")
		private TableRowSorter trsfiltro;
		
		JTextField txtFiltrop = new Componentes().text(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<", 300, "String");
		Border blackline, etched, raisedbevel, loweredbevel, empty;
	    
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Cat_Filtro_De_Productos(String establecimiento){
			int ancho = 1024;//Toolkit.getDefaultToolkit().getScreenSize().width;
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
			panel.add(scrollAsignado).setBounds(15,y+=20,ancho-30,alto-70);
	        
			init_tabla();
			agregar(tabla2);
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
			    			txtcod_prod.setText(folio);
			    			buscar_producto();
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
	
	//TODO Filtro De inventarios parciales
		public class Cat_Filtro_De_Inventarios_Parciales extends JDialog{
			  Container cont = getContentPane();
			  JLayeredPane panel = new JLayeredPane();
			  Connexion con = new Connexion();
			  Runtime R = Runtime.getRuntime();
			  Obj_tabla  Objetotabla = new Obj_tabla();
				
				int columnas = 8,checkbox=-1;
				public void init_tabla(){
			    	this.tabla3.getColumnModel().getColumn(0).setMinWidth(100);	
			    	this.tabla3.getColumnModel().getColumn(1).setMinWidth(200);
			    	this.tabla3.getColumnModel().getColumn(2).setMinWidth(120);
			    	this.tabla3.getColumnModel().getColumn(3).setMinWidth(120);
			    	this.tabla3.getColumnModel().getColumn(4).setMinWidth(220);
			    	this.tabla3.getColumnModel().getColumn(5).setMaxWidth(50);
			    	this.tabla3.getColumnModel().getColumn(6).setMinWidth(120);
			    	this.tabla3.getColumnModel().getColumn(7).setMinWidth(220);
			    	
					String comando="exec sp_select_filtro_de_inventarios_fisicos_parciales '01/11/2016 23:59:59'" ;
					String basedatos="26",pintar="si";
					Objetotabla.Obj_Refrescar(tabla3,modelo3, columnas, comando, basedatos,pintar,checkbox);
			    }
				
			  public DefaultTableModel modelo3 = new DefaultTableModel(null, new String[]{"Folio Inventario" ,"Establecimiento" ,"Fecha" ,"Diferencia" ,"Usuario Capturo" ,"Estatus","Fecha Cancelacion","Usuario Cancelacion"}){
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
			    
			    JTable tabla3 = new JTable(modelo3);
				public JScrollPane scroll_tabla = new JScrollPane(tabla3);
		    JScrollPane scrollAsignado = new JScrollPane(tabla3,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		    
			@SuppressWarnings("rawtypes")
			private TableRowSorter trsfiltro;
			
			JTextField txtFiltrop = new Componentes().text(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<", 300, "String");
			Border blackline, etched, raisedbevel, loweredbevel, empty;
		    
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Cat_Filtro_De_Inventarios_Parciales(){
				int ancho = 1024;//Toolkit.getDefaultToolkit().getScreenSize().width;
				int alto = Toolkit.getDefaultToolkit().getScreenSize().height-50;
				this.setSize(ancho, alto);
				this.setResizable(false);
				this.setLocationRelativeTo(null);
				this.setTitle("Filtro De Busqueda De Inventarios Fisicos Parciales");
				this.setModal(true);				
				this.setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/lista-icono-7220-32.png"));
				blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
				panel.setBorder(BorderFactory.createTitledBorder(blackline,"Doble Click A El Inventario Deseado"));
				this.cont.add(panel);

				trsfiltro = new TableRowSorter(modelo3); 
				tabla3.setRowSorter(trsfiltro);
				txtFiltrop.addKeyListener(opFiltro);

				int y = 20;
				panel.add(txtFiltrop).setBounds(15,y,500,20);
				panel.add(scrollAsignado).setBounds(15,y+=20,ancho-30,alto-70);
		        
				init_tabla();
				agregar(tabla3);
			}

			KeyListener opFiltro = new KeyListener(){
				@SuppressWarnings("unchecked")
				public void keyReleased(KeyEvent arg0) {
					int[] columnas ={0,1,2,3,4,5,6,7,8};
					new Obj_Filtro_Dinamico_Plus(tabla3 , txtFiltrop.getText().toString().trim().toUpperCase(), columnas  );
					trsfiltro.setRowFilter(RowFilter.regexFilter(txtFiltrop.getText(), 0));
				}
				public void keyTyped(KeyEvent arg0) {}
				public void keyPressed(KeyEvent arg0) {}	
			    };
			
			private void agregar(final JTable tbl) {
				tbl.addMouseListener(new MouseListener() {
					public void mouseReleased(MouseEvent e) {
							if(e.getClickCount() == 2){
								int fila_Select = tabla3.getSelectedRow();
				    			String folio =  tabla3.getValueAt(fila_Select, 0).toString().trim();
				    			dispose();
				    			txtFolio.setText(folio);
				    			btnReporte.doClick();
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
			new Cat_Alimentacion_De_Inventarios_Parciales().setVisible(true);
		}catch(Exception e){	}
	}
};
	
