package Cat_Reportes;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

import com.toedter.calendar.JDateChooser;  
  
import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Generacion_Reportes;
import Obj_Principal.Componentes;
import Obj_Principal.JCTextField;
  


@SuppressWarnings("serial")
public class Cat_Reporte_Evaluacione_Operativas extends JDialog {
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JDateChooser c_inicio = new JDateChooser();
	
	JButton btn_generar = new JButton  ("Reporte de Evaluaciones",new ImageIcon("imagen/inasistencia16x16.png"));
	JButton btn_generar_observaciones = new JButton  ("Reporte de Observaciones",new ImageIcon("imagen/inasistencia16x16.png"));
		
	JLabel JLBlinicio= new JLabel(new ImageIcon("Imagen/iniciar-icono-4628-16.png") );
	JLabel JLBfin= new JLabel(new ImageIcon("Imagen/acabado-icono-7912-16.png") );
	JLabel JLBestablecimiento= new JLabel(new ImageIcon("Imagen/folder-home-home-icone-5663-16.png") );
	JLabel JLBdepartamento= new JLabel(new ImageIcon("Imagen/departamento-icono-5365-16.png") );
	
	
	JLabel lblFolio = new JLabel("Folio Matriz :");
	JTextField txtFolio = new Componentes().text(new JCTextField(), "Folio", 30, "Int");   
	  
	
	public Cat_Reporte_Evaluacione_Operativas(){  
		this.setModal(true);
		this.setTitle("Reportes de Evaluaciones Operativas");
		this.panel.setBorder(BorderFactory.createTitledBorder("Reporte_Evaluaciones"));
		this.panel.add(new JLabel("Fecha :")).setBounds(15,25,100,20);
		this.panel.add(JLBlinicio).setBounds(75,25,20,20);     
		this.panel.add(c_inicio).setBounds(95,25,100,20);  
		panel.add(lblFolio).setBounds(15, 55, 100, 25);   
		panel.add(txtFolio).setBounds(90, 55, 100, 25);  
		this.panel.add(btn_generar).setBounds(15,105,180,35);
		panel.add(btn_generar_observaciones).setBounds(15, 155, 180, 35);
				   
		this.cont.add(panel);
		
		this.setSize(220,250);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		 cargar_fechas();
				 
		btn_generar.addActionListener(op_generar_reporte_matrices);
		btn_generar_observaciones.addActionListener(op_generar_reporte_matrices_observaciones);
		
	}
	
	public void cargar_fechas(){
		Date date1 = null;
				  try {
					date1 = new SimpleDateFormat("dd/MM/yyyy").parse(new BuscarSQL().fecha(0));
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();   
				}
		c_inicio.setDate(date1);
	};
	
	String basedatos="2.26";
	String vista_previa_reporte="no";
	int vista_previa_de_ventana=0;
	String comando="";
	String reporte = "";
	
	ActionListener op_generar_reporte_matrices = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(validar_fechas().equals("")){
				String fecha_inicio = new SimpleDateFormat("dd/MM/yyyy").format(c_inicio.getDate());
					Reporte_de_EvaluacionesOperativas(fecha_inicio,Integer.valueOf(txtFolio.getText().toString()));
			}		
		}
	};
	ActionListener op_generar_reporte_matrices_observaciones = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(validar_fechas().equals("")){
				String fecha_inicio = new SimpleDateFormat("dd/MM/yyyy").format(c_inicio.getDate());
				Reporte_de_EvaluacionesOperativas_observaciones(fecha_inicio,txtFolio.getText().toString());
			}		    
		}     
	};    
	   
	public void Reporte_de_EvaluacionesOperativas(String fecha, int FolioMat){  
		 reporte = "Obj_Reporte_Matrices2.jrxml";
		 comando = "exec sp_generar_promedio_departamentos_matrices_operativas '"+fecha+"', "+FolioMat+"";  
		 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
		 
	}  
	public void Reporte_de_EvaluacionesOperativas_observaciones(String fecha, String FolioMat){
		 reporte = "Obj_Reporte_Matriz_Observaciones.jrxml";
		 comando = "exec sp_generar_reporte_observaciones_matriz '"+fecha+"', "+FolioMat+"";       
		 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
	}
	
	public String validar_fechas(){   
		String error = "";
		String fechainicioNull = c_inicio.getDate()+"";
	    if(fechainicioNull.equals("null"))error+= "Fecha  inicio\n"; 
		return error;
	}  
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Reporte_Evaluacione_Operativas().setVisible(true);
		}catch(Exception e){	}
	}

}
