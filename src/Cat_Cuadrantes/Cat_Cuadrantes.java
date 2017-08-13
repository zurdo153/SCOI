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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
	
	Obj_tabla ObjTab =new Obj_tabla();
	int columnas = 5,checkbox=-1;
	
	@SuppressWarnings("rawtypes")
	public Class[] baselunes (){
		Class[] types = new Class[columnas];
		for(int i = 0; i<columnas; i++){types[i]= java.lang.Object.class;}
		 return types;
	}
	public DefaultTableModel modelLunes = new DefaultTableModel(null, new String[]{"Orden","Folio","Actividad","Hora Inicio","Hora Final"}){
		 @SuppressWarnings("rawtypes")
			Class[] types = baselunes();
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
			public boolean isCellEditable(int fila, int columna){if(columna>2){return true;}else{ return false;}}
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
 	public DefaultTableModel modelMartes = new DefaultTableModel(null, new String[]{"Orden","Folio","Actividad","Hora Inicio","Hora Final"}){
 		 @SuppressWarnings("rawtypes")
 			Class[] types = basemartes();
 			@SuppressWarnings({ "rawtypes", "unchecked" })
 			public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
 			public boolean isCellEditable(int fila, int columna){if(columna>2){return true;}else{ return false;}}
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
   	public DefaultTableModel modelMiercoles = new DefaultTableModel(null, new String[]{"Orden","Folio","Actividad","Hora Inicio","Hora Final"}){
   		 @SuppressWarnings("rawtypes")
   			Class[] types = basemiercoles();
   			@SuppressWarnings({ "rawtypes", "unchecked" })
   		public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
   		public boolean isCellEditable(int fila, int columna){if(columna>2){return true;}else{ return false;}}
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
    public DefaultTableModel modelJueves = new DefaultTableModel(null, new String[]{"Orden","Folio","Actividad","Hora Inicio","Hora Final"}){
     @SuppressWarnings("rawtypes")
       	Class[] types = baseJueves();
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
    public boolean isCellEditable(int fila, int columna){if(columna>2){return true;}else{ return false;}}
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
    public DefaultTableModel modelViernes = new DefaultTableModel(null, new String[]{"Orden","Folio","Actividad","Hora Inicio","Hora Final"}){
        @SuppressWarnings("rawtypes")
      Class[] types = baseViernes();
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
    public boolean isCellEditable(int fila, int columna){if(columna>2){return true;}else{ return false;}}
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
    public DefaultTableModel modelSabado = new DefaultTableModel(null, new String[]{"Orden","Folio","Actividad","Hora Inicio","Hora Final"}){
        @SuppressWarnings("rawtypes")
      Class[] types = baseSabado();
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
    public boolean isCellEditable(int fila, int columna){if(columna>2){return true;}else{ return false;}}
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
    public DefaultTableModel modelDomingo = new DefaultTableModel(null, new String[]{"Orden","Folio","Actividad","Hora Inicio","Hora Final"}){
        @SuppressWarnings("rawtypes")
      Class[] types = baseDomingo();
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
    public boolean isCellEditable(int fila, int columna){if(columna>2){return true;}else{ return false;}}
    };
    JTable tablaDomingo = new JTable(modelDomingo);
    public JScrollPane Scroll_TablaDomingo = new JScrollPane(tablaDomingo);
    @SuppressWarnings({ "rawtypes", "unused" })
    private TableRowSorter trsfiltroDomingo;  
    
    
	JTextField txtBuscar     = new Componentes().text(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<", 300, "String");
	JTextField txtFolio      = new Componentes().text(new JCTextField(), "Folio", 10                                                   , "Int"   );
	JTextField txtCuadrante  = new Componentes().text(new JCTextField(), "Cuadrante", 200                                              , "String");
	JTextField txtPuesto     = new Componentes().text(new JCTextField(), "Puesto", 200                                                 , "String");
	JTextField txtReporta    = new Componentes().text(new JCTextField(), "Puesto Al Que Reporta", 200                                  , "String");
	
	JTextArea txaObjetivo 	 = new Componentes().textArea(new JTextArea(), "Objetivo"       ,300);
	JScrollPane scrollobjet  = new JScrollPane(txaObjetivo);
	
    JTextArea txaResponsabili= new Componentes().textArea(new JTextArea(), "Responsabilidad",700);
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
	
	JToolBar toolbarLunes      = new JToolBar();
	JCButton btnAgregLunes     = new JCButton("Agregar","double-arrow-icone-3883-16.png"  ,"Azul" );
	JCButton btnSubLunes       = new JCButton("Subir"  ,"Up.png"                          ,"Azul" );
	JCButton btnBajLunes       = new JCButton("Bajar"  ,"Down.png"                        ,"Azul" );
	JCButton btnEljLunes       = new JCButton("Eliminar","eliminar-bala-icono-7773-32.png","Azul" );
	JCButton btnCopLunes       = new JCButton("Copiar A Martes","igual.png               ","Azul" );
	
	JTabbedPane pestanas    = new JTabbedPane();
	JLayeredPane pDomingo   = new JLayeredPane(); 
	JLayeredPane pLunes     = new JLayeredPane(); 
	JLayeredPane pMarte     = new JLayeredPane();
	JLayeredPane pMiercoles = new JLayeredPane(); 
	JLayeredPane pJueves    = new JLayeredPane(); 
	JLayeredPane pViernes   = new JLayeredPane(); 
	JLayeredPane pSabado    = new JLayeredPane(); 
	
	String NuevoModifica ="";
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
		this.menu_toolbar.setFloatable(false);
		
		this.pestanas.addTab("Lunes"    ,pLunes    );
//		this.lunes();
		this.pestanas.addTab("Martes"   ,pMarte    );
//		this.martes();
		this.pestanas.addTab("Miércoles",pMiercoles);
//		this.miercoles();
		this.pestanas.addTab("Jueves"   ,pJueves   );
//		this.jueves();
		this.pestanas.addTab("Viernes"  ,pViernes  );
//		this.viernes();
		this.pestanas.addTab("Sábado"   ,pSabado   );
//		this.sabado();
		this.pestanas.addTab("Domingo"  ,pDomingo  );
//		this.domingo();
		
		 int x=15, y=20,width=120,height=20,sep=75;
		this.panel.add(menu_toolbar).setBounds                  (x     ,y      ,width*4    ,height );
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
		this.panel.add(new JLabel("Puesto:")).setBounds         (x     ,y+=30  ,width      ,height );
		this.panel.add(txtPuesto).setBounds                     (x+sep ,y      ,width*3-20 ,height );
		this.panel.add(btnfilpuestos).setBounds                 (x+355 ,y       ,height    ,height );
		this.panel.add(new JLabel("Reporta A:")).setBounds      (x     ,y+=30  ,width      ,height );
		this.panel.add(txtReporta).setBounds                    (x+sep ,y      ,width*3-20 ,height );
		this.panel.add(btnfilrepora).setBounds                  (x+355 ,y      ,height     ,height );
		this.panel.add(new JLabel("Objetivo Del Puesto:")).setBounds(x ,y+=30  ,width      ,height );
		this.panel.add(scrollobjet).setBounds                   (x     ,y+=20  ,width*4-25 ,140    );
		this.panel.add(new JLabel("Responsabilidades Del Puesto:")).setBounds(x,y+=150,180 ,height );
		this.panel.add(scrollrespons).setBounds                 (x     ,y+=20  ,width*4-25 ,220    );
		
		this.init_tablalunes();
		this.panelEnabledFalse();
		
		this.txtBuscar.addKeyListener  (opFiltro );
		this.btnNuevo.addActionListener(nuevo    );
		
		btnfilpuestos.addActionListener(opBuscarPuesto);
		btnfilrepora.addActionListener(opBuscarReporta);
		
		btnGuardar.addActionListener   (guardar );
		btnDeshacer.addActionListener  (deshacer);
		btnModificar.addActionListener (editar  );
		
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
             
	}
	
	public void init_tablalunes(){
    	this.tablaLunes.getColumnModel().getColumn(0).setMinWidth(50);
    	this.tablaLunes.getColumnModel().getColumn(1).setMinWidth(50);
    	this.tablaLunes.getColumnModel().getColumn(2).setMinWidth(287);
    	this.tablaLunes.getColumnModel().getColumn(3).setMinWidth(55);
    	this.tablaLunes.getColumnModel().getColumn(4).setMinWidth(55);
		String comandof="select 1 as orden,1020 folio,'Actividad Prueba De Dato' as actividad,0 as hora_inicio, 0 as hora_final"
				+ "      union all select 2 as orden,1025 folio,'Actividad 2 Prueba De Dato' as actividad,0 as hora_inicio, 0 as hora_final "
		        + "      union all select 2 as orden,1025 folio,'Actividad 2 Prueba De Dato' as actividad,0 as hora_inicio, 0 as hora_final ";
		String basedatos="26",pintar="si";
		ObjTab.Obj_Refrescar(tablaLunes,modelLunes, columnas, comandof, basedatos,pintar,checkbox);
		
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
		this.pLunes.add(txtBuscar).setBounds                  (x     ,y+=sev ,width    ,height );
		this.pLunes.add(Scroll_TablaLunes).setBounds          (x     ,y+=20  ,width    ,495    ); 
    //		this.pLunes.add(pLunes).setBounds                   (0     ,0    ,width+20  ,500    ); 
		btnSubLunes.addActionListener(opLunes);

    }
	
	
	public void panelEnabledFalse(){
		txtFolio.setEditable  (false);
		txtCuadrante.setEditable(false);
		btnModificar.setEnabled  (false);
        btnGuardar.setEnabled (false);		
		cmb_status.setEnabled (false);
		this.addWindowListener(new WindowAdapter() {
            public void windowOpened( WindowEvent e ){
            	txtBuscar.requestFocus();
           }
        });
	}		
	
	public void panelEnabledTrue(){	
		txtCuadrante.setEditable(true );
		btnModificar.setEnabled  (false);
		btnNuevo.setEnabled   (false);
		cmb_status.setEnabled(true);
		this.addWindowListener(new WindowAdapter() {
            public void windowOpened( WindowEvent e ){
             txtCuadrante.requestFocus();
           }
        });
	}
	
	public void panelLimpiar(){	
		txtFolio.setText("");
		txtCuadrante.setText("");
		txtBuscar.setText("");
		cmb_status.setSelectedIndex(0);
		ObjTab.Obj_Filtro(tablaLunes, txtBuscar.getText(), columnas);
	}	
	
	
	KeyListener opFiltro = new KeyListener(){
		public void keyReleased(KeyEvent arg0) {
			ObjTab.Obj_Filtro(tablaLunes, txtBuscar.getText(), columnas);
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
	
	ActionListener opLunes = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			if(tablaLunes.getRowCount()>1){
				if(tablaLunes.isRowSelected(tablaLunes.getSelectedRow())){
					if(tablaLunes.getSelectedRow() == 0 || tablaLunes.getSelectedRow() == tablaLunes.getRowCount()-1){
						JOptionPane.showMessageDialog(null,"Esta fila no se puede desplazar","Aviso",JOptionPane.INFORMATION_MESSAGE);
						return;
					}else{
						if(arg0.getSource().equals(btnSubLunes)){
							if(tablaLunes.getSelectedRow() > 1){
								Object primero = modelLunes.getValueAt(tablaLunes.getSelectedRow(),1);
								Object segundo = modelLunes.getValueAt(tablaLunes.getSelectedRow()-1,1);
								
								modelLunes.setValueAt(primero,tablaLunes.getSelectedRow()-1,1);
								modelLunes.setValueAt(segundo,tablaLunes.getSelectedRow(),1);	
								tablaLunes.setRowSelectionInterval(tablaLunes.getSelectedRow()-1,tablaLunes.getSelectedRow()-1);
							}else{
								JOptionPane.showMessageDialog(null,"No más filas hacia arriba!","Aviso",JOptionPane.INFORMATION_MESSAGE);
								return;
							}
									
						}
						if(arg0.getSource().equals(btnBajLunes)){
							if(tablaLunes.getSelectedRow()+1 < tablaLunes.getRowCount()-1){
								Object primero = modelLunes.getValueAt(tablaLunes.getSelectedRow(),1);
								Object segundo = modelLunes.getValueAt(tablaLunes.getSelectedRow()+1,1);
								
								modelLunes.setValueAt(primero,tablaLunes.getSelectedRow()+1,1);
								modelLunes.setValueAt(segundo,tablaLunes.getSelectedRow(),1);	
								tablaLunes.setRowSelectionInterval(tablaLunes.getSelectedRow()+1,tablaLunes.getSelectedRow()+1);
							
							}else{
								JOptionPane.showMessageDialog(null,"No más filas hacia abajo!","Aviso",JOptionPane.INFORMATION_MESSAGE);
								return;
							}
						}
						for(int lunes = 0; lunes<tablaLunes.getRowCount(); lunes++){
							tablaLunes.setValueAt(lunes+1+"  ", lunes, 0);
						}
					}
		
				}else{
					JOptionPane.showMessageDialog(null,"No esta seleccionada ninguna fila!","Aviso",JOptionPane.INFORMATION_MESSAGE);
					return;
				}
			
			}else{
				JOptionPane.showMessageDialog(null,"No hay filas que desplazar!","Aviso",JOptionPane.INFORMATION_MESSAGE);
				return;
			}
		}
	};
	
	public void Subir(JTable tabla){
		//TODO CREAR FUNCION
	}
	
	ActionListener nuevo = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			int CantFilas=modelLunes.getRowCount();
			if(CantFilas != 0){
				panelEnabledTrue();
				panelLimpiar();
				txtFolio.setText(CantFilas+"");
				txtCuadrante.requestFocus();
				btnGuardar.setEnabled(true);
			}else{
				panelLimpiar();
				panelEnabledTrue();
				txtFolio.setText(CantFilas+"");
				btnGuardar.setEnabled(true);
			}
			NuevoModifica="N";
		}
	};
	
	ActionListener deshacer = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			panelLimpiar();
			panelEnabledFalse();
			init_tablalunes();
			btnNuevo.setEnabled(true);
		}
	};
	
	ActionListener editar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			panelEnabledTrue();
			cmb_status.setEnabled(true);
			txtFolio.setEditable(false);
			btnModificar.setEnabled(false);
			btnGuardar.setEnabled(true);
			NuevoModifica="M";
		}		
		};
	
	public String Valida(){
//		String Folio   = txtFolio.getText().trim();
//		String Aspecto = txtCuadrante.getText().trim();
//		String Orden   = txtOrden.getText().trim();
	    String Mensaje ="";
//	    if(txtCuadrante.getText().equals("")){Mensaje+="\nEs Requerido Para Guardar Que Se Alimente Un Aspecto"; }
//	    if(txtValor.getText().equals("")){Mensaje+="\nEs Requerido Para Guardar Que Se Alimente Una Ponderacion"; }
//	    if(txtOrden.getText().equals("")){Mensaje+="\nEs Requerido Para Guardar Que Se Alimente Un Orden en El Aspecto"; }
//	    
//		for(int i =0; i<tabla.getRowCount(); i++){
//			    	  if(!Folio.equals(tabla.getValueAt(i, 0).toString().trim()))  {   	
//			    		  if(Aspecto.equals(tabla.getValueAt(i, 1).toString().trim())){	Mensaje+="\nEl Aspecto :"+Aspecto+" Existe En El Folio:"+tabla.getValueAt(i, 0).toString().trim();	    	  };
//                          if(Orden.equals(tabla.getValueAt(i, 3).toString().trim()))  {	Mensaje+="\nEl Orden :"+Orden+" Existe En El Folio:"+tabla.getValueAt(i, 0).toString().trim();	  	    	  };
//			    	  };
//        }
		return Mensaje;
	}	
	
	ActionListener guardar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
