package Cat_Reportes;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.UIManager;

import Conexiones_SQL.Generacion_Reportes;
import Obj_Principal.JCButton;


@SuppressWarnings("serial")
public class Cat_Reporte_De_Cumpleanios_Del_Mes extends JDialog{
	 
		Container cont = getContentPane();
		JLayeredPane panel = new JLayeredPane();
		
		String Meses[] = {"Seleciona Un Mes","Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre","Todo El Año"};
		@SuppressWarnings({ "rawtypes", "unchecked" })
		JComboBox cmbMeses = new JComboBox(Meses);
		
		JCButton btngenerar = new JCButton("Generar","buscar.png","Azul");
		
		public Cat_Reporte_De_Cumpleanios_Del_Mes(){
			
			panel.setBorder(BorderFactory.createTitledBorder("Reporte De Cumpleaños"));
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/cookies-tarta-de-cumpleanos-icono-9840-16.png"));
			this.setTitle("RePorte De Cumpleaños Por Mes");
					
			panel.add(new JLabel("Seleciona El Mes:")).setBounds(15,35,110,20);
			panel.add(cmbMeses).setBounds(100,35,120,20);
			panel.add(btngenerar).setBounds(60, 70, 130, 32);
			cont.add(panel);
			
			btngenerar.addActionListener(opGenerar);
			
			this.setSize(250, 140);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		}
		
		ActionListener opGenerar = new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String basedatos="2.26";
				String vista_previa_reporte="no";
				int vista_previa_de_ventana=0;
				String comando="exec sp_Reporte_De_Cumpleanios_del_Mes "+cmbMeses.getSelectedIndex();
				String reporte = "Obj_Reporte_De_Empleados_Cumpleaños_Del_Mes.jrxml";
						 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
			}
		};

	
		public static void main(String args[]){
			try{
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				new Cat_Reporte_De_Cumpleanios_Del_Mes().setVisible(true);
			}catch(Exception e){	}
		}

	}

