package Cat_Checador;

import java.awt.Container;
import java.awt.event.MouseEvent;

import javax.swing.JDialog;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Conexiones_SQL.BuscarSQL;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Checador_De_Transferencias extends JDialog  {
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	 public DefaultTableModel modelo = new DefaultTableModel(null,new String[]{"Transferencia","De","A","Status","Status Recepcion","Fecha Cancelacion","Usuario Cancelacion"}){
        @SuppressWarnings("rawtypes")
        Class[] types = new Class[]{
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
                         case 0  : return false; 
                         case 1  : return false; 
                         case 2  : return false; 
                         case 3  : return false;
                         case 4  : return false; 
                         case 5  : return false; 
                         case 6  : return false;
                 }
                  return false;
         }
     };
     
     JTable tabla = new JTable(modelo);
     JScrollPane Scroll = new JScrollPane(tabla);

	public Cat_Checador_De_Transferencias(String establecimiento, int folio_encargado){
		this.setModal(true);
		this.setSize(1024, 250);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		
		this.setTitle("Seleccionar Tranferencia");
		
		llamarRender();
		panel.add(Scroll).setBounds(20,20,980,180) ;
		
		llenarTabla(establecimiento);
		
		agregar(tabla,folio_encargado);
		
		cont.add(panel);

	}
	
   	private void llamarRender(){		
		tabla.getTableHeader().setReorderingAllowed(false) ;
		tabla.getColumnModel().getColumn(0).setMinWidth(80);
		tabla.getColumnModel().getColumn(1).setMinWidth(110);
		tabla.getColumnModel().getColumn(2).setMinWidth(110);
		tabla.getColumnModel().getColumn(3).setMinWidth(80);
		tabla.getColumnModel().getColumn(4).setMinWidth(80);
		tabla.getColumnModel().getColumn(5).setMinWidth(100);
		tabla.getColumnModel().getColumn(6).setMinWidth(100);
		
		tabla.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 	
		tabla.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
		tabla.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 	
		tabla.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
		tabla.getColumnModel().getColumn(4).setCellRenderer(new tablaRenderer("texto","centro","Arial","normal",12)); 	
		tabla.getColumnModel().getColumn(5).setCellRenderer(new tablaRenderer("texto","centro","Arial","normal",12)); 
		tabla.getColumnModel().getColumn(6).setCellRenderer(new tablaRenderer("texto","centro","Arial","normal",12)); 	
   	}
   	
	public void llenarTabla(String estab){
		Object[][] lista = new BuscarSQL().getTransferenciasPendientes(estab);
		
		for(Object[] row : lista){
			modelo.addRow(row);
		}
	}
	
	private void agregar(final JTable tbl, int folio_encargado) {
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
	        public void mouseClicked(MouseEvent e) {
	        	
	        	if(e.getClickCount() == 2){
	    			int fila = tabla.getSelectedRow();
	    			String status = tabla.getValueAt(fila, 3).toString().trim();
	    			String status_recepcion = tabla.getValueAt(fila, 4).toString().trim();
	    			if(status.equals("VIGENTE") && status_recepcion.equals("PENDIENTE")){
	    				
		    			String trasferencia = tabla.getValueAt(fila, 0).toString().trim();
		    			String estab_surte  = tabla.getValueAt(fila, 1).toString().trim();
		    			String estab_recibe = tabla.getValueAt(fila, 2).toString().trim();
		    			
	    				new Cat_Validar_Chofer_Para_Tranferencia(trasferencia,estab_surte,estab_recibe,folio_encargado).setVisible(true);

//		    			System.out.println("Folio De Transferencia Seleccionado: "+folio+"  ->   Abrir Catalogo De Choferes Para Asignacion De Transferencia");

	    			}else{
	    				JOptionPane.showMessageDialog(null, "Solo Se Pueden Mandar Las Transferencias Con Status Vigente Y Transferencia Pendiente","Aviso",JOptionPane.INFORMATION_MESSAGE);
	    				return;
	    			}
	    			
	        	}
	        }
        });
    }
	
	public static void main(String[] args) {
		new Cat_Checador_De_Transferencias("CEDIS",491).setVisible(true);
	}
}
