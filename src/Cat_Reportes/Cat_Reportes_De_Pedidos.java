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
import javax.swing.UIManager;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Generacion_Reportes;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Contabilidad.Obj_Indicadores;
import Obj_Principal.JCButton;

import com.toedter.calendar.JDateChooser;

@SuppressWarnings("serial")
public class Cat_Reportes_De_Pedidos extends JFrame {
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JDateChooser c_inicio = new JDateChooser();
	JDateChooser c_final = new JDateChooser();
	
	String operador[] = {"Selecciona Un Reporte"
							,"Reporte Indicador De Nivel De Surtido"
							,"Reporte Indicador De Nivel De Servicio"
							,"Reporte De Productos Faltantes Por Establecimientos"
							,"Reporte De Productos Negados Por Establecimientos"
							,"Reporte De Total De Productos Negados Con Localizacion"
							,"Reporte De Productos Con Ajuste Por Establecimientos"
							,"Surtido De Pedido En Tiempo"
							,"Surtido De Pedido En Tiempo A Detalle"};
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbConcepto = new JComboBox(operador);
	
	JCButton btngenerar_reporte = new JCButton("Generar Reporte En Pantalla","hoja-de-calculo-icono-8865-32.png","Azul");
	JCButton btngenerar_excel   = new JCButton("Generar Reporte En XLS ","xls-icon-3376-32px.png","Verde");
	
	JLabel JLBlinicio			= new JLabel(new ImageIcon("Imagen/iniciar-icono-4628-16.png") );
	JLabel JLBfin				= new JLabel(new ImageIcon("Imagen/acabado-icono-7912-16.png") );
	JLabel JLBestablecimiento	= new JLabel(new ImageIcon("Imagen/folder-home-home-icone-5663-16.png") );
	JLabel JLBdepartamento		= new JLabel(new ImageIcon("Imagen/departamento-icono-5365-16.png") );
	
