package Biblioteca;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_Filtro_Dinamico_Plus;
import Obj_Principal.Obj_tabla;

@SuppressWarnings("serial")
public class Cat_Ordenes_De_Gasto_Cancelacion_De_Pagos  extends JFrame{
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	Obj_tabla ObjTab= new Obj_tabla();
	
	int columnas = 9,checkbox=-1;	
	public void refrescar(){
		this.tabla.getColumnModel().getColumn(0).setMaxWidth(40);
		this.tabla.getColumnModel().getColumn(0).setMinWidth(40);
		this.tabla.getColumnModel().getColumn(1).setMinWidth(70);
		this.tabla.getColumnModel().getColumn(2).setMinWidth(60);
		this.tabla.getColumnModel().getColumn(2).setMaxWidth(60);
		this.tabla.getColumnModel().getColumn(3).setMinWidth(210);
		this.tabla.getColumnModel().getColumn(4).setMinWidth(230);
		this.tabla.getColumnModel().getColumn(5).setMinWidth(120);
		this.tabla.getColumnModel().getColumn(6).setMinWidth(105);
		this.tabla.getColumnModel().getColumn(7).setMinWidth(80);
		this.tabla.getColumnModel().getColumn(8).setMinWidth(57);
		this.tabla.getColumnModel().getColumn(8).setMaxWidth(57);
		String comando="exec orden_de_gasto_cancelacion_de_pago '"+cmb_concepto.getSelectedItem().toString().trim()+"'";
		String basedatos="26",pintar="si";
		ObjTab.Obj_Refrescar(tabla,modelo, columnas, comando, basedatos,pintar,checkbox);
    }
	
