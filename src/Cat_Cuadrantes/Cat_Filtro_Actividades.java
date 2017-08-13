package Cat_Cuadrantes;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Obj_Principal.Componentes;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_tabla;

@SuppressWarnings("serial")
public class Cat_Filtro_Actividades extends JDialog {
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	Obj_tabla ObjTab =new Obj_tabla();
	int columnas = 13,checkbox=-1;
	public void init_tabla(){
    	this.tabla.getColumnModel().getColumn(0).setMinWidth(50);
    	this.tabla.getColumnModel().getColumn(0).setMaxWidth(50);
    	this.tabla.getColumnModel().getColumn(1).setMinWidth(250);
    	this.tabla.getColumnModel().getColumn(2).setMinWidth(300);
    	this.tabla.getColumnModel().getColumn(3).setMinWidth(80);
    	this.tabla.getColumnModel().getColumn(4).setMinWidth(110);
    	this.tabla.getColumnModel().getColumn(5).setMinWidth(60);
    	this.tabla.getColumnModel().getColumn(6).setMinWidth(110);
    	this.tabla.getColumnModel().getColumn(7).setMinWidth(60);
    	this.tabla.getColumnModel().getColumn(8).setMinWidth(60);
    	this.tabla.getColumnModel().getColumn(9).setMinWidth(60);
    	this.tabla.getColumnModel().getColumn(10).setMinWidth(250);
    	this.tabla.getColumnModel().getColumn(11).setMinWidth(120);
    	this.tabla.getColumnModel().getColumn(12).setMinWidth(120);
    	
		String comandof="exec cuadrantes_actividades_filtro";
		String basedatos="26",pintar="si";
		ObjTab.Obj_Refrescar(tabla,modelo, columnas, comandof, basedatos,pintar,checkbox);
    }
	
	@SuppressWarnings("rawtypes")
	public Class[] base (){
		Class[] types = new Class[columnas];
		for(int i = 0; i<columnas; i++){types[i]= java.lang.Object.class;}
		 return types;
	}
	
	public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"Folio","Actividad","Descripcion","Tipo Respuesta","Aspecto","Nivel Critico","Temporada","Exige Evidencia","Exige Observacion","Estatus","Colaborador","Fecha","Fecha Ultima Modificacion"}){
		 @SuppressWarnings("rawtypes")
			Class[] types = base();
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
			public boolean isCellEditable(int fila, int columna){return false;}
	};
	
	JTable tabla = new JTable(modelo);
	public JScrollPane scroll_tabla = new JScrollPane(tabla);
    @SuppressWarnings("rawtypes")
	private TableRowSorter trsfiltro;
	     
	JTextField txtFiltro  = new Componentes().text(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<", 500, "String");
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Cat_Filtro_Actividades(){
		this.setSize(1024,600);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("Filtro de Actividades");
		this.panel.setBorder(BorderFactory.createTitledBorder("Filtro de Actividades"));
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Filter-List-icon32.png"));
		this.trsfiltro = new TableRowSorter(modelo); 
		this.tabla.setRowSorter(trsfiltro);
		
		this.panel.add(txtFiltro).setBounds   (10 ,15 ,995 ,20 );
		this.panel.add(scroll_tabla).setBounds(10 ,35 ,995 ,530);
		
		this.cont.add(panel);
		
		this.init_tabla();
		
		this.tabla.addMouseListener(opAgregar);
		
		this.txtFiltro.addKeyListener(op_filtro_nombre);
		
	}
	
	MouseListener opAgregar = new MouseListener() {
		public void mouseReleased(MouseEvent arg0) {}
		public void mousePressed(MouseEvent arg0) {}
		public void mouseExited(MouseEvent arg0) {}
		public void mouseEntered(MouseEvent arg0) {}
		public void mouseClicked(MouseEvent arg0) {
			if(arg0.getClickCount() == 2){
    			int fila = tabla.getSelectedRow();
    			Object folio =  tabla.getValueAt(fila, 0);
    			dispose();
    			new Cat_Actividades(Integer.parseInt(folio.toString().trim())).setVisible(true);
        	}
		}
	};
	
	KeyListener op_filtro_nombre = new KeyListener(){
		public void keyReleased(KeyEvent arg0) {
			ObjTab.Obj_Filtro(tabla, txtFiltro.getText(), columnas);
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}		
	};
		
	
		public static void main(String args[]){
			try{
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				new Cat_Filtro_Actividades().setVisible(true);
			}catch(Exception e){
				System.err.println("Error en Main: "+e.getMessage());
			}
		}
}
