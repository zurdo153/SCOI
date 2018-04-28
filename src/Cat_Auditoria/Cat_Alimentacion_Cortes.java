package Cat_Auditoria;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
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
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import Cat_Reportes.Cat_Reporte_De_Corte_De_Caja;
import Cat_Reportes.Cat_Reporte_De_Depositos_Cortes;
import Cat_Reportes.Cat_Reporte_De_Efectivo_Cortes;
import Conexiones_SQL.ActualizarSQL;
import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.BuscarTablasModel;
import Conexiones_SQL.Connexion;
import Conexiones_SQL.Generacion_Reportes;
import Obj_Auditoria.Obj_Alimentacion_Cortes;
import Obj_Auditoria.Obj_Alimentacion_De_Cheques;
import Obj_Auditoria.Obj_Alimentacion_Denominacion;
import Obj_Auditoria.Obj_Alimentacion_Por_Denominacion;
import Obj_Lista_de_Raya.Obj_Empleados;
import Obj_Lista_de_Raya.Obj_Puestos;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.Obj_tabla;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Alimentacion_Cortes extends JFrame{

	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	Connexion con = new Connexion();
	
	JTextArea txaObservaciones = new Componentes().textArea(new JTextArea(), "Observasiones", 500);
	JScrollPane Observasiones = new JScrollPane(txaObservaciones);
	
	JLabel lblFolio_Empleado = new JLabel();
	JLabel lblNombre_Completo = new JLabel();
	JLabel lblEstablecimineto = new JLabel();
	JLabel lblPuesto = new JLabel();
	JLabel lblFoto = new JLabel();
	JLabel lblComentario = new JLabel("Comentario: ");
	JLabel lbltablaAsignacionesPorFecha = new JLabel();
	JLabel lbltablaRetiros = new JLabel();
	JLabel lblDiferencia = new JLabel();
	JLabel lblDiferenciaCorte = new JLabel("$ 0000.00");
	JLabel lblEtiquetaCorte = new JLabel("");
	JLabel lblMarco = new JLabel();
	JLabel lblFolio_Corte = new JLabel();
	
	JButton btnGuardarCorte     = new JCButton("Guardar"                  ,"Guardar.png","Azul");
	JButton btnFiltro           = new JCButton("Buscar Otro Cajero"       ,"Text preview.png","Azul");
	JButton btnAsignacion       = new JCButton("Asignaciones"             ,"Accounting.png","Azul"); 
	JButton btnTurno            = new JCButton("Turno"                    ,"Accounting.png","Azul"); 
	JButton btnVauchers         = new JCButton("Vouchers"                 ,"tarjeta-de-credito-visa-icono-8242-16.png","Azul"); 
	JButton btnReimprimir       = new JCButton("Reimprimir Corte Guardado","Print.png","Azul"); 
	JButton btnCancelar         = new JCButton("Cancelar"                 ,"deshacer16.png","Azul"); 
	JButton btnSalir            = new JCButton("Salir"                    ,"salir16.png","Azul"); 
	JButton btnEfectivo         = new JCButton(""                         ,"dinero-icono-8797-16.png","Azul"); 
	JButton btnDeposito         = new JCButton(""                         ,"monedas-en-efectivo-en-moneda-icono-4023-16.png","Azul"); 
	JButton btnCheques	        = new JCButton(""                         ,"","Azul");
	JButton btnFS               = new JCButton(""                         ,"fast-food-icon16.png","Azul");
	JButton btnRetiroCajero     = new JCButton(""                         ,"boveda-de-dinero-en-efectivo-de-seguridad-icono-6192-16.png","Azul");
	JButton btnQuitarAsignacion = new JCButton("Quitar Asignacion"        ,"eliminar-bala-icono-7773-32.png","Rojo"); 
	JButton btnQuitarVauchers   = new JCButton("Quitar Voucher"           ,"eliminar-bala-icono-7773-32.png","Rojo"); 
//	JButton btnQuitarTurno      = new JCButton("Quitar Turno"             ,"eliminar-bala-icono-7773-32.png","Rojo"); 
	
	JCheckBox chStatus = new JCheckBox("Status");
	
	JTextField txtApartados         = new JTextField("");
	JTextField txtAbonos            = new JTextField("");
	JTextField txtEfectivo          = new JTextField("");
	JTextField txtFechaCorte        = new JTextField("");
	JTextField txtDeposito          = new JTextField("");
	JTextField txtDineroElectronico = new JTextField("");
	JTextField txtRetiroCajero      = new JTextField("");
	JTextField txtCheques           = new JTextField("");
	JTextField txtTiempoAire        = new JTextField("");
	JTextField txtReciboLuz         = new JTextField("");
	JTextField txtCorteSistema      = new JTextField("");
	JTextField txtTotalVaucher      = new JTextField("");
	JTextField txtTotalRetiros      = new JTextField("");
	JTextField txtTotalFS           = new JTextField("");

	Border border = LineBorder.createGrayLineBorder();
//	    Icon warnIcon = MetalIconFactory.getTreeComputerIcon();
	
	String Efectivo = "";
	public String img = "";
	String file = "X:\\Empleados\\Un.JPG";
	
	int folio_usuario;
	JLabel lblUsuario = new JLabel("USUARIO: ");
	
	Obj_tabla ObjTabf =new Obj_tabla();
	
	int columnasig = 8,checkbox=-1;
	public void init_tabla_asignaciones(){
    	this.tabla_asignaciones.getColumnModel().getColumn(0).setMinWidth(70);
    	this.tabla_asignaciones.getColumnModel().getColumn(1).setMinWidth(70);
    	this.tabla_asignaciones.getColumnModel().getColumn(2).setMinWidth(210);
    	this.tabla_asignaciones.getColumnModel().getColumn(3).setMinWidth(90);
    	this.tabla_asignaciones.getColumnModel().getColumn(4).setMinWidth(70);
     	this.tabla_asignaciones.getColumnModel().getColumn(5).setMinWidth(160);
     	this.tabla_asignaciones.getColumnModel().getColumn(6).setMinWidth(140);
     	this.tabla_asignaciones.getColumnModel().getColumn(7).setMinWidth(140);
		String comandof="";
		String basedatos="0",pintar="si";
		ObjTabf.Obj_Refrescar(tabla_asignaciones,modelo_asignaciones, columnasig, comandof, basedatos,pintar,checkbox);
    }
	
	@SuppressWarnings("rawtypes")
	public Class[] base (){
		Class[] types = new Class[columnasig];
		for(int i = 0; i<columnasig; i++){types[i]= java.lang.Object.class;}
		return types;
	}
	
	 public DefaultTableModel modelo_asignaciones = new DefaultTableModel(null, new String[]{"Asignacion", "F.Cajero(a)","Nombre Cajera(o)","Total","Cod Estab","Establecimiento","Fecha de Asignacion","Fecha de Liquidacion"}){
		 @SuppressWarnings("rawtypes")
			Class[] types = base();
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
			public boolean isCellEditable(int fila, int columna){return false;}
	    };
	    JTable tabla_asignaciones = new JTable(modelo_asignaciones);
		public JScrollPane scroll = new JScrollPane(tabla_asignaciones);
		
	 	int columnaturno = 8;
	 	public void init_tabla_turno(){
	     	this.tabla_turnos.getColumnModel().getColumn(0).setMinWidth(70);
	     	this.tabla_turnos.getColumnModel().getColumn(1).setMinWidth(230);
	     	this.tabla_turnos.getColumnModel().getColumn(2).setMinWidth(230);
	     	this.tabla_turnos.getColumnModel().getColumn(3).setMinWidth(150);
	     	this.tabla_turnos.getColumnModel().getColumn(4).setMinWidth(80);
	      	this.tabla_turnos.getColumnModel().getColumn(5).setMinWidth(130);
	    	this.tabla_turnos.getColumnModel().getColumn(6).setMinWidth(100);
	    	this.tabla_turnos.getColumnModel().getColumn(7).setMinWidth(100);
	 		String comandof="";
	 		String basedatos="0",pintar="si";
	 		ObjTabf.Obj_Refrescar(tabla_turnos,modelo_turnos, columnaturno, comandof, basedatos,pintar,checkbox);
	     }
	 	
	 	@SuppressWarnings("rawtypes")
	 	public Class[] baseturno (){
	 		Class[] types = new Class[columnaturno];
	 		for(int i = 0; i<columnaturno; i++){types[i]= java.lang.Object.class;}
	 		return types;
	 	}
	 	
	    public DefaultTableModel modelo_turnos = new DefaultTableModel(null, new String[]{"Turno","Usuario", "Nombre","Total","Cod.Estab","Establecimiento","Fecha Inicial","Fecha Final"}){
	 		 @SuppressWarnings("rawtypes")
	 			Class[] types = base();
	 			@SuppressWarnings({ "unchecked", "rawtypes" })
	 			public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
	 			public boolean isCellEditable(int fila, int columna){return false;}
	 	};
	 	 JTable tabla_turnos = new JTable(modelo_turnos);
	 	 public JScrollPane scrollTurno = new JScrollPane(tabla_turnos);
	 	 
	 	int columnavouchers = 12;
	 	public void init_tabla_vauchers(){
		     	this.tabla_vauchers.getColumnModel().getColumn(0).setMinWidth(70);
		     	this.tabla_vauchers.getColumnModel().getColumn(1).setMinWidth(80);
		     	this.tabla_vauchers.getColumnModel().getColumn(2).setMinWidth(120);
		     	this.tabla_vauchers.getColumnModel().getColumn(3).setMinWidth(120);
		     	this.tabla_vauchers.getColumnModel().getColumn(4).setMinWidth(70);
		      	this.tabla_vauchers.getColumnModel().getColumn(5).setMinWidth(100);
		      	this.tabla_vauchers.getColumnModel().getColumn(6).setMinWidth(100);
		      	this.tabla_vauchers.getColumnModel().getColumn(7).setMinWidth(80);
		      	this.tabla_vauchers.getColumnModel().getColumn(8).setMinWidth(110);
		      	this.tabla_vauchers.getColumnModel().getColumn(9).setMinWidth(70);
		      	this.tabla_vauchers.getColumnModel().getColumn(10).setMinWidth(70);
		      	this.tabla_vauchers.getColumnModel().getColumn(11).setMinWidth(70);
		 		String comandof="";
		 		String basedatos="0",pintar="si";
		 		ObjTabf.Obj_Refrescar(tabla_vauchers,modelo_vauchers,columnavouchers, comandof, basedatos,pintar,checkbox);
		     }
		 	
		 	@SuppressWarnings("rawtypes")
		 	public Class[] basevouchers (){
		 		Class[] types = new Class[columnavouchers];
		 		for(int i = 0; i<columnavouchers; i++){types[i]= java.lang.Object.class;}
		 		return types;
		 	}
		 	
		    public DefaultTableModel modelo_vauchers = new DefaultTableModel(null, new String[]{"Ticket", "Afiliacion", "Numero De Tarjeta",  "Fecha E.", "Cod. Aut.", "Tipo De Tarjeta", "Banco Emisor", "Tipo De Operacion", "Fecha Aut", "Importe","Asignacion","Retiro cliente"}){
		 		 @SuppressWarnings("rawtypes")
		 			Class[] types = base();
		 			@SuppressWarnings({ "unchecked", "rawtypes" })
		 			public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
		 			public boolean isCellEditable(int fila, int columna){return false;}
		 	};
		 	 JTable tabla_vauchers = new JTable(modelo_vauchers);
		 	 public JScrollPane scrollVauchers = new JScrollPane(tabla_vauchers);

				
			int columnatotales_fecha = 3;
			 public void init_tabla_totales_fecha(){
			    	this.tabla_totales_por_fecha.getColumnModel().getColumn(0).setMinWidth(80);
			     	this.tabla_totales_por_fecha.getColumnModel().getColumn(1).setMinWidth(110);
			     	this.tabla_totales_por_fecha.getColumnModel().getColumn(2).setMinWidth(120);
			 		String comandof="";
			 		String basedatos="0",pintar="si";
			 		ObjTabf.Obj_Refrescar(tabla_totales_por_fecha,modelo_totales_por_fecha,columnatotales_fecha, comandof, basedatos,pintar,checkbox);
			  }
				 	
			 	@SuppressWarnings("rawtypes")
			 public Class[] basetotalesfecha (){
			 		Class[] types = new Class[columnatotales_fecha];
			 		for(int i = 0; i<columnatotales_fecha; i++){types[i]= java.lang.Object.class;}
			 		return types;
			 }
				 	
			 public DefaultTableModel modelo_totales_por_fecha = new DefaultTableModel(null, new String[]{"Asignacion","Fecha", "Importe Total"}){
			 		@SuppressWarnings("rawtypes")
					Class[] types = base();
					@SuppressWarnings({ "unchecked", "rawtypes" })
				    public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
				 	public boolean isCellEditable(int fila, int columna){return false;}
		  	 };
		  	 
			 JTable tabla_totales_por_fecha = new JTable(modelo_totales_por_fecha);
			 public JScrollPane scroll_totales_por_fecha = new JScrollPane(tabla_totales_por_fecha);

	
				int columnaretiro_clientes = 2;
				 public void init_tabla_retiros_clientes(){
				    	this.tabla_retiro_de_clientes.getColumnModel().getColumn(0).setMinWidth(90);
				     	this.tabla_retiro_de_clientes.getColumnModel().getColumn(1).setMinWidth(110);
				 		String comandof="";
				 		String basedatos="0",pintar="si";
				 		ObjTabf.Obj_Refrescar(tabla_retiro_de_clientes,modelo_retiro_de_clientes,columnaretiro_clientes, comandof, basedatos,pintar,checkbox);
				  }
					 	
				 	@SuppressWarnings("rawtypes")
				 public Class[] basetoretiros_clientes(){
				 		Class[] types = new Class[columnaretiro_clientes];
				 		for(int i = 0; i<columnaretiro_clientes; i++){types[i]= java.lang.Object.class;}
				 		return types;
				 }
					 	
				 public DefaultTableModel modelo_retiro_de_clientes = new DefaultTableModel(null, new String[]{"Tickets", "Retiro"}){
				 		@SuppressWarnings("rawtypes")
						Class[] types = base();
						@SuppressWarnings({ "unchecked", "rawtypes" })
					    public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
					 	public boolean isCellEditable(int fila, int columna){return false;}
			  	 };
			  	 
				 JTable tabla_retiro_de_clientes = new JTable(modelo_retiro_de_clientes);
				 public JScrollPane scroll_retiro_de_clientes = new JScrollPane(tabla_retiro_de_clientes);
				 
	Border blackline, etched, raisedbevel, loweredbevel, empty;
	DecimalFormat formato = new DecimalFormat("#0.00");

	
	int filaDep = 0;
	int columnaDep = 1;
	int filaEfec = 0;
	int columnaEfect = 4;
	int bandera_de_guardado = 0;
	String establecimiento="";
	String Bandera_Asignacion_o_Turno="";
	
	public Cat_Alimentacion_Cortes(int folio, String estab, String folio_corte) {
		establecimiento=estab;
		
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/bolsa-de-dinero-en-efectivo-icono-6673-32.png"));
		this.setTitle("Alimentacion Cortes");
		
		txaObservaciones.setBorder(border);
		
		lblMarco.setBorder(BorderFactory.createTitledBorder(blackline,"Empleado"));
		lbltablaAsignacionesPorFecha.setBorder(BorderFactory.createTitledBorder(blackline,"Tabla de asignaciones por fecha de uso"));
		lbltablaRetiros.setBorder(BorderFactory.createTitledBorder(blackline,"Retiros"));
		lblDiferencia.setBorder(BorderFactory.createTitledBorder(blackline,"Diferiencia de corte"));
		
		Font font = new Font("Verdana", Font.BOLD, 14);
		lblUsuario.setFont(font);
		
		lblDiferenciaCorte.setFont(new Font("arial", Font.BOLD, 55));
		lblDiferenciaCorte.setForeground(Color.BLACK);
		lblEtiquetaCorte.setFont(new Font("arial", Font.BOLD,20));
		lblEtiquetaCorte.setForeground(Color.BLACK);
		lblComentario.setFont(new Font("arial",Font.BOLD,16));
		lblComentario.setForeground(Color.BLACK);
		
		int x = 20, y=15, ancho=140, x2=530,width=380,height=20;;
		txtCorteSistema.requestFocus();
		panel.setBorder(BorderFactory.createTitledBorder("Alimentacion Cortes"));
		
		CargarUsuario();
		txtRetiroCajero.setText(new Obj_Alimentacion_Cortes().retiroCajero(folio,estab)+"");
		
		panel.add(lblUsuario).setBounds(x2-50,y,ancho*2+90,20);
		panel.add(lblMarco).setBounds(x2-60,y+=25,ancho*2+160,135);
		
		panel.add(new JLabel("Folio Empleado:")).setBounds   (x2-50,y+=15,ancho,20);
		panel.add(lblFolio_Empleado).setBounds               (x2+50,y,ancho,20);
		panel.add(btnFiltro).setBounds                       (x2+80,y,170,20);
		
		panel.add(lblFoto).setBounds(x+ancho*5+65,y-5,120,120);
		
		panel.add(new JLabel("Nombre Completo:")).setBounds(x2-50,y+=30,ancho,20);
		panel.add(lblNombre_Completo).setBounds(x2+60,y,ancho+80,20);
		panel.add(new JLabel("Establecimiento:")).setBounds(x2-50,y+=30,ancho,20);
		panel.add(lblEstablecimineto).setBounds(x2+50,y,ancho+80,20);
		panel.add(new JLabel("Puesto:")).setBounds(x2-50,y+=30,ancho+40,20);
		panel.add(lblPuesto).setBounds(x2,y,ancho+110,20);

		y=20;
		
		panel.add(new JLabel("Folio Corte:")).setBounds(x,y,ancho,height);
		panel.add(lblFolio_Corte).setBounds(ancho-40,y,ancho*2-150,height);
		panel.add(chStatus).setBounds(x*3+ancho-20,y,70,height);
		
		panel.add(new JLabel("Deposito:")).setBounds(ancho*2,y,ancho,20);
		panel.add(txtDeposito).setBounds(ancho+x*10,y,ancho-50,20);
		panel.add(btnDeposito).setBounds(ancho+x*10+90,y,29,20);
		
		panel.add(btnAsignacion).setBounds(x,y+=25,ancho+20,20);
		panel.add(btnQuitarAsignacion).setBounds(x+260,y,ancho+40,20);
		panel.add(scroll).setBounds(x,y+=20,ancho*3+20,60);
		
		panel.add(btnTurno).setBounds(x,y+=65,ancho+20,20);
//		panel.add(btnQuitarTurno).setBounds(x+260,y,ancho+40,20);
		panel.add(scrollTurno).setBounds(x,y+=20,ancho*3+20,60);
		
		x=20;y=215;width=100;
		panel.add(new JLabel("Dinero Electronico:")).setBounds  (x      ,y    ,width ,height);
		panel.add(txtDineroElectronico).setBounds               (x+90   ,y    ,width ,height);
		panel.add(new JLabel("Retiros Cajero:")).setBounds      (x      ,y+=25,width ,height);
		panel.add(txtRetiroCajero).setBounds                    (x+90   ,y    ,width ,height);
		panel.add(btnRetiroCajero).setBounds                    (x+190  ,y    ,29    ,height);
		panel.add(new JLabel("Efectivo:")).setBounds            (x      ,y+=25,width ,height);
		panel.add(txtEfectivo).setBounds                        (x+90   ,y    ,width ,height);
		panel.add(btnEfectivo).setBounds                        (x+190  ,y    ,29    ,height);
		panel.add(new JLabel("Cheques: ")).setBounds            (x      ,y+=25,width ,height);
		panel.add(txtCheques).setBounds                         (x+90   ,y    ,width ,height);
		panel.add(btnCheques).setBounds                         (x+190  ,y    ,29    ,height);
		panel.add(new JLabel("F. Sodas: ")).setBounds           (x      ,y+=25,width ,height);
		panel.add(txtTotalFS).setBounds                         (x+90   ,y    ,width ,height);
		panel.add(btnFS).setBounds                              (x+190  ,y    ,29    ,height);
		panel.add(new JLabel("Total De Vouchers: ")).setBounds  (x      ,y+=25,width ,height);
		panel.add(txtTotalVaucher).setBounds                    (x+90   ,y    ,width ,height);
		
		y=215;
		panel.add(new JLabel("Corte del Sistema:")).setBounds   (x*10+50,y,ancho,20);
		panel.add(txtCorteSistema).setBounds(ancho+x*10+30,y,ancho*2-190,20);
		
		panel.add(new JLabel("Apartados: ")).setBounds(x*10+50,y+=25,ancho,height);
		panel.add(txtApartados).setBounds(ancho+x*10+30,y,ancho*2-190,height);
		
		panel.add(lblComentario).setBounds(x2-50,y,100,height);
		panel.add(Observasiones).setBounds(x2-50,y+=25,420,105);
		
		panel.add(new JLabel("Abono Clientes: ")).setBounds(x*10+50,y,ancho,20);
		panel.add(txtAbonos).setBounds(ancho+x*10+30,y,ancho*2-190,20);
		
		panel.add(new JLabel("Tiempo Aire: ")).setBounds(x*10+50,y+=25,ancho,20);
		panel.add(txtTiempoAire).setBounds(ancho+x*10+30,y,ancho*2-190,20);
		
		panel.add(new JLabel("Recibo de luz: ")).setBounds(x*10+50,y+=25,ancho,20);
		panel.add(txtReciboLuz).setBounds(ancho+x*10+30,y,ancho*2-190,20);
		
		panel.add(new JLabel("Total de retiros: ")).setBounds(x*10+50,y+=25,ancho,20);
		panel.add(txtTotalRetiros).setBounds(ancho+x*10+30,y-1,ancho*2-190,20);
		
		x=20;
		panel.add(btnVauchers).setBounds(x,y+=25,ancho+20,20);
		panel.add(scrollVauchers).setBounds(x,y+=20,ancho*6+40,105);
		
		panel.add(lblDiferencia).setBounds(x2+50,y+130,ancho*2+40,100);
		panel.add(lblDiferenciaCorte).setBounds(x2+60,y+140,ancho*2+120,80);
		panel.add(lblEtiquetaCorte).setBounds(x2+280,y+192,150,40);
		
		panel.add(lbltablaAsignacionesPorFecha).setBounds(x,y+130,ancho*2+40,125);
		panel.add(scroll_totales_por_fecha).setBounds(x+10,y+145,ancho*2+20,100);
		
		panel.add(lbltablaRetiros).setBounds(x*17+10,y+130,ancho+80,125);
		panel.add(scroll_retiro_de_clientes).setBounds(x*18,y+145,ancho+60,100);
		
		panel.add(btnQuitarVauchers).setBounds(x,y+=105,ancho+20,20);
		panel.add(btnReimprimir).setBounds(x*34,y,ancho+80,30);
		
		panel.add(btnGuardarCorte).setBounds(x*29,y+=130,ancho-40,20);
		panel.add(btnCancelar).setBounds(x*29+110,y,ancho-37,20);
		panel.add(btnSalir).setBounds(x*29+220,y,ancho-40,20);

		init_tabla_asignaciones();
		init_tabla_turno();
		init_tabla_vauchers();
		init_tabla_totales_fecha();
		init_tabla_retiros_clientes();
		
		lblEstablecimineto.setText(estab);
		
		btnAsignacion.addActionListener      (opAsignacion);
		btnQuitarAsignacion.addActionListener(opQuitarAsignacion);
		btnTurno.addActionListener           (opTurno);
//		btnQuitarTurno.addActionListener     (opQuitarTurno);
		btnVauchers.addActionListener        (op_Abrir_Ventana_Seleccion_De_Vouchers);
		btnQuitarVauchers.addActionListener  (opQuitarVauchers);
		btnRetiroCajero.addActionListener    (opRetirosCajeros);
		btnGuardarCorte.addActionListener    (guardar);
		btnCancelar.addActionListener        (cancelar);
		btnSalir.addActionListener           (salir);
		btnFiltro.addActionListener          (filtro);

		btnCancelar.setEnabled(false);
		
		txaObservaciones.setLineWrap(true); 
		txaObservaciones.setWrapStyleWord(true);
		
		btnEfectivo.addActionListener  (opAlimentarDenominacion);
		btnDeposito.addActionListener  (opAlimentarDeposito);
		btnCheques.addActionListener   (opAlimentarCheques);
		btnFS.addActionListener        (opFS);
		btnReimprimir.addActionListener(opReimprimir);

		txtCorteSistema.addKeyListener(validaNumericoConPunto);
		txtDeposito.addKeyListener(validaNumericoConPunto2);
		
		lblFoto.setBorder(border);
		
		ImageIcon tmpIconBtncheque = new ImageIcon("imagen/Icono_cheque.png");
		Icon iconobtncheque = new ImageIcon(tmpIconBtncheque.getImage().getScaledInstance(btnCheques.getWidth(), btnCheques.getHeight(), Image.SCALE_DEFAULT));
		btnCheques.setIcon(iconobtncheque);
		
//		Blokear funcion de cerrar ventana de windows
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		cont.add(panel);
		
		Obj_Empleados re = new Obj_Empleados().buscar(folio);
		
		if(re.getFolio()!=0){
			ImageIcon tmpIconAux = new ImageIcon(System.getProperty("user.dir")+"/tmp/tmp.jpg");
			Icon icono = new ImageIcon(tmpIconAux.getImage().getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(), Image.SCALE_DEFAULT));
			lblFoto.setIcon(icono);	
			lblFolio_Empleado.setText(re.getFolio()+"");
			lblNombre_Completo.setText(re.getNombre()+" "+re.getAp_paterno()+" "+re.getAp_materno()+"");
		}
		
		Obj_Puestos puesto = new Obj_Puestos().buscar(re.getPuesto());
		lblPuesto.setText(puesto.getPuesto());
		
		lblFolio_Corte.setText(folio_corte);

		chStatus.setSelected(true);
		
		txtDineroElectronico.setEditable(false);
		txtFechaCorte.setEditable(false);
		txtCorteSistema.setEditable(false);
		txtApartados.setEditable(false);
		txtAbonos.setEditable(false);
		txtDeposito.setEditable(false);
		txtRetiroCajero.setEditable(false);
		txtEfectivo.setEditable(false);
		
		txtTiempoAire.setEditable(false);
		txtReciboLuz.setEditable(false);
		txtCheques.setEditable(false);
		txtTotalVaucher.setEditable(false);
		txtTotalRetiros.setEditable(false);
		txtTotalFS.setEditable(false);		
		
		chStatus.setEnabled(false);
		
		btnDeposito.setEnabled(false);
		btnEfectivo.setEnabled(false);
		btnCheques.setEnabled(false);
		btnFS.setEnabled(false);
		
		if(lblEstablecimineto.getText().equals("REFACCIONARIA") || lblEstablecimineto.getText().equals("FERRETERIA")|| lblEstablecimineto.getText().equals("HOGARY")){
//			btnAsignacion.setEnabled(false);
//			btnQuitarAsignacion.setEnabled(false);
			Bandera_Asignacion_o_Turno="T";
		}else {
			btnTurno.setEnabled(false);
			Bandera_Asignacion_o_Turno="A";
		}
	
		btnReimprimir.setEnabled(true);
		
		this.setSize(940,685);
		this.setResizable(true);
		this.setLocationRelativeTo(null);

	}
	
	public void CargarUsuario()
	{
		  File archivo = null;
 	      FileReader fr = null;
 	      BufferedReader br = null;
		 try {
 	         archivo = new File ("Config/users");
 	         fr = new FileReader (archivo);
 	         br = new BufferedReader(fr);
 	         String linea;
 	         
 	        folio_usuario=Integer.parseInt(br.readLine());
 	         while((linea=br.readLine())!=null){
 	        	lblUsuario.setText("Usuario: "+linea);
 	         }
 	      }
 	      catch(Exception e){
 	         e.printStackTrace();
 	      }finally{
 	         try{                   
 	            if( null != fr ){  
 	               fr.close();    
 	            }                 
 	         }catch (Exception e2){
 	            e2.printStackTrace();
 	         }
 	      }
	}
	

	
	public void tablaRender(JTable tbl){
		tbl.getTableHeader().setReorderingAllowed(false) ;
		tbl.setAutoResizeMode(JTable.AUTO_RESIZE_OFF );
		
		for(int i=0; i<tbl.getColumnCount(); i++){
			tbl.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",12));
		}
	}
	
