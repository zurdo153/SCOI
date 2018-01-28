package Cat_Compras;

import java.awt.Color;
import java.awt.Container;
import java.awt.Event;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Connexion;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Compras.Obj_Programacion_De_Proveedores;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_tabla;
import Obj_Seguridad.Obj_Registro_Proveedores;

@SuppressWarnings("serial")
public class Cat_Programacion_De_Proveedores extends JFrame{
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	Connexion con = new Connexion();
	boolean bloqueocolumna=false;
	
	Obj_tabla ObjTab =new Obj_tabla();
	Obj_Registro_Proveedores proveedores = new Obj_Registro_Proveedores();
	Obj_Usuario usuario = new Obj_Usuario().LeerSession();
	
	int columnas = 10,checkbox=-1;
	
	@SuppressWarnings("rawtypes")
	public Class[] baselunes (){
		Class[] types = new Class[columnas];
		for(int i = 0; i<columnas; i++){types[i]= java.lang.Object.class;}
		 return types;
	}
	public DefaultTableModel modelLunes = new DefaultTableModel(null, new String[]{ "Orden","Tipo Visita","Cod Prov","Proveedor","Orden De Compra","Llegada","Salida","Observacion", "Usuario Programo","Fecha"}){
		 @SuppressWarnings("rawtypes")
			Class[] types = baselunes();
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
			public boolean isCellEditable(int fila, int columna){if(columna>2){return bloqueocolumna;}else{ return false;}}
	};
	JTable tablaLunes = new JTable(modelLunes);
	public JScrollPane Scroll_TablaLunes = new JScrollPane(tablaLunes);
     @SuppressWarnings({ "rawtypes", "unused" })
    private TableRowSorter trsfiltroLunes;
     
     
 	@SuppressWarnings("rawtypes")
 	public Class[] basemartes (){
 		Class[] types = new Class[columnas];
 		for(int i = 0; i<columnas; i++){types[i]= java.lang.Object.class;}
 		 return types;
 	}
 	public DefaultTableModel modelMartes = new DefaultTableModel(null, new String[]{"Orden","Tipo Visita","Cod Prov","Proveedor","Orden De Compra","Llegada","Salida","Observacion", "Usuario Programo","Fecha"}){
 		 @SuppressWarnings("rawtypes")
 			Class[] types = basemartes();
 			@SuppressWarnings({ "rawtypes", "unchecked" })
 			public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
 			public boolean isCellEditable(int fila, int columna){if(columna>2){return bloqueocolumna;}else{ return false;}}
 	};
 	JTable tablaMartes = new JTable(modelMartes);
 	public JScrollPane Scroll_TablaMartes = new JScrollPane(tablaMartes);
      @SuppressWarnings({ "rawtypes", "unused" })
    private TableRowSorter trsfiltroMartes;
      
   	@SuppressWarnings("rawtypes")
   	public Class[] basemiercoles (){
   		Class[] types = new Class[columnas];
   		for(int i = 0; i<columnas; i++){types[i]= java.lang.Object.class;}
   		 return types;
   	}
   	
   	public DefaultTableModel modelMiercoles = new DefaultTableModel(null, new String[]{"Orden","Tipo Visita","Cod Prov","Proveedor","Orden De Compra","Llegada","Salida","Observacion", "Usuario Programo","Fecha"}){
   		 @SuppressWarnings("rawtypes")
   			Class[] types = basemiercoles();
   			@SuppressWarnings({ "rawtypes", "unchecked" })
   		public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
   		public boolean isCellEditable(int fila, int columna){if(columna>2){return bloqueocolumna;}else{ return false;}}
   	};
   	
   	JTable tablaMiercoles = new JTable(modelMiercoles);
   	public JScrollPane Scroll_TablaMiercoles = new JScrollPane(tablaMiercoles);
        @SuppressWarnings({ "rawtypes", "unused" })
    private TableRowSorter trsfiltroMiercoles;  
        
    @SuppressWarnings("rawtypes")
   	public Class[] baseJueves (){
       		Class[] types = new Class[columnas];
       		for(int i = 0; i<columnas; i++){types[i]= java.lang.Object.class;}
    return types;
   	}
    
    public DefaultTableModel modelJueves = new DefaultTableModel(null, new String[]{"Orden","Tipo Visita","Cod Prov","Proveedor","Orden De Compra","Llegada","Salida","Observacion", "Usuario Programo","Fecha"}){
     @SuppressWarnings("rawtypes")
       	Class[] types = baseJueves();
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
    public boolean isCellEditable(int fila, int columna){if(columna>2){return bloqueocolumna;}else{ return false;}}
    };
    
    JTable tablaJueves = new JTable(modelJueves);
    public JScrollPane Scroll_TablaJueves = new JScrollPane(tablaJueves);
        @SuppressWarnings({ "rawtypes", "unused" })
    private TableRowSorter trsfiltroJueves;      
        
    @SuppressWarnings("rawtypes")
  	public Class[] baseViernes (){
        Class[] types = new Class[columnas];
        for(int i = 0; i<columnas; i++){types[i]= java.lang.Object.class;}
    return types;
    }
    public DefaultTableModel modelViernes = new DefaultTableModel(null, new String[]{"Orden","Tipo Visita","Cod Prov","Proveedor","Orden De Compra","Llegada","Salida","Observacion", "Usuario Programo","Fecha"}){
        @SuppressWarnings("rawtypes")
      Class[] types = baseViernes();
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
    public boolean isCellEditable(int fila, int columna){if(columna>2){return bloqueocolumna;}else{ return false;}}
    };
    
    JTable tablaViernes = new JTable(modelViernes);
    public JScrollPane Scroll_TablaViernes = new JScrollPane(tablaViernes);
    @SuppressWarnings({ "rawtypes", "unused" })
    private TableRowSorter trsfiltroViernes;  
  
    
    @SuppressWarnings("rawtypes")
  	public Class[] baseSabado (){
        Class[] types = new Class[columnas];
        for(int i = 0; i<columnas; i++){types[i]= java.lang.Object.class;}
    return types;
    }
    public DefaultTableModel modelSabado = new DefaultTableModel(null, new String[]{"Orden","Tipo Visita","Cod Prov","Proveedor","Orden De Compra","Llegada","Salida","Observacion", "Usuario Programo","Fecha"}){
        @SuppressWarnings("rawtypes")
      Class[] types = baseSabado();
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
    public boolean isCellEditable(int fila, int columna){if(columna>2){return bloqueocolumna;}else{ return false;}}
    };
    
    JTable tablaSabado = new JTable(modelSabado);
    public JScrollPane Scroll_TablaSabado = new JScrollPane(tablaSabado);
    @SuppressWarnings({ "rawtypes", "unused" })
    private TableRowSorter trsfiltroSabado;  
                
    
    @SuppressWarnings("rawtypes")
  	public Class[] baseDomingo (){
        Class[] types = new Class[columnas];
        for(int i = 0; i<columnas; i++){types[i]= java.lang.Object.class;}
    return types;
    }
    public DefaultTableModel modelDomingo = new DefaultTableModel(null, new String[]{"Orden","Tipo Visita","Cod Prov","Proveedor","Orden De Compra","Llegada","Salida","Observacion", "Usuario Programo","Fecha"}){
        @SuppressWarnings("rawtypes")
      Class[] types = baseDomingo();
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
    public boolean isCellEditable(int fila, int columna){if(columna>2){return bloqueocolumna;}else{ return false;}}
    };
    JTable tablaDomingo = new JTable(modelDomingo);
    public JScrollPane Scroll_TablaDomingo = new JScrollPane(tablaDomingo);
    @SuppressWarnings({ "rawtypes", "unused" })
    private TableRowSorter trsfiltroDomingo;  
    
    int columnasor=6;
    @SuppressWarnings("rawtypes")
 	public Class[] baseorden (){
 		Class[] types = new Class[columnasor];
 		for(int i = 0; i<columnasor; i++){types[i]= java.lang.Object.class;}
 		 return types;
 	}
 	public DefaultTableModel modeloorden = new DefaultTableModel(null, new String[]{"Factura","Importe","Oden De Compra","Importe","Diferencia","Tipo Proveedor"}){
 		 @SuppressWarnings("rawtypes")
 			Class[] types = baseorden();
 			@SuppressWarnings({ "rawtypes", "unchecked" })
 			public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
 			public boolean isCellEditable(int fila, int columna){
 				return false;
 				}
 	};
   	JTable tablaOrden = new JTable(modeloorden);
   	public JScrollPane Scroll_tablaOrden = new JScrollPane(tablaOrden);
        @SuppressWarnings({ "rawtypes", "unused" })
       private TableRowSorter trsfiltroOrden;
        
    int columnas2 = 11,checkbox2=1;
	public void init_tabla(){
    	this.tablafa.getColumnModel().getColumn(0).setMinWidth(20);
    	this.tablafa.getColumnModel().getColumn(0).setMaxWidth(20);
    	this.tablafa.getColumnModel().getColumn(1).setMinWidth(50);
    	this.tablafa.getColumnModel().getColumn(1).setMaxWidth(50);
    	this.tablafa.getColumnModel().getColumn(2).setMinWidth(250);
    	this.tablafa.getColumnModel().getColumn(3).setMinWidth(300);
    	this.tablafa.getColumnModel().getColumn(4).setMinWidth(80);
    	this.tablafa.getColumnModel().getColumn(5).setMinWidth(110);
    	this.tablafa.getColumnModel().getColumn(6).setMinWidth(60);
    	this.tablafa.getColumnModel().getColumn(7).setMinWidth(110);
    	this.tablafa.getColumnModel().getColumn(8).setMinWidth(60);
    	this.tablafa.getColumnModel().getColumn(9).setMinWidth(60);
    	this.tablafa.getColumnModel().getColumn(10).setMinWidth(60);
		String comandof="exec cuadrantes_actividades_filtro_cuadrantes";
		String basedatos="26",pintar="si";
		ObjTab.Obj_Refrescar(tablafa,modeloa, columnas2, comandof, basedatos,pintar,checkbox2);
    }
    
    @SuppressWarnings("rawtypes")
	public Class[] base (){
		Class[] types = new Class[columnas2];
		for(int i =0; i<columnas2; i++){  
			if(i==0){
				types[i]=java.lang.Boolean.class;
			}else{
				types[i]=java.lang.Object.class;
			}
		}
		 return types;
	}
	
	public DefaultTableModel modeloa = new DefaultTableModel(null, new String[]{"S","Folio","Actividad","Descripcion","Tipo Respuesta","Aspecto","Nivel Critico","Temporada","Exige Evidencia","Exige Observacion","Estatus"}){
		 @SuppressWarnings("rawtypes")
			Class[] types = base();
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
			public boolean isCellEditable(int fila, int columnas2){
				if(columnas2==0)return true; return false;
				}
	};
    JTable tablafa = new JTable(modeloa);
	public JScrollPane scroll_tabla_a = new JScrollPane(tablafa);
	
