package Cat_Checador;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

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
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import com.digitalpersona.onetouch.DPFPDataPurpose;
import com.digitalpersona.onetouch.DPFPFeatureSet;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPSample;
import com.digitalpersona.onetouch.DPFPTemplate;
import com.digitalpersona.onetouch.capture.DPFPCapture;
import com.digitalpersona.onetouch.capture.event.DPFPDataAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPDataEvent;
import com.digitalpersona.onetouch.processing.DPFPEnrollment;
import com.digitalpersona.onetouch.processing.DPFPFeatureExtraction;
import com.digitalpersona.onetouch.processing.DPFPImageQualityException;
import com.digitalpersona.onetouch.verification.DPFPVerification;
import com.digitalpersona.onetouch.verification.DPFPVerificationResult;

import Conexiones_SQL.BuscarSQL;
import Obj_Checador.Obj_Checador;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.Obj_tabla;
import rojerusan.componentes.RSProgressCircleAnimatedUno;


@SuppressWarnings("serial")
public class Cat_Checador extends JFrame {
	// DECLARAMOS EL OBJETO RUNTIME PARA EJECUTAR APLICACIONES
	Runtime R = Runtime.getRuntime();
        Container cont = getContentPane();
        JLayeredPane panel = new JLayeredPane();
        
        static Obj_Checador checador ;
        boolean huellaAceptada=false;
        Cat_Reloj_Sincronizado_Servidor trae_hora = new Cat_Reloj_Sincronizado_Servidor();
        
        static Obj_tabla  ObjTab = new Obj_tabla();
        static int columnas = 9;
		static int checkbox=-1;
        public static void init_tabla(){
        	
            tabla.getTableHeader().setReorderingAllowed(false) ;
            
            int x,y,z,decremento,incremento1,incremento2;
            if(anchoMon <= 1380){
            	x=45;
                y=245;
                z=60;
                decremento=-10;
                incremento1=10;
                incremento2=30;
            }else{
            	x=50;
                y=280;
                z=80;
                decremento=-40;
                incremento1=30;
                incremento2=10;
            }
            
            tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            
            tabla.getColumnModel().getColumn(0).setMaxWidth(x);
            tabla.getColumnModel().getColumn(0).setMinWidth(x);
            tabla.getColumnModel().getColumn(1).setMaxWidth(y);
            tabla.getColumnModel().getColumn(1).setMinWidth(y);
            tabla.getColumnModel().getColumn(2).setMaxWidth(z);
            tabla.getColumnModel().getColumn(2).setMinWidth(z);
            tabla.getColumnModel().getColumn(3).setMaxWidth(z);
            tabla.getColumnModel().getColumn(3).setMinWidth(z);
            tabla.getColumnModel().getColumn(4).setMaxWidth(z);
            tabla.getColumnModel().getColumn(4).setMinWidth(z);
            
            tabla.getColumnModel().getColumn(5).setMaxWidth(z+decremento);
            tabla.getColumnModel().getColumn(5).setMinWidth(z+decremento);
            tabla.getColumnModel().getColumn(6).setMaxWidth(z+incremento1);
            tabla.getColumnModel().getColumn(6).setMinWidth(z+incremento1);
            tabla.getColumnModel().getColumn(7).setMaxWidth(z+incremento2);
            tabla.getColumnModel().getColumn(7).setMinWidth(z+incremento2);
            tabla.getColumnModel().getColumn(8).setMaxWidth(z+incremento2);
            tabla.getColumnModel().getColumn(8).setMinWidth(z+incremento2);
            
			String comando="exec sp_select_tabla_checador" ;
			String basedatos="26",pintar="si";
			ObjTab.Obj_Refrescar(tabla,tabla_model, columnas, comando, basedatos,pintar,checkbox);
	    }
        
        public static DefaultTableModel tabla_model = new DefaultTableModel(null,	new String[]{	"Folio",	"Nombre", "EntoSal", "H Evento", 
        																			"T Retardo","Alerta",	"PC",		"IP",	  "Tipo Entrada"}){
                        @SuppressWarnings("rawtypes")
                        Class[] types = new Class[]{
                                   java.lang.Object.class,
                                   java.lang.Object.class, 
                                   java.lang.Object.class, 
                                   java.lang.Object.class, 
                                   java.lang.Object.class, 
                                   java.lang.Object.class, 
                                   java.lang.Object.class, 
                                   java.lang.Object.class,  
                                   java.lang.Object.class,  
                                   java.lang.Object.class
                    };
                        @SuppressWarnings({ "rawtypes", "unchecked" })
                        public Class getColumnClass(int columnIndex) {
                                return types[columnIndex];
                        }
                    public boolean isCellEditable(int fila, int columna){
                                switch(columna){
                                        case 0  : return false; 
                                        case 1  : return false; 
                                        case 2  : return false; 
                                        case 3  : return false; 
                                        case 4  : return false; 
                                        case 5  : return false;
                                        case 6  : return false; 
                                        case 7  : return false; 
                                        case 8  : return false; 
                                }
                                 return false;
                         }
                };
        
                static JTable tabla = new JTable(tabla_model){
                	 public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
                	        Component componente = super.prepareRenderer(renderer, row, col);
                	        if(col==5){
                	        	 int retardo = Integer.parseInt(tabla_model.getValueAt(row,4).toString().trim());
                	        	 Color c = Color.green;
		                            if(retardo>=4 && retardo<7){ c = Color.yellow; }
		                            if(retardo>=7 && retardo<=10){ c = new java.awt.Color(243,97,0); }
		                            if(retardo>10){	c = new java.awt.Color(255,0,0); }
		                         componente.setBackground(c);
                	        }
                	     return componente;
                	 }
                };
                
                static JScrollPane panelScroll = new JScrollPane(tabla);
                
                JLabel lblClave = new JLabel("Clave:");
                
                JPasswordField txtClaveReal = new Componentes().textPassword(new JPasswordField(), "Clave", 30);
                JCButton btnMaster = new JCButton("", "clave.png", "Azul");

                JButton btnEmplorar = new JButton("");
                
                static JLabel lblSemaforoRojo = new JLabel("");
                static JLabel lblSemaforoVerde = new JLabel("");
                JLabel lblLogo = new JLabel("");
                JLabel lblCerrar = new JLabel("");
                
                JButton btnFoto = new JButton("");
                JLabel lblFecha = new JLabel("");
                
                JLabel lblNota = new JLabel("");
                JLabel lblNota2 = new JLabel("");
                
                JLabel lblNombre = new JLabel("Emp: ");
                JLabel lblEstablecimiento = new JLabel("Estab: ");
                JLabel lblPuesto = new JLabel("Puesto: ");
                JLabel lblHorario = new JLabel("Horario: ");
                
                JLabel btnMensaje = new JLabel("");
                
                JScrollPane barra_mensaje= new JScrollPane();
                JTextArea txaAvisos = new JTextArea("");
                ImageIcon img = new ImageIcon("imagen/txa.jpg");
                
                static int anchoMon = Toolkit.getDefaultToolkit().getScreenSize().width;
        		int altoMon  = Toolkit.getDefaultToolkit().getScreenSize().height;
                
                static JLabel fondo = new JLabel();
              
                Font font;
                
//                String semaforoR = System.getProperty("user.dir")+"/Imagen/semaforo_rojo_chica.png";
                String semaforoR = System.getProperty("user.dir")+"/Imagen/circulo-rojo-icono-9411-128.png";
                
                ImageIcon tmpIconSemR = new ImageIcon(semaforoR);
                
