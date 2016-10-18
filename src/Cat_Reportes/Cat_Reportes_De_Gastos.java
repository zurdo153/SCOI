package Cat_Reportes;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.border.Border;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Connexion;
import Conexiones_SQL.Generacion_Reportes;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.JCButton;

import com.toedter.calendar.JDateChooser;

@SuppressWarnings("serial")
public class Cat_Reportes_De_Gastos extends JFrame {
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	Connexion con = new Connexion();
	
	JDateChooser c_inicio = new JDateChooser();
	JDateChooser c_final = new JDateChooser();
	
	String operador[] = {"Selecciona Un Concepto","Gastos De Ventas","Gastos De Ventas NC","Gastos De Administración","Gastos De Administración NC","Gastos Financieros","Gastos Financieros NC","Gastos De Ventas Globales","Gastos De Ventas Globales NC"};
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbConcepto = new JComboBox(operador);
	
	String establecimiento[] = new Obj_Establecimiento().Combo_Establecimiento_Estado_resultados();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbEstablecimiento = new JComboBox(establecimiento);
	
	JCButton btn_Origen = new JCButton  ("","list-icon-1440-32px.png","Azul");
	JCButton btn_generarpdf = new JCButton  ("","pdf-icon-png-2081-32px.png","Azul");
	JCButton btn_generarxls = new JCButton  ("","xls-icon-3376-32px.png","Azul");
	
	JLabel JLBlinicio= new JLabel(new ImageIcon("Imagen/iniciar-icono-4628-16.png") );
	JLabel JLBfin= new JLabel(new ImageIcon("Imagen/acabado-icono-7912-16.png") );
	JLabel JLBFactor= new JLabel();
    
	Border blackline, etched, raisedbevel, loweredbevel, empty;	
	String parametroGeneral = "";
	String Lista="";
	String factor="";
	Obj_Usuario usuario = new Obj_Usuario().LeerSession();
	public Cat_Reportes_De_Gastos(){
		this.setSize(470,220);
		cont.add(panel);
		setResizable(false);
		setLocationRelativeTo(null);
		setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/diferiencia_de_sueldos_entre_listas_de_raya2_64.png"));
		setTitle("Reportes De Gastos");
		panel.setBorder(BorderFactory.createTitledBorder("Seleccione Las Fechas Y De Click a Buscar"));
		
		btn_Origen.setText(	"<html> <FONT FACE="+"arial"+" SIZE=3 COLOR=WHITE" +
				"		<CENTER><p>Generar Reporte Del Concepto</p></CENTER></FONT>" +
				"</html>");
		
		btn_generarpdf.setText(	"<html> <FONT FACE="+"arial"+" SIZE=3 COLOR=WHITE" +
				"		<CENTER><p>Generar Reportes En PDF</p></CENTER></FONT>" +
				"</html>");
		
		btn_generarxls.setText(	"<html> <FONT FACE="+"arial"+" SIZE=3 COLOR=WHITE" +
				"		<CENTER><p>Generar Reportes En XLS</p></CENTER></FONT>" +
				"</html>");
		
		int x=15,y=25,l=100,a=20;

		this.panel.add(new JLabel("Fecha Inicial:")).setBounds(x   ,y     ,l  ,a);
		this.panel.add(JLBlinicio).setBounds                  (x+60,y     ,a  ,a);
		this.panel.add(c_inicio).setBounds                    (x+80,y     ,l  ,a);
	
		this.panel.add(new JLabel("Fecha Final:")).setBounds  (x   ,y+30  ,l  ,a);
		this.panel.add(JLBfin).setBounds                      (x+60,y+30  ,a  ,a);
		this.panel.add(c_final).setBounds                     (x+80,y+30  ,l  ,a);
		
		l=200;
		this.panel.add(cmbConcepto).setBounds                 (x+230,y    ,l  ,a);
		this.panel.add(cmbEstablecimiento).setBounds          (x+230,y+30 ,l  ,a);
		
		a=35;
		this.panel.add(btn_generarpdf).setBounds              (x    ,y+65 ,l  ,a);
		this.panel.add(btn_Origen).setBounds                  (x+230,y+65 ,l  ,a);
		this.panel.add(btn_generarxls).setBounds              (x+120,y+115,l  ,a);
		
        c_inicio.setDate(cargar_fechas(7));
        c_final.setDate(cargar_fechas(0));

		btn_Origen.addActionListener(opGenerarReporte_de_concepto);
		btn_generarpdf.addActionListener(opGenerarEnPDF);
		btn_generarxls.addActionListener(opGenerarEnXLS);
		
        this.addWindowListener(new WindowAdapter() { public void windowOpened( WindowEvent e ){
            	c_inicio.requestFocus();
                      } });
	}

