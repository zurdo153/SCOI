package Cat_Lista_de_Raya;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
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
public class Cat_Reportes_De_Prestamos_De_Lista_De_Raya extends JFrame {
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	String operador[] = {"Selecciona Un Reporte"
							,"Reporte De Prestamos Por Establecimiento de Lista de Raya Actual"
							,"Reporte De Prestamos Por Establecimiento de Listas de Raya Pasadas"
							,"Reporte De Prestamos Para Exportar Por Empleado de Lista de Raya Actual" 
							,"Reporte De Prestamos Por Establecimiento de Lista de Raya Actual Por Estatus"
							,"Reporte De Prestamos Por Colaborador de Listas de Raya" 
							};
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbConcepto = new JComboBox(operador);
	
	JCButton btngenerar_reporte = new JCButton("Generar Reporte En Pantalla","hoja-de-calculo-icono-8865-32.png","Azul");
	JCButton btnFiltro          = new JCButton(""      ,"Filter-List-icon16.png","Azul" );
	
	JLabel JLBlinicio			= new JLabel(new ImageIcon("Imagen/iniciar-icono-4628-16.png") );
	JLabel JLBfin				= new JLabel(new ImageIcon("Imagen/acabado-icono-7912-16.png") );
	JLabel JLBestablecimiento	= new JLabel(new ImageIcon("Imagen/folder-home-home-icone-5663-16.png") );
	JLabel JLBdepartamento		= new JLabel(new ImageIcon("Imagen/departamento-icono-5365-16.png") );
	JTextField txtFolio         = new Componentes().text(new JCTextField()  ,"Folio Colaborador" ,15   ,"Int"   );
	
	public Cat_Reportes_De_Prestamos_De_Lista_De_Raya(){
		this.setSize(470,180);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("Reportes De Prestamos");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/articulo-icono-9036-48.png"));
		this.panel.setBorder(BorderFactory.createTitledBorder("Seleccione  La Fecha, El Tipo de Reporte, el Establecimiento y Genere El Reporte "));
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Report.png"));

		int x=20, y=25, width=100,height=20;
		x=20;width=420;
		this.panel.add(cmbConcepto).setBounds                     (x     ,y      ,width   ,height  );
		this.panel.add(new JLabel("Folio Colaborador:")).setBounds(x     ,y+=30  ,width   ,height  );
		this.panel.add(txtFolio).setBounds                        (x+95  ,y      ,120     ,height  );
		this.panel.add(btnFiltro).setBounds                       (x+215 ,y      ,height  ,height  );
		
		this.panel.add(btngenerar_reporte).setBounds          (x+70  ,y+=40   ,300   ,height*2 );
		this.cont.add(panel);
		
		btngenerar_reporte.addActionListener(opGenerar_reporte);
		btnFiltro.addActionListener(opFiltro_Colaborador);
	}
	
