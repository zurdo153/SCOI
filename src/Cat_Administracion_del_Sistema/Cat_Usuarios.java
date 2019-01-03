package Cat_Administracion_del_Sistema;

import java.awt.Container;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.tree.DefaultMutableTreeNode;

import Conexiones_SQL.Connexion;
import Obj_Administracion_del_Sistema.Obj_CheckBoxNode;
import Obj_Administracion_del_Sistema.Obj_CheckBoxNodeEditor;
import Obj_Administracion_del_Sistema.Obj_CheckBoxNodeRenderer;
import Obj_Administracion_del_Sistema.Obj_MD5;
import Obj_Administracion_del_Sistema.Obj_NombreVector;
import Obj_Administracion_del_Sistema.Obj_SubMenus;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_Filtro_Dinamico;
import Obj_Principal.Obj_tabla;


@SuppressWarnings("serial")
public class Cat_Usuarios extends JFrame{
	
	Container cont = getContentPane();
	JLayeredPane campo = new JLayeredPane();
	
	Obj_Usuario user = new Obj_Usuario().LeerSession();
	// MENU PRICIPAL ADMINISTRACION DEL SISTEMA  (1) select nombre from tb_submenu where menu_principal = 1 order by nombre asc
	String[] Sub_Administracion_del_Sistema = new Obj_Administracion_del_Sistema.Obj_SubMenus().Relacion_de_SubMenus(1);
	Obj_CheckBoxNode Administracion_del_sistema[] = {
		new Obj_CheckBoxNode(Sub_Administracion_del_Sistema[0], false),
		new Obj_CheckBoxNode(Sub_Administracion_del_Sistema[1], false),
		new Obj_CheckBoxNode(Sub_Administracion_del_Sistema[2], false),
		new Obj_CheckBoxNode(Sub_Administracion_del_Sistema[3], false),
		new Obj_CheckBoxNode(Sub_Administracion_del_Sistema[4], false),
		new Obj_CheckBoxNode(Sub_Administracion_del_Sistema[5], false),
		new Obj_CheckBoxNode(Sub_Administracion_del_Sistema[6], false),
	};
	@SuppressWarnings("rawtypes")
	Vector Administracion_del_sistemaVector = new Obj_NombreVector("Administración Del Sistema", Administracion_del_sistema);
	
	// MENU PRINCIPAL AUDITORIA (2)
	String[] Sub_Auditoria = new Obj_SubMenus().Relacion_de_SubMenus(2);
	Obj_CheckBoxNode Auditoria[] = {
		new Obj_CheckBoxNode(Sub_Auditoria[0], false),
	    new Obj_CheckBoxNode(Sub_Auditoria[1], false),
	    new Obj_CheckBoxNode(Sub_Auditoria[2], false),
	    new Obj_CheckBoxNode(Sub_Auditoria[3], false),
	    new Obj_CheckBoxNode(Sub_Auditoria[4], false),
	    new Obj_CheckBoxNode(Sub_Auditoria[5], false),
	    new Obj_CheckBoxNode(Sub_Auditoria[6], false),
	    new Obj_CheckBoxNode(Sub_Auditoria[7], false),
	    new Obj_CheckBoxNode(Sub_Auditoria[8], false),
	    new Obj_CheckBoxNode(Sub_Auditoria[9], false),
	    new Obj_CheckBoxNode(Sub_Auditoria[10], false),
	    new Obj_CheckBoxNode(Sub_Auditoria[11], false),
	    new Obj_CheckBoxNode(Sub_Auditoria[12], false),
	    new Obj_CheckBoxNode(Sub_Auditoria[13], false),	  
	    new Obj_CheckBoxNode(Sub_Auditoria[14], false),	
	    new Obj_CheckBoxNode(Sub_Auditoria[15], false),	
	    new Obj_CheckBoxNode(Sub_Auditoria[16], false),	
	};
	
	@SuppressWarnings("rawtypes")
	Vector AuditoriaVector = new Obj_NombreVector("Auditoria", Auditoria);
	
	// MENU PRINCIPAL CHECADOR (3)
	String[] Sub_Checador = new Obj_SubMenus().Relacion_de_SubMenus(3);
	Obj_CheckBoxNode Checador[] = {
		new Obj_CheckBoxNode(Sub_Checador[0], false),
	    new Obj_CheckBoxNode(Sub_Checador[1], false),
	    new Obj_CheckBoxNode(Sub_Checador[2], false),
	    new Obj_CheckBoxNode(Sub_Checador[3], false),
	    new Obj_CheckBoxNode(Sub_Checador[4], false),
	    new Obj_CheckBoxNode(Sub_Checador[5], false),
	    new Obj_CheckBoxNode(Sub_Checador[6], false),
	    new Obj_CheckBoxNode(Sub_Checador[7], false),
	    new Obj_CheckBoxNode(Sub_Checador[8], false),
	    new Obj_CheckBoxNode(Sub_Checador[9], false),
	    new Obj_CheckBoxNode(Sub_Checador[10], false),
	    new Obj_CheckBoxNode(Sub_Checador[11], false),
	    new Obj_CheckBoxNode(Sub_Checador[12], false),
	    new Obj_CheckBoxNode(Sub_Checador[13], false),
	    new Obj_CheckBoxNode(Sub_Checador[14], false),
	    new Obj_CheckBoxNode(Sub_Checador[15], false),
	    new Obj_CheckBoxNode(Sub_Checador[16], false),
	    new Obj_CheckBoxNode(Sub_Checador[17], false),
	    new Obj_CheckBoxNode(Sub_Checador[18], false),
	};
	
	@SuppressWarnings("rawtypes")
	Vector ChecadorVector = new Obj_NombreVector("Checador", Checador);
	
