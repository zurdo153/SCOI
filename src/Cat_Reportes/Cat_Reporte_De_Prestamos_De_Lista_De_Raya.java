package Cat_Reportes;

import java.awt.Component;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

import Conexiones_SQL.Connexion;
import Obj_Lista_de_Raya.Obj_Revision_De_Lista_Raya;
import Obj_Principal.Componentes;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

@SuppressWarnings("serial")
public class Cat_Reporte_De_Prestamos_De_Lista_De_Raya extends JFrame {
	JButton btnPrestamos_Limpio = new JButton();
	JButton btnPrestamos_Por_Establecimiento = new JButton("Impresion de Reporte De Prestamos Por Establecimiento");
	JButton btnPrestamos_Listaraya_pasadas = new JButton();
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	//declaracion de Bordes
	Border blackline, etched, raisedbevel, loweredbevel, empty;
	TitledBorder title4; 

	public Cat_Reporte_De_Prestamos_De_Lista_De_Raya() {
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/dinero-en-efectivo-cartera-monedero-icono-7127-32.png"));
		
		blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
		panel.setBorder(BorderFactory.createTitledBorder(blackline,"Seleccion Del Reporte de Prestamos de Lista de Raya Actual"));
		this.setTitle("Reportes de Prestamos");
		
		btnPrestamos_Limpio.setText(	"<html> <FONT FACE="+"arial"+" SIZE=3 COLOR=BLACk>" +
				"		<p>Impresion de Reporte de Prestamos Para</p>" +
				"		<CENTER><p>Por Empleado de Lista de Raya Actual</p></CENTER></FONT>" +
				"</html>"); 
		
		btnPrestamos_Por_Establecimiento.setText(	"<html> <FONT FACE="+"arial"+" SIZE=3 COLOR=BLACk>" +
				"		<p>Impresion de Reporte De Prestamos Por</p>" +
				"		<CENTER><p>Establecimiento de Lista de Raya Actual</p></CENTER></FONT>" +
				"</html>"); 
		
		btnPrestamos_Listaraya_pasadas.setText(	"<html> <FONT FACE="+"arial"+" SIZE=3 COLOR=BLACk>" +
				"		<p>Impresion de Reporte De Prestamos Por</p>" +
				"		<CENTER><p>Establecimiento de Listas de Raya Pasadas</p></CENTER></FONT>" +
				"</html>"); 
		
		
		panel.add(btnPrestamos_Limpio).setBounds(40,40,280,40);
		panel.add(btnPrestamos_Por_Establecimiento).setBounds(40,100,280,40);
		panel.add(btnPrestamos_Listaraya_pasadas).setBounds(40,160,280,40);
		
		
		btnPrestamos_Limpio.addActionListener(Reporte_Prestamos_Lista_de_Raya_Actual_limpio);
		btnPrestamos_Por_Establecimiento.addActionListener(Reporte_Prestamos_Lista_de_Raya_Actual_Por_Establecimiento);
		btnPrestamos_Listaraya_pasadas.addActionListener(Reporte_Prestamos_de_Listas_de_Raya_Pasadas_Por_Establecimiento);
		cont.add(panel);
		this.setSize(365,260);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
	}
	
