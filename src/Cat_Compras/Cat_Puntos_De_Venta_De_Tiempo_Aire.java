package Cat_Compras;

import java.awt.Container;
import java.awt.Event;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Conexiones_SQL.Connexion;
import Obj_Compras.Obj_Puntos_De_Venta_De_Tiempo_Aire;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.Componentes;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_Filtro_Dinamico_Plus;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Puntos_De_Venta_De_Tiempo_Aire extends JFrame{
	int foliosiguiente=0;
	String Activo ="";
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JTextField txtFolio = new Componentes().text(new JCTextField(), "Folio", 9, "Int");
	JTextField txtNombre_pc= new Componentes().text(new JCTextField(), "Nombre Computadora",80,"String");
	JTextField txtfiltro_tabla = new Componentes().text(new JCTextField(), "Teclea Aqui Para Buscar En La Tabla", 30, "String");
	JTextField txtNombre_Punto_De_Venta= new Componentes().text(new JCTextField(), "Nombre Punto De Venta De Tiempo Aire",80,"String");
	
	JLabel JLBactivo= new JLabel();
	
	String status[] = {"VIGENTE","CANCELADO"};
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmb_status = new JComboBox(status);
	
	String establecimiento[] = new Obj_Establecimiento().Combo_Establecimiento();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbEstablecimiento = new JComboBox(establecimiento);
	
	JButton btnBuscar = new JButton("Buscar",new ImageIcon("imagen/buscar.png"));
	JButton btnSalir = new JButton("Salir",new ImageIcon("imagen/salir16.png"));
	JButton btnDeshacer = new JButton("Deshacer",new ImageIcon("imagen/deshacer16.png"));
	JButton btnGuardar = new JButton("Guardar",new ImageIcon("imagen/Guardar.png"));
	JButton btnEditar = new JButton("Editar",new ImageIcon("imagen/editara.png"));
	JButton btnNuevo = new JButton("Nuevo",new ImageIcon("imagen/Nuevo.png"));
	
	 public static DefaultTableModel modelo = new DefaultTableModel(null,new String[]{"Folio","Establecimiento", "Computadora","Punto Venta","Estatus"}){
	            @SuppressWarnings("rawtypes")
	            Class[] types = new Class[]{
	                       java.lang.Object.class,
	                       java.lang.Object.class,  
	                       java.lang.Object.class, 
	                       java.lang.Object.class, 
	                       java.lang.Object.class   
	        };
	            @SuppressWarnings({ "rawtypes", "unchecked" })
	            public Class getColumnClass(int columnIndex) {
	                    return types[columnIndex];
	            }
	        public boolean isCellEditable(int fila, int columna){
	                    switch(columna){
	                            case 0  : return false; 
	                            case 1  : return false; 
	                            case 2  : return false; 
	                            case 3  : return false;
	                            case 4  : return false; 
	                    }
	                     return false;
	             }
	    };
		JTable tabla = new JTable(modelo);
		JScrollPane scrollAsignado = new JScrollPane(tabla);
		@SuppressWarnings("rawtypes")
		private TableRowSorter trsfiltro;
		
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Cat_Puntos_De_Venta_De_Tiempo_Aire(){
			this.setSize(1000,345);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/telefono-celular-hp-ipaq-2-de-telefonia-movil-icono-6906-64.png"));
			panel.setBorder(BorderFactory.createTitledBorder("Escribe El Nombre De Una Computadora, El Punto De Venta y Selecciona El Establecimiento Al Que Le Corresponde"));
			
			this.setTitle("Puntos De Venta De Tiempo Aire");
			trsfiltro = new TableRowSorter(modelo); 
			tabla.setRowSorter(trsfiltro);
			
			int x = 45, y=30, ancho=100;
			
			panel.add(new JLabel("Folio Del Registro:")).setBounds(x-25,y-5,ancho,20);
			panel.add(txtFolio).setBounds(x+-25,y+=20,250,20);
			panel.add(btnBuscar).setBounds(277,y,98,20);
			
			panel.add(new JLabel("Nombre De La Computadora:")).setBounds(x-25,y+=30,150,20);
			panel.add(txtNombre_pc).setBounds(x+-25,y+=20,250,20);
			panel.add(cmb_status).setBounds(277,y,98,20);
			
			panel.add(new JLabel("Nombre Del Punto De Venta:")).setBounds(x-25,y+=30,150,20);
			panel.add(txtNombre_Punto_De_Venta).setBounds(x-25,y+=20,250,20);
			panel.add(btnNuevo).setBounds(277,y,98,20);
			
			panel.add(new JLabel("Nombre Del Establecimiento:")).setBounds(x-25,y+=30,150,20);
			panel.add(cmbEstablecimiento).setBounds(x-25,y+=20,250,20);

			panel.add(btnDeshacer).setBounds(277,y,98,20);
			panel.add(btnGuardar).setBounds(x-25,y+=55,100,20);
			panel.add(btnEditar).setBounds(x+105,y,100,20);
			panel.add(btnSalir).setBounds(277,y,98,20);
			
			panel.add(txtfiltro_tabla).setBounds(380,15,400,20);
			
			panel.add(getPanelTabla()).setBounds((x*2)+(ancho*3)-10,35,603,240);
			
			txtNombre_pc.setEditable(false);
			cmb_status.setEnabled(false);
			cmbEstablecimiento.setEnabled(false);
			btnEditar.setEnabled(false);
			txtNombre_Punto_De_Venta.setEnabled(false);
			
			btnGuardar.addActionListener(guardar);
			btnSalir.addActionListener(salir);
			btnDeshacer.addActionListener(deshacer);
			btnNuevo.addActionListener(nuevo);
			btnEditar.addActionListener(editar);
			btnBuscar.addActionListener(buscar);
			
			txtFolio.addKeyListener(buscar_action);
			
			txtfiltro_tabla.addKeyListener(opFiltroGeneral);
			txtNombre_pc.addKeyListener(enterpasaraPunto_De_Venta);
			txtNombre_Punto_De_Venta.addKeyListener(enterpasaraEstablecimiento);
			cmbEstablecimiento.addKeyListener(enterpasaraNombre_Pc);

			agregar(tabla);
			cont.add(panel);
			
              ///asigna el foco al filtro
						 this.addWindowListener(new WindowAdapter() {
				                public void windowOpened( WindowEvent e ){
				                	txtFolio.requestFocus();
				             }
				        });
		
             ///deshacer con escape
			             getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
			             getRootPane().getActionMap().put("escape", new AbstractAction(){
			                 public void actionPerformed(ActionEvent e)
			                 {                 	    btnDeshacer.doClick();
			               	    }
			             });
			             
			///guardar con F2
			              getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), "buscar");
			                  getRootPane().getActionMap().put("buscar", new AbstractAction(){
			                      public void actionPerformed(ActionEvent e)
			                      {                 	    btnBuscar.doClick();
				                    	    }
			                 });
			                  
			                  
             ///guardar con control+G
			             getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_G,Event.CTRL_MASK),"guardar");
			                  getRootPane().getActionMap().put("guardar", new AbstractAction(){
			                      public void actionPerformed(ActionEvent e)
			                      {                 	    btnGuardar.doClick();
			                    	    }
			                 });
			                  
		    ///guardar con F12
				              getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0), "guardar");
				                  getRootPane().getActionMap().put("guardar", new AbstractAction(){
				                      public void actionPerformed(ActionEvent e)
				                      {                 	    btnGuardar.doClick();
					                    	    }
				                 });
			                  
			///nuevo con F9
			              getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F9, 0), "nuevo");
			                  getRootPane().getActionMap().put("nuevo", new AbstractAction(){
			                      public void actionPerformed(ActionEvent e)
			                      {                 	    btnNuevo.doClick();
				                    	    }
			                 });
			                  
		      ///nuevo con F10
				              getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F10, 0), "editar");
				                  getRootPane().getActionMap().put("editar", new AbstractAction(){
				                      public void actionPerformed(ActionEvent e)
				                      {                 	    btnEditar.doClick();
					                    	    }
				                 });
			///editar con Ctrl+E
				              getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_E,Event.CTRL_MASK), "editar");
				                  getRootPane().getActionMap().put("editar", new AbstractAction(){
				                      public void actionPerformed(ActionEvent e)
				                      {                 	    btnEditar.doClick();
					                    	    }
				                 });
			                  
			 ///nuevo con control+N
			              getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_N,Event.CTRL_MASK),"nuevo");
			                   getRootPane().getActionMap().put("nuevo", new AbstractAction(){
			                       public void actionPerformed(ActionEvent e)
			                       {                 	    btnNuevo.doClick();
				                    	    }
			                 });
	}
	
	private JScrollPane getPanelTabla()	{	
		
			tabla.getTableHeader().setReorderingAllowed(false) ;
			tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			
		    this.tabla.getColumnModel().getColumn(0).setMinWidth(40);
		    this.tabla.getColumnModel().getColumn(0).setMaxWidth(40);
		    this.tabla.getColumnModel().getColumn(1).setMinWidth(180);
		    this.tabla.getColumnModel().getColumn(1).setMaxWidth(500);
		    this.tabla.getColumnModel().getColumn(2).setMinWidth(180);
		    this.tabla.getColumnModel().getColumn(2).setMaxWidth(500);
		    this.tabla.getColumnModel().getColumn(3).setMinWidth(100);
		    this.tabla.getColumnModel().getColumn(3).setMaxWidth(500);
		    this.tabla.getColumnModel().getColumn(4).setMinWidth(100);
		    this.tabla.getColumnModel().getColumn(4).setMaxWidth(100);
		    
		    tabla.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","centro","Arial","normal",12)); 	
		    tabla.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","centro","Arial","normal",12)); 
		    tabla.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","centro","Arial","normal",12)); 
		    tabla.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","centro","Arial","normal",12)); 
		    tabla.getColumnModel().getColumn(4).setCellRenderer(new tablaRenderer("texto","centro","Arial","normal",12)); 
			
							refrestabla();
					 JScrollPane scrol = new JScrollPane(tabla);
				    return scrol; 
	    
	    }
	private void refrestabla(){
		Statement s;
		ResultSet rs;
		try {
			Connexion con = new Connexion();
			s = con.conexion().createStatement();
			rs = s.executeQuery("select tb_pc_asignadas_a_un_punto_de_venta_TA.folio"
					+ "      ,tb_establecimiento.nombre"
					+ "	  ,tb_pc_asignadas_a_un_punto_de_venta_TA.nombre_pc"
					+ "	  ,tb_pc_asignadas_a_un_punto_de_venta_TA.nombre_punto_de_venta"
					+ "	  ,case when tb_pc_asignadas_a_un_punto_de_venta_TA.status='1' then (select 'VIGENTE') when tb_pc_asignadas_a_un_punto_de_venta_TA.status=0 then (select 'CANCELADO') end as status"
					+ "	   from tb_pc_asignadas_a_un_punto_de_venta_TA"
					+ " inner join tb_establecimiento on tb_establecimiento.folio=tb_pc_asignadas_a_un_punto_de_venta_TA.folio_establecimiento order by tb_establecimiento.nombre asc");
			while (rs.next())
			{ 
			   String [] fila = new String[5];
			   fila[0] = rs.getString(1).trim();
			   fila[1] = rs.getString(2).trim();
			   fila[2] = rs.getString(3).trim(); 
			   fila[3] = rs.getString(4).trim();
			   fila[4] = rs.getString(5).trim();
			   modelo.addRow(fila); 
			}	
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en Cat_Etapas en la funcion refrestabla  SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	KeyListener buscar_action = new KeyListener() {
		@Override
		public void keyTyped(KeyEvent e){
		}
		@Override
		public void keyReleased(KeyEvent e) {	
		}
		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				btnBuscar.doClick();
			}
		}
	};
	
    
	KeyListener opFiltroGeneral = new KeyListener(){
		public void keyReleased(KeyEvent arg0) {
			int[] columnas = {0,1,2,3,4};
			new Obj_Filtro_Dinamico_Plus(tabla, txtfiltro_tabla.getText().toUpperCase(), columnas);
		}
		public void keyPressed(KeyEvent arg0) {}
		@Override
		public void keyTyped(KeyEvent arg0) {
		}		
	};
	
	KeyListener enterpasaraPunto_De_Venta = new KeyListener() {
		public void keyTyped(KeyEvent e){}
		public void keyReleased(KeyEvent e) {}
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				txtNombre_Punto_De_Venta.requestFocus();
			}
		}
	};
	
	KeyListener enterpasaraEstablecimiento = new KeyListener() {
		public void keyTyped(KeyEvent e){}
		public void keyReleased(KeyEvent e) {}
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				cmbEstablecimiento.requestFocus();
			}
		}
	};
	
	KeyListener enterpasaraNombre_Pc = new KeyListener() {
		public void keyTyped(KeyEvent e){}
		public void keyReleased(KeyEvent e) {}
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				txtNombre_pc.requestFocus();
			}
		}
	};
	
	
	ActionListener salir = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			dispose();
		}
	};
	
	ActionListener buscar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(txtFolio.getText().equals("")){
				JOptionPane.showMessageDialog(null, "Seleccione Un Registro De La Tabla A La Derecha Para Editar o Teclee El Folio","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
				txtFolio.requestFocus();
				return;
			}else{
				Obj_Puntos_De_Venta_De_Tiempo_Aire tpc = new Obj_Puntos_De_Venta_De_Tiempo_Aire().buscar(Integer.parseInt(txtFolio.getText()));
			if(tpc.getFolio() != 0){
			txtNombre_pc.setText(tpc.getNombre_Pc().toString()+"");
			txtNombre_Punto_De_Venta.setText(tpc.getNombre_Punto_Venta_TA()+"");
			cmbEstablecimiento.setSelectedItem(tpc.getEstablecimiento()+"");
			cmb_status.setSelectedItem(tpc.getStatus()==1?"VIGENTE":"CANCELADO");
			btnEditar.setEnabled(true);
			btnGuardar.setEnabled(false);
			}else{
				JOptionPane.showMessageDialog(null, "El Folio Buscado No Existe","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
				return;
			}
		  }
		}
		};
	
	ActionListener deshacer = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			txtFolio.setText("");
			txtFolio.setEnabled(true);
			txtNombre_Punto_De_Venta.setText("");
			txtNombre_Punto_De_Venta.setEnabled(false);
			txtfiltro_tabla.setText("");
			btnNuevo.setEnabled(true);
			btnEditar.setEnabled(true);
			txtNombre_pc.setText("");
			txtNombre_pc.setEditable(false);
			cmbEstablecimiento.setEnabled(false);
			cmb_status.setEnabled(false);
			cmb_status.setSelectedIndex(0);
			cmbEstablecimiento.setSelectedIndex(0);
			txtFolio.requestFocus();
		}
	};
	
	ActionListener editar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(txtNombre_pc.getText().equals("")){
				JOptionPane.showMessageDialog(null, "Seleccione Un Registro De La Tabla A La Derecha Para Editar o Teclee El Folio","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}else{
				txtFolio.setEnabled(false);
				btnEditar.setEnabled(false);
				btnNuevo.setEnabled(true);
				cmb_status.setEnabled(true);
				cmbEstablecimiento.setEnabled(true);
				btnEditar.setEnabled(false);
				btnGuardar.setEnabled(true);
				txtNombre_pc.setEditable(true);
				txtNombre_pc.requestFocus(true);
				txtNombre_Punto_De_Venta.setEnabled(true);
			}
		}
	};
	
	
	ActionListener nuevo = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			busqueda_proximo_folio();
			if(foliosiguiente != 0){
				btnDeshacer.doClick();
				txtFolio.setText( busqueda_proximo_folio()+"");
				txtFolio.setEnabled(false);
				btnGuardar.setEnabled(true);
				txtNombre_pc.setEditable(true);
				txtNombre_pc.requestFocus();
				txtNombre_Punto_De_Venta.setEnabled(true);
				btnEditar.setEnabled(false);
				cmb_status.setSelectedIndex(0);
				cmb_status.setEnabled(true);
				cmbEstablecimiento.setSelectedIndex(0);
				cmbEstablecimiento.setEnabled(true);
			}else{
				btnDeshacer.doClick();
				txtFolio.setText(1+"");
				txtFolio.setEditable(false);
				btnGuardar.setEnabled(true);
				txtNombre_pc.requestFocus();
				btnEditar.setEnabled(false);
				cmb_status.setSelectedIndex(0);
				cmb_status.setEnabled(true);
			}
		}
	};
	
	ActionListener guardar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
				try {
					if(validaCampos()!="") {
						JOptionPane.showMessageDialog(null, "Los Siguientes Datos Son Requeridos:\n "+validaCampos(), "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
						return;
					} else{
						
						Obj_Puntos_De_Venta_De_Tiempo_Aire tpc = new Obj_Puntos_De_Venta_De_Tiempo_Aire().buscar(Integer.parseInt(txtFolio.getText()));
					if(tpc.getFolio() == Integer.parseInt(txtFolio.getText())){
						if(JOptionPane.showConfirmDialog(null, "El registro ya existe, ¿desea cambiarlo?") == 0){
							if(validaCampos()!="") {
								JOptionPane.showMessageDialog(null, "Los Siguientes Datos Son Requeridos:\n"+validaCampos(), "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
								return;
							}else{
								tpc.setNombre_Pc(txtNombre_pc.getText().toString().toUpperCase().trim());
								tpc.setEstablecimiento(cmbEstablecimiento.getSelectedItem().toString().toUpperCase().trim());
								tpc.setNombre_Punto_Venta_TA(txtNombre_Punto_De_Venta.getText().toString().toUpperCase().trim());
									switch(cmb_status.getSelectedIndex()){
															case 0: tpc.setStatus(1); break;
															case 1: tpc.setStatus(0); break;	}
											if(tpc.actualizar(Integer.parseInt(txtFolio.getText()))){
															modelo.setRowCount(0);
															refrestabla();
									JOptionPane.showMessageDialog(null,"El Registró Se Actualizó Correctamente","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//aplicara-el-dialogo-icono-6256-32.png"));
									btnDeshacer.doClick();
									txtFolio.setEditable(true);
									txtFolio.requestFocus();
									return;
								}else{
									JOptionPane.showMessageDialog(null, "El Registro No Se Actualizó", "Error !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-icono-eliminar5252-64.png"));
									return;
								}
							}
						}else{
							return;
						}
					}else{
						tpc.setFolio(Integer.parseInt(txtFolio.getText()));
						tpc.setNombre_Pc(txtNombre_pc.getText().toUpperCase().trim());
						tpc.setEstablecimiento(cmbEstablecimiento.getSelectedItem().toString().toUpperCase().trim());
						tpc.setNombre_Punto_Venta_TA(txtNombre_Punto_De_Venta.getText().toUpperCase().trim());
							switch(cmb_status.getSelectedIndex()){
							case 0: tpc.setStatus(1); break;
							case 1: tpc.setStatus(0); break;	}
								if(tpc.guardar()){
						        	modelo.setRowCount(0);
									refrestabla();
									JOptionPane.showMessageDialog(null,"El Registró Se Guardó  Correctamente","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//aplicara-el-dialogo-icono-6256-32.png"));
									btnDeshacer.doClick();
									txtFolio.setEditable(true);
									txtFolio.requestFocus();
									return;
								}else{
									JOptionPane.showMessageDialog(null, "El Registro No Se Guardó", "Error !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-icono-eliminar5252-64.png"));
									return;
								}
							}
					}
				} catch (NumberFormatException e1) {
					e1.printStackTrace();
				} 				
		}
	};
	
	
	private void agregar(final JTable tbl) {
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
	        	if(e.getClickCount()==1){
	        		int fila = tabla.getSelectedRow();
	        		    txtFolio.setText(tabla.getValueAt(fila,0).toString().substring(0,tabla.getValueAt(fila,0).toString().length()));
					    cmbEstablecimiento.setSelectedItem(tabla.getValueAt(fila,1).toString().substring(0,tabla.getValueAt(fila,1).toString().length()));
						txtNombre_pc.setText(tabla.getValueAt(fila,2).toString().substring(0,tabla.getValueAt(fila,2).toString().length()));
						txtNombre_Punto_De_Venta.setText(tabla.getValueAt(fila,3).toString().substring(0,tabla.getValueAt(fila,3).toString().length()));
						cmb_status.setSelectedItem(tabla.getValueAt(fila,4).toString().substring(0,tabla.getValueAt(fila,4).toString().length()));
						btnEditar.setEnabled(true);
						cmb_status.setEnabled(false);
                        cmbEstablecimiento.setEnabled(false);
						btnEditar.setEnabled(true);
						txtNombre_pc.setEditable(false);
						txtNombre_pc.requestFocus();
						txtNombre_Punto_De_Venta.setEnabled(false);
						txtFolio.setEnabled(true);
	        	}
	        }
        });
    }
	
	public int  busqueda_proximo_folio() {
		Connexion con = new Connexion();
		String query = "select max(folio)+1 as 'Maximo' from tb_pc_asignadas_a_un_punto_de_venta_TA ";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
		    ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				 foliosiguiente =(rs.getInt(1));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error");
			JOptionPane.showMessageDialog(null, "Error en Cat_Puntos_De_Venta_De_Tiempo_Aire  en la funcion busqueda_proximo_folio()"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			return foliosiguiente ;
		}
		finally{
			 if (stmt != null) { try {
				stmt.close();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Error en Cat_Puntos_De_Venta_De_Tiempo_Aire   en la funcion busqueda_proximo_folio()"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
				e.printStackTrace();
			} }
		}
		return foliosiguiente;
			}
	
	
	
	private String validaCampos(){
		String error="";
		if(txtNombre_pc.getText().equals("")) 		error+= "-Nombre De La Computadora\n";
		if(cmbEstablecimiento.getSelectedIndex()==(0))error+= " -Nombre Establecimiento\n";
		if(txtNombre_Punto_De_Venta.getText().equals("")) 	error+= "-Nombre Del Punto De Venta\n";
		return error;
	}
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Puntos_De_Venta_De_Tiempo_Aire().setVisible(true);
		}catch(Exception e){	}
	}
	
}
