package Cat_Contabilidad;

import java.awt.Container;
import java.awt.Event;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Conexiones_SQL.BuscarSQL;
import Obj_Contabilidad.Obj_Alta_Proveedores_Polizas;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_tabla;

@SuppressWarnings({ "serial", "unchecked" })
public class Cat_Proveedores extends JDialog{
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	Obj_tabla ObjTab                    = new Obj_tabla                ();		
	int columnas = 4,checkbox=-1;
	public void init_tablaordenes(){
		this.tabla.getColumnModel().getColumn(0).setMaxWidth(65);
		this.tabla.getColumnModel().getColumn(0).setMinWidth(65);		
		this.tabla.getColumnModel().getColumn(1).setMaxWidth(300);
		this.tabla.getColumnModel().getColumn(1).setMinWidth(300);
		this.tabla.getColumnModel().getColumn(2).setMaxWidth(40);
		this.tabla.getColumnModel().getColumn(2).setMinWidth(40);
		this.tabla.getColumnModel().getColumn(3).setMinWidth(250);
		
		String comandof="select folio_proveedor ,nombre+' '+ap_paterno+' '+ap_materno as empleado ,plazo  ,direccion from tb_proveedores where status = 1";
		String basedatos="26",pintar="si";
		modelo.setRowCount(0);
		ObjTab.Obj_Refrescar(tabla,modelo, columnas, comandof, basedatos,pintar,checkbox);
    }
	
	@SuppressWarnings("rawtypes")
	public Class[] baseOr (){
		Class[] types = new Class[columnas];
		for(int i = 0; i<columnas; i++){  
			if(i==0){
				types[i]=java.lang.Boolean.class;
			}else{
				types[i]=java.lang.Object.class;
			}
		}
		 return types;
	}
	
