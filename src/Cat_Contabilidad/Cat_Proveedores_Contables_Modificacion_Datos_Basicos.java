package Cat_Contabilidad;

import java.awt.Color;
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
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import Conexiones_SQL.Connexion;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Contabilidad.Obj_Proveedores;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextArea;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_tabla;

@SuppressWarnings("serial")
public class Cat_Proveedores_Contables_Modificacion_Datos_Basicos extends JFrame {
		Container cont = getContentPane();
		JLayeredPane campo = new JLayeredPane();
		Connexion con = new Connexion();
		Obj_tabla ObjTab= new Obj_tabla();
		Obj_Usuario usuario = new Obj_Usuario().LeerSession();
		Obj_Proveedores proveedores = new Obj_Proveedores();
		
		@SuppressWarnings("rawtypes")
		public Class[] tipos(){
			Class[] tip = new Class[Cantidad_Real_De_Columnas];
			for(int i =0; i<Cantidad_Real_De_Columnas; i++){
				if(i==checkboxindex-1){
					tip[i]=java.lang.Boolean.class;
				}else{
					tip[i]=java.lang.Object.class;
				}
			}
			return tip;
		}
		
		int Cantidad_Real_De_Columnas=12,checkboxindex=-1;
		public void init_tabla_principal(){
			this.tabla.getColumnModel().getColumn( 0).setMinWidth(140);
			this.tabla.getColumnModel().getColumn( 1).setMinWidth(50);
	    	this.tabla.getColumnModel().getColumn( 1).setMaxWidth(50);
	    	this.tabla.getColumnModel().getColumn( 2).setMinWidth(350);
	    	this.tabla.getColumnModel().getColumn( 3).setMinWidth(90);
	    	this.tabla.getColumnModel().getColumn( 4).setMinWidth(50);
	    	this.tabla.getColumnModel().getColumn( 4).setMaxWidth(50);
	    	this.tabla.getColumnModel().getColumn( 5).setMinWidth(100);
	    	this.tabla.getColumnModel().getColumn( 6).setMinWidth(100);
	    	this.tabla.getColumnModel().getColumn( 7).setMinWidth(100);
	    	this.tabla.getColumnModel().getColumn( 8).setMinWidth(300);
	    	this.tabla.getColumnModel().getColumn( 9).setMinWidth(300);
	    	this.tabla.getColumnModel().getColumn(10).setMinWidth(110);
	    	this.tabla.getColumnModel().getColumn(11).setMinWidth(90);
	    	
			String comando = "proveedores_BMS_lista_de_proveedores '"+cmb_tipo_proveedor.getSelectedItem().toString().trim()+"'";
			String basedatos="26",pintar="si";
			ObjTab.Obj_Refrescar(tabla,modelo, Cantidad_Real_De_Columnas, comando, basedatos,pintar,checkboxindex);
	    }
		
