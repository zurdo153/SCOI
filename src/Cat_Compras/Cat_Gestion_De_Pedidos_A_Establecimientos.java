package Cat_Compras;

import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
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
import java.awt.event.WindowListener;
import java.io.File;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.KeyStroke;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.JTextComponent;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.BuscarTablasModel;
import Conexiones_SQL.Cargar_Combo;
import Conexiones_SQL.Connexion;
import Conexiones_SQL.GuardarSQL;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Compras.Obj_Gestion_De_Pedidos_A_Establecimientos;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_Filtro_Dinamico;
import Obj_Renders.ColorCeldas;
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
	JButton btnDeshacer				= new JCButton("Deshacer", "deshacer16.png", "Azul");
	JButton btnPendientes 			= new JCButton("", "Filter-List-icon16.png", "Azul");
	JButton btnCalcularInventario 	= new JCButton("Calcular Inventario", "Lista-32.png", "Azul");
	JButton btnGuardar 				= new JCButton("Guardar", "Guardar.png", "Azul");
	JButton btnReporte 				= new JCButton("Reportes", "Report.png", "Azul");
	JButton btnAsignar 				= new JCButton("Asignar", "articulo-icono-9036-32-mas.png", "Azul");
	
	String[] clasificacion = new Cargar_Combo().clasificador_de_pedidos_de_establecimientos();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbClasificador = new JComboBox(clasificacion);
	
	String[] establecimientos = new Obj_Establecimiento().Combo_Establecimientos("R");
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbEstablecimientos = new JComboBox(establecimientos);
	
	JTextField txtStatus = new Componentes().text(new JCTextField(), "Status Pedido", 50, "String");
	
	public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"Codigo Prod","Descripcion", "Disponible", "Pedido", "Surtida","Pendiente","Unidad","Localizacion","Partida"} ){
			@SuppressWarnings({ "rawtypes" })
			Class[] types = new Class[]{
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
	                        case 7  : return false;
	                        case 8  : return false;
	                }
	                 return false;
	         }
	    };
		
	    JTable tabla = new JTable(modelo);
		JScrollPane scroll = new JScrollPane(tabla,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
		int fila = 0;
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
		
		panel.add(btnDeshacer).setBounds(x+ancho*3-40, y, ancho*2, 30);
		x=840;
		panel.add(btnGuardar).setBounds(x, y, ancho*2, 30);
		
		llamarRender();
		cmbEstablecimientos.setSelectedItem("CEDIS");
//		cmbEstablecimientos.setEnabled(false);
		
		txtEstablecimientoOrigen.setEditable(false);
		txtEstablecimientoDestino.setEditable(false);
		txtUsuario.setEditable(false);
		txtStatus.setEditable(false);
		
		ExisteInventarioElDiaActual();
		
		btnCalcularInventario.addActionListener(opCargarInventario);
		
		btnPendientes.addActionListener(opPendientes);
		btnAsignar.addActionListener(opFiltroAsignacion);
		btnReporte.addActionListener(opFiltroReporteDeAsignaciones);
		btnBuscar.addActionListener(opBuscarPedido);
		txtPedido.addActionListener(opBuscarPedido);
		btnDeshacer.addActionListener(opDeshacer);
		btnGuardar.addActionListener(opGuardar);
		tabla.addKeyListener(op_key);
		agregar(tabla);
		
		cont.add(panel);
		
	   	///deshacer con escape
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
          getRootPane().getActionMap().put("escape", new AbstractAction(){
         public void actionPerformed(ActionEvent e)
         {                 	    btnDeshacer.doClick();
       	    }
     });
	}
	
	public void ExisteInventarioElDiaActual(){
		boolean ExisteInventario = new BuscarSQL().existeInventarioElDiaActual(cmbEstablecimientos.getSelectedItem().toString().trim());
		txtPedido.setEditable(ExisteInventario);
		btnBuscar.setEnabled(ExisteInventario);
		btnPendientes.setEnabled(ExisteInventario);
	}
	
	float surtido = 0;
	KeyListener op_key = new KeyListener() {
		public void keyTyped(KeyEvent e) {}
		public void keyReleased(KeyEvent e) {
			
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
						editaCelda("enter");
					}
		}
		public void keyPressed(KeyEvent e) {}
	};
	
	private void agregar(final JTable tbl) {
		tbl.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
					if(e.getClickCount() == 1){
						editaCelda("click");
					}
			}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {}
		});
		
	}
	
	public void editaCelda(String evento){
		validarCelda();
		
		float surtido = (Float.valueOf(modelo.getValueAt(fila, 4).toString().equals("")?"0":modelo.getValueAt(fila, 4).toString()));
		tabla.setValueAt(surtido, fila, 4);
		tabla.setValueAt((Float.valueOf(modelo.getValueAt(fila, 3).toString()))-surtido, fila, 5);
		
		if(evento.equals("click")){
			fila=tabla.getSelectedRow();
			 recorrerFoco();
		}else{
			try {
				fila++;
				recorrerFoco();
				
			} catch (Exception e2) {
				fila=0;
				recorrerFoco();
			}
		}
	}
	
	public void recorrerFoco(){
		
		tabla.getSelectionModel().setSelectionInterval(fila, fila);
		tabla.editCellAt(fila, 4);
		Component aComp=tabla.getEditorComponent();
		final JTextComponent jtc = (JTextComponent)aComp;
		  jtc.requestFocus();
		  jtc.selectAll();
		aComp.requestFocus();
		scrollposicion(tabla,fila,4);
	}
	
	public static void scrollposicion(JTable table, int filap, int columnap) {
        if (!(table.getParent() instanceof JViewport)) {
            return;
        }
        JViewport viewport = (JViewport)table.getParent();
        Rectangle rect = table.getCellRect(filap, columnap, true);
        Point pt = viewport.getViewPosition();
        rect.setLocation(rect.x-pt.x, rect.y-pt.y);
        table.scrollRectToVisible(rect);
        // Scroll the area into view
        viewport.scrollRectToVisible(rect);
    }
	
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
   		this.tabla.getTableHeader().setReorderingAllowed(false) ;
   		this.tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
   		
		tabla.getColumnModel().getColumn(0).setMinWidth(70);
		tabla.getColumnModel().getColumn(0).setMaxWidth(70);
		tabla.getColumnModel().getColumn(1).setMinWidth(420);
		tabla.getColumnModel().getColumn(2).setMinWidth(75);
		tabla.getColumnModel().getColumn(3).setMinWidth(75);
		tabla.getColumnModel().getColumn(4).setMinWidth(75);
		tabla.getColumnModel().getColumn(5).setMinWidth(75);
		tabla.getColumnModel().getColumn(6).setMinWidth(45);
		tabla.getColumnModel().getColumn(6).setMaxWidth(45);
		tabla.getColumnModel().getColumn(7).setMinWidth(75);
		tabla.getColumnModel().getColumn(8).setMinWidth(45);
		tabla.getColumnModel().getColumn(8).setMaxWidth(45);
		
		tabla.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12)); 	
		tabla.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
		tabla.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12)); 	
		tabla.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12)); 
		tabla.getColumnModel().getColumn(4).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12)); 	
		tabla.getColumnModel().getColumn(5).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12)); 
		tabla.getColumnModel().getColumn(6).setCellRenderer(new tablaRenderer("texto","centro","Arial","normal",12)); 	
		tabla.getColumnModel().getColumn(7).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
		tabla.getColumnModel().getColumn(8).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12)); 
   	}
	
   	ActionListener opDeshacer = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			limpiar();
		}
   	};
   	
   	public void limpiar(){
   		
   		cmbEstablecimientos.setEnabled(true);
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
			
			if(new BuscarSQL().existenPedidosActivos()){
				
				System.out.println(new BuscarSQL().existeInventarioElDiaActual(cmbEstablecimientos.getSelectedItem().toString().trim()));
				if(!new BuscarSQL().existeInventarioElDiaActual(cmbEstablecimientos.getSelectedItem().toString().trim())){
					//limpiar pedidos
					cargarInventario("FINALIZAR_PEDIDOS");
					
				}else{
					if(JOptionPane.showConfirmDialog(null, "Ya Existe Inventario El Dia De Hoy, Desea Recalcularlo?") == 0){
						cargarInventario("");
					}
				}
			}else{
				JOptionPane.showMessageDialog(null, "No Se Puede Generar Nuevamente El Inventario Una Vez Que Se Guardó Un Pedido", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}
			
			
		}
	};
	
	public void cargarInventario(String bandera){
//		if(new BuscarSQL().existenPedidosActivos()){
			if(cmbEstablecimientos.getSelectedIndex()>0){
					if(new GuardarSQL().Cargar_Inventario(cmbEstablecimientos.getSelectedItem().toString().trim(),bandera)){
						ExisteInventarioElDiaActual();
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
//		}else{
////			if(bandera.equals("FINALIZAR_PEDIDOS")){
////				if(new GuardarSQL().Cargar_Inventario(cmbEstablecimientos.getSelectedItem().toString().trim(),bandera)){
////					JOptionPane.showMessageDialog(null, "El Inventario Se Cargo Exitosamente", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/aplicara-el-dialogo-icono-6256-32.png"));
////					return;
////				}else{
////					JOptionPane.showMessageDialog(null, "No Se Ha Podido Cargar El Inventario", "Error", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-icono-eliminar5252-64.png"));
////					return;
////				}
////			}else{
//			
//			if(new GuardarSQL().Cargar_Inventario(cmbEstablecimientos.getSelectedItem().toString().trim(),bandera)){
//				JOptionPane.showMessageDialog(null, "El Inventario Se Cargo Exitosamente", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/aplicara-el-dialogo-icono-6256-32.png"));
//				return;
//			}else{
//				JOptionPane.showMessageDialog(null, "No Se Ha Podido Cargar El Inventario", "Error", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-icono-eliminar5252-64.png"));
//				return;
//			}
//			
////				JOptionPane.showMessageDialog(null, "No Se Ha Podido Cargar El Inventario Debido A Que Hay Pedidos Pendientes\nPara Cargar El Inventario, Surta O Cancele Los Pedidos.", "Error", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
////				return;
////			}
//			
//		}
	}
	
	ActionListener opBuscarPedido = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			
//			//MODIFICAR ESTA FUNCION  (BUSCAR SI YA SE CAPTURO Y RECONSULTAR DE SCOI O  BUSCAR DIRECTO)
			banderaGuardarModificar = new BuscarSQL().existeEnScoi(txtPedido.getText().trim());
			
			if(!txtPedido.getText().equals("")){
				Obj_Gestion_De_Pedidos_A_Establecimientos pedido = new Obj_Gestion_De_Pedidos_A_Establecimientos().buscar(txtPedido.getText().toUpperCase());
				
				//S211515
				System.out.println(pedido.getStatus_pedido().toString().trim());
				if(new BuscarSQL().existenPedidosPendientesPorSurtir()){
					if(pedido.getStatus_pedido().trim().equals("VIGENTE") || pedido.getStatus_pedido().equals("RECIBIDO") || pedido.getStatus_pedido().equals("NUEVO")){
						BUSCAR();
					  }else{
						  
		  				    if(pedido.getStatus_pedido().equals("SURTIDO") || pedido.getStatus_pedido().equals("ASIGNADO")){
		  				    	limpiar();
								JOptionPane.showMessageDialog(null, "Los Pedidos Con Status SURTIDO y ASIGNADO No Pueden Ser Modificado", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
								return;
		  				    }else{
								limpiar();
								JOptionPane.showMessageDialog(null, "Es Necesario Surtir Todos Los Pedidos Pendientes", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
								return;
							}
					  }
				}else{
					if(new BuscarSQL().existeInventarioElDiaActual(cmbEstablecimientos.getSelectedItem().toString().trim())){
						BUSCAR();
						
					}else{
						limpiar();
						JOptionPane.showMessageDialog(null, "El Dia De Hoy No Se Ha Calculado El Inventario", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
						return;
					}
				}
				
			}else{
				limpiar();
				txtPedido.requestFocus();
				JOptionPane.showMessageDialog(null, "Ingrese Un Folio Para Consultar Pedido", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}
		}
	};
	
	@SuppressWarnings("deprecation")
	public void BUSCAR(){
		Obj_Gestion_De_Pedidos_A_Establecimientos pedido = new Obj_Gestion_De_Pedidos_A_Establecimientos().buscar(txtPedido.getText().toUpperCase());
		
		if(pedido.getStatus_pedido().equals("SURTIDO")){
			limpiar();
			JOptionPane.showMessageDialog(null, "El Pedido No Se Puede Mostrar Debido A Que Ya Fue Surtido", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
			return;
		}else{
				if(!pedido.getUsuario().equals("")){
								cmbEstablecimientos.setEnabled(false);
								txtPedido.setEditable(false);
								txtEstablecimientoOrigen.setText(pedido.getOrigen());
								txtEstablecimientoDestino.setText(pedido.getDestino());
								cmbClasificador.setSelectedItem(pedido.getClasificador());
								txtUsuario.setText(pedido.getUsuario());		
								txtStatus.setText(pedido.getStatus_pedido());
								modelo.setRowCount(0);
								Object[][] productos = new BuscarTablasModel().Buscar_Pedido(txtPedido.getText().toUpperCase(),banderaGuardarModificar);

								for(Object[] prod : productos){
											modelo.addRow(prod);
										}
										if(!banderaGuardarModificar.equals("MODIFICAR")){
											calcularSurtido("AUTOMATICO");
										}
								fila=0;		
								tabla.lostFocus(null, null);
								tabla.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
								tabla.getSelectionModel().clearSelection();
				}else{
					limpiar();
					txtPedido.setText("");
					txtPedido.requestFocus();
					JOptionPane.showMessageDialog(null, "No Se Ha Encontrado Pedido Con El Folio Especificado", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
					return;
				}
		
		}
	}
	
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
			new Cat_Asignacion_De_Pedido(cmbEstablecimientos.getSelectedItem().toString().trim()).setVisible(true);
		}
	};
	
	
	
	ActionListener opGuardar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
				if(txtStatus.getText().equals("RECIBIDO")){
					 seleccionDeStatus();
				}else{
					//el parametro vacio no altera el estatus (quedara el valor default)
					guardar("N");
				}
			}
		};
		
		public void seleccionDeStatus(){
				
			Icon icon = new ImageIcon("Imagen/equipos-de-tarea-asignada-icono-7668-32.png");
	        
			String[] options = {"Recibido", "Surtir", "Cancelar"};
			String aviso = "El Pedido Ya Se Encuentra Asignado, Si Desea Seguir Realizando Cambios Seleccione [Recibido],\nSi El Pedido Ya Es Correcto Seleccione [Surtir], Si No Desea Realizar Cambios Seleccione [Cancelar]";
			int seleccion = JOptionPane.showOptionDialog(null, aviso, "Seleccion De Status", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, icon, options, options[0]);
			
			//entra si selecciona [Asignado] ó [Surtir]
			if(seleccion<2){
				guardar(seleccion==0?"N":"S");
			}
			
		} 
		
	public void guardar(String decideStatus){

		if(tabla.isEditing()){
			tabla.getCellEditor().stopCellEditing();
		}
		calcularSurtido("GUARDAR");
		
		Obj_Gestion_De_Pedidos_A_Establecimientos pedido = new Obj_Gestion_De_Pedidos_A_Establecimientos();
		
		pedido.buscar(txtPedido.getText().trim());
		 
		if(banderaGuardarModificar.equals("MODIFICAR")){
//				modificar
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
									JOptionPane.showMessageDialog(null, "El Registro Se Actualizo Exitosamente", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/aplicara-el-dialogo-icono-6256-32.png"));
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
	
	String[][] filtroDePendientes;
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void consultarfiltro(String Modificar,String folio_pedido, String establecim){
		
		modelo.setRowCount(0);
		Connexion con = new Connexion();
		Statement s;
		ResultSet rs;
		try {
			String query = "exec sp_select_filtro_de_seleccion_de_pedido2 '"+Modificar+"','"+folio_pedido+"','"+(new Obj_Usuario().LeerSession().getFolio())+"','"+establecim+"'"; 
			s = con.conexion().createStatement();
			rs = s.executeQuery(query);
			
			System.out.println(query);
			
			Vector vPrincipal = new Vector();//filas
			Vector SubVector;//columnas
			
			while (rs.next())
			{ 
				SubVector = new Vector();
				for(int j =0; j<7; j++){
					SubVector.add(rs.getString(j+1).trim());
				}
				vPrincipal.add(SubVector);
			}
			
			filtroDePendientes = new String[vPrincipal.size()][((Vector) vPrincipal.get(0)).size()];
			for(int i=0; i<vPrincipal.size(); i++){
				for(int j=0; j<((Vector) vPrincipal.get(0)).size(); j++){
					filtroDePendientes[i][j] = ((Vector) vPrincipal.get(i)).get(j).toString(); 
				}
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en consultarfiltro SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
	}
	

	public static void main(String[] args) {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Gestion_De_Pedidos_A_Establecimientos().setVisible(true);
		}catch(Exception e){
			System.err.println("Error :"+ e.getMessage());
		}
	}
	
	//TODO Filtro De Pedidos
	public class Cat_Filtro_De_Pedidos_Nuevos extends JDialog{
		Object[][] Matriz_pedidos_ctes ;
		Container cont = getContentPane();
		JLayeredPane panel = new JLayeredPane();
//		Runtime R = Runtime.getRuntime();
		
		DefaultTableModel modeloFiltro = new DefaultTableModel(null,new String[]{"Folio", "Usuario Capturo","Estab Solicitante","Estab Surte","Fecha Elaboracion","Fecha Modificacion","Status"}
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
		
	    JTable tabla = new JTable(modeloFiltro);
	    JScrollPane scrollAsignado = new JScrollPane(tabla,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		@SuppressWarnings("rawtypes")
		private TableRowSorter trsfiltro;
		
		JTextField txtFolio= new JTextField();
		JTextField txtFolioProveedor = new JTextField();
		
		JButton btnFinalizarEmbarque 			= new JCButton("FINALIZAR EMBARQUE", "iconMeta.png", "Azul");
		JButton btnCancelar 			= new JCButton("CANCELAR", "Delete.png", "Azul");
		JButton btnActualizarFiltro = new JCButton("ACTUALIZAR","refrescar-volver-a-cargar-las-flechas-icono-4094-32.png","Azul");

		JCheckBox chbActivar_Avisos = new JCheckBox("Ventana De Avisos");
		
		Border blackline, etched, raisedbevel, loweredbevel, empty;
		
		int cantidad_pedido_actual = 0;
	    String FechaIn = "";
		String FechaFin = "";
		String estab = "";
	    
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Cat_Filtro_De_Pedidos_Nuevos(String establecimiento){
			this.setModal(true);
			int ancho = 1024;//Toolkit.getDefaultToolkit().getScreenSize().width;
			int alto = Toolkit.getDefaultToolkit().getScreenSize().height-50;
			
			estab = establecimiento;
			chbActivar_Avisos.setSelected(true);
			
			this.setSize(ancho, alto);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setTitle("Filtro De Pedidos Pendientes");
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/lista-icono-7220-32.png"));
			blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
			panel.setBorder(BorderFactory.createTitledBorder(blackline,"Seleccion De Pedido A Surtir"));
			this.cont.add(panel);

			btnActualizarFiltro.setEnabled(true);
			btnActualizarFiltro.setToolTipText("<F5> Tecla Directa");
			
			trsfiltro = new TableRowSorter(modeloFiltro); 
			tabla.setRowSorter(trsfiltro);
			txtFolio.setToolTipText("Filtro Por Folio De Entrada");
			txtFolioProveedor.setToolTipText("Filtro Por Codigo De Proveedor");

			txtFolio.addKeyListener(opFiltro);
			txtFolioProveedor.addKeyListener(opFiltro);
			
//			llamarRender();
			
			llenarTablaFiltro("");
			
			int y = 20;
			panel.add(btnFinalizarEmbarque).setBounds(375,y-12,200,32);
			panel.add(btnCancelar).setBounds(605,y-12,180,32);
			panel.add(btnActualizarFiltro).setBounds(815,y-12,180,32);
			panel.add(txtFolio).setBounds(15,y,60,20);
			panel.add(txtFolioProveedor).setBounds(75,y,260,20);
			panel.add(chbActivar_Avisos).setBounds(340,y,150,20);
			panel.add(scrollAsignado).setBounds(15,y+=20,ancho-30,alto-70);
	            
			cantidad_pedido_actual = tabla.getRowCount();
			
			PintarEstatusTabla(tabla,"Filtro De Pedido De Pendientes Por Establecimiento",6);//tipo_de_tabla , columnas 0 
			Hilo_1_Minuto();
			
//			buscarEntradas(establecimiento,"Refresh");
//			consultarfiltro("","",establecimiento);
			agregar(tabla);
			btnActualizarFiltro.addActionListener(Buscar_Cambios);
			btnCancelar.addActionListener(Buscar_Cambios);
			btnFinalizarEmbarque.addActionListener(Finalizar_Pedido);
			
//	     Buscar Con F5
	        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
	                     KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0), "Actualizar");
	                  getRootPane().getActionMap().put("Actualizar", new AbstractAction(){
	                      public void actionPerformed(ActionEvent e)
	                      {        	    btnActualizarFiltro.doClick();          	    }
	                  });
	                  
////		Buscar Con CLOSE
//	        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
//	                     KeyStroke.getKeyStroke(KeyEvent.VK, 0), "cerrar");
//	                  getRootPane().getActionMap().put("cerrar", new AbstractAction(){
//	                      public void actionPerformed(ActionEvent e)
//	                      {        	    seg.stop();          	    }
//	                  });
	                  
//	     asigna el foco al JTextField fecha al arrancar la ventana
	                  this.addWindowListener(new WindowAdapter() {
	                          public void windowOpened( WindowEvent e ){
	                        	  txtFolioProveedor.requestFocus();
	                       }
	                  });
	                  
	    this.addWindowListener(new WindowListener() {
			public void windowOpened(WindowEvent e) {
			}
			public void windowIconified(WindowEvent e) {
			}
			public void windowDeiconified(WindowEvent e) {
			}
			public void windowDeactivated(WindowEvent e) {
			}
			@SuppressWarnings("deprecation")
			public void windowClosing(WindowEvent e) {
				seg.stop();
			}
			public void windowClosed(WindowEvent e) {
			}
			public void windowActivated(WindowEvent e) {
			}
		});
		}
		
		public void llenarTablaFiltro(String actualizar){
			modeloFiltro.setRowCount(0);
			
			if(filtroDePendientes==null ||  actualizar.equals("SI") ||  actualizar.equals("HILO")){
				System.out.println("nulooo");
				consultarfiltro("","",cmbEstablecimientos.getSelectedItem().toString().trim());
			}			
				
			int Existen_pedidos_nuevos = filtroDePendientes.length;
			
				String[] fila = new String[7];
				for(int i=0 ; i<filtroDePendientes.length; i++){
					
					fila[0] = filtroDePendientes[i][0].toString();
					fila[1] = filtroDePendientes[i][1].toString();
					fila[2] = filtroDePendientes[i][2].toString();
					fila[3] = filtroDePendientes[i][3].toString();
					fila[4] = filtroDePendientes[i][4].toString();
					fila[5] = filtroDePendientes[i][5].toString();
					fila[6] = filtroDePendientes[i][6].toString();
					
					modeloFiltro.addRow(fila);
				}
				
				if(actualizar.equals("HILO") && Existen_pedidos_nuevos > cantidad_pedido_actual){
			    	cantidad_pedido_actual=Existen_pedidos_nuevos;
			    	mostrarAviso = "si";
			    	
			    }else{
			    	cantidad_pedido_actual=Existen_pedidos_nuevos;
			    	mostrarAviso = "no";
			    }
		}
		
		ActionListener Buscar_Cambios = new ActionListener(){
			@SuppressWarnings({ })
			public void actionPerformed(ActionEvent e){
				pedido(cmbEstablecimientos.getSelectedItem().toString(),e.getActionCommand());
			}
		};
		
		public void pedido(String establecimiento,String boton){
			
			String folio_pedido = tabla.getSelectedRow()>-1?tabla.getValueAt(tabla.getSelectedRow(), 0).toString().trim():"";
	    	String status_pedido = tabla.getSelectedRow()>-1?tabla.getValueAt(tabla.getSelectedRow(), 6).toString().trim():"";
	    	
				if(boton.toUpperCase().equals("CANCELAR") && folio_pedido.equals("")){
						JOptionPane.showMessageDialog(null, "Favor De Seleccionar El Registro Que Se Desea Cancelar", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
						return;
				}else{
					
						if((boton.toUpperCase().equals("ACTUALIZAR")) || (boton.toUpperCase().equals("CANCELAR") && status_pedido.equals("VIGENTE")) ){
							
							 llenarTablaFiltro("SI");
//							 dispose();
							 
						}else{
								JOptionPane.showMessageDialog(null, "Solo Se Pueden Cancelar Registros Con Status Vigente", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
								return;
						}
				}
		}
		
		ActionListener Finalizar_Pedido = new ActionListener(){
			@SuppressWarnings({ })
			public void actionPerformed(ActionEvent e){
				
				String folio_pedido = tabla.getSelectedRow()>-1?tabla.getValueAt(tabla.getSelectedRow(), 0).toString().trim():"";
		    	String status_pedido = tabla.getSelectedRow()>-1?tabla.getValueAt(tabla.getSelectedRow(), 6).toString().trim():"";
//				int cantidad_pedidos_generados = new BuscarSQL().Folios_generados(folio_pedido);//contar folios generados
				
		    	if(tabla.getSelectedRow() >= 0){
		    		
					if(status_pedido.equals("ASIGNADO") || status_pedido.equals("RECIBIDO") || status_pedido.equals("MULTIPLES")/*&& cantidad_pedidos_generados > 0*/ ){
						
						if(JOptionPane.showConfirmDialog(null, "Al Finalizar El Pedido No Podra Realizar El Embarque Del Mismo,\nSerá Necesario Realizar Un Pedido Nuevo, Desea Continuar?") == 0){
							System.out.println("Finalizar Pedido Aqui");
							if(new GuardarSQL().Finalizar_Pedido(folio_pedido))
							{
//								System.out.println("Finalizar Pedido Aqui");
								llenarTablaFiltro("SI");
								JOptionPane.showMessageDialog(null, "EL Pedido Se Finalizo Correctamente", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
//								dispose();
							}
						}	
					}else{
						JOptionPane.showMessageDialog(null, "Solo Se Pueden Finalizar Pedidos Con Status: ASIGNADO, RECIBIDO Ó MULTIPLES", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
						return;
					}
		    	}else{
		    		JOptionPane.showMessageDialog(null, "Es Necesario Que Seleccione El Pedido Que Desea Finalizar", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
					return;
		    	}
			}
		};
		
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
			    

		private void agregar(final JTable tbl) {
			tbl.addMouseListener(new MouseListener() {
				@SuppressWarnings("deprecation")
				public void mouseReleased(MouseEvent e) {
						if(e.getClickCount() == 2){
							int fila_Select = tabla.getSelectedRow();
			    			String folio =  tabla.getValueAt(fila_Select, 0).toString().trim();
			    			String status = tabla.getValueAt(fila_Select, 6).toString().trim();
			    			dispose();
			    			seg.stop();
			    			System.out.println(status);
			    			if(status.equals("NUEVO") || status.equals("VIGENTE") || status.equals("RECIBIDO")){
			    				txtPedido.setText(folio);
			    				btnBuscar.doClick();
			    			}else{
			    				JOptionPane.showMessageDialog(null, "Solo Pueden Realizar Movimientos Sobre Pedidos Con Status: NUEVO, VIGENTE Y RECIBIDO", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
								return;
			    			}
			    			
			    			
						}
				}
				public void mousePressed(MouseEvent e) {}
				public void mouseExited(MouseEvent e) {}
				public void mouseEntered(MouseEvent e) {}
				public void mouseClicked(MouseEvent e) {}
			});
		}
		
		int SeActivoSonido=0;
		String mostrarAviso = "no";
		 AudioClip sonido;
		 boolean cerrarhilo = false;
		
//	  	pintado De tabla
		public void PintarEstatusTabla(final JTable tb, String tipo_de_tabla, int columnas){
			//se crea instancia a clase FormatoTable y se indica columna patron
	        ColorCeldas ft = new ColorCeldas(tipo_de_tabla,columnas);
	        tb.setDefaultRenderer (Object.class, ft );
		}
		
		@SuppressWarnings({ "unchecked", "deprecation" })
		public void MostrarAvisoEmergente(){
			try {
				
					if(mostrarAviso.equals("si")){
						  //////Limpiar	Filtros
								trsfiltro.setRowFilter(RowFilter.regexFilter("", 0));
								trsfiltro.setRowFilter(RowFilter.regexFilter("", 1));
								trsfiltro.setRowFilter(RowFilter.regexFilter("", 2));
								
								txtFolio.setText("");
								txtFolioProveedor.setText("");
						         
						         txtFolioProveedor.requestFocus();
						         
									if(chbActivar_Avisos.isSelected()){
										   File f=new File("M:\\SISTEMA DE CONTROL OPERATIVO IZAGAR\\SCOI\\voz\\Nuevo_Pedido.wav");//archivo de audio
							        	    URL u=f.toURL();//lo convertimos a url
							        	    sonido=JApplet.newAudioClip(u); //Bueno de la AudioClip no se puede instancias por eso esto
								        	    sonido.play();//para que suene
								        	    SeActivoSonido=1;
								        	    
								        	//   apartado para configurar el uso de la pantalla de avisos--------------------------------
						                    JDialog frame = new JDialog();
						                     String ruta= "prueba mensaje";//fila_mensaje.get(3).toString().trim();
						        		    frame.setUndecorated(true);
						        		    new Cat_Avisos_De_Pedido(frame,ruta);
						        		    frame.setVisible(true);
									}
						    	 // seresetea la variable para que no muestre aviso de nuevo asta que existan entradas nuevas
									mostrarAviso="no";
			    	}
//				}
				
		
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
			JOptionPane.showMessageDialog(null, "Error en Cat_Supervision_De_Entrada_De_Mercancia en la funcion MostrarAvisoEmergente   SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
	}
		}
		
	/////////////////////////////////////////////////////////////////////////////////////
	////////////HILO REVISION AUTOMATICA DE PEDIDOS CADA 60 SEGUNDOS
		segundero seg = new segundero();
		public void Hilo_1_Minuto() {
				
				seg.start();
			    	}
			    	int reconsultar=0;
			    	public class segundero extends Thread {
			    		public void run() {
			    			while(cerrarhilo !=true){
			    					try {
			    						Thread.sleep(1000);
			    						reconsultar+=1;
			    						if(reconsultar==600)////cambiar a 600 segundos = 10 min
			    						{
			    						   reconsultar=0;
			    						   
//			    						   FechaIn = new SimpleDateFormat("dd/MM/yyyy").format(fh_inicial.getDate());
//			    						   FechaFin = new SimpleDateFormat("dd/MM/yyyy").format(fh_final.getDate());
										
			    						   llenarTablaFiltro("HILO");
										   MostrarAvisoEmergente();
//					    				   btnBuscar.doClick();	
			    						   
			    						   

			    						}
			    					} catch (InterruptedException e) {
			    		                 JOptionPane.showMessageDialog(null, "Error en Cat_Hilo_1_Minuto en la funcion segundero  SQLException: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			    						System.err.println("Error: "+ e.getMessage());
			    				}
			    			}
			    	}
			    }
	//////////////////////////////////////////////////////////////////////////////////
	///////////CATALOO EMERGENTE DE AVISO		    	
			    	
		  	public class Cat_Avisos_De_Pedido extends JComponent {
			    		
						private Image background;
			    		
			    		JLabel lblAviso = new JLabel();
			    		String fileFoto = System.getProperty("user.dir")+"/imagen/avisos/PedidoNuevo.png";
			    		ImageIcon tmpIconAuxFoto = new ImageIcon(fileFoto);

			    		public Cat_Avisos_De_Pedido(final JDialog frame,String ruta) {
			    			
			    			//fileFoto=ruta;
			    			frame.setModal(true);
			    			updateBackground( );
			    			frame.add(lblAviso).setBounds(0, 0, 500, 400);
			    			 Icon iconoFoto = new ImageIcon(tmpIconAuxFoto.getImage().getScaledInstance(lblAviso.getWidth(), lblAviso.getHeight(), Image.SCALE_DEFAULT));
			                 lblAviso.setIcon(iconoFoto);
			                 
			    			frame.setLayout(new BorderLayout( ));
			    			frame.getContentPane( ).add("Center",this);
			    			frame.pack( );
			    			frame.setAlwaysOnTop( true );
			    			frame.setSize(500,400);
			    			frame.setLocationRelativeTo(null);
			    		    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
			    				       KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "foco");
			    		    
			    		    getRootPane().getActionMap().put("foco", new AbstractAction(){
			    		        @Override
			    		        public void actionPerformed(ActionEvent e)
			    		        {
			    		        	frame.dispose();
			    		        }
			    		    });
			    		}
		  	
		  	
		  	public void updateBackground( ) {
	    		try {
	    		Robot rbt = new Robot( );
	    		Toolkit tk = Toolkit.getDefaultToolkit( );
	    		Dimension dim = tk.getScreenSize( );
	    		background = rbt.createScreenCapture(
	    		new Rectangle(0,0,(int)dim.getWidth( ),
	    		(int)dim.getHeight( )));
	    		} catch (Exception ex) {
	    		ex.printStackTrace( );
	    		}
	    		}
		  	
		  	public void paintComponent(Graphics g) {
	    		Point pos = this.getLocationOnScreen( );
	    		Point offset = new Point(-pos.x,-pos.y);
	    		g.drawImage(background,offset.x,offset.y,null);
	    		repaint();
	    		}
		  	}
//	   	@SuppressWarnings("unused")
//		private void llamarRender()	{		
//			tabla.getTableHeader().setReorderingAllowed(false) ;
//			tabla.getColumnModel().getColumn(0).setMinWidth(80);
//			tabla.getColumnModel().getColumn(1).setMinWidth(270);
//			tabla.getColumnModel().getColumn(2).setMinWidth(130);
//			tabla.getColumnModel().getColumn(3).setMinWidth(130);
//			tabla.getColumnModel().getColumn(4).setMinWidth(85);
//			tabla.getColumnModel().getColumn(5).setMinWidth(85);
//			tabla.getColumnModel().getColumn(6).setMinWidth(80);
//			
//			tabla.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 	
//			tabla.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
//			tabla.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 	
//			tabla.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
//			tabla.getColumnModel().getColumn(4).setCellRenderer(new tablaRenderer("texto","centro","Arial","normal",12)); 	
//			tabla.getColumnModel().getColumn(5).setCellRenderer(new tablaRenderer("texto","centro","Arial","normal",12)); 
//			tabla.getColumnModel().getColumn(6).setCellRenderer(new tablaRenderer("texto","centro","Arial","normal",12)); 	
//	   	}
	}	
	
}
