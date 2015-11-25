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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.UIManager;



import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Generacion_Reportes;
import Obj_Lista_de_Raya.Obj_Departamento;
import Obj_Lista_de_Raya.Obj_Establecimiento;

import com.toedter.calendar.JDateChooser;


@SuppressWarnings("serial")
public class Cat_Reportes_De_Entradas_y_Salidas_De_Cascos extends JFrame {
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JDateChooser c_inicio = new JDateChooser();
	JDateChooser c_final = new JDateChooser();
	
	String establecimiento[] = new Obj_Establecimiento().Combo_Establecimiento();
	String departamento[] = new Obj_Departamento().Combo_Departamento();
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbEstablecimiento = new JComboBox(establecimiento);
	
	JButton btn_generar_altas = new JButton  ("Reporte de Movimientos De Cascos",new ImageIcon("imagen/bajas_altas_16p.png"));
//	JButton btn_generar_Bajas = new JButton  ("Reporte de Bajas",new ImageIcon("imagen/baja16.png"));
	
	JLabel JLBlinicio= new JLabel(new ImageIcon("Imagen/iniciar-icono-4628-16.png") );
	JLabel JLBfin= new JLabel(new ImageIcon("Imagen/acabado-icono-7912-16.png") );
	JLabel JLBdepartamento= new JLabel(new ImageIcon("Imagen/departamento-icono-5365-16.png") );
	
	public Cat_Reportes_De_Entradas_y_Salidas_De_Cascos(){
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/bajas_altas.png"));
		this.setTitle("Reportes de Altas Y Bajas de Personal En Un Rango de Fechas");
		this.panel.setBorder(BorderFactory.createTitledBorder("Reportes de Altas y Bajas de Personal"));
//		this.panel.add(new JLabel("Fecha Inicio:")).setBounds(15,25,100,20);
//		this.panel.add(JLBlinicio).setBounds(75,25,20,20);
//		this.panel.add(c_inicio).setBounds(95,25,100,20);
		this.panel.add(new JLabel("Fecha Final:")).setBounds(15,55,100,20);
		this.panel.add(JLBfin).setBounds(75,55,20,20);
		this.panel.add(c_final).setBounds(95,55,100,20);
//	    this.panel.add(new JLabel("Establecimiento:")).setBounds(220,25,150,20);
//		this.panel.add(cmbEstablecimiento).setBounds(320,25,170,20);
	
		this.panel.add(btn_generar_altas).setBounds(130,100,250,25);
//		this.panel.add(btn_generar_Bajas).setBounds(290,100,150,25);
		
		
		this.cont.add(panel);
		this.setSize(470,165);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		 cargar_fechas();
		 
		btn_generar_altas.addActionListener(op_generar); 
//		btn_generar_Bajas.addActionListener(op_generar);

		
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
				String fecha_inicio = new SimpleDateFormat("dd/MM/yyyy").format(c_inicio.getDate())+" 00:00:00";
				String fecha_final = new SimpleDateFormat("dd/MM/yyyy").format(c_final.getDate())+" 23:59:58";
				String Establecimiento = cmbEstablecimiento.getSelectedItem().toString();
			

				if(c_inicio.getDate().before(c_final.getDate())){
					
					Reporte_de_mov(fecha_inicio,fecha_final,Establecimiento,e.getActionCommand().equals("Reporte de Altas")?"altas":"bajas");
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

	
	public void Reporte_de_mov(String fecha_inicio, String fecha_final,String Establecimiento,String mov){
		 reporte = "Obj_Reporte_De_Entradas_Y_Salidas_De_Cascos_Hasta_Una_Fecha.jrxml";
		 
		 comando = "exec sp_Reporte_De_Entradas_Y_Salidas_De_Cascos_Hasta_Una_Fecha '"+fecha_final+"'";
		 
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
			new Cat_Reportes_De_Entradas_y_Salidas_De_Cascos().setVisible(true);
		}catch(Exception e){	}
	}

}
