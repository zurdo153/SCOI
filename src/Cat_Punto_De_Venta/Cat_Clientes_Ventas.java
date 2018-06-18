package Cat_Punto_De_Venta;

import java.awt.Container;
import java.awt.Event;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Connexion;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_tabla;
import Obj_Punto_De_Venta.Obj_Clientes_Ventas;

@SuppressWarnings("serial")
public class Cat_Clientes_Ventas extends JFrame{
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	Connexion con = new Connexion();
	Obj_Clientes_Ventas clientes = new Obj_Clientes_Ventas();
	
	int columnas = 7,checkbox=-1;
	public void init_tabla(){
    	this.tabla.getColumnModel().getColumn(0).setMinWidth(60 );	
    	this.tabla.getColumnModel().getColumn(0).setMaxWidth(60 );	
    	this.tabla.getColumnModel().getColumn(1).setMinWidth(170);
    	this.tabla.getColumnModel().getColumn(2).setMinWidth(150);
    	this.tabla.getColumnModel().getColumn(3).setMinWidth(150);
    	this.tabla.getColumnModel().getColumn(4).setMinWidth(155);
    	this.tabla.getColumnModel().getColumn(5).setMinWidth(70 );
    	
		String comando="exec ventas_express_clientes_select ";
		String basedatos="26",pintar="si";
		new Obj_tabla().Obj_Refrescar(tabla,modelo, columnas, comando, basedatos,pintar,checkbox);
    }
	
