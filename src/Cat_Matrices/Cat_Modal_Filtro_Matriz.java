package Cat_Matrices;

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

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

import Cat_Reportes.Cat_Reportes_De_Apartados;
import Cat_Reportes.Cat_Reportes_De_Vouchers;
import Conexiones_SQL.BuscarTablasModel;
import Conexiones_SQL.Connexion;
import Obj_Matrices.Obj_Matriz;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_Filtro_Dinamico_Plus;
import Obj_Principal.Obj_Refrescar;
import Obj_Renders.tablaRenderer;

@SuppressWarnings({ "serial", "unused" })
public class Cat_Modal_Filtro_Matriz extends JDialog {
	
	Obj_Matriz obm = new Obj_Matriz();
	
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();

	 int checkbox=-1;
	 
	 @SuppressWarnings("rawtypes")
	 public Class[] tipos(int columnas){
	 	Class[] tip = new Class[columnas];
	 	
	 	for(int i =0; i<columnas; i++){
	 		if(i==checkbox){
	 			tip[i]=java.lang.Boolean.class;
	 		}else{
	 			tip[i]=java.lang.Object.class;
	 		}
	 		
	 	}
	 	return tip;
	 }

	public String getValor_catalogo() {
		return valor_catalogo;
	}

	public void setValor_catalogo(String valor_catalogo) {
		this.valor_catalogo = valor_catalogo;
	}

	JTextField txtFolio = new Componentes().text(new JCTextField(), "ingrese lo que desea buscar dentro de la tabla Matrices", 300, "String");

	   String[]Colum={"Folio","Establecimiento","Descripcion"};
	
		String valor_catalogo;
	
@SuppressWarnings({ "unchecked", "rawtypes" })
public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"Folio", "Establecimiento", "Observacion"}){
		Class[] types = tipos(this.getColumnCount());
		
		public Class getColumnClass(int columnIndex) {
        return types[columnIndex];
    }
		
    public boolean isCellEditable(int fila, int columna){
//   	 if(columna ==checkbox)
				return true;
		}
   };
   
   JTable tabla = new JTable(modelo);
	public JScrollPane scroll_tabla = new JScrollPane(tabla);
public 	Cat_Modal_Filtro_Matriz(String bandera_origen_consulta_filtro){
	
	
	setModal(true);
	valor_catalogo=bandera_origen_consulta_filtro;
	
	setResizable(false);
	setLocation(500, 150);
	setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Filter-List-icon32.png"));
	setTitle("Matrices Realizadas");
	
	panel.setBorder(BorderFactory.createTitledBorder("Busqueda de Matrices "));
	
	this.setSize(500,400);
	int x=15, y=45, ancho=100;

	panel.add(txtFolio).setBounds(x, y, (ancho*4)+50, 20);
	panel.add(scroll_tabla).setBounds(x,y+30,450,280);
	cont.add(panel);
	
	init_tabla();
	agregar(tabla);
	 
	txtFolio.addKeyListener(op_filtro);
}

private void agregar(final JTable tbl) {
    tbl.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(MouseEvent e) {if(tabla.isEditing()){
			tabla.getCellEditor().stopCellEditing();
		}
		String cadena = ""+modelo.getValueAt(tabla.getSelectedRow(), 0).toString().trim();
		
		valor_catalogo=cadena;
		dispose();
		
	}
    });
}

public void init_tabla(){
	this.tabla.getColumnModel().getColumn(0).setMinWidth(50);		
	this.tabla.getColumnModel().getColumn(1).setMinWidth(150);
	this.tabla.getColumnModel().getColumn(2).setMinWidth(200);
	
	int columnas = modelo.getColumnCount();
	
	String comando="select folio_matriz,matriz,isnull(descripcion,'') as descripcion from tb_matrices";
	String basedatos="26",pintar="si";
	
	 new Obj_Refrescar(tabla,modelo, columnas, comando, basedatos,pintar,checkbox);
}

KeyListener op_filtro = new KeyListener(){ 
	public void keyReleased(KeyEvent arg0) {
		int[] columnas ={0,1,2};
		new Obj_Filtro_Dinamico_Plus(tabla, txtFolio.getText().toString().trim().toUpperCase(), columnas);
	}
	public void keyTyped(KeyEvent arg0)   {}
	public void keyPressed(KeyEvent arg0) {}		
};

public static void main(String []yo)
{
	try{
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		new Cat_Modal_Filtro_Matriz("Consulta_de_Matrices").setVisible(true);
	}catch(Exception e){	}
}

	

}
