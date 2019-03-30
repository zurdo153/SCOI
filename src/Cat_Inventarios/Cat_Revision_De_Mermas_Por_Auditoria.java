package Cat_Inventarios;

import java.awt.Color;
import java.awt.Container;
import java.awt.Event;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Icon;
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
import Conexiones_SQL.GuardarSQL;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Compras.Obj_Alimentacion_De_Inventarios_Parciales;
import Obj_Compras.Obj_Cotizaciones_De_Un_Producto;
import Obj_Inventarios.Obj_Alimentacion_De_Mermas;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_Filtro_Dinamico_Plus;
import Obj_Principal.Obj_tabla;

@SuppressWarnings("serial")
public class Cat_Revision_De_Mermas_Por_Auditoria extends JFrame{
	
	byte[] imagB = null;
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
    JToolBar menu_toolbar  = new JToolBar();
	Connexion con = new Connexion();
	 Obj_tabla  Objetotabla = new Obj_tabla();
	
	int columnas = 14,checkbox=-1;
	public void init_tabla(String folio_merma){
    	this.tabla.getColumnModel().getColumn(0).setMinWidth(90);	
    	this.tabla.getColumnModel().getColumn(1).setMinWidth(380);
    	this.tabla.getColumnModel().getColumn(2).setMinWidth(100);
    	this.tabla.getColumnModel().getColumn(3).setMinWidth(100);
    	this.tabla.getColumnModel().getColumn(4).setMinWidth(100);
    	this.tabla.getColumnModel().getColumn(5).setMinWidth(100);
    	this.tabla.getColumnModel().getColumn(6).setMinWidth(100);
    	this.tabla.getColumnModel().getColumn(7).setMinWidth(100);
    	this.tabla.getColumnModel().getColumn(8).setMinWidth(100);
    	this.tabla.getColumnModel().getColumn(9).setMinWidth(100);
    	this.tabla.getColumnModel().getColumn(10).setMinWidth(100);
    	this.tabla.getColumnModel().getColumn(11).setMinWidth(100);
    	this.tabla.getColumnModel().getColumn(12).setMinWidth(100);
    	this.tabla.getColumnModel().getColumn(13).setMinWidth(100);
    	
		String comando= folio_merma.equals("")?"Select '' as Codigo_Producto, '' as Descripcion, 0 as  Existencia, 0 as Merma_Captura, 0 as Merma_Seguridad, 0 as Merma_Supervisor ,0 as Existencia_Sin_Merma,'' as Razon_De_Merma,'' as Destino_De_Merma, 0 as Ultimo_Costo, 0 as Costo_Promedio , 0 as Precio_De_Lista, 0 as Total_Ultimo_Costo, 0 as Total_Costo_Promedio ":"sp_verificacion_de_merma_por_supervisor '"+folio_merma+"'" ;
		String basedatos="26",pintar="si";
		Objetotabla.Obj_Refrescar(tabla,modelo, columnas, comando, basedatos,pintar,checkbox);
    }
	
