package Cat_Auditoria;

import java.awt.Color;
import java.awt.Container;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

import com.toedter.calendar.JDateChooser;

import Conexiones_SQL.BuscarTablasModel;
import Conexiones_SQL.GuardarSQL;
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
	
	private String establecimiento[] = new Obj_Establecimiento().Combo_Establecimiento_Revision_De_Cortes();
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private JComboBox cmbEstablecimientos = new JComboBox(establecimiento);
	
    JTextField txtFolioCorteFiltro = new Componentes().text(new JTextField(), "Folio De Corte", 15, "String");
    JTextField txtAsignacion = new Componentes().text(new JTextField(), "Asignaciones", 15, "String");
    JTextField txtCajero = new Componentes().text(new JTextField(), "Cajera(o)", 50, "String");
    
    JCheckBox chbMosrtarTodo = new JCheckBox("Mostrar Todo");
    
    JButton btnGenerar = new JButton("Consultar");
    
//	TODO (Variables globales)
	int fila = 0;
	int columna = 21;
	
//    TODO Inicializar (modelo)
    public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"Folio", "F.Corte", "F.Asig", "Fecha De Corte", "Cajero", "Corte Sistema", "Apartado", "Abono", "Retiros prog.", "Efectivo","Cheques"
    																			, "Fte. Sodas", "Voucher", "Diferencia", "TA", "R.Luz", "Retiro Clt","Total Voucher", "EFECTIVO TOTAL", "Realizo Corte"
    																			, "Establecimiento", "Deposito", "Obs. Corte", "fecha Asignacion", "Fecha Liquidacion", "Status Cobro Aud", "Diferencia Aud", "Obs Aud", "Fecha Reviso Aud", "Reviso Aud"
    																			, "Status Cobro Seg", "Diferencia Seg", "Obs Seg", "Fecha Reviso Seg", "Reviso Seg"} ){
                    
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
                        case 32 : return false; 
                        case 33 : return false; 
                        case 34 : return false;
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
		int anchop = Toolkit.getDefaultToolkit().getScreenSize().width;
		int altop = Toolkit.getDefaultToolkit().getScreenSize().height-50;
		this.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds()); 
		
		this.setTitle("Revision De Cortes");
		this.panel.setBorder(BorderFactory.createTitledBorder( "Revision De Cortes"));
		
		trsfiltro = new TableRowSorter(modelo); 
		tabla.setRowSorter(trsfiltro);  
		
		int x=35,y=25,ancho=168,alto=20;
		
//		TODO Agregar Componentes al panel (Coordenadas)

		panel.add(new JLabel("De: ")).setBounds(x+=20, y, 20, alto); 
		panel.add(cfecha_in).setBounds(x+=20, y, ancho, alto); 
		panel.add(new JLabel("A: ")).setBounds(x+=(ancho+10), y, 20, alto); 
		panel.add(cfecha_fin).setBounds(x+=20, y, ancho, alto); 
		panel.add(btnGenerar).setBounds(x+=(ancho+20), y, 100, alto);
		
		panel.add(chbMosrtarTodo).setBounds(x+120, y, 100, alto);
		
		x=65;
		
		panel.add(txtFolioCorteFiltro).setBounds(x, y+=35, ancho-=105, alto);  
		panel.add(txtAsignacion).setBounds(x+=ancho, y, ancho, alto);  
		panel.add(txtCajero).setBounds((x+=ancho)+120, y, (ancho*=2.5)+80, alto);  
		panel.add(cmbEstablecimientos).setBounds(x*4+75, y, 168, alto); 
		
		panel.add(scroll_grupos).setBounds(8, y+=20, anchop-25, altop-130);
		
		llamar_render();
		
		cmbEstablecimientos.addActionListener(op_filtro_establecimiento);
		txtFolioCorteFiltro.addKeyListener(op_filtro_folio_corte);
		txtAsignacion.addKeyListener(op_filtro_asignacion);
		txtCajero.addKeyListener(op_filtro_cajero); 

		agregar(tabla);
		btnGenerar.addActionListener(opGenerar);

		cont.add(panel);
		
		this.setSize(anchop,altop);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
	}
	
