package Cat_Auditoria;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import Conexiones_SQL.ActualizarSQL;
import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.BuscarTablasModel;
import Conexiones_SQL.Connexion;
import Conexiones_SQL.GuardarTablasModel;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_Filtro_Dinamico;
import Obj_Renders.CaveceraTablaRenderer;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Trabajos_Cortes extends JFrame{

	DecimalFormat df = new DecimalFormat("#0.00");
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
//	String establecimiento[] = new Obj_Establecimiento().Combo_Establecimiento();
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	JComboBox cmbEstablecimiento = new JComboBox(establecimiento);
	
	JButton btnRegresarCortes = new JButton("Restaurar Quitados");
	JButton btnQuitarSeleccionado = new JButton("Quitar Seleccionado",new ImageIcon("imagen/Delete.png"));
	JButton btnQuitarDesmarcados = new JButton("Quitar NO Seleccionado",new ImageIcon("imagen/Delete.png"));

	JButton btnRestaurar = new JButton("Restaurar", new ImageIcon("imagen/flecha-naranja-alerta-de-descarga-de-la-actualizacion-icono-8872-16.png"));
	JButton btnCV = new JButton("A Caja Verde",new ImageIcon("imagen/flecha-verde-icono-8451-16.png"));
	JButton btnReposicionEfec = new JButton("Reposicion De Efectivo");
	
	JButton btnGenerarUltimoTrabajo = new JButton("Reporte De Ultimo Trabajo",new ImageIcon("imagen/Report.png"));
	
	static JTextField txtFiltroPorAsignacion = new Componentes().text(new JCTextField(), "      Asignación", 15, "String");
	
	static JTextField txtDepositos = new JTextField();
	static JTextField txtIzacel = new JTextField();
	static JTextField txtEfectivoPlanes = new Componentes().text(new JTextField(), "Igresar Efectivo De Planes", 10, "Double");
	static JTextField txtPines = new JTextField();
	static JTextField txtCajaVerde = new JTextField();
	
	static JTextField txtTotalDelCorte= new JTextField();
	static JTextField txtTotalRetiroCliente = new JTextField();
	static JTextField txtTotalRecibosDeLuz = new JTextField();
	
	public static String cadenaCajaVerde(){
		String cadena = "";
			if(tabla_c_verde.getRowCount()>0){
					for(int i=0; i<tabla_c_verde.getRowCount(); i++){
						cadena+=tabla_c_verde.getValueAt(i, 1).toString().trim()+"'',''";
					}
				cadena=cadena.substring(0,cadena.length()-5);
			}
		return cadena;
	}
	
	public static String cadenaCortesQuitados(String marcados_vs_desmarcados){
		String cadena = "";
			if(tabla_grupos.getRowCount()>0){
					for(int i=0; i<tabla_model_grupos.getRowCount(); i++){
						if(tabla_grupos.getValueAt(i, 0).toString().trim().equals(marcados_vs_desmarcados)){
							cadena+=tabla_model_grupos.getValueAt(i, 2).toString().trim()+"'',''";
						}
					}
				cadena= cadena.equals("")?cadena:cadena.substring(0,cadena.length()-5);
			}
		return cadena;
	}
	
    public static DefaultTableModel tabla_model_grupos = new DefaultTableModel(null, new String[]{"*","Establecimiento","F.Corte", "F.Asignacion", "Cajero", "Total Efec.", "Retiros prog.", "Cheques", "Vales", "Dolares", "F.Sodas", "PIN-PAD", "Total Corte", "Diferencia", "Retiros Clt.", "TA", "R.Luz","Apartados", "Abono_Ahorro", "Fecha Corte", "Observacion"} ){
                    
		@SuppressWarnings({ "rawtypes" })
		Class[] types = new Class[]{
                   java.lang.Boolean.class,
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
    public boolean isCellEditable(int fila, int columna){
                switch(columna){
//                        case 0  : if(tabla_model_grupos.getValueAt(fila, 1).toString().trim().equals("IZACEL") || Double.valueOf(tabla_model_grupos.getValueAt(fila, 11).toString()) > 0){
//                        				return false; 
//			                        }else{
//			                        	return true; 
//			                        }      
                		case 0	: return true;
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
                }
                 return false;
         }
    };
	
    static JTable tabla_grupos = new JTable(tabla_model_grupos);
	JScrollPane scroll_grupos = new JScrollPane(tabla_grupos,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    public static DefaultTableModel tabla_model_c_verde = new DefaultTableModel(null, new String[]{"Establecimiento","F.Corte", "F.Asignacion", "Cajero", "Total Efec.", "Retiros prog.", "Cheques", "Vales", "Dolares", "F.Sodas", "PIN-PAD", "Total Corte", "Diferencia", "Retiros Clt.", "TA", "R.Luz","Apartados", "Abono_Ahorro", "Fecha Corte", "Observacion"} ){
        
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
                   java.lang.Object.class
                    
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
                }
                 return false;
         }
    };
	
    static JTable tabla_c_verde = new JTable(tabla_model_c_verde);
	JScrollPane scroll = new JScrollPane(tabla_c_verde,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    public static DefaultTableModel tabla_model_concentrado = new DefaultTableModel( null, new String[]{"Establecimiento", "Total Efec.", "Retiros prog.", "Cheques", "Vales", "Dolares", "F.Sodas", "PIN-PAD", "Total Corte", "Diferencia", "Retiros Clt.", "TA", "R.Luz","Apartados", "Abono_Ahorro"} ){
        
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
                   java.lang.Object.class
                    
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
                }
                 return false;
         }
    };
	
    JTable tabla_concentrado = new JTable(tabla_model_concentrado);
	JScrollPane scroll_concentrado = new JScrollPane(tabla_concentrado,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

	Border blackline;
	
	Object[] fila_cacl= new Object[15];
	
	static int folio_trabajo_realizado=0;
	static String grupo_corte="";
	
	public Cat_Trabajos_Cortes(String grupo_de_concentrado){
		
		tabla_model_c_verde.setRowCount(0);
		tabla_model_grupos.setRowCount(0);
		tabla_model_concentrado.setRowCount(0);
		
		grupo_corte = grupo_de_concentrado;
		txtEfectivoPlanes.setBackground(Color.lightGray);
		txtEfectivoPlanes.setBorder(BorderFactory.createLineBorder(Color.black));
		
//		if(new ActualizarSQL().actualizar_tabla_cortes_con_asignaciones()){ 
			refresh();
//			while(tabla_grupos.getRowCount()>0){
//				tabla_model_grupos.removeRow(0);
//			}
//				String[][] matriz = new BuscarTablasModel().tabla_model_trabajo_de_cortes(cadenaCajaVerde(),grupo_de_concentrado);
//	 
//				String[] fila = new String[20];
//		        for(int i=0; i<matriz.length; i++){
//		        	
//		        	for(int j=0; j<20; j++){
//		        		fila[j] = matriz[i][j]+"";
//		        	}
//		        		tabla_model_grupos.addRow(fila);
//		        }
//		}else{
//			JOptionPane.showMessageDialog(null, "No se validaron los folios de asignacion","Aviso",JOptionPane.WARNING_MESSAGE);
//			return;
//		}

		 
		 
		 
//		blackline = BorderFactory.createLineBorder(new Color(255,171,0));
		this.setTitle("Trabajos De Cortes");
		this.panel.setBorder(BorderFactory.createTitledBorder( "Concentrados De Cortes"));
		
//		trsfiltro = new TableRowSorter(tabla_model_grupos); 
//		tabla_grupos.setRowSorter(trsfiltro);  
		
		panel.add(btnRestaurar).setBounds(890, 10, 100, 20);
		panel.add(scroll).setBounds(20, 30, 970, 80);
		
//		panel.add(cmbEstablecimiento).setBounds(45, 165, 160, 20);
		panel.add(btnRegresarCortes).setBounds(400, 115, 130, 20);
		panel.add(btnQuitarSeleccionado).setBounds(545, 115, 150, 20);
		panel.add(btnQuitarDesmarcados).setBounds(710, 115, 150, 20);
		panel.add(btnCV).setBounds(875, 115, 115, 20);
		
		panel.add(txtFiltroPorAsignacion).setBounds(275, 115, 110, 20);
		panel.add(scroll_grupos).setBounds(20, 135, 970, 290);
//		panel.add(btnCalcular).setBounds(20, 0, 160, 20);
		panel.add(scroll_concentrado).setBounds(20, 430, 970, 130);
		
		int x=20, y= 565,ancho=90;
		
		panel.add(new JLabel("DEPOSITOS: ")	 ).setBounds(x, y, ancho, 20);           panel.add(new JLabel("TOTAL DEL CORTE: ")		).setBounds(x+250, y, ancho+50, 20);       
		panel.add(txtDepositos 				 ).setBounds(x+ancho+10, y, ancho, 20);  panel.add(txtTotalDelCorte						).setBounds(x+ancho+320, y, ancho, 20);    			panel.add(btnGenerarUltimoTrabajo).setBounds(x+ancho+700, y, ancho+90, 40);
		panel.add(new JLabel("IZACEL: ")	 ).setBounds(x, y+=22, ancho, 20);       panel.add(new JLabel("TOTAL RETIRO CLIENTES: ")).setBounds(x+250, y, ancho+50, 20);    
		panel.add(txtIzacel 				 ).setBounds(x+ancho+10, y, ancho, 20);  panel.add(txtTotalRetiroCliente				).setBounds(x+ancho+320, y, ancho, 20);  
		panel.add(new JLabel("PLAN TELCEL: ")).setBounds(x, y+=22, ancho, 20);       panel.add(new JLabel("TOTAL RECIBOS DE LUZ: ")	).setBounds(x+250, y, ancho+50, 20);    
		panel.add(txtEfectivoPlanes			 ).setBounds(x+ancho+10, y, ancho, 20);  panel.add(txtTotalRecibosDeLuz 				).setBounds(x+ancho+320, y, ancho, 20);  
		panel.add(new JLabel("PINES: ")		 ).setBounds(x, y+=22, ancho, 20);       panel.add(btnReposicionEfec					).setBounds(x+ancho+250, y, ancho+70, 30);        
		panel.add(txtPines 					 ).setBounds(x+ancho+10, y, ancho, 20);             
		panel.add(new JLabel("CAJA VERDE: ") ).setBounds(x, y+=22, ancho, 20);
		panel.add(txtCajaVerde				 ).setBounds(x+ancho+10, y, ancho, 20);
		
		llamar_render();
		calcular();
		calcular_totales();
		
//		cmbEstablecimiento.addActionListener(opFiltro);
		btnCV.addActionListener(opCajaVerde);
		btnRestaurar.addActionListener(opRestaurar);
		btnReposicionEfec.addActionListener(opGenerar);
		
		opQuitar(btnQuitarDesmarcados);
		opQuitar(btnQuitarSeleccionado);
		btnRegresarCortes.addActionListener(opRegresarCorte);
		btnGenerarUltimoTrabajo.addActionListener(opReimprimirUltimoTrabajo);
		
		txtEfectivoPlanes.addKeyListener(opTeclearPlanes);
		txtFiltroPorAsignacion.addKeyListener(opFiltroDinamico);
		
		agregar(tabla_grupos);
		
		cont.add(panel);
		
		txtDepositos.setEditable(false); 			
		txtIzacel.setEditable(false);			
		txtPines.setEditable(false);
		txtCajaVerde.setEditable(false);
		txtTotalDelCorte.setEditable(false);
		txtTotalRetiroCliente.setEditable(false);
		txtTotalRecibosDeLuz.setEditable(false);
		
//      asigna el foco al JTextField deseado al arrancar la ventana
        this.addWindowListener(new WindowAdapter() {
                public void windowOpened( WindowEvent e ){
                	txtFiltroPorAsignacion.requestFocus();
             }
        });

		this.setSize(1024,710);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
	}
	
	public void agregar(final JTable tb){
		tb.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				
				int fila = tb.getSelectedRow();
				
				if(e.getClickCount()==2){
					new Cat_Modificacion_De_Corte(tb.getValueAt(fila, 2).toString().trim()//folio corte
													,tb.getValueAt(fila, 3).toString().trim()//folio asignacion
													,tb.getValueAt(fila, 4).toString().trim()//nombre cajero
													,tb.getValueAt(fila, 5).toString().trim() //total efectivo
													,tb.getValueAt(fila, 6).toString().trim() //retiros programados
													,tb.getValueAt(fila, 7).toString().trim() //cheques
													,tb.getValueAt(fila, 8).toString().trim() //vales
													,tb.getValueAt(fila, 9).toString().trim() //dolares
													,tb.getValueAt(fila, 10).toString().trim()//fuente de sodas
													,tb.getValueAt(fila, 11).toString().trim()).setVisible(true);//pin pad
				}
			}
		});
		
	}
	
	KeyListener opFiltroDinamico = new KeyListener(){
		public void keyReleased(KeyEvent arg0) {
			
			new Obj_Filtro_Dinamico(tabla_grupos,"F.Asignacion", txtFiltroPorAsignacion.getText().toUpperCase(),"","", "", "", "", "");
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}		
	};
	
	@SuppressWarnings("static-access")
	public void llamar_render(){
		//		tipo de valor = imagen,chb,texto
//		tabla.getColumnModel().getColumn(# columna).setCellRenderer(new CellRenderer("tipo_de_valor","alineacion","tipo_de_letra","negrita",# tamanio_fuente));
    
		Color fondoEncabezado_c_verde = new Color(3,178,47);
		Color fondoEncabezado_grupos = new Color(255,171,0);
		Color fondoEncabezado_concentrado = new Color(0,154,250);
		
		Color textoEncabezado = Color.black;
		
//		tabla_c_verde.getColumnModel().getColumn(0).setHeaderRenderer(new CaveceraTablaRenderer(fondoEncabezado_c_verde,textoEncabezado,"centro","Arial","negrita",10));
//		tabla_c_verde.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("CHB","izquierda","Arial","negrita",10));
		for(int i = 0; i<tabla_c_verde.getColumnCount(); i++){
			tabla_c_verde.getColumnModel().getColumn(i).setHeaderRenderer(new CaveceraTablaRenderer(fondoEncabezado_c_verde,textoEncabezado,"centro","Arial","negrita",10));
			
			switch(i){
//					case 0: tabla_c_verde.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",11)); break;
					case 0: tabla_c_verde.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",9)); break;
					case 1: tabla_c_verde.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",10)); break;
					case 2: tabla_c_verde.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",9)); break;
					case 3: tabla_c_verde.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",10)); break;
					case 16:tabla_c_verde.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",11)); break;
					case 18:tabla_c_verde.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",11)); break;
					default: tabla_c_verde.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","derecha","Arial","negrita",11)); break;
				}
		}
		
		tabla_grupos.getColumnModel().getColumn(0).setHeaderRenderer(new CaveceraTablaRenderer(fondoEncabezado_grupos,textoEncabezado,"centro","Arial","negrita",10));
		tabla_grupos.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("CHB","izquierda","Arial","negrita",10));
		for(int i = 1; i<tabla_grupos.getColumnCount(); i++){
			tabla_grupos.getColumnModel().getColumn(i).setHeaderRenderer(new CaveceraTablaRenderer(fondoEncabezado_grupos,textoEncabezado,"centro","Arial","negrita",10));
			
			switch(i){
//					case 0: tabla_grupos.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","centro"	,"Arial","negrita",11)); break;
					case 1: tabla_grupos.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",9) ); break;
					case 2: tabla_grupos.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",10)); break;
					case 3: tabla_grupos.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",9) ); break;
					case 4: tabla_grupos.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",10)); break;
					case 19:tabla_grupos.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","centro"	,"Arial","negrita",11)); break;
					case 20:tabla_grupos.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",11)); break;
					default: tabla_grupos.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","derecha"	,"Arial","negrita",11)); break;
				}
		}
		
		
		
		tabla_concentrado.getColumnModel().getColumn(0).setHeaderRenderer(new CaveceraTablaRenderer(fondoEncabezado_concentrado,textoEncabezado,"centro","Arial","negrita",10));
		tabla_concentrado.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",10));
		for(int i = 1; i<tabla_concentrado.getColumnCount(); i++){
			tabla_concentrado.getColumnModel().getColumn(i).setHeaderRenderer(new CaveceraTablaRenderer(fondoEncabezado_concentrado,textoEncabezado,"centro","Arial","negrita",10));
			
			switch(i){
					case 0: tabla_concentrado.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",11)); break;
					case 1: tabla_concentrado.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",11)); break;
					case 2: tabla_concentrado.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",11)); break;
					case 3: tabla_concentrado.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",11)); break;
					case 4: tabla_concentrado.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",11)); break;
					default: tabla_concentrado.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","derecha","Arial","negrita",11)); break;
				}
		}
    	
		int x=70;
		
    	this.tabla_c_verde.getTableHeader().setReorderingAllowed(false) ;
    	this.tabla_c_verde.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    	
    	this.tabla_c_verde.getColumnModel().getColumn(0 ).setMaxWidth(x*3-50);
    	this.tabla_c_verde.getColumnModel().getColumn(0 ).setMinWidth(x*3-50);
    	this.tabla_c_verde.getColumnModel().getColumn(1 ).setMaxWidth(x);
    	this.tabla_c_verde.getColumnModel().getColumn(1 ).setMinWidth(x);
    	this.tabla_c_verde.getColumnModel().getColumn(2 ).setMaxWidth(x+40);
    	this.tabla_c_verde.getColumnModel().getColumn(2 ).setMinWidth(x+40);
    	                                               
    	this.tabla_c_verde.getColumnModel().getColumn(3 ).setMaxWidth(x*4);
    	this.tabla_c_verde.getColumnModel().getColumn(3 ).setMinWidth(x*4);		
    	this.tabla_c_verde.getColumnModel().getColumn(4 ).setMaxWidth(x);
    	this.tabla_c_verde.getColumnModel().getColumn(4 ).setMinWidth(x);
    	this.tabla_c_verde.getColumnModel().getColumn(5 ).setMaxWidth(x);
    	this.tabla_c_verde.getColumnModel().getColumn(5 ).setMinWidth(x);
    	this.tabla_c_verde.getColumnModel().getColumn(6 ).setMaxWidth(x);
    	this.tabla_c_verde.getColumnModel().getColumn(6 ).setMinWidth(x);
    	                                                
    	this.tabla_c_verde.getColumnModel().getColumn(7 ).setMaxWidth(x);
    	this.tabla_c_verde.getColumnModel().getColumn(7 ).setMinWidth(x);		
    	this.tabla_c_verde.getColumnModel().getColumn(8 ).setMaxWidth(x);
    	this.tabla_c_verde.getColumnModel().getColumn(8 ).setMinWidth(x);
    	this.tabla_c_verde.getColumnModel().getColumn(9 ).setMaxWidth(x);
    	this.tabla_c_verde.getColumnModel().getColumn(9 ).setMinWidth(x);
    	this.tabla_c_verde.getColumnModel().getColumn(10).setMaxWidth(x);
    	this.tabla_c_verde.getColumnModel().getColumn(10).setMinWidth(x);
    	
    	this.tabla_c_verde.getColumnModel().getColumn(11).setMaxWidth(x);
    	this.tabla_c_verde.getColumnModel().getColumn(11).setMinWidth(x);		
    	this.tabla_c_verde.getColumnModel().getColumn(12).setMaxWidth(x);
    	this.tabla_c_verde.getColumnModel().getColumn(12).setMinWidth(x);
    	this.tabla_c_verde.getColumnModel().getColumn(13).setMaxWidth(x);
    	this.tabla_c_verde.getColumnModel().getColumn(13).setMinWidth(x);
    	this.tabla_c_verde.getColumnModel().getColumn(14).setMaxWidth(x);
    	this.tabla_c_verde.getColumnModel().getColumn(14).setMinWidth(x);
    	this.tabla_c_verde.getColumnModel().getColumn(15).setMaxWidth(x);
    	this.tabla_c_verde.getColumnModel().getColumn(15).setMinWidth(x);
    	this.tabla_c_verde.getColumnModel().getColumn(16).setMaxWidth(x);
    	this.tabla_c_verde.getColumnModel().getColumn(16).setMinWidth(x);
    	this.tabla_c_verde.getColumnModel().getColumn(17).setMaxWidth(x*2);
    	this.tabla_c_verde.getColumnModel().getColumn(17).setMinWidth(x*2);
    	this.tabla_c_verde.getColumnModel().getColumn(18).setMaxWidth(x*5);
    	this.tabla_c_verde.getColumnModel().getColumn(18).setMinWidth(x*5);
    	
    	this.tabla_grupos.getTableHeader().setReorderingAllowed(false) ;
    	this.tabla_grupos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    	
    	this.tabla_grupos.getColumnModel().getColumn(0 ).setMaxWidth(25);   
    	this.tabla_grupos.getColumnModel().getColumn(0 ).setMinWidth(25);	
    	this.tabla_grupos.getColumnModel().getColumn(1 ).setMaxWidth(x*3-50);   
    	this.tabla_grupos.getColumnModel().getColumn(1 ).setMinWidth(x*3-50);   
    	this.tabla_grupos.getColumnModel().getColumn(2 ).setMaxWidth(x);
    	this.tabla_grupos.getColumnModel().getColumn(2 ).setMinWidth(x);
    	this.tabla_grupos.getColumnModel().getColumn(3 ).setMaxWidth(x+40); 
    	this.tabla_grupos.getColumnModel().getColumn(3 ).setMinWidth(x+40); 
    	                                               
    	this.tabla_grupos.getColumnModel().getColumn(4 ).setMaxWidth(x*4);
    	this.tabla_grupos.getColumnModel().getColumn(4 ).setMinWidth(x*3);		
    	this.tabla_grupos.getColumnModel().getColumn(5 ).setMaxWidth(x);
    	this.tabla_grupos.getColumnModel().getColumn(5 ).setMinWidth(x);
    	this.tabla_grupos.getColumnModel().getColumn(6 ).setMaxWidth(x);
    	this.tabla_grupos.getColumnModel().getColumn(6 ).setMinWidth(x);
    	this.tabla_grupos.getColumnModel().getColumn(7 ).setMaxWidth(x);
    	this.tabla_grupos.getColumnModel().getColumn(7 ).setMinWidth(x);
    	                                               
    	this.tabla_grupos.getColumnModel().getColumn(8 ).setMaxWidth(x);
    	this.tabla_grupos.getColumnModel().getColumn(8 ).setMinWidth(x);		
    	this.tabla_grupos.getColumnModel().getColumn(9 ).setMaxWidth(x);
    	this.tabla_grupos.getColumnModel().getColumn(9 ).setMinWidth(x);
    	this.tabla_grupos.getColumnModel().getColumn(10).setMaxWidth(x);
    	this.tabla_grupos.getColumnModel().getColumn(10).setMinWidth(x);
    	this.tabla_grupos.getColumnModel().getColumn(11).setMaxWidth(x);
    	this.tabla_grupos.getColumnModel().getColumn(11).setMinWidth(x);
    	
    	this.tabla_grupos.getColumnModel().getColumn(12).setMaxWidth(x);
    	this.tabla_grupos.getColumnModel().getColumn(12).setMinWidth(x);		
    	this.tabla_grupos.getColumnModel().getColumn(13).setMaxWidth(x);
    	this.tabla_grupos.getColumnModel().getColumn(13).setMinWidth(x);
    	this.tabla_grupos.getColumnModel().getColumn(14).setMaxWidth(x);
    	this.tabla_grupos.getColumnModel().getColumn(14).setMinWidth(x);
    	this.tabla_grupos.getColumnModel().getColumn(15).setMaxWidth(x);
    	this.tabla_grupos.getColumnModel().getColumn(15).setMinWidth(x);
    	this.tabla_grupos.getColumnModel().getColumn(16).setMaxWidth(x);
    	this.tabla_grupos.getColumnModel().getColumn(16).setMinWidth(x);
    	this.tabla_grupos.getColumnModel().getColumn(17).setMaxWidth(x);
    	this.tabla_grupos.getColumnModel().getColumn(17).setMinWidth(x);
    	this.tabla_grupos.getColumnModel().getColumn(18).setMaxWidth(x+20);
    	this.tabla_grupos.getColumnModel().getColumn(18).setMinWidth(x+20);
    	this.tabla_grupos.getColumnModel().getColumn(19).setMaxWidth(x*2);
    	this.tabla_grupos.getColumnModel().getColumn(19).setMinWidth(x*2);
    	this.tabla_grupos.getColumnModel().getColumn(20).setMaxWidth(x*5);
    	this.tabla_grupos.getColumnModel().getColumn(20).setMinWidth(x*5);
    	
    	this.tabla_concentrado.getTableHeader().setReorderingAllowed(false) ;
    	this.tabla_concentrado.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    	
    	this.tabla_concentrado.getColumnModel().getColumn(0 ).setMaxWidth(x*3-20);
    	this.tabla_concentrado.getColumnModel().getColumn(0 ).setMinWidth(x*3-20);
    	this.tabla_concentrado.getColumnModel().getColumn(1 ).setMaxWidth(x);
    	this.tabla_concentrado.getColumnModel().getColumn(1 ).setMinWidth(x);
    	this.tabla_concentrado.getColumnModel().getColumn(2 ).setMaxWidth(x+10);
    	this.tabla_concentrado.getColumnModel().getColumn(2 ).setMinWidth(x+10);
    	                                                    
    	this.tabla_concentrado.getColumnModel().getColumn(3 ).setMaxWidth(x);
    	this.tabla_concentrado.getColumnModel().getColumn(3 ).setMinWidth(x);		
    	this.tabla_concentrado.getColumnModel().getColumn(4 ).setMaxWidth(x);
    	this.tabla_concentrado.getColumnModel().getColumn(4 ).setMinWidth(x);
    	this.tabla_concentrado.getColumnModel().getColumn(5 ).setMaxWidth(x);
    	this.tabla_concentrado.getColumnModel().getColumn(5 ).setMinWidth(x);
    	this.tabla_concentrado.getColumnModel().getColumn(6 ).setMaxWidth(x);
    	this.tabla_concentrado.getColumnModel().getColumn(6 ).setMinWidth(x);
    	                                                    
    	this.tabla_concentrado.getColumnModel().getColumn(7 ).setMaxWidth(x);
    	this.tabla_concentrado.getColumnModel().getColumn(7 ).setMinWidth(x);		
    	this.tabla_concentrado.getColumnModel().getColumn(8 ).setMaxWidth(x);
    	this.tabla_concentrado.getColumnModel().getColumn(8 ).setMinWidth(x);
    	this.tabla_concentrado.getColumnModel().getColumn(9 ).setMaxWidth(x);
    	this.tabla_concentrado.getColumnModel().getColumn(9 ).setMinWidth(x);
    	this.tabla_concentrado.getColumnModel().getColumn(10).setMaxWidth(x);
    	this.tabla_concentrado.getColumnModel().getColumn(10).setMinWidth(x);
    	
    	this.tabla_concentrado.getColumnModel().getColumn(11).setMaxWidth(x);
    	this.tabla_concentrado.getColumnModel().getColumn(11).setMinWidth(x);		
    	this.tabla_concentrado.getColumnModel().getColumn(12).setMaxWidth(x);
    	this.tabla_concentrado.getColumnModel().getColumn(12).setMinWidth(x);
    	this.tabla_concentrado.getColumnModel().getColumn(13).setMaxWidth(x);
    	this.tabla_concentrado.getColumnModel().getColumn(13).setMinWidth(x);
    }
	
	ActionListener opCajaVerde = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
		
			txtFiltroPorAsignacion.setText("");
			new Obj_Filtro_Dinamico(tabla_grupos,"F.Asignacion", txtFiltroPorAsignacion.getText().toUpperCase(),"","", "", "", "", "");
			
			Object[] vector = new Object[20];
			
			for(int i =0; i<tabla_grupos.getRowCount(); i++){
				
				if(tabla_grupos.getValueAt(i, 0).toString().trim().equals("true")){
					
					for(int j=0; j<tabla_grupos.getColumnCount()-1; j++){
							vector[j] = tabla_grupos.getValueAt(i, j+1);
					}
					tabla_model_c_verde.addRow(vector);
				}
			}
			refresh();
		}
	};
	
	ActionListener opRestaurar = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			
			int filaSeleccionada = tabla_c_verde.getSelectedRow();
			
			if(filaSeleccionada<0){
				JOptionPane.showMessageDialog(null, "No se ha seleccionado ningun registro de la caja verde","Aviso",JOptionPane.WARNING_MESSAGE);
				return;
			}else{
				tabla_model_c_verde.removeRow(filaSeleccionada);
				
				txtFiltroPorAsignacion.setText("");
				new Obj_Filtro_Dinamico(tabla_grupos,"F.Asignacion", txtFiltroPorAsignacion.getText().toUpperCase(),"","", "", "", "", "");
				refresh();
			}
		}
	};
	
	ActionListener opGenerar = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			
		if(!txtFiltroPorAsignacion.getText().equals("")){
			txtFiltroPorAsignacion.setText("");
			new Obj_Filtro_Dinamico(tabla_grupos,"F.Asignacion", txtFiltroPorAsignacion.getText().toUpperCase(),"","", "", "", "", "");
			JOptionPane.showMessageDialog(null, "El Filtro De Asignacion Se Encontrava Con Texto y Fue Limpiado, Favor De Intentar De Nuevo","Aviso",JOptionPane.WARNING_MESSAGE);
			return;
		}else{
			
			int bandera = 0;
			for(int i=0; i<tabla_grupos.getRowCount(); i++){
				if(tabla_grupos.getValueAt(i, 0).toString().trim().equals("true")){
					bandera++;
				}
			}
			
			if(bandera>0){
				JOptionPane.showMessageDialog(null, "Existen Cortes Seleccionados En La Tabla, Para Continuar\nCon El Trabajo No Deben Haber Cortes Seleccionados","Aviso",JOptionPane.WARNING_MESSAGE);
				return;
			}else{
					if(txtEfectivoPlanes.getText().equals("")){
						JOptionPane.showMessageDialog(null, "El Campo Planes Se encuentra Vacio.\nIngrese Una Cantidad Para Seguir","Aviso",JOptionPane.WARNING_MESSAGE);
						return;
					}else{
						
							double tFS=0;
							double tVales=0;
							double tDolares=0;
							double tDiferencia=0;
							double tCorteCV=0;
							double tRetirosClientes=0;
							
							Object[][] lista_cv = new Object[tabla_c_verde.getRowCount()][2];
							for(int i = 0; i<tabla_c_verde.getRowCount(); i++){
								
								lista_cv[i][0] = tabla_c_verde.getValueAt(i, 2).toString().trim();
								lista_cv[i][1] = tabla_c_verde.getValueAt(i, 11).toString().trim();
								
							}
							
							for(int i=0; i<tabla_concentrado.getRowCount(); i++){
								
								tVales		+=	Double.valueOf(tabla_concentrado.getValueAt(i, 4).toString().trim());
								tDolares	+=	Double.valueOf(tabla_concentrado.getValueAt(i, 5).toString().trim());
								tFS			+=	Double.valueOf(tabla_concentrado.getValueAt(i, 6).toString().trim());
								tDiferencia	+=	Double.valueOf(tabla_concentrado.getValueAt(i, 9).toString().trim());
								
								if(tabla_concentrado.getValueAt(i, 0).toString().trim().equals("TOTAL CAJA VERDE")){
										tCorteCV	+=	Double.valueOf(tabla_concentrado.getValueAt(i,8).toString().trim());
										tRetirosClientes +=	Double.valueOf(tabla_concentrado.getValueAt(i, 10).toString().trim());
								}
								//Total dato

							}
							new Cat_Reposicion_De_Efectivo(df.format(tFS),df.format(tDolares),df.format(tDiferencia),df.format(tCorteCV),df.format(tVales),txtEfectivoPlanes.getText(), lista_cv, df.format(tRetirosClientes)).setVisible(true);
					}
			}
		}
	}
};

	String quitarCortes="";
	
	public void opQuitar(final JButton btn){
		
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				txtFiltroPorAsignacion.setText("");
				new Obj_Filtro_Dinamico(tabla_grupos,"F.Asignacion", txtFiltroPorAsignacion.getText().toUpperCase(),"","", "", "", "", "");
				
//				System.out.print(btn.getActionCommand());
				if(btn.getActionCommand().equals("Quitar Seleccionado")){
					quitarCortes=quitarCortes.equals("")?cadenaCortesQuitados("true"):quitarCortes+"'',''"+cadenaCortesQuitados("true");
				}else{
					quitarCortes=quitarCortes.equals("")?cadenaCortesQuitados("false"):quitarCortes+"'',''"+cadenaCortesQuitados("false");
				}
				refresh();
			}
		});
		
	}
	
	ActionListener opReimprimirUltimoTrabajo = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			
			new Cat_Reporte_De_Concentrado_De_Cortes(grupo_corte).setVisible(true);
		}
	};

	ActionListener opRegresarCorte = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			
			
			new Cat_Cortes_Quitados().setVisible(true);
			
