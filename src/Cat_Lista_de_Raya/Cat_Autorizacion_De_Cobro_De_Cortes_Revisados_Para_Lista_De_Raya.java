package Cat_Lista_de_Raya;

import java.awt.Container;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Conexiones_SQL.ActualizarSQL;
import Conexiones_SQL.Connexion;
import Obj_Principal.Componentes;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Autorizacion_De_Cobro_De_Cortes_Revisados_Para_Lista_De_Raya extends JFrame {
	    
		Container cont = getContentPane();
		JLayeredPane campo = new JLayeredPane();
		Connexion con = new Connexion();
		
		DefaultTableModel model = new DefaultTableModel(0,15){
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {
		             return listacolumnas(15)[columnIndex];
		         }   
			public boolean isCellEditable(int fila, int columna){
				if(columna == 0)
					return true;
				return false;
			}
		};
		
		@SuppressWarnings("rawtypes")
		public Class[] listacolumnas(int Columnas){
		Class[] lista = new Class[Columnas];
		for (int i = 0; i<lista.length; i++){
			if(i==0){
				lista[i]=(Boolean.class);
			}else{
				lista[i] =(String.class);
			}
		 }
		 return lista;
	   };
		
		JTable tabla = new JTable(model);
		@SuppressWarnings("rawtypes")
		private TableRowSorter trsfiltro;
		JTextField txtFolio = new Componentes().text(new JTextField(),"Teclee Folio del Empleado", 150, "Integer");
		JTextField txtNombre = new Componentes().text(new JTextField(),"Teclee Nombre del Empleado", 150, "String");
	    JButton btnAceptar = new JButton("Cobrar",new ImageIcon("Imagen/Aplicar.png"));
	    JButton btnNegar = new JButton("No Cobrar",new ImageIcon("Imagen/Delete.png"));
	    
	    
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Cat_Autorizacion_De_Cobro_De_Cortes_Revisados_Para_Lista_De_Raya()	{
			int ancho = Toolkit.getDefaultToolkit().getScreenSize().width;
			int alto = Toolkit.getDefaultToolkit().getScreenSize().height;
			
			setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds()); 
			
//			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/asistencia.png"));
			this.setTitle("Autorizacion De Cobro De Cortes Revisados");

			campo.setBorder(BorderFactory.createTitledBorder("Seleccione Los Cortes A Aplicar"));
			trsfiltro = new TableRowSorter(model); 
			tabla.setRowSorter(trsfiltro);  
			campo.add(getPanelTabla()).setBounds(15,42,ancho-25,alto-125);
			campo.add(txtFolio).setBounds(45,20,70,20);
			campo.add(txtNombre).setBounds(115,20,270,20);

			campo.add(btnNegar).setBounds(475,20,150,20);
			campo.add(btnAceptar).setBounds(743,20,150,20);
			
			Actualizar_tabla();
			cargar_render();
			
//			agregar(tabla);
			cont.add(campo);
			txtFolio.addKeyListener(opFiltroFolio);
			txtNombre.addKeyListener(opFiltroNombre);
			btnAceptar.addActionListener(opaceptar);
			btnNegar.addActionListener(opaceptar);
		}
		
		
		ActionListener opaceptar = new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
	    		
	    		actualizar(arg0.getActionCommand().equals("Cobrar")?1:0);
			}
		};
		
		public void actualizar(int aceptar_negar){
		Object[][] arreglo_guardado= new Object[tabla.getRowCount()][3];
		for (int i=0;i<tabla.getRowCount();i++){
		       	 arreglo_guardado[i][0]=tabla.getValueAt(i,0).toString().trim();
		       	 arreglo_guardado[i][1]=tabla.getValueAt(i,1).toString().trim();
		       	 arreglo_guardado[i][2]=aceptar_negar;
		    }
		
			if(new ActualizarSQL().Aceptar_Negar_Cobro_De_Corte(arreglo_guardado)){
	        	while(tabla.getRowCount()>0){	
		              model.removeRow(0);  }
	        	Actualizar_tabla();
				JOptionPane.showMessageDialog(null, "Se Actualizo Correctamente", "Aviso", JOptionPane.OK_OPTION,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
			}else{
				JOptionPane.showMessageDialog(null, "Error Al Actualizar", "Avise al Administrador Del Sistema", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			}
		}
		
		KeyListener opFiltroFolio = new KeyListener(){
			@SuppressWarnings("unchecked")
			public void keyReleased(KeyEvent arg0) {
				trsfiltro.setRowFilter(RowFilter.regexFilter(txtFolio.getText(), 1));
			}
			public void keyTyped(KeyEvent arg0) {
				char caracter = arg0.getKeyChar();
				if(((caracter < '0') ||
					(caracter > '9')) &&
				    (caracter != KeyEvent.VK_BACK_SPACE)){
					arg0.consume(); 
				}	
			}
			public void keyPressed(KeyEvent arg0) {}		
		};
		
		KeyListener opFiltroNombre = new KeyListener(){
			@SuppressWarnings("unchecked")
			public void keyReleased(KeyEvent arg0) {
				trsfiltro.setRowFilter(RowFilter.regexFilter(txtNombre.getText().toUpperCase().trim(), 2));
			}
			public void keyTyped(KeyEvent arg0) {}
			public void keyPressed(KeyEvent arg0) {}		
		};
		
    	public void cargar_render(){
    		//		tipo de valor = imagen,chb,texto
//    		tabla.getColumnModel().getColumn(# columna).setCellRenderer(new CellRenderer("tipo_de_valor","alineacion","tipo_de_letra","negrita",# tamanio_fuente));
        
    		tabla.getColumnModel().getColumn(0 ).setCellRenderer(new tablaRenderer("chb","","","",0));
    		tabla.getColumnModel().getColumn(1 ).setCellRenderer(new tablaRenderer("numerico","derecha","Arial","normal",12)); 
    		tabla.getColumnModel().getColumn(2 ).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
    		tabla.getColumnModel().getColumn(3 ).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
    		tabla.getColumnModel().getColumn(4 ).setCellRenderer(new tablaRenderer("texto","derecha","Arial","negrita",12));
    		tabla.getColumnModel().getColumn(5 ).setCellRenderer(new tablaRenderer("numerico","izquierda","Arial","normal",12)); 
    		tabla.getColumnModel().getColumn(6 ).setCellRenderer(new tablaRenderer("numerico","derecha","Arial","negrita",12)); 
    		tabla.getColumnModel().getColumn(7 ).setCellRenderer(new tablaRenderer("numerico","izquierda","Arial","normal",12)); 
    		tabla.getColumnModel().getColumn(8 ).setCellRenderer(new tablaRenderer("numerico","izquierda","Arial","normal",12));
    		tabla.getColumnModel().getColumn(9 ).setCellRenderer(new tablaRenderer("texto","centro","Arial","normal",12));
    		tabla.getColumnModel().getColumn(10).setCellRenderer(new tablaRenderer("fecha","izquierda","Arial","normal",12));
    		tabla.getColumnModel().getColumn(11).setCellRenderer(new tablaRenderer("texto","derecha","Arial","negrita",12));
    		tabla.getColumnModel().getColumn(12).setCellRenderer(new tablaRenderer("fecha","izquierda","Arial","normal",12));
    		tabla.getColumnModel().getColumn(13).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
    		tabla.getColumnModel().getColumn(14).setCellRenderer(new tablaRenderer("texto","centro","Arial","normal",12));
    	}
    	
		
	   	private JScrollPane getPanelTabla()	{		
			int a=70,b=200;
			
			tabla.getColumnModel().getColumn(0).setHeaderValue("*");
			tabla.getColumnModel().getColumn(0).setMaxWidth(30);
			tabla.getColumnModel().getColumn(0).setMinWidth(30);
			tabla.getColumnModel().getColumn(1).setHeaderValue("Folio Corte");
			tabla.getColumnModel().getColumn(1).setMaxWidth(a);
			tabla.getColumnModel().getColumn(1).setMinWidth(a);
			tabla.getColumnModel().getColumn(2).setHeaderValue("Empleado");
			tabla.getColumnModel().getColumn(2).setMaxWidth(b+a);
			tabla.getColumnModel().getColumn(2).setMinWidth(b+a);
			tabla.getColumnModel().getColumn(3).setHeaderValue("Establecimiento");
			tabla.getColumnModel().getColumn(3).setMaxWidth(b-50);
			tabla.getColumnModel().getColumn(3).setMinWidth(b-50);
			tabla.getColumnModel().getColumn(4).setHeaderValue("Dif. Corte");
			tabla.getColumnModel().getColumn(4).setMaxWidth(a);
			tabla.getColumnModel().getColumn(4).setMinWidth(a);
			tabla.getColumnModel().getColumn(5).setHeaderValue("Status Auditoria");
			tabla.getColumnModel().getColumn(5).setMaxWidth(b-60);
			tabla.getColumnModel().getColumn(5).setMinWidth(b-60);
			tabla.getColumnModel().getColumn(6).setHeaderValue("Dif. Auditoria");
			tabla.getColumnModel().getColumn(6).setMaxWidth(a+20);
			tabla.getColumnModel().getColumn(6).setMinWidth(a+20);
			tabla.getColumnModel().getColumn(7).setHeaderValue("Observaciones Auditoria");
			tabla.getColumnModel().getColumn(7).setMaxWidth(b*3);
			tabla.getColumnModel().getColumn(7).setMinWidth(b-50);
			tabla.getColumnModel().getColumn(8).setHeaderValue("Reviso Auditoria");
			tabla.getColumnModel().getColumn(8).setMaxWidth(b+a);
			tabla.getColumnModel().getColumn(8).setMinWidth(b+a);
			tabla.getColumnModel().getColumn(9).setHeaderValue("Fecha Revision Auditoria");
			tabla.getColumnModel().getColumn(9).setMaxWidth(b);
			tabla.getColumnModel().getColumn(9).setMinWidth(b);
			tabla.getColumnModel().getColumn(10).setHeaderValue("Status Seguridad");
			tabla.getColumnModel().getColumn(10).setMaxWidth(b-60);
			tabla.getColumnModel().getColumn(10).setMinWidth(b-60);
			tabla.getColumnModel().getColumn(11).setHeaderValue("Dif. Seguridad");
			tabla.getColumnModel().getColumn(11).setMaxWidth(a+20);
			tabla.getColumnModel().getColumn(11).setMinWidth(a+20);
			tabla.getColumnModel().getColumn(12).setHeaderValue("Observaciones Seguridad");
			tabla.getColumnModel().getColumn(12).setMaxWidth(b*3);
			tabla.getColumnModel().getColumn(12).setMinWidth(b-50);
			tabla.getColumnModel().getColumn(13).setHeaderValue("Reviso Seguridad");
			tabla.getColumnModel().getColumn(13).setMaxWidth(b+a);
			tabla.getColumnModel().getColumn(13).setMinWidth(b+a);
			tabla.getColumnModel().getColumn(14).setHeaderValue("Fecha Revision Seguridad");
			tabla.getColumnModel().getColumn(14).setMaxWidth(b);
			tabla.getColumnModel().getColumn(14).setMinWidth(b);


	    	tabla.getTableHeader().setReorderingAllowed(false) ;
	    	tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			

			 JScrollPane scrol = new JScrollPane(tabla);
			   
		    return scrol; 
		}
		
	   	public void Actualizar_tabla(){
			Statement s;
			ResultSet rs;
			try {
				s = con.conexion().createStatement();
				rs = s.executeQuery("exec sp_select_revision_de_cortes_con_cobro");
				Object [] fila = new Object[15];
				while (rs.next()) {
				   fila[0 ] = false;
				   fila[1 ] = rs.getString(1)+"  ";   
				   fila[2 ] = "   "+rs.getString(2);  
				   fila[3 ] = "   "+rs.getString(3);  
				   fila[4 ] = "   "+rs.getString(4);  
				   fila[5 ] = "   "+rs.getString(5);  
				   fila[6 ] = "   "+rs.getString(6);  
				   fila[7 ] = "   "+rs.getString(7);  
				   fila[8 ] = "   "+rs.getString(8);  
				   fila[9 ] = "   "+rs.getString(9);  
				   fila[10] = "   "+rs.getString(10); 
				   fila[11] = "   "+rs.getString(11);
				   fila[12] = "   "+rs.getString(12);
				   fila[13] = "   "+rs.getString(13);
				   fila[14] = "   "+rs.getString(14); 
				   
			  	   model.addRow(fila); 
				}	
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
	   	};
		KeyListener validaCantidad = new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e){
				char caracter = e.getKeyChar();				
				if(((caracter < '0') ||	
			    	(caracter > '9')) && 
			    	(caracter != '.' )){
			    	e.consume();
		    	}
			}
			@Override
			public void keyReleased(KeyEvent e) {	
			}
			@Override
			public void keyPressed(KeyEvent arg0) {
			}	
		};
		
		KeyListener validaNumericoConPunto = new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				char caracter = e.getKeyChar();
				
			    if(((caracter < '0') ||	
			    	(caracter > '9')) && 
			    	(caracter != '.')){
			    	e.consume();
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
				new Cat_Autorizacion_De_Cobro_De_Cortes_Revisados_Para_Lista_De_Raya().setVisible(true);
			}catch(Exception e){	}
		}
	}

