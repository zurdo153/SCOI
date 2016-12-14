package Cat_Checador;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Conexiones_SQL.BuscarSQL;
import Obj_Checador.Obj_Entosal;
import Obj_Lista_de_Raya.Obj_Empleados;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.Componentes;
import Obj_Principal.JCTextField;

@SuppressWarnings("serial")
public class Cat_Validar_Encargado_Para_Tranferencia extends JDialog  {
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	String[] establecimiento = new Obj_Establecimiento().Combo_Establecimiento_Estado_resultados();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbEstablecimiento = new JComboBox(establecimiento);
	JTextField txtGafeteEncargado = new Componentes().text(new JCTextField(), "", 20, "String");

	public Cat_Validar_Encargado_Para_Tranferencia(){
		this.setModal(true);
		this.setSize(360, 120);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		
		this.setTitle("Programar Tranferencia");
		
		
		panel.add(new JLabel("Establecimiento:")).setBounds(15,20,110,20) ;
		panel.add(cmbEstablecimiento).setBounds(120,20,210,20) ;
		panel.add(new JLabel("Clave Encargado:")).setBounds(15,45,110,20) ;
		panel.add(txtGafeteEncargado).setBounds(120,45,210,20) ;
		
		cmbEstablecimiento.addActionListener(opEstablecimiento);
		txtGafeteEncargado.addActionListener(opValidarUsuario);
		
		txtGafeteEncargado.setEditable(cmbEstablecimiento.getSelectedIndex()==0?false:true);
		
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
    
    ActionListener opEstablecimiento = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			txtGafeteEncargado.setText(cmbEstablecimiento.getSelectedIndex()==0?"":txtGafeteEncargado.getText());
			txtGafeteEncargado.setEditable(cmbEstablecimiento.getSelectedIndex()==0?false:true);
			txtGafeteEncargado.requestFocus();
		}
	};
    
    int folio_empleado;
    String claveMaster;
	ActionListener opValidarUsuario = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			
			if(!txtGafeteEncargado.getText().toUpperCase().equals("")){
				 
				String codigoBarrar = txtGafeteEncargado.getText().toUpperCase().trim();
				 
			 	int posicionC = codigoBarrar.indexOf('C');
			 	
			 	if(posicionC>0){
				
			 		if(isNumeric(codigoBarrar.substring(0, posicionC))){
						
						folio_empleado = Integer.parseInt(codigoBarrar.substring(0, posicionC));
						claveMaster = codigoBarrar.substring(posicionC+1,codigoBarrar.length());
						
						Obj_Empleados re = new Obj_Empleados().buscar(folio_empleado);  //busca a empleado 
                        Obj_Entosal entosalClave = new Obj_Entosal().buscar(); //busca clave maestra
                 
	                        if(re.getFolio()==folio_empleado){
	                        	
                           		switch (re.getStatus()){
                                          case 4:	JOptionPane.showMessageDialog(null, "No puedes checar, tu estatus es de baja\nfavor de comunicarte a desarrollo humano,\npara que puedas registrar tu entrada a trabajar,\nde lo contrario no te sera valido el pago de este dia","Aviso",JOptionPane.WARNING_MESSAGE);
			                                        txtGafeteEncargado.setText("");
			                             			txtGafeteEncargado.requestFocus();
                                          break;
                                          case 5:	JOptionPane.showMessageDialog(null, "No puedes checar, tu estatus es de baja\nfavor de comunicarte a desarrollo humano,\npara que puedas registrar tu entrada a trabajar,\nde lo contrario no te sera valido el pago de este dia","Aviso",JOptionPane.WARNING_MESSAGE);
			                                        txtGafeteEncargado.setText("");
			                             			txtGafeteEncargado.requestFocus();
                                          break;
                                          case 7:	JOptionPane.showMessageDialog(null, "No puedes checar, tu estatus es de baja\nfavor de comunicarte a desarrollo humano,\npara que puedas registrar tu entrada a trabajar,\nde lo contrario no te sera valido el pago de este dia","Aviso",JOptionPane.WARNING_MESSAGE);
			                                        txtGafeteEncargado.setText("");
			                             			txtGafeteEncargado.requestFocus();
		                                  break;
                                          default: if(re.getNo_checador().equals(codigoBarrar)){
                                        	  				registrarTransferencia("-",cmbEstablecimiento.getSelectedItem().toString());
		                                          	 }else{
		                                          		 if(entosalClave.getClave().equals(claveMaster)){
		                                          			 	registrarTransferencia("MASTER",cmbEstablecimiento.getSelectedItem().toString());
		                                          		 }else{
		                                          			 
			                    				     		     JOptionPane.showMessageDialog(null, "La Clave Ingresada No Corresponde A Ningun Trabajador ","Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
			                    				     		     txtGafeteEncargado.setText("");
		                                            			 txtGafeteEncargado.requestFocus();
		                                                         return;
		                                          		 }
		                                          	 }
		                                  break;  
                                };
                       
						}else{
		     		  		 JOptionPane.showMessageDialog(null, "La Clave Ingresada No Corresponde A Ningun Trabajador > ","Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
		     		  		 txtGafeteEncargado.setText("");
		     		  		 txtGafeteEncargado.requestFocus();
	                         return;
						}
	                        
			 	}else{
				 		txtGafeteEncargado.setText("");
				  		txtGafeteEncargado.requestFocus();
				  		JOptionPane.showMessageDialog(null, "La Clave Ingresada No Corresponde A Ningun Trabajador >>","Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
		                return;
	                }
				
			 	}else{
			 		txtGafeteEncargado.setText("");
			 		txtGafeteEncargado.requestFocus();
			  		JOptionPane.showMessageDialog(null, "La Clave Ingresada No Corresponde A Ningun Trabajador >>>","Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
		            return;
		 		}
               
			 }else{
				txtGafeteEncargado.setText("");
				txtGafeteEncargado.requestFocus();
				JOptionPane.showMessageDialog(null, "Es Necesario Pasar El Gafete Por El Lector","Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));                  
				return;
			 }
								
		}
	};
	
    public void registrarTransferencia(String checada, String establecimiento){
//    	Obj_Entosal entosal=new Obj_Entosal().checar_dia_descanso(folio_empleado);
//    	if (entosal.getValor_Descanso().equals("true")){
//	    		
//             JOptionPane.showMessageDialog(null, "El Dia De Hoy Lo Tienes Registrado Como Tu Dia De Descanso,\nAvisa A Desarrollo Humano Para Que Puedas Registrar Tu Entrada \nA Trabajar, De Lo Contrario No Te Sera Valido El Pago De Este Dia","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
//             JOptionPane.showMessageDialog(null, "El Dia De Hoy Lo Tienes Registrado Como Tu Dia De Descanso,\nAvisa A Desarrollo Humano Para Que Puedas Registrar Tu Entrada \nA Trabajar, De Lo Contrario No Te Sera Valido El Pago De Este Dia","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/red-de-usuario-icono-6758-64.png"));
//             txtGafeteEncargado.setText("");
//             txtGafeteEncargado.requestFocus();
//             return;
//	    	
//	     }else{
//	     	if (entosal.getValor_Pc().equals("false")){
//	  			  JOptionPane.showMessageDialog(null, "Estas Intentando Checar En Una Computadora Que No Esta Asignada A Tu Establecimiento \nAvisa A Desarrollo Humano Para Que Puedas Checar En Esta Computadora","Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
//	  			  JOptionPane.showMessageDialog(null, "Estas Intentando Checar En Una Computadora Que No Esta Asignada A Tu Establecimiento \nAvisa A Desarrollo Humano Para Que Puedas Checar En Esta Computadora","Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/red-de-usuario-icono-6758-64.png"));
//	  			  txtGafeteEncargado.setText("");
//	  			  txtGafeteEncargado.requestFocus();
//                  return;
//	        }else{
//	        	
//	        }
	    	 
	    	 
				if( new BuscarSQL().validacion_usuario_trasferencia_de_embarque(txtGafeteEncargado.getText()) ){
//					String folio = txtGafeteEncargado.getText().trim().substring(0, txtGafeteEncargado.getText().trim().indexOf("C"));
//					System.out.print(folio);
					new Cat_Checador_De_Transferencias(establecimiento,folio_empleado).setVisible(true);
				}else{
					JOptionPane.showMessageDialog(null, "El Usuario No Tiene Acceso A Este Modulo, Comuniquese Con El Administrador","Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
					return;
				}
	    	 
	     	
//	    }
	    	txtGafeteEncargado.setText("");
	    	txtGafeteEncargado.requestFocus();
	}

	public static void main(String[] args) {
		new Cat_Validar_Encargado_Para_Tranferencia().setVisible(true);

	}

}
