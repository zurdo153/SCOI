package Cat_Lista_de_Raya;

import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Cargar_Combo;
import Obj_Lista_de_Raya.Obj_Autorizacion_Auditoria;
import Obj_Lista_de_Raya.Obj_Autorizacion_Finanzas;
import Obj_Lista_de_Raya.Obj_Deducciones_Y_Percepciones_De_Lista_De_Raya;
import Obj_Principal.Obj_Filtro_Dinamico;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Deducciones_Y_Percepciones_De_Lista_De_Raya extends Cat_Root implements TableModelListener {
	Runtime R = Runtime.getRuntime();

	private JCheckBox chb_habilitar = new JCheckBox("Habilitar");
	
	private String lista1[] = {"","1","2","3","4","5","6","7"};
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private JComboBox cmb_tabla_dias = new JComboBox(lista1);
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private JComboBox cmb_tabla_gafete = new JComboBox(lista1);
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private JComboBox cmb_tabla_dias_ext = new JComboBox(lista1);
   
	public String[] listaHrs(){
		String[] lista = new String[81];
		float valor=0;
			for (int i = 0; i<lista.length; i++){
				lista[i] = ((valor)-20)==0?"":((valor)-20)+"";
				valor+=.5;
			}
		return lista;
	};
	
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private JComboBox cmb_tabla_horas = new JComboBox(listaHrs());
    
	public String[] Combo_Conseptos(){
		try {return new Cargar_Combo().Conseptos();}
		catch (SQLException e) {e.printStackTrace();}
		return null;
	}	
	
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private JComboBox cmb_tabla_conseptos = new JComboBox(Combo_Conseptos());
    
    private DefaultTableModel tabla_model = new DefaultTableModel(new Obj_Deducciones_Y_Percepciones_De_Lista_De_Raya().get_tabla_model(),
            new String[]{"Folio", "Nombre Completo", "Establecimiento", "Inpuntualidad","Bono Puntualidad", "Omision", "Días Falta", "Inasitencia","Bono Asistencia","Días Gafete", "Dias Extra", "Hrs Extra","Extra", "P.Fisic", "Conceptos" }
			){
	     @SuppressWarnings("rawtypes")
		Class[] types = new Class[]{
	    	java.lang.Object.class, //folio
	    	java.lang.Object.class, //nombre completo
	    	java.lang.Object.class, //Establecimiento
	    	java.lang.Object.class, //Inpuntualidad
	    	java.lang.Boolean.class,//bono inpuntualidad
	    	java.lang.Object.class, //omision
	    	java.lang.Object.class, //Dias Falta
	    	java.lang.Boolean.class,//Inasistencia
	    	java.lang.Boolean.class,//Bono Asistencia
	    	java.lang.Object.class, //Dias Gafete
	    	java.lang.Object.class, //Dias Extra
	    	java.lang.Object.class, //Horas Extra
	    	java.lang.Object.class, //Extra
	    	java.lang.Boolean.class,//Precencia Fisica
	    	java.lang.Object.class  //Conceptos

         };
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Class getColumnClass(int columnIndex) {
             return types[columnIndex];
         }
         public boolean isCellEditable(int fila, int columna){
        	 
        	 switch(columna){
        	 	case 0  : return false; 
        	 	case 1  : return false; 
        	 	case 2  : return false; 
        	 	case 3  : return true; 
        	 	case 4  : return false;
        	 	case 5  : return true;
        	 	case 6  : return true;
        	 	case 7  : if(chb_habilitar.isSelected()){return true;} else{return false;}
        	 	case 8  : return false;
        	 	case 9  : return true;
        	 	case 10 : return true; 
        	 	case 11 : return true; 
        	 	case 12 : return true; 
        	 	case 13 :if(chb_habilitar.isSelected()){return true;} else{return false;}
        	 	case 14 :return true; 
        	 	
        	 }
 			return false;
 		}

	};

	public JTable tabla = new JTable(tabla_model);
	public JScrollPane scroll_tabla = new JScrollPane(tabla);
    
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public TableRowSorter trsfiltro = new TableRowSorter(tabla_model); 
 
	public TableColumn columna_dia_falta = tabla.getColumnModel().getColumn(6);
	public TableColumn columna_dia_gafete = tabla.getColumnModel().getColumn(9);
	public TableColumn columna_dias_ext = tabla.getColumnModel().getColumn(10);
	public TableColumn columna_hrs_ext = tabla.getColumnModel().getColumn(11);
	public TableColumn columna_conseptos = tabla.getColumnModel().getColumn(14);
	
	@SuppressWarnings("unused")
	public void tableChanged(TableModelEvent e) {
	        int row = e.getFirstRow();
	        int column = e.getColumn();
	        TableModel model = (TableModel)e.getSource();
	        String columnName = model.getColumnName(column);
	        String data = model.getValueAt(row, column).toString();

	        if(column!=3 && column!=4 && column!=7 && column!=8 && column!=13 && column!=14){
	        	 try{
	 	        	if(!data.equals("")){
	 	        		Float.valueOf(data);
	 	        	}
	 	        } catch (NumberFormatException nfe){
	 	        	tabla.setValueAt("", row, column);
	 	        }
	        }
	    }

	int fila = 0;
	int columna = 0;
	
	public Cat_Deducciones_Y_Percepciones_De_Lista_De_Raya(){
		int ancho = Toolkit.getDefaultToolkit().getScreenSize().width;
		int alto = Toolkit.getDefaultToolkit().getScreenSize().height;
		alto=alto-50;
		this.setSize(ancho, alto);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/percepciones_y_deducciones32.png"));
		this.setTitle("Deducciónes y Precepciones De Lista De Raya");
		tabla.getModel().addTableModelListener(this);
		this.columna_dia_falta.setCellEditor(new javax.swing.DefaultCellEditor(cmb_tabla_dias));
		this.columna_dia_gafete.setCellEditor(new javax.swing.DefaultCellEditor(cmb_tabla_gafete));
		
		this.columna_dias_ext.setCellEditor(new javax.swing.DefaultCellEditor(cmb_tabla_dias_ext));
		this.columna_hrs_ext.setCellEditor(new javax.swing.DefaultCellEditor(cmb_tabla_horas));
		this.columna_conseptos.setCellEditor(new javax.swing.DefaultCellEditor(cmb_tabla_conseptos));

		this.panel.remove(txtFolio);
		this.panel.remove(txtNombre_Completo);
		this.panel.remove(cmbEstablecimientos);
		this.panel.remove(chb_habilitar);
		
		this.panel.add(txtFolio).setBounds(30,35,35,20);
		this.panel.add(txtNombre_Completo).setBounds(65,35,290,20);
		this.panel.add(cmbEstablecimientos).setBounds(355,35,180,20);

		this.panel.add(chb_habilitar).setBounds(955,35,80,20);
		this.panel.add(scroll_tabla).setBounds(30,60,ancho-70,alto-120);

		this.cont.add(panel);

		this.llamar_render();
		this.init_tabla();
		
//      asigna el foco al JTextField deseado al arrancar la ventana
        this.addWindowListener(new WindowAdapter() {
                public void windowOpened( WindowEvent e ){
                txtNombre_Completo.requestFocus();
             }
        });

		this.btn_guardar.addActionListener(op_guardar);
			this.btn_guardar.setToolTipText("Guardar");
		this.btn_refrescar.setVisible(false);

		this.txtFolio.addKeyListener(op_filtro_folio);
		this.txtNombre_Completo.addKeyListener(op_filtro_nombre);
		this.cmbEstablecimientos.addActionListener(op_filtro_establecimiento);
		this.addWindowListener(op_cerrar);
		
		evento(tabla);
		tabla.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent arg0) {}
			public void keyReleased(KeyEvent arg0) {
				
				if(tabla.getSelectedColumn()==3){
					if(arg0.getKeyCode()==KeyEvent.VK_ENTER){
//						tabla.editingStopped(null);
						
						validarCelda();
						fila++;
							tabla.getSelectionModel().setSelectionInterval(fila, fila);
							tabla.editCellAt(fila, 3);
							Component aComp=tabla.getEditorComponent();
							aComp.requestFocus();
							
						tabla.setValueAt( ((new BuscarSQL().validar_si_tiene_bono_de_asistencia_y_puntualidad(Integer.valueOf(tabla.getValueAt(fila-1, 0).toString().trim()),"bono_puntualidad")) && (tabla.getValueAt(fila-1, 3).toString().equals("")) )?"true":"false", fila-1, 4);
						
						if(fila==tabla.getRowCount()-1){
							tabla.getCellEditor().stopCellEditing();
							tabla.setValueAt( ((new BuscarSQL().validar_si_tiene_bono_de_asistencia_y_puntualidad(Integer.valueOf(tabla.getValueAt(tabla.getRowCount()-1, 0).toString().trim()),"bono_puntualidad")) && (tabla.getValueAt(tabla.getRowCount()-1, 3).toString().equals("")) )?"true":"false", tabla.getRowCount()-1, 4);
						}
						
					}
				}
			}
			public void keyPressed(KeyEvent arg0) {}
		});
	}
	
	public void validarCelda(){
		try{
	        	if(!tabla.getValueAt(fila, 3).toString().trim().equals("")){
	        		Float.valueOf(tabla.getValueAt(fila, 3).toString().trim());
	        	}
	        } catch (NumberFormatException nfe){
	        	tabla.setValueAt("", fila, 3);
	        }
	}
	
	public void evento(final JTable tb){
		tb.addMouseListener(new MouseListener() {
			
			int contadorDeClick = 0;
			public void mouseReleased(MouseEvent e) {	
					if(tb.getSelectedColumn()==3){
					
						contadorDeClick ++;
						if(contadorDeClick > 1){
							tabla.setValueAt( ((new BuscarSQL().validar_si_tiene_bono_de_asistencia_y_puntualidad(Integer.valueOf(tabla.getValueAt(fila, 0).toString().trim()),"bono_puntualidad")) && (tabla.getValueAt(fila, 3).toString().equals("")) )?"true":"false", fila, 4);
						}
					
					fila = tabla.getSelectedRow();
					columna = tb.getSelectedColumn();
					
					tabla.getSelectionModel().setSelectionInterval(fila, fila);
					tb.editCellAt(fila, 3);
					Component aComp=tb.getEditorComponent();
					aComp.requestFocus();
				}
			}
			public void mousePressed(MouseEvent e) {			}
			public void mouseExited(MouseEvent e) {			}
			public void mouseEntered(MouseEvent e) {
				
				switch(tb.getSelectedColumn()){
		        	case 6: tb.setValueAt( ((new BuscarSQL().validar_si_tiene_bono_de_asistencia_y_puntualidad(Integer.valueOf(tb.getValueAt(tb.getSelectedRow(), 0).toString().trim()),"bono_asistencia")) && (tb.getValueAt(tb.getSelectedRow(), 7).toString().equals("false") && (tb.getValueAt(tb.getSelectedRow(), 6).toString().equals("")) ) )?"true":"false", tb.getSelectedRow(), 8); break;
		        	case 7: tb.setValueAt( ((new BuscarSQL().validar_si_tiene_bono_de_asistencia_y_puntualidad(Integer.valueOf(tb.getValueAt(tb.getSelectedRow(), 0).toString().trim()),"bono_asistencia")) && (tb.getValueAt(tb.getSelectedRow(), 7).toString().equals("false") && (tb.getValueAt(tb.getSelectedRow(), 6).toString().equals("")) ) )?"true":"false", tb.getSelectedRow(), 8); break;
				}
			}
			
			
			public void mouseClicked(MouseEvent e) {		}
		});
	}
		
	WindowListener op_cerrar = new WindowListener() {
		public void windowOpened(WindowEvent e) {}
		public void windowIconified(WindowEvent e) {}
		public void windowDeiconified(WindowEvent e) {}
		public void windowDeactivated(WindowEvent e) {}
		public void windowClosing(WindowEvent e) {
			if(JOptionPane.showConfirmDialog(null, "¿Desea guardar antes de cerrar?", "Aviso!", JOptionPane.YES_NO_OPTION) == 0){
				new Obj_Deducciones_Y_Percepciones_De_Lista_De_Raya().guardar(tabla_guardar());
			}
		}
		public void windowClosed(WindowEvent e) {}
		public void windowActivated(WindowEvent e) {}
	};

	public void refresh_pres_fisica(){
		for(int i = 0; i<tabla.getRowCount(); i++){
			int impunt 		= Integer.valueOf(!tabla.getValueAt(i, 3).toString().equals("")?1:0); 
        	int omi 		= Integer.valueOf(!tabla.getValueAt(i, 5).toString().equals("")?1:0); 
        	int dias_Falt 	= Integer.valueOf(!tabla.getValueAt(i, 6).toString().equals("")?1:0); 
        	int inasist 	= Integer.valueOf(tabla.getValueAt(i, 7).toString().equals("true")?1:0); 
        	int gafete 		= Integer.valueOf(!tabla.getValueAt(i, 9).toString().equals("")?1:0); 
        	tabla.setValueAt(((impunt+omi+dias_Falt+inasist+gafete)==0)?true:false, i, 13);
		}
	}
	
	ActionListener op_guardar = new ActionListener() {
		@SuppressWarnings("unchecked")
		public void actionPerformed(ActionEvent arg0) {
			Obj_Autorizacion_Auditoria auditoria = new Obj_Autorizacion_Auditoria().buscar();
			Obj_Autorizacion_Finanzas finanzas = new Obj_Autorizacion_Finanzas().buscar();
			
			boolean auditoriaBoolean = auditoria.isAutorizar();
			boolean finanzasBoolean = finanzas.isAutorizar();
			
				if((auditoriaBoolean == true)  || (finanzasBoolean == true)){
						
						JOptionPane.showMessageDialog(null, "La Lista De Raya Fue Autorizada No Puede Ser Modificada Ninguna Deduccion o Percepcion de Lista de Raya....."
						       +" \n Hasta Que Se Genere Por D.H o Se Desautorize por Finanzas o Auditoria <<>>","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
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
			
						if(valida_tabla() != ""){
								JOptionPane.showMessageDialog(null, "Las siguientes celdas están mal en su formato:\n"+valida_tabla(),"Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
								return;
						}else{
							if(JOptionPane.showConfirmDialog(null, "¿Desea guardar la lista de deducción por inasistencia?") == 0){
								 refresh_pres_fisica();
								Obj_Deducciones_Y_Percepciones_De_Lista_De_Raya inasistencia = new Obj_Deducciones_Y_Percepciones_De_Lista_De_Raya();
								
									if(inasistencia.guardar(tabla_guardar())){
										JOptionPane.showMessageDialog(null, "La tabla Deducción por Inasistencia se guardó exitosamente","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
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
		}
	};

	private Object[][] tabla_guardar(){
		Object[][] matriz = new Object[tabla.getRowCount()][tabla.getColumnCount()];
		for(int i=0; i<tabla.getRowCount(); i++){
			for(int j=0; j<tabla.getColumnCount(); j++){
						matriz[i][j] = tabla_model.getValueAt(i,j).toString().trim().equals("")?0:tabla_model.getValueAt(i,j).toString().trim();
			}
		}
		return matriz;
	}

	private String valida_tabla(){
		String error = "";
		for(int i=0; i<tabla.getRowCount(); i++){
			try{
				if(!isNumeric(tabla_model.getValueAt(i,10).toString())){
					error += "   La celda de la columna Extra no es un número en el [Folio: "+tabla_model.getValueAt(i,0)+"]\t\n";
				}
			} catch(Exception e){
				JOptionPane.showMessageDialog(null, "La tabla tiene una celda con texto en lugar de un valor numérico: \n"+e,"Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
			}
		}
		return error;
	}

	public void llamar_render(){
		tabla.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","derecha","Arial","negrita",12));
		tabla.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",12));
		tabla.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",12));
		tabla.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","derecha","Arial","negrita",12));
		tabla.getColumnModel().getColumn(4).setCellRenderer(new tablaRenderer("CHB","centro","Arial","negrita",12));
		tabla.getColumnModel().getColumn(5).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",12));
		tabla.getColumnModel().getColumn(6).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",12));
		tabla.getColumnModel().getColumn(7).setCellRenderer(new tablaRenderer("CHB","centro","Arial","negrita",12));
		tabla.getColumnModel().getColumn(8).setCellRenderer(new tablaRenderer("CHB","centro","Arial","negrita",12));
		tabla.getColumnModel().getColumn(9).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",12));
		tabla.getColumnModel().getColumn(10).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",12));
		tabla.getColumnModel().getColumn(11).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",12));
		tabla.getColumnModel().getColumn(12).setCellRenderer(new tablaRenderer("texto","derecha","Arial","negrita",12));
		tabla.getColumnModel().getColumn(13).setCellRenderer(new tablaRenderer("CHB","centro","Arial","negrita",12));
		tabla.getColumnModel().getColumn(14).setCellRenderer(new tablaRenderer("texto","derecha","Arial","negrita",12));
	}
	@SuppressWarnings("unchecked")
	public void init_tabla(){
		this.tabla.getTableHeader().setReorderingAllowed(false) ;
		tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		this.tabla.getColumnModel().getColumn(0).setMinWidth(30);
		this.tabla.getColumnModel().getColumn(1).setMinWidth(290);
		this.tabla.getColumnModel().getColumn(2).setMinWidth(150);
		this.tabla.getColumnModel().getColumn(3).setMinWidth(80);
		this.tabla.getColumnModel().getColumn(4).setMinWidth(70);
		this.tabla.getColumnModel().getColumn(5).setMinWidth(70);
		this.tabla.getColumnModel().getColumn(6).setMinWidth(70);
		this.tabla.getColumnModel().getColumn(7).setMinWidth(70);
		this.tabla.getColumnModel().getColumn(8).setMinWidth(70);
		this.tabla.getColumnModel().getColumn(9).setMinWidth(70);
		this.tabla.getColumnModel().getColumn(10).setMinWidth(70);
		this.tabla.getColumnModel().getColumn(11).setMinWidth(70);
		this.tabla.getColumnModel().getColumn(12).setMinWidth(70);
		this.tabla.getColumnModel().getColumn(13).setMinWidth(70);
		this.tabla.getColumnModel().getColumn(14).setMinWidth(190);
		this.tabla.setRowSorter(trsfiltro);  

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
		public void keyReleased(KeyEvent arg0) {
			new Obj_Filtro_Dinamico(tabla,"Nombre Completo", txtNombre_Completo.getText().toUpperCase(),"Establecimiento",cmbEstablecimientos.getSelectedItem()+"", "", "", "", "");
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}		
	};

	ActionListener op_filtro_establecimiento = new ActionListener(){
		public void actionPerformed(ActionEvent arg0){
			new Obj_Filtro_Dinamico(tabla,"Nombre Completo", txtNombre_Completo.getText().toUpperCase(),"Establecimiento",cmbEstablecimientos.getSelectedItem()+"", "", "", "", "");
		}
	};
	
	public static void main(String [] arg){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Deducciones_Y_Percepciones_De_Lista_De_Raya().setVisible(true);
		}catch(Exception e){	}
	}

}