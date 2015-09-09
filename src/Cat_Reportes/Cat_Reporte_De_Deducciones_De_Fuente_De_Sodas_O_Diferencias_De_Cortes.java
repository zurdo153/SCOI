package Cat_Reportes;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import Conexiones_SQL.Generacion_Reportes;

@SuppressWarnings("serial")
public class Cat_Reporte_De_Deducciones_De_Fuente_De_Sodas_O_Diferencias_De_Cortes extends JFrame {
	JButton btnReporte = new JButton();
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	Border blackline, etched, raisedbevel, loweredbevel, empty;
	TitledBorder title4; 

	public Cat_Reporte_De_Deducciones_De_Fuente_De_Sodas_O_Diferencias_De_Cortes() {
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/dinero-en-efectivo-cartera-monedero-icono-7127-32.png"));
		blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
		panel.setBorder(BorderFactory.createTitledBorder(blackline,"Seleccion Del Reporte de Prestamos de Lista de Raya Actual"));
		this.setTitle("Reportes De Deducciones De Fuente De Sodas o Diferencias De Cortes");
		btnReporte.setText(	"<html> <FONT FACE="+"arial"+" SIZE=3 COLOR=BLACk>" +
				"		<p>Impresion De Reporte De Deducciones De</p>" +
				"		<p>Fuente De Sodas o Diferencias De Cortes</p>" +
				"		<CENTER><p>De Lista De Raya Actual</p></CENTER></FONT>" +
				"</html>"); 
		panel.add(btnReporte).setBounds(40,40,280,60);
		btnReporte.addActionListener(opGenerar);
		cont.add(panel);
		this.setSize(365,160);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
	}
	ActionListener opGenerar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String basedatos="2.26";
			String vista_previa_reporte="no";
			int vista_previa_de_ventana=0;
			String comando="exec sp_Reporte_De_Deducciones_Por_Fuente_De_Sodas_O_Diferencias_De_Cortes"  ;
			String reporte = "Obj_Reporte_De_Deducciones_Por_Fuente_De_Sodas_O_Diferencias_De_Cortes.jrxml";
		   new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
		}
	};

	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Reporte_De_Deducciones_De_Fuente_De_Sodas_O_Diferencias_De_Cortes().setVisible(true);
		}catch(Exception e){	}
	}
}
