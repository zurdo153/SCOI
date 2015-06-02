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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.toedter.calendar.JDateChooser;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.BuscarTablasModel;
import Conexiones_SQL.Connexion;
import Obj_Compras.Obj_Cotizaciones_De_Un_Producto;
import Obj_Principal.Componentes;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Alimentacion_De_Costos_De_Competencia extends JFrame implements TableModelListener{
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
				
				JDateChooser cfecha = new JDateChooser();
				Connexion con = new Connexion();
				
				public static Object[][] get_tabla(){return new BuscarTablasModel().tabla_model_competencia();}
				DefaultTableModel modelo_captura = new DefaultTableModel(get_tabla(), new String[]{"Folio","Competencia", "Costo"}){
				     @SuppressWarnings("rawtypes")
					Class[] types = new Class[]{
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
			        	 	case 2 : return true;
			        	 	} 				
			 			return false;
			 		}
				};
				
				JTable tabla_captura = new JTable(modelo_captura);
				JScrollPane scrollcaptura = new JScrollPane(tabla_captura,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				
				
				DefaultTableModel modelo_prv = new DefaultTableModel(null,
			            new String[]{"Cod.Prod","Descripcion", "Ultimo Costo","Costo Promedio","P.Venta","Fol. Comp","P.Venta Comp","Realizo Captura","Fecha Captura"}
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
			  double precio_de_venta=0;
			  
				@SuppressWarnings("unused")
				public void tableChanged(TableModelEvent e) {
			        int row = e.getFirstRow();
			        int column = e.getColumn();
			        TableModel model = (TableModel)e.getSource();
			        String columnName = model.getColumnName(column);
			        String data = model.getValueAt(row, column).toString();

			        if(column==2){
			        	 try{
			 	        	if(!data.equals("")){
			 	        		Float.valueOf(data);
			 	        	}
			 	        } catch (NumberFormatException nfe){
			 	        	tabla_captura.setValueAt("", row, column);
			 	        	System.out.println("no es entero");
			 	        }
			        }
			       
			    }
			  
	public Cat_Alimentacion_De_Costos_De_Competencia(String cod_prod){
		
		codigo_producto=cod_prod+"";
		txtcod_prod.setText(codigo_producto+"");
		
		tabla_captura.getModel().addTableModelListener(this);

		setSize(1024,635);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Alimentacion De Costos De Competencia");
		setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/estrategiadeprecios64.png"));
		blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
		panel.setBorder(BorderFactory.createTitledBorder(blackline,"Seleccione o teclee un producto"));

		int x=10 ;
		int y=20 ;
		int a=20;
		int l=100;
		
		panel.add(new JLabel("Fecha:")).setBounds(x,y,l-40,a);
		panel.add(cfecha).setBounds(x+85,y,l+10,a);
		
		panel.add(scrollcaptura).setBounds(x+385,y,300,140);
		
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
		
	    render_proveedor();
	    Llenar_Tabla_proveedores();
	    
		cont.add(panel);

		txtcosto_promedio.setEditable(false);
		txtPrecioVenta.setEditable(false);
		txtultimo_costo.setEditable(false);
		
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
          
		try {
			String fecha = new BuscarSQL().fecha(0);
			Date Fecha = new SimpleDateFormat("dd/MM/yyyy").parse(fecha);
			cfecha.setDate(Fecha);
		} catch (SQLException | ParseException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
          
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
	     
	ActionListener opBuscar_Producto = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			dispose();
			new Cat_Filtro_De_Busqueda_De_Productos(Nombre_Catalogo_Para_Filtro,"").setVisible(true);
		}
	};
	
	@SuppressWarnings("unused")
	private boolean Validar(int fila, int columna) { 
			String valor=""; 
			double numero =0;
			
			if(tabla_captura.getValueAt(fila,columna).toString().equals("")) {
				numero =0;
				return true; 
			}else{ 
				
				try{
						numero = Double.valueOf(tabla_captura.getValueAt(fila, columna).toString().trim());
						return true;
				}catch(NumberFormatException e){
						return false;
				}
			} 
	}
	
	public String[][] competencia(){
		String[][] comp = new String[tabla_captura.getRowCount()][2];
		
		for(int i=0; i<comp.length; i++){
			System.out.println(tabla_captura.getValueAt(i,0).toString());
			System.out.println(tabla_captura.getValueAt(i,2).toString());
			comp[i][0]=tabla_captura.getValueAt(i,0).toString();
			comp[i][1]=tabla_captura.getValueAt(i,2).toString();
		}
		return comp;
	}
	
	ActionListener Guardar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			
                Obj_Cotizaciones_De_Un_Producto cotizacion_prod = new Obj_Cotizaciones_De_Un_Producto();					
				
                cotizacion_prod.setCod_Prod(codigo_producto);
                cotizacion_prod.setUltimo_Costo(ultimo_costo);
                cotizacion_prod.setCosto_Promedio(costo_promedio);
                
                cotizacion_prod.setPrecio_de_venta(precio_de_venta);
                cotizacion_prod.setDescripcion_Prod(JLBdescripcion.getText().toLowerCase().toString().trim());
                
               if(cotizacion_prod.Guardar_Captura_competencia(competencia())){
            	   limpiar();
            	   
            	   while(tabla_Proveedor.getRowCount()>0){
						modelo_prv.removeRow(0);  }
					 Llenar_Tabla_proveedores ();
					 render_proveedor();
            	   JOptionPane.showMessageDialog(null, "Se Guardo Correctamente:","Aviso", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen//aplicara-el-dialogo-icono-6256-32.png"));
   				return;
               }
	   			   JOptionPane.showMessageDialog(null, "Error  en la funcion [ Guardar ] \n if(Obj_Cotizaciones_De_Un_Producto.Guardar_Cotizacion()) ", "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
					return;
		}
	};
	
	ActionListener deshacer = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			limpiar();
		}
	};
	
	public void limpiar(){
		 txtcod_prod.setEditable(true);
		 btnBuscar_Producto.setEnabled(true);
		 
		 txtcod_prod.setText("");
		 txtcosto_promedio.setText("");
		 txtPrecioVenta.setText("");
		 txtultimo_costo.setText("");
		 JLBdescripcion.setText("");
	}
	
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
						precio_de_venta = Datos_Producto.getPrecio_de_venta();
						txtcod_prod.setText(Datos_Producto.getCod_Prod());
						
						JLBdescripcion.setText(descripcion);
						txtultimo_costo.setText(ultimo_costo+"");
						txtcosto_promedio.setText(costo_promedio+"");
						txtPrecioVenta.setText(precio_de_venta+"");
						
						 codigo_producto=txtcod_prod.getText().trim().toUpperCase()+"";
						 
						 txtcod_prod.setEditable(false);
						 btnBuscar_Producto.setEnabled(false);
						 
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
					
					tabla_captura.getColumnModel().getColumn(0).setMaxWidth(50);
					tabla_captura.getColumnModel().getColumn(0).setMinWidth(50);
					tabla_captura.getColumnModel().getColumn(1).setMaxWidth(165);
					tabla_captura.getColumnModel().getColumn(1).setMinWidth(165);
					tabla_captura.getColumnModel().getColumn(2).setMaxWidth(70);
					tabla_captura.getColumnModel().getColumn(2).setMinWidth(70);
		
					tabla_captura.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
					tabla_captura.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
					tabla_captura.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12));
	
					tabla_Proveedor.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
					tabla_Proveedor.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
					tabla_Proveedor.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
					tabla_Proveedor.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("fecha","izquierda","Arial","normal",12));
					tabla_Proveedor.getColumnModel().getColumn(4).setCellRenderer(new tablaRenderer("texto","centro","Arial","normal",12));
					tabla_Proveedor.getColumnModel().getColumn(5).setCellRenderer(new tablaRenderer("texto","centro","Arial","normal",12));
					tabla_Proveedor.getColumnModel().getColumn(6).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12));
					tabla_Proveedor.getColumnModel().getColumn(7).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12));
					tabla_Proveedor.getColumnModel().getColumn(8).setCellRenderer(new tablaRenderer("texto","centro","Arial","normal",12));
			
				}
				
				
		private JScrollPane Tabla_Proveedor()	{		
		
			    this.tabla_Proveedor.getTableHeader().setReorderingAllowed(false) ;
				this.tabla_Proveedor.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
					
				int x=90;
				tabla_Proveedor.getColumnModel().getColumn(0).setMaxWidth(x);
				tabla_Proveedor.getColumnModel().getColumn(0).setMinWidth(x);
				tabla_Proveedor.getColumnModel().getColumn(1).setMaxWidth(x*5);
				tabla_Proveedor.getColumnModel().getColumn(1).setMinWidth(x*3);
				tabla_Proveedor.getColumnModel().getColumn(2).setMaxWidth(x);
				tabla_Proveedor.getColumnModel().getColumn(2).setMinWidth(x);
				tabla_Proveedor.getColumnModel().getColumn(3).setMaxWidth(x);
				tabla_Proveedor.getColumnModel().getColumn(3).setMinWidth(x);
				tabla_Proveedor.getColumnModel().getColumn(4).setMaxWidth(x);
				tabla_Proveedor.getColumnModel().getColumn(4).setMinWidth(x);
				tabla_Proveedor.getColumnModel().getColumn(5).setMaxWidth(x);
				tabla_Proveedor.getColumnModel().getColumn(5).setMinWidth(x);
				tabla_Proveedor.getColumnModel().getColumn(6).setMaxWidth(x);
				tabla_Proveedor.getColumnModel().getColumn(6).setMinWidth(x);
				tabla_Proveedor.getColumnModel().getColumn(7).setMaxWidth(x*3);
				tabla_Proveedor.getColumnModel().getColumn(7).setMinWidth(x*3);
				tabla_Proveedor.getColumnModel().getColumn(8).setMaxWidth(x*2);
				tabla_Proveedor.getColumnModel().getColumn(8).setMinWidth(x*2);
					
					 JScrollPane scrol = new JScrollPane(tabla_Proveedor);
				    return scrol; 
				}
				
				public void Llenar_Tabla_proveedores (){
					Statement s;
					ResultSet rs;
					
					try {
						
					/////ORIGEN COMPRAS
						s = con.conexion().createStatement();
						
						rs = s.executeQuery(" select tb_costos_de_competencia.cod_prod"
								+ " ,tb_costos_de_competencia.descripcion "
								+ " ,tb_costos_de_competencia.ultimo_costo "
								+ " ,tb_costos_de_competencia.costo_promedio "
								+ " ,tb_costos_de_competencia.precio_de_venta "
								+ " ,tb_competencias.competencia "
								+ " ,tb_costos_de_competencia.precio_de_venta_competencia "
								+ " ,tb_empleado.nombre+' '+tb_empleado.ap_paterno+' '+tb_empleado.ap_materno as realizo_captura "
								+ " ,convert(varchar(20),tb_costos_de_competencia.fecha,103)+' '+convert(varchar(20),tb_costos_de_competencia.fecha,108) as fecha_captura"
								+ " from tb_costos_de_competencia "
								+ " inner join tb_competencias on tb_competencias.folio_competencia = tb_costos_de_competencia.folio_competencia "
								+ " inner join tb_empleado on tb_empleado.folio = tb_costos_de_competencia.folio_realizo_captura "
								+ " order by fecha desc");
						
						while (rs.next())
						{ 
						   String [] fila = new String[9];
						   fila[0] = rs.getString(1).trim();
						   fila[1] = rs.getString(2).trim();
						   fila[2] = rs.getString(3).trim(); 
						   fila[3] = rs.getString(4).trim(); 
						   fila[4] = rs.getString(5).trim(); 
						   fila[5] = rs.getString(6).trim(); 
						   fila[6] = rs.getString(7).trim();
						   fila[7] = rs.getString(8).trim();
						   fila[8] = rs.getString(9).trim();
						   
						   modelo_prv.addRow(fila); 
						}	

					} catch (SQLException e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, "Error en Cat_Costos_Competencia en la funcion Llenar_Tabla_proveedores  SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
					}
}
	
		
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Alimentacion_De_Costos_De_Competencia("").setVisible(true);
		}catch(Exception e){	}
	}
}
