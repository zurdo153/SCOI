package Cat_Auditoria;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Conexiones_SQL.Connexion;
import Obj_Auditoria.Obj_Alimentacion_Cortes;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.Componentes;
import Obj_Principal.JCTextField;
import Obj_Renders.tablaRenderer;

@SuppressWarnings({ "serial", "unchecked" })
public class Cat_Cortes_De_Cajeros extends JFrame{
	
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
	
	JTextField txtBuscar = new Componentes().text(new JCTextField(), "Busqueda Por Nombre de Cajero", 50, "String");
	
	String establecimiento[] = new Obj_Establecimiento().Combo_Establecimiento();
	@SuppressWarnings("rawtypes")
	JComboBox cmbEstablecimiento = new JComboBox(establecimiento);
	
	public Cat_Cortes_De_Cajeros()	{
		Constructor();
	}
	
	public Cat_Cortes_De_Cajeros(String establecimiento)	{
		cmbEstablecimiento.setSelectedItem(establecimiento);
		Constructor();
		
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened( WindowEvent e ){
          	  txtBuscar.requestFocus();
           }
        });
	}
	
	@SuppressWarnings("rawtypes")
	public void Constructor(){
		this.setTitle("Filtro Cortes ");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/bolsa-de-dinero-en-efectivo-icono-6673-64.png"));
		
		txtBuscar.addKeyListener(new KeyAdapter() { 
			public void keyReleased(final KeyEvent e) { 
                filtro(); 
            } 
        });
	
		trsfiltro = new TableRowSorter(model); 
		tabla.setRowSorter(trsfiltro);  
		
		campo.add(new JLabel("ESTABLECIMIENTO:")).setBounds(15,15,120,20);
		campo.add(new JLabel(new ImageIcon("imagen/folder-home-home-icone-5663-16.png"))).setBounds(120,15,20,20); 
		campo.add(cmbEstablecimiento).setBounds(138, 15, 235, 20);
		campo.add(new JLabel("BUSCAR : ")).setBounds(15,40,70,20);
		campo.add(txtBuscar).setBounds(80,40,295,20);
		campo.add(getPanelTabla()).setBounds(10,60,365,450);
		cmbEstablecimiento.addKeyListener(op_key);
		agregar(tabla);
		cont.add(campo);
		
		this.setSize(390,550);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
	}
	
	KeyListener op_key = new KeyListener() {
	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {

		//  guardar al presionar la tecla f2
	    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
	       KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "foco");
	    
	    getRootPane().getActionMap().put("foco", new AbstractAction(){
		        public void actionPerformed(ActionEvent e)
		        {
		    		if(cmbEstablecimiento.getSelectedIndex()>0){
		    			txtBuscar.requestFocus();
		    		} 	
		        }
	    });
	}
	public void keyPressed(KeyEvent e) {}
};

	private void agregar(final JTable tbl) {
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
	        public void mouseClicked(MouseEvent e) {
	        	if(e.getClickCount() == 2){
	        		
	        		if(cmbEstablecimiento.getSelectedIndex()==0){
	        		  JOptionPane.showMessageDialog(null, "Seleccione un Establecimineto", "Aviso!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
	        			cmbEstablecimiento.requestFocus();
	        			cmbEstablecimiento.showPopup();
	    				return;
	        		}else{
		        			int fila = tabla.getSelectedRow();
		        			String estab = cmbEstablecimiento.getSelectedItem().toString().trim()+"";
		        					Obj_Alimentacion_Cortes generarFolioCorteNuevo = new Obj_Alimentacion_Cortes();
		        					if(generarFolioCorteNuevo.generar_folio_corte()){
					        			String folio_corte = generarFolioCorteNuevo.buscar(estab);
//		        					        String folio_corte="PRUEBA";//quitar
						        			Object folio =  tabla.getValueAt(fila, 0);
						        			dispose();
						        			new Cat_Alimentacion_Cortes(Integer.parseInt(folio+""),estab,folio_corte).setVisible(true);
						        			
				        			}else{
				        				JOptionPane.showMessageDialog(null, "No se genero el folio de corte correctamente", "Aviso!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
					    				return;
				        			}
	        		}
	        	}
	        }
        });
    }
	
	public void filtro() { 
			trsfiltro.setRowFilter(RowFilter.regexFilter(txtBuscar.getText().toUpperCase().trim(), 1));
	}  
	private JScrollPane getPanelTabla()	{		
		new Connexion();
		
		this.tabla.getTableHeader().setReorderingAllowed(false) ;
		this.tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tabla.getColumnModel().getColumn(0).setHeaderValue("Folio");
		tabla.getColumnModel().getColumn(0).setMaxWidth(70);
		tabla.getColumnModel().getColumn(0).setMinWidth(70);
		tabla.getColumnModel().getColumn(1).setHeaderValue("Nombre Completo");
		tabla.getColumnModel().getColumn(1).setMaxWidth(285);
		tabla.getColumnModel().getColumn(1).setMinWidth(285);
		
		for(int i=0; i<tabla.getColumnCount(); i++){
			tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",12));
		}
		
		Statement s;
		ResultSet rs;
		try {
			s = con.conexion().createStatement();
			rs = s.executeQuery("exec sp_select_empleados_en_cortes" );
			while (rs.next())
			{ 
			   String [] fila = new String[2];
			   fila[0] = rs.getString(1).trim();
			   fila[1] = rs.getString(2).trim();
			   
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
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			new Cat_Cortes_De_Cajeros().setVisible(true);
		}catch(Exception e){}
		
		
	}
}
