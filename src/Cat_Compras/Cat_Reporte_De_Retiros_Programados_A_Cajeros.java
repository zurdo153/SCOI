package Cat_Compras;
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
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;

import com.toedter.calendar.JDateChooser;

import Conexiones_SQL.Generacion_Reportes;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;

@SuppressWarnings("serial")
public class Cat_Reporte_De_Retiros_Programados_A_Cajeros extends JFrame{
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JTextField txtFolioAsignacion = new Componentes().text(new JCTextField(), "Folio De Asignacion", 12, "String");
	JTextField txtFolioCajero = new Componentes().text(new JCTextField(), "Folio De Cajero(a)", 12, "String");
	
	JDateChooser fhIn = new JDateChooser();
	JDateChooser fhFin = new JDateChooser();
	
	JCButton btnDeshacer = new JCButton("Deshacer", "Deshacer16.png", "Azul");
	JCButton btnGenerar = new JCButton("Buscar", "buscar.png", "Azul");

	Border borde;
	public Cat_Reporte_De_Retiros_Programados_A_Cajeros() {
		
		this.setTitle("Reporte De Retiros Programados");
		panel.setBorder(BorderFactory.createTitledBorder(borde,"Reporte De Retiros Programados A Cajeros"));
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/lista-icono-7220-32.png"));
		borde = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
		
		int x=10,	y=20,	ancho=90;
		
		panel.add(new JLabel("Folio De Asignacion: ")).setBounds(x,y,ancho+30,20);
		panel.add(txtFolioAsignacion).setBounds(x+ancho+40, y, ancho*3, 20);
		
		panel.add(new JLabel("Folio De Cajero(a): ")).setBounds(x,y+=25,ancho+30,20);
		panel.add(txtFolioCajero).setBounds(x+ancho+40, y, ancho*3, 20);
		
		panel.add(new JLabel("Fecha                      Del:")).setBounds(x,y+=25,ancho+30,20);
		panel.add(fhIn).setBounds(x+ancho+40, y, ancho+30, 20);
		
		panel.add(new JLabel("Al:")).setBounds(x+ancho*3-10,y,30,20);
		panel.add(fhFin).setBounds(x+ancho*3+10, y, ancho+30, 20);
		
		panel.add(btnDeshacer).setBounds(x+(ancho*2)-10,y+=25,ancho+20,20);
		panel.add(btnGenerar).setBounds(x+(ancho*3)+20,y,ancho+20,20);
		
		cont.add(panel);
		
		btnDeshacer.addActionListener(opDeshacer);
		btnGenerar.addActionListener(opGenerar);
		
		this.setSize(425, 155);
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
	
	ActionListener opDeshacer = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			txtFolioAsignacion.setText("");
			txtFolioCajero.setText("");
			fhIn.setDate(null);
			fhFin.setDate(null);
			
			fecha_inicio = "";
			fecha_final = "";
		}
	};
	
	public void reporte(){
		
		int cajero = txtFolioCajero.getText().trim().equals("")?0:1;
		int asignacion = txtFolioAsignacion.getText().equals("")?0:1;
		int fechas = fecha_inicio.equals("")?0:1; 
		
		if((cajero+asignacion+fechas)>0){
			
				String basedatos="2.98";
				String vista_previa_reporte="no";
				int vista_previa_de_ventana=0;
				
				String reporte = "Obj_Reporte_De_Retiros_Programados_A_Cajeros.jrxml";				
				String consulta = "exec sp_select_reporte_de_retiros_programados '"+txtFolioAsignacion.getText().trim().toUpperCase()+"','"+(txtFolioCajero.getText().trim().equals("")?"0":txtFolioCajero.getText().trim().toUpperCase())+"','"+fecha_inicio+"','"+fecha_final+"'";
				
				new Generacion_Reportes().Reporte(reporte, consulta, basedatos, vista_previa_reporte,vista_previa_de_ventana);
		
		}else{
				JOptionPane.showMessageDialog(null,"Es Necesario Ingresar Un Filtro","Aviso!", JOptionPane.WARNING_MESSAGE);
				return;
		}
	}
	
	public static void main(String [] arg){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Reporte_De_Retiros_Programados_A_Cajeros().setVisible(true);
		}catch(Exception e){	}
	}

}
