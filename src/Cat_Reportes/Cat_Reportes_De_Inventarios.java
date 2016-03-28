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
public class Cat_Reportes_De_Inventarios extends JFrame{
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	String establecimiento[] = new Obj_Establecimiento().Combo_Establecimiento201();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbEstablecimiento = new JComboBox(establecimiento);
	
	JCButton btninventario_transf90 = new JCButton("Inventario De Productos Recepcionados En 90 Dias Por Establecimiento","Lista.png","Azul");
	JCButton btngenerar = new JCButton("Generar","buscar-buscar-ampliar-icono-6234-32.png","Naranja");

	int tipo_Reporte = 0;
	
	public Cat_Reportes_De_Inventarios(){
		setSize(570,200);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Reportes De Inventarios");
		setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/articulo-icono-9036-48.png"));
		panel.setBorder(BorderFactory.createTitledBorder("Seleccione el Tipo de Reporte "));
		
		int x=20, y=25, width=260,height=30;
		panel.add(btninventario_transf90).setBounds              (x  ,y    ,width*2 ,height   );
		panel.add(new JLabel("Folio:")).setBounds                (x  ,y+=50,width   ,20       );		
		panel.add(cmbEstablecimiento).setBounds                  (60 ,y    ,195     ,20       );
		panel.add(btngenerar).setBounds                          (x  ,y+=45,width   ,height   );
		cmbEstablecimiento.setEnabled(false);
	    btngenerar.setEnabled(false);
	    
		cont.add(panel);
		btngenerar.addActionListener(opGenerar);
		btninventario_transf90.addActionListener(opReporte_Por_Folio);
	}
	
	ActionListener opReporte_Por_Folio = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			cmbEstablecimiento.setEnabled(true);
			btngenerar.setEnabled(true);
			tipo_Reporte=1;
		}
	};
	
	ActionListener opGenerar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String basedatos="2.200";
			String vista_previa_reporte="no";
			int vista_previa_de_ventana=0;
			String comando="";
			String reporte = "";
			
			if(tipo_Reporte==1){
						if(cmbEstablecimiento.getSelectedIndex()==0){
							JOptionPane.showMessageDialog(null,"Debe de Seleccionar Un Establecimiento: ","Aviso!", JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
							return;		
						}else{
							 comando = "exec sp_reporte_de_productos_con_existencia '"+cmbEstablecimiento.getSelectedItem().toString().trim()+"'" ;
   							 reporte="Obj_Reporte_De_Inventario_De_Productos_Transferidos_en_los_ultimos_90_dias_Exportar.jrxml";
					    }
			}else{
			 }
			 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
			 return;
		}
	};
	
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Reportes_De_Inventarios().setVisible(true);
		}catch(Exception e){	}
	}
}
