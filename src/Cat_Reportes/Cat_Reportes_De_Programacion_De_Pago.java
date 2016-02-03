package Cat_Reportes;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import Conexiones_SQL.Generacion_Reportes;
import Obj_Principal.Componentes;
import Obj_Principal.JCTextField;

@SuppressWarnings("serial")
public class Cat_Reportes_De_Programacion_De_Pago extends JFrame{
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	JTextField txtFolio = new Componentes().text(new JCTextField(), "Folio Programacion De Pago", 10, "Int");
	JButton btnReporte_actual = new JButton("",new ImageIcon("imagen/plan-icono-5073-16.png"));
	int tipo_Reporte = 0;
	
	public Cat_Reportes_De_Programacion_De_Pago(){
		setSize(425,220);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Reporte De Programacion De Pagos");
		setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/presentar-una-pluma-para-escribir-icono-9594-32.png"));
		panel.setBorder(BorderFactory.createTitledBorder("Teclea El Folio De La Programacion De Pago"));
		btnReporte_actual.setText(	"<html> <FONT FACE="+"arial"+" SIZE=3 COLOR=BLACk>" +
				"		<CENTER><p>Reporte De Programacion De Pagos</p></CENTER></FONT>" +
				"</html>");	
		
		panel.add(new JLabel("Folio Recepcion De Transferencia:")).setBounds(140,25,250,20);		
		panel.add(txtFolio).setBounds(120,50,200,20);
		panel.add(btnReporte_actual).setBounds(35,80,350,45);
		cont.add(panel);
		btnReporte_actual.addActionListener(opGenerar);
	}
	
	ActionListener opGenerar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String basedatos="2.200";
			String vista_previa_reporte="no";
			int vista_previa_de_ventana=0;
			String comando="SELECT mprogramaciones_pago.folio as folio_programacion_de_pago"
					+ "       ,recepcion_mercancia_proveedores.orden_compra"
					+ "  	  ,recepcion_mercancia_proveedores.Folio as folio_recepcion"
					+ " 	  ,mprogramaciones_pago.factura"
					+ " 	  ,recepcion_mercancia_proveedores.costo as costo_de_la_compra"
					+ "       ,rtrim(ltrim(mprogramaciones_pago.cod_prv)) as cod_prv"
					+ "  	  ,proveedores.razon_social as proveedor"
					+ " 	  ,recepcion_mercancia_proveedores.importe"
					+ " 	  ,recepcion_mercancia_proveedores.iva"
					+ " 	  ,recepcion_mercancia_proveedores.ieps"
					+ " 	  ,recepcion_mercancia_proveedores.importe_factura_proveedor"
					+ " 	  ,isnull((select sum(importe) from abonos_proveedores with(nolock) where  folio_aplicado=mprogramaciones_pago.factura and cod_prv=mprogramaciones_pago.cod_prv and transaccion='482'),0) as devoluciones"
					+ " 	  ,isnull((select sum(importe) from abonos_proveedores with(nolock) where  folio_aplicado=mprogramaciones_pago.factura and cod_prv=mprogramaciones_pago.cod_prv and transaccion='480'),0) as anticipos_proveedores"
					+ " 	  ,mprogramaciones_pago.importe as importe_programado"
					+ " 	  ,recepcion_mercancia_proveedores.importe_factura_proveedor-mprogramaciones_pago.importe  as diferiencia"
					+ " 	  ,mprogramaciones_pago.importe_descuento_financiero"
					+ " 	  ,mprogramaciones_pago.folio_pago"
					+ " FROM mprogramaciones_pago"
					+ "    LEFT OUTER JOIN proveedores ON mprogramaciones_pago.cod_prv = proveedores.cod_prv"
					+ "    LEFT OUTER JOIN transacciones ON mprogramaciones_pago.transaccion_factura = transacciones.transaccion"
					+ "    LEFT OUTER JOIN recepcion_mercancia_proveedores with(nolock) on recepcion_mercancia_proveedores.factura_proveedor=mprogramaciones_pago.factura and recepcion_mercancia_proveedores.cod_prv=mprogramaciones_pago.cod_prv"
					+ "  WHERE (mprogramaciones_pago.transaccion = '243') and mprogramaciones_pago.folio = '"+String.valueOf(txtFolio.getText().toUpperCase().trim())+"' order by proveedores.razon_social"  ;
			
			String reporte = "Obj_Reportes_De_Programacion_De_Pagos.jrxml";
			
	   		 if(!txtFolio.getText().equals("")){
							 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
		       }else{
		    	   JOptionPane.showMessageDialog(null,"Necesita Teclear Un Folio de Programacion De Pago","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
		       }
		}
	};
	
	public static void main(String args[]){
		try{			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Reportes_De_Programacion_De_Pago().setVisible(true);
		}catch(Exception e){	}	}
}
