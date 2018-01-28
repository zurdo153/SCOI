package Cat_Auditoria;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_tabla;

@SuppressWarnings("serial")
public class Cat_Filtro_De_Vauchers extends JDialog{
	Container cont = getContentPane();
	JLayeredPane campo = new JLayeredPane();
	Object[][] MatrizFiltro ;
	Obj_tabla ObjTab =new Obj_tabla();
	int columnaspo = 13,checkbox=1;
	public void init_tabla_seleccion_de_vouchers(String establecimiento,String turnos_asignacion ,String tipo_movimiento ,String listado_de_vouchers_pre_seleccionados){
		tabla_vaucher_filtro.getColumnModel().getColumn(0).setMaxWidth(25);
		tabla_vaucher_filtro.getColumnModel().getColumn(0).setMinWidth(25);
		tabla_vaucher_filtro.getColumnModel().getColumn(1).setMinWidth(80);
		tabla_vaucher_filtro.getColumnModel().getColumn(2).setMinWidth(80);
		tabla_vaucher_filtro.getColumnModel().getColumn(3).setMaxWidth(110);
		tabla_vaucher_filtro.getColumnModel().getColumn(3).setMinWidth(110);
		tabla_vaucher_filtro.getColumnModel().getColumn(4).setMaxWidth(60);
		tabla_vaucher_filtro.getColumnModel().getColumn(4).setMinWidth(60);
		tabla_vaucher_filtro.getColumnModel().getColumn(5).setMinWidth(80);
		tabla_vaucher_filtro.getColumnModel().getColumn(6).setMaxWidth(110);
		tabla_vaucher_filtro.getColumnModel().getColumn(6).setMinWidth(110);
		tabla_vaucher_filtro.getColumnModel().getColumn(7).setMaxWidth(100);
		tabla_vaucher_filtro.getColumnModel().getColumn(7).setMinWidth(100);
		tabla_vaucher_filtro.getColumnModel().getColumn(8).setMaxWidth(70);
		tabla_vaucher_filtro.getColumnModel().getColumn(8).setMinWidth(70);
		tabla_vaucher_filtro.getColumnModel().getColumn(9).setMaxWidth(130);
		tabla_vaucher_filtro.getColumnModel().getColumn(9).setMinWidth(130);
		tabla_vaucher_filtro.getColumnModel().getColumn(10).setMaxWidth(70);
		tabla_vaucher_filtro.getColumnModel().getColumn(10).setMinWidth(70);
		tabla_vaucher_filtro.getColumnModel().getColumn(11).setMinWidth(120);
		tabla_vaucher_filtro.getColumnModel().getColumn(12).setMinWidth(120);
		String comandof="sp_IZAGAR_recopilacion_de_vouchers_por_turno '"+establecimiento+"','"+turnos_asignacion+"','"+tipo_movimiento+"','"+listado_de_vouchers_pre_seleccionados+"'";
		System.out.println(comandof);
		
		String basedatos="200",pintar="si";
		ObjTab.Obj_Refrescar(tabla_vaucher_filtro,modelo_vaucher_filtro, columnaspo, comandof, basedatos,pintar,checkbox);
    }
	
	@SuppressWarnings("rawtypes")
	public Class[] baseOr (){
		Class[] types = new Class[columnaspo];
		for(int i = 0; i<columnaspo; i++){  
			if(i==0){
				types[i]=java.lang.Boolean.class;
			}else{
				types[i]=java.lang.Object.class;
			}
		}
		 return types;
	}
	
	public DefaultTableModel modelo_vaucher_filtro = new DefaultTableModel(null, new String[]{"*","Ticket", "Afiliacion", "Numero De Tarjeta", "Fecha E.", "Autorizacion", "Tipo De Tarjeta", "Banco Emisor", "Tipo De Operacion", "Fecha Autorizacion", "Importe","Asignacion/Turno","Retiro cliente"}){
		 @SuppressWarnings("rawtypes")
			Class[] types = baseOr();
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
			public boolean isCellEditable(int fila, int columnaspo){	if(columnaspo==0)return true; return false;}
	};
	
	JTable tabla_vaucher_filtro = new JTable(modelo_vaucher_filtro);
	public JScrollPane scroll = new JScrollPane(tabla_vaucher_filtro);
     @SuppressWarnings({ "rawtypes" })
    private TableRowSorter trsfiltro;
    
	JTextField txtFolioTicket = new Componentes().text(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<"    , 300, "String");
	JButton btnCargar         = new JCButton("Cargar Vouchers","flecha-descargar-blue-icono-4553-16.png","Azul");
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Cat_Filtro_De_Vauchers() {
		this.setSize(1014,430);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setModal(true);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("imagen/tarjeta-de-credito-visa-icono-8242-32.png"));
		this.setTitle("Filtro De Vouchers");
		this.campo.setBorder(BorderFactory.createTitledBorder("Seleccione los Vouchers Deseados"));
		this.trsfiltro = new TableRowSorter(modelo_vaucher_filtro); 
		this.tabla_vaucher_filtro.setRowSorter(trsfiltro); 

		
		campo.add(btnCargar).setBounds     (835 ,15 ,160 ,20 );		
		campo.add(txtFolioTicket).setBounds(10  ,38 ,985 ,20 );
		campo.add(scroll).setBounds        (10  ,60 ,985 ,320);
		cont.add(campo);
		txtFolioTicket.addKeyListener(opFiltro_busqueda);
	}
	
	KeyListener opFiltro_busqueda = new KeyListener(){
		public void keyReleased(KeyEvent arg0) {
			ObjTab.Obj_Filtro(tabla_vaucher_filtro, txtFolioTicket.getText().toUpperCase(), columnaspo);
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}		
	};

	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Filtro_De_Vauchers().setVisible(true);
		}catch(Exception e){
			
		}
	}
}
