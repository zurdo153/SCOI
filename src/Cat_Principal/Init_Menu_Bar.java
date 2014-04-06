package Cat_Principal;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.*;

import Cat_Evaluaciones.Cat_Captura_Del_Cuadrante_Personal;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Principal.*;

@SuppressWarnings("serial")
public class Init_Menu_Bar extends JFrame{
	public Container cont = getContentPane();
	public JLayeredPane panel = new JLayeredPane();
	public ArrayList<WP_Relation> relacion = new ArrayList<WP_Relation>();
	
	public Init_Menu_Bar(){
		this.setTitle("SCOI [Sistema de Control Operativo Izagar] V:2.0.0");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/layers_1_icon&16.png"));
		
		this.setJMenuBar(miMenuTop());
		
		int ancho = Toolkit.getDefaultToolkit().getScreenSize().width;
		int alto = Toolkit.getDefaultToolkit().getScreenSize().height;
		
		this.setSize(ancho,alto);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}	
	
	@SuppressWarnings("rawtypes")
	public JMenuBar miMenuTop(){
		JMenuBar Barra = new JMenuBar();
		
		Vector MenuVector = new Obj_Menus().getMenusNivel();
		ArrayList<WP_Menu> lsMenus = new ArrayList<WP_Menu>();
		for(int i=0; i<MenuVector.size(); i++){
			String[] tmpSTR = String.valueOf(MenuVector.get(i)).split(",");
			lsMenus.add(new WP_Menu(tmpSTR[0], tmpSTR[1], tmpSTR[2], tmpSTR[3], tmpSTR[4]));
		}
		
		Vector SubMenuVector = new Obj_Menus().getSubmenuNivel();
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
					}else{
						Class instance = Class.forName(new Componentes().classExiste(e.getActionCommand()));
						Object instanceObject = instance.newInstance();
						((Window) instanceObject).setVisible(true);
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
	
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Init_Menu_Bar().setVisible(true);
		}catch(Exception e){}
	}
}