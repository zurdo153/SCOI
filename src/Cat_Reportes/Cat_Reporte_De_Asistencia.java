package Cat_Reportes;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

import Conexiones_SQL.Connexion;
import Obj_Lista_de_Raya.Obj_Departamento;
import Obj_Lista_de_Raya.Obj_Establecimiento;

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

	JButton btn_generar_Completo = new JButton  ("Reporte de Asistencia Completo");
	JButton btn_generar_Permisos = new JButton  ("Reporte de Permisos a Empleados");
	
	JLabel JLBlinicio= new JLabel(new ImageIcon("Imagen/iniciar-icono-4628-16.png") );
	JLabel JLBfin= new JLabel(new ImageIcon("Imagen/acabado-icono-7912-16.png") );
	JLabel JLBestablecimiento= new JLabel(new ImageIcon("Imagen/folder-home-home-icone-5663-16.png") );
	JLabel JLBdepartamento= new JLabel(new ImageIcon("Imagen/departamento-icono-5365-16.png") );
	
	public Cat_Reporte_De_Asistencia(){
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/asistencia-comunitaria-icono-9465-32.png"));
		this.setTitle("Reportes de Asistencia");
		this.panel.setBorder(BorderFactory.createTitledBorder("Reportes de Asistencia"));
		this.panel.add(new JLabel("Fecha Inicio:")).setBounds(15,25,100,20);
		this.panel.add(JLBlinicio).setBounds(75,25,20,20);
		this.panel.add(c_inicio).setBounds(95,25,100,20);
		this.panel.add(new JLabel("Fecha Final:")).setBounds(15,55,100,20);
		this.panel.add(JLBfin).setBounds(75,55,20,20);
		this.panel.add(c_final).setBounds(95,55,100,20);
	    this.panel.add(new JLabel("Establecimiento:")).setBounds(220,25,150,20);
	    this.panel.add(JLBestablecimiento).setBounds(300,25,20,20);
		this.panel.add(cmbEstablecimiento).setBounds(320,25,170,20);
		this.panel.add(new JLabel("Departamento:")).setBounds(225,55,150,20);
		this.panel.add(JLBdepartamento).setBounds(300,55,20,20);
		this.panel.add(cmbDepartamento).setBounds(320,55,170,20);
		this.btn_generar_Completo.addActionListener(op_generar);
		this.btn_generar_Permisos.addActionListener(op_generar_permisos);
		this.panel.add(btn_generar_Completo).setBounds(140,130,210,20);
		this.panel.add(btn_generar_Permisos).setBounds(140,160,210,20);
		this.cont.add(panel);
		this.setSize(510,270);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
	}
	
	
	ActionListener op_generar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(validar_fechas().equals("")){
				String fecha_inicio = new SimpleDateFormat("dd/MM/yyyy").format(c_inicio.getDate())+" 00:00:00";
				String fecha_final = new SimpleDateFormat("dd/MM/yyyy").format(c_final.getDate())+" 23:59:59";
				String Establecimiento = cmbEstablecimiento.getSelectedItem().toString();
				String Departamento = cmbDepartamento.getSelectedItem().toString();
				String folios_empleados = "Selecciona un Empleado";

				if(c_inicio.getDate().before(c_final.getDate())){
					Reporte_de_Asistencia_establecimiento(fecha_inicio,fecha_final,Establecimiento,Departamento,folios_empleados);

					
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
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void Reporte_de_Asistencia_establecimiento(String fecha_inicio, String fecha_final,String Establecimiento,String Departamento,String folios_empleados){
		String query = "exec sp_Reporte_General_de_Asistencia_Por_Establecimiento '"+fecha_inicio+"','"+fecha_final+"','"+Establecimiento+"','"+Departamento+"','"+folios_empleados+"'";
		Statement stmt = null;
		try {
			
			stmt =  new Connexion().conexion().createStatement();
		    ResultSet rs = stmt.executeQuery(query);
		    
			JasperReport report = JasperCompileManager.compileReport(System.getProperty("user.dir")+"\\src\\Obj_Reportes\\Obj_General_de_Asistencia_Por_Establecimiento.jrxml");
			JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
			JasperPrint print = JasperFillManager.fillReport(report, new HashMap(), resultSetDataSource);
			JasperViewer.viewReport(print, false);
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
			JOptionPane.showMessageDialog(null, "Error En Cat_Reporte_General_de_Asistencia_Por_Establecimiento ", "Error !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
	}
	}
	
	
	ActionListener op_generar_permisos = new ActionListener() {
		@SuppressWarnings("unchecked")
		public void actionPerformed(ActionEvent e) {
			if(validar_fechas().equals("")){
				String fecha_inicio = new SimpleDateFormat("dd/MM/yyyy").format(c_inicio.getDate())+" 00:00:00";
				String fecha_final = new SimpleDateFormat("dd/MM/yyyy").format(c_final.getDate())+" 23:59:59";
				String Establecimiento = cmbEstablecimiento.getSelectedItem().toString();
				
				if(c_inicio.getDate().before(c_final.getDate())){
						
						   String query = "exec sp_Reporte_De_Permisos_A_Empleados '"+fecha_inicio+"','"+fecha_final+"','"+Establecimiento+"';";
							
							Statement stmt = null;
							try {
								stmt =  new Connexion().conexion().createStatement();
							    ResultSet rs = stmt.executeQuery(query);
							    
								JasperReport report = JasperCompileManager.compileReport(System.getProperty("user.dir")+"\\src\\Obj_Reportes\\Obj_Permisos_A_Empleados.jrxml");
								JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
								@SuppressWarnings("rawtypes")
								JasperPrint print = JasperFillManager.fillReport(report, new HashMap(), resultSetDataSource);
								JasperViewer.viewReport(print, false);
							} catch (Exception e1) {
								System.out.println(e1.getMessage());
						}
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
			new Cat_Reporte_De_Asistencia().setVisible(true);
		}catch(Exception e){	}
	}

}
