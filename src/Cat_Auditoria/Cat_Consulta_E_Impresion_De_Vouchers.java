package Cat_Auditoria;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
import Obj_Principal.Componentes;

@SuppressWarnings("serial")
public class Cat_Consulta_E_Impresion_De_Vouchers extends JFrame{
	
	Container cont = getContentPane();
	JLayeredPane campo = new JLayeredPane();
	JTextField txtFolio = new Componentes().text(new JTextField(),"Teclea el Folio del Ticket o Remision", 50, "String");
 	JTextField txtNombre_Completo = new JTextField();
	JButton btnconsultavoucher = new JButton("Buscar Voucher",new ImageIcon("imagen/buscar.png"));
	
	String transaccion[] = {"Remision","Ticket"};
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbtransaccion = new JComboBox(transaccion);
	
	Border blackline, etched, raisedbevel, loweredbevel, empty;
	
 	public Cat_Consulta_E_Impresion_De_Vouchers() {
 		blackline = BorderFactory.createLineBorder(new java.awt.Color(100,100,100));
 		
 		this.campo.setBorder(BorderFactory.createTitledBorder(blackline, "Consulta e Impresion De Vouchers"));
 		
		setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/tarjeta-de-credito-visa-icono-8242-32.png"));
		setTitle("Consulta E Impresion de Vouchers");
		
		setSize(400,180);
		setResizable(false);
		setLocationRelativeTo(null);
		
		campo.add(new JLabel("Teclee el Folio Remision/Ticket:")).setBounds(45,30,160,20);
		campo.add(txtFolio).setBounds(200,30,120,20);
		
		campo.add(new JLabel("Selecciona Remision o Ticket:")).setBounds(45,60,150,20);
		campo.add(cmbtransaccion).setBounds(200,60,120,20);
		
		campo.add(btnconsultavoucher).setBounds(130,110,150,20);
		
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
					case 0: transaccion=37; break;
					case 1: transaccion=38; break;		
					};
					 reporte = "Obj_Reporte_IZAGAR_de_Impresion_de_Voucher.jrxml";
					 comando = "exec sp_consulta_de_datos_voucher '"+txtFolio.getText().toUpperCase().trim()+"',"+transaccion ;
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
			new Cat_Consulta_E_Impresion_De_Vouchers().setVisible(true);
		}catch(Exception e){	}
	}
}