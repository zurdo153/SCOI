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


import Conexiones_SQL.Generacion_Reportes;

@SuppressWarnings("serial")
public class Cat_Reportes_De_Totales_De_Cheques_De_Lista_De_Raya extends JFrame {
	JButton btnCheque_Lista_Raya_Actual = new JButton("",new ImageIcon("Imagen/dinero-icono-8797-32.png"));
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	Border blackline, etched, raisedbevel, loweredbevel, empty;

	
	public Cat_Reportes_De_Totales_De_Cheques_De_Lista_De_Raya() {
		this.setSize(320,150);
//		this.setAlwaysOnTop(true);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/dinero-icono-8797-32.png"));
		blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
		panel.setBorder(BorderFactory.createTitledBorder(blackline,"Reporte De Totales de Cheque De Lista De Raya"));
		this.setTitle("Reportes Totales De Cheque Por Lista De Raya");
		
		btnCheque_Lista_Raya_Actual.setText(	"<html> <FONT FACE="+"arial"+" SIZE=3 COLOR=BLACk>" +
				"		<p>Impresion de Reporte De Totales</p>" +
				"		<CENTER><p>De Cheque De Lista De Raya Actual</p></CENTER></FONT>" +
				"</html>"); 
		panel.add(btnCheque_Lista_Raya_Actual).setBounds(20,30,270,60);
		
		btnCheque_Lista_Raya_Actual.addActionListener(Reporte_De_Totales_de_Cheques_De_Lista_De_Raya_Actual);
		cont.add(panel);
	}
	
	ActionListener Reporte_De_Totales_de_Cheques_De_Lista_De_Raya_Actual = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			String basedatos="2.26";
			String vista_previa_reporte="no";
			int vista_previa_de_ventana=0;
			String comando="exec sp_Reporte_De_Totales_De_Cheque_De_Lista_De_Raya_Actual";
			String reporte = "Obj_Reporte_De_Totales_de_Cheques_De_Lista_De_Raya_Actual.jrxml";
			 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
		}
	};
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Reportes_De_Totales_De_Cheques_De_Lista_De_Raya().setVisible(true);
		}catch(Exception e){	}
	}

}
