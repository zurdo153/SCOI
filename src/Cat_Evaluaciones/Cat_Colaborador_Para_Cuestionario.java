package Cat_Evaluaciones;

import java.awt.AWTException;
import java.awt.Container;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Connexion;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_Filtro_Dinamico;
import Obj_Principal.Obj_tabla;

@SuppressWarnings("serial")
public class Cat_Colaborador_Para_Cuestionario extends JFrame {
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JPasswordField pswGafete = new JPasswordField();
	JCButton btnFiltro = new JCButton("", "buscar.png", "Azul");
	
	JLabel JLBlinicio			= new JLabel(new ImageIcon("Imagen/iniciar-icono-4628-16.png") );
	JLabel JLBfin				= new JLabel(new ImageIcon("Imagen/acabado-icono-7912-16.png") );
	JLabel JLBestablecimiento	= new JLabel(new ImageIcon("Imagen/folder-home-home-icone-5663-16.png") );
	JLabel JLBdepartamento		= new JLabel(new ImageIcon("Imagen/departamento-icono-5365-16.png") );
	
	public Cat_Colaborador_Para_Cuestionario(){
		this.setSize(270,130);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("Seleccion De Colaborador");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/articulo-icono-9036-48.png"));
		this.panel.setBorder(BorderFactory.createTitledBorder("Seleccion De Colaborador"));
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Report.png"));

		int x=20, y=25, width=100,height=20;
		
		this.panel.add(new JLabel("Gafete Colaborador:")).setBounds	(x     		,y      ,width ,height  );
		this.panel.add(pswGafete).setBounds                   	   	(x+100 		,y      ,width ,height  );
		this.panel.add(btnFiltro).setBounds                   		(x+100+width,y      ,30    ,20   	);
		
		
		this.cont.add(panel);
		
		pswGafete.addActionListener(opBuscar);
		btnFiltro.addActionListener(opBuscar);
	}
	
	ActionListener opBuscar = new ActionListener() {
		@SuppressWarnings({ })
		public void actionPerformed(ActionEvent e) {
			buscar();
		}
	};
	
	@SuppressWarnings("deprecation")
	public void buscar(){
		System.out.println("Verificar Colaborador");
		if(!pswGafete.getText().toString().trim().equals("")){
			//buscar
			int folio_colaborador = new BuscarSQL().folio_colaborador_para_custionario(pswGafete.getText().toString().trim().toUpperCase());
			if(folio_colaborador>0){
				new Cat_Contestacion_De_Cuestionario(folio_colaborador).setVisible(true);
			}else{
				JOptionPane.showMessageDialog(null,"No Se Encontro El Colaborador","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}
		}else{
			new Cat_Filtro_Empleado().setVisible(true);
		}
	}
	
	//TODO filtro Busqueda de Colaborador
		public class Cat_Filtro_Empleado extends JDialog {
			
			Container cont = getContentPane();
			JLayeredPane campo = new JLayeredPane();
			
			Connexion con = new Connexion();
			Obj_tabla ObjTabf =new Obj_tabla();
			int columnas = 9,checkbox=-1;
			public void init_tablaf(){
		    	this.tablaf.getColumnModel().getColumn(0).setMinWidth(50);
		    	this.tablaf.getColumnModel().getColumn(0).setMaxWidth(50);
		    	this.tablaf.getColumnModel().getColumn(1).setMinWidth(270);
		    	this.tablaf.getColumnModel().getColumn(2).setMinWidth(120);
		    	this.tablaf.getColumnModel().getColumn(3).setMinWidth(250);
				String comandof="exec sp_filtro_empleado";
				String basedatos="26",pintar="si";
				ObjTabf.Obj_Refrescar(tablaf,modelf, columnas, comandof, basedatos,pintar,checkbox);
		    }
			
			@SuppressWarnings("rawtypes")
			public Class[] base (){
				Class[] types = new Class[columnas];
				for(int i = 0; i<columnas; i++){types[i]= java.lang.Object.class;}
				return types;
			}
			
			 public DefaultTableModel modelf = new DefaultTableModel(null, new String[]{"Folio","Colaborador","Establecimiento","Puesto","Sueldo","Bono","Estatus","Fuente Sodas","Gafete"}){
				 @SuppressWarnings("rawtypes")
					Class[] types = base();
					@SuppressWarnings({ "rawtypes", "unchecked" })
					public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
					public boolean isCellEditable(int fila, int columna){return false;}
			    };
			    JTable tablaf = new JTable(modelf);
				public JScrollPane scroll_tablaf = new JScrollPane(tablaf);
			     @SuppressWarnings("rawtypes")
			    private TableRowSorter trsfiltro;
			
			JTextField txtFolioFiltroEmpleado  = new Componentes().text(new JCTextField(), "Teclea Aqui Para Buscar En La Tabla", 500, "String");
			
			String establecimientos[] = new Obj_Establecimiento().Combo_Establecimiento();
			@SuppressWarnings({ "rawtypes", "unchecked" })
			JComboBox cmbEstablecimientos = new JComboBox(establecimientos);

			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Cat_Filtro_Empleado(){
				this.setSize(1040,650);
				this.setModal(true);
				this.setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/filter_icon&16.png"));
				this.setTitle("Filtro de Empleados");
				campo.setBorder(BorderFactory.createTitledBorder("Filtro De Empleado"));
				this.setResizable(false);
				this.setLocationRelativeTo(null);
				this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			
				trsfiltro = new TableRowSorter(modelf); 
				tablaf.setRowSorter(trsfiltro); 
				
				campo.add(scroll_tablaf).setBounds(15,42,1000,565);
				campo.add(txtFolioFiltroEmpleado).setBounds(15,20,300,20);
				campo.add(cmbEstablecimientos).setBounds(315,20, 180, 20);
				
				init_tablaf();
				agregar(tablaf);
				cont.add(campo);
				txtFolioFiltroEmpleado.addKeyListener(opFiltrof);
				cmbEstablecimientos.addActionListener(opFiltro);
//	          asigna el foco al JTextField del nombre deseado al arrancar la ventana
	            this.addWindowListener(new WindowAdapter() {
	                    public void windowOpened( WindowEvent e ){
	                    	txtFolioFiltroEmpleado.requestFocus();
	                 }
	            });
	              
//	         pone el foco en el txtFolio al presionar la tecla scape
	              getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
	                 KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "foco");
	              
	              getRootPane().getActionMap().put("foco", new AbstractAction(){
	                  @Override
	                  public void actionPerformed(ActionEvent e)
	                  {
	                	  txtFolioFiltroEmpleado.setText("");
	                	  txtFolioFiltroEmpleado.requestFocus();
	                  }
	              });
	              
	              tablaf.addKeyListener(seleccionEmpleadoconteclado);
	              
//	            pone el foco en la tabla al presionar f4
	              getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
	                 KeyStroke.getKeyStroke(KeyEvent.VK_F4 , 0), "dtabla");
	              
	              getRootPane().getActionMap().put("dtabla", new AbstractAction(){
	                  @Override
	                  public void actionPerformed(ActionEvent e)
	                  {
	                	tablaf.requestFocus();
	                	iniciarSeleccionConTeclado();
	                  }
	              });
	              
					KeyStroke tab = KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0);
					tablaf.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(tab, "TAB");
					
					tablaf.getActionMap().put("TAB", new AbstractAction(){
		                 public void actionPerformed(ActionEvent e)
		                 {
		                	iniciarSeleccionConTeclado();
		                 }
		            });
			}
			
