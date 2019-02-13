package Cat_Lista_de_Raya;

import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import Cat_IZAGAR.Cat_IZAGAR_Pasar_Netos_De_Nomina_A_Bancos;
import Cat_IZAGAR.Cat_IZAGAR_Selecionar_Nomina_Para_Netos;
import Cat_Reportes.Cat_Reportes_De_Empleados_Sin_Deposito_A_Bancos;
import Cat_Reportes.Cat_Reportes_De_Depositos_A_Bancos;
import Cat_Reportes.Cat_Reportes_De_Empleados_Con_Deposito_En_Bancos_Excedido;
import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Connexion;
import IZAGAR_Obj.Obj_IZAGAR_Netos_Nominas;
import Obj_Lista_de_Raya.Obj_Depositos_A_Bancos;
import Obj_Principal.JCButton;
import Obj_Principal.Obj_Filtro_Dinamico;
import Obj_Principal.Obj_tabla;

@SuppressWarnings("serial")
public class Cat_Depositos_A_Bancos extends Cat_Root {
	Runtime R = Runtime.getRuntime();
	
	public static JCheckBox chbHabilitarBanorte = new JCheckBox("Habilitar");
	public JCheckBox chbNegativos = new JCheckBox("Valores Negativos");
	Obj_tabla ObjTab =new Obj_tabla();
	Obj_Depositos_A_Bancos banco = new Obj_Depositos_A_Bancos();
	
	float numero=0;
	int columnas = 7,checkbox=-1;
	String[][] tabla_guardado;
	String[][] tabla_bancos_de_totales = null;
	
	   @SuppressWarnings("static-access")
	public void init_tabla(){
		    
		    this.tabla.getColumnModel().getColumn(0).setMaxWidth(72);
	    	this.tabla.getColumnModel().getColumn(0).setMinWidth(72);		
	    	this.tabla.getColumnModel().getColumn(1).setMaxWidth(280);
	    	this.tabla.getColumnModel().getColumn(1).setMinWidth(280);
	    	this.tabla.getColumnModel().getColumn(2).setMaxWidth(180);
	    	this.tabla.getColumnModel().getColumn(2).setMinWidth(180);
	    	this.tabla.getColumnModel().getColumn(3).setMaxWidth(120);
	    	this.tabla.getColumnModel().getColumn(3).setMinWidth(120);
	    	this.tabla.getColumnModel().getColumn(4).setMaxWidth(120);
	    	this.tabla.getColumnModel().getColumn(4).setMinWidth(120);		
	    	this.tabla.getColumnModel().getColumn(5).setMaxWidth(130);
	    	this.tabla.getColumnModel().getColumn(5).setMinWidth(130);
	    	this.tabla.getColumnModel().getColumn(6).setMaxWidth(110);
	    	this.tabla.getColumnModel().getColumn(6).setMinWidth(110);
	    	
	    	this.tabla_totales.getColumnModel().getColumn(0).setMaxWidth(140);
	    	this.tabla_totales.getColumnModel().getColumn(0).setMinWidth(140);		
	    	this.tabla_totales.getColumnModel().getColumn(1).setMaxWidth(110);
	    	this.tabla_totales.getColumnModel().getColumn(1).setMinWidth(110);

			String basedatos="98",pintar="si";
			
			tabla_model.setRowCount(0);
			String comando="exec bancos_depositos_consulta" ;
			ObjTab.Obj_Refrescar(tabla        , tabla_model        ,columnas, comando, basedatos,pintar,checkbox);
			calcularSugerido();

			tabla_model_totales.setRowCount(0);
			String comandot="select '', 0 " ;
			ObjTab.Obj_Refrescar(tabla_totales, tabla_model_totales, 2      , comandot, basedatos,pintar,checkbox);
		}
	   
	   
	public static DefaultTableModel tabla_model=  new DefaultTableModel(null,new String[]{"Folio", "Nombre Completo", "Establecimiento", "Banco", "Deposito", "Total a Pagar","Sugerido" })
	{;	
        public boolean isCellEditable(int fila, int columna){
        	switch(columna){
        		case 0 : return false; 
        	 	case 1 : return false; 
        	 	case 2 : return false; 
        	 	case 3 : return false;     	 			
        	 	case 4 : 
    	 			if(chbHabilitarBanorte.isSelected()){
    	 				if(tabla_model.getValueAt(fila,4).toString().length() != 0){
    	 					return true;
    	 				}else{	return true; }
    	 			 }else{	 return false; }
        	 	case 5 : return false;
        	 	case 6 : return false;
        	 } 				
 			return false;
 		}
	};
	public static JTable tabla = new JTable(tabla_model);
	public JScrollPane scroll_tabla = new JScrollPane(tabla);
	
	
	public static DefaultTableModel tabla_model_totales = new DefaultTableModel(null,new String[]{"Banco", "Totales"}){
        public boolean isCellEditable(int fila, int columna){
        	switch(columna){
        		case 0 : return false; 
        	 	case 1 : return false; 
        	 } 				
 			return false;
 		}
	};
	public static JTable tabla_totales = new JTable(tabla_model_totales);
	public JScrollPane scroll_tabla_totales = new JScrollPane(tabla_totales);

