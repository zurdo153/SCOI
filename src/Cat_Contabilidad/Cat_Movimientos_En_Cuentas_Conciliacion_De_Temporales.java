package Cat_Contabilidad;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
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
import Obj_Contabilidad.Obj_Pagos_Emitidos;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_tabla;

@SuppressWarnings("serial")
public class Cat_Movimientos_En_Cuentas_Conciliacion_De_Temporales extends JFrame{

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
		    
		    JTable tablaArchivo = new JTable(modeloArchivo);
			public JScrollPane scroll_tablaArchivo = new JScrollPane(tablaArchivo);

			JTable tablaConciliada = new JTable(modeloConciliados);
			public JScrollPane scroll_tablaConciliada = new JScrollPane(tablaConciliada);
			
			JTable tablaBMS = new JTable(modeloBMS);
			public JScrollPane scroll_tablaBMS = new JScrollPane(tablaBMS);
			
		DefaultTableModel modelo = null;
		JTable tabla = null;
		
		DefaultTableModel modeloRespaldo = new DefaultTableModel(0, 22);
		
		JCButton btnBuscar = new JCButton("", "buscar.png", "Azul");
		JCButton btnDeshacer = new JCButton("", "deshacer16.png", "Azul");
		JCButton btnGuardar  = new JCButton("", "Guardar.png", "Azul");
		
		JCButton btnConciliar = new JCButton("Conciliar", "agregar - copia.png", "AzulC");
		JCButton btnCancelar = new JCButton("Cancelar", "cancelar-icono-4961-16.png", "AzulC");
		
		JTextField txtPagosEmitidos		= new Componentes().text(new JCTextField(), "Pagos Emitidos", 20, "Double");
		
		JTextField txtSaldoComciliado 	= new Componentes().text(new JCTextField(), "Saldo Conciliado", 20, "Double");
		JTextField txtSaldoDisponible	= new Componentes().text(new JCTextField(), "Saldo Disponible", 20, "Double");
		
		Object[] cuentas = new Obj_Pagos_Emitidos().cuentas(); 
		@SuppressWarnings({ "rawtypes", "unchecked" })
		JComboBox cmbCuentas = new JComboBox(cuentas); 
		
		DecimalFormat df = new DecimalFormat("#0.00");
		double depositos = 0;
		double retiros = 0;
		
	public Cat_Movimientos_En_Cuentas_Conciliacion_De_Temporales() {
//		this.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds()); 
		int anchoVentana = Toolkit.getDefaultToolkit().getScreenSize().width;
		int altoVentana  = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setSize(anchoVentana,altoVentana-280);
		
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/asistencia.png"));
		this.setTitle("Saldos Cuentas Movimientos Banco");
		panel.setBorder(BorderFactory.createTitledBorder("Movimientos De Cuenta"));

		int x=15,y=15,ancho=80;	
		
		panel.add(new JLabel("Cuenta:")).setBounds      		(x							,y		  					,ancho+30 				,20 				);
		panel.add(cmbCuentas).setBounds             			(x+40						,y		  					,ancho*2 				,20 				);
		panel.add(btnBuscar).setBounds           				(x+ancho*2+40				,y    						,30 					,20 				);
		panel.add(btnDeshacer).setBounds           				(x+ancho*2+71				,y    						,30 					,20 				);
		panel.add(btnGuardar).setBounds           				(x+ancho*3+22				,y    						,30		 				,20 				);
		
		panel.add(new JLabel("Saldo Conciliado:")).setBounds 	(x+(ancho*4)+30				,y	  						,ancho+30 				,20 				);
		panel.add(txtSaldoComciliado).setBounds    				(x+(ancho*5)+30				,y	  						,ancho+30 				,20 				);
		
		panel.add(new JLabel("Pagos Emitidos Sin Contabilizar(Sin Cobrar):")).setBounds (x+(ancho*7)+10		,y			,ancho*3 				,20 				);
		panel.add(txtPagosEmitidos).setBounds					   	 					(x+(ancho*10)-15	,y			,ancho+30 				,20 				);
		
		panel.add(new JLabel("Saldo Disponible:")).setBounds    (x+(ancho*4)+30				,y+=22 						,ancho+30 				,20 				);
		panel.add(txtSaldoDisponible).setBounds   				(x+(ancho*5)+30				,y	 						,ancho+30 				,20 				);
		
		panel.add(btnConciliar).setBounds           			(x+((anchoVentana-50)/2)+10 ,y    						,ancho+30 				,25 				);
		
		panel.add(scroll_tablaArchivo).setBounds  				(x							,y+=25  					,(anchoVentana-50)/2 	,(altoVentana-250)/3);
		panel.add(scroll_tablaBMS).setBounds   	  				(x+((anchoVentana-50)/2)+10 ,y							,(anchoVentana-50)/2 	,(altoVentana-250)/3);
		panel.add(btnCancelar).setBounds           				(x							,y+=((altoVentana-250)/3)+20,ancho+30    		 	,25 				);
		panel.add(scroll_tablaConciliada).setBounds  			(x							,y+=30       				,(anchoVentana-50)   	,(altoVentana-200)/3);
		
		btnGuardar.setEnabled(false);
		txtPagosEmitidos.setEditable(false);
		txtSaldoComciliado.setEditable(false);
		txtSaldoDisponible.setEditable(false);
				
		init_tabla_principal(tablaArchivo,"tbArchivo");
		init_tabla_principalBMS(tablaBMS,false);
		tablaBMS.getTableHeader().setReorderingAllowed(false);
		
		init_tabla_principal(tablaConciliada,"tbConciliaciones");
		
		btnBuscar.addActionListener(opBuscar);
		btnDeshacer.addActionListener(opDeshacer);
		btnGuardar.addActionListener(opGuardar);
		
		btnConciliar.addActionListener(opConciliar);
		btnCancelar.addActionListener(opCancelar);
		
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
				String comandob = "movimientos_en_cuenta_conciliacion_temporal_BMS '"+cmbCuentas.getSelectedItem().toString().trim()+"','"+""+"','"+""+"'";//TODO(cambiar procedimiento almacenado --mostrar todos los registros ???? -- )
				String basedatos="26",pintar="si";
				ObjTab.Obj_Refrescar(tablaP,((DefaultTableModel) tablaP.getModel()), 9, comandob, basedatos,pintar,checkboxindex);
		}else{
				String comandob = "select '','','','','','','','','','',''";
				String basedatos="26",pintar="si";
				ObjTab.Obj_Refrescar(tablaP,((DefaultTableModel) tablaP.getModel()), 9, comandob, basedatos,pintar,checkboxindex);
				((DefaultTableModel) tablaP.getModel()).setRowCount(0);
		}
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
	
