package Cat_Compras;

import java.awt.Container;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.ParseException;
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
import javax.swing.SpinnerDateModel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import com.toedter.calendar.JDateChooser;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Connexion;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Principal.Componentes;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_Filtro_Dinamico_Plus;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Revision_De_Cascos extends JFrame{
//	TODO (Componentes)
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();

	JDateChooser fh_in = new JDateChooser();
	JDateChooser fh_fin = new JDateChooser();
	
    JTextField txtFiltro = new Componentes().text(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla <<<", 300, "String");
    
    JButton btnGenerar = new JButton("Consultar",new ImageIcon("imagen/buscar.png"));
	SpinnerDateModel sdtIn =  new SpinnerDateModel();
	  JSpinner spHoraIn = new JSpinner(sdtIn);                                         
	  JSpinner.DateEditor spDHoraIn = new JSpinner.DateEditor(spHoraIn,"H:mm:ss");  
	  
	SpinnerDateModel sdtFin =  new SpinnerDateModel();
	  JSpinner spHoraFin = new JSpinner(sdtFin);                                         
	  JSpinner.DateEditor spDHoraFin = new JSpinner.DateEditor(spHoraFin,"H:mm:ss");  
	  
	int columnas = 8;
//    TODO Inicializar (modelo)
    public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"F.Producto SCOI", "Descripcion Producto SCOI", "Folio Origen", "Transaccion", "Fecha", "Folio Producto", "Descripcion", "Cantidad"} ){
		@SuppressWarnings({ "rawtypes" })
		Class[] types = new Class[]{
                   java.lang.Object.class,
    };
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
        }
		
    public boolean isCellEditable(int row, int column){
                 return false;
         }
    };
	
    JTable tabla = new JTable(modelo);
	JScrollPane scroll_grupos = new JScrollPane(tabla,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public TableRowSorter trsfiltro = new TableRowSorter(modelo);
	
	
	Border blackline;
	Obj_Usuario usuario = new Obj_Usuario().LeerSession();

	
//	TODO (Contructor)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Cat_Revision_De_Cascos(){
		int anchop = Toolkit.getDefaultToolkit().getScreenSize().width;
		int altop = Toolkit.getDefaultToolkit().getScreenSize().height-50;
		this.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds()); 
		this.setSize(anchop,altop);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Sales-by-payment-method-icon-64.png"));
		this.setTitle("Revision De Movimientos de Cascos");
		this.panel.setBorder(BorderFactory.createTitledBorder( "Revision De Movimientos de Cascos"));
		
		trsfiltro = new TableRowSorter(modelo); 
		tabla.setRowSorter(trsfiltro);  
		
		int x=35,y=25,ancho=168,alto=20;
		
		panel.add(new JLabel("De: ")).setBounds(x+=20, y, alto, alto); 
		panel.add(fh_in).setBounds(x+=20, y, ancho, alto); 
		panel.add(spHoraIn).setBounds(x+=(ancho), y, 80, alto);

		panel.add(new JLabel("A: ")).setBounds(x+=(ancho-70), y, alto, alto); 
		panel.add(fh_fin).setBounds(x+=20, y, ancho, alto); 
		panel.add(spHoraFin).setBounds(x+=(ancho), y, 80, alto);
		
		panel.add(btnGenerar).setBounds(x+=(ancho-80), y, 100, alto);
		
		x=10;
		
		panel.add(txtFiltro).setBounds(x, y+=35, ancho*3, alto);  
		panel.add(scroll_grupos).setBounds(8, y+=20, anchop-25, altop-130);
		
		llamar_render();
		fh_in.setEnabled(false);
		spHoraIn.setEnabled(false);
		tiempodefault();
		
		txtFiltro.addKeyListener(op_filtro_folio_corte);
		
		btnGenerar.addActionListener(opGenerar);
		cont.add(panel);
		

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

	
//	TODO (opGenerar)
	ActionListener opGenerar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
