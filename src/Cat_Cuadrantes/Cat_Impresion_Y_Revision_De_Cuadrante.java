package Cat_Cuadrantes;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
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
import javax.swing.table.DefaultTableModel;
 
import Conexiones_SQL.Connexion;
import Conexiones_SQL.Generacion_Reportes;
import Obj_Cuadrantes.Obj_Cuadrantes;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_tabla;

@SuppressWarnings("serial")
public class Cat_Impresion_Y_Revision_De_Cuadrante extends JFrame{
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	Container contfiltro = getContentPane();
	JLayeredPane panelfiltro = new JLayeredPane();
	
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
			public boolean isCellEditable(int fila, int columna){return false;}
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
	
	JTextField txtBuscarLunes    = new Componentes().textfiltro(new JCTextField()  , ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla Lunes<<<"    , 300, "String",tablaLunes     ,columnas);
	JTextField txtBuscarMartes   = new Componentes().textfiltro(new JCTextField()  , ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla Martes<<<"   , 300, "String",tablaMartes    ,columnas);
	JTextField txtBuscarMiercoles= new Componentes().textfiltro(new JCTextField()  , ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla Miercoles<<<", 300, "String",tablaMiercoles ,columnas);
	JTextField txtBuscarJueves   = new Componentes().textfiltro(new JCTextField()  , ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla Jueves<<<"   , 300, "String",tablaJueves    ,columnas);
	JTextField txtBuscarViernes  = new Componentes().textfiltro(new JCTextField()  , ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla Viernes<<<"  , 300, "String",tablaViernes   ,columnas);
	JTextField txtBuscarSabado   = new Componentes().textfiltro(new JCTextField()  , ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla Sabado<<<"   , 300, "String",tablaSabado    ,columnas);
	JTextField txtBuscarDomingo  = new Componentes().textfiltro(new JCTextField()  , ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla Domingo<<<"  , 300, "String",tablaDomingo   ,columnas);
	
	JPasswordField txtFolio = new Componentes().textPassword(new JPasswordField(), "Clave", 100);
			
	JTextField txtColaborador    = new Componentes().text(new JCTextField()  , "Nombre Colaborador", 200                                     , "String");
	JTextField txtfolioColaborado= new Componentes().text(new JCTextField()  , "Folio Colaborador", 50                                       , "String");
	JTextField txtCuadrante      = new Componentes().text(new JCTextField()  , "Cuadrante", 200                                              , "String");
	JTextField txtfolioCuadrante = new Componentes().text(new JCTextField()  , "Folio Cuadrante", 200                                        , "String");
	JTextField txtEstablecimiento= new Componentes().text(new JCTextField()  , "Establecimiento", 200                                        , "String");
	JTextField txtDepartamento   = new Componentes().text(new JCTextField()  , "Departamento", 200                                           , "String");
	JTextField txtPuesto         = new Componentes().text(new JCTextField()  , "Puesto", 200                                                 , "String");
	JTextField txtReporta        = new Componentes().text(new JCTextField()  , "Puesto Al Que Reporta", 200                                  , "String");
	
	JTextArea  txaObjetivo       = new Componentes().textArea(new JTextArea(), "Objetivo"       ,500);
	JScrollPane scrollobjet      = new JScrollPane(txaObjetivo);
	
    JTextArea txaResponsabili    = new Componentes().textArea(new JTextArea(), "Responsabilidad",1000);
	JScrollPane scrollrespons    = new JScrollPane(txaResponsabili);
	
	JToolBar menu_toolbar       = new JToolBar();
	JCButton btnDeshacer        = new JCButton("Deshacer"                          ,"deshacer16.png"                                                   ,"Azul");
	JCButton btnMenuPrincipal   = new JCButton("Volver Al Menu Principal","folder-home-home-icone-5663-16.png"                                         ,"Azul");
 	JCButton btnGenerar         = new JCButton("Cuadrante Personal Actividad"      ,"Lista.png"                                                        ,"Azul"); 
 	JCButton btnGenerarDetalle  = new JCButton("Cuadrante Personal Descripcion"    ,"Lista.png"                                                        ,"Azul"); 
	
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
	public Cat_Impresion_Y_Revision_De_Cuadrante(){
		this.setSize(255,100);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/todo-list-control-icone-3792-48.png"));
		this.setTitle("Captura De Cuadrantes");
		this.panelfiltro.setBorder(BorderFactory.createTitledBorder("Pase El Gafete Para Ver El Cuadrante"));
  
		 int x=15, y=30,height=20;
		this.panelfiltro.add(txtFolio).setBounds                                         (x   ,y  ,200        ,height );
		
		this.txtFolio.addKeyListener        (busqueda_datos_por_gafete_abre_catalogo ); 
		contfiltro.add(panelfiltro);
	}
	
	 Obj_Cuadrantes cuadrante = new Obj_Cuadrantes();
	public void constructor_cuadrante_gafete (String clave_checador, String tipo){
		this.setSize(1024,685);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/todo-list-control-icone-3792-48.png"));
		this.setTitle("Impresion y Revision De Cuadrante Personal");
		this.panel.setBorder(BorderFactory.createTitledBorder("Selecione La Pestana Del Dia Que Desea Ver El Cuadrante"));		
		
		this.pestanas.addTab("Principal"    ,Principal    );
		this.Principal.setBorder(BorderFactory.createTitledBorder("Principal"));
		this.Principal.setOpaque(true); 
		this.Principal.setBackground(new Color(Integer.parseInt("EBEBEB",16)));
		
		 int x=15, y=20,width=120,height=20,sep=75;
		 
		   if(tipo.equals("colaborador")){
			this.menu_toolbar.add(btnDeshacer     );
			this.menu_toolbar.addSeparator(       );
			this.menu_toolbar.addSeparator(       );
			this.menu_toolbar.add(btnMenuPrincipal);
			this.menu_toolbar.addSeparator(       );
			this.menu_toolbar.addSeparator(       );
			this.menu_toolbar.add(btnGenerar      );
			this.menu_toolbar.addSeparator(       );
			this.menu_toolbar.addSeparator(       );
			this.menu_toolbar.add(btnGenerarDetalle);
			this.menu_toolbar.setFloatable(false);
			
			this.panel.add(menu_toolbar).setBounds                      (x     ,y      ,width*7    ,height );
			getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape"                );
	        getRootPane().getActionMap().put("escape", new AbstractAction(){ public void actionPerformed(ActionEvent e){ btnDeshacer.doClick(); }  });
		   }
		   
			this.panel.add(pestanas).setBounds                          (x=15  ,y+=30  ,990        ,590    );
			this.Principal.add(new JLabel("Colaborador:")).setBounds    (x     ,y      ,width=100  ,height );
			this.Principal.add(txtColaborador).setBounds                (x+sep ,y      ,width*5-50 ,height );
			this.Principal.add(txtfolioColaborado).setBounds            (x+525 ,y      ,50         ,height );			
			this.Principal.add(new JLabel("Cuadrante:")).setBounds      (x     ,y+=30  ,width      ,height );
			this.Principal.add(txtCuadrante).setBounds                  (x+sep ,y      ,width*5-50 ,height );
			this.Principal.add(txtfolioCuadrante).setBounds             (x+525 ,y      ,50         ,height );
			this.Principal.add(new JLabel("Establecimiento:")).setBounds(x     ,y+=30  ,width      ,height );
			this.Principal.add(txtEstablecimiento).setBounds            (x+sep ,y      ,width*5    ,height );
			this.Principal.add(new JLabel("Departamento:")).setBounds   (x     ,y+=30  ,width      ,height );
			this.Principal.add(txtDepartamento).setBounds               (x+sep ,y      ,width*5    ,height );
			this.Principal.add(new JLabel("Puesto:")).setBounds         (x     ,y+=30  ,width      ,height );
			this.Principal.add(txtPuesto).setBounds                     (x+sep ,y      ,width*5    ,height );
			this.Principal.add(new JLabel("Reporta A:")).setBounds      (x     ,y+=30  ,width      ,height );
			this.Principal.add(txtReporta).setBounds                    (x+sep ,y      ,width*5    ,height );
			this.Principal.add(new JLabel("Objetivo Del Puesto:")).setBounds(x ,y+=30  ,width      ,height );
			this.Principal.add(scrollobjet).setBounds                   (x     ,y+=20  ,width*6    ,140    );
			this.Principal.add(new JLabel("Responsabilidades Del Puesto:")).setBounds(x,y+=150,180 ,height );
			this.Principal.add(scrollrespons).setBounds                 (x     ,y+=20  ,width*6    ,140    );

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
		this.panelEnabledFalse();
		
		this.btnDeshacer.addActionListener  (deshacer                 );
		this.txtFolio.addKeyListener        (busqueda_datos_por_gafete); 
		this.btnMenuPrincipal.addActionListener(volver_al_menu_principal);
        this.btnGenerar.addActionListener(opGenerar);
        this.btnGenerarDetalle.addActionListener(opGenerar);
		cont.add(panel);
		Seleccionar_Respuesta(tablaLunes);
		Seleccionar_Respuesta(tablaMartes);
		Seleccionar_Respuesta(tablaMiercoles);
		Seleccionar_Respuesta(tablaJueves);
		Seleccionar_Respuesta(tablaViernes);
		Seleccionar_Respuesta(tablaSabado);
		Seleccionar_Respuesta(tablaDomingo);
        
		 tablacompleta= cuadrante.refrescar_tabla_captura_cuadrante(clave_checador);
		 cargar_datos_tablas();
		 panelEnabledTrue();
	}
	
	ActionListener opGenerar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String basedatos="2.26";
			String vista_previa_reporte="si";
			int vista_previa_de_ventana=0;
			String reporte ="";
			String comando="exec cuadrantes_reporte_por_folio_de_colaborador "+txtfolioColaborado.getText()+","+0;

			if(e.getActionCommand().equals("Cuadrante Personal Actividad")) {		
			  reporte = "Obj_Reporte_De_Cuadrante_Por_Persona_Por_Dia.jrxml";
			}else {
			  reporte = "Obj_Reporte_De_Cuadrante_Por_Persona_Por_Dia_Detalle_Descripcion.jrxml";
			}
		     new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
		     btnMenuPrincipal.doClick();
		      return;
		     }
	};	
	
	public void init_tablalunes(){
		ObjTab.tabla_mascara(tablaLunes,-1,-1);
		this.pLunes.setBorder(BorderFactory.createTitledBorder("Lunes"));
		this.pLunes.setOpaque(true); 
		this.pLunes.setBackground(new Color(Integer.parseInt("EBEBEB",16)));
		 int x=10, y=15,width=965,height=20,sev=25;
		this.pLunes.add(txtBuscarLunes).setBounds             (x     ,y+=sev ,width    ,height );
		this.pLunes.add(Scroll_TablaLunes).setBounds          (x     ,y+=20  ,width    ,495    ); 
    }
	
	public void init_tablamartes(){
		ObjTab.tabla_mascara(tablaMartes,-1,-1);
		this.pMarte.setBorder(BorderFactory.createTitledBorder("Martes"));
		this.pMarte.setOpaque(true); 
		this.pMarte.setBackground(new Color(Integer.parseInt("EBEBEB",16)));
		 int x=10, y=15,width=965,height=20,sev=25;
		this.pMarte.add(txtBuscarMartes).setBounds             (x     ,y+=sev ,width    ,height );
		this.pMarte.add(Scroll_TablaMartes).setBounds          (x     ,y+=20  ,width    ,495    ); 
    }
	
	public void init_tablamiercoles(){
		ObjTab.tabla_mascara(tablaMiercoles,-1,-1);
		this.pMiercoles.setBorder(BorderFactory.createTitledBorder("Miercoles"));
		this.pMiercoles.setOpaque(true); 
		this.pMiercoles.setBackground(new Color(Integer.parseInt("EBEBEB",16)));
		 int x=10, y=15,width=965,height=20,sev=25;
		this.pMiercoles.add(txtBuscarMiercoles).setBounds             (x     ,y+=sev ,width    ,height );
		this.pMiercoles.add(Scroll_TablaMiercoles).setBounds          (x     ,y+=20  ,width    ,495    );
    }
	
	public void init_tablajueves(){
	ObjTab.tabla_mascara(tablaJueves,-1,-1);
	this.pJueves.setBorder(BorderFactory.createTitledBorder("Jueves"));
	this.pJueves.setOpaque(true); 
	this.pJueves.setBackground(new Color(Integer.parseInt("EBEBEB",16)));
	 int x=10, y=15,width=965,height=20,sev=25;
	this.pJueves.add(txtBuscarJueves).setBounds             (x     ,y+=sev ,width    ,height );
	this.pJueves.add(Scroll_TablaJueves).setBounds          (x     ,y+=20  ,width    ,495    );
	}
	
	public void init_tablaviernes(){
		ObjTab.tabla_mascara(tablaViernes,-1,-1);
		this.pViernes.setBorder(BorderFactory.createTitledBorder("Viernes"));
		this.pViernes.setOpaque(true); 
		this.pViernes.setBackground(new Color(Integer.parseInt("EBEBEB",16)));
		 int x=10, y=15,width=965,height=20,sev=25;
		this.pViernes.add(txtBuscarViernes).setBounds             (x     ,y+=sev ,width    ,height );
		this.pViernes.add(Scroll_TablaViernes).setBounds          (x     ,y+=20  ,width    ,495    );
		}
	
	public void init_tablasabado(){
		ObjTab.tabla_mascara(tablaSabado,-1,-1);
		this.pSabado.setBorder(BorderFactory.createTitledBorder("Sabado"));
		this.pSabado.setOpaque(true); 
		this.pSabado.setBackground(new Color(Integer.parseInt("EBEBEB",16)));
		 int x=10, y=15,width=965,height=20,sev=25;
		this.pSabado.add(txtBuscarSabado).setBounds             (x     ,y+=sev ,width    ,height );
		this.pSabado.add(Scroll_TablaSabado).setBounds          (x     ,y+=20  ,width    ,495    );
		}

	public void init_tablaDomingo(){
		ObjTab.tabla_mascara(tablaDomingo,-1,-1);
		this.pDomingo.setBorder(BorderFactory.createTitledBorder("Domingo"));
		this.pDomingo.setOpaque(true); 
		this.pDomingo.setBackground(new Color(Integer.parseInt("EBEBEB",16)));
		 int x=10, y=15,width=965,height=20,sev=25;
		this.pDomingo.add(txtBuscarDomingo).setBounds             (x     ,y+=sev ,width    ,height );
		this.pDomingo.add(Scroll_TablaDomingo).setBounds          (x     ,y+=20  ,width    ,495    );
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
		
		ActionListener volver_al_menu_principal = new ActionListener(){
			public void actionPerformed(ActionEvent e){
					dispose();
					new Cat_Impresion_Y_Revision_De_Cuadrante().setVisible(true);
			}
		};
		
		 KeyListener busqueda_datos_por_gafete_abre_catalogo = new KeyListener(){
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
					 				panelfiltro.removeAll();
					 				contfiltro.removeAll();
					 				txtFolio.removeKeyListener(busqueda_datos_por_gafete_abre_catalogo);
					 				constructor_cuadrante_gafete(clave_checador, "colaborador");
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
			
			 KeyListener busqueda_datos_por_gafete= new KeyListener(){
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
		  txtfolioColaborado.setText        (tablacompleta[0][14].toString());
		  txtCuadrante.setText              (tablacompleta[0][ 7].toString());
		  txtfolioCuadrante.setText         (tablacompleta[0][ 6].toString());
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
				vector[5] = tablacompleta[i][27].toString();
				vector[6] = tablacompleta[i][28].toString();
				vector[7] = "";
				vector[8] = tablacompleta[i][26].toString();
				}
	   			modelLunes.addRow(vector);
			}
			
	        if(Dia==2){
	        	for(int j=0;j<5;j++){
	    			vector[j] = tablacompleta[i][j].toString();
	    			vector[5] = tablacompleta[i][27].toString();
	    			vector[6] = tablacompleta[i][28].toString();
	    			vector[7] = "";
	    			vector[8] = tablacompleta[i][26].toString();
	    			}
	    			modelMartes.addRow(vector);
			}
	        
	        if(Dia==3){
	        	for(int j=0;j<5;j++){
	    			vector[j] = tablacompleta[i][j].toString();
	    			vector[5] = tablacompleta[i][27].toString();
	    			vector[6] = tablacompleta[i][28].toString();
	    			vector[7] = "";
	    			vector[8] = tablacompleta[i][26].toString();
	    			}
	    			modelMiercoles.addRow(vector);
			}
	        
	        if(Dia==4){
	        	for(int j=0;j<5;j++){
	    			vector[j] = tablacompleta[i][j].toString();
	    			vector[5] = tablacompleta[i][27].toString();
	    			vector[6] = tablacompleta[i][28].toString();
	    			vector[7] = "";
	    			vector[8] = tablacompleta[i][26].toString();
	    			}
	    			modelJueves.addRow(vector);
			}
	        
	        if(Dia==5){
	        	for(int j=0;j<5;j++){
	    			vector[j] = tablacompleta[i][j].toString();
	    			vector[5] = tablacompleta[i][27].toString();
	    			vector[6] = tablacompleta[i][28].toString();
	    			vector[7] = "";
	    			vector[8] = tablacompleta[i][26].toString();
	    			}
	    			modelViernes.addRow(vector);
			}
	        
	        if(Dia==6){
	        	for(int j=0;j<5;j++){
	    			vector[j] = tablacompleta[i][j].toString();
	    			vector[5] = tablacompleta[i][27].toString();
	    			vector[6] = tablacompleta[i][28].toString();
	    			vector[7] = "";
	    			vector[8] = tablacompleta[i][26].toString();
	    			}
	    			modelSabado.addRow(vector);
			}
	        
	        if(Dia==7){
	        	for(int j=0;j<5;j++){
	    			vector[j] = tablacompleta[i][j].toString();
	    			vector[5] = tablacompleta[i][27].toString();
	    			vector[6] = tablacompleta[i][28].toString();
	    			vector[7] = "";
	    			vector[8] = tablacompleta[i][26].toString();
	    			}
	    			modelDomingo.addRow(vector);
			}
		 }	
		
		dia_de_la_semana=Integer.valueOf(tablacompleta[0][24].toString());
		dia_de_descanso=Integer.valueOf(tablacompleta[0][25].toString());
		}
	
		   
	 private static boolean isNumeric(String cadena){
	     	try {
	     		Integer.parseInt(cadena);
	     		return true;
	     	} catch (NumberFormatException nfe){
	     		return false;
	     	}
	     }
	 
	ActionListener deshacer = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			panelLimpiar();
			panelEnabledFalse();
			NuevoModifica="";
		}
	};
	
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
		txtfolioCuadrante.setText("");
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
		txtfolioColaborado.setText("");
		txtEstablecimiento.setText("");
		txtDepartamento.setText("");
		
		ObjTab.Obj_Filtro(tablaLunes    , "", columnas,txtBuscarLunes);
		ObjTab.Obj_Filtro(tablaMartes   , "", columnas,txtBuscarMartes);
		ObjTab.Obj_Filtro(tablaMiercoles, "", columnas,txtBuscarMiercoles);
		ObjTab.Obj_Filtro(tablaJueves   , "", columnas,txtBuscarJueves);
		ObjTab.Obj_Filtro(tablaViernes  , "", columnas,txtBuscarViernes);
		ObjTab.Obj_Filtro(tablaSabado   , "", columnas,txtBuscarSabado);
		ObjTab.Obj_Filtro(tablaDomingo  , "", columnas,txtBuscarDomingo);
	}	
	
	public void panelEnabledFalse(){
		txtColaborador.setEditable(false);
		txtfolioColaborado.setEditable(false);
		txtDepartamento.setEditable(false);
		txtEstablecimiento.setEditable(false);
		txtCuadrante.setEditable(false);
		txtfolioCuadrante.setEditable(false);
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
	}		
	
	public void panelEnabledTrue(){	
		txtBuscarLunes.setEditable(true);
		txtBuscarMartes.setEditable(true);
		txtBuscarMiercoles.setEditable(true);
		txtBuscarJueves.setEditable(true);
		txtBuscarViernes.setEditable(true);
		txtBuscarSabado.setEditable(true);
		txtBuscarDomingo.setEditable(true);
	}
	
	public static void main(String args[]){
		try{UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Impresion_Y_Revision_De_Cuadrante().setVisible(true);
		}catch(Exception e){System.err.println("Error en Main: "+e.getMessage());
		}
	}
}