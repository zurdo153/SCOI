package Cat_Administracion_del_Sistema;

import java.awt.Color;
import java.awt.Container;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;

import Conexiones_SQL.Connexion;
import Obj_Administracion_del_Sistema.Obj_Configuracion_Base_de_Datos;
import Obj_Administracion_del_Sistema.Obj_Configuracion_Base_de_Datos_2;

@SuppressWarnings("serial")
public class Cat_Configuracion_Base_de_Datos extends JFrame{

	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JLabel lblFondo = new JLabel();
	Icon iconoFondo;
    String fileFondo2 = "Imagen/fondoBD.jpg";
    ImageIcon tmpIconAuxFondo;
	
//	SCOI---------------------------------------------------
	JLabel lblBDSCOI = new JLabel();
	JTextField txtIp = new JTextField();
	JTextField txtPuerto = new JTextField();
	JTextField txtNombreBD = new JTextField();
	JTextField txtUsuario = new JTextField();
	JPasswordField txtContrasena = new JPasswordField();
	
	JButton btnEditar = new JButton("Editar");
	JButton btnTest = new JButton("Probar");
	JButton btnGuardar = new JButton("Guardar");
	
//	BD2----------------------------------------------------
	JLabel lblBD2 = new JLabel();
	JTextField txtIp2 = new JTextField();
	JTextField txtPuerto2 = new JTextField();
	JTextField txtNombreBD2 = new JTextField();
	JTextField txtUsuario2 = new JTextField();
	JPasswordField txtContrasena2 = new JPasswordField();
	
	JButton btnEditar2 = new JButton("Editar");
	JButton btnTest2 = new JButton("Probar");
	JButton btnGuardar2 = new JButton("Guardar");
	
	
	
	
	public Cat_Configuracion_Base_de_Datos(){
		this.setTitle("Configuracion de Base de Datos");
		tmpIconAuxFondo = new ImageIcon(fileFondo2);
        iconoFondo = new ImageIcon(tmpIconAuxFondo.getImage().getScaledInstance(555,230, Image.SCALE_DEFAULT));
        lblFondo.setIcon(iconoFondo);
        
    	lblBD2        .setForeground(Color.WHITE);
        
		int x = 20, y=30, ancho=85;
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("imagen/Database.png"));
		lblBDSCOI.setBorder(BorderFactory.createTitledBorder("Configuracion de Base de Datos SCOI"));
		lblBD2.setBorder(BorderFactory.createTitledBorder("Configuracion de Base de Datos 2"));
//		panel.setBorder(BorderFactory.createTitledBorder("Configuracion de Base de Datos"));
			
//		txtIp.setDocument(new JTextFieldLimit(9));
//		txtNombre_Completo.setDocument(new JTextFieldLimit(53));
//		txtContrasena.setDocument(new JTextFieldLimit(33));
//		txtContrasena1.setDocument(new JTextFieldLimit(33));
		
		panel.add(lblBDSCOI).setBounds(0,0,ancho*3+20,200);                                                 panel.add(lblBD2).setBounds(275,0,ancho*3+20,200);                   
		panel.add(new JLabel("Puerto:")).setBounds(x,y,ancho,20);                                           panel.add(new JLabel("Puerto:")).setBounds(x+275,y,ancho,20);             
		panel.add(txtPuerto).setBounds(x+ancho-10,y,50,20);                                                 panel.add(txtPuerto2).setBounds(x+ancho+265,y,50,20);                   
		                                                                                                                                                                          
		panel.add(new JLabel("Dirección IPv4:")).setBounds(x,y+=25,ancho,20);                               panel.add(new JLabel("Dirección IPv4:")).setBounds(x+275,y,ancho,20); 
		panel.add(txtIp).setBounds(x+ancho-10,y,ancho*2,20);                                                panel.add(txtIp2).setBounds(x+ancho+265,y,ancho*2,20);                  
		                                                                                                                                                                          
		panel.add(new JLabel("Nombre BD:")).setBounds(x,y+=25,ancho,20);                                    panel.add(new JLabel("Nombre BD:")).setBounds(x+275,y,ancho,20);      
		panel.add(txtNombreBD).setBounds(x+ancho-10,y,ancho*2,20);                                          panel.add(txtNombreBD2).setBounds(x+ancho+265,y,ancho*2,20);            
		                                                                                                                                                                          
