package Cat_Reportes;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
import javax.swing.border.Border;

import Cat_Principal.Cat_Comandos;
import Conexiones_SQL.Connexion;
import Conexiones_SQL.Generacion_Reportes;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;


@SuppressWarnings("serial")
public class Cat_Reportes_De_Ubicaciones_De_Productos extends JFrame {
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	Connexion con = new Connexion();
	
	String operador[] = {"Selecciona Un Reporte","Consulta De Recepcion De Mercancia","Consulta De Entrada De Mercancia","Reporte De Productos Clasificados Del Establecimiento","Reporte De Productos Faltantes De Clasificar Del Establecimiento","Reporte De Productos Faltantes De Clasificar Con Existencia Del Establecimiento"};
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbConcepto = new JComboBox(operador);
	
	String establecimiento[] = new Obj_Establecimiento().Combo_Establecimientos("R");
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbEstablecimiento = new JComboBox(establecimiento);	
	
	JCButton btn_Origen = new JCButton  ("","list-icon-1440-32px.png","Azul");
	JCButton btn_generarpdf = new JCButton  ("","pdf-icon-png-2081-32px.png","Azul");
	JCButton btn_generarxls = new JCButton  ("","xls-icon-3376-32px.png","Azul");
	
	JLabel JLBlinicio= new JLabel(new ImageIcon("Imagen/iniciar-icono-4628-16.png") );
	JLabel JLBfin= new JLabel(new ImageIcon("Imagen/acabado-icono-7912-16.png") );
	JLabel JLBFactor= new JLabel();
    
	JTextField txtFolio = new Componentes().text(new JCTextField(), "Folio", 12, "String");
	
	Border blackline, etched, raisedbevel, loweredbevel, empty;	
	String parametroGeneral = "";
	String Lista="";
	String factor="";
	Obj_Usuario usuario = new Obj_Usuario().LeerSession();
	
	public Cat_Reportes_De_Ubicaciones_De_Productos(){
		this.setSize(400,290);
		cont.add(panel);
		setResizable(false);
		setLocationRelativeTo(null);
		setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/la-localizacion-gps-de-la-aguja-icono-9649-64.png"));
		setTitle("Reportes De Ubicaciones");
		panel.setBorder(BorderFactory.createTitledBorder("Teclee El Folio, Seleccione El Reporte Y De Click al Boton Deseado"));
		
		btn_Origen.setText(	"<html> <FONT FACE="+"arial"+" SIZE=3 COLOR=WHITE" +
				"		<CENTER><p>Ver Reporte Del Concepto</p></CENTER></FONT>" +
				"</html>");
		
		btn_generarpdf.setText(	"<html> <FONT FACE="+"arial"+" SIZE=3 COLOR=WHITE" +
				"		<CENTER><p>Generar Reportes En PDF</p></CENTER></FONT>" +
				"</html>");
		
		btn_generarxls.setText(	"<html> <FONT FACE="+"arial"+" SIZE=3 COLOR=WHITE" +
				"		<CENTER><p>Generar Reportes En XLS</p></CENTER></FONT>" +
				"</html>");
		
		int x=20,y=25,l=355,a=20;
		
		this.panel.add(cmbConcepto).setBounds                 (x    ,y    ,l    ,a);
		this.panel.add(cmbEstablecimiento).setBounds          (x    ,y+=30,l    ,a);
		 
		this.panel.add(new JLabel("Folio:")).setBounds        (x+10 ,y+=30,l-235,a);	
		this.panel.add(txtFolio).setBounds                    (x+40 ,y    ,l-100,a);
		
		a=35;
		this.panel.add(btn_Origen).setBounds                  (x    ,y+=40,l    ,a);
		this.panel.add(btn_generarpdf).setBounds              (x    ,y+=40,l    ,a);
		this.panel.add(btn_generarxls).setBounds              (x    ,y+=40,l    ,a);
		
		btn_Origen.addActionListener(opGenerarReporte_de_concepto);
		btn_generarpdf.addActionListener(opGenerarEnPDF);
		btn_generarxls.addActionListener(opGenerarEnXLS);
		
        this.addWindowListener(new WindowAdapter() { public void windowOpened( WindowEvent e ){
        	  cmbConcepto.requestFocus();
			  cmbConcepto.showPopup();
                      } });
        
	}
	