	ActionListener Reporte_Prestamos_Lista_de_Raya_Actual_Por_Establecimiento = new ActionListener(){
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public void actionPerformed(ActionEvent e){
			try {
				JasperReport report = JasperCompileManager.compileReport(System.getProperty("user.dir")+"\\src\\Obj_Reportes\\Obj_Reporte_De_Prestamos_De_Lista_De_Raya_Actual.jrxml");
				
				JasperPrint print = JasperFillManager.fillReport(report, new HashMap(), new Connexion().conexion());
				JasperViewer.viewReport(print, false);
			} catch (Exception e1) {
				System.out.println(e1.getMessage());
				JOptionPane.showMessageDialog(null, "Error en Cat_Reporte_De_Prestamos_De_Lista_De_Raya_Actual  en la funcion [ ActionListener Reporte_Prestamos_Lista_de_Raya_Actual_Por_Establecimiento ]   SQLException:  "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);

			}
		}
	};
	
	
	ActionListener Reporte_Prestamos_Lista_de_Raya_Actual_limpio = new ActionListener(){
		@SuppressWarnings("rawtypes")
		public void actionPerformed(ActionEvent e){
				try {
					JasperReport report = JasperCompileManager.compileReport(System.getProperty("user.dir")+"\\src\\Obj_Reportes\\Obj_Reporte_De_Prestamos_De_Lista_De_Raya_Actual_Para_Exportar.jrxml");
					@SuppressWarnings("unchecked")
					JasperPrint print = JasperFillManager.fillReport(report, new HashMap(), new Connexion().conexion());
					JasperViewer.viewReport(print, false);
				} catch (Exception e2) {
					System.out.println(e2.getMessage());
					JOptionPane.showMessageDialog(null, "Error en Cat_Reporte_De_Prestamos_De_Lista_De_Raya_Actual  en la funcion [ ActionListener Reporte_Depositos_Bancos_limpio ]   SQLException:  "+e2.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
		}
	};
	
	
	ActionListener Reporte_Prestamos_de_Listas_de_Raya_Pasadas_Por_Establecimiento = new ActionListener(){
		public void actionPerformed(ActionEvent e){
            new Cat_Reporte_de_Prestamos_de_Lista_de_Raya_Pasadas().setVisible(true);     
		}
	};
	
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////CATALOGO DE PRESTAMOS DE LISTA DE RAYA PASADAS
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public class Cat_Reporte_de_Prestamos_de_Lista_de_Raya_Pasadas extends JDialog {
		
		Container cont = getContentPane();
		JLayeredPane campo = new JLayeredPane();
		Connexion con = new Connexion();
		
		DefaultTableModel model = new DefaultTableModel(0,2){
			public boolean isCellEditable(int fila, int columna){
				if(columna < 0)
					return true;
				return false;
			}
		};
		
		JTable tabla = new JTable(model);
		@SuppressWarnings("rawtypes")
		private TableRowSorter trsfiltro;
		
		JTextField txtFolio = new Componentes().text(new JTextField(),"Teclee Folio Lista de Raya", 150, "Integer");
		JTextField txtFecha = new Componentes().text(new JTextField(),"Teclee Fecha Final de La Lista de Raya <Fecha de Cierre>", 150, "String");
	    
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Cat_Reporte_de_Prestamos_de_Lista_de_Raya_Pasadas()	{
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/filter_icon&16.png"));
			this.setTitle("Reporte de Listas de Raya Pasadas");

			campo.setBorder(BorderFactory.createTitledBorder("Seleccione La Lista de Raya a Consultar"));
			trsfiltro = new TableRowSorter(model); 
			tabla.setRowSorter(trsfiltro);  
			campo.add(getPanelTabla()).setBounds(15,42,220,360);
			campo.add(txtFolio).setBounds(15,20,100,20);
			campo.add(txtFecha).setBounds(116,20,100,20);
			
			agregar(tabla);
			cont.add(campo);
			
			txtFolio.addKeyListener(opFiltroFolio);
			txtFecha.addKeyListener(opFiltroFecha);
			this.setSize(255,445);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			
		}
		
		private void agregar(final JTable tbl) {
	        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
		        public void mouseClicked(MouseEvent e) {
		        	if(e.getClickCount() == 2){
		        		int fila = tabla.getSelectedRow();
		    			int folio = Integer.parseInt(tabla.getValueAt(fila, 0).toString().trim());
		    				    			
		    			if (new Obj_Revision_De_Lista_Raya().Lista_de_Raya_Pasada(folio)) {
		    				dispose();
		    				Impresion_de_Reporte_Prestamos_LRPasadas(folio);
		    			}
		    			else{
		    				JOptionPane.showMessageDialog(null,"Error Al Intentar Abrir el Reporte","Error",JOptionPane.ERROR_MESSAGE);
		    			 }
		        	}
		        }
	        });
	    }
		
		
		
		KeyListener opFiltroFolio = new KeyListener(){
			@SuppressWarnings("unchecked")
			public void keyReleased(KeyEvent arg0) {
				trsfiltro.setRowFilter(RowFilter.regexFilter(txtFolio.getText(), 0));
			}
			public void keyTyped(KeyEvent arg0) {
				char caracter = arg0.getKeyChar();
				if(((caracter < '0') ||
					(caracter > '9')) &&
				    (caracter != KeyEvent.VK_BACK_SPACE)){
					arg0.consume(); 
				}	
			}
			public void keyPressed(KeyEvent arg0) {}		
		};
		
		KeyListener opFiltroFecha = new KeyListener(){
			@SuppressWarnings("unchecked")
			public void keyReleased(KeyEvent arg0) {
				trsfiltro.setRowFilter(RowFilter.regexFilter(txtFecha.getText().toUpperCase().trim(), 1));
			}
			public void keyTyped(KeyEvent arg0) {}
			public void keyPressed(KeyEvent arg0) {}		
		};
		
	   	private JScrollPane getPanelTabla()	{		
			
			DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
			tcr.setHorizontalAlignment(SwingConstants.CENTER);
			
			tabla.getColumnModel().getColumn(0).setHeaderValue("Num. Lista Raya");
			tabla.getColumnModel().getColumn(0).setMaxWidth(100);
			tabla.getColumnModel().getColumn(0).setMinWidth(100);
			tabla.getColumnModel().getColumn(1).setHeaderValue("Fecha Cierre");
			tabla.getColumnModel().getColumn(1).setMaxWidth(100);
			tabla.getColumnModel().getColumn(1).setMinWidth(100);
			
	    	tabla.getTableHeader().setReorderingAllowed(false) ;
	    	tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			
			TableCellRenderer render = new TableCellRenderer() { 
				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, 
				boolean hasFocus, int row, int column) { 
					JLabel lbl = new JLabel(value == null? "": value.toString());
					if(row%2==0){
							lbl.setOpaque(true); 
							lbl.setBackground(new java.awt.Color(177,177,177));
					} 
					
					if(table.getSelectedRow() == row){
						lbl.setOpaque(true); 
						lbl.setBackground(new java.awt.Color(186,143,73));
					}
					
					switch(column){
						case 0 : lbl.setHorizontalAlignment(SwingConstants.LEFT); break;
						case 1 : lbl.setHorizontalAlignment(SwingConstants.LEFT); break;
					}
				return lbl; 
				} 
			}; 

			this.tabla.getColumnModel().getColumn(0).setCellRenderer(render); 
			this.tabla.getColumnModel().getColumn(1).setCellRenderer(render); 
					
			Statement s;
			ResultSet rs;
			try {
				s = con.conexion().createStatement();
				rs = s.executeQuery("exec sp_filtro_lista_rayas_pasadas");
				String [] fila = new String[4];
				while (rs.next()) {
				   fila[0] = rs.getString(1)+"  ";
				   fila[1] = "   "+rs.getString(2);
				   
			  	   model.addRow(fila); 
				}	
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			 JScrollPane scrol = new JScrollPane(tabla);
			   
		    return scrol; 
		}
		
		KeyListener validaCantidad = new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e){
				char caracter = e.getKeyChar();				
				if(((caracter < '0') ||	
			    	(caracter > '9')) && 
			    	(caracter != '.' )){
			    	e.consume();
		    	}
			}
			@Override
			public void keyReleased(KeyEvent e) {	
			}
			@Override
			public void keyPressed(KeyEvent arg0) {
			}	
		};
		
		KeyListener validaNumericoConPunto = new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				char caracter = e.getKeyChar();
				
			    if(((caracter < '0') ||	
			    	(caracter > '9')) && 
			    	(caracter != '.')){
			    	e.consume();
		    	}
			}
			@Override
			public void keyPressed(KeyEvent e){}
			@Override
			public void keyReleased(KeyEvent e){}
									
		};
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public void Impresion_de_Reporte_Prestamos_LRPasadas(int folio) {
			
			String query_corte_caja = "exec sp_consulta_de_prestamos_de_listas_de_raya_pasadas '"+folio+"';";
			Statement stmt = null;
			try {
				stmt =  new Connexion().conexion().createStatement();
			    ResultSet rs = stmt.executeQuery(query_corte_caja);
				JasperReport report = JasperCompileManager.compileReport(System.getProperty("user.dir")+"\\src\\Obj_Reportes\\Obj_Reporte_De_Prestamos_De_Listas_De_Raya_Pasadas.jrxml");
				JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
				JasperPrint print = JasperFillManager.fillReport(report, new HashMap(), resultSetDataSource);
				JasperViewer.viewReport(print, false);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				JOptionPane.showMessageDialog(null, "Error En Cat_Reporte_De_Prestamos en la funcion Impresion_de_Reporte_Prestamos_LRPasadas ", "Error !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
			}
		 }
	}

	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Reporte_De_Prestamos_De_Lista_De_Raya().setVisible(true);
		}catch(Exception e){	}
	}
}
