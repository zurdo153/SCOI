package Cat_Reportes;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import Conexiones_SQL.Generacion_Reportes;
import Obj_Principal.Componentes;
import Obj_Principal.JCTextField;

@SuppressWarnings("serial")
public class Cat_Reportes_De_Diferencias_Entre_Dos_Nominas extends JFrame {
	
	 JTextField txtnomina1 = new Componentes().text(new JCTextField(), "Nomina 1", 10, "Int");
	 JTextField txtnomina2 = new Componentes().text(new JCTextField(), "Nomina 2", 10, "Int");
	 JButton btnReporte              = new JButton("",new ImageIcon("imagen/buscar.png"));
	 JButton btnReporte_prenomina    = new JButton("",new ImageIcon("imagen/buscar.png"));
	 JButton btnReporte_diferiencias = new JButton("",new ImageIcon("imagen/buscar.png"));
	 
	 Container cont = getContentPane();
 	 JLayeredPane panel = new JLayeredPane();
	 Border blackline, etched, raisedbevel, loweredbevel, empty;
	 TitledBorder title4; 

	public Cat_Reportes_De_Diferencias_Entre_Dos_Nominas() {
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/dinero-en-efectivo-cartera-monedero-icono-7127-32.png"));
		 blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
		 panel.setBorder(BorderFactory.createTitledBorder(blackline,"Teclee Los Folios De Nomina Que Desea Comparar"));
		this.setTitle("Diferencias Entre Dos Nominas");
		 btnReporte.setText(	"<html> <FONT FACE="+"arial"+" SIZE=3 COLOR=BLACk>" +
				"		<CENTER><p>Reporte De Diferencias Entre Dos Nominas</p></CENTER></FONT>" +
				"</html>");
		 btnReporte_prenomina.setText(	"<html> <FONT FACE="+"arial"+" SIZE=3 COLOR=BLACk>" +
					"		<CENTER><p>Reporte De Diferencias Entre Prenomina y Nomina</p></CENTER></FONT>" +
					"</html>");
		 
		 btnReporte_diferiencias.setText(	"<html> <FONT FACE="+"arial"+" SIZE=3 COLOR=BLACk>" +
					"		<CENTER><p>Reporte De Diferencias Entre Nomina y Lista De Raya Actual"
					+ "</p></CENTER></FONT>" +
					"</html>");
		 
		 int x=20,y=25,width=100,height=20;
		 
		panel.add(txtnomina1).setBounds(x, y, width, height);
		panel.add(txtnomina2).setBounds(x+200, y, width, height);
		panel.add(btnReporte).setBounds(x,y+=40,width*3,height*2);
		panel.add(btnReporte_prenomina).setBounds(x,y+=60,width*3,height*2);
		panel.add(btnReporte_diferiencias).setBounds(x,y+=60,width*3,height*2);
		
		btnReporte.addActionListener(opGenerar);
		btnReporte_prenomina.addActionListener(opGenerarconPrenomina);
		btnReporte_diferiencias.addActionListener(opGenerarConProblemas_En_Nomina);
		
		txtnomina1.addKeyListener(op_a_bnomina2);
		txtnomina2.addKeyListener(op_buscar_desde_nomina2);
		
		cont.add(panel);
		this.setSize(350,280);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	ActionListener opGenerar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(txtnomina1.getText().toString().equals("")||txtnomina2.getText().toString().equals("")){
			  JOptionPane.showMessageDialog(null,"Tiene Que Alimentar Los folios De las Dos Nominas Que Desea Comparar", "Aviso!",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));	
               return;
			}else{
				if(Integer.valueOf(txtnomina1.getText().toString())>Integer.valueOf(txtnomina2.getText().toString())){	
					String basedatos="2.200";
					String vista_previa_reporte="no";
					int vista_previa_de_ventana=0;
					String comando="exec sp_Reporte_IZAGAR_de_Diferencias_Entre_Dos_Nominas "+txtnomina1.getText().toString()+","+txtnomina2.getText().toString()  ;
					String reporte = "Obj_Reporte_De_Diferencias_Entre_Dos_Nominas.jrxml";
				   new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
				}else{
					  JOptionPane.showMessageDialog(null,"El folio De La Nomina 1 \nDebe De Ser Mayor Que \nEl  folio De La Nomina 2 \nPara Poder Buscar", "Aviso!",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));	
					  txtnomina2.setText("");
					  return;
				}
			}
		}
	};
	
	ActionListener opGenerarconPrenomina = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(txtnomina1.getText().toString().equals("")){
			  JOptionPane.showMessageDialog(null,"Tiene Que Alimentar El folio de La Nomina 1 Que Desea Comparar Con La Prenomina", "Aviso!",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));	
               return;
			}else{
				if(txtnomina2.getText().toString().equals("")){	
					String basedatos="2.200";
					String vista_previa_reporte="no";
					int vista_previa_de_ventana=0;
					String comando="exec sp_Reporte_IZAGAR_de_Diferencias_Entre_PreNomina_Y_una_Nomina "+txtnomina1.getText().toString() ;
					String reporte = "Obj_Reporte_De_Diferencias_Entre_Dos_Nominas.jrxml";
				   new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
				   
				}else{
					  JOptionPane.showMessageDialog(null,"El folio De La Nomina 2 Debe estar vacio Para Poder Buscar", "Aviso!",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));	
					  txtnomina2.setText("");
					  return;
				}
			}
		}
	};
	
	ActionListener opGenerarConProblemas_En_Nomina = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(txtnomina1.getText().toString().equals("")){
			  JOptionPane.showMessageDialog(null,"Tiene Que Alimentar El folio de La Nomina 1 Que Desea Comparar Con La Prenomina", "Aviso!",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));	
               return;
			}else{
				if(txtnomina2.getText().toString().equals("")){	
					String basedatos="2.26";
					String vista_previa_reporte="no";
					int vista_previa_de_ventana=0;
					String comando="exec sp_Reporte_De_Empleados_Que_No_Se_Les_Aplicara_Deposito_En_Nomina "+txtnomina1.getText().toString() ;
					String reporte = "Obj_Reporte_De_Empleados_Con_Problemas_En_Nomina.jrxml";
				   new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
				   
				}else{
					  JOptionPane.showMessageDialog(null,"El folio De La Nomina 2 Debe estar vacio Para Poder Buscar", "Aviso!",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));	
					  txtnomina2.setText("");
					  return;
				}
			}
		}
	};
	
	
	
    KeyListener op_a_bnomina2 = new KeyListener(){
			public void keyReleased(KeyEvent e1) {
			}
			public void keyTyped(KeyEvent e1) {
			}
			public void keyPressed(KeyEvent e1) {
				if(e1.getKeyCode()==KeyEvent.VK_ENTER){
				txtnomina2.requestFocus();
				}
			    }
		    };
		    
    KeyListener op_buscar_desde_nomina2 = new KeyListener(){
				public void keyReleased(KeyEvent e1) {
				}
				public void keyTyped(KeyEvent e1) {
				}
				public void keyPressed(KeyEvent e1) {
					if(e1.getKeyCode()==KeyEvent.VK_ENTER){
					btnReporte.doClick();
					}
				    }
			    };	    
		    
		    

	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Reportes_De_Diferencias_Entre_Dos_Nominas().setVisible(true);
		}catch(Exception e){	}
	}
}
