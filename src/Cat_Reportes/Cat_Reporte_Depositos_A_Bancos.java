package Cat_Reportes;

import java.util.HashMap;

import javax.swing.JFrame;

import Conexiones_SQL.Connexion;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

@SuppressWarnings("serial")
public class Cat_Reporte_Depositos_A_Bancos extends JFrame {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Cat_Reporte_Depositos_A_Bancos() {
		try {
			JasperReport report = JasperCompileManager.compileReport(System.getProperty("user.dir")+"\\src\\Obj_Reportes\\Obj_Reporte_Depositos_A_Bancos.jrxml");
			JasperPrint print = JasperFillManager.fillReport(report, new HashMap(), new Connexion().conexion());
			JasperViewer.viewReport(print, true);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