//	btnDeposito
	ActionListener opAlimentarDeposito = new ActionListener(){
		public void actionPerformed(ActionEvent e){
				btnFiltro.setEnabled(false);
				
				Obj_Alimentacion_Cortes folio_corte = new Obj_Alimentacion_Cortes().buscar_folio_corte_deposito(lblFolio_Corte.getText());
				
				if(folio_corte.getFolio_corte().equals("")){
					new Cat_Alimentacion_Deposito().setVisible(true);
				}else{
					new Cat_Alimentacion_Deposito_Modificar().setVisible(true);
				}
//			}
		}
	};
	
//	btnEfectivo
	ActionListener opAlimentarDenominacion = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			
				btnFiltro.setEnabled(false);
				new Cat_Alimentacion_De_Efectivo().setVisible(true);
		}
	};

//	btnCheques
	ActionListener opAlimentarCheques = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			
				btnFiltro.setEnabled(false);
				new Cat_Captura_Totatales_De_Cheques().setVisible(true);
		}
	};
	
//	btnRetirosCajeros
	ActionListener opRetirosCajeros = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			new Cat_Retiros_A_Detalle(Integer.valueOf(lblFolio_Empleado.getText()),lblEstablecimineto.getText()).setVisible(true);
		}
	};
	
	ActionListener guardar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(validaCampos()!="") {
				JOptionPane.showMessageDialog(null, "los siguientes campos son requeridos:\n"+validaCampos(), "Error al guardar registro", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
				return;
			}else{
					Obj_Alimentacion_Cortes corte = new Obj_Alimentacion_Cortes();
					
						corte.setFolio_corte(lblFolio_Corte.getText());
						corte.setUsuario(folio_usuario);
						corte.setFolio_empleado(Integer.parseInt(lblFolio_Empleado.getText()+""));
						corte.setEstablecimiento_de_corte(lblEstablecimineto.getText().toUpperCase().trim());
						
						corte.setCorte_sistema(Float.parseFloat(txtCorteSistema.getText()));
						corte.setApartado(Float.parseFloat(txtApartados.getText().equals("")?"0":txtApartados.getText()));
						corte.setAbono(Float.parseFloat(txtAbonos.getText().equals("")?"0":txtAbonos.getText()));
						
						corte.setDinero_electonico(Float.parseFloat(txtDineroElectronico.getText().equals("")?"0":txtDineroElectronico.getText()));
						corte.setTiempo_aire(Float.parseFloat(txtTiempoAire.getText().equals("")?"0":txtTiempoAire.getText()));
						corte.setRecibo_luz(Float.parseFloat(txtReciboLuz.getText().equals("")?"0":txtReciboLuz.getText()));
						corte.setDeposito(Float.parseFloat(txtDeposito.getText()));
						corte.setEfectivo(Float.parseFloat(txtEfectivo.getText()));
						corte.setCheques(Float.valueOf(txtCheques.getText().equals("")?"0":txtCheques.getText()));
						corte.setTotal_de_vauchers(Float.valueOf(txtTotalVaucher.getText().equals("")?"0":txtTotalVaucher.getText()));
						corte.setDiferencia_corte(Float.valueOf(lblDiferenciaCorte.getText().substring(2,lblDiferenciaCorte.getText().length())));
						
						if(txaObservaciones.getText().length()!=0){
							corte.setComentario(txaObservaciones.getText());
						}else{
							corte.setComentario("");
						}
						
						//TODO GUARDADO DE CORTES
						if(Bandera_Asignacion_o_Turno.equals("T")) {
							if(corte.guardar(tabla_guardar_turnos(),tabla_guardar_vauchers(),tabla_guardar_totales_por_fecha(), lista_de_turnos_en_uso(),Bandera_Asignacion_o_Turno)){
								btnAsignacion.setEnabled(false);
								btnQuitarAsignacion.setEnabled(false);
								btnVauchers.setEnabled(false);
								btnQuitarVauchers.setEnabled(false);
								btnEfectivo.setEnabled(false);
								btnDeposito.setEnabled(false);
								btnCheques.setEnabled(false);
								btnFS.setEnabled(false);
								btnGuardarCorte.setEnabled(false);
								btnCancelar.setEnabled(false);
								btnTurno.setEnabled(false);
								btnRetiroCajero.setEnabled(false);
								btnSalir.setEnabled(true);
								btnFiltro.setEnabled(true);
								
								bandera_de_guardado=1;
								
								if(bandera_de_guardado==1){
									btnReimprimir.setEnabled(true);
								}
								
								new Cat_Reporte_De_Corte_De_Caja(lblFolio_Corte.getText().trim());
								
							}else{
								JOptionPane.showMessageDialog(null, "Ha ocurrido un error al guardar el corte","Error",JOptionPane.WARNING_MESSAGE);
								return;
							}
						}else {
							if(corte.guardar(tabla_guardar_asignaciones(),tabla_guardar_vauchers(),tabla_guardar_totales_por_fecha(), lista_de_asignaciones_en_uso(),Bandera_Asignacion_o_Turno)){
								btnAsignacion.setEnabled(false);
								btnQuitarAsignacion.setEnabled(false);
								btnVauchers.setEnabled(false);
								btnQuitarVauchers.setEnabled(false);
								btnEfectivo.setEnabled(false);
								btnDeposito.setEnabled(false);
								btnCheques.setEnabled(false);
								btnFS.setEnabled(false);
								btnGuardarCorte.setEnabled(false);
								btnCancelar.setEnabled(false);
								btnRetiroCajero.setEnabled(false);
								btnSalir.setEnabled(true);
								btnFiltro.setEnabled(true);
								
								bandera_de_guardado=1;
								
								if(bandera_de_guardado==1){
									btnReimprimir.setEnabled(true);
								}
								
								new Cat_Reporte_De_Corte_De_Caja(lblFolio_Corte.getText().trim());
								
							}else{
								JOptionPane.showMessageDialog(null, "Ha ocurrido un error al guardar el corte","Error",JOptionPane.WARNING_MESSAGE);
								return;
							}
						}	
				}	
			}
	};
	
	ActionListener opReimprimir = new ActionListener(){
		public void actionPerformed(ActionEvent e){
				new Cat_Reportes_De_Cortes().setVisible(true);
		}
	};
	
   	public int getFilasIZAGAR(String qry){
		int filas=0;
		Statement stmt = null;
		try {
			stmt = new Connexion().conexion_IZAGAR().createStatement();
			ResultSet rs = stmt.executeQuery(qry);
			while(rs.next()){
				filas++;
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return filas;
	}	
   	
	private Object[] lista_de_asignaciones_en_uso(){
		Object[] vector = new Object[tabla_asignaciones.getRowCount()];
		
			for(int i=0; i<tabla_asignaciones.getRowCount(); i++){
				vector[i] = modelo_asignaciones.getValueAt(i,0).toString().trim();
			}
		return vector;
	}
	
	private Object[] lista_de_turnos_en_uso(){

		Object[] vector = new Object[tabla_turnos.getRowCount()];
		
			for(int i=0; i<tabla_turnos.getRowCount(); i++){
				vector[i] = modelo_turnos.getValueAt(i,0).toString().trim();
			}
		return vector;
	}
	
	private Object[][] tabla_guardar_asignaciones(){
		Object[][] matriz = new Object[tabla_asignaciones.getRowCount()][8];
		for(int i=0; i<tabla_asignaciones.getRowCount(); i++){
			
			matriz[i][0] = modelo_asignaciones.getValueAt(i,0).toString().trim();
			matriz[i][1] = modelo_asignaciones.getValueAt(i,1).toString().trim();
			matriz[i][2] = modelo_asignaciones.getValueAt(i,2).toString().trim();
			matriz[i][3] = modelo_asignaciones.getValueAt(i,3).toString().trim();
			matriz[i][4] = modelo_asignaciones.getValueAt(i,4).toString().trim();
			matriz[i][5] = modelo_asignaciones.getValueAt(i,5).toString().trim();
			matriz[i][6] = modelo_asignaciones.getValueAt(i,6).toString().trim();
			matriz[i][7] = modelo_asignaciones.getValueAt(i,7).toString().trim();
			
		}
		return matriz;
	}
	
	private Object[][] tabla_guardar_turnos(){
		Object[][] matriz = new Object[tabla_turnos.getRowCount()][8];
		for(int i=0; i<tabla_turnos.getRowCount(); i++){
			
			matriz[i][0] = modelo_turnos.getValueAt(i,0).toString().trim();
			matriz[i][1] = modelo_turnos.getValueAt(i,1).toString().trim();
			matriz[i][2] = modelo_turnos.getValueAt(i,2).toString().trim();
			matriz[i][3] = modelo_turnos.getValueAt(i,3).toString().trim();
			matriz[i][4] = modelo_turnos.getValueAt(i,4).toString().trim();
			matriz[i][5] = modelo_turnos.getValueAt(i,5).toString().trim();
			matriz[i][6] = modelo_turnos.getValueAt(i,6).toString().trim();
			matriz[i][7] = modelo_turnos.getValueAt(i,7).toString().trim();
			
		}
		return matriz;
	}
	
	private Object[][] tabla_guardar_vauchers(){
		Object[][] matriz = new Object[tabla_vauchers.getRowCount()][12];
		for(int i=0; i<tabla_vauchers.getRowCount(); i++){
			
			matriz[i][0] = modelo_vauchers.getValueAt(i,0).toString().trim();
			matriz[i][1] = modelo_vauchers.getValueAt(i,1).toString().trim();
			matriz[i][2] = modelo_vauchers.getValueAt(i,2).toString().trim();
			matriz[i][3] = modelo_vauchers.getValueAt(i,3).toString().trim();
			matriz[i][4] = modelo_vauchers.getValueAt(i,4).toString().trim();
			matriz[i][5] = modelo_vauchers.getValueAt(i,5).toString().trim();
			matriz[i][6] = modelo_vauchers.getValueAt(i,6).toString().trim();
			matriz[i][7] = modelo_vauchers.getValueAt(i,7).toString().trim();
			matriz[i][8] = modelo_vauchers.getValueAt(i,8).toString().trim();
			matriz[i][9] = modelo_vauchers.getValueAt(i,9).toString().trim();
			matriz[i][10] = modelo_vauchers.getValueAt(i,10).toString().trim();
			matriz[i][11] = modelo_vauchers.getValueAt(i,11).toString().trim();
			
		}
		return matriz;
	}
	
	private Object[][] tabla_guardar_totales_por_fecha(){
		Object[][] matriz = new Object[tabla_totales_por_fecha.getRowCount()][3];
		for(int i=0; i<tabla_totales_por_fecha.getRowCount(); i++){
			
			matriz[i][0] = modelo_totales_por_fecha.getValueAt(i,0).toString().trim();
			matriz[i][1] = modelo_totales_por_fecha.getValueAt(i,1).toString().trim();
			matriz[i][2] = modelo_totales_por_fecha.getValueAt(i,2).toString().trim();
			
		}
		return matriz;
	}
	
	
	ActionListener cancelar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			
			borrar_lista_de_asignaciones();
			
					Obj_Alimentacion_Cortes folio_corte_denominacion = new Obj_Alimentacion_Cortes();
						if(folio_corte_denominacion.eliminar(lblFolio_Corte.getText())){
							
							txtFechaCorte.setText("");
							
							txtCorteSistema.setText("");
							txtTiempoAire.setText("");
							txtReciboLuz.setText("");
							txtDineroElectronico.setText("");
							txtEfectivo.setText("");
							txtDeposito.setText("");
							txtCheques.setText("");
							
							txtTotalVaucher.setText("");
							txaObservaciones.setText("");
							
							lblDiferenciaCorte.setText("");
							
							btnCancelar.setEnabled(false);
							btnFiltro.setEnabled(true);
							btnSalir.setEnabled(true);
							
							btnEfectivo.setEnabled(false);
							btnDeposito.setEnabled(false);
							btnCheques.setEnabled(false);
							btnFS.setEnabled(false);
							
							while(tabla_asignaciones.getRowCount()>0) {
								modelo_asignaciones.removeRow(0);
							}
						
							while(tabla_vauchers.getRowCount()>0) {
								modelo_vauchers.removeRow(0);
							}
							
							while (tabla_totales_por_fecha.getRowCount()>0) {
								modelo_totales_por_fecha.removeRow(0);							
							}
							
//							dispose();
							JOptionPane.showMessageDialog(null, "La registro se elimino exitosamente","Aviso",JOptionPane.INFORMATION_MESSAGE);
							return;
						}else{
							JOptionPane.showMessageDialog(null, "Ocurrió un error al intentar eliminar el registro","Error",JOptionPane.ERROR_MESSAGE);
							return;
						}
				}
			};
			
	ActionListener opAsignacion = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(tabla_asignaciones.getRowCount()>0){
				JOptionPane.showMessageDialog(null, "Ya se a seleccionado una asignacion, para cambiarla es necesario quitar la asignacion seleccionada","Aviso",JOptionPane.ERROR_MESSAGE);
				return;
			}else{
				Bandera_Asignacion_o_Turno="A";
				new Cat_Filtrar_Asignaciones(cadenaAsignacionParametro()/*,lblNombre_Completo.getText()*/).setVisible(true);
			}
			
		}
	};
	
	ActionListener opTurno = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(tabla_asignaciones.getRowCount()>0){
				Bandera_Asignacion_o_Turno="T";
				JOptionPane.showMessageDialog(null, "Ya se a seleccionado una asignacion, para cambiarla es necesario quitar la asignacion seleccionada","Aviso",JOptionPane.ERROR_MESSAGE);
				return;
			}else{
				new Cat_Filtrar_Turno().setVisible(true);
			}
			
		}
	};
	
	ActionListener opFS = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			new Cat_Seleccion_De_Ticket_De_FS_Cortes(Integer.valueOf(lblFolio_Empleado.getText()), lblNombre_Completo.getText().trim()).setVisible(true);
		}
	};
	
	public String cadenaAsignacionParametro(){
		
		String cadena="";
		
		if(tabla_asignaciones.getRowCount()>0){
				for(int i=0; i<tabla_asignaciones.getRowCount(); i++){
					cadena+= "'"+(cargar_tabla_asignaciones_en_uso()[i][0].toString().trim())+"',";
				}
				cadena = cadena.substring(0,cadena.length()-1);
		}
		else{
			cadena = "''";
		}
		return cadena;
	}
	
	private Object[][] cargar_tabla_asignaciones_en_uso(){

		Object[][] matriz = new Object[tabla_asignaciones.getRowCount()][8];
		
			for(int i=0; i<tabla_asignaciones.getRowCount(); i++){
				
					matriz[i][0] = modelo_asignaciones.getValueAt(i,0).toString().trim();
					matriz[i][1] = modelo_asignaciones.getValueAt(i,1).toString().trim();
					matriz[i][2] = modelo_asignaciones.getValueAt(i,2).toString().trim();
					matriz[i][3] = modelo_asignaciones.getValueAt(i,3).toString().trim();
					matriz[i][4] = modelo_asignaciones.getValueAt(i,4).toString().trim();
					matriz[i][5] = modelo_asignaciones.getValueAt(i,5).toString().trim();
					matriz[i][6] = modelo_asignaciones.getValueAt(i,6).toString().trim();
					matriz[i][7] = modelo_asignaciones.getValueAt(i,7).toString().trim();
			}
		return matriz;
	}
	
	
	ActionListener op_Abrir_Ventana_Seleccion_De_Vouchers = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			
					if(tabla_asignaciones.getRowCount()>0) {
						new Cat_Filtrar_Vauchers("A").setVisible(true);
						return;
					}else {
						if(tabla_turnos.getRowCount()>0) {
							new Cat_Filtrar_Vauchers("T").setVisible(true);
							return;
					    }else {
			      			 JOptionPane.showMessageDialog(null, "Seleccione Por Lo Menos Un Folio De Asignacion o Turno de Venta Mostrador","Aviso",JOptionPane.INFORMATION_MESSAGE);
			                 return;
					    	
					    }
			    	}
		}				
	};
	
 	public boolean borrar_lista_de_asignaciones(){
		String query_total_ta_rluz_por_folio_de_corte = "delete tb_relacion_por_pagos_de_servicios where folio_corte = '" + lblFolio_Corte.getText() + "'";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query_total_ta_rluz_por_folio_de_corte);
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en Cat_Alimentacion_Cortes en la funcion borrar_lista_de_asignaciones  SQLException: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en Cat_Alimentacion_Cortes en la funcion borrar_lista_de_asignaciones  SQLException: "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);

				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
		}		
		return true;
 	}
 	
 	public boolean cargar_lista_de_asignaciones(){
 		boolean registrado = false;
 		
 		Connection con = new Connexion().conexion();
		PreparedStatement pstmt_ta_rluz = null;
		
		for(int i=0; i<tabla_asignaciones.getRowCount(); i++){
			
			String consulta_ta_rluz = "declare @asignacion varchar(50), @Fecha varchar(30) " +
									" set @asignacion= '"+modelo_asignaciones.getValueAt(i,0).toString().trim()+"' " +
					"		SET @Fecha =(SELECT TOP 1  CONVERT (VARCHAR(20),abonos_clientes.fecha,103) AS Fecha FROM  abonos_clientes with(nolock) LEFT OUTER JOIN  facremtick with(nolock) ON  facremtick.folio=abonos_clientes.folio_aplicado " +
					"                        LEFT OUTER JOIN  asignaciones_cajeros ON asignaciones_cajeros.folio=facremtick.folio_cajero " +
					"       where  facremtick.status <> 'C' and facremtick.cond_pago = '4' AND facremtick.folio_cajero=@asignacion and abonos_clientes.status='V' and abonos_clientes.fecha>=asignaciones_cajeros.fecha_inicial and abonos_clientes.fecha<=asignaciones_cajeros.fecha_liquidacion)" +
					"	(SELECT '"+lblFolio_Corte.getText()+"' as folio_corte" +
					"  ,'APARTADOS' as concepto ,facremtick.folio_cajero as Asignacion" +
					"  ,abonos_clientes.importe as Abono " +
					"  ,CONVERT (VARCHAR(20),abonos_clientes.fecha,103) AS Fecha " +
					"      FROM         abonos_clientes with(nolock)" +
					"         LEFT OUTER JOIN  facremtick with(nolock) ON  facremtick.folio=abonos_clientes.folio_aplicado" +
					"         LEFT OUTER JOIN  asignaciones_cajeros ON asignaciones_cajeros.folio=facremtick.folio_cajero " +
					"      where  facremtick.status <> 'C' and facremtick.cond_pago = '4' AND facremtick.folio_cajero=@asignacion and abonos_clientes.status='V' and abonos_clientes.fecha>=asignaciones_cajeros.fecha_inicial and abonos_clientes.fecha<=asignaciones_cajeros.fecha_liquidacion)" +
					"   UNION ALL  " +
					"	(SELECT '"+lblFolio_Corte.getText()+"' as folio_corte" +
					"   ,'APARTADOS' as concepto ,@asignacion as Asignacion" +
					"   ,sum(case when liquidaciones_tickets.afectacion = '+' then liquidaciones_tickets.importe else liquidaciones_tickets.importe * -1 end) as Abono" +
					"   ,@FECHA Fecha " +
					"     FROM ((select ticket  from liquidaciones_tickets with(nolock) where folio_asignacion=@asignacion)" +
					"            except" +
					"      (select folio  as ticket from facremtick with(nolock) where folio_cajero=@asignacion)) folio" +
					"     inner join liquidaciones_tickets on liquidaciones_tickets.folio_asignacion=@asignacion and folio.ticket=liquidaciones_tickets.ticket" +
					"     inner join datos_apartados_mercancia ON datos_apartados_mercancia.folio = folio.ticket " +
					"   group by folio.ticket,datos_apartados_mercancia.entregar_a" +
					"         ,datos_apartados_mercancia.domicilio,datos_apartados_mercancia.telefono) "+ 
												"                                           UNION ALL " +
												
										"	SELECT '"+lblFolio_Corte.getText()+"' as folio_corte" +
										"       ,'TA' as concepto " +
										" 		,@asignacion as asignacion" +
										"		,SUM(ta)as total " +
										"		,fecha " +
										"			FROM (SELECT sum(entysal.total)as ta " +
										"							,convert(varchar(20),facremtick.fecha,103)as fecha " +
										"						FROM facremtick with (nolock) " +
										"						INNER JOIN entysal on entysal.folio=facremtick.folio  WHERE (facremtick.folio_cajero = @asignacion and entysal.cod_prod='52401') " +
										"						GROUP BY convert(varchar(20),facremtick.fecha,103) " +
										"				UNION all " +
										"				SELECT isnull(sum(entysal.total*-1),0)as ta " +
										"								,convert(varchar(20),facremtick.fecha,103)as fecha " +
										"						FROM facremtick   with (nolock) " +
										"						INNER JOIN entysal on entysal.folio=facremtick.folio WHERE ( (facremtick.status = 'C') AND (facremtick.numdpc = 'FAC' + @asignacion)  and entysal.cod_prod='52401' ) " +
										"						group by convert(varchar(20),facremtick.fecha,103))t " +
										"		GROUP BY t.fecha " +
										"	UNION ALL " +
										"		SELECT '"+lblFolio_Corte.getText()+"' as folio_corte" +
										"               ,'DE' as concepto" +
										"  		    ,@asignacion as asignacion" +
										" 		    ,isnull(sum(cantidad),0) as total" +
										"  		    ,convert(varchar(20),getdate(),103)as fecha " +
									    " 	  from bonificaciones_analiticas with (nolock)" +
										"        where folio in( select folio from  facremtick with (nolock) where transaccion in ('36','37','38') and facremtick.folio_cajero=@asignacion )and operacion='U' " +
										
										"	UNION ALL " +
										"		SELECT '"+lblFolio_Corte.getText()+"' as folio_corte" +
										"               ,'LUZ' as concepto " +
										"				,@asignacion as asignacion" +
										"				,SUM(ta)as total " +
										"				,fecha " +
										"			FROM (	SELECT sum(entysal.total)as ta " +
										"							,convert(varchar(20),facremtick.fecha,103)as fecha " +
										"						FROM facremtick with (nolock) " +
										"						INNER JOIN entysal on entysal.folio=facremtick.folio  WHERE (facremtick.folio_cajero = @asignacion and entysal.cod_prod='52384') " +
										"						GROUP BY convert(varchar(20),facremtick.fecha,103) " +
										"        			UNION all " +
										"		  			SELECT isnull(sum(entysal.total*-1),0)as ta " +
										"								 ,convert(varchar(20),facremtick.fecha,103)as fecha" +
										"						FROM facremtick   with (nolock) " +
										"						INNER JOIN entysal on entysal.folio=facremtick.folio WHERE ( (facremtick.status = 'C') AND (facremtick.numdpc = 'FAC' + @asignacion)  and entysal.cod_prod='52384' )" +
										"						 group by convert(varchar(20),facremtick.fecha,103))rl " +
										"	GROUP BY rl.fecha";
			System.out.println(consulta_ta_rluz);
				String query_ta_rluz = "exec sp_insert_relacion_por_pagos_de_servicios ?,?,?,?,?";
				
				Statement s_IZAGAR;
				ResultSet rs_IZAGAR;
				
				try {
						s_IZAGAR = new Connexion().conexion_IZAGAR().createStatement();
						rs_IZAGAR = s_IZAGAR.executeQuery(consulta_ta_rluz);
						
						while(rs_IZAGAR.next()){
							
								con.setAutoCommit(false);
								pstmt_ta_rluz =  con.prepareStatement(query_ta_rluz);
								System.out.println(rs_IZAGAR.getString(1));
								System.out.println(rs_IZAGAR.getString(2));
								System.out.println(rs_IZAGAR.getString(3));
								System.out.println(rs_IZAGAR.getString(4));
								System.out.println(rs_IZAGAR.getString(5));
								pstmt_ta_rluz.setString(1, 	rs_IZAGAR.getString(1));
								pstmt_ta_rluz.setString(2, 	rs_IZAGAR.getString(2));
								pstmt_ta_rluz.setString(3,	rs_IZAGAR.getString(3));
								pstmt_ta_rluz.setDouble(4,	rs_IZAGAR.getDouble(4));
								pstmt_ta_rluz.setString(5, 	rs_IZAGAR.getString(5));
								pstmt_ta_rluz.executeUpdate();
								
								registrado = true;
						}
				} catch (SQLException e1) {
							e1.printStackTrace();
							registrado = false;
				}
			}
			
			try {
					con.commit();
			} catch (SQLException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error En Cat_Alimentacion_de_Cortes \n en la funcion cargar_lista_de_asignaciones", "Error Avise al Administrador del Sistema !!!\n"+e, JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
			}
		return registrado;
	}
 	
 	//TODO Totales TA LUZ APARTADOS ABONOS DINERO ELECTRONICO
	public void obtener_totales_de_tAire_rLuz_por_folio_de_corte(){
		String query_total_ta_rluz_por_folio_de_corte = "exec sp_select_suma_ta_rluz '"+lblFolio_Corte.getText()+"',"+Integer.valueOf(lblFolio_Empleado.getText().trim());
			Connection con = new Connexion().conexion();
			try {	Statement s = con.createStatement();
					ResultSet rs = s.executeQuery(query_total_ta_rluz_por_folio_de_corte);
				
					while(rs.next()){
						txtTiempoAire.setText(rs.getString("TA"));
						txtDineroElectronico.setText(rs.getString("DE"));
						txtReciboLuz.setText(rs.getString("RLUZ"));
						txtApartados.setText(rs.getString("APA"));
						txtAbonos.setText(rs.getString("TOTAL_ABONOS"));
						
						corte_sitema();
					}
				con.commit();
				
			} catch (SQLException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error En Cat_Alimentacion_de_Cortes \n en la funcion obtener_totales_de_tAire_rLuz_por_folio_de_corte", "Error Avise al Administrador del Sistema !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
			}
	}
	
	public void corte_sitema(){
		if(tabla_asignaciones.getRowCount()==0){
			txtCorteSistema.setText( "0.0000" );
			txtAbonos.setText("0.0000");
		}else{
			txtCorteSistema.setText( formato.format(Double.valueOf(tabla_asignaciones.getValueAt(0, 3).toString().trim()))+"" );
		}
	}
	ActionListener opQuitarAsignacion = new ActionListener(){
		public void actionPerformed(ActionEvent e){
				int fila = tabla_asignaciones.getSelectedRow();
				if(fila<0){
			   			 JOptionPane.showMessageDialog(null, "Debe Seleccionar La Asignacion Que Desea Quitar","Aviso",JOptionPane.INFORMATION_MESSAGE);
			              return;
				}else{
					modelo_asignaciones.removeRow(fila);
						
						txtTotalVaucher.setText( "" );
						txtTotalRetiros.setText("");
						
						while(tabla_vauchers.getRowCount()>0){
								modelo_vauchers.removeRow(0);
						}
						
						while(tabla_retiro_de_clientes.getRowCount()>0){
							modelo_retiro_de_clientes.removeRow(0);
						}
						
						if(borrar_lista_de_asignaciones()){
							cargar_lista_de_asignaciones();
				    		obtener_totales_de_tAire_rLuz_por_folio_de_corte();

				    		calculoDinamico();
				    			
//			    			procedimiento para llenar  (tabla_de_ventas_por_fecha)  con respecto a las asignaciones seleccionadas
			    			llenar_venta_de_asignaciones_por_fecha();
						}
				}
				if(tabla_asignaciones.getRowCount()==0){
						btnDeposito.setEnabled(false);
						btnEfectivo.setEnabled(false);
						btnCheques.setEnabled(false);
						btnFS.setEnabled(false);
				}
		}
	};
	