	// MENU PRINCIPAL CONTABILIDAD (4)
	String[] Sub_Contabilidad = new Obj_SubMenus().Relacion_de_SubMenus(4);
	Obj_CheckBoxNode Contabilidad[] = {
		new Obj_CheckBoxNode(Sub_Contabilidad[0], false),
		new Obj_CheckBoxNode(Sub_Contabilidad[1], false),
		new Obj_CheckBoxNode(Sub_Contabilidad[2], false),
		new Obj_CheckBoxNode(Sub_Contabilidad[3], false),
		new Obj_CheckBoxNode(Sub_Contabilidad[4], false),
		new Obj_CheckBoxNode(Sub_Contabilidad[5], false),
		new Obj_CheckBoxNode(Sub_Contabilidad[6], false),
		new Obj_CheckBoxNode(Sub_Contabilidad[7], false),
		new Obj_CheckBoxNode(Sub_Contabilidad[8], false),
		new Obj_CheckBoxNode(Sub_Contabilidad[9], false),
		new Obj_CheckBoxNode(Sub_Contabilidad[10], false),
		new Obj_CheckBoxNode(Sub_Contabilidad[11], false),
		new Obj_CheckBoxNode(Sub_Contabilidad[12], false),
		new Obj_CheckBoxNode(Sub_Contabilidad[13], false),
		new Obj_CheckBoxNode(Sub_Contabilidad[14], false),
		new Obj_CheckBoxNode(Sub_Contabilidad[15], false),
		new Obj_CheckBoxNode(Sub_Contabilidad[16], false),
		new Obj_CheckBoxNode(Sub_Contabilidad[17], false),
		new Obj_CheckBoxNode(Sub_Contabilidad[18], false),
		new Obj_CheckBoxNode(Sub_Contabilidad[19], false),
		new Obj_CheckBoxNode(Sub_Contabilidad[20], false),
		new Obj_CheckBoxNode(Sub_Contabilidad[21], false),
		new Obj_CheckBoxNode(Sub_Contabilidad[22], false),
		new Obj_CheckBoxNode(Sub_Contabilidad[23], false),
		new Obj_CheckBoxNode(Sub_Contabilidad[24], false),
		new Obj_CheckBoxNode(Sub_Contabilidad[25], false),
		new Obj_CheckBoxNode(Sub_Contabilidad[26], false),
		new Obj_CheckBoxNode(Sub_Contabilidad[27], false),
		new Obj_CheckBoxNode(Sub_Contabilidad[28], false),
		new Obj_CheckBoxNode(Sub_Contabilidad[29], false),
		new Obj_CheckBoxNode(Sub_Contabilidad[30], false),
		new Obj_CheckBoxNode(Sub_Contabilidad[31], false),
		new Obj_CheckBoxNode(Sub_Contabilidad[32], false),
		new Obj_CheckBoxNode(Sub_Contabilidad[33], false),
		new Obj_CheckBoxNode(Sub_Contabilidad[34], false),
		new Obj_CheckBoxNode(Sub_Contabilidad[35], false),
		new Obj_CheckBoxNode(Sub_Contabilidad[36], false),
	};
	@SuppressWarnings("rawtypes")
	Vector ContabilidadVector = new Obj_NombreVector("Contabilidad", Contabilidad);
	
	// MENU PRINCIPAL EVALUACIONES  5
	String[] Sub_Evaluaciones = new Obj_SubMenus().Relacion_de_SubMenus(5);
	Obj_CheckBoxNode Evaluaciones[] = {
		new Obj_CheckBoxNode(Sub_Evaluaciones[0], false),
		new Obj_CheckBoxNode(Sub_Evaluaciones[1], false),
		new Obj_CheckBoxNode(Sub_Evaluaciones[2], false),
		new Obj_CheckBoxNode(Sub_Evaluaciones[3], false),
		new Obj_CheckBoxNode(Sub_Evaluaciones[4], false),
		new Obj_CheckBoxNode(Sub_Evaluaciones[5], false),
		new Obj_CheckBoxNode(Sub_Evaluaciones[6], false),
		new Obj_CheckBoxNode(Sub_Evaluaciones[7], false),
		new Obj_CheckBoxNode(Sub_Evaluaciones[8], false),
		new Obj_CheckBoxNode(Sub_Evaluaciones[9], false),
		new Obj_CheckBoxNode(Sub_Evaluaciones[10], false),
		new Obj_CheckBoxNode(Sub_Evaluaciones[11], false),
		new Obj_CheckBoxNode(Sub_Evaluaciones[12], false),
		new Obj_CheckBoxNode(Sub_Evaluaciones[13], false),
		new Obj_CheckBoxNode(Sub_Evaluaciones[14], false),
		new Obj_CheckBoxNode(Sub_Evaluaciones[15], false),
		new Obj_CheckBoxNode(Sub_Evaluaciones[16], false),
		new Obj_CheckBoxNode(Sub_Evaluaciones[17], false),
		new Obj_CheckBoxNode(Sub_Evaluaciones[18], false),
		new Obj_CheckBoxNode(Sub_Evaluaciones[19], false),
		new Obj_CheckBoxNode(Sub_Evaluaciones[20], false),
		new Obj_CheckBoxNode(Sub_Evaluaciones[21], false),
		new Obj_CheckBoxNode(Sub_Evaluaciones[22], false),
		new Obj_CheckBoxNode(Sub_Evaluaciones[23], false),
	};
	@SuppressWarnings("rawtypes")
	Vector EvaluacionesVector = new Obj_NombreVector("Evaluaciones", Evaluaciones);
	
