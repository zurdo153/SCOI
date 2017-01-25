package Cat_Checador;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

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
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import com.sun.glass.events.KeyEvent;

import Conexiones_SQL.BuscarSQL;
import Obj_Checador.Obj_Chacador_De_Embarque_De_Pedidos;
import Obj_Checador.Obj_Entosal;
import Obj_Lista_de_Raya.Obj_Empleados;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Validar_Encargado_Para_Tranferencia extends JDialog  {
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	String[] establecimiento = new Obj_Establecimiento().Combo_Establecimiento_Estado_resultados();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbEstablecimiento = new JComboBox(establecimiento);
	JPasswordField txtGafeteEncargado = new JPasswordField();
	
	JTextField txtCodigoBarras = new Componentes().text(new JTextField(), "Folio De Transferencia", 15, "String");
	
	JCButton btnFiltroTransferencias = new JCButton("", "Filter-List-icon16.png", "Azul");
	
	JCButton btnDeshacer = new JCButton("Deshacer", "deshacer16.png", "Azul");
	JCButton btnGuardar = new JCButton("Guardar", "Guardar.png", "Azul");
	
	JCButton btnQuitar = new JCButton("Quitar De Lista", "", "Azul");
	 public DefaultTableModel modelo = new DefaultTableModel(null,new String[]{"Transferencia","De","A","Status","Status Recepcion","Fecha Cancelacion","Usuario Cancelacion"}){
	        @SuppressWarnings("rawtypes")
	        Class[] types = new Class[]{
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
	                 }
	                  return false;
	         }
	     };
	     
	     JTable tabla = new JTable(modelo);
	     JScrollPane Scroll = new JScrollPane(tabla);

	public Cat_Validar_Encargado_Para_Tranferencia(){
		this.setModal(true);
		this.setSize(1014, 270);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		
		this.setTitle("Programar Tranferencia");
		
		
		panel.add(new JLabel("Establecimiento Salida:")).setBounds(15,20,150,20) ;
		panel.add(cmbEstablecimiento).setBounds(160,20,210,20) ;
		panel.add(new JLabel("Clave Encargado:")).setBounds(50,45,110,20) ;
		panel.add(txtGafeteEncargado).setBounds(160,45,210,20) ;
		panel.add(new JLabel("Codigo:")).setBounds(105,70,110,20) ;
		panel.add(txtCodigoBarras).setBounds(160,70,210,20) ;
		panel.add(btnFiltroTransferencias).setBounds(370,70,30,20) ;
		
		panel.add(btnGuardar).setBounds(450,70,120,20) ;
		panel.add(btnDeshacer).setBounds(600,70,120,20) ;
		panel.add(btnQuitar).setBounds(820,70,170,20) ;
		
		panel.add(Scroll).setBounds(15,100,980,130) ;
		
		llamarRender();
		
		cmbEstablecimiento.addActionListener(opEstablecimiento);
		txtGafeteEncargado.addActionListener(opValidarUsuario);
		txtCodigoBarras.addActionListener(opFolioDeTransferencia);
		btnFiltroTransferencias.addActionListener(opFiltroDeTransferencia);
		
		btnDeshacer.addActionListener(opDeshacer);
		btnQuitar.addActionListener(opQuitar);
		
		btnGuardar.addActionListener(opGuardar);
		
		txtGafeteEncargado.setEditable(cmbEstablecimiento.getSelectedIndex()==0?false:true);
		txtCodigoBarras.setEditable(false);
		btnFiltroTransferencias.setEnabled(false);
		
		cont.add(panel);
		

	}
	
   	private void llamarRender(){		
		tabla.getTableHeader().setReorderingAllowed(false) ;
		tabla.getColumnModel().getColumn(0).setMinWidth(80);
		tabla.getColumnModel().getColumn(1).setMinWidth(110);
		tabla.getColumnModel().getColumn(2).setMinWidth(110);
		tabla.getColumnModel().getColumn(3).setMinWidth(80);
		tabla.getColumnModel().getColumn(4).setMinWidth(80);
		tabla.getColumnModel().getColumn(5).setMinWidth(100);
		tabla.getColumnModel().getColumn(6).setMinWidth(100);
		
		tabla.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 	
		tabla.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
		tabla.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 	
		tabla.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
		tabla.getColumnModel().getColumn(4).setCellRenderer(new tablaRenderer("texto","centro","Arial","normal",12)); 	
		tabla.getColumnModel().getColumn(5).setCellRenderer(new tablaRenderer("texto","centro","Arial","normal",12)); 
		tabla.getColumnModel().getColumn(6).setCellRenderer(new tablaRenderer("texto","centro","Arial","normal",12)); 	
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
		@SuppressWarnings("deprecation")
		public void actionPerformed(ActionEvent e) {
			
			txtGafeteEncargado.setText(cmbEstablecimiento.getSelectedIndex()==0?"":txtGafeteEncargado.getText());
			txtGafeteEncargado.setEditable(cmbEstablecimiento.getSelectedIndex()==0?false:true);
			txtGafeteEncargado.requestFocus();
		}
	};
    
    int folio_empleado;
    String claveMaster;
	ActionListener opValidarUsuario = new ActionListener() {
		@SuppressWarnings("deprecation")
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
                                        	  				ConsultarTransferencia(cmbEstablecimiento.getSelectedItem().toString());
//                                        	  				registrarTransferencia("-",cmbEstablecimiento.getSelectedItem().toString());
		                                          	 }else{
		                                          		 if(entosalClave.getClave().equals(claveMaster)){
		                                          			 	ConsultarTransferencia(cmbEstablecimiento.getSelectedItem().toString());
//		                                          			 	registrarTransferencia("MASTER",cmbEstablecimiento.getSelectedItem().toString());
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
							 
							 folio_empleado=0;
							 
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
	
	Object[][] lista;
	@SuppressWarnings("deprecation")
	public void ConsultarTransferencia(String estab){
		
		if( new BuscarSQL().validacion_usuario_trasferencia_de_embarque(txtGafeteEncargado.getText()) ){
			txtGafeteEncargado.setEditable(false);
			txtCodigoBarras.setEditable(true);
			btnFiltroTransferencias.setEnabled(true);
			txtCodigoBarras.setText("");
			txtCodigoBarras.requestFocus();
			lista = new BuscarSQL().getTransferenciasPendientes(estab);
//			new Cat_Checador_De_Transferencias(establecimiento,folio_empleado).setVisible(true);
		}else{
			txtCodigoBarras.setEditable(false);
			btnFiltroTransferencias.setEnabled(false);
			txtGafeteEncargado.setEditable(true);
			txtGafeteEncargado.setText("");
			txtGafeteEncargado.requestFocus();
			JOptionPane.showMessageDialog(null, "El Usuario No Tiene Acceso A Este Modulo, Comuniquese Con El Administrador","Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
			return;
		}
	}
	
	public void seleccionDeTransferencia(String folio_transferencia){
    	
    	boolean seAgregoTransferencia = false;
    	if(modelo.getRowCount()>0){
    		for(int i= 0; i<modelo.getRowCount(); i++){
    			if(modelo.getValueAt(i, 0).toString().trim().toUpperCase().equals(folio_transferencia)){
    				seAgregoTransferencia=true;
    			}
    		}
    	}
    	
    	if(!seAgregoTransferencia){
    		
    		String estab_destino = modelo.getRowCount()>0?modelo.getValueAt(0, 2).toString().trim().toUpperCase():"";
    		
    		boolean existeTransferencia = false;
        	for(Object[] ls : lista){
        		
        		System.out.println(ls[0].toString().trim().toUpperCase());
        		if(ls[0].toString().trim().toUpperCase().equals(folio_transferencia)){
        			
        			if(ls[4].toString().trim().toUpperCase().equals("PENDIENTE")){
        				
        				if(modelo.getRowCount()>0){
        					if(ls[2].toString().trim().toUpperCase().equals(estab_destino) ){
		        				existeTransferencia=true;
			        			modelo.addRow(ls);
			        			break;
	        				}else{
	        					txtCodigoBarras.setText("");
	                    		txtCodigoBarras.requestFocus();
	                    		JOptionPane.showMessageDialog(null, "Las Transferencias Realizadas Deben Ir Al Mismo Destino","Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
	                			return;
	        				}
        				}else{
        					existeTransferencia=true;
		        			modelo.addRow(ls);
		        			break;
        				}
        				
        			}else{
        				txtCodigoBarras.setText("");
                		txtCodigoBarras.requestFocus();
                		JOptionPane.showMessageDialog(null, "La transferencia Ingresada Tiene Status ["+ls[4].toString().trim().toUpperCase()+"], Solo Se Pueden Agregar transferencias PENDIENTES","Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
            			return;
        			}
        		}
        	}
        	
        	if(!existeTransferencia){
        		txtCodigoBarras.setText("");
        		txtCodigoBarras.requestFocus();
        		JOptionPane.showMessageDialog(null, "No Se Encontro El Folio De Transferencia ["+folio_transferencia+"]","Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
    			return;
        	}
        	
        	
    	}else{
    		txtCodigoBarras.setText("");
    		txtCodigoBarras.requestFocus();
    		JOptionPane.showMessageDialog(null, "El Folio De Transferencia ["+folio_transferencia+"] Ya Fue Agregado","Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
			return;
    	}
	}
	
	ActionListener opQuitar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			if(modelo.getRowCount()>0){
				if(tabla.getSelectedRow()>=0){
					modelo.removeRow(tabla.getSelectedRow());
				}else{
					JOptionPane.showMessageDialog(null, "Debe Seleccionar La Transferencia Que Desea Remover","Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
					return;
				}
			}else{
				JOptionPane.showMessageDialog(null, "No Hay Registros Que Quitar En La Tabla De Transferencias","Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}
		}
	};
	
	ActionListener opDeshacer = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			deshacer();
			
		}
	};
	
	public void deshacer(){
		modelo.setRowCount(0);
		lista = null;
		folio_empleado = 0;
		
		cmbEstablecimiento.setSelectedIndex(0);
		txtGafeteEncargado.setText("");
		txtCodigoBarras.setText("");
		
		txtGafeteEncargado.setEditable(false);
		txtCodigoBarras.setEditable(false);
		btnFiltroTransferencias.setEnabled(false);
		
		cmbEstablecimiento.requestFocus();
	}
	
	ActionListener opGuardar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			if(modelo.getRowCount()>0){
				Object[] lista_transferencias = new Object[modelo.getRowCount()];
				for(int i=0; i<modelo.getRowCount(); i++){
					lista_transferencias[i] = modelo.getValueAt(i, 0);
				}
				
				String estab_surte = modelo.getValueAt(0, 1).toString().trim().toUpperCase();
				String estab_recibe =modelo.getValueAt(0, 2).toString().trim().toUpperCase(); 
				
				new Cat_Validar_Chofer_Para_Tranferencia(lista_transferencias,estab_surte,estab_recibe,folio_empleado).setVisible(true);
			}else{
				JOptionPane.showMessageDialog(null, "No Se Han Ingresado Folios De Transferencia, Solo Se Pueden Agregar transferencias PENDIENTES","Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
    			return;
			}
		}
	};
	
	ActionListener opFolioDeTransferencia = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			seleccionDeTransferencia(txtCodigoBarras.getText().toString().trim().toUpperCase());
		}
	};
	
	ActionListener opFiltroDeTransferencia = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			new Cat_Filtro_De_Transferencias().setVisible(true);
		}
	};
	
	public class Cat_Filtro_De_Transferencias extends JDialog  {
		
		Container cont = getContentPane();
		JLayeredPane panel = new JLayeredPane();
		
		 public DefaultTableModel modeloFiltro = new DefaultTableModel(null,new String[]{"Transferencia","De","A","Status","Status Recepcion","Fecha Cancelacion","Usuario Cancelacion"}){
	        @SuppressWarnings("rawtypes")
	        Class[] types = new Class[]{
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
	                 }
	                  return false;
	         }
	     };
	     
	     JTable tablaFiltro = new JTable(modeloFiltro);
	     JScrollPane ScrollFiltro = new JScrollPane(tablaFiltro);

		public Cat_Filtro_De_Transferencias(){
			this.setModal(true);
			this.setSize(1024, 250);
			this.setLocationRelativeTo(null);
			this.setResizable(false);
			
			this.setTitle("Seleccionar Tranferencia");
			
			llamarRender();
			panel.add(ScrollFiltro).setBounds(20,20,980,180) ;
			
			llenarTablaFiltro(cmbEstablecimiento.getSelectedItem().toString().trim().toUpperCase());
			
			agregar(tablaFiltro);
			
			cont.add(panel);

		}
		
	   	private void llamarRender(){		
			tablaFiltro.getTableHeader().setReorderingAllowed(false) ;
			tablaFiltro.getColumnModel().getColumn(0).setMinWidth(80);
			tablaFiltro.getColumnModel().getColumn(1).setMinWidth(110);
			tablaFiltro.getColumnModel().getColumn(2).setMinWidth(110);
			tablaFiltro.getColumnModel().getColumn(3).setMinWidth(80);
			tablaFiltro.getColumnModel().getColumn(4).setMinWidth(80);
			tablaFiltro.getColumnModel().getColumn(5).setMinWidth(100);
			tablaFiltro.getColumnModel().getColumn(6).setMinWidth(100);
			
			tablaFiltro.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 	
			tablaFiltro.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
			tablaFiltro.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 	
			tablaFiltro.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
			tablaFiltro.getColumnModel().getColumn(4).setCellRenderer(new tablaRenderer("texto","centro","Arial","normal",12)); 	
			tablaFiltro.getColumnModel().getColumn(5).setCellRenderer(new tablaRenderer("texto","centro","Arial","normal",12)); 
			tablaFiltro.getColumnModel().getColumn(6).setCellRenderer(new tablaRenderer("texto","centro","Arial","normal",12)); 	
	   	}
	   	
		public void llenarTablaFiltro(String estab){
			for(Object[] row : lista){
				modeloFiltro.addRow(row);
			}
		}
		
		private void agregar(final JTable tbl) {
	        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
		        public void mouseClicked(MouseEvent e) {
		        	
		        	if(e.getClickCount() == 2){
		    			int fila = tablaFiltro.getSelectedRow();
		    			seleccionDeTransferencia(modeloFiltro.getValueAt(fila, 0).toString().trim().toUpperCase());
		    			dispose();
		        	}
		        }
	        });
	    }
	}
	
	public class Cat_Validar_Chofer_Para_Tranferencia extends JDialog  {
		
		Container cont = getContentPane();
		JLayeredPane panel = new JLayeredPane();
		
		JTextField txtNoCarro = new Componentes().text(new JCTextField(), "", 3, "Int");
		JTextField txtNoCincho = new Componentes().text(new JCTextField(), "", 20, "String");
		JPasswordField pwfGafeteChofer = new JPasswordField();

		Object[] trasferencias=null;
		String estab_surte = "";
		String estab_recibe = "";
		int folio_encargado = 0;
		
		public Cat_Validar_Chofer_Para_Tranferencia(Object[] trasferencias,String estab_surte,String estab_recibe,int folio_encargado){
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
		
	    private boolean isNumeric(String cadena){
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
	    			deshacer();
	    			dispose();
	        		JOptionPane.showMessageDialog(null, "Se Registro Salida Del Embarque Exitosamete","Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
	    			return;
	    		}else{
	    			JOptionPane.showMessageDialog(null, "No Se Pudo Registrar La Salida Del Embarque","Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
	    			return;
	    		}
		}
	}


	public static void main(String[] args) {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Validar_Encargado_Para_Tranferencia().setVisible(true);
		}catch(Exception e){	}
	}

}
