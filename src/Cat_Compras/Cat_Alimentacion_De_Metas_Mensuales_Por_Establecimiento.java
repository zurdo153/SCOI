package Cat_Compras;

import java.awt.Component;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;

import Conexiones_SQL.Connexion;
import Obj_Compras.Obj_Metas_Establecimiento_periodo;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.JCButton;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Alimentacion_De_Metas_Mensuales_Por_Establecimiento extends JFrame {
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	String[]Colum={"Folio","Nombre de Clasificacion ","Venta Real ","Sugerido A %","Meta de Venta 'A'","Sugerido B %","Meta de Venta 'B'","Sugerido C %","Meta de Venta 'C'"};

	 int columna=3;
	 int columna2=5;
	 int columna3=7;
     int fila=0;
	
	Obj_Metas_Establecimiento_periodo omep = new Obj_Metas_Establecimiento_periodo();
	
	JLabel lblEstablecimiento =new JLabel("Establecimiento:");
	JLabel lblAnio = new JLabel("Año: ");
	JLabel lblMes = new JLabel("Mes: ");
	JLabel lblGrupo = new JLabel("Grupo: ");
	
	JCButton btnGenerar    = new JCButton("Calcular Metas"    ,"bandera-a-cuadros-para-terminar-icono-8019-32.png","Azul"); 
	JCButton btnGuardar    = new JCButton("Guardar"    ,"Guardar.png","Azul"); 
	JCButton btnDeshacer = new JCButton("Deshacer","deshacer16.png","Azul");

	    String Establecimiento[] = new Obj_Establecimiento().Combo_Establecimiento_Estado_resultados();
	      @SuppressWarnings({ "rawtypes", "unchecked" })
		JComboBox cmbEstablecimiento= new JComboBox(Establecimiento);
		
		String []anio=omep.Combo_Respuesta_anio(); 
		  @SuppressWarnings({ "rawtypes", "unchecked" })
		JComboBox cmbAnio=new JComboBox(anio);
		
		   @SuppressWarnings({ "rawtypes", "unchecked" })
		JComboBox cmbMes= new JComboBox(new String[]{"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"});

		   @SuppressWarnings({ "rawtypes", "unchecked" })
		JComboBox cmbGrupo= new JComboBox(new String[]{"Seleccione un Grupo","Alimentos","Dulcerias","Ferreterias","Papelerias","Refaccionarias","Supermercados","Izacel"});
		
		public DefaultTableModel modelo = new DefaultTableModel(null, Colum){
			 @SuppressWarnings("rawtypes")
				Class[] types = new Class[]{
						java.lang.Object.class,
						java.lang.Object.class,
						java.lang.Object.class,
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
					if(columna == 3||columna == 5||columna == 7)
						return true;
					    return false;
				}
		    };
		
		public JTable tabla = new JTable(modelo);
		public JScrollPane scroll_tabla = new JScrollPane(tabla);
		
		
		
public Cat_Alimentacion_De_Metas_Mensuales_Por_Establecimiento() {
	            this.setSize(1024,550);
				this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/bandera-a-cuadros-para-terminar-icono-8019-64.png"));
				this.setTitle("Alimentacion De Metas Mensuales Por Establecimiento");
				this.setLocationRelativeTo(null);
				this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				panel.setBorder(BorderFactory.createTitledBorder("#"));

				int x=15, y=20, ancho=100,salto=20 ,z=25;
				
				panel.add(lblEstablecimiento).setBounds(x, y, ancho-15, z);
				panel.add(cmbEstablecimiento).setBounds(lblEstablecimiento.getX()+lblEstablecimiento.getWidth(), y, ancho+70, z);

				panel.add(lblAnio).setBounds(x+cmbEstablecimiento.getX()+cmbEstablecimiento.getWidth(), y, ancho/3, z);
				panel.add(cmbAnio).setBounds(lblAnio.getX()+lblAnio.getWidth(), y, ancho, z);
				
				panel.add(lblMes).setBounds(x+cmbAnio.getX()+cmbAnio.getWidth(), y, ancho/3, z);
				panel.add(cmbMes).setBounds(lblMes.getX()+lblMes.getWidth(), y, ancho, z);
				
				panel.add(lblGrupo).setBounds(x+cmbMes.getX()+cmbMes.getWidth(), y,(ancho/2), z);
				panel.add(cmbGrupo).setBounds(lblGrupo.getX()+lblGrupo.getWidth(), y, (ancho+50), z);
				
				ancho=140;
				panel.add(btnGenerar).setBounds (x+cmbGrupo.getX()+cmbGrupo.getWidth()+35 ,y,ancho+20, z);
				panel.add(btnDeshacer).setBounds(x                                        ,470,ancho, z);
				panel.add(btnGuardar).setBounds (x+cmbGrupo.getX()+cmbGrupo.getWidth()+55 ,470,ancho, z);
				
				panel.add(scroll_tabla).setBounds (x ,cmbGrupo.getY()+salto+10,980,400);
			
				cont.add(panel);
				
				tabla.addKeyListener(op_key);
				btnGuardar.addActionListener(opGuargar);
				btnGenerar.addActionListener(opGenerarTabla);
				btnDeshacer.addActionListener(opDeshacer);
		        btnGuardar.setEnabled(false);
		        init_tablaconsulta();
		        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
		        getRootPane().getActionMap().put("escape", new AbstractAction(){
		            public void actionPerformed(ActionEvent e)
		            {                 	    btnDeshacer.doClick();
		            }
		        });	
		}



public void init_tablaconsulta(){
	this.tabla.getTableHeader().setReorderingAllowed(false) ;
	this.tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

	this.tabla.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",12));
	this.tabla.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",12));
	this.tabla.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","derecha","Arial","negrita",12));
	this.tabla.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",12));
	this.tabla.getColumnModel().getColumn(4).setCellRenderer(new tablaRenderer("texto","derecha","Arial","negrita",12));
	this.tabla.getColumnModel().getColumn(5).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",12));
	this.tabla.getColumnModel().getColumn(6).setCellRenderer(new tablaRenderer("texto","derecha","Arial","negrita",12));
	this.tabla.getColumnModel().getColumn(7).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",12));
	this.tabla.getColumnModel().getColumn(8).setCellRenderer(new tablaRenderer("texto","derecha","Arial","negrita",12));

	this.tabla.getColumnModel().getColumn(0).setMinWidth(40);
	this.tabla.getColumnModel().getColumn(1).setMinWidth(200);
	this.tabla.getColumnModel().getColumn(2).setMinWidth(100);
	this.tabla.getColumnModel().getColumn(3).setMinWidth(100);  
	this.tabla.getColumnModel().getColumn(4).setMinWidth(100);
	this.tabla.getColumnModel().getColumn(5).setMinWidth(100);
	this.tabla.getColumnModel().getColumn(6).setMinWidth(100);
	this.tabla.getColumnModel().getColumn(7).setMinWidth(100);
	this.tabla.getColumnModel().getColumn(8).setMinWidth(100);
	
						}

	private void refrestabla(){    
			modelo.setRowCount(0);
			Statement s;
			ResultSet rs;
			 String [] fila = new String[9];
			try {
				Connexion con = new Connexion();
				s = con.conexion_ventas().createStatement();
				rs = s.executeQuery("exec sp_sugerido_de_metas_por_mes_año_y_establecimiento "+"'"+cmbGrupo.getSelectedItem()+"',"+cmbAnio.getSelectedItem()+","+"'"+cmbMes.getSelectedItem()+"',"+"'"+cmbEstablecimiento.getSelectedItem()+"'");
				   while (rs.next()){ 
					
				  fila[0] = rs.getString(1).trim();
				  fila[1] = rs.getString(2).trim()+"";
				  fila[2] = rs.getString(3).trim()+""; 
				  fila[3] = rs.getString(4).trim(); 
				  fila[4] = rs.getString(5).trim(); 
				  fila[5] = rs.getString(6).trim(); 
				  fila[6] = rs.getString(7).trim();
				  fila[7] = rs.getString(8).trim(); 
				  fila[8] = rs.getString(9).trim(); 
				  	modelo.addRow(fila);
				  	  }
		s.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error en la  SQLException: "+e1.getMessage(), "Avisa al Administrador Del Sistema",JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
										  }
	}
	
	public void ReCalcular(){
		   for(int i = 0; i<tabla.getRowCount(); i++){
			//Funcion Redondeo necesita parametro vta y sugerido            columna vta                       columna sugerido     pone colum meta                                  
		 		tabla.setValueAt((Redondear(Double.valueOf((String) modelo.getValueAt(i, 2)),Float.valueOf((String) modelo.getValueAt(i, 3)))), i, 4);
		 		tabla.setValueAt((Redondear(Double.valueOf((String) modelo.getValueAt(i, 2)),Float.valueOf((String) modelo.getValueAt(i, 5)))), i, 6);
				tabla.setValueAt((Redondear(Double.valueOf((String) modelo.getValueAt(i, 2)),Float.valueOf((String) modelo.getValueAt(i, 7)))), i, 8);
			}
	}	
	
	public double Redondear(double numero, float porcentaje){
		porcentaje=1+(porcentaje/100);
		return (Math.rint((((numero*porcentaje)/100)+0.5))*100);
    }
	
	public void buscaMetaFecha(){    
			modelo.setRowCount(0);
			Statement s;
			ResultSet rs;

			try {
				Connexion con = new Connexion();
				s = con.conexion_ventas().createStatement();
				rs = s.executeQuery("exec sp_consulta_de_metas_mensuales_por_establecimiento '"+cmbEstablecimiento.getSelectedItem().toString().trim()+"',"+cmbAnio.getSelectedItem().toString().trim()+","+cmbMes.getSelectedIndex());
			 String [] fila = new String[10];
			while (rs.next()){   
				  fila[0] = rs.getString(1).trim();//clasificacionMeta
				  fila[1] = rs.getString(2).trim();//code_esta
				  fila[2] = rs.getString(3).trim();//meta_mensual 
				  fila[3] = rs.getString(4).trim();//mes 
				  fila[4] = rs.getString(5).trim();//año 
				  fila[5] = rs.getString(6).trim();//estatus 
				  fila[6] = rs.getString(7).trim();//porcentaje
				  fila[7] = rs.getString(8).trim();//porcentaje de b 
				  fila[8] = rs.getString(9).trim();//porcentaje de C 
				 					modelo.addRow(fila); 
			}
			
		s.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error en la  SQLException: "+e1.getMessage(), "Avisa al Administrador Del Sistema", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
										  }
	}
	
	ActionListener opCalcular = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReCalcular();
			   }  
		};
		
	ActionListener opGenerarTabla = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
				btnGuardar.setEnabled(false);
				int anio=Integer.valueOf((String) cmbAnio.getSelectedItem());
				int mes =cmbMes.getSelectedIndex()+1;
				String estab = (String)cmbEstablecimiento.getSelectedItem().toString().trim();
				int cod_meta=cmbGrupo.getSelectedIndex();
				
				if(cmbEstablecimiento.getSelectedItem().toString().trim().equals("Selecciona un Establecimiento")){
					JOptionPane.showMessageDialog(null, "Es Necesario Selecionar Un Establecimiento ", "Aviso",JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));	
					cmbEstablecimiento.requestFocus();
					cmbEstablecimiento.showPopup();
					return;
				}
				if(cmbGrupo.getSelectedItem().toString().trim().equals("Seleccione un Grupo")){
					JOptionPane.showMessageDialog(null, "Es Necesario Selecionar Un Grupo ", "Aviso",JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));	
					cmbGrupo.requestFocus();
					cmbGrupo.showPopup();
					return;
				}
				
				if( omep.buscar_metas_periodo(anio,mes,cmbEstablecimiento.getSelectedItem().toString().trim()).equals("si")){
					
					JOptionPane.showMessageDialog(null, "La Meta Que Estas Queriendo Calcular Ya Existe y No Puede Ser Modificada,\n Esta Se Cargará A Continuación", "Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));	
					buscaMetaFecha();
					btnGenerar.setEnabled(false);
					btnGuardar.setEnabled(false);
					cmbAnio.setEnabled(false);
					cmbMes.setEnabled(false);
					cmbEstablecimiento.setEnabled(false);
					cmbGrupo.setEnabled(false);
					return;
			   }else{
			            if(cmbGrupo.getSelectedItem().toString().trim().equals("Supermercados")){
			            	cod_meta=1;
			            }	 
			            
			            if(cmbGrupo.getSelectedItem().toString().trim().equals("Alimentos")){
			            	cod_meta=7;
			            }
			            
			            if(cmbGrupo.getSelectedItem().toString().trim().equals("Papelerias")){
			            	cod_meta=16;
			            }
			            
			            if(cmbGrupo.getSelectedItem().toString().trim().equals("Refaccionarias")){
			            	cod_meta=23;
			            }
			            
			            if(cmbGrupo.getSelectedItem().toString().trim().equals("Izacel")){
			            	cod_meta=49;
			            }
			            
			            if(cmbGrupo.getSelectedItem().toString().trim().equals("Dulcerias")){
			            	cod_meta=57;
			            }

			            if(cmbGrupo.getSelectedItem().toString().trim().equals("Ferreterias")){
			            	cod_meta=79;
			            }
			            
						if(omep.buscar_metas_a_generar(anio, mes,cod_meta,estab).equals("no") ){
								modelo.setRowCount(0);
								JOptionPane.showMessageDialog(null, "No Se Pudieron Calcular Metas Con Los Parametros Seleccionados "
										+ "  \no No Existe Venta del Año Anterior Seleccionado:["+cmbAnio.getSelectedItem().toString().trim()+"]"
										+ "  \nA Continuación Unos Ejemplos De Ayuda"
										+ "  \nEjemplo 1: Establecimiento [SUPER I]        Grupo [Supermercados] "
										+ "  \nEjemplo 2: Establecimiento [ESPACIO 35] Grupo [Alimentos]"
										+ "  \nEjemplo 3: Establecimiento [PAPER CITY] Grupo [Papelerias]"
										+ "  \nEjemplo 3: Establecimiento [FIESTILANDIA] Grupo [Dulcerias]"
										+ "  \nEjemplo 3: Establecimiento [REFACCIONARIA] Grupo [Refaccionarias]"
										, "Aviso",JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));	
								return;						
						}else{
							    refrestabla();
							    btnGenerar.setEnabled(false);
								btnGuardar.setEnabled(true);
								cmbAnio.setEnabled(false);
								cmbMes.setEnabled(false);
								cmbEstablecimiento.setEnabled(false);
								cmbGrupo.setEnabled(false);
								return;
						} 
			   }
		}
	};
		
	ActionListener opDeshacer  = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tabla.isEditing()){
					tabla.getCellEditor().stopCellEditing();
				}
				modelo.setRowCount(0);
				btnGuardar.setEnabled(false);
				cmbAnio.setEnabled(true);
				cmbAnio.setSelectedIndex(0);
				cmbMes.setEnabled(true);
				cmbMes.setSelectedIndex(0);
				cmbEstablecimiento.setEnabled(true);
				cmbEstablecimiento.setSelectedIndex(0);
				cmbGrupo.setEnabled(true);	
				cmbGrupo.setSelectedIndex(0);
				btnGenerar.setEnabled(true);
	            cmbEstablecimiento.showPopup();
			   }  
	};
		
	ActionListener opGuargar = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tabla.isEditing()){
					tabla.getCellEditor().stopCellEditing();
				}
				
			    if (modelo.getRowCount() == 0){
			 	       JOptionPane.showMessageDialog(null, "La Tabla Metas por Periodo \n No contiene Datos ", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
				}else{
					int anio=Integer.valueOf((String) cmbAnio.getSelectedItem());
					int mes =cmbMes.getSelectedIndex()+1;
					String Establecimiento =""+cmbEstablecimiento.getSelectedItem().toString().trim();
					
						if(omep.buscar_metas_periodo(anio, mes, Establecimiento).equals("no")){ 
							if(omep.guardar_matriz(tabla_para_guardado(),Establecimiento,cmbMes.getSelectedIndex()+1,Integer.parseInt((String) cmbAnio.getSelectedItem()))){
								modelo.setRowCount(0);
									JOptionPane.showMessageDialog(null, "Se han Guardado Satisfactoriamente Las Metas Del Establecimiento "+cmbEstablecimiento.getSelectedItem().toString().trim()+" "+" Del Mes De "+cmbMes.getSelectedItem().toString().trim(), "Aviso", JOptionPane.WARNING_MESSAGE,  new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
									btnDeshacer.doClick();
									return;
										}else{  
											JOptionPane.showMessageDialog(null, "No Se Guardaron Las Metas\n Error En el Guardado de la Metas Mensuales Por Establecimiento","Avise Al Administrador Del Sistema",JOptionPane.ERROR_MESSAGE, new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
											return;
									}
							     }else{
							    	 JOptionPane.showMessageDialog(null, "ya existen metas con este periodo  \n Error En el Guardado de la Metas por Periodo","Avise Al Adiministrador",JOptionPane.ERROR_MESSAGE, new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
                                     ////falta hacer el update para cambios en las metas
//							    	 if(obm.Modidicar_matriz(tabla_para_guardado())){
//			 
//							    		 init_tablaconsulta();
//							    		 JOptionPane.showMessageDialog(null, "Se han Actualizado Correctamente Los Datos de la Matriz ", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
							    	 btnDeshacer.doClick();
							    	 return;
//							    	 	}
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
		  KeyListener op_key = new KeyListener() {
		
				public void keyTyped(KeyEvent e) {}
				public void keyReleased(KeyEvent e) {
					switch (tabla.getSelectedColumn()) {
				
				case 3:
					ValidaValor();	
		
					break;

				case 5:
					ValidaValor2();	
					break;
				case 7:
					ValidaValor3();	
					break;
				}
				}
				public void keyPressed(KeyEvent e) {}
			};
			
			 public boolean ValidaValor(){
					boolean valor=false;
								if(isNumeric(modelo.getValueAt(tabla.getSelectedRow()+1,columna).toString().trim())){
											RecorridoFoco(tabla.getSelectedRow());
											valor = true;
									}else{
											tabla.getSelectionModel().setSelectionInterval(fila, fila);
											JOptionPane.showMessageDialog(null, "La Fila  [ "+(fila+1)+" ] En La Columna Cantidad Solo Acepta Numeros Enteros","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
											tabla.editCellAt(fila, columna);
											Component accion=tabla.getEditorComponent();
											accion.requestFocus();
									}
					return valor;
				}

		   private boolean isNumeric(String cadena){
					try {
						if(cadena.equals("")){
				    		return true;
						}else{
							Integer.parseInt(cadena);
				    		return true;
						}
					} catch (NumberFormatException nfe){
						return false;
					}
				}
		@SuppressWarnings("deprecation")
		public void RecorridoFoco(int filap){
			fila=filap;
			int cantidadDeFilas = tabla.getRowCount();
			String sacarFocoDeTabla = "no";
			if(fila == cantidadDeFilas-1){
					if(columna==3){
						for(int i =0; i<tabla.getRowCount(); i++){
							 ReCalcular(); 
						}
						sacarFocoDeTabla="si";
							}
			}else{
				sacarFocoDeTabla = "no";
					for(int i =0; i<tabla.getRowCount(); i++){
					      ReCalcular(); 
				}
				fila=fila+1;
			}
			tabla.getSelectionModel().setSelectionInterval(fila, fila);
			tabla.editCellAt(fila, columna);
			Component accion=tabla.getEditorComponent();
			
			final JTextComponent jtc = (JTextComponent)accion;
			  jtc.requestFocus();
			  jtc.selectAll();	
			
			if(sacarFocoDeTabla.equals("si")){
				tabla.lostFocus(null, null);
				tabla.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
				tabla.getSelectionModel().clearSelection();
				if(fila >= tabla.getRowCount()-1)
				{
				fila=0;
				return;
					
				}
			}
		};
		//foco2
		KeyListener op_key2 = new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {
				ValidaValor2();	
			}
			public void keyPressed(KeyEvent e) {}
		};
		
		 public boolean ValidaValor2(){
				boolean valor=false;
							if(isNumeric(modelo.getValueAt(tabla.getSelectedRow()+1,columna2).toString().trim())){
										RecorridoFoco2(tabla.getSelectedRow());
										valor = true;
								}else{
										tabla.getSelectionModel().setSelectionInterval(fila, fila);
										JOptionPane.showMessageDialog(null, "La Fila  [ "+(fila+1)+" ] En La Columna Cantidad Solo Acepta Numeros Enteros","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
										tabla.editCellAt(fila, columna2);
										Component accion=tabla.getEditorComponent();
										accion.requestFocus();
								}
				return valor;
			}

	@SuppressWarnings("deprecation")
	public void RecorridoFoco2(int filap){
		fila=filap;
		int cantidadDeFilas = tabla.getRowCount();
		String sacarFocoDeTabla = "no";
		if(fila == cantidadDeFilas-1){
				if(columna2==5){
					for(int i =0; i<tabla.getRowCount(); i++){
						 ReCalcular(); 
					}
					sacarFocoDeTabla="si";
						}
			
				
		}else{
			sacarFocoDeTabla = "no";
				for(int i =0; i<tabla.getRowCount(); i++){
				      ReCalcular(); 
			}
			fila=fila+1;
		}
		tabla.getSelectionModel().setSelectionInterval(fila, fila);
		tabla.editCellAt(fila, columna2);
		Component accion=tabla.getEditorComponent();
		
		final JTextComponent jtc = (JTextComponent)accion;
		  jtc.requestFocus();
		  jtc.selectAll();	
		
		if(sacarFocoDeTabla.equals("si")){
			tabla.lostFocus(null, null);
			tabla.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
			tabla.getSelectionModel().clearSelection();
			//fila=0;
			if(fila >= tabla.getRowCount()-1)
			{
			fila=0;
			return;
				
			}
		}
	};
	//foco3
	KeyListener op_key3 = new KeyListener() {
		public void keyTyped(KeyEvent e) {}
		public void keyReleased(KeyEvent e) {
			ValidaValor3();	
		}
		public void keyPressed(KeyEvent e) {}
	};
	
	 public boolean ValidaValor3(){
			boolean valor=false;
						if(isNumeric(modelo.getValueAt(tabla.getSelectedRow()+1,columna3).toString().trim())){
							
									RecorridoFoco3(tabla.getSelectedRow());
									valor = true;
							 
							 }else{
									tabla.getSelectionModel().setSelectionInterval(fila, fila);
									JOptionPane.showMessageDialog(null, "La Fila  [ "+(fila+1)+" ] En La Columna Cantidad Solo Acepta Numeros Enteros","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
									tabla.editCellAt(fila, columna3);
									Component accion=tabla.getEditorComponent();
									accion.requestFocus();
							}
			return valor;
		}

@SuppressWarnings("deprecation")
public void RecorridoFoco3(int filap){
	fila=filap;
	int cantidadDeFilas = tabla.getRowCount();
	String sacarFocoDeTabla = "no";
	if(fila == cantidadDeFilas-1){
			if(columna3==7){
				for(int i =0; i<tabla.getRowCount(); i++){
					 ReCalcular(); 
				}
				sacarFocoDeTabla="si";
					}
		
			
	}else{
		sacarFocoDeTabla = "no";
			for(int i =0; i<tabla.getRowCount(); i++){
			      ReCalcular(); 
		}
		fila=fila+1;
	}
	tabla.getSelectionModel().setSelectionInterval(fila, fila);
	tabla.editCellAt(fila, columna3);
	Component accion=tabla.getEditorComponent();
	
	final JTextComponent jtc = (JTextComponent)accion;
	  jtc.requestFocus();
	  jtc.selectAll();	
	
	if(sacarFocoDeTabla.equals("si")){
		tabla.lostFocus(null, null);
		tabla.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		tabla.getSelectionModel().clearSelection();
		//fila=0;
		if(fila >= tabla.getRowCount()-1)
		{
		fila=0;
		return ;
			
		}
	}
};

		
public static void main(String []yo)
{
try{
	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	new Cat_Alimentacion_De_Metas_Mensuales_Por_Establecimiento().setVisible(true);
	}catch(Exception e){	
					}
				  }
		  }

		  
