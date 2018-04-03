package Cat_Auditoria;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import Conexiones_SQL.Generacion_Reportes;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;

import com.toedter.calendar.JDateChooser;

@SuppressWarnings("serial")
public class Cat_Reportes_De_Movimientos_Operados_Por_Establecimiento extends JFrame {
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JDateChooser c_inicio = new Componentes().jchooser(new JDateChooser()  ,"Fecha"  ,0);
	JCButton btn_generar    = new JCButton("Generar Reporte"    ,"buscar.png"     ,"Azul"); 
	
	String establecimientos[] = new Obj_Establecimiento().Combo_Establecimiento201();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbEstablecimiento = new JComboBox(establecimientos);
	
	public Cat_Reportes_De_Movimientos_Operados_Por_Establecimiento(){
		this.setSize(270,180);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/reporte_icon&16.png"));
		this.setTitle("Movimientos Operados");
		this.panel.setBorder(BorderFactory.createTitledBorder("Movimientos Operados Por Establecimiento"));
        this.panel.add(cmbEstablecimiento).setBounds  (40 ,30 ,180 ,20);
		this.panel.add(new JLabel("Fecha:")).setBounds(40 ,60 ,100 ,20);
		this.panel.add(c_inicio).setBounds            (80 ,60 ,140 ,20);
		this.panel.add(btn_generar).setBounds         (40 ,100,180 ,20);
		this.btn_generar.addActionListener(op_generar);
		this.cont.add(panel);
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
				
				 reporte = "Obj_Reporte_IZAGAR_de_Movimientos_Operados_Por_Establecimiento.jrxml";
				 comando = "exec sp_Reporte_IZAGAR_de_Movimientos_Operados_Por_Establecimiento '"+fecha+"','"+cmbEstablecimiento.getSelectedItem().toString().trim()+"'";
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
	    if(cmbEstablecimiento.getSelectedIndex()==0)error+= "Establecimiento \n";
		return error;
	}

	public static void main(String args[]){
		try{UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		new Cat_Reportes_De_Movimientos_Operados_Por_Establecimiento().setVisible(true);	
		}catch(Exception e){System.err.println("Error en Main: "+e.getMessage());
		}
	}
}
