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
public class Cat_Reporte_De_Revision_De_Cortes extends JFrame {
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JDateChooser c_inicio = new JDateChooser();
	JDateChooser c_final = new JDateChooser();
	
	JButton btn_todos 						= new JButton  ("Todos",new ImageIcon("imagen/proceso-para-los-usuarios-icono-5903-16.png"));
	JButton btn_revisados_por_auditoria 	= new JButton  ("Revisados por auditoria",new ImageIcon("imagen/apoyo-y-asistencia-icono-6525-16.png"));
	JButton btn_sin_revisar_por_auditoria	= new JButton  ("Sin revisar por auditoria",new ImageIcon("imagen/asistencia-comunitaria-icono-9465-16.png"));
	JButton btn_auditoria_paso_a_cobro 		= new JButton  ("Auditoria paso a cobro",new ImageIcon("imagen/apoyo-y-asistencia-icono-6525-16.png"));
	JButton btn_auditoria_paso_a_seguridad 	= new JButton  ("Auditoria paso a seguridad",new ImageIcon("imagen/asistencia-comunitaria-icono-9465-16.png"));
	
	JLabel JLBlinicio			= new JLabel(new ImageIcon("Imagen/iniciar-icono-4628-16.png") );
	JLabel JLBfin				= new JLabel(new ImageIcon("Imagen/acabado-icono-7912-16.png") );
	JLabel JLBestablecimiento	= new JLabel(new ImageIcon("Imagen/folder-home-home-icone-5663-16.png") );
	JLabel JLBdepartamento		= new JLabel(new ImageIcon("Imagen/departamento-icono-5365-16.png") );
	
	public Cat_Reporte_De_Revision_De_Cortes(){
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/asistencia-comunitaria-icono-9465-32.png"));
		this.setTitle("Reportes De Revision De Cortes");
		
		this.panel.setBorder(BorderFactory.createTitledBorder("Reportes De Revision De Cortes"));
		
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
	
		this.panel.add(btn_todos 						).setBounds(x,y,ancho,30);
		this.panel.add(btn_revisados_por_auditoria 		).setBounds(x,y+=33,ancho,30);
		this.panel.add(btn_sin_revisar_por_auditoria	).setBounds(x,y+=33,ancho,30);
		this.panel.add(btn_auditoria_paso_a_cobro 		).setBounds(x,y+=33,ancho,30);
		this.panel.add(btn_auditoria_paso_a_seguridad 	).setBounds(x,y+=33,ancho,30);
		
		this.cont.add(panel);
		this.setSize(420,270);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		 cargar_fechas();
		

		 btn_todos.addActionListener(op_generar);
		 btn_revisados_por_auditoria.addActionListener(op_generar);
		 btn_sin_revisar_por_auditoria.addActionListener(op_generar);
		 btn_auditoria_paso_a_cobro.addActionListener(op_generar);
		 btn_auditoria_paso_a_seguridad.addActionListener(op_generar);
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
			
			
			
			if(e.getActionCommand().toString().trim().toUpperCase().equals("AUDITORIA PASO A SEGURIDAD")){
				cargar_fechas();
				
				String fecha_final = new SimpleDateFormat("dd/MM/yyyy").format(c_final.getDate());
				
				Reporte_de_Asistencia_completo("01/01/1900",fecha_final,e.getActionCommand().toString().trim().toUpperCase());
			}else{
					if(validar_fechas().equals("")){
						
						String fecha_inicio = new SimpleDateFormat("dd/MM/yyyy").format(c_inicio.getDate());
						String fecha_final = new SimpleDateFormat("dd/MM/yyyy").format(c_final.getDate());
	
						
								if(c_inicio.getDate().before(c_final.getDate())){
										Reporte_de_Asistencia_completo(fecha_inicio,fecha_final,e.getActionCommand().toString().trim().toUpperCase());
								}else{
									  JOptionPane.showMessageDialog(null, "El Rango De Fechas Esta Invertido","Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
					                  return;
								}
					}else{
						  JOptionPane.showMessageDialog(null, "Los Siguientes Campos Estan Vacios y Se Necesitan Para La Consulta:\n "+validar_fechas(),"Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
		                  return;
					}
			}
			
		}
	};
	
	public void Reporte_de_Asistencia_completo(String fecha_inicio, String fecha_final, String tipo_de_reporte){
//		+new Obj_Usuario().getFolio()+
		 reporte = "Obj_Reporte_De_Revision_De_Cortes.jrxml";
		 comando = "exec sp_Reporte_De_Revision_De_Cortes '"+fecha_inicio+"','"+fecha_final+"','"+tipo_de_reporte+"','"+1+"'";
		 System.out.println(comando);
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
			new Cat_Reporte_De_Revision_De_Cortes().setVisible(true);
		}catch(Exception e){	}
	}

}
