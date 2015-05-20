package Cat_Compras;

import java.awt.AWTException;
import java.awt.Container;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;



import com.toedter.calendar.JDateChooser;



import Conexiones_SQL.Connexion;
import Obj_Compras.Obj_Cotizaciones_De_Un_Producto;
import Obj_Principal.Componentes;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Costos_Competencia extends JFrame{
				Container cont = getContentPane();
				JLayeredPane panel = new JLayeredPane();
				
				JTextField txtcod_prod = new Componentes().text(new JTextField(), "Codigo del Producto", 15, "String");
				JButton btnBuscar_Producto = new JButton("",new ImageIcon("imagen/Filter-List-icon16.png"));
				JButton btnGuardar = new JButton("Guardar",new ImageIcon("imagen/Guardar.png"));
				JButton btnDeshacer = new JButton("Deshacer",new ImageIcon("imagen/deshacer16.png"));
				
				JLabel Marcoproveedor1= new JLabel();
				JLabel JLBdescripcion= new JLabel();
				JTextField txtultimo_costo= new JTextField();
				JTextField txtcosto_promedio= new JTextField();
				JTextField txtPrecioVenta= new JTextField();
				
				JTextArea txaCondiciones = new Componentes().textArea(new JTextArea(), "Condiciones De La Compra", 350);
				JScrollPane Condiciones = new JScrollPane(txaCondiciones);
				
				JDateChooser cfecha = new JDateChooser();
				Connexion con = new Connexion();
				
				DefaultTableModel modelo_captura = new DefaultTableModel(null,
			            new String[]{"Competencia", "Costo"}
						){
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
				
				JTable tabla_captura = new JTable(modelo_captura);
				JScrollPane scrollcaptura = new JScrollPane(tabla_captura,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				
				
				DefaultTableModel modelo_prv = new DefaultTableModel(null,
			            new String[]{"Compra","Cod.Prv", "Proveedor","Fecha Compra","Unid","Cont","Cantidad","Costo","Costo PZ","Ultimo Costo","Costo Prom","Exist Cedis","Exist Total","Nota de La Negociacion","Cotizo"}
						){
				     @SuppressWarnings("rawtypes")
					Class[] types = new Class[]{
				    	java.lang.String.class,
				    	java.lang.String.class,
				    	java.lang.String.class,
				    	java.lang.String.class,
				    	java.lang.String.class,
				    	java.lang.String.class,
				    	java.lang.String.class,
				    	java.lang.String.class,
				    	java.lang.String.class,
				    	java.lang.String.class,
				    	java.lang.String.class,
				    	java.lang.String.class,
				    	java.lang.String.class,
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
			        	 	case 2 : return false;
			        	 	case 3 : return false;
			        	 	case 4 : return false;
			        	 	case 5 : return false;
			        	 	case 6 : return false;
			        	 	case 7 : return false;
			        	 	case 8 : return false;
			        	 	case 9 : return false;
			        	 	case 10 : return true;
			        	 	case 11 : return true;
			        	 	case 12 : return true;
			        	 	case 13 : return true;
			        	 	case 14 : return true;
			        	 } 				
			 			return false;
			 		}
				};
			    JTable tabla_Proveedor = new JTable(modelo_prv);
			    JScrollPane scrollAsignado = new JScrollPane(tabla_Proveedor);
			    
			    
				Border blackline, etched, raisedbevel, loweredbevel, empty;
				
			  String codigo_producto = "";
	          String Nombre_Catalogo_Para_Filtro="";
	  		  String descripcion="";
			  double ultimo_costo=0;
			  double costo_promedio=0;
			  double venta_total=0;
			  
	public Cat_Costos_Competencia(String cod_prod){
		
		codigo_producto=cod_prod+"";
		txtcod_prod.setText(codigo_producto+"");

		setSize(1024,635);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Cotizaciones De Un Producto En Proveedores");
		setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/encontrar-busqueda-lupa-de-la-ventana-de-zoom-icono-4008-32.png"));
		blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
		panel.setBorder(BorderFactory.createTitledBorder(blackline,"Seleccione el Tipo de Reporte"));
		txaCondiciones.setBorder(BorderFactory.createTitledBorder(blackline));
		txaCondiciones.setLineWrap(true); 
		txaCondiciones.setWrapStyleWord(true);

		int x=10 ;
		int y=20 ;
		int a=20;
		int l=100;
		
		panel.add(new JLabel("Fecha:")).setBounds(x,y,l-40,a);
		panel.add(cfecha).setBounds(x+85,y,l+10,a);
		
		panel.add(scrollcaptura).setBounds(x+385,y,250,140);
		
		panel.add(new JLabel("Producto:")).setBounds(x,y+=25,l-40,a);
		panel.add(txtcod_prod).setBounds(x+85,y,l,a);
		panel.add(btnBuscar_Producto).setBounds((l+=x)+85,y,a,a);
		
		panel.add(JLBdescripcion).setBounds(x,y+=25,l+340,a);
		
		
		panel.add(new JLabel("Ultimo Costo:")).setBounds(x,y+=25,l-40,a);
		panel.add(txtultimo_costo).setBounds(x+85,y,l-35,a);
		
		panel.add(new JLabel("Costo Promedio:")).setBounds(x,y+=25,l-30,a);
		panel.add(txtcosto_promedio).setBounds(x+85,y,l-35,a);
		panel.add(btnDeshacer).setBounds(x+165,y,l,a);
		
		panel.add(new JLabel("Precio De Venta:")).setBounds(x,y+=25,l-20,a);
		panel.add(txtPrecioVenta).setBounds(x+85,y,l-35,a);
		panel.add(btnGuardar).setBounds(x+165,y,l,a);
		
		panel.add(Tabla_Proveedor()).setBounds(10,170,1000,430);
		
		Condiciones.setEnabled(false);
		Condiciones.getHorizontalScrollBar().setEnabled(false);
		Condiciones.getVerticalScrollBar().setEnabled(false);
		Condiciones.getViewport().getView().setEnabled(false);
		
		btnDeshacer.setEnabled(false);
		btnGuardar.setEnabled(false);
	    cfecha.setEnabled(true);
	    
	    render_proveedor();
	    
		cont.add(panel);
		
		Nombre_Catalogo_Para_Filtro=this.getClass().getSimpleName();
		btnBuscar_Producto.addActionListener(opBuscar_Producto);
		btnDeshacer.addActionListener(deshacer);
		
		btnGuardar.addActionListener(Guardar);
		
		txtcod_prod.addKeyListener(Buscar_Datos_Producto);
		
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), "buscarLR");
          getRootPane().getActionMap().put("buscarLR", new AbstractAction(){
         public void actionPerformed(ActionEvent e)
         {         
        	 btnBuscar_Producto.doClick();
       	    }
         });
          
          
          if(codigo_producto.equals("")){
			          this.addWindowListener(new WindowAdapter() {
			                  public void windowOpened( WindowEvent e ){
			                  	txtcod_prod.requestFocus();
			                  	 }
			          });
          }else{
			          this.addWindowListener(new WindowAdapter() {
		                  public void windowOpened( WindowEvent e ){
		                  	txtcod_prod.requestFocus();
		                  	enterauto();
		                  	 }
		              });
          }
          
          
          String fecha = new Obj_Cotizaciones_De_Un_Producto().Hoymenos3meses().getFecha();
          java.util.Date Fecha = null;
		try {
			Fecha = new SimpleDateFormat("dd/MM/yyyy").parse(fecha);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
          cfecha.setDate(Fecha);
          cfecha.getDateEditor().addPropertyChangeListener(opBusqueda_Con_La_Fecha);
	}
	
	
	public void enterauto(){
		Robot robot;
		try {
            robot = new Robot();
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
        } catch (AWTException e) {
            e.printStackTrace();
        }
     };
	     
	 PropertyChangeListener opBusqueda_Con_La_Fecha = new PropertyChangeListener() {
	     	  public void propertyChange(PropertyChangeEvent e) {
	     	            if ("date".equals(e.getPropertyName())) {
	     	            	
	   	  	            	if(cfecha.getDate() != null){
	   	  	            		
	   	  	            	while(tabla_Proveedor.getRowCount()>0){
								modelo_prv.removeRow(0);  }
//							 Llenar_Tabla_proveedores ();
							 render_proveedor();
	   	  	            	}
	          }
	   	}
	 };
	
	
	ActionListener opBuscar_Producto = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			dispose();
			new Cat_Filtro_De_Busqueda_De_Productos(Nombre_Catalogo_Para_Filtro,"").setVisible(true);
		}
	};
	
	ActionListener salir = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			dispose();
		}
	};
	
	ActionListener nuevo = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			
		    btnDeshacer.setEnabled(true); 
		    btnGuardar.setEnabled(true);
			Condiciones.getHorizontalScrollBar().setEnabled(true);
			Condiciones.getVerticalScrollBar().setEnabled(true);
			Condiciones.getViewport().getView().setEnabled(true);
			
		}
	};
	
	ActionListener deshacer = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			txaCondiciones.setText("");
			
			Condiciones.setEnabled(false);
			Condiciones.getHorizontalScrollBar().setEnabled(false);
			Condiciones.getVerticalScrollBar().setEnabled(false);
			Condiciones.getViewport().getView().setEnabled(false);
			
			btnDeshacer.setEnabled(false);
			btnGuardar.setEnabled(false);
			
		}
	};
	
	ActionListener limpiar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			
			txtcod_prod.setText("");
			txaCondiciones.setText("");
			
			Condiciones.setEnabled(false);
			Condiciones.getHorizontalScrollBar().setEnabled(false);
			Condiciones.getVerticalScrollBar().setEnabled(false);
			Condiciones.getViewport().getView().setEnabled(false);
			
			
			btnDeshacer.setEnabled(false);
			btnGuardar.setEnabled(false);
			
			while(tabla_Proveedor.getRowCount()>0){
				modelo_prv.removeRow(0);  }
			
			JLBdescripcion.setText("");
			txtultimo_costo.setText("");
			txtcosto_promedio.setText("");
			txtPrecioVenta.setText("");
		}
	};
	
	ActionListener Guardar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			
			if(validaCampos()!="") {
				JOptionPane.showMessageDialog(null, "los siguientes campos son requeridos:\n"+validaCampos(), "Error al guardar registro", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
				return;
			}else{
                Obj_Cotizaciones_De_Un_Producto cotizacion_prod = new Obj_Cotizaciones_De_Un_Producto();					
				
                cotizacion_prod.setCod_Prod(codigo_producto);
                cotizacion_prod.setUltimo_Costo(ultimo_costo);
                cotizacion_prod.setCosto_Promedio(costo_promedio);
                cotizacion_prod.setNotas_Negociacion(txaCondiciones.getText().toUpperCase().trim()+"");
//                cotizacion_prod.setExistencia_Total(exist_total);
                
                
               if(cotizacion_prod.Guardar_Cotizacion()){
            	   btnDeshacer.doClick();  
            	   while(tabla_Proveedor.getRowCount()>0){
						modelo_prv.removeRow(0);  }
//					 Llenar_Tabla_proveedores ();
					 render_proveedor();
            	   JOptionPane.showMessageDialog(null, "Se Guardo Correctamente:","Aviso", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen//aplicara-el-dialogo-icono-6256-32.png"));
   				return;
               }
   			   JOptionPane.showMessageDialog(null, "Error  en la funcion [ Guardar ] \n if(Obj_Cotizaciones_De_Un_Producto.Guardar_Cotizacion()) ", "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				return;
                
				}
			
		}
	};
	private String validaCampos(){
		String error="";
		if(codigo_producto.equals("")) error +="Codigo de Producto \n";
		if(txaCondiciones.getText().equals(""))	error+= "Condiciones De La Compra\n";
		return error;
	}
	
	KeyListener pasa_notas= new KeyListener() {
		public void keyTyped(KeyEvent e){}
		public void keyReleased(KeyEvent e) {}
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				txaCondiciones.requestFocus();
			}
		}
	};
	
	KeyListener Buscar_Datos_Producto = new KeyListener() {
		public void keyTyped(KeyEvent e){}
		public void keyReleased(KeyEvent e) {}
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				venta_total=0;
				try {
					
					if(new Obj_Cotizaciones_De_Un_Producto().Existe_Producto(txtcod_prod.getText().trim().toUpperCase()+"")){
						
				      Obj_Cotizaciones_De_Un_Producto  Datos_Producto= new Obj_Cotizaciones_De_Un_Producto().buscardatos_producto(txtcod_prod.getText().trim().toUpperCase()+"");
			            
						descripcion=Datos_Producto.getDescripcion_Prod();
						ultimo_costo=Datos_Producto.getUltimo_Costo();
						costo_promedio=Datos_Producto.getCosto_Promedio();
						txtcod_prod.setText(Datos_Producto.getCod_Prod());
						
						JLBdescripcion.setText(descripcion);
						txtultimo_costo.setText(ultimo_costo+"");
						txtcosto_promedio.setText(costo_promedio+"");
						
						
//						txtPrecioVenta.setText(venta_total+"");
//						while(tabla_Proveedor.getRowCount()>0){
//							modelo_prv.removeRow(0); }
//						 Llenar_Tabla_proveedores ();
//						 render_proveedor();
						 
						 codigo_producto=txtcod_prod.getText().trim().toUpperCase()+"";
						 
					}else{
						JOptionPane.showMessageDialog(null, "El Codigo Esta Mal Escrito o El Producto No Existe" , "Aviso", JOptionPane.CANCEL_OPTION);
					
                    }
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Error en Cat_Cotizaciones_De_Un_Producto_En_Proveedores  en la funcion existe_Producto \n SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
			}
		}
	};

