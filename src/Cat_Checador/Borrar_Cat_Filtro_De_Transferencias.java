package Cat_Checador;

import java.awt.Container;
import java.awt.event.MouseEvent;

import javax.swing.JDialog;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Borrar_Cat_Filtro_De_Transferencias extends JDialog  {
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	 public DefaultTableModel modeloFiltro = new DefaultTableModel(null,new String[]{"Transferencia","De","A","Status","Status Recepcion","Fecha Cancelacion","Usuario Cancelacion"}){
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
     
     JTable tablaFiltro = new JTable(modeloFiltro);
     JScrollPane ScrollFiltro = new JScrollPane(tablaFiltro);

	public Borrar_Cat_Filtro_De_Transferencias(String establecimiento, int folio_encargado){
		this.setModal(true);
		this.setSize(1024, 250);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		
		this.setTitle("Seleccionar Tranferencia");
		
		llamarRender();
		panel.add(ScrollFiltro).setBounds(20,20,980,180) ;
		
		llenarTablaFiltro(establecimiento);
		
		agregar(tablaFiltro,folio_encargado);
		
		cont.add(panel);

	}
	
   	private void llamarRender(){		
		tablaFiltro.getTableHeader().setReorderingAllowed(false) ;
		tablaFiltro.getColumnModel().getColumn(0).setMinWidth(80);
		tablaFiltro.getColumnModel().getColumn(1).setMinWidth(110);
		tablaFiltro.getColumnModel().getColumn(2).setMinWidth(110);
		tablaFiltro.getColumnModel().getColumn(3).setMinWidth(80);
		tablaFiltro.getColumnModel().getColumn(4).setMinWidth(80);
		tablaFiltro.getColumnModel().getColumn(5).setMinWidth(100);
		tablaFiltro.getColumnModel().getColumn(6).setMinWidth(100);
		
		tablaFiltro.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 	
		tablaFiltro.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
		tablaFiltro.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 	
		tablaFiltro.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
		tablaFiltro.getColumnModel().getColumn(4).setCellRenderer(new tablaRenderer("texto","centro","Arial","normal",12)); 	
		tablaFiltro.getColumnModel().getColumn(5).setCellRenderer(new tablaRenderer("texto","centro","Arial","normal",12)); 
		tablaFiltro.getColumnModel().getColumn(6).setCellRenderer(new tablaRenderer("texto","centro","Arial","normal",12)); 	
   	}
   	
	public void llenarTablaFiltro(String estab){
//		for(Object[] row : lista){
//			modeloFiltro.addRow(row);
//		}
	}
	
	private void agregar(final JTable tbl, int folio_encargado) {
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
	        public void mouseClicked(MouseEvent e) {
	        	
	        	if(e.getClickCount() == 2){
	    			int fila = tablaFiltro.getSelectedRow();
	    			String status = tablaFiltro.getValueAt(fila, 3).toString().trim();
	    			String status_recepcion = tablaFiltro.getValueAt(fila, 4).toString().trim();
	    			
	    			if(status.equals("VIGENTE") && status_recepcion.equals("PENDIENTE")){
	    				
//		    			String trasferencia = tablaFiltro.getValueAt(fila, 0).toString().trim();
//		    			String estab_surte  = tablaFiltro.getValueAt(fila, 1).toString().trim();
//		    			String estab_recibe = tablaFiltro.getValueAt(fila, 2).toString().trim();
		    			
//	    				new Cat_Validar_Chofer_Para_Tranferencia(trasferencia,estab_surte,estab_recibe,folio_encargado).setVisible(true);

//		    			System.out.println("Folio De Transferencia Seleccionado: "+folio+"  ->   Abrir Catalogo De Choferes Para Asignacion De Transferencia");

	    			}else{
	    				JOptionPane.showMessageDialog(null, "Solo Se Pueden Mandar Las Transferencias Con Status [Vigente] Y Status De Recepcion [Pendiente]","Aviso",JOptionPane.INFORMATION_MESSAGE);
	    				return;
	    			}
	    			
	        	}
	        }
        });
    }
}
