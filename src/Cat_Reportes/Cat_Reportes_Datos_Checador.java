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
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.JCButton;

import com.toedter.calendar.JDateChooser;


@SuppressWarnings("serial")
public class Cat_Reportes_Datos_Checador extends JFrame {
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JDateChooser c_inicio = new JDateChooser();
	JDateChooser c_final = new JDateChooser();
	
	String establecimiento[] = new Obj_Establecimiento().Combo_Establecimiento();
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbEstablecimiento = new JComboBox(establecimiento);
	
	JCButton btn_tiempo_en_ruta = new JCButton  ("Tiempo En Ruta","reloj.png","Azul");
	JCButton btn_detalles_huella = new JCButton  ("Detalles De Huellas","huella_cargada_16.png","Azul");
	JCButton btn_status_checador = new JCButton  ("Status Checador","asistencia-comunitaria-icono-9465-16.png","Azul");
	
	JLabel JLBlinicio= new JLabel(new ImageIcon("Imagen/iniciar-icono-4628-16.png") );
	JLabel JLBfin= new JLabel(new ImageIcon("Imagen/acabado-icono-7912-16.png") );
	JLabel JLBestablecimiento= new JLabel(new ImageIcon("Imagen/folder-home-home-icone-5663-16.png") );
	JLabel JLBdepartamento= new JLabel(new ImageIcon("Imagen/departamento-icono-5365-16.png") );
	
	public Cat_Reportes_Datos_Checador(){
		this.setSize(510,210);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		 cargar_fechas();
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/arrow1_405291532.png"));
		this.setTitle("Reportes De Datos Para Checador");
		this.panel.setBorder(BorderFactory.createTitledBorder("Seleccione Los Paramentros Necesarios y De Click al Reporte Deseado"));
		
		int x=15,y=25,width=100,height=20;
		
		this.panel.add(new JLabel("Fecha Inicio:")).setBounds   (x      ,y    ,width  ,height);
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
		this.panel.add(btn_tiempo_en_ruta).setBounds             (x      ,y+=40,width  ,height);
		this.panel.add(btn_detalles_huella).setBounds       (x+=270 ,y,width  ,height);
		this.panel.add(btn_status_checador).setBounds             (x   ,y+=40, width ,height);
		x=15;width=320;
		
		
		this.cont.add(panel);
		btn_tiempo_en_ruta.addActionListener       (op_generar); 
		btn_status_checador.addActionListener       (op_generar);
		btn_detalles_huella.addActionListener (op_generar); 
		
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
			
				if(c_inicio.getDate().before(c_final.getDate())){
				   if( e.getActionCommand().equals("Tiempo En Ruta")){
					   reporte = "Obj_Reporte_De_Tiempo_En_Recorrido.jrxml";
					   comando = "exec reporte_de_entradas_y_salidas_ruta '"+fecha_inicio+"','"+fecha_final+"','"+Establecimiento+"'";
				   }	
				   
				   if( e.getActionCommand().equals("Detalles De Huellas")){
					   reporte ="Obj_Reporte_De_Colaboradores_Con_Opciones_De_Huella.jrxml";
					   comando="exec reporte_de_colaboradores_con_opciones_de_huella '"+Establecimiento+"','','',0";/*estab,depto,puesto,folio_emp*/
				   }
				   
				   if( e.getActionCommand().equals("Status Checador")){
					   reporte ="Obj_Status_Checador.jrxml";
					   comando="exec reporte_de_colaboradores_con_status_checador '"+Establecimiento+"'";
				   }	
			
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
			new Cat_Reportes_Datos_Checador().setVisible(true);
		}catch(Exception e){	}
	}

}
