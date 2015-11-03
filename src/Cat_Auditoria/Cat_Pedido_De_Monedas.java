package Cat_Auditoria;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;

import Cat_Auditoria.Cat_Notas;
import Conexiones_SQL.BuscarTablasModel;
import Conexiones_SQL.Cargar_Combo;
import Obj_Auditoria.Obj_Pedido_De_Monedas;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Pedido_De_Monedas extends JDialog {
	
	String nota = "";
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	public JToolBar menu_toolbar = new JToolBar();
	JButton btn_guardar= new JButton(new ImageIcon("Iconos/save_icon&16.png"));
	
	JButton btnNota = new JButton("Nota", new ImageIcon("imagen/nota16.png"));
	
	public String[] empleadosAutorizadosParaEnrtegar(){try {return new Cargar_Combo().entregoMonedas();} catch (SQLException e) {e.printStackTrace();}return null;}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbEntrega = new JComboBox(empleadosAutorizadosParaEnrtegar());
	
	JLabel lblEmpleado = new JLabel();
	JTextField txtTotalPedido 		= new JTextField();
	JTextField txtTotalSurtido 		= new JTextField();
	JTextField txtTotalEntregado 	= new JTextField();
	JTextField txtTotalRecibido 	= new JTextField();
	
	String activarColumna = "";
	
	String columnNames[] = { "Moneda", "Valor De Bolsa", "Pedido De Bolsas", "Total De Pedido", "Surtido De Bolsas", "Total Surtido", "Entregado De Bolsas", "Total Entregado", "Recibo De Bolsas", "Total Recibido"};
	public String[][] dataValues(){return new BuscarTablasModel().denominaciones_pedido_de_monedas();}
	
	DefaultTableModel modelo = new DefaultTableModel(dataValues(), columnNames) {
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
	     	 	case 2 : return activarColumna.equals("PEDIDO")?true:false; 
	     	 	case 3 : return false;
	    	 	case 4 : return activarColumna.equals("SURTIDO")?true:false;
	    	 	case 5 : return false;
        	 	case 6 : return activarColumna.equals("ENTREGADO")?true:false;
        	 	case 7 : return false;
	    	 	case 8 : return activarColumna.equals("RECIBIDO")?true:false;
        	 	case 9 : return false;
        	 } 				
 			return false;
 		}
	};
	
	JTable tabla = new JTable(modelo);
	JScrollPane scroll = new JScrollPane(tabla);
	
	int fila = 0;
	int columna = 2;
	
	public Cat_Pedido_De_Monedas(){
		
//      asigna el foco al JTextField deseado al arrancar la ventana
        this.addWindowListener(new WindowAdapter() {
                public void windowOpened( WindowEvent e ){
                	tabla.setEnabled(true);
                	tabla.editCellAt(fila, columna);
        			Component aComp=tabla.getEditorComponent();
        			aComp.requestFocus();
             }
        });
	}
	
	public void Constructor(){
		this.setModal(true);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/captura_nomina_icon&16.png"));
		
		lblEmpleado.setForeground(Color.GRAY);
		
		this.panel.add(menu_toolbar).setBounds(0,0,200,25);
		this.panel.add(lblEmpleado).setBounds(15,60,350,20);
		
		this.panel.add(scroll).setBounds(15,80,960,200);
		
		this.panel.add(btnNota).setBounds(315,5,90,20);
		
		this.panel.add(new JLabel("Entrego Pedido: ")).setBounds(15,30,100,20);
		this.panel.add(cmbEntrega).setBounds(105,30,300,20);
		
		this.panel.add(new JLabel("Totales:")).setBounds(220,280,100,20);
		this.panel.add(txtTotalPedido).setBounds(275,280,90,20);   
		this.panel.add(txtTotalSurtido).setBounds(465,280,80,20);
		this.panel.add(txtTotalEntregado).setBounds(665,280,100,20);
		this.panel.add(txtTotalRecibido).setBounds(865,280,90,20); 	
		                                                            
		this.menu_toolbar.add(btn_guardar);                          
		this.menu_toolbar.setEnabled(true);
		this.txtTotalPedido.setEditable(false);
		this.txtTotalSurtido.setEditable(false);
		this.txtTotalEntregado.setEditable(false);
		this.txtTotalRecibido.setEditable(false);
		                                                               
		this.init_tabla();                                              
		
		this.cont.add(panel);
		
//		this.tabla.addKeyListener(op_key);
		
		this.btnNota.addActionListener(opNota);		
		
		//  guardar al presionar la tecla f5
	    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
	       KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0), "foco");
	    
	    getRootPane().getActionMap().put("foco", new AbstractAction(){
		        public void actionPerformed(ActionEvent e)
		        {
		        	btn_guardar.doClick();    	
		        }
	    });
	    
	    fila=0;
	    tabla.getSelectionModel().setSelectionInterval(fila, fila);
		
		this.setSize(995,350);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
