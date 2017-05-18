package Cat_Reportes;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import Cat_Lista_de_Raya.Cat_Filtro_De_Listas_De_Raya_Pasadas;
import Conexiones_SQL.Generacion_Reportes;
import Obj_Principal.JCButton;

@SuppressWarnings("serial")
public class Cat_Reportes_De_Prestamos_De_Lista_De_Raya extends JFrame {
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	String operador[] = {"Selecciona Un Reporte"
							,"Reporte De Prestamos Por Establecimiento de Lista de Raya Actual"
							,"Reporte De Prestamos Por Establecimiento de Listas de Raya Pasadas"
							,"Reporte De Prestamos Para Exportar Por Empleado de Lista de Raya Actual" 
							,"Reporte De Prestamos Por Establecimiento de Lista de Raya Actual Por Estatus"
							};
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbConcepto = new JComboBox(operador);
	
	JCButton btngenerar_reporte = new JCButton("Generar Reporte En Pantalla","hoja-de-calculo-icono-8865-32.png","Azul");
	
	JLabel JLBlinicio			= new JLabel(new ImageIcon("Imagen/iniciar-icono-4628-16.png") );
	JLabel JLBfin				= new JLabel(new ImageIcon("Imagen/acabado-icono-7912-16.png") );
	JLabel JLBestablecimiento	= new JLabel(new ImageIcon("Imagen/folder-home-home-icone-5663-16.png") );
	JLabel JLBdepartamento		= new JLabel(new ImageIcon("Imagen/departamento-icono-5365-16.png") );
	
	public Cat_Reportes_De_Prestamos_De_Lista_De_Raya(){
		this.setSize(470,150);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("Reportes De Prestamos");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/articulo-icono-9036-48.png"));
		this.panel.setBorder(BorderFactory.createTitledBorder("Seleccione  La Fecha, El Tipo de Reporte, el Establecimiento y Genere El Reporte "));
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Report.png"));

		int x=20, y=25, width=100,height=20;
		x=20;width=420;
		this.panel.add(cmbConcepto).setBounds                 (x     ,y      ,width   ,height   );
		this.panel.add(btngenerar_reporte).setBounds          (x+70  ,y+=40   ,300   ,height*2 );
		this.cont.add(panel);
		
		btngenerar_reporte.addActionListener(opGenerar_reporte);
	}
	
	ActionListener opGenerar_reporte = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
					String basedatos="2.98";
					String vista_previa_reporte="no";
					int vista_previa_de_ventana=0;
					String comando= "";
					String reporte = "";
			
			 if(cmbConcepto.getSelectedIndex()==0){
			       JOptionPane.showMessageDialog(null,"Debe de Seleccionar Un Tipo De Reporte","Aviso!", JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
			        cmbConcepto.requestFocus();
			        cmbConcepto.showPopup();
				    return;		
			      }else{ 
							 String concepto=cmbConcepto.getSelectedItem().toString().trim();
							 
								if(concepto.equals("Reporte De Prestamos Para Exportar Por Empleado de Lista de Raya Actual" )){
										comando="exec sp_Reporte_De_Prestamos_De_Lista_De_Raya_Actual";
										reporte ="Obj_Reporte_De_Prestamos_De_Lista_De_Raya_Actual_Para_Exportar.jrxml";
								   }
								
								if(concepto.equals("Reporte De Prestamos Por Establecimiento de Lista de Raya Actual")){
									comando="exec sp_Reporte_De_Prestamos_De_Lista_De_Raya_Actual";
									reporte ="Obj_Reporte_De_Prestamos_De_Lista_De_Raya_Actual.jrxml";
							   }
								
								if(concepto.equals("Reporte De Prestamos Por Establecimiento de Lista de Raya Actual Por Estatus")){
									comando="sp_Reporte_De_Prestamos_De_Lista_De_Raya_Actual_Por_Estatus";
									reporte ="Obj_Reporte_De_Prestamos_De_Lista_De_Raya_Actual_Por_Estatus.jrxml";
							   }
								
								if(concepto.equals("Reporte De Prestamos Por Establecimiento de Listas de Raya Pasadas"	)){
								    new Cat_Filtro_De_Listas_De_Raya_Pasadas(2).setVisible(true);   
								    return;
							   }
								
		       }
			    new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
			   return;
		}
	};
	
		public void Impresion_de_Reporte_Prestamos_LRPasadas(int folio) {
			String basedatos="2.98";
			String vista_previa_reporte="no";
			int vista_previa_de_ventana=0;
			String comando = "exec sp_consulta_de_prestamos_de_listas_de_raya_pasadas '"+folio+"';";
			String reporte ="Obj_Reporte_De_Prestamos_De_Listas_De_Raya_Pasadas.jrxml";
		    new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
		 }

	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Reportes_De_Prestamos_De_Lista_De_Raya().setVisible(true);
		}catch(Exception e){	}
	}
}
