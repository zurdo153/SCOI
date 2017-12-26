package Cat_Inventarios;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
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
import Obj_Compras.Obj_Ubicaciones_De_Productos;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_tabla;

@SuppressWarnings("serial")
public class Cat_Existencia_De_Un_Producto_En_Establecimientos extends JFrame{
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	Obj_tabla ObjTab =new Obj_tabla();
	int columnas = 5,checkbox=-1;
	
	@SuppressWarnings("rawtypes")
	public Class[] baselunes (){
		Class[] types = new Class[columnas];
		for(int i = 0; i<columnas; i++){types[i]= java.lang.Object.class;}
		 return types;
	}
	public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"Establecimiento","Localizacion","Minimo","Maximo","Existencia","Costo Prom","Ultimo Costo","Fecha Ultima Vez Agoto","Fecha Ultima Recepcion","Cantidad Ultima Recepcion"}){
		 @SuppressWarnings("rawtypes")
			Class[] types = baselunes();
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
			public boolean isCellEditable(int fila, int columna){return false;}
	};
	JTable tabla = new JTable(modelo);
	public JScrollPane Scroll_Tabla = new JScrollPane(tabla);
     @SuppressWarnings({ "rawtypes", "unused" })
    private TableRowSorter trsfiltro;
     
	
	JTextField txtcodigo_prod          = new Componentes().text(new JCTextField(), "Teclea El Codigo Del Producto", 15, "String");
	JCButton btndeshacer               = new JCButton("Deshacer","deshacer-icono-4321-32.png","Azul");
	JLabel   JLBcod_prod               = new JLabel("");  
	JLabel   JLBdescripcion            = new JLabel("");  
	JLabel   JLBEstatus                = new JLabel("");
	JLabel   JLBClase_De_Producto      = new JLabel("");
	JLabel   JLBCategoria              = new JLabel("");  
	JLabel   JLBFamilia                = new JLabel("");
	JLabel   JLBArea                   = new JLabel("");  
	String htmlini ="<html> <FONT FACE="+"arial"+" SIZE=5 COLOR=BLACK><RIGHT><b><p>";
	String htmlfin ="</p></b></RIGHT></FONT></html>";
	
	String htmlinib ="<html> <FONT FACE="+"arial"+" SIZE=5 COLOR=BLUE><RIGHT><b><p>";
	String htmlfinb ="</p></b></RIGHT></FONT></html>";
	String[][] tablacompleta;
	  Object[]   vector = new Object[10];
	public Cat_Existencia_De_Un_Producto_En_Establecimientos(){
		setSize(800,600);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Datos De Productos BMS");
		setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/articulo-icono-9036-48.png"));
		panel.setBorder(BorderFactory.createTitledBorder("Escanee o Teclee El Codigo Del Producto"));
		

		ObjTab.tabla_precargada_derecha(tabla);
		
		int x=10, y=10, width=400,height=20,sep=25,sep2=130;
		panel.add(txtcodigo_prod).setBounds                                   (x      ,y+=10    ,200     ,height   );
		panel.add(btndeshacer).setBounds                                      (x+520  ,y-5      ,140     ,28       );
		
		width=600;
		panel.add(new JLabel(htmlinib+"CODIGO:"+htmlfinb)).setBounds      	  (x       ,y+=sep  ,width   ,height   );
		panel.add(JLBcod_prod).setBounds                                   	  (x+sep2  ,y       ,width   ,height   );
		panel.add(new JLabel(htmlinib+"ESTATUS:"+htmlfinb)).setBounds	      (x+330   ,y       ,width   ,height   );
		panel.add(JLBEstatus).setBounds                              	      (x+450   ,y       ,width   ,height   );
		
		panel.add(new JLabel(htmlinib+"DESCRIPCION:"+htmlfinb)).setBounds     (x       ,y+=sep+7,width   ,height   );
		panel.add(JLBdescripcion).setBounds                               	  (x+sep2  ,y-=6    ,width   ,37       );
		panel.add(new JLabel(htmlinib+"CLASE:"+htmlfinb)).setBounds       	  (x       ,y+=sep*2,width   ,height   );
		panel.add(JLBClase_De_Producto).setBounds                         	  (x+sep2  ,y       ,width   ,height   );
		panel.add(new JLabel(htmlinib+"CATEGORIA:"+htmlfinb)).setBounds       (x       ,y+=sep  ,width   ,height   );
		panel.add(JLBCategoria).setBounds                                     (x+sep2  ,y       ,width   ,height   );
		panel.add(new JLabel(htmlinib+"FAMILIA:"+htmlfinb)).setBounds         (x       ,y+=sep  ,width   ,height   );	
		panel.add(JLBFamilia).setBounds                                       (x+sep2  ,y       ,width   ,height   );
		panel.add(new JLabel(htmlinib+"AREA:"+htmlfinb)).setBounds            (x       ,y+=sep  ,width   ,height   );	
		panel.add(JLBArea).setBounds                                          (x+sep2  ,y       ,width   ,height   );
		panel.add(Scroll_Tabla).setBounds                                     (x       ,y+=sep  ,775     ,340      );
		
		cont.add(panel);
		btndeshacer.addActionListener(opdeshacer);
		txtcodigo_prod.addKeyListener(Buscar_Datos_Producto);
		
		this.tabla.getColumnModel().getColumn( 0).setMinWidth(170);
		this.tabla.getColumnModel().getColumn( 7).setMinWidth(140);
		this.tabla.getColumnModel().getColumn( 8).setMinWidth(140);
		this.tabla.getColumnModel().getColumn( 9).setMinWidth(120);
		
	}
	
	ActionListener opdeshacer = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			modelo.setRowCount(0);
			JLBcod_prod.setText         ("");
			JLBdescripcion.setText      ("");
			JLBClase_De_Producto.setText("");
			JLBCategoria.setText        ("");
			JLBFamilia.setText          ("");
			JLBArea.setText             ("");
			JLBEstatus.setText          ("");
			txtcodigo_prod.setText      ("");
		   return;	
		}
	};

	public void cargar_datos_tablas(String cod_prod){
		  Obj_Ubicaciones_De_Productos ubicaciones = new Obj_Ubicaciones_De_Productos();
		  String[][] tablacompleta= ubicaciones.Buscar_Existencia_De_Producto_En_Establecimiento(cod_prod);
		  
		    JLBcod_prod.setText         (htmlini+tablacompleta[0][0].toString()+htmlfin);
			JLBdescripcion.setText      (htmlini+tablacompleta[0][1].toString()+htmlfin);
			JLBClase_De_Producto.setText(htmlini+tablacompleta[0][2].toString()+htmlfin);
			JLBCategoria.setText        (htmlini+tablacompleta[0][3].toString()+htmlfin);
			JLBFamilia.setText          (htmlini+tablacompleta[0][4].toString()+htmlfin);
			JLBArea.setText             (htmlini+tablacompleta[0][5].toString()+htmlfin);
			JLBEstatus.setText          (htmlini+tablacompleta[0][6].toString()+htmlfin);

		    for(int i=0;i<tablacompleta.length;i++){
	            for(int j=9,v=0;j<19;j++,v++){ 
	            	vector[v] = tablacompleta[i][j].toString();
	            }
	   			modelo.addRow(vector);
		    }
		
		}
	
	
	
	KeyListener Buscar_Datos_Producto = new KeyListener() {
		public void keyTyped(KeyEvent e){}
		public void keyReleased(KeyEvent e) {}
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				String codigo=txtcodigo_prod .getText().toUpperCase().trim();
				String cod_prod=new BuscarSQL().cod_prod_principal_bms(codigo);
				btndeshacer.doClick();
						if(!cod_prod.equals("false no existe") ){
							cargar_datos_tablas(cod_prod);
							txtcodigo_prod.requestFocus();
						 }else{
								JOptionPane.showMessageDialog(null, "El Codigo "+cod_prod+" Esta Mal Escrito o El Producto No Existe" , "Aviso", JOptionPane.CANCEL_OPTION,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
								txtcodigo_prod.requestFocus();
								return;
		                }
			}
		}
	};
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Existencia_De_Un_Producto_En_Establecimientos().setVisible(true);
		}catch(Exception e){	}
	}
}
