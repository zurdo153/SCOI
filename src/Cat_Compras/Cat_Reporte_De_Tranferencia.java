package Cat_Compras;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

import Conexiones_SQL.Generacion_Reportes;
import Obj_Principal.Componentes;

@SuppressWarnings("serial")
public class Cat_Reporte_De_Tranferencia extends JDialog{

	JTextField txtFolioTransferencia = new Componentes().text(new JTextField(), "Folio De Transferencia", 15, "String");
	
	JButton btngenerar = new JButton("Generar");
	
	public Cat_Reporte_De_Tranferencia(){
		this.setModal(true);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Contacts-icon.png"));
		this.setTitle("Reporte de Transferencia");
		
		Container cont = getContentPane();
		JLayeredPane panel = new JLayeredPane();
		
		panel.setBorder(BorderFactory.createTitledBorder("Consulta De Transferencia Para Embarque"));
		
		panel.add(new JLabel("Folio De Transferencia:")).setBounds(10, 25, 220, 20);
		panel.add(txtFolioTransferencia).setBounds(10, 45, 220, 20);
		panel.add(btngenerar).setBounds(70, 70, 100, 20);
		
		cont.add(panel);
		
		btngenerar.addActionListener(opGenerar);
		txtFolioTransferencia.addActionListener(opGenerar);
		
		this.setSize(250, 135);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	ActionListener opGenerar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
				String basedatos="2.200";
				String vista_previa_reporte="no";
				int vista_previa_de_ventana=0;
				String comando="DECLARE @Folio char(10) "
								+ " SET @Folio='"+txtFolioTransferencia.getText().trim().toUpperCase()+"' "
								+ " SELECT entysal.folio, "
								+ "		productos.codigo_barras_pieza, "
								+ "		estab_origen.nombre as estab_origen, "
								+ "		estab_destino.nombre as estab_destino, "
								+ "		entysal.transaccion, "
								+ "		entysal.fecha, "
								+ "		entysal.cod_prod, "
								+ "		entysal.abreviatura_unidad AS unidad, "
								+ "		entysal.cantidad, "
								+ "		entysal.precio_lista, "
								+ "		entysal.importe, "
								+ "     entysal.iva, "
								+ "		entysal.ieps, "
								+ "		entysal.costo, "
								+ "		entysal.peso, "
								+ "		entysal.volumen, "
								+ "		entysal.status, "
								+ "		entysal.total AS Total, "
								+ "		productos.descripcion_completa, "
								+ "		entysal.id, "
								+ "        productos.cod_prod AS Expr1, "
								+ "		rtransf.nombre as razon, "
								+ "		mi.usuario as folio_usuario_capturo, "
								+ "		su.nombre as usuario_capturo, "
								+ "		ISNULL(t.nombre, '') AS nom_talla "
								+ " FROM entysal WITH (nolock) "
								+ " INNER JOIN Movimientos_Internos mi with (nolock) on mi.folio = entysal.folio "
								+ " INNER JOIN establecimientos estab_origen on estab_origen.cod_estab = mi.cod_estab "
								+ " INNER JOIN establecimientos estab_destino on estab_destino.cod_estab = mi.cod_estab_alterno "
								+ " INNER JOIN razones_transferencia rtransf on rtransf.razon_transferencia = mi.razon_aod_inventario "
								+ " INNER JOIN usuarios su on su.usuario = mi.usuario "
								+ " INNER JOIN productos WITH (nolock) ON entysal.cod_prod = productos.cod_prod "
								+ " LEFT OUTER JOIN tallas AS t WITH (nolock) ON productos.talla = t.talla "
								+ " WHERE (entysal.folio = @Folio) "
								+ " AND (entysal.transaccion = '35') "
								+ " ORDER BY productos.descripcion_completa";
				
				String reporte = "Obj_Consulta_De_Tranferencia_De_Pedidos.jrxml";
						 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
			}
	};
	
	public static void main(String [] arg){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Reporte_De_Tranferencia().setVisible(true);
		}catch(Exception e){
			System.err.println("Error :"+ e.getMessage());
		}
	}
}
