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
import javax.swing.JLayeredPane;
import Conexiones_SQL.Generacion_Reportes;
import Obj_Lista_de_Raya.Obj_Establecimiento;

@SuppressWarnings("serial")
public class Cat_Personal_Con_Horario extends JFrame{

	String establecimiento[] = new Obj_Establecimiento().Combo_Establecimiento();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbEstablecimiento = new JComboBox(establecimiento);
	
	JButton btngenerar = new JButton("Generar Horario",new ImageIcon("imagen/buscar.png"));
	JButton btngenerarplantilla = new JButton("Generar Plantilla",new ImageIcon("imagen/buscar.png"));
	
	public Cat_Personal_Con_Horario(){
	
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/plan-icono-5073-16.png"));
		this.setTitle("Reporte de Plantilla");
		
		Container cont = getContentPane();
		JLayeredPane panel = new JLayeredPane();
		
		panel.setBorder(BorderFactory.createTitledBorder("Plantilla de Personal Por Establecimiento"));
		
		panel.add(cmbEstablecimiento).setBounds(10, 50, 220, 20);
		panel.add(btngenerar).setBounds(20, 100, 200, 20);
		panel.add(btngenerarplantilla).setBounds(20, 140, 200, 20);
		
		cont.add(panel);
		
		btngenerar.addActionListener(opGenerar);
		btngenerarplantilla.addActionListener(opGenerar_Plantilla);
		
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
			String comando="exec sp_Reporte_de_Plantilla_de_Puesto_Por_Establecimiento '"+cmbEstablecimiento.getSelectedItem()+"';";
			String reporte = "Obj_Reporte_De_Plantilla_Por_Establecimiento.jrxml";
					 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
			}
	};
	
	public static void main(String [] arg){
		new Cat_Personal_Con_Horario().setVisible(true);
	}
}
