package Cat_Evaluaciones;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Obj_Principal.Componentes;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_tabla;

@SuppressWarnings({ "serial", "unchecked" })
public class Cat_Filtro_Nivel_Jerarquico extends JFrame{
	Container cont = getContentPane();
	JLayeredPane campo = new JLayeredPane();
	
	Obj_tabla ObjTabf =new Obj_tabla();
	int columnas = 3,checkbox=-1;
	public void init_tablaf(){
    	this.tabla.getColumnModel().getColumn(0).setMinWidth(55);
    	this.tabla.getColumnModel().getColumn(1).setMinWidth(300);
    	this.tabla.getColumnModel().getColumn(2).setMinWidth(288);
		String comandof="select tb_nivel_jerarquico.folio,tb_nivel_jerarquico.descripcion,tb_puesto.nombre " +
				             "from tb_nivel_jerarquico " +
				         "inner join tb_puesto on tb_puesto.folio=tb_nivel_jerarquico.puesto_principal";
		String basedatos="26",pintar="si";
		ObjTabf.Obj_Refrescar(tabla,model, columnas, comandof, basedatos,pintar,checkbox);
    }
	
	@SuppressWarnings("rawtypes")
	public Class[] base (){
		Class[] types = new Class[columnas];
		for(int i = 0; i<columnas; i++){types[i]= java.lang.Object.class;}
		 return types;
	}
	
	public DefaultTableModel model = new DefaultTableModel(null, new String[]{"Folio","Nombre Nivel Jerarquico","Puesto Principal"}){
		 @SuppressWarnings("rawtypes")
			Class[] types = base();
			@SuppressWarnings("rawtypes")
			public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
			public boolean isCellEditable(int fila, int columna){return false;}
	};
	    JTable tabla = new JTable(model);
		public JScrollPane scroll_tablaf = new JScrollPane(tabla);
	     @SuppressWarnings("rawtypes")
	    private TableRowSorter trsfiltro;
	     
	JTextField txtBuscar =new Componentes().text(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<", 300, "String");
	
	@SuppressWarnings("rawtypes")
	public Cat_Filtro_Nivel_Jerarquico()	{
		this.setSize(700,495);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Filter-List-icon16.png"));
		this.setTitle("Filtro Nivel Jerarquico");
        this.init_tablaf(); 		
        this.trsfiltro = new TableRowSorter(model); 
		this.tabla.setRowSorter(trsfiltro);
		
		this.campo.add(txtBuscar).setBounds    (5,5,683,20);
		this.campo.add(scroll_tablaf).setBounds(5,25,683,435);
		agregar(tabla);
		
		txtBuscar.addKeyListener(new KeyAdapter() { 
			public void keyReleased(final KeyEvent e) { 
                filtro(); 
            } 
        });
		
		cont.add(campo);
		
	}
	private void agregar(final JTable tbl) {
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
	        public void mouseClicked(MouseEvent e) {
	        	if(e.getClickCount() == 2){
	        		int fila = tabla.getSelectedRow();
	    			Object folio =  tabla.getValueAt(fila, 0).toString().trim();
	    			dispose();
	    			new Cat_Nivel_Jerarquico(folio+"").setVisible(true);
	        	}
	        }
        });
    }
	
	public void filtro() { 
		ObjTabf.Obj_Filtro(tabla, txtBuscar.getText().toUpperCase(), 3,txtBuscar);
	}  
	
	
}
