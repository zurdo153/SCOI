package Cat_Principal;

import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;    

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.*;

import Cat_Compras.Cat_Alimentacion_De_Precios_De_Competencia;
import Cat_Compras.Cat_Analisis_De_Precios_De_Competencia;
import Cat_Compras.Cat_Cotizaciones_De_Un_Producto_En_Proveedores;
import Cat_Compras.Cat_Reporte_De_Ventas;
import Cat_Evaluaciones.Cat_Captura_Del_Cuadrante_Personal;
import Cat_Lista_de_Raya.Cat_Aviso_Vencimiento_De_Contrato;
import Cat_Reportes.Cat_Reportes_De_Apartados;
import Cat_Reportes.Cat_Reportes_De_Vouchers;
import Conexiones_SQL.Connexion;
import Obj_Administracion_del_Sistema.Obj_MD5;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Principal.*;
@SuppressWarnings("serial")
public class Init_Menu_Bar extends Init_Login{
	public ArrayList<WP_Relation> relacion = new ArrayList<WP_Relation>();
    	JMenuBar Barra = new JMenuBar();
	
	public Init_Menu_Bar(){
		this.setTitle("SCOI [Sistema de Control Operativo Izagar] Version 1.187");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/IconoSCOI.png"));
		btnAceptar.addActionListener(opLogin);
		btnSalir.addActionListener(opSalir);
    	int ancho = Toolkit.getDefaultToolkit().getScreenSize().width;
		int alto = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setSize(ancho,alto);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	 }	
	
		
	@SuppressWarnings("rawtypes")
	public JMenuBar miMenuTop(){
		Vector MenuVector = new Obj_Menus().getMenusNivel (Integer.parseInt(txtFolio.getText()));
		ArrayList<WP_Menu> lsMenus = new ArrayList<WP_Menu>();
		for(int i=0; i<MenuVector.size(); i++){
			String[] tmpSTR = String.valueOf(MenuVector.get(i)).split(",");
			
			lsMenus.add(new WP_Menu(tmpSTR[0], tmpSTR[1], tmpSTR[2], tmpSTR[3], tmpSTR[4]));
		}
		
		Vector SubMenuVector = new Obj_Menus().getSubmenuNivel (Integer.parseInt(txtFolio.getText()));
		ArrayList<WP_Submenu> lsSubMenus = new ArrayList<WP_Submenu>();
		for(int i=0; i<SubMenuVector.size(); i++){
			String[] tmpSTR = String.valueOf(SubMenuVector.get(i)).split(",");
			lsSubMenus.add(new WP_Submenu(tmpSTR[0], tmpSTR[1], tmpSTR[2]));
		}
				
		for(WP_Menu me: lsMenus)
			relacion.add(new WP_Relation(me, lsMenus, lsSubMenus));
		for(WP_Menu tmp : lsMenus){
			for(WP_Relation tmps : relacion){
				if(tmp.Nivel == 1 && tmps.Id.getActionCommand().equalsIgnoreCase(tmp.Name)){
					if(!tmp.Name.equalsIgnoreCase("Ayuda"))
						Barra.add(tmps.Id);
				}
			}
		}
		
		for(WP_Menu tmp : lsMenus){
			for(WP_Relation tmps : relacion){
				if(tmp.Nivel == 1 && tmps.Id.getActionCommand().equalsIgnoreCase(tmp.Name)){
					if(tmp.Name.equalsIgnoreCase("Ayuda"))
						Barra.add(tmps.Id);
				}
			}
		}
		return Barra;
	}
	
	public class WP_Menu{
		public int Nivel;
		public int Folio;
		public String Name;
		public int Dependiente_id;
		public String Name_Dependiente;
		public WP_Menu(String nivel, String folio, String name, String dependiente_id, String name_dependiente){
			this.Nivel = Integer.valueOf(nivel);
			this.Folio = Integer.valueOf(folio);
			this.Name = name;
			this.Dependiente_id = Integer.valueOf(dependiente_id);
			this.Name_Dependiente = name_dependiente;
		}
	}
	
	public class WP_Submenu{
		public int Folio;
		public String Nombre;
		public int Menu_Id;
		public WP_Submenu(String folio, String nombre, String menu_id){
			this.Folio = Integer.valueOf(folio);
			this.Nombre = nombre;
			this.Menu_Id = Integer.valueOf(menu_id);
		}
	}
	
