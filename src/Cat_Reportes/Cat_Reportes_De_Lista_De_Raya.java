package Cat_Reportes;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

import Cat_Lista_de_Raya.Cat_Filtro_De_Listas_De_Raya_Pasadas;
import Conexiones_SQL.Generacion_Reportes;
import Obj_Principal.Componentes;

@SuppressWarnings("serial")
public class Cat_Reportes_De_Lista_De_Raya extends JDialog{
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JTextField txtFolio = new Componentes().text(new JTextField(), "Folio del Corte", 15, "String");
	
	JButton btnReporte_Diferencias_Lista_Raya = new JButton("",new ImageIcon("imagen/hoja-de-calculo-excel-icono-5223-16.png"));
	JButton btnReporte_porfecha = new JButton("",new ImageIcon("imagen/orange-folder-saved-search-icone-8197-16.png"));
	JButton btnReporte_actual = new JButton("",new ImageIcon("imagen/plan-icono-5073-16.png"));
	JButton btnReporte_Firmas = new JButton("",new ImageIcon("imagen/plan-icono-5073-16.png"));
	JButton btnReporte_Limpio = new JButton("",new ImageIcon("imagen/plan-icono-5073-16.png"));
	
    JButton btnSeleccionLR =new JButton("",new ImageIcon ("imagen/Filter-List-icon16.png"));
	
	JButton btngenerar = new JButton("Generar",new ImageIcon("imagen/buscar.png"));
	
	int tipo_Reporte = 0;
	
	public Cat_Reportes_De_Lista_De_Raya(){
		btnReporte_actual.setEnabled(false);
		Constructor();
		
	}
	
	public Cat_Reportes_De_Lista_De_Raya(int Parametro){
		Constructor();
	}
	
