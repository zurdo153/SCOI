package Cat_Auditoria;

import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.net.URL;
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
public class Cat_Retiros_A_Cajeros extends JFrame{

	
	Object[][] Matriz_pedidos_ctes ;
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	Connexion con = new Connexion();
	Runtime R = Runtime.getRuntime();
	
	
	
	JCheckBox chbActivar_Avisos = new JCheckBox("Ventana De Avisos");
	
	
	JTextField txtNombre = new Componentes().text(new JTextField(),"Nombre", 250, "String");
	JTextField txtEstablecimiento = new JTextField();
	JTextField txtFolio = new JTextField();
	JTextField txtFechaPedido = new JTextField();
	
	JButton btnBuscar = new JButton(new ImageIcon("imagen/refrescar-volver-a-cargar-las-flechas-icono-4094-32.png"));;
	JButton btnFoto = new JButton();
	
	Border blackline, etched, raisedbevel, loweredbevel, empty;
	
    int aux =0;
    int Existen_pedidos_nuevos=0;
    int SeActivoSonido=0;
    
    boolean cerrarhilo = false;
	
    
	public Cat_Retiros_A_Cajeros(Integer Folio_Empleado){
		this.setSize(400,400);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setTitle("Retiros_a_Cajeros");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/lista-icono-7220-32.png"));
		blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
		panel.setBorder(BorderFactory.createTitledBorder(blackline,"Cajeros"));
		this.cont.add(panel);
		this.addWindowListener(op_cerrar);

		
		btnBuscar.setEnabled(true);
		btnBuscar.setToolTipText("<F5> Tecla Directa");
		btnBuscar.addActionListener(Buscar_Cambios);
		
		chbActivar_Avisos.setSelected(true);
		
		txtEstablecimiento.setToolTipText("Filtro Por Nombre De Establecimiento");
		txtFolio.setToolTipText("Filtro Por Folio Del Pedido");
		txtFechaPedido.setToolTipText("Filtro Por Fecha Del Pedido");

		Hilo_1_Minuto();
		cargar_datos_del_empleado(Folio_Empleado);
		
		panel.add(chbActivar_Avisos).setBounds(420,20,150,20);
		panel.add(btnBuscar).setBounds(350,8,32,32);
		panel.add(btnFoto).setBounds(10,20,135,105);
		
		
//		panel.add(txtEstablecimiento).setBounds(15,20,130,20);
//		panel.add(txtFolio).setBounds(145,20,50,20);
//		panel.add(txtFechaPedido).setBounds(195,20,120,20);
             
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
                          	txtFolio.requestFocus();
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
						
						txtEstablecimiento.setText("");
						txtFolio.setText("");
						txtFechaPedido.setText("");
						
					 if(SeActivoSonido!=0){
						 	sonido.stop();}
							cerrarhilo=true;
							dispose();
				}
				public void windowClosed(WindowEvent e) {}
				public void windowActivated(WindowEvent e) {}
			};
			
	@SuppressWarnings("unused")
	public void cargar_datos_del_empleado(Integer folio_empleado){
		
		Obj_Retiros_Cajeros datosEmpleado= new Obj_Retiros_Cajeros().buscarEmpleado(folio_empleado);
		
 		ImageIcon tmpIconAux = new ImageIcon(System.getProperty("user.dir")+"/tmp/tmp_cajero/cajerotmp.jpg");
  	    btnFoto.setIcon(new ImageIcon(tmpIconAux.getImage().getScaledInstance(120, 95, Image.SCALE_DEFAULT)));	
		
	}		
			
	////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////ACTUALIZAR

	ActionListener Buscar_Cambios = new ActionListener(){
		@SuppressWarnings("deprecation")
		public void actionPerformed(ActionEvent e){
				try {
			
					Existen_pedidos_nuevos = ObtenerPedidoNuevo();
			   
				if(Existen_pedidos_nuevos != aux){
		      //////Limpiar	Filtros
					
					txtEstablecimiento.setText("");
					txtFolio.setText("");
					txtFechaPedido.setText("");
              //////Limpiar Tabla
              //////llenar arreglo desde funcion
			    	
			           txtFolio.requestFocus();
			         
						if(chbActivar_Avisos.isSelected()){
							   File f=new File("M:\\SISTEMA DE CONTROL OPERATIVO IZAGAR\\SCOI\\voz\\Nuevo_Pedido.wav");//archivo de audio
				        	    URL u=f.toURL();//lo convertimos a url
				        	    sonido=JApplet.newAudioClip(u); //Bueno de la AudioClip no se puede instancias por eso esto
  				        	    sonido.play();//para que suene
  				        	    SeActivoSonido=1;
  				        	    
  				        	//   apartado para configurar el uso de la pantalla de avisos--------------------------------
                                JDialog frame = new JDialog();
                                 String ruta= "prueba mensaje";//fila_mensaje.get(3).toString().trim();
                    		    frame.setUndecorated(true);
                    		    new Cat_Avisos_De_Pedido(frame,ruta);
                    		    frame.setVisible(true);
						}
		        	 // se asigna a auxiliar el ultimi valor de los pedidos para que se compare en la siguiente consulta
		        	 aux = Existen_pedidos_nuevos; 
		        	}
				
				} catch (Exception e1) {
					System.out.println(e1.getMessage());
					JOptionPane.showMessageDialog(null, "Error en Cat_Consulta_De_Status_De_Pedidos_De_Clientes en la funcion Buscar_Cambios   SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			}
		}
	};
	
	/////////OBTIENE PEDIDOS PARA AVISAR QUE HAY PEDIDO NUEVO 
   	public int ObtenerPedidoNuevo(){
   		int pedidos =0;
   		
		String todos = ("select count(establecimientos.nombre) FROM pedcte " +
						"left outer join establecimientos on establecimientos.cod_estab=pedcte.cod_estab " +
						"where pedcte.fecha_elaboracion>convert(varchar(20),getdate(),103)+' 00:00:000' and pedcte.cod_estab in(20,15)");
		Statement s;
		ResultSet rs2;
		
		try {
			s = new Connexion().conexion_IZAGAR().createStatement();
			rs2 = s.executeQuery(todos);
			
			while(rs2.next()){
				pedidos = rs2.getInt(1);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en Cat_Consulta_De_Status_De_Pedidos_De_Clientes  en la funcion [ ObtenerValoresPedClientes ]   SQLException:  "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
	    return pedidos; 
	}
   	
	/////////OBTIENE DATOS DESDE IZAGAR PARA LLENAR TABLA CON LOS NUEVOS CAMBIOS
   	public Object[][] ObtenerValoresPedClientes(){
   		
		String todos = (" select establecimientos.nombre as establecimiento " +
		        " ,pedcte.folio as pedido    " +
				" ,convert(varchar(50),pedcte.fecha,103)+' '+convert(varchar(50),pedcte.fecha,108)as elaboracion_pedido " +
				" ,isnull(convert(varchar(50),facremtick.fecha,103)+' '+convert(varchar(50),facremtick.fecha,108),'NO SURTIDO')as surtido_pedido " +
				" ,isnull(DATEDIFF(minute,pedcte.fecha,facremtick.fecha),(datediff(minute,pedcte.fecha,getdate())))as Tiempo_Para_Surtido " +
				" ,pedcte.notas " +
				" ,isnull(cajeros.nombre,'NO SURTIDO') as cajero" +
				" FROM pedcte" +
				" left outer join facremtick on facremtick.folio_origen=pedcte.folio" +
				" left outer join cajeros on cajeros.cajero=facremtick.cajero" +
				" left outer join establecimientos on establecimientos.cod_estab=pedcte.cod_estab " +
				" where pedcte.fecha_elaboracion>convert(varchar(20),getdate(),103)+' 00:00:000' and pedcte.cod_estab in(20,15) " +
				" order by pedcte.cod_estab desc,pedcte.fecha asc ");
		
		Statement s;
		ResultSet rs2;

		try {
			s = new Connexion().conexion_IZAGAR().createStatement();
			rs2 = s.executeQuery(todos);
			Matriz_pedidos_ctes = new Object[LlenadoFilasPedCte(todos)][7];
			int i=0;
			while(rs2.next()){
				Matriz_pedidos_ctes[i][0] = "   "+rs2.getString(1).trim();
				Matriz_pedidos_ctes[i][1] = "   "+rs2.getString(2).trim();
				Matriz_pedidos_ctes[i][2] = "   "+rs2.getString(3).trim();
				Matriz_pedidos_ctes[i][3] = "   "+rs2.getString(4).trim();
				Matriz_pedidos_ctes[i][4] = "   "+rs2.getString(5).trim();
				Matriz_pedidos_ctes[i][5] = "   "+rs2.getString(6).trim();
				Matriz_pedidos_ctes[i][6] = "   "+rs2.getString(7).trim();
								i++;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en Cat_Consulta_De_Status_De_Pedidos_De_Clientes  en la funcion [ ObtenerValoresPedClientes ]   SQLException:  "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
	    return Matriz_pedidos_ctes; 
	}
  public int LlenadoFilasPedCte(String qry){
		int filas=0;
		Statement stmt = null;
		try {stmt = new Connexion().conexion_IZAGAR().createStatement();
			ResultSet rs2 = stmt.executeQuery(qry);
			while(rs2.next()){filas++;}
		} catch (SQLException e1) {	e1.printStackTrace();}
		return filas;
	}
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
		    		JLabel lblAviso = new JLabel();
		    		String fileFoto = System.getProperty("user.dir")+"/imagen/avisos/Hay_Un_Pedido_1.png";
		    		ImageIcon tmpIconAuxFoto = new ImageIcon(fileFoto);

		    		public Cat_Avisos_De_Pedido(final JDialog frame,String ruta) {
		    			
		    			//fileFoto=ruta;
		    			frame.setModal(true);
		    			updateBackground( );
		    			frame.add(lblAviso).setBounds(0, 0, 500, 400);
		    			 Icon iconoFoto = new ImageIcon(tmpIconAuxFoto.getImage().getScaledInstance(lblAviso.getWidth(), lblAviso.getHeight(), Image.SCALE_DEFAULT));
		                 lblAviso.setIcon(iconoFoto);
		                 
		    			frame.setLayout(new BorderLayout( ));
		    			frame.getContentPane( ).add("Center",this);
		    			frame.pack( );
		    			frame.setAlwaysOnTop( true );
		    			frame.setSize(700,400);
		    			frame.setLocationRelativeTo(null);
		    		    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
		    				       KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "foco");
		    		    
		    		    getRootPane().getActionMap().put("foco", new AbstractAction(){
		    		        @Override
		    		        public void actionPerformed(ActionEvent e)
		    		        {
		    		        	frame.dispose();
		    		        }
		    		    });
		    		}
		    		
		    		
		    		public void updateBackground( ) {
		    		try {
		    		Robot rbt = new Robot( );
		    		Toolkit tk = Toolkit.getDefaultToolkit( );
		    		Dimension dim = tk.getScreenSize( );
		    		background = rbt.createScreenCapture(
		    		new Rectangle(0,0,(int)dim.getWidth( ),
		    		(int)dim.getHeight( )));
		    		} catch (Exception ex) {
		    		ex.printStackTrace( );
		    		}
		    		}
		    		
		    		public void paintComponent(Graphics g) {
		    		Point pos = this.getLocationOnScreen( );
		    		Point offset = new Point(-pos.x,-pos.y);
		    		g.drawImage(background,offset.x,offset.y,null);
		    		repaint();
		    		}

		    	}
		    	
///////////////////////////////////////////////////////////////////////////////////		    	
		    
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Retiros_A_Cajeros(1).setVisible(true);
		}catch(Exception e){	}
	}
}