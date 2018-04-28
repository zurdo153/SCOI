package Cat_Contabilidad;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Generacion_Reportes;
import Conexiones_SQL.GuardarSQL;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_Filtro_Dinamico_Plus;
import Obj_Principal.Obj_tabla;

@SuppressWarnings("serial")
public class Cat_Corte_Del_Pago_De_Ordenes_De_Gasto  extends JFrame{
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	Obj_tabla ObjTab= new Obj_tabla();
	
	int columnas = 10,checkbox=10;	
	public void refrescar(){
		this.tabla.getColumnModel().getColumn(0).setMaxWidth(40);
		this.tabla.getColumnModel().getColumn(0).setMinWidth(40);
		this.tabla.getColumnModel().getColumn(1).setMinWidth(125);
		this.tabla.getColumnModel().getColumn(2).setMinWidth(60);
		this.tabla.getColumnModel().getColumn(2).setMaxWidth(60);
		this.tabla.getColumnModel().getColumn(3).setMinWidth(200);
		this.tabla.getColumnModel().getColumn(4).setMinWidth(220);
		this.tabla.getColumnModel().getColumn(5).setMinWidth(60);
		this.tabla.getColumnModel().getColumn(6).setMinWidth(105);
		this.tabla.getColumnModel().getColumn(7).setMinWidth(80);
		this.tabla.getColumnModel().getColumn(8).setMinWidth(57);
		this.tabla.getColumnModel().getColumn(8).setMaxWidth(57);
		this.tabla.getColumnModel().getColumn(9).setMaxWidth(20);
		this.tabla.getColumnModel().getColumn(9).setMinWidth(20);
		String comando="exec orden_de_gasto_corte_de_caja_chica '"+cmb_concepto.getSelectedItem().toString().trim()+"'";
		String basedatos="26",pintar="si";
		ObjTab.Obj_Refrescar(tabla,modelo, columnas, comando, basedatos,pintar,checkbox);
    }
	
