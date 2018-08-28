package Cat_Inventarios;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Connexion;
import Obj_Compras.Obj_Ubicaciones_De_Productos;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_Filtro_Dinamico_Plus;
import Obj_Principal.Obj_tabla;

@SuppressWarnings("serial")
public class Cat_Existencia_De_Un_Producto_En_Establecimientos extends JDialog{
	
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
				
	JTextField txtcodigo_prod          = new Componentes().text(new JCTextField(), "Teclea El Codigo Del Producto", 15, "String");
	JCButton btndeshacer               = new JCButton("Deshacer","deshacer-icono-4321-32.png","Azul");
	JCButton btnProducto               = new JCButton("Productos"    ,"Filter-List-icon16.png","Azul");
	
	JLabel   JLBcod_prod               = new JLabel("");  
	JLabel   JLBdescripcion            = new JLabel("");  
	JLabel   JLBEstatus                = new JLabel("");
	JLabel   JLBClase_De_Producto      = new JLabel("");
	JLabel   JLBCategoria              = new JLabel("");  
	JLabel   JLBFamilia                = new JLabel("");
	JLabel   JLBArea                   = new JLabel("");  
	
	String htmlini ="<html> <FONT FACE="+"arial"+" SIZE=5 COLOR=BLACK><RIGHT><b><p>";
	String htmlfin ="</p></b></RIGHT></FONT></html>";
	
