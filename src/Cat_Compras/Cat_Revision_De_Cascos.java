package Cat_Compras;

import java.awt.Color;
import java.awt.Container;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Time;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SpinnerDateModel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import com.toedter.calendar.JDateChooser;

import Conexiones_SQL.BuscarSQL;
import Obj_Principal.Componentes;
import Obj_Renders.CaveceraTablaRenderer;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Revision_De_Cascos extends JFrame{

	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
//	TODO (Componentes)
	
	JDateChooser fh_in = new JDateChooser();
	JDateChooser fh_fin = new JDateChooser();
	
    JTextField txtFolioCorteFiltro = new Componentes().text(new JTextField(), "Folio De Corte", 15, "String");
    JTextField txtAsignacion = new Componentes().text(new JTextField(), "Asignaciones", 15, "String");
    JTextField txtCajero = new Componentes().text(new JTextField(), "Cajera(o)", 50, "String");
    
//    JCheckBox chbMosrtarTodo = new JCheckBox("Mostrar Todo");
    
    JButton btnGenerar = new JButton("Consultar",new ImageIcon("imagen/buscar.png"));
    
	SpinnerDateModel sdtIn =  new SpinnerDateModel();
	  JSpinner spHoraIn = new JSpinner(sdtIn);                                         
	  JSpinner.DateEditor spDHoraIn = new JSpinner.DateEditor(spHoraIn,"H:mm:ss");  
	  
	SpinnerDateModel sdtFin =  new SpinnerDateModel();
	  JSpinner spHoraFin = new JSpinner(sdtFin);                                         
	  JSpinner.DateEditor spDHoraFin = new JSpinner.DateEditor(spHoraFin,"H:mm:ss");  
	  
//	TODO (Variables globales)
	int fila = 0;
	int columna = 21;
	
//    TODO Inicializar (modelo)
    public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"Folio", "F.Corte", "F.Asig", "Fecha De Corte"
    																		, "Corte Sistema", "Apartado", "Abono", "Retiros prog."} ){
                    
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
	
//	TODO (Contructor)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Cat_Revision_De_Cascos(){
		int anchop = Toolkit.getDefaultToolkit().getScreenSize().width;
		int altop = Toolkit.getDefaultToolkit().getScreenSize().height-50;
		this.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds()); 
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Sales-by-payment-method-icon-64.png"));
		
		this.setTitle("Revision De Movimientos de Cascos");
		this.panel.setBorder(BorderFactory.createTitledBorder( "Revision De Movimientos de Cascos"));
		
		trsfiltro = new TableRowSorter(modelo); 
		tabla.setRowSorter(trsfiltro);  
		
		
		
		int x=35,y=25,ancho=168,alto=20;
		
//		TODO Agregar Componentes al panel (Coordenadas)

		panel.add(new JLabel("De: ")).setBounds(x+=20, y, 20, alto); 
		panel.add(fh_in).setBounds(x+=20, y, ancho, alto); 
		panel.add(spHoraIn).setBounds(x+=(ancho), y, 80, alto);
		
		panel.add(new JLabel("A: ")).setBounds(x+=(ancho-70), y, 20, alto); 
		panel.add(fh_fin).setBounds(x+=20, y, ancho, alto); 
		panel.add(spHoraFin).setBounds(x+=(ancho), y, 80, alto);
		
		panel.add(btnGenerar).setBounds(x+=(ancho-80), y, 100, alto);
		
		x=65;
		
		panel.add(txtFolioCorteFiltro).setBounds(x, y+=35, ancho-=105, alto);  
		panel.add(txtAsignacion).setBounds(x+=ancho, y, ancho, alto);  
		panel.add(txtCajero).setBounds((x+=ancho)+120, y, (ancho*=2.5)+80, alto);  
//		panel.add(cmbEstablecimientos).setBounds(x*4+75, y, 168, alto); 
		
		panel.add(scroll_grupos).setBounds(8, y+=20, anchop-25, altop-130);
		
		llamar_render();
		fh_in.setEnabled(false);
		spHoraIn.setEnabled(false);
		tiempodefault();
		
