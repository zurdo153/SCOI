package Cat_Compras;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.GuardarSQL;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_Filtro_Dinamico_Plus;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Cambio_De_Estatus_De_Produnto_En_Establecimiento extends JFrame{

	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JTextField txtFolio = new Componentes().text(new JCTextField(), "Cod. Prod.", 15, "Int");
	JTextField txtDescripcion = new Componentes().text(new JCTextField(), "Producto", 15, "Int");
	
	String[] status ={"VIGENTE","CANCELADO"};
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbStatus = new JComboBox(status);
	
	JButton btnBuscar = new JCButton("", "busca.png","Azul");
	JButton btnGuardar = new JCButton("Guardar", "Guardar.png","Azul");
	
	public Cat_Cambio_De_Estatus_De_Produnto_En_Establecimiento() {

		this.setTitle("Modificacion De Status De Producto En Establecimiento");
		this.panel.setBorder(BorderFactory.createTitledBorder( "Modificacion De Status De Producto En Establecimiento"));
		this.setSize(470,130);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		
		int y=20;
		panel.add(new JLabel("Folio:")).setBounds(15,y,60,20);
		panel.add(txtFolio).setBounds(80,y,80,20);
		panel.add(btnBuscar).setBounds(160,y,30,20);
		
		panel.add(new JLabel("Descripcion:")).setBounds(15,y+=25,60,20);
		panel.add(txtDescripcion).setBounds(80,y,360,20);
		
		panel.add(new JLabel("Status:")).setBounds(15,y+=25,60,20);
		panel.add(cmbStatus).setBounds(80,y,120,20);
		
		panel.add(btnGuardar).setBounds(340,y,100,20);
		
		cont.add(panel);
		
		txtDescripcion.setEditable(false);
		
		btnBuscar.addActionListener(opBuscar);
		txtFolio.addActionListener(opBuscar);
		
		btnGuardar.addActionListener(opGuardar);
		
		//  abre el filtro de busqueda de empleado al presionar la tecla f2
	    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
	       KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), "foco");
	    
	    getRootPane().getActionMap().put("foco", new AbstractAction(){
	        @Override
	        public void actionPerformed(ActionEvent e)
	        {	    
					new Cat_Filtro_De_Productos().setVisible(true);
	        }
	    });
	    
		
	}
	
	ActionListener opBuscar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		
			if(txtFolio.getText().trim().equals("")){
				new Cat_Filtro_De_Productos().setVisible(true);
			}else{
				
				if(new BuscarSQL().existeProductosEnEstablecimiento(txtFolio.getText().trim())){
					String[][] consulta = new BuscarSQL().getProductosEnEstablecimiento(txtFolio.getText().trim());
					txtDescripcion.setText(consulta[0][1]);
					cmbStatus.setSelectedItem(consulta[0][2]);
				}else{
					txtFolio.setText("");
					txtDescripcion.setText("");
					cmbStatus.setSelectedItem("VIGENTE");
					JOptionPane.showMessageDialog(null, "No Se A Encontrado Producto Con El Codigo Especificado", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
					return;
				}
			}
		}
	};
	
	ActionListener opGuardar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		
			if(txtDescripcion.getText().trim().equals("")){
				JOptionPane.showMessageDialog(null, "Favor De Buscar Un Producto", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}else{
					String[][] consulta = new BuscarSQL().getProductosEnEstablecimiento(txtFolio.getText().trim());
					
					if(!txtDescripcion.getText().trim().equals(consulta[0][1].toString().trim())){
						JOptionPane.showMessageDialog(null, "El Codigo De Producto No Corresponde, Favor De Buscarlo Nuevamente", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
						return;
					}else{
						if(new GuardarSQL().Guardar_Cambio_De_Status_De_Producto(txtFolio.getText().trim(),cmbStatus.getSelectedItem().toString())){
							JOptionPane.showMessageDialog(null, "El Status Se Modifico Correctamente","Aviso", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/aplicara-el-dialogo-icono-6256-32.png"));
							return;
						}else{
							JOptionPane.showMessageDialog(null, "No Se A Podido Realizar El Cambio De Status", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
							return;
						}
					}
					
					
			}
		}
	};
	
	public static void main(String[] args) {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Cambio_De_Estatus_De_Produnto_En_Establecimiento().setVisible(true);
		}catch(Exception e){
			System.err.println("Error :"+ e.getMessage());
		}
	}
	
	public class Cat_Filtro_De_Productos extends JDialog{

		Container cont_quitados = getContentPane();
		JLayeredPane panel_quitados = new JLayeredPane();
		
		JTextField txtFiltro 	= new Componentes().text(new JTextField(), "Filtro", 120, "String");
		
		 public DefaultTableModel model = new DefaultTableModel(new BuscarSQL().getProductosEnEstablecimiento(""), new String[]{"Folio","Descripcion", "Status"} ){
	         
				@SuppressWarnings({ "rawtypes" })
				Class[] types = new Class[]{
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
		                }
		                 return false;
		         }
		    };
			
		    JTable tabla = new JTable(model);
			JScrollPane scroll_filtro_scoi = new JScrollPane(tabla,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			
			
			DecimalFormat df = new DecimalFormat("#0.00");
			
			String establecimiento = "";

		public Cat_Filtro_De_Productos(){
			this.setModal(true);
			this.setTitle("Filtro De Productos");
			this.panel_quitados.setBorder(BorderFactory.createTitledBorder( "Filtro De Productos"));
			
			panel_quitados.add(txtFiltro 	    ).setBounds(20, 30, 745, 20);
			panel_quitados.add(scroll_filtro_scoi).setBounds(20, 50, 745, 200);
			
			cont_quitados.add(panel_quitados);
			
			render_filtro(tabla);
			filtro(tabla);
			agregar(tabla);
			
			
			this.setSize(790,300);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			
		}
		
		public void agregar(final JTable tb){
			tb.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if(e.getClickCount()==2){
						txtFolio.setText(tb.getValueAt(tabla.getSelectedRow(), 0).toString());
						txtDescripcion.setText(tb.getValueAt(tabla.getSelectedRow(), 1).toString());
						cmbStatus.setSelectedItem(tb.getValueAt(tabla.getSelectedRow(), 2).toString());
						dispose();
					}
				}
			});
		}
		public void filtro(final JTable tb){
			txtFiltro.addKeyListener(new KeyListener() {
				public void keyTyped(KeyEvent arg0) {}
				public void keyReleased(KeyEvent arg0){
					int[] columnas = {0,1,2};
					new Obj_Filtro_Dinamico_Plus(tabla,txtFiltro.getText().toUpperCase().trim(), columnas);
				}
				public void keyPressed(KeyEvent arg0) {}
			});
		}
		
		public void render_filtro(final JTable tb){
			for(int i = 0; i<tb.getColumnCount(); i++){
				switch(i){
						case 0: tb.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","derecha","Arial","negrita",11)); break;
						case 1: tb.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",11) ); break;
						default:tb.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","centro"	,"Arial","negrita",11)); break;
					}
			}
			
			tb.getTableHeader().setReorderingAllowed(false) ;
	    	tb.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	    	
	    	int x=40;
	    	
	    	tb.getColumnModel().getColumn(0 ).setMinWidth(x*2); 
	    	tb.getColumnModel().getColumn(1 ).setMinWidth(x*13);   
	    	tb.getColumnModel().getColumn(2 ).setMinWidth(x*3);
	    	                                          
		}
		
	}

}
