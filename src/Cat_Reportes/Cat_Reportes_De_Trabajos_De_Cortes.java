package Cat_Reportes;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

import com.toedter.calendar.JDateChooser;
import Cat_Lista_de_Raya.Cat_Filtro_De_Listas_De_Raya_Pasadas;
import Conexiones_SQL.Generacion_Reportes;
import Obj_Principal.Componentes;

@SuppressWarnings("serial")
public class Cat_Reportes_De_Trabajos_De_Cortes extends JFrame{
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	JTextField txtFolio = new Componentes().text(new JTextField(), "Folio del Corte", 15, "String");
	
	JButton btnReporte_porfolio = new JButton("",new ImageIcon("imagen/fast-food-icon16.png"));

	JButton btnReporte_actual = new JButton("",new ImageIcon("imagen/plan-icono-5073-16.png"));
	JButton btnReporte_porfecha = new JButton("",new ImageIcon("imagen/Calendar.png"));
	JButton btnReporte_por_deunafecha = new JButton("",new ImageIcon("imagen/Calendar.png"));
    JButton btnSeleccionLR =new JButton("",new ImageIcon ("imagen/Filter-List-icon16.png"));
	JButton btngenerar = new JButton("Generar",new ImageIcon("imagen/buscar.png"));
	JDateChooser cfecha = new JDateChooser();
	
	int tipo_Reporte = 0;
	
