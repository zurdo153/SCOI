package Cat_Auditoria;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import com.toedter.calendar.JDateChooser;

import Conexiones_SQL.BuscarTablasModel;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.Componentes;
import Obj_Renders.CaveceraTablaRenderer;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Revision_De_Cortes extends JFrame{

	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
//	TODO (Componentes)
	
	JDateChooser cfecha_in = new JDateChooser();
	JDateChooser cfecha_fin = new JDateChooser();
	
	private String establecimiento[] = new Obj_Establecimiento().Combo_Establecimiento();
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private JComboBox cmbEstablecimientos = new JComboBox(establecimiento);
	
    JTextField txtFolioCorte = new Componentes().text(new JTextField(), "Folio De Corte", 15, "String");
    JTextField txtAsignacion = new Componentes().text(new JTextField(), "Asignaciones", 15, "String");
    JTextField txtCajero = new Componentes().text(new JTextField(), "Cajera(o)", 50, "String");
    
    JButton btnGenerar = new JButton("Consultar");
    
//	TODO (Variables globales)
	int fila = 0;
	int columna = 21;
	
//    TODO Inicializar (modelo)
    public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"Folio", "F.Corte", "F.Asig", "Fecha", "Cajero", "Corte Sistema", "Apartado", "Retiros prog.", "Fectivo","Cheques"
    																			, "Fte. Sodas", "Voucher", "Diferencia", "TA", "R.Luz", "Retiro Clt","Total Voucher","Corte Total", "Efectivo Total", ""
    																			, "", "", "", "", "", "", "", "", "", ""
    																			, "", ""} ){
                    
		@SuppressWarnings({ "rawtypes" })
		Class[] types = new Class[]{
                   java.lang.Object.class,
                   java.lang.Object.class, 
                   java.lang.Object.class, 
                   java.lang.Object.class, 
                   java.lang.Object.class,
                   java.lang.Object.class, 
                   java.lang.Object.class, 
                   java.lang.Object.class,
                   java.lang.Object.class, 
                   java.lang.Object.class, 
                   java.lang.Object.class,
                   java.lang.Object.class, 
                   java.lang.Object.class, 
                   java.lang.Object.class, 
                   java.lang.Object.class,
                   java.lang.Object.class, 
                   java.lang.Object.class, 
                   java.lang.Object.class,
                   java.lang.Object.class, 
                   java.lang.Object.class,
                   java.lang.Object.class, 
                   java.lang.Object.class, 
                   java.lang.Object.class,
                   java.lang.Object.class, 
                   java.lang.Object.class, 
                   java.lang.Object.class, 
                   java.lang.Object.class,
                   java.lang.Object.class,
                   java.lang.Object.class, 
                   java.lang.Object.class,
                   java.lang.Object.class,
                   java.lang.Object.class
                    
    };
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
        }
    public boolean isCellEditable(int row, int column){
                switch(column){
                        case 0  : return false; 
                        case 1  : return false; 
                        case 2  : return false; 
                        case 3  : return false; 
                        case 4  : return false; 
                        case 5  : return false; 
                        case 6  : return false; 
                        case 7  : return false; 
                        case 8  : return false; 
                        case 9  : return false; 
                        case 10 : return false; 
                        case 11 : return false; 
                        case 12 : return false; 
                        case 13 : return false; 
                        case 14 : return false; 
                        case 15 : return false; 
                        case 16 : return false; 
                        case 17 : return false; 
                        case 18 : return false; 
                        case 19 : return false;
                        
                        case 20 : return false;
                        case 21 : return false;
                        case 22 : return false;
                        
                        case 23 : return false; 
                        case 24 : return false; 
                        case 25 : return false; 
                        case 26 : return false; 
                        case 27 : return false; 
                        case 28 : return false; 
                        case 29 : return false; 
                        case 30 : return false; 
                        case 31 : return false; 
                }
                 return false;
         }
    };
	
