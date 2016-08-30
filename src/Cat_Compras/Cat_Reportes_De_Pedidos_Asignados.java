package Cat_Compras;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Generacion_Reportes;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_Filtro_Dinamico_Plus;
import Obj_Principal.Obj_Refrescar;

@SuppressWarnings("serial")
public class Cat_Reportes_De_Pedidos_Asignados extends JDialog {
		
		Container cont = getContentPane();
		JLayeredPane campo = new JLayeredPane();
		
		int checkbox=-1;
		@SuppressWarnings("rawtypes")
		public Class[] tipos(int columnas){
			Class[] tip = new Class[columnas];
			
			for(int i =0; i<columnas; i++){
				if(i==checkbox){
					tip[i]=java.lang.Boolean.class;
				}else{
					tip[i]=java.lang.Object.class;
				}
				
			}
			return tip;
		}
		
	 public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"Pedido", "Destino", "Origen","Nombre Surtidor","Cant. Hojas"}){
		 @SuppressWarnings("rawtypes")
			Class[] types = tipos(this.getColumnCount());
			
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Class getColumnClass(int columnIndex) {
	         return types[columnIndex];
	     }
				
	    public boolean isCellEditable(int fila, int columna){
	    	 if(columna ==1)
					return true; return false;
			}
	    };
	    
	    JTable tabla = new JTable(modelo);
		public JScrollPane scroll_tabla = new JScrollPane(tabla);
		
	JTextField txtNombre_Completo2 = new Componentes().text(new JCTextField(), "Filtrar Por Folio De Pedido, Establecimiento Origen y Destino", 250, "String");
	
	JDateChooser fchInicia = new JDateChooser();
	JDateChooser fchFinal = new JDateChooser();
	
	JButton btnActualizar = new JCButton("Actualizar", "", "Azul");
	JButton btnReporte = new JCButton("Generar", "", "Azul");
	
	@SuppressWarnings("rawtypes")
	public Class[] tiposAsignacion(int columnas){
		Class[] tip = new Class[columnas];
		
		for(int i =0; i<columnas; i++){
			if(i==checkbox){
				tip[i]=java.lang.Boolean.class;
			}else{
				tip[i]=java.lang.Object.class;
			}
			
		}
		return tip;
	}
	int paginas = 0;
	public Cat_Reportes_De_Pedidos_Asignados(){
		
		this.setModal(true);
		
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("imagen/usuario-busquedaicono-4661-64.png"));
		this.setTitle("Filtro de Pedidos Asignados");
		campo.setBorder(BorderFactory.createTitledBorder("Filtro De Pedidos Asignados"));
		
		campo.add(txtNombre_Completo2).setBounds(10,20,415,20);
		
		campo.add(new JLabel("De:")).setBounds(430, 20, 30, 20);
		campo.add(fchInicia).setBounds(455,20,100,20);
		campo.add(new JLabel("A:")).setBounds(565, 20, 30, 20);
		campo.add(fchFinal).setBounds(585 ,20,100,20);
		
		campo.add(btnActualizar).setBounds(685,20,100,20);
		campo.add(scroll_tabla).setBounds(10,42,775,220);
		campo.add(btnReporte).setBounds(685,265,100,20);
		
		cont.add(campo);
		
		fchInicia.setDate(cargar_fecha(7));
		fchFinal.setDate(cargar_fecha(0));
		
		init_tabla();
		tamanioColumnas();
		
		btnActualizar.addActionListener(opActualizar);
		btnReporte.addActionListener(opReportePedido);
		txtNombre_Completo2.addKeyListener(op_filtro);
		
		this.setSize(800,320);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
	}
	
	ActionListener opActualizar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			init_tabla();
		}
	};
	
	public Date cargar_fecha(int dias){
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
	
	ActionListener opReportePedido = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			
			String basedatos="2.26";
			String vista_previa_reporte="no";
			int vista_previa_de_ventana=0;
			
			if(tabla.getSelectedRow()<0){
				JOptionPane.showMessageDialog(null,"Necesita Seleccionar Un Pedido", "Mensaje!",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}else{
				
				String comando="exec sp_reporte_de_gestion_de_pedidos '"+tabla.getValueAt(tabla.getSelectedRow(), 0)+"'";
				String reporte = "Obj_Reporte_De_Pedidos_Asignados.jrxml";
				new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
			}
		}
	};
	
	public void tamanioColumnas(){
		
		tabla.getTableHeader().setReorderingAllowed(false) ;
    	tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF );
    	
		this.tabla.getColumnModel().getColumn(0).setMinWidth(50);		
    	this.tabla.getColumnModel().getColumn(1).setMinWidth(170);
    	this.tabla.getColumnModel().getColumn(2).setMinWidth(170);
    	this.tabla.getColumnModel().getColumn(3).setMinWidth(280);
    	this.tabla.getColumnModel().getColumn(4).setMinWidth(50);
	}
	
	public void init_tabla(){
    	
		if(fchInicia.getDate() == null || fchFinal.getDate() == null){
			
			JOptionPane.showMessageDialog(null, "Verifique que las Fechas Se Hayan Ingresado Correcamente", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
			return;

		}else{
			
			String fecha_in = new SimpleDateFormat("dd/MM/yyyy").format(fchInicia.getDate())+" 00:00:00";
			String fecha_fin = new SimpleDateFormat("dd/MM/yyyy").format(fchFinal.getDate())+" 23:59:59";
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm"); 

			  Date fecha1 = sdf.parse(fecha_in , new ParsePosition(0));
			  Date fecha2 = sdf.parse(fecha_fin , new ParsePosition(0));
			
				if(fecha1.before(fecha2)){
					
					modelo.setRowCount(0);
					int columnas = modelo.getColumnCount();
			    	
					String comando="exec sp_select_pedidos_asignados '"+fecha_in+"','"+fecha_fin+"'";
					String basedatos="26",pintar="si";
					new Obj_Refrescar(tabla,modelo, columnas, comando, basedatos,pintar,checkbox);
					
				}else{
					
					JOptionPane.showMessageDialog(null, "Las Fechas Estan Invertidas, Favor De Corregirlas", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
					return;
				}
		}
    } 
	
	KeyListener op_filtro = new KeyListener(){
		public void keyReleased(KeyEvent arg0) {
			int[] columnas ={0,1,2};
			new Obj_Filtro_Dinamico_Plus(tabla, txtNombre_Completo2.getText().toString().trim().toUpperCase(), columnas);
		}
		public void keyTyped(KeyEvent arg0)   {}
		public void keyPressed(KeyEvent arg0) {}		
	};
	
	public static void main(String[] args) {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Reportes_De_Pedidos_Asignados().setVisible(true);
		}catch(Exception e){
			System.err.println("Error :"+ e.getMessage());
		}
	}
	
}

