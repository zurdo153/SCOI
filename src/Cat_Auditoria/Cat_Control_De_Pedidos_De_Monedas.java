package Cat_Auditoria;

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
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
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
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.BuscarTablasModel;
import Conexiones_SQL.Cargar_Combo;
import Obj_Auditoria.Obj_Pedido_De_Monedas;
import Obj_Principal.Obj_Filtro_Dinamico;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Control_De_Pedidos_De_Monedas extends JFrame{

	Container cont = getContentPane();
	JLayeredPane campo = new JLayeredPane();
	
	JButton btnCancelar = new JButton("Cancelar Pedido",new ImageIcon("imagen/Delete.png"));
	
	public Object[][] Filtro_Cuentas( ){
			try {
				return new BuscarSQL().Filtro_De_Cuentas_polizas();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
	}
	
	DefaultTableModel modelo = new DefaultTableModel(null,
            new String[]{"Fecha De Pedido", "Folio", "Empleado","Establecimiento","Puesto","Status De Pedido","Total De Pedido" }
			){
	     @SuppressWarnings("rawtypes")
		Class[] types = new Class[]{
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
        	 	} 				
 			return false;
 		}
	};
	JTable tabla = new JTable(modelo);
    JScrollPane scroll = new JScrollPane(tabla,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
	JTextField txtCodigo = new JTextField();
	JTextField txtDescripcion = new JTextField();
	
	public String[] establecimiento(){try {return new Cargar_Combo().EstablecimientoTb();} catch (SQLException e) {e.printStackTrace();}return null;}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbEstablecimiento = new JComboBox(establecimiento());
	
	public String[] statusPedido = {"PEDIDO","SURTIDO","ENTREGADO","RECIBIDO","CANCELADO","TODOS"};
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbStatusPedido = new JComboBox(statusPedido);
	
	
	String status_parametro = "";
	
//	esta funcion se usara en la venta de retiros programados para avilitar el boton de pedido o de recibir
	public static String tipoDeUsuarioQuePidio(){return new BuscarTablasModel().tipoDeUsuarioParaPedidoDeMonedas();}
	
	public Cat_Control_De_Pedidos_De_Monedas(){
		String tipoDeUsuarioDePedidodeMonedas = tipoDeUsuarioQuePidio();
		String titulo = "";

		switch(tipoDeUsuarioDePedidodeMonedas){
			case "SURTIDO":		titulo="Surtir Pedido De Cajero(a)";	break;
			case "ENTREGADO":	titulo="Recibir Pedido De Cajero(a)";	break;
			default:				titulo="";							break;
		}
		
		if(titulo.equals("")){
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen//usuario-de-alerta-icono-4069-64.png"));
			this.setTitle("Aviso");
			constructorAviso();
		}else{
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/monedas-en-efectivo-en-moneda-icono-4023-64.png"));
			this.setTitle(titulo);
			campo.setBorder(BorderFactory.createTitledBorder("Seleccionar Un Cajero(a)"));
			constructor(tipoDeUsuarioDePedidodeMonedas);
		}
	}
	
public void constructor(String tipoDeUsuarioDePedidodeMonedas){
		status_parametro = tipoDeUsuarioDePedidodeMonedas;
		campo.add(scroll).setBounds(15,42,1000,565);
		campo.add(txtCodigo).setBounds(145,20,70,20);
		campo.add(txtDescripcion).setBounds(214,20,320,20);
		campo.add(cmbEstablecimiento).setBounds(535,20,160,20);
		campo.add(cmbStatusPedido).setBounds(815,20,100,20);
		campo.add(btnCancelar).setBounds(15,20,130,20);
		
		btnCancelar.setEnabled(false);
		
		if(status_parametro.equals("ENTREGADO")){
			cmbStatusPedido.setSelectedItem("SURTIDO");
			cmbStatusPedido.setEnabled(false);
		}else{
			cmbStatusPedido.setSelectedItem(cmbStatusPedido.getSelectedItem().toString());
			cmbStatusPedido.setEnabled(true);
		}
		llenarTablaDePedidos();
		
		render();
		agregar(tabla);
		btnCancelar.addActionListener(opCancelar);
		cmbStatusPedido.addActionListener(opBuscarConCmbStatus);
		
		cmbEstablecimiento.addActionListener(opFiltroDimanicoEstab);
		txtCodigo.addKeyListener(opFiltroDimanico);
		txtDescripcion.addKeyListener(opFiltroDimanico);
		cont.add(campo);
		
		this.setSize(1040,650);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//      asigna el foco al JTextField del nombre deseado al arrancar la ventana
        this.addWindowListener(new WindowAdapter() {
                public void windowOpened( WindowEvent e ){
                	txtDescripcion.requestFocus();
             }
        });
          
	}

public void constructorAviso(){
	JLabel imagen = new JLabel(new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
	campo.add(imagen).setBounds(15,20,64,64);
	campo.add(new JLabel("El Usuario No Esta Autorizado Para Realizar Movimientos En Esta Opción")).setBounds(100,45,550,20);
	cont.add(campo);
	
    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
       KeyStroke.getKeyStroke(KeyEvent.VK_ENTER , 0), "aceptarAviso");
    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
    	       KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE , 0), "aceptarAviso");
    
    getRootPane().getActionMap().put("aceptarAviso", new AbstractAction(){
        @Override
        public void actionPerformed(ActionEvent e)
        {
        	dispose();
        }
    });
	this.setSize(500,150);
	this.setResizable(false);
	this.setLocationRelativeTo(null);
	this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
}

