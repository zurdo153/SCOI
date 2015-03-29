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
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

@SuppressWarnings("serial")
public class Cat_Documentacion_De_Empleado extends JDialog{
		
		Container contenedor = getContentPane();
		JLayeredPane panelxml = new JLayeredPane();
		
		ImageIcon img = new ImageIcon("imagen/Nuevo.png");
		JButton btnTabla = new JButton("asd");
		
		JButton btnSolicitud = new JButton("Solicitud",new ImageIcon("imagen/Nuevo.png"));
		JButton btnActaNacimiento = new JButton("Acta de nacimiento",new ImageIcon("imagen/Nuevo.png"));
		JButton btnCurp = new JButton("Curp",new ImageIcon("imagen/Nuevo.png"));
		
		JButton btnSeguro = new JButton("Seguro Social",new ImageIcon("imagen/Nuevo.png"));
		JButton btnInfonavit = new JButton("Infonavit",new ImageIcon("imagen/Nuevo.png"));
		JButton btnPencionAlim = new JButton("Pencion alimenticia",new ImageIcon("imagen/Nuevo.png"));
		
		JButton btnIFE = new JButton("IFE",new ImageIcon("imagen/Nuevo.png"));
		JButton btnComprobanteDom = new JButton("Comprobante de domicilio",new ImageIcon("imagen/Nuevo.png"));
		
		JTextField txtSolicitud = new JTextField();
		JTextField txtActaNacimiento = new JTextField();
		JTextField txtCurp = new JTextField();
		
		JTextField txtSeguro = new JTextField();
		JTextField txtInfonavit = new JTextField();
		JTextField txtPencionAlim = new JTextField();
		
		JTextField txtIFE = new JTextField();
		JTextField txtComprobanteDom = new JTextField();
		
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
		
		String xml_pdf = "";
		
		public Cat_Documentacion_De_Empleado(){
			this.setModal(true);
			this.setTitle("Documentacion de empleados");
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/control_facturas_y_xml.png"));
			blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
			panelxml.setBorder(BorderFactory.createTitledBorder(blackline,"Seleccionar documentos de empleados"));
			
			int y = 20; int ancho = 220;
			
//			panelxml.add(btnSolicitud).setBounds(10,y    ,ancho,20);
//			panelxml.add(btnActaNacimiento).setBounds(10,y+=20,ancho,20);
//			panelxml.add(btnCurp).setBounds(10,y+=20,ancho,20);
//			panelxml.add(btnSeguro).setBounds(10,y+=20,ancho,20);
//			panelxml.add(btnInfonavit).setBounds(10,y+=20,ancho,20);
//			panelxml.add(btnPencionAlim).setBounds(10,y+=20,ancho,20);
//			panelxml.add(btnIFE).setBounds(10,y+=20,ancho,20);
//			panelxml.add(btnComprobanteDom).setBounds(10,y+=20,ancho,20);
			
//			y = 20;
			
			panelxml.add(tabla).setBounds(7,y    ,ancho*2+140,160);
			
			tabla.setRowHeight(20);
//			panelxml.add(txtSolicitud).setBounds(ancho+10,y    ,ancho+130,20);
//			panelxml.add(txtActaNacimiento).setBounds(ancho+10,y+=25,ancho+130,20);
//			panelxml.add(txtCurp).setBounds(ancho+10,y+=25,ancho+130,20);
//			panelxml.add(txtSeguro).setBounds(ancho+10,y+=25,ancho+130,20);
//			panelxml.add(txtInfonavit).setBounds(ancho+10,y+=25,ancho+130,20);
//			panelxml.add(txtPencionAlim).setBounds(ancho+10,y+=25,ancho+130,20);
//			panelxml.add(txtIFE).setBounds(ancho+10,y+=25,ancho+130,20);
//			panelxml.add(txtComprobanteDom).setBounds(ancho+10,y+=25,ancho+130,20);
			
//        	AGREGAR BOTON ALA TABLA 
        	tabla.getColumn("").setCellRenderer(new ButtonRenderer());
    		tabla.getColumn("").setCellEditor(new ButtonEditor(new JCheckBox()));
    		
//    		ACCION DEL BOTON DE LA table
    		btnTabla.addActionListener(opExaminarXML_PDF);
//    		btnTabla.setBackground(Color.BLUE);
			
//    		JButton btnSolicitud = new JButton("Solicitud",new ImageIcon("imagen/Nuevo.png"));
//    		JButton btnActaNacimiento = new JButton("Acta de nacimiento",new ImageIcon("imagen/Nuevo.png"));
//    		JButton btnCurp = new JButton("Curp",new ImageIcon("imagen/Nuevo.png"));
//    		
//    		JButton btnSeguro = new JButton("Seguro Social",new ImageIcon("imagen/Nuevo.png"));
//    		JButton btnInfonavit = new JButton("Infonavit",new ImageIcon("imagen/Nuevo.png"));
//    		JButton btnPencionAlim = new JButton("Pencion alimenticia",new ImageIcon("imagen/Nuevo.png"));
//    		
//    		JButton btnIFE = new JButton("IFE",new ImageIcon("imagen/Nuevo.png"));
//    		JButton btnComprobanteDom = new JButton("Comprobante de domicilio",new ImageIcon("imagen/Nuevo.png"));
    		
			 botones();
			
			panelxml.add(btnGuardarFacXml).setBounds(380,190,180,20);
			
	        this.contenedor.add(panelxml);
	        
//	        btnActaNacimiento.addActionListener(opExaminarXML_PDF);
//	        btnCurp.addActionListener(opExaminarXML_PDF);
//	        btnSeguro.addActionListener(opExaminarXML_PDF);
//	        btnInfonavit.addActionListener(opExaminarXML_PDF);
//	        btnPencionAlim.addActionListener(opExaminarXML_PDF);
//	        btnIFE.addActionListener(opExaminarXML_PDF);
//	        btnComprobanteDom.addActionListener(opExaminarXML_PDF);
	        
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
//    			System.out.println(table.getValueAt(row, 0));
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
					
					case 3:	vector [0]="Hoja Seguro Social";
							vector [1]="";
							tabla_model.addRow(vector);
					break;
					
					case 4:	vector [0]="Hoja De Retención Infonavit";
							vector [1]="";
							tabla_model.addRow(vector);
					break;
					
					case 5:	vector [0]="Hoja De Retención de Pensión Alimenticia";
							vector [1]="";
							tabla_model.addRow(vector);
					break;
					
					case 6:	vector [0]="Credencial De Identificacion";
							vector [1]="";
							tabla_model.addRow(vector);
					break;
					
					case 7:	vector [0]="Comprobante de Domicilio";
							vector [1]="";
							tabla_model.addRow(vector);
					break;
				}
			}
    	}
		
