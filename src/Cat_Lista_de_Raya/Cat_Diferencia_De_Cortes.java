package Cat_Lista_de_Raya;

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

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
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
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.Componentes;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Diferencia_De_Cortes extends JFrame {
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	Connexion con = new Connexion();
	
	DefaultTableModel model = new DefaultTableModel(0,5){
		public boolean isCellEditable(int fila, int columna){
			if(columna < 0)
				return true;
			return false;
		}
	};
	
	JTable tabla = new JTable(model);
	
	@SuppressWarnings("rawtypes")
	private TableRowSorter trsfiltro;
	
	JTextField txtFolio = new Componentes().text(new JTextField(), "Folio", 10, "Int");

	JTextField txtNombre_Completo = new JTextField();
	String establecimientos[] = new Obj_Establecimiento().Combo_Establecimiento_Cajeras();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbEstablecimientos = new JComboBox(establecimientos);
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Cat_Diferencia_De_Cortes()	{
		this.setTitle("Filtro de Diferencia de Cortes");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Filter-List-icon32.png"));
		panel.setBorder(BorderFactory.createTitledBorder("Filtro Diferencia de Cortes"));
		this.setSize(645,768);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		trsfiltro = new TableRowSorter(model); 
		tabla.setRowSorter(trsfiltro);  

		panel.add(getPanelTabla()).setBounds(15,42,605,680);
		agregar(tabla);
		panel.add(txtFolio).setBounds(15,20,69,20);
		panel.add(txtNombre_Completo).setBounds(85,20,239,20);
		panel.add(cmbEstablecimientos).setBounds(325,20, 148, 20);
	
		cont.add(panel);
		txtFolio.addKeyListener(opFiltroFolio);
		txtNombre_Completo.addKeyListener(opFiltroNombre);
		cmbEstablecimientos.addActionListener(opFiltro);
	}
	
	private void agregar(final JTable tbl) {
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
	        public void mouseClicked(MouseEvent e) {
	        	if(e.getClickCount() == 2){
	    			int fila = tabla.getSelectedRow();
	    			String folio =  tabla.getValueAt(fila, 0).toString().trim();
	    			if(Double.valueOf(tabla.getValueAt(fila, 4).toString().trim())==0){
	    				JOptionPane.showMessageDialog(null, "El Empleado Seleccionado No Cuenta Con Ninguna Diferencia De Corte Aplicada", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
	    				return;
	    			}else{
	    				new Cat_Filtro_Diferencia_De_Cortes(folio).setVisible(true);
	    			dispose();
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
	
	KeyListener opFiltroNombre = new KeyListener(){
		@SuppressWarnings("unchecked")
		public void keyReleased(KeyEvent arg0) {
			trsfiltro.setRowFilter(RowFilter.regexFilter(txtNombre_Completo.getText().toUpperCase().trim(), 1));
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}
		
	};
	
	ActionListener opFiltro = new ActionListener(){
		@SuppressWarnings("unchecked")
		public void actionPerformed(ActionEvent arg0){
			if(cmbEstablecimientos.getSelectedIndex() != 0){
				trsfiltro.setRowFilter(RowFilter.regexFilter(cmbEstablecimientos.getSelectedItem()+"", 2));
			}else{
				trsfiltro.setRowFilter(RowFilter.regexFilter("", 2));
			}
		}
	};
 
	private JScrollPane getPanelTabla()	{		
		new Connexion();

		tabla.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",11));
		tabla.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",11));
		tabla.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",11));
		tabla.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",11));
		tabla.getColumnModel().getColumn(4).setCellRenderer(new tablaRenderer("texto","derecha","Arial","negrita",12));
		tabla.getTableHeader().setReorderingAllowed(false) ;
		tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		tabla.getColumnModel().getColumn(0).setHeaderValue("Folio");
		tabla.getColumnModel().getColumn(0).setMaxWidth(65);
		tabla.getColumnModel().getColumn(0).setMinWidth(65);
		tabla.getColumnModel().getColumn(1).setHeaderValue("Nombre Completo");
		tabla.getColumnModel().getColumn(1).setMaxWidth(240);
		tabla.getColumnModel().getColumn(1).setMinWidth(240);
		tabla.getColumnModel().getColumn(2).setHeaderValue("Establecimiento");
		tabla.getColumnModel().getColumn(2).setMaxWidth(150);
		tabla.getColumnModel().getColumn(2).setMinWidth(150);
		tabla.getColumnModel().getColumn(3).setHeaderValue("Status");
		tabla.getColumnModel().getColumn(3).setMaxWidth(70);
		tabla.getColumnModel().getColumn(4).setHeaderValue("Saldo");
		tabla.getColumnModel().getColumn(4).setMaxWidth(60);
		tabla.getColumnModel().getColumn(4).setMinWidth(60);
		
		Statement s;
		ResultSet rs;
		try {
			s = con.conexion().createStatement();
			rs = s.executeQuery("exec sp_lista_diferencia_cortes");
			while (rs.next()) {
				String [] fila = new String[6];
				fila[0] = rs.getString(1)+"  ";
				fila[1] = "  "+rs.getString(2).trim();
				fila[2] = rs.getString(3).trim(); 
				fila[3] = rs.getString(4).trim();
				fila[4] = rs.getFloat(5)+""; 
				
				model.addRow(fila); 
			}	
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		JScrollPane scrol = new JScrollPane(tabla);	   
	    return scrol; 
	}
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Diferencia_De_Cortes().setVisible(true);
		}catch(Exception e){	}
	}
}

