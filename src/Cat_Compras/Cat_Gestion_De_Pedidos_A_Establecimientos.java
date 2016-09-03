package Cat_Compras;

import java.awt.Component;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
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
import Conexiones_SQL.BuscarTablasModel;
import Conexiones_SQL.Cargar_Combo;
import Conexiones_SQL.Connexion;
import Conexiones_SQL.GuardarSQL;
import Obj_Compras.Obj_Gestion_De_Pedidos_A_Establecimientos;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_Filtro_Dinamico;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Gestion_De_Pedidos_A_Establecimientos extends JFrame{
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JTextField txtPedido 				 = new Componentes().text(new JCTextField(), "Folio De Pedido", 12, "String");
	JTextField txtEstablecimientoOrigen  = new Componentes().text(new JCTextField(), "Establecimiento Origen", 100, "String");
	JTextField txtEstablecimientoDestino = new Componentes().text(new JCTextField(), "Establecimiento Destino", 100, "String");
	JTextField txtUsuario				 = new Componentes().text(new JCTextField(), "Nombre Del Usuario", 280, "String");
	
	JButton btnBuscar 				= new JCButton("", "Buscar.png", "Azul");
	JButton btnDeshecer				= new JCButton("Deshacer", "deshacer16.png", "Azul");
	JButton btnPendientes 			= new JCButton("", "Filter-List-icon16.png", "Azul");
	JButton btnCalcularInventario 	= new JCButton("Calcular Inventario", "Lista-32.png", "Azul");
	JButton btnGuardar 				= new JCButton("Guardar", "Guardar.png", "Azul");
	JButton btnReporte 				= new JCButton("Reporte", "Report.png", "Azul");
	JButton btnAsignar 				= new JCButton("Asignar", "articulo-icono-9036-32-mas.png", "Azul");
	
	String[] clasificacion = new Cargar_Combo().clasificador_de_pedidos_de_establecimientos();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbClasificador = new JComboBox(clasificacion);
	
	String[] establecimientos = new Obj_Establecimiento().Combo_Establecimiento_Estado_resultados();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbEstablecimientos = new JComboBox(establecimientos);
	
	JTextField txtStatus = new Componentes().text(new JCTextField(), "Status Pedido", 50, "String");
	
	public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"Cod_Prod","Descripcion", "Disponible", "Pedido", "Surtida","Pendiente","Unidad"} ){
         
			@SuppressWarnings({ "rawtypes" })
			Class[] types = new Class[]{
	                   java.lang.Object.class, 
	                   java.lang.Object.class,
	                   java.lang.Object.class,
	                   java.lang.Object.class, 
	                   java.lang.Object.class,
	                   java.lang.Object.class,
	                   java.lang.Object.class
	                    
	    };
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {
	                return types[columnIndex];
	        }
	    public boolean isCellEditable(int fila, int columna){
	                switch(columna){
	                		case 0	: return false;
	                        case 1  : return false; 
	                        case 2  : return false; 
	                        case 3  : return false; 
	                        case 4  : return true;
	                        case 5  : return false;
	                        case 6  : return false;
	                }
	                 return false;
	         }
	    };
		
	    JTable tabla = new JTable(modelo);
		JScrollPane scroll = new JScrollPane(tabla,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
		int filaDep = 0;
		int columnaDep = 4;
		String banderaGuardarModificar = "";
		
	public Cat_Gestion_De_Pedidos_A_Establecimientos() {
		this.setTitle("Gestion De Pedidos A Establecimiento");
		
		this.setSize(1024,660);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		int x=20,y=15,ancho=80;
		panel.add(new JLabel("Folio Pedido:")).setBounds(x, y, ancho, 20);
		panel.add(txtPedido).setBounds(x+=(ancho), y, ancho, 20);
		panel.add(btnBuscar).setBounds(x+=(ancho)+2, y, 30, 20);
		panel.add(btnPendientes).setBounds(x+34, y, 30, 20);
		
		panel.add(new JLabel("Clasificacion:")).setBounds(x+=(ancho)+20, y, ancho*3-5, 20);
		panel.add(cmbClasificador).setBounds(x+=(ancho), y, ancho*2, 20);
		
		panel.add(new JLabel("Status:")).setBounds(x+=(ancho*2)+15, y, ancho*3-5, 20);
		panel.add(txtStatus).setBounds(x+=(ancho)-35, y, ancho*2, 20);
		
		panel.add(cmbEstablecimientos).setBounds(x+=(ancho*2)+20, y, ancho*3-5, 20);

		x=20;
		panel.add(new JLabel("Origen:")).setBounds(x, y+=25, ancho, 20);
		panel.add(txtEstablecimientoOrigen).setBounds(x+=(ancho), y, ancho*2, 20);
		panel.add(new JLabel("Destino:")).setBounds(x+=(ancho*2+20), y, ancho, 20);
		panel.add(txtEstablecimientoDestino).setBounds(x+=(ancho), y, ancho*2, 20);
		
		panel.add(btnAsignar).setBounds(x+=(ancho*2+10), y, (ancho+30)*2, 45);
		panel.add(btnCalcularInventario).setBounds(x+=(ancho*3)-10, y, ancho*3, 45);
		
		x=20;
		panel.add(new JLabel("Usuario:")).setBounds(x, y+=25, ancho, 20);
		panel.add(txtUsuario).setBounds(x+=(ancho), y, ancho*5+20, 20);
		
		x=20;
		panel.add(scroll).setBounds(x, y+=25, ancho*12+20, 500);
		panel.add(btnReporte).setBounds(x, y+=505, ancho*2, 30);
		
		panel.add(btnDeshecer).setBounds(x+ancho*3-40, y, ancho*2, 30);
		x=840;
		panel.add(btnGuardar).setBounds(x, y, ancho*2, 30);
		
		llamarRender();
		cmbEstablecimientos.setSelectedItem("CEDIS");
		cmbEstablecimientos.setEnabled(false);
		
		txtEstablecimientoOrigen.setEditable(false);
		txtEstablecimientoDestino.setEditable(false);
		txtUsuario.setEditable(false);
		txtStatus.setEditable(false);
		
		btnCalcularInventario.addActionListener(opCargarInventario);
		
		btnPendientes.addActionListener(opPendientes);
		btnAsignar.addActionListener(opFiltroAsignacion);
		btnReporte.addActionListener(opFiltroReporteDeAsignaciones);
		
		btnBuscar.addActionListener(opBuscarPedido);
		txtPedido.addActionListener(opBuscarPedido);
		btnDeshecer.addActionListener(opDeshacer);
		btnGuardar.addActionListener(opGuardar);
		tabla.addKeyListener(op_key);
		
		cont.add(panel);
	}
	
	int fila = 0;
	float surtido = 0;
	KeyListener op_key = new KeyListener() {
		public void keyTyped(KeyEvent e) {}
		public void keyReleased(KeyEvent e) {
			
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					validarCelda();
					
					float surtido = (Float.valueOf(modelo.getValueAt(fila, 4).toString().equals("")?"0":modelo.getValueAt(fila, 4).toString()));
					tabla.setValueAt(surtido, fila, 4);
					tabla.setValueAt((Float.valueOf(modelo.getValueAt(fila, 3).toString()))-surtido, fila, 5);
//					tabla.setValueAt( ((Float.valueOf(modelo.getValueAt(fila, 3).toString()))-surtido)<0?0:((Float.valueOf(modelo.getValueAt(fila, 3).toString()))-surtido), fila, 5);
					
					try {
						fila++;
						tabla.getSelectionModel().setSelectionInterval(fila, fila);
						tabla.editCellAt(fila, 4);
						Component aComp=tabla.getEditorComponent();
						aComp.requestFocus();
						
						
					} catch (Exception e2) {
						// TODO: handle exception
						fila=0;
						tabla.getSelectionModel().setSelectionInterval(fila, fila);
						tabla.editCellAt(fila, 4);
						Component aComp=tabla.getEditorComponent();
						aComp.requestFocus();
					}
					
				}
		}
		public void keyPressed(KeyEvent e) {}
	};
	
	public void validarCelda(){
		try{
	        	if(!tabla.getValueAt(fila, 4).toString().trim().equals("")){
	        		Float.valueOf(tabla.getValueAt(fila, 4).toString().trim());
	        	}
	        } catch (NumberFormatException nfe){
	        	tabla.setValueAt(0, fila, 4);
	        }
	}
	
   	private void llamarRender()	{		
		tabla.getTableHeader().setReorderingAllowed(false) ;
		tabla.getColumnModel().getColumn(0).setMinWidth(80);
		tabla.getColumnModel().getColumn(1).setMinWidth(420);
		tabla.getColumnModel().getColumn(2).setMinWidth(80);
		tabla.getColumnModel().getColumn(3).setMinWidth(80);
		tabla.getColumnModel().getColumn(4).setMinWidth(80);
		tabla.getColumnModel().getColumn(5).setMinWidth(80);
		tabla.getColumnModel().getColumn(6).setMinWidth(80);
		
		tabla.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12)); 	
		tabla.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
		tabla.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12)); 	
		tabla.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12)); 
		tabla.getColumnModel().getColumn(4).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12)); 	
		tabla.getColumnModel().getColumn(5).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12)); 
		tabla.getColumnModel().getColumn(6).setCellRenderer(new tablaRenderer("texto","centro","Arial","normal",12)); 	
   	}
	
   	ActionListener opDeshacer = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			limpiar();
		}
   	};
   	
   	public void limpiar(){
   		txtPedido.setEditable(true);
   		txtPedido.setText("");
   		txtEstablecimientoOrigen.setText("");
   		txtEstablecimientoDestino.setText("");
   		txtUsuario.setText("");
   		txtPedido.requestFocus();
   		txtStatus.setText("");
   		modelo.setRowCount(0);
   		
   		banderaGuardarModificar = "";
   		cmbClasificador.setSelectedIndex(0);
   	}
   	
   	
	ActionListener opCargarInventario = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			
