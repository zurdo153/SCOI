package Cat_Lista_de_Raya;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import Obj_Lista_de_Raya.Obj_Autorizacion_Auditoria;
import Obj_Lista_de_Raya.Obj_Autorizacion_Finanzas;
import Obj_Lista_de_Raya.Obj_Deducciones_Y_Percepciones_De_Lista_De_Raya;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Lista_de_Raya.Obj_Traspaso_De_Sugerido_Sistema_De_Deducciones_Por_Inasistencia;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")

public class Cat_Traspaso_De_Deducciones_Por_Inasistencia_Sugerido_Por_Sistema extends JFrame implements TableModelListener{
	public Container cont = getContentPane();
	
	public JLayeredPane panel = new JLayeredPane();
	
	public JToolBar menu_toolbar = new JToolBar();
	
	public JTextField txtFolio;
	public JTextField txtNombre_Completo;
	
	public String establecimientos[] = new Obj_Establecimiento().Combo_Establecimiento();
	@SuppressWarnings("rawtypes")
	public JComboBox cmbEstablecimientos ;
	
	public JButton btn_guardar = new JButton("Guardar",new ImageIcon("Iconos/save_icon&16.png"));
	public JButton btn_refrescar = new JButton(new ImageIcon("Iconos/refresh_icon&16.png"));
	
	Runtime R = Runtime.getRuntime();
    
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
    
	
	@SuppressWarnings("unused")
	public void tableChanged(TableModelEvent e) {
        int row = e.getFirstRow();
        int column = e.getColumn();
        TableModel model = (TableModel)e.getSource();
        String columnName = model.getColumnName(column);
        String data = model.getValueAt(row, column).toString();

//        realizar validaciones o cualquier otro movimiento
//        System.out.print(columnName+"    ");
//        System.out.println(data);
        if(column>3){
        	 try{
 	        	if(!data.equals("")){
 	        		Integer.valueOf(data);
 	        	}
 	        } catch (NumberFormatException nfe){
 	        	tabla.setValueAt("", row, column);
 	        	System.out.println("no es entero");
 	        }
        }
       
    }
    
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Cat_Traspaso_De_Deducciones_Por_Inasistencia_Sugerido_Por_Sistema(){
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		
//		int ancho = Toolkit.getDefaultToolkit().getScreenSize().width;
		int alto = Toolkit.getDefaultToolkit().getScreenSize().height;
		alto=alto-50;
		this.setSize(1100, alto);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		cmbEstablecimientos = new JComboBox(establecimientos);
		txtFolio = new JTextField();
		txtNombre_Completo = new JTextField();
		
		
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/actualizacion-del-sistema-icono-5792-48.png"));
		this.setTitle("Traspaso De Deducción por Inasistencia Sugerido Por Sistema");
		this.panel.add(menu_toolbar).setBounds(25,0,150,25);
		
		this.panel.add(txtFolio).setBounds(30,30,75,25);
		this.panel.add(txtNombre_Completo).setBounds(101,30,359,25);
		this.panel.add(cmbEstablecimientos).setBounds(463,30,300,25);
		
		panel.add(obtener_tabla()).setBounds(30,60,1024,alto-120);
		llenar_tabla__sugerido ();
		pitar_tabla();
		
		tabla.getModel().addTableModelListener(this);

		this.cont.add(panel);
		this.btn_guardar.addActionListener(op_guardar);
		this.btn_guardar.setToolTipText("Guardar");
		this.btn_refrescar.setVisible(false);
		
		this.menu_toolbar.add(btn_guardar);
		this.menu_toolbar.setEnabled(false);
		this.btn_guardar.setToolTipText("Guardar");
		this.txtFolio.addKeyListener(op_filtro_folio);
		this.txtNombre_Completo.addKeyListener(op_filtro_nombre);
		this.cmbEstablecimientos.addActionListener(op_filtro_establecimiento);


		this.addWindowListener(op_cerrar);
		
	}

	WindowListener op_cerrar = new WindowListener() {
		public void windowOpened(WindowEvent e) {}
		public void windowIconified(WindowEvent e) {}
		public void windowDeiconified(WindowEvent e) {}
		public void windowDeactivated(WindowEvent e) {}
		public void windowClosing(WindowEvent e) {
				try {
					UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InstantiationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (UnsupportedLookAndFeelException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		public void windowClosed(WindowEvent e) {}
		public void windowActivated(WindowEvent e) {}
	};

	ActionListener op_guardar = new ActionListener() {
		@SuppressWarnings("unchecked")
		public void actionPerformed(ActionEvent arg0) {
			Obj_Autorizacion_Auditoria auditoria = new Obj_Autorizacion_Auditoria().buscar();
			Obj_Autorizacion_Finanzas finanzas = new Obj_Autorizacion_Finanzas().buscar();
			
			boolean auditoriaBoolean = auditoria.isAutorizar();
			boolean finanzasBoolean = finanzas.isAutorizar();
			
				if((auditoriaBoolean == true)  || (finanzasBoolean == true)){
						
						JOptionPane.showMessageDialog(null, "La Lista De Raya Fue Autorizada No Puede Ser Modificada Ninguna Deduccion o Percepcion de Lista de Raya....."
						       +" \n Hasta Que Se Genere Por D.H o Se Desautorize por Finanzas o Auditoria <<>>","Aviso",JOptionPane.WARNING_MESSAGE);
				}else{
						trsfiltro.setRowFilter(RowFilter.regexFilter("", 0));
						trsfiltro.setRowFilter(RowFilter.regexFilter("", 1));
						trsfiltro.setRowFilter(RowFilter.regexFilter("", 2));
			
						txtFolio.setText("");
						txtNombre_Completo.setText("");
						cmbEstablecimientos.setSelectedIndex(0);
			
						if(tabla.isEditing()){
							tabla.getCellEditor().stopCellEditing();
						}
			
							if(JOptionPane.showConfirmDialog(null, "¿Desea guardar la lista de traspaso por deducciones sugerido?") == 0){
								
								Obj_Deducciones_Y_Percepciones_De_Lista_De_Raya inasistencia = new Obj_Deducciones_Y_Percepciones_De_Lista_De_Raya();
								
									if(inasistencia.guardar_traspaso_de_deduccion_sugerido(tabla_guardar())){
										JOptionPane.showMessageDialog(null, "El traspaso por deducciones sugerido se guardó exitosamente","Aviso",JOptionPane.INFORMATION_MESSAGE);
										return;
									}else{
										JOptionPane.showMessageDialog(null, "Ocurrió un error al intentar guardar la tabla","Error",JOptionPane.ERROR_MESSAGE);
										return;
									}
							}else{
								return;
							}
				}
		}
	};
	
	private Object[][] tabla_guardar(){
		Object[][] matriz = new Object[tabla.getRowCount()][tabla.getColumnCount()];
		for(int i=0; i<tabla.getRowCount(); i++){
			for(int j=0; j<tabla.getColumnCount(); j++){
								matriz[i][j] = modelo.getValueAt(i,j).toString().trim().equals("")?0:modelo.getValueAt(i,j).toString().trim();
			}
		}
		return matriz;
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