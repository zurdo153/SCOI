package Cat_Reportes;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import Conexiones_SQL.Generacion_Reportes;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Principal.Componentes;

@SuppressWarnings("serial")
public class Cat_Reporte_De_Diferencia_De_Recepcion_De_Transferencia extends JFrame{
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	JTextField txtFolio = new Componentes().text(new JTextField(), "Folio de la Rececion de Transferencia", 15, "String");
	JButton btnReporte_actual = new JButton("",new ImageIcon("imagen/plan-icono-5073-16.png"));
	JButton btnReporte_actual_ticket = new JButton("",new ImageIcon("imagen/plan-icono-5073-16.png"));
	int tipo_Reporte = 0;
	Obj_Usuario usuario = new Obj_Usuario().LeerSession();
	
	public Cat_Reporte_De_Diferencia_De_Recepcion_De_Transferencia(){
		setSize(425,220);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Reportes De Difereriencias De Recepcion De Transferencia");
		setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/presentar-una-pluma-para-escribir-icono-9594-32.png"));
		panel.setBorder(BorderFactory.createTitledBorder("Teclea El Folio De La Recepcion"));
		btnReporte_actual.setText(	"<html> <FONT FACE="+"arial"+" SIZE=3 COLOR=BLACk>" +
				"		<CENTER><p>Reporte De Diferiencia De Recepcion De Transferencia (Carta)</p></CENTER></FONT>" +
				"</html>");	
		
		btnReporte_actual_ticket.setText(	"<html> <FONT FACE="+"arial"+" SIZE=3 COLOR=BLACk>" +
				"		<CENTER><p>Reporte De Diferiencia De Recepcion De Transferencia (Ticket)</p></CENTER></FONT>" +
				"</html>");	
		panel.add(new JLabel("Folio Recepcion De Transferencia:")).setBounds(140,25,250,20);		
		panel.add(txtFolio).setBounds(120,50,200,20);
		panel.add(btnReporte_actual).setBounds(35,80,350,45);
		panel.add(btnReporte_actual_ticket).setBounds(35,135,350,45);
		cont.add(panel);
		btnReporte_actual.addActionListener(opGenerar);
		btnReporte_actual_ticket.addActionListener(opGenerar);
	}
	
	ActionListener opGenerar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String basedatos="2.200";
			String vista_previa_reporte="no";
			int vista_previa_de_ventana=0;
			String comando="exec sp_Reporte_De_Diferiencias_De_Recepciones_De_Transferencia_2 '"+String.valueOf(txtFolio.getText().toUpperCase().trim())+"','"+usuario.getNombre_completo()+"'"  ;
			String reporte = "";
			if(e.getActionCommand().equals("<html> <FONT FACE=arial SIZE=3 COLOR=BLACk>		<CENTER><p>Reporte De Diferiencia De Recepcion De Transferencia (Carta)</p></CENTER></FONT></html>")){
				reporte = "Obj_Reporte_IZAGAR_de_Diferiencias_De_Recepciones_De_Transferencia.jrxml";
			}else{
				reporte = "Obj_Reporte_IZAGAR_de_Diferiencias_De_Recepciones_De_Transferencia_Ticket.jrxml";
			}
	   		 if(!txtFolio.getText().equals("")){
							 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
		       }else{
		    	   JOptionPane.showMessageDialog(null,"Necesita Teclear Un Folio de Transferencia","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
		       }
		}
	};
	
	public static void main(String args[]){
		try{			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Reporte_De_Diferencia_De_Recepcion_De_Transferencia().setVisible(true);
		}catch(Exception e){	}	}
}