//	TODO Funcion (llamar_render())
	public void llamar_render(){
    
		Color fondoEncabezado_grupos = new Color(255,171,0);
		
		Color textoEncabezado = Color.black;
		
		tabla.getColumnModel().getColumn(0).setHeaderRenderer(new CaveceraTablaRenderer(fondoEncabezado_grupos,textoEncabezado,"centro","Arial","negrita",10));
		for(int i = 0; i<tabla.getColumnCount(); i++){
			
			if(i==13 || i==26 || i==31){
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
					
					case 20:tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",11)); break;
//					case 21:tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","derecha","Arial","negrita",11)); break;
					case 22:tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",11)); break;
					case 23:tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",11)); break;
					case 24:tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",11)); break;

					case 27:tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",11)); break;
					case 28:tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",11)); break;
					case 29:tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",11)); break;
					
					case 32:tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",11)); break;
					case 33:tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",11)); break;
					case 34:tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",11)); break;
					
					default: tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","derecha","Arial","negrita",11)); break;
				}
		}
		
		int x=60;
		
    	this.tabla.getTableHeader().setReorderingAllowed(false) ;
    	this.tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    	
    	this.tabla.getColumnModel().getColumn(0 ).setMaxWidth(x);   
    	this.tabla.getColumnModel().getColumn(0 ).setMinWidth(x);	
    	this.tabla.getColumnModel().getColumn(1 ).setMaxWidth(x);   
    	this.tabla.getColumnModel().getColumn(1 ).setMinWidth(x);   
    	this.tabla.getColumnModel().getColumn(2 ).setMaxWidth(x);
    	this.tabla.getColumnModel().getColumn(2 ).setMinWidth(x);
    	this.tabla.getColumnModel().getColumn(3 ).setMaxWidth(x*2); 
    	this.tabla.getColumnModel().getColumn(3 ).setMinWidth(x*2); 
    	                                               
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
    	this.tabla.getColumnModel().getColumn(18).setMaxWidth(x+30);
    	this.tabla.getColumnModel().getColumn(18).setMinWidth(x+30);
    	this.tabla.getColumnModel().getColumn(19).setMaxWidth((x*5)-30);
    	this.tabla.getColumnModel().getColumn(19).setMinWidth((x*5)-30);
    	
    	this.tabla.getColumnModel().getColumn(20).setMaxWidth((x*2)+40);
    	this.tabla.getColumnModel().getColumn(20).setMinWidth((x*2)+40);
    	this.tabla.getColumnModel().getColumn(21).setMaxWidth(x);
    	this.tabla.getColumnModel().getColumn(21).setMinWidth(x);
    	this.tabla.getColumnModel().getColumn(22).setMaxWidth(x*8);
    	this.tabla.getColumnModel().getColumn(22).setMinWidth(x*5);
    	
    	this.tabla.getColumnModel().getColumn(23).setMaxWidth(x*2);
    	this.tabla.getColumnModel().getColumn(23).setMinWidth(x*2);
    	this.tabla.getColumnModel().getColumn(24).setMaxWidth(x*2);
    	this.tabla.getColumnModel().getColumn(24).setMinWidth(x*2);		
    	
    	this.tabla.getColumnModel().getColumn(25).setMaxWidth(x*2);
    	this.tabla.getColumnModel().getColumn(25).setMinWidth(x*2);
    	this.tabla.getColumnModel().getColumn(26).setMaxWidth(x*2);
    	this.tabla.getColumnModel().getColumn(26).setMinWidth(x*2);
    	this.tabla.getColumnModel().getColumn(27).setMaxWidth(x*4);
    	this.tabla.getColumnModel().getColumn(27).setMinWidth(x*4);
    	this.tabla.getColumnModel().getColumn(28).setMaxWidth(x*2);
    	this.tabla.getColumnModel().getColumn(28).setMinWidth(x*2);
    	this.tabla.getColumnModel().getColumn(29).setMaxWidth(x*4+5);
    	this.tabla.getColumnModel().getColumn(29).setMinWidth(x*4+5);
    	
    	this.tabla.getColumnModel().getColumn(30).setMaxWidth(x*2);
    	this.tabla.getColumnModel().getColumn(30).setMinWidth(x*2);
    	this.tabla.getColumnModel().getColumn(31).setMaxWidth(x*2);
    	this.tabla.getColumnModel().getColumn(31).setMinWidth(x*2);
    	this.tabla.getColumnModel().getColumn(32).setMaxWidth(x*4);
    	this.tabla.getColumnModel().getColumn(32).setMinWidth(x*4);
    	this.tabla.getColumnModel().getColumn(33).setMaxWidth(x*2);
    	this.tabla.getColumnModel().getColumn(33).setMinWidth(x*2);
    	this.tabla.getColumnModel().getColumn(34).setMaxWidth(x*4+5);
    	this.tabla.getColumnModel().getColumn(34).setMinWidth(x*4+5);
    	
    }
	
	private void agregar(final JTable tbl) {
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
	        public void mouseClicked(MouseEvent e) {
	        	int columna = 25;
	        	
	        	if(e.getClickCount() == 2){
	        		fila= tbl.getSelectedRow();
	        		
	        		String[] matriz = new BuscarTablasModel().permiso_para_revision_de_cortes();
	        		
	        		String seguridad = matriz[0].toString().trim();
	        		String auditoria = matriz[1].toString().trim();
	        		
	        		if(seguridad.equals("true") && auditoria.equals("false")){ columna = 30;	}
	        		if(seguridad.equals("flase") && auditoria.equals("true")){ columna = 25;	}
					if(seguridad.equals("true") && auditoria.equals("true")) { columna = 30;	}
	        		
	        			if(!tbl.getValueAt(fila, columna).toString().trim().equals("")){
	        				JOptionPane.showMessageDialog(null, "No se puede abrir este corte por que ya fue revisado","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
	    					return;
	        			}else{
	        				String f_corte 			=	tbl.getValueAt(fila, 1).toString().trim();
			        		String f_asignacion 	=	tbl.getValueAt(fila, 2).toString().trim();
			        		String fecha_corte		=	tbl.getValueAt(fila, 3).toString().trim();
			        		String cajero			=	tbl.getValueAt(fila, 4).toString().trim();
			        		String corte_sistema	=	tbl.getValueAt(fila, 5).toString().trim();
			        		String diferencia		=	tbl.getValueAt(fila, 13).toString().trim();
			        		String efectivo_corte	=	tbl.getValueAt(fila, 18).toString().trim();
			        		String establecimiento	=	tbl.getValueAt(fila, 20).toString().trim();
			        			
				        	new Revisar__Y_Clasificar_Corte(f_corte,f_asignacion,fecha_corte,cajero,corte_sistema,diferencia,efectivo_corte,establecimiento).setVisible(true);
	        			}
	        	}
	        }
        });
    }
	
