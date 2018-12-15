package Cat_Contabilidad;

import java.awt.Container;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.GuardarSQL;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_tabla;

@SuppressWarnings("serial")
public class Cat_Saldos_Cuentas_Movimientos_Conciliacion extends JFrame{

	Obj_tabla ObjTab= new Obj_tabla();
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	int Cantidad_Real_De_Columnas=13,checkboxindex=1;
	@SuppressWarnings("rawtypes")
	public Class[] tipos(){
		Class[] tip = new Class[Cantidad_Real_De_Columnas];
		
		for(int i =0; i<Cantidad_Real_De_Columnas; i++){
			if(i==checkboxindex-1){
				tip[i]=java.lang.Boolean.class;
			}else{
				tip[i]=java.lang.Object.class;
			}
		}
		return tip;
	}
	
	String[] nombreCabeceras = new String[]{"*","Fecha","Cheque","Deposito","Retiros","Transaccion","Cod_Transac","Descripcion","Descripcion Detallada","Folio_Mov","Status_Concil","Folio_Trans","Folio_BMS"};
	String[] nombreCabecerasBMS = new String[]{"*","Cheque","Cantidad","Tipo_Beneficiario","Beneficiario","Concepto","Transaccion","Folio_Trans","Folio_BMS"};
	
	 public DefaultTableModel modeloArchivo = new DefaultTableModel(null, nombreCabeceras){
		 @SuppressWarnings("rawtypes")
			Class[] types = tipos();
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {
	         return types[columnIndex];
	     }
			public boolean isCellEditable(int fila, int columna){
				if(columna==0)
					return true; return false;
			}
	    };
	    
	    public DefaultTableModel modeloBMS = new DefaultTableModel(null, nombreCabecerasBMS){
			 @SuppressWarnings("rawtypes")
				Class[] types = tipos();
				@SuppressWarnings({ "unchecked", "rawtypes" })
				public Class getColumnClass(int columnIndex) {
		         return types[columnIndex];
		     }
				public boolean isCellEditable(int fila, int columna){
					if(columna==0)
						return true; return false;
				}
		    };
		    
	    public DefaultTableModel modeloConciliados = new DefaultTableModel(null, nombreCabeceras){
			 @SuppressWarnings("rawtypes")
				Class[] types = tipos();
				@SuppressWarnings({ "unchecked", "rawtypes" })
				public Class getColumnClass(int columnIndex) {
		         return types[columnIndex];
		     }
				public boolean isCellEditable(int fila, int columna){
					if(columna==0){
						return modeloConciliados.getValueAt(fila, 10).toString().trim().equals("A")?false:true;
					} 
					else{
						return false;
					}
				}
		    };
		    
	    public DefaultTableModel modeloComisiones = new DefaultTableModel(null, nombreCabeceras){
			 @SuppressWarnings("rawtypes")
				Class[] types = tipos();
				@SuppressWarnings({ "unchecked", "rawtypes" })
				public Class getColumnClass(int columnIndex) {
		         return types[columnIndex];
		     }
				public boolean isCellEditable(int fila, int columna){
					if(columna==0)
						return true; return false;
				}
		    };
	
		    JTable tablaArchivo = new JTable(modeloArchivo);
			public JScrollPane scroll_tablaArchivo = new JScrollPane(tablaArchivo);

			JTable tablaConciliada = new JTable(modeloConciliados);
			public JScrollPane scroll_tablaConciliada = new JScrollPane(tablaConciliada);
			
			JTable tablaComisiones = new JTable(modeloComisiones);
			public JScrollPane scroll_tablaComisiones = new JScrollPane(tablaComisiones);
			
			JTable tablaBMS = new JTable(modeloBMS);
			public JScrollPane scroll_tablaBMS = new JScrollPane(tablaBMS);
			
			
		DefaultTableModel modelo = null;
		JTable tabla = null;
		
		DefaultTableModel modeloRespaldo = new DefaultTableModel(0, 22);
		
		JCButton btnExaminar = new JCButton("Examinar", "buscar.png", "Azul");
		JCButton btnDeshacer = new JCButton("Deshacer", "deshacer16.png", "Azul");
		JCButton btnGuardar  = new JCButton("Guardar", "Guardar.png", "Azul");
		
		JCButton btnConciliar = new JCButton("Conciliar", "agregar - copia.png", "AzulC");
		JCButton btnConciliarTemporal = new JCButton("Conciliacion Temporal", "agregar - copia.png", "AzulC");
		JCButton btnCancelar = new JCButton("Cancelar", "cancelar-icono-4961-16.png", "AzulC");
		
