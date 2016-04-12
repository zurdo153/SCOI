package Cat_Reportes;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.UIManager;




import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Generacion_Reportes;
import Obj_Administracion_del_Sistema.Obj_Usuario;

import com.toedter.calendar.JDateChooser;


@SuppressWarnings("serial")
public class Cat_Reportes_De_Venta_De_Tiempo_Aire extends JFrame {
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JDateChooser c_inicio = new JDateChooser();
	JDateChooser c_final = new JDateChooser();
	
	JButton btn_venta_ta 		= new JButton  ("Reporte De Venta De Tiempo Aire",new ImageIcon("imagen/diferiencia_de_sueldos_entre_listas_de_raya2_16.png"));
	
	JLabel JLBlinicio			= new JLabel(new ImageIcon("Imagen/iniciar-icono-4628-16.png") );
	JLabel JLBfin				= new JLabel(new ImageIcon("Imagen/acabado-icono-7912-16.png") );
	JLabel JLBestablecimiento	= new JLabel(new ImageIcon("Imagen/folder-home-home-icone-5663-16.png") );
	JLabel JLBdepartamento		= new JLabel(new ImageIcon("Imagen/departamento-icono-5365-16.png") );
	
	public Cat_Reportes_De_Venta_De_Tiempo_Aire(){
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/diferiencia_de_sueldos_entre_listas_de_raya2_64.png"));
		this.setTitle("Reportes De Venta De Tiempo Aire");
		
		this.panel.setBorder(BorderFactory.createTitledBorder("Reportes De Venta De Tiempo Aire"));
		
		int x=200,y=25,ancho=100;
		
		this.panel.add(new JLabel("Fecha Inicio:")).setBounds(15,y,ancho,20);
		this.panel.add(JLBlinicio).setBounds(75,y,20,20);
		this.panel.add(c_inicio).setBounds(95,y,100,20);
		
		this.panel.add(new JLabel("Fecha Final:")).setBounds(x+15,y,100,20);
		this.panel.add(JLBfin).setBounds(x+75,y,20,20);
		this.panel.add(c_final).setBounds(x+95,y,100,20);
		
		x=80;
		y=60;
		ancho=250;
	
		this.panel.add(btn_venta_ta 	   ).setBounds(x,y+=33,ancho,30);
		
		
		this.cont.add(panel);
		this.setSize(420,200);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		 cargar_fechas();
		

		 btn_venta_ta.addActionListener(op_generar);
	}
	
	public void cargar_fechas(){
		Date date1 = null;
				  try {
					date1 = new SimpleDateFormat("dd/MM/yyyy").parse(new BuscarSQL().fecha(7));
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		c_inicio.setDate(date1);
					
	    Date date2 = null;
					  try {
						date2 = new SimpleDateFormat("dd/MM/yyyy").parse(new BuscarSQL().fecha(0));
					} catch (ParseException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					}
		c_final.setDate(date2);
	};
	String basedatos="2.26";
	String vista_previa_reporte="no";
	int vista_previa_de_ventana=0;
	String comando="";
	String reporte = "";
	
	
	ActionListener op_generar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			
					if(validar_fechas().equals("")){
						
						if( c_inicio.getDate().before(c_final.getDate()) ){
							
							String fecha_inicio = new SimpleDateFormat("dd/MM/yyyy").format(c_inicio.getDate());
							String fecha_final = new SimpleDateFormat("dd/MM/yyyy").format(c_final.getDate());
							
							Reporte_de_Venta_de_TA(fecha_inicio,fecha_final);
							
						}else{
							JOptionPane.showMessageDialog(null, "Las Fechas Estan Invertidas","Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
			                  return;	
						}
 							
					}else{
						  JOptionPane.showMessageDialog(null, "Los Siguientes Campos Estan Vacios y Se Necesitan Para La Consulta:\n "+validar_fechas(),"Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
		                  return;
					}
			
		}
	};
	
	public void Reporte_de_Venta_de_TA(String fecha_inicio, String fecha_final){
		 reporte = "Obj_Reporte_De_venta_De_Tiempo_Aire.jrxml";
		 
		 comando = "exec sp_select_Reportes_De_Ventas_De_Tiempo_Aire '"+fecha_inicio+"','"+fecha_final+"','"+new Obj_Usuario().LeerSession().getNombre_completo()+"'";
		 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
	}
	
	public String validar_fechas(){
		String error = "";
		String fechainicioNull = c_inicio.getDate()+"";
		String fechafinalNull = c_final.getDate()+"";
	    if(fechainicioNull.equals("null"))error+= "Fecha  inicio\n";
		if(fechafinalNull.equals("null"))error+= "Fecha Final\n";
		return error;
	}
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Reportes_De_Venta_De_Tiempo_Aire().setVisible(true);
		}catch(Exception e){	}
	}

}
