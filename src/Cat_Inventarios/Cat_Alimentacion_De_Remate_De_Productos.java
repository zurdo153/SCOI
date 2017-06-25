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
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Connexion;
import Conexiones_SQL.Generacion_Reportes;
import Obj_Compras.Obj_Alimentacion_De_Productos_Proximos_A_Caducar;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_Filtro_Dinamico_Plus;
import Obj_Principal.Obj_tabla;

@SuppressWarnings("serial")
public class Cat_Alimentacion_De_Remate_De_Productos extends JFrame{
	Container cont         = getContentPane();
	JLayeredPane panel     = new JLayeredPane();
    JToolBar menu_toolbar  = new JToolBar();
	Connexion         con  = new Connexion();
	Obj_tabla  Objetotabla = new Obj_tabla();
	
	int columnas = 9,checkbox=-1;
	public void init_tabla_principal(String consulta){
    	this.tabla.getColumnModel().getColumn(0).setMinWidth(40);	
     	this.tabla.getColumnModel().getColumn(0).setMaxWidth(50);	
    	this.tabla.getColumnModel().getColumn(1).setMinWidth(340);
    	this.tabla.getColumnModel().getColumn(2).setMinWidth(60);
    	this.tabla.getColumnModel().getColumn(3).setMinWidth(100);
    	this.tabla.getColumnModel().getColumn(4).setMinWidth(90);
    	this.tabla.getColumnModel().getColumn(5).setMinWidth(98);
    	this.tabla.getColumnModel().getColumn(6).setMinWidth(80);
    	this.tabla.getColumnModel().getColumn(7).setMinWidth(90);
    	this.tabla.getColumnModel().getColumn(8).setMinWidth(150);
		String comando=consulta;
		String basedatos="26",pintar="si";
		Objetotabla.Obj_Refrescar(tabla,modelo, columnas, comando, basedatos,pintar,checkbox);
    }
	
