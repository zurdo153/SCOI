package Cat_Cuadrantes;

import java.awt.Color;
import java.awt.Container;
import java.awt.Event;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import Conexiones_SQL.Connexion;
import Conexiones_SQL.Generacion_Reportes;
import Obj_Cuadrantes.Obj_Cuadrantes;
import Obj_Lista_de_Raya.Obj_Departamento;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_tabla;

@SuppressWarnings("serial")
public class Cat_Cuadrantes extends JFrame{
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	Connexion con = new Connexion();
	boolean bloqueocolumna=false;
	
	Obj_tabla ObjTab =new Obj_tabla();
	int columnas = 5,checkbox=-1;
	
	@SuppressWarnings("rawtypes")
	public Class[] baselunes (){
		Class[] types = new Class[columnas];
		for(int i = 0; i<columnas; i++){types[i]= java.lang.Object.class;}
		 return types;
	}
	public DefaultTableModel modelLunes = new DefaultTableModel(null, new String[]{"Orden","Folio","Actividad","Inicia","Termina"}){
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
 	public DefaultTableModel modelMartes = new DefaultTableModel(null, new String[]{"Orden","Folio","Actividad","Inicia","Termina"}){
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
   	
   	public DefaultTableModel modelMiercoles = new DefaultTableModel(null, new String[]{"Orden","Folio","Actividad","Inicia","Termina"}){
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
    
    public DefaultTableModel modelJueves = new DefaultTableModel(null, new String[]{"Orden","Folio","Actividad","Inicia","Termina"}){
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
    public DefaultTableModel modelViernes = new DefaultTableModel(null, new String[]{"Orden","Folio","Actividad","Inicia","Termina"}){
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
    public DefaultTableModel modelSabado = new DefaultTableModel(null, new String[]{"Orden","Folio","Actividad","Inicia","Termina"}){
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
    public DefaultTableModel modelDomingo = new DefaultTableModel(null, new String[]{"Orden","Folio","Actividad","Inicia","Termina"}){
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
		String comandof="exec cuadrantes_actividades_filtro_cuadrantes ";
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
				if(columnas2==0)return true; return false;}
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
	
	JTextField txtFolio           = new Componentes().text(new JCTextField(), "Folio", 10                                                   , "Int"   );
	JTextField txtCuadrante       = new Componentes().text(new JCTextField(), "Cuadrante", 200                                              , "String");
	JTextField txtPuesto          = new Componentes().text(new JCTextField(), "Puesto", 200                                                 , "String");
	JTextField txtReporta         = new Componentes().text(new JCTextField(), "Puesto Al Que Reporta", 200                                  , "String");
	
	JTextArea txaObjetivo         = new Componentes().textArea(new JTextArea(), "Objetivo"       ,500);
	JScrollPane scrollobjet       = new JScrollPane(txaObjetivo);
	
    JTextArea txaResponsabili= new Componentes().textArea(new JTextArea(), "Responsabilidad",1000);
	JScrollPane scrollrespons= new JScrollPane(txaResponsabili);
	
	String status[] = {"Vigente","Cancelado"};
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmb_status = new JComboBox(status);
	
	String establecimientoScoi[] = new Obj_Establecimiento().Combo_Establecimiento();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbEstablecimiento = new JComboBox(establecimientoScoi);
	
	String Departamentos[] = new Obj_Departamento().Combo_Departamento();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbDepartamento = new JComboBox(Departamentos);  
	
	String Turnos[] = new Obj_Cuadrantes().Combo_Turnos();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbTurnos = new JComboBox(Turnos);  
	
	JToolBar menu_toolbar = new JToolBar();
	JCButton btnBuscar    = new JCButton("Buscar"    ,"Filter-List-icon16.png"     ,"Azul"); 
	JCButton btnNuevo     = new JCButton("Nuevo"     ,"Nuevo.png"                  ,"Azul");
	JCButton btnModificar = new JCButton("Modificar" ,"Modify.png"                 ,"Azul");
	JCButton btnGuardar   = new JCButton("Guardar"   ,"Guardar.png"                ,"Azul");
	JCButton btnDeshacer  = new JCButton("Deshacer"  ,"deshacer16.png"             ,"Azul");
	JCButton btnSimilar   = new JCButton("Similar"   ,"similar9036-16mas.png"      ,"Azul");
	JCButton btnderecha   = new JCButton(""          ,"adelante.png"               ,"Azul");
	JCButton btnizquierda = new JCButton(""          ,"atras.png"                  ,"Azul");
	JCButton btnfilpuestos= new JCButton(""          ,"Filter-List-icon16.png"     ,"Azul");
	JCButton btnfilrepora = new JCButton(""          ,"Filter-List-icon16.png"     ,"Azul");
	
	JCButton btnReporte   = new JCButton("Reporte"   ,"Lista.png"                ,"Azul");
	JCButton btnReporteL   = new JCButton("Reporte Limpio"   ,"Lista.png"                ,"Azul");

	JToolBar toolbarLunes         = new JToolBar();
	JCButton btnAgregLunes        = new JCButton("Agregar Lunes","double-arrow-icone-3883-16.png"  ,"Azul" );
	JCButton btnSubLunes          = new JCButton("Subir"  ,"Up.png"                          ,"Azul" );
	JCButton btnBajLunes          = new JCButton("Bajar"  ,"Down.png"                        ,"Azul" );
	JCButton btnEljLunes          = new JCButton("Eliminar","eliminar-bala-icono-7773-32.png","Azul" );
	JCButton btnCopLunes          = new JCButton("Copiar","igual.png               ","Azul" );
	
	JToolBar toolbarMartes        = new JToolBar();
	JCButton btnAgregMartes       = new JCButton("Agregar Martes","double-arrow-icone-3883-16.png"  ,"Azul" );
	JCButton btnSubMartes         = new JCButton("Subir"  ,"Up.png"                          ,"Azul" );
	JCButton btnBajMartes         = new JCButton("Bajar"  ,"Down.png"                        ,"Azul" );
	JCButton btnEljMartes         = new JCButton("Eliminar","eliminar-bala-icono-7773-32.png","Azul" );
	JCButton btnCopMartes         = new JCButton("Copiar","igual.png               ","Azul" );
	
	JToolBar toolbarMiercoles     = new JToolBar();
	JCButton btnAgregMiercoles    = new JCButton("Agregar Miercoles","double-arrow-icone-3883-16.png"  ,"Azul" );
	JCButton btnSubMiercoles      = new JCButton("Subir"  ,"Up.png"                          ,"Azul" );
	JCButton btnBajMiercoles      = new JCButton("Bajar"  ,"Down.png"                        ,"Azul" );
	JCButton btnEljMiercoles      = new JCButton("Eliminar","eliminar-bala-icono-7773-32.png","Azul" );
	JCButton btnCopMiercoles      = new JCButton("Copiar","igual.png               ","Azul" );
	
	JToolBar toolbarJueves        = new JToolBar();
	JCButton btnAgregJueves       = new JCButton("Agregar Jueves","double-arrow-icone-3883-16.png"  ,"Azul" );
	JCButton btnSubJueves         = new JCButton("Subir"  ,"Up.png"                          ,"Azul" );
	JCButton btnBajJueves         = new JCButton("Bajar"  ,"Down.png"                        ,"Azul" );
	JCButton btnEljJueves         = new JCButton("Eliminar","eliminar-bala-icono-7773-32.png","Azul" );
	JCButton btnCopJueves         = new JCButton("Copiar","igual.png               ","Azul" );
	
	JToolBar toolbarViernes       = new JToolBar();
	JCButton btnAgregViernes      = new JCButton("Agregar Viernes","double-arrow-icone-3883-16.png"  ,"Azul" );
	JCButton btnSubViernes        = new JCButton("Subir"  ,"Up.png"                          ,"Azul" );
	JCButton btnBajViernes        = new JCButton("Bajar"  ,"Down.png"                        ,"Azul" );
	JCButton btnEljViernes        = new JCButton("Eliminar","eliminar-bala-icono-7773-32.png","Azul" );
	JCButton btnCopViernes        = new JCButton("Copiar","igual.png               ","Azul" );
	
	JToolBar toolbarSabado        = new JToolBar();
	JCButton btnAgregSabado       = new JCButton("Agregar Sabado","double-arrow-icone-3883-16.png"  ,"Azul" );
	JCButton btnSubSabado         = new JCButton("Subir"  ,"Up.png"                          ,"Azul" );
	JCButton btnBajSabado         = new JCButton("Bajar"  ,"Down.png"                        ,"Azul" );
	JCButton btnEljSabado         = new JCButton("Eliminar","eliminar-bala-icono-7773-32.png","Azul" );
	JCButton btnCopSabado         = new JCButton("Copiar","igual.png               ","Azul" );
	
	JToolBar toolbarDomingo       = new JToolBar();
	JCButton btnAgregDomingo      = new JCButton("Agregar Domingo","double-arrow-icone-3883-16.png"  ,"Azul" );
	JCButton btnSubDomingo        = new JCButton("Subir"  ,"Up.png"                          ,"Azul" );
	JCButton btnBajDomingo        = new JCButton("Bajar"  ,"Down.png"                        ,"Azul" );
	JCButton btnEljDomingo        = new JCButton("Eliminar","eliminar-bala-icono-7773-32.png","Azul" );
	JCButton btnCopDomingo        = new JCButton("Copiar","igual.png               ","Azul" );
	
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
	public Cat_Cuadrantes(){
		this.setSize(1024,685);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/favoritos-ver-boton-icono-8318-32.png"));
		this.setTitle("Cuadrantes");
		this.panel.setBorder(BorderFactory.createTitledBorder("Cuadrantes"));
		
		this.menu_toolbar.add(btnBuscar  );
	    this.menu_toolbar.addSeparator(  );
	    this.menu_toolbar.addSeparator(  );
		this.menu_toolbar.add(btnModificar);
	    this.menu_toolbar.addSeparator(  );
	    this.menu_toolbar.addSeparator(  );
	    this.menu_toolbar.add(btnSimilar );
	    this.menu_toolbar.addSeparator(  );
	    this.menu_toolbar.addSeparator(  );
	    this.menu_toolbar.add(btnNuevo   );
	    this.menu_toolbar.addSeparator(  );
	    this.menu_toolbar.addSeparator(  );
		this.menu_toolbar.add(btnDeshacer);
		this.menu_toolbar.addSeparator(  );
		this.menu_toolbar.addSeparator(  );
		this.menu_toolbar.add(btnGuardar );
		this.menu_toolbar.addSeparator(  );
		this.menu_toolbar.addSeparator(  );
		this.menu_toolbar.add(btnReporte );
		this.menu_toolbar.addSeparator(  );
		this.menu_toolbar.addSeparator(  );
		this.menu_toolbar.add(btnReporteL);
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
	
		 int x=15, y=20,width=120,height=20,sep=75;
		this.panel.add(menu_toolbar).setBounds                  (x     ,y      ,width*6    ,height );
		this.panel.add(new JLabel("Folio:")).setBounds          (x     ,y+=40  ,width      ,height );
		this.panel.add(txtFolio).setBounds                      (x+=sep,y      ,width      ,height );
        this.panel.add(btnizquierda).setBounds                  (x+=125,y      ,height     ,height );
        this.panel.add(btnderecha).setBounds                    (x+=25 ,y      ,height     ,height );
		this.panel.add(new JLabel("Estatus:")).setBounds        (x+=35 ,y      ,width      ,height );
		this.panel.add(cmb_status).setBounds                    (x+=45 ,y      ,width-50   ,height );
		this.panel.add(pestanas).setBounds                      (x+=80 ,y      ,610        ,590 );
		 x=15;width=100;
		this.panel.add(new JLabel("Cuadrante:")).setBounds      (x     ,y+=30  ,width      ,height );
		this.panel.add(txtCuadrante).setBounds                  (x+sep ,y      ,width*3    ,height );
		this.panel.add(new JLabel("Establecimiento:")).setBounds(x     ,y+=30  ,width      ,height );
		this.panel.add(cmbEstablecimiento).setBounds            (x+sep ,y      ,width*3    ,height );
		this.panel.add(new JLabel("Departamento:")).setBounds   (x     ,y+=30  ,width      ,height );
		this.panel.add(cmbDepartamento).setBounds               (x+sep ,y      ,width*3    ,height );
		this.panel.add(new JLabel("Turno:")).setBounds          (x     ,y+=30  ,width      ,height );
		this.panel.add(cmbTurnos).setBounds                     (x+sep ,y      ,width*3    ,height );
		this.panel.add(new JLabel("Puesto:")).setBounds         (x     ,y+=30  ,width      ,height );
		this.panel.add(txtPuesto).setBounds                     (x+sep ,y      ,width*3-20 ,height );
		this.panel.add(btnfilpuestos).setBounds                 (x+355 ,y       ,height    ,height );
		this.panel.add(new JLabel("Reporta A:")).setBounds      (x     ,y+=30  ,width      ,height );
		this.panel.add(txtReporta).setBounds                    (x+sep ,y      ,width*3-20 ,height );
		this.panel.add(btnfilrepora).setBounds                  (x+355 ,y      ,height     ,height );
		this.panel.add(new JLabel("Objetivo Del Puesto:")).setBounds(x ,y+=30  ,width      ,height );
		this.panel.add(scrollobjet).setBounds                   (x     ,y+=20  ,width*4-25 ,100    );
		this.panel.add(new JLabel("Responsabilidades Del Puesto:")).setBounds(x,y+=105,180 ,height );
		this.panel.add(scrollrespons).setBounds                 (x     ,y+=20  ,width*4-25 ,220    );
		this.panelEnabledFalse();
		
		this.btnNuevo.addActionListener     (nuevo          );		
		this.btnfilpuestos.addActionListener(opBuscarPuesto );
		this.btnfilrepora.addActionListener (opBuscarReporta);		
		this.btnGuardar.addActionListener   (guardar        );
		this.btnDeshacer.addActionListener  (deshacer       );
		this.btnModificar.addActionListener (editar         );
		this.btnBuscar.addActionListener    (buscar         );
		this.btnSimilar.addActionListener   (op_similar     );
		this.btnderecha.addActionListener   (opDerecha      );
		this.btnizquierda.addActionListener (opIzquierda    );
		
		this.btnReporte.addActionListener(reporte);
		this.btnReporteL.addActionListener(reporte);
		
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
	
	public void cargar_datos_tablas(int cuadranteparametro){
	  Obj_Cuadrantes cuadrante = new Obj_Cuadrantes();
	  String[][] tablacompleta= cuadrante.refrescar_tablas(cuadranteparametro);
	  Object[]   vector = new Object[5];
	  
	  txtFolio.setText                  (tablacompleta[0][ 6].toString());
	  txtCuadrante.setText              (tablacompleta[0][ 7].toString());
	  cmbEstablecimiento.setSelectedItem(tablacompleta[0][ 8].toString());
	  cmbDepartamento.setSelectedItem   (tablacompleta[0][ 9].toString());
	  txtPuesto.setText                 (tablacompleta[0][10].toString());
	  txtReporta.setText                (tablacompleta[0][11].toString());
	  txaResponsabili.setText           (tablacompleta[0][12].toString());
	  txaObjetivo.setText               (tablacompleta[0][13].toString()); 
	  cmb_status.setSelectedItem        (tablacompleta[0][14].toString());
	  cmbTurnos.setSelectedItem         (tablacompleta[0][15].toString());
	  
	  for(int i=0;i<tablacompleta.length;i++){
		 if(Integer.valueOf(tablacompleta[i][5].toString())==1){
			for(int j=0;j<5;j++){
			vector[j] = tablacompleta[i][j].toString();
		    }
			modelLunes.addRow(vector);
		}
		
        if(Integer.valueOf(tablacompleta[i][5].toString())==2){
        	for(int j=0;j<5;j++){
    			vector[j] = tablacompleta[i][j].toString();
    			}
    			modelMartes.addRow(vector);
		}
        
        if(Integer.valueOf(tablacompleta[i][5].toString())==3){
        	for(int j=0;j<5;j++){
    			vector[j] = tablacompleta[i][j].toString();
    			}
    			modelMiercoles.addRow(vector);
		}
        
        if(Integer.valueOf(tablacompleta[i][5].toString())==4){
        	for(int j=0;j<5;j++){
    			vector[j] = tablacompleta[i][j].toString();
    			}
    			modelJueves.addRow(vector);
		}
        
        if(Integer.valueOf(tablacompleta[i][5].toString())==5){
        	for(int j=0;j<5;j++){
    			vector[j] = tablacompleta[i][j].toString();
    			}
    			modelViernes.addRow(vector);
		}
        
        if(Integer.valueOf(tablacompleta[i][5].toString())==6){
        	for(int j=0;j<5;j++){
    			vector[j] = tablacompleta[i][j].toString();
    			}
    			modelSabado.addRow(vector);
		}
        
        if(Integer.valueOf(tablacompleta[i][5].toString())==7){
        	for(int j=0;j<5;j++){
    			vector[j] = tablacompleta[i][j].toString();
    			}
    			modelDomingo.addRow(vector);
		}
	 }	
	}
	
	public void init_tablalunes(){
		ObjTab.tabla_mascara(tablaLunes,3,4);
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
	    this.toolbarLunes.addSeparator(    );
	    this.toolbarLunes.addSeparator(    );
		this.toolbarLunes.add(btnCopLunes  );
		this.toolbarLunes.setFloatable(false);
		 int x=10, y=15,width=585,height=20,sev=25;
		this.pLunes.add(toolbarLunes).setBounds               (x     ,y      ,width    ,height );
		this.pLunes.add(txtBuscarLunes).setBounds             (x     ,y+=sev ,width    ,height );
		this.pLunes.add(Scroll_TablaLunes).setBounds          (x     ,y+=20  ,width    ,495    ); 
		this.txtBuscarLunes.addKeyListener(opFiltro_lunes);
		this.tablaLunes.addKeyListener(new op_validacelda_tabla(tablaLunes));
		this.btnSubLunes.addActionListener(new opMoverArriba(tablaLunes));
		this.btnBajLunes.addActionListener(new opMoverAbajo(tablaLunes));
		this.btnEljLunes.addActionListener(new opEliminarfila(tablaLunes));
		this.btnAgregLunes.addActionListener(new opAgregar_Actividad(tablaLunes));
		this.btnCopLunes.addActionListener(new OpCopiar_Tabla_Cuadrante(tablaLunes));
    }
	
	public void init_tablamartes(){
		ObjTab.tabla_mascara(tablaMartes,3,4);
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
	    this.toolbarMartes.addSeparator(     );
	    this.toolbarMartes.addSeparator(     );
		this.toolbarMartes.add(btnCopMartes  );
		this.toolbarMartes.setFloatable(false);
		 int x=10, y=15,width=585,height=20,sev=25;
		this.pMarte.add(toolbarMartes).setBounds               (x     ,y      ,width    ,height );
		this.pMarte.add(txtBuscarMartes).setBounds             (x     ,y+=sev ,width    ,height );
		this.pMarte.add(Scroll_TablaMartes).setBounds          (x     ,y+=20  ,width    ,495    ); 
		this.txtBuscarMartes.addKeyListener (opFiltro_martes);
		this.tablaMartes.addKeyListener(new op_validacelda_tabla(tablaMartes));
		this.btnSubMartes.addActionListener(new opMoverArriba(tablaMartes));
		this.btnBajMartes.addActionListener(new opMoverAbajo(tablaMartes));
		this.btnEljMartes.addActionListener(new opEliminarfila(tablaMartes));
		this.btnAgregMartes.addActionListener(new opAgregar_Actividad(tablaMartes));
		this.btnCopMartes.addActionListener(new OpCopiar_Tabla_Cuadrante(tablaMartes));
    }
	
	public void init_tablamiercoles(){
		ObjTab.tabla_mascara(tablaMiercoles,3,4);
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
	    this.toolbarMiercoles.addSeparator(     );
	    this.toolbarMiercoles.addSeparator(     );
		this.toolbarMiercoles.add(btnCopMiercoles  );
		this.toolbarMiercoles.setFloatable(false);
		 int x=10, y=15,width=585,height=20,sev=25;
		this.pMiercoles.add(toolbarMiercoles).setBounds               (x     ,y      ,width    ,height );
		this.pMiercoles.add(txtBuscarMiercoles).setBounds             (x     ,y+=sev ,width    ,height );
		this.pMiercoles.add(Scroll_TablaMiercoles).setBounds          (x     ,y+=20  ,width    ,495    );
		this.txtBuscarMiercoles.addKeyListener (opFiltro_miercoles);
		this.tablaMiercoles.addKeyListener(new op_validacelda_tabla(tablaMiercoles));
		this.btnSubMiercoles.addActionListener(new opMoverArriba(tablaMiercoles));
		this.btnBajMiercoles.addActionListener(new opMoverAbajo(tablaMiercoles));
		this.btnEljMiercoles.addActionListener(new opEliminarfila(tablaMiercoles));
		this.btnAgregMiercoles.addActionListener(new opAgregar_Actividad(tablaMiercoles));
		this.btnCopMiercoles.addActionListener(new OpCopiar_Tabla_Cuadrante(tablaMiercoles));
    }
	
	public void init_tablajueves(){
	ObjTab.tabla_mascara(tablaJueves,3,4);
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
    this.toolbarJueves.addSeparator(     );
    this.toolbarJueves.addSeparator(     );
	this.toolbarJueves.add(btnCopJueves  );
	this.toolbarJueves.setFloatable(false);
	 int x=10, y=15,width=585,height=20,sev=25;
	this.pJueves.add(toolbarJueves).setBounds               (x     ,y      ,width    ,height );
	this.pJueves.add(txtBuscarJueves).setBounds             (x     ,y+=sev ,width    ,height );
	this.pJueves.add(Scroll_TablaJueves).setBounds          (x     ,y+=20  ,width    ,495    );
	this.txtBuscarJueves.addKeyListener (opFiltro_jueves);
	this.tablaJueves.addKeyListener(new op_validacelda_tabla(tablaJueves));
	this.btnSubJueves.addActionListener(new opMoverArriba(tablaJueves));
	this.btnBajJueves.addActionListener(new opMoverAbajo(tablaJueves));
	this.btnEljJueves.addActionListener(new opEliminarfila(tablaJueves));
	this.btnAgregJueves.addActionListener(new opAgregar_Actividad(tablaJueves));
	this.btnCopJueves.addActionListener(new OpCopiar_Tabla_Cuadrante(tablaJueves));
	}
	
	public void init_tablaviernes(){
		ObjTab.tabla_mascara(tablaViernes,3,4);
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
	    this.toolbarViernes.addSeparator(      );
	    this.toolbarViernes.addSeparator(      );
		this.toolbarViernes.add(btnCopViernes  );
		this.toolbarViernes.setFloatable(false);
		 int x=10, y=15,width=585,height=20,sev=25;
		this.pViernes.add(toolbarViernes).setBounds               (x     ,y      ,width    ,height );
		this.pViernes.add(txtBuscarViernes).setBounds             (x     ,y+=sev ,width    ,height );
		this.pViernes.add(Scroll_TablaViernes).setBounds          (x     ,y+=20  ,width    ,495    );
		this.txtBuscarViernes.addKeyListener (opFiltro_viernes);
		this.tablaViernes.addKeyListener(new op_validacelda_tabla(tablaViernes));
		this.btnSubViernes.addActionListener(new opMoverArriba(tablaViernes));
		this.btnBajViernes.addActionListener(new opMoverAbajo(tablaViernes));
		this.btnEljViernes.addActionListener(new opEliminarfila(tablaViernes));
		this.btnAgregViernes.addActionListener(new opAgregar_Actividad(tablaViernes));
		this.btnCopViernes.addActionListener(new OpCopiar_Tabla_Cuadrante(tablaViernes));
		}
	
	public void init_tablasabado(){
		ObjTab.tabla_mascara(tablaSabado,3,4);
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
	    this.toolbarSabado.addSeparator(     );
	    this.toolbarSabado.addSeparator(     );
		this.toolbarSabado.add(btnCopSabado  );
		this.toolbarSabado.setFloatable(false);
		 int x=10, y=15,width=585,height=20,sev=25;
		this.pSabado.add(toolbarSabado).setBounds               (x     ,y      ,width    ,height );
		this.pSabado.add(txtBuscarSabado).setBounds             (x     ,y+=sev ,width    ,height );
		this.pSabado.add(Scroll_TablaSabado).setBounds          (x     ,y+=20  ,width    ,495    );
		this.txtBuscarSabado.addKeyListener (opFiltro_sabado);
		this.tablaSabado.addKeyListener(new op_validacelda_tabla(tablaSabado));
		this.btnSubSabado.addActionListener(new opMoverArriba(tablaSabado));
		this.btnBajSabado.addActionListener(new opMoverAbajo(tablaSabado));
		this.btnEljSabado.addActionListener(new opEliminarfila(tablaSabado));
		this.btnAgregSabado.addActionListener(new opAgregar_Actividad(tablaSabado));
		this.btnCopSabado.addActionListener(new OpCopiar_Tabla_Cuadrante(tablaSabado));
		}

	public void init_tablaDomingo(){
		ObjTab.tabla_mascara(tablaDomingo,3,4);
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
	    this.toolbarDomingo.addSeparator(      );
	    this.toolbarDomingo.addSeparator(      );
		this.toolbarDomingo.add(btnCopDomingo  );
		this.toolbarDomingo.setFloatable(false);
		 int x=10, y=15,width=585,height=20,sev=25;
		this.pDomingo.add(toolbarDomingo).setBounds               (x     ,y      ,width    ,height );
		this.pDomingo.add(txtBuscarDomingo).setBounds             (x     ,y+=sev ,width    ,height );
		this.pDomingo.add(Scroll_TablaDomingo).setBounds          (x     ,y+=20  ,width    ,495    );
		this.txtBuscarDomingo.addKeyListener (opFiltro_domingo);
		this.tablaDomingo.addKeyListener(new op_validacelda_tabla(tablaDomingo));
		this.btnSubDomingo.addActionListener(new opMoverArriba(tablaDomingo));
		this.btnBajDomingo.addActionListener(new opMoverAbajo(tablaDomingo));
		this.btnEljDomingo.addActionListener(new opEliminarfila(tablaDomingo));
		this.btnAgregDomingo.addActionListener(new opAgregar_Actividad(tablaDomingo));
		this.btnCopDomingo.addActionListener(new OpCopiar_Tabla_Cuadrante(tablaDomingo));
		}
	
	KeyListener opFiltro_lunes = new KeyListener(){
		public void keyReleased(KeyEvent arg0) {
			ObjTab.Obj_Filtro(tablaLunes, txtBuscarLunes.getText(), columnas,txtBuscarLunes);
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}		
	};
	
	KeyListener opFiltro_martes = new KeyListener(){
		public void keyReleased(KeyEvent arg0) {
			ObjTab.Obj_Filtro(tablaMartes, txtBuscarMartes.getText(), columnas,txtBuscarMartes);
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}		
	};
	
	KeyListener opFiltro_miercoles = new KeyListener(){
		public void keyReleased(KeyEvent arg0) {
			ObjTab.Obj_Filtro(tablaMiercoles, txtBuscarMiercoles.getText(), columnas,txtBuscarMiercoles);
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}		
	};
	
	KeyListener opFiltro_jueves = new KeyListener(){
		public void keyReleased(KeyEvent arg0) {
			ObjTab.Obj_Filtro(tablaJueves, txtBuscarJueves.getText(), columnas,txtBuscarJueves);
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}		
	};
	
	KeyListener opFiltro_viernes = new KeyListener(){
		public void keyReleased(KeyEvent arg0) {
			ObjTab.Obj_Filtro(tablaViernes, txtBuscarViernes.getText(), columnas,txtBuscarViernes);
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}		
	};
	
	KeyListener opFiltro_sabado = new KeyListener(){
		public void keyReleased(KeyEvent arg0) {
			ObjTab.Obj_Filtro(tablaSabado, txtBuscarSabado.getText(), columnas,txtBuscarSabado);
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}		
	};
	
	KeyListener opFiltro_domingo = new KeyListener(){
		public void keyReleased(KeyEvent arg0) {
			ObjTab.Obj_Filtro(tablaDomingo, txtBuscarDomingo.getText(), columnas,txtBuscarDomingo);
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}		
	};
	
	ActionListener opBuscarPuesto = new ActionListener(){
		public void actionPerformed(ActionEvent e){
         new Cat_Filtro_Puestos("Puesto").setVisible(true);
		}
	};
	
	ActionListener opBuscarReporta = new ActionListener(){
		public void actionPerformed(ActionEvent e){
         new Cat_Filtro_Puestos("Reporta").setVisible(true);
		}
	};
	
	ActionListener opDerecha = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			int Folio=0;
			if(!txtFolio.getText().equals("")){
				Folio=Integer.valueOf(txtFolio.getText().trim());
			}
			panelLimpiar();
			panelEnabledFalse();
			cargar_datos_tablas(Folio+1);
			btnSimilar.setEnabled(true);
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
			panelEnabledFalse();
			cargar_datos_tablas(Folio-1);
			btnSimilar.setEnabled(true);
			btnModificar.setEnabled(true);
		}
	};
	
	ActionListener deshacer = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			panelLimpiar();
			panelEnabledFalse();
			btnNuevo.setEnabled(true);
			btnModificar.setEnabled(false);
			NuevoModifica="";
		}
	};
	
	ActionListener buscar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			new Cat_Filtro_Buscar_Cuadrantes().setVisible(true);
		}
	};
	
	ActionListener op_similar = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			if(!txtFolio.getText().equals("")){
				panelEnabledTrue();
				txtCuadrante.requestFocus();
				txtFolio.setText(new Obj_Cuadrantes().Nuevo()+"");
				cmb_status.setSelectedIndex(0);
				txtCuadrante.setEditable(true);
				NuevoModifica="N";
				btnModificar.setEnabled(false);
			}else{
				JOptionPane.showMessageDialog(null, "Busque un registro primero antes de hacer un similar", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}
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
            if(columnak>2){
            	if(ObjTab.validacelda(tablaparametro,"hora", filak, columnak)){
            		ObjTab.RecorridoFocotabla(tablaparametro, filak, columnak, "seguir");
  			     }
            }
		}
		@Override
		public void keyTyped(KeyEvent arg0) {}
	};
	
