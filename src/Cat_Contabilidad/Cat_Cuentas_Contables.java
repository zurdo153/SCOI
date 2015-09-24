package Cat_Contabilidad;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.BuscarTablasModel;
import Conexiones_SQL.Cargar_Combo;
import Conexiones_SQL.Connexion;
import Conexiones_SQL.GuardarSQL;
import Obj_Contabilidad.Obj_Cuentas_Contables;
import Obj_Principal.Componentes;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_Filtro_Dinamico;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Cuentas_Contables extends JFrame{
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	String status[] = {"Vigente","Cancelado"};
	String naturaleza[] = {"Acreedora","Deudora"};
	String grupo[] = {"Activo","Capital","Cuentas de Orden","Pasivo","Resultados"};
	
	
	///TODO DECLARACION DE CUENTAS CONTABLES
	Object[][] Matriz_Cuenta_Contable ;
	Object[][] Matriz_Subcuenta_Contable ;
	Object[][] Matriz_Sub_Subcuenta_Contable ;
	
//TODO DECLARACION COMPONENTES PARA CUENTAS-----------------------------------------------------------------------------------------------------------------------------------------------------------
	JTextField txtFolio_Cuenta_Contable = new Componentes().text(new JCTextField(), "Folio Cuenta", 8, "String");
	JTextField txtCuenta_Contable = new Componentes().text(new JCTextField(), "Nombre De La Cuenta Contable", 100, "String");
	JTextField txtFolio_Cuenta_Contablebusqueda = new Componentes().text(new JCTextField(), "Folio Cuenta", 8, "String");
	JTextField txtCuenta_Contablebusqueda = new Componentes().text(new JCTextField(), "Teclee El Nombre De La Cuenta Contable Para La Busqueda", 100, "String");
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbNaturaleza_Cuenta_Contable = new JComboBox(naturaleza);
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbGrupo_Cuenta_Contable = new JComboBox(grupo);
	
	String clasificacion[] = new Obj_Cuentas_Contables().Combo_clasificacion_contable();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbClasif_Cuenta_Contable = new JComboBox(clasificacion);
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbStatus_Cuenta_Contable = new JComboBox(status);
	
	public String[] establecimiento(){try {return new Cargar_Combo().EstablecimientoPoliza();} catch (SQLException e) {e.printStackTrace();}return null;}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbEstablecimiento = new JComboBox(establecimiento());
	
	JButton btnNuevoCC = new JButton("Nuevo",new ImageIcon("imagen/Nuevo.png"));
	JButton btnEditarCC = new JButton("Editar",new ImageIcon("imagen/editara.png"));
	JButton btnDeshacerCC = new JButton("Deshacer",new ImageIcon("imagen/deshacer16.png"));
	JButton btnGuardarCC = new JButton("Guardar",new ImageIcon("imagen/Guardar.png"));
	
	DefaultTableModel modeloCuenta_Contable = new DefaultTableModel(null,
            new String[]{"Folio", "Cuenta","Naturaleza","Grupo","Clasificacion","Estatus" }
			){
	     @SuppressWarnings("rawtypes")
		Class[] types = new Class[]{
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class
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
        	 	} 				
 			return false;
 		}
	};
	JTable tabla_cuentas_contables = new JTable(modeloCuenta_Contable);
    JScrollPane scroll = new JScrollPane(tabla_cuentas_contables);
    
    int ancho = 0;
    
//TODO DECLARACION COMPONENTES PARA SUBCUENTAS-----------------------------------------------------------------------------------------------------------------------------------------------------------
	JButton btnNuevoSC = new JButton("Nuevo",new ImageIcon("imagen/Nuevo.png"));
	JButton btnEditarSC = new JButton("Editar",new ImageIcon("imagen/editara.png"));
	JButton btnDeshacerSC = new JButton("Deshacer",new ImageIcon("imagen/deshacer16.png"));
	JButton btnGuardarSC = new JButton("Guardar",new ImageIcon("imagen/Guardar.png"));
	
	JTextField txtFolio_Cuenta = new Componentes().text(new JCTextField(), "Folio Cuenta", 8, "String");
	JTextField txtFolio_SC = new Componentes().text(new JCTextField(), "Folio Subcuenta", 8, "String");
	JTextField txtSC = new Componentes().text(new JCTextField(), "Nombre De La Subcuenta", 100, "String");
	
    JTextField txtFolio_SCFiltro = new Componentes().text(new JCTextField(), "Folio Subcuenta", 8, "String");
	JTextField txtSCFiltro = new Componentes().text(new JCTextField(), "Nombre De La Subcuenta", 100, "String");
	
	public String[] transaccion(){try {return new Cargar_Combo().cuentas();} catch (SQLException e) {e.printStackTrace();}return null;}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbTransaccion = new JComboBox(transaccion());
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbStatus_SC = new JComboBox(status);
    
	DefaultTableModel modeloSub_Cuenta_Contable = new DefaultTableModel(null,
            new String[]{"Folio Cuenta", "Folio SubCuenta", "SubCuenta","Transaccion","Estatus" }
			){
	     @SuppressWarnings("rawtypes")
		Class[] types = new Class[]{
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class
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
        	 	} 				
 			return false;
 		}
	};
	JTable tablaSub_Cuenta_Contable = new JTable(modeloSub_Cuenta_Contable);
    JScrollPane scrollSub_Cuenta_Contable = new JScrollPane(tablaSub_Cuenta_Contable);
    
//TODO DECLARACION COMPONENTES PARA SUB-SUBCUENTAS-----------------------------------------------------------------------------------------------------------------------------------------------------------
	JButton btnNuevoSSC = new JButton("Nuevo",new ImageIcon("imagen/Nuevo.png"));
	JButton btnEditarSSC = new JButton("Editar",new ImageIcon("imagen/editara.png"));
	JButton btnDeshacerSSC = new JButton("Deshacer",new ImageIcon("imagen/deshacer16.png"));
	JButton btnGuardarSSC = new JButton("Guardar",new ImageIcon("imagen/Guardar.png"));
	
	JTextField txtFolio_Cuenta2 = new Componentes().text(new JCTextField(), "Folio Cuenta", 8, "String");
	JTextField txtFolio_SC2 = new Componentes().text(new JCTextField(), "Folio Subcuenta", 8, "String");
	JTextField txtFolio_SSC = new Componentes().text(new JCTextField(), "Folio Sub-Subcuenta", 8, "String");
	JTextField txtSSC = new Componentes().text(new JCTextField(), "Nombre De La Sub-Subcuenta", 100, "String");
	
    JTextField txtFolio_SSCFiltro = new Componentes().text(new JCTextField(), "Folio Sub-Subcuenta", 8, "String");
	JTextField txtSSCFiltro = new Componentes().text(new JCTextField(), "Nombre De La Sub-Subcuenta", 100, "String");
	
	public String[] transaccionSSC(){try {return new Cargar_Combo().cuentas();} catch (SQLException e) {e.printStackTrace();}return null;}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbTransaccionSSC = new JComboBox(transaccionSSC());
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbStatus_SSC = new JComboBox(status);
    
