package Cat_Compras;

import java.awt.Container;
import java.awt.Event;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
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

import Cat_Filtros_IZAGAR.Cat_Filtro_De_Busqueda_De_Productos;
import Conexiones_SQL.Connexion;
import Obj_Compras.Obj_Cotizaciones_De_Un_Producto;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.Obj_tabla;

@SuppressWarnings("serial")
public class Cat_Alimentacion_De_Precios_De_Competencia extends JFrame {
				Container cont = getContentPane();
				JLayeredPane panel = new JLayeredPane();
				
				JTextField txtcod_prod = new Componentes().text(new JTextField(), "Codigo del Producto", 15, "String");
				JButton btnBuscar_Producto = new JButton("",new ImageIcon("imagen/Filter-List-icon16.png"));
				
				JCButton btnGuardar        = new JCButton("Guardar"                          ,"Guardar.png"                                                   ,"Azul");
				JCButton btnDeshacer       = new JCButton("Deshacer"                         ,"deshacer16.png"                                                   ,"Azul");
				
				JLabel Marcoproveedor1= new JLabel();
				JLabel JLBdescripcion= new JLabel();
				JTextField txtultimo_costo= new JTextField();
				JTextField txtcosto_promedio= new JTextField();
				JTextField txtPrecioVenta= new JTextField();
				JTextField txtPrecioNormal= new JTextField();
				
				JDateChooser cfecha  = new Componentes().jchooser(new JDateChooser()  ,"Fecha"  ,0);
				Connexion con = new Connexion();
				
				Obj_tabla ObjTab =new Obj_tabla();
				
				int columnasb = 4,checkbox=-1;
				public void init_tabla(){
					this.tabla_captura.getColumnModel().getColumn(0).setMaxWidth(40);
					this.tabla_captura.getColumnModel().getColumn(0).setMinWidth(40);
					this.tabla_captura.getColumnModel().getColumn(1).setMinWidth(150);
					this.tabla_captura.getColumnModel().getColumn(2).setMinWidth(120);
					this.tabla_captura.getColumnModel().getColumn(3).setMinWidth(325);
					String basedatos="98",pintar="si";
					String comandob="select folio_competencia, competencia,'' as costo, '' as observaciones  from tb_competencias where status = 1 order by folio_competencia asc ";
					ObjTab.Obj_Refrescar(tabla_captura,modelo_captura, columnasb, comandob, basedatos,pintar,checkbox);
			    }
				
