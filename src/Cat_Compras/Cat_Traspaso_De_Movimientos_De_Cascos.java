package Cat_Compras;

import java.awt.Container;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
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

import com.toedter.calendar.JDateChooser;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.GuardarSQL;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_Filtro_Dinamico_Plus;
import Obj_Principal.Obj_Refrescar;

@SuppressWarnings("serial")
public class Cat_Traspaso_De_Movimientos_De_Cascos extends JFrame{
//	TODO (Componentes)
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	JDateChooser fh_in = new JDateChooser();
    JTextField txtFiltro = new Componentes().text(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla <<<", 300, "String");
    
    JCButton btnGenerar = new JCButton("Traspaso","Aplicar.png");
	SpinnerDateModel sdtIn =  new SpinnerDateModel();
	  JSpinner spHoraIn = new JSpinner(sdtIn);                                         
	  JSpinner.DateEditor spDHoraIn = new JSpinner.DateEditor(spHoraIn,"H:mm:ss");  
	  
    public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"F.Producto SCOI", "Descripcion Producto SCOI", "Folio Origen", "Transaccion", "Fecha", "Folio Producto", "Descripcion", "Cantidad"} ){
    	@SuppressWarnings("rawtypes")
		Class[] types = new Class[]{
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
       	 	case 0 : return false; 
       	 	case 1 : return false; 
       	 	case 2 : return false; 
       	 	case 3 : return false; 
       	 	case 4 : return false; 
       	 	case 5 : return false; 
       	 	case 6 : return false; 
       	 	case 7 : return false; 
       	 }
			return false;
		}
    };
    JTable tabla = new JTable(modelo);
	JScrollPane scroll_grupos = new JScrollPane(tabla,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	Border blackline;
	Obj_Usuario usuario = new Obj_Usuario().LeerSession();
	
	int columnas = 8,checkbox=-1;
	String comando="exec sp_traspaso_de_movimientos_de_cascos "+usuario.getFolio();
	String basedatos="26",pintar="si";
	
//	TODO (Contructor)
	public Cat_Traspaso_De_Movimientos_De_Cascos(){
		int anchop = Toolkit.getDefaultToolkit().getScreenSize().width;
		int altop = Toolkit.getDefaultToolkit().getScreenSize().height-50;
		this.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds()); 
		this.setSize(anchop,altop);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/bajas_altas.png"));
		this.setTitle("Revision De Movimientos de Cascos");
		this.panel.setBorder(BorderFactory.createTitledBorder( "Traspaso De Movimientos de Cascos"));
		int x=35,y=25,ancho=168,alto=20;
		
		panel.add(new JLabel("De: ")).setBounds(x+=20, y, alto, alto); 
		panel.add(fh_in).setBounds(x+=20, y, ancho, alto); 
		panel.add(spHoraIn).setBounds(x+=(ancho), y, 80, alto);
		panel.add(btnGenerar).setBounds(x+=ancho, y, ancho-50, alto);
		
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
		} catch (ParseException e) {
			e.printStackTrace();
		}
		fechas[1].toString().split(":");
		String[] inicioDefault = fechas[1].toString().split(":");
		spHoraIn.setValue(new Time(Integer.parseInt(inicioDefault[0]),Integer.parseInt(inicioDefault[1]),Integer.parseInt(inicioDefault[2])));
		spHoraIn.setEditor(spDHoraIn);
		
		String[] FinDefault = fechas[3].toString().split(":");
		horaFinal = new Time(Integer.parseInt(FinDefault[0]),Integer.parseInt(FinDefault[1]),Integer.parseInt(inicioDefault[2]));
	}

	
//	TODO (opGenerar)
	ActionListener opGenerar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(new GuardarSQL().traspaso_de_movimientos_de_cascos()){
				new Obj_Refrescar(tabla,modelo, columnas, comando, basedatos,pintar,checkbox);
				JOptionPane.showMessageDialog(null,"Se Realizo el Traspaso De Movimientos Correctamente","Aviso", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
				return;
			}else{
				
					JOptionPane.showMessageDialog(null,"Error Al Realizar el Traspaso ","Avise Al Administrador Del Sistema!", JOptionPane.ERROR_MESSAGE, new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
					return;
					
			}		
		}
	};
	
	
	public void llamar_render(){
		int x=110;
    	this.tabla.getColumnModel().getColumn(0 ).setMinWidth(x);	
    	this.tabla.getColumnModel().getColumn(1 ).setMinWidth(x*3); 
    	this.tabla.getColumnModel().getColumn(2 ).setMinWidth(x);
    	this.tabla.getColumnModel().getColumn(3 ).setMinWidth(x*2); 
    	this.tabla.getColumnModel().getColumn(4 ).setMinWidth(x*2);
    	this.tabla.getColumnModel().getColumn(5 ).setMinWidth(x);
    	this.tabla.getColumnModel().getColumn(6 ).setMinWidth(x*5);
    	this.tabla.getColumnModel().getColumn(7 ).setMinWidth(x);

    	new Obj_Refrescar(tabla,modelo, columnas, comando, basedatos,pintar,checkbox);
    }
	
	KeyListener op_filtro_folio_corte = new KeyListener(){
		public void keyReleased(KeyEvent arg0) {
			int[] columnas = {0,1,2,3,4,5,6};
			new Obj_Filtro_Dinamico_Plus(tabla, txtFiltro.getText().toUpperCase(), columnas);
		}
		public void keyTyped(KeyEvent arg0)   {}
		public void keyPressed(KeyEvent arg0) {}		
	};

	public static void main(String[] args) {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Traspaso_De_Movimientos_De_Cascos().setVisible(true);
		}catch(Exception e){
			System.err.println("Error :"+ e.getMessage());
		}
	}
	
}