		JTextField txtSaldoInicial  	= new Componentes().text(new JCTextField(), "Saldo Inicial", 20, "Double");
		JTextField txtDepositos 		= new Componentes().text(new JCTextField(), "Depositos", 20, "Double");
		JTextField txtRetiros		 	= new Componentes().text(new JCTextField(), "Retiros", 20, "Double");
		JTextField txtPreconciliacion 	= new Componentes().text(new JCTextField(), "Preconciliacion", 20, "Double");
		JTextField txtSaldoFinal		= new Componentes().text(new JCTextField(), "Saldo Final", 20, "Double");
		
		JTextField txtCuenta = new Componentes().text(new JCTextField(), "No Cuenta", 15, "Int");
		JTextField txtFechaIn = new Componentes().text(new JCTextField(), "Fecha Inicial", 15, "String");
		JTextField txtFechaFin = new Componentes().text(new JCTextField(), "Fecha Final", 15, "String");
		
		DecimalFormat df = new DecimalFormat("#0.00");
		double depositos = 0;
		double retiros = 0;
		
//		RSProgressMaterial pbm = new RSProgressMaterial();
		
	public Cat_Saldos_Cuentas_Movimientos_Conciliacion() {
		this.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds()); 
		int anchoVentana = Toolkit.getDefaultToolkit().getScreenSize().width;
		int altoVentana  = Toolkit.getDefaultToolkit().getScreenSize().height;
		
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/asistencia.png"));
		this.setTitle("Saldos Cuentas Movimientos Banco");
		panel.setBorder(BorderFactory.createTitledBorder("Movimientos De Cuenta"));

		int x=15,y=15,ancho=80;	
//		panel.add(pbm).setBounds(anchoVentana/2-80, ((altoVentana-100)/2)-20, 150, 150);
		
		panel.add(btnExaminar).setBounds           				(x							,y    						,ancho+28 				,20 				);
		panel.add(btnDeshacer).setBounds           				(x+ancho+30					,y    						,ancho+28 				,20 				);
		panel.add(btnGuardar).setBounds           				(x+ancho*2+60				,y    						,ancho+28 				,20 				);
		
		panel.add(new JLabel("Saldo Inicial:")).setBounds 		(x+(ancho*4)+30				,y	  						,ancho+30 				,20 				);
		panel.add(txtSaldoInicial).setBounds    				(x+(ancho*5)+30				,y	  						,ancho+30 				,20 				);
		panel.add(new JLabel("Depositos:")).setBounds    		(x+(ancho*7)+30				,y	  						,ancho+30 				,20 				);
		panel.add(txtDepositos).setBounds   	 				(x+(ancho*8)+30				,y	  						,ancho+30 				,20 				);
		
		panel.add(new JLabel("PreConciliacion:")).setBounds     (x+(ancho*4)+30				,y+22 						,ancho+30 				,20 				);
		panel.add(txtPreconciliacion).setBounds   				(x+(ancho*5)+30				,y+22 						,ancho+30 				,20 				);
		panel.add(new JLabel("Retiros:")).setBounds	    		(x+(ancho*7)+30				,y+22 						,ancho+30 				,20 				);
		panel.add(txtRetiros).setBounds  		  				(x+(ancho*8)+30				,y+22 						,ancho+30 				,20 				);
		
		panel.add(new JLabel("Saldo Final:")).setBounds  		(x+(ancho*4)+30				,y+44 						,ancho+30 				,20 				);
		panel.add(txtSaldoFinal).setBounds         				(x+(ancho*5)+30				,y+44 						,ancho+30 				,20 				);
		
		panel.add(new JLabel("Cuenta:")).setBounds      		(x							,y+=25  					,ancho+30 				,20 				);
		panel.add(new JLabel("Fecha In:")).setBounds    		(x+ancho+30					,y      					,ancho+30 				,20 				);
		panel.add(new JLabel("Fecha Fin:")).setBounds   		(x+ancho*2+60				,y      					,ancho+30 				,20 				);
		
		panel.add(txtCuenta).setBounds             				(x							,y+=20  					,ancho+30 				,20 				);
		panel.add(txtFechaIn).setBounds            				(x+ancho+30					,y      					,ancho+30 				,20 				);
		panel.add(txtFechaFin).setBounds          				(x+ancho*2+60				,y      					,ancho+30 				,20 				);
		
		panel.add(btnConciliarTemporal).setBounds   			(x+((anchoVentana-50)/2)-200,y    						,ancho+120				,25 				);
		panel.add(btnConciliar).setBounds           			(x+((anchoVentana-50)/2)+10 ,y    						,ancho+30 				,25 				);
		
