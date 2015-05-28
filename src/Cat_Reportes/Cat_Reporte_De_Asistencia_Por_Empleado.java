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
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

import com.toedter.calendar.JDateChooser;

import Conexiones_SQL.BuscarSQL;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.Componentes;

@SuppressWarnings("serial")
public class Cat_Reporte_De_Asistencia_Por_Empleado extends JDialog{
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	String establecimiento[] = new Obj_Establecimiento().Combo_Establecimiento();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbEstablecimiento = new JComboBox(establecimiento);
	
	JTextField txtFolio = new Componentes().text(new JTextField(), "Folio del Empleado", 10, "String");
	JTextField txtNombreEmpleado = new Componentes().text(new JTextField(), "Nombre del Empleado",100, "String");
	JTextField txtEstablecimiento = new Componentes().text(new JTextField(), "Establecimiento",100, "String");
	JTextField txtDepartamento = new Componentes().text(new JTextField(), "Departamento",100, "String");
	
	JDateChooser c_inicio = new JDateChooser();
	JDateChooser c_final = new JDateChooser();
	
	JLabel JLBlinicio= new JLabel(new ImageIcon("Imagen/iniciar-icono-4628-16.png") );
	JLabel JLBfin= new JLabel(new ImageIcon("Imagen/acabado-icono-7912-16.png") );
	JLabel JLBestablecimiento= new JLabel(new ImageIcon("Imagen/folder-home-home-icone-5663-16.png") );
	JLabel JLBdepartamento= new JLabel(new ImageIcon("Imagen/departamento-icono-5365-16.png") );
	
	JButton btnCompleto = new JButton("Completo",new ImageIcon("imagen/proceso-para-los-usuarios-icono-5903-16.png"));
	JButton btnConsideraciones = new JButton("Consideraciones",new ImageIcon("imagen/check-vcard-icone-9025-16.png"));
	JButton btnPermisos = new JButton("Permisos",new ImageIcon("imagen/apoyo-y-asistencia-icono-6525-16.png"));
	JButton btnfaltas = new JButton("Faltas",new ImageIcon("imagen/inasistencia16x16.png"));
	