//    TODO Inicializar (tabla y scroll)
    JTable tabla = new JTable(modelo);
	JScrollPane scroll_grupos = new JScrollPane(tabla,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public TableRowSorter trsfiltro = new TableRowSorter(modelo);
	
	Border blackline;
	
	public TableColumn columna_status_cobro = tabla.getColumnModel().getColumn(20);
	public TableColumn columna_cobro_considerado = tabla.getColumnModel().getColumn(21);
	
//	TODO (Contructor)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Cat_Revision_De_Cortes(){
		
		this.setTitle("Abonos Clientes");
		this.panel.setBorder(BorderFactory.createTitledBorder( "Captura de abonos clientes"));
		
//		this.columna_status_cobro.setCellEditor(new javax.swing.DefaultCellEditor(cmb_status_de_cobro));
//		this.columna_cobro_considerado.setCellEditor(new javax.swing.DefaultCellEditor(txtCobroConsiderado));
		
		trsfiltro = new TableRowSorter(modelo); 
		tabla.setRowSorter(trsfiltro);  
		
		int x=35,y=25,ancho=168,alto=20;
		
//		TODO Agregar Componentes al panel (Coordenadas)

		panel.add(new JLabel("De: ")).setBounds(x+=20, y, 20, alto); 
		panel.add(cfecha_in).setBounds(x+=20, y, ancho, alto); 
		panel.add(new JLabel("A: ")).setBounds(x+=(ancho+10), y, 20, alto); 
		panel.add(cfecha_fin).setBounds(x+=20, y, ancho, alto); 
		panel.add(btnGenerar).setBounds(x+=(ancho+20), y, 100, alto);
		
		x=35;
		
		panel.add(cmbEstablecimientos).setBounds(x, y+=45, ancho, alto);  
		panel.add(txtFolioCorte).setBounds(x+=ancho, y, ancho-=105, alto);  
		panel.add(txtAsignacion).setBounds(x+=ancho, y, ancho+=35, alto);  
		panel.add(txtCajero).setBounds(x+=ancho, y, ancho*=2.5, alto);  
		
		panel.add(scroll_grupos).setBounds(8, y+=25, 1000, 630);
		
		llamar_render();
//		refresh();
		
		cmbEstablecimientos.addActionListener(op_filtro_establecimiento);
		txtFolioCorte.addKeyListener(op_filtro_folio_corte);
		txtAsignacion.addKeyListener(op_filtro_asignacion);
		txtCajero.addKeyListener(op_filtro_cajero); 

		agregar(tabla);
//		tabla.addKeyListener(op_key);
////		tabla.addMouseListener(opMouse);
		btnGenerar.addActionListener(opGenerar);

		cont.add(panel);
		
		this.setSize(1024,768);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
	}
	
