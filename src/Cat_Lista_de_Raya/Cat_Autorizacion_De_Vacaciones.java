package Cat_Lista_de_Raya;

import java.awt.Container;
import java.awt.GraphicsEnvironment;
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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Conexiones_SQL.ActualizarSQL;
import Conexiones_SQL.Connexion;
import Conexiones_SQL.Generacion_Reportes;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.Obj_Filtro_Dinamico;
import Obj_Principal.Obj_Filtro_Dinamico_Plus;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Autorizacion_De_Vacaciones extends JFrame {
	    
		Container cont = getContentPane();
		JLayeredPane campo = new JLayeredPane();
		Connexion con = new Connexion();
		
		DefaultTableModel model = new DefaultTableModel(0,9){
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {
		             return listacolumnas(9)[columnIndex];
		         }   
			public boolean isCellEditable(int fila, int columna){
				if(columna < 8)
					return false;
				return true;
			}
		};
		
		@SuppressWarnings("rawtypes")
		public Class[] listacolumnas(int Columnas){
		Class[] lista = new Class[Columnas];
		for (int i = 0; i<lista.length; i++){
				lista[i] =(String.class);
		 }
		 return lista;
	   };
		
		JTable tabla = new JTable(model);
		@SuppressWarnings("rawtypes")
		private TableRowSorter trsfiltro;
		
		JTextField txtFolio = new Componentes().text(new JTextField(),"Teclee Folio del Empleado", 150, "Int");
		JTextField txtEmpleado = new Componentes().text(new JTextField(),"Teclee Nombre del Empleado", 150, "String");
	    JButton btnAceptar = new JCButton("Aceptar","Aplicar.png","Azul");
	    JButton btnNegar = new JCButton("Negar","Delete.png","Azul");
	    
	    JButton btnReporte = new JCButton("Prevacaciones", "Report.png","Azul");
	  
	    JButton btnVacacionesAutorizadas = new JCButton("Vacaciones Autorizadas","Report.png","Azul");
	    
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Cat_Autorizacion_De_Vacaciones()	{
			int ancho = Toolkit.getDefaultToolkit().getScreenSize().width;
			int alto = Toolkit.getDefaultToolkit().getScreenSize().height;
			
			setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds()); 
			
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/asistencia.png"));
			this.setTitle("Autorizacion De Vacaciones");

			campo.setBorder(BorderFactory.createTitledBorder("Autorizacion De Vacaciones"));
			trsfiltro = new TableRowSorter(model); 
			tabla.setRowSorter(trsfiltro);  
			campo.add(getPanelTabla()).setBounds(15,42,ancho-25,alto-125);
			campo.add(txtFolio).setBounds(85,20,70,20);
			campo.add(txtEmpleado).setBounds(155,20,270,20);

			campo.add(btnNegar).setBounds(475,20,100,20);
			campo.add(btnAceptar).setBounds(643,20,100,20);
			
			campo.add(btnReporte).setBounds(803,20,150,20);
			campo.add(btnVacacionesAutorizadas).setBounds(1103,20,200,20);
			
			Actualizar_tabla("V");
			cargar_render();
			
			agregar(tabla);
			cont.add(campo);
			txtFolio.addKeyListener(opFiltroEmpleado);
			txtEmpleado.addKeyListener(opFiltroEmpleado);
			
			btnAceptar.addActionListener(opAceptar);
			btnNegar.addActionListener(opNegar);
			btnReporte.addActionListener(opReporte);
			btnVacacionesAutorizadas.addActionListener(opVacaciones);
		}
		
		String observacionTestigo = "";
		private void agregar(final JTable tbl) {
	        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
		        public void mouseClicked(MouseEvent e) {
		        	if(e.getClickCount() > 0){
		    			observacionTestigo = tabla.getValueAt(tabla.getSelectedRow(), 8).toString().trim().toUpperCase();
		        	}
		        }
	        });
	    }
		
		ActionListener opReporte = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(tabla.getSelectedRow() >= 0){
					reporte(Integer.valueOf(tabla.getValueAt(tabla.getSelectedRow(), 0).toString().trim()), "prevacaciones".toUpperCase());
				}else{
					JOptionPane.showMessageDialog(null, "Es Necesario Seleccionar Un Colaborador De La Tabla Para Revisar Sus Vacaciones", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
					return;
				}
			}
		};
		
		public void reporte(int folio_vacaciones, String tipo_de_reporte){
			String basedatos="2.26";
			String vista_previa_reporte="no";
			int vista_previa_de_ventana=0;
			String comando= "exec reporte_de_vacaciones "+folio_vacaciones+",'"+tipo_de_reporte+"','"+new Obj_Usuario().LeerSession().getNombre_completo()+"'";
			String reporte ="Obj_Vacaciones.jrxml";
			 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
//			 reporte="Obj_Registro_De_Finiquito.jrxml";
//			 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
		}
		
		ActionListener opAceptar = new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
	    		
	    		if(tabla.getSelectedRow() >= 0){
	    			Autorizar_Negar("A");
				}else{
					JOptionPane.showMessageDialog(null, "Es Necesario Seleccionar El EL Registro De Vacaciones Que Sera Aprovado", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
					return;
				}
	    		
			}
		};
		
		ActionListener opNegar = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(tabla.getSelectedRow() >= 0){
	    			Autorizar_Negar("N");
				}else{
					JOptionPane.showMessageDialog(null, "Es Necesario Seleccionar El Registro De Vacaciones Que Sera Rechazado", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
					return;
				}
			}
		};
		
		public void Autorizar_Negar(String Status_Vacaciones){
			
			if(tabla.isEditing()){
				tabla.getCellEditor().stopCellEditing();
			}
			
			int folio_vacaciones = Integer.valueOf(tabla.getValueAt(tabla.getSelectedRow(), 0).toString().trim());
			String observacion = observacionTestigo.equals(tabla.getValueAt(tabla.getSelectedRow(), 8).toString().trim().toUpperCase())?"":tabla.getValueAt(tabla.getSelectedRow(), 8).toString().trim().toUpperCase();
			
			if(new ActualizarSQL().Autorizacion_de_vacaciones(folio_vacaciones,Status_Vacaciones,observacion)){
		        	model.setRowCount(0);
		        	Actualizar_tabla("V");
		        	
		        	if(Status_Vacaciones.equals("A")){
		        		reporte(folio_vacaciones,"vacaciones".toUpperCase());
		        	}else{
						JOptionPane.showMessageDialog(null, "Se Actualizo Correctamente", "Aviso", JOptionPane.OK_OPTION,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
		        	}
			}else{
				JOptionPane.showMessageDialog(null, "Error Al Actualizar", "Avise al Administrador Del Sistema", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			}
		};
		
		ActionListener opVacaciones = new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
	    		new Cat_Vacaciones_Autorizados().setVisible(true);
			}
		};
		
		KeyListener opFiltroEmpleado = new KeyListener(){
			public void keyReleased(KeyEvent arg0) {
				new Obj_Filtro_Dinamico(tabla,"Folio Emp.",txtFolio.getText().trim(),"Empleado",txtEmpleado.getText().toUpperCase().trim(),"","","","");
			}
			public void keyTyped(KeyEvent arg0)   {}
			public void keyPressed(KeyEvent arg0) {}		
		};
		
    	public void cargar_render(){
    		//		tipo de valor = imagen,chb,texto
//    		tabla.getColumnModel().getColumn(# columna).setCellRenderer(new CellRenderer("tipo_de_valor","alineacion","tipo_de_letra","negrita",# tamanio_fuente));
        
    		tabla.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("numerico","derecha","Arial","normal",10)); 
    		tabla.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("numerico","derecha","Arial","normal",10)); 
    		tabla.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",10)); 
    		tabla.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",10));
    		tabla.getColumnModel().getColumn(4).setCellRenderer(new tablaRenderer("numerico","derecha","Arial","negrita",9));
    		tabla.getColumnModel().getColumn(5).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",11)); 
    		tabla.getColumnModel().getColumn(6).setCellRenderer(new tablaRenderer("fecha","izquierda","Arial","normal",11)); 
    		tabla.getColumnModel().getColumn(7).setCellRenderer(new tablaRenderer("texto","centro","Arial","normal",11)); 
    		tabla.getColumnModel().getColumn(8).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",11)); 
    	}
    	
		
	   	private JScrollPane getPanelTabla()	{		
			int a=70,b=200;
			
			tabla.getColumnModel().getColumn(0).setHeaderValue("Folio");
			tabla.getColumnModel().getColumn(0).setMaxWidth(a);
			tabla.getColumnModel().getColumn(0).setMinWidth(a);
			tabla.getColumnModel().getColumn(1).setHeaderValue("Folio Emp.");
			tabla.getColumnModel().getColumn(1).setMaxWidth(a);
			tabla.getColumnModel().getColumn(1).setMinWidth(a);
			tabla.getColumnModel().getColumn(2).setHeaderValue("Empleado");
			tabla.getColumnModel().getColumn(2).setMaxWidth(b+a);
			tabla.getColumnModel().getColumn(2).setMinWidth(b+a);
			tabla.getColumnModel().getColumn(3).setHeaderValue("Establecimiento");
			tabla.getColumnModel().getColumn(3).setMaxWidth(b-60);
			tabla.getColumnModel().getColumn(3).setMinWidth(b-60);
			tabla.getColumnModel().getColumn(4).setHeaderValue("Total a Pagar");
			tabla.getColumnModel().getColumn(4).setMaxWidth(b-100);
			tabla.getColumnModel().getColumn(4).setMinWidth(b-100);
			tabla.getColumnModel().getColumn(5).setHeaderValue("Elaboro");
			tabla.getColumnModel().getColumn(5).setMaxWidth(b+a);
			tabla.getColumnModel().getColumn(5).setMinWidth(b+a);
			tabla.getColumnModel().getColumn(6).setHeaderValue("Fecha De Elaboracion");
			tabla.getColumnModel().getColumn(6).setMaxWidth(a*2);
			tabla.getColumnModel().getColumn(6).setMinWidth(a*2);
			tabla.getColumnModel().getColumn(7).setHeaderValue("Dias Transcurridos");
			tabla.getColumnModel().getColumn(7).setMaxWidth(b-100);
			tabla.getColumnModel().getColumn(7).setMinWidth(b-100);
			tabla.getColumnModel().getColumn(8).setHeaderValue("Observacion");
			tabla.getColumnModel().getColumn(8).setMaxWidth(a*8);
			tabla.getColumnModel().getColumn(8).setMinWidth(a*6-25);
			
	    	tabla.getTableHeader().setReorderingAllowed(false) ;
	    	tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			
			 JScrollPane scrol = new JScrollPane(tabla);
			   
		    return scrol; 
		}
		
	   	public void Actualizar_tabla(String status){
			Statement s;
			ResultSet rs;
			try {
				s = con.conexion().createStatement();
				rs = s.executeQuery("exec vacaciones_autorizacion '"+status+"'");
				Object [] fila = new Object[9];
				while (rs.next()) {
				   fila[0] = rs.getString(1)+"  ";
				   fila[1] = rs.getString(2)+"  ";
				   fila[2] = "   "+rs.getString(3);
				   fila[3] = "   "+rs.getString(4);
				   fila[4] = "   "+rs.getString(5);
				   fila[5] = "   "+rs.getString(6);
				   fila[6] = "   "+rs.getString(7);
				   fila[7] = "   "+rs.getString(8);
				   fila[8] = rs.getString(9);
				   
			  	   model.addRow(fila); 
				}	
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
	   	};
		
		public static void main(String args[]){
			try{
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				new Cat_Autorizacion_De_Vacaciones().setVisible(true);
			}catch(Exception e){	}
		}
		
		public class Cat_Vacaciones_Autorizados extends JFrame{
			Container contFiltro = getContentPane();
			JLayeredPane campoFiltro = new JLayeredPane();
//			Connexion con = new Connexion();
			
			DefaultTableModel modelFiltro = new DefaultTableModel(0,7){
				@SuppressWarnings({ "unchecked", "rawtypes" })
				public Class getColumnClass(int columnIndex) {
			             return listacolumnas(7)[columnIndex];
			         }   
				public boolean isCellEditable(int fila, int columna){
					if(columna < 7)
						return false;
					return true;
				}
			};

			@SuppressWarnings("rawtypes")
			public Class[] listacolumnas(int Columnas){
			Class[] lista = new Class[Columnas];
			for (int i = 0; i<lista.length; i++){
					lista[i] =(String.class);
			 }
			 return lista;
		   };
			
			JTable tablaFiltro = new JTable(modelFiltro);
			JTextField txtFiltro = new Componentes().text(new JTextField(),"Teclee Folio De Vacaciones, Folio o Nombre Del Empleado", 150, "String");
		    
		    JButton btnReporteVacaciones = new JCButton("Vacaciones", "Report.png","Azul");
		  
		    
			public Cat_Vacaciones_Autorizados()	{
				int ancho = Toolkit.getDefaultToolkit().getScreenSize().width-390;
				int alto = Toolkit.getDefaultToolkit().getScreenSize().height-60;
				
				setSize(ancho, alto);
//				setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds()); 
				
				this.setResizable(false);
				this.setLocationRelativeTo(null);
				this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/asistencia.png"));
				this.setTitle("Reportes De Vacaciones");

				campoFiltro.setBorder(BorderFactory.createTitledBorder("Reporte De Vacaciones"));
				campoFiltro.add(getPanelTablaFiltro()).setBounds(15,42,ancho-30,alto-85);
				campoFiltro.add(txtFiltro).setBounds(15,20,410,20);

				campoFiltro.add(btnReporteVacaciones).setBounds(803,20,150,20);
				
				txtFiltro.addKeyListener(opFiltro);
				
				Actualizar_tabla_filtro("A");
				cargar_render();
				
//				agregar(tabla);
				contFiltro.add(campoFiltro);
				txtFiltro.addKeyListener(opFiltro);
				btnReporteVacaciones.addActionListener(opReporteVacaciones);
			}
			
			KeyListener opFiltro = new KeyListener(){
				public void keyReleased(KeyEvent arg0) {
					int[] columnas = {0,1,2};
					new Obj_Filtro_Dinamico_Plus(tablaFiltro, txtFiltro.getText().toUpperCase().trim(), columnas);
				}
				public void keyTyped(KeyEvent arg0)   {}
				public void keyPressed(KeyEvent arg0) {}		
			};
			
			ActionListener opReporteVacaciones = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					if(tablaFiltro.getSelectedRow() >= 0){
						reporte(Integer.valueOf(tablaFiltro.getValueAt(tablaFiltro.getSelectedRow(), 0).toString().trim()), "vacaciones".toUpperCase());
					}else{
						JOptionPane.showMessageDialog(null, "Es Necesario Seleccionar Un Colaborador De La Tabla Para Revisar Sus Vacaciones", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
						return;
					}
					
				}
			};
			
	    	public void cargar_render(){
	    		tablaFiltro.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("numerico","derecha","Arial","normal",10)); 
	    		tablaFiltro.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("numerico","derecha","Arial","normal",10)); 
	    		tablaFiltro.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",10)); 
	    		tablaFiltro.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",10));
	    		tablaFiltro.getColumnModel().getColumn(4).setCellRenderer(new tablaRenderer("numerico","derecha","Arial","negrita",9));
	    		tablaFiltro.getColumnModel().getColumn(5).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",11)); 
	    		tablaFiltro.getColumnModel().getColumn(6).setCellRenderer(new tablaRenderer("fecha","izquierda","Arial","normal",11)); 
	    	}
	    	
			
		   	private JScrollPane getPanelTablaFiltro()	{		
				int a=70,b=200;
				
				tablaFiltro.getColumnModel().getColumn(0).setHeaderValue("Folio");
				tablaFiltro.getColumnModel().getColumn(0).setMaxWidth(a);
				tablaFiltro.getColumnModel().getColumn(0).setMinWidth(a);
				tablaFiltro.getColumnModel().getColumn(1).setHeaderValue("Folio Emp.");
				tablaFiltro.getColumnModel().getColumn(1).setMaxWidth(a);
				tablaFiltro.getColumnModel().getColumn(1).setMinWidth(a);
				tablaFiltro.getColumnModel().getColumn(2).setHeaderValue("Empleado");
				tablaFiltro.getColumnModel().getColumn(2).setMaxWidth(b+a);
				tablaFiltro.getColumnModel().getColumn(2).setMinWidth(b+a);
				tablaFiltro.getColumnModel().getColumn(3).setHeaderValue("Establecimiento");
				tablaFiltro.getColumnModel().getColumn(3).setMaxWidth(b-30);
				tablaFiltro.getColumnModel().getColumn(3).setMinWidth(b-30);
				tablaFiltro.getColumnModel().getColumn(4).setHeaderValue("Total a Pagar");
				tablaFiltro.getColumnModel().getColumn(4).setMaxWidth(b-30);
				tablaFiltro.getColumnModel().getColumn(4).setMinWidth(b-30);
				tablaFiltro.getColumnModel().getColumn(5).setHeaderValue("Elaboro");
				tablaFiltro.getColumnModel().getColumn(5).setMaxWidth(b+a);
				tablaFiltro.getColumnModel().getColumn(5).setMinWidth(b+a);
				tablaFiltro.getColumnModel().getColumn(6).setHeaderValue("Fecha De Elaboracion");
				tablaFiltro.getColumnModel().getColumn(6).setMaxWidth(a*2);
				tablaFiltro.getColumnModel().getColumn(6).setMinWidth(a*2);
				
		    	tablaFiltro.getTableHeader().setReorderingAllowed(false) ;
		    	tablaFiltro.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				
				 JScrollPane scrol = new JScrollPane(tablaFiltro);
				   
			    return scrol; 
			}
		   	
		   	public void Actualizar_tabla_filtro(String status){
				Statement s;
				ResultSet rs;
				try {
					s = con.conexion().createStatement();
					rs = s.executeQuery("exec vacaciones_autorizacion '"+status+"'");
					Object [] fila = new Object[7];
					while (rs.next()) {
					   fila[0] = rs.getString(1)+"  ";
					   fila[1] = rs.getString(2)+"  ";
					   fila[2] = "   "+rs.getString(3);
					   fila[3] = "   "+rs.getString(4);
					   fila[4] = "   "+rs.getString(5);
					   fila[5] = "   "+rs.getString(6);
					   fila[6] = "   "+rs.getString(7);
					   
				  	   modelFiltro.addRow(fila); 
					}	
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
		   	};
		}
	}