//			quitarCortes="";
//			
//			txtFiltroPorAsignacion.setText("");
//			new Obj_Filtro_Dinamico(tabla_grupos,"F.Asignacion", txtFiltroPorAsignacion.getText().toUpperCase(),"","", "", "", "", "");
//			
//			refresh();
		}
	};
	
	public void refresh(){
		
		txtFiltroPorAsignacion.setText("");
		tabla_model_grupos.setRowCount(0);
		
		System.out.println(cadenaCajaVerde().equals("") ? quitarCortes : (quitarCortes.equals("")?cadenaCajaVerde():cadenaCajaVerde()+"'',''"+quitarCortes));
		
		String[][] matriz = new BuscarTablasModel().tabla_model_trabajo_de_cortes(cadenaCajaVerde().equals("") ? quitarCortes : (quitarCortes.equals("")?cadenaCajaVerde():cadenaCajaVerde()+"'',''"+quitarCortes), grupo_corte);
		for(String[] fila: matriz){
			tabla_model_grupos.addRow(fila);
		}

			calcular();
			calcular_caja_verde();
			calcular_totales();
	}
	
	public void calcular(){
		
		String estab     ="";
		String estab_aux ="";
		
		double t_efect   = 0;
		double r_prog    = 0;
		double cheques   = 0;
		double vales     = 0;
		double dolares   = 0;
		double f_sodas   = 0;
		double pin_pad   = 0;
		double t_corte   = 0;
		double dif       = 0;
		double r_clt     = 0;
		double ta        = 0;
		double luz       = 0;
		double apartados = 0;
		double abono_ahorro = 0;
		
		tabla_model_concentrado.setRowCount(0);
		
		if(tabla_grupos.getRowCount()<=0){
			
			tabla_model_concentrado.setRowCount(0);
				JOptionPane.showMessageDialog(null, "No existen cortes en el concentrado seleccionado, por lo cual no puede realizarce un calculo","Aviso",JOptionPane.WARNING_MESSAGE);
				return;
		}else{
			
			estab = tabla_grupos.getValueAt(0, 1).toString().trim();
			
				for(int i =0; i<tabla_grupos.getRowCount(); i++){
					
					estab_aux = estab;
					estab = tabla_grupos.getValueAt(i, 1).toString().trim();
					
					if(estab.equals(estab_aux)){
						
						t_efect 	+= Double.valueOf(tabla_grupos.getValueAt(i, 5 ).toString().trim());
						r_prog 		+= Double.valueOf(tabla_grupos.getValueAt(i, 6 ).toString().trim());
						cheques 	+= Double.valueOf(tabla_grupos.getValueAt(i, 7 ).toString().trim());
						vales 		+= Double.valueOf(tabla_grupos.getValueAt(i, 8 ).toString().trim());
						dolares 	+= Double.valueOf(tabla_grupos.getValueAt(i, 9 ).toString().trim());
						f_sodas 	+= Double.valueOf(tabla_grupos.getValueAt(i, 10).toString().trim());
						pin_pad 	+= Double.valueOf(tabla_grupos.getValueAt(i, 11).toString().trim());
						t_corte 	+= Double.valueOf(tabla_grupos.getValueAt(i, 12).toString().trim());
						dif 		+= Double.valueOf(tabla_grupos.getValueAt(i, 13).toString().trim());
						r_clt 		+= Double.valueOf(tabla_grupos.getValueAt(i, 14).toString().trim());
						ta 			+= Double.valueOf(tabla_grupos.getValueAt(i, 15).toString().trim());
						luz 		+= Double.valueOf(tabla_grupos.getValueAt(i, 16).toString().trim());
						apartados 	+= Double.valueOf(tabla_grupos.getValueAt(i, 17).toString().trim());
						abono_ahorro+= Double.valueOf(tabla_grupos.getValueAt(i, 18).toString().trim());
						
							if(i==tabla_grupos.getRowCount()-1){
								
								fila_cacl[0] = estab_aux.toString() ;
								fila_cacl[1] = df.format(t_efect    )    ; 
								fila_cacl[2] = df.format(r_prog     )    ; 
								fila_cacl[3] = df.format(cheques    )    ; 
								fila_cacl[4] = df.format(vales      )    ; 
								fila_cacl[5] = df.format(dolares    )    ; 
								fila_cacl[6] = df.format(f_sodas    )    ; 
								fila_cacl[7] = df.format(pin_pad    )    ; 
								fila_cacl[8] = df.format(t_corte    )    ; 
								fila_cacl[9] = df.format(dif        )    ; 
								fila_cacl[10] =df.format(r_clt      )    ; 
								fila_cacl[11] =df.format(ta         )    ; 
								fila_cacl[12] =df.format(luz        )    ; 
								fila_cacl[13] =df.format(apartados  )    ;
								fila_cacl[14] =df.format(abono_ahorro)   ;
								tabla_model_concentrado.addRow(fila_cacl);
							}
					}else{
						
						fila_cacl[0]  = estab_aux ;
						fila_cacl[1] = df.format(t_efect    )    ; 
						fila_cacl[2] = df.format(r_prog     )    ; 
						fila_cacl[3] = df.format(cheques    )    ; 
						fila_cacl[4] = df.format(vales      )    ; 
						fila_cacl[5] = df.format(dolares    )    ; 
						fila_cacl[6] = df.format(f_sodas    )    ; 
						fila_cacl[7] = df.format(pin_pad    )    ; 
						fila_cacl[8] = df.format(t_corte    )    ; 
						fila_cacl[9] = df.format(dif        )    ; 
						fila_cacl[10] =df.format(r_clt      )    ; 
						fila_cacl[11] =df.format(ta         )    ; 
						fila_cacl[12] =df.format(luz        )    ; 
						fila_cacl[13] =df.format(apartados  )    ; 
						fila_cacl[14] =df.format(abono_ahorro)   ;
						tabla_model_concentrado.addRow(fila_cacl);
						
						t_efect   = 0;
						r_prog    = 0;
						cheques   = 0;
						vales     = 0;
						dolares   = 0;
						f_sodas   = 0;
						pin_pad   = 0;
						t_corte   = 0;
						dif       = 0;
						r_clt     = 0;
						ta        = 0;
						luz       = 0;
						apartados = 0;
						abono_ahorro=0;
						
						t_efect 	+= Double.valueOf(tabla_grupos.getValueAt(i, 5 ).toString().trim());
						r_prog 		+= Double.valueOf(tabla_grupos.getValueAt(i, 6 ).toString().trim());
						cheques 	+= Double.valueOf(tabla_grupos.getValueAt(i, 7 ).toString().trim());
						vales 		+= Double.valueOf(tabla_grupos.getValueAt(i, 8 ).toString().trim());
						dolares 	+= Double.valueOf(tabla_grupos.getValueAt(i, 9 ).toString().trim());
						f_sodas 	+= Double.valueOf(tabla_grupos.getValueAt(i, 10).toString().trim());
						pin_pad 	+= Double.valueOf(tabla_grupos.getValueAt(i, 11).toString().trim());
						t_corte 	+= Double.valueOf(tabla_grupos.getValueAt(i, 12).toString().trim());
						dif 		+= Double.valueOf(tabla_grupos.getValueAt(i, 13).toString().trim());
						r_clt 		+= Double.valueOf(tabla_grupos.getValueAt(i, 14).toString().trim());
						ta 			+= Double.valueOf(tabla_grupos.getValueAt(i, 15).toString().trim());
						luz 		+= Double.valueOf(tabla_grupos.getValueAt(i, 16).toString().trim());
						apartados 	+= Double.valueOf(tabla_grupos.getValueAt(i, 17).toString().trim());
						abono_ahorro+= Double.valueOf(tabla_grupos.getValueAt(i, 18).toString().trim());
						
						if(i==tabla_grupos.getRowCount()-1){
							fila_cacl[0]  = estab ;
							fila_cacl[1] = df.format(t_efect    )    ; 
							fila_cacl[2] = df.format(r_prog     )    ; 
							fila_cacl[3] = df.format(cheques    )    ; 
							fila_cacl[4] = df.format(vales      )    ; 
							fila_cacl[5] = df.format(dolares    )    ; 
							fila_cacl[6] = df.format(f_sodas    )    ; 
							fila_cacl[7] = df.format(pin_pad    )    ; 
							fila_cacl[8] = df.format(t_corte    )    ; 
							fila_cacl[9] = df.format(dif        )    ; 
							fila_cacl[10] =df.format(r_clt      )    ; 
							fila_cacl[11] =df.format(ta         )    ; 
							fila_cacl[12] =df.format(luz        )    ; 
							fila_cacl[13] =df.format(apartados  )    ;
							fila_cacl[14] =df.format(abono_ahorro)   ;
							tabla_model_concentrado.addRow(fila_cacl);
						}
						
					}
				}
		}
	}
	
	public void calcular_caja_verde(){
		
		double t_efect   = 0;
		double r_prog    = 0;
		double cheques   = 0;
		double vales     = 0;
		double dolares   = 0;
		double f_sodas   = 0;
		double pin_pad   = 0;
		double t_corte   = 0;
		double dif       = 0;
		double r_clt     = 0;
		double ta        = 0;
		double luz       = 0;
		double apartados = 0;
		double abono_ahorro=0;
		
		if(tabla_c_verde.getRowCount()>0){
			
				for(int i =0; i<tabla_c_verde.getRowCount(); i++){
					
						t_efect 	+= Double.valueOf(tabla_c_verde.getValueAt(i, 4 ).toString().trim());
						r_prog 		+= Double.valueOf(tabla_c_verde.getValueAt(i, 5 ).toString().trim());
						cheques 	+= Double.valueOf(tabla_c_verde.getValueAt(i, 6 ).toString().trim());
						vales 		+= Double.valueOf(tabla_c_verde.getValueAt(i, 7 ).toString().trim());
						dolares 	+= Double.valueOf(tabla_c_verde.getValueAt(i, 8 ).toString().trim());
						f_sodas 	+= Double.valueOf(tabla_c_verde.getValueAt(i, 9).toString().trim());
						pin_pad 	+= Double.valueOf(tabla_c_verde.getValueAt(i, 10).toString().trim());
						t_corte 	+= Double.valueOf(tabla_c_verde.getValueAt(i, 11).toString().trim());
						dif 		+= Double.valueOf(tabla_c_verde.getValueAt(i, 12).toString().trim());
						r_clt 		+= Double.valueOf(tabla_c_verde.getValueAt(i, 13).toString().trim());
						ta 			+= Double.valueOf(tabla_c_verde.getValueAt(i, 14).toString().trim());
						luz 		+= Double.valueOf(tabla_c_verde.getValueAt(i, 15).toString().trim());
						apartados 	+= Double.valueOf(tabla_c_verde.getValueAt(i, 16).toString().trim());
						abono_ahorro+= Double.valueOf(tabla_c_verde.getValueAt(i, 17).toString().trim());
					}
				
				fila_cacl[0] = "TOTAL CAJA VERDE" ;
				fila_cacl[1] = df.format(t_efect    )    ;
				fila_cacl[2] = df.format(r_prog     )    ;
				fila_cacl[3] = df.format(cheques    )    ;
				fila_cacl[4] = df.format(vales      )    ;
				fila_cacl[5] = df.format(dolares    )    ;
				fila_cacl[6] = df.format(f_sodas    )    ;
				fila_cacl[7] = df.format(pin_pad    )    ;
				fila_cacl[8] = df.format(t_corte    )    ;
				fila_cacl[9] = df.format(dif        )    ;
				fila_cacl[10] =df.format(r_clt      )    ;
				fila_cacl[11] =df.format(ta         )    ;
				fila_cacl[12] =df.format(luz        )    ;
				fila_cacl[13] =df.format(apartados  )    ;
				fila_cacl[14] =df.format(abono_ahorro)   ;
				
				if(tabla_concentrado.getRowCount() > 0){
					if(tabla_concentrado.getValueAt(tabla_concentrado.getRowCount()-1, 0).equals("TOTAL CAJA VERDE")){
						tabla_model_concentrado.removeRow(tabla_concentrado.getRowCount()-1);
					}	
				}
				
				tabla_model_concentrado.addRow(fila_cacl);
		}
	}
	
	double total_de_corte_real = 0;
	public void calcular_totales(){
		
//		double t_efect   = 0;
//		double r_prog    = 0;
//		double cheques   = 0;
//		double vales     = 0;
//		double dolares   = 0;

		double pin_pad   = 0;
		
		double r_clt     = 0;
//		double ta        = 0;
		double luz       = 0;
//		double apartados = 0;
		double t_corte_totales  = 0;
		
		double total_izacel    = 0;
		double total_ta		   = 0;
		double total_f_sodas   = 0;
		double total_dif       = 0;
		double r_clt_cv		   = 0;
		double total_corte_cv  = 0;
		double pin_pad_cv	   = 0;
////		double total_cheques   = 0;
////		double total_vales	   = 0;
//		double total_dolar	   = 0;
		
		double total_ta_cv 	  = 0;
		double total_rluz_cv  = 0;
//		double total_apart_cv = 0;
		

		
			int filasCV = 0;
			if(tabla_c_verde.getRowCount()>0){
				filasCV = 1;
			}

			for(int i =0; i<tabla_concentrado.getRowCount()-filasCV; i++){
						
////							total_cheques+= Double.valueOf(tabla_concentrado.getValueAt(i, 3 ).toString().trim());
////							total_vales	 += Double.valueOf(tabla_concentrado.getValueAt(i, 4 ).toString().trim());
//							total_dolar	 += Double.valueOf(tabla_concentrado.getValueAt(i, 5 ).toString().trim());
							total_f_sodas	+= Double.valueOf(tabla_concentrado.getValueAt(i, 6 ).toString().trim());
							
							pin_pad 	 	+= Double.valueOf(tabla_concentrado.getValueAt(i, 7 ).toString().trim());
							t_corte_totales	+= Double.valueOf(tabla_concentrado.getValueAt(i, 8 ).toString().trim());
							total_dif    	+= Double.valueOf(tabla_concentrado.getValueAt(i, 9 ).toString().trim());
							r_clt 		 	+= Double.valueOf(tabla_concentrado.getValueAt(i, 10).toString().trim());
							total_ta	 	+= Double.valueOf(tabla_concentrado.getValueAt(i, 11).toString().trim());
							luz 		 	+= Double.valueOf(tabla_concentrado.getValueAt(i, 12).toString().trim());
							
							if(tabla_concentrado.getValueAt(i, 0 ).toString().trim().equals("IZACEL")){
//								System.out.println(total_izacel+"  "+total_ta+"  "+tabla_concentrado.getValueAt(i, 1 ).toString().trim());
								total_izacel += Double.valueOf(tabla_concentrado.getValueAt(i, 8 ).toString().trim());
							}
							
//							if(tabla_concentrado.getValueAt(i, 0 ).toString().trim().equals("TOTAL CAJA VERDE")){
//								total_corte_cv 	+= Double.valueOf(tabla_concentrado.getValueAt(i, 8 ).toString().trim());
//							}
						}
					
			if(filasCV==1){
//				txtTotalDelCorte.setText(		df.format(t_corte-Double.valueOf(tabla_concentrado.getValueAt(tabla_concentrado.getRowCount()-1, 8 ).toString().trim())));
//				txtTotalRetiroCliente.setText(	df.format(r_clt-Double.valueOf  (tabla_concentrado.getValueAt(tabla_concentrado.getRowCount()-1, 10).toString().trim())));
				
//				en esta parte se les suma los totales de caja verde
////				total_cheques += Double.valueOf(tabla_concentrado.getValueAt(tabla_concentrado.getRowCount()-1, 3 ).toString().trim());
////				total_vales	  += Double.valueOf(tabla_concentrado.getValueAt(tabla_concentrado.getRowCount()-1, 4 ).toString().trim());
//				total_dolar	  += Double.valueOf(tabla_concentrado.getValueAt(tabla_concentrado.getRowCount()-1, 5 ).toString().trim());
				total_f_sodas += Double.valueOf(tabla_concentrado.getValueAt(tabla_concentrado.getRowCount()-1, 6 ).toString().trim());
				pin_pad_cv 	  = Double.valueOf(tabla_concentrado.getValueAt(tabla_concentrado.getRowCount()-1, 7 ).toString().trim());
				total_dif     += Double.valueOf(tabla_concentrado.getValueAt(tabla_concentrado.getRowCount()-1, 9 ).toString().trim());
				
				total_corte_cv 	= Double.valueOf(tabla_concentrado.getValueAt(tabla_concentrado.getRowCount()-1, 8 ).toString().trim());
				r_clt_cv 		= Double.valueOf(tabla_concentrado.getValueAt(tabla_concentrado.getRowCount()-1, 10).toString().trim());
				total_ta_cv		= Double.valueOf(tabla_concentrado.getValueAt(tabla_concentrado.getRowCount()-1, 11).toString().trim());
				total_rluz_cv 	= Double.valueOf(tabla_concentrado.getValueAt(tabla_concentrado.getRowCount()-1, 12).toString().trim());
//				total_apart_cv 	= Double.valueOf(tabla_concentrado.getValueAt(tabla_concentrado.getRowCount()-1, 13).toString().trim());
				
				
//				txtCajaVerde.setText(df.format(	total_corte_cv
//												- total_f_sodas
////												- total_dolar
//												+ (total_dif) ));
				
			}
			txtCajaVerde.setText(df.format(	total_corte_cv
					- total_f_sodas
					+ (total_dif) ));
			
			
			
//			else{
////				txtTotalDelCorte.setText(		df.format(t_corte));
////				txtTotalRetiroCliente.setText(	df.format(r_clt));
//				txtCajaVerde.setText("0.00");
//			}
					
			txtTotalDelCorte.setText( 	df.format(		t_corte_totales + (	Double.valueOf(	txtEfectivoPlanes.getText().equals("")?"0":txtEfectivoPlanes.getText()	)	)	) 	);
			total_de_corte_real = (	Double.valueOf(	txtTotalDelCorte.getText().equals("")?"0":txtTotalDelCorte.getText()	)	);
			
			txtTotalRetiroCliente.setText(	df.format(r_clt));
			
			txtTotalRecibosDeLuz.setText(	df.format(luz+total_rluz_cv));
					
//					System.out.println(total_izacel+"  "+total_ta);
					txtIzacel.setText( df.format(total_izacel + total_ta + total_ta_cv) );
					
//					pendiente
//					txtPlanes.setText(df.format(0));
					
					txtPines.setText(df.format(pin_pad + pin_pad_cv + r_clt + r_clt_cv));
					
					txtDepositos.setText(df.format( Double.valueOf(txtTotalDelCorte.getText())+
													 Double.valueOf(txtTotalRetiroCliente.getText())-
//													 Double.valueOf(txtTotalRecibosDeLuz.getText())-
													 Double.valueOf(txtIzacel.getText())-
													 Double.valueOf(txtEfectivoPlanes.getText().equals("")?"0":txtEfectivoPlanes.getText())-
													 Double.valueOf(txtPines.getText())
													)
											);

	}
	
	KeyListener opTeclearPlanes = new KeyListener() {
		public void keyTyped(KeyEvent e) {	}
		public void keyReleased(KeyEvent e) {
			
					txtTotalDelCorte.setText(	 	df.format(	total_de_corte_real + (	Double.valueOf(	txtEfectivoPlanes.getText().equals("")?"0":txtEfectivoPlanes.getText()	)	)	) );
					txtDepositos.setText(	df.format (	Double.valueOf(txtTotalDelCorte.getText().equals("")?"0":txtTotalDelCorte.getText())+
														 Double.valueOf(txtTotalRetiroCliente.getText().equals("")?"0":txtTotalRetiroCliente.getText())-
//														 Double.valueOf(txtTotalRecibosDeLuz.getText().equals("")?"0":txtTotalRecibosDeLuz.getText())-
														 Double.valueOf(txtIzacel.getText().equals("")?"0":txtIzacel.getText())-
														 Double.valueOf(txtEfectivoPlanes.getText().equals("")?"0":txtEfectivoPlanes.getText())-
														 Double.valueOf(txtPines.getText().equals("")?"0":txtPines.getText())
												   	  )	
										);
		}
		public void keyPressed(KeyEvent e){}
	};
	

		@SuppressWarnings({ "rawtypes", "unchecked" })
		public static void Cat_Reporte_De_Trabajo_De_Crotes(int folio_trabajo_de_corte) {
			
			try {
				JasperReport report = JasperCompileManager.compileReport(System.getProperty("user.dir")+"\\src\\Obj_Reportes\\Obj_Reporte_Para_Trabajo_De_Cortes.jrxml");
				
				Map parametro = new HashMap();
				parametro.put("folio_trabajo", folio_trabajo_de_corte);
				
				JasperPrint print = JasperFillManager.fillReport(report, parametro, new Connexion().conexion());
				JasperViewer.viewReport(print, false);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				JOptionPane.showMessageDialog(null, "Error En Cat_Reporte_De_Corte_De_Caja ", "Error !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
			}
		}
	
	public static void main(String[] args) {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Trabajos_Cortes("CONCENTRADO 5").setVisible(true);
		}catch(Exception e){
			System.err.println("Error :"+ e.getMessage());
		}
	}
	
	public class Cat_Modificacion_De_Corte extends JDialog{
		
		Container contModif = getContentPane();
		JLayeredPane panelModif = new JLayeredPane();
		
		
		JTextField txtFolioCorte 				= new Componentes().text(new JTextField(), "Folio Del Corte", 60, "String");
		JTextField txtAsignacion 				= new Componentes().text(new JTextField(), "Folio De Asignacion", 60, "String");
		JTextField txtCajero 					= new Componentes().text(new JTextField(), "Nombre Del Cajero(a)", 160, "String");
		
		JTextField txtTotalDeEfectivo 			= new Componentes().text(new JTextField(), "Total De Efectivo", 160, "Double");
		JTextField txtTotalRetirosProgramados 	= new Componentes().text(new JTextField(), "Total De Retiros Programados", 160, "Double");
		JTextField txtTotalCheques 				= new Componentes().text(new JTextField(), "Total De Cheques", 160, "Double");
		JTextField txtTotalVales 				= new Componentes().text(new JTextField(), "Total De Vales", 160, "Double");
		JTextField txtTotalDolares 				= new Componentes().text(new JTextField(), "Total De Dolares", 160, "Double");
		JTextField txtTotalFuenteDeSodas 		= new Componentes().text(new JTextField(), "Total De Fuente De Sodas", 160, "Double");
		JTextField txtTotalPines 				= new Componentes().text(new JTextField(), "Total De Pines", 160, "Double");
		
		JButton btnGuardarModificacion = new JButton("Guardar");
		
//		tb.getValueAt(fila, 2).toString().trim()//folio corte
//		,tb.getValueAt(fila, 3).toString().trim()//folio asignacion
//		,tb.getValueAt(fila, 4).toString().trim()//nombre cajero
		
//		,tb.getValueAt(fila, 5).toString().trim() //total efectivo
		
//		,tb.getValueAt(fila, 5).toString().trim() //retiros programados
//		,tb.getValueAt(fila, 5).toString().trim() //cheques
//		,tb.getValueAt(fila, 5).toString().trim() //vales
//		,tb.getValueAt(fila, 5).toString().trim() //dolares
//		,tb.getValueAt(fila, 10).toString().trim()//fuente de sodas
//		,tb.getValueAt(fila, 11).toString().trim()
		
		public Cat_Modificacion_De_Corte(String fCorte, String fAsignacion, String cajero, String efectivo, String retiros_programados, String cheques, String vales, String dolares, String fSodas, String pines){
			this.setTitle("Modificacion De Trabajo De Corte");
			
			
			txtTotalDeEfectivo.setBackground(Color.lightGray);
			txtTotalRetirosProgramados.setBackground(Color.lightGray);
			txtTotalCheques.setBackground(Color.lightGray);
			txtTotalVales.setBackground(Color.lightGray);
			txtTotalDolares.setBackground(Color.lightGray);
			txtTotalFuenteDeSodas.setBackground(Color.lightGray);
			txtTotalPines.setBackground(Color.lightGray);
			
			txtTotalDeEfectivo.setBorder(BorderFactory.createLineBorder(Color.black)); 			
			txtTotalRetirosProgramados.setBorder(BorderFactory.createLineBorder(Color.black)); 	
			txtTotalCheques.setBorder(BorderFactory.createLineBorder(Color.black)); 				
			txtTotalVales.setBorder(BorderFactory.createLineBorder(Color.black)); 				
			txtTotalDolares.setBorder(BorderFactory.createLineBorder(Color.black)); 				
			txtTotalFuenteDeSodas.setBorder(BorderFactory.createLineBorder(Color.black)); 		
			txtTotalPines.setBorder(BorderFactory.createLineBorder(Color.black));
						
			int x=15,y=20,ancho=90;
			
			panelModif.add(new JLabel("Cajero(a):")).setBounds(x,y,ancho,20);
			panelModif.add(txtCajero).setBounds(x+ancho,y,ancho*3+10,20);
			
			panelModif.add(new JLabel("Folio De Corte:")).setBounds(x,y+=25,ancho,20);
			panelModif.add(txtFolioCorte).setBounds(x+ancho,y,ancho,20);
			
			panelModif.add(new JLabel("Asignacion:")).setBounds(x+210,y,ancho,20);
			panelModif.add(txtAsignacion).setBounds(x+ancho+190,y,ancho,20);
			
			panelModif.add(new JLabel("Total De Efectivo:")).setBounds(x,y+=25,ancho,20);
			panelModif.add(txtTotalDeEfectivo).setBounds(x+ancho+45,y,ancho,20);
			
			panelModif.add(new JLabel("Total De Retiros Programados:")).setBounds(x,y+=25,ancho+65,20);
			panelModif.add(txtTotalRetirosProgramados).setBounds(x+ancho+65,y,ancho-20,20);
			
			panelModif.add(new JLabel("Total De Cheques:")).setBounds(x,y+=25,ancho,20);
			panelModif.add(txtTotalCheques).setBounds(x+ancho+45,y,ancho,20);
			
			panelModif.add(new JLabel("Total De Vales:")).setBounds(x,y+=25,ancho,20);
			panelModif.add(txtTotalVales).setBounds(x+ancho+45,y,ancho,20);
			
			panelModif.add(new JLabel("Total De Dolares:")).setBounds(x,y+=25,ancho,20);
			panelModif.add(txtTotalDolares).setBounds(x+ancho+45,y,ancho,20);
			
			panelModif.add(new JLabel("Total De Fuente De Sodas:")).setBounds(x,y+=25,ancho+50,20);
			panelModif.add(txtTotalFuenteDeSodas).setBounds(x+ancho+65,y,ancho-20,20);
			
			panelModif.add(new JLabel("Total De Pines:")).setBounds(x,y+=25,ancho,20);
			panelModif.add(txtTotalPines).setBounds(x+ancho+45,y,ancho,20);
			
			panelModif.add(btnGuardarModificacion).setBounds(x+ancho+180,y,ancho+10,20);
			
			contModif.add(panelModif);
			
			txtCajero.setEditable(false);
			txtFolioCorte.setEditable(false);
			txtAsignacion.setEditable(false);
			
//			txtTotalDeEfectivo.setEditable(false);
//			txtTotalRetirosProgramados.setEditable(false);	
//			txtTotalCheques.setEditable(false);		
//			txtTotalVales.setEditable(false);
//			txtTotalDolares.setEditable(false);
//			txtTotalFuenteDeSodas.setEditable(false);
//			txtTotalPines.setEditable(false);
			
			
			txtFolioCorte.setText(fCorte);
			txtAsignacion.setText(fAsignacion);
			txtCajero.setText(cajero);
			
			txtTotalDeEfectivo.setText(efectivo);
			txtTotalRetirosProgramados.setText(retiros_programados); 	
			txtTotalCheques.setText(cheques); 				
			txtTotalVales.setText(vales); 				
			txtTotalDolares.setText(dolares);	
			txtTotalFuenteDeSodas.setText(fSodas);	
			txtTotalPines.setText(pines); 				
			
			
			btnGuardarModificacion.addActionListener(opGuardarModif);
			
			this.setSize(420, 300);
			this.setResizable(true);
			this.setLocationRelativeTo(null);
		}
		
		ActionListener opGuardarModif = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(validaCampos().equals("")){
						if(new ActualizarSQL().Modificacion_De_Corte_Para_Trabajos(txtFolioCorte.getText().trim(), Double.valueOf(txtTotalDeEfectivo.getText().trim()), Double.valueOf(txtTotalFuenteDeSodas.getText().trim()), Double.valueOf(txtTotalPines.getText().trim()), Double.valueOf(txtTotalRetirosProgramados.getText().trim()), Double.valueOf(txtTotalCheques.getText().trim()), Double.valueOf(txtTotalVales.getText().trim()), Double.valueOf(txtTotalDolares.getText().trim()) ) ){
							refresh();
							dispose();
							JOptionPane.showMessageDialog(null, "El Corte Se Modifico Correctamente"+validaCampos(),"Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen//aplicara-el-dialogo-icono-6256-32.png"));
		    				return;
						}else{
							JOptionPane.showMessageDialog(null, "El Corte No Se Pudo Modificar","Error",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen//usuario-icono-eliminar5252-64.png"));
		    				return;
						}
				}else{
					JOptionPane.showMessageDialog(null, "Los Siguientes Campos Son Requeridos:\n"+validaCampos(),"Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
    				return;
				}
				
			}
		};
		
		public String validaCampos(){
			String error = "";
			
			if(txtTotalDeEfectivo.getText().equals("")) error+="Total De Efectivo\n";
			if(txtTotalRetirosProgramados.getText().equals("")) error+="Total De Retiros Programados\n";
			if(txtTotalCheques.getText().equals("")) error+="Total De Cheques\n";
			if(txtTotalVales.getText().equals("")) error+="Total De Vales\n";
			if(txtTotalDolares.getText().equals("")) error+="Total De Dolares\n";
			if(txtTotalFuenteDeSodas.getText().equals("")) error+="Total De Fuentes De Sodas\n";
			if(txtTotalPines.getText().equals("")) error+="Total De Pines\n";
			
			return error;
		}
		
	}
	
	public class Cat_Reposicion_De_Efectivo extends JDialog{
		
		Container contRep = getContentPane();
		JLayeredPane panelRep = new JLayeredPane();
		
		JDateChooser fchTrabajoCorte = new JDateChooser();
		
		   public DefaultTableModel modelo_caja_verde = new DefaultTableModel(null, new String[]{"", "Caja Verde"} ){
               
				@SuppressWarnings({ "rawtypes" })
				Class[] types = new Class[]{
		                   java.lang.Object.class,
		                   java.lang.Object.class
		                    
		    };
				@SuppressWarnings({ "unchecked", "rawtypes" })
				public Class getColumnClass(int columnIndex) {
		                return types[columnIndex];
		        }
		    public boolean isCellEditable(int fila, int columna){
		                switch(columna){
//		                        case 0  : if(tabla_model_grupos.getValueAt(fila, 1).toString().trim().equals("IZACEL") || Double.valueOf(tabla_model_grupos.getValueAt(fila, 11).toString()) > 0){
//		                        				return false; 
//					                        }else{
//					                        	return true; 
//					                        }      
		                		case 0	: return false;
		                        case 1 : return false; 
		                }
		                 return false;
		         }
		    };
			
		    JTable tabla_caja_verde = new JTable(modelo_caja_verde);
			JScrollPane scroll_caja_verde = new JScrollPane(tabla_caja_verde,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		JTextField txtConcentrado = new Componentes().text(new JTextField(), "No. De Concentrado.", 80, "String");
		JTextField txtElaboro = new Componentes().text(new JTextField(), "Elaboro Trabajo.", 200, "String");
		JTextField txtReviso = new Componentes().text(new JTextField(), "Reviso Trabajo.", 200, "String");
		
		JTextArea txaComentario = new Componentes().textArea(new JTextArea(), "Comnetarios", 420);
		JScrollPane observacion = new JScrollPane(txaComentario);
		
		JTextField txtFuenteDeSodas = new Componentes().text(new JTextField(), "Fuente De Sodas", 20, "Double");
		JTextField txtDolares = new Componentes().text(new JTextField(), "Dolares", 20, "Double");
		JTextField txtVales = new Componentes().text(new JTextField(), "Vales", 20, "Double");
		JTextField txtDiferenciaDeCortes = new Componentes().text(new JTextField(), "Diferencia De Cortes", 20, "Double");
		
		JTextField txtOtrosFaltantes = new Componentes().text(new JTextField(), "Otros Faltantes", 20, "Double");
		JTextField txtOtrosSobrentes = new Componentes().text(new JTextField(), "Otros Sobrantes", 20, "Double");
		JTextField txtCajaVerdeRepEfect = new Componentes().text(new JTextField(), "Caja Verde", 160, "Double");
		
//		JTextField txtTotal = new Componentes().text(new JTextField(), "Total", 20, "Double");
//		JTextField txtSobrantesFinanazas = new Componentes().text(new JTextField(), "Sobrantes Finanazas", 20, "Double");
		JTextField txtRetiros_Clientes = new Componentes().text(new JTextField(), "Retiros Clientes", 20, "Double");
		
		JTextField txtTotalFinal = new Componentes().text(new JTextField(), "Total Final", 20, "Double");
		
		JTextField txtDeposito2 = new Componentes().text(new JTextField(), "Deposito", 20, "Double");
		JTextField txtBancoInterno = new Componentes().text(new JTextField(), "Banco Interno", 20, "Double");
		
		JTextField txtTotalPlanesRep = new Componentes().text(new JTextField(), "Ingrese El Total De Planes Que Debe tener", 10, "Double");
		JTextField txtEfectivoPlanesRep = new Componentes().text(new JTextField(), "Efectivo De Planes Con Lo Que Se Cuenta", 10, "Double");
		JTextField txtDiferenciaPlanesRep = new Componentes().text(new JTextField(), "Diferencia De Planes", 10, "Double");
		
		JCButton btnGuardarReposicionEfectivo = new JCButton("Guardar","guardar.png","Azul");
		
		public Cat_Reposicion_De_Efectivo(String tFS, String tDolares, String tDiferencia, String tCorteCV, String tVales, String efectivoPlanTelcel, Object[][] lista_cv2, String tRetirosClientes/*, Vector vector_cVerde, String[] caja_verde_totales_de_corte*/){
			this.setTitle("Reposicion De Efectivo");
			
			txtOtrosFaltantes.setBackground(Color.lightGray);
			txtOtrosSobrentes.setBackground(Color.lightGray);
			txtDeposito2.setBackground(Color.lightGray);
			txtTotalPlanesRep.setBackground(Color.lightGray);
			
			txtOtrosFaltantes.setBorder(BorderFactory.createLineBorder(Color.black));
			txtOtrosSobrentes.setBorder(BorderFactory.createLineBorder(Color.black));
			txtDeposito2.setBorder(BorderFactory.createLineBorder(Color.black));
			txtTotalPlanesRep.setBorder(BorderFactory.createLineBorder(Color.black));
			txaComentario.setBorder(BorderFactory.createLineBorder(Color.black));
			
//			fchTrabajoCorte.setEnabled(false);
			((JTextFieldDateEditor)fchTrabajoCorte.getDateEditor()).setDisabledTextColor(Color.lightGray);
			fchTrabajoCorte.setBorder(BorderFactory.createLineBorder(Color.black));
			
			txaComentario.setLineWrap(true); 
			txaComentario.setWrapStyleWord(true);
			
			int x=15,y=20,ancho=110;
			
			panelRep.add(new JLabel("Concentrado:")).setBounds(x,y,ancho,20);
			panelRep.add(txtConcentrado).setBounds(x+ancho-20,y,ancho*3+40,20);
			
			panelRep.add(new JLabel("Elaboro:")).setBounds(x,y+=25,ancho,20);
			panelRep.add(txtElaboro).setBounds(x+ancho-20,y,ancho*3+40,20);
			
			panelRep.add(new JLabel("Fecha Del Trabajo:")).setBounds(x,y+=25,ancho,20);
			panelRep.add(fchTrabajoCorte).setBounds(x+ancho+10,y,ancho,20);
			
			panelRep.add(new JLabel("Fuente De Sodas:")).setBounds(x,y+=25,ancho,20);
		panelRep.add(txtFuenteDeSodas).setBounds(x+ancho+10,y,ancho,20);						panelRep.add(scroll_caja_verde).setBounds(x+ancho+140,y,ancho+90,97);	panelRep.add(new JLabel("Planes Telcel (Fisico):")).setBounds(x+ancho*3+140,y,ancho+90,20);    
			                                                                                                                                                            panelRep.add(txtEfectivoPlanesRep).setBounds(x+ancho*3+265,y,70,20);                           
			panelRep.add(new JLabel("Dolares:")).setBounds(x,y+=25,ancho,20);                                                                                           panelRep.add(new JLabel("Planes Telcel (Sistema):")).setBounds(x+ancho*3+140,y,ancho+90,20);                                                                                                
			panelRep.add(txtDolares).setBounds(x+ancho+10,y,ancho,20);                                                                                                  panelRep.add(txtTotalPlanesRep).setBounds(x+ancho*3+265,y,70,20);                                                                                                                           
			panelRep.add(new JLabel("Vales:")).setBounds(x,y+=25,ancho,20);                                                                                             panelRep.add(new JLabel("Planes Telcel (Diferencia):")).setBounds(x+ancho*3+140,y,ancho+90,20);
			panelRep.add(txtVales).setBounds(x+ancho+10,y,ancho,20);                                                                                                    panelRep.add(txtDiferenciaPlanesRep).setBounds(x+ancho*3+265,y,70,20);                         
			                                                                                                                                                                                                                                                                     
			panelRep.add(new JLabel("Diferencia De Cortes:")).setBounds(x,y+=25,ancho,20);                                                                                          
			panelRep.add(txtDiferenciaDeCortes).setBounds(x+ancho+10,y,ancho,20);                                                                                                   
			
			panelRep.add(new JLabel("Caja Verde:")).setBounds(x,y+=25,ancho,20);               	panelRep.add(new JLabel("Comentarios:")).setBounds(x+ancho+140,y+10,ancho+90,20);
			panelRep.add(txtCajaVerdeRepEfect).setBounds(x+ancho+10,y,ancho,20);               	panelRep.add(observacion).setBounds(x+ancho+140,y+25,ancho*3+90,145);               
			
			panelRep.add(new JLabel("Retiros Clientes:")).setBounds(x,y+=25,ancho+50,20);				
			panelRep.add(txtRetiros_Clientes).setBounds(x+ancho+10,y,ancho,20);	
			
			panelRep.add(new JLabel("Otros Faltantes:")).setBounds(x,y+=25,ancho+50,20);			
			panelRep.add(txtOtrosFaltantes).setBounds(x+ancho+10,y,ancho,20);						
			
//			--------------------------------------------------------------------------------
			panelRep.add(new JLabel("Otros Sobrantes:")).setBounds(x,y+=25,ancho,20);               
			panelRep.add(txtOtrosSobrentes).setBounds(x+ancho+10,y,ancho,20);                       
			
//			panelRep.add(new JLabel("Total:")).setBounds(x,y+=25,ancho,20);
//			panelRep.add(txtTotal).setBounds(x+ancho+10,y,ancho,20);								
			
			panelRep.add(new JLabel("Total Final:")).setBounds(x,y+=25,ancho,20);			
			panelRep.add(txtTotalFinal).setBounds(x+ancho+10,y,ancho,20);							
			
			panelRep.add(new JLabel("Deposito:")).setBounds(x,y+=25,ancho,20);
			panelRep.add(txtDeposito2).setBounds(x+ancho+10,y,ancho,20);
			
			panelRep.add(new JLabel("Banco Interno:")).setBounds(x,y+=25,ancho,20);
			panelRep.add(txtBancoInterno).setBounds(x+ancho+10,y,ancho,20);						panelRep.add(btnGuardarReposicionEfectivo).setBounds(x+ancho*5,y+=25,120,20);
			
//			panelRep.add(new JLabel(":")).setBounds(x,y+=25,ancho+50,20);
//			panelRep.add(txtSobrantesJuan).setBounds(x+ancho+50,y,ancho-20,20);
			
			
			contRep.add(panelRep);
			
			txtConcentrado.setEditable(false);
			txtElaboro.setEditable(false);
			txtFuenteDeSodas.setEditable(false);
			txtDolares.setEditable(false);
			txtVales.setEditable(false);
			txtDiferenciaDeCortes.setEditable(false);
			txtCajaVerdeRepEfect.setEditable(false);
			txtRetiros_Clientes.setEditable(false);
//			txtTotal.setEditable(false);
			txtTotalFinal.setEditable(false);
			txtBancoInterno.setEditable(false);
			
			txtEfectivoPlanesRep.setEditable(false);
			txtDiferenciaPlanesRep.setEditable(false);
			
			llamar_render2();
			
//			txtOtrosFaltantes.setEditable(true);
//			txtOtrosSobrentes.setEditable(true);
//			txtSobrantesJuan.setEditable(true);
//			txtDeposito2.setEditable(true);
			
			txtConcentrado.setText(grupo_corte);
			
			fchTrabajoCorte.setDate(cargar_fechas_de_baja(1));
			
			txtElaboro.setText(new Obj_Usuario().LeerSession().getNombre_completo());
			
			txtFuenteDeSodas.setText(tFS);
			txtVales.setText(tVales);
			txtDolares.setText(tDolares);
			txtDiferenciaDeCortes.setText(tDiferencia);
			txtCajaVerdeRepEfect.setText(tCorteCV);
			
			txtEfectivoPlanesRep.setText(efectivoPlanTelcel);
			txtRetiros_Clientes.setText(tRetirosClientes);
			
			llenarCVReposicion(lista_cv2);
			calcularReposicionDeEfectivo();
			
			txtDiferenciaPlanesRep.setText(df.format(Double.valueOf(txtEfectivoPlanes.getText().toString().trim().equals("")?"0":txtEfectivoPlanes.getText().toString().trim())
					-(Double.valueOf(txtTotalPlanesRep.getText().toString().trim().equals("")?"0":txtTotalPlanesRep.getText().toString().trim())))+"");
//			txtOtrosFaltantes.addKeyListener(opReposicionEfectivo);
//			txtOtrosSobrentes.addKeyListener(opReposicionEfectivo);
//			txtSobrantesJuan.addKeyListener(opReposicionEfectivo);
//			txtDeposito2.addKeyListener(opReposicionEfectivo);
			
			reposicionEfectivo(txtOtrosFaltantes,"otroFaltante");
			reposicionEfectivo(txtOtrosSobrentes,"otroSobrante");
			reposicionEfectivo(txtRetiros_Clientes,"sobranteFinanzas");
			reposicionEfectivo(txtDeposito2,"deposito");
			
			txtTotalPlanesRep.addKeyListener(opDiferenciaPlanesTelcel);
			
			
			btnGuardarReposicionEfectivo.addActionListener(opGuardarRep);
			
//          asigna el foco al JTextField deseado al arrancar la ventana
            this.addWindowListener(new WindowAdapter() {
                    public void windowOpened( WindowEvent e ){
                    	txtOtrosFaltantes.requestFocus();
                 }
            });
			
			this.setSize(720, 440);
			this.setResizable(true);
			this.setLocationRelativeTo(null);
		}
		
		public Date cargar_fechas_de_baja(int dias){
			Date date1 = null;
					  try {
						date1 = new SimpleDateFormat("dd/MM/yyyy").parse(new BuscarSQL().fecha(dias));
					} catch (ParseException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					  return (date1);
		};
		
		public void llenarCVReposicion(Object[][] lista_cv3){
			
			modelo_caja_verde.setRowCount(0);
			double total = 0;
			int i = 0;
			
			for(Object[] cv: lista_cv3){
				total += Double.valueOf(lista_cv3[i][1].toString().trim());
				i++;
				modelo_caja_verde.addRow(cv);
			}
			
			Object[] cv2 = {"TOTAL:",total};
			modelo_caja_verde.addRow(cv2);
		}
		
		KeyListener opDiferenciaPlanesTelcel = new KeyListener() {
			public void keyTyped(KeyEvent e) {	}
			@SuppressWarnings("static-access")
			public void keyReleased(KeyEvent e) {
				
				if(e.getKeyCode() == e.VK_ENTER){
					
					txtOtrosFaltantes.requestFocus();
					
				}else{
					txtDiferenciaPlanesRep.setText(Double.valueOf(txtEfectivoPlanes.getText().toString().trim().equals("")?"0":txtEfectivoPlanes.getText().toString().trim())
													-(Double.valueOf(txtTotalPlanesRep.getText().toString().trim().equals("")?"0":txtTotalPlanesRep.getText().toString().trim()))+""
							);
				}
				
			}
			public void keyPressed(KeyEvent e){}
		};
		
		
		public void reposicionEfectivo(final JTextField txt, final String referencia){
			txt.addKeyListener(new KeyListener() {
				public void keyTyped(KeyEvent e) {	}
				@SuppressWarnings("static-access")
				public void keyReleased(KeyEvent e) {
					
					if(e.getKeyCode() == e.VK_ENTER){
						
						
						switch(referencia){
							case "otroFaltante"		: txtOtrosSobrentes.requestFocus(); break;
							case "otroSobrante"		: txtRetiros_Clientes.requestFocus(); break;
							case "sobranteFinanzas"	: txtDeposito2.requestFocus(); break;
							case "deposito"			: txtTotalPlanesRep.requestFocus(); break;
						}
						
					}else{
						calcularReposicionDeEfectivo();
					}
				}
				public void keyPressed(KeyEvent e){}
			});
		}
//		KeyListener opReposicionEfectivo = new KeyListener() {
//			public void keyTyped(KeyEvent e) {	}
//			@SuppressWarnings("static-access")
//			public void keyReleased(KeyEvent e) {
//				
//				if(e.getKeyCode() == e.VK_ENTER){
//					System.out.println(txt);
//				}
//				
//				
//				
//				calcularReposicionDeEfectivo();
//			}
//			public void keyPressed(KeyEvent e){}
//		};
		
		public void calcularReposicionDeEfectivo(){
			
			txtTotalFinal.setText(df.format( (Double.valueOf(txtCajaVerdeRepEfect.getText().toString().trim())
							-Double.valueOf(txtFuenteDeSodas.getText().toString().trim())
							-Double.valueOf(txtVales.getText().toString().trim())
							-Double.valueOf(txtDolares.getText().toString().trim())
							+Double.valueOf(txtDiferenciaDeCortes.getText().toString().trim())
							-Double.valueOf(txtOtrosFaltantes.getText().toString().trim().equals("")?"0":txtOtrosFaltantes.getText().toString().trim())
							+Double.valueOf(txtOtrosSobrentes.getText().toString().trim().equals("")?"0":txtOtrosSobrentes.getText().toString().trim())) 
							+Double.valueOf(txtRetiros_Clientes.getText().toString().trim().equals("")?"0":txtRetiros_Clientes.getText().toString().trim()) 
							) );
			
			
//			txtTotalFinal.setText(df.format( (Double.valueOf(txtTotal.getText().toString().trim())
//									/*+Double.valueOf(txtRetiros_Clientes.getText().toString().trim().equals("")?"0":txtRetiros_Clientes.getText().toString().trim())*/) ) );
			
			
			txtBancoInterno.setText(df.format( (Double.valueOf(txtTotalFinal.getText().toString().trim())
											-Double.valueOf(txtDeposito2.getText().toString().trim().equals("")?"0":txtDeposito2.getText().toString().trim())) )+"");
		}
		
		
		ActionListener opGuardarRep = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				double gastos 				=	Double.valueOf(txtFuenteDeSodas.getText().toString().trim().equals("")?"0":txtFuenteDeSodas.getText().toString().trim());
				double dolares 				=	Double.valueOf(txtDolares.getText().toString().trim().equals("")?"0":txtDolares.getText().toString().trim());
				double valers 				=	Double.valueOf(txtVales.getText().toString().trim().equals("")?"0":txtVales.getText().toString().trim());
				double diferencia_de_cortes =	Double.valueOf(txtDiferenciaDeCortes.getText().toString().trim().equals("")?"0":txtDiferenciaDeCortes.getText().toString().trim());
				double otros_faltantes 		=	Double.valueOf(txtOtrosFaltantes.getText().toString().trim().equals("")?"0":txtOtrosFaltantes.getText().toString().trim());
				double otros_sobrantes 		=	Double.valueOf(txtOtrosSobrentes.getText().toString().trim().equals("")?"0":txtOtrosSobrentes.getText().toString().trim());
				double caja_verde 			=	Double.valueOf(txtCajaVerdeRepEfect.getText().toString().trim().equals("")?"0":txtCajaVerdeRepEfect.getText().toString().trim());
				double total 				=	Double.valueOf(txtTotalFinal.getText().toString().trim().equals("")?"0":txtTotalFinal.getText().toString().trim());
			
				//se cambio sobrante_finanza por retiros_clientes
				double sobrante_juan 		=	Double.valueOf(txtRetiros_Clientes.getText().toString().trim().equals("")?"0":txtRetiros_Clientes.getText().toString().trim());
			
				double total_final 			=	Double.valueOf(txtTotalFinal.getText().toString().trim().equals("")?"0":txtTotalFinal.getText().toString().trim());
				double deposito2 			=	Double.valueOf(txtDeposito2.getText().toString().trim().equals("")?"0":txtDeposito2.getText().toString().trim());
				double banco_interno 		=	Double.valueOf(txtBancoInterno.getText().toString().trim().equals("")?"0":txtBancoInterno.getText().toString().trim());
				
				if(validaCampos().equals("")){
					
						if(guardarTrabajo( tabla_cv(), tabla_gr() ,gastos ,dolares ,valers ,diferencia_de_cortes ,otros_faltantes ,otros_sobrantes ,caja_verde ,total ,sobrante_juan ,total_final ,deposito2 ,banco_interno ,Double.valueOf(txtTotalPlanesRep.getText()) ,Double.valueOf(txtEfectivoPlanesRep.getText()), Double.valueOf(txtDiferenciaPlanesRep.getText()), new SimpleDateFormat("dd/MM/yyyy").format(fchTrabajoCorte.getDate())) > 0 ){
							Cat_Reporte_De_Trabajo_De_Crotes(folio_trabajo_realizado);
						}else{
							JOptionPane.showMessageDialog(null, "El trabajo no pudo ser guardado","Aviso",JOptionPane.WARNING_MESSAGE);
							return;
						}
						
				}else{
					JOptionPane.showMessageDialog(null, "Los Siguientes Campos Son Requeridos:\n"+validaCampos()+"Si No Cuenta Con Faltantes O Sobrantes Ingreselos En Cero (0)","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
    				return;
				}
				
			}
		};
		
		@SuppressWarnings({ })
		public void llamar_render2(){
			//		tipo de valor = imagen,chb,texto
//			tabla.getColumnModel().getColumn(# columna).setCellRenderer(new CellRenderer("tipo_de_valor","alineacion","tipo_de_letra","negrita",# tamanio_fuente));
	    
			Color fondoEncabezado_c_verde = new Color(3,178,47);
			Color textoEncabezado = Color.black;
			
			tabla_caja_verde.getColumnModel().getColumn(0).setHeaderRenderer(new CaveceraTablaRenderer(fondoEncabezado_c_verde,textoEncabezado,"centro","Arial","negrita",10));
//			tabla_concentrado.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",10));
			for(int i = 1; i<tabla_caja_verde.getColumnCount(); i++){
				tabla_caja_verde.getColumnModel().getColumn(i).setHeaderRenderer(new CaveceraTablaRenderer(fondoEncabezado_c_verde,textoEncabezado,"centro","Arial","negrita",10));
				
				switch(i){
						case 0: tabla_caja_verde.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",11)); break;
						default: tabla_caja_verde.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","derecha","Arial","negrita",11)); break;
					}
			}
	    	
			int x=70;
			
	    	this.tabla_caja_verde.getTableHeader().setReorderingAllowed(false) ;
	    	this.tabla_caja_verde.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	    	
	    	this.tabla_caja_verde.getColumnModel().getColumn(0).setMaxWidth(x);
	    	this.tabla_caja_verde.getColumnModel().getColumn(0).setMinWidth(x);
	    	this.tabla_caja_verde.getColumnModel().getColumn(1).setMaxWidth(x+40);
	    	this.tabla_caja_verde.getColumnModel().getColumn(1).setMinWidth(x+40);
	    }
		
		public Object[][] tabla_cv(){
			Object[][] matrizCV = new Object[tabla_c_verde.getRowCount()][tabla_c_verde.getColumnCount()];
			
			for(int i =0; i<tabla_c_verde.getRowCount(); i++){
				for(int j =0; j<tabla_c_verde.getColumnCount(); j++){
					matrizCV[i][j] = tabla_c_verde.getValueAt(i, j).toString();
				}
			}
			
			return matrizCV;
		}
		
		public Object[][] tabla_gr(){
			
			Object[][] matrizGr = new Object[tabla_grupos.getRowCount()][tabla_grupos.getColumnCount()];
			
			for(int i =0; i<tabla_grupos.getRowCount(); i++){
				for(int j =0; j<tabla_grupos.getColumnCount()-1; j++){
					matrizGr[i][j] = tabla_model_grupos.getValueAt(i, j+1).toString().trim();
				}
			}
			return matrizGr;
		}
		
		public int guardarTrabajo( Object[][] tb_CV, Object[][] tb_Gr ,double gastos ,double dolares ,double valers ,double diferencia_de_cortes ,double otros_faltantes ,double otros_sobrantes ,double caja_verde ,double total ,double sobrante_juan ,double total_final ,double deposito2 ,double banco_interno ,double totalPlanesRep,double efectivoPlanesRep, double diferencia_planesRep, String fecha_trabajo_corte){
			folio_trabajo_realizado=new GuardarTablasModel().Guarda_tabla_trabajos(tb_CV, tb_Gr, Double.valueOf(txtTotalDelCorte.getText()), Double.valueOf(txtTotalRetiroCliente.getText()), Double.valueOf(txtTotalRecibosDeLuz.getText()), Double.valueOf(txtIzacel.getText()), Double.valueOf(txtEfectivoPlanes.getText()), Double.valueOf(txtPines.getText()), Double.valueOf(txtDepositos.getText()), Double.valueOf(txtCajaVerde.getText()),grupo_corte,     gastos ,dolares ,valers ,diferencia_de_cortes ,otros_faltantes ,otros_sobrantes ,caja_verde ,total ,sobrante_juan ,total_final ,deposito2 ,banco_interno ,totalPlanesRep, efectivoPlanesRep, diferencia_planesRep, txaComentario.getText().toString().trim().toUpperCase(),fecha_trabajo_corte); 
			return folio_trabajo_realizado;
		}
		
		public String validaCampos(){
			String error = "";
			if(txtOtrosFaltantes.getText().equals("")) error+=" -Otros Faltantes\n";
			if(txtOtrosSobrentes.getText().equals("")) error+=" -Otro Sobrantes\n";
//			if(txtRetiros_Clientes.getText().equals("")) error+=" -Retiros Clientes\n";
			if(txtDeposito2.getText().equals("")) error+=" -Deposito\n";
			if(txtTotalPlanesRep.getText().equals("")) error+=" -Planes Telcel (Sistema)\n";
			
			return error;
		}
		
	}
	
	public class Cat_Cortes_Quitados extends JDialog{

		Container cont_quitados = getContentPane();
		JLayeredPane panel_quitados = new JLayeredPane();
		
		JButton btnRestaurarFiltro = new JButton("Restaurar Seleccionados", new ImageIcon("imagen/flecha-naranja-alerta-de-descarga-de-la-actualizacion-icono-8872-16.png"));
		
		JTextField txtFiltroAsignacion = new Componentes().text(new JCTextField(), "      Asignación", 15, "String");
		
	    public DefaultTableModel tabla_model_quitados = new DefaultTableModel(null, new String[]{"*","Establecimiento","F.Corte", "F.Asignacion", "Cajero", "Total Efec.", "Retiros prog.", "Cheques", "Vales", "Dolares", "F.Sodas", "PIN-PAD", "Total Corte", "Diferencia", "Retiros Clt.", "TA", "R.Luz","Apartados", "Fecha Corte", "Observacion"} ){
	                    
			@SuppressWarnings({ "rawtypes" })
			Class[] types = new Class[]{
	                   java.lang.Boolean.class,
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
	    public boolean isCellEditable(int fila, int columna){
	                switch(columna){
	                		case 0	: return true;
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
	                }
	                 return false;
	         }
	    };
		
	    JTable tabla_quitados = new JTable(tabla_model_quitados);
		JScrollPane scroll_quitados = new JScrollPane(tabla_quitados,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
//		cadenaCortesQuitados
		public Cat_Cortes_Quitados(){
			this.setModal(true);
			this.setTitle("Cortes Quitados");
			this.panel_quitados.setBorder(BorderFactory.createTitledBorder( "Filtro De Cortes Quitados"));
			
			panel_quitados.add(btnRestaurarFiltro).setBounds(20, 30, 180, 20);
			panel_quitados.add(txtFiltroAsignacion).setBounds(275, 30, 110, 20);
			panel_quitados.add(scroll_quitados).setBounds(20, 50, 970, 280);
			
			cont_quitados.add(panel_quitados);
			
			llenar_tabla_filtro();
			render_filtro();
			
			opQuitar(btnRestaurarFiltro);
			txtFiltroAsignacion.addKeyListener(opFiltroDinamico);
			
			this.setSize(1024,380);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
		}
		
		public void render_filtro(){
//			tabla_quitados.getColumnModel().getColumn(0).setHeaderRenderer(new CaveceraTablaRenderer(fondoEncabezado_grupos,textoEncabezado,"centro","Arial","negrita",10));
			tabla_quitados.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("CHB","izquierda","Arial","negrita",10));
			for(int i = 1; i<tabla_quitados.getColumnCount(); i++){
//				tabla_quitados.getColumnModel().getColumn(i).setHeaderRenderer(new CaveceraTablaRenderer(fondoEncabezado_grupos,textoEncabezado,"centro","Arial","negrita",10));
				
				switch(i){
						case 0: tabla_quitados.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","centro"	,"Arial","negrita",11)); break;
						case 1: tabla_quitados.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",9) ); break;
						case 2: tabla_quitados.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",10)); break;
						case 3: tabla_quitados.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",9) ); break;
						case 4: tabla_quitados.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",10)); break;
						case 17:tabla_quitados.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","centro"	,"Arial","negrita",11)); break;
						case 19:tabla_quitados.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",11)); break;
						default: tabla_quitados.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","derecha"	,"Arial","negrita",11)); break;
					}
			}
			
			this.tabla_quitados.getTableHeader().setReorderingAllowed(false) ;
	    	this.tabla_quitados.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	    	
	    	int x=70;
	    	
	    	this.tabla_quitados.getColumnModel().getColumn(0 ).setMaxWidth(25);   
	    	this.tabla_quitados.getColumnModel().getColumn(0 ).setMinWidth(25);	
	    	this.tabla_quitados.getColumnModel().getColumn(1 ).setMaxWidth(x*3-50);   
	    	this.tabla_quitados.getColumnModel().getColumn(1 ).setMinWidth(x*3-50);   
	    	this.tabla_quitados.getColumnModel().getColumn(2 ).setMaxWidth(x);
	    	this.tabla_quitados.getColumnModel().getColumn(2 ).setMinWidth(x);
	    	this.tabla_quitados.getColumnModel().getColumn(3 ).setMaxWidth(x+40); 
	    	this.tabla_quitados.getColumnModel().getColumn(3 ).setMinWidth(x+40); 
	    	                                               
	    	this.tabla_quitados.getColumnModel().getColumn(4 ).setMaxWidth(x*4);
	    	this.tabla_quitados.getColumnModel().getColumn(4 ).setMinWidth(x*3);		
	    	this.tabla_quitados.getColumnModel().getColumn(5 ).setMaxWidth(x);
	    	this.tabla_quitados.getColumnModel().getColumn(5 ).setMinWidth(x);
	    	this.tabla_quitados.getColumnModel().getColumn(6 ).setMaxWidth(x);
	    	this.tabla_quitados.getColumnModel().getColumn(6 ).setMinWidth(x);
	    	this.tabla_quitados.getColumnModel().getColumn(7 ).setMaxWidth(x);
	    	this.tabla_quitados.getColumnModel().getColumn(7 ).setMinWidth(x);
	    	                                               
	    	this.tabla_quitados.getColumnModel().getColumn(8 ).setMaxWidth(x);
	    	this.tabla_quitados.getColumnModel().getColumn(8 ).setMinWidth(x);		
	    	this.tabla_quitados.getColumnModel().getColumn(9 ).setMaxWidth(x);
	    	this.tabla_quitados.getColumnModel().getColumn(9 ).setMinWidth(x);
	    	this.tabla_quitados.getColumnModel().getColumn(10).setMaxWidth(x);
	    	this.tabla_quitados.getColumnModel().getColumn(10).setMinWidth(x);
	    	this.tabla_quitados.getColumnModel().getColumn(11).setMaxWidth(x);
	    	this.tabla_quitados.getColumnModel().getColumn(11).setMinWidth(x);
	    	
	    	this.tabla_quitados.getColumnModel().getColumn(12).setMaxWidth(x);
	    	this.tabla_quitados.getColumnModel().getColumn(12).setMinWidth(x);		
	    	this.tabla_quitados.getColumnModel().getColumn(13).setMaxWidth(x);
	    	this.tabla_quitados.getColumnModel().getColumn(13).setMinWidth(x);
	    	this.tabla_quitados.getColumnModel().getColumn(14).setMaxWidth(x);
	    	this.tabla_quitados.getColumnModel().getColumn(14).setMinWidth(x);
	    	this.tabla_quitados.getColumnModel().getColumn(15).setMaxWidth(x);
	    	this.tabla_quitados.getColumnModel().getColumn(15).setMinWidth(x);
	    	this.tabla_quitados.getColumnModel().getColumn(16).setMaxWidth(x);
	    	this.tabla_quitados.getColumnModel().getColumn(16).setMinWidth(x);
	    	this.tabla_quitados.getColumnModel().getColumn(17).setMaxWidth(x);
	    	this.tabla_quitados.getColumnModel().getColumn(17).setMinWidth(x);
	    	this.tabla_quitados.getColumnModel().getColumn(18).setMaxWidth(x*2);
	    	this.tabla_quitados.getColumnModel().getColumn(18).setMinWidth(x*2);
	    	this.tabla_quitados.getColumnModel().getColumn(19).setMaxWidth(x*5);
	    	this.tabla_quitados.getColumnModel().getColumn(19).setMinWidth(x*5);
		}
		
		KeyListener opFiltroDinamico = new KeyListener(){
			public void keyReleased(KeyEvent arg0) {
				
				new Obj_Filtro_Dinamico(tabla_quitados,"F.Asignacion", txtFiltroAsignacion.getText().toUpperCase(),"","", "", "", "", "");
			}
			public void keyTyped(KeyEvent arg0) {}
			public void keyPressed(KeyEvent arg0) {}		
		};
		
		public void opQuitar(final JButton btn){
			
			btn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
					txtFiltroAsignacion.setText("");
					new Obj_Filtro_Dinamico(tabla_quitados,"F.Asignacion", txtFiltroAsignacion.getText().toUpperCase(),"","", "", "", "", "");
					
						quitarCortes=cadenaCortesQuitadosFiltro();
						dispose();
						
					refresh();
				}
			});
			
		}
		
		public String cadenaCortesQuitadosFiltro(){
			String cadena = "";
				if(tabla_quitados.getRowCount()>0){
						for(int i=0; i<tabla_model_quitados.getRowCount(); i++){
							if(tabla_quitados.getValueAt(i, 0).toString().trim().equals("false")){
								cadena+=tabla_model_quitados.getValueAt(i, 2).toString().trim()+"'',''";
							}
						}
					cadena= cadena.equals("")?cadena:cadena.substring(0,cadena.length()-5);
					System.out.println("filtro:   "+cadena);
				}
			return cadena;
		}
		
		public void llenar_tabla_filtro(){
			
			tabla_model_quitados.setRowCount(0);
			
			String[][] matriz = new BuscarTablasModel().tabla_model_cortes_quitados(quitarCortes, grupo_corte);
			for(String[] fila: matriz){
				tabla_model_quitados.addRow(fila);
			}
		}
	}
}