		panel.add(new JLabel("Usuario:")).setBounds(x,y+=25,ancho,20);                                      panel.add(new JLabel("Usuario:")).setBounds(x+275,y,ancho,20);        
		panel.add(txtUsuario).setBounds(x+ancho-10,y,ancho*2,20);                                           panel.add(txtUsuario2).setBounds(x+ancho+265,y,ancho*2,20);             
		                                                                                                                                                                          
		panel.add(new JLabel("Contraseña:")).setBounds(x,y+=25,ancho,20);                                   panel.add(new JLabel("Contraseña:")).setBounds(x+275,y,ancho,20);     
		panel.add(txtContrasena).setBounds(x+ancho-10,y,ancho*2,20);                                        panel.add(txtContrasena2).setBounds(x+ancho+265,y,ancho*2,20);          
		                                                                                                                                                                          
		panel.add(btnEditar).setBounds(x,y+=30,ancho-10,20);                                                 panel.add(btnEditar2).setBounds(x+275,y,ancho-10,20);                   
		panel.add(btnTest).setBounds((x*5),y,ancho-10,20);                                            		panel.add(btnTest2).setBounds((x*5)+275,y,ancho-10,20);                  
		panel.add(btnGuardar).setBounds((x*8)+20,y,ancho-10,20);                                            panel.add(btnGuardar2).setBounds((x*8)+295,y,ancho-10,20);              
		                                                                                                   
		panel.add(lblFondo).setBounds(-1,-10,555,230);                                                     
		                                                                                                   
		btnEditar.addActionListener(editar);                                                                btnEditar2.addActionListener(editar2);  
		btnGuardar.addActionListener(guardarBDSCOI);                                                        btnGuardar2.addActionListener(guardarBD2);
		btnTest.addActionListener(opTest);                                                                 btnTest2.addActionListener(opTest2);    
		                                                                                                   
		txtPuerto.setText("1433");                                                                          txtPuerto2.setText("1433");                                                 
		File archivo = new File(System.getProperty("user.dir")+"\\Config\\config");                         File archivo2 = new File(System.getProperty("user.dir")+"\\Config\\config2");
		
		if(archivo.exists()){                                                                               if(archivo2.exists()){                                                                       
			Obj_Configuracion_Base_de_Datos config = new Obj_Configuracion_Base_de_Datos().buscar();        	Obj_Configuracion_Base_de_Datos_2 config2 = new Obj_Configuracion_Base_de_Datos_2().buscar();
			                                                                                                	                                                                                        
			txtIp.setText(config.getDireccionIPV4());                                                       	txtIp2.setText(config2.getDireccionIPV4());                                               
			txtNombreBD.setText(config.getNombreBD());                                                      	txtNombreBD2.setText(config2.getNombreBD());                                              
			txtUsuario.setText(config.getUsuario());                                                        	txtUsuario2.setText(config2.getUsuario());                                                
			txtContrasena.setText(config.getContrasena());                                                  	txtContrasena2.setText(config2.getContrasena());                                          
		                                                                                                                                                                                                
		}		                                                                                            }		                                                                                    
		txtPuerto.setEditable(false);                                                                       txtPuerto2.setEditable(false);                                                               
		panelEnabledFalse();                                                                                panelEnabledFalse2();                                                                        
		
		cont.add(panel);
		this.setSize(555,230);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	ActionListener opTest = new ActionListener(){
		@SuppressWarnings("deprecation")
		public void actionPerformed(ActionEvent e){
			new Connexion().probar_conexion(txtIp.getText(),txtNombreBD.getText(),txtUsuario.getText(),txtContrasena.getText());
		}
	};
	
	ActionListener opTest2 = new ActionListener(){
		@SuppressWarnings("deprecation")
		public void actionPerformed(ActionEvent e){
			new Connexion().probar_conexion(txtIp2.getText(),txtNombreBD2.getText(),txtUsuario2.getText(),txtContrasena2.getText());
		}
	};
	