	 public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"Tipo Proveedor","Folio","Proveedor","Nombre Comercial","Plazo","Telefono","Telefono 2","Telefono 3","Notas","Domicilio", "RFC","Limite Credito"}){
		 @SuppressWarnings("rawtypes")
			Class[] types = tipos();
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {
	         return types[columnIndex];
	     }
			public boolean isCellEditable(int fila, int columna){
				if(columna==0)
					return true; return false;
			}
	    };
	    JTable tabla = new JTable(modelo);
		public JScrollPane scroll_tabla = new JScrollPane(tabla);
		
		String tipo[] =  proveedores.Combo_tipos_Todos();
		@SuppressWarnings({ "unchecked", "rawtypes" })
		JComboBox cmb_tipo_proveedor = new JComboBox(tipo);
		
		JTextField txtPedientes  = new Componentes().text(new JCTextField(), "Cant. Pendientes", 150, "String");
		JTextField txtFiltro     = new Componentes().textfiltro(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<",300 , "String",tabla,Cantidad_Real_De_Columnas );
		
		JCButton btnActualizar   = new JCButton("Actualizar"  ,"Actualizar.png"  ,"Azul" );	
		
		public Cat_Proveedores_Contables_Modificacion_Datos_Basicos()	{
			int ancho = Toolkit.getDefaultToolkit().getScreenSize().width;
			int alto = Toolkit.getDefaultToolkit().getScreenSize().height;
			setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds()); 
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/supplier.png"));
			this.setTitle("Clasificacion de Proveedores");
			this.campo.setBorder(BorderFactory.createTitledBorder("Seleccione El Proveedor y De Doble Click Para Editar"));
            int x=15 ,y=20 ,width=115 ,height=20;

			campo.add(txtFiltro).setBounds    (x      ,y     ,400      ,height  );
			campo.add(cmb_tipo_proveedor).setBounds   (x+=420 ,y     ,width    ,height  );
			campo.add(btnActualizar).setBounds(x+=130 ,y     ,width    ,height  );
			campo.add(scroll_tabla).setBounds (x=15   ,y+=25 , ancho-25,alto-125);
			cmb_tipo_proveedor.setSelectedItem("EN VALIDACION");
			agregar(tabla);
			
			init_tabla_principal();			
			cont.add(campo);
			this.btnActualizar.addActionListener(OpActualizar);
			this.cmb_tipo_proveedor.addActionListener(OpActualizar);
		}
		
	    ActionListener OpActualizar = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				init_tabla_principal();
			 }
	    };
	    
	    private void agregar(final JTable tbl) {
			tbl.addMouseListener(new MouseListener() {
				public void mouseReleased(MouseEvent e) {
			 	 if(e.getClickCount() == 1){
	    				int fila = tabla.getSelectedRow();
                     new Cat_Solicitud_De_Orden_De_Gasto_Validacion(tabla.getValueAt(fila, 0).toString(), tabla.getValueAt(fila, 1).toString(), tabla.getValueAt(fila, 2).toString() , Integer.valueOf(tabla.getValueAt(fila, 4).toString()),tabla.getValueAt(fila, 3).toString(),tabla.getValueAt(fila, 11).toString(), tabla.getValueAt(fila, 5).toString() ,tabla.getValueAt(fila, 6).toString() ,tabla.getValueAt(fila, 7).toString(),tabla.getValueAt(fila, 8).toString() ,tabla.getValueAt(fila, 9).toString(), tabla.getValueAt(fila, 10).toString()  ).setVisible(true);
			 	 }
				}
				public void mousePressed(MouseEvent e) {}
				public void mouseExited(MouseEvent e)  {}
				public void mouseEntered(MouseEvent e) {}
				public void mouseClicked(MouseEvent e) {}
			});
			tbl.addKeyListener(new KeyListener() {
				public void keyPressed(KeyEvent e)  {
					if(e.getKeyCode()==KeyEvent.VK_ENTER){
						int fila = tabla.getSelectedRow();
	                    new Cat_Solicitud_De_Orden_De_Gasto_Validacion(tabla.getValueAt(fila, 0).toString(), tabla.getValueAt(fila, 1).toString(), tabla.getValueAt(fila, 2).toString() , Integer.valueOf(tabla.getValueAt(fila, 4).toString()),tabla.getValueAt(fila, 3).toString(),tabla.getValueAt(fila, 11).toString(), tabla.getValueAt(fila, 5).toString() ,tabla.getValueAt(fila, 6).toString() ,tabla.getValueAt(fila, 7).toString(),tabla.getValueAt(fila, 8).toString() ,tabla.getValueAt(fila, 9).toString(), tabla.getValueAt(fila, 10).toString()  ).setVisible(true);
					}
				}
				public void keyReleased(KeyEvent e)   {}
				public void keyTyped   (KeyEvent e)   {}
			});
	    }
	    
///////////TODO  FILTRO DE  SOLICITUD SELECCIONADA
public class Cat_Solicitud_De_Orden_De_Gasto_Validacion extends JDialog{
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	Connexion con = new Connexion();
	JToolBar menu_toolbar       = new JToolBar();

