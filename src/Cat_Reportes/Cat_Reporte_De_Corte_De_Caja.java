package Cat_Reportes;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

import javax.swing.JFrame;

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
//			JasperReport report = JasperCompileManager.compileReport(System.getProperty("user.dir")+"\\src\\Obj_Reportes\\Obj_Impresion_De_4_Gafetes.jrxml");
//			@SuppressWarnings({ "rawtypes", "unchecked" })
//			JasperPrint print = JasperFillManager.fillReport(report, new HashMap(), new Connexion().conexion());
//			JasperViewer.viewReport(print, false);
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
		
		
		
		try {
//			String query_corte_caja = "exec sp_select_reporte_corte_de_caja '"+folio_corte+"';";
//			Statement stmt_corte_caja = null;
//		
////			corte de caja
//			stmt_corte_caja =  new Connexion().conexion().createStatement();
//		    ResultSet rs_corte_caja = stmt_corte_caja.executeQuery(query_corte_caja);
//		    
			JasperReport report_corte_caja = JasperCompileManager.compileReport(System.getProperty("user.dir")+"\\src\\Obj_Reportes\\Obj_Reporte_De_Corte_De_Caja.jrxml");
//			JRResultSetDataSource resultSetDataSource_corte_caja = new JRResultSetDataSource(rs_corte_caja);
			JasperPrint print_corte_caja = JasperFillManager.fillReport(report_corte_caja, new HashMap(), new Connexion().conexion());
			JasperViewer.viewReport(print_corte_caja, false);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}