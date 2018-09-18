package Cat_Contabilidad;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import Obj_Principal.Componentes;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_tabla;

import com.toedter.calendar.JDateChooser;
import Conexiones_SQL.Generacion_Reportes;

@SuppressWarnings("serial")
public class Cat_Reimpresion_De_Pagos_De_Caja_Chica extends JDialog{
	Container cont = getContentPane();
	JLayeredPane campo = new JLayeredPane();
	Obj_tabla  Objetotabla = new Obj_tabla();

	int columnas = 10,checkbox=-1;
	public void init_tabla(){
		String fechaInicial = new SimpleDateFormat("dd/MM/yyyy").format(fhFechaReporte.getDate())+" 00:00:00";
    	this.tabla_Filtro_Ref.getColumnModel().getColumn(1).setMinWidth(130);
    	this.tabla_Filtro_Ref.getColumnModel().getColumn(3).setMinWidth(240);
    	this.tabla_Filtro_Ref.getColumnModel().getColumn(4).setMinWidth(400);
    	this.tabla_Filtro_Ref.getColumnModel().getColumn(5).setMinWidth(130);
     	this.tabla_Filtro_Ref.getColumnModel().getColumn(6).setMinWidth(250);
     	this.tabla_Filtro_Ref.getColumnModel().getColumn(7).setMinWidth(130);
     	this.tabla_Filtro_Ref.getColumnModel().getColumn(8).setMinWidth(250);
     	this.tabla_Filtro_Ref.getColumnModel().getColumn(9).setMinWidth(130);
		String comando="exec orden_de_gasto_pago_en_efectivo_filtro '"+fechaInicial+"'";
		String basedatos="26",pintar="si";
		Objetotabla.Obj_Refrescar(tabla_Filtro_Ref,modelo_Filtro_Ref, columnas, comando, basedatos,pintar,checkbox);
    }
	
	@SuppressWarnings("rawtypes")
	public Class[] tipos(){
		Class[] tip = new Class[columnas];
		for(int i =0; i<columnas; i++){
				tip[i]=java.lang.Object.class;
		}
		return tip;
	}
	
	DefaultTableModel modelo_Filtro_Ref = new DefaultTableModel(null,new String[]{"Folio", "Fecha", "Importe", "Beneficiario", "Observaciones Pago","Folio Orden De Gasto","Usuario Realizo Pago", "Folio Corte Caja Chica", "Usuario Realizo Corte", "Folio Banco Interno"}
			){
		 @SuppressWarnings("rawtypes")
			Class[] types = tipos();
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {
	         return types[columnIndex];
	     }
			public boolean isCellEditable(int fila, int columna){
				if(columna==0)
					return true; return false;
			}
	    };
	    
	JTable tabla_Filtro_Ref = new JTable(modelo_Filtro_Ref);
    JScrollPane scroll_Filtro_Ref = new JScrollPane(tabla_Filtro_Ref);
	
	JTextField txtFiltro     = new Componentes().textfiltro(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<",300 , "String",tabla_Filtro_Ref,columnas );
	JDateChooser fhFechaReporte  = new Componentes().jchooser(new JDateChooser()  ,"Fecha"  ,7);
	
	public Cat_Reimpresion_De_Pagos_De_Caja_Chica(){			
		this.setModal(true);			
		this.setSize(790,650);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Filter-List-icon32.png"));
		this.setTitle("Filtro De Orden De Pago En Efectivo");
		this.campo.setBorder(BorderFactory.createTitledBorder("Para Generar Un Reporte De Orden De Pago En Efectivo > Dar Doble Click ó Presionar Enter En La Orden Requerida"));
		campo.add(new JLabel("Buscar Ordenes De Pago En Efectivo A Partir De La Fecha: ")).setBounds(20,20,390,20);
		campo.add(fhFechaReporte).setBounds   (320,20 , 90 , 20);			
		campo.add(txtFiltro).setBounds        (10 ,45 ,760 , 20);			
		campo.add(scroll_Filtro_Ref).setBounds(10 ,65 ,760 ,540);
		init_tabla();
		agregar(tabla_Filtro_Ref);
		cont.add(campo);
		fhFechaReporte.addPropertyChangeListener(opBusqueda);
		
        this.addWindowListener(new WindowAdapter() {
                public void windowOpened( WindowEvent e ){
                	txtFiltro.requestFocus();
             }
        });
	}
	
	PropertyChangeListener opBusqueda = new PropertyChangeListener() {
	  	  public void propertyChange(PropertyChangeEvent e) {
  	            if ("date".equals(e.getPropertyName())){
  	            	init_tabla();
  	            }
	  	  }
	};
	
	private void agregar(final JTable tbl) {
		tbl.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
		 	 if(e.getClickCount() == 2){funcion_agregar();}
			}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e)  {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {}
		});
		
		tbl.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e)  {
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					funcion_agregar();	
				}
			}
			public void keyReleased(KeyEvent e)   {}
			public void keyTyped   (KeyEvent e)   {}
		});
    }

	public void funcion_agregar() {
		String comando="exec orden_de_gasto_reporte_de_pago_en_efectivo "+tabla_Filtro_Ref.getValueAt( tabla_Filtro_Ref.getSelectedRow(), 0).toString().trim() ;
		String reporte = "Obj_Reporte_De_Orden_De_Gasto_Pago_En_Efectivo.jrxml";
		new Generacion_Reportes().Reporte(reporte, comando, "2.26", "no",0);
    }
	     
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Reimpresion_De_Pagos_De_Caja_Chica().setVisible(true);
		}catch(Exception e){	}
	}

}
