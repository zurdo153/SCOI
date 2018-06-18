package Cat_Lista_de_Raya;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import Obj_Principal.Componentes;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_tabla;
@SuppressWarnings("serial")
public class Cat_Prestamos extends JFrame {
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	Obj_tabla  ObjTab = new Obj_tabla();
	
	int columnasog = 6,checkbox=-1;
	public void init_tabla(){
    	this.tablaog.getColumnModel().getColumn(0).setMinWidth(50);	
    	this.tablaog.getColumnModel().getColumn(0).setMaxWidth(50);	
    	this.tablaog.getColumnModel().getColumn(1).setMinWidth(245);
    	this.tablaog.getColumnModel().getColumn(2).setMinWidth(130);
    	this.tablaog.getColumnModel().getColumn(3).setMinWidth(70);
    	this.tablaog.getColumnModel().getColumn(4).setMinWidth(95);
    	this.tablaog.getColumnModel().getColumn(5).setMinWidth(72);
    	
		String comando="prestamo_select_filtro" ;
		String basedatos="26",pintar="si";
		ObjTab.Obj_Refrescar(tablaog,modelog, columnasog, comando, basedatos,pintar,checkbox);
    }
	
	@SuppressWarnings("rawtypes")
	public Class[] basemovimientos (){
		Class[] types = new Class[columnasog];
		for(int i = 0; i<columnasog; i++){types[i]= java.lang.Object.class;}
		 return types;
	}
	
	public DefaultTableModel modelog = new DefaultTableModel(null, new String[]{"Folio","Nombre Completo","Establecimiento","Estatus","Limite De Prestamo", "Saldo"}){
		 @SuppressWarnings("rawtypes")
			Class[] types = basemovimientos();
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
			public boolean isCellEditable(int fila, int columna){ return false;}
	};
	
	JTable tablaog = new JTable(modelog);
	public JScrollPane scroll_tabla = new JScrollPane(tablaog);

	JTextField txtBuscarb  = new Componentes().textfiltro(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<", 500, "String",tablaog,columnasog);
	public Cat_Prestamos() {
		this.setSize(720,415);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/prestamo.png"));
		this.setTitle("Filtro De Prestamos");
		this.panel.setBorder(BorderFactory.createTitledBorder("Filtro De Prestamos"));
		
		panel.add(txtBuscarb).setBounds  (10 ,20 ,690 ,20 );
		panel.add(scroll_tabla).setBounds(10 ,40 ,690 ,327);
		this.addWindowListener(new WindowAdapter() {public void windowOpened( WindowEvent e ){    	txtBuscarb.requestFocus();        }       });
		cont.add(panel);
		
		init_tabla();
		agregar(tablaog);
	}
	
	private void agregar(final JTable tbl) {
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
	        public void mouseClicked(MouseEvent e) {
	        	if(e.getClickCount() == 2){
	    			int fila = tablaog.getSelectedRow();
	    			Object folio =  tablaog.getValueAt(fila, 0);
	    			dispose();
	    			new Cat_Filtro_De_Prestamos(folio+"").setVisible(true);
	        	}
	        }
        });
    }
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Prestamos().setVisible(true);
		}catch(Exception e){	}
	}
}