	JButton btn_IDepositosBancLimpio  = new JButton();
	JButton btn_IDepositosBancP_Estab = new JButton();
	
	JCButton btn_lay_out          = new JCButton(""                 ,"tarjeta-de-credito-visa-icono-8242-32.png","Azul" );
	JCButton btn_cargar_nomina    = new JCButton(""                 ,"ingresos_32.png"                          ,"Verde");
	
	JCButton btn_EmpleadosS_Dep   = new JCButton(""                 ,"usuario-de-alerta-icono-4069-32 -.png"    ,"Azul" );
	JCButton btn_Empleados_Pagar_Negativo  = new JCButton(""        ,"rebicionTotales_32.png"                   ,"Azul" );
	JCButton btn_aplicar_Sugerido = new JCButton("Aplicar Sugerido" ,"Aplicar.png"                              ,"Azul" );
	JCButton btnBuscar            = new JCButton("Buscar"           ,"Filter-List-icon16.png"                   ,"Azul" ); 

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public TableRowSorter trsfiltro = new TableRowSorter(tabla_model); 
		
    @SuppressWarnings("static-access")
	public Cat_Depositos_A_Bancos(){
		this.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds()); 
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("Depositos A Tarjetas De Nomina Bancos ");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/banco.png"));

		int alto = Toolkit.getDefaultToolkit().getScreenSize().height;
		alto=alto-150;
			
		this.panel.add(cmbEstablecimientos).setBounds(385,35,190,20);
		this.panel.add(chbHabilitarBanorte).setBounds(700,35,90,20);
		this.panel.add(chbNegativos).setBounds(800,35,115,20);
		this.panel.add(btn_aplicar_Sugerido).setBounds(915,35,155,20);
		
		panel.remove(txtNombre_Completo);
		this.panel.add(txtNombre_Completo).setBounds(101,35,280,20);
		
		this.panel.add(scroll_tabla).setBounds(30,60,1035,alto);
		this.panel.add(btn_lay_out).setBounds(1085,200,40,40);
		this.panel.add(new JLabel("Generar Lay Out")).setBounds(1150,210,130,20);
		this.panel.add(btn_cargar_nomina).setBounds(1085,250,40,40);
		this.panel.add(new JLabel("Cargar Nomina")).setBounds(1150,260,130,20);
		this.panel.add(btn_IDepositosBancLimpio).setBounds(1085,300,40,40);
		this.panel.add(new JLabel("Imprimir Reporte Para Exportar a Excel")).setBounds(1150,310,250,20);
		this.panel.add(btn_IDepositosBancP_Estab).setBounds(1085,350,40,40);
		this.panel.add(new JLabel("Imprimir Reporte Por Establecimiento")).setBounds(1150,360,250,20);
		this.panel.add(btn_EmpleadosS_Dep).setBounds(1085,400,40,40);
		this.panel.add(new JLabel("Reportes de Empleados Sin Depositos A Bancos")).setBounds(1150,410,300,20);
		this.panel.add(btn_Empleados_Pagar_Negativo).setBounds(1085,450,40,40);
		this.panel.add(new JLabel("Reportes de Empleados Con Deposito Excedido")).setBounds(1150,460,300,20);
		
		this.panel.add(scroll_tabla_totales).setBounds(1085,60,270,120);
		
		ImageIcon imaglimpio = new ImageIcon(System.getProperty("user.dir")+"/Iconos/hoja-de-calculo-excel-icono-8804-48.png");
	    btn_IDepositosBancLimpio.setIcon(new ImageIcon(imaglimpio.getImage().getScaledInstance(btn_lay_out.getWidth()-4,btn_lay_out.getHeight()-4, Image.SCALE_DEFAULT)));	
		
