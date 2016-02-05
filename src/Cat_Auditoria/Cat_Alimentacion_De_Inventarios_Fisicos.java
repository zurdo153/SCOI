package Cat_Auditoria;

import java.awt.Container;
import java.awt.GraphicsEnvironment;
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
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Cat_Planeacion.Cat_Alimentacion_De_Plan_Semanal.Cat_Observacion_De_Actividad;
import Conexiones_SQL.Connexion;
import Conexiones_SQL.GuardarSQL;
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
		
		JTextArea txa_Observacion = new Componentes().textArea(new JTextArea(), "Obsevacion De Inventario F�sico", 1000);
		JScrollPane observacion = new JScrollPane(txa_Observacion);
		
		String[] columnas = {"Codigo Producto"
							,"Producto"
							,"Contenido"
							,"Existencia Teorica"
							,"Existencia Fisica"
							,"Diferencia"
							,"Costo Por Carton"
							,"Costo Por Pieza"
							,"Costo Total Teorico"
							,"Costo Total Fisico"
							,"Diferiencia Real Ultimo Costo"
							,"Establecimiento"
							,"Fecha Aplicaci�n Inventario"};

		DefaultTableModel model = new DefaultTableModel(null, columnas){
		     @SuppressWarnings("rawtypes")
			Class[] types = new Class[]{
		    	java.lang.String.class,
		    	java.lang.String.class,
		    	java.lang.String.class,
		    	java.lang.String.class,
		    	java.lang.String.class,
		    	java.lang.String.class,
		    	java.lang.String.class,
		    	java.lang.String.class,
		    	java.lang.String.class,
		    	java.lang.String.class,
		    	java.lang.String.class,
		    	java.lang.String.class,
		    	java.lang.String.class	
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
	        	 	case 5 : return false; 
	        	 	case 6 : return false; 
	        	 	case 7 : return false; 
	        	 	case 8 : return false; 
	        	 	case 9 : return false; 
	        	 	case 10 : return false; 
	        	 	case 11 : return false; 
	        	 	case 12 : return false; 
	        	 } 				
	 			return false;
	 		}
		};
		
		JTable tabla = new JTable(model);
	    JScrollPane scroll = new JScrollPane(tabla);
		
		@SuppressWarnings("rawtypes")
		private TableRowSorter trsfiltro;
		JTextField txtFiltro= new Componentes().text(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<", 300, "String");
		
		JButton btnExaminar = new JButton("Examinar",new ImageIcon("Iconos/zoom_icon&16.png"));
		
		JButton btnAgregar = new JButton("Aplicar",new ImageIcon("imagen/Aplicar.png"));
		JButton btnDeshacer = new JButton("Deshacer",new ImageIcon("imagen/deshacer16.png"));
		
		Obj_Usuario usuario = new Obj_Usuario().LeerSession();
		Obj_Seleccion_De_Usuarios usuarios= new Obj_Seleccion_De_Usuarios();
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Cat_Alimentacion_De_Inventarios_Fisicos(){
			
			int ancho = Toolkit.getDefaultToolkit().getScreenSize().width;
			int alto = Toolkit.getDefaultToolkit().getScreenSize().height;
			
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds()); 
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/ayudar-a-ver-el-boton-icono-4900-64.png"));
			this.setTitle("Alimentacion De Inventario F�sico");
		    this.panel.setBorder(BorderFactory.createTitledBorder("Inventario F�sico"));
			
		    this.trsfiltro = new TableRowSorter(model); 
			this.tabla.setRowSorter(trsfiltro);  
			this.txa_Observacion.setEditable(true);
			this.txa_Observacion.setLineWrap(true); 
			this.txa_Observacion.setWrapStyleWord(true);
			
			int x=15,y=20,width=100,height=20;

			this.panel.add(txtFiltro).setBounds                                      (x        ,y    						,ancho-140   ,height);
			this.panel.add(btnExaminar).setBounds                                    (ancho-120,y     						,width       ,height);
			
			this.panel.add(scroll).setBounds                                         (x        ,y+=20 						,ancho-35	 ,(((alto-40)/5)*3) );
			
			x=15;
			this.panel.add(new JLabel("Observacion De Inventario F�sico:")).setBounds(x        ,y+=(((alto-40)/5)*3) +30 	,width*3     ,height);
			this.panel.add(observacion  ).setBounds                                    (x        ,y+=25 						,ancho-35	 ,(((alto-300)/5)*1) );
			this.panel.add(btnDeshacer).setBounds                                    (x        ,y+=(((alto-300)/5)*1) + 15 ,width       ,height);
			this.panel.add(btnAgregar).setBounds                                     (ancho-120,y 							,width       ,height);
			
			this.cont.add(panel);
			this.init_tabla();
			
			this.btnExaminar.addActionListener(opExaminar);
			this.btnDeshacer.addActionListener(opDeshacer);
			this.btnAgregar.addActionListener(opGuardar);
			
			this.txtFiltro.addKeyListener(opFiltroFolio);
		}
		
		ActionListener opExaminar = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
					
				JFileChooser elegir = new JFileChooser();
            	//Creamos el filtro
            	FileNameExtensionFilter filtro = new FileNameExtensionFilter("*.xls", "xls");
            	 
            	//Le indicamos el filtro
            	elegir.setFileFilter(filtro);
            	
            	int opcion = elegir.showOpenDialog(btnExaminar);
		                	
                 //Si presionamos el boton ABRIR en pathArchivo obtenemos el path del archivo
                 if(opcion == JFileChooser.APPROVE_OPTION){
                    
                	 String pathArchivo = elegir.getSelectedFile().getPath(); //Obtiene path del archivo
                    
	                    if(!pathArchivo.equals("")){
	                      importar_excel(pathArchivo);                    	
	                    }
                 }
			}
		};
		
		public void importar_excel(String rutaCompleta) {
			Workbook libroexcel = null;
			try {
//				libroexcel = Workbook.getWorkbook(new File(System.getProperty("user.dir")+"/Excel/Inventarios/"+nombre_archivo_excel_a_leer+".xls"));
				libroexcel = Workbook.getWorkbook(new File(rutaCompleta));
				
				 Sheet hoja = libroexcel.getSheet(0); //Seleccionamos la hoja que vamos a leer
				 String[] vector = new String[hoja.getRows()];
				 
				 
				 for (int columna = 0; columna < hoja.getColumns(); columna++) {
					 if(!hoja.getCell(columna, 0).getContents().equals(columnas[columna].toString())){
						 JOptionPane.showMessageDialog(null, "La Columna  ["+columnas[columna].toString()+"]  No Coinside Con La Del Archivo Que Selecciono,\nVerifique Que El Archivo Seleccionado Sea El Correcto O Que Las Columnas\nDel Archivo Tengan Los Nombres Correctamente.","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
		    				return;
					 }
				 }
				 
				 
				 
				 
				 for (int fila = 1; fila < hoja.getRows(); fila++) {                   
					 for (int columna = 0; columna < hoja.getColumns(); columna++) {
						 vector[columna]=hoja.getCell(columna, fila).getContents();
					 }
					 model.addRow(vector);
			  }
				 
				 
				 
			} catch (BiffException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error Al Intentar Leer El Archivo \nMensaje:"+e.getMessage(), "Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			} 

		};
		
		ActionListener opDeshacer = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				model.setRowCount(0);
				txa_Observacion.setText("");
			}
		};
		
		ActionListener opGuardar = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				txtFiltro.setText("");
				filtro();
				Object[][] matriz = new Object[tabla.getRowCount()][tabla.getColumnCount()];
				
				for(int i = 0; i < tabla.getRowCount(); i++){
					for(int j = 0; j < tabla.getColumnCount(); j++){
						matriz[i][j] = tabla.getValueAt(i, j).toString().trim();
					}
				}
				
				if(new GuardarSQL().Guardar_Alimentacion_De_Inventario_Fisico(matriz)){
					JOptionPane.showMessageDialog(null, "La Alimentacion De Inventario Fisico Se Guardo Correctamente","Aviso", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/aplicara-el-dialogo-icono-6256-32.png"));
					return;
				}else{
					JOptionPane.showMessageDialog(null, "No Se A Podido Gaurdar El Inventario Fisico", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
					return;
				}
				
				
			}
		};
		
		KeyListener opFiltroFolio = new KeyListener(){
			public void keyReleased(KeyEvent arg0) {
				filtro();
			}
			public void keyTyped(KeyEvent arg0)   {}
			public void keyPressed(KeyEvent arg0) {}		
		};
		
		public void filtro(){
			int[] columnas = {0,1,2,3,4};
			new Obj_Filtro_Dinamico_Plus(tabla, txtFiltro.getText().toUpperCase(), columnas);
		}
		
		public void init_tabla(){
			
			this.tabla.getTableHeader().setReorderingAllowed(false) ;
			this.tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

			int a = 120,b=500;
			for(int i = 0; i < 13; i++){
				if(i<=0){
					tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12));
				}else{
					tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
				}
				
				if(i==1){
					tabla.getColumnModel().getColumn(i).setMinWidth(b);
					tabla.getColumnModel().getColumn(i).setMaxWidth(a+b);
				}else{
					
					if(i==10 || i==12){
						tabla.getColumnModel().getColumn(i).setMinWidth(a+40);
						tabla.getColumnModel().getColumn(i).setMaxWidth(a*3);
					}else{
						tabla.getColumnModel().getColumn(i).setMinWidth(a);
						tabla.getColumnModel().getColumn(i).setMaxWidth(a*3);
					}
				}
			}
		}
	
	public static void main(String[] args) {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Alimentacion_De_Inventarios_Fisicos().setVisible(true);
		}catch(Exception e){	}	
	}
}