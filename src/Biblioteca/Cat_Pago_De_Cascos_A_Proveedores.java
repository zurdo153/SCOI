package Biblioteca;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import Cat_Compras.Cat_Pago_De_Cascos_Al_Proveedor;
import Obj_Principal.Componentes;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_Filtro_Dinamico_Plus;
import Obj_Principal.Obj_Refrescar;

@SuppressWarnings("serial")
public class Cat_Pago_De_Cascos_A_Proveedores extends JDialog {
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	public JTextField txtFiltro = new Componentes().text(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla <<<", 300, "String");

	@SuppressWarnings("rawtypes")
	public Class[] tipos(){
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
	
	int columnas = 3,checkbox=-1;
	public void init_tabla(){
    	this.tabla.getColumnModel().getColumn(0).setMinWidth(30);		
    	this.tabla.getColumnModel().getColumn(1).setMinWidth(300);
    	this.tabla.getColumnModel().getColumn(2).setMinWidth(410);
    	
		String comando="select cod_prv as folio,razon_social as proveedor,calle+' No. EXTERIOR:'+num_exterior+' '+colonia+' C.P:'+cod_postal+' '+pobmunedo+' TELS:'+tel1+' FAX:'+fax as Domicilio from proveedores where status_proveedor =1 order by proveedor asc";
		String basedatos="200",pintar="si";
		new Obj_Refrescar(tabla,modelo, columnas, comando, basedatos,pintar,checkbox);
    }
	
	
 public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"Folio", "Nombre de Proveedor", "Descripción"}){
	 @SuppressWarnings("rawtypes")
		Class[] types = tipos();
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Class getColumnClass(int columnIndex) {
         return types[columnIndex];
     }
		public boolean isCellEditable(int fila, int columna){
			if(columna ==checkbox)
				return true; return false;
		}
    };
    
    JTable tabla = new JTable(modelo);
	public JScrollPane scroll_tabla = new JScrollPane(tabla);
////////////////////////////////////////////////////////////////	
	
	Border blackline, etched, raisedbevel, loweredbevel, empty;
	public Cat_Pago_De_Cascos_A_Proveedores(){
		
		this.setModal(true);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Filter-List-icon16.png"));
		this.setTitle("Filtro de Proveedores");
		blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
		this.panel.setBorder(BorderFactory.createTitledBorder(blackline,"Busqueda y Seleccion del Proveedor"));
		
		this.panel.add(txtFiltro).setBounds(20,20,590,20);
		this.panel.add(scroll_tabla).setBounds(20,40,820,365);
		
		this.init_tabla();
		this.tabla.addMouseListener(opAgregar);
		this.tabla.addKeyListener(seleccionEmpleadoconteclado);
		this.txtFiltro.addKeyListener(op_filtro);
		this.cont.add(panel);
		this.setSize(870,450);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
//      pone el foco en el txtproveedor al presionar la tecla scape y limpia lo buscado
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "foco");
        getRootPane().getActionMap().put("foco", new AbstractAction(){
            @Override
            public void actionPerformed(ActionEvent e)
            {   txtFiltro.setText("");
                txtFiltro.requestFocus(); }
        });
//      asigna el foco al JTextField del nombre deseado al arrancar la ventana
        this.addWindowListener(new WindowAdapter() {
                public void windowOpened( WindowEvent e ){  	txtFiltro.requestFocus();   }
        });
        
	}
	
	MouseListener opAgregar = new MouseListener() {
		public void mouseReleased(MouseEvent arg0) {}
		public void mousePressed(MouseEvent arg0) {}
		public void mouseExited(MouseEvent arg0) {}
		public void mouseEntered(MouseEvent arg0) {}
		public void mouseClicked(MouseEvent arg0) {
			if(arg0.getClickCount() == 2){
//    			int fila = tabla.getSelectedRow();
//    			Object folio =  tabla.getValueAt(fila, 0);
//    			Object Proveedor =  tabla.getValueAt(fila, 1);
    			dispose();
        	}
		}
	};
	
	KeyListener op_filtro = new KeyListener(){
		public void keyReleased(KeyEvent arg0) {
			int[] columnas ={0,1,2};
			new Obj_Filtro_Dinamico_Plus(tabla, txtFiltro.getText().toUpperCase(), columnas);
		}
		public void keyTyped(KeyEvent arg0)   {}
		public void keyPressed(KeyEvent arg0) {}		
	};
		
		KeyListener seleccionEmpleadoconteclado = new KeyListener() {
			@SuppressWarnings("static-access")
			@Override
			public void keyTyped(KeyEvent e) {
				char caracter = e.getKeyChar();
					if(caracter==e.VK_ENTER){
//				int fila=tabla.getSelectedRow()-1;
//				String folio = tabla.getValueAt(fila,0).toString().trim();
//				String proveedor = tabla.getValueAt(fila,1).toString().trim();
				dispose();
				}
			}
			@Override
			public void keyPressed(KeyEvent e){}
			@Override
			public void keyReleased(KeyEvent e){}
									
		};
		
		
		public static void main(String args[]){
			try{
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				new Cat_Pago_De_Cascos_A_Proveedores().setVisible(true);
			}catch(Exception e){	}
		}
	
}
