package Cat_Reportes;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.UIManager;


import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Generacion_Reportes;
import Obj_Lista_de_Raya.Obj_Departamento;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.JCButton;

import com.toedter.calendar.JDateChooser;


@SuppressWarnings("serial")
public class Cat_Reporte_De_Asistencia extends JFrame {
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JDateChooser c_inicio = new JDateChooser();
	JDateChooser c_final = new JDateChooser();
	
	String establecimiento[] = new Obj_Establecimiento().Combo_Establecimiento();
	String departamento[] = new Obj_Departamento().Combo_Departamento();
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbEstablecimiento = new JComboBox(establecimiento);
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbDepartamento = new JComboBox(departamento);
	
	JCButton btn_generar_faltas          = new JCButton  ("Reporte de Faltas","inasistencia16x16.png","Azul");
	JCButton btn_generar_consideraciones = new JCButton  ("Reporte de Consideraciones","check-vcard-icone-9025-16.png","Azul");
	JCButton btn_generar_Permisos        = new JCButton  ("Reporte de Permisos a Empleados","apoyo-y-asistencia-icono-6525-16.png","Azul");
	JCButton btn_generar_completo        = new JCButton  ("Reporte de Asistencia Completo","proceso-para-los-usuarios-icono-5903-16.png","Azul");
	
	JLabel JLBlinicio= new JLabel(new ImageIcon("Imagen/iniciar-icono-4628-16.png") );
	JLabel JLBfin= new JLabel(new ImageIcon("Imagen/acabado-icono-7912-16.png") );
	JLabel JLBestablecimiento= new JLabel(new ImageIcon("Imagen/folder-home-home-icone-5663-16.png") );
	JLabel JLBdepartamento= new JLabel(new ImageIcon("Imagen/departamento-icono-5365-16.png") );
	
	public Cat_Reporte_De_Asistencia(){
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/asistencia-comunitaria-icono-9465-32.png"));
		this.setTitle("Reportes de Asistencia");
		this.panel.setBorder(BorderFactory.createTitledBorder("Reportes de Asistencia"));
		int x=15, y=25, width=100, height=20;
		this.panel.add(new JLabel("Fecha Inicio:")).setBounds    (x    ,y,width,height);
		this.panel.add(JLBlinicio).setBounds(75,25,height,height);
		this.panel.add(c_inicio).setBounds(95,25,width,height);
		this.panel.add(new JLabel("Fecha Final:")).setBounds(15,55,width,height);
		this.panel.add(JLBfin).setBounds(75,55,20,height);
		this.panel.add(c_final).setBounds(95,55,width,height);
	    this.panel.add(new JLabel("Establecimiento:")).setBounds(220,25,150,height);
	    this.panel.add(JLBestablecimiento).setBounds(300,25,height,height);
		this.panel.add(cmbEstablecimiento).setBounds(320,25,170,height);
		this.panel.add(new JLabel("Departamento:")).setBounds(225,55,150,height);
		this.panel.add(JLBdepartamento).setBounds(300,55,height,height);
		this.panel.add(cmbDepartamento).setBounds(320,55,170,height);
		x=110;width=280;height=35;
		this.panel.add(btn_generar_faltas).setBounds             (x     ,100    ,width,height);
		this.panel.add(btn_generar_consideraciones).setBounds    (x     ,145    ,width,height);
		this.panel.add(btn_generar_Permisos).setBounds           (x     ,190    ,width,height);
		this.panel.add(btn_generar_completo).setBounds           (x     ,235    ,width,height);
				
		this.cont.add(panel);
		this.setSize(510,330);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		 cargar_fechas();
				 
		this.btn_generar_completo.addActionListener(op_generar);
		this.btn_generar_Permisos.addActionListener(op_generar_permisos);
		this.btn_generar_consideraciones.addActionListener(op_generar);
		btn_generar_faltas.addActionListener(op_generar_faltas); 
		
	}
	
