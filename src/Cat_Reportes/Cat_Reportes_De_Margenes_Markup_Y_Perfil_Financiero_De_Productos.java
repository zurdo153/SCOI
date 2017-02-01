package Cat_Reportes;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import Cat_Principal.Cat_Comandos;
import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Generacion_Reportes;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.JCButton;

@SuppressWarnings("serial")
public class Cat_Reportes_De_Margenes_Markup_Y_Perfil_Financiero_De_Productos extends JFrame{
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	String establecimiento[] = new Obj_Establecimiento().Combo_Establecimiento201();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbEstablecimiento = new JComboBox(establecimiento);
	
	String operador[] = {"Selecciona Un Reporte","Reporte De Markup De Productos Por Establecimiento"};
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbConcepto = new JComboBox(operador);
	
	JCButton btngenerar_reporte = new JCButton("Generar Reporte En Pantalla","hoja-de-calculo-icono-8865-32.png","Azul");
	JCButton btngenerar_excel   = new JCButton("Generar Reporte En XLS ","xls-icon-3376-32px.png","Verde");
	
	int tipo_Reporte = 0;
	
	public Cat_Reportes_De_Margenes_Markup_Y_Perfil_Financiero_De_Productos(){
		setSize(450,250);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Reportes De Margenes Markup y Perfil Financiero De Productos");
		setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/articulo-icono-9036-48.png"));
		panel.setBorder(BorderFactory.createTitledBorder("Seleccione Establecimiento, el Tipo de Reporte y Genere El Reporte "));
		
		int x=20, y=25, width=400,height=20;
		panel.add(cmbEstablecimiento).setBounds                  (x    ,y       ,width   ,height   );
		panel.add(cmbConcepto).setBounds                         (x    ,y+=30   ,width   ,height   );
		
		x=80;width=300;
		panel.add(btngenerar_reporte).setBounds                  (x    ,y+=50   ,width   ,height*2 );
		panel.add(btngenerar_excel).setBounds                    (x    ,y+=50   ,width   ,height*2 );
		
		cmbEstablecimiento.requestFocus();
		cont.add(panel);
		btngenerar_reporte.addActionListener(opGenerar_reporte);
		btngenerar_excel.addActionListener(opGenerar_XLS);
	}
	
	ActionListener opGenerar_reporte = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String basedatos="2.200";
			String vista_previa_reporte="no";
			int vista_previa_de_ventana=0;
			String comando= "";
			String reporte = "";
			
			if(cmbEstablecimiento.getSelectedIndex()==0){
			   JOptionPane.showMessageDialog(null,"Debe de Seleccionar Un Establecimiento: ","Aviso!", JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
			      cmbEstablecimiento.requestFocus();
				  cmbEstablecimiento.showPopup();
			   	 return;		
			}else{
				if(cmbConcepto.getSelectedIndex()==0){
			    	 JOptionPane.showMessageDialog(null,"Debe de Seleccionar Un Tipo De Reporte","Aviso!", JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
			    	    cmbConcepto.requestFocus();
			    	    cmbConcepto.showPopup();
				     return;		
				 }else{ 
					 String concepto=cmbConcepto.getSelectedItem().toString().trim();
				 	 comando = new Cat_Comandos().markup(concepto, cmbEstablecimiento.getSelectedItem().toString().trim());				 	 
			 	     
				 if(concepto.equals("Reporte De Markup De Productos Por Establecimiento")){
					 reporte="Obj_Reporte_De_Markup_y_Margen_De_Productos_Por_Establecimiento.jrxml";
				  }
				 
		    }
			    new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
			   return;
			}	 
		}
	};
	
	
	ActionListener opGenerar_XLS = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String basedatos="2.200";
			String vista_previa_reporte="no";
			int vista_previa_de_ventana=0;
			String comando= "";
			String reporte = "";
			String fecha_guardado="";
				try {fecha_guardado=new BuscarSQL().fecha_guardado(); } catch (SQLException e1) {e1.printStackTrace();}
			
			if(cmbEstablecimiento.getSelectedIndex()==0){
			   JOptionPane.showMessageDialog(null,"Debe de Seleccionar Un Establecimiento: ","Aviso!", JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
			      cmbEstablecimiento.requestFocus();
				  cmbEstablecimiento.showPopup();
			   	 return;		
			}else{
				  if(cmbConcepto.getSelectedIndex()==0){
			    	 JOptionPane.showMessageDialog(null,"Debe de Seleccionar Un Tipo De Reporte","Aviso!", JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
			    	    cmbConcepto.requestFocus();
			    	    cmbConcepto.showPopup();
				     return;		
				  }else{ 
						 String concepto=cmbConcepto.getSelectedItem().toString().trim();
					 	 comando = new Cat_Comandos().markup(concepto, cmbEstablecimiento.getSelectedItem().toString().trim());			 	 
				 	     
					 if(concepto.equals("Reporte De Markup De Productos Por Establecimiento")){
						 reporte="Obj_Reporte_De_Markup_y_Margen_De_Productos_Por_Establecimiento.jrxml";
					  }				     
   				     
		    }
				  new Generacion_Reportes().Reporte_Guardado(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana,"xls",cmbConcepto.getSelectedItem().toString().trim()+" "+cmbEstablecimiento.getSelectedItem().toString().trim()+"_"+fecha_guardado);
			   return;
			}
		}
	};
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Reportes_De_Margenes_Markup_Y_Perfil_Financiero_De_Productos().setVisible(true);
		}catch(Exception e){	}
	}
}
