package Cat_Reportes;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

import Conexiones_SQL.Connexion;
import Obj_Principal.Componentes;

@SuppressWarnings("serial")
public class Cat_Reporte_De_Diferencia_De_Recepcion_De_Transferencia extends JFrame{
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JTextField txtFolio = new Componentes().text(new JTextField(), "Folio de la Rececion de Transferencia", 15, "String");
	JButton btnReporte_actual = new JButton("",new ImageIcon("imagen/plan-icono-5073-16.png"));
	JButton btnReporte_actual_ticket = new JButton("",new ImageIcon("imagen/plan-icono-5073-16.png"));
	
	int tipo_Reporte = 0;
	
	public Cat_Reporte_De_Diferencia_De_Recepcion_De_Transferencia(){
		setSize(335,200);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Reportes De Difereriencias De Recepcion De Transferencia");
		setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/presentar-una-pluma-para-escribir-icono-9594-32.png"));
		panel.setBorder(BorderFactory.createTitledBorder("Teclea El Folio De La Recepcion"));

		btnReporte_actual.setText(	"<html> <FONT FACE="+"arial"+" SIZE=3 COLOR=BLACk>" +
				"		<CENTER><p>Reporte De Diferiencias De Recepciones De Transferencias (Carte)</p></CENTER></FONT>" +
				"</html>");	
		
		btnReporte_actual_ticket.setText(	"<html> <FONT FACE="+"arial"+" SIZE=3 COLOR=BLACk>" +
				"		<CENTER><p>Reporte De Diferiencias De Recepciones De Transferencias (Ticket)</p></CENTER></FONT>" +
				"</html>");	
	
		panel.add(new JLabel("Folio Recepcion De Mercancia:")).setBounds(95,15,200,20);		
		panel.add(txtFolio).setBounds(90,30,155,20);
		panel.add(btnReporte_actual).setBounds(35,60,260,45);
		panel.add(btnReporte_actual_ticket).setBounds(35,115,260,45);

		cont.add(panel);
		btnReporte_actual.addActionListener(opGenerar);
		btnReporte_actual_ticket.addActionListener(opGenerar);
	}
	
	ActionListener opGenerar = new ActionListener() {
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public void actionPerformed(ActionEvent e) {
			
			String nom_reporte = "";
			if(e.getActionCommand().equals("<html> <FONT FACE=arial SIZE=3 COLOR=BLACk>		<CENTER><p>Reporte De Diferiencias De Recepciones De Transferencias (Carte)</p></CENTER></FONT></html>")){
				nom_reporte = "Obj_Reporte_IZAGAR_de_Diferiencias_De_Recepciones_De_Transferencia.jrxml";
			}else{
				nom_reporte = "Obj_Reporte_IZAGAR_de_Diferiencias_De_Recepciones_De_Transferencia_Ticket.jrxml";
			}
			
			tipo_Reporte=2;
			if(tipo_Reporte==2){
						if(!txtFolio.getText().equals("")){
							String query = "exec sp_Reporte_De_Diferiencias_De_Recepciones_De_Transferencia '"+String.valueOf(txtFolio.getText().toUpperCase().trim())+"'"  ;
							Statement stmt = null;
								try {
									stmt =  new Connexion().conexion_IZAGAR().createStatement();
								    ResultSet rs = stmt.executeQuery(query);
									JasperReport report = JasperCompileManager.compileReport(System.getProperty("user.dir")+"\\src\\Obj_Reportes\\"+nom_reporte);
									JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
									JasperPrint print = JasperFillManager.fillReport(report, new HashMap(), resultSetDataSource);
									JasperViewer.viewReport(print, false);
								} catch (Exception e2) {
									System.out.println(e2.getMessage());
									JOptionPane.showMessageDialog(null, "Error en Generar Reporte de Diferiencia sp_Reporte_De_Diferiencias_De_Recepciones_De_Transferencia SQLException: \n "+e2.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
								}	
								return;	
					    }
		}
		}
	};
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Reporte_De_Diferencia_De_Recepcion_De_Transferencia().setVisible(true);
		}catch(Exception e){	}
	}
}