DefaultTableModel modelo_Sub_Sub_Cuenta_Contable = new DefaultTableModel(null,
            new String[]{"Folio Cuenta", "Folio Subcuenta", "Folio_Sub_Subcuenta", "Sub Subcuenta", "Folio Transaccion", "Status"}
			){
	     @SuppressWarnings("rawtypes")
		Class[] types = new Class[]{
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class
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
        	 	} 				
 			return false;
 		}
	};
	JTable tabla_Sub_Sub_Cuenta_Contable = new JTable(modelo_Sub_Sub_Cuenta_Contable);
    JScrollPane scroll_Sub_Sub_Cuenta_Contable = new JScrollPane(tabla_Sub_Sub_Cuenta_Contable);
    
     ///TODO CONSTRUCTOR	
	public Cat_Cuentas_Contables(){
		ancho = Toolkit.getDefaultToolkit().getScreenSize().width;
		int alto = Toolkit.getDefaultToolkit().getScreenSize().height;
		alto=alto-40;
		cont.add(panel);
		setSize(1024,alto);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Catalogo de Cuentas Contables");
		setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/ok-lista-de-verificacion-icono-7906-64.png"));
		panel.setBorder(BorderFactory.createTitledBorder("Cuentas Contables"));
		scroll =new JScrollPane(tabla_cuentas_contables);

		int x=10,y=25,l=100,a=20;
//TODO COORDENADAS DE COMPONENETES DE CUENTAS --------------------------------------------------------------------------------------------------------------------------------------------------------
		panel.add(new JLabel("Cuenta Contable:")).setBounds(x,y,l,a);
		panel.add(txtFolio_Cuenta_Contable).setBounds(x+=85, y, l-50, a);
		panel.add(txtCuenta_Contable).setBounds(x+=50, y, l*3, a);
		panel.add(new JLabel("Naturaleza:")).setBounds(x+=310,y,l-40,a);
		panel.add(cmbNaturaleza_Cuenta_Contable).setBounds(x+=55, y, l-20, a);
		panel.add(new JLabel("Grupo:")).setBounds(x+=85,y,l-40,a);
		panel.add(cmbGrupo_Cuenta_Contable).setBounds(x+=35, y, l, a);
		panel.add(new JLabel("Clasificacion:")).setBounds(x+=105,y,l-30,a);
		panel.add(cmbClasif_Cuenta_Contable).setBounds(x+=60, y, l, a);
		panel.add(new JLabel("Estatus:")).setBounds(x+=105,y,l-30,a);
		panel.add(cmbStatus_Cuenta_Contable).setBounds(x+=40, y, l-35, a);
		x=10;
		panel.add(txtFolio_Cuenta_Contablebusqueda).setBounds(x, y+=25, l-40, a);
		panel.add(txtCuenta_Contablebusqueda).setBounds(x+=60, y, l*3-50, a);
		
		panel.add(new JLabel("Establecimiento:")).setBounds(x+=385,y-3,l-10,a);
		panel.add(cmbEstablecimiento).setBounds(x+=90, y-3, l+50, a);

		x=10;
		panel.add(scroll).setBounds(x, y+=20, 850, alto/3-85);
		panel.add(btnNuevoCC).setBounds(x+=860, y, l, a);
		panel.add(btnEditarCC).setBounds(x,y+=25, l, a);
		panel.add(btnDeshacerCC).setBounds(x,y+=25, l, a);
		panel.add(btnGuardarCC).setBounds(x,y+=25, l, a);
		
//TODO COORDENADAS DE COMPONENETES DE SUBCUENTA-------------------------------------------------------------------------------------------------------------------------------------------------------
		x=10;
		panel.add(new JLabel("Folio Cuenta:")).setBounds(x,y+=(alto/3-140),l,a);
		panel.add(txtFolio_Cuenta).setBounds(x+=65, y, l-50, a);
		
		panel.add(new JLabel("Subcuenta:")).setBounds(x+=60,y,l,a);
		panel.add(txtFolio_SC).setBounds(x+=60, y, l-50, a);
		panel.add(txtSC).setBounds(x+=50, y, l*3, a);
		
		panel.add(new JLabel("Transaccion:")).setBounds(x+=320,y,l-10,a);
		panel.add(cmbTransaccion).setBounds(x+=73, y, l, a);
		panel.add(new JLabel("Estatus:")).setBounds(x+=120,y,l-40,a);
		panel.add(cmbStatus_SC).setBounds(x+=56, y, l, a);
		
		x=105;
		panel.add(txtFolio_SCFiltro).setBounds(x-13, y+=25, l-20, a);
		panel.add(txtSCFiltro).setBounds(x+=67, y, l*3-55, a);
		
		x=10;
		panel.add(scrollSub_Cuenta_Contable).setBounds(x, y+=20, 610, alto/3-85);
		
		panel.add(btnNuevoSC).setBounds(x+=620, y, l, a);
		panel.add(btnEditarSC).setBounds(x,y+=25, l, a);
		panel.add(btnDeshacerSC).setBounds(x,y+=25, l, a);
		panel.add(btnGuardarSC).setBounds(x,y+=25, l, a);
		
//TODO COORDENADAS DE COMPONENETES DE SUB-SUBCUENTA---------------------------------------------------------------------------------------------------------------------------------------------
		
		x=10;
		panel.add(new JLabel("Folio Cuenta:")).setBounds(x,y+=(alto/4-80),l,a);
		panel.add(txtFolio_Cuenta2).setBounds(x+=65, y, l-50, a);
		
		panel.add(new JLabel("Folio Subcuenta:")).setBounds(x+=60,y,l,a);
		panel.add(txtFolio_SC2).setBounds(x+=80, y, l-50, a);
		
		panel.add(new JLabel("Sub-Subcuenta:")).setBounds(x+=60,y,l,a);
		panel.add(txtFolio_SSC).setBounds(x+=80, y, l-50, a);
		panel.add(txtSSC).setBounds(x+=50, y, l*3, a);
		
		panel.add(new JLabel("Transaccion:")).setBounds(x+=310,y,l-10,a);
		panel.add(cmbTransaccionSSC).setBounds(x+=63, y, l, a);
		panel.add(new JLabel("Estatus:")).setBounds(x+=110,y,l-40,a);
		panel.add(cmbStatus_SSC).setBounds(x+=41, y, l-20, a);
		
		x=205;
		panel.add(txtFolio_SSCFiltro).setBounds(x-15, y+=25, l+20, a);
		panel.add(txtSSCFiltro).setBounds(x+=105, y, l*3-50, a);

		x=10;
		panel.add(scroll_Sub_Sub_Cuenta_Contable).setBounds(x, y+=20, 750, alto/3-85);
		panel.add(btnNuevoSSC).setBounds(x+=760, y, l, a);
		panel.add(btnEditarSSC).setBounds(x,y+=25, l, a);
		panel.add(btnDeshacerSSC).setBounds(x,y+=25, l, a);
		panel.add(btnGuardarSSC).setBounds(x,y+=25, l, a);
		
		
//	CUENTAS----------------------------------------------------
		componentes_cuentas_contables(false);
		tabla_Cuentas_Contables();
//	SUBCUENTA--------------------------------------------------	
		componentes_subcuentas(false);
		tabla_Subcuentas_Contables();
//	SUBSUBCUENTA-----------------------------------------------
		componentes_sub_subcuentas(false);
		tabla_Sub_Subcuentas_Contables();
		
		
//   CUENTA-------------------------------------------------------------------------------------------------------------
		tabla_cuentas_contables.addMouseListener(opCuenta);
		
		btnNuevoCC.addActionListener(nuevo_cuentas_contables);
		btnEditarCC.addActionListener(editar_cuentas_contables);
		btnDeshacerCC.addActionListener(deshacer_cuentas_contables);
        btnGuardarCC.addActionListener(guardar_cuentas_contables);
        
        txtFolio_Cuenta_Contablebusqueda.addKeyListener(opFiltroCC);
        txtCuenta_Contablebusqueda.addKeyListener(opFiltroCC);
        
//   SUBCUENTA----------------------------------------------------------------------------------------------------------
        tablaSub_Cuenta_Contable.addMouseListener(opSCuenta);
        
        btnNuevoSC.addActionListener(nuevo_subcuentas);
		btnEditarSC.addActionListener(editar_subcuentas);
		btnDeshacerSC.addActionListener(deshacer_subcuentas);
        btnGuardarSC.addActionListener(guardar_subcuentas);
        
        txtFolio_SCFiltro.addKeyListener(opFiltroSC);
        txtSCFiltro.addKeyListener(opFiltroSC);
        
//   SUB-SUBCUENTA------------------------------------------------------------------------------------------------------
        tabla_Sub_Sub_Cuenta_Contable.addMouseListener(opSSCuenta);
        
        btnNuevoSSC.addActionListener(nuevo_sub_subcuentas);
		btnEditarSSC.addActionListener(editar_sub_subcuentas);
		btnDeshacerSSC.addActionListener(deshacer_sub_subcuentas);
        btnGuardarSSC.addActionListener(guardar_sub_subcuentas);
        
        txtFolio_SSCFiltro.addKeyListener(opFiltroSSC);
        txtSSCFiltro.addKeyListener(opFiltroSSC);  
        
}
//	CUENTAS----------------------------------------------------
		public void btnCuentas(boolean valor){
			btnNuevoCC.setEnabled(valor);
			btnEditarCC.setEnabled(valor);
			btnDeshacerCC.setEnabled(valor);
			btnGuardarCC.setEnabled(valor);
		}