	class OpCopiar_Tabla_Cuadrante implements ActionListener{   
		JTable tablaparametro;
	    public OpCopiar_Tabla_Cuadrante (final JTable tblp){
	    	tablaparametro = tblp;
	    }
	    public void actionPerformed(ActionEvent evt){
	    	String[][] tablaCopiar =(ObjTab.tabla_guardar_sin_validacion(tablaparametro));
	    	new Cat_Copiar_Cuadrante(tablaCopiar).setVisible(true);
	    }
	};
	
	class opAgregar_Actividad implements ActionListener{   
		JTable tablaparametro;
	    public opAgregar_Actividad(final JTable tblp){
	    	tablaparametro = tblp;
	    }
	    public void actionPerformed(ActionEvent evt){
	         new Cat_Filtro_Actividades_De_Cuadrantes(tablaparametro).setVisible(true);
	    }
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
					
					modeloparametro.setValueAt(primeraColum,tablaparametro.getSelectedRow()-1    ,1);
					modeloparametro.setValueAt(segundaColum,tablaparametro.getSelectedRow()      ,1);	
					modeloparametro.setValueAt(terceraColumsube,tablaparametro.getSelectedRow()-1,2);	
					modeloparametro.setValueAt(terceraColumbaja,tablaparametro.getSelectedRow()  ,2);	
					modeloparametro.setValueAt(cuartaColumsube,tablaparametro.getSelectedRow()-1 ,3);	
					modeloparametro.setValueAt(cuartaColumbaja,tablaparametro.getSelectedRow()   ,3);	
					modeloparametro.setValueAt(quintaColumsube,tablaparametro.getSelectedRow()-1 ,4);	
					modeloparametro.setValueAt(quintaColumbaja,tablaparametro.getSelectedRow()   ,4);	
					
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
					
