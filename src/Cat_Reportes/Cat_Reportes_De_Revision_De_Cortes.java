package Cat_Reportes;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.UIManager;




import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Generacion_Reportes;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Principal.JCButton;

import com.toedter.calendar.JDateChooser;


@SuppressWarnings("serial")
public class Cat_Reportes_De_Revision_De_Cortes extends JFrame {
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JDateChooser c_inicio = new JDateChooser();
	JDateChooser c_final = new JDateChooser();
	
	
	JButton btn_todos 						= new JCButton  ("Todos"                    ,"Lista.png"                                          ,"Azul");
	JButton btn_revisados_por_auditoria 	= new JCButton  ("Revisados Por Auditoria"  ,"Text preview.png"                                   ,"Azul");
	JButton btn_sin_revisar_por_auditoria	= new JCButton  ("Sin Revisar Por Auditoria","orange-folder-saved-search-icone-8197-16.png"       ,"Azul");
	JButton btn_auditoria_paso_a_cobro 		= new JCButton  ("Auditoria  Paso  A  Cobro","diferiencia_de_sueldos_entre_listas_de_raya2_16.png","Azul");
	JButton btn_auditoria_paso_a_seguridad 	= new JCButton  ("Auditoria  A  Seguridad"  ,"vista-previa-del-ojo-icono-7248-16.png"             ,"Azul");
	JButton btn_cortesaceptado             	= new JCButton  ("Cortes Aceptados El Cobro","Aplicar.png"                                        ,"Azul");
	JButton btn_cortesnegados              	= new JCButton  ("Cortes Negados El Cobro"  ,"Delete.png"                                         ,"Azul");
	
	JLabel  JLBlinicio			= new JLabel(new ImageIcon("Imagen/iniciar-icono-4628-16.png") );
	JLabel  JLBfin				= new JLabel(new ImageIcon("Imagen/acabado-icono-7912-16.png") );
	JLabel  JLBestablecimiento	= new JLabel(new ImageIcon("Imagen/folder-home-home-icone-5663-16.png") );
	JLabel  JLBdepartamento		= new JLabel(new ImageIcon("Imagen/departamento-icono-5365-16.png") );
	
	public Cat_Reportes_De_Revision_De_Cortes(){
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/diferiencia_de_sueldos_entre_listas_de_raya2_64.png"));
		this.setTitle("Reportes De Revision De Cortes");
		
		this.panel.setBorder(BorderFactory.createTitledBorder("Reportes De Revision De Cortes"));
		
		int x=200,y=25,ancho=100;
		
		this.panel.add(new JLabel("Fecha Inicio:")).setBounds(15,y,ancho,20);
		this.panel.add(JLBlinicio).setBounds(75,y,20,20);
		this.panel.add(c_inicio).setBounds(95,y,100,20);
		
		this.panel.add(new JLabel("Fecha Final:")).setBounds(x+15,y,100,20);
		this.panel.add(JLBfin).setBounds(x+75,y,20,20);
		this.panel.add(c_final).setBounds(x+95,y,100,20);
		
		x=80;
		y=60;
		ancho=250;
	
		this.panel.add(btn_todos 				       ).setBounds(x,y,ancho,30);
		this.panel.add(btn_revisados_por_auditoria 	   ).setBounds(x,y+=33,ancho,30);
		this.panel.add(btn_sin_revisar_por_auditoria   ).setBounds(x,y+=33,ancho,30);
		this.panel.add(btn_auditoria_paso_a_cobro 	   ).setBounds(x,y+=33,ancho,30);
		this.panel.add(btn_auditoria_paso_a_seguridad  ).setBounds(x,y+=33,ancho,30);
		this.panel.add(btn_cortesaceptado              ).setBounds(x,y+=33,ancho,30);
		this.panel.add(btn_cortesnegados	           ).setBounds(x,y+=33,ancho,30);
		
		
		this.cont.add(panel);
		this.setSize(420,350);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		 cargar_fechas();
		

		 btn_todos.addActionListener(op_generar);
		 btn_revisados_por_auditoria.addActionListener(op_generar);
		 btn_sin_revisar_por_auditoria.addActionListener(op_generar);
		 btn_auditoria_paso_a_cobro.addActionListener(op_generar);
		 btn_auditoria_paso_a_seguridad.addActionListener(op_generar);
		 btn_cortesaceptado.addActionListener(op_generar); 
		 btn_cortesnegados.addActionListener(op_generar); 
	}
	
