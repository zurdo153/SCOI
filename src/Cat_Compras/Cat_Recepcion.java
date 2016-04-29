package Cat_Compras;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Robot;
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
import java.text.DecimalFormat;

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
import javax.swing.text.JTextComponent;

import Cat_Filtros_IZAGAR.Cat_Filtro_De_Busqueda_De_Productos;
import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.BuscarTablasModel;
import Conexiones_SQL.Connexion;
import Conexiones_SQL.GuardarSQL;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Compras.Obj_Cotizaciones_De_Un_Producto;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Recepcion extends JFrame implements TableModelListener{
				Container cont = getContentPane();
				JLayeredPane panel = new JLayeredPane();
				
				JTextField txtUsuario 					= new Componentes().text(new JTextField(), "Usuario", 15, "String");
				JTextField txtfolio_transferencia 		= new Componentes().text(new JTextField(), "Folio Establecimiento Origen", 15, "String");
				
				JTextField txtfolio_estab_origen 		= new Componentes().text(new JTextField(), "Folio Establecimiento Origen", 15, "String");
				JTextField txtestab_origen 				= new Componentes().text(new JTextField(), "Establecimiento Origen", 115, "String");
				JTextField txtfolio_estab_destino 		= new Componentes().text(new JTextField(), "Filio Establecimiento Destino", 15, "String");
				JTextField txtestablecimiento_destino 	= new Componentes().text(new JTextField(), "Establecimiento Destino", 115, "String");
				
				JTextField txtCincho					= new Componentes().text(new JTextField(), "Folio De Cincho", 15, "String");
				JTextField txtChofer				 	= new Componentes().text(new JTextField(), "Chofer", 115, "String");
				
				JTextField txtcod_prod = new Componentes().text(new JTextField(), "Codigo del Producto", 15, "String");
				JButton btnBuscar_Producto = new JButton("",new ImageIcon("imagen/Filter-List-icon16.png"));
				
				JButton btnGuardar = new JCButton("Guardar","Guardar.png","Azul");
				JButton btnDeshacer = new JCButton("Deshacer","deshacer16.png","Azul");
				JButton btnQuitar = new JCButton("Quitar","cambio-de-basura-icono-7741-16.png","Rojo");
				
				JLabel Marcoproveedor1= new JLabel();
				
				Connexion con = new Connexion();
				
				DefaultTableModel modelo = new DefaultTableModel(null,
			            new String[]{"","Cod.Prod","Descripcion", "Cantidad","Unidad","Costo Unitario","Importe","IVA","Costo Total","Importe Total"}
						){
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
				    	java.lang.Object.class
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
			        	 	case 3 : return true;
			        	 	case 4 : return false;
			        	 	case 5 : return false;
			        	 	case 6 : return false;
			        	 	case 7 : return false;
			        	 	case 8 : return false;
			        	 	case 9 : return false;
			        	 } 				
			 			return false;
			 		}
				};
			    JTable tabla = new JTable(modelo);
			    JScrollPane scroll = new JScrollPane(tabla);
			    
			    
				Border blackline, etched, raisedbevel, loweredbevel, empty;
				
	          String Nombre_Catalogo_Para_Filtro="";
			  
				@SuppressWarnings("unused")
				public void tableChanged(TableModelEvent e) {
			        int row = e.getFirstRow();
			        int column = e.getColumn();
			        TableModel model = (TableModel)e.getSource();
			        String columnName = model.getColumnName(column);
			        String data = model.getValueAt(row, column).toString();

			        if(column==3){
			        	 try{
			 	        	if(!data.equals("")){
			 	        		Float.valueOf(data);
			 	        	}
			 	        } catch (NumberFormatException nfe){
			 	        	tabla.setValueAt("", row, column);
			 	        	System.out.println("no es entero");
			 	        }
			        }
			       
			    }
			  
	public Cat_Recepcion(String folio_tranferencia,String cod_prod,String[][] productos){
		
		setSize(1024,635);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Tranferencia");
		setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/estrategiadeprecios64.png"));
		blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
		panel.setBorder(BorderFactory.createTitledBorder(blackline,"Seleccione o teclee una tranferencia"));

		int x=10 ;
		int y=20 ;
		int a=20;        		
		int l=100;      
		
		panel.add(new JLabel("Usuario:")).setBounds(x,y,l+30,a);
		panel.add(txtUsuario).setBounds(x+l+20,y,l*4,a);
		
		panel.add(new JLabel("Folio De tranferencia:")).setBounds(x,y+=25,l+30,a);
		panel.add(txtfolio_transferencia).setBounds(x+l+20,y,l+30,a);
		
		panel.add(new JLabel("Establecimiento Origen:")).setBounds(x,y+=25,l+30,a);
		panel.add(txtfolio_estab_origen).setBounds(x+l+20,y,l-40,a);
		panel.add(txtestab_origen).setBounds((l*2)-10,y,l*3+40,a);
		
		panel.add(new JLabel("Chofer:")).setBounds(x+l*5+50,y,l+30,a);
		panel.add(txtChofer).setBounds(x+l*6+20,y,l*3+40,a);
		
		panel.add(new JLabel("Establecimiento Destino:")).setBounds(x,y+=25,l+30,a);
		panel.add(txtfolio_estab_destino).setBounds(x+l+20,y,l-40,a);
		panel.add(txtestablecimiento_destino).setBounds((l*2)-10,y,l*3+40,a);
		
		panel.add(new JLabel("No. Cincho:")).setBounds(x+l*5+50,y,l+30,a);
		panel.add(txtCincho).setBounds(x+l*6+20,y,l*2,a);
		 		
		panel.add(new JLabel("Producto:")).setBounds(x,y+=25,l-40,a);
		panel.add(txtcod_prod).setBounds(x+l+20,y,l,a);
		panel.add(btnBuscar_Producto).setBounds(x+l+120,y,a,a);
		
		panel.add(btnGuardar).setBounds(x+260,y,l,a);
		panel.add(btnDeshacer).setBounds(x+l+280,y,l+20,a);
		
		panel.add(btnQuitar).setBounds(x,y+=25,l,a);
		panel.add(scroll).setBounds(10,170,1000,430);
		
		txtUsuario.setEditable(false);
		txtfolio_estab_origen.setEditable(false);
		txtestab_origen.setEditable(false);
		txtfolio_estab_destino.setEditable(false);
		txtestablecimiento_destino.setEditable(false);
		
		txtcod_prod.setEditable(false);
		btnBuscar_Producto.setEnabled(false);
		
		txtUsuario.setText(new Obj_Usuario().LeerSession().getNombre_completo());
        txtfolio_transferencia.setText(folio_tranferencia.trim());
        txtcod_prod.setText(cod_prod.trim());
        
        if(productos!=null){
        	for(String[] prod : productos){
	        	modelo.addRow(prod);
	        }
        }
       
        
//      asigna el foco al JTextField deseado al arrancar la ventana
        this.addWindowListener(new WindowAdapter() {
                public void windowOpened( WindowEvent e ){
                	
                	txtfolio_transferencia.requestFocus();
                	
                	if(!txtfolio_transferencia.getText().trim().equals("")){
                		enterauto();
                	}
             }
        });
        
	    render_proveedor();
//	    Llenar_Tabla_proveedores();
	    
		cont.add(panel);

		Nombre_Catalogo_Para_Filtro=this.getClass().getSimpleName();
		System.out.println(Nombre_Catalogo_Para_Filtro);
		
		evento(tabla);
		
		txtfolio_transferencia.addActionListener(opBuscarTranferencia);
		btnBuscar_Producto.addActionListener(opBuscar_Producto);
		btnDeshacer.addActionListener(deshacer);
		btnGuardar.addActionListener(Guardar);
		btnQuitar.addActionListener(opQuitar);
		
		txtcod_prod.addKeyListener(Buscar_Datos_Producto);
		
		tabla.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent arg0) {}
			@SuppressWarnings("deprecation")
			public void keyReleased(KeyEvent arg0) {
				
//				if(tabla.getSelectedColumn()==3){
					if(arg0.getKeyCode()==KeyEvent.VK_ENTER){
//						tabla.editingStopped(null);
						
						if(fila==tabla.getRowCount()-1){
								validarCelda(tabla.getRowCount()-1);
								tabla.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
				        	 	tabla.getSelectionModel().clearSelection();
				        	 	tabla.lostFocus(null, null);
				        	 	txtcod_prod.requestFocus();
						}else{
								fila++;
								tabla.getSelectionModel().setSelectionInterval(fila, fila);
								tabla.editCellAt(fila, 3);
								Component aComp=tabla.getEditorComponent();
								aComp.requestFocus();
								
								final JTextComponent jtc = (JTextComponent)aComp;
								  jtc.requestFocus();
								  jtc.selectAll();	
								  
								validarCelda(fila-1);
						}
						
					}
			}
			public void keyPressed(KeyEvent arg0) {}
		});

