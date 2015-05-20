package Cat_Auditoria;

import java.awt.AWTException;
import java.awt.Container;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Conexiones_SQL.Connexion;
import Obj_Principal.Componentes;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Filtro_De_Turno extends JDialog{
	
	Container cont = getContentPane();
	JLayeredPane campo = new JLayeredPane();
	
	Object[][] MatrizFiltro ;
	
	DefaultTableModel modeloFiltro = new DefaultTableModel(null,
			new String[]{"Turno", "Nombre","Cod.Estab","Usuario","Fecha Inicial","Fecha Final","*"}
			){
	     @SuppressWarnings("rawtypes")
		Class[] types = new Class[]{
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.Boolean.class
	    
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
        	 	case 6 : return true;
        	 	} 				
 			return false;
 		}
         
         @Override
         public void setValueAt(Object value, int row, int col) {
             super.setValueAt(value, row, col);
             	if (col == 6 && value.equals(Boolean.TRUE))
                 deselectValues(row, col);
         }

         private void deselectValues(int selectedRow, int col) {
             for (int row = 0; row < getRowCount(); row++) {
                 if (getValueAt(row, col).equals(Boolean.TRUE)
                         && row != selectedRow) {
                     setValueAt(Boolean.FALSE, row, col);
                     fireTableCellUpdated(row, col);
                 }
             }
         }
	};
	
	JTable tablaFiltro = new JTable(modeloFiltro);
	JScrollPane scroll = new JScrollPane(tablaFiltro,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    @SuppressWarnings("rawtypes")
	private TableRowSorter trsfiltro;
    
	JTextField txtFolioTurno = new Componentes().text(new JTextField(), "Folio Asignacion", 10, "String");
	JTextField txtNombre = new Componentes().text(new JTextField(), "Folio Cajero", 40, "String");
	
	JButton btnCargar = new JButton("Cargar");
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Cat_Filtro_De_Turno() {
		setModal(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage("imagen/Accounting.png"));
		setTitle("Filtro De Turnos");
		campo.setBorder(BorderFactory.createTitledBorder("Lista De Turnos"));
		
		trsfiltro = new TableRowSorter(modeloFiltro); 
		tablaFiltro.setRowSorter(trsfiltro); 
		
		tablaRender();
		
		campo.add(txtFolioTurno).setBounds(10,38,70,20);
		campo.add(txtNombre).setBounds(80,38,230,20);
		campo.add(btnCargar).setBounds(755,18,80,30);
		
		campo.add(scroll).setBounds(10,60,825,320);
		
		cont.add(campo);
		
		txtFolioTurno.addKeyListener(opFiltroTurno);
		txtNombre.addKeyListener(opFiltroNombre);
		
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened( WindowEvent e ){
            	txtNombre.requestFocus();
          	enterauto();	  
           }
        });
        
		setSize(850,450);
		setResizable(false);
		setLocationRelativeTo(null);
	}
	
	public void enterauto(){
			Robot robot;
			try {
	            robot = new Robot();
	            robot.keyPress(KeyEvent.VK_ENTER);
	            robot.keyRelease(KeyEvent.VK_ENTER);
	        } catch (AWTException e) {
	            e.printStackTrace();
	        }
 	     };
	
 	     
	@SuppressWarnings("unchecked")
	public void limpiar_filtro(){
		trsfiltro.setRowFilter(RowFilter.regexFilter("", 0));
		trsfiltro.setRowFilter(RowFilter.regexFilter("", 1));
		
		txtFolioTurno.setText("");
		txtNombre.setText("");
	}
	
	public void tablaRender(){
		
		tablaFiltro.getTableHeader().setReorderingAllowed(false) ;
		
		tablaFiltro.getColumnModel().getColumn(0).setMaxWidth(70);
		tablaFiltro.getColumnModel().getColumn(0).setMinWidth(70);
		tablaFiltro.getColumnModel().getColumn(1).setMaxWidth(230);
		tablaFiltro.getColumnModel().getColumn(1).setMinWidth(230);
		tablaFiltro.getColumnModel().getColumn(2).setMaxWidth(70);
		tablaFiltro.getColumnModel().getColumn(2).setMinWidth(70);
		tablaFiltro.getColumnModel().getColumn(3).setMaxWidth(150);
		tablaFiltro.getColumnModel().getColumn(3).setMinWidth(150);
		tablaFiltro.getColumnModel().getColumn(4).setMaxWidth(130);
		tablaFiltro.getColumnModel().getColumn(4).setMinWidth(130);
		tablaFiltro.getColumnModel().getColumn(5).setMaxWidth(130);
		tablaFiltro.getColumnModel().getColumn(5).setMinWidth(130);
		tablaFiltro.getColumnModel().getColumn(6).setMaxWidth(30);
		tablaFiltro.getColumnModel().getColumn(6).setMinWidth(30);
		
		tablaFiltro.getTableHeader().setReorderingAllowed(false) ;
		tablaFiltro.setAutoResizeMode(JTable.AUTO_RESIZE_OFF );
			
			for(int i=0; i<tablaFiltro.getColumnCount(); i++){
				if(i==6){
					tablaFiltro.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("CHB","centro","Arial","normal",11));
				}else{
					tablaFiltro.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",11));
				}
			}
	}
	
	 	public Object[][] getTablaFiltro(String estab){

	 		String todos ="declare @establecimiento varchar(20) "
	 				+ "				set @establecimiento = ('"+estab+"'); "
	 				+ "		declare @folio_establecimiento int "
	 				+ "				set @folio_establecimiento = (select cod_estab from establecimientos where ltrim(rtrim(nombre)) = @establecimiento); "
	 				+ "					select turnos_ventas_mostrador.turno "
	 				+ "							,turnos_ventas_mostrador.nombre "
	 				+ "							,turnos_ventas_mostrador.cod_estab "
	 				+ "							,usuarios.nombre as usuario "
	 				+ "							,turnos_ventas_mostrador.fecha_inicial "
	 				+ "							,turnos_ventas_mostrador.fecha_final "
	 				+ "					from turnos_ventas_mostrador "
	 				+ "					inner join usuarios on usuarios.usuario = turnos_ventas_mostrador.usuario "
	 				+ "					where turnos_ventas_mostrador.cod_estab = @folio_establecimiento";
		
	 		Statement s;
	 		ResultSet rs;
	 		
		try {
			s = new Connexion().conexion_IZAGAR().createStatement();
			rs = s.executeQuery(todos);
			MatrizFiltro = new Object[getFilasIZAGAR(todos)][7];
			int i=0;
			while(rs.next()){
				MatrizFiltro[i][0] = "   "+rs.getString(1).trim();
				MatrizFiltro[i][1] = "   "+rs.getString(2).trim();
				MatrizFiltro[i][2] = "   "+rs.getString(3).trim();
				MatrizFiltro[i][3] = "   "+rs.getString(4).trim();
				MatrizFiltro[i][4] = "   "+rs.getString(5).trim();
				MatrizFiltro[i][5] = "   "+rs.getString(6).trim();
				MatrizFiltro[i][6] = "false";
				i++;
			}
			rs.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en Cat_Filtro_De_Turnos "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
	    return MatrizFiltro; 
     }
   	
   	public int getFilasIZAGAR(String qry){
		int filas=0;
		Statement stmt = null;
		try {
			stmt = new Connexion().conexion_IZAGAR().createStatement();
			ResultSet rs = stmt.executeQuery(qry);
			while(rs.next()){
				filas++;
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return filas;
	}	
	public int getFilasSCOI(String qry){
		int filas=0;
		Statement stmt = null;
		try {
			stmt = new Connexion().conexion_IZAGAR().createStatement();
			ResultSet rs = stmt.executeQuery(qry);
			while(rs.next()){
				filas++;
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return filas;
	}
	
	KeyListener opFiltroTurno = new KeyListener(){
		@SuppressWarnings("unchecked")
		public void keyReleased(KeyEvent arg0) {
			trsfiltro.setRowFilter(RowFilter.regexFilter(txtFolioTurno.getText().toUpperCase(), 0));
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}		
	};
	
	KeyListener opFiltroNombre = new KeyListener(){
		@SuppressWarnings("unchecked")
		public void keyReleased(KeyEvent arg0) {
			trsfiltro.setRowFilter(RowFilter.regexFilter(txtNombre.getText().toUpperCase(), 1));
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}		
	};
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Filtro_De_Turno().setVisible(true);
		}catch(Exception e){
			
		}
	}
}
