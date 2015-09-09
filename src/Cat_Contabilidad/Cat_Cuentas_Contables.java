package Cat_Contabilidad;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
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
import javax.swing.table.TableColumn;

import Conexiones_SQL.Connexion;
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
    
//	String establecimiento[] = new Obj_Establecimiento().Combo_Establecimiento();
//	@SuppressWarnings("rawtypes")
//	JComboBox cmbEstablecimiento = new JComboBox(establecimiento);
	
    int ancho = 0;

	JButton btnNuevoSCC = new JButton("Nuevo",new ImageIcon("imagen/Nuevo.png"));
	JButton btnEditarSCC = new JButton("Editar",new ImageIcon("imagen/editara.png"));
	JButton btnDeshacerSCC = new JButton("Deshacer",new ImageIcon("imagen/deshacer16.png"));
	JButton btnGuardarSCC = new JButton("Guardar",new ImageIcon("imagen/Guardar.png"));
	
//	JTextField txtFolio_Cuenta_Contable = new Componentes().text(new JCTextField(), "Folio Cuenta", 8, "String");
//	JTextField txtCuenta_Contable = new Componentes().text(new JCTextField(), "Nombre De La Cuenta Contable", 100, "String");
    JTextField txtFolio_SubcuentaFiltro = new Componentes().text(new JCTextField(), "Folio Subcuenta", 8, "String");
	JTextField txtSubcuentaFiltro = new Componentes().text(new JCTextField(), "Nombre De La Subcuenta Contable", 100, "String");
	
	String[] transaccion = {"No Aplica","Establecimiento","Departamento","Empleado","Proveedor"};
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbTransaccion = new JComboBox(transaccion);
    
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
    
    public TableColumn columna_transaccion = tablaSub_Cuenta_Contable.getColumnModel().getColumn(3);
	
     ///TODO CONSTRUCTOR	
	public Cat_Cuentas_Contables(){
		ancho = Toolkit.getDefaultToolkit().getScreenSize().width;
		int alto = Toolkit.getDefaultToolkit().getScreenSize().height;
		alto=alto-40;
		cont.add(panel);
		setSize(ancho,alto);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Catalogo de Cuentas Contables");
		setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/ok-lista-de-verificacion-icono-7906-64.png"));
		panel.setBorder(BorderFactory.createTitledBorder("Cuentas Contables"));
		scroll =new JScrollPane(tabla_cuentas_contables);

		int x=10,y=25,l=100,a=20;
		
		panel.add(new JLabel("Cuenta Contable:")).setBounds(x,y,l,a);
		panel.add(txtFolio_Cuenta_Contable).setBounds(x+=85, y, l-20, a);
		panel.add(txtCuenta_Contable).setBounds(x+=80, y, l*3, a);
		panel.add(new JLabel("Naturaleza:")).setBounds(x+=315,y,l-40,a);
		panel.add(cmbNaturaleza_Cuenta_Contable).setBounds(x+=58, y, l, a);
		panel.add(new JLabel("Grupo:")).setBounds(x+=115,y,l-40,a);
		panel.add(cmbGrupo_Cuenta_Contable).setBounds(x+=36, y, l, a);
		panel.add(new JLabel("Clasificacion:")).setBounds(x+=115,y,l-30,a);
		panel.add(cmbClasif_Cuenta_Contable).setBounds(x+=63, y, l, a);
		panel.add(new JLabel("Estatus:")).setBounds(x+=115,y,l-30,a);
		panel.add(cmbStatus_Cuenta_Contable).setBounds(x+=40, y, l, a);
		x=10;
		panel.add(txtFolio_Cuenta_Contablebusqueda).setBounds(x, y+=35, l, a);
		panel.add(txtCuenta_Contablebusqueda).setBounds(x+=100, y, l*4-35, a);
		panel.add(btnNuevoCC).setBounds(x+=400, y, l, a);
		panel.add(btnEditarCC).setBounds(x+=160,y, l, a);
		panel.add(btnDeshacerCC).setBounds(x+=160,y, l, a);
		panel.add(btnGuardarCC).setBounds(x+=160,y, l, a);
		x=10;
		panel.add(scroll).setBounds(x, y+=25, ancho-30, alto/3-65);
		
		x=105;
		panel.add(txtFolio_SubcuentaFiltro).setBounds(x, y+=(alto/3-50), l, a);
		panel.add(txtSubcuentaFiltro).setBounds(x+=100, y, l*4-35, a);
		panel.add(btnNuevoSCC).setBounds(x+=400, y, l, a);
		panel.add(btnEditarSCC).setBounds(x+=160,y, l, a);
		panel.add(btnDeshacerSCC).setBounds(x+=160,y, l, a);
		panel.add(btnGuardarSCC).setBounds(x+=160,y, l, a);
		x=10;
		panel.add(scrollSub_Cuenta_Contable).setBounds(x, y+=25, (ancho/2)-140, alto/3-65);
		
		deshabilitarcomponentes();
		tabla_Cuentas_Contables();
		tabla_Subcuentas_Contables();
		
		this.columna_transaccion.setCellEditor(new javax.swing.DefaultCellEditor(cmbTransaccion));
		
		btnNuevoCC.addActionListener(nuevo_cuentas_contables);
		btnEditarCC.addActionListener(editar_cuentas_contables);
		btnDeshacerCC.addActionListener(deshacer_cuentas_contables);
        btnGuardarCC.addActionListener(guardar_cuentas_contables);
        
        agregar_cuenta_click(tabla_cuentas_contables);
        
        txtFolio_Cuenta_Contablebusqueda.addKeyListener(opFiltroCC);
        txtCuenta_Contablebusqueda.addKeyListener(opFiltroCC);
        
        txtFolio_SubcuentaFiltro.addKeyListener(opFiltroSCC);
        txtSubcuentaFiltro.addKeyListener(opFiltroSCC);
		
//		btnReporte_actual.addActionListener(opGenerar);
	}
	
	KeyListener opFiltroCC = new KeyListener(){
		public void keyReleased(KeyEvent arg0) {
			new Obj_Filtro_Dinamico(tabla_cuentas_contables,"Folio", txtFolio_Cuenta_Contablebusqueda.getText().toUpperCase(),"Cuenta",txtCuenta_Contablebusqueda.getText().toUpperCase(), "", "", "", "");
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}		
	};
	
	KeyListener opFiltroSCC = new KeyListener(){
		public void keyReleased(KeyEvent arg0) {
			new Obj_Filtro_Dinamico(tablaSub_Cuenta_Contable,"Folio Cuenta", txtFolio_Cuenta_Contable.getText().toUpperCase(),"Folio SubCuenta",txtFolio_SubcuentaFiltro.getText().toUpperCase(), "SubCuenta", txtSubcuentaFiltro.getText().toUpperCase(), "", "");
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}		
	};
	
	public void deshabilitarcomponentes(){
		txtFolio_Cuenta_Contable.setEditable(false);
		txtCuenta_Contable.setEditable(false);
		cmbNaturaleza_Cuenta_Contable.setEnabled(false);
		cmbGrupo_Cuenta_Contable.setEnabled(false);
		cmbClasif_Cuenta_Contable.setEnabled(false);
        cmbStatus_Cuenta_Contable.setEnabled(false);  
	}
	
	public void habilitarcomponentes_cuentas_contables(){
		txtFolio_Cuenta_Contable.setEditable(true);
		txtCuenta_Contable.setEditable(true);
		cmbNaturaleza_Cuenta_Contable.setEnabled(true);
		cmbGrupo_Cuenta_Contable.setEnabled(true);
		cmbClasif_Cuenta_Contable.setEnabled(true);
        cmbStatus_Cuenta_Contable.setEnabled(true);  
	}
	
	private void agregar_cuenta_click(final JTable tabl) {
        tabl.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
	        	if(e.getClickCount()==1){
	        		deshabilitarcomponentes();
	        		int fila = tabla_cuentas_contables.getSelectedRow();
	        		txtFolio_Cuenta_Contable.setText(tabla_cuentas_contables.getValueAt(fila, 0).toString().trim());
	        		txtCuenta_Contable.setText(tabla_cuentas_contables.getValueAt(fila, 1).toString());
	        		cmbNaturaleza_Cuenta_Contable.setSelectedItem(tabla_cuentas_contables.getValueAt(fila, 2).toString().trim());
	        		cmbGrupo_Cuenta_Contable.setSelectedItem(tabla_cuentas_contables.getValueAt(fila, 3).toString().trim());
	        		cmbClasif_Cuenta_Contable.setSelectedItem(tabla_cuentas_contables.getValueAt(fila, 4).toString().trim());
	        		cmbStatus_Cuenta_Contable.setSelectedItem(tabla_cuentas_contables.getValueAt(fila, 5).toString().trim());
					btnEditarCC.setEnabled(true);
					
					new Obj_Filtro_Dinamico(tablaSub_Cuenta_Contable,"Folio Cuenta", txtFolio_Cuenta_Contable.getText().toUpperCase(),"Folio SubCuenta",txtFolio_SubcuentaFiltro.getText().toUpperCase(), "SubCuenta", txtSubcuentaFiltro.getText().toUpperCase(), "", "");

	        	}
	        }
        });
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
					
				    int ancho_columnas= ((ancho-30)/5);
					tabla_cuentas_contables.getColumnModel().getColumn(0).setMinWidth(0);
				    tabla_cuentas_contables.getColumnModel().getColumn(0).setMaxWidth(95);
					tabla_cuentas_contables.getColumnModel().getColumn(1).setMinWidth(0);
					tabla_cuentas_contables.getColumnModel().getColumn(1).setMaxWidth(ancho_columnas);
					tabla_cuentas_contables.getColumnModel().getColumn(2).setMinWidth(0);
					tabla_cuentas_contables.getColumnModel().getColumn(2).setMaxWidth(ancho_columnas);
					tabla_cuentas_contables.getColumnModel().getColumn(3).setMinWidth(0);
					tabla_cuentas_contables.getColumnModel().getColumn(3).setMaxWidth(ancho_columnas);
					tabla_cuentas_contables.getColumnModel().getColumn(4).setMinWidth(0);
					tabla_cuentas_contables.getColumnModel().getColumn(4).setMaxWidth(ancho_columnas);
					tabla_cuentas_contables.getColumnModel().getColumn(5).setMinWidth(0);
					tabla_cuentas_contables.getColumnModel().getColumn(5).setMaxWidth(ancho_columnas);
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
//DIMENCIONES
//Tabla Cuentas Contables {"Folio", "Cuenta","Naturaleza","Grupo","Clasificacion","Estatus" }