	 public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"Folio","Nombre","Ap. Paterno","Ap. Materno","Domicilio","Telefono","Estatus"}){
		 @SuppressWarnings("rawtypes")
			Class[] types = new Class[]{
					java.lang.Object.class,
					java.lang.Object.class,
					java.lang.Object.class,
					java.lang.Object.class,
					java.lang.Object.class,
					java.lang.Object.class,
					java.lang.Object.class,
			};
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {
	         return types[columnIndex];
	     }
			public boolean isCellEditable(int fila, int columna){
					return false;
			}
	    };
	
	    JTable tabla = new JTable(modelo);
		public JScrollPane scroll_tabla = new JScrollPane(tabla);
		JToolBar menu_toolbar   = new JToolBar();
		
	@SuppressWarnings("rawtypes")
	private TableRowSorter trsfiltro;
	
	JTextField txtFiltro      = new Componentes().textfiltro(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<",500 , "String",tabla,columnas );
	JTextField txtFolio       = new Componentes().text(new JCTextField(), "Folio"            ,9    ,"Int"    );
	JTextField txtNombre      = new Componentes().textfiltro(new JCTextField(), "Nombre"           ,100  ,"String",tabla,columnas );
	JTextField txtAp_Paterno  = new Componentes().textfiltro(new JCTextField(), "Apellido Paterno",100  ,"String",tabla,columnas );
	JTextField txtAp_Materno  = new Componentes().textfiltro(new JCTextField(), "Apellido Materno" ,100  ,"String",tabla,columnas );
	JTextField txtDomicilio   = new Componentes().text(new JCTextField(), "Domicilio"        ,100  ,"String" );	
	JTextField txtTelefono    = new Componentes().textfiltro(new JCTextField(), "Telefono"         ,12   ,"Int" ,tabla,columnas );
	
	String status[] = {"Vigente","Cancelado"};
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmb_status = new JComboBox(status);
	
	JCButton btnNuevo       = new JCButton("Nuevo"     ,"Nuevo.png"                           ,"Azul");
	JCButton btnGuardar     = new JCButton("Guardar"   ,"Guardar.png"                         ,"Azul");
	JCButton btnDeshacer    = new JCButton("Deshacer"  ,"deshacer16.png"                      ,"Azul");
	JCButton btnEditar      = new JCButton("Editar"    ,"editara.png"                         ,"Azul");
	
    String guardar_actualizar="";
    
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Cat_Clientes_Ventas(){
		this.setSize(785,400);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/contrato-de-acuerdo-de-acuerdo-de-la-mano-encuentros-socio-icono-7428-16.png"));
		this.panel.setBorder(BorderFactory.createTitledBorder("Clientes Ventas Express"));
		this.setTitle("Clientes Ventas");
		
		trsfiltro = new TableRowSorter(modelo); 
		tabla.setRowSorter(trsfiltro);
		
		this.menu_toolbar.add(btnNuevo    );
	    this.menu_toolbar.addSeparator(   );
	    this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.add(btnEditar   );
	    this.menu_toolbar.addSeparator(   );
	    this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.add(btnDeshacer );
		this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.add(btnGuardar  );
		
		this.menu_toolbar.setFloatable(false);
		
		int x = 10, y=30, w=150,l=20;
		this.panel.add(menu_toolbar).setBounds         (x      ,y     ,500 ,l  );
		panel.add(cmb_status).setBounds                (x+680  ,y     ,80  ,l  );
		panel.add(new JLabel("Folio:")).setBounds      (x      ,y+=30 ,w   ,l  );
		panel.add(txtFolio).setBounds                  (x+=30  ,y     ,80  ,l  );
		panel.add(new JLabel("Nombre:")).setBounds     (x+=85  ,y     ,w   ,l  );
		panel.add(txtNombre).setBounds                 (x+=45  ,y     ,w   ,l  );
		panel.add(new JLabel("Ap.Paterno:")).setBounds (x+=160 ,y     ,w   ,l  );
		panel.add(txtAp_Paterno).setBounds             (x+=65  ,y     ,w   ,l  );
		panel.add(new JLabel("Ap.Materno:")).setBounds (x+=160 ,y     ,w   ,l  );
		panel.add(txtAp_Materno).setBounds             (x+=65  ,y     ,w   ,l  );
		panel.add(new JLabel("Domicilio:")).setBounds  (x=10   ,y+=30 ,w   ,l  );
		panel.add(txtDomicilio).setBounds              (x+=50  ,y     ,550 ,l  );
		panel.add(new JLabel("Telefono:")).setBounds   (x+=560 ,y     ,w   ,l  );
		panel.add(txtTelefono).setBounds               (x+=50  ,y     ,100 ,l  );
		panel.add(txtFiltro).setBounds                 (x=10   ,y+=30 ,760 ,l  );
		panel.add(scroll_tabla).setBounds              (x      ,y+=20 ,760 ,220);
		
		init_tabla();
		panel(false);
		txtFolio.setEditable(false);
		agregar(tabla);
		
		txtFolio.requestFocus();
//	----------------------------------------------------------------------------------------------------------------	
		btnGuardar.addActionListener(guardar);
		btnDeshacer.addActionListener(deshacer);
		btnNuevo.addActionListener(nuevo);
		btnEditar.addActionListener(editar);
		btnEditar.setEnabled(false);
		
		cont.add(panel);
		
		   getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
	        getRootPane().getActionMap().put("escape", new AbstractAction(){ public void actionPerformed(ActionEvent e){ btnDeshacer.doClick();} });

	        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_N,Event.CTRL_MASK),"nuevo");
	        getRootPane().getActionMap().put("nuevo", new AbstractAction(){ public void actionPerformed(ActionEvent e){btnNuevo.doClick();}});
	        
	        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_G,Event.CTRL_MASK),"guardar");
	        getRootPane().getActionMap().put("guardar", new AbstractAction(){ public void actionPerformed(ActionEvent e){btnGuardar.doClick();}});
	
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
	   int fila = tabla.getSelectedRow();
	    txtFolio.setText            (tabla.getValueAt(fila,0).toString());
		txtNombre.setText           (tabla.getValueAt(fila,1).toString());
		txtAp_Paterno.setText      (tabla.getValueAt(fila,2).toString());
		txtAp_Materno.setText       (tabla.getValueAt(fila,3).toString());
		txtDomicilio.setText        (tabla.getValueAt(fila,4).toString());
		txtTelefono.setText         (tabla.getValueAt(fila,5).toString());
		cmb_status.setSelectedItem  (tabla.getValueAt(fila,6).toString());
		panel(false);
		btnEditar.setEnabled(true);
    }
	
    ActionListener guardar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(validaCampos()!="") {
				JOptionPane.showMessageDialog(null, "Los Siguientes Campos Son Requeridos:\n "+validaCampos(), "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}else{	
				 clientes.setFolio      (Integer.valueOf(txtFolio.getText()));
			     clientes.setNombre     (txtNombre.getText().toUpperCase().trim());    
			     clientes.setAp_paterno (txtAp_Paterno.getText().toUpperCase().trim());  
			     clientes.setAp_materno (txtAp_Materno.getText().toUpperCase().trim());  
			     clientes.setDomicilio  (txtDomicilio.getText().toUpperCase().trim());  
			     clientes.setTelefono   (txtTelefono.getText());  
			     clientes.setEstatus    (cmb_status.getSelectedItem().toString());  
			     clientes.setGuardar_actualizar(guardar_actualizar);
				if(clientes.GuardarActualizar().getFolio()>0){ 
							init_tabla();
							panelLimpiar();
							panel(false);
							txtFiltro.requestFocus();
					JOptionPane.showMessageDialog(null,"El Registró se Guardó de Forma Segura","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/aplicara-el-dialogo-icono-6256-32.png"));
				 }else{
						JOptionPane.showMessageDialog(null,"Error Al Guarda el Puesto Avise al Administrador del Sistema","Aviso",JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-icono-eliminar5252-64.png"));
						}
			}			
		}
	};
	
	
	private String validaCampos(){
		String error="";
		if(txtFolio.getText().equals("")) 			error+= "Folio\n";
		if(txtNombre.getText().equals("")) 			error+= "Nombre\n";
		if(txtAp_Paterno.getText().equals("")) 			error+= "Apellido Paterno\n";
		if(txtAp_Materno.getText().equals("")) 			error+= "Apellido Materno\n";
		if(txtDomicilio.getText().equals("")) 			error+= "Domicilio\n";
		if(txtTelefono.getText().equals("")) 			error+= "Telefono\n";
		return error;
	}
	
	ActionListener nuevo = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			panelLimpiar();
			String folio = "";
			guardar_actualizar="N";
			try {folio= new BuscarSQL().folio_siguiente(66+"");} catch (SQLException e1) {	e1.printStackTrace();}
			txtFolio.setText(folio+"");
			panel(true);
			txtFolio.setEditable(false);
			txtNombre.requestFocus();
		}
	};
	
	ActionListener deshacer = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			panelLimpiar();
			panel(false);
			btnNuevo.setEnabled(true);
			btnEditar.setEnabled(false);
		}
	};
	
	ActionListener editar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			panel(true);
			btnEditar.setEnabled(false);
			btnNuevo.setEnabled(true);
			btnGuardar.setEnabled(true);
			guardar_actualizar="M";
		}		
	};
	
	public void panel(boolean boooleano){	
		txtNombre.setEditable      (boooleano);
		txtAp_Paterno.setEditable (boooleano);
		txtAp_Materno.setEditable  (boooleano);
		txtDomicilio.setEditable   (boooleano);
		txtTelefono.setEditable    (boooleano);
		cmb_status.setEnabled      (boooleano);
	}		
	
	public void panelLimpiar(){	
		txtFolio.setText("");
		txtNombre.setText("");
		txtAp_Paterno.setText("");
		txtAp_Materno.setText("");
		txtDomicilio.setText("");
		txtTelefono.setText("");
		txtFiltro.setText("");
		cmb_status.setSelectedIndex(0);
		guardar_actualizar="";
	}
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Clientes_Ventas().setVisible(true);
		}catch(Exception e){	}
	}
	
}