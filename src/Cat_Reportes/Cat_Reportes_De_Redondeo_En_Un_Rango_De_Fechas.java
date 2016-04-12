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
public class Cat_Reportes_De_Redondeo_En_Un_Rango_De_Fechas extends JFrame {
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JDateChooser c_inicio = new JDateChooser();
	JDateChooser c_final = new JDateChooser();
	
	String establecimiento[] = new Obj_Establecimiento().Combo_Establecimiento201();
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbEstablecimiento = new JComboBox(establecimiento);
	
	JCButton btn_generar_Bajas = new JCButton  ("Reporte de Redondeo","contrato-de-acuerdo-de-acuerdo-de-la-mano-encuentros-socio-icono-7428-32.png","Azul");
	JLabel JLBlinicio= new JLabel(new ImageIcon("Imagen/iniciar-icono-4628-16.png") );
	JLabel JLBfin= new JLabel(new ImageIcon("Imagen/acabado-icono-7912-16.png") );
	JLabel JLBestablecimiento= new JLabel(new ImageIcon("Imagen/folder-home-home-icone-5663-16.png") );
	
	Obj_Usuario usuario = new Obj_Usuario().LeerSession();
	
	public Cat_Reportes_De_Redondeo_En_Un_Rango_De_Fechas(){
		this.setSize(510,155);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/contrato-de-acuerdo-de-acuerdo-de-la-mano-encuentros-socio-icono-7428-48.png"));
		this.setTitle("Reportes de Redondeo En Un Rango de Fechas");
		this.panel.setBorder(BorderFactory.createTitledBorder("Seleccione Un Periodo De Fecha y De Click Al Reporte "));
		
		this.panel.add(new JLabel("Fecha Inicio:")).setBounds(15,25,100,20);
		this.panel.add(JLBlinicio).setBounds(75,25,20,20);
		this.panel.add(c_inicio).setBounds(95,25,100,20);
		this.panel.add(new JLabel("Fecha Final:")).setBounds(15,55,100,20);
		this.panel.add(JLBfin).setBounds(75,55,20,20);
		this.panel.add(c_final).setBounds(95,55,100,20);
	    this.panel.add(new JLabel("Establecimiento:")).setBounds(220,25,150,20);
	    this.panel.add(JLBestablecimiento).setBounds(300,25,20,20);
		this.panel.add(cmbEstablecimiento).setBounds(320,25,170,20);
		this.panel.add(btn_generar_Bajas).setBounds(220,55,270,25);
		
		this.cont.add(panel);
		cargar_fechas();
		 
		btn_generar_Bajas.addActionListener(op_generar);
		
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
	
	String basedatos="2.200";
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
                String Usuario =  usuario.getNombre_completo();
				
				if(c_inicio.getDate().before(c_final.getDate())){
					Reporte_de_mov(fecha_inicio,fecha_final,Establecimiento,Usuario,cmbEstablecimiento.getSelectedItem().toString().trim().equals("Selecciona un Establecimiento")?"Reporte de Redondeo Por Establecimiento":"Reporte de Redondeo Del Establecimiento");
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
	
	public void Reporte_de_mov(String fecha_inicio, String fecha_final,String Establecimiento,String Usuario,String mov){
		 reporte = "Obj_Reporte_De_Redondeo_Por_Establecimiento .jrxml";
		 comando = "exec sp_reporte_de_redondeo_en_un_periodo '"+fecha_inicio+"','"+fecha_final+"','"+Establecimiento+"','"+Usuario+"','"+mov+"'";
		 
		 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
	}
	
	public String validar_fechas(){
		String error = "";
		String fechainicioNull = c_inicio.getDate()+"";
		String fechafinalNull = c_final.getDate()+"";
	    if(fechainicioNull.equals("null"))error+= "Fecha inicio\n";
		if(fechafinalNull.equals("null"))error+= "Fecha Final\n";
		return error;
	}
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Reportes_De_Redondeo_En_Un_Rango_De_Fechas().setVisible(true);
		}catch(Exception e){	}
	}

}