	@SuppressWarnings("rawtypes")
	public Class[] basemovimientos (){
		Class[] types = new Class[columnas];
		for(int i = 0; i<columnas; i++){
		types[i]= java.lang.Object.class; 
		}
		 return types;
	}
	public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"Folio", "Fecha", "Importe", "Beneficiario", "Descripcion", "Detalle", "Establecimiento", "Elaboro","Concepto"}){
		 @SuppressWarnings("rawtypes")
			Class[] types = basemovimientos();
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
			public boolean isCellEditable(int fila, int columna){  return false;}
	};
	
	public JTable tabla = new JTable(modelo);
	private JScrollPane scroll_tabla = new JScrollPane(tabla,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public TableRowSorter trsfiltro = new TableRowSorter(modelo); 
	JTextField txtFiltro= new Componentes().text(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<", 300, "String");
	
	JTextField txtFolio            = new Componentes().text(new JCTextField(), "Folio"           ,60  , "String");
	JTextField txtFecha            = new Componentes().text(new JCTextField(), "Fecha"           ,60  , "String");
	JTextField txtImporte          = new Componentes().text(new JCTextField(), "Importe"         ,60  , "String");
	JTextField txtBeneficiario     = new Componentes().text(new JCTextField(), "Beneficiario"    ,150 , "String");
	JTextField txtElaboro          = new Componentes().text(new JCTextField(), "Elaboro"         ,150 , "String");
	JTextField txtEstablecimiento  = new Componentes().text(new JCTextField(), "Establecimiento" ,150 , "String");
	JTextField txtConcepto         = new Componentes().text(new JCTextField(), "Concepto"        ,150 , "String");	
	
//	"Folio", "Fecha", "Importe", "Beneficiario", "Descripcion", "Detalle", "Establecimiento", "Elaboro","Concepto"
    JTextArea txaDescripcion = new Componentes().textArea(new JTextArea(), "Descripcion", 160);
    JScrollPane Descripcion  = new JScrollPane(txaDescripcion);
    
    JTextArea txaDetalle     = new Componentes().textArea(new JTextArea(), "Detalle", 160);
    JScrollPane Detalle      = new JScrollPane(txaDetalle);
    
    JTextArea txaMotivo      = new Componentes().textArea(new JTextArea(), "Motivo Cancelación", 160);
    JScrollPane Motivo       = new JScrollPane(txaMotivo);

    JCButton btnCancelar = new JCButton("Cancelar"    ,"cancelar-icono-4961-16.png" ,"Azul"); 
	
	String conceptos[] = {"GASTO","COMPRA"};
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmb_concepto = new JComboBox(conceptos);
	
	public Cat_Ordenes_De_Gasto_Cancelacion_De_Pagos(){
		this.setSize(1024, 610);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/ok-lista-de-verificacion-icono-7906-64.png"));
		this.setTitle("Cancelacion De Pagos En Efectivo");
		panel.setBorder(BorderFactory.createTitledBorder("Selecciona La Orden De Pago Que Se Desea Cancelar"));
		
		int x=10, y=20,width=90,height=20;
		
		this.panel.add(txtFiltro).setBounds                    (x       ,y        ,910      ,height );
		this.panel.add(cmb_concepto).setBounds                 (x+913   ,y        ,70       ,height );
		this.panel.add(scroll_tabla).setBounds                 (x       ,y+=20    ,985      ,290    );
		
		this.panel.add(new JLabel("Descripcion Del Motivo De La Cancelacion Del Pago de Caja Chica:")).setBounds(x ,y+=300 ,350 ,height );
		this.panel.add(btnCancelar).setBounds                  (x+875   ,y-5      ,110      ,height );
		this.panel.add(Motivo).setBounds                       (x       ,y+=20    ,985      ,40     );
		
		this.panel.add(txtFolio).setBounds                     (x       ,y+=60    ,60       ,height );
		this.panel.add(txtFecha).setBounds                     (x+=60   ,y        ,width    ,height );
		this.panel.add(txtConcepto).setBounds                  (x+=90   ,y        ,width    ,height );
		this.panel.add(txtImporte).setBounds                   (x+=90   ,y        ,width    ,height );
		this.panel.add(txtEstablecimiento).setBounds           (x+=90   ,y        ,130      ,height );
		this.panel.add(txtBeneficiario).setBounds              (x+=130  ,y        ,300      ,height );
		this.panel.add(txtElaboro).setBounds                   (x+=300  ,y        ,225      ,height );
		
		this.panel.add(new JLabel("Descripcion De La Orden De Pago:")).setBounds(x=10 ,y+=20,170 ,height );
		this.panel.add(Descripcion).setBounds                  (x       ,y+=20    ,985      ,40     );
		this.panel.add(new JLabel("Detalle De La Orden De Pago:")).setBounds(x=10 ,y+=40,170,height );
		this.panel.add(Detalle).setBounds                      (x       ,y+=20    ,985      ,40     );		
		
		this.txtFolio.setEditable(false);
		this.txtFecha.setEditable(false);
		this.txtConcepto.setEditable(false);
		this.txtImporte.setEditable(false);
		this.txtEstablecimiento.setEditable(false);
		this.txtBeneficiario.setEditable(false);
		this.txtElaboro.setEditable(false);
		this.txaDescripcion.setEditable(false);
		this.txaDetalle.setEditable(false);
		
		this.cont.add(panel);
		refrescar();
		
		agregar(tabla);
		txtFiltro.addKeyListener(opFiltroGeneral);
		btnCancelar.addActionListener(op_guardar);
		cmb_concepto.addActionListener(opCambiar_Concepto);
	}

	private void agregar(final JTable tbl) {
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
	        	if(e.getClickCount()==1){
	        		int fila = tabla.getSelectedRow();
	        		    btnCancelar.setEnabled(true);
	        		    txtFolio.setText(tabla.getValueAt(fila, 0)+"");
	        			txtFecha.setText(tabla.getValueAt(fila,1)+"");
	        		    txtConcepto.setText(tabla.getValueAt(fila, 8)+"");
	        			txtImporte.setText(tabla.getValueAt(fila,2)+"");
	        			txtEstablecimiento.setText(tabla.getValueAt(fila,6)+"");
	        			txtBeneficiario.setText(tabla.getValueAt(fila,3)+"");
	        			txtElaboro.setText(tabla.getValueAt(fila, 7)+"");
	        			txaDescripcion.setText(tabla.getValueAt(fila,4)+"");
	        			txaDetalle.setText(tabla.getValueAt(fila, 5)+"");
						return;
	        	}
	        }
        });
    };
    
	ActionListener op_guardar = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			int[] columnas = {0,1,2};
			new Obj_Filtro_Dinamico_Plus(tabla,"", columnas);
			if(tabla.isEditing()){tabla.getCellEditor().stopCellEditing();	}
//			if(new GuardarSQL().Guardar_Cancelar_Corte_Del_Pago_De_Ordenes_De_Gasto(ObjTab.tabla_guardar(tabla),"T")){
//					JOptionPane.showMessageDialog(null, "Se Guardo El Corte De Ordenes De Pago Correctamente","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
//					 refrescar();
//					return;
//				 }else{
//					 JOptionPane.showMessageDialog(null, "Ocurrió un Error al Intentar Guardar El Corte","Avisa Al Administrador Del Sistema",JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
//				 	return;
//				}
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
			new Cat_Ordenes_De_Gasto_Cancelacion_De_Pagos().setVisible(true);
		}catch(Exception e){	}
	}

}