//	ActionListener opQuitarTurno = new ActionListener(){
//		public void actionPerformed(ActionEvent e){
//				int fila = tabla_asignaciones.getSelectedRow();
//				if(fila<0){
//			   			 JOptionPane.showMessageDialog(null, "Debe Seleccionar La Asignacion Que Desea Quitar","Aviso",JOptionPane.INFORMATION_MESSAGE);
//			              return;
//				}else{
//					
//					modelo_asignaciones.removeRow(fila);
//						txtTotalVaucher.setText( "" );
//						txtTotalRetiros.setText("");
//						
//						while(tabla_vauchers.getRowCount()>0){
//								modelo_vauchers.removeRow(0);
//						}
//						
//						while(tabla_retiro_de_clientes.getRowCount()>0){
//							modelo_retiro_de_clientes.removeRow(0);
//						}
//						
//						if(borrar_lista_de_asignaciones()){
//							cargar_lista_de_asignaciones();
//				    		obtener_totales_de_tAire_rLuz_por_folio_de_corte();
//
//				    		calculoDinamico();
//				    			
////			    			procedimiento para llenar  (tabla_de_ventas_por_fecha)  con respecto a las asignaciones seleccionadas
//			    			llenar_venta_de_asignaciones_por_fecha();
//						}
//				}
//				
//				if(tabla_asignaciones.getRowCount()==0){
//						btnDeposito.setEnabled(false);
//						btnEfectivo.setEnabled(false);
//						btnCheques.setEnabled(false);
//						btnFS.setEnabled(false);
//				}
//				
//		}
//	};
	
	ActionListener opQuitarVauchers = new ActionListener(){
		public void actionPerformed(ActionEvent e){
				int fila = tabla_vauchers.getSelectedRow();
				
				if(fila<0){
		   			 JOptionPane.showMessageDialog(null, "Debe Seleccionar El Folio Del Ticket Que Desea Quitar","Aviso",JOptionPane.INFORMATION_MESSAGE);
		              return;
				}else{
					double cantidad =  Double.valueOf(tabla_vauchers.getValueAt(fila, 9).toString().trim());
					double retiroClient =  Double.valueOf(tabla_vauchers.getValueAt(fila, 11).toString().trim());
					
					txtTotalVaucher.setText( formato.format((Double.valueOf(txtTotalVaucher.getText()) - cantidad))+"" );
					txtTotalRetiros.setText( formato.format((Double.valueOf(txtTotalRetiros.getText()) - retiroClient))+"" );
					
					modelo_vauchers.removeRow(fila);
					
					while(tabla_retiro_de_clientes.getRowCount()>0){
						modelo_retiro_de_clientes.removeRow(0);
					}
					
					String[] retiro = new String[2];
					for(int i=0; i <= tabla_vauchers.getRowCount()-1; i++){
						if(Float.valueOf(tabla_vauchers.getValueAt(i, 11).toString().trim())>0){
							retiro[0] = modelo_vauchers.getValueAt(i, 0).toString().trim();
							retiro[1] = modelo_vauchers.getValueAt(i, 11).toString().trim();
							modelo_retiro_de_clientes.addRow(retiro);
						}
					}
					
					calculoDinamico();
				}
		}
	};
	
 	public Object[][] getTablaRetiroClientes(String cadena_de_vouchers_para_retiro_clientes){
 		String query ="select ticket,importe " +
 						" from liquidaciones_tickets with (nolock) " +
 						" where folio_asignacion in ("+cadena_de_vouchers_para_retiro_clientes+") " +
 						" and folio_documento = 'RetiroCte'";
		Statement s;
		ResultSet rs;
		Object[][] MatrizFiltro = new Object[getFilas_IZAGAR(query)][2];
		try {
				s = new Connexion().conexion_IZAGAR().createStatement();
				rs = s.executeQuery(query);
				int i=0;
				while(rs.next()){
					
					MatrizFiltro[i][0] = "   "+rs.getString(1).trim();
					MatrizFiltro[i][1] = "   "+rs.getString(2).trim();
					
					i++;
				}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	    return MatrizFiltro; 
 	}
	
	ActionListener salir = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			
//			si es igual a 1 es por que ya se guardo el corte  y no se borrara nada al salir
			if(bandera_de_guardado!=1){
				borrar_lista_de_asignaciones();
				bandera_de_guardado = 0;
			}
			dispose();
			
		}
	};
	
	ActionListener filtro = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			dispose();
			new Cat_Cortes_De_Cajeros(lblEstablecimineto.getText().trim()).setVisible(true);
			bandera_de_guardado = 0;
		}
	};	
	
	public void panelEnabledFalse(){	
		txtCorteSistema.setEditable(false);
	}

		
	KeyListener validaNumericoConPunto = new KeyListener() {
		public void keyTyped(KeyEvent e) {
			char caracter = e.getKeyChar();
		    if(((caracter < '0') ||	
		    	(caracter > '9')) && 
		    	(caracter != '.' )){
		    	e.consume();
		    	} 
		    	
		   if (caracter==KeyEvent.VK_PERIOD){
		    	String texto = txtCorteSistema.getText().toString();
				if (texto.indexOf(".")>-1) e.consume();
			}
		}
		public void keyPressed(KeyEvent e){}
		public void keyReleased(KeyEvent e){}
	};
	
	KeyListener validaNumericoConPunto2 = new KeyListener() {
		@Override
		public void keyTyped(KeyEvent e) {
			char caracter = e.getKeyChar();
		    if(((caracter < '0') ||	
		    	(caracter > '9')) && 
		    	(caracter != '.' )){
		    	e.consume();
		    	}
		   if (caracter==KeyEvent.VK_PERIOD){
		    	String texto = txtDeposito.getText().toString();
				if (texto.indexOf(".")>-1) e.consume();
			}
		}
		public void keyPressed(KeyEvent e){}
		public void keyReleased(KeyEvent e){}
								
	};
	
	private String validaCampos(){
		String error="";
		if(txtCorteSistema.getText().equals(""))error+= "Corte del Sistema\n";
		if(txtDeposito.getText().equals(""))error+= "Deposito\n";
		if(txtEfectivo.getText().equals(""))error+= "Efectivo\n";
		return error;
	}

	public int getFilas_IZAGAR(String qry){
		int filas=0;
		try {
			Statement s = con.conexion_IZAGAR().createStatement();
			ResultSet rs = s.executeQuery(qry);
			while(rs.next()){
				filas++;
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return filas;
	}

	public class Cat_Alimentacion_De_Efectivo extends Cat_Efectivo{
		
		public Cat_Alimentacion_De_Efectivo(){
			this.lblEmpleado.setText(lblNombre_Completo.getText());
			tabla_model_efectivo.setRowCount(0);
        
        Object [][] lista_tabla = new Obj_Alimentacion_Por_Denominacion().get_tabla_model_modificar(lblFolio_Corte.getText());
        String[] fila = new String[5];
                for(int i=0; i<lista_tabla.length; i++){
                        fila[0] = lista_tabla[i][0]+"";
                        fila[1] = lista_tabla[i][1]+"";
                        fila[2] = lista_tabla[i][2]+"";
                        fila[3] = lista_tabla[i][3]+"";
                        fila[4] = lista_tabla[i][4]+"";
                        tabla_model_efectivo.addRow(fila);
                }
			
            this.tabla_efectivo.addKeyListener(op_key);
			this.btn_guardar.addActionListener(op_guardar);
			this.addWindowListener(op_limpiar);
			
			filaEfec=0;
			
			 agregar(tabla_efectivo);
			
				//  guardar al presionar la tecla f2
			    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
			       KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0), "foco");
			    
			    getRootPane().getActionMap().put("foco", new AbstractAction(){
				        public void actionPerformed(ActionEvent e)
				        {
				        	btn_guardar.doClick();    	
				        }
			    });
		}
		
		private void agregar(final JTable tbl) {
	        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
		        public void mouseClicked(MouseEvent e) {
		        	filaEfec= tbl.getSelectedRow();
//		        	filaEfecMod= tbl.getSelectedRow();
		        }
	        });
	    }
		
		WindowListener op_limpiar = new WindowListener() {
			public void windowOpened(WindowEvent e) {}
			public void windowIconified(WindowEvent e) {}
			public void windowDeiconified(WindowEvent e) {}
			public void windowDeactivated(WindowEvent e) {}
			public void windowClosing(WindowEvent e) {
				if(!new Obj_Alimentacion_Cortes().buscar_folio_corte(lblFolio_Corte.getText())){
					txtEfectivo.setText("");
				}
			}
			public void windowClosed(WindowEvent e) {}
			public void windowActivated(WindowEvent e) {}
		};
		
		public boolean CalcularImporte(){
			boolean valor=false;
				if(Validar(filaEfec,columnaEfect)){
					
						int cantidadDeFilas = tabla_efectivo.getRowCount();
						filaEfec+=1;
							if(filaEfec == cantidadDeFilas){	filaEfec=0;		}
					
							tabla_efectivo.editCellAt(filaEfec, columnaEfect);
							Component aComp=tabla_efectivo.getEditorComponent();
							aComp.requestFocus();
							
						float suma = 0;
						for(int i=0; i<tabla_efectivo.getRowCount(); i++){
							
								if(tabla_model_efectivo.getValueAt(i,4).toString().equals("")){
										suma = suma + 0;
								}else{
									
										if(isNumeric(tabla_model_efectivo.getValueAt(i,4).toString().trim())){
					    						suma += Float.parseFloat(tabla_model_efectivo.getValueAt(i,4).toString())*(Float.parseFloat(tabla_model_efectivo.getValueAt(i,2).toString())*Float.parseFloat(tabla_model_efectivo.getValueAt(i,3).toString()));
										}else{
												JOptionPane.showMessageDialog(null, "La nomina en el establecimiento "+tabla_model_efectivo.getValueAt(i,0).toString()+"  están mal en su formato:\n","Error",JOptionPane.ERROR_MESSAGE);
												tabla_model_efectivo.setValueAt("", i, 4);
										}
								}
						}
						txtTotal.setText(suma+"");
						txtEfectivo.setText(suma+"");
		//				lblDiferenciaCorte.setText((Float.parseFloat(txtCorteSistema.getText())-(suma+Float.parseFloat(txtDeposito.getText())))+"");
						valor = true;
				}
			return valor;
		}
		KeyListener op_key = new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {
				if(CalcularImporte()==false){
						JOptionPane.showMessageDialog(null, "Se introdujo un valor no valido","Aviso",JOptionPane.INFORMATION_MESSAGE);
						tabla_efectivo.setValueAt(0, filaEfec, columnaEfect);
						return;
			}
			}
			public void keyPressed(KeyEvent e) {}
		};
		
		@SuppressWarnings("unused")
		private boolean Validar(int fila, int columna) { 
			String valor=""; 
			
				if(tabla_efectivo.getValueAt(fila,columna)==null) { 
					return false; 
				}else{ 
					
					try{
							return true;
					}catch(NumberFormatException e){
							return false;
					}
				} 
		}
		
		ActionListener op_guardar = new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				
				if(tabla_efectivo.isEditing()){
					tabla_efectivo.getCellEditor().stopCellEditing();
				}
				
				if(valida_tabla() != ""){
					txtTotal.setText("0.0");
					JOptionPane.showMessageDialog(null, "Las siguientes celdas están mal en su formato:\n"+valida_tabla(),"Error",JOptionPane.ERROR_MESSAGE);
					return;
				}else{
					
						if(JOptionPane.showConfirmDialog(null, "¿Desea guardar la lista de Denominaciones?") == 0){
									Obj_Alimentacion_Denominacion Alim_Denom = new Obj_Alimentacion_Denominacion();
									
									Alim_Denom.setFolio_corte(lblFolio_Corte.getText());
									Alim_Denom.setEmpleado(lblEmpleado.getText());
									Alim_Denom.setEstablecimiento(lblEstablecimineto.getText());
									
									boolean existe_folio_corte = new Obj_Alimentacion_Cortes().buscar_folio_corte(lblFolio_Corte.getText());
									
									if(existe_folio_corte){
										
											if(Alim_Denom.actualizar(folio_usuario,tabla_guardar())){
													
													btnCancelar.setEnabled(true);
													btnSalir.setEnabled(false);
													
//		calculo automatico sin dar enter ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
													float suma = 0;
													for(int i=0; i<tabla_efectivo.getRowCount(); i++){
														
															if(tabla_model_efectivo.getValueAt(i,4).toString().equals("")){
																	suma = suma + 0;
															}else{
																
																	if(isNumeric(tabla_model_efectivo.getValueAt(i,4).toString().trim())){
												    						suma += Float.parseFloat(tabla_model_efectivo.getValueAt(i,4).toString())*(Float.parseFloat(tabla_model_efectivo.getValueAt(i,2).toString())*Float.parseFloat(tabla_model_efectivo.getValueAt(i,3).toString()));
																	}else{
																			JOptionPane.showMessageDialog(null, "La nomina en el establecimiento "+tabla_model_efectivo.getValueAt(i,0).toString()+"  están mal en su formato:\n","Error",JOptionPane.ERROR_MESSAGE);
																			tabla_model_efectivo.setValueAt("", i, 4);
																	}
															}
													}
													txtTotal.setText(suma+"");
													txtEfectivo.setText(suma+"");
//		----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
													calculoDinamico();
													dispose();
													
													new Cat_Reporte_De_Efectivo_Cortes(lblFolio_Corte.getText().trim());
//													JOptionPane.showMessageDialog(null, "La tabla Denominaciones se actualizó exitosamente","Aviso",JOptionPane.INFORMATION_MESSAGE);
//													return;
											}else{
												txtTotal.setText("0.0");
												JOptionPane.showMessageDialog(null, "Ocurrió un error al intentar actualizó la tabla","Error",JOptionPane.ERROR_MESSAGE);
												return;
											}
								
									}else{
								
											if(Alim_Denom.guardar(folio_usuario,tabla_guardar())){
												
													btnCancelar.setEnabled(true);
													btnSalir.setEnabled(false);
													
//													calculo automatico sin dar enter ---------------------------------------------------------------------------------------------------------------------------------------------------------------------
													
													float suma = 0;
													for(int i=0; i<tabla_efectivo.getRowCount(); i++){
														
															if(tabla_model_efectivo.getValueAt(i,4).toString().equals("")){
																	suma = suma + 0;
															}else{
																
																	if(isNumeric(tabla_model_efectivo.getValueAt(i,4).toString().trim())){
												    						suma += Float.parseFloat(tabla_model_efectivo.getValueAt(i,4).toString())*(Float.parseFloat(tabla_model_efectivo.getValueAt(i,2).toString())*Float.parseFloat(tabla_model_efectivo.getValueAt(i,3).toString()));
																	}else{
																			JOptionPane.showMessageDialog(null, "La denominacion del efectivo "+tabla_model_efectivo.getValueAt(i,0).toString()+"  están mal en su formato:\n","Error",JOptionPane.ERROR_MESSAGE);
																			tabla_model_efectivo.setValueAt("", i, 4);
																	}
															}
													}
													txtTotal.setText(suma+"");
													txtEfectivo.setText(suma+"");
//		----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
													calculoDinamico();
													dispose();
													
													new Cat_Reporte_De_Efectivo_Cortes(lblFolio_Corte.getText().trim());
//													JOptionPane.showMessageDialog(null, "La tabla Denominaciones se guardó exitosamente","Aviso",JOptionPane.INFORMATION_MESSAGE);
//													return;
											}else{
													txtTotal.setText("0.0");
													JOptionPane.showMessageDialog(null, "Ocurrió un error al intentar guardar la tabla","Error",JOptionPane.ERROR_MESSAGE);
													return;
											}
									}
						}else{
							return;
						}
				}
			}
		};
		
		private Object[][] tabla_guardar(){
			Object[][] matriz = new Object[tabla_efectivo.getRowCount()][5];
			for(int i=0; i<tabla_efectivo.getRowCount(); i++){
				
				matriz[i][0] = tabla_model_efectivo.getValueAt(i,0)+"";
				matriz[i][1] = tabla_model_efectivo.getValueAt(i, 1)+"";
				matriz[i][2] = tabla_model_efectivo.getValueAt(i, 2)+"";
				matriz[i][3] = Double.valueOf(tabla_model_efectivo.getValueAt(i, 3)+"");
				
				if(tabla_model_efectivo.getValueAt(i,4).toString().trim().length() == 0){
					matriz[i][4] = Float.parseFloat("0"); 
				}else{
					matriz[i][4] = Float.parseFloat(tabla_model_efectivo.getValueAt(i,4)+"");
				}
			}
			return matriz;
		}
		
		private String valida_tabla(){
			String error = "";
			for(int i=0; i<tabla_efectivo.getRowCount(); i++){
				try{
					if(!isNumeric(tabla_model_efectivo.getValueAt(i,4).toString())){
						error += "   La celda de la columna Cantidad no es un número en el [Folio: "+tabla_model_efectivo.getValueAt(i,0)+"]\t\n";
						tabla_model_efectivo.setValueAt("",i, 4);
					}
				} catch(Exception e){
					JOptionPane.showMessageDialog(null, "La tabla tiene una celda con texto en lugar de un valor numérico: \n"+e,"Error",JOptionPane.ERROR_MESSAGE);
				}
			}
			return error;
		}
		
	    private boolean isNumeric(String cadena){
	    	try {
	    		if(cadena.equals("")){
	        		return true;
	    		}else{
	    			Float.parseFloat(cadena);
	        		return true;
	    		}
	    	} catch (NumberFormatException nfe){
	    		return false;
	    	}
	    }
	}

