package Cat_Reportes;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Conexiones_SQL.Connexion;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

@SuppressWarnings("serial")
public class Cat_Reporte_De_Corte_De_Caja extends JFrame {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Cat_Reporte_De_Corte_De_Caja(String folio_corte) {

		
//		try {
//			
//			Statement stmt_corte_caja = null;
//		
////			corte de caja
//			stmt_corte_caja =  new Connexion().conexion().createStatement();
//		    ResultSet rs_corte_caja = stmt_corte_caja.executeQuery(query_corte_caja);
////		    
//			JasperReport report_corte_caja = JasperCompileManager.compileReport(System.getProperty("user.dir")+"\\src\\Obj_Reportes\\Obj_Reporte_De_Corte_De_Caja_2.jrxml");
//			JRResultSetDataSource resultSetDataSource_corte_caja = new JRResultSetDataSource(rs_corte_caja);
//			JasperPrint print_corte_caja = JasperFillManager.fillReport(report_corte_caja, new HashMap(), new Connexion().conexion());
//			JasperViewer.viewReport(print_corte_caja, false);
//			
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
		
		String query_corte_caja = "exec sp_select_reporte_corte_de_caja_2 '"+folio_corte+"';";
		Statement stmt = null;
		try {
			
			stmt =  new Connexion().conexion().createStatement();
		    ResultSet rs = stmt.executeQuery(query_corte_caja);
			JasperReport report = JasperCompileManager.compileReport(System.getProperty("user.dir")+"\\src\\Obj_Reportes\\Obj_Reporte_De_Corte_De_Caja_2.jrxml");
			JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
			JasperPrint print = JasperFillManager.fillReport(report, new HashMap(), resultSetDataSource);
			JasperViewer.viewReport(print, false);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(null, "Error En Cat_Reporte_De_Corte_De_Caja ", "Error !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
		}
		
	
	
		
		
		String query_corte_de_caja_por_dia = "exec sp_Reporte_de_Corte_por_Dia_por_Asignacion '"+folio_corte+"';";
		Statement stmt1 = null;
		try {
			
			stmt1 =  new Connexion().conexion().createStatement();
		    ResultSet rs = stmt1.executeQuery(query_corte_de_caja_por_dia);
			JasperReport report = JasperCompileManager.compileReport(System.getProperty("user.dir")+"\\src\\Obj_Reportes\\Obj_Reporte_De_Corte_De_Caja_Sub_Por_Dia.jrxml");
			JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
			JasperPrint print = JasperFillManager.fillReport(report, new HashMap(), resultSetDataSource);
			JasperViewer.viewReport(print, false);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(null, "Error En Cat_Reporte_De_Corte_De_Caja ", "Error !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
		}
		
	}
}