//	SUBCUENTA--------------------------------------------------	
		public void btnSCuentas(boolean valor){
			btnNuevoSC.setEnabled(valor);
			btnEditarSC.setEnabled(valor);
			btnDeshacerSC.setEnabled(valor);
			btnGuardarSC.setEnabled(valor);
		}
//	SUBSUBCUENTA-----------------------------------------------
		public void btnSSCuentas(boolean valor){
			btnNuevoSSC.setEnabled(valor);
			btnEditarSSC.setEnabled(valor);
			btnDeshacerSSC.setEnabled(valor);
			btnGuardarSSC.setEnabled(valor);
		}
		
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
///////TODO TABLA CUENTAS CONTABLES
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	//DIMENCIONES
//   Tabla Cuentas Contables {"Folio", "Cuenta","Naturaleza","Grupo","Clasificacion","Estatus" }
	
		public void tabla_Cuentas_Contables(){
			llenado_tabla_cuentas_contables(2);
			tabla_cuentas_contables.getTableHeader().setReorderingAllowed(false) ;
		    		tabla_cuentas_contables.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
				    tabla_cuentas_contables.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
				    tabla_cuentas_contables.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
				    tabla_cuentas_contables.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
				    tabla_cuentas_contables.getColumnModel().getColumn(4).setCellRenderer(new tablaRenderer("fecha","izquierda","Arial","normal",12));
				    tabla_cuentas_contables.getColumnModel().getColumn(5).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
					
					tabla_cuentas_contables.getColumnModel().getColumn(0).setMinWidth(0);
				    tabla_cuentas_contables.getColumnModel().getColumn(0).setMaxWidth(60);
					tabla_cuentas_contables.getColumnModel().getColumn(1).setMinWidth(0);
					tabla_cuentas_contables.getColumnModel().getColumn(1).setMaxWidth(250);
					tabla_cuentas_contables.getColumnModel().getColumn(2).setMinWidth(0);
					tabla_cuentas_contables.getColumnModel().getColumn(2).setMaxWidth(80);
					tabla_cuentas_contables.getColumnModel().getColumn(3).setMinWidth(0);
					tabla_cuentas_contables.getColumnModel().getColumn(3).setMaxWidth(180);
					tabla_cuentas_contables.getColumnModel().getColumn(4).setMinWidth(0);
					tabla_cuentas_contables.getColumnModel().getColumn(4).setMaxWidth(180);
					tabla_cuentas_contables.getColumnModel().getColumn(5).setMinWidth(0);
					tabla_cuentas_contables.getColumnModel().getColumn(5).setMaxWidth(80);
		};
		
		//BORRADO Y LLENADO
		public void llenado_tabla_cuentas_contables(Integer consulta){
		  while(tabla_cuentas_contables.getRowCount()>0){	modeloCuenta_Contable.removeRow(0);	}
		  Object[][] getTablaCuentaContable = null;
 		     getTablaCuentaContable = Consulta_para_llenar_Mov_Contabilidad();
			 Object[] fila = new Object[6];
			     for(int i=0; i<getTablaCuentaContable.length; i++){
			             fila[0] = getTablaCuentaContable[i][0];
			             fila[1] = getTablaCuentaContable[i][1]+"";
			             fila[2] = getTablaCuentaContable[i][2]+"";
			             fila[3] = getTablaCuentaContable[i][3]+"";
			             fila[4] = getTablaCuentaContable[i][4]+"";
			             fila[5] = getTablaCuentaContable[i][5]+"";
			             modeloCuenta_Contable.addRow(fila); }
		}
		
		////CONSULTA 
		public Object[][] Consulta_para_llenar_Mov_Contabilidad(){
//			String fecha  = new SimpleDateFormat("dd/MM/yyyy").format(cfecha.getDate())+" 23:59:00";
			String consulta = "select"
					+ "      folio_cuenta_contable"
					+ "	  ,tb_cuentas_contables.cuenta_contable"
					+ "	  ,case when tb_cuentas_contables.naturaleza='A' then 'Acreedora'"
					+ "	        when tb_cuentas_contables.naturaleza='D' then 'Deudora'"
					+ "			end as naturaleza"
					+ "	  ,case when tb_cuentas_contables.grupo_contable='A' then 'Activo'"
					+ "	        when tb_cuentas_contables.grupo_contable='C' then 'Capital'"
					+ "	        when tb_cuentas_contables.grupo_contable='O' then 'Cuentas de Orden'"
					+ "            when tb_cuentas_contables.grupo_contable='P' then 'Pasivo'"
					+ "			when tb_cuentas_contables.grupo_contable='R' then 'Resultados'"
					+ "			 end as grupo_contable"
					+ "	  ,isnull(tb_clasificaciones_contables.clasificacion_contable,'') as clasificacion_contable"
					+ "  	  ,case when tb_cuentas_contables.status='V' then 'Vigente'"
					+ "	        when tb_cuentas_contables.status='C' then 'Cancelado'"
					+ "			ELSE 'ERROR' end as status"
					+ "   from tb_cuentas_contables"
					+ " left outer join tb_clasificaciones_contables on  tb_clasificaciones_contables.folio_clasificacion_contable=tb_cuentas_contables.folio_clasificacion_contable and tb_clasificaciones_contables.grupo_contable=tb_cuentas_contables.grupo_contable ";
	   		
			Statement s;
			ResultSet rs2;
			try {
				s = new Connexion().conexion().createStatement();
				rs2 = s.executeQuery(consulta);
				Matriz_Cuenta_Contable = new Object[getFilasSCOI(consulta)][6];
				int i=0;
				while(rs2.next()){
					Matriz_Cuenta_Contable[i][0] = "   "+rs2.getString(1).trim();
					Matriz_Cuenta_Contable[i][1] = "   "+rs2.getString(2).trim();
					Matriz_Cuenta_Contable[i][2] = "   "+rs2.getString(3).trim();
					Matriz_Cuenta_Contable[i][3] = "   "+rs2.getString(4).trim();
					Matriz_Cuenta_Contable[i][4] = "   "+rs2.getString(5).trim();
					Matriz_Cuenta_Contable[i][5] = "   "+rs2.getString(6).trim();
									i++;
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null,"Error en La consulta"+consulta+"\n SQLException"+e1.getMessage(),"Avise Al Administrador del Sistema",JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			}
		    return Matriz_Cuenta_Contable; 
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
	    
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////TODO TABLA SUBCUENTAS
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public void tabla_Subcuentas_Contables(){
	llenado_tabla_subcuentas_contables(2);
	tablaSub_Cuenta_Contable.getTableHeader().setReorderingAllowed(false) ;
	tablaSub_Cuenta_Contable.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
	tablaSub_Cuenta_Contable.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
	tablaSub_Cuenta_Contable.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
	tablaSub_Cuenta_Contable.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
	tablaSub_Cuenta_Contable.getColumnModel().getColumn(4).setCellRenderer(new tablaRenderer("fecha","izquierda","Arial","normal",12));

	tablaSub_Cuenta_Contable.getColumnModel().getColumn(0).setMinWidth(0);
	tablaSub_Cuenta_Contable.getColumnModel().getColumn(0).setMaxWidth(80);
	tablaSub_Cuenta_Contable.getColumnModel().getColumn(1).setMinWidth(0);
	tablaSub_Cuenta_Contable.getColumnModel().getColumn(1).setMaxWidth(80);
	tablaSub_Cuenta_Contable.getColumnModel().getColumn(2).setMinWidth(0);
	tablaSub_Cuenta_Contable.getColumnModel().getColumn(2).setMaxWidth(250);
	tablaSub_Cuenta_Contable.getColumnModel().getColumn(3).setMinWidth(0);
	tablaSub_Cuenta_Contable.getColumnModel().getColumn(3).setMaxWidth(100);
	tablaSub_Cuenta_Contable.getColumnModel().getColumn(4).setMinWidth(0);
	tablaSub_Cuenta_Contable.getColumnModel().getColumn(4).setMaxWidth(80);
};

//BORRADO Y LLENADO
public void llenado_tabla_subcuentas_contables(Integer consulta){
	while(tablaSub_Cuenta_Contable.getRowCount()>0){	modeloSub_Cuenta_Contable.removeRow(0);	}
	Object[][] getTablaCuentaContable = null;
	getTablaCuentaContable = Consulta_para_llenar_Subcuenas();
	Object[] fila = new Object[5];
	for(int i=0; i<getTablaCuentaContable.length; i++){
	fila[0] = getTablaCuentaContable[i][0];
	fila[1] = getTablaCuentaContable[i][1]+"";
	fila[2] = getTablaCuentaContable[i][2]+"";
	fila[3] = getTablaCuentaContable[i][3]+"";
	fila[4] = getTablaCuentaContable[i][4]+"";
	modeloSub_Cuenta_Contable.addRow(fila); }
}

////CONSULTA 
public Object[][] Consulta_para_llenar_Subcuenas(){
//String fecha  = new SimpleDateFormat("dd/MM/yyyy").format(cfecha.getDate())+" 23:59:00";
String consulta = " select tb_subcuentas_contables.folio_cuenta_contable,tb_subcuentas_contables.folio_subcuenta_contable,tb_subcuentas_contables.subcuenta_contable,tb_folios.transaccion,"
		+ "              		case when tb_subcuentas_contables.status='V' then 'Vigente' "
		+ "						       when tb_subcuentas_contables.status='C' then 'Cancelado' end as estatus from tb_subcuentas_contables "
		+ "							   inner join tb_folios on tb_folios.folio_transaccion = tb_subcuentas_contables.folio_transaccion and tb_folios.status = 1";

		Statement s;
		ResultSet rs2;
		try {
		s = new Connexion().conexion().createStatement();
		rs2 = s.executeQuery(consulta);
		Matriz_Subcuenta_Contable = new Object[getFilasSCOI(consulta)][5];
		int i=0;
		while(rs2.next()){
			Matriz_Subcuenta_Contable[i][0] = "   "+rs2.getString(1).trim();
			Matriz_Subcuenta_Contable[i][1] = "   "+rs2.getString(2).trim();
			Matriz_Subcuenta_Contable[i][2] = "   "+rs2.getString(3).trim();
			Matriz_Subcuenta_Contable[i][3] = "   "+rs2.getString(4).trim();
			Matriz_Subcuenta_Contable[i][4] = "   "+rs2.getString(5).trim();
		i++;
		}
} catch (SQLException e1) {
	e1.printStackTrace();
	JOptionPane.showMessageDialog(null,"Error En La consulta"+consulta+"\n SQLException"+e1.getMessage(),"Avise Al Administrador del Sistema",JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
}
return Matriz_Subcuenta_Contable; 
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////TODO TABLA SUB-SUBCUENTAS
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public void tabla_Sub_Subcuentas_Contables(){
	llenado_tabla_sub_subcuentas_contables(2);
	tabla_Sub_Sub_Cuenta_Contable.getTableHeader().setReorderingAllowed(false) ;
	tabla_Sub_Sub_Cuenta_Contable.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
	tabla_Sub_Sub_Cuenta_Contable.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
	tabla_Sub_Sub_Cuenta_Contable.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
	tabla_Sub_Sub_Cuenta_Contable.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
	tabla_Sub_Sub_Cuenta_Contable.getColumnModel().getColumn(4).setCellRenderer(new tablaRenderer("fecha","izquierda","Arial","normal",12));
	tabla_Sub_Sub_Cuenta_Contable.getColumnModel().getColumn(5).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 

	tabla_Sub_Sub_Cuenta_Contable.getColumnModel().getColumn(0).setMinWidth(0);
	tabla_Sub_Sub_Cuenta_Contable.getColumnModel().getColumn(0).setMaxWidth(80);
	tabla_Sub_Sub_Cuenta_Contable.getColumnModel().getColumn(1).setMinWidth(0);
	tabla_Sub_Sub_Cuenta_Contable.getColumnModel().getColumn(1).setMaxWidth(100);
	tabla_Sub_Sub_Cuenta_Contable.getColumnModel().getColumn(2).setMinWidth(0);
	tabla_Sub_Sub_Cuenta_Contable.getColumnModel().getColumn(2).setMaxWidth(120);
	tabla_Sub_Sub_Cuenta_Contable.getColumnModel().getColumn(3).setMinWidth(0);
	tabla_Sub_Sub_Cuenta_Contable.getColumnModel().getColumn(3).setMaxWidth(250);
	tabla_Sub_Sub_Cuenta_Contable.getColumnModel().getColumn(4).setMinWidth(0);
	tabla_Sub_Sub_Cuenta_Contable.getColumnModel().getColumn(4).setMaxWidth(100);
	
	tabla_Sub_Sub_Cuenta_Contable.getColumnModel().getColumn(5).setMinWidth(30);
	tabla_Sub_Sub_Cuenta_Contable.getColumnModel().getColumn(5).setMaxWidth(80);
};

//BORRADO Y LLENADO
public void llenado_tabla_sub_subcuentas_contables(Integer consulta){
	while(tabla_Sub_Sub_Cuenta_Contable.getRowCount()>0){	modelo_Sub_Sub_Cuenta_Contable.removeRow(0);	}
	Object[][] getTablaCuentaContable = null;
	getTablaCuentaContable = Consulta_para_llenar_Sub_Subcuenas();
	Object[] fila = new Object[6];
	for(int i=0; i<getTablaCuentaContable.length; i++){
	fila[0] = getTablaCuentaContable[i][0]+"";
	fila[1] = getTablaCuentaContable[i][1]+"";
	fila[2] = getTablaCuentaContable[i][2]+"";
	fila[3] = getTablaCuentaContable[i][3]+"";
	fila[4] = getTablaCuentaContable[i][4]+"";
	fila[5] = getTablaCuentaContable[i][5]+"";
	modelo_Sub_Sub_Cuenta_Contable.addRow(fila); }
}

////CONSULTA 
public Object[][] Consulta_para_llenar_Sub_Subcuenas(){
//String fecha  = new SimpleDateFormat("dd/MM/yyyy").format(cfecha.getDate())+" 23:59:00";
String consulta = " select tb_subsubcuentas_contables.folio_cuenta_contable "
		+ "					,tb_subsubcuentas_contables.folio_subcuenta_contable "
		+ "					,tb_subsubcuentas_contables.folio_subsubcuenta_contable "
		+ "					,tb_subsubcuentas_contables.subsubcuenta_contable "
		+ "					,tb_folios.transaccion "
		+ "					,case when(tb_subsubcuentas_contables.status)='V'then 'Vigente' else 'Cancelado' end as status "
		+ "			from tb_subsubcuentas_contables "
		+ "			inner join tb_empleado on tb_empleado.folio = tb_subsubcuentas_contables.usuario_modifico "
		+ "			inner join tb_folios on tb_folios.folio_transaccion = tb_subsubcuentas_contables.folio_transaccion and tb_folios.status = 1 ";

		Statement s;
		ResultSet rs2;
		try {
		s = new Connexion().conexion().createStatement();
		rs2 = s.executeQuery(consulta);
		Matriz_Sub_Subcuenta_Contable = new Object[getFilasSCOI(consulta)][6];
		int i=0;
		while(rs2.next()){
			Matriz_Sub_Subcuenta_Contable[i][0] = "   "+rs2.getString(1).trim();
			Matriz_Sub_Subcuenta_Contable[i][1] = "   "+rs2.getString(2).trim();
			Matriz_Sub_Subcuenta_Contable[i][2] = "   "+rs2.getString(3).trim();
			Matriz_Sub_Subcuenta_Contable[i][3] = "   "+rs2.getString(4).trim();
			Matriz_Sub_Subcuenta_Contable[i][4] = "   "+rs2.getString(5).trim();
			Matriz_Sub_Subcuenta_Contable[i][5] = "   "+rs2.getString(6).trim();
		i++;
		}
} catch (SQLException e1) {
	e1.printStackTrace();
	JOptionPane.showMessageDialog(null,"Error En La consulta"+consulta+"\n SQLException"+e1.getMessage(),"Avise Al Administrador del Sistema",JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
}
return Matriz_Sub_Subcuenta_Contable; 
}


///////////////TODO ACTION LISTENERS CUENTAS CONTABLES

MouseListener opCuenta = new MouseListener() {
	public void mouseReleased(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {

    	if(e.getClickCount()==1){
    		componentes_cuentas_contables(false);
    		int fila = tabla_cuentas_contables.getSelectedRow();
    		txtFolio_Cuenta_Contable.setText(tabla_cuentas_contables.getValueAt(fila, 0).toString().trim());
    		txtCuenta_Contable.setText(tabla_cuentas_contables.getValueAt(fila, 1).toString());
    		cmbNaturaleza_Cuenta_Contable.setSelectedItem(tabla_cuentas_contables.getValueAt(fila, 2).toString().trim());
    		cmbGrupo_Cuenta_Contable.setSelectedItem(tabla_cuentas_contables.getValueAt(fila, 3).toString().trim());
    		cmbClasif_Cuenta_Contable.setSelectedItem(tabla_cuentas_contables.getValueAt(fila, 4).toString().trim().equals("")?"No Aplica":tabla_cuentas_contables.getValueAt(fila, 4).toString().trim());
    		cmbStatus_Cuenta_Contable.setSelectedItem(tabla_cuentas_contables.getValueAt(fila, 5).toString().trim());
    		
//    		CONSULTAR ESTABLECIMIENTO AL DAR CLICK CON EL FOLIO DE LA CUENTA
    		cmbEstablecimiento.setSelectedItem(new BuscarTablasModel().establecimientoCuenta(txtFolio_Cuenta_Contable.getText()));
    		
			btnEditarCC.setEnabled(true);
			
			limpiarSC();
			limpiarSSC();
			
			new Obj_Filtro_Dinamico(tablaSub_Cuenta_Contable
					, "Folio Cuenta", txtFolio_Cuenta_Contable.getText().toUpperCase()
					, "Folio SubCuenta",txtFolio_SCFiltro.getText().toUpperCase()
					, "SubCuenta", txtSCFiltro.getText().toUpperCase()
					, "", "");
			
			new Obj_Filtro_Dinamico(tabla_Sub_Sub_Cuenta_Contable
					, "Folio Cuenta", txtFolio_Cuenta_Contable.getText().toUpperCase()
					, "Folio Subcuenta",txtFolio_SC.getText().toUpperCase()
					, "Folio_Sub_Subcuenta", txtFolio_SSCFiltro.getText().toUpperCase()
					, "Sub Subcuenta", txtSSCFiltro.getText().toUpperCase());

    	}
    
	}
};
	
		KeyListener opFiltroCC = new KeyListener(){
			public void keyReleased(KeyEvent arg0) {
				new Obj_Filtro_Dinamico(tabla_cuentas_contables
						, "Folio", txtFolio_Cuenta_Contablebusqueda.getText().toUpperCase()
						, "Cuenta",txtCuenta_Contablebusqueda.getText().toUpperCase()
						, "", ""
						, "", "");
			}
			public void keyTyped(KeyEvent arg0) {}
			public void keyPressed(KeyEvent arg0) {}		
		};

		public void componentes_cuentas_contables(boolean valor){
			txtFolio_Cuenta_Contable.setEditable(valor);
			txtCuenta_Contable.setEditable(valor);
			cmbNaturaleza_Cuenta_Contable.setEnabled(valor);
			cmbGrupo_Cuenta_Contable.setEnabled(valor);
			cmbClasif_Cuenta_Contable.setEnabled(valor);
		    cmbStatus_Cuenta_Contable.setEnabled(valor);
		    cmbEstablecimiento.setEnabled(valor);
		}
	    
		ActionListener nuevo_cuentas_contables = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				componentes_cuentas_contables(true);
				
				limpiarC();
				limpiarSC();
				txtFolio_Cuenta_Contable.requestFocus();
				
				btnCuentas(true);
				btnSCuentas(false);
				btnSSCuentas(false);
				
				tabla_cuentas_contables.removeMouseListener(opCuenta);
		        tablaSub_Cuenta_Contable.removeMouseListener(opSCuenta);
		        tabla_Sub_Sub_Cuenta_Contable.removeMouseListener(opSSCuenta);
				
//				filtro de de subcuenta con informacion de la cuenta
				new Obj_Filtro_Dinamico(tablaSub_Cuenta_Contable
						,"Folio Cuenta", txtFolio_Cuenta_Contable.getText().toUpperCase()
						,"Folio SubCuenta",txtFolio_SCFiltro.getText().toUpperCase()
						, "SubCuenta", txtSCFiltro.getText().toUpperCase()
						, "", "");
				
//				filtro de de sub-subcuenta con informacion de la cuenta
				new Obj_Filtro_Dinamico(tabla_Sub_Sub_Cuenta_Contable
						,"Folio Cuenta", txtFolio_Cuenta.getText().toUpperCase()
						,"Folio Subcuenta",txtFolio_SC.getText().toUpperCase()
						, "Folio_Sub_Subcuenta", txtFolio_SSCFiltro.getText().toUpperCase()
						, "Sub Subcuenta", txtSSCFiltro.getText().toUpperCase());
			}
		};
		
		ActionListener editar_cuentas_contables = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				if(!txtFolio_Cuenta_Contable.getText().equals("")){
					limpiarSC();
					componentes_cuentas_contables(true);
					txtFolio_Cuenta_Contable.setEditable(false);
					txtCuenta_Contable.requestFocus();
					btnCuentas(true);
					btnSCuentas(false);
					btnSSCuentas(false);
					
					tabla_cuentas_contables.removeMouseListener(opCuenta);
			        tablaSub_Cuenta_Contable.removeMouseListener(opSCuenta);
			        tabla_Sub_Sub_Cuenta_Contable.removeMouseListener(opSSCuenta);
					
//					filtro de de subcuenta con informacion de la cuenta
					new Obj_Filtro_Dinamico(tablaSub_Cuenta_Contable
							,"Folio Cuenta", txtFolio_Cuenta_Contable.getText().toUpperCase()
							,"Folio SubCuenta",txtFolio_SCFiltro.getText().toUpperCase()
							, "SubCuenta", txtSCFiltro.getText().toUpperCase()
							, "", "");
					
//					filtro de de sub-subcuenta con informacion de la cuenta
					new Obj_Filtro_Dinamico(tabla_Sub_Sub_Cuenta_Contable
							,"Folio Cuenta", txtFolio_Cuenta_Contable.getText().toUpperCase()
							,"Folio Subcuenta",txtFolio_SC.getText().toUpperCase()
							, "Folio_Sub_Subcuenta", txtFolio_SSCFiltro.getText().toUpperCase()
							, "Sub Subcuenta", txtSSCFiltro.getText().toUpperCase());
					
				}else{
					JOptionPane.showMessageDialog(null,"Debe Seleccionar Una Cuenta Para Su Edicion","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
					return;
				}
			}
		};
		
		ActionListener deshacer_cuentas_contables = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				limpiarC();
				limpiarSC();
				
				deshacerC();
				deshacerSC();
				deshacerSSC();
				
				tabla_cuentas_contables.addMouseListener(opCuenta);
		        tablaSub_Cuenta_Contable.addMouseListener(opSCuenta);
		        tabla_Sub_Sub_Cuenta_Contable.addMouseListener(opSSCuenta);

			}
		};
		
		public void deshacerC(){
			componentes_cuentas_contables(false);
			
			limpiarC();
			
			btnCuentas(true);
			btnSCuentas(true);
			btnSSCuentas(true);
			
//			filtro cuenta contable
			new Obj_Filtro_Dinamico(tabla_cuentas_contables
					,"Folio", txtFolio_Cuenta_Contablebusqueda.getText().toUpperCase()
					,"Cuenta",txtCuenta_Contablebusqueda.getText().toUpperCase()
					, "", ""
					, "", "");
		}
		
		public void limpiarC(){
			txtFolio_Cuenta_Contable.setText("");
			txtCuenta_Contable.setText("");
			cmbNaturaleza_Cuenta_Contable.setSelectedIndex(0);
			cmbGrupo_Cuenta_Contable.setSelectedIndex(0);
			cmbClasif_Cuenta_Contable.setSelectedIndex(0);
			cmbStatus_Cuenta_Contable.setSelectedIndex(0);
			cmbEstablecimiento.setSelectedIndex(0);
			
			txtFolio_Cuenta_Contablebusqueda.setText("");
			txtCuenta_Contablebusqueda.setText("");
		}
		
		public boolean existeCuenta(String folio){
				return new BuscarSQL().existe_cuenta(folio);
		}
		
		ActionListener guardar_cuentas_contables = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				if(!validaCamposCuentas().equals("")){
					JOptionPane.showMessageDialog(null, "Los Siguiente Campos Son Requeridos: "+validaCamposCuentas(),"Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
					return;
				}else{
//					BUSCAR CUENTA CON EL FOLIO TECLEADO, SI EXISTE  PREGUNTAR SI DE DESEA ACTUALIZARLO
					if(existeCuenta(txtFolio_Cuenta_Contable.getText().trim())){
							if(JOptionPane.showConfirmDialog(null, "El Registro Existe, ¿Desea Actualizarlo?") == 0){
								guardarCuenta();
							}else{
								return;
							}
					}else{
						guardarCuenta();
					}
				}

			}
		};
		
		public void guardarCuenta(){
			
			if(new GuardarSQL().Guardar_Cuenta_Contable(txtFolio_Cuenta_Contable.getText()
														,txtCuenta_Contable.getText()
														,cmbNaturaleza_Cuenta_Contable.getSelectedItem().toString()
														,cmbGrupo_Cuenta_Contable.getSelectedItem().toString()
														,cmbClasif_Cuenta_Contable.getSelectedItem().toString()
														,cmbStatus_Cuenta_Contable.getSelectedItem().toString()
														,cmbEstablecimiento.getSelectedItem().toString())){

				componentes_cuentas_contables(false);
				llenado_tabla_cuentas_contables(2);
				
				limpiarC();
				limpiarSC();
				limpiarSSC();
				
				deshacerC();
				deshacerSC();
				deshacerSSC();
				
				tabla_cuentas_contables.addMouseListener(opCuenta);
		        tablaSub_Cuenta_Contable.addMouseListener(opSCuenta);
		        tabla_Sub_Sub_Cuenta_Contable.addMouseListener(opSSCuenta);
		        
				JOptionPane.showMessageDialog(null, "La Cuenta Se Guardó Exitosamente","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen//aplicara-el-dialogo-icono-6256-32.png"));
				return;
			}else{
				JOptionPane.showMessageDialog(null, "Ocurrió Un Error Al Intentar Guardar","Error",JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen//usuario-icono-eliminar5252-64.png"));
				return;
			}
			
		}
		
		private String validaCamposCuentas(){
			String error="";
			
			if(txtFolio_Cuenta_Contable.equals(""))error+= "Folio Cuenta\n";
			if(txtCuenta_Contable.equals(""))error+= "Cuenta\n";
			
			return error;
		}
		