//tabla captura de cheques
	public class Cat_Captura_Totatales_De_Cheques extends Cat_Alimentacion_De_Cheques{
		
	public Cat_Captura_Totatales_De_Cheques(){
		tabla_model_cheques.setRowCount(0);
      Object [][] lista_tabla = new BuscarTablasModel().tabla_model_alimentacion_cheques(lblFolio_Corte.getText().toUpperCase().trim());	
      String[] fila = new String[2];
	  for(int i =0; i<lista_tabla.length; i++){																								
			fila[0]=lista_tabla[i][0].toString();																								
			fila[1]=lista_tabla[i][1].toString();																								
			tabla_model_cheques.addRow(fila);																									
		}																																		

		for(int j =lista_tabla.length; j<50; j++){																								
			fila[0]=j+1+"";																														
			fila[1]="";																															
			tabla_model_cheques.addRow(fila);																									
		};																																		

	
        this.tabla_cheques.addKeyListener(op_key);
		this.btn_guardar.addActionListener(op_guardar_cheques);
		this.addWindowListener(op_limpiar);
		
		
		//  guardar al presionar la tecla f2
	    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
	       KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0), "foco");
	    
	    getRootPane().getActionMap().put("foco", new AbstractAction(){
		        public void actionPerformed(ActionEvent e)
		        {
		        	btn_guardar.doClick();    	
		        }
	    });
	    
	}
	WindowListener op_limpiar = new WindowListener() {
		public void windowOpened(WindowEvent e) {}
		public void windowIconified(WindowEvent e) {}
		public void windowDeiconified(WindowEvent e) {}
		public void windowDeactivated(WindowEvent e) {}
		public void windowClosing(WindowEvent e) {
			if(!new Obj_Alimentacion_Cortes().buscar_folio_corte_cheques(lblFolio_Corte.getText())){
				txtCheques.setText("");
			}
		}
		public void windowClosed(WindowEvent e) {}
		public void windowActivated(WindowEvent e) {}
	};
	
	public boolean CalcularImporte(){
		boolean valor=false;
		if(Validar(filaCheque,columnaCheque)){
			
			int cantidadDeFilas = tabla_cheques.getRowCount();
			filaCheque+=1;
				if(filaCheque == cantidadDeFilas){	filaCheque=0;		}
		
				tabla_cheques.editCellAt(filaCheque, columnaCheque);
				Component aComp=tabla_cheques.getEditorComponent();
				aComp.requestFocus();
				
			float suma = 0;
			for(int i=0; i<tabla_cheques.getRowCount(); i++){
				
					if(tabla_model_cheques.getValueAt(i,1).toString().equals("")){
							suma = suma + 0;
					}else{
						
							if(isNumeric(tabla_model_cheques.getValueAt(i,1).toString().trim())){
		    						suma += Float.parseFloat(tabla_model_cheques.getValueAt(i,1).toString());
							}else{
									JOptionPane.showMessageDialog(null, "La tabla de cheques  "+tabla_model_cheques.getValueAt(i,0).toString()+"  están mal en su formato:\n","Error",JOptionPane.ERROR_MESSAGE);
									tabla_model_cheques.setValueAt("", i, 1);
							}
					}
			}
			txtTotal.setText(suma+"");
			txtCheques.setText(suma+"");
//			lblDiferenciaCorte.setText((Float.parseFloat(txtCorteSistema.getText())-(suma+Float.parseFloat(txtDeposito.getText())))+"");
			valor = true;
		}
		return valor;
	}
	KeyListener op_key = new KeyListener() {
		public void keyTyped(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {
				if(CalcularImporte()==false){
					JOptionPane.showMessageDialog(null, "Se introdujo un valor no valido","Aviso",JOptionPane.INFORMATION_MESSAGE);
					tabla_cheques.setValueAt(0, filaEfec, columnaEfect);
					return;
				}
			}
		public void keyPressed(KeyEvent e) {}
	};
	
	@SuppressWarnings("unused")
	private boolean Validar(int fila, int columna) { 
		String valor=""; 
		
			if(tabla_cheques.getValueAt(fila,columna)==null) { 
					return false; 
			}else{ 
				
					try{
							return true;
					}catch(NumberFormatException e){
							return false;
					}
			} 
	}
	
	ActionListener op_guardar_cheques = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			
			if(tabla_cheques.isEditing()){
				tabla_cheques.getCellEditor().stopCellEditing();
			}
			
			if(valida_tabla() != ""){
				txtTotal.setText("0.0");
				JOptionPane.showMessageDialog(null, "Las siguientes celdas están mal en su formato:\n"+valida_tabla(),"Error",JOptionPane.ERROR_MESSAGE);
				return;
			}else{
				
					if(JOptionPane.showConfirmDialog(null, "¿Desea guardar la lista de totales de cheques?") == 0){
						
							Obj_Alimentacion_De_Cheques Alim_cheqes = new Obj_Alimentacion_De_Cheques();
							
							Alim_cheqes.setFolio_corte(lblFolio_Corte.getText());
							Alim_cheqes.setFolio_empleado(Integer.valueOf(lblFolio_Empleado.getText()));
							
							boolean existe_folio_corte = new Obj_Alimentacion_Cortes().buscar_folio_corte_cheques(lblFolio_Corte.getText());
							
							String basedatos="2.26";
							String vista_previa_reporte="no";
							int vista_previa_de_ventana=0;
							String comando= "exec sp_select_reporte_cheques_de_cortes '"+lblFolio_Corte.getText().trim()+"';";
							String reporte = "Obj_Reporte_De_Cheques_Para_Cortes.jrxml";
							
							if(existe_folio_corte){
										if(Alim_cheqes.ActualizarTotalesDeCheques(folio_usuario, tabla_guardar())){
												
												btnCancelar.setEnabled(true);
												btnSalir.setEnabled(false);
												
												float suma = 0;
												for(int i=0; i<tabla_cheques.getRowCount(); i++){
													
														if(tabla_model_cheques.getValueAt(i,1).toString().equals("")){
																suma = suma + 0;
														}else{
															
																if(isNumeric(tabla_model_cheques.getValueAt(i,1).toString().trim())){
											    						suma += Float.parseFloat(tabla_model_cheques.getValueAt(i,1).toString());
																}else{
																		JOptionPane.showMessageDialog(null, "Es Incorrecto El formato Numerico "+tabla_model_cheques.getValueAt(i,0).toString()+"  ","Aviso",JOptionPane.ERROR_MESSAGE);
																		tabla_model_cheques.setValueAt("", i, 1);
																}
														}
												}
												txtTotal.setText(suma+"");
												txtCheques.setText(suma+"");
//	 -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
												calculoDinamico();
												dispose();
												
												new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
										}else{
												txtTotal.setText("0.0");
												JOptionPane.showMessageDialog(null, "Ocurrió un error al intentar actualizó la tabla","Error",JOptionPane.ERROR_MESSAGE);
												return;
										}
								
							}else{
										if(Alim_cheqes.GuardarTotalesDeCheques(folio_usuario,tabla_guardar())){
											
												btnCancelar.setEnabled(true);
												btnSalir.setEnabled(false);
												
//	calculo automatico de total sin dar enter ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
												float suma = 0;
												for(int i=0; i<tabla_cheques.getRowCount(); i++){
													
														if(tabla_model_cheques.getValueAt(i,1).toString().equals("")){
																suma = suma + 0;
														}else{
															
																if(isNumeric(tabla_model_cheques.getValueAt(i,1).toString().trim())){
											    						suma += Float.parseFloat(tabla_model_cheques.getValueAt(i,1).toString());
																}else{
																		JOptionPane.showMessageDialog(null,"Es Incorrecto El formato Numerico "+tabla_model_cheques.getValueAt(i,0).toString()+"  están mal en su formato:\n","Aviso",JOptionPane.ERROR_MESSAGE);
																		tabla_model_cheques.setValueAt("", i, 1);
																}
														}
												}
												txtTotal.setText(suma+"");
												txtCheques.setText(suma+"");
//	 -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
												calculoDinamico();
												dispose();
											    new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
										}else{
												txtTotal.setText("0.0");
												JOptionPane.showMessageDialog(null, "Ocurrió un error al intentar guardar la tabla","Error",JOptionPane.ERROR_MESSAGE);
												return;
										}
							}

					}else{
							return;
					}
			}
		}
	};
	
	private Object[] tabla_guardar(){
		
		Object[] matriz = new Object[tabla_cheques.getRowCount()];
		
		for(int i=0; i<tabla_cheques.getRowCount(); i++){
			
				if(tabla_model_cheques.getValueAt(i,1).toString().trim().length() == 0){
						matriz[i] = Float.parseFloat("0"); 
				}else{
						matriz[i] = Float.parseFloat(tabla_model_cheques.getValueAt(i,1)+"");
				}
		}
		return matriz;
	}
	
	private String valida_tabla(){
		
		String error = "";
		for(int i=0; i<tabla_cheques.getRowCount(); i++){
				try{
					if(!isNumeric(tabla_model_cheques.getValueAt(i,1).toString())){
							error += "   La celda de la columna Cantidad no es un número en el [Folio: "+tabla_model_cheques.getValueAt(i,0)+"]\t\n";
							tabla_model_cheques.setValueAt("",i, 1);
					}
				} catch(Exception e){
						JOptionPane.showMessageDialog(null, "La tabla tiene una celda con texto en lugar de un valor numérico: \n"+e,"Error",JOptionPane.ERROR_MESSAGE);
				}
		}
		return error;
	}
	
    private boolean isNumeric(String cadena){
    	try {
    		if(cadena.equals("")){
        		return true;
    		}else{
    			Float.parseFloat(cadena);
        		return true;
    		}
    	} catch (NumberFormatException nfe){
    		return false;
    	}
    }
}
	