//	TODO (opGenerar)
	ActionListener opGenerar = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			
    		String[] matriz = new BuscarTablasModel().permiso_para_revision_de_cortes();
    		
    		String seguridad = matriz[0].toString().trim();
    		String auditoria = matriz[1].toString().trim();
    		
    		if(seguridad.equals("false") && auditoria.equals("false")){
    			JOptionPane.showMessageDialog(null, "No Cuentas Con Autorizacion Para Realizar Esta Consulta","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
    		}else{
    			if( validar_fechas().equals("")){
    				
    				if(cfecha_fin.getDate().before(cfecha_in.getDate())){
    					JOptionPane.showMessageDialog(null, "Las fechas estan invertidas, no se permite que la fecha inicial sea mayor que la fecha final","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
    					return;
    				}else{
    					refresh();
    					txtFolioCorteFiltro.requestFocus();
    				}
    				
    			}else{
    				JOptionPane.showMessageDialog(null, "Verifique que los siguientes campos esten correctos:\n"+validar_fechas(),"Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
    				return;
    			}
    		}
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
		
		String fecha_in = new SimpleDateFormat("dd/MM/yyyy").format(cfecha_in.getDate());
		String fecha_fin= new SimpleDateFormat("dd/MM/yyyy").format(cfecha_fin.getDate());
		
		String seleccionar_todos = "1";
		if(chbMosrtarTodo.isSelected()){
			seleccionar_todos = "0";
		}
			String[][] matriz = new BuscarTablasModel().tabla_model_revision_de_cortes_por_auditoria(fecha_in,fecha_fin,seleccionar_todos);
			
			if(matriz.length==0){
				JOptionPane.showMessageDialog(null, "No se encontraron registros con las condiciones de busqueda proporcionada","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}else{
				String[] fila = new String[35];
		        for(int i=0; i<matriz.length; i++){
		        	
		        	for(int j=0; j<35; j++){
		        		fila[j] = matriz[i][j]+"";
		        	}
		        	modelo.addRow(fila);
		        }
			}
	}
	
	public String validar_fechas(){
		String error = "";
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
				trsfiltro.setRowFilter(RowFilter.regexFilter(cmbEstablecimientos.getSelectedItem()+"", 20));
			}else{
				trsfiltro.setRowFilter(RowFilter.regexFilter("", 2));
			}
		}
	};
	
//	TODO (KeyListener para filtro)
	KeyListener op_filtro_folio_corte = new KeyListener(){
		@SuppressWarnings("unchecked")
		public void keyReleased(KeyEvent arg0) {
			trsfiltro.setRowFilter(RowFilter.regexFilter(txtFolioCorteFiltro.getText().toUpperCase().trim(), 1));
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}		
	};
	KeyListener op_filtro_asignacion = new KeyListener(){
		@SuppressWarnings("unchecked")
		public void keyReleased(KeyEvent arg0) {
			trsfiltro.setRowFilter(RowFilter.regexFilter(txtAsignacion.getText().toUpperCase().trim(), 2));
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
	
	
//	TODO subClase (editar y clasificar un corte)
	public class Revisar__Y_Clasificar_Corte extends JDialog{
		
		Container cont = getContentPane();
		JLayeredPane panel = new JLayeredPane();
		
		JLabel lblFolioCorte	 			= new JLabel("Folio De Corte:	 	");
		JLabel lblFolioAsignacion			= new JLabel("Folio De Asignacion:	");
		JLabel lblFechaCorte				= new JLabel("Fecha De Corte:		");
		JLabel lblCajero					= new JLabel("Cajero:				");
		JLabel lblEstablecimiento			= new JLabel("Establecimiento:		");
		JLabel lblCorteSistema				= new JLabel("Corte De Sistema:		");
		JLabel lblDiferenciaCorte			= new JLabel("Diferencia Corte:		");
		JLabel lblEfectivoTotal				= new JLabel("Efectivo Total:		");
		JLabel lblStatusCobro				= new JLabel("Status de cobro:		");
		JLabel lblDiferenciaManual			= new JLabel("Diferencia manual:	");
		JLabel lblStatusResponsable			= new JLabel("Responsable:			");
	
		JTextField txtFolioCorte	 		= new JTextField("");
		JTextField txtFolioAsignacion		= new JTextField("");
		JTextField txtFechaCorte			= new JTextField("");
		JTextField txtCajero				= new JTextField("");
		JTextField txtEstablecimiento		= new JTextField("");
		JTextField txtCorteSistema			= new JTextField("");
		JTextField txtDiferenciaCorte 		= new JTextField("");
		JTextField txtEfectivoTotal			= new JTextField("");
		JTextField txtDiferenciaManual		= new Componentes().text(new JTextField(), "diferencia por auditoria", 8, "Real");
		
		
		String[] statusCobro = {"Seleccione un movimiento","No Cobrar","Cobrar","Pasar a seguridad"};
		@SuppressWarnings({ "rawtypes", "unchecked" })
		JComboBox cmbStatusCobro = new JComboBox(statusCobro);
		
		String[] statusResponsable = new BuscarTablasModel().Responsable_de_error_en_corte();
		@SuppressWarnings({ "rawtypes", "unchecked" })
		JComboBox cmbStatusResponsable = new JComboBox(statusResponsable);
		
		JButton btnGuardar = new JButton("Guardar");
		
		JTextArea txaObservacion = new Componentes().textArea(new JTextArea(), "Observaciones", 400);
		JScrollPane Observasion = new JScrollPane(txaObservacion);
		
		public Revisar__Y_Clasificar_Corte(String f_corte, String f_asignacion, String fecha_corte, String cajero, String corte_sistema, String diferencia, String efectivo_corte, String establecimiento){
			this.setModal(true);
			this.setTitle("Clasificar Corte");
			this.panel.setBorder(BorderFactory.createTitledBorder( "Clasificar Corte"));
			
			txaObservacion.setLineWrap(true);
			
			int x=15,y=15,ancho=110;
	
			panel.add(lblCajero				).setBounds(x,y,ancho,20);           	 panel.add(txtCajero				).setBounds(x+ancho+20,y,ancho*3,20); 
			panel.add(lblEstablecimiento	).setBounds(x,y+=25,ancho,20);           panel.add(txtEstablecimiento		).setBounds(x+ancho+20,y,ancho+50,20); 	panel.add(lblCorteSistema		).setBounds(x+350,y,ancho,20);           panel.add(txtCorteSistema			).setBounds(x+ancho+370,y,ancho,20);

			panel.add(lblFolioCorte	 		).setBounds(x,y+=25,ancho,20);           panel.add(txtFolioCorte	 		).setBounds(x+ancho+20,y,ancho,20);     panel.add(lblDiferenciaCorte	).setBounds(x+350,y,ancho,20);           panel.add(txtDiferenciaCorte		).setBounds(x+ancho+370,y,ancho,20);
			panel.add(lblFolioAsignacion	).setBounds(x,y+=25,ancho,20);           panel.add(txtFolioAsignacion		).setBounds(x+ancho+20,y,ancho,20);     panel.add(lblEfectivoTotal		).setBounds(x+350,y,ancho,20);           panel.add(txtEfectivoTotal			).setBounds(x+ancho+370,y,ancho,20);
			panel.add(lblFechaCorte			).setBounds(x,y+=25,ancho,20);           panel.add(txtFechaCorte			).setBounds(x+ancho+20,y,ancho+50,20);  panel.add(lblDiferenciaManual	).setBounds(x+350,y,ancho,20);			 panel.add(txtDiferenciaManual		).setBounds(x+ancho+370,y,ancho,20);
			panel.add(lblStatusCobro		).setBounds(x,y+=25,ancho,20);           panel.add(cmbStatusCobro			).setBounds(x+ancho+20,y,ancho+50,20);  panel.add(lblStatusResponsable	).setBounds(x+350,y,ancho,20);			 panel.add(cmbStatusResponsable		).setBounds(x+ancho+320,y,ancho+50,20);  
			panel.add(Observasion			).setBounds(x,y+=25,ancho*5+40,120);
			panel.add(btnGuardar			).setBounds(x+ancho+370,y+=122,ancho,20);
			
			txtFolioCorte	 	.setEditable(false);
			txtFolioAsignacion	.setEditable(false);
			txtFechaCorte		.setEditable(false);
			txtCajero			.setEditable(false);
			txtEstablecimiento	.setEditable(false);
			txtCorteSistema		.setEditable(false);
			txtDiferenciaCorte	.setEditable(false);
			txtEfectivoTotal	.setEditable(false);
			
			txtFolioCorte	 	.setText(f_corte);
			txtFolioAsignacion	.setText(f_asignacion);
			txtFechaCorte		.setText(fecha_corte);
			txtCajero			.setText(cajero);
			txtEstablecimiento	.setText(establecimiento);
			txtCorteSistema		.setText(corte_sistema);
			txtDiferenciaCorte	.setText(diferencia);
			txtEfectivoTotal	.setText(efectivo_corte);	
			
			cmbStatusCobro.addActionListener(opFocoDif);
			txtDiferenciaManual.addActionListener(opFocoObs);
			btnGuardar.addActionListener(opGuardar);
			
			cont.add(panel);
			
			this.setSize(620,340);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
		}
		
		ActionListener opFocoDif = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(cmbStatusCobro.getSelectedIndex()==0){
					cmbStatusCobro.requestFocus();
				}else{
					txtDiferenciaManual.requestFocus();
				}
			}
		};
		
		ActionListener opFocoObs = new ActionListener(){
			public void actionPerformed(ActionEvent e){
					txaObservacion.requestFocus();
			}
		};
		
		ActionListener opGuardar = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
					if(cmbStatusCobro.getSelectedIndex()==0){
						JOptionPane.showMessageDialog(null, "Para poder guardar la revision primero se debe clasificar el status de cobro","Aviso",JOptionPane.WARNING_MESSAGE);
						return;
					}else{
						
							if(txaObservacion.getText().trim().equals("")){
									JOptionPane.showMessageDialog(null, "Es necesario poner una observacion","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
									return;
							}else{
								
								if(new GuardarSQL().Guardar_Revision_De_Corte_Aud(txtFolioCorte.getText(),cmbStatusCobro.getSelectedItem()+"",txtDiferenciaManual.getText(),txaObservacion.getText().toUpperCase(),cmbStatusResponsable.getSelectedItem().toString().trim())){
									
										while(tabla.getRowCount()>0){
											modelo.removeRow(0);
										}
										
										refresh();
										dispose();
										JOptionPane.showMessageDialog(null, "El Registro Se Guardo Exitosamente!!!","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
										return;
										
								}else{
										JOptionPane.showMessageDialog(null, "No se pudo guardar el registro","Error",JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
										return;
								}
								
							}
					}
			}
		};
	}
	
//	TODO metodo principal(main(String [] arg))
	public static void main(String[] args) {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Revision_De_Cortes().setVisible(true);
		}catch(Exception e){
			System.err.println("Error :"+ e.getMessage());
		}
	}
	
}
