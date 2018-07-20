package Cat_Lista_de_Raya;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.BuscarTablasModel;
import Conexiones_SQL.Connexion;
import Obj_Lista_de_Raya.Obj_Alimentacion_De_Vacaciones;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Renders.tablaRenderer;

import com.toedter.calendar.JDateChooser;

@SuppressWarnings("serial")
public class Cat_Alimentacion_De_Vacaciones_Pasadas extends JFrame{

	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	static JTextField txtFolioEmpleado = new Componentes().text(new JCTextField(), "Folio Emp.", 10, "Int");
	static JTextField txtEmpleado = new Componentes().text(new JCTextField(), "Empleado", 200, "String");
	static JTextField txtEstablecimiento = new Componentes().text(new JCTextField(), "Establecimiento", 150, "String");
	static JTextField txtPuesto = new Componentes().text(new JCTextField(), "Puesto", 150, "String");
	
	static JTextField txtVacacionesProximas = new Componentes().text(new JCTextField(), "Años", 10, "Int");
	
	JDateChooser fechaInicio = new Componentes().jchooser(new JDateChooser()  ,"",0);
	JDateChooser fechaRegreso = new Componentes().jchooser(new JDateChooser()  ,"",0);
	
	JCButton btnFiltro = new JCButton("","buscar.png","Azul");
	JCButton btnGuardar = new JCButton("Guardar","Guardar.png","Azul");
	JCButton btnLimpiar = new JCButton("Limpiar","","Azul");
	
	Border blackline;
	