	// MENU PRINCIPAL LISTA DE RAYA 6
	String[] Sub_Lista_de_Raya = new Obj_SubMenus().Relacion_de_SubMenus(6);
	Obj_CheckBoxNode Lista_de_Raya[] = {
		new Obj_CheckBoxNode(Sub_Lista_de_Raya[0], false),
		new Obj_CheckBoxNode(Sub_Lista_de_Raya[1], false),
		new Obj_CheckBoxNode(Sub_Lista_de_Raya[2], false),
		new Obj_CheckBoxNode(Sub_Lista_de_Raya[3], false),
		new Obj_CheckBoxNode(Sub_Lista_de_Raya[4], false),
		new Obj_CheckBoxNode(Sub_Lista_de_Raya[5], false),
		new Obj_CheckBoxNode(Sub_Lista_de_Raya[6], false),
		new Obj_CheckBoxNode(Sub_Lista_de_Raya[7], false),
		new Obj_CheckBoxNode(Sub_Lista_de_Raya[8], false),
		new Obj_CheckBoxNode(Sub_Lista_de_Raya[9], false),
		new Obj_CheckBoxNode(Sub_Lista_de_Raya[10], false),
		new Obj_CheckBoxNode(Sub_Lista_de_Raya[11], false),
		new Obj_CheckBoxNode(Sub_Lista_de_Raya[12], false),
		new Obj_CheckBoxNode(Sub_Lista_de_Raya[13], false),
		new Obj_CheckBoxNode(Sub_Lista_de_Raya[14], false),
		new Obj_CheckBoxNode(Sub_Lista_de_Raya[15], false),
		new Obj_CheckBoxNode(Sub_Lista_de_Raya[16], false),
		new Obj_CheckBoxNode(Sub_Lista_de_Raya[17], false),
		new Obj_CheckBoxNode(Sub_Lista_de_Raya[18], false),
		new Obj_CheckBoxNode(Sub_Lista_de_Raya[19], false),
		new Obj_CheckBoxNode(Sub_Lista_de_Raya[20], false),
		new Obj_CheckBoxNode(Sub_Lista_de_Raya[21], false),
		new Obj_CheckBoxNode(Sub_Lista_de_Raya[22], false),
		new Obj_CheckBoxNode(Sub_Lista_de_Raya[23], false),
		new Obj_CheckBoxNode(Sub_Lista_de_Raya[24], false),
		new Obj_CheckBoxNode(Sub_Lista_de_Raya[25], false),
		new Obj_CheckBoxNode(Sub_Lista_de_Raya[26], false),
		new Obj_CheckBoxNode(Sub_Lista_de_Raya[27], false),
		new Obj_CheckBoxNode(Sub_Lista_de_Raya[28], false),
		new Obj_CheckBoxNode(Sub_Lista_de_Raya[29], false),
		new Obj_CheckBoxNode(Sub_Lista_de_Raya[30], false),
		new Obj_CheckBoxNode(Sub_Lista_de_Raya[31], false),
		new Obj_CheckBoxNode(Sub_Lista_de_Raya[32], false),
		new Obj_CheckBoxNode(Sub_Lista_de_Raya[33], false),
		new Obj_CheckBoxNode(Sub_Lista_de_Raya[34], false),
		new Obj_CheckBoxNode(Sub_Lista_de_Raya[35], false),
		new Obj_CheckBoxNode(Sub_Lista_de_Raya[36], false),
		new Obj_CheckBoxNode(Sub_Lista_de_Raya[37], false),
		new Obj_CheckBoxNode(Sub_Lista_de_Raya[38], false),
		new Obj_CheckBoxNode(Sub_Lista_de_Raya[39], false),
		new Obj_CheckBoxNode(Sub_Lista_de_Raya[40], false),
		new Obj_CheckBoxNode(Sub_Lista_de_Raya[41], false),
		new Obj_CheckBoxNode(Sub_Lista_de_Raya[42], false),
		new Obj_CheckBoxNode(Sub_Lista_de_Raya[43], false),
		new Obj_CheckBoxNode(Sub_Lista_de_Raya[44], false),
		new Obj_CheckBoxNode(Sub_Lista_de_Raya[45], false),
		new Obj_CheckBoxNode(Sub_Lista_de_Raya[46], false),
		new Obj_CheckBoxNode(Sub_Lista_de_Raya[47], false),
		new Obj_CheckBoxNode(Sub_Lista_de_Raya[48], false),
		new Obj_CheckBoxNode(Sub_Lista_de_Raya[49], false),
		new Obj_CheckBoxNode(Sub_Lista_de_Raya[50], false),
		new Obj_CheckBoxNode(Sub_Lista_de_Raya[51], false),
		new Obj_CheckBoxNode(Sub_Lista_de_Raya[52], false),
		new Obj_CheckBoxNode(Sub_Lista_de_Raya[53], false),
	};
	@SuppressWarnings("rawtypes")
	Vector Lista_de_RayaVector = new Obj_NombreVector("Lista De Raya", Lista_de_Raya);
	
	// MENU PRINCIPAL SEGURIDAD (7)
		String[] Sub_Reportes_Especiales = new Obj_SubMenus().Relacion_de_SubMenus(7);
		Obj_CheckBoxNode Reportes_Especiales[] = {
			new Obj_CheckBoxNode(Sub_Reportes_Especiales[0], false),
			new Obj_CheckBoxNode(Sub_Reportes_Especiales[1], false),
			new Obj_CheckBoxNode(Sub_Reportes_Especiales[2], false),
		};
		@SuppressWarnings("rawtypes")
		Vector ReportesEspecialesVector = new Obj_NombreVector("Seguridad", Reportes_Especiales);
		
