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

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
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

//import Cat_Cuadrantes.Cat_Captura_De_Cuadrantes.CargaDatosDelCombo;
import Conexiones_SQL.Connexion;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Contabilidad.Obj_Orden_De_Gasto;
import Obj_Contabilidad.Obj_Revision_De_Programacion_de_Pago;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_tabla;

@SuppressWarnings("serial")
public class Cat_Revision_De_Programacion_De_Pago extends JFrame {
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
			String comandot = "select clasificador,0,0,0 from programacion_de_pagos_clasificadores ";
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
				if(columna==0 ||columna==9||columna==10)
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
	
	 public DefaultTableModel modelot = new DefaultTableModel(null, new String[]{"Clasificador","T.Programado", "T.Pagado", "T.Pendiente"}){
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
			
		String status[] = programacion.combo_estatus_programacion();
		@SuppressWarnings("rawtypes")
		JComboBox cmb_status = new JComboBox(status);
		
		@SuppressWarnings("rawtypes")
		JComboBox cmbAgrupacion  = new JComboBox();
		
		JToolBar menu_toolbar    = new JToolBar();
		JTextField txtFolio      = new Componentes().text(new JCTextField()  ,"Folio Programacion"   ,15   ,"Int");
		JTextField txtPedientes  = new Componentes().text(new JCTextField(), "Cant. Pendientes", 150, "String");
		JTextField txtFiltro     = new Componentes().textfiltro(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<",300 , "String",tabla,Cantidad_Real_De_Columnas );
		JTextField txtTotal      = new Componentes().text(new JCTextField()  ,"Total"                     ,30   ,"String");
		
		JCButton btnFiltro       = new JCButton("Filtro"      ,"Filter-List-icon16.png","Azul" );
		JCButton btnGuardar      = new JCButton("Guardar"     ,"Guardar.png"           ,"Azul");
		JCButton btnActualizar   = new JCButton("Actualizar"  ,"Actualizar.png"        ,"Azul" );	
		JCButton btnImprimir     = new JCButton("Imprimir"    ,"imprimir-16.png"       ,"Azul" );
		JCButton btnAplicar      = new JCButton("Aplicar"     ,"Aplicar.png"           ,"Verde");
		
		private JCheckBox chb_invertir = new JCheckBox("Invertir");
		
		public Cat_Revision_De_Programacion_De_Pago()	{
			int ancho = Toolkit.getDefaultToolkit().getScreenSize().width;
			int alto = Toolkit.getDefaultToolkit().getScreenSize().height;
			setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds()); 
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/asistencia.png"));
			this.setTitle("Revisión De Programación de Pagos");
			this.campo.setBorder(BorderFactory.createTitledBorder("Seleccione Las Facturas Que Desea Programar Su Pago"));
			
			this.menu_toolbar.add(btnFiltro     );
		    this.menu_toolbar.addSeparator(     );
		    this.menu_toolbar.addSeparator(     );
			this.menu_toolbar.add(btnActualizar );
			this.menu_toolbar.addSeparator(     );
			this.menu_toolbar.addSeparator(     );
			this.menu_toolbar.add(btnImprimir   );
		    this.menu_toolbar.addSeparator(     );
		    this.menu_toolbar.addSeparator(     );
			this.menu_toolbar.add(btnGuardar    );
			this.menu_toolbar.setFloatable(false);
			
            int x=15 ,y=20 ,width=130 ,height=20;

			this.campo.add(menu_toolbar).setBounds        (x     ,y     ,400      ,40      );
			this.campo.add(new JLabel("Folio:")).setBounds(x     ,y+=45 ,width    ,height  );
			this.campo.add(txtFolio).setBounds            (x+40  ,y     ,width    ,height  );
			this.campo.add(new JLabel("Fecha:")).setBounds(x+190 ,y     ,width    ,height  );
			this.campo.add(chb_invertir).setBounds        (x     ,y+=30 ,width    ,height  );
			this.campo.add(cmb_status).setBounds          (x+150 ,y     ,width    ,height  );
			
			this.campo.add(scroll_tablatptales).setBounds (x+420 ,y=5   ,480      ,125     );
			campo.add(txtFiltro).setBounds                (x     ,y+=125,ancho-25 ,height  );
			campo.add(scroll_tabla).setBounds             (x     ,y+=20 ,ancho-25 ,alto-235);
			
			init_tabla_principal();		
			init_tabla_totales();
			Seleccionar_Respuesta(tabla);
			cont.add(campo);
			btnGuardar.addActionListener(opaceptar);
			btnActualizar.addActionListener(OpActualizar);
			btnImprimir.addActionListener(opImprimir_Reporte);
			btnFiltro.addActionListener(opfiltro);
		}
		
		public void calculo_De_totales() {
			float totalprogramado=0;
			float totalpagado=0;
			float totalpendiente=0;
			init_tabla_totales();

			for(int i=0;i<tabla.getRowCount();i++) {
				for(int t=0;t<tablat.getRowCount();t++) {
					if(tabla.getValueAt(i, 9).toString().trim().equals(tablat.getValueAt(t, 0).toString().trim()) && tabla.getValueAt(i, 13).toString().trim().equals("")  ) {
					   totalprogramado= Float.valueOf(tablat.getValueAt(t, 1).toString().trim())+Float.valueOf(tabla.getValueAt(i, 5).toString().trim());
					   tablat.setValueAt(totalprogramado+"", t, 1);
					}else{
						if(tabla.getValueAt(i, 9).toString().trim().equals(tablat.getValueAt(t, 0).toString().trim()) && !tabla.getValueAt(i, 13).toString().trim().equals("") ) { 
					       totalpagado= Float.valueOf(tablat.getValueAt(t, 2).toString().trim())+Float.valueOf(tabla.getValueAt(i, 5).toString().trim());
						   tablat.setValueAt(totalpagado+"", t, 2);
					    }
					}
					if(totalprogramado==0){
						totalpendiente=0;
					}else {
						totalpendiente=totalprogramado-totalpagado;
					}
					tablat.setValueAt(totalpendiente+"", t, 3);
				}
			}
			
			
			
		}
		
	    ActionListener OpActualizar = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(txtFolio.getText().toString().equals("")) {
				 JOptionPane.showMessageDialog(null, "Es Requerido Teclee el Folio de una Programación de pagos \n o busque una en el filtro y la seleccione antes de dar Actualizar","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
				 txtFolio.requestFocus();
				 return;
				}else {
					 tablacompleta= programacion.refrescar_tabla_programacion(txtFolio.getText().toString().trim());
					 ObjTab.llenado_de_modelo_desde_datos_tabla_precargados(tablacompleta, tabla);
					 calculo_De_totales();
				}
			 }
	    };
	    
