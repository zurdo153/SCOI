package Cat_Compras;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;

import Obj_Checador.Obj_Entosal;
import Obj_Compras.Obj_Registrar_Zona_Completada;
import Obj_Lista_de_Raya.Obj_Empleados;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;

@SuppressWarnings("serial")
public class Cat_Registrar_Zona_Completada extends JDialog{
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JTextField txtCodigoBarras = new Componentes().text(new JCTextField(), "Codigo De Barras", 20, "String");
	JTextField txtPedido = new Componentes().text(new JCTextField(), "Folio De Pedido", 20, "String");
	JTextField txtZona = new Componentes().text(new JCTextField(), "Zona", 20, "String");
	JTextField txtFolioEmpAsignado = new Componentes().text(new JCTextField(), "Folio", 20, "String");
	JTextField txtEmpAsignado = new Componentes().text(new JCTextField(), "Nombre De Surtidor", 20, "String");
	JTextField txtStatus = new Componentes().text(new JCTextField(), "Status", 20, "String");

	JPasswordField PswGafeteEncargado = new JPasswordField();
	
	JCButton btnDeshacer = new JCButton("Deshacer", "deshacer.png", "Azul");

	public Cat_Registrar_Zona_Completada() {
		this.setModal(true);
		this.setTitle("Entrega De Surtido Por Zona");
		
		int x=10,y=15,ancho=110;
		panel.add(new JLabel("CODIGO DE ENTREGA:")).setBounds(x, y, ancho+20, 20);
		panel.add(txtCodigoBarras).setBounds(x+ancho+20, y, ancho, 20);
		panel.add(btnDeshacer).setBounds(x+(ancho*3)+20, y, ancho, 20);
		
		panel.add(new JLabel("FOLIO PEDIDO:")).setBounds(x, y+=25, ancho, 20);
		panel.add(txtPedido).setBounds(x+ancho+20, y, ancho, 20);
		
		panel.add(new JLabel("ZONA:")).setBounds(x+(ancho*3)-30, y, ancho, 20);
		panel.add(txtZona).setBounds(x+(ancho*3)+20, y, ancho, 20);
		
		panel.add(new JLabel("SURTIDOR:")).setBounds(x, y+=25, ancho, 20);
		panel.add(txtFolioEmpAsignado).setBounds(x+ancho+20, y, ancho/2, 20);
		panel.add(txtEmpAsignado).setBounds(x+(ancho*2)-30, y, (ancho*2)+50, 20);
		
		panel.add(new JLabel("GAFETE ENCARGADO:")).setBounds(x, y+=25, ancho+30, 20);
		panel.add(PswGafeteEncargado).setBounds(x+ancho+20, y, ancho+20, 20);
		
		panel.add(new JLabel("Status:")).setBounds(x+(ancho*3)-30, y, ancho, 20);
		panel.add(txtStatus).setBounds(x+(ancho*3)+20, y, ancho, 20);
		
		cont.add(panel);
		
		txtPedido.setEditable(false);
		txtZona.setEditable(false);
		txtFolioEmpAsignado.setEditable(false);
		txtEmpAsignado.setEditable(false);
		PswGafeteEncargado.setEditable(false);
		txtStatus.setEditable(false);
		
		txtCodigoBarras.addKeyListener(opKey);
		btnDeshacer.addActionListener(opDeshacer);
		PswGafeteEncargado.addActionListener(opRecibirPedido);
		
		this.setSize(490,150);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
	}
	
