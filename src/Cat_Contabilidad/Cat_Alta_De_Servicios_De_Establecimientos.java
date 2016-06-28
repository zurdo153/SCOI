package Cat_Contabilidad;
import java.awt.Container;
import java.awt.Event;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

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


import Conexiones_SQL.Connexion;
import Obj_Contabilidad.Obj_Alta_Servicios_Establecimientos;
import Obj_Principal.Componentes;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_Filtro_Dinamico_Plus;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Alta_De_Servicios_De_Establecimientos extends JFrame {
	
	String foliosiguiente="";
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	int bandera=0;
	
	Obj_Alta_Servicios_Establecimientos objas= new Obj_Alta_Servicios_Establecimientos();
	
	JLabel lblFolio= new JLabel("Folio :");
	JLabel lblDescripcion= new JLabel("Descripcion :");
	JLabel lblnumeroControl= new JLabel("No. Control :");
	JLabel lblClasificacion= new JLabel("Clasificacion :");
	JLabel lblStatus= new JLabel("Status :");
	JLabel lblEstablecimiento= new JLabel("Establecimiento :");
	
	JTextField txtFolio = new Componentes().text(new JCTextField(), "Teclea el Folio Del Servicio", 20, "String");
	JTextField txtDescripcion = new Componentes().text(new JCTextField(), "Descripcion Del Servicio",250,"String");
	JTextField txtNumeroControl = new Componentes().text(new JCTextField(), "Codigo del Servicio", 20, "String");
	JTextField txtDescripciondFiltro = new Componentes().text(new JCTextField(), "Teclea Folio o Descripcion  Del Servicio Para Buscar En La Tabla", 30, "String");

	String Clasificacion[] = objas.Combo_Respuesta_Clasificacion();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbClasificacion = new JComboBox(Clasificacion);
	
	String status[] = {"VIGENTE","CANCELADO"};
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbStatus = new JComboBox(status);
	
	String Establecimiento[] =objas.Combo_Respuesta_Establecimiento();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbEstablecimiento = new JComboBox(Establecimiento);
	
	JButton btnBuscar = new JButton("Buscar",new ImageIcon("imagen/buscar.png"));
	JButton btnSalir = new JButton("Salir",new ImageIcon("imagen/salir16.png"));
	JButton btnDeshacer = new JButton("Deshacer",new ImageIcon("imagen/deshacer16.png"));
	JButton btnGuardar = new JButton("Guardar",new ImageIcon("imagen/Guardar.png"));
	JButton btnEditar = new JButton("Editar",new ImageIcon("imagen/editara.png"));
	JButton btnNuevo = new JButton("Nuevo",new ImageIcon("imagen/Nuevo.png"));
	
	public static DefaultTableModel modelo = new DefaultTableModel(null,new String[]{"Folio", "Descripcion", "Numero de Control","Clasificacion de Servicio","Status","Establecimiento"}){
         @SuppressWarnings("rawtypes")
         Class[] types = new Class[]{
                    java.lang.Object.class,
                    java.lang.Object.class,  
                    java.lang.Object.class,
                    java.lang.Object.class,  
                    java.lang.Object.class,
                    java.lang.Object.class,
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
                 }
                  return false;
          }
 };
    JTable tabla = new JTable(modelo);
	JScrollPane scrollAsignado = new JScrollPane(tabla);
	
	public Cat_Alta_De_Servicios_De_Establecimientos(){
		
		init();
		
		this.setSize(1024,768);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/articulo-icono-9036-48.png"));
		panel.setBorder(BorderFactory.createTitledBorder("#"));
		this.setTitle("Servicios De Establecimientos");

		int x = 10, y=20, ancho=100 , z=25;
		
		panel.add(lblFolio).setBounds(x,y,ancho/2,20);
		panel.add(txtFolio).setBounds(x+=60,y,ancho*2,20);
		panel.add(btnBuscar).setBounds(x+=230,y,ancho,20);
		panel.add(btnNuevo).setBounds(x+=120,y,ancho,20);
		panel.add(btnEditar).setBounds(x+=120,y,ancho,20);
		panel.add(btnGuardar).setBounds(x+=120,y,ancho,20);
		panel.add(btnDeshacer).setBounds(x+=120,y,ancho,20);
		panel.add(btnSalir).setBounds(x+=120,y,ancho,20);
		
		x=10;
		panel.add(lblDescripcion).setBounds(x,y+=30,80,20);
		panel.add(txtDescripcion).setBounds(x+=65,y,ancho*4,z);
		
		panel.add(lblEstablecimiento).setBounds(5+txtDescripcion.getX()+txtDescripcion.getWidth(),txtDescripcion.getY(),ancho,z);
		panel.add(cmbEstablecimiento).setBounds(lblEstablecimiento.getX()+lblEstablecimiento.getWidth()-10,lblEstablecimiento.getY(),ancho*2,z);
		
		x=10;
		panel.add(lblnumeroControl).setBounds(x,y+=30,ancho,20);
		panel.add(txtNumeroControl).setBounds(x+=65,y,ancho*4,z);
		
		panel.add(lblClasificacion).setBounds(txtNumeroControl.getX()+txtNumeroControl.getWidth()+5,txtNumeroControl.getY(),ancho-13,z);
		panel.add(cmbClasificacion).setBounds(lblClasificacion.getX()+lblClasificacion.getWidth()+2,lblClasificacion.getY(),ancho*2,z);
		
		panel.add(lblStatus).setBounds(cmbEstablecimiento.getX()+cmbEstablecimiento.getWidth()+15,cmbEstablecimiento.getY(),ancho/2,20);
		panel.add(cmbStatus).setBounds(lblStatus.getX()+lblStatus.getWidth()+5,lblStatus.getY(),ancho+50,z);
		
		x=10;
		panel.add(txtDescripciondFiltro).setBounds(x,y+=30,720,20);
		panel.add(scrollAsignado).setBounds(x,y+=20,1000,600);
		
		cont.add(panel);
		///buscar con F2
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
		KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), "filtro");
		getRootPane().getActionMap().put("filtro", new AbstractAction(){
			public void actionPerformed(ActionEvent e)
			{  
				btnBuscar.doClick();
			}
			
			});
		 ///deshacer con escape
			getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
			getRootPane().getActionMap().put("escape", new AbstractAction(){
				public void actionPerformed(ActionEvent e)
				{
					btnDeshacer.doClick();
					}
					});
			///guardar con control+G
				getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_G,Event.CTRL_MASK),"guardar");
				getRootPane().getActionMap().put("guardar", new AbstractAction(){
					public void actionPerformed(ActionEvent e)
					{                 	   
						btnGuardar.doClick();

					}
					});
				///guardar con F12
				getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0), "guardar");
				getRootPane().getActionMap().put("guardar", new AbstractAction(){
					public void actionPerformed(ActionEvent e)
					{                 	   
						btnGuardar.doClick();
					}
					});

				KeyListener opBuscarEnter = new KeyListener() {
					@Override
					public void keyTyped(KeyEvent e){

						if(e.getKeyChar() == KeyEvent.VK_ENTER){
							Buscar();
							}
							}

						@Override
						public void keyPressed(KeyEvent e) {

								}
						
						@Override
						public void keyReleased(KeyEvent e) {

									}
							};
		
		btnNuevo.addActionListener(opNuevo);
		btnGuardar.addActionListener(opGuardar);
		btnBuscar.addActionListener(opBuscar);
		txtFolio.addKeyListener(opBuscarEnter);
		btnEditar.addActionListener(opEditar);
		btnDeshacer.addActionListener(opDeshacer);
		txtDescripciondFiltro.addKeyListener(op_filtro);
        agregar(tabla);
        btnSalir.addActionListener(opCerrar);
	
	}
	@SuppressWarnings("unused")
	private void init_tablaconsulta(){	

		tabla.getTableHeader().setReorderingAllowed(false) ;
		tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
	    this.tabla.getColumnModel().getColumn(0).setMinWidth(80);
	    this.tabla.getColumnModel().getColumn(1).setMinWidth(250);
	    this.tabla.getColumnModel().getColumn(2).setMinWidth(250);
	    this.tabla.getColumnModel().getColumn(3).setMinWidth(160);
	    this.tabla.getColumnModel().getColumn(4).setMinWidth(140);
	    this.tabla.getColumnModel().getColumn(5).setMinWidth(140);
		   
		this.tabla.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",12));
		this.tabla.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",12));
		this.tabla.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",12));
		this.tabla.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",12));
		this.tabla.getColumnModel().getColumn(4).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",12));
		this.tabla.getColumnModel().getColumn(5).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",12));
	
		refrestabla();
		}

		private void refrestabla(){    
			modelo.setRowCount(0);
			Statement s;
			ResultSet rs;

			try {
				Connexion con = new Connexion();
				s = con.conexion().createStatement();
				rs = s.executeQuery("exec sp_select_servicios_estableciminetos "+txtFolio.getText().toString().trim());
	
			while (rs.next())
			{ 
			 String [] fila = new String[6];
			  fila[0] = rs.getString(1).trim();
			  fila[1] = rs.getString(2).trim();
			  fila[2] = rs.getString(3).trim(); 
			  fila[3] = rs.getString(4).trim(); 
			  fila[4] = rs.getString(5).trim(); 
			  fila[5] = rs.getString(6).trim();
			
			 
			  	modelo.addRow(fila); 
	  
			}	   
		s.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error en la  SQLException: "+e1.getMessage(), "Avisa al Administrador Del Sistema",
								   JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
										  }
							}
	
	///actions Listeners
	ActionListener opNuevo = new ActionListener() {
		 public void actionPerformed(ActionEvent e) {
             btnDeshacer.doClick();
             modelo.setRowCount(0);
			 txtFolio.setEnabled(false);
			 String foliosiguiente=(objas.FolioSiguiente());
			 txtFolio.setText(""+Integer.valueOf(foliosiguiente));
			 txtNumeroControl.setEnabled(true);
			 cmbEstablecimiento.setEnabled(true);
			 cmbClasificacion.setEnabled(true);
			 cmbStatus.setEnabled(true);
			  btnGuardar.setEnabled(true);
			  txtDescripcion.setEnabled(true);
			 
			  }
		 };
		 
		 
		 
    ActionListener opGuardar = new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
				 String  sts=(String) (cmbStatus.getSelectedItem());
				 char st= sts.charAt(0);
		if(bandera==0){
				if( objas.guardar_servios_establcimientos(txtDescripcion.getText(), txtNumeroControl.getText(),(String) cmbClasificacion.getSelectedItem(), ""+st,(String) cmbEstablecimiento.getSelectedItem())){
				 		JOptionPane.showMessageDialog(null, "Se han Guardado Satisfatoriamente Los Datos De los Servicios de establecimientos ", "Aviso", JOptionPane.WARNING_MESSAGE,
						                                   new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
					init();
					bandera=1;
       				return;
						}else{  
							JOptionPane.showMessageDialog(null, "No Se Pudo guardar los servicios  \n Error En el Los Servicios de Establecimientos","Avise Al Adiministrador",JOptionPane.ERROR_MESSAGE,
									new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
							
							return;
					}
		}else{ 
			if(objas.modificar_servios_establcimientos(txtFolio.getText(),txtDescripcion.getText(), txtNumeroControl.getText(),(String) cmbClasificacion.getSelectedItem(), ""+st,(String) cmbEstablecimiento.getSelectedItem())){
			
				JOptionPane.showMessageDialog(null, "Se han Actualizado Satisfatoriamente Los Datos De los Servicios de establecimientos ", "Aviso", JOptionPane.WARNING_MESSAGE,
                        new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
						init();
						bandera=0;
						return;
					}else{  
						JOptionPane.showMessageDialog(null, "No Se Pudo guardar los servicios  \n Error En el Los Servicios de Establecimientos","Avise Al Adiministrador",JOptionPane.ERROR_MESSAGE,
								new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
					
						return;
					}
				
				}
			 }
			 
			 };
	
	ActionListener opBuscar = new ActionListener() {
				 public void actionPerformed(ActionEvent e) {
					 Buscar();
					 			}  
			 				};
			 				public void Buscar(){
			 					
			 				 if(txtFolio.getText().toString().equals("")){
								 JOptionPane.showMessageDialog(null, "Deve de Teclear un Folio  \n Para Poder Realizar la Busqueda", "Aviso", JOptionPane.WARNING_MESSAGE,
											new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
							 }else{
									
											if(objas.Buscar_Servicios_establecimientos_numCtrol(txtFolio.getText()).toString().equals("no")){
												
												JOptionPane.showMessageDialog(null, "No Se Encontro Registro Con folio ó numero de control:"+txtFolio.getText().toString(), "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
												txtFolio.requestFocus();
											}
											else
											{
												init_tablaconsulta_numctrol();
												
											}
												
							 			}
			 				
			 				}	
			 				
				 ActionListener opEditar = new ActionListener() {
					 public void actionPerformed(ActionEvent e) {
						 
						 txtFolio.setEnabled(false);
							
						 txtNumeroControl.setEnabled(true);
						 cmbEstablecimiento.setEnabled(true);
						 cmbClasificacion.setEnabled(true);
						 cmbStatus.setEnabled(true);
						  btnGuardar.setEnabled(true);
						  txtDescripcion.setEnabled(true);
						  bandera=1;
					
						 
					 }
					 
					 };
					 ActionListener opCerrar = new ActionListener() {
						 public void actionPerformed(ActionEvent e) {
							 
							dispose();
						
							 
						 }
						 
						 };
					 ActionListener opDeshacer = new ActionListener() {
						 public void actionPerformed(ActionEvent e) {
							init();
						 }
						 };
		/////////////////methods
		 public void init(){
			 txtFolio.setText("");
			 txtFolio.setEnabled(true);
			 txtDescripcion.setEnabled(false);
			 txtNumeroControl.setEnabled(false);
			 cmbEstablecimiento.setEnabled(false);
			 cmbClasificacion.setEnabled(false);
			 cmbStatus.setEnabled(false); 
			 btnEditar.setEnabled(false);
			 btnGuardar.setEnabled(false);
			 txtDescripcion.setText("");
			 txtNumeroControl.setText("");
			 cmbEstablecimiento.setSelectedIndex(0);
			 cmbClasificacion.setSelectedIndex(0);
			 cmbStatus.setSelectedIndex(0);
			 modelo.setRowCount(0);
			 
			 
			 tabla_filtro();
			 
		}
		 
		 KeyListener op_filtro = new KeyListener(){ 
			
				public void keyReleased(KeyEvent arg0) {
					
					int[] columnas ={0,1,2,3,4,5};
					new Obj_Filtro_Dinamico_Plus(tabla, txtDescripciondFiltro.getText().toString().trim().toUpperCase(), columnas);
				}
				public void keyTyped(KeyEvent arg0)   {}
				public void keyPressed(KeyEvent arg0) {}		
			};
			private void tabla_filtro(){	

				tabla.getTableHeader().setReorderingAllowed(false) ;
				tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				
			    this.tabla.getColumnModel().getColumn(0).setMinWidth(80);
			    this.tabla.getColumnModel().getColumn(1).setMinWidth(250);
			    this.tabla.getColumnModel().getColumn(2).setMinWidth(250);
			    this.tabla.getColumnModel().getColumn(3).setMinWidth(160);
			    this.tabla.getColumnModel().getColumn(4).setMinWidth(140);
			    this.tabla.getColumnModel().getColumn(5).setMinWidth(140);
				   
				this.tabla.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",12));
				this.tabla.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",12));
				this.tabla.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",12));
				this.tabla.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",12));
				this.tabla.getColumnModel().getColumn(4).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",12));
				this.tabla.getColumnModel().getColumn(5).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",12));
			
				filtro();
				}

				private void filtro(){    
					modelo.setRowCount(0);
					Statement s;
					ResultSet rs;

					try {
						Connexion con = new Connexion();
						s = con.conexion().createStatement();
						rs = s.executeQuery("exec sp_select_servicios_establecimientos ");
			
					while (rs.next())
					{ 
					 String [] fila = new String[6];
					  fila[0] = rs.getString(1).trim();
					  fila[1] = rs.getString(2).trim();
					  fila[2] = rs.getString(3).trim(); 
					  fila[3] = rs.getString(4).trim(); 
					  fila[4] = rs.getString(5).trim(); 
					  fila[5] = rs.getString(6).trim();
					
					 
					  	modelo.addRow(fila);  
			  
					}	   
				s.close();
						} catch (SQLException e1) {
							e1.printStackTrace();
							JOptionPane.showMessageDialog(null, "Error en la  SQLException: "+e1.getMessage(), "Avisa al Administrador Del Sistema",
										   JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
												  }
				              }
				private void agregar(final JTable tbl) {
				    tbl.addMouseListener(new java.awt.event.MouseAdapter() {
				        public void mouseClicked(MouseEvent e) {if(tabla.isEditing()){
							tabla.getCellEditor().stopCellEditing();
						}
						String folio = ""+modelo.getValueAt(tabla.getSelectedRow(), 0).toString().trim();
						txtFolio.setText(""+folio);
						String Desc = ""+modelo.getValueAt(tabla.getSelectedRow(), 1).toString().trim();
						txtDescripcion.setText(""+Desc);
						String NumCtrol = ""+modelo.getValueAt(tabla.getSelectedRow(), 2).toString().trim();
						txtNumeroControl.setText(""+NumCtrol);
						String Clasif = ""+modelo.getValueAt(tabla.getSelectedRow(), 3).toString().trim();
						cmbClasificacion.setSelectedItem(""+Clasif);
						String sts = ""+modelo.getValueAt(tabla.getSelectedRow(), 4).toString().trim();
					   if(sts.toString().equals("V"))
					   {
						   sts="VIGENTE";
					   }
					   else{sts="CANCELADO";}
					   cmbStatus.setSelectedItem(""+sts);
					   String Est = ""+modelo.getValueAt(tabla.getSelectedRow(), 5).toString().trim();
					   cmbEstablecimiento.setSelectedItem(""+Est);
					   btnEditar.setEnabled(true);
				}
				    });
				}
				private void init_tablaconsulta_numctrol(){	

					tabla.getTableHeader().setReorderingAllowed(false) ;
					tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
					
				    this.tabla.getColumnModel().getColumn(0).setMinWidth(80);
				    this.tabla.getColumnModel().getColumn(1).setMinWidth(250);
				    this.tabla.getColumnModel().getColumn(2).setMinWidth(250);
				    this.tabla.getColumnModel().getColumn(3).setMinWidth(160);
				    this.tabla.getColumnModel().getColumn(4).setMinWidth(140);
				    this.tabla.getColumnModel().getColumn(5).setMinWidth(140);
					   
					this.tabla.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",12));
					this.tabla.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",12));
					this.tabla.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",12));
					this.tabla.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",12));
					this.tabla.getColumnModel().getColumn(4).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",12));
					this.tabla.getColumnModel().getColumn(5).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",12));
				
					numCtrol();
					}

					private void numCtrol(){    
						modelo.setRowCount(0);
						Statement s;
						ResultSet rs;

						try {
							Connexion con = new Connexion();
							s = con.conexion().createStatement();
							rs = s.executeQuery("sp_select_filtro_servicios_establecimientos_numctrol '"+(txtFolio.getText().toString().trim())+"'");
				
						while (rs.next())
						{ 
						 String [] fila = new String[6];
						  fila[0] = rs.getString(1).trim();
						  fila[1] = rs.getString(2).trim();
						  fila[2] = rs.getString(3).trim(); 
						  fila[3] = rs.getString(4).trim(); 
						  fila[4] = rs.getString(5).trim(); 
						  fila[5] = rs.getString(6).trim();
						
						 
						  	modelo.addRow(fila); 
				  
						}	   
					s.close();
							} catch (SQLException e1) {
								e1.printStackTrace();
								JOptionPane.showMessageDialog(null, "Error en la  SQLException: "+e1.getMessage(), "Avisa al Administrador Del Sistema",
											   JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
													  }
					}
			
				
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Alta_De_Servicios_De_Establecimientos().setVisible(true);
		}catch(Exception e){	}
	}
	
	
	
	
	
	
	
	
	
	
}
