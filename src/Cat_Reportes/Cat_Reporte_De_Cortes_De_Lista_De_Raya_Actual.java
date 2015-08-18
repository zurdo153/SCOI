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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import com.toedter.calendar.JDateChooser;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Generacion_Reportes;
import Obj_Principal.Componentes;

@SuppressWarnings("serial")
public class Cat_Reporte_De_Cortes_De_Lista_De_Raya_Actual extends JFrame {
	
	JLabel lblFolioEmpleado = new JLabel("Folio Empleado:");
	JTextField txtFolioEmpleado = new Componentes().text(new JTextField(), "Folio De Empleado", 10, "Int");
	JButton btnFiltroEmpleado = new JButton(new ImageIcon("Iconos/zoom_icon&16.png"));
	
	JButton btncortes_Limpio = new JButton(new ImageIcon("imagen/hoja-de-calculo-excel-icono-5223-16.png"));
	JButton btnPrestamos_Por_Establecimiento =new JButton(new ImageIcon("imagen/plan-icono-5073-16.png"));
	
	JLabel lblLinea = new JLabel();
	JDateChooser fechaIn = new JDateChooser();
	JDateChooser fechaFin = new JDateChooser();
	JButton btnAbonos_Cortes = new JButton(new ImageIcon("imagen/plan-icono-5073-16.png"));
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	

	Border blackline, etched, raisedbevel, loweredbevel, empty;
	TitledBorder title4; 
	
	public Cat_Reporte_De_Cortes_De_Lista_De_Raya_Actual(String folio_empleado) {
		
		Constructor();
		
		txtFolioEmpleado.setText(folio_empleado);
		
			Date fechaI;
			try {
				fechaI = new SimpleDateFormat("dd/MM/yyyy").parse("1/01/2015");
				fechaIn.setDate(fechaI);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			fechaFin.setDate(cargar_fecha_Sugerida(0));
		
	}
	
	public Cat_Reporte_De_Cortes_De_Lista_De_Raya_Actual() {
		Constructor();
	}
	
	public Date cargar_fecha_Sugerida(Integer dias){
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

	public void Constructor(){
		
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/dinero-icono-8797-48.jpg"));
		blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
		panel.setBorder(BorderFactory.createTitledBorder(blackline,"Seleccion Del Reporte de Diferencia de Cortes de Lista de Raya Actual"));
		this.setTitle("Reportes de Abonos y Saldos A Cortes");
		
		lblLinea.setBorder(BorderFactory.createTitledBorder(blackline,"Reporte De Abonos De Cortes"));
		
		btncortes_Limpio.setText(	"<html> <FONT FACE="+"arial"+" SIZE=3 COLOR=BLACk>" +
				"		<p>Impresion De Reporte De Cortes</p>" +
				"		<CENTER><p>Para Exportar a Excel</p></CENTER></FONT>" +
				"</html>"); 
		
		btnPrestamos_Por_Establecimiento.setText(	"<html> <FONT FACE="+"arial"+" SIZE=3 COLOR=BLACk>" +
				"		<p>Impresion De Reporte De Abonos y Saldo </p>" +
				"		<CENTER><p>De Lista de Raya Actual Por Establecimiento</p></CENTER></FONT>" +
				"</html>"); 
		
		btnAbonos_Cortes.setText(	"<html> <FONT FACE="+"arial"+" SIZE=3 COLOR=BLACk>" +
				"		<p>Impresion De Reporte De Abonos </p>" +
				"		<CENTER><p>De Cortes Por Fecha</p></CENTER></FONT>" +
				"</html>"); 
		
		int x= 35,y=30,ancho=300;
		
		panel.add(lblFolioEmpleado).setBounds(x,y,80,20);
		panel.add(txtFolioEmpleado).setBounds(x+80,y,60,20);
		panel.add(btnFiltroEmpleado).setBounds(x+140,y,30,20);
		
		panel.add(btnPrestamos_Por_Establecimiento).setBounds(x,y+=25,ancho,40);
		
		panel.add(lblLinea			).setBounds(x,y+=45,ancho,95);
		panel.add(new JLabel("De:")	).setBounds(x+30,y+=20,100,20);
		panel.add(fechaIn			).setBounds(x+50,y,100,20);
		panel.add(new JLabel("A:")	).setBounds(x*4+47,y,100,20);
		panel.add(fechaFin			).setBounds(x*5+25,y,100,20);
		
		panel.add(btnAbonos_Cortes	).setBounds(x+10,y+=25,ancho-20,40);
		
//		panel.add(btncortes_Limpio).setBounds(40,100,300,40);
		btnPrestamos_Por_Establecimiento.addActionListener(Reporte_Cortes_Lista_de_Raya_Actual_Por_Establecimiento);
		btnAbonos_Cortes.addActionListener(Reporte_Abonos_Cortes);
		btncortes_Limpio.addActionListener(Reporte_Cortes_Lista_de_Raya_Actual_limpio);
		
		txtFolioEmpleado.setHorizontalAlignment(4);

		cont.add(panel);
		this.setSize(375,255);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
	}
	
	String basedatos="2.26";
	String vista_previa_reporte="no";
	int vista_previa_de_ventana=0;
	String comando="";
	String reporte = "";
	
	
	ActionListener Reporte_Cortes_Lista_de_Raya_Actual_Por_Establecimiento = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			 reporte = "Obj_Reporte_De_Cortes_De_Lista_De_Raya_Actual.jrxml";
			 
			 String folioEmpleado = txtFolioEmpleado.getText().equals("")?"0":txtFolioEmpleado.getText();
			 comando = "exec sp_Reporte_De_Cortes_De_Lista_De_Raya_Actual_Para_Exportar 'status',"+folioEmpleado;
			 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
		}
	};
	
	ActionListener Reporte_Abonos_Cortes = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			
			String fechaInicio = (fechaIn.getDate()+"").equals("null")?"null":new SimpleDateFormat("dd/MM/yyyy").format(fechaIn.getDate());
			String fechaFinal =  (fechaFin.getDate()+"").equals("null")?"null":new SimpleDateFormat("dd/MM/yyyy").format(fechaFin.getDate());
			
			if(fechaIn.getDate()==null || fechaFin.getDate()==null){
				JOptionPane.showMessageDialog(null, "Alguna de las fechas se encuentra vacia", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
				return;
			}else{
					if(fechaIn.getDate().before(fechaFin.getDate())){
						reporte = "Obj_Reporte_De_Abonos_Y_Diferencia_De_Cortes.jrxml"; // Obj_Abono_De_Cortes.jrxml
						
						 String folioEmpleado = txtFolioEmpleado.getText().equals("")?"0":txtFolioEmpleado.getText();
						comando = "exec sp_Reporte_De_Abono_De_Cortes_Por_Fecha "+folioEmpleado+",'"+fechaInicio+" 00:00:00','"+fechaFinal+" 23:59:00'";
						new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
					}else{
						JOptionPane.showMessageDialog(null, "Verifique que las fechas no esten invertidas", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
						return;
					}
			}
		}
	};
	
	
	ActionListener Reporte_Cortes_Lista_de_Raya_Actual_limpio = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			 reporte = "Obj_Reporte_De_Cortes_De_Lista_De_Raya_Actual_Para_Exportar.jrxml";
			 comando = "exec sp_Reporte_De_Cortes_De_Lista_De_Raya_Actual_Para_Exportar 'NombreCompleto '";
			 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
		}
	};
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Reporte_De_Cortes_De_Lista_De_Raya_Actual().setVisible(true);
		}catch(Exception e){	}
	}


}
