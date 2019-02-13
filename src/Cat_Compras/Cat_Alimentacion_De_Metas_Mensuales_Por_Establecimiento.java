package Cat_Compras;

import java.awt.Container;
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
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import Conexiones_SQL.Connexion;
import Obj_Compras.Obj_Metas_Establecimiento_periodo;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.JCButton;
import Obj_Principal.Obj_tabla;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Alimentacion_De_Metas_Mensuales_Por_Establecimiento extends JFrame {
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	String[]Colum={"Folio","Nombre de Clasificacion ","Venta Real ","Sugerido A %","Meta de Venta 'A'","Sugerido B %","Meta de Venta 'B'","Sugerido C %","Meta de Venta 'C'", "Utilidad"};

	int columna=0;
    int fila=0;
	
    Obj_tabla  Objetotabla = new Obj_tabla();
	Obj_Metas_Establecimiento_periodo omep = new Obj_Metas_Establecimiento_periodo();
	
	JLabel lblEstablecimiento =new JLabel("Establecimiento:");
	JLabel lblAnio = new JLabel("Año: ");
	JLabel lblMes = new JLabel("Mes: ");
	JLabel lblGrupo = new JLabel("Grupo: ");
	
	JCButton btnGenerar    = new JCButton("Calcular Metas"    ,"bandera-a-cuadros-para-terminar-icono-8019-32.png","Azul"); 
	JCButton btnGuardar    = new JCButton("Guardar"    ,"Guardar.png","Azul"); 
	JCButton btnDeshacer = new JCButton("Deshacer","deshacer16.png","Azul");

	    String Establecimiento[] = new Obj_Establecimiento().Combo_Establecimientos("R");
	      @SuppressWarnings({ "rawtypes", "unchecked" })
		JComboBox cmbEstablecimiento= new JComboBox(Establecimiento);
		
		String []anio=omep.Combo_Respuesta_anio(); 
		  @SuppressWarnings({ "rawtypes", "unchecked" })
		JComboBox cmbAnio=new JComboBox(anio);
		
		   @SuppressWarnings({ "rawtypes", "unchecked" })
		JComboBox cmbMes= new JComboBox(new String[]{"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"});

		   @SuppressWarnings({ "rawtypes", "unchecked" })
		JComboBox cmbGrupo= new JComboBox(new String[]{"Seleccione un Grupo","Alimentos","Dulcerias","Depositos","Ferreterias","Izacel","Papelerias","Refaccionarias","Supermercados"});
		
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
		JToolBar menu_toolbar       = new JToolBar();
		
public Cat_Alimentacion_De_Metas_Mensuales_Por_Establecimiento() {
	            this.setSize(1024,550);
				this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/bandera-a-cuadros-para-terminar-icono-8019-64.png"));
				this.setTitle("Alimentacion De Metas Mensuales Por Establecimiento");
				this.setLocationRelativeTo(null);
				this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				this.panel.setBorder(BorderFactory.createTitledBorder("#"));

				this.menu_toolbar.add(cmbEstablecimiento );
				this.menu_toolbar.addSeparator(   );
				this.menu_toolbar.addSeparator(   );
				this.menu_toolbar.add(cmbAnio );
				this.menu_toolbar.addSeparator(   );
				this.menu_toolbar.addSeparator(   );
				this.menu_toolbar.add(cmbMes );
				this.menu_toolbar.addSeparator(   );
				this.menu_toolbar.addSeparator(   );
		   	    this.menu_toolbar.add(btnGenerar    );
				this.menu_toolbar.addSeparator(   );
				this.menu_toolbar.addSeparator(   );
				this.menu_toolbar.add(btnDeshacer   );
				this.menu_toolbar.addSeparator(   );
				this.menu_toolbar.addSeparator(   );
				this.menu_toolbar.add(btnGuardar );
				this.menu_toolbar.setFloatable(false);
				
				panel.add(menu_toolbar).setBounds (15 ,20 ,980, 25);
				panel.add(scroll_tabla).setBounds (15 ,50 ,980,440);
			
				cont.add(panel);
				
				agregar_alclick(tabla);
				tabla.addKeyListener(op_validanumero_en_celda);
				
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
	this.tabla.getColumnModel().getColumn(9).setCellRenderer(new tablaRenderer("texto","derecha","Arial","negrita",12));
	
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

	private void refrestabla(String Qry){    
			modelo.setRowCount(0);
			Statement s;
			ResultSet rs;
			 String [] fila = new String[10];
			 System.out.println(Qry);
			try {
				Connexion con = new Connexion();
				s = con.conexion_ventas().createStatement();
				rs = s.executeQuery(Qry);
				  
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
				  fila[9] = rs.getString(10).trim(); 
				  	modelo.addRow(fila);
				  	  }
		s.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error en la  SQLException: "+e1.getMessage(), "Avisa al Administrador Del Sistema",JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
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
				
				if(cmbEstablecimiento.getSelectedItem().toString().trim().equals("Selecciona un Establecimiento")){
					JOptionPane.showMessageDialog(null, "Es Necesario Selecionar Un Establecimiento ", "Aviso",JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));	
					cmbEstablecimiento.requestFocus();
					cmbEstablecimiento.showPopup();
					return;
				}
				
				if( omep.buscar_metas_periodo(anio,mes,estab).equals("si")){
					JOptionPane.showMessageDialog(null, "La Meta Que Estas Queriendo Calcular Ya Existe y No Puede Ser Modificada,\n Esta Se Cargará A Continuación", "Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));	
					refrestabla("exec sp_consulta_de_metas_mensuales_por_establecimiento '"+estab+"',"+anio+","+mes);
					btnGenerar.setEnabled(false);
					btnGuardar.setEnabled(false);
					cmbAnio.setEnabled(false);
					cmbMes.setEnabled(false);
					cmbEstablecimiento.setEnabled(false); 
					cmbGrupo.setEnabled(false);
					return;
			   }else{
							    refrestabla("exec sp_sugerido_de_metas_por_mes_año_y_establecimiento '"+estab+"',"+anio+","+mes);
							    btnGenerar.setEnabled(false);
								btnGuardar.setEnabled(true);
								cmbAnio.setEnabled(false);
								cmbMes.setEnabled(false);
								cmbEstablecimiento.setEnabled(false);
								cmbGrupo.setEnabled(false);
								return;
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
				cmbMes.setEnabled(true);
				cmbEstablecimiento.setEnabled(true);
				cmbGrupo.setEnabled(true);	
				cmbGrupo.setSelectedIndex(0);
				btnGenerar.setEnabled(true);
	            cmbEstablecimiento.showPopup();
			   }  
	};
		
	ActionListener opGuargar = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReCalcular();
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
						
		  KeyListener op_validanumero_en_celda = new KeyListener() {
				public void keyTyped(KeyEvent e) {}
				public void keyReleased(KeyEvent e) {
					fila=tabla.getSelectedRow();
					columna=tabla.getSelectedColumn();
					ReCalcular();
					switch (columna) {
				case 3:
					if(Objetotabla.validacelda(tabla,"decimal", fila, columna)){
						  RecorridoFoco(fila,"x"); 
						  ReCalcular(); 
					}
					break;
				case 5:
					if(Objetotabla.validacelda(tabla,"decimal", fila, columna)){
						  RecorridoFoco(fila,"x"); 
						  ReCalcular(); 
					}
					break;
				case 7:
					if(Objetotabla.validacelda(tabla,"decimal", fila, columna)){
						  RecorridoFoco(fila,"x"); 
						  ReCalcular(); 
					}
					break;
				}
				}
				public void keyPressed(KeyEvent e) {}
			};
		
			private void agregar_alclick(final JTable tbl) {
		        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
			        public void mouseClicked(MouseEvent e) {
			        	if(e.getClickCount() == 1){
			        		columna=tabla.getSelectedColumn();
			        		if(columna==3||columna==5||columna==7){
			        		RecorridoFoco(tbl.getSelectedRow()-1,"x");
			        		}else{
			        			return;

			        		}
			        	}
			        }
		        });
		    }
			
			public void RecorridoFoco(int filap,String parametrosacarfoco){
					if(Objetotabla.RecorridoFocotabla(tabla, filap, columna, parametrosacarfoco).equals("si")){
						Objetotabla.RecorridoFocotabla(tabla, -1, columna, parametrosacarfoco);
					};
				}
			
public static void main(String []yo)
{
try{
	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	new Cat_Alimentacion_De_Metas_Mensuales_Por_Establecimiento().setVisible(true);
	}catch(Exception e){	
					}
				  }
		  }
