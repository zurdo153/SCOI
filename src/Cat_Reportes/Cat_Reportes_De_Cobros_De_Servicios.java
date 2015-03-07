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

import com.toedter.calendar.JDateChooser;

import Conexiones_SQL.Generacion_Reportes;
import Obj_Principal.Componentes;

@SuppressWarnings("serial")
public class Cat_Reportes_De_Cobros_De_Servicios extends JFrame{
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JDateChooser fhInico = new JDateChooser();
	JDateChooser fhFin = new JDateChooser();
	JTextField txtFolio = new Componentes().text(new JTextField(), "Folio de la Asignacion", 500, "String");
	
	JButton btncortePorFecha = new JButton("",new ImageIcon("imagen/Calendar.png"));
	JButton btncortedelfolio = new JButton("",new ImageIcon("imagen/idea-de-bombilla-icono-3949-32.png"));
	JButton btngenerar = new JButton("Generar",new ImageIcon("imagen/buscar.png"));
	
	
	int tipo_Reporte = 0;
	
	public Cat_Reportes_De_Cobros_De_Servicios(){
		
		fhInico.setIcon(new ImageIcon("Iconos/calendar_icon&16.png"));
		fhFin.setIcon(new ImageIcon("Iconos/calendar_icon&16.png"));
		
		setSize(305,250);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Reportes De Cobros De Servicios");
		setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Service-icon.png"));
		panel.setBorder(BorderFactory.createTitledBorder("Seleciona El Tipo De Reporte"));
	
		btncortePorFecha.setText(	"<html> <FONT FACE="+"arial"+" SIZE=3 COLOR=BLACk>" +
				"		<CENTER><p>Reporte De Cobros De CFE Por Fecha</p></CENTER></FONT>" +
				"</html>");
		
		btncortedelfolio.setText(	"<html> <FONT FACE="+"arial"+" SIZE=3 COLOR=BLACk>" +
				"		<CENTER><p>Reporte De Cobros De CFE Por Asignacion</p></CENTER></FONT>" +
				"</html>");
		
		panel.add(btncortePorFecha).setBounds(20,25,260,40);
		panel.add(btncortedelfolio).setBounds(20,65,260,40);
		panel.add(new JLabel("Folio Asignacion:")).setBounds(20,80,200,20);		
		
		panel.add(new JLabel("De:")).setBounds(20,120,30,20);
		panel.add(fhInico).setBounds(50,120,90,20);
		panel.add(new JLabel("A:")).setBounds(160,120,30,20);
		panel.add(fhFin).setBounds(190,120,90,20);
		
		panel.add(txtFolio).setBounds(20,150,260,20);
		panel.add(btngenerar).setBounds(100,180,120,30);
	    txtFolio.setEditable(false);
	    btngenerar.setEnabled(false);
		cont.add(panel);
		btngenerar.addActionListener(opGenerar);
		btncortePorFecha.addActionListener(opReporte_Por_Folio);
		btncortedelfolio.addActionListener(opReporte_Por_Folio);
		
	}
	
	ActionListener opReporte_Por_Folio = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			
			
			txtFolio.setEditable(true);
			btngenerar.setEnabled(true);
			tipo_Reporte=1;
			txtFolio.requestFocus();
		}
	};
	
	ActionListener opGenerar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String reporte = "";
			String comando="";
			String basedatos="";
			String vista_previa_reporte="";
			int vista_previa_de_ventana=0;
			
			if(tipo_Reporte==1){
						if(!txtFolio.getText().equals("")){
							      
							   	   basedatos= "2.200";
							       // Con Vista Previa >>"si"<< , sin Vista Previa y permite selecionar impresora >>"no"<<, sin vista previa e imprime directo >>"id"<<
							       vista_previa_reporte="si";
							       //sera 1  para que tome encuenta la configuracion de la ventana y 0 para la opcion del usuario
							       vista_previa_de_ventana=0;
							       
							       reporte = "Obj_Reporte_De_Cobro_De_CFE_Por_Asignacion.jrxml";
							       
  								   comando ="declare @asignacion varchar(500),@SQLQRY nvarchar(max), @Fecha varchar(30)" +
  								   		" 	set @asignacion="+ txtFolio.getText().trim()+""+
  								   		
  								   		"			SET @SQLQRY='" +
  								   		"                                SELECT  facremtick.folio as ticket " +
  								   		"                                            ,entysal.total as ta " +
  								   		"                                       	   ,convert(varchar(20),facremtick.fecha,103)as fecha                          " +
  										"                                                  ,productos.descripcion as descripcion_producto                          " +
  										"                                               ,comentarios_ventas.comentario                                             " +
  										"                                               ,facremtick.folio_cajero as asignacion                                     " +
  										"                                        	FROM facremtick with (nolock)                                                   " +
  										"                                        		INNER JOIN entysal on entysal.folio=facremtick.folio                        " +
  										"                                            INNER JOIN productos on productos.cod_prod=entysal.cod_prod                    " +
  										"                                            LEFT OUTER JOIN comentarios_ventas on comentarios_ventas.folio=facremtick.folio" + 
  										"                                       WHERE (facremtick.folio_cajero in ('''+@asignacion+''') and entysal.cod_prod=''52384'')  " +
  										"                                  	UNION all                                                                                    " +
  										"                                   SELECT  facremtick.folio as ticket                                                           " +
  										"                                          ,entysal.total*-1 as ta                                                             " +
  										"                                   	      ,convert(varchar(20),facremtick.fecha,103)as fecha                                  " +
  										"                                          ,productos.descripcion as descripcion_producto                                         " +
  										"                                           ,comentarios_ventas.comentario                                                        " +
  										"                                                 ,facremtick.folio_cajero as asignacion                                          " +
  										"                                          	  FROM facremtick   with (nolock)                                                      " +
  										"                                         	       INNER JOIN entysal on entysal.folio=facremtick.folio                            " +
  										"                                                  INNER JOIN productos on productos.cod_prod=entysal.cod_prod                     " +
  										"                                                  LEFT OUTER JOIN comentarios_ventas on comentarios_ventas.folio=facremtick.folio " +
  										"                                              WHERE ( (facremtick.status = ''C'') AND                                             " +
                                        "                                                                                                                                  " +
  									    "                                                        (facremtick.folio_cajero in('''+@asignacion+'''))                         " +
  									    "                                                 and entysal.cod_prod=''52384'' )  ;'                                             " +
                                        "					exec sp_executesql @SQLQRY ";

  								           
								   new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
								   txtFolio.requestFocus();
						}else{
							JOptionPane.showMessageDialog(null,"Debe de Teclear un Folio: ","Aviso!", JOptionPane.WARNING_MESSAGE);
							return;		
					    }
			}
		}
	};
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Reportes_De_Cobros_De_Servicios().setVisible(true);
		}catch(Exception e){	}
	}
}
