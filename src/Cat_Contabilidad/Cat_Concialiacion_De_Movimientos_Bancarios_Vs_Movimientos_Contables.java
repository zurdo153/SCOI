package Cat_Contabilidad;


import java.awt.Container;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Connexion;
import IZAGAR_Obj.Obj_Conciliacion_de_Movimientos_Bancarios_Contra_Contabilidad;
import Obj_Principal.Componentes;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Concialiacion_De_Movimientos_Bancarios_Vs_Movimientos_Contables  extends JDialog{
	
	Container cont = getContentPane();
	JLayeredPane campo = new JLayeredPane();
	Object[][] MatrizFiltro ;
	Object[][] Matriz_Mov_Contabilidad ;
	Object[][] Matriz_Conciliados;
	
	JButton btnActualizar =new JButton("Actualizar",new ImageIcon("imagen/Actualizar.png"));
	 
	JButton btnAgregar = new JButton("Traspaso Por Nombre",new ImageIcon("imagen/double-arrow-icone-3883-16.png"));
	JButton btnAplicar = new JButton("Aplicar Nomina a Depositos Banco",new ImageIcon("imagen/Aplicar.png"));
	JButton btnRemover = new JButton("Remover Conciliado",new ImageIcon("imagen/eliminar-bala-icono-7773-32.png"));
	public JButton btnReporte = new JButton("Reporte Conciliado Nomina",new ImageIcon("imagen/hoja-de-calculo-excel-icono-5223-16.png"));
	
	String CuentasBancarias[] = new Obj_Conciliacion_de_Movimientos_Bancarios_Contra_Contabilidad().Combo_CuentasBancarias();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbCuentasBancarias = new JComboBox(CuentasBancarias);
	
	
	JLabel lblTablaMovBancarios = new JLabel("Movimientos Bancarios");
	JLabel lblTablaMovContabilidad = new JLabel("Movimientos Contabilidad");
	JLabel lblTablaConciliados = new JLabel("Movimientos Conciliados");
	JDateChooser cfecha = new JDateChooser();
	
	JTextField txtBanco = new Componentes().text( new JTextField(), "Nombre Del Banco", 100, "String");
	JTextField txtCuentaContable = new Componentes().text( new JTextField(), "Cuenta Contable", 100, "String");
	
//TABLA MOVIMIENTOS BANCARIOS
	DefaultTableModel modeloFiltro = new DefaultTableModel(null,
            new String[]{"Folio E", "Nombre Empleado SCOI", "Establecimiento","" }
			){
	     @SuppressWarnings("rawtypes")
		Class[] types = new Class[]{
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
        	 	case 3 : return true;
        	 	} 				
 			return false;
 		}
	};
	JTable tablaFiltro = new JTable(modeloFiltro);
    JScrollPane scroll = new JScrollPane(tablaFiltro);
    
  //TABLA MOVIMIENTOS CONTABILIDAD 
	DefaultTableModel modelocontabilidad = new DefaultTableModel(null,
            new String[]{"Conciliar", "Poliza","Tipo","Mes Año","Fecha","Mov","Importe","Referencia","Concepto","Establecimiento"}
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
            new String[]{"Folio Empleado", "Nombre Empleado Nomina","Neto","Establecimiento","Departamento",""}
			){
	
		@SuppressWarnings("rawtypes")
		Class[] types = new Class[]{
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.Boolean.class
                                    };
	    
		@SuppressWarnings({ "unchecked", "rawtypes" })
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
        	 	case 5 : return true;
        	 } 				
 			return false;
 		}
	};
	
    JTable tablaconciliados = new JTable(modeloconciliados);
    JScrollPane scrollconciliados = new JScrollPane(tablaconciliados);

	
	
	//TODO INCIA CONSTRUCTOR
	public Cat_Concialiacion_De_Movimientos_Bancarios_Vs_Movimientos_Contables() {
		this.setModal(true);
		cont.add(campo);
		setSize(1024,768);
		setResizable(false);
		setLocationRelativeTo(null);
		
		lblTablaMovBancarios.setFont(new Font("arial", Font.BOLD, 14));	
		lblTablaMovContabilidad.setFont(new Font("arial", Font.BOLD, 14));	
		lblTablaConciliados.setFont(new Font("arial", Font.BOLD, 14));	
		lblTablaMovBancarios.setForeground(new java.awt.Color(0,0,100));
		lblTablaMovContabilidad.setForeground(new java.awt.Color(0,0,100));
		lblTablaConciliados.setForeground(new java.awt.Color(0,0,100));
		
		setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/filter_icon&16.png"));
		setTitle("Traspaso de Netos de Nomina a Bancos");
		campo.setBorder(BorderFactory.createTitledBorder("Traspaso de Netos a Bancos"));
		

		scroll_Mov_Contabilidad = new JScrollPane(tabla_mov_contabilidad);

		int y=0;
		campo.add(new JLabel ("Cuenta:")).setBounds(15,y+=20,100,20);
		campo.add(cmbCuentasBancarias).setBounds(60,y,100,20);
		campo.add(txtBanco).setBounds(170,y,100,20);
		campo.add(txtCuentaContable).setBounds(300,y,100,20);
		
		
		campo.add(new JLabel ("Fecha:")).setBounds(15,y+=30,100,20);
		campo.add(cfecha).setBounds(60,y,100,20);
        campo.add(btnActualizar).setBounds(170,y,100,20); 		
		campo.add(lblTablaMovBancarios).setBounds(15,y+=30, 300, 35);
    	campo.add(scroll).setBounds(15,y+30,485,250);
    	
    	campo.add(lblTablaMovContabilidad).setBounds(520,y, 300, 35);
		campo.add(scroll_Mov_Contabilidad).setBounds(515,y+30,485,250);
		
		campo.add(lblTablaConciliados).setBounds(15,y+=400,630, 35);
		campo.add(scrollconciliados).setBounds(15,y+30,895,250);
     	
		txtBanco.setEnabled(false);
		txtCuentaContable.setEnabled(false);
		cargar_fecha_Sugerida();
		cargar_datos_cuenta_bancaria();
		
		this.addWindowListener(op_cerrar);
		btnActualizar.addActionListener(opActualizar);
		cmbCuentasBancarias.addActionListener(opBuscar_datoscuenta_bancaria);
		
		
		
		
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
//		campo.add(btnAgregar).setBounds(700,10,195,20);
//		campo.add(btnRemover).setBounds(700, 290,195, 20);
//		
//		campo.add(btnReporte).setBounds(650,560,240,20);
//		campo.add(btnAplicar).setBounds(650,583,240,20);
//		btnAplicar.setEnabled(false);
//		btnAgregar.addActionListener(OpAgregar);
//		btnRemover.addActionListener(OpRemover);
//		guarda_auto_netos_nomina_po_empleado_temp(folio_nomina);
//		funcion para seleccionar solo un registro a la ves en la tabla de SCOI-----------------------------------------------------------
//        tablaFiltro.addMouseListener(opTablaFiltroSeleccion);
//		funcion para seleccionar solo un registro a la ves en la tabla de NOMINA--------------------------------------------------------- 
//        tablanomina.addMouseListener(opTablaNominaSeleccion);
//		funcion para seleccionar solo un registro a la ves en la tabla CONCILIADOS-------------------------------------------------------
//        tablaconciliados.addMouseListener(opTablaConciliadosSeleccion);
	}
	public void cargar_datos_cuenta_bancaria() {
		Obj_Conciliacion_de_Movimientos_Bancarios_Contra_Contabilidad Datos_Cuenta_Bancaria =new Obj_Conciliacion_de_Movimientos_Bancarios_Contra_Contabilidad().buscar_datos_cuenta_Bancaria(cmbCuentasBancarias.getSelectedItem().toString());
		txtBanco.setText(Datos_Cuenta_Bancaria.getBanco().toString());
		txtCuentaContable.setText(Datos_Cuenta_Bancaria.getCuenta_Contable().toString());
	};
	
	public void cargar_fecha_Sugerida(){
			Date date1 = null;
					  try {
						date1 = new SimpleDateFormat("dd/MM/yyyy").parse(new BuscarSQL().fecha(0));
					} catch (ParseException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					}
			cfecha.setDate(date1);
		};
	
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
/////////////// TODO  INICIA TABLA DE MOVIMIENTOS CONTABILIDAD LIMPIAR/ LLENAR/ DIMENCIONES DE COLUMNAS
	//DIMENCIONES
	public void tabla_Mov_Bancarios(){
	    this.tabla_mov_contabilidad.getTableHeader().setReorderingAllowed(false) ;
	    this.tabla_mov_contabilidad.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	    llenado_tabla_mov_bancarios();
	    
	    		tabla_mov_contabilidad.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("CHB","centro","Arial","normal",12));
			    tabla_mov_contabilidad.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
			    tabla_mov_contabilidad.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
			    tabla_mov_contabilidad.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
			    tabla_mov_contabilidad.getColumnModel().getColumn(4).setCellRenderer(new tablaRenderer("fecha","izquierda","Arial","normal",12));
			    
			    tabla_mov_contabilidad.getColumnModel().getColumn(5).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
			    tabla_mov_contabilidad.getColumnModel().getColumn(6).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
			    tabla_mov_contabilidad.getColumnModel().getColumn(7).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
			    tabla_mov_contabilidad.getColumnModel().getColumn(8).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
			    tabla_mov_contabilidad.getColumnModel().getColumn(9).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
				
				
				tabla_mov_contabilidad.getColumnModel().getColumn(0).setMinWidth(20);
			    tabla_mov_contabilidad.getColumnModel().getColumn(0).setMaxWidth(20);
				tabla_mov_contabilidad.getColumnModel().getColumn(1).setMinWidth(45);
				tabla_mov_contabilidad.getColumnModel().getColumn(1).setMaxWidth(45);
				tabla_mov_contabilidad.getColumnModel().getColumn(2).setMinWidth(30);
				tabla_mov_contabilidad.getColumnModel().getColumn(2).setMaxWidth(30);
				tabla_mov_contabilidad.getColumnModel().getColumn(3).setMinWidth(55);
				tabla_mov_contabilidad.getColumnModel().getColumn(3).setMaxWidth(55);
				tabla_mov_contabilidad.getColumnModel().getColumn(4).setMinWidth(85);
				tabla_mov_contabilidad.getColumnModel().getColumn(4).setMaxWidth(130);
				
				tabla_mov_contabilidad.getColumnModel().getColumn(5).setMinWidth(30);
				tabla_mov_contabilidad.getColumnModel().getColumn(5).setMaxWidth(30);
				tabla_mov_contabilidad.getColumnModel().getColumn(6).setMinWidth(70);		
				tabla_mov_contabilidad.getColumnModel().getColumn(6).setMaxWidth(130);
				tabla_mov_contabilidad.getColumnModel().getColumn(7).setMinWidth(70);
				tabla_mov_contabilidad.getColumnModel().getColumn(7).setMaxWidth(130);
				tabla_mov_contabilidad.getColumnModel().getColumn(8).setMinWidth(450);
				tabla_mov_contabilidad.getColumnModel().getColumn(8).setMaxWidth(850);
				tabla_mov_contabilidad.getColumnModel().getColumn(9).setMinWidth(40);
				tabla_mov_contabilidad.getColumnModel().getColumn(9).setMaxWidth(40);

	};
	//BORRADO Y LLENADO
	public void llenado_tabla_mov_bancarios(){
	  while(tabla_mov_contabilidad.getRowCount()>0){	modelocontabilidad.removeRow(0);	}
			Object[][] getTablaNomina = Consulta_para_llenar_Mov_Contabilidad();
			Object[] fila = new Object[10];
		     for(int i=0; i<getTablaNomina.length; i++){
		             fila[0] = getTablaNomina[i][0];
		             fila[1] = getTablaNomina[i][1]+"";
		             fila[2] = getTablaNomina[i][2]+"";
		             fila[3] = getTablaNomina[i][3]+"";
		             fila[4] = getTablaNomina[i][4]+"";
		             fila[5] = getTablaNomina[i][5]+"";
		             fila[6] = getTablaNomina[i][6]+"";
		             fila[7] = getTablaNomina[i][7]+"";
		             fila[8] = getTablaNomina[i][8]+"";
		             fila[9] = getTablaNomina[i][9]+"";
		             modelocontabilidad.addRow(fila); }
	}
	
	////CONSULTA
	public Object[][] Consulta_para_llenar_Mov_Contabilidad(){
		
		String fecha  = new SimpleDateFormat("dd/MM/yyyy").format(cfecha.getDate())+" 00:00:00";
		
		String consulta = "DECLARE	   @FI smalldatetime,@FF smalldatetime,@Cuenta varchar(1024) SET @FI='Ene  8 2010 12:00:00:000AM' SET @FF='"+fecha+"' SET @Cuenta='"+txtCuentaContable.getText().toString()+"'"
				      + "  SELECT       'false' conciliar, mp.folio AS poliza, mp.tipo_poliza, mp.mes_año, convert(varchar(20),mp.fecha_poliza,103)+' '+convert(varchar(20),mp.fecha_poliza,108) as fecha_poliza, mp.cargo_abono AS tipo_movimiento, convert(numeric(19,2), mp.importe) as importe,ISNULL(pp.folio_documento_pago, mp.referencia) AS referencia, mp.concepto, mp.cod_estab"
				      + "  , mp.id, CASE WHEN isnull(pp.tipo_cambio, 0) = 0 THEN 1 ELSE pp.tipo_cambio END AS tipo_cambio "
				      + "    FROM    mpolizas_contables AS mp WITH (nolock) LEFT OUTER JOIN "
				      +"   pagos_proveedores AS pp WITH (nolock) ON pp.poliza_contable = mp.folio AND pp.tipo_poliza_contable = mp.tipo_poliza AND "
				      +"                 pp.mesaño_poliza_contable = mp.mes_año"
				      + " WHERE        (mp.fecha_poliza BETWEEN @FI AND @FF) AND (mp.status = 'V') "
				      + "        AND (RTRIM(mp.cuenta_contable) + RTRIM(mp.subcuenta_contable) + RTRIM(mp.subsubcuenta_contable) = @Cuenta)"
				      + "        AND (CONVERT(varchar(15), mp.id) NOT IN (SELECT  id_mpolizas  FROM    movimientos_bancos "
				      + "                                                 WHERE (id_mpolizas IS NOT NULL))) AND (mp.id_movimientos_bancarios IS NULL) AND (mp.fecha_conciliacion_bancos IS NULL) ";
   		
//   		String consulta="select 'false','c003','E','072015','08/07/2015 09:08:00','A,','25296.00','117321','PAGO DE VACACIONES A EMPLEADO: BEJARANO RIOS ROBERTO CARLOS','2','783135332'";
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
		}
	    return Matriz_Mov_Contabilidad; 
	}