		panel.add(scroll_tablaArchivo).setBounds  				(x							,y+=25  					,(anchoVentana-50)/2 	,(altoVentana-250)/3);
		panel.add(scroll_tablaBMS).setBounds   	  				(x+((anchoVentana-50)/2)+10 ,y							,(anchoVentana-50)/2 	,(altoVentana-250)/3);
		panel.add(btnCancelar).setBounds           				(x							,y+=((altoVentana-250)/3)+20,ancho+30    		 	,25 				);
		panel.add(scroll_tablaConciliada).setBounds  			(x							,y+=30       				,(anchoVentana-50)   	,(altoVentana-200)/3);
		panel.add(scroll_tablaComisiones).setBounds  			(x							,y+=((altoVentana-250)/3)+50,(anchoVentana-50)   	,(altoVentana-400)/3);
		
//		pbm.setAnchoProgress(10);
//		pbm.setVisible(true);
		 
		txtCuenta.setEditable(false);
		txtFechaIn.setEditable(false);
		txtFechaFin.setEditable(false);
		
		txtSaldoInicial.setEditable(false);
		txtDepositos.setEditable(false);
		txtRetiros.setEditable(false);
		txtPreconciliacion.setEditable(false);
		txtSaldoFinal.setEditable(false);
				
		init_tabla_principal(tablaArchivo);
		init_tabla_principalBMS(tablaBMS,false);
		init_tabla_principal(tablaConciliada);
		init_tabla_principal(tablaComisiones);
		
		btnExaminar.addActionListener(opExaminar);
		btnDeshacer.addActionListener(opDeshacer);
		btnGuardar.addActionListener(opGuardar);
		
		btnConciliar.addActionListener(opConciliar);
		btnConciliarTemporal.addActionListener(opConciliarTemporal);
		btnCancelar.addActionListener(opCancelar);
		
		agregar(tablaArchivo,"tbArchivo");
		agregar(tablaBMS,"tbBMS");
		agregar(tablaConciliada,"tbConciliaciones");
		
