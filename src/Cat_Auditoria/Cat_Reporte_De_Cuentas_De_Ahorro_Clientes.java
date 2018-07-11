package Cat_Auditoria;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.border.Border;

import com.toedter.calendar.JDateChooser;

import Conexiones_SQL.Generacion_Reportes;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;

@SuppressWarnings("serial")
public class Cat_Reporte_De_Cuentas_De_Ahorro_Clientes extends JFrame{
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JDateChooser fhIn  = new Componentes().jchooser(new JDateChooser()  ,"Fecha Inicial" ,1);
	JDateChooser fhFin = new Componentes().jchooser(new JDateChooser()  ,"Fecha Final"   ,0);
	
	JCButton btnGenerar = new JCButton("Buscar", "buscar.png", "Azul");

	Border borde;
	public Cat_Reporte_De_Cuentas_De_Ahorro_Clientes() {
		
		this.setTitle("Reporte De Cuenta De Ahorro Clientes");
		panel.setBorder(BorderFactory.createTitledBorder(borde,"Historial De Cuentas De Ahorro De Clientes"));
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/lista-icono-7220-32.png"));
		borde = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
		
		int x=10,	y=20,	ancho=90;
		
		panel.add(new JLabel("Fecha                      Del:")).setBounds(x,y,ancho+30,20);
		panel.add(fhIn).setBounds(x+ancho+40, y, ancho+30, 20);
		
		panel.add(new JLabel("Al:")).setBounds(x+ancho*3-10,y,30,20);
		panel.add(fhFin).setBounds(x+ancho*3+10, y, ancho+30, 20);
		
		panel.add(btnGenerar).setBounds(x+(ancho*3)+10,y+=25,ancho+30,30);
		
		cont.add(panel);
		btnGenerar.addActionListener(opGenerar);
		
		this.setSize(425, 125);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
	}
	
	String fecha_inicio = "";
	String fecha_final  = "";
	ActionListener opGenerar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			
			if(fhIn.getDate()==null || fhFin.getDate() == null){
				fhIn.setDate(null);
				fhFin.setDate(null);
				reporte();
			}else{
				fecha_inicio = new SimpleDateFormat("dd/MM/yyyy").format(fhIn.getDate())+" 00:00:00";
				fecha_final  = new SimpleDateFormat("dd/MM/yyyy").format(fhFin.getDate())+"  23:59:00";
				
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm"); 
				Date fecha1 = sdf.parse(fecha_inicio , new ParsePosition(0));
				Date fecha2 = sdf.parse(fecha_final , new ParsePosition(0));
				
				if(fecha1.before(fecha2)){	
			 		reporte();
			 	}else{
					JOptionPane.showMessageDialog(null,"El Rango de Fechas Esta Invertido","Aviso!", JOptionPane.WARNING_MESSAGE);
					return;
				}
			}
		}
	};
	
	public void reporte(){
		
		int fechas = fecha_inicio.equals("")?0:1; 
		
		if(fechas>0){
			
				String basedatos="2.98";
				String vista_previa_reporte="no";
				int vista_previa_de_ventana=0;
				
				String reporte = "Obj_Reporte_De_Cuntas_De_Ahorro.jrxml";				
				String consulta = "exec cuenta_de_ahorro_clientes_historial '"+fecha_inicio+"','"+fecha_final+"'";
				
				new Generacion_Reportes().Reporte(reporte, consulta, basedatos, vista_previa_reporte,vista_previa_de_ventana);
		
		}else{
				JOptionPane.showMessageDialog(null,"Es Necesario Ingresar Un Filtro","Aviso!", JOptionPane.WARNING_MESSAGE);
				return;
		}
	}
	
	public static void main(String [] arg){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Reporte_De_Cuentas_De_Ahorro_Clientes().setVisible(true);
		}catch(Exception e){	}
	}

}
