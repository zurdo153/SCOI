package Cat_Auditoria;

import java.awt.Color;
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

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import Conexiones_SQL.BuscarTablasModel;
import Obj_Auditoria.Obj_Alimentacion_Denominacion;
import Obj_Auditoria.Obj_Pedido_De_Monedas;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Pedido_De_Monedas extends JDialog {
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	public JToolBar menu_toolbar = new JToolBar();
	JButton btn_guardar= new JButton(new ImageIcon("Iconos/save_icon&16.png"));
	
	JLabel lblEmpleado = new JLabel();
	JTextField txtTotal = new JTextField();
	
	String columnNames[] = { "Moneda", "Valor De Bolsa", "Pedido De Bolsas", "Total De Pedido"};
	public String[][] dataValues(){return new BuscarTablasModel().denominaciones_pedido_de_monedas();}
	
	DefaultTableModel tabla_model_depocitos = new DefaultTableModel(dataValues(), columnNames) {
	     @SuppressWarnings("rawtypes")
		Class[] types = new Class[]{
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
        	 	case 2 : return true; 
        	 	case 3 : return false; 
        	 } 				
 			return false;
 		}
	};
	
	JTable tabla_depositos = new JTable(tabla_model_depocitos);
	JScrollPane scroll_tabla_depositos = new JScrollPane(tabla_depositos);
	
	int filaDep = 0;
	int columnaDep = 2;
	
	public Cat_Pedido_De_Monedas(int folioEmp,String empleado, String status_pedido, String tipo_usuario, boolean pedirRecibir){
		
//		pedirRecibir = true     --->   activar recibir pedido	
//		pedirRecibir = true     --->   activar realizar pedido
		
		switch(tipo_usuario){
			case "CAJERA":	this.setTitle(pedirRecibir?"Recibir Pedido De Monedas":"Realizar Pedido De Monedas");break;
			case "CORTES":	this.setTitle("Surtir Pedido De Monedas");	break;
			default:		this.setTitle("Recibir Pedido De Monedas");	break;//	recibir por (ENCARGADO / CAJERA)
		}
		
		Constructor();
		this.lblEmpleado.setText("EMPLEADO:  "+folioEmp+"  "+empleado);
		agregar(tabla_depositos);
	    guardar(btn_guardar, folioEmp, status_pedido);
		
//      asigna el foco al JTextField deseado al arrancar la ventana
        this.addWindowListener(new WindowAdapter() {
                public void windowOpened( WindowEvent e ){
                	tabla_depositos.setEnabled(true);
        			tabla_depositos.editCellAt(filaDep, columnaDep);
        			Component aComp=tabla_depositos.getEditorComponent();
        			aComp.requestFocus();
        			
             }
        });
	}
	
	public void Constructor(){
		this.setModal(true);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/captura_nomina_icon&16.png"));
		
		lblEmpleado.setForeground(Color.GRAY);
		
		this.panel.add(menu_toolbar).setBounds(0,0,200,25);
		this.panel.add(lblEmpleado).setBounds(30,35,350,20);
		
		this.panel.add(scroll_tabla_depositos).setBounds(20,60,400,420);
		
		this.panel.add(new JLabel("Total de Cantidades:")).setBounds(220,485,100,20);
		this.panel.add(txtTotal).setBounds(330,485,90,20);
		
		this.menu_toolbar.add(btn_guardar);
		this.menu_toolbar.setEnabled(true);
		this.txtTotal.setEditable(false);
		
		this.init_tabla();
		
		this.cont.add(panel);
		
		this.tabla_depositos.addKeyListener(op_key);
//		this.addWindowListener(op_limpiar);
		
		
		//  guardar al presionar la tecla f2
	    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
	       KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0), "foco");
	    
	    getRootPane().getActionMap().put("foco", new AbstractAction(){
		        public void actionPerformed(ActionEvent e)
		        {
		        	btn_guardar.doClick();    	
		        }
	    });
	    
	    filaDep=0;
	    tabla_depositos.getSelectionModel().setSelectionInterval(filaDep, filaDep);
		
		this.setSize(450,550);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	private void agregar(final JTable tbl) {
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
	        public void mouseClicked(MouseEvent e) {
	        	filaDep= tbl.getSelectedRow();
	        	tabla_depositos.getSelectionModel().setSelectionInterval(filaDep, filaDep);
            	tabla_depositos.setEnabled(true);
    			tabla_depositos.editCellAt(filaDep, columnaDep);
    			Component aComp=tabla_depositos.getEditorComponent();
    			aComp.requestFocus();
	        }
        });
    }
	
	private void guardar(final JButton btn,final int folioEmpleado, final String status_pedido) {
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
//				System.out.println(folioEmpleado);
//				System.out.println(estab);
//				System.out.println(tipoUsuario);
				
				if(tabla_depositos.isEditing()){
					tabla_depositos.getCellEditor().stopCellEditing();
					tabla_depositos.addKeyListener(op_key);
				}
				
				if(valida_tabla() != ""){
					txtTotal.setText("0.0");
					JOptionPane.showMessageDialog(null, "Se a ingresado un valor no numerico en la tabla","Error",JOptionPane.ERROR_MESSAGE);
					return;
				}else{
						
					if(Float.valueOf(txtTotal.getText().equals("")?"0":txtTotal.getText())>0){
						
							if(JOptionPane.showConfirmDialog(null, "¿Desea guardar la lista de Denominaciones?") == 0){
								Obj_Pedido_De_Monedas pedido = new Obj_Pedido_De_Monedas();
								
								pedido.setFolioUsuario(folioEmpleado);
								pedido.setStatus_pedido(status_pedido);
								pedido.setMatriz(tabla_guardar());
	
//								for(int i=0; i<tabla_guardar().length; i++){
//									System.out.print(tabla_guardar()[i][0].toString()+"  ");
//									System.out.print(tabla_guardar()[i][1].toString()+"  ");
//									System.out.print(tabla_guardar()[i][2].toString()+"  ");
//									System.out.println(tabla_guardar()[i][3].toString());
//									
//								}
								
								if(pedido.guardar()){
								
									CalcularImporte();
//									calculoDinamico();
									dispose();
									
//									new Cat_Reporte_De_Depositos_Cortes(lblFolio_Corte.getText().trim());
//									JOptionPane.showMessageDialog(null, "La tabla Depodito se guardó exitosamente","Aviso",JOptionPane.INFORMATION_MESSAGE);
//									return;
								}else{
									txtTotal.setText("0.0");
									JOptionPane.showMessageDialog(null, "Ocurrió un error al intentar modificar la tabla","Error",JOptionPane.ERROR_MESSAGE);
									return;
								}
							}else{
								return;
							}
							
					}else{
						JOptionPane.showMessageDialog(null, "No Se Puede Guardar En Movimiento En Ceros","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
						return;
					}
				}
			}
		});
    }
	
	KeyListener op_key = new KeyListener() {
		public void keyTyped(KeyEvent e) {}
		public void keyReleased(KeyEvent e) {
			if(CalcularImporte()==false){
				tabla_depositos.getSelectionModel().setSelectionInterval(filaDep, filaDep);
					JOptionPane.showMessageDialog(null, "Se introdujo un valor no valido","Aviso",JOptionPane.INFORMATION_MESSAGE);
					tabla_depositos.setValueAt(0, filaDep, columnaDep);
					return;
			}
		}
		public void keyPressed(KeyEvent e) {}
	};
	
	public boolean CalcularImporte(){
		boolean valor=false;
		if(Validar(filaDep,columnaDep)){
			
			int cantidadDeFilas = tabla_depositos.getRowCount();
			filaDep+=1;

				if(filaDep == cantidadDeFilas){	filaDep=0;		}
				
				tabla_depositos.getSelectionModel().setSelectionInterval(filaDep, filaDep);
            	tabla_depositos.setEnabled(true);
    			tabla_depositos.editCellAt(filaDep, columnaDep);
    			Component aComp=tabla_depositos.getEditorComponent();
    			aComp.requestFocus();
    			
				float suma = 0;
				for(int i=0; i<tabla_depositos.getRowCount(); i++){
					
						if(isNumeric(tabla_model_depocitos.getValueAt(i,2).toString().trim())){
							
								float totalRow = (/*Float.parseFloat(tabla_model_depocitos.getValueAt(i,0).toString())**/
													 Float.parseFloat(tabla_model_depocitos.getValueAt(i,1).toString())*
													 Float.parseFloat(tabla_model_depocitos.getValueAt(i,2).toString().trim().equals("")?"0":tabla_model_depocitos.getValueAt(i,2).toString().trim()));
								tabla_model_depocitos.setValueAt(totalRow==0?"":totalRow, i, 3);
		
								suma += Float.parseFloat(tabla_model_depocitos.getValueAt(i,3).toString().trim().equals("")?"0":tabla_model_depocitos.getValueAt(i,3).toString().trim())	;
						}else{
								JOptionPane.showMessageDialog(null, "La cantidad en la Moneda "+tabla_model_depocitos.getValueAt(i,0).toString()+"  están mal en su formato:\n","Error",JOptionPane.ERROR_MESSAGE);
								tabla_model_depocitos.setValueAt("", i, 2);
								tabla_model_depocitos.setValueAt("", i, 3);
						}
				}
				txtTotal.setText(suma+"");
				valor = true;
		}
		return valor;
	}
	
	@SuppressWarnings("unused")
	private boolean Validar(int fila, int columna) { 
		String valor=""; 
		
			if(tabla_depositos.getValueAt(fila,columna)==null) { 
				return false; 
			}else{ 
				try{
					return true;
				}catch(NumberFormatException e){
				 return false;
				}
			} 
	}
	
	private Object[][] tabla_guardar(){
		
		Object[][] matriz = new Object[tabla_depositos.getRowCount()][4];
		
		for(int i=0; i<tabla_depositos.getRowCount(); i++){
			
			matriz[i][0] = tabla_model_depocitos.getValueAt(i,0).toString().trim();
			matriz[i][1] = tabla_model_depocitos.getValueAt(i,1).toString().trim();
			matriz[i][2] = tabla_model_depocitos.getValueAt(i,2).toString().trim().equals("")?"0":tabla_model_depocitos.getValueAt(i,2).toString().trim();
			matriz[i][3] = tabla_model_depocitos.getValueAt(i,3).toString().trim().equals("")?"0":tabla_model_depocitos.getValueAt(i,3).toString().trim();
			
		}
		return matriz;
	}
	
	private String valida_tabla(){
		String error = "";
		for(int i=0; i<tabla_depositos.getRowCount(); i++){
			try{
				if(!isNumeric(tabla_model_depocitos.getValueAt(i,1).toString())){
					error += "   La celda de la columna Cantidad no es un número en el [Folio: "+tabla_model_depocitos.getValueAt(i,0)+"]\t\n";
					tabla_model_depocitos.setValueAt("",i, 1);
				}
			} catch(Exception e){
				JOptionPane.showMessageDialog(null, "La tabla tiene una celda con texto en lugar de un valor numérico: \n"+e,"Error",JOptionPane.ERROR_MESSAGE);
			}
		}
		return error;
	}
	
	public void init_tabla(){
		this.tabla_depositos.getTableHeader().setReorderingAllowed(false) ;
		
		tabla_depositos.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",12));
		tabla_depositos.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",12));
		tabla_depositos.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",12));
		tabla_depositos.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",12));
		
    	this.tabla_depositos.getColumnModel().getColumn(0).setMaxWidth(100);
    	this.tabla_depositos.getColumnModel().getColumn(0).setMinWidth(100);		
    	this.tabla_depositos.getColumnModel().getColumn(1).setMaxWidth(130);
    	this.tabla_depositos.getColumnModel().getColumn(1).setMinWidth(130);
    	
    	this.tabla_depositos.getColumnModel().getColumn(2).setMaxWidth(80);
    	this.tabla_depositos.getColumnModel().getColumn(2).setMinWidth(80);		
    	this.tabla_depositos.getColumnModel().getColumn(3).setMaxWidth(130);
    	this.tabla_depositos.getColumnModel().getColumn(3).setMinWidth(130);
    	
		float suma = 0;
		txtTotal.setText(suma+"");
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
    
    public static boolean pedirRecibir(){return new BuscarTablasModel().checar_Pedido_De_Monedas_Cajero();}
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Pedido_De_Monedas(491,"EDGAR EDUARDO JIMENEZ MOLINA", "PEDIDO", "CAJERA", pedirRecibir()).setVisible(true);
		}catch(Exception e){	}	}

}