	// MENU PRINCIPAL COMPRAS (8)
			String[] Sub_Compras = new Obj_SubMenus().Relacion_de_SubMenus(8);
			Obj_CheckBoxNode Compras[] = {
				new Obj_CheckBoxNode(Sub_Compras[0], false),
				new Obj_CheckBoxNode(Sub_Compras[1], false),
				new Obj_CheckBoxNode(Sub_Compras[2], false),
				new Obj_CheckBoxNode(Sub_Compras[3], false),
				new Obj_CheckBoxNode(Sub_Compras[4], false),
				new Obj_CheckBoxNode(Sub_Compras[5], false),
				new Obj_CheckBoxNode(Sub_Compras[6], false),
				new Obj_CheckBoxNode(Sub_Compras[7], false),
				new Obj_CheckBoxNode(Sub_Compras[8], false),
				new Obj_CheckBoxNode(Sub_Compras[9], false),
				new Obj_CheckBoxNode(Sub_Compras[10], false),
				new Obj_CheckBoxNode(Sub_Compras[11], false),
				new Obj_CheckBoxNode(Sub_Compras[12], false),
				new Obj_CheckBoxNode(Sub_Compras[13], false),
				new Obj_CheckBoxNode(Sub_Compras[14], false),
				new Obj_CheckBoxNode(Sub_Compras[15], false),
				new Obj_CheckBoxNode(Sub_Compras[16], false),
				new Obj_CheckBoxNode(Sub_Compras[17], false),
				new Obj_CheckBoxNode(Sub_Compras[18], false),
				new Obj_CheckBoxNode(Sub_Compras[19], false),
				new Obj_CheckBoxNode(Sub_Compras[20], false),
				new Obj_CheckBoxNode(Sub_Compras[21], false),
				new Obj_CheckBoxNode(Sub_Compras[22], false),
				new Obj_CheckBoxNode(Sub_Compras[23], false),
				new Obj_CheckBoxNode(Sub_Compras[24], false),
				new Obj_CheckBoxNode(Sub_Compras[25], false),
				new Obj_CheckBoxNode(Sub_Compras[26], false),
				new Obj_CheckBoxNode(Sub_Compras[27], false),
				new Obj_CheckBoxNode(Sub_Compras[28], false),
				new Obj_CheckBoxNode(Sub_Compras[29], false),
				new Obj_CheckBoxNode(Sub_Compras[30], false),
				new Obj_CheckBoxNode(Sub_Compras[31], false),
				new Obj_CheckBoxNode(Sub_Compras[32], false),
				new Obj_CheckBoxNode(Sub_Compras[33], false),
				new Obj_CheckBoxNode(Sub_Compras[34], false),
				new Obj_CheckBoxNode(Sub_Compras[35], false),
			};
			@SuppressWarnings("rawtypes")
			Vector ComprasVector = new Obj_NombreVector("Compras", Compras);
			
	// MENU PRICIPAL PUNTO DE VENTA (9)
	String[] Sub_Punto_de_Venta = new Obj_Administracion_del_Sistema.Obj_SubMenus().Relacion_de_SubMenus(9);
	Obj_CheckBoxNode Punto_de_Venta[] = {
		new Obj_CheckBoxNode(Sub_Punto_de_Venta[0], false),
		new Obj_CheckBoxNode(Sub_Punto_de_Venta[1], false),
		new Obj_CheckBoxNode(Sub_Punto_de_Venta[2], false),
		new Obj_CheckBoxNode(Sub_Punto_de_Venta[3], false),
		new Obj_CheckBoxNode(Sub_Punto_de_Venta[4], false),
	};
	@SuppressWarnings("rawtypes")
	Vector Vector_Punto_De_Venta = new Obj_NombreVector("Punto De Venta", Punto_de_Venta);
	
	// MENU PRINCIPAL INVENTARIOS (10) el numero es el menu principal
	String[] Sub_inventarios = new Obj_Administracion_del_Sistema.Obj_SubMenus().Relacion_de_SubMenus(10);
	Obj_CheckBoxNode Inventarios[] = {
		new Obj_CheckBoxNode(Sub_inventarios[0], false),
		new Obj_CheckBoxNode(Sub_inventarios[1], false),
		new Obj_CheckBoxNode(Sub_inventarios[2], false),
		new Obj_CheckBoxNode(Sub_inventarios[3], false),
		new Obj_CheckBoxNode(Sub_inventarios[4], false),
		new Obj_CheckBoxNode(Sub_inventarios[5], false),
		new Obj_CheckBoxNode(Sub_inventarios[6], false),
		new Obj_CheckBoxNode(Sub_inventarios[7], false),
		new Obj_CheckBoxNode(Sub_inventarios[8], false),
		new Obj_CheckBoxNode(Sub_inventarios[9], false),
		new Obj_CheckBoxNode(Sub_inventarios[10], false),
		new Obj_CheckBoxNode(Sub_inventarios[11], false),
		new Obj_CheckBoxNode(Sub_inventarios[12], false),
		new Obj_CheckBoxNode(Sub_inventarios[13], false),
		new Obj_CheckBoxNode(Sub_inventarios[14], false),
		new Obj_CheckBoxNode(Sub_inventarios[15], false),
		new Obj_CheckBoxNode(Sub_inventarios[16], false),
	};
	@SuppressWarnings("rawtypes")
	Vector Vector_inventarios = new Obj_NombreVector("Inventarios", Inventarios);
	
	// MENU PRINCIPAL SERVICIOS (11) el numero es el menu principal
	String[] Sub_servicios = new Obj_Administracion_del_Sistema.Obj_SubMenus().Relacion_de_SubMenus(11);
	Obj_CheckBoxNode Servicios[] = {
		new Obj_CheckBoxNode(Sub_servicios[0], false),
		new Obj_CheckBoxNode(Sub_servicios[1], false),
		new Obj_CheckBoxNode(Sub_servicios[2], false),
		new Obj_CheckBoxNode(Sub_servicios[3], false),
		new Obj_CheckBoxNode(Sub_servicios[4], false),
		new Obj_CheckBoxNode(Sub_servicios[5], false),
		new Obj_CheckBoxNode(Sub_servicios[6], false),
		new Obj_CheckBoxNode(Sub_servicios[7], false),
		new Obj_CheckBoxNode(Sub_servicios[8], false),
		new Obj_CheckBoxNode(Sub_servicios[9], false),
		new Obj_CheckBoxNode(Sub_servicios[10], false),
		new Obj_CheckBoxNode(Sub_servicios[11], false),
		
	};
	
	@SuppressWarnings("rawtypes")
	Vector Vector_servicios = new Obj_NombreVector("Servicios", Servicios);
	
