package Cat_Lista_de_Raya;

import java.awt.Component;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import Conexiones_SQL.GuardarSQL;

@SuppressWarnings("serial")
public class Cat_Documentacion_De_Empleado extends JDialog{
		
		Container contenedor = getContentPane();
		JLayeredPane panelxml = new JLayeredPane();
		
		ImageIcon img = new ImageIcon("imagen/Nuevo.png");
		JButton btnTabla = new JButton("asd");
		
//		JTextField txtSolicitud = new JTextField();
//		JTextField txtActaNacimiento = new JTextField();
//		JTextField txtCurp = new JTextField();
//		
//		JTextField txtSeguro = new JTextField();
//		JTextField txtInfonavit = new JTextField();
//		JTextField txtPencionAlim = new JTextField();
//		
//		JTextField txtIFE = new JTextField();
//		JTextField txtComprobanteDom = new JTextField();
		
        DefaultTableModel tabla_model = new DefaultTableModel(null,
                new String[]{ "","Ruta(s)"}
                            ){
                 @SuppressWarnings("rawtypes")
                    Class[] types = new Class[]{
                        javax.swing.JButton.class,
                        java.lang.String.class
             };
                 
             @SuppressWarnings({ "rawtypes", "unchecked" })
            public Class getColumnClass(int columnIndex) {
                 return types[columnIndex];
            }
                 
            public boolean isCellEditable(int fila, int columna){
                 switch(columna){
                         case 0 : return true;
                         case 1 : return false; 
                 }                                 
             return false;
            }
        };
		JTable tabla = new JTable(tabla_model);
		
		JButton btnGuardarFacXml = new JButton("Guardar Archivo",new ImageIcon("imagen/Aplicar.png"));
		
		Border blackline, etched, raisedbevel, loweredbevel, empty;
		
//		String archivo = "";
		
		public Cat_Documentacion_De_Empleado(){
			this.setModal(true);
			this.setTitle("Documentacion de empleados");
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/control_facturas_y_xml.png"));
			blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
			panelxml.setBorder(BorderFactory.createTitledBorder(blackline,"Seleccionar Documentos De Empleado"));
			
			int y = 20; int ancho = 220;
			
			panelxml.add(tabla).setBounds(7,y    ,ancho*2+140,160);
			panelxml.add(btnGuardarFacXml).setBounds(380,190,180,20);
			
			tabla.setRowHeight(20);
			botones();
			
//        	AGREGAR BOTON ALA TABLA 
        	tabla.getColumn("").setCellRenderer(new ButtonRenderer());
    		tabla.getColumn("").setCellEditor(new ButtonEditor(new JCheckBox()));
    		
//    		ACCION DEL BOTON DE LA table
    		btnTabla.addActionListener(opExaminarXML_PDF);
			btnGuardarFacXml.addActionListener(opGauardarXMLpdf);
			
	        this.contenedor.add(panelxml);
	        
			this.setSize(600,260);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		}
		
    	class ButtonRenderer extends JButton implements TableCellRenderer {
    		public ButtonRenderer() {
    			setOpaque(true);
    		}
    		
    		public Component getTableCellRendererComponent(JTable table, Object value,
    		boolean isSelected, boolean hasFocus, int row, int column) {
    			setText((value == null) ? "" : value.toString());
    			return this;
    		}
    	}
    		
    	class ButtonEditor extends DefaultCellEditor {
    		private String label;
    		public ButtonEditor(JCheckBox checkBox) {
    			super(checkBox);
    		}
    		
    		public Component getTableCellEditorComponent(JTable table, Object value,
    		boolean isSelected, int row, int column) {
    			label = table.getValueAt(row, 0).toString();
    			
    			btnTabla.setText(label);
    			return btnTabla;
    		}
    		
    		public Object getCellEditorValue() {
    			return new String(label);
    		}
    	}
    	
    	public void botones(){
    		Object[] vector = new Object[2];
    		
			for(int i = 0; i<8; i++){
				switch(i){
					case 0:	vector [0]="Solicitud";
							vector [1]="";
							tabla_model.addRow(vector);
					break;
					case 1:	vector [0]="Acta De Nacimiento";
							vector [1]="";
							tabla_model.addRow(vector);
					break;
					case 2:	vector [0]="Curp";
							vector [1]="";
							tabla_model.addRow(vector);
					break;
					case 3:	vector [0]="Hoja De Seguro Social";
							vector [1]="";
							tabla_model.addRow(vector);
					break;
					case 4:	vector [0]="Hoja De Retencion Infonavit";
							vector [1]="";
							tabla_model.addRow(vector);
					break;
					case 5:	vector [0]="Hoja De Retencion De Pension Alimenticia";
							vector [1]="";
							tabla_model.addRow(vector);
					break;
					case 6:	vector [0]="Credencial De Identificacion";
							vector [1]="";
							tabla_model.addRow(vector);
					break;
					case 7:	vector [0]="Comprobante De Domicilio";
							vector [1]="";
							tabla_model.addRow(vector);
					break;
				}
			}
    	}
		
	ActionListener opGauardarXMLpdf = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				
				String[][] documentos = new String[8][2];
				for(int i =0; i<tabla.getRowCount(); i++){
						documentos[i][0]=tabla.getValueAt(i, 0).toString();
						documentos[i][1]=tabla.getValueAt(i, 1).toString();
				}
				
				if(new GuardarSQL().Archivar_Documentos_De_Empleados(491,documentos)){
					JOptionPane.showMessageDialog(null,"Los Archivos Fueron Guardado Exitosamente","Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
     				return;
				}else{
					JOptionPane.showMessageDialog(null,"Los Archivos No Pudieron Guardarse,\nFavor De Avisar Al Administrador Del Sistema","Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
     				return;
				}
		}
	};
	
	ActionListener opExaminarXML_PDF = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
				
			int filaSeleccionada = tabla.getSelectedRow();
			
            	JFileChooser elegir = new JFileChooser();
            	
//            	filtro de extenciones
            	FileNameExtensionFilter filter = new FileNameExtensionFilter("Solo PDF", "pdf");
    			elegir.setFileFilter(filter);
		                	
//				Cambiar al directorio Windows
//            	elegir.setCurrentDirectory(new File("c:/SCOI/ArchivosPDF"));
		                	
            	int opcion = elegir.showOpenDialog(btnTabla);
		                	
                 //Si presionamos el boton ABRIR en pathArchivo obtenemos el path del archivo
                 if(opcion == JFileChooser.APPROVE_OPTION){
                 	
                     String pathArchivo = elegir.getSelectedFile().getPath(); //Obtiene path del archivo
//                     String nombre = elegir.getSelectedFile().getName(); //obtiene nombre del archivo
 				    	
                         if(pathArchivo.toUpperCase().substring(pathArchivo.length()-3, pathArchivo.length()).equals("PDF")){
                        	 	tabla.setValueAt(pathArchivo, filaSeleccionada, 1);
                         }else{
	                         	JOptionPane.showMessageDialog(null,"El archivo seleccionado es de tipo ("+pathArchivo.substring(pathArchivo.length()-3, pathArchivo.length()).trim()+") \nCuando se requiere uno de tipo (PDF)","Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
	             				return;
                         }  
                 }
		}
	};
		
		public static void main(String [] arg){
			try{
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				new Cat_Documentacion_De_Empleado().setVisible(true);
			}catch(Exception e){	}
		}
	}