	ActionListener opGenerarReporte_de_concepto = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
	
	 		int testigo=0;
			String basedatos="2.200";
			String vista_previa_reporte="no";
			int vista_previa_de_ventana=0;
			String comando="";
			String reporte ="";
			String concepto=cmbConcepto.getSelectedItem().toString().trim();
			
			if(concepto.equals("Selecciona Un Reporte")){
			  JOptionPane.showMessageDialog(null,"Es Necesario Seleccionar Un Concepto Para Poder Generar El Reporte Del Concepto", "Aviso!",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
			  cmbConcepto.requestFocus();
			  cmbConcepto.showPopup();
               return;
			}else{
			    if(concepto.equals("Consulta De Recepcion De Mercancia" )||concepto.equals("Consulta De Entrada De Mercancia") ){
			    	 if(txtFolio.getText().equals("")){
						  JOptionPane.showMessageDialog(null,"El Campo Folio Esta Vacio Es Requerido Un Folio Para Este Reporte", "Aviso!",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
							txtFolio.requestFocus();
							return;
			    	 }else{
			    		 
			    		 if(concepto.equals("Consulta De Recepcion De Mercancia" )){			    			 
			    		 comando = new Cat_Comandos().Ubicaciones(concepto, txtFolio.getText().toString().trim());
 						 reporte = "Obj_Reporte_De_Recepcion_De_Mercacia_Con_Localizacion_Para_Su_Acomodo.jrxml";
						 testigo=1;
			    		 }else{
				    		 comando = new Cat_Comandos().Ubicaciones(concepto, txtFolio.getText().toString().trim());
	 						 reporte = "Obj_Reporte_De_Entrada_De_Mercacia_Con_Localizacion_Para_Su_Acomodo.jrxml";
							 testigo=1;
			    		 }
			    		 
			    	 }	
				}
			    
			    if(concepto.equals( "Reporte De Productos Clasificados Del Establecimiento") ){
			    	 if(cmbEstablecimiento.getSelectedItem().toString().trim().equals( "Selecciona un Establecimiento")){
						  JOptionPane.showMessageDialog(null,"Es Necesario Seleccionar Un Establecimiento Para Este Reporte", "Aviso!",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
						   cmbEstablecimiento.requestFocus();
						   cmbEstablecimiento.showPopup();
						return;
			    	 }else{
							comando="exec sp_reporte_de_productos_clasificados '"+cmbEstablecimiento.getSelectedItem().toString().trim()+"','"+concepto+"'";
							reporte = "Obj_Reporte_De_Productos_Localizaciones.jrxml";
							testigo=1;
			    	 }	
				}
			    
			    if(concepto.equals( "Reporte De Productos Faltantes De Clasificar Del Establecimiento") ){
			    	 if(cmbEstablecimiento.getSelectedItem().toString().trim().equals( "Selecciona un Establecimiento")){
						  JOptionPane.showMessageDialog(null,"Es Necesario Seleccionar Un Establecimiento Para Este Reporte", "Aviso!",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
						   cmbEstablecimiento.requestFocus();
						   cmbEstablecimiento.showPopup();
						return;
			    	 }else{
							comando="exec sp_reporte_de_productos_clasificados '"+cmbEstablecimiento.getSelectedItem().toString().trim()+"','"+concepto+"'";
							reporte = "Obj_Reporte_De_Productos_Localizaciones.jrxml";
							testigo=1;
			    	 }	
				}
			    
			    if(concepto.equals( "Reporte De Productos Faltantes De Clasificar Con Existencia Del Establecimiento") ){
			    	 if(cmbEstablecimiento.getSelectedItem().toString().trim().equals( "Selecciona un Establecimiento")){
						  JOptionPane.showMessageDialog(null,"Es Necesario Seleccionar Un Establecimiento Para Este Reporte", "Aviso!",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
						   cmbEstablecimiento.requestFocus();
						   cmbEstablecimiento.showPopup();
						return;
			    	 }else{
							comando="exec sp_reporte_de_productos_clasificados '"+cmbEstablecimiento.getSelectedItem().toString().trim()+"','"+concepto+"'";
							reporte = "Obj_Reporte_De_Productos_Localizaciones.jrxml";
							testigo=1;
			    	 }	
				}
			    
			if(testigo==1){
				  new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
			}else{
			  	  JOptionPane.showMessageDialog(null,"Error Reoporte No Identificado","Avisa Al Adimistrador Del Sistema!", JOptionPane.ERROR_MESSAGE);
				  return;
				 }
			}
		}
	};
	
	
	ActionListener opGenerarEnPDF = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			int testigo=0;
			String basedatos="2.200";
			String vista_previa_reporte="no";
			int vista_previa_de_ventana=0;
			String comando="";
			String reporte ="";
			
			java.util.Date fecha = new Date();
			String fecha_guardado=new SimpleDateFormat("dd-MM-yyyy").format(fecha);
			
			if(cmbConcepto.getSelectedItem().toString().trim().equals("Selecciona Un Reporte")){
			  JOptionPane.showMessageDialog(null,"Es Necesario Seleccionar Un Concepto Para Poder Generar El Reporte Del Concepto", "Aviso!",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
			  cmbConcepto.requestFocus();
			  cmbConcepto.showPopup();
               return;
			}else{
			    if(cmbConcepto.getSelectedItem().toString().trim().equals("Consulta De Recepcion De Mercancia") ){
			    	 if(txtFolio.getText().equals("")){
						  JOptionPane.showMessageDialog(null,"El Campo Folio Esta Vacio Es Requerido Un Folio Para Este Reporte", "Aviso!",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
							txtFolio.requestFocus();
							return;
			    	 }else{
							comando="    SELECT     RTRIM(LTRIM(E.cod_prod))  AS cod_prod"
									+ "	          ,P.codigo_barras_pieza"
									+ "	          ,P.descripcion_completa"
									+ "			  ,isnull((select top 1 upper(localizacion)+'      ' from localizaciones_surtido_productos with(nolock) where cod_prod = E.cod_prod and cod_estab = E.cod_estab),'SIN LOCALIZACION') as localicacion"
									+ "			  ,isnull((select top 1 SUBSTRING(upper(localizacion),0,3) from localizaciones_surtido_productos with(nolock) where cod_prod = E.cod_prod and cod_estab = E.cod_estab),0) as zona"
									+ "			  ,isnull((select top 1 SUBSTRING(upper(localizacion),3,3) from localizaciones_surtido_productos with(nolock) where cod_prod = E.cod_prod and cod_estab = E.cod_estab),0) as pasillo"
									+ "			  ,isnull((select top 1 SUBSTRING(upper(localizacion),6,3) from localizaciones_surtido_productos with(nolock) where cod_prod = E.cod_prod and cod_estab = E.cod_estab),0) as rack"
									+ "			  ,isnull((select top 1 SUBSTRING(upper(localizacion),9,1) from localizaciones_surtido_productos with(nolock) where cod_prod = E.cod_prod and cod_estab = E.cod_estab),0) as nivel"
									+ "			  ,E.cantidad"
									+ "           ,E.abreviatura_unidad"
									+ "			  ,(select rtrim(ltrim(nombre)) from establecimientos Est where Est.cod_estab=E.cod_estab) as establecimiento"
									+ "			  ,E.folio as recepcion"
									+ "   		  ,E.status"
									+ "			  ,E.fecha"
									+ "			  ,E.precio_lista"
									+ "			  ,E.descuento_porcentual"
									+ "			  ,E.importe_descuento"
									+ "			  ,E.importe"
									+ "			  ,E.iva"
									+ "			  ,E.ieps"
									+ "			  ,E.costo"
									+ "			  ,E.total"
									+ "			  ,PRV.razon_social"
									+ "			  ,'"+usuario.getNombre_completo()+"' as usuario"
									+ "		 	  ,'' as ubicador"
									+ "     FROM  entysal E"
									+ "	  LEFT OUTER JOIN  productos P WITH (nolock) ON E.cod_prod = P.cod_prod"
									+ "	  LEFT OUTER JOIN  proveedores PRV WITH (nolock) ON E.cod_prv=PRV.cod_prv "
									+ " WHERE   E.folio = '"+txtFolio.getText().toString().trim()+"' AND (E.transaccion ='44') "
									+ " ORDER BY ZONA,PASILLO,RACK,NIVEL,descripcion_completa";
							
							reporte = "Obj_Reporte_De_Recepcion_De_Mercacia_Con_Localizacion_Para_Su_Acomodo.jrxml";
							testigo=1;
			    	 }	
				}
			    
			    if(cmbConcepto.getSelectedItem().toString().trim().equals( "Reporte De Productos Clasificados Del Establecimiento") ){
			    	 if(cmbEstablecimiento.getSelectedItem().toString().trim().equals( "Selecciona un Establecimiento")){
						  JOptionPane.showMessageDialog(null,"Es Necesario Seleccionar Un Establecimiento Para Este Reporte", "Aviso!",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
						   cmbEstablecimiento.requestFocus();
						   cmbEstablecimiento.showPopup();
						return;
			    	 }else{
							comando="exec sp_reporte_de_productos_clasificados '"+cmbEstablecimiento.getSelectedItem().toString().trim()+"','"+cmbConcepto.getSelectedItem().toString().trim()+"'";
							reporte = "Obj_Reporte_De_Productos_Localizaciones.jrxml";
							testigo=1;
			    	 }	
				}
			    
			    if(cmbConcepto.getSelectedItem().toString().trim().equals( "Reporte De Productos Faltantes De Clasificar Del Establecimiento") ){
			    	 if(cmbEstablecimiento.getSelectedItem().toString().trim().equals( "Selecciona un Establecimiento")){
						  JOptionPane.showMessageDialog(null,"Es Necesario Seleccionar Un Establecimiento Para Este Reporte", "Aviso!",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
						   cmbEstablecimiento.requestFocus();
						   cmbEstablecimiento.showPopup();
						return;
			    	 }else{
							comando="exec sp_reporte_de_productos_clasificados '"+cmbEstablecimiento.getSelectedItem().toString().trim()+"','"+cmbConcepto.getSelectedItem().toString().trim()+"'";
							reporte = "Obj_Reporte_De_Productos_Localizaciones.jrxml";
							testigo=1;
			    	 }	
				}
			    
			    if(cmbConcepto.getSelectedItem().toString().trim().equals( "Reporte De Productos Faltantes De Clasificar Con Existencia Del Establecimiento") ){
			    	 if(cmbEstablecimiento.getSelectedItem().toString().trim().equals( "Selecciona un Establecimiento")){
						  JOptionPane.showMessageDialog(null,"Es Necesario Seleccionar Un Establecimiento Para Este Reporte", "Aviso!",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
						   cmbEstablecimiento.requestFocus();
						   cmbEstablecimiento.showPopup();
						return;
			    	 }else{
							comando="exec sp_reporte_de_productos_clasificados '"+cmbEstablecimiento.getSelectedItem().toString().trim()+"','"+cmbConcepto.getSelectedItem().toString().trim()+"'";
							reporte = "Obj_Reporte_De_Productos_Localizaciones.jrxml";
							testigo=1;
			    	 }	
				}

					if(testigo==1){
					  new Generacion_Reportes().Reporte_Guardado(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana,"pdf",cmbConcepto.getSelectedItem().toString().trim()+cmbEstablecimiento.getSelectedItem().toString().trim()+fecha_guardado);
					}else{
					  	  JOptionPane.showMessageDialog(null,"Error Reoporte No Identificado","Avisa Al Adimistrador Del Sistema!", JOptionPane.ERROR_MESSAGE);
						  return;
						 }
					}
				}
			};
			
	
	
	ActionListener opGenerarEnXLS = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			int testigo=0;
			String basedatos="2.200";
			String vista_previa_reporte="no";
			int vista_previa_de_ventana=0;
			String comando="";
			String reporte ="";
			
			java.util.Date fecha = new Date();
			String fecha_guardado=new SimpleDateFormat("dd-MM-yyyy").format(fecha);
			
			if(cmbConcepto.getSelectedItem().toString().trim().equals("Selecciona Un Reporte")){
			  JOptionPane.showMessageDialog(null,"Es Necesario Seleccionar Un Concepto Para Poder Generar El Reporte Del Concepto", "Aviso!",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
			  cmbConcepto.requestFocus();
			  cmbConcepto.showPopup();
               return;
			}else{
			    if(cmbConcepto.getSelectedItem().toString().trim().equals("Consulta De Recepcion De Mercancia") ){
			    	 if(txtFolio.getText().equals("")){
						  JOptionPane.showMessageDialog(null,"El Campo Folio Esta Vacio Es Requerido Un Folio Para Este Reporte", "Aviso!",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
							txtFolio.requestFocus();
							return;
			    	 }else{
							comando="    SELECT     RTRIM(LTRIM(E.cod_prod))  AS cod_prod"
									+ "	          ,P.codigo_barras_pieza"
									+ "	          ,P.descripcion_completa"
									+ "			  ,isnull((select top 1 upper(localizacion)+'      ' from localizaciones_surtido_productos with(nolock) where cod_prod = E.cod_prod and cod_estab = E.cod_estab),'SIN LOCALIZACION') as localicacion"
									+ "			  ,isnull((select top 1 SUBSTRING(upper(localizacion),0,3) from localizaciones_surtido_productos with(nolock) where cod_prod = E.cod_prod and cod_estab = E.cod_estab),0) as zona"
									+ "			  ,isnull((select top 1 SUBSTRING(upper(localizacion),3,3) from localizaciones_surtido_productos with(nolock) where cod_prod = E.cod_prod and cod_estab = E.cod_estab),0) as pasillo"
									+ "			  ,isnull((select top 1 SUBSTRING(upper(localizacion),6,3) from localizaciones_surtido_productos with(nolock) where cod_prod = E.cod_prod and cod_estab = E.cod_estab),0) as rack"
									+ "			  ,isnull((select top 1 SUBSTRING(upper(localizacion),9,1) from localizaciones_surtido_productos with(nolock) where cod_prod = E.cod_prod and cod_estab = E.cod_estab),0) as nivel"
									+ "			  ,E.cantidad"
									+ "           ,E.abreviatura_unidad"
									+ "			  ,(select rtrim(ltrim(nombre)) from establecimientos Est where Est.cod_estab=E.cod_estab) as establecimiento"
									+ "			  ,E.folio as recepcion"
									+ "   		  ,E.status"
									+ "			  ,E.fecha"
									+ "			  ,E.precio_lista"
									+ "			  ,E.descuento_porcentual"
									+ "			  ,E.importe_descuento"
									+ "			  ,E.importe"
									+ "			  ,E.iva"
									+ "			  ,E.ieps"
									+ "			  ,E.costo"
									+ "			  ,E.total"
									+ "			  ,PRV.razon_social"
									+ "			  ,'"+usuario.getNombre_completo()+"' as usuario"
									+ "		 	  ,'' as ubicador"
									+ "     FROM  entysal E"
									+ "	  LEFT OUTER JOIN  productos P WITH (nolock) ON E.cod_prod = P.cod_prod"
									+ "	  LEFT OUTER JOIN  proveedores PRV WITH (nolock) ON E.cod_prv=PRV.cod_prv "
									+ " WHERE   E.folio = '"+txtFolio.getText().toString().trim()+"' AND (E.transaccion ='44') "
									+ " ORDER BY ZONA,PASILLO,RACK,NIVEL,descripcion_completa";
							
							reporte = "Obj_Reporte_De_Recepcion_De_Mercacia_Con_Localizacion_Para_Su_Acomodo.jrxml";
							testigo=1;
			    	 }	
				}
			    
			    if(cmbConcepto.getSelectedItem().toString().trim().equals( "Reporte De Productos Clasificados Del Establecimiento") ){
			    	 if(cmbEstablecimiento.getSelectedItem().toString().trim().equals( "Selecciona un Establecimiento")){
						  JOptionPane.showMessageDialog(null,"Es Necesario Seleccionar Un Establecimiento Para Este Reporte", "Aviso!",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
						   cmbEstablecimiento.requestFocus();
						   cmbEstablecimiento.showPopup();
						return;
			    	 }else{
							comando="exec sp_reporte_de_productos_clasificados '"+cmbEstablecimiento.getSelectedItem().toString().trim()+"','"+cmbConcepto.getSelectedItem().toString().trim()+"'";
							reporte = "Obj_Reporte_De_Productos_Localizaciones.jrxml";
							testigo=1;
			    	 }	
				}
			    
			    if(cmbConcepto.getSelectedItem().toString().trim().equals( "Reporte De Productos Faltantes De Clasificar Del Establecimiento") ){
			    	 if(cmbEstablecimiento.getSelectedItem().toString().trim().equals( "Selecciona un Establecimiento")){
						  JOptionPane.showMessageDialog(null,"Es Necesario Seleccionar Un Establecimiento Para Este Reporte", "Aviso!",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
						   cmbEstablecimiento.requestFocus();
						   cmbEstablecimiento.showPopup();
						return;
			    	 }else{
							comando="exec sp_reporte_de_productos_clasificados '"+cmbEstablecimiento.getSelectedItem().toString().trim()+"','"+cmbConcepto.getSelectedItem().toString().trim()+"'";
							reporte = "Obj_Reporte_De_Productos_Localizaciones.jrxml";
							testigo=1;
			    	 }	
				}
			    
			    if(cmbConcepto.getSelectedItem().toString().trim().equals( "Reporte De Productos Faltantes De Clasificar Con Existencia Del Establecimiento") ){
			    	 if(cmbEstablecimiento.getSelectedItem().toString().trim().equals( "Selecciona un Establecimiento")){
						  JOptionPane.showMessageDialog(null,"Es Necesario Seleccionar Un Establecimiento Para Este Reporte", "Aviso!",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
						   cmbEstablecimiento.requestFocus();
						   cmbEstablecimiento.showPopup();
						return;
			    	 }else{
							comando="exec sp_reporte_de_productos_clasificados '"+cmbEstablecimiento.getSelectedItem().toString().trim()+"','"+cmbConcepto.getSelectedItem().toString().trim()+"'";
							reporte = "Obj_Reporte_De_Productos_Localizaciones.jrxml";
							testigo=1;
			    	 }	
				}

					if(testigo==1){
					  new Generacion_Reportes().Reporte_Guardado(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana,"xls",cmbConcepto.getSelectedItem().toString().trim()+cmbEstablecimiento.getSelectedItem().toString().trim()+fecha_guardado);
					}else{
					  	  JOptionPane.showMessageDialog(null,"Error Reoporte No Identificado","Avisa Al Adimistrador Del Sistema!", JOptionPane.ERROR_MESSAGE);
						  return;
						 }
					}
		}
	};
	


	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Reportes_De_Ubicaciones_De_Productos().setVisible(true);
		}catch(Exception e){	}
	}
}
