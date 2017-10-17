package Cat_Cuadrantes;

import java.awt.Color;
import java.awt.Component;
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

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
 
import Conexiones_SQL.Connexion;
import Obj_Cuadrantes.Obj_Cuadrantes;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_tabla;

@SuppressWarnings("serial")
public class Cat_Captura_De_Cuadrantes extends JFrame{
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	Connexion con = new Connexion();
	
	Obj_tabla ObjTab =new Obj_tabla();
	int columnas = 9,checkbox=-1;
	
	@SuppressWarnings("rawtypes")
	public Class[] baselunes (){
		Class[] types = new Class[columnas];
		for(int i = 0; i<columnas; i++){types[i]= java.lang.Object.class;}
		 return types;
	}
	public DefaultTableModel modelLunes = new DefaultTableModel(null, new String[]{"Orden","Folio","Actividad","Inicia","Termina","Respuestas","Observaciones","Evidencia","Tipo"}){
		 @SuppressWarnings("rawtypes")
			Class[] types = baselunes();
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
			public boolean isCellEditable(int fila, int columna){if(columna>4 && columna<7 ){return true;}else{ return false;}}
	};
	JTable tablaLunes = new JTable(modelLunes);
	public JScrollPane Scroll_TablaLunes = new JScrollPane(tablaLunes);
     
 	@SuppressWarnings("rawtypes")
 	public Class[] basemartes (){
 		Class[] types = new Class[columnas];
 		for(int i = 0; i<columnas; i++){types[i]= java.lang.Object.class;}
 		 return types;
 	}
 	public DefaultTableModel modelMartes = new DefaultTableModel(null, new String[]{"Orden","Folio","Actividad","Inicia","Termina","Respuestas","Observaciones","Evidencia","Tipo"}){
 		 @SuppressWarnings("rawtypes")
 			Class[] types = basemartes();
 			@SuppressWarnings({ "rawtypes", "unchecked" })
 			public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
 			public boolean isCellEditable(int fila, int columna){if(columna>4 && columna<7){return true;}else{ return false;}}
 	};
 	JTable tablaMartes = new JTable(modelMartes);
 	public JScrollPane Scroll_TablaMartes = new JScrollPane(tablaMartes);
     
   	@SuppressWarnings("rawtypes")
   	public Class[] basemiercoles (){
   		Class[] types = new Class[columnas];
   		for(int i = 0; i<columnas; i++){types[i]= java.lang.Object.class;}
   		 return types;
   	}
   	public DefaultTableModel modelMiercoles = new DefaultTableModel(null, new String[]{"Orden","Folio","Actividad","Inicia","Termina","Respuestas","Observaciones","Evidencia","Tipo"}){
   		 @SuppressWarnings("rawtypes")
   			Class[] types = basemiercoles();
   			@SuppressWarnings({ "rawtypes", "unchecked" })
   		public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
   		public boolean isCellEditable(int fila, int columna){if(columna>4 && columna<7){return true;}else{ return false;}}
   	};
   	JTable tablaMiercoles = new JTable(modelMiercoles);
   	public JScrollPane Scroll_TablaMiercoles = new JScrollPane(tablaMiercoles);
        
    @SuppressWarnings("rawtypes")
   	public Class[] baseJueves (){
       		Class[] types = new Class[columnas];
       		for(int i = 0; i<columnas; i++){types[i]= java.lang.Object.class;}
    return types;
   	}
    public DefaultTableModel modelJueves = new DefaultTableModel(null, new String[]{"Orden","Folio","Actividad","Inicia","Termina","Respuestas","Observaciones","Evidencia","Tipo"}){
     @SuppressWarnings("rawtypes")
       	Class[] types = baseJueves();
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
    public boolean isCellEditable(int fila, int columna){if(columna>4 && columna<7){return true;}else{ return false;}}
    };
    JTable tablaJueves = new JTable(modelJueves);
    public JScrollPane Scroll_TablaJueves = new JScrollPane(tablaJueves);
        
    @SuppressWarnings("rawtypes")
  	public Class[] baseViernes (){
        Class[] types = new Class[columnas];
        for(int i = 0; i<columnas; i++){types[i]= java.lang.Object.class;}
    return types;
    }
    public DefaultTableModel modelViernes = new DefaultTableModel(null, new String[]{"Orden","Folio","Actividad","Inicia","Termina","Respuestas","Observaciones","Evidencia","Tipo"}){
        @SuppressWarnings("rawtypes")
      Class[] types = baseViernes();
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
    public boolean isCellEditable(int fila, int columna){if(columna>4 && columna<7){return true;}else{ return false;}}
    };
    JTable tablaViernes = new JTable(modelViernes);
    public JScrollPane Scroll_TablaViernes = new JScrollPane(tablaViernes);
    
    @SuppressWarnings("rawtypes")
  	public Class[] baseSabado (){
        Class[] types = new Class[columnas];
        for(int i = 0; i<columnas; i++){types[i]= java.lang.Object.class;}
    return types;
    }
    public DefaultTableModel modelSabado = new DefaultTableModel(null, new String[]{"Orden","Folio","Actividad","Inicia","Termina","Respuestas","Observaciones","Evidencia","Tipo"}){
        @SuppressWarnings("rawtypes")
      Class[] types = baseSabado();
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
    public boolean isCellEditable(int fila, int columna){if(columna>4 && columna<7){return true;}else{ return false;}}
    };
    JTable tablaSabado = new JTable(modelSabado);
    public JScrollPane Scroll_TablaSabado = new JScrollPane(tablaSabado);
    
    @SuppressWarnings("rawtypes")
  	public Class[] baseDomingo (){
        Class[] types = new Class[columnas];
        for(int i = 0; i<columnas; i++){types[i]= java.lang.Object.class;}
    return types;
    }
    public DefaultTableModel modelDomingo = new DefaultTableModel(null, new String[]{"Orden","Folio","Actividad","Inicia","Termina","Respuestas","Observaciones","Evidencia","Tipo"}){
        @SuppressWarnings("rawtypes")
      Class[] types = baseDomingo();
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
    public boolean isCellEditable(int fila, int columna){if(columna>4 && columna<7){return true;}else{ return false;}}
    };
    JTable tablaDomingo = new JTable(modelDomingo);
    public JScrollPane Scroll_TablaDomingo = new JScrollPane(tablaDomingo);
	