	public Cat_Reportes_De_Pedidos(){
		this.setSize(430,250);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("Reportes De Pedidos");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/articulo-icono-9036-48.png"));
		this.panel.setBorder(BorderFactory.createTitledBorder("Seleccione  El Rango De Fechas,El Tipo de Reporte y Genere El Reporte "));
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Report.png"));

		int x=20, y=25, width=100,height=20;
		this.panel.add(new JLabel("Fecha Inicio:")).setBounds (x     ,y      ,width  ,height    );
		this.panel.add(JLBlinicio).setBounds                  (x+=55 ,y      ,height ,height    );
		this.panel.add(c_inicio).setBounds                    (x+=20 ,y      ,width  ,height    );
		this.panel.add(new JLabel("Fecha Final:")).setBounds  (x+=130,y      ,width  ,height    );
		this.panel.add(JLBfin).setBounds                      (x+=55 ,y      ,height ,height    );
		this.panel.add(c_final).setBounds                     (x+=20 ,y      ,width  ,height    );
		
		x=20;width=380;
		this.panel.add(cmbConcepto).setBounds                 (x     ,y+=35  ,width   ,height   );
		
		x=70;width=300;
		this.panel.add(btngenerar_reporte).setBounds          (x    ,y+=50   ,width   ,height*2 );
//		this.panel.add(btngenerar_excel).setBounds            (x    ,y+=50   ,width   ,height*2 );

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
			
			 if(cmbConcepto.getSelectedIndex()==0){
			       JOptionPane.showMessageDialog(null,"Debe de Seleccionar Un Tipo De Reporte","Aviso!", JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
			        cmbConcepto.requestFocus();
			        cmbConcepto.showPopup();
				    return;		
			      }else{ 
					  
						if(validar_fechas().equals("")){
							String fecha_inicio = new SimpleDateFormat("dd/MM/yyyy").format(c_inicio.getDate())+" 00:00:00";
							  String fecha_final  = new SimpleDateFormat("dd/MM/yyyy").format(c_final.getDate())+"  23:59:00";
							  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm"); 
							  Date fecha1 = sdf.parse(fecha_inicio , new ParsePosition(0));
							  Date fecha2 = sdf.parse(fecha_final , new ParsePosition(0));
							  
							  if(fecha1.before(fecha2)){
								  String concepto=cmbConcepto.getSelectedItem().toString().trim();
								  
								if(concepto.equals("Surtido De Pedido En Tiempo") || concepto.equals("Surtido De Pedido En Tiempo A Detalle")){
									  Obj_Indicadores indicador = new Obj_Indicadores().buscar(concepto);
										comando=indicador.getProcedimiento_almacenado()+" '"+fecha_inicio.substring(0, 10)+"','"+fecha_final.substring(0, 10)+"'";
										reporte =indicador.getReporte();
								   }
								  
								  if(concepto.equals("Reporte Indicador De Nivel De Surtido")){
									  Obj_Indicadores indicador = new Obj_Indicadores().buscar("Nivel De Surtido");
										comando=indicador.getProcedimiento_almacenado()+" '"+fecha_inicio+"','"+fecha_final+"','"+new Obj_Usuario().getNombre_completo()+"',''";
										reporte =indicador.getReporte();
								   }
								  
								  if(concepto.equals("Reporte Indicador De Nivel De Servicio")){
									  Obj_Indicadores indicador = new Obj_Indicadores().buscar("Nivel De Servicio");
										comando=indicador.getProcedimiento_almacenado()+" '"+fecha_inicio+"','"+fecha_final+"'";
										reporte =indicador.getReporte();
								   }
								  
								  if(concepto.equals("Reporte De Productos Negados Por Establecimientos")){
									    comando="exec sp_select_productos_negados_por_establecimiento '"+fecha_inicio+"','"+fecha_final+"'";
										reporte = "Obj_Reporte_Productos_Negados_Por_Establecimiento.jrxml";
								   }
								  
								  if(concepto.equals("Reporte De Productos Faltantes Por Establecimientos")){
									    comando="exec sp_reporte_productos_faltantes_por_establecimiento_con_clasificadores '"+fecha_inicio+"','"+fecha_final+"'";
										reporte = "Obj_Reporte_De_Faltantes_De_Pedidos_Por_Establecimiento_En_Un_Periodo.jrxml";
								   }
									
								  if(concepto.equals("Reporte De Total De Productos Negados Con Localizacion")){
								        comando="exec sp_select_total_de_productos_negados '"+fecha_inicio+"','"+fecha_final+"'";
									    reporte = "Obj_Reporte_De_Total_De_Productos_Negados.jrxml";
								  }
								  
								  if(concepto.equals("Reporte De Productos Con Ajuste Por Establecimientos")){
								        comando="exec sp_select_productos_de_pedido_con_ajustes '"+fecha_inicio+"','"+fecha_final+"'";
									    reporte = "Obj_Reporte_De_Ajustes_De_Pedidos_A_Establecimientos.jrxml";
								  }
								  
							  }else{
						        JOptionPane.showMessageDialog(null,"El Rango de Fechas Esta Invertido","Aviso!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
							     return;
							  }
							
						}else{
						  JOptionPane.showMessageDialog(null, "Los Siguientes Campos Estan Vacios y Se Necesitan Para La Consulta:\n "+validar_fechas(),"Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
			               return;
						} 
		       }
			    new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
			   return;
		}
	};
	
//	ActionListener opGenerar_XLS = new ActionListener() {
//		public void actionPerformed(ActionEvent e) {
//			String basedatos="2.200";
//			String vista_previa_reporte="no";
//			int vista_previa_de_ventana=0;
//			String comando= "";
//			String reporte = "";
//			String fecha_guardado="";
//				try {fecha_guardado=new BuscarSQL().fecha_guardado(); } catch (SQLException e1) {e1.printStackTrace();}
//			
//			if(cmbEstablecimiento.getSelectedIndex()==0){
//			   JOptionPane.showMessageDialog(null,"Debe de Seleccionar Un Establecimiento: ","Aviso!", JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
//			      cmbEstablecimiento.requestFocus();
//				  cmbEstablecimiento.showPopup();
//			   	 return;		
//			}else{
//				  if(cmbConcepto.getSelectedIndex()==0){
//			    	 JOptionPane.showMessageDialog(null,"Debe de Seleccionar Un Tipo De Reporte","Aviso!", JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
//			    	    cmbConcepto.requestFocus();
//			    	    cmbConcepto.showPopup();
//				     return;		
//				  }else{ 
//				 	 comando = new Cat_Comandos().Comandos_Maximos_y_Minimos(cmbConcepto.getSelectedItem().toString().trim(), cmbEstablecimiento.getSelectedItem().toString().trim());
//   				     reporte="Obj_Reporte_De_Pedido_Sugerido_Maximos_y_Minimos.jrxml";
//		    }
//				  new Generacion_Reportes().Reporte_Guardado(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana,"xls",cmbConcepto.getSelectedItem().toString().trim()+" "+cmbEstablecimiento.getSelectedItem().toString().trim()+"_"+fecha_guardado);
//			   return;
//			}
//		}
//	};
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Reportes_De_Pedidos().setVisible(true);
		}catch(Exception e){	}
	}

}
