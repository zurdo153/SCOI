package Cat_Auditoria;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.BuscarTablasModel;
import Conexiones_SQL.Connexion;
import Conexiones_SQL.Generacion_Reportes;
import Conexiones_SQL.GuardarSQL;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Auditoria.Obj_Retiros_Cajeros;
import Obj_Principal.Componentes;
import Obj_Principal.JCTextField;

@SuppressWarnings("serial")
public class Cat_Retiros_A_Cajeros extends JFrame {
	
	Object[][] Matriz_pedidos_ctes ;
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	Connexion con = new Connexion();
	Runtime R = Runtime.getRuntime();
	
	JTextField txtNombre = new Componentes().text(new JTextField(),"Nombre", 250, "String");
	JTextField txtEstablecimiento = new Componentes().text(new JTextField(),"Establecimiento", 150, "String");
	JTextField txtFolio_empleado =  new Componentes().text(new JTextField(),"Folio Empleado", 150, "String");
	JTextField txtpuesto =new Componentes().text(new JTextField(),"Puesto", 150, "String");
	JTextField txtasignacion =new Componentes().text(new JTextField(),"Asignacion", 150, "String");
	JTextField txtpc = new Componentes().text(new JTextField(),"Nombre Pc", 150, "String");
	JTextField txtsaldoTA = new Componentes().text(new JCTextField(),"Saldo TA", 150, "Int");

	JButton btnFoto = new JButton();
	JButton btnaviso = new JButton();
	JButton btnImpresion_Retiros_Pasados=new JButton("Retiros",new ImageIcon("imagen/Print.png"));
	JButton btnISaldoTA=new JButton("Captura Saldo TA",new ImageIcon("imagen/telefono-celular-hp-ipaq-2-de-telefonia-movil-icono-6906-16.png"));
	JButton btnGuardar = new JButton("Guardar",new ImageIcon("imagen/Guardar.png"));
	JButton btnPedido=new JButton("Pedir Monedas",new ImageIcon("imagen/monedas-en-efectivo-en-moneda-icono-4023-16.png"));
	JButton btnRecibir=new JButton("Recibir Monedas",new ImageIcon("imagen/monedas-en-efectivo-en-moneda-icono-4023-16.png"));
	
	Icon iconoFondo_cajero;
	ImageIcon ImagenconFondo_cajero;
	JLabel jlFondo_cajero =new JLabel();
	JLabel JLBlinicioTA= new JLabel(new ImageIcon("Imagen/flecha-verde-icono-8451-16.png") );
	JLabel JLBlsalidaTA= new JLabel(new ImageIcon("Imagen/flecha-azul-hacia-abajo-icono-7343-16.png") );
	
	int folio_empleado =0;
    float importe_retiros_guardados =0;
    float importe_nuevo_devuelto=0;
    float valor_a_retirar_deacuerdo_al_dia =0;
    
    String Asignacion =""; 
    boolean cerrarhilo = false;
    String  saldoinicialfinal="";
    
	public Cat_Retiros_A_Cajeros(){
		this.cont.add(panel);
		this.setSize(355,165);
		this.setResizable(false);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/boveda-de-dinero-en-efectivo-de-seguridad-icono-6192-32.png"));
		this.cont.add(panel);
		this.setTitle("Retiros A Cajeros");
		
		folio_empleado=new Obj_Usuario().LeerSession().getFolio();
		///todo revisar
		Obj_Retiros_Cajeros datosEmpleado= new Obj_Retiros_Cajeros().buscarEmpleado(folio_empleado);
		if(datosEmpleado.getAsignacion()== null){
			JOptionPane.showMessageDialog(null, "El Usuario No Esta Asignado ", "Aviso", JOptionPane.WARNING_MESSAGE);
			btnaviso.setText(	"<html> <FONT FACE="+"arial"+" SIZE=5 COLOR=BLUE>" +
					"		<CENTER><p> CIERRA ESTA VENTANA Y VUELVE A INTENTARLO UN MINUTO DESPUES QUE TE ASIGNEN</p></CENTER></FONT></html>"); 
			panel.add(btnaviso).setBounds(1,1,350,90);
		}else{
			
		this.setUndecorated(true);
		this.setOpacity(0.99f);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		btnFoto.setToolTipText("<F5> Tecla Directa");
		btnFoto.addActionListener(Buscar_Cambios);
		
		Hilo_1_Minuto();
		cargar_datos_del_empleado(folio_empleado);
		
		panel.add(btnFoto).setBounds(7,8,135,105);
		panel.add(txtFolio_empleado).setBounds(145,8,30,20);
		panel.add(btnImpresion_Retiros_Pasados).setBounds(175,8,100,20);
		panel.add(txtasignacion).setBounds(275,8,70,20);
		panel.add(txtNombre).setBounds(145,28,200,20);
		panel.add(txtEstablecimiento).setBounds(145,48,200,20);
		panel.add(txtpuesto).setBounds(145,68,200,20);
		panel.add(txtpc).setBounds(145,88,200,20);
		panel.add(btnISaldoTA).setBounds(7,113,137,20);
		
		panel.add(JLBlinicioTA).setBounds(140,113,20,20);
		panel.add(txtsaldoTA).setBounds(160,113,70,20);
		panel.add(JLBlsalidaTA).setBounds(230,113,20,20);
		panel.add(btnGuardar).setBounds(250,113,95,20);
		
		panel.add(btnPedido).setBounds(7,135,137,20);
		panel.add(btnRecibir).setBounds(214,135,130,20);
		
		ValidaPedido();
//		pedidoDeMonedas(btnPedido);
//		pedidoDeMonedas(btnRecibir);
		
		txtFolio_empleado.setEditable(false);
		txtasignacion.setEditable(false);
		txtNombre.setEditable(false);
		txtEstablecimiento.setEditable(false);
		txtpuesto.setEditable(false);
		txtpc.setEditable(false);
		txtsaldoTA.setEnabled(false);
		JLBlinicioTA.setEnabled(false);
		JLBlsalidaTA.setEnabled(false);
		btnGuardar.setEnabled(false);
		
		btnImpresion_Retiros_Pasados.addActionListener(op_filtro_reimpresion_de_Retiros);
		btnISaldoTA.addActionListener(op_captura_saldo_TA);
		btnGuardar.addActionListener(op_guardar_saldo);
		txtsaldoTA.addKeyListener(Pasar_Guarda_Saldo_TA);
		
         //////fondo		
		ImagenconFondo_cajero = new ImageIcon("imagen/marco_aux_caja.png");
	    iconoFondo_cajero = new ImageIcon(ImagenconFondo_cajero.getImage().getScaledInstance(355,165, Image.SCALE_DEFAULT));
	    jlFondo_cajero.setIcon(iconoFondo_cajero);
	    panel.add(jlFondo_cajero).setBounds(0,0,355,165);
             
           //  Buscar Con F5
                  getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                     KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0), "Actualizar");
                  getRootPane().getActionMap().put("Actualizar", new AbstractAction(){
                      public void actionPerformed(ActionEvent e)
                      {        	    btnFoto.doClick();          	    }
                  });
                  
