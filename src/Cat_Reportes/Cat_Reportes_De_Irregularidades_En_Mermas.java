package Cat_Reportes;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.ParsePosition;
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
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.JCButton;

import com.toedter.calendar.JDateChooser;


@SuppressWarnings("serial")
public class Cat_Reportes_De_Irregularidades_En_Mermas extends JFrame {
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JDateChooser c_inicio = new JDateChooser();
	JDateChooser c_final = new JDateChooser();
	
	String establecimiento[] = new Obj_Establecimiento().Combo_Establecimiento();
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbEstablecimiento = new JComboBox(establecimiento);
	
	JCButton btnGenerarReporte = new JCButton  ("Generar Reporte","Report.png","Azul");
	
	JLabel JLBlinicio= new JLabel(new ImageIcon("Imagen/iniciar-icono-4628-16.png") );
	JLabel JLBfin= new JLabel(new ImageIcon("Imagen/acabado-icono-7912-16.png") );
	JLabel JLBestablecimiento= new JLabel(new ImageIcon("Imagen/folder-home-home-icone-5663-16.png") );
	JLabel JLBdepartamento= new JLabel(new ImageIcon("Imagen/departamento-icono-5365-16.png") );
	
	public Cat_Reportes_De_Irregularidades_En_Mermas(){
		this.setSize(510,165);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		 cargar_fechas();
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/arrow1_405291532.png"));
		this.setTitle("Reportes De Irregularidades En Mermas");
		this.panel.setBorder(BorderFactory.createTitledBorder("Seleccione Los Paramentros Necesarios y De Click al Reporte Deseado"));
		
		int x=15,y=25,width=100,height=20;
		
		this.panel.add(new JLabel("Fecha Inicio:")).setBounds   (x      ,y		,width  ,height);
		this.panel.add(JLBlinicio).setBounds                    (x+=60  ,y    ,height ,height);
		this.panel.add(c_inicio).setBounds                      (x+=20  ,y    ,width  ,height);
		
	    this.panel.add(new JLabel("Establecimiento:")).setBounds(x+=120 ,y    ,width  ,height);
	    this.panel.add(JLBestablecimiento).setBounds            (x+=75  ,y    ,height ,height);
		this.panel.add(cmbEstablecimiento).setBounds            (x+=25  ,y    ,170    ,height);
		
		 x=15;
		this.panel.add(new JLabel("Fecha Final:")).setBounds    (x      ,y+=25,width  ,height);
		this.panel.add(JLBfin).setBounds                        (x+=60  ,y    ,height ,height);
		this.panel.add(c_final).setBounds                       (x+=20  ,y    ,width  ,height);
		
		x=15;width=200;height=25;
		this.panel.add(btnGenerarReporte).setBounds             (x+150      ,y+=40,width  ,30);
		x=15;width=320;
		
		this.cont.add(panel);
		btnGenerarReporte.addActionListener       (op_generar); 
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
	
	Obj_Usuario usuario = new Obj_Usuario().LeerSession();
	
	String basedatos="2.26";
	String vista_previa_reporte="no";
	int vista_previa_de_ventana=0;
	String comando="";
	String reporte = "";
	
	ActionListener op_generar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(validar_fechas().equals("")){
				String fecha_inicio = new SimpleDateFormat("dd/MM/yyyy").format(c_inicio.getDate())+" 00:00:00";
				String fecha_final = new SimpleDateFormat("dd/MM/yyyy").format(c_final.getDate())+" 23:59:58";
				String Establecimiento = cmbEstablecimiento.getSelectedItem().toString();
				
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm"); 
				Date fecha1 = sdf.parse(fecha_inicio , new ParsePosition(0));
				Date fecha2 = sdf.parse(fecha_final , new ParsePosition(0));
				
			
				if(fecha1.before(fecha2)){
					   reporte = "Obj_Reporte_De_Mermas_Para_Deteccion_De_Irregularidades.jrxml";
					   comando = "exec sp_reporte_de_mermas_con_irregularidades '"+fecha_inicio+"','"+fecha_final+"','"+Establecimiento+"'";
				   
				   new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
				   return;
				}else{
					  JOptionPane.showMessageDialog(null, "El Rango De Fechas Esta Invertido","Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
                      return;
				}
			}else{
				  JOptionPane.showMessageDialog(null, "Los Siguientes Campos Estan Vacios y Se Necesitan Para La Consulta:\n "+validar_fechas(),"Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
                  return;
			}
		}
	};
	
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
			new Cat_Reportes_De_Irregularidades_En_Mermas().setVisible(true);
		}catch(Exception e){	}
	}

}
