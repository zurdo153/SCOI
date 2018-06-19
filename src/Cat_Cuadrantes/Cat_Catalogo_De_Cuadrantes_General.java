package Cat_Cuadrantes;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
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
public class Cat_Catalogo_De_Cuadrantes_General extends JFrame{
			
	Container contfb = getContentPane();
	JLayeredPane panelfb = new JLayeredPane();
	Connexion con = new Connexion();
	Obj_tabla ObjTab =new Obj_tabla();
	int columnasb = 15,checkbox=-1;
	public void init_tablafp(){
    	this.tablab.getColumnModel().getColumn( 0).setMinWidth(20);
    	this.tablab.getColumnModel().getColumn( 0).setMaxWidth(20);
    	this.tablab.getColumnModel().getColumn( 1).setMinWidth(360);
    	this.tablab.getColumnModel().getColumn( 2).setMinWidth(200);
    	this.tablab.getColumnModel().getColumn( 3).setMinWidth(70);
    	this.tablab.getColumnModel().getColumn( 3).setMaxWidth(70);
    	this.tablab.getColumnModel().getColumn( 4).setMinWidth(100);
    	this.tablab.getColumnModel().getColumn( 5).setMinWidth(90);
    	this.tablab.getColumnModel().getColumn( 6).setMinWidth(200);
    	this.tablab.getColumnModel().getColumn( 7).setMinWidth(200);
    	this.tablab.getColumnModel().getColumn( 8).setMinWidth(200);
    	this.tablab.getColumnModel().getColumn( 9).setMinWidth(200);
    	this.tablab.getColumnModel().getColumn(10).setMinWidth(120);
    	String comandof=" exec cuadrantes_filtro_colaboradores_por_cuadrante";
		String basedatos="26",pintar="si";
		ObjTab.Obj_Refrescar(tablab,modelob, columnasb, comandof, basedatos,pintar,checkbox);
    }
	
	@SuppressWarnings("rawtypes")
	public Class[] base (){
		Class[] types = new Class[columnasb];
		for(int i = 0; i<columnasb; i++){types[i]= java.lang.Object.class;}
		 return types;
	}
	
	public DefaultTableModel modelob = new DefaultTableModel(null, new String[]{"Folio","Nombre Cuadrante","Nombre Colaborador","Estatus Colaborador","Establecimiento","Departamento","Puesto Colaborador","Puesto Reporta","Responsabilidades","Objetivos"
			 ,"Estatus","Fecha Guardado","Fecha Ultima Modificacion","Usuario Ultima Modificacion","Folio Colaborador Del Cuadrante"}){
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
	
 	JCButton btnGenerar         = new JCButton("Cuadrante Personal Actividad"    ,"buscar-buscar-ampliar-icono-6234-32.png"     ,"Azul"); 
 	JCButton btnGenerarDetalle  = new JCButton("Cuadrante Personal Descripcion"  ,"buscar-buscar-ampliar-icono-6234-32.png"     ,"Azul"); 
 	
	JTextField txtBuscarb     = new Componentes().text(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<", 500, "String");
	JTextField txtfolio       = new Componentes().text(new JCTextField(), "Folio", 20, "String");
	JTextField txtcolaborador = new Componentes().text(new JCTextField(), "Selecione a Un Colaborador De La Tabla", 500, "String");
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Cat_Catalogo_De_Cuadrantes_General(){
		this.setSize(1024,520);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Filter-List-icon32.png"));
		this.panelfb.setBorder(BorderFactory.createTitledBorder("Selecione Una Fila y De Click a Generar"));
		this.setTitle("Catalogo De Cuadrantes General");
		trsfiltro = new TableRowSorter(modelob); 
		tablab.setRowSorter(trsfiltro);
		int x=10,y=20;
		this.panelfb.add(txtBuscarb).setBounds        (x      ,y     ,1004 , 20 );
		this.panelfb.add(scroll_tablab).setBounds     (x      ,y+=20 ,1004 ,400 );
		this.panelfb.add(txtfolio).setBounds          (x      ,y+=400,55   , 20 );
		this.panelfb.add(txtcolaborador).setBounds    (x+=55  ,y     ,280  , 20 );		
		this.panelfb.add(btnGenerar).setBounds        (x+=300 ,y+=5  ,270  , 35 ); 
		this.panelfb.add(btnGenerarDetalle).setBounds (x+=280 ,y     ,270  , 35 ); 
		this.init_tablafp();
		this.agregar(tablab);
		this.txtBuscarb.addKeyListener  (opFiltropuestos );
		this.btnGenerar.addActionListener(opGenerar);
		this.btnGenerarDetalle.addActionListener(opGenerar);
		this.txtfolio.setEditable(false);
		this.txtcolaborador.setEditable(false);
		contfb.add(panelfb);
	}

	private void agregar(final JTable tbl) {
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
	        	if(e.getClickCount()==1){
	        		int fila = tablab.getSelectedRow();
					    txtfolio.setText   (tablab.getValueAt(fila,14)+"");
					    txtcolaborador.setText   (tablab.getValueAt(fila,2)+"");
	        	}
	        }
        });
    }
	
    private KeyListener opFiltropuestos = new KeyListener(){
		public void keyReleased(KeyEvent arg0) {
			ObjTab.Obj_Filtro(tablab, txtBuscarb.getText(), columnasb,txtBuscarb);
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}		
	};

	ActionListener opGenerar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(txtfolio.getText().toString().equals("")){
				JOptionPane.showMessageDialog(null, "Es requerido Seleccione un Colaborador Primero Para Poder Generar El Reporte", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}
			String basedatos="2.26";
			String vista_previa_reporte="si";
			int vista_previa_de_ventana=0;
			String reporte ="";
			String comando="exec cuadrantes_reporte_por_folio_de_colaborador "+txtfolio.getText()+","+0;
			
			if(e.getActionCommand().equals("Cuadrante Personal Actividad")) {		
			  reporte = "Obj_Reporte_De_Cuadrante_Por_Persona_Por_Dia.jrxml";
			}else {
			  reporte = "Obj_Reporte_De_Cuadrante_Por_Persona_Por_Dia_Detalle_Descripcion.jrxml";
			}
		     new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
		      return;
		     }
	};		

	public static void main(String args[]){
		try{UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Catalogo_De_Cuadrantes_General().setVisible(true);
		}catch(Exception e){System.err.println("Error en Main: "+e.getMessage());
		}
	}
}

	
