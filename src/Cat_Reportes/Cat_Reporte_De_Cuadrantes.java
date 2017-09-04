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
public class Cat_Reporte_De_Cuadrantes extends JFrame {

	String NombreReporte="";
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
		public Cat_Reporte_De_Cuadrantes(String fecha_inicio, String fecha_final, int operadorEqTrabajo, String cadenaEqTrabajo,
										int operadorEmpleado, String cadenaEmpleados,
										int operadorEstablecimiento, String cadenaEstablecimiento,
										int operadorPuesto, String cadenaPuesto,
										int operadorDepartamento, String cadenaDepartamento,
										int operadorNivelCritico, String cadenaNivelCritico,
										int operadorRespuesta, String cadenaRespuesta,
										int reportePresentado) {
		if(reportePresentado < 2){
			NombreReporte="Obj_Reporte_Dinamico_De_Cuadrantes";
			System.out.println("abrira reportes por establecimiento");
		}else{
//			poner el nombre del reporte cuando sera presentado por equipo de trabajo
			NombreReporte="Obj_Reporte_Dinamico_De_Cuadrantes";
			System.out.println("abrira reportes por equipo de trabajo");
		}
		
		String query = "exec sp_reporte_dinamico_de_cuadrantes '"+fecha_inicio+"','"+fecha_final+"','"+
					operadorEqTrabajo+"','"+cadenaEqTrabajo+"','"+
				operadorEmpleado+"','"+cadenaEmpleados+"','"+
				operadorEstablecimiento+"','"+cadenaEstablecimiento+"','"+
				operadorPuesto+"','"+cadenaPuesto+"','"+
				operadorDepartamento+"','"+cadenaDepartamento+"','"+
				operadorNivelCritico+"','"+cadenaNivelCritico+"','"+
				operadorRespuesta+"','"+cadenaRespuesta+"','"+
				reportePresentado
				+"';";
		
		Statement stmt = null;
		try {
			stmt =  new Connexion().conexion().createStatement();
		    ResultSet rs = stmt.executeQuery(query);
		    
		    System.out.println(query);
			JasperReport report = JasperCompileManager.compileReport(System.getProperty("user.dir")+"\\src\\Obj_Reportes\\"+NombreReporte+".jrxml");
			JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
			JasperPrint print = JasperFillManager.fillReport(report, new HashMap(), resultSetDataSource);
			JasperViewer.viewReport(print, false);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
			
	}
}