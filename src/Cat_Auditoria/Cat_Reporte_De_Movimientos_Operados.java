package Cat_Auditoria;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import Conexiones_SQL.Generacion_Reportes;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;

import com.toedter.calendar.JDateChooser;

@SuppressWarnings("serial")
public class Cat_Reporte_De_Movimientos_Operados extends JFrame {
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	JDateChooser c_inicio = new Componentes().jchooser(new JDateChooser()  ,"Fecha"  ,1);
	JCButton btn_generar = new JCButton("Generar Reporte En Pantalla","hoja-de-calculo-icono-8865-32.png","Azul");
	
	public Cat_Reporte_De_Movimientos_Operados(){
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/reporte_icon&16.png"));
		this.setTitle("Reportes de Movimientos Operados");
		this.panel.setBorder(BorderFactory.createTitledBorder("Movimientos Operados"));
		this.panel.add(new JLabel("Fecha:")).setBounds(110,40,100,20);
		this.panel.add(c_inicio).setBounds(150,40,140,20);
		this.btn_generar.addActionListener(op_generar);
		this.panel.add(btn_generar).setBounds(75,80,250,40);
		this.cont.add(panel);
		this.setSize(400,200);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
	}
	
	ActionListener op_generar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(validar_fechas().equals("")){
				String fecha = new SimpleDateFormat("dd/MM/yyyy").format(c_inicio.getDate());
				String basedatos="2.200";
				String vista_previa_reporte="no";
				int vista_previa_de_ventana=0;
				String comando="";
				String reporte = "";
				
				 reporte = "Obj_Reporte_IZAGAR_de_Movimientos_Operados.jrxml";
				 comando = "exec sp_Reporte_IZAGAR_de_Movimientos_Operados '"+fecha+"';";
				 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
			}else{
				JOptionPane.showMessageDialog(null,"Los siguientes campos están vacíos: "+validar_fechas(),"Aviso!", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
	};
	public String validar_fechas(){
		String error = "";
		String fechaNull = c_inicio.getDate()+"";
	    if(fechaNull.equals("null"))error+= "Fecha \n";
		return error;
	}
	
	public static  void main(String []arg){
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Reporte_De_Movimientos_Operados().setVisible(true);	
		}catch(Exception e){	}
	}
}