	public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"Folio","Proveedor","Plazo","Domicilio"}){
		 @SuppressWarnings("rawtypes")
			Class[] types = baseOr();
			@SuppressWarnings({ "rawtypes" })
			public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
			public boolean isCellEditable(int fila, int columnaspo){return false;}
	};
	
	JTable tabla = new JTable(modelo);
	JScrollPane panelScroll = new JScrollPane(tabla);
	
	@SuppressWarnings("rawtypes")
	private TableRowSorter trsfiltro;
	
	JCButton btnNuevo     = new JCButton("Nuevo"       ,"Nuevo.png"            ,"Azul"); 
	JCButton btnGuardar   = new JCButton("Guardar"     ,"Guardar.png"          ,"Azul");
	JCButton btnModificar = new JCButton("Modificar"   ,"Modify.png"           ,"Azul"); 
	JCButton btnDeshacer  = new JCButton("Deshacer"    ,"deshacer16.png"       ,"Azul");
	
	JTextField txtFolioProveedor = new Componentes().text(new JCTextField(), "Folio Del Proveedor",20, "Int"   );
	JTextField txtNombre         = new Componentes().text(new JCTextField(), "Nombre de Empleado" ,50, "String");
	JTextField txtApPaterno      = new Componentes().text(new JCTextField(), "Apellido Paterno"   ,50, "String");
	JTextField txtApMaterno      = new Componentes().text(new JCTextField(), "Apellido Materno"   ,50, "String");
	JTextField txtDomicilio      = new Componentes().text(new JCTextField(), "Domicilio"          ,50, "String");
	JTextField txtTelefono       = new Componentes().text(new JCTextField(), "Telefono"           ,10, "Int"   );
	JTextField txtPlazo          = new Componentes().text(new JCTextField(), "Plazo"              ,2 , "Int"   );
	JTextField txtBuscar         = new Componentes().textfiltro(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<", 500, "String",tabla,columnas );
	
	Border blackline;
	JToolBar menu_toolbar        = new JToolBar();
	String GuardarActualizar="";
	@SuppressWarnings("rawtypes")
	public Cat_Proveedores(){
		this.setModal(true);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("imagen/tarjeta-de-informacion-del-usuario-icono-7370-16.png"));
		this.setTitle("Alta Proveedores");
		this.blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
		this.panel.setBorder(BorderFactory.createTitledBorder(blackline, "Filtrar"));
		this.setSize(700,515);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
	    this.menu_toolbar.add(btnNuevo    );
	    this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.addSeparator(   );
	    this.menu_toolbar.add(btnModificar);
	    this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.addSeparator(   );
	    this.menu_toolbar.add(btnGuardar  );
	    this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.addSeparator(   );
	    this.menu_toolbar.add(btnDeshacer );
		this.menu_toolbar.setFloatable(false);

		trsfiltro = new TableRowSorter(modelo); 
		tabla.setRowSorter(trsfiltro);  
		
		int x=10; int y=15; int ancho=110;
		panel.add(menu_toolbar).setBounds               (x     ,y      ,500      ,20 );	
		panel.add(new JLabel("Nombre: ")).setBounds     (x     ,y+=25  ,ancho    ,20 );
		panel.add(txtNombre).setBounds                  (x+65  ,y      ,ancho+20 ,20 );
		panel.add(new JLabel("Cod. Prov:")).setBounds   (x+210 ,y      ,ancho    ,20 );
		panel.add(txtFolioProveedor).setBounds          (x+265 ,y      ,ancho+20 ,20 );
		panel.add(new JLabel("Plazo:")).setBounds       (x+405 ,y      ,ancho    ,20 );
		panel.add(txtPlazo).setBounds                   (x+435 ,y      ,ancho    ,20 );
		
		panel.add(new JLabel("Ap. Paterno: ")).setBounds(x     ,y+=25  ,ancho    ,20 );
		panel.add(txtApPaterno).setBounds               (x+65  ,y      ,ancho+20 ,20 );
		panel.add(new JLabel("Telefono: ")).setBounds   (x+210 ,y      ,ancho    ,20 );
		panel.add(txtTelefono).setBounds                (x+265 ,y      ,ancho+20 ,20 );
		
		panel.add(new JLabel("Ap. Materno: ")).setBounds(x     ,y+=25  ,ancho    ,20 );
		panel.add(txtApMaterno).setBounds               (x+65  ,y      ,ancho+20 ,20 );
		panel.add(new JLabel("Domicilio: ")).setBounds  (x+210 ,y      ,ancho    ,20 );
		panel.add(txtDomicilio).setBounds               (x+265 ,y      ,ancho+170,20 );
		panel.add(txtBuscar).setBounds                  (10    ,y+=25  ,675      ,20 );
		panel.add(panelScroll).setBounds                (10    ,y+=20  ,675      ,340);
		
		cont.add(panel);
		
		txtFolioProveedor.setEditable(false);
		btnGuardar.setEnabled(false);
		btnModificar.setEnabled(false);
		
		txtNombre.addKeyListener(opReduccion);
		txtApPaterno.addKeyListener(opReduccion);
		txtApMaterno.addKeyListener(opReduccion);
		
		txtNombre.addActionListener(opSaltar);
		txtApPaterno.addActionListener(opSaltar);
		txtApMaterno.addActionListener(opSaltar);
		txtTelefono.addActionListener(opSaltar);
		txtDomicilio.addActionListener(opSaltar);
		
		btnNuevo.addActionListener(opNuevo);
		btnGuardar.addActionListener(opGuardar);
		btnModificar.addActionListener(opModificar);
		btnDeshacer.addActionListener(deshacer);
		
		agregar(tabla);
		
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put( KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), "saltar");
		getRootPane().getActionMap().put("saltar", new AbstractAction(){public void actionPerformed(ActionEvent e){	cambiarFoco();	}});
		
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
        getRootPane().getActionMap().put("escape", new AbstractAction(){public void actionPerformed(ActionEvent e){  btnDeshacer.doClick(); } });

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_G,Event.CTRL_MASK),"guardar");
        getRootPane().getActionMap().put("guardar", new AbstractAction(){public void actionPerformed(ActionEvent e){ btnGuardar.doClick(); } });

	    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0), "guardar");
	      getRootPane().getActionMap().put("guardar", new AbstractAction(){public void actionPerformed(ActionEvent e){ btnGuardar.doClick(); } });
	             
	    this.addWindowListener(new WindowAdapter() {  public void windowOpened( WindowEvent e ){ txtBuscar.requestFocus();  }  });   
	    
		limpiarPantalla(false);		
		init_tablaordenes();
	}

	  private void agregar(final JTable tbl) {
		  tbl.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e){
			 if(e.getClickCount() == 1){agregar(); }
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
					agregar();	
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {}
			@Override
			public void keyTyped(KeyEvent e)    {}
		});
    }
	
	private void agregar() {
		trsfiltro.setRowFilter(RowFilter.regexFilter("", 1));
		limpiarPantalla(false);
		
		int fila = tabla.getSelectedRow();
		if (fila<0) { fila=0;}
		
		int folio =  Integer.valueOf(tabla.getValueAt(fila, 0).toString().trim());
		btnNuevo.setEnabled(true);
		btnModificar.setEnabled(true);
		txtNombre.requestFocus();
		trsfiltro.removeRowSorterListener(tabla);
		try {
			Obj_Alta_Proveedores_Polizas prv = new Obj_Alta_Proveedores_Polizas().buscar(folio);
			
			txtFolioProveedor.setText(prv.getFolio_proveedor()+"");
			txtNombre.setText        (prv.getNombre()            );
			txtApPaterno.setText     (prv.getAp_paterno()        );
			txtApMaterno.setText     (prv.getAp_materno()        );
			txtDomicilio.setText     (prv.getDireccion()         );
			txtTelefono.setText      (prv.getTelefono()          );
			txtPlazo.setText         (prv.getPlazo()+""          );
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null,"Error Al Guardar Avise al Administrador del Sistema \n"+e1,"Aviso",JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-icono-eliminar5252-64.png"));
		} 		
        };
        
	int contador=0;
	
	ActionListener opSaltar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			cambiarFoco();
		}
	};
	
	public void cambiarFoco(){
		
					if(txtFolioProveedor.getText().equals("")){
						txtNombre.requestFocus();
					}else{
							if(txtNombre.getText().equals("")){		
								txtNombre.requestFocus();
							}else{
									txtApPaterno.requestFocus();
									if(txtApPaterno.getText().equals("")){
											txtApPaterno.requestFocus();
									}else{
											txtApMaterno.requestFocus();
											if(txtApMaterno.getText().equals("")){
													txtApMaterno.requestFocus();
											}else{
													txtTelefono.requestFocus();
													if(txtTelefono.getText().equals("")){
															txtTelefono.requestFocus();
													}else{
															txtDomicilio.requestFocus();
															if(txtDomicilio.getText().equals("")){
																	txtDomicilio.requestFocus();
															}else{
																btnGuardar.requestFocus();
															}
													}
											}
							}
					}
			}
	}
	
	ActionListener opNuevo = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			try {
				limpiarPantalla(true);
				txtFolioProveedor.setText( new BuscarSQL().folio_siguiente(47+""));
				btnNuevo.setEnabled(false);
				btnGuardar.setEnabled(true);
				btnModificar.setEnabled(false);
				txtNombre.requestFocus();
				
				trsfiltro.addRowSorterListener(tabla);
				
				contador=0;
				GuardarActualizar="N";
				
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
		}
	};
	
	ActionListener deshacer = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			    GuardarActualizar="";
				limpiarPantalla(false);
				btnNuevo.setEnabled(true);
				btnGuardar.setEnabled(false);
				btnModificar.setEnabled(false);
				init_tablaordenes();
				txtBuscar.requestFocus();
		}
	};
	
	ActionListener opModificar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			GuardarActualizar="M";
			txtNombre.setEditable(true);
			txtApPaterno.setEditable(true);
			txtApMaterno.setEditable(true);
			txtDomicilio.setEditable(true);
			txtTelefono.setEditable(true);
			txtPlazo.setEditable(true);
			btnGuardar.setEnabled(true);
			btnModificar.setEnabled(false);
		}
	};
	
	public void limpiarPantalla(boolean booleano){
		txtBuscar.setText("");
		trsfiltro.setRowFilter(RowFilter.regexFilter("", 1));
		
		txtFolioProveedor.setText("");
		txtNombre.setText("");
		txtApPaterno.setText("");
		txtApMaterno.setText("");
		txtDomicilio.setText("");
		txtTelefono.setText("");
		txtPlazo.setText("");
		
		txtNombre.setEditable(booleano);
		txtApPaterno.setEditable(booleano);
		txtApMaterno.setEditable(booleano);
		txtDomicilio.setEditable(booleano);
		txtTelefono.setEditable(booleano);
		txtPlazo.setEditable(booleano);
	}
	
	
	
	ActionListener opGuardar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
				if(validaCampos() != ""){
						txtNombre.requestFocus();
						JOptionPane.showMessageDialog(null, "Los Siguientes Campos Son Requeridos:\n"+validaCampos(), "Aviso", JOptionPane.OK_OPTION,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
						return;
				}else{
					
									Obj_Alta_Proveedores_Polizas prv = new Obj_Alta_Proveedores_Polizas();
									prv.setFolio_proveedor(Integer.valueOf(txtFolioProveedor.getText().toString().trim()));
									prv.setNombre(txtNombre.getText().toUpperCase().trim());
									prv.setAp_paterno(txtApPaterno.getText().toUpperCase().trim());
									prv.setAp_materno(txtApMaterno.getText().toUpperCase().trim());
									prv.setDireccion(txtDomicilio.getText().toUpperCase().trim());
									prv.setTelefono(txtTelefono.getText().trim());
									prv.setPlazo( txtPlazo.getText().trim() );
									prv.setGuardarActualizar(GuardarActualizar);
							
								if(prv.guardar()){
									limpiarPantalla(false);
									btnNuevo.setEnabled(true);
									btnGuardar.setEnabled(false);
									btnModificar.setEnabled(false);
									
									modelo.setRowCount(0);	
									init_tablaordenes();
									txtNombre.requestFocus();
									contador=0;
									GuardarActualizar = "";
									JOptionPane.showMessageDialog(null, "Se Guardo Correctamente", "Aviso", JOptionPane.OK_OPTION,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
									return;
								}else{
									JOptionPane.showMessageDialog(null,"Error Al Guardar Avise al Administrador del Sistema","Aviso",JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-icono-eliminar5252-64.png"));
									return;
								}
						
				}
		}
	};

	
	public String validaCampos(){
		String error="";
		if(txtNombre.getText().equals("")) 		error+="Nombre\n";
		if(txtApPaterno.getText().equals("")) 	error+="Ap Paterno\n";
		if(txtApMaterno.getText().equals("")) 	error+="Ap Materno\n";
		
		return error;
	}
	
	KeyAdapter opReduccion = new KeyAdapter() { 
		public void keyReleased(final KeyEvent e) {
			String nombre = "";

			switch(txtApPaterno.getText().toUpperCase().trim().length()){
				case 0:	nombre = txtNombre.getText().toUpperCase().trim()+" "+txtApMaterno.getText().toUpperCase().trim(); break;
				default:nombre = txtNombre.getText().toUpperCase().trim()+" "+txtApPaterno.getText().toUpperCase().trim()+" "+txtApMaterno.getText().toUpperCase().trim(); break;
			}
			trsfiltro.setRowFilter(RowFilter.regexFilter(nombre.trim(), 1));
		} 
	};
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Proveedores().setVisible(true);
		}catch(Exception e){
			System.err.println("Error :"+ e.getMessage());
		}
	}
}
