package Cat_Lista_de_Raya;

import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import Cat_Reportes.Cat_Reportes_De_Lista_De_Raya;
import Conexiones_SQL.ActualizarSQL;
import Conexiones_SQL.Connexion;
import Obj_Lista_de_Raya.Obj_Autorizacion_Auditoria;
import Obj_Lista_de_Raya.Obj_Autorizacion_Finanzas;
import Obj_Lista_de_Raya.Obj_Fue_Sodas_DH;
import Obj_Lista_de_Raya.Obj_Revision_De_Lista_Raya;
import Obj_Lista_de_Raya.Obj_Totales_De_Cheque;
import Obj_Principal.Obj_Filtro_Dinamico_Plus;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
/** CTRL EN CAT_ROOT_LISTA_RAYA PARA AGREGAR BOTON **/
public class Cat_Revision_De_Lista_Raya extends Cat_Root_Lista_Raya {
	
	JButton btnQuitarObservacionI = new JButton("Limpiar Observacion DH");
	
	 boolean acceso = null != null;
	 int cantidad_sueldos_mod =0;	
	 
	private DefaultTableModel tabla_model = new DefaultTableModel(new Obj_Revision_De_Lista_Raya().get_tabla_model(),
		new String[]{"","Folio", "Nombre Completo", "Establecimiento", "Sueldo","Bono", "P.Saldo ini", "Desc.Prest", "P.Saldo Fin", "F.Sodas","Impuntualidad",
			  "B.Puntualidad"
			,"Omision", "Faltas", "Inasistencia",
			  "B.Asistencia"
			,"Gafete", "Dif.Cortes", "Infonavit",
			  "Infonacot"
			,"Pension", "Banco", "Deposito", "Hrs Extras", "Extra","D�a Ext", "A Pagar", "Observaciones D.H.", "Observaciones II" }){
			
		@SuppressWarnings("rawtypes")
		Class[] types = new Class[]{
		   	java.lang.Boolean.class,
		   	java.lang.Object.class, 
		   	java.lang.Object.class, 
		   	java.lang.Object.class, 
		   	java.lang.Object.class, 
		    
		   	java.lang.Object.class,
		   	java.lang.Object.class, 
		   	java.lang.Object.class, 
		   	java.lang.Object.class,  
		   	java.lang.Object.class,  
		   	java.lang.Object.class, 
		    	
		   	java.lang.Object.class, 
		   	java.lang.Object.class, 
		   	java.lang.Object.class,  
		   	java.lang.Object.class,  
		   	java.lang.Object.class, 
		    	
		   	java.lang.Object.class, 
		   	java.lang.Object.class, 
		   	java.lang.Object.class,  
		   	java.lang.Object.class,  
		   	java.lang.Object.class,
		   	
		   	java.lang.Object.class,
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
	       	 	case 0  : return true; 
	      	 	case 1  : return false; 
	       	 	case 3  : return false; 
	       	 	case 4  : return false; 
	       	 	
	       	 	case 5  : return false;
	       	 	case 6  : return false; 
	       	 	case 7  : return false; 
	       	 	case 8  : return false; 
	       	 	case 9  : return false; 
	        	 	
	       	 	case 10 : return false; 
	       	 	case 11 : return false; 
	       	 	case 12 : return false; 
	       	 	case 13 : return false; 
	       	 	case 14 : return false; 
	        	 	
	       	 	case 15 : return false; 
	       	 	case 16 : return false; 
	       	 	case 17 : return false; 
	       	 	case 18 : return false; 
	       	 	case 19 : return false;
	        	 	
	       	 	case 20 : return false; 
	       	 	case 21 : return false; 
	       	 	case 22 : return false; 
	       	    case 23 : return false; 
	       	    case 24 : return false; 
	       	    case 25 : return false; 
	       	    case 26 : return false; 
	       	 	case 27 : return false; 
	       	 	case 28 : return true;
	       	 	
	       	 }
	 		return false;
	 	}
	   
	};
		
	private JTable tabla = new JTable(tabla_model);
	private JScrollPane scroll_tabla = new JScrollPane(tabla);
	JLabel JLBcambios_sueldo= new JLabel();	
	
    private JTable freezeTable = new JTable();	
    
	public void fixedColum(int fixedColumns){
		
	renderEnFixed();
	cargar_autorizaciones();
		
      freezeTable.setAutoCreateColumnsFromModel(false);
      freezeTable.setModel(tabla_model);
      freezeTable.setSelectionModel(tabla.getSelectionModel());
      freezeTable.setFocusable(false);
      
    TableColumnModel colModel = tabla.getColumnModel();
      for (int i = 0; i < fixedColumns; i++) {
    	 TableColumn column = colModel.getColumn(0);
         colModel.removeColumn(column);
         freezeTable.getColumnModel().addColumn(column);
      }
      
		
		
      
      //  Add the fixed table to the scroll pane
      freezeTable.setPreferredScrollableViewportSize(freezeTable.getPreferredSize());
      scroll_tabla.setRowHeaderView(freezeTable);
      scroll_tabla.setCorner(JScrollPane.UPPER_LEFT_CORNER, freezeTable.getTableHeader());
      
      
      freezeTable.getTableHeader().setReorderingAllowed(false) ;
      freezeTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
      
	}
	
	public void renderEnFixed(){
		
		tabla.getTableHeader().setReorderingAllowed(false) ;
		tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
      
		
		tabla.getColumnModel().getColumn(0).setMinWidth(16);
		tabla.getColumnModel().getColumn(1).setMinWidth(40);
		tabla.getColumnModel().getColumn(2).setMinWidth(260);
		tabla.getColumnModel().getColumn(3).setMinWidth(140);
		tabla.getColumnModel().getColumn(27).setMinWidth(350);
		tabla.getColumnModel().getColumn(28).setMinWidth(230);
		
		for(int i=0; i<tabla.getColumnCount(); i++){
			
			if(i>0){
				if(i==2 || i==3 || i==21 || i==27 || i==28){
					tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
				}else{
					tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12));
				}
			}else{
				tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("CHB","derecha","Arial","normal",12));
			}
			
			if(tabla.getValueAt(i, 0).toString().equals("true")){
				tabla.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","Verde",12));
			}
		}
	}
	
	/* EL CONSTRUCTOR TIENE EL NOMBRE PUBLIC Y SEGUIDO DEL NOMBRE DE LA CLASE */
	public Cat_Revision_De_Lista_Raya(){
		int ancho = Toolkit.getDefaultToolkit().getScreenSize().width;
		int alto = Toolkit.getDefaultToolkit().getScreenSize().height;
		
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds()); 
//		this.addWindowListener(op_cerrar);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		this.setTitle("Revisi�n lista raya");
		
		//fijar las primeras columnas(las que se mandan de parametro)
		fixedColum(4);
		
		this.panel.add(scroll_tabla).setBounds(30,60,ancho-50,alto-60-75);
		cont.add(panel);
		
		panel.add(new JLabel("Autorizacion Auditoria:")).setBounds(730,20,120,20);
		panel.add(JLBAutoriazacion_Auditoria).setBounds(840,20,20,20);
		panel.add(new JLabel("Traspaso Fuente Sodas:")).setBounds(870,20,120,20);
		panel.add(JLBGuardado_Fte_Sodas).setBounds(1000,20,20,20);
		
		panel.add(new JLabel("Autorizacion Finanzas:")).setBounds(730,40,120,20);
		panel.add(JLBAutorizacion_finanzas).setBounds(840,40,20,20);
		panel.add(new JLabel("Totales Nomina y Cheques:")).setBounds(870,40,135,20);
		panel.add(JLBTotales_Nomina).setBounds(1000,40,20,20);
		
		panel.add(btnQuitarObservacionI).setBounds(1430,40,150,20);
		panel.add(JLBcambios_sueldo).setBounds(1050,40,350,20);
		
		this.menu_toolbar.remove(btn_refrescar);
		
		this.init_component();
		txtCalendario.setEnabled(false);
		
		btn_imprimir.setEnabled(false);
		btn_nomina.setEnabled(false);
		tabla.addMouseListener(opTablaFiltroSeleccion);
		
		this.btn_guardar.addActionListener(op_guardar);
		this.btn_imprimir.addActionListener(op_imprimir);
		this.btn_nomina.addActionListener(op_totales_cheque);
