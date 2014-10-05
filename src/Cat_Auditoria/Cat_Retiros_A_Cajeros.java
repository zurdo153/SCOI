package Cat_Auditoria;

import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.border.Border;
import Conexiones_SQL.Connexion;
import Obj_Auditoria.Obj_Retiros_Cajeros;
import Obj_Principal.Componentes;


@SuppressWarnings("serial")
public class Cat_Retiros_A_Cajeros extends JFrame {

	
	Object[][] Matriz_pedidos_ctes ;
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	Connexion con = new Connexion();
	Runtime R = Runtime.getRuntime();
	
	JCheckBox chbActivar_Avisos = new JCheckBox("Ventana De Avisos");
	
	JTextField txtNombre = new Componentes().text(new JTextField(),"Nombre", 250, "String");
	JTextField txtEstablecimiento = new Componentes().text(new JTextField(),"Establecimiento", 150, "String");
	JTextField txtFolio_empleado =  new Componentes().text(new JTextField(),"Folio Empleado", 150, "String");
	JTextField txtpuesto =new Componentes().text(new JTextField(),"Puesto", 150, "String");
	JTextField txtasignacion =new Componentes().text(new JTextField(),"Asignacion", 150, "String");
	JTextField txtpc = new Componentes().text(new JTextField(),"Nombre Pc", 150, "String");
	
	
	JButton btnBuscar = new JButton(new ImageIcon("imagen/refrescar-volver-a-cargar-las-flechas-icono-4094-32.png"));;
	JButton btnFoto = new JButton();
	Border blackline, etched, raisedbevel, loweredbevel, empty;
	
	int folio_empleado =0;
    float importe_retiros_guardados =0;
    float importe_nuevo_devuelto=0;
    float valor_a_retirar_deacuerdo_al_dia =0;
    
    boolean cerrarhilo = false;
    
    
    
	public Cat_Retiros_A_Cajeros(Integer Folio_Empleado){
		folio_empleado=Folio_Empleado;
		
		this.setSize(400,190);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setTitle("Retiros_a_Cajeros");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/lista-icono-7220-32.png"));
		blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
		panel.setBorder(BorderFactory.createTitledBorder(blackline,"Cajero"));
//		(Window cont).setAlwaysOnTop( true );
		
		this.cont.add(panel);
		this.addWindowListener(op_cerrar);

		btnBuscar.setEnabled(true);
		btnBuscar.setToolTipText("<F5> Tecla Directa");
		btnBuscar.addActionListener(Buscar_Cambios);
		chbActivar_Avisos.setSelected(true);
		
		Hilo_1_Minuto();
		cargar_datos_del_empleado(Folio_Empleado);
		
		panel.add(chbActivar_Avisos).setBounds(420,20,150,20);
		panel.add(btnBuscar).setBounds(350,8,32,32);
		panel.add(btnFoto).setBounds(10,20,135,105);
		
		panel.add(txtFolio_empleado).setBounds(160,20,30,20);
		panel.add(txtNombre).setBounds(160,40,200,20);
		panel.add(txtEstablecimiento).setBounds(160,60,200,20);
		panel.add(txtpuesto).setBounds(160,80,200,20);
		panel.add(txtpc).setBounds(160,100,200,20);
		
		
		txtFolio_empleado.setEditable(false);
		txtNombre.setEditable(false);
		txtEstablecimiento.setEditable(false);
		txtpuesto.setEditable(false);
		txtpc.setEditable(false);
		
             
//     Buscar Con F5
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                     KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0), "Actualizar");
                  getRootPane().getActionMap().put("Actualizar", new AbstractAction(){
                      public void actionPerformed(ActionEvent e)
                      {        	    btnBuscar.doClick();          	    }
                  });
//     asigna el foco al JTextField fecha al arrancar la ventana
                  this.addWindowListener(new WindowAdapter() {
                          public void windowOpened( WindowEvent e ){
                        	  txtFolio_empleado.requestFocus();
                       }
                  });
	}
	
		    
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
////DISPOSE MATA EL HILO Y DETIENE EL SONIDO SI SONO ALGUNA VEZ		    
 AudioClip sonido;