	String htmlinib ="<html> <FONT FACE="+"arial"+" SIZE=5 COLOR=BLUE><RIGHT><b><p>";
	String htmlfinb ="</p></b></RIGHT></FONT></html>";
	String[][] tablacompleta;
	  Object[]   vector = new Object[10];
	public Cat_Existencia_De_Un_Producto_En_Establecimientos(){
		this.setModal(true);
		setSize(800,600);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Datos De Productos BMS");
		setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/articulo-icono-9036-48.png"));
		panel.setBorder(BorderFactory.createTitledBorder("Escanee o Teclee El Codigo Del Producto"));
		

		ObjTab.tabla_precargada_derecha(tabla);
		
		int x=10, y=10, width=400,height=20,sep=25,sep2=130;
		panel.add(txtcodigo_prod).setBounds                                   (x      ,y+=10    ,200     ,height   );
		panel.add(btnProducto).setBounds                                      (x+200  ,y        ,115     ,height   );
		
		panel.add(btndeshacer).setBounds                                      (x+520  ,y-5      ,140     ,28       );
		
		width=600;
		panel.add(new JLabel(htmlinib+"CODIGO:"+htmlfinb)).setBounds      	  (x       ,y+=sep  ,width   ,height   );
		panel.add(JLBcod_prod).setBounds                                   	  (x+sep2  ,y       ,width   ,height   );
		panel.add(new JLabel(htmlinib+"ESTATUS:"+htmlfinb)).setBounds	      (x+330   ,y       ,width   ,height   );
		panel.add(JLBEstatus).setBounds                              	      (x+450   ,y       ,width   ,height   );
		
		panel.add(new JLabel(htmlinib+"DESCRIPCION:"+htmlfinb)).setBounds     (x       ,y+=sep+7,width   ,height   );
		panel.add(JLBdescripcion).setBounds                               	  (x+sep2  ,y-=6    ,width   ,37       );
		panel.add(new JLabel(htmlinib+"CLASE:"+htmlfinb)).setBounds       	  (x       ,y+=sep*2,width   ,height   );
		panel.add(JLBClase_De_Producto).setBounds                         	  (x+sep2  ,y       ,width   ,height   );
		panel.add(new JLabel(htmlinib+"CATEGORIA:"+htmlfinb)).setBounds       (x       ,y+=sep  ,width   ,height   );
		panel.add(JLBCategoria).setBounds                                     (x+sep2  ,y       ,width   ,height   );
		panel.add(new JLabel(htmlinib+"FAMILIA:"+htmlfinb)).setBounds         (x       ,y+=sep  ,width   ,height   );	
		panel.add(JLBFamilia).setBounds                                       (x+sep2  ,y       ,width   ,height   );
		panel.add(new JLabel(htmlinib+"AREA:"+htmlfinb)).setBounds            (x       ,y+=sep  ,width   ,height   );	
		panel.add(JLBArea).setBounds                                          (x+sep2  ,y       ,width   ,height   );
		panel.add(Scroll_Tabla).setBounds                                     (x       ,y+=sep  ,775     ,340      );
		
		cont.add(panel);
		btndeshacer.addActionListener(opdeshacer);
		txtcodigo_prod.addKeyListener(Buscar_Datos_Producto);
		btnProducto.addActionListener(opbuscar_producto);
		this.tabla.getColumnModel().getColumn( 0).setMinWidth(170);
		this.tabla.getColumnModel().getColumn( 7).setMinWidth(140);
		this.tabla.getColumnModel().getColumn( 8).setMinWidth(140);
		this.tabla.getColumnModel().getColumn( 9).setMinWidth(120);
		
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), "producto");
        getRootPane().getActionMap().put("producto", new AbstractAction(){public void actionPerformed(ActionEvent e){btnProducto.doClick();}});

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
        getRootPane().getActionMap().put("escape", new AbstractAction(){ public void actionPerformed(ActionEvent e){btndeshacer.doClick();}});
		
	}
	
	ActionListener opbuscar_producto = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			new Cat_Filtro_De_Productos().setVisible(true);;
		   return;	
		}
	};
	
	ActionListener opdeshacer = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			modelo.setRowCount(0);
			JLBcod_prod.setText         ("");
			JLBdescripcion.setText      ("");
			JLBClase_De_Producto.setText("");
			JLBCategoria.setText        ("");
			JLBFamilia.setText          ("");
			JLBArea.setText             ("");
			JLBEstatus.setText          ("");
			txtcodigo_prod.setText      ("");
		   return;	
		}
	};

	public void cargar_datos_tablas(String cod_prod){
		  Obj_Ubicaciones_De_Productos ubicaciones = new Obj_Ubicaciones_De_Productos();
		  String[][] tablacompleta= ubicaciones.Buscar_Existencia_De_Producto_En_Establecimiento(cod_prod);
		  
		    JLBcod_prod.setText         (htmlini+tablacompleta[0][0].toString()+htmlfin);
			JLBdescripcion.setText      (htmlini+tablacompleta[0][1].toString()+htmlfin);
			JLBClase_De_Producto.setText(htmlini+tablacompleta[0][2].toString()+htmlfin);
			JLBCategoria.setText        (htmlini+tablacompleta[0][3].toString()+htmlfin);
			JLBFamilia.setText          (htmlini+tablacompleta[0][4].toString()+htmlfin);
			JLBArea.setText             (htmlini+tablacompleta[0][5].toString()+htmlfin);
			JLBEstatus.setText          (htmlini+tablacompleta[0][6].toString()+htmlfin);

		    for(int i=0;i<tablacompleta.length;i++){
	            for(int j=9,v=0;j<19;j++,v++){ 
	            	vector[v] = tablacompleta[i][j].toString();
	            }
	   			modelo.addRow(vector);
		    }
		
		}
	
	
	
	KeyListener Buscar_Datos_Producto = new KeyListener() {
		public void keyTyped(KeyEvent e){}
		public void keyReleased(KeyEvent e) {}
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				String codigo=txtcodigo_prod .getText().toUpperCase().trim();
				String cod_prod=new BuscarSQL().cod_prod_principal_bms(codigo);
				btndeshacer.doClick();
						if(!cod_prod.equals("false no existe") ){
							cargar_datos_tablas(cod_prod);
							txtcodigo_prod.requestFocus();
						 }else{
								JOptionPane.showMessageDialog(null, "El Codigo "+cod_prod+" Esta Mal Escrito o El Producto No Existe" , "Aviso", JOptionPane.CANCEL_OPTION,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
								txtcodigo_prod.requestFocus();
								return;
		                }
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
			    			cargar_datos_tablas(folio);
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
			new Cat_Existencia_De_Un_Producto_En_Establecimientos().setVisible(true);
		}catch(Exception e){	}
	}
}
