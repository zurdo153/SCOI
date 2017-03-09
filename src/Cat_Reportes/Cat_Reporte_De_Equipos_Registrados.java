package Cat_Reportes;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.UIManager;

import Conexiones_SQL.Cargar_Combo;
import Conexiones_SQL.Generacion_Reportes;
import Obj_Lista_de_Raya.Obj_Departamento;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.JCButton;

@SuppressWarnings("serial")
public class Cat_Reporte_De_Equipos_Registrados extends JFrame{

	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	String[] establecimiento = new Obj_Establecimiento().Combo_Establecimiento();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbEstablecimiento = new JComboBox(establecimiento);
	
	String[] departamento = new Obj_Departamento().Combo_Departamento();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbDepartamento = new JComboBox(departamento);
	
	String[] equipos = new Cargar_Combo().tipo_de_equipo();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbEquipo = new JComboBox(equipos);
	
	JCButton btnGenerar = new JCButton("Generar", "lista.png", "Azul");
	
	public Cat_Reporte_De_Equipos_Registrados() {
		this.setTitle("Consunta De Equipos Registrados");
		
		int x = 20,y=15,ancho=80;
		
		panel.add(new JLabel("Establecimiento")).setBounds(x,y,ancho+30,20);
		panel.add(cmbEstablecimiento).setBounds(x+ancho+30, y, ancho*3, 20);
		panel.add(new JLabel("Departamento")).setBounds(x,y+=25,ancho+30,20);
		panel.add(cmbDepartamento).setBounds(x+ancho+30, y, ancho*3, 20);
		panel.add(new JLabel("Equipo")).setBounds(x,y+=25,ancho+30,20);
		panel.add(cmbEquipo).setBounds(x+ancho+30, y, ancho*3, 20);
		
		panel.add(btnGenerar).setBounds(x+ancho+30, y+=35, ancho*3, 40);
		
		cont.add(panel);
		
		btnGenerar.addActionListener(opGenerar);
		
		this.setResizable(false);
		this.setSize(400,180);
		this.setLocationRelativeTo(null);
		this.getDefaultCloseOperation();
	}
	
	ActionListener opGenerar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			String basedatos="2.26";
			String vista_previa_reporte="no";
			int vista_previa_de_ventana=0;
			
			String comando="exec sp_reporte_de_control_de_equipos_registrados '"+cmbEstablecimiento.getSelectedItem().toString().trim()+"','"+cmbDepartamento.getSelectedItem().toString().trim()+"','"+cmbEquipo.getSelectedItem().toString().trim()+"'";
			String reporte = "Obj_Reporte_De_Activos.jrxml";
					 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
		}
	};

	public static void main(String[] args) {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Reporte_De_Equipos_Registrados().setVisible(true);
		}catch(Exception e){	}
	}

}
