package Cat_Auditoria;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;
import javax.swing.border.Border;

import Conexiones_SQL.BuscarSQL;

	@SuppressWarnings("serial")
	public class Cat_Generar_xml_pdf extends JDialog{
		
		Container contenedor = getContentPane();
		JLayeredPane panelxml = new JLayeredPane();
		
		JTextField txtFolioConsultar = new JTextField();
		JButton btnConsultar = new JButton("DESCARGAR",new ImageIcon("imagen/Aplicar.png"));
		
		Border blackline, etched, raisedbevel, loweredbevel, empty;
		
		public Cat_Generar_xml_pdf(){
			this.setModal(true);
			this.setTitle("Generar Facturas y XML De Proveedores");
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/control_facturas_y_xml.png"));
			blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
			panelxml.setBorder(BorderFactory.createTitledBorder(blackline,"Guardar Facturas y XML De Proveedores"));

			panelxml.add(txtFolioConsultar).setBounds(100,130,80,20);
			panelxml.add(btnConsultar).setBounds(200,130,80,20);
			
	        this.contenedor.add(panelxml);
	        
	        btnConsultar.addActionListener(opDescargarXMLpdf);
	        
			this.setSize(300,200);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		}
		
		ActionListener opDescargarXMLpdf = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				try {
					
					new BuscarSQL().buscar_xml_pdf(txtFolioConsultar.getText());
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		};
		
		public static void main (String [] arg){
			new Cat_Generar_xml_pdf().setVisible(true);
		}
}