///////////////TODO ACTION LISTENERS SUBCUENTAS
		
		String SCuenta = "";
		String SSCuenta = "";
		
		MouseListener opSCuenta = new MouseListener() {
			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {
				
	        	if(e.getClickCount()==1){
	        		componentes_subcuentas(false);
	        		int fila = tablaSub_Cuenta_Contable.getSelectedRow();
	        		txtFolio_Cuenta.setText(tablaSub_Cuenta_Contable.getValueAt(fila, 0).toString().trim());
	        		txtFolio_SC.setText(tablaSub_Cuenta_Contable.getValueAt(fila, 1).toString().trim());
	        		txtSC.setText(tablaSub_Cuenta_Contable.getValueAt(fila, 2).toString().trim());
	        		cmbTransaccion.setSelectedItem(tablaSub_Cuenta_Contable.getValueAt(fila, 3).toString().trim());
	        		cmbStatus_SC.setSelectedItem(tablaSub_Cuenta_Contable.getValueAt(fila, 4).toString().trim());
	        		
	        		btnEditarSC.setEnabled(true);
					
	        		limpiarSSC();
	        		
//					filtro de de sub-subcuenta con informacion de la cuenta
					new Obj_Filtro_Dinamico(tabla_Sub_Sub_Cuenta_Contable
							,"Folio Cuenta", txtFolio_Cuenta.getText().toUpperCase()
							,"Folio Subcuenta",txtFolio_SC.getText().toUpperCase()
							, "Folio_Sub_Subcuenta", txtFolio_SSCFiltro.getText().toUpperCase()
							, "Sub Subcuenta", txtSSCFiltro.getText().toUpperCase());
	        	}
	        
			}
		};
		
		KeyListener opFiltroSC = new KeyListener(){
			public void keyReleased(KeyEvent arg0) {
				
//				filtro de de subcuenta con informacion de la cuenta
				new Obj_Filtro_Dinamico(tablaSub_Cuenta_Contable
						,"Folio Cuenta", txtFolio_Cuenta_Contable.getText().toUpperCase()
						,"Folio SubCuenta",txtFolio_SCFiltro.getText().toUpperCase()
						, "SubCuenta", txtSCFiltro.getText().toUpperCase()
						, "", "");
				
			}
			public void keyTyped(KeyEvent arg0) {}
			public void keyPressed(KeyEvent arg0) {}		
		};
		
		public void componentes_subcuentas(boolean valor){
			
			
			txtFolio_Cuenta.setEditable(valor);
			txtFolio_SC.setEditable(valor);
			txtSC.setEditable(valor);
			cmbTransaccion.setEnabled(valor);
			cmbStatus_SC.setEnabled(valor);
		}
		
		ActionListener nuevo_subcuentas = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				if(!txtFolio_Cuenta_Contable.isEditable() && !txtFolio_Cuenta_Contable.getText().equals("")){
						componentes_subcuentas(true);
						txtFolio_Cuenta.setEditable(false);
						
						limpiarSC();
						limpiarSSC();
						txtFolio_SC.requestFocus();
						
						btnCuentas(false);
						btnSCuentas(true);
						btnSSCuentas(false);
						
						tabla_cuentas_contables.removeMouseListener(opCuenta);
				        tablaSub_Cuenta_Contable.removeMouseListener(opSCuenta);
				        tabla_Sub_Sub_Cuenta_Contable.removeMouseListener(opSSCuenta);
				        
				        SCuenta = "nueva";
				        
				}else{
					JOptionPane.showMessageDialog(null,"Debe Seleccionar Una Cuenta Para Crear Una Nueva Subcuenta","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
					return;
				}
				
				
				
			}
		};
		
		ActionListener editar_subcuentas = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				if(!txtFolio_SC.getText().equals("")){
					componentes_subcuentas(true);
					txtFolio_Cuenta.setEditable(false);
					txtFolio_SC.setEditable(false);
					txtSC.requestFocus();
					
					btnCuentas(false);
					btnSCuentas(true);
					btnSSCuentas(false);
					
					tabla_cuentas_contables.removeMouseListener(opCuenta);
			        tablaSub_Cuenta_Contable.removeMouseListener(opSCuenta);
			        tabla_Sub_Sub_Cuenta_Contable.removeMouseListener(opSSCuenta);
			        
			        SCuenta = "modificar";
			        
				}else{
					JOptionPane.showMessageDialog(null,"Debe Seleccionar Una Subcuenta Para Su Edicion","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
					return;
				}
			}
		};
		
		ActionListener deshacer_subcuentas = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				limpiarSC();
				
				deshacerSSC();
				deshacerSC();
				
				tabla_cuentas_contables.addMouseListener(opCuenta);
		        tablaSub_Cuenta_Contable.addMouseListener(opSCuenta);
		        tabla_Sub_Sub_Cuenta_Contable.addMouseListener(opSSCuenta);
			}
		};
		
		public void deshacerSC(){
			componentes_subcuentas(false);
			
			btnCuentas(true);
			btnSCuentas(true);
			btnSSCuentas(true);
			
//			filtro de de subcuenta con informacion de la cuenta
			new Obj_Filtro_Dinamico(tablaSub_Cuenta_Contable
					,"Folio Cuenta", txtFolio_Cuenta_Contable.getText().toUpperCase()
					,"Folio SubCuenta",txtFolio_SCFiltro.getText().toUpperCase()
					, "SubCuenta", txtSCFiltro.getText().toUpperCase()
					, "", "");
			
//			filtro de de sub-subcuenta con informacion de la cuenta
			new Obj_Filtro_Dinamico(tabla_Sub_Sub_Cuenta_Contable
					,"Folio Cuenta", txtFolio_Cuenta_Contable.getText().toUpperCase()
					,"Folio Subcuenta",txtFolio_SC.getText().toUpperCase()
					, "Folio_Sub_Subcuenta", txtFolio_SSCFiltro.getText().toUpperCase()
					, "Sub Subcuenta", txtSSCFiltro.getText().toUpperCase());
			
		}
		
		public void limpiarSC(){
			txtFolio_Cuenta.setText("");
			txtFolio_SC.setText("");
			txtSC.setText("");
			cmbTransaccion.setSelectedIndex(0);
			cmbStatus_SC.setSelectedIndex(0);
			
			txtSCFiltro.setText("");
			txtFolio_SCFiltro.setText("");
		}
		
		public boolean existeSCuenta(String cuenta,String subcuenta){
			return new BuscarSQL().existe_subcuenta(cuenta,subcuenta);
		}

		ActionListener guardar_subcuentas = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				if(!validaCamposSCuenta().equals("")){
					JOptionPane.showMessageDialog(null, "Los Siguiente Campos Son Requeridos: "+validaCamposCuentas(),"Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
					return;
				}else{
//					BUSCAR CUENTA CON EL FOLIO TECLEADO, SI EXISTE  PREGUNTAR SI DE DESEA ACTUALIZARLO
//					 SCuenta = "nueva"
					
					
					
					if(existeSCuenta(SCuenta.equals("nueva")?txtFolio_Cuenta_Contable.getText().trim():txtFolio_SC.getText(),txtFolio_SC.getText())){
							if(JOptionPane.showConfirmDialog(null, "El Registro Existe, ¿Desea Actualizarlo?") == 0){
								guardarSCuenta();
							}else{
								return;
							}
					}else{
						guardarSCuenta();
					}
				}

			}
		};
		
		public void guardarSCuenta(){
//			Guardar_SubCuenta_Contable(String f_cuenta, String f_scuenta,String scuenta, String transaccion, String status)
			
			
//			"nueva")?txtFolio_Cuenta.getText().trim():txtFolio_Cuenta2.getText()
//			,SCuenta.equals("nueva")?txtFolio_SC.getText():txtFolio_SC2.getText()
//			,txtFolio_SSC.getText()
//			,txtSSC.getText()
//			,cmbTransaccionSSC.getSelectedItem().toString()
//			,cmbStatus_SSC.getSelectedItem().toString()
			
			
			if(new GuardarSQL().Guardar_SubCuenta_Contable(SCuenta.equals("nueva")?txtFolio_Cuenta_Contable.getText():txtFolio_Cuenta.getText()
															,txtFolio_SC.getText()
															,txtSC.getText()
															,cmbTransaccion.getSelectedItem().toString()
															,cmbStatus_SC.getSelectedItem().toString())){
				componentes_subcuentas(false);
				
				llenado_tabla_subcuentas_contables(2);
				
				limpiarC();
				limpiarSC();
				limpiarSSC();
				
				deshacerC();
				deshacerSC();
				deshacerSSC();
				
				tabla_cuentas_contables.addMouseListener(opCuenta);
		        tablaSub_Cuenta_Contable.addMouseListener(opSCuenta);
		        tabla_Sub_Sub_Cuenta_Contable.addMouseListener(opSSCuenta);
		        
				JOptionPane.showMessageDialog(null, "La Cuenta Se Guardó Exitosamente","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen//aplicara-el-dialogo-icono-6256-32.png"));
				return;
			}else{
				JOptionPane.showMessageDialog(null, "Ocurrió Un Error Al Intentar Guardar","Error",JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen//usuario-icono-eliminar5252-64.png"));
				return;
			}
			
		}
		
		private String validaCamposSCuenta(){
			String error="";
			
			if(txtFolio_SC.equals(""))error+= "Folio SubCuenta\n";
			if(txtSC.equals(""))error+= "SubCuenta\n";
			
			return error;
		}
		
		
