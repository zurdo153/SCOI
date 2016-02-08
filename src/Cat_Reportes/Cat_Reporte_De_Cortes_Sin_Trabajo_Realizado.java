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
import Obj_Lista_de_Raya.Obj_Establecimiento;

import com.toedter.calendar.JDateChooser;


@SuppressWarnings("serial")
public class Cat_Reporte_De_Cortes_Sin_Trabajo_Realizado extends JFrame {
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	String[] vector = new Obj_Establecimiento().Combo_Establecimiento_Concentrado();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbConcentrado = new JComboBox(vector);
	
	JDateChooser c_inicio = new JDateChooser();
	JDateChooser c_final = new JDateChooser();
	
	JButton btn_generar_reporte = new JButton  ("Reporte de Cortes Sin Trabajo",new ImageIcon("imagen/Report.png"));
	
	JLabel JLBlinicio= new JLabel(new ImageIcon("Imagen/iniciar-icono-4628-16.png") );
	JLabel JLBfin= new JLabel(new ImageIcon("Imagen/acabado-icono-7912-16.png") );
	JLabel JLBestablecimiento= new JLabel(new ImageIcon("Imagen/folder-home-home-icone-5663-16.png") );
	JLabel JLBdepartamento= new JLabel(new ImageIcon("Imagen/departamento-icono-5365-16.png") );
	
	public Cat_Reporte_De_Cortes_Sin_Trabajo_Realizado(){
		
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Report.png"));
		this.setTitle("Reportes De Cortes Sin Trabajo Realizado");
		this.panel.setBorder(BorderFactory.createTitledBorder("Generar Reportes De Cortes Sin Trabajo Realizado"));
		
		this.panel.add(new JLabel("Concentrado:")).setBounds(15,25,100,20);
		this.panel.add(cmbConcentrado).setBounds(95,25,190,20);
		
		this.panel.add(new JLabel("Fecha Inicio:")).setBounds(15,55,100,20);
		this.panel.add(JLBlinicio).setBounds(75,55,20,20);
		this.panel.add(c_inicio).setBounds(95,55,100,20);
		
		this.panel.add(new JLabel("Fecha Final:")).setBounds(225,55,100,20);
		this.panel.add(JLBfin).setBounds(285,55,20,20);
		this.panel.add(c_final).setBounds(305,55,100,20);
	
		this.panel.add(btn_generar_reporte).setBounds(100,90,250,35);
				
		this.cont.add(panel);
		this.setSize(430,180);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		
		 cargar_fechas();
				 
		 btn_generar_reporte.addActionListener(op_generar_faltas); 
		
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
	
	
	ActionListener op_generar_faltas = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			if(validar_campos().equals("")){
				String fecha_inicio = new SimpleDateFormat("dd/MM/yyyy").format(c_inicio.getDate());
				String fecha_final = new SimpleDateFormat("dd/MM/yyyy").format(c_final.getDate());

				if(c_inicio.getDate().before(c_final.getDate())){
					llamar_reporte(cmbConcentrado.getSelectedItem().toString().trim(),fecha_inicio,fecha_final);
				}else{
					  JOptionPane.showMessageDialog(null, "El Rango De Fechas Esta Invertido","Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
                      return;
				}
			}else{
				  JOptionPane.showMessageDialog(null, "Los Siguientes Campos Estan Vacios y Se Necesitan Para La Consulta:\n "+validar_campos(),"Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
                  return;
			}
		}
	};
	
	
	public void llamar_reporte(String concentrado, String fecha_inicio, String fecha_final){
		 reporte = "Obj_Reporte_De_Cortes_Sin_Trabajo_Realizado.jrxml";
		 
		 comando = "exec sp_select_cortes_pendientes_de_trabajo_por_fecha '"+concentrado+"','"+fecha_inicio+"','"+fecha_final+"'";
		 
		 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
	}
	
	public String validar_campos(){
		String error = "";
		String fechainicioNull = c_inicio.getDate()+"";
		String fechafinalNull = c_final.getDate()+"";
		if(cmbConcentrado.getSelectedIndex() == 0 ) error += "Seleccione Un Concentrado\n";
	    if(fechainicioNull.equals("null"))error+= "Fecha  inicio\n";
		if(fechafinalNull.equals("null"))error+= "Fecha Final\n";
		return error;
	}
	
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Reporte_De_Cortes_Sin_Trabajo_Realizado().setVisible(true);
		}catch(Exception e){	}
	}

}