//		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	public void calcularTotales(){
		
		float sumaPedido = 0;
		float sumaSurtido = 0;
		float sumaEntregado = 0;
		float sumaRecibido = 0;
		for(int i=0; i<tabla.getRowCount(); i++){
			
			sumaPedido 		+= Float.parseFloat(modelo.getValueAt(i,3).toString().trim().equals("")?"0":modelo.getValueAt(i,3).toString().trim());
			sumaSurtido 	+= Float.parseFloat(modelo.getValueAt(i,5).toString().trim().equals("")?"0":modelo.getValueAt(i,5).toString().trim());
			sumaEntregado 	+= Float.parseFloat(modelo.getValueAt(i,7).toString().trim().equals("")?"0":modelo.getValueAt(i,7).toString().trim());
			sumaRecibido  	+= Float.parseFloat(modelo.getValueAt(i,9).toString().trim().equals("")?"0":modelo.getValueAt(i,9).toString().trim());
		}
		txtTotalPedido.setText(sumaPedido+"");
		txtTotalSurtido.setText(sumaSurtido+"");
		txtTotalEntregado.setText(sumaEntregado+"");
		txtTotalRecibido.setText(sumaRecibido+"");
	}
	
	ActionListener opNota = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			new Cat_notas(nota).setVisible(true);
		}
	};
	

	
	public void guardado(final JButton btn,final int folioEmpleado, final String status_pedido, final JTextField txt){
		if(tabla.isEditing()){
			tabla.getCellEditor().stopCellEditing();
		}
		
		if(valida_tabla() != ""){
			JOptionPane.showMessageDialog(null, "Se a ingresado un valor no numerico en la tabla","Error",JOptionPane.ERROR_MESSAGE);
			return;
		}else{
				
			if(Float.valueOf(txt.getText().equals("")?"0":txt.getText())>0){       
						Obj_Pedido_De_Monedas pedido = new Obj_Pedido_De_Monedas();                      
						                                                                                 
						pedido.setFolioUsuario(folioEmpleado);
						pedido.setStatus_pedido(status_pedido);
						pedido.setMatriz(tabla_guardar());
						pedido.setObservacion(nota);
						pedido.setEmpleado_entrego(cmbEntrega.getSelectedItem().toString().trim());

						if(pedido.guardar()){
							
//							new Cat_Reporte_De_Depositos_Cortes(lblFolio_Corte.getText().trim());
							JOptionPane.showMessageDialog(null, "El Registro Se Guardó Exitosamente","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
//							return;
							dispose();
							
						}else{
							JOptionPane.showMessageDialog(null, "Ocurrió un error al intentar modificar la tabla","Error",JOptionPane.ERROR_MESSAGE);
							return;
						}
					
			}else{
				JOptionPane.showMessageDialog(null, "No Se Puede Guardar En Movimiento En Ceros","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
				return;
			}
		}
	}
	
	
	public void tablaKey(final JTextField txt){
		tabla.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {
				if(CalcularImporte(txt)==false){
					tabla.getSelectionModel().setSelectionInterval(fila, fila);
						JOptionPane.showMessageDialog(null, "Se Introdujo Un Valor Incorrecto, Solo Se Permiten Numeros Enteros","Aviso",JOptionPane.INFORMATION_MESSAGE);
						tabla.setValueAt(0, fila, columna);
						return;
				}
//				tabla.setValueAt("", fila-1, columna);
			}
			public void keyPressed(KeyEvent e) {}
		});
	}
	
	public boolean CalcularImporte(final JTextField textFiel){
		
		boolean valor=false;
		
		if(Validar(fila,columna)){
			
			int cantidadDeFilas = tabla.getRowCount();
			fila+=1;

				if(fila == cantidadDeFilas){	fila=0;		}
				
				tabla.getSelectionModel().setSelectionInterval(fila, fila);
				tabla.setEnabled(true);
				tabla.editCellAt(fila, columna);
    			Component aComp=tabla.getEditorComponent();
    			aComp.requestFocus();
    			
				float suma = 0;
				
				for(int i=0; i<tabla.getRowCount(); i++){
					
						if(isNumeric(modelo.getValueAt(i,columna).toString().trim())){
							
								float totalRow = (/*Float.parseFloat(modelo.getValueAt(i,0).toString())**/
													 Float.parseFloat(modelo.getValueAt(i,1).toString())*
													 Float.parseFloat(modelo.getValueAt(i,columna).toString().trim().equals("")?"0":modelo.getValueAt(i,columna).toString().trim()));
								modelo.setValueAt(totalRow==0?"":totalRow, i, (columna+1));
		
								suma += Float.parseFloat(modelo.getValueAt(i,(columna+1)).toString().trim().equals("")?"0":modelo.getValueAt(i,(columna+1)).toString().trim())	;
						}else{
								JOptionPane.showMessageDialog(null, "La cantidad en la Moneda "+modelo.getValueAt(i,0).toString()+"  están mal en su formato:\n","Error",JOptionPane.ERROR_MESSAGE);
								modelo.setValueAt("", i, columna);
								modelo.setValueAt("", i, (columna+1));
						}
				}
				textFiel.setText(suma+"");
				valor = true;
		}
		return valor;
	}
	
	private boolean Validar(int fila, int columna) {
		try {
//    		if(tabla.getValueAt(fila,columna)==null){
//        		return true;
//    		}else{
    			Integer.valueOf(tabla.getValueAt(fila,columna).toString().equals("")?"0":tabla.getValueAt(fila,columna).toString());
        		return true;
//    		}
    	} catch (NumberFormatException nfe){
    		return false;
    	}
	}
	
	private Object[][] tabla_guardar(){
		
		Object[][] matriz = new Object[tabla.getRowCount()][4];
		
		for(int i=0; i<tabla.getRowCount(); i++){
			
			matriz[i][0] = modelo.getValueAt(i,0).toString().trim();
			matriz[i][1] = modelo.getValueAt(i,1).toString().trim();
			matriz[i][2] = modelo.getValueAt(i,columna).toString().trim().equals("")?"0":modelo.getValueAt(i,columna).toString().trim();
			matriz[i][3] = modelo.getValueAt(i,(columna+1)).toString().trim().equals("")?"0":modelo.getValueAt(i,(columna+1)).toString().trim();
			
		}
		return matriz;
	}
	
	private String valida_tabla(){
		String error = "";
		for(int i=0; i<tabla.getRowCount(); i++){
			try{
				if(!isNumeric(modelo.getValueAt(i,1).toString())){
					error += "   La celda de la columna Cantidad no es un número en el [Folio: "+modelo.getValueAt(i,0)+"]\t\n";
					modelo.setValueAt("",i, 1);
				}
			} catch(Exception e){
				JOptionPane.showMessageDialog(null, "La tabla tiene una celda con texto en lugar de un valor numérico: \n"+e,"Error",JOptionPane.ERROR_MESSAGE);
			}
		}
		return error;
	}
	
	public void init_tabla(){
		this.tabla.getTableHeader().setReorderingAllowed(false) ;
		
		tabla.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",12));
		tabla.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",12));
		tabla.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",12));
		tabla.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",12));
		tabla.getColumnModel().getColumn(4).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",12));
		tabla.getColumnModel().getColumn(5).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",12));
		tabla.getColumnModel().getColumn(6).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",12));
		
		tabla.getColumnModel().getColumn(7).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",12));
		tabla.getColumnModel().getColumn(8).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",12));
		tabla.getColumnModel().getColumn(9).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",12));
		
    	this.tabla.getColumnModel().getColumn(0).setMaxWidth(70);
    	this.tabla.getColumnModel().getColumn(0).setMinWidth(70);		
    	this.tabla.getColumnModel().getColumn(1).setMaxWidth(90);
    	this.tabla.getColumnModel().getColumn(1).setMinWidth(90);
    	this.tabla.getColumnModel().getColumn(2).setMaxWidth(100);
    	this.tabla.getColumnModel().getColumn(2).setMinWidth(100);		
    	this.tabla.getColumnModel().getColumn(3).setMaxWidth(90);
    	this.tabla.getColumnModel().getColumn(3).setMinWidth(90);
    	this.tabla.getColumnModel().getColumn(4).setMaxWidth(100);
    	this.tabla.getColumnModel().getColumn(4).setMinWidth(100);
    	this.tabla.getColumnModel().getColumn(5).setMaxWidth(80);
    	this.tabla.getColumnModel().getColumn(5).setMinWidth(80);		
    	this.tabla.getColumnModel().getColumn(6).setMaxWidth(120);
    	this.tabla.getColumnModel().getColumn(6).setMinWidth(120);
    	
    	this.tabla.getColumnModel().getColumn(7).setMaxWidth(100);
    	this.tabla.getColumnModel().getColumn(7).setMinWidth(100);
    	this.tabla.getColumnModel().getColumn(8).setMaxWidth(100);
    	this.tabla.getColumnModel().getColumn(8).setMinWidth(100);		
    	this.tabla.getColumnModel().getColumn(9).setMaxWidth(90);
    	this.tabla.getColumnModel().getColumn(9).setMinWidth(90);
    	
		float suma = 0;
		txtTotalPedido.setText(suma+"");
		txtTotalSurtido.setText(suma+""); 	
		txtTotalEntregado.setText(suma+""); 
		txtTotalRecibido.setText(suma+""); 
    }
	
    private boolean isNumeric(String cadena){
    	try {
    		if(cadena.equals("")){
        		return true;
    		}else{
    			Float.parseFloat(cadena);
        		return true;
    		}
    	} catch (NumberFormatException nfe){
    		return false;
    	}
    }
    
//	public static void main(String args[]){
//		try{
//			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//			new Cat_Pedido_De_Monedas(491,"EDGAR EDUARDO JIMENEZ MOLINA", "RECIBIDO", "CAJERA").setVisible(true);
//		}catch(Exception e){	}	
//	}
	
	public class Cat_notas extends Cat_Notas{
		
		public Cat_notas(String nta){
			txaNota.setText(nta);
			
			btnAgregar.addActionListener(opAgregar);
		}
		
		ActionListener opAgregar = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				nota = txaNota.getText().toString().toUpperCase().trim();
				dispose();
			}
		};
	}
	
}