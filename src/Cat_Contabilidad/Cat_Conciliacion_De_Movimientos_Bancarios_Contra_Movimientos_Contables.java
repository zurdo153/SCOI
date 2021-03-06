package Cat_Contabilidad;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import com.toedter.calendar.JDateChooser;

import Conexiones_SQL.ActualizarSQL;
import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Connexion;
import Conexiones_SQL.Generacion_Reportes;
import IZAGAR_Obj.Obj_Conciliacion_de_Movimientos_Bancarios_Contra_Contabilidad;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Conciliacion_De_Movimientos_Bancarios_Contra_Movimientos_Contables  extends JFrame{
	Container cont = getContentPane();
	JTabbedPane pestanas = new JTabbedPane();
	
	JLayeredPane campo = new JLayeredPane();
	JLayeredPane reportes = new JLayeredPane();
	
	Object[][] Matriz_Mov_Bancarios ;	
	Object[][] Matriz_Mov_Contabilidad ;
	Object[][] Matriz_Conciliados;
	
	JCButton btnGuardar         = new JCButton("Guardar","Guardar.png","Azul");
	JCButton btnBorrar          = new JCButton("Correjir","eliminar-bala-icono-7773-32.png","Rojo");
	JCButton btnEliminarBanco   = new JCButton("Cancelar","eliminar-bala-icono-7773-32.png","Rojo");
	JCButton btnActualizar      = new JCButton("Actualizar","Actualizar.png","Verde");
	
	JCButton btnConciliar                               = new JCButton("Conciliar","double-arrow-icone-3883-16.png","AzulC");
	JCButton btnConciliaAutoImporte                     = new JCButton("Conciliacion Automatica Por Importe","reconstruir-icono-6593-16.png","AzulC");
	JCButton btnConciliaAutoImporteyReferencia          = new JCButton("Conciliacion Automatica Importe y Referencia","reconstruir-icono-6593-16.png","AzulC");
	
	JCButton btnMovimientosContablesPendientesConciliar = new JCButton("Movimientos Contables Sin Conciliar","Lista.png","Azul");
	JCButton btnMovimientosBancariosPendientesConciliar = new JCButton("Movimientos Bancarios Sin Conciliar","Lista.png","Azul");
	JCButton btnReporteConciliado                       = new JCButton("Movimientos Conciliados"            ,"Lista.png","Azul");
	JCButton btnReporteComparacion_Entre_conciliaciones = new JCButton("Diferencias De Conciliaciones"      ,"Lista.png","Azul");
	
	JButton btntabladesconciliar =new JButton("Desconciliar");
	
	String CuentasBancarias[] = new Obj_Conciliacion_de_Movimientos_Bancarios_Contra_Contabilidad().Combo_CuentasBancarias();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbCuentasBancarias = new JComboBox(CuentasBancarias);
	
	JLabel lblTablaMovBancarios = new JLabel("Movimientos Bancarios");
	JLabel lblTablaMovContabilidad = new JLabel("Movimientos Contabilidad");
	JLabel lblTablaConciliados = new JLabel("Movimientos Conciliados");
	
	JDateChooser cfecha = new JDateChooser();
	JDateChooser cfecha_conciliado = new JDateChooser();
	
	JTextField txtBanco = new Componentes().text( new JTextField(), "Nombre Del Banco", 100, "String");
	JTextField txtCuentaContable = new Componentes().text( new JTextField(), "Cuenta Contable", 100, "String");
    JTextField txtSaldoBanco = new Componentes().text(new JTextField(), "Total de Importe Selecionado de Movimiento Bancarios", 20, "Float");
    JTextField txtSaldoContabilidad = new Componentes().text(new JTextField(), "Total de Importe Selecionado de Movimiento Contabilidad", 20, "Float");
    
//TABLA MOVIMIENTOS BANCARIOS
	DefaultTableModel modelobancarios = new DefaultTableModel(null,
            new String[]{"Concepto", "Fecha","Mov","Importe","Referencia","CodEstab+id","Conciliar" }
			){
	     @SuppressWarnings("rawtypes")
		Class[] types = new Class[]{
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.Boolean.class
	    	                       };
	     @SuppressWarnings({ "rawtypes", "unchecked" })
		public Class getColumnClass(int columnIndex) {
             return types[columnIndex];
         }
         public boolean isCellEditable(int fila, int columna){
        	 switch(columna){
        	 	case 0 : return false; 
        	 	case 1 : return false; 
        	 	case 2 : return false;
        	 	case 3 : return false;
        	 	case 4 : return false;
        	 	case 5 : return false;
        	 	case 6 : return true;
        	 	} 				
 			return false;
 		}
	};
	JTable tabla_mov_bancarios = new JTable(modelobancarios);
    JScrollPane scroll = new JScrollPane(tabla_mov_bancarios);
    
  //TABLA MOVIMIENTOS CONTABILIDAD 
	DefaultTableModel modelocontabilidad = new DefaultTableModel(null,
            new String[]{"Conciliar", "Poliza","Tipo","Mes A�o","Fecha","Mov","Importe","Referencia","Concepto","CodEstab+id"}
			){
	     @SuppressWarnings("rawtypes")
		Class[] types = new Class[]{
	    	java.lang.Boolean.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class     };
	     @SuppressWarnings({ "rawtypes", "unchecked" })
		public Class getColumnClass(int columnIndex) {
             return types[columnIndex];
         }
         public boolean isCellEditable(int fila, int columna){
        	 switch(columna){
        	 	case 0 : return true; 
        	 	case 1 : return false; 
        	 	case 2 : return false;
        	 	case 3 : return false;
        	 	case 4 : return false;
        	 	case 5 : return false;
        	 	case 6 : return false;
        	 	case 7 : return false;
        	 	case 8 : return false;
        	 	case 9 : return false;	 } 				
 			return false;
 		}
	};
	
    JTable tabla_mov_contabilidad = new JTable(modelocontabilidad);
    JScrollPane scroll_Mov_Contabilidad = new JScrollPane(tabla_mov_contabilidad);
	
//TABLA MOVIMIENTOS CONCILIADOS
	DefaultTableModel modeloconciliados= new DefaultTableModel(null,
            new String[]{"Desconciliar","Fecha Conciliado","Concepto Banco", "Fecha Banco","MovBanco","Cod Estab + ID Banco","Importe Banco","Referencia Banco","Importe Pol","Referencia Pol","Poliza","Tipo","Mes A�o Pol","Fecha Pol","Mov Pol","Concepto Pol","CodEstab + ID Poliza"}
			){
		@SuppressWarnings("rawtypes")
		Class[] types = new Class[]{
			javax.swing.JButton.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class
                                    };
	    
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Class getColumnClass(int columnIndex) {
             return types[columnIndex];
         }
         public boolean isCellEditable(int fila, int columna){
        	 switch(columna){
        	 	case 0 : return true; 
        	 	case 1 : return false; 
        	 	case 2 : return false;
        	 	case 3 : return false;
        	 	case 4 : return false;
        	 	case 5 : return false;
        	 	case 6 : return false;
        	 	case 7 : return false;
        	 	case 8 : return false;
        	 	case 9 : return false;
        	 	case 10 : return false;
        	 	case 11 : return false;
        	 	case 12 : return false;
        	 	case 13 : return false;
        	 	case 14 : return false;
        	 	case 15 : return false;
        	 	case 16 : return false;
        	 } 				
 			return false;
 		}
	};
	
    JTable tablaconciliados = new JTable(modeloconciliados);
    JScrollPane scrollconciliados = new JScrollPane(tablaconciliados);
	
	//TODO INCIA CONSTRUCTOR
	public Cat_Conciliacion_De_Movimientos_Bancarios_Contra_Movimientos_Contables() {
		int ancho = Toolkit.getDefaultToolkit().getScreenSize().width;
		int alto = Toolkit.getDefaultToolkit().getScreenSize().height;

		pestanas.addTab("Conciliacion", campo);
		pestanas.addTab("Reportes", reportes);
		
		cont.add(pestanas);
		setSize(ancho,alto);
		setResizable(false);
		setLocationRelativeTo(null);
		setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/reconstruir-icono-6593-64.png"));
		setTitle("Conciliacion De Movimientos Bancarios Contra Contabilidad");
		this.campo.setBorder(BorderFactory.createTitledBorder("Conciliacion De Movimientos Bancarios Contra Contabilidad"));
		this.reportes.setBorder(BorderFactory.createTitledBorder("Reportes de Colaboradores"));
		
		lblTablaMovBancarios.setFont(new Font("arial", Font.BOLD, 14));	
		lblTablaMovContabilidad.setFont(new Font("arial", Font.BOLD, 14));	
		lblTablaConciliados.setFont(new Font("arial", Font.BOLD, 14));	
		lblTablaMovBancarios.setForeground(new java.awt.Color(0,0,100));
		lblTablaMovContabilidad.setForeground(new java.awt.Color(0,0,100));
		lblTablaConciliados.setForeground(new java.awt.Color(0,0,100));

		scroll =new JScrollPane(tabla_mov_bancarios);
		scroll_Mov_Contabilidad = new JScrollPane(tabla_mov_contabilidad);

		int y=0 ;int width=300;int height=20;
		campo.add(new JLabel ("Cuenta:")).setBounds            (15  ,y+=20 ,100,height);
		campo.add(cmbCuentasBancarias).setBounds               (60  ,y     ,100,height);
		campo.add(txtBanco).setBounds                          (165 ,y    ,113,height);
		campo.add(new JLabel ("Cuenta Contable:")).setBounds   (285 ,y    ,100,height);
		campo.add(txtCuentaContable).setBounds                 (375 ,y    ,100,height);
		campo.add(btnConciliaAutoImporte).setBounds            (485 ,y    ,335,height);
		campo.add(btnActualizar).setBounds                     (830 ,y    ,120,height);
		campo.add(btnEliminarBanco).setBounds                  (960 ,y    ,120,height);
		
		campo.add(new JLabel ("Fecha:")).setBounds             (15  ,y+=30,100,20);
		campo.add(cfecha).setBounds                            (60  ,y,100,20);
        campo.add(new JLabel ("Fecha Conciliacion:")).setBounds(285 ,y,100,20);
        campo.add(cfecha_conciliado).setBounds                 (375 ,y,100,20);
        campo.add(btnConciliaAutoImporteyReferencia).setBounds (485 ,y, 335, 20);
        campo.add(btnConciliar).setBounds                      (830 ,y, 120, 20);
        campo.add(btnBorrar).setBounds                         (960 ,y,120,20);
        campo.add(btnGuardar).setBounds                        (1090,y,120,20);
        
    	int y2=(alto/2);
        int x=(ancho/2);
        
		campo.add(lblTablaMovBancarios).setBounds(15,y+=30,x-60, 35);
		campo.add(txtSaldoBanco ).setBounds(x-115,y+5,100,20);;
    	campo.add(scroll).setBounds(15,y+30,x-30,y2-80);
		campo.add(txtSaldoContabilidad).setBounds(x,y+5,100,20);;
	    campo.add(lblTablaMovContabilidad).setBounds(x+300,y, 300, 35);
		campo.add(scroll_Mov_Contabilidad).setBounds(x,y+30,x-30,y2-80);
		campo.add(lblTablaConciliados).setBounds(15,y2+=30,630, 35);
		campo.add(scrollconciliados).setBounds(15,y2+30,ancho-45,(alto/2)-100);
     	
		y=30;x=30;height=40;
		reportes.add(btnMovimientosContablesPendientesConciliar).setBounds  (x      ,y     ,width ,height);
		reportes.add(btnMovimientosBancariosPendientesConciliar).setBounds  (x+=400 ,y     ,width ,height);
        reportes.add(btnReporteConciliado).setBounds                        (x-=400 ,y+=60 ,width ,height);
        reportes.add(btnReporteComparacion_Entre_conciliaciones).setBounds  (x+=400 ,y     ,width ,height);
        
		txtBanco.setEnabled(false);
		txtCuentaContable.setEnabled(false);
		txtSaldoBanco.setEnabled(false);
        txtSaldoContabilidad.setEnabled(false);
		
        cfecha.setDate(cargar_fecha_Sugerida(1));;
        cfecha_conciliado.setDate(cargar_fecha_Sugerida(1));;
		cargar_datos_cuenta_bancaria();
		
//    	AGREGAR BOTON A LA TABLA 
    	tablaconciliados.getColumn("Desconciliar").setCellRenderer(new ButtonRenderer());
    	tablaconciliados.getColumn("Desconciliar").setCellEditor(new ButtonEditor(new JCheckBox()));
////		ACCION DEL BOTON DE LA table
		btntabladesconciliar.addActionListener(opDesconciliar);
		btntabladesconciliar.setBackground(Color.ORANGE);
		
		this.addWindowListener(op_cerrar);
		btnActualizar.addActionListener(opActualizar);
		btnConciliar.addActionListener(opConciliar);
		btnConciliaAutoImporte.addActionListener(opConciliacion_por_importe);
		btnConciliaAutoImporteyReferencia.addActionListener(opConciliacion_por_importe_y_Referencia);
		btnGuardar.addActionListener(opGuardarConciliacion);
		btnBorrar.addActionListener(opBorrar_ventana_emergente);
		btnMovimientosContablesPendientesConciliar.addActionListener(opReporte_De_Movimientos_Contables_Pendientes_De_Conciliar);
		btnMovimientosBancariosPendientesConciliar.addActionListener(opReporte_De_Movimientos_Bancarios_Pendientes_De_Conciliar);
		btnReporteConciliado.addActionListener(opReporte_De_Movimientos_Concialiados);
		btnReporteComparacion_Entre_conciliaciones.addActionListener(opReporte_De_Diferencias_Entre_Conciliaciones);
		btnEliminarBanco.addActionListener(opEliminar_Ventana_emergente);
		cmbCuentasBancarias.addActionListener(opBuscar_datoscuenta_bancaria);
		
	    tabla_mov_contabilidad.getTableHeader().setReorderingAllowed(false) ;
	    tabla_mov_bancarios.getTableHeader().setReorderingAllowed(false) ;
	    tablaconciliados.getTableHeader().setReorderingAllowed(false) ;
		
	}
	
	class ButtonRenderer extends JButton implements TableCellRenderer {
		public ButtonRenderer() {
			setOpaque(true);
		}
		public Component getTableCellRendererComponent(JTable table, Object value,
		boolean isSelected, boolean hasFocus, int row, int column) {
			setText((value == null) ? "" :"Desconciliar");
			return this;
		}
	}
	
	class ButtonEditor extends DefaultCellEditor {
		private String label;
		public ButtonEditor(JCheckBox checkBox) {
			super(checkBox);
		}
		public Component getTableCellEditorComponent(JTable table, Object value,
		boolean isSelected, int row, int column) {
			label = (value == null) ? "" : "Desconciliar";
			btntabladesconciliar.setText(label);
			return btntabladesconciliar;
		}
		public Object getCellEditorValue() {
			return new String(label);
		}
	}
	
	public void cargar_datos_cuenta_bancaria() {
		Obj_Conciliacion_de_Movimientos_Bancarios_Contra_Contabilidad Datos_Cuenta_Bancaria =new Obj_Conciliacion_de_Movimientos_Bancarios_Contra_Contabilidad().buscar_datos_cuenta_Bancaria(cmbCuentasBancarias.getSelectedItem().toString());
		txtBanco.setText(Datos_Cuenta_Bancaria.getBanco().toString());
		txtCuentaContable.setText(Datos_Cuenta_Bancaria.getCuenta_Contable().toString());
	};
	
	public Date cargar_fecha_Sugerida(Integer dias){
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
		
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////// TODO  INICIA TABLA DE MOVIMIENTOS BANCARIOS LIMPIAR/ LLENAR/ DIMENCIONES DE COLUMNAS
//DIMENCIONES
	public void tabla_Mov_Bancarios(){
		llenado_tabla_mov_bancarios_inicial(2);

	
	tabla_mov_bancarios.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","centro","Arial","normal",12));
	tabla_mov_bancarios.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
	tabla_mov_bancarios.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
	tabla_mov_bancarios.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12));
	tabla_mov_bancarios.getColumnModel().getColumn(4).setCellRenderer(new tablaRenderer("fecha","izquierda","Arial","normal",12));
	tabla_mov_bancarios.getColumnModel().getColumn(5).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
	tabla_mov_bancarios.getColumnModel().getColumn(6).setCellRenderer(new tablaRenderer("CHB","derecha","Arial","normal",12));
	
	tabla_mov_bancarios.getColumnModel().getColumn(0).setMinWidth(70);
	tabla_mov_bancarios.getColumnModel().getColumn(0).setMaxWidth(800);
	tabla_mov_bancarios.getColumnModel().getColumn(1).setMinWidth(85);
	tabla_mov_bancarios.getColumnModel().getColumn(1).setMaxWidth(130);
	tabla_mov_bancarios.getColumnModel().getColumn(2).setMinWidth(30);
	tabla_mov_bancarios.getColumnModel().getColumn(2).setMaxWidth(30);
	tabla_mov_bancarios.getColumnModel().getColumn(3).setMinWidth(85);
	tabla_mov_bancarios.getColumnModel().getColumn(3).setMaxWidth(130);
	tabla_mov_bancarios.getColumnModel().getColumn(4).setMinWidth(80);
	tabla_mov_bancarios.getColumnModel().getColumn(4).setMaxWidth(130);
	
	tabla_mov_bancarios.getColumnModel().getColumn(5).setMinWidth(50);
	tabla_mov_bancarios.getColumnModel().getColumn(5).setMaxWidth(120);
	tabla_mov_bancarios.getColumnModel().getColumn(6).setMinWidth(30);		
	tabla_mov_bancarios.getColumnModel().getColumn(6).setMaxWidth(180);
	
	};
	
	//BORRADO Y LLENADO INICIAL
	public void llenado_tabla_mov_bancarios_inicial(Integer consulta){
	while(tabla_mov_bancarios.getRowCount()>0){	modelobancarios.removeRow(0);	}
	Object[][] getTabla=null;
	if(consulta==1){
	 getTabla = Consulta_para_llenar_Mov_Bancarios_inicial();
	}else{
	 getTabla = Consulta_para_llenar_Mov_Bancarios_desde_SCOI();	
	}
	
	Object[] fila = new Object[7];
	for(int i=0; i<getTabla.length; i++){
	fila[0] = getTabla[i][0];
	fila[1] = getTabla[i][1]+"";
	fila[2] = getTabla[i][2]+"";
	fila[3] = getTabla[i][3]+"";
	fila[4] = getTabla[i][4]+"";
	fila[5] = getTabla[i][5]+"";
	fila[6] = getTabla[i][6]+"";
	modelobancarios.addRow(fila); }
	}
	
	////CONSULTA INICIAL DESDE IZAGAR
	public Object[][] Consulta_para_llenar_Mov_Bancarios_inicial(){
	String fecha  = new SimpleDateFormat("dd/MM/yyyy").format(cfecha.getDate())+" 23:59:00";
	
	String consulta = "declare @FI smalldatetime,@FF smalldatetime,@Cuenta varchar(20)"
			        + "set @FI='Ene  8 2010 12:00:00:000AM' set @FF='"+fecha+"' set @Cuenta='"+cmbCuentasBancarias.getSelectedItem().toString()+"'"
			        + "  SELECT    concepto, convert(varchar(20),fecha,103)+' '+convert(varchar(20),fecha,108) as fecha, cargo_abono AS tipo_movimiento,convert(numeric(19,2),Importe) as Importe,referencia,Rtrim(convert(varchar(3),cod_estab))+convert(varchar(20),id) as id ,'false' as conciliar,id "
			        + "           FROM movimientos_bancos"
			        + "  WHERE        (id_mpolizas IS NULL) AND (fecha BETWEEN @FI AND @FF) AND (cuenta_bancaria = @Cuenta) order by importe desc";
	//String consulta="select 'false','c003','E','072015','08/07/2015 09:08:00','A,','25296.00','117321','PAGO DE VACACIONES A EMPLEADO: BEJARANO RIOS ROBERTO CARLOS','2','783135332'";
	Statement s;
	ResultSet rs2;
	try {
	s = new Connexion().conexion_IZAGAR().createStatement();
	rs2 = s.executeQuery(consulta);
	Matriz_Mov_Bancarios = new Object[getFilasIZAGAR(consulta)][7];
	int i=0;
	while(rs2.next()){
	Matriz_Mov_Bancarios[i][0] = "   "+rs2.getString(1).trim();
	Matriz_Mov_Bancarios[i][1] = "   "+rs2.getString(2).trim();
	Matriz_Mov_Bancarios[i][2] = "   "+rs2.getString(3).trim();
	Matriz_Mov_Bancarios[i][3] = "   "+rs2.getString(4).trim();
	Matriz_Mov_Bancarios[i][4] = "   "+rs2.getString(5).trim();
	Matriz_Mov_Bancarios[i][5] = "   "+rs2.getString(6).trim();
	Matriz_Mov_Bancarios[i][6] = Boolean.valueOf(rs2.getString(7).trim());
	i++;
	}
	} catch (SQLException e1) {
	e1.printStackTrace();
	JOptionPane.showMessageDialog(null,"Error en La consulta"+consulta+"\n SQLException"+e1.getMessage(),"Avise Al Administrador del Sistema",JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
	}
	return Matriz_Mov_Bancarios; 
	}
	
	///////GUARDADO INICIAL DESDE BMS
	private Object[][] tabla_guardar_movimientos_bancarios_iniciales(){
		Object[][] matriz_bancarios = new Object[tabla_mov_bancarios.getRowCount()][7];
		for(int i=0; i<tabla_mov_bancarios.getRowCount(); i++){
			matriz_bancarios[i][0] = modelobancarios.getValueAt(i,0).toString().trim();
			matriz_bancarios[i][1] = modelobancarios.getValueAt(i,1).toString().trim();
			matriz_bancarios[i][2] = modelobancarios.getValueAt(i,2).toString().trim();
			matriz_bancarios[i][3] = modelobancarios.getValueAt(i,3).toString().trim();
			matriz_bancarios[i][4] = modelobancarios.getValueAt(i,4).toString().trim();
			matriz_bancarios[i][5] = modelobancarios.getValueAt(i,5).toString().trim();
			matriz_bancarios[i][6] = cmbCuentasBancarias.getSelectedItem().toString();
		}
		return matriz_bancarios;
	}
	
	////CONSULTA DESDE SCOI
	public Object[][] Consulta_para_llenar_Mov_Bancarios_desde_SCOI(){
	
	String consulta =" SELECT    concepto, convert(varchar(20),fecha,103)+' '+convert(varchar(20),fecha,108) as fecha, movimiento, convert(numeric(19,2),Importe) as Importe,referencia, id,'false' as conciliar "
	                + " FROM IZAGAR_movimientos_bancarios where status_conciliado='PE' and  cuenta_bancaria='"+cmbCuentasBancarias.getSelectedItem().toString()+"' order by importe asc";
	Statement s;
	ResultSet rs2;
	try {
	s = new Connexion().conexion().createStatement();
	rs2 = s.executeQuery(consulta);
	Matriz_Mov_Bancarios = new Object[getFilasSCOI(consulta)][7];
	int i=0;
	while(rs2.next()){
	Matriz_Mov_Bancarios[i][0] = "   "+rs2.getString(1).trim();
	Matriz_Mov_Bancarios[i][1] = "   "+rs2.getString(2).trim();
	Matriz_Mov_Bancarios[i][2] = "   "+rs2.getString(3).trim();
	Matriz_Mov_Bancarios[i][3] = "   "+rs2.getString(4).trim();
	Matriz_Mov_Bancarios[i][4] = "   "+rs2.getString(5).trim();
	Matriz_Mov_Bancarios[i][5] = "   "+rs2.getString(6).trim();
	Matriz_Mov_Bancarios[i][6] = Boolean.valueOf(rs2.getString(7).trim());
	i++;
	}
	} catch (SQLException e1) {
	e1.printStackTrace();
	JOptionPane.showMessageDialog(null,"Error en La consulta"+consulta+"\n SQLException"+e1.getMessage(),"Avise Al Administrador del Sistema",JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
	}
	return Matriz_Mov_Bancarios; 
	}

///////////////////////////////////////////////////////////////////FIN TABLA MOVIMIENTOS BANCARIOS////////////////////////////////////////////////////////////////////////////////////////////////////	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////// TODO  INICIA TABLA DE MOVIMIENTOS CONTABILIDAD LIMPIAR/ LLENAR/ DIMENCIONES DE COLUMNAS
	//DIMENCIONES
	public void tabla_Mov_Contabilidad(){
		llenado_tabla_mov_contabilidad(2);
	    
	    		tabla_mov_contabilidad.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("CHB","centro","Arial","normal",12));
			    tabla_mov_contabilidad.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
			    tabla_mov_contabilidad.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
			    tabla_mov_contabilidad.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
			    tabla_mov_contabilidad.getColumnModel().getColumn(4).setCellRenderer(new tablaRenderer("fecha","izquierda","Arial","normal",12));
			    
			    tabla_mov_contabilidad.getColumnModel().getColumn(5).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
			    tabla_mov_contabilidad.getColumnModel().getColumn(6).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12));
			    tabla_mov_contabilidad.getColumnModel().getColumn(7).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
			    tabla_mov_contabilidad.getColumnModel().getColumn(8).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
			    tabla_mov_contabilidad.getColumnModel().getColumn(9).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
				
				tabla_mov_contabilidad.getColumnModel().getColumn(0).setMinWidth(0);
			    tabla_mov_contabilidad.getColumnModel().getColumn(0).setMaxWidth(60);
				tabla_mov_contabilidad.getColumnModel().getColumn(1).setMinWidth(0);
				tabla_mov_contabilidad.getColumnModel().getColumn(1).setMaxWidth(45);
				tabla_mov_contabilidad.getColumnModel().getColumn(2).setMinWidth(0);
				tabla_mov_contabilidad.getColumnModel().getColumn(2).setMaxWidth(30);
				tabla_mov_contabilidad.getColumnModel().getColumn(3).setMinWidth(0);
				tabla_mov_contabilidad.getColumnModel().getColumn(3).setMaxWidth(55);
				tabla_mov_contabilidad.getColumnModel().getColumn(4).setMinWidth(0);
				tabla_mov_contabilidad.getColumnModel().getColumn(4).setMaxWidth(130);
				
				tabla_mov_contabilidad.getColumnModel().getColumn(5).setMinWidth(0);
				tabla_mov_contabilidad.getColumnModel().getColumn(5).setMaxWidth(30);
				tabla_mov_contabilidad.getColumnModel().getColumn(6).setMinWidth(0);		
				tabla_mov_contabilidad.getColumnModel().getColumn(6).setMaxWidth(130);
				tabla_mov_contabilidad.getColumnModel().getColumn(7).setMinWidth(0);
				tabla_mov_contabilidad.getColumnModel().getColumn(7).setMaxWidth(130);
				tabla_mov_contabilidad.getColumnModel().getColumn(8).setMinWidth(0);
				tabla_mov_contabilidad.getColumnModel().getColumn(8).setMaxWidth(850);
				tabla_mov_contabilidad.getColumnModel().getColumn(9).setMinWidth(0);
				tabla_mov_contabilidad.getColumnModel().getColumn(9).setMaxWidth(120);

	};
	//BORRADO Y LLENADO
	public void llenado_tabla_mov_contabilidad(Integer consulta){
	  while(tabla_mov_contabilidad.getRowCount()>0){	modelocontabilidad.removeRow(0);	}
	  Object[][] getTablaContabilidad = null;
	  if(consulta==1){
		  getTablaContabilidad = Consulta_para_llenar_Mov_Contabilidad();
		 }else{
			 getTablaContabilidad = Consulta_para_llenar_Mov_Contabilidad_SCOI();
		 }
			Object[] fila = new Object[10];
		     for(int i=0; i<getTablaContabilidad.length; i++){
		             fila[0] = getTablaContabilidad[i][0];
		             fila[1] = getTablaContabilidad[i][1]+"";
		             fila[2] = getTablaContabilidad[i][2]+"";
		             fila[3] = getTablaContabilidad[i][3]+"";
		             fila[4] = getTablaContabilidad[i][4]+"";
		             fila[5] = getTablaContabilidad[i][5]+"";
		             fila[6] = getTablaContabilidad[i][6]+"";
		             fila[7] = getTablaContabilidad[i][7]+"";
		             fila[8] = getTablaContabilidad[i][8]+"";
		             fila[9] = getTablaContabilidad[i][9]+"";
		             modelocontabilidad.addRow(fila); }
	}
	
	////CONSULTA INICIAL CONTABILIDAD IZAGAR
	public Object[][] Consulta_para_llenar_Mov_Contabilidad(){
		String fecha  = new SimpleDateFormat("dd/MM/yyyy").format(cfecha.getDate())+" 23:59:00";
		String consulta = "DECLARE	   @FI smalldatetime,@FF smalldatetime,@Cuenta varchar(1024) SET @FI='Ene  8 2010 12:00:00:000AM' SET @FF='"+fecha+"' SET @Cuenta='"+txtCuentaContable.getText().toString()+"'"
				      + "  SELECT       'false' as conciliar, mp.folio AS poliza, mp.tipo_poliza, mp.mes_a�o, convert(varchar(20),mp.fecha_poliza,103)+' '+convert(varchar(20),mp.fecha_poliza,108) as fecha_poliza, mp.cargo_abono AS tipo_movimiento, convert(numeric(19,2), mp.importe) as importe,ISNULL(pp.folio_documento_pago, mp.referencia) AS referencia, mp.concepto"
				      + " ,rtrim(convert(varchar(15),mp.cod_estab))+convert(varchar(15),mp.id) as cod_estab"
				      + "  , mp.id, CASE WHEN isnull(pp.tipo_cambio, 0) = 0 THEN 1 ELSE pp.tipo_cambio END AS tipo_cambio "
				      + "    FROM    mpolizas_contables AS mp WITH (nolock) LEFT OUTER JOIN "
				      +"   pagos_proveedores AS pp WITH (nolock) ON pp.poliza_contable = mp.folio AND pp.tipo_poliza_contable = mp.tipo_poliza AND "
				      +"                 pp.mesa�o_poliza_contable = mp.mes_a�o"
				      + " WHERE        (mp.fecha_poliza BETWEEN @FI AND @FF) AND (mp.status = 'V') "
				      + "        AND (RTRIM(mp.cuenta_contable) + RTRIM(mp.subcuenta_contable) + RTRIM(mp.subsubcuenta_contable) = @Cuenta)"
				      + "        AND (CONVERT(varchar(15), mp.id) NOT IN (SELECT  id_mpolizas  FROM    movimientos_bancos "
				      + "                                                 WHERE (id_mpolizas IS NOT NULL))) AND (mp.id_movimientos_bancarios IS NULL) AND (mp.fecha_conciliacion_bancos IS NULL) order by importe asc ";
   		Statement s;
		ResultSet rs2;
		try {
			s = new Connexion().conexion_IZAGAR().createStatement();
			rs2 = s.executeQuery(consulta);
			Matriz_Mov_Contabilidad = new Object[getFilasIZAGAR(consulta)][10];
			int i=0;
			while(rs2.next()){
				Matriz_Mov_Contabilidad[i][0] = Boolean.valueOf(rs2.getString(1).trim());
				Matriz_Mov_Contabilidad[i][1] = "   "+rs2.getString(2).trim();
				Matriz_Mov_Contabilidad[i][2] = "   "+rs2.getString(3).trim();
				Matriz_Mov_Contabilidad[i][3] = "   "+rs2.getString(4).trim();
				Matriz_Mov_Contabilidad[i][4] = "   "+rs2.getString(5).trim();
				Matriz_Mov_Contabilidad[i][5] = "   "+rs2.getString(6).trim();
				Matriz_Mov_Contabilidad[i][6] = "   "+rs2.getString(7).trim();
				Matriz_Mov_Contabilidad[i][7] = "   "+rs2.getString(8).trim();
				Matriz_Mov_Contabilidad[i][8] = "   "+rs2.getString(9).trim();
				Matriz_Mov_Contabilidad[i][9] = "   "+rs2.getString(10).trim();
								i++;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null,"Error en La consulta"+consulta+"\n SQLException"+e1.getMessage(),"Avise Al Administrador del Sistema",JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
		}
	    return Matriz_Mov_Contabilidad; 
	}
	
///////GUARDADO CONTABILIDAD INICIAL DESDE BMS
    private Object[][] tabla_guardar_movimientos_contabilidad_iniciales(){
	  Object[][] matriz_contabilidad = new Object[tabla_mov_contabilidad.getRowCount()][10];
		  for(int i=0; i<tabla_mov_contabilidad.getRowCount(); i++){
			matriz_contabilidad[i][0] = modelocontabilidad.getValueAt(i,1).toString().trim();
			matriz_contabilidad[i][1] = modelocontabilidad.getValueAt(i,2).toString().trim();
			matriz_contabilidad[i][2] = modelocontabilidad.getValueAt(i,3).toString().trim();
			matriz_contabilidad[i][3] = modelocontabilidad.getValueAt(i,4).toString().trim();
			matriz_contabilidad[i][4] = modelocontabilidad.getValueAt(i,5).toString().trim();
			matriz_contabilidad[i][5] = modelocontabilidad.getValueAt(i,6).toString().trim();
			matriz_contabilidad[i][6] = modelocontabilidad.getValueAt(i,7).toString().trim();
			matriz_contabilidad[i][7] = modelocontabilidad.getValueAt(i,8).toString().trim();
			matriz_contabilidad[i][8] = modelocontabilidad.getValueAt(i,9).toString().trim();
			matriz_contabilidad[i][9] =txtCuentaContable.getText().toString();
		}
		return matriz_contabilidad;
	  }

	//CONSULTA SCOI
	public Object[][] Consulta_para_llenar_Mov_Contabilidad_SCOI(){
		String fecha  = new SimpleDateFormat("dd/MM/yyyy").format(cfecha.getDate())+" 23:59:00";
		String consulta = "SELECT  'false' as conciliar ,[poliza],[tipo],[MesA�o],[fecha],[mov] as tipo_movimiento,convert(numeric(19,2),[importe])as importe,[referencia],[concepto],[cod_establecimiento] "
				+ "  FROM  IZAGAR_movimientos_polizas where status_conciliado='PE' and cuenta_contable='"+txtCuentaContable.getText().toString()+"' and fecha<'"+fecha+"' order by importe asc";
   		Statement s;
		ResultSet rs2;
		try {
			s = new Connexion().conexion().createStatement();
			rs2 = s.executeQuery(consulta);
			Matriz_Mov_Contabilidad = new Object[getFilasSCOI(consulta)][10];
			int i=0;
			while(rs2.next()){
				Matriz_Mov_Contabilidad[i][0] = Boolean.valueOf(rs2.getString(1).trim());
				Matriz_Mov_Contabilidad[i][1] = "   "+rs2.getString(2).trim();
				Matriz_Mov_Contabilidad[i][2] = "   "+rs2.getString(3).trim();
				Matriz_Mov_Contabilidad[i][3] = "   "+rs2.getString(4).trim();
				Matriz_Mov_Contabilidad[i][4] = "   "+rs2.getString(5).trim();
				Matriz_Mov_Contabilidad[i][5] = "   "+rs2.getString(6).trim();
				Matriz_Mov_Contabilidad[i][6] = "   "+rs2.getString(7).trim();
				Matriz_Mov_Contabilidad[i][7] = "   "+rs2.getString(8).trim();
				Matriz_Mov_Contabilidad[i][8] = "   "+rs2.getString(9).trim();
				Matriz_Mov_Contabilidad[i][9] = "   "+rs2.getString(10).trim();
								i++;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null,"Error en La consulta"+consulta+"\n SQLException"+e1.getMessage(),"Avise Al Administrador del Sistema",JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
		}
	    return Matriz_Mov_Contabilidad; 
	}
	
///////////////////////////////////////////////////////////////////FIN TABLA MOVIMIENTOS CONTABILIDAD/////////////////////////////////////////////////////////////////////////////////////////////////	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	 public int getFilasIZAGAR(String consulta){
			int filas=0;
			Statement stmt = null;
			try {stmt = new Connexion().conexion_IZAGAR().createStatement();
				ResultSet rs2 = stmt.executeQuery(consulta);
				while(rs2.next()){filas++;}
			} catch (SQLException e1) {	e1.printStackTrace();}
			return filas;
		}
	 
	 public int getFilasSCOI(String consulta){
			int filas=0;
			Statement stmt = null;
			try {stmt = new Connexion().conexion().createStatement();
				ResultSet rs2 = stmt.executeQuery(consulta);
				while(rs2.next()){filas++;}
			} catch (SQLException e1) {	e1.printStackTrace();}
			return filas;
		}
	 
	
	 private Object[][] tabla_guardar_conciliados(){
		Object[][] matriz_conciliados = new Object[tablaconciliados.getRowCount()][7];
		for(int i=0; i<tablaconciliados.getRowCount(); i++){
			matriz_conciliados[i][0] = modeloconciliados.getValueAt(i,1).toString().trim();//fecha_conciliado
			matriz_conciliados[i][1] = modeloconciliados.getValueAt(i,5).toString().trim();//id mov bancario
			matriz_conciliados[i][2] = modeloconciliados.getValueAt(i,10).toString().trim();//poliza
			matriz_conciliados[i][3] = modeloconciliados.getValueAt(i,11).toString().trim();//tipo
			matriz_conciliados[i][4] = modeloconciliados.getValueAt(i,12).toString().trim();//poliza mes a�o
			matriz_conciliados[i][5] = modeloconciliados.getValueAt(i,8).toString().trim();//importe poliza
			matriz_conciliados[i][6] = modeloconciliados.getValueAt(i,16).toString().trim();//id mov contable
		}
		return matriz_conciliados;
	 }
	 
	 
	 
	
 ///////////////////TODO LISTENERS
    ActionListener opGuardarConciliacion = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			 Obj_Conciliacion_de_Movimientos_Bancarios_Contra_Contabilidad guardar_conciliados = new Obj_Conciliacion_de_Movimientos_Bancarios_Contra_Contabilidad();
			 
			if(tablaconciliados.isEditing()){
				tablaconciliados.getCellEditor().stopCellEditing();
			 }
            if(guardar_conciliados.guardar_conciliacion(tabla_guardar_conciliados())){
				while(tablaconciliados.getRowCount()>0){
					  modeloconciliados.removeRow(0); }
                          	  
          }
		}
	};

   ActionListener opBuscar_datoscuenta_bancaria = new ActionListener(){
		   public void actionPerformed(ActionEvent arg0) {
			  cargar_datos_cuenta_bancaria();
		   }
	};
	
   ActionListener opBorrar_ventana_emergente = new ActionListener(){
		   public void actionPerformed(ActionEvent arg0) {
			  new Cat_Emergente_Correccion_de_Movimientos_Contables().setVisible(true);;
		   }
	};
		
   ActionListener opEliminar_Ventana_emergente = new ActionListener(){
		   public void actionPerformed(ActionEvent arg0) {
			  new Cat_Emergente_Eliminar_de_Movimientos_Bancarios().setVisible(true);;
		   }
	};
		
	
   ActionListener opReporte_De_Movimientos_Contables_Pendientes_De_Conciliar = new ActionListener(){
		   public void actionPerformed(ActionEvent arg0) {
		  		String fecha  = new SimpleDateFormat("dd/MM/yyyy").format(cfecha.getDate())+" 23:59:00";    
		  		
				String basedatos="2.26";
				String vista_previa_reporte="no";
				int vista_previa_de_ventana=0;
				String reporte = "Obj_Reporte_De_Movimientos_Contables_Pendientes_De_Conciliar.jrxml";
			    String comando = "exec sp_Reporte_De_Movimientos_Contables_Pendientes_De_Conciliar '"+txtCuentaContable.getText().toString()+"','"+txtBanco.getText().toString()+"','"+cmbCuentasBancarias.getSelectedItem().toString()+"','"+fecha+"'";
			 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
		   }
	};
	
   ActionListener opReporte_De_Movimientos_Bancarios_Pendientes_De_Conciliar = new ActionListener(){
		   public void actionPerformed(ActionEvent arg0) {
		  		String fecha  = new SimpleDateFormat("dd/MM/yyyy").format(cfecha.getDate())+" 23:59:00";    
		  		
				String basedatos="2.26";
				String vista_previa_reporte="no";
				int vista_previa_de_ventana=0;
				String reporte = "Obj_Reporte_De_Movimientos_Bancarios_Pendientes_De_Conciliar.jrxml";
			    String comando = "exec sp_Reporte_De_Movimientos_Bancarios_Pendientes_De_Conciliar '"+txtBanco.getText().toString()+"','"+cmbCuentasBancarias.getSelectedItem().toString()+"','"+fecha+"'";
			
			    System.out.println(comando);
			    
			    new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
		   }
	};
	
	
	ActionListener opReporte_De_Diferencias_Entre_Conciliaciones = new ActionListener(){
		   public void actionPerformed(ActionEvent arg0) {
			   btnReporteComparacion_Entre_conciliaciones.setEnabled(false);
			   String fechaNull = cfecha.getDate()+"";
			    String cuenta_contable=txtCuentaContable.getText().toString();
			   if(fechaNull.equals("null")){
					JOptionPane.showMessageDialog(null,"Necesita Selecionar una Fecha o la Fecha tecleada es Incorrecta","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
					return;
				   }else{
			          Obj_Conciliacion_de_Movimientos_Bancarios_Contra_Contabilidad guardar_movimientos = new Obj_Conciliacion_de_Movimientos_Bancarios_Contra_Contabilidad();
 
					if(guardar_movimientos.guardar_movimientos_contabilidad_reporte_comparacion(Consulta_para_llenar_Mov_Contabilidad(),cuenta_contable)){
				  		String fecha  = new SimpleDateFormat("dd/MM/yyyy").format(cfecha.getDate())+" 23:59:00";    
				  		
						String basedatos="2.26";
						String vista_previa_reporte="no";
						int vista_previa_de_ventana=0;
						String reporte = "Obj_Reporte_De_Diferencias_De_Movimientos_Contables_Pendientes_De_Conciliar.jrxml";
					    String comando = "exec sp_Reporte_De_Diferencias_De_Movimientos_Contables_Pendientes_De_Conciliar '"+txtCuentaContable.getText().toString()+"','"+txtBanco.getText().toString()+"','"+cmbCuentasBancarias.getSelectedItem().toString()+"','"+fecha+"'";
					    new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
						   }else{
			                   JOptionPane.showMessageDialog(null, "Ocurri� un error al intentar guardar la tabla_guardar_movimientos_contabilidad_iniciales","Avise Al Adiministrador",JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			                   return;
		                    }
						 btnReporteComparacion_Entre_conciliaciones.setEnabled(true);
		   }
		   }
	};
	
	
	
	ActionListener opReporte_De_Movimientos_Concialiados = new ActionListener(){
		   public void actionPerformed(ActionEvent arg0) {
				String basedatos="2.26";
				String vista_previa_reporte="no";
				int vista_previa_de_ventana=0;
				String comando="";
				String reporte = "";
				String fecha  = new SimpleDateFormat("dd/MM/yyyy").format(cfecha_conciliado.getDate());
				reporte = "Obj_Reporte_De_Movimientos_Conciliados.jrxml";
			    comando = "exec sp_Reporte_De_Movimientos_Conciliados '"+fecha+"','"+txtCuentaContable.getText().toString()+"'";
			 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
		   }
	};

	
   ActionListener opActualizar = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			   String fechaNull = cfecha.getDate()+"";
			   if(fechaNull.equals("null")){
					JOptionPane.showMessageDialog(null,"Necesita Selecionar una Fecha o la Fecha tecleada es Incorrecta","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
					return;
				   }else{
					   btnConciliaAutoImporte.setEnabled(true);
				       Obj_Conciliacion_de_Movimientos_Bancarios_Contra_Contabilidad guardar_movimientos = new Obj_Conciliacion_de_Movimientos_Bancarios_Contra_Contabilidad();
						
					   llenado_tabla_mov_bancarios_inicial(1);
					   if(tabla_mov_bancarios.isEditing()){
						   tabla_mov_bancarios.getCellEditor().stopCellEditing();
						}
						if(guardar_movimientos.guardar_movimientos_bancarios_iniciales(tabla_guardar_movimientos_bancarios_iniciales())){
											while(tabla_mov_bancarios.getRowCount()>0){
												  modelobancarios.removeRow(0); }
											tabla_Mov_Bancarios();
				                        }else{
						                   JOptionPane.showMessageDialog(null, "Ocurri� un error al intentar guardar la tabla_guardar_movimientos_bancarios_iniciales","Avise Al Adiministrador",JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
						                  return;
					                    }
						llenado_tabla_mov_contabilidad(1);
						if(tabla_mov_contabilidad.isEditing()){
						    tabla_mov_contabilidad.getCellEditor().stopCellEditing();
						}
						if(guardar_movimientos.guardar_movimientos_contabilidad_iniciales(tabla_guardar_movimientos_contabilidad_iniciales())){
								while(tabla_mov_contabilidad.getRowCount()>0){
									  modelocontabilidad.removeRow(0); }
								tabla_Mov_Contabilidad();
	                        }else{
			                   JOptionPane.showMessageDialog(null, "Ocurri� un error al intentar guardar la tabla_guardar_movimientos_contabilidad_iniciales","Avise Al Adiministrador",JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			                  return;
		                    }
					
						if(tablaconciliados.isEditing()){
							tablaconciliados.getCellEditor().stopCellEditing();
						};
						while(tablaconciliados.getRowCount()>0){
							  modeloconciliados.removeRow(0); }
				   }
		   }
	};
	
	ActionListener opConciliacion_por_importe = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
		 Object[] vector = new Object[17];
		 float importe_mov_bancarios=0;
		 float importe_mov_contabilidad=0;
		 String fecha  = new SimpleDateFormat("dd/MM/yyyy").format(cfecha_conciliado.getDate())+" 00:00:00";

		 for(int i =0; i<tabla_mov_bancarios.getRowCount(); i++){
			   importe_mov_bancarios=Float.valueOf(tabla_mov_bancarios.getValueAt(i, 3).toString().trim());
			      for(int i1 =0; i1<tabla_mov_contabilidad.getRowCount(); i1++){
			    	  importe_mov_contabilidad=Float.valueOf(tabla_mov_contabilidad.getValueAt(i1, 6).toString().trim());
			    	  if(importe_mov_bancarios==importe_mov_contabilidad&&importe_mov_bancarios!=0){
				 		  vector[0] =  "";
				 		  vector[1] =  fecha; 
				 		  vector[2] = tabla_mov_bancarios.getValueAt(i,0).toString().trim();
				 		  vector[3] = tabla_mov_bancarios.getValueAt(i,1).toString().trim();
				 		  vector[4] = tabla_mov_bancarios.getValueAt(i,2).toString().trim();
				 		  vector[5] = tabla_mov_bancarios.getValueAt(i,5).toString().trim();
				 		  vector[6] = tabla_mov_bancarios.getValueAt(i,3).toString().trim();
				 		  vector[7] = tabla_mov_bancarios.getValueAt(i,4).toString().trim();
				 		  vector[8] =  tabla_mov_contabilidad.getValueAt(i1,6).toString().trim();
					 	  vector[9] =  tabla_mov_contabilidad.getValueAt(i1,7).toString().trim();
					 	  vector[10] =  tabla_mov_contabilidad.getValueAt(i1,1).toString().trim();
					 	  vector[11] =  tabla_mov_contabilidad.getValueAt(i1,2).toString().trim();
					 	  vector[12] =  tabla_mov_contabilidad.getValueAt(i1,3).toString().trim();
					 	  vector[13] =  tabla_mov_contabilidad.getValueAt(i1,4).toString().trim();
					 	  vector[14] =  tabla_mov_contabilidad.getValueAt(i1,5).toString().trim();
					 	  vector[15] =  tabla_mov_contabilidad.getValueAt(i1,8).toString().trim();
					 	  vector[16] =  tabla_mov_contabilidad.getValueAt(i1,9).toString().trim();
					 	 modelobancarios.removeRow(i); 
					 	 modelocontabilidad.removeRow(i1);
					 	 modeloconciliados.addRow(vector);
					 	importe_mov_bancarios=0;
			    	  }
				   }	
			   }
		   }
	};
	
	//tabla mov bancarios	 "Concepto", "Fecha","Mov","Importe","Referencia","Estab","Conciliar"
	//tabla mov contabilidad "Conciliar", "Poliza","Tipo","Mes A�o","Fecha","Mov","Importe","Referencia","Concepto","Establecimiento"
	//tabla conciliados      "Desconciliar","Fecha Conciliado","Concepto Banco", "Fecha Banco","MovBanco","Cod Estab + ID Banco","Importe Banco","Referencia Banco","Importe Pol","Referencia Pol",10"Poliza","Tipo","Mes A�o Pol","Fecha Pol","Mov Pol","Concepto Pol","CodEstab + ID Poliza"}

	ActionListener opConciliacion_por_importe_y_Referencia = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			 Object[] vector = new Object[17];
			 float importe_mov_bancarios=0;
			 float importe_mov_contabilidad=0;
			 String Referencia_Bancario ="";
			 String Referencia_Contabilidad="";
			 String fecha  = new SimpleDateFormat("dd/MM/yyyy").format(cfecha_conciliado.getDate())+" 00:00:00";
			 
		 for(int i =0; i<tabla_mov_bancarios.getRowCount(); i++){
			   importe_mov_bancarios=Float.valueOf(tabla_mov_bancarios.getValueAt(i, 3).toString().trim());
		       Referencia_Bancario=tabla_mov_bancarios.getValueAt(i,4).toString().trim();
			      for(int i1 =0; i1<tabla_mov_contabilidad.getRowCount(); i1++){
			    	  importe_mov_contabilidad=Float.valueOf(tabla_mov_contabilidad.getValueAt(i1, 6).toString().trim());
			    	  Referencia_Contabilidad=tabla_mov_contabilidad.getValueAt(i1,7).toString().trim();
			    	  if(importe_mov_bancarios==importe_mov_contabilidad&&Referencia_Bancario.equals(Referencia_Contabilidad)){
				 		  vector[0] =  "";
				 		  vector[1] =  fecha; 
				 		  vector[2] = tabla_mov_bancarios.getValueAt(i,0).toString().trim();
				 		  vector[3] = tabla_mov_bancarios.getValueAt(i,1).toString().trim();
				 		  vector[4] = tabla_mov_bancarios.getValueAt(i,2).toString().trim();
				 		  vector[5] = tabla_mov_bancarios.getValueAt(i,5).toString().trim();
				 		  vector[6] = tabla_mov_bancarios.getValueAt(i,3).toString().trim();
				 		  vector[7] = tabla_mov_bancarios.getValueAt(i,4).toString().trim();
				 		  vector[8] =  tabla_mov_contabilidad.getValueAt(i1,6).toString().trim();
					 	  vector[9] =  tabla_mov_contabilidad.getValueAt(i1,7).toString().trim();
					 	  vector[10] =  tabla_mov_contabilidad.getValueAt(i1,1).toString().trim();
					 	  vector[11] =  tabla_mov_contabilidad.getValueAt(i1,2).toString().trim();
					 	  vector[12] =  tabla_mov_contabilidad.getValueAt(i1,3).toString().trim();
					 	  vector[13] =  tabla_mov_contabilidad.getValueAt(i1,4).toString().trim();
					 	  vector[14] =  tabla_mov_contabilidad.getValueAt(i1,5).toString().trim();
					 	  vector[15] =  tabla_mov_contabilidad.getValueAt(i1,8).toString().trim();
					 	  vector[16] =  tabla_mov_contabilidad.getValueAt(i1,9).toString().trim();
					 	 modelobancarios.removeRow(i); 
					 	 modelocontabilidad.removeRow(i1);
					 	 modeloconciliados.addRow(vector);
			    	  }
				   }	
			   }
//		    btnConciliaAutoImporteyReferencia.setEnabled(false);
		   }
	};
		 
	ActionListener opConciliar = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
		 Object[] vector = new Object[17];
		 int seleccion =0;
		 
		 for(int i =0; i<tabla_mov_bancarios.getRowCount(); i++){
			  if(tabla_mov_bancarios.getValueAt(i, 6).toString().trim().equals("true")){
				 seleccion=seleccion+1;
			  }
		   }
		 for(int i =0; i<tabla_mov_contabilidad.getRowCount(); i++){
			   if(tabla_mov_contabilidad.getValueAt(i, 0).toString().trim().equals("true")){
				   seleccion=seleccion+1;;		   
			   }		   
		   }
		 
		 if(seleccion==2){
			 
				if(tabla_mov_contabilidad.isEditing()){
				    tabla_mov_contabilidad.getCellEditor().stopCellEditing();
				}
				if(tabla_mov_bancarios.isEditing()){
				    tabla_mov_bancarios.getCellEditor().stopCellEditing();
				}
			 
				for(int i =0; i<tabla_mov_bancarios.getRowCount(); i++){
			 	   if(tabla_mov_bancarios.getValueAt(i, 6).toString().trim().equals("true")){
			 		  String fecha  = new SimpleDateFormat("dd/MM/yyyy").format(cfecha_conciliado.getDate())+" 00:00:00";
			 		  vector[0] =  "";
			 		  vector[1] =  fecha; 
			 		  vector[2] = tabla_mov_bancarios.getValueAt(i,0).toString().trim();
			 		  vector[3] = tabla_mov_bancarios.getValueAt(i,1).toString().trim();
			 		  vector[4] = tabla_mov_bancarios.getValueAt(i,2).toString().trim();
			 		  vector[5] = tabla_mov_bancarios.getValueAt(i,5).toString().trim();
			 		  vector[6] = tabla_mov_bancarios.getValueAt(i,3).toString().trim();
			 		  vector[7] = tabla_mov_bancarios.getValueAt(i,4).toString().trim();
			 		   modelobancarios.removeRow(i);
					}
			 	 }
				for(int i =0; i<tabla_mov_contabilidad.getRowCount(); i++){
				 	   if(tabla_mov_contabilidad.getValueAt(i, 0).toString().trim().equals("true")){
				 		 vector[8] =  tabla_mov_contabilidad.getValueAt(i,6).toString().trim();
				 		 vector[9] =  tabla_mov_contabilidad.getValueAt(i,7).toString().trim();
				 		 vector[10] =  tabla_mov_contabilidad.getValueAt(i,1).toString().trim();
				 		 vector[11] =  tabla_mov_contabilidad.getValueAt(i,2).toString().trim();
				 		 vector[12] =  tabla_mov_contabilidad.getValueAt(i,3).toString().trim();
				 		 vector[13] =  tabla_mov_contabilidad.getValueAt(i,4).toString().trim();
				 		 vector[14] =  tabla_mov_contabilidad.getValueAt(i,5).toString().trim();
				 		 vector[15] =  tabla_mov_contabilidad.getValueAt(i,8).toString().trim();
				 		 vector[16] =  tabla_mov_contabilidad.getValueAt(i,9).toString().trim();
				 		 modelocontabilidad.removeRow(i);
				   }
			    }
				modeloconciliados.addRow(vector);
	    }
		 else{
				JOptionPane.showMessageDialog(null, "Para Poder Conciliar Se Necesita Selecionar Un Movimiento Bancario y Un Movimiento de Contabilidad", "Mensaje", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
		 }
		}
	};
	
	
    ActionListener opDesconciliar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			int filaseleccionada = 0;
			Object[] vector_contabilidad = new Object[10];
			Object[] vector_bancos = new Object[7];
			filaseleccionada = tablaconciliados.getSelectedRow();
			
			vector_contabilidad[0] = "false";
			vector_contabilidad[1] =  tablaconciliados.getValueAt(filaseleccionada,10).toString().trim();
			vector_contabilidad[2] =  tablaconciliados.getValueAt(filaseleccionada,11).toString().trim();
			vector_contabilidad[3] =  tablaconciliados.getValueAt(filaseleccionada,12).toString().trim();
			vector_contabilidad[4] =  tablaconciliados.getValueAt(filaseleccionada,13).toString().trim();
			vector_contabilidad[5] =  tablaconciliados.getValueAt(filaseleccionada,14).toString().trim();
			vector_contabilidad[6] =  tablaconciliados.getValueAt(filaseleccionada,8).toString().trim();
			vector_contabilidad[7] =  tablaconciliados.getValueAt(filaseleccionada,9).toString().trim();
			vector_contabilidad[8] =  tablaconciliados.getValueAt(filaseleccionada,15).toString().trim();
			vector_contabilidad[9] =  tablaconciliados.getValueAt(filaseleccionada,16).toString().trim();
			modelocontabilidad.addRow(vector_contabilidad);
			
			vector_bancos[0] =  tablaconciliados.getValueAt(filaseleccionada,2).toString().trim();
			vector_bancos[1] =  tablaconciliados.getValueAt(filaseleccionada,3).toString().trim();
			vector_bancos[2] =  tablaconciliados.getValueAt(filaseleccionada,4).toString().trim();
			vector_bancos[3] =  tablaconciliados.getValueAt(filaseleccionada,6).toString().trim();
			vector_bancos[4] =  tablaconciliados.getValueAt(filaseleccionada,7).toString().trim();
			vector_bancos[5] =  tablaconciliados.getValueAt(filaseleccionada,5).toString().trim();
			vector_bancos[6] =  "false";
			modelobancarios.addRow(vector_bancos);
			
			if(tablaconciliados.isEditing()){
				tablaconciliados.getCellEditor().stopCellEditing();
			}
			modeloconciliados.removeRow(filaseleccionada);
		}
	};
	
    WindowListener op_cerrar = new WindowListener() {
		public void windowOpened(WindowEvent e) {}
		public void windowIconified(WindowEvent e) {}
		public void windowDeiconified(WindowEvent e) {}
		public void windowDeactivated(WindowEvent e) {}
		public void windowClosing(WindowEvent e) {
//			if(JOptionPane.showConfirmDialog(null, "�Desea guardar antes de cerrar?", "Aviso!", JOptionPane.YES_NO_OPTION) == 0){
//				new Obj_IZAGAR_Netos_Nominas().update_IZAGAR_netos_de_nomina_por_empleado_pre_conciliados();
//			}
		}
		public void windowClosed(WindowEvent e) {}
		public void windowActivated(WindowEvent e) {}
	};
	
	 ///////////////////TODO CATALOGO DE BORRADO DE MOVIMIENTOS DE POLIZA CORREGIDOS
	public class Cat_Emergente_Correccion_de_Movimientos_Contables extends JDialog{
		Container cont = getContentPane();
		JLayeredPane panel = new JLayeredPane();
		private JTextField txtid = new Componentes().text(new JTextField(), "ID del Movimiento Contable A Buscar", 25, "String");
		private JTextField txtpoliza = new Componentes().text(new JTextField(), "Poliza", 25, "String");
		private JTextField txtimporte = new Componentes().text(new JTextField(), "Importe", 25, "String");
		private JTextField txtreferencia = new Componentes().text(new JTextField(), "Referencia", 25, "String");
		private JTextField txtconcepto    = new Componentes().text(new JTextField(), "Concepto", 25, "String");
		private JTextField txtcuenta_contable   = new Componentes().text(new JTextField(), "Cuenta Contable", 25, "String");
		private JTextField txtestatus_conciliado    = new Componentes().text(new JTextField(), "Estatus Conciliado", 25, "String");
		private JTextField txtmov_desconciliado    = new Componentes().text(new JTextField(), "Estatus Desconciliado", 25, "String");
		private JTextField txtid_movimiento_bancario_conciliado    = new Componentes().text(new JTextField(), "ID Movimiento Bancario Conciliado", 25, "String");
		private JTextField txtreferencia_mov_bancario_conciliado    = new Componentes().text(new JTextField(), "Referencia del Movimiento Bancario Conciliado", 25, "String");
		
		private JButton btnBorrado_Movimiento = new JButton("",new ImageIcon("imagen/Delete.png"));
		private JButton btnDesconciliar = new JButton("",new ImageIcon("imagen/Delete.png"));
		
		 private ButtonGroup bgcorreccion = new ButtonGroup();
		 private JRadioButton rbmcancelar = new JRadioButton("",true);
		 private JRadioButton rb2desconciliar = new JRadioButton("",false);
		
		public Cat_Emergente_Correccion_de_Movimientos_Contables(){
			
			setSize(300,400);
			setResizable(false);
			setLocationRelativeTo(null);
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			setModal(true);
			setTitle("Borrado de Movimientos Contables Modificados");
			setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/listaRoja.png"));
			panel.setBorder(BorderFactory.createTitledBorder(""));
			btnBorrado_Movimiento.setText(	"<html> <FONT FACE="+"arial"+" SIZE=3 COLOR=BLACk>" +
					"		<CENTER><p>Borrar Movimiento Tecleado</p></CENTER></FONT>" +
					"</html>");	
			
			btnDesconciliar.setText(	"<html> <FONT FACE="+"arial"+" SIZE=3 COLOR=BLACk>" +
					"		<CENTER><p>Desconciliar</p></CENTER></FONT>" +
					"</html>");	
			this.bgcorreccion.add(rb2desconciliar);
			this.bgcorreccion.add(rbmcancelar);
			
			int x=10,y=15;
			
			panel.add(new JLabel("Desconciliar")).setBounds(x,y,100,20);	
			panel.add(rb2desconciliar).setBounds(x+55, y, 20,20);
			panel.add(new JLabel("Borrar")).setBounds(x+120,y,100,20);	
			panel.add(rbmcancelar).setBounds(x+160, y, 20,20);
			
			
			panel.add(new JLabel("ID:")).setBounds(x,y+=25,100,20);		
			panel.add(txtid).setBounds(x+70,y,210,20);
			
			panel.add(new JLabel("Poliza:")).setBounds(x,y+=25,100,20);		
			panel.add(txtpoliza).setBounds(x+70,y,210,20);
			
			panel.add(new JLabel("Importe:")).setBounds(x,y+=25,100,20);		
			panel.add(txtimporte).setBounds(x+70,y,210,20);
			
			panel.add(new JLabel("Referencia:")).setBounds(x,y+=25,100,20);		
			panel.add(txtreferencia).setBounds(x+70,y,210,20);
			
			panel.add(new JLabel("Concepto:")).setBounds(x,y+=25,100,20);		
			panel.add(txtconcepto).setBounds(x+70,y,210,20);
			
			panel.add(new JLabel("Cuenta:")).setBounds(x,y+=25,100,20);		
			panel.add(txtcuenta_contable).setBounds(x+70,y,210,20);
			
			panel.add(new JLabel("Estatus Conciliado:")).setBounds(x,y+=25,100,20);		
			panel.add(txtestatus_conciliado).setBounds(x+110,y,170,20);
			
			panel.add(new JLabel("Estatus Desconciliado:")).setBounds(x,y+=25,120,20);		
			panel.add(txtmov_desconciliado).setBounds(x+110,y,170,20);
			
			panel.add(new JLabel("ID Bancario Conciliado:")).setBounds(x,y+=25,120,20);		
			panel.add(txtid_movimiento_bancario_conciliado).setBounds(x+110,y,170,20);
			
			panel.add(new JLabel("Referecia Bancaria:")).setBounds(x,y+=25,120,20);		
			panel.add(txtreferencia_mov_bancario_conciliado).setBounds(x+110,y,170,20);
			
			panel.add(btnBorrado_Movimiento).setBounds(x+30,y+=40,220,20);
			panel.add(btnDesconciliar).setBounds(x+30,y+=30,220,20);
			
			cont.add(panel);
			txtpoliza.setEditable(false);
			txtimporte.setEditable(false);
			txtreferencia.setEditable(false);
			txtconcepto.setEditable(false);
			txtcuenta_contable.setEditable(false);
			txtestatus_conciliado.setEditable(false);
			txtmov_desconciliado.setEditable(false);
			txtid_movimiento_bancario_conciliado.setEditable(false);
			txtreferencia_mov_bancario_conciliado.setEditable(false);
			
			 limpiar();
			rbmcancelar.setSelected(true);
			txtid.requestFocus();
			
			txtid.addKeyListener(opbuscar_datos_del_id);
			txtreferencia.addKeyListener(opbuscar_datos_de_la_referencia);
			btnBorrado_Movimiento.addActionListener(opBorrar_movimiento_tecleado);
			btnDesconciliar.addActionListener(opdesconciliar_movimiento_contable_tecleado);
			
			rb2desconciliar.addActionListener(opseleccionarconcialiar_desconciliar);
			rbmcancelar.addActionListener(opseleccionarconcialiar_desconciliar);
			
			}
		
		          KeyListener opbuscar_datos_del_id = new KeyListener(){
					public void keyReleased(KeyEvent e1) {
					}
					public void keyTyped(KeyEvent e1) {
					}
					public void keyPressed(KeyEvent e1) {
						if(e1.getKeyCode()==KeyEvent.VK_ENTER){
							if(existe_mov_contable(1)){
								buscar_datos_del_mov(1);
								btnBorrado_Movimiento.setEnabled(true);
								btnDesconciliar.setEnabled(false);
								txtid.setEditable(false);
							}else{
					   			JOptionPane.showMessageDialog(null,"No Se Encontro El Movimiento Tecleado Por Alguna de Las Siguientes Razones\n-No Existe El ID Tecleado \n-Esta Conciliado El Movimiento","Aviso!", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
					   			limpiar();
							}
						   }    	   
					    }
				    };
				    
				    
			        KeyListener opbuscar_datos_de_la_referencia = new KeyListener(){
							public void keyReleased(KeyEvent e1) {
							}
							public void keyTyped(KeyEvent e1) {
							}
							public void keyPressed(KeyEvent e1) {
								
								if(e1.getKeyCode()==KeyEvent.VK_ENTER){
									if(existe_mov_contable(2)){
										buscar_datos_del_mov(2);
									}else{
							   			JOptionPane.showMessageDialog(null,"No Existe El la Referencia Tecleada ","Aviso!", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
							   			limpiar();
									}
								   }    	   
							    }
						    };
						    
				    
					public boolean existe_mov_contable(int desconciliar_borrar){
						String query ="";
						if(desconciliar_borrar==1){
						     query ="	IF(SELECT COUNT(POLIZA) FROM IZAGAR_movimientos_polizas  WHERE status_conciliado='PE' AND cod_establecimiento='"+txtid.getText().toString()+"')=1 "+
								      "  SELECT 'true' ELSE SELECT 'false' ";
						}
						if(desconciliar_borrar==2){
						     query ="	IF(SELECT COUNT(POLIZA) FROM IZAGAR_movimientos_polizas  WHERE referencia='"+txtreferencia.getText().toString().toUpperCase().trim()+"')=1 "+
									    "   SELECT 'true' ELSE SELECT 'false' ";
						}
						boolean existe = false;
						try { Connexion con = new Connexion();
							  Statement s = con.conexion().createStatement();
							  ResultSet rs = s.executeQuery(query);
							while(rs.next()){
							    	existe = rs.getBoolean(1);
							      }
						} catch (SQLException e1) {
							e1.printStackTrace();
							JOptionPane.showMessageDialog(null, "Error en la funcion existe_mov_contable \n SQLException: "+e1.getMessage(), "Avisa al Administrador del Sistema", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
						}
				      return existe;
					}
					
					public void buscar_datos_del_mov(int desconciliar_borrar){
						String query ="";
						if(desconciliar_borrar==1){ query ="SELECT cod_establecimiento,POLIZA ,IMPORTE ,REFERENCIA ,CONCEPTO,cuenta_contable,case WHEN status_conciliado='PE' then 'PENDIENTE' WHEN  status_conciliado='CO' then 'CONCILIADO' END AS status_conciliado ,mov_desconciliado,id_movimiento_bancario_conciliado"
		                                                           +" ,isnull((SELECT referencia from IZAGAR_movimientos_bancarios where id=IZAGAR_movimientos_polizas.id_movimiento_bancario_conciliado),'') as referencia_banco  "
								                                + "  FROM IZAGAR_movimientos_polizas  WHERE status_conciliado='PE' and mov_desconciliado='NO' AND cod_establecimiento='"+txtid.getText().toString()+"'";
						}
						if(desconciliar_borrar==2){query ="SELECT cod_establecimiento,POLIZA ,IMPORTE ,REFERENCIA ,CONCEPTO,cuenta_contable,case WHEN status_conciliado='PE' then 'PENDIENTE' WHEN  status_conciliado='CO' then 'CONCILIADO' END AS status_conciliado ,mov_desconciliado,id_movimiento_bancario_conciliado"
								                        +" ,isnull((SELECT referencia from IZAGAR_movimientos_bancarios where id=IZAGAR_movimientos_polizas.id_movimiento_bancario_conciliado),'') as referencia_banco  "
								                       + "  FROM IZAGAR_movimientos_polizas  WHERE REFERENCIA='"+txtreferencia.getText().toString().trim()+"'";
						}
						try { Connexion con = new Connexion();
							  Statement s = con.conexion().createStatement();
							  ResultSet rs = s.executeQuery(query);
							while(rs.next()){
							 	    txtid.setText(rs.getString(1).toString());
							    	txtpoliza.setText(rs.getString(2).toString());
							    	txtimporte.setText(rs.getString(3).toString());
							    	txtreferencia.setText(rs.getString(4).toString());
							    	txtconcepto.setText(rs.getString(5).toString());
							    	txtcuenta_contable.setText(rs.getString(6).toString());
							    	txtestatus_conciliado.setText(rs.getString(7).toString());
							    	txtmov_desconciliado.setText(rs.getString(8).toString());
							    	txtid_movimiento_bancario_conciliado.setText(rs.getString(9).toString());
							    	txtreferencia_mov_bancario_conciliado.setText(rs.getString(10).toString());
							      }
							if(txtmov_desconciliado.getText().toString().trim().equals("NO")&&desconciliar_borrar==2&&!txtid_movimiento_bancario_conciliado.getText().toString().trim().equals("")){
								btnDesconciliar.setEnabled(true);
								txtreferencia.setEditable(false);
							}else{
								btnDesconciliar.setEnabled(false);
							}
						   } catch (SQLException e1) {
							e1.printStackTrace();
							JOptionPane.showMessageDialog(null, "Error en la funcion buscar_datos_del_id \n SQLException: "+e1.getMessage(), "Avisa al Administrador del Sistema", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
						}
					}
					
			        public void limpiar(){
						    txtid.setText("");
							txtpoliza.setText("");
							txtimporte.setText("");
							txtreferencia.setText("");
							txtconcepto.setText("");
							txtcuenta_contable.setText("");
							txtestatus_conciliado.setText("");
							txtmov_desconciliado.setText("");
							txtid_movimiento_bancario_conciliado.setText("");
							txtreferencia_mov_bancario_conciliado.setText("");
							btnBorrado_Movimiento.setEnabled(false);
							btnDesconciliar.setEnabled(false);
			         };
				
			         ActionListener opseleccionarconcialiar_desconciliar = new ActionListener(){
			 			   public void actionPerformed(ActionEvent arg0) {
							   if(rbmcancelar.isSelected()){
								   txtid.setEditable(true);
								   txtreferencia.setEditable(false);
								   limpiar();
								txtid.requestFocus();
						   }
						   if(rb2desconciliar.isSelected()){
								   txtid.setEditable(false);
								   txtreferencia.setEditable(true);
								   limpiar();
								   txtreferencia.requestFocus();
						   }
					   };
					 };
					
				    ActionListener opBorrar_movimiento_tecleado = new ActionListener(){
					   public void actionPerformed(ActionEvent arg0) {
						   if(new ActualizarSQL().Borrar_movimiento_contabilidad(txtid.getText().toString())){
							JOptionPane.showMessageDialog(null, "El Registro Se Elimino Correctamente", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
							   
								while(tablaconciliados.getRowCount()>0){
									  modeloconciliados.removeRow(0); }
								
								while(tabla_mov_bancarios.getRowCount()>0){
									modelobancarios.removeRow(0); }
								
								while(tabla_mov_contabilidad.getRowCount()>0){
									modelocontabilidad.removeRow(0); }
								   dispose();
						   }else{
								JOptionPane.showMessageDialog(null, "Error en la funcion opBorrar_movimiento_tecleado \n al intentar borrar el ID:"+txtid.getText().toString(), "Avisa al Administrador del Sistema", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
						  }	 
					      }
				        };
				    
				    ActionListener opdesconciliar_movimiento_contable_tecleado = new ActionListener(){
					   public void actionPerformed(ActionEvent arg0) {
						   if(new ActualizarSQL().desconciliar_movimiento_contabilidad(txtid.getText().toString(),txtreferencia.getText().toString(),txtpoliza.getText().toString(),txtid_movimiento_bancario_conciliado.getText().toString())){
							JOptionPane.showMessageDialog(null, "El Registro Se Desconcilio Correctamente", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
							   
								while(tablaconciliados.getRowCount()>0){
									  modeloconciliados.removeRow(0); }
								
								while(tabla_mov_bancarios.getRowCount()>0){
									modelobancarios.removeRow(0); }
								
								while(tabla_mov_contabilidad.getRowCount()>0){
									modelocontabilidad.removeRow(0); }
								   dispose();
						   }else{
								JOptionPane.showMessageDialog(null, "Error en la funcion opdesconciliar_movimiento_contable \n al intentar borrar el ID:"+txtid.getText().toString(), "Avisa al Administrador del Sistema", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
						  }	 
					      }
				        };
				    };
				
				
				    ///////////////////TODO CATALOGO DE BORRADO DE MOVIMIENTOS DE POLIZA CORREGIDOS
					public class Cat_Emergente_Eliminar_de_Movimientos_Bancarios extends JDialog{
						Container cont = getContentPane();
						JLayeredPane panel = new JLayeredPane();
						private JTextField txtid                   = new Componentes().text(new JCTextField(), "ID del Movimiento Bancario", 25, "String");
						private JTextField txtConcepto             = new Componentes().text(new JCTextField(), "Concepto"                  , 25, "String");
						private JTextField txtFecha                = new Componentes().text(new JCTextField(), "Fecha"                     , 25, "String");
						private JTextField txtimporte              = new Componentes().text(new JCTextField(), "Importe"                   , 25, "String");
						private JTextField txtreferencia           = new Componentes().text(new JCTextField(), "Referencia"                , 25, "String");
						private JTextField txtcuenta_contable      = new Componentes().text(new JCTextField(), "Cuenta Contable"           , 25, "String");
						private JTextField txtestatus_conciliado   = new Componentes().text(new JCTextField(), "Estatus Conciliado"        , 25, "String");
						private JTextField txtMovimiento           = new Componentes().text(new JCTextField(), "Movimiento"                , 25, "String");
						
						private JButton btnBorrado_Movimiento = new JButton("",new ImageIcon("imagen/Delete.png"));
						
						public Cat_Emergente_Eliminar_de_Movimientos_Bancarios(){
							setSize(300,400);
							setResizable(false);
							setLocationRelativeTo(null);
							setDefaultCloseOperation(DISPOSE_ON_CLOSE);
							setModal(true);
							setTitle("Eliminar Movimientos Bancarios");
							setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/listaRoja.png"));
							panel.setBorder(BorderFactory.createTitledBorder(""));
							btnBorrado_Movimiento.setText(	"<html> <FONT FACE="+"arial"+" SIZE=3 COLOR=BLACk>" +
									"		<CENTER><p>Cancelar Movimiento Tecleado</p></CENTER></FONT>"+"</html>");	
							
							int x=10,y=15, width=100, height=20;
							panel.add(new JLabel("ID:")).setBounds                (x     ,y+=25  ,width   ,height);		
							panel.add(txtid).setBounds                            (x+80  ,y      ,width*2 ,height);
							panel.add(new JLabel("Concepto:")).setBounds          (x     ,y+=25  ,width   ,height);		
							panel.add(txtConcepto).setBounds                      (x+80  ,y      ,width*2 ,height);
							panel.add(new JLabel("Fecha:")).setBounds             (x     ,y+=25  ,width   ,height);		
							panel.add(txtFecha).setBounds                         (x+80  ,y      ,width*2 ,height);
							panel.add(new JLabel("Mov.:")).setBounds              (x     ,y+=25  ,width   ,height);		
							panel.add(txtMovimiento).setBounds                    (x+80  ,y      ,width*2 ,height);
							panel.add(new JLabel("Importe:")).setBounds           (x     ,y+=25  ,width   ,height);		
							panel.add(txtimporte).setBounds                       (x+80  ,y      ,width*2 ,height);
							panel.add(new JLabel("Referencia:")).setBounds        (x     ,y+=25  ,width   ,height);		
							panel.add(txtreferencia).setBounds                    (x+80  ,y      ,width*2 ,height);
							panel.add(new JLabel("Cuenta:")).setBounds            (x     ,y+=25  ,width   ,height);		
							panel.add(txtcuenta_contable).setBounds               (x+80  ,y      ,width*2 ,height);
							panel.add(new JLabel("Estatus Conciliado:")).setBounds(x     ,y+=25  ,width   ,height);		
							panel.add(txtestatus_conciliado).setBounds            (x+90  ,y      ,190     ,height);
							panel.add(btnBorrado_Movimiento).setBounds            (x+30  ,y+=40  ,width*2 ,height*2);
							
							cont.add(panel);
							txtConcepto.setEditable(false);
							txtimporte.setEditable(false);
							txtreferencia.setEditable(false);
							txtFecha.setEditable(false);
							txtcuenta_contable.setEditable(false);
							txtestatus_conciliado.setEditable(false);
							txtMovimiento.setEditable(false);
							
							txtid.requestFocus();
							txtid.addKeyListener(opbuscar_datos_del_id);
							btnBorrado_Movimiento.addActionListener(opBorrar_movimiento_tecleado);
							}
						
						          KeyListener opbuscar_datos_del_id = new KeyListener(){
									public void keyReleased(KeyEvent e1) {
									}
									public void keyTyped(KeyEvent e1) {
									}
									public void keyPressed(KeyEvent e1) {
										if(e1.getKeyCode()==KeyEvent.VK_ENTER){
											if(existe_mov_contable(1)){
												buscar_datos_del_mov(1);
												btnBorrado_Movimiento.setEnabled(true);
												txtid.setEditable(false);
											}else{
									   			JOptionPane.showMessageDialog(null,"No Se Encontro El Movimiento Tecleado Por Alguna de Las Siguientes Razones\n-No Existe El ID Tecleado \n-Esta Conciliado El Movimiento \n-Esta Cancelado El Movimiento","Aviso!", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
											}
										   }    	   
									    }
								    };

								    public boolean existe_mov_contable(int desconciliar_borrar){
										String query ="";
										if(desconciliar_borrar==1){
										     query ="	IF(SELECT COUNT(concepto) FROM IZAGAR_movimientos_bancarios  WHERE status_conciliado='PE' AND id='"+txtid.getText().toString()+"')=1 "+
												      "  SELECT 'true' ELSE SELECT 'false' ";
										}
										
										boolean existe = false;
										try { Connexion con = new Connexion();
											  Statement s = con.conexion().createStatement();
											  ResultSet rs = s.executeQuery(query);
											while(rs.next()){
											    	existe = rs.getBoolean(1);
											      }
										} catch (SQLException e1) {
											e1.printStackTrace();
											JOptionPane.showMessageDialog(null, "Error en la funcion existe_mov_contable \n SQLException: "+e1.getMessage(), "Avisa al Administrador del Sistema", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
										}
								      return existe;
									}
			
									public void buscar_datos_del_mov(int desconciliar_borrar){
										String query ="SELECT id,concepto,fecha,movimiento,importe,referencia,cuenta_bancaria,status_conciliado   FROM IZAGAR_movimientos_bancarios"
												     +"   WHERE status_conciliado='PE' AND id='"+txtid.getText().toString()+"'";
										try { Connexion con = new Connexion();
											  Statement s = con.conexion().createStatement();
											  ResultSet rs = s.executeQuery(query);
											while(rs.next()){
											 	    txtid.setText(rs.getString(1).toString());
											 		txtConcepto.setText(rs.getString(2).toString());
											    	txtFecha.setText(rs.getString(3).toString());
											    	txtMovimiento.setText(rs.getString(4).toString());
											    	txtimporte.setText(rs.getString(5).toString());
											    	txtreferencia.setText(rs.getString(6).toString());
											    	txtcuenta_contable.setText(rs.getString(7).toString());
											    	txtestatus_conciliado.setText(rs.getString(8).toString());
											      }
									    	return;
										   } catch (SQLException e1) {
											e1.printStackTrace();
											JOptionPane.showMessageDialog(null, "Error en la funcion buscar_datos_del_id \n SQLException: "+e1.getMessage(), "Avisa al Administrador del Sistema", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
										}
									}

									public void limpiar(){
										    txtid.setText("");
											txtFecha.setText("");
											txtimporte.setText("");
											txtreferencia.setText("");
											txtConcepto.setText("");
											txtcuenta_contable.setText("");
											txtestatus_conciliado.setText("");
											txtMovimiento.setText("");
											btnBorrado_Movimiento.setEnabled(false);
											txtid.setEditable(true);
											txtid.requestFocus();
							         };

							         ActionListener opBorrar_movimiento_tecleado = new ActionListener(){
									   public void actionPerformed(ActionEvent arg0) {
										   if(new ActualizarSQL().cancelar_movimiento_bancario(txtid.getText().toString())){
											JOptionPane.showMessageDialog(null, "El Registro Se Cancelo Correctamente", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
											   
												while(tablaconciliados.getRowCount()>0){
													  modeloconciliados.removeRow(0); }
												
												while(tabla_mov_bancarios.getRowCount()>0){
													modelobancarios.removeRow(0); }
												
												while(tabla_mov_contabilidad.getRowCount()>0){
													modelocontabilidad.removeRow(0); }
												limpiar();
										   }else{
												JOptionPane.showMessageDialog(null, "Error en la funcion opBorrar_movimiento_tecleado \n al intentar borrar el ID:"+txtid.getText().toString(), "Avisa al Administrador del Sistema", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
										  }	 
									      }
								        };
									
								    };
								    
				
		public static void main(String args[]){
			try{
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				new Cat_Conciliacion_De_Movimientos_Bancarios_Contra_Movimientos_Contables().setVisible(true);
			}catch(Exception e){	}
		}
}