///TODO Ventana Deposito	
	JTextField txtFaltanteDeDeposito = new JTextField();
	public class Cat_Alimentacion_Deposito extends JDialog {
		Container cont = getContentPane();
		JLayeredPane panel = new JLayeredPane();
		
		public JToolBar menu_toolbar = new JToolBar();
		JButton btn_guardar=  new JCButton("","Guardar.png","Azul");
		
		JLabel lblEmpleado = new JLabel();
		JTextField txtTotal = new JTextField();
		
		String columnNames[] = { "Moneda", "Cantidad"};
		
		String dataValues[][] ={{ "0.10", ""},
								{ "0.20", ""},
								{ "0.50", ""},
								{ "1.00", ""},
								{ "2.00", ""},
								{ "5.00", ""},
								{ "10.00", ""},
								{ "20.00", ""},
								{ "MONEDAS", ""},
								{ "EFECTIVO EN CAJA", ""}
							};
		
		DefaultTableModel tabla_model_depocitos = new DefaultTableModel(dataValues, columnNames) {
		     @SuppressWarnings("rawtypes")
			Class[] types = new Class[]{
		    	java.lang.Object.class,
		    	java.lang.Object.class
	         };
		     
		     @SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int columnIndex) {
	             return types[columnIndex];
	         }
	         public boolean isCellEditable(int fila, int columna){
	        	 switch(columna){
	        	 	case 0 : return false; 
	        	 	case 1 : 
	        	 		return true; 
	        	 } 				
	 			return false;
	 		}
		};
		
		JTable tabla_depositos = new JTable(tabla_model_depocitos);
		JScrollPane scroll_tabla_depositos = new JScrollPane(tabla_depositos);