	JTextField txtFolio_prv       = new Componentes().text(new JCTextField()  ,"Folio Prv"                 ,30   ,"String");
	JTextField txtProveedor       = new Componentes().text(new JCTextField()  ,"Proveedor"                 ,250  ,"String");
	JTextField txtPlazo           = new Componentes().text(new JCTextField()  ,"Plazo"                     ,5    ,"String");
	JTextField txtNombreComercial = new Componentes().text(new JCTextField()  ,"Nombre Comercial"          ,30   ,"String");
	JTextField txtRFC             = new Componentes().text(new JCTextField()  ,"RFC"                       ,30   ,"String");
	JTextField txtLimiteCredito   = new Componentes().text(new JCTextField()  ,"Limite De Credito"         ,90   ,"String");
	JTextField txtTelefono1       = new Componentes().text(new JCTextField()  ,"Telefono 1"                ,25   ,"Int"   );
	JTextField txtTelefono2       = new Componentes().text(new JCTextField()  ,"Telefono 2"                ,25   ,"Int"   );	
	JTextField txtTelefono3       = new Componentes().text(new JCTextField()  ,"Telefono 3"                ,25   ,"Int"   );	
    
	JTextArea txaUso              = new Componentes().textArea(new JCTextArea(), "Domicilio",600);
	JScrollPane Uso               = new JScrollPane(txaUso  );
    JTextArea txaNotas            = new Componentes().textArea(new JCTextArea(), "Notas", 1000  );
	JScrollPane notas             = new JScrollPane(txaNotas);
	
	JCButton btnAceptar   = new JCButton("Guardar"    ,"Guardar.png"    ,"Azul"); 
	JCButton btnModificar = new JCButton("Modificar"  ,"Modify.png"     ,"Azul"); 
	
	Border blackline, etched, raisedbevel, loweredbevel, empty;

	String tipo_normal[] =  proveedores.Combo_tipos();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbtipoproveedor = new JComboBox(tipo_normal);