//			btnCalcularInventario.setEnabled(new BuscarSQL().existenPedidosActivos());
			
			if(new BuscarSQL().existenPedidosActivos()){
				
					if(cmbEstablecimientos.getSelectedIndex()>0){
						
						if(new GuardarSQL().Cargar_Inventario(cmbEstablecimientos.getSelectedItem().toString())){
							JOptionPane.showMessageDialog(null, "El Inventario Se Cargo Exitosamente", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/aplicara-el-dialogo-icono-6256-32.png"));
							return;
						}else{
							JOptionPane.showMessageDialog(null, "No Se Ha Podido Cargar El Inventario", "Error", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-icono-eliminar5252-64.png"));
							return;
						}
						
					}else{
						JOptionPane.showMessageDialog(null, "Seleccione Un Establecimiento", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
						return;
					}
			}else{
				JOptionPane.showMessageDialog(null, "No Se Ha Podido Cargar El Inventario Debido A Que Hay Pedidos Pendientes\nPara Cargar El Inventario Surta O Cancele Los Pedidos.", "Error", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}
			
		}
	};
	
	ActionListener opBuscarPedido = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			
//			//MODIFICAR ESTA FUNCION  (BUSCAR SI YA SE CAPTURO Y RECONSULTAR DE SCOI O  BUSCAR DURECTO)
			banderaGuardarModificar = new BuscarSQL().existeEnScoi(txtPedido.getText().trim());
			
			if(!txtPedido.getText().equals("")){
				
				Obj_Gestion_De_Pedidos_A_Establecimientos pedido = new Obj_Gestion_De_Pedidos_A_Establecimientos().buscar(txtPedido.getText().toUpperCase());
				
				if(pedido.getStatus_pedido().equals("SURTIDO")){
					JOptionPane.showMessageDialog(null, "El Pedido No Se Puede Mostrar Debido A Que Ya Fue Surtido", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
					return;
				}else{
					
						if(!pedido.getUsuario().equals("")){
		
							txtPedido.setEditable(false);
							txtEstablecimientoOrigen.setText(pedido.getOrigen());
							txtEstablecimientoDestino.setText(pedido.getDestino());
							
							cmbClasificador.setSelectedItem(pedido.getClasificador());
							
							txtUsuario.setText(pedido.getUsuario());		
							txtStatus.setText(pedido.getStatus_pedido());
							
							Object[][] productos = new BuscarTablasModel().Buscar_Pedido(txtPedido.getText().toUpperCase(),banderaGuardarModificar);
							
//							tabla.lostFocus(null, null);
							tabla.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
//							tabla.getSelectionModel().clearSelection();
							modelo.setRowCount(0);
							
							System.out.println(productos[0][0].toString());
							if(!productos[0][0].toString().equals("El Pedido No Cuenta Con Productos")){
									for(Object[] prod : productos){
										modelo.addRow(prod);
									}
									if(!banderaGuardarModificar.equals("MODIFICAR")){
										calcularSurtido("AUTOMATICO");
									}
							}else{
								JOptionPane.showMessageDialog(null, productos[0][0].toString(), "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
								return;
							}
							
							filaDep=0;
							columnaDep=4;
			            	tabla.setEnabled(true);
			    			tabla.editCellAt(filaDep, columnaDep);
			    			Component aComp=tabla.getEditorComponent();
			    			aComp.requestFocus();
			    			tabla.putClientProperty("terminateEditOnFocusLost", Boolean.FALSE);
			    			
						}else{
							txtPedido.setText("");
							txtPedido.requestFocus();
							JOptionPane.showMessageDialog(null, "No Se Ha Encontrado Pedido Con El Folio Especificado", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
							return;
						}
				
				}
				
			}else{
				txtPedido.requestFocus();
				JOptionPane.showMessageDialog(null, "Ingrese Un Folio Para Consultar Pedido", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}
			
		}
	};
	
	public void calcularSurtido(String movimiento){
		for(int i=0; i<modelo.getRowCount(); i++){
			float surtido = 0;
			
			if(!movimiento.equals("GUARDAR")){
				surtido = (Float.valueOf(modelo.getValueAt(i, 2).toString()))>=(Float.valueOf(modelo.getValueAt(i, 3).toString()))?(Float.valueOf(modelo.getValueAt(i, 3).toString())):(Float.valueOf(modelo.getValueAt(i, 2).toString()));
				tabla.setValueAt(surtido, i, 4);
				tabla.setValueAt((Float.valueOf(modelo.getValueAt(i, 3).toString()))-surtido, i, 5);
			}else{
				surtido = 0;
				tabla.setValueAt((Float.valueOf(modelo.getValueAt(i, 3).toString()))-(Float.valueOf(modelo.getValueAt(i, 4).toString())), i, 5);
			}
		}
	}
	
	ActionListener opPendientes = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			new Cat_Filtro_De_Pedidos_Nuevos(cmbEstablecimientos.getSelectedItem().toString()).setVisible(true);
		}
	};
	
	ActionListener opFiltroReporteDeAsignaciones = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			new Cat_Reportes_De_Pedidos_Asignados().setVisible(true);
		}
	};
	
	ActionListener opFiltroAsignacion = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			new Cat_Asignacion_De_Pedido().setVisible(true);
		}
	};
	
	
	
	ActionListener opGuardar = new ActionListener(){
		public void actionPerformed(ActionEvent e){

				if(txtStatus.getText().equals("ASIGNADO")){
					 seleccionDeStatus();
				}else{
					//el parametro vacio no altera el estatus (quedara el valor default)
					guardar("");
				}

			}
		};
		
		public void seleccionDeStatus(){
				
			Icon icon = new ImageIcon("Iconos/camara_icon&16.png");
	        
			String[] options = {"Asignado", "Surtir"};
			String aviso = "El Pedido Ya Se Encuentra Asignado, Si Desea Seguir Realizando Cambios Selecciones [Asignado],\nSi El Pedido Ya Es Correcto Seleccione [Surtir]";
			int seleccion = JOptionPane.showOptionDialog(null, aviso, "Seleccion De Status", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, icon, options, options[0]);
			System.out.println(seleccion);
			
			//el parametro vacio no altera el estatus (quedara a como este)
			guardar(seleccion==0?"":"S");
		}
		
	public void guardar(String decideStatus){

		if(tabla.isEditing()){
			tabla.getCellEditor().stopCellEditing();
		}
		calcularSurtido("GUARDAR");
		
		Obj_Gestion_De_Pedidos_A_Establecimientos pedido = new Obj_Gestion_De_Pedidos_A_Establecimientos();
		
		pedido.buscar(txtPedido.getText().trim());
		 
		if(banderaGuardarModificar.equals("MODIFICAR")){
//				modifiar
				if(JOptionPane.showConfirmDialog(null, "El registro existe, ¿desea actualizarlo?") == 0){
					
						if(ValidaCampos().equals("")){
							
								pedido.setFolio_pedido(txtPedido.getText().toUpperCase().trim());
								pedido.setOrigen(txtEstablecimientoOrigen.getText().toUpperCase().trim());
								pedido.setDestino(txtEstablecimientoDestino.getText().toUpperCase().trim());
								pedido.setClasificador(cmbClasificador.getSelectedItem().toString());
								pedido.setUsuario(txtUsuario.getText().toUpperCase().trim());
								pedido.setStatus_pedido(decideStatus);
								
								pedido.setMatriz(cargarTabla());
								
								if(pedido.guardar_actualizar(banderaGuardarModificar)){
									limpiar();
									JOptionPane.showMessageDialog(null, "El Registro Se Actualizo Exitisamente", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/aplicara-el-dialogo-icono-6256-32.png"));
									return;
								}else{
									JOptionPane.showMessageDialog(null, "El Registro No Se Pudo Actualizar", "Error", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-icono-eliminar5252-64.png"));
									return;
								}
						}else{
							JOptionPane.showMessageDialog(null, "Los Siguientes Campos Son Requeridos: \n"+ValidaCampos(), "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
							return;
						}
				}
			
		}else{
//				guardar
				if(ValidaCampos().equals("")){
					
						pedido.setFolio_pedido(txtPedido.getText().toUpperCase().trim());
						pedido.setOrigen(txtEstablecimientoOrigen.getText().toUpperCase().trim());
						pedido.setDestino(txtEstablecimientoDestino.getText().toUpperCase().trim());
						pedido.setClasificador(cmbClasificador.getSelectedItem().toString());
						pedido.setUsuario(txtUsuario.getText().toUpperCase().trim());
						pedido.setStatus_pedido(decideStatus);
						
						pedido.setMatriz(cargarTabla());
						
						if(pedido.guardar_actualizar(banderaGuardarModificar)){
							limpiar();
							JOptionPane.showMessageDialog(null, "El Registro Se Guardo Exitisamente", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/aplicara-el-dialogo-icono-6256-32.png"));
							return;
						}else{
							JOptionPane.showMessageDialog(null, "El Registro No Se Pudo Guardar", "Error", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-icono-eliminar5252-64.png"));
							return;
						}
				}else{
					JOptionPane.showMessageDialog(null, "Los Siguientes Campos Son Requeridos: \n"+ValidaCampos(), "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
					return;
				}
		}
	
	}
	
	public Object[][] cargarTabla(){
		
		Object[][] arreglo = new Object[tabla.getRowCount()][tabla.getColumnCount()];
		for(int i=0; i<tabla.getRowCount(); i++){
			for(int j=0; j<tabla.getColumnCount(); j++){
				arreglo[i][j] = tabla.getValueAt(i, j).toString().trim();
			}
		}
		return arreglo;
	}
	
	public String ValidaCampos(){
		String vacios = "";
		
		vacios += txtPedido.getText().equals("")?"Pedido\n":"";
		vacios += txtEstablecimientoOrigen.getText().equals("")?"Origen\n":"";
		vacios += txtEstablecimientoDestino.getText().equals("")?"Destino\n":"";
		vacios += txtUsuario.getText().trim().equals("")?"Usuario\n":"";
		
		vacios += cmbClasificador.getSelectedIndex()==0?"Clasificador\n":"";
		vacios += cmbEstablecimientos.getSelectedIndex()==0?"Establecimiento\n":"";
		
		vacios += tabla.getRowCount()==0?"Pedido Sin Productos\n":"";
		
		return vacios;
	}

	public static void main(String[] args) {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Gestion_De_Pedidos_A_Establecimientos().setVisible(true);
		}catch(Exception e){
			System.err.println("Error :"+ e.getMessage());
		}
	}
	
	public class Cat_Filtro_De_Pedidos_Nuevos extends JFrame{
		
		Object[][] Matriz_pedidos_ctes ;
		Container cont = getContentPane();
		JLayeredPane panel = new JLayeredPane();
		Connexion con = new Connexion();
		Runtime R = Runtime.getRuntime();
		
		DefaultTableModel modelo = new DefaultTableModel(null,new String[]{"Folio", "Usuario Capturo","Estab Solicitante","Estab Surte","Fecha Elevoracion","Fecha Modificacion","Status"}
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
	        	 } 				
	 			return false;
	 		}
		};
		
	    JTable tabla = new JTable(modelo);
	    JScrollPane scrollAsignado = new JScrollPane(tabla,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		@SuppressWarnings("rawtypes")
		private TableRowSorter trsfiltro;
		
		JTextField txtFolio= new JTextField();
		JTextField txtFolioProveedor = new JTextField();
		
		JButton btnActualizarFiltro = new JCButton("ACTUALIZAR","refrescar-volver-a-cargar-las-flechas-icono-4094-32.png","Azul");

		Border blackline, etched, raisedbevel, loweredbevel, empty;
		
	    String FechaIn = "";
		String FechaFin = "";
	    
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Cat_Filtro_De_Pedidos_Nuevos(String establecimiento){
			int ancho = 1024;//Toolkit.getDefaultToolkit().getScreenSize().width;
			int alto = Toolkit.getDefaultToolkit().getScreenSize().height-50;
			
			this.setSize(ancho, alto);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setTitle("Supervision De Pedidos De Establecimientos");
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/lista-icono-7220-32.png"));
			blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
			panel.setBorder(BorderFactory.createTitledBorder(blackline,"Status Revision De Pedidos De Establecimientos"));
			this.cont.add(panel);

			btnActualizarFiltro.setEnabled(true);
			btnActualizarFiltro.setToolTipText("<F5> Tecla Directa");
			
			trsfiltro = new TableRowSorter(modelo); 
			tabla.setRowSorter(trsfiltro);
			txtFolio.setToolTipText("Filtro Por Folio De Entrada");
			txtFolioProveedor.setToolTipText("Filtro Por Codigo De Proveedor");

			txtFolio.addKeyListener(opFiltro);
			txtFolioProveedor.addKeyListener(opFiltro);
			
			llamarRender();
			
			int y = 20;
			panel.add(btnActualizarFiltro).setBounds(695,y-12,180,32);
			panel.add(txtFolio).setBounds(15,y,60,20);
			panel.add(txtFolioProveedor).setBounds(75,y,260,20);
			panel.add(scrollAsignado).setBounds(15,y+=20,ancho-30,alto-70);
	             
			buscarEntradas(establecimiento);
			agregar(tabla);
			btnActualizarFiltro.addActionListener(Buscar_Cambios);
			
//	     Buscar Con F5
	        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
	                     KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0), "Actualizar");
	                  getRootPane().getActionMap().put("Actualizar", new AbstractAction(){
	                      public void actionPerformed(ActionEvent e)
	                      {        	    btnActualizarFiltro.doClick();          	    }
	                  });
//	     asigna el foco al JTextField fecha al arrancar la ventana
	                  this.addWindowListener(new WindowAdapter() {
	                          public void windowOpened( WindowEvent e ){
	                        	  txtFolioProveedor.requestFocus();
	                       }
	                  });
		}
		
		public void buscarEntradas(String establecimiento){
			modelo.setRowCount(0);
			
			Statement s;
			ResultSet rs;
			try {
				
				String query = "select pedestab.folio "
						+ "		,usuarios.nombre as usuario_captura "
						+ "		,estab.nombre as estab "
						+ "		,estab_alt.nombre as estab_alterno "
						+ "		,convert(varchar(20),pedestab.fecha_elaboracion,103) AS fecha_elaboracion "
						+ "		,convert(varchar(20),pedestab.ultima_modificacion,103)+' '+convert(varchar(20),pedestab.ultima_modificacion,108) as ultima_modificacion "
						+ "		,case when (pedestab.status_surtido)='N' then 'NUEVO' "
						+ "				when (pedestab.status_surtido)='T' then 'TRANSFERIDO' "
						+ "				else 'RECEPCIONADO' end as status_surtido "
						+ " from pedestab "
						+ " inner join establecimientos estab on estab.cod_estab = pedestab.cod_estab "
						+ " inner join establecimientos estab_alt on estab_alt.cod_estab = pedestab.cod_estab_alterno and ltrim(rtrim(estab_alt.nombre)) = '"+establecimiento+"' "
						+ " inner join usuarios on usuarios.usuario = pedestab.usuario_captura "
						+ " where pedestab.ultima_modificacion > CONVERT(DATETIME, convert(varchar(20),getdate()-1,103) ) "
						+ " and pedestab.status_surtido = 'N' "
						+ " order by ultima_modificacion desc";
				
				s = con.conexion_IZAGAR().createStatement();
				rs = s.executeQuery(query);
				
				while (rs.next())
				{ 
				   String [] fila = new String[7];
				   fila[0] = rs.getString(1).trim();
				   fila[1] = rs.getString(2).trim();
				   fila[2] = rs.getString(3).trim(); 
				   fila[3] = rs.getString(4).trim(); 
				   fila[4] = rs.getString(5).trim(); 
				   fila[5] = rs.getString(6).trim(); 
				   fila[6] = rs.getString(7).trim(); 

				   modelo.addRow(fila); 
				}
				System.out.println(query);
				
			} catch (SQLException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en buscarEntradas SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			}
		}
		
		KeyListener opFiltro = new KeyListener(){
			@SuppressWarnings("unchecked")
			public void keyReleased(KeyEvent arg0) {
				new Obj_Filtro_Dinamico(tabla,"Folio", txtFolio.getText().toUpperCase().trim(),"Usuario Capturo",txtFolioProveedor.getText().toUpperCase().trim(), "", "", "", "");

				trsfiltro.setRowFilter(RowFilter.regexFilter(txtFolio.getText(), 0));
				
			}
			public void keyTyped(KeyEvent arg0) {}
			public void keyPressed(KeyEvent arg0) {}	
		    };
		
		public String cargar_fechas(int dias){
			String date = null;
		    	try {
					date = new BuscarSQL().fecha(dias);
					} catch (SQLException e) {
						// catch block
						e.printStackTrace();
						}
			return date;
		};
			    
		ActionListener Buscar_Cambios = new ActionListener(){
			@SuppressWarnings({ })
			public void actionPerformed(ActionEvent e){
				buscarEntradas(cmbEstablecimientos.getSelectedItem().toString());
			}
		};
		
		private void agregar(final JTable tbl) {
	        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
		        public void mouseClicked(MouseEvent e) {
		        	
		        	if(e.getClickCount() == 2){
		    			int fila = tabla.getSelectedRow();
		    			Object folio =  tabla.getValueAt(fila, 0).toString().trim();
		    			dispose();
		    			txtPedido.setText(folio+"");
		    			btnBuscar.doClick();
		        	}
		        }
	        });
	    }
		
	   	private void llamarRender()	{		
			tabla.getTableHeader().setReorderingAllowed(false) ;
			tabla.getColumnModel().getColumn(0).setMinWidth(80);
			tabla.getColumnModel().getColumn(1).setMinWidth(270);
			tabla.getColumnModel().getColumn(2).setMinWidth(130);
			tabla.getColumnModel().getColumn(3).setMinWidth(130);
			tabla.getColumnModel().getColumn(4).setMinWidth(80);
			tabla.getColumnModel().getColumn(5).setMinWidth(130);
			tabla.getColumnModel().getColumn(6).setMinWidth(80);
			
			tabla.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 	
			tabla.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
			tabla.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 	
			tabla.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
			tabla.getColumnModel().getColumn(4).setCellRenderer(new tablaRenderer("texto","centro","Arial","normal",12)); 	
			tabla.getColumnModel().getColumn(5).setCellRenderer(new tablaRenderer("texto","centro","Arial","normal",12)); 
			tabla.getColumnModel().getColumn(6).setCellRenderer(new tablaRenderer("texto","centro","Arial","normal",12)); 	
	   	}
	}	

}