			private void agregar(final JTable tbl) {
		        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
			        public void mouseClicked(MouseEvent e) {
			        	
			        	if(e.getClickCount() == 1){
			        		iniciarSeleccionConTeclado();
			        	}
			        	if(e.getClickCount() == 2){
			    			fila = tablaf.getSelectedRow();
			    			Object folio =  tablaf.getValueAt(fila, 0).toString().trim();
			    			dispose();
			    			pswGafete.setText(folio+"");
			    			buscar();
			        	}
			        }
		        });
		    }
			
			int fila=0;
			public void iniciarSeleccionConTeclado(){
				Robot robot;
				try {
		            robot = new Robot();
		            robot.keyPress(KeyEvent.VK_A);
		            robot.keyRelease(KeyEvent.VK_A);
		        } catch (AWTException e) {
		            e.printStackTrace();
		        }
	 	     };
	 	     
			KeyListener seleccionEmpleadoconteclado = new KeyListener() {
				@Override
				public void keyTyped(KeyEvent e) {
					
					KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
					tablaf.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(enter, "Enter");
					
					tablaf.getActionMap().put("Enter", new AbstractAction(){
		                 public void actionPerformed(ActionEvent e)
		                 {
		                	iniciarSeleccionConTeclado();
		                	
		                	fila=tablaf.getSelectedRow();
		     				String folio = tablaf.getValueAt(fila,0).toString().trim();
		     					
		     				pswGafete.setText(folio);
		     				btnFiltro.doClick();
		     				dispose();
		                 }
		            });
				}
				public void keyPressed(KeyEvent e){}
				public void keyReleased(KeyEvent e){}
			};
			
			
			KeyListener opFiltrof = new KeyListener(){
				public void keyReleased(KeyEvent arg0) {
					ObjTabf.Obj_Filtro(tablaf, txtFolioFiltroEmpleado.getText().toUpperCase(), columnas);
				}
				public void keyTyped(KeyEvent arg0) {}
				public void keyPressed(KeyEvent arg0) {}		
			};
			
			
			ActionListener opFiltro = new ActionListener(){
				public void actionPerformed(ActionEvent arg0){
					new Obj_Filtro_Dinamico(tablaf,"Nombre Completo", txtFolioFiltroEmpleado.getText().toUpperCase(),"Establecimiento",cmbEstablecimientos.getSelectedItem()+"", "", "", "", "");
				}
			};
			
		}
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Colaborador_Para_Cuestionario().setVisible(true);
		}catch(Exception e){	}
	}

}