//	TODO Funcion (llamar_render())
	public void llamar_render(){
    
		Color fondoEncabezado_grupos = new Color(255,171,0);
		
		Color textoEncabezado = Color.black;
		
		tabla.getColumnModel().getColumn(0).setHeaderRenderer(new CaveceraTablaRenderer(fondoEncabezado_grupos,textoEncabezado,"centro","Arial","negrita",10));
		tabla.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("CHB","izquierda","Arial","negrita",10));
		for(int i = 1; i<tabla.getColumnCount(); i++){
			
			if(i==13){
				tabla.getColumnModel().getColumn(i).setHeaderRenderer(new CaveceraTablaRenderer(Color.blue,Color.WHITE,"centro","Arial","negrita",10));
			}else{
				tabla.getColumnModel().getColumn(i).setHeaderRenderer(new CaveceraTablaRenderer(fondoEncabezado_grupos,textoEncabezado,"centro","Arial","negrita",10));
			}
			
			switch(i){
					case 0: tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",11)); break;
					case 1: tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",9)); break;
					case 2: tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",10)); break;
					case 3: tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",9)); break;
					case 4: tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",10)); break;
					case 17:tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",11)); break;
					case 19:tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",11)); break;
					
					case 20:tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",11)); break;
					case 21:tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","derecha","Arial","negrita",11)); break;
					case 22:tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",11)); break;
					
					default: tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","derecha","Arial","negrita",11)); break;
				}
		}
		
		int x=60;
		
    	this.tabla.getTableHeader().setReorderingAllowed(false) ;
    	this.tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    	
    	this.tabla.getColumnModel().getColumn(0 ).setMaxWidth(25);   
    	this.tabla.getColumnModel().getColumn(0 ).setMinWidth(25);	
    	this.tabla.getColumnModel().getColumn(1 ).setMaxWidth(x*3-10);   
    	this.tabla.getColumnModel().getColumn(1 ).setMinWidth(x*3-10);   
    	this.tabla.getColumnModel().getColumn(2 ).setMaxWidth(x);
    	this.tabla.getColumnModel().getColumn(2 ).setMinWidth(x);
    	this.tabla.getColumnModel().getColumn(3 ).setMaxWidth(x+40); 
    	this.tabla.getColumnModel().getColumn(3 ).setMinWidth(x+40); 
    	                                               
    	this.tabla.getColumnModel().getColumn(4 ).setMaxWidth(x*4+5);
    	this.tabla.getColumnModel().getColumn(4 ).setMinWidth(x*4+5);		
    	this.tabla.getColumnModel().getColumn(5 ).setMaxWidth(x);
    	this.tabla.getColumnModel().getColumn(5 ).setMinWidth(x);
    	this.tabla.getColumnModel().getColumn(6 ).setMaxWidth(x);
    	this.tabla.getColumnModel().getColumn(6 ).setMinWidth(x);
    	this.tabla.getColumnModel().getColumn(7 ).setMaxWidth(x);
    	this.tabla.getColumnModel().getColumn(7 ).setMinWidth(x);
    	                                               
    	this.tabla.getColumnModel().getColumn(8 ).setMaxWidth(x);
    	this.tabla.getColumnModel().getColumn(8 ).setMinWidth(x);		
    	this.tabla.getColumnModel().getColumn(9 ).setMaxWidth(x);
    	this.tabla.getColumnModel().getColumn(9 ).setMinWidth(x);
    	this.tabla.getColumnModel().getColumn(10).setMaxWidth(x);
    	this.tabla.getColumnModel().getColumn(10).setMinWidth(x);
    	this.tabla.getColumnModel().getColumn(11).setMaxWidth(x);
    	this.tabla.getColumnModel().getColumn(11).setMinWidth(x);
    	
    	this.tabla.getColumnModel().getColumn(12).setMaxWidth(x);
    	this.tabla.getColumnModel().getColumn(12).setMinWidth(x);		
    	this.tabla.getColumnModel().getColumn(13).setMaxWidth(x);
    	this.tabla.getColumnModel().getColumn(13).setMinWidth(x);
    	this.tabla.getColumnModel().getColumn(14).setMaxWidth(x);
    	this.tabla.getColumnModel().getColumn(14).setMinWidth(x);
    	this.tabla.getColumnModel().getColumn(15).setMaxWidth(x);
    	this.tabla.getColumnModel().getColumn(15).setMinWidth(x);
    	this.tabla.getColumnModel().getColumn(16).setMaxWidth(x);
    	this.tabla.getColumnModel().getColumn(16).setMinWidth(x);
    	this.tabla.getColumnModel().getColumn(17).setMaxWidth(x);
    	this.tabla.getColumnModel().getColumn(17).setMinWidth(x);
    	this.tabla.getColumnModel().getColumn(18).setMaxWidth(x*2);
    	this.tabla.getColumnModel().getColumn(18).setMinWidth(x*2);
    	this.tabla.getColumnModel().getColumn(19).setMaxWidth(x*5);
    	this.tabla.getColumnModel().getColumn(19).setMinWidth(x*5);
    	
    	this.tabla.getColumnModel().getColumn(20).setMaxWidth(x*2);
    	this.tabla.getColumnModel().getColumn(20).setMinWidth(x*2);
    	this.tabla.getColumnModel().getColumn(21).setMaxWidth(x+50);
    	this.tabla.getColumnModel().getColumn(21).setMinWidth(x+50);
    	this.tabla.getColumnModel().getColumn(22).setMaxWidth(x*8);
    	this.tabla.getColumnModel().getColumn(22).setMinWidth(x*5);
    	
