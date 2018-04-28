package Cat_Auditoria;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Conexiones_SQL.Connexion;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_tabla;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Filtro_De_Asignacion extends JDialog{
	Container cont = getContentPane();
	JLayeredPane campo = new JLayeredPane();
	Object[][] MatrizFiltro ;
	Obj_tabla ObjTab =new Obj_tabla();
	int columnas = 9,checkbox=1;
	
	DefaultTableModel modeloFiltro = new DefaultTableModel(null,
            new String[]{"Asignacion", "F. Cajero(a)","Nombre Cajera(o)","Total","Cod Estab","Establecimiento","Fecha de Asignacion","Fecha de Liquidacion",""}
			){
	     @SuppressWarnings("rawtypes")
		Class[] types = new Class[]{
	    	java.lang.String.class,
	    	java.lang.String.class,
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
        	 	case 6 : return false;
        	 	case 7 : return false;
        	 	case 8 : return true;
        	 	} 				
 			return false;
 		}
         
         @Override
         public void setValueAt(Object value, int row, int col) {
             super.setValueAt(value, row, col);
             	if (col == 8 && value.equals(Boolean.TRUE))
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
	JTextField txtFiltro_asignacion = new Componentes().text(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<"    , 300, "String");
	
	JButton btnCargar = new JCButton("Cargar"                  ,"la-flecha-verde-de-la-derecha-icono-8326-32.png","Azul");
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Cat_Filtro_De_Asignacion() {
		setSize(1020,410);
		setResizable(false);
		setLocationRelativeTo(null);
		setModal(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage("imagen/Accounting.png"));
		setTitle("Filtro De Asignaciones");
		campo.setBorder(BorderFactory.createTitledBorder("Asignaciones Liquidadas"));
		
		trsfiltro = new TableRowSorter(modeloFiltro); 
		tablaFiltro.setRowSorter(trsfiltro); 
		
		tablaRender();
		
		campo.add(txtFiltro_asignacion).setBounds(10  ,30 ,810 ,20 );
		campo.add(btnCargar).setBounds           (880 ,18 ,125 ,30 );
		campo.add(scroll).setBounds              (10  ,50 ,995 ,320);
		cont.add(campo);
		
		txtFiltro_asignacion.addKeyListener(opFiltro_busqueda);
		
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened( WindowEvent e ){
            	txtFiltro_asignacion.requestFocus();
           }
        });
	}
 	     
	public void limpiar_filtro(){
		txtFiltro_asignacion.setText("");
		ObjTab.Obj_Filtro(tablaFiltro, "", columnas,txtFiltro_asignacion);
	}
	
	public void tablaRender(){
		tablaFiltro.getTableHeader().setReorderingAllowed(false) ;
		tablaFiltro.setAutoResizeMode(JTable.AUTO_RESIZE_OFF );
			
		tablaFiltro.getColumnModel().getColumn(0).setMaxWidth(70);
		tablaFiltro.getColumnModel().getColumn(0).setMinWidth(70);
		tablaFiltro.getColumnModel().getColumn(1).setMaxWidth(70);
		tablaFiltro.getColumnModel().getColumn(1).setMinWidth(70);
		tablaFiltro.getColumnModel().getColumn(2).setMinWidth(210);
		tablaFiltro.getColumnModel().getColumn(3).setMaxWidth(90);
		tablaFiltro.getColumnModel().getColumn(3).setMinWidth(90);
		tablaFiltro.getColumnModel().getColumn(4).setMaxWidth(70);
		tablaFiltro.getColumnModel().getColumn(4).setMinWidth(70);
		tablaFiltro.getColumnModel().getColumn(5).setMaxWidth(160);
		tablaFiltro.getColumnModel().getColumn(5).setMinWidth(160);
		tablaFiltro.getColumnModel().getColumn(6).setMaxWidth(140);
		tablaFiltro.getColumnModel().getColumn(6).setMinWidth(140);
		tablaFiltro.getColumnModel().getColumn(7).setMaxWidth(140);
		tablaFiltro.getColumnModel().getColumn(7).setMinWidth(140);
		tablaFiltro.getColumnModel().getColumn(8).setMaxWidth(25);
		tablaFiltro.getColumnModel().getColumn(8).setMinWidth(25);

		for(int i=0; i<tablaFiltro.getColumnCount(); i++){
			if(i==8){
				tablaFiltro.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("CHB","centro","Arial","normal",11));
			}else{
				tablaFiltro.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",11));
			}
		}
	}
	
	 	public Object[][] getTablaFiltro(String cadena_asignaciones_en_uso,String Establecimiento){
			Statement s;
			ResultSet rs;
			
////////////RECOPILACION DE ASIGNACIONES////////////////////////////////////////////////////				
	 	String recopilacion_asignaciones="  exec sp_Recopilacion_IZAGAR_de_Asignaciones_y_cajeros  " ;	
		Connection con = new Connexion().conexion_IZAGAR();
		PreparedStatement pstmt = null;
	 	try {
	 		pstmt = con.prepareStatement(recopilacion_asignaciones);
	 		pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
					JOptionPane.showMessageDialog(null, "Error en Cat_Filtro_De_Asignaciones  sp_Recopilacion_IZAGAR_de_Asignaciones_y_cajeros "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en Cat_Filtro_De_Asignaciones  sp_Recopilacion_IZAGAR_de_Asignaciones_y_cajeros "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
			}
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			}
	 	
		String todos ="exec sp_consulta_de_asignaciones  '"+Establecimiento+"'";
		
		try {
			s = new Connexion().conexion_IZAGAR().createStatement();
			rs = s.executeQuery(todos);
			MatrizFiltro = new Object[getFilasIZAGAR(todos)][9];
			int i=0;
			while(rs.next()){
				MatrizFiltro[i][0] = "   "+rs.getString(1).trim();
				MatrizFiltro[i][1] = "   "+rs.getString(2).trim();
				MatrizFiltro[i][2] = "   "+rs.getString(3).trim();
				MatrizFiltro[i][3] = "   "+rs.getString(4).trim();
				MatrizFiltro[i][4] = "   "+rs.getString(5).trim();
				MatrizFiltro[i][5] = "   "+rs.getString(6).trim();
				MatrizFiltro[i][6] = "   "+rs.getString(7).trim();
				MatrizFiltro[i][7] = "   "+rs.getString(8).trim();
				MatrizFiltro[i][8] = "false";
				i++;
			}
			rs.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en Cat_Filtro_De_Asignaciones  sp_consulta_de_asignaciones "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
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
	
   	KeyListener opFiltro_busqueda = new KeyListener(){
		public void keyReleased(KeyEvent arg0) {
			ObjTab.Obj_Filtro(tablaFiltro, txtFiltro_asignacion.getText().toUpperCase(), columnas,txtFiltro_asignacion);
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}		
	};

	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Filtro_De_Asignacion().setVisible(true);
		}catch(Exception e){
			
		}
	}
}
