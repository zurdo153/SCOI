package Cat_Auditoria;

import java.awt.Container;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.GuardarSQL;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Planeacion.Obj_Seleccion_De_Usuarios;
import Obj_Principal.Componentes;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_Filtro_Dinamico_Plus;
import Obj_Renders.tablaRenderer;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import jxl.*;
import jxl.read.biff.BiffException;


@SuppressWarnings("serial")
public class Cat_Alimentacion_De_Inventarios_Fisicos extends JFrame{
		Container cont = getContentPane();
		JLayeredPane panel = new JLayeredPane();
		
		JTextArea txa_Observacion = new Componentes().textArea(new JTextArea(), "Obsevacion De Inventario Físico", 1000);
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
							,"Fecha Aplicación Inventario"};

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
		
		JButton btnAgregar = new JButton("Guardar Inventario",new ImageIcon("imagen/Guardar.png"));
		JButton btnDeshacer = new JButton("Deshacer",new ImageIcon("imagen/deshacer16.png"));
		JTextField txtTotalDiferencia = new JTextField();
		
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
			
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/informe-icono-7785-32.png"));
			this.setTitle("Alimentacion De Inventario Físico");
		    this.panel.setBorder(BorderFactory.createTitledBorder("Inventario Físico"));
			
		    this.trsfiltro = new TableRowSorter(model); 
			this.tabla.setRowSorter(trsfiltro);  
			this.txa_Observacion.setEditable(true);
			this.txa_Observacion.setLineWrap(true); 
			this.txa_Observacion.setWrapStyleWord(true);
			
			this.txtTotalDiferencia.setEditable(false);
			
			int x=15,y=20,width=100,height=20;

			this.panel.add(txtFiltro).setBounds                                      (x        ,y    						,ancho-140   ,height);
			this.panel.add(btnExaminar).setBounds                                    (ancho-120,y     						,width       ,height);
			this.panel.add(scroll).setBounds                                         (x        ,y+=20 						,ancho-35	 ,(((alto-40)/5)*3) );
			
			this.panel.add(new JLabel("Total De Diferencia Real Ultimo Costo:")).setBounds(ancho-320	,y+=(((alto-40)/5)*3)+5 	,200     ,height);
			this.panel.add(txtTotalDiferencia).setBounds(ancho-120	,y 	,100     ,height);
			
			this.panel.add(new JLabel("Observacion De Inventario Físico:")).setBounds  (x        ,y+=25 	,width*3     ,height);
			this.panel.add(observacion  ).setBounds                                    (x        ,y+=25 						,ancho-35	 ,(((alto-300)/5)*1) );
			this.panel.add(btnDeshacer).setBounds                                      (x        ,y+=(((alto-300)/5)*1) + 15 ,width       ,height);
			this.panel.add(btnAgregar).setBounds                                       (ancho-180,y 							,width+60       ,height);
			
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
//	                    	System.out.println(pathArchivo.substring(pathArchivo.length()-4, pathArchivo.length()).toUpperCase());
	                    	if(pathArchivo.substring(pathArchivo.length()-4, pathArchivo.length()).toUpperCase().equals(".XLS")){
	                    		 importar_excel(pathArchivo);      
	                    	}else{
	                    		JOptionPane.showMessageDialog(null, "Solo Se Permiten Archivos Con Extencion .xls, favor De Guardar Su Archivo Con La version 97-2003","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
	     		    			return;
	                    	}
	                                   	
	                    }
                 }
			}
		};
		
//		Workbook libroexcel = null;
//		Sheet hoja = null;
		public void importar_excel(String rutaCompleta) {
			double total_diferencia = 0;
			model.setRowCount(0); 
			 
			String Fecha="";
			String Establecimiento="";
			
			try {
				Workbook libroexcel = Workbook.getWorkbook(new File(rutaCompleta));
				
				 System.out.println("cantidad de hojas: "+libroexcel.getNumberOfSheets());
				
				
				Sheet hoja = libroexcel.getSheet(0); //Seleccionamos la hoja que vamos a leer
				
				 String[] vector = new String[hoja.getRows()];
				 
				 for (int columna = 0; columna < hoja.getColumns(); columna++) {
					 if(!hoja.getCell(columna, 0).getContents().equals(columnas[columna].toString())){
						 JOptionPane.showMessageDialog(null, "La Columna  ["+columnas[columna].toString()+"]  No Coinside Con La Del Archivo Que Selecciono,\nVerifique Que El Archivo Seleccionado Sea El Correcto O Que Las Columnas\nDel Archivo Tengan Los Nombres Correctamente.","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
		    			return;
					 }
				 }
				 
				 Fecha=hoja.getCell(12, 1).getContents();
				 Establecimiento=hoja.getCell(11, 1).getContents();
				 String existe = new BuscarSQL().Existe_Inventario_Guardado_Del_Establecimiento_En_La_Fecha(Fecha, Establecimiento);
				 String existeestablecimiento = new BuscarSQL().Existe_establecimiento_inventario_fisico( Establecimiento);
				 
				 if(existeestablecimiento.equals("N")){
					 JOptionPane.showMessageDialog(null, "El Establecimiento "+Establecimiento+" No Existe Verifique El Nombre","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
		    		return;						 
				 }
				 
				 if(existe.equals("N")){
//					 System.out.println(hoja.getRows());
					 for (int fila = 1; fila < hoja.getRows(); fila++){         
						 total_diferencia += Double.valueOf(hoja.getCell( 10 , fila ).getContents().toString().trim());
						
//						 if(!validarCelda(fila)){
//							 System.out.println(fila+"   "+hoja.getCell(0 , fila ).getContents().toString().trim());
//						 }
						 
						 for (int columna = 0; columna < hoja.getColumns(); columna++){
							 vector[columna]=hoja.getCell(columna, fila).getContents();
						 }
						 model.addRow(vector);
					 }
					 
			     }else{
			    	 JOptionPane.showMessageDialog(null, " "+existe,"Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
			    	 return;
			     }
				 txtTotalDiferencia.setText(new DecimalFormat().format(total_diferencia)+"");
				 
			} catch (BiffException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error Al Intentar Leer El Archivo \nMensaje:"+e.getMessage(), "Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			} 
		};
		
//	 	public boolean validarCelda(int fila){
//	 		
//			try{
//		        	if(!hoja.getCell( 10 , fila ).getContents().toString().trim().equals("")){
//		        		Float.valueOf(hoja.getCell( 10 , fila ).getContents().toString().trim());
//		        		return true;
//		        	}
//		        } catch (NumberFormatException nfe){
////		        	tabla.setValueAt("", fila, 3);
//		        	System.out.println("no es entero");
//		        }
//			return false;
//		}
		
		ActionListener opDeshacer = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				model.setRowCount(0);
				txa_Observacion.setText("");
			}
		};
		
		ActionListener opGuardar = new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				
				txtFiltro.setText("");
				filtro();
				Object[][] matriz = new Object[tabla.getRowCount()][tabla.getColumnCount()];
				
				for(int i = 0; i < tabla.getRowCount(); i++){
					for(int j = 0; j < tabla.getColumnCount(); j++){
						matriz[i][j] = tabla.getValueAt(i, j).toString().trim();
					}
				}
				
				if(new GuardarSQL().Guardar_Alimentacion_De_Inventario_Fisico(matriz)){
					btnDeshacer.doClick();
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