	JTextField txtBuscarLunes    = new Componentes().text(new JCTextField()  , ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla Lunes<<<"    , 300, "String");
	JTextField txtBuscarMartes   = new Componentes().text(new JCTextField()  , ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla Martes<<<"   , 300, "String");
	JTextField txtBuscarMiercoles= new Componentes().text(new JCTextField()  , ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla Miercoles<<<", 300, "String");
	JTextField txtBuscarJueves   = new Componentes().text(new JCTextField()  , ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla Jueves<<<"   , 300, "String");
	JTextField txtBuscarViernes  = new Componentes().text(new JCTextField()  , ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla Viernes<<<"  , 300, "String");
	JTextField txtBuscarSabado   = new Componentes().text(new JCTextField()  , ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla Sabado<<<"   , 300, "String");
	JTextField txtBuscarDomingo  = new Componentes().text(new JCTextField()  , ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla Domingo<<<"  , 300, "String");
	
	JPasswordField txtFolio = new Componentes().textPassword(new JPasswordField(), "Clave", 100);
			
	JTextField txtColaborador    = new Componentes().text(new JCTextField()  , "Nombre Colaborador", 200                                     , "String");
	JTextField txtCuadrante      = new Componentes().text(new JCTextField()  , "Cuadrante", 200                                              , "String");
	JTextField txtEstablecimiento= new Componentes().text(new JCTextField()  , "Establecimiento", 200                                        , "String");
	JTextField txtDepartamento   = new Componentes().text(new JCTextField()  , "Departamento", 200                                           , "String");
	JTextField txtPuesto         = new Componentes().text(new JCTextField()  , "Puesto", 200                                                 , "String");
	JTextField txtReporta        = new Componentes().text(new JCTextField()  , "Puesto Al Que Reporta", 200                                  , "String");
	
	JTextArea  txaObjetivo       = new Componentes().textArea(new JTextArea(), "Objetivo"       ,500);
	JScrollPane scrollobjet      = new JScrollPane(txaObjetivo);
	
    JTextArea txaResponsabili    = new Componentes().textArea(new JTextArea(), "Responsabilidad",1000);
	JScrollPane scrollrespons    = new JScrollPane(txaResponsabili);
	
	JToolBar menu_toolbar = new JToolBar();
	JCButton btnGuardar   = new JCButton("Guardar"   ,"Guardar.png"                ,"Azul");
	JCButton btnDeshacer  = new JCButton("Deshacer"  ,"deshacer16.png"             ,"Azul");
	
	JToolBar toolbarLunes         = new JToolBar();
	JCButton btnAgregLunes        = new JCButton("Agregar Actividad Lunes","double-arrow-icone-3883-16.png"  ,"Azul" );
	JCButton btnEljLunes          = new JCButton("Eliminar","eliminar-bala-icono-7773-32.png","Azul" );
	
	JToolBar toolbarMartes        = new JToolBar();
	JCButton btnAgregMartes       = new JCButton("Agregar Actividad Martes","double-arrow-icone-3883-16.png"  ,"Azul" );
	JCButton btnEljMartes         = new JCButton("Eliminar","eliminar-bala-icono-7773-32.png","Azul" );
	
	JToolBar toolbarMiercoles     = new JToolBar();
	JCButton btnAgregMiercoles    = new JCButton("Agregar Actividad Miercoles","double-arrow-icone-3883-16.png"  ,"Azul" );
	JCButton btnEljMiercoles      = new JCButton("Eliminar","eliminar-bala-icono-7773-32.png","Azul" );
	
	JToolBar toolbarJueves        = new JToolBar();
	JCButton btnAgregJueves       = new JCButton("Agregar Actividad Jueves","double-arrow-icone-3883-16.png"  ,"Azul" );
	JCButton btnEljJueves         = new JCButton("Eliminar","eliminar-bala-icono-7773-32.png","Azul" );
	
	JToolBar toolbarViernes       = new JToolBar();
	JCButton btnAgregViernes      = new JCButton("Agregar Actividad Viernes","double-arrow-icone-3883-16.png"  ,"Azul" );
	JCButton btnEljViernes        = new JCButton("Eliminar","eliminar-bala-icono-7773-32.png","Azul" );
	
	JToolBar toolbarSabado        = new JToolBar();
	JCButton btnAgregSabado       = new JCButton("Agregar Actividad Sabado","double-arrow-icone-3883-16.png"  ,"Azul" );
	JCButton btnEljSabado         = new JCButton("Eliminar","eliminar-bala-icono-7773-32.png","Azul" );
	
	JToolBar toolbarDomingo       = new JToolBar();
	JCButton btnAgregDomingo      = new JCButton("Agregar Actividad Domingo","double-arrow-icone-3883-16.png"  ,"Azul" );
	JCButton btnEljDomingo        = new JCButton("Eliminar","eliminar-bala-icono-7773-32.png","Azul" );
	
	@SuppressWarnings("rawtypes")
	JComboBox cmbRespuesta = new JComboBox();
	
	JTabbedPane pestanas    = new JTabbedPane();
	JLayeredPane Principal  = new JLayeredPane(); 
	JLayeredPane pDomingo   = new JLayeredPane(); 
	JLayeredPane pLunes     = new JLayeredPane(); 
	JLayeredPane pMarte     = new JLayeredPane();
	JLayeredPane pMiercoles = new JLayeredPane(); 
	JLayeredPane pJueves    = new JLayeredPane(); 
	JLayeredPane pViernes   = new JLayeredPane(); 
	JLayeredPane pSabado    = new JLayeredPane(); 
	
	int  dia_de_la_semana;
	int  dia_de_descanso;
	String NuevoModifica ="";
	String FActividadesCargado ="";
	String[][] tablaprecargadaactividades;
	public Cat_Captura_De_Cuadrantes(){
		this.setSize(1024,685);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/favoritos-ver-boton-icono-8318-32.png"));
		this.setTitle("Cuadrantes");
		this.panel.setBorder(BorderFactory.createTitledBorder("Cuadrantes"));
		
		this.menu_toolbar.add(btnDeshacer );
		this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.add(btnGuardar  );
		this.menu_toolbar.setFloatable(false);
		
		this.pestanas.addTab("Principal"    ,Principal    );
		this.init_tablaPrincipal();
		
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
	
		 int x=15, y=20,width=120,height=20;

		this.panel.add(new JLabel("Folio:")).setBounds          (x     ,y      ,width      ,height );
		this.panel.add(txtFolio).setBounds                      (x+=30 ,y      ,width      ,height );
		this.panel.add(menu_toolbar).setBounds                  (x+=120,y      ,width*4    ,height );
		this.panel.add(pestanas).setBounds                      (x=15  ,y+=30  ,990        ,590    );
		
		this.panelEnabledFalse();
		
		this.btnGuardar.addActionListener   (guardar        );
		this.btnDeshacer.addActionListener  (deshacer       );
		this.txtFolio.addKeyListener        (busqueda_datos ); 
		cont.add(panel);
		Seleccionar_Respuesta(tablaLunes);
		Seleccionar_Respuesta(tablaMartes);
		Seleccionar_Respuesta(tablaMiercoles);
		Seleccionar_Respuesta(tablaJueves);
		Seleccionar_Respuesta(tablaViernes);
		Seleccionar_Respuesta(tablaSabado);
		Seleccionar_Respuesta(tablaDomingo);
		
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape"                );
        getRootPane().getActionMap().put("escape", new AbstractAction(){ public void actionPerformed(ActionEvent e){ btnDeshacer.doClick(); }  });
         
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_G,Event.CTRL_MASK),"guardar"        );
        getRootPane().getActionMap().put("guardar", new AbstractAction(){public void actionPerformed(ActionEvent e){btnGuardar.doClick();   }  });
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0), "guardar"                  );
        getRootPane().getActionMap().put("guardar", new AbstractAction(){public void actionPerformed(ActionEvent e) { btnGuardar.doClick(); }  });    
	}

	public void init_tablaPrincipal(){
		this.Principal.setBorder(BorderFactory.createTitledBorder("Principal"));
		this.Principal.setOpaque(true); 
		this.Principal.setBackground(new Color(Integer.parseInt("EBEBEB",16)));
		 int x=15, y=20,width=100,height=20,sep=75;
			this.Principal.add(new JLabel("Colaborador:")).setBounds    (x     ,y      ,width      ,height );
			this.Principal.add(txtColaborador).setBounds                (x+sep ,y      ,width*4    ,height );
			this.Principal.add(new JLabel("Cuadrante:")).setBounds      (x     ,y+=30  ,width      ,height );
			this.Principal.add(txtCuadrante).setBounds                  (x+sep ,y      ,width*4    ,height );
			this.Principal.add(new JLabel("Establecimiento:")).setBounds(x     ,y+=30  ,width      ,height );
			this.Principal.add(txtEstablecimiento).setBounds            (x+sep ,y      ,width*4    ,height );
			this.Principal.add(new JLabel("Departamento:")).setBounds   (x     ,y+=30  ,width      ,height );
			this.Principal.add(txtDepartamento).setBounds               (x+sep ,y      ,width*4    ,height );
			this.Principal.add(new JLabel("Puesto:")).setBounds         (x     ,y+=30  ,width      ,height );
			this.Principal.add(txtPuesto).setBounds                     (x+sep ,y      ,width*4    ,height );
			this.Principal.add(new JLabel("Reporta A:")).setBounds      (x     ,y+=30  ,width      ,height );
			this.Principal.add(txtReporta).setBounds                    (x+sep ,y      ,width*4    ,height );
			this.Principal.add(new JLabel("Objetivo Del Puesto:")).setBounds(x ,y+=30  ,width      ,height );
			this.Principal.add(scrollobjet).setBounds                   (x     ,y+=20  ,width*6    ,140    );
			this.Principal.add(new JLabel("Responsabilidades Del Puesto:")).setBounds(x,y+=150,180 ,height );
			this.Principal.add(scrollrespons).setBounds                 (x     ,y+=20  ,width*6    ,140    );
    }
	
	public void init_tablalunes(){
		ObjTab.tablas_dias_del_cuadrante(tablaLunes);
		this.pLunes.setBorder(BorderFactory.createTitledBorder("Lunes"));
		this.pLunes.setOpaque(true); 
		this.pLunes.setBackground(new Color(Integer.parseInt("EBEBEB",16)));
	    this.toolbarLunes.add(btnAgregLunes);
	    this.toolbarLunes.addSeparator(    );
	    this.toolbarLunes.addSeparator(    );
	    this.toolbarLunes.add(btnEljLunes  );
		this.toolbarLunes.setFloatable(false);
		 int x=10, y=15,width=965,height=20,sev=25;
		this.pLunes.add(toolbarLunes).setBounds               (x     ,y      ,width    ,height );
		this.pLunes.add(txtBuscarLunes).setBounds             (x     ,y+=sev ,width    ,height );
		this.pLunes.add(Scroll_TablaLunes).setBounds          (x     ,y+=20  ,width    ,495    ); 
		this.txtBuscarLunes.addKeyListener(opFiltro_lunes);
		this.btnEljLunes.addActionListener(new opEliminarfila(tablaLunes));
		this.btnAgregLunes.addActionListener(new opAgregar_Actividad(tablaLunes));
    }
	
	public void init_tablamartes(){
		ObjTab.tablas_dias_del_cuadrante(tablaMartes);
		this.pMarte.setBorder(BorderFactory.createTitledBorder("Martes"));
		this.pMarte.setOpaque(true); 
		this.pMarte.setBackground(new Color(Integer.parseInt("EBEBEB",16)));
	    this.toolbarMartes.add(btnAgregMartes);
	    this.toolbarMartes.addSeparator(     );
	    this.toolbarMartes.addSeparator(     );
	    this.toolbarMartes.add(btnEljMartes  );
		this.toolbarMartes.setFloatable(false);
		 int x=10, y=15,width=965,height=20,sev=25;
		this.pMarte.add(toolbarMartes).setBounds               (x     ,y      ,width    ,height );
		this.pMarte.add(txtBuscarMartes).setBounds             (x     ,y+=sev ,width    ,height );
		this.pMarte.add(Scroll_TablaMartes).setBounds          (x     ,y+=20  ,width    ,495    ); 
		this.txtBuscarMartes.addKeyListener (opFiltro_martes);
		this.btnEljMartes.addActionListener(new opEliminarfila(tablaMartes));
		this.btnAgregMartes.addActionListener(new opAgregar_Actividad(tablaMartes));
    }
	
	public void init_tablamiercoles(){
		ObjTab.tablas_dias_del_cuadrante(tablaMiercoles);
		this.pMiercoles.setBorder(BorderFactory.createTitledBorder("Miercoles"));
		this.pMiercoles.setOpaque(true); 
		this.pMiercoles.setBackground(new Color(Integer.parseInt("EBEBEB",16)));
	    this.toolbarMiercoles.add(btnAgregMiercoles);
	    this.toolbarMiercoles.addSeparator(     );
	    this.toolbarMiercoles.addSeparator(     );
	    this.toolbarMiercoles.add(btnEljMiercoles  );
		this.toolbarMiercoles.setFloatable(false);
		 int x=10, y=15,width=965,height=20,sev=25;
		this.pMiercoles.add(toolbarMiercoles).setBounds               (x     ,y      ,width    ,height );
		this.pMiercoles.add(txtBuscarMiercoles).setBounds             (x     ,y+=sev ,width    ,height );
		this.pMiercoles.add(Scroll_TablaMiercoles).setBounds          (x     ,y+=20  ,width    ,495    );
		this.txtBuscarMiercoles.addKeyListener (opFiltro_miercoles);
		this.btnEljMiercoles.addActionListener(new opEliminarfila(tablaMiercoles));
		this.btnAgregMiercoles.addActionListener(new opAgregar_Actividad(tablaMiercoles));
    }
	
	public void init_tablajueves(){
	ObjTab.tablas_dias_del_cuadrante(tablaJueves);
	this.pJueves.setBorder(BorderFactory.createTitledBorder("Jueves"));
	this.pJueves.setOpaque(true); 
	this.pJueves.setBackground(new Color(Integer.parseInt("EBEBEB",16)));
    this.toolbarJueves.add(btnAgregJueves);
    this.toolbarJueves.addSeparator(     );
    this.toolbarJueves.addSeparator(     );
    this.toolbarJueves.add(btnEljJueves  );
	this.toolbarJueves.setFloatable(false);
	 int x=10, y=15,width=965,height=20,sev=25;
	this.pJueves.add(toolbarJueves).setBounds               (x     ,y      ,width    ,height );
	this.pJueves.add(txtBuscarJueves).setBounds             (x     ,y+=sev ,width    ,height );
	this.pJueves.add(Scroll_TablaJueves).setBounds          (x     ,y+=20  ,width    ,495    );
	this.txtBuscarJueves.addKeyListener (opFiltro_jueves);
	this.btnEljJueves.addActionListener(new opEliminarfila(tablaJueves));
	this.btnAgregJueves.addActionListener(new opAgregar_Actividad(tablaJueves));
	}
	
	public void init_tablaviernes(){
		ObjTab.tablas_dias_del_cuadrante(tablaViernes);
		this.pViernes.setBorder(BorderFactory.createTitledBorder("Viernes"));
		this.pViernes.setOpaque(true); 
		this.pViernes.setBackground(new Color(Integer.parseInt("EBEBEB",16)));
	    this.toolbarViernes.add(btnAgregViernes);
	    this.toolbarViernes.addSeparator(      );
	    this.toolbarViernes.addSeparator(      );
	    this.toolbarViernes.add(btnEljViernes  );
		this.toolbarViernes.setFloatable(false);
		 int x=10, y=15,width=965,height=20,sev=25;
		this.pViernes.add(toolbarViernes).setBounds               (x     ,y      ,width    ,height );
		this.pViernes.add(txtBuscarViernes).setBounds             (x     ,y+=sev ,width    ,height );
		this.pViernes.add(Scroll_TablaViernes).setBounds          (x     ,y+=20  ,width    ,495    );
		this.txtBuscarViernes.addKeyListener (opFiltro_viernes);
		this.btnEljViernes.addActionListener(new opEliminarfila(tablaViernes));
		this.btnAgregViernes.addActionListener(new opAgregar_Actividad(tablaViernes));
		}
	
	public void init_tablasabado(){
		ObjTab.tablas_dias_del_cuadrante(tablaSabado);
		this.pSabado.setBorder(BorderFactory.createTitledBorder("Sabado"));
		this.pSabado.setOpaque(true); 
		this.pSabado.setBackground(new Color(Integer.parseInt("EBEBEB",16)));
	    this.toolbarSabado.add(btnAgregSabado);
	    this.toolbarSabado.addSeparator(     );
	    this.toolbarSabado.addSeparator(     );
	    this.toolbarSabado.add(btnEljSabado  );
		this.toolbarSabado.setFloatable(false);
		 int x=10, y=15,width=965,height=20,sev=25;
		this.pSabado.add(toolbarSabado).setBounds               (x     ,y      ,width    ,height );
		this.pSabado.add(txtBuscarSabado).setBounds             (x     ,y+=sev ,width    ,height );
		this.pSabado.add(Scroll_TablaSabado).setBounds          (x     ,y+=20  ,width    ,495    );
		this.txtBuscarSabado.addKeyListener (opFiltro_sabado);
		this.btnEljSabado.addActionListener(new opEliminarfila(tablaSabado));
		this.btnAgregSabado.addActionListener(new opAgregar_Actividad(tablaSabado));
		}

	public void init_tablaDomingo(){
		ObjTab.tablas_dias_del_cuadrante(tablaDomingo);
		this.pDomingo.setBorder(BorderFactory.createTitledBorder("Domingo"));
		this.pDomingo.setOpaque(true); 
		this.pDomingo.setBackground(new Color(Integer.parseInt("EBEBEB",16)));
	    this.toolbarDomingo.add(btnAgregDomingo);
	    this.toolbarDomingo.addSeparator(      );
	    this.toolbarDomingo.addSeparator(      );
	    this.toolbarDomingo.add(btnEljDomingo  );
		this.toolbarDomingo.setFloatable(false);
		 int x=10, y=15,width=965,height=20,sev=25;
		this.pDomingo.add(toolbarDomingo).setBounds               (x     ,y      ,width    ,height );
		this.pDomingo.add(txtBuscarDomingo).setBounds             (x     ,y+=sev ,width    ,height );
		this.pDomingo.add(Scroll_TablaDomingo).setBounds          (x     ,y+=20  ,width    ,495    );
		this.txtBuscarDomingo.addKeyListener (opFiltro_domingo);
		this.btnEljDomingo.addActionListener(new opEliminarfila(tablaDomingo));
		this.btnAgregDomingo.addActionListener(new opAgregar_Actividad(tablaDomingo));
		}
	
	   String[][] tablacompleta;

		private void Seleccionar_Respuesta(final JTable tbl) {
			    tbl.addMouseListener(new java.awt.event.MouseAdapter() {
					@SuppressWarnings("deprecation")
					public void mousePressed(MouseEvent e) {
			        	if(e.getClickCount()==1){
	                         if(tbl.getSelectedColumn()==5){
	     		        		int fila =tbl.getSelectedRow();
	    		    	    	if(tbl.getValueAt(fila,8).equals("Extra") ){
	    		    				JOptionPane.showMessageDialog(null, "Esta Actividad Es Extra \nLa Respuesta Es Siempre >>Si \nSolo Puedes Eliminarla", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
	    					        tbl.lostFocus(null, null);
	    					        tbl.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
	    					        tbl.getSelectionModel().clearSelection();
	    		    				tbl.setValueAt("Si", fila, 5);
	    		    				return;	
	    		    	    	}else{		
	                    	        tbl.getColumnModel().getColumn(5).setCellEditor(new javax.swing.DefaultCellEditor(cmbRespuesta));
	                    		    tbl.getColumn("Respuestas").setCellEditor(new CargaDatosDelCombo());
							       return;
	    		    	    	}
	                         } else{
							        tbl.lostFocus(null, null);
							        tbl.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
							        tbl.getSelectionModel().clearSelection();
	                        	 return;
	                         }	
			        	}else{
					        tbl.lostFocus(null, null);
					        tbl.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
					        tbl.getSelectionModel().clearSelection();
			        		return;
			        	}
			        }
			    });
			}
		
		private class CargaDatosDelCombo extends DefaultCellEditor{
	        @SuppressWarnings("rawtypes")
			public CargaDatosDelCombo(){
	        	super(new JComboBox());
	        }
	        @SuppressWarnings({ "rawtypes", "unchecked" })
			public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
	        	JComboBox combo = (JComboBox)getComponent();
	        	combo.removeAllItems();
				try {
					String respuestas="";
					if(table.getValueAt(row, 8).toString().equals("Extra")){
						respuestas="Si";
					}else{
					    for(int i=0;i<tablacompleta.length;i++){
						 if(table.getValueAt(row, 1).toString().equals(tablacompleta[i][1].toString())){
							respuestas=tablacompleta[i][16].toString();
						 }
					}	
					}
					String tipo_de_respuestas[] = respuestas.split("/");
					for(int i=0; i<tipo_de_respuestas.length; i++)  combo.addItem(tipo_de_respuestas[i]);
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
	            return combo;          
	        }
	    }
		
		public void cargar_datos_tablas(){
		  Object[]   vector = new Object[9];

		  txtColaborador.setText            (tablacompleta[0][15].toString());
		  txtCuadrante.setText              (tablacompleta[0][ 7].toString());
		  txtEstablecimiento.setText        (tablacompleta[0][ 8].toString());
		  txtDepartamento.setText           (tablacompleta[0][ 9].toString());
		  txtPuesto.setText                 (tablacompleta[0][10].toString());
		  txtReporta.setText                (tablacompleta[0][11].toString());
		  txaResponsabili.setText           (tablacompleta[0][12].toString());
		  txaObjetivo.setText               (tablacompleta[0][13].toString()); 
		  int Dia = 0;    
		
		for(int i=0;i<tablacompleta.length;i++){
			 Dia=Integer.valueOf(tablacompleta[i][5].toString());
			 
			if(Dia==1){
				for(int j=0;j<5;j++){
				vector[j] = tablacompleta[i][j].toString();
				vector[5] = "Respuestas";
				vector[6] = "";
				vector[7] = "";
				vector[8] = tablacompleta[i][26].toString();
				}
	   			modelLunes.addRow(vector);
			}
			
	        if(Dia==2){
	        	for(int j=0;j<5;j++){
	    			vector[j] = tablacompleta[i][j].toString();
	    			vector[5] = "Respuestas";
	    			vector[6] = "";
	    			vector[7] = "";
	    			vector[8] = tablacompleta[i][26].toString();
	    			}
	    			modelMartes.addRow(vector);
			}
	        
	        if(Dia==3){
	        	for(int j=0;j<5;j++){
	    			vector[j] = tablacompleta[i][j].toString();
	    			vector[5] = "Respuestas";
	    			vector[6] = "";
	    			vector[7] = "";
	    			vector[8] = tablacompleta[i][26].toString();
	    			}
	    			modelMiercoles.addRow(vector);
			}
	        
	        if(Dia==4){
	        	for(int j=0;j<5;j++){
	    			vector[j] = tablacompleta[i][j].toString();
	    			vector[5] = "Respuestas";
	    			vector[6] = "";
	    			vector[7] = "";
	    			vector[8] = tablacompleta[i][26].toString();
	    			}
	    			modelJueves.addRow(vector);
			}
	        
	        if(Dia==5){
	        	for(int j=0;j<5;j++){
	    			vector[j] = tablacompleta[i][j].toString();
	    			vector[5] = "Respuestas";
	    			vector[6] = "";
	    			vector[7] = "";
	    			vector[8] = tablacompleta[i][26].toString();
	    			}
	    			modelViernes.addRow(vector);
			}
	        
	        if(Dia==6){
	        	for(int j=0;j<5;j++){
	    			vector[j] = tablacompleta[i][j].toString();
	    			vector[5] = "Respuestas";
	    			vector[6] = "";
	    			vector[7] = "";
	    			vector[8] = tablacompleta[i][26].toString();
	    			}
	    			modelSabado.addRow(vector);
			}
	        
	        if(Dia==7){
	        	for(int j=0;j<5;j++){
	    			vector[j] = tablacompleta[i][j].toString();
	    			vector[5] = "Respuestas";
	    			vector[6] = "";
	    			vector[7] = "";
	    			vector[8] = tablacompleta[i][26].toString();
	    			}
	    			modelDomingo.addRow(vector);
			}
		 }	
		
		dia_de_la_semana=Integer.valueOf(tablacompleta[0][24].toString());
		dia_de_descanso=Integer.valueOf(tablacompleta[0][25].toString());
		inabilitarPestanas();
		}
		
		public void inabilitarPestanas(){
	       if(dia_de_la_semana==1){
				pestanas.setEnabledAt(0, true);
				pestanas.setEnabledAt(1, true);
				pestanas.setEnabledAt(2, false);
				pestanas.setEnabledAt(3, false);
				pestanas.setEnabledAt(4, false);
				pestanas.setEnabledAt(5, false);
				pestanas.setEnabledAt(6, false);
				pestanas.setEnabledAt(7, false);
		       };
	       
		   if(dia_de_la_semana==2 ){
				pestanas.setEnabledAt(0, true);
				pestanas.setEnabledAt(1, true);
				pestanas.setEnabledAt(2, true);
				pestanas.setEnabledAt(3, false);
				pestanas.setEnabledAt(4, false);
				pestanas.setEnabledAt(5, false);
				pestanas.setEnabledAt(6, false);
				pestanas.setEnabledAt(7, false);
	    	   };
		 	   
		   if(dia_de_la_semana==3 && dia_de_descanso==2){
				pestanas.setEnabledAt(0, true);
				pestanas.setEnabledAt(1, true);
				pestanas.setEnabledAt(2, true);
				pestanas.setEnabledAt(3, true);
				pestanas.setEnabledAt(4, false);
				pestanas.setEnabledAt(5, false);
				pestanas.setEnabledAt(6, false);
				pestanas.setEnabledAt(7, false);
		   	   };	 
		    	   
	       if(dia_de_la_semana==3 && !(dia_de_descanso==2)){
				pestanas.setEnabledAt(0, true);
				pestanas.setEnabledAt(1, false);
				pestanas.setEnabledAt(2, true);
				pestanas.setEnabledAt(3, true);
				pestanas.setEnabledAt(4, false);
				pestanas.setEnabledAt(5, false);
				pestanas.setEnabledAt(6, false);
				pestanas.setEnabledAt(7, false);
		   	   };	    	   
	        		
	       if(dia_de_la_semana==4 && dia_de_descanso==3){
				pestanas.setEnabledAt(0, true);
				pestanas.setEnabledAt(1, false);
				pestanas.setEnabledAt(2, true);
				pestanas.setEnabledAt(3, true);
				pestanas.setEnabledAt(4, true);
				pestanas.setEnabledAt(5, false);
				pestanas.setEnabledAt(6, false);
				pestanas.setEnabledAt(7, false);
			   };	 
			   	   
		   if(dia_de_la_semana==4 && !(dia_de_descanso==3)){
				pestanas.setEnabledAt(0, true);
				pestanas.setEnabledAt(1, false);
				pestanas.setEnabledAt(2, false);
				pestanas.setEnabledAt(3, true);
				pestanas.setEnabledAt(4, true);
				pestanas.setEnabledAt(5, false);
				pestanas.setEnabledAt(6, false);
				pestanas.setEnabledAt(7, false);
			   };
			   
		   if(dia_de_la_semana==5 && dia_de_descanso==4){
				pestanas.setEnabledAt(0, true);
				pestanas.setEnabledAt(1, false);
				pestanas.setEnabledAt(2, false);
				pestanas.setEnabledAt(3, true);
				pestanas.setEnabledAt(4, true);
				pestanas.setEnabledAt(5, true);
				pestanas.setEnabledAt(6, false);
				pestanas.setEnabledAt(7, false);
		       }
		   
		   if(dia_de_la_semana==5 && !(dia_de_descanso==4)){
				pestanas.setEnabledAt(0, true);
				pestanas.setEnabledAt(1, false);
				pestanas.setEnabledAt(2, false);
				pestanas.setEnabledAt(3, false);
				pestanas.setEnabledAt(4, true);
				pestanas.setEnabledAt(5, true);
				pestanas.setEnabledAt(6, false);
				pestanas.setEnabledAt(7, false);
		       }
			 
		   if(dia_de_la_semana==6 && (dia_de_descanso==5)){
				pestanas.setEnabledAt(0, true);
				pestanas.setEnabledAt(1, false);
				pestanas.setEnabledAt(2, false);
				pestanas.setEnabledAt(3, false);
				pestanas.setEnabledAt(4, true);
				pestanas.setEnabledAt(5, true);
				pestanas.setEnabledAt(6, true);
				pestanas.setEnabledAt(7, false);
		       }
		   
		   if(dia_de_la_semana==6 && !(dia_de_descanso==5)){
				pestanas.setEnabledAt(0, true);
				pestanas.setEnabledAt(1, false);
				pestanas.setEnabledAt(2, false);
				pestanas.setEnabledAt(3, false);
				pestanas.setEnabledAt(4, false);
				pestanas.setEnabledAt(5, true);
				pestanas.setEnabledAt(6, true);
				pestanas.setEnabledAt(7, false);
		   }

	       if(dia_de_la_semana==7 && (dia_de_descanso==5)){ 
				pestanas.setEnabledAt(0, true);
				pestanas.setEnabledAt(1, false);
				pestanas.setEnabledAt(2, false);
				pestanas.setEnabledAt(3, false);
				pestanas.setEnabledAt(4, false);
				pestanas.setEnabledAt(5, true);
				pestanas.setEnabledAt(6, true);
				pestanas.setEnabledAt(7, true);
	           };
	           
	        if(dia_de_la_semana==7 && !(dia_de_descanso==5)){ 
	   			pestanas.setEnabledAt(0, true);
	   			pestanas.setEnabledAt(1, false);
	   			pestanas.setEnabledAt(2, false);
	   			pestanas.setEnabledAt(3, false);
	   			pestanas.setEnabledAt(4, false);
	   			pestanas.setEnabledAt(5, false);
	   			pestanas.setEnabledAt(6, true);
	   			pestanas.setEnabledAt(7, true);
	         };     
		}
		   
	 private static boolean isNumeric(String cadena){
	     	try {
	     		Integer.parseInt(cadena);
	     		return true;
	     	} catch (NumberFormatException nfe){
	     		return false;
	     	}
	     }
	 
	  KeyListener busqueda_datos = new KeyListener(){
			public void keyReleased(KeyEvent evento) {
				if(evento.getKeyCode()==KeyEvent.VK_ENTER){
				   Obj_Cuadrantes cuadrante = new Obj_Cuadrantes();
				   @SuppressWarnings("deprecation")
				String clave_checador=txtFolio.getText().toString().toUpperCase().trim();
				   int posicionC = clave_checador.indexOf('C');
				   panelLimpiar();
				 	if(posicionC>0){
				 		if(isNumeric(clave_checador.substring(0, posicionC))){
				 			if(cuadrante.Validacion_captura_existe_cuadrante(clave_checador)){
				 				 tablacompleta= cuadrante.refrescar_tabla_captura_cuadrante(clave_checador);
								 cargar_datos_tablas();
								 panelEnabledTrue();
								  return;
							}else{
							   JOptionPane.showMessageDialog(null, "El Colaborador No tiene Ningun Cuadrante Asignado En Su Puesto \n Solicita A Mejora Continua Se Asigne Su Cuadrante", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));		    	
							   return;
							}	
				 		}else{
						  JOptionPane.showMessageDialog(null, "La Clave No Corresponde A Ningun Trabajador", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));		    	
				 		  return;
				 		}
				 	}else{
					  JOptionPane.showMessageDialog(null, "La Clave No Corresponde A Ningun Trabajador", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));		    	
					  return;
				 	}
				}	 	
			}
			public void keyTyped(KeyEvent arg0) {}
			public void keyPressed(KeyEvent arg0) {}		
		};		
		 
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
	
	ActionListener deshacer = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			panelLimpiar();
			panelEnabledFalse();
			NuevoModifica="";
		}
	};
	
	class opAgregar_Actividad implements ActionListener{   
		JTable tablaparametro;
	    public opAgregar_Actividad(final JTable tblp){
	    	tablaparametro = tblp;
	    }
	    public void actionPerformed(ActionEvent evt){
	        new  Cat_Actividades_Extras().setVisible(true);
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
	    	
	    	if(tablaparametro.getValueAt(tablaparametro.getSelectedRow(),8).equals("Cuadrante") ){
				JOptionPane.showMessageDialog(null, "Esta Fila Es Una Actividad De Tu Cuadrante , Solo Puedes Eliminar Actividades Extras", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
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

    ActionListener guardar = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				    String Mensaje =Valida();
					if(!Mensaje.equals("Para Poder Guardar Es Requerido Alimente:")){
						JOptionPane.showMessageDialog(null, Mensaje, "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
					}else{
//					Obj_Cuadrantes cuadrantes =new Obj_Cuadrantes();
//
//        			if(cuadrantes.Guardar()){
//    					JOptionPane.showMessageDialog(null,"El Registro Se Guardó Correctamente!","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/aplicara-el-dialogo-icono-6256-32.png"));
//    					btnDeshacer.doClick();
//    					return;
//    				}else{
    					JOptionPane.showMessageDialog(null,"Error Al Guardar Avise al Administrador del Sistema","Aviso",JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-icono-eliminar5252-64.png"));
    					return;
//    				}		
				}
		 }
	 };
	
	
//	 "Orden","Folio","Actividad","Inicia","Termina","Respuestas","Observaciones","Evidencia","Tipo" 
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
			String[][] tablas = new String[filas][9];	
			while(rengloneslunes > 0){
					tablas[i][0] = modelLunes.getValueAt(fila, 0)+"";
					tablas[i][1] = modelLunes.getValueAt(fila, 1)+"";
					tablas[i][2] = modelLunes.getValueAt(fila, 2)+"";
					tablas[i][3] = modelLunes.getValueAt(fila, 3)+"";
					tablas[i][4] = modelLunes.getValueAt(fila, 4)+"";
					tablas[i][5] = modelLunes.getValueAt(fila, 5)+"";
					tablas[i][6] = modelLunes.getValueAt(fila, 6)+"";
					tablas[i][7] = modelLunes.getValueAt(fila, 7)+"";
					tablas[i][8] = modelLunes.getValueAt(fila, 8)+"";
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
					tablas[i][5] = modelMartes.getValueAt(fila, 5)+"";
					tablas[i][6] = modelMartes.getValueAt(fila, 6)+"";
					tablas[i][7] = modelMartes.getValueAt(fila, 7)+"";
					tablas[i][8] = modelMartes.getValueAt(fila, 8)+"";
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
					tablas[i][5] = modelMiercoles.getValueAt(fila, 5)+"";
					tablas[i][6] = modelMiercoles.getValueAt(fila, 6)+"";
					tablas[i][7] = modelMiercoles.getValueAt(fila, 7)+"";
					tablas[i][8] = modelMiercoles.getValueAt(fila, 8)+"";
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
					tablas[i][5] = modelJueves.getValueAt(fila, 5)+"";
					tablas[i][6] = modelJueves.getValueAt(fila, 6)+"";
					tablas[i][7] = modelJueves.getValueAt(fila, 7)+"";
					tablas[i][8] = modelJueves.getValueAt(fila, 8)+"";
					tablas[i][9] = modelJueves.getValueAt(fila, 9)+"";
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
					tablas[i][5] = modelViernes.getValueAt(fila, 5)+"";
					tablas[i][6] = modelViernes.getValueAt(fila, 6)+"";
					tablas[i][7] = modelViernes.getValueAt(fila, 7)+"";
					tablas[i][8] = modelViernes.getValueAt(fila, 8)+"";
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
					tablas[i][5] = modelSabado.getValueAt(fila, 5)+"";
					tablas[i][6] = modelSabado.getValueAt(fila, 6)+"";
					tablas[i][7] = modelSabado.getValueAt(fila, 7)+"";
					tablas[i][8] = modelSabado.getValueAt(fila, 8)+"";
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
				tablas[i][5] = modelDomingo.getValueAt(fila, 5)+"";
				tablas[i][6] = modelDomingo.getValueAt(fila, 6)+"";
				tablas[i][7] = modelDomingo.getValueAt(fila, 7)+"";
				tablas[i][8] = modelDomingo.getValueAt(fila, 8)+"";
				i+=1;
				fila+=1;
			renglonesdomingo--;
		   }
			return tablas;
		}
		
	public String Valida(){
	    String Mensaje ="Para Poder Guardar Es Requerido Alimente:";
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
		txtColaborador.setText("");
		txtEstablecimiento.setText("");
		txtDepartamento.setText("");
		
		ObjTab.Obj_Filtro(tablaLunes    , "", columnas);
		ObjTab.Obj_Filtro(tablaMartes   , "", columnas);
		ObjTab.Obj_Filtro(tablaMiercoles, "", columnas);
		ObjTab.Obj_Filtro(tablaJueves   , "", columnas);
		ObjTab.Obj_Filtro(tablaViernes  , "", columnas);
		ObjTab.Obj_Filtro(tablaSabado   , "", columnas);
		ObjTab.Obj_Filtro(tablaDomingo  , "", columnas);
	}	
	
	public void panelEnabledFalse(){
		txtColaborador.setEditable(false);
		txtDepartamento.setEditable(false);
		txtEstablecimiento.setEditable(false);
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
		
        btnGuardar.setEnabled (false);
        btnAgregLunes.setEnabled(false);
        btnAgregMartes.setEnabled(false);
        btnAgregMiercoles.setEnabled(false);
        btnAgregJueves.setEnabled(false);
        btnAgregViernes.setEnabled(false);
        btnAgregSabado.setEnabled(false);
        btnAgregDomingo.setEnabled(false);
        btnEljLunes.setEnabled(false);
        btnEljMartes.setEnabled(false);
        btnEljMiercoles.setEnabled(false);
        btnEljJueves.setEnabled(false);
        btnEljViernes.setEnabled(false);
        btnEljSabado.setEnabled(false);
        btnEljDomingo.setEnabled(false);
	}		
	
	public void panelEnabledTrue(){	
		txtBuscarLunes.setEditable(true);
		txtBuscarMartes.setEditable(true);
		txtBuscarMiercoles.setEditable(true);
		txtBuscarJueves.setEditable(true);
		txtBuscarViernes.setEditable(true);
		txtBuscarSabado.setEditable(true);
		txtBuscarDomingo.setEditable(true);
		
        btnGuardar.setEnabled (true);
        btnAgregLunes.setEnabled(true);
        btnAgregMartes.setEnabled(true);
        btnAgregMiercoles.setEnabled(true);
        btnAgregJueves.setEnabled(true);
        btnAgregViernes.setEnabled(true);
        btnAgregSabado.setEnabled(true);
        btnAgregDomingo.setEnabled(true);
        btnEljLunes.setEnabled(true);
        btnEljMartes.setEnabled(true);
        btnEljMiercoles.setEnabled(true);
        btnEljJueves.setEnabled(true);
        btnEljViernes.setEnabled(true);
        btnEljSabado.setEnabled(true);
        btnEljDomingo.setEnabled(true);
	}
	
	//TODO Inicia Catalogo De Actividades Extras
	public class Cat_Actividades_Extras extends JDialog{
		Runtime R = Runtime.getRuntime();
		Container cont = getContentPane();
		JLayeredPane panel = new JLayeredPane();
		JTextArea txa_Resultado_Configuracion = new Componentes().textArea(new JTextArea(), "Detalle De La Actividad", 250);
		JScrollPane JPActividad = new JScrollPane(txa_Resultado_Configuracion);
		
		JButton btnAprovar = new JButton("Aceptar",new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
		
		JLabel lblGrupoOrdenActividad= new JLabel("");
		JCheckBox chbLunes     = new JCheckBox("Lunes");
		JCheckBox chbMartes    = new JCheckBox("Martes");
		JCheckBox chbMiercoles = new JCheckBox("Miercoles");
		JCheckBox chbJueves    = new JCheckBox("Jueves");
		JCheckBox chbViernes   = new JCheckBox("Viernes");
		JCheckBox chbSabado    = new JCheckBox("Sabado");
		JCheckBox chbDomingo   = new JCheckBox("Domingo"); 
		ButtonGroup Grupodias= new ButtonGroup();
		
		Border linea;
		String fecha_extra ="";
		
		public Cat_Actividades_Extras(){
			this.setSize(395, 330);
			this.setLocationRelativeTo(null);
			this.setResizable(false);
			this.setModal(true);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/reinicio-pelota-cute-icono-7443-64.png"));
			this.panel.setBorder(BorderFactory.createTitledBorder("Selecciona Los Datos Deseados De Respuesta"));
			this.linea = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
			this.lblGrupoOrdenActividad.setBorder(BorderFactory.createTitledBorder(linea,"Orden De La Actividad Por Hora"));
			this.setTitle("Actividades Extras De Una Planeacion");
			
			int x=15,y=20,width=70,height=20;
			panel.add(new JLabel("Detalle De La Actividad:")).setBounds            (x     ,y     ,width*3 ,height     );
			panel.add(JPActividad).setBounds                                       (x     ,y+=20 ,355     ,height*6+10);
			panel.add(chbLunes).setBounds                                          (x     ,y+=140,width   ,height     );
			panel.add(chbMartes).setBounds                                         (x+=90 ,y     ,width   ,height     );
			panel.add(chbMiercoles).setBounds                                      (x+=90 ,y     ,width   ,height     );
			panel.add(chbJueves).setBounds                                         (x+=90 ,y     ,width   ,height     );
			panel.add(chbViernes).setBounds                                        (x-=180,y+=25 ,width   ,height     );
			panel.add(chbSabado).setBounds                                         (x+=90 ,y     ,width   ,height     );
			panel.add(chbDomingo).setBounds                                        (x+=90 ,y     ,width   ,height     );
			panel.add(btnAprovar).setBounds                                        (x-=160,y+=35 ,width*2 ,height*2   );
			
			       if(dia_de_la_semana==1){
				       chbLunes.setSelected   (true );
				       chbMartes.setEnabled   (false);
				   	   chbMiercoles.setEnabled(false);
				   	   chbJueves.setEnabled   (false);
				   	   chbViernes.setEnabled  (false);
				   	   chbSabado.setEnabled   (false);
				   	   chbDomingo.setEnabled  (false);
				       };
			       
				   if(dia_de_la_semana==2 ){
					   chbLunes.setEnabled    (true );
					   chbMartes.setSelected  (true );
					   chbMiercoles.setEnabled(false);
					   chbJueves.setEnabled   (false);
					   chbViernes.setEnabled  (false);
					   chbSabado.setEnabled   (false);
					   chbDomingo.setEnabled  (false);
			    	   };
				   
				   if(dia_de_la_semana==3 && dia_de_descanso==2){
		        	   chbLunes.setEnabled    (true );
		   			   chbMartes.setEnabled   (true );
		   			   chbMiercoles.setSelected(true);
		   			   chbJueves.setEnabled   (false);
		   			   chbViernes.setEnabled  (false);
		   			   chbSabado.setEnabled   (false);
		   			   chbDomingo.setEnabled  (false);
				   	   };	 
				    	   
			       if(dia_de_la_semana==3 && !(dia_de_descanso==2)){
				   	   chbLunes.setEnabled    (false);
					   chbMartes.setEnabled   (true );
				       chbMiercoles.setSelected(true);
				   	   chbJueves.setEnabled   (false);
				   	   chbViernes.setEnabled  (false);
				   	   chbSabado.setEnabled   (false);
				   	   chbDomingo.setEnabled  (false);
				   	   };	    	   
		            		
			       if(dia_de_la_semana==4 && dia_de_descanso==3){
		        	   chbLunes.setEnabled    (false);
		   			   chbMartes.setEnabled   (true );
		   			   chbMiercoles.setEnabled(true );
		   			   chbJueves.setSelected  (true );
		   			   chbViernes.setEnabled  (false);
		   			   chbSabado.setEnabled   (false);
		   		       chbDomingo.setEnabled  (false);
					   };	 
					   	   
				   if(dia_de_la_semana==4 && !(dia_de_descanso==3)){
				   	   chbLunes.setEnabled    (false);
				       chbMartes.setEnabled   (false);
				   	   chbMiercoles.setEnabled(true );
				   	   chbJueves.setSelected   (true );
				   	   chbViernes.setEnabled  (false);
				   	   chbSabado.setEnabled   (false);
				   	   chbDomingo.setEnabled  (false);
					   };
					   
				   if(dia_de_la_semana==5 && dia_de_descanso==4){
		        	   chbLunes.setEnabled    (false);
		   			   chbMartes.setEnabled   (false);
		   			   chbMiercoles.setEnabled(true );
		   			   chbJueves.setEnabled   (true );
		   			   chbViernes.setSelected  (true );
		   			   chbSabado.setEnabled   (false);
		   			   chbDomingo.setEnabled  (false);
				       }
				   
				   if(dia_de_la_semana==5 && !(dia_de_descanso==4)){
		        	   chbLunes.setEnabled    (false);
		   			   chbMartes.setEnabled   (false);
		   			   chbMiercoles.setEnabled(false);
		   			   chbJueves.setEnabled   (true );
		   			   chbViernes.setSelected  (true );
		   			   chbSabado.setEnabled  (false);
		   			   chbDomingo.setEnabled  (false);
				       }
					 
				   if(dia_de_la_semana==6 && (dia_de_descanso==5)){
		        	   chbLunes.setEnabled    (false);
		   			   chbMartes.setEnabled   (false);
		   			   chbMiercoles.setEnabled(false);
		   			   chbJueves.setEnabled   (true );
		   			   chbViernes.setEnabled  (true );
		   			   chbSabado.setSelected   (true );
		   			   chbDomingo.setEnabled  (false);
				       }
				   
				   if(dia_de_la_semana==6 && !(dia_de_descanso==5)){
		        	   chbLunes.setEnabled    (false);
		   			   chbMartes.setEnabled   (false);
		   			   chbMiercoles.setEnabled(false);
		   			   chbJueves.setEnabled   (false);
		   			   chbViernes.setEnabled  (true );
		   			   chbSabado.setSelected   (true );
		   			   chbDomingo.setEnabled  (false);
				       }
				   
		           if(dia_de_la_semana==7){ 
		        	   chbLunes.setEnabled    (false );
		   			   chbMartes.setEnabled   (false);
		   			   chbMiercoles.setEnabled(false);
		   			   chbJueves.setEnabled   (false);
		   			   chbViernes.setEnabled  (false);
		   			   chbSabado.setEnabled   (false);
		   			   chbDomingo.setSelected  (true);
			           };
				
				Grupodias.add(chbLunes);
				Grupodias.add(chbMartes);
				Grupodias.add(chbMiercoles);
				Grupodias.add(chbJueves);
				Grupodias.add(chbViernes);
				Grupodias.add(chbSabado);
				Grupodias.add(chbDomingo);

			
			txa_Resultado_Configuracion.setLineWrap(true); 
			txa_Resultado_Configuracion.setWrapStyleWord(true);
			
			btnAprovar.addActionListener(aceptar);
	        
	        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A,Event.CTRL_MASK),"guardar");
	             getRootPane().getActionMap().put("guardar", new AbstractAction(){
	                 public void actionPerformed(ActionEvent e)
	                 {                 	    btnAprovar.doClick();           	    }
	            });
	 	    this.addWindowListener(new WindowAdapter() {
	 	                     public void windowOpened( WindowEvent e ){
	 	                    	 txa_Resultado_Configuracion.requestFocus();
	 	                  }
	 	             });
	 	    cont.add(panel);
		}
		
	 ActionListener aceptar = new ActionListener(){
				public void actionPerformed(ActionEvent e){
//		"Orden","Folio","Actividad","Inicia","Termina","Respuestas","Observaciones","Evidencia","Tipo"
		 Object[] vector = new Object[9];
		  vector[0] = 0;
		  vector[1] = 0; 
		  vector[2] = txa_Resultado_Configuracion.getText().toString().trim(); 
		  vector[3] =  "00:00";
		  vector[4] =  "00:00";
		  vector[5] =  "Si";
		  vector[6] =  "";
		  vector[7] =  "";
		  vector[8] =  "Extra";
		
		if(chbLunes.isSelected()){
			  modelLunes.addRow(vector);
		}
		if(chbMartes.isSelected()){
			  modelMartes.addRow(vector);
		}
		if(chbMiercoles.isSelected()){
			  modelMiercoles.addRow(vector);
		}
		if(chbJueves.isSelected()){
			  modelJueves.addRow(vector);
		}
		if(chbViernes.isSelected()){
			  modelViernes.addRow(vector);
		}
		if(chbSabado.isSelected()){
			  modelSabado.addRow(vector);
		}
		if(chbDomingo.isSelected()){
			  modelDomingo.addRow(vector);
		}
		dispose();
		return;
	    }
	  };
	
	}
///////////////termina filtro actividades	
		
	public static void main(String args[]){
		try{UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Captura_De_Cuadrantes().setVisible(true);
		}catch(Exception e){System.err.println("Error en Main: "+e.getMessage());
		}
	}
}