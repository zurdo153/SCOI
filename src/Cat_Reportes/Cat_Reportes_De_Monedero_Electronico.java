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
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;

import com.toedter.calendar.JDateChooser;

import Cat_Principal.Cat_Comandos;


@SuppressWarnings("serial")
public class Cat_Reportes_De_Monedero_Electronico extends JFrame {
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JDateChooser c_inicio = new JDateChooser();
	JDateChooser c_final = new JDateChooser();
	
	String operador[] = {"Selecciona Un Reporte","Reporte A Detalle Del Monedero Electronico","Reporte De Dinero Electronico Usado En Una Asignacion","Reporte De Dinero Electronico Acumulado De La Tarjeta","Reporte De Dinero Electronico Usado De La Tarjeta","Reporte De Dinero Electronico De Tarjetas Acumulado, Usado y Saldo "};
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbConcepto = new JComboBox(operador);
	
	String detalle[] = { "Colonia","Edad","Poblacion","Sexo","Tarjeta"};
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbDetalle = new JComboBox(detalle);
	
	JCButton btngenerar_reporte = new JCButton("Generar Reporte En Pantalla","hoja-de-calculo-icono-8865-32.png","Azul");
	JCButton btngenerar_excel   = new JCButton("Generar Reporte En XLS ","xls-icon-3376-32px.png","Verde");
	
	JLabel JLBlinicio			= new JLabel(new ImageIcon("Imagen/iniciar-icono-4628-16.png") );
	JLabel JLBfin				= new JLabel(new ImageIcon("Imagen/acabado-icono-7912-16.png") );
	JLabel JLBestablecimiento	= new JLabel(new ImageIcon("Imagen/folder-home-home-icone-5663-16.png") );
	JLabel JLBdepartamento		= new JLabel(new ImageIcon("Imagen/departamento-icono-5365-16.png") );
	JLabel JLtexto              = new JLabel("Teclee El Dato Solicitado");    
	
	String textotxt="";
	JTextField txtTexto= new Componentes().text(new JCTextField(), textotxt, 300, "String");
	
