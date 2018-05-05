package Biblioteca;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;

import Obj_Principal.Obj_tabla;

@SuppressWarnings("serial")
public class Cat_Valida_Celda_Activa_Recorrido_De_Foco extends JFrame{

	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	Obj_tabla ObjTab =new Obj_tabla();
	int columnasTabla = 4,checkbox=-1;
	int columna=0,fila=0;
	@SuppressWarnings("rawtypes")
	public Class[] base (){
		Class[] types = new Class[columnasTabla];
		for(int i = 0; i<columnasTabla; i++){types[i]= java.lang.Object.class;}
		 return types;
	}
	
	public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"Folio","tests","Puesto","a"}){
		 @SuppressWarnings("rawtypes")
			Class[] types = base();
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
			public boolean isCellEditable(int fila, int columna){
				switch(columna){
				case 0: return true; 
				case 1: return true; 
				case 2: return true;
				case 3: return true;
				}
				return false;
				}
	};
	
	JTable tabla = new JTable(modelo);
	public JScrollPane scroll_tabla = new JScrollPane(tabla);
	
	public Cat_Valida_Celda_Activa_Recorrido_De_Foco() {
		this.setSize(700,350);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		
		 panel.add(scroll_tabla).setBounds(10, 10, 670, 300);
		 
		 init_tabla();
		 agregar(tabla);
		 tabla.addKeyListener(opKeyTable);
		 
		 cont.add(panel);
	}

	public void init_tabla(){
    	this.tabla.getColumnModel().getColumn(0).setMinWidth(55);
    	this.tabla.getColumnModel().getColumn(1).setMinWidth(375);
    	String comandof="exec cuadrantes_puestos_para_nuevos_cuadrantes ";
		String basedatos="26",pintar="si";
		ObjTab.Obj_Refrescar(tabla,modelo, columnasTabla, comandof, basedatos,pintar,checkbox);
    }
	
	private void agregar(final JTable tbl) {
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
	        public void mouseClicked(MouseEvent e) {
	        	
	        	fila    = tbl.getSelectedRow();
	        	columna = tbl.getSelectedColumn();
	        	
	        		if(tabla.isCellEditable(fila, columna)){
	        			for(int i=0; i<columnasEditables.length; i++){
	        				if(columna==columnasEditables[i]){
		        				indiceSeleccionado=i;
		        			}
	        			}
	        		}else{
	        			indiceSeleccionado=0;
	        		}
	        		
	        	if(e.getClickCount() == 1){
	        		RecorridoFoco("click");
	        	}
	        }
        });
    }
	
	KeyListener opKeyTable = new KeyListener() {
		public void keyTyped(KeyEvent e) {		}
		@SuppressWarnings("static-access")
		public void keyReleased(KeyEvent e) {
			
			if(e.getKeyCode()==e.VK_ENTER){				
				RecorridoFoco("darecha");
			}
			if(e.getKeyCode()==e.VK_DOWN){
				RecorridoFoco("bajar");
			}
			if(e.getKeyCode()==e.VK_UP){
				RecorridoFoco("subir");
			}
//			if(e.getKeyCode()==e.VK_LEFT){
//				RecorridoFoco("izquierda");
//			}
//			if(e.getKeyCode()==e.VK_RIGHT){
//				RecorridoFoco("derecha");
//			}
//			if(e.getKeyCode()==e.VK_END){
//				RecorridoFoco("fin");
//			}
//			if(e.getKeyCode()==36){
//				
//				RecorridoFoco("inicio");
//			}
		}
		public void keyPressed(KeyEvent e) {	}
	};
	
	int indiceSeleccionado=0;
	int[] columnasEditables = columnasEdit();
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int[] columnasEdit(){
		Vector col = new Vector();
		for(int i=0; i<tabla.getColumnCount(); i++){
			if(tabla.isCellEditable(0, i)){
				col.addElement(i);
			}
		}
		
		int[] columnasE = new int[col.size()];
		
		for(int i=0; i<col.size(); i++){
			columnasE[i]=(int) col.get(i);
		}
		
		return columnasE;
	}
	
	public void CeldaSiguiente(){
		if(columna==columnasEditables[columnasEditables.length-1]){
			indiceSeleccionado=0;
			columna=columnasEditables[indiceSeleccionado];
			fila = fila<tabla.getRowCount()-1 ? fila+1: fila ;
		}else{
			indiceSeleccionado++;
			columna=columnasEditables[indiceSeleccionado];
		}
	}
	
	@SuppressWarnings("deprecation")
	public void RecorridoFoco(String mover){
		int cantidadDeFilas = tabla.getRowCount();
		
		System.out.println(indiceSeleccionado+"<---- indice");
		
		switch(mover){
			case "bajar": fila= (fila==cantidadDeFilas-1)?cantidadDeFilas-1:fila+1; break;
			case "subir": fila= (fila==0)?0:fila-1; break;
			case "izquierda": columna--; break;
//			case "darecha": columna++; break;
			case "darecha": CeldaSiguiente(); break;
			case "inicio": fila=0; break;
			case "fin": fila=tabla.getRowCount()-1; break;
		}
		
		String sacarFocoDeTabla = "no";
		if(fila == cantidadDeFilas-1){
			sacarFocoDeTabla="si";
		}
		else{
			sacarFocoDeTabla = "no";
		}
		tabla.getSelectionModel().setSelectionInterval(fila, fila);
		tabla.editCellAt(fila, columna);
		  Component accion=tabla.getEditorComponent();
		final JTextComponent jtc = (JTextComponent)accion;
		  jtc.requestFocus();
		  jtc.selectAll();	
		
		if(sacarFocoDeTabla.equals("si")){
			tabla.lostFocus(null, null);
//			.requestFocus();
			tabla.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
			tabla.getSelectionModel().clearSelection();
		}
	};
	
	public static void main(String [] arg){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Valida_Celda_Activa_Recorrido_De_Foco().setVisible(true);
		}catch(Exception e){	}
	}
}