	public Cat_Reportes_De_Trabajos_De_Cortes(){
		setSize(305,370);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Reportes De Fuente De Sodas");
		setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/fast-food-icon32.png"));
		panel.setBorder(BorderFactory.createTitledBorder("Seleccione el Tipo de Reporte"));
	
		btnReporte_actual.setText(	"<html> <FONT FACE="+"arial"+" SIZE=3 COLOR=BLACk>" +
				"		<CENTER><p>Reporte De Fuente De Sodas A Cobrar En Lista De Raya Actual </p></CENTER></FONT>" +
				"</html>");	
		
		btnReporte_porfolio.setText(	"<html> <FONT FACE="+"arial"+" SIZE=3 COLOR=BLACk>" +
				"		<CENTER><p>Reporte De Fuente De Sodas Cobrada Por Folio De Una Lista De Raya</p></CENTER></FONT>" +
				"</html>");
		
		btnReporte_porfecha.setText(	"<html> <FONT FACE="+"arial"+" SIZE=3 COLOR=BLACk>" +
				"		<CENTER><p>Reporte De Fuente De Sodas Sin Cobro  Hasta Una Fecha </p></CENTER></FONT>" +
				"</html>");	
		btnReporte_por_deunafecha.setText(	"<html> <FONT FACE="+"arial"+" SIZE=3 COLOR=BLACk>" +
				"		<CENTER><p>Reporte De Fuente De Sodas De Una Fecha</p></CENTER></FONT>" +
				"</html>");	
		

		
		int x=20,y=25;
		
		panel.add(btnReporte_actual).setBounds(x,y,260,40);
		panel.add(btnReporte_porfolio).setBounds(x,y+=50,260,40);
		panel.add(btnReporte_porfecha).setBounds(x,y+=50,260,40);
		panel.add(btnReporte_por_deunafecha).setBounds(x,y+=50,260,40);		
		
		panel.add(new JLabel("Folio:")).setBounds(x+10,y+=50,200,20);		
		panel.add(txtFolio).setBounds(x+60,y,175,20);
		panel.add(btnSeleccionLR).setBounds(x+235,y,20,20);
		panel.add(new JLabel("Fecha:")).setBounds(x+10,y+=25,200,20);
		panel.add(cfecha).setBounds(x+60,y,195,20);
		panel.add(btngenerar).setBounds(x+80,y+=50,120,30);
	    
	    txtFolio.setEditable(false);
	    cfecha.setEnabled(false);
	    btngenerar.setEnabled(false);
	    btnSeleccionLR.setEnabled(false);
	    
		cont.add(panel);
		btngenerar.addActionListener(opGenerar);
		btnReporte_actual.addActionListener(opReporte_Actual);
		btnReporte_porfolio.addActionListener(opReporte_Por_Folio);
		btnReporte_porfecha.addActionListener(opReporte_Por_Fecha);
		btnReporte_por_deunafecha.addActionListener(opReporte_De_Una_Fecha);

		btnSeleccionLR.addActionListener(opfiltroLR);
		
     	///filtro de Listas de Raya
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), "buscarLR");
          getRootPane().getActionMap().put("buscarLR", new AbstractAction(){
         public void actionPerformed(ActionEvent e)
         {                 	    btnSeleccionLR.doClick();
       	    }
     });
	}
	
	ActionListener opReporte_Por_Folio = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			txtFolio.setEditable(true);
			cfecha.setEnabled(false);
			btngenerar.setEnabled(true);
			btnSeleccionLR.setEnabled(true);
			tipo_Reporte=1;
			cfecha.setDate(null);
			txtFolio.requestFocus();
		}
	};

	ActionListener opReporte_Actual = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			txtFolio.setEditable(false);
			cfecha.setEnabled(false);
			btngenerar.setEnabled(false);
			btnSeleccionLR.setEnabled(false);
			txtFolio.setText("");
			cfecha.setDate(null);
			Reporte_De_Fuente_De_Sodas();
		}
	};
	
	ActionListener opReporte_Por_Fecha = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			txtFolio.setEditable(false);
			cfecha.setEnabled(true);
			btngenerar.setEnabled(true);
			btnSeleccionLR.setEnabled(false);
			tipo_Reporte=2;
			txtFolio.setText("");
		}
	};
	
	ActionListener opReporte_De_Una_Fecha = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			txtFolio.setEditable(false);
			cfecha.setEnabled(true);
			btngenerar.setEnabled(true);
			btnSeleccionLR.setEnabled(false);
			tipo_Reporte=3;
			txtFolio.setText("");
		}
	};
	
	ActionListener opfiltroLR = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			 new Cat_Filtro_De_Listas_De_Raya_Pasadas(1).setVisible(true);
		}
	};
	
	public String validar_fechas(){
		String error = "";
		@SuppressWarnings("unused")
		String fechafinalNull = cfecha.getDate()+"";
	    if(cfecha.equals("null"))error+= "Fecha\n";
		return error;
	}
	
	String basedatos="2.26";
	String vista_previa_reporte="no";
	int vista_previa_de_ventana=0;
	String comando="";
	String reporte = "";
	
	public  void obtiene_lista_de_raya_selecionada(final Integer folio){
	     String	Foliorecibido = folio+"";
		 comando = "exec sp_Reporte_De_Fuente_De_Sodas_De_Lista_De_Raya_Por_Folio "+Foliorecibido  ;
		 reporte="Obj_Reporte_De_Fuente_De_Sodas.jrxml";
		 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
			txtFolio.setText(Foliorecibido);
			return;	
	}
	
	ActionListener opGenerar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(tipo_Reporte==1){
						if(!txtFolio.getText().equals("")){
							 comando ="exec sp_Reporte_De_Fuente_De_Sodas_De_Lista_De_Raya_Por_Folio "+Integer.valueOf(txtFolio.getText());
							 reporte="Obj_Reporte_De_Fuente_De_Sodas.jrxml";
							 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
						}else{
							JOptionPane.showMessageDialog(null,"Debes de Teclear Un Folio de Lista de Raya \n O Seleccionarla de la Lista  Siguiente ","Aviso!", JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
							 new Cat_Filtro_De_Listas_De_Raya_Pasadas(1).setVisible(true);
							return;		
					    }
			 }
			if(tipo_Reporte==2){
				 String fechaNull = cfecha.getDate()+"";
				   if(fechaNull.equals("null")){
						JOptionPane.showMessageDialog(null,"Necesita Selecionar una Fecha o la Fecha tecleada es Incorrecta","Mensaje",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
						return;
					   }else{
						     String fecha = new SimpleDateFormat("dd/MM/yyyy").format(cfecha.getDate());
							 comando = "exec sp_Reporte_De_Fuente_De_Sodas_Sin_Cobro_En_lista_De_Raya'"+fecha+"'" ;
							 reporte="Obj_Reporte_De_Fuente_De_Sodas_de_Empleados_Sin_Cobro_En_Listas_de_Raya.jrxml";
							 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
					   }
			}
			if(tipo_Reporte==3){
				 String fechaNull = cfecha.getDate()+"";
				   if(fechaNull.equals("null")){
						JOptionPane.showMessageDialog(null,"Necesita Selecionar una Fecha o la Fecha tecleada es Incorrecta","Mensaje",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
						return;
					   }else{
						     String fecha = new SimpleDateFormat("dd/MM/yyyy").format(cfecha.getDate());
							 comando = "exec sp_Reporte_De_Fuente_De_Sodas_Por_Fecha'"+fecha+"'" ;
							 reporte="Obj_Reporte_De_Fuente_De_Sodas_de_Empleados_Por_Fecha.jrxml";
							 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
					   }
			}
			
			
		}
	};
	
	
		public void Reporte_De_Fuente_De_Sodas() {
		 comando = "exec sp_Reporte_De_Fuente_De_Sodas_De_Lista_De_Raya_Actual " ;
		 reporte="Obj_Reporte_De_Fuente_De_Sodas.jrxml";
		 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
		}
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Reportes_De_Trabajos_De_Cortes().setVisible(true);
		}catch(Exception e){	}
	}
}
