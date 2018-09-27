package Cat_Lista_de_Raya;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

import Conexiones_SQL.Generacion_Reportes;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;

@SuppressWarnings("serial")
public class Cat_Reportes_De_Lista_De_Raya extends JDialog{
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JTextField txtFolio = new Componentes().text(new JTextField(), "Folio del Corte", 15, "String");
	
	JButton btnReporte_actual = new JCButton(""       ,"plan-icono-5073-16.png","Azul");
	JButton btnReporte_Firmas = new JCButton(""       ,"plan-icono-5073-16.png","Azul");
    JButton btnSeleccionLR    = new JCButton(""       ,"Filter-List-icon16.png","Azul");
	JButton btngenerar        = new JCButton("Generar","buscar.png","Azul");
	
	int tipo_Reporte = 0;
	
	public Cat_Reportes_De_Lista_De_Raya(){
		btnReporte_actual.setEnabled(false);
		Constructor();
	}
	
	public Cat_Reportes_De_Lista_De_Raya(int Parametro){
		Constructor();
	}
	
	String operador[] = {"Selecciona Un Reporte"
			,"Reporte De  Diferencias De Lista De Raya Actual y Pasada" 
			,"Reporte De Lista De Raya Actual Por Apellido Paterno"
			,"Reporte De Lista De Raya Pasada"
			,"Reporte De Lista De Raya Pasada Limpio"
			,"Reporte De Lista De Raya Pasada Con Totales"
			,"Reporte De Lista De Raya Pasada Solo Totales"
			};
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbConcepto = new JComboBox(operador);
	
	public void Constructor(){
		setSize(350,280);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Reportes De Lista De Raya");
		setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/plan-icono-5073-16.png"));
		panel.setBorder(BorderFactory.createTitledBorder("Seleccione el Tipo de Reporte"));
		
		btnReporte_actual.setText(	"<html> <FONT FACE="+"arial"+" SIZE=4 COLOR=WHITE>" +
				"		<CENTER><p>Reporte De Lista De Raya Actual</p></CENTER></FONT>" +
				"</html>");	
		
		btnReporte_Firmas.setText(	"<html> <FONT FACE="+"arial"+" SIZE=4 COLOR=WHITE>" +
				"		<CENTER><p>Reporte De Firmas De Lista De Raya Actual</p></CENTER></FONT>" +
				"</html>");	
		int x=20,y=25;
		
		panel.add(cmbConcepto).setBounds         (x   ,y     ,300 ,20);
		panel.add(btnReporte_actual).setBounds   (x   ,y+=30 ,300 ,40);
		panel.add(btnReporte_Firmas).setBounds   (x   ,y+=50 ,300 ,40);
		panel.add(new JLabel("Folio:")).setBounds(x+10,y+=50 ,200 ,20);		
		panel.add(txtFolio).setBounds            (x+50,y     ,185 ,20);
		panel.add(btnSeleccionLR).setBounds      (255 ,y     ,20  ,20);
		panel.add(btngenerar).setBounds          (100 ,y+=30 ,120 ,30);
	    
	    txtFolio.setEditable(false);
	    btngenerar.setEnabled(false);
	    btnSeleccionLR.setEnabled(false);
	    
		cont.add(panel);
		
		btngenerar.addActionListener(opGenerar);
		
		cmbConcepto.addActionListener(opReporte_Diferencias_Lista_de_Rayas);
		btnReporte_actual.addActionListener(opReporte_Actual);
		btnSeleccionLR.addActionListener(opfiltroLR);
		btnReporte_Firmas.addActionListener(opReporte_Lista_De_firmas);
		
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
			 if(cmbConcepto.getSelectedIndex()==0){
			       JOptionPane.showMessageDialog(null,"Debe de Seleccionar Un Tipo De Reporte","Aviso!", JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
			        cmbConcepto.requestFocus();
			        cmbConcepto.showPopup();
				    return;		
			      }else{ 
			        String concepto=cmbConcepto.getSelectedItem().toString().trim();
							 
					if(concepto.equals("Reporte De  Diferencias De Lista De Raya Actual y Pasada" )){
						btnSeleccionLR.setEnabled(false);
						txtFolio.setEditable(false);
						btngenerar.setEnabled(false);
						txtFolio.setText("");
						tipo_Reporte=1;
						reporte = "Obj_Reporte_De_Diferencias_Entre_Lista_De_Raya_Actal_y_Lista_De_Raya_Pasada.jrxml";
						comando = "exec sp_Reporte_De_Diferencias_De_lista_De_Raya_Actual_Contra_La_Semana_Pasada "  ;
						 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
						 return;
					 }
					
					if(concepto.equals("Reporte De Lista De Raya Actual Por Apellido Paterno")){
						txtFolio.setEditable(false);
						btngenerar.setEnabled(true);
						btnSeleccionLR.setEnabled(false);
						tipo_Reporte=2;
						txtFolio.setText("");
						txtFolio.requestFocus();
						
						 reporte = "Obj_Reporte_De_Lista_de_Raya_Actual_Por_Ap_Paterno.jrxml";
						 comando = "exec sp_Reporte_De_Lista_De_Raya_Por_Ap_Paterno ";
						 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana); 
						 return;
					 }
					
					if(concepto.equals("Reporte De Lista De Raya Pasada")){
						txtFolio.setEditable(true);
						btngenerar.setEnabled(true);
						btnSeleccionLR.setEnabled(true);
						tipo_Reporte=3;
						txtFolio.setText("");
						txtFolio.requestFocus();
					 }
					
					if(concepto.equals("Reporte De Lista De Raya Pasada Limpio")){
						txtFolio.setEditable(true);
						btngenerar.setEnabled(true);
						btnSeleccionLR.setEnabled(true);
						tipo_Reporte=9;
						txtFolio.setText("");
						txtFolio.requestFocus();
					 }
					
					if(concepto.equals("Reporte De Lista De Raya Pasada Con Totales")){
						txtFolio.setEditable(true);
						btngenerar.setEnabled(true);
						btnSeleccionLR.setEnabled(true);
						tipo_Reporte=10;
						txtFolio.setText("");
						txtFolio.requestFocus();
					 }
					
					if(concepto.equals("Reporte De Lista De Raya Pasada Solo Totales")){
						txtFolio.setEditable(true);
						btngenerar.setEnabled(true);
						btnSeleccionLR.setEnabled(true);
						tipo_Reporte=11;
						txtFolio.setText("");
						txtFolio.requestFocus();
					 }
					
			      }
	 }
	};
	
	ActionListener opGenerar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(txtFolio.getText().equals("")){
				  JOptionPane.showMessageDialog(null, "Es Requerido Que Teclee un Folio De Lista De Raya","Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
                  return;	
			}else{
				generar_reporte(Integer.valueOf(txtFolio.getText().toString().trim()),tipo_Reporte);	
		}
		}
	};
	
	public void generar_reporte(final Integer folio,int tipo_Reporte_parametro ){
		tipo_Reporte=tipo_Reporte_parametro;//para reportes desde el filtro de raya

		if(tipo_Reporte==3){
			  reporte = "Obj_Reporte_De_Lista_de_Raya_Pasadas.jrxml";
			  comando = "exec sp_Reporte_De_Lista_De_Raya_Pasada "+folio  ;
		    }
			
		if(tipo_Reporte==9){
			  reporte = "Obj_Reporte_De_Lista_de_Raya_Limpia.jrxml";
			  comando = "exec sp_Reporte_De_Lista_De_Raya_Pasada "+folio  ;
         }
			
		if(tipo_Reporte==10){
				  reporte = "Obj_Reporte_De_Lista_de_Raya_Pasada_Totales_Detalle.jrxml";
				  comando = "exec sp_reporte_de_lista_de_raya_pasada_totales "+folio  ;
	    }
		
		if(tipo_Reporte==11){
			  reporte = "Obj_Reporte_De_Lista_de_Raya_Pasada_Totales.jrxml";
			  comando = "exec sp_reporte_de_lista_de_raya_pasada_totales "+folio  ;
        }
			
		 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
	}
	
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
	
	ActionListener opReporte_Lista_De_firmas = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			dispose();