//			if(validar_fechas().equals("")){
//				String fecha_inicio = new SimpleDateFormat("dd/MM/yyyy").format(fh_in.getDate())+" "+new SimpleDateFormat("HH:mm:ss").format(spHoraIn.getValue());
//				String fecha_final = new SimpleDateFormat("dd/MM/yyyy").format(fh_fin.getDate())+" "+new SimpleDateFormat("HH:mm:ss").format(horaFinal.getTime());
//				
//				String fecha_final_principal = fechaFinal_principal+" "+new SimpleDateFormat("HH:mm:ss").format(((Date) spHoraFin.getValue()).getTime());
//				
//				  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
//
//				  Date fecha1 = sdf.parse(fecha_inicio , new ParsePosition(0));
//				  Date fecha2 = sdf.parse(fecha_final , new ParsePosition(0));
//				  Date fecha3 = sdf.parse(fecha_final_principal , new ParsePosition(0));
//
//				if(fecha1.before(fecha2)){
//					
//						if(fecha3.before(fecha2)){	
//							
//							modelo.setRowCount(0);
//							String[][] llenarTabla = new BuscarSQL().buscarCascosPendientes(fecha_inicio, fecha_final);
//							for(String[] casco: llenarTabla){
//								modelo.addRow(casco);
//							}
//						}else{
//							JOptionPane.showMessageDialog(null,"No Se Puede Ingresar una fecha mas reciente","Aviso!", JOptionPane.WARNING_MESSAGE);
//							return;
//						}
//					
//				}else{
//					JOptionPane.showMessageDialog(null,"El Rango de Fechas Esta Invertido","Aviso!", JOptionPane.WARNING_MESSAGE);
//					return;
//				}
//			}else{
//				JOptionPane.showMessageDialog(null,"Los siguientes campos están vacíos: "+validar_fechas(),"Aviso!", JOptionPane.ERROR_MESSAGE);
//				return;
//			}
//
		}
	};
	
	
	public String validar_fechas(){
		String error = "";
		String fechaFinalNull = fh_fin.getDate()+"";
	    if(fechaFinalNull.equals("null"))error+= "Hora final\n";
		return error;
	}
	
	public void llamar_render(){
    	this.tabla.getTableHeader().setReorderingAllowed(false) ;
    	this.tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		for(int i = 0; i<tabla.getColumnCount(); i++){
					tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",11));
		}
		
		int x=110;
      
    	this.tabla.getColumnModel().getColumn(0 ).setMinWidth(x);	
     	this.tabla.getColumnModel().getColumn(0 ).setMaxWidth(x*3);
    	this.tabla.getColumnModel().getColumn(1 ).setMinWidth(x*3); 
    	this.tabla.getColumnModel().getColumn(1 ).setMaxWidth(x*9);  
    	this.tabla.getColumnModel().getColumn(2 ).setMinWidth(x);
    	this.tabla.getColumnModel().getColumn(2 ).setMaxWidth(x*3);
    	this.tabla.getColumnModel().getColumn(3 ).setMinWidth(x*2); 
    	this.tabla.getColumnModel().getColumn(3 ).setMaxWidth(x*4); 
    	this.tabla.getColumnModel().getColumn(4 ).setMinWidth(x*2);
    	this.tabla.getColumnModel().getColumn(4 ).setMaxWidth(x*4);
    	this.tabla.getColumnModel().getColumn(5 ).setMinWidth(x);
    	this.tabla.getColumnModel().getColumn(5 ).setMaxWidth(x*3);
    	this.tabla.getColumnModel().getColumn(6 ).setMinWidth(x*5);
    	this.tabla.getColumnModel().getColumn(6 ).setMaxWidth(x*9);
    	this.tabla.getColumnModel().getColumn(7 ).setMaxWidth(x);
    	this.tabla.getColumnModel().getColumn(7 ).setMinWidth(x);
    	
    	refrestabla_pendientes();
    	                                               
    }
	
	private void refrestabla_pendientes(){
		modelo.setRowCount(0);
		try {
			Connexion con = new Connexion();
			Statement  s = con.conexion().createStatement();
			ResultSet rs = s.executeQuery("exec sp_traspaso_de_movimientos_de_cascos "+usuario.getFolio());
			while (rs.next()){ 
			    	String [] fila = new String[columnas];
					for(int i=0;i<columnas;i++){
					   fila[i] = rs.getString(i+1).trim();
					 }
			   modelo.addRow(fila); 
			}	
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en la funcion refrestabla_pendientes SQLException: "+e1.getMessage(), "Avisa al Administrador Del Sistema", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
		}
	}
	
	KeyListener op_filtro_folio_corte = new KeyListener(){
		public void keyReleased(KeyEvent arg0) {
			int[] columnas = {0,1,2,3,4};
			new Obj_Filtro_Dinamico_Plus(tabla, txtFiltro.getText().toUpperCase(), columnas);
		}
		public void keyTyped(KeyEvent arg0)   {}
		public void keyPressed(KeyEvent arg0) {}		
	};

	public static void main(String[] args) {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Revision_De_Cascos().setVisible(true);
		}catch(Exception e){
			System.err.println("Error :"+ e.getMessage());
		}
	}
	
}
