package Cat_Reportes;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Generacion_Reportes;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;

import com.toedter.calendar.JDateChooser;

@SuppressWarnings("serial")
public class Cat_Reporte_Para_Revision_De_Pedidos extends JFrame {
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JTextField txtPedido = new Componentes().text(new JTextField(), "Folio De Pedido", 15, "String");
	JDateChooser c_inicio = new JDateChooser();
	JDateChooser c_final = new JDateChooser();
	
	String[] establecimientosExterno = new Obj_Establecimiento().Combo_Establecimiento201();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbEstablecimientos = new JComboBox(establecimientosExterno);
	
	JCButton btngenerar_reporte = new JCButton("Generar Reporte En Pantalla","hoja-de-calculo-icono-8865-32.png","Azul");
	
	JLabel JLBlinicio			= new JLabel(new ImageIcon("Imagen/iniciar-icono-4628-16.png") );
	JLabel JLBfin				= new JLabel(new ImageIcon("Imagen/acabado-icono-7912-16.png") );
	JLabel JLBestablecimiento	= new JLabel(new ImageIcon("Imagen/folder-home-home-icone-5663-16.png") );
	JLabel JLBdepartamento		= new JLabel(new ImageIcon("Imagen/departamento-icono-5365-16.png") );
	
	public Cat_Reporte_Para_Revision_De_Pedidos(){
		this.setSize(430,250);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("Consulta De Pedidos");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/articulo-icono-9036-48.png"));
		this.panel.setBorder(BorderFactory.createTitledBorder("Revision De Pedidos"));
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Report.png"));

		int x=20, y=25, width=100,height=20;
		
		this.panel.add(new JLabel("F. Pedido:")).setBounds 	  (x     ,y      ,width  ,height    );
		this.panel.add(txtPedido).setBounds                   (x+=55 ,y      ,width ,height    );
		
		this.panel.add(new JLabel("Fecha Inicio:")).setBounds (x=20  ,y+=35  ,width  ,height    );
		this.panel.add(JLBlinicio).setBounds                  (x+=55 ,y      ,height ,height    );
		this.panel.add(c_inicio).setBounds                    (x+=20 ,y      ,width  ,height    );
		
		this.panel.add(new JLabel("Fecha Final:")).setBounds  (x+=130,y      ,width  ,height    );
		this.panel.add(JLBfin).setBounds                      (x+=55 ,y      ,height ,height    );
		this.panel.add(c_final).setBounds                     (x+=20 ,y      ,width  ,height    );
		
		x=20;width=380;
		this.panel.add(new JLabel("Surte:")).setBounds  	  (x     ,y+=35   ,80	    ,height    );
		this.panel.add(cmbEstablecimientos).setBounds         (x+40  ,y       ,width-60,height  );
		
		x=70;width=300;
		this.panel.add(btngenerar_reporte).setBounds          (x    ,y+=50   ,width   ,height*2 );

		this.cont.add(panel);
		
		c_inicio.setDate( cargar_fechas(7));
		c_final.setDate( cargar_fechas(0));

		btngenerar_reporte.addActionListener(opGenerar_reporte);
	}
	
	public Date cargar_fechas(Integer dias){
		Date date1 = null;
				  try {
					date1 = new SimpleDateFormat("dd/MM/yyyy").parse(new BuscarSQL().fecha(dias));
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		return date1;
	};
	
	public String validar_fechas(){
		String error = "";
		String fechainicioNull = c_inicio.getDate()+"";
		String fechafinalNull = c_final.getDate()+"";
	    if(fechainicioNull.equals("null"))error+= "Fecha  inicio\n";
		if(fechafinalNull.equals("null"))error+= "Fecha Final\n";
		return error;
	}
	
	ActionListener opGenerar_reporte = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			String basedatos="2.26";
			String vista_previa_reporte="no";
			int vista_previa_de_ventana=0;
			String comando= "";
			String reporte = "";
			
				if(validar_fechas().equals("")){
					String fecha_inicio = new SimpleDateFormat("dd/MM/yyyy").format(c_inicio.getDate())+" 00:00:00";
					  String fecha_final  = new SimpleDateFormat("dd/MM/yyyy").format(c_final.getDate())+"  23:59:00";
					  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm"); 
					  Date fecha1 = sdf.parse(fecha_inicio , new ParsePosition(0));
					  Date fecha2 = sdf.parse(fecha_final , new ParsePosition(0));
					  
					  if(fecha1.before(fecha2)){
							    comando="exec sp_reporte_gestion_de_pedido_limpio '"+txtPedido.getText().toString().trim()+"','"+fecha_inicio+"','"+fecha_final+"','"+(cmbEstablecimientos.getSelectedItem().toString().trim().equals("Selecciona Un Establecimiento")?"":cmbEstablecimientos.getSelectedItem().toString().trim())+"'";
								reporte = "Obj_Reporte_De_Gestion_De_Pedidos_Limpio.jrxml";
								new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
							   return;
					  }else{
				        JOptionPane.showMessageDialog(null,"El Rango de Fechas Esta Invertido","Aviso!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
					     return;
					  }
					
				}else{
				  JOptionPane.showMessageDialog(null, "Los Siguientes Campos Estan Vacios y Se Necesitan Para La Consulta:\n "+validar_fechas(),"Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
	               return;
				} 
		}
	};
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Reporte_Para_Revision_De_Pedidos().setVisible(true);
		}catch(Exception e){	}
	}

}