	@SuppressWarnings("rawtypes")
	public Class[] basemovimientos (){
		Class[] types = new Class[columnas];
		for(int i = 0; i<columnas; i++){
		if(i==9) {types[i]=java.lang.Boolean.class;}else {types[i]= java.lang.Object.class;  }
    	
		}
		 return types;
	}
	public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"Folio", "Fecha", "Importe", "Beneficiario", "Descripcion", "Detalle", "Establecimiento", "Elaboro","Concepto",""}){
		 @SuppressWarnings("rawtypes")
			Class[] types = basemovimientos();
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
			public boolean isCellEditable(int fila, int columna){  if(columna==9) {return true;}else { return false;}}
	};
	
	public JTable tabla = new JTable(modelo);
	private JScrollPane scroll_tabla = new JScrollPane(tabla,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public TableRowSorter trsfiltro = new TableRowSorter(modelo); 
	JTextField txtFiltro= new Componentes().text(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<", 300, "String");
	JTextField txtfoliocorte =new Componentes().text(new JTextField(), "Folio Del Corte De Ordenes de Pago", 10, "Integer");
	JTextField txtfolio_corte_consulta= new Componentes().text(new JCTextField(), "Folio Del Corte>>y Click>>", 300, "Int");
	
	JCButton btnGuardar    = new JCButton("Guardar"      ,"Guardar.png"                       ,"Azul");
	JCButton btnReporte   = new JCButton("Imprimir"     ,"imprimir-16.png"                   ,"Azul");
	
	String conceptos[] = {"GASTO","COMPRA"};
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmb_concepto = new JComboBox(conceptos);
	
	public Cat_Corte_Del_Pago_De_Ordenes_De_Gasto(){
		this.setSize(1024, 710);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/ok-lista-de-verificacion-icono-7906-64.png"));
		this.setTitle("Corte De Ordenes De Pago En Efectivo");
		panel.setBorder(BorderFactory.createTitledBorder("Selecciona Las Ordenes De Pago Que Quieres Incluir En El Corte"));
		this.panel.add(txtFiltro).setBounds                    (10  ,20 ,500  ,20 );
		this.panel.add(cmb_concepto).setBounds                 (520 ,20 ,70   ,20 );
		panel.add(new JLabel("Folio Corte Ordenes De Pago:")).setBounds(600,20,190 ,20 );
		this.panel.add(txtfoliocorte).setBounds                (745 ,20 ,75   ,20 );
		this.panel.add(btnGuardar).setBounds                   (850 ,20 ,145  ,20 );
		this.panel.add(scroll_tabla).setBounds                 ( 10 ,40 ,985  ,600);
		this.panel.add(txtfolio_corte_consulta).setBounds      (700 ,645,145  ,20 );
		this.panel.add(btnReporte).setBounds                   (850 ,645,145  ,20 );
		this.cont.add(panel);
		this.refrescar();
		this.txtfoliocorte.setEditable(false);
		try {
			txtfoliocorte.setText( new BuscarSQL().folio_siguiente(86+""));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		txtFiltro.addKeyListener(opFiltroGeneral);
		btnGuardar.addActionListener(op_guardar);
		btnReporte.addActionListener(opReporte_De_Corte_De_Ordenes_De_Compra);
		cmb_concepto.addActionListener(opCambiar_Concepto);
	}

	ActionListener op_guardar = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			int[] columnas = {0,1,2};
			new Obj_Filtro_Dinamico_Plus(tabla,"", columnas);
			if(tabla.isEditing()){tabla.getCellEditor().stopCellEditing();	}
			
			if(new GuardarSQL().Guardar_Cancelar_Corte_Del_Pago_De_Ordenes_De_Gasto(ObjTab.tabla_guardar(tabla),"T")){
					JOptionPane.showMessageDialog(null, "Se Guardo El Corte De Ordenes De Pago Correctamente","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
					 refrescar();
					try {txtfoliocorte.setText( new BuscarSQL().folio_siguiente(86+""));} catch (SQLException e) {	e.printStackTrace();}
					
					btnReporte.doClick();
					return;
				 }else{
					 JOptionPane.showMessageDialog(null, "Ocurrió un Error al Intentar Guardar El Corte","Avisa Al Administrador Del Sistema",JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
				 	return;
				}
		}
	};
	 
	int foliosiguiente=0;

	 
	ActionListener opReporte_De_Corte_De_Ordenes_De_Compra = new ActionListener(){
		   public void actionPerformed(ActionEvent arg0) {
			    Integer folio=(Integer.valueOf(txtfoliocorte.getText().toString().trim())-1);
			   if(!txtfolio_corte_consulta.getText().toString().trim().equals("")){
				   folio=Integer.valueOf(txtfolio_corte_consulta.getText().toString().trim());
			   }
				String basedatos="2.26";
				String vista_previa_reporte="no";
				int vista_previa_de_ventana=0;
				String reporte = "Obj_Reporte_De_Corte_Caja_Chica_Ordenes_de_Gasto.jrxml";
			    String comando = "exec ordenes_de_pago_reporte_de_corte "+folio;
			 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
		   }
	};
	
	ActionListener opCambiar_Concepto = new ActionListener(){
		   public void actionPerformed(ActionEvent arg0) {
			   refrescar();
		   }
	};
	
	  private KeyListener opFiltroGeneral = new KeyListener(){
			public void keyReleased(KeyEvent arg0) {
				ObjTab.Obj_Filtro(tabla, txtFiltro.getText().toUpperCase(), columnas,txtFiltro);
			}
			public void keyTyped(KeyEvent arg0) {}
			public void keyPressed(KeyEvent arg0) {}		
		};
	
	public static void main(String [] arg){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Corte_Del_Pago_De_Ordenes_De_Gasto().setVisible(true);
		}catch(Exception e){	}
	}

}