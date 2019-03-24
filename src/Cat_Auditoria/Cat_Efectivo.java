package Cat_Auditoria;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;

import Obj_Principal.JCButton;
import Obj_Renders.tablaRenderer;

//guardar denominaciones
@SuppressWarnings("serial")
public class Cat_Efectivo extends JDialog {
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	int filaEfec = 0;
	int columnaEfect = 4;
	
	public JToolBar menu_toolbar = new JToolBar();
	JButton btn_guardar=  new JCButton("Guardar","Guardar.png","Azul");
	
	JLabel lblEmpleado = new JLabel();
	JTextField txtTotal = new JTextField();
	
	DefaultTableModel tabla_model_efectivo = new DefaultTableModel( null, new String[]{"Folio", "Denominacion","# Denominacion", "Valor", "$ Cantidad"}) {
	     @SuppressWarnings("rawtypes")
		Class[] types = new Class[]{
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
        	 	case 3 : return false; 
        	 	case 4 :
        	 		float suma = 0;
	    			for(int i=0; i<tabla_efectivo.getRowCount(); i++){
	    				if(tabla_model_efectivo.getValueAt(i,4).toString().length() == 0){
	    					suma = suma + 0;
	    				}else{
	    					if(isNumeric(tabla_model_efectivo.getValueAt(i,4).toString().trim())){
		    					suma += (Float.parseFloat(tabla_model_efectivo.getValueAt(i,4).toString()))*(Float.parseFloat(tabla_model_efectivo.getValueAt(i,2).toString())*Float.parseFloat(tabla_model_efectivo.getValueAt(i,3).toString()));
							}else{
								JOptionPane.showMessageDialog(null, "La cantidad en el Folio "+tabla_model_efectivo.getValueAt(i,0).toString()+"  están mal en su formato:\n","Error",JOptionPane.ERROR_MESSAGE);
								tabla_model_efectivo.setValueAt("", i, 4);
							}
	    				}
	    			}
	    			txtTotal.setText(suma+"");
        	 		return true; 
        	 } 				
 			return false;
 		}
	};
	
	JTable tabla_efectivo = new JTable(tabla_model_efectivo);
	JScrollPane scroll_tabla = new JScrollPane(tabla_efectivo);
	
	public Cat_Efectivo(){
//		cont.setBackground(new Color(86,161,85));
		Constructor();
		
//      asigna el foco al JTextField deseado al arrancar la ventana
        this.addWindowListener(new WindowAdapter() {
                public void windowOpened( WindowEvent e ){
                	tabla_efectivo.setEnabled(true);
        			tabla_efectivo.editCellAt(filaEfec, columnaEfect);
        			Component aComp=tabla_efectivo.getEditorComponent();
        			aComp.requestFocus();
             }
        });
	}
	
	public void Constructor(){
		this.setModal(true);
		this.setTitle("Alimentación de Efectivo");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/dinero-icono-8797-48.jpg"));
		
		lblEmpleado.setForeground(Color.BLACK);
		
		this.panel.add(menu_toolbar).setBounds(0,0,150,25);
		this.panel.add(lblEmpleado).setBounds(30,25,350,20);
		this.panel.add(scroll_tabla).setBounds                      ( 20 ,45  ,455,270);
		this.panel.add(new JLabel("Total de Cantidades:")).setBounds(270 ,315 ,100, 20);
		this.panel.add(txtTotal).setBounds                          (380 ,315 , 90, 20);
		
		this.menu_toolbar.add(btn_guardar);
		this.menu_toolbar.setEnabled(false);
		this.txtTotal.setEditable(false);
		
		this.init_tabla();
		
		this.cont.add(panel);
		
		this.setSize(500,380);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	public void init_tabla(){
    	this.tabla_efectivo.getColumnModel().getColumn(0).setMaxWidth(50);
    	this.tabla_efectivo.getColumnModel().getColumn(0).setMinWidth(50);		
    	this.tabla_efectivo.getColumnModel().getColumn(1).setMaxWidth(120);
    	this.tabla_efectivo.getColumnModel().getColumn(1).setMinWidth(120);
    	this.tabla_efectivo.getColumnModel().getColumn(2).setMaxWidth(100);
    	this.tabla_efectivo.getColumnModel().getColumn(2).setMinWidth(100);		
    	this.tabla_efectivo.getColumnModel().getColumn(3).setMaxWidth(80);
    	this.tabla_efectivo.getColumnModel().getColumn(3).setMinWidth(80);
    	this.tabla_efectivo.getColumnModel().getColumn(4).setMaxWidth(100);
    	this.tabla_efectivo.getColumnModel().getColumn(4).setMinWidth(100);
    	
       tablaRender(tabla_efectivo);
		
		float suma = 0;
		for(int i=0; i<tabla_efectivo.getRowCount(); i++){
			if(tabla_model_efectivo.getValueAt(i,4).toString().length() == 0){
				suma = suma + 0;
			}else{
				suma += Float.parseFloat(tabla_model_efectivo.getValueAt(i,4).toString())*(Float.parseFloat(tabla_model_efectivo.getValueAt(i,2).toString())*Float.parseFloat(tabla_model_efectivo.getValueAt(i,3).toString()));
			}
		}
		txtTotal.setText(suma+"");
    }
	
public void tablaRender(JTable tbl){
		tbl.getTableHeader().setReorderingAllowed(false) ;
		tbl.setAutoResizeMode(JTable.AUTO_RESIZE_OFF );
		for(int i=0; i<tbl.getColumnCount(); i++){
			tbl.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",12));
		}
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
    
}