	ActionListener guardarBDSCOI = new ActionListener(){
		@SuppressWarnings("deprecation")
		public void actionPerformed(ActionEvent e){
			if(validaCampos()!="") {
				JOptionPane.showMessageDialog(null, "los siguientes campos son requeridos:\n"+validaCampos(), "Error al guardar registro", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
				return;
			}else{
				Obj_Configuracion_Base_de_Datos con = new Obj_Configuracion_Base_de_Datos();
				
				con.setDireccionIPV4(txtIp.getText());
				con.setNombreBD(txtNombreBD.getText());
				con.setUsuario(txtUsuario.getText());
				con.setContrasena(txtContrasena.getText());
				
				if(con.guardar()){
					panelEnabledFalse();
				}
				
				panelEnabledFalse();
				JOptionPane.showMessageDialog(null,"El registró se almacenó de forma segura","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//Exito.png"));
						
			}
		}
	};
	
	ActionListener guardarBD2 = new ActionListener(){
		@SuppressWarnings("deprecation")
		public void actionPerformed(ActionEvent e){
			if(validaCampos2()!="") {
				JOptionPane.showMessageDialog(null, "los siguientes campos son requeridos:\n"+validaCampos2(), "Error al guardar registro", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
				return;
			}else{
				Obj_Configuracion_Base_de_Datos_2 con = new Obj_Configuracion_Base_de_Datos_2();
				
				con.setDireccionIPV4(txtIp2.getText());
				con.setNombreBD(txtNombreBD2.getText());
				con.setUsuario(txtUsuario2.getText());
				con.setContrasena(txtContrasena2.getText());
				
				if(con.guardar()){
					panelEnabledFalse2();
				}
				
				panelEnabledFalse();
				JOptionPane.showMessageDialog(null,"El registró se almacenó de forma segura","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//Exito.png"));
						
			}
		}
	};
	
	ActionListener editar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			panelEnabledTrue();
			txtIp.requestFocus();
		}		
	};
	
	ActionListener editar2 = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			panelEnabledTrue2();
			txtIp2.requestFocus();
		}		
	};
	
	KeyListener numerico_action = new KeyListener() {
		@Override
		public void keyTyped(KeyEvent e) {
			char caracter = e.getKeyChar();

		   if(((caracter < '0') ||
		        (caracter > '9')) &&
		        (caracter != KeyEvent.VK_BACK_SPACE)){
		    	e.consume(); 
		    }			
		}
		@Override
		public void keyPressed(KeyEvent e){}
		@Override
		public void keyReleased(KeyEvent e){}
								
	};
	
	@SuppressWarnings("deprecation")
	private String validaCampos(){
		String error="";
		
		if(txtIp.getText().equals(""))error+= "Direción IPv4\n";
		if(txtNombreBD.getText().equals(""))error+= "Nombre de la BD\n";
		if(txtContrasena.getText().equals(""))error+= "Nombre de Usuario\n";
		if(txtUsuario.getText().equals(""))error+= "Contraseña\n";
				
		return error;
	}
	
	@SuppressWarnings("deprecation")
	private String validaCampos2(){
		String error="";
		
		if(txtIp.getText().equals(""))error+= "Direción IPv4\n";
		if(txtNombreBD.getText().equals(""))error+= "Nombre de la BD\n";
		if(txtContrasena.getText().equals(""))error+= "Nombre de Usuario\n";
		if(txtUsuario.getText().equals(""))error+= "Contraseña\n";
				
		return error;
	}
	
	ActionListener salir = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			dispose();
		}
	};
	
	public void panelEnabledFalse(){	
		txtIp.setEditable(false);
		txtNombreBD.setEditable(false);
		txtUsuario.setEditable(false);
		txtContrasena.setEditable(false);
	}		
	
	public void panelEnabledTrue(){	
		txtIp.setEditable(true);
		txtNombreBD.setEditable(true);
		txtUsuario.setEditable(true);
		txtContrasena.setEditable(true);
	}
	
	public void panelEnabledFalse2(){	 
		txtIp2.setEditable(false);        
		txtNombreBD2.setEditable(false);  
		txtUsuario2.setEditable(false);   
		txtContrasena2.setEditable(false);
	}		                             
	                                     
	public void panelEnabledTrue2(){	     
		txtIp2.setEditable(true);         
		txtNombreBD2.setEditable(true);   
		txtUsuario2.setEditable(true);    
		txtContrasena2.setEditable(true); 
	}                                    
	
	public static void main(String [] arg){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Configuracion_Base_de_Datos().setVisible(true);
		}catch(Exception e){	}

	}
	
}