		////ESTA PARTE SE ORDENA LA POSICION EN LA QUE SE QUIERE EL MENU PRINCIPAL  EN EL ARBOL
	Object rootNodos[] = { Administracion_del_sistemaVector, AuditoriaVector, ChecadorVector, ComprasVector,Vector_inventarios, ContabilidadVector, EvaluacionesVector, 
			Lista_de_RayaVector,ReportesEspecialesVector,Vector_Punto_De_Venta,Vector_servicios};
	    
	@SuppressWarnings("rawtypes")
	Vector rootVector = new Obj_NombreVector("Permisos", rootNodos);
	JTree tree = new JTree(rootVector);
	JScrollPane scrolltree = new JScrollPane(tree);
	
	Obj_CheckBoxNodeRenderer  renderer = new Obj_CheckBoxNodeRenderer ();	
	
	Obj_tabla ObjTab =new Obj_tabla();
	
	int columnasb = 3,checkbox=-1;
	public void init_tabla(){
    	this.tabla.getColumnModel().getColumn( 0).setMinWidth(55);
    	this.tabla.getColumnModel().getColumn( 1).setMinWidth(260);
		 String comandob=" exec usuarios_lista_para_administracion_permisos '"+user.getFolio()+"'" ;
		String basedatos="98",pintar="si";
		ObjTab.Obj_Refrescar(tabla,model, columnasb, comandob, basedatos,pintar,checkbox);
    }

	@SuppressWarnings("rawtypes")
	public Class[] base (){
		Class[] types = new Class[columnasb];
		for(int i = 0; i<columnasb; i++){types[i]= java.lang.Object.class;}
		 return types;
	}
	
	public DefaultTableModel model = new DefaultTableModel(null, new String[]{"Folio", "Nombre", "Usuario"}){
		 @SuppressWarnings("rawtypes")
			Class[] types = base();
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
			public boolean isCellEditable(int fila, int columna){return false;}
	};
	
	JTable tabla = new JTable(model);
	JScrollPane scrolltable = new JScrollPane(tabla);
	@SuppressWarnings({ "rawtypes", "unchecked" })
	TableRowSorter trsfiltro = new TableRowSorter(model); 
	
	JTextField txtFolioFiltro = new JTextField();
	JTextField txtNombre_CompletoFiltro = new JTextField();
	
    JTextField txtFolio = new JTextField();
	JTextField txtNombre_Completo = new JTextField();
	JPasswordField txtContrasena = new JPasswordField();
	JPasswordField txtContrasena1 = new JPasswordField();
    
	JButton btnGuardar        = new JCButton("Guardar","Guardar.png"                     ,"Azul" );
	JButton btnFoto           = new JCButton(""  ,""                                     ,"Azul" );
	JButton btnUsuariovigente = new JCButton(""  ,"usuario-icono-vigente7340-64.png"     ,"Azul" );
	JButton btnNoEsUsuario    = new JCButton(""  ,"usuario-icono-noes_usuario9131-64.png","Cafe" );
	JButton btnfiltroclonar   = new JCButton(""  ,"Usuario.png"                          ,"Azul" );
	JButton btnDefault        = new JCButton("Aplicar Contraseña Default Al Usuario Seleccionado","refrescar-volver-a-cargar-las-flechas-icono-4094-16.png","Cafe");
	JButton btnNuevo          = new JButton(new ImageIcon("imagen/usuario-icono-editar8476-64.png"));
	
	String establecimiento[] = new Obj_SubMenus().Combo_Usuarios();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbempleado_usuario = new JComboBox(establecimiento);
	
    JTextField txtFolio_usuario = new JTextField();
	JTextField txtNombre_usuario = new JTextField();
	