//                String semaforoV = System.getProperty("user.dir")+"/Imagen/semaforo_verde_chica.png";
                String semaforoV = System.getProperty("user.dir")+"/Imagen/circulo-verde-icono-4055-128.png";
                
                ImageIcon tmpIconSemV = new ImageIcon(semaforoV);
                
                String fileLogo = System.getProperty("user.dir")+"/Imagen/LogPrincipal.png";
                ImageIcon tmpIconLogo = new ImageIcon(fileLogo);
                
                String fileCerrar = System.getProperty("user.dir")+"/Imagen/cerrar.png";
                ImageIcon tmpIconCerrar = new ImageIcon(fileCerrar);
              
                String fileFondo = "Imagen/calaFondoChecador.jpg";
                String fileFondo2 = "Imagen/calaFondoChecador2.jpg";
                ImageIcon tmpIconAuxFondo;
               
                String fileFoto = System.getProperty("user.dir")+"/Iconos/Un.JPG";
                ImageIcon tmpIconAuxFoto = new ImageIcon(fileFoto);
                
                Icon iconoFondo;
                
                Icon iconoSemaforoR;
                Icon iconoSemaforoV;
                
                Icon iconoLogo;
                Icon iconoCerrar;
                
                JCButton btnMenu = new JCButton("Menu", "", "");
                boolean btn_salir_huella = false;
                String checoCon = "GAFETE";
        @SuppressWarnings("static-access")
		public Cat_Checador(){
                
                this.init_tabla();
                Resolucion(anchoMon, altoMon);
                
                
                
                Icon iconoFoto = new ImageIcon(tmpIconAuxFoto.getImage().getScaledInstance(btnFoto.getWidth(), btnFoto.getHeight(), Image.SCALE_DEFAULT));
                btnFoto.setIcon(iconoFoto);
                
//                btnExaminar.addActionListener(opExaminar);
                this.setIconImage(Toolkit.getDefaultToolkit().getImage("imagen/checador.png"));
                this.setTitle("Checador");
                
                txtClaveReal.addKeyListener(action_registrar_entrada);
//                btnChecar.addActionListener(opChecar);
                btnMenu.addActionListener(opMenu);
                btnMaster.addActionListener(opKeyMaster);
                
                lblSemaforoRojo.setEnabled(false);
                lblSemaforoVerde.setEnabled(false);
             
//           asigna el foco al JTextField deseado al arrancar la ventana
              this.addWindowListener(new WindowAdapter() {
                      public void windowOpened( WindowEvent e ){
                      txtClaveReal.requestFocus();
                   }
              });
                
//           pone el foco en el txtFolio al presionar la tecla scape
                getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                   KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "foco");
                
                getRootPane().getActionMap().put("foco", new AbstractAction(){
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                                     txtClaveReal.setText("");
                                     txtClaveReal.requestFocus();
                    }
                });
                
                tabla.addMouseListener(new MouseListener() {
					public void mousePressed(MouseEvent e) {
						txtClaveReal.setText("");
                        txtClaveReal.requestFocus();
					}
					public void mouseClicked(MouseEvent e) {}
					public void mouseEntered(MouseEvent e) {}
					public void mouseExited(MouseEvent e) {}
					public void mouseReleased(MouseEvent e) {}
				});
                
                cont.add(panel);
                
                if(anchoMon>=1280){
                	this.setSize(1280,768);
                }else{
                	this.setSize(anchoMon,altoMon);
                }
                
                this.setResizable(false);
                this.setUndecorated(true);
                this.setLocationRelativeTo(null);
                this.setDefaultCloseOperation(EXIT_ON_CLOSE);
                this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

                lblCerrar.addMouseListener ( new  MouseAdapter (){
                        public void mouseReleased (MouseEvent e){
                             dispose();
                 			try {
                				R.exec("taskkill /f /im javaw.exe");
                			} catch (Exception e2){}
                        }  
                 });  
        }
        
        ActionListener opKeyMaster = new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		txtClaveReal.setText("");
        		txtClaveReal.requestFocus();
        		new Cat_Huellas_Personalizado("CLAVE MASTER").setVisible(true);
        	}
        };
        
        KeyListener action_registrar_entrada = new KeyListener() {
			public void keyPressed(KeyEvent e) {	
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
						checoCon = "GAFETE";
						checar();
				}
			}
			public void keyReleased(KeyEvent e) {}
			public void keyTyped(KeyEvent e) {}
        }; 
        
        ActionListener opMenu = new ActionListener(){
            public void actionPerformed(ActionEvent e) {
	          	  
	          	  new Cat_Menu_Checador().setVisible(true);
	            }
	      };
        
        int folio_empleado;
        String claveMaster;
        
        @SuppressWarnings("deprecation")
		public void checar(){        
        	if(txtClaveReal.getText().toUpperCase().equals("")){
				 
				 lblSemaforoRojo.setEnabled(true);
				 lblSemaforoVerde.setEnabled(false);
                 JOptionPane.showMessageDialog(null, "Es Necesario Pasar El Gafete Por El Lector","Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
                     
                txtClaveReal.setText("");
                txtClaveReal.requestFocus();
                return;
			 }else{   
				 	
				 String codigoBarrar = txtClaveReal.getText().toUpperCase().trim();
				 int posicionC = codigoBarrar.indexOf('C');
				 	
				 	if(posicionC>0){
				 		
							if(isNumeric(codigoBarrar.substring(0, posicionC))){
								
								folio_empleado = Integer.parseInt(codigoBarrar.substring(0, posicionC));
								claveMaster = codigoBarrar.substring(posicionC+1,codigoBarrar.length());
								
								checador = new Obj_Checador().buscar(folio_empleado);
								
//								Obj_Empleados re = new Obj_Empleados().buscar(folio_empleado);  //busca a empleado 
//		                        Obj_Entosal entosalClave = new Obj_Entosal().buscar(); //busca clave maestra
								
								if(checador.getStatus_checador().equals("CHECADOR BLOQUEADO")){
										txtClaveReal.setText("");
		                       			txtClaveReal.requestFocus();
			                        	pantallaDeAvisos();
	                        	}else{
			                        		
				                        if(checador.getFolio_empleado()==folio_empleado){
				                        	
				                        	if(!checoCon.equals("CLAVE MASTER")){
				                        		
				                        		int contador_de_intentos_de_huella=0;
					                        	//TODO(LLAMAR VERIFICADOR DE HUELLA)
					                        	if(checador.getForma_de_checar().equals("GH")){
													new Cat_Huellas_Personalizado("GAFETE").setVisible(true);
													
														while(!huellaAceptada){
																if(!btn_salir_huella){
																		if(contador_de_intentos_de_huella == 3){
																			txtClaveReal.setText("");
																			txtClaveReal.requestFocus();
																			JOptionPane.showMessageDialog(null, "1.-Se Detectaron Problemas Con La Huella O No Se Ha Registrado, Comuniquese AL Departamente De Desarrollo Humano.", "Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
																			return;
																		}else{
																			contador_de_intentos_de_huella++;
																			JOptionPane.showMessageDialog(null, "No Se Puedo Identificar La Huella, Vuelva A Intentarlo", "Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
																			new Cat_Huellas_Personalizado("GAFETE").setVisible(true);
																		}
																}else{
																	txtClaveReal.setText("");
																	txtClaveReal.requestFocus();
																	btn_salir_huella = false;
																	return;
																}
														}
												}
	        			    	    		}
				                        	
				                        	if((!huellaAceptada) && (checoCon.equals("CLAVE MASTER"))){
				                        		txtClaveReal.setText("");
												txtClaveReal.requestFocus();
												btn_salir_huella = false;
				                        		JOptionPane.showMessageDialog(null, "2.-Se Detectaron Problemas Con La Huella O No Se Ha Registrado, Comuniquese AL Departamente De Desarrollo Humano.", "Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
												return;
				                        	}
				                        	
				                           		switch (checador.getStatus()){
				                                         case 1: if(checador.getNo_checador().equals(codigoBarrar)){
				                                            		 		registrarEntrada("-");
				                                            	 }else{
				                                            		 if(checador.getMaster_key().equals(claveMaster)){
				                                            			 	registrarEntrada("MASTER");
				                                            		 }else{
					                                            			 lblSemaforoRojo.setEnabled(true);
					                                                         lblSemaforoVerde.setEnabled(false);
						                    				     		     JOptionPane.showMessageDialog(null, "La Clave Ingresada No Corresponde A Ningun Trabajador <>","Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				                                                			 txtClaveReal.setText("");
				                                                			 txtClaveReal.requestFocus();
				                                                			 return;
				                                            		 }
				                                            	 }
				                                          break;
				                                          case 2:	lblSemaforoRojo.setEnabled(true);
				                                          			lblSemaforoVerde.setEnabled(false);
				                                          			JOptionPane.showMessageDialog(null, "No puedes checar, tu estatus es vacaciones\nfavor de comunicarte a desarrollo humano,\npara que puedas registrar tu entrada a trabajar,\nde lo contrario no te sera valido el pago de este dia","Aviso",JOptionPane.WARNING_MESSAGE);
				                                                                         txtClaveReal.setText("");
				                                                                         txtClaveReal.requestFocus();
				                                          break;
				                                          case 3:	lblSemaforoRojo.setEnabled(true);
				                                          			lblSemaforoVerde.setEnabled(false);
				                                          			JOptionPane.showMessageDialog(null, "No puedes checar, tu estatus es de incapacidad\nfavor de comunicarte a desarrollo humano,\npara que puedas registrar tu entrada a trabajar,\nde lo contrario no te sera valido el pago de este dia","Aviso",JOptionPane.WARNING_MESSAGE);
				                                                                         txtClaveReal.setText("");
				                                                                         txtClaveReal.requestFocus();
				                                          break;
				                                          case 4:	lblSemaforoRojo.setEnabled(true);
				                                          			lblSemaforoVerde.setEnabled(false);
				                                          			JOptionPane.showMessageDialog(null, "No puedes checar, tu estatus es de baja\nfavor de comunicarte a desarrollo humano,\npara que puedas registrar tu entrada a trabajar,\nde lo contrario no te sera valido el pago de este dia","Aviso",JOptionPane.WARNING_MESSAGE);
				                                                                         txtClaveReal.setText("");
				                                                                         txtClaveReal.requestFocus();
				                                          break;
				                                          case 5:	lblSemaforoRojo.setEnabled(true);
				                                          			lblSemaforoVerde.setEnabled(false);
				                                          			JOptionPane.showMessageDialog(null, "No puedes checar, tu estatus es de baja\nfavor de comunicarte a desarrollo humano,\npara que puedas registrar tu entrada a trabajar,\nde lo contrario no te sera valido el pago de este dia","Aviso",JOptionPane.WARNING_MESSAGE);
				                                                                         txtClaveReal.setText("");
				                                                                         txtClaveReal.requestFocus();
				                                          break;
				                                          case 6: if(checador.getNo_checador().equals(codigoBarrar)){
						                                          		 		registrarEntrada("-");
						                                          	 }else{
						                                          		 if(checador.getMaster_key().equals(claveMaster)){
						                                          			 	registrarEntrada("MASTER");
						                                          		 }else{
						                                          			 
						                                            			 lblSemaforoRojo.setEnabled(true);
						                                                         lblSemaforoVerde.setEnabled(false);
							                    				     		     JOptionPane.showMessageDialog(null, "La Clave Ingresada No Corresponde A Ningun Trabajador ","Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
						                                              			 txtClaveReal.setText("");
						                                              			 txtClaveReal.requestFocus();
						                                                         return;
						                                          		 }
						                                          	 }
						                                  break;
				                                };
		                        	
									}else{
										lblSemaforoRojo.setEnabled(true);
										lblSemaforoVerde.setEnabled(false);
					     		  		 JOptionPane.showMessageDialog(null, "La Clave Ingresada No Corresponde A Ningun Trabajador > ","Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				                         txtClaveReal.setText("");
				                         txtClaveReal.requestFocus();
				                         return;
									}
		                        }
					 	}else{
	                   		 lblSemaforoRojo.setEnabled(true);
	                   		 lblSemaforoVerde.setEnabled(false);
	      		  			 JOptionPane.showMessageDialog(null, "La Clave Ingresada No Corresponde A Ningun Trabajador >>","Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
	              			 txtClaveReal.setText("");
	              			 txtClaveReal.requestFocus();
	                         return;
                        }
				 }else{
				 		lblSemaforoRojo.setEnabled(true);
						lblSemaforoVerde.setEnabled(false);
			                
     		  		 	JOptionPane.showMessageDialog(null, "La Clave Ingresada No Corresponde A Ningun Trabajador >>>","Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
     		  		    txtClaveReal.setText("");
                        txtClaveReal.requestFocus();
                        return;
		 		}
			 }
        }
        
        @SuppressWarnings("deprecation")
        public void registrarEntrada(String checada){
//----------------------------------------------------------------------------------------------------------------------        
////meter split para que extraiga el puro numero
////                        declarar variable que cachara el valor real de la clave
//                        String CadenaDeClave = "";
//                        for (int x=0; x < txtClaveReal.getText().length(); x++) {
////                     condicion(si el caracter en la posicion ubicada es diferente de vacio entra y asigna)
////                    toma el valor de CadenaDeClave y le asigna el siguiente caracter
//                                if (txtClaveReal.getText().charAt(x) != ' ')
//                                        CadenaDeClave += txtClaveReal.getText().charAt(x);
//                        }
//----------------------------------------------------------------------------------------------------------------------        
//                if(txtClaveReal.getText().toUpperCase().equals(numero_de_checador)){
        	
//-----------------------------------------
//        	Obj_Entosal entosal=new Obj_Entosal().checar_dia_descanso(folio_empleado);
           
//        	if (checador.getValor_Descanso().equals("true")){
    		if (checador.isValida_descanso()){
        			lblSemaforoRojo.setEnabled(true);
		            lblSemaforoVerde.setEnabled(false);
		            JOptionPane.showMessageDialog(null, "El Dia De Hoy Lo Tienes Registrado Como Tu Dia De Descanso,\nAvisa A Desarrollo Humano Para Que Puedas Registrar Tu Entrada \nA Trabajar, De Lo Contrario No Te Sera Valido El Pago De Este Dia","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
                    JOptionPane.showMessageDialog(null, "El Dia De Hoy Lo Tienes Registrado Como Tu Dia De Descanso,\nAvisa A Desarrollo Humano Para Que Puedas Registrar Tu Entrada \nA Trabajar, De Lo Contrario No Te Sera Valido El Pago De Este Dia","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/red-de-usuario-icono-6758-64.png"));
                    txtClaveReal.setText("");
                    txtClaveReal.requestFocus();
                    return;
         }else{
         	
//                if(new Obj_Entosal().buscar_colicion(folio_empleado)){
                	if (checador.isValida_chequeo_duplicado()){
                	lblSemaforoRojo.setEnabled(true);
                    lblSemaforoVerde.setEnabled(false);
                    JOptionPane.showMessageDialog(null, "Estas Intentando Checar 2 Veces En Menos\n De 1 Minuto Espere Un Momento y Reintente","Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
                    txtClaveReal.setText("");
                    txtClaveReal.requestFocus();
                    return;
                }else{
//                        if(new Obj_Entosal().checadas_dia_dobla(folio_empleado)){
                        	if (checador.isValida_checar_dia_dobla()){
                        	lblSemaforoRojo.setEnabled(true);
                            lblSemaforoVerde.setEnabled(false);
                            JOptionPane.showMessageDialog(null, "A excedido el numero de checadas son 4 para turno normal\ny 6 para el dia que tienen 15 minutos extras ","Aviso",JOptionPane.INFORMATION_MESSAGE);
                            txtClaveReal.setText("");
                            txtClaveReal.requestFocus();
                            return;
                        }else{
//                              if(new Obj_Entosal().checa_salida_comer(folio_empleado)){
                        	  if (checador.isValida_checar_salida_a_comer()){
                                        new Cat_Checador_Selecion_Comida((folio_empleado),checada).setVisible(true);
                               }else{
//	                                    if(re.getNo_checador().equals(txtClaveReal.getText().toUpperCase())||entosalClave.getClave().equals(claveMaster)){
                                    	if(checador.getNo_checador().equals(txtClaveReal.getText().toUpperCase())||checador.getMaster_key().equals(claveMaster)){
	                                    	
                                    		Object[][] registro = intentar_checar(folio_empleado,checada,0);
                                    		
                                    		
                                    		if(!registro[0][0].toString().trim().equals("false")){
	                                    			ImageIcon tmpIconAux = new ImageIcon(System.getProperty("user.dir")+"/tmp/tmp.jpg");
			         		                        Icon icono = new ImageIcon(tmpIconAux.getImage().getScaledInstance(btnFoto.getWidth(), btnFoto.getHeight(), Image.SCALE_DEFAULT));
			         		                        btnFoto.setIcon(icono);  
		                                    	
				                                    String tipo=registro[0][0].toString();
				                                    String hora=registro[0][1].toString();
				                                    String Fecha=registro[0][7].toString();
				                                    lblFecha.setText(Fecha+"");
				                                    
				                                    txtClaveReal.setText("");
				                                    txtClaveReal.requestFocus();
		            
			                                            if(Integer.parseInt(registro[0][1].toString().trim().substring(0,2))<2){
			                                                            lblNota.setText("EL EMPLEADO "+checador.getNombre_empleado());
			                                                            lblNota2.setText("A CHECADO "+tipo+" A LA "+hora.substring(0,hora.length()-3)+" Hr");
			
			                                            }else{
			                                                            lblNota.setText("EL EMPLEADO "+checador.getNombre_empleado());
			                                                            lblNota2.setText("A  CHECADO "+tipo+" A LAS "+hora.substring(0,hora.length()-3)+" Hrs");
			                                            }
		                                            
			                                        lblNombre.setText("Emp: ");
			                                        lblEstablecimiento.setText("Estab: ");
			                                        lblPuesto.setText("Puesto: ");
			                                        lblHorario.setText("Horario: ");
	                                                            
		                                            lblNombre.setText(lblNombre.getText() + checador.getNombre_empleado());
		                                            lblEstablecimiento.setText(lblEstablecimiento.getText() + checador.getEstablecimiento());
		                                            lblPuesto.setText(lblPuesto.getText() + checador.getPuesto());
		                                            
		                                            txtClaveReal.requestFocus(); 	
                                    		}else{
                                    			txtClaveReal.setText("");
                                    			txtClaveReal.requestFocus(); 	
                                    			JOptionPane.showMessageDialog(null, "Error Al Momento De Checar, Comuniquese Al Departamento De Desarrollo Humano Para Que Revisen Su Horario.","Error",JOptionPane.ERROR_MESSAGE);
                                    			return;
                                    		}
                                    		
                                    		
                                    		
	                                    		
                                 }else{
	                                	 lblSemaforoRojo.setEnabled(true);
	                                     lblSemaforoVerde.setEnabled(false);
                                         JOptionPane.showMessageDialog(null, "La Clave No Corresponde","Error",JOptionPane.WARNING_MESSAGE);
                                         panelLimpiar();
                                         txtClaveReal.requestFocus();
                                         return;
                                    }
                               }
                        }
             }
        }
                txtClaveReal.setText("");
                txtClaveReal.requestFocus();
   }
        
        public void panelLimpiar(){        
                txtClaveReal.setText("");
        }
        
        public static Object[][] intentar_checar(int folio_empleado,String tipo_entrada,int tipo_salida_comer){
                
//metodo para llenar vector para checador2--------------------------------------
        	
        	
//        	new Obj_Checador().insertar_checada(folio_empleado,tipo_entrada,tipo_salida_comer);
        	
                Object[][] vector = new BuscarSQL().buscar_registro_checador(folio_empleado,tipo_entrada,tipo_salida_comer);
                if(!vector[0][0].toString().trim().equals("false")){
                	                	
                	lblSemaforoRojo.setEnabled(false);
                    lblSemaforoVerde.setEnabled(true);
                 	tabla_model.setRowCount(0);
                 	init_tabla();                                
                            	
                 	pantallaDeAvisos();
                }else{
                	lblSemaforoRojo.setEnabled(true);
                    lblSemaforoVerde.setEnabled(false);
                }
                return vector;
        }
        
        
        public static void pantallaDeAvisos(){
            //TODO   apartado para configurar el uso de la pantalla de avisos--------------------------------
          	 if(checador.getArreglo_mensaje()[0][0].toString().trim().equals("true")){
                      JDialog frame = new JDialog();
//                      String ruta=fila_mensaje.get(3).toString().trim();
//                      String mensaje=fila_mensaje.get(2).toString().trim();
//                      String color_fuente=fila_mensaje.get(4).toString().trim();
                      
                      String ruta=checador.getArreglo_mensaje()[0][3].toString().trim();
                      String mensaje=checador.getArreglo_mensaje()[0][2].toString().trim();
                      String color_fuente=checador.getArreglo_mensaje()[0][4].toString().trim();
                      
              	           frame.setUndecorated(true);
              	           
              		    new Cat_Avisos_Checador(frame,ruta,mensaje,color_fuente);
              		    frame.setVisible(true);
          }
//-------------------------------------------------------------------------------------------
        }
        
        private static boolean isNumeric(String cadena){
        	try {
        		Integer.parseInt(cadena);
        		return true;
        	} catch (NumberFormatException nfe){
        		return false;
        	}
        }
        
    	public void Resolucion(int ancho, int alto){
    		int x = 10, y = 30, z = 100;
    		if(ancho == 800){
//    			valores unicos(800*600)
    			tmpIconAuxFondo = new ImageIcon(fileFondo2);
                iconoFondo = new ImageIcon(tmpIconAuxFondo.getImage().getScaledInstance(anchoMon,altoMon, Image.SCALE_DEFAULT));
                fondo.setIcon(iconoFondo);
                
    			trae_hora.lblHora.setFont(new java.awt.Font("Algerian",0,70));
    			
   			 lblClave.setForeground(Color.BLUE);
                lblClave.setFont(new Font("Arial",0,10));
                
                lblFecha.setForeground(Color.BLUE);
                lblFecha.setFont(new Font("Algerian",0,20));
                
                lblNota.setForeground(Color.BLUE);
                lblNota.setFont(new Font("Arial",0,18));
                
                lblNota2.setForeground(Color.BLUE);
                lblNota2.setFont(new Font("Arial",0,18));
                
                lblNombre.setFont(new Font("Monospaced",0,9));
                lblEstablecimiento.setFont(new Font("Monospaced",0,9));
                lblPuesto.setFont(new Font("Monospaced",0,9));
                lblHorario.setFont(new Font("Monospaced",0,9));
                
               panel.add(lblClave).setBounds(5,y,z,20);
               panel.add(txtClaveReal).setBounds(x*4,y,z-18,20);
               panel.add(btnMaster).setBounds(x*4+(z-18),y,30,20);
               
               panel.add(trae_hora.lblHora).setBounds(x*13,y-20, z*5, 100);
               panel.add(lblNota).setBounds(x*6,y+=115, 800, 30);
               panel.add(lblNota2).setBounds(100,y+=30, 800, 30);
               
               panel.add(lblFecha).setBounds(570,100, 150, 30);
               panel.add(lblSemaforoRojo).setBounds(15,52, 40, 40);
               panel.add(lblSemaforoVerde).setBounds(70,52, 40, 40);
               panel.add(lblLogo).setBounds((anchoMon/2)-45, 21, 75, 75);
               panel.add(lblCerrar).setBounds(anchoMon-62,21, 70, 75);
               panel.add(btnFoto).setBounds(anchoMon-334,21,77,77);
               
               panel.add(lblNombre).setBounds(550,22,190,10);
               panel.add(lblEstablecimiento).setBounds(550,40,190,10);
               panel.add(lblPuesto).setBounds(550,58,190,10);
               panel.add(lblHorario).setBounds(550,76,190,10);
               
               panel.add(btnMenu).setBounds(15,103,180,20);

               panel.add(panelScroll).setBounds(15,y+63,773,altoMon-250);
               
               panel.add(fondo).setBounds(0,0,anchoMon,altoMon);
               
               iconoSemaforoR = new ImageIcon(tmpIconSemR.getImage().getScaledInstance(lblSemaforoRojo.getWidth(), lblSemaforoRojo.getHeight(), Image.SCALE_DEFAULT));
               lblSemaforoRojo.setIcon(iconoSemaforoR);
               
               iconoSemaforoV = new ImageIcon(tmpIconSemV.getImage().getScaledInstance(lblSemaforoVerde.getWidth(), lblSemaforoVerde.getHeight(), Image.SCALE_DEFAULT));
               lblSemaforoVerde.setIcon(iconoSemaforoV);
               
               iconoLogo = new ImageIcon(tmpIconLogo.getImage().getScaledInstance(75, 75, Image.SCALE_DEFAULT));
               lblLogo.setIcon(iconoLogo);        
       
               iconoCerrar = new ImageIcon(tmpIconCerrar.getImage().getScaledInstance(70, 75, Image.SCALE_DEFAULT));
               lblCerrar.setIcon(iconoCerrar);
    			
    		}
    		if(ancho == 1024){
//    			valores unicos(1024*768)
    			tmpIconAuxFondo = new ImageIcon(fileFondo2);
                iconoFondo = new ImageIcon(tmpIconAuxFondo.getImage().getScaledInstance(anchoMon,altoMon, Image.SCALE_DEFAULT));
                fondo.setIcon(iconoFondo);
                
    			txaAvisos.setLineWrap(true);
                barra_mensaje.setOpaque(false);
                barra_mensaje.setViewportView(txaAvisos);
                
    			trae_hora.lblHora.setFont(new java.awt.Font("Algerian",0,80));
    			
    			lblClave.setForeground(Color.BLUE);
                lblClave.setFont(new Font("Arial",0,10));
                
                lblFecha.setForeground(Color.BLUE);
                lblFecha.setFont(new Font("Algerian",0,20));
                
                lblNota.setForeground(Color.BLUE);
                lblNota.setFont(new Font("Arial",0,30));
                
                lblNota2.setForeground(Color.BLUE);
                lblNota2.setFont(new Font("Arial",0,30));
                
                lblNombre.setFont(new Font("Monospaced",0,11));
                lblEstablecimiento.setFont(new Font("Monospaced",0,11));
                lblPuesto.setFont(new Font("Monospaced",0,11));
                lblHorario.setFont(new Font("Monospaced",0,11));
                
                txaAvisos.setBackground(new java.awt.Color(0,0,205));
                txaAvisos.setForeground(new java.awt.Color(255,69,0));
                
                Font font = new Font("Verdana", Font.BOLD, 24);
                txaAvisos.setFont(font);
               
               panel.add(lblClave).setBounds(5,y,z,20);
               panel.add(txtClaveReal).setBounds(x*4,y,z-18,20);
               panel.add(btnMaster).setBounds(x*4+(z-18),y,30,20);
               
               panel.add(trae_hora.lblHora).setBounds(x*18,y-5, z*5, 100);
               panel.add(lblNota).setBounds(x*6,y+=165, 800, 30);
               panel.add(lblNota2).setBounds(100,y+=30, 800, 30);
               
               panel.add(lblFecha).setBounds(780,130, 150, 30);
               panel.add(lblSemaforoRojo).setBounds(5,52, 70, 70);
               panel.add(lblSemaforoVerde).setBounds(85,52, 70, 70);
               panel.add(lblLogo).setBounds((anchoMon/2)-58, 27, 95, 95);
               panel.add(lblCerrar).setBounds(anchoMon-79,28, 85, 95);
               panel.add(btnFoto).setBounds(anchoMon-431,24,105,105);
               
               panel.add(lblNombre).setBounds(705,30,230,20);
               panel.add(lblEstablecimiento).setBounds(705,55,230,20);
               panel.add(lblPuesto).setBounds(705,80,230,20);
               panel.add(lblHorario).setBounds(705,105,230,20);
               
               panel.add(btnMenu).setBounds(15,136,180,20);
               
               panel.add(barra_mensaje).setBounds(810,y-58,210,580);
               
               panel.add(panelScroll).setBounds(17,y+73,783,altoMon-320);
               panel.add(fondo).setBounds(0,0,anchoMon,altoMon);
               
               iconoSemaforoR = new ImageIcon(tmpIconSemR.getImage().getScaledInstance(lblSemaforoRojo.getWidth(), lblSemaforoRojo.getHeight(), Image.SCALE_DEFAULT));
               lblSemaforoRojo.setIcon(iconoSemaforoR);
               
               iconoSemaforoV = new ImageIcon(tmpIconSemV.getImage().getScaledInstance(lblSemaforoVerde.getWidth(), lblSemaforoVerde.getHeight(), Image.SCALE_DEFAULT));
               lblSemaforoVerde.setIcon(iconoSemaforoV);
               
               iconoLogo = new ImageIcon(tmpIconLogo.getImage().getScaledInstance(lblLogo.getWidth(), lblLogo.getHeight(), Image.SCALE_DEFAULT));
               lblLogo.setIcon(iconoLogo);        
       
               iconoCerrar = new ImageIcon(tmpIconCerrar.getImage().getScaledInstance(lblCerrar.getWidth(), lblCerrar.getHeight(), Image.SCALE_DEFAULT));
               lblCerrar.setIcon(iconoCerrar);
    		}
    		if(ancho == 1152){
    			switch(alto){
    			case 864:
    				tmpIconAuxFondo = new ImageIcon(fileFondo2);
                    iconoFondo = new ImageIcon(tmpIconAuxFondo.getImage().getScaledInstance(anchoMon,altoMon, Image.SCALE_DEFAULT));
                    fondo.setIcon(iconoFondo);
                    
        			txaAvisos.setLineWrap(true);
                    barra_mensaje.setOpaque(false);
                    barra_mensaje.setViewportView(txaAvisos);
                    
        			trae_hora.lblHora.setFont(new java.awt.Font("Algerian",0,90));
        			
        			lblClave.setForeground(Color.BLUE);
                    lblClave.setFont(new Font("Arial",0,12));
                    
                    lblFecha.setForeground(Color.BLUE);
                    lblFecha.setFont(new Font("Algerian",0,22));
                    
                    lblNota.setForeground(Color.BLUE);
                    lblNota.setFont(new Font("Arial",0,35));
                    
                    lblNota2.setForeground(Color.BLUE);
                    lblNota2.setFont(new Font("Arial",0,35));
                    
                    lblNombre.setFont(new Font("Monospaced",0,11));
                    lblEstablecimiento.setFont(new Font("Monospaced",0,11));
                    lblPuesto.setFont(new Font("Monospaced",0,11));
                    lblHorario.setFont(new Font("Monospaced",0,11));
                    
                    txaAvisos.setBackground(new java.awt.Color(0,0,205));
                    txaAvisos.setForeground(new java.awt.Color(255,69,0));
                    
                    Font font = new Font("Verdana", Font.BOLD, 24);
                    txaAvisos.setFont(font);
                   
                   panel.add(lblClave).setBounds(5,y,z,20);
                   panel.add(txtClaveReal).setBounds(x*4,y,z-18,20);
                   panel.add(btnMaster).setBounds(x*4+(z-18),y,30,20);
                   
                   panel.add(trae_hora.lblHora).setBounds(x*20,y+5, z*5, 100);
                   panel.add(lblNota).setBounds(x*6,y+=185, 800, 40);
                   panel.add(lblNota2).setBounds(100,y+=40, 800, 40);
                   
                   panel.add(lblFecha).setBounds(850,160, 150, 30);
                   panel.add(lblSemaforoRojo).setBounds(15,62, 70, 70);
                   panel.add(lblSemaforoVerde).setBounds(95,62, 70, 70);
                   panel.add(lblLogo).setBounds((anchoMon/2)-68, 27, 115, 115);
                   panel.add(lblCerrar).setBounds(anchoMon-89,28, 90, 110);
                   panel.add(btnFoto).setBounds(anchoMon-484,29,115,115);
                   
                   panel.add(lblNombre).setBounds(790,30,270,20);
                   panel.add(lblEstablecimiento).setBounds(790,55,270,20);
                   panel.add(lblPuesto).setBounds(790,80,270,20);
                   panel.add(lblHorario).setBounds(790,105,270,20);
                   
                   panel.add(btnMenu).setBounds(15,136,180,20);
                   
                   panel.add(barra_mensaje).setBounds(915,y-68,220,645);
                   
                   panel.add(panelScroll).setBounds(72,y+73,783,altoMon-360);
                   panel.add(fondo).setBounds(0,0,anchoMon,altoMon);
                   
                   iconoSemaforoR = new ImageIcon(tmpIconSemR.getImage().getScaledInstance(lblSemaforoRojo.getWidth(), lblSemaforoRojo.getHeight(), Image.SCALE_DEFAULT));
                   lblSemaforoRojo.setIcon(iconoSemaforoR);
                   
                   iconoSemaforoV = new ImageIcon(tmpIconSemV.getImage().getScaledInstance(lblSemaforoVerde.getWidth(), lblSemaforoVerde.getHeight(), Image.SCALE_DEFAULT));
                   lblSemaforoVerde.setIcon(iconoSemaforoV);
                   
                   iconoLogo = new ImageIcon(tmpIconLogo.getImage().getScaledInstance(lblLogo.getWidth(), lblLogo.getHeight(), Image.SCALE_DEFAULT));
                   lblLogo.setIcon(iconoLogo);        
           
                   iconoCerrar = new ImageIcon(tmpIconCerrar.getImage().getScaledInstance(lblCerrar.getWidth(), lblCerrar.getHeight(), Image.SCALE_DEFAULT));
                   lblCerrar.setIcon(iconoCerrar);
                 break;
    			}
    		}
    		if(ancho >= 1280){
    			switch(alto){
    			case 600:
    				tmpIconAuxFondo = new ImageIcon(fileFondo);
                    iconoFondo = new ImageIcon(tmpIconAuxFondo.getImage().getScaledInstance(anchoMon,altoMon, Image.SCALE_DEFAULT));
                    fondo.setIcon(iconoFondo);
                    
        			txaAvisos.setLineWrap(true);
                    barra_mensaje.setOpaque(false);
                    barra_mensaje.setViewportView(txaAvisos);
                    
        			trae_hora.lblHora.setFont(new java.awt.Font("Algerian",0,100));
        			
        			lblClave.setForeground(Color.BLUE);
                    lblClave.setFont(new Font("Arial",0,12));
                    
                    lblFecha.setForeground(Color.BLUE);
                    lblFecha.setFont(new Font("Algerian",0,22));
                    
                    lblNota.setForeground(Color.BLUE);
                    lblNota.setFont(new Font("Arial",0,20));
                    
                    lblNota2.setForeground(Color.BLUE);
                    lblNota2.setFont(new Font("Arial",0,20));
                    
                    lblNombre.setFont(new Font("Monospaced",0,11));
                    lblEstablecimiento.setFont(new Font("Monospaced",0,11));
                    lblPuesto.setFont(new Font("Monospaced",0,11));
                    lblHorario.setFont(new Font("Monospaced",0,11));
                    
                    txaAvisos.setBackground(new java.awt.Color(0,0,205));
                    txaAvisos.setForeground(new java.awt.Color(255,69,0));
                    
                    font = new Font("Verdana", Font.BOLD, 24);
                    txaAvisos.setFont(font);
                   
                   panel.add(lblClave).setBounds(45,y,z,20);
                   panel.add(txtClaveReal).setBounds(x*8,y,z-18,20);
                   panel.add(btnMaster).setBounds(x*8+(z-18),y,30,20);
                   
                   panel.add(trae_hora.lblHora).setBounds(x*24,y, z*5, 100);
                   panel.add(lblNota).setBounds(x*12,y+=145, 800, 40);
                   panel.add(lblNota2).setBounds(160,y+=30, 800, 40);
                   
                   panel.add(lblFecha).setBounds(900,135, 250, 30);
                   panel.add(lblSemaforoRojo).setBounds(35,55, 70, 70);
                   panel.add(lblSemaforoVerde).setBounds(115,55, 70, 70);
                   panel.add(lblLogo).setBounds((anchoMon/2)-45, 27, 105, 105);
                   panel.add(lblCerrar).setBounds(anchoMon-89,28, 90, 100);
                   panel.add(btnFoto).setBounds(anchoMon-505,28,118,100);
                   
                   panel.add(lblNombre).setBounds(900,30,290,20);
                   panel.add(lblEstablecimiento).setBounds(900,55,290,20);
                   panel.add(lblPuesto).setBounds(900,80,290,20);
                   panel.add(lblHorario).setBounds(900,105,290,20);
                   
                   panel.add(btnMenu).setBounds(15,136,180,20);
                   
                   panel.add(panelScroll).setBounds(17,y+53,733,altoMon-268);
                   panel.add(fondo).setBounds(0,0,anchoMon,altoMon);
                   
                   iconoSemaforoR = new ImageIcon(tmpIconSemR.getImage().getScaledInstance(lblSemaforoRojo.getWidth(), lblSemaforoRojo.getHeight(), Image.SCALE_DEFAULT));
                   lblSemaforoRojo.setIcon(iconoSemaforoR);
                   
                   iconoSemaforoV = new ImageIcon(tmpIconSemV.getImage().getScaledInstance(lblSemaforoVerde.getWidth(), lblSemaforoVerde.getHeight(), Image.SCALE_DEFAULT));
                   lblSemaforoVerde.setIcon(iconoSemaforoV);
                   
                   iconoLogo = new ImageIcon(tmpIconLogo.getImage().getScaledInstance(lblLogo.getWidth(), lblLogo.getHeight(), Image.SCALE_DEFAULT));
                   lblLogo.setIcon(iconoLogo);        
           
                   iconoCerrar = new ImageIcon(tmpIconCerrar.getImage().getScaledInstance(lblCerrar.getWidth(), lblCerrar.getHeight(), Image.SCALE_DEFAULT));
                   lblCerrar.setIcon(iconoCerrar);
                 break;
    			case 720:
    				tmpIconAuxFondo = new ImageIcon(fileFondo);
                    iconoFondo = new ImageIcon(tmpIconAuxFondo.getImage().getScaledInstance(1280,720, Image.SCALE_DEFAULT));
                    fondo.setIcon(iconoFondo);
    				
        			txaAvisos.setLineWrap(true);
                    barra_mensaje.setOpaque(false);
                    barra_mensaje.setViewportView(txaAvisos);
                    
        			trae_hora.lblHora.setFont(new java.awt.Font("Algerian",0,100));
        			
        			lblClave.setForeground(Color.BLUE);
                    lblClave.setFont(new Font("Arial",0,12));
                    
                    lblFecha.setForeground(Color.BLUE);
                    lblFecha.setFont(new Font("Algerian",0,30));
                    
                    lblNota.setForeground(Color.BLUE);
                    lblNota.setFont(new Font("Arial",0,22));
                    
                    lblNota2.setForeground(Color.BLUE);
                    lblNota2.setFont(new Font("Arial",0,22));
                    
                    lblNombre.setFont(new Font("Monospaced",0,14));
                    lblEstablecimiento.setFont(new Font("Monospaced",0,14));
                    lblPuesto.setFont(new Font("Monospaced",0,14));
                    lblHorario.setFont(new Font("Monospaced",0,14));
                   
                   panel.add(lblClave).setBounds(45,y+15,z,20);
                   panel.add(txtClaveReal).setBounds(x*8,y+15,z-18,20);
                   panel.add(btnMaster).setBounds(x*8+(z-18),y+15,30,20);
                   
                   panel.add(trae_hora.lblHora).setBounds(x*24,y+10, z*5, 100);
                   panel.add(lblNota).setBounds(x*10,y+=185, 800, 40);
                   panel.add(lblNota2).setBounds(150,y+=30, 800, 40);
                   
                   panel.add(lblFecha).setBounds(910,171, 250, 30);
                   panel.add(lblSemaforoRojo).setBounds(35,70, 70, 70);
                   panel.add(lblSemaforoVerde).setBounds(115,70, 70, 70);
                   panel.add(lblLogo).setBounds((1280/2)-60, 33, 125, 125);
                   panel.add(lblCerrar).setBounds(1280-89,33, 90, 123);
                   panel.add(btnFoto).setBounds(1280-505,33,118,123);
                   
                   panel.add(lblNombre).setBounds(900,33,290,20);
                   panel.add(lblEstablecimiento).setBounds(900,63,290,20);
                   panel.add(lblPuesto).setBounds(900,93,290,20);
                   panel.add(lblHorario).setBounds(900,123,290,20);
                   
                   panel.add(btnMenu).setBounds(15,166,180,20);
                   
                   panel.add(panelScroll).setBounds(19,y+62,733,401);
                   panel.add(fondo).setBounds(0,0,1280,720);
                   
                   iconoSemaforoR = new ImageIcon(tmpIconSemR.getImage().getScaledInstance(lblSemaforoRojo.getWidth(), lblSemaforoRojo.getHeight(), Image.SCALE_DEFAULT));
                   lblSemaforoRojo.setIcon(iconoSemaforoR);
                   
                   iconoSemaforoV = new ImageIcon(tmpIconSemV.getImage().getScaledInstance(lblSemaforoVerde.getWidth(), lblSemaforoVerde.getHeight(), Image.SCALE_DEFAULT));
                   lblSemaforoVerde.setIcon(iconoSemaforoV);
                   
                   iconoLogo = new ImageIcon(tmpIconLogo.getImage().getScaledInstance(lblLogo.getWidth(), lblLogo.getHeight(), Image.SCALE_DEFAULT));
                   lblLogo.setIcon(iconoLogo);        
           
                   iconoCerrar = new ImageIcon(tmpIconCerrar.getImage().getScaledInstance(lblCerrar.getWidth(), lblCerrar.getHeight(), Image.SCALE_DEFAULT));
                   lblCerrar.setIcon(iconoCerrar);
                 break;
    			default:
    				tmpIconAuxFondo = new ImageIcon(fileFondo);
                    iconoFondo = new ImageIcon(tmpIconAuxFondo.getImage().getScaledInstance(1280,768, Image.SCALE_DEFAULT));
                    fondo.setIcon(iconoFondo);
    				
        			txaAvisos.setLineWrap(true);
                    barra_mensaje.setOpaque(false);
                    barra_mensaje.setViewportView(txaAvisos);
                    
        			trae_hora.lblHora.setFont(new java.awt.Font("Berlin Sans FB Demi",0,110));
        			
        			lblClave.setForeground(Color.BLUE);
                    lblClave.setFont(new Font("Arial",0,12));
                    
                    lblFecha.setForeground(Color.BLUE);
                    lblFecha.setFont(new Font("Algerian",0,30));
                    
                    lblNota.setForeground(Color.BLUE);
                    lblNota.setFont(new Font("Arial",0,22));
                    
                    lblNota2.setForeground(Color.BLUE);
                    lblNota2.setFont(new Font("Arial",0,22));
                    
                    lblNombre.setFont(new Font("Monospaced",0,14));
                    lblEstablecimiento.setFont(new Font("Monospaced",0,14));
                    lblPuesto.setFont(new Font("Monospaced",0,14));
                    lblHorario.setFont(new Font("Monospaced",0,14));
                   
                   panel.add(lblClave).setBounds(45,y+25,z,20);
                   panel.add(txtClaveReal).setBounds(x*8,y+25,z-18,20);
                   panel.add(btnMaster).setBounds(x*8+(z-18),y+25,30,20);
                   
                   panel.add(trae_hora.lblHora).setBounds(x*24,y+20, z*5, 100);
                   panel.add(lblNota).setBounds(x*10,y+=205, 800, 40);
                   panel.add(lblNota2).setBounds(150,y+=30, 800, 40);
                   
                   panel.add(lblFecha).setBounds(910,176, 250, 30);
                   panel.add(lblSemaforoRojo).setBounds(35,80, 70, 70);
                   panel.add(lblSemaforoVerde).setBounds(115,80, 70, 70);
                   
                   panel.add(lblLogo).setBounds((1280/2)-60, 38, 127, 127);
                   panel.add(lblCerrar).setBounds(1280-89,38, 90, 130);
                   panel.add(btnFoto).setBounds(1280-505,38,118,128);
                   
                   panel.add(lblNombre).setBounds(900,38,290,20);
                   panel.add(lblEstablecimiento).setBounds(900,68,290,20);
                   panel.add(lblPuesto).setBounds(900,98,290,20);
                   panel.add(lblHorario).setBounds(900,128,290,20);
                   
                   panel.add(btnMenu).setBounds(15,180,180,20);
                   
                   panel.add(panelScroll).setBounds(19,y+62,733,429);
                   panel.add(fondo).setBounds(0,0,1280,768);
                   
                   iconoSemaforoR = new ImageIcon(tmpIconSemR.getImage().getScaledInstance(lblSemaforoRojo.getWidth(), lblSemaforoRojo.getHeight(), Image.SCALE_DEFAULT));
                   lblSemaforoRojo.setIcon(iconoSemaforoR);
                   
                   iconoSemaforoV = new ImageIcon(tmpIconSemV.getImage().getScaledInstance(lblSemaforoVerde.getWidth(), lblSemaforoVerde.getHeight(), Image.SCALE_DEFAULT));
                   lblSemaforoVerde.setIcon(iconoSemaforoV);
                   
                   iconoLogo = new ImageIcon(tmpIconLogo.getImage().getScaledInstance(lblLogo.getWidth(), lblLogo.getHeight(), Image.SCALE_DEFAULT));
                   lblLogo.setIcon(iconoLogo);        
           
                   iconoCerrar = new ImageIcon(tmpIconCerrar.getImage().getScaledInstance(lblCerrar.getWidth(), lblCerrar.getHeight(), Image.SCALE_DEFAULT));
                   lblCerrar.setIcon(iconoCerrar);
                 break;
    			}
    		}
    	}
    	
    	//--TODO(Captura De Huella Digital(inicio))	--------------------------------------------------------------------------------------------------------------------------------------------------------------------
    	public class Cat_Huellas_Personalizado extends JDialog{
    		
    		Container cont = getContentPane();
    		JLayeredPane panel = new JLayeredPane();
    		
    		RSProgressCircleAnimatedUno pca1 =new RSProgressCircleAnimatedUno();
    		
    		//Varible que permite iniciar el dispositivo de lector de huella conectado
    		// con sus distintos metodos.
    		private DPFPCapture Lector = DPFPGlobal.getCaptureFactory().createCapture();
    	//------------------------------------------------------------------------------------------------------------------------	
    		//Varible que permite establecer las capturas de la huellas, para determina sus caracteristicas
    		// y poder estimar la creacion de un template de la huella para luego poder guardarla
    		private DPFPEnrollment Reclutador = DPFPGlobal.getEnrollmentFactory().createEnrollment();
    		
    		//Variable que para crear el template de la huella luego de que se hallan creado las caracteriticas
    		// necesarias de la huella si no ha ocurrido ningun problema
    		private DPFPTemplate template;
    	//------------------------------------------------------------------------------------------------------------------------	
    		//Varible que permite establecer las capturas de la huellas, para determina sus caracteristicas
    		// y poder estimar la creacion de un template de la huella para luego poder guardarla
    		private DPFPEnrollment Reclutador2 = DPFPGlobal.getEnrollmentFactory().createEnrollment();
    		
    		//Variable que para crear el template de la huella luego de que se hallan creado las caracteriticas
    		// necesarias de la huella si no ha ocurrido ningun problema
    		private DPFPTemplate template2;
    	//------------------------------------------------------------------------------------------------------------------------	
    		public String TEMPLATE_PROPERTY = "template";
    			
    		//Esta variable tambien captura una huella del lector y crea sus caracteristcas para auntetificarla
    		// o verificarla con alguna guarda en la BD
    		private DPFPVerification Verificador = DPFPGlobal.getVerificationFactory().createVerification();
    		
    		public Cat_Huellas_Personalizado(String formaDeChecar) {
    			this.setModal(true);
    			
    			checoCon = formaDeChecar;
    			
    	        this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/encuesta.png"));
    			panel.setBorder(BorderFactory.createTitledBorder("huella"));
    			
    			this.setTitle("Verificar Huella");
    			this.setSize(270,270);
    			this.setResizable(false);
    			this.setLocationRelativeTo(null);
    			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    			
    			panel.add(pca1).setBounds(0, 20, 270, 200);
    			
    			pca1.setString("Ingresar Huella");
    			pca1.setIndeterminate(true);
    			 
    			cont.add(panel);
    			
    	        this.addWindowListener(new java.awt.event.WindowAdapter() {
    	            public void windowClosing(java.awt.event.WindowEvent evt) {
    	                formWindowClosing(evt);
    	            }
    	            public void windowOpened(java.awt.event.WindowEvent evt) {
    	                formWindowOpened(evt);
    	            }
    	        });
    		}

    		private void formWindowOpened(java.awt.event.WindowEvent evt){
    	        Iniciar();
    	        start();
    	    }

    	    private void formWindowClosing(java.awt.event.WindowEvent evt) {
    	    	btn_salir_huella = true;
    	    	System.out.println("cerar con btn Cerrar");
    	        stop();
    	    }
    	    
    	    public  void stop(){
    	        Lector.stopCapture();
    		}
    	    
    	    protected void Iniciar(){
    	    	   Lector.addDataListener(new DPFPDataAdapter() {
    		    	    @Override 
    		    	    public void dataAcquired(final DPFPDataEvent e) {
    			    	    SwingUtilities.invokeLater(new Runnable() {	
    			    	    	public void run() {
    			    	    		ProcesarCaptura(e.getSample());
    			    	    		
    			    	    		
    			    	    		
    			    	    		int folio = 0;
    			    	    		
    			    	    			
    			    	    			if(checoCon.equals("CLAVE MASTER")){
    			    	    				
    			    	    					String DatoCapturado = JOptionPane.showInputDialog("Folio Del Personal A Verificar");
    			    	    					
	    			    	    				if(validaDatoCapturado(DatoCapturado)){
	    			    	    					folio = Integer.valueOf(DatoCapturado);
	    			    	    				}else{
	    	    			    	    			System.out.println("error con el folio");
	    	    			    	    		}
	    			    	    				
        			    	    		}else{
        			    	    			folio = folio_empleado;
        			    	    		}
    			    	    			
    			    	    			
    			    	    			
    			    	    		
    			    	    		
    			    				  try {
    			    					verificarHuella(folio);
    			    				} catch (SQLException e1) {
    			    					e1.printStackTrace();
    			    				}
    			    				  Reclutador.clear();
    			    				  Reclutador2.clear();
    			    				  stop();
    			    				  dispose();
    			    	    	}
    				    	});
    		    	    }
    	    	   });
    	    	}
    	    public boolean validaDatoCapturado(String datoCapturado){
				try {
					Integer.valueOf(datoCapturado);
					return true;
				} catch (Exception e) {
					return false;
				}
    	    }
    	    
    	    public  void start(){
    	    	Lector.startCapture();
    	    }
    	    
    	    public DPFPFeatureSet featuresverificacion;
    	    public  void ProcesarCaptura(DPFPSample sample){
	    	     // Procesar la muestra de la huella y crear un conjunto de características con el propósito de verificacion.
	    	     featuresverificacion = extraerCaracteristicas(sample, DPFPDataPurpose.DATA_PURPOSE_VERIFICATION);
    	    }
    	    
      	    public  DPFPFeatureSet extraerCaracteristicas(DPFPSample sample, DPFPDataPurpose purpose){
    	        DPFPFeatureExtraction extractor = DPFPGlobal.getFeatureExtractionFactory().createFeatureExtraction();
    	        try {
    	         return extractor.createFeatureSet(sample, purpose);
    	        } catch (DPFPImageQualityException e) {
    	         return null;
    	        }
    	   }
    	    
    	    public DPFPTemplate getTemplate() {
    	        return template;
    	    }

    	    public void setTemplate(DPFPTemplate template) {
    	        DPFPTemplate old = this.template;
    	        this.template = template;
    	        firePropertyChange(TEMPLATE_PROPERTY, old, template);
    	    }
    	    
    	    public DPFPTemplate getTemplate2() {
    	        return template2;
    	    }
    	    
    	    public void setTemplate2(DPFPTemplate template) {
    	        DPFPTemplate old = this.template2;
    	        this.template2 = template;
    	        firePropertyChange(TEMPLATE_PROPERTY, old, template);
    	    }

    	    public  Image CrearImagenHuella(DPFPSample sample) {
    	    	return DPFPGlobal.getSampleConversionFactory().createImage(sample);
    	    }

    	     public void verificarHuella(int folio) throws SQLException {
    	    	 
    	    	 int folio_emp=0;
    	    	 byte[] muestra1 = null;
    	    	 byte[] muestra2 = null;
    	    	 String masterKey = "";
    	    	 if(checoCon.equals("CLAVE MASTER")){
    	    		 
    	    		 Object[][] datos = new BuscarSQL().Buscar_Huella(folio);
    	    		 	folio_emp = Integer.valueOf(datos[0][0].toString().trim());
	    	    		muestra1 = (byte[]) datos[0][1];
	    	    		muestra2 = (byte[]) datos[0][2];
	    	    		masterKey = datos[0][3].toString().trim();
	    	    		 
 	    		}else{
	 	    			muestra1 = checador.getHuella_1();
	 	    			muestra2 = checador.getHuella_2();
 	    		}
    	    	 
    	        //Lee la plantilla de la base de datos
    	        byte[] templateBuffer1 = muestra1;
    	        //Crea una nueva plantilla a partir de la guardada en la base de datos
    	        DPFPTemplate referenceTemplate1 = DPFPGlobal.getTemplateFactory().createTemplate(templateBuffer1);
    	        //Envia la plantilla creada al objeto contendor de Template del componente de huella digital
    	        setTemplate(referenceTemplate1);
    	        
    	        //Lee la plantilla de la base de datos
    	        byte[] templateBuffer2 = muestra2;
    	        //Crea una nueva plantilla a partir de la guardada en la base de datos
    	        DPFPTemplate referenceTemplate2 = DPFPGlobal.getTemplateFactory().createTemplate(templateBuffer2);
    	        //Envia la plantilla creada al objeto contendor de Template del componente de huella digital
    	        setTemplate2(referenceTemplate2);

    	        // Compara las caracteriticas de la huella recientemente capturda con la
    	        // plantilla guardada al usuario especifico en la base de datos
    	        DPFPVerificationResult result = Verificador.verify(featuresverificacion, getTemplate());
    	        DPFPVerificationResult result2 = Verificador.verify(featuresverificacion, getTemplate2());

    	        //compara las plantilas (actual vs bd)
    	        if (result.isVerified() || result2.isVerified()){
    	        	huellaAceptada=true;
    	        	 if(checoCon.equals("CLAVE MASTER")){
    	        		 txtClaveReal.setText(folio_emp+"C"+masterKey);
    	   	    		 	checar();
     	    		}
    	        	
    	        }
    	        else{
    	        	huellaAceptada=false;
    	        	if(checoCon.equals("CLAVE MASTER")){
    	        		JOptionPane.showMessageDialog(null, "La Huella No Fue Detectada O No Ha Sido Registrada","Error",JOptionPane.WARNING_MESSAGE);
                        return;
    	    		}
    	        	
    	        }
    	        System.out.println(huellaAceptada);
    	     }

    	}
    //--TODO(Captura De Huella Digital(fin))	--------------------------------------------------------------------------------------------------------------------------------------------------------------------
    	public static void main(String args[]){
    		try{
    			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    			new Cat_Checador().setVisible(true);
    		}catch(Exception e){
    			System.err.println("Error :"+ e.getMessage());
    		}
    	}
}