//    	this.tabla.getColumnModel().getColumn(11).setMaxWidth(x);
//    	this.tabla.getColumnModel().getColumn(11).setMinWidth(x);
//    	
//    	this.tabla.getColumnModel().getColumn(12).setMaxWidth(x);
//    	this.tabla.getColumnModel().getColumn(12).setMinWidth(x);		
//    	this.tabla.getColumnModel().getColumn(13).setMaxWidth(x);
//    	this.tabla.getColumnModel().getColumn(13).setMinWidth(x);
//    	this.tabla.getColumnModel().getColumn(14).setMaxWidth(x);
//    	this.tabla.getColumnModel().getColumn(14).setMinWidth(x);
//    	this.tabla.getColumnModel().getColumn(15).setMaxWidth(x);
//    	this.tabla.getColumnModel().getColumn(15).setMinWidth(x);
//    	this.tabla.getColumnModel().getColumn(16).setMaxWidth(x);
//    	this.tabla.getColumnModel().getColumn(16).setMinWidth(x);
//    	this.tabla.getColumnModel().getColumn(17).setMaxWidth(x);
//    	this.tabla.getColumnModel().getColumn(17).setMinWidth(x);
//    	this.tabla.getColumnModel().getColumn(18).setMaxWidth(x*2);
//    	this.tabla.getColumnModel().getColumn(18).setMinWidth(x*2);
//    	this.tabla.getColumnModel().getColumn(19).setMaxWidth(x*5);
//    	this.tabla.getColumnModel().getColumn(19).setMinWidth(x*5);
//    	
//    	this.tabla.getColumnModel().getColumn(20).setMaxWidth(x*2);
//    	this.tabla.getColumnModel().getColumn(20).setMinWidth(x*2);
//    	this.tabla.getColumnModel().getColumn(21).setMaxWidth(x+50);
//    	this.tabla.getColumnModel().getColumn(21).setMinWidth(x+50);
//    	this.tabla.getColumnModel().getColumn(22).setMaxWidth(x*8);
//    	this.tabla.getColumnModel().getColumn(22).setMinWidth(x*5);
    	
    }
	
	private void agregar(final JTable tbl) {
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
	        public void mouseClicked(MouseEvent e) {
	        	
	        	if(e.getClickCount() == 1){
	        		fila= tbl.getSelectedRow();
	        	
	        		if(tbl.getSelectedColumn()>20){
	        			
		        		System.out.println(fila);
		        		System.out.println(tbl.getValueAt(fila, columna-1));
	        		
//			        		if(tbl.getValueAt(fila, columna-1).toString().trim().equals("No Cobrar")){
//			        			
////			                  quite edicion de celda de jtable
//					            tabla.putClientProperty ("terminateEditOnFocusLost", Boolean.TRUE) ;
//					            
////								modelo.setValueAt("", fila,columna);
////								tabla.setEnabled(true);
////				    			tabla.editCellAt(fila, columna);
////		    					Component aComp=tabla.getEditorComponent();
////		    					aComp.requestFocus();
//				    			
//				    			
////		        				System.out.println(fila);
//				        	}
			        		if(tbl.getValueAt(fila, columna-1).toString().trim().equals("Cobrar")){
//								modelo.setValueAt("", fila,columna);
//								tabla.setEnabled(true);
				    			tabla.editCellAt(fila, columna);
				    			Component aComp=tabla.getEditorComponent();
				    			aComp.requestFocus();
				    			
				    			
				    			
//				        		System.out.println(fila);
				        	}
			        		if(tbl.getValueAt(fila, columna-1).toString().trim().equals("Pasar A Seguridad")){
//								modelo.setValueAt("", fila,columna+1);
//								tabla.setEnabled(true);
				    			tabla.editCellAt(fila, columna+1);
				    			Component aComp=tabla.getEditorComponent();
				    			aComp.requestFocus();
				    			
				    			
//				        		System.out.println(fila);
				        	}
	        		
	        			}
	        	}
	        }
        });
    }
	
