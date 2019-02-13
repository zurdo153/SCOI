package Cat_Contabilidad;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import Conexiones_SQL.ActualizarSQL;
import Conexiones_SQL.BuscarSQL;
import Obj_Contabilidad.Obj_Pagos_Emitidos;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.Obj_tabla;
import Obj_Xml.CrearXmlString;

@SuppressWarnings("serial")
public class Cat_Movimientos_En_Cuentas_Cancelacion_De_Conciliados_Temporales extends JFrame{

	Obj_tabla ObjTab= new Obj_tabla();
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JDateChooser fhInicial = new Componentes().jchooser(new JDateChooser()  ,"",0);
	JDateChooser fhFinal = new Componentes().jchooser(new JDateChooser()  ,"",0);
	
	Object[] cuentas = new Obj_Pagos_Emitidos().cuentas(); 
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbCuentas = new JComboBox(cuentas); 
	
	JTextArea txaObservacion = new Componentes().textArea(new JTextArea(), "Observacion", 500);
	JScrollPane scrollObservacion = new JScrollPane(txaObservacion);
	
	JButton btnBuscar = new JCButton("", "buscar.png", "Azul");
	JButton btnDeshacer = new JCButton("", "deshacer16.png", "Azul");
	JButton btnRestaurar = new JCButton("Restaurar", "guardar.png", "Azul");
	
	int Columnas=9,checkboxindex=1;
	@SuppressWarnings("rawtypes")
	public Class[] tipos(int incremento,String tb){
		Class[] tip = new Class[Columnas+incremento];
		for(int i =0; i<Columnas+incremento; i++){
			if(i==checkboxindex-1){
				tip[i]=java.lang.Boolean.class;
			}else{
				tip[i]=java.lang.Object.class;
			}
		}
		return tip;
	}
	