	public Cat_Reporte_De_Asistencia_Por_Empleado(String Folio,String Nombre,String Establecimiento,String Departamento){

		
		panel.setBorder(BorderFactory.createTitledBorder("Reporte De Asistencia Por Empleado"));
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/archivo-icono-8809-32.png"));
		this.setTitle("Asistencia Por Empleado En Un Periodo");
		
		panel.add(txtFolio).setBounds(60,25,40,20);
		txtFolio.setEnabled(false);
		txtFolio.setText(Folio);
				
		panel.add(new JLabel("Nombre:")).setBounds(15,25,50,20);
		panel.add(txtNombreEmpleado).setBounds(100,25,280,20);
		txtNombreEmpleado.setEnabled(false);
        txtNombreEmpleado.setText(Nombre);
        
		panel.add(new JLabel("Establecimiento:")).setBounds(15,55,80,20);
		panel.add(JLBestablecimiento).setBounds(90,55,20,20);
		panel.add(txtEstablecimiento).setBounds(110,55,270,20);
		txtEstablecimiento.setEnabled(false);
        txtEstablecimiento.setText(Establecimiento);
        
		panel.add(new JLabel("Departamento:")).setBounds(15,85,80,20);
		panel.add(JLBdepartamento).setBounds(90,85,20,20);
		panel.add(txtDepartamento).setBounds(110,85,270,20);
		txtDepartamento.setEnabled(false);
        txtDepartamento.setText(Departamento);        
        
        
		panel.add(new JLabel("Fecha Inicio:")).setBounds(15,115,100,20);
		panel.add(JLBlinicio).setBounds(80,115,20,20);
		panel.add(c_inicio).setBounds(100,115,90,20);
		panel.add(new JLabel("Fecha Final:")).setBounds(210,115,100,20);
		panel.add(JLBfin).setBounds(270,115,20,20);
		panel.add(c_final).setBounds(290,115,90,20);		

		
		panel.add(btnCompleto).setBounds(40, 150, 150, 20);
		panel.add(btnConsideraciones).setBounds(220, 150, 150, 20);
		panel.add(btnPermisos).setBounds(40, 180, 150, 20);
		panel.add(btnfaltas).setBounds(220, 180, 150, 20);
		
		cont.add(panel);
		cargar_fechas();
		btnCompleto.addActionListener(opGenerar);
		btnConsideraciones.addActionListener(opGenerar);
		btnPermisos.addActionListener(opGenerar_Permisos);
		btnfaltas.addActionListener(opGenerar_faltas);
		
		this.setSize(400, 250);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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
	
	ActionListener opGenerar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
     if(validar_fechas().equals("")){
			String fecha_inicio = new SimpleDateFormat("dd/MM/yyyy").format(c_inicio.getDate())+" 00:00:00";
			String fecha_final = new SimpleDateFormat("dd/MM/yyyy").format(c_final.getDate())+" 23:59:59";
			
			String Establecimiento=txtEstablecimiento.getText()+"";
			String Departamento=txtDepartamento.getText()+"";
			String folios_empleados=txtFolio.getText()+"";
			
			if(c_inicio.getDate().before(c_final.getDate())){
				new Cat_Reporte_De_Asistencia().Reporte_de_Asistencia_consideraciones(fecha_inicio,fecha_final,Establecimiento,Departamento,folios_empleados,e.getActionCommand().equals("Consideraciones")?"SI":"NO");
				
			}else{
				JOptionPane.showMessageDialog(null,"El Rango de Fechas Esta Invertido","Aviso!", JOptionPane.WARNING_MESSAGE);
				return;
			}
	 	}else{
			JOptionPane.showMessageDialog(null,"Los siguientes campos están vacíos: "+validar_fechas(),"Aviso!", JOptionPane.ERROR_MESSAGE);
			return;
		}
		}
	};
	
	ActionListener opGenerar_Permisos = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
     if(validar_fechas().equals("")){
			String fecha_inicio = new SimpleDateFormat("dd/MM/yyyy").format(c_inicio.getDate())+" 00:00:00";
			String fecha_final = new SimpleDateFormat("dd/MM/yyyy").format(c_final.getDate())+" 23:59:59";
			String Establecimiento=txtEstablecimiento.getText()+"";
			String folios_empleados=txtFolio.getText()+"";
			
			if(c_inicio.getDate().before(c_final.getDate())){
				new Cat_Reporte_De_Asistencia().Reporte_de_Permisos(fecha_inicio, fecha_final, Establecimiento, folios_empleados);
				
			}else{
				JOptionPane.showMessageDialog(null,"El Rango de Fechas Esta Invertido","Aviso!", JOptionPane.WARNING_MESSAGE);
				return;
			}
	 	}else{
			JOptionPane.showMessageDialog(null,"Los siguientes campos están vacíos: "+validar_fechas(),"Aviso!", JOptionPane.ERROR_MESSAGE);
			return;
		}
		}
	};
	
	ActionListener opGenerar_faltas = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
     if(validar_fechas().equals("")){
			String fecha_inicio = new SimpleDateFormat("dd/MM/yyyy").format(c_inicio.getDate())+" 00:00:00";
			String fecha_final = new SimpleDateFormat("dd/MM/yyyy").format(c_final.getDate())+" 23:59:59";
			String Departamento=txtDepartamento.getText()+"";
			String Establecimiento=txtEstablecimiento.getText()+"";
			String folios_empleados=txtFolio.getText()+"";
			
			if(c_inicio.getDate().before(c_final.getDate())){
				new Cat_Reporte_De_Asistencia().Reporte_de_faltas(fecha_inicio, fecha_final, Establecimiento,Departamento, folios_empleados);
				
			}else{
				JOptionPane.showMessageDialog(null,"El Rango de Fechas Esta Invertido","Aviso!", JOptionPane.WARNING_MESSAGE);
				return;
			}
	 	}else{
			JOptionPane.showMessageDialog(null,"Los siguientes campos están vacíos: "+validar_fechas(),"Aviso!", JOptionPane.ERROR_MESSAGE);
			return;
		}
		}
	};
	
	
	
	
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
			new Cat_Reporte_De_Asistencia_Por_Empleado("1","MARCO ANTONIO BODART GUZMAN","SISTEMAS","SISTEMAS").setVisible(true);
		}catch(Exception e){	}
	}
}