//TODO  MENU DEPOSITO		
		public Cat_Alimentacion_Deposito(){
//			cont.setBackground(new Color(86,161,85));
			txtFaltanteDeDeposito.setText("2000");
			Constructor();
			this.lblEmpleado.setText(lblNombre_Completo.getText());
			
            this.addWindowListener(new WindowAdapter() {
                    public void windowOpened( WindowEvent e ){
                    	tabla_depositos.setEnabled(true);
            			tabla_depositos.editCellAt(filaDep, columnaDep);
            			Component aComp=tabla_depositos.getEditorComponent();
            			aComp.requestFocus();
                 }
            });
		}
		
		public void Constructor(){
			this.setModal(true);
			this.setTitle("Alimentación de Deposito");
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/captura_nomina_icon&16.png"));
			
			lblEmpleado.setForeground(Color.GRAY);
			
			this.panel.add(menu_toolbar).setBounds(0,0,200,25);
			this.panel.add(lblEmpleado).setBounds(30,35,350,20);
			
			this.panel.add(scroll_tabla_depositos).setBounds(20,60,400,420);
			
			this.panel.add(new JLabel("Deposito Faltante:")).setBounds(220,5,100,20);
			this.panel.add(txtFaltanteDeDeposito).setBounds(330,5,90,20);
			
			this.panel.add(new JLabel("Total de Cantidades:")).setBounds(220,485,100,20);
			this.panel.add(txtTotal).setBounds(330,485,90,20);
			
			this.menu_toolbar.add(btn_guardar);
			this.menu_toolbar.setEnabled(true);
			this.txtTotal.setEditable(false);
			txtFaltanteDeDeposito.setEditable(false);
			
			this.init_tabla();
			
			this.cont.add(panel);
			this.btn_guardar.addActionListener(op_guardar);
			
			this.tabla_depositos.addKeyListener(op_key);
			this.addWindowListener(op_limpiar);
			
			
			//  guardar al presionar la tecla f2
		    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
		       KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0), "foco");
		    
		    getRootPane().getActionMap().put("foco", new AbstractAction(){
			        public void actionPerformed(ActionEvent e)
			        {
			        	btn_guardar.doClick();    	
			        }
		    });
		    
		    filaDep=0;
		    agregar(tabla_depositos);
			
			this.setSize(450,550);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		}
		
		private void agregar(final JTable tbl) {
	        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
		        public void mouseClicked(MouseEvent e) {
		        	filaDep= tbl.getSelectedRow();
		        }
	        });
	    }
		
		WindowListener op_limpiar = new WindowListener() {
			public void windowOpened(WindowEvent e) {}
			public void windowIconified(WindowEvent e) {}
			public void windowDeiconified(WindowEvent e) {}
			public void windowDeactivated(WindowEvent e) {}
			public void windowClosing(WindowEvent e) {
					txtDeposito.setText("");
			}
			public void windowClosed(WindowEvent e) {}
			public void windowActivated(WindowEvent e) {}
		};
		
		
		
		KeyListener op_key = new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {
				if(CalcularImporte()==false){
						JOptionPane.showMessageDialog(null, "Se introdujo un valor no valido","Aviso",JOptionPane.INFORMATION_MESSAGE);
						tabla_depositos.setValueAt(0, filaDep, columnaDep);
						return;
			}
				
			}
			public void keyPressed(KeyEvent e) {}
		};
		
		public boolean CalcularImporte(){
			boolean valor=false;
			if(Validar(filaDep,columnaDep)){
				
				int cantidadDeFilas = tabla_depositos.getRowCount();
				filaDep+=1;

					if(filaDep == cantidadDeFilas){	filaDep=0;		}
					
                	tabla_depositos.setEnabled(true);
        			tabla_depositos.editCellAt(filaDep, columnaDep);
        			Component aComp=tabla_depositos.getEditorComponent();
        			aComp.requestFocus();
        			
					float suma = 0;
					for(int i=0; i<tabla_depositos.getRowCount(); i++){
						
							if(tabla_model_depocitos.getValueAt(i,1).toString().equals("")){
									suma = suma + 0;
							}else{
									if(tabla_model_depocitos.getValueAt(i,0).toString().equals("EFECTIVO EN CAJA") || tabla_model_depocitos.getValueAt(i,0).toString().equals("MONEDAS")){
			    							suma += Float.parseFloat(tabla_model_depocitos.getValueAt(i,1).toString());
			    					}else{
				    						if(isNumeric(tabla_model_depocitos.getValueAt(i,1).toString().trim())){
						    						suma += Float.parseFloat(tabla_model_depocitos.getValueAt(i,0).toString())*Float.parseFloat(tabla_model_depocitos.getValueAt(i,1).toString());
											}else{
													JOptionPane.showMessageDialog(null, "La cantidad en la Moneda "+tabla_model_depocitos.getValueAt(i,0).toString()+"  están mal en su formato:\n","Error",JOptionPane.ERROR_MESSAGE);
													tabla_model_depocitos.setValueAt("", i, 1);
											}
			    					}
							}
					}
					txtTotal.setText(suma+"");
					txtDeposito.setText(suma+"");
					txtFaltanteDeDeposito.setText( formato.format(2000-suma)+"" );
					
					valor = true;
			}
			return valor;
		}
		
		@SuppressWarnings("unused")
		private boolean Validar(int fila, int columna) { 
			String valor=""; 
			
				if(tabla_depositos.getValueAt(fila,columna)==null) { 
					return false; 
				}else{ 
					try{
						return true;
					}catch(NumberFormatException e){
					 return false;
					}
				} 
		}
		
		ActionListener op_guardar = new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				
				if(tabla_depositos.isEditing()){
					tabla_depositos.getCellEditor().stopCellEditing();
					tabla_depositos.addKeyListener(op_key);
				}
				
				if(valida_tabla() != ""){
					txtTotal.setText("0.0");
					JOptionPane.showMessageDialog(null, "Se a ingresado un valor no numerico en la tabla","Error",JOptionPane.ERROR_MESSAGE);
					return;
				}else{
						
						if(JOptionPane.showConfirmDialog(null, "¿Desea guardar la lista de Denominaciones?") == 0){
							Obj_Alimentacion_Denominacion Alim_Denom = new Obj_Alimentacion_Denominacion();
							
							Alim_Denom.setEmpleado(lblEmpleado.getText());
							Alim_Denom.setEstablecimiento(lblFolio_Corte.getText());

							for(int i=0; i<tabla_guardar().length; i++){
								
							}
							if(Alim_Denom.guardar_deposito(folio_usuario,tabla_guardar())){
								
								txtDeposito.setText(txtTotal.getText());
								
								btnCancelar.setEnabled(true);
								btnSalir.setEnabled(false);
								CalcularImporte();
								calculoDinamico();
								dispose();
								
								new Cat_Reporte_De_Depositos_Cortes(lblFolio_Corte.getText().trim());
							}else{
								txtTotal.setText("0.0");
								JOptionPane.showMessageDialog(null, "Ocurrió un error al intentar modificar la tabla","Error",JOptionPane.ERROR_MESSAGE);
								return;
							}
						}else{
							return;
						}
				}
			}
		};
		
		private Object[][] tabla_guardar(){
			Object[][] matriz = new Object[tabla_depositos.getRowCount()][2];
			for(int i=0; i<tabla_depositos.getRowCount(); i++){
				
				matriz[i][0] = tabla_model_depocitos.getValueAt(i,0).toString().trim();
				
				if(tabla_model_depocitos.getValueAt(i,1).toString().trim().length() == 0){
					matriz[i][1] = Float.parseFloat("0"); 
				}else{
					matriz[i][1] = Float.parseFloat(tabla_model_depocitos.getValueAt(i,1).toString().trim());
				}
			}
			return matriz;
		}
		
		private String valida_tabla(){
			String error = "";
			for(int i=0; i<tabla_depositos.getRowCount(); i++){
				try{
					if(!isNumeric(tabla_model_depocitos.getValueAt(i,1).toString())){
						error += "   La celda de la columna Cantidad no es un número en el [Folio: "+tabla_model_depocitos.getValueAt(i,0)+"]\t\n";
						tabla_model_depocitos.setValueAt("",i, 1);
					}
				} catch(Exception e){
					JOptionPane.showMessageDialog(null, "La tabla tiene una celda con texto en lugar de un valor numérico: \n"+e,"Error",JOptionPane.ERROR_MESSAGE);
				}
			}
			return error;
		}
		
		public void init_tabla(){
			this.tabla_depositos.getTableHeader().setReorderingAllowed(false) ;
			
	    	this.tabla_depositos.getColumnModel().getColumn(0).setMaxWidth(120);
	    	this.tabla_depositos.getColumnModel().getColumn(0).setMinWidth(120);		
	    	this.tabla_depositos.getColumnModel().getColumn(1).setMaxWidth(290);
	    	this.tabla_depositos.getColumnModel().getColumn(1).setMinWidth(290);
	    	
	    	tablaRender(tabla_depositos);
			
			float suma = 0;
			for(int i=0; i<tabla_depositos.getRowCount(); i++){
				if(tabla_model_depocitos.getValueAt(i,1).toString().length() == 0){
					suma = suma + 0;
				}else{
					if(tabla_model_depocitos.getValueAt(i,0).toString().equals("EFECTIVO EN CAJA")){
						suma += Float.parseFloat(tabla_model_depocitos.getValueAt(i,1).toString());
					}else{
						if(isNumeric(tabla_model_depocitos.getValueAt(i,1).toString().trim())){
	    					suma += Float.parseFloat(tabla_model_depocitos.getValueAt(i,0).toString())*Float.parseFloat(tabla_model_depocitos.getValueAt(i,1).toString());
						}else{
							JOptionPane.showMessageDialog(null, "La cantidad en la Moneda "+tabla_model_depocitos.getValueAt(i,0).toString()+"  están mal en su formato:\n","Error",JOptionPane.ERROR_MESSAGE);
							tabla_model_depocitos.setValueAt("", i, 1);
						}
					}				}
			}
			txtTotal.setText(suma+"");
	    }
		
	    private boolean isNumeric(String cadena){
	    	try {
	    		if(cadena.equals("")){
	        		return true;
	    		}else{
	    			Float.parseFloat(cadena);
	        		return true;
	    		}
	    	} catch (NumberFormatException nfe){
	    		return false;
	    	}
	    }

	}
	
//  ---------------------------------------------------------------------------------------------------------------------------------------------------------	
	//TODO modificar deposito
	public class Cat_Alimentacion_Deposito_Modificar extends JDialog {
		
		Container cont = getContentPane();
		JLayeredPane panel = new JLayeredPane();
		
		public JToolBar menu_toolbar = new JToolBar();
		JButton btn_modificar= new JCButton("","Guardar.png","Azul");
		JLabel lblEmpleado = new JLabel();
		JTextField txtTotal = new JTextField();
		
		String columnNames[] = { "Moneda", "Cantidad"};
		
		public Object[][] get_tabla_modificar(String folio_corte){
			return new BuscarTablasModel().tabla_model_alimentacion_deposito_modificar(folio_corte);
		}
		
		DefaultTableModel tabla_model = new DefaultTableModel(this.get_tabla_modificar(lblFolio_Corte.getText()), columnNames) {
		     @SuppressWarnings("rawtypes")
			Class[] types = new Class[]{
		    	java.lang.Object.class,
		    	java.lang.Object.class
	         };
		     
		     @SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int columnIndex) {
	             return types[columnIndex];
	         }
	         public boolean isCellEditable(int fila, int columna){
	        	 switch(columna){
	        	 	case 0 : return false; 
	        	 	case 1 : 
	        	 		return true; 
	        	 } 				
	 			return false;
	 		}
		};
		
		JTable tabla_deposito = new JTable(tabla_model);
		JScrollPane scroll_tabla = new JScrollPane(tabla_deposito);
		
		public Cat_Alimentacion_Deposito_Modificar(){
			Constructor();
			
//          asigna el foco al JTextField deseado al arrancar la ventana
            this.addWindowListener(new WindowAdapter() {
                    public void windowOpened( WindowEvent e ){
            			tabla_deposito.setEnabled(true);
        				tabla_deposito.editCellAt(filaDep, columnaDep);
        				Component aComp=tabla_deposito.getEditorComponent();
        				aComp.requestFocus();
                 }
            });
				
			this.lblEmpleado.setText(lblNombre_Completo.getText());
		}
		public void Constructor(){
			this.setSize(450,550);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			this.setModal(true);
			this.setTitle("Alimentación de Deposito");
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("monedas-en-efectivo-en-moneda-icono-4023-16.png"));
			lblEmpleado.setForeground(Color.GRAY);
			
			this.panel.add(menu_toolbar).setBounds(0,0,200,25);
			this.panel.add(lblEmpleado).setBounds(30,35,350,20);
			this.panel.add(scroll_tabla).setBounds(20,60,400,420);
			this.panel.add(new JLabel("Deposito Faltante:")).setBounds(220,5,100,20);
			this.panel.add(txtFaltanteDeDeposito).setBounds(330,5,90,20);
			
			this.panel.add(new JLabel("Total de Cantidades:")).setBounds(220,485,100,20);
			this.panel.add(txtTotal).setBounds(330,485,90,20);
			
			this.menu_toolbar.add(btn_modificar);
			this.menu_toolbar.setEnabled(true);
			this.txtTotal.setEditable(false);
			
			this.init_tabla();
			
			this.cont.add(panel);
			this.btn_modificar.addActionListener(op_modificar);
			
			this.tabla_deposito.addKeyListener(op_key);
			
			txtFaltanteDeDeposito.setEditable(false);
			
			filaDep=0;
			 agregar(tabla_deposito);
			
			//  modificar al presionar la tecla f2
		    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
		       KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0), "foco");
		    
		    getRootPane().getActionMap().put("foco", new AbstractAction(){
			        public void actionPerformed(ActionEvent e)
			        {
			        	btn_modificar.doClick();    	
			        }
		    });
		}
		
		private void agregar(final JTable tbl) {
	        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
		        public void mouseClicked(MouseEvent e) {
		        	filaDep= tbl.getSelectedRow();
		        }
	        });
	    }
		
		KeyListener op_key = new KeyListener() {
			public void keyTyped(KeyEvent e) {
			}
			public void keyReleased(KeyEvent e) {
				
				int cantidadDeFilas = tabla_deposito.getRowCount();
				filaDep+=1;
					if(filaDep == cantidadDeFilas){	filaDep=0;		}
			
					tabla_deposito.editCellAt(filaDep, columnaDep);
					Component aComp=tabla_deposito.getEditorComponent();
					aComp.requestFocus();
					totalDeposito();
			}
			public void keyPressed(KeyEvent e) {
			}
		};
		
		WindowListener op_limpiar = new WindowListener() {
			public void windowOpened(WindowEvent e) {}
			public void windowIconified(WindowEvent e) {}
			public void windowDeiconified(WindowEvent e) {}
			public void windowDeactivated(WindowEvent e) {}
			public void windowClosing(WindowEvent e) {
					txtDeposito.setText("");
			}
			public void windowClosed(WindowEvent e) {}
			public void windowActivated(WindowEvent e) {}
		};
		
		ActionListener op_modificar = new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				
				if(tabla_deposito.isEditing()){
					tabla_deposito.getCellEditor().stopCellEditing();
				}
				
				if(valida_tabla() != ""){
					txtTotal.setText("0.0");
					JOptionPane.showMessageDialog(null, "Las siguientes celdas están mal en su formato:\n"+valida_tabla(),"Error",JOptionPane.ERROR_MESSAGE);
					return;
				}else{
						if(JOptionPane.showConfirmDialog(null, "¿Desea guardar la lista de Denominaciones?") == 0){
							Obj_Alimentacion_Denominacion Alim_Denom = new Obj_Alimentacion_Denominacion();
							Alim_Denom.setEmpleado(lblEmpleado.getText());
							Alim_Denom.setEstablecimiento(lblFolio_Corte.getText());
							if(Alim_Denom.actualizar_deposito(folio_usuario, tabla_guardar())){
								
								txtDeposito.setText(txtTotal.getText());
								btnCancelar.setEnabled(true);
								btnSalir.setEnabled(false);
								
								totalDeposito();
								calculoDinamico();
								dispose();
								
								new Cat_Reporte_De_Depositos_Cortes(lblFolio_Corte.getText().trim());
							}else{
								txtTotal.setText("0.0");
								JOptionPane.showMessageDialog(null, "Ocurrió un error al intentar modificar la tabla","Error",JOptionPane.ERROR_MESSAGE);
								return;
							}
						}else{
							return;
						}
				}
			}
		};
		
		public void totalDeposito(){
			float suma = 0;
			for(int i=0; i<tabla_deposito.getRowCount(); i++){
				
					if(tabla_model.getValueAt(i,1).toString().equals("")){
							suma = suma + 0;
					}else{
						if(tabla_model.getValueAt(i,0).toString().equals("EFECTIVO EN CAJA") || tabla_model.getValueAt(i,0).toString().equals("MONEDAS")){
	    							suma += Float.parseFloat(tabla_model.getValueAt(i,1).toString());
	    					}else{
		    						if(isNumeric(tabla_model.getValueAt(i,1).toString().trim())){
				    						suma += Float.parseFloat(tabla_model.getValueAt(i,0).toString())*Float.parseFloat(tabla_model.getValueAt(i,1).toString());
									}else{
											JOptionPane.showMessageDialog(null, "La cantidad en la Moneda "+tabla_model.getValueAt(i,0).toString()+"  están mal en su formato:\n","Error",JOptionPane.ERROR_MESSAGE);
											tabla_model.setValueAt("", i, 1);
									}
	    					}
					}
			}
			txtTotal.setText(suma+"");
			txtDeposito.setText(suma+"");
			txtFaltanteDeDeposito.setText(2000-suma+"");
		}
		
		private Object[][] tabla_guardar(){
			Object[][] matriz = new Object[tabla_deposito.getRowCount()][2];
			for(int i=0; i<tabla_deposito.getRowCount(); i++){
				
				matriz[i][0] = tabla_model.getValueAt(i,0).toString().trim();
				
				if(tabla_model.getValueAt(i,1).toString().trim().length() == 0){
					matriz[i][1] = Float.parseFloat("0"); 
				}else{
					matriz[i][1] = Float.parseFloat(tabla_model.getValueAt(i,1).toString().trim());
				}
			}
			return matriz;
		}
		
		private String valida_tabla(){
			String error = "";
			for(int i=0; i<tabla_deposito.getRowCount(); i++){
				try{
					if(!isNumeric(tabla_model.getValueAt(i,1).toString())){
						error += "   La celda de la columna Cantidad no es un número en el [Folio: "+tabla_model.getValueAt(i,0)+"]\t\n";
						tabla_model.setValueAt("",i, 1);
					}
				} catch(Exception e){
					JOptionPane.showMessageDialog(null, "La tabla tiene una celda con texto en lugar de un valor numérico: \n"+e,"Error",JOptionPane.ERROR_MESSAGE);
				}
			}
			return error;
		}
		
		public void init_tabla(){
			this.tabla_deposito.getTableHeader().setReorderingAllowed(false) ;
			
	    	this.tabla_deposito.getColumnModel().getColumn(0).setMaxWidth(120);
	    	this.tabla_deposito.getColumnModel().getColumn(0).setMinWidth(120);		
	    	this.tabla_deposito.getColumnModel().getColumn(1).setMaxWidth(290);
	    	this.tabla_deposito.getColumnModel().getColumn(1).setMinWidth(290);
	    	
	    	tablaRender(tabla_deposito);
             			
			float suma = 0;
			for(int i=0; i<tabla_deposito.getRowCount(); i++){
				if(tabla_model.getValueAt(i,1).toString().length() == 0){
					suma = suma + 0;
				}else{
					if(tabla_model.getValueAt(i,0).toString().equals("EFECTIVO EN CAJA") || tabla_model.getValueAt(i,0).toString().equals("MONEDAS")){
						suma += Float.parseFloat(tabla_model.getValueAt(i,1).toString());
					}else{
						if(isNumeric(tabla_model.getValueAt(i,1).toString().trim())){
	    					suma += Float.parseFloat(tabla_model.getValueAt(i,0).toString())*Float.parseFloat(tabla_model.getValueAt(i,1).toString());
						}else{
							JOptionPane.showMessageDialog(null, "La cantidad en la fila "+tabla_model.getValueAt(i,0).toString()+"  están mal en su formato:\n","Error",JOptionPane.ERROR_MESSAGE);
							tabla_model.setValueAt("", i, 1);
						}
					}				
				}
			}
			txtTotal.setText(suma+"");
	    }
		
	    private boolean isNumeric(String cadena){
	    	try {
	    		if(cadena.equals("")){
	        		return true;
	    		}else{
	    			Float.parseFloat(cadena);
	        		return true;
	    		}
	    	} catch (NumberFormatException nfe){
	    		return false;
	    	}
	    }
	}
	
