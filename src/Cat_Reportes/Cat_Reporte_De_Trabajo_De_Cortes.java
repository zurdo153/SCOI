package Cat_Reportes;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

import com.toedter.calendar.JDateChooser;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Cargar_Combo;
import Conexiones_SQL.Connexion;

@SuppressWarnings("serial")
public class Cat_Reporte_De_Trabajo_De_Cortes extends JFrame{
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JDateChooser cfecha = new JDateChooser();
	String[] concentrados = new Cargar_Combo().tipos_de_concentrados();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbConcentrados = new JComboBox(concentrados);
	
	JButton btngenerar = new JButton("Generar",new ImageIcon("imagen/buscar.png"));
	
	public Cat_Reporte_De_Trabajo_De_Cortes(){
		setSize(355,180);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Reportes De Trabajo De Corte");
		setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/fast-food-icon32.png"));
		panel.setBorder(BorderFactory.createTitledBorder("Seleccione el Tipo de Reporte"));
	
		int x=20,y=25;
		
		panel.add(new JLabel("No. Concentrado:")).setBounds(x+10,y,200,20);		
		panel.add(cmbConcentrados).setBounds(x+110,y,175,20);
		panel.add(new JLabel("Fecha Concentrado:")).setBounds(x+10,y+=25,200,20);
		panel.add(cfecha).setBounds(x+110,y,195,20);
		panel.add(btngenerar).setBounds(x+110,y+=50,120,30);
	    
		cfecha.setDate(cargar_fecha_Sugerida(0));
		
		cont.add(panel);
		btngenerar.addActionListener(opGenerar);
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
	
	ActionListener opGenerar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			String fechafinalNull = cfecha.getDate()+"";
			
			if(fechafinalNull.equals("null")){
				JOptionPane.showMessageDialog(null, "La Fecha Del Trabajo De Corte Es Requerida","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
				return;
			}else{
				String fecha_trabajo = new SimpleDateFormat("dd/MM/yyyy").format(cfecha.getDate());
				Cat_Reporte_De_Trabajo_De_Crotes(new BuscarSQL().Folio_De_Trabajo(fecha_trabajo, cmbConcentrados.getSelectedItem().toString()));
			}
		}
	};
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void Cat_Reporte_De_Trabajo_De_Crotes(int folio_trabajo_de_corte) {
		System.out.println(folio_trabajo_de_corte);
		try {
			JasperReport report = JasperCompileManager.compileReport(System.getProperty("user.dir")+"\\src\\Obj_Reportes\\Obj_Reporte_Para_Trabajo_De_Cortes.jrxml");
			
			Map parametro = new HashMap();
			parametro.put("folio_trabajo", folio_trabajo_de_corte);
			
			JasperPrint print = JasperFillManager.fillReport(report, parametro, new Connexion().conexion());
			JasperViewer.viewReport(print, false);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(null, "Error En Cat_Reporte_De_Corte_De_Caja ", "Error !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
		}
		
	}
	
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Reporte_De_Trabajo_De_Cortes().setVisible(true);
		}catch(Exception e){	}
	}
}
