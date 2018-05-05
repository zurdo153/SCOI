package Cat_Punto_De_Venta;

import java.awt.Color;
import java.awt.Container;
import java.awt.Event;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
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
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_tabla;
import Obj_Punto_De_Venta.Obj_Ventas_Express;

@SuppressWarnings("serial")
public class Cat_Liquidacion_Ventas_Express extends JFrame{
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();

	Obj_tabla ObjTab =new Obj_tabla();
	Obj_Ventas_Express Venta_Express = new Obj_Ventas_Express(); 
	Obj_Usuario usuario = new Obj_Usuario().LeerSession();
	
	int columnas = 5,checkbox=-1;
	@SuppressWarnings("rawtypes")
	public Class[] base(){
		Class[] types = new Class[columnas];
		for(int i = 0; i<columnas; i++){types[i]= java.lang.Object.class;}
		 return types;
	}
	public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"Codigo","Descripcion","Precio","Cantidad","Importe"}){
		 @SuppressWarnings("rawtypes")
			Class[] types = base();
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
			public boolean isCellEditable(int fila, int columna){ if(columna==3) return true; return false;}
	};
	JTable tabla = new JTable(modelo);
	public JScrollPane Scroll_Tabla = new JScrollPane(tabla);
     @SuppressWarnings({ "rawtypes", "unused" })
    private TableRowSorter trsfiltro;
     
     public void init_tabla_venta(){
	    	this.tabla.getColumnModel().getColumn(0).setMinWidth(90);	
	    	this.tabla.getColumnModel().getColumn(1).setMinWidth(410);
	    	this.tabla.getColumnModel().getColumn(2).setMinWidth(100);
	    	this.tabla.getColumnModel().getColumn(3).setMinWidth(93);
	    	this.tabla.getColumnModel().getColumn(4).setMinWidth(100);
	    	
			String comando="select 0,' ',0 ,0, 0" ;
			String basedatos="98",pintar="si";
			ObjTab.Obj_Refrescar(tabla,modelo, columnas, comando, basedatos,pintar,checkbox);
			modelo.setRowCount(0);
		}
				
	String establecimiento[] = Venta_Express.Combo_Establecimientos();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbEstablecimiento  = new JComboBox(establecimiento);
	
	String status[] = {"Vigente","Cancelado","Surtido","Abono","Liquidado"};
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmb_status = new JComboBox(status);
	
    JTextArea txtNota       = new Componentes().textArea(new JTextArea(), "Descripcion del Producto", 500);
	JScrollPane Notas       = new JScrollPane(txtNota);

	JToolBar menu_toolbar   = new JToolBar();
	JCButton btnBuscar      = new JCButton("Buscar"    ,"Filter-List-icon16.png"              ,"Azul"); 
	JCButton btnGuardar     = new JCButton("Guardar"   ,"Guardar.png"                         ,"Azul");
	JCButton btnDeshacer    = new JCButton("Deshacer"  ,"deshacer16.png"                      ,"Azul");
	JCButton btnImprimir    = new JCButton("Imprimir"   ,"imprimir-16.png"                     ,"Azul");
	
	JTextField txtFolioVendedor  = new Componentes().text(new JCTextField() ,"Folio Vendedor"                ,16    ,"String" );
	JTextField txtVendedor       = new Componentes().text(new JCTextField() ,"Vendedor"                      ,16    ,"String" );	
	JTextField txtFolio          = new Componentes().text(new JCTextField() ,"Folio"                         ,30    ,"Int"    );
	JTextField txtFolio_cliente  = new Componentes().text(new JCTextField() ,"Cliente"                       ,30    ,"Int"    );
	JTextField txtNombre_cliente = new Componentes().text(new JCTextField() ,"Nombre Del Cliente"            ,250   ,"String" );
	JTextField txtTotalImporte   = new Componentes().text(new JCTextField() , "Total"                        ,16    ,"String" );
	JTextField txtSaldo_anterior = new Componentes().text(new JCTextField() , "Saldo Anterior"               ,16    ,"String" );
	JTextField txtAbono          = new Componentes().text(new JCTextField() , "Abono"                        ,16    ,"Double" );
	JTextField txtSaldo          = new Componentes().text(new JCTextField() , "Saldo Nuevo"                  ,16    ,"String" );
	JTextField txtfolio_usuario  = new Componentes().text(new JCTextField() , "User"                         ,16    ,"String" );
	JTextField txtUsuario        = new Componentes().text(new JCTextField() , "Nombre"                       ,300   ,"String" );
	
	JRadioButton rbCliente_SCOI  = new JRadioButton("Cliente SCOI");
	JRadioButton rbCliente       = new JRadioButton("Cliente");
	ButtonGroup  grupo           = new ButtonGroup();
    
	public Cat_Liquidacion_Ventas_Express(){
		setSize(820,385);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Liquidaciones De Ventas Espress");
		setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/articulo-icono-9036-48.png"));
		panel.setBorder(BorderFactory.createTitledBorder("Seleccione La Venta Express y Capture El Abono"));
		
		this.menu_toolbar.add(btnBuscar   );
	    this.menu_toolbar.addSeparator(   );
	    this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.add(btnDeshacer );
		this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.add(btnGuardar  );
		this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.add(btnImprimir );
		
		this.menu_toolbar.setFloatable(false);
		
		this.grupo.add(rbCliente_SCOI);
   		this.grupo.add(rbCliente);
   		this.rbCliente.setSelected(true);
   		
		int x=10, y=25, width=115,height=20;
		panel.add(menu_toolbar).setBounds                                     (x      ,y        ,400     ,height  );
		panel.add(txtFolioVendedor).setBounds                                 (x+=400 ,y        ,60      ,height  );
		panel.add(txtVendedor).setBounds                                      (x+=60  ,y        ,335     ,height  );
		panel.add(txtFolio).setBounds                                         (x=10   ,y+=25    ,70      ,height  );
		panel.add(cmbEstablecimiento).setBounds                               (x+=70  ,y        ,width   ,height  );
		panel.add(txtFolio_cliente).setBounds                                 (x+=115 ,y        ,80      ,height  );
		panel.add(txtNombre_cliente).setBounds                                (x+=80  ,y        ,335     ,height  );
		panel.add(rbCliente).setBounds                                        (x+=335 ,y        ,58      ,height  );
		panel.add(rbCliente_SCOI).setBounds                                   (x+54   ,y        ,90      ,height  );	
		panel.add(new JLabel("Notas:")).setBounds                         	  (x=10   ,y+=20    ,width   ,height  );
		panel.add(Notas).setBounds                                   	      (x      ,y+=15    ,795     ,60      );
		panel.add(Scroll_Tabla).setBounds                                     (x=10   ,y+=65    ,795     ,150     );
		panel.add(cmb_status).setBounds                                       (x      ,y+=150   ,70      ,height  );
		panel.add(new JLabel("Total Venta:")).setBounds                  	  (x+=80  ,y        ,width   ,height  );
		panel.add(txtTotalImporte).setBounds                          	      (x+=60  ,y        ,width   ,20      );
		panel.add(new JLabel("Saldo:")).setBounds                        	  (x+=130 ,y        ,width   ,height  );
		panel.add(txtSaldo_anterior).setBounds                                (x+=30  ,y        ,width   ,20      );
		panel.add(new JLabel("Abono:$")).setBounds            	              (x+=150 ,y        ,width   ,height  );
		panel.add(txtAbono).setBounds                               	      (x+=40  ,y        ,width   ,20      );
		panel.add(new JLabel("Saldo Nuevo:")).setBounds        	              (x+=125 ,y        ,width   ,height  );
		panel.add(txtSaldo).setBounds                               	      (x+=65  ,y        ,width   ,20      );
		panel.add(txtfolio_usuario).setBounds                                 (x=10   ,y+=25    ,70      ,20      );
		panel.add(txtUsuario).setBounds                                       (x+=70  ,y        ,335     ,20      );		
		
		txtfolio_usuario.setText(usuario.getFolio()+"");
		txtUsuario.setText(usuario.getNombre_completo()+"");
		
		init_tabla_venta();
		panel(false);
	
		cont.add(panel);
		
		btnBuscar.addActionListener     (opBuscarVenta_Express );
		btnImprimir.addActionListener   (opImprimir_Reporte    );
		btnDeshacer.addActionListener   (opdeshacer            );
		btnGuardar.addActionListener    (opguardar     		   );
        txtAbono.addKeyListener         (opcalculo             );
        
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
        getRootPane().getActionMap().put("escape", new AbstractAction(){ public void actionPerformed(ActionEvent e){ btnDeshacer.doClick();} });

	    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put( KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), "foco");	    
	    getRootPane().getActionMap().put("foco", new AbstractAction(){ @Override public void actionPerformed(ActionEvent e) {btnBuscar.doClick(); }});
	    
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_G,Event.CTRL_MASK),"guardar");
        getRootPane().getActionMap().put("guardar", new AbstractAction(){ public void actionPerformed(ActionEvent e){btnGuardar.doClick();}});
	    
	}
	
	public void calculo_saldo() {
	    double abono=(txtAbono.getText().equals(""))?0:Double.valueOf(txtAbono.getText().toString());
	    double importe=(txtSaldo_anterior.getText().equals(""))?0:Double.valueOf(txtSaldo_anterior.getText().toString());
	    if(abono>importe) {
		  JOptionPane.showMessageDialog(null, "Está Intentando Pagar Más Del Valor Total Del Saldo De La Cuenta \nEl Importe Maximo Que Puede Abonar Es:$"+importe,"Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-icono-noes_usuario9131-64.png"));
		  txtAbono.setText(txtSaldo_anterior.getText());
		  return;		
	    }else {
		  txtSaldo.setText((importe-abono)+"");	
		  return;	
	    }
	}
	
	KeyListener opcalculo = new KeyListener() {
		public void keyTyped(KeyEvent e)    {calculo_saldo();}
		public void keyReleased(KeyEvent e) {calculo_saldo();}
		public void keyPressed(KeyEvent e)  {calculo_saldo();}
    };
    
	ActionListener opImprimir_Reporte = new ActionListener(){
  		public void actionPerformed(ActionEvent e){
  			if(txtFolio.getText().equals("")) {
  				JOptionPane.showMessageDialog(null, "Es Requerido Seleccione Un Folio Antes De Dar Click A Imprimir","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
  			}else {
	  			String basedatos="98";
	  			String vista_previa_reporte="no";
	  			int vista_previa_de_ventana=0;
	  			String comando="ventas_express_reporte_por_folio '"+txtFolio.getText().toString()+"'";
	  			String reporte = "Obj_Reporte_De_Venta_Express.jrxml";
	  			 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
  		   } 
  		}
  	};
  	
	ActionListener opBuscarVenta_Express = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		  new Cat_Filtro_Buscar_Venta_Express().setVisible(true);
		}
	};
	
	ActionListener opguardar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			calculo();
		 if(txtAbono.getText().equals("")||Float.valueOf(txtAbono.getText())==0){	
			JOptionPane.showMessageDialog(null, "Es Requerido Seleccione Una Venta Express y Teclee su Abono","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
			txtAbono.requestFocus();
		   return;
		 }else{	 
			 Venta_Express.setFolio(Integer.valueOf(txtFolio.getText().toString().trim()));	
			 Venta_Express.setDeuda_antes_de_abono(Double.valueOf(txtTotalImporte.getText().toString().trim()));	
			 Venta_Express.setAbono(Double.valueOf(txtAbono.getText().toString().trim()));	
			 Venta_Express.setSaldo(Double.valueOf(txtSaldo.getText().toString().trim()));	
			 Venta_Express.setFolio_usuario_abono(Integer.valueOf(txtfolio_usuario.getText().toString().trim()));	
			 Venta_Express.setGuardar_actualizar("A");
			 Venta_Express.setEstatus(cmb_status.getSelectedItem().toString().trim());
			  if(Venta_Express.GuardarLiquidacion().getFolio()>0){	
				    btnImprimir.setEnabled(true);
					btnImprimir.doClick();
					btnDeshacer.doClick();
					return;
				}else{
					JOptionPane.showMessageDialog(null,"Error Al Guardar Avise al Administrador del Sistema","Aviso",JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-icono-eliminar5252-64.png"));
					return;
				} 
		 }
		}
	};
	
	ActionListener opdeshacer = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			panel(false);
			txtNota.setText           ("");
			txtFolio.setText          ("");
			txtFolio_cliente.setText  ("");
			txtNombre_cliente.setText ("");
			txtTotalImporte.setText   ("");
			txtFolioVendedor.setText  ("");
			txtVendedor.setText       ("");
			txtAbono.setText          ("");
			txtSaldo_anterior.setText ("");
			txtSaldo.setText          ("");
			txtNota.setBackground(new Color(Integer.parseInt("EBEBEB",16)));
			modelo.setRowCount(0);
		   return;	
		}
	};
	
	public void panel(boolean boleano) {
		txtNota.setEditable(boleano);
		txtFolio.setEditable(boleano);
		txtFolioVendedor.setEditable(boleano);
		txtVendedor.setEditable(boleano);
		txtFolio_cliente.setEditable(boleano);
		txtNombre_cliente.setEditable(boleano);
		txtTotalImporte.setEditable(boleano);
		txtSaldo.setEditable(boleano);
		txtfolio_usuario.setEditable(boleano);
		txtUsuario.setEditable(boleano);
		txtSaldo_anterior.setEditable(boleano);
		cmb_status.setEnabled(boleano);
		cmbEstablecimiento.setEnabled(boleano);
		rbCliente.setEnabled(boleano);
		rbCliente_SCOI.setEnabled(boleano);
	};
	
	public void calculo() {
		for(int i=0;i<tabla.getRowCount();i++) {
			 double Importe=0;
			if(ObjTab.validacelda(tabla,"decimal", i,3)){
			  Importe=( Double.valueOf(tabla.getValueAt(i, 2).toString())* Double.valueOf(tabla.getValueAt(i, 3).toString()));	
			  tabla.setValueAt(Importe+"", i, 4);
			}
		}

	}

	//////////////////////TODO inicia filtro_Buscar Venta Express
		public class Cat_Filtro_Buscar_Venta_Express extends JDialog{
			Container contfb = getContentPane();
			JLayeredPane panelfb = new JLayeredPane();
			Connexion con = new Connexion();
			Obj_tabla ObjTab =new Obj_tabla();
			int columnasb = 16,checkbox=-1;
			public void init_tablafp(){
		    	this.tablab.getColumnModel().getColumn( 0).setMinWidth(50);
		    	this.tablab.getColumnModel().getColumn( 0).setMaxWidth(50);
		    	this.tablab.getColumnModel().getColumn( 1).setMinWidth(50);
		    	this.tablab.getColumnModel().getColumn( 1).setMaxWidth(50);
		    	this.tablab.getColumnModel().getColumn( 2).setMinWidth(250);
		    	this.tablab.getColumnModel().getColumn( 3).setMinWidth(50);		    	
		    	this.tablab.getColumnModel().getColumn( 3).setMaxWidth(50);
		    	this.tablab.getColumnModel().getColumn( 4).setMinWidth(250);
		    	this.tablab.getColumnModel().getColumn( 5).setMinWidth(50);
		    	this.tablab.getColumnModel().getColumn( 5).setMaxWidth(50);
		    	this.tablab.getColumnModel().getColumn( 6).setMinWidth(250);
		    	this.tablab.getColumnModel().getColumn( 7).setMinWidth(50);
		    	this.tablab.getColumnModel().getColumn( 7).setMaxWidth(50);
		    	this.tablab.getColumnModel().getColumn( 8).setMinWidth(250);
		    	this.tablab.getColumnModel().getColumn( 9).setMinWidth(50);
		    	this.tablab.getColumnModel().getColumn( 9).setMaxWidth(50);
		    	this.tablab.getColumnModel().getColumn(10).setMinWidth(130);
		    	this.tablab.getColumnModel().getColumn(11).setMinWidth(200);
		    	this.tablab.getColumnModel().getColumn(12).setMinWidth(80);
		    	this.tablab.getColumnModel().getColumn(12).setMaxWidth(80);
		    	this.tablab.getColumnModel().getColumn(13).setMinWidth(130);
		    	this.tablab.getColumnModel().getColumn(14).setMinWidth(100);
		    	this.tablab.getColumnModel().getColumn(15).setMinWidth(100);
		    	
				String comandob = "ventas_express_liquidaciones_filtro_select";
		    	String basedatos="98",pintar="si";
				ObjTab.Obj_Refrescar(tablab,modelob, columnasb, comandob, basedatos,pintar,checkbox);
		    }
			
			@SuppressWarnings("rawtypes")
			public Class[] base (){
				Class[] types = new Class[columnasb];
				for(int i = 0; i<columnasb; i++){types[i]= java.lang.Object.class;}
				 return types;
			}
			
			public DefaultTableModel modelob = new DefaultTableModel(null, new String[]{  "Folio","Folio C.", "Nombre Cliente", "Folio V.", "Vendedor", "Folio P.","Proveedor","Folio S.","Nombre Autorizó","Folio E.", "Establecimiento", "Notas", "Total Venta", "Fecha", "Tipo Cliente", "Estatus"}){
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
			     
			JTextField txtBuscarb  = new Componentes().textfiltro(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<", 500, "String",tablab,columnasb);
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Cat_Filtro_Buscar_Venta_Express(){
				this.setSize(825,400);
				this.setResizable(false);
				this.setLocationRelativeTo(null);
				this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				this.setModal(true);
				this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Filter-List-icon32.png"));
				this.panelfb.setBorder(BorderFactory.createTitledBorder("Selecione Un Registro Con Doble Click"));
				this.setTitle("Filtro De Ordenes De Pago");
				trsfiltro = new TableRowSorter(modelob); 
				tablab.setRowSorter(trsfiltro);
				this.panelfb.add(txtBuscarb).setBounds      (10 ,20 ,800 , 20 );
				this.panelfb.add(scroll_tablab).setBounds   (10 ,40 ,800 ,300 );
				this.init_tablafp();
				this.agregar(tablab);
				contfb.add(panelfb);
			}

			private void agregar(final JTable tbl) {
				tbl.addMouseListener(new MouseListener() {
					public void mouseReleased(MouseEvent e) {
				 	 if(e.getClickCount() == 1){funcion_agregar();}
					}
					public void mousePressed(MouseEvent e) {}
					public void mouseExited(MouseEvent e)  {}
					public void mouseEntered(MouseEvent e) {}
					public void mouseClicked(MouseEvent e) {}
				});
				
				tbl.addKeyListener(new KeyListener() {
					public void keyPressed(KeyEvent e)  {
						if(e.getKeyCode()==KeyEvent.VK_ENTER){
							funcion_agregar();	
						}
					}
					public void keyReleased(KeyEvent e)   {}
					public void keyTyped   (KeyEvent e)   {}
				});
		    }

			 public void funcion_agregar() {
			   int fila = tablab.getSelectedRow();
			    modelo.setRowCount(0);

	       		String[][] tablacompleta=Venta_Express.consulta_Liquidacion_abono_venta_express(Integer.valueOf(tablab.getValueAt(fila,0)+""));
	       	    Object[]   vectortabla = new Object[4];
	       		for(int i=0;i<tablacompleta.length;i++){
	       				for(int j=0;j<4;j++){
	       				  vectortabla[j] = tablacompleta[i][j].toString();
	       				}
	       				modelo.addRow(vectortabla);
	       			}
       		
              txtFolio.setText(tablacompleta[0][4].toString());
              cmbEstablecimiento.setSelectedItem(tablacompleta[0][6].toString().trim());
              if(tablacompleta[0][7].toString().equals("BMS")) {rbCliente.setSelected(true);}else{rbCliente_SCOI.setSelected(true);};
	              txtFolio_cliente.setText  (tablacompleta[0][8].toString().trim()        );
	              txtNombre_cliente.setText (tablacompleta[0][9].toString().trim()        );
	              txtNota.setText           (tablacompleta[0][10].toString().trim()       );
	              txtFolioVendedor.setText  (tablacompleta[0][11].toString().trim()       );
	              txtVendedor.setText       (tablacompleta[0][12].toString().trim()       );
	              txtTotalImporte.setText   (tablacompleta[0][13].toString().trim()       );
	              cmb_status.setSelectedItem(tablacompleta[0][19].toString().trim()       );
	              txtSaldo_anterior.setText (tablacompleta[0][21].toString().trim()       );             
	              calculo();
	              txtAbono.requestFocus();              
              if(tablacompleta[0][19].toString().trim().equals("Liquidado")){ btnGuardar.setEnabled(false); }else { btnGuardar.setEnabled(true); }
               dispose();
			 }
		    }
		
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Liquidacion_Ventas_Express().setVisible(true);
		}catch(Exception e){	}
	}
}
