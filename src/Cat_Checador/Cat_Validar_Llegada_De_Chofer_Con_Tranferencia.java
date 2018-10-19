package Cat_Checador;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Conexiones_SQL.ActualizarSQL;
import Conexiones_SQL.BuscarSQL;
import Obj_Checador.Obj_Entosal;
import Obj_Lista_de_Raya.Obj_Empleados;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Validar_Llegada_De_Chofer_Con_Tranferencia extends JDialog  {
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	String[] estab = new Obj_Establecimiento().Combo_Establecimientos("R");
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbEstablecimiento = new JComboBox(estab);
	JPasswordField txtGafeteChofer = new JPasswordField();

	String trasferencia = "";
	String estab_surte = "";
	String estab_recibe = "";
	int folio_encargado = 0;
	
	public Cat_Validar_Llegada_De_Chofer_Con_Tranferencia(){
		this.setModal(true);
		this.setSize(390, 115);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		
		this.setTitle("Llegada De Tranferencia");
		
		panel.add(new JLabel("Establecimiento Recibe:")).setBounds(15,20,150,20) ;
		panel.add(cmbEstablecimiento).setBounds(160,20,210,20) ;
		panel.add(new JLabel("Clave Chofer:")).setBounds(75,45,110,20) ;
		panel.add(txtGafeteChofer).setBounds(160,45,210,20) ;
		
		txtGafeteChofer.setEditable(false);
		
		cmbEstablecimiento.addActionListener(opCmbEstabEntrega);
		txtGafeteChofer.addActionListener(opValidarChofer);
		
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
    
    ActionListener opCmbEstabEntrega = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			if(cmbEstablecimiento.getSelectedIndex()==0){
				txtGafeteChofer.setText("");
				cmbEstablecimiento.requestFocus();
				txtGafeteChofer.setEditable(false);
			}else{
				txtGafeteChofer.setEditable(true);
				txtGafeteChofer.requestFocus();
			}
		}
    };

    int folio_chofer;
    String establecimiento = "";
    String claveMaster;	
	ActionListener opValidarChofer = new ActionListener() {
		@SuppressWarnings("deprecation")
		public void actionPerformed(ActionEvent e) {
			
				if(!txtGafeteChofer.getText().toUpperCase().equals("") && cmbEstablecimiento.getSelectedIndex() != 0){
					 
					String codigoBarrar = txtGafeteChofer.getText().toUpperCase().trim();
					 
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
				                                        txtGafeteChofer.setText("");
				                                        txtGafeteChofer.requestFocus();
	                                          break;
	                                          case 5:	JOptionPane.showMessageDialog(null, "No puedes Realizar Movimientos, tu estatus es de baja\nfavor de comunicarte a desarrollo humano,\npara que puedas registrar tu entrada a trabajar,\nde lo contrario no te sera valido el pago de este dia","Aviso",JOptionPane.WARNING_MESSAGE);
				                                        txtGafeteChofer.setText("");
				                                        txtGafeteChofer.requestFocus();
	                                          break;
	                                          case 7:	JOptionPane.showMessageDialog(null, "No puedes Realizar Movimientos, tu estatus es de baja\nfavor de comunicarte a desarrollo humano,\npara que puedas registrar tu entrada a trabajar,\nde lo contrario no te sera valido el pago de este dia","Aviso",JOptionPane.WARNING_MESSAGE);
				                                        txtGafeteChofer.setText("");
				                                        txtGafeteChofer.requestFocus();
		                             		  break;
		                             		  default: if(re.getNo_checador().equals(codigoBarrar)){
		                             			  				establecimiento = cmbEstablecimiento.getSelectedItem().toString().trim();
		                             			  				txtGafeteChofer.setText("");
		                             			  				new Cat_Seleccion_De_Transferencias("-").setVisible(true);
			                                          	 }else{
			                                          		 if(entosalClave.getClave().equals(claveMaster)){
			                                          			establecimiento = cmbEstablecimiento.getSelectedItem().toString().trim();
			                                          			txtGafeteChofer.setText("");
			                                          			new Cat_Seleccion_De_Transferencias("MASTER").setVisible(true);
			                                          		 }else{
			                                          			 
				                    				     		     JOptionPane.showMessageDialog(null, "La Clave Ingresada No Corresponde A Ningun Trabajador ","Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				                    				     		     	txtGafeteChofer.setText("");
			                    				     		     		txtGafeteChofer.requestFocus();
			                                                         return;
			                                          		 }
			                                          	 }
			                                  break;  
	                                };
	                       
							}else{
			     		  		 JOptionPane.showMessageDialog(null, "La Clave Ingresada No Corresponde A Ningun Trabajador > ","Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
			     		  		 txtGafeteChofer.setText("");
			     		  		 txtGafeteChofer.requestFocus();
		                         return;
							}
		                        
				 	}else{
					 		txtGafeteChofer.setText("");
					 		txtGafeteChofer.requestFocus();
					  		JOptionPane.showMessageDialog(null, "La Clave Ingresada No Corresponde A Ningun Trabajador >>","Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
			                return;
		                }
					
				 	}else{
				 		txtGafeteChofer.setText("");
				 		txtGafeteChofer.requestFocus();
				  		JOptionPane.showMessageDialog(null, "La Clave Ingresada No Corresponde A Ningun Trabajador >>>","Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
			            return;
			 		}
	               
				 }else{
					 txtGafeteChofer.setText("");
					 txtGafeteChofer.requestFocus();
					JOptionPane.showMessageDialog(null, "Es Necesario Seleccionar El Establecimiento De Entrega y Pasar El Gafete Por El Lector","Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));                  
					return;
				 }
		}
	};
	
//    public void registrarLlegadaDeTransferencia(String checada){
//	    
//    	String trasferencia = "";
//    	String estab_surte = "";
//    	String estab_recibe = "";
//    	int folio_encargado = 0;
//    	
////    	if(!new BuscarSQL().chofer_ocupado(folio_chofer)){
//    		
//    		Obj_Chacador_De_Embarque_De_Pedidos pedido = new Obj_Chacador_De_Embarque_De_Pedidos();
//    		
//    		pedido.setFolio_transferencia(trasferencia);
//    		pedido.setEstab_surte(estab_surte);
//    		pedido.setEstab_recibe(estab_recibe);
//    		pedido.setFolio_encagado_asigno_embarque(folio_encargado);
//    		pedido.setFolio_chofer(folio_chofer);
////    		pedido.setNo_carro(NoCarro);
////    		pedido.setNo_cincho(NoCincho);
//    		
//    		if(pedido.guardar()){
//    			txtGafeteChofer.setText("");
//        		JOptionPane.showMessageDialog(null, "Se Registro Salida Del Embarque Exitosamete","Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
//    			return;
//    		}else{
//    			JOptionPane.showMessageDialog(null, "No Se Pudo Registrar La Salida Del Embarque","Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
//    			return;
//    		}
//    		
////    	}else{
////    		JOptionPane.showMessageDialog(null, "No Puede Asignarle Tranferencia A Este Chofer Debido A Que Ya No A Registrado Entrega Del Ultimo Embarque","Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
////			return;
////    	}
//	}
    
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public class Cat_Seleccion_De_Transferencias extends JDialog  {
    	
    	Container cont = getContentPane();
    	JLayeredPane panel = new JLayeredPane();
    	
    	JLabel lblAviso = new JLabel("");
    	JLabel lblAviso2 = new JLabel("");
    	JCButton btnRegistrarLlegada = new JCButton("Registrar Lleagada","","Azul");
    	
    	 public DefaultTableModel modelo = new DefaultTableModel(null,new String[]{"Transferencia","De","A","*"}){
            @SuppressWarnings("rawtypes")
            Class[] types = new Class[]{
                        java.lang.Object.class,
                        java.lang.Object.class,
                        java.lang.Object.class,
                        java.lang.Boolean.class
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
                             case 3  : return true; 
                     }
                      return false;
             }
         };
         
         JTable tabla = new JTable(modelo);
         JScrollPane Scroll = new JScrollPane(tabla);
         

       
    	public Cat_Seleccion_De_Transferencias(String checada){
    		this.setModal(true);
    		this.setSize(645, 250);
    		this.setLocationRelativeTo(null);
    		this.setResizable(false);
    		
    		this.setTitle("Seleccionar Tranferencia");
    		
    		lblAviso.setFont(new Font("arial", Font.BOLD, 18));
    		lblAviso2.setFont(new Font("arial", Font.BOLD, 25));
    		
    		lblAviso.setForeground(Color.RED);
    		lblAviso2.setForeground(Color.RED);
    		
    		llamarRender();
    		
    		panel.add(lblAviso).setBounds(20,60,600,50);
    		panel.add(lblAviso2).setBounds(20,80,600,60);
    		panel.add(Scroll).setBounds(20,20,600,180);
    		panel.add(btnRegistrarLlegada).setBounds(450,200,170,20) ;
    		
    		lblAviso.setHorizontalAlignment(0);
    		lblAviso2.setHorizontalAlignment(0);
    		
    		llenarTabla();
    		
    		btnRegistrarLlegada.addActionListener(opPasarTransferencias);
    		
    		cont.add(panel);
    	}
    	
       	private void llamarRender(){
       		
       		tabla.getTableHeader().setReorderingAllowed(false) ;
       		
    		tabla.getTableHeader().setReorderingAllowed(false) ;
    		tabla.getColumnModel().getColumn(0).setMinWidth(90);
    		tabla.getColumnModel().getColumn(1).setMinWidth(230);
    		tabla.getColumnModel().getColumn(2).setMinWidth(230);
    		tabla.getColumnModel().getColumn(3).setMinWidth(30);
    		
    		tabla.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 	
    		tabla.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
    		tabla.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 	
    		tabla.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("CHB","centro","Arial","normal",12)); 	
       	}
       	
    	public void llenarTabla(){
    		
    		modelo.setRowCount(0);
    		
    		Object[][] lista = new BuscarSQL().getLlegadaTransferencias(folio_chofer,establecimiento);
    		
    		if(lista.length>0){
    			
    			lblAviso.setVisible(false);
    			lblAviso2.setVisible(false);
    			
    			for(Object[] row : lista){
	    			modelo.addRow(row);
	    		}
    		}else{
    			
    			lblAviso.setText("El Chofer No Tiene Pedido Por Entregar En El Establecimeinto: ");
    			lblAviso2.setText(establecimiento+" o la entrega no a sido validada por seguridad");
    			lblAviso.setVisible(true);
    			lblAviso2.setVisible(true);
    			Scroll.setVisible(false);
    			btnRegistrarLlegada.setVisible(false);
    		}
    		
    	}
    	
    	ActionListener opPasarTransferencias = new ActionListener() {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public void actionPerformed(ActionEvent e) {
			
				String tranferencias = "";
				Vector folios = new Vector();
				for(int i = 0; i<tabla.getRowCount(); i++){
					if(tabla.getValueAt(i, 3).toString().trim().equals("true")){
						tranferencias += "- "+tabla.getValueAt(i, 0).toString().trim()+"\n";
						folios.add(tabla.getValueAt(i, 0).toString().trim());
					}
				}
				
				if(folios.size()>0){
					if(JOptionPane.showConfirmDialog(null,"Verifique Si Los Folios Seleccionados Son Los Correctos:\n"+tranferencias+" Desea Continuar?") == 0){

						new Cat_Valida_Seguridad(folios).setVisible(true);
//						if(new ActualizarSQL().Registrar_Llegada_De_Transferencia(folios) ){
//							
//							llenarTabla();
//							
//							JOptionPane.showMessageDialog(null, "Se Ha Registrado La LLegada Correctamente","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
//							return;
//						}else{
//							JOptionPane.showMessageDialog(null, "No Se Pudo Registrar La Llegada De La Transferencia","Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
//		                    return;
//						}
					}
				}else{
					JOptionPane.showMessageDialog(null, "Favor De Seleccionar Las Tranferencias Que Entregará","Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
                    return;
				}

			}
		};
		
		   //--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	    public class Cat_Valida_Seguridad extends JDialog  {
	    	
	    	Container cont = getContentPane();
	    	JLayeredPane panel = new JLayeredPane();
	    	
	    	JLabel lblTransferencias = new JLabel("");
	    	JCButton btnRegistrarLlegada = new JCButton("Registrar Lleagada","","Azul");
	    	
	    	JTextField txtCincho = new Componentes().text(new JTextField(), "No Cincho", 20, "String");
	    	JPasswordField pswSeguridad = new JPasswordField();
	    	
	         
	       @SuppressWarnings("rawtypes")
	       Vector Folios = null;
	       
			@SuppressWarnings("rawtypes")
			public Cat_Valida_Seguridad(Vector folios){
	    		this.setModal(true);
	    		this.setSize(390, 145);
	    		this.setLocationRelativeTo(null);
	    		this.setResizable(false);
	    		
	    		this.setTitle("Confirmar Por Seguridad");
	    		
	    		this.Folios = folios;    		
	    		
	    		panel.add(new JLabel("Transferencias:")).setBounds(15,20,150,20) ;
	    		panel.add(lblTransferencias).setBounds(160,20,210,20) ;
	    		panel.add(new JLabel("No Cincho:")).setBounds(15,45,150,20) ;
	    		panel.add(txtCincho).setBounds(160,45,210,20) ;
	    		panel.add(new JLabel("Clave Seguridad:")).setBounds(50,70,110,20) ;
	    		panel.add(pswSeguridad).setBounds(160,70,210,20) ;
	    		
	    		lblTransferencias.setText(foliosDeTransferencia());
	    		
	    		txtCincho.addActionListener(opCincho);
	    		pswSeguridad.addActionListener(opRegistrarLlegada);
	    		
	    		cont.add(panel);
	    		
	    	}
			
			public String foliosDeTransferencia(){
				String tranferencias = "";
				for(int i = 0; i<Folios.size(); i++){
						tranferencias += Folios.get(i)+", ";
				}
				tranferencias = tranferencias.substring(0,tranferencias.length()-2);
				return tranferencias;
			}
			
		    ActionListener opCincho = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					pswSeguridad.requestFocus();
				}
			};
	    	
	    	ActionListener opRegistrarLlegada = new ActionListener() {
				@SuppressWarnings({ "deprecation" })
				public void actionPerformed(ActionEvent e) {
					
					if(!txtCincho.getText().equals("")){
						
						if(new BuscarSQL().validacion_usuario_trasferencia_de_embarque(pswSeguridad.getText()) /*validar usuario autorizados como revision de seguridad*/){
							
								if(new ActualizarSQL().Registrar_Llegada_De_Transferencia(Folios,txtCincho.getText().toUpperCase(),pswSeguridad.getText().trim().toUpperCase()) ){
									
									llenarTabla();
									dispose();
									JOptionPane.showMessageDialog(null, "Se Ha Registrado La LLegada Correctamente","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
									return;
								}else{
									JOptionPane.showMessageDialog(null, "No Se Pudo Registrar La Llegada De La Transferencia","Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				                    return;
								}
						}else{
								JOptionPane.showMessageDialog(null, "El Usuario No Esta Registrado Para Realizar Autorizaciones De Seguridad","Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
			                    return;
						}
						
					}else{
						JOptionPane.showMessageDialog(null, "Es Necesario Registrar EL Numero De Cincho","Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
	                    return;
					}
				
				}
			};
			
			
	    	
	    }
    	
    }

	public static void main(String[] args) {
		new Cat_Validar_Llegada_De_Chofer_Con_Tranferencia().setVisible(true);
	}

}