//		cmbEstablecimientos.addActionListener(op_filtro_establecimiento);
		txtFolioCorteFiltro.addKeyListener(op_filtro_folio_corte);
		txtAsignacion.addKeyListener(op_filtro_asignacion);
		txtCajero.addKeyListener(op_filtro_cajero); 
		
		btnGenerar.addActionListener(opGenerar);
//		agregar(tabla);

		cont.add(panel);
		
		this.setSize(anchop,altop);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
	}
	
	Date horaFinal = null;
	String fechaFinal_principal = "";
	@SuppressWarnings("deprecation")
	public void tiempodefault(){
		
		String[] fechas = new BuscarSQL().fechas_de_movimiento_de_cascos();
		
		try {
			fh_in.setDate((Date) new SimpleDateFormat("dd/MM/yyyy").parse(fechas[0].toString()));
			fechaFinal_principal=fechas[2].toString();
			fh_fin.setDate((Date) new SimpleDateFormat("dd/MM/yyyy").parse(fechaFinal_principal));
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		fechas[1].toString().split(":");
		String[] inicioDefault = fechas[1].toString().split(":");
		spHoraIn.setValue(new Time(Integer.parseInt(inicioDefault[0]),Integer.parseInt(inicioDefault[1]),Integer.parseInt(inicioDefault[2])));
		spHoraIn.setEditor(spDHoraIn);
		
		
		
		String[] FinDefault = fechas[3].toString().split(":");
		horaFinal = new Time(Integer.parseInt(FinDefault[0]),Integer.parseInt(FinDefault[1]),Integer.parseInt(inicioDefault[2]));
		spHoraFin.setValue(horaFinal);
		spHoraFin.setEditor(spDHoraFin);
		
	}
	
//	private void agregar(final JTable tbl) {
//        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
//	        public void mouseClicked(MouseEvent e) {
//	        	
//	        	if(e.getClickCount() == 2){
//	        		fila= tbl.getSelectedRow();
//	        		
////	        		String[] matriz = new BuscarTablasModel().permiso_para_revision_de_cortes();
////	        		
////				        	new Revisar__Y_Clasificar_Corte(f_corte,f_asignacion,fecha_corte,cajero,corte_sistema,diferencia,efectivo_corte,establecimiento, reviso_auditoria).setVisible(true);
////	        			}
//	        	}
//	        }
//        });
//    }
	
//	TODO (opGenerar)
	ActionListener opGenerar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			if(validar_fechas().equals("")){
				String fecha_inicio = new SimpleDateFormat("dd/MM/yyyy").format(fh_in.getDate())+" "+new SimpleDateFormat("HH:mm:ss").format(spHoraIn.getValue());
				String fecha_final = new SimpleDateFormat("dd/MM/yyyy").format(fh_fin.getDate())+" "+new SimpleDateFormat("HH:mm:ss").format(horaFinal.getTime());
				
				String fecha_final_principal = fechaFinal_principal+" "+new SimpleDateFormat("HH:mm:ss").format(((Date) spHoraFin.getValue()).getTime());
				
				  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 

				  Date fecha1 = sdf.parse(fecha_inicio , new ParsePosition(0));
				  Date fecha2 = sdf.parse(fecha_final , new ParsePosition(0));
				  Date fecha3 = sdf.parse(fecha_final_principal , new ParsePosition(0));

				if(fecha1.before(fecha2)){
					
						if(fecha3.before(fecha2)){	
							
//							System.out.println(horaFinal.getTime());
//							System.out.println(((Date) spHoraFin.getValue()).getTime());
//							
//							System.out.println(fecha_inicio);
//							System.out.println(fecha_final);
//							System.out.println(fecha_final_principal);
							
							
							modelo.setRowCount(0);
							String[][] llenarTabla = new BuscarSQL().buscarCascosPendientes(fecha_inicio, fecha_final);
							for(String[] casco: llenarTabla){
								modelo.addRow(casco);
							}
							
//							JOptionPane.showMessageDialog(null,"inicio="+fecha_inicio+"    fin="+fecha_final,"Aviso!", JOptionPane.WARNING_MESSAGE);
//							return;
						}else{
							JOptionPane.showMessageDialog(null,"No Se Puede Ingresar una fecha mas reciente","Aviso!", JOptionPane.WARNING_MESSAGE);
							return;
						}
					
				}else{
					JOptionPane.showMessageDialog(null,"El Rango de Fechas Esta Invertido","Aviso!", JOptionPane.WARNING_MESSAGE);
					return;
				}
			}else{
				JOptionPane.showMessageDialog(null,"Los siguientes campos están vacíos: "+validar_fechas(),"Aviso!", JOptionPane.ERROR_MESSAGE);
				return;
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
	
	public String validar_fechas(){
		String error = "";
		String fechaFinalNull = fh_fin.getDate()+"";
	    if(fechaFinalNull.equals("null"))error+= "Hora final\n";
	    
		return error;
	}
	
//	TODO Funcion (llamar_render())
	public void llamar_render(){
    
		Color fondoEncabezado_grupos = new Color(255,171,0);
		
		Color textoEncabezado = Color.black;
		
		tabla.getColumnModel().getColumn(0).setHeaderRenderer(new CaveceraTablaRenderer(fondoEncabezado_grupos,textoEncabezado,"centro","Arial","negrita",10));
		for(int i = 0; i<tabla.getColumnCount(); i++){
			
//			if(i==13 || i==26 || i==31){
//				tabla.getColumnModel().getColumn(i).setHeaderRenderer(new CaveceraTablaRenderer(Color.blue,Color.WHITE,"centro","Arial","negrita",10));
//			}else{
				tabla.getColumnModel().getColumn(i).setHeaderRenderer(new CaveceraTablaRenderer(fondoEncabezado_grupos,textoEncabezado,"centro","Arial","negrita",10));
//			}
			
			switch(i){
					case 0: tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",11)); break;
					case 1: tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","derecha","Arial","negrita",9)); break;
					case 2: tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",10)); break;
//					
					case 4: tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","derecha","Arial","negrita",10)); break;
					case 5: tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",10)); break;
					case 6: tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","derecha","Arial","negrita",10)); break;
					
					default: tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",11)); break;
				}
		}
		
		int x=110;
		
    	this.tabla.getTableHeader().setReorderingAllowed(false) ;
    	this.tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    	
    	this.tabla.getColumnModel().getColumn(0 ).setMaxWidth(x);   
    	this.tabla.getColumnModel().getColumn(0 ).setMinWidth(x);	
    	this.tabla.getColumnModel().getColumn(1 ).setMaxWidth(x);   
    	this.tabla.getColumnModel().getColumn(1 ).setMinWidth(x);   
    	this.tabla.getColumnModel().getColumn(2 ).setMaxWidth(x*4-20);
    	this.tabla.getColumnModel().getColumn(2 ).setMinWidth(x*4-20);
    	this.tabla.getColumnModel().getColumn(3 ).setMaxWidth(x+50); 
    	this.tabla.getColumnModel().getColumn(3 ).setMinWidth(x+50); 
    	                                               
    	this.tabla.getColumnModel().getColumn(4 ).setMaxWidth(x);
    	this.tabla.getColumnModel().getColumn(4 ).setMinWidth(x);		
    	this.tabla.getColumnModel().getColumn(5 ).setMaxWidth(x*4);
    	this.tabla.getColumnModel().getColumn(5 ).setMinWidth(x*4);
    	this.tabla.getColumnModel().getColumn(6 ).setMaxWidth(x);
    	this.tabla.getColumnModel().getColumn(6 ).setMinWidth(x);
    	this.tabla.getColumnModel().getColumn(7 ).setMaxWidth(x);
    	this.tabla.getColumnModel().getColumn(7 ).setMinWidth(x);
    	                                               
    }
	
//	TODO (ActionListener)
//	ActionListener op_filtro_establecimiento = new ActionListener(){
//		@SuppressWarnings("unchecked")
//		public void actionPerformed(ActionEvent arg0){
//			if(cmbEstablecimientos.getSelectedIndex() != 0){
//				trsfiltro.setRowFilter(RowFilter.regexFilter(cmbEstablecimientos.getSelectedItem()+"", 20));
//			}else{
//				trsfiltro.setRowFilter(RowFilter.regexFilter("", 2));
//			}
//		}
//	};
	
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
	
//	TODO metodo principal(main(String [] arg))
	public static void main(String[] args) {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Revision_De_Cascos().setVisible(true);
		}catch(Exception e){
			System.err.println("Error :"+ e.getMessage());
		}
	}
	
}
