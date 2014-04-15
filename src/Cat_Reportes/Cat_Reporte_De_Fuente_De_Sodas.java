package Cat_Reportes;

import java.util.HashMap;

import javax.swing.JFrame;



import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

@SuppressWarnings("serial")
public class Cat_Reporte_De_Fuente_De_Sodas extends JFrame {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Cat_Reporte_De_Fuente_De_Sodas() {
		try {
			JasperReport report = JasperCompileManager.compileReport(System.getProperty("user.dir")+"\\src\\Obj_Reportes\\Obj_Reporte_De_Fuente_De_Sodas.jrxml");
			JasperPrint print = JasperFillManager.fillReport(report, new HashMap(), new Conexiones_SQL.Connexion().conexion());
			JasperViewer.viewReport(print, false);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
