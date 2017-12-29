package Cat_Checador;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

import Conexiones_SQL.Connexion;
import Obj_Checador.Obj_Reporte_Impresion_Gafetes;
import Obj_Principal.JCButton;
import Obj_Principal.Obj_tabla;

@SuppressWarnings("serial")
public class Cat_Gafetes_para_Empleados extends JFrame{
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	Obj_tabla ObjTab =new Obj_tabla();
	
	JCButton btn_Empleados               = new JCButton("F. Colaboradores","asistencia-comunitaria-icono-9465-32.png" ,"Azul");
	JCButton btn_Generar                 = new JCButton("Mision y Vision ","Logo_Izagar32_Circulo.png"           ,"Azul");
	JCButton btn_Generar_Caj_Supers      = new JCButton("Cajas de Super's"   ,"Logo_Izagar32.png"                ,"Azul");
	JCButton btn_Generar_AnaquelesSupers = new JCButton("Abarrote Super's"   ,"Logo_Izagar32.png"                ,"Azul");
	JCButton btn_Generar_Frutasyverduras = new JCButton("F. y V.  Super's"   ,"Logo_Izagar32.png"                 ,"Azul");
	JCButton btn_Generar_Cajas_Papers    = new JCButton("Cajas de Paper's"   ,"Logo_Izagar32.png"                 ,"Azul");
	
	
	JToolBar menu_toolbar       = new JToolBar();
	JToolBar menu_toolbar2      = new JToolBar();
	
	private DefaultTableModel tabla_model = new DefaultTableModel(null,
            new String[]{"Folio", "Nombre Completo","Establecimiento","Puesto"}
			){
		@SuppressWarnings("rawtypes")
		Class[] types = new Class[]{
	    	java.lang.Object.class,
	    	java.lang.Object.class,
	    	java.lang.Object.class,
	    	java.lang.Object.class
         };
	     
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Class getColumnClass(int columnIndex) {
			return types[columnIndex];
		}
        public boolean isCellEditable(int fila, int columna){
        	switch(columna){
        		case 0 : return false; 
        	 	case 1 : return false; 
        		case 2 : return false;
        		case 3 : return false;
        
        	 } 				
 			return false;
 		}
	};
	
	public JTable tabla = new JTable(tabla_model);
	public JScrollPane scroll_tabla = new JScrollPane(tabla);
	
	public Cat_Gafetes_para_Empleados(){
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/reporte_icon&16.png"));
		this.setTitle("Impresión de Gafetes");
		this.panel.setBorder(BorderFactory.createTitledBorder("Impresión de Gafetes"));
		
		ObjTab.tabla_precargada(tabla);
		
		this.menu_toolbar.add(btn_Empleados );
		this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.add(btn_Generar  );
		this.menu_toolbar.addSeparator(       );
		this.menu_toolbar.addSeparator(       );
		this.menu_toolbar.add(btn_Generar_Caj_Supers);
		this.menu_toolbar.addSeparator(       );
		this.menu_toolbar.addSeparator(       );
		this.menu_toolbar.add(btn_Generar_AnaquelesSupers);
		this.menu_toolbar.addSeparator(       );
		this.menu_toolbar.addSeparator(       );
		this.menu_toolbar.add(btn_Generar_Frutasyverduras);
		this.menu_toolbar.setFloatable(false);
		
		this.menu_toolbar2.add(btn_Generar_Cajas_Papers );
		this.menu_toolbar2.addSeparator(   );
		this.menu_toolbar2.addSeparator(   );
		this.menu_toolbar2.setFloatable(false);
		
		int x=10,y=20;
        this.panel.add(menu_toolbar).setBounds (x,y     ,775 ,35); 
        this.panel.add(menu_toolbar2).setBounds(x,y+=35 ,775 ,35); 
  		this.panel.add(scroll_tabla).setBounds (x,y+=65 ,762 ,92);
		
		this.btn_Empleados.addActionListener(op_filtro);
		this.btn_Generar.addActionListener(new opGenerar_Archivo());
		this.btn_Generar_Caj_Supers.addActionListener(new opGenerar_Archivo());
		this.btn_Generar_AnaquelesSupers.addActionListener(new opGenerar_Archivo());
		this.btn_Generar_Frutasyverduras.addActionListener(new opGenerar_Archivo());
		
		
		btn_Generar_Cajas_Papers.setEnabled(false);
		
		
		botones(false);
		this.cont.add(panel);
		
		this.setSize(790,250);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		tabla.getColumnModel().getColumn(0).setMaxWidth(45);
		tabla.getColumnModel().getColumn(0).setMinWidth(45);
		tabla.getColumnModel().getColumn(1).setMaxWidth(335);
		tabla.getColumnModel().getColumn(1).setMinWidth(335);
		tabla.getColumnModel().getColumn(2).setMaxWidth(140);
		tabla.getColumnModel().getColumn(2).setMinWidth(140);
		tabla.getColumnModel().getColumn(3).setMaxWidth(240);
		tabla.getColumnModel().getColumn(3).setMinWidth(240);

	}