	@SuppressWarnings("unchecked")
	public Cat_Usuarios(){
		this.setSize(905,560);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("Usuarios y Permisos");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/usuario-grupo-icono-5183-64.png"));
		
		ImageIcon tmpIconAux = new ImageIcon(System.getProperty("user.dir")+"/Iconos/Un.jpg");
 		btnFoto.setIcon(new ImageIcon(tmpIconAux.getImage().getScaledInstance(90, 85, Image.SCALE_DEFAULT)));
		
		tabla.setRowSorter(trsfiltro);  
		
		campo.setBorder(BorderFactory.createTitledBorder("Usuarios y Permisos"));
		init_tabla();
		
		int x=10, y =15 ,h=20;
		
		campo.add(txtFolioFiltro).setBounds                            (x     ,20  ,68 ,h  );
		campo.add(txtNombre_CompletoFiltro).setBounds                  (x+68  ,20  ,362,h  );
		campo.add(scrolltable).setBounds                               (x     ,40  ,430,480);
		
		campo.add(btnFoto).setBounds                                   (x=450 ,y+=5   ,100 ,95 );
		campo.add(new JLabel("Folio:")).setBounds                      (x+110 ,y      ,90  ,h  );		
		campo.add(txtFolio).setBounds                                  (x+160 ,y      ,100 ,h  );
		campo.add(btnGuardar).setBounds                                (x+270 ,y      ,100 ,h  );
		campo.add(btnNoEsUsuario).setBounds                            (x+375 ,y      ,64  ,64 );
		campo.add(btnUsuariovigente).setBounds                         (x+375 ,y      ,64  ,64 );
		campo.add(btnNuevo).setBounds                                  (x+375 ,y      ,64  ,64 );
		
		campo.add(new JLabel("Usuario:")).setBounds                    (x+110 ,y+=25  ,90  ,h  );
		campo.add(txtNombre_Completo).setBounds                        (x+160 ,y      ,210 ,h  );
		campo.add(new JLabel("Clonar Permisos del Usuario:")).setBounds(x+110 ,y+=25  ,200 ,h  );
		campo.add(cmbempleado_usuario).setBounds                       (x+110 ,y+=20  ,260 ,h  );		
		campo.add(btnfiltroclonar).setBounds                           (x+370 ,y      ,20 ,h  );		
		campo.add(btnDefault).setBounds                                (x     ,y+=35  ,370 ,h  );	
		campo.add(scrolltree).setBounds                                (x     ,y+=25  ,438 ,370);
		
		btnNuevo.setVisible(true);
		btnNoEsUsuario.setVisible(false);
	    btnUsuariovigente.setVisible(false);
		cont.add(campo);
		
		btnfiltroclonar.addActionListener(opAbrirFiltroUserClonar);
		btnGuardar.addActionListener(opguardar);
		txtFolioFiltro.addKeyListener(opFiltroFolio);
		txtNombre_CompletoFiltro.addKeyListener(opFiltroNombre);
		seleccion_usuario(tabla);
		
		tree.setCellRenderer(renderer);
		tree.setCellEditor(new Obj_CheckBoxNodeEditor(tree));
		tree.setEditable(true);
		
		txtFolio.setEditable(false);
		txtNombre_Completo.setEditable(false);
		
		btnDefault.setEnabled(false);
		
		btnDefault.addActionListener(contraseniaDefault);
		
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened( WindowEvent e ){
            	txtNombre_CompletoFiltro.requestFocus();
          }
     });
		
	}
	
	ActionListener opAbrirFiltroUserClonar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			new Cat_Filtro_User_Clonar().setVisible(true);
		}
	};
	
	boolean usuario = false;
	ActionListener contraseniaDefault = new ActionListener(){
		@SuppressWarnings("static-access")
		public void actionPerformed(ActionEvent e){
			if(!validaCampos().equals("")){
			    	JOptionPane.showMessageDialog(null,"Seleccione un Usuario","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
			    	return;
			}else{
					usuario = tabla.getValueAt(tabla.getSelectedRow(), 2).equals("")?false:true;
				
					if(usuario){
								if(JOptionPane.showConfirmDialog(null, "Se le Asignara la Cotraseña Default '1234567890'  al Usuario: \n"+txtNombre_Completo.getText()+"\n¿Desea Continuar?") == 0){
									
									String nuevacontrasena = new Obj_MD5().cryptMD5("1234567890", "izagar").trim().toLowerCase();
									 new Obj_Usuario().CambiarContrasena(Integer.valueOf(txtFolio.getText()),nuevacontrasena);
									 JOptionPane.showMessageDialog(null,"La Contraseña Se Restauro A La Default: 1234567890","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
								     return;
								}
					}else{
								JOptionPane.showMessageDialog(null,"El empleado seleccionado no cuenta con permisos de usuario","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//Exito.png"));
						    	return;
					}
			}
		}
	};
		
	
	private void seleccion_usuario(final JTable tbl) {
		tbl.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
		 	 if(e.getClickCount() != 0){funcion_agregar();}
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
	
    @SuppressWarnings("rawtypes")
	public void funcion_agregar() {

			int fila = tabla.getSelectedRow();
			int folio_empleado = Integer.valueOf(tabla.getValueAt(fila, 0).toString());
			Object Nombre_Completo = tabla.getValueAt(fila, 1);
			
			btnNoEsUsuario.setVisible(false);
		    btnUsuariovigente.setVisible(false);
		    
		    btnDefault.setEnabled(true);
		    
			txtFolio.setText(folio_empleado+"");
    		txtNombre_Completo.setText(Nombre_Completo.toString());
    		
    		Obj_SubMenus usuario = new Obj_SubMenus().BuscarUsuariosua(folio_empleado);
    		
    		ImageIcon fotoIcono= new ImageIcon(usuario.getFoto());
    		btnFoto.setIcon(new ImageIcon(fotoIcono.getImage().getScaledInstance(90, 85, Image.SCALE_DEFAULT)));

			if(new Obj_Usuario().ExisteUsuario(folio_empleado) == true){
									
				txtFolio.setText(folio_empleado+"");
        		txtNombre_Completo.setText(Nombre_Completo.toString());
        		
        		txtContrasena.setText(usuario.getContrasena());
        		btnUsuariovigente.setVisible(true);
        		
        		Vector catalogo = new Obj_Usuario().returnPermisos(folio_empleado,1);
        		for(int i = 0; i<Administracion_del_sistema.length; i++){
        			Administracion_del_sistema[i].setSelected(Boolean.parseBoolean(catalogo.get(i).toString()));
        		}
        		tree.collapseRow(0);      	
        		
        		Vector auditoria = new Obj_Usuario().returnPermisos(folio_empleado, 2);
        		for(int i=0; i <Auditoria.length; i ++){
        			Auditoria[i].setSelected(Boolean.parseBoolean(auditoria.get(i).toString()));
        		}
        		tree.collapseRow(1);
        		
        		Vector checador = new Obj_Usuario().returnPermisos(folio_empleado, 3);
        		for(int i = 0; i<Checador.length; i ++){
        			Checador[i].setSelected(Boolean.parseBoolean(checador.get(i).toString()));
        		}
        		tree.collapseRow(2);
        		
        		Vector cuadrantes_alimentacion = new Obj_Usuario().returnPermisos(folio_empleado, 4);
        		for(int i = 0; i<Contabilidad.length; i ++){
        			Contabilidad[i].setSelected(Boolean.parseBoolean(cuadrantes_alimentacion.get(i).toString()));
        		}
        		tree.collapseRow(3);
        		
        		Vector evaluaciones = new Obj_Usuario().returnPermisos(folio_empleado, 5);
        		for(int i = 0; i<Evaluaciones.length; i ++){
        			Evaluaciones[i].setSelected(Boolean.parseBoolean(evaluaciones.get(i).toString()));
        		}
        		tree.collapseRow(4);
        		
        		Vector lista_de_Raya = new Obj_Usuario().returnPermisos(folio_empleado, 6);
        		for(int i = 0; i<Lista_de_Raya.length; i ++){
        			Lista_de_Raya[i].setSelected(Boolean.parseBoolean(lista_de_Raya.get(i).toString()));
        		}
        		tree.collapseRow(5);
        		
        		Vector reportes_especiales = new Obj_Usuario().returnPermisos(folio_empleado, 7);
        		for(int i = 0; i<Reportes_Especiales.length; i ++){
        			Reportes_Especiales[i].setSelected(Boolean.parseBoolean(reportes_especiales.get(i).toString()));
        		}
        		tree.collapseRow(6);
        		
        		Vector compras = new Obj_Usuario().returnPermisos(folio_empleado, 8);
        		for(int i = 0; i<Compras.length; i ++){
        			Compras[i].setSelected(Boolean.parseBoolean(compras.get(i).toString()));
        		}
        		tree.collapseRow(7);
        		
        		Vector punto_de_venta = new Obj_Usuario().returnPermisos(folio_empleado, 9);
        		for(int i = 0; i<Punto_de_Venta.length; i ++){
        			Punto_de_Venta[i].setSelected(Boolean.parseBoolean(punto_de_venta.get(i).toString()));
        		}
        		tree.collapseRow(8);
        		
        		Vector inventarios = new Obj_Usuario().returnPermisos(folio_empleado, 10);
        		for(int i = 0; i<Inventarios.length; i ++){
        			Inventarios[i].setSelected(Boolean.parseBoolean(inventarios.get(i).toString()));
        		}
        		tree.collapseRow(9);
        		
				Vector servicios = new Obj_Usuario().returnPermisos(folio_empleado, 11);
        		for(int i = 0; i<Servicios.length; i ++){
        			Servicios[i].setSelected(Boolean.parseBoolean(servicios.get(i).toString()));
        		}
        		tree.collapseRow(9);
        		
			}else{
				
				btnNoEsUsuario.setVisible(true);
				
        		for(int i = 0; i<Administracion_del_sistema.length; i++){
        			Administracion_del_sistema[i].setSelected(false);
        		}
        		tree.collapseRow(0);
        		
        		for(int i=0; i <Auditoria.length; i ++){
        			Auditoria[i].setSelected(false);
        		}
        		tree.collapseRow(1);
        		
        		for(int i = 0; i<Checador.length; i ++){
        			Checador[i].setSelected(false);
        		}
        		tree.collapseRow(2);
        		
        		for(int i = 0; i<Contabilidad.length; i ++){
        			Contabilidad[i].setSelected(false);
        		}
        		tree.collapseRow(3);
        		
        		for(int i = 0; i<Evaluaciones.length; i ++){
        			Evaluaciones[i].setSelected(false);
        		}
        		tree.collapseRow(4);
        		
        		for(int i = 0; i<Lista_de_Raya.length; i ++){
        			Lista_de_Raya[i].setSelected(false);
        		}
        		for(int i = 0; i<Reportes_Especiales.length; i ++){
        			Reportes_Especiales[i].setSelected(false);
        		}
        		tree.collapseRow(5);
        		
        		for(int i = 0; i<Compras.length; i ++){
        			Compras[i].setSelected(false);
        		}
        		tree.collapseRow(6);
        		
        		for(int i = 0; i<Punto_de_Venta.length; i ++){
        			Punto_de_Venta[i].setSelected(false);
        		}
        		tree.collapseRow(7);
        		
        		for(int i = 0; i<Inventarios.length; i ++){
        			Inventarios[i].setSelected(false);
        		}
        		tree.collapseRow(8);
        		
        		for(int i = 0; i<Servicios.length; i ++){
        			Servicios[i].setSelected(false);
        		}
        		tree.collapseRow(9);
        		
     		
		}
    };
	
	ActionListener opguardar = new ActionListener(){
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public void actionPerformed(ActionEvent e){
			trsfiltro.setRowFilter(RowFilter.regexFilter("", 0));
			trsfiltro.setRowFilter(RowFilter.regexFilter("", 1));
			if(validaCampos()!="") {
				JOptionPane.showMessageDialog(null, "Necesita Selecionar Un Empleado de la Tabla\n", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				        return;
			                      } else{ Obj_Usuario usuario = new Obj_Usuario().BuscarUsuario(Integer.valueOf(txtFolio.getText()));
										  Vector subMenus = vectorComponentes(tree);
												if(new Obj_Usuario().ExisteUsuario(Integer.valueOf(txtFolio.getText())) == false){
													
													if(cmbempleado_usuario.getSelectedIndex()==0){
														usuario.setFolio(Integer.parseInt(txtFolio.getText()));
												    	usuario.guardarPermisos(subMenus);
												    	JOptionPane.showMessageDialog(null,"El Usuario Nuevo:\n >>"+txtNombre_Completo.getText()+"\nSe Guardó de Forma Segura con Contraseña 1234567890","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/aplicara-el-dialogo-icono-6256-32.png"));
														dispose();
														new Cat_Usuarios().setVisible(true);
													
													}else{
														String empleado_de_clonar = cmbempleado_usuario.getSelectedItem().toString().toUpperCase();
														int folio_empleado = Integer.parseInt(txtFolio.getText());
														usuario.Clonar_permisos(folio_empleado, empleado_de_clonar);
														JOptionPane.showMessageDialog(null,"Al Usuario Nuevo:\n >>"+txtNombre_Completo.getText()+"\nSe le Clonaron las opciones de forma segura con contraseña 1234567890 del Usuario:\n <<"+empleado_de_clonar,"Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/aplicara-el-dialogo-icono-6256-32.png"));
														dispose();
														new Cat_Usuarios().setVisible(true);
														}
										   	} else{									        		
										          if(cmbempleado_usuario.getSelectedIndex()==0){
										        	  		  			if(JOptionPane.showConfirmDialog(null, "El usuario existe, ¿desea actualizarlo?") == 0){
																				usuario.actualizar(Integer.valueOf(txtFolio.getText()), subMenus);
																				JOptionPane.showMessageDialog(null,"El Usuario:"+txtNombre_Completo.getText()+" \n Se Actualizó Correctamente","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/aplicara-el-dialogo-icono-6256-32.png"));
																				dispose();
																				new Cat_Usuarios().setVisible(true);
										        	  		  			}
													                               	}else{
													                               		if(JOptionPane.showConfirmDialog(null, "El usuario existe, ¿desea actualizarlo?") == 0){ 
																							String empleado_de_clonar = cmbempleado_usuario.getSelectedItem().toString().toUpperCase();
																							int folio_empleado = Integer.parseInt(txtFolio.getText());
																							usuario.Clonar_permisos(folio_empleado, empleado_de_clonar);
																							JOptionPane.showMessageDialog(null,"Al Usuario:\n >>"+txtNombre_Completo.getText()+"\nSe le Clonaron las Opciones del Usuario Selecionado:\n<<"+empleado_de_clonar,"Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/aplicara-el-dialogo-icono-6256-32.png"));
																							dispose();
																							new Cat_Usuarios().setVisible(true);
													                               		}
										          }
		                 }
		             }
		      }
	};
	
	KeyListener opFiltroFolio = new KeyListener(){
		@SuppressWarnings("unchecked")
		public void keyReleased(KeyEvent arg0) {
			trsfiltro.setRowFilter(RowFilter.regexFilter(txtFolioFiltro.getText(), 0));
		}
		public void keyTyped(KeyEvent arg0) {
			char caracter = arg0.getKeyChar();
			if(((caracter < '0') ||
				(caracter > '9')) &&
			    (caracter != KeyEvent.VK_BACK_SPACE)){
				arg0.consume(); 
			}	
		}
		public void keyPressed(KeyEvent arg0) {}
	};
	
	KeyListener opFiltroNombre = new KeyListener(){
		public void keyReleased(KeyEvent arg0) {
			new Obj_Filtro_Dinamico(tabla,"Nombre", txtNombre_CompletoFiltro.getText().toUpperCase(), "", "", "", "", "", "");
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}
		
	};
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Vector vectorComponentes(JTree tree) {
		Vector lista = new Vector();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode)(tree.getModel().getRoot());
		Enumeration enumeration = root.depthFirstEnumeration();

		while (enumeration.hasMoreElements()){
			String input = enumeration.nextElement()+"", extracted;
			extracted = input.substring(input.indexOf('[')+1,input.length()-1);
			int indice = extracted.indexOf('/');        

			if(indice != -1){
				lista.add(extracted);
			}			
		}
		return lista;
	}

	private String validaCampos(){
		String error="";
		if(txtFolio.getText().equals("")) 			error+= "Folio\n";
		if(txtNombre_Completo.getText().equals("")) error+= "Nombre Completo\n";
		return error;
	}
	
	//TODO inicia filtro_puestos	
	public class Cat_Filtro_User_Clonar extends JDialog{
		Container contf = getContentPane();
		JLayeredPane panelf = new JLayeredPane();
		Connexion con = new Connexion();
		Obj_tabla ObjTab =new Obj_tabla();
			int columnasp2 = 2,checkbox2=-1;
			public void init_tablafp2(){
		    	this.tablafp2.getColumnModel().getColumn(0).setMinWidth(55);
		    	this.tablafp2.getColumnModel().getColumn(1).setMinWidth(375);
		    	String comandof="exec usuarios_filtro_clonar ";
				String basedatos="26",pintar="si";
				ObjTab.Obj_Refrescar(tablafp2,modelof2, columnasp2, comandof, basedatos,pintar,checkbox2);
		    }
			
			@SuppressWarnings("rawtypes")
			public Class[] base2 (){
				Class[] types = new Class[columnasp2];
				for(int i = 0; i<columnasp2; i++){types[i]= java.lang.Object.class;}
				 return types;
			}
			
			public DefaultTableModel modelof2 = new DefaultTableModel(null, new String[]{"Folio","Nombre Colaborador"}){
				 @SuppressWarnings("rawtypes")
					Class[] types = base2();
					@SuppressWarnings({ "rawtypes", "unchecked" })
					public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
					public boolean isCellEditable(int fila, int columna){return false;}
			};
			
			JTable tablafp2 = new JTable(modelof2);
			public JScrollPane scroll_tablafp2 = new JScrollPane(tablafp2);
		     @SuppressWarnings({ "rawtypes" })
		    private TableRowSorter trsfiltro2;
		     
		JTextField txtBuscarfp  = new Componentes().textfiltro(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<", 300, "String",tablafp2,columnasp2);
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Cat_Filtro_User_Clonar(){
			this.setSize(500,500);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			this.setModal(true);
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Filter-List-icon32.png"));
			this.panelf.setBorder(BorderFactory.createTitledBorder("Selecione Un Colaborador Con Doble Click"));
			this.setTitle("Filtro De Colaboradores Disponibles Para Clonar");
			trsfiltro2 = new TableRowSorter(modelof2); 
			tablafp2.setRowSorter(trsfiltro2);
			this.panelf.add(txtBuscarfp).setBounds       (10 ,20 ,470 , 20 );
			this.panelf.add(scroll_tablafp2).setBounds   (10 ,40 ,470 ,415 );
			this.init_tablafp2();
			this.agregar(tablafp2);
			contf.add(panelf);
		}
		
		private void agregar(final JTable tbl) {
			tbl.addMouseListener(new MouseListener() {
				public void mouseReleased(MouseEvent e) {
			 	 if(e.getClickCount() == 2){funcion_agregar();}
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
	    	int fila = tablafp2.getSelectedRow();
		    cmbempleado_usuario.setSelectedItem(tablafp2.getValueAt(fila,1)+"");
		dispose();
	    };
	    
		
	}
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Usuarios().setVisible(true);
		}catch(Exception e){}
	}
}