//	TODO (op_key)
//	KeyListener op_key = new KeyListener() {
//		public void keyTyped(KeyEvent e) {}
//		public void keyReleased(KeyEvent e) {
//			System.out.println(fila);
//			
//			if(modelo.getValueAt(fila,columna-1).toString().trim().equals("Cobrar")){
//				
//				if(modelo.getValueAt(fila,columna).toString().trim().equals("")){
//					
//					JOptionPane.showMessageDialog(null, "vacio","Aviso",JOptionPane.WARNING_MESSAGE);
//					modelo.setValueAt("", fila,columna);
//					tabla.setEnabled(true);
//	    			tabla.editCellAt(fila, columna);
//	    			Component aComp=tabla.getEditorComponent();
//	    			aComp.requestFocus();
//					return;
//					
//				}else{
////					if(!tabla.getValueAt(filaDep, columnaDep+1).equals("")){
////						tabla.setEnabled(false);
////					}else{
//						modelo.setValueAt("", fila,columna+1);
//						tabla.setEnabled(true);
//		    			tabla.editCellAt(fila, columna+1);
//		    			Component aComp=tabla.getEditorComponent();
//		    			aComp.requestFocus();
//		    			
//		    			
////					}
//				}
//			}
//			if(modelo.getValueAt(fila,columna-1).toString().trim().equals("Pasar A Seguridad")){
//				
//				if(modelo.getValueAt(fila,columna+1).toString().trim().equals("")){
//					
//					JOptionPane.showMessageDialog(null, "vacio","Aviso",JOptionPane.WARNING_MESSAGE);
//					modelo.setValueAt("", fila,columna+1);
//					tabla.setEnabled(true);
//	    			tabla.editCellAt(fila, columna+1);
//	    			Component aComp=tabla.getEditorComponent();
//	    			aComp.requestFocus();
//					return;
//					
//				}
////				else{
//////					if(!tabla.getValueAt(filaDep, columnaDep+1).equals("")){
//////						tabla.setEnabled(false);
//////					}else{
////						modelo.setValueAt("", fila,columna+1);
////						tabla.setEnabled(true);
////		    			tabla.editCellAt(fila, columna+1);
////		    			Component aComp=tabla.getEditorComponent();
////		    			aComp.requestFocus();
//////					}
////				}
//			}
//			
//			
//			
//
//		}
//		public void keyPressed(KeyEvent e) {}
//	};
	