	public void botones (boolean valor){
		btn_Generar.setEnabled(valor);
		btn_Generar_Caj_Supers.setEnabled(valor);
		btn_Generar_AnaquelesSupers.setEnabled(valor);
		btn_Generar_Frutasyverduras.setEnabled(valor);
	}	
	
	class opGenerar_Archivo implements ActionListener{   
		JTable tablaparametro;
	    public opGenerar_Archivo(){
	    	
	    }
	    public void actionPerformed(ActionEvent evt){
	    	
	    	String reporte="";
		    if(evt.getActionCommand().equals("Mision y Vision ")){
		    	reporte="Obj_Reporte_De_Gafete_De_Empleados.jrxml";
		    }
		    if(evt.getActionCommand().equals("Cajas de Super's")){
		    	reporte="Obj_Reporte_De_Gafete_De_Empleados_Cajas.jrxml";
		    }
		    if(evt.getActionCommand().equals("Abarrote Super's")){
		    	reporte="Obj_Reporte_De_Gafete_De_Empleados_Anaqueles.jrxml";
		    }
		    
		    if(evt.getActionCommand().equals("F. y V.  Super's")){
		    	reporte="Obj_Reporte_De_Gafete_De_Empleados_FyV.jrxml";
		    }
		    
			new Obj_Reporte_Impresion_Gafetes().buscar_masivo(list_folios().trim());
		    try {
				JasperReport report = JasperCompileManager.compileReport(System.getProperty("user.dir")+"\\src\\Obj_Reportes\\"+reporte);
				@SuppressWarnings({ "unchecked", "rawtypes" })
				JasperPrint print = JasperFillManager.fillReport(report, new HashMap(), new Connexion().conexion());
				JasperViewer.viewReport(print, false);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		    	
	    }
	};

	public String list_folios(){
		String lista = "";
		int cantidad_de_registos=4;
		int cantidad_seleccionada=tabla.getRowCount();
		
		if(cantidad_seleccionada>0){
			for(int i=0; i<cantidad_seleccionada; i++){
				lista += tabla_model.getValueAt(i,0).toString().trim()+", ";
			}
		}
		for(int i=0; i<(cantidad_de_registos-cantidad_seleccionada); i++){
			lista += "0, ";
		}
		return lista;
	}
	
	ActionListener op_filtro = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			new Cat_Filtro_Empleado().setVisible(true);
		}
	};
	
	
