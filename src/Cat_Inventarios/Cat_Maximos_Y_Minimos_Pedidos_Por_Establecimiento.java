package Cat_Inventarios;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import Conexiones_SQL.BuscarTablasModel;
import Obj_Principal.Obj_tabla;

@SuppressWarnings("serial")
public class Cat_Maximos_Y_Minimos_Pedidos_Por_Establecimiento extends JFrame {
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	@SuppressWarnings("rawtypes")
	public Class[] base (){
		Class[] types = new Class[columnasb];
		for(int i = 0; i<columnasb; i++){types[i]= java.lang.Object.class;}
		 return types;
	}
	
	public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"Cod_Prod","Descripcion","Minimo","Maximo","Exist. Estab","Sugerido","Exist. Cedis","Confirmacion","Estatus Prod.","Area Tipo Distribucion"}){
		 @SuppressWarnings("rawtypes")
			Class[] types = base();
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
			public boolean isCellEditable(int fila, int columna){return false;}
	};

	JTable tabla = new JTable(modelo);
		    
			public JScrollPane scroll_tabla = new JScrollPane(tabla);
			
			Obj_tabla ObjTab =new Obj_tabla();
			int columnasb = 10,checkbox=-1;
			public void init_tablafp(){
		    	this.tabla.getColumnModel().getColumn( 0).setMinWidth(55);
		    	this.tabla.getColumnModel().getColumn( 1).setMinWidth(355);
		    	this.tabla.getColumnModel().getColumn( 2).setMinWidth(150);
		    	this.tabla.getColumnModel().getColumn( 3).setMinWidth(150);
		    	this.tabla.getColumnModel().getColumn( 4).setMinWidth(200);
		    	this.tabla.getColumnModel().getColumn( 5).setMinWidth(200);
		    	this.tabla.getColumnModel().getColumn( 6).setMinWidth(120);
		    	this.tabla.getColumnModel().getColumn( 7).setMinWidth(120);
		    	this.tabla.getColumnModel().getColumn( 8).setMinWidth(120);
		    	this.tabla.getColumnModel().getColumn( 9).setMinWidth(120);
		    	String comandof=" exec consulta_maximos_y_minimos 'SUPER V'";
				String basedatos="26",pintar="si";
				ObjTab.Obj_Refrescar(tabla,modelo, columnasb, comandof, basedatos,pintar,checkbox);
		    }
			
	public Cat_Maximos_Y_Minimos_Pedidos_Por_Establecimiento() {
		// TODO Auto-generated constructor stub
		
		setSize(1024,600);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Maximos y Minimos, Pedidos Por Establecimiento");
		setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/articulo-icono-9036-48.png"));
		panel.setBorder(BorderFactory.createTitledBorder("Pedidos Por Establecimiento"));
		
		init_tablafp();
		int x=20, y=25, width=980,height=450;
		panel.add(scroll_tabla).setBounds                  (x    ,y       ,width   ,height   );
//		panel.add(cmbConcepto).setBounds                         (x    ,y+=30   ,width   ,height   );
		
		cont.add(panel);
	}
  int fila=0;
	KeyListener op_validanumero_en_celda = new KeyListener() {
		public void keyTyped(KeyEvent e) {}
		public void keyReleased(KeyEvent e) {
			fila=tabla.getSelectedRow();
//			if(ObjTab.validacelda(tabla,"decimal", fila, columna)){
//				  RecorridoFoco(fila,"x"); 
//				  calculo_diferencia(fila);
//			}
		}
		public void keyPressed(KeyEvent e) {}
	};
	
	public static void main(String[] args) {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Maximos_Y_Minimos_Pedidos_Por_Establecimiento().setVisible(true);
		}catch(Exception e){	}

	}

}
