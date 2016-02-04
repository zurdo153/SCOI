package Cat_Auditoria;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Conexiones_SQL.Connexion;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Planeacion.Obj_Seleccion_De_Usuarios;
import Obj_Principal.Componentes;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_Filtro_Dinamico_Plus;
import Obj_Renders.tablaRenderer;
import java.io.File;
import java.io.IOException;

import jxl.*;
import jxl.read.biff.BiffException;


@SuppressWarnings("serial")
public class Cat_Alimentacion_De_Inventarios_Fisicos extends JFrame{
		Container cont = getContentPane();
		JLayeredPane panel = new JLayeredPane();
		
		JTextArea txa_Resultado_Seleccion = new Componentes().textArea(new JTextArea(), "Concepto", 135);
		JScrollPane Resultado = new JScrollPane(txa_Resultado_Seleccion);
		
		DefaultTableModel model = new DefaultTableModel(null, new String[]{"Folio", "Nombre Completo","Establecimiento","Departamento","Puesto", " *"}
				){
		     @SuppressWarnings("rawtypes")
			Class[] types = new Class[]{
		    	java.lang.Integer.class,
		    	java.lang.String.class,
		    	java.lang.String.class,
		    	java.lang.String.class,
		    	java.lang.String.class,
		    	java.lang.Boolean.class	    	
	         };
		     @SuppressWarnings({ "rawtypes", "unchecked" })
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
	        	 	case 5 : return true; 
	        	 } 				
	 			return false;
	 		}
			
		};
		
		JTable tabla = new JTable(model);
	    JScrollPane scroll = new JScrollPane(tabla);
		
		@SuppressWarnings("rawtypes")
		private TableRowSorter trsfiltro;
		JTextField txtFiltro= new Componentes().text(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<", 300, "String");
		
		JButton btnAgregar = new JButton("Aplicar",new ImageIcon("imagen/Aplicar.png"));
		JButton btnDeshacer = new JButton("Deshacer",new ImageIcon("imagen/deshacer16.png"));
		
		Obj_Usuario usuario = new Obj_Usuario().LeerSession();
		Obj_Seleccion_De_Usuarios usuarios= new Obj_Seleccion_De_Usuarios();
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Cat_Alimentacion_De_Inventarios_Fisicos()	{
			this.setSize(1024,740);
			this.setLocationRelativeTo(null);
			this.setResizable(false);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/ayudar-a-ver-el-boton-icono-4900-64.png"));
			this.setTitle("Filtro De Seleccion De Colaboradores");
		    this.panel.setBorder(BorderFactory.createTitledBorder("Selecciona A El (Los) Colaborador(es) A El (Los) Que Aplicara La Actividad"));
			this.trsfiltro = new TableRowSorter(model); 
			this.tabla.setRowSorter(trsfiltro);  
			this.txa_Resultado_Seleccion.setEditable(false);
			this.txa_Resultado_Seleccion.setLineWrap(true); 
			this.txa_Resultado_Seleccion.setWrapStyleWord(true);
			
			int x=15,y=20,width=100,height=20;

			this.panel.add(txtFiltro).setBounds                                                   (x     ,y     ,width*9+40 ,height);
			this.panel.add(scroll).setBounds                                                      (x     ,y+=20 ,width*10-10,width*3);
			
			x=15;
			this.panel.add(new JLabel("     Detalle De La Configuracion Seleccionada:")).setBounds(x     ,y+=320,width*3    ,height);
			this.panel.add(Resultado  ).setBounds                                                 (x     ,y+=20 ,width*10-10,width*3);
			this.panel.add(btnDeshacer).setBounds                                                 (x     ,y+=305,width      ,height);
			this.panel.add(btnAgregar).setBounds                                                  (x+892 ,y     ,width      ,height);
			
			importar_excel();
			
			this.cont.add(panel);
			this.init_tabla();
			this.tabla.addMouseListener(opcomentario);
			
//			this.tabla.addKeyListener(opseleccioncontecladocomentario);
			this.txtFiltro.addKeyListener(opFiltroFolio);
			
			this.btnAgregar.addActionListener(Agregar);
			this.btnDeshacer.addActionListener(deshacer);

		}
		String nombre_archivo_excel_a_leer="excel";
		public void importar_excel() {
			Workbook libroexcel = null;
			try {
				libroexcel = Workbook.getWorkbook(new File(System.getProperty("user.dir")+"/Excel/Inventarios/"+nombre_archivo_excel_a_leer+".xls"));
			} catch (BiffException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error Al Intentar Leer El Archivo \nMensaje:"+e.getMessage(), "Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			} 
			 Sheet hoja = libroexcel.getSheet(0); //Seleccionamos la hoja que vamos a leer
			 String columnaA ="",columnaB="",columnaC="";
			 
			 for (int fila = 1; fila < hoja.getRows(); fila++) {                     //recorremos las filas
				 for (int columna = 0; columna < hoja.getColumns(); ) {     //recorremos las columnas
					 if(columna==0){
					 columnaA = hoja.getCell(columna, fila).getContents();        //setear la celda leida a nombre
					 }
					 if(columna==1){
					 columnaB = hoja.getCell(columna, fila).getContents();        //setear la celda leida a nombre
					 }
					 columnaC= hoja.getCell(columna, fila).getContents();        //setear la celda leida a nombre
				    System.out.print("columna A:"+columnaA+"\n" ); // imprimir nombre
				    System.out.print("Columna B:"+columnaB+"\n" ); // imprimir nombre
				columna++;
				 }
				System.out.println("\n");
		  }
		};
		
		
		ActionListener Agregar = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int[] columnas = {0,1,2};
				new Obj_Filtro_Dinamico_Plus(tabla,"", columnas);
				if(tabla.isEditing()){
					tabla.getCellEditor().stopCellEditing();
				}
				txtFiltro.setText("");
				
//				usuarios.setUsuarios(tabla_seleccion_de_folios_colaboradores());
									dispose();
			}
		};
		
		ActionListener deshacer = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				txa_Resultado_Seleccion.setText("");
				refrestabla();
			 }
			};
		
			
		MouseListener opcomentario = new MouseListener() {
			public void mouseReleased(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseClicked(MouseEvent arg0) {
			}
		};
		
		
		KeyListener opFiltroFolio = new KeyListener(){
			public void keyReleased(KeyEvent arg0) {
				int[] columnas = {0,1,2,3,4};
				new Obj_Filtro_Dinamico_Plus(tabla, txtFiltro.getText().toUpperCase(), columnas);
			}
			public void keyTyped(KeyEvent arg0)   {}
			public void keyPressed(KeyEvent arg0) {}		
		};
		
		
		public void tabla_seleccion_default_usuario(){
			for(int i=0; i<tabla.getRowCount(); i++){
				 if(Integer.valueOf(tabla.getValueAt(i,0).toString().trim())==(usuario.getFolio())){
					  model.setValueAt("true", i, 5);
			     }
			}
		}
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public String[] tabla_seleccion_para_comentario(){
			Vector vector = new Vector();
			for(int i=0; i<tabla.getRowCount(); i++){
				 if(Boolean.valueOf(tabla.getValueAt(i,5).toString().trim())){
					  vector.add(model.getValueAt(i,1).toString().trim());
			     }
			}
			String[] matriz = new String[vector.size()];
			int j =0;
			while(j<vector.size()){
				matriz[j] = vector.get(j).toString();
				j++;
			}
			return matriz;
		}
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public String[] tabla_seleccion_de_folios_colaboradores(){
			Vector vector = new Vector();
			for(int i=0; i<tabla.getRowCount(); i++){
				 if(Boolean.valueOf(tabla.getValueAt(i,5).toString().trim())){
					  vector.add(model.getValueAt(i,0).toString().trim());
			     }
			}
			String[] matriz = new String[vector.size()];
			int j =0;
			while(j<vector.size()){
				matriz[j] = vector.get(j).toString();
				j++;
			}
			return matriz;
		}
		
		@SuppressWarnings("unchecked")
		public void init_tabla(){
			this.tabla.getTableHeader().setReorderingAllowed(false) ;
			this.tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

			this.tabla.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",12));
			this.tabla.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",12));
			this.tabla.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",12));
			this.tabla.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",12));
			this.tabla.getColumnModel().getColumn(4).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",12));
			this.tabla.getColumnModel().getColumn(5).setCellRenderer(new tablaRenderer("CHB","centro","Arial","negrita",12));
			
			this.tabla.getColumnModel().getColumn(0).setMaxWidth(40);
			this.tabla.getColumnModel().getColumn(0).setMinWidth(40);
			this.tabla.getColumnModel().getColumn(1).setMinWidth(335);
			this.tabla.getColumnModel().getColumn(1).setMaxWidth(335);
			this.tabla.getColumnModel().getColumn(2).setMinWidth(140);
			this.tabla.getColumnModel().getColumn(2).setMaxWidth(140);
			this.tabla.getColumnModel().getColumn(3).setMinWidth(185);
			this.tabla.getColumnModel().getColumn(3).setMaxWidth(500);
			this.tabla.getColumnModel().getColumn(4).setMinWidth(240);
			this.tabla.getColumnModel().getColumn(4).setMaxWidth(500);
			this.tabla.getColumnModel().getColumn(5).setMinWidth(20);
			this.tabla.getColumnModel().getColumn(5).setMaxWidth(30);
			this.tabla.setRowSorter(trsfiltro);  
			refrestabla();
		}
		
		private void refrestabla(){
			model.setRowCount(0);
			Statement s;
			ResultSet rs;
			try {
				Connexion con = new Connexion();
				s = con.conexion().createStatement();
				rs = s.executeQuery("exec sp_filtro_empleado_actividades_status_vigente "+usuario.getFolio());
				while (rs.next())
				{  String [] fila = new String[6];
				   fila[0] = rs.getString(1).trim();
				   fila[1] = rs.getString(2).trim();
				   fila[2] = rs.getString(3).trim(); 
				   fila[3] = rs.getString(4).trim(); 
				   fila[4] = rs.getString(5).trim(); 
				   fila[5] = "false";
				   model.addRow(fila); 
				}	
			} catch (SQLException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en Cat_Etapas en la funcion refrestabla  SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			}
		}
	
	public static void main(String[] args) {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Alimentacion_De_Inventarios_Fisicos().setVisible(true);
		}catch(Exception e){	}	
	}
}