//	MouseListener opMouse = new MouseListener() {
//		public void mouseReleased(MouseEvent e) {
//			
//		}
//		public void mousePressed(MouseEvent e) {
//			
//		}
//		public void mouseExited(MouseEvent e) {
//			
//		}
//		public void mouseEntered(MouseEvent e) {
//			
//		}
//		public void mouseClicked(MouseEvent e) {
//			if(modelo.getValueAt(fila,columna-1).toString().trim().equals("Cobrar")){
//				
//				if(modelo.getValueAt(fila,columna).toString().trim().equals("")){
//					
////					JOptionPane.showMessageDialog(null, "vacio","Aviso",JOptionPane.WARNING_MESSAGE);
//					modelo.setValueAt("", fila,columna);
//					tabla.setEnabled(true);
//	    			tabla.editCellAt(fila, columna);
//	    			Component aComp=tabla.getEditorComponent();
////	    			aComp.requestFocus();
////					return;
//					
//				}else{
////					if(!tabla.getValueAt(filaDep, columnaDep+1).equals("")){
////						tabla.setEnabled(false);
////					}else{
//						modelo.setValueAt("", fila,columna+1);
//						tabla.setEnabled(true);
//		    			tabla.editCellAt(fila, columna+1);
//		    			Component aComp=tabla.getEditorComponent();
//		    			aComp.requestFocus();
//		    			
//		    			
////					}
//				}
//			}
//			if(modelo.getValueAt(fila,columna-1).toString().trim().equals("Pasar A Seguridad")){
//				
//				if(modelo.getValueAt(fila,columna+1).toString().trim().equals("")){
//					
//					JOptionPane.showMessageDialog(null, "vacio","Aviso",JOptionPane.WARNING_MESSAGE);
//					modelo.setValueAt("", fila,columna+1);
//					tabla.setEnabled(true);
//	    			tabla.editCellAt(fila, columna+1);
//	    			Component aComp=tabla.getEditorComponent();
//	    			aComp.requestFocus();
//					return;
//					
//				}
////				else{
//////					if(!tabla.getValueAt(filaDep, columnaDep+1).equals("")){
//////						tabla.setEnabled(false);
//////					}else{
////						modelo.setValueAt("", fila,columna+1);
////						tabla.setEnabled(true);
////		    			tabla.editCellAt(fila, columna+1);
////		    			Component aComp=tabla.getEditorComponent();
////		    			aComp.requestFocus();
//////					}
////				}
//			}
//		}
//	};
	
//	TODO (opGenerar)
	ActionListener opGenerar = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			
			if( validar_fechas().equals("")){
				
				if(cfecha_fin.getDate().before(cfecha_in.getDate())){
					JOptionPane.showMessageDialog(null, "Las fechas estan invertidas, no se permite que la fecha inicial sea mayor que la fecha final","Aviso",JOptionPane.WARNING_MESSAGE);
					return;
				}else{
					refresh();
				}
				
			}else{
				JOptionPane.showMessageDialog(null, "Verifique que los siguientes campos esten correctos:\n"+validar_fechas(),"Aviso",JOptionPane.WARNING_MESSAGE);
				return;
			}
			
//			trsfiltro.setRowFilter(RowFilter.regexFilter("", 1));
//			trsfiltro.setRowFilter(RowFilter.regexFilter("", 2));
			
//			cmbEstablecimiento.setSelectedIndex(0);
//			
//			if(txtPlanes.getText().equals("")){
//				JOptionPane.showMessageDialog(null, "El Campo Planes Se encuentra Vacio.\nIngrese Una Cantidad Para Seguir","Aviso",JOptionPane.WARNING_MESSAGE);
//				return;
//			}else{
//				
//			}
			
//			if(guardarTrabajo( tabla_cv(), tabla_gr())>0){
//				Cat_Reporte_De_Trabajo_De_Crotes(folio_trabajo_realizado);
//			}else{
//				JOptionPane.showMessageDialog(null, "El trabajo no pudo ser guardado","Aviso",JOptionPane.WARNING_MESSAGE);
//				return;
//			}
			
		}
	};
	
//	TODO Llenar Arreglo con los datos de la tabla(tabla_gr())
	public Object[][] tabla_gr(){
		
		Object[][] matrizGr = new Object[tabla.getRowCount()][tabla.getColumnCount()];
		
		for(int i =0; i<tabla.getRowCount(); i++){
			for(int j =0; j<tabla.getColumnCount()-1; j++){
				matrizGr[i][j] = modelo.getValueAt(i, j+1).toString().trim();
			}
		}
		return matrizGr;
	}
	
