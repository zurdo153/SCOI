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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Generacion_Reportes;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.JCButton;

import com.toedter.calendar.JDateChooser;

@SuppressWarnings("serial")
public class Cat_Reportes_De_80_20 extends JFrame {
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JDateChooser c_inicio = new JDateChooser();
	JDateChooser c_final = new JDateChooser();
	
	String[] establecimiento = new Obj_Establecimiento().Combo_Establecimiento201();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbEstablecimiento = new JComboBox(establecimiento);
	
	String operador[] = {"Selecciona Un Reporte"
							,"Reporte De Agotados Por Establecimiento En Una Fecha" 
							,"Reporte De Agotados Promedios Por Establecimiento De Una Semana"
							,"Reporte 80/20 Por Establecimiento General"							
							};
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbConcepto = new JComboBox(operador);
	
	JCButton btngenerar_reporte = new JCButton("Generar Reporte En Pantalla","hoja-de-calculo-icono-8865-32.png","Azul");
	
	JLabel JLBlinicio			= new JLabel(new ImageIcon("Imagen/iniciar-icono-4628-16.png") );
	JLabel JLBfin				= new JLabel(new ImageIcon("Imagen/acabado-icono-7912-16.png") );
	JLabel JLBestablecimiento	= new JLabel(new ImageIcon("Imagen/folder-home-home-icone-5663-16.png") );
	JLabel JLBdepartamento		= new JLabel(new ImageIcon("Imagen/departamento-icono-5365-16.png") );
	
	public Cat_Reportes_De_80_20(){
		this.setSize(430,250);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("Reportes De Agotados");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/articulo-icono-9036-48.png"));
		this.panel.setBorder(BorderFactory.createTitledBorder("Seleccione  La Fecha, El Tipo de Reporte, el Establecimiento y Genere El Reporte "));
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Report.png"));

		int x=20, y=25, width=100,height=20;
		x=20;width=380;
		this.panel.add(cmbConcepto).setBounds                 (x     ,y      ,width   ,height   );
		width=100;
		this.panel.add(new JLabel("Fecha:")).setBounds        (x     ,y+=35  ,width   ,height    );
		this.panel.add(JLBlinicio).setBounds                  (x+=55 ,y      ,height  ,height    );
		this.panel.add(c_inicio).setBounds                    (x+=20 ,y      ,width   ,height    );
		this.panel.add(cmbEstablecimiento).setBounds          (x+=120,y      ,width+88,height    );

		x=70;width=300;
		this.panel.add(btngenerar_reporte).setBounds          (x    ,y+=50   ,width   ,height*2 );
		this.cont.add(panel);
		
		c_inicio.setDate( cargar_fechas(0));
		btngenerar_reporte.addActionListener(opGenerar_reporte);
	}
	
	public Date cargar_fechas(Integer dias){
		Date date1 = null;
				  try {
					date1 = new SimpleDateFormat("dd/MM/yyyy").parse(new BuscarSQL().fecha(dias));
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		return date1;
	};
	
	public String validar_campos(){
		String error = "";
		String fechainicioNull = c_inicio.getDate()+"";
	    if(fechainicioNull.equals("null"))error+= "Fecha  inicio\n";
		if(cmbEstablecimiento.getSelectedIndex()==0)error+= "Seleccionar Un Establecimiento\n";
		return error;
	}
	
	ActionListener opGenerar_reporte = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
					String basedatos="2.94";
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
						if(validar_campos().equals("")){
							 String concepto=cmbConcepto.getSelectedItem().toString().trim();
							 String fecha_inicio = new SimpleDateFormat("dd/MM/yyyy").format(c_inicio.getDate())+" 00:00:00";
							 
//							  String fecha_final  = new SimpleDateFormat("dd/MM/yyyy").format(c_final.getDate())+"  23:59:00";
//							  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm"); 
//							  Date fecha1 = sdf.parse(fecha_inicio , new ParsePosition(0));
//							  Date fecha2 = sdf.parse(fecha_final , new ParsePosition(0));
//							  if(fecha1.before(fecha2)){
//							 
//						      }else{
//						        JOptionPane.showMessageDialog(null,"El Rango de Fechas Esta Invertido","Aviso!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
//							     return;
//							  }
								  
								if(concepto.equals("Reporte De Agotados Por Establecimiento En Una Fecha")){
										comando="reporte_de_agotados_y_proximos_agotar_por_clasificacion_meta_por_establecimiento '"+cmbEstablecimiento.getSelectedItem().toString().trim()+"','"+fecha_inicio.substring(0, 10)+"'";
										reporte ="Obj_Reporte_De_Agotados_y_Proximos_Agotar_por_Meta_y_Establecimiento.jrxml";
								   }
								
								if(concepto.equals("Reporte 80/20 Por Establecimiento General")){
									comando="exec reporte_80_20_venta_con_impuesto_por_establecimiento_scoi '"+cmbEstablecimiento.getSelectedItem().toString().trim()+"','"+fecha_inicio.substring(0, 10)+"'";
									reporte ="Obj_Reporte_De_Venta_80_20_Por_Establecimiento.jrxml";
							   }
								if(concepto.equals("Reporte De Agotados Promedios Por Establecimiento De Una Semana")){
									comando="exec reporte_de_agotados_porcentaje_de_la_semana_del_anio_por_establecimiento '"+cmbEstablecimiento.getSelectedItem().toString().trim()+"','"+fecha_inicio.substring(0, 10)+"'";
									reporte ="Obj_Reporte_De_Agotados_y_Proximos_Agotar_Por_Meta_y_Establecimiento_Promedios.jrxml";
							   }
							
						}else{
						  JOptionPane.showMessageDialog(null, "Los Siguientes Campos Estan Vacios y Se Necesitan Para La Consulta:\n "+validar_campos(),"Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
			               return;
						} 
		       }
			    new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
			   return;
		}
	};
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Reportes_De_80_20().setVisible(true);
		}catch(Exception e){	}
	}

}
