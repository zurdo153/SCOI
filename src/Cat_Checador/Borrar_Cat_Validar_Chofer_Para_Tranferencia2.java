package Cat_Checador;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.sun.glass.events.KeyEvent;

import Obj_Checador.Obj_Chacador_De_Embarque_De_Pedidos;
import Obj_Checador.Obj_Entosal;
import Obj_Lista_de_Raya.Obj_Empleados;
import Obj_Principal.Componentes;
import Obj_Principal.JCTextField;

@SuppressWarnings("serial")
public class Borrar_Cat_Validar_Chofer_Para_Tranferencia2 extends JDialog  {
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JTextField txtNoCarro = new Componentes().text(new JCTextField(), "", 3, "Int");
	JTextField txtNoCincho = new Componentes().text(new JCTextField(), "", 20, "String");
	JPasswordField pwfGafeteChofer = new JPasswordField();

	Object[] trasferencias=null;
	String estab_surte = "";
	String estab_recibe = "";
	int folio_encargado = 0;
	
	public Borrar_Cat_Validar_Chofer_Para_Tranferencia2(Object[] trasferencias,String estab_surte,String estab_recibe,int folio_encargado){
		this.setModal(true);
		this.setSize(360, 145);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		
		this.setTitle("Programar Tranferencia");
		
		this.trasferencias = trasferencias;
		this.estab_surte = estab_surte;
		this.estab_recibe = estab_recibe;
		this.folio_encargado = folio_encargado;
		
		panel.add(new JLabel("No. De Carro:")).setBounds(15,20,110,20) ;
		panel.add(txtNoCarro).setBounds(120,20,210,20) ;
		panel.add(new JLabel("No. De Cincho:")).setBounds(15,45,110,20) ;
		panel.add(txtNoCincho).setBounds(120,45,210,20) ;
		panel.add(new JLabel("Clave Chofer:")).setBounds(15,70,110,20) ;
		panel.add(pwfGafeteChofer).setBounds(120,70,210,20) ;
		
		txtNoCarro.addKeyListener(opCincho);
		txtNoCincho.addKeyListener(opCincho);
		pwfGafeteChofer.addActionListener(opValidarChofer);
		
		cont.add(panel);
		

	}
	
    private static boolean isNumeric(String cadena){
    	try {
    		Integer.parseInt(cadena);
    		return true;
    	} catch (NumberFormatException nfe){
    		return false;
    	}
    }