//TODO (LLAMAR AL FILTRO DE ASIGNACIONES)-----------------------------------------------------------------------------------------------------------------
	public class Cat_Filtrar_Asignaciones extends Cat_Filtro_De_Asignacion{
		public Cat_Filtrar_Asignaciones(String cadena/*,String nombre*/){

            this.addWindowListener(new WindowAdapter() {
                public void windowOpened( WindowEvent e ){
                	txtFiltro_asignacion.requestFocus();
             }
        });
            
				 modeloFiltro.setRowCount(0);
			String[] fila = new String[9];
			Object[][] getTablaFiltro = getTablaFiltro(cadena,establecimiento);
            for(int i=0; i<getTablaFiltro.length; i++){
                    fila[0] = getTablaFiltro[i][0]+"";
                    fila[1] = getTablaFiltro[i][1]+"";
                    fila[2] = getTablaFiltro[i][2]+"";
                    fila[3] = getTablaFiltro[i][3]+"";
                    fila[4] = getTablaFiltro[i][4]+"";
                    fila[5] = getTablaFiltro[i][5]+"";
                    fila[6] = getTablaFiltro[i][6]+"";
                    fila[7] = getTablaFiltro[i][7]+"";
                    fila[8] = getTablaFiltro[i][8]+"";
                    modeloFiltro.addRow(fila);
            }
				 
            btnCargar.addActionListener(opCargar);
		}
		

		ActionListener opCargar = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpiar_filtro();
				
				double suma_total =0;
				String[] fila = new String[8];
				
	    			for(int i=0; i<cargar_tabla_asignaciones_de_filtro().length; i++){
	    				
	    				if(cargar_tabla_asignaciones_de_filtro()[i][8].toString().trim()=="true"){
			    				
			    				 fila[0] = cargar_tabla_asignaciones_de_filtro()[i][0].toString().trim();
                                 fila[1] = cargar_tabla_asignaciones_de_filtro()[i][1].toString().trim();
                                 fila[2] = cargar_tabla_asignaciones_de_filtro()[i][2].toString().trim();
                                 fila[3] = cargar_tabla_asignaciones_de_filtro()[i][3].toString().trim();
                                 fila[4] = cargar_tabla_asignaciones_de_filtro()[i][4].toString().trim();
                                 fila[5] = cargar_tabla_asignaciones_de_filtro()[i][5].toString().trim();
                                 fila[6] = cargar_tabla_asignaciones_de_filtro()[i][6].toString().trim();
                                 fila[7] = cargar_tabla_asignaciones_de_filtro()[i][7].toString().trim();
                                 
                                 modelo_asignaciones.addRow(fila);
                                 suma_total += (Double.valueOf(cargar_tabla_asignaciones_de_filtro()[i][3].toString().trim()));
	    				}
	    			}
	    			
	    			modelo_vauchers.setRowCount(0);
	    			txtTotalVaucher.setText("");

	    			if(tabla_retiro_de_clientes.getRowCount()>0){
	    				while(tabla_retiro_de_clientes.getRowCount()>0){
							 modelo_retiro_de_clientes.removeRow(0);
	    				}
	    			}
	    			
	    			if(txtCorteSistema.getText().toString().trim().equals("")){
	    				txtCorteSistema.setText(formato.format(suma_total)+"");
	    			}else{
	    				txtCorteSistema.setText(formato.format(Double.valueOf(txtCorteSistema.getText().toString().trim())+suma_total)+"");
	    			}
	    			
	    			if(tabla_asignaciones.getRowCount()>0){
	    				btnDeposito.setEnabled(true);
	    				btnEfectivo.setEnabled(true);
	    				btnCheques.setEnabled(true);
	    				btnFS.setEnabled(true);
	    				
	    				llenar_venta_de_asignaciones_por_fecha();
	    			}
	    		dispose();
	    		
				if(borrar_lista_de_asignaciones()){
					if(cargar_lista_de_asignaciones()){
		    			obtener_totales_de_tAire_rLuz_por_folio_de_corte();
		    		}
				}
				calculoDinamico();
			}
		};
		
			private Object[][] cargar_tabla_asignaciones_de_filtro(){
				Object[][] matriz = new Object[tablaFiltro.getRowCount()][9];
					for(int i=0; i<tablaFiltro.getRowCount(); i++){
							matriz[i][0] = modeloFiltro.getValueAt(i,0).toString().trim();
							matriz[i][1] = modeloFiltro.getValueAt(i,1).toString().trim();
							matriz[i][2] = modeloFiltro.getValueAt(i,2).toString().trim();
							matriz[i][3] = modeloFiltro.getValueAt(i,3).toString().trim();
							matriz[i][4] = modeloFiltro.getValueAt(i,4).toString().trim();
							matriz[i][5] = modeloFiltro.getValueAt(i,5).toString().trim();
							matriz[i][6] = modeloFiltro.getValueAt(i,6).toString().trim();
							matriz[i][7] = modeloFiltro.getValueAt(i,7).toString().trim();
							matriz[i][8] = modeloFiltro.getValueAt(i,8).toString().trim();
					}
				return matriz;
			}
	} 
////////////////Termina Filtro De Asignaciones
	
//	TODO (CARGA FILTRO PARA SELECCION DE TURNOS--------------------------------------------------------------------------------------------
	public class Cat_Filtrar_Turno extends Cat_Filtro_De_Turno{
		String Establecimientopara=establecimiento;
		public Cat_Filtrar_Turno( ){
			modelo_turnos.setRowCount(0);
			init_tabla_turnos(Establecimientopara);
            btnCargar.addActionListener(opCargar);
            txtCorteSistema.setText("");
		}

		ActionListener opCargar = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
    			txtTotalVaucher.setText("");
    			modelo_vauchers.setRowCount(0);
    			modelo_retiro_de_clientes.setRowCount(0);
    			double suma_total =0;
    			
    			Object[]   vectorturnos = new Object[8];
			    double importe_turno=0;
			    
			    for(int i=0;i<modelo_filtro_turnos.getRowCount();i++) {
			    if(tabla_filtro_turnos.getValueAt(i, 0).toString().equals("true")&&modelo_filtro_turnos.getValueAt(i,5).toString().trim().equals("SIN TERMINAR")){
					JOptionPane.showMessageDialog(null, "El Turno "+modelo_filtro_turnos.getValueAt(i,1).toString().trim()+" Esta Sin Terminar \nEs Requerido Que el Turno Este Terminado Para Poder Realizar el Corte", "Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
			    	return;
			     }
			    }
			    	
				   for(int i=0;i<modelo_filtro_turnos.getRowCount();i++) {
					    	 ObjTab.Obj_Filtro(tabla_filtro_turnos, "", columnaspo,txtCorteSistema);
					    	if(tabla_filtro_turnos.getValueAt(i, 0).toString().equals("true")) {
					    		importe_turno= Double.valueOf(new BuscarSQL().Venta_Del_Turno(modelo_filtro_turnos.getValueAt(i,8).toString().trim(), modelo_filtro_turnos.getValueAt(i,1).toString().trim())) ;//calculo del total
					    		vectorturnos[0] = modelo_filtro_turnos.getValueAt(i,1).toString().trim();
					    		vectorturnos[1] = modelo_filtro_turnos.getValueAt(i,6).toString().trim(); 
					    		vectorturnos[2] = modelo_filtro_turnos.getValueAt(i,2).toString().trim();
					    		vectorturnos[3] = importe_turno+"";
					    		vectorturnos[4] = modelo_filtro_turnos.getValueAt(i,7).toString().trim();
					    		vectorturnos[5] = modelo_filtro_turnos.getValueAt(i,8).toString().trim();
					    		vectorturnos[6] = modelo_filtro_turnos.getValueAt(i,3).toString().trim();
					    		vectorturnos[7] = modelo_filtro_turnos.getValueAt(i,4).toString().trim();
					    		modelo_turnos.addRow(vectorturnos);
					    	    suma_total += (importe_turno);
				   }
				}
					
	    			if(txtCorteSistema.getText().toString().trim().equals("")){
	    				txtCorteSistema.setText(formato.format(suma_total)+"");
	    			}else{
	    				txtCorteSistema.setText(formato.format(Double.valueOf(txtCorteSistema.getText().toString().trim())+suma_total)+"");
	    			}

	    			if(tabla_turnos.getRowCount()>0){
	    				btnDeposito.setEnabled(true);
	    				btnEfectivo.setEnabled(true);
	    				btnCheques.setEnabled(true);
	    				btnFS.setEnabled(true);
	    				llenar_venta_de_asignaciones_por_fecha();
	    			}
	    		dispose();
	    		
				if(borrar_lista_de_asignaciones()){
					if(cargar_lista_de_asignaciones()){
		    			obtener_totales_de_tAire_rLuz_por_folio_de_corte();
		    		}
				}
				calculoDinamico();
			}
		};
	} 
//////////////////////termina filtro de turnos
////////TODO VENTANA DE SELECCION DE VOUCHERS LLAMAR AL FILTRO DE VAUCHERS--------------------------------------------------------------------------------------------------------------------------
	public class Cat_Filtrar_Vauchers extends Cat_Filtro_De_Vauchers{
		int bandera;
		String cadenaAsignacionesTurnosSeleccionadas="";
		String cadenaVouchersSeleccionados="";
		public Cat_Filtrar_Vauchers(String Asignacion_O_Turno){
			bandera = 0;
			txtFolioTicket.setText("");
			ObjTab.Obj_Filtro(tabla_vaucher_filtro, "", columnaspo,txtFolioTicket);
			
			if(Asignacion_O_Turno.equals("A")) {
				for(int i=0; i<tabla_asignaciones.getRowCount(); i++){
					cadenaAsignacionesTurnosSeleccionadas+= "''"+(cargar_tabla_asignaciones()[i][0].toString().trim())+"'',";
			    }
				cadenaAsignacionesTurnosSeleccionadas = cadenaAsignacionesTurnosSeleccionadas.substring(0,cadenaAsignacionesTurnosSeleccionadas.length()-1);
			}
			
			if(Asignacion_O_Turno.equals("T")) {
				for(int i=0; i<tabla_turnos.getRowCount(); i++){
					cadenaAsignacionesTurnosSeleccionadas+= "''"+(cargar_tabla_turnos()[i][0].toString().trim())+"'',";
			    }
				cadenaAsignacionesTurnosSeleccionadas = cadenaAsignacionesTurnosSeleccionadas.substring(0,cadenaAsignacionesTurnosSeleccionadas.length()-1);
			}
			
			if(tabla_vauchers.getRowCount()>0 ) {
				for(int i=0; i<tabla_vauchers.getRowCount(); i++){
					cadenaVouchersSeleccionados+= "''"+(cargar_tabla_voucher_selecionados()[i][0].toString().trim())+"'',";
			    }
				cadenaVouchersSeleccionados = cadenaVouchersSeleccionados.substring(0,cadenaVouchersSeleccionados.length()-1);
			}else {
				cadenaVouchersSeleccionados="''''";
			}
			
			init_tabla_seleccion_de_vouchers(establecimiento,cadenaAsignacionesTurnosSeleccionadas,Asignacion_O_Turno, cadenaVouchersSeleccionados );
			btnCargar.addActionListener(opCargar);
		}
		
		private Object[][] cargar_tabla_turnos(){
			Object[][] matriz = new Object[tabla_turnos.getRowCount()][1];
			for(int i=0; i<tabla_turnos.getRowCount(); i++){
					matriz[i][0] = modelo_turnos.getValueAt(i,0).toString().trim();
			}
		  return matriz;
		}
		
		private Object[][] cargar_tabla_asignaciones(){
			Object[][] matriz = new Object[tabla_asignaciones.getRowCount()][1];
			for(int i=0; i<tabla_asignaciones.getRowCount(); i++){
					matriz[i][0] = modelo_asignaciones.getValueAt(i,0).toString().trim();
			}
		  return matriz;
		}
		
		private Object[][] cargar_tabla_voucher_selecionados(){
			Object[][] matriz = new Object[tabla_vauchers.getRowCount()][1];
			for(int i=0; i<tabla_vauchers.getRowCount(); i++){
					matriz[i][0] = modelo_vauchers.getValueAt(i,0).toString().trim();
			}
		  return matriz;
		}
		
		
		ActionListener opCargar = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bandera+=1;
				txtFolioTicket.setText("");
				ObjTab.Obj_Filtro(tabla_vaucher_filtro, "", columnaspo,txtTotalVaucher);
				double suma_total =0;
				String[] fila = new String[12];
				
				double suma_total_retiros =0;
				String[] retiro = new String[2];
				
				if(bandera==1){
	    			for(int i=0; i<cargar_tabla_vauchers_de_filtro().length; i++){
	    				if(cargar_tabla_vauchers_de_filtro()[i][0].toString().trim().equals("true")){
			    				 fila[0] = cargar_tabla_vauchers_de_filtro()[i][1].toString().trim();
                                 fila[1] = cargar_tabla_vauchers_de_filtro()[i][2].toString().trim();
                                 fila[2] = cargar_tabla_vauchers_de_filtro()[i][3].toString().trim();
                                 fila[3] = cargar_tabla_vauchers_de_filtro()[i][4].toString().trim();
                                 fila[4] = cargar_tabla_vauchers_de_filtro()[i][5].toString().trim();
                                 fila[5] = cargar_tabla_vauchers_de_filtro()[i][6].toString().trim();
                                 fila[6] = cargar_tabla_vauchers_de_filtro()[i][7].toString().trim();
                                 fila[7] = cargar_tabla_vauchers_de_filtro()[i][8].toString().trim();
                                 fila[8] = cargar_tabla_vauchers_de_filtro()[i][9].toString().trim();
                                 fila[9] = cargar_tabla_vauchers_de_filtro()[i][10].toString().trim();
                                 fila[10] = cargar_tabla_vauchers_de_filtro()[i][11].toString().trim();
                                 fila[11] = cargar_tabla_vauchers_de_filtro()[i][12].toString().trim();
                                 modelo_vauchers.addRow(fila);
                                 
                                 if(Float.valueOf(cargar_tabla_vauchers_de_filtro()[i][12].toString().trim())>0){
                                	 retiro[0] = cargar_tabla_vauchers_de_filtro()[i][1].toString().trim();
                                	 retiro[1] = cargar_tabla_vauchers_de_filtro()[i][12].toString().trim();
                                	 modelo_retiro_de_clientes.addRow(retiro);
                                	 suma_total_retiros += Double.valueOf( cargar_tabla_vauchers_de_filtro()[i][12].toString().trim());
                                 }
                                 suma_total += (Double.valueOf(cargar_tabla_vauchers_de_filtro()[i][10].toString().trim()));
	    				}
	    			}
	    			
	    			if(txtTotalVaucher.getText().toString().trim().equals("")){
	    				txtTotalVaucher.setText(formato.format(suma_total)+"");
	    			}else{
	    				txtTotalVaucher.setText(formato.format(Double.valueOf(txtTotalVaucher.getText().toString().trim())+suma_total)+"");
	    			}
	    				txtTotalRetiros.setText(formato.format(suma_total_retiros)+"");
				}
				calculoDinamico();
		    dispose();
			}
		};
		
		private Object[][] cargar_tabla_vauchers_de_filtro(){
			Object[][] matriz = new Object[tabla_vaucher_filtro.getRowCount()][13];
				for(int i=0; i<tabla_vaucher_filtro.getRowCount(); i++){
						matriz[i][0]  = modelo_vaucher_filtro.getValueAt(i, 0).toString().trim();
						matriz[i][1]  = modelo_vaucher_filtro.getValueAt(i, 1).toString().trim();
						matriz[i][2]  = modelo_vaucher_filtro.getValueAt(i, 2).toString().trim();
						matriz[i][3]  = modelo_vaucher_filtro.getValueAt(i, 3).toString().trim();
						matriz[i][4]  = modelo_vaucher_filtro.getValueAt(i, 4).toString().trim();
						matriz[i][5]  = modelo_vaucher_filtro.getValueAt(i, 5).toString().trim();
						matriz[i][6]  = modelo_vaucher_filtro.getValueAt(i, 6).toString().trim();
						matriz[i][7]  = modelo_vaucher_filtro.getValueAt(i, 7).toString().trim();
						matriz[i][8]  = modelo_vaucher_filtro.getValueAt(i, 8).toString().trim();
						matriz[i][9]  = modelo_vaucher_filtro.getValueAt(i, 9).toString().trim();
						matriz[i][10] = modelo_vaucher_filtro.getValueAt(i,10).toString().trim();
						matriz[i][11] = modelo_vaucher_filtro.getValueAt(i,11).toString().trim();
						matriz[i][12] = modelo_vaucher_filtro.getValueAt(i,12).toString().trim();
				}
			return matriz;
		}
	}
