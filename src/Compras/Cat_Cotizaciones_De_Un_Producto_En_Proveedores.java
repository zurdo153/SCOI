package Compras;

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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;


import com.toedter.calendar.JDateChooser;


import Conexiones_SQL.Connexion;
import Obj_Compras.Obj_Cotizaciones_De_Un_Producto;
import Obj_Principal.Componentes;

@SuppressWarnings("serial")
public class Cat_Cotizaciones_De_Un_Producto_En_Proveedores extends JFrame{
				Container cont = getContentPane();
				JLayeredPane panel = new JLayeredPane();
				
				JTextField txtcod_prod = new Componentes().text(new JTextField(), "Codigo del Producto", 15, "String");
				JButton btnBuscar_Producto = new JButton("",new ImageIcon("imagen/Filter-List-icon16.png"));
				
				JLabel Marcoproveedor1= new JLabel();
				JLabel JLBdescripcion= new JLabel();
				JLabel JLBultimo_costo= new JLabel();
				JLabel JLBcosto_promedio= new JLabel();
				JLabel JLBexist_cedis= new JLabel();
				JLabel JLBexist_total= new JLabel();
				
				JTextField txtProveedor1 = new Componentes().text(new JTextField(), "Nombre Del Proveedor",150, "String");
				JTextField txtFoliocompra1 = new Componentes().text(new JTextField(), "Folio De La Compra",50, "String");
				JTextField txtUlt_Fecha_Comp_prov1 = new Componentes().text(new JTextField(), "Fecha De La Compra",50, "String");
				JTextField txtUlt_Costo_prov1 = new Componentes().text(new JTextField(), "Ultimo Costo De La Compra",50, "String");
				JTextField txtUlt_Compra_prov1 = new Componentes().text(new JTextField(), "Cantidad De La Compra",50, "String");
				JTextField txtCosto_Nuevo_prov1 = new Componentes().text(new JTextField(), "Costo Nuevo Del Proveedor",50, "String");
				JTextField txtCondicion_Cant_compra_prov1 = new Componentes().text(new JTextField(), "Cantidad Minima de Compra Para Respetar Costo",150, "String");
			
				
				JDateChooser cfecha = new JDateChooser();
				Connexion con = new Connexion();
				