					DefaultTableModel modelo_captura = new DefaultTableModel(null, new String[]{"Folio", "Competencia","Costo","Observaciones"}){
					     @SuppressWarnings("rawtypes")
						Class[] types = new Class[]{
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
				        	 	case 2 : return true; 
				        	 	case 3 : return true; 
				        	 } 				
				 			return false;
				 		}
						
					};
				
				JTable tabla_captura = new JTable(modelo_captura);
			    JScrollPane scrollcaptura = new JScrollPane(tabla_captura);
			    
			    public void init_tabla_proveedores(){
					this.tabla_Proveedor.getColumnModel().getColumn(0).setMaxWidth(60);
					this.tabla_Proveedor.getColumnModel().getColumn(0).setMinWidth(60);
					this.tabla_Proveedor.getColumnModel().getColumn(1).setMinWidth(300);
					this.tabla_Proveedor.getColumnModel().getColumn(2).setMinWidth(80);
					this.tabla_Proveedor.getColumnModel().getColumn(3).setMinWidth(90);
					this.tabla_Proveedor.getColumnModel().getColumn(4).setMinWidth(70);
					this.tabla_Proveedor.getColumnModel().getColumn(5).setMinWidth(70);
					this.tabla_Proveedor.getColumnModel().getColumn(6).setMinWidth(70);
					this.tabla_Proveedor.getColumnModel().getColumn(7).setMinWidth(90);
					this.tabla_Proveedor.getColumnModel().getColumn(8).setMinWidth(320);
					this.tabla_Proveedor.getColumnModel().getColumn(9).setMinWidth(120);
					this.tabla_Proveedor.getColumnModel().getColumn(10).setMinWidth(120);
					
					String basedatos="98",pintar="si";
					String comandob="precios_de_competencia_busqueda_ultimos_capturados '01/01/2018' ";
					ObjTab.Obj_Refrescar(tabla_Proveedor,modelo_prv, 11, comandob, basedatos,pintar,checkbox);
			    }
			    
				DefaultTableModel modelo_prv = new DefaultTableModel(null,
			            new String[]{"Cod.Prod","Descripcion", "Ultimo Costo","Costo Promedio","P.Normal","P.Venta","Fol. Comp","P.Venta Comp","Realizo Captura","Fecha Captura","Observaciones"}
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
			        	 	case 10: return false;
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
			  double precio_normal=0;
			  
	public Cat_Alimentacion_De_Precios_De_Competencia(String cod_prod){
		codigo_producto=cod_prod+"";
		txtcod_prod.setText(codigo_producto+"");
		setSize(1024,635);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Alimentacion De Precios De Competencia");
		setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/estrategiadeprecios64.png"));
		blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
		panel.setBorder(BorderFactory.createTitledBorder(blackline,"Seleccione o teclee un producto"));
		cfecha.setIcon(new ImageIcon("Iconos/calendar_icon&16.png"));

		int x=10, y=20, a=20, l=100;
		
		panel.add(new JLabel("Fecha:")).setBounds(x,y,l-40,a);
		panel.add(cfecha).setBounds(x+85,y,l+10,a);
		
		panel.add(scrollcaptura).setBounds(x+345,y,655,140);
		
		panel.add(new JLabel("Producto:")).setBounds(x,y+=25,l-40,a);
		panel.add(txtcod_prod).setBounds(x+85,y,l,a);
		panel.add(btnBuscar_Producto).setBounds((l+=x)+85,y,a,a);
		
		panel.add(JLBdescripcion).setBounds(x,y+=25,l+340,a);
		
		panel.add(new JLabel("Ultimo Costo:")).setBounds(x,y+=25,l-40,a);
		panel.add(txtultimo_costo).setBounds(x+85,y,l-35,a);
		
		panel.add(new JLabel("Precio De Venta:")).setBounds(x+170,y,l-20,a);
		panel.add(txtPrecioVenta).setBounds(x+255,y,l-35,a);
		
		panel.add(new JLabel("Costo Promedio:")).setBounds(x,y+=25,l-30,a);
		panel.add(txtcosto_promedio).setBounds(x+85,y,l-35,a);
		panel.add(btnDeshacer).setBounds(x+220,y,l,a);

		panel.add(new JLabel("Precio Normal:")).setBounds(x,y+=25,l-20,a);
		panel.add(txtPrecioNormal).setBounds(x+85,y,l-35,a);
		
		panel.add(btnGuardar).setBounds(x+220,y,l,a);
		panel.add(scrollAsignado).setBounds(10,170,1000,430);
		limpiar();

		cont.add(panel);

		txtcosto_promedio.setEditable(false);
		txtPrecioVenta.setEditable(false);
		txtPrecioNormal.setEditable(false);
		txtultimo_costo.setEditable(false);

		Nombre_Catalogo_Para_Filtro=this.getClass().getSimpleName();
		
		btnBuscar_Producto.addActionListener(opBuscar_Producto);
		btnDeshacer.addActionListener(deshacer);
		btnGuardar.addActionListener(Guardar);
		txtcod_prod.addKeyListener(Buscar_Datos_Producto);
        tabla_captura.addKeyListener(op_validanumero_en_celda);
          
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), "filtro");
        getRootPane().getActionMap().put("filtro", new AbstractAction(){public void actionPerformed(ActionEvent e)
                 {   txtcod_prod.setText("");
               	    btnBuscar_Producto.doClick(); }
             });
		
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
        getRootPane().getActionMap().put("escape", new AbstractAction(){ public void actionPerformed(ActionEvent e) { btnDeshacer.doClick();  }   });

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_G,Event.CTRL_MASK),"guardar");
        getRootPane().getActionMap().put("guardar", new AbstractAction(){public void actionPerformed(ActionEvent e) { btnGuardar.doClick();   }   });

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0), "guardar");
        getRootPane().getActionMap().put("guardar", new AbstractAction(){public void actionPerformed(ActionEvent e) { btnGuardar.doClick();   }   });
	}
	
	KeyListener op_validanumero_en_celda = new KeyListener() {
		public void keyTyped(KeyEvent e) {}
		public void keyReleased(KeyEvent e) {
			int fila=tabla_captura.getSelectedRow();
			int columna=2; 
			if(fila==-1)fila=fila+1;
			
			if(ObjTab.validacelda(tabla_captura,"decimal", fila,columna)){
						  if(ObjTab.RecorridoFocotabla_con_evento(tabla_captura, fila,columna, "x",e).equals("si")){
								txtcod_prod.requestFocus();
						  };
			}	
		}
		public void keyPressed(KeyEvent e) {}
	};
	     
	ActionListener opBuscar_Producto = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			dispose();
			new Cat_Filtro_De_Busqueda_De_Productos(Nombre_Catalogo_Para_Filtro,"","",null).setVisible(true);
		}
	};
	
	public String[][] competencia(){
		String[][] comp = new String[tabla_captura.getRowCount()][3];
		for(int i=0; i<comp.length; i++){
			comp[i][0]=tabla_captura.getValueAt(i,0).toString();
			comp[i][1]=tabla_captura.getValueAt(i,2).toString();
			comp[i][2]=tabla_captura.getValueAt(i,3).toString();
		}
		return comp;
	}
	
	ActionListener Guardar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			
                Obj_Cotizaciones_De_Un_Producto cotizacion_prod = new Obj_Cotizaciones_De_Un_Producto();					
				
                cotizacion_prod.setFecha(new SimpleDateFormat("dd/MM/yyyy").format(cfecha.getDate()));
                cotizacion_prod.setCod_Prod(codigo_producto);
                cotizacion_prod.setUltimo_Costo(ultimo_costo);
                cotizacion_prod.setCosto_Promedio(costo_promedio);
                cotizacion_prod.setPrecio_de_venta_normal(precio_normal);
                cotizacion_prod.setPrecio_de_venta(precio_de_venta);
                cotizacion_prod.setDescripcion_Prod(JLBdescripcion.getText().toLowerCase().toString().trim());
                
                if(tabla_captura.isEditing()){
    				tabla_captura.getCellEditor().stopCellEditing();
    			}
                
               if(cotizacion_prod.Guardar_Captura_competencia(competencia())){
            	     limpiar();
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
		 modelo_prv.setRowCount(0);
		 init_tabla();
		 init_tabla_proveedores();
		 txtcod_prod.setText("");
		 txtcosto_promedio.setText("");
		 txtPrecioVenta.setText("");
		 txtPrecioNormal.setText("");
		 txtultimo_costo.setText("");
		 JLBdescripcion.setText("");
		 txtcod_prod.requestFocus();
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
			            
				  	     for(int i=0; i<tabla_captura.getRowCount(); i++){
				  				modelo_captura.setValueAt("", i, 2);
				  			}
				  	     
						descripcion=Datos_Producto.getDescripcion_Prod();
						ultimo_costo=Datos_Producto.getUltimo_Costo();
						costo_promedio=Datos_Producto.getCosto_Promedio();
						precio_de_venta = Datos_Producto.getPrecio_de_venta();
						precio_normal = Datos_Producto.getPrecio_de_venta_normal();
						
						txtcod_prod.setText(Datos_Producto.getCod_Prod());
						
						JLBdescripcion.setText(descripcion);
						txtultimo_costo.setText(ultimo_costo+"");
						txtcosto_promedio.setText(costo_promedio+"");
						txtPrecioVenta.setText(precio_de_venta+"");
						txtPrecioNormal.setText(precio_normal+"");
						
						 codigo_producto=txtcod_prod.getText().trim().toUpperCase()+"";
						 
						 txtcod_prod.setEditable(false);
						 btnBuscar_Producto.setEnabled(false);
						 tabla_captura.requestFocus();
						 
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
				
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Alimentacion_De_Precios_De_Competencia("").setVisible(true);
		}catch(Exception e){	}
	}
}
