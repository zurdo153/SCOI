package Cat_Compras;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

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

import Conexiones_SQL.BuscarTablasModel;
import Conexiones_SQL.Generacion_Reportes;
import Conexiones_SQL.GuardarTablasModel;
import Obj_Principal.Componentes;
import Obj_Principal.Obj_Filtro_Dinamico;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Recibo_De_Mercancia_En_Resguardo extends JFrame{
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JButton btnReporte = new JButton("Reporte",new ImageIcon("imagen/Lista.png"));
	JButton btnLimpiar = new JButton("Limpiar",new ImageIcon("imagen/clear-brush-broom-sweeping-change-icone-7230-16.png"));
	
	JTextField txtRecepcionS = new Componentes().text(new JTextField(), "Folio De Recepcion Seleccionado", 20, "String");
	JTextField txtCodProvS 	= new Componentes().text(new JTextField(), "Codigo De Proveedor Selecionado", 15, "Int");
	JTextField txtProveedorS = new Componentes().text(new JTextField(), "Proveedor Selecionado", 110, "String");
	
	JTextField txtRecepcion = new Componentes().text(new JTextField(), "Folio De Recepcion", 20, "String");
	JTextField txtCodProv 	= new Componentes().text(new JTextField(), "Codigo De Proveedor", 15, "Int");
	JTextField txtProveedor = new Componentes().text(new JTextField(), "Proveedor", 110, "String");
	
	public Object[][] get_tabla_model(){
		return new BuscarTablasModel().filtro_de_proovedores_con_recepciones();
	}
	DefaultTableModel modelo = new DefaultTableModel(get_tabla_model(),new String[]{"Cod Prv","Proveedor","Recepcion","Fecha"}){
		
		@SuppressWarnings("rawtypes")
		Class[] types = new Class[]{
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
       	 }
			return false;
		}
	};
	
	JTable tabla = new JTable(modelo);
	JScrollPane scroll = new JScrollPane(tabla);

	public Cat_Recibo_De_Mercancia_En_Resguardo(){
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/descargar-caja-de-madera-icono-5941-64.png"));
		this.setTitle("Recibo De Mercancia En Resguardo");
		panel.setBorder(BorderFactory.createTitledBorder("Seleccione La Recepcion Con Dos Click Continuos Y Se Abrira La Ventana Para Alimentar La Mercancia A Recibir"));		
		int x=15,y=15,ancho=80;
		
		panel.add(txtCodProv).setBounds(x,y,50,20);
		panel.add(txtProveedor  ).setBounds(x+50,y,ancho*4+60,20);
		panel.add(txtRecepcion).setBounds(x+(ancho*5)+30,y,70,20);
		
		panel.add(scroll).setBounds(x,y+=20,ancho*8,600);
		
		x=15;
		panel.add(new JLabel ("Recepcion:")).setBounds(x,y+=615,55,20);
		panel.add(txtRecepcionS).setBounds(x+=52,y,70,20);
		panel.add(new JLabel ("Proveedor:")).setBounds(x+=80,y,55,20);
		panel.add(txtCodProvS).setBounds(x+=55,y,60,20);
		panel.add(txtProveedorS).setBounds(x+=60,y,ancho*4+73,20);
		x=15;
		panel.add(btnLimpiar).setBounds(x,y+=25,100,20);
		panel.add(new JLabel ("Si Le Das Click  A Limpiar y Luego A Reporte Te Mostrara Todas Las Recepciones")).setBounds(x+120,y,420,20);
		panel.add(new JLabel ("Si Seleccionas Con Un Click Una Recepcion De La Tabla Solo Esa Te Mostrara")).setBounds(x+130,y+15,400,20);
		panel.add(btnReporte).setBounds(x+540,y,100,20);
		
		cont.add(panel);
		llamar_render();
		agregar(tabla);
		
		btnLimpiar.addActionListener(opLimpiar);
		btnReporte.addActionListener(opGenerar);
		
		txtRecepcion.addKeyListener(opFiltroDinamicoRecepcion);
		txtCodProv.addKeyListener(opFiltroDinamicoFolioPrv);
		txtProveedor.addKeyListener(opFiltroDinamicoProveedor);
		
		txtRecepcionS.setEditable(false);
		txtCodProvS.setEditable(false);
		txtProveedorS.setEditable(false);
		
		this.setSize(685, 760);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	public void llamar_render(){
		tabla.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","derecha","Arial","negrita",12));
		tabla.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",12));
		tabla.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",12));
		tabla.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",12));
		
		this.tabla.getTableHeader().setReorderingAllowed(false) ;

		this.tabla.getColumnModel().getColumn(0).setMaxWidth(50);
		this.tabla.getColumnModel().getColumn(0).setMinWidth(50);
		this.tabla.getColumnModel().getColumn(1).setMaxWidth(380);
		this.tabla.getColumnModel().getColumn(1).setMinWidth(380);
		this.tabla.getColumnModel().getColumn(2).setMaxWidth(70);
		this.tabla.getColumnModel().getColumn(2).setMinWidth(70);
		this.tabla.getColumnModel().getColumn(3).setMaxWidth(140);
		this.tabla.getColumnModel().getColumn(3).setMinWidth(140);
	}
	
	public boolean generarConsulta(){
		String folio_Recepcion =txtRecepcionS.getText().toString().toUpperCase().trim();
		if(!folio_Recepcion.equals("")){ folio_Recepcion="  where tb_productos_en_resguardo_por_recepcion.folio_recepcion='"+folio_Recepcion+"'";
		}
		
		return new BuscarTablasModel().reporte_de_recepcion_de_mercancia_en_resguardo(folio_Recepcion);
	}
	
	ActionListener opGenerar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(generarConsulta()){
				String basedatos="2.26";
				String vista_previa_reporte="no";
				int vista_previa_de_ventana=0;
					String reporte = "Obj_Reporte_De_Movimiento_De_Mercancia_En_Resguardo.jrxml";
					String comando = "exec sp_Reporte_De_Movimientos_De_Recepciones_De_Mercancia_En_Resguardo";
			   	    new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
			}else{
				JOptionPane.showMessageDialog(null, "No Se Ha Generado La Consulta Correctamente, Avise Al Administrador","Error", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-icono-eliminar5252-64.png"));
		          return;
			}
	 	}
   };
   
	ActionListener opLimpiar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			txtRecepcionS.setText("");
			txtCodProvS.setText("");
			txtProveedorS.setText("");
	 	}
   };
   
		private void agregar(final JTable tbl) {
	        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
		        public void mouseClicked(MouseEvent e) {
		        	if(e.getClickCount() == 1){
		    			int fila			= tabla.getSelectedRow();
		    			txtCodProvS.setText(tabla.getValueAt(fila, 0).toString());
		    			txtProveedorS.setText(tabla.getValueAt(fila, 1).toString());
		    			txtRecepcionS.setText(tabla.getValueAt(fila, 2).toString());
		        	}
		        	
		        	if(e.getClickCount() == 2){
		    			int fila			= tabla.getSelectedRow();
		    			String cod_prv		= tabla.getValueAt(fila, 0).toString();
		    			String proveedor	= tabla.getValueAt(fila, 1).toString();
		    			String fol_recepcion= tabla.getValueAt(fila, 2).toString();
		    			String fecha 		= tabla.getValueAt(fila, 3).toString();
		    			new Cat_Alimentacion_De_Resguardo(cod_prv,proveedor,fol_recepcion,fecha).setVisible(true);
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
			new Cat_Recibo_De_Mercancia_En_Resguardo().setVisible(true);
		}catch(Exception e){	}
	}
	
	public class Cat_Alimentacion_De_Resguardo extends JDialog{
	
	Container cont2 = getContentPane();
	JLayeredPane panel2 = new JLayeredPane();
	
	JButton btnGuardar = new JButton("Guardar", new ImageIcon("imagen/Guardar.png"));

	JTextField txtCodProducto = new Componentes().text(new JTextField(), "Codigo De Producto", 15, "String");
	JTextField txtDescripcion 	= new Componentes().text(new JTextField(), "Descripcion", 100, "String");
	
	JTextField txtCod_Prv 		= new Componentes().text(new JTextField(), "Codigo de proveedor", 20, "String");
	JTextField txtProveedor 	= new Componentes().text(new JTextField(), "Proveedor", 150, "String");
	JTextField txtRecepcion 	= new Componentes().text(new JTextField(), "Recepcion", 20, "String");
	JTextField txtfecha			= new Componentes().text(new JTextField(), "Fecha", 20, "String");
	
		DefaultTableModel modeloRecep = new DefaultTableModel(null,new String[]{"Codigo","Producto","Cant. Factura","Cant. Resguardo","Cant. Recibida","Cant. Pendiente","Cant. A Recibir"}){
		
		@SuppressWarnings("rawtypes")
		Class[] types = new Class[]{
				java.lang.Object.class,
				java.lang.Object.class,
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
       	 	case 4 : return false; 
       	 	case 5 : return false; 
       	 	case 6 : if(Float.valueOf(tablaRecep.getValueAt(fila, 5).toString())==0){
		 				return false;
		 			}else{
		 				return true;
		 			}
       	 	
       	 }
			return false;
		}
	};
	
	JTable tablaRecep = new JTable(modeloRecep);
	JScrollPane scrollRecep = new JScrollPane(tablaRecep);

	public Cat_Alimentacion_De_Resguardo(String cod_prv, String proveedor, String fol_recepcion, String fecha){
		
		this.setModal(true);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/descargar-caja-de-madera-icono-5941-64.png"));
		this.setTitle("Captura De Mercancia En Resguardo Recibida");
		panel2.setBorder(BorderFactory.createTitledBorder("Teclee La Cantidad De Productos En La Columna Cant. A Recibir"));		
		int x=15,y=20,ancho=80;
		
		
		panel2.add(new JLabel("Proveedor: ")).setBounds(x,y,70,20);
		panel2.add(txtCod_Prv 	).setBounds(x+80,y,70,20);    
		panel2.add(txtProveedor ).setBounds(x+150,y,ancho*4,20);
		
		panel2.add(new JLabel("Folio De Recepcion: ")).setBounds(x,y+=25,110,20);
		panel2.add(txtRecepcion ).setBounds(x+110,y,70,20); 
		
		panel2.add(new JLabel("Fecha: ")).setBounds(x+200,y,70,20);
		panel2.add(txtfecha		).setBounds(x+250,y,ancho+30,20);
		
		panel2.add(txtCodProducto  ).setBounds(x,y+=25,75,20);
		panel2.add(txtDescripcion).setBounds(x+75,y,ancho*6+20,20);
		panel2.add(btnGuardar).setBounds(x+870,y,100,20);
		
		panel2.add(scrollRecep).setBounds(x,y+=20,ancho*12+10,600);
		
		cont2.add(panel2);
		
		llamar_render_Recep();
		
		txtCodProducto.addKeyListener(opFiltroDinamicoCodProd);
		txtDescripcion.addKeyListener(opFiltroDinamicoProduct);
		
		
		txtCod_Prv 	.setEditable(false);
		txtProveedor.setEditable(false);   
		txtRecepcion.setEditable(false);   
		txtfecha	.setEditable(false);	
		
		txtCod_Prv 	.setText(cod_prv);
		txtProveedor   .setText(proveedor);
		txtRecepcion   .setText(fol_recepcion);
		txtfecha		.setText(fecha);
		
		llenarRecepcion(fol_recepcion);
		
		btnGuardar.addActionListener(opGuardar);
		
		this.setSize(1024, 720);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	
	public Object[][] get_Recep(String recepcion){
		return new BuscarTablasModel().recepcion_de_mercancia_en_resguardo(recepcion);
	}
	public void llenarRecepcion(String recep){
		
		while(tablaRecep.getRowCount()>0){
			modeloRecep.removeRow(0);
		}
		
		Object[][] recepciones = get_Recep(recep);
		for(Object[] rc : recepciones){
			modeloRecep.addRow(rc);
		}
	}
	
	private String valida_tabla(){
		String error = "";
		for(int i=0; i<tablaRecep.getRowCount(); i++){
//			try{
				if(!isNumeric(tablaRecep.getValueAt(i,6).toString())){
					error += "La Cant. A Recibir En El Codigo De Producto ["+modeloRecep.getValueAt(i,0).toString()+"] Esta Mal En Su Formato\n";
					tablaRecep.setValueAt("",i, 6);
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
				JOptionPane.showMessageDialog(null, "Algunas Cantidades A Recibir Estan Mal En Su Formato","Error",JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen//usuario-icono-eliminar5252-64.png"));
				return;
			}else{
				
				String[][] matriz = new String[tablaRecep.getRowCount()][2];
				
				for(int i=0; i<tablaRecep.getRowCount(); i++){
					
					if(Float.valueOf(tablaRecep.getValueAt(i, 3).toString())>=(Float.valueOf(tablaRecep.getValueAt(i, 4).toString())+Float.valueOf(tablaRecep.getValueAt(i, 6).toString()))){
//						guardar
						matriz[i][0]=tablaRecep.getValueAt(i, 0).toString().trim();
						matriz[i][1]=tablaRecep.getValueAt(i, 6).toString().trim().equals("")?"0":tablaRecep.getValueAt(i, 6).toString().trim();
					}else{
//						aviso  (sfsdfsd)
						JOptionPane.showMessageDialog(null, "La Cantidad a Recibir Ingresada Del Producto: "+tablaRecep.getValueAt(i, 1).toString().trim()+"\n Sobrepasa La Cantidad Pendiente del Resguardo:"+tablaRecep.getValueAt(i, 5).toString().trim(),"Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
						return;
					}
				}
				
				if(new GuardarTablasModel().Guardar_captura_inicial_de_resguardo_de_mercancia(txtCod_Prv.getText().trim(),txtRecepcion.getText().trim(),matriz)){
					JOptionPane.showMessageDialog(null, "La Cantidad de Recibo Se Guardó Exitosamente","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen//aplicara-el-dialogo-icono-6256-32.png"));
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
		tablaRecep.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",12));
		tablaRecep.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","derecha","Arial","negrita",12));
		
		tablaRecep.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","derecha","Arial","negrita",12));
		tablaRecep.getColumnModel().getColumn(4).setCellRenderer(new tablaRenderer("texto","derecha","Arial","negrita",12));
		tablaRecep.getColumnModel().getColumn(5).setCellRenderer(new tablaRenderer("texto","derecha","Arial","negrita",12));
		tablaRecep.getColumnModel().getColumn(6).setCellRenderer(new tablaRenderer("texto","derecha","Arial","negrita",12));
		
		this.tablaRecep.getTableHeader().setReorderingAllowed(false) ;

		int largo=80;
		this.tablaRecep.getColumnModel().getColumn(0).setMaxWidth(largo-20);
		this.tablaRecep.getColumnModel().getColumn(0).setMinWidth(largo-20);
		this.tablaRecep.getColumnModel().getColumn(1).setMaxWidth(470);
		this.tablaRecep.getColumnModel().getColumn(1).setMinWidth(470);
		this.tablaRecep.getColumnModel().getColumn(2).setMaxWidth(largo);
		this.tablaRecep.getColumnModel().getColumn(2).setMinWidth(largo);
		
		this.tablaRecep.getColumnModel().getColumn(3).setMaxWidth(largo+15);
		this.tablaRecep.getColumnModel().getColumn(3).setMinWidth(largo+15);
		this.tablaRecep.getColumnModel().getColumn(4).setMaxWidth(largo+5);
		this.tablaRecep.getColumnModel().getColumn(4).setMinWidth(largo+5);
		this.tablaRecep.getColumnModel().getColumn(5).setMaxWidth(largo+10);
		this.tablaRecep.getColumnModel().getColumn(5).setMinWidth(largo+10);
		this.tablaRecep.getColumnModel().getColumn(6).setMaxWidth(largo+10);
		this.tablaRecep.getColumnModel().getColumn(6).setMinWidth(largo+10);
	}
	KeyListener opFiltroDinamicoCodProd= new KeyListener(){
		public void keyReleased(KeyEvent arg0) {
			
				txtDescripcion.setText("");
				new Obj_Filtro_Dinamico(tablaRecep,"Producto", "","","", "", "", "", "");
				new Obj_Filtro_Dinamico(tablaRecep,"Codigo", txtCodProducto.getText().toUpperCase().trim(),"","", "", "", "", "");
			
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}		
	};
	KeyListener opFiltroDinamicoProduct  = new KeyListener(){
		public void keyReleased(KeyEvent arg0) {
			
				txtCodProducto.setText("");
				new Obj_Filtro_Dinamico(tablaRecep,"Codigo", "","","", "", "", "", "");
				new Obj_Filtro_Dinamico(tablaRecep,"Producto",  txtDescripcion.getText().toUpperCase().trim(),"","", "", "", "", "");
				
			
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}		
	};
	
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