//		///buscar con F2
//        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
//                KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), "filtro");
//             getRootPane().getActionMap().put("filtro", new AbstractAction(){
//                 public void actionPerformed(ActionEvent e)
//                 {  
//                	 txtcod_prod.setText("");
//               	    btnBuscar_Producto.doClick();
//               	 }
//             });
		
		///deshacer con escape
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
        getRootPane().getActionMap().put("escape", new AbstractAction(){
        @SuppressWarnings("deprecation")
		public void actionPerformed(ActionEvent e)
         {                 	   
        	 	tabla.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        	 	tabla.getSelectionModel().clearSelection();
        	 	tabla.lostFocus(null, null);
        	 	txtcod_prod.requestFocus();
       	 }
     });
//     	///guardar con control+G
//         getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_G,Event.CTRL_MASK),"guardar");
//           getRootPane().getActionMap().put("guardar", new AbstractAction(){
//              public void actionPerformed(ActionEvent e)
//              {                 	    btnGuardar.doClick();
//            	    }
//         });
//       ///guardar con F12
//          getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0), "guardar");
//              getRootPane().getActionMap().put("guardar", new AbstractAction(){
//                  public void actionPerformed(ActionEvent e)
//                  {                 	    btnGuardar.doClick();
//                    	    }
//             });
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
     
    ActionListener opBuscarTranferencia = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			Object[][] comparar = new BuscarSQL().Consultar_Transferencia(txtfolio_transferencia.getText().toString().trim());
			
			if(Integer.valueOf(comparar[0][0].toString().trim())<0){
//				aviso el folio de transaccion es no existe
				txtfolio_transferencia.setEditable(true);
				txtcod_prod.setEditable(false);
				btnBuscar_Producto.setEnabled(false);
				txtfolio_transferencia.requestFocus();
				JOptionPane.showMessageDialog(null, "El Folio De Transaccion No Existe", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
				return;
			}else{
//				si la tranferencia origen == tranferencia destino
				if(Integer.valueOf(comparar[0][4].toString().trim()) == Integer.valueOf(comparar[0][5].toString().trim())){
					
					txtfolio_transferencia.setEditable(false);
					txtcod_prod.setEditable(true);
					btnBuscar_Producto.setEnabled(true);
					
					txtfolio_estab_origen.setText(comparar[0][0].toString().trim());
					txtestab_origen.setText(comparar[0][1].toString().trim());
					txtfolio_estab_destino.setText(comparar[0][2].toString().trim());
					txtestablecimiento_destino.setText(comparar[0][3].toString().trim());
					
					txtcod_prod.requestFocus();
				}else{
//					aviso la tranferencia no esta completa 
					txtfolio_transferencia.setEditable(true);
					txtcod_prod.setEditable(false);
					btnBuscar_Producto.setEnabled(false);
					txtfolio_transferencia.requestFocus();
					JOptionPane.showMessageDialog(null, "La Tranferencia No Esta Completa", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
					return;
				}
			}
		}
	}; 
	
	KeyListener Buscar_Datos_Producto = new KeyListener() {
		public void keyTyped(KeyEvent e){}
		public void keyReleased(KeyEvent e) {}
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				try {
					
					if(new Obj_Cotizaciones_De_Un_Producto().Existe_Producto(txtcod_prod.getText().trim().toUpperCase()+"")){
						
				      Obj_Cotizaciones_De_Un_Producto  Datos_Producto= new Obj_Cotizaciones_De_Un_Producto().buscardatos_producto(txtcod_prod.getText().trim().toUpperCase());
						txtcod_prod.setText(Datos_Producto.getCod_Prod().trim());
						
						Object[][] producto = new BuscarTablasModel().productoEnTranferencia(txtcod_prod.getText().toString().trim(),txtfolio_estab_destino.getText().trim());
						
							if(!producto[0][2].toString().trim().equals("No Existe En El Establecimiento")){
									
//								int existeProductoEnTabla = -1;
//									if(tabla.getRowCount()>0){
//										for(int i=0; i<tabla.getRowCount(); i++){
//											if(producto[0][1].toString().trim().equals(tabla.getValueAt(i, 1).toString().trim())){
//												existeProductoEnTabla = i;
//												break;
//											}
//										}
//									}
									
//									if(existeProductoEnTabla<0){
										for(Object[] reg : producto){
											modelo.addRow(reg);
										}
										CalcularFoliosTabla(tabla);
										
										fila = tabla.getRowCount()-1;
										
										
										tabla.getSelectionModel().setSelectionInterval(fila, fila);
										tabla.editCellAt(fila, 3);
										Component aComp=tabla.getEditorComponent();
										aComp.requestFocus();
										
										final JTextComponent jtc = (JTextComponent)aComp;
										  jtc.requestFocus();
										  jtc.selectAll();	
										  
//									}else{
//										
//										fila = existeProductoEnTabla;
//										
//											
//											tabla.getSelectionModel().setSelectionInterval(fila, fila);
//											tabla.editCellAt(fila, 3);
//											Component aComp=tabla.getEditorComponent();
//											aComp.requestFocus();
//											
//											final JTextComponent jtc = (JTextComponent)aComp;
//											  jtc.requestFocus();
//											  jtc.selectAll();					        	 	
//									}
									txtcod_prod.setText("");
									
							}else{
								JOptionPane.showMessageDialog(null, "No Se Encontro Producto En El Establecimiento Destino", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
								return;
							}		
						
					}else{
						JOptionPane.showMessageDialog(null, "El Codigo Esta Mal Escrito o El Producto No Existe" , "Aviso", JOptionPane.CANCEL_OPTION);
						return;
                    }
					
				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(null, "Error en Cat_Cotizaciones_De_Un_Producto_En_Proveedores  en la funcion existe_Producto \n SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
			}
			if(e.getKeyCode()==KeyEvent.VK_F2){
				
				dispose();
				new Cat_Filtro_De_Busqueda_De_Productos(Nombre_Catalogo_Para_Filtro,"",txtfolio_transferencia.getText().trim(),tabla_productos()).setVisible(true);
			}
		}
	};
   
	ActionListener opBuscar_Producto = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			dispose();
			new Cat_Filtro_De_Busqueda_De_Productos(Nombre_Catalogo_Para_Filtro,"",txtfolio_transferencia.getText().trim(),tabla_productos()).setVisible(true);
		}
	};
	
	public String[][] tabla_productos(){
		
		String[][] arreglo = null;
		
		int row = tabla.getRowCount();
		if(row>0){
			arreglo = new String[row][tabla.getColumnCount()];
			for(int i=0; i<= row-1; i++){
				for(int j=0; j<= tabla.getColumnCount()-1; j++){
					arreglo[i][j] = tabla.getValueAt(i, j).toString();
				}
			}
		}
		
		return arreglo;
	}
	
	ActionListener opQuitar = new ActionListener(){
		public void actionPerformed(ActionEvent arg0){
			
			if(tabla.getSelectedRow()>=0){
				
					if(JOptionPane.showConfirmDialog(null, "El Codigo De Producto ["+tabla.getValueAt(tabla.getSelectedRow(),0)+"] Se Quitara De La Tabla, ¿Desea Continuar?") == 0){
						modelo.removeRow(tabla.getSelectedRow());
						txtcod_prod.requestFocus();
					}
				
			}else{
				JOptionPane.showMessageDialog(null, "Seleccione El Producto Que Desea Remover", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
				return;
			}
		}
	};
	
	int fila = 0;
	public void evento(final JTable tb){
		tb.addMouseListener(new MouseListener() {
			int contadorDeClick = 0;
			public void mouseReleased(MouseEvent e) {
				
					contadorDeClick ++;
					if(contadorDeClick > 1){
						validarCelda(fila);
					}
					
					fila = tb.getSelectedRow();
					
					tb.getSelectionModel().setSelectionInterval(fila, fila);
					tb.editCellAt(fila, 3);
					Component aComp=tb.getEditorComponent();
					aComp.requestFocus();
					
					final JTextComponent jtc = (JTextComponent)aComp;
					  jtc.requestFocus();
					  jtc.selectAll();	
			}
			public void mousePressed(MouseEvent e) {		}
			public void mouseExited(MouseEvent e) {			}
			public void mouseEntered(MouseEvent e) {		}
			public void mouseClicked(MouseEvent e) {		}
		});
	}

 	public void validarCelda(int ValidarFila){
		try{
	        	if(!tabla.getValueAt(ValidarFila, 3).toString().trim().equals("")){
	        		Float.valueOf(tabla.getValueAt(ValidarFila, 3).toString().trim());
	        		calcularFila(ValidarFila);
	        	}
	        } catch (NumberFormatException nfe){
	        	tabla.setValueAt("", ValidarFila, 3);
	        	calcularFila(ValidarFila);
	        	System.out.println("no es entero");
	        }
	}
 	
     DecimalFormat df = new DecimalFormat("#0.00");
     public void calcularFila(int fila_actializar){
//    	importe
    	 double importe = Double.valueOf(tabla.getValueAt(fila_actializar, 3).toString().trim().equals("")?"0":tabla.getValueAt(fila_actializar, 3).toString().trim()) * Double.valueOf(tabla.getValueAt(fila_actializar, 5).toString().trim());
    	 tabla.setValueAt(df.format(importe), fila_actializar, 6);
//    	iva	 
     	 tabla.setValueAt(df.format(importe*0.16), fila_actializar, 7);    	 
//   	costo total 	 
    	 tabla.setValueAt(df.format(importe), fila_actializar, 8);
//    	importe total
    	 tabla.setValueAt(df.format(importe+(importe*0.16)), fila_actializar, 9);
     }
	  
	
	ActionListener Guardar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			
			if(tabla.isEditing()){
				tabla.getCellEditor().stopCellEditing();
			}
			
			if(new GuardarSQL().Guardar_Recepcion(tabla_productos(), txtfolio_transferencia.getText().trim(), txtfolio_estab_origen.getText().trim(), txtfolio_estab_destino.getText(), txtChofer.getText().toUpperCase().trim(), txtCincho.getText().toUpperCase().trim())){
	         	   limpiar();
	         	   JOptionPane.showMessageDialog(null, "Se Guardo Correctamente:","Aviso", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen//aplicara-el-dialogo-icono-6256-32.png"));
	         	   return;
			}else{
	   			   JOptionPane.showMessageDialog(null, "Error  en la funcion [ Guardar ] ", "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
	   			   return;
			}
			
		}
	};
	
	ActionListener deshacer = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			limpiar();
		}
	};
	
	public void limpiar(){
		
		txtfolio_transferencia.setEditable(true);
		txtcod_prod.setEditable(false);
		btnBuscar_Producto.setEnabled(false);
		txtfolio_transferencia.requestFocus();
		
		txtfolio_transferencia.setText("");
		txtfolio_estab_origen.setText("");
		txtestab_origen.setText("");
		txtfolio_estab_destino.setText("");
		txtestablecimiento_destino.setText("");
		txtcod_prod.setText("");
		 
		 modelo.setRowCount(0);
	}
	
	public void CalcularFoliosTabla(final JTable tb){
	for(int i = 0; i<tb.getRowCount(); i++){
		tb.setBackground(Color.blue);
		tb.setForeground(Color.white);
		tb.setValueAt((i+1)+"", i, 0);
	}
}

	
public void render_proveedor(){
	
		this.tabla.getTableHeader().setReorderingAllowed(false) ;
		this.tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			
		int x=80;
		tabla.getColumnModel().getColumn(0).setMaxWidth(x/2);
		tabla.getColumnModel().getColumn(0).setMinWidth(x/3);
		tabla.getColumnModel().getColumn(1).setMaxWidth(x);
		tabla.getColumnModel().getColumn(1).setMinWidth(x);
//		tabla.getColumnModel().getColumn(2).setMaxWidth(x*5);
		tabla.getColumnModel().getColumn(2).setMinWidth(x*4+20);
		tabla.getColumnModel().getColumn(3).setMaxWidth(x);
		tabla.getColumnModel().getColumn(3).setMinWidth(x);
		tabla.getColumnModel().getColumn(4).setMaxWidth(x/2);
		tabla.getColumnModel().getColumn(4).setMinWidth(x/2);
		tabla.getColumnModel().getColumn(5).setMaxWidth(x);
		tabla.getColumnModel().getColumn(5).setMinWidth(x);
		tabla.getColumnModel().getColumn(6).setMaxWidth(x);
		tabla.getColumnModel().getColumn(6).setMinWidth(x);
		tabla.getColumnModel().getColumn(7).setMaxWidth(x);
		tabla.getColumnModel().getColumn(7).setMinWidth(x);
		tabla.getColumnModel().getColumn(8).setMaxWidth(x);
		tabla.getColumnModel().getColumn(8).setMinWidth(x);
		tabla.getColumnModel().getColumn(9).setMaxWidth(x);
		tabla.getColumnModel().getColumn(9).setMinWidth(x);
	
//		tabla.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
		tabla.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12));
		tabla.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
		tabla.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("fecha","derecha","Arial","normal",12));
		tabla.getColumnModel().getColumn(4).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12));
		tabla.getColumnModel().getColumn(5).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12));
		tabla.getColumnModel().getColumn(6).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12));
		tabla.getColumnModel().getColumn(7).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12));
		tabla.getColumnModel().getColumn(8).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12));
		tabla.getColumnModel().getColumn(9).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12));
			
	}

	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Recepcion("","",null).setVisible(true);//recepcion,operador,cod_prod
		}catch(Exception e){	}
	}
}
