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
public class Cat_Reporte_De_Compra_De_Cascos_Por_Fecha extends JFrame {
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JDateChooser c_inicio = new JDateChooser();
	JDateChooser c_final = new JDateChooser();
	
	JButton btn_generar_completo = new JButton  ("Generar Reportes De Compra De Cascos",new ImageIcon("imagen/proceso-para-los-usuarios-icono-5903-16.png"));
	
	JLabel JLBlinicio= new JLabel(new ImageIcon("Imagen/iniciar-icono-4628-16.png") );
	JLabel JLBfin= new JLabel(new ImageIcon("Imagen/acabado-icono-7912-16.png") );
	
	public Cat_Reporte_De_Compra_De_Cascos_Por_Fecha(){
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/asistencia-comunitaria-icono-9465-32.png"));
		this.setTitle("Reportes De Compra De Cascos");
		this.panel.setBorder(BorderFactory.createTitledBorder("Reportes De Compra De Cascos"));
		this.panel.add(new JLabel("Fecha Inicio:")).setBounds(55,25,100,20);
		this.panel.add(JLBlinicio).setBounds(115,25,20,20);
		this.panel.add(c_inicio).setBounds(135,25,100,20);
		this.panel.add(new JLabel("Fecha Final:")).setBounds(55,55,100,20);
		this.panel.add(JLBfin).setBounds(115,55,20,20);
		this.panel.add(c_final).setBounds(135,55,100,20);
	
		this.panel.add(btn_generar_completo).setBounds(25,95,250,35);
				
		this.cont.add(panel);
		this.setSize(310,170);
		this.setResizable(false);
		this.setLocationRelativeTo(null);

		c_inicio.setDate( cargar_fechas(7));
		c_final.setDate( cargar_fechas(0));
				 
		this.btn_generar_completo.addActionListener(op_generar);
		
	}
	
	public Date cargar_fechas(int dias){
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

				if(c_inicio.getDate().before(c_final.getDate())){
					Reporte_de_Asistencia_consideraciones(fecha_inicio,fecha_final);
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
	
	public void Reporte_de_Asistencia_consideraciones(String fecha_inicio, String fecha_final){
		 reporte = "Obj_Reporte_De_Compra_De_Cascos_Por_Fechas.jrxml";
		 comando = "exec sp_Reporte_De_Compra_De_Cascos_Por_Fecha '"+fecha_inicio+"','"+fecha_final+"'";
	
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
			new Cat_Reporte_De_Compra_De_Cascos_Por_Fecha().setVisible(true);
		}catch(Exception e){	}
	}

}
