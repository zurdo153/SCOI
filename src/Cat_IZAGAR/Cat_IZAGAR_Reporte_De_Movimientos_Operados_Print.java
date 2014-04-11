package Cat_IZAGAR;


import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

import javax.swing.JFrame;





import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

@SuppressWarnings("serial")
public class Cat_IZAGAR_Reporte_De_Movimientos_Operados_Print extends JFrame {

	@SuppressWarnings({ "rawtypes", "unchecked" })
		public Cat_IZAGAR_Reporte_De_Movimientos_Operados_Print(String fecha) {
			String query = "exec sp_Reporte_IZAGAR_de_Movimientos_Operados '"+fecha+"';";
			System.out.println(fecha);
			Statement stmt = null;
			try {
				stmt =  new Conexiones_SQL.Connexion().conexion_IZAGAR().createStatement();
			    ResultSet rs = stmt.executeQuery(query);
			    
				JasperReport report = JasperCompileManager.compileReport(System.getProperty("user.dir")+"\\src\\IZAGAR_Obj_Reportes\\Obj_Reporte_IZAGAR_de_Movimientos_Operados.jrxml");
				JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
				JasperPrint print = JasperFillManager.fillReport(report, new HashMap(), resultSetDataSource);
				JasperViewer.viewReport(print, false);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	
}
