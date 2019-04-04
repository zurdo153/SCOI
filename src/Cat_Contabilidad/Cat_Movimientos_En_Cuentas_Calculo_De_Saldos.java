package Cat_Contabilidad;

import java.awt.Color;
import java.awt.Container;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Connexion;
import Conexiones_SQL.GuardarSQL;
import Obj_Contabilidad.Obj_Pagos_Emitidos;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_Filtro_Dinamico_Plus;
import Obj_Principal.Obj_tabla;

@SuppressWarnings("serial")
public class Cat_Movimientos_En_Cuentas_Calculo_De_Saldos extends JFrame{

	Obj_tabla ObjTab= new Obj_tabla();
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	int Cantidad_Real_De_Columnas=13,checkboxindex=1;
	@SuppressWarnings("rawtypes")
	public Class[] tipos(int incremento,String tb){
		Class[] tip = new Class[Cantidad_Real_De_Columnas+incremento];
		for(int i =0; i<Cantidad_Real_De_Columnas+incremento; i++){
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
	
	String[] nombreCabecerasConciliaciones = new String[]{"*","Fecha","Cheque","Deposito","Retiros","Transaccion","Cod_Transac","Descripcion","Descripcion Detallada","Folio_Mov","Status_Concil","Folio_Trans","Folio_BMS","Nota"};
	String[] nombreCabecerasComisiones = new String[]{"Fecha","Cheque","Deposito","Retiros","Transaccion","Cod_Transac","Descripcion","Descripcion Detallada","Folio_Mov","Status_Concil","Folio_Trans","Folio_BMS","Nota"};
	
	 public DefaultTableModel modeloArchivo = new DefaultTableModel(null, nombreCabeceras){
			@SuppressWarnings("rawtypes")
			Class[] types = tipos(0,"");
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
				Class[] types = tipos(0,"");
				@SuppressWarnings({ "unchecked", "rawtypes" })
				public Class getColumnClass(int columnIndex) {
			         return types[columnIndex];
			     }
				public boolean isCellEditable(int fila, int columna){
					if(columna==0)
						return true; return false;
				}
		    };
		    
	    public DefaultTableModel modeloConciliados = new DefaultTableModel(null, nombreCabecerasConciliaciones){
			 @SuppressWarnings("rawtypes")
				Class[] types = tipos(1,"");
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
		    
	    public DefaultTableModel modeloComisiones = new DefaultTableModel(null, nombreCabecerasComisiones){
			 @SuppressWarnings("rawtypes")
				Class[] types = tipos(0,"SIN_CHB");
				@SuppressWarnings({ "unchecked", "rawtypes" })
				public Class getColumnClass(int columnIndex) {
		         return types[columnIndex];
		     }
				public boolean isCellEditable(int fila, int columna){
					return false;
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
		JCButton btnCancelacionDePagosEmitidos = new JCButton("", "buscar.png", "Azul");
		JCButton btnNuevoPagoEmitido = new JCButton("Nuevo", "", "Azul");
		
		JTextField txtDepositos 		= new Componentes().text(new JCTextField(), "Depositos", 20, "Double");
		JTextField txtRetiros		 	= new Componentes().text(new JCTextField(), "Retiros", 20, "Double");
		JTextField txtPagosEmitidos		= new Componentes().text(new JCTextField(), "Pagos Emitidos", 20, "Double");
		
		JTextField txtSaldoInicial  	= new Componentes().text(new JCTextField(), "Saldo Inicial", 20, "Double");
		JTextField txtSaldoComciliado 	= new Componentes().text(new JCTextField(), "Saldo Conciliado", 20, "Double");
		JTextField txtSaldoDisponible	= new Componentes().text(new JCTextField(), "Saldo Disponible", 20, "Double");
		
		JTextField txtCuenta = new Componentes().text(new JCTextField(), "No Cuenta", 15, "Int");
		JTextField txtFechaIn = new Componentes().text(new JCTextField(), "Fecha Inicial", 15, "String");
		JTextField txtFechaFin = new Componentes().text(new JCTextField(), "Fecha Final", 15, "String");
		
		JTextField txtFiltro = new Componentes().text(new JCTextField(), "Filtro De Movimientos", 200, "String");
		
		DecimalFormat df = new DecimalFormat("#0.00");
		double depositos = 0;
		double retiros = 0;
		
	public Cat_Movimientos_En_Cuentas_Calculo_De_Saldos() {
		this.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds()); 
		int anchoVentana = Toolkit.getDefaultToolkit().getScreenSize().width;
		int altoVentana  = Toolkit.getDefaultToolkit().getScreenSize().height;
		
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/asistencia.png"));
		this.setTitle("Saldos Cuentas Movimientos Banco");
		panel.setBorder(BorderFactory.createTitledBorder("Movimientos De Cuenta"));

		int x=15,y=15,ancho=80;	
		
		panel.add(btnExaminar).setBounds           				(x							,y    						,ancho+28 				,20 				);
		panel.add(btnDeshacer).setBounds           				(x+ancho+30					,y    						,ancho+28 				,20 				);
		panel.add(btnGuardar).setBounds           				(x+ancho*2+60				,y    						,ancho+28 				,20 				);
		
		panel.add(new JLabel("Saldo Inicial:")).setBounds 		(x+(ancho*4)+30				,y	  						,ancho+30 				,20 				);
		panel.add(txtSaldoInicial).setBounds    				(x+(ancho*5)+30				,y	  						,ancho+30 				,20 				);
		panel.add(new JLabel("Depositos:")).setBounds    		(x+(ancho*7)+30				,y	  						,ancho+30 				,20 				);
		panel.add(txtDepositos).setBounds   	 				(x+(ancho*8)+30				,y	  						,ancho+30 				,20 				);
		
		panel.add(new JLabel("Pagos Emitidos Sin Contabilizar(Sin Cobrar):")).setBounds    		(x+(ancho*10)+30				,y	  						,ancho*3 				,20 				);
		panel.add(txtPagosEmitidos).setBounds					   	 							(x+(ancho*13)+30				,y	  						,ancho+30 				,20 				);
		panel.add(btnCancelacionDePagosEmitidos).setBounds		   	 							(x+(ancho*14)+60				,y	  						,30 					,20 				);
		panel.add(btnNuevoPagoEmitido).setBounds		   	 									(x+(ancho*14)+91				,y	  						,ancho 					,20 				);
		
		panel.add(new JLabel("Saldo Conciliado:")).setBounds    (x+(ancho*4)+30				,y+22 						,ancho+30 				,20 				);
		panel.add(txtSaldoComciliado).setBounds   				(x+(ancho*5)+30				,y+22 						,ancho+30 				,20 				);
		panel.add(new JLabel("Retiros:")).setBounds	    		(x+(ancho*7)+30				,y+22 						,ancho+30 				,20 				);
		panel.add(txtRetiros).setBounds  		  				(x+(ancho*8)+30				,y+22 						,ancho+30 				,20 				);
		
		panel.add(new JLabel("Saldo Disponible:")).setBounds  	(x+(ancho*4)+30				,y+44 						,ancho+30 				,20 				);
		panel.add(txtSaldoDisponible).setBounds         		(x+(ancho*5)+30				,y+44 						,ancho+30 				,20 				);
		
		panel.add(new JLabel("Cuenta:")).setBounds      		(x							,y+=25  					,ancho+30 				,20 				);
		panel.add(new JLabel("Fecha In:")).setBounds    		(x+ancho+30					,y      					,ancho+30 				,20 				);
		panel.add(new JLabel("Fecha Fin:")).setBounds   		(x+ancho*2+60				,y      					,ancho+30 				,20 				);
		
		panel.add(txtCuenta).setBounds             				(x							,y+=20  					,ancho+30 				,20 				);
		panel.add(txtFechaIn).setBounds            				(x+ancho+30					,y      					,ancho+30 				,20 				);
		panel.add(txtFechaFin).setBounds          				(x+ancho*2+60				,y      					,ancho+30 				,20 				);
		
		panel.add(btnConciliarTemporal).setBounds   			(x+((anchoVentana-50)/2)-200,y    						,ancho+120				,25 				);
		panel.add(btnConciliar).setBounds           			(x+((anchoVentana-50)/2)+10 ,y    						,ancho+30 				,25 				);
		panel.add(txtFiltro).setBounds           				(x+((anchoVentana)/2)+100 ,y    						,ancho*7 				,25 				);

		panel.add(scroll_tablaArchivo).setBounds  				(x							,y+=25  					,(anchoVentana-50)/2 	,(altoVentana-250)/3);
		panel.add(scroll_tablaBMS).setBounds   	  				(x+((anchoVentana-50)/2)+10 ,y							,(anchoVentana-50)/2 	,(altoVentana-250)/3);
		panel.add(btnCancelar).setBounds           				(x							,y+=((altoVentana-250)/3)+20,ancho+30    		 	,25 				);
		panel.add(scroll_tablaConciliada).setBounds  			(x							,y+=30       				,(anchoVentana-50)   	,(altoVentana-200)/3);
		panel.add(scroll_tablaComisiones).setBounds  			(x							,y+=((altoVentana-250)/3)+50,(anchoVentana-50)   	,(altoVentana-400)/3);
		
		txtCuenta.setEditable(false);
		txtFechaIn.setEditable(false);
		txtFechaFin.setEditable(false);
		
		txtSaldoInicial.setEditable(false);
		txtDepositos.setEditable(false);
		txtRetiros.setEditable(false);
		txtPagosEmitidos.setEditable(false);
		txtSaldoComciliado.setEditable(false);
		txtSaldoDisponible.setEditable(false);
				
		init_tabla_principal(tablaArchivo,"tbArchivo");
		init_tabla_principalBMS(tablaBMS,false);
		tablaBMS.getTableHeader().setReorderingAllowed(false);
		
		init_tabla_principal(tablaConciliada,"tbConciliaciones");
		init_tabla_principal(tablaComisiones,"tbComisiones");
		
		btnExaminar.addActionListener(opExaminar);
		btnDeshacer.addActionListener(opDeshacer);
		btnGuardar.addActionListener(opGuardar);
		btnCancelacionDePagosEmitidos.addActionListener(opCancelarPagosEmitidos);
		btnNuevoPagoEmitido.addActionListener(opNuevoPagosEmitidos);
		
		btnConciliar.addActionListener(opConciliar);
		btnConciliarTemporal.addActionListener(opConciliarTemporal);
		btnCancelar.addActionListener(opCancelar);
		txtFiltro.addKeyListener(opFiltro);
		
		agregar(tablaArchivo,"tbArchivo");
		agregar(tablaBMS,"tbBMS");
		agregar(tablaConciliada,"tbConciliaciones");
		
		cont.add(panel);
	}
	
	public void init_tabla_principal(JTable tablaP, String nombreTb){
		tablaP.getTableHeader().setEnabled(false);
		
		String basedatos="26",pintar="si";
		String comandob = "";
		
		if(nombreTb.equals("tbArchivo")){
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
			comandob = "select '','','','','','','','','','','','',''";
			ObjTab.Obj_Refrescar(tablaP,((DefaultTableModel) tablaP.getModel()), Cantidad_Real_De_Columnas, comandob, basedatos,pintar,checkboxindex);
		}
		if(nombreTb.equals("tbConciliaciones")){
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
			tablaP.getColumnModel().getColumn(13).setMinWidth(80);
			comandob = "select '','','','','','','','','','','','','',''";
			ObjTab.Obj_Refrescar(tablaP,((DefaultTableModel) tablaP.getModel()), Cantidad_Real_De_Columnas+1, comandob, basedatos,pintar,checkboxindex);
		}
		
		if(nombreTb.equals("tbComisiones")){
			tablaP.getColumnModel().getColumn( 0).setMinWidth(120);
			tablaP.getColumnModel().getColumn( 0).setMaxWidth(120);
			tablaP.getColumnModel().getColumn( 1).setMinWidth(100);
			tablaP.getColumnModel().getColumn( 2).setMinWidth(80);
			tablaP.getColumnModel().getColumn( 3).setMinWidth(80);
			tablaP.getColumnModel().getColumn( 4).setMinWidth(100);
			tablaP.getColumnModel().getColumn( 5).setMinWidth(100);
			tablaP.getColumnModel().getColumn( 6).setMinWidth(200);
			tablaP.getColumnModel().getColumn( 7).setMinWidth(400);
			tablaP.getColumnModel().getColumn( 8).setMinWidth(130);
			tablaP.getColumnModel().getColumn( 9).setMinWidth(80);
			tablaP.getColumnModel().getColumn(10).setMinWidth(80);
			tablaP.getColumnModel().getColumn(11).setMinWidth(80);
			tablaP.getColumnModel().getColumn(12).setMinWidth(30);
			tablaP.getColumnModel().getColumn(12).setMaxWidth(30);
			comandob = "select '','','','','','','','','','','','',''";
			ObjTab.Obj_Refrescar(tablaP,((DefaultTableModel) tablaP.getModel()), Cantidad_Real_De_Columnas, comandob, basedatos,pintar,-1);
		}
		((DefaultTableModel) tablaP.getModel()).setRowCount(0);
	}
	
	public void init_tabla_principalBMS(JTable tablaP,boolean datosEncontrados){
		tablaP.getTableHeader().setEnabled(false);
		
		tablaP.getColumnModel().getColumn( 0).setMinWidth(30);
		tablaP.getColumnModel().getColumn( 0).setMaxWidth(30);
		tablaP.getColumnModel().getColumn( 1).setMinWidth(100);
		tablaP.getColumnModel().getColumn( 1).setMaxWidth(100);
		tablaP.getColumnModel().getColumn( 2).setMinWidth(80);
		tablaP.getColumnModel().getColumn( 3).setMinWidth(80);
		tablaP.getColumnModel().getColumn( 4).setMinWidth(80);
		
		tablaP.getColumnModel().getColumn( 5).setMinWidth(200);
		tablaP.getColumnModel().getColumn( 6).setMinWidth(100);
		
		tablaP.getColumnModel().getColumn( 7).setMinWidth(40);
		tablaP.getColumnModel().getColumn( 8).setMinWidth(60);
	
		if(datosEncontrados){
				((DefaultTableModel) tablaP.getModel()).setRowCount(0);
				String comandob = "movimientos_en_cuenta_conciliacion_temporal_BMS '"+txtCuenta.getText()+"','"+txtFechaIn.getText()+"','"+txtFechaFin.getText()+"'";
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
		double pagosEmitidos = Double.valueOf(txtPagosEmitidos.getText().trim());
		
		txtDepositos.setText(df.format(depositos+depositoP));
		txtRetiros.setText(df.format(retiros+retirosP));
		
		txtSaldoComciliado.setText(df.format( saldo + (depositos+depositoP) - (retiros+retirosP) ));
		txtSaldoDisponible.setText( df.format( Double.valueOf(txtSaldoComciliado.getText().trim()) - pagosEmitidos ) );
	}
	
	ActionListener opDeshacer = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			
			if(JOptionPane.showConfirmDialog(null, "Se Perderan Los Cambios Realizados, Deséa Continuar?") == 0){
					deshacer();
			}else{
				return;
			}
		}
	};
	
	KeyListener opFiltro = new KeyListener() {
		public void keyTyped(KeyEvent e) {		}
		public void keyReleased(KeyEvent e) {
			int[] columnas = {1,2,3,4,5,6,7,8};
			new Obj_Filtro_Dinamico_Plus(tablaBMS,txtFiltro.getText().toString().trim(), columnas);
		}
		public void keyPressed(KeyEvent e) {		}
	};
	
	ActionListener opCancelarPagosEmitidos = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			
			if(!txtCuenta.getText().trim().equals("")){
				if(txtPagosEmitidos.getText().trim().equals("") || Double.valueOf(txtPagosEmitidos.getText().trim())==0 ){
					JOptionPane.showMessageDialog(null,  "No Existen Pagos Emitidos Vigentes","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
					return;
				}else{
					new Cat_Cancelacion_De_Pagos_Emitidos().setVisible(true);
				}
			}else{
				JOptionPane.showMessageDialog(null,  "Se Requiere Que Primero Se Cargue El Archivo Del Banco. [Examinar]","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
				return;
			}
			
		}
	};
	
	ActionListener opNuevoPagosEmitidos = new ActionListener(){
		public void actionPerformed(ActionEvent e){
				new Cat_Pagos_Emitidos().setVisible(true);
		}
	};
	
	public void deshacer(){
		modeloArchivo.setRowCount(0);
		modeloConciliados.setRowCount(0);
		modeloComisiones.setRowCount(0);
		modeloBMS.setRowCount(0);
		modeloRespaldo.setRowCount(0);
		
		txtCuenta.setText("");
		txtFechaIn.setText("");
		txtFechaFin.setText("");
		
		txtSaldoInicial.setText("");
		txtSaldoComciliado.setText("");
		txtSaldoDisponible.setText("");
		txtDepositos.setText("");
		txtRetiros.setText("");
		txtPagosEmitidos.setText("");
		
		filaAnteriorArchivo = -1;
		filaAnteriorBMS = -1;
		filaAnteriorConciliaciones = -1;
		
		depositos = 0;
		retiros = 0;
	}
	
	ActionListener opGuardar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			
			if( (modeloConciliados.getRowCount() + modeloComisiones.getRowCount()) > 0 ){
				
					if( modeloArchivo.getRowCount() == 0 ){
						
						filaAnteriorArchivo = -1;
						filaAnteriorBMS = -1;
						filaAnteriorConciliaciones = -1;
					
							int[] ignorarColumnas = {-1};
							String xml = new Obj_Xml.CrearXmlString().CadenaXML2(ArregloGuardar(), ignorarColumnas);
							 
								boolean guardado = new GuardarSQL().Guardar_Conciliacion_De_Cuenta_Bancaria(txtCuenta.getText().trim(), 
																											txtFechaIn.getText().trim(), 
																											txtFechaFin.getText().trim(), 
																											Double.valueOf(txtSaldoInicial.getText().trim()), 
																											Double.parseDouble(txtDepositos.getText().trim()), 
																											Double.parseDouble(txtRetiros.getText().trim()), 
																											xml,xmlBase);
								 if(guardado){
										String cuenta = txtCuenta.getText().toString().trim();
										String fechaIn = txtFechaIn.getText().toString().trim();
										String fechaFin = txtFechaFin.getText().toString().trim();

										String saldoInicial = txtSaldoInicial.getText().toString().trim();
										String depositos = txtDepositos.getText().toString().trim();
										String retiros = txtRetiros.getText().toString().trim();
										String saldoConciliado = txtSaldoComciliado.getText().toString().trim();
										String pagosEmitidos = txtPagosEmitidos.getText().toString().trim();
										String saldoDisponible = txtSaldoDisponible.getText().toString().trim();
										
										String Aviso = "Los Registros Fueron Guardados Correctamente."
														+"\n-No Cuenta: "+cuenta
														+"\n-Fecha Del: "+fechaIn+" Al "+fechaFin
														+"\n-Saldo Inicial: "+saldoInicial
														+"\n-Depositos: "+depositos
														+"\n-Retiros: "+retiros
														+"\n-Saldo Conciliado: "+saldoConciliado
														+"\n-Pagos Emitidos: "+pagosEmitidos
														+"\n-Saldo Disponible: "+saldoDisponible;
										
									 	deshacer();
									 	JOptionPane.showMessageDialog(null, Aviso, "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
						                return;
								 }else{
									 JOptionPane.showMessageDialog(null,  "A Ocurrido Un Error En El Guardado, Avise Al Administrador Del Sistema","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
									 return;
								 }
					}else{
						JOptionPane.showMessageDialog(null,  "Es Necesario Conciliar Todos Los Registros","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
						 return;
					}
			}else{
				JOptionPane.showMessageDialog(null,  "No Es Posible Guardar Sin Regitros Por Conciliar","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
				 return;
			}
			
		}
	};
	
	public Object[][] ArregloGuardar(){
		
		Object[][] array = new Object[modeloConciliados.getRowCount()+modeloComisiones.getRowCount()][7];
		
		for(int i = 0; i<modeloConciliados.getRowCount(); i++){
			
			array[i][0] = modeloConciliados.getValueAt(i, 3);
			array[i][1] = modeloConciliados.getValueAt(i, 4);
			array[i][2] = modeloConciliados.getValueAt(i, 9);
			array[i][3] = modeloConciliados.getValueAt(i, 10);
			array[i][4] = modeloConciliados.getValueAt(i, 11);
			array[i][5] = modeloConciliados.getValueAt(i, 12);
			array[i][6] = modeloConciliados.getValueAt(i, 13);
		}
		
		for(int i = 0; i<modeloComisiones.getRowCount(); i++){
			
			array[i+modeloConciliados.getRowCount()][0] = modeloComisiones.getValueAt(i, 2);
			array[i+modeloConciliados.getRowCount()][1] = modeloComisiones.getValueAt(i, 3);
			array[i+modeloConciliados.getRowCount()][2] = modeloComisiones.getValueAt(i, 8);
			array[i+modeloConciliados.getRowCount()][3] = modeloComisiones.getValueAt(i, 9);
			array[i+modeloConciliados.getRowCount()][4] = modeloComisiones.getValueAt(i,10);
			array[i+modeloConciliados.getRowCount()][5] = modeloComisiones.getValueAt(i,11);
			array[i+modeloConciliados.getRowCount()][6] = modeloComisiones.getValueAt(i,12);
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
				
					Object[] arregloTemporal = new Object[14];
					for(int i=0; i<tablaArchivo.getColumnCount(); i++){
							arregloTemporal[i] = i==0 ? "false" : i==modeloArchivo.getColumnCount()-3 ? "T" : modeloArchivo.getValueAt(filaSeleccionada, i);
					}
					arregloTemporal[13]="";
					new Cat_Conciliacion_Temporal(filaSeleccionada, txtCuenta.getText().trim(), arregloTemporal).setVisible(true);
			}else{
				JOptionPane.showMessageDialog(null, "Verifícar Que Solo Se Haya Seleccionado 1 Registro En La Tabla De Archivo", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}
			
		}
	};
		
	ActionListener opConciliar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			
			int regSeleccionadosArch = 0;
			for(int i = 0; i<tablaArchivo.getRowCount() ;i++){
				if(Boolean.valueOf(tablaArchivo.getValueAt(i, 0).toString())){
					regSeleccionadosArch++;
				}
			}
			
			if(regSeleccionadosArch==1){
				
					txtFiltro.setText("");
					int[] columnas = {1,2,3,4,5,6,7,8};
					new Obj_Filtro_Dinamico_Plus(tablaBMS,txtFiltro.getText().toString().trim(), columnas);
				
					int regSeleccionadosBMS = 0;
					int filaAEliminarBms = -1;
					for(int i = 0; i<tablaBMS.getRowCount() ;i++){
						if(Boolean.valueOf(tablaBMS.getValueAt(i, 0).toString())){
							regSeleccionadosBMS++;
							filaAEliminarBms=i;
						}
					}
				
					if(regSeleccionadosBMS==1){
						
							Object[] arregloConciliarRespaldo = new Object[22];
							Object[] arregloConciliar = new Object[16];
							
							for(int i=0; i<modeloRespaldo.getColumnCount(); i++){
								if(i<tablaArchivo.getColumnCount()){
									arregloConciliarRespaldo[i]= i==0  || i==modeloRespaldo.getColumnCount() ? "false" : tablaArchivo.getValueAt(filaAnteriorArchivo, i);
									arregloConciliar[i]= i==0 ? "false" : tablaArchivo.getValueAt(filaAnteriorArchivo, i);
								}else{
									arregloConciliarRespaldo[i]= i==0 || i==modeloRespaldo.getColumnCount() ? "false" : tablaBMS.getValueAt(filaAEliminarBms, (i-(tablaArchivo.getColumnCount())) );
								}
							}
							
							modeloArchivo.removeRow(filaAnteriorArchivo);
							modeloBMS.removeRow(filaAEliminarBms);
							
							recalcularConciliacion(Double.valueOf(arregloConciliar[3].toString().trim()), Double.valueOf(arregloConciliar[4].toString().trim()));
							
							modeloRespaldo.addRow(arregloConciliarRespaldo);
							modeloConciliados.addRow(arregloConciliar);
							
							modeloConciliados.setValueAt("M", modeloConciliados.getRowCount()-1, modeloConciliados.getColumnCount()-4);
							modeloConciliados.setValueAt("" , modeloConciliados.getRowCount()-1, modeloConciliados.getColumnCount()-1);
							modeloConciliados.setValueAt(arregloConciliarRespaldo[arregloConciliarRespaldo.length-1], modeloConciliados.getRowCount()-1, modeloConciliados.getColumnCount()-2);
							modeloConciliados.setValueAt(arregloConciliarRespaldo[arregloConciliarRespaldo.length-2], modeloConciliados.getRowCount()-1, modeloConciliados.getColumnCount()-3);
							
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
	
	String xmlBase = "";
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
     						 
	     						 if(!model.getValueAt(0, 0).toString().equals("Archivo Incorrecto")){
	     							 
		     							 modeloArchivo.setRowCount(0);
	        							 modeloBMS.setRowCount(0);
	        							 modeloConciliados.setRowCount(0);
	        							 modeloComisiones.setRowCount(0);
	        							 
	        							 depositos = 0;
        								 retiros = 0;
	     							 
			     							int[] ignorarColumnas = {-1};
			        						 xmlBase = new Obj_Xml.CrearXmlString().CadenaXML(tabla, ignorarColumnas);
			        						 Object[] guardarArchivo = new BuscarSQL().Saldos_De_Cuentas_Mov_Banco(xmlBase);
			        						 
//			        						 if(guardarArchivo.length==0){
//			        							 System.out.println("Los Archivo Ya Fue Cargado o Se Encuentra Vacío");
//			        							 return;
//			        						 }else{
			        							 System.out.println( guardarArchivo[0].toString().trim()+"   "+guardarArchivo[5].toString().trim());
//			        						 }
			        						 
			        						
			        						 
			        						 
			        						 
			        						 
			        						 if(guardarArchivo[5].toString().trim().equals("PRIMER REGISTRO") || guardarArchivo[5].toString().trim().equals("FOLIO CONSECUTIVO")){
					        							 if(!guardarArchivo[0].toString().equals("")){
						        							 
						        							 txtCuenta.setText(guardarArchivo[0].toString());
							           						 txtFechaIn.setText(guardarArchivo[1].toString());
							           						 txtFechaFin.setText(guardarArchivo[2].toString());
							           						 txtSaldoInicial.setText(guardarArchivo[3].toString());
							           						 txtPagosEmitidos.setText( df.format(Double.valueOf(guardarArchivo[4].toString())) );
						           						 
							           						 Object[][] RegistrosBanco = new BuscarSQL().Saldos_De_Cuentas_Mov_Conciliacion_Auto(guardarArchivo[0].toString(), guardarArchivo[1].toString(), guardarArchivo[2].toString(),xmlBase);
						           						 
							           						 for(Object[] reg: RegistrosBanco){
							           							 
							           							int folio_transac = Integer.valueOf(reg[6].toString().trim());
							           							if(folio_transac==508 || folio_transac==512 || folio_transac==511 ){
							           								
								           								if(reg[10].toString().trim().equals("A")){
								           									
									           									modeloConciliados.addRow(reg);
									           									modeloConciliados.setValueAt("", modeloConciliados.getRowCount()-1, modeloConciliados.getColumnCount()-1);
							           							 				//incrementar variables
							           							 				depositos += Double.valueOf(reg[3].toString().trim());
							           							 				retiros += Double.valueOf(reg[4].toString().trim());
						           							 				
								           								}else{
								           										modeloArchivo.addRow(reg);
								           								}
					           							 		}else{
					           							 			
					           							 			Object[] regConcil = new Object[reg.length-1];
					           							 			for(int i=0; i<regConcil.length; i++){
					           							 				regConcil[i] = reg[i+1].toString();
					           							 			}
					           							 			
					           							 			modeloComisiones.addRow(regConcil);
					           							 			modeloComisiones.setValueAt("", modeloComisiones.getRowCount()-1, modeloComisiones.getColumnCount()-1);
					           							 			//incrementar variables
						           							 		depositos += Double.valueOf(reg[3].toString().trim());
				           							 				retiros += Double.valueOf(reg[4].toString().trim());
					           							 		}
							           						 }
							           						 
							           						 init_tabla_principalBMS(tablaBMS,txtCuenta.getText().equals("")?false:true);
							           						 tablaBMS.getTableHeader().setReorderingAllowed(false);
							           						
							           						 txtDepositos.setText(df.format(depositos));
							           						 txtRetiros.setText(df.format(retiros));
							           						 txtSaldoComciliado.setText(df.format( Double.valueOf(txtSaldoInicial.getText())+Double.valueOf(txtDepositos.getText())-Double.valueOf(txtRetiros.getText()) ) );
							           						 txtSaldoDisponible.setText(df.format( Double.valueOf(txtSaldoComciliado.getText())-Double.valueOf(txtPagosEmitidos.getText().trim())) ); 
							           						  
						        						 }else{
						        							JOptionPane.showMessageDialog(null, "No Se Pudo Cargar El Archivo Del Banco", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
						        							return;
						        						 }
			        						 }else{
			        							 JOptionPane.showMessageDialog(null, "No Se Pudo Cargar El Archivo Del Banco,\nEl Archivo Que Intenta Cargar Esta Vacío O Le Faltan Movimientos.", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
			        							return;
			        						 }
			        						 
	     						 }else{
	     							JOptionPane.showMessageDialog(null, "El Archivo Seleccionado No Cuenta Con El Formato Requerido", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
	                				return;
	     						 }
         				}else{
         					JOptionPane.showMessageDialog(null, "Solo Se Pueden Cargar Archivos Con Extencion TXT.", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
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
						
						String cadena_tmp = "";
						cadena_tmp = getTextProcesaClean(cadena);
						
							if(cadena_tmp.trim().equals("Cuenta|Fecha de Operacion|Fecha|Referencia|Descripcion|Cod. Transac|Sucursal|Depositos|Retiros|Saldo|Movimiento|Descripcion Detallada|Cheque")){
								cabecera = cadena_tmp.replace(" ", "_").split("\\|");
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
	
	public String getTextProcesaClean(String input) {
	    String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
	    String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
	    
	    for(int i=0; i<original.length(); i++)
	    	input = input.replace(original.charAt(i), ascii.charAt(i));

	    return input;
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
		        				tbl.setValueAt(tablaName.equals("tbConciliaciones") && tbl.getValueAt(fila, 10).equals("A")?false:true, fila, columna);

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
					
					System.out.println(tablaConciliada.getRowCount()+" <- total de filas");
					
					for(int i = 0; i<tablaConciliada.getRowCount(); i++){
						
						if(Boolean.valueOf(tablaConciliada.getValueAt(i, 0).toString())){
							
							if(tablaConciliada.getValueAt(i, tablaConciliada.getColumnCount()-4).toString()=="M"){
								
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
//												i=0;
												break;
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
		
	public class Cat_Conciliacion_Temporal extends JDialog{
		
		Container contTemp = getContentPane();
		JLayeredPane panelTemp = new JLayeredPane();
		
		JTextField txtCuentaTemp			= new Componentes().text(new JCTextField(), "Cuenta Bancaria", 25, "String");
		JTextField txtCheque 				= new Componentes().text(new JCTextField(), "Cheque", 25, "String");
		JTextField txtTransaccion 			= new Componentes().text(new JCTextField(), "Transaccion", 25, "String");
		JTextField txtFechaTemp				= new Componentes().text(new JCTextField(), "Fecha", 25, "String");
		JTextField txtRetiroTemp 			= new Componentes().text(new JCTextField(), "Retiro", 25, "String");
		JTextField txtDepositoTemp			= new Componentes().text(new JCTextField(), "Deposito", 25, "String");
		
		JTextArea txaDescripcion 			= new Componentes().textArea(new JTextArea(), "Descripcion Completa", 1000);
		JScrollPane scrollDescripcion		= new JScrollPane(txaDescripcion);
		
		JTextArea txaDescripcionCompleta 	= new Componentes().textArea(new JTextArea(), "Descripcion Completa", 1000);
		JScrollPane scrollDescripcionComp	= new JScrollPane(txaDescripcionCompleta);
		
		JTextArea txaNota				 	= new Componentes().textArea(new JTextArea(), "Nota", 1000);	
		JScrollPane scrollNota	 			= new JScrollPane(txaNota);
		
		JCButton btnAceptar = new JCButton("Aceptar", "guardar.png", "Azult");
		
		int filaSeleccion = 0;
		Object[] arregloTemp;
		public Cat_Conciliacion_Temporal(int filaSelect, String cuenta, Object[] arregloParametro){
			
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/asistencia.png"));
			panelTemp.setBorder(BorderFactory.createTitledBorder("Movimientos De Cuenta"));
			this.setModal(true);
			
			int x=20,y=15,ancho=80;
			
			panelTemp.add(new JLabel("Cuenta: ")).setBounds(x,y,ancho,20);
			panelTemp.add(txtCuentaTemp).setBounds(x+ancho,y,ancho*2,20);
			
			panelTemp.add(new JLabel("Cheque: ")).setBounds(x,y+=25,ancho,20);
			panelTemp.add(txtCheque).setBounds(x+ancho,y,ancho*2,20);
			
			panelTemp.add(new JLabel("Transaccion: ")).setBounds(x,y+=25,ancho,20);
			panelTemp.add(txtTransaccion).setBounds(x+ancho,y,ancho*2,20);
			
			panelTemp.add(new JLabel("Retiro: ")).setBounds(x,y+=25,ancho,20);
			panelTemp.add(txtRetiroTemp).setBounds(x+ancho,y,ancho*2,20);
			
			panelTemp.add(new JLabel("Deposito: ")).setBounds(x,y+=25,ancho,20);
			panelTemp.add(txtDepositoTemp).setBounds(x+ancho,y,ancho*2,20);
			
			panelTemp.add(new JLabel("Fecha: ")).setBounds(x,y+=25,ancho,20);
			panelTemp.add(txtFechaTemp).setBounds(x+ancho,y,ancho*2,20);
			
			panelTemp.add(new JLabel("Descripcion: ")).setBounds(x=300,y=15,ancho*2,20);
			panelTemp.add(scrollDescripcion).setBounds(x,y+=17,ancho*5,55);
			
			panelTemp.add(new JLabel("Descripcion Completa: ")).setBounds(x,y+=55,ancho*2,20);
			panelTemp.add(scrollDescripcionComp).setBounds(x,y+=17,ancho*5,55);
			
			panelTemp.add(new JLabel("Nota: ")).setBounds(x=20,y+=60,ancho,20);
			panelTemp.add(scrollNota).setBounds(x,y+=17,ancho*8+40,65);
			
			panelTemp.add(btnAceptar).setBounds(x,y+=70,ancho*2,30);
			
			txtCuentaTemp.setEditable(false);
			txtCheque.setEditable(false);
			txtTransaccion.setEditable(false);
			txtRetiroTemp.setEditable(false);
			txtDepositoTemp.setEditable(false);
			txtFechaTemp.setEditable(false);
			txaDescripcion.setEditable(false);
			txaDescripcionCompleta.setEditable(false);
			txaNota.setBackground(Color.white);
			
			filaSeleccion = filaSelect;
			arregloTemp = arregloParametro;
			llenarAutomatico(cuenta);

			btnAceptar.addActionListener(opAceptar);
			contTemp.add(panelTemp);
			this.setTitle("Conciliacion Temporal");
			this.setSize(730,320);
			this.setLocationRelativeTo(null);
			this.setResizable(false);
			
		}
		
		public void llenarAutomatico(String cuent){
			txtCuentaTemp.setText(cuent);
			txtCheque.setText(arregloTemp[2].toString());
			txtTransaccion.setText(arregloTemp[5].toString());
			txtRetiroTemp.setText(arregloTemp[4].toString());
			txtDepositoTemp.setText(arregloTemp[3].toString());
			txtFechaTemp.setText(arregloTemp[1].toString());
			txaDescripcion.setText(arregloTemp[7].toString());
			txaDescripcionCompleta.setText(arregloTemp[8].toString());
		}
		
		ActionListener opAceptar = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				arregloTemp[13] = txaNota.getText().toString().trim().toUpperCase();
				recalcular();
				dispose();
			}
		};
		
		public void recalcular(){
			recalcularConciliacion(Double.valueOf(arregloTemp[3].toString().trim()), Double.valueOf(arregloTemp[4].toString().trim()));
			modeloArchivo.removeRow(filaSeleccion);
			modeloConciliados.addRow(arregloTemp);
		}
	}		
	
	//TODO Cancelacion De Pagos Emitidos
	public class Cat_Cancelacion_De_Pagos_Emitidos extends JDialog {
		Container cont = getContentPane();
		JLayeredPane campo = new JLayeredPane();
		Connexion con = new Connexion();
		Obj_tabla ObjTabf =new Obj_tabla();
		int columnas = 10,checkbox=1;
		public void init_tablaf(){
	    	this.tablaf.getColumnModel().getColumn(0).setMinWidth(30);
	    	this.tablaf.getColumnModel().getColumn(0).setMaxWidth(30);
	    	this.tablaf.getColumnModel().getColumn(1).setMinWidth(50);
	    	this.tablaf.getColumnModel().getColumn(2).setMinWidth(100);
	    	this.tablaf.getColumnModel().getColumn(3).setMinWidth(100);
	    	
	    	this.tablaf.getColumnModel().getColumn(4).setMinWidth(100);
	    	this.tablaf.getColumnModel().getColumn(5).setMinWidth(100);
	    	this.tablaf.getColumnModel().getColumn(6).setMinWidth(150);
	    	this.tablaf.getColumnModel().getColumn(7).setMinWidth(150);
	    	this.tablaf.getColumnModel().getColumn(8).setMinWidth(250);
	    	this.tablaf.getColumnModel().getColumn(9).setMinWidth(100);
	    	
			String comandof="exec movimientos_en_cuentas_pagos_emitidos_cancelacion";
			String basedatos="26",pintar="si";
			modelf.setRowCount(0);
			ObjTabf.Obj_Refrescar(tablaf,modelf, columnas, comandof, basedatos,pintar,checkbox);
	    }
		
		@SuppressWarnings("rawtypes")
		public Class[] base (){
			Class[] types = new Class[columnas];
			for(int i = 0; i<columnas; i++){
					if(i==0)
						types[i]= java.lang.Boolean.class;
					else
						types[i]= java.lang.Object.class;
				}
			return types;
		}
		
		 public DefaultTableModel modelf = new DefaultTableModel(null, new String[]{"*","Folio","Cuenta","Cheque","Fecha","Importe","Status_Cobro","Observacion","Usuario","Fecha Ult. Mod."}){
			 @SuppressWarnings("rawtypes")
				Class[] types = base();
				@SuppressWarnings({ "rawtypes", "unchecked" })
				public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
				public boolean isCellEditable(int fila, int columna){
					if(columna==0)
						return true;
					return false;}
		    };
		    JTable tablaf = new JTable(modelf);
			public JScrollPane scroll_tablaf = new JScrollPane(tablaf);
			
		     JTextField txtFolioFiltroEmpleado  = new Componentes().textfiltro(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<", 300, "String",tablaf,columnas);
		     JCButton btnCancelar         = new JCButton("Cancelar"    ,"Filter-List-icon16.png"      ,"Azul"); 
		     
		public Cat_Cancelacion_De_Pagos_Emitidos(){
			this.setSize(1040,650);
			this.setModal(true);
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/filter_icon&16.png"));
			this.setTitle("Filtro de Pagos Emitidos");
			campo.setBorder(BorderFactory.createTitledBorder("Filtro De Pagos Emitidos"));
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
			campo.add(scroll_tablaf).setBounds(15,42,1000,565);
			campo.add(txtFolioFiltroEmpleado).setBounds(15,20,500,20);
			campo.add(btnCancelar).setBounds(900,20,110,20);
			
			btnCancelar.addActionListener(opCancelar);
			
			init_tablaf();
			cont.add(campo);
			txtFolioFiltroEmpleado.requestFocus();
			
              getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put( KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "foco");
              getRootPane().getActionMap().put("foco", new AbstractAction(){       
            	  @Override
                  public void actionPerformed(ActionEvent e) {
                	  txtFolioFiltroEmpleado.setText("");
                	  txtFolioFiltroEmpleado.requestFocus();
                  }
              });
              
		}
		
		ActionListener opCancelar = new ActionListener() {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public void actionPerformed(ActionEvent e) {
				
				Vector vect = new Vector();
		    	for(int i=0; i<tablaf.getRowCount(); i++){
		    		if(Boolean.valueOf(tablaf.getValueAt(i, 0).toString())){
		    			vect.addElement(tablaf.getValueAt(i, 1));
		    		}
		    	}
		    	
		    	Object[][] listaDeFolios = new Object[1][vect.size()];
		    	for(int i=0; i<tablaf.getRowCount(); i++){
		    		listaDeFolios[0][i]=vect.get(i);
		    	}

		    	//armar XML
		    	int[] ignorarColumnas = {-1};
				String xml = new Obj_Xml.CrearXmlString().CadenaXML2(listaDeFolios, ignorarColumnas);
				
//				cancelar registro del xml
				new Obj_Pagos_Emitidos().cancelarPagosEmitidos(xml);
				
//				agregar a total de importes
				txtPagosEmitidos.setText(df.format(new Obj_Pagos_Emitidos().TotalPagosEmitidos(txtCuenta.getText().toString().trim())));
				
//				recalcular saldo disponible
				txtSaldoDisponible.setText( df.format(Double.valueOf(txtSaldoComciliado.getText().trim()) - Double.valueOf(txtPagosEmitidos.getText().trim())) );
				
				dispose();
			}
		};
		
	}
	
	public class Cat_Pagos_Emitidos extends JDialog{
		
		Container cont = getContentPane();
		JLayeredPane panel = new JLayeredPane();
		
		JToolBar menu_toolbar      = new JToolBar();
		
		JCButton btnNuevo          = new JCButton("Nuevo"     ,"Nuevo.png"                   ,"Azul");
		JCButton btnBuscar         = new JCButton("Buscar"    ,"Filter-List-icon16.png"      ,"Azul"); 
		JCButton btnEditar         = new JCButton("Editar"    ,"Filter-List-icon16.png"      ,"Azul"); 
		JCButton btnGuardar        = new JCButton("Guardar"   ,"Guardar.png"                 ,"Azul");
		JCButton btnDeshacer       = new JCButton("Deshacer"  ,"deshacer16.png"              ,"Azul");
		
		Object[] cuentas = new Obj_Pagos_Emitidos().cuentas(); 
//			{"Selecciona Una Cuenta","00000001","00000002"};
		@SuppressWarnings({ "rawtypes", "unchecked" })
		JComboBox cmbCuentas = new JComboBox(cuentas); 
		
		String[] status = {"Selecciona Un Status","COBRADO","PENDIENTE DE COBRO"};
		@SuppressWarnings({ "rawtypes", "unchecked" })
		JComboBox cmbStatus = new JComboBox(status);
		
		JDateChooser DCFecha = new Componentes().jchooser(new JDateChooser()  ,"",0);
		
		JTextField txtFolio = new Componentes().text(new JCTextField(), "Folio", 15, "Int");
		JTextField txtCheque = new Componentes().text(new JCTextField(), "Folio De Cheque", 15, "String");
		JTextField txtImporte = new Componentes().text(new JCTextField(), "Importe", 20, "Double");
		
		JTextArea txaObservacion = new Componentes().textArea(new JTextArea(), "Observacion", 500);
		JScrollPane scrollObservacion = new JScrollPane(txaObservacion);

		String bandera = "";
		public Cat_Pagos_Emitidos() {
			this.setModal(true);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/asistencia.png"));
			this.setTitle("Pagos Emitidos");
			panel.setBorder(BorderFactory.createTitledBorder("Capturar"));
			
		    this.menu_toolbar.add(btnNuevo    );
		    this.menu_toolbar.addSeparator(   );
		    this.menu_toolbar.addSeparator(   );
		    this.menu_toolbar.add(btnBuscar   );
		    this.menu_toolbar.addSeparator(   );
		    this.menu_toolbar.addSeparator(   );
		    this.menu_toolbar.add(btnEditar   );
		    this.menu_toolbar.addSeparator(   );
		    this.menu_toolbar.addSeparator(   );
			this.menu_toolbar.add(btnDeshacer );
			this.menu_toolbar.addSeparator(   );
			this.menu_toolbar.addSeparator(   );
			this.menu_toolbar.add(btnGuardar  );
			this.menu_toolbar.setFloatable(false);
			
			int x=20, y=15, ancho=80;
			
			panel.add(menu_toolbar).setBounds(x, y, ancho*5, 20);
			
			panel.add(new JLabel("Folio:")).setBounds(x, y+=30, ancho, 20);
			panel.add(txtFolio).setBounds(x+ancho, y, ancho*2, 20);
			
			panel.add(new JLabel("Cuenta:")).setBounds(x, y+=25, ancho, 20);
			panel.add(cmbCuentas).setBounds(x+ancho, y, ancho*2, 20);
			
			panel.add(new JLabel("Observacion:")).setBounds(x+(ancho*3)+30, y, ancho, 20);
			panel.add(scrollObservacion).setBounds(x+(ancho*3)+30, y+20, ancho*4, 100);
			
			panel.add(new JLabel("Cheque:")).setBounds(x, y+=25, ancho, 20);
			panel.add(txtCheque).setBounds(x+ancho, y, ancho*2, 20);
			
			panel.add(new JLabel("Fecha:")).setBounds(x, y+=25, ancho, 20);
			panel.add(DCFecha).setBounds(x+ancho, y, ancho*2, 20);
			
			panel.add(new JLabel("Importe:")).setBounds(x, y+=25, ancho, 20);
			panel.add(txtImporte).setBounds(x+ancho, y, ancho*2, 20);
			
			panel.add(new JLabel("Status:")).setBounds(x, y+=25, ancho, 20);
			panel.add(cmbStatus).setBounds(x+ancho, y, ancho*2, 20);
			
			cont.add(panel);
			
			deshacer();
			btnNuevo.addActionListener(opBtn);
			btnBuscar.addActionListener(opBtn);
			btnEditar.addActionListener(opBtn);
			btnDeshacer.addActionListener(opBtn);
			btnGuardar.addActionListener(opBtn);
			
			this.setSize(630,240);
			this.setLocationRelativeTo(null);
			this.setResizable(false);
		}
		
		ActionListener opBtn = new ActionListener(){
			public void actionPerformed(ActionEvent e){

				switch(e.getActionCommand()){
					case "Nuevo"	: 	nuevo(); 	break;
					case "Buscar"	: 	buscar(); 	break;
					case "Editar"	: 	editar(); 	break;
					case "Deshacer"	:	deshacer(); break;
					case "Guardar"	:	guardar(); 	break;
				}
			}
		};
		
		public void nuevo(){
			txtFolio.setText(new BuscarSQL().buscar_folio_consecutivo_por_folio_de_transaccion(50));//pagos emitidos
			blockNuevo();
		}
		
		public void buscar(){
			new Cat_Filtro_De_Pagos_Emitidos().setVisible(true);
			bandera = "MODIFICAR";
		}
		
		public void editar(){
			bandera="MODIFICAR";
			blockEditar();
		}
		
		public void deshacer(){
			blockDefault();
		}
		
		public void guardar(){
			
			String CamposRequeridos = validaCampos();
			
			if(CamposRequeridos.equals("")){
					Obj_Pagos_Emitidos pagos = new Obj_Pagos_Emitidos();
					
					pagos.setFolio(Integer.parseInt(txtFolio.getText().trim()));
					pagos.setCuenta(cmbCuentas.getSelectedItem().toString().trim());
					pagos.setCheque(txtCheque.getText().trim());
					pagos.setFecha(new SimpleDateFormat("dd/MM/yyyy").format(DCFecha.getDate()));
					pagos.setImporte(Double.valueOf(txtImporte.getText().trim()));
					pagos.setStatus_cobro(cmbStatus.getSelectedItem().toString().trim());
					pagos.setObservacion(txaObservacion.getText().toUpperCase().trim());
					pagos.setBandera(bandera);
					
					if(pagos.guardar()){
						
						if(!txtSaldoComciliado.getText().trim().equals("")){
//							agregar a total de importes
							txtPagosEmitidos.setText(df.format(pagos.TotalPagosEmitidos(txtCuenta.getText().toString().trim()))); //consulta  (pagos emitidos total)    <-- poner consulta en cancelacion de pagos emitidos
						
//							recalcular saldo disponible
							txtSaldoDisponible.setText( df.format(Double.valueOf(txtSaldoComciliado.getText().trim()) - Double.valueOf(txtPagosEmitidos.getText().trim())) );
						}

							deshacer();
							JOptionPane.showMessageDialog(null, "Los Registros Fueron Guardados Correctamente", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
							return;
					 }else{
							JOptionPane.showMessageDialog(null,  "A Ocurrido Un Error En El Guardado, Avise Al Administrador Del Sistema","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
							return;
					 }
			}else{
					JOptionPane.showMessageDialog(null,  "Los Siguientes Campos Son Requeridos:\n"+CamposRequeridos,"Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
					return;
			}
			
		}
		
		public String validaCampos(){
			String cadena = "";
			
				cadena+=txtFolio.getText().trim().equals("") ? "- Folio\n" : "";
				cadena+=cmbCuentas.getSelectedIndex()==0 ? "- Cuenta\n":"";
				cadena+=txtCheque.getText().trim().equals("") ? "- Cheque\n":"";
				cadena+=DCFecha.getDate()==null ? "- Fecha\n":"";
				cadena+=txtImporte.getText().trim().equals("") ? "- Importe\n":"";
				cadena+=cmbStatus.getSelectedIndex()==0 ? "- Status\n":"";
				cadena+=txaObservacion.getText().trim().equals("") ? "- Observacion\n":"";
			
			return cadena;
		}
		
		public void blockDefault(){
			txtFolio.setEditable(false);
			cmbCuentas.setEnabled(false);
			txtCheque.setEditable(false);
			DCFecha.setEnabled(false);
			txtImporte.setEditable(false);
			cmbStatus.setEnabled(false);
			txaObservacion.setEditable(false);
			txaObservacion.setBackground(new Color(Integer.parseInt("EBEBEB",16)));
			
			btnNuevo.setEnabled(true);
			btnBuscar.setEnabled(true);
			btnEditar.setEnabled(false);
			btnDeshacer.setEnabled(true);
			btnGuardar.setEnabled(false);
			
			txtFolio.setText("");
			cmbCuentas.setSelectedIndex(0);
			txtCheque.setText("");
			DCFecha.setDate(null);
			txtImporte.setText("");
			cmbStatus.setSelectedIndex(0);
			txaObservacion.setText("");
			
			bandera = "";
			cmbStatus.setSelectedItem("PENDIENTE DE COBRO");
		}
		
		public void blockNuevo(){
			txtFolio.setEditable(false);
			cmbCuentas.setEnabled(true);
			txtCheque.setEditable(true);
			DCFecha.setEnabled(true);
			txtImporte.setEditable(true);
//			cmbStatus.setEnabled(true);
			txaObservacion.setEditable(true);
			txaObservacion.setBackground(new Color(Integer.parseInt("FFFFFF",16)));
			
			btnNuevo.setEnabled(false);
			btnBuscar.setEnabled(false);
			btnEditar.setEnabled(false);
			btnDeshacer.setEnabled(true);
			btnGuardar.setEnabled(true);
			
			cmbCuentas.setSelectedIndex(0);
			txtCheque.setText("");
			DCFecha.setDate(null);
			txtImporte.setText("");
//			cmbStatus.setSelectedIndex(0);
			txaObservacion.setText("");
			
			bandera = "GUARDAR";
		}
		
		public void blockEditar(){
			txtFolio.setEditable(false);
			cmbCuentas.setEnabled(true);
			txtCheque.setEditable(true);
			DCFecha.setEnabled(true);
			txtImporte.setEditable(true);
//			cmbStatus.setEnabled(true);
			txaObservacion.setEditable(true);
			txaObservacion.setBackground(new Color(Integer.parseInt("FFFFFF",16)));
			
			btnNuevo.setEnabled(false);
			btnBuscar.setEnabled(false);
			btnEditar.setEnabled(false);
			btnDeshacer.setEnabled(true);
			btnGuardar.setEnabled(true);
			
			bandera = "MODIFICAR";
		}
		
		//TODO filtro Busqueda de Pagos Emitidos
			public class Cat_Filtro_De_Pagos_Emitidos extends JDialog {
				Container cont = getContentPane();
				JLayeredPane campo = new JLayeredPane();
				Connexion con = new Connexion();
				Obj_tabla ObjTabf =new Obj_tabla();
				int columnas = 9,checkbox=-1;
				public void init_tablaf(){
			    	this.tablaf.getColumnModel().getColumn(0).setMinWidth(50);
			    	this.tablaf.getColumnModel().getColumn(0).setMaxWidth(50);
			    	this.tablaf.getColumnModel().getColumn(1).setMinWidth(100);
			    	this.tablaf.getColumnModel().getColumn(2).setMinWidth(100);
			    	this.tablaf.getColumnModel().getColumn(3).setMinWidth(100);
			    	
			    	this.tablaf.getColumnModel().getColumn(4).setMinWidth(100);
			    	this.tablaf.getColumnModel().getColumn(5).setMinWidth(150);
			    	this.tablaf.getColumnModel().getColumn(6).setMinWidth(150);
			    	this.tablaf.getColumnModel().getColumn(7).setMinWidth(250);
			    	this.tablaf.getColumnModel().getColumn(8).setMinWidth(100);
			    	
					String comandof="exec movimientos_en_cuentas_pagos_emitidos_filtro";
					String basedatos="26",pintar="si";
					ObjTabf.Obj_Refrescar(tablaf,modelf, columnas, comandof, basedatos,pintar,checkbox);
			    }
				
				@SuppressWarnings("rawtypes")
				public Class[] base (){
					Class[] types = new Class[columnas];
					for(int i = 0; i<columnas; i++){types[i]= java.lang.Object.class;}
					return types;
				}
				
				 public DefaultTableModel modelf = new DefaultTableModel(null, new String[]{"Folio","Cuenta","Cheque","Fecha","Importe","Status_Cobro","Observacion","Usuario","Fecha Ult. Mod."}){
					 @SuppressWarnings("rawtypes")
						Class[] types = base();
						@SuppressWarnings({ "rawtypes", "unchecked" })
						public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
						public boolean isCellEditable(int fila, int columna){return false;}
				    };
				    JTable tablaf = new JTable(modelf);
					public JScrollPane scroll_tablaf = new JScrollPane(tablaf);
				     JTextField txtFolioFiltroEmpleado  = new Componentes().textfiltro(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<", 300, "String",tablaf,columnas);

				public Cat_Filtro_De_Pagos_Emitidos(){
					this.setSize(1040,650);
					this.setModal(true);
					this.setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/filter_icon&16.png"));
					this.setTitle("Filtro de Pagos Emitidos");
					campo.setBorder(BorderFactory.createTitledBorder("Filtro De Pagos Emitidos"));
					this.setResizable(false);
					this.setLocationRelativeTo(null);
					this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				
					campo.add(scroll_tablaf).setBounds(15,42,1000,565);
					campo.add(txtFolioFiltroEmpleado).setBounds(15,20,300,20);
					
					init_tablaf();
					agregar(tablaf);
					cont.add(campo);
					txtFolioFiltroEmpleado.requestFocus();
					
		              getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put( KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "foco");
		              getRootPane().getActionMap().put("foco", new AbstractAction(){       
		            	  @Override
		                  public void actionPerformed(ActionEvent e) {
		                	  txtFolioFiltroEmpleado.setText("");
		                	  txtFolioFiltroEmpleado.requestFocus();
		                  }
		              });
		              
		       		this.agregar(tablaf);
				}
				
				private void agregar(final JTable tbl) {
					tbl.addMouseListener(new MouseListener() {
						public void mouseReleased(MouseEvent e) {
					 	 if(e.getClickCount() == 2){try {
							funcion_agregar();
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}}
						}
						public void mousePressed(MouseEvent e) {}
						public void mouseExited(MouseEvent e)  {}
						public void mouseEntered(MouseEvent e) {}
						public void mouseClicked(MouseEvent e) {}
					});
					tbl.addKeyListener(new KeyListener() {
						public void keyPressed(KeyEvent e)  {
							if(e.getKeyCode()==KeyEvent.VK_ENTER){
								try {
									funcion_agregar();
								} catch (ParseException e1) {
									e1.printStackTrace();
								}	
							}
						}
						public void keyReleased(KeyEvent e)   {}
						public void keyTyped   (KeyEvent e)   {}
					});
			    }
			    public void funcion_agregar() throws ParseException {
			    	
			    	btnNuevo.setEnabled(false);
			    	btnBuscar.setEnabled(false);
			    	btnEditar.setEnabled(true);
			    	
			    	int fila = tablaf.getSelectedRow();

			    	txtFolio.setText(tablaf.getValueAt(fila, 0).toString().trim());
					cmbCuentas.setSelectedItem(tablaf.getValueAt(fila, 1).toString().trim());
					txtCheque.setText(tablaf.getValueAt(fila, 2).toString().trim());
					DCFecha.setDate(fecha(tablaf.getValueAt(fila, 3).toString().trim()));
					txtImporte.setText(tablaf.getValueAt(fila, 4).toString().trim());
					cmbStatus.setSelectedItem(tablaf.getValueAt(fila, 5).toString().trim());
					txaObservacion.setText(tablaf.getValueAt(fila, 6).toString().trim());
					
					dispose();
			    };
				
			}
			
			public Date fecha(String fechap) {
				Date fecha = null;
				try {if(fechap.equals("")){	fecha=null;	}else {	fecha=new SimpleDateFormat("dd/MM/yyyy").parse(fechap);	}
				} catch (ParseException e) {
					e.printStackTrace();
				}
				return fecha;
			} 
	}
			
	public static void main(String[] args) {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Movimientos_En_Cuentas_Calculo_De_Saldos().setVisible(true);
		}catch(Exception e){	}
	}
}