	String[] nombreCabeceras = new String[]{"*","Cuenta","Cheque","Descripcion","Descripcion Detallada","Deposito","Retiros","Transaccion_BMS","Folio_BMS"};
	public DefaultTableModel modelo = new DefaultTableModel(null, nombreCabeceras){
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
    JTable tabla = new JTable(modelo);
	public JScrollPane scroll = new JScrollPane(tabla);
	
	public void init_tabla(boolean buscar){
		
		((DefaultTableModel) tabla.getModel()).setRowCount(0);
		
		tabla.getTableHeader().setEnabled(false);
		
		String basedatos="26",pintar="si";
		
			tabla.getColumnModel().getColumn( 0).setMinWidth(30);
			tabla.getColumnModel().getColumn( 0).setMaxWidth(30);
			tabla.getColumnModel().getColumn( 1).setMinWidth(80);
			tabla.getColumnModel().getColumn( 2).setMinWidth(80);
			tabla.getColumnModel().getColumn( 3).setMinWidth(350);
			tabla.getColumnModel().getColumn( 4).setMinWidth(620);
			tabla.getColumnModel().getColumn( 5).setMinWidth(80);
			tabla.getColumnModel().getColumn( 6).setMinWidth(130);
			tabla.getColumnModel().getColumn(7).setMinWidth(80);
			tabla.getColumnModel().getColumn(8).setMinWidth(80);
			
			String fechaIn = new SimpleDateFormat("dd/MM/yyyy").format(fhInicial.getDate())+" 00:00";
			String fechaFin = new SimpleDateFormat("dd/MM/yyyy").format(fhFinal.getDate())+" 23:59";
			
			String comandob = "declare @cuenta varchar(25) , @fechaIn datetime , @fechaFin datetime"
					+ " set @cuenta = '"+cmbCuentas.getSelectedItem().toString().trim()+"' "
					+ " set @fechaIn = '"+fechaIn+"' "
					+ " set @fechaFin = '"+fechaFin+"'"
					+ " select 'false' as selector,"
					+ "			mc.Cuenta, "
					+ "			mc.Referencia as Cheque, "
					+ "			mc.Descripcion, "
					+ "			mc.Descripcion_Detallada, "
					+ "			mc.Depositos, "
					+ "			mc.Retiros, "
					+ "			mc.folio_transaccion_bms as Folio_Transaccion_Bms, "
					+ "			mc.folio_bms as Folio_Bms "
					+ " from movimientos_en_cuenta mc "
					+ " where mc.Cuenta = case @cuenta when 'Selecciona Una Cuenta' then mc.Cuenta else @cuenta end "
					+ " and mc.observacion_temp = 'TEMPORAL' "
					+ " and folio_transaccion_bms !='' "
					+ " and folio_bms !='' "
					+ " and mc.Fecha between @fechaIn and @fechaFin";
			
			ObjTab.Obj_Refrescar(tabla,((DefaultTableModel) tabla.getModel()), Columnas, buscar?comandob:"select '','','','','','','','',''", basedatos,pintar,checkboxindex);
			
			if(!buscar){
				((DefaultTableModel) tabla.getModel()).setRowCount(0);
			}
			
	}
    
	public Cat_Movimientos_En_Cuentas_Cancelacion_De_Conciliados_Temporales() {
		int anchoVentana = Toolkit.getDefaultToolkit().getScreenSize().width;
		int altoVentana  = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setSize(anchoVentana,altoVentana-450);
		
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/asistencia.png"));
		this.setTitle("Saldos Cuentas Movimientos Banco");
		panel.setBorder(BorderFactory.createTitledBorder("cancelacion De Movimientos De Cuenta Temporales"));

		int x=15,y=15,ancho=80;	
		
		panel.add(new JLabel("Fecha De:")).setBounds(x				,y		,ancho+30 				,20 				);
		panel.add(fhInicial).setBounds             	(x+50			,y		,ancho+20 				,20 				);
		panel.add(new JLabel("A:")).setBounds  		(x+160			,y		,ancho+30 				,20 				);

		panel.add(fhFinal).setBounds             	(x+170			,y		,ancho+20 				,20 				);
		
		panel.add(new JLabel("Cuenta:")).setBounds  (x				,y+=25	,ancho+30 				,20 				);
		panel.add(cmbCuentas).setBounds             (x+50			,y		,ancho*3-20				,20 				);
//		panel.add(btnBuscar).setBounds           	(x+ancho*2+40	,y    	,30 					,20 				);
//		panel.add(btnDeshacer).setBounds           	(x+ancho*2+71	,y    	,30 					,20 				);
		panel.add(btnBuscar).setBounds          	(x+ancho*3+32	,y    	,30		 				,20 				);
		panel.add(btnDeshacer).setBounds          	(x+ancho*3+65	,y    	,30		 				,20 				);
		panel.add(btnRestaurar).setBounds           (x				,y+=35	,130					,20 				);
		panel.add(scroll).setBounds  				(x				,y+=25  ,(anchoVentana-50) 		,(altoVentana-300)/2);

		camposDefault(true);
		
		fhInicial.setDate(cargar_fechas(7));
		fhFinal.setDate(cargar_fechas(0));
		init_tabla(false);
		btnBuscar.addActionListener(opRecargar);
		btnDeshacer.addActionListener(opDeshacer);
		btnRestaurar.addActionListener(opRestaurar);
		
		cont.add(panel);
	}
	
	public void camposDefault(boolean select){
		fhInicial.setEnabled(select);
		fhFinal.setEnabled(select);
		cmbCuentas.setEnabled(select);
		btnBuscar.setEnabled(select);
		btnRestaurar.setEnabled(!select);
		
		if(select){
			fhInicial.setDate(cargar_fechas(7));
			fhFinal.setDate(cargar_fechas(0));
			cmbCuentas.setSelectedIndex(0);
		}
	}
	
	public Date cargar_fechas(int dias){
		Date date1 = null;
				  try {
					date1 = new SimpleDateFormat("dd/MM/yyyy").parse(new BuscarSQL().fecha(dias));
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		  return date1;
	};
	
	ActionListener opRecargar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			
			String camposRequeridos = validaCampos();
			
			if(camposRequeridos.equals("")){
				init_tabla(true);
				
				if(tabla.getRowCount()<=0){
					
					String fechaIn = new SimpleDateFormat("dd/MM/yyyy").format(fhInicial.getDate());
					String fechaFin = new SimpleDateFormat("dd/MM/yyyy").format(fhFinal.getDate());
					
					String parametros = "- Fecha Inicial: "+fechaIn+"\n- Fecha Final: "+fechaFin+"\n- Cuenta: "+cmbCuentas.getSelectedItem();
					
					camposDefault(true);
					JOptionPane.showMessageDialog(null,  "No Se Encontraron Registros Con Los Parametros Especificados:\n"+parametros,"Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
					return;
				}else{
					camposDefault(false);
				}
				
			}else{
				JOptionPane.showMessageDialog(null,  "Los Siguientes Campos Son Requeridos:\n"+camposRequeridos+"Verifique Que Esten Alimentados Correctamente","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
				return;
			}
		}
	};
	
	public String validaCampos(){
		String error = "";
		
			error += (fhInicial.getDate()+"").equals("null")?"- Fecha Inicial\n":"";
			error += (fhFinal.getDate()+"").equals("null")?"- Fecha Final\n":"";
			error += cmbCuentas.getSelectedIndex()==0?"- Cuenta\n":"";
		
		return error;
	}
	
	String[] nombreCabecerasParametro = new String[]{"Cuenta","Cheque","Transaccion_BMS","Folio_BMS"};
	public DefaultTableModel modeloParametro = new DefaultTableModel(null, nombreCabecerasParametro);
	JTable tb_parametro = new JTable(modeloParametro);
	
	ActionListener opRestaurar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			
			Object[] reg = new Object[4];
			
			for(int i=0; i<tabla.getRowCount(); i++){
				if(Boolean.valueOf(tabla.getValueAt(i, 0).toString().trim())){
					reg[0] = modelo.getValueAt(i, 1).toString().trim(); 
					reg[1] = modelo.getValueAt(i, 2).toString().trim(); 
					reg[2] = modelo.getValueAt(i, 7).toString().trim(); 
					reg[3] = modelo.getValueAt(i, 8).toString().trim(); 
					
					modeloParametro.addRow(reg);
				}
			}
			
			if(modeloParametro.getRowCount()>0){
				
					int[] columnas = {-1};
					String xml = new CrearXmlString().CadenaXML(tb_parametro, columnas);
					if(new ActualizarSQL().Restaurar_Conciliacion_De_Cuenta_Bancaria_Temporales(xml)){
						init_tabla(true);
						JOptionPane.showMessageDialog(null, "Los Registros Fueron Restaurados Correctamente", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
		                return;
					}else{
						JOptionPane.showMessageDialog(null,  "A Ocurrido Un Error Al Reastaurar El Registro, Avise Al Administrador Del Sistema","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
						 return;
					}
				
			}else{
				JOptionPane.showMessageDialog(null,  "Es Necesario Seleccionar Registros En La Tabla","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
				return;
			}
			
		}
	};
	
	ActionListener opDeshacer = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			
			camposDefault(true);
			modelo.setRowCount(0);
		}
	};

	public static void main(String[] args) {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Movimientos_En_Cuentas_Cancelacion_De_Conciliados_Temporales().setVisible(true);
		}catch(Exception e){	}
	}
}
