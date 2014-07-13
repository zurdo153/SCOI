package Cat_Reportes;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.border.Border;


import Conexiones_SQL.Connexion;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

@SuppressWarnings("serial")
public class Cat_Reportes_De_Horarios extends JFrame {
	JButton btnHorariosAsignados = new JButton();
	JButton btnDepositos_Bancos_Por_Establecimiento = new JButton();
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	Border blackline, etched, raisedbevel, loweredbevel, empty;

	
	public Cat_Reportes_De_Horarios() {
		
		blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
		panel.setBorder(BorderFactory.createTitledBorder(blackline,"Seleccion Del Reporte Depositos A Bancos"));
		this.setTitle("Reportes Depositos A Bancos");
		
		btnHorariosAsignados.setText(	"<html> <FONT FACE="+"arial"+" SIZE=3 COLOR=BLACk>" +
				"		<p>Impresion de Reporte de</p>" +
				"		<CENTER><p>Horarios Asignados a Empleados</p></CENTER></FONT>" +
				"</html>"); 
		
		btnDepositos_Bancos_Por_Establecimiento.setText(	"<html> <FONT FACE="+"arial"+" SIZE=3 COLOR=BLACk>" +
				"		<p>Impresion de Reporte de</p>" +
				"		<CENTER><p>Horarios Sin Asignar</p></CENTER></FONT>" +
				"</html>"); 
		
		panel.add(btnHorariosAsignados).setBounds(40,40,240,40);
		
		panel.add(btnDepositos_Bancos_Por_Establecimiento).setBounds(40,100,240,40);
		
		btnHorariosAsignados.addActionListener(Reporte_De_Horarios_Asignados);
		btnDepositos_Bancos_Por_Establecimiento.addActionListener(Reporte_De_Horarios_Sin_Asignar);
		cont.add(panel);
		this.setSize(320,200);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		
	}
	ActionListener Reporte_De_Horarios_Asignados = new ActionListener(){
	
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public void actionPerformed(ActionEvent e){
			String query="";
			Statement stmt = null;
			query = "exec sp_Reporte_de_Horarios_Asignados_Completo";
			
				try {
					stmt = new Connexion().conexion().createStatement();
					ResultSet rs = stmt.executeQuery(query);
					
				    JasperReport report = JasperCompileManager.compileReport(System.getProperty("user.dir")+"\\src\\Obj_Reportes\\Obj_Horarios.jrxml");
					JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
					JasperPrint print = JasperFillManager.fillReport(report, new HashMap(), resultSetDataSource);
					JasperViewer.viewReport(print, false);
					
				} catch (Exception e1) {
					System.out.println(e1.getMessage());
					JOptionPane.showMessageDialog(null, "Error en Cat_Reportes_De_Horarios  en la funcion [ ActionListener Reporte_De_Horarios_Asignados ]   SQLException:  "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);

			}
		}
	};
	ActionListener Reporte_De_Horarios_Sin_Asignar = new ActionListener(){

		@SuppressWarnings({ "rawtypes", "unchecked" })
		public void actionPerformed(ActionEvent e){
			String query="";
			Statement stmt = null;
			query = "exec sp_Reporte_de_Horarios_Sin_Asignar";
				try {
					stmt = new Connexion().conexion().createStatement();
					ResultSet rs = stmt.executeQuery(query);
					
				    JasperReport report = JasperCompileManager.compileReport(System.getProperty("user.dir")+"\\src\\Obj_Reportes\\Obj_Horarios.jrxml");
					JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
					JasperPrint print = JasperFillManager.fillReport(report, new HashMap(), resultSetDataSource);
					JasperViewer.viewReport(print, false);
				} catch (Exception e2) {
					System.out.println(e2.getMessage());
					JOptionPane.showMessageDialog(null, "Error en Cat_Reportes_De_Horarios  en la funcion [ ActionListener Reporte_De_Horarios_Sin_Asignar ]   SQLException:  "+e2.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
		}
	};
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Reportes_De_Horarios().setVisible(true);
		}catch(Exception e){	}
	}

}
