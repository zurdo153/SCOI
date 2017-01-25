package Cat_Reportes;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLayeredPane;
import javax.swing.UIManager;

import Conexiones_SQL.Generacion_Reportes;
import Obj_Lista_de_Raya.Obj_Establecimiento;

@SuppressWarnings("serial")
public class Cat_Reporte_De_Plantilla_De_Puestos_Por_Establecimiento extends JDialog{

	String establecimiento[] = new Obj_Establecimiento().Combo_Establecimiento();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbEstablecimiento = new JComboBox(establecimiento);
	
	JButton btngenerar = new JButton("Generar");
	
	public Cat_Reporte_De_Plantilla_De_Puestos_Por_Establecimiento(){
		this.setModal(true);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Contacts-icon.png"));
		this.setTitle("Reporte de Plantilla");
		
		Container cont = getContentPane();
		JLayeredPane panel = new JLayeredPane();
		
		panel.setBorder(BorderFactory.createTitledBorder("Plantilla De Puestos Por Establecimiento"));
		
		panel.add(cmbEstablecimiento).setBounds(10, 50, 220, 20);
		panel.add(btngenerar).setBounds(70, 120, 100, 20);
		
		cont.add(panel);
		
		btngenerar.addActionListener(opGenerar);
		
		this.setSize(250, 200);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	ActionListener opGenerar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
				String basedatos="2.26";
				String vista_previa_reporte="no";
				int vista_previa_de_ventana=0;
				String comando="exec sp_Reporte_de_Plantilla_de_Puesto_Por_Establecimiento '"+cmbEstablecimiento.getSelectedItem()+"';";
				String reporte = "Obj_Reporte_De_Plantilla_Por_Establecimiento.jrxml";
						 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
			}
	};
	
	public static void main(String [] arg){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Reporte_De_Plantilla_De_Puestos_Por_Establecimiento().setVisible(true);
		}catch(Exception e){
			System.err.println("Error :"+ e.getMessage());
		}
	}
}