///////termina Filtro De Seleccion de Vouchers	
	
///////TODO	catalogo de seleccion de ticket de fuente de sodas
			public class Cat_Seleccion_De_Ticket_De_FS_Cortes extends JDialog {
				
				Container cont = getContentPane();
				JLayeredPane campo = new JLayeredPane();
				
				int folio_empleado=0;
				
				DefaultTableModel modeloFiltro = new DefaultTableModel(null,
			            new String[]{"Ticket", "Importe","Fecha",""}
						){
				     @SuppressWarnings("rawtypes")
					Class[] types = new Class[]{
				    	java.lang.Integer.class,
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
			        	 	case 3 : return true;
			        	 		
			        	 } 				
			 			return false;
			 		}
				};
				
				JTable tablaFiltro = new JTable(modeloFiltro);
			    JScrollPane scroll = new JScrollPane(tablaFiltro);
				
				JTextField txtFolio = new JTextField();
				JTextField txtNombre_Completo = new JTextField();
				
				JButton btnAgregar = new JButton(new ImageIcon("Iconos/agregar.png"));
				
				public Cat_Seleccion_De_Ticket_De_FS_Cortes(int folio,String empleado) {
					
					this.setModal(true);
					setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/filter_icon&16.png"));
					setTitle("Ticket Capturados Por Cajero(a)");
					campo.setBorder(BorderFactory.createTitledBorder("Seleccion De Ticket Por Empleado"));
					
					this.txtFolio.setEditable(false);
					this.txtNombre_Completo.setEditable(false);
					
					folio_empleado=folio;
					
					txtFolio.setText(folio+"");
					txtNombre_Completo.setText(empleado);
					
					buscar_tabla(folio_empleado);
					
					btnAgregar.setToolTipText("Agregar");
					
					campo.add(scroll).setBounds(15,43,374,360);
					
					campo.add(txtFolio).setBounds(15,20,40,20);
					campo.add(txtNombre_Completo).setBounds(56,20,280,20);
					campo.add(btnAgregar).setBounds(340,20,50,20);
					
					cont.add(campo);
					
					configuracionTabla();
					
					btnAgregar.addActionListener(opAgregar);
					setSize(415,450);
					setResizable(false);
					setLocationRelativeTo(null);
				}
				
				public void configuracionTabla(){
					
					tablaFiltro.getColumnModel().getColumn(0).setMaxWidth(100);
					tablaFiltro.getColumnModel().getColumn(0).setMinWidth(100);
					tablaFiltro.getColumnModel().getColumn(1).setMaxWidth(140);
					tablaFiltro.getColumnModel().getColumn(1).setMinWidth(140);
					tablaFiltro.getColumnModel().getColumn(2).setMaxWidth(80);
					tablaFiltro.getColumnModel().getColumn(2).setMinWidth(80);
					tablaFiltro.getColumnModel().getColumn(3).setMaxWidth(40);
					tablaFiltro.getColumnModel().getColumn(3).setMinWidth(40);
					
//					tablaRender(tablaFiltro);
					
					TableCellRenderer render = new TableCellRenderer() { 
						public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, 
						boolean hasFocus, int row, int column) { 
							
							Component componente = null;
							
							switch(column){
								case 0: 
									componente = new JLabel(value == null? "": value.toString());
									if(row %2 == 0){
										((JComponent) componente).setOpaque(true); 
										componente.setBackground(new java.awt.Color(177,177,177));	
									}
									if(Boolean.parseBoolean(modeloFiltro.getValueAt(row,2).toString())){
										((JComponent) componente).setOpaque(true); 
										componente.setBackground(new java.awt.Color(186,143,73));
									}
									if(table.getSelectedRow() == row){
										((JComponent) componente).setOpaque(true); 
										componente.setBackground(new java.awt.Color(186,143,73));
									}
									((JLabel) componente).setHorizontalAlignment(SwingConstants.RIGHT);
									break;
								case 1: 
									componente = new JLabel(value == null? "": value.toString());
									if(row %2 == 0){
										((JComponent) componente).setOpaque(true); 
										componente.setBackground(new java.awt.Color(177,177,177));	
									}
									if(Boolean.parseBoolean(modeloFiltro.getValueAt(row,2).toString())){
										((JComponent) componente).setOpaque(true); 
										componente.setBackground(new java.awt.Color(186,143,73));
									}
									if(table.getSelectedRow() == row){
										((JComponent) componente).setOpaque(true); 
										componente.setBackground(new java.awt.Color(186,143,73));
									}
									((JLabel) componente).setHorizontalAlignment(SwingConstants.RIGHT);
									break;
								case 2: 
									componente = new JLabel(value == null? "": value.toString());
									if(row %2 == 0){
										((JComponent) componente).setOpaque(true); 
										componente.setBackground(new java.awt.Color(177,177,177));	
									}
									if(Boolean.parseBoolean(modeloFiltro.getValueAt(row,2).toString())){
										((JComponent) componente).setOpaque(true); 
										componente.setBackground(new java.awt.Color(186,143,73));
									}
									if(table.getSelectedRow() == row){
										((JComponent) componente).setOpaque(true); 
										componente.setBackground(new java.awt.Color(186,143,73));
									}
									((JLabel) componente).setHorizontalAlignment(SwingConstants.CENTER);
									break;
								case 3: 
									componente = new JCheckBox("",Boolean.parseBoolean(value.toString()));
									if(row%2==0){
										((JComponent) componente).setOpaque(true); 
										componente.setBackground(new java.awt.Color(177,177,177));	
									}
									if(Boolean.parseBoolean(modeloFiltro.getValueAt(row,2).toString())){
										((JComponent) componente).setOpaque(true); 
										componente.setBackground(new java.awt.Color(186,143,73));
									}
									if(table.getSelectedRow() == row){
										((JComponent) componente).setOpaque(true); 
										componente.setBackground(new java.awt.Color(186,143,73));
									}
									((AbstractButton) componente).setHorizontalAlignment(SwingConstants.CENTER);
									break;
							}
							return componente;
						} 
					}; 
				
					tablaFiltro.getColumnModel().getColumn(0).setCellRenderer(render); 
					tablaFiltro.getColumnModel().getColumn(1).setCellRenderer(render);
					tablaFiltro.getColumnModel().getColumn(2).setCellRenderer(render);
					tablaFiltro.getColumnModel().getColumn(3).setCellRenderer(render);
				}
				
				ActionListener opAgregar = new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						Obj_Alimentacion_Cortes corte = new Obj_Alimentacion_Cortes();
						
							if(tablaFiltro.isEditing()){
					 			tablaFiltro.getCellEditor().stopCellEditing();
							}
							corte.actualizarCapturaFS(lblFolio_Corte.getText(), folio_usuario, fs_corte());
							calculoDinamico();
							btnSalir.setEnabled(false);
							dispose();				
					}
				};
				
				public Object[][] fs_corte(){
					
					Object[][] tabla = new Object[tablaFiltro.getRowCount()][2];
					
					double totalFS = 0;
					
					for(int i = 0; i<=tablaFiltro.getRowCount()-1; i++){
						tabla[i][0] = tablaFiltro.getValueAt(i, 0).toString().trim(); 
						tabla[i][1] = tablaFiltro.getValueAt(i, 3).toString().trim();
						
						if(tablaFiltro.getValueAt(i, 3).toString().trim().equals("true")){
							totalFS = totalFS + (Double.valueOf(tablaFiltro.getValueAt(i, 1).toString()));
						}
					}
					
					txtTotalFS.setText(formato.format(totalFS)+"");
					totalFS=0;
					
					return tabla;
				}
				
				public void buscar_tabla(int folio_empleado){
					
					String[][] TicketsCapturadosPorCajera = new BuscarSQL().getTicket_FS_Para_Cortes(folio_empleado);
					
					for(int i=0; i<TicketsCapturadosPorCajera.length; i++){
						 		Object[] dom = new Object[5];
						 		
						 		dom[0] = TicketsCapturadosPorCajera[i][0]+"   ";
						 		dom[1] = "   "+TicketsCapturadosPorCajera[i][1];                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
						 		dom[2] = TicketsCapturadosPorCajera[i][2];
						 		dom[3] = TicketsCapturadosPorCajera[i][3];
						 		modeloFiltro.addRow(dom);
					}
				}
			}
			
	/////////////////termina filtro de seleccion de fuente de sodas
	public void llenar_venta_de_asignaciones_por_fecha(){
		  modelo_totales_por_fecha.setRowCount(0);
		Object[] vectorDeAsignaciones = new Object[3];
		
		if(Bandera_Asignacion_o_Turno.equals("A")) {
			for(int i=0; i<tabla_asignaciones.getRowCount(); i++){
					Object[][] getTablaFiltroVauchers = getTotalPorFecha(tabla_asignaciones.getValueAt(i, 0).toString().toUpperCase().trim());
				
					for(int j=0; j<getTablaFiltroVauchers.length; j++){
							vectorDeAsignaciones[0] = getTablaFiltroVauchers[j][0]+"";
					    	vectorDeAsignaciones[1] = getTablaFiltroVauchers[j][1]+"";
					    	vectorDeAsignaciones[2] = getTablaFiltroVauchers[j][2]+"";
	                        modelo_totales_por_fecha.addRow(vectorDeAsignaciones);
					}
			}
		}else {
			for(int i=0; i<tabla_turnos.getRowCount(); i++){
				Object[][] getTablaFiltroVauchers = getTotalPorFecha(tabla_turnos.getValueAt(i, 0).toString().toUpperCase().trim());
			
				for(int j=0; j<getTablaFiltroVauchers.length; j++){
						vectorDeAsignaciones[0] = getTablaFiltroVauchers[j][0]+"";
				    	vectorDeAsignaciones[1] = getTablaFiltroVauchers[j][1]+"";
				    	vectorDeAsignaciones[2] = getTablaFiltroVauchers[j][2]+"";
                        modelo_totales_por_fecha.addRow(vectorDeAsignaciones);
				}
		}
		}
	}
	
 	public Object[][] getTotalPorFecha(String asignacion_turno){
 		String query ="exec sp_IZAGAR_Recopilacion_de_venta_por_fecha_de_un_turno_o_asignacion '"+asignacion_turno+"','"+Bandera_Asignacion_o_Turno+"'" ;
		Statement s; ResultSet rs;
		Object[][] MatrizFiltro = new Object[getFilas_IZAGAR(query)][3];
		try {
				s = new Connexion().conexion_IZAGAR().createStatement();
				rs = s.executeQuery(query);		
				
			int i=0;
				while(rs.next()){
					MatrizFiltro[i][0] = "   "+rs.getString(1).trim();
					MatrizFiltro[i][1] = "   "+rs.getString(2).trim();
					MatrizFiltro[i][2] = "   "+rs.getString(3).trim();
					i++;
				}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	    return MatrizFiltro; 
 	}
	
//	variables para calculo dinamico de la diferiencia de corte  funcion = (calculoDinamico());
	double corteSistema = 0;
	double apartado = 0;
	double abono = 0;
	double dinero_electronico = 0;
	double efectivo = 0;
	double retiroCajero = 0;
//	double deposito = 0;
	double tiempoAire = 0;
	double resiboLuz = 0;
	double cheque = 0;
	double totalFS = 0;
	double totalVauchers = 0;
	double retiroCliente = 0;
	double diferienciaCorte = 0;
	
		public void calculoDinamico(){
			
			corteSistema = txtCorteSistema.getText().equals("")?0:Double.valueOf(txtCorteSistema.getText());
			apartado = txtApartados.getText().equals("")?0:Double.valueOf(txtApartados.getText());
			abono = txtAbonos.getText().equals("")?0:Double.valueOf(txtAbonos.getText());
			dinero_electronico=txtDineroElectronico.getText().equals("")?0:Double.valueOf(txtDineroElectronico.getText());
			efectivo = txtEfectivo.getText().equals("")?0:Double.valueOf(txtEfectivo.getText());
			retiroCajero = txtRetiroCajero.getText().equals("")?0:Double.valueOf(txtRetiroCajero.getText());
			tiempoAire = txtTiempoAire.getText().equals("")?0:Double.valueOf(txtTiempoAire.getText());
			resiboLuz = txtReciboLuz.getText().equals("")?0:Double.valueOf(txtReciboLuz.getText());
			cheque = txtCheques.getText().equals("")?0:Double.valueOf(txtCheques.getText());
			totalFS = txtTotalFS.getText().equals("")?0:Double.valueOf(txtTotalFS.getText());
			
			totalVauchers = txtTotalVaucher.getText().equals("")?0:Double.valueOf(txtTotalVaucher.getText());			
			retiroCliente = txtTotalRetiros.getText().equals("")?0:Double.parseDouble(txtTotalRetiros.getText());
			
			diferienciaCorte = ((corteSistema+apartado/*-abono*/)-((efectivo+retiroCajero+dinero_electronico)/*+deposito+tiempoAire+resiboLuz*/+(totalVauchers-retiroCliente)+cheque+totalFS));
			
			if(diferienciaCorte < 0){
					lblEtiquetaCorte.setText("Sobrante");
					lblEtiquetaCorte.setForeground(Color.GREEN);
					diferienciaCorte = diferienciaCorte*(-1);
			}else{
					if(diferienciaCorte == 0){
						lblEtiquetaCorte.setText("Exacto");
						lblEtiquetaCorte.setForeground(Color.BLACK);
					}else{
						lblEtiquetaCorte.setText("faltante");
						lblEtiquetaCorte.setForeground(Color.RED);
					}
			}
			lblDiferenciaCorte.setText("$ "+formato.format(diferienciaCorte));
		}
		

		
		public class Cat_Retiros_A_Detalle extends Cat_Consulta_Retiros_A_Detalle{
			
			public Cat_Retiros_A_Detalle(int folio_cajero,String establecimiento){
				model_retiros.setRowCount(0);
				String[][] retiros_a_detalle = new BuscarSQL().getRetiros_a_detalle(folio_cajero,establecimiento);
				for(int i=0; i<retiros_a_detalle.length; i++){
					 		Object[] retiro = new Object[5];
					 		retiro[0] = retiros_a_detalle[i][0];
					 		retiro[1] = retiros_a_detalle[i][1];                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               
					 		retiro[2] = retiros_a_detalle[i][2];
					 		retiro[3] = retiros_a_detalle[i][3];
					 		retiro[4] = retiros_a_detalle[i][4];
					 		model_retiros.addRow(retiro);
				}
				
				btnAgregar.addActionListener(opCargar);
				this.addWindowListener(op_cerrar);
			}
			
			WindowListener op_cerrar = new WindowListener() {
				public void windowOpened(WindowEvent e) {}
				public void windowIconified(WindowEvent e) {}
				public void windowDeiconified(WindowEvent e) {}
				public void windowDeactivated(WindowEvent e) {}
				public void windowClosing(WindowEvent e) {
					btnAgregar.doClick();
				}
				public void windowClosed(WindowEvent e) {}
				public void windowActivated(WindowEvent e) {}
			};
			
			ActionListener opCargar = new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					double retiros_programados = 0;
					String [][] Retirosfalse = new String[tabla_retiros.getRowCount()][2];
					
					for(int i=0; i<tabla_retiros.getRowCount(); i++){
							
						  Retirosfalse[i][0] = tabla_retiros.getValueAt(i, 0).toString();
						  Retirosfalse[i][1] =  tabla_retiros.getValueAt(i,4 ).toString();
						  
							if(Boolean.valueOf(tabla_retiros.getValueAt(i, 4).toString())){  
								retiros_programados+=Double.valueOf(tabla_retiros.getValueAt(i,1).toString());
							}
					}
					
					new ActualizarSQL().Actualizar_retiros_seleccionados(Retirosfalse);
					 txtRetiroCajero.setText(retiros_programados+"");
					 calculoDinamico();
					 dispose();
				}
			};
			public boolean Actualizar_Captura_FS(String folio_corte, int folio_usuario, Object[][] tabla){
				String queryReset ="exec sp_reset_captura_fs ?";
				String query ="exec sp_update_captura_fs ?,?,?,?";
				Connection con = new Connexion().conexion();
				
				try {
					con.setAutoCommit(false);
					PreparedStatement pstmtReset = con.prepareStatement(queryReset);
					PreparedStatement pstmt = con.prepareStatement(query);
					pstmtReset.setString(1, folio_corte);
					pstmtReset.executeUpdate();
					
					for(int i=0; i<tabla.length; i++){
						
						pstmt.setString(1, folio_corte);
						pstmt.setString (2, tabla[i][0].toString());
						pstmt.setString (3, tabla[i][1].toString());
						pstmt.setInt (4, folio_usuario);
						pstmt.executeUpdate();
					}
					con.commit();
				} catch (Exception e) {
						System.out.println("SQLException: "+e.getMessage());
							if(con != null){
								try{
									System.out.println("La transacción ha sido abortada");
									con.rollback();
									JOptionPane.showMessageDialog(null, "Error en ActualizarSQL  en la funcion [ Actualizar_Captura_FS ] update  SQLException: sp_update_captura_fs "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
								}catch(SQLException ex){
									System.out.println(ex.getMessage());
									JOptionPane.showMessageDialog(null, "Error en ActualizarSQL  en la funcion [ Actualizar_Captura_FS ] update  SQLException: sp_update_captura_fs "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
							}
							return false;
							}	
				}finally{
						try {
							con.close();
						} catch(SQLException e){
							e.printStackTrace();
						}
				}		
				return true;
			}	
		}
}