    KeyListener opCarro = new KeyListener() {
		public void keyTyped(java.awt.event.KeyEvent e) {}
		public void keyReleased(java.awt.event.KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER){
				if(txtNoCarro.getText().trim().equals("")){
					txtNoCarro.requestFocus();
				}else{
					txtNoCincho.requestFocus();
				}
			}
		}
		public void keyPressed(java.awt.event.KeyEvent e) {}
	};
	
    KeyListener opCincho = new KeyListener() {
		public void keyTyped(java.awt.event.KeyEvent e) {}
		public void keyReleased(java.awt.event.KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER){
				if(txtNoCincho.getText().trim().equals("")){
					txtNoCincho.requestFocus();
				}else{
					pwfGafeteChofer.requestFocus();
				}
			}
		}
		public void keyPressed(java.awt.event.KeyEvent e) {}
	};
	
	public String validaCampos(){
		String Error = "";
		
		Error += txtNoCarro.getText().equals("")?"- No De Carro.\n":"";
		Error += txtNoCincho.getText().equals("")?"- No De Cincho.\n":"";
		
		return Error;
	}
	
    int folio_chofer;
    String claveMaster;	
	ActionListener opValidarChofer = new ActionListener() {
		@SuppressWarnings("deprecation")
		public void actionPerformed(ActionEvent e) {
			
			if(validaCampos().equals("")){
				
				if(!pwfGafeteChofer.getText().toUpperCase().equals("")){
					 
					String codigoBarrar = pwfGafeteChofer.getText().toUpperCase().trim();
					 
				 	int posicionC = codigoBarrar.indexOf('C');
				 	
				 	if(posicionC>0){
					
				 		if(isNumeric(codigoBarrar.substring(0, posicionC))){
							
				 			folio_chofer = Integer.parseInt(codigoBarrar.substring(0, posicionC));
							claveMaster = codigoBarrar.substring(posicionC+1,codigoBarrar.length());
							
							Obj_Empleados re = new Obj_Empleados().buscar(folio_chofer);  //busca a empleado chofer 
	                        Obj_Entosal entosalClave = new Obj_Entosal().buscar(); //busca clave maestra
	                 
		                        if(re.getFolio()==folio_chofer){
		                        	
	                           		switch (re.getStatus()){
	                                          case 4:	JOptionPane.showMessageDialog(null, "No puedes Realizar Movimientos, tu estatus es de baja\nfavor de comunicarte a desarrollo humano,\npara que puedas registrar tu entrada a trabajar,\nde lo contrario no te sera valido el pago de este dia","Aviso",JOptionPane.WARNING_MESSAGE);
	                                          			pwfGafeteChofer.setText("");
				                                        pwfGafeteChofer.requestFocus();
	                                          break;
	                                          case 5:	JOptionPane.showMessageDialog(null, "No puedes Realizar Movimientos, tu estatus es de baja\nfavor de comunicarte a desarrollo humano,\npara que puedas registrar tu entrada a trabajar,\nde lo contrario no te sera valido el pago de este dia","Aviso",JOptionPane.WARNING_MESSAGE);
				                                        pwfGafeteChofer.setText("");
				                                        pwfGafeteChofer.requestFocus();
	                                          break;
	                                          case 7:	JOptionPane.showMessageDialog(null, "No puedes Realizar Movimientos, tu estatus es de baja\nfavor de comunicarte a desarrollo humano,\npara que puedas registrar tu entrada a trabajar,\nde lo contrario no te sera valido el pago de este dia","Aviso",JOptionPane.WARNING_MESSAGE);
	                                          			pwfGafeteChofer.setText("");
				                                        pwfGafeteChofer.requestFocus();
		                             		  break;
		                             		  default: if(re.getNo_checador().equals(codigoBarrar)){
	                                        	  				registrarTransferencia("-",Integer.valueOf(txtNoCarro.getText().toUpperCase().trim()),txtNoCincho.getText().toUpperCase().trim());
			                                          	 }else{
			                                          		 if(entosalClave.getClave().equals(claveMaster)){
			                                          			 	registrarTransferencia("MASTER",Integer.valueOf(txtNoCarro.getText().toUpperCase().trim()),txtNoCincho.getText().toUpperCase().trim());
			                                          		 }else{
			                                          			 
				                    				     		     JOptionPane.showMessageDialog(null, "La Clave Ingresada No Corresponde A Ningun Trabajador ","Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				                    				     		     pwfGafeteChofer.setText("");
				                    				     		     pwfGafeteChofer.requestFocus();
			                                                         return;
			                                          		 }
			                                          	 }
			                                  break;  
	                                };
	                       
							}else{
			     		  		 JOptionPane.showMessageDialog(null, "La Clave Ingresada No Corresponde A Ningun Trabajador > ","Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
			     		  		 pwfGafeteChofer.setText("");
			     		  		 pwfGafeteChofer.requestFocus();
		                         return;
							}
		                        
				 	}else{
				 			pwfGafeteChofer.setText("");
					 		pwfGafeteChofer.requestFocus();
					  		JOptionPane.showMessageDialog(null, "La Clave Ingresada No Corresponde A Ningun Trabajador >>","Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
			                return;
		                }
					
				 	}else{
				 		pwfGafeteChofer.setText("");
				 		pwfGafeteChofer.requestFocus();
				  		JOptionPane.showMessageDialog(null, "La Clave Ingresada No Corresponde A Ningun Trabajador >>>","Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
			            return;
			 		}
	               
				 }else{
					 pwfGafeteChofer.setText("");
					 pwfGafeteChofer.requestFocus();
					JOptionPane.showMessageDialog(null, "Es Necesario Pasar El Gafete Por El Lector","Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));                  
					return;
				 }
			}else{
				JOptionPane.showMessageDialog(null, "Los Siguientes Campos Son Requeridos:\n"+validaCampos(),"Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}
		}
	};
	
    public void registrarTransferencia(String checada, int NoCarro, String NoCincho){
	    
    		Obj_Chacador_De_Embarque_De_Pedidos pedido = new Obj_Chacador_De_Embarque_De_Pedidos();
    		
    		pedido.setFolio_transferencia(trasferencias);
    		pedido.setEstab_surte(estab_surte);
    		pedido.setEstab_recibe(estab_recibe);
    		pedido.setFolio_encagado_asigno_embarque(folio_encargado);
    		pedido.setFolio_chofer(folio_chofer);
    		pedido.setNo_carro(NoCarro);
    		pedido.setNo_cincho(NoCincho);
    		
    		if(pedido.guardar()){
//    			deshacer();
    			dispose();
        		JOptionPane.showMessageDialog(null, "Se Registro Salida Del Embarque Exitosamete","Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
    			return;
    		}else{
    			JOptionPane.showMessageDialog(null, "No Se Pudo Registrar La Salida Del Embarque","Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
    			return;
    		}
	}

	public static void main(String[] args) {
		new Borrar_Cat_Validar_Chofer_Para_Tranferencia2(null,"","",491).setVisible(true);

	}

}
