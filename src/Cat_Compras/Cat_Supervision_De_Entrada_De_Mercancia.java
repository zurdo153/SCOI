package Cat_Compras;

import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import com.toedter.calendar.JDateChooser;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Connexion;
import Obj_Renders.ColorCeldas;

@SuppressWarnings("serial")
public class Cat_Supervision_De_Entrada_De_Mercancia extends JFrame{
	
	Object[][] Matriz_pedidos_ctes ;
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	Connexion con = new Connexion();
	Runtime R = Runtime.getRuntime();
	
	JDateChooser fh_inicial = new JDateChooser();
	JDateChooser fh_final = new JDateChooser();
	
	DefaultTableModel modelo = new DefaultTableModel(null,new String[]{"Folio", "Cod Prob","Rezon Social","establecimiento","Folio Usuario","Usuario","Fecha","Nota","Status"}
			){
	     @SuppressWarnings("rawtypes")
		Class[] types = new Class[]{
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
                                    };
	     @SuppressWarnings({ "rawtypes", "unchecked" })
		public Class getColumnClass(int columnIndex) {
             return types[columnIndex];
         }
         public boolean isCellEditable(int fila, int columna){
        	 switch(columna){
        	 	case 0 : return false; 
        	 	case 1 : return false; 
        	 	case 2 : return false;
        	 	case 3 : return false;
        	 	case 4 : return false;
        	 	case 5 : return false;
        	 	case 6 : return false;
        	 	case 7 : return false;
        	 	case 8 : return false;
        	 } 				
 			return false;
 		}
	};
	
    JTable tabla = new JTable(modelo);
    JScrollPane scrollAsignado = new JScrollPane(tabla,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	@SuppressWarnings("rawtypes")
	private TableRowSorter trsfiltro;
	
	JCheckBox chbActivar_Avisos = new JCheckBox("Ventana De Avisos");
	
	JTextField txtFolio= new JTextField();
	JTextField txtFolioProveedor = new JTextField();
	JTextField txtRazon_Social = new JTextField();
	
	JButton btnBuscar = new JButton("ACTUALIZAR MANUAL",new ImageIcon("imagen/refrescar-volver-a-cargar-las-flechas-icono-4094-32.png"));;

	Border blackline, etched, raisedbevel, loweredbevel, empty;
	
    int cantidad_pedido_actual =0;
    int Existen_pedidos_nuevos=0;
    int SeActivoSonido=0;
    
    String FechaIn = "";
	String FechaFin = "";
    
	String mostrarAviso = "no";
	
    boolean cerrarhilo = false;
	
    
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Cat_Supervision_De_Entrada_De_Mercancia(){
		int ancho = Toolkit.getDefaultToolkit().getScreenSize().width;
		int alto = Toolkit.getDefaultToolkit().getScreenSize().height;
		
		setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds()); 
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setTitle("Consulta de Pedidos De Clientes");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/lista-icono-7220-32.png"));
		blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
		panel.setBorder(BorderFactory.createTitledBorder(blackline,"Estatus De Pedidos Clientes"));
		this.cont.add(panel);
		this.addWindowListener(op_cerrar);

		btnBuscar.setEnabled(true);
		btnBuscar.setToolTipText("<F5> Tecla Directa");
//		btnBuscar.addActionListener(Buscar_Cambios);
		
		chbActivar_Avisos.setSelected(true);
		
		trsfiltro = new TableRowSorter(modelo); 
		tabla.setRowSorter(trsfiltro);
		txtFolio.setToolTipText("Filtro Por Folio De Entrada");
		txtFolioProveedor.setToolTipText("Filtro Por Codigo De Proveedor");
		txtRazon_Social.setToolTipText("Filtro Por Razon Social");

		txtFolio.addKeyListener(opFiltroEstablecimiento);
		txtFolioProveedor.addKeyListener(opFiltroFolio);
		txtRazon_Social.addKeyListener(opFiltroFechaPedido);
		
		try {
			
			fh_inicial.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(cargar_fechas(1)));
			fh_final.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(cargar_fechas(0)));
			FechaIn = cargar_fechas(1);
			FechaFin = cargar_fechas(0);
			
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		buscarEntradas(FechaIn,FechaFin,"manual");
		
