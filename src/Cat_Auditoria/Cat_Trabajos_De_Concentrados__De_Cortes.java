package Cat_Auditoria;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import Conexiones_SQL.BuscarTablasModel;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Renders.tablaRenderer;


@SuppressWarnings("serial")
public class Cat_Trabajos_De_Concentrados__De_Cortes extends JFrame{
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	String[] vector = new Obj_Establecimiento().Combo_Establecimiento_Concentrado();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbConcentrado = new JComboBox(vector);

	JButton btnGenerar = new JButton("Generar", new ImageIcon("imagen/buscar.png"));
	
	DefaultTableModel modelo_establecimiento_para_concentrado = new DefaultTableModel(new BuscarTablasModel().tabla_establecimientos_para_concentrado(), new String[]{"Establecimiento", "Grupo"}){
	     @SuppressWarnings("rawtypes")
		Class[] types = new Class[]{
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
        	 	} 				
 			return false;
 		}
	};
	
	JTable tabla_establecimiento_para_concentrado = new JTable(modelo_establecimiento_para_concentrado);
	JScrollPane scroll_establecimiento_para_concentrado = new JScrollPane(tabla_establecimiento_para_concentrado,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

	@SuppressWarnings("rawtypes")
	private TableRowSorter trsfiltro;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Cat_Trabajos_De_Concentrados__De_Cortes(){
		this.setSize(450,380);
		this.setResizable(true);
		this.setLocationRelativeTo(null);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Filter-List-icon32.png"));
		this.setTitle("Seleccion Del Concentrado Para El Trabajo De Cortes");
		panel.setBorder(BorderFactory.createTitledBorder("Concentrados De Cortes"));
		trsfiltro = new TableRowSorter(modelo_establecimiento_para_concentrado); 
		tabla_establecimiento_para_concentrado.setRowSorter(trsfiltro);
		panel.add(new JLabel("Concentrado: ")).setBounds(60,20,80,20);
		panel.add(cmbConcentrado).setBounds(150,20,220,20);
		
		panel.add(scroll_establecimiento_para_concentrado).setBounds(60,50,310,250);
		panel.add(btnGenerar).setBounds(270,310,100,20);
		
		tablaRender();
		
		cmbConcentrado.addActionListener(opFiltro);
		btnGenerar.addActionListener(opGenerar);
		cont.add(panel);
	}
	
	ActionListener opFiltro = new ActionListener(){
		@SuppressWarnings("unchecked")
		public void actionPerformed(ActionEvent arg0){
			if(cmbConcentrado.getSelectedIndex() != 0){
				trsfiltro.setRowFilter(RowFilter.regexFilter(cmbConcentrado.getSelectedItem()+"", 1));
			}else{
				trsfiltro.setRowFilter(RowFilter.regexFilter("", 1));
			}
		}
	};
	
	ActionListener opGenerar = new ActionListener(){
		public void actionPerformed(ActionEvent arg0){
			if(cmbConcentrado.getSelectedIndex() == 0 || tabla_establecimiento_para_concentrado.getRowCount() == 0){
				JOptionPane.showMessageDialog(null, "Seleccionar un concentrado que contenga establecimientos clasificados", "Aviso", JOptionPane.WARNING_MESSAGE);
				return;
			}else{
				new Cat_Trabajos_Cortes(cmbConcentrado.getSelectedItem().toString()).setVisible(true);
			}
		}
	};
	
	public void tablaRender(){
		tabla_establecimiento_para_concentrado.getTableHeader().setReorderingAllowed(false) ;
		tabla_establecimiento_para_concentrado.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tabla_establecimiento_para_concentrado.getColumnModel().getColumn(0).setMaxWidth(160);
		tabla_establecimiento_para_concentrado.getColumnModel().getColumn(0).setMinWidth(160);
		tabla_establecimiento_para_concentrado.getColumnModel().getColumn(1).setMaxWidth(140);
		tabla_establecimiento_para_concentrado.getColumnModel().getColumn(1).setMinWidth(140);
		tabla_establecimiento_para_concentrado.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 	
		tabla_establecimiento_para_concentrado.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 	
	}
	
	public static void main(String[] args) {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Trabajos_De_Concentrados__De_Cortes().setVisible(true);
		}catch(Exception e){
			System.err.println("Error :"+ e.getMessage());
		}
	}

}
