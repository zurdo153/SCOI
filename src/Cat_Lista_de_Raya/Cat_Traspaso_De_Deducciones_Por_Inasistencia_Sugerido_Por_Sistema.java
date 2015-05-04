package Cat_Lista_de_Raya;

import java.awt.Component;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.AbstractButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import Conexiones_SQL.Connexion;
import Obj_Lista_de_Raya.Obj_Autorizacion_Auditoria;
import Obj_Lista_de_Raya.Obj_Autorizacion_Finanzas;
import Obj_Lista_de_Raya.Obj_Traspaso_De_Sugerido_Sistema_De_Deducciones_Por_Inasistencia;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Traspaso_De_Deducciones_Por_Inasistencia_Sugerido_Por_Sistema extends Cat_Root{
	
	Runtime R = Runtime.getRuntime();
    
	private String lista1[] = {"","1","2","3","4","5","6","7"};
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private JComboBox cmb_tabla_dias = new JComboBox(lista1);
  
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private JComboBox cmb_tabla_gafete = new JComboBox(lista1);
    	
    private DefaultTableModel modelo = new DefaultTableModel(null,new String[]{"Folio", "Nombre Completo", "Establecimiento", "Inpuntualidad", "Sug.Impuntualidad", "Sug.Omisiones", "Sug.Gafete"}
			){
	     @SuppressWarnings("rawtypes")
		Class[] types = new Class[]{
	    	java.lang.Object.class,
	    	java.lang.Object.class, 
	    	java.lang.Object.class, 
	    	java.lang.Object.class, 
	    	java.lang.Object.class, 
	    	java.lang.Object.class,
	    	java.lang.Object.class,

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
        	 	case 5 : return true; 
        	 	case 6 : return true; 
        	 		}
			return false;
         }	 
     };

	public JTable tabla = new JTable(modelo);
    JScrollPane scroll_tabla = new JScrollPane(tabla,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public TableRowSorter trsfiltro = new TableRowSorter(modelo); 
    
	
//	public TableColumn columna_dia_falta = tabla.getColumnModel().getColumn(5);
//	public TableColumn columna_dia_gafete = tabla.getColumnModel().getColumn(9);
    
	public Cat_Traspaso_De_Deducciones_Por_Inasistencia_Sugerido_Por_Sistema(){
		int ancho = Toolkit.getDefaultToolkit().getScreenSize().width;
		int alto = Toolkit.getDefaultToolkit().getScreenSize().height;
		alto=alto-50;
		
		this.setSize(1100, alto);
		this.setLocationRelativeTo(null);
//		this.addWindowListener(op_cerrar);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/actualizacion-del-sistema-icono-5792-48.png"));
		this.setTitle("Traspaso De Deducción por Inasistencia Sugerido Por Sistema");
		this.panel.add(cmbEstablecimientos).setBounds(463,35,150,20);

//		this.columna_dia_falta.setCellEditor(new javax.swing.DefaultCellEditor(cmb_tabla_dias));
//		this.columna_dia_gafete.setCellEditor(new javax.swing.DefaultCellEditor(cmb_tabla_gafete));

		panel.add(obtener_tabla()).setBounds(30,60,1024,alto-120);
		llenar_tabla__sugerido ();
		pitar_tabla();
		

		this.cont.add(panel);
		this.btn_guardar.addActionListener(op_guardar);
			this.btn_guardar.setToolTipText("Guardar");
		this.btn_refrescar.setVisible(false);
		
		this.txtFolio.addKeyListener(op_filtro_folio);
		this.txtNombre_Completo.addKeyListener(op_filtro_nombre);
		this.cmbEstablecimientos.addActionListener(op_filtro_establecimiento);



	}

//	WindowListener op_cerrar = new WindowListener() {
//		public void windowOpened(WindowEvent e) {}
//		public void windowIconified(WindowEvent e) {}
//		public void windowDeiconified(WindowEvent e) {}
//		public void windowDeactivated(WindowEvent e) {}
//		public void windowClosing(WindowEvent e) {
//			if(JOptionPane.showConfirmDialog(null, "¿Desea guardar antes de cerrar?", "Aviso!", JOptionPane.YES_NO_OPTION) == 0){
//				new Obj_Deducciones_Por_Inasistencia().guardar(tabla_guardar());
//			}
//		}
//		public void windowClosed(WindowEvent e) {}
//		public void windowActivated(WindowEvent e) {}
//	};

	ActionListener op_guardar = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			Obj_Autorizacion_Auditoria auditoria = new Obj_Autorizacion_Auditoria().buscar();
			Obj_Autorizacion_Finanzas finanzas = new Obj_Autorizacion_Finanzas().buscar();
			
			boolean auditoriaBoolean = auditoria.isAutorizar();
			boolean finanzasBoolean = finanzas.isAutorizar();
			
			if((auditoriaBoolean == true)  || (finanzasBoolean == true)){
				
				JOptionPane.showMessageDialog(null, "La Lista De Raya Fue Autorizada No Puede Ser Modificada Ninguna Deduccion Por Inasistencia......"
				       +" Hasta Que Se Genere Por D.H o Se Desautorize por Finanzas o Auditoria <<Al dar Click en Aceptar SCOI se Cerrará>>","Aviso",JOptionPane.WARNING_MESSAGE);
				
				try {	R.exec("taskkill /f /im javaw.exe"); } catch (IOException e1) {	e1.printStackTrace(); }		
				
			}
//			else{
//			trsfiltro.setRowFilter(RowFilter.regexFilter("", 0));
//			trsfiltro.setRowFilter(RowFilter.regexFilter("", 1));
//			trsfiltro.setRowFilter(RowFilter.regexFilter("", 2));
//
//			txtFolio.setText("");
//			txtNombre_Completo.setText("");
//			cmbEstablecimientos.setSelectedIndex(0);
//
//			if(tabla.isEditing()){
//				tabla.getCellEditor().stopCellEditing();
//			}
//
//			if(valida_tabla() != ""){
//				JOptionPane.showMessageDialog(null, "Las siguientes celdas están mal en su formato:\n"+valida_tabla(),"Error",JOptionPane.ERROR_MESSAGE);
//				return;
//			}else{
//				if(JOptionPane.showConfirmDialog(null, "¿Desea guardar la lista de deducción por inasistencia?") == 0){
//					Obj_Deducciones_Por_Inasistencia inasistencia = new Obj_Deducciones_Por_Inasistencia();
//					if(inasistencia.guardar(tabla_guardar())){
//						JOptionPane.showMessageDialog(null, "La tabla Deducción por Inasistencia se guardó exitosamente","Aviso",JOptionPane.INFORMATION_MESSAGE);
//						return;
//					}else{
//						JOptionPane.showMessageDialog(null, "Ocurrió un error al intentar guardar la tabla","Error",JOptionPane.ERROR_MESSAGE);
//						return;
//					}
//				}else{
//					return;
//				}
//			}
//		}
		}
	};

