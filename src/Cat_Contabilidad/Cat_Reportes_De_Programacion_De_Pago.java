package Cat_Contabilidad;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
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

import Conexiones_SQL.Connexion;
import Conexiones_SQL.Generacion_Reportes;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_tabla;

@SuppressWarnings("serial")
public class Cat_Reportes_De_Programacion_De_Pago extends JFrame{
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	JTextField txtFolio        = new Componentes().text(new JCTextField(), "Folio Programacion De Pago", 10, "Int");
	JCButton btnReporte_actual = new JCButton("Generar Reporte" ,"plan-icono-5073-16.png","Azul" );
	JCButton btnFiltro         = new JCButton("Filtro"          ,"Filter-List-icon16.png","Azul" );
	
	int tipo_Reporte = 0;
	
	public Cat_Reportes_De_Programacion_De_Pago(){
		setSize(360,180);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Reporte De Programacion De Pagos");
		setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/presentar-una-pluma-para-escribir-icono-9594-32.png"));
		panel.setBorder(BorderFactory.createTitledBorder("Teclea El Folio De La Programacion De Pago"));
		int x=30;
		
		panel.add(new JLabel("Folio Programacion de Pago:")).setBounds(x     ,25 ,240 ,20);		
		panel.add(txtFolio).setBounds                                 (x     ,50 ,190 ,20);
		panel.add(btnFiltro).setBounds                                (x+195 ,50 ,90  ,20);
		panel.add(btnReporte_actual).setBounds                        (x     ,80 ,285 ,35);
		cont.add(panel);
		
		btnReporte_actual.addActionListener(opGenerar);
		btnFiltro.addActionListener(opFiltro);
	}
	
	ActionListener opGenerar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String basedatos="2.26";
			String vista_previa_reporte="no";
			int vista_previa_de_ventana=0;
			String comando="exec programacion_de_pago_reporte '"+String.valueOf(txtFolio.getText().toUpperCase().trim())+"'"  ;
			String reporte = "Obj_Reportes_De_Programacion_De_Pagos.jrxml";
			
	   		 if(!txtFolio.getText().equals("")){
				  new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
		     }else{
		    	   JOptionPane.showMessageDialog(null,"Necesita Teclear Un Folio de Programacion De Pago","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
		     }
		}
	};
	
	ActionListener opFiltro = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			new Cat_Filtro_Buscar_Programacion_de_Pago().setVisible(true);
		}
	};
	
////////// TODO filtro de programacion de pago	
	public class Cat_Filtro_Buscar_Programacion_de_Pago extends JDialog{
		Container contfb = getContentPane();
		JLayeredPane panelfb = new JLayeredPane();
		Connexion con = new Connexion();
		Obj_tabla ObjTab =new Obj_tabla();
		int columnasb = 4,checkbox=-1;
		public void init_tablafp(){
		this.tablab.getColumnModel().getColumn( 0).setMinWidth(50);
		this.tablab.getColumnModel().getColumn( 0).setMaxWidth(70);
		this.tablab.getColumnModel().getColumn( 1).setMinWidth(130);
		this.tablab.getColumnModel().getColumn( 2).setMinWidth(80);
		this.tablab.getColumnModel().getColumn( 3).setMinWidth(300);
				
		String comandob = "exec programacion_de_pago_filtro_seleccion_por_folio";
		String basedatos="26",pintar="si";
		ObjTab.Obj_Refrescar(tablab,modelob, columnasb, comandob, basedatos,pintar,checkbox);
	}
	
	@SuppressWarnings("rawtypes")
	public Class[] base (){
	  Class[] types = new Class[columnasb];
	    for(int i = 0; i<columnasb; i++){types[i]= java.lang.Object.class;}
	  return types;
	}
	
	public DefaultTableModel modelob = new DefaultTableModel(null, new String[]{"Folio","Fecha","Estatus","Realizo Programacion"}){
	 @SuppressWarnings("rawtypes")
	 Class[] types = base();
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	  public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
	  public boolean isCellEditable(int fila, int columna){return false;}
	};
	
	JTable tablab = new JTable(modelob);
	public JScrollPane scroll_tablab = new JScrollPane(tablab);
	@SuppressWarnings({ "rawtypes" })
	private TableRowSorter trsfiltro;
	
	JTextField txtBuscarb  = new Componentes().textfiltro(new JCTextField() ,">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<" ,500 ,"String" ,tablab ,columnasb );
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Cat_Filtro_Buscar_Programacion_de_Pago(){
		this.setSize(625,400);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setModal(true);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Filter-List-icon32.png"));
		this.panelfb.setBorder(BorderFactory.createTitledBorder("Selecione Un Registro Con Doble Click"));
		this.setTitle("Filtro De Programaciones De Pago");
		trsfiltro = new TableRowSorter(modelob); 
		tablab.setRowSorter(trsfiltro);
		this.panelfb.add(txtBuscarb).setBounds      (10 ,20 ,600 , 20 );
		this.panelfb.add(scroll_tablab).setBounds   (10 ,40 ,600 ,300 );
		this.init_tablafp();
		this.agregar(tablab);
		contfb.add(panelfb);
	}
	
	private void agregar(final JTable tbl) {
	tbl.addMouseListener(new MouseListener() {
	public void mouseReleased(MouseEvent e){
	if(e.getClickCount() == 2){agregarf(); }
	}
	public void mousePressed(MouseEvent e) {}
	public void mouseExited(MouseEvent e)  {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {}
	});
	
	tbl.addKeyListener(new KeyListener() {
	@Override
	public void keyPressed(KeyEvent e)  {
		if(e.getKeyCode()==KeyEvent.VK_ENTER){
		 agregarf();	
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {}
	@Override
	public void keyTyped(KeyEvent e)    {}
	});
	}
	
	private void agregarf() {
	  int fila = tablab.getSelectedRow();
	  txtFolio.setText(tablab.getValueAt(fila,0).toString() );
	  dispose();
	}
	
	}
	
	public static void main(String args[]){
		try{			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Reportes_De_Programacion_De_Pago().setVisible(true);
		}catch(Exception e){	}	}
}
