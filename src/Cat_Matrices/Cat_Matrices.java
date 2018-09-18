package Cat_Matrices;
import java.awt.Container;

import java.awt.Event;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Cat_Reportes.Cat_Reporte_Evaluacione_Operativas;
import Conexiones_SQL.Connexion;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Matrices.Obj_Matriz;
import Obj_Matrices.Obj_Seleccion_Tabla_Matriz;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Matrices extends JFrame{
	 //declaracion e inicializacion de var 
	Obj_Usuario usuario = new Obj_Usuario().LeerSession();
	Obj_Matriz  obm     = new Obj_Matriz();
	Obj_Seleccion_Tabla_Matriz Tabla_Matriz= new Obj_Seleccion_Tabla_Matriz();
	
	
	
	
	int bandera_guardado =0;
	int columnas = 4,checkbox=4;  
	String valor_catalogo="";
	String ValorBuffer;
	
	
	String[]Colum={"Orden","Etapa","Unidad De Inspeccion","Departamento"};

	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	
	JLabel lblFolio =new JLabel("Folio");
	JLabel lblDescripcion = new JLabel("Descripcion");
	JLabel lblEtapa = new JLabel("Etapa");
	JLabel lblAspecto_De_Etapa = new JLabel("Aspectos de la Etapa");
	JLabel lblUnidad_De_Inspeccion = new JLabel("Unidad De Inspeccion");
	JLabel lblDepartamento = new JLabel("Departamento");
	JLabel lblEstablecimiento = new JLabel("Establecimiento");
	
	public JTextField txtFolio = new Componentes().text(new JCTextField(), "", 30, "Int");
	public JTextField txtDescripcion = new Componentes().text(new JCTextField(), "Descripcion", 300, "String");
	
	JCButton btnBuscar    = new JCButton("Buscar"    ,"buscar.png","Azul"); 
	JCButton btnDeshacer  = new JCButton("Deshacer"  ,"deshacer16.png","Azul");
	JCButton btnGuardar   = new JCButton("Guardar"   ,"Guardar.png","Azul");
	JCButton btnAgg       = new JCButton("Agregar"   ,"Nuevo.png","Azul");	//agrega filas a la tabla
	JCButton btnQuitar    = new JCButton("Quitar"    ,"Delete.png","Azul");	//quita filas a la tabla
	JCButton btnNuevo     = new JCButton("Nuevo"     ,"Nuevo.png","Azul");
	JCButton btnEditar    = new JCButton("Editar"    ,"editar-16.png","Azul");
	JCButton btnFiltro    = new JCButton(""    ,"Filter-List-icon16.png","Azul");
	JButton btnBajar = new JButton(new ImageIcon("Iconos/down_icon&16.png"));
	JButton btnSubir = new JButton(new ImageIcon("Iconos/up_icon&16.png"));
	
	JCButton btnReporte    = new JCButton("Reporte"    ,"Lista.png","Azul"); 
	
	String []Etapa= {"PENDIENTE","AUTORIZADO","CANCELADO","FINALIZADO","NEGADO"};;
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbEtapa= new JComboBox(Etapa);
	
	String []Departamento =obm.Combo_Respuesta_Departamento();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbDepartamento=new JComboBox(Departamento);
	
	String []UnidadDeInspeccion={"PENDIENTE","AUTORIZADO","CANCELADO","FINALIZADO","NEGADO"};;
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbUnidadDeInspeccion=new JComboBox(UnidadDeInspeccion);
	
	 String Establecimiento[] ={"PENDIENTE","AUTORIZADO","CANCELADO","FINALIZADO","NEGADO"};;
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbEstablecimiento= new JComboBox(Establecimiento);
	
	public DefaultTableModel modelo = new DefaultTableModel(null, Colum){
		 @SuppressWarnings("rawtypes")
			Class[] types = new Class[]{
					java.lang.Object.class,
					java.lang.Object.class,
					java.lang.Object.class,
					java.lang.Object.class,
					
					
					
			};
			
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {
	         return types[columnIndex];
	     }
			public boolean isCellEditable(int fila, int columna){
				if(columna ==4)
					return true; return false;
			}
	    };
	    
	    
	
	public JTable tabla = new JTable(modelo);
	public JScrollPane scroll_tabla = new JScrollPane(tabla);
	


	public Cat_Matrices(){
		Catalogo();
	}
	public Cat_Matrices(String cadena){
		 valor_catalogo=cadena;
		 txtFolio.setText(valor_catalogo);
		Catalogo();
	}
	@SuppressWarnings("unused")
	
	public void Catalogo(){
		
	     setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
	       addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
                close();
            					}
	       			});
	        //creacion de la ventana
			setResizable(false);
			setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/evaluacion2.png"));
			setTitle("Matrices");
			panel.setBorder(BorderFactory.createTitledBorder("Crear, buscar o Modificar Matriz "));
        
	       	inial();
	       	InicializarCatalogo();
	       	btnSubir.setEnabled(false);
	       	btnBajar.setEnabled(false);
			btnAgg.setEnabled(false);
			btnQuitar.setEnabled(false);
			btnGuardar.setEnabled(false);
			
		
			this.setSize(955,780);
			this.setLocationRelativeTo(null);
			this.setResizable(false);
			int x=15, y=20, ancho=100,salto=20;
	
				panel.add(lblFolio).setBounds(x, y, ancho, 20);
				panel.add(txtFolio).setBounds(lblFolio.getX()+(ancho-20), y, ancho, 20);
				panel.add(btnFiltro).setBounds(txtFolio.getX()+txtFolio.getWidth()+15, y, 45, 20);
				panel.add(btnBuscar).setBounds((btnFiltro.getX()+btnFiltro.getWidth()+3), y, ancho, 20);
		
				panel.add(btnNuevo).setBounds(btnBuscar.getX()+btnBuscar.getWidth()+10, y, ancho, 20);
				panel.add(btnDeshacer).setBounds(btnNuevo.getX()+btnNuevo.getWidth()+10, y, ancho+10, 20);
				panel.add(btnEditar).setBounds(btnDeshacer.getX()+btnDeshacer.getWidth()+10, y, ancho, 20);
				
				panel.add(btnReporte).setBounds(btnEditar.getX()+btnEditar.getWidth()+110, y, ancho+20, 20);
		
				int y1=btnBuscar.getY();
				int x1=txtFolio.getX()+txtFolio.getWidth()+15;
		
				panel.add(lblDescripcion).setBounds(x, lblFolio.getY()+salto+10, ancho, 20);
				panel.add(txtDescripcion).setBounds(lblDescripcion.getX()+ancho-20, lblDescripcion.getY(), ancho+738, 20);
		
				panel.add(lblEstablecimiento).setBounds(x, 	txtDescripcion.getY()+salto, ancho+350, 20);
				panel.add(cmbEstablecimiento).setBounds(x, lblEstablecimiento.getY()+salto, ancho+350, 20);
		
				panel.add(lblEtapa).setBounds(x+cmbEstablecimiento.getX()+cmbEstablecimiento.getWidth(), lblEstablecimiento.getY(), ancho, 20);
				panel.add(cmbEtapa).setBounds(lblEtapa.getX(), lblEtapa.getY()+salto, ancho+350, 20);
		
				panel.add(lblUnidad_De_Inspeccion).setBounds(x, cmbEstablecimiento.getY()+salto ,ancho+200, 20);
				panel.add(cmbUnidadDeInspeccion).setBounds(x, lblUnidad_De_Inspeccion.getY()+salto, ancho+350, 20);
		
				panel.add(lblDepartamento).setBounds(x+cmbUnidadDeInspeccion.getX()+cmbUnidadDeInspeccion.getWidth(), cmbEtapa.getY()+salto, ancho+200, 20);
				panel.add(cmbDepartamento).setBounds(lblDepartamento.getX(), lblDepartamento.getY()+salto, ancho+350, 20);
	
				panel.add(btnAgg).setBounds((txtDescripcion.getWidth())-170, cmbDepartamento.getY()+salto+20, ancho+20, 20);
				panel.add(btnQuitar).setBounds(btnAgg.getX()+btnAgg.getWidth()+20, btnAgg.getY(),ancho+20, 20);
				
				panel.add(scroll_tabla).setBounds (x ,btnAgg.getY()+salto+10,920,500);
		
				int alturaBajoTabla = scroll_tabla.getY()+scroll_tabla.getHeight();
				int lado1=(btnQuitar.getX()+btnQuitar.getWidth()+20);
				int lado2 = +((scroll_tabla.getWidth())-120);
		
				panel.add(btnSubir).setBounds(x,alturaBajoTabla+salto,ancho/2 ,20);
				panel.add(btnBajar).setBounds(btnSubir.getX()+btnSubir.getWidth()+20, alturaBajoTabla+salto, ancho/2, 20);
				int xTablaAncho=x+scroll_tabla.getWidth();
				panel.add(btnGuardar).setBounds(xTablaAncho-100, alturaBajoTabla+salto, ancho, 20);
	
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
						
						///buscar con F1
						getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
						KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0), "help");
						getRootPane().getActionMap().put("help", new AbstractAction(){
							public void actionPerformed(ActionEvent e)
							{  
								try{ 
									// File archivo = menu_bar.nom_arch; 
									 Runtime runTime = Runtime.getRuntime(); 
									 Process p = runTime.exec("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe "+"\\192.168.2.26\\WebServer\\Cat_Matriz.html"); 
									 }catch(IOException e1){ 
									 System.out.print(e1.toString()); 
									 }
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
	             btnGuardar.addActionListener(opGuargar);
	             btnNuevo.addActionListener(opNuevo);
	             btnDeshacer.addActionListener(opDeshacer);
	             btnBuscar.addActionListener(opBuscar);
	             btnAgg.addActionListener(opAgregar);
	             btnQuitar.addActionListener(opQuitaFila); 
	             btnSubir.addActionListener(opOrdenar);
	             btnBajar.addActionListener(opOrdenar);
	             btnEditar.addActionListener(opEditar);
	             btnFiltro.addActionListener(opFiltro);
	             txtFolio.addKeyListener(opBuscarEnter);
	             
	             btnReporte.addActionListener(opReporte);
		
						}     public void inial()
						{
							this.tabla.getTableHeader().setReorderingAllowed(false) ;
							this.tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				      
							this.tabla.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",12));
							this.tabla.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",12));
							this.tabla.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",12));
							this.tabla.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",12));
							this.tabla.getColumnModel().getColumn(0).setMinWidth(40);
							this.tabla.getColumnModel().getColumn(1).setMinWidth(440);
							this.tabla.getColumnModel().getColumn(2).setMinWidth(250);
							this.tabla.getColumnModel().getColumn(3).setMinWidth(150);  

							
							
						}  
	
		public void init_tablaconsulta(){
			this.tabla.getTableHeader().setReorderingAllowed(false) ;
			this.tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
      
			this.tabla.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",12));
			this.tabla.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",12));
			this.tabla.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",12));
			this.tabla.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",12));
			this.tabla.getColumnModel().getColumn(0).setMinWidth(40);
			this.tabla.getColumnModel().getColumn(1).setMinWidth(375);
			this.tabla.getColumnModel().getColumn(2).setMinWidth(100);
			this.tabla.getColumnModel().getColumn(3).setMinWidth(100);  

			
			
			
			
			refrestabla();
								}
	
				private void refrestabla(){    
					modelo.setRowCount(0);  
					Statement s;
					ResultSet rs;
		
					try {
						Connexion con = new Connexion();
						s = con.conexion().createStatement();
						rs = s.executeQuery("exec sp_select_matrices_datos "+txtFolio.getText().toString().trim());
			
					while (rs.next())
					{ 
					 String [] fila = new String[4];
					  fila[0] = rs.getString(1).trim();
					  fila[1] = rs.getString(4).trim();
					  fila[2] = rs.getString(5).trim(); 
					  fila[3] = rs.getString(3).trim(); 
					  cmbEstablecimiento.setSelectedItem(""+rs.getString(6).toString().trim());
					 
					  	modelo.addRow(fila); 
			  
					}
					
					
					
					
				s.close();
						} catch (SQLException e1) {
							e1.printStackTrace();
							JOptionPane.showMessageDialog(null, "Error en la  SQLException: "+e1.getMessage(), "Avisa al Administrador Del Sistema",
										   JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
												  }
				
			}

					ActionListener opBuscar = new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if(txtFolio.getText().toString().equals("")){
								JOptionPane.showMessageDialog(null, "Es Necesario Escribir Un Folio Para Poder Reazalizar la Busqueda Matrices", "Aviso", JOptionPane.WARNING_MESSAGE,
										new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
																		}
							else
								{
									int f =Integer.parseInt(txtFolio.getText());	
										if(obm.Buscar_Descripciones(f).equals("No Se Encontro Registro Con Este folio")){
												JOptionPane.showMessageDialog(null, "No Se Encontro Registro Con Este folio", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
												txtFolio.requestFocus();
												ValorBuffer=(txtFolio.getText());
												return;
										}else{	
										    }
												init_tablaconsulta();
												String desc=obm.Buscar_Descripciones(f);
												txtDescripcion.setText(desc);
												String establecimiento=	(String) modelo.getValueAt(1, 5);
												cmbEstablecimiento.setSelectedItem(establecimiento);
												cmbEstablecimiento.setEnabled(false);
												btnNuevo.setEnabled(false);
												btnEditar.setEnabled(true);
												txtFolio.setEnabled(false);
								}
						   }  
					};
					
	
					ActionListener opFiltro = new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							Cat_Modal_Filtro_Matriz cmf =new Cat_Modal_Filtro_Matriz("");
							cmf.setVisible(true);
							txtFolio.setText(((Cat_Modal_Filtro_Matriz) cmf).getValor_catalogo());
							  btnBuscar.doClick();
								}
						};
		
				   ActionListener opEditar = new ActionListener() {
					   @SuppressWarnings("unused")
					public void actionPerformed(ActionEvent e) {
						   
						   int folio = Integer.parseInt(txtFolio.getText());
						 
							
						   int f =Integer.parseInt(txtFolio.getText());   
						   
						   btnSubir.setEnabled(true);
						   btnBajar.setEnabled(true);
						   btnAgg.setEnabled(true);
						   btnQuitar.setEnabled(true);
						   btnGuardar.setEnabled(true);
						   btnBuscar.setEnabled(false);
						   btnNuevo.setEnabled(false);  
						   cmbDepartamento.setEnabled(true);
						   cmbEtapa.setEnabled(true);
						   cmbUnidadDeInspeccion.setEnabled(true);
						   txtDescripcion.setEnabled(true);
						   txtFolio.setEnabled(false);
						   btnFiltro.setEnabled(false);
						   
						   
						  
						 
						   	bandera_guardado=1;
							
			
					   			}
				   			};
	
		   			ActionListener opReporte = new ActionListener() {
						   public void actionPerformed(ActionEvent e) {
							   new Cat_Reporte_Evaluacione_Operativas().setVisible(true);
						   }
			   		};
		   		
				   ActionListener opDeshacer = new ActionListener() {
					   public void actionPerformed(ActionEvent e) {
						Deshacer();
			
					   	     	}
				   		};
				   		
				 ActionListener opAgregar = new ActionListener() {
					 public void actionPerformed(ActionEvent e) {
						 if(txtDescripcion.getText().toString().equals("")){
							 JOptionPane.showMessageDialog(null, "Es Necesario Poner Una Descripcion De La Matriz", "Aviso", JOptionPane.WARNING_MESSAGE,
	            		  						new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
						 }else{
							 String[] row = new String[4];
							 row[0] = "";
							 row[1] = cmbEtapa.getSelectedItem().toString();
							 row[2] = cmbUnidadDeInspeccion.getSelectedItem().toString();
							 row[3] = cmbDepartamento.getSelectedItem().toString(); 
							
				
							 txtDescripcion.setEnabled(false);
							 cmbEstablecimiento.setEnabled(false);
	        	
							 modelo.addRow(row);
							 	for(int i = 0; i<tabla.getRowCount(); i++){
							 		tabla.setValueAt(i+1, i, 0);
							 			}
						 			}
					 			}
				 			};
				 ActionListener opNuevo = new ActionListener() {
					 public void actionPerformed(ActionEvent e) {
						 inial();
						 String foliosiguiente=(obm.FolioSiguiente());
						 txtFolio.setText(foliosiguiente);
						 txtFolio.setEnabled(false);
						 btnBuscar.setEnabled(false);
						 txtDescripcion.setText("");
			
					
						 modelo.setRowCount(0);
						 btnSubir.setEnabled(true);
						 btnBajar.setEnabled(true);
						 btnAgg.setEnabled(true);
						 btnQuitar.setEnabled(true);
						 btnGuardar.setEnabled(true);
						 cmbEstablecimiento.setEnabled(true);
			
						 cmbDepartamento.setEnabled(true);
						 cmbEstablecimiento.setEnabled(true);
						 cmbEtapa.setEnabled(true);
						 cmbUnidadDeInspeccion.setEnabled(true);
						 txtDescripcion.setEnabled(true);
						 txtDescripcion.requestFocus();
						 bandera_guardado=0; 
						 cmbDepartamento.setSelectedIndex(0);
						 cmbEstablecimiento.setSelectedIndex(0);
						 cmbEtapa.setSelectedIndex(0);
						 cmbUnidadDeInspeccion.setSelectedIndex(0);
						 btnFiltro.setEnabled(false);
						 
						
						 
					 }
			
				 };
	
				ActionListener opGuargar = new ActionListener() {
					public void actionPerformed(ActionEvent e) {
			
						if (modelo.getRowCount() == 0){
							JOptionPane.showMessageDialog(null, "La Tabla Para Aplicar La matriz No Contiene Datos. \n Hay que Agregarle para poder Guardar", "Aviso", JOptionPane.WARNING_MESSAGE,
	  						new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));

										}
						else{
								if(bandera_guardado==0){ 
									if(obm.guardar_matriz((String)cmbEstablecimiento.getSelectedItem() ,tabla_para_guardado(),txtDescripcion.getText().toString())){
										modelo.setRowCount(0);
											JOptionPane.showMessageDialog(null, "Se han Guardado Satisfatoriamente Los Datos Para Una Nueva Matriz ", "Aviso", JOptionPane.WARNING_MESSAGE,
            		  						                                   new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
										
											Deshacer();
//                    	
											return;
												}else{  
													JOptionPane.showMessageDialog(null, "No Se Pudo Guardar La Matriz  \n Error En el Guardado de la Matriz","Avise Al Adiministrador",JOptionPane.ERROR_MESSAGE,
															new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
													return;
											}
									     }else{
			
									    	 if(obm.Modidicar_matriz((String)cmbEstablecimiento.getSelectedItem(),tabla_para_guardado(),Integer.valueOf(txtFolio.getText().toString().trim()),txtDescripcion.getText().toString())){
					 
									    		 init_tablaconsulta();
									    		 JOptionPane.showMessageDialog(null, "Se han Actualizado Correctamente Los Datos de la Matriz ", "Aviso", JOptionPane.WARNING_MESSAGE,
				    		  						new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
				     
									    		 Deshacer();
									    		 return;
					
									    	 	}
									    	 
									    	 	bandera_guardado=0;
									     }  
									   }   
									}
					
								};   
									
								public Object[][] tabla_para_guardado(){
		
									Object[][] tab = new Object[tabla.getRowCount()][tabla.getColumnCount()];
		
										for(int i=0; i<tabla.getRowCount(); i++){
											for(int j=0; j<tabla.getColumnCount(); j++){
												tab[i][j]= modelo.getValueAt(i,j).toString().trim();
														}
													}
											return tab;
												}
	
								ActionListener opQuitaFila = new ActionListener() {
									public void actionPerformed(ActionEvent e) {
		
										int fila = tabla.getSelectedRow();
										 if(fila<0){
											 JOptionPane.showMessageDialog(null, "Seleccione una fila para poder Eliminar", "Aviso", JOptionPane.WARNING_MESSAGE,
  											  new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
											 return;
								      		 }else{      		 
											 modelo.removeRow(fila);
												for(int i = 0; i<tabla.getRowCount(); i++){
											 		tabla.setValueAt(i+1, i, 0);
											 			}
								      		 }
										}
									};
	
	
									ActionListener opOrdenar = new ActionListener() {
										public void actionPerformed(ActionEvent arg0) {
											if(tabla.getRowCount()>0){
		
												if(tabla.getSelectedRow()>-1){
					
													if(arg0.getSource().equals(btnSubir)){
														moverFila(-1);
													}else{
														moverFila(1);
													}
													for(int i = 0; i<tabla.getRowCount(); i++){
														tabla.setValueAt(i+1, i, 0);
													}
												}else{
													JOptionPane.showMessageDialog(null,"No esta seleccionada ninguna fila!","Aviso",JOptionPane.INFORMATION_MESSAGE);
													return;
													}	
			
												}else{
													JOptionPane.showMessageDialog(null,"No Hay Regisatros En La Tabla!","Aviso",JOptionPane.INFORMATION_MESSAGE);
													return;
													}
			
												}
	
										};
	
										public void moverFila(int mover){
		
											if( (mover < 0 && tabla.getSelectedRow() == 0) || (mover > 0 && tabla.getSelectedRow() == tabla.getRowCount()-1) ){
												JOptionPane.showMessageDialog(null,"Has llegado al Limite Ya no se Puede Mover esta Columna","Aviso",JOptionPane.INFORMATION_MESSAGE);
												return;
												}else{
													modelo.moveRow(tabla.getSelectedRow(),tabla.getSelectedRow(),tabla.getSelectedRow()+mover);
													tabla.setRowSelectionInterval(tabla.getSelectedRow()+mover, tabla.getSelectedRow()+mover);
													}
												}
	
	
  
										KeyListener opBuscarEnter = new KeyListener() {
											@Override
											public void keyTyped(KeyEvent e){
						
												if(e.getKeyChar() == KeyEvent.VK_ENTER){
													if(txtFolio.getText().toString().equals("")){
														JOptionPane.showMessageDialog(null, "Es Necesario Teclear Un Folio Para Poder Buscar Matrices", "Aviso", JOptionPane.WARNING_MESSAGE,	new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
														txtFolio.requestFocus();
														return;

													}else{
														int f =Integer.parseInt(txtFolio.getText());	
														if(obm.Buscar_Descripciones(f).equals("No Se Encontro Registro Con Este folio")){
															JOptionPane.showMessageDialog(null, "No Se Encontro Registro Con Este folio", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
															txtFolio.requestFocus();
															return;
														}else{					
															init_tablaconsulta();
				
															String desc=obm.Buscar_Descripciones(f);
															txtDescripcion.setText(desc);
															String establecimiento=	""+cmbEstablecimiento.getSelectedItem();
				
															cmbEstablecimiento.setSelectedItem(establecimiento);
															cmbEstablecimiento.setEnabled(false);
															txtDescripcion.requestFocus();

															cmbEstablecimiento.setSelectedItem(establecimiento);
															cmbEstablecimiento.setEnabled(false);
															btnNuevo.setEnabled(false);
															btnEditar.setEnabled(true);
															
															txtFolio.setEnabled(false);
																}
			    
															}
			
														}
													}

												@Override
												public void keyPressed(KeyEvent e) {
			
														}
												
												@Override
												public void keyReleased(KeyEvent e) {
			
															}
													};

									private void close(){
										dispose();
											}
							public void InicializarCatalogo(){
								btnEditar.setEnabled(false);
								cmbDepartamento.setEnabled(false);
								cmbEstablecimiento.setEnabled(false);
								cmbEtapa.setEnabled(false);
								cmbUnidadDeInspeccion.setEnabled(false);
								txtDescripcion.setEnabled(false);
	
								cmbDepartamento.setSelectedIndex(0);
								cmbEstablecimiento.setSelectedIndex(0);
								cmbEtapa.setSelectedIndex(0);
								cmbUnidadDeInspeccion.setSelectedIndex(0);
	
														}  
							public void Deshacer(){
						   		   txtFolio.setEnabled(true);
								   txtFolio.setText("");
								   txtDescripcion.setEnabled(true);
								   txtDescripcion.setText("");
								   modelo.setRowCount(0);//init table
								   btnSubir.setEnabled(false);
								   btnBajar.setEnabled(false);
								   btnAgg.setEnabled(false);
								   btnQuitar.setEnabled(false);
								   btnBuscar.setEnabled(true);
								   txtFolio.requestFocus();
								   btnGuardar.setEnabled(false); 
								   bandera_guardado=0; 
								   btnNuevo.setEnabled(true);
								   btnFiltro.setEnabled(true);
								   InicializarCatalogo();
						   	}
				public static void main(String []yo)
					{
					try{
						UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
						new Cat_Matrices("").setVisible(true);
						}catch(Exception e){	
										}
									  }
								    }	
