package Cat_Seguridad;

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
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Cat_Checador.Cat_Reloj_Sincronizado_Servidor;
import Conexiones_SQL.Connexion;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_tabla;
import Obj_Seguridad.Obj_Registro_Proveedores;

@SuppressWarnings("serial")
public class Cat_Registro_De_Entrada_y_Salida_De_Proveedores extends JFrame{
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	Connexion con = new Connexion();
	
	Obj_tabla ObjTab =new Obj_tabla();
	Obj_Registro_Proveedores proveedores = new Obj_Registro_Proveedores();
	Cat_Reloj_Sincronizado_Servidor trae_hora = new Cat_Reloj_Sincronizado_Servidor();
	 
	int columnas = 3,checkbox=-1;
	
	@SuppressWarnings("rawtypes")
	public Class[] baselunes (){
		Class[] types = new Class[columnas];
		for(int i = 0; i<columnas; i++){types[i]= java.lang.Object.class;}
		 return types;
	}
	public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"Movimiento","Hora","Observaciones"}){
		 @SuppressWarnings("rawtypes")
			Class[] types = baselunes();
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
			public boolean isCellEditable(int fila, int columna){if(columna>1){return true;}else{ return false;}}
	};
	JTable tablaEntrada_Salida = new JTable(modelo);
	public JScrollPane Scroll_tablaEntrada_Salida = new JScrollPane(tablaEntrada_Salida);
     
 	@SuppressWarnings("rawtypes")
 	public Class[] baseorden (){
 		Class[] types = new Class[columnas];
 		for(int i = 0; i<columnas; i++){types[i]= java.lang.Object.class;}
 		 return types;
 	}
 	public DefaultTableModel modeloorden = new DefaultTableModel(null, new String[]{"Factura","Importe","Oden De Compra","Importe","Diferencia","Tipo Proveedor"}){
 		 @SuppressWarnings("rawtypes")
 			Class[] types = baselunes();
 			@SuppressWarnings({ "rawtypes", "unchecked" })
 			public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
 			public boolean isCellEditable(int fila, int columna){return false;}
 	};
   	JTable tablaOrden = new JTable(modeloorden);
   	public JScrollPane Scroll_tablaOrden = new JScrollPane(tablaOrden);
        @SuppressWarnings({ "rawtypes", "unused" })
       private TableRowSorter trsfiltroOrden;
      
	JTextField txtFolio        = new Componentes().text(new JCTextField(), "Folio", 10                                                , "Int"   );
	JTextField txtProveedor    = new Componentes().text(new JCTextField(), "Proveedor", 200                                           , "String");
	JTextField txtEjecutivo    = new Componentes().text(new JCTextField(), "Nombre Del Chofer Del Proveedor", 200                     , "String");
	JTextField txtFechaGuardo  = new Componentes().text(new JCTextField(), "Fecha Guardo"                      , 200                  , "String");
	
	JPasswordField PtxtClave   = new Componentes().textPassword(new JPasswordField(), "Clave", 100);
	
	String status[] = {"Vigente","Cancelado"};
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmb_status = new JComboBox(status);
	
	String establecimientoScoi[] = new Obj_Establecimiento().Combo_Establecimiento();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbEstablecimiento = new JComboBox(establecimientoScoi);
	
	JToolBar menu_toolbar = new JToolBar();
	JCButton btnBuscar    = new JCButton("Buscar"    ,"Filter-List-icon16.png"     ,"Azul"); 
	JCButton btnModificar = new JCButton("Modificar" ,"Modify.png"                 ,"Azul");
	JCButton btnNuevo     = new JCButton("Nuevo"     ,"Nuevo.png"                  ,"Azul");
	JCButton btnGuardar   = new JCButton("Guardar"   ,"Guardar.png"                ,"Azul");
	JCButton btnDeshacer  = new JCButton("Deshacer"  ,"deshacer16.png"             ,"Azul");
	JCButton btnderecha   = new JCButton(""          ,"adelante.png"               ,"Azul");
	JCButton btnizquierda = new JCButton(""          ,"atras.png"                  ,"Azul");
	JCButton btnfilproveedor= new JCButton(""        ,"Filter-List-icon16.png"     ,"Azul");
	
	JToolBar toolbarEntradaSalida   = new JToolBar();
	JCButton btnAgregMov  = new JCButton("Agregar Movimiento","double-arrow-icone-3883-16.png"  ,"Azul" );
	JCButton btnEljLunes    = new JCButton("Eliminar","eliminar-bala-icono-7773-32.png","Azul" );

	JToolBar toolbarOden    = new JToolBar();
	JCButton btnAgregFactura  = new JCButton("Agregar Factura" ,"double-arrow-icone-3883-16.png"  ,"Azul" );
	JCButton btnEljOrden    = new JCButton("Eliminar"        ,"eliminar-bala-icono-7773-32.png" ,"Azul" );
	
	JLabel lblHora   = new JLabel();
	JLabel lblFecha  = new JLabel();
	JLabel lblNombre = new JLabel();
	JLabel lblFolio  = new JLabel();
	
	JTextArea  txaObservaciones  = new Componentes().textArea(new JTextArea(), "Observaciones Generales"       ,500);
	JScrollPane scrollobjet      = new JScrollPane(txaObservaciones);
	
	String NuevoModifica ="";
	String FActividadesCargado ="";
	String[][] tablaproveedores;
	String[][] tablaordenes_compra;
	Object[]   vector = new Object[3];
	public Cat_Registro_De_Entrada_y_Salida_De_Proveedores(){
		this.setSize(819,390);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/asistencia.png"));
		this.setTitle("Registro De Entrada y Salida De Proveedores");
		this.panel.setBorder(BorderFactory.createTitledBorder("Registro De Entrada y Salida De Proveedores"));
		
		this.menu_toolbar.add(btnBuscar   );
	    this.menu_toolbar.addSeparator(   );
	    this.menu_toolbar.addSeparator(   );
	    this.menu_toolbar.add(btnModificar);
	    this.menu_toolbar.addSeparator(   );
	    this.menu_toolbar.addSeparator(   );
	    this.menu_toolbar.add(btnNuevo    );
	    this.menu_toolbar.addSeparator(   );
	    this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.add(btnDeshacer );
		this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.add(btnGuardar  );
		this.menu_toolbar.setFloatable(false);
		
		trae_hora.lblHora.setFont(new java.awt.Font("Arial",0,40));
		lblFecha.setFont(new java.awt.Font("Arial",0,40));
		
		 int x=15, y=0,width=130,height=20,sep=75;
		this.panel.add(lblFecha).setBounds                      (x+480 ,y      ,width*2    ,height*3);
		this.panel.add(trae_hora.lblHora).setBounds             (x+690 ,y      ,width*2    ,height*3); 
		this.panel.add(menu_toolbar).setBounds                  (x     ,y+=20  ,width*4    ,height );
		this.panel.add(new JLabel("Folio:")).setBounds          (x     ,y+=40  ,width      ,height );
		this.panel.add(txtFolio).setBounds                      (x+=sep,y      ,width      ,height );
        this.panel.add(btnizquierda).setBounds                  (x+=135,y      ,height     ,height );
        this.panel.add(btnderecha).setBounds                    (x+=25 ,y      ,height     ,height );
		this.panel.add(new JLabel("Estatus:")).setBounds        (x+=55 ,y      ,width      ,height );
		this.panel.add(cmb_status).setBounds                    (x+=45 ,y      ,width      ,height );
		this.panel.add(new JLabel("Pase El Gafete:")).setBounds (x=15  ,y+=30  ,width      ,height ); 
		this.panel.add(PtxtClave).setBounds                     (x+sep ,y      ,width      ,height );
		this.panel.add(lblFolio).setBounds                      (x+210 ,y      ,width      ,height ); 
		this.panel.add(lblNombre).setBounds                     (x+250 ,y      ,width*4    ,height ); 
		this.panel.add(new JLabel("Establecimiento:")).setBounds(x     ,y+=30  ,width*2    ,height );
		this.panel.add(cmbEstablecimiento).setBounds            (x+sep ,y      ,width*3    ,height );
		this.panel.add(new JLabel("Proveedor:")).setBounds      (x     ,y+=30  ,width      ,height );
		this.panel.add(txtProveedor).setBounds                  (x+sep ,y      ,width*3-20 ,height );
		this.panel.add(btnfilproveedor).setBounds               (x+445 ,y      ,height     ,height );		
		this.panel.add(new JLabel("Ejecutivo:")).setBounds      (x     ,y+=30  ,width      ,height );
		this.panel.add(txtEjecutivo).setBounds                  (x+sep ,y      ,width*3    ,height );
		this.panel.add(toolbarOden).setBounds                   (x     ,y+=30  ,335        ,height );
		this.panel.add(txtFechaGuardo).setBounds                (x+335 ,y      ,width      ,height );
		this.panel.add(Scroll_tablaOrden).setBounds             (x     ,y+=25  ,465        ,115    );
		this.panel.add(toolbarEntradaSalida).setBounds          (x=500 ,y=60   ,width=300  ,height );
		this.panel.add(Scroll_tablaEntrada_Salida).setBounds    (x     ,y+=25  ,width      ,115    );
		this.panel.add(new JLabel("Observaciones:")).setBounds  (x     ,y+=130 ,width      ,height );
		this.panel.add(scrollobjet).setBounds                   (x     ,y+=20  ,width      ,115    );
		
		ObjTab.tabla_precargada(tablaEntrada_Salida);
		ObjTab.tabla_precargada(tablaOrden         );
		
		    this.toolbarEntradaSalida.add(btnAgregMov);
			this.toolbarEntradaSalida.addSeparator(    );
			this.toolbarEntradaSalida.addSeparator(    );
		    this.toolbarEntradaSalida.add(btnEljLunes  );
			this.toolbarEntradaSalida.setFloatable(false);

		    this.toolbarOden.add(btnAgregFactura);
			this.toolbarOden.addSeparator(      );
			this.toolbarOden.addSeparator(      );
		    this.toolbarOden.add(btnEljOrden    );
			this.toolbarOden.setFloatable(false );
			
		this.btnAgregFactura.addActionListener(opBuscarOrden_Compra);
		this.btnEljLunes.addActionListener(new opEliminarfila(tablaEntrada_Salida));
		this.btnAgregMov.addActionListener(new opAgregar_Movimiento(tablaEntrada_Salida));
		this.PtxtClave.addKeyListener(busqueda_datos);
		this.btnEljOrden.addActionListener(new opEliminarOrdenCompra(tablaOrden));
		
		this.tablaEntrada_Salida.getColumnModel().getColumn(2).setMinWidth(148);
		this.tablaOrden.getColumnModel().getColumn(0).setMinWidth(110);
		this.tablaOrden.getColumnModel().getColumn(1).setMinWidth(85 );
		this.tablaOrden.getColumnModel().getColumn(2).setMinWidth(110);
		this.tablaOrden.getColumnModel().getColumn(3).setMinWidth(83 );
		
		panelEnabledFalse();
		this.btnNuevo.addActionListener     (nuevo          );		
		this.btnfilproveedor.addActionListener(opBuscarProveedor );
		this.btnGuardar.addActionListener   (guardar        );
		this.btnDeshacer.addActionListener  (deshacer       );
		this.btnBuscar.addActionListener    (buscar         );
		this.btnderecha.addActionListener   (opDerecha      );
		this.btnizquierda.addActionListener (opIzquierda    );
		
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
		tablaproveedores = proveedores.refrescar_tablas();
		lblFecha.setText(tablaproveedores[0][2].toString());
		txtFechaGuardo.setText(tablaproveedores[0][2].toString());
	}
	
	 KeyListener busqueda_datos = new KeyListener(){
			public void keyReleased(KeyEvent evento) {
				if(evento.getKeyCode()==KeyEvent.VK_ENTER){
				     @SuppressWarnings("deprecation")
					     String clave_checador=PtxtClave.getText().toString().toUpperCase().trim();
					     int posicionC = clave_checador.indexOf('C');
					     lblNombre.setText("");
					     lblFolio.setText("");
				 	if(posicionC>0){
				 		if(isNumeric(clave_checador.substring(0, posicionC))){
							Obj_Registro_Proveedores provedorvalida =new Obj_Registro_Proveedores().Valida_existe_colaborador(clave_checador);
							if(provedorvalida.getExiste().equals("true")){
								 cmbEstablecimiento.setSelectedItem(provedorvalida.getEstablecimiento());
								 lblFolio.setText(provedorvalida.getFolio_colaborador_recibe()+"");
								 lblNombre.setText(provedorvalida.getNombre_recibe());
								  return;
							}else{
							   JOptionPane.showMessageDialog(null, "El Colaborador Tiene Un Estatus No Valido", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));		    	
							   PtxtClave.setText("");
							   return;
							}	
				 		}else{
						  JOptionPane.showMessageDialog(null, "La Clave No Corresponde A Ningun Trabajador", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));		    	
						  PtxtClave.setText("");
						  return;
				 		}
				 	}else{
					  JOptionPane.showMessageDialog(null, "La Clave No Corresponde A Ningun Trabajador", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));		    	
					  PtxtClave.setText("");
					  return;
				 	}
				}	 	
			}
			public void keyTyped(KeyEvent arg0) {}
			public void keyPressed(KeyEvent arg0) {}		
	};	
		
	private static boolean isNumeric(String cadena){
	     	try {
	     		Integer.parseInt(cadena);
	     		return true;
	     	} catch (NumberFormatException nfe){
	     		return false;
	     	}
	}

	class opAgregar_Movimiento implements ActionListener{   
		JTable tablaparametro;
	    public opAgregar_Movimiento(final JTable tblp){
	    	tablaparametro = tblp;
	    }
	    public void actionPerformed(ActionEvent evt){
	    	DefaultTableModel modeloparametro= (DefaultTableModel) tablaparametro.getModel();
	    	Object[]   vector = new Object[5];
	    	int filas =tablaparametro.getRowCount();
                  if(filas%2==0)vector[0] ="Entrada" ;
				   else vector[0] ="Salida" ; 
                    vector[1] =trae_hora.lblHora.getText().toString().trim();
    	    		vector[2] ="" ;
    	    		modeloparametro.addRow(vector);
    				return;
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
	    	if(tablaparametro.getSelectedRow()==0){
				JOptionPane.showMessageDialog(null, "El Primer Registro De Entrada No Se Puede Eliminar", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;		
	    	}
	    	if(tablaparametro.getRowCount()>0){
	    		
					Object primeraColum     = modeloparametro.getValueAt(tablaparametro.getSelectedRow(),1);
					modeloparametro.setValueAt(primeraColum,tablaparametro.getSelectedRow()    ,1);
		    		modeloparametro.removeRow(tablaparametro.getSelectedRow());
					
						tablaparametro.setRowSelectionInterval(tablaparametro.getSelectedRow()+1,tablaparametro.getSelectedRow()+1);
						for(int i =0; i<tablaparametro.getRowCount(); i++){
							if(i%2==0)modeloparametro.setValueAt("Entrada",i,0);
							   else modeloparametro.setValueAt("Salida",i,0);
				    	  };
				
	    	}else{
				JOptionPane.showMessageDialog(null, "Es Requerido Que Seleccione Una Fila De La Tabla Para Eliminar", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
	    	}
	    }
	};
	
	class opEliminarOrdenCompra implements ActionListener{   
		JTable tablaparametro;
	    public opEliminarOrdenCompra(final JTable tblp){
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
	    	}else{
				JOptionPane.showMessageDialog(null, "Es Requerido Que Seleccione Una Fila De La Tabla Para Eliminar", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
	    	}
	    }
	};
	
	ActionListener opBuscarProveedor = new ActionListener(){
		public void actionPerformed(ActionEvent e){
         new Cat_Filtro_Proveedores().setVisible(true);
		}
	};
	
	ActionListener opBuscarOrden_Compra = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(cmbEstablecimiento.getSelectedIndex()==0){
				JOptionPane.showMessageDialog(null, "Es Requerido Que Seleccione Un Establecimiento Para Buscar La Orden De Compra", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}
			tablaordenes_compra=proveedores.refrescar_tablas_ordenes_de_compra(cmbEstablecimiento.getSelectedItem().toString().trim());
         new Cat_Filtro_Ordenes_de_Compra().setVisible(true);
     	
		}
	};
	
	ActionListener seleccionar_estab = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			 btnfilproveedor.doClick();
		}		
	};

	ActionListener deshacer = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			panelLimpiar();
			panelEnabledFalse();
			btnNuevo.setEnabled(true);
			NuevoModifica="";
		}
	};
	
	ActionListener nuevo = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			panelLimpiar();
			panelEnabledTrue();
			txtFolio.setText(new Obj_Registro_Proveedores().Nuevo()+"");
			btnGuardar.setEnabled(true);
			NuevoModifica="N";
			PtxtClave.setEditable(true);
            PtxtClave.requestFocus();   
    		txaObservaciones.setEditable(true);
    		cmb_status.setEnabled(false);
    		
    		vector[0] ="Entrada" ;
    		vector[1] =trae_hora.lblHora.getText().toString().trim();
    		vector[2] ="" ;
    		modelo.addRow(vector);
    		btnModificar.setEnabled(false);
			return;
		}
	};
		
	public void cargar_datos_tablas(int folio){
			  Obj_Registro_Proveedores proveedores = new Obj_Registro_Proveedores().refrescar_tablas(folio);
			  String [][] Tabla_facturas  = proveedores.getTabla_facturas(); 
			  String [][] Tabla_registros = proveedores.getTabla_registros(); 
			  Object []   vector = new Object[6];
			  txtFolio.setText                  (proveedores.getFolio()+"");
			  cmb_status.setSelectedItem(proveedores.getEstatus() );
			  lblFolio.setText(proveedores.getFolio_colaborador_recibe()+"");
			  lblNombre.setText(proveedores.getNombre_recibe());
			  cmbEstablecimiento.setSelectedItem(proveedores.getEstablecimiento() );
			  txtProveedor.setText              (proveedores.getProveedor().toString());
			  txtEjecutivo.setText              (proveedores.getChofer().toString());
			  txtFechaGuardo.setText            (proveedores.getFecha().toString());
			  
			for(int i=0;i<Tabla_facturas.length;i++){
				for(int j=0;j<6;j++){
				  vector[j] = Tabla_facturas[i][j].toString();
				}
					modeloorden.addRow(vector);
			 }	
			
			vector = new Object[3];
			for(int i=0;i<Tabla_registros.length;i++){
				for(int j=0;j<3;j++){
				  vector[j] = Tabla_registros[i][j].toString();
				}
					modelo.addRow(vector);
			 }	
			
	}
	
	ActionListener opDerecha = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			int Folio=0;
			if(!txtFolio.getText().equals("")){
				Folio=Integer.valueOf(txtFolio.getText().trim());
			}
			panelLimpiar();
			panelEnabledFalse();
			cargar_datos_tablas(Folio+1);
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
		}
	};
	
	ActionListener buscar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			new Cat_Filtro_Buscar_Cuadrantes().setVisible(true);
		}
	};
	
    ActionListener guardar = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				    String Mensaje =Valida();
					if(!Mensaje.equals("Para Poder Guardar Es Requerido Alimente:")){
						JOptionPane.showMessageDialog(null, Mensaje, "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
					}else{
						proveedores.setFolio(Integer.valueOf(txtFolio.getText().toString().trim()));
						proveedores.setFolio_colaborador_recibe(Integer.valueOf(lblFolio.getText().toString()));
						proveedores.setEstablecimiento(cmbEstablecimiento.getSelectedItem().toString()); 					
						proveedores.setProveedor(txtProveedor.getText().toString().trim());
						proveedores.setChofer(txtEjecutivo.getText().toString().trim());
						proveedores.setObservaciones(txaObservaciones.getText().toString().trim());		
						proveedores.setEstatus(cmb_status.getSelectedItem().toString().trim());
						proveedores.setNuevoModifica(NuevoModifica);
						proveedores.setTabla_registros(TablaGuardado());
						proveedores.setTabla_facturas(TablaGuardadotb2());
						
					if(proveedores.Guardar_Captura()){
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
			int rengloneslunes     = tablaEntrada_Salida.getRowCount()    ;
			int filas = rengloneslunes;
			int fila  = 0;
			int i     = 0;
			String[][] tablas = new String[filas][3];	
			while(rengloneslunes > 0){
					tablas[i][0] = modelo.getValueAt(fila, 0)+"";
					tablas[i][1] = modelo.getValueAt(fila, 1)+"";
					tablas[i][2] = modelo.getValueAt(fila, 2)+"";
					i+=1;
					fila+=1;
					rengloneslunes--;
			}
			return tablas;
		}
 	 
 	 public String[][] TablaGuardadotb2(){
			int rengloneslunes     = tablaOrden.getRowCount()    ;
			int filas = rengloneslunes;
			int fila  = 0;
			int i     = 0;
			String[][] tablas = new String[filas][6];	
			while(rengloneslunes > 0){
					tablas[i][0] = modeloorden.getValueAt(fila, 0)+"";
					tablas[i][1] = modeloorden.getValueAt(fila, 1)+"";
					tablas[i][2] = modeloorden.getValueAt(fila, 2)+"";
					tablas[i][3] = modeloorden.getValueAt(fila, 3)+"";
					tablas[i][4] = modeloorden.getValueAt(fila, 4)+"";
					tablas[i][5] = modeloorden.getValueAt(fila, 5)+"";
					i+=1;
					fila+=1;
					rengloneslunes--;
			}
			return tablas;
		}
 	 
		
	public String Valida(){
	    String Mensaje ="Para Poder Guardar Es Requerido Alimente:";
	    if(lblFolio.getText().equals("")){Mensaje+="\nEl La Persona Que Recibe Al Proveedor"; }
	    if(cmbEstablecimiento.getSelectedIndex()==0){Mensaje+="\nEl Establecimiento";         }
	    if(txtProveedor.getText().equals("")){Mensaje+="\nEl Nombre Del Puesto";              }
	    if(txtEjecutivo.getText().equals("")){Mensaje+="\nEl Nombre Ejecutivo Del Proveedor"; }
	    if(modeloorden.getRowCount()==0){Mensaje+="\nEs Requerido Alimente La Factura Que Se Recibe"; }
		return Mensaje;
	}	
	
	public void panelLimpiar(){	
		modelo.setRowCount(0);
		modeloorden.setRowCount(0);
		lblFolio.setText("");
		lblNombre.setText("");
		txtFolio.setText("");
		txtProveedor.setText("");
		txtEjecutivo.setText("");
		txaObservaciones.setText("");
		PtxtClave.setText("");
		cmb_status.setSelectedIndex(0);
		cmbEstablecimiento.setSelectedIndex(0);
		ObjTab.Obj_Filtro(tablaEntrada_Salida    , "", columnas);
	}	
	
	public void panelEnabledFalse(){
		PtxtClave.setEditable(false);
		txtFolio.setEditable  (false);
		txtProveedor.setEditable(false);
		txtEjecutivo.setEditable(false);
		txaObservaciones.setEditable(false);
		
        btnGuardar.setEnabled (false);
        btnfilproveedor.setEnabled(false);
        btnAgregMov.setEnabled(false);
        btnEljLunes.setEnabled(false);
        btnAgregFactura.setEnabled(false);
        btnEljOrden.setEnabled(false);
        btnModificar.setEnabled(false);
        txtFechaGuardo.setEditable(false);
        
		cmb_status.setEnabled (false);
		cmbEstablecimiento.setEnabled(false);
	}		
	
	public void panelEnabledTrue(){	
        btnGuardar.setEnabled (true);
        btnfilproveedor.setEnabled(true);
        btnAgregMov.setEnabled(true);
        btnModificar.setEnabled(true);
        btnEljLunes.setEnabled(true);
        btnAgregFactura.setEnabled(true);
        btnEljOrden.setEnabled(true);
        txtEjecutivo.setEditable(true);
        
		cmb_status.setEnabled (true);
		cmbEstablecimiento.setEnabled(true);
		btnBuscar.requestFocus();
	}

//TODO inicia filtro Proveedor	
		public class Cat_Filtro_Proveedores extends JDialog{
			Container contf = getContentPane();
			JLayeredPane panelf = new JLayeredPane();
			Connexion con = new Connexion();
			int columnasp = 3,checkbox=-1;
			
			@SuppressWarnings("rawtypes")
			public Class[] base (){
				Class[] types = new Class[columnasp];
				for(int i = 0; i<columnasp; i++){types[i]= java.lang.Object.class;}
				 return types;
			}
			
			public DefaultTableModel modeloprv = new DefaultTableModel(null, new String[]{"Folio", "Nombre de Proveedor", "Domicilio"}){
				 @SuppressWarnings("rawtypes")
					Class[] types = base();
					@SuppressWarnings({ "rawtypes", "unchecked" })
					public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
					public boolean isCellEditable(int fila, int columna){return false;}
			};
			
			JTable tablafprv = new JTable(modeloprv);
			public JScrollPane scroll_tablafp = new JScrollPane(tablafprv);
		     @SuppressWarnings({ "rawtypes" })
		    private TableRowSorter trsfiltro;
			
		    JCButton btnActualizar  = new JCButton("Actualizar"    ,"Actualizar.png"     ,"Azul");  
			JTextField txtBuscarfp  = new Componentes().text(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<", 300, "String");
			
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Cat_Filtro_Proveedores(){
				this.setSize(870,470);
				this.setResizable(false);
				this.setLocationRelativeTo(null);
				this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				this.setModal(true);
				this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Filter-List-icon32.png"));
				this.setTitle("Filtro De Proveedores");
				this.panelf.setBorder(BorderFactory.createTitledBorder("Selecione Un Proveedor Con Doble Click"));
				trsfiltro = new TableRowSorter(modeloprv); 
				tablafprv.setRowSorter(trsfiltro);
				
				ObjTab.tabla_precargada(tablafprv);
				
		    	this.tablafprv.getColumnModel().getColumn(0).setMinWidth(30);		
		    	this.tablafprv.getColumnModel().getColumn(1).setMinWidth(300);
		    	this.tablafprv.getColumnModel().getColumn(2).setMinWidth(458);
		    	
				  Object[]   vector = new Object[3];
				for(int i=0;i<tablaproveedores.length;i++){
					  for(int j=0;j<3;j++){
						vector[j] = tablaproveedores[i][j].toString();
					}
					  modeloprv.addRow(vector);
				 }	
		
				this.panelf.add(btnActualizar).setBounds    (738,10 ,120 , 20 );
				this.panelf.add(txtBuscarfp).setBounds      (5  ,35 ,852 , 20 );
				this.panelf.add(scroll_tablafp).setBounds   (5  ,55 ,852 ,383);
				this.agregar(tablafprv);
				this.txtBuscarfp.addKeyListener  (opFiltroproveedor);
				this.btnActualizar.addActionListener(actualizar);
				contf.add(panelf);
				
				 this.addWindowListener(new WindowAdapter() {public void windowOpened( WindowEvent e ){
		                	txtBuscarfp.requestFocus();}});
			}
			
			private void agregar(final JTable tbl) {
		        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
			        	if(e.getClickCount()==1){
			        		int fila = tablafprv.getSelectedRow();
			        		txtProveedor.setText  (tablafprv.getValueAt(fila,1)+"");
			        		
			        		
							dispose();
			        	}
			        }
		        });
		    }

			ActionListener actualizar = new ActionListener(){
				public void actionPerformed(ActionEvent e){
					  tablaproveedores= proveedores.refrescar_tablas();
				      Object[]   vector = new Object[3];
					for(int i=0;i<tablaproveedores.length;i++){
						  for(int j=0;j<3;j++){
							vector[j] = tablaproveedores[i][j].toString();
						}
						  modeloprv.addRow(vector);
					 }	
				}		
				};
				
	        private KeyListener opFiltroproveedor = new KeyListener(){
				public void keyReleased(KeyEvent arg0) {
					ObjTab.Obj_Filtro(tablafprv, txtBuscarfp.getText().toUpperCase(), columnasp);
				}
				public void keyTyped(KeyEvent arg0) {}
				public void keyPressed(KeyEvent arg0) {}		
			};
		}
		//termina filtro proveedor

		//TODO inicia filtro_factura	
		public class Cat_Filtro_Ordenes_de_Compra extends JDialog{
			Container contf = getContentPane();
			JLayeredPane panelf = new JLayeredPane();
			Connexion con = new Connexion();
			int columnaspo = 11,checkbox=-1;
			
			@SuppressWarnings("rawtypes")
			public Class[] base (){
				Class[] types = new Class[columnaspo];
				for(int i = 0; i<columnaspo; i++){types[i]= java.lang.Object.class;}
				 return types;
			}
            
			public DefaultTableModel modeloor_filtro = new DefaultTableModel(null, new String[]{"Folio","Establecimiento","Proveedor","Productos","Total","Fecha Elaboracion","Fecha Expiracion","Condicion Pago","Plazo","Fecha Autorizacion","Notas"}){
				 @SuppressWarnings("rawtypes")
					Class[] types = base();
					@SuppressWarnings({ "rawtypes", "unchecked" })
					public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
					public boolean isCellEditable(int fila, int columna){return false;}
			};
			
			JTable tablafilordenes = new JTable(modeloor_filtro);
			public JScrollPane scroll_tablafp = new JScrollPane(tablafilordenes);
		     @SuppressWarnings({ "rawtypes" })
		    private TableRowSorter trsfiltro;
		     
		    JCButton btnAceptar          = new JCButton("Aceptar"    ,"double-arrow-icone-3883-16.png"  ,"Azul");  
		    
			JTextField txtBuscarfp       = new Componentes().text(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<", 300, "String");
			JTextField txtFolioFactura   = new Componentes().text(new JCTextField(), "Folio Factura"                , 18, "String");
			JTextField txtImporteFactura = new Componentes().text(new JCTextField(), "Importe Total Factura"        , 10, "Double");
			JTextField txtFolioOrden     = new Componentes().text(new JCTextField(), "Folio Orden De Compra"        , 18, "String");
			JTextField txtImporteorden   = new Componentes().text(new JCTextField(), "Importe Total Orden De Compra", 10, "Double");
			JLabel     JlProveedor         = new JLabel();    
			
			String TipoPoveedor[] = {"Compra","Gasto"};
			@SuppressWarnings({ "unchecked", "rawtypes" })
			JComboBox cmb_tipo_Proveedor = new JComboBox(TipoPoveedor);
			
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Cat_Filtro_Ordenes_de_Compra(){
				this.setSize(800,380);
				this.setResizable(false);
				this.setLocationRelativeTo(null);
				this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				this.setModal(true);
				this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Filter-List-icon32.png"));
				this.setTitle("Filtro De Ordenes De Compra");
				this.panelf.setBorder(BorderFactory.createTitledBorder("Teclee El Folio De La Factura, Importes y Selecione Una Orden De Compra"));
				trsfiltro = new TableRowSorter(modeloor_filtro); 
				tablafilordenes.setRowSorter(trsfiltro);
				
	            int x=10,y=20,width=780, height=20;
	            this.panelf.add(new JLabel("Folio Factura:")).setBounds    (x     ,y     ,width ,height );
	            this.panelf.add(txtFolioFactura).setBounds                 (x+80  ,y     ,170   ,height ); 
	            this.panelf.add(new JLabel("Importe Factura:")).setBounds  (x+270 ,y     ,width ,height );
	            this.panelf.add(txtImporteFactura).setBounds               (x+360 ,y     ,170   ,height );
	            
	            this.panelf.add(new JLabel("Tipo Proveedor:")).setBounds   (x+550 ,y     ,width ,height );
				this.panelf.add(cmb_tipo_Proveedor).setBounds              (x+630 ,y     ,150   ,height );
				
	            this.panelf.add(new JLabel("Folio Orden C.:")).setBounds   (x     ,y+=25 ,width ,height );
	            this.panelf.add(txtFolioOrden).setBounds                   (x+80  ,y     ,170   ,height ); 
	            this.panelf.add(new JLabel("Importe Orden C.:")).setBounds (x+270 ,y     ,width ,height );
	            this.panelf.add(txtImporteorden).setBounds                 (x+360 ,y     ,170   ,height ); 
				this.panelf.add(btnAceptar).setBounds                      (x+630 ,y+15  ,150   ,height );
	            
				 this.panelf.add(new JLabel("Proveedor:")).setBounds       (x     ,y+=20 ,width ,height );
				this.panelf.add(JlProveedor).setBounds                     (x+80  ,y     ,width ,height );
				this.panelf.add(txtBuscarfp).setBounds                     (x     ,y+=18 ,width ,height );
				this.panelf.add(scroll_tablafp).setBounds                  (x     ,y+=20 ,width ,245    );
				
				ObjTab.tabla_precargada(tablafilordenes);
		    	this.tablafilordenes.getColumnModel().getColumn(0).setMinWidth(70);		
		    	this.tablafilordenes.getColumnModel().getColumn(1).setMinWidth(160);
		    	this.tablafilordenes.getColumnModel().getColumn(2).setMinWidth(390);
		    	this.tablafilordenes.getColumnModel().getColumn(3).setMinWidth(80);
		    	this.tablafilordenes.getColumnModel().getColumn(4).setMinWidth(80);
		    	this.tablafilordenes.getColumnModel().getColumn(5).setMinWidth(130);
		    	this.tablafilordenes.getColumnModel().getColumn(6).setMinWidth(130);
		    	this.tablafilordenes.getColumnModel().getColumn(7).setMinWidth(130);
		    	this.tablafilordenes.getColumnModel().getColumn(9).setMinWidth(130);
		    	this.tablafilordenes.getColumnModel().getColumn(10).setMinWidth(300);
		    	
		    	txtFolioOrden.setEditable(false);
		    	txtImporteorden.setEditable(false);
		        txtBuscarfp.setText(txtProveedor.getText().toString().trim());
		        
				ObjTab.Obj_Filtro(tablafilordenes, txtProveedor.getText().toString().trim().toUpperCase(), columnaspo);
				
				  Object[]   vector = new Object[11];
				for(int i=0;i<tablaordenes_compra.length;i++){
					  for(int j=0;j<11;j++){
						vector[j] = tablaordenes_compra[i][j].toString();
					}
					  modeloor_filtro.addRow(vector);
				 }	
				
				this.agregar(tablafilordenes);
				this.txtBuscarfp.addKeyListener  (opFiltropuestos );
				this.btnAceptar.addActionListener(aceptar);
				contf.add(panelf);
				 this.addWindowListener(new WindowAdapter() {public void windowOpened( WindowEvent e ){
	                	txtFolioFactura.requestFocus();}});
			}

			ActionListener aceptar = new ActionListener(){
				public void actionPerformed(ActionEvent e){
					Object[]   vector = new Object[6];
					 String Mensaje =Validaprov();
						if(!Mensaje.equals("Para Poder Aceptar Es Requerido Alimente:")){
							   JOptionPane.showMessageDialog(null,Mensaje, "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));		    	
						}else{
							if(txtFolioOrden.getText().toString().toUpperCase().trim().equals("")){
								if(JOptionPane.showConfirmDialog(null, "Falta Selecionar Una Orden De compra De La Lista"+"\n ¿Desea Continuar? " ) == 0){
									  vector[0]=txtFolioFactura.getText().toString().toUpperCase().trim();
								      vector[1]=txtImporteFactura.getText().toString().toUpperCase().trim();
								      vector[2]="";
								      vector[3]=0;
								      vector[4]= Double.valueOf(txtImporteFactura.getText().toString())- 0; 
								      vector[5]= cmb_tipo_Proveedor.getSelectedItem().toString().trim();
								      modeloorden.addRow(vector);
									  dispose();
									  return;
								}else{
									return;
								}
							}else{						
						      vector[0]=txtFolioFactura.getText().toString().toUpperCase().trim();
						      vector[1]=txtImporteFactura.getText().toString().toUpperCase().trim();
						      vector[2]=txtFolioOrden.getText().toString().toUpperCase().trim();
						      vector[3]=txtImporteorden.getText().toString().toUpperCase().trim();
						      vector[4]= Double.valueOf(txtImporteFactura.getText().toString())- Double.valueOf(txtImporteorden.getText().toString()); 
						      vector[5]= cmb_tipo_Proveedor.getSelectedItem().toString().trim();
						      modeloorden.addRow(vector);
							  dispose();
							  return;
					        }		
					}
				}	
			};
			
			public String Validaprov(){
			    String Mensaje ="Para Poder Aceptar Es Requerido Alimente:";
			    if(txtFolioFactura.getText().equals("")){Mensaje+="\nEl Folio De La Factura"; }
			    if(txtImporteFactura.getText().equals("")){Mensaje+="\nEl Importe De La Factura"; }
				return Mensaje;
			}	
			
			private void agregar(final JTable tbl) {
		        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
			        	if(e.getClickCount()==1){
			        		int fila = tablafilordenes.getSelectedRow();
			        			txtFolioOrden.setText  (tablafilordenes.getValueAt(fila,0)+"");	
			        			txtImporteorden.setText  (tablafilordenes.getValueAt(fila,4)+"");	
			        			JlProveedor.setText(tablafilordenes.getValueAt(fila,2)+"");
			        	}
			        }
		        });
		    }
			
	        private KeyListener opFiltropuestos = new KeyListener(){
				public void keyReleased(KeyEvent arg0) {
					ObjTab.Obj_Filtro(tablafilordenes, txtBuscarfp.getText(), columnaspo);
				}
				public void keyTyped(KeyEvent arg0) {}
				public void keyPressed(KeyEvent arg0) {}		
			};
		}
		
	//TODO inicia filtro_Buscar	
		public class Cat_Filtro_Buscar_Cuadrantes extends JDialog{
			Container contfb = getContentPane();
			JLayeredPane panelfb = new JLayeredPane();
			Connexion con = new Connexion();
			Obj_tabla ObjTab =new Obj_tabla();
			int columnasb = 7,checkbox=-1;
			public void init_tablafp(){
		    	this.tablab.getColumnModel().getColumn( 0).setMinWidth(55);
		    	this.tablab.getColumnModel().getColumn( 1).setMinWidth(60);
		    	this.tablab.getColumnModel().getColumn( 2).setMinWidth(250);
		    	this.tablab.getColumnModel().getColumn( 3).setMinWidth(150);
		    	this.tablab.getColumnModel().getColumn( 4).setMinWidth(200);
		    	this.tablab.getColumnModel().getColumn( 5).setMinWidth(100);
		    	this.tablab.getColumnModel().getColumn( 6).setMinWidth(120);
		    	String comandof=" exec proveedores_registro_de_entradas_y_salidas_filtro";
				String basedatos="26",pintar="si";
				ObjTab.Obj_Refrescar(tablab,modelob, columnasb, comandof, basedatos,pintar,checkbox);
		    }
			
			@SuppressWarnings("rawtypes")
			public Class[] base (){
				Class[] types = new Class[columnasb];
				for(int i = 0; i<columnasb; i++){types[i]= java.lang.Object.class;}
				 return types;
			}
			
			public DefaultTableModel modelob = new DefaultTableModel(null, new String[]{"Folio","Cod Proveedor","Proveedor","Establecimiento","Recibio","Fecha","Observaciones"}){
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
				this.setSize(780,350);
				this.setResizable(false);
				this.setLocationRelativeTo(null);
				this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				this.setModal(true);
				this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Filter-List-icon32.png"));
				this.panelfb.setBorder(BorderFactory.createTitledBorder("Selecione Un Registro Con Doble Click"));
				this.setTitle("Filtro De Registro De Entrada y Salida De Proveedores");
				trsfiltro = new TableRowSorter(modelob); 
				tablab.setRowSorter(trsfiltro);
				this.panelfb.add(txtBuscarb).setBounds      (10 ,20 ,750 , 20 );
				this.panelfb.add(scroll_tablab).setBounds   (10 ,40 ,750 ,280 );
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
			new Cat_Registro_De_Entrada_y_Salida_De_Proveedores().setVisible(true);
		}catch(Exception e){System.err.println("Error en Main: "+e.getMessage());
		}
	}
}