ActionListener opBuscarConCmbStatus = new ActionListener() {
	public void actionPerformed(ActionEvent arg0) {
		llenarTablaDePedidos();
	}
};

public void llenarTablaDePedidos(){
	Object[][] registros = null;
	if(status_parametro.equals("SURTIDO")){
		registros = new BuscarTablasModel().listaDePedidoDeMonedas(cmbStatusPedido.getSelectedItem().toString());
	}else{
		registros = new BuscarTablasModel().listaDePedidoDeMonedas("SURTIDO");
	}
	modelo.setRowCount(0);
	for(Object[] fila : registros){
		modelo.addRow(fila);
	}
	
}
	
	private void agregar(final JTable tbl) {
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
	        public void mouseClicked(MouseEvent e) {
	        	
	        	fila = tabla.getSelectedRow();
	        	btnCancelar.setEnabled(true);
	        	
	        	if(e.getClickCount() == 2){
	        		
		        		fila = tabla.getSelectedRow();
		        		
		        		if( tabla.getValueAt(fila,5).toString().trim().equals("CANCELADO")){
			    				JOptionPane.showMessageDialog(null, "El Registro Esta Con El Estatus de CANCELADO  No Puede Ser Surtido","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
			    				return;
		        		}else{
		        			
			        			if(status_parametro.equals("SURTIDO")){
				        				if(!tabla.getValueAt(fila,5).toString().trim().equals("PEDIDO")){
				        						JOptionPane.showMessageDialog(null, "los Pedidos SURTIDO, RECIBIDO, ENTREGADO O CANCELADO  No Pueden Cancelarse","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
				        						return;
				        				}else{
					        				new CapturarPedido(Integer.valueOf(tabla.getValueAt(fila, 1).toString().trim()),tabla.getValueAt(fila, 2).toString().trim(), status_parametro, status_parametro.equals("SURTIDO")?"CORTES":"ENCARGADO").setVisible(true);
				        				}
			        			}else{
//			  		      				ENTREGADO
					        			new CapturarPedido(Integer.valueOf(tabla.getValueAt(fila, 1).toString().trim()),tabla.getValueAt(fila, 2).toString().trim(), status_parametro, status_parametro.equals("SURTIDO")?"CORTES":"ENCARGADO").setVisible(true);
			        			}
		        		}
	        	}
	        }
        });
    }
	
	int fila=0;
	
	ActionListener opCancelar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			if(!tabla.getValueAt(fila,5).toString().trim().equals("PEDIDO")){
				JOptionPane.showMessageDialog(null, "los Pedidos SURTIDO, RECIBIDO, ENTREGADO O CANCELADO  No Pueden Cancelarse","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
        	       return;
			}else{
			
			Obj_Pedido_De_Monedas pedido = new Obj_Pedido_De_Monedas();
			
			pedido.setFolioUsuario(Integer.valueOf(tabla.getValueAt(fila, 1).toString().trim()));
			pedido.setStatus_pedido("CANCELADO");
			
			Object[][] relleno = {{0,0,0,0}};
			pedido.setMatriz(relleno);
			
			pedido.setObservacion("");
			pedido.setEmpleado_entrego("");
			
			if(pedido.guardar()){
				
				llenarTablaDePedidos();
				
				if(tabla.getRowCount()==0){btnCancelar.setEnabled(false);}
				
				JOptionPane.showMessageDialog(null, "El Pedido De Monedas Fue Cancelado","Aviso",JOptionPane.INFORMATION_MESSAGE);
				return;
			}else{
				JOptionPane.showMessageDialog(null, "Ocurrió un error al intentar cancelar el pedido","Error",JOptionPane.ERROR_MESSAGE);
				return;
			}
			}
			
		}
	};	
	
	ActionListener opFiltroDimanicoEstab = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			new Obj_Filtro_Dinamico(tabla,"Folio", txtCodigo.getText().toUpperCase(),"Empleado",txtDescripcion.getText(), "Establecimiento", cmbEstablecimiento.getSelectedItem().toString(), "", "");

		}
	};
	
	KeyListener opFiltroDimanico = new KeyListener(){
		public void keyReleased(KeyEvent arg0) {
			new Obj_Filtro_Dinamico(tabla,"Folio", txtCodigo.getText().toUpperCase(),"Empleado",txtDescripcion.getText(), "Establecimiento", cmbEstablecimiento.getSelectedItem().toString(), "", "");
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}		
	};
	
	
	
   	private void render(){		
		
		tabla.getTableHeader().setReorderingAllowed(false) ;
		tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		tabla.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
	    tabla.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
	    tabla.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
	    tabla.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
	    tabla.getColumnModel().getColumn(4).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
	    tabla.getColumnModel().getColumn(5).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
	    tabla.getColumnModel().getColumn(6).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
		
		tabla.getColumnModel().getColumn(0).setMinWidth(130);
		tabla.getColumnModel().getColumn(0).setMaxWidth(130);
		tabla.getColumnModel().getColumn(1).setMinWidth(70);
	    tabla.getColumnModel().getColumn(1).setMaxWidth(70);
		tabla.getColumnModel().getColumn(2).setMinWidth(320);
		tabla.getColumnModel().getColumn(2).setMaxWidth(320);
		tabla.getColumnModel().getColumn(3).setMinWidth(160);
		tabla.getColumnModel().getColumn(3).setMaxWidth(180);
		tabla.getColumnModel().getColumn(4).setMinWidth(120);
		tabla.getColumnModel().getColumn(4).setMaxWidth(180);
		tabla.getColumnModel().getColumn(5).setMinWidth(100);
	    tabla.getColumnModel().getColumn(5).setMaxWidth(100);
		tabla.getColumnModel().getColumn(6).setMinWidth(90);
		tabla.getColumnModel().getColumn(6).setMaxWidth(100);
		
	}
   	
   	
	public static void main(String[] args) {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Control_De_Pedidos_De_Monedas().setVisible(true);
		}catch(Exception e){	}		
	}
	
	public class CapturarPedido extends Cat_Pedido_De_Monedas{
		
		public CapturarPedido(int folioEmp,String empleado, String status_pedido, String tipo_usuario){
			activarColumna = status_pedido;
			
//			pedirRecibir = true     --->   activar recibir pedido	
//			pedirRecibir = true     --->   activar realizar pedido
			boolean entregoMonedas = false;
			switch(tipo_usuario){
				case "CAJERA":	this.setTitle(status_pedido.equals("RECIBIDO")?"Recibir Pedido De Monedas ("+tipo_usuario+")":"Realizar Pedido De Monedas ("+tipo_usuario+")");break;
				case "CORTES":	this.setTitle("Surtir Pedido De Monedas ("+tipo_usuario+")");	break;
				default:		this.setTitle(status_pedido.equals("RECIBIDO")?"Recibir Pedido De Monedas ("+tipo_usuario+")":"Realizar Pedido De Monedas ("+tipo_usuario+")");break;//	recibir por (ENCARGADO / CAJERA)
			}
			
			switch(status_pedido){
				case "PEDIDO":		entregoMonedas=false;	columna = 2;	break;
				case "SURTIDO":		entregoMonedas=false;	columna = 4;	break;
				case "ENTREGADO":	entregoMonedas=true;	columna = 6;	break;
				case "RECIBIDO":	entregoMonedas=true;	columna = 8;	break;
			}
			
			cmbEntrega.setEnabled(entregoMonedas);
			
			Constructor();
			calcularTotales();
			
			this.lblEmpleado.setText("EMPLEADO:  "+folioEmp+"  "+empleado);
			agregar(tabla);
			tablaKey(status_pedido.equals("PEDIDO")?txtTotalPedido:( status_pedido.equals("SURTIDO")?txtTotalSurtido:( status_pedido.equals("ENTREGADO")?txtTotalEntregado:( txtTotalRecibido ) ) ));
		    guardar(btn_guardar, folioEmp, status_pedido, status_pedido.equals("PEDIDO")?txtTotalPedido:( status_pedido.equals("SURTIDO")?txtTotalSurtido:( status_pedido.equals("ENTREGADO")?txtTotalEntregado:( txtTotalRecibido ) )));
		    
		}
		
		private void agregar(final JTable tbl) {
	        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
		        public void mouseClicked(MouseEvent e) {
		        	fila= tbl.getSelectedRow();
		        	tabla.getSelectionModel().setSelectionInterval(fila, fila);
		        	tabla.setEnabled(true);
		        	tabla.editCellAt(fila, columna);
	    			Component aComp=tabla.getEditorComponent();
	    			aComp.requestFocus();
		        }
	        });
	    }
		
		private void guardar(final JButton btn,final int folioEmpleado, final String status_pedido, final JTextField txt) {
			btn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					if(activarColumna.equals("ENTREGADO") || activarColumna.equals("RECIBIDO")){
						if(cmbEntrega.getSelectedIndex()==0){
							JOptionPane.showMessageDialog(null, "Favor De Seleccionar Quien Le Entrego El Pedido","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
							return;
						}else{
							guardado(btn, folioEmpleado, status_pedido, txt);
						}
					}else{
						guardado(btn, folioEmpleado, status_pedido, txt);
					}
					
					if(activarColumna.equals("SURTIDO") || activarColumna.equals("ENTREGADO")){
						llenarTablaDePedidos();
					}
					
				}
			});
	    }
	}
	
}