public void render_proveedor(){
			//		tabla.getColumnModel().getColumn(# columna).setCellRenderer(new CellRenderer("tipo_de_valor","alineacion","tipo_de_letra","negrita",# tamanio_fuente));
	
					this.tabla_captura.getTableHeader().setReorderingAllowed(false) ;
					this.tabla_captura.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
						
					tabla_captura.getColumnModel().getColumn(0).setMaxWidth(165);
					tabla_captura.getColumnModel().getColumn(0).setMinWidth(165);
					tabla_captura.getColumnModel().getColumn(1).setMaxWidth(70);
					tabla_captura.getColumnModel().getColumn(1).setMinWidth(70);
		
					tabla_captura.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
					tabla_captura.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12));
	
					tabla_Proveedor.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",10)); 
					tabla_Proveedor.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
					tabla_Proveedor.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
					tabla_Proveedor.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("fecha","izquierda","Arial","normal",12));
					tabla_Proveedor.getColumnModel().getColumn(4).setCellRenderer(new tablaRenderer("texto","centro","Arial","normal",12));
					tabla_Proveedor.getColumnModel().getColumn(5).setCellRenderer(new tablaRenderer("texto","centro","Arial","normal",12));
					tabla_Proveedor.getColumnModel().getColumn(6).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12));
					tabla_Proveedor.getColumnModel().getColumn(7).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12));
					tabla_Proveedor.getColumnModel().getColumn(8).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12));
					tabla_Proveedor.getColumnModel().getColumn(9).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12));
					tabla_Proveedor.getColumnModel().getColumn(10).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12));
					tabla_Proveedor.getColumnModel().getColumn(11).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12));
					tabla_Proveedor.getColumnModel().getColumn(12).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12));
					tabla_Proveedor.getColumnModel().getColumn(13).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",10));
					tabla_Proveedor.getColumnModel().getColumn(14).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",10));
				}
				
				
		private JScrollPane Tabla_Proveedor()	{		
		
			    this.tabla_Proveedor.getTableHeader().setReorderingAllowed(false) ;
				this.tabla_Proveedor.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
					
				tabla_Proveedor.getColumnModel().getColumn(0).setMaxWidth(65);
				tabla_Proveedor.getColumnModel().getColumn(0).setMinWidth(65);
				tabla_Proveedor.getColumnModel().getColumn(1).setMaxWidth(65);
				tabla_Proveedor.getColumnModel().getColumn(1).setMinWidth(65);
				tabla_Proveedor.getColumnModel().getColumn(2).setMaxWidth(300);
				tabla_Proveedor.getColumnModel().getColumn(2).setMinWidth(300);
				tabla_Proveedor.getColumnModel().getColumn(3).setMaxWidth(80);
				tabla_Proveedor.getColumnModel().getColumn(3).setMinWidth(80);
				tabla_Proveedor.getColumnModel().getColumn(4).setMaxWidth(35);
				tabla_Proveedor.getColumnModel().getColumn(4).setMinWidth(35);
				tabla_Proveedor.getColumnModel().getColumn(5).setMaxWidth(35);
				tabla_Proveedor.getColumnModel().getColumn(5).setMinWidth(35);
				tabla_Proveedor.getColumnModel().getColumn(6).setMaxWidth(70);
				tabla_Proveedor.getColumnModel().getColumn(6).setMinWidth(60);
				tabla_Proveedor.getColumnModel().getColumn(7).setMaxWidth(70);
				tabla_Proveedor.getColumnModel().getColumn(7).setMinWidth(60);
				tabla_Proveedor.getColumnModel().getColumn(8).setMaxWidth(70);
				tabla_Proveedor.getColumnModel().getColumn(8).setMinWidth(60);
				tabla_Proveedor.getColumnModel().getColumn(9).setMaxWidth(70);
				tabla_Proveedor.getColumnModel().getColumn(9).setMinWidth(60);
				tabla_Proveedor.getColumnModel().getColumn(10).setMaxWidth(70);
				tabla_Proveedor.getColumnModel().getColumn(10).setMinWidth(60);
				tabla_Proveedor.getColumnModel().getColumn(11).setMaxWidth(70);
				tabla_Proveedor.getColumnModel().getColumn(11).setMinWidth(60);
				tabla_Proveedor.getColumnModel().getColumn(12).setMaxWidth(70);
				tabla_Proveedor.getColumnModel().getColumn(12).setMinWidth(60);
				tabla_Proveedor.getColumnModel().getColumn(13).setMaxWidth(500);
				tabla_Proveedor.getColumnModel().getColumn(13).setMinWidth(90);
				tabla_Proveedor.getColumnModel().getColumn(14).setMaxWidth(250);
				tabla_Proveedor.getColumnModel().getColumn(14).setMinWidth(90);
			
					
					 JScrollPane scrol = new JScrollPane(tabla_Proveedor);
				    return scrol; 
				}
				