//			new Cat_Reporte_De_Lista_De_Firmas_De_Lista_De_Raya_Actual().setVisible(true);
			
			String basedatos="2.26";
			String vista_previa_reporte="si";
			int vista_previa_de_ventana=1;
			
			String comando="exec firmas_lista_de_raya_actual";
			String reporte = "Obj_Lista_De_Firmas_LR_Actual.jrxml";
			 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
		}
	};
	
	
	
	ActionListener opfiltroLR = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			 new Cat_Filtro_De_Listas_De_Raya_Pasadas(tipo_Reporte).setVisible(true);
		}
	};
	
	public  void obtiene_lista_de_raya_selecionada_pasada_limpia(final Integer folio){
		 String	Foliorecibido = folio+"";
		 reporte = "Obj_Reporte_De_Lista_de_Raya_Limpia.jrxml";
		 comando = "exec sp_Reporte_De_Lista_De_Raya_Pasada "+Foliorecibido  ;
		 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
		 txtFolio.setText(Foliorecibido);
		 return;	
	}
	
	public  void obtiene_lista_de_raya_selecionada(final Integer folio){
		 String	Foliorecibido = folio+"";
		 reporte = "Obj_Reporte_De_Lista_de_Raya_Pasadas.jrxml";
		 comando = "exec sp_Reporte_De_Lista_De_Raya_Pasada "+Foliorecibido  ;
		 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
		 txtFolio.setText(Foliorecibido);
		 return;	
	}
		
	public  void obtiene_lista_de_raya_selecionada2(final Integer folio){
			 String	Foliorecibido = folio+"";
			 reporte = "Obj_Reporte_De_Plantilla_De_Lista_De_Raya.jrxml";
			 comando = "exec sp_select_plantilla_de_lista_de_raya "+Foliorecibido  ;
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
