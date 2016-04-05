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
	
	String establecimiento2[] = new Obj_Establecimiento().Combo_Establecimiento();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbEstablecimiento2 = new JComboBox(establecimiento);
	
	String status[] = {"Seleccione un Estatus","Vigente","Vacaciones","Incapacidad","Baja","No Contratable","Provisional Checador"};
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbStatus = new JComboBox(status);
	
	Border blackline, etched, raisedbevel, loweredbevel, empty;
	JLabel lblPlantillaHorarioColaboradores = new JLabel();
	JLabel lblPuestosColaboradores = new JLabel();
	JLabel lblStatusColaboradores = new JLabel();
	
	
	JButton btngenerar = new JButton("Plantilla De Horario",new ImageIcon("imagen/buscar.png"));
	JButton btngenerarplantilla = new JButton("Plantilla Base de Puestos",new ImageIcon("imagen/buscar.png"));
	JButton btnReporteColaboradores = new JButton("Personal Por Estatus",new ImageIcon("imagen/buscar.png"));
	public Cat_Personal_Con_Horario(){
	
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/plan-icono-5073-16.png"));
		this.setTitle("Reportes de Colaboradores.");
		
		Container cont = getContentPane();
		JLayeredPane panel = new JLayeredPane();
		
		blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
		
		;
		this.lblPlantillaHorarioColaboradores.setBorder(BorderFactory.createTitledBorder(blackline,"Colaboradores por Horario"));
		this.lblPuestosColaboradores.setBorder(BorderFactory.createTitledBorder(blackline,"Colaboradores Base De Puestos Por Establecimiento"));
		this.lblStatusColaboradores.setBorder(BorderFactory.createTitledBorder(blackline,"Colaboradores por Estatus"));
		panel.setBorder(BorderFactory.createTitledBorder("Seleccione su Tipo de Reporte :"));
		
		
		
		panel.add(lblPlantillaHorarioColaboradores).setBounds(13, 20, 270,95);
		panel.add(cmbEstablecimiento).setBounds(40,40,220,20);
		panel.add(btngenerar).setBounds(40,80,220,20);
		panel.add(cmbEstablecimiento2).setBounds(40,140,220,20);
		panel.add(lblPuestosColaboradores).setBounds(13, 120, 270, 95);
		panel.add(btngenerarplantilla).setBounds(50, 180, 200, 20);
		panel.add(lblStatusColaboradores).setBounds(13, 220, 270, 95);
		panel.add(cmbStatus).setBounds(40,240,220,20);
		panel.add(btnReporteColaboradores).setBounds(50,280,200,20);
		
		cont.add(panel);
		
		btngenerar.addActionListener(opGenerar);
		btngenerarplantilla.addActionListener(opGenerar_Plantilla);
		btnReporteColaboradores.addActionListener(opGenerarreporte2);
		
		this.setSize(300, 350);
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
	
	ActionListener opGenerar_Plantilla = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String basedatos="2.26";
			String vista_previa_reporte="no";
			int vista_previa_de_ventana=0;
			String comando="exec sp_Reporte_de_Plantilla_de_Puesto_Por_Establecimiento '"+cmbEstablecimiento2.getSelectedItem()+"';";
			String reporte = "Obj_Reporte_De_Plantilla_Por_Establecimiento.jrxml";
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