///////////////////////////////////////////////////////////////////FIN TABLA MOVIMIENTOS CONTABILIDAD//////////////////////////////////////////////////////////////////////////////////////////////////	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	

	ActionListener opActualizar = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			   String fechaNull = cfecha.getDate()+"";
			   if(fechaNull.equals("null")){
					JOptionPane.showMessageDialog(null,"Necesita Selecionar una Fecha o la Fecha tecleada es Incorrecta","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
					return;
				   }else{
					   tabla_Mov_Bancarios();
				   }
		   }
		};
		
	ActionListener opBuscar_datoscuenta_bancaria = new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				cargar_datos_cuenta_bancaria();
			}
			};

			
    WindowListener op_cerrar = new WindowListener() {
		public void windowOpened(WindowEvent e) {}
		public void windowIconified(WindowEvent e) {}
		public void windowDeiconified(WindowEvent e) {}
		public void windowDeactivated(WindowEvent e) {}
		public void windowClosing(WindowEvent e) {
//			if(JOptionPane.showConfirmDialog(null, "¿Desea guardar antes de cerrar?", "Aviso!", JOptionPane.YES_NO_OPTION) == 0){
//				new Obj_IZAGAR_Netos_Nominas().update_IZAGAR_netos_de_nomina_por_empleado_pre_conciliados();
//			}
		}
		public void windowClosed(WindowEvent e) {}
		public void windowActivated(WindowEvent e) {}
	};
	

	
	
	
		
		
		
		
	MouseListener opTablaFiltroSeleccion = new MouseListener() {
		public void mousePressed(MouseEvent e) {
			int fila = tablaFiltro.getSelectedRow();
			int columna = tablaFiltro.getSelectedColumn();
			
			if(columna==3){
				for(int i=0; i<=tablaFiltro.getRowCount()-1; i++){
					tablaFiltro.setValueAt(false, i, 3);
				}
				tablaFiltro.setValueAt(true, fila, columna);
			}
		}
		public void mouseClicked(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
	};
	
//	MouseListener opTablaNominaSeleccion = new MouseListener() {
//		public void mousePressed(MouseEvent e) {
//			int fila = tablanomina.getSelectedRow();
//			int columna = tablanomina.getSelectedColumn();
//			
//			if(columna==3){
//				for(int i=0; i<=tablanomina.getRowCount()-1; i++){
//					tablanomina.setValueAt(false, i, 3);
//				}
//				tablanomina.setValueAt(true, fila, columna);
//			}
//		}
//		public void mouseClicked(MouseEvent e) {}
//		public void mouseEntered(MouseEvent e) {}
//		public void mouseExited(MouseEvent e) {}
//		public void mouseReleased(MouseEvent e) {}
//	};
	
	MouseListener opTablaConciliadosSeleccion = new MouseListener() {
		public void mousePressed(MouseEvent e) {
			int fila = tablaconciliados.getSelectedRow();
			int columna = tablaconciliados.getSelectedColumn();
			
			if(columna==5){
				for(int i=0; i<=tablaconciliados.getRowCount()-1; i++){
					tablaconciliados.setValueAt(false, i, 5);
				}
				tablaconciliados.setValueAt(true, fila, columna);
			}
		}
		public void mouseClicked(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
	};
	
  //GUARDADO AUTOMATICO DE LA NOMINA TECLEADA//  
//  public void guarda_auto_netos_nomina_po_empleado_temp(String folio_nomina){
//				
//			if(tablaFiltro.isEditing()){
//	 			tablaFiltro.getCellEditor().stopCellEditing();
//			}
//			
//			Obj_IZAGAR_Netos_Nominas guardar_netos_nomina = new Obj_IZAGAR_Netos_Nominas();
//							if(guardar_netos_nomina.guardar_netos_nominas_temp(tabla_guardar_nomina_temp())){
//							System.out.println("se guardo AUTO");
//							
//							while(tablanomina.getRowCount()>0){
//								modelonomina.removeRow(0); }
//							preconciliacion_automatica( folio_nomina);
//							
//							
////							llenado tabla exportacion de bm completa despues de guardarlos en scoi
//							Object[][] getTablaNomina = getTablanetosnomina_guardados_scoi(folio_nomina);
//							Object[] fila = new Object[4];
////							 vuelve a llenar tablanomina desde el scoi
//					         for(int i=0; i<getTablaNomina.length; i++){
//					                 fila[0] = getTablaNomina[i][0]+"";
//					                 fila[1] = getTablaNomina[i][1]+"";
//					                 fila[2] = getTablaNomina[i][2]+"";
////					                 fila[3] = Boolean.valueOf(getTablaNomina[i][3].toString().trim());
//					                 fila[3] = false;
//					                 modelonomina.addRow(fila);}
//					         
//					        RefreshTablas(folio_nomina);
//
//						}else{
//							JOptionPane.showMessageDialog(null, "Ocurrió un error al intentar guardar la tabla liquidaciones","Error",JOptionPane.ERROR_MESSAGE);
//							return;
//						}
//	};
	
	public void RefreshTablas(String folio_nom){
        
		while(tablaFiltro.getRowCount()>0){
			modeloFiltro.removeRow(0); }
		
		while(tablaconciliados.getRowCount()>0){
			modeloconciliados.removeRow(0); }
		
// 		llenado tabla conciliados
     	Object[][] getTablaconciliados = getTablaconciliados(folio_nom);
     	Object[] fila2 = new Object[5];
//		 llenar tabla conciliados
        for(int i=0; i<getTablaconciliados.length; i++){
                fila2[0] = getTablaconciliados[i][0]+"";
                fila2[1] = getTablaconciliados[i][1]+"";
                fila2[2] = getTablaconciliados[i][2]+"";
                fila2[3] = getTablaconciliados[i][3]+"";
                fila2[4] = getTablaconciliados[i][4]+"";
//                fila2[5] = Boolean.valueOf(getTablaNomina[i][5].toString().trim());
                modeloconciliados.addRow(fila2);} 
        
        
// 		llenado tabla faltantes de conciliar
//     	Object[][] getTablaFiltro = getTablaempleadoscoi(folio_nom);
//     	Object[] fila1 = new Object[4];
////		 llenar tabla empleado scoi
//        for(int i=0; i<getTablaFiltro.length; i++){
//                fila1[0] = getTablaFiltro[i][0]+"";
//                fila1[1] = getTablaFiltro[i][1]+"";
//                fila1[2] = getTablaFiltro[i][2]+"";
////                fila1[3] = Boolean.valueOf(getTablaNomina[i][3].toString().trim());
//                fila1[3] = false;
                
//                modeloFiltro.addRow(fila1); 
                }
        
		
	
//	private Object[][] tabla_guardar_nomina_temp(){
//
//		Object[][] matriz = new Object[tablanomina.getRowCount()][3];
//		for(int i=0; i<tablanomina.getRowCount(); i++){
//			
//				matriz[i][0] = modelonomina.getValueAt(i,0).toString().trim();
//				matriz[i][1] = modelonomina.getValueAt(i,1).toString().trim();
//				matriz[i][2] = modelonomina.getValueAt(i,2).toString().trim();
//		}
//		return matriz;
//	}
	

	public Object[][] getTablaempleadoscoi(String folio_nomina){
		String todos = "exec IZAGAR_select_empleados_scoi_traspaso_depositos_bancos_de_nomina '"+folio_nomina+"'";
		Statement s;
		ResultSet rs;
		try {
			s = new Connexion().conexion().createStatement();
			rs = s.executeQuery(todos);
					
			MatrizFiltro = new Object[getFilasSCOI(todos)][4];
			int i=0;
			while(rs.next()){
				MatrizFiltro[i][0] = "   "+rs.getString(1).trim();
				MatrizFiltro[i][1] = "   "+rs.getString(2).trim();
				MatrizFiltro[i][2] = "   "+rs.getString(3).trim();
				MatrizFiltro[i][3] = Boolean.valueOf(rs.getString(4).trim());
				i++;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	    return MatrizFiltro; 
	}
	 	public int getFilasSCOI(String qry){
			int filas=0;
			Statement stmt = null;
			try {stmt = new Connexion().conexion().createStatement();
				ResultSet rs = stmt.executeQuery(qry);
				while(rs.next()){filas++;}
			} catch (SQLException e1) {	e1.printStackTrace();}
			return filas;
			}	
   	
   	

   	
   	
   	
   	public int getFilasIZAGAR(String qry){
		int filas=0;
		Statement stmt = null;
		try {stmt = new Connexion().conexion_IZAGAR().createStatement();
			ResultSet rs2 = stmt.executeQuery(qry);
			while(rs2.next()){filas++;}
		} catch (SQLException e1) {	e1.printStackTrace();}
		return filas;
	}
   	
  	
   	
   	public Object[][] getTablanetosnomina_guardados_scoi(String folio_nomina){
		String todos = "exec IZAGAR_select_empleados_netos_nomina_scoi_temp '"+folio_nomina+"'";
		Statement s;
		ResultSet rs;
		try {
			s = new Connexion().conexion().createStatement();
			rs = s.executeQuery(todos);
			System.out.println("Carga de netos a Tabla temp:"+folio_nomina);
			
			MatrizFiltro = new Object[getFilasSCOI_temp(todos)][4];
			int i=0;
			while(rs.next()){
				MatrizFiltro[i][0] = "   "+rs.getString(1).trim();
				MatrizFiltro[i][1] = "   "+rs.getString(2).trim();
				MatrizFiltro[i][2] = "   "+rs.getString(3).trim();
//				MatrizFiltro[i][3] = Boolean.valueOf(rs.getString(4).trim());
				MatrizFiltro[i][3] = false;
				i++;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	    return MatrizFiltro; 
	}
	 	public int getFilasSCOI_temp(String qry){
			int filas=0;
			Statement stmt = null;
			try {stmt = new Connexion().conexion().createStatement();
				ResultSet rs = stmt.executeQuery(qry);
				while(rs.next()){filas++;}
			} catch (SQLException e1) {	e1.printStackTrace();}
			return filas;
			}
	 	
/////////DATOS TABLA CONCILIADOS
	   	public Object[][] getTablaconciliados(String folio_nomina){
	   		
			String todos = "exec IZAGAR_select_empleados_scoi_pre_conciliados '" +folio_nomina+"'";
			Statement s;
			ResultSet rs;

			try {
				s = new Connexion().conexion().createStatement();
				rs = s.executeQuery(todos);
				Matriz_Conciliados = new Object[getFilasConciliados(todos)][6];
				int i=0;
				while(rs.next()){
					Matriz_Conciliados[i][0] = "   "+rs.getString(1).trim();
					Matriz_Conciliados[i][1] = "   "+rs.getString(2).trim();
					Matriz_Conciliados[i][2] = "   "+rs.getString(3).trim();
					Matriz_Conciliados[i][3] = "   "+rs.getString(4).trim();
					Matriz_Conciliados[i][4] = "   "+rs.getString(5).trim();
					Matriz_Conciliados[i][5] = false;
									i++;
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en CAT_IZAGAR_Pasar_Netos_De_Nomina_A_Bancos  en la funcion getTablaconciliados  procedimiento almacenado IZAGAR_select_empleados_scoi_pre_conciliados SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			}
		    return Matriz_Conciliados; 
		}
	 	public int getFilasConciliados(String qry){
			int filas=0;
			Statement stmt = null;
			try {stmt = new Connexion().conexion().createStatement();
				ResultSet rs = stmt.executeQuery(qry);
				while(rs.next()){filas++;}
			} catch (SQLException e1) {	e1.printStackTrace();}
			return filas;
			}
	 	

/////////////PRE CONCILIACION AUTOMATICA
	 	public void preconciliacion_automatica(String folio_nomina){
			String todos = "exec IZAGAR_traspaso_automatico_a_empleados_pre_conciliados_los_netos_de_nomina '"+folio_nomina+"'";
			PreparedStatement pstmt = null;
			Connection con = new Connexion().conexion();
			try {
				con.setAutoCommit(false);
				pstmt = con.prepareStatement(todos);
			
				pstmt.executeUpdate();
				con.commit();
			} catch (Exception e) {
				System.out.println("SQLException: "+e.getMessage());
				if(con != null){
					try{
						System.out.println("La transacción ha sido abortada");
						con.rollback();
					}catch(SQLException ex){
						System.out.println(ex.getMessage());
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
	 	
//	 	ActionListener OpAgregar = new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				
//				boolean filaSeleccionadaSCOI=false;
//				int numeroFilaSCOI=0;
//				
//				boolean filaSeleccionadaNOMINA=false;
//				int numeroFilaNOMINA=0;
//				
//				for(int i=0; i<tablaFiltro.getRowCount(); i++){
//					if(tablaFiltro.getValueAt(i, 3).toString().trim().equals("true")){
//						filaSeleccionadaSCOI=true;
//						numeroFilaSCOI=i;
//						break;
//					}
//				}
//				
//				for(int i=0; i<tablanomina.getRowCount(); i++){
//					if(tablanomina.getValueAt(i, 3).toString().trim().equals("true")){
//						filaSeleccionadaNOMINA=true;
//						numeroFilaNOMINA=i;
//						break;
//					}
//				}
//				
//				if(filaSeleccionadaSCOI && filaSeleccionadaNOMINA){
//					
//					int folio_empleado = Integer.valueOf(tablaFiltro.getValueAt(numeroFilaSCOI, 0).toString().trim());
//					int nomina = Integer.valueOf(tablanomina.getValueAt(numeroFilaNOMINA, 0).toString().trim());
//					float neto = Float.valueOf(tablanomina.getValueAt(numeroFilaNOMINA, 2).toString().trim());
//
//					if(new Obj_IZAGAR_Netos_Nominas().guardar_netos_nominas_temp_individual(folio_empleado, nomina, neto)){
//						RefreshTablas(nomina+"");
//						for(int i=0; i<=tablanomina.getRowCount()-1; i++){
//							tablanomina.setValueAt(false, i, 3);
//						}
//						JOptionPane.showMessageDialog(null,"El Registro Se Guardo Exitosamente", "Aviso", JOptionPane.INFORMATION_MESSAGE);
//						return;
//					}else{
//						JOptionPane.showMessageDialog(null,"El Registro No Se Guardo", "Aviso", JOptionPane.INFORMATION_MESSAGE);
//						return;
//					}
//				}else{
//					JOptionPane.showMessageDialog(null,"Debe Seleccionar Un Registro En Tabla SCOI\n y Uno De La Tabla NOMINA", "Aviso", JOptionPane.INFORMATION_MESSAGE);
//					return;
//				}
//			}
//		};
		
//	 	ActionListener OpRemover = new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				
//				boolean filaSeleccionadaConciliados=false;
//				int numeroFilaConciliados=0;
//				
//				for(int i=0; i<tablaconciliados.getRowCount(); i++){
//					if(tablaconciliados.getValueAt(i, 5).toString().trim().equals("true")){
//						filaSeleccionadaConciliados=true;
//						numeroFilaConciliados=i;
//						break;
//					}
//				}
//				
//				if(filaSeleccionadaConciliados){
//					
//					int folio_empleado = Integer.valueOf(tablaconciliados.getValueAt(numeroFilaConciliados, 0).toString().trim());
//					int nomina = Integer.valueOf(tablanomina.getValueAt(0, 0).toString().trim());
////					float neto = 0;Float.valueOf(tablanomina.getValueAt(numeroFilaConciliados, 2).toString().trim());
//
//					if(new Obj_IZAGAR_Netos_Nominas().remover_netos_nominas_temp_individual(folio_empleado,nomina)){
//						RefreshTablas(nomina+"");
//						for(int i=0; i<=tablanomina.getRowCount()-1; i++){
//							tablanomina.setValueAt(false, i, 3);
//						}
//						JOptionPane.showMessageDialog(null,"El Registro Se Removio Con Exito", "Aviso", JOptionPane.INFORMATION_MESSAGE);
//						return;
//					}else{
//						JOptionPane.showMessageDialog(null,"El Registro No Pudo Ser Removido", "Aviso", JOptionPane.INFORMATION_MESSAGE);
//						return;
//					}
//				}else{
//					JOptionPane.showMessageDialog(null,"Debe Seleccionar Un Registro En La Tabla CONCILIADOS", "Aviso", JOptionPane.INFORMATION_MESSAGE);
//					return;
//				}
//			}
//		};
		public static void main(String args[]){
			try{
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				new Cat_Concialiacion_De_Movimientos_Bancarios_Vs_Movimientos_Contables().setVisible(true);
			}catch(Exception e){	}
		}
}