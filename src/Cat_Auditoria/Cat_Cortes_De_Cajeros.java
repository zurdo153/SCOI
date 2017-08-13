package Cat_Auditoria;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Conexiones_SQL.Connexion;
import Obj_Auditoria.Obj_Alimentacion_Cortes;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.Componentes;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_tabla;

@SuppressWarnings({ "serial", "unchecked" })
public class Cat_Cortes_De_Cajeros extends JFrame{
	Container cont = getContentPane();
	JLayeredPane campo = new JLayeredPane();
	Connexion con = new Connexion();
    JTextField txtFiltrof = new Componentes().text(new JCTextField(), "Teclea Aqui Para Buscar En La Tabla", 500, "String");
    
	Obj_tabla ObjTabf =new Obj_tabla();
	int columnas = 2,checkbox=-1;
	public void init_tablaf(){
    	this.tabla.getColumnModel().getColumn(0).setMinWidth(65);
    	this.tabla.getColumnModel().getColumn(1).setMinWidth(270);
		String comandof="exec sp_select_empleados_en_cortes";
		String basedatos="26",pintar="si";
		ObjTabf.Obj_Refrescar(tabla,model, columnas, comandof, basedatos,pintar,checkbox);
    }
	
	@SuppressWarnings("rawtypes")
	public Class[] base (){
		Class[] types = new Class[columnas];
		for(int i = 0; i<columnas; i++){types[i]= java.lang.Object.class;}
		return types;
	}
	
	 public DefaultTableModel model = new DefaultTableModel(null, new String[]{"Folio","Nombre Colaborador"}){
		 @SuppressWarnings("rawtypes")
			Class[] types = base();
			@SuppressWarnings({ "rawtypes" })
			public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
			public boolean isCellEditable(int fila, int columna){return false;}
	    };
	    JTable tabla = new JTable(model);
		public JScrollPane scroll_tablaf = new JScrollPane(tabla);
	     @SuppressWarnings("rawtypes")
	    private TableRowSorter trsfiltro;
	
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
            	txtFiltrof.requestFocus();
           }
        });
	}
	
	@SuppressWarnings("rawtypes")
	public void Constructor(){
		this.setSize(390,550);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("Filtro Cortes ");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/bolsa-de-dinero-en-efectivo-icono-6673-64.png"));
		
		trsfiltro = new TableRowSorter(model); 
		tabla.setRowSorter(trsfiltro); 
		init_tablaf();
		
		campo.add(new JLabel("Establecimiento:")).setBounds(15,15,120,20);
		campo.add(new JLabel(new ImageIcon("imagen/folder-home-home-icone-5663-16.png"))).setBounds(120,15,20,20); 
		campo.add(cmbEstablecimiento).setBounds (138, 15, 235, 20);
		campo.add(txtFiltrof).setBounds         (10,40,365,20);
		campo.add(scroll_tablaf).setBounds      (10,60,365,450);
		cmbEstablecimiento.addKeyListener(op_key);
		
		txtFiltrof.addKeyListener(opFiltrof);
		agregar(tabla);
		cont.add(campo);
		
	}
	
	KeyListener opFiltrof = new KeyListener(){
		public void keyReleased(KeyEvent arg0) {
			ObjTabf.Obj_Filtro(tabla, txtFiltrof.getText().toUpperCase(), columnas);
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}		
	};
	
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
		    			txtFiltrof.requestFocus();
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
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			new Cat_Cortes_De_Cajeros().setVisible(true);
		}catch(Exception e){}
		
		
	}
}