	public void cargar_fechas(){
		Date date1 = null;
				  try {
					date1 = new SimpleDateFormat("dd/MM/yyyy").parse(new BuscarSQL().fecha(7));
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		c_inicio.setDate(date1);
					
	    Date date2 = null;
					  try {
						date2 = new SimpleDateFormat("dd/MM/yyyy").parse(new BuscarSQL().fecha(0));
					} catch (ParseException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					}
		c_final.setDate(date2);
	};
	String basedatos="2.26";
	String vista_previa_reporte="no";
	int vista_previa_de_ventana=0;
	String comando="";
	String reporte = "";
	
	
	ActionListener op_generar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String fecha_inicio = new SimpleDateFormat("dd/MM/yyyy").format(c_inicio.getDate());
			String fecha_final = new SimpleDateFormat("dd/MM/yyyy").format(c_final.getDate());
			
			if(e.getActionCommand().toString().trim().equals("Auditoria  A  Seguridad")){
				cargar_fechas();
				String fecha_final1 = new SimpleDateFormat("dd/MM/yyyy").format(c_final.getDate());
				Reporte_de_Revision_de_Cortes("01/01/1900",fecha_final1,e.getActionCommand().toString().trim().toUpperCase());
  			  }
			
			if(e.getActionCommand().toString().trim().equals("Cortes Aceptados El Cobro")||e.getActionCommand().toString().trim().equals("Cortes Negados El Cobro")){
				String aceptado_negado=e.getActionCommand().equals("Cortes Negados El Cobro")?"2":"1";
				  Reporte_de_Aceptados_Negados(fecha_inicio, fecha_final, aceptado_negado);
			  }else{
					if(validar_fechas().equals("")){

 							Reporte_de_Revision_de_Cortes(fecha_inicio,fecha_final,e.getActionCommand().toString().trim().toUpperCase());
					}else{
						  JOptionPane.showMessageDialog(null, "Los Siguientes Campos Estan Vacios y Se Necesitan Para La Consulta:\n "+validar_fechas(),"Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
		                  return;
					}
			}
			
		}
	};
	
	
	public void Reporte_de_Revision_de_Cortes(String fecha_inicio, String fecha_final, String tipo_de_reporte){
		int folio_usuario = new Obj_Usuario().LeerSession().getFolio();
		 reporte = "Obj_Reporte_De_Revision_De_Cortes.jrxml";
		 comando = "exec sp_Reporte_De_Revision_De_Cortes '"+fecha_inicio+"','"+fecha_final+"','"+tipo_de_reporte+"','"+folio_usuario+"'";
		 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
	}
	
	
	public void Reporte_de_Aceptados_Negados(String fecha_inicio, String fecha_final, String aceptado_negado){
		 reporte = "Obj_Reporte_De_Cortes_Autorizados_o_Negados_El_Cobro.jrxml";
		 comando = "Sp_Reporte_De_Cortes_Autorizados_Negados_Cobro_En_Un_Rango_De_Fechas '"+fecha_inicio+"','"+fecha_final+"',"+aceptado_negado;
		 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
	}
	
	public String validar_fechas(){
		String error = "";
		String fechainicioNull = c_inicio.getDate()+"";
		String fechafinalNull = c_final.getDate()+"";
	    if(fechainicioNull.equals("null"))error+= "Fecha  inicio\n";
		if(fechafinalNull.equals("null"))error+= "Fecha Final\n";
		return error;
	}
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Reportes_De_Revision_De_Cortes().setVisible(true);
		}catch(Exception e){	}
	}

}
