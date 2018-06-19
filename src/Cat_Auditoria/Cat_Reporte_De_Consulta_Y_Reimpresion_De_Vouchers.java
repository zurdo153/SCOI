package Cat_Auditoria;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.border.Border;

import Conexiones_SQL.Generacion_Reportes;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;

@SuppressWarnings("serial")
public class Cat_Reporte_De_Consulta_Y_Reimpresion_De_Vouchers extends JFrame{
	Obj_Usuario usuario = new Obj_Usuario().LeerSession();
	
	Container cont = getContentPane();
	JLayeredPane campo = new JLayeredPane();
	JTextField txtFolio           = new Componentes().text(new JCTextField(),"Folio Ticket/Remision"  ,25     ,"String");
	JCButton btnconsultavoucher              = new JCButton("Buscar"                                ,"buscar.png","Azul");
	
	String transaccion[] = {"Remision","Ticket"};
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbtransaccion = new JComboBox(transaccion);
	
	Border blackline, etched, raisedbevel, loweredbevel, empty;
	
 	public Cat_Reporte_De_Consulta_Y_Reimpresion_De_Vouchers() {
		this.setSize(400,180);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.blackline = BorderFactory.createLineBorder(new java.awt.Color(100,100,100));
 		this.campo.setBorder(BorderFactory.createTitledBorder(blackline, "Consulta e Impresion De Vouchers"));
 		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/tarjeta-de-credito-visa-icono-8242-32.png"));
 		this.setTitle("Consulta E Impresion de Vouchers");
		
		campo.add(new JLabel("Teclee el Folio Remision(20)/Ticket(38):")).setBounds(10   ,30  ,200  ,20);
		campo.add(txtFolio).setBounds                                              (200  ,30  ,150  ,20);
		campo.add(new JLabel("Selecciona Remision o Ticket:")).setBounds           (45   ,60  ,150  ,20);
		campo.add(cmbtransaccion).setBounds                                        (200  ,60  ,150  ,20);
		campo.add(btnconsultavoucher).setBounds                                    (130  ,90  ,150  ,20);
		
		cont.add(campo);
		btnconsultavoucher.addActionListener(buscar);
		txtFolio.addActionListener(buscar);
		txtFolio.addKeyListener(opBuscar);
	}
 	KeyListener opBuscar = new KeyListener() {
 		public void keyTyped(KeyEvent e) {}
 		public void keyReleased(KeyEvent e) {

 		    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
 		       KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "foco");
 		    
 		    getRootPane().getActionMap().put("foco", new AbstractAction(){
 			        public void actionPerformed(ActionEvent e)
 			              {
 			    		btnconsultavoucher.doClick();
 			               }
 		    });
 		}
 		public void keyPressed(KeyEvent e) {}
 	};
 	

	
	ActionListener buscar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(txtFolio.getText().equals("")){
				JOptionPane.showMessageDialog(null, "Ingrese El No. De Folio","Mensaje",JOptionPane.WARNING_MESSAGE);
				return;
			}else{
				String basedatos="2.200";
				String vista_previa_reporte="no";
				int vista_previa_de_ventana=0;
				String comando="";
				String reporte = "";
				try {	
					int transaccion =0;
					switch(cmbtransaccion.getSelectedIndex()){
					case 0: transaccion=20; break;
					case 1: transaccion=38; break;		
					};
					 reporte = "Obj_Reporte_ReImpresion_de_Voucher.jrxml";
					 comando = "exec sp_consulta_de_datos_voucher '"+txtFolio.getText().toUpperCase().trim()+"',"+transaccion+",'"+usuario.getNombre_completo()+"',"+usuario.getFolio() ;
					 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
				} catch (NumberFormatException e1) {
					e1.printStackTrace(); 
					JOptionPane.showMessageDialog(null, "Error en impresion voucher  en la funcion buscar  procedimiento almacenado sp_consulta_de_datos_voucher SQLException: \n"+comando+" "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);

				} 			
			}
		}
	};
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Reporte_De_Consulta_Y_Reimpresion_De_Vouchers().setVisible(true);
		}catch(Exception e){	}
	}
}