	public class WP_Relation{
		public JMenu Id;
		public WP_Relation(WP_Menu id, ArrayList<WP_Menu> dep, ArrayList<WP_Submenu> depSub){
			this.Id = new JMenu(id.Name);
			for(WP_Menu me: dep){
				if(id.Folio == me.Dependiente_id){
					JMenu tmp = new JMenu(me.Name);
					for(WP_Menu me1: dep){
						if(me1.Name_Dependiente.equalsIgnoreCase(tmp.getActionCommand())){
							JMenu tmp1 = new JMenu(me1.Name);
							for(WP_Menu me2: dep){
								if(me2.Name_Dependiente.equalsIgnoreCase(tmp1.getActionCommand())){
									JMenu tmp2 = new JMenu(me2.Name);
									for(WP_Menu me3: dep){
										if(me3.Name_Dependiente.equalsIgnoreCase(tmp2.getActionCommand())){
											JMenu tmp3 = new JMenu(me3.Name);
											for(WP_Menu me4: dep){
												if(me4.Name_Dependiente.equalsIgnoreCase(tmp3.getActionCommand())){
													JMenu tmp4 = new JMenu(me4.Name);
													for(WP_Menu me5: dep){
														if(me5.Name_Dependiente.equalsIgnoreCase(tmp4.getActionCommand())){
															JMenu tmp5 = new JMenu(me5.Name);
															for(WP_Submenu su: depSub){
																if(me5.Folio == su.Menu_Id){
																	JMenuItem subtmp = new JMenuItem(su.Nombre);
																	subtmp.addActionListener(Opciones);
																	tmp5.add(subtmp);
																}
															}
															tmp4.add(tmp5);
														}
													}
													for(WP_Submenu su: depSub){
														if(me4.Folio == su.Menu_Id){
															JMenuItem subtmp = new JMenuItem(su.Nombre);
															subtmp.addActionListener(Opciones);
															tmp4.add(subtmp);
														}
													}
													tmp3.add(tmp4);
												}
											}
											for(WP_Submenu su: depSub){
												if(me3.Folio == su.Menu_Id){
													JMenuItem subtmp = new JMenuItem(su.Nombre);
													subtmp.addActionListener(Opciones);
													tmp3.add(subtmp);
												}
											}
											tmp2.add(tmp3);
										}
									}
									for(WP_Submenu su: depSub){
										if(me2.Folio == su.Menu_Id){
											JMenuItem subtmp = new JMenuItem(su.Nombre);
											subtmp.addActionListener(Opciones);
											tmp2.add(subtmp);
										}
									}
									tmp1.add(tmp2);
								}
							}
							for(WP_Submenu su: depSub){
								if(me1.Folio == su.Menu_Id){
									JMenuItem subtmp = new JMenuItem(su.Nombre);
									subtmp.addActionListener(Opciones);
									tmp1.add(subtmp);
								}
							}
							tmp.add(tmp1);
						}
					}
					for(WP_Submenu su: depSub){
						if(me.Folio == su.Menu_Id){
							JMenuItem subtmp = new JMenuItem(su.Nombre);
							subtmp.addActionListener(Opciones);
							tmp.add(subtmp);
						}
					}
					Id.add(tmp);
				}
			}
			for(WP_Submenu su: depSub){
				if(id.Folio == su.Menu_Id){
					JMenuItem tmp = new JMenuItem(su.Nombre);
						tmp.addActionListener(Opciones);
					Id.add(tmp);
				}
			}
		}
	}
	