//		this.btn_refrescar.addActionListener(op_refrescar);
		this.btn_generar.addActionListener(op_generar);
		
		btnQuitarObservacionI.addActionListener(op_Borrar_ObservacionDH);

		this.btn_lista_raya_pasadas.addActionListener(op_consulta_lista);
		
//		this.txtFolio.addKeyListener(op_filtro_folio);
		this.txtNombre_Completo.addKeyListener(op_filtro_nombre);
//		this.cmbEstablecimientos.addActionListener(op_filtro_establecimiento);
		
//      asigna el foco al JTextField 
        this.addWindowListener(new WindowAdapter() {
                public void windowOpened( WindowEvent e ){
                    txtNombre_Completo.requestFocus();
             }
        });

	}

	/**
	 *  SE DIO DE ALTA LA ACCION LISTENER OP_CONSULTA LISTA
	 *  SE LE DARA CLICK ALA OPERACION Y MOSTRARA LA OPCION ADD METOD
	**/
	ActionListener op_consulta_lista = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			new Cat_Consulta_Lista_de_Raya_Pasadas().setVisible(true);	
		}
	};
	
	
	
//	WindowListener op_cerrar = new WindowListener() {
//		public void windowOpened(WindowEvent e) {}
//		public void windowIconified(WindowEvent e) {}
//		public void windowDeiconified(WindowEvent e) {}
//		public void windowDeactivated(WindowEvent e) {}
//		public void windowClosing(WindowEvent e) {
//			if(JOptionPane.showConfirmDialog(null, "�Desea guardar antes de cerrar?", "Aviso!", JOptionPane.YES_NO_OPTION) == 0){
//				
//				txtNombre_Completo.setText("");
//				int[] columnas = {1,2,3};
//				new Obj_Filtro_Dinamico_Plus(tabla,txtNombre_Completo.getText().toUpperCase(), columnas);
//				new Obj_Filtro_Dinamico_Plus(freezeTable,txtNombre_Completo.getText().toUpperCase(), columnas);
//				
//				if(tabla.isEditing()){
//					tabla.getCellEditor().stopCellEditing();
//				}
//				
//				new Obj_Revision_De_Lista_Raya().guardar(tabla_guardar(),new SimpleDateFormat("dd/MM/yyyy").format(txtCalendario.getDate()));
//			}
//		}
//		public void windowClosed(WindowEvent e) {}
//		public void windowActivated(WindowEvent e) {}
//	};
	
	public int  Checar_Cambios_De_Sueldo_Pendientes_De_Autorizar() {
		Connexion con = new Connexion();
		String query = "	if (select validacion_sueldo_auditoria from tb_configuracion_sistema)='true' "+
		               "        select count(folio_empleado) from tb_historico_sueldos_empleados where status='N'" +
		               " 	else select 0  ";
		
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
		    ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				cantidad_sueldos_mod =(rs.getInt(1));
				
				if(cantidad_sueldos_mod>0){ 
					JLBcambios_sueldo.setText("<html> <FONT FACE="+"arial"+" SIZE=3 COLOR=RED><CENTER><b><p>Sueldos Pendientes Por Autorizar: "+cantidad_sueldos_mod+"</p></b></CENTER></FONT></html>");
			        btn_guardar.setEnabled(false);
			        btn_imprimir.setEnabled(false);
					}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error");
			JOptionPane.showMessageDialog(null, "Error en Cat_Autorizacion_De_Cambio_De_Sueldo_O_Bono en la funcion Checar_Activo()"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			return cantidad_sueldos_mod ;
		}
		finally{
			 if (stmt != null) { try {
				stmt.close();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Error en Cat_Autorizacion_De_Cambio_De_Sueldo_O_Bono en la funcion Checar_Activo()"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
				e.printStackTrace();
			} }
		}
		return cantidad_sueldos_mod;
			}
	
  
		
		
		
	public void cargar_autorizaciones(){
		
		if(!EmpleadoConNegativo().equals("")||!EmpleadoConSueldoCero().equals("No se encontraron sueldos en cero")){
			txtNombre_Completo.requestFocus();
			JOptionPane.showMessageDialog(null,"\n No Podra Usar El Boton Guardar Con Empleados Que Tengan Valores Negativos En \n <<Descuento De Prestamo,Descuento De Cortes O En El Total A Pagar>>\nSolo podra Guardar Cerrando La Ventana De Revision De Lista De Raya Y Confirmando Que Quiere Guardar \n \n                          Empleado                                                   Desc_Prest               Cortes              A Pagar\n   No Podra Usar El Boton Guardar Con Empleados Que Tengan Sueldo 0 \n"+EmpleadoConNegativo()+EmpleadoConSueldoCero(),"Aviso:",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
		}  
		
		if((new Obj_Fue_Sodas_DH().busquedaautoizacionfs().isStatus_autorizacion()))
		  {btn_imprimir.setEnabled(false);
		   JLBGuardado_Fte_Sodas.setEnabled(false);
		  }else{btn_imprimir.setEnabled(true);
			    JLBGuardado_Fte_Sodas.setEnabled(true);}
		
		if(new Obj_Autorizacion_Auditoria().buscar().isAutorizar()){
			JLBAutoriazacion_Auditoria.setEnabled(true);
			 }else{JLBAutoriazacion_Auditoria.setEnabled(false);}
		
		if(new Obj_Autorizacion_Finanzas().buscar().isAutorizar()){
			JLBAutorizacion_finanzas.setEnabled(true);
			 }else{JLBAutorizacion_finanzas.setEnabled(false);}
	    
	    if(new Obj_Totales_De_Cheque().buscar_autorizacion().isAutorizar()){
	    	JLBTotales_Nomina.setEnabled(true);
		    }else{JLBTotales_Nomina.setEnabled(false);}
	    
	    Checar_Cambios_De_Sueldo_Pendientes_De_Autorizar();
	};

		
	
	ActionListener op_generar = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			
//			trsfiltro.setRowFilter(RowFilter.regexFilter("", 1));
			
			txtNombre_Completo.setText("");
			int[] columnas2 = {1,2,3};
			new Obj_Filtro_Dinamico_Plus(tabla,txtNombre_Completo.getText().toUpperCase(), columnas2);
			new Obj_Filtro_Dinamico_Plus(freezeTable,txtNombre_Completo.getText().toUpperCase(), columnas2);
			
			if(tabla.isEditing()){
				tabla.getCellEditor().stopCellEditing();
			}
			
			if(valida_error() != ""){
				JOptionPane.showMessageDialog(null, "Los siguientes datos son requeridos:\n"+valida_error(),"Error",JOptionPane.ERROR_MESSAGE);
				return;
			}else{
				if(new Obj_Revision_De_Lista_Raya().init_autorizacion()){
					if(new Obj_Revision_De_Lista_Raya().init_totales_nomina()){
						 if(new Obj_Totales_De_Cheque().buscar_autorizacion().isAutorizar()){
						    	
						Obj_Revision_De_Lista_Raya lista_raya = new Obj_Revision_De_Lista_Raya();
						if(lista_raya.generar(tabla_generar(),new SimpleDateFormat("dd/MM/yyyy").format(txtCalendario.getDate()))){
							
							JOptionPane.showMessageDialog(null, "La Lista De Raya Se Gener� Con �xito \n Se Cerrara-Abrira y Le Pedira Guardar La Nueva Pre Lista De Raya ","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
							dispose();
							new Cat_Revision_De_Lista_Raya().setVisible(true);
							btn_guardar.doClick();
							return;
						}else{
							JOptionPane.showMessageDialog(null, "La lista de raya no se pudo generar","Error",JOptionPane.ERROR_MESSAGE);
							return;
						}
						 }else{
							 
								JOptionPane.showMessageDialog(null, "Antes De Generar La Lista De Raya Tiene Que Guardar Los Totales De Cheque", "Mensaje", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
								return;
						 }
					  }else{
						  
						JOptionPane.showMessageDialog(null, "Antes De Generar La Lista De Raya Tiene Que Guardar Los Totales De Nomina", "Mensaje", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
						return;
					}
				}else{
					JOptionPane.showMessageDialog(null, "La Lista De Raya No Esta Autorizada:", "Mensaje", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
					return;
				}
			}
		}
	};
	
	private Object[][] tabla_generar(){
		Object[][] matriz = new Object[tabla_model.getRowCount()][tabla_model.getColumnCount()];
		for(int i=0; i<tabla_model.getRowCount(); i++){
			for(int j=1; j<=tabla_model.getColumnCount()-1; j++){
				
				matriz[i][j] = tabla_model.getValueAt(i,j).toString().trim().equals("")?"0":tabla_model.getValueAt(i,j).toString().trim();
				
//				switch(j){
///*folio*/				case 1 : matriz[i][j] = Integer.parseInt(tabla_model.getValueAt(i,j).toString().trim());		break;
///*empleado*/			case 2 : matriz[i][j] = tabla_model.getValueAt(i,j).toString().trim();							break;
///*establecimiento*/		case 3 : matriz[i][j] =tabla_model.getValueAt(i,j).toString().trim();							break;
///*sueldo*/				case 4 :
//						if(tabla_model.getValueAt(i,j).toString().length() == 0){
//							matriz[i][j] = Float.parseFloat("0");
//						}else{
//							matriz[i][j] = Float.parseFloat(tabla_model.getValueAt(i,j).toString().trim());
//						}
//						break;
///*bono*/					case 5 :
//						if(tabla_model.getValueAt(i,j).toString().length() == 0){
//							matriz[i][j] = Float.parseFloat("0");
//						}else{
//							matriz[i][j] = Float.parseFloat(tabla_model.getValueAt(i,j).toString().trim());
//						}
//						break;
///*P.Saldo ini*/					case 6 :
//						if(tabla_model.getValueAt(i,j).toString().length() == 0){
//							matriz[i][j] = Float.parseFloat("0");
//						}else{
//							matriz[i][j] = Float.parseFloat(tabla_model.getValueAt(i,j).toString().trim());
//						}
//						break;
///*Desc.Prest*/					case 7 :
//						if(tabla_model.getValueAt(i,j).toString().length() == 0){
//							matriz[i][j] = Float.parseFloat("0");
//						}else{
//							matriz[i][j] = Float.parseFloat(tabla_model.getValueAt(i,j).toString().trim());
//						}
//						break;
///*P.Saldo Fin*/				case 8 :
//						if(tabla_model.getValueAt(i,j).toString().length() == 0){
//							matriz[i][j] = Float.parseFloat("0");
//						}else{
//							matriz[i][j] = Float.parseFloat(tabla_model.getValueAt(i,j).toString().trim());
//						}
//						break;
///*F.Sodas*/				case 9 :
//						if(tabla_model.getValueAt(i,j).toString().length() == 0){
//							matriz[i][j] = Float.parseFloat("0");
//						}else{
//							matriz[i][j] = Float.parseFloat(tabla_model.getValueAt(i,j).toString().trim());
//						}
//						break;
///*Imp.*/				case 10 :
//						if(tabla_model.getValueAt(i,j).toString().length() == 0){
//							matriz[i][j] = Float.parseFloat("0");
//						}else{
//							matriz[i][j] = Float.parseFloat(tabla_model.getValueAt(i,j).toString().trim());
//						}
//						break;
///*B.Puntualidad*/case 11 :
//						if(tabla_model.getValueAt(i,j).toString().length() == 0){
//							matriz[i][j] = Float.parseFloat("0");
//						}else{
//							matriz[i][j] = Float.parseFloat(tabla_model.getValueAt(i,j).toString().trim());
//						}
//						break;
///*Omision*/		case 12 :
//						if(tabla_model.getValueAt(i,j).toString().length() == 0){
//							matriz[i][j] = Float.parseFloat("0");
//						}else{
//							matriz[i][j] = Float.parseFloat(tabla_model.getValueAt(i,j).toString().trim());
//						}
//						break;
///*Faltas*/   	 case 13 :
//						if(tabla_model.getValueAt(i,j).toString().length() == 0){
//							matriz[i][j] = Float.parseFloat("0");
//						}else{
//							matriz[i][j] = Float.parseFloat(tabla_model.getValueAt(i,j).toString().trim());
//						}
//						break;
///*Inasistencia*/  case 14 :
//						if(tabla_model.getValueAt(i,j).toString().length() == 0){
//							matriz[i][j] = Float.parseFloat("0");
//						}else{
//							matriz[i][j] = Float.parseFloat(tabla_model.getValueAt(i,j).toString().trim());
//						}
//						break;
///*B. Asistencia*/ case 15 :
//						if(tabla_model.getValueAt(i,j).toString().length() == 0){
//							matriz[i][j] = Float.parseFloat("0");
//						}else{
//							matriz[i][j] = Float.parseFloat(tabla_model.getValueAt(i,j).toString().trim());
//						}
//						break;
///*Gafete*/	 	  case 16 :
//						if(tabla_model.getValueAt(i,j).toString().length() == 0){
//							matriz[i][j] = Float.parseFloat("0");
//						}else{
//							matriz[i][j] = Float.parseFloat(tabla_model.getValueAt(i,j).toString().trim());
//						}
//						break;
///*Dif Cortes*/    case 17 :
//						if(tabla_model.getValueAt(i,j).toString().length() == 0){
//							matriz[i][j] = Float.parseFloat("0");
//						}else{
//							matriz[i][j] = Float.parseFloat(tabla_model.getValueAt(i,j).toString().trim());
//						}
//						break;
///*Infonavit*/  	   case 18 :
//						if(tabla_model.getValueAt(i,j).toString().length() == 0){
//							matriz[i][j] = Float.parseFloat("0");
//						}else{
//							matriz[i][j] = (tabla_model.getValueAt(i,j).toString().trim());
//						}
//						break;
///*Infonacot*/ 	   case 19 :
//						if(tabla_model.getValueAt(i,j).toString().length() == 0){
//							matriz[i][j] = Float.parseFloat("0");
//						}else{
//							matriz[i][j] = Float.parseFloat(tabla_model.getValueAt(i,j).toString().trim());
//						}
//						break;
///*Pension*/	  	   case 20 :
//						if(tabla_model.getValueAt(i,j).toString().length() == 0){
//							matriz[i][j] = Float.parseFloat("0");
//						}else{
//							matriz[i][j] = Float.parseFloat(tabla_model.getValueAt(i,j).toString().trim());
//						}
//						break;
///*Banco*/	  	    case 21 :
//	                      matriz[i][j] =tabla_model.getValueAt(i,j).toString().trim();	
//						break;
///*Deposito*/	    case 22 :
//						if(tabla_model.getValueAt(i,j).toString().length() == 0){
//							matriz[i][j] = Float.parseFloat("0");
//						}else{
//							matriz[i][j] = Float.parseFloat(tabla_model.getValueAt(i,j).toString().trim());
//						}
//						break;
///*Hrs Extras*/	 	case 23 :
//						if(tabla_model.getValueAt(i,j).toString().length() == 0){
//							matriz[i][j] = Float.parseFloat("0");
//						}else{
//							matriz[i][j] = Float.parseFloat(tabla_model.getValueAt(i,j).toString().trim());
//						}
//						break;
///*Extra */			case 24 :
//						if(tabla_model.getValueAt(i,j).toString().length() == 0){
//							matriz[i][j] = Float.parseFloat("0");
//						}else{
//							matriz[i][j] = Float.parseFloat(tabla_model.getValueAt(i,j).toString().trim());
//						}
///*Dia Ext*/			case 25 :
//						if(tabla_model.getValueAt(i,j).toString().length() == 0){
//							matriz[i][j] = Float.parseFloat("0");
//						}else{
//							matriz[i][j] = Float.parseFloat(tabla_model.getValueAt(i,j).toString().trim());
//						}
///*A Pagar*/			case 26 :
//						if(tabla_model.getValueAt(i,j).toString().length() == 0){
//							matriz[i][j] = Float.parseFloat("0");
//						}else{
//							matriz[i][j] = Float.parseFloat(tabla_model.getValueAt(i,j).toString().trim());
//						}
//											
//						
//						
//						break;
///*Obs D.H.*/			case 27 : matriz[i][j] = tabla_model.getValueAt(i,j).toString().trim();			break;
///*Obs II*/
//					
//				}

			}

		}
		return matriz;
	}
	
	
	public String EmpleadoConNegativo(){
	 String registro="";
	  for(int i=0; i<tabla_model.getRowCount(); i++){
		float descPrest = tabla_model.getValueAt(i, 7).toString().equals("")?0:Float.valueOf(tabla_model.getValueAt(i, 7).toString());
		float corte = tabla_model.getValueAt(i, 17).toString().equals("")?0:Float.valueOf(tabla_model.getValueAt(i, 17).toString());
		float aPagar = tabla_model.getValueAt(i, 26).toString().equals("")?0:Float.valueOf(tabla_model.getValueAt(i, 26).toString());
		
		if(descPrest<0 || corte<0 || aPagar<0){
			registro += ("* "+tabla_model.getValueAt(i, 2).toString().trim()+".....................................................................").substring(0,64)+"    "+(descPrest+"                             ").substring(0,25)+(corte+"                             ").substring(0,25)+aPagar+"\n";
		}
	  }
	 return registro;
	}
	
	public String EmpleadoConSueldoCero(){
	 String registro="";
	  for(int i=0; i<tabla_model.getRowCount(); i++){
		float sueldoCero = tabla_model.getValueAt(i, 4).toString().equals("")?0:Float.valueOf(tabla_model.getValueAt(i, 4).toString());
		if(sueldoCero==0){
			registro+="\n\nSueldos En Cero:\n";
			registro += ("* "+tabla_model.getValueAt(i, 2).toString().trim()+".....................................................................").substring(0,64)+"    "+(sueldoCero+"                             ").substring(0,25)+"\n";
		}
	  }
	  if(registro.equals("")){
		  registro="No se encontraron sueldos en cero";
	  }
	 return registro;
	}
	
	ActionListener op_Borrar_ObservacionDH = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			if(JOptionPane.showConfirmDialog(null, "Si Borran Las Observaciones No Podran Ser Restauradas.\n�Desea Continual?", "Aviso!", JOptionPane.YES_NO_OPTION) == 0){
				if(new ActualizarSQL().Borrar_Observacion_DH()){
					
					tabla_model.setRowCount(0);
					Object[][] Tabla = new Obj_Revision_De_Lista_Raya().get_tabla_model();
					for(Object[] fila : Tabla){
						tabla_model.addRow(fila); 
					}
					
					JOptionPane.showMessageDialog(null, "Las Observaciones Se Han Eliminado Correctamente!!!","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
					return;
				}
			}
		}
	};
	
	ActionListener op_guardar = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
//			trsfiltro.setRowFilter(RowFilter.regexFilter("", 1));
			
			txtNombre_Completo.setText("");
			int[] columnas2 = {1,2,3};
			new Obj_Filtro_Dinamico_Plus(tabla,txtNombre_Completo.getText().toUpperCase(), columnas2);
			new Obj_Filtro_Dinamico_Plus(freezeTable,txtNombre_Completo.getText().toUpperCase(), columnas2);
			
			if(tabla.isEditing()){
				tabla.getCellEditor().stopCellEditing();
			}
			
			if(valida_error() != ""){
				JOptionPane.showMessageDialog(null, "Los siguientes datos son requeridos:\n"+valida_error(),"Error",JOptionPane.ERROR_MESSAGE);
				return;
			}else{
				if(!EmpleadoConNegativo().equals("")||!EmpleadoConSueldoCero().equals("No se encontraron sueldos en cero")){
					txtNombre_Completo.requestFocus();
					JOptionPane.showMessageDialog(null,"\n No Es Posible Guardar Con Empleados Que Tengan Valores Negativos En \n <<Descuento De Prestamo,Descuento De Cortes O En El Total A Pagar>> \n O/Y Empleados Que Tengan Sueldo 0 \nSolo podra Guardar Cerrando La Ventana De Revision De Lista De Raya Y Confirmando Que Quiere Guardar\n\n                          Empleado                                                   Desc_Prest               Cortes              A Pagar\n"+EmpleadoConNegativo()+EmpleadoConSueldoCero(),"Aviso:",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
					return;
				}else{
				
					if(JOptionPane.showConfirmDialog(null, "�Desea Guardar La  Pre Lista De Raya?") == 0){
						Obj_Revision_De_Lista_Raya lista_raya = new Obj_Revision_De_Lista_Raya();
						
						
//						for(int i=0; i<tabla_guardar().length; i++){
//							System.out.println(tabla_guardar()[i][0]);
//							System.out.println(tabla_guardar()[i][1]);
//							System.out.println(tabla_guardar()[i][2]);
//							System.out.println(tabla_guardar()[i][3]);
//							System.out.println(tabla_guardar()[i][4]);
//							System.out.println(tabla_guardar()[i][5]);
//						}
						
						
						if(lista_raya.guardar(tabla_guardar(),new SimpleDateFormat("dd/MM/yyyy").format(txtCalendario.getDate()))){
							tabla_model.setRowCount(0);
							Object[][] Tabla = new Obj_Revision_De_Lista_Raya().get_tabla_model();
							Object[] fila = new Object[tabla_model.getColumnCount()];
							for(int i=0; i<Tabla.length; i++){
								tabla_model.addRow(fila); 
								for(int j=0; j<tabla_model.getColumnCount(); j++){
									tabla_model.setValueAt(Tabla[i][j], i,j);
								}
							}
							
							Obj_Totales_De_Cheque autorizacion = new Obj_Totales_De_Cheque();
								 autorizacion.setAutorizar(false);
								if(autorizacion.actualizar()){
									cargar_autorizaciones();
		
									btn_nomina.setEnabled(true);
									JOptionPane.showMessageDialog(null, "La Revision De La Lista De Raya Se Guardo Exitosamente!!!","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
									return;
							}else{
								JOptionPane.showMessageDialog(null, "Error Al Guardar ", "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
                  		   		 }
						}else{
							JOptionPane.showMessageDialog(null, "Ocurri� un error al intentar guardar la tabla","Error",JOptionPane.ERROR_MESSAGE);
							return;
					     	 }
					}else{
						return;
					    }
				}
			}
		}
	};
	
	ActionListener op_imprimir = new ActionListener(){
		public void actionPerformed(ActionEvent arg0){
			update_autorizar_finanzas();
			cargar_autorizaciones();
			new Cat_Reportes_De_Lista_De_Raya(1).setVisible(true);
		}
	};
	
	ActionListener op_totales_cheque = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			dispose();
			new Cat_Totales_De_Nomina().setVisible(true);
		}
	};
	
	public void update_autorizar_finanzas(){
		String todos = "update tb_autorizaciones set autorizar_finanzas='true' ";
		PreparedStatement pstmt = null;
		Connection con = new Connexion().conexion();
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(todos);
		
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en Cat_Imprimir_Lista_de_Raya  en la funcion [ update_autorizar_finanzas ] SQLException: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			if(con != null){
				try{
					System.out.println("La transacci�n ha sido abortada");
					JOptionPane.showMessageDialog(null, "Error en Cat_Imprimir_Lista_de_Raya  en la funcion [ update_autorizar_finanzas ] SQLException: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en Cat_Imprimir_Lista_de_Raya  en la funcion [ update_autorizar_finanzas ] SQLException: "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
			}
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		

	}
	private Object[][] tabla_guardar(){
		Object[][] matriz = new Object[tabla_model.getRowCount()][6];
		for(int i=0; i<tabla_model.getRowCount(); i++){
			matriz[i][0] = Boolean.parseBoolean(tabla_model.getValueAt(i,0).toString().trim());
			matriz[i][1] = Integer.parseInt(tabla_model.getValueAt(i,1).toString().trim());
			matriz[i][2] = tabla_model.getValueAt(i,3).toString().trim();
			matriz[i][3] = tabla_model.getValueAt(i,26).toString().trim().equals("") ? 0 : Float.parseFloat(tabla_model.getValueAt(i,26).toString().trim());
			matriz[i][4] = tabla_model.getValueAt(i,27).toString().trim();
			matriz[i][5] = tabla_model.getValueAt(i,28).toString().trim();
		}
		return matriz;
	}
	
	public String valida_error(){
		String error="";
			String fechaNull = txtCalendario.getDate()+"";
			if(fechaNull.equals("null"))error+= "Fecha de la lista de raya\n";
		return error;
	}
	
	public void init_component(){
		String fecha = new Obj_Revision_De_Lista_Raya().get_fecha();
		
		if(fecha != ""){
			try {
				Date date = new SimpleDateFormat("dd/MM/yyyy").parse(fecha);
				txtCalendario.setDate(date);
				txtCalendario.setEnabled(false);
			} catch (ParseException e1) {
				e1.printStackTrace();
			}				
			}
	}
	
	MouseListener opTablaFiltroSeleccion = new MouseListener() {
		public void mousePressed(MouseEvent e) {
				btn_imprimir.setEnabled(false);
				btn_nomina.setEnabled(false);
			}
			public void mouseClicked(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
	};
	
//	KeyListener op_filtro_folio = new KeyListener(){
//		@SuppressWarnings("unchecked")
//		public void keyReleased(KeyEvent arg0) {
//			new Obj_Filtro_Dinamico(freezeTable,"Nombre Completo", "","Establecimiento","", "", "", "", "");
//			new Obj_Filtro_Dinamico(tabla,"Nombre Completo", "","Establecimiento","", "", "", "", "");
//		}
//		
//		public void keyTyped(KeyEvent arg0) {
//			char caracter = arg0.getKeyChar();
//			if(((caracter < '0') ||
//				(caracter > '9')) &&
//			    (caracter != KeyEvent.VK_BACK_SPACE)){
//				arg0.consume(); 
//			}	
//		}
//		
//		public void keyPressed(KeyEvent arg0) {}
//	};
	
	KeyListener op_filtro_nombre = new KeyListener(){
		public void keyReleased(KeyEvent arg0) {
			
			int[] columnas2 = {1,2,3};
			new Obj_Filtro_Dinamico_Plus(tabla,txtNombre_Completo.getText().toUpperCase(), columnas2);
			new Obj_Filtro_Dinamico_Plus(freezeTable,txtNombre_Completo.getText().toUpperCase(), columnas2);
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}		
	};
	
	public static void main(String [] arg){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Revision_De_Lista_Raya().setVisible(true);
		}catch(Exception e){	}
	}

}
