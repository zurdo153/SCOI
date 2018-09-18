package Cat_Inventarios;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.border.Border;

import com.toedter.calendar.JDateChooser;

import Conexiones_SQL.Generacion_Reportes;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;

@SuppressWarnings("serial")
public class Cat_Consulta_De_Pedidos_Realizados_Por_Establecimieto extends JFrame {
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JDateChooser cfecha = new Componentes().jchooser(new JDateChooser()  ,"Fecha"  ,0);
	
	String[] estab = new Obj_Establecimiento().Combo_Establecimiento201();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbEstablecimiento = new JComboBox(estab);
	
	JCButton btnBuscar = new JCButton("Buscar", "buscar.png", "Azul");

	Border blackline;
	
	public Cat_Consulta_De_Pedidos_Realizados_Por_Establecimieto() {
		this.setTitle("Reporte De Pedidos Por Establecimiento");
		this.panel.setBorder(BorderFactory.createTitledBorder(blackline, "Seleccionar Un Establecimiento"));
		cont.add(panel);
		this.setSize(400, 150);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		
		int x=15,y=25,ancho=90;
		
		panel.add(new JLabel("Fecha: ")).setBounds(x, y, ancho, 20);
		panel.add(cfecha).setBounds(x+ancho, y, ancho+50, 20);
		
		panel.add(new JLabel("Establecimiento: ")).setBounds(x, y+=25, ancho, 20);
		panel.add(cmbEstablecimiento).setBounds(x+ancho, y, ancho*3, 20);
		panel.add(btnBuscar).setBounds(x+ancho*3-20, y+=25, ancho+20, 30);
		
		btnBuscar.addActionListener(opBuscar);
		
		cont.add(panel);
	}
	
	ActionListener opBuscar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			System.out.println(cfecha.getDate());
			
			if(cfecha.getDate() != null){
				System.out.println("Fecha correcta");
				
				String fecha = new SimpleDateFormat("dd/MM/yyyy").format(cfecha.getDate());
				
				System.out.println(fecha);
				System.out.println(cmbEstablecimiento.getSelectedIndex());
				
				
				if(cmbEstablecimiento.getSelectedIndex()!=0){
					reporte(cmbEstablecimiento.getSelectedItem().toString().trim(), fecha);
				}else{
					JOptionPane.showMessageDialog(null, "Es Necesario Seleccionar Un Establecimiento", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
					return;
				}
				
			}else{
				JOptionPane.showMessageDialog(null, "La Fecha Ingresada Es Incorrecta", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}
			
			
			
			
		}
	};
	
	public void reporte(String establecimiento, String fecha){
		String basedatos="2.26";
		String vista_previa_reporte="no";
		int vista_previa_de_ventana=0;
		String comando= "exec buscar_pedidos_por_establecimiento_al_dia '"+establecimiento+"','"+fecha+"'";
		String reporte ="Obj_Reporte_De_Pedido_Por_Establecimiento.jrxml";
		 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
	}

	public static void main(String[] args) {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Consulta_De_Pedidos_Realizados_Por_Establecimieto().setVisible(true);
		}catch(Exception e){
			System.err.println("Error :"+ e.getMessage());
		}
	}

}