	public String validar_fechas(){
		String error = "";
		String fechainicioNull = c_inicio.getDate()+"";
	    if(fechainicioNull.equals("null"))error+= "Fecha  inicio\n";		
		return error;
	}
	
	public Date cargar_fechas(int dias){
		Date date = null;
				  try {
					date = new SimpleDateFormat("dd/MM/yyyy").parse(new BuscarSQL().fecha(dias));
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		return date;
	};
	
	ActionListener opGenerarEnPDF = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			 if(validar_fechas().equals("")){
				  String fecha_inicio = new SimpleDateFormat("dd/MM/yyyy").format(c_inicio.getDate())+" 00:00:00";
				  String fecha_final  = new SimpleDateFormat("dd/MM/yyyy").format(c_final.getDate())+"  23:59:00";
				  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm"); 
				  Date fecha1 = sdf.parse(fecha_inicio , new ParsePosition(0));
				  Date fecha2 = sdf.parse(fecha_final , new ParsePosition(0));
	 	if(fecha1.before(fecha2)){		
			String basedatos="2.26";
			String vista_previa_reporte="si";
			int vista_previa_de_ventana=1;
			String comando="";
			String reporte ="";
 		    String fecha_guardado=new SimpleDateFormat("dd-MM-yyyy").format(c_final.getDate());
			
			comando="exec sp_Reporte_De_Gastos_En_Un_Periodo '','"+fecha_inicio+"','"+fecha_final+"','Gastos De Ventas Globales','"+usuario.getNombre_completo()+"'" ;
		  	reporte = "Obj_Reporte_De_Gastos_Estado_Resultados_Globales.jrxml";
			new Generacion_Reportes().Reporte_Guardado(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana,"pdfciclo","Gastos De Ventas Globales "+fecha_guardado);
			
			comando="exec sp_Reporte_De_Gastos_En_Un_Periodo '','"+fecha_inicio+"','"+fecha_final+"','Gastos De Ventas Globales NC','"+usuario.getNombre_completo()+"'" ;
		  	reporte = "Obj_Reporte_De_Gastos_Estado_Resultados_Globales.jrxml";
			new Generacion_Reportes().Reporte_Guardado(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana,"pdfciclo","Gastos De Ventas Globales NC "+fecha_guardado);
			
			comando="exec sp_Reporte_De_Gastos_En_Un_Periodo '','"+fecha_inicio+"','"+fecha_final+"','Gastos De Administración','"+usuario.getNombre_completo()+"'" ;
			reporte = "Obj_Reporte_De_Gastos_Estado_Resultados.jrxml";
			new Generacion_Reportes().Reporte_Guardado(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana,"pdfciclo","Gastos De Administración "+fecha_guardado);
			
			comando="exec sp_Reporte_De_Gastos_En_Un_Periodo '','"+fecha_inicio+"','"+fecha_final+"','Gastos De Administración NC','"+usuario.getNombre_completo()+"'" ;
			reporte = "Obj_Reporte_De_Gastos_Estado_Resultados.jrxml";
			new Generacion_Reportes().Reporte_Guardado(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana,"pdfciclo","Gastos De Administración NC "+fecha_guardado);
			
			comando="exec sp_Reporte_De_Gastos_En_Un_Periodo '','"+fecha_inicio+"','"+fecha_final+"','Gastos Financieros','"+usuario.getNombre_completo()+"'" ;
			reporte = "Obj_Reporte_De_Gastos_Estado_Resultados.jrxml";
			new Generacion_Reportes().Reporte_Guardado(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana,"pdfciclo","Gastos Financieros "+fecha_guardado);

			comando="exec sp_Reporte_De_Gastos_En_Un_Periodo '','"+fecha_inicio+"','"+fecha_final+"','Gastos Financieros NC','"+usuario.getNombre_completo()+"'" ;
			reporte = "Obj_Reporte_De_Gastos_Estado_Resultados.jrxml";
			new Generacion_Reportes().Reporte_Guardado(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana,"pdfciclo","Gastos Financieros NC "+fecha_guardado);

				for(int i=1;i<establecimiento.length;i++){
					comando="exec sp_Reporte_De_Gastos_En_Un_Periodo '"+establecimiento[i].trim()+"','"+fecha_inicio+"','"+fecha_final+"','Gastos De Ventas','"+usuario.getNombre_completo()+"'" ;
					reporte = "Obj_Reporte_De_Gastos_Estado_Resultados.jrxml";
					new Generacion_Reportes().Reporte_Guardado(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana,"pdfciclo","Gastos De Ventas "+establecimiento[i]+" "+fecha_guardado);

					comando="exec sp_Reporte_De_Gastos_En_Un_Periodo '"+establecimiento[i].trim()+"','"+fecha_inicio+"','"+fecha_final+"','Gastos De Ventas NC','"+usuario.getNombre_completo()+"'" ;
					reporte = "Obj_Reporte_De_Gastos_Estado_Resultados.jrxml";
					new Generacion_Reportes().Reporte_Guardado(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana,"pdfciclo","Gastos De Ventas NC "+establecimiento[i]+fecha_guardado);
			   }
				
				 JOptionPane.showMessageDialog(null, "Se Crearon Correctamente Los Reportes En Formato PDF \nEn La Carpeta C:\\REPORTES SCOI\\PDF\\","Aviso", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/aplicara-el-dialogo-icono-6256-32.png"));
	 	 }else{
			JOptionPane.showMessageDialog(null,"El Rango de Fechas Esta Invertido","Aviso!", JOptionPane.WARNING_MESSAGE);
			return;
			}
		}
		}
	};
	
	
	ActionListener opGenerarEnXLS = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			 if(validar_fechas().equals("")){
				  String fecha_inicio = new SimpleDateFormat("dd/MM/yyyy").format(c_inicio.getDate())+" 00:00:00";
				  String fecha_final  = new SimpleDateFormat("dd/MM/yyyy").format(c_final.getDate())+"  23:59:00";
				  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm"); 
				  Date fecha1 = sdf.parse(fecha_inicio , new ParsePosition(0));
				  Date fecha2 = sdf.parse(fecha_final , new ParsePosition(0));
	 	if(fecha1.before(fecha2)){		
			String basedatos="2.26";
			String vista_previa_reporte="si";
			int vista_previa_de_ventana=1;
			String comando="";
			String reporte ="";
 		    String fecha_guardado=new SimpleDateFormat("dd-MM-yyyy").format(c_final.getDate());
			
			comando="exec sp_Reporte_De_Gastos_En_Un_Periodo '','"+fecha_inicio+"','"+fecha_final+"','Gastos De Ventas Globales','"+usuario.getNombre_completo()+"'" ;
		  	reporte = "Obj_Reporte_De_Gastos_Estado_Resultados_Globales.jrxml";
			new Generacion_Reportes().Reporte_Guardado(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana,"xlsciclo","Gastos De Ventas Globales "+fecha_guardado);
			
			comando="exec sp_Reporte_De_Gastos_En_Un_Periodo '','"+fecha_inicio+"','"+fecha_final+"','Gastos De Ventas Globales NC','"+usuario.getNombre_completo()+"'" ;
		  	reporte = "Obj_Reporte_De_Gastos_Estado_Resultados_Globales.jrxml";
			new Generacion_Reportes().Reporte_Guardado(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana,"xlsciclo","Gastos De Ventas Globales NC "+fecha_guardado);
			
			comando="exec sp_Reporte_De_Gastos_En_Un_Periodo '','"+fecha_inicio+"','"+fecha_final+"','Gastos De Administración','"+usuario.getNombre_completo()+"'" ;
			reporte = "Obj_Reporte_De_Gastos_Estado_Resultados.jrxml";
			new Generacion_Reportes().Reporte_Guardado(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana,"xlsciclo","Gastos De Administración "+fecha_guardado);
			
			comando="exec sp_Reporte_De_Gastos_En_Un_Periodo '','"+fecha_inicio+"','"+fecha_final+"','Gastos De Administración NC','"+usuario.getNombre_completo()+"'" ;
			reporte = "Obj_Reporte_De_Gastos_Estado_Resultados.jrxml";
			new Generacion_Reportes().Reporte_Guardado(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana,"xlsciclo","Gastos De Administración NC "+fecha_guardado);
			
			comando="exec sp_Reporte_De_Gastos_En_Un_Periodo '','"+fecha_inicio+"','"+fecha_final+"','Gastos Financieros','"+usuario.getNombre_completo()+"'" ;
			reporte = "Obj_Reporte_De_Gastos_Estado_Resultados.jrxml";
			new Generacion_Reportes().Reporte_Guardado(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana,"xlsciclo","Gastos Financieros "+fecha_guardado);

			comando="exec sp_Reporte_De_Gastos_En_Un_Periodo '','"+fecha_inicio+"','"+fecha_final+"','Gastos Financieros NC','"+usuario.getNombre_completo()+"'" ;
			reporte = "Obj_Reporte_De_Gastos_Estado_Resultados.jrxml";
			new Generacion_Reportes().Reporte_Guardado(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana,"xlsciclo","Gastos Financieros NC "+fecha_guardado);

				for(int i=1;i<establecimiento.length;i++){
					comando="exec sp_Reporte_De_Gastos_En_Un_Periodo '"+establecimiento[i].trim()+"','"+fecha_inicio+"','"+fecha_final+"','Gastos De Ventas','"+usuario.getNombre_completo()+"'" ;
					reporte = "Obj_Reporte_De_Gastos_Estado_Resultados.jrxml";
					new Generacion_Reportes().Reporte_Guardado(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana,"xlsciclo","Gastos De Ventas "+establecimiento[i]+" "+fecha_guardado);

					comando="exec sp_Reporte_De_Gastos_En_Un_Periodo '"+establecimiento[i].trim()+"','"+fecha_inicio+"','"+fecha_final+"','Gastos De Ventas NC','"+usuario.getNombre_completo()+"'" ;
					reporte = "Obj_Reporte_De_Gastos_Estado_Resultados.jrxml";
					new Generacion_Reportes().Reporte_Guardado(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana,"xlsciclo","Gastos De Ventas NC "+establecimiento[i]+fecha_guardado);
			   }
				 JOptionPane.showMessageDialog(null, "Se Crearon Correctamente Los Reportes En Formato XLS \nEn La Carpeta C:\\REPORTES SCOI\\XLS\\","Aviso", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/aplicara-el-dialogo-icono-6256-32.png"));
	 	 }else{
			JOptionPane.showMessageDialog(null,"El Rango de Fechas Esta Invertido","Aviso!", JOptionPane.WARNING_MESSAGE);
			return;
			}
		}
		}
	};

	ActionListener opGenerarReporte_de_concepto = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			 if(validar_fechas().equals("")){
				  String fecha_inicio = new SimpleDateFormat("dd/MM/yyyy").format(c_inicio.getDate())+" 00:00:00";
				  String fecha_final  = new SimpleDateFormat("dd/MM/yyyy").format(c_final.getDate())+"  23:59:00";
				  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm"); 
				  Date fecha1 = sdf.parse(fecha_inicio , new ParsePosition(0));
				  Date fecha2 = sdf.parse(fecha_final , new ParsePosition(0));
	 	if(fecha1.before(fecha2)){		
	 		int testigo=0;
			String basedatos="2.26";
			String vista_previa_reporte="no";
			int vista_previa_de_ventana=0;
			String comando="";
			String reporte ="";
			
			if(cmbConcepto.getSelectedItem().toString().trim().equals("Selecciona Un Concepto")){
			  JOptionPane.showMessageDialog(null,"Es Necesario Seleccionar Un Concepto Para Poder Generar El Reporte Del Concepto", "Aviso!",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));	
               return;
			}else{
				
			    if(cmbConcepto.getSelectedItem().toString().trim().equals("Gastos De Ventas Globales NC")||cmbConcepto.getSelectedItem().toString().trim().equals("Gastos De Ventas Globales") ){
							comando="exec sp_Reporte_De_Gastos_En_Un_Periodo '"+cmbEstablecimiento.getSelectedItem().toString().trim()+"','"+fecha_inicio+"','"+fecha_final+"','"+cmbConcepto.getSelectedItem().toString().trim()+"','"+usuario.getNombre_completo()+"'" ;
							reporte = "Obj_Reporte_De_Gastos_Estado_Resultados_Globales.jrxml";
							testigo=1;
				}else{
							if(cmbEstablecimiento.getSelectedItem().toString().trim().equals("Selecciona un Establecimiento")){	
								  JOptionPane.showMessageDialog(null,"Es Necesario Seleccionar Un Establecimiento Para Poder Generar El Reporte Del Concepto ", "Aviso!",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));	
								  return;
							}else{
								
								if(cmbConcepto.getSelectedItem().toString().trim().equals("Diferiencias De Inventario")){
									comando="exec sp_Reporte_De_Diferiencias_De_Inventario '"+cmbEstablecimiento.getSelectedItem().toString().trim()+"','"+fecha_inicio+"','"+fecha_final+"','"+usuario.getNombre_completo()+"'" ;
									reporte = "Obj_Reporte_De_Inventarios_Fisicos_Estado_Resultados.jrxml";
									testigo=1;
								}
			
								if(cmbConcepto.getSelectedItem().toString().trim().equals("Nomina Gastos Administracion Impuestos")||cmbConcepto.getSelectedItem().toString().trim().equals("Nomina Gastos Venta Impuestos")||cmbConcepto.getSelectedItem().toString().trim().equals("Gastos De Administración")||cmbConcepto.getSelectedItem().toString().trim().equals("Gastos De Ventas")||cmbConcepto.getSelectedItem().toString().trim().equals("Gastos Financieros")||cmbConcepto.getSelectedItem().toString().trim().equals("Gastos De Ventas NC") ||cmbConcepto.getSelectedItem().toString().trim().equals("Gastos De Administración NC")||cmbConcepto.getSelectedItem().toString().trim().equals("Gastos Financieros NC")  ){
											comando="exec sp_Reporte_De_Gastos_En_Un_Periodo '"+cmbEstablecimiento.getSelectedItem().toString().trim()+"','"+fecha_inicio+"','"+fecha_final+"','"+cmbConcepto.getSelectedItem().toString().trim()+"','"+usuario.getNombre_completo()+"'" ;
											reporte = "Obj_Reporte_De_Gastos_Estado_Resultados.jrxml";
											testigo=1;
								}
							    
							    if(cmbConcepto.getSelectedItem().toString().trim().equals("Nomina") ){
											comando="exec sp_reporte_de_lista_de_raya_en_un_periodo_edo_resultados '"+cmbEstablecimiento.getSelectedItem().toString().trim()+"','"+fecha_inicio+"','"+fecha_final+"','"+cmbConcepto.getSelectedItem().toString().trim()+"','"+usuario.getNombre_completo()+"'" ;
											reporte = "Obj_Reporte_De_Listas_De_Raya_En_Un_Periodo.jrxml";
											testigo=1;
								}
				           }
				}
			if(testigo==1){
				  new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
			}else{
			  	  JOptionPane.showMessageDialog(null,"Error Concepto No Identificado","Avisa Al Adimistrador Del Sistema!", JOptionPane.ERROR_MESSAGE);
				  return;
				 }
			}		
	 	 }else{
			JOptionPane.showMessageDialog(null,"El Rango de Fechas Esta Invertido","Aviso!", JOptionPane.WARNING_MESSAGE);
			return;
			}
		}
		}
	};
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Reportes_De_Gastos().setVisible(true);
		}catch(Exception e){	}
	}
}
