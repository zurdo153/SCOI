package Cat_Contabilidad;

import java.awt.Component;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import com.toedter.calendar.JDateChooser;

import Conexiones_SQL.Connexion;
import Obj_Lista_de_Raya.Obj_Establecimiento;

@SuppressWarnings("serial")
public class Cat_Relacion_De_Facturas_En_Un_Periodo extends JDialog{
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	String establecimiento[] = new Obj_Establecimiento().Combo_Establecimiento();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbEstablecimiento = new JComboBox(establecimiento);
	
//	JTextField txtFolio = new Componentes().text(new JTextField(), "Folio del Empleado", 10, "String");
	
	JDateChooser c_inicio = new JDateChooser();
	JDateChooser c_final = new JDateChooser();
	
	JButton btngenerar = new JButton("Buscar",new ImageIcon("imagen/buscar.png"));
	
	
	DefaultTableModel modelo = new DefaultTableModel(null,
            new String[]{"Folio", "CodCte","Cliente","Fecha Fact","Importe","Iva","IEPS","Costo","Contribucion","Total","C.E.","Establecimiento",
			             "Folio_Origen","CP","N_Cond_Pago","Notas","CU","N Usuario","Tasa 0 Importe","Tasa 0 IVA","Tasa 0 IEPS","Tasa 0 Neto",
			             "Tasa 16 Importe","Tasa 16 IVA","Tasa 16 IEPS","Tasa 16 Neto","Status","Fecha Cancelacion",""}
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
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
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
        	 	case 8 : return false;
        	 	case 9 : return false;
        	 	case 10 : return false;
        	 	case 11 : return false;
        	 	case 12 : return false;
        	 	case 13 : return false;
        	 	case 14 : return false;
        	 	case 15 : return false;
        	 	case 16 : return false;
        	 	case 17 : return false;
        	 	case 18 : return false;
        	 	case 19 : return false;
        	 	case 20 : return false;
        	 	case 21 : return false;
        	 	case 22 : return false;
        	 	case 23 : return false;
        	 	case 24 : return false;
        	 	case 25 : return false;
        	 	case 26 : return false;
        	 	case 27 : return false;
        	 	case 28 : return true;
        	 } 				
 			return false;
 		}
	};
	
	
    JTable tablaFacturas = new JTable(modelo);
    JScrollPane scrollAsignado = new JScrollPane(tablaFacturas);
	
	
    Border blackline, etched, raisedbevel, loweredbevel, empty;
	public Cat_Relacion_De_Facturas_En_Un_Periodo(){
		setSize(1024, 768);
		panel.add(new JLabel("Fecha Inicio:")).setBounds(15,20,100,20);
		panel.add(c_inicio).setBounds(80,20,100,20);
		panel.add(new JLabel("Fecha Final:")).setBounds(200,20,100,20);
		panel.add(c_final).setBounds(260,20,100,20);		
		panel.add(btngenerar).setBounds(400, 20, 100, 20);
		panel.add(ObtenerPanelTabla()).setBounds(15,80,990,650);
		
		
		cont.add(panel);
		
		btngenerar.addActionListener(opGenerar);
		
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/archivo-icono-8809-32.png"));
		this.blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
		this.panel.setBorder(BorderFactory.createTitledBorder(blackline,"Relacion De Facturas En Un Periodo Con Valores Por Tasas"));
		this.setTitle("Relacion De Facturas En Un Periodo");
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	private JScrollPane ObtenerPanelTabla()	{	
		tablaFacturas.getTableHeader().setReorderingAllowed(false) ;
		tablaFacturas.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
	tablaFacturas.getColumnModel().getColumn(0).setMinWidth(45);
	tablaFacturas.getColumnModel().getColumn(0).setMaxWidth(50);
	tablaFacturas.getColumnModel().getColumn(1).setMinWidth(45);
	tablaFacturas.getColumnModel().getColumn(1).setMaxWidth(50);
	tablaFacturas.getColumnModel().getColumn(2).setMinWidth(250);
	tablaFacturas.getColumnModel().getColumn(2).setMaxWidth(900);
	tablaFacturas.getColumnModel().getColumn(3).setMinWidth(80);
	tablaFacturas.getColumnModel().getColumn(3).setMaxWidth(200);
	tablaFacturas.getColumnModel().getColumn(4).setMaxWidth(80);
	tablaFacturas.getColumnModel().getColumn(4).setMinWidth(120);
	tablaFacturas.getColumnModel().getColumn(5).setMinWidth(80);
	tablaFacturas.getColumnModel().getColumn(5).setMaxWidth(120);
	tablaFacturas.getColumnModel().getColumn(6).setMinWidth(80);
	tablaFacturas.getColumnModel().getColumn(6).setMaxWidth(120);
	tablaFacturas.getColumnModel().getColumn(7).setMinWidth(80);
	tablaFacturas.getColumnModel().getColumn(7).setMaxWidth(120);
	tablaFacturas.getColumnModel().getColumn(8).setMinWidth(80);
	tablaFacturas.getColumnModel().getColumn(8).setMaxWidth(120);
	tablaFacturas.getColumnModel().getColumn(9).setMinWidth(80);
	tablaFacturas.getColumnModel().getColumn(9).setMaxWidth(120);
	tablaFacturas.getColumnModel().getColumn(10).setMinWidth(20);
	tablaFacturas.getColumnModel().getColumn(10).setMaxWidth(40);
	tablaFacturas.getColumnModel().getColumn(11).setMinWidth(80);
	tablaFacturas.getColumnModel().getColumn(11).setMaxWidth(120);
	tablaFacturas.getColumnModel().getColumn(12).setMinWidth(80);
	tablaFacturas.getColumnModel().getColumn(12).setMaxWidth(120);
	tablaFacturas.getColumnModel().getColumn(13).setMinWidth(20);
	tablaFacturas.getColumnModel().getColumn(13).setMaxWidth(20);
	tablaFacturas.getColumnModel().getColumn(14).setMinWidth(50);
	tablaFacturas.getColumnModel().getColumn(14).setMaxWidth(50);
	tablaFacturas.getColumnModel().getColumn(15).setMinWidth(50);
	tablaFacturas.getColumnModel().getColumn(15).setMaxWidth(500);
	tablaFacturas.getColumnModel().getColumn(16).setMinWidth(20);
	tablaFacturas.getColumnModel().getColumn(16).setMaxWidth(20);
	tablaFacturas.getColumnModel().getColumn(17).setMinWidth(50);
	tablaFacturas.getColumnModel().getColumn(17).setMaxWidth(50);
	tablaFacturas.getColumnModel().getColumn(18).setMinWidth(50);
	tablaFacturas.getColumnModel().getColumn(18).setMaxWidth(50);
	tablaFacturas.getColumnModel().getColumn(19).setMinWidth(50);
	tablaFacturas.getColumnModel().getColumn(19).setMaxWidth(50);
	tablaFacturas.getColumnModel().getColumn(20).setMinWidth(50);
	tablaFacturas.getColumnModel().getColumn(20).setMaxWidth(50);
	tablaFacturas.getColumnModel().getColumn(21).setMinWidth(50);
	tablaFacturas.getColumnModel().getColumn(21).setMaxWidth(50);
	tablaFacturas.getColumnModel().getColumn(22).setMinWidth(50);
	tablaFacturas.getColumnModel().getColumn(22).setMaxWidth(50);
	tablaFacturas.getColumnModel().getColumn(23).setMinWidth(50);
	tablaFacturas.getColumnModel().getColumn(23).setMaxWidth(50);
	tablaFacturas.getColumnModel().getColumn(24).setMinWidth(50);
	tablaFacturas.getColumnModel().getColumn(24).setMaxWidth(50);
	tablaFacturas.getColumnModel().getColumn(25).setMinWidth(80);
	tablaFacturas.getColumnModel().getColumn(25).setMaxWidth(120);
	tablaFacturas.getColumnModel().getColumn(26).setMinWidth(80);
	tablaFacturas.getColumnModel().getColumn(26).setMaxWidth(120);
	tablaFacturas.getColumnModel().getColumn(27).setMinWidth(80);
	tablaFacturas.getColumnModel().getColumn(27).setMaxWidth(120);
	tablaFacturas.getColumnModel().getColumn(28).setMinWidth(20);
	tablaFacturas.getColumnModel().getColumn(28).setMaxWidth(20);

	
//	TableCellRenderer render = new TableCellRenderer() { 
//		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, 
//		boolean hasFocus, int row, int column) { 
//			
//			Component componente = null;
//			
//			switch(column){
//				case 0: 
//					componente = new JLabel(value == null? "": value.toString());
//					if(row %2 == 0){
//						((JComponent) componente).setOpaque(true); 
//						componente.setBackground(new java.awt.Color(177,177,177));	
//					}
//					if(Boolean.parseBoolean(modelo.getValueAt(row,2).toString())){
//						((JComponent) componente).setOpaque(true); 
//						componente.setBackground(new java.awt.Color(186,143,73));
//					}
//					if(table.getSelectedRow() == row){
//						((JComponent) componente).setOpaque(true); 
//						componente.setBackground(new java.awt.Color(186,143,73));
//					}
//					((JLabel) componente).setHorizontalAlignment(SwingConstants.LEFT);
//					break;
//				case 1: 
//					componente = new JLabel(value == null? "": value.toString());
//					if(row %2 == 0){
//						((JComponent) componente).setOpaque(true); 
//						componente.setBackground(new java.awt.Color(177,177,177));	
//					}
//					if(Boolean.parseBoolean(modelo.getValueAt(row,2).toString())){
//						((JComponent) componente).setOpaque(true); 
//						componente.setBackground(new java.awt.Color(186,143,73));
//					}
//					if(table.getSelectedRow() == row){
//						((JComponent) componente).setOpaque(true); 
//						componente.setBackground(new java.awt.Color(186,143,73));
//					}
//					((JLabel) componente).setHorizontalAlignment(SwingConstants.LEFT);
//					break;
//				case 2: 
//					componente = new JLabel(value == null? "": value.toString());
//					if(row %2 == 0){
//						((JComponent) componente).setOpaque(true); 
//						componente.setBackground(new java.awt.Color(177,177,177));	
//					}
//					if(Boolean.parseBoolean(modelo.getValueAt(row,2).toString())){
//						((JComponent) componente).setOpaque(true); 
//						componente.setBackground(new java.awt.Color(186,143,73));
//					}
//					if(table.getSelectedRow() == row){
//						((JComponent) componente).setOpaque(true); 
//						componente.setBackground(new java.awt.Color(186,143,73));
//					}
//					((JLabel) componente).setHorizontalAlignment(SwingConstants.LEFT);
//					break;
//				case 3: 
//					componente = new JLabel(value == null? "": value.toString());
//					if(row %2 == 0){
//						((JComponent) componente).setOpaque(true); 
//						componente.setBackground(new java.awt.Color(177,177,177));	
//					}
//					if(Boolean.parseBoolean(modelo.getValueAt(row,2).toString())){
//						((JComponent) componente).setOpaque(true); 
//						componente.setBackground(new java.awt.Color(186,143,73));
//					}
//					if(table.getSelectedRow() == row){
//						((JComponent) componente).setOpaque(true); 
//						componente.setBackground(new java.awt.Color(186,143,73));
//					}
//					((JLabel) componente).setHorizontalAlignment(SwingConstants.LEFT);
//					break;
//				case 4: 
//					componente = new JLabel(value == null? "": value.toString());
//					if(row %2 == 0){
//						((JComponent) componente).setOpaque(true); 
//						componente.setBackground(new java.awt.Color(177,177,177));	
//					}
//					if(Boolean.parseBoolean(modelo.getValueAt(row,2).toString())){
//						((JComponent) componente).setOpaque(true); 
//						componente.setBackground(new java.awt.Color(186,143,73));
//					}
//					if(table.getSelectedRow() == row){
//						((JComponent) componente).setOpaque(true); 
//						componente.setBackground(new java.awt.Color(186,143,73));
//					}
//					((JLabel) componente).setHorizontalAlignment(SwingConstants.LEFT);
//					break;
//				case 5: 
//					componente = new JLabel(value == null? "": value.toString());
//					if(row %2 == 0){
//						((JComponent) componente).setOpaque(true); 
//						componente.setBackground(new java.awt.Color(177,177,177));	
//					}
//					if(Boolean.parseBoolean(modelo.getValueAt(row,2).toString())){
//						((JComponent) componente).setOpaque(true); 
//						componente.setBackground(new java.awt.Color(186,143,73));
//					}
//					if(table.getSelectedRow() == row){
//						((JComponent) componente).setOpaque(true); 
//						componente.setBackground(new java.awt.Color(186,143,73));
//					}
//					((JLabel) componente).setHorizontalAlignment(SwingConstants.LEFT);
//					break;
//				case 6: 
//					componente = new JCheckBox("",Boolean.parseBoolean(value.toString()));
//					if(row%2==0){
//						((JComponent) componente).setOpaque(true); 
//						componente.setBackground(new java.awt.Color(177,177,177));	
//					}
//					if(Boolean.parseBoolean(modelo.getValueAt(row,2).toString())){
//						((JComponent) componente).setOpaque(true); 
//						componente.setBackground(new java.awt.Color(186,143,73));
//					}
//					if(table.getSelectedRow() == row){
//						((JComponent) componente).setOpaque(true); 
//						componente.setBackground(new java.awt.Color(186,143,73));
//					}
//					((AbstractButton) componente).setHorizontalAlignment(SwingConstants.CENTER);
//					break;
//			}
//			return componente;
//		} 
//	}; 
	
//		            	tabla.getColumnModel().getColumn(0).setCellRenderer(render); 
//						tabla.getColumnModel().getColumn(1).setCellRenderer(render); 
//						tabla.getColumnModel().getColumn(2).setCellRenderer(render);
//						tabla.getColumnModel().getColumn(3).setCellRenderer(render);
//						tabla.getColumnModel().getColumn(4).setCellRenderer(render);
//						tabla.getColumnModel().getColumn(5).setCellRenderer(render);
//						tabla.getColumnModel().getColumn(6).setCellRenderer(render);
	
//    TableCellRenderer render = new TableCellRenderer() { 
//		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, 
//		boolean hasFocus, int row, int column) { 
//          		Component componente = null;
//					componente = new JLabel(value == null? "": value.toString());
//				
//					if (column==28){
//						componente = new JCheckBox("",Boolean.parseBoolean(value.toString()));
//						if(row%2==0){
//							((JComponent) componente).setOpaque(true); 
//							componente.setBackground(new java.awt.Color(177,177,177));	
//						}
//						if(Boolean.parseBoolean(modelo.getValueAt(row,2).toString())){
//							((JComponent) componente).setOpaque(true); 
//							componente.setBackground(new java.awt.Color(186,143,73));
//						}
//						if(table.getSelectedRow() == row){
//							((JComponent) componente).setOpaque(true); 
//							componente.setBackground(new java.awt.Color(186,143,73));
//						}
//						
//					}else{	
//						
//						componente = new JLabel(value == null? "": value.toString());
//						if(row %2 == 0){
//							((JComponent) componente).setOpaque(true); 
//							componente.setBackground(new java.awt.Color(177,177,177));	
//						}
//						if(Boolean.parseBoolean(modelo.getValueAt(row,2).toString())){
//							((JComponent) componente).setOpaque(true); 
//							componente.setBackground(new java.awt.Color(186,143,73));
//						}
//						if(table.getSelectedRow() == row){
//							((JComponent) componente).setOpaque(true); 
//							componente.setBackground(new java.awt.Color(186,143,73));
//						}
//					}
//					((JLabel) componente).setHorizontalAlignment(SwingConstants.RIGHT);
//			return componente;
					
			
			
//		} 
//	}; 

//	for(int i=0; i<tablaFacturas.getColumnCount(); i++){
//	    this.tablaFacturas.getColumnModel().getColumn(i).setCellRenderer(render); 
//	}

	refrestabla("30/08/2014 00:00:00","31/08/2014 23:59:59");
		 JScrollPane scrol = new JScrollPane(tablaFacturas);
	    return scrol; 
	}
	
	private void refrestabla(String FI, String FF){
		Statement s;
		ResultSet rs;
		Connexion con = new Connexion();
		try {
			s = con.conexion_IZAGAR().createStatement();
			rs = s.executeQuery("exec sp_reporte_de_facturas_en_un_periodo_con_tasas '"+FI+"','"+FF+"'");
			while (rs.next())
			{ 
			   String [] fila = new String[29];
			   fila[0] = rs.getString(1).trim();
			   fila[1] = rs.getString(2).trim();
			   fila[2] = rs.getString(3).trim(); 
			   fila[3] = rs.getString(4).trim(); 
			   fila[4] = rs.getString(5).trim(); 
			   fila[5] = rs.getString(6).trim();
			   fila[6] = rs.getString(7).trim();
			   fila[7] = rs.getString(8).trim();
			   fila[8] = rs.getString(9).trim();
			   fila[9] = rs.getString(10).trim();
			   fila[10] = rs.getString(11).trim();
			   fila[11] = rs.getString(12).trim();
			   fila[12] = rs.getString(13).trim();
			   fila[13] = rs.getString(14).trim();
			   fila[14] = rs.getString(15).trim();
			   fila[15] = rs.getString(16).trim();
			   fila[16] = rs.getString(17).trim();
			   fila[17] = rs.getString(18).trim();
			   fila[18] = rs.getString(19).trim();
			   fila[19] = rs.getString(20).trim();
			   fila[20] = rs.getString(21).trim();
			   fila[21] = rs.getString(22).trim();
			   fila[22] = rs.getString(23).trim();
			   fila[23] = rs.getString(24).trim();
			   fila[24] = rs.getString(25).trim();
			   fila[25] = rs.getString(26).trim();
			   fila[26] = rs.getString(27).trim();
			   fila[27] = rs.getString(28).trim();
//			   fila[28] = "true";
			   modelo.addRow(fila); 
			}	
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en Cat_Relacion_De_Facturas_En_Un_Periodo en la funcion ObtenerPanelTabla  SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	
	ActionListener opGenerar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {

		if(validar_fechas().equals("")){
			String fecha_inicio = new SimpleDateFormat("dd/MM/yyyy").format(c_inicio.getDate())+" 00:00:00";
			String fecha_final = new SimpleDateFormat("dd/MM/yyyy").format(c_final.getDate())+" 23:59:59";
			
			if(c_inicio.getDate().before(c_final.getDate())){
				while(tablaFacturas.getRowCount()>0){
					modelo.removeRow(0);  }
				
//				new Cat_Reporte_General_de_Asistencia_Por_Establecimiento(fecha_inicio,fecha_final,Establecimiento,Departamento,folio_empleado);
			 }else{
				JOptionPane.showMessageDialog(null,"El Rango de Fechas Esta Invertido","Aviso!", JOptionPane.WARNING_MESSAGE);
				return;
			}
	 	}else{
			JOptionPane.showMessageDialog(null,"Los siguientes campos están vacíos: "+validar_fechas(),"Aviso!", JOptionPane.ERROR_MESSAGE);
			return;
		}
		}
	};
	
	public String validar_fechas(){
		String error = "";
		String fechainicioNull = c_inicio.getDate()+"";
		String fechafinalNull = c_final.getDate()+"";
	    if(fechainicioNull.equals("null"))error+= "Fecha  inicio\n";
		if(fechafinalNull.equals("null"))error+= "Fecha Final\n";
		return error;
	}
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Relacion_De_Facturas_En_Un_Periodo().setVisible(true);
		}catch(Exception e){	}
	}
}