					modeloparametro.setValueAt(primeraColum,tablaparametro.getSelectedRow()+1    ,1);
					modeloparametro.setValueAt(segundaColumBaja,tablaparametro.getSelectedRow()+1,1);	
					modeloparametro.setValueAt(segundaColumSube,tablaparametro.getSelectedRow()  ,1);	
					modeloparametro.setValueAt(terceraColumBaja,tablaparametro.getSelectedRow()+1,2);	
					modeloparametro.setValueAt(terceraColumSube,tablaparametro.getSelectedRow()  ,2);
					modeloparametro.setValueAt(cuartaColumBaja,tablaparametro.getSelectedRow()+1 ,3);	
					modeloparametro.setValueAt(cuartaColumSube,tablaparametro.getSelectedRow()   ,3);	
					modeloparametro.setValueAt(quintaColumBaja,tablaparametro.getSelectedRow()+1 ,4);	
					modeloparametro.setValueAt(quintaColumSube,tablaparametro.getSelectedRow()   ,4);	
					
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
    
	ActionListener reporte = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			
			String tipo = e.getActionCommand().toString();
			
			if(!txtFolio.getText().trim().equals("")){
					String basedatos="2.26";
					String vista_previa_reporte="no";
					int vista_previa_de_ventana=0;
					String reporte = "Obj_Reporte_De_Cuadrantes"+(tipo.equals("Reporte")?"":"_Limpio")+".jrxml";
				    String comando = "exec reporte_de_cuadrante "+txtFolio.getText().trim();
				    new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
			}else{
				JOptionPane.showMessageDialog(null, "Es Necesario Ingresar El Folio Del Cuadrante","Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}
		}		
	};
	