	public void Constructor(){
		setSize(305,380);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Reportes De Lista De Raya");
		setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/plan-icono-5073-16.png"));
		panel.setBorder(BorderFactory.createTitledBorder("Seleccione el Tipo de Reporte"));
	
		btnReporte_Diferencias_Lista_Raya.setText(	"<html> <FONT FACE="+"arial"+" SIZE=3 COLOR=BLACk>" +
				"		<CENTER><p>Reporte De  Diferencias De Lista De Raya Actual y Pasada</p></CENTER></FONT>" +
				"</html>");
		
		btnReporte_porfecha.setText(	"<html> <FONT FACE="+"arial"+" SIZE=3 COLOR=BLACk>" +
				"		<CENTER><p>Reporte De Listas De Raya Pasadas</p></CENTER></FONT>" +
				"</html>");	
		
		btnReporte_actual.setText(	"<html> <FONT FACE="+"arial"+" SIZE=3 COLOR=BLACk>" +
				"		<CENTER><p>Reporte De Lista De Raya Actual</p></CENTER></FONT>" +
				"</html>");	
		
		btnReporte_Firmas.setText(	"<html> <FONT FACE="+"arial"+" SIZE=3 COLOR=BLACk>" +
				"		<CENTER><p>Reporte De Firmas De Lista De Raya Actual</p></CENTER></FONT>" +
				"</html>");	
		btnReporte_Limpio.setText(	"<html> <FONT FACE="+"arial"+" SIZE=3 COLOR=BLACk>" +
				"		<CENTER><p>Reporte De Lista De Raya Limpio</p></CENTER></FONT>" +
				"</html>");	
		
		int x=20,y=25;
		
		panel.add(btnReporte_Diferencias_Lista_Raya).setBounds(x,y,260,40);
		panel.add(btnReporte_porfecha).setBounds(x,y+=50,260,40);
		panel.add(btnReporte_actual).setBounds(x,y+=50,260,40);
		panel.add(btnReporte_Firmas).setBounds(x,y+=50,260,40);
		panel.add(btnReporte_Limpio).setBounds(x,y+=50,260,40);
		
		panel.add(new JLabel("Folio:")).setBounds(x+10,y+=50,200,20);		
		panel.add(txtFolio).setBounds(x+50,y,185,20);
		panel.add(btnSeleccionLR).setBounds(255,y,20,20);
				
		panel.add(btngenerar).setBounds(100,y+=30,120,30);
	    
	    txtFolio.setEditable(false);
	    btngenerar.setEnabled(false);
	    btnSeleccionLR.setEnabled(false);
	    
		cont.add(panel);
		btngenerar.addActionListener(opGenerar);
		btnReporte_Diferencias_Lista_Raya.addActionListener(opReporte_Diferencias_Lista_de_Rayas);
		btnReporte_porfecha.addActionListener(opReporte_Por_Fecha);
		btnReporte_actual.addActionListener(opReporte_Actual);
		btnSeleccionLR.addActionListener(opfiltroLR);
		btnReporte_Firmas.addActionListener(opReporte_Lista_De_firmas);
		btnReporte_Limpio.addActionListener(opReporte_Limpio);
		
     	///filtro de Listas de Raya
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), "buscarLR");
          getRootPane().getActionMap().put("buscarLR", new AbstractAction(){
         public void actionPerformed(ActionEvent e)
         {                 	    btnSeleccionLR.doClick();
       	    }
         });
	}
	
	String basedatos="2.26";
	String vista_previa_reporte="no";
	int vista_previa_de_ventana=0;
	String comando="";
	String reporte = "";
	
	
	ActionListener opReporte_Diferencias_Lista_de_Rayas = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			btnSeleccionLR.setEnabled(false);
			txtFolio.setEditable(false);
			btngenerar.setEnabled(false);
			txtFolio.setText("");
			tipo_Reporte=1;
			 reporte = "Obj_Reporte_De_Diferencias_Entre_Lista_De_Raya_Actal_y_Lista_De_Raya_Pasada.jrxml";
			 comando = "exec sp_Reporte_De_Diferencias_De_lista_De_Raya_Actual_Contra_La_Semana_Pasada "  ;
			 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
		}
	};
	
	ActionListener opReporte_Actual = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			txtFolio.setEditable(false);
			btngenerar.setEnabled(false);
			btnSeleccionLR.setEnabled(false);
			txtFolio.setText("");
			 reporte = "Obj_Reporte_De_Lista_De_Raya_Actual.jrxml";
			 comando = "exec sp_Reporte_De_lista_De_Raya_Actual " ;
			 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
		}
	};
	
	ActionListener opReporte_Por_Fecha = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			txtFolio.setEditable(true);
			btngenerar.setEnabled(true);
			btngenerar.setEnabled(true);
			btnSeleccionLR.setEnabled(true);
			tipo_Reporte=2;
			txtFolio.setText("");
			txtFolio.requestFocus();
			
		}
	};
	
	
	ActionListener opReporte_Lista_De_firmas = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			dispose();
			new Cat_Reporte_De_Lista_De_Firmas_De_Lista_De_Raya_Actual().setVisible(true);;
			
		}
	};
	
	
	ActionListener opReporte_Limpio = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			txtFolio.setEditable(true);
			btngenerar.setEnabled(true);
			btngenerar.setEnabled(true);
			btnSeleccionLR.setEnabled(true);
			tipo_Reporte=3;
			txtFolio.setText("");
			txtFolio.requestFocus();
			
		}
	};
	
	
	ActionListener opfiltroLR = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			 new Cat_Filtro_De_Listas_De_Raya_Pasadas(3).setVisible(true);
		}
	};

	
	ActionListener opGenerar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			if(tipo_Reporte==2){
						if(!txtFolio.getText().equals("")){
							 reporte = "Obj_Reporte_De_Lista_de_Raya_Pasadas.jrxml";
							 comando = "exec sp_Reporte_De_Lista_De_Raya_Pasada "+Integer.valueOf(txtFolio.getText())  ;
//							 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
					    }else{
							  JOptionPane.showMessageDialog(null, "El Campo Folio No Debe De Estar Vacio","Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
		                      return;
							}
		}
			if(tipo_Reporte==3){
				if(!txtFolio.getText().equals("")){
					 reporte = "Obj_Reporte_De_Lista_de_Raya_Limpia.jrxml";
					 comando = "exec sp_Reporte_De_Lista_De_Raya_Pasada "+Integer.valueOf(txtFolio.getText())  ;
//					 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
			    }else{
					  JOptionPane.showMessageDialog(null, "El Campo Folio No Debe De Estar Vacio","Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
                      return;
					}
}
			 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
		}
	};
	
	
		public  void obtiene_lista_de_raya_selecionada(final Integer folio){
			 String	Foliorecibido = folio+"";
			 reporte = "Obj_Reporte_De_Lista_de_Raya_Pasadas.jrxml";
			 comando = "exec sp_Reporte_De_Lista_De_Raya_Pasada "+Foliorecibido  ;
			 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
			 txtFolio.setText(Foliorecibido);
			 return;	
		}
	
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Reportes_De_Lista_De_Raya().setVisible(true);
		}catch(Exception e){	}
	}
}