	public void cargar_fechas(){
		Date date1 = null;
				  try {
					date1 = new SimpleDateFormat("dd/MM/yyyy").parse(new BuscarSQL().fecha(7));
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		c_inicio.setDate(date1);
					
	    Date date2 = null;
					  try {
						date2 = new SimpleDateFormat("dd/MM/yyyy").parse(new BuscarSQL().fecha(0));
					} catch (ParseException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					}
		c_final.setDate(date2);
	};
	
	
	String basedatos="2.26";
	String vista_previa_reporte="no";
	int vista_previa_de_ventana=0;
	String comando="";
	String reporte = "";
	
	ActionListener op_generar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(validar_fechas().equals("")){
				String fecha_inicio = new SimpleDateFormat("dd/MM/yyyy").format(c_inicio.getDate())+" 00:00:00";
				String fecha_final = new SimpleDateFormat("dd/MM/yyyy").format(c_final.getDate())+" 23:59:10";
				String Establecimiento = cmbEstablecimiento.getSelectedItem().toString();
				String Departamento = cmbDepartamento.getSelectedItem().toString();
				String folios_empleados = "Selecciona un Empleado";

				if(c_inicio.getDate().before(c_final.getDate())){
					Reporte_de_Asistencia_consideraciones(fecha_inicio,fecha_final,Establecimiento,Departamento,folios_empleados,e.getActionCommand().equals("Reporte de Consideraciones")?"SI":"NO");
				}else{
					  JOptionPane.showMessageDialog(null, "El Rango De Fechas Esta Invertido","Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
                      return;
				}
			}else{
				  JOptionPane.showMessageDialog(null, "Los Siguientes Campos Estan Vacios y Se Necesitan Para La Consulta:\n "+validar_fechas(),"Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
                  return;
			}
		}
	};
	
	ActionListener op_generar_faltas = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(validar_fechas().equals("")){
				String fecha_inicio = new SimpleDateFormat("dd/MM/yyyy").format(c_inicio.getDate())+" 00:00:00";
				String fecha_final = new SimpleDateFormat("dd/MM/yyyy").format(c_final.getDate())+" 23:59:59";
				String Establecimiento = cmbEstablecimiento.getSelectedItem().toString();
				String Departamento = cmbDepartamento.getSelectedItem().toString();
				String folios_empleados = "Selecciona un Empleado";

				if(c_inicio.getDate().before(c_final.getDate())){
					Reporte_de_faltas(fecha_inicio,fecha_final,Establecimiento,Departamento,folios_empleados);
				}else{
					  JOptionPane.showMessageDialog(null, "El Rango De Fechas Esta Invertido","Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
                      return;
				}
			}else{
				  JOptionPane.showMessageDialog(null, "Los Siguientes Campos Estan Vacios y Se Necesitan Para La Consulta:\n "+validar_fechas(),"Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
                  return;
			}
		}
	};
	
	
	ActionListener op_generar_permisos = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(validar_fechas().equals("")){
				String fecha_inicio = new SimpleDateFormat("dd/MM/yyyy").format(c_inicio.getDate())+" 00:00:00";
				String fecha_final = new SimpleDateFormat("dd/MM/yyyy").format(c_final.getDate())+" 23:59:59";
				String Establecimiento = cmbEstablecimiento.getSelectedItem().toString();
				String folios_empleados= "";
				if(c_inicio.getDate().before(c_final.getDate())){
					Reporte_de_Permisos(fecha_inicio, fecha_final, Establecimiento, folios_empleados);
				}else{
					  JOptionPane.showMessageDialog(null, "El Rango De Fechas Esta Invertido","Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
                      return;
				}
			}else{
				  JOptionPane.showMessageDialog(null, "Los Siguientes Campos Estan Vacios y Se Necesitan Para La Consulta:\n "+validar_fechas(),"Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
                  return;
			}
		}
	};
	
	
	public void Reporte_de_faltas(String fecha_inicio, String fecha_final,String Establecimiento,String Departamento,String folios_empleados){
		 reporte = "Obj_Reporte_De_Asistencia_Faltas.jrxml";
		 comando = "exec sp_Reporte_De_Faltas '"+fecha_inicio+"','"+fecha_final+"','"+Establecimiento+"','"+Departamento+"','"+folios_empleados+"'";
		 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
	}
	
	public void Reporte_de_Permisos(String fecha_inicio, String fecha_final,String Establecimiento,String folios_empleados){
		 reporte = "Obj_Reporte_De_Permisos_A_Empleados.jrxml";
		 comando = "exec sp_Reporte_De_Permisos_A_Empleados '"+fecha_inicio+"','"+fecha_final+"','"+Establecimiento+"','Selecciona un Empleado'";
		 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
	}
	
	public void Reporte_de_Asistencia_consideraciones(String fecha_inicio, String fecha_final,String Establecimiento,String Departamento,String folios_empleados,String solo_consideraciones){
		 reporte = "Obj_Reporte_De_Asistencia_General.jrxml";
		 comando = "exec sp_Reporte_De_Asistencia_Por_Establecimiento_Con_Consideraciones '"+fecha_inicio+"','"+fecha_final+"','"+Establecimiento+"','"+Departamento+"','"+folios_empleados+"','"+solo_consideraciones+"'";
		 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
	}
	
	public String validar_fechas(){
		String error = "";
		String fechainicioNull = c_inicio.getDate()+"";
		String fechafinalNull = c_final.getDate()+"";
	    if(fechainicioNull.equals("null"))error+= "Fecha  inicio\n";
		if(fechafinalNull.equals("null"))error+= "Fecha Final\n";
		return error;
	}
	
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Reporte_De_Asistencia().setVisible(true);
		}catch(Exception e){	}
	}

}
