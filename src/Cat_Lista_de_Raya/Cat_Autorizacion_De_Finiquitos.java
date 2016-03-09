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
import Conexiones_SQL.Generacion_Reportes;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Autorizacion_De_Finiquitos extends JFrame {
	    
		Container cont = getContentPane();
		JLayeredPane campo = new JLayeredPane();
		Connexion con = new Connexion();
		
		DefaultTableModel model = new DefaultTableModel(0,8){
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {
		             return listacolumnas(8)[columnIndex];
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
		
		JTable tabla = new JTable(model);
		@SuppressWarnings("rawtypes")
		private TableRowSorter trsfiltro;
		
		JTextField txtFolio = new Componentes().text(new JTextField(),"Teclee Folio del Empleado", 150, "Integer");
		JTextField txtEmpleado = new Componentes().text(new JTextField(),"Teclee Nombre del Empleado", 150, "String");
	    JButton btnAceptar = new JCButton("Aceptar","Aplicar.png");
	    JButton btnNegar = new JCButton("Negar","Delete.png");
	    
	    JButton btnReporte = new JCButton("Prefiniquito", "Report.png");
	    
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Cat_Autorizacion_De_Finiquitos()	{
			int ancho = Toolkit.getDefaultToolkit().getScreenSize().width;
			int alto = Toolkit.getDefaultToolkit().getScreenSize().height;
			
			setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds()); 
			
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/asistencia.png"));
			this.setTitle("Autorizacion De Finiquitos");

			campo.setBorder(BorderFactory.createTitledBorder("Autorizacion De Finiquitos"));
			trsfiltro = new TableRowSorter(model); 
			tabla.setRowSorter(trsfiltro);  
			campo.add(getPanelTabla()).setBounds(15,42,ancho-25,alto-125);
			campo.add(txtFolio).setBounds(85,20,70,20);
			campo.add(txtEmpleado).setBounds(155,20,270,20);

			campo.add(btnNegar).setBounds(475,20,100,20);
			campo.add(btnAceptar).setBounds(643,20,100,20);
			
			campo.add(btnReporte).setBounds(803,20,150,20);
			
			Actualizar_tabla();
			cargar_render();
			
//			agregar(tabla);
			cont.add(campo);
			txtFolio.addKeyListener(opFiltroFolioEmp);
			txtEmpleado.addKeyListener(opFiltroEmpleado);
			btnAceptar.addActionListener(opaceptar);
			btnNegar.addActionListener(opnegar);
			btnReporte.addActionListener(opReporte);
		}
		
		ActionListener opReporte = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(tabla.getSelectedRow() >= 0){
					reporte(Integer.valueOf(tabla.getValueAt(tabla.getSelectedRow(), 1).toString().trim()), "prefiniquito");
				}else{
					JOptionPane.showMessageDialog(null, "Es Necesario Seleccionar Un Colaborador De La Tabla Para Revisar Su Finiquito", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
					return;
				}
				
			}
		};
		
		public void reporte(int folio_empleado_scoi, String tipo_de_reporte){
			String basedatos="2.26";
			String vista_previa_reporte="no";
			int vista_previa_de_ventana=0;
			String comando="";
			String reporte = "";
			
			comando = "exec sp_select_reporte_de_finiquito "+folio_empleado_scoi+",'"+tipo_de_reporte+"'";
			
			 reporte="Obj_Finiquito.jrxml";
			 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
			 reporte="Obj_Registro_De_Finiquito.jrxml";
			 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
			 
//				if(tipo_de_reporte.equals("prefiniquito")){
//				
//			}else{
//				
//			}
		}
		
		
		ActionListener opaceptar = new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
	    		
	    		if(tabla.getSelectedRow() >= 0){
	    			Autorizar_Negar("L",Integer.valueOf(tabla.getValueAt(tabla.getSelectedRow(), 1).toString().trim()));
				}else{
					JOptionPane.showMessageDialog(null, "Es Necesario Seleccionar El EL Registro De Finiquito Que Sera Aprovado", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
					return;
				}
	    		
			}
		};
		
		ActionListener opnegar = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(tabla.getSelectedRow() >= 0){
	    			Autorizar_Negar("N", Integer.valueOf(tabla.getSelectedRows().toString().trim()));
				}else{
					JOptionPane.showMessageDialog(null, "Es Necesario Seleccionar El EL Registro De Finiquito Que Sera Rechazado", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
					return;
				}
			}
		};
		
		
		public void Autorizar_Negar(String Status_Finiquito, int folio_empleado_scoi){
			
			if(tabla.isEditing()){
				tabla.getCellEditor().stopCellEditing();
			}
			
			int folio_finiquito = Integer.valueOf(tabla.getValueAt(tabla.getSelectedRow(), 0).toString().trim());
			String observacion = tabla.getValueAt(tabla.getSelectedRow(), 7).toString().trim().toUpperCase();
			
			if(new ActualizarSQL().Modificar_Status_Revision(folio_finiquito,Status_Finiquito,observacion)){
		        	while(tabla.getRowCount()>0){	
			              model.removeRow(0);  }
		        	Actualizar_tabla();
		        	
		        	if(Status_Finiquito.equals('L')){
		        		reporte(folio_empleado_scoi,"finiquito");
		        	}else{
						JOptionPane.showMessageDialog(null, "Se Actualizo Correctamente", "Aviso", JOptionPane.OK_OPTION,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
		        	}
	        	
			}else{
				JOptionPane.showMessageDialog(null, "Error Al Actualizar", "Avise al Administrador Del Sistema", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			}
			
		};
		
		KeyListener opFiltroFolioEmp = new KeyListener(){
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
		
		KeyListener opFiltroEmpleado = new KeyListener(){
			@SuppressWarnings("unchecked")
			public void keyReleased(KeyEvent arg0) {
				trsfiltro.setRowFilter(RowFilter.regexFilter(txtEmpleado.getText().toUpperCase().trim(), 2));
			}
			public void keyTyped(KeyEvent arg0) {}
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
    		tabla.getColumnModel().getColumn(7).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",11)); 
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
			tabla.getColumnModel().getColumn(3).setMaxWidth(b-30);
			tabla.getColumnModel().getColumn(3).setMinWidth(b-30);
			tabla.getColumnModel().getColumn(4).setHeaderValue("Total a Pagar");
			tabla.getColumnModel().getColumn(4).setMaxWidth(b-30);
			tabla.getColumnModel().getColumn(4).setMinWidth(b-30);
			tabla.getColumnModel().getColumn(5).setHeaderValue("Elaboro");
			tabla.getColumnModel().getColumn(5).setMaxWidth(b+a);
			tabla.getColumnModel().getColumn(5).setMinWidth(b+a);
			tabla.getColumnModel().getColumn(6).setHeaderValue("Fecha De Elaboracion");
			tabla.getColumnModel().getColumn(6).setMaxWidth(a*2);
			tabla.getColumnModel().getColumn(6).setMinWidth(a*2);
			tabla.getColumnModel().getColumn(7).setHeaderValue("Observacion");
			tabla.getColumnModel().getColumn(7).setMaxWidth(a*8);
			tabla.getColumnModel().getColumn(7).setMinWidth(a*6-25);
			
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
				rs = s.executeQuery("exec sp_select_filtro__de_finiquito_vigentes");
				Object [] fila = new Object[8];
				while (rs.next()) {
				   fila[0] = rs.getString(1)+"  ";
				   fila[1] = rs.getString(2)+"  ";
				   fila[2] = "   "+rs.getString(3);
				   fila[3] = "   "+rs.getString(4);
				   fila[4] = "   "+rs.getString(5);
				   fila[5] = "   "+rs.getString(6);
				   fila[6] = "   "+rs.getString(7);
				   fila[7] = "";
				   
			  	   model.addRow(fila); 
				}	
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
	   	};
		
		public static void main(String args[]){
			try{
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				new Cat_Autorizacion_De_Finiquitos().setVisible(true);
			}catch(Exception e){	}
		}
	}