	public Cat_Alimentacion_De_Vacaciones_Pasadas(){
		this.setTitle("Alimentacion De Ultimas Vacaciones");
		this.panel.setBorder(BorderFactory.createTitledBorder(blackline, "Capturar Datos De Ultimas Vacaciones Disfrutadas"));
		
		this.fechaInicio.setIcon(new ImageIcon("Iconos/calendar_icon&16.png"));
		this.fechaRegreso.setIcon(new ImageIcon("Iconos/calendar_icon&16.png"));
		
		int y=25;
		panel.add(new JLabel("Folio Empleado: ")).setBounds(20, y, 100, 20);
		panel.add(txtFolioEmpleado).setBounds(110, y, 80, 20);
		panel.add(btnFiltro).setBounds(190, y, 30, 20);
		
		panel.add(new JLabel("Empleado: ")).setBounds(20, y+=25, 100, 20);
		panel.add(txtEmpleado).setBounds(110, y, 320, 20);
		
		panel.add(new JLabel("Establecimiento: ")).setBounds(20, y+=25, 100, 20);
		panel.add(txtEstablecimiento).setBounds(110, y, 160, 20);
		
		panel.add(new JLabel("Puesto: ")).setBounds(20, y+=25, 100, 20);
		panel.add(txtPuesto).setBounds(110, y, 320, 20);
		
		panel.add(new JLabel("Fecha Inicial: ")).setBounds(20, y+=25, 100, 20);
		panel.add(fechaInicio).setBounds(110, y, 100, 20);
		
		panel.add(new JLabel("Fecha Regreso: ")).setBounds(245, y, 100, 20);
		panel.add(fechaRegreso).setBounds(330, y, 100, 20);
		
		panel.add(new JLabel("Vacaciones Correspondientes Al Año: ")).setBounds(20, y+=25, 185, 20);
		panel.add(txtVacacionesProximas).setBounds(210, y, 40, 20);
		
		panel.add(btnLimpiar).setBounds(245, y+=25, 80, 20);
		panel.add(btnGuardar).setBounds(330, y, 100, 20);
		
		txtEmpleado.setEditable(false);
		txtEstablecimiento.setEditable(false);
		txtPuesto.setEditable(false);
		
		txtFolioEmpleado.addActionListener(opFiltro);
		btnFiltro.addActionListener(opFiltro);
		btnGuardar.addActionListener(opGuardar);
		btnLimpiar.addActionListener(opLimpiar);
		
		//  abre el filtro de busqueda de empleado al presionar la tecla f2
	    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
	       KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), "foco");
	    
	    getRootPane().getActionMap().put("foco", new AbstractAction(){
	        @Override
	        public void actionPerformed(ActionEvent e)
	        {
	        	new Cat_Filtro_Empleados_Vacaciones().setVisible(true);   	
	        }
	    });
		
		cont.add(panel);
		this.setSize(455, 250);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
	}
	
	ActionListener opFiltro = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			
			if(txtFolioEmpleado.getText().equals("")){
					new Cat_Filtro_Empleados_Vacaciones().setVisible(true);
			}else{
				buscarColaborador(Integer.valueOf(txtFolioEmpleado.getText()));
			}
		}
	};
	
	public static void buscarColaborador(int folio_colaborador){

		Obj_Alimentacion_De_Vacaciones alimentacion = new Obj_Alimentacion_De_Vacaciones().buscar_empleado_para_vacaciones(folio_colaborador);
	
		if(alimentacion.getFolio_empleado() != 0){
			txtFolioEmpleado.setText(alimentacion.getFolio_empleado()+"");
			txtEmpleado.setText(alimentacion.getEmpleado());
			txtEstablecimiento.setText(alimentacion.getEstablecimiento());
			txtPuesto.setText(alimentacion.getPuesto());
		}else{
			txtFolioEmpleado.setText("");
			txtFolioEmpleado.requestFocus();
			JOptionPane.showMessageDialog(null, "Solo Puede Alimentar Vacaciones Del Personal VIGENTE", "Mensaje",JOptionPane.WARNING_MESSAGE);
			return;
		}
	}
	
	ActionListener opLimpiar = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			limpiar();
		}
	};
	
	public void limpiar(){
		txtFolioEmpleado.setText("");
		txtEmpleado.setText("");
		txtEstablecimiento.setText("");
		txtPuesto.setText("");
		txtVacacionesProximas.setText("");
		fechaInicio.setDate(null);
		fechaRegreso.setDate(null);
	}
	
	ActionListener opGuardar = new ActionListener(){
			public void actionPerformed(ActionEvent e){
//				cambiar por valida campos
				if(txtEmpleado.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Seleccione Un Empleado", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
					return;
				}else{	
                    
					if(new BuscarSQL().Buscar_Si_Cuenta_Con_Vacaciones(Integer.valueOf(txtFolioEmpleado.getText())) == true/*si existen registros del empleado en la tabla alimentacion de vacaciones avisar joptionpane*/){
						
						JOptionPane.showMessageDialog(null, "No Puede Realizar La Operacion Por Que El Empleado\nYa Tiene Vacaciones Registradas", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
						return;
						
					}else{
						
						Obj_Alimentacion_De_Vacaciones alimentacion = new Obj_Alimentacion_De_Vacaciones().buscar_empleado_para_vacaciones(Integer.valueOf(txtFolioEmpleado.getText()));
						if(alimentacion.getFolio_empleado() == Integer.parseInt(txtFolioEmpleado.getText())){
							
							if(validaCampos()!="") {
								JOptionPane.showMessageDialog(null, "los siguientes campos son requeridos:\n"+validaCampos(), "Error al guardar registro", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
								return;
							}else{
//								codigo para guardar---------------------------------------------------------------------------------------
								
								alimentacion.setFolio_empleado(Integer.valueOf(txtFolioEmpleado.getText().trim()));
								alimentacion.setFecha_inicio(new SimpleDateFormat("dd/MM/yyyy").format(fechaInicio.getDate()));
								alimentacion.setFecha_final(new SimpleDateFormat("dd/MM/yyyy").format(fechaRegreso.getDate()));
								alimentacion.setProximas_vacaciones(Integer.valueOf(txtVacacionesProximas.getText().trim()));
//								guardar			
								if(alimentacion.guardar_ultimas_vacaciones()){
								
									limpiar();
									txtFolioEmpleado.requestFocus();
									JOptionPane.showMessageDialog(null,"El registró se guardó de forma segura","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//Exito.png"));
									return;
						
								}else{
									JOptionPane.showMessageDialog(null, "El registro no se guardó", "Error !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
									return;
								}
							}
						}else{
							JOptionPane.showMessageDialog(null,"El Folio No Corresponde al Empleado","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//Exito.png"));
							return;
						}

					}
				}		
			}
		};
		
		private String validaCampos(){
			String error="";
			String fechaInicioNull = fechaInicio.getDate()+"";
			String fechaFinNull = fechaRegreso.getDate()+"";
			
			if(txtEmpleado.getText().equals(""))error += "Seleccione Un Empleado\n";
			if(fechaInicioNull.equals("null"))error += "Fecha Inicial\n";	
			if(fechaFinNull.equals("null"))error += "Fecha Regreso\n";
			if(txtVacacionesProximas.getText().equals(""))error += "Asigne Años Correspondientes\n";
			
			return error;
		}
	
	public static class Cat_Filtro_Empleados_Vacaciones extends JFrame {
		
		Container cont = getContentPane();
		JLayeredPane panel = new JLayeredPane();
		
		Connexion con = new Connexion();
		
		public static Object[][] get_tabla(){
			return new BuscarTablasModel().tabla_model_empleados_vacaciones();
		}
		
	    public static DefaultTableModel tabla_model = new DefaultTableModel(
	    		get_tabla(),	new String[]{	"Folio",	"Nombre Completo", "Establecimiento", "Puesto"}){
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
	                             return false;
	                     }
	            };
		
		JTable tabla = new JTable(tabla_model);
		JScrollPane panelScroll = new JScrollPane(tabla);
		
		@SuppressWarnings("rawtypes")
		private TableRowSorter trsfiltro;
		
		JTextField txtFiltroColaboradores  = new Componentes().textfiltro(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<", 300, "String",tabla,4);
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Cat_Filtro_Empleados_Vacaciones()	{
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/sun_icon&16.png"));
			this.setTitle("Filtro De Empleado Vigentes");
			panel.setBorder(BorderFactory.createTitledBorder("Seleccionar Un Empleado"));

			this.init_tabla();
			
			trsfiltro = new TableRowSorter(tabla_model); 
			tabla.setRowSorter(trsfiltro);  
			
			panel.add(panelScroll).setBounds(15,42,730,327);
			panel.add(txtFiltroColaboradores).setBounds(15,20,730,20);
			
			agregar(tabla);
			cont.add(panel);
			
			this.setSize(780,415);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			
		}
		
		private void agregar(final JTable tbl) {
	        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
		        public void mouseClicked(MouseEvent e) {
		        	if(e.getClickCount() == 2){
		    			
		        		int fila = tabla.getSelectedRow();
		    			int folio =  Integer.parseInt(tabla.getValueAt(fila, 0)+"");
		    			
		    			buscarColaborador(folio);
		    			dispose();
		        	}
		        }
	        });
	    }
		
		public void init_tabla(){
	        this.tabla.getTableHeader().setReorderingAllowed(false) ;
	        
	        	   int w=60;
	               int x=330;
	               int y=160;
	               
	                this.tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	                
	                this.tabla.getColumnModel().getColumn(0).setMaxWidth(w);
	                this.tabla.getColumnModel().getColumn(0).setMinWidth(w);
	                this.tabla.getColumnModel().getColumn(1).setMaxWidth(x);
	                this.tabla.getColumnModel().getColumn(1).setMinWidth(x);
	                this.tabla.getColumnModel().getColumn(2).setMaxWidth(y);
	                this.tabla.getColumnModel().getColumn(2).setMinWidth(y);
	                this.tabla.getColumnModel().getColumn(3).setMaxWidth(y);
	                this.tabla.getColumnModel().getColumn(3).setMinWidth(y);
	                
	                tabla.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","derecha","Arial","negrita",11));
	                tabla.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",11));
	                tabla.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",11));
	                tabla.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",11));
	}
		
	}

	public static void main(String [] arg){
		
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Alimentacion_De_Vacaciones_Pasadas().setVisible(true);
		}catch(Exception e){
			System.err.println("Error :"+ e.getMessage());
		}
		
	}
}

