package Cat_Inventarios;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Connexion;
import Obj_Compras.Obj_Ubicaciones_De_Productos;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_Filtro_Dinamico_Plus;
import Obj_Principal.Obj_tabla;

@SuppressWarnings("serial")
public class Cat_Datos_De_Productos_BMS extends JFrame{
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	Obj_tabla ObjTab =new Obj_tabla();
	int columnas = 5,checkbox=-1;
	
	@SuppressWarnings("rawtypes")
	public Class[] baselunes (){
		Class[] types = new Class[columnas];
		for(int i = 0; i<columnas; i++){types[i]= java.lang.Object.class;}
		 return types;
	}
	public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"Establecimiento","Localizacion","Minimo","Maximo","Existencia","Costo Prom","Ultimo Costo","Fecha Ultima Vez Agoto","Fecha Ultima Recepcion","Cantidad Ultima Recepcion"}){
		 @SuppressWarnings("rawtypes")
			Class[] types = baselunes();
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
			public boolean isCellEditable(int fila, int columna){return false;}
	};
	JTable tabla = new JTable(modelo);
	public JScrollPane Scroll_Tabla = new JScrollPane(tabla);
     @SuppressWarnings({ "rawtypes", "unused" })
    private TableRowSorter trsfiltro;
     
 	String FActividadesCargado ="";
	String[][] tablaprecargadaproductos;
	
     int columnas2 = 7;
		public void init_tabla_filtro_productos(){
	    	this.tabla2.getColumnModel().getColumn(0).setMinWidth(90);	
	    	this.tabla2.getColumnModel().getColumn(1).setMinWidth(410);
	    	this.tabla2.getColumnModel().getColumn(2).setMinWidth(150);
	    	this.tabla2.getColumnModel().getColumn(3).setMinWidth(190);
	    	this.tabla2.getColumnModel().getColumn(4).setMinWidth(100);
	    	this.tabla2.getColumnModel().getColumn(5).setMinWidth(100);
	    	
			String comando="exec inventarios_filtro_catalogo_de_productos_con_80_20 ''" ;
			String basedatos="200",pintar="si";
		
			ObjTab.Obj_Refrescar(tabla2, modelo2, columnas2, comando, basedatos,pintar,checkbox);
		}
			String establecimiento80="";
			
		 public DefaultTableModel modelo2 = new DefaultTableModel(null, new String[]{"Codigo Producto","Descripcion","Clase Producto","Categoria","Familia","Marca","80/20 De "+establecimiento80}){
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
				
				public boolean isCellEditable(int fila, int columna2){
							if(columna2 ==3)
							return true; return false;
						}
			    };
				    
			   JTable tabla2 = new JTable(modelo2);
				public JScrollPane scroll_tabla_filtro = new JScrollPane(tabla2);
				
				
	String establecimiento[] = new Obj_Establecimiento().Combo_Establecimiento201();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbEstablecimiento = new JComboBox(establecimiento);
	
	JTextField txtcodigo_prod          = new Componentes().text(new JCTextField(), "Teclea El Codigo del Producto", 16, "String");
	
    JTextArea txtdescripcion       = new Componentes().textArea(new JTextArea(), "Descripcion del Producto", 500);
	JScrollPane descripcion        = new JScrollPane(txtdescripcion);
	
    JTextArea txtCod_Prod          = new Componentes().textArea(new JTextArea(), "Codigo del Producto", 500);
	JScrollPane codigo             = new JScrollPane(txtCod_Prod);
	
    JTextArea txtCod_Barras        = new Componentes().textArea(new JTextArea(), "Codigo De Barras del Producto", 500);
	JScrollPane codigo_barras      = new JScrollPane(txtCod_Barras);
	
    JTextArea txtPrecioVenta      = new Componentes().textArea(new JTextArea(), "Precio De Venta", 500);
	JScrollPane PrecioVenta       = new JScrollPane(txtPrecioVenta);
	
    JTextArea txtLocalizacion      = new Componentes().textArea(new JTextArea(), "Localizacion", 500);
	JScrollPane localizacion       = new JScrollPane(txtLocalizacion);
	
	
	JCButton btndeshacer               = new JCButton("Deshacer","deshacer16.png","Azul");
	JCButton btnProducto               = new JCButton("Productos"    ,"Filter-List-icon16.png","Azul");
	
	JLabel   JLBClase_De_Producto      = new JLabel("");
	JLabel   JLBCategoria              = new JLabel("");  
	JLabel   JLBFamilia                = new JLabel("");
	JLabel   JLBArea                   = new JLabel("");  
	JLabel   JLBUltimo_Costo           = new JLabel("");
	JLabel   JLBCosto_Promedio         = new JLabel(""); 
	JLabel   JLBExistencia_Cedis       = new JLabel("");
	JLabel   JLBExistencia_Total       = new JLabel("");
	JLabel   JLBprecio_de_venta        = new JLabel("");
	JLabel   JLBCant_Negada_Ult_7_dias = new JLabel("");
	JLabel   JLBMaximo                 = new JLabel("");
	JLabel   JLBMinimo                 = new JLabel("");
	JLabel   JLBFecha_Agotado          = new JLabel("");
	
	String htmlini ="<html> <FONT FACE="+"arial"+" SIZE=5 COLOR=BLACK><RIGHT><b><p>";
	String htmlfin ="</p></b></RIGHT></FONT></html>";
	
	String htmlinib ="<html> <FONT FACE="+"arial"+" SIZE=5 COLOR=BLUE><RIGHT><b><p>";
	String htmlfinb ="</p></b></RIGHT></FONT></html>";
	
	public Cat_Datos_De_Productos_BMS(){
		setSize(700,400);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Datos De Productos BMS");
		setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/articulo-icono-9036-48.png"));
		panel.setBorder(BorderFactory.createTitledBorder("Seleccione Establecimiento y Escanee El Codigo Del Producto"));
		
		int x=10, y=25, width=140,height=20,sep=30,sep2=140;
		panel.add(cmbEstablecimiento).setBounds                               (x      ,y        ,180     ,height   );
		panel.add(txtcodigo_prod).setBounds                                   (x+200  ,y        ,180     ,height   );
		panel.add(btnProducto).setBounds                                      (x+380  ,y        ,120     ,height   );
		panel.add(btndeshacer).setBounds                                      (x+550  ,y        ,120     ,height   );
		
		panel.add(new JLabel("CODIGO:")).setBounds                         	  (x       ,y+=25   ,width   ,height   );
		panel.add(codigo).setBounds                                   	      (x       ,y+=15   ,width   ,height   );
		
		panel.add(new JLabel("CODIGO BARRAS:")).setBounds                  	  (x+=140  ,y-=15   ,width   ,height   );
		panel.add(codigo_barras).setBounds                             	      (x       ,y+=15   ,width   ,height   );
		
		panel.add(new JLabel("PRECIO VENTA:")).setBounds                  	  (x+=140  ,y-=15   ,width   ,height   );
		panel.add(PrecioVenta).setBounds                                	  (x       ,y+=15   ,width   ,height   );
		
		panel.add(new JLabel("LOCALIZACION:")).setBounds                  	  (x+=140  ,y-=15   ,width   ,height   );
		panel.add(localizacion).setBounds                                	  (x       ,y+=15   ,width   ,height   );
		
		width=600;
		panel.add(new JLabel("DESCRIPCION:")).setBounds                       (x=10    ,y+=20   ,width   ,height   );
		panel.add(descripcion).setBounds                               	      (x       ,y+=15   ,675     ,42       );
		panel.add(new JLabel(htmlinib+"CLASE:"+htmlfinb)).setBounds       	  (x       ,y+=sep*2,width   ,height   );
		panel.add(JLBClase_De_Producto).setBounds                         	  (x+sep2  ,y       ,width   ,height   );
		panel.add(new JLabel(htmlinib+"CATEGORIA:"+htmlfinb)).setBounds       (x       ,y+=sep  ,width   ,height   );
		panel.add(JLBCategoria).setBounds                                     (x+sep2  ,y       ,width   ,height   );
		panel.add(new JLabel(htmlinib+"FAMILIA:"+htmlfinb)).setBounds         (x       ,y+=sep  ,width   ,height   );	
		panel.add(JLBFamilia).setBounds                                       (x+sep2  ,y       ,width   ,height   );
		panel.add(new JLabel(htmlinib+"AREA:"+htmlfinb)).setBounds            (x       ,y+=sep  ,width   ,height   );	
		panel.add(JLBArea).setBounds                                          (x+sep2  ,y       ,width   ,height   );
		
		panel.add(new JLabel(htmlinib+"EXISTENCIA:"+htmlfinb)).setBounds      (x       ,y+=sep  ,width   ,height   );	
		panel.add(JLBExistencia_Total).setBounds                              (x+sep2  ,y       ,width   ,height   );
		panel.add(new JLabel(htmlinib+"FECHA AGOTADO:"+htmlfinb)).setBounds   (x+330   ,y       ,width   ,height   );	
		panel.add(JLBFecha_Agotado).setBounds                                 (x+500   ,y       ,width   ,height   );
		
		panel.add(new JLabel(htmlinib+"MINIMO:"+htmlfinb)).setBounds          (x       ,y+=sep  ,width   ,height   );	
		panel.add(JLBMaximo).setBounds                                        (x+sep2  ,y       ,width   ,height   );
		panel.add(new JLabel(htmlinib+"MAXIMO:"+htmlfinb)).setBounds          (x+330   ,y       ,width   ,height   );	
		panel.add(JLBMinimo).setBounds                                        (x+500   ,y       ,width   ,height   );
		
		textareaajuste(txtdescripcion);
		textareaajuste(txtCod_Prod);
		textareaajuste(txtCod_Barras);
		textareaajuste(txtLocalizacion);
		textareaajuste(txtPrecioVenta);
		
		cmbEstablecimiento.requestFocus();
		cont.add(panel);
		btndeshacer.addActionListener(opdeshacer);
		txtcodigo_prod.addKeyListener(Buscar_Datos_Producto);
		btnProducto.addActionListener(filtro_producto);
		
        this.addWindowListener(new WindowAdapter(){public void windowOpened( WindowEvent e ){cmbEstablecimiento.requestFocus();cmbEstablecimiento.showPopup();}});
		
	}
	
	ActionListener filtro_producto = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		  new Cat_Filtro_De_Productos().setVisible(true);
		}
	};
		
	public void textareaajuste(final JTextArea parametro) {
		parametro.setLineWrap(true); 
		parametro.setWrapStyleWord(true);
		parametro.setEditable(false);
		parametro.setBackground(new Color(Integer.parseInt("EBEBEB",16)));
		Font font = new Font("Arial", Font.BOLD, 12);
		parametro.setFont(font );
	}
	
	ActionListener opdeshacer = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			cmbEstablecimiento.setEnabled(true);
			txtCod_Prod.setText         ("");
			txtdescripcion.setText      ("");
			txtLocalizacion.setText     ("");
			
			JLBClase_De_Producto.setText("");
			JLBCategoria.setText        ("");
			JLBFamilia.setText          ("");
			JLBArea.setText             ("");

			JLBExistencia_Cedis.setText ("");
			JLBExistencia_Total.setText ("");
			JLBFecha_Agotado.setText    ("");
			JLBMaximo.setText           ("");
			JLBMinimo.setText           ("");
			txtcodigo_prod.setText      ("");
		    cmbEstablecimiento.requestFocus();
			cmbEstablecimiento.showPopup();
		   return;	
		}
	};
	
	KeyListener Buscar_Datos_Producto = new KeyListener() {
		public void keyTyped(KeyEvent e){}
		public void keyReleased(KeyEvent e) {}
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				llenar_datos_producto();
			}
		}
	};
	public void llenar_datos_producto() {
		String codigo=txtcodigo_prod .getText().toUpperCase().trim();
		String cod_prod=new BuscarSQL().cod_prod_principal_bms(codigo);
		btndeshacer.doClick();
		if(cmbEstablecimiento.getSelectedIndex()==0){
			      JOptionPane.showMessageDialog(null,"Debe de Seleccionar Primero Un Establecimiento: ","Aviso!", JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
			      cmbEstablecimiento.requestFocus();
				  cmbEstablecimiento.showPopup();
			   	 return;		
			}else{
				if(!cod_prod.equals("false no existe") ){
				  cmbEstablecimiento.setEnabled(false);
				  Obj_Ubicaciones_De_Productos  Datos_Producto= new Obj_Ubicaciones_De_Productos().buscardatos_producto(cod_prod,cmbEstablecimiento.getSelectedItem().toString().trim().toUpperCase()+"");
					txtdescripcion.setText      (Datos_Producto.getDescripcion_Prod().toString().trim());
					txtCod_Prod.setText         (Datos_Producto.getCod_Prod().toString().trim());
					txtCod_Barras.setText       (Datos_Producto.getCodigo_Barras().toString().trim());
					txtPrecioVenta.setText      (Datos_Producto.getPrecio_de_venta()+"");							
					txtLocalizacion.setText     (Datos_Producto.getLocalizacion().toString().trim());
					JLBClase_De_Producto.setText(htmlini+Datos_Producto.getClase_De_Producto().toString().trim()+htmlfin);
					JLBCategoria.setText        (htmlini+Datos_Producto.getCategoria().toString().trim()+htmlfin);
					JLBFamilia.setText          (htmlini+Datos_Producto.getFamilia().toString().trim()+htmlfin);
					JLBArea.setText             (htmlini+Datos_Producto.getArea().toString().trim()+htmlfin);

					JLBExistencia_Cedis.setText (htmlini+Datos_Producto.getExistencia_Cedis()+"".trim()+htmlfin);
					JLBExistencia_Total.setText (htmlini+Datos_Producto.getExistencia_Total()+"".trim()+htmlfin);
					JLBFecha_Agotado.setText    (htmlini+Datos_Producto.getFecha_Ultima_Vez_Se_Agoto().toString().trim()+htmlfin);
					JLBMaximo.setText           (htmlini+Datos_Producto.getMaximo()+"".trim()+htmlfin);
					JLBMinimo.setText           (htmlini+Datos_Producto.getMinimo()+"".trim()+htmlfin);
					txtcodigo_prod.requestFocus();
				 }else{
						JOptionPane.showMessageDialog(null, "El Codigo "+cod_prod+" Esta Mal Escrito o El Producto No Existe" , "Aviso", JOptionPane.CANCEL_OPTION,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
						txtcodigo_prod.requestFocus();
						cmbEstablecimiento.setEnabled(true);
						return;
                }
		}	 
	};
	
	
	//TODO FILTRO DE PRODUCTOS	
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
				panel.add(scroll_tabla_filtro).setBounds(15,y+=20,ancho-30,alto-70);
		        
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
				    			txtcodigo_prod.setText(folio);
				    			txtcodigo_prod.requestFocus();
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
			new Cat_Datos_De_Productos_BMS().setVisible(true);
		}catch(Exception e){	}
	}
}
