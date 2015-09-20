package Cat_Lista_de_Raya;
import java.awt.AWTException;
import java.awt.Container;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import Conexiones_SQL.ActualizarSQL;
import Conexiones_SQL.Connexion;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.Componentes;
import Obj_Principal.Obj_Filtro_Dinamico;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Consulta_Borrado_Y_Cancelacion_De_Tickets_De_Fuente_De_Sodas_Sin_Cobro extends JFrame {
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	private JTextField txtfolio_empleado = new Componentes().text(new JTextField(), "Folio Del Empleado", 15, "Int");
	private JTextField txtnombre_empleado = new Componentes().text(new JTextField(), "Nombre Del Empleado", 100, "String");
	private JTextField txtstatus_empleado = new Componentes().text(new JTextField(), "Status Del Empleado", 100, "String");
	private JTextField txtestablecimiento   = new Componentes().text(new JTextField(), "Establecimiento", 80, "String");
	private JTextField txtticket = new Componentes().text(new JTextField(), "Ticket", 15, "String");
	private JTextField txtimporte = new Componentes().text(new JTextField(), "Importe", 15, "String");
	private JTextField txtfecha_ticket    = new Componentes().text(new JTextField(), "Fecha Ticket", 15, "String");
	private JTextField txtstatus_ticket    = new Componentes().text(new JTextField(), "Estatus Ticket", 25, "String");
	private JTextField txtcorte   = new Componentes().text(new JTextField(), "Folio Corte", 25, "String");
	private JTextField txtfecha_corte    = new Componentes().text(new JTextField(), "Fecha Corte", 25, "String");
	private JTextField txtcajero    = new Componentes().text(new JTextField(), "Cajero", 100, "String");
	private JTextField txtcapturado_auxf   = new Componentes().text(new JTextField(), "Fecha En Que Capturo Finanzas", 100, "String");
	private JTextField txtcapturado_rh   = new Componentes().text(new JTextField(), "Fecha En Que Capturo Desarrollo Humano", 100, "String");
	private JTextField txtLista_raya   = new Componentes().text(new JTextField(), "Lista De Raya En Que Se Capturo", 100, "String");
	private JTextArea  txtAObservacion    = new Componentes().textArea(new JTextArea(), "Observacion Para La Cancelacion", 200);
	private JTextField txtUsuario_Cancelo    = new Componentes().text(new JTextField(), "Usuario Cancelo", 100, "String");
	
	Border blackline, etched, raisedbevel, loweredbevel, empty;
	
	private JButton btnCancelar = new JButton("Cancelar",new ImageIcon("imagen/cancelar-icono-4961-16.png"));
	private JButton btnBorrarLista_Raya = new JButton("Borrar",new ImageIcon("imagen/borrar-lapiz-icono-7503-16.png"));
	private JButton btnDeshacer = new JButton("Deshacer",new ImageIcon("imagen/deshacer16.png"));
	
    JButton btnfiltroEmpleado =new JButton("",new ImageIcon ("imagen/Filter-List-icon16.png"));
	
	public Cat_Consulta_Borrado_Y_Cancelacion_De_Tickets_De_Fuente_De_Sodas_Sin_Cobro(){
		setSize(370,550);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Cancelacion De Tickets De Fuente De Sodas");
		setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/listaRoja.png"));
		panel.setBorder(BorderFactory.createTitledBorder(""));
		this.txtAObservacion.setBorder(BorderFactory.createTitledBorder(blackline));
		txtAObservacion.setLineWrap(true); 
		txtAObservacion.setWrapStyleWord(true);
		
		int x=10,y=15, d=78,width=260;
		
		panel.add(btnDeshacer).setBounds(x+242,y,100,20);
		
		panel.add(new JLabel("Folio Empleado:")).setBounds(x,y+=25,100,20);		
		panel.add(txtfolio_empleado).setBounds(x+d,y,width-20,20);
        panel.add(btnfiltroEmpleado ).setBounds(x+width+60, y,20,20);;
		
		panel.add(new JLabel("Nombre:")).setBounds(x,y+=25,100,20);		
		panel.add(txtnombre_empleado).setBounds(x+d,y,width,20);
		
		panel.add(new JLabel("Estatus Emple.:")).setBounds(x,y+=25,100,20);		
		panel.add(txtstatus_empleado).setBounds(x+d,y,width,20);
		
		panel.add(new JLabel("Establecimiento:")).setBounds(x,y+=25,width,20);		
		panel.add(txtestablecimiento).setBounds(x+d,y,width,20);
		
		panel.add(new JLabel("Ticket:")).setBounds(x,y+=25,100,20);		
		panel.add(txtticket).setBounds(x+d,y,width,20);
		
		panel.add(new JLabel("Importe:")).setBounds(x,y+=25,100,20);		
		panel.add(txtimporte).setBounds(x+d,y,width,20);
		
		panel.add(new JLabel("Fecha Ticket:")).setBounds(x,y+=25,100,20);		
		panel.add(txtfecha_ticket).setBounds(x+d,y,width,20);
		
		panel.add(new JLabel("Estatus Ticket:")).setBounds(x,y+=25,100,20);		
		panel.add(txtstatus_ticket).setBounds(x+d,y,width,20);
		
		panel.add(new JLabel("Folio Corte:")).setBounds(x,y+=25,120,20);		
		panel.add(txtcorte).setBounds(x+d,y,width,20);
		
		panel.add(new JLabel("Fecha Corte:")).setBounds(x,y+=25,120,20);		
		panel.add(txtfecha_corte).setBounds(x+d,y,width,20);
		
		panel.add(new JLabel("Cajero:")).setBounds(x,y+=25,120,20);		
		panel.add(txtcajero).setBounds(x+d,y,width,20);
		
		panel.add(new JLabel("Fecha Capturo AuxF:")).setBounds(x,y+=25,120,20);		
		panel.add(txtcapturado_auxf).setBounds(x+d+24,y,width-24,20);

		panel.add(new JLabel("Fecha Capturo D.H.:")).setBounds(x,y+=25,120,20);		
		panel.add(txtcapturado_rh).setBounds(x+d+24,y,width-24,20);
		
		panel.add(new JLabel("Lista Raya Se Cobró:")).setBounds(x,y+=25,120,20);		
		panel.add(txtLista_raya).setBounds(x+d+24,y,width-24,20);
		
		panel.add(new JLabel("Observacion:")).setBounds(x,y+=25,100,20);		
		panel.add(txtAObservacion).setBounds(x+d,y,width,60);
		
		panel.add(new JLabel("Usuario Cancelo:")).setBounds(x,y+=65,120,20);		
		panel.add(txtUsuario_Cancelo).setBounds(x+d,y,width,20);

		panel.add(btnBorrarLista_Raya).setBounds(x,y+=30,100,20);
		
		panel.add(btnCancelar).setBounds(x+242,y,100,20);
		
		cont.add(panel);
		
		txtnombre_empleado.setEditable(false);
		txtstatus_empleado.setEditable(false);
		txtestablecimiento.setEditable(false);
		txtticket.setEditable(false);
		txtimporte.setEditable(false);
		txtfecha_ticket.setEditable(false);
		txtstatus_ticket.setEditable(false);
		txtcorte.setEditable(false);
		txtfecha_corte.setEditable(false);
		txtcajero.setEditable(false);
		txtAObservacion.setEditable(false);
		txtUsuario_Cancelo.setEditable(false);
		txtcapturado_rh.setEditable(false);
		txtcapturado_auxf.setEditable(false);
		txtLista_raya.setEditable(false);
		txtfolio_empleado.addKeyListener(opbuscar_datos_del_empleado); 
		txtticket.addKeyListener(opbuscar_datos_del_ticket);

		limpiar();

		btnDeshacer.addActionListener(opdeshacer);
		btnCancelar.addActionListener(opCancelar);
		btnfiltroEmpleado.addActionListener(opfiltro);
		btnBorrarLista_Raya.addActionListener(opBorrar);
		
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened( WindowEvent e ){
           	 txtfolio_empleado.requestFocus();
            }
         });
		  ///deshacer con escape
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
        getRootPane().getActionMap().put("escape", new AbstractAction(){
            public void actionPerformed(ActionEvent e)
            {                 	    btnDeshacer.doClick();
          	    }
        });
	}
    public void limpiar(){
    	txtfolio_empleado.setText("");
    	txtnombre_empleado.setText("");
		txtstatus_empleado.setText("");
		txtestablecimiento.setText("");
		txtUsuario_Cancelo.setText("");
		txtticket.setText("");
		txtimporte.setText("");
		txtfecha_ticket.setText("");
		txtstatus_ticket.setText("");
		txtcorte.setText("");
		txtfecha_corte.setText("");
		txtcajero.setText("");
		txtAObservacion.setText("");
		txtcapturado_rh.setText("");
        txtcapturado_auxf.setText("");
        txtLista_raya.setText("");
		txtfolio_empleado.setEditable(true);
		txtticket.setEditable(false);
		btnCancelar.setEnabled(false);
		btnBorrarLista_Raya.setEnabled(false);
		txtfolio_empleado.requestFocus();
		txtAObservacion.setEditable(false);

     };
                  KeyListener opbuscar_datos_del_empleado = new KeyListener(){
					public void keyReleased(KeyEvent e1) {
					}
					public void keyTyped(KeyEvent e1) {
					}
					public void keyPressed(KeyEvent e1) {
						if(e1.getKeyCode()==KeyEvent.VK_ENTER){
							if(txtfolio_empleado.getText().equals("")){
					   			JOptionPane.showMessageDialog(null,"Es Necesario Teclear Un Numero De Empleado Para Poder Hacer La Busqueda","Aviso!", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
							return;
							}
							
							if(existe_mov_enfuente_de_sodas(1)){
								txtfolio_empleado.setEditable(false);
								txtticket.setEditable(true);
								txtticket.requestFocus();
								buscar_datos(1);
							}else{
					   			JOptionPane.showMessageDialog(null,"El Empleado No Cuenta Con Tickets De Fuente De Sodas","Aviso!", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
					   			limpiar();
							}
						   }    	   
					    }
				    };
				    
	              KeyListener opbuscar_datos_del_ticket = new KeyListener(){
	  					public void keyReleased(KeyEvent e1) {
	  					}
	  					public void keyTyped(KeyEvent e1) {
	  					}
	  					public void keyPressed(KeyEvent e1) {
	  						if(e1.getKeyCode()==KeyEvent.VK_ENTER){

								if(txtticket.getText().equals("")){
						   			JOptionPane.showMessageDialog(null,"Es Necesario Teclear Un Numero De Ticket Para Poder Hacer La Busqueda","Aviso!", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
						   			return;
								}
	  							if(existe_mov_enfuente_de_sodas(2)){
	  								buscar_datos(2);
	  							}else{
	  					   			JOptionPane.showMessageDialog(null,"El Ticket Tecleado: "+txtticket.getText().toString().trim()+" No Existe","Aviso!", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
	  							}
	  						   }    	   
	  					    }
	  				    };
				    
	  				public void enterauto(){
	  						Robot robot;
	  						try {
	  				            robot = new Robot();
	  				            robot.keyPress(KeyEvent.VK_ENTER);
	  				            robot.keyRelease(KeyEvent.VK_ENTER);
	  				        } catch (AWTException e) {
	  				            e.printStackTrace();
	  				        }
	  				     };
				    
					public boolean existe_mov_enfuente_de_sodas(int dato_a_buscar){
						String query ="";
						if(dato_a_buscar==1){	
						 query ="IF(SELECT COUNT(folio_empleado) FROM tb_captura_fuente_sodas  where  folio_empleado="+txtfolio_empleado.getText().toString()+")>0 "+
								      "  SELECT 'true' ELSE SELECT 'false' ";
						}
						if(dato_a_buscar==2){	
							 query ="IF(SELECT COUNT(ticket) FROM tb_captura_fuente_sodas  where folio_empleado="+txtfolio_empleado.getText().toString()+" and ticket='"+txtticket.getText().toString()+"')=1 "+
									      "  SELECT 'true' ELSE SELECT 'false' ";
							}
						
						
						boolean existe = false;
						try { Connexion con = new Connexion();
							  Statement s = con.conexion().createStatement();
							  ResultSet rs = s.executeQuery(query);
							while(rs.next()){
							    	existe = rs.getBoolean(1);
							      }
						} catch (SQLException e1) {
							e1.printStackTrace();
							JOptionPane.showMessageDialog(null, "Error en la funcion existe_mov_enfuente_de_sodas \n SQLException: "+e1.getMessage()+query, "Avisa al Administrador del Sistema", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
						}
				      return existe;
					}
					
					
	             public void buscar_datos(int dato_a_buscar){
						String query ="";
								if(dato_a_buscar==1){								
									query=	"select tb_empleado.folio,tb_empleado.nombre+' '+tb_empleado.ap_paterno+' '+tb_empleado.ap_materno as nombre_completo "
										+ "                   ,case when(tb_empleado.status)=1 then 'VIGENTE'"
										+ "                         when(tb_empleado.status)=2 then 'VACACIONES'"
										+ "                         when(tb_empleado.status)=3 then 'INCAPACIDAD'"
										+ "                         when(tb_empleado.status)=4 then 'BAJA'"
										+ "                         when(tb_empleado.status)=5 then 'NO CONTRATABLE' end as estatus_empleado"
										+ "             	 ,tb_establecimiento.nombre as establecimiento"
										+ "    from tb_empleado inner join tb_establecimiento on tb_establecimiento.folio=tb_empleado.establecimiento_id where tb_empleado.folio="+txtfolio_empleado.getText().toString();
								}
                               if(dato_a_buscar==2){
                            	    query= " select tb_captura_fuente_sodas.ticket,tb_captura_fuente_sodas.importe,convert (varchar(15),tb_captura_fuente_sodas.fecha,103)as fecha_del_ticket,case when tb_captura_fuente_sodas.status=1 then 'VIGENTE' when tb_captura_fuente_sodas.status=0 then 'COBRADO' when tb_captura_fuente_sodas.status=3 then 'CANCELADO' end "
                            			  +" ,cajero.nombre+' '+cajero.ap_paterno+' '+cajero.ap_materno as cajero " 
                                      	  +" ,tb_captura_fuente_sodas.folio_corte  "
                            			  +" ,isnull(convert(varchar(15),tb_alimentacion_de_cortes.fecha_de_corte,103),'NO TIENE CORTE')as fecha_corte "
                            			  + ",case when fecha_cancelacion='01/01/1900' then '' else tb_captura_fuente_sodas.observacion_cancelacion+' FECHA CANCELACION:' +convert(varchar(15),fecha_cancelacion,103) end as observacion"
                            			  + ",isnull(usuario_cancelo.nombre+' '+usuario_cancelo.ap_paterno+' '+usuario_cancelo.ap_materno,'') as usuario_cancelo "
                            			  +",isnull(convert(varchar(20),tb_fuente_sodas_auxf.Fecha_Captura,103),'') as fecha_captura_aux_f  "
										  +",isnull(convert(varchar(20),tb_fuente_sodas_rh.fecha_captura,103),'') as fecha_captura_rh  "
										  +",isnull(tb_captura_fuente_sodas.lista_raya_cobrado,'')as lista_raya  "                                 
										                 	  +"  from tb_captura_fuente_sodas " 
                            			  +" inner join tb_empleado as cajero on cajero.folio=tb_captura_fuente_sodas.folio_usuario "
                            			  +" left outer join tb_empleado as usuario_cancelo on usuario_cancelo.folio=tb_captura_fuente_sodas.usuario_cancelo "
                            			  +" left outer join tb_alimentacion_de_cortes on tb_alimentacion_de_cortes.folio_corte=tb_captura_fuente_sodas.folio_corte 	"
                            			  +" left outer join tb_fuente_sodas_auxf on tb_captura_fuente_sodas.ticket=tb_fuente_sodas_auxf.ticket"
                            			  +" left outer join tb_fuente_sodas_rh on tb_captura_fuente_sodas.ticket=tb_fuente_sodas_rh.ticket"
                            			  +"		where tb_captura_fuente_sodas.folio_empleado="+txtfolio_empleado.getText().toString()+" and tb_captura_fuente_sodas.ticket='"+txtticket.getText().toString()+"'";  
                               }																
								
							try { Connexion con = new Connexion();
							  Statement s = con.conexion().createStatement();
							  ResultSet rs = s.executeQuery(query);
							  if(dato_a_buscar==1){
									while(rs.next()){
									 	    txtnombre_empleado.setText(rs.getString(2).toString());
									 	    txtstatus_empleado.setText(rs.getString(3).toString());
									 	    txtestablecimiento.setText(rs.getString(4).toString());
									      }
							  }
							  
							  if(dato_a_buscar==2){
									while(rs.next()){
										    txtticket.setText(rs.getString(1).toString());
									 	    txtimporte.setText(rs.getString(2).toString());
									 	    txtfecha_ticket.setText(rs.getString(3).toString());
									 	    txtstatus_ticket.setText(rs.getString(4).toString());
									 	    txtcajero.setText(rs.getString(5).toString());
									 	    txtcorte.setText(rs.getString(6).toString());
									 	    txtfecha_corte.setText(rs.getString(7).toString());
									 	    txtAObservacion.setText(rs.getString(8).toString());
									 	    txtUsuario_Cancelo.setText(rs.getString(9).toString());
									 	    txtcapturado_auxf.setText(rs.getString(10).toString());
									 	    txtcapturado_rh.setText(rs.getString(11).toString());
									 	    txtLista_raya.setText(rs.getString(12).toString());
									      }
									
									if(txtstatus_ticket.getText().toString().trim().equals("VIGENTE")){
										if(txtcapturado_auxf.getText().toString().equals("")){
										
										btnCancelar.setEnabled(true);
										txtticket.setEditable(false);
										txtAObservacion.setEditable(true);
										txtAObservacion.requestFocus();
										}else{
											JOptionPane.showMessageDialog(null, "El ticket Tiene Estatus de "+txtstatus_ticket.getText().toString().trim()+"\nPero Ya Fue Capturado Para Su Cobro En Lista De Raya Actual \n Necesita Eliminarse De La Lista De Raya Para Poder Cancelarlo", "Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
											btnCancelar.setEnabled(false);
											txtAObservacion.setEditable(false);
											txtticket.setEditable(false);
											btnBorrarLista_Raya.setEnabled(true);
										}
										
									}else{
										JOptionPane.showMessageDialog(null, "El ticket Tiene Estatus de "+txtstatus_ticket.getText().toString().trim()+"\n No se Puede Cancelar ", "Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
										btnCancelar.setEnabled(false);
										txtAObservacion.setEditable(false);
									}
							  }
						   } catch (SQLException e1) {
							e1.printStackTrace();
							JOptionPane.showMessageDialog(null, "Error en la funcion buscar_datos \n SQLException: "+e1.getMessage(), "Avisa al Administrador del Sistema", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
						}
					}
					
			         ActionListener opdeshacer = new ActionListener(){
			 			   public void actionPerformed(ActionEvent arg0) {
			 				   limpiar();
					   };
					 };
					 

				  ActionListener opCancelar = new ActionListener(){
					   public void actionPerformed(ActionEvent arg0) {
						   if(new ActualizarSQL().cancelar_ticket_de_fuente_de_sodas(txtfolio_empleado.getText().toString(),txtticket.getText().toString(),txtAObservacion.getText().toString().toUpperCase().trim())){
							JOptionPane.showMessageDialog(null, "El Registro Se Cancelo Correctamente", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
							 limpiar();
						   }else{
								JOptionPane.showMessageDialog(null, "Error en la funcion opCancelar \n al intentar cancelar el ticket:"+txtticket.getText().toString(), "Avisa al Administrador del Sistema", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
						  }	 
					      }
				        };
				        
				        
				  ActionListener opBorrar = new ActionListener(){
			   	       public void actionPerformed(ActionEvent arg0) {
						   if(new ActualizarSQL().borrarr_ticket_de_fuente_de_sodas_capturado_porauxd_o_dh(txtfolio_empleado.getText().toString(),txtticket.getText().toString())){
									JOptionPane.showMessageDialog(null, ">>El Registro Se Borro Correctamente \n>>Se Desautorizo la Fuente de Sodas de La Lista De Raya", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
									 btnDeshacer.doClick();
								   }else{
										JOptionPane.showMessageDialog(null, "Error en la funcion opCancelar \n al intentar cancelar el ticket:"+txtticket.getText().toString(), "Avisa al Administrador del Sistema", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
								  }	 
							      }
						        };
						        
				        
			      ActionListener opfiltro = new ActionListener(){
					   public void actionPerformed(ActionEvent arg0) {
						   new Cat_Seleccion_De_Usuario().setVisible(true);
					      }
			      };
			      
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////				        
//////////////////////////filtro de Empleados con usuario en SCOI y con status vigente////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

			      public class Cat_Seleccion_De_Usuario extends JDialog {
							Container cont = getContentPane();
							JLayeredPane campo = new JLayeredPane();
							DefaultTableModel model = new DefaultTableModel(0,3){
								public boolean isCellEditable(int fila, int columna){
									if(columna < 0)
										return true;
									return false;
								}
							};
							
							JTable tabla = new JTable(model);
							@SuppressWarnings("rawtypes")
						private TableRowSorter trsfiltro;
							
						JTextField txtFolioFiltroEmpleado = new JTextField();
						JTextField txtNombre_Completo = new JTextField();
						
						String establecimientos[] = new Obj_Establecimiento().Combo_Establecimiento();
						@SuppressWarnings({ "rawtypes", "unchecked" })
						JComboBox cmbEstablecimientos = new JComboBox(establecimientos);
						
						@SuppressWarnings({ "rawtypes", "unchecked" })
						public Cat_Seleccion_De_Usuario(){
							this.setSize(490,650);
							this.setResizable(false);
							this.setLocationRelativeTo(null);
							this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
							this.setModal(true);
							
							this.setIconImage(Toolkit.getDefaultToolkit().getImage("imagen/usuario-busquedaicono-4661-64.png"));
							this.setTitle("Filtro de Empleados");
							campo.setBorder(BorderFactory.createTitledBorder("Filtro De Empleado"));

							campo.add(getPanelTabla()).setBounds(15,42,450,565);
							campo.add(txtFolioFiltroEmpleado).setBounds(15,20,48,20);
							campo.add(txtNombre_Completo).setBounds(64,20,229,20);
							campo.add(cmbEstablecimientos).setBounds(295,20, 148, 20);
							
							trsfiltro = new TableRowSorter(model); 
							tabla.setRowSorter(trsfiltro);  
							agregar(tabla);
							cont.add(campo);
							txtFolioFiltroEmpleado.addKeyListener(opFiltroFolio);
							txtNombre_Completo.addKeyListener(opFiltroNombre);
							cmbEstablecimientos.addActionListener(opFiltro);
							tabla.addKeyListener(seleccionEmpleadoconteclado);
							
						    this.addWindowListener(new WindowAdapter() {
						            public void windowOpened( WindowEvent e ){ 	txtNombre_Completo.requestFocus();       }
						    });
						}
						
						private void agregar(final JTable tbl) {
						    tbl.addMouseListener(new java.awt.event.MouseAdapter() {
						        public void mouseClicked(MouseEvent e) {
						        	if(e.getClickCount() == 2){
						    			int fila = tabla.getSelectedRow();
						    			Object folio =  tabla.getValueAt(fila, 0).toString().trim();
						    			dispose();
						    			txtfolio_empleado.setText(folio+"");
		                                txtfolio_empleado.requestFocus(); 								
										enterauto();
						        	}
						        }
						    });
						}
						
						KeyListener seleccionEmpleadoconteclado = new KeyListener() {
							@SuppressWarnings("static-access")
							@Override
							public void keyTyped(KeyEvent e) {
								char caracter = e.getKeyChar();

								if(caracter==e.VK_ENTER){
								int fila=tabla.getSelectedRow()-1;
								String folio = tabla.getValueAt(fila,0).toString().trim();
									
								txtfolio_empleado.setText(folio);
                                txtfolio_empleado.requestFocus(); 								
								enterauto();
								dispose();
								}
							}
							@Override
							public void keyPressed(KeyEvent e){}
							@Override
							public void keyReleased(KeyEvent e){}
						};
						
						KeyListener opFiltroFolio = new KeyListener(){
							@SuppressWarnings("unchecked")
							public void keyReleased(KeyEvent arg0) {
								trsfiltro.setRowFilter(RowFilter.regexFilter(txtFolioFiltroEmpleado.getText(), 0));
							}
							public void keyTyped(KeyEvent arg0) {
								char caracter = arg0.getKeyChar();
								if(((caracter < '0') ||
									(caracter > '9')) &&
								    (caracter != KeyEvent.VK_BACK_SPACE)){
									arg0.consume(); 
								}	
							}
							public void keyPressed(KeyEvent arg0) {}		
						};
						
						KeyListener opFiltroNombre = new KeyListener(){
							public void keyReleased(KeyEvent arg0) {
								new Obj_Filtro_Dinamico(tabla,"Nombre Completo", txtNombre_Completo.getText().toUpperCase(),"Establecimiento",cmbEstablecimientos.getSelectedItem()+"", "", "", "", "");
							}
							public void keyTyped(KeyEvent arg0) {}
							public void keyPressed(KeyEvent arg0) {}		
						};
						
						ActionListener opFiltro = new ActionListener(){
							public void actionPerformed(ActionEvent arg0){
								new Obj_Filtro_Dinamico(tabla,"Nombre Completo", txtNombre_Completo.getText().toUpperCase(),"Establecimiento",cmbEstablecimientos.getSelectedItem()+"", "", "", "", "");
							}
						};
						
						private JScrollPane getPanelTabla()	{	
							tabla.getTableHeader().setReorderingAllowed(false) ;
							tabla.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
							tabla.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
							tabla.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));

							tabla.getColumnModel().getColumn(0).setHeaderValue("Folio");
							tabla.getColumnModel().getColumn(0).setMaxWidth(50);
							tabla.getColumnModel().getColumn(0).setMinWidth(50);
							tabla.getColumnModel().getColumn(1).setHeaderValue("Nombre Completo");
							tabla.getColumnModel().getColumn(1).setMaxWidth(230);
							tabla.getColumnModel().getColumn(1).setMinWidth(230);
							tabla.getColumnModel().getColumn(2).setHeaderValue("Establecimiento");
							tabla.getColumnModel().getColumn(2).setMaxWidth(150);
							tabla.getColumnModel().getColumn(2).setMinWidth(150);
							
							Statement s;
							ResultSet rs;
							try {
								s = new Connexion().conexion().createStatement();
								rs = s.executeQuery("exec sp_select_usuarios_scoi");
								while (rs.next())
								{ 
								   String [] fila = new String[3];
								   fila[0] = rs.getString(1)+"  ";
								   fila[1] = "   "+rs.getString(2);
								   fila[2] = "   "+rs.getString(3).trim();
								   model.addRow(fila); 
								}	
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
							 JScrollPane scrol = new JScrollPane(tabla);
						    return scrol; 
						}
				}
	
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Consulta_Borrado_Y_Cancelacion_De_Tickets_De_Fuente_De_Sodas_Sin_Cobro().setVisible(true);
		}catch(Exception e){	}
	}

}