	JTextField txtBuscarLunes     = new Componentes().text(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla Lunes<<<"    , 300, "String");
	JTextField txtBuscarMartes    = new Componentes().text(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla Martes<<<"   , 300, "String");
	JTextField txtBuscarMiercoles = new Componentes().text(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla Miercoles<<<", 300, "String");
	JTextField txtBuscarJueves    = new Componentes().text(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla Jueves<<<"   , 300, "String");
	JTextField txtBuscarViernes   = new Componentes().text(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla Viernes<<<"  , 300, "String");
	JTextField txtBuscarSabado    = new Componentes().text(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla Sabado<<<"   , 300, "String");
	JTextField txtBuscarDomingo   = new Componentes().text(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla Domingo<<<"  , 300, "String");
	JTextField txtFolio           = new Componentes().text(new JCTextField(), "Folio"                                                            ,  10, "Int"   );
	JTextField txtProveedor       = new Componentes().text(new JCTextField(), "Proveedor"                                                        , 200, "String");
	JTextField txtFecha           = new Componentes().text(new JCTextField(), "Fecha"                                                            ,  60, "String");
	JTextField txtSemana          = new Componentes().text(new JCTextField(), "Semana"                                                           ,   2, "String");
	
	String status[] = {"Vigente","Cancelado"};
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmb_status = new JComboBox(status);
	
	String establecimientoScoi[] = new Obj_Establecimiento().Combo_Establecimiento();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbEstablecimiento = new JComboBox(establecimientoScoi);
	
	String anio[] = new Obj_Programacion_De_Proveedores().Combo_Anio(0,"An");
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbAnio = new JComboBox(anio);
	
	String semana_del_anio[] =  new Obj_Programacion_De_Proveedores().Combo_Semanas_Del_Año(0,"St" ,cmbEstablecimiento.getSelectedItem().toString().trim());
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbSemanas_del_anio = new JComboBox(semana_del_anio);
	
	JToolBar menu_toolbar = new JToolBar();
	JCButton btnBuscar    = new JCButton("Buscar"    ,"Filter-List-icon16.png"     ,"Azul"); 
	JCButton btnNuevo     = new JCButton("Nuevo"     ,"Nuevo.png"                  ,"Azul");
	JCButton btnModificar = new JCButton("Modificar" ,"Modify.png"                 ,"Azul");
	JCButton btnGuardar   = new JCButton("Guardar"   ,"Guardar.png"                ,"Azul");
	JCButton btnDeshacer  = new JCButton("Deshacer"  ,"deshacer16.png"             ,"Azul");
	JCButton btnderecha   = new JCButton(""          ,"adelante.png"               ,"Azul");
	JCButton btnizquierda = new JCButton(""          ,"atras.png"                  ,"Azul");
	
	JToolBar toolbarLunes         = new JToolBar();
	JCButton btnAgregLunes        = new JCButton("Agregar Provedor Lunes","double-arrow-icone-3883-16.png"  ,"Azul" );
	JCButton btnSubLunes          = new JCButton("Subir"  ,"Up.png"                          ,"Azul" );
	JCButton btnBajLunes          = new JCButton("Bajar"  ,"Down.png"                        ,"Azul" );
	JCButton btnEljLunes          = new JCButton("Eliminar","eliminar-bala-icono-7773-32.png","Azul" );
	
	JToolBar toolbarMartes        = new JToolBar();
	JCButton btnAgregMartes       = new JCButton("Agregar Provedor Martes","double-arrow-icone-3883-16.png"  ,"Azul" );
	JCButton btnSubMartes         = new JCButton("Subir"  ,"Up.png"                          ,"Azul" );
	JCButton btnBajMartes         = new JCButton("Bajar"  ,"Down.png"                        ,"Azul" );
	JCButton btnEljMartes         = new JCButton("Eliminar","eliminar-bala-icono-7773-32.png","Azul" );
	
	JToolBar toolbarMiercoles     = new JToolBar();
	JCButton btnAgregMiercoles    = new JCButton("Agregar Provedor Miercoles","double-arrow-icone-3883-16.png"  ,"Azul" );
	JCButton btnSubMiercoles      = new JCButton("Subir"  ,"Up.png"                          ,"Azul" );
	JCButton btnBajMiercoles      = new JCButton("Bajar"  ,"Down.png"                        ,"Azul" );
	JCButton btnEljMiercoles      = new JCButton("Eliminar","eliminar-bala-icono-7773-32.png","Azul" );
	
	JToolBar toolbarJueves        = new JToolBar();
	JCButton btnAgregJueves       = new JCButton("Agregar Provedor Jueves","double-arrow-icone-3883-16.png"  ,"Azul" );
	JCButton btnSubJueves         = new JCButton("Subir"  ,"Up.png"                          ,"Azul" );
	JCButton btnBajJueves         = new JCButton("Bajar"  ,"Down.png"                        ,"Azul" );
	JCButton btnEljJueves         = new JCButton("Eliminar","eliminar-bala-icono-7773-32.png","Azul" );
	
	JToolBar toolbarViernes       = new JToolBar();
	JCButton btnAgregViernes      = new JCButton("Agregar Provedor Viernes","double-arrow-icone-3883-16.png"  ,"Azul" );
	JCButton btnSubViernes        = new JCButton("Subir"  ,"Up.png"                          ,"Azul" );
	JCButton btnBajViernes        = new JCButton("Bajar"  ,"Down.png"                        ,"Azul" );
	JCButton btnEljViernes        = new JCButton("Eliminar","eliminar-bala-icono-7773-32.png","Azul" );
	
	JToolBar toolbarSabado        = new JToolBar();
	JCButton btnAgregSabado       = new JCButton("Agregar Provedor Sabado","double-arrow-icone-3883-16.png"  ,"Azul" );
	JCButton btnSubSabado         = new JCButton("Subir"  ,"Up.png"                          ,"Azul" );
	JCButton btnBajSabado         = new JCButton("Bajar"  ,"Down.png"                        ,"Azul" );
	JCButton btnEljSabado         = new JCButton("Eliminar","eliminar-bala-icono-7773-32.png","Azul" );
	
	JToolBar toolbarDomingo       = new JToolBar();
	JCButton btnAgregDomingo      = new JCButton("Agregar Provedor Domingo","double-arrow-icone-3883-16.png"  ,"Azul" );
	JCButton btnSubDomingo        = new JCButton("Subir"  ,"Up.png"                          ,"Azul" );
	JCButton btnBajDomingo        = new JCButton("Bajar"  ,"Down.png"                        ,"Azul" );
	JCButton btnEljDomingo        = new JCButton("Eliminar","eliminar-bala-icono-7773-32.png","Azul" );
	
	JTabbedPane pestanas    = new JTabbedPane();
	JLayeredPane pDomingo   = new JLayeredPane(); 
	JLayeredPane pLunes     = new JLayeredPane(); 
	JLayeredPane pMarte     = new JLayeredPane();
	JLayeredPane pMiercoles = new JLayeredPane(); 
	JLayeredPane pJueves    = new JLayeredPane(); 
	JLayeredPane pViernes   = new JLayeredPane(); 
	JLayeredPane pSabado    = new JLayeredPane(); 
	
	String NuevoModifica ="";
	String FActividadesCargado ="";
	String[][] tablaprecargadaactividades;
	public Cat_Programacion_De_Proveedores(){
		this.setSize(1024,590);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/favoritos-ver-boton-icono-8318-32.png"));
		this.setTitle("Programación de Proveedores");
		this.panel.setBorder(BorderFactory.createTitledBorder("Programación De Proveedores"));
		
		this.menu_toolbar.add(btnBuscar  );
	    this.menu_toolbar.addSeparator(  );
	    this.menu_toolbar.addSeparator(  );
		this.menu_toolbar.add(btnModificar);
	    this.menu_toolbar.addSeparator(  );
	    this.menu_toolbar.addSeparator(  );
	    this.menu_toolbar.add(btnNuevo   );
	    this.menu_toolbar.addSeparator(  );
	    this.menu_toolbar.addSeparator(  );
		this.menu_toolbar.add(btnDeshacer);
		this.menu_toolbar.addSeparator(  );
		this.menu_toolbar.addSeparator(  );
		this.menu_toolbar.add(btnGuardar );
		this.menu_toolbar.setFloatable(false);
		
		this.pestanas.addTab("Lunes"    ,pLunes    );
		this.init_tablalunes();
		this.pestanas.addTab("Martes"   ,pMarte    );
		this.init_tablamartes();
		this.pestanas.addTab("Miércoles",pMiercoles);
		this.init_tablamiercoles();
		this.pestanas.addTab("Jueves"   ,pJueves   );
		this.init_tablajueves();
		this.pestanas.addTab("Viernes"  ,pViernes  );
		this.init_tablaviernes();
		this.pestanas.addTab("Sábado"   ,pSabado   );
		this.init_tablasabado();
		this.pestanas.addTab("Domingo"  ,pDomingo  );
		this.init_tablaDomingo();
	
		 int x=15, y=20,width=120,height=20,sep=40;
		this.panel.add(menu_toolbar).setBounds                  (x     ,y      ,width*4    ,height );
		this.panel.add(new JLabel("Folio:")).setBounds          (x     ,y+=sep ,width      ,height );
		this.panel.add(txtFolio).setBounds                      (x+=sep,y      ,width      ,height );
        this.panel.add(btnizquierda).setBounds                  (x+=125,y      ,height     ,height );
        this.panel.add(btnderecha).setBounds                    (x+=25 ,y      ,height     ,height );
		this.panel.add(new JLabel("Estatus:")).setBounds        (x+=sep,y      ,width      ,height );
		this.panel.add(cmb_status).setBounds                    (x+=sep,y      ,width-50   ,height );
		this.panel.add(new JLabel("Establecimiento:")).setBounds(x+=80 ,y      ,width      ,height );
		this.panel.add(cmbEstablecimiento).setBounds            (x+=80 ,y      ,170        ,height );
		this.panel.add(new JLabel("Año:")).setBounds            (x+=180,y      ,width      ,height );
		this.panel.add(cmbAnio).setBounds                       (x+=25 ,y      ,50         ,height );
		this.panel.add(new JLabel("Semana Del Año:")).setBounds (x+=60 ,y      ,width      ,height );
		this.panel.add(cmbSemanas_del_anio).setBounds           (x+=85 ,y      ,50         ,height );
		this.panel.add(new JLabel("Fecha Actual:")).setBounds   (x+=60 ,y      ,width      ,height );
		this.panel.add(txtFecha).setBounds                      (x+=72 ,y      ,75         ,height );
		this.panel.add(new JLabel("Semana Actual:")).setBounds  (x-=72 ,y+25   ,width      ,height );
		this.panel.add(txtSemana).setBounds                     (x+=72 ,y+25   ,75         ,height );
		this.panel.add(pestanas).setBounds                      (x=15  ,y+25   ,990        ,460    );
		
		try { txtFecha.setText(new BuscarSQL().fecha(0).toString());} catch (SQLException e1) {e1.printStackTrace();}
		try { txtSemana.setText(new BuscarSQL().semana_anio().toString());} catch (SQLException e1) {e1.printStackTrace();}
	    this.txtFecha.setEditable (false); 
		this.txtSemana.setEditable(false); 
		
		this.panel_boolean(false);
		this.btnNuevo.addActionListener     (nuevo          );		
		this.btnGuardar.addActionListener   (guardar        );
		this.btnDeshacer.addActionListener  (deshacer       );
		this.btnModificar.addActionListener (editar         );
		this.btnBuscar.addActionListener    (buscar         );
		this.btnderecha.addActionListener   (opDerecha      );
		this.btnizquierda.addActionListener (opIzquierda    );
		this.cmbAnio.addActionListener      (Op_Cambio_Anio );
		
		cont.add(panel);
		
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape"                );
        getRootPane().getActionMap().put("escape", new AbstractAction(){ public void actionPerformed(ActionEvent e){ btnDeshacer.doClick(); }  });
         
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F9, 0), "nuevo"                     );
        getRootPane().getActionMap().put("nuevo", new AbstractAction(){ public void actionPerformed(ActionEvent e){ btnNuevo.doClick();     }  });
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_N,Event.CTRL_MASK),"nuevo"          );
        getRootPane().getActionMap().put("nuevo", new AbstractAction(){ public void actionPerformed(ActionEvent e) { btnNuevo.doClick();    }  });
         
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_G,Event.CTRL_MASK),"guardar"        );
        getRootPane().getActionMap().put("guardar", new AbstractAction(){public void actionPerformed(ActionEvent e){btnGuardar.doClick();   }  });
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0), "guardar"                  );
        getRootPane().getActionMap().put("guardar", new AbstractAction(){public void actionPerformed(ActionEvent e) { btnGuardar.doClick(); }  });    
        
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), "buscar"                );
        getRootPane().getActionMap().put("buscar", new AbstractAction(){ public void actionPerformed(ActionEvent e){ btnBuscar.doClick(); }  });
	}
	
	@SuppressWarnings("unchecked")
	public void cargar_datos_tablas(int folioparametro){
	  Obj_Programacion_De_Proveedores proveedores = new Obj_Programacion_De_Proveedores();
	  String[][] tablacompleta= proveedores.refrescar_tablas(folioparametro);
	  Object[]   vector = new Object[10];

	  cmbAnio.removeActionListener(Op_Cambio_Anio);
	  cmbAnio.removeAllItems();
	  String anio_todos[] = new Obj_Programacion_De_Proveedores().Combo_Anio(0,"At");
	  for(int i=0;i<anio_todos.length;i++){
		  cmbAnio.addItem(anio_todos[i].toString().trim());
	  }
	  cmbAnio.addActionListener(Op_Cambio_Anio);
	  cmbAnio.setSelectedItem            (tablacompleta[0][13].toString());	 
	  
	  
	  cmbSemanas_del_anio.removeAllItems();
	  String semana_del_aniop[] = new Obj_Programacion_De_Proveedores().Combo_Semanas_Del_Año(0,"St" ,cmbEstablecimiento.getSelectedItem().toString().trim());
	  for(int i=0;i<semana_del_aniop.length;i++){
		  cmbSemanas_del_anio.addItem(semana_del_aniop[i].toString().trim());
	  }
	  cmbSemanas_del_anio.setSelectedItem(tablacompleta[0][14].toString());
	  
	  
	  txtFolio.setText                   (tablacompleta[0][10].toString());
	  cmbEstablecimiento.setSelectedItem (tablacompleta[0][12].toString());
	  cmb_status.setSelectedItem         (tablacompleta[0][16].toString());
	  
	for(int i=0;i<tablacompleta.length;i++){
		if(Integer.valueOf(tablacompleta[i][17].toString())==1){
			
			for(int j=0;j<10;j++){
			vector[j] = tablacompleta[i][j].toString();
			}
			modelLunes.addRow(vector);
		}
		
        if(Integer.valueOf(tablacompleta[i][17].toString())==2){
        	for(int j=0;j<10;j++){
    		vector[j] = tablacompleta[i][j].toString();
    		}
    			modelMartes.addRow(vector);
		}
        
        if(Integer.valueOf(tablacompleta[i][17].toString())==3){
        	for(int j=0;j<10;j++){
    		vector[j] = tablacompleta[i][j].toString();
    		}
    			modelMiercoles.addRow(vector);
		}
        
        if(Integer.valueOf(tablacompleta[i][17].toString())==4){
        	for(int j=0;j<10;j++){
    		vector[j] = tablacompleta[i][j].toString();
    		}
    			modelJueves.addRow(vector);
		}
        
        if(Integer.valueOf(tablacompleta[i][17].toString())==5){
        	for(int j=0;j<10;j++){
    		vector[j] = tablacompleta[i][j].toString();
    		}
    			modelViernes.addRow(vector);
		}
        
        if(Integer.valueOf(tablacompleta[i][17].toString())==6){
        	for(int j=0;j<10;j++){
    		vector[j] = tablacompleta[i][j].toString();
    		}
    			modelSabado.addRow(vector);
		}
        
        if(Integer.valueOf(tablacompleta[i][17].toString())==7){
        	for(int j=0;j<10;j++){
    		vector[j] = tablacompleta[i][j].toString();
    		}
    			modelDomingo.addRow(vector);
		}
	 }	
	}
	
	public void init_tablalunes(){
		ObjTab.tabla_programacion_proveedores_mascara(tablaLunes,5,6);
		this.pLunes.setBorder(BorderFactory.createTitledBorder("Lunes"));
		this.pLunes.setOpaque(true); 
		this.pLunes.setBackground(new Color(Integer.parseInt("EBEBEB",16)));
	    this.toolbarLunes.add(btnAgregLunes);
		this.toolbarLunes.addSeparator(    );
		this.toolbarLunes.addSeparator(    );
		this.toolbarLunes.add(btnSubLunes  );
	    this.toolbarLunes.addSeparator(    );
	    this.toolbarLunes.addSeparator(    );
	    this.toolbarLunes.add(btnBajLunes  );
	    this.toolbarLunes.addSeparator(    );
	    this.toolbarLunes.addSeparator(    );
	    this.toolbarLunes.add(btnEljLunes  );
		this.toolbarLunes.setFloatable(false);
		 int x=10, y=15,width=965,height=20,sev=25;
		this.pLunes.add(toolbarLunes).setBounds               (x     ,y      ,width    ,height );
		this.pLunes.add(txtBuscarLunes).setBounds             (x     ,y+=sev ,width    ,height );
		this.pLunes.add(Scroll_TablaLunes).setBounds          (x     ,y+=20  ,width    ,360    ); 
		this.txtBuscarLunes.addKeyListener(opFiltro_lunes);
		this.tablaLunes.addKeyListener(new op_validacelda_tabla(tablaLunes));
		this.btnSubLunes.addActionListener(new opMoverArriba(tablaLunes));
		this.btnBajLunes.addActionListener(new opMoverAbajo(tablaLunes));
		this.btnEljLunes.addActionListener(new opEliminarfila(tablaLunes));
		this.btnAgregLunes.addActionListener(new opAgregar_Proveedor(tablaLunes));
    }
	
	public void init_tablamartes(){
		ObjTab.tabla_programacion_proveedores_mascara(tablaMartes,5,6);
		this.pMarte.setBorder(BorderFactory.createTitledBorder("Martes"));
		this.pMarte.setOpaque(true); 
		this.pMarte.setBackground(new Color(Integer.parseInt("EBEBEB",16)));
	    this.toolbarMartes.add(btnAgregMartes);
		this.toolbarMartes.addSeparator(     );
		this.toolbarMartes.addSeparator(     );
		this.toolbarMartes.add(btnSubMartes  );
	    this.toolbarMartes.addSeparator(     );
	    this.toolbarMartes.addSeparator(     );
	    this.toolbarMartes.add(btnBajMartes  );
	    this.toolbarMartes.addSeparator(     );
	    this.toolbarMartes.addSeparator(     );
	    this.toolbarMartes.add(btnEljMartes  );
		this.toolbarMartes.setFloatable(false);
		 int x=10, y=15,width=965,height=20,sev=25;
		this.pMarte.add(toolbarMartes).setBounds               (x     ,y      ,width    ,height );
		this.pMarte.add(txtBuscarMartes).setBounds             (x     ,y+=sev ,width    ,height );
		this.pMarte.add(Scroll_TablaMartes).setBounds          (x     ,y+=20  ,width    ,360    ); 
		this.txtBuscarMartes.addKeyListener (opFiltro_martes);
		this.tablaMartes.addKeyListener(new op_validacelda_tabla(tablaMartes));
		this.btnSubMartes.addActionListener(new opMoverArriba(tablaMartes));
		this.btnBajMartes.addActionListener(new opMoverAbajo(tablaMartes));
		this.btnEljMartes.addActionListener(new opEliminarfila(tablaMartes));
		this.btnAgregMartes.addActionListener(new opAgregar_Proveedor(tablaMartes));
    }
	
	public void init_tablamiercoles(){
		ObjTab.tabla_programacion_proveedores_mascara(tablaMiercoles,5,6);
		this.pMiercoles.setBorder(BorderFactory.createTitledBorder("Miercoles"));
		this.pMiercoles.setOpaque(true); 
		this.pMiercoles.setBackground(new Color(Integer.parseInt("EBEBEB",16)));
	    this.toolbarMiercoles.add(btnAgregMiercoles);
		this.toolbarMiercoles.addSeparator(     );
		this.toolbarMiercoles.addSeparator(     );
		this.toolbarMiercoles.add(btnSubMiercoles  );
	    this.toolbarMiercoles.addSeparator(     );
	    this.toolbarMiercoles.addSeparator(     );
	    this.toolbarMiercoles.add(btnBajMiercoles  );
	    this.toolbarMiercoles.addSeparator(     );
	    this.toolbarMiercoles.addSeparator(     );
	    this.toolbarMiercoles.add(btnEljMiercoles  );
		this.toolbarMiercoles.setFloatable(false);
		 int x=10, y=15,width=965,height=20,sev=25;
		this.pMiercoles.add(toolbarMiercoles).setBounds               (x     ,y      ,width    ,height );
		this.pMiercoles.add(txtBuscarMiercoles).setBounds             (x     ,y+=sev ,width    ,height );
		this.pMiercoles.add(Scroll_TablaMiercoles).setBounds          (x     ,y+=20  ,width    ,360    );
		this.txtBuscarMiercoles.addKeyListener (opFiltro_miercoles);
		this.tablaMiercoles.addKeyListener(new op_validacelda_tabla(tablaMiercoles));
		this.btnSubMiercoles.addActionListener(new opMoverArriba(tablaMiercoles));
		this.btnBajMiercoles.addActionListener(new opMoverAbajo(tablaMiercoles));
		this.btnEljMiercoles.addActionListener(new opEliminarfila(tablaMiercoles));
		this.btnAgregMiercoles.addActionListener(new opAgregar_Proveedor(tablaMiercoles));
    }
	
	public void init_tablajueves(){
	ObjTab.tabla_programacion_proveedores_mascara(tablaJueves,5,6);
	this.pJueves.setBorder(BorderFactory.createTitledBorder("Jueves"));
	this.pJueves.setOpaque(true); 
	this.pJueves.setBackground(new Color(Integer.parseInt("EBEBEB",16)));
    this.toolbarJueves.add(btnAgregJueves);
	this.toolbarJueves.addSeparator(     );
	this.toolbarJueves.addSeparator(     );
	this.toolbarJueves.add(btnSubJueves  );
    this.toolbarJueves.addSeparator(     );
    this.toolbarJueves.addSeparator(     );
    this.toolbarJueves.add(btnBajJueves  );
    this.toolbarJueves.addSeparator(     );
    this.toolbarJueves.addSeparator(     );
    this.toolbarJueves.add(btnEljJueves  );
	this.toolbarJueves.setFloatable(false);
	 int x=10, y=15,width=965,height=20,sev=25;
	this.pJueves.add(toolbarJueves).setBounds               (x     ,y      ,width    ,height );
	this.pJueves.add(txtBuscarJueves).setBounds             (x     ,y+=sev ,width    ,height );
	this.pJueves.add(Scroll_TablaJueves).setBounds          (x     ,y+=20  ,width    ,360    );
	this.txtBuscarJueves.addKeyListener (opFiltro_jueves);
	this.tablaJueves.addKeyListener(new op_validacelda_tabla(tablaJueves));
	this.btnSubJueves.addActionListener(new opMoverArriba(tablaJueves));
	this.btnBajJueves.addActionListener(new opMoverAbajo(tablaJueves));
	this.btnEljJueves.addActionListener(new opEliminarfila(tablaJueves));
	this.btnAgregJueves.addActionListener(new opAgregar_Proveedor(tablaJueves));
	}
	
	public void init_tablaviernes(){
		ObjTab.tabla_programacion_proveedores_mascara(tablaViernes,5,6);
		this.pViernes.setBorder(BorderFactory.createTitledBorder("Viernes"));
		this.pViernes.setOpaque(true); 
		this.pViernes.setBackground(new Color(Integer.parseInt("EBEBEB",16)));
	    this.toolbarViernes.add(btnAgregViernes);
		this.toolbarViernes.addSeparator(      );
		this.toolbarViernes.addSeparator(      );
		this.toolbarViernes.add(btnSubViernes  );
	    this.toolbarViernes.addSeparator(      );
	    this.toolbarViernes.addSeparator(      );
	    this.toolbarViernes.add(btnBajViernes  );
	    this.toolbarViernes.addSeparator(      );
	    this.toolbarViernes.addSeparator(      );
	    this.toolbarViernes.add(btnEljViernes  );
		this.toolbarViernes.setFloatable(false);
		 int x=10, y=15,width=965,height=20,sev=25;
		this.pViernes.add(toolbarViernes).setBounds               (x     ,y      ,width    ,height );
		this.pViernes.add(txtBuscarViernes).setBounds             (x     ,y+=sev ,width    ,height );
		this.pViernes.add(Scroll_TablaViernes).setBounds          (x     ,y+=20  ,width    ,360    );
		this.txtBuscarViernes.addKeyListener (opFiltro_viernes);
		this.tablaViernes.addKeyListener(new op_validacelda_tabla(tablaViernes));
		this.btnSubViernes.addActionListener(new opMoverArriba(tablaViernes));
		this.btnBajViernes.addActionListener(new opMoverAbajo(tablaViernes));
		this.btnEljViernes.addActionListener(new opEliminarfila(tablaViernes));
		this.btnAgregViernes.addActionListener(new opAgregar_Proveedor(tablaViernes));
		}
	
	public void init_tablasabado(){
		ObjTab.tabla_programacion_proveedores_mascara(tablaSabado,5,6);
		this.pSabado.setBorder(BorderFactory.createTitledBorder("Sabado"));
		this.pSabado.setOpaque(true); 
		this.pSabado.setBackground(new Color(Integer.parseInt("EBEBEB",16)));
	    this.toolbarSabado.add(btnAgregSabado);
		this.toolbarSabado.addSeparator(     );
		this.toolbarSabado.addSeparator(     );
		this.toolbarSabado.add(btnSubSabado  );
	    this.toolbarSabado.addSeparator(     );
	    this.toolbarSabado.addSeparator(     );
	    this.toolbarSabado.add(btnBajSabado  );
	    this.toolbarSabado.addSeparator(     );
	    this.toolbarSabado.addSeparator(     );
	    this.toolbarSabado.add(btnEljSabado  );
		this.toolbarSabado.setFloatable(false);
		 int x=10, y=15,width=965,height=20,sev=25;
		this.pSabado.add(toolbarSabado).setBounds               (x     ,y      ,width    ,height );
		this.pSabado.add(txtBuscarSabado).setBounds             (x     ,y+=sev ,width    ,height );
		this.pSabado.add(Scroll_TablaSabado).setBounds          (x     ,y+=20  ,width    ,360    );
		this.txtBuscarSabado.addKeyListener (opFiltro_sabado);
		this.tablaSabado.addKeyListener(new op_validacelda_tabla(tablaSabado));
		this.btnSubSabado.addActionListener(new opMoverArriba(tablaSabado));
		this.btnBajSabado.addActionListener(new opMoverAbajo(tablaSabado));
		this.btnEljSabado.addActionListener(new opEliminarfila(tablaSabado));
		this.btnAgregSabado.addActionListener(new opAgregar_Proveedor(tablaSabado));
		}

	public void init_tablaDomingo(){
		ObjTab.tabla_programacion_proveedores_mascara(tablaDomingo,5,6);
		this.pDomingo.setBorder(BorderFactory.createTitledBorder("Domingo"));
		this.pDomingo.setOpaque(true); 
		this.pDomingo.setBackground(new Color(Integer.parseInt("EBEBEB",16)));
	    this.toolbarDomingo.add(btnAgregDomingo);
		this.toolbarDomingo.addSeparator(      );
		this.toolbarDomingo.addSeparator(      );
		this.toolbarDomingo.add(btnSubDomingo  );
	    this.toolbarDomingo.addSeparator(      );
	    this.toolbarDomingo.addSeparator(      );
	    this.toolbarDomingo.add(btnBajDomingo  );
	    this.toolbarDomingo.addSeparator(      );
	    this.toolbarDomingo.addSeparator(      );
	    this.toolbarDomingo.add(btnEljDomingo  );
		this.toolbarDomingo.setFloatable(false);
		 int x=10, y=15,width=965,height=20,sev=25;
		this.pDomingo.add(toolbarDomingo).setBounds               (x     ,y      ,width    ,height );
		this.pDomingo.add(txtBuscarDomingo).setBounds             (x     ,y+=sev ,width    ,height );
		this.pDomingo.add(Scroll_TablaDomingo).setBounds          (x     ,y+=20  ,width    ,360    );
		this.txtBuscarDomingo.addKeyListener (opFiltro_domingo);
		this.tablaDomingo.addKeyListener(new op_validacelda_tabla(tablaDomingo));
		this.btnSubDomingo.addActionListener(new opMoverArriba(tablaDomingo));
		this.btnBajDomingo.addActionListener(new opMoverAbajo(tablaDomingo));
		this.btnEljDomingo.addActionListener(new opEliminarfila(tablaDomingo));
		this.btnAgregDomingo.addActionListener(new opAgregar_Proveedor(tablaDomingo));
		}
	
	KeyListener opFiltro_lunes = new KeyListener(){
		public void keyReleased(KeyEvent arg0) {
			ObjTab.Obj_Filtro(tablaLunes, txtBuscarLunes.getText(), columnas);
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}		
	};
	
	KeyListener opFiltro_martes = new KeyListener(){
		public void keyReleased(KeyEvent arg0) {
			ObjTab.Obj_Filtro(tablaMartes, txtBuscarMartes.getText(), columnas);
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}		
	};
	
	KeyListener opFiltro_miercoles = new KeyListener(){
		public void keyReleased(KeyEvent arg0) {
			ObjTab.Obj_Filtro(tablaMiercoles, txtBuscarMiercoles.getText(), columnas);
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}		
	};
	
	KeyListener opFiltro_jueves = new KeyListener(){
		public void keyReleased(KeyEvent arg0) {
			ObjTab.Obj_Filtro(tablaJueves, txtBuscarJueves.getText(), columnas);
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}		
	};
	
	KeyListener opFiltro_viernes = new KeyListener(){
		public void keyReleased(KeyEvent arg0) {
			ObjTab.Obj_Filtro(tablaViernes, txtBuscarViernes.getText(), columnas);
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}		
	};
	
	KeyListener opFiltro_sabado = new KeyListener(){
		public void keyReleased(KeyEvent arg0) {
			ObjTab.Obj_Filtro(tablaSabado, txtBuscarSabado.getText(), columnas);
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}		
	};
	
	KeyListener opFiltro_domingo = new KeyListener(){
		public void keyReleased(KeyEvent arg0) {
			ObjTab.Obj_Filtro(tablaDomingo, txtBuscarDomingo.getText(), columnas);
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}		
	};

	
    ActionListener nuevo = new ActionListener(){
	@SuppressWarnings("unchecked")
	public void actionPerformed(ActionEvent e) {
			panelLimpiar();
			panel_boolean(true);
			
		    cmbAnio.removeActionListener(Op_Cambio_Anio);
			cmbAnio.removeAllItems();
			String anio_todos[] = new Obj_Programacion_De_Proveedores().Combo_Anio(0,"An");
			for(int i=0;i<anio_todos.length;i++){
				  cmbAnio.addItem(anio_todos[i].toString().trim());
			}
			cmbAnio.addActionListener(Op_Cambio_Anio);
			  
			cmbSemanas_del_anio.removeAllItems();
			String semana_del_aniop[] = new Obj_Programacion_De_Proveedores().Combo_Semanas_Del_Año(Integer.valueOf(cmbAnio.getSelectedItem().toString().trim()),"Sr" ,cmbEstablecimiento.getSelectedItem().toString().trim());
			for(int i=0;i<semana_del_aniop.length;i++){
			  cmbSemanas_del_anio.addItem(semana_del_aniop[i].toString().trim());
			}
			
			cmbAnio.setSelectedIndex(0);
			txtFolio.setText(new Obj_Programacion_De_Proveedores().Nuevo()+"");
			btnGuardar.setEnabled(true);
			NuevoModifica="N";
			txtFolio.setEditable(false);
			cmb_status.setEnabled(false);
			cmbEstablecimiento.requestFocus();
			cmbEstablecimiento.showPopup();
			btnModificar.setEnabled(false);
	   }
    }; 
    
    ActionListener Op_Cambio_Anio = new ActionListener(){
	@SuppressWarnings("unchecked")
	public void actionPerformed(ActionEvent e) {
			cmbSemanas_del_anio.removeAllItems();
	  		String semana_del_aniop[] = new Obj_Programacion_De_Proveedores().Combo_Semanas_Del_Año(Integer.valueOf(cmbAnio.getSelectedItem().toString()),"Sr" ,cmbEstablecimiento.getSelectedItem().toString().trim());
	  		for(int i=0;i<semana_del_aniop.length;i++) {
	  			cmbSemanas_del_anio.addItem(semana_del_aniop[i].toString().trim());
	  		}
  	   }
      }; 
      
  	ActionListener deshacer = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			panelLimpiar();
			panel_boolean(false);
			btnNuevo.setEnabled(true);
			btnModificar.setEnabled(false);
			NuevoModifica="";
		}
	};      
	
	ActionListener opDerecha = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			int Folio=0;
			if(!txtFolio.getText().equals("")){
				Folio=Integer.valueOf(txtFolio.getText().trim());
			}
			panelLimpiar();
			panel_boolean(false);
			cargar_datos_tablas(Folio+1);
			btnModificar.setEnabled(true);
		}
	};
	
	ActionListener opIzquierda = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			int Folio=0;
			if(!txtFolio.getText().equals("")){
				Folio=Integer.valueOf(txtFolio.getText().trim());
			}
			panelLimpiar();
			panel_boolean(false);
			cargar_datos_tablas(Folio-1);
			btnModificar.setEnabled(true);
		}
	};
	
	ActionListener buscar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			new Cat_Filtro_Buscar_Cuadrantes().setVisible(true);
		}
	};
	
	class opAgregar_Proveedor implements ActionListener{   
		JTable tablaparametro;
	    public opAgregar_Proveedor(final JTable tblp){
	    	tablaparametro = tblp;
	    }
	    public void actionPerformed(ActionEvent evt){
			if(cmbEstablecimiento.getSelectedIndex()==0){
				JOptionPane.showMessageDialog(null, "Es Requerido Que Seleccione Un Establecimiento Para Buscar La Orden De Compra", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}
	         new Cat_Filtro_Ordenes_de_Compra(tablaparametro).setVisible(true);
	    }
	};
	
	class op_validacelda_tabla implements KeyListener{   
		JTable tablaparametro;
		int filak=0,columnak=0;
	    public op_validacelda_tabla (final JTable tblp){
	    	tablaparametro = tblp;
	    }
	    public void actionPerformed(ActionEvent evt){}
		@Override
		public void keyPressed(KeyEvent arg0) {}
		@Override
		public void keyReleased(KeyEvent arg0) {
			filak=tablaparametro.getSelectedRow();
			columnak=tablaparametro.getSelectedColumn();
            if(columnak>4&&columnak<7){
            	if(ObjTab.validacelda(tablaparametro,"hora", filak, columnak)){
            		ObjTab.RecorridoFocotabla(tablaparametro, filak, columnak, "seguir");
  			     }
            }
		}
		@Override
		public void keyTyped(KeyEvent arg0) {}
	};
	
	class opMoverArriba implements ActionListener{   
		JTable tablaparametro;
	    public opMoverArriba(final JTable tblp){
	    	tablaparametro = tblp;
	    }
	    public void actionPerformed(ActionEvent evt){
	    	DefaultTableModel modeloparametro= (DefaultTableModel) tablaparametro.getModel();
	    	if(!tablaparametro.isRowSelected(tablaparametro.getSelectedRow())){
				JOptionPane.showMessageDialog(null, "Es Requerido El Selecionar Una Fila Para Poder Moverla ", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;	
	    	}
	    	if(tablaparametro.getRowCount()>1){
	    		if(tablaparametro.getSelectedRow() == 0){
					JOptionPane.showMessageDialog(null, "Es La Primera Fila No Se Puede Subir Mas : )", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
					return;
	    		}else{
					Object primeraColum     = modeloparametro.getValueAt(tablaparametro.getSelectedRow()  ,1);
					Object segundaColum     = modeloparametro.getValueAt(tablaparametro.getSelectedRow()-1,1);
					Object terceraColumsube = modeloparametro.getValueAt(tablaparametro.getSelectedRow()  ,2);
					Object terceraColumbaja = modeloparametro.getValueAt(tablaparametro.getSelectedRow()-1,2);
					Object cuartaColumsube  = modeloparametro.getValueAt(tablaparametro.getSelectedRow()  ,3);
					Object cuartaColumbaja  = modeloparametro.getValueAt(tablaparametro.getSelectedRow()-1,3);
					Object quintaColumsube  = modeloparametro.getValueAt(tablaparametro.getSelectedRow()  ,4);
					Object quintaColumbaja  = modeloparametro.getValueAt(tablaparametro.getSelectedRow()-1,4);
					Object sextaColumsube   = modeloparametro.getValueAt(tablaparametro.getSelectedRow()  ,5);
					Object sextaColumbaja   = modeloparametro.getValueAt(tablaparametro.getSelectedRow()-1,5);
					Object septimaColumsube = modeloparametro.getValueAt(tablaparametro.getSelectedRow()  ,6);
					Object septimaColumbaja = modeloparametro.getValueAt(tablaparametro.getSelectedRow()-1,6);
					Object octavaColumsube  = modeloparametro.getValueAt(tablaparametro.getSelectedRow()  ,7);
					Object octavaColumbaja  = modeloparametro.getValueAt(tablaparametro.getSelectedRow()-1,7);
					Object novenaColumsube  = modeloparametro.getValueAt(tablaparametro.getSelectedRow()  ,8);
					Object novenaColumbaja  = modeloparametro.getValueAt(tablaparametro.getSelectedRow()-1,8);
					Object decimaColumsube  = modeloparametro.getValueAt(tablaparametro.getSelectedRow()  ,9);
					Object decimaColumbaja  = modeloparametro.getValueAt(tablaparametro.getSelectedRow()-1,9);
					
					
					modeloparametro.setValueAt(primeraColum    ,tablaparametro.getSelectedRow()-1 ,1);
					modeloparametro.setValueAt(segundaColum    ,tablaparametro.getSelectedRow()   ,1);	
					modeloparametro.setValueAt(terceraColumsube,tablaparametro.getSelectedRow()-1 ,2);	
					modeloparametro.setValueAt(terceraColumbaja,tablaparametro.getSelectedRow()   ,2);	
					modeloparametro.setValueAt(cuartaColumsube ,tablaparametro.getSelectedRow()-1 ,3);	
					modeloparametro.setValueAt(cuartaColumbaja ,tablaparametro.getSelectedRow()   ,3);	
					modeloparametro.setValueAt(quintaColumsube ,tablaparametro.getSelectedRow()-1 ,4);	
					modeloparametro.setValueAt(quintaColumbaja ,tablaparametro.getSelectedRow()   ,4);	
					modeloparametro.setValueAt(sextaColumsube  ,tablaparametro.getSelectedRow()-1 ,5);	
					modeloparametro.setValueAt(sextaColumbaja  ,tablaparametro.getSelectedRow()   ,5);	
					modeloparametro.setValueAt(septimaColumsube,tablaparametro.getSelectedRow()-1 ,6);	
					modeloparametro.setValueAt(septimaColumbaja,tablaparametro.getSelectedRow()   ,6);	
					modeloparametro.setValueAt(octavaColumsube ,tablaparametro.getSelectedRow()-1 ,7);	
					modeloparametro.setValueAt(octavaColumbaja ,tablaparametro.getSelectedRow()   ,7);	
					modeloparametro.setValueAt(novenaColumsube ,tablaparametro.getSelectedRow()-1 ,8);	
					modeloparametro.setValueAt(novenaColumbaja ,tablaparametro.getSelectedRow()   ,8);	
					modeloparametro.setValueAt(decimaColumsube ,tablaparametro.getSelectedRow()-1 ,9);	
					modeloparametro.setValueAt(decimaColumbaja ,tablaparametro.getSelectedRow()   ,9);	
					
					tablaparametro.setRowSelectionInterval(tablaparametro.getSelectedRow()-1,tablaparametro.getSelectedRow()-1);
	    		}
	    	}else{
				JOptionPane.showMessageDialog(null, "Es Requerido Seleccione Una Fila De La Tabla Para Desplazar", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
	    	}
	    }
	};

	class opMoverAbajo implements ActionListener{   
		JTable tablaparametro;
	    public opMoverAbajo(final JTable tblp){
	    	tablaparametro = tblp;
	    }
	    public void actionPerformed(ActionEvent evt){
	    	DefaultTableModel modeloparametro= (DefaultTableModel) tablaparametro.getModel();
	    	if(!tablaparametro.isRowSelected(tablaparametro.getSelectedRow())){
				JOptionPane.showMessageDialog(null, "Es Requerido El Selecionar Una Fila Para Poder Moverla ", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;	
	    	}
	    	
	    	if(tablaparametro.getRowCount()>1){
	    		if(tablaparametro.getSelectedRow()+1 == tablaparametro.getRowCount()){
					JOptionPane.showMessageDialog(null, "Es La Ultima Fila No Se Puede Bajar Mas : )", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
					return;
	    		}else{
					Object primeraColum     = modeloparametro.getValueAt(tablaparametro.getSelectedRow()+1,1);
					Object segundaColumBaja = modeloparametro.getValueAt(tablaparametro.getSelectedRow()  ,1);
					Object segundaColumSube = modeloparametro.getValueAt(tablaparametro.getSelectedRow()+1,1);
					Object terceraColumBaja = modeloparametro.getValueAt(tablaparametro.getSelectedRow()  ,2);
					Object terceraColumSube = modeloparametro.getValueAt(tablaparametro.getSelectedRow()+1,2);
					Object cuartaColumBaja  = modeloparametro.getValueAt(tablaparametro.getSelectedRow()  ,3);
					Object cuartaColumSube  = modeloparametro.getValueAt(tablaparametro.getSelectedRow()+1,3);
					
					Object quintaColumBaja  = modeloparametro.getValueAt(tablaparametro.getSelectedRow()  ,4);
					Object quintaColumSube  = modeloparametro.getValueAt(tablaparametro.getSelectedRow()+1,4);
					
					Object sextaColumbaja   = modeloparametro.getValueAt(tablaparametro.getSelectedRow()  ,5);					
					Object sextaColumsube   = modeloparametro.getValueAt(tablaparametro.getSelectedRow()+1,5);
					
					Object septimaColumbaja = modeloparametro.getValueAt(tablaparametro.getSelectedRow()  ,6);
					Object septimaColumsube = modeloparametro.getValueAt(tablaparametro.getSelectedRow()+1,6);
					
					Object octavaColumbaja  = modeloparametro.getValueAt(tablaparametro.getSelectedRow()  ,7);
					Object octavaColumsube  = modeloparametro.getValueAt(tablaparametro.getSelectedRow()+1,7);
					
					Object novenaColumbaja  = modeloparametro.getValueAt(tablaparametro.getSelectedRow()  ,8);
					Object novenaColumsube  = modeloparametro.getValueAt(tablaparametro.getSelectedRow()+1,8);
					
					Object decimaColumbaja  = modeloparametro.getValueAt(tablaparametro.getSelectedRow()  ,9);
					Object decimaColumsube  = modeloparametro.getValueAt(tablaparametro.getSelectedRow()+1,9);
					
					modeloparametro.setValueAt(primeraColum,tablaparametro.getSelectedRow()+1    ,1);
					modeloparametro.setValueAt(segundaColumBaja,tablaparametro.getSelectedRow()+1,1);	
					modeloparametro.setValueAt(segundaColumSube,tablaparametro.getSelectedRow()  ,1);	
					modeloparametro.setValueAt(terceraColumBaja,tablaparametro.getSelectedRow()+1,2);	
					modeloparametro.setValueAt(terceraColumSube,tablaparametro.getSelectedRow()  ,2);
					modeloparametro.setValueAt(cuartaColumBaja,tablaparametro.getSelectedRow()+1 ,3);	
					modeloparametro.setValueAt(cuartaColumSube,tablaparametro.getSelectedRow()   ,3);	
					modeloparametro.setValueAt(quintaColumBaja,tablaparametro.getSelectedRow()+1 ,4);	
					modeloparametro.setValueAt(quintaColumSube,tablaparametro.getSelectedRow()   ,4);
					
					modeloparametro.setValueAt(sextaColumbaja,tablaparametro.getSelectedRow()+1  ,5);	
					modeloparametro.setValueAt(sextaColumsube,tablaparametro.getSelectedRow()    ,5);	
					
					modeloparametro.setValueAt(septimaColumbaja,tablaparametro.getSelectedRow()+1,6);	
					modeloparametro.setValueAt(septimaColumsube,tablaparametro.getSelectedRow()  ,6);	
					
					modeloparametro.setValueAt(octavaColumbaja,tablaparametro.getSelectedRow()+1 ,7);	
					modeloparametro.setValueAt(octavaColumsube,tablaparametro.getSelectedRow()   ,7);	
					
					modeloparametro.setValueAt(novenaColumbaja,tablaparametro.getSelectedRow()+1 ,8);	
					modeloparametro.setValueAt(novenaColumsube,tablaparametro.getSelectedRow()   ,8);
					
					modeloparametro.setValueAt(decimaColumbaja,tablaparametro.getSelectedRow()+1 ,9);	
					modeloparametro.setValueAt(decimaColumsube,tablaparametro.getSelectedRow()   ,9);	
					
					tablaparametro.setRowSelectionInterval(tablaparametro.getSelectedRow()+1,tablaparametro.getSelectedRow()+1);
	    		}
	    	}else{
				JOptionPane.showMessageDialog(null, "Es Requerido Que Seleccione Una Fila De La Tabla Para Desplazar, y  Que Existan Mas De Una Fila", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
	    	}
	    }
	};
	
	class opEliminarfila implements ActionListener{   
		JTable tablaparametro;
	    public opEliminarfila(final JTable tblp){
	    	tablaparametro = tblp;
	    }
	    public void actionPerformed(ActionEvent evt){
	    	DefaultTableModel modeloparametro= (DefaultTableModel) tablaparametro.getModel();
	    	if(!tablaparametro.isRowSelected(tablaparametro.getSelectedRow())){
				JOptionPane.showMessageDialog(null, "Es Requerido El Selecionar Una Fila Para Poder Eliminarla ", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;	
	    	}
	    	if(tablaparametro.getRowCount()>0){
					Object primeraColum     = modeloparametro.getValueAt(tablaparametro.getSelectedRow(),1);
					modeloparametro.setValueAt(primeraColum,tablaparametro.getSelectedRow()    ,1);
		    		modeloparametro.removeRow(tablaparametro.getSelectedRow());
					if(tablaparametro.getRowCount()>0){
						tablaparametro.setRowSelectionInterval(tablaparametro.getSelectedRow()+1,tablaparametro.getSelectedRow()+1);
						for(int i =0; i<tablaparametro.getRowCount(); i++){
							modeloparametro.setValueAt(i+1,i,0);
				    	  };
					}
	    	}else{
				JOptionPane.showMessageDialog(null, "Es Requerido Que Seleccione Una Fila De La Tabla Para Eliminar", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
	    	}
	    }
	};
	
	ActionListener editar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			int AnioActual =Integer.valueOf(txtFecha.getText().toString().trim().substring(6,txtFecha.getText().toString().trim().length()));
			int SemanaActual =Integer.valueOf(txtSemana.getText().toString().trim());
		   if(Integer.valueOf(cmbAnio.getSelectedItem().toString())<AnioActual ) {
			    JOptionPane.showMessageDialog(null, "Solo Se Puede Editar El Año En Trascurso "+AnioActual+" o a Futuro" , "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));		
			    return;	  
		   }else {   
		   		   if(Integer.valueOf(cmbSemanas_del_anio.getSelectedItem().toString())<SemanaActual){
					 JOptionPane.showMessageDialog(null, "Solo Se Puede Editar La Semana En Transcurso "+txtSemana.getText().trim()+" o a Futuro" , "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));		
					 return;
				   }else {
						panel_boolean(true);
						cmbEstablecimiento.setEnabled(false);
			            cmbAnio.setEnabled(false);
			            cmbSemanas_del_anio.setEnabled(false);
			            btnEljDomingo.setEnabled(false);
			            btnEljLunes.setEnabled(false);
			            btnEljMartes.setEnabled(false);
			            btnEljMiercoles.setEnabled(false);
			            btnEljJueves.setEnabled(false);
			            btnEljViernes.setEnabled(false);
			            btnEljSabado.setEnabled(false);
						cmb_status.setEnabled(true);
						txtFolio.setEditable(false);
						btnModificar.setEnabled(false);
						btnGuardar.setEnabled(true);
						NuevoModifica="M";
				   }
		   }   
		}		
	};
	
    ActionListener guardar = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				    String Mensaje =Valida();
					if(!Mensaje.equals("Para Poder Guardar Es Requerido Alimente:")){
						JOptionPane.showMessageDialog(null, Mensaje, "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
					}else{
					    if(new Obj_Programacion_De_Proveedores().validacion_existe (cmbSemanas_del_anio.getSelectedItem().toString().trim(),cmbEstablecimiento.getSelectedItem().toString().trim())||NuevoModifica.equals("M")){	
									Obj_Programacion_De_Proveedores proveedores = new Obj_Programacion_De_Proveedores();
									proveedores.setFolio(Integer.valueOf(txtFolio.getText().toString().trim()));
									proveedores.setEstablecimiento(cmbEstablecimiento.getSelectedItem().toString());
									proveedores.setAnio(Integer.valueOf(cmbAnio.getSelectedItem().toString().trim()));
									proveedores.setSemana_de_anio(Integer.valueOf(cmbSemanas_del_anio.getSelectedItem().toString().trim()));
									proveedores.setNuevoModifica(NuevoModifica);  
									proveedores.setTabla_programacion(TablaGuardado());
			        			if(proveedores.Guardar()){
			    					JOptionPane.showMessageDialog(null,"El Registro Se Guardó Correctamente!","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/aplicara-el-dialogo-icono-6256-32.png"));
			    					btnDeshacer.doClick();
			    					return;
			    				}else{
			    					JOptionPane.showMessageDialog(null,"Error Al Guardar Avise al Administrador del Sistema","Aviso",JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-icono-eliminar5252-64.png"));
			    					return;
			    				}	
			        			
					     }else {
							JOptionPane.showMessageDialog(null, "La Semana Actual Ya Esta Guardada", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
						 }	
				}
		 }
	 };
	
 	 public String[][] TablaGuardado(){
			int rengloneslunes     = tablaLunes.getRowCount()    ;
			int renglonesMartes    = tablaMartes.getRowCount()   ;
			int renglonesMiercoles = tablaMiercoles.getRowCount();
			int renglonesJueves    = tablaJueves.getRowCount()   ;
			int renglonesViernes   = tablaViernes.getRowCount()  ;
			int renglonesSabado    = tablaSabado.getRowCount()   ;
			int renglonesdomingo   = tablaDomingo.getRowCount()  ;
			int filas = rengloneslunes+renglonesMartes+renglonesMiercoles+renglonesJueves+renglonesViernes+renglonesSabado+renglonesdomingo;
			int fila  = 0;
			int i     = 0;
			String[][] tablas = new String[filas][11];	
			while(rengloneslunes > 0){
					tablas[i][ 0] = modelLunes.getValueAt(fila, 0)+"";
					tablas[i][ 1] = modelLunes.getValueAt(fila, 1)+"";
					tablas[i][ 2] = modelLunes.getValueAt(fila, 2)+"";
					tablas[i][ 3] = modelLunes.getValueAt(fila, 3)+"";
					tablas[i][ 4] = modelLunes.getValueAt(fila, 4)+"";
					tablas[i][ 5] = modelLunes.getValueAt(fila, 5)+"";
					tablas[i][ 6] = modelLunes.getValueAt(fila, 6)+"";
					tablas[i][ 7] = modelLunes.getValueAt(fila, 7)+"";
					tablas[i][ 8] = modelLunes.getValueAt(fila, 8)+"";
					tablas[i][ 9] = modelLunes.getValueAt(fila, 9)+"";
					tablas[i][10] = "1";
					i+=1;
					fila+=1;
					rengloneslunes--;
			}
			
			fila=0;
			while(renglonesMartes > 0){
					tablas[i][ 0] = modelMartes.getValueAt(fila, 0)+"";
					tablas[i][ 1] = modelMartes.getValueAt(fila, 1)+"";
					tablas[i][ 2] = modelMartes.getValueAt(fila, 2)+"";
					tablas[i][ 3] = modelMartes.getValueAt(fila, 3)+"";
					tablas[i][ 4] = modelMartes.getValueAt(fila, 4)+"";
					tablas[i][ 5] = modelMartes.getValueAt(fila, 5)+"";
					tablas[i][ 6] = modelMartes.getValueAt(fila, 6)+"";
					tablas[i][ 7] = modelMartes.getValueAt(fila, 7)+"";
					tablas[i][ 8] = modelMartes.getValueAt(fila, 8)+"";
					tablas[i][ 9] = modelMartes.getValueAt(fila, 9)+"";
					tablas[i][10] = "2";
					i+=1;
					fila+=1;
					renglonesMartes--;
			}
			
			fila=0;
			while(renglonesMiercoles > 0){
					tablas[i][ 0] = modelMiercoles.getValueAt(fila, 0)+"";
					tablas[i][ 1] = modelMiercoles.getValueAt(fila, 1)+"";
					tablas[i][ 2] = modelMiercoles.getValueAt(fila, 2)+"";
					tablas[i][ 3] = modelMiercoles.getValueAt(fila, 3)+"";
					tablas[i][ 4] = modelMiercoles.getValueAt(fila, 4)+"";
					tablas[i][ 5] = modelMiercoles.getValueAt(fila, 5)+"";
					tablas[i][ 6] = modelMiercoles.getValueAt(fila, 6)+"";
					tablas[i][ 7] = modelMiercoles.getValueAt(fila, 7)+"";
					tablas[i][ 8] = modelMiercoles.getValueAt(fila, 8)+"";
					tablas[i][ 9] = modelMiercoles.getValueAt(fila, 9)+"";
					tablas[i][10] = "3";
					i+=1;
					fila+=1;
					renglonesMiercoles--;
			}
			
			fila=0;
			while(renglonesJueves > 0){
					tablas[i][ 0] = modelJueves.getValueAt(fila, 0)+"";
					tablas[i][ 1] = modelJueves.getValueAt(fila, 1)+"";
					tablas[i][ 2] = modelJueves.getValueAt(fila, 2)+"";
					tablas[i][ 3] = modelJueves.getValueAt(fila, 3)+"";
					tablas[i][ 4] = modelJueves.getValueAt(fila, 4)+"";
					tablas[i][ 5] = modelJueves.getValueAt(fila, 5)+"";
					tablas[i][ 6] = modelJueves.getValueAt(fila, 6)+"";
					tablas[i][ 7] = modelJueves.getValueAt(fila, 7)+"";
					tablas[i][ 8] = modelJueves.getValueAt(fila, 8)+"";
					tablas[i][ 9] = modelJueves.getValueAt(fila, 9)+"";
					tablas[i][10] = "4";
					i+=1;
					fila+=1;
					renglonesJueves--;
			}
			
			fila=0;
			while(renglonesViernes > 0){
					tablas[i][ 0] = modelViernes.getValueAt(fila, 0)+"";
					tablas[i][ 1] = modelViernes.getValueAt(fila, 1)+"";
					tablas[i][ 2] = modelViernes.getValueAt(fila, 2)+"";
					tablas[i][ 3] = modelViernes.getValueAt(fila, 3)+"";
					tablas[i][ 4] = modelViernes.getValueAt(fila, 4)+"";
					tablas[i][ 5] = modelViernes.getValueAt(fila, 5)+"";
					tablas[i][ 6] = modelViernes.getValueAt(fila, 6)+"";
					tablas[i][ 7] = modelViernes.getValueAt(fila, 7)+"";
					tablas[i][ 8] = modelViernes.getValueAt(fila, 8)+"";
					tablas[i][ 9] = modelViernes.getValueAt(fila, 9)+"";
					tablas[i][10] = "5";
					i+=1;
					fila+=1;
					renglonesViernes--;
			}
			
			fila=0;
			while(renglonesSabado > 0){
					tablas[i][ 0] = modelSabado.getValueAt(fila, 0)+"";
					tablas[i][ 1] = modelSabado.getValueAt(fila, 1)+"";
					tablas[i][ 2] = modelSabado.getValueAt(fila, 2)+"";
					tablas[i][ 3] = modelSabado.getValueAt(fila, 3)+"";
					tablas[i][ 4] = modelSabado.getValueAt(fila, 4)+"";
					tablas[i][ 5] = modelSabado.getValueAt(fila, 5)+"";
					tablas[i][ 6] = modelSabado.getValueAt(fila, 6)+"";
					tablas[i][ 7] = modelSabado.getValueAt(fila, 7)+"";
					tablas[i][ 8] = modelSabado.getValueAt(fila, 8)+"";
					tablas[i][ 9] = modelSabado.getValueAt(fila, 9)+"";
					tablas[i][10] = "6";
					i+=1;
					fila+=1;
					renglonesSabado--;
			}
			
			fila=0;			
			while(renglonesdomingo > 0){
					tablas[i][ 0] = modelDomingo.getValueAt(fila, 0)+"";
					tablas[i][ 1] = modelDomingo.getValueAt(fila, 1)+"";
					tablas[i][ 2] = modelDomingo.getValueAt(fila, 2)+"";
					tablas[i][ 3] = modelDomingo.getValueAt(fila, 3)+"";
					tablas[i][ 4] = modelDomingo.getValueAt(fila, 4)+"";
					tablas[i][ 5] = modelDomingo.getValueAt(fila, 5)+"";
					tablas[i][ 6] = modelDomingo.getValueAt(fila, 6)+"";
					tablas[i][ 7] = modelDomingo.getValueAt(fila, 7)+"";
					tablas[i][ 8] = modelDomingo.getValueAt(fila, 8)+"";
					tablas[i][ 9] = modelDomingo.getValueAt(fila, 9)+"";
					tablas[i][10] = "7";
				i+=1;
				fila+=1;
			renglonesdomingo--;
		   }
			return tablas;
		}
		
	public String Valida(){
	    String Mensaje ="Para Poder Guardar Es Requerido Alimente:";
	    if(cmbEstablecimiento.getSelectedIndex()==0){Mensaje+="\nEl Establecimiento"; }
	    int rengloneslunes     = tablaLunes.getRowCount()    ;
		int renglonesMartes    = tablaMartes.getRowCount()   ;
		int renglonesMiercoles = tablaMiercoles.getRowCount();
		int renglonesJueves    = tablaJueves.getRowCount()   ;
		int renglonesViernes   = tablaViernes.getRowCount()  ;
		int renglonesSabado    = tablaSabado.getRowCount()   ;
		int renglonesdomingo   = tablaDomingo.getRowCount()  ;
		int filas = rengloneslunes+renglonesMartes+renglonesMiercoles+renglonesJueves+renglonesViernes+renglonesSabado+renglonesdomingo;
		if (filas==0){
			Mensaje+="\nAlguna Compra/ Gastos o Visita DE Proveedor En Por Lo Menos 1 Dia De La Semana"; 
		}
		return Mensaje;
	}	
	
	public void panelLimpiar(){	
		modelLunes.setRowCount(0);
		modelMartes.setRowCount(0);
		modelMiercoles.setRowCount(0);
		modelJueves.setRowCount(0);
		modelViernes.setRowCount(0);
		modelSabado.setRowCount(0);
		modelDomingo.setRowCount(0);
		
		txtFolio.setText("");
		txtBuscarLunes.setText("");
		txtBuscarMartes.setText("");
		txtBuscarMiercoles.setText("");
		txtBuscarJueves.setText("");
		txtBuscarViernes.setText("");
		txtBuscarSabado.setText("");
		txtBuscarDomingo.setText("");
		
		cmb_status.setSelectedIndex(0);
		cmbEstablecimiento.setSelectedIndex(0);
		
		ObjTab.Obj_Filtro(tablaLunes    , "", columnas);
		ObjTab.Obj_Filtro(tablaMartes   , "", columnas);
		ObjTab.Obj_Filtro(tablaMiercoles, "", columnas);
		ObjTab.Obj_Filtro(tablaJueves   , "", columnas);
		ObjTab.Obj_Filtro(tablaViernes  , "", columnas);
		ObjTab.Obj_Filtro(tablaSabado   , "", columnas);
		ObjTab.Obj_Filtro(tablaDomingo  , "", columnas);
	}	
	
	public void panel_boolean(boolean boleano){
		txtFolio.setEditable  (boleano);
		txtBuscarLunes.setEditable(boleano);
		txtBuscarMartes.setEditable(boleano);
		txtBuscarMiercoles.setEditable(boleano);
		txtBuscarJueves.setEditable(boleano);
		txtBuscarViernes.setEditable(boleano);
		txtBuscarSabado.setEditable(boleano);
		txtBuscarDomingo.setEditable(boleano);
		
		btnModificar.setEnabled  (boleano);
        btnGuardar.setEnabled (boleano);
        btnAgregLunes.setEnabled(boleano);
        btnAgregMartes.setEnabled(boleano);
        btnAgregMiercoles.setEnabled(boleano);
        btnAgregJueves.setEnabled(boleano);
        btnAgregViernes.setEnabled(boleano);
        btnAgregSabado.setEnabled(boleano);
        btnAgregDomingo.setEnabled(boleano);
        btnBajLunes.setEnabled(boleano);
        btnBajMartes.setEnabled(boleano);
        btnBajMiercoles.setEnabled(boleano);
        btnBajJueves.setEnabled(boleano);
        btnBajViernes.setEnabled(boleano);
        btnBajSabado.setEnabled(boleano);
        btnBajDomingo.setEnabled(boleano);
        btnEljLunes.setEnabled(boleano);
        btnEljMartes.setEnabled(boleano);
        btnEljMiercoles.setEnabled(boleano);
        btnEljJueves.setEnabled(boleano);
        btnEljViernes.setEnabled(boleano);
        btnEljSabado.setEnabled(boleano);
        btnEljDomingo.setEnabled(boleano);
        btnSubLunes.setEnabled(boleano);
        btnSubMartes.setEnabled(boleano);
        btnSubMiercoles.setEnabled(boleano);
        btnSubJueves.setEnabled(boleano);
        btnSubViernes.setEnabled(boleano);
        btnSubSabado.setEnabled(boleano);
        btnSubDomingo.setEnabled(boleano);
		cmb_status.setEnabled (boleano);
		cmbEstablecimiento.setEnabled(boleano);
		cmbSemanas_del_anio.setEnabled(boleano);
		cmbAnio.setEnabled(boleano);
		bloqueocolumna=boleano;
	}		

//TODO inicia filtro agregar proveedor
	public class Cat_Filtro_Ordenes_de_Compra extends JDialog{
		Container contf = getContentPane();
		JLayeredPane panelf = new JLayeredPane();
		Connexion con = new Connexion();
		int columnaspo = 13,checkbox=1;
		public void init_tablaordenes(String parametrop, String cadena){
		 	this.tablafilordenes.getColumnModel().getColumn(0).setMinWidth (20 );	
		 	this.tablafilordenes.getColumnModel().getColumn(0).setMaxWidth (20 );	
	    	this.tablafilordenes.getColumnModel().getColumn(1).setMinWidth (40 );		
	    	this.tablafilordenes.getColumnModel().getColumn(2).setMinWidth (100);
	    	this.tablafilordenes.getColumnModel().getColumn(3).setMinWidth (350);
	    	this.tablafilordenes.getColumnModel().getColumn(4).setMinWidth (40 );
	    	this.tablafilordenes.getColumnModel().getColumn(5).setMinWidth (40 );
	    	this.tablafilordenes.getColumnModel().getColumn(6).setMinWidth (120);
	    	this.tablafilordenes.getColumnModel().getColumn(7).setMinWidth (120);
	    	this.tablafilordenes.getColumnModel().getColumn(8).setMinWidth (80 );
	    	this.tablafilordenes.getColumnModel().getColumn(10).setMinWidth(130);
	    	this.tablafilordenes.getColumnModel().getColumn(11).setMinWidth(300);

			String comandof="exec proveedores_programacion_filtro_ordenes_de_compra '"+cmbEstablecimiento.getSelectedItem().toString().trim()+"','"+parametrop+"','"+cadena+"'";
	    	
			String basedatos="26",pintar="si";
			ObjTab.Obj_Refrescar(tablafilordenes,modeloor_filtro, columnaspo, comandof, basedatos,pintar,checkbox2);
	    }
		
		@SuppressWarnings("rawtypes")
		public Class[] baseOr (){
			Class[] types = new Class[columnaspo];
			for(int i = 0; i<columnaspo; i++){  
				if(i==0){
					types[i]=java.lang.Boolean.class;
				}else{
					types[i]=java.lang.Object.class;
				}
			}
			 return types;
		}
		
		public DefaultTableModel modeloor_filtro = new DefaultTableModel(null, new String[]{"","Folio","Establecimiento","Proveedor","Productos","Total","Fecha Elaboracion","Fecha Expiracion","Condicion Pago","Plazo","Fecha Autorizacion","Notas","Cod Proveedor"}){
			 @SuppressWarnings("rawtypes")
				Class[] types = baseOr();
				@SuppressWarnings({ "rawtypes", "unchecked" })
				public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
				public boolean isCellEditable(int fila, int columnaspo){	if(columnaspo==0)return true; return false;}
		};
		
		JTable tablafilordenes = new JTable(modeloor_filtro);
		public JScrollPane scroll_tablafp = new JScrollPane(tablafilordenes);
	     @SuppressWarnings({ "rawtypes" })
	    private TableRowSorter trsfiltro;
	     
	    JCButton   btnAceptar      = new JCButton("Aceptar"    ,"double-arrow-icone-3883-16.png"  ,"Azul");  
		JTextField txtBuscarfp     = new Componentes().text(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<", 300, "String");
		JLabel     JlProveedor     = new JLabel();    
		
		String TipoPoveedor[] = {"Compra","Gasto","Visita"};
		@SuppressWarnings({ "unchecked", "rawtypes" })
		JComboBox cmb_tipo_Proveedor = new JComboBox(TipoPoveedor);
		
		JTable tablaparametro_ordenes;
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Cat_Filtro_Ordenes_de_Compra(final JTable tblp){
			tablaparametro_ordenes = tblp;
			this.setSize(980,400);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			this.setModal(true);
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Filter-List-icon32.png"));
			this.setTitle("Filtro De Ordenes De Compra");
			this.panelf.setBorder(BorderFactory.createTitledBorder("Teclee El Folio De La Factura, Importes y Selecione Una Orden De Compra"));
			trsfiltro = new TableRowSorter(modeloor_filtro); 
			tablafilordenes.setRowSorter(trsfiltro);
			
            int x=10,y=20,width=953, height=20;
            
            this.panelf.add(new JLabel("Tipo De Visita:")).setBounds   (x+400 ,y     ,width ,height );
			this.panelf.add(cmb_tipo_Proveedor).setBounds              (x+480 ,y     ,150   ,height );
			this.panelf.add(btnAceptar).setBounds                      (x+630 ,y  ,150   ,height );
			this.panelf.add(txtBuscarfp).setBounds                     (x     ,y+=18 ,width ,height );
			this.panelf.add(scroll_tablafp).setBounds                  (x     ,y+=20 ,width ,305    );
	        txtBuscarfp.setText(txtProveedor.getText().toString().trim());
	        
			ObjTab.Obj_Filtro(tablafilordenes, txtProveedor.getText().toString().trim().toUpperCase(), columnaspo);
			init_tablaordenes(cmb_tipo_Proveedor.getSelectedItem().toString().trim(),parametro_busqueda());
			cmb_tipo_Proveedor.addActionListener(proveedor);
			this.agregar(tablafilordenes);
			this.txtBuscarfp.addKeyListener  (opFiltropuestos );
			this.btnAceptar.addActionListener(aceptar);
			this.contf.add(panelf);
			this.addWindowListener(new WindowAdapter() {public void windowOpened( WindowEvent e ){txtProveedor.requestFocus();}});
		}
		
		 public String parametro_busqueda(){
				int rengloneslunes     = tablaLunes.getRowCount()    ;
				int renglonesMartes    = tablaMartes.getRowCount()   ;
				int renglonesMiercoles = tablaMiercoles.getRowCount();
				int renglonesJueves    = tablaJueves.getRowCount()   ;
				int renglonesViernes   = tablaViernes.getRowCount()  ;
				int renglonesSabado    = tablaSabado.getRowCount()   ;
				int renglonesdomingo   = tablaDomingo.getRowCount()  ;
				int fila  = 0;
				String lista = "(";	
				while(rengloneslunes > 0){
					 lista =lista+"''"+modelLunes.getValueAt(fila, 4).toString().trim()+"'',";
						fila+=1;
						rengloneslunes--;
				}
				
				fila=0;
				while(renglonesMartes > 0){					
					 lista =lista+"''"+modelMartes.getValueAt(fila, 4).toString().trim()+"'',";
						fila+=1;
						renglonesMartes--;
				}
				
				fila=0;
				while(renglonesMiercoles > 0){
					  lista =lista+"''"+modelMiercoles.getValueAt(fila, 4).toString().trim()+"'',";
						fila+=1;
						renglonesMiercoles--;
				}
				
				fila=0;
				while(renglonesJueves > 0){
					   lista =lista+"''"+modelJueves.getValueAt(fila, 4).toString().trim()+"'',";
						fila+=1;
						renglonesJueves--;
				}
				
				fila=0;
				while(renglonesViernes > 0){
					   lista =lista+"''"+modelViernes.getValueAt(fila, 4).toString().trim()+"'',";
						fila+=1;
						renglonesViernes--;
				}
				
				fila=0;
				while(renglonesSabado > 0){
					   lista =lista+"''"+modelSabado.getValueAt(fila, 4).toString().trim()+"'',";
						fila+=1;
						renglonesSabado--;
				}
				
				fila=0;			
				while(renglonesdomingo > 0){
					   lista =lista+"''"+modelDomingo.getValueAt(fila, 4).toString().trim()+"'',";
					fila+=1;
				renglonesdomingo--;
			   }
				lista=lista+"&";
				String[] parts=lista.split(",&");
				lista=parts[0]+")";
				
				if(lista.equals("(&)")){lista="('''')";};
				
				return lista;
			}
		 
		ActionListener proveedor = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				init_tablaordenes(cmb_tipo_Proveedor.getSelectedItem().toString().trim(),parametro_busqueda());
			}	
		};
		
		ActionListener aceptar = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Object[]   vectorAgregarOrden = new Object[10];
				 String Mensaje =Validaprov();
					if(!Mensaje.equals("Para Poder Aceptar Es Requerido:")){
						   JOptionPane.showMessageDialog(null,Mensaje, "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));		    	
					}else{
						DefaultTableModel modeloparametro= (DefaultTableModel) tablaparametro_ordenes.getModel();
						int orden= modeloparametro.getRowCount();
						
					    for(int i=0;i<modeloor_filtro.getRowCount();i++) {
					    	 ObjTab.Obj_Filtro(tablafilordenes, "", columnaspo);
					    	if(tablafilordenes.getValueAt(i, 0).toString().equals("true")) {
					    		orden=orden+1;
					    		vectorAgregarOrden[0]=orden+"";
					    		vectorAgregarOrden[1]=cmb_tipo_Proveedor.getSelectedItem().toString().trim();
					    		vectorAgregarOrden[2]=tablafilordenes.getValueAt(i, 12).toString();
					    		vectorAgregarOrden[3]=tablafilordenes.getValueAt(i, 3).toString();
					    		vectorAgregarOrden[4]=tablafilordenes.getValueAt(i, 1).toString();
					    		vectorAgregarOrden[5]="00:00";
					    		vectorAgregarOrden[6]="00:00";
					    		vectorAgregarOrden[7]="";
					    		vectorAgregarOrden[8]=usuario.getNombre_completo();
					    		vectorAgregarOrden[9]=txtFecha.getText().toString().trim();
					    		modeloparametro.addRow(vectorAgregarOrden);
					    	}
					    	dispose();
					    }
				}
			}	
		};
		
		public String Validaprov(){
		    String Mensaje ="Para Poder Aceptar Es Requerido:";
		    int valor=0;
		    
		    if(cmb_tipo_Proveedor.getSelectedItem().toString().equals("Compra")) {
			    for(int i=0;i<modeloor_filtro.getRowCount();i++) {
			    	if(tablafilordenes.getValueAt(i, 0).toString().equals("true")) {
			    		valor=valor+1;
			    	}
			    }
		    }else {
		    	valor=1;
		    }
		    
		    if(valor==0){
		    	Mensaje+="\nSeleccionar Alguna Orden De Compra del Proveedor"; 
		    }
			return Mensaje;
		}	
		
		private void agregar(final JTable tbl) {
	        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
		        	if(e.getClickCount()==1){
		        		int fila = tablafilordenes.getSelectedRow();
		        			JlProveedor.setText(tablafilordenes.getValueAt(fila,2)+"");
		        	}
		        }
	        });
	    }
		
        private KeyListener opFiltropuestos = new KeyListener(){
			public void keyReleased(KeyEvent arg0) {
				ObjTab.Obj_Filtro(tablafilordenes, txtBuscarfp.getText().toUpperCase(), columnaspo);
			}
			public void keyTyped(KeyEvent arg0) {}
			public void keyPressed(KeyEvent arg0) {}		
		};
	}
///////////////termina filtro proveedor	
	
	//TODO inicia filtro_Buscar	
		public class Cat_Filtro_Buscar_Cuadrantes extends JDialog{
			Container contfb = getContentPane();
			JLayeredPane panelfb = new JLayeredPane();
			Connexion con = new Connexion();
			Obj_tabla ObjTab =new Obj_tabla();
			int columnasb = 7,checkbox=-1;
			public void init_tablafp(){
		    	this.tablab.getColumnModel().getColumn( 0).setMinWidth(55);
		    	this.tablab.getColumnModel().getColumn( 1).setMinWidth(100);
		    	this.tablab.getColumnModel().getColumn( 2).setMinWidth(150);
		    	this.tablab.getColumnModel().getColumn( 3).setMinWidth(70);
		    	this.tablab.getColumnModel().getColumn( 4).setMinWidth(70);
		    	this.tablab.getColumnModel().getColumn( 5).setMinWidth(150);
		    	this.tablab.getColumnModel().getColumn( 6).setMinWidth(70);
		    	
		    	String comandof=" exec proveedores_programacion_visita_filtro";
				String basedatos="26",pintar="si";
				ObjTab.Obj_Refrescar(tablab,modelob, columnasb, comandof, basedatos,pintar,checkbox);
		    }
			
			@SuppressWarnings("rawtypes")
			public Class[] base (){
				Class[] types = new Class[columnasb];
				for(int i = 0; i<columnasb; i++){types[i]= java.lang.Object.class;}
				 return types;
			}

			  
			public DefaultTableModel modelob = new DefaultTableModel(null, new String[]{"Folio","Codigo Estab","Establecimiento","Semana","Año","Fecha","Estatus"}){
				 @SuppressWarnings("rawtypes")
					Class[] types = base();
					@SuppressWarnings({ "rawtypes", "unchecked" })
					public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
					public boolean isCellEditable(int fila, int columna){return false;}
			};
			
			JTable tablab = new JTable(modelob);
			public JScrollPane scroll_tablab = new JScrollPane(tablab);
		     @SuppressWarnings({ "rawtypes" })
		    private TableRowSorter trsfiltro;
			     
			JTextField txtBuscarb  = new Componentes().text(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<", 500, "String");
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Cat_Filtro_Buscar_Cuadrantes(){
				this.setSize(724,500);
				this.setResizable(false);
				this.setLocationRelativeTo(null);
				this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				this.setModal(true);
				this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Filter-List-icon32.png"));
				this.panelfb.setBorder(BorderFactory.createTitledBorder("Selecione Un Registro Con Doble Click"));
				this.setTitle("Filtro De Programacion De Visita De Programadores");
				trsfiltro = new TableRowSorter(modelob); 
				tablab.setRowSorter(trsfiltro);
				this.panelfb.add(txtBuscarb).setBounds      (10 ,20 ,700 , 20 );
				this.panelfb.add(scroll_tablab).setBounds   (10 ,40 ,700 ,415 );
				this.init_tablafp();
				this.agregar(tablab);
				this.txtBuscarb.addKeyListener  (opFiltropuestos );
				contfb.add(panelfb);

			}
			 
			private void agregar(final JTable tbl) {
		        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
			        	if(e.getClickCount()==1){
			        		int fila = tablab.getSelectedRow();
			        		    panelLimpiar();
			        		    panel_boolean(false);
			        		    btnModificar.setEnabled(true);
							    txtFolio.setText   (tablab.getValueAt(fila,1)+"");
			        		   cargar_datos_tablas(Integer.valueOf(tablab.getValueAt(fila,0).toString()));
							dispose();
			        	}
			        }
		        });
		    }
			
	        private KeyListener opFiltropuestos = new KeyListener(){
				public void keyReleased(KeyEvent arg0) {
					ObjTab.Obj_Filtro(tablab, txtBuscarb.getText(), columnasb);
				}
				public void keyTyped(KeyEvent arg0) {}
				public void keyPressed(KeyEvent arg0) {}		
			};
		}

	public static void main(String args[]){
		try{UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Programacion_De_Proveedores().setVisible(true);
		}catch(Exception e){System.err.println("Error en Main: "+e.getMessage());
		}
	}
}