package Cat_Filtros_IZAGAR;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Cat_Reportes.Cat_Reportes_De_Apartados;
import Cat_Reportes.Cat_Reportes_De_Vouchers;
import Conexiones_SQL.BuscarTablasModel;
import Conexiones_SQL.Connexion;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.Componentes;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_Filtro_Dinamico;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Filtro_De_Busqueda_De_Asignaciones extends JDialog {
		Container cont = getContentPane();
		JLayeredPane panel = new JLayeredPane();
		
		Connexion con = new Connexion();
		
		public Object[][] get_tabla(){
			return new BuscarTablasModel().tabla_model_asignaciones();
		}
		
	    public DefaultTableModel tabla_model = new DefaultTableModel(
	    		get_tabla(),	new String[]{	"Asignacion",	"Folio de Corte", "Cajera(o)", "Establecimiento", "*"}){
	                    @SuppressWarnings("rawtypes")
	                    Class[] types = new Class[]{
	                               java.lang.Object.class, 
	                               java.lang.Object.class, 
	                               java.lang.Object.class,  
	                               java.lang.Object.class,  
	                               java.lang.Boolean.class
	                };
	                    @SuppressWarnings({ "unchecked", "rawtypes" })
						public Class getColumnClass(int columnIndex) {
	                            return types[columnIndex];
	                    }
	                public boolean isCellEditable(int fila, int columna){
	                            switch(columna){
	                                    case 0  : return false; 
	                                    case 1  : return false; 
	                                    case 2  : return false; 
	                                    case 3  : return false; 
	                                    case 4  : return true; 
	                            }
	                             return false;
	                     }
	            };
		
		JTable tabla = new JTable(tabla_model);
		JScrollPane panelScroll = new JScrollPane(tabla);
		
		@SuppressWarnings("rawtypes")
		private TableRowSorter trsfiltro;
  	    JTextField txtNombre = new Componentes().text(new JCTextField(), "Nombre Cajero", 200, "String");
 		JTextField txtFolioAsignacion = new JTextField();
		JTextField txtFolioCorte = new JTextField();
		String establecimientos[] = new Obj_Establecimiento().Combo_Establecimiento();
		JButton btnCargar = new JButton("Cargar");
		@SuppressWarnings({ "unchecked", "rawtypes" })
		JComboBox cmbEstablecimientos = new JComboBox(establecimientos);
		
	Border blackline, etched, raisedbevel, loweredbevel, empty;
	String valor_catalogo="";
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Cat_Filtro_De_Busqueda_De_Asignaciones(String bandera_origen_consulta_filtro){
		        valor_catalogo=bandera_origen_consulta_filtro;
				
		        this.setSize(755,415);
				this.setResizable(false);
				this.setLocationRelativeTo(null);
				this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				this.setModal(true);
				this.setTitle("Filtro de Asignaciones Liquidadas");
				blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
				this.panel.setBorder(BorderFactory.createTitledBorder(blackline,"Seleccione La Asignacion"));
			    setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Filter-List-icon32.png"));

			    this.init_tabla();
				
				trsfiltro = new TableRowSorter(tabla_model); 
				tabla.setRowSorter(trsfiltro);  
				
				panel.add(panelScroll).setBounds(15,42,720,327);
				
				panel.add(txtFolioAsignacion).setBounds(15,20,80,20);
				panel.add(txtFolioCorte).setBounds(96,20,79,20);
				


				panel.add(txtNombre).setBounds(176,20,320,20);
				
				panel.add(cmbEstablecimientos).setBounds(497,20, 170, 20);
				panel.add(btnCargar).setBounds(668,20, 65, 20);
				
				cont.add(panel);
				
				txtFolioAsignacion.addKeyListener(opFiltroFolio);
				txtFolioCorte.addKeyListener(opFiltroFolioCorte);
				txtNombre.addKeyListener(opFiltroNombre);
				
				cmbEstablecimientos.addActionListener(opFiltro);
				btnCargar.addActionListener(opCargar);
	}
	
ActionListener opCargar = new ActionListener() {
	public void actionPerformed(ActionEvent arg0) {
 		if(tabla.isEditing()){
			tabla.getCellEditor().stopCellEditing();
		}
		String cadena = "";
		for(int i = 0; i<tabla.getRowCount(); i++){
			
			if(tabla.getValueAt(i, 4).toString().equals("true")){
				cadena+=tabla.getValueAt(i, 0).toString().trim();
			}
		 }
		
		if(valor_catalogo.equals("Reportes De Vouchers")){
				new Cat_Reportes_De_Vouchers(cadena).setVisible(true);
		        dispose();
		}
		if(valor_catalogo.equals("Reportes De Apartados")){
			new Cat_Reportes_De_Apartados(cadena).setVisible(true);
	        dispose();
	}
	}
};

KeyListener opFiltroFolio = new KeyListener(){
	public void keyReleased(KeyEvent arg0) {
		new Obj_Filtro_Dinamico(tabla, "Asignacion",txtFolioAsignacion.getText().toUpperCase().toString(),"Folio de Corte", txtFolioCorte.getText().toUpperCase().toString(), "Cajera(o)",txtNombre.getText().toUpperCase().toString(), "Establecimiento",cmbEstablecimientos.getSelectedItem().toString());
	}
	public void keyTyped(KeyEvent arg0) {}
	public void keyPressed(KeyEvent arg0) {}
	
};

KeyListener opFiltroFolioCorte = new KeyListener(){
	public void keyReleased(KeyEvent arg0) {
		new Obj_Filtro_Dinamico(tabla, "Asignacion",txtFolioAsignacion.getText().toUpperCase().toString(),"Folio de Corte", txtFolioCorte.getText().toUpperCase().toString(), "Cajera(o)",txtNombre.getText().toUpperCase().toString(), "Establecimiento",cmbEstablecimientos.getSelectedItem().toString());
		}
	public void keyTyped(KeyEvent arg0) {}
	public void keyPressed(KeyEvent arg0) {}
	
};

KeyListener opFiltroNombre = new KeyListener(){
	public void keyReleased(KeyEvent arg0) {
		new Obj_Filtro_Dinamico(tabla, "Asignacion",txtFolioAsignacion.getText().toUpperCase().toString(),"Folio de Corte", txtFolioCorte.getText().toUpperCase().toString(), "Cajera(o)",txtNombre.getText().toUpperCase().toString(), "Establecimiento",cmbEstablecimientos.getSelectedItem().toString());
			}
	public void keyTyped(KeyEvent arg0) {}
	public void keyPressed(KeyEvent arg0) {}
	
};

ActionListener opFiltro = new ActionListener(){
	public void actionPerformed(ActionEvent arg0){
		new Obj_Filtro_Dinamico(tabla, "Asignacion",txtFolioAsignacion.getText().toUpperCase().toString(),"Folio de Corte", txtFolioCorte.getText().toUpperCase().toString(), "Cajera(o)",txtNombre.getText().toUpperCase().toString(), "Establecimiento",cmbEstablecimientos.getSelectedItem().toString());
		}
};

public void init_tabla(){
    this.tabla.getTableHeader().setReorderingAllowed(false) ;
    
    	   int w=100;
           int x=80;
           int y=320;
           int z=50;
           
            this.tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            
            this.tabla.getColumnModel().getColumn(0).setMaxWidth(x);
            this.tabla.getColumnModel().getColumn(0).setMinWidth(x);
            this.tabla.getColumnModel().getColumn(1).setMaxWidth(x);
            this.tabla.getColumnModel().getColumn(1).setMinWidth(x);
            this.tabla.getColumnModel().getColumn(2).setMaxWidth(y);
            this.tabla.getColumnModel().getColumn(2).setMinWidth(y);
            this.tabla.getColumnModel().getColumn(3).setMaxWidth(w+70);
            this.tabla.getColumnModel().getColumn(3).setMinWidth(w+70);
            this.tabla.getColumnModel().getColumn(4).setMaxWidth(z);
            this.tabla.getColumnModel().getColumn(4).setMinWidth(z);
            
            this.tabla.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer( "texto" ,"izquierda",	"arial", "normal",12));
            this.tabla.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer( "texto" ,"izquierda",	"arial", "normal",12));
            this.tabla.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer( "texto" ,"izquierda",	"arial", "normal",12));
            this.tabla.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer( "texto" ,"izquierda",	"arial", "normal",12));
            this.tabla.getColumnModel().getColumn(4).setCellRenderer(new tablaRenderer( "CHB" ,"centro",	"arial", "normal",12));
}
		public static void main(String args[]){
			try{
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				new Cat_Filtro_De_Busqueda_De_Asignaciones("Reporte_De_Ventas").setVisible(true);
			}catch(Exception e){	}
		}
		
}