				DefaultTableModel modelo_prv = new DefaultTableModel(null,
			            new String[]{"Compra","Cod.Prv", "Proveedor","Fecha Compra","Unid","Cont","Cantidad","Costo","Importe","Costo PZ","CostoNvo","Cantidad","Nota de La Negociacion"}
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
			        	 } 				
			 			return false;
			 		}
				};
			    JTable tabla_Proveedor = new JTable(modelo_prv);
			    JScrollPane scrollAsignado = new JScrollPane(tabla_Proveedor);
			    
				String codigo_producto = "";
				Border blackline, etched, raisedbevel, loweredbevel, empty;
	
	          String Nombre_Catalogo_Para_Filtro="";
	  		  String descripcion="";
			  double ultimo_costo=0;
			  double costo_promedio=0;
			  double exist_cedis=0;
			  double exist_total=0;
	public Cat_Cotizaciones_De_Un_Producto_En_Proveedores(String cod_prod){
		codigo_producto=cod_prod+"";
		txtcod_prod.setText(codigo_producto+"");

		setSize(1024,768);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Cotizaciones De Un Producto En Proveedores");
		setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Filter-List-icon16.png"));
		blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
		panel.setBorder(BorderFactory.createTitledBorder(blackline,"Seleccione el Tipo de Reporte"));
		
	
		int x=10 ;
		int y=20 ;
		int a=20;
		int l=100;
		
		panel.add(txtcod_prod).setBounds(x,y,l,a);
		panel.add(btnBuscar_Producto).setBounds(l+=x,y,a,a);
		
		panel.add(JLBdescripcion).setBounds(l+x+20,y,l+700,a);
		panel.add(cfecha).setBounds(x,y+=25,l,a);
		panel.add(new JLabel("Ultimo Costo:")).setBounds(x+=l+20,y,l-40,a);
		panel.add(JLBultimo_costo).setBounds(x+=75,y,l-20,a);
		panel.add(new JLabel("Costo Promedio:")).setBounds(x+=l-20,y,l-30,a);
		panel.add(JLBcosto_promedio).setBounds(x+=85,y,l-20,a);
		panel.add(new JLabel("Existencia Cedis:")).setBounds(x+=l-20,y,l-20,a);
		panel.add(JLBexist_cedis).setBounds(x+=85,y,l-20,a);
		panel.add(new JLabel("Existencia Total:")).setBounds(x+=l-20,y,l-20,a);
		panel.add(JLBexist_total).setBounds(x+=85,y,l-20,a);
		panel.add(Tabla_Proveedor()).setBounds(10,200,1000,400);
		
	    cfecha.setEnabled(true);
	    
		cont.add(panel);
		
		
		Nombre_Catalogo_Para_Filtro=this.getClass().getSimpleName();
		btnBuscar_Producto.addActionListener(opBuscar_Producto);
		
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
							 Llenar_Tabla_proveedores ();
	   		
	   	  	            	}
	          }
	   	}
	 };
	
	
	ActionListener opBuscar_Producto = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			dispose();
			new Cat_Filtro_De_Busqueda_De_Productos(Nombre_Catalogo_Para_Filtro).setVisible(true);
		}
	};
	
	KeyListener Buscar_Datos_Producto = new KeyListener() {
		public void keyTyped(KeyEvent e){}
		public void keyReleased(KeyEvent e) {}
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				
				try {
					
					;
					if(new Obj_Cotizaciones_De_Un_Producto().Existe_Producto(txtcod_prod.getText().trim().toUpperCase()+"")){
						
				      Obj_Cotizaciones_De_Un_Producto  Datos_Producto= new Obj_Cotizaciones_De_Un_Producto().buscardatos_producto(txtcod_prod.getText().trim().toUpperCase()+"");
			            
						descripcion=Datos_Producto.getDescripcion_Prod();
						ultimo_costo=Datos_Producto.getUltimo_Costo();
						costo_promedio=Datos_Producto.getCosto_Promedio();
						exist_cedis=Datos_Producto.getExistencia_Cedis();
						exist_total=Datos_Producto.getExistencia_Total();
						txtcod_prod.setText(Datos_Producto.getCod_Prod());
						
						
						JLBdescripcion.setText("<html> <FONT FACE="+"arial"+" SIZE=5 COLOR=BLACk><CENTER><p>"+descripcion+"</p></CENTER></FONT></html>");
						JLBultimo_costo.setText("<html> <FONT FACE="+"arial"+" SIZE=4 COLOR=BLACk><p>"+ultimo_costo+"</p></FONT></html>");
						JLBcosto_promedio.setText("<html> <FONT FACE="+"arial"+" SIZE=4 COLOR=BLACk><p>"+costo_promedio+"</p></FONT></html>");
						JLBexist_cedis.setText("<html> <FONT FACE="+"arial"+" SIZE=4 COLOR=BLACk><p>"+exist_cedis+"</p></FONT></html>");
						JLBexist_total.setText("<html> <FONT FACE="+"arial"+" SIZE=4 COLOR=BLACk><p>"+exist_total+"</p></FONT></html>");
						
						while(tabla_Proveedor.getRowCount()>0){
							modelo_prv.removeRow(0);  }
						 Llenar_Tabla_proveedores ();
						 
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

	
	
	private JScrollPane Tabla_Proveedor()	{		
		
		this.tabla_Proveedor.getTableHeader().setReorderingAllowed(false) ;
		this.tabla_Proveedor.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
	tabla_Proveedor.getColumnModel().getColumn(0).setMaxWidth(50);
	tabla_Proveedor.getColumnModel().getColumn(0).setMinWidth(50);
	tabla_Proveedor.getColumnModel().getColumn(1).setMaxWidth(50);
	tabla_Proveedor.getColumnModel().getColumn(1).setMinWidth(50);
	tabla_Proveedor.getColumnModel().getColumn(2).setMaxWidth(300);
	tabla_Proveedor.getColumnModel().getColumn(2).setMinWidth(300);
	tabla_Proveedor.getColumnModel().getColumn(3).setMaxWidth(65);
	tabla_Proveedor.getColumnModel().getColumn(3).setMinWidth(65);
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
	tabla_Proveedor.getColumnModel().getColumn(12).setMaxWidth(500);
	tabla_Proveedor.getColumnModel().getColumn(12).setMinWidth(90);

		
		 JScrollPane scrol = new JScrollPane(tabla_Proveedor);
	    return scrol; 
	}
	public void Llenar_Tabla_proveedores (){
		Statement s;
		ResultSet rs;
		String cod_producto=txtcod_prod.getText().trim().toUpperCase()+"";
		
		try {
			s = con.conexion_IZAGAR().createStatement();
			String fecha_busqueda =new SimpleDateFormat("dd/MM/yyyy").format(cfecha.getDate());
			
			rs = s.executeQuery("declare @cod_prod varchar(35) set @cod_prod='"+cod_producto+"' "+
					"    select entysal.folio  ,proveedores.cod_prv ,proveedores.razon_social as proveedor" +
					"                   ,convert(varchar(15),entysal.fecha,103) as fecha_compra ,entysal.unidad" +
					"                   ,convert(numeric(10,2),productos.contenido) as contenido ,convert(numeric(10,2),entysal.cantidad) as cantidad " +
					"                   ,convert(numeric(10,2),entysal.costo) as costo    ,convert(numeric(10,2),entysal.importe) as importe " +
					"                   ,convert(numeric(10,2),round((entysal.importe/entysal.cantidad),2)) as costo_unitario" +
					"              from entysal with(nolock) " +
					"     inner join productos on productos.cod_prod=entysal.cod_prod " +
					"     inner join proveedores on proveedores.cod_prv=entysal.cod_prv " +
					"      where entysal.transaccion='44'  and entysal.cod_prod=@cod_prod and entysal.fecha>'"+fecha_busqueda+" 00:00:00' " +
					" order by  entysal.importe/entysal.cantidad asc,fecha desc")   ;
		
			while (rs.next())
			{ 
			   Object [] fila = new Object[10];
			   fila[0] = rs.getString(1).trim();
			   fila[1] = rs.getString(2).trim();
			   fila[2] = rs.getString(3).trim(); 
			   fila[3] = rs.getString(4).trim(); 
			   fila[4] = rs.getString(5).trim(); 
			   fila[5] = rs.getString(6).trim(); 
			   fila[6] = rs.getString(7).trim();
			   fila[7] = rs.getString(8).trim();
			   fila[8] = rs.getString(9).trim();
			   fila[9] = rs.getString(10).trim();
			   modelo_prv.addRow(fila); 
			}	
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en Cat_Control_De_Facturas_Y_XML_De_Proveedores en la funcion getPaneltabla_Proveedor  SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
	}
	
//	ActionListener opGenerar = new ActionListener() {
//		@SuppressWarnings({ "rawtypes", "unchecked" })
//		public void actionPerformed(ActionEvent e) {
//			
//			if(tipo_Reporte==1){
//						if(!txtcod_prod.getText().equals("")){
//														
//							String query = "exec sp_Reporte_De_Fuente_De_Sodas_De_Lista_De_Raya_Por_Folio "+Integer.valueOf(txtcod_prod.getText())  ;
//							Statement stmt = null;
//								try {
//									stmt =  new Connexion().conexion().createStatement();
//								    ResultSet rs = stmt.executeQuery(query);
//									JasperReport report = JasperCompileManager.compileReport(System.getProperty("user.dir")+"\\src\\Obj_Reportes\\Obj_Reporte_De_Fuente_De_Sodas.jrxml");
//									JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
//									JasperPrint print = JasperFillManager.fillReport(report, new HashMap(), resultSetDataSource);
//									JasperViewer.viewReport(print, false);
//									
//								} catch (Exception e2) {
//									System.out.println(e2.getMessage());
//									JOptionPane.showMessageDialog(null, "Error en Generar Reporte de Fuente de Sodas de Lista del Folio procedure sp_Reporte_De_Fuente_De_Sodas_De_Lista_De_Raya_Por_Folio SQLException: \n "+e2.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
//
//								}
//								return;	
//						}else{
//							JOptionPane.showMessageDialog(null,"Debes de Teclear Un Folio de Lista de Raya \n O Seleccionarla de la Lista  Siguiente ","Aviso!", JOptionPane.WARNING_MESSAGE);
//							 new Cat_Filtro_De_Listas_De_Raya_Pasadas(1).setVisible(true);
//							return;		
//					    }
//			}else{
//				 String fechaNull = cfecha.getDate()+"";
//				   if(fechaNull.equals("null")){
//						JOptionPane.showMessageDialog(null,"Necesita Selecionar una Fecha o la Fecha tecleada es Incorrecta","Mensaje",JOptionPane.WARNING_MESSAGE);
//						return;
//					   }else{
//						   
//						   String fecha = new SimpleDateFormat("dd/MM/yyyy").format(cfecha.getDate());
//						   String query = "exec sp_Reporte_De_Fuente_De_Sodas_Sin_Cobro_En_lista_De_Raya '"+fecha+"'" ;
//							Statement stmt = null;
//							
//							try {
//								stmt =  new Connexion().conexion().createStatement();
//							    ResultSet rs = stmt.executeQuery(query);
//								JasperReport report = JasperCompileManager.compileReport(System.getProperty("user.dir")+"\\src\\Obj_Reportes\\Obj_Reporte_De_Fuente_De_Sodas_de_Empleados_Sin_Cobro_En_Listas_de_Raya.jrxml");
//								JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
//								JasperPrint print = JasperFillManager.fillReport(report, new HashMap(), resultSetDataSource);
//								JasperViewer.viewReport(print, false);
//							} catch (Exception e1) {
//								System.out.println(e1.getMessage());
//								JOptionPane.showMessageDialog(null, "Error en Generar Reporte de Fuente de Sodas Sin Cobro En Lista de Raya procedure sp_Reporte_De_Fuente_De_Sodas_Sin_Cobro_En_lista_De_Raya SQLException: \n "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
//							}
//							return;					   
//					   }
//			}
//		}
//	};
	
	
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Cotizaciones_De_Un_Producto_En_Proveedores("").setVisible(true);
		}catch(Exception e){	}
	}
}
