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
	
	String tipoDeReprote[] = {"Selecciona Un Tipo De Reporte","Tiempo En Ruta","Detalles De Huellas","Status Checador"};
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbTipoDeReporte = new JComboBox(tipoDeReprote);
	
	String statusChecador[] = {"Selecciona Un Status","Normal","Libre","Checador Bloqueado","Exclusivo Ruta"};
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbStatusChecador = new JComboBox(statusChecador);
	
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
	
	public Cat_Reportes_Datos_Checador(){
		this.setSize(510,210);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		 cargar_fechas();
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/arrow1_405291532.png"));
		this.setTitle("Reportes De Datos Para Checador");
		this.panel.setBorder(BorderFactory.createTitledBorder("Seleccione Los Paramentros Necesarios y De Click al Reporte Deseado"));
		
		int x=15,y=25,width=100,height=20;
		
		this.panel.add(cmbTipoDeReporte).setBounds            (x+40  ,y    ,width*4 ,30);
		
		this.panel.add(new JLabel("Fecha Inicio:")).setBounds   (x      ,y+=45,width  ,height);
		this.panel.add(JLBlinicio).setBounds                    (x+=60  ,y    ,height ,height);
		this.panel.add(c_inicio).setBounds                      (x+=20  ,y    ,width  ,height);
		
	    this.panel.add(new JLabel("Establecimiento:")).setBounds(x+=120 ,y    ,width  ,height);
	    this.panel.add(JLBestablecimiento).setBounds            (x+=75  ,y    ,height ,height);
		this.panel.add(cmbEstablecimiento).setBounds            (x+=25  ,y    ,170    ,height);
		
		 x=15;
		this.panel.add(new JLabel("Fecha Final:")).setBounds    (x      ,y+=25,width  ,height);
		this.panel.add(JLBfin).setBounds                        (x+=60  ,y    ,height ,height);
		this.panel.add(c_final).setBounds                       (x+=20  ,y    ,width  ,height);
		
		this.panel.add(new JLabel("Status Checador:")).setBounds(x+=120 ,y    ,width  ,height);
//	    this.panel.add(JLBestablecimiento).setBounds            (x+=75  ,y    ,height ,height);
		this.panel.add(cmbStatusChecador).setBounds            (x+=100  ,y    ,170    ,height);

		x=15;width=200;height=25;
		this.panel.add(btnGenerarReporte).setBounds             (x+150      ,y+=40,width  ,30);
		x=15;width=320;
		
		cmb();
		
		this.cont.add(panel);
		btnGenerarReporte.addActionListener       (op_generar); 
		cmbTipoDeReporte.addActionListener(opCmb);
		
	}
	
	ActionListener opCmb = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			cmb();
		}
	};
	
	public void cmb(){
		switch(cmbTipoDeReporte.getSelectedItem().toString().trim()){
			case "Selecciona Un Tipo De Reporte":
					c_inicio.setEnabled(false);
					c_final.setEnabled(false);
					cmbEstablecimiento.setEnabled(false);
					cmbStatusChecador.setEnabled(false);
					
					cargar_fechas();
					cmbEstablecimiento.setSelectedIndex(0);
					cmbStatusChecador.setSelectedIndex(0);
					btnGenerarReporte.setEnabled(false);
				break;
			case "Tiempo En Ruta":
					c_inicio.setEnabled(true);
					c_final.setEnabled(true);
					cmbEstablecimiento.setEnabled(true);
					cmbStatusChecador.setEnabled(false);
					btnGenerarReporte.setEnabled(true);
					
					cargar_fechas();
					cmbEstablecimiento.setSelectedIndex(0);
					cmbStatusChecador.setSelectedIndex(0);
				break;
			case "Detalles De Huellas":
					c_inicio.setEnabled(false);
					c_final.setEnabled(false);
					cmbEstablecimiento.setEnabled(true);
					cmbStatusChecador.setEnabled(false);
					btnGenerarReporte.setEnabled(true);
					
					cargar_fechas();
					cmbEstablecimiento.setSelectedIndex(0);
					cmbStatusChecador.setSelectedIndex(0);
				break;
			case "Status Checador":
					c_inicio.setEnabled(false);
					c_final.setEnabled(false);
					cmbEstablecimiento.setEnabled(true);
					cmbStatusChecador.setEnabled(true);
					btnGenerarReporte.setEnabled(true);
					
					cargar_fechas();
					cmbEstablecimiento.setSelectedIndex(0);
					cmbStatusChecador.setSelectedIndex(0);
				break;
		}
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
				String StatusChecador = cmbStatusChecador.getSelectedItem().toString();
			
				if(c_inicio.getDate().before(c_final.getDate())){
				   if( cmbTipoDeReporte.getSelectedItem().toString().trim().equals("Tiempo En Ruta")){
					   reporte = "Obj_Reporte_De_Tiempo_En_Recorrido.jrxml";
					   comando = "exec reporte_de_entradas_y_salidas_ruta '"+fecha_inicio+"','"+fecha_final+"','"+Establecimiento+"'";
					   System.out.println(comando);
				   }	
				   
				   if( cmbTipoDeReporte.getSelectedItem().toString().trim().equals("Detalles De Huellas")){
					   reporte ="Obj_Reporte_De_Colaboradores_Con_Opciones_De_Huella.jrxml";
					   comando="exec reporte_de_colaboradores_con_opciones_de_huella '"+Establecimiento+"','','',0";/*estab,depto,puesto,folio_emp*/
				   }
				   
				   if( cmbTipoDeReporte.getSelectedItem().toString().trim().equals("Status Checador")){
					   reporte ="Obj_Status_Checador.jrxml";
					   comando="exec reporte_de_colaboradores_con_status_checador '"+Establecimiento+"','"+StatusChecador+"'";
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
