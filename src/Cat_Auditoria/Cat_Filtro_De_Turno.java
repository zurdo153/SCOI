package Cat_Auditoria;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
public class Cat_Filtro_De_Turno extends JDialog{
	Container cont = getContentPane();
	JLayeredPane campo = new JLayeredPane();
	
	Object[][] MatrizFiltro ;
	Obj_tabla ObjTab =new Obj_tabla();
	int columnaspo = 9,checkbox=1;
	public void init_tabla_turnos(String parametrop){
		tabla_filtro_turnos.getColumnModel().getColumn(0).setMaxWidth(20);
		tabla_filtro_turnos.getColumnModel().getColumn(0).setMinWidth(20);
		tabla_filtro_turnos.getColumnModel().getColumn(1).setMaxWidth(60);
		tabla_filtro_turnos.getColumnModel().getColumn(1).setMinWidth(60);
		tabla_filtro_turnos.getColumnModel().getColumn(2).setMaxWidth(270);
		tabla_filtro_turnos.getColumnModel().getColumn(2).setMinWidth(270);
		tabla_filtro_turnos.getColumnModel().getColumn(3).setMaxWidth(120);
		tabla_filtro_turnos.getColumnModel().getColumn(3).setMinWidth(120);
		tabla_filtro_turnos.getColumnModel().getColumn(4).setMaxWidth(120);
		tabla_filtro_turnos.getColumnModel().getColumn(4).setMinWidth(120);
		tabla_filtro_turnos.getColumnModel().getColumn(5).setMaxWidth(90);
		tabla_filtro_turnos.getColumnModel().getColumn(5).setMinWidth(90);
		tabla_filtro_turnos.getColumnModel().getColumn(6).setMaxWidth(190);
		tabla_filtro_turnos.getColumnModel().getColumn(6).setMinWidth(190);
		tabla_filtro_turnos.getColumnModel().getColumn(7).setMaxWidth(60);
		tabla_filtro_turnos.getColumnModel().getColumn(7).setMinWidth(60);
		tabla_filtro_turnos.getColumnModel().getColumn(8).setMinWidth(130);
		String comandof="exec sp_IZAGAR_Relacion_de_turnos_para_corte '"+parametrop+"'";
		String basedatos="200",pintar="si";
		ObjTab.Obj_Refrescar(tabla_filtro_turnos,modelo_filtro_turnos, columnaspo, comandof, basedatos,pintar,checkbox);
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
	
	public DefaultTableModel modelo_filtro_turnos = new DefaultTableModel(null, new String[]{"","Turno","Nombre","Fecha Inicial", "Fecha Final","Estatus", "Usuario","Codigo E.", "Establecimiento"}){
		 @SuppressWarnings("rawtypes")
			Class[] types = baseOr();
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
			public boolean isCellEditable(int fila, int columnaspo){	if(columnaspo==0)return true; return false;}
	};
	
	JTable tabla_filtro_turnos = new JTable(modelo_filtro_turnos);
	public JScrollPane scroll_tablafp = new JScrollPane(tabla_filtro_turnos);
     @SuppressWarnings({ "rawtypes" })
    private TableRowSorter trsfiltro;
    
	JTextField txtNombre     = new Componentes().text(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<"    , 300, "String");
	
	JButton btnCargar = new JCButton("Cargar Turnos"                  ,"flecha-descargar-blue-icono-4553-16.png","Azul");
	 String Establecimientopara="";
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Cat_Filtro_De_Turno() {
		setSize(1024,450);
		setResizable(false);
		setLocationRelativeTo(null);
		setModal(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage("imagen/Accounting.png"));
		setTitle("Filtro De Turnos");
		campo.setBorder(BorderFactory.createTitledBorder("Lista De Turnos"));
		trsfiltro = new TableRowSorter(modelo_filtro_turnos); 
		tabla_filtro_turnos.setRowSorter(trsfiltro); 
		 int x=10, y=18,ancho=20;
		 
		campo.add(btnCargar).setBounds     (850 ,y-5  ,150   ,ancho );
		campo.add(txtNombre).setBounds     (x   ,y    ,750   ,ancho );
		campo.add(scroll_tablafp).setBounds(x   ,y+=20,1000  ,365    );
		
		cont.add(campo);
		
		txtNombre.addKeyListener(opFiltro);
		
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened( WindowEvent e ){
            	txtNombre.requestFocus();
           }
        });
	}
	
	KeyListener opFiltro = new KeyListener(){
		public void keyReleased(KeyEvent arg0) {
			ObjTab.Obj_Filtro(tabla_filtro_turnos, txtNombre.getText().toUpperCase(), columnaspo);
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}		
	};
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//			Establecimientopara="REFACCIONARIA";
			new Cat_Filtro_De_Turno().setVisible(true);
		}catch(Exception e){
			
		}
	}
}