WindowListener op_cerrar = new WindowListener() {
				public void windowOpened(WindowEvent e) {}
				public void windowIconified(WindowEvent e) {}
				public void windowDeiconified(WindowEvent e) {}
				public void windowDeactivated(WindowEvent e) {}
				public void windowClosing(WindowEvent e) {
						
						
						txtFolio_empleado.setText("");
						txtNombre.setText("");
						txtEstablecimiento.setText("");
						txtpuesto.setText("");
						
							cerrarhilo=true;
							dispose();
				}
				public void windowClosed(WindowEvent e) {}
				public void windowActivated(WindowEvent e) {}
			};
			
			
	public void cargar_datos_del_empleado(Integer folio_empleado){
		
		Obj_Retiros_Cajeros datosEmpleado= new Obj_Retiros_Cajeros().buscarEmpleado(folio_empleado);
		
 		ImageIcon tmpIconAux = new ImageIcon(System.getProperty("user.dir")+"/tmp/tmp_cajero/cajerotmp.jpg");
  	    btnFoto.setIcon(new ImageIcon(tmpIconAux.getImage().getScaledInstance(120, 95, Image.SCALE_DEFAULT)));	
  	    
        txtFolio_empleado.setText(datosEmpleado.getFolio_empleado()+"");
  	    txtNombre.setText(datosEmpleado.getNombre()+"");
  	    txtEstablecimiento.setText(datosEmpleado.getEstablecimiento()+"");
  	    txtpuesto.setText(datosEmpleado.getPuesto()+"");
  	    txtpc.setText(datosEmpleado.getPc()+"");
  	    btnBuscar.doClick();
	}		
			
	/////////CONSULTA EL IMPORTE NUEVO
   	public float Consulta_de_Importe_Nuevo(){
   		float importe_nuevo =0;
   		
	   	   String pc_nombre="";
			try {
			    	pc_nombre = InetAddress.getLocalHost().getHostName();
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion datos_cajero \n no se pudo obtener el nombre de la pc "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			}
		
   		String query_importe_nvo="SELECT  isnull(sum(liquidaciones_tickets.importe),0)as importe" +
	             " ,(select nombre from establecimientos where cod_estab=(select cod_estab from cajas where caja=(select caja from equipos_bms where nombre='"+pc_nombre+"')))as establecimiento"+
               "   FROM liquidaciones_tickets "+			             
               "WHERE liquidaciones_tickets.afectacion='+' AND liquidaciones_tickets.forma_pago=1 and (liquidaciones_tickets.folio_asignacion = (select folio_asignacion from cajeros"+ 
                           " where cod_estab=(select cod_estab from cajas where caja=(select caja from equipos_bms where nombre='"+pc_nombre+"')) and e_mail='"+folio_empleado+"'))";
   		
		Statement s;
		ResultSet rs2;
		
		try {
			s = new Connexion().conexion_IZAGAR().createStatement();
			rs2 = s.executeQuery(query_importe_nvo);
			while(rs2.next()){
				importe_nuevo = rs2.getFloat(1);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en Cat_Retiros_a_Cajeros  en la funcion [ Consulta_de_Importe_Nuevo ]   SQLException:  "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
	    return importe_nuevo; 
	}
   	
	/////////CONSULTA EL DE LOS RETIROS YA GUARDADOS
   	public float Consulta_El_Importe__de_los_Retiros_Guardados(){
   		
   		String query_importe_retiros="select isnull(sum(importe_retiro),0)as importe_retiro from tb_retiros_a_cajeros where status_retiro_corte=1 and folio_cajero="+folio_empleado;
   		
		Statement s;
		ResultSet rs2;
		
		try {
			s = new Connexion().conexion().createStatement();
			rs2 = s.executeQuery(query_importe_retiros);
			while(rs2.next()){
				importe_retiros_guardados = rs2.getFloat(1);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en Cat_Retiros_a_Cajeros  en la funcion [ Consulta_de_Importe_Nuevo ]   SQLException:  "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
	    return importe_retiros_guardados; 
	}
   	
	/////////CONSULTA DEL VALOR A RETIRAR DE ACUERDO AL DIA
   	public float Consulta_del_Importe_del_retiro_del_dia(){
   		
   		String query_importe_retiros_del_dia="exec sp_obtener_importe_del_retiro_del_dia";
   		
		Statement s;
		ResultSet rs2;
		
		try {
			s = new Connexion().conexion().createStatement();
			rs2 = s.executeQuery(query_importe_retiros_del_dia);
			while(rs2.next()){
				valor_a_retirar_deacuerdo_al_dia = rs2.getFloat(1);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en Cat_Retiros_a_Cajeros  en la funcion [ Consulta_del_Importe_del_retiro_del_dia ]   SQLException:  "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
	    return valor_a_retirar_deacuerdo_al_dia; 
	}
   	
////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////ACTUALIZAR

ActionListener Buscar_Cambios = new ActionListener(){
@SuppressWarnings("deprecation")
public void actionPerformed(ActionEvent e){
try {

importe_retiros_guardados        = Consulta_El_Importe__de_los_Retiros_Guardados();
importe_nuevo_devuelto           = Consulta_de_Importe_Nuevo();
valor_a_retirar_deacuerdo_al_dia = Consulta_del_Importe_del_retiro_del_dia();

System.out.println("importe_retiros_guardados"+importe_retiros_guardados);
System.out.println("importe_nuevo_devuelto"+importe_nuevo_devuelto);
System.out.println("valor_a_retirar_deacuerdo_al_dia"+valor_a_retirar_deacuerdo_al_dia);


if(importe_nuevo_devuelto-importe_retiros_guardados >= valor_a_retirar_deacuerdo_al_dia){

if(chbActivar_Avisos.isSelected()){
	

//   apartado para configurar el uso de la pantalla de avisos--------------------------------
JDialog frame = new JDialog();
String ruta= "prueba mensaje";//fila_mensaje.get(3).toString().trim();
frame.setUndecorated(true);
new Cat_Avisos_De_Pedido(frame,ruta);
frame.setVisible(true);
}
// se asigna a auxiliar el ultimi valor de los pedidos para que se compare en la siguiente consulta
//aux = importe_nuevo_devuelto; 
}

} catch (Exception e1) {
System.out.println(e1.getMessage());
JOptionPane.showMessageDialog(null, "Error en Cat_Consulta_De_Status_De_Pedidos_De_Clientes en la funcion Buscar_Cambios   SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
}
}
};
   	
/////////////////////////////////////////////////////////////////////////////////////
////////////HILO REVISION AUTOMATICA DE PEDIDOS CADA 60 SEGUNDOS
	public void Hilo_1_Minuto() {
			segundero seg = new segundero();
			seg.start();
		    	}
		    	int reconsultar=0;
		    	public class segundero extends Thread {
		    		public void run() {
		    			while(cerrarhilo !=true){
		    					try {
		    						Thread.sleep(1000);
		    						reconsultar+=1;
		    						if(reconsultar==60)////cambiar a 60 segundos
		    						{
		    						   reconsultar=0;
		    						   btnBuscar.doClick();
		    						}
		    					} catch (InterruptedException e) {
		    		                 JOptionPane.showMessageDialog(null, "Error en Cat_Hilo_1_Minuto en la funcion segundero  SQLException: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		    						System.err.println("Error: "+ e.getMessage());
		    				}
		    			}
		    	}
		    }
		    	
		    	
		    	
		    	
//////////////////////////////////////////////////////////////////////////////////
///////////CATALOO EMERGENTE DE AVISO		    	
		    	
	  	public class Cat_Avisos_De_Pedido extends JComponent {
		    		
		    		private Image background;
		    		
		    		JPasswordField txtClaveSupervisor = new Componentes().textPassword(new JPasswordField(), "Clave", 30);
		    		JLabel lblclave = new JLabel("Clave del Supervisor");
		    		Icon iconoFondo;
		    		ImageIcon tmpIconAuxFondo;
		    		JButton btnFoto_supervisor = new JButton();
		    		JLabel fondo = new JLabel();
		    		JTextField txtNombreSupervisor = new Componentes().text(new JTextField(),"Nombre del Supervisor", 250, "String");

		    		
		    		public Cat_Avisos_De_Pedido(final JDialog frame,String ruta) {
		    			
		    			//fileFoto=ruta;
		    			frame.setModal(true);
		    			frame.add(lblclave).setBounds(100,10,200,20); 
		    			frame.add(txtClaveSupervisor).setBounds(50,30,200,20);
		    			frame.add(btnFoto_supervisor).setBounds(85,70,135,105);
		    			frame.add(txtNombreSupervisor).setBounds(40,190,220,20);
		    			
		    			frame.add(fondo).setBounds(0,0,300,600);
		    			tmpIconAuxFondo = new ImageIcon("imagen/retiro_cajero.png");
		                iconoFondo = new ImageIcon(tmpIconAuxFondo.getImage().getScaledInstance(300,600, Image.SCALE_DEFAULT));
		                fondo.setIcon(iconoFondo);
		                
		                txtNombreSupervisor.setEditable(false);
		    			frame.setLayout(new BorderLayout( ));
		    			frame.getContentPane( ).add("Center",this);
		    			frame.setAlwaysOnTop( true );
		    			frame.setSize(300,600);
		    			frame.setLocationRelativeTo(null);
		    			
		    			 txtClaveSupervisor.addKeyListener(buscar_supervisor);
		    			
		    			
		    		    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
		    				       KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0), "cerrar");
		    		    
		    		    getRootPane().getActionMap().put("cerrar", new AbstractAction(){
		    		    	
		    		        @Override
		    		        public void actionPerformed(ActionEvent e)
		    		        {
		    		        	frame.dispose();
		    		        }
		    		    });
		    		    
		    		}
		    		KeyListener buscar_supervisor = new KeyListener() {
		    			@SuppressWarnings("deprecation")
						public void keyPressed(KeyEvent e) {	
		    				
		    				if(e.getKeyCode()==KeyEvent.VK_ENTER){
		    					cargar_datos_del_supervisor(txtClaveSupervisor.getText()+"");
		    					
		    				}
		    			}
		    			public void keyReleased(KeyEvent e) {}
		    			public void keyTyped(KeyEvent e) {}
		            }; 
		            
		    		public void cargar_datos_del_supervisor(String clave){
		    			
		    			if(txtClaveSupervisor.getText().toUpperCase().equals("")){
		                    JOptionPane.showMessageDialog(null, "La clave es requerida \n", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
		                    txtClaveSupervisor.setText("");
		                    txtClaveSupervisor.requestFocus();
		                    return;
		    				
		    			}else{
		    				
		    			
		    			Obj_Retiros_Cajeros datosSupervisor= new Obj_Retiros_Cajeros().buscarSupervisor(clave);
		    			
		    	 		ImageIcon tmpIconAux = new ImageIcon(System.getProperty("user.dir")+"/tmp/tmp_supervisor/supervisortmp.jpg");
		    	 		btnFoto_supervisor.setIcon(new ImageIcon(tmpIconAux.getImage().getScaledInstance(120, 95, Image.SCALE_DEFAULT)));	
		    	  	    
		    	        txtNombreSupervisor.setText(datosSupervisor.getNombre_Supervisor()+"");
//		    	  	    btnBuscar.doClick();
		    		    }	
		    		}
		    		
		    	}
		    	
///////////////////////////////////////////////////////////////////////////////////		    	
		    
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Retiros_A_Cajeros(740).setVisible(true);
		}catch(Exception e){	}
	}
}