	    ActionListener opImprimir_Reporte = new ActionListener(){
			public void actionPerformed(ActionEvent e){
//				int fila;
//				if(tabla.getSelectedRowCount()==0) {
//				 JOptionPane.showMessageDialog(null, "Es Requerido Seleccione Un Registro De La Tabla \nPara Poder Generar El Reporte De La Solicitud De Gasto","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
//				}else {
//				if(cmb_status.getSelectedItem().toString().trim().equals("EN VALIDACION")){
//						JOptionPane.showMessageDialog(null, "Las Ordenes De Gastos Que Se Originan De Un Servicio Es Requerido Sea Validada Para Poder Imprimir","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
//						return;
//				}else {
//					fila = tabla.getSelectedRow();
//					String basedatos="2.26";
//					String vista_previa_reporte="no";
//					int vista_previa_de_ventana=0;
//					String comando="orden_de_gasto_reporte '"+tabla.getValueAt(fila, 1)+"'";
//					String reporte = "Obj_Reporte_De_Orden_De_Gasto.jrxml";
//			  	    new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
//			    }
//				}
			}
	  	};
		
	  	private void Seleccionar_Respuesta(final JTable tbl) {
		    tbl.addMouseListener(new java.awt.event.MouseAdapter() {
				@SuppressWarnings("deprecation")
				public void mousePressed(MouseEvent e) {
		        	if(e.getClickCount()!=0){
                         if(tbl.getSelectedColumn()==9){
        					    int fila=tabla.getSelectedRow();
        					    tabla.setValueAt(usuario.getNombre_completo(), fila, 17); 
                    	        tbl.getColumnModel().getColumn(9).setCellEditor(new javax.swing.DefaultCellEditor(cmbAgrupacion));
                    		    tbl.getColumn("Clasificacion").setCellEditor(new CargaDatosDelCombo());
						       return;
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
				public void mouseReleased(MouseEvent e) {
					calculo_De_totales();
				}
				
				public void mouseEntered(MouseEvent e) {
					calculo_De_totales();
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
					 String respuestas=tablacompleta[0][19].toString();
					 String tipo_de_respuestas[] = respuestas.split("/");
						for(int i=0; i<tipo_de_respuestas.length; i++)  combo.addItem(tipo_de_respuestas[i]);
					} catch (NumberFormatException e) {
						e.printStackTrace();
					}
		            return combo;          
		        }
		    }
	  	 
		ActionListener opaceptar = new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
	    		if(tabla.isEditing()){			tabla.getCellEditor().stopCellEditing();	}
				for (int i=0;i<tabla.getRowCount();i++){
				  if((tabla.getValueAt(i,checkboxindex-1).toString().trim()).equals("true")){
//					actualizar();
					return;
				  };
				}
				JOptionPane.showMessageDialog(null, "Necesita Por Lo Menos Seleccionar Un Registro", "Aviso", JOptionPane.OK_OPTION,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
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
	  tablacompleta= programacion.refrescar_tabla_programacion(tablab.getValueAt(fila,0).toString() );
	  ObjTab.llenado_de_modelo_desde_datos_tabla_precargados(tablacompleta, tabla);
	  txtFolio.setText(tablab.getValueAt(fila,0).toString() );
	  dispose();
	  calculo_De_totales();
	}
	
	}

		
		public static void main(String args[]){
			try{
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				new Cat_Revision_De_Programacion_De_Pago().setVisible(true);
			}catch(Exception e){	}
		}
	}

