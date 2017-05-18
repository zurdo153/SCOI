package Cat_Reportes;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import Conexiones_SQL.Generacion_Reportes;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.JCButton;

@SuppressWarnings("serial")
public class Cat_Reportes_De_Perfil_Financiero_De_Producto extends JFrame {
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	String[] establecimiento = new Obj_Establecimiento().Combo_Establecimiento201();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbEstablecimiento = new JComboBox(establecimiento);
	
	String operador[] = {"Selecciona Un Reporte"
							,"Reporte De Perfil Financiero De Productos Por Establecimiento" 
							};
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbConcepto = new JComboBox(operador);
	
	JCButton btngenerar_reporte = new JCButton("Generar Reporte En Pantalla","hoja-de-calculo-icono-8865-32.png","Azul");
	public Cat_Reportes_De_Perfil_Financiero_De_Producto(){
		this.setSize(430,250);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("Reportes De Perfil Financiero De Productos");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/articulo-icono-9036-48.png"));
		this.panel.setBorder(BorderFactory.createTitledBorder("Seleccione  La Fecha, El Tipo de Reporte, el Establecimiento y Genere El Reporte "));
		int x=20, y=25, width=380,height=20;
		this.panel.add(cmbConcepto).setBounds                   (x     ,y      ,width    ,height   );
		this.panel.add(new JLabel("Establecimiento:")).setBounds(x     ,y+=35  ,width    ,height   );
		this.panel.add(cmbEstablecimiento).setBounds            (x+80  ,y      ,width/2  ,height   );
		this.panel.add(btngenerar_reporte).setBounds            (x     ,y+=50  ,width    ,height*2 );
		this.cont.add(panel);
		btngenerar_reporte.addActionListener(opGenerar_reporte);
	}
	
	public String validar_campos(){
		String error = "";
		if(cmbEstablecimiento.getSelectedIndex()==0)error+= "Seleccionar Un Establecimiento\n";
		return error;
	}
	
	ActionListener opGenerar_reporte = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
					String basedatos="2.94";
					String vista_previa_reporte="no";
					int vista_previa_de_ventana=0;
					String comando= "";
					String reporte = "";
			 if(cmbConcepto.getSelectedIndex()==0){
			       JOptionPane.showMessageDialog(null,"Debe de Seleccionar Un Tipo De Reporte","Aviso!", JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
			        cmbConcepto.requestFocus();
			        cmbConcepto.showPopup();
				    return;		
			      }else{ 
						if(validar_campos().equals("")){
							 String concepto=cmbConcepto.getSelectedItem().toString().trim();
								if(concepto.equals("Reporte De Perfil Financiero De Productos Por Establecimiento" )){
										comando="reporte_de_perfil_financiero_de_productos_por_establecimiento_con_categorias '"+cmbEstablecimiento.getSelectedItem().toString().trim()+"'";
										reporte ="Obj_Reporte_De_Perfil_Financiero_De_Productos_Por_Establecimiento.jrxml";
								   }

						}else{
						  JOptionPane.showMessageDialog(null, "Los Siguientes Campos Estan Vacios y Se Necesitan Para La Consulta:\n "+validar_campos(),"Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
			               return;
						} 
		       }
			    new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
			   return;
		}
	};
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Reportes_De_Perfil_Financiero_De_Producto().setVisible(true);
		}catch(Exception e){	}
	}

}
