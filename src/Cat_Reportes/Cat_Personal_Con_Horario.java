package Cat_Reportes;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;

import Conexiones_SQL.Generacion_Reportes;
import Obj_Lista_de_Raya.Obj_Establecimiento;

@SuppressWarnings("serial")
public class Cat_Personal_Con_Horario extends JFrame{

	String establecimiento[] = new Obj_Establecimiento().Combo_Establecimiento();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbEstablecimiento = new JComboBox(establecimiento);
	
	String status[] = {"Seleccione un Estatus","Vigente","Vacaciones","Incapacidad","Baja","No Contratable","Provisional Checador"};
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbStatus = new JComboBox(status);
	
	Border blackline, etched, raisedbevel, loweredbevel, empty;
	JLabel lblPlantillaColaboradores = new JLabel();
	JLabel lblColaboradores = new JLabel();
	
	JButton btngenerar = new JButton("Plantilla de Personal con Horario",new ImageIcon("imagen/buscar.png"));
	JButton btnReporteColaboradores = new JButton("Reporte de Colaboradores",new ImageIcon("imagen/buscar.png"));
	public Cat_Personal_Con_Horario(){
	
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/plan-icono-5073-16.png"));
		this.setTitle("Reporte de Colaboradores.");
		
		Container cont = getContentPane();
		JLayeredPane panel = new JLayeredPane();
		
		blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
		this.lblColaboradores.setBorder(BorderFactory.createTitledBorder(blackline,"Reporte de Colaboradores por Estatus"));
		this.lblPlantillaColaboradores.setBorder(BorderFactory.createTitledBorder(blackline,"Datos Plantilla Con Horario"));
		panel.setBorder(BorderFactory.createTitledBorder("Plantilla de Personal Por Establecimiento"));
		
		
		
		panel.add(lblPlantillaColaboradores).setBounds(13, 20, 270, 95);
		panel.add(cmbEstablecimiento).setBounds(40,40,220,20);
		panel.add(btngenerar).setBounds(43,70,220,20);
		panel.add(lblColaboradores).setBounds(13, 120, 270, 95);
		panel.add(cmbStatus).setBounds(40,140,220,20);
		panel.add(btnReporteColaboradores).setBounds(53,180,200,20);
		
		cont.add(panel);
		
		btngenerar.addActionListener(opGenerar);
		btnReporteColaboradores.addActionListener(opGenerarreporte2);
		this.setSize(300, 250);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	ActionListener opGenerar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String basedatos="2.26";
			String vista_previa_reporte="no";
			int vista_previa_de_ventana=0;
			String comando="exec sp_Reporte_de_Plantilla_de_Personal_con_Horario '"+cmbEstablecimiento.getSelectedItem()+"';";
			String reporte = "Obj_Reporte_De_Empleados_Plantilla_De_Horario_De_Personal_Por_Establecimiento.jrxml";
					 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
			}
	};
	
	ActionListener opGenerarreporte2 = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String basedatos="2.26";
			String vista_previa_reporte="no";
			int vista_previa_de_ventana=0;
			String comando="exec sp_select_reporte_empleados_activos "+cmbStatus.getSelectedIndex();
			String reporte = "Obj_Reporte_Status_de_Colaboradores.jrxml";
					 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
			}
	};
	public static void main(String [] arg){
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new Cat_Personal_Con_Horario().setVisible(true);
	}
}