	ActionListener opDeshacer = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			
			limpiar();
		}
	};
	
	public void limpiar(){
		txtCodigoBarras.setText("");
		txtPedido.setText("");
		txtZona.setText("");
		txtFolioEmpAsignado.setText("");
		txtEmpAsignado.setText("");
		PswGafeteEncargado.setText("");
		txtStatus.setText("");
		
		txtCodigoBarras.setEditable(true);
		PswGafeteEncargado.setEditable(false);
		txtCodigoBarras.requestFocus();
	}
	
	KeyListener opKey = new KeyListener() {
		public void keyTyped(KeyEvent e) {}
		public void keyReleased(KeyEvent e) {}
		public void keyPressed(KeyEvent e) {

			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				
				String codigo = txtCodigoBarras.getText().toString().toUpperCase().trim();

//				System.out.println("951zdsrZ3XS211367".indexOf("Z"));
//				System.out.println(codigo.indexOf("Z"));
//				System.out.println(codigo.indexOf("Z")+2);
//				System.out.println(codigo.indexOf("X"));
//				System.out.println(codigo.indexOf("X")-codigo.indexOf("Z"));
//				System.out.println(isInteget(codigo.substring(0, codigo.indexOf("Z"))));//identificar si el folio del surtidor es entero
				
				int inicio_De_X_respectoA_Z = codigo.indexOf("X")-codigo.indexOf("Z");
				
					if(codigo.indexOf("Z")> 0 && codigo.indexOf("X") == codigo.indexOf("Z")+inicio_De_X_respectoA_Z && isInteget(codigo.substring(0, codigo.indexOf("Z")))){
						
						Obj_Registrar_Zona_Completada buscar = new Obj_Registrar_Zona_Completada().buscar(txtCodigoBarras.getText().toString().toUpperCase().trim());
						
						if(buscar.getStatus().toString().trim().equals("ASIGNADO")){
							txtPedido.setText(buscar.getFolio_pedido());
							txtFolioEmpAsignado.setText(buscar.getFolio_surtidor()==0?"":buscar.getFolio_surtidor()+"");
							txtEmpAsignado.setText(buscar.getNombre_surtidor());
							txtZona.setText(buscar.getZona());
							txtStatus.setText(buscar.getStatus());
							
							txtCodigoBarras.setEditable(false);
							PswGafeteEncargado.setEditable(true);
							PswGafeteEncargado.requestFocus();
						}else{
							
							String aviso = buscar.getStatus().equals("VIGENTE")?"El Pedido No Se Encuentra Asignado A Ningun Surtidor":(
												buscar.getStatus().equals("SURTIDO")?"El Pedido Ya Fue Surtido":(
														buscar.getStatus().equals("CANCELADO")?"El Pedido Se Encuentra Con Status Cancelado":(
																buscar.getStatus().equals("NEGADO")?"El Pedido Se Encuentra Con Status Negado":(
																		buscar.getStatus().equals("RECIBIDO")?"El Pedido Ya Fue Recibido":"No Se Encontraron Registro Con El Codigo Proporcionado"))));
									
							JOptionPane.showMessageDialog(null, aviso, "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
							return;
						}
				}else{
					JOptionPane.showMessageDialog(null, "El Codigo Ingresado Tiene Formato Incorrecto", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
					return;
				}
				
			}
		
		}
	};
	
	public boolean isInteget(String Cadena){
		boolean esEntero = false;
		try{
			Integer.valueOf(Cadena);
			esEntero=true;
		}catch(Exception e){
			
		}
		return esEntero;
	}
	
    int folio_empleado_recibe;
    String claveMaster;	
	ActionListener opRecibirPedido = new ActionListener() {
		@SuppressWarnings("deprecation")
		public void actionPerformed(ActionEvent e) {
			
				if(!PswGafeteEncargado.getText().toUpperCase().equals("")){
					 
					String codigoBarrar = PswGafeteEncargado.getText().toUpperCase().trim();
					 
				 	int posicionC = codigoBarrar.indexOf('C');
				 	
				 	if(posicionC>0){
					
				 		if(isNumeric(codigoBarrar.substring(0, posicionC))){
							
				 			folio_empleado_recibe = Integer.parseInt(codigoBarrar.substring(0, posicionC));
							claveMaster = codigoBarrar.substring(posicionC+1,codigoBarrar.length());
							
							Obj_Empleados re = new Obj_Empleados().buscar(folio_empleado_recibe);  //busca a empleado chofer 
	                        Obj_Entosal entosalClave = new Obj_Entosal().buscar(); //busca clave maestra
	                 
		                        if(re.getFolio()==folio_empleado_recibe){
		                        	
	                           		switch (re.getStatus()){
	                                          case 4:	JOptionPane.showMessageDialog(null, "No puedes Realizar Movimientos, tu estatus es de baja\nfavor de comunicarte a desarrollo humano,\npara que puedas registrar tu entrada a trabajar,\nde lo contrario no te sera valido el pago de este dia","Aviso",JOptionPane.WARNING_MESSAGE);
	                                          			PswGafeteEncargado.setText("");
				                                        PswGafeteEncargado.requestFocus();
	                                          break;
	                                          case 5:	JOptionPane.showMessageDialog(null, "No puedes Realizar Movimientos, tu estatus es de baja\nfavor de comunicarte a desarrollo humano,\npara que puedas registrar tu entrada a trabajar,\nde lo contrario no te sera valido el pago de este dia","Aviso",JOptionPane.WARNING_MESSAGE);
				                                        PswGafeteEncargado.setText("");
				                                        PswGafeteEncargado.requestFocus();
	                                          break;
	                                          case 7:	JOptionPane.showMessageDialog(null, "No puedes Realizar Movimientos, tu estatus es de baja\nfavor de comunicarte a desarrollo humano,\npara que puedas registrar tu entrada a trabajar,\nde lo contrario no te sera valido el pago de este dia","Aviso",JOptionPane.WARNING_MESSAGE);
	                                          			PswGafeteEncargado.setText("");
				                                        PswGafeteEncargado.requestFocus();
		                             		  break;
		                             		  default: if(re.getNo_checador().equals(codigoBarrar)){
		                             			  				recibirPedido();
			                                          	 }else{
			                                          		 if(entosalClave.getClave().equals(claveMaster)){
			                                          			 	recibirPedido();
			                                          		 }else{
			                                          			 
				                    				     		     JOptionPane.showMessageDialog(null, "La Clave Ingresada No Corresponde A Ningun Trabajador ","Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				                    				     		     PswGafeteEncargado.setText("");
				                    				     		     PswGafeteEncargado.requestFocus();
			                                                         return;
			                                          		 }
			                                          	 }
			                                  break;  
	                                };
	                       
							}else{
			     		  		 JOptionPane.showMessageDialog(null, "La Clave Ingresada No Corresponde A Ningun Trabajador > ","Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
			     		  		 PswGafeteEncargado.setText("");
			     		  		 PswGafeteEncargado.requestFocus();
		                         return;
							}
		                        
				 	}else{
				 			PswGafeteEncargado.setText("");
					 		PswGafeteEncargado.requestFocus();
					  		JOptionPane.showMessageDialog(null, "La Clave Ingresada No Corresponde A Ningun Trabajador >>","Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
			                return;
		                }
					
				 	}else{
				 		PswGafeteEncargado.setText("");
				 		PswGafeteEncargado.requestFocus();
				  		JOptionPane.showMessageDialog(null, "La Clave Ingresada No Corresponde A Ningun Trabajador >>>","Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
			            return;
			 		}
	               
				 }else{
					 PswGafeteEncargado.setText("");
					 PswGafeteEncargado.requestFocus();
					JOptionPane.showMessageDialog(null, "Es Necesario Pasar El Gafete Por El Lector","Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));                  
					return;
				 }
		}
	};
	
	public void recibirPedido(){
		Obj_Registrar_Zona_Completada recibir = new Obj_Registrar_Zona_Completada();
		
		recibir.setFolio_pedido(txtPedido.getText().trim());
		recibir.setFolio_surtidor(Integer.valueOf(txtFolioEmpAsignado.getText().trim()));
		recibir.setZona(txtZona.getText().trim());
		recibir.setStatus(txtStatus.getText().trim());
		recibir.setFolio_emp_recibe(folio_empleado_recibe);
		
		if(recibir.guardar()){
			limpiar();
			JOptionPane.showMessageDialog(null, "El Registro Se Guardó Exitosamente!" , "Exito al Guardar!", JOptionPane.INFORMATION_MESSAGE);
			return;
		}else{
			JOptionPane.showMessageDialog(null, "No Se Pudo Recibir El Pedido "+txtPedido.getText().trim(), "Aviso !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
			return;
		}
		
	}
	
	private boolean isNumeric(String cadena){
    	try {
    		Integer.parseInt(cadena);
    		return true;
    	} catch (NumberFormatException nfe){
    		return false;
    	}
    }
	
	public static void main(String [] args){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Registrar_Zona_Completada().setVisible(true);
		}catch(Exception e){
			System.err.println("Error :"+ e.getMessage());
		}
	}

}