    ActionListener opLogin = new ActionListener(){
		@SuppressWarnings({ "deprecation", "static-access" })
		public void actionPerformed(ActionEvent arg0) {
			if(txtContrasena.getText().length() != 0){
				 Obj_MD5 algoritmo = new Obj_MD5();
			 	 Obj_Usuario user = new Obj_Usuario().buscar(Integer.parseInt(txtFolio.getText()));
				if(!algoritmo.cryptMD5(txtContrasena.getText(), "izagar").trim().equals(user.getContrasena().trim())){
					JOptionPane.showMessageDialog(null, "La Contrase�a No Es V�lida...","Aviso",JOptionPane.WARNING_MESSAGE , new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
					txtContrasena.setText("");
					txtContrasena.requestFocus(true);
					return;
				}else{
					if(txtContrasena.getText().toString().equals("1234567890")){
						JOptionPane.showMessageDialog(null, "La Contrase�a Es la de Default Necesita Cambiarla Para Poder Accesar","Aviso",JOptionPane.WARNING_MESSAGE , new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
						btnCambiarContrasena.doClick();				
					}else{

					btnCambiarContrasena.setVisible(true); 
					cargar_usuariotrue();
					setJMenuBar(miMenuTop());
					subMenusbotones();
					user.Session();
					txtContrasena.setEnabled(false);
					
					///cargar foto del empleado///
					fotolb.setVisible(true);
					int folio_empleado =Integer.valueOf(txtFolio.getText());
	        		new Obj_Usuario().BuscarUsuario(folio_empleado);
	    	 		ImageIcon tmpIconAux = new ImageIcon(System.getProperty("user.dir")+"/tmp/tmp_usuario/usuariotmp.jpg");
	    	 		fotolb.setIcon(new ImageIcon(tmpIconAux.getImage().getScaledInstance(140, 110, Image.SCALE_DEFAULT)));
	    	 		
	    	 		
					if(Integer.valueOf(buscarRegistro_Contrato()[0])>0){
						if(Integer.valueOf(buscarRegistro_Contrato()[1])>0){
							new Cat_Aviso_Vencimiento_De_Contrato().setVisible(true);
						}
					}
					}
				}
			}else{
				JOptionPane.showMessageDialog(null, "La Contrase�a Est� Vac�a...","Aviso",JOptionPane.WARNING_MESSAGE, new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				txtContrasena.requestFocus(true);
				return;
			}
		}
	};
	
	private String[] buscarRegistro_Contrato(){
		Statement s;
		ResultSet rs;
		
		String [] fila = new String[2];
		try {
			Connexion con = new Connexion();
			s = con.conexion().createStatement();
			
			String query = ("exec sp_select_mostrar_contratos_proximos_a_terminar "+new Obj_Usuario().LeerSession().getFolio());
			rs = s.executeQuery(query);
			
			while (rs.next())
			{ 
			   fila[0] = rs.getString(1).trim();
			   fila[1] = rs.getString(2).trim();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en Cat_Establecimientos en la funcion refrestabla  SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
		return fila;
	}

	ActionListener Opciones = new ActionListener(){
		@SuppressWarnings("rawtypes")
		public void actionPerformed(ActionEvent e){
			if(! new Componentes().classExiste(e.getActionCommand()).equalsIgnoreCase("")){
				try {
					if(e.getActionCommand().equalsIgnoreCase("Captura Del Cuadrante Personal")){
						Obj_Usuario usuario = new Obj_Usuario().LeerSession();
						Obj_Evaluaciones.Obj_Captura_Del_Cuadrante_Personal datos_cuadrante = new Obj_Evaluaciones.Obj_Captura_Del_Cuadrante_Personal().buscarEmpleado( new Componentes().getTextProcesa(usuario.getNombre_completo()));
						if(!datos_cuadrante.getEquipo_trabajo().equals(""))
							new Cat_Captura_Del_Cuadrante_Personal(new Componentes().getTextProcesa(usuario.getNombre_completo())).setVisible(true);
						else
							JOptionPane.showMessageDialog(null, "El usuario no tiene cuadrante", "Aviso!", JOptionPane.OK_CANCEL_OPTION);
					}else{if(e.getActionCommand().equalsIgnoreCase("Cotizaciones De Un Producto En Proveedores")){
					        	new Cat_Cotizaciones_De_Un_Producto_En_Proveedores("").setVisible(true);
					}else{if(e.getActionCommand().equalsIgnoreCase("Alimentacion De Precios De Competencia")){
			        	new Cat_Alimentacion_De_Precios_De_Competencia("").setVisible(true);
		          	}else{if(e.getActionCommand().equalsIgnoreCase("Reporte De Ventas")){
					    new Cat_Reporte_De_Ventas("","Todos").setVisible(true);
					}else{if(e.getActionCommand().equalsIgnoreCase("Analisis De Precios De Competencia")){
					    new Cat_Analisis_De_Precios_De_Competencia("","Todos").setVisible(true);
					}else{if(e.getActionCommand().equalsIgnoreCase("Reportes De Vouchers")){
					    new Cat_Reportes_De_Vouchers("").setVisible(true);
					}else{if(e.getActionCommand().equalsIgnoreCase("Reportes De Apartados")){
					    new Cat_Reportes_De_Apartados("").setVisible(true);
					}else{
						Class instance = Class.forName(new Componentes().classExiste(e.getActionCommand()));
						Object instanceObject = instance.newInstance();
						((Window) instanceObject).setVisible(true);
					}	
					}
					}
					}
					}
					}
					}
					
				} catch (ClassNotFoundException e1) {
					System.err.println(e1.getMessage());
					//e1.printStackTrace();
				} catch (InstantiationException e1) {
					System.err.println(e1.getMessage());
					//e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					System.err.println(e1.getMessage());
					//e1.printStackTrace();
				}
			}
		}
	};
	
	
	ActionListener opSalir = new ActionListener(){
		public void actionPerformed(ActionEvent e){
						
			txtFolio.setText("");
			txtUsuario.setText("");
			txtContrasena.setText("");
		    txtFolio.setEditable(true);
			txtContrasena.setEditable(true);
			txtFolio.requestFocus();
			btnAceptar.setVisible(true);
			btnAceptar.setEnabled(false);
			btnBuscar.setEnabled(true);
            btnBanco.setEnabled(false);
			btnInasistencia.setEnabled(false);
			btnCaja.setEnabled(false);
			btnFsRH.setEnabled(false);
			btnCortes_Cajeros.setEnabled(false);
			btnRetirosCajeras.setEnabled(false);
			btnPrestamo.setEnabled(false);
			btnPlaneacionyRevision.setEnabled(false);
			btnListaRaya.setEnabled(false);
			btnListaFirma.setEnabled(false);
			btnColaborador.setEnabled(false);
			btnChecador.setEnabled(false);
			btnCambiarContrasena.setVisible(false);
			fotolb.setVisible(false);
			deshabilitarCambiarContrasena ();
			cargar_usuariotrue();
			dispose();
			new Init_Menu_Bar().setVisible(true);
		}
	};
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//			UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
//			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
//			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel"); 
//			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Init_Menu_Bar().setVisible(true);
		}catch(Exception e){}
	}
}