	ActionListener editar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			panelEnabledTrue();
			cmb_status.setEnabled(true);
			txtFolio.setEditable(false);
			btnModificar.setEnabled(false);
			btnGuardar.setEnabled(true);
			txtCuadrante.setEditable(true);
			NuevoModifica="M";
		}		
		};
		
	ActionListener nuevo = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			panelLimpiar();
			panelEnabledTrue();
			txtFolio.setText(new Obj_Cuadrantes().Nuevo()+"");
			btnGuardar.setEnabled(true);
			txtCuadrante.setEditable(true);
			NuevoModifica="N";
			txtCuadrante.requestFocus();
			btnModificar.setEnabled(false);
		}
	};
	
    ActionListener guardar = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				    String Mensaje =Valida();
					if(!Mensaje.equals("Para Poder Guardar Es Requerido Alimente:")){
						JOptionPane.showMessageDialog(null, Mensaje, "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
					}else{
					Obj_Cuadrantes cuadrantes = new Obj_Cuadrantes();

					cuadrantes.setFolio(Integer.valueOf(txtFolio.getText().toString().trim()));
					cuadrantes.setCuadrante(txtCuadrante.getText().toString().trim());
					cuadrantes.setEstablecimiento(cmbEstablecimiento.getSelectedItem().toString());
					cuadrantes.setDepartamento(cmbDepartamento.getSelectedItem().toString());
					cuadrantes.setTurno(cmbTurnos.getSelectedItem().toString());
					cuadrantes.setPuesto(txtPuesto.getText().toString().trim());
					cuadrantes.setPuesto_reporta(txtReporta.getText().toString().trim());
					cuadrantes.setResponsabilidades(txaResponsabili.getText().toString().trim());
					cuadrantes.setObjetivo(txaObjetivo.getText().toString().trim());
					cuadrantes.setEstatus(cmb_status.getSelectedItem().toString().trim());
                    cuadrantes.setNuevoModifica(NuevoModifica);  
                    cuadrantes.setTabla_actividades(TablaGuardado());
                    
        			if(cuadrantes.Guardar()){
    					JOptionPane.showMessageDialog(null,"El Registro Se Guardó Correctamente!","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/aplicara-el-dialogo-icono-6256-32.png"));
    					btnDeshacer.doClick();
    					return;
    				}else{
    					JOptionPane.showMessageDialog(null,"Error Al Guardar Avise al Administrador del Sistema","Aviso",JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-icono-eliminar5252-64.png"));
    					return;
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
			String[][] tablas = new String[filas][6];	
			while(rengloneslunes > 0){
					tablas[i][0] = modelLunes.getValueAt(fila, 0)+"";
					tablas[i][1] = modelLunes.getValueAt(fila, 1)+"";
					tablas[i][2] = modelLunes.getValueAt(fila, 2)+"";
					tablas[i][3] = modelLunes.getValueAt(fila, 3)+"";
					tablas[i][4] = modelLunes.getValueAt(fila, 4)+"";
					tablas[i][5] = "1";
					i+=1;
					fila+=1;
					rengloneslunes--;
			}
			
			fila=0;
			while(renglonesMartes > 0){
					tablas[i][0] = modelMartes.getValueAt(fila, 0)+"";
					tablas[i][1] = modelMartes.getValueAt(fila, 1)+"";
					tablas[i][2] = modelMartes.getValueAt(fila, 2)+"";
					tablas[i][3] = modelMartes.getValueAt(fila, 3)+"";
					tablas[i][4] = modelMartes.getValueAt(fila, 4)+"";
					tablas[i][5] = "2";
					i+=1;
					fila+=1;
					renglonesMartes--;
			}
			
			fila=0;
			while(renglonesMiercoles > 0){
					tablas[i][0] = modelMiercoles.getValueAt(fila, 0)+"";
					tablas[i][1] = modelMiercoles.getValueAt(fila, 1)+"";
					tablas[i][2] = modelMiercoles.getValueAt(fila, 2)+"";
					tablas[i][3] = modelMiercoles.getValueAt(fila, 3)+"";
					tablas[i][4] = modelMiercoles.getValueAt(fila, 4)+"";
					tablas[i][5] = "3";
					i+=1;
					fila+=1;
					renglonesMiercoles--;
			}
			
			fila=0;
			while(renglonesJueves > 0){
					tablas[i][0] = modelJueves.getValueAt(fila, 0)+"";
					tablas[i][1] = modelJueves.getValueAt(fila, 1)+"";
					tablas[i][2] = modelJueves.getValueAt(fila, 2)+"";
					tablas[i][3] = modelJueves.getValueAt(fila, 3)+"";
					tablas[i][4] = modelJueves.getValueAt(fila, 4)+"";
					tablas[i][5] = "4";
					i+=1;
					fila+=1;
					renglonesJueves--;
			}
			
			fila=0;
			while(renglonesViernes > 0){
					tablas[i][0] = modelViernes.getValueAt(fila, 0)+"";
					tablas[i][1] = modelViernes.getValueAt(fila, 1)+"";
					tablas[i][2] = modelViernes.getValueAt(fila, 2)+"";
					tablas[i][3] = modelViernes.getValueAt(fila, 3)+"";
					tablas[i][4] = modelViernes.getValueAt(fila, 4)+"";
					tablas[i][5] = "5";
					i+=1;
					fila+=1;
					renglonesViernes--;
			}
			
			fila=0;
			while(renglonesSabado > 0){
					tablas[i][0] = modelSabado.getValueAt(fila, 0)+"";
					tablas[i][1] = modelSabado.getValueAt(fila, 1)+"";
					tablas[i][2] = modelSabado.getValueAt(fila, 2)+"";
					tablas[i][3] = modelSabado.getValueAt(fila, 3)+"";
					tablas[i][4] = modelSabado.getValueAt(fila, 4)+"";
					tablas[i][5] = "6";
					i+=1;
					fila+=1;
					renglonesSabado--;
			}
			
			fila=0;			
			while(renglonesdomingo > 0){
				tablas[i][0] = modelDomingo.getValueAt(fila, 0)+"";
				tablas[i][1] = modelDomingo.getValueAt(fila, 1)+"";
				tablas[i][2] = modelDomingo.getValueAt(fila, 2)+"";
				tablas[i][3] = modelDomingo.getValueAt(fila, 3)+"";
				tablas[i][4] = modelDomingo.getValueAt(fila, 4)+"";
				tablas[i][5] = "7";
				i+=1;
				fila+=1;
			renglonesdomingo--;
		   }
			return tablas;
		}
		
	public String Valida(){
	    String Mensaje ="Para Poder Guardar Es Requerido Alimente:";
	    if(txtCuadrante.getText().equals("")){Mensaje+="\nEl Nombre Del Cuadrante"; }
	    if(cmbEstablecimiento.getSelectedIndex()==0){Mensaje+="\nEl Establecimiento"; }
	    if(cmbDepartamento.getSelectedIndex()==0){Mensaje+="\nEl Departamento"; }
	    if(cmbTurnos.getSelectedIndex()==0){Mensaje+="\nEl Turno"; }
	    if(txtPuesto.getText().equals("")){Mensaje+="\nEl Nombre Del Puesto"; }
	    if(txtReporta.getText().equals("")){Mensaje+="\nEl Puesto Al Que Reporta"; }
	    if(txaObjetivo.getText().equals("")){Mensaje+="\nLos Objetivos Del Puesto"; }
	    if(txaResponsabili.getText().equals("")){Mensaje+="\nLas Responsabilidades Del Puesto"; }
	    
	    int rengloneslunes     = tablaLunes.getRowCount()    ;
		int renglonesMartes    = tablaMartes.getRowCount()   ;
		int renglonesMiercoles = tablaMiercoles.getRowCount();
		int renglonesJueves    = tablaJueves.getRowCount()   ;
		int renglonesViernes   = tablaViernes.getRowCount()  ;
		int renglonesSabado    = tablaSabado.getRowCount()   ;
		int renglonesdomingo   = tablaDomingo.getRowCount()  ;
		int filas = rengloneslunes+renglonesMartes+renglonesMiercoles+renglonesJueves+renglonesViernes+renglonesSabado+renglonesdomingo;
		if (filas==0){
			Mensaje+="\nAlguna Actividad En Por Lo Menos 1 Dia De La Semana"; 
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
		txtCuadrante.setText("");
		txtPuesto.setText("");
		txtReporta.setText("");
		txaResponsabili.setText("");
		txaObjetivo.setText("");
		txtBuscarLunes.setText("");
		txtBuscarMartes.setText("");
		txtBuscarMiercoles.setText("");
		txtBuscarJueves.setText("");
		txtBuscarViernes.setText("");
		txtBuscarSabado.setText("");
		txtBuscarDomingo.setText("");
		
		cmb_status.setSelectedIndex(0);
		cmbDepartamento.setSelectedIndex(0);
		cmbEstablecimiento.setSelectedIndex(0);
		cmbTurnos.setSelectedIndex(0);
		
		ObjTab.Obj_Filtro(tablaLunes    , "", columnas,txtBuscarLunes);
		ObjTab.Obj_Filtro(tablaMartes   , "", columnas,txtBuscarMartes);
		ObjTab.Obj_Filtro(tablaMiercoles, "", columnas,txtBuscarMiercoles);
		ObjTab.Obj_Filtro(tablaJueves   , "", columnas,txtBuscarJueves);
		ObjTab.Obj_Filtro(tablaViernes  , "", columnas,txtBuscarViernes);
		ObjTab.Obj_Filtro(tablaSabado   , "", columnas,txtBuscarSabado);
		ObjTab.Obj_Filtro(tablaDomingo  , "", columnas,txtBuscarDomingo);
	}	
	
	
	public void panelEnabledFalse(){
		txtFolio.setEditable  (false);
		txtCuadrante.setEditable(false);
		txtPuesto.setEditable(false);
		txtReporta.setEditable(false);
		txaResponsabili.setEditable(false);
		txaObjetivo.setEditable(false);
		txtBuscarLunes.setEditable(false);
		txtBuscarMartes.setEditable(false);
		txtBuscarMiercoles.setEditable(false);
		txtBuscarJueves.setEditable(false);
		txtBuscarViernes.setEditable(false);
		txtBuscarSabado.setEditable(false);
		txtBuscarDomingo.setEditable(false);
		
		btnModificar.setEnabled  (false);
        btnGuardar.setEnabled (false);
        btnfilpuestos.setEnabled(false);
        btnfilrepora.setEnabled(false);
        btnAgregLunes.setEnabled(false);
        btnAgregMartes.setEnabled(false);
        btnAgregMiercoles.setEnabled(false);
        btnAgregJueves.setEnabled(false);
        btnAgregViernes.setEnabled(false);
        btnAgregSabado.setEnabled(false);
        btnAgregDomingo.setEnabled(false);
        btnBajLunes.setEnabled(false);
        btnBajMartes.setEnabled(false);
        btnBajMiercoles.setEnabled(false);
        btnBajJueves.setEnabled(false);
        btnBajViernes.setEnabled(false);
        btnBajSabado.setEnabled(false);
        btnBajDomingo.setEnabled(false);
        btnEljLunes.setEnabled(false);
        btnEljMartes.setEnabled(false);
        btnEljMiercoles.setEnabled(false);
        btnEljJueves.setEnabled(false);
        btnEljViernes.setEnabled(false);
        btnEljSabado.setEnabled(false);
        btnEljDomingo.setEnabled(false);
        btnSubLunes.setEnabled(false);
        btnSubMartes.setEnabled(false);
        btnSubMiercoles.setEnabled(false);
        btnSubJueves.setEnabled(false);
        btnSubViernes.setEnabled(false);
        btnSubSabado.setEnabled(false);
        btnSubDomingo.setEnabled(false);
        btnCopLunes.setEnabled(false);
        btnCopMartes.setEnabled(false);
        btnCopMiercoles.setEnabled(false);
        btnCopJueves.setEnabled(false);
        btnCopViernes.setEnabled(false);
        btnCopSabado.setEnabled(false);
        btnCopDomingo.setEnabled(false);
		cmb_status.setEnabled (false);
		cmbDepartamento.setEnabled(false);
		cmbTurnos.setEnabled(false);
		cmbEstablecimiento.setEnabled(false);
		
		bloqueocolumna=false;
	}		
	
	public void panelEnabledTrue(){	
		txaResponsabili.setEditable(true);
		txaObjetivo.setEditable(true);
		txtBuscarLunes.setEditable(true);
		txtBuscarMartes.setEditable(true);
		txtBuscarMiercoles.setEditable(true);
		txtBuscarJueves.setEditable(true);
		txtBuscarViernes.setEditable(true);
		txtBuscarSabado.setEditable(true);
		txtBuscarDomingo.setEditable(true);
		
		bloqueocolumna=true;
		
		btnModificar.setEnabled  (true);
        btnGuardar.setEnabled (true);
        btnfilpuestos.setEnabled(true);
        btnfilrepora.setEnabled(true);
        btnAgregLunes.setEnabled(true);
        btnAgregMartes.setEnabled(true);
        btnAgregMiercoles.setEnabled(true);
        btnAgregJueves.setEnabled(true);
        btnAgregViernes.setEnabled(true);
        btnAgregSabado.setEnabled(true);
        btnAgregDomingo.setEnabled(true);
        btnBajLunes.setEnabled(true);
        btnBajMartes.setEnabled(true);
        btnBajMiercoles.setEnabled(true);
        btnBajJueves.setEnabled(true);
        btnBajViernes.setEnabled(true);
        btnBajSabado.setEnabled(true);
        btnBajDomingo.setEnabled(true);
        btnEljLunes.setEnabled(true);
        btnEljMartes.setEnabled(true);
        btnEljMiercoles.setEnabled(true);
        btnEljJueves.setEnabled(true);
        btnEljViernes.setEnabled(true);
        btnEljSabado.setEnabled(true);
        btnEljDomingo.setEnabled(true);
        btnSubLunes.setEnabled(true);
        btnSubMartes.setEnabled(true);
        btnSubMiercoles.setEnabled(true);
        btnSubJueves.setEnabled(true);
        btnSubViernes.setEnabled(true);
        btnSubSabado.setEnabled(true);
        btnSubDomingo.setEnabled(true);
        btnCopLunes.setEnabled(true);
        btnCopMartes.setEnabled(true);
        btnCopMiercoles.setEnabled(true);
        btnCopJueves.setEnabled(true);
        btnCopViernes.setEnabled(true);
        btnCopSabado.setEnabled(true);
        btnCopDomingo.setEnabled(true);
        
		cmb_status.setEnabled (true);
		cmbDepartamento.setEnabled(true);
		cmbTurnos.setEnabled(true);
		cmbEstablecimiento.setEnabled(true);
		btnBuscar.requestFocus();
	}
	
	//TODO inicia catalogo copiar cuadrante	
	public class Cat_Copiar_Cuadrante extends JDialog{
		Container contf = getContentPane();
		JLayeredPane panelf = new JLayeredPane();
		Connexion con = new Connexion();
		int columnacopiar = 5,checkbox=-1;
		
		@SuppressWarnings("rawtypes")
		public Class[] base (){
			Class[] types = new Class[columnacopiar];
			for(int i = 0; i<columnacopiar; i++){types[i]= java.lang.Object.class;}
			 return types;
		}
		
		public DefaultTableModel modelocopiar = new DefaultTableModel(null, new String[]{"Orden","Folio","Actividad","Inicia","Termina"}){
			 @SuppressWarnings("rawtypes")
				Class[] types = base();
				@SuppressWarnings({ "rawtypes", "unchecked" })
				public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
				public boolean isCellEditable(int fila, int columna){return false;}
		};
		
		JTable tablacopiar = new JTable(modelocopiar);
		public JScrollPane scroll_tabla_copiar = new JScrollPane(tablacopiar);
	     @SuppressWarnings({ "rawtypes" })
	    private TableRowSorter trsfiltro;
		
	    JCheckBox chbP_Lunes     = new JCheckBox("Lunes    ");
	 	JCheckBox chbP_Martes    = new JCheckBox("Martes   ");
	 	JCheckBox chbP_Miercoles = new JCheckBox("Miercoles");
	 	JCheckBox chbP_Jueves    = new JCheckBox("Jueves   ");
	 	JCheckBox chbP_Viernes   = new JCheckBox("Viernes  ");
	 	JCheckBox chbP_Sabado    = new JCheckBox("Sabado   ");
	 	JCheckBox chbP_Domingo   = new JCheckBox("Domingo  ");
	 	
		JCButton btnAgregCopiar      = new JCButton("Agregar Actividad","double-arrow-icone-3883-16.png"                  ,"Azul" );
		JCButton btnSubCopiar        = new JCButton("Subir"            ,"Up.png"                                          ,"Azul" );
		JCButton btnBajCopiar        = new JCButton("Bajar"            ,"Down.png"                                        ,"Azul" );
		JCButton btnEljCopiar        = new JCButton("Eliminar"         ,"eliminar-bala-icono-7773-32.png"                 ,"Azul" );
		JCButton btnCopiar           = new JCButton("Copiar A Dias Selecionados","copy-documents-files-icone-9003-16.png","Azul" );
		
		JToolBar toolbarcopiar = new JToolBar();
		
		JTextField txtBuscarfp  = new Componentes().text(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<", 300, "String");
		String [][] tablaparametro=null;
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Cat_Copiar_Cuadrante(String[][] arregloparametro){
			this.setSize(610,620);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			this.setModal(true);
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/tarjeta-de-informacion-del-usuario-icono-7370-32.png"));
			this.panelf.setBorder(BorderFactory.createTitledBorder("Selecione los Dias a Los Que Se Copiarará El Cuadrante"));
			this.setTitle("Copiar Cuadrantes");
			this.trsfiltro = new TableRowSorter(modelocopiar); 
			this.tablacopiar.setRowSorter(trsfiltro);
			
		    this.toolbarcopiar.add(btnAgregCopiar);
			this.toolbarcopiar.addSeparator(    );
			this.toolbarcopiar.addSeparator(    );
			this.toolbarcopiar.add(btnSubCopiar  );
		    this.toolbarcopiar.addSeparator(    );
		    this.toolbarcopiar.addSeparator(    );
		    this.toolbarcopiar.add(btnBajCopiar  );
		    this.toolbarcopiar.addSeparator(    );
		    this.toolbarcopiar.addSeparator(    );
		    this.toolbarcopiar.add(btnEljCopiar  );
		    this.toolbarcopiar.addSeparator(    );
		    this.toolbarcopiar.addSeparator(    );
		    this.toolbarcopiar.add(btnCopiar    );
		    
			this.toolbarcopiar.setFloatable(false);
            
			ObjTab.tabla_mascara(tablacopiar,3,4);
			String[][] tablacompiada= arregloparametro;
			Object[]   vectorc = new Object[5];				  
				for(int i=0;i<tablacompiada.length;i++){
			    	for(int j=0;j<5;j++){
						vectorc[j] = tablacompiada[i][j].toString();
					}
			      modelocopiar.addRow(vectorc);
				}
			int	x=10, y=20, width=73, height=20,sep=70;
				
            this.panelf.add(chbP_Lunes).setBounds          (x      ,y     ,width     ,height);
            this.panelf.add(chbP_Martes).setBounds         (x+=sep ,y     ,width     ,height);
            this.panelf.add(chbP_Miercoles).setBounds      (x+=sep ,y     ,width     ,height);
            this.panelf.add(chbP_Jueves).setBounds         (x+=sep ,y     ,width     ,height);
            this.panelf.add(chbP_Viernes).setBounds        (x+=sep ,y     ,width     ,height);
            this.panelf.add(chbP_Sabado).setBounds         (x+=sep ,y     ,width     ,height);
            this.panelf.add(chbP_Domingo).setBounds        (x+=sep ,y     ,width     ,height);
            this.panelf.add(toolbarcopiar).setBounds       (x=10   ,y+=30 ,width=585 ,height);
			this.panelf.add(txtBuscarfp).setBounds         (x      ,y+=30 ,width     ,height);
			this.panelf.add(scroll_tabla_copiar).setBounds (x      ,y+=20 ,width     ,495   );
			
			this.txtBuscarfp.addKeyListener      (opFiltroCopiar );
			this.btnAgregCopiar.addActionListener(new opAgregar_Actividad(tablacopiar));
			this.btnSubCopiar.addActionListener  (new opMoverArriba(tablacopiar));
			this.btnBajCopiar.addActionListener  (new opMoverAbajo(tablacopiar));
			this.btnEljCopiar.addActionListener  (new opEliminarfila(tablacopiar));
			this.btnCopiar.addActionListener     (copiar);
			
			contf.add(panelf);
		}
		
		ActionListener copiar = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(chbP_Lunes.isSelected())    {copiar_al_modelo(modelLunes    );}
				if(chbP_Martes.isSelected())   {copiar_al_modelo(modelMartes   );}
				if(chbP_Miercoles.isSelected()){copiar_al_modelo(modelMiercoles);}
				if(chbP_Jueves.isSelected())   {copiar_al_modelo(modelJueves   );}
				if(chbP_Viernes.isSelected())  {copiar_al_modelo(modelViernes  );}
				if(chbP_Sabado.isSelected())   {copiar_al_modelo(modelSabado   );}
				if(chbP_Domingo.isSelected())  {copiar_al_modelo(modelDomingo  );}
				dispose();
			}		
			};
			
		public void copiar_al_modelo(DefaultTableModel  modelo){
			modelo.setRowCount(0);
			String[][] tablaCopiar =(ObjTab.tabla_guardar_sin_validacion(tablacopiar));
			Object[]   vectorc = new Object[5];				  
			for(int i=0;i<tablaCopiar.length;i++){
		    	for(int j=0;j<5;j++){
					vectorc[j] = tablaCopiar[i][j].toString();
				}
		    	modelo.addRow(vectorc);
			}
		}
		
        private KeyListener opFiltroCopiar = new KeyListener(){
			public void keyReleased(KeyEvent arg0) {
				ObjTab.Obj_Filtro(tablacopiar, txtBuscarfp.getText(), columnacopiar,txtBuscarfp);
			}
			public void keyTyped(KeyEvent arg0) {}
			public void keyPressed(KeyEvent arg0) {}		
		};
	}
//termina filtro copiar cuadrante 	
//TODO inicia filtro actividades
	public class Cat_Filtro_Actividades_De_Cuadrantes extends JDialog {
		Container cont = getContentPane();
		JLayeredPane panel = new JLayeredPane();
	     @SuppressWarnings({ "rawtypes" })
	    private TableRowSorter trsfiltro;
		JTextField txtFiltro_a  = new Componentes().text(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<", 500, "String");
		JCButton btnAgregarActividad       = new JCButton("Agregar Actividad(es) Selecionada(s)","double-arrow-icone-3883-16.png"  ,"Azul" );

		JTable tablaparametro;
		DefaultTableModel modeloparametro;
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Cat_Filtro_Actividades_De_Cuadrantes(JTable jtableactividades){
			 tablaparametro=jtableactividades;
	    	 modeloparametro= (DefaultTableModel) tablaparametro.getModel();
			this.setSize(1024,600);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setModal(true);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			this.setTitle("Filtro de Actividades");
			this.panel.setBorder(BorderFactory.createTitledBorder("Filtro de Actividades"));
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Filter-List-icon32.png"));
			this.trsfiltro = new TableRowSorter(modeloa); 
			tablafa.setRowSorter(trsfiltro);
			this.panel.add(txtFiltro_a).setBounds   (10 ,15 ,700 ,20 );
			this.panel.add(scroll_tabla_a).setBounds(10 ,35 ,995 ,530);
			this.panel.add(btnAgregarActividad).setBounds(725, 15, 280, 20);;
			this.cont.add(panel);
			
			if(FActividadesCargado.equals("S")){
				datos_tabla_precargados();
			}else{
			 this.init();
			  tablaprecargadaactividades= ObjTab.tabla_guardar(tablafa);
			  FActividadesCargado="S";
			}
			
			txtFiltro_a.requestFocus();
			this.btnAgregarActividad.addActionListener(opAgregar);
			this.txtFiltro_a.addKeyListener(op_filtro_nombre_a);
		}
		  
		public void datos_tabla_precargados(){
			 modeloa.setRowCount(0);
			 String[][] tablacompleta =tablaprecargadaactividades;
			  Object[] vector = new Object[11];
			for(int i=0;i<tablacompleta.length;i++){
				   for(int j=0;j<11;j++){
					vector[j] = tablacompleta[i][j].toString();
					}
					modeloa.addRow(vector);
			}
		}
		
		public void init(){
			init_tabla();
		}
		
		ActionListener opAgregar = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				  if(tablafa.isEditing()){tablafa.getCellEditor().stopCellEditing(); }
				  ObjTab.Obj_Filtro(tablafa, "", columnas2,txtFiltro_a);
				  for (int i=0;i<tablafa.getRowCount();i++){
						if((tablafa.getValueAt(i,checkbox2-1).toString().trim()).equals("true")){
				    		  Object[] vector = new Object[5];
					    		  vector[0] = 0;
					    		  vector[1] =  tablafa.getValueAt(i,1).toString().trim();
					    		  vector[2] =  tablafa.getValueAt(i,2).toString().trim();
					    		  vector[3] =  "00:00";
					    		  vector[4] =  "00:00";
				    		  modeloparametro.addRow(vector);
						   }
					  };
				  for(int i2 =0; i2<tablaparametro.getRowCount(); i2++){
					  modeloparametro.setValueAt(i2+1,i2,0);
			      };
			      dispose();    
                  return;	    			
			}
		};
	
		KeyListener op_filtro_nombre_a = new KeyListener(){
			public void keyReleased(KeyEvent arg0) {
				ObjTab.Obj_Filtro(tablafa, txtFiltro_a.getText(), columnas2,txtFiltro_a);
			}
			public void keyTyped(KeyEvent arg0)   {}
			public void keyPressed(KeyEvent arg0) {}		
		};
		
	}	
///////////////termina filtro actividades	
//TODO inicia filtro_puestos	
	public class Cat_Filtro_Puestos extends JDialog{
		Container contf = getContentPane();
		JLayeredPane panelf = new JLayeredPane();
		Connexion con = new Connexion();
		
		Obj_tabla ObjTab =new Obj_tabla();
		int columnasp = 2,checkbox=-1;
		public void init_tablafp(){
	    	this.tablafp.getColumnModel().getColumn(0).setMinWidth(55);
	    	this.tablafp.getColumnModel().getColumn(1).setMinWidth(375);
	    	String comandof="exec cuadrantes_puestos_para_nuevos_cuadrantes ";
			String basedatos="26",pintar="si";
			ObjTab.Obj_Refrescar(tablafp,modelof, columnasp, comandof, basedatos,pintar,checkbox);
	    }
		
		@SuppressWarnings("rawtypes")
		public Class[] base (){
			Class[] types = new Class[columnasp];
			for(int i = 0; i<columnasp; i++){types[i]= java.lang.Object.class;}
			 return types;
		}
		
		public DefaultTableModel modelof = new DefaultTableModel(null, new String[]{"Folio","Puesto"}){
			 @SuppressWarnings("rawtypes")
				Class[] types = base();
				@SuppressWarnings({ "rawtypes", "unchecked" })
				public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
				public boolean isCellEditable(int fila, int columna){return false;}
		};
		
		JTable tablafp = new JTable(modelof);
		public JScrollPane scroll_tablafp = new JScrollPane(tablafp);
	     @SuppressWarnings({ "rawtypes" })
	    private TableRowSorter trsfiltro;
	     
			int columnasp2 = 4,checkbox2=-1;
			public void init_tablafp2(){
		    	this.tablafp2.getColumnModel().getColumn(0).setMinWidth(55);
		    	this.tablafp2.getColumnModel().getColumn(1).setMinWidth(375);
		    	this.tablafp2.getColumnModel().getColumn(2).setMinWidth(55);
		    	this.tablafp2.getColumnModel().getColumn(3).setMinWidth(280);
		    	String comandof="exec cuadrantes_puestos_para_nuevos_cuadrantes ";
				String basedatos="26",pintar="si";
				ObjTab.Obj_Refrescar(tablafp2,modelof2, columnasp2, comandof, basedatos,pintar,checkbox);
		    }
			
			@SuppressWarnings("rawtypes")
			public Class[] base2 (){
				Class[] types = new Class[columnasp2];
				for(int i = 0; i<columnasp2; i++){types[i]= java.lang.Object.class;}
				 return types;
			}
			
			public DefaultTableModel modelof2 = new DefaultTableModel(null, new String[]{"Folio","Puesto","Folio C.","Cuadrante"}){
				 @SuppressWarnings("rawtypes")
					Class[] types = base2();
					@SuppressWarnings({ "rawtypes", "unchecked" })
					public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
					public boolean isCellEditable(int fila, int columna){return false;}
			};
			
			JTable tablafp2 = new JTable(modelof2);
			public JScrollPane scroll_tablafp2 = new JScrollPane(tablafp2);
		     @SuppressWarnings({ "rawtypes" })
		    private TableRowSorter trsfiltro2;
		     
		JTextField txtBuscarfp  = new Componentes().text(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<", 300, "String");
		String parametro="";
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Cat_Filtro_Puestos(String btnparametro){
			parametro=btnparametro;
			
			if(parametro.equals("Puesto")){
				this.setSize(850,500);
				trsfiltro2 = new TableRowSorter(modelof2); 
				tablafp2.setRowSorter(trsfiltro2);
				this.panelf.add(txtBuscarfp).setBounds       (10 ,20 ,820 , 20 );
				this.panelf.add(scroll_tablafp2).setBounds   (10 ,40 ,820 ,415 );
				this.init_tablafp2();
				this.agregar(tablafp2);
				this.txtBuscarfp.addKeyListener  (opFiltropuestos2 );
			}else{
			this.setSize(500,500);
			trsfiltro = new TableRowSorter(modelof); 
			tablafp.setRowSorter(trsfiltro);
			this.panelf.add(txtBuscarfp).setBounds      (10 ,20 ,470 , 20 );
			this.panelf.add(scroll_tablafp).setBounds   (10 ,40 ,470 ,415 );
			this.init_tablafp();
			this.agregar(tablafp);
			this.txtBuscarfp.addKeyListener  (opFiltropuestos );

			}
			
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			this.setModal(true);
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Filter-List-icon32.png"));
			this.panelf.setBorder(BorderFactory.createTitledBorder("Selecione Un Puesto Con Doble Click"));
			this.setTitle("Filtro De Puestos");
			
			contf.add(panelf);
			
		}
		
		private void agregar(final JTable tbl) {
	        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
		        	if(e.getClickCount()==1){
		        		if(parametro.equals("Puesto")){
		        			int fila = tablafp2.getSelectedRow();
//		        			if(!tablafp2.getValueAt(fila,2).equals("0")){
//								JOptionPane.showMessageDialog(null, "Este Puesto Ya esta En Uso En El Cuadrante \n"+tablafp2.getValueAt(fila,3)+" \n Selecione Otro", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
//								txtBuscarfp.requestFocus();
//								return;
//		        			}else{		        			
						    txtPuesto.setText   (tablafp2.getValueAt(fila,1)+"");
//		        			}
						    
		        		}else{
		        			int fila = tablafp.getSelectedRow();
		        			txtReporta.setText  (tablafp.getValueAt(fila,1)+"");	
		        		}
		        		
						dispose();
		        	}
		        }
	        });
	    }
		
        private KeyListener opFiltropuestos = new KeyListener(){
			public void keyReleased(KeyEvent arg0) {
				ObjTab.Obj_Filtro(tablafp, txtBuscarfp.getText().toUpperCase(), columnasp,txtBuscarfp);
			}
			public void keyTyped(KeyEvent arg0) {}
			public void keyPressed(KeyEvent arg0) {}		
		};
		
		 private KeyListener opFiltropuestos2 = new KeyListener(){
				public void keyReleased(KeyEvent arg0) {
					ObjTab.Obj_Filtro(tablafp2, txtBuscarfp.getText().toUpperCase(), columnasp2,txtBuscarfp);
				}
				public void keyTyped(KeyEvent arg0) {}
				public void keyPressed(KeyEvent arg0) {}		
			};
	}//termina filtro puestos
	
	//TODO inicia filtro_Buscar	
		public class Cat_Filtro_Buscar_Cuadrantes extends JDialog{
			Container contfb = getContentPane();
			JLayeredPane panelfb = new JLayeredPane();
			Connexion con = new Connexion();
			Obj_tabla ObjTab =new Obj_tabla();
			int columnasb = 12,checkbox=-1;
			public void init_tablafp(){
		    	this.tablab.getColumnModel().getColumn( 0).setMinWidth(55);
		    	this.tablab.getColumnModel().getColumn( 1).setMinWidth(355);
		    	this.tablab.getColumnModel().getColumn( 2).setMinWidth(150);
		    	this.tablab.getColumnModel().getColumn( 3).setMinWidth(150);
		    	this.tablab.getColumnModel().getColumn( 4).setMinWidth(200);
		    	this.tablab.getColumnModel().getColumn( 5).setMinWidth(200);
		    	this.tablab.getColumnModel().getColumn( 6).setMinWidth(120);
		    	this.tablab.getColumnModel().getColumn( 7).setMinWidth(120);
		    	this.tablab.getColumnModel().getColumn( 8).setMinWidth(120);
		    	this.tablab.getColumnModel().getColumn( 9).setMinWidth(120);
		    	this.tablab.getColumnModel().getColumn(10).setMinWidth(120);
		    	String comandof=" exec cuadrantes_filtro";
				String basedatos="26",pintar="si";
				ObjTab.Obj_Refrescar(tablab,modelob, columnasb, comandof, basedatos,pintar,checkbox);
		    }
			
			@SuppressWarnings("rawtypes")
			public Class[] base (){
				Class[] types = new Class[columnasb];
				for(int i = 0; i<columnasb; i++){types[i]= java.lang.Object.class;}
				 return types;
			}
			
			public DefaultTableModel modelob = new DefaultTableModel(null, new String[]{"Folio","Nombre Cuadrante","Establecimiento","Departamento","Puesto","Puesto Reporta","Responsabilidades","Objetivos"
					 ,"Estatus","Fecha Guardado","Fecha Ultima Modificacion","Usuario Ultima Modificacion"}){
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
				this.setSize(1024,500);
				this.setResizable(false);
				this.setLocationRelativeTo(null);
				this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				this.setModal(true);
				this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Filter-List-icon32.png"));
				this.panelfb.setBorder(BorderFactory.createTitledBorder("Selecione Un Cuadrante Con Doble Click"));
				this.setTitle("Filtro De Cuadrantes");
				trsfiltro = new TableRowSorter(modelob); 
				tablab.setRowSorter(trsfiltro);
				this.panelfb.add(txtBuscarb).setBounds      (10 ,20 ,1004 , 20 );
				this.panelfb.add(scroll_tablab).setBounds   (10 ,40 ,1004 ,415 );
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
			        		    panelEnabledFalse();
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
					ObjTab.Obj_Filtro(tablab, txtBuscarb.getText(), columnasb,txtBuscarb);
				}
				public void keyTyped(KeyEvent arg0) {}
				public void keyPressed(KeyEvent arg0) {}		
			};
		}

	public static void main(String args[]){
		try{UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Cuadrantes().setVisible(true);
		}catch(Exception e){System.err.println("Error en Main: "+e.getMessage());
		}
	}
}