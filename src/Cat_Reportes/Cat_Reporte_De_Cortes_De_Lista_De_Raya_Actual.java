package Cat_Reportes;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import Conexiones_SQL.Generacion_Reportes;

@SuppressWarnings("serial")
public class Cat_Reporte_De_Cortes_De_Lista_De_Raya_Actual extends JFrame {
	JButton btncortes_Limpio = new JButton(new ImageIcon("imagen/hoja-de-calculo-excel-icono-5223-16.png"));
	JButton btnPrestamos_Por_Establecimiento =new JButton(new ImageIcon("imagen/plan-icono-5073-16.png"));
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	

	Border blackline, etched, raisedbevel, loweredbevel, empty;
	TitledBorder title4; 

	public Cat_Reporte_De_Cortes_De_Lista_De_Raya_Actual() {
		
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/dinero-icono-8797-48.jpg"));
		blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
		panel.setBorder(BorderFactory.createTitledBorder(blackline,"Seleccion Del Reporte de Diferencia de Cortes de Lista de Raya Actual"));
		this.setTitle("Reportes de Abonos y Saldos A Cortes");
		
		btncortes_Limpio.setText(	"<html> <FONT FACE="+"arial"+" SIZE=3 COLOR=BLACk>" +
				"		<p>Impresion de Reporte De Cortes</p>" +
				"		<CENTER><p>Para Exportar a Excel</p></CENTER></FONT>" +
				"</html>"); 
		
		btnPrestamos_Por_Establecimiento.setText(	"<html> <FONT FACE="+"arial"+" SIZE=3 COLOR=BLACk>" +
				"		<p>Impresion de Reporte De Abonos y Saldo </p>" +
				"		<CENTER><p>De Lista de Raya Actual Por Establecimiento</p></CENTER></FONT>" +
				"</html>"); 
		

		panel.add(btnPrestamos_Por_Establecimiento).setBounds(40,40,300,40);
//		panel.add(btncortes_Limpio).setBounds(40,100,300,40);
		btnPrestamos_Por_Establecimiento.addActionListener(Reporte_Cortes_Lista_de_Raya_Actual_Por_Establecimiento);
		btncortes_Limpio.addActionListener(Reporte_Cortes_Lista_de_Raya_Actual_limpio);

		cont.add(panel);
		this.setSize(380,150);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
	}
	
	String basedatos="2.26";
	String vista_previa_reporte="no";
	int vista_previa_de_ventana=0;
	String comando="";
	String reporte = "";
	
	
	ActionListener Reporte_Cortes_Lista_de_Raya_Actual_Por_Establecimiento = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			 reporte = "Obj_Reporte_De_Cortes_De_Lista_De_Raya_Actual.jrxml";
			 comando = "exec sp_Reporte_De_Cortes_De_Lista_De_Raya_Actual_Para_Exportar 'status',0";
			 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
		}
	};
	
	
	ActionListener Reporte_Cortes_Lista_de_Raya_Actual_limpio = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			 reporte = "Obj_Reporte_De_Cortes_De_Lista_De_Raya_Actual_Para_Exportar.jrxml";
			 comando = "exec sp_Reporte_De_Cortes_De_Lista_De_Raya_Actual_Para_Exportar 'NombreCompleto '";
			 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
		}
	};
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Reporte_De_Cortes_De_Lista_De_Raya_Actual().setVisible(true);
		}catch(Exception e){	}
	}


}