		cont.add(panel);
	}
	
	public void init_tabla_principal(JTable tablaP){
		tablaP.getColumnModel().getColumn( 0).setMinWidth(30);
		tablaP.getColumnModel().getColumn( 0).setMaxWidth(30);
		tablaP.getColumnModel().getColumn( 1).setMinWidth(120);
		tablaP.getColumnModel().getColumn( 1).setMaxWidth(120);
		tablaP.getColumnModel().getColumn( 2).setMinWidth(100);
		tablaP.getColumnModel().getColumn( 3).setMinWidth(80);
		tablaP.getColumnModel().getColumn( 4).setMinWidth(80);
		tablaP.getColumnModel().getColumn( 5).setMinWidth(100);
		tablaP.getColumnModel().getColumn( 6).setMinWidth(100);
		tablaP.getColumnModel().getColumn( 7).setMinWidth(200);
		tablaP.getColumnModel().getColumn( 8).setMinWidth(400);
		tablaP.getColumnModel().getColumn( 9).setMinWidth(130);
		tablaP.getColumnModel().getColumn(10).setMinWidth(80);
		tablaP.getColumnModel().getColumn(11).setMinWidth(80);
		tablaP.getColumnModel().getColumn(12).setMinWidth(80);
		
		String comandob = "select '','','','','','','','','','','','',''";
		String basedatos="26",pintar="si";
		ObjTab.Obj_Refrescar(tablaP,((DefaultTableModel) tablaP.getModel()), Cantidad_Real_De_Columnas, comandob, basedatos,pintar,checkboxindex);
		((DefaultTableModel) tablaP.getModel()).setRowCount(0);
	}
	
	public void init_tabla_principalBMS(JTable tablaP,boolean datosEncontrados){
		tablaP.getColumnModel().getColumn( 0).setMinWidth(30);
		tablaP.getColumnModel().getColumn( 0).setMaxWidth(30);
		tablaP.getColumnModel().getColumn( 1).setMinWidth(100);
		tablaP.getColumnModel().getColumn( 1).setMaxWidth(100);
		tablaP.getColumnModel().getColumn( 2).setMinWidth(80);
		tablaP.getColumnModel().getColumn( 3).setMinWidth(80);
		tablaP.getColumnModel().getColumn( 4).setMinWidth(80);
//		
		tablaP.getColumnModel().getColumn( 5).setMinWidth(200);
		tablaP.getColumnModel().getColumn( 6).setMinWidth(100);
		
		tablaP.getColumnModel().getColumn( 7).setMinWidth(40);
		tablaP.getColumnModel().getColumn( 8).setMinWidth(60);
//		
		if(datosEncontrados){
				((DefaultTableModel) tablaP.getModel()).setRowCount(0);
				String comandob = "buscar_pagos_a_proveedores_pendientes_de_conciliar '"+txtCuenta.getText()+"','"+txtFechaIn.getText()+"','"+txtFechaFin.getText()+"'";
				String basedatos="26",pintar="si";
				ObjTab.Obj_Refrescar(tablaP,((DefaultTableModel) tablaP.getModel()), 9, comandob, basedatos,pintar,checkboxindex);
		}else{
				String comandob = "select '','','','','','','','','','',''";
				String basedatos="26",pintar="si";
				ObjTab.Obj_Refrescar(tablaP,((DefaultTableModel) tablaP.getModel()), 9, comandob, basedatos,pintar,checkboxindex);
				((DefaultTableModel) tablaP.getModel()).setRowCount(0);
		}
	}
	
	public void recalcularConciliacion(double depositoP, double retirosP){
		
		double saldo = Double.valueOf(txtSaldoInicial.getText().trim());
		double depositos = Double.valueOf(txtDepositos.getText().trim());
		double retiros = Double.valueOf(txtRetiros.getText().trim());
		
		txtDepositos.setText(df.format(depositos+depositoP));
		txtRetiros.setText(df.format(retiros+retirosP));
		
		txtPreconciliacion.setText(df.format( (depositos+depositoP)-(retiros+retirosP) ));
		txtSaldoFinal.setText(df.format( saldo + (depositos+depositoP) - (retiros+retirosP) ));
	}
	
	ActionListener opDeshacer = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			
			if(JOptionPane.showConfirmDialog(null, "Se Perderan Los Cambios Realizados, Deséa Continuar?") == 0){
				
					modeloArchivo.setRowCount(0);
					modeloConciliados.setRowCount(0);
					modeloComisiones.setRowCount(0);
					modeloBMS.setRowCount(0);
					modeloRespaldo.setRowCount(0);
					
					txtCuenta.setText("");
					txtFechaIn.setText("");
					txtFechaFin.setText("");
					
					txtSaldoInicial.setText("");
					txtPreconciliacion.setText("");
					txtSaldoFinal.setText("");
					txtDepositos.setText("");
					txtRetiros.setText("");
					
					filaAnteriorArchivo = -1;
					filaAnteriorBMS = -1;
					filaAnteriorConciliaciones = -1;
					
			}else{
				return;
			}
		}
	};
	
	ActionListener opGuardar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			
			if( (modeloConciliados.getRowCount() + modeloComisiones.getRowCount()) > 0 ){
				
					int[] ignorarColumnas = {-1};
					String xml = new Obj_Xml.CrearXmlString().CadenaXML2(ArregloGuardar(), ignorarColumnas);
					 
						boolean guardado = new GuardarSQL().Guardar_Conciliacion_De_Cuenta_Bancaria(txtCuenta.getText().trim(), 
																									txtFechaIn.getText().trim(), 
																									txtFechaFin.getText().trim(), 
																									Double.valueOf(txtSaldoInicial.getText().trim()), 
																									Double.parseDouble(txtDepositos.getText().trim()), 
																									Double.parseDouble(txtRetiros.getText().trim()), 
																									Double.valueOf(txtSaldoFinal.getText().trim()), 
																									xml);
						 if(guardado){
							 	JOptionPane.showMessageDialog(null, "Los Registros Fueron Guardados Correctamente", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
				                return;
						 }else{
							 JOptionPane.showMessageDialog(null,  "A Ocurrido Un Error En El Guardado, Avise Al Administrador Del Sistema","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
							 return;
						 }
				 
			}else{
				JOptionPane.showMessageDialog(null,  "No Es Posible Guardar Sin Regitros Por Conciliar","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
				 return;
			}
			
		}
	};
	
	public Object[][] ArregloGuardar(){
		
		Object[][] array = new Object[modeloConciliados.getRowCount()+modeloComisiones.getRowCount()][12];
		
		for(int i = 0; i<modeloConciliados.getRowCount(); i++){
			
			array[i][0] = modeloConciliados.getValueAt(i, 1);
			array[i][1] = modeloConciliados.getValueAt(i, 2);
			array[i][2] = modeloConciliados.getValueAt(i, 3);
			array[i][3] = modeloConciliados.getValueAt(i, 4);
			array[i][4] = modeloConciliados.getValueAt(i, 5);
			array[i][5] = modeloConciliados.getValueAt(i, 6);
			array[i][6] = modeloConciliados.getValueAt(i, 7);
			array[i][7] = modeloConciliados.getValueAt(i, 8);
			array[i][8] = modeloConciliados.getValueAt(i, 9);
			array[i][9] = modeloConciliados.getValueAt(i, 10);
			array[i][10] = modeloConciliados.getValueAt(i, 11);
			array[i][11] = modeloConciliados.getValueAt(i, 12);
		}
		
		for(int i = 0; i<modeloComisiones.getRowCount(); i++){
			array[i+modeloConciliados.getRowCount()][0] = modeloComisiones.getValueAt(i, 0);
			array[i+modeloConciliados.getRowCount()][1] = modeloComisiones.getValueAt(i, 1);
			array[i+modeloConciliados.getRowCount()][2] = modeloComisiones.getValueAt(i, 2);
			array[i+modeloConciliados.getRowCount()][3] = modeloComisiones.getValueAt(i, 3);
			array[i+modeloConciliados.getRowCount()][4] = modeloComisiones.getValueAt(i, 4);
			array[i+modeloConciliados.getRowCount()][5] = modeloComisiones.getValueAt(i, 5);
			array[i+modeloConciliados.getRowCount()][6] = modeloComisiones.getValueAt(i, 6);
			array[i+modeloConciliados.getRowCount()][7] = modeloComisiones.getValueAt(i, 7);
			array[i+modeloConciliados.getRowCount()][8] = modeloComisiones.getValueAt(i, 8);
			array[i+modeloConciliados.getRowCount()][9] = modeloComisiones.getValueAt(i, 9);
			array[i+modeloConciliados.getRowCount()][10] = modeloComisiones.getValueAt(i, 10);
			array[i+modeloConciliados.getRowCount()][11] = modeloComisiones.getValueAt(i, 11);
		}
		
		return array;
	}
	
	ActionListener opConciliarTemporal = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			
			int filaSeleccionada = 0;
			int regSeleccionadosArch = 0;
			for(int i = 0; i<tablaArchivo.getRowCount() ;i++){
				if(Boolean.valueOf(tablaArchivo.getValueAt(i, 0).toString())){
					regSeleccionadosArch++;
					filaSeleccionada = i;
				}
			}
			
			if(regSeleccionadosArch==1){
				
				Object[] arregloTemporal = new Object[13];
				for(int i=0; i<tablaArchivo.getColumnCount(); i++){
						arregloTemporal[i] = i==0 ? "false" : i==modeloArchivo.getColumnCount()-3 ? "T" : modeloArchivo.getValueAt(filaSeleccionada, i);
				}
				
				recalcularConciliacion(Double.valueOf(arregloTemporal[3].toString().trim()), Double.valueOf(arregloTemporal[4].toString().trim()));
				modeloArchivo.removeRow(filaSeleccionada);
				modeloConciliados.addRow(arregloTemporal);
				
			}else{
				JOptionPane.showMessageDialog(null, "Verifícar Que Solo Se Haya Seleccionado 1 Registro En La Tabla De Archivo", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}
			
		}
	};
		
	ActionListener opConciliar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			
			int regSeleccionadosArch = 0;
			for(int i = 0; i<modeloArchivo.getRowCount() ;i++){
				if(Boolean.valueOf(modeloArchivo.getValueAt(i, 0).toString())){
					regSeleccionadosArch++;
				}
			}
			
			if(regSeleccionadosArch==1){
					int regSeleccionadosBMS = 0;
					for(int i = 0; i<modeloBMS.getRowCount() ;i++){
						if(Boolean.valueOf(modeloBMS.getValueAt(i, 0).toString())){
							regSeleccionadosBMS++;
						}
					}
				
					if(regSeleccionadosBMS==1){
						
						Object[] arregloConciliarRespaldo = new Object[22];
						Object[] arregloConciliar = new Object[16];
						
						for(int i=0; i<modeloRespaldo.getColumnCount(); i++){
							if(i<modeloArchivo.getColumnCount()){
								arregloConciliarRespaldo[i]= i==0  || i==modeloRespaldo.getColumnCount() ? "false" : modeloArchivo.getValueAt(filaAnteriorArchivo, i);
								arregloConciliar[i]= i==0 ? "false" : modeloArchivo.getValueAt(filaAnteriorArchivo, i);
							}else{
								arregloConciliarRespaldo[i]= i==0 || i==modeloRespaldo.getColumnCount() ? "false" : modeloBMS.getValueAt(filaAnteriorBMS, (i-(modeloArchivo.getColumnCount())) );
							}
						}
						
						System.out.println(arregloConciliarRespaldo[arregloConciliarRespaldo.length-1]);
						System.out.println(arregloConciliarRespaldo[arregloConciliarRespaldo.length-2]);
						
						modeloArchivo.removeRow(filaAnteriorArchivo);
						modeloBMS.removeRow(filaAnteriorBMS);
						
						recalcularConciliacion(Double.valueOf(arregloConciliar[3].toString().trim()), Double.valueOf(arregloConciliar[4].toString().trim()));
						
						modeloRespaldo.addRow(arregloConciliarRespaldo);
						modeloConciliados.addRow(arregloConciliar);
						
//						System.out.println(arregloConciliarRespaldo[arregloConciliarRespaldo.length-1]);
//						System.out.println(arregloConciliarRespaldo[arregloConciliarRespaldo.length-2]);
						
						modeloConciliados.setValueAt("M", modeloConciliados.getRowCount()-1, modeloConciliados.getColumnCount()-3);
						modeloConciliados.setValueAt(arregloConciliarRespaldo[arregloConciliarRespaldo.length-1], modeloConciliados.getRowCount()-1, modeloConciliados.getColumnCount()-1);
						modeloConciliados.setValueAt(arregloConciliarRespaldo[arregloConciliarRespaldo.length-2], modeloConciliados.getRowCount()-1, modeloConciliados.getColumnCount()-2);
						
						filaAnteriorArchivo = -1;
						filaAnteriorBMS = -1;
						
					}else{
						JOptionPane.showMessageDialog(null, "Verifícar Que Solo Se Haya Seleccionado 1 Registro En La Tabla De BMS", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
        				return;
					}
			}else{
				JOptionPane.showMessageDialog(null, "Verifícar Que Solo Se Haya Seleccionado 1 Registro En La Tabla De Archivo", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}
			
		}
	};
	
	ActionListener opExaminar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			
			JFileChooser elegir = new JFileChooser();
        	int opcion = elegir.showOpenDialog(null);
        	
             if(opcion == JFileChooser.APPROVE_OPTION){
                String pathArchivo = elegir.getSelectedFile().getPath(); //Obtiene path del archivo
                
     					if(pathArchivo.substring(pathArchivo.indexOf(".")+1, pathArchivo.length()).trim().toUpperCase().equals("TXT") ){
     						
     						DefaultTableModel model = null;
     						 try {
     							model = leerFile(pathArchivo);
								tabla = new JTable(model);
							} catch (IOException e1) {
								e1.printStackTrace();
							}
     						 
//     						 System.out.println(model.getValueAt(0, 0).toString());
	     						 if(!model.getValueAt(0, 0).toString().equals("Archivo Incorrecto")){
	     							 
		     							 modeloArchivo.setRowCount(0);
	        							 modeloBMS.setRowCount(0);
	        							 modeloConciliados.setRowCount(0);
	        							 modeloComisiones.setRowCount(0);
	     							 
			     							int[] ignorarColumnas = {-1};
			        						 String xml = new Obj_Xml.CrearXmlString().CadenaXML(tabla, ignorarColumnas);
			        						 Object[] guardarArchivo = new BuscarSQL().Saldos_De_Cuentas_Mov_Banco(xml);//
			        						 
			        						 if(!guardarArchivo[0].toString().equals("")){
			        							 
			        							 txtCuenta.setText(guardarArchivo[0].toString());
				           						 txtFechaIn.setText(guardarArchivo[1].toString());
				           						 txtFechaFin.setText(guardarArchivo[2].toString());
				           						 txtSaldoInicial.setText(guardarArchivo[3].toString());
			           						 
				           						 Object[][] RegistrosBanco = new BuscarSQL().Saldos_De_Cuentas_Mov_Conciliacion_Auto(guardarArchivo[0].toString(), guardarArchivo[1].toString(), guardarArchivo[2].toString());
			           						 
				           						 for(Object[] reg: RegistrosBanco){
				           							 
				           							int folio_transac = Integer.valueOf(reg[6].toString().trim());
				           							if(folio_transac==508 || folio_transac==512){
				           								
				           								if(reg[10].toString().trim().equals("A")){
				           									
					           									modeloConciliados.addRow(reg);
			           							 				//incrementar variables
			           							 				depositos += Double.valueOf(reg[3].toString().trim());
			           							 				retiros += Double.valueOf(reg[4].toString().trim());
		           							 				
				           								}else{
				           										modeloArchivo.addRow(reg);
				           								}
				           								
		           							 			
		           							 		}else{
		           							 			modeloComisiones.addRow(reg);
		           							 			//incrementar variables
			           							 		depositos += Double.valueOf(reg[3].toString().trim());
	           							 				retiros += Double.valueOf(reg[4].toString().trim());
		           							 		}
				           							
//				           							 switch(reg[10].toString().trim()){
//				           							 
//				           							 
//				           							 
//				           							 
//				           							 	case "A":	modeloConciliados.addRow(reg);
//				           							 				//incrementar variables
//				           							 				depositos += Double.valueOf(reg[3].toString().trim());
//				           							 				retiros += Double.valueOf(reg[4].toString().trim());
//				           							 				break;
//				           							 	default: 
//					           							 		int folio_transac = Integer.valueOf(reg[6].toString().trim());
//					           							 		if(folio_transac==508 || folio_transac==512){
//					           							 			modeloArchivo.addRow(reg);
//					           							 		}else{
//					           							 			modeloComisiones.addRow(reg);
//					           							 			//incrementar variables
//						           							 		depositos += Double.valueOf(reg[3].toString().trim());
//				           							 				retiros += Double.valueOf(reg[4].toString().trim());
//					           							 		}
//				           							 }
				           							
				           							
				           						 }
				           						 
				           						 tablaComisiones.removeColumn(tablaComisiones.getColumnModel().getColumn(0));
			           						 
				           						 init_tabla_principalBMS(tablaBMS,txtCuenta.getText().equals("")?false:true);
				           						 
				           						 txtDepositos.setText(df.format(depositos));
				           						 txtRetiros.setText(df.format(retiros));
				           						 txtPreconciliacion.setText(df.format( Double.valueOf(txtDepositos.getText()) - Double.valueOf(txtRetiros.getText()) ));
				           						 txtSaldoFinal.setText(df.format( Double.valueOf(txtSaldoInicial.getText())+Double.valueOf(txtDepositos.getText())-Double.valueOf(txtRetiros.getText()) ) ); 
				           						 
			        						 }else{
			        							JOptionPane.showMessageDialog(null, "No Se Pudo Guardar El Archivo Del Banco", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
			        							return;
			        						 }
			        						 
	     						 }else{
	     							JOptionPane.showMessageDialog(null, "El Archivo Seleccionado No Cuenta Con El Formato Requerido", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
	                				return;
	     						 }
         				}else{
         					JOptionPane.showMessageDialog(null, "Solo Se Pueden Cargar Imagenes Con Extencion TXT.", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
            				return;
         				}
             }
		}
	};
	
	@SuppressWarnings({ "resource", "unused" })
	public DefaultTableModel leerFile(String ruta) throws IOException{
		Object[] cabecera=null;
		Object[] contenido=null;
		
//			FileReader archivo = new FileReader("C:\\movimientos 03-04 oct.txt");
			FileReader archivo = new FileReader(ruta);
			BufferedReader bufferedWriter = new BufferedReader(archivo);
			
				String cadena = "";
				int cont=0;
				while( (cadena = bufferedWriter.readLine()) !=null){
					
					if(cont==0){
						
							if(cadena.trim().equals("Cuenta|Fecha de Operación|Fecha|Referencia|Descripcion|Cod. Transac|Sucursal|Depósitos|Retiros|Saldo|Movimiento|Descripción Detallada|Cheque")){
								cabecera = cadena.replace(" ", "_").split("\\|");
								int columnas=cabecera.length;
								modelo = new DefaultTableModel(null, cabecera);
							}else{
								modelo = new DefaultTableModel(null, new Object[]{"Aviso"});
								modelo.addRow(new Object[]{"Archivo Incorrecto"});
	            				return modelo;
							}
						
					}else{
						contenido = cadena.replace("$", "").split("\\|");
						modelo.addRow(contenido);
					}
					cont++;
				}
				
				for(int i=0; i<modelo.getRowCount(); i++){
					for(int j=0; j<modelo.getColumnCount(); j++){
						
						if(modelo.getValueAt(i, j)==null){
							modelo.setValueAt("", i, j);
						}
						if(j == 7 || j == 8 || j == 9){
							modelo.setValueAt(modelo.getValueAt(i, j).toString().replace(",", ""), i, j);
						}
					}
				}
				return modelo;
	}
	
			int filaAnteriorArchivo = -1;
			int filaAnteriorBMS = -1;
			int filaAnteriorConciliaciones = -1;
			private void agregar(final JTable tbl,String tablaName) {
		        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
			        public void mousePressed(MouseEvent e) {
			        	
			        	int filaAnterior = tablaName.equals("tbArchivo") ? filaAnteriorArchivo : (tablaName.equals("tbBMS") ? filaAnteriorBMS : filaAnteriorConciliaciones);
			        	
			        		int columna= tbl.getSelectedColumn();
			        		if(columna==0){
			        			
			        			int fila= tbl.getSelectedRow();
				        		
			        			if(filaAnterior!=-1){
			        				if(tbl.getRowCount()>0){
			        					tbl.setValueAt(false, filaAnterior, columna);
			        				}
			        			}
			        			
			        			//validar seleccion en tablas (si es la tabla conciliaciones y el registro seleccionado es Automatico )
		        				tbl.setValueAt(tablaName.equals("tbConciliaciones")&&tbl.getValueAt(fila, 10).equals("A")?false:true, fila, columna);

			        			if(tablaName.equals("tbArchivo")){
			        				filaAnteriorArchivo = fila;
			        			}
			        			if(tablaName.equals("tbBMS")){
			        				filaAnteriorBMS = fila;
			        			}
			        			if(tablaName.equals("tbConciliaciones")){
			        				filaAnteriorConciliaciones = fila;
			        			}
			        		}
			        }
		        });
		    }

			ActionListener opCancelar = new ActionListener(){
				public void actionPerformed(ActionEvent e){
					
//					System.out.println(tablaConciliada.getColumnCount()-3);
					for(int i = 0; i<tablaConciliada.getRowCount(); i++){
						
						if(Boolean.valueOf(tablaConciliada.getValueAt(i, 0).toString())){
							
							System.out.println(tablaConciliada.getValueAt(i, tablaConciliada.getColumnCount()-3).toString());
							if(tablaConciliada.getValueAt(i, tablaConciliada.getColumnCount()-3).toString()=="M"){
								
										for(int resp=0; resp<modeloRespaldo.getRowCount(); i++){
											
											//comparar registro de tabla de conciliado con modelo de respaldo para cancelacion
											if(tablaConciliada.getValueAt(i, 9).toString().trim().equals(modeloRespaldo.getValueAt(resp, 9).toString().trim())){
												
												//crear arreglo para insertarlo de nuevo a las tablas Archivos y De BMS
												Object[] regArchivo = new Object[13];
												regArchivo[0] = false;
												regArchivo[1] = modeloRespaldo.getValueAt(resp, 1);
												regArchivo[2] = modeloRespaldo.getValueAt(resp, 2);
												regArchivo[3] = modeloRespaldo.getValueAt(resp, 3);
												regArchivo[4] = modeloRespaldo.getValueAt(resp, 4);
												regArchivo[5] = modeloRespaldo.getValueAt(resp, 5);
												regArchivo[6] = modeloRespaldo.getValueAt(resp, 6);
												regArchivo[7] = modeloRespaldo.getValueAt(resp, 7);
												regArchivo[8] = modeloRespaldo.getValueAt(resp, 8);
												regArchivo[9] = modeloRespaldo.getValueAt(resp, 9);
												regArchivo[10] = modeloRespaldo.getValueAt(resp,10);
												regArchivo[11] = modeloRespaldo.getValueAt(resp,11);
												regArchivo[12] = modeloRespaldo.getValueAt(resp,12);
												modeloArchivo.addRow(regArchivo);
												
												Object[] regBMS = new Object[9];
												regBMS[0] = false;
												regBMS[1] = modeloRespaldo.getValueAt(resp, 14);
												regBMS[2] = modeloRespaldo.getValueAt(resp, 15);
												regBMS[3] = modeloRespaldo.getValueAt(resp, 16);
												regBMS[4] = modeloRespaldo.getValueAt(resp, 17);
												regBMS[5] = modeloRespaldo.getValueAt(resp, 18);
												regBMS[6] = modeloRespaldo.getValueAt(resp, 19);
												regBMS[7] = modeloRespaldo.getValueAt(resp, 20);
												regBMS[8] = modeloRespaldo.getValueAt(resp, 21);
												modeloBMS.addRow(regBMS);
												
												//se multiplica por -1 para que invierta las operaciones en la funcion
												recalcularConciliacion(Double.valueOf(regArchivo[3].toString().trim())*-1, Double.valueOf(regArchivo[4].toString().trim())*-1);
											
												modeloRespaldo.removeRow(resp);
												modeloConciliados.removeRow(i);
												i=0;
											}
										}
										
							}else{
								
								//crear arreglo para insertarlo de nuevo a las tablas Archivos y De BMS
								Object[] regArchivo = new Object[13];
								regArchivo[0]  = false;
								regArchivo[1]  = modeloConciliados.getValueAt (i, 1);
								regArchivo[2]  = modeloConciliados.getValueAt (i, 2);
								regArchivo[3]  = modeloConciliados.getValueAt (i, 3);
								regArchivo[4]  = modeloConciliados.getValueAt (i, 4);
								regArchivo[5]  = modeloConciliados.getValueAt (i, 5);
								regArchivo[6]  = modeloConciliados.getValueAt (i, 6);
								regArchivo[7]  = modeloConciliados.getValueAt (i, 7);
								regArchivo[8]  = modeloConciliados.getValueAt (i, 8);
								regArchivo[9]  = modeloConciliados.getValueAt (i, 9);
								regArchivo[10] = "";
								regArchivo[11] = "";
								regArchivo[12] = "";
								
								//se multiplica por -1 para que invierta las operaciones en la funcion
								recalcularConciliacion(Double.valueOf(regArchivo[3].toString().trim())*-1, Double.valueOf(regArchivo[4].toString().trim())*-1);
							
								modeloArchivo.addRow(regArchivo);
								modeloConciliados.removeRow(i);
							}
						}
					}
				}
			};
			
	public static void main(String[] args) {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Saldos_Cuentas_Movimientos_Conciliacion().setVisible(true);
		}catch(Exception e){	}
	}

}