           // asigna el foco al JTextField fecha al arrancar la ventana
                  this.addWindowListener(new WindowAdapter() {
                          public void windowOpened( WindowEvent e ){
                        	  txtFolio_empleado.requestFocus();
                       }
                  });
		}    
		dispose();
	}
	
	KeyListener Pasar_Guarda_Saldo_TA = new KeyListener() {
		public void keyPressed(KeyEvent e) {	
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				btnGuardar.doClick();
			}
		}
		public void keyReleased(KeyEvent e) {}
		public void keyTyped(KeyEvent e) {}
    };
    
	
	public void ValidaPedido(){
//			System.out.println(new BuscarTablasModel().checar_Pedido_De_Monedas_Cajero());
		switch(new BuscarTablasModel().checar_Pedido_De_Monedas_Cajero()){
			case "PEDIDO": 		btnPedido.setEnabled(false);		btnRecibir.setEnabled(false); break;
			case "SURTIDO": 	btnPedido.setEnabled(false);		btnRecibir.setEnabled(true); break;
			case "ENTREGADO": 	btnPedido.setEnabled(false);		btnRecibir.setEnabled(true); break;
			case "CANCELADO": 	btnPedido.setEnabled(true);			btnRecibir.setEnabled(false); break;
			default: 			btnPedido.setEnabled(true);			btnRecibir.setEnabled(false); break; //RECIBIDO
		}
	}

	public void pedidoDeMonedas(final JButton btn){
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(btn.getActionCommand().equals("Pedir Monedas")){
					new CapturarPedido(Integer.valueOf(txtFolio_empleado.getText().toString().trim()),txtNombre.getText().toString().trim(), "PEDIDO", "CAJERA").setVisible(true);
				}else{
					new CapturarPedido(Integer.valueOf(txtFolio_empleado.getText().toString().trim()),txtNombre.getText().toString().trim(), "RECIBIDO", "CAJERA").setVisible(true);
				}
			}
		});
	}
	
	public void cargar_datos_del_empleado(Integer folio_empleado){
		Obj_Retiros_Cajeros datosEmpleado= new Obj_Retiros_Cajeros().buscarEmpleado(folio_empleado);

		ImageIcon tmpIconAux = new ImageIcon(System.getProperty("user.dir")+"/tmp/tmp_cajero/cajerotmp.jpg");
  	    btnFoto.setIcon(new ImageIcon(tmpIconAux.getImage().getScaledInstance(120, 95, Image.SCALE_DEFAULT)));	
  	    txtFolio_empleado.setText(datosEmpleado.getFolio_empleado()+"");
  	    txtNombre.setText(datosEmpleado.getNombre()+"");
  	    txtEstablecimiento.setText(datosEmpleado.getEstablecimiento()+"");
  	    txtpuesto.setText(datosEmpleado.getPuesto()+"");
  	    txtpc.setText(datosEmpleado.getPc()+"");
  	    txtasignacion.setText(datosEmpleado.getAsignacion()+"");
  	    btnFoto.doClick();
  	    
	    String  Guardo_sesion=new Obj_Retiros_Cajeros().guardar_sesion(datosEmpleado.getEstablecimiento()+"",folio_empleado);
	 
		   if(Guardo_sesion !="Error en GuardarSQL"){
		  }else{
			  JOptionPane.showMessageDialog(null, "Error en Cat_Retiros_A_Cajeros  en la funcion Guardo_sesion \n no se pudo obtener el nombre de la pc ", "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		            }
	}
	
	ActionListener op_filtro_reimpresion_de_Retiros = new ActionListener(){
		public void actionPerformed(ActionEvent arg0){
         new Cat_Filtro_De_Retiros_Guardados().setVisible(true);
		}
	};
	
	ActionListener op_captura_saldo_TA = new ActionListener(){
		public void actionPerformed(ActionEvent arg0){
			try {
			if(new BuscarSQL().inicio_final_TA(folio_empleado).equals("I"))
			{
				JLBlinicioTA.setEnabled(false);
				JLBlsalidaTA.setEnabled(true);
				saldoinicialfinal="Saldo Final";
			}else{
			   	JLBlinicioTA.setEnabled(true);
				JLBlsalidaTA.setEnabled(false);
			    saldoinicialfinal="Saldo Inicial";
		   	}
			;
			} catch (SQLException e) {
				       e.printStackTrace();
			}
            txtsaldoTA.setEnabled(true);
            txtsaldoTA.setText("");
            btnGuardar.setEnabled(true);
            txtsaldoTA.requestFocus();
		}
	};

	
	ActionListener op_guardar_saldo = new ActionListener(){
		public void actionPerformed(ActionEvent arg0){
			if(txtsaldoTA.getText().toString().equals(""))
			{
				JOptionPane.showMessageDialog(null, "Necesita Teclear Una Cantidad >En Saldo TA< Para Poder Guardar", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
			   txtsaldoTA.requestFocus();	
				return;
			}else{
				if(Actualizar_ta_tira()){
					txtsaldoTA.setEnabled(false);
					btnGuardar.setEnabled(false);
					new Cat_Validar_Clave_Supervisor().setVisible(true);
				}else{
					JOptionPane.showMessageDialog(null, "Error en Actualizar_ta_tira  en la funcion SQLException: ", "Avisa al Administrador del sistema", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		}
	};

////////////UPDATE DE MOVIMIENTOS VIEJOS PARA QUE NO SE REVUELVAN CON LOS NUEVOS EN EL REPORTE DE LA TIRA DE TIEMPO AIRE	
	public boolean Actualizar_ta_tira(){
		String query = "update tb_venta_por_cada_tira_ta set estatus='R' " +
				       " where folio_empleado="+folio_empleado+" and estatus='V' ";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en Actualizar_ta_tira  en la funcion SQLException: "+e.getMessage()+" "+ex.getMessage(), "Avisa al Administrador del sistema", JOptionPane.ERROR_MESSAGE);
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return true;
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

		String query_importe_nvo="exec IZAGAR_efectivo_en_caja'"+pc_nombre+"','"+folio_empleado+"'";
		Statement s;
		ResultSet rs2;
		try {
			s = new Connexion().conexion_IZAGAR().createStatement();
			rs2 = s.executeQuery(query_importe_nvo);
			while(rs2.next()){
				importe_nuevo = rs2.getFloat(1);
				Asignacion=rs2.getString(2);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en Cat_Retiros_a_Cajeros  en la funcion [ Consulta_de_Importe_Nuevo ]   SQLException:  "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
	    return importe_nuevo; 
	}
   	
/////////CONSULTA EL DE LOS RETIROS YA GUARDADOS
   	public float Consulta_El_Importe__de_los_Retiros_Guardados(){
   		importe_retiros_guardados=0;
   		String query_importe_retiros="exec sp_consulta_acumulado_de_retiros_a_cajeros_del_dia_2 "+folio_empleado+",'"+Asignacion.trim()+"'";
		Statement s;
		ResultSet rs2;
		
		try {
			s = new Connexion().conexion().createStatement();
			rs2 = s.executeQuery(query_importe_retiros);
			while(rs2.next()){
				importe_retiros_guardados = rs2.getFloat("importe_retiro");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en Cat_Retiros_a_Cajeros  en la funcion [ Consulta_de_Importe_Nuevo ]   SQLException: sp_consulta_acumulado_de_retiros_a_cajeros_del_dia  "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
	    return importe_retiros_guardados; 
	}
   	
/////////CONSULTA DEL VALOR A RETIRAR DE ACUERDO AL DIA
   	public float Consulta_del_Importe_del_retiro_del_dia(){
   		valor_a_retirar_deacuerdo_al_dia=0;
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
/////////TODO CALCULO DEL SALDOACTUALIZAR

ActionListener Buscar_Cambios = new ActionListener(){
public void actionPerformed(ActionEvent e){
	
	ValidaPedido();
	
try {
	importe_retiros_guardados        = 0;
	importe_nuevo_devuelto           = 0;
	valor_a_retirar_deacuerdo_al_dia = 0;
	
importe_nuevo_devuelto           = Consulta_de_Importe_Nuevo();	
importe_retiros_guardados        = Consulta_El_Importe__de_los_Retiros_Guardados();	
valor_a_retirar_deacuerdo_al_dia = Consulta_del_Importe_del_retiro_del_dia();

//System.out.println("importe_retiros_guardados:"+importe_retiros_guardados);
//System.out.println("importe_nuevo_devuelto:"+importe_nuevo_devuelto);
//System.out.println("valor_a_retirar_deacuerdo_al_dia:"+valor_a_retirar_deacuerdo_al_dia);

if(importe_nuevo_devuelto-importe_retiros_guardados >= valor_a_retirar_deacuerdo_al_dia){
	String establecimiento= txtEstablecimiento.getText()+"" ;
	        cerrarhilo=true;
			//   apartado para configurar el uso de la pantalla de avisos--------------------------------
					JDialog frame = new JDialog();
					String ruta= "prueba mensaje";//fila_mensaje.get(3).toString().trim();
					frame.setUndecorated(true);
					
					new Cat_Avisos_De_Retiro(frame,ruta,establecimiento,valor_a_retirar_deacuerdo_al_dia);
					frame.setVisible(true);

}

} catch (Exception e1) {
System.out.println(e1.getMessage());
JOptionPane.showMessageDialog(null, "Error en Cat_Consulta_De_Status_De_Pedidos_De_Clientes en la funcion Buscar_Cambios   SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
}
}
};
   	
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
		    						if(reconsultar==120)////cambiar a 60 segundos
		    						{
		    						   reconsultar=0;
		    						   btnFoto.doClick();
		    						}
		    					} catch (InterruptedException e) {
		    		                 JOptionPane.showMessageDialog(null, "Error en Cat_Hilo_1_Minuto en la funcion segundero  SQLException: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		    						System.err.println("Error: "+ e.getMessage());
		    				}
		    			}
		    	}
		    }
		    	
	////////////TODO REPORTE DEL RETIRO				
				public void Reporte_De_Retiros_Cajeros(String folio_retiro) {
					String basedatos="2.26";
					String vista_previa_reporte="no";
					int vista_previa_de_ventana=0;
					String reporte = "Obj_Reporte_De_Retiro_A_Cajeros.jrxml";
					String comando = "exec sp_Reporte_De_Retiros_A_Cajeros '"+folio_retiro+"';";
			   	    new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
				 }
		    	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////CATALOGO EMERGENTE DEL RETIRO//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	    	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		    	
	  	public class Cat_Avisos_De_Retiro extends JComponent {
		    		
		    		JPasswordField txtClaveSupervisor = new Componentes().textPassword(new JPasswordField(), "Clave", 30);
		    		JPasswordField txtClaveSupervisorconfirma = new Componentes().textPassword(new JPasswordField(), "Clave del Supervisor Confirmacion", 30);
		    		JLabel lblclave = new JLabel("Clave del Supervisor");
		    		JLabel lblretiro = new JLabel("Cantidad del Retiro");
		    		JLabel lblNombre_Supervisor= new JLabel();
		    		JLabel lblConfirmacion= new JLabel("Confirmacion Clave De Supervisor");
		    		
		    		Icon iconoFondo;
		    		ImageIcon tmpIconAuxFondo;
		    		JButton btnFoto_supervisor = new JButton();
		    		JButton btnNoExiste_Supervisor = new JButton();
		    		JButton btnError_Clave_requerida =new JButton();
		    		JButton btnError =new JButton();
		    		JButton btnValidar_Retiro= new JButton("Validar",new ImageIcon("imagen/Aplicar.png"));
		    		JButton btnEditar_retiro = new JButton("Editar Retiro",new ImageIcon("imagen/editara.png"));
		    		JButton btnImprimir = new JButton("Imprimir",new ImageIcon("imagen/Print.png"));
		    		JButton btnSalir = new JButton("Salir",new ImageIcon("imagen/salir16.png"));
		    		
		    		JLabel fondo = new JLabel();
		    		JTextField txtfolio_Supervisor = new Componentes().text(new JTextField(),"folio_supervisor", 250, "String");
		    		JTextField txtRetiro = new Componentes().text(new JTextField(),"Cantidad del Retiro del Cajero", 30, "Double");
		    		JDialog framesalir=null;
		    		String Establecimiento="";
		    		String folio_retiro="";
		    		float importe_del_retiro_del_dia=0;
		    		
		    		public Cat_Avisos_De_Retiro(final JDialog frame,String ruta, String establecimiento,float Importe_del_Retiro_del_dia) {
		    			Establecimiento=establecimiento;
		    			framesalir=frame;
		    			importe_del_retiro_del_dia=Importe_del_Retiro_del_dia;
		    			
		    			frame.setModal(true);
		    			frame.add(lblclave).setBounds(100,10,200,20); 
		    			frame.add(txtClaveSupervisor).setBounds(80,30,140,20);
		    			frame.add(btnFoto_supervisor).setBounds(85,70,135,105);
		    			frame.add(lblNombre_Supervisor).setBounds(60,190,220,20);
		    			frame.add(lblretiro).setBounds(100,270,200,20); 
		    			frame.add(txtRetiro).setBounds(80,290,140,20);
		    			frame.add(btnValidar_Retiro).setBounds(100,330,100,20);
		    			frame.add(lblConfirmacion).setBounds(70,380,200,20);
		    			frame.add(txtClaveSupervisorconfirma).setBounds(80,400,140,20);
		    			frame.add(btnEditar_retiro).setBounds(15,440,130,20);
		    			frame.add(btnSalir).setBounds(155,440,130,20);
		    			frame.add(btnNoExiste_Supervisor).setBounds(8,245,283,345);
		    			frame.add(btnError_Clave_requerida).setBounds(8,245,283,345);
		    			frame.add(btnError).setBounds(8,470,283,120);
		    			frame.add(fondo).setBounds(0,0,300,600);
		    			
		    			
		    			tmpIconAuxFondo = new ImageIcon("imagen/retiro_cajero.png");
		                iconoFondo = new ImageIcon(tmpIconAuxFondo.getImage().getScaledInstance(300,600, Image.SCALE_DEFAULT));
		                fondo.setIcon(iconoFondo);
	
		        		btnNoExiste_Supervisor.setText(	"<html> <FONT FACE="+"arial"+" SIZE=7 COLOR=RED>" +
		        										"		<CENTER><p>NO EXISTE</p></CENTER>" +
		        										"		<CENTER><p>EL SUPERVISOR</p></CENTER>" +
		        										"		<CENTER><p> O CLAVE INCORRECTA</p></CENTER></FONT>" +
		        										"</html>"); 
		                
		        		btnError_Clave_requerida.setText(	"<html> <FONT FACE="+"arial"+" SIZE=7 COLOR=BLACK>" +
								"		<CENTER><p> SE REQUIERE CLAVE DE SUPERVISOR</p></CENTER></FONT></html>"); 
		        		
		        		
		        		txtClaveSupervisor.setEditable(true);
		                txtRetiro.setVisible(false);
		                lblNombre_Supervisor.setVisible(false);
		                lblretiro.setVisible(false);
		                lblConfirmacion.setVisible(false);
		                btnNoExiste_Supervisor.setVisible(false);
		                btnError_Clave_requerida.setVisible(false);
		                btnValidar_Retiro.setVisible(false);
		                btnFoto_supervisor.setVisible(false);
		                btnEditar_retiro.setVisible(false);
		                btnImprimir.setVisible(false);
		                btnSalir.setVisible(false);
		                btnError.setVisible(false);
		                btnEditar_retiro.setEnabled(false);
		                btnImprimir.setEnabled(false);
		                
		                
		                txtClaveSupervisorconfirma.setVisible(false);
		                
		    			frame.setLayout(new BorderLayout( ));
		    			frame.getContentPane( ).add("Center",this);
		    			frame.setAlwaysOnTop( true );
		    			frame.setSize(300,600);
		    			frame.setLocationRelativeTo(null);
		    			
		    			txtRetiro.setText(importe_del_retiro_del_dia+"");
		    			
	    			    txtClaveSupervisor.addKeyListener(buscar_supervisor);
		    			txtClaveSupervisorconfirma.addKeyListener(validacion_clave_supervisor);
		    			txtRetiro.addKeyListener(pasar_a_validar_retiro);
			            btnValidar_Retiro.addActionListener(pasar_a_validar_clave_supervisor);
			            btnEditar_retiro.addActionListener(regresar_modificar_retiro);
			        	btnImprimir.addActionListener(imprimir_retiro); 
		    			btnSalir.addActionListener(salir);
		    			
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
		            
		            
		    		KeyListener validacion_clave_supervisor = new KeyListener() {
		    			@SuppressWarnings("deprecation")
						public void keyPressed(KeyEvent e) {	
		    				
		    				if(e.getKeyCode()==KeyEvent.VK_ENTER){
		    					btnError.setVisible(false);
		    					validar_clave_de_supervisor(txtClaveSupervisorconfirma.getText()+"");
		    				}
		    			}
		    			public void keyReleased(KeyEvent e) {}
		    			public void keyTyped(KeyEvent e) {}
		            };
		            
		            
		            
		    		KeyListener pasar_a_validar_retiro = new KeyListener() {
		    			public void keyPressed(KeyEvent e) {	
		    				if(e.getKeyCode()==KeyEvent.VK_ENTER){
		    					btnValidar_Retiro.doClick();
		    				}
		    			}
		    			public void keyReleased(KeyEvent e) {}
		    			public void keyTyped(KeyEvent e) {}
		            }; 

		            
		    		@SuppressWarnings("deprecation")
					public void cargar_datos_del_supervisor(String clave){
		    			
		    			if(txtClaveSupervisor.getText().toUpperCase().equals("")){
		                    txtClaveSupervisor.setText("");
		                    txtClaveSupervisor.requestFocus();
		                    txtClaveSupervisorconfirma.setVisible(true);
		                    txtClaveSupervisorconfirma.setEditable(false);
		                    btnError_Clave_requerida.setVisible(true);
		                    btnNoExiste_Supervisor.setVisible(false);
		                    btnFoto_supervisor.setVisible(false);
		                    return;
		    			}else{
		    				
		    			
		    			Obj_Retiros_Cajeros datosSupervisor= new Obj_Retiros_Cajeros().buscarSupervisor(clave);
		    			
		    			if(datosSupervisor.getExiste_supervisor().equals("NO EXISTE")){
			    	 		ImageIcon tmpIconAux = new ImageIcon(System.getProperty("user.dir")+"/Iconos/Un.jpg");
			    	 		btnFoto_supervisor.setIcon(new ImageIcon(tmpIconAux.getImage().getScaledInstance(120, 95, Image.SCALE_DEFAULT)));
			    	 		lblNombre_Supervisor.setVisible(true);
		                    txtClaveSupervisor.setText("");
		                    txtClaveSupervisor.requestFocus();
		                    
			    	 		lblNombre_Supervisor.setText(	"<html> <FONT FACE="+"arial"+" SIZE=4 COLOR=RED>" +
									"	<CENTER><p> "+datosSupervisor.getNombre_Supervisor()+"</p></CENTER></FONT></html>"); 
			    	 		
			    	 		btnNoExiste_Supervisor.setVisible(true);
			    	 		btnError_Clave_requerida.setVisible(false);
			    	 		btnFoto_supervisor.setVisible(true);
			    	 		
		    			}else{
		    			
		    	 		ImageIcon tmpIconAux = new ImageIcon(System.getProperty("user.dir")+"/tmp/tmp_supervisor/supervisortmp.jpg");
		    	 		btnFoto_supervisor.setIcon(new ImageIcon(tmpIconAux.getImage().getScaledInstance(120, 95, Image.SCALE_DEFAULT)));	
		    	        lblNombre_Supervisor.setText(datosSupervisor.getNombre_Supervisor()+"");
		    	        txtfolio_Supervisor.setText(datosSupervisor.getFolio_supervisor()+"");
		    	        
		    	        lblNombre_Supervisor.setVisible(true);
		    	        lblretiro.setVisible(true);
		    	        lblConfirmacion.setVisible(true);
		    	        txtClaveSupervisor.setEditable(false);
		    	        
                        txtClaveSupervisorconfirma.setEditable(false);		    	        
		    	        txtClaveSupervisorconfirma.setVisible(true);
		    	        txtRetiro.setVisible(true);
		    	        txtRetiro.setEditable(false);
		    	       
		    	        
		    	        btnNoExiste_Supervisor.setVisible(false);
		    	        btnError_Clave_requerida.setVisible(false);
		    	        btnValidar_Retiro.setVisible(true);
		    	        btnValidar_Retiro.setEnabled(true);
		    	        btnValidar_Retiro.doClick();
		    	        btnFoto_supervisor.setVisible(true);
		    	        
		    			}
		    		    }	
		    		}
		    		
		    		@SuppressWarnings("deprecation")
					public void validar_clave_de_supervisor(String clave_supervisor){
		    			if(txtClaveSupervisorconfirma.getText().toUpperCase().equals("")){
		                    txtClaveSupervisorconfirma.setText("");
		                    txtClaveSupervisorconfirma.requestFocus();
			        		btnError.setText(	"<html> <FONT FACE="+"arial"+" SIZE=5 COLOR=RED>" +
									"		<CENTER><p> NECESITA LA CLAVE DEL MISMO SUPERVISOR >NO VACIO< </p></CENTER></FONT></html>"); 
		                    btnError.setVisible(true);		                    
		    			}else{
		    				Obj_Retiros_Cajeros validar_Supervisor_Guardar_Retiro = new Obj_Retiros_Cajeros().buscarSupervisor(txtClaveSupervisor.getText().toUpperCase().trim());
		    				
		    				  if( validar_Supervisor_Guardar_Retiro.getClave().equals(txtClaveSupervisorconfirma.getText().toUpperCase().trim()))
		    						{
		    					  if(Float.valueOf(txtRetiro.getText())>10000){
		  			        		btnError.setText(	"<html> <FONT FACE="+"arial"+" SIZE=5 COLOR=RED>" +
											"		<CENTER><p> EL MONTO MAYOR A RETIRAR DEBE DE SER DE:"+10000+"</p></CENTER></FONT></html>"); 
				                    btnError.setVisible(true);
		    					  }else{
		    					  folio_retiro=new Obj_Retiros_Cajeros().guardar(Establecimiento,folio_empleado,Integer.valueOf(txtfolio_Supervisor.getText()),Float.valueOf(txtRetiro.getText()),Asignacion)+"";
		    					  
		    					  if(folio_retiro !="Error en GuardarSQL"){
		    					  btnValidar_Retiro.setEnabled(false);
		    					  btnEditar_retiro.setEnabled(false);
		    					  txtClaveSupervisorconfirma.setEnabled(false);
		    					  btnImprimir.setEnabled(true);
		    					  btnSalir.setVisible(true);
		    					  btnSalir.setEnabled(true);
                                  btnSalir.requestFocus();
                                  
		    					 }else{
					                    txtClaveSupervisorconfirma.setText("");
					                    txtClaveSupervisorconfirma.requestFocus();
						        		btnError.setText(	"<html> <FONT FACE="+"arial"+" SIZE=5 COLOR=RED>" +
												"		<CENTER><p>ERROR INTERNO EN EL GUARDADO AVISAR A SISTEMAS</p></CENTER></FONT></html>"); 
					                    btnError.setVisible(true);	
		    					 }
		    					  }
		    					 
		    				   }else{
				                    txtClaveSupervisorconfirma.setText("");
				                    txtClaveSupervisorconfirma.requestFocus();
					        		btnError.setText(	"<html> <FONT FACE="+"arial"+" SIZE=5 COLOR=RED>" +
											"		<CENTER><p> NO COINCIDEN LAS CONTRASEÑAS </p></CENTER></FONT></html>"); 
				                    btnError.setVisible(true);	
		    				        }
		    			}
		    		}
		    		
		    		ActionListener pasar_a_validar_clave_supervisor = new ActionListener(){
		    			public void actionPerformed(ActionEvent e){
		    				lblConfirmacion.setVisible(true);
		    				txtRetiro.setEditable(false);
		    				txtClaveSupervisorconfirma.setEditable(true);
		    				txtClaveSupervisorconfirma.requestFocus();
		    				btnEditar_retiro.setVisible(true);
		    				btnEditar_retiro.setEnabled(true);
		    				btnImprimir.setVisible(true);
		    				btnImprimir.setEnabled(false);
		    				btnValidar_Retiro.setEnabled(false);
		    			}
		    		};
		    		
		    		ActionListener regresar_modificar_retiro = new ActionListener(){
		    			public void actionPerformed(ActionEvent e){
		    				lblConfirmacion.setVisible(true);
		    				txtRetiro.setEditable(true);
		    				txtRetiro.requestFocus();
		    				txtClaveSupervisorconfirma.setEditable(false);
		    				txtClaveSupervisorconfirma.setText("");
		    				btnEditar_retiro.setVisible(true);
		    				btnEditar_retiro.setEnabled(false);
		    				btnSalir.setVisible(false);
		    				btnImprimir.setVisible(false);
		    				btnError.setVisible(false);
		    				btnValidar_Retiro.setEnabled(true);
		    				btnEditar_retiro.setVisible(false);
		    			}
		    		};
		    		
				  	ActionListener salir = new ActionListener(){
						public void actionPerformed(ActionEvent e){
							btnImprimir.doClick();
							framesalir.dispose();
						    importe_retiros_guardados =0;
						    importe_nuevo_devuelto=0;
						    valor_a_retirar_deacuerdo_al_dia =0;
							cerrarhilo=false;
							Hilo_1_Minuto();

						}
					};
					
					ActionListener imprimir_retiro = new ActionListener(){
						public void actionPerformed(ActionEvent e){
						 Reporte_De_Retiros_Cajeros(folio_retiro);
						}
					};
		    	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////CATALOGO IMPRESION DE REIMPRESION DE RETIROS//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	    	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	  	public class Cat_Filtro_De_Retiros_Guardados extends JDialog {
			Container cont = getContentPane();
			JLayeredPane retiros_panel = new JLayeredPane();
			Connexion con = new Connexion();
			
			JPasswordField txtClaveSupervisor_reimpresion = new Componentes().textPassword(new JPasswordField(), "Clave", 30);
			
			DefaultTableModel modelo = new DefaultTableModel(0,2){
				public boolean isCellEditable(int fila, int columna){
					if(columna < 0)
						return true;
					return false;
				}
			};

			JTable tabla = new JTable(modelo);
			String folio_retiro_reimpresion ="";
			public Cat_Filtro_De_Retiros_Guardados()	{
				this.setIconImage(Toolkit.getDefaultToolkit().getImage("imagen/Filter-List-icon32.png"));
				this.setTitle("Filtro Reimpresion De Retiros");

				retiros_panel.setBorder(BorderFactory.createTitledBorder("Seleccione El Retiro Que Desea Imprimir"));
				retiros_panel.add(getPanelTabla()).setBounds(15,15,220,150);
				txtClaveSupervisor_reimpresion.addKeyListener(buscar_supervisor_reimpresion);
				
				agregar(tabla);
				cont.add(retiros_panel);
				
				this.setSize(255,205);
				this.setResizable(false);
				this.setLocationRelativeTo(null);
				this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			}
			
    		KeyListener buscar_supervisor_reimpresion = new KeyListener() {
    			@SuppressWarnings("deprecation")
				public void keyPressed(KeyEvent e) {	
    				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					Obj_Retiros_Cajeros datosSupervisor= new Obj_Retiros_Cajeros().buscarSupervisor(txtClaveSupervisor_reimpresion.getText().toUpperCase().trim());
	    			
	    			if(datosSupervisor.getExiste_supervisor().equals("NO EXISTE")){
	    				txtClaveSupervisor_reimpresion.setText("");
	    				txtClaveSupervisor_reimpresion.requestFocus();
						JOptionPane.showMessageDialog(null,"Se Requiere Clave De Un Supervisor","Mensaje",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
	    			}else{
	    				dispose();
	    			    Reporte_De_Retiros_Cajeros(folio_retiro_reimpresion);
	    			}
    				}
    			}
    			public void keyReleased(KeyEvent e) {}
    			public void keyTyped(KeyEvent e) {}
            };
    		
			private void agregar(final JTable tbl) {
		        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
			        public void mouseClicked(MouseEvent e) {
			        	if(e.getClickCount() == 2){
			        		int fila = tabla.getSelectedRow();
			    			folio_retiro_reimpresion = tabla.getValueAt(fila, 0).toString().trim();
			    			
						       while(tabla.getRowCount()>0){ modelo.removeRow(0);  }
						       retiros_panel.add(getPanelTabla()).setBounds(15,15,0,0);
						       retiros_panel.add(new JButton ("Pase El Gafete Del Supevisor:")).setBounds(16,16,218,20);
						       retiros_panel.add(txtClaveSupervisor_reimpresion).setBounds(16,35,218,20);
						       txtClaveSupervisor_reimpresion.requestFocus();
			        	}
			        }
		        });
		    }
			
		   	private JScrollPane getPanelTabla()	{		
				
				DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
				tcr.setHorizontalAlignment(SwingConstants.CENTER);
				tabla.getColumnModel().getColumn(0).setHeaderValue("Folio");
				tabla.getColumnModel().getColumn(0).setMaxWidth(97);
				tabla.getColumnModel().getColumn(0).setMinWidth(97);
				tabla.getColumnModel().getColumn(1).setHeaderValue("Fecha");
				tabla.getColumnModel().getColumn(1).setMaxWidth(120);
				tabla.getColumnModel().getColumn(1).setMinWidth(120);
		    	tabla.getTableHeader().setReorderingAllowed(false) ;
		    	tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
						
				Statement s;
				ResultSet rs;
				try {
					s = con.conexion().createStatement();
					rs = s.executeQuery("SELECT     folio_retiro,convert(varchar(15),fecha,103)+' '+convert(varchar(15),fecha,108) as fecha"
							+" FROM   tb_retiros_a_cajeros  WHERE    folio_cajero = "+txtFolio_empleado.getText().trim()+" and status_retiro_corte=1");
					String [] fila = new String[4];
					while (rs.next()) {	   fila[0] = rs.getString(1)+"  ";
					                       fila[1] = "   "+rs.getString(2);
					                       modelo.addRow(fila); 
					}	
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				 JScrollPane scrol = new JScrollPane(tabla);
				   
			    return scrol; 
			 }
			}
	  
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////TODO CATALOGO VALIDACION E IMPRESION DE SALDO TA//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	    	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	  	public class Cat_Validar_Clave_Supervisor extends JDialog{
			JPasswordField txtClaveSupervisor_validacion = new Componentes().textPassword(new JPasswordField(), "Clave", 30);
			JTextField txtcomprobar_saldoTA = new Componentes().text(new JCTextField(), "Teclee El Saldo TA de Nuevo", 30, "Int");
			String Supervisor ="";
			JButton btnReporte = new JButton("Imprimir",new ImageIcon("imagen/Print.png"));
			double venta=0;
			JLabel JblAvisosupervisor = new JLabel("");
			
	  		public Cat_Validar_Clave_Supervisor(){
	  			this.setSize(220, 160);
	  			this.setResizable(false);
	  			this.setLocationRelativeTo(null);
	  			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	  			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/plan-icono-5073-16.png"));
	  			this.setTitle("Guardado Saldo TA");
	  			Container cont = getContentPane();
	  			JLayeredPane panel = new JLayeredPane();
	  			this.setModal(true);
	  			this.addWindowListener(op_cerrar);
	  			
	  			panel.setBorder(BorderFactory.createTitledBorder( "  Teclee El Saldo De Nuevo Para Validar"));
	  			panel.add(txtcomprobar_saldoTA).setBounds(10, 20, 190, 20);
	  			panel.add(JblAvisosupervisor).setBounds(10,50,200,20);
	  			panel.add(txtClaveSupervisor_validacion).setBounds(10, 70, 190, 20);
	  			panel.add(btnReporte).setBounds(50, 100, 100, 20);;
	  			cont.add(panel);

	  			txtClaveSupervisor_validacion.addKeyListener(buscar_supervisor_existe);
	  			txtcomprobar_saldoTA.addActionListener(ValidarTA_Tecleado);
	  			btnReporte.addActionListener(imprimir_Reporte);
	  			txtClaveSupervisor_validacion.setEnabled(false);
	  			
	  			txtcomprobar_saldoTA.setEditable(true);
	  			btnReporte.setEnabled(false);
	  		}
	  		WindowListener op_cerrar = new WindowListener() {
	  			public void windowOpened(WindowEvent e) {}
	  			public void windowIconified(WindowEvent e) {}
	  			public void windowDeiconified(WindowEvent e) {}
	  			public void windowDeactivated(WindowEvent e) {}
	  			public void windowClosing(WindowEvent e) {
	  				Actualizar_ta_tira();
	  			}
	  			public void windowClosed(WindowEvent e) {}
	  			public void windowActivated(WindowEvent e) {}
	  		};
	  		
	  		ActionListener ValidarTA_Tecleado = new ActionListener(){
        		public void actionPerformed(ActionEvent e){
        		if(!txtcomprobar_saldoTA.getText().toString().trim().equals("")){	
    			if((Integer.valueOf(txtsaldoTA.getText().toString().trim())-Integer.valueOf(txtcomprobar_saldoTA.getText().toString().trim()))==0){
    						txtcomprobar_saldoTA.setEnabled(false);
    						txtClaveSupervisor_validacion.setEnabled(true);
    						txtClaveSupervisor_validacion.requestFocus();
    						JblAvisosupervisor.setText("      Pase El Gafete Del Supervisor");
    				 }else{
 						JOptionPane.showMessageDialog(null,"No Coinciden Las Cantidades Tecleadas de Saldo Vefique E Intente De Nuevo \n *1er Saldo Tecleado:"
    				                                     +Integer.valueOf(txtsaldoTA.getText().toString().trim())+"\n*2do Saldo Tecleado:"+Integer.valueOf(txtcomprobar_saldoTA.getText().toString().trim()),"Mensaje",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png")); 
    				 return;
    				 
        			}
        		}else{
						JOptionPane.showMessageDialog(null,"Necesitas Teclear De Nuevo El Valor Del Tiempo Aire","Mensaje",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png")); 

        			return;
        		}
        		}
        	};

	  		KeyListener buscar_supervisor_existe = new KeyListener() {
    			@SuppressWarnings("deprecation")
				public void keyPressed(KeyEvent e) {	
    				if(e.getKeyCode()==KeyEvent.VK_ENTER){
 					   Obj_Retiros_Cajeros datosSupervisor= new Obj_Retiros_Cajeros().buscarSupervisor(txtClaveSupervisor_validacion.getText().toUpperCase().trim());
	    			if(datosSupervisor.getExiste_supervisor().equals("NO EXISTE")){
	    				txtClaveSupervisor_validacion.setText("");
	    				txtClaveSupervisor_validacion.requestFocus();
						JOptionPane.showMessageDialog(null,"Se Requiere Clave De Un Supervisor","Mensaje",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
	    			}else{
	    				cargar_lista_de_asignaciones();
	    				if(new GuardarSQL().GuardarSaldo_TA(folio_empleado,datosSupervisor.getFolio_supervisor(),txtEstablecimiento.getText().toString().trim(),txtsaldoTA.getText().toString().trim(),venta,txtasignacion.getText().toString().trim())){
		    				txtClaveSupervisor_validacion.setEnabled(false);
		    				JOptionPane.showMessageDialog(null,"Se Guardo El >"+saldoinicialfinal+"< Exitosamente","Mensaje",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
	    					btnReporte.setEnabled(true);
	    					Supervisor=datosSupervisor.getNombre_Supervisor().toString();
	    					reporte (Supervisor);
	    					venta=0;
	    					saldoinicialfinal="";
	    				}
	    			}
    				}
    			}
    			public void keyReleased(KeyEvent e) {}
    			public void keyTyped(KeyEvent e) {}
            };
            
        	ActionListener imprimir_Reporte = new ActionListener(){
        		public void actionPerformed(ActionEvent e){
        			reporte (Supervisor);
        		}
        	};
        	
         public boolean cargar_lista_de_asignaciones(){
         		boolean registrado = false;
         		Connection con_scoi = new Connexion().conexion();
        		PreparedStatement pstmt_SCOI = null;
       			String consulta_ta= "select folio as ticket"
						+ "                 ,convert(varchar(15),fecha,103) +' '+convert(varchar(15),fecha,108)  as fecha"
						+ "                 ,isnull(total,0) as total"
						+ "                 ,'"+folio_empleado+"' as folio_empleado"
						+ "                 ,'"+txtasignacion.getText().trim()+"' as asignacion "
						+ "  from entysal with (nolock)   "
				        + " where  cod_prod='52401' and folio in(select folio from facremtick with (nolock) where folio_cajero='"+txtasignacion.getText().trim()+"') order by fecha";
       			
  				String query_ta_guardado = "exec sp_insert_venta_tiempo_aire_por_tira_de_saldo_de_cajero ?,?,?,?,?";
    			Statement s_IZAGAR;
        		ResultSet rs_IZAGAR;
        				try {
        						s_IZAGAR = new Connexion().conexion_IZAGAR().createStatement();
        						rs_IZAGAR = s_IZAGAR.executeQuery(consulta_ta);
        						while(rs_IZAGAR.next()){
        							    con_scoi.setAutoCommit(false);
        							    pstmt_SCOI =  con_scoi.prepareStatement(query_ta_guardado);
        							    pstmt_SCOI.setString(1, 	rs_IZAGAR.getString(1));
        							    pstmt_SCOI.setString(2, 	rs_IZAGAR.getString(2));
        							    pstmt_SCOI.setString(3,	    rs_IZAGAR.getString(3));
        							    pstmt_SCOI.setString(4,	    rs_IZAGAR.getString(4));
        							    pstmt_SCOI.setString(5, 	rs_IZAGAR.getString(5));
        							    pstmt_SCOI.executeUpdate();
        								registrado = true;
        								venta=venta+rs_IZAGAR.getDouble(3);
        						}
        				} catch (SQLException e1) {
        							e1.printStackTrace();
        							JOptionPane.showMessageDialog(null, "Error en la funcion cargar_lista_de_asignaciones  SQLException: "+e1.getMessage(), "Avisa al Administrador del sistema", JOptionPane.ERROR_MESSAGE);
        							registrado = false;
        				}
        			try {
        				con_scoi.commit();
        			} catch (SQLException e) {
        					e.printStackTrace();
        			}
        		return registrado;
        	}
         
    	    public void reporte (String nombre_supervisor) {
    					String basedatos="2.26";
    					String vista_previa_reporte="no";
    					int vista_previa_de_ventana=0;
    					String comando="exec sp_select_saldos_ta "+folio_empleado+",'"+txtpc.getText().toString().trim()+"','"+txtasignacion.getText().toString().trim()+"','"+nombre_supervisor+"','"+txtEstablecimiento.getText().toString().trim()+"'";
    					String reporte = "Obj_Reporte_De_Saldo_Tiempo_Aire_Capturado_Supervisor_Ticket.jrxml";
    					 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
    			};
        	
	  	}
        	
	  	public static void main(String [] arg){
			try{
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				new Cat_Retiros_A_Cajeros().setVisible(true);
			}catch(Exception e){	}
		}
	  	
		public class CapturarPedido extends Cat_Pedido_De_Monedas{
			
			public CapturarPedido(int folioEmp,String empleado, String status_pedido, String tipo_usuario){
				activarColumna = status_pedido;
				
//				pedirRecibir = true     --->   activar recibir pedido	
//				pedirRecibir = true     --->   activar realizar pedido
				boolean entregoMonedas = false;
				switch(tipo_usuario){
					case "CAJERA":	this.setTitle(status_pedido.equals("RECIBIDO")?"Recibir Pedido De Monedas ("+tipo_usuario+")":"Realizar Pedido De Monedas ("+tipo_usuario+")");break;
					case "CORTES":	this.setTitle("Surtir Pedido De Monedas ("+tipo_usuario+")");	break;
					default:		this.setTitle(status_pedido.equals("RECIBIDO")?"Recibir Pedido De Monedas ("+tipo_usuario+")":"Realizar Pedido De Monedas ("+tipo_usuario+")");break;//	recibir por (ENCARGADO / CAJERA)
				}
				
				switch(status_pedido){
					case "PEDIDO":		entregoMonedas=false;	columna = 2;	break;
					case "SURTIDO":		entregoMonedas=false;	columna = 4;	break;
					case "ENTREGADO":	entregoMonedas=true;	columna = 6;	break;
					case "RECIBIDO":	entregoMonedas=true;	columna = 8;	break;
//					default:			entregoMonedas=true;	break;
				}
				
//				System.out.println(entregoMonedas);
				cmbEntrega.setEnabled(entregoMonedas);
				
				Constructor();
				calcularTotales();
				
				this.lblEmpleado.setText("EMPLEADO:  "+folioEmp+"  "+empleado);
				agregar(tabla);
				tablaKey(status_pedido.equals("PEDIDO")?txtTotalPedido:( status_pedido.equals("SURTIDO")?txtTotalSurtido:( status_pedido.equals("ENTREGADO")?txtTotalEntregado:( txtTotalRecibido ) ) ));
			    guardar(btn_guardar, folioEmp, status_pedido, status_pedido.equals("PEDIDO")?txtTotalPedido:( status_pedido.equals("SURTIDO")?txtTotalSurtido:( status_pedido.equals("ENTREGADO")?txtTotalEntregado:( txtTotalRecibido ) )));
			    
			}
			
			private void agregar(final JTable tbl) {
		        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
			        public void mouseClicked(MouseEvent e) {
			        	fila= tbl.getSelectedRow();
			        	tabla.getSelectionModel().setSelectionInterval(fila, fila);
			        	tabla.setEnabled(true);
			        	tabla.editCellAt(fila, columna);
		    			Component aComp=tabla.getEditorComponent();
		    			aComp.requestFocus();
			        }
		        });
		    }
			
			private void guardar(final JButton btn,final int folioEmpleado, final String status_pedido, final JTextField txt) {
				btn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						if(activarColumna.equals("ENTREGADO") || activarColumna.equals("RECIBIDO")){
							if(cmbEntrega.getSelectedIndex()==0){
								JOptionPane.showMessageDialog(null, "Favor De Seleccionar Quien Le Entrego El Pedido","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
								return;
							}else{
								guardado(btn, folioEmpleado, status_pedido, txt);
							}
						}else{
							guardado(btn, folioEmpleado, status_pedido, txt);
						}
							ValidaPedido();
					}
				});
		    }
		}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////  	
	
		
	////EJEMPLO DE VECTOR
//		@SuppressWarnings({ "rawtypes", "unchecked" })
//		public Vector busqueda_datos_saldo() throws SQLException{
//			Vector fila = new Vector();
//			String query = "exec sp_select_saldos_ta "+txtFolio_empleado.getText().toString()+",'"+txtpc.getText().toString().trim()+"'";
//			Statement stmt = null;
//			try {
//				stmt = con.conexion().createStatement();
//			    ResultSet rs = stmt.executeQuery(query);
//				while(rs.next()){
//					fila.add(rs.getObject(1));
//					fila.add(rs.getObject(2));
//					fila.add(rs.getObject(3));
//					fila.add(rs.getObject(4));
//					fila.add(rs.getObject(5));
//					fila.add(rs.getObject(6));
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//				System.err.println("Error");
//				JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion Vector buscar_entosal  store procedure sp_select_entosal: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
//				return null;
//			}
//			finally{
//				 if (stmt != null) { stmt.close(); }
//			}
//			return fila;
//		}
////COMO SE USA EL VECTOR
//		Vector vector=new Vector();
//		try {
//			vector=busqueda_datos_saldo();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//			    + "            ,"+vector.get(0).toString()+" as saldo_inicial"
//				+ "            ,"+vector.get(1).toString()+" as total_traspaso"
//				+ "            ,"+vector.get(2).toString()+" as venta "
//				+ "            ,"+vector.get(3).toString()+" as saldo_final_de_movimientos"
//				+ "            ,"+vector.get(3).toString()+" as saldo_final_tecleado "
//				+ "            ,"+vector.get(5).toString()+" as diferiencia "

}