//	private Object[][] tabla_guardar(){
//		Object[][] matriz = new Object[tabla.getRowCount()][11];
//		for(int i=0; i<tabla.getRowCount(); i++){
//			for(int j=0; j<tabla.getColumnCount(); j++){
//				switch(j){
//					case 0: 
//						matriz[i][j] = Integer.parseInt(tabla_model.getValueAt(i,j).toString().trim());
//						break;
//					case 1: 
//						matriz[i][j] = tabla_model.getValueAt(i,j).toString().trim();
//						break;
//					case 2: 
//						matriz[i][j] = tabla_model.getValueAt(i,j).toString().trim();
//						break;
//					case 3: 
//						if(tabla_model.getValueAt(i,j).toString().equals("")){
//							matriz[i][j] = Boolean.parseBoolean("false");
//						}else{
//							matriz[i][j] = Boolean.parseBoolean(tabla_model.getValueAt(i,j).toString());
//						}
//						break;
//					case 4: 
//						if(tabla_model.getValueAt(i,j).toString().equals("")){
//							matriz[i][j] = Boolean.parseBoolean("false");
//						}else{
//							matriz[i][j] = Boolean.parseBoolean(tabla_model.getValueAt(i,j).toString());
//						}
//						break;
//					case 5: 
//						if(tabla_model.getValueAt(i,j).toString().trim().length() == 0){
//							matriz[i][j] = Integer.parseInt("0"); 
//						}else{
//							matriz[i][j] = Integer.parseInt(tabla_model.getValueAt(i,j).toString().trim());
//						}
//						break;
//					case 6: 
//						if(tabla_model.getValueAt(i,j).toString().equals("")){
//							matriz[i][j] = Boolean.parseBoolean("false");
//						}else{
//							matriz[i][j] = Boolean.parseBoolean(tabla_model.getValueAt(i,j).toString());
//						}
//						break;
//					case 8: 
//						if(tabla_model.getValueAt(i,j).toString().equals("")){
//							matriz[i][j] = Boolean.parseBoolean("false");
//						}else{
//							matriz[i][j] = Boolean.parseBoolean(tabla_model.getValueAt(i,j).toString());
//						}
//						break;
//					case 9: 
//						if(tabla_model.getValueAt(i,j).toString().trim().length() == 0){
//							matriz[i][j] = Integer.parseInt("0"); 
//						}else{
//							matriz[i][j] = Integer.parseInt(tabla_model.getValueAt(i,j).toString().trim());
//						}
//						break;
//					case 10: 
//						if(tabla_model.getValueAt(i,j).toString().trim().length() == 0){
//							matriz[i][j] = Float.parseFloat("0"); 
//						}else{
//							matriz[i][j] = Float.parseFloat(tabla_model.getValueAt(i,j).toString().trim());
//						}
//						break;
//
//				}
//			}
//		}
//		return matriz;
//	}

	private String valida_tabla(){
		String error = "";
		for(int i=0; i<tabla.getRowCount(); i++){
			try{
				if(!isNumeric(tabla.getValueAt(i,5).toString())){
					error += "   La celda de la columna Extra no es un número en el [Folio: "+tabla.getValueAt(i,0)+"]\t\n";
				}
			} catch(Exception e){
				JOptionPane.showMessageDialog(null, "La tabla tiene una celda con texto en lugar de un valor numérico: \n"+e,"Error",JOptionPane.ERROR_MESSAGE);
			}
		}
		return error;
	}

	public JScrollPane obtener_tabla(){
		this.tabla.getTableHeader().setReorderingAllowed(false) ;
		this.tabla.getColumnModel().getColumn(0).setMaxWidth(72);
		this.tabla.getColumnModel().getColumn(0).setMinWidth(72);
		this.tabla.getColumnModel().getColumn(1).setMaxWidth(360);
		this.tabla.getColumnModel().getColumn(1).setMinWidth(360);
		this.tabla.getColumnModel().getColumn(2).setMaxWidth(150);
		this.tabla.getColumnModel().getColumn(2).setMinWidth(150);
		this.tabla.getColumnModel().getColumn(3).setMaxWidth(100);
		this.tabla.getColumnModel().getColumn(3).setMinWidth(100);
		this.tabla.getColumnModel().getColumn(4).setMaxWidth(100);
		this.tabla.getColumnModel().getColumn(4).setMinWidth(100);
		this.tabla.getColumnModel().getColumn(5).setMaxWidth(100);
		this.tabla.getColumnModel().getColumn(5).setMinWidth(100);
		this.tabla.getColumnModel().getColumn(6).setMaxWidth(100);
		this.tabla.getColumnModel().getColumn(6).setMinWidth(100);
		JScrollPane scrol = new JScrollPane(tabla);
		    return scrol; 
	}
	
	
	public void llenar_tabla__sugerido (){
		Obj_Traspaso_De_Sugerido_Sistema_De_Deducciones_Por_Inasistencia  datos_sugerido_inasistencia = new Obj_Traspaso_De_Sugerido_Sistema_De_Deducciones_Por_Inasistencia();
		
		try {
		String[][] matriz = datos_sugerido_inasistencia.buscar_datos_sugerido_inasistencia();
//		while(tabla.getRowCount()>0){modelo.removeRow(0);}
		
        String[] fila = new String[7];
		for(int i=0; i<matriz.length; i++){
			for(int j=0; j<7; j++){
				fila[j] = matriz[i][j ]+"";
			}
			modelo.addRow(fila);
		}
		} catch (SQLException e2) {
			e2.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion llenar_tabla_deduccion_inasistencia_sugerido_sistema \nprocedimiento almacenado sp_buscar_sugerido_sistemas_inasistencia \n SQL Server Exception: "+e2.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
	};
	
	public void pitar_tabla(){
		//		tabla.getColumnModel().getColumn(# columna).setCellRenderer(new CellRenderer("tipo_de_valor","alineacion","tipo_de_letra","negrita",# tamanio_fuente));
			tabla.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","derecha","Arial","negrita",12)); 
			tabla.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
			tabla.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
			tabla.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12));
			tabla.getColumnModel().getColumn(4).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12));
			tabla.getColumnModel().getColumn(5).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12));
			tabla.getColumnModel().getColumn(6).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12));
	}
	


	private static boolean isNumeric(String cadena){
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

	
	
	KeyListener op_filtro_folio = new KeyListener(){
		@SuppressWarnings("unchecked")
		public void keyReleased(KeyEvent arg0) {
			trsfiltro.setRowFilter(RowFilter.regexFilter(txtFolio.getText(), 0));
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

	KeyListener op_filtro_nombre = new KeyListener(){
		@SuppressWarnings("unchecked")
		public void keyReleased(KeyEvent arg0) {
			trsfiltro.setRowFilter(RowFilter.regexFilter(txtNombre_Completo.getText().toUpperCase().trim(), 1));
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}		
	};

	ActionListener op_filtro_establecimiento = new ActionListener(){
		@SuppressWarnings("unchecked")
		public void actionPerformed(ActionEvent arg0){
			if(cmbEstablecimientos.getSelectedIndex() != 0){
				trsfiltro.setRowFilter(RowFilter.regexFilter(cmbEstablecimientos.getSelectedItem()+"", 2));
			}else{
				trsfiltro.setRowFilter(RowFilter.regexFilter("", 2));
			}
		}
	};
	
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new  Cat_Traspaso_De_Deducciones_Por_Inasistencia_Sugerido_Por_Sistema().setVisible(true);
		}catch(Exception e){	}
	}
}