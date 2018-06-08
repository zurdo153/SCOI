package Cat_Lista_de_Raya;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.PrintJob;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.GrayFilter;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import Obj_Lista_de_Raya.Obj_Captura_Fuente_Sodas;
import Obj_Principal.Componentes;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Captura_De_Fuente_De_Sodas_De_Cajeras extends JFrame
{
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JPasswordField txtClaveCajero = new JPasswordField();
	
	JPasswordField txtClave = new JPasswordField();
	JTextField txtTicket = new JTextField();
	JTextField txtImporte = new Componentes().text(new JTextField (),"Importe Total del Ticket", 50, "Double");
	
	JPasswordField txtConfirmarCompra = new JPasswordField();
	
	JButton btnImprimir = new JButton("Imprimir autorizacion",new ImageIcon("imagen/Print.png"));
	JButton btnGuardar = new JButton("Guardar",new ImageIcon("imagen/Guardar.png"));
	JButton btnCancelar = new JButton("Deshacer",new ImageIcon("imagen/deshacer16.png"));
	
	JLabel lblNombre_Empleado = new JLabel();
	JLabel lblEstablecimiento_Empleado = new JLabel();
	JLabel lblPuesto_Empleado = new JLabel();
	JLabel lblFoto = new 	JLabel();
	
	JLabel lblCajero = new JLabel("Cajera(O):");
	JLabel lblUsuario = new JLabel();
	JLabel lblClaveCajero = new JLabel("Clave Cajero:");
	
	JLabel lblClave = new JLabel("Clave:");
	JLabel lblTicket = new JLabel("Ticket:");
	JLabel lblImporte = new JLabel("Importe:");
	JLabel lblConfirmarCompra = new JLabel("Confirmar:");
	
	JLabel lblNombre = new JLabel("Nombre:");
	JLabel lblEstablecimiento = new JLabel("Establecimiento:");
	JLabel lblPuesto = new JLabel("Puesto:");
	
	JLabel lblSaldo = new JLabel();
	JLabel Imgsigno = new JLabel("$");
	
	JLabel lblEnmarcadoSaldo = new JLabel();
	
    public static DefaultTableModel tabla_model = new DefaultTableModel(null,
            new String[]{"Tiket", "Importe", "Cajera(o)", "PC","Fecha"}){
                    
            @SuppressWarnings("rawtypes")
            Class[] types = new Class[]{
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
                    }
                     return false;
             }
    };
	
    static JTable tabla = new JTable(tabla_model);
    JScrollPane panelScroll = new JScrollPane(tabla);
	
	Date date = new Date();
	DateFormat df4 = DateFormat.getDateInstance(DateFormat.FULL);
	String fecha = df4.format(date);
	
	String claveGafete="";
	
	Border blackline;
	
	public void getContenedor()
	{
		init_tabla();
        this.setIconImage(Toolkit.getDefaultToolkit().getImage("imagen/fast-food-icon64.png"));
		this.setTitle("Captura de fuente de sodas");
		this.panel.setBorder(BorderFactory.createTitledBorder("Captura de fuente de sodas"));
		
		blackline = BorderFactory.createLineBorder(Color.blue);
		this.lblEnmarcadoSaldo.setBorder(BorderFactory.createTitledBorder(blackline,"Credito Disponible"));
		
		lblFoto.setBorder(LineBorder.createGrayLineBorder());
		Imgsigno.setFont(new Font("arial", Font.BOLD, 80));
		lblSaldo.setFont(new Font("arial", Font.BOLD, 80));
		
		lblClaveCajero.setFont(new Font("arial", Font.BOLD, 12));
		lblClave.setFont(new Font("arial", Font.BOLD, 12));
		lblTicket.setFont(new Font("arial", Font.BOLD, 12));
		lblImporte.setFont(new Font("arial", Font.BOLD, 12));
		lblConfirmarCompra.setFont(new Font("arial", Font.BOLD, 12));
		
		lblCajero.setFont(new Font("arial", Font.BOLD, 16));
		lblUsuario.setFont(new Font("arial", Font.BOLD, 16));
		lblNombre.setFont(new Font("arial", Font.BOLD, 16));
		lblNombre_Empleado.setFont(new Font("arial", Font.BOLD, 16));
		lblEstablecimiento.setFont(new Font("arial", Font.BOLD, 16));
		lblEstablecimiento_Empleado.setFont(new Font("arial", Font.BOLD, 16));
		lblPuesto.setFont(new Font("arial", Font.BOLD, 16));
		lblPuesto_Empleado.setFont(new Font("arial", Font.BOLD, 16));
		
		
		lblClaveCajero.setForeground(new java.awt.Color(105,105,105));
		lblClave.setForeground(new java.awt.Color(105,105,105));
		lblTicket.setForeground(new java.awt.Color(105,105,105));
		lblImporte.setForeground(new java.awt.Color(105,105,105));
		lblConfirmarCompra.setForeground(new java.awt.Color(105,105,105));
		
		lblCajero.setForeground(new java.awt.Color(105,105,105));
		lblUsuario.setForeground(new java.awt.Color(105,105,105));
		lblNombre.setForeground(new java.awt.Color(105,105,105));
		lblEstablecimiento.setForeground(new java.awt.Color(105,105,105));
		lblPuesto.setForeground(new java.awt.Color(105,105,105));
		Imgsigno.setForeground(new java.awt.Color(105,105,105));
		lblSaldo.setForeground(new java.awt.Color(105,105,105));
		
		panel.add(lblCajero).setBounds(20,20,80,20);
		panel.add(lblUsuario).setBounds(105,20,350,20);
		panel.add(lblClaveCajero).setBounds(20,45,100,20);
		panel.add(new JLabel(new ImageIcon("imagen/key-group-icone-5159-16.png"))).setBounds(100,45,20,20);
		panel.add(new JLabel(new ImageIcon("imagen/fast-food-icon72.png"))).setBounds(760,5,72,72);
		
		panel.add(txtClaveCajero).setBounds(120,45,110,20);
		
		panel.add(lblClave).setBounds(20,85,50,20);
		panel.add(new JLabel(new ImageIcon("imagen/Key.png"))).setBounds(100,85,20,20);
		panel.add(txtClave).setBounds(120,85,110,20);
		
		panel.add(lblTicket).setBounds(20,115,50,20);
		panel.add(new JLabel(new ImageIcon("imagen/Lista.png"))).setBounds(100,115,20,20);
		panel.add(txtTicket).setBounds(120,115,110,20);
		
		panel.add(lblImporte).setBounds(20,145,50,20);
		panel.add(new JLabel(new ImageIcon("imagen/Dollar.png"))).setBounds(100,145,20,20);
		panel.add(txtImporte).setBounds(120,145,110,20);
		
		panel.add(lblConfirmarCompra).setBounds(20,180,70,20);
		panel.add(new JLabel(new ImageIcon("imagen/Aplicar.png"))).setBounds(100,180,20,20);
		panel.add(txtConfirmarCompra).setBounds(120,180,110,20);
		
		panel.add(lblFoto).setBounds(235,85,170,170);
		
		panel.add(lblNombre).setBounds(420,80,70,20);
		panel.add(lblNombre_Empleado).setBounds(490,80,320,20);
		
		panel.add(lblEstablecimiento).setBounds(420,105,130,20);
		panel.add(lblEstablecimiento_Empleado).setBounds(550,105,280,20);
		
		panel.add(lblPuesto).setBounds(420,130,70,20);
		panel.add(lblPuesto_Empleado).setBounds(490,130,320,20);
		
		panel.add(lblEnmarcadoSaldo).setBounds(420,155,400,100);
		panel.add(Imgsigno).setBounds(430,170,80,80);
		panel.add(lblSaldo).setBounds(520,170,360,80);
		
		panel.add(btnImprimir).setBounds(20,210,210,20);
		panel.add(btnGuardar).setBounds(20,235,100,20);
		panel.add(btnCancelar).setBounds(130,235,100,20);
		
		panel.add(panelScroll).setBounds(20,260,800,300);
		
		ImageIcon tmpIconAux = new ImageIcon(System.getProperty("user.dir")+"/Iconos/Un.jpg");
	    lblFoto.setIcon(new ImageIcon(tmpIconAux.getImage().getScaledInstance(lblFoto.getWidth(),lblFoto.getHeight(), Image.SCALE_DEFAULT)));	
		
	    
	    txtClaveCajero.setEnabled(false);
		txtTicket.setEnabled(false);
		txtImporte.setEnabled(false);
		txtConfirmarCompra.setEnabled(false);
		btnGuardar.setEnabled(false);
		
		txtClaveCajero.addActionListener(claveCajero);
		txtImporte.addKeyListener(numerico_action_punto);
		txtClave.addActionListener(opClave);
		txtTicket.addActionListener(opTiket);
		txtImporte.addActionListener(importe);
		txtConfirmarCompra.addActionListener(guardar);
		btnGuardar.addActionListener(guardar);
		btnImprimir.addActionListener(opImprmiAutorizacion);
		btnCancelar.addActionListener(cancelar);
		
		this.setSize(850,610);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		cont.add(panel);
		CargarCajero();
		
        ///deshacer con escape
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
        getRootPane().getActionMap().put("escape", new AbstractAction(){
            public void actionPerformed(ActionEvent e)
            {                 	    btnCancelar.doClick();
          	    }
        });
	}
	
	public Cat_Captura_De_Fuente_De_Sodas_De_Cajeras(){
		getContenedor();
	}
	
	ActionListener claveCajero = new ActionListener() {
		@SuppressWarnings("deprecation")
		public void actionPerformed(ActionEvent arg0) 
		{
			if(txtClaveCajero.getText().toUpperCase().equals("")){
				JOptionPane.showMessageDialog(null, "Se Necesita Pasar El Gafete Del Cajero!!!","Aviso",JOptionPane.INFORMATION_MESSAGE, new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
				
				}else{
					try {
						if(new Obj_Captura_Fuente_Sodas().buscarcajero(txtClaveCajero.getText(),lblUsuario.getText().toUpperCase().trim())){
						txtClaveCajero.setText("");	
						txtClaveCajero.setEnabled(false);
						txtTicket.setEnabled(true);
						txtTicket.requestFocus();
						
						return;
						 }else{
							 txtClaveCajero.setText("");
								JOptionPane.showMessageDialog(null, "Clave Incorrecta Necesita Pasar El Gafete Del Cajero!!!","Aviso",JOptionPane.INFORMATION_MESSAGE, new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
								return;
						 }
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(null, "Error al Buscar el Cajero en Cat_Captura_De_Fuente_De_Sodas_Cajeras en la ActionListener claveCajero!!","Aviso",JOptionPane.ERROR_MESSAGE);
						e.printStackTrace();
					}

			}
		}
	};
	
	
	ActionListener opClave = new ActionListener() {
		@SuppressWarnings("deprecation")
		public void actionPerformed(ActionEvent arg0) 
		{
			if(txtClave.getText().length()!=0){
				
				Obj_Captura_Fuente_Sodas capturaFS = new Obj_Captura_Fuente_Sodas().buscar(txtClave.getText());
				
					if(txtClave.getText().toUpperCase().equals(capturaFS.getClave())){
						
						ImageIcon tmpIconAux = new ImageIcon(System.getProperty("user.dir")+"/tmp/tmp.jpg");
					    lblFoto.setIcon(new ImageIcon(tmpIconAux.getImage().getScaledInstance(lblFoto.getWidth(),lblFoto.getHeight(), Image.SCALE_DEFAULT)));	
						
						lblNombre_Empleado.setText(capturaFS.getEmpleado());
						lblEstablecimiento_Empleado.setText(capturaFS.getEstablecimiento());
						lblPuesto_Empleado.setText(capturaFS.getPuesto());
						lblSaldo.setText(capturaFS.getTotal()+"");
						
						try {
							String[][] tabla = new Obj_Captura_Fuente_Sodas().tabla(txtClave.getText());
										
							while(tabla_model.getRowCount()>0){
								tabla_model.removeRow(0);
							}
								
							for(int i=0; i<tabla.length; i++){
								 		Object[] dom = new Object[5];
								 		dom[0] = tabla[i][0]+"   ";
								 		dom[1] = tabla[i][1]+"   ";                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
								 		dom[2] = "   "+tabla[i][2];
								 		dom[3] = tabla[i][3];
								 		dom[4] = tabla[i][4]+"   ";
								 		
								 		tabla_model.addRow(dom);
							}
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						if(capturaFS.getTotal() <= 0){
							txtClave.setText("");
							txtClave.requestFocus();
							JOptionPane.showMessageDialog(null,"No cuenta con Saldo Suficiente","Aviso", JOptionPane.WARNING_MESSAGE);
							return;
						}else{
							txtClave.setEnabled(false);
							txtClaveCajero.setEnabled(true);
							txtClaveCajero.requestFocus();
					}
					}else{
						
						txtClave.setText("");
						txtClave.requestFocus();
						JOptionPane.showMessageDialog(null,"No Se Encontro Al Empleado O No Cuenta \n Con Fuente De Sodas Favor De Comunicarse \n A Desarrollo Humano","Aviso", JOptionPane.WARNING_MESSAGE, new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
						return;
					}
			}else{
				txtClave.setText("");
				txtClave.requestFocus();
				JOptionPane.showMessageDialog(null,"Pase el Gafete para Confirmar el Cargo al Empleado","Aviso", JOptionPane.WARNING_MESSAGE, new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}
		}
	};
	
	ActionListener opTiket = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			if(txtTicket.getText().length() != 0 ){
			try {
				if(new Obj_Captura_Fuente_Sodas().validarticket(lblUsuario.getText().toUpperCase().trim(), txtTicket.getText().toUpperCase().trim())){
					txtTicket.setEnabled(false);
					txtImporte.setEnabled(true);
					txtImporte.requestFocus();
					btnGuardar.setEnabled(true);
				 }else{
				JOptionPane.showMessageDialog(null,"El Folio del Ticket Tecleado Ya Existe ,Verifique El Folio ","Aviso", JOptionPane.WARNING_MESSAGE, new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
				}
			    } catch (SQLException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error al Buscar el ticket en Cat_Captura_De_Fuente_De_Sodas_Cajeras en la ActionListener opTiket!!","Aviso",JOptionPane.ERROR_MESSAGE);
			 };
			}else{
				JOptionPane.showMessageDialog(null,"ingrese codigo de tiket para registrar compra","Aviso", JOptionPane.WARNING_MESSAGE, new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}
		}
	};
	
	ActionListener opImprmiAutorizacion= new ActionListener(){
		@SuppressWarnings("deprecation")
		public void actionPerformed(ActionEvent e) {
			
			if(txtClave.getText().equals("")){
				txtClave.requestFocus();
				JOptionPane.showMessageDialog(null,"Pase El Gafete Para Obtener El Ultimo Ticket\nDel Empleado","Aviso", JOptionPane.WARNING_MESSAGE, new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
				
			}else{
				Obj_Captura_Fuente_Sodas capturaFS = new Obj_Captura_Fuente_Sodas().buscar(txtClave.getText());
				if(txtClave.getText().toUpperCase().trim().equals(capturaFS.getClave())){
					new Imprime_Ticket_Captura_Fuente_Sodas(txtClave.getText().toUpperCase()).setVisible(true);

				}else{
						txtClave.setText("");
						txtClave.requestFocus();
						JOptionPane.showMessageDialog(null,"El Empleado No Cuenta Con Fuente De Sodas","Aviso", JOptionPane.WARNING_MESSAGE, new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
						return;
				}
			}
		}
	};
	
	ActionListener importe = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) 
		{
			if(txtImporte.getText().equals("")){
				JOptionPane.showMessageDialog(null, "Ingrese Una Cantidad Para Continuar La Operacion!!!","Aviso",JOptionPane.INFORMATION_MESSAGE, new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}else{
				
				if(Float.parseFloat(txtImporte.getText())>Float.parseFloat(lblSaldo.getText())){
					JOptionPane.showMessageDialog(null, "No Cuenta Con El Saldo Suficiente !!!","Aviso",JOptionPane.INFORMATION_MESSAGE, new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
					return;
				}else{
					txtImporte.setEnabled(false);
					txtConfirmarCompra.setEnabled(true);
					txtConfirmarCompra.requestFocus();
				}
			}
		}
	};
	
	
	ActionListener guardar = new ActionListener() {
		@SuppressWarnings("deprecation")
		public void actionPerformed(ActionEvent arg0) 
		{
			if(txtConfirmarCompra.getText().equals("")){
				JOptionPane.showMessageDialog(null, "Pase El Gafete Del Empleado Para Confirmar La Compra!!!","Aviso",JOptionPane.INFORMATION_MESSAGE, new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}else{
				if(txtConfirmarCompra.getText().toUpperCase().equals(txtClave.getText().toUpperCase())){
					txtClave.setEnabled(false);
					Obj_Captura_Fuente_Sodas sodas = new Obj_Captura_Fuente_Sodas();
						sodas.setClave(txtClave.getText().toUpperCase().trim());
						sodas.setEstablecimiento(lblEstablecimiento_Empleado.getText());
						sodas.setPuesto(lblPuesto_Empleado.getText());
						sodas.setTicket(txtTicket.getText());
						sodas.setImporte(Float.parseFloat(txtImporte.getText()));
						sodas.setUsuario(lblUsuario.getText());
						if(sodas.Guardar()){
							 new Imprime_Ticket_Captura_Fuente_Sodas(txtClave.getText().toUpperCase()).setVisible(true);
					}else{
						JOptionPane.showMessageDialog(null, "La Clave No Coincide!!!","Aviso",JOptionPane.INFORMATION_MESSAGE);
						return;
					}
				}else{
					txtConfirmarCompra.setText("");
					JOptionPane.showMessageDialog(null, "No  Coincide La Clave Del Empleado\nPase El Gafete De Nuevo O Comuniquese\n A Desarrollo Humano","Aviso",JOptionPane.INFORMATION_MESSAGE, new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
					return;
				}
			}
		}
	};
	
	ActionListener cancelar = new ActionListener() {
		public void actionPerformed(ActionEvent e) 
		{
			txtClaveCajero.setText("");
			txtClaveCajero.setEnabled(false);
			txtClave.setText("");
			txtTicket.setText("");
			txtImporte.setText("");
			txtConfirmarCompra.setText("");
			
			lblFoto.setText("");
			lblNombre_Empleado.setText("");
			lblEstablecimiento_Empleado.setText("");
			lblPuesto_Empleado.setText("");
			lblSaldo.setText("");
			
			txtClave.setEnabled(true);
			txtTicket.setEnabled(false);
			txtImporte.setEnabled(false);
			txtConfirmarCompra.setEnabled(false);
			txtClave.requestFocus();
			
			ImageIcon tmpIconAux = new ImageIcon(System.getProperty("user.dir")+"/Iconos/Un.jpg");
		    lblFoto.setIcon(new ImageIcon(tmpIconAux.getImage().getScaledInstance(lblFoto.getWidth(),lblFoto.getHeight(), Image.SCALE_DEFAULT)));	
			
			    while(tabla.getRowCount()>0){
	                tabla_model.removeRow(0);
			    }
		}
	};
	
	public void agregar()
	{
		if(txtImporte.getText().length()!=0)
		{
			double variable;
			variable=Double.parseDouble(txtImporte.getText());
			lblSaldo.setText(variable+"");
			
			String[] row = new String[7];
			row[0]=" "+lblNombre_Empleado.getText().toUpperCase();
			row[1]=""+lblEstablecimiento_Empleado.getText().toUpperCase();
			row[2]=""+fecha;
			row[3]=lblUsuario.getText().toUpperCase();
			row[4]=txtTicket.getText().toUpperCase();
			row[5]=txtImporte.getText().toUpperCase();
			tabla_model.addRow(row);
		}else{
			JOptionPane.showMessageDialog(null,"El campo de texto importe est� vac�o","Aviso", JOptionPane.WARNING_MESSAGE, new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
			return;
		}
	}
	
	public void CargarCajero()
	{
		  File archivo = null;
 	      FileReader fr = null;
 	      BufferedReader br = null;
		 try {
 	         archivo = new File ("Config/users");
 	         fr = new FileReader (archivo);
 	         br = new BufferedReader(fr);
 	        
 	         br.readLine();
 	        lblUsuario.setText(br.readLine());
// 	         String linea;
 	         
// 	         while((linea=br.readLine())!=null)
// 	        	lblUsuario.setText(linea);
 	      }
 	      catch(Exception e){
 	         e.printStackTrace();
 	      }finally{
 	         try{                   
 	            if( null != fr ){  
 	               fr.close();    
 	            }                 
 	         }catch (Exception e2){
 	            e2.printStackTrace();
 	         }
 	      }
	}
	
    @SuppressWarnings({ "static-access" })
	public void init_tabla(){
    	this.tabla.getTableHeader().setReorderingAllowed(false) ;
    	
    	this.tabla.getColumnModel().getColumn(0).setMaxWidth(100);
    	this.tabla.getColumnModel().getColumn(0).setMinWidth(100);		
    	this.tabla.getColumnModel().getColumn(1).setMaxWidth(100);
    	this.tabla.getColumnModel().getColumn(1).setMinWidth(100);
    	this.tabla.getColumnModel().getColumn(2).setMaxWidth(320);
    	this.tabla.getColumnModel().getColumn(2).setMinWidth(320);
    	this.tabla.getColumnModel().getColumn(3).setMaxWidth(140);
    	this.tabla.getColumnModel().getColumn(3).setMinWidth(140);
    	this.tabla.getColumnModel().getColumn(4).setMaxWidth(140);
    	this.tabla.getColumnModel().getColumn(4).setMinWidth(140);		
    	tabla.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",10)); 
    	tabla.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12));
    	tabla.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",10)); 
    	tabla.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
    	tabla.getColumnModel().getColumn(4).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",10)); 
    }
 	
	
	KeyListener numerico_action_punto = new KeyListener() {
		public void keyTyped(KeyEvent e){
			char caracter = e.getKeyChar();

			 if(((caracter < '0') ||	
				    	(caracter > '9')) && 
				    	(caracter != '.' )){
				    	e.consume();
		    }
			 if (caracter==KeyEvent.VK_PERIOD){
			    	String texto = txtImporte.getText().toString();
					if (texto.indexOf(".")>-1) e.consume();
					
				}
		}
		public void keyReleased(KeyEvent e) {	
		}
		public void keyPressed(KeyEvent e) {}
	};
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Captura_De_Fuente_De_Sodas_De_Cajeras().setVisible(true);
		}catch(Exception e){}
	}

	public class Imprime_Ticket_Captura_Fuente_Sodas extends JFrame
	{
		Container container = getContentPane();
		JLayeredPane panel2 = new JLayeredPane();
		
	//Declarar Imagen para Txa	
		ImageIcon img = new ImageIcon("imagen/fuenteSodasTicket.png");
		
		JScrollPane jScrollPane1 = new JScrollPane();
		JButton jButImprime = new JButton("Imprimir");
		
		private JTextArea jTextArea1=new JTextArea(){
			
			Image image = img.getImage();
			
			Image grayImage = GrayFilter.createDisabledImage(image);{
				setOpaque(false);
			}
			
			public void paint(Graphics g){
					g.drawImage(grayImage,0,0,this);
					super.paint(g);
			}
		};
//variables para ticket -----------------------------------------------------------------------------------
		String usuario= "Cajera(o): ";
		String fecha ="Fecha: ";
		String lblEmpleado="Empleado: ";
		
		String establecimiento="Establecimiento: ";
		String puesto="Puesto: ";
		String ticket="Ticket: ";
		String importe="Importe: $";
		String linea = "_____________________________________________________";
		String firma=" Firma: (  ";
//-----------------------------------------------------------------------------------------------------------

		@SuppressWarnings("deprecation")
		public Imprime_Ticket_Captura_Fuente_Sodas(String pass)
		{
			this.setTitle("Imprimir Ticket");
			Font font = new Font("ARIAL",Font.PLAIN,8);
			jTextArea1.setFont(font);
			
			Obj_Captura_Fuente_Sodas ultimiTicket = new Obj_Captura_Fuente_Sodas().buscar_ultimo_ticket(txtClave.getText().toUpperCase());
			
			usuario=		usuario+ultimiTicket.getUsuario();
			fecha= 			fecha+ultimiTicket.getFecha();
			lblEmpleado=	lblEmpleado+ultimiTicket.getEmpleado();
			establecimiento=establecimiento+ultimiTicket.getEstablecimiento();
			puesto=			puesto+ultimiTicket.getPuesto();
			ticket=			ticket+ultimiTicket.getTicket();
			importe=		importe+ultimiTicket.getImporte();
			firma=			firma+ultimiTicket.getEmpleado()+"  )";
			
			panel2.add(jButImprime).setBounds(134,10,100,20);
			panel2.add(jTextArea1).setBounds(14,50,210,310);
			
			jTextArea1.setText(
        		new String ("\n\n\n\n\n\n\n"+usuario
        				+"\n\n                                       "//espacio para acomodar fecha
        				+"                   "+fecha+"\n\n"
						+lblEmpleado+"\n\n"+establecimiento+"\n\n"
						+puesto+"\n\n"+ticket+"\n\n"+importe+"\n\n\n\n"
						+linea+"\n"+firma+"\n\n\n\n.")
	        );
			
			jTextArea1.setEditable(false);
			
			jButImprime.requestFocus();
			jButImprime.addActionListener(opImprimir);
			
			this.setSize(260, 400);
			this.setLocationRelativeTo(null);
			container.add(panel2);
			txtConfirmarCompra.setEnabled(false);
			txtClave.setText("");
			txtTicket.setText("");
			txtImporte.setText("");
			txtConfirmarCompra.setText("");
			
			lblFoto.setText("");
			lblNombre_Empleado.setText("");
			lblEstablecimiento_Empleado.setText("");
			lblPuesto_Empleado.setText("");
			lblSaldo.setText("");
			
			txtClave.setEnabled(true);
			txtTicket.setEnabled(false);
			txtImporte.setEnabled(false);
			txtConfirmarCompra.setEnabled(false);
			txtClave.requestFocus();
			btnGuardar.setEnabled(false);
			
			ImageIcon tmpIconAux = new ImageIcon(System.getProperty("user.dir")+"/Iconos/Un.jpg");
		    lblFoto.setIcon(new ImageIcon(tmpIconAux.getImage().getScaledInstance(lblFoto.getWidth(),lblFoto.getHeight(), Image.SCALE_DEFAULT)));	
			
			    while(tabla.getRowCount()>0){
	                tabla_model.removeRow(0);}
			
		}
		
		ActionListener opImprimir = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					imprimir();
					imprimir();
					dispose();
					btnCancelar.doClick();
			}
		};
		
		public void imprimir() {
			
			PrintJob print = Toolkit.getDefaultToolkit().getPrintJob(this, "", null); 
					try { 
						Graphics g = print.getGraphics(); 
						jTextArea1.print(g); 
						print.end();
						g.dispose();
					}catch(Exception e) {
						System.out.println("LA IMPRESION HA SIDO CANCELADA..."); 
					} 
		}
	}
}