	public void deshacer(){
		modeloArchivo.setRowCount(0);
		modeloConciliados.setRowCount(0);
		modeloBMS.setRowCount(0);
		modeloRespaldo.setRowCount(0);
		
		cmbCuentas.setSelectedIndex(0);
		
		txtSaldoComciliado.setText("");
		txtSaldoDisponible.setText("");
		txtPagosEmitidos.setText("");
		
		filaAnteriorArchivo = -1;
		filaAnteriorBMS = -1;
		filaAnteriorConciliaciones = -1;
		
		depositos = 0;
		retiros = 0;
		
		cmbCuentas.setEnabled(true);
		btnBuscar.setEnabled(true);
		btnGuardar.setEnabled(false);
	}
	
	ActionListener opBuscar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			
			if(cmbCuentas.getSelectedIndex()>0){
					cmbCuentas.setEnabled(false);
					btnBuscar.setEnabled(false);
					btnGuardar.setEnabled(true);
					
					Object[] buscarSaldos = new BuscarSQL().Saldos_De_Cuentas_Sin_Conciliacion_Automatica(cmbCuentas.getSelectedItem().toString().trim());
					 txtSaldoComciliado.setText(df.format(buscarSaldos[0]));
					 txtPagosEmitidos.setText(df.format(buscarSaldos[1]));
					 txtSaldoDisponible.setText(df.format(buscarSaldos[2])); 
						 
					Object[][] RegistrosBanco = new BuscarSQL().Saldos_De_Cuentas_Mov_Conciliacion_Temporal(cmbCuentas.getSelectedItem().toString().trim());
					for(Object[] reg: RegistrosBanco){
						modeloArchivo.addRow(reg);
					}
					
					init_tabla_principalBMS(tablaBMS,cmbCuentas.getSelectedIndex()==0?false:true);
					 tablaBMS.getTableHeader().setReorderingAllowed(false);
			}else{
					 JOptionPane.showMessageDialog(null,  "Se Requiere Seleccionar Una Cuenta","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
					 return;
			}
		}
	};
	
	ActionListener opGuardar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			
			if( modeloConciliados.getRowCount() > 0 ){
				
					int[] ignorarColumnas = {-1};
					String xml = new Obj_Xml.CrearXmlString().CadenaXML2(ArregloGuardar(), ignorarColumnas);
					
						boolean guardado = new GuardarSQL().Guardar_Conciliacion_De_Cuenta_Bancaria_De_Temporales(cmbCuentas.getSelectedItem().toString().trim(),xml);
						 if(guardado){
							 	deshacer();
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
		
		Object[][] array = new Object[modeloConciliados.getRowCount()][7];
		
		for(int i = 0; i<modeloConciliados.getRowCount(); i++){
			
			array[i][0] = modeloConciliados.getValueAt(i, 3);
			array[i][1] = modeloConciliados.getValueAt(i, 4);
			array[i][2] = modeloConciliados.getValueAt(i, 9);
			array[i][3] = modeloConciliados.getValueAt(i, 10);
			array[i][4] = modeloConciliados.getValueAt(i, 11);
			array[i][5] = modeloConciliados.getValueAt(i, 12);
			array[i][6] = modeloConciliados.getValueAt(i, 13);
		}
		
		return array;
	}
	
	ActionListener opConciliar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			
			int regSeleccionadosArch = 0;
			for(int i = 0; i<tablaArchivo.getRowCount() ;i++){
				if(Boolean.valueOf(tablaArchivo.getValueAt(i, 0).toString())){
					regSeleccionadosArch++;
				}
			}
			
			if(regSeleccionadosArch==1){
				
					int regSeleccionadosBMS = 0;
					for(int i = 0; i<tablaBMS.getRowCount() ;i++){
						if(Boolean.valueOf(tablaBMS.getValueAt(i, 0).toString())){
							regSeleccionadosBMS++;
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
									arregloConciliarRespaldo[i]= i==0 || i==modeloRespaldo.getColumnCount() ? "false" : tablaBMS.getValueAt(filaAnteriorBMS, (i-(tablaArchivo.getColumnCount())) );
								}
							}
							
							modeloArchivo.removeRow(filaAnteriorArchivo);
							modeloBMS.removeRow(filaAnteriorBMS);
							
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
			new Cat_Movimientos_En_Cuentas_Conciliacion_De_Temporales().setVisible(true);
		}catch(Exception e){	}
	}
}
