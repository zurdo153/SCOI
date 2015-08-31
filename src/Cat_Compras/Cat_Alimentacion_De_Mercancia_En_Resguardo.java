package Cat_Compras;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import Conexiones_SQL.BuscarTablasModel;
import Conexiones_SQL.GuardarTablasModel;
import Obj_Principal.Componentes;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_Filtro_Dinamico;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Alimentacion_De_Mercancia_En_Resguardo extends JFrame{
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JDateChooser fhIn = new JDateChooser();
	
	JButton btnBuscar = new JButton("Buscar",new ImageIcon("imagen/buscar.png"));
	
	JTextField txtRecepcion = new Componentes().text(new JTextField(), "Folio De Recepcion", 20, "String");
	JTextField txtCodProv 	= new Componentes().text(new JTextField(), "Codigo De Proveedor", 15, "Int");
	JTextField txtProveedor = new Componentes().text(new JCTextField(), "Teclee El Nombre De Un Proveedor Para Su Busqueda En La Lista", 110, "String");
	
	DefaultTableModel modelo = new DefaultTableModel(null,new String[]{"Recepcion","Cod Prv","Proveedor"}){
		
		@SuppressWarnings("rawtypes")
		Class[] types = new Class[]{
				java.lang.Object.class,
				java.lang.Object.class,
				java.lang.Object.class
		};
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Class getColumnClass(int columnIndex) {
            return types[columnIndex];
        }
        public boolean isCellEditable(int fila, int columna){
       	 
       	 switch(columna){
       	 	case 0 : return false; 
       	 	case 1 : return false; 
       	 	case 2 : return false; 
       	 	
       	 }
			return false;
		}
	};
	
	JTable tabla = new JTable(modelo);
	JScrollPane scroll = new JScrollPane(tabla);

	public Cat_Alimentacion_De_Mercancia_En_Resguardo(){
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/cajas-de-cajas-de-embalaje-de-envio-de-un-archivo-tar-icono-4009-64.png"));
		this.setTitle("Filtro De Recepciones Para Resguardo");
		panel.setBorder(BorderFactory.createTitledBorder("Seleccione o Teclee Una Fecha  y De Clic En Buscar"));		
		int x=15,y=20,ancho=80;
		
		panel.add(new JLabel("Fecha de Recepcion:")).setBounds(x,y,140,20);
		panel.add(fhIn).setBounds(x+110,y,100,20);
		panel.add(btnBuscar).setBounds(x+210,y,100,20);
		
		panel.add(txtRecepcion).setBounds(x,y+=25,70,20);
		panel.add(txtCodProv  ).setBounds(x+70,y,70,20);
		panel.add(txtProveedor).setBounds(x+140,y,ancho*6-20,20);
		panel.add(scroll).setBounds(x,y+=20,ancho*7+40,600);
		
		cont.add(panel);
		
		llamar_render();
		
		agregar(tabla);
		
		btnBuscar.addActionListener(opBuscar);
		
		txtRecepcion.addKeyListener(opFiltroDinamicoRecepcion);
		txtCodProv.addKeyListener(opFiltroDinamicoFolioPrv);
		txtProveedor.addKeyListener(opFiltroDinamicoProveedor);
		
		this.setSize(645, 720);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
		public void llamar_render(){
		tabla.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","derecha","Arial","negrita",12));
		tabla.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","derecha","Arial","negrita",12));
		tabla.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",12));
		
				this.tabla.getTableHeader().setReorderingAllowed(false) ;

		this.tabla.getColumnModel().getColumn(0).setMaxWidth(70);
		this.tabla.getColumnModel().getColumn(0).setMinWidth(70);
		this.tabla.getColumnModel().getColumn(1).setMaxWidth(70);
		this.tabla.getColumnModel().getColumn(1).setMinWidth(70);
		this.tabla.getColumnModel().getColumn(2).setMaxWidth(457);
		this.tabla.getColumnModel().getColumn(2).setMinWidth(457);
	}
	
	public Object[][] get_tabla_model(String fecha){
		return new BuscarTablasModel().filtro_recepcion_de_mercancia_y_proovedores(fecha);
	}
	ActionListener opBuscar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			if ((fhIn.getDate()+"").equals("null")) {
				JOptionPane.showMessageDialog(null, "Es Necesario Seleccionar Una Fecha Para Buscar Los Folios De Recepcion","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
				return;
			}else{
			
				while(tabla.getRowCount()>0){modelo.removeRow(0);}
				
				String fecha = new SimpleDateFormat("dd/MM/yyyy").format(fhIn.getDate());
				
				Object[][] filtro = get_tabla_model(fecha);
				for(Object[] reg:filtro){
					modelo.addRow(reg);
				}
			}
		}
	};
	
			private void agregar(final JTable tbl) {
	        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
		        public void mouseClicked(MouseEvent e) {
		        	if(e.getClickCount() == 2){
		        		
		    			int fila = tabla.getSelectedRow();
		    			String recepcion =  tabla.getValueAt(fila, 0).toString();
		    			String codPrv =  tabla.getValueAt(fila, 1).toString();
		    			String proveedor =  tabla.getValueAt(fila, 2).toString();
		    			
		    			new Cat_Recepcion_De_Resguardo(recepcion,codPrv,proveedor).setVisible(true);
		    			
//		    			dispose();
		        	}
		        }
	        });
	    }
	
	KeyListener opFiltroDinamicoRecepcion = new KeyListener(){
		public void keyReleased(KeyEvent arg0) {
			
				txtCodProv.setText("");
				txtProveedor.setText("");
				new Obj_Filtro_Dinamico(tabla,"Cod Prv", "","","", "", "", "", "");
				new Obj_Filtro_Dinamico(tabla,"Proveedor", "","","", "", "", "", "");
				new Obj_Filtro_Dinamico(tabla,"Recepcion", txtRecepcion.getText().toUpperCase().trim(),"","", "", "", "", "");
			
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}		
	};
	
		KeyListener opFiltroDinamicoFolioPrv = new KeyListener(){
		public void keyReleased(KeyEvent arg0) {
			
				txtRecepcion.setText("");
				txtProveedor.setText("");
				new Obj_Filtro_Dinamico(tabla,"Proveedor", "","","", "", "", "", "");
				new Obj_Filtro_Dinamico(tabla,"Recepcion","","","", "", "", "", "");
				new Obj_Filtro_Dinamico(tabla,"Cod Prv",  txtCodProv.getText().toUpperCase().trim(),"","", "", "", "", "");
				
			
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}		
	};
	
		KeyListener opFiltroDinamicoProveedor = new KeyListener(){
		public void keyReleased(KeyEvent arg0) {
			
				txtRecepcion.setText("");
				txtCodProv.setText("");
				new Obj_Filtro_Dinamico(tabla,"Recepcion","","","", "", "", "", "");
				new Obj_Filtro_Dinamico(tabla,"Cod Prv",  "","","", "", "", "", "");
				new Obj_Filtro_Dinamico(tabla,"Proveedor", txtProveedor.getText().toUpperCase().trim(),"","", "", "", "", "");
			
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}		
	};
	
	public static void main(String[] args) {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Alimentacion_De_Mercancia_En_Resguardo().setVisible(true);
		}catch(Exception e){	}
	}
	
	
	
	
	
	
	public class Cat_Recepcion_De_Resguardo extends JDialog{
	
	Container cont2 = getContentPane();
	JLayeredPane panel2 = new JLayeredPane();
	
	JButton btnGuardar = new JButton("Guardar",new ImageIcon("imagen/Guardar.png"));
	
	JTextField txtCodPrv = new Componentes().text(new JTextField(), "Codigo De Proveedor", 20, "String");
	JTextField txtProveedor 	= new Componentes().text(new JTextField(), "Proveedor", 180, "String");
	
	JTextField txtRecep = new Componentes().text(new JTextField(), "Recepcion", 20, "String");
	
	JTextField txtCodProducto = new Componentes().text(new JTextField(), "Codigo De Producto", 15, "String");
	JTextField txtDescripcion 	= new Componentes().text(new JCTextField(), "Teecle El Nombre Del Producto Para Su Busqueda En La Lista", 100, "String");
	
		DefaultTableModel modeloRecep = new DefaultTableModel(null,new String[]{"Cod.Prod","Descripcion","Fecha","Cantidad De Factura","Cantidad A Resguardo"}){
		
		@SuppressWarnings("rawtypes")
		Class[] types = new Class[]{
				java.lang.Object.class,
				java.lang.Object.class,
				java.lang.Object.class,
				java.lang.Object.class,
				java.lang.Object.class
		};
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Class getColumnClass(int columnIndex) {
            return types[columnIndex];
        }
        public boolean isCellEditable(int fila, int columna){
       	 
       	 switch(columna){
       	 	case 0 : return false; 
       	 	case 1 : return false; 
       	 	case 2 : return false; 
       	 	case 3 : return false; 
       	 	case 4 : return true; 
       	 	
       	 }
			return false;
		}
	};
	
	JTable tablaRecep = new JTable(modeloRecep);
	JScrollPane scrollRecep = new JScrollPane(tablaRecep);

	public Cat_Recepcion_De_Resguardo(String recepcion,String codProv,String Proveed){
		
		this.setModal(true);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/cajas-de-cajas-de-embalaje-de-envio-de-un-archivo-tar-icono-4009-64.png"));
		this.setTitle("Recepcion Con Resguardo");
		panel2.setBorder(BorderFactory.createTitledBorder("Ingrese Las Cantidades A Resguardo De Los Productos"));		
		int x=15,y=15,ancho=80;
		
		panel2.add(new JLabel("Proveedor:")).setBounds(x,y,70,20);
		panel2.add(txtCodProv  ).setBounds(x+70,y,70,20);
		panel2.add(txtProveedor).setBounds(x+140,y,ancho*5,20);
		
		panel2.add(new JLabel("Recepcion:")).setBounds(x,y+=25,70,20);
		panel2.add(txtRecep  ).setBounds(x+70,y,70,20);
		
		panel2.add(txtCodProducto  ).setBounds(x,y+=25,70,20);
		panel2.add(txtDescripcion).setBounds(x+70,y,ancho*6,20);
		panel2.add(btnGuardar).setBounds(x+870,y,100,20);
		
		panel2.add(scrollRecep).setBounds(x,y+=20,ancho*12+10,600);
		
		cont2.add(panel2);
		
		llamar_render_Recep();
		
		txtCodProducto.addKeyListener(opFiltroDinamicoProducto);
		txtDescripcion.addKeyListener(opFiltroDinamicoDescripcion);
		
		txtRecep.setText(recepcion);
		txtCodProv.setText(codProv);
		txtProveedor.setText(Proveed);
		
		llenarRecepcion(recepcion);
		
		txtRecep.setEditable(false);
		txtCodProv.setEditable(false);
		txtProveedor.setEditable(false);
		
		btnGuardar.addActionListener(opGuardar);
		
		this.setSize(1024, 720);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	
	public Object[][] get_tabla_model_Recep(String recepcion){
		return new BuscarTablasModel().lista_recepcion_de_mercancia(recepcion);
	}
	public void llenarRecepcion(String recep){
		
		while(tablaRecep.getRowCount()>0){
			modeloRecep.removeRow(0);
		}
		
		Object[][] recepciones = get_tabla_model_Recep(recep);
		for(Object[] rc : recepciones){
			modeloRecep.addRow(rc);
		}
	}
	
	private String valida_tabla(){
		String error = "";
		for(int i=0; i<tablaRecep.getRowCount(); i++){
//			try{
				if(!isNumeric(tablaRecep.getValueAt(i,4).toString())){
					error += "La Cant. A Recibir En El Codigo De Producto ["+modeloRecep.getValueAt(i,0).toString()+"] Esta Mal En Su Formato\n";
					tablaRecep.setValueAt("",i, 4);
				}
//			} catch(Exception e){
//				JOptionPane.showMessageDialog(null, "La tabla tiene una celda con texto en lugar de un valor numérico: \n"+e,"Error",JOptionPane.ERROR_MESSAGE);
//			}
		}
		return error;
	}
	
	 private boolean isNumeric(String cadena){
	    	try {
	    		if(cadena.equals("")){
	        		return true;
	    		}else{
	    			Float.parseFloat(cadena);
	        		return true;
	    		}
	    	} catch (NumberFormatException nfe){
	    		return false;
	    	}
	    }
	 
	ActionListener opGuardar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			if(tablaRecep.isEditing()){
				tablaRecep.getCellEditor().stopCellEditing();
			}
			
			if(!valida_tabla().equals("")){
				JOptionPane.showMessageDialog(null, "Algunas Cantidades A Recibir Estan Mal En Su Formato","Aviso",JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}else{
				String[][] matriz = new String[tablaRecep.getRowCount()][6];
				for(int i=0; i<tablaRecep.getRowCount(); i++){
					
					matriz[i][0]=txtCodProv.getText().trim();
					matriz[i][1]=tablaRecep.getValueAt(i, 2).toString().trim();
					matriz[i][2]=txtRecep.getText().trim();
					matriz[i][3]=tablaRecep.getValueAt(i, 0).toString().trim();
					matriz[i][4]=tablaRecep.getValueAt(i, 3).toString().trim();
					matriz[i][5]=tablaRecep.getValueAt(i, 4).toString().trim();
				if(Double.valueOf(tablaRecep.getValueAt(i, 4).toString().trim())>Double.valueOf(tablaRecep.getValueAt(i, 3).toString().trim())){
					JOptionPane.showMessageDialog(null, "La Cantidad Del Pruducto:"+tablaRecep.getValueAt(i, 1).toString().trim()+" \nEs Mayor A La Que Trae En La Cantidad De Factura","Aviso",JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
					return;
				}
					
				}
				
				
				if(new GuardarTablasModel().Guardar_captura_inicial_de_resguardo_de_mercancia(matriz)){
					JOptionPane.showMessageDialog(null, "La Mercancia Pendiente De La Recepción Se Guardó Exitosamente","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen//aplicara-el-dialogo-icono-6256-32.png"));
					dispose();
					return;
				}else{
					JOptionPane.showMessageDialog(null, "Ocurrió Un Error Al Intentar Guardar La Recepción","Error",JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen//usuario-icono-eliminar5252-64.png"));
					return;
				}
			}
		}
	};
	
	
	
	
		public void llamar_render_Recep(){
		tablaRecep.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","derecha","Arial","negrita",12));
		tablaRecep.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",12));
		
		tablaRecep.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","derecha","Arial","negrita",12));
		tablaRecep.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","derecha","Arial","negrita",12));
		tablaRecep.getColumnModel().getColumn(4).setCellRenderer(new tablaRenderer("texto","derecha","Arial","negrita",12));
		
		this.tablaRecep.getTableHeader().setReorderingAllowed(false) ;

		this.tablaRecep.getColumnModel().getColumn(0).setMaxWidth(70);
		this.tablaRecep.getColumnModel().getColumn(0).setMinWidth(70);
		this.tablaRecep.getColumnModel().getColumn(1).setMaxWidth(480);
		this.tablaRecep.getColumnModel().getColumn(1).setMinWidth(480);
		
		this.tablaRecep.getColumnModel().getColumn(2).setMaxWidth(140);
		this.tablaRecep.getColumnModel().getColumn(2).setMinWidth(140);
		this.tablaRecep.getColumnModel().getColumn(3).setMaxWidth(140);
		this.tablaRecep.getColumnModel().getColumn(3).setMinWidth(140);
		this.tablaRecep.getColumnModel().getColumn(4).setMaxWidth(140);
		this.tablaRecep.getColumnModel().getColumn(4).setMinWidth(140);
	}
	
	KeyListener opFiltroDinamicoProducto= new KeyListener(){
		public void keyReleased(KeyEvent arg0) {
			
				txtDescripcion.setText("");
				new Obj_Filtro_Dinamico(tablaRecep,"Descripcion", "","","", "", "", "", "");
				new Obj_Filtro_Dinamico(tablaRecep,"Cod.Prod", txtCodProducto.getText().toUpperCase().trim(),"","", "", "", "", "");
			
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}		
	};
		KeyListener opFiltroDinamicoDescripcion  = new KeyListener(){
		public void keyReleased(KeyEvent arg0) {
			
				txtCodProducto.setText("");
				new Obj_Filtro_Dinamico(tablaRecep,"Cod.Prod", "","","", "", "", "", "");
				new Obj_Filtro_Dinamico(tablaRecep,"Descripcion",  txtDescripcion.getText().toUpperCase().trim(),"","", "", "", "", "");
				
			
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}		
	};
	
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
