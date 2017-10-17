package Cat_Reportes;

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
public class Cat_Reportes_De_Cuadrante_Personal extends JFrame{
			
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
	
 	JCButton btnGenerar    = new JCButton("Generar Cuadrante Personal"    ,"buscar-buscar-ampliar-icono-6234-32.png"     ,"Azul"); 
 	
	JTextField txtBuscarb     = new Componentes().text(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<", 500, "String");
	JTextField txtfolio       = new Componentes().text(new JCTextField(), "Folio", 20, "String");
	JTextField txtcolaborador = new Componentes().text(new JCTextField(), "Selecione a Un Colaborador De La Tabla", 500, "String");
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Cat_Reportes_De_Cuadrante_Personal(){
		this.setSize(1024,520);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Filter-List-icon32.png"));
		this.panelfb.setBorder(BorderFactory.createTitledBorder("Selecione Una Fila y De Click a Generar"));
		this.setTitle("Reporte De Cuadrante Personal");
		trsfiltro = new TableRowSorter(modelob); 
		tablab.setRowSorter(trsfiltro);
		this.panelfb.add(txtBuscarb).setBounds      (10  , 20 ,1004 , 20 );
		this.panelfb.add(scroll_tablab).setBounds   (10  , 40 ,1004 ,400 );
		this.panelfb.add(txtfolio).setBounds        (10  ,450 ,55   , 20 );
		this.panelfb.add(txtcolaborador).setBounds  (60  ,450 ,280  , 20 );		
		this.panelfb.add(btnGenerar).setBounds      (710 ,450 ,250  , 35 ); 
		this.init_tablafp();
		this.agregar(tablab);
		this.txtBuscarb.addKeyListener  (opFiltropuestos );
		this.btnGenerar.addActionListener(opGenerar);
		txtfolio.setEditable(false);
		txtcolaborador.setEditable(false);
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
			ObjTab.Obj_Filtro(tablab, txtBuscarb.getText(), columnasb);
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
			String comando="exec reporte_de_cuadrante_por_folio_de_colaborador "+txtfolio.getText()+","+0;
			String reporte = "Obj_Reporte_De_Cuadrante_Por_Persona_Por_Dia.jrxml";
			
				     new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
				     return;
		     }
	};		
			
//	public int valida_cantidad_seleccion (){
//		int i=0;
//		for (int y=0; y<tablaFiltro.getRowCount(); y=y+1){
//			if(Boolean.parseBoolean(modeloFiltro.getValueAt(y,5).toString().trim())){
//				i=i+1;
//				folio_empleado= Integer.valueOf(modeloFiltro.getValueAt(y, 0).toString());
//				
//			}
//		}
//		return i;	
//	}
//	
//	public void obtieneNombre_empleado(){
//		
//		for (int y=0; y<tablaFiltro.getRowCount(); y=y+1){
//			if(Boolean.parseBoolean(modeloFiltro.getValueAt(y,5).toString().trim())){
//					nombre_completo= modeloFiltro.getValueAt(y, 1).toString().trim();
//				}
//		}
//	}
//		
//	public  int [] Obtener_empleados_seleccionados (){
//		
//		int [] vector_seleccionados=new int[1] ;
//		int i=0;
//		for (int y=0; y<tablaFiltro.getRowCount(); y=y+1){
//			if(Boolean.parseBoolean(modeloFiltro.getValueAt(y,5).toString().trim()) == true){
//				vector_seleccionados[i]=Integer.parseInt(modeloFiltro.getValueAt(y,0).toString().trim());
//				i++;
//			}
//		}
//		return vector_seleccionados;
//	}
	
	//TODO REVISAR PARA ELIMINAR
//	ActionListener opimprimircuadrante = new ActionListener() {
//		public void actionPerformed(ActionEvent e) {
//			if(valida_cantidad_seleccion ()==1){
//			String query = "exec sp_Reporte_Impresion_Cuadrante_del_dia '"+folio_empleado+"'" ;
//				Statement stmt = null;
//				try {
//					stmt =  new Connexion().conexion().createStatement();
//				    ResultSet rs = stmt.executeQuery(query);
//					JasperReport report = JasperCompileManager.compileReport(System.getProperty("user.dir")+"\\src\\Obj_Reportes\\Obj_Reporte_Impresion_De_Cuadrante.jrxml");
//					JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
//					@SuppressWarnings({ "rawtypes", "unchecked" })
//					JasperPrint print = JasperFillManager.fillReport(report, new HashMap(), resultSetDataSource);
//					JasperViewer.viewReport(print, false);
//				} catch (Exception e1) {
//					System.out.println(e1.getMessage());
//				}
//				}
//			}
//	};
//
//	ActionListener opReporteCaptura = new ActionListener() {
//		@SuppressWarnings({ })
//		public void actionPerformed(ActionEvent arg0) {
//			
//			txtFolio.setText("");
//			txtNombre_Completo.setText("");
//			
//			if(tablaFiltro.isEditing()){
//				tablaFiltro.getCellEditor().stopCellEditing();
//			}
//	
//			if(valida_cantidad_seleccion ()==1){
//	
//	
//	//REVISAR PARA BORRAR EL OBJETO Y EL PROCEDIMIENTO						
//	//						if (new Obj_Imprimir_Cuadrante().Obj_Imprimir_Cuadrante_Update_Folio(folio_empleado)) {
//	
//			  		
//					new Cat_Reporte_De_Cuadrantes("scoi", "scoif",
//							   0, "0",
//							1,"(''" +folio_empleado+"'')",
//							0,"0",
//							0,"0",
//							0,"0",
//							0,"0",
//							0,"0",
//							0);
//	//					}
//			
//			}
//			else{JOptionPane.showMessageDialog(null,"Debe de Seleccionar Un Empleado","Aviso",JOptionPane.NO_OPTION);
//			}				
//		}
//	};
//	ActionListener opReporteCaptura7 = new ActionListener() {
//		public void actionPerformed(ActionEvent arg0) {
//			txtFolio.setText("");
//			txtNombre_Completo.setText("");
//			
//			if(tablaFiltro.isEditing())
//				tablaFiltro.getCellEditor().stopCellEditing();
//	
//			if(valida_cantidad_seleccion()==1){
//				new Cat_Reporte_De_Cuadrantes("scoi7", "scoif7",
//						0, "0",
//						1,"(''" +folio_empleado+"'')",
//						0,"0",
//						0,"0",
//						0,"0",
//						0,"0",
//						0,"0",
//				0);
//			}else{
//				JOptionPane.showMessageDialog(null,"Debe de Seleccionar Un Empleado","Aviso",JOptionPane.NO_OPTION);
//			}
//		}
//				
//	};
	public static void main(String args[]){
		try{UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Reportes_De_Cuadrante_Personal().setVisible(true);
		}catch(Exception e){System.err.println("Error en Main: "+e.getMessage());
		}
	}
}

	