//				public void Llenar_Tabla_proveedores (){
//					Statement s;
//					ResultSet rs;
//					
//					try {
//						
//					/////ORIGEN COMPRAS
//						s = con.conexion_IZAGAR().createStatement();
//						String cod_producto=txtcod_prod.getText().trim().toUpperCase()+"";
//						String fecha_busqueda =new SimpleDateFormat("dd/MM/yyyy").format(cfecha.getDate());
//						
//						rs = s.executeQuery("DECLARE @cod_prod varchar(35) set @cod_prod='"+cod_producto+"'" +
//								" 	SELECT entysal.folio" +
//								" 		  ,proveedores.cod_prv" +
//								" 		  ,proveedores.razon_social as proveedor" +
//								"   	  ,convert(varchar(15),entysal.fecha,103) as fecha_compra" +
//								" 		  ,entysal.unidad" +
//								"		  ,convert(numeric(10,2),productos.contenido) as contenido" +
//								"		  ,convert(numeric(10,2),entysal.cantidad) as cantidad" +
//								"		  ,convert(numeric(10,2),entysal.costo) as costo" +
//								"		  ,convert(numeric(10,2),round((entysal.importe/entysal.cantidad),2)) as costo_pz" +
//								"   	  ,0 AS ultimo_costo" +
//								"		  ,0 as costo_promedio" +
//								"   	  ,0 as exist_cedis" +
//								"         ,0 as exist_total" +
//								"		  ,'' as notas" +
//								"         ,'' as cotizo "+
//								"         ,entysal.fecha as fecha_order_by "+    
//								"   FROM entysal with(nolock)  " +
//								"	     inner join productos on productos.cod_prod=entysal.cod_prod" +
//								"	     inner join proveedores on proveedores.cod_prv=entysal.cod_prv" +
//								" 	 WHERE entysal.transaccion='44'  and entysal.cod_prod=@cod_prod and entysal.fecha>'"+fecha_busqueda+"'   " +
//								"     UNION ALL" +
//								"  SELECT folio_compra as folio" +
//								" 		  ,cod_prv" +
//								"		  ,proveedor" +
//								"		  ,convert(varchar(15),fecha_de_cotizacion,103) as fecha_compra" +
//								"		  ,'PZ' as unidad" +
//								"		  ,1 as contenido" +
//								"		  ,cantidad_requerida as cantidad" +
//								"		  ,convert(numeric(30,2),cantidad_requerida*costo_nuevo) as costo" +
//								"		  ,convert(numeric(10,2),costo_nuevo) as costo_pz" +
//								"		  ,convert(numeric(10,2),ultimo_costo) as ultimo_costo" +
//								"		  ,convert(numeric(10,2),costo_promedio) as costo_promedio" +
//								"		  ,exist_cedis" +
//								"		  ,exist_total" +
//								"		  ,notas" +
//								"         ,folio_empleado_cotizo"+
//								"         ,fecha_de_cotizacion as fecha_order_by "+
//								"   FROM IZAGAR_captura_cotizaciones_de_un_producto_en_proveedores as cotizacion" +
//								"	 WHERE cod_prod=@cod_prod and fecha_de_cotizacion>'"+fecha_busqueda+"' order by fecha_order_by desc")   ;
//						
//						
//						
//						while (rs.next())
//						{ 
//						   Object [] fila = new Object[15];
//						   fila[0] = rs.getString(1).trim();
//						   fila[1] = rs.getString(2).trim();
//						   fila[2] = rs.getString(3).trim(); 
//						   fila[3] = rs.getString(4).trim(); 
//						   fila[4] = rs.getString(5).trim(); 
//						   fila[5] = rs.getString(6).trim(); 
//						   fila[6] = rs.getString(7).trim();
//						   fila[7] = rs.getString(8).trim();
//						   fila[8] = rs.getString(9).trim();
//						   fila[9] = rs.getString(10).trim();
//						   fila[10] = rs.getString(11).trim();
//						   fila[11] = rs.getString(12).trim();
//						   fila[12] = rs.getString(13).trim();
//						   fila[13] = rs.getString(14).trim();
//						   fila[14] = rs.getString(15).trim();
//						   
//						   
//						   modelo_prv.addRow(fila); 
//						}	
//
//					} catch (SQLException e1) {
//						e1.printStackTrace();
//						JOptionPane.showMessageDialog(null, "Error en Cat_Cotizaciones_De_Un_Producto_En_Proveedores en la funcion Llenar_Tabla_proveedores  SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
//					}
//}
	
		
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Costos_Competencia("").setVisible(true);
		}catch(Exception e){	}
	}
}