	public Cat_Reportes_De_Monedero_Electronico(){
		this.setSize(430,250);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("Reportes De Monedero Electronico");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/articulo-icono-9036-48.png"));
		this.panel.setBorder(BorderFactory.createTitledBorder("Seleccione  el Tipo de Reporte y de click a Generar el Reporte "));

		int x=20, y=25,width=380,height=20;
		this.panel.add(cmbConcepto).setBounds                 (x     ,y      ,width    ,height    );
		
		width=100;
		this.panel.add(new JLabel("Detalle:")).setBounds      (x     ,y+=35  ,width    ,height    );
		this.panel.add(cmbDetalle).setBounds                  (x+160,y      ,width+120,height    );
		this.panel.add(JLtexto).setBounds                     (x     ,y+=35  ,width+80 ,height    );
		this.panel.add(txtTexto).setBounds                    (x+160 ,y      ,width+120,height    );
		this.panel.add(new JLabel("Fecha Inicio:")).setBounds (x     ,y+=35  ,width    ,height    );
		this.panel.add(JLBlinicio).setBounds                  (x+=55 ,y      ,height   ,height    );
		this.panel.add(c_inicio).setBounds                    (x+=20 ,y      ,width    ,height    );
		this.panel.add(new JLabel("Fecha Final:")).setBounds  (x+=130,y      ,width    ,height    );
		this.panel.add(JLBfin).setBounds                      (x+=55 ,y      ,height   ,height    );
		this.panel.add(c_final).setBounds                     (x+=20 ,y      ,width    ,height    );
		
		x=70;width=300;
		this.panel.add(btngenerar_reporte).setBounds          (x    ,y+=45   ,width    ,height*2 );
//		this.panel.add(btngenerar_excel).setBounds            (x    ,y+=50   ,width    ,height*2 );

		this.cont.add(panel);
		
        cmbConcepto.addActionListener(opSeleccion_combo);
        cmbDetalle.addActionListener(opSeleccion_detalle_combo);
        btngenerar_reporte.addActionListener(opGenerar_reporte);
        btngenerar_reporte.setEnabled(false);
        cmbDetalle.setEnabled(false);
        txtTexto.setEditable(false);
		c_final.setEnabled(false);
		c_inicio.setEnabled(false);
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
	
	ActionListener opSeleccion_combo = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			 String concepto=cmbConcepto.getSelectedItem().toString().trim();
			  btngenerar_reporte.setEnabled(true);
			  
			 if(concepto.equals("Selecciona Un Reporte") ){
				  c_final.setEnabled(false);
				  c_inicio.setEnabled(false);
				  txtTexto.setEditable(false);
				  txtTexto.setText("Teclee El Dato Solicitado");
				  c_final.setDate(null);
				  c_inicio.setDate(null);
				  cmbConcepto.requestFocus();
				  cmbConcepto.showPopup();
				  btngenerar_reporte.setEnabled(false);
			  }
			 
			 if(concepto.equals("Reporte De Dinero Electronico De Tarjetas Acumulado, Usado y Saldo") ){
				  c_final.setEnabled(false);
				  c_inicio.setEnabled(false);
				  cmbDetalle.setEnabled(false);
				  txtTexto.setEditable(false);
				  txtTexto.setText("Teclee El Dato Solicitado");
				  c_final.setDate(null);
				  c_inicio.setDate(null);
			  }
			 
			  if(concepto.equals("Reporte De Dinero Electronico Usado De La Tarjeta")||concepto.equals("Reporte De Dinero Electronico Acumulado De La Tarjeta") ){
				  c_final.setEnabled(false);
				  c_inicio.setEnabled(false);
				  cmbDetalle.setEnabled(false);
				  JLtexto.setText("Teclee El Folio De La Tarjeta:");
				  txtTexto.setText("");
				  txtTexto.setEditable(true);
				  txtTexto.requestFocus();
			  }else{
				  if(concepto.equals("Reporte De Dinero Electronico Usado En Una Asignacion")){
					  c_final.setEnabled(false);
					  c_inicio.setEnabled(false);
					  cmbDetalle.setEnabled(false);
					  JLtexto.setText("Teclee El Folio De La Asignacion:");
					  txtTexto.setText("");
					  txtTexto.setEditable(true);
					  txtTexto.requestFocus();
				  }else{
					  JLtexto.setText("");	  
					  txtTexto.setText("");
					  txtTexto.setEditable(false);
				  }
			  }
			  
			  if(concepto.equals("Reporte A Detalle Del Monedero Electronico")){
				  txtTexto.setEditable(true);
				  cmbDetalle.setEnabled(true);
				  c_final.setEnabled(true);
				  c_inicio.setEnabled(true);
				  c_inicio.setDate( cargar_fechas(7));
				  c_final.setDate( cargar_fechas(0));
				  c_final.requestFocus();
				  JLtexto.setText("Teclee La (El) "+cmbDetalle.getSelectedItem().toString().trim()+":");
                  cmbDetalle.requestFocus(true);
                  cmbDetalle.showPopup();
			  }
			  
		}
	};
	
	ActionListener opSeleccion_detalle_combo = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		  if(cmbDetalle.getSelectedItem().toString().trim().equals("Sexo")){
			  JLtexto.setText("Teclee El "+cmbDetalle.getSelectedItem().toString().trim()+":");  
		  }else{
			  JLtexto.setText("Teclee La "+cmbDetalle.getSelectedItem().toString().trim()+":"); 
		  }
		  txtTexto.requestFocus();
		}
	};
	
	
	ActionListener opGenerar_reporte = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String basedatos="2.200";
			String vista_previa_reporte="no";
			int vista_previa_de_ventana=0;
			String comando= "";
			String reporte = "";
			
			String concepto=cmbConcepto.getSelectedItem().toString().trim();
			 
			 if(concepto.equals("Reporte De Dinero Electronico Usado De La Tarjeta")||concepto.equals("Reporte De Dinero Electronico Acumulado De La Tarjeta")){
				  if(!txtTexto.getText().toString().trim().equals("")){
				 	     comando = new Cat_Comandos().dinero_electronico(concepto,txtTexto.getText().toString().trim(),"","" );
					     reporte="Obj_Reporte_De_Dinero_Electronico_General_I.jrxml";
				  }else{
				        JOptionPane.showMessageDialog(null,"Es Necesario Que Teclee Un Folio de Tarjeta Para Que Genere El Reporte","Aviso!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				         txtTexto.requestFocus();
					    return;  
				  }
		     }
			 
			 
			 if(concepto.equals("Reporte De Dinero Electronico Usado En Una Asignacion")){
				  if(!txtTexto.getText().toString().trim().equals("")){
				 	     comando = new Cat_Comandos().dinero_electronico(concepto,txtTexto.getText().toString().trim(),"","" );
					     reporte="Obj_Reporte_De_Dinero_Electronico_Por_Asignacion.jrxml";
				  }else{
				        JOptionPane.showMessageDialog(null,"Es Necesario Que Teclee Un Folio de Asignacion Para Que Genere El Reporte","Aviso!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				         txtTexto.requestFocus();
					    return;  
				  }		    
			  }
			 
			 
			 if(concepto.equals("Reporte De Dinero Electronico De Tarjetas Acumulado, Usado y Saldo" )){
		 	     comando = new Cat_Comandos().dinero_electronico(concepto,txtTexto.getText().toString().trim(),"","" );
			     reporte="Obj_Reporte_De_Dinero_Electronico_Saldos_En_Tarjetas.jrxml"; 
	    	 }
			 
			 
			 if(concepto.equals("Reporte A Detalle Del Monedero Electronico")){
				  if(!txtTexto.getText().toString().trim().equals("")){
						  if(validar_fechas().equals("")){
									  String fecha_inicio = new SimpleDateFormat("dd/MM/yyyy").format(c_inicio.getDate())+" 00:00:00";
									  String fecha_final  = new SimpleDateFormat("dd/MM/yyyy").format(c_final.getDate())+"  23:59:00";
									  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm"); 
									  Date fecha1 = sdf.parse(fecha_inicio , new ParsePosition(0));
									  Date fecha2 = sdf.parse(fecha_final , new ParsePosition(0));
									  
									  if(fecha1.before(fecha2)){
										     comando = "exec sp_IZAGAR_reporte_de_monedero_electronico '"+txtTexto.getText().toString().trim()+"','"+cmbDetalle.getSelectedItem().toString().trim()+"','"+fecha_inicio+"','"+fecha_final+"'";
										     reporte="Obj_Reporte_De_Dinero_Electronico_A_Detalle.jrxml";
									  }else{
								        JOptionPane.showMessageDialog(null,"El Rango de Fechas Esta Invertido","Aviso!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
									     return;
									  }
						  }else{
						   JOptionPane.showMessageDialog(null, "Los Siguientes Campos Estan Vacios y Se Necesitan Para La Consulta:\n "+validar_fechas(),"Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				           return;
						  } 
					  
				 	    					     
				  }else{
				        JOptionPane.showMessageDialog(null,"Es Necesario Que Teclee El Dato >"+cmbDetalle.getSelectedItem().toString()+"< Solicitado Para Generar El Reporte","Aviso!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				         txtTexto.requestFocus();
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
			new Cat_Reportes_De_Monedero_Electronico().setVisible(true);
		}catch(Exception e){	}
	}

}