public void tabla_Subcuentas_Contables(){
	llenado_tabla_subcuentas_contables(2);
	tablaSub_Cuenta_Contable.getTableHeader().setReorderingAllowed(false) ;
	tablaSub_Cuenta_Contable.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
	tablaSub_Cuenta_Contable.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
	tablaSub_Cuenta_Contable.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
	tablaSub_Cuenta_Contable.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
	tablaSub_Cuenta_Contable.getColumnModel().getColumn(4).setCellRenderer(new tablaRenderer("fecha","izquierda","Arial","normal",12));

	int ancho_columnas= ((ancho-30)/5);
	tablaSub_Cuenta_Contable.getColumnModel().getColumn(0).setMinWidth(0);
	tablaSub_Cuenta_Contable.getColumnModel().getColumn(0).setMaxWidth(95);
	tablaSub_Cuenta_Contable.getColumnModel().getColumn(1).setMinWidth(0);
	tablaSub_Cuenta_Contable.getColumnModel().getColumn(1).setMaxWidth(95);
	tablaSub_Cuenta_Contable.getColumnModel().getColumn(2).setMinWidth(0);
	tablaSub_Cuenta_Contable.getColumnModel().getColumn(2).setMaxWidth(ancho_columnas);
	tablaSub_Cuenta_Contable.getColumnModel().getColumn(3).setMinWidth(0);
	tablaSub_Cuenta_Contable.getColumnModel().getColumn(3).setMaxWidth(140);
	tablaSub_Cuenta_Contable.getColumnModel().getColumn(4).setMinWidth(0);
	tablaSub_Cuenta_Contable.getColumnModel().getColumn(4).setMaxWidth(95);
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
	fila[3] = "";
	fila[4] = getTablaCuentaContable[i][3]+"";
	modeloSub_Cuenta_Contable.addRow(fila); }
}

