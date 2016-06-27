package Cat_Auditoria;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import Conexiones_SQL.BuscarTablasModel;
import Conexiones_SQL.Generacion_Reportes;
import Obj_Principal.Componentes;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_Filtro_Dinamico;
import Obj_Principal.Obj_Filtro_Dinamico_Plus;
import Obj_Renders.tablaRenderer;


@SuppressWarnings("serial")
public class Cat_Trabajos_Con_Movimiento_De_Ahorro extends JFrame{
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
//	String[] vector = new Obj_Establecimiento().Combo_Establecimiento_Concentrado();
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	JComboBox cmbConcentrado = new JComboBox(vector);
	
	JTextField txtFiltro = new Componentes().text(new JCTextField(), "Filtrar Trabajos", 50, "String");

	JButton btnGenerar = new JButton("Generar", new ImageIcon("imagen/buscar.png"));

	DefaultTableModel modelo_concentrado = new DefaultTableModel(new BuscarTablasModel().tabla_concentrados_con_movimiento_de_ahorros(), new String[]{"Trabajo", "Grupo","Fecha","Abonos"}){
	     @SuppressWarnings("rawtypes")
		Class[] types = new Class[]{
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
       	 	} 				
			return false;
		}
	};
	
	JTable tabla_concentrado = new JTable(modelo_concentrado);
	JScrollPane scroll_concentrado = new JScrollPane(tabla_concentrado,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

	
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
	public Cat_Trabajos_Con_Movimiento_De_Ahorro(){
		this.setSize(450,420);
		this.setResizable(true);
		this.setLocationRelativeTo(null);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Filter-List-icon32.png"));
		this.setTitle("Seleccion Del Concentrado Para El Trabajo De Cortes");
		panel.setBorder(BorderFactory.createTitledBorder("Concentrados De Cortes"));
		trsfiltro = new TableRowSorter(modelo_establecimiento_para_concentrado); 
		tabla_establecimiento_para_concentrado.setRowSorter(trsfiltro);
		
		int x=20,y=20;
		panel.add(txtFiltro).setBounds(x,y,300,20);
		panel.add(btnGenerar).setBounds(320,y,100,20);
		panel.add(scroll_concentrado).setBounds(x,y+=20,400,170);
		
		panel.add(new JLabel("Establecimientos En El Concentrado Seleccionado")).setBounds(x,y+=175,250,20);
//		panel.add(cmbConcentrado).setBounds(150,y,220,20);
		panel.add(scroll_establecimiento_para_concentrado).setBounds(x,y+=20,320,123);
		
		
		tablaRender();
		agregar(tabla_concentrado);
		
		txtFiltro.addKeyListener(opFiltro);
		btnGenerar.addActionListener(opGenerar);
		cont.add(panel);
	}
	
	public void agregar(JTable tb){
		tb.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
	        	if(e.getClickCount() == 1){
	    			new Obj_Filtro_Dinamico(tabla_establecimiento_para_concentrado, "Grupo", tabla_concentrado.getValueAt(tb.getSelectedRow(), 1).toString(), "", "", "", "", "", "");
	        	}
			}
		});
	}
	
	KeyListener opFiltro = new KeyListener() {
		public void keyTyped(KeyEvent arg0) {}
		public void keyReleased(KeyEvent arg0) {
			int[] columnas = new int[4];
			new Obj_Filtro_Dinamico_Plus(tabla_concentrado, txtFiltro.getText(), columnas);
		}
		public void keyPressed(KeyEvent arg0) {}
	};
	
	ActionListener opGenerar = new ActionListener(){
		public void actionPerformed(ActionEvent arg0){
			
			if(tabla_concentrado.getSelectedRow()>=0){
				Cat_Reporte_De_Valor_De_Nomina(Integer.valueOf(tabla_concentrado.getValueAt(tabla_concentrado.getSelectedRow(), 0).toString().trim()));
			}else{
//				aviso
			}
		}
	};
	
	public static void Cat_Reporte_De_Valor_De_Nomina(int folio_trabjo){
		try {
			
			String basedatos="2.26";
			String vista_previa_reporte="no";
			int vista_previa_de_ventana=0;
			
			String comando="exec sp_select_reporte_de_movimineto_de_ahorro_por_folio_de_trabajo '"+folio_trabjo+"'";
			String reporte = "Obj_Reporte_De_Ahorros_Por_Folio_De_Trabajo_De_Corte.jrxml";
			new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
			 
		} catch (Exception e) {
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(null, "Error En Cat_Reporte_De_Corte_De_Caja ", "Error !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
		}
	}
	
	public void tablaRender(){
		tabla_concentrado.getTableHeader().setReorderingAllowed(false) ;
		tabla_concentrado.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		tabla_concentrado.getColumnModel().getColumn(0).setMaxWidth(60);
		tabla_concentrado.getColumnModel().getColumn(0).setMinWidth(60);
		tabla_concentrado.getColumnModel().getColumn(1).setMaxWidth(120);
		tabla_concentrado.getColumnModel().getColumn(1).setMinWidth(120);
		tabla_concentrado.getColumnModel().getColumn(2).setMaxWidth(90);
		tabla_concentrado.getColumnModel().getColumn(2).setMinWidth(90);
		tabla_concentrado.getColumnModel().getColumn(3).setMaxWidth(100);
		tabla_concentrado.getColumnModel().getColumn(3).setMinWidth(100);
		tabla_concentrado.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12)); 	
		tabla_concentrado.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
		tabla_concentrado.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","centro","Arial","normal",12)); 	
		tabla_concentrado.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12));
		
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
			new Cat_Trabajos_Con_Movimiento_De_Ahorro().setVisible(true);
		}catch(Exception e){
			System.err.println("Error :"+ e.getMessage());
		}
	}

}
