package Cat_Reportes;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import Conexiones_SQL.Connexion;
import Obj_Principal.JCButton;

@SuppressWarnings("serial")
public class Cat_Reporte_De_Ausentismo_En_Lista_De_Raya extends JFrame{
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	public String[] anios(){
		String[] lista = new String[63];
			
			for(int i=0; i<63; i++){
				lista[i] = 2016+i+"";
			}
		
		return lista;
	} 
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbAnios = new JComboBox(anios());
	
//	se consultan los empleados en la misma funcion del objeto, mandandole el folio del departamento en el que esta dado de alta
	String[] meses ={"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre" };
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbMeses = new JComboBox(meses);
	

	
	JButton btngenerar = new JCButton("Generar", "buscar.png", "Azul");
//			new JButton("*Generar",new ImageIcon("imagen/buscar.png"));
	
	public Cat_Reporte_De_Ausentismo_En_Lista_De_Raya(){
		this.setTitle("Consulta De Ausentismo En Lista De Raya");
		this.setSize(360,200);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/inasistencia32.png"));
		panel.setBorder(BorderFactory.createTitledBorder("Seleccione El Mes Que Desea Calcular"));
		
		int x=15,y=20,ancho=230;
		panel.add(new JLabel("Fecha: ")).setBounds(x,y,80,20);
		panel.add(cmbAnios).setBounds(x+45,y,55,20);
		panel.add(cmbMeses).setBounds(x+100,y,ancho,20);
		panel.add(btngenerar).setBounds(x,y+=35,100,20);
		
		cont.add(panel);
		btngenerar.addActionListener(opGenerar);
		
	}
	
	ActionListener opGenerar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			int anio = Integer.valueOf(cmbAnios.getSelectedItem().toString().trim());
			int mes = cmbMeses.getSelectedIndex()+1;
			
			if(anio>=2016){
				
					if((anio==2016 && mes<=2)){
						JOptionPane.showMessageDialog(null, "Solo Se Pueden Consultar El Reporte De Ausentismo A Partir del Mes De Marzo Del 2016","Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				          return;
						}else{
							Cat_Reporte_De_Ausentismo(anio+"",mes+"");
//							Cat_Reporte_De_Valor_De_Nomina(anio+"",mes+"");
						}
			}else{
		  	  	JOptionPane.showMessageDialog(null, "Solo Se Pueden Consultar El Reporte De Ausentismo A Partir del Mes De Marzo Del 2016","Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
		  	  	return;
			}
	 	}
	};
	   
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void Cat_Reporte_De_Ausentismo(String anio,String mes) {
			
		try {
			JasperReport report = JasperCompileManager.compileReport(System.getProperty("user.dir")+"\\src\\Obj_Reportes\\Obj_Reporte_De_Ausentismo_En_Lista_De_Raya.jrxml");
			
			Map parametro = new HashMap();
			parametro.put("anio", anio);
			parametro.put("mes", mes);
			
			JasperPrint print = JasperFillManager.fillReport(report, parametro, new Connexion().conexion());
			JasperViewer.viewReport(print, false);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(null, "Error En Cat_Reporte_De_Corte_De_Caja ", "Error !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
		}
	}
	
	
//	public static void Cat_Reporte_De_Valor_De_Nomina(String anio,String mes) {
//			
//		try {
//			
//			String basedatos="2.26";
//			String vista_previa_reporte="no";
//			int vista_previa_de_ventana=0;
//			
//			String comando="exec sp_select_valor_de_nomina '"+anio+"','"+mes+"'";
//			String reporte = "Obj_Reporte_De_Valor_De_Nomina.jrxml";
//			new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
//			 
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//			JOptionPane.showMessageDialog(null, "Error En Cat_Reporte_De_Corte_De_Caja ", "Error !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
//		}
//	}
	
	public static void main(String[] args) {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Reporte_De_Ausentismo_En_Lista_De_Raya().setVisible(true);
		}catch(Exception e){	}
	}
}
