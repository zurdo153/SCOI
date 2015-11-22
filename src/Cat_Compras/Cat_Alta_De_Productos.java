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
import java.text.DecimalFormat;

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
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Conexiones_SQL.Connexion;
import Obj_Compras.Obj_Alta_De_Productos;
import Obj_Principal.Componentes;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Alta_De_Productos extends JFrame{
	String foliosiguiente="";
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JTextField txtFolio = new Componentes().text(new JTextField(), "Folio", 9, "Int");
	JTextField txtDescricion = new Componentes().text(new JTextField(), "Descricion",250,"String");
	JTextField txtCodigoDeBarras = new Componentes().text(new JTextField(), "Codigo De Barras", 40, "String");
	JTextField txtCosto = new Componentes().text(new JTextField(), "Costo", 10, "Double");
	JTextField txtPrecioDeVenta = new Componentes().text(new JTextField(), "Precio De Venta", 15, "Double");
	
	JTextField txtFolioFiltro = new JTextField();
	JTextField txtDescripciondFiltro = new Componentes().text(new JTextField(), "Filtro Por Descripcion", 30, "String");
	
	String UnidadDeMedida[] = new Obj_Alta_De_Productos().ComboUnidadDeMedida();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbUnidadDeMedida = new JComboBox(UnidadDeMedida);
	
	String uso[] = new Obj_Alta_De_Productos().ComboUso();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbUso = new JComboBox(uso);
	
	String status[] = {"VIGENTE","CANCELADO"};
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmb_status = new JComboBox(status);
	
	JButton btnBuscar = new JButton("Buscar",new ImageIcon("imagen/buscar.png"));
	JButton btnSalir = new JButton("Salir",new ImageIcon("imagen/salir16.png"));
	JButton btnDeshacer = new JButton("Deshacer",new ImageIcon("imagen/deshacer16.png"));
	JButton btnGuardar = new JButton("Guardar",new ImageIcon("imagen/Guardar.png"));
	JButton btnEditar = new JButton("Editar",new ImageIcon("imagen/editara.png"));
	JButton btnNuevo = new JButton("Nuevo",new ImageIcon("imagen/Nuevo.png"));
	
	 public static DefaultTableModel modelo = new DefaultTableModel(null,new String[]{"Folio", "Descripcion", "Unidad De Medida","Uso","Codigo De Barras Principal","Costo","Precio De Venta","Status"}){
	            @SuppressWarnings("rawtypes")
	            Class[] types = new Class[]{
	                       java.lang.Object.class,
	                       java.lang.Object.class,  
	                       java.lang.Object.class,
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
	                            case 5  : return false; 
	                            case 6  : return false; 
	                            case 7  : return false; 
	                    }
	                     return false;
	             }
	    };
		JTable tabla = new JTable(modelo);
		JScrollPane scrollAsignado = new JScrollPane(tabla);
		@SuppressWarnings("rawtypes")
		private TableRowSorter trsfiltro;
		
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Cat_Alta_De_Productos(){
		
		
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/folder-home-home-icone-5663-32.png"));
			panel.setBorder(BorderFactory.createTitledBorder("Alta De Productos"));
			
			this.setTitle("Alta De Productos");
			
			trsfiltro = new TableRowSorter(modelo); 
			tabla.setRowSorter(trsfiltro);
			
			int x = 45, y=30, ancho=100;
			
			
			panel.add(new JLabel("Folio:")).setBounds(x-25,y-15,ancho,20);
			panel.add(txtFolio).setBounds(90,y-15,ancho-20,20);
			panel.add(btnBuscar).setBounds(x+(ancho)+30,y-15,100,20);
			panel.add(btnNuevo).setBounds(x+(ancho*2)+30,y-15,100,20);
			
			panel.add(new JLabel("Descripcion:")).setBounds(x-25,y+=15,80,20);
			panel.add(txtDescricion).setBounds(90,y,ancho*3-15,20);
			
			panel.add(new JLabel("Unidad De Medida:")).setBounds(x-25,y+=30,ancho,20);
			panel.add(cmbUnidadDeMedida).setBounds(110,y,ancho+50,20);
			panel.add(btnEditar).setBounds(x+(ancho*2)+30,y,100,20);
			
			panel.add(new JLabel("Uso:")).setBounds(x-25,y+=30,ancho,20);
			panel.add(cmbUso).setBounds(90,y,ancho+70,20);
			panel.add(btnGuardar).setBounds(x+ancho*2+30,y,100,20);
			
			panel.add(new JLabel("Codigo De Barrar:")).setBounds(x-25,y+=30,ancho,20);
			panel.add(txtCodigoDeBarras).setBounds(110,y,ancho+50,20);
			panel.add(btnDeshacer).setBounds(x+ancho*2+30,y,100,20);
			
			panel.add(new JLabel("Costo:")).setBounds(x-25,y+=30,ancho,20);
			panel.add(txtCosto).setBounds(90,y,ancho+70,20);
			panel.add(btnSalir).setBounds(x+ancho*2+30,y,100,20);
			
			panel.add(new JLabel("Precio De Venta:")).setBounds(x-25,y+=30,ancho,20);
			panel.add(txtPrecioDeVenta).setBounds(110,y,ancho+50,20);
			
			panel.add(new JLabel("Estatus:")).setBounds(x-25,y+=30,ancho,20);
			panel.add(cmb_status).setBounds(90,y,ancho+70,20);
			
			panel.add(txtFolioFiltro).setBounds((x*2)+(ancho*3)-5,15,58,20);
			panel.add(txtDescripciondFiltro).setBounds((x*2)+(ancho*3)+53,15,240,20);
			
			panel.add(getPanelTabla()).setBounds((x*2)+(ancho*3)-5,35,623,355);
			
			
			txtDescricion.setEditable(false);
			txtCodigoDeBarras.setEditable(false);
			txtCosto.setEditable(false);
			
			cmb_status.setEnabled(false);
			cmbUnidadDeMedida.setEnabled(false);
			cmbUso.setEnabled(false);
			
			txtPrecioDeVenta.setEditable(false);
			
			btnEditar.setEnabled(false);
			
			txtFolio.addKeyListener(buscar_action);
			
			btnGuardar.addActionListener(guardar);
			btnSalir.addActionListener(salir);
			btnBuscar.addActionListener(buscar);
			btnDeshacer.addActionListener(deshacer);
			btnNuevo.addActionListener(nuevo);
			btnEditar.addActionListener(editar);
			
			txtFolioFiltro.addKeyListener(opFiltroFolio);
//			txtDescripciondFiltro.addKeyListener(opFiltrounidad);
			
			txtDescricion.addKeyListener(enterpasaraAbreviatura);
			txtCodigoDeBarras.addKeyListener(enterpasaraunidad);
			
			agregar_de_tabla(tabla);
			cont.add(panel);
			this.setSize(1024,430);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			
              ///asigna el foco al filtro
						 this.addWindowListener(new WindowAdapter() {
				                public void windowOpened( WindowEvent e ){
				                	txtDescripciondFiltro.requestFocus();
				             }
				        });
		
             ///deshacer con escape
			             getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
			             getRootPane().getActionMap().put("escape", new AbstractAction(){
			                 public void actionPerformed(ActionEvent e)
			                 {                 	    btnDeshacer.doClick();
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
			                  
		      ///editar con F10
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
		
	    this.tabla.getColumnModel().getColumn(0).setMinWidth(70);
	    this.tabla.getColumnModel().getColumn(0).setMaxWidth(70);
	    this.tabla.getColumnModel().getColumn(1).setMinWidth(240);
	    this.tabla.getColumnModel().getColumn(1).setMaxWidth(800);
	    this.tabla.getColumnModel().getColumn(2).setMinWidth(100);
	    this.tabla.getColumnModel().getColumn(2).setMaxWidth(180);
	    this.tabla.getColumnModel().getColumn(3).setMinWidth(160);
	    this.tabla.getColumnModel().getColumn(3).setMaxWidth(220);
	    this.tabla.getColumnModel().getColumn(4).setMinWidth(110);
	    this.tabla.getColumnModel().getColumn(4).setMaxWidth(140);
	    this.tabla.getColumnModel().getColumn(5).setMinWidth(60);
	    this.tabla.getColumnModel().getColumn(5).setMaxWidth(100);
	    
	    this.tabla.getColumnModel().getColumn(6).setMinWidth(100);
	    this.tabla.getColumnModel().getColumn(6).setMaxWidth(100);
	    this.tabla.getColumnModel().getColumn(7).setMinWidth(60);
	    this.tabla.getColumnModel().getColumn(7).setMaxWidth(60);
		    
						    
		for(int i=0; i<tabla.getColumnCount(); i++){
			if(i==5 || i==6){
				this.tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12));
			}else{
				this.tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
			}
			
		}
							
		refrestabla();
		
		JScrollPane scrol = new JScrollPane(tabla);
	    return scrol; 
	    
	    }
	private void refrestabla(){

		DecimalFormat df = new DecimalFormat("#0.00");
		modelo.setRowCount(0);
		
		Statement s;
		ResultSet rs;
		try {
			Connexion con = new Connexion();
			s = con.conexion().createStatement();
			rs = s.executeQuery(" select tb_productos.folio_producto "
								+ "		,tb_productos.descripcion "
								+ "		,tb_unidad_de_medida_de_productos.descripcion "
								+ "		,tb_uso_de_productos.descripcion "
								+ "		,tb_productos.codigo_de_barras_principal "
								+ "		,tb_productos.costo "
								+ "		,tb_productos.precio_de_venta "
								+ "		,case when ( tb_productos.status = 'V') then 'VIGENTE' "
								+ "			else 'CANCELAR' "
								+ "		 end as status "
								+ " from tb_productos "
								+ " inner join tb_unidad_de_medida_de_productos on tb_unidad_de_medida_de_productos.folio = tb_productos.folio_unidad_de_medida "
								+ " inner join tb_uso_de_productos on tb_uso_de_productos.folio = tb_productos.folio_uso "
								+ " where tb_productos.status = 'V' ");
			
			while (rs.next())
			{ 
			   Object [] fila = new Object[8];
			   fila[0] = rs.getString(1).trim();
			   fila[1] = rs.getString(2).trim();
			   fila[2] = rs.getString(3).trim(); 
			   fila[3] = rs.getString(4).trim(); 
			   fila[4] = rs.getString(5).trim(); 
			   fila[5] = df.format(rs.getDouble(6)); 
			   fila[6] = df.format(rs.getDouble(7)); 
			   fila[7] = rs.getString(8).trim();
			   modelo.addRow(fila); 
			}	
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en Cat_Alta_De_Productos en la funcion refrestabla  SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
    
	KeyListener opFiltroFolio = new KeyListener(){
		@SuppressWarnings("unchecked")
		public void keyReleased(KeyEvent arg0) {
			trsfiltro.setRowFilter(RowFilter.regexFilter(txtFolioFiltro.getText(), 0));
		}
		public void keyTyped(KeyEvent arg0) {
			char caracter = arg0.getKeyChar();
			if(((caracter < '0') ||
				(caracter > '9')) &&
			    (caracter != KeyEvent.VK_BACK_SPACE)){
				arg0.consume(); 
			}	
		}
		public void keyPressed(KeyEvent arg0) {}		
	};
	
//	KeyListener opFiltrounidad = new KeyListener(){
//		@SuppressWarnings("unchecked")
//		public void keyReleased(KeyEvent arg0) {
//			trsfiltro.setRowFilter(RowFilter.regexFilter(txtUnidadFiltro.getText().toUpperCase().trim(), 1));
//		}
//		public void keyTyped(KeyEvent arg0) {}
//		public void keyPressed(KeyEvent arg0) {}		
//	};
	
	KeyListener enterpasaraAbreviatura = new KeyListener() {
		public void keyTyped(KeyEvent e){}
		public void keyReleased(KeyEvent e) {}
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				txtCodigoDeBarras.requestFocus();
			}
		}
	};
	
	KeyListener enterpasaraunidad = new KeyListener() {
		public void keyTyped(KeyEvent e){}
		public void keyReleased(KeyEvent e) {}
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				txtDescricion.requestFocus();
			}
		}
	};
	
	KeyListener buscar_action = new KeyListener() {
		public void keyTyped(KeyEvent e){}
		public void keyReleased(KeyEvent e) {}
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				btnBuscar.doClick();
			}
		}
	};
	
	ActionListener salir = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			dispose();
		}
	};
	
	
	ActionListener deshacer = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			txtFolio.setEditable(true);
			txtFolio.requestFocus();
			btnNuevo.setEnabled(true);
			btnEditar.setEnabled(true);
			
			txtFolio.setText("");
			txtDescricion.setText("");
			txtCodigoDeBarras.setText("");
			txtCosto.setText("");
			
			txtPrecioDeVenta.setText("");
			
			cmb_status.setSelectedIndex(0);
			
			cmbUnidadDeMedida.setSelectedIndex(0);
			cmbUso.setSelectedIndex(0);
			
			txtDescricion.setEditable(false);
			txtCodigoDeBarras.setEditable(false);
			txtCosto.setEditable(false);
			
			txtPrecioDeVenta.setEditable(false);
			
			cmbUnidadDeMedida.setEnabled(false);
			cmbUso.setEnabled(false);
			cmb_status.setEnabled(false);
		}
	};
	
	ActionListener editar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(txtFolio.getText().equals("")){
				JOptionPane.showMessageDialog(null, "No hay registro que Editar","Error",JOptionPane.WARNING_MESSAGE);
				return;
			}else{
				txtFolio.setEditable(false);
				txtDescricion.setEditable(true);
				txtCodigoDeBarras.setEditable(true);
				txtCosto.setEditable(true);
				
				txtPrecioDeVenta.setEditable(true);
				
				cmbUnidadDeMedida.setEnabled(true);
				cmbUso.setEnabled(true);
				cmb_status.setEnabled(true);
				
				btnEditar.setEnabled(false);
				btnNuevo.setEnabled(true);
				btnEditar.setEnabled(false);
				btnGuardar.setEnabled(true);
				txtDescricion.requestFocus(true);
			}
		}
	};
	
	ActionListener buscar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			busqueda(txtFolio.getText().toString());
		}
	};
	
	private void agregar_de_tabla(final JTable tbl) {
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
	        	if(e.getClickCount()==1){
	        		int fila = tabla.getSelectedRow();
	        		String id = tabla.getValueAt(fila,0).toString().substring(0,tabla.getValueAt(fila,0).toString().length());
	        
	        		busqueda(id);
	        	}
	        }
        });
    }
	
	public void busqueda(String folio){
		if(folio.equals("")){
			JOptionPane.showMessageDialog(null, "Ingrese el No. de Folio","Error",JOptionPane.WARNING_MESSAGE);
			return;
		}else{
			try {
				Obj_Alta_De_Productos prod = new Obj_Alta_De_Productos().buscar(folio);
				if(!prod.getFolio().equals("")){
					
					txtFolio.setText(prod.getFolio()+"");
					txtDescricion.setText(prod.getDescripcion()+"");
					txtCodigoDeBarras.setText(prod.getCodigoDeBarras()+"");
					txtCosto.setText(prod.getCosto()+"");
					
					txtPrecioDeVenta.setText(prod.getPrecioDeVenta()+"");
					
					cmbUnidadDeMedida.setSelectedItem(prod.getUnidadDeMedida());
					cmbUso.setSelectedItem(prod.getUso());
					cmb_status.setSelectedItem(prod.getStatus());
					
					btnNuevo.setEnabled(true);
					btnEditar.setEnabled(true);
					
					txtFolio.setEditable(false);
					txtDescricion.setEditable(false);
					txtCodigoDeBarras.setEditable(false);
					txtCosto.setEditable(false);
					
					txtPrecioDeVenta.setEditable(false);
					
					cmbUnidadDeMedida.setEnabled(false);
					cmbUso.setEnabled(false);
					cmb_status.setEnabled(false);
					
					txtFolio.requestFocus();
				} else{
					JOptionPane.showMessageDialog(null, "El Registro no existe","Error",JOptionPane.WARNING_MESSAGE);
					return;
				}
			} catch (NumberFormatException e1) {
				e1.printStackTrace();
			} 			
		}
	}
	
	ActionListener nuevo = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			
			if(foliosiguiente.equals("")){
				btnDeshacer.doClick();
				btnGuardar.setEnabled(true);
				
				txtDescricion.setEditable(true);
				txtCodigoDeBarras.setEditable(true);
				txtCosto.setEditable(true);
				
				txtPrecioDeVenta.setEditable(true);
				
				
				txtFolio.setEditable(false);
				txtDescricion.requestFocus();
				btnEditar.setEnabled(false);
				
				cmbUnidadDeMedida.setSelectedIndex(0);
				cmbUso.setSelectedIndex(0);
				cmb_status.setSelectedIndex(0);
				
                cmbUnidadDeMedida.setEnabled(true);
                cmbUso.setEnabled(true);
				cmb_status.setEnabled(true);
			}else{
				btnDeshacer.doClick();
				btnGuardar.setEnabled(true);
				
				txtDescricion.setEditable(true);
				txtCodigoDeBarras.setEditable(true);
				txtCosto.setEditable(true);
				
				txtPrecioDeVenta.setEditable(true);
				
				txtFolio.setEditable(false);
				txtDescricion.requestFocus();
				btnEditar.setEnabled(false);
				
				cmbUnidadDeMedida.setSelectedIndex(0);
				cmbUso.setSelectedIndex(0);
				cmb_status.setSelectedIndex(0);
				
                cmbUnidadDeMedida.setEnabled(true);
                cmbUso.setEnabled(true);
				cmb_status.setEnabled(true);
			}
			
			txtFolio.setText( busqueda_proximo_folio());
		}
	};
	
	ActionListener guardar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			
			try {
					if(validaCampos()!="") {
						JOptionPane.showMessageDialog(null, "los siguientes campos son requeridos:\n "+validaCampos(), "Error al guardar registro", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
						return;
					} else{
							Obj_Alta_De_Productos prod = new Obj_Alta_De_Productos().buscar(txtFolio.getText());
								if(prod.getFolio().equals(txtFolio.getText())){
										if(JOptionPane.showConfirmDialog(null, "El registro ya existe, ¿desea cambiarlo?") == 0){
												if(validaCampos()!="") {
													JOptionPane.showMessageDialog(null, "los siguientes campos son requeridos:\n"+validaCampos(), "Error al guardar registro", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
													return;
												}else{
													prod.setFolio(txtFolio.getText().toString());
													prod.setDescripcion(txtDescricion.getText().toLowerCase().toString());
													prod.setCodigoDeBarras(txtCodigoDeBarras.getText().toLowerCase().toString());
													prod.setCosto(Double.valueOf(txtCosto.getText().toLowerCase().toString()));
					  							    prod.setUnidadDeMedida(cmbUnidadDeMedida.getSelectedItem().toString());
					  							    prod.setUso(cmbUso.getSelectedItem().toString());
					  							    
					  							    prod.setPrecioDeVenta(Double.valueOf(txtPrecioDeVenta.getText().toString()));
					  							
					  							    prod.setStatus(cmb_status.getSelectedItem().toString());
					  							    
														if(prod.guardar()){
																refrestabla();
																JOptionPane.showMessageDialog(null,"El registró se actualizó de forma segura","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//Exito.png"));
																	btnDeshacer.doClick();
																	txtFolio.setEditable(true);
																	txtFolio.requestFocus();
																return;
														}else{
															JOptionPane.showMessageDialog(null, "El registro no se actualizó", "Error !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
															return;
														}
												}
										}else{
											return;
										}
								}else{
										prod.setFolio(txtFolio.getText().toString());
										prod.setDescripcion(txtDescricion.getText().toLowerCase().toString());
										prod.setCodigoDeBarras(txtCodigoDeBarras.getText().toLowerCase().toString());
										prod.setCosto(Double.valueOf(txtCosto.getText().toLowerCase().toString()));
		  							    prod.setUnidadDeMedida(cmbUnidadDeMedida.getSelectedItem().toString());
		  							    prod.setUso(cmbUso.getSelectedItem().toString());
		  							    
		  							    prod.setPrecioDeVenta(Double.valueOf(txtPrecioDeVenta.getText().toLowerCase().toString()));
		  							
		  							    prod.setStatus(cmb_status.getSelectedItem().toString());
									
										if(prod.guardar()){
											refrestabla();
											JOptionPane.showMessageDialog(null,"El registró se guardó de forma segura","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//Exito.png"));
												btnDeshacer.doClick();
												txtFolio.setEditable(true);
												txtFolio.requestFocus();
											return;
											
										}else{
											JOptionPane.showMessageDialog(null, "El registro no se guardó", "Error !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
											return;
										}
								}
		               }
				 } catch (NumberFormatException e1) {
					e1.printStackTrace();
				} 				
							
		}
	};
	
	public String  busqueda_proximo_folio() {
		Connexion con = new Connexion();
		String query = "select right('0000000000'+convert(varchar(10),(folio+1)),7) from tb_folios where folio_transaccion = 17";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
		    ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				 foliosiguiente =(rs.getString(1));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error");
			JOptionPane.showMessageDialog(null, "Error en Cat_Alta_De_Productos  en la funcion busqueda_proximo_folio()"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			return foliosiguiente ;
		}
		finally{
			 if (stmt != null) { try {
				stmt.close();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Error en Cat_Alta_De_Productos  en la funcion busqueda_proximo_folio()"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			} }
		}
		return foliosiguiente;
			}

	
	private String validaCampos(){
		String error="";
		if(txtDescricion.getText().equals("")) 		error+= "Descricion\n";
		if(txtCodigoDeBarras.getText().equals("")) 		error+= "Codigo De Barras\n";
		if(txtCosto.getText().equals("")) 		error+= "Costo\n";
		
		if(cmbUnidadDeMedida.getSelectedIndex()==0) 		error+= "Unidad De Medida\n";
		if(cmbUso.getSelectedIndex()==0) 		error+= "Uso\n";
		
		if(txtPrecioDeVenta.getText().equals("")) 		error+= "Precio De Venta\n";
		
		return error;
	}
	
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Alta_De_Productos().setVisible(true);
		}catch(Exception e){	}
	}
	
}
