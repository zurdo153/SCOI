package Cat_Contabilidad;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

import Conexiones_SQL.Connexion;
import Conexiones_SQL.Generacion_Reportes;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Contabilidad.Obj_Revision_De_Programacion_de_Pago;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_tabla;

@SuppressWarnings("serial")
public class Cat_Programacion_De_Pago_Presupuesto extends JFrame {
	    String aceptar_negar="";
		Container cont = getContentPane();
		JLayeredPane campo = new JLayeredPane();
		Connexion con = new Connexion();
		Obj_tabla ObjTab= new Obj_tabla();
		Obj_Usuario usuario = new Obj_Usuario().LeerSession();
		Obj_Revision_De_Programacion_de_Pago programacion = new Obj_Revision_De_Programacion_de_Pago();
	    String[][] tablacompleta;
	    String[][] tablaclafificadores;
		   
		@SuppressWarnings("rawtypes")
		public Class[] tipos(){
			Class[] tip = new Class[Cantidad_Real_De_Columnas];
			for(int i =0; i<Cantidad_Real_De_Columnas; i++){
				if(i==0){
					tip[i]=java.lang.Boolean.class;
				}else{
					tip[i]=java.lang.Object.class;
				}
			}
			return tip;
		}
		
		int Cantidad_Real_De_Columnas=19,checkboxindex=1;
		public void init_tabla_principal(){
			this.tabla.getColumnModel().getColumn( 0).setMinWidth(20);
			this.tabla.getColumnModel().getColumn( 0).setMaxWidth(20);
			this.tabla.getColumnModel().getColumn( 1).setMinWidth(50);
	    	this.tabla.getColumnModel().getColumn( 1).setMaxWidth(50);
	    	this.tabla.getColumnModel().getColumn( 2).setMinWidth(50);
	    	this.tabla.getColumnModel().getColumn( 2).setMinWidth(50);	    	
	    	this.tabla.getColumnModel().getColumn( 3).setMinWidth(280);
	    	this.tabla.getColumnModel().getColumn( 4).setMinWidth(110);
	      	this.tabla.getColumnModel().getColumn( 5).setMinWidth(90);
	    	this.tabla.getColumnModel().getColumn( 6).setMinWidth(90);
	    	this.tabla.getColumnModel().getColumn( 7).setMinWidth(80);
	       	this.tabla.getColumnModel().getColumn( 8).setMinWidth(155);
	    	this.tabla.getColumnModel().getColumn( 9).setMinWidth(120);
	    	this.tabla.getColumnModel().getColumn(10).setMinWidth(155);
	    	this.tabla.getColumnModel().getColumn(11).setMinWidth(90);
	    	this.tabla.getColumnModel().getColumn(12).setMinWidth(120);
	    	this.tabla.getColumnModel().getColumn(13).setMinWidth(70);
	    	this.tabla.getColumnModel().getColumn(14).setMinWidth(100);
	    	this.tabla.getColumnModel().getColumn(15).setMinWidth(90);
	    	this.tabla.getColumnModel().getColumn(16).setMinWidth(90);
	    	this.tabla.getColumnModel().getColumn(17).setMinWidth(250);
	    	this.tabla.getColumnModel().getColumn(18).setMinWidth(90);
	    	
			String comando = "select '','','','','',0,0,'','','','','','','','','','','','' ";
			ObjTab.Obj_Refrescar(tabla,modelo, Cantidad_Real_De_Columnas, comando, "26","si",checkboxindex);
			modelo.setRowCount(0);
	    }
		
	 public void init_tabla_totales(){
			this.tablat.getColumnModel().getColumn( 0).setMinWidth(130);
			this.tablat.getColumnModel().getColumn( 1).setMinWidth(110);
			this.tablat.getColumnModel().getColumn( 1).setMaxWidth(110);
			this.tablat.getColumnModel().getColumn( 2).setMinWidth(110);
			this.tablat.getColumnModel().getColumn( 2).setMaxWidth(110);
			this.tablat.getColumnModel().getColumn( 3).setMinWidth(110);
			this.tablat.getColumnModel().getColumn( 3).setMaxWidth(110);
			String comandot = "select clasificador,0,0,0 from programacion_de_pagos_clasificadores";
			ObjTab.Obj_Refrescar(tablat,modelot, 4, comandot, "26","si",-1);
	 }
		