	    ImageIcon imagCompleto = new ImageIcon(System.getProperty("user.dir")+"/Iconos/hoja-de-calculo-excel-invoice-icono-5449-48.png");
	    btn_IDepositosBancP_Estab.setIcon(new ImageIcon(imagCompleto.getImage().getScaledInstance(btn_lay_out.getWidth()-4,btn_lay_out.getHeight()-4, Image.SCALE_DEFAULT)));	
	    
		this.cont.add(panel);
		this.init_tabla();
		
		try {
			tabla_bancos_de_totales = banco.buscar_bancos();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		totales();
		
		this.btn_guardar.addActionListener(op_guardar);
		this.btn_lay_out.addActionListener(op_lay_out);
		this.btn_cargar_nomina.addActionListener(ventana_cargar_nomina);
		this.btn_IDepositosBancLimpio.addActionListener(Reporte_Depositos_Bancos_limpio);
		this.btn_IDepositosBancP_Estab.addActionListener(Reporte_Depositos_Bancos_);
		this.btn_EmpleadosS_Dep.addActionListener(Reporte_Empleados_Sin_Depositos_A_Bancos_);
		this.btn_Empleados_Pagar_Negativo.addActionListener(Reporte_Empleados_Con_Depositos_A_Bancos_Excedido);
		this.btn_aplicar_Sugerido.addActionListener(aplicarSugerido);
		
		this.txtFolio.addKeyListener(op_filtro);
		this.txtNombre_Completo.addKeyListener(op_filtro);
		this.cmbEstablecimientos.addActionListener(op_filtro_establecimiento);
		this.chbNegativos.addActionListener(op_filtro_establecimiento);
		this.tabla.addKeyListener            (op_validanumero_en_celda );
		this.btn_refrescar.setVisible(false);
		
		this.addWindowListener(new WindowAdapter() { public void windowOpened( WindowEvent e ){
           	 txtNombre_Completo.requestFocus();
           } });
		
		this.addWindowListener(op_cerrar);
	}
    
    public void calcularSugerido(){
 		for(int i=0; i<tabla.getRowCount(); i++){
 			if(tabla_model.getValueAt(i,4).toString().equals("")){
 				tabla_model.setValueAt("", i, 6);
 			}else{
 				if(tabla_model.getValueAt(i,5).toString().equals("")){numero=0;}else{ numero=Float.valueOf(tabla_model.getValueAt(i,5).toString());}
	 				if(numero < 0){
	 						tabla_model.setValueAt(Float.valueOf(tabla_model.getValueAt(i,4).toString())+numero+"", i, 6) ;
	 				}else{
	 					tabla_model.setValueAt("", i, 6);
	 				}
	        }
 		}
    }
    
    public void totales(){
		 float total = 0;
		 String banco="";
	     ObjTab.llenado_de_modelo_desde_datos_tabla_precargados(tabla_bancos_de_totales, tabla_totales);
		
		for(int i = 0 ; i<tabla_model.getRowCount(); i++){
			banco=tabla_model.getValueAt(i, 3).toString().trim() ;
			total=Float.parseFloat(tabla_model.getValueAt(i, 4).toString().trim()) ;
			for(int b=0;b<tabla_model_totales.getRowCount();b++ ) {
				if(banco.equals(tabla_model_totales.getValueAt(b,0).toString())){
					total=total+Float.valueOf(tabla_model_totales.getValueAt(b,1).toString());
					tabla_model_totales.setValueAt(total, b, 1);					
				}  
			}
		}
	}
	