///////////////TODO ACTION LISTENERS SUB-SUBCUENTAS
		
		MouseListener opSSCuenta = new MouseListener() {
			public void mouseReleased(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {
				
	        	if(e.getClickCount()==1){
	        		componentes_sub_subcuentas(false);
	        		int fila = tabla_Sub_Sub_Cuenta_Contable.getSelectedRow();
	        		
	        		txtFolio_Cuenta2.setText(tabla_Sub_Sub_Cuenta_Contable.getValueAt(fila, 0).toString().trim());
	        		txtFolio_SC2.setText(tabla_Sub_Sub_Cuenta_Contable.getValueAt(fila, 1).toString().trim());
	        		txtFolio_SSC.setText(tabla_Sub_Sub_Cuenta_Contable.getValueAt(fila, 2).toString().trim());
	        		txtSSC.setText(tabla_Sub_Sub_Cuenta_Contable.getValueAt(fila, 3).toString().trim());
	        		cmbTransaccionSSC.setSelectedItem(tabla_Sub_Sub_Cuenta_Contable.getValueAt(fila, 4).toString().trim());
	        		cmbStatus_SSC.setSelectedItem(tabla_Sub_Sub_Cuenta_Contable.getValueAt(fila, 5).toString().trim());
	        		btnEditarSSC.setEnabled(true);
	        	}
	        
			}
		};
		
		KeyListener opFiltroSSC = new KeyListener(){
			public void keyReleased(KeyEvent arg0) {
				
//				filtro de de sub-subcuenta con informacion de la cuenta
				new Obj_Filtro_Dinamico(tabla_Sub_Sub_Cuenta_Contable
						,"Folio Cuenta", txtFolio_Cuenta.getText().toUpperCase()
						,"Folio Subcuenta",txtFolio_SC.getText().toUpperCase()
						, "Folio_Sub_Subcuenta", txtFolio_SSCFiltro.getText().toUpperCase()
						, "Sub Subcuenta", txtSSCFiltro.getText().toUpperCase());
				
			}
			public void keyTyped(KeyEvent arg0) {}
			public void keyPressed(KeyEvent arg0) {}		
		};
		
		public void componentes_sub_subcuentas(boolean valor){
			txtFolio_Cuenta2.setEditable(valor);
			txtFolio_SC2.setEditable(valor);
			txtFolio_SSC.setEditable(valor);
			txtSSC.setEditable(valor);
			cmbTransaccionSSC.setEnabled(valor);
			cmbStatus_SSC.setEnabled(valor);
		}
		
		ActionListener nuevo_sub_subcuentas = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				if(!txtFolio_Cuenta.isEditable() && !txtFolio_Cuenta.getText().equals("") && !txtFolio_SC.isEditable() && !txtFolio_SC.getText().equals("")){
					
					componentes_sub_subcuentas(true);
					txtFolio_Cuenta2.setEditable(false);
					txtFolio_SC2.setEditable(false);
					
					limpiarSSC();
					txtFolio_SSC.requestFocus();
					
					btnCuentas(false);
					btnSCuentas(false);
					btnSSCuentas(true);
					
					tabla_cuentas_contables.removeMouseListener(opCuenta);
			        tablaSub_Cuenta_Contable.removeMouseListener(opSCuenta);
			        tabla_Sub_Sub_Cuenta_Contable.removeMouseListener(opSSCuenta);
			        
			        SSCuenta = "nueva";
				}else{
					JOptionPane.showMessageDialog(null,"Debe Seleccionar Una Subcuenta Para Crear Una Nueva Sub-Subcuenta","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
					return;
				}

			}
		};
		
		ActionListener editar_sub_subcuentas = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				if(!txtFolio_SSC.getText().equals("")){
					componentes_sub_subcuentas(true);
					txtFolio_Cuenta2.setEditable(false);
					txtFolio_SC2.setEditable(false);
					txtFolio_SSC.setEditable(false);
					txtSSC.requestFocus();
					
					btnCuentas(false);
					btnSCuentas(false);
					btnSSCuentas(true);
					
					tabla_cuentas_contables.removeMouseListener(opCuenta);
			        tablaSub_Cuenta_Contable.removeMouseListener(opSCuenta);
			        tabla_Sub_Sub_Cuenta_Contable.removeMouseListener(opSSCuenta);
					
			        SSCuenta = "modificar";
			        
				}else{
					JOptionPane.showMessageDialog(null,"Debe Seleccionar Una Sub-Subcuenta Para Su Edicion","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
					return;
				}
			}
		};
		
		ActionListener deshacer_sub_subcuentas = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				limpiarSSC();
				deshacerSSC();
				
				tabla_cuentas_contables.addMouseListener(opCuenta);
		        tablaSub_Cuenta_Contable.addMouseListener(opSCuenta);
		        tabla_Sub_Sub_Cuenta_Contable.addMouseListener(opSSCuenta);
			}
		};
		
		public void deshacerSSC(){
			componentes_sub_subcuentas(false);
			
			btnCuentas(true);
			btnSCuentas(true);
			btnSSCuentas(true);
			
//			filtro de de sub-subcuenta con informacion de la cuenta
			new Obj_Filtro_Dinamico(tabla_Sub_Sub_Cuenta_Contable
					,"Folio Cuenta", txtFolio_Cuenta.getText().toUpperCase()
					,"Folio Subcuenta",txtFolio_SC.getText().toUpperCase()
					, "Folio_Sub_Subcuenta", txtFolio_SSCFiltro.getText().toUpperCase()
					, "Sub Subcuenta", txtSSCFiltro.getText().toUpperCase());
			
		}
		
		public void limpiarSSC(){
			txtFolio_Cuenta2.setText("");
			txtFolio_SC2.setText("");
			txtFolio_SSC.setText("");
			txtSSC.setText("");
			cmbTransaccionSSC.setSelectedIndex(0);
			cmbStatus_SSC.setSelectedIndex(0);
			
			txtFolio_SSCFiltro.setText("");
			txtSSCFiltro.setText("");
		}
		
		
		public boolean existeSSCuenta(String cuenta,String subcuenta,String subsubcuenta){
			return new BuscarSQL().existe_subsubcuenta(cuenta,subcuenta,subsubcuenta);
		}

		ActionListener guardar_sub_subcuentas = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				if(!validaCamposSSCuenta().equals("")){
					JOptionPane.showMessageDialog(null, "Los Siguiente Campos Son Requeridos: "+validaCamposCuentas(),"Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
					return;
				}else{
//					BUSCAR CUENTA CON EL FOLIO TECLEADO, SI EXISTE  PREGUNTAR SI DE DESEA ACTUALIZARLO
					if(existeSSCuenta(SSCuenta.equals("nueva")?txtFolio_Cuenta.getText().trim():txtFolio_Cuenta2.getText()
							,SSCuenta.equals("nueva")?txtFolio_SC.getText():txtFolio_SC2.getText()
							,txtFolio_SSC.getText())){
							if(JOptionPane.showConfirmDialog(null, "El Registro Existe, ¿Desea Actualizarlo?") == 0){
								guardarSSCuenta();
							}else{
								return;
							}
					}else{
						guardarSSCuenta();
					}
				}

			}
		};
		
		public void guardarSSCuenta(){
//			Guardar_SubCuenta_Contable(String f_cuenta, String f_scuenta,String scuenta, String transaccion, String status)
			if(new GuardarSQL().Guardar_Sub_SubCuenta_Contable(SSCuenta.equals("nueva")?txtFolio_Cuenta.getText().trim():txtFolio_Cuenta2.getText()
															,SSCuenta.equals("nueva")?txtFolio_SC.getText():txtFolio_SC2.getText()
															,txtFolio_SSC.getText()
															,txtSSC.getText()
															,cmbTransaccionSSC.getSelectedItem().toString()
															,cmbStatus_SSC.getSelectedItem().toString())){
				componentes_sub_subcuentas(false);
				
				llenado_tabla_sub_subcuentas_contables(2);
				
				limpiarC();
				limpiarSC();
				limpiarSSC();
				
				deshacerC();
				deshacerSC();
				deshacerSSC();
				
				tabla_cuentas_contables.addMouseListener(opCuenta);
		        tablaSub_Cuenta_Contable.addMouseListener(opSCuenta);
		        tabla_Sub_Sub_Cuenta_Contable.addMouseListener(opSSCuenta);
		        
				JOptionPane.showMessageDialog(null, "La Sub-SubCuenta Se Guardó Exitosamente","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen//aplicara-el-dialogo-icono-6256-32.png"));
				return;
			}else{
				JOptionPane.showMessageDialog(null, "Ocurrió Un Error Al Intentar Guardar","Error",JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen//usuario-icono-eliminar5252-64.png"));
				return;
			}
			
		}
		
		private String validaCamposSSCuenta(){
			String error="";
			
			if(txtFolio_SSC.equals(""))error+= "Folio Sub-SubCuenta\n";
			if(txtSSC.equals(""))error+= "Sub-SubCuenta\n";
			
			return error;
		}
		
	    
	public static void main(String args[]){
		try{			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Cuentas_Contables().setVisible(true);
		}catch(Exception e){	}	}
}
