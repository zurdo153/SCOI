package Cat_Lista_de_Raya;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Obj_Lista_de_Raya.Obj_Captura_Fuente_Sodas;
import Obj_Lista_de_Raya.Obj_Filtro_Ticket_Fuente_Sodas;
import Obj_Renders.tablaRenderer;

//FILTRO DE TICKETS QUE NO SE LE AN DESCONTADO AL EMPLEADO SELECCIONADO	
	 	@SuppressWarnings("serial")
		public class Cat_Filtro_Ticket_Fuente_Sodas_AUXF extends JDialog {
			
			Container cont = getContentPane();
			JLayeredPane campo = new JLayeredPane();
			
			int folio_empleado=0;
			
			DefaultTableModel modeloFiltro = new DefaultTableModel(null,
		            new String[]{"Ticket", "Importe","Fecha",""}
					){
			     @SuppressWarnings("rawtypes")
				Class[] types = new Class[]{
			    	java.lang.Integer.class,
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
		        	 	case 3 : return true;
		        	 		
		        	 } 				
		 			return false;
		 		}
			};
			
			JTable tablaFiltro = new JTable(modeloFiltro);
		    JScrollPane scroll = new JScrollPane(tablaFiltro);
			
			JTextField txtFolio = new JTextField();
			JTextField txtNombre_Completo = new JTextField();
			JTextField txtPeriodo = new JTextField();
			
			JButton btnAgregar = new JButton(new ImageIcon("Iconos/agregar.png"));
			
			int fila = 0;
			
			public Cat_Filtro_Ticket_Fuente_Sodas_AUXF( int folio, String empleado, int folio_periodo, int fila_empleado) {
				
				this.setModal(true);
				setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/filter_icon&16.png"));
				setTitle("Tabla De Ticket Por Empleado (Auxiliar y Finanzas)");
				campo.setBorder(BorderFactory.createTitledBorder("Seleccion De Ticket Por Empleado"));
				
				this.txtFolio.setEditable(false);
				this.txtNombre_Completo.setEditable(false);
				
				folio_empleado=folio;
				fila=fila_empleado;
				
				txtFolio.setText(folio_empleado+"");
				txtNombre_Completo.setText(empleado);
				
				buscar_tabla(folio_empleado);
				
				btnAgregar.setToolTipText("Agregar");
				
				campo.add(scroll).setBounds(15,43,374,360);
				
				campo.add(txtFolio).setBounds(15,20,40,20);
				campo.add(txtNombre_Completo).setBounds(56,20,240,20);
				campo.add(txtPeriodo).setBounds(298,20,40,20);
				campo.add(btnAgregar).setBounds(340,20,50,20);
				
				cont.add(campo);
				
				configuracionTabla();
				
				btnAgregar.addActionListener(opAgregar);
				
				txtPeriodo.setEditable(false);
				txtPeriodo.setText(folio_periodo+"");
				txtPeriodo.setHorizontalAlignment(0);
				
				setSize(415,450);
				setResizable(false);
				setLocationRelativeTo(null);
			}
			
			public void configuracionTabla(){
				
				tablaFiltro.getColumnModel().getColumn(0).setMaxWidth(100);
				tablaFiltro.getColumnModel().getColumn(0).setMinWidth(100);
				tablaFiltro.getColumnModel().getColumn(1).setMaxWidth(150);
				tablaFiltro.getColumnModel().getColumn(1).setMinWidth(150);
				tablaFiltro.getColumnModel().getColumn(2).setMaxWidth(80);
				tablaFiltro.getColumnModel().getColumn(2).setMinWidth(80);
				tablaFiltro.getColumnModel().getColumn(3).setMaxWidth(40);
				tablaFiltro.getColumnModel().getColumn(3).setMinWidth(40);
				
				tablaFiltro.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
				tablaFiltro.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
				tablaFiltro.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
				tablaFiltro.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("CHB","centro","Arial","normal",12));
			}
			
			ActionListener opAgregar = new ActionListener() {
				@SuppressWarnings("static-access")
				public void actionPerformed(ActionEvent arg0) {
					
						if(tablaFiltro.isEditing()){
				 			tablaFiltro.getCellEditor().stopCellEditing();
						}
					if(new Obj_Filtro_Ticket_Fuente_Sodas().guardar(tabla_guardar(), Integer.parseInt(txtFolio.getText()), txtNombre_Completo.getText(), Integer.valueOf(txtPeriodo.getText().trim()))){

						//tabla de tickets--------------------------------
						while(tablaFiltro.getRowCount()>0){
							modeloFiltro.removeRow(0);
					    }
						buscar_tabla(folio_empleado);
						//------------------------------------------------
						if(tablaFiltro.getRowCount()==0){
							dispose();
							//tabla de empleados con adeudo en fuente de sodas auxf--------------------------------
								new Cat_Traspaso_A_Cobro_De_Fuente_De_Sodas_AUXF().tabla_model.removeRow(fila);
							//------------------------------------------------
						}
					}else{
						JOptionPane.showMessageDialog(null, "Error al guardar", "Error al guardar registro", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
						return;
					}
				}
			};
			
			public void buscar_tabla(int folio_empleado){
				try {
					String[][] tabla = new Obj_Captura_Fuente_Sodas().tabla(folio_empleado);
										
					for(int i=0; i<tabla.length; i++){
						 		Object[] dom = new Object[5];
						 		
						 		dom[0] = tabla[i][0]+"   ";
						 		dom[1] = "   "+tabla[i][1];                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
						 		dom[2] = tabla[i][2];
						 		dom[3] = "";
						 		modeloFiltro.addRow(dom);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			
			private Object[][] tabla_guardar(){

				Object[][] matriz = new Object[tablaFiltro.getRowCount()][4];
				for(int i=0; i<tablaFiltro.getRowCount(); i++){
						matriz[i][0] = modeloFiltro.getValueAt(i,0).toString().trim();
						matriz[i][1] = modeloFiltro.getValueAt(i,1).toString().trim();
						matriz[i][2] = modeloFiltro.getValueAt(i,2).toString().trim();
						matriz[i][3] = Boolean.parseBoolean(modeloFiltro.getValueAt(i,3).toString().trim());
				}
				return matriz;
			}
}
	