//	TODO Limpiar tabla y reconsultar (refresh())
	public void refresh(){
		
		while(tabla.getRowCount()>0){
			modelo.removeRow(0);
		}
		String fecha_in = new SimpleDateFormat("dd/MM/yyyy").format(cfecha_in.getDate());;
		String fecha_fin= new SimpleDateFormat("dd/MM/yyyy").format(cfecha_fin.getDate());;
		
			String[][] matriz = new BuscarTablasModel().tabla_model_revision_de_cortes_por_auditoria(fecha_in,fecha_fin);
 
			String[] fila = new String[32];
	        for(int i=0; i<matriz.length; i++){
	        	
	        	for(int j=0; j<32; j++){
	        		fila[j] = matriz[i][j]+"";
	        	}
	        	modelo.addRow(fila);
	        }
	}
	
	public String validar_fechas(){
		String error = "";
//		@SuppressWarnings("unused")
		String fechaInicialNull = cfecha_in.getDate()+"";
		String fechaFinalNull = cfecha_fin.getDate()+"";
//		
	    if(fechaInicialNull.equals("null"))error+= "Fecha inicial\n";
	    if(fechaFinalNull.equals("null"))error+= "Fecha final\n";
	    
		return error;
	}
	
//		public void Cat_Reporte_De_Trabajo_De_Crotes(int folio_trabajo_de_corte) {
//			
////			String query_corte_caja = "exec sp_Reporte_De_Trabajo_De_Cortes "+folio_trabajo_de_corte;
////			Statement stmt = null;
//			
//			try {
//				
////				stmt =  new Connexion().conexion().createStatement();
//////			    ResultSet rs = 
////			    		stmt.executeQuery(query_corte_caja);
//			    
//				JasperReport report = JasperCompileManager.compileReport(System.getProperty("user.dir")+"\\src\\Obj_Reportes\\Obj_Reporte_Para_Trabajo_De_Cortes.jrxml");
//				
//				Map parametro = new HashMap();
//				parametro.put("folio_trabajo", folio_trabajo_de_corte);
//				
//				JasperPrint print = JasperFillManager.fillReport(report, parametro, new Connexion().conexion());
//				JasperViewer.viewReport(print, false);
//			} catch (Exception e) {
//				System.out.println(e.getMessage());
//				JOptionPane.showMessageDialog(null, "Error En Cat_Reporte_De_Corte_De_Caja ", "Error !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
//			}
//			
//		}

//	TODO (ActionListener)
	ActionListener op_filtro_establecimiento = new ActionListener(){
		@SuppressWarnings("unchecked")
		public void actionPerformed(ActionEvent arg0){
			if(cmbEstablecimientos.getSelectedIndex() != 0){
				trsfiltro.setRowFilter(RowFilter.regexFilter(cmbEstablecimientos.getSelectedItem()+"", 1));
			}else{
				trsfiltro.setRowFilter(RowFilter.regexFilter("", 2));
			}
		}
	};
	
//	TODO (KeyListener para filtro)
	KeyListener op_filtro_folio_corte = new KeyListener(){
		@SuppressWarnings("unchecked")
		public void keyReleased(KeyEvent arg0) {
			trsfiltro.setRowFilter(RowFilter.regexFilter(txtFolioCorte.getText().toUpperCase().trim(), 2));
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}		
	};
	KeyListener op_filtro_asignacion = new KeyListener(){
		@SuppressWarnings("unchecked")
		public void keyReleased(KeyEvent arg0) {
			trsfiltro.setRowFilter(RowFilter.regexFilter(txtAsignacion.getText().toUpperCase().trim(), 3));
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}		
	};
	KeyListener op_filtro_cajero = new KeyListener(){
		@SuppressWarnings("unchecked")
		public void keyReleased(KeyEvent arg0) {
			trsfiltro.setRowFilter(RowFilter.regexFilter(txtCajero.getText().toUpperCase().trim(), 4));
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}		
	};
	
//	TODO metodo principal(mail(String [] arg))
	public static void main(String[] args) {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Revision_De_Cortes().setVisible(true);
		}catch(Exception e){
			System.err.println("Error :"+ e.getMessage());
		}
	}
}