	ActionListener opFiltro_Colaborador = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			new Cat_Filtro_Buscar_Proveedor().setVisible(true);
		}
	};
	
	ActionListener opGenerar_reporte = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
					String basedatos="2.98";
					String vista_previa_reporte="no";
					int vista_previa_de_ventana=0;
					String comando= "";
					String reporte = "";
			
			 if(cmbConcepto.getSelectedIndex()==0){
			       JOptionPane.showMessageDialog(null,"Debe de Seleccionar Un Tipo De Reporte","Aviso!", JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
			        cmbConcepto.requestFocus();
			        cmbConcepto.showPopup();
				    return;		
			      }else{ 
							 String concepto=cmbConcepto.getSelectedItem().toString().trim();
							 
								if(concepto.equals("Reporte De Prestamos Para Exportar Por Empleado de Lista de Raya Actual" )){
										comando="exec sp_Reporte_De_Prestamos_De_Lista_De_Raya_Actual";
										reporte ="Obj_Reporte_De_Prestamos_De_Lista_De_Raya_Actual_Para_Exportar.jrxml";
								   }
								
								if(concepto.equals("Reporte De Prestamos Por Establecimiento de Lista de Raya Actual")){
									comando="exec sp_Reporte_De_Prestamos_De_Lista_De_Raya_Actual";
									reporte ="Obj_Reporte_De_Prestamos_De_Lista_De_Raya_Actual.jrxml";
							   }
								
								if(concepto.equals("Reporte De Prestamos Por Establecimiento de Lista de Raya Actual Por Estatus")){
									comando="sp_Reporte_De_Prestamos_De_Lista_De_Raya_Actual_Por_Estatus";
									reporte ="Obj_Reporte_De_Prestamos_De_Lista_De_Raya_Actual_Por_Estatus.jrxml";
							   }
								
								if(concepto.equals("Reporte De Prestamos Por Colaborador de Listas de Raya" )){
									if(txtFolio.getText().toString().trim().equals("")) {
										JOptionPane.showMessageDialog(null, "Es Requerido Teclee un Folio de Colaborador Para Generar El Reporte","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
									   txtFolio.requestFocus();
										return;
 									}else {
									 comando="prestamos_reporte_de_movimientos_por_empleado "+txtFolio.getText().toString().trim();
									 reporte ="Obj_Reporte_De_Prestamos_De_Listas_De_Raya_Pasadas_Por_Colaborador.jrxml";
									}
							   }
								
								if(concepto.equals("Reporte De Prestamos Por Establecimiento de Listas de Raya Pasadas"	)){
								    new Cat_Filtro_De_Listas_De_Raya_Pasadas(2).setVisible(true);   
								    return;
							   }
								
		       }
			    new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
			   return;
		}
	};
	
	
		public void Impresion_de_Reporte_Prestamos_LRPasadas(int folio) {
			String basedatos="2.98";
			String vista_previa_reporte="no";
			int vista_previa_de_ventana=0;
			String comando = "exec sp_consulta_de_prestamos_de_listas_de_raya_pasadas '"+folio+"';";
			String reporte ="Obj_Reporte_De_Prestamos_De_Listas_De_Raya_Pasadas.jrxml";
		    new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
		 }

		//TODO inicia filtro_colabroador	
		public class Cat_Filtro_Buscar_Proveedor extends JDialog{
			Container contfb = getContentPane();
			JLayeredPane panelfb = new JLayeredPane();
			Connexion con = new Connexion();
			Obj_tabla ObjTab =new Obj_tabla();
			int columnasb = 2,checkbox=-1;
			public void init_tablafp(){
		    	this.tablab.getColumnModel().getColumn( 0).setMinWidth(55);
		    	this.tablab.getColumnModel().getColumn( 1).setMinWidth(350);
				 String comandob=" select folio,nombre+' '+ap_paterno+' '+ap_materno as nombre from tb_empleado order by nombre"; 
				String basedatos="201",pintar="si";
				ObjTab.Obj_Refrescar(tablab,modelob, columnasb, comandob, basedatos,pintar,checkbox);
		    }
			
			@SuppressWarnings("rawtypes")
			public Class[] base (){
				Class[] types = new Class[columnasb];
				for(int i = 0; i<columnasb; i++){types[i]= java.lang.Object.class;}
				 return types;
			}
			
			public DefaultTableModel modelob = new DefaultTableModel(null, new String[]{"Folio","Colaborador"}){
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
			     
			JTextField txtBuscarb  = new Componentes().text(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<", 500, "String");
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Cat_Filtro_Buscar_Proveedor(){
				this.setSize(475,250);
				this.setResizable(false);
				this.setLocationRelativeTo(null);
				this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				this.setModal(true);
				this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Filter-List-icon32.png"));
				this.panelfb.setBorder(BorderFactory.createTitledBorder("Selecione Un Registro Con Doble Click"));
				this.setTitle("Filtro De Colaborador");
				trsfiltro = new TableRowSorter(modelob); 
				tablab.setRowSorter(trsfiltro);
				this.panelfb.add(txtBuscarb).setBounds      (10 ,20 ,450 , 20 );
				this.panelfb.add(scroll_tablab).setBounds   (10 ,40 ,450 ,170 );
				this.init_tablafp();
				this.agregar(tablab);
				this.txtBuscarb.addKeyListener  (opFiltrempleado );
				contfb.add(panelfb);
			}
			
			private void agregar(final JTable tbl) {
		        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
			        	if(e.getClickCount()==1){
			        		int fila = tablab.getSelectedRow();
			        		txtFolio.setText   (tablab.getValueAt(fila,0)+"");
							dispose();
			        	}
			        }
		        });
		    }
			
	        private KeyListener opFiltrempleado = new KeyListener(){
				public void keyReleased(KeyEvent arg0) {
					ObjTab.Obj_Filtro(tablab, txtBuscarb.getText().toUpperCase(), columnasb,txtBuscarb);
				}
				public void keyTyped(KeyEvent arg0) {}
				public void keyPressed(KeyEvent arg0) {}		
			};
		}

		
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Reportes_De_Prestamos_De_Lista_De_Raya().setVisible(true);
		}catch(Exception e){	}
	}
}