		cantidad_pedido_actual = tabla.getRowCount();
		
		PintarEstatusTabla(tabla,"Revision De Entrada De Mercancia",8);//tipo_de_tabla , columnas 0 
		Hilo_1_Minuto();
		
		int y = 20;
		panel.add(new JLabel("De: ")).setBounds(20,y,30,20);
		panel.add(fh_inicial).setBounds(50,y,150,20);
		panel.add(new JLabel("A: ")).setBounds(220,y,30,20);
		panel.add(fh_final).setBounds(250,y,150,20);
		
		
		panel.add(btnBuscar).setBounds(900,8,180,32);
		panel.add(txtFolio).setBounds(15,y+=25,100,20);
		panel.add(txtFolioProveedor).setBounds(115,y,80,20);
		panel.add(txtRazon_Social).setBounds(195,y,365,20);
		panel.add(chbActivar_Avisos).setBounds(560,y,150,20);
		panel.add(getPanelTabla()).setBounds(15,y+=20,ancho-30,alto-150);
             
		btnBuscar.addActionListener(Buscar_Cambios);
		
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
                        	  txtFolioProveedor.requestFocus();
                       }
                  });
	}
	//////////////////////////////////////////////////////////
	
	private JScrollPane getPanelTabla()	{	
		
	tabla.getTableHeader().setReorderingAllowed(false) ;
	tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	
	tabla.getColumnModel().getColumn(0).setMinWidth(100);
	tabla.getColumnModel().getColumn(0).setMaxWidth(100);
	tabla.getColumnModel().getColumn(1).setMinWidth(80);
	tabla.getColumnModel().getColumn(1).setMaxWidth(80);
	tabla.getColumnModel().getColumn(2).setMinWidth(365);
	tabla.getColumnModel().getColumn(2).setMaxWidth(365);
	tabla.getColumnModel().getColumn(3).setMinWidth(125);
	tabla.getColumnModel().getColumn(3).setMaxWidth(125);
	tabla.getColumnModel().getColumn(4).setMinWidth(80);
	tabla.getColumnModel().getColumn(4).setMaxWidth(80);
	tabla.getColumnModel().getColumn(5).setMinWidth(220);
	tabla.getColumnModel().getColumn(5).setMaxWidth(220);
	tabla.getColumnModel().getColumn(6).setMinWidth(150);
	tabla.getColumnModel().getColumn(6).setMaxWidth(150);
	
	tabla.getColumnModel().getColumn(7).setMinWidth(350);
	tabla.getColumnModel().getColumn(7).setMaxWidth(350);
	tabla.getColumnModel().getColumn(8).setMinWidth(80);
	tabla.getColumnModel().getColumn(8).setMaxWidth(80);

		JScrollPane scrol = new JScrollPane(tabla);
	    return scrol; 
	}
	
	public void buscarEntradas(String FechaInicial,String FechaFinal,String tipoDeBusqueda){
//		limpiar modelo
		modelo.setRowCount(0);
		
		Statement s;
		ResultSet rs;
		try {
			
			String query = "exec sp_Reperte_De_Entradas_En_Un_Periodo '"+FechaInicial+"','"+FechaFinal+"'";
			s = con.conexion_IZAGAR().createStatement();
			rs = s.executeQuery(query);
			
		    int Existen_pedidos_nuevos = getFilas(query);
		    
			while (rs.next())
			{ 
			   String [] fila = new String[9];
			   fila[0] = rs.getString(1).trim();
			   fila[1] = rs.getString(2).trim();
			   fila[2] = rs.getString(3).trim(); 
			   fila[3] = rs.getString(4).trim(); 
			   fila[4] = rs.getString(5).trim(); 
			   fila[5] = rs.getString(6).trim(); 
			   fila[6] = rs.getString(7).trim(); 
			   fila[7] = rs.getString(8).trim(); 
			   fila[8] = rs.getString(9).trim(); 

			   modelo.addRow(fila); 
			}
			System.out.println(query);
			if(tipoDeBusqueda.equals("hilo") && Existen_pedidos_nuevos > cantidad_pedido_actual){
		    	cantidad_pedido_actual=Existen_pedidos_nuevos;
		    	mostrarAviso = "si";
		    	
		    }else{
		    	cantidad_pedido_actual=Existen_pedidos_nuevos;
		    	mostrarAviso = "no";
		    }
			
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en Cat_Control_De_Facturas_Y_XML_De_Proveedores en la funcion getPanelTabla  SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public int getFilas(String qry){
		int filas=0;
		try {
			Statement s = con.conexion_IZAGAR().createStatement();
			ResultSet rs = s.executeQuery(qry);
			while(rs.next()){
				filas++;
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return filas;
	}
	
	KeyListener opFiltroEstablecimiento = new KeyListener(){
		@SuppressWarnings("unchecked")
		public void keyReleased(KeyEvent arg0) {
			trsfiltro.setRowFilter(RowFilter.regexFilter(txtFolio.getText(), 0));
			
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}	
	    };
	
	KeyListener opFiltroFolio = new KeyListener(){
		@SuppressWarnings("unchecked")
		public void keyReleased(KeyEvent arg0) {
			trsfiltro.setRowFilter(RowFilter.regexFilter(txtFolioProveedor.getText().toUpperCase().trim(), 1));
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}		
	    };
	   
	KeyListener opFiltroFechaPedido = new KeyListener(){
			@SuppressWarnings("unchecked")
			public void keyReleased(KeyEvent arg0) {
				trsfiltro.setRowFilter(RowFilter.regexFilter(txtRazon_Social.getText().toUpperCase().trim(), 2));
			}
			public void keyTyped(KeyEvent arg0) {}
			public void keyPressed(KeyEvent arg0) {}		
		    };
		  
	public String cargar_fechas(int dias){
		String date = null;
	    	try {
				date = new BuscarSQL().fecha(dias);
				} catch (SQLException e) {
					// catch block
					e.printStackTrace();
					}
		return date;
	};
		    
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
////DISPOSE MATA EL HILO Y DETIENE EL SONIDO SI SONO ALGUNA VEZ		    
 AudioClip sonido;
WindowListener op_cerrar = new WindowListener() {
				public void windowOpened(WindowEvent e) {}
				public void windowIconified(WindowEvent e) {}
				public void windowDeiconified(WindowEvent e) {}
				public void windowDeactivated(WindowEvent e) {}
				@SuppressWarnings({ "unchecked" })
				public void windowClosing(WindowEvent e) {
						trsfiltro.setRowFilter(RowFilter.regexFilter("", 0));
						trsfiltro.setRowFilter(RowFilter.regexFilter("", 1));
						trsfiltro.setRowFilter(RowFilter.regexFilter("", 2));
						
						txtFolio.setText("");
						txtFolioProveedor.setText("");
						txtRazon_Social.setText("");
						
					 if(SeActivoSonido!=0){
						 	sonido.stop();}
							cerrarhilo=true;
							dispose();
				}
				public void windowClosed(WindowEvent e) {}
				public void windowActivated(WindowEvent e) {}
			};
			
			
	////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////ACTUALIZAR

	ActionListener Buscar_Cambios = new ActionListener(){
		@SuppressWarnings({ })
		public void actionPerformed(ActionEvent e){
				try {
					
						if(fh_final.getDate().before(fh_inicial.getDate())){
							JOptionPane.showMessageDialog(null, "Las Fechas Se Encuentran Invertidas", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
							  return;
//							aviso fechas invertidas
						}else{
							FechaIn = new SimpleDateFormat("dd/MM/yyyy").format(fh_inicial.getDate());
							FechaFin = new SimpleDateFormat("dd/MM/yyyy").format(fh_final.getDate());
							
							buscarEntradas(FechaIn,FechaFin,"manual");
						}
						
				} catch (Exception e1) {
					System.out.println(e1.getMessage());
					JOptionPane.showMessageDialog(null, "Error en Cat_Consulta_De_Status_De_Pedidos_De_Clientes en la funcion Buscar_Cambios   SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			}
		}
	};
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public void MostrarAvisoEmergente(){
		try {
			
			if(fh_final.getDate().before(fh_inicial.getDate())){
				JOptionPane.showMessageDialog(null, "Las Fechas Se Encuentran Invertidas", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
				  return;
//				aviso fechas invertidas
			}else{
				
				if(mostrarAviso.equals("si")){
					  //////Limpiar	Filtros
							trsfiltro.setRowFilter(RowFilter.regexFilter("", 0));
							trsfiltro.setRowFilter(RowFilter.regexFilter("", 1));
							trsfiltro.setRowFilter(RowFilter.regexFilter("", 2));
							
							txtFolio.setText("");
							txtFolioProveedor.setText("");
							txtRazon_Social.setText("");
					         
					         txtFolioProveedor.requestFocus();
					         
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
					    	 // seresetea la variable para que no muestre aviso de nuevo asta que existan entradas nuevas
								mostrarAviso="no";
		    	}
			}
			
	
	} catch (Exception e1) {
		System.out.println(e1.getMessage());
		JOptionPane.showMessageDialog(null, "Error en Cat_Supervision_De_Entrada_De_Mercancia en la funcion MostrarAvisoEmergente   SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
}
	}
	
	/////////OBTIENE PEDIDOS PARA AVISAR QUE HAY PEDIDO NUEVO 
//   	public int ObtenerPedidoNuevo(){
//   		int pedidos =0;
//   		
//		String todos = ("select count(establecimientos.nombre) FROM pedcte " +
//						"left outer join establecimientos on establecimientos.cod_estab=pedcte.cod_estab " +
//						"where pedcte.fecha_elaboracion>convert(varchar(20),getdate(),103)+' 00:00:000' and pedcte.cod_estab in(20,15)");
//		Statement s;
//		ResultSet rs2;
//		
//		try {
//			s = new Connexion().conexion_IZAGAR().createStatement();
//			rs2 = s.executeQuery(todos);
//			
//			while(rs2.next()){
//				pedidos = rs2.getInt(1);
//			}
//		} catch (SQLException e1) {
//			e1.printStackTrace();
//			JOptionPane.showMessageDialog(null, "Error en Cat_Consulta_De_Status_De_Pedidos_De_Clientes  en la funcion [ ObtenerValoresPedClientes ]   SQLException:  "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
//		}
//	    return pedidos; 
//	}
   	
	/////////OBTIENE DATOS DESDE IZAGAR PARA LLENAR TABLA CON LOS NUEVOS CAMBIOS
//   	public Object[][] ObtenerValoresPedClientes(){
//   		
//		String todos = (" select establecimientos.nombre as establecimiento " +
//		        " ,pedcte.folio as pedido    " +
//				" ,convert(varchar(50),pedcte.fecha,103)+' '+convert(varchar(50),pedcte.fecha,108)as elaboracion_pedido " +
//				" ,isnull(convert(varchar(50),facremtick.fecha,103)+' '+convert(varchar(50),facremtick.fecha,108),'NO SURTIDO')as surtido_pedido " +
//				" ,isnull(DATEDIFF(minute,pedcte.fecha,facremtick.fecha),(datediff(minute,pedcte.fecha,getdate())))as Tiempo_Para_Surtido " +
//				" ,pedcte.notas " +
//				" ,isnull(cajeros.nombre,'NO SURTIDO') as cajero" +
//				" FROM pedcte" +
//				" left outer join facremtick on facremtick.folio_origen=pedcte.folio" +
//				" left outer join cajeros on cajeros.cajero=facremtick.cajero" +
//				" left outer join establecimientos on establecimientos.cod_estab=pedcte.cod_estab " +
//				" where pedcte.fecha_elaboracion>convert(varchar(20),getdate(),103)+' 00:00:000' and pedcte.cod_estab in(20,15) " +
//				" order by pedcte.cod_estab desc,pedcte.fecha asc ");
//		
//		Statement s;
//		ResultSet rs2;
//
//		try {
//			s = new Connexion().conexion_IZAGAR().createStatement();
//			rs2 = s.executeQuery(todos);
//			Matriz_pedidos_ctes = new Object[LlenadoFilasPedCte(todos)][7];
//			int i=0;
//			while(rs2.next()){
//				Matriz_pedidos_ctes[i][0] = "   "+rs2.getString(1).trim();
//				Matriz_pedidos_ctes[i][1] = "   "+rs2.getString(2).trim();
//				Matriz_pedidos_ctes[i][2] = "   "+rs2.getString(3).trim();
//				Matriz_pedidos_ctes[i][3] = "   "+rs2.getString(4).trim();
//				Matriz_pedidos_ctes[i][4] = "   "+rs2.getString(5).trim();
//				Matriz_pedidos_ctes[i][5] = "   "+rs2.getString(6).trim();
//				Matriz_pedidos_ctes[i][6] = "   "+rs2.getString(7).trim();
//								i++;
//			}
//		} catch (SQLException e1) {
//			e1.printStackTrace();
//			JOptionPane.showMessageDialog(null, "Error en Cat_Consulta_De_Status_De_Pedidos_De_Clientes  en la funcion [ ObtenerValoresPedClientes ]   SQLException:  "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
//		}
//	    return Matriz_pedidos_ctes; 
//	}
//  public int LlenadoFilasPedCte(String qry){
//		int filas=0;
//		Statement stmt = null;
//		try {stmt = new Connexion().conexion_IZAGAR().createStatement();
//			ResultSet rs2 = stmt.executeQuery(qry);
//			while(rs2.next()){filas++;}
//		} catch (SQLException e1) {	e1.printStackTrace();}
//		return filas;
//	}
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
		    						if(reconsultar==600)////cambiar a 600 segundos = 10 min
		    						{
		    						   reconsultar=0;
		    						   
		    						   FechaIn = new SimpleDateFormat("dd/MM/yyyy").format(fh_inicial.getDate());
		    						   FechaFin = new SimpleDateFormat("dd/MM/yyyy").format(fh_final.getDate());
									
									
									   buscarEntradas(FechaIn,FechaFin,"hilo");
									   MostrarAvisoEmergente();
//				    				   btnBuscar.doClick();	
		    						   
		    						   

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
		    		
		    		JLabel lblAviso = new JLabel();
		    		String fileFoto = System.getProperty("user.dir")+"/imagen/avisos/entradaDeMercancia.png";
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
		    			frame.setSize(500,400);
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
	  	
//	  	pintado De tabla
		public void PintarEstatusTabla(final JTable tb, String tipo_de_tabla, int columnas){
			//se crea instancia a clase FormatoTable y se indica columna patron
	        ColorCeldas ft = new ColorCeldas(tipo_de_tabla,columnas);
	        tb.setDefaultRenderer (Object.class, ft );
		}
		    	
///////////////////////////////////////////////////////////////////////////////////		    	
		    
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Supervision_De_Entrada_De_Mercancia().setVisible(true);
		}catch(Exception e){	}
	}
}