//TODO  filtro empleado
	class Cat_Filtro_Empleado extends JFrame {
		Container cont = getContentPane();
		JLayeredPane campo = new JLayeredPane();
		Object[][] Matriz ;

		int columnas = 5,checkbox=5;
		
		public void init_tablafp(){
			tablab.getColumnModel().getColumn(0).setMaxWidth(45);
			tablab.getColumnModel().getColumn(0).setMinWidth(45);
			tablab.getColumnModel().getColumn(1).setMinWidth(335);
			tablab.getColumnModel().getColumn(2).setMinWidth(140);
			tablab.getColumnModel().getColumn(3).setMinWidth(240);
			tablab.getColumnModel().getColumn(4).setMaxWidth(25);
			tablab.getColumnModel().getColumn(4).setMinWidth(25);
	    	String comandof=" exec filtro_colaboradores_vigentes";
			String basedatos="26",pintar="si";
			ObjTab.Obj_Refrescar(tablab,modelob, columnas, comandof, basedatos,pintar,checkbox);
	    }
		
		@SuppressWarnings("rawtypes")
		public Class[] base (){
			Class[] types = new Class[columnas];
			for(int i = 0; i<columnas; i++){if(i==4){types[i]= java.lang.Boolean.class;}else{types[i]= java.lang.Object.class;}  }
			 return types;
		}
		
		public DefaultTableModel modelob = new DefaultTableModel(null, new String[]{"Folio", "Nombre Completo","Establecimiento","Puesto", "S"}){
			 @SuppressWarnings("rawtypes")
				Class[] types = base();
				@SuppressWarnings({ "rawtypes", "unchecked" })
				public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
				public boolean isCellEditable(int fila, int columna){if(columnas==5){return true;}else{return false;}}
		};
		
		JTable tablab = new JTable(modelob);
		public JScrollPane scroll_tablab = new JScrollPane(tablab);
	     @SuppressWarnings({ "rawtypes" })
	    private TableRowSorter trsfiltro;
		JTextField txtNombre_Completo = new JTextField();
		JLabel lblAviso = new JLabel("<html><style>h5{color:blue;}</style><h5>SOLO SE PUEDE SELECCIONAR UN MAXIMO DE 4 COLABORADORES</h5></html>");
		JCButton btnAgregar   = new JCButton("Agregar Empleados"   ,"double-arrow-icone-3883-16.png" ,"Azul");
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Cat_Filtro_Empleado()	{
			setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/filter_icon&16.png"));
			setTitle("Filtro de Colaboradores");
			campo.setBorder(BorderFactory.createTitledBorder("Filtro De Colaboradores"));
			trsfiltro = new TableRowSorter(modelob); 
			tablab.setRowSorter(trsfiltro);  
			
			campo.add(txtNombre_Completo).setBounds(15  ,20 ,259,20);
			campo.add(scroll_tablab).setBounds     (15  ,42 ,810,500);
			campo.add(btnAgregar).setBounds        (324 ,20 ,170, 20);
			campo.add(lblAviso).setBounds          (500 ,20 ,350, 20);
			
			cont.add(campo);
			txtNombre_Completo.addKeyListener(opFiltroNombre);
			btnAgregar.addActionListener(Agregar);
			init_tablafp();
			setSize(850,600);
			setResizable(false);
			setLocationRelativeTo(null);
		}
		
		ActionListener Agregar = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(tabla.isEditing()){tabla.getCellEditor().stopCellEditing();}

				ObjTab.Obj_Filtro(tablab, "", columnas);
				txtNombre_Completo.setText("");
				
				if(valida_select() > 4){
					JOptionPane.showMessageDialog(null,"Solo Puede Seleccionar hasta 4 Colaboradores", "Aviso", JOptionPane.WARNING_MESSAGE);
				}else{
					while(tabla_model.getRowCount() > 0)
						tabla_model.removeRow(0);

					Object[] vectornull = new Object[tabla.getColumnCount()];
					for(int i=0; i<tablab.getRowCount(); i++){
						if(Boolean.parseBoolean(modelob.getValueAt(i, 4).toString()) == true){
							tabla_model.addRow(vectornull);
							for(int j=0; j<tabla.getColumnCount(); j++){
								tabla_model.setValueAt(modelob.getValueAt(i,j), tabla.getRowCount()-1, j);
							}
						}
					}

					botones(true);
					dispose();
				}
			}
		};

		public int valida_select(){
			int contador = 0;
			for(int i=0; i<tablab.getRowCount(); i++){
				if(Boolean.parseBoolean(modelob.getValueAt(i, 4).toString()) == true){
					contador++;
				}
			}
			return contador;
		}
		public int valida_select_2(){
			int contador = 0;
			for(int i=0; i<tablab.getRowCount(); i++){
				if(Boolean.parseBoolean(modelob.getValueAt(i, 4).toString()) == true){
					contador++;
				}
			}
			return contador;
		}
		
		KeyListener opFiltroNombre = new KeyListener(){
			public void keyReleased(KeyEvent arg0) {
				ObjTab.Obj_Filtro(tablab, txtNombre_Completo.getText().toUpperCase().trim(), columnas);
			}
			public void keyTyped(KeyEvent arg0) {}
			public void keyPressed(KeyEvent arg0) {}		
		};
		
	}
///////////////////termina filtro	

	public static void main(String args[]){
		try{UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		new Cat_Gafetes_para_Empleados().setVisible(true);
		}catch(Exception e){System.err.println("Error en Main: "+e.getMessage());
		}
	}
}