////CONSULTA 
public Object[][] Consulta_para_llenar_Subcuenas(){
//String fecha  = new SimpleDateFormat("dd/MM/yyyy").format(cfecha.getDate())+" 23:59:00";
String consulta = "select folio_cuenta_contable,folio_subcuenta_contable,subcuenta_contable,"
  		           + "   case when tb_subcuentas_contables.status='V' then 'Vigente'"
					+ "	       when tb_subcuentas_contables.status='C' then 'Cancelado' end from tb_subcuentas_contables";

		Statement s;
		ResultSet rs2;
		try {
		s = new Connexion().conexion().createStatement();
		rs2 = s.executeQuery(consulta);
		Matriz_Subcuenta_Contable = new Object[getFilasSCOI(consulta)][4];
		int i=0;
		while(rs2.next()){
			Matriz_Subcuenta_Contable[i][0] = "   "+rs2.getString(1).trim();
			Matriz_Subcuenta_Contable[i][1] = "   "+rs2.getString(2).trim();
			Matriz_Subcuenta_Contable[i][2] = "   "+rs2.getString(3).trim();
			Matriz_Subcuenta_Contable[i][3] = "   "+rs2.getString(4).trim();
		i++;
		}
} catch (SQLException e1) {
	e1.printStackTrace();
	JOptionPane.showMessageDialog(null,"Error En La consulta"+consulta+"\n SQLException"+e1.getMessage(),"Avise Al Administrador del Sistema",JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
}
return Matriz_Subcuenta_Contable; 
}

///////////////TODO ACTION LISTENERS CUENTAS CONTABLES
	    
		ActionListener nuevo_cuentas_contables = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				habilitarcomponentes_cuentas_contables();

			}
		};
		
		ActionListener editar_cuentas_contables = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				habilitarcomponentes_cuentas_contables();

			}
		};
		
		ActionListener deshacer_cuentas_contables = new ActionListener(){
			public void actionPerformed(ActionEvent e){

				deshabilitarcomponentes();
				txtFolio_Cuenta_Contable.setText("");
				txtCuenta_Contable.setText("");
				new Obj_Filtro_Dinamico(tablaSub_Cuenta_Contable,"Folio Cuenta", txtFolio_Cuenta_Contable.getText().toUpperCase(),"Folio SubCuenta",txtFolio_SubcuentaFiltro.getText().toUpperCase(), "SubCuenta", txtSubcuentaFiltro.getText().toUpperCase(), "", "");

			}
		};
		
		ActionListener guardar_cuentas_contables = new ActionListener(){
			public void actionPerformed(ActionEvent e){

				deshabilitarcomponentes();
			}
		};
		
	    
	public static void main(String args[]){
		try{			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Cuentas_Contables().setVisible(true);
		}catch(Exception e){	}	}
}
