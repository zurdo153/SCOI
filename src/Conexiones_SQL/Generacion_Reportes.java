package Conexiones_SQL;

import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Obj_Administracion_del_Sistema.Obj_Usuario;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.swing.JRViewer;

@SuppressWarnings("deprecation")
public class Generacion_Reportes {
	String reporte = "";
	String comando="";
	String vista_previa_reporte="";
	String vista_previa_de_ventana="";
	String basedatos="";

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void  Reporte(String reporte,String comando,String basedatos,String vista_previa_reporte, int vista_previa_de_ventana){
			    Obj_Usuario usuario = new Obj_Usuario();
                String query =comando ;
				Statement stmt = null;
				
				if (vista_previa_de_ventana==0){
					vista_previa_reporte=usuario.buscar(usuario.LeerSession().getFolio()).getVista_previa_impresion();
				}
				
				if (basedatos=="2.200"){
					try {
						stmt =  new Connexion().conexion_IZAGAR().createStatement();
					} catch (SQLException e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, "Error En La Coneccion Con el Servidor:"+basedatos+" 1 \n "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
					}
				}else{
					try {
						stmt =  new Connexion().conexion().createStatement();
					} catch (SQLException e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, "Error En La Coneccion Con el Servidor:"+basedatos+" 1 \n "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/configuracion-de-usuario-de-configuracion-de-la-herramienta-de-ocio-icono-7245-64.png"));
					}
				}
				
				try{
					JDialog viewer = new JDialog(new JFrame(),reporte, true);
					viewer.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Report.png"));
					viewer.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds()); 
					viewer.setLocationRelativeTo(null);
					
					// Cargar el archivo .jrxml
					ResultSet rs = stmt.executeQuery(query);
					JasperReport report = JasperCompileManager.compileReport(System.getProperty("user.dir")+"\\src\\Obj_Reportes\\"+reporte);
					JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
					
					
					
					// En mapa se especifican los parametros del reporte
					JasperPrint print = JasperFillManager.fillReport(report, new HashMap(), resultSetDataSource);
					if (vista_previa_reporte.equals("si")){
						// Mostrar el reporte
							JRViewer jrv = new JRViewer(print);
							jrv.setZoomRatio(1);//zoom default del reporte
							viewer.getContentPane().add(jrv);
							viewer.show();
						}else{
							  if(vista_previa_reporte.equals("no")){
								//imprimir con seleccion de impresora
								 JasperPrintManager.printReport(print, true);
						      }else{ 
						    	 if(vista_previa_reporte.equals("id")){
		  					   //imprimime directo en impresora determinada en la pc
							      JasperPrintManager.printReport(print, true);
				    	       }else{
							  JOptionPane.showMessageDialog(null, "Error en La Clase Generacion Reportes En El Valor De La Variable Vista Previa \n Valor No Identificado De Los Siguientes: <<<si,no,id>>> \n Valor Recibido:"+vista_previa_reporte,"Avisa Al Administrador Del Sistema", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/configuracion-de-usuario-de-configuracion-de-la-herramienta-de-ocio-icono-7245-64.png"));
		                      return;
				    	       }
				     	 }
					 	 }
					}
					catch(Exception ex){
						System.out.println(ex.getMessage());
						System.out.println(comando);
						
						JOptionPane.showMessageDialog(null, "Error Al Intentar Generar El Reporte: \n En La Clase Generacion Reportes Reporte:"+reporte+"\n Comando: "+comando+"\n Mensaje Exception: "+ex.getMessage(), "Avisa Al Administrador Del Sistema", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/configuracion-de-usuario-de-configuracion-de-la-herramienta-de-ocio-icono-7245-64.png"));
					}
		}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void  Reporte_Guardado(String reporte,String comando,String basedatos,String vista_previa_reporte, int vista_previa_de_ventana,String Guardar_reporte_formato, String nombre_reporte){
	    Obj_Usuario usuario = new Obj_Usuario();
        String query =comando ;
		Statement stmt = null;
		
		if (vista_previa_de_ventana==0){
			vista_previa_reporte=usuario.buscar(usuario.LeerSession().getFolio()).getVista_previa_impresion();
		}
		
		if (basedatos=="2.200"){
			try {
				stmt =  new Connexion().conexion_IZAGAR().createStatement();
			} catch (SQLException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error En La Coneccion Con el Servidor:"+basedatos+" 1 \n "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			}
		}else{
			try {
				stmt =  new Connexion().conexion().createStatement();
			} catch (SQLException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error En La Coneccion Con el Servidor:"+basedatos+" 1 \n "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/configuracion-de-usuario-de-configuracion-de-la-herramienta-de-ocio-icono-7245-64.png"));
			}
		}
		
		try{
			
			JDialog viewer = new JDialog(new JFrame(),"Vista previa del reporte", true);
			viewer.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Report.png"));
			viewer.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds()); 
			viewer.setLocationRelativeTo(null);
			
			// Cargar el archivo .jrxml
			ResultSet rs = stmt.executeQuery(query);
			JasperReport report = JasperCompileManager.compileReport(System.getProperty("user.dir")+"\\src\\Obj_Reportes\\"+reporte);
			JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
			// En mapa se especifican los parametros del reporte
			JasperPrint print = JasperFillManager.fillReport(report, new HashMap(), resultSetDataSource);
			
			
			if(Guardar_reporte_formato.equals("pdf")||Guardar_reporte_formato.equals("pdfciclo")){
				String ruta = "c:\\REPORTES SCOI\\PDF\\";
				File archivos = new File(ruta);
				// creamos fichero si no existe y escribimos archivo 
				if (!archivos.exists()) { 
						archivos.mkdirs();
				}
				
				if(Guardar_reporte_formato.equals("pdf")){
					JasperExportManager.exportReportToPdfFile(print, "C:\\REPORTES SCOI\\PDF\\"+nombre_reporte+"."+Guardar_reporte_formato);
					  JOptionPane.showMessageDialog(null, "Se Crearon Correctamente El(Los) Reporte(s) En Formato >>"+Guardar_reporte_formato+"\nEn La Carpeta C:\\REPORTES SCOI\\PDF\\","Aviso", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/aplicara-el-dialogo-icono-6256-32.png"));
					 return;
			      }
				
				if(Guardar_reporte_formato.equals("pdfciclo")){
					JasperExportManager.exportReportToPdfFile(print, "C:\\REPORTES SCOI\\PDF\\"+nombre_reporte+".pdf");
					return;
			      }
			}

			
			if(Guardar_reporte_formato.equals("xls")||Guardar_reporte_formato.equals("xlsciclo")){
				String ruta = "c:\\REPORTES SCOI\\XLS\\";
				File archivos = new File(ruta);
				if (!archivos.exists()) { 
						archivos.mkdirs();
				}
				
				if(Guardar_reporte_formato.equals("xls")){
					 String reportDestination = "C:\\REPORTES SCOI\\XLS\\"+nombre_reporte+".xlsx";  //This is generated Correctly
			            File xlsFile = new File(reportDestination);
			            JRXlsxExporter Xlsxexporter = new JRXlsxExporter();
			            Xlsxexporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
			            Xlsxexporter.setParameter(JRExporterParameter.OUTPUT_FILE, xlsFile);
			            Xlsxexporter.exportReport();
			            FileInputStream fis = new FileInputStream(new File(reportDestination));
			            fis.close();
					  JOptionPane.showMessageDialog(null, "Se Crearon Correctamente El(Los) Reporte(s) En Formato >>"+Guardar_reporte_formato+"\nEn La Carpeta C:\\REPORTES SCOI\\XLS\\","Aviso", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/aplicara-el-dialogo-icono-6256-32.png"));
					 return;
			      }
				
				if(Guardar_reporte_formato.equals("xlsciclo")){
					 String reportDestination = "C:\\REPORTES SCOI\\XLS\\"+nombre_reporte+".xlsx";  //This is generated Correctly
			            File xlsFile = new File(reportDestination);
			            JRXlsxExporter Xlsxexporter = new JRXlsxExporter();
			            Xlsxexporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
			            Xlsxexporter.setParameter(JRExporterParameter.OUTPUT_FILE, xlsFile);
			            Xlsxexporter.exportReport();
			            FileInputStream fis = new FileInputStream(new File(reportDestination));
			            fis.close();
					return;
			      }
			}
			
			}
			catch(Exception ex){
				System.out.println(ex.getMessage());
				System.out.println(comando);
				
				JOptionPane.showMessageDialog(null, "Error Al Intentar Generar El Reporte: \n En La Clase Generacion Reportes Reporte:"+reporte+"\n Comando: "+comando+"\n Mensaje Exception: "+ex.getMessage(), "Avisa Al Administrador Del Sistema", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/configuracion-de-usuario-de-configuracion-de-la-herramienta-de-ocio-icono-7245-64.png"));
			}
		
}
}