//	ActionListener opGauardarXMLpdf = new ActionListener(){
//			public void actionPerformed(ActionEvent e) {
//				
//			if(JOptionPane.showConfirmDialog(null,"De El Proveedor:"+proveedor_recibido+" La Factura:"+cod_factura_recibido+" \n Va Hacer Marcada Como Recibida: Confirmar?") == 0){
//				
////				boolean proveedorr =
//						new Obj_Proveedores().marcar_recibido_factura(cod_prvrecibido.trim(), cod_factura_recibido.trim(),cmbTipo.getSelectedItem().toString(),new File(xml_pdf));
//				
//				while(tabla.getRowCount()>0){
//						modelo.removeRow(0);  }
//			    Object [][] lista_proveedores = new BuscarTablasModel().tabla_model_proveedores_guardados();;
//			    String[] fila = new String[9];
//			            for(int i=0; i<lista_proveedores.length; i++){
//			                    fila[0] = lista_proveedores[i][0]+"";
//			                    fila[1] = lista_proveedores[i][1]+"";
//			                    fila[2] = lista_proveedores[i][2]+"";
//			                    fila[3] = lista_proveedores[i][3]+"";
//			                    fila[4] = lista_proveedores[i][4]+"";
//			                    fila[5] = lista_proveedores[i][5]+"";
//			                    fila[6] = lista_proveedores[i][6]+"";
//                 			   	fila[7] = lista_proveedores[i][7]+"";
//			                    fila[8] = "false";
//			                    modelo.addRow(fila);
//			              }
//			            
//	            xml_pdf ="";
//				JOptionPane.showMessageDialog(null,"El registró se actualizó de forma segura","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//Exito.png"));
//				dispose();
//			}else{
//				JOptionPane.showMessageDialog(null,"Se Cancelo La Marcacion De La Factura","Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
//				return;
//			}
//		}
//	};
		
		ActionListener opExaminarXML_PDF = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				
		                	JFileChooser elegir = new JFileChooser();
		                	int opcion = elegir.showOpenDialog(btnTabla);
		                	
		                	System.out.println(opcion);
		            
			                 //Si presionamos el boton ABRIR en pathArchivo obtenemos el path del archivo
			                 if (opcion == JFileChooser.APPROVE_OPTION) {
			                 	
			                     String pathArchivo = elegir.getSelectedFile().getPath(); //Obtiene path del archivo
			                     String nombre = elegir.getSelectedFile().getName(); //obtiene nombre del archivo
			 				    	
			                         if(pathArchivo.toUpperCase().substring(pathArchivo.length()-3, pathArchivo.length()).equals("PDF")){
			                        	 System.out.println(pathArchivo);
			                        	 System.out.println(nombre);
			                         	xml_pdf = pathArchivo;
			                         	txtSolicitud.setText(nombre);
			                         }else{
			                         	xml_pdf = "";
			                         	txtSolicitud.setText("");
			                         	JOptionPane.showMessageDialog(null,"El archivo seleccionado es de tipo ("+pathArchivo.substring(pathArchivo.length()-3, pathArchivo.length()).trim()+") \nCuando se requiere uno de tipo (PDF)","Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
			             				return;
			                         }  
			                 }
			}
		};
		
		public static void main(String [] arg){
			new Cat_Documentacion_De_Empleado().setVisible(true);
		}
		
	}