	 public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"S","Folio","Codigo Prv","Proveedor","Factura","Importe","Imp.Desc.Fin.","Fecha Vence","Observaciones Contabilidad", "Clasificacion","Observaciones Comercializacion","Folio Pago","Fecha Pago","Poliza","Cuenta Bancaria","Fecha Cheque", "Folio Cheque", "Usuario Programo Pago","Fecha Programo"}){
		 @SuppressWarnings("rawtypes")
			Class[] types = tipos();
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {
	         return types[columnIndex];
	     }
			public boolean isCellEditable(int fila, int columna){
				if(columna==8)
					return true; return false;
			}
	    };
	    JTable tabla = new JTable(modelo){
       	 public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
       	     Component componente = super.prepareRenderer(renderer, row, col);
       	       if(col==9){
       	        	Color c = new java.awt.Color(255,0,0);
       	           if(tabla.getValueAt(row,9).toString().trim().equals("Posfechado")){
                   	 c = new java.awt.Color(0,255,220);
                   }
       	        	
                   if(tabla.getValueAt(row,9).toString().trim().equals("Productos Frescos")){
                      	 c = new java.awt.Color(3,181,14); 
                   }
                   
                   if(tabla.getValueAt(row,9).toString().trim().equals("Abarrotes")){
                    	 c = new java.awt.Color(216,164,0); 
                   }
                   
                   if(tabla.getValueAt(row,9).toString().trim().equals("Nax")){
                  	     c = new java.awt.Color(0,46,255); 
                   }
                   
                   if(tabla.getValueAt(row,9).toString().trim().equals("Ferreteria")){
                       c = new java.awt.Color(255,232,0); 
                   }
                     
                   if(tabla.getValueAt(row,9).toString().trim().equals("Hogary")){
                        c = new java.awt.Color(255,93,18); 
                   }
                   
                   if(tabla.getValueAt(row,9).toString().trim().equals("Napolitana")){
                       c = new java.awt.Color(255,0,170); 
                   }
                  
                   if(tabla.getValueAt(row,9).toString().trim().equals("Espacio 35")){
                       c = new java.awt.Color(255,0,170); 
                   }
                       componente.setBackground(c); 
               }
      	     return componente;
       	 }
       };
       
		public JScrollPane scroll_tabla = new JScrollPane(tabla);
	
	 public DefaultTableModel modelot = new DefaultTableModel(null, new String[]{"Clasificador","T.Programado", "T.Pagado", "T.Pendiente%"}){
			 @SuppressWarnings("rawtypes")
				Class[] types = tipos();
				@SuppressWarnings({ "unchecked", "rawtypes" })
				public Class getColumnClass(int columnIndex) {
		         return types[columnIndex];
		     }
			 public boolean isCellEditable(int fila, int columna){
				 return false;
				}
		    };
		    
		    JTable tablat = new JTable(modelot){
		       	 public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
		       	     Component componente = super.prepareRenderer(renderer, row, col);
		       	     
		       	       if(col==0){
		       	        	Color c = new java.awt.Color(255,0,0);
		       	           if(tablat.getValueAt(row,0).toString().trim().equals("Posfechado")){
		                   	 c = new java.awt.Color(0,255,220);
		                   }
		       	        	
		                   if(tablat.getValueAt(row,0).toString().trim().equals("Productos Frescos")){
		                      	 c = new java.awt.Color(3,181,14); 
		                   }
		                   
		                   if(tablat.getValueAt(row,0).toString().trim().equals("Abarrotes")){
		                    	 c = new java.awt.Color(216,164,0); 
		                   }
		                   
		                   if(tablat.getValueAt(row,0).toString().trim().equals("Nax")){
		                  	     c = new java.awt.Color(0,46,255); 
		                   }
		                   
		                   if(tablat.getValueAt(row,0).toString().trim().equals("Ferreteria")){
		                       c = new java.awt.Color(255,232,0); 
		                   }
		                     
		                   if(tablat.getValueAt(row,0).toString().trim().equals("Hogary")){
		                        c = new java.awt.Color(255,93,18); 
		                   }
		                   
		                   if(tablat.getValueAt(row,0).toString().trim().equals("Napolitana")){
		                       c = new java.awt.Color(255,0,170); 
		                   }
		                  
		                   if(tablat.getValueAt(row,0).toString().trim().equals("Espacio 35")){
		                       c = new java.awt.Color(255,0,170); 
		                   }
		                       componente.setBackground(c); 
		               }
		      	     return componente;
		       	 }
		       };
			public JScrollPane scroll_tablatptales = new JScrollPane(tablat);
			
		JToolBar menu_toolbar    = new JToolBar();
		JTextField txtFolio      = new Componentes().text(new JCTextField()  ,"Folio Programacion" ,15   ,"Int"   );
		JTextField txtPedientes  = new Componentes().text(new JCTextField(), "Cant. Pendientes"    , 150, "String");
		JTextField txtFiltro     = new Componentes().textfiltro(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<",300 , "String",tabla,Cantidad_Real_De_Columnas );
		JTextField txtestatus    = new Componentes().text(new JCTextField()  ,"Estatus"            ,15   ,"Int"   );
		
		JTextField txtProgramacion= new Componentes().text(new JCTextField() ,"Total Programacion"   ,20   ,"Double");
		JTextField txtPresupuesto = new Componentes().text(new JCTextField() ,"Total Presupuesto"    ,20   ,"Double");
		JTextField txtTotal       = new Componentes().text(new JCTextField() ,"Total Propuesto"      ,20   ,"Double");
		JTextField txtPagado      = new Componentes().text(new JCTextField() ,"Total Pagado"         ,20   ,"Double");
		JTextField txtPendiente   = new Componentes().text(new JCTextField() ,"Total Pagado"         ,20   ,"Double");
		JTextField txtPropuestopre= new Componentes().text(new JCTextField() ,"Presupuesto Propuesto",15   ,"Int");
		
		JCButton btnFiltro       = new JCButton("Buscar"      ,"Filter-List-icon16.png","Azul" );
		JCButton btnGuardar      = new JCButton("Guardar"     ,"Guardar.png"           ,"Azul");
		JCButton btnImprimir     = new JCButton("Imprimir"    ,"imprimir-16.png"       ,"Azul" );
		
		String GuardarActualizar="";
		
		public Cat_Programacion_De_Pago_Presupuesto()	{
			int ancho = Toolkit.getDefaultToolkit().getScreenSize().width;
			int alto = Toolkit.getDefaultToolkit().getScreenSize().height;
			setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds()); 
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/asistencia.png"));
			this.setTitle("Programación de Pago Presupuesto");
			this.campo.setBorder(BorderFactory.createTitledBorder("Teclee La Cantidad de la programacion de Pago"));
			
			this.menu_toolbar.add(btnFiltro     );
		    this.menu_toolbar.addSeparator(     );
		    this.menu_toolbar.addSeparator(     );
			this.menu_toolbar.add(btnImprimir   );
		    this.menu_toolbar.addSeparator(     );
		    this.menu_toolbar.addSeparator(     );
			this.menu_toolbar.add(btnGuardar    );
			this.menu_toolbar.setFloatable(false);
			
            int x=15 ,y=20 ,width=130 ,height=20;

			this.campo.add(menu_toolbar).setBounds               (x     ,y     ,320      ,20      );
			this.campo.add(new JLabel("Folio:")).setBounds       (x     ,y+=25 ,width    ,height  );
			this.campo.add(txtFolio).setBounds                   (x+65  ,y     ,145      ,height  );
			this.campo.add(new JLabel("Presupuesto:")).setBounds (x     ,y+=25 ,width    ,height  );
			this.campo.add(txtPropuestopre).setBounds            (x+65  ,y     ,145      ,height  );
			this.campo.add(scroll_tablatptales).setBounds        (x+335 ,y=5   ,480      ,145     );
			this.campo.add(new JLabel("Programacion:")).setBounds(x=880 ,y     ,width    ,height  );
			this.campo.add(txtProgramacion).setBounds            (x+70  ,y     ,width    ,height  );
			this.campo.add(new JLabel("Presupuesto:")).setBounds (x     ,y+=25 ,width    ,height  );
			this.campo.add(txtPresupuesto).setBounds             (x+70  ,y     ,width    ,height  );
			this.campo.add(new JLabel("Propuesto:")).setBounds   (x     ,y+=25 ,width    ,height  );
			this.campo.add(txtTotal).setBounds                   (x+70  ,y     ,width    ,height  );
			this.campo.add(new JLabel("Pagado:")).setBounds      (x     ,y+=25 ,width    ,height  );
			this.campo.add(txtPagado).setBounds                  (x+70  ,y     ,width    ,height  );
			this.campo.add(txtFiltro).setBounds                  (x=15  ,y+=75 ,ancho-25 ,height  );
			campo.add(scroll_tabla).setBounds                    (x     ,y+=20 ,ancho-25 ,alto-235);
			
			init_tabla_principal();		
			init_tabla_totales();
			cont.add(campo);
			btnGuardar.addActionListener(opaGuardar);
			btnImprimir.addActionListener(opImprimir_Reporte);
			btnFiltro.addActionListener(opfiltro);
			
			txtProgramacion.setEditable(false);
			txtPresupuesto.setEditable(false);
			txtTotal.setEditable(false);
			txtFolio.setEditable(false);
			txtPagado.setEditable(false);
			txtPropuestopre.setEditable(false);
		}
		 
		public void inicializartablatotales() {
			 tablaclafificadores= programacion.Tabla_Programacion_de_pago_clasificadores();
			 ObjTab.llenado_de_modelo_desde_datos_tabla_precargados(tablaclafificadores, tablat);
		}
		
		float totalpropuesto=0;
		public void calculo_De_totales() {
			float totalprogramado=0;
			float totalpagado=0;
			float totalpendiente=0;
			float totalpagadofinal=0;
			totalpropuesto=0;
		    DecimalFormat formateador = new DecimalFormat("###,###.##"); 
			ObjTab.llenado_de_modelo_desde_datos_tabla_precargados(tablaclafificadores, tablat);

			for(int i=0;i<tabla.getRowCount();i++) {
				for(int t=0;t<tablat.getRowCount();t++) {
					if(tabla.getValueAt(i, 9).toString().trim().equals(tablat.getValueAt(t, 0).toString().trim())) {
					   totalprogramado= Float.valueOf(tablat.getValueAt(t, 1).toString().trim())+Float.valueOf(tabla.getValueAt(i, 5).toString().trim());
					   tablat.setValueAt(totalprogramado+"", t, 1);
					}
					
					if(tabla.getValueAt(i, 9).toString().trim().equals(tablat.getValueAt(t, 0).toString().trim()) && !tabla.getValueAt(i, 13).toString().trim().equals("") ) { 
					       totalpagado= Float.valueOf(tablat.getValueAt(t, 2).toString().trim())+Float.valueOf(tabla.getValueAt(i, 5).toString().trim());
						   tablat.setValueAt(totalpagado+"", t, 2);
					}
				    totalpendiente=0;
					if(tabla.getValueAt(i, 9).toString().trim().equals(tablat.getValueAt(t, 0).toString().trim()) && totalpagado>0 && totalprogramado>0) {
						totalpendiente=(totalpagado/totalprogramado)*100 ;
						tablat.setValueAt(totalpendiente+"", t, 3);
					}
				}
				
				if(!tabla.getValueAt(i, 13).toString().trim().equals("") ) { 
					totalpagadofinal=totalpagadofinal+Float.valueOf(Float.valueOf(tabla.getValueAt(i, 5).toString().trim()));
				}
			}
			
			for(int t=0;t<tablat.getRowCount();t++) {
				if(t>0) {
					totalpropuesto=totalpropuesto+Float.valueOf(tablat.getValueAt(t, 1).toString().trim());
				}
			}
			
			  txtTotal.setText(formateador.format (totalpropuesto ));
			  txtTotal.setHorizontalAlignment(JTextField.RIGHT);
			  txtPagado.setText(formateador.format (totalpagadofinal ));
			  txtPagado.setHorizontalAlignment(JTextField.RIGHT);
		}
		
	    ActionListener opImprimir_Reporte = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(txtFolio.getText().toString().equals("")) {
				 JOptionPane.showMessageDialog(null, "Es Requerido Seleccione Una Programacion de Pago Para Imprimir","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
				}else {
					String basedatos="2.26";
					String vista_previa_reporte="no";
					int vista_previa_de_ventana=0;
					String comando="exec programacion_de_pago_reporte '"+String.valueOf(txtFolio.getText().toUpperCase().trim())+"'"  ;
					String reporte = "Obj_Reportes_De_Programacion_De_Pagos.jrxml";
			  	    new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
				}
			}
	  	};
		
		ActionListener opaGuardar = new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
	    		if(txtFolio.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Es Requerido Carge Una Programacion De Pago Para Poder Guardar,\na continuación se abrirá el filtro de seleccion de programación de pago","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
					btnFiltro.doClick();
					return;
	    		}
				if(programacion.BucarEstatus(txtFolio.getText().toString().trim())) {
					btnGuardar.setEnabled(false);
					JOptionPane.showMessageDialog(null, "La Programación De Pago Está Cerrada y Solo Puede Ser Consultada","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
					return;
				}
				
	    		if(tabla.isEditing()){			tabla.getCellEditor().stopCellEditing();	}
				    programacion.setGuardarActualizar(GuardarActualizar);
				    programacion.setTabla_programacion(ObjTab.tabla_guardar_sin_validacion(tabla));
				    programacion.setTotal_programacion(Float.valueOf(tablacompleta[0][20].toString()));
				    programacion.setTotal_presupuesto(Float.valueOf(txtPropuestopre.getText().toString()));
				    programacion.setTotal_propuesto(Float.valueOf(txtTotal.getText().toString().trim().replace(",","")));
			    if(programacion.Guardar_presupuesto()) {
				   JOptionPane.showMessageDialog(null, "Se Guardo Correctamente", "Aviso", JOptionPane.OK_OPTION,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
			       return;
			    }else {
				   JOptionPane.showMessageDialog(null,"Error Al Guardar Avise al Administrador del Sistema","Aviso",JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-icono-eliminar5252-64.png"));
				   return;
			    }
			}
		};
		
		ActionListener opfiltro = new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
	    		if(tabla.isEditing()){			tabla.getCellEditor().stopCellEditing();	}
				new Cat_Filtro_Buscar_Programacion_de_Pago().setVisible(true);;
	    	}	
		};
		
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////TODO inicia filtro_Buscar ORDEN DE GASTO////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public class Cat_Filtro_Buscar_Programacion_de_Pago extends JDialog{
		Container contfb = getContentPane();
		JLayeredPane panelfb = new JLayeredPane();
		Connexion con = new Connexion();
		Obj_tabla ObjTab =new Obj_tabla();
		int columnasb = 4,checkbox=-1;
		public void init_tablafp(){
		this.tablab.getColumnModel().getColumn( 0).setMinWidth(50);
		this.tablab.getColumnModel().getColumn( 0).setMaxWidth(70);
		this.tablab.getColumnModel().getColumn( 1).setMinWidth(130);
		this.tablab.getColumnModel().getColumn( 2).setMinWidth(80);
		this.tablab.getColumnModel().getColumn( 3).setMinWidth(300);
				
		String comandob = "exec programacion_de_pago_filtro_seleccion_por_folio";
		String basedatos="26",pintar="si";
		ObjTab.Obj_Refrescar(tablab,modelob, columnasb, comandob, basedatos,pintar,checkbox);
	}
	
	@SuppressWarnings("rawtypes")
	public Class[] base (){
	  Class[] types = new Class[columnasb];
	    for(int i = 0; i<columnasb; i++){types[i]= java.lang.Object.class;}
	  return types;
	}
	
	public DefaultTableModel modelob = new DefaultTableModel(null, new String[]{"Folio","Fecha","Estatus","Realizo Programacion"}){
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
	
	JTextField txtBuscarb  = new Componentes().textfiltro(new JCTextField() ,">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<" ,500 ,"String" ,tablab ,columnasb );
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Cat_Filtro_Buscar_Programacion_de_Pago(){
		this.setSize(625,400);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setModal(true);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Filter-List-icon32.png"));
		this.panelfb.setBorder(BorderFactory.createTitledBorder("Selecione Un Registro Con Doble Click"));
		this.setTitle("Filtro De Programaciones De Pago");
		trsfiltro = new TableRowSorter(modelob); 
		tablab.setRowSorter(trsfiltro);
		this.panelfb.add(txtBuscarb).setBounds      (10 ,20 ,600 , 20 );
		this.panelfb.add(scroll_tablab).setBounds   (10 ,40 ,600 ,300 );
		this.init_tablafp();
		this.agregar(tablab);
		contfb.add(panelfb);
	}
	
	private void agregar(final JTable tbl) {
	tbl.addMouseListener(new MouseListener() {
	public void mouseReleased(MouseEvent e){
	if(e.getClickCount() == 2){agregarf(); }
	}
	public void mousePressed(MouseEvent e) {}
	public void mouseExited(MouseEvent e)  {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {}
	});
	
	tbl.addKeyListener(new KeyListener() {
	@Override
	public void keyPressed(KeyEvent e)  {
		if(e.getKeyCode()==KeyEvent.VK_ENTER){
		 agregarf();	
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {}
	@Override
	public void keyTyped(KeyEvent e)    {}
	});
	}
	
	private void agregarf() {
	  modelo.setRowCount(0);
	  int fila = tablab.getSelectedRow();
	  tablacompleta= programacion.refrescar_tabla_programacion(tablab.getValueAt(fila,0).toString(),0+"" );
	  ObjTab.llenado_de_modelo_desde_datos_tabla_precargados(tablacompleta, tabla);
	  txtFolio.setText(tablab.getValueAt(fila,0).toString() );
	  
	  DecimalFormat formateador = new DecimalFormat("###,###.##"); 
	  Double totalprogramacion= Double.valueOf(tablacompleta[0][20].toString());
	  Double totalpresupuesto = Double.valueOf(tablacompleta[0][21].toString());

	  System.out.println(tablacompleta[0][23].toString());
	  GuardarActualizar=tablacompleta[0][23].toString().trim();
	  txtProgramacion.setText(formateador.format (totalprogramacion));
	  txtPresupuesto.setText(formateador.format (totalpresupuesto ));
	  txtPropuestopre.setText(tablacompleta[0][21].toString());
	  
	  txtProgramacion.setHorizontalAlignment(JTextField.RIGHT);
	  txtPresupuesto.setHorizontalAlignment(JTextField.RIGHT);
	  
	  
	  dispose();
	  inicializartablatotales();
	  calculo_De_totales();
	
		if(programacion.BucarEstatus(txtFolio.getText().toString().trim())) {
			btnGuardar.setEnabled(false);
			txtPropuestopre.setEditable(false);
			JOptionPane.showMessageDialog(null, "La Programación De Pago Está Cerrada \n No Podrá Guardar Cambios","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
			return;
		}else {
			btnGuardar.setEnabled(true);
		    txtPropuestopre.setEditable(true);
		}
	}
	
	}
		
		public static void main(String args[]){
			try{
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				new Cat_Programacion_De_Pago_Presupuesto().setVisible(true);
			}catch(Exception e){	}
		}
	}

