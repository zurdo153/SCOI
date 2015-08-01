package Cat_Reportes;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

import com.toedter.calendar.JDateChooser;

import Cat_Filtros_IZAGAR.Cat_Filtro_De_Busqueda_De_Asignaciones;
import Conexiones_SQL.Generacion_Reportes;
@SuppressWarnings("serial")
public class Cat_Reportes_De_Apartados extends JFrame {
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JTextField txtAsignacion = new JTextField();

	JButton btnGenerar = new JButton("Generar",new ImageIcon("imagen/buscar.png"));
	JButton btnApartadoAsignacion =new JButton("",new ImageIcon("imagen/Accounting.png"));
	JButton btnApartadoFecha =new JButton("",new ImageIcon("imagen/Calendar.png"));
    JButton btnApartadoApartirFecha= new JButton("",new ImageIcon("imagen/plan-icono-5073-16.png"));
	JButton btnSeleccionAsignacion =new JButton("",new ImageIcon ("imagen/Filter-List-icon16.png"));
	
	JDateChooser cfecha = new JDateChooser();
	
	int tipo_Reporte = 0;
	String Asignacion;
	
	public Cat_Reportes_De_Apartados(String asignacion){
		Asignacion=asignacion+"";
		
		cont.add(panel);
		setSize(305,350);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Reporte de Apartados y Abonos");
		setIconImage(Toolkit.getDefaultToolkit().getImage("imagen/documentos-de-gabinete-icono-4840-32.png"));
		panel.setBorder(BorderFactory.createTitledBorder("Seleccione el Tipo de Reporte Asignacion-Fecha"));
		
		btnApartadoAsignacion.setText(	"<html> <FONT FACE="+"arial"+" SIZE=3 COLOR=BLACk>" +
				"		<CENTER><p>Reporte De Apartado Por Asignacion</p></CENTER></FONT>" +
				"</html>");
		
		btnApartadoFecha.setText(	"<html> <FONT FACE="+"arial"+" SIZE=3 COLOR=BLACk>" +
				"		<CENTER><p>Reporte De Apartado Por Fecha     </p></CENTER></FONT>" +
				"</html>");
		
		btnApartadoApartirFecha.setText(	"<html> <FONT FACE="+"arial"+" SIZE=3 COLOR=BLACk>" +
				"		<CENTER><p>Reporte De Apartados A Partir De Una Fecha Selecionada</p></CENTER></FONT>" +
				"</html>");
		
		panel.add(btnApartadoAsignacion).setBounds(20,25,260,35);
		panel.add(btnApartadoFecha).setBounds(20,75,260,35);
		panel.add(btnApartadoApartirFecha).setBounds(20,125,260,35);
		panel.add(new JLabel("Asignacion:")).setBounds(20,175,200,20);		
		panel.add(txtAsignacion).setBounds(80,175,175,20);
		panel.add(btnSeleccionAsignacion).setBounds(255,175,20,20);
		
		panel.add(new JLabel("Fecha:")).setBounds(20,205,200,20);		
		panel.add(cfecha).setBounds(80,205,195,20);
		
	    panel.add(btnGenerar).setBounds(100,240,120,30);
		
	    txtAsignacion.setEditable(false);
	    cfecha.setEnabled(false);
	    btnGenerar.setEnabled(false);
	    btnSeleccionAsignacion.setEnabled(false);
	    
	    if (!Asignacion.equals("")){
	    	txtAsignacion.setEditable(true);
			cfecha.setEnabled(false);
			btnGenerar.setEnabled(true);
		    btnSeleccionAsignacion.setEnabled(true);
			tipo_Reporte=1;
			cfecha.setDate(null);
			txtAsignacion.setText(Asignacion+"");
	    }
		btnGenerar.addActionListener(opGenerar) ;
		btnApartadoAsignacion.addActionListener(opReporte_Por_Asignacion);
		btnApartadoFecha.addActionListener(opReporte_Por_Fecha);
		btnApartadoApartirFecha.addActionListener(opReporte_Apartir_Fecha);
		btnSeleccionAsignacion.addActionListener(opfiltroAsignacion);
	}
	
	ActionListener opSalir = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			dispose();
		}
	};
	
	ActionListener opfiltroAsignacion = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			new Cat_Filtro_De_Busqueda_De_Asignaciones("Reportes De Apartados").setVisible(true);
			dispose();
		}
	};
	
	ActionListener opReporte_Por_Asignacion = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			txtAsignacion.setEditable(true);
		    btnSeleccionAsignacion.setEnabled(true);
			cfecha.setEnabled(false);
			btnGenerar.setEnabled(true);
			tipo_Reporte=1;
			cfecha.setDate(null);
		}
	};
	
	ActionListener opReporte_Por_Fecha = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			txtAsignacion.setEditable(false);
			cfecha.setEnabled(true);
			btnGenerar.setEnabled(true);
		    btnSeleccionAsignacion.setEnabled(false);
			tipo_Reporte=2;
			txtAsignacion.setText("");
		}
	};
	
	ActionListener opReporte_Apartir_Fecha = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			txtAsignacion.setEditable(false);
			cfecha.setEnabled(true);
			btnGenerar.setEnabled(true);
		    btnSeleccionAsignacion.setEnabled(false);
			tipo_Reporte=3;
			txtAsignacion.setText("");
		}
	};
	
	public String validar_fechas(){
		String error = "";
		@SuppressWarnings("unused")
		String fechafinalNull = cfecha.getDate()+"";
	    if(cfecha.equals("null"))error+= "Fecha\n";
		return error;
	}
	
	ActionListener opGenerar = new ActionListener(){
		public void actionPerformed(ActionEvent arg0){
			String basedatos="2.200";
			String vista_previa_reporte="no";
			int vista_previa_de_ventana=0;
			String comando="";
			String reporte = "";
			if(tipo_Reporte==1){
			if(txtAsignacion.getText().equals(""))
				{
					JOptionPane.showMessageDialog(null,"Necesita Teclear Una Asignacion","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
						return;
					}else{
						 comando="exec sp_Reporte_IZAGAR_de_Apartados_Por_Asignacion '"+txtAsignacion.getText()+"'";
						 reporte="Obj_Reporte_De_Apartados.jrxml";
						 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);	 
						 return;
			 }
		     }
			if(tipo_Reporte==2){
			   String fechaNull = cfecha.getDate()+"";
			   if(fechaNull.equals("null")){
					JOptionPane.showMessageDialog(null,"Necesita Selecionar una Fecha o la Fecha tecleada es Incorrecta","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
					return;
				   }else{
					  String fecha  = new SimpleDateFormat("dd/MM/yyyy").format(cfecha.getDate())+" 00:00:00";
					  comando = "exec IZAGAR_consulta_de_apartados '"+fecha+"'" ;
					  reporte ="Obj_Reporte_De_Apartados_Por_Fecha.jrxml";
					  new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);	
						return;					   
				   }
			 }
			if(tipo_Reporte==3){
				   String fechaNull = cfecha.getDate()+"";
				   if(fechaNull.equals("null")){
						JOptionPane.showMessageDialog(null,"Necesita Selecionar una Fecha o la Fecha tecleada es Incorrecta","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
						return;
					   }else{
						  String fecha  = new SimpleDateFormat("dd/MM/yyyy").format(cfecha.getDate())+" 00:00:00";
						  comando = "exec sp_Reporte_de_Apartados_General '"+fecha+"'" ;
						  reporte ="Obj_Reporte_De_Apartados_Completo.jrxml";
						  new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);	
							return;					   
					   }
				}
		}
	};
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Reportes_De_Apartados("").setVisible(true);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