//				Obj_Aspectos aspecto = new Obj_Aspectos();
//			    String Mensaje =Valida();
//			if(!Mensaje.equals("")){
//				JOptionPane.showMessageDialog(null, Mensaje, "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
//			}else{
//				aspecto.setFolio(Integer.valueOf(txtFolio.getText()));
//				aspecto.setAspecto(txtCuadrante.getText().toString());
//				aspecto.setEstatus(cmb_status.getSelectedItem().toString());
//				aspecto.setGuardaModifica(NuevoModifica);
//				if(aspecto.guardar()){
//					JOptionPane.showMessageDialog(null,"El Registro Se Guardó Correctamente!","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/aplicara-el-dialogo-icono-6256-32.png"));
//					btnDeshacer.doClick();
//					return;
//				}else{
//					JOptionPane.showMessageDialog(null,"Error Al Guardar Avise al Administrador del Sistema","Aviso",JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-icono-eliminar5252-64.png"));
//					return;
//				}		
//			}
		}
	};
	
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
	    	String comandof="select folio, nombre  from tb_puesto order by nombre ";
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
	     @SuppressWarnings({ "rawtypes", "unused" })
	    private TableRowSorter trsfiltro;
		     
		JTextField txtBuscarfp  = new Componentes().text(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<", 300, "String");
		String parametro="";
		public Cat_Filtro_Puestos(String btnparametro){
			parametro=btnparametro;
			this.setSize(500,500);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			this.setModal(true);
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Filter-List-icon32.png"));
			this.panelf.setBorder(BorderFactory.createTitledBorder("Selecione Un Puesto Con Doble Click"));
			this.setTitle("Filtro De Puestos");
			this.panelf.add(txtBuscarfp).setBounds      (10 ,20 ,470 , 20 );
			this.panelf.add(scroll_tablafp).setBounds   (10 ,40 ,470 ,415 );
			this.init_tablafp();
			this.agregar(tablafp);
			this.txtBuscarfp.addKeyListener  (opFiltropuestos );
			contf.add(panelf);
		}
		
		private void agregar(final JTable tbl) {
	        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
		        	if(e.getClickCount()==1){
		        		int fila = tablafp.getSelectedRow();
		        		if(parametro.equals("Puesto")){
						    txtPuesto.setText   (tablafp.getValueAt(fila,1)+"");
		        		}else{
		        			txtReporta.setText  (tablafp.getValueAt(fila,1)+"");	
		        		}
		        		
						dispose();
		        	}
		        }
	        });
	    }
		
        private KeyListener opFiltropuestos = new KeyListener(){
			public void keyReleased(KeyEvent arg0) {
				ObjTab.Obj_Filtro(tablafp, txtBuscarfp.getText().toUpperCase(), columnasp);
			}
			public void keyTyped(KeyEvent arg0) {}
			public void keyPressed(KeyEvent arg0) {}		
		};
	}//termina filtro puestos
	
	
	public static void main(String args[]){
		try{UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Cuadrantes().setVisible(true);
		}catch(Exception e){System.err.println("Error en Main: "+e.getMessage());
		}
	}
	
}