package Cat_Auditoria;

import java.awt.Component;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

import Conexiones_SQL.Connexion;
import Obj_Principal.Componentes;

@SuppressWarnings("serial")
public class Cat_Filtro_De_Asignacion extends JFrame{
	
	Container cont = getContentPane();
	JLayeredPane campo = new JLayeredPane();
	
	Object[][] MatrizFiltro ;
	
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
	};
	
	JTable tablaFiltro = new JTable(modeloFiltro);
	JScrollPane scroll = new JScrollPane(tablaFiltro,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    @SuppressWarnings("rawtypes")
	private TableRowSorter trsfiltro;
    
	JTextField txtFolioAsignacion = new Componentes().text(new JTextField(), "Folio Asignacion", 9, "String");
	JTextField txtFolioCajero = new Componentes().text(new JTextField(), "Folio Cajero", 9, "String");
	JTextField txtNombreCajero = new Componentes().text(new JTextField(), "Nombre Cajero", 40, "String");
	
	JButton btnCargar = new JButton("Cargar");
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Cat_Filtro_De_Asignacion() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/filter_icon&16.png"));
		setTitle("Filtro De Asignaciones");
		campo.setBorder(BorderFactory.createTitledBorder("Asignaciones Liquidadas"));
		
		trsfiltro = new TableRowSorter(modeloFiltro); 
		tablaFiltro.setRowSorter(trsfiltro); 
				
//		cont.setBackground(new Color(86,161,85));
		
		tablaRender();
		
		
		campo.add(txtFolioAsignacion).setBounds(10,38,70,20);
		campo.add(txtFolioCajero).setBounds(80,38,70,20);
		campo.add(txtNombreCajero).setBounds(150,38,210,20);
		campo.add(btnCargar).setBounds(925,18,80,30);
		
		campo.add(scroll).setBounds(10,60,995,320);
		
		cont.add(campo);
		
		txtFolioAsignacion.addKeyListener(opFiltroAsignacion);
		txtFolioCajero.addKeyListener(opFiltroFolioCajero);
		txtNombreCajero.addKeyListener(opFiltroNombreCajero);

		setSize(1020,450);
		setResizable(false);
		setLocationRelativeTo(null);
	}
	
	@SuppressWarnings("unchecked")
	public void limpiar_filtro(){
		trsfiltro.setRowFilter(RowFilter.regexFilter("", 0));
		trsfiltro.setRowFilter(RowFilter.regexFilter("", 1));
		trsfiltro.setRowFilter(RowFilter.regexFilter("", 2));
		
		txtFolioAsignacion.setText("");
		txtFolioCajero.setText("");
		txtNombreCajero.setText("");
	}
	
	public void tablaRender(){
		
		tablaFiltro.getTableHeader().setReorderingAllowed(false) ;
		
		tablaFiltro.getColumnModel().getColumn(0).setMaxWidth(70);
		tablaFiltro.getColumnModel().getColumn(0).setMinWidth(70);
		tablaFiltro.getColumnModel().getColumn(1).setMaxWidth(70);
		tablaFiltro.getColumnModel().getColumn(1).setMinWidth(70);
		tablaFiltro.getColumnModel().getColumn(2).setMaxWidth(210);
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

		TableCellRenderer render = new TableCellRenderer() { 
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, 
			boolean hasFocus, int row, int column) { 
				
				tablaFiltro.setAutoResizeMode(JTable.AUTO_RESIZE_OFF );
				
				Component componente = null;
				
				switch(column){
					case 0: 
						componente = new JLabel(value == null? "": value.toString());
						if(row %2 == 0){
							((JComponent) componente).setOpaque(true); 
							componente.setBackground(new java.awt.Color(177,177,177));	
						}
//						if(Boolean.parseBoolean(modeloFiltro.getValueAt(row,3).toString())){
//							((JComponent) componente).setOpaque(true); 
//							componente.setBackground(new java.awt.Color(186,143,73));
//						}
						if(table.getSelectedRow() == row){
							((JComponent) componente).setOpaque(true); 
							componente.setBackground(new java.awt.Color(186,143,73));
						}
						((JLabel) componente).setHorizontalAlignment(SwingConstants.LEFT);
						break;
					case 1: 
						componente = new JLabel(value == null? "": value.toString());
						if(row %2 == 0){
							((JComponent) componente).setOpaque(true); 
							componente.setBackground(new java.awt.Color(177,177,177));	
						}
//						if(Boolean.parseBoolean(modeloFiltro.getValueAt(row,3).toString())){
//							((JComponent) componente).setOpaque(true); 
//							componente.setBackground(new java.awt.Color(186,143,73));
//						}
						if(table.getSelectedRow() == row){
							((JComponent) componente).setOpaque(true); 
							componente.setBackground(new java.awt.Color(186,143,73));
						}
						((JLabel) componente).setHorizontalAlignment(SwingConstants.LEFT);
						break;
					case 2:
						componente = new JLabel(value == null? "": value.toString());
						if(row %2 == 0){
							((JComponent) componente).setOpaque(true); 
							componente.setBackground(new java.awt.Color(177,177,177));	
						}
//						if(Boolean.parseBoolean(modeloFiltro.getValueAt(row,3).toString())){
//							((JComponent) componente).setOpaque(true); 
//							componente.setBackground(new java.awt.Color(186,143,73));
//						}
						if(table.getSelectedRow() == row){
							((JComponent) componente).setOpaque(true); 
							componente.setBackground(new java.awt.Color(186,143,73));
						}
						((JLabel) componente).setHorizontalAlignment(SwingConstants.LEFT);
						break;
					case 3:
						componente = new JLabel(value == null? "": value.toString());
						if(row %2 == 0){
							((JComponent) componente).setOpaque(true); 
							componente.setBackground(new java.awt.Color(177,177,177));	
						}
//						if(Boolean.parseBoolean(modeloFiltro.getValueAt(row,3).toString())){
//							((JComponent) componente).setOpaque(true); 
//							componente.setBackground(new java.awt.Color(186,143,73));
//						}
						if(table.getSelectedRow() == row){
							((JComponent) componente).setOpaque(true); 
							componente.setBackground(new java.awt.Color(186,143,73));
						}
						((JLabel) componente).setHorizontalAlignment(SwingConstants.LEFT);
						break;
					case 4:
						componente = new JLabel(value == null? "": value.toString());
						if(row %2 == 0){
							((JComponent) componente).setOpaque(true); 
							componente.setBackground(new java.awt.Color(177,177,177));	
						}
//						if(Boolean.parseBoolean(modeloFiltro.getValueAt(row,3).toString())){
//							((JComponent) componente).setOpaque(true); 
//							componente.setBackground(new java.awt.Color(186,143,73));
//						}
						if(table.getSelectedRow() == row){
							((JComponent) componente).setOpaque(true); 
							componente.setBackground(new java.awt.Color(186,143,73));
						}
						((JLabel) componente).setHorizontalAlignment(SwingConstants.LEFT);
						break;
					case 5:
						componente = new JLabel(value == null? "": value.toString());
						if(row %2 == 0){
							((JComponent) componente).setOpaque(true); 
							componente.setBackground(new java.awt.Color(177,177,177));	
						}
//						if(Boolean.parseBoolean(modeloFiltro.getValueAt(row,3).toString())){
//							((JComponent) componente).setOpaque(true); 
//							componente.setBackground(new java.awt.Color(186,143,73));
//						}
						if(table.getSelectedRow() == row){
							((JComponent) componente).setOpaque(true); 
							componente.setBackground(new java.awt.Color(186,143,73));
						}
						((JLabel) componente).setHorizontalAlignment(SwingConstants.LEFT);
						break;
					case 6:
						componente = new JLabel(value == null? "": value.toString());
						if(row %2 == 0){
							((JComponent) componente).setOpaque(true); 
							componente.setBackground(new java.awt.Color(177,177,177));	
						}
//						if(Boolean.parseBoolean(modeloFiltro.getValueAt(row,3).toString())){
//							((JComponent) componente).setOpaque(true); 
//							componente.setBackground(new java.awt.Color(186,143,73));
//						}
						if(table.getSelectedRow() == row){
							((JComponent) componente).setOpaque(true); 
							componente.setBackground(new java.awt.Color(186,143,73));
						}
						((JLabel) componente).setHorizontalAlignment(SwingConstants.LEFT);
						break;
					case 7:
						componente = new JLabel(value == null? "": value.toString());
						if(row %2 == 0){
							((JComponent) componente).setOpaque(true); 
							componente.setBackground(new java.awt.Color(177,177,177));	
						}
//						if(Boolean.parseBoolean(modeloFiltro.getValueAt(row,3).toString())){
//							((JComponent) componente).setOpaque(true); 
//							componente.setBackground(new java.awt.Color(186,143,73));
//						}
						if(table.getSelectedRow() == row){
							((JComponent) componente).setOpaque(true); 
							componente.setBackground(new java.awt.Color(186,143,73));
						}
						((JLabel) componente).setHorizontalAlignment(SwingConstants.LEFT);
						break;
//					case 8:
//						componente = new JLabel(value == null? "": value.toString());
//						if(row %2 == 0){
//							((JComponent) componente).setOpaque(true); 
//							componente.setBackground(new java.awt.Color(177,177,177));	
//						}
//						if(Boolean.parseBoolean(modeloFiltro.getValueAt(row,3).toString())){
//							((JComponent) componente).setOpaque(true); 
//							componente.setBackground(new java.awt.Color(186,143,73));
//						}
//						if(table.getSelectedRow() == row){
//							((JComponent) componente).setOpaque(true); 
//							componente.setBackground(new java.awt.Color(186,143,73));
//						}
//						((JLabel) componente).setHorizontalAlignment(SwingConstants.LEFT);
//						break;
					case 8: 
						componente = new JCheckBox("",Boolean.parseBoolean(value.toString()));
						if(row%2==0){
							((JComponent) componente).setOpaque(true); 
							componente.setBackground(new java.awt.Color(177,177,177));	
						}
//						if(Boolean.parseBoolean(modeloFiltro.getValueAt(row,3).toString())){
//							((JComponent) componente).setOpaque(true); 
//							componente.setBackground(new java.awt.Color(186,143,73));
//						}
						if(table.getSelectedRow() == row){
							((JComponent) componente).setOpaque(true); 
							componente.setBackground(new java.awt.Color(186,143,73));
						}
						((AbstractButton) componente).setHorizontalAlignment(SwingConstants.CENTER);
						break;
				}
				return componente;
			} 
		}; 
		tablaFiltro.getColumnModel().getColumn(0).setCellRenderer(render); 
		tablaFiltro.getColumnModel().getColumn(1).setCellRenderer(render); 
		tablaFiltro.getColumnModel().getColumn(2).setCellRenderer(render);
		tablaFiltro.getColumnModel().getColumn(3).setCellRenderer(render);
		tablaFiltro.getColumnModel().getColumn(4).setCellRenderer(render);
		tablaFiltro.getColumnModel().getColumn(5).setCellRenderer(render);
		tablaFiltro.getColumnModel().getColumn(6).setCellRenderer(render);
		tablaFiltro.getColumnModel().getColumn(7).setCellRenderer(render);
		tablaFiltro.getColumnModel().getColumn(8).setCellRenderer(render);
		
	}
	
	 	public Object[][] getTablaFiltro(String cadena_asignaciones_en_uso){
	 		
		String cadenaFinal="";
		String cadenaAsignacionesGuardadasEnSCOI="";
		
	 		String asignacionesSCOI ="select asignacion from tb_tabla_de_asignaciones_para_cortes";
	 		
			Statement s;
			ResultSet rs;
			try {
				s = new Connexion().conexion().createStatement();
				rs = s.executeQuery(asignacionesSCOI);
				
				while(rs.next()){	cadenaAsignacionesGuardadasEnSCOI+= "'"+(rs.getString(1).trim())+"',";	}
									cadenaFinal = cadenaAsignacionesGuardadasEnSCOI+cadena_asignaciones_en_uso;
				
			} catch (SQLException e1) {
				e1.printStackTrace();
			}	 	
	 		
		String todos = "declare @fecha_inicial varchar(50),@fecha_final varchar(50) " +
				"		set @fecha_inicial=convert(varchar(20), getdate()-7,103)+' 00:00:00' " +
				"		set @fecha_final=convert(varchar(20), getdate(),103)+' 23:59:59' " +

				"		SELECT Asignaciones_cajeros.Folio as Asignacion " +
				"      ,cajeros.cajero as Cajero " +
				"      ,cajeros.nombre as Nombre_Cajero " +
				"      ,ISNULL(SUM(Movimientos_cajeros.Importe), 0) as Total " +
				"      ,ISNULL(Asignaciones_cajeros.cod_estab, '') as Cod_Estab " +
				"      ,ISNULL(establecimientos.nombre, '') as Establecimiento " +
				"      ,convert(varchar(20), Asignaciones_cajeros.fecha,103)+' '+ convert(varchar(20), Asignaciones_cajeros.fecha,108) as Fecha_Asignacion " +
				"      ,ISNULL(convert(varchar(20),Asignaciones_cajeros.fecha_liquidacion,103)+' '+convert(varchar(20), Asignaciones_cajeros.fecha_liquidacion,108),'SIN LIQUIDAR') as Fecha_Liquidacion " +
				"	  FROM Asignaciones_cajeros " +
				"	  LEFT JOIN establecimientos ON Asignaciones_cajeros.cod_estab = establecimientos.cod_estab " +
				"     LEFT JOIN Movimientos_cajeros ON Asignaciones_cajeros.Folio = Movimientos_cajeros.Folio_asignacion  and movimientos_cajeros.Tipo_movimiento_cajero = 'R' " +
				"     INNER JOIN  cajeros ON Asignaciones_cajeros.cajero = cajeros.cajero " +
				"		WHERE " +
				"		(Asignaciones_cajeros.ultima_modificacion >= @fecha_inicial and Asignaciones_cajeros.ultima_modificacion <= @fecha_final) " +
				"		AND " +
				"		Asignaciones_cajeros.status = 'L' " +
				"		AND Asignaciones_cajeros.Folio not in ("+cadenaFinal+")" +
				"		GROUP BY Asignaciones_cajeros.Folio, Asignaciones_cajeros.fecha, Asignaciones_cajeros.status, cajeros.cajero, cajeros.nombre, Asignaciones_cajeros.caja,Asignaciones_cajeros.cod_estab " +
				"		         ,establecimientos.nombre, Asignaciones_cajeros.fecha_liquidacion " +
				"		order by Fecha_Liquidacion desc ";		
		
		cadenaFinal="";
		
		try {
			s = new Connexion().conexion_IZAGAR().createStatement();
			rs = s.executeQuery(todos);
			
			
			MatrizFiltro = new Object[getFilasIZAGAR(todos)][9];
			int i=0;
			while(rs.next()){
				
//				System.out.print(rs.getString(1).trim()+"   ");
//				System.out.print(rs.getString(2).trim()+"   ");
//				System.out.print(rs.getString(3).trim()+"   ");
//				System.out.print(rs.getString(4).trim()+"   ");
//				System.out.print(rs.getString(5).trim()+"   ");
//				System.out.print(rs.getString(6).trim()+"   ");
//				System.out.print(rs.getString(7).trim()+"   ");
//				System.out.println(rs.getString(8).trim()+"   ");
				
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
		} catch (SQLException e1) {
			e1.printStackTrace();
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
	
	KeyListener opFiltroAsignacion = new KeyListener(){
		@SuppressWarnings("unchecked")
		public void keyReleased(KeyEvent arg0) {
			trsfiltro.setRowFilter(RowFilter.regexFilter(txtFolioAsignacion.getText().toUpperCase(), 0));
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}		
	};
	
	KeyListener opFiltroFolioCajero = new KeyListener(){
		@SuppressWarnings("unchecked")
		public void keyReleased(KeyEvent arg0) {
			trsfiltro.setRowFilter(RowFilter.regexFilter(txtFolioCajero.getText().toUpperCase(), 1));
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}		
	};
	
	KeyListener opFiltroNombreCajero = new KeyListener(){
		@SuppressWarnings("unchecked")
		public void keyReleased(KeyEvent arg0) {
			trsfiltro.setRowFilter(RowFilter.regexFilter(txtNombreCajero.getText().toUpperCase(), 2));
		}
		public void keyTyped(KeyEvent arg0) {
//			char caracter = arg0.getKeyChar();
//			if(((caracter < '0') ||
//				(caracter > '9')) &&
//			    (caracter != KeyEvent.VK_BACK_SPACE)){
//				arg0.consume(); 
//			}	
		}
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