  String guardar_actualizar="";
 public  Cat_Solicitud_De_Orden_De_Gasto_Validacion( String Tipo,String cod_prv ,String Proveedor ,int Plazo ,String NombreComercial ,String Limite_de_Credito ,String Telefono1 ,String Telefono2 ,String Telefono3 ,String Notas  ,String Domicilio ,String RFC ){
	    this.cont.add(panel);
		this.setSize(790,300);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setModal(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("Datos Del Proveedor");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/supplier.png"));
		this.blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
		this.panel.setBorder(BorderFactory.createTitledBorder(blackline,"Modificacion De Datos Proveedor"));

		this.menu_toolbar.add(btnAceptar  );
		this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.add(btnModificar);
		this.menu_toolbar.setFloatable(false);
 	 
		int x=20, y=20,width=122,height=20, sep=150;
		this.panel.add(menu_toolbar).setBounds                        (x         ,y      ,300     ,height );

		this.panel.add(txtFolio_prv).setBounds                        (x=20      ,y+=25  ,60      ,height );
		this.panel.add(txtProveedor).setBounds                        (x+=60     ,y      ,600     ,height );
		this.panel.add(new JLabel("Plazo:")).setBounds                (x+=610    ,y      ,width   ,height );
		this.panel.add(txtPlazo).setBounds                            (x+=35     ,y      ,42      ,height );	
		this.panel.add(new JLabel("Nom. Comercial:")).setBounds       (x=20      ,y+=30  ,width   ,height );
		this.panel.add(txtNombreComercial).setBounds                  (x+=80     ,y      ,300     ,height );
		this.panel.add(new JLabel("RFC:")).setBounds                  (x+=310    ,y      ,width   ,height );
		this.panel.add(txtRFC).setBounds                              (x+=40     ,y      ,150     ,height );
		this.panel.add(new JLabel("Limite Credito:")).setBounds       (x+=160    ,y      ,width   ,height );
		this.panel.add(txtLimiteCredito).setBounds                    (x+=70     ,y      ,87      ,height );
		this.panel.add(new JLabel("Domicilio:")).setBounds            (x=20      ,y+=20  ,width   ,height );
		this.panel.add(Uso).setBounds                                 (x         ,y+=15  ,747     ,40     );
		this.panel.add(new JLabel("Notas:")).setBounds                (x         ,y+=45  ,width   ,height );
		this.panel.add(notas).setBounds                               (x         ,y+=15  ,747     ,40     );
		
		this.panel.add(new JLabel("Telefonos:")).setBounds            (x         ,y+=55  ,width=142,height);
		this.panel.add(txtTelefono1).setBounds                        (x+=60     ,y      ,width   ,height );
		this.panel.add(txtTelefono2).setBounds                        (x+=sep    ,y      ,width   ,height );
		this.panel.add(txtTelefono3).setBounds                        (x+=sep    ,y      ,width   ,height );
		this.panel.add(cmbtipoproveedor).setBounds                    (x+=sep    ,y      ,width   ,height );

	    this.txtFolio_prv.setText(cod_prv               );
	    this.txtProveedor.setText(Proveedor             );
	    this.txtPlazo.setText(Plazo+""                  );
	    this.txtNombreComercial.setText(NombreComercial );
	    this.txtRFC.setText(RFC                         );
	    this.txtLimiteCredito.setText(Limite_de_Credito );
	    this.txtTelefono1.setText(Telefono1             );
	    this.txtTelefono2.setText(Telefono2             );
	    this.txtTelefono3.setText(Telefono3             );
	    this.txaUso.setText(Domicilio                   );
	    this.txaNotas.setText(Notas                     );
	    this.cmbtipoproveedor.setSelectedItem(Tipo      );
	    
	    this.txtFolio_prv.setEditable(false       );
	    this.txtProveedor.setEditable(false       );
	    this.txtPlazo.setEditable(false           );
	    this.txtNombreComercial.setEditable(false );
	    this.txtRFC.setEditable(false             );
	    this.txtLimiteCredito.setEditable(false   );
	    this.txtTelefono1.setEditable(false       );
	    this.txtTelefono2.setEditable(false       );
	    this.txtTelefono3.setEditable(false       );
	    this.txaUso.setEditable(false             );
	    this.txaNotas.setEditable(false           );
	    this.cmbtipoproveedor.setEnabled(false    );
	    this.btnAceptar.setEnabled(false          );

		this.btnAceptar.addActionListener(opaceptar);
		this.btnModificar.addActionListener(opamodificar);
  }
	
	ActionListener opaceptar = new ActionListener() {
    	public void actionPerformed(ActionEvent arg0) {
    		proveedores.setCod_prv(txtFolio_prv.getText().toString());
    		proveedores.setNombre_comercial(txtNombreComercial.getText().toString());
    		proveedores.setNotas(txaNotas.getText().toString());
    		proveedores.setTelefono1(txtTelefono1.getText().toString());
    		proveedores.setTelefono2(txtTelefono2.getText().toString());
    		proveedores.setTelefono3(txtTelefono3.getText().toString());
     		proveedores.setTipo_proveedor(cmbtipoproveedor.getSelectedItem().toString());
     		
    		if(proveedores.Guardar_Proveedor_BMS()){
    			dispose();
    			init_tabla_principal();
    		}else{
    			JOptionPane.showMessageDialog(null, "Error Al Actualizar", "Avise al Administrador Del Sistema", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
    		}
    		
		}
	};
	
	ActionListener opamodificar = new ActionListener() {
    	public void actionPerformed(ActionEvent arg0) {
    	   txtNombreComercial.setEditable(true );
    	   txtTelefono1.setEditable(true       );
    	   txtTelefono2.setEditable(true       );
    	   txtTelefono3.setEditable(true       );
    	   txaNotas.setEditable(true           );
    	   cmbtipoproveedor.setEnabled(true    );
    	   btnAceptar.setEnabled(true          );
    	   txaNotas.setBackground(new Color(Integer.parseInt("FFFFFF",16)));
		}
	};


}
		  	
		
		public static void main(String args[]){
			try{
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				new Cat_Proveedores_Contables_Modificacion_Datos_Basicos().setVisible(true);
			}catch(Exception e){	}
		}
	}