    ActionListener aplicarSugerido = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
		  new Obj_Filtro_Dinamico(tabla,"Folio", "", "", "","","","","");			
		 if(tabla.isEditing()){tabla.getCellEditor().stopCellEditing();}
 		for(int i=0; i<tabla.getRowCount(); i++){
 			if(!tabla_model.getValueAt(i,6).toString().equals("")){		
		 				tabla_model.setValueAt(Float.valueOf(tabla_model.getValueAt(i,6).toString()), i, 4); 
	 			}
 		}
 		guardar();
     }
    };
    
    WindowListener op_cerrar = new WindowListener() {
		public void windowOpened(WindowEvent e) {}
		public void windowIconified(WindowEvent e) {}
		public void windowDeiconified(WindowEvent e) {}
		public void windowDeactivated(WindowEvent e) {}
		public void windowClosing(WindowEvent e) {
			if(JOptionPane.showConfirmDialog(null, "¿Desea guardar antes de cerrar?", "Aviso!", JOptionPane.YES_NO_OPTION) == 0){
				tabla_guardado=ObjTab.tabla_guardar(tabla);
				new Obj_Depositos_A_Bancos().guardar(tabla_guardado);
			}
		}
		public void windowClosed(WindowEvent e) {}
		public void windowActivated(WindowEvent e) {}
	};
	
    ActionListener op_guardar = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			boolean estatus_autorizacion=false;
			try{estatus_autorizacion= new BuscarSQL().autorizacion_lista_de_raya_estatus() ;} catch (SQLException e) {	e.printStackTrace();}
			if(estatus_autorizacion == true){
				JOptionPane.showMessageDialog(null, "La Lista De Raya Fue Autorizada No Puede Ser Modificado Ningun Deposito a Bancos......\nHasta Que Se Genere Por D.H o Se Desautorize por Finanzas o Auditoria ","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
			}else{
			txtFolio.setText("");
			txtNombre_Completo.setText("");
			cmbEstablecimientos.setSelectedIndex(0);
			if(tabla.isEditing()){tabla.getCellEditor().stopCellEditing();}
			if(JOptionPane.showConfirmDialog(null, "¿Desea guardar la lista de bancos?") == 0){
				guardar();
			}else{
				return;
			}
		 }
		}
	};
	
	public void guardar() {
		  Obj_Depositos_A_Bancos banco = new Obj_Depositos_A_Bancos();
		  tabla_guardado=ObjTab.tabla_guardar(tabla);
		if(banco.guardar(tabla_guardado)){
			    chbNegativos.setSelected(false);
			    init_tabla();
				totales();
				calcularSugerido();
		    	JOptionPane.showMessageDialog(null, "Se Guardo Correctamente", "Aviso", JOptionPane.OK_OPTION,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
			return;
		}else{
			JOptionPane.showMessageDialog(null,"Error Al Guardar Avise al Administrador del Sistema","Aviso",JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-icono-eliminar5252-64.png"));
			return;
		}	
	}
	
	ActionListener op_filtro_establecimiento = new ActionListener(){
		public void actionPerformed(ActionEvent arg0){
			String negativo = chbNegativos.isSelected()?"-":"";
			new Obj_Filtro_Dinamico(tabla,"Folio", txtFolio.getText().toString().trim(), "Nombre Completo", txtNombre_Completo.getText().toUpperCase(),"Establecimiento",cmbEstablecimientos.getSelectedItem()+"","Total a Pagar",negativo);
		}
	};	
	
    ActionListener op_lay_out = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			new Cat_Lay_Out().setVisible(true);
		}
	};
	
	ActionListener ventana_cargar_nomina = new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent arg0) {
				txtFolio.setText("");
				trsfiltro.setRowFilter(RowFilter.regexFilter(txtFolio.getText(), 0));
				txtNombre_Completo.setText("");
				trsfiltro.setRowFilter(RowFilter.regexFilter(txtNombre_Completo.getText().toUpperCase().trim(), 1));
				new filtroSeleccion().setVisible(true);
			}
	};
	
	KeyListener op_validanumero_en_celda = new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {
				int fila=tabla.getSelectedRow();
				int columna=4; //tabla.getSelectedColumn();
				if(fila==-1)fila=fila+1;
			 if(ObjTab.validacelda(tabla,"decimal", fila,columna)){
							  if(ObjTab.RecorridoFocotabla_con_evento(tabla, fila,columna, "x",e).equals("si")){
									txtNombre_Completo.requestFocus();
							  };
				}	
			 totales();
			}
			public void keyPressed(KeyEvent e) {}
	};
		
	KeyListener op_filtro = new KeyListener(){
		public void keyReleased(KeyEvent arg0) {
			String negativo = chbNegativos.isSelected()?"-":"";
			new Obj_Filtro_Dinamico(tabla,"Folio", txtFolio.getText().toString().trim(), "Nombre Completo", txtNombre_Completo.getText().toUpperCase(),"Establecimiento",cmbEstablecimientos.getSelectedItem()+"","Total a Pagar",negativo);
		}
		public void keyTyped(KeyEvent arg0)   {}
		public void keyPressed(KeyEvent arg0) {}
	};
	
	public class filtroSeleccion extends Cat_IZAGAR_Selecionar_Nomina_Para_Netos{
		public filtroSeleccion(){
			this.txtFolionomina.addActionListener(optBuscar);
			this.btnconsultanomina.addActionListener(optBuscar);
	}
		
	ActionListener optBuscar = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(txtFolionomina.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Ingrese el No. de Folio", "Error", JOptionPane.WARNING_MESSAGE);
					return;
				}else{
					try{
						new asignarBancos(txtFolionomina.getText()).setVisible(true);
						dispose();
					}catch (NumberFormatException e1){
						e1.getStackTrace();
					}
				}
			}
		};
	}
	
	ActionListener Reporte_Depositos_Bancos_ = new ActionListener(){
		public void actionPerformed(ActionEvent e){
				new Cat_Reportes_De_Depositos_A_Bancos().setVisible(true);
		}
	};
	
	ActionListener Reporte_Empleados_Sin_Depositos_A_Bancos_ = new ActionListener(){
		public void actionPerformed(ActionEvent e){
				new Cat_Reportes_De_Empleados_Sin_Deposito_A_Bancos().setVisible(true);
		}
	};
	
	ActionListener Reporte_Empleados_Con_Depositos_A_Bancos_Excedido = new ActionListener(){
		public void actionPerformed(ActionEvent e){
				new Cat_Reportes_De_Empleados_Con_Deposito_En_Bancos_Excedido().setVisible(true);
		}
	};
	
	ActionListener Reporte_Depositos_Bancos_limpio = new ActionListener(){
		@SuppressWarnings("rawtypes")
		public void actionPerformed(ActionEvent e){
				try {
					JasperReport report = JasperCompileManager.compileReport(System.getProperty("user.dir")+"\\src\\Obj_Reportes\\Obj_Reporte_Depositos_A_Bancos_Para_Exportar.jrxml");
					@SuppressWarnings("unchecked")
					JasperPrint print = JasperFillManager.fillReport(report, new HashMap(), new Connexion().conexion());
					JasperViewer.viewReport(print, false);
				} catch (Exception e1) {
					System.out.println(e1.getMessage());
					JOptionPane.showMessageDialog(null, "Error en Cat_Depositos_A_Bancos  en la funcion [ ActionListener Reporte_Depositos_Bancos_limpio ]   SQLException:  "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
		}
	};
	
	public class asignarBancos extends Cat_IZAGAR_Pasar_Netos_De_Nomina_A_Bancos{
		String fNomina = "";
		public asignarBancos(String folio_nomina) {
			super(folio_nomina);
			 fNomina=folio_nomina;
			 btnAplicar.addActionListener(optAplicar);
			 btnReporte.addActionListener(optGenerarReporteConciliadosNomina);
	}
		
	ActionListener optGenerarReporteConciliadosNomina = new ActionListener(){
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public void actionPerformed(ActionEvent arg0) {
				String query = "exec IZAGAR_select_empleados_scoi_pre_conciliados '"+fNomina+"'"  ;
				Statement stmt = null;
					try {
						stmt =  new Connexion().conexion().createStatement();
					    ResultSet rs = stmt.executeQuery(query);
						JasperReport report = JasperCompileManager.compileReport(System.getProperty("user.dir")+"\\src\\Obj_Reportes\\Obj_Reporte_Conciliados_De_Nomina.jrxml");
						JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(rs);
						JasperPrint print = JasperFillManager.fillReport(report, new HashMap(), resultSetDataSource);
						JasperViewer.viewReport(print, false);
					} catch (Exception e2) {
						System.out.println(e2.getMessage());
						JOptionPane.showMessageDialog(null, "Error en Generar Reporte de Diferiencia sp_Reporte_De_Diferiencias_De_Recepciones_De_Transferencia SQLException: \n "+e2.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
					}	
			}
	};

	ActionListener optAplicar = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
					boolean estatus_autorizacion=false;
					try{estatus_autorizacion= new BuscarSQL().autorizacion_lista_de_raya_estatus() ;} catch (SQLException e) {	e.printStackTrace();}
					if(estatus_autorizacion == true){
						JOptionPane.showMessageDialog(null, "La Lista De Raya Fue Autorizada No Puede Ser Modificado Ningun Deposito a Bancos......\nHasta Que Se Genere Por D.H o Se Desautorize por Finanzas o Auditoria ","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
					}else{
							if(new Obj_IZAGAR_Netos_Nominas().guardar_totales_deposito_nomina_bancos()){
								dispose();
								init_tabla();
								tabla_guardado=ObjTab.tabla_guardar(tabla);
								new Obj_Depositos_A_Bancos().guardar(tabla_guardado);
								init_tabla();	
								JOptionPane.showMessageDialog(null,"El Transpaso Se a Realizado Exitosamente", "Aviso", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
								return;
							}else{
								JOptionPane.showMessageDialog(null,"No Se A Realizado El Transpaso", "Error", JOptionPane.WARNING_MESSAGE);
								return;
							}
				 	}
			  }	
		};
	}
	
	public static void main (String [] arg) {
		try{
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		new Cat_Depositos_A_Bancos().setVisible(true);
		}catch(Exception e){			
		}
	}
}