  public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"Codigo","Descripcion","Cantidad","Fecha Caducidad","Ultimo Costo","Costo Promedio "," Precio Venta" ,"Precio Remate" ,"Clasificacion" }){
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
			if(columna ==7||columna ==8)
				return true; return false;
		}
    };
    
    JTable tabla = new JTable(modelo);
	public JScrollPane scroll_tabla = new JScrollPane(tabla);
	
	JTextField txtFolio    = new Componentes().text(new JCTextField(), "Folio Del Inventario", 30, "String");
	JTextField txtFiltro   = new Componentes().text(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<", 300, "String");
	
	JCButton btnQuitarfila = new JCButton("Eliminar Fila","boton-rojo-menos-icono-5393-16.png","Azul");
	JCButton btnReporte    = new JCButton("Reporte"      ,"Lista.png","Azul");
	JCButton btnBuscar     = new JCButton("Buscar"       ,"Filter-List-icon16.png","Azul"); 
	JCButton btnGuardar   = new JCButton("Guardar"      ,"Guardar.png","Azul");
	JCButton btnDeshacer  = new JCButton("Deshacer"     ,"deshacer16.png","Azul");
	
	String establecimiento[] = new Obj_Establecimiento().Combo_Establecimiento201();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbEstablecimiento = new JComboBox(establecimiento);

	String status[] = {"VIGENTE","CANCELADO", "REMATE"};
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmb_status = new JComboBox(status);
	
	String razones_remate[] = new BuscarSQL().Razones_De_Remate();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbRazonDeRemate = new JComboBox(razones_remate);
	
	Border blackline, etched, raisedbevel, loweredbevel, empty;
	int fila=0;
    int columna=0;
    Object[] vector = new Object[8];
    
    JTextArea txaNota 	= new Componentes().textArea(new JTextArea(), "Nota", 500);
	JScrollPane Nota = new JScrollPane(txaNota);

   public  Cat_Alimentacion_De_Remate_De_Productos(){
	   this.cont.add(panel);
		this.setSize(1024,728);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("Alimentacion De Remate De Productos Proximos A Caducar");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/de-entrada-de-alerta-icono-4398-48.png"));

		blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
		panel.setBorder(BorderFactory.createTitledBorder(blackline,"Alimentacion De Remate De Productos Proximos A Caducar "));
        cont.setBackground(new java.awt.Color(255, 255, 255));
   	    tabla.getTableHeader().setReorderingAllowed(false) ;
   	
		int x=10, y=20,width=122,height=20, sep=135;
		panel.add(menu_toolbar).setBounds                  (x         ,y      ,330     ,height );
		panel.add(txtFolio).setBounds                      (x         ,y+=30  ,width   ,height );
		panel.add(cmb_status).setBounds   		           (x+=sep    ,y      ,width   ,height );
		panel.add(cmbEstablecimiento).setBounds            (x+=sep    ,y      ,width+60,height );
		panel.add(new JLabel("Nota:")).setBounds           (x+sep+60  ,y-15   ,50      ,height );
		panel.add(Nota).setBounds                          (x+sep+60  ,y      ,533     ,47     );
		
		x=10;
		panel.add(txtFiltro).setBounds   		           (x         ,y+=27  ,453     ,height );
		panel.add(scroll_tabla).setBounds                  (x         ,y+=23  ,997     ,580    );

		this.menu_toolbar.add(btnBuscar);
	    this.menu_toolbar.addSeparator();
	    this.menu_toolbar.addSeparator( );
		this.menu_toolbar.add(btnDeshacer);
		this.menu_toolbar.addSeparator();
		this.menu_toolbar.addSeparator( );
		this.menu_toolbar.add(btnReporte);
		this.menu_toolbar.addSeparator();
		this.menu_toolbar.addSeparator( );
		this.menu_toolbar.add(btnGuardar);
		this.menu_toolbar.setFloatable(false);
		
		TableColumn razon = tabla.getColumnModel().getColumn(8);		
		razon.setCellEditor(new javax.swing.DefaultCellEditor(cmbRazonDeRemate));
		
		txaNota.setLineWrap(true); 
		txaNota.setWrapStyleWord(true);
		txaNota.setEditable(false);
		cmb_status.setEnabled(false);
		btnGuardar.setEnabled(false);
		btnQuitarfila.setEnabled(false);
		cmbEstablecimiento.setEnabled(false);
		
		init_tabla_principal("Select '' as Codigo_Producto, '' as Descripcion, 0 as Cantidad ,'' as Fecha_Caducidad ,0 as Ultimo_Costo ,0 as Costo_Promedio ,0 as Precio_Venta ,0 as Precio_Remate ,'' as Clasificacion" );
		modelo.setRowCount(0);
		btnDeshacer.setToolTipText("<ESC> Tecla Directa");
		btnGuardar.setToolTipText("<CTRL+G> Tecla Directa");
		
		txtFiltro.addKeyListener(opFiltrotabla);
		tabla.addKeyListener(op_validanumero_en_celda);
		
		cmbEstablecimiento.addActionListener(Establecimiento);
		btnDeshacer.addActionListener(deshacer);
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
		                 {             	    btnBuscar.doClick();
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
	                      {            	    btnGuardar.doClick();
		                    	    }
	                 });
    }
   
	
	KeyListener op_validanumero_en_celda = new KeyListener() {
		public void keyTyped(KeyEvent e) {}
		public void keyReleased(KeyEvent e) {
			    fila=tabla.getSelectedRow();
            	columna=6;
           Objetotabla.validacelda(tabla,"decimal", fila, columna);
           RecorridoFoco(fila,"x"); 
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
	
	
	ActionListener filtro_inventarios = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			new Cat_Filtro_De_Inventarios_Parciales().setVisible(true);
		}
	};
	
	ActionListener deshacer = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(tabla.getRowCount()>0){
				if(JOptionPane.showConfirmDialog(null, "Hay Datos Capturados y No Han Sido Guardados, ¿Desea Borrar Todo?", "Aviso", JOptionPane.INFORMATION_MESSAGE,0, new ImageIcon("Imagen/usuario-icono-noes_usuario9131-64.png") )== 0){
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
			 String[][] tabla_guardado = Objetotabla.tabla_guardar_sin_validacion(tabla);
			 if(tabla_guardado.length==0){
				 if(txaNota.getText().toString().trim().equals("")){
					  JOptionPane.showMessageDialog(null, "Para Guardar El Registro Vacio Se Requiere Que Argumente En Las Notas El Porque Se Guarda Sin Alimentar Productos", "Aviso", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				 }else{
					 
					 Obj_Alimentacion_De_Productos_Proximos_A_Caducar Productos_Proximos_a_Caducar = new Obj_Alimentacion_De_Productos_Proximos_A_Caducar();
					 Productos_Proximos_a_Caducar.setTabla_obj(tabla_guardado);
					 Productos_Proximos_a_Caducar.setEstablecimiento(cmbEstablecimiento.getSelectedItem().toString().trim());
					 Productos_Proximos_a_Caducar.setNotas    (txaNota.getText().toString().trim()+" ");
					 Productos_Proximos_a_Caducar.setStatus(cmb_status.getSelectedItem().toString().trim());
					 Productos_Proximos_a_Caducar.setGuardar_actualizar("Registro Vacio");
					  
					  if(Productos_Proximos_a_Caducar.Guardar_Alimentacion_Proximos_A_Caducar() ){
			                JOptionPane.showMessageDialog(null, "Se Guardo Correctamente los Productos Proximos a Caducar", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
			                 deshacer();
						     btnReporte.doClick();
				      }else{
						JOptionPane.showMessageDialog(null, "El Registro No Se Guardo", "Avise Al Administrador Del Sistema !!!",JOptionPane.ERROR_MESSAGE, new ImageIcon("Imagen/usuario-icono-eliminar5252-64.png"));
				    	return;
				      }
					 return;
				 }
			 }else{		 
				 
				 Obj_Alimentacion_De_Productos_Proximos_A_Caducar Productos_Proximos_a_Caducar = new Obj_Alimentacion_De_Productos_Proximos_A_Caducar();
				 Productos_Proximos_a_Caducar.setTabla_obj(tabla_guardado);
				 Productos_Proximos_a_Caducar.setEstablecimiento(cmbEstablecimiento.getSelectedItem().toString().trim());
				 Productos_Proximos_a_Caducar.setNotas    (txaNota.getText().toString().trim()+" ");
				 Productos_Proximos_a_Caducar.setStatus(cmb_status.getSelectedItem().toString().trim());
				 Productos_Proximos_a_Caducar.setGuardar_actualizar("Guardar");
				  
				  if(Productos_Proximos_a_Caducar.Guardar_Alimentacion_Proximos_A_Caducar() ){
		                JOptionPane.showMessageDialog(null, "Se Guardo Correctamente los Productos Proximos a Caducar", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
		                 deshacer();
					     btnReporte.doClick();
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
				String comando="exec Sp_Reporte_De_Captura_De_Productos_Proximos_A_Caducar  "+Integer.valueOf(txtFolio.getText().toUpperCase().trim());
				String reporte = "Obj_Reporte_De_Productos_Proximos_A_Caducar.jrxml";
    			 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
		       }else{
		    	   JOptionPane.showMessageDialog(null,"Necesita Teclear Un Folio para Generar El Reporte","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
		    	   txtFolio.requestFocus();
		    	   return;
		       }
		}
	};
	
	public void deshacer(){
		txaNota.setText("");
		cmbEstablecimiento.removeActionListener(Establecimiento);
		cmbEstablecimiento.setSelectedIndex(0);
		cmbEstablecimiento.setEnabled(false);
		cmbEstablecimiento.addActionListener(Establecimiento);
		txtFolio.setEditable(true);
		modelo.setRowCount(0);
		btnGuardar.setEnabled(false);
		btnQuitarfila.setEnabled(false);
		btnBuscar.setEnabled(true);
		btnReporte.setEnabled(true);
	}
	
	private void agregar(final JTable tbl) {
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
	        public void mouseClicked(MouseEvent e) {
	        	if(e.getClickCount() == 1){
	        		columna=tabla.getSelectedColumn();
	        		RecorridoFoco(tbl.getSelectedRow()-1,"x");
	        	}
	        }
        });
    }
	
	public void RecorridoFoco(int filap,String parametrosacarfoco){
			Objetotabla.RecorridoFocotabla(tabla, filap, 7, parametrosacarfoco).equals("si");
			
		}
	
	//TODO Filtro De inventarios parciales
		public class Cat_Filtro_De_Inventarios_Parciales extends JDialog{
			  Container cont = getContentPane();
			  JLayeredPane panel = new JLayeredPane();
			  Connexion con = new Connexion();
			  Runtime R = Runtime.getRuntime();
			  Obj_tabla  Objetotabla = new Obj_tabla();
				
				int columnas = 9,checkbox=-1;
				public void init_tabla(){
			    	this.tabla3.getColumnModel().getColumn(0).setMinWidth(50);	
			    	this.tabla3.getColumnModel().getColumn(1).setMinWidth(150);
			    	this.tabla3.getColumnModel().getColumn(2).setMinWidth(120);
			    	this.tabla3.getColumnModel().getColumn(3).setMinWidth(80);
			    	this.tabla3.getColumnModel().getColumn(4).setMinWidth(220);
			    	this.tabla3.getColumnModel().getColumn(5).setMaxWidth(70);
			    	this.tabla3.getColumnModel().getColumn(6).setMinWidth(300);
			    	this.tabla3.getColumnModel().getColumn(7).setMinWidth(120);
			    	this.tabla3.getColumnModel().getColumn(8).setMinWidth(220);
			    	
					String comando="exec sp_select_filtro_de_productos_proximos_a_caducar " ;
					String basedatos="26",pintar="si";
					Objetotabla.Obj_Refrescar(tabla3,modelo3, columnas, comando, basedatos,pintar,checkbox);
			    }
				
			  public DefaultTableModel modelo3 = new DefaultTableModel(null, new String[]{"Folio" ,"Establecimiento" ,"Fecha" ,"Costo Total" ,"Usuario Capturo" ,"Estatus","Notas" ,"Fecha Cancelacion","Usuario Cancelacion"}){
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
				this.setTitle("Filtro De Busqueda De Productos Proximos a Caducar");
				this.setModal(true);				
				this.setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/lista-icono-7220-32.png"));
				blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
				panel.setBorder(BorderFactory.createTitledBorder(blackline,"Doble Click Al Registro Deseado"));
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
				    		
				    			txtFolio.setText(tabla3.getValueAt(fila_Select, 0).toString().trim());
				    			cmbEstablecimiento.setSelectedItem(tabla3.getValueAt(fila_Select, 1).toString().trim());
				    			cmb_status.setSelectedItem(tabla3.getValueAt(fila_Select, 5).toString().trim());
				    		    txaNota.setText(tabla3.getValueAt(fila_Select, 6).toString().trim());
				    		    init_tabla_principal("exec sp_select_productos_proximos_a_caducar "+tabla3.getValueAt(fila_Select, 0).toString().trim());
				    		    btnGuardar.setEnabled(true);
				    			dispose();
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
			new Cat_Alimentacion_De_Remate_De_Productos().setVisible(true);
		}catch(Exception e){	}
	}
};
	