  public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"Codigo_Producto","Descripcion","Existencia","Merma_Captura","Merma_Seguridad","Merma_Supervisor","Existencia_Sin_Merma","Razon_De_Merma","Destino_De_Merma","Ultimo_Costo","Costo_Promedio", "Precio_De_Lista", "Total_Ultimo_Costo","Total_Costo_Promedio"}){
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
			if(columna ==5 /*|| columna==7 */|| columna==8)
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
	JCButton btnBuscar    = new JCButton("Mermas"  ,"Filter-List-icon16.png","Azul"); 
	JCButton btnEditar    = new JCButton("Editar"       ,"editara.png","Azul");
	JCButton btnGuardar   = new JCButton("Guardar"      ,"Guardar.png","Azul");
	JCButton btnDeshacer  = new JCButton("Deshacer"     ,"deshacer16.png","Azul");
	JCButton btnFotoMerma  = new JCButton(""     ,"investigacion-icono-9565-48.png","Azul");
	
//	String establecimiento[] = new Obj_Establecimiento().Combo_Establecimiento201();
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	JComboBox cmbEstablecimiento = new JComboBox(establecimiento);
	
	JTextField txtEstablecimiento = new Componentes().text(new JCTextField(), "Establecimiento", 100, "String");
	
	String razon_de_merma[] = new BuscarSQL().Razones_De_Mermas();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbRazonDeMerma = new JComboBox(razon_de_merma);
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbRazonDeMermaTabla = new JComboBox(razon_de_merma);
	
	String destino_de_merma[] = new BuscarSQL().Destino_De_Mermas("S");
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbDestinoDeMerma = new JComboBox(destino_de_merma);
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbDestinoDeMermaTabla = new JComboBox(destino_de_merma);
	
	Border blackline, etched, raisedbevel, loweredbevel, empty;
	int fila=0;
    int columna=0;
    Object[] vector = new Object[14];
    
    JLabel lblNota = new JLabel("");
    JTextArea txaNota 	= new Componentes().textArea(new JTextArea(), "Nota", 500);
	JScrollPane Nota = new JScrollPane(txaNota);
	
	int folio_usuario = 0;
	int folio_usuario_valida = 0;
	
//	JCButton btnExaminar = new JCButton("Examinar", "", "Azul");
//	JCButton btnCamara = new JCButton("", "camara_icon&16.png", "Azul");
	
//	boolean imagenCargada = false ;
//	String rutaFoto = "";
	
	JLabel lblSinImag = new JLabel();
	Icon iconoSinImag;
    String fileSinImag = "Imagen/Aplicar.png";
    ImageIcon tmpIconSinImag;
    
    JLabel lblConImag = new JLabel();
	Icon iconoConImag;
    String fileConImag = "Imagen/cancelar-icono-4961-16.png";
    ImageIcon tmpIconConImag;
	
   @SuppressWarnings("rawtypes")
   Vector productos_originales = new Vector();
   
   DecimalFormat df = new DecimalFormat("#0.00");
   JLabel lblTotalCostoMerma = new JLabel("Total Costo De Merma: $0.00");
   
   public  Cat_Revision_De_Mermas_Por_Auditoria(){
	   this.cont.add(panel);
		this.setSize(1024,768);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		lblTotalCostoMerma.setFont(new java.awt.Font("Algerian",0,25));
		
		this.setTitle("Revisión De Mermas");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/articulo-icono-9036-48.png"));
		blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
		panel.setBorder(BorderFactory.createTitledBorder(blackline,"Revisión De Mermas Por Establecimiento"));
        cont.setBackground(new java.awt.Color(255, 255, 255));
   	    tabla.getTableHeader().setReorderingAllowed(false) ;
   	    
   	    tmpIconSinImag = new ImageIcon(fileSinImag);
   	    iconoSinImag = new ImageIcon(tmpIconSinImag.getImage().getScaledInstance(16,16, Image.SCALE_DEFAULT));
	    lblSinImag.setIcon(iconoSinImag);
	    
	    tmpIconConImag = new ImageIcon(fileConImag);
	    iconoConImag = new ImageIcon(tmpIconConImag.getImage().getScaledInstance(16,16, Image.SCALE_DEFAULT));
	    lblConImag.setIcon(iconoConImag);
	    
	    lblNota.setForeground(Color.BLUE);
   	
		int x=20, y=20,width=122,height=20, sep=135;
		panel.add(lblNota).setBounds                       (x+490      ,y-20   ,480     ,50		);
		panel.add(menu_toolbar).setBounds                  (x         ,y      ,250     ,height );
		panel.add(txtFolio).setBounds                      (x         ,y+=30  ,width   ,height );
//		panel.add(btnBuscar).setBounds                     (x+=sep    ,y      ,width   ,height );
		panel.add(txtEstablecimiento).setBounds            (x+=sep*2+30 ,y      ,width+60,height );
		panel.add(new JLabel("Nota:")).setBounds           (x+sep+60  ,y-15   ,50      ,height );
		panel.add(Nota).setBounds                          (x+sep+60  ,y      ,480     ,50     );
		
		x=20;
		panel.add(txtcod_prod).setBounds                   (x         ,y+=30  ,width   ,height );
		panel.add(btnProducto).setBounds                   (x+=sep    ,y      ,width   ,height );
		panel.add(btnFotoMerma).setBounds                   (x+320    ,y-5      ,30   ,30 );
//		panel.add(new JLabel("Foto:")).setBounds		   (x+=160     ,y	   ,100     ,height );
//		panel.add(btnExaminar).setBounds				   (x+=30      ,y	   ,100     ,height );
//		panel.add(btnCamara).setBounds					   (x+=110     ,y	   ,30      ,height );
//		panel.add(lblSinImag).setBounds					   (x+32	   ,y	   ,30      ,height );
//		panel.add(lblConImag).setBounds					   (x+32	   ,y	   ,30      ,height );
		
		x=20;
		panel.add(new JLabel("Razon De Merma:")).setBounds (x  		  ,y+=27  ,150     ,height );
		panel.add(cmbRazonDeMerma).setBounds   		       (x+sep	  ,y	  ,350     ,height );
		panel.add(new JLabel("Destino De Merma:")).setBounds(x+sep*3+90,y	  ,150     ,height );
		panel.add(cmbDestinoDeMerma).setBounds	           (x+sep*5-80,y	  ,200     ,height );
		
		panel.add(txtFiltro).setBounds   		           (x         ,y+=27  ,800     ,height );
		
		panel.add(btnQuitarfila).setBounds                 (x+847     ,y      ,width   ,height ); 
		panel.add(scroll_tabla).setBounds                  (x         ,y+=23  ,972     ,520    );
//		panel.add(btnDeshacer).setBounds                   (x    	  ,y+=585 ,width   ,height );
//		panel.add(btnGuardar).setBounds                    (x+=sep    ,y      ,width   ,height );
//		panel.add(btnEditar).setBounds                     (x+=sep    ,y    ,width   ,height );
		panel.add(lblTotalCostoMerma).setBounds	           (x+507     ,y+=535  ,width*4   ,30 );
		
		this.menu_toolbar.add(btnBuscar);
	    this.menu_toolbar.addSeparator( );
	    this.menu_toolbar.addSeparator( );
//	    this.menu_toolbar.add(btnNuevo);
//	    this.menu_toolbar.addSeparator();
//	    this.menu_toolbar.addSeparator( );
		this.menu_toolbar.add(btnDeshacer);
		this.menu_toolbar.addSeparator();
		this.menu_toolbar.addSeparator( );
//		this.menu_toolbar.add(btnReporte);
//		this.menu_toolbar.addSeparator();
//		this.menu_toolbar.addSeparator( );
		this.menu_toolbar.add(btnGuardar);
		this.menu_toolbar.setFloatable(false);
		
		txaNota.setLineWrap(true); 
		txaNota.setWrapStyleWord(true);
		txaNota.setEditable(false);
		
		txtcod_prod.setEnabled(false);
		btnGuardar.setEnabled(false);
		btnQuitarfila.setEnabled(false);
		btnProducto.setEnabled(false);
		txtEstablecimiento.setEnabled(false);
		
		folio_usuario = new Obj_Usuario().LeerSession().getFolio();
		
		TableColumn razon = tabla.getColumnModel().getColumn(7);		
		razon.setCellEditor(new javax.swing.DefaultCellEditor(cmbRazonDeMermaTabla));
		
		TableColumn destino = tabla.getColumnModel().getColumn(8);		
		destino.setCellEditor(new javax.swing.DefaultCellEditor(cmbDestinoDeMermaTabla));
		
		try {
			imagB  = Files.readAllBytes(Paths.get(System.getProperty("user.dir")+"/Iconos/Un.JPG"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		
		init_tabla("");
		modelo.setRowCount(0);
		btnDeshacer.setToolTipText("<ESC> Tecla Directa");
		btnGuardar.setToolTipText("<CTRL+G> Tecla Directa");
		
		txtFiltro.addKeyListener(opFiltrotabla);
		txtcod_prod.addKeyListener(Buscar_Datos_Producto);
		tabla.addKeyListener(op_validanumero_en_celda);
		
//		cmbEstablecimiento.addActionListener(Establecimiento);
		btnDeshacer.addActionListener(deshacer);
		btnProducto.addActionListener(filtro_productos);
		btnBuscar.addActionListener(filtro_inventarios);
		btnGuardar.addActionListener(guardar);
		cmbRazonDeMerma.addActionListener(opRazonMerma);
		
//		btnExaminar.addActionListener(opExaminar);
//		btnCamara.addActionListener(opFoto);
		
		btnQuitarfila.addActionListener(opQuitarfila);
		btnFotoMerma.addActionListener(opMostrarFoto);
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
   
	public Icon crearIcon(byte[] bs){
		ImageIcon tmpIconDefault= new ImageIcon(bs);
//			int anchoRealDeImagen = tmpIconDefault.getIconWidth();
//			int altoRealDeImg = tmpIconDefault.getIconHeight();
		
//			int anchoDeImagenEscala = (btnFoto.getWidth());
//			int altoDeImgagenEscala = (btnFoto.getHeight());
			
			int anchoDeImagenEscala = (500);
			int altoDeImgagenEscala = (480);
			return new ImageIcon(tmpIconDefault.getImage().getScaledInstance(anchoDeImagenEscala, altoDeImgagenEscala, Image.SCALE_DEFAULT));
	}
//   public void imagMerma(){
//	   
//	   if(rutaFoto.length()==0){
//		   lblConImag.setVisible(true);
//		   lblSinImag.setVisible(false);
//	   }else{
//		   lblConImag.setVisible(false);
//		   lblSinImag.setVisible(true);
//	   }
//   }
   
//	ActionListener opExaminar = new ActionListener(){
//		public void actionPerformed(ActionEvent e) {
//			FileDialog file = new FileDialog(new Frame());
//			
//			file.setTitle("Selecciona una Imagen");
//			file.setMode(FileDialog.LOAD);
//			file.setVisible(true);
//			
//			if(file.getDirectory() != null){
//						imagenCargada = true ;
//						rutaFoto = file.getDirectory()+file.getFile();
//						System.out.println(rutaFoto);
//				    	imagMerma();
//			}else{
//				JOptionPane.showMessageDialog(null,"No ha seleccionado ninguna imagen","Aviso",JOptionPane.WARNING_MESSAGE);
//				return;
//			}
//		}
//	};
	
//	ActionListener opFoto = new ActionListener(){
//		public void actionPerformed(ActionEvent e){
//				try{
//					new Llamar_Camara().setVisible(true);
////					new MainCamara("tmp.jpg").setVisible(true);
//				}catch(Exception ee){
//					JOptionPane.showMessageDialog(null, "Verifique si está conectada y configurada la camara", "Error!", JOptionPane.ERROR_MESSAGE);
//				}
//		}
//	};
	
	 ActionListener opMostrarFoto = new ActionListener(){
		 public void actionPerformed(ActionEvent e){
			 new FotoMerma().setVisible(true);
		 }  
	   };
	   
   ActionListener opRazonMerma = new ActionListener(){
	 public void actionPerformed(ActionEvent e){
		 if(tabla.getRowCount()>0){
			 for(int i =0; i<tabla.getRowCount(); i++){
				 tabla.setValueAt(cmbRazonDeMerma.getSelectedItem().toString().trim(), i, 7);
			 }
		 }
	 }  
   };
   
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
		  double merma=Double.valueOf(tabla.getValueAt(fila,5).toString());
		  double valor=existencia-merma;
		  tabla.setValueAt(df.format(valor), fila, 6);
		  
		  double ultimo_costo = Double.valueOf(tabla.getValueAt(fila, 9).toString().trim());
		  double costo_promedio = Double.valueOf(tabla.getValueAt(fila, 10).toString().trim());
		  
		  double total_ultimo_costo = merma*ultimo_costo;
		  double total_costo_promedio = merma*costo_promedio;
		  
		  tabla.setValueAt(df.format(total_ultimo_costo), fila, 12);
		  tabla.setValueAt(df.format(total_costo_promedio), fila, 13);
		  
		  calcularTotalCostoDeMerma(13);
	 }
	
	   public void calcularTotalCostoDeMerma(int columna){
		   double total = 0;
		   
		   if(tabla.getRowCount()>0){
			   for(int i = 0; i<tabla.getRowCount(); i++){
				   total+=Double.valueOf(tabla.getValueAt(i, columna).toString());
			   }
		   }else{
			   total = 0;
		   }
		   lblTotalCostoMerma.setText("Total Costo De Merma: $"+df.format(total));
	   }
	
//	ActionListener Establecimiento = new ActionListener() {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            String s = cmbEstablecimiento.getSelectedItem().toString().trim();
//            switch (s) {
//                case "Selecciona un Establecimiento":
//                	 JOptionPane.showMessageDialog(null, "Se Requiere Seleccionar Un Establecimiento", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
//         			 cmbEstablecimiento.setEnabled(true);
//        			 cmbEstablecimiento.requestFocus();
//        			 cmbEstablecimiento.showPopup();
//                	 break;
//                  default:
//                	 cmbEstablecimiento.setEnabled(false);
//                	 btnProducto.setEnabled(true);
//                	 txtcod_prod.setEnabled(true);
//                	 txtcod_prod.requestFocus();
//                     break;
//            }
//        }
//    };
	
	ActionListener opQuitarfila = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			int seleccion = tabla.getSelectedRow();
			
			if(seleccion<0){
				JOptionPane.showMessageDialog(null, "Debe seleccionar la fila que desea quitar","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
				return;
			}else{
				
				boolean destinoOrigen = false;
				String codProd = tabla.getValueAt(seleccion, 0).toString().trim();
				
				for(int i = 0; i<productos_originales.size(); i++){
					if(productos_originales.get(i).toString().trim().equals(codProd)){
						destinoOrigen=true;
					}
				}
				
				if(seleccion < tabla.getRowCount()){
					
					if(!destinoOrigen){
						modelo.removeRow(seleccion);
						calcularTotalCostoDeMerma(13);
						tabla.getSelectionModel().setSelectionInterval(seleccion, seleccion);
						
						cmbRazonDeMerma.setEnabled(tabla.getRowCount()>0?false:true);
					}else{
						JOptionPane.showMessageDialog(null, "Solo Puede Eliminar Los Registros Que Haya Agregado Despues De La Consulta","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
						return;
					}
					
				}
			}
		}
	};
	
	public void validaGuardado(){
			
			txtFolio.setEditable(false);
           
			btnGuardar.setEnabled(true);
			btnQuitarfila.setEnabled(true);
			btnBuscar.setEnabled(false);
			txaNota.setEditable(true);
//			txtEstablecimiento.setEnabled(true);
			 
	}
	
	ActionListener filtro_productos = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			new Cat_Filtro_De_Productos(txtEstablecimiento.getText().trim()).setVisible(true);
		}
	};
	
	ActionListener filtro_inventarios = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			new Cat_Filtro_De_Mermas("").setVisible(true);
		}
	};
	
	ActionListener deshacer = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(tabla.isEditing()){
				tabla.getCellEditor().stopCellEditing();
			}
			
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
		
		if(tabla.isEditing()){
			tabla.getCellEditor().stopCellEditing();
		}
		
		 String[][] tabla_guardado = Objetotabla.tabla_guardar(tabla);
		 if(tabla_guardado.length==0){
			 return;
		 }else{		 
			 
			 calculo_diferencia(fila);
			 
//			 if(imagenCargada){
				 
				 Obj_Alimentacion_De_Mermas mermas = new Obj_Alimentacion_De_Mermas();
				 mermas.setArreglo(tabla_guardado);
				 mermas.setFolio(Integer.valueOf(txtFolio.getText().trim()));
				 mermas.setEstablecimiento(txtEstablecimiento.getText().toString().trim());
				 mermas.setNota(txaNota.getText().toString().trim());
//				 mermas.setRutaFoto(rutaFoto);
				 
//				 guardar con valiacion de cargar imagen por seguridad
				  if(mermas.Guardar_Merma("TERMINADO",folio_usuario_valida)){
					  
					  String folioMerma = new GuardarSQL().finalizar_merma(txtEstablecimiento.getText().trim(), txtFolio.getText().trim());
					  
					  if(!folioMerma.equals("")){
						  JOptionPane.showMessageDialog(null, "La Merma Fue Finalizó Correctamente Con El Folio: ["+folioMerma+"].", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
						  deshacer();
					  }else{
						  JOptionPane.showMessageDialog(null, "El Registro No Se Pudo Ser Finalizádo", "Avise Al Administrador Del Sistema !!!",JOptionPane.ERROR_MESSAGE, new ImageIcon("Imagen/usuario-icono-eliminar5252-64.png"));
					      return;
					  }
		                
			      }else{
					JOptionPane.showMessageDialog(null, "El Registro No Se Guardo", "Avise Al Administrador Del Sistema !!!",JOptionPane.ERROR_MESSAGE, new ImageIcon("Imagen/usuario-icono-eliminar5252-64.png"));
			    	return;
			      }
				  
//			 }else{
//				 JOptionPane.showMessageDialog(null, "Es Necesario Que Ingrese La Foto De La Merma", "Aviso",JOptionPane.ERROR_MESSAGE, new ImageIcon("Imagen/usuario-icono-eliminar5252-64.png"));
//				 return;
//			 }
			 
		 }
	  }			
    };
    
	public void deshacer(){
		lblNota.setText("");
		txaNota.setText("");
		txtcod_prod.setText("");
		txtcod_prod.setEnabled(false);
//		cmbEstablecimiento.removeActionListener(Establecimiento);
		txtEstablecimiento.setText("");
//		txtEstablecimiento.setEnabled(false);
//		cmbEstablecimiento.addActionListener(Establecimiento);
		txtFolio.setEditable(true);
		modelo.setRowCount(0);
		btnGuardar.setEnabled(false);
		btnProducto.setEnabled(false);
		btnQuitarfila.setEnabled(false);
		btnBuscar.setEnabled(true);
		
		cmbRazonDeMerma.setSelectedIndex(0);
		cmbDestinoDeMerma.setSelectedIndex(0);
		calcularTotalCostoDeMerma(13);
		
		cmbRazonDeMerma.setEnabled(tabla.getRowCount()>0?false:true);
		
		try {
			imagB  = Files.readAllBytes(Paths.get(System.getProperty("user.dir")+"/Iconos/Un.JPG"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		
//		rutaFoto="";
//		imagMerma();
	}
	
	public void buscar_producto(){
		int testigo=0;
	     if(!txtcod_prod.getText().equals("")){
	    	 txtFiltro.setText("");
	    	 
	    	int[] columnas ={0,1,2,3,4,5};
	    	new Obj_Filtro_Dinamico_Plus(tabla , txtFiltro.getText().toString().trim().toUpperCase(), columnas  );
	    	 
			try {
				if(new Obj_Cotizaciones_De_Un_Producto().Existe_Producto(txtcod_prod.getText().trim().toUpperCase()+"")){
			      Obj_Alimentacion_De_Inventarios_Parciales  Datos_Producto= new Obj_Alimentacion_De_Inventarios_Parciales().buscardatos_producto(txtcod_prod.getText().trim().toUpperCase()+"", txtEstablecimiento.getText().toString().trim());
					for(int i=0; i<tabla.getRowCount(); i++){
						if(tabla.getValueAt(i, 0).toString().equals(Datos_Producto.getCod_Prod().trim())){
							testigo=1;
				         	 JOptionPane.showMessageDialog(null, "El Producto Ya Existe En La Captura", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				         	 fila=i+1;
						 };							
					  }
					
					  if(testigo==0){
				 		  vector[0] = Datos_Producto.getCod_Prod().trim() ;
				 		  vector[1] = Datos_Producto.getDescripcion_Prod();
				 		  vector[2] = Datos_Producto.getExistencia();
				 		  vector[3] = 0;			//merma captura
				 		  vector[4] = 0;			//merma seguridad
				 		  vector[5] = 0;			//merma auditoria
				 		  vector[6] = 0;			//existencia sin merma
				 		  vector[7] = cmbRazonDeMerma.getSelectedItem().toString();
				 		  vector[8] = cmbDestinoDeMerma.getSelectedItem().toString();
				 		  vector[9] = Datos_Producto.getUltimo_Costo();
				 		  vector[10] = Datos_Producto.getCosto_Promedio();
				 		  vector[11] = Datos_Producto.getPrecio_Lista();
				 		  vector[12] = 0;
				 		  vector[13] = 0;

				 		  modelo.addRow(vector);
	 			 		  txtcod_prod.setText("");
	 			 		  fila=tabla.getRowCount();
	 			 		  
	 			 		  cmbRazonDeMerma.setEnabled(tabla.getRowCount()>0?false:true);
	 			 		  
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
	        		columna=5;
	        		RecorridoFoco(tbl.getSelectedRow()-1,"x");
	        	}
	        }
        });
    }
	
	public void RecorridoFoco(int filap,String parametrosacarfoco){
			if(Objetotabla.RecorridoFocotabla(tabla, filap, 5, parametrosacarfoco).equals("si")){
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
		public class Cat_Filtro_De_Mermas extends JDialog{
			  Container cont = getContentPane();
			  JLayeredPane panel = new JLayeredPane();
			  Connexion con = new Connexion();
			  Runtime R = Runtime.getRuntime();
			  Obj_tabla  Objetotabla = new Obj_tabla();
				
				int columnas = 8,checkbox=-1;
				public void init_tabla_f(String status,String establecimiento){
			    	this.tabla3.getColumnModel().getColumn(0).setMinWidth(100);	
			    	this.tabla3.getColumnModel().getColumn(1).setMinWidth(200);
			    	this.tabla3.getColumnModel().getColumn(2).setMinWidth(120);
			    	this.tabla3.getColumnModel().getColumn(3).setMinWidth(120);
			    	this.tabla3.getColumnModel().getColumn(4).setMinWidth(220);
			    	this.tabla3.getColumnModel().getColumn(5).setMaxWidth(50);
			    	this.tabla3.getColumnModel().getColumn(6).setMinWidth(120);
			    	this.tabla3.getColumnModel().getColumn(7).setMinWidth(220);
			    	
					String comando="exec sp_filtro_de_mermas '"+status+"','"+establecimiento+"'" ;
					String basedatos="26",pintar="si";
					Objetotabla.Obj_Refrescar(tabla3,modelo3, columnas, comando, basedatos,pintar,checkbox);
			    }
				
			  public DefaultTableModel modelo3 = new DefaultTableModel(null, new String[]{"Folio Merma" ,"Fecha" ,"Piezas" ,"Total Ultimo Costo" ,"Total Costo Promedio" ,"Encargado","Establecimiento","Nota"}){
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
			public Cat_Filtro_De_Mermas(String estab){
				int ancho = 1024;//Toolkit.getDefaultToolkit().getScreenSize().width;
				int alto = Toolkit.getDefaultToolkit().getScreenSize().height-50;
				this.setSize(ancho, alto);
				this.setResizable(false);
				this.setLocationRelativeTo(null);
				this.setTitle("Filtro De Busqueda De Mermas Autorizadas");
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
		        
				init_tabla_f("A",estab);
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
					@SuppressWarnings("unchecked")
					public void mouseReleased(MouseEvent e) {
							if(e.getClickCount() == 2){
								int fila_Select = tabla3.getSelectedRow();
				    			String folio =  tabla3.getValueAt(fila_Select, 0).toString().trim();
				    			String estab = tabla3.getValueAt(fila_Select, 6).toString().trim();
				    			String nota = tabla3.getValueAt(fila_Select, 7).toString().trim();
				    			imagB = new BuscarSQL().buscar_foto_de_merma(Integer.valueOf(folio));
				    			
				    			txtFolio.setText(folio);
				    			txtEstablecimiento.setText(estab);
				    			lblNota.setText(nota);
//				    			txaNota.setText(nota);
     			  				
     			  				validaGuardado();
//     			  				cmbEstablecimiento.setEnabled(false);
     			  				
     			  				init_tabla(folio);
     			  				
     			  				for(int i=0 ; i<tabla.getRowCount(); i++){
     			  					productos_originales.add(tabla.getValueAt(i, 0).toString());
     			  				}
     			  				
     		                	 btnProducto.setEnabled(true);
     		                	 txtcod_prod.setEnabled(true);
     		                	 txtcod_prod.requestFocus();
     		                	calcularTotalCostoDeMerma(13);
     		                	cmbRazonDeMerma.setSelectedItem(tabla.getValueAt(0, 7).toString().trim());
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
		
		public class FotoMerma extends JDialog{
			Container cont = getContentPane();
			JLayeredPane panel = new JLayeredPane();
			
			JLabel lblFoto = new JLabel("");
			
			@SuppressWarnings({ })
			public FotoMerma()
			{
				this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Filter-List-icon32.png"));
				panel.setBorder(BorderFactory.createTitledBorder("Foto"));	
				this.setModal(true);
				panel.add(lblFoto).setBounds(20,10,500,500);
				
				lblFoto.setIcon(crearIcon(imagB));
				
				cont.add(panel);
				this.setTitle("Visualizar Merma");
				this.setSize(540,540);
				this.setLocationRelativeTo(null);
				this.setResizable(false);
				
			}
		}
		
//public class Llamar_Camara extends Cat_Camara{
//			
//			public Llamar_Camara(){
//				
//				btnCapturar.addActionListener(opFoto);
//			}
//			
//			ActionListener opFoto = new ActionListener() {
//				public void actionPerformed(ActionEvent e) {
//					if(e.getSource() == btnCapturar){
//						
//						rutaFoto = System.getProperty("user.dir")+"/tmp/tmp_mermas/merma.jpg";
//						File folder = new File(rutaFoto);
//		            	folder.mkdirs();
//		            	
//						BufferedImage image = webcam.getImage();
//						try {
//							ImageIO.write(image, "JPG", new File(rutaFoto));
//							imagenCargada = true ;
//							imagMerma();
//							System.out.println(rutaFoto);
//							
//							ImageIcon tmpIconDefault = new ImageIcon(rutaFoto);
//					        Icon iconoDefault = new ImageIcon(tmpIconDefault.getImage().getScaledInstance(lblVistaPrevia.getWidth(), lblVistaPrevia.getHeight(), Image.SCALE_DEFAULT));
//					        lblVistaPrevia.setIcon(iconoDefault);
//							
//						} catch (Exception ex) {
//							imagenCargada = false ;
//						}	
//					}	
//				}
//			};
//		}
		
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Revision_De_Mermas_Por_Auditoria().setVisible(true);
		}catch(Exception e){	}
	}
};
	
