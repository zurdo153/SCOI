package Cat_Principal;

import java.awt.Container;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Vector;


import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;

import com.digitalpersona.onetouch.DPFPGlobal;

import Cat_Auditoria.Cat_Cortes_De_Cajeros;
import Cat_Auditoria.Cat_Retiros_A_Cajeros;
import Cat_Chat.Cat_Chat;
import Cat_Checador.Cat_Checador;
import Cat_Contabilidad.Cat_Solicitud_De_Orden_De_Gasto;
import Cat_Cuadrantes.Cat_Impresion_Y_Revision_De_Cuadrante;
import Cat_Inventarios.Cat_Orden_De_Compra_Interna_Solicitud;
import Cat_Lista_de_Raya.Cat_Captura_De_Fuente_De_Sodas_De_Cajeras;
import Cat_Lista_de_Raya.Cat_Empleados;
import Cat_Lista_de_Raya.Cat_Prestamos;
import Cat_Lista_de_Raya.Cat_Revision_De_Lista_Raya;
import Cat_Planeacion.Cat_Alimentacion_De_Plan_Semanal;
import Cat_Planeacion.Cat_Programacion_Y_Revision_Del_Plan_Semanal;
import Cat_Planeacion.Cat_Revision_Y_Evidencia_De_Actividades_Por_Nivel_Jerarquico;
import Cat_Servicios.Cat_Seguimiento_De_Servicios;
import Cat_Servicios.Cat_Solicitud_De_Servicios;
import Conexiones_SQL.BuscarSQL;
import Obj_Administracion_del_Sistema.Obj_MD5;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Principal.CLabel;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCPasswordField;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_Filtro_Dinamico_Plus;
import Obj_Principal.Obj_Menus;
import Obj_Principal.Obj_tabla;



@SuppressWarnings("serial")
public class Init_Login extends JFrame{
	
	public Container cont = getContentPane();
	public JLayeredPane panel = new JLayeredPane();
	JLabel fondo = new JLabel();
	int Version=1315;
	
	String color[] =  new Obj_Menus().Combo_Colores();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbcolores = new JComboBox(color);
	
	/* BOTON CAPTURA DE FUENTE DE SODAS CAJERAS */
	JCButton btnFuenteSodasCajeras= new JCButton("","captura_fuente_de_Sodas_64.png","Gris");
	JLabel lblFuente_sodascajeras = new JLabel("Captura Fsodas");
	JLabel lblFuente_sodascajeras2 = new JLabel("por Cajeras");
	
	/* BOTON RETIROS CAJERAS */
	JCButton btnRetirosCajeras= new JCButton("","boveda-de-dinero-en-efectivo-de-seguridad-icono-6192-64.png","Gris");
	JLabel lblPExtras1= new JLabel("Retiros A");
	JLabel lblPExtras2= new JLabel("Cajeras");
	
	/* BOTON CORTES CAJEROS */
	JCButton btnCortes_Cajeros= new JCButton("","bolsa-de-dinero-en-efectivo-icono-6673-64.png","Gris");
	JLabel lblcorteCajero1= new JLabel("Cortes");
	JLabel lblcorteCajero2= new JLabel("De Cajera(o)s");
	
	/* BOTON SOLICITUD DE SERVICIOS*/
	JCButton btnSolSer= new JCButton("","utilidades-agt-icono-6387-64.png","Gris");
	JLabel lblFsRH2= new JLabel("Solicitud De");
	JLabel lblFsRH3= new JLabel("Servicios");
	
	/* BOTON SOLICITUDES */
	JCButton btnServicios= new JCButton("","Solicitud-64.png","Gris");
	JLabel lblSolicitudes1 = new JLabel("Seguimiento a");
	JLabel lblSolicitudes= new JLabel("Servicios");
	
	/* BOTON ORDENES DE GASTO */
	JButton btnOrden_Gasto= new JCButton("","Solicitud-OG-64.png","Gris"); 
	JLabel lblBanco= new JLabel("Solicitud De");
	JLabel lblBanco2= new JLabel("Orden De Gasto");
	
	/* BOTON DEDUCCIONES Y PRECEPCIONES */
	JButton btnImpresionCuadrante=  new JCButton("","equipos-de-tarea-asignada-icono-7668-64.png","Gris");
	JLabel lblImpresioncuadrante2= new JLabel("Impresion y Revision");
	JLabel lblImpresionCuadrante4= new JLabel("De Cuadrante");
	
	/* BOTON SOLICITUD DE ORDEN DE COMPRA INTERNA*/
	JButton btnOCI = new JCButton("","Solicitud_OCI-64.png","Gris");
	JLabel lblCaja2= new JLabel("Solicitud de Orden");
	JLabel lblCaja3= new JLabel("De Compra Interna");
	
	/* BOTON PRESTAMOS */
	JButton btnPrestamo= new JButton(new ImageIcon("imagen/prestamo.png"));
	JLabel lblPrestamo2= new JLabel("Prestamos");
	
	/* BOTON PLANEACION Y REVISION */
	JButton btnPlaneacionyRevision= new JButton(new ImageIcon("imagen/Planeacion_Y_Revision.png"));
	JLabel lblPlaneacionRev1= new JLabel("Programación");
	JLabel lblPlaneacionRev2= new JLabel("De Plan Semanal");
	
	/* BOTON ALIMENTACION DE PLAN SEMANAL*/
	JButton btnAlimentacionPlan_Semanal= new JButton(new ImageIcon("imagen/Planeacion_alimentacion.png"));
	JLabel lblAlimentacionPlan1  = new JLabel("Alimentación");
	JLabel lblAlimentacionPlan12 = new JLabel("De Plan Semanal");
	
	/* BOTON CAPTURA DE CUADRANTE EQUIPO */
    JButton btnRevision_Jerarquias = new JButton(new ImageIcon("imagen/Planeacion_Revision_Nivel_Jerarquico.png"));
	JLabel lblRevision_Nivel_Jerarquico1 = new JLabel("Revision De Planes");
	JLabel lblRevision_Nivel_Jerarquico12 = new JLabel("Por Nivel Jerarquico");
	
	/* BOTON LISTA DE RAYA */
	JButton btnListaRaya= new JButton(new ImageIcon("imagen/listaR.png"));
	JLabel lblListaRaya2= new JLabel("Lista de");
	JLabel lblListaRaya3= new JLabel("Raya");
	
	/* BOTON LISTA DE FIRMAS */
	JButton btnListaFirma= new JButton(new ImageIcon("imagen/listaF.png"));
	JLabel lblListaFirma2= new JLabel("Lista de");
	JLabel lblListaFirma3= new JLabel("Firmas");
	
	/* BOTON COLABORADORES */
	JButton btnColaborador   = new JCButton("","usuario-grupo-icono-5183-64.png","Gris");
	JLabel lblColaboradores1 = new JLabel("Datos De");
	JLabel lblColaboradores2 = new JLabel("Colaboradores");
	
	/* BOTON CHECADOR */
	JCButton btnChecador= new JCButton("","checador.png","Gris");
	JLabel lblListaChecador2= new JLabel("Checador");
	
	/*BOTON SALIR DE SCOI*/
	JCButton btnCerrar= new JCButton("","RedPowerButton64.png","GrisR");
	JLabel lblListaCerrar= new JLabel("Cerrar SCOI");
	
	
	/* BOTONES,ETIQUETAS Y TEXTFIELDS DEL LOGIN*/
    JLabel lblfolio=new JLabel("Folio:");
    JLabel lblusuario=new JLabel("Usuario:");	
    JLabel lblcontrasena=new JLabel("Contraseña:");	

    JLabel lblcontrasena_Actual=new JLabel("Actual:");
    JLabel lblcontrasena_Nueva=new JLabel("Nueva:");	
    JLabel lblcontrasena_Confirmar=new JLabel("Confirmar:");	
	
	JTextField txtFolio = new Componentes().text(new JCTextField(), "Folio Empleado", 10, "Int");
	JTextField txtUsuario = new JTextField("");
	JPasswordField txtContrasena = new Componentes().textPassword(new JCPasswordField(), "Contraseña", 50);
 	
	JPasswordField txtContrasenaActual = new Componentes().textPassword(new JCPasswordField(), "Contraseña Actual", 50); 
	JPasswordField txtContrasenaNueva = new Componentes().textPassword(new JCPasswordField(), "Contraseña Nueva", 50);
	JPasswordField txtContrasenaConfirmar = new Componentes().textPassword(new JCPasswordField(), "Confirmar Contraseña", 50);
	
	JCButton btnSalir             = new JCButton("Salir"             ,"logout-icone-6625-16.png","AzulO");
	JCButton btnCambiarContrasena = new JCButton("Cambiar Contraseña","signo-kgpg-icono-4248-16.png","AzulO");
	JCButton btnBuscar            = new JCButton(""                  ,"buscar.png","AzulO"); 
	JCButton btnChat              = new JCButton("Chat"              ,"google-talk-chat-icone-6146-16.png","AzulO"); 
	
	JButton btnAceptar = new JButton("Entrar");
	
	JButton btnGuardarContrasena =new JButton ("Guardar Contraseña");
	JButton btnValidarContrasena =new JButton ("Validar Contraseña",new ImageIcon ("imagen/Key.png"));
	
	JLabel lblLogo = new JLabel(new ImageIcon("imagen/LogPrincipal.png"));
	JLabel fotolb = new CLabel();
	
	
	// DECLARAMOS EL OBJETO RUNTIME PARA EJECUTAR APLICACIONES DE WINDOWS
	Runtime R = Runtime.getRuntime();
	
	public Init_Login(){
		int ancho = Toolkit.getDefaultToolkit().getScreenSize().width;
		int alto = Toolkit.getDefaultToolkit().getScreenSize().height;
		
//      asigna el foco al JTextField deseado al arrancar la ventana
        this.addWindowListener(new WindowAdapter() {
                public void windowOpened( WindowEvent e ){
                txtFolio.requestFocus();
             }
        });
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        cont.setBackground(new java.awt.Color(255, 255, 255));
        int VersionValida=0;	
		 
        VersionValida= new BuscarSQL().VersionValida();
		
		
		if(Version<VersionValida ) {
		  JOptionPane.showMessageDialog(null, "La Version "+Version+" Que Tiene Instalada Es Obsoleta Es Requerido Solicite Actualizacion \nPara Poder Utilizar El Sistema.\n\nSolo Es Funcional Apartir De La Version "+VersionValida+"." ,"Aviso", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));        

		  this.panel.add(fondo).setBounds    (200,0 ,1024,600);
		  this.panel.add(btnCerrar).setBounds(  0,0 ,64,64);
			btnCerrar.addActionListener(Opciones);
	      ImageIcon imagen_estatus = new ImageIcon(System.getProperty("user.dir")+"/Imagen/Logotipo_IZAGAR.jpg");
	      Icon icono_estatus = new ImageIcon(imagen_estatus.getImage().getScaledInstance(550, 550, Image.SCALE_DEFAULT));
	      fondo.setIcon(icono_estatus);
		  cont.add(panel);
	     return;
		}else {
           Resolucion(ancho, alto);
		}
		
		// buscar usuario con F2
	    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
	       KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), "filtro");
	    
	    getRootPane().getActionMap().put("filtro", new AbstractAction(){
		        public void actionPerformed(ActionEvent e)
		        {
		        	new Cat_Seleccion_De_Usuario().setVisible(true);
		        }
	    });
		
		btnBuscar.addActionListener(opBuscar);
		txtFolio.addKeyListener(enterBuscar);
		txtContrasena.addKeyListener(enterIn);
		txtContrasenaActual.addKeyListener(entervalidar);

		txtContrasenaNueva.addKeyListener(entervalidar);
		txtContrasenaConfirmar.addKeyListener(entervalidar);
		
		deshabilitarCambiarContrasena();
		cargar_usuariotrue();
		
		btnCambiarContrasena.addActionListener(opCambiarContrasena);
		btnValidarContrasena.addActionListener(opValidarContrasena);
		btnGuardarContrasena.addActionListener(opGuardarContrasena);
		txtUsuario.setEditable(false);
		btnAceptar.setEnabled(false);
		btnBuscar.setEnabled(true);
		
		btnOrden_Gasto.addActionListener(Opciones);
		btnImpresionCuadrante.addActionListener(Opciones);
		btnServicios.addActionListener(Opciones);
		btnOCI.addActionListener(Opciones);
		btnSolSer.addActionListener(Opciones);
		btnCortes_Cajeros.addActionListener(Opciones);
		btnRetirosCajeras.addActionListener(Opciones);
		btnPrestamo.addActionListener(Opciones);
		btnPlaneacionyRevision.addActionListener(Opciones);
		btnAlimentacionPlan_Semanal.addActionListener(Opciones);
		btnListaRaya.addActionListener(Opciones);
		btnListaFirma.addActionListener(Opciones);
		btnFuenteSodasCajeras.addActionListener(Opciones);
		btnColaborador.addActionListener(Opciones);
		btnChecador.addActionListener(Opciones);
		btnCerrar.addActionListener(Opciones);
		btnRevision_Jerarquias.addActionListener(Opciones);
		btnChat.addActionListener(Opciones);
		
		btnOrden_Gasto.setEnabled(false);
		btnOCI.setEnabled(false);
		btnSolSer.setEnabled(false);
		btnCortes_Cajeros.setEnabled(false);
		btnRetirosCajeras.setEnabled(false);
		btnPrestamo.setEnabled(false);
		btnPlaneacionyRevision.setEnabled(false);
		btnServicios.setEnabled(false);
		btnListaRaya.setEnabled(false);
		btnListaFirma.setEnabled(false);
		btnColaborador.setEnabled(false);
		btnAlimentacionPlan_Semanal.setEnabled(false);
		btnRevision_Jerarquias.setEnabled(false);
		btnFuenteSodasCajeras.setEnabled(false);
		btnChat.setEnabled(false);
		fotolb.setVisible(false);
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	
	
	public void Resolucion(int ancho, int alto){
		if(ancho > 1024){
			panel.add(lblLogo).setBounds(920,0,400,218);
			panel.add(fotolb).setBounds(1080,380,100,95);
			panel.add(cmbcolores).setBounds(1215,460,70,20);
			
			int   x = 30  ,y = 40, z = 65;
			int  zl = 120 ,w = 20;
					
			/* COLUMNA 1 *//////////////////////////////////////////////////////
			panel.add(btnFuenteSodasCajeras).setBounds   (x     ,y     ,z,z);
			panel.add(btnRetirosCajeras).setBounds       (x     ,y+=115,z,z);
			panel.add(btnCortes_Cajeros).setBounds                (x     ,y+=115,z,z);	
			panel.add(btnSolSer).setBounds                 (x     ,y+=115,z,z);
			panel.add(btnServicios).setBounds          (x     ,y+=115,z,z);  

			y = 60;
			panel.add(lblFuente_sodascajeras).setBounds  (x+=75 ,y     ,zl,w);
			panel.add(lblFuente_sodascajeras2).setBounds (x     ,y+10  ,zl,w);
			panel.add(lblPExtras1).setBounds             (x     ,y+=115,zl,w);
			panel.add(lblPExtras2).setBounds             (x     ,y+10  ,zl,w);
			panel.add(lblcorteCajero1).setBounds               (x     ,y+=115,zl,w);
			panel.add(lblcorteCajero2).setBounds               (x     ,y+10  ,zl,w);
			panel.add(lblFsRH2).setBounds                (x     ,y+=115,zl,w);
			panel.add(lblFsRH3).setBounds                (x     ,y+10  ,zl,w);
			panel.add(lblSolicitudes1).setBounds           (x     ,y+=115,zl,w);
			panel.add(lblSolicitudes).setBounds         (x     ,y+10  ,zl,w);  
			
			
			/* COLUMNA 2 *//////////////////////////////////////////////////////
			y=40;			
			panel.add(btnOrden_Gasto).setBounds                (x+=140,y     ,z,z);
			panel.add(btnOCI).setBounds                 (x     ,y+=115,z,z);
			panel.add(btnPrestamo).setBounds             (x     ,y+=115,z,z);
			panel.add(btnColaborador).setBounds     (x     ,y+=115,z,z);
			panel.add(btnListaRaya).setBounds            (x     ,y+=115,z,z);
			
			y = 60;		
			panel.add(lblBanco).setBounds                (x+=75 ,y     ,zl,w);
			panel.add(lblBanco2).setBounds               (x     ,y+10  ,zl,w);
			panel.add(lblCaja2).setBounds                (x     ,y+=115,zl,w);
			panel.add(lblCaja3).setBounds                (x     ,y+10  ,zl,w);
			panel.add(lblPrestamo2).setBounds            (x     ,y+=115,zl,w);
			panel.add(lblColaboradores1).setBounds    (x     ,y+=115,zl,w);
			panel.add(lblColaboradores2).setBounds    (x     ,y+10  ,zl,w);
			panel.add(lblListaRaya2).setBounds           (x     ,y+=115,zl,w);
			panel.add(lblListaRaya3).setBounds           (x     ,y+10  ,zl,w);
			
			/* COLUMNA 3 *///////////////////////////////////////////////////////
			y=40;			
			panel.add(btnPlaneacionyRevision).setBounds  (x+=140, y    ,z,z);
			panel.add(btnAlimentacionPlan_Semanal).setBounds    (x     ,y+=115,z,z);
			panel.add(btnRevision_Jerarquias).setBounds      (x     ,y+=115,z,z);
			panel.add(btnImpresionCuadrante).setBounds       (x     ,y+=115,z,z);
			panel.add(btnChecador).setBounds             (x     ,y+=115,z,z);
			
			y = 60;	
			panel.add(lblPlaneacionRev1).setBounds       (x+=75 ,y     ,zl,w);
			panel.add(lblPlaneacionRev2).setBounds       (x     ,y+10  ,zl,w);
			panel.add(lblAlimentacionPlan1).setBounds    (x     ,y+=115,zl,w);
			panel.add(lblAlimentacionPlan12).setBounds   (x     ,y+10  ,zl,w);
			panel.add(lblRevision_Nivel_Jerarquico1).setBounds      (x     ,y+=115,zl,w);
			panel.add(lblRevision_Nivel_Jerarquico12).setBounds     (x     ,y+10  ,zl,w);
			panel.add(lblImpresioncuadrante2).setBounds      (x     ,y+=115,zl,w);
			panel.add(lblImpresionCuadrante4).setBounds      (x     ,y+10,zl,w); 
			panel.add(lblListaChecador2).setBounds       (x     ,y+=115,zl,w);

			/* COLUMNA 4 *///////////////////////////////////////////////////////
			y=40;
			panel.add(btnCerrar).setBounds               (x+=140,y     ,z,z);

			y = 60;
			panel.add(lblListaCerrar).setBounds          (x+=75 ,y     ,zl,w);
						
			/*LOGIN*////////////////////////////////////////////////////////////
			y=490;
			panel.add(lblfolio).setBounds                (x+=200,y     ,100,w);
			panel.add(lblcontrasena_Actual).setBounds    (x     ,y     ,100,w);
			panel.add(lblusuario).setBounds              (x     ,y+=30 ,100,w);
			panel.add(lblcontrasena_Nueva).setBounds     (x     ,y     ,100,w);
			panel.add(lblcontrasena).setBounds           (x     ,y+=30 ,100,w);
			panel.add(lblcontrasena_Confirmar).setBounds (x     ,y     ,100,w);
			
			
			y=490;
			panel.add(txtFolio).setBounds                (x+=60  ,y    ,150,w);
			panel.add(btnBuscar).setBounds               (x+150  ,y    ,30 ,w);
			panel.add(btnChat).setBounds                 (x+185  ,y    ,90 ,w);
			
			panel.add(txtContrasenaActual).setBounds     (x     ,y     ,240,w);
			panel.add(txtUsuario).setBounds              (x     ,y+=30 ,275,w);
			panel.add(txtContrasenaNueva).setBounds      (x     ,y     ,275,w);
			panel.add(txtContrasena).setBounds           (x     ,y+=30 ,275,w);
			panel.add(txtContrasenaConfirmar).setBounds  (x     ,y     ,275,w);
			panel.add(btnCambiarContrasena).setBounds    (x     ,y+=30 ,175,w);
			panel.add(btnGuardarContrasena).setBounds    (x     ,y     ,175,w);
			panel.add(btnValidarContrasena).setBounds    (x     ,y     ,175,w);
			panel.add(btnSalir).setBounds                (x+=185,y     ,90 ,w);
			
			y=490;
			

			
			cont.add(panel);
			this.setSize(ancho,alto);
		}

		if(ancho == 1024){
			
			ImageIcon tmpIconAux = new ImageIcon("imagen/LogPrincipal.png");
			panel.add(new JLabel(new ImageIcon(tmpIconAux.getImage().getScaledInstance(150,150, Image.SCALE_DEFAULT)))).setBounds(690,0,400,218);
					
//			reloj.lblHora.setFont(new java.awt.Font("Algerian",0,60));
//			panel.add(reloj.lblHora).setBounds(813,200,400,100);
//			panel.add(btnFoto).setBounds(710,300,100,95);
			panel.add(fotolb).setBounds(780,300,100,95);
			
			int   x = 10  ,y = 10, z = 65;
			int  zl = 120 ,w = 20;
					
			/* COLUMNA 1 *//////////////////////////////////////////////////////
			panel.add(btnFuenteSodasCajeras).setBounds   (x     ,y     ,z,z);
			panel.add(btnRetirosCajeras).setBounds       (x     ,y+=115,z,z);
			panel.add(btnCortes_Cajeros).setBounds                (x     ,y+=115,z,z);	
			panel.add(btnSolSer).setBounds                 (x     ,y+=115,z,z);
			panel.add(btnServicios).setBounds          (x     ,y+=115,z,z);   

			y = 30;
			panel.add(lblFuente_sodascajeras).setBounds  (x+=75 ,y     ,zl,w);
			panel.add(lblFuente_sodascajeras2).setBounds (x     ,y+10  ,zl,w);
			panel.add(lblPExtras1).setBounds             (x     ,y+=115,zl,w);
			panel.add(lblPExtras2).setBounds             (x     ,y+10  ,zl,w);
			panel.add(lblcorteCajero1).setBounds               (x     ,y+=115,zl,w);
			panel.add(lblcorteCajero2).setBounds               (x     ,y+10  ,zl,w);
			panel.add(lblFsRH2).setBounds                (x     ,y+=115,zl,w);
			panel.add(lblFsRH3).setBounds                (x     ,y+10  ,zl,w);
			panel.add(lblSolicitudes1).setBounds         (x     ,y+=115,zl,w); 
			panel.add(lblSolicitudes).setBounds            (x     ,y+10  ,zl,w);
			
			/* COLUMNA 2 *//////////////////////////////////////////////////////
			y=10;			
			panel.add(btnOrden_Gasto).setBounds                (x+=140,y     ,z,z);
			panel.add(btnOCI).setBounds                 (x     ,y+=115,z,z);
			panel.add(btnPrestamo).setBounds             (x     ,y+=115,z,z);
			panel.add(btnColaborador).setBounds     (x     ,y+=115,z,z);
			panel.add(btnListaRaya).setBounds            (x     ,y+=115,z,z);
			
			y = 30;		
			panel.add(lblBanco).setBounds                (x+=75 ,y     ,zl,w);
			panel.add(lblBanco2).setBounds               (x     ,y+10  ,zl,w);
			panel.add(lblCaja2).setBounds                (x     ,y+=115,zl,w);
			panel.add(lblCaja3).setBounds                (x     ,y+10  ,zl,w);
			panel.add(lblPrestamo2).setBounds            (x     ,y+=115,zl,w);
			panel.add(lblColaboradores1).setBounds       (x     ,y+=115,zl,w);
			panel.add(lblColaboradores2).setBounds       (x     ,y+10  ,zl,w);
			panel.add(lblListaRaya2).setBounds           (x     ,y+=115,zl,w);
			panel.add(lblListaRaya3).setBounds           (x     ,y+10  ,zl,w);
			
			/* COLUMNA 3 *///////////////////////////////////////////////////////
			y=10;			
			panel.add(btnPlaneacionyRevision).setBounds  (x+=140, y    ,z,z);
			panel.add(btnAlimentacionPlan_Semanal).setBounds    (x     ,y+=115,z,z);
			panel.add(btnRevision_Jerarquias).setBounds      (x     ,y+=115,z,z);
			panel.add(btnImpresionCuadrante).setBounds      (x     ,y+=115,z,z);
			panel.add(btnChecador).setBounds             (x     ,y+=115,z,z);
			
			y = 30;	
			panel.add(lblPlaneacionRev1).setBounds       (x+=75 ,y     ,zl,w);
			panel.add(lblPlaneacionRev2).setBounds       (x     ,y+10  ,zl,w);
			panel.add(lblAlimentacionPlan1).setBounds    (x     ,y+=115,zl,w);
			panel.add(lblAlimentacionPlan12).setBounds   (x     ,y+10  ,zl,w);
			panel.add(lblRevision_Nivel_Jerarquico1).setBounds (x     ,y+=115,zl,w);
			panel.add(lblRevision_Nivel_Jerarquico12).setBounds(x     ,y+10  ,zl,w);			
			panel.add(lblImpresioncuadrante2).setBounds              (x     ,y+=115,zl,w);
			panel.add(lblImpresionCuadrante4).setBounds              (x     ,y+10  ,zl,w);
			panel.add(lblListaChecador2).setBounds             (x     ,y+=115,zl,w);

			/* COLUMNA 4 *///////////////////////////////////////////////////////
			y=10;
			panel.add(btnCerrar).setBounds               (x+=140,y     ,z,z);

			y = 30;
			panel.add(lblListaCerrar).setBounds          (x+=75 ,y     ,zl,w);
						
			/*LOGIN*////////////////////////////////////////////////////////////
			y=400; x=650;
			
			panel.add(lblfolio).setBounds                (x     ,y     ,100,w);
			panel.add(lblcontrasena_Actual).setBounds    (x     ,y     ,100,w);
			panel.add(lblusuario).setBounds              (x     ,y+=30 ,100,w);
			panel.add(lblcontrasena_Nueva).setBounds     (x     ,y     ,100,w);
			panel.add(lblcontrasena).setBounds           (x     ,y+=30 ,100,w);
			panel.add(lblcontrasena_Confirmar).setBounds (x     ,y     ,100,w);
			
			y=400;
			
			panel.add(txtFolio).setBounds                (x+=60  ,y    ,240,w);
			panel.add(txtContrasenaActual).setBounds     (x     ,y     ,240,w);
			panel.add(txtUsuario).setBounds              (x     ,y+=30 ,275,w);
			panel.add(txtContrasenaNueva).setBounds      (x     ,y     ,275,w);
			panel.add(txtContrasena).setBounds           (x     ,y+=30 ,275,w);
			panel.add(txtContrasenaConfirmar).setBounds  (x     ,y     ,275,w);
			panel.add(btnCambiarContrasena).setBounds    (x     ,y+=30 ,175,w);
			panel.add(btnGuardarContrasena).setBounds    (x     ,y     ,175,w);
			panel.add(btnValidarContrasena).setBounds    (x     ,y     ,175,w);
			panel.add(btnSalir).setBounds                (x+=195,y     ,78 ,w);
			
			y=400;
			panel.add(btnBuscar).setBounds               (x+50  ,y     ,30 ,w);
			
			cont.add(panel);
			this.setSize(ancho,alto);
		}
		if(ancho < 1024){
			ImageIcon tmpIconAux = new ImageIcon("imagen/LogPrincipal.png");
			panel.add(new JLabel(new ImageIcon(tmpIconAux.getImage().getScaledInstance(150,150, Image.SCALE_DEFAULT)))).setBounds(500,0,400,218);
			
//			reloj.lblHora.setFont(new java.awt.Font("Algerian",0,50));
//			panel.add(reloj.lblHora).setBounds(638,190,400,100);
			
//			getImage().getScaledInstance(230, 195, Image.SCALE_DEFAULT)
			int   x = 10  ,y = 10, z = 65;
			int  zl = 120 ,w = 20;
					
			/* COLUMNA 1 *//////////////////////////////////////////////////////
			panel.add(btnFuenteSodasCajeras).setBounds   (x     ,y     ,z,z);
			panel.add(btnRetirosCajeras).setBounds       (x     ,y+=90,z,z);
			panel.add(btnCortes_Cajeros).setBounds                (x     ,y+=90,z,z);	
			panel.add(btnSolSer).setBounds                 (x     ,y+=90,z,z);
			panel.add(btnServicios).setBounds          (x     ,y+=90,z,z); 



			y = 20;
			panel.add(lblFuente_sodascajeras).setBounds  (x+=65 ,y     ,zl,w);
			panel.add(lblFuente_sodascajeras2).setBounds (x     ,y+10  ,zl,w);
			panel.add(lblPExtras1).setBounds             (x     ,y+=90,zl,w);
			panel.add(lblPExtras2).setBounds             (x     ,y+10  ,zl,w);
			panel.add(lblcorteCajero1).setBounds               (x     ,y+=90,zl,w);
			panel.add(lblcorteCajero2).setBounds               (x     ,y+10  ,zl,w);
			panel.add(lblFsRH2).setBounds                (x     ,y+=90,zl,w);
			panel.add(lblFsRH3).setBounds                (x     ,y+10  ,zl,w);	
			panel.add(lblSolicitudes1).setBounds          (x     ,y+=90,zl,w);  
			panel.add(lblSolicitudes).setBounds           (x     ,y+10  ,zl,w);
			/* COLUMNA 2 *//////////////////////////////////////////////////////
			y=10;			
			panel.add(btnOrden_Gasto).setBounds                (x+=100,y     ,z,z);
			panel.add(btnOCI).setBounds                 (x     ,y+=90 ,z,z);
			panel.add(btnPrestamo).setBounds             (x     ,y+=90 ,z,z);
			panel.add(btnColaborador).setBounds     (x     ,y+=90 ,z,z);
			panel.add(btnListaRaya).setBounds            (x     ,y+=90 ,z,z);
			
			y = 20;		
			panel.add(lblBanco).setBounds                (x+=65 ,y     ,zl,w);
			panel.add(lblBanco2).setBounds               (x     ,y+10  ,zl,w);
			panel.add(lblCaja2).setBounds                (x     ,y+=90 ,zl,w);
			panel.add(lblCaja3).setBounds                (x     ,y+10  ,zl,w);
			panel.add(lblPrestamo2).setBounds            (x     ,y+=90 ,zl,w);
			panel.add(lblColaboradores1).setBounds    (x     ,y+=90 ,zl,w);
			panel.add(lblColaboradores2).setBounds    (x     ,y+10  ,zl,w);
			panel.add(lblListaRaya2).setBounds           (x     ,y+=90 ,zl,w);
			panel.add(lblListaRaya3).setBounds           (x     ,y+10  ,zl,w);
			
			/* COLUMNA 3 *///////////////////////////////////////////////////////
			y=10;			
			panel.add(btnPlaneacionyRevision).setBounds     (x+=100,y    ,z,z);
			panel.add(btnAlimentacionPlan_Semanal).setBounds(x     ,y+=90,z,z);
			panel.add(btnRevision_Jerarquias).setBounds     (x     ,y+=90,z,z);
			panel.add(btnImpresionCuadrante).setBounds            (x     ,y+=90,z,z);
			panel.add(btnChecador).setBounds                (x     ,y+=90,z,z);
			
			y = 20;	
			panel.add(lblPlaneacionRev1).setBounds       (x+=65 ,y     ,zl,w);
			panel.add(lblPlaneacionRev2).setBounds       (x     ,y+10  ,zl,w);
			panel.add(lblAlimentacionPlan1).setBounds    (x     ,y+=90 ,zl,w);
			panel.add(lblAlimentacionPlan12).setBounds   (x     ,y+10  ,zl,w);
			panel.add(lblRevision_Nivel_Jerarquico1).setBounds (x     ,y+=90 ,zl,w);
			panel.add(lblRevision_Nivel_Jerarquico12).setBounds(x     ,y+10  ,zl,w);
			panel.add(lblImpresioncuadrante2).setBounds              (x     ,y+=90 ,zl,w);
			panel.add(lblImpresionCuadrante4).setBounds              (x     ,y+10 ,zl,w); 
			panel.add(lblListaChecador2).setBounds             (x     ,y+=90 ,zl,w);

			/* COLUMNA 4 *///////////////////////////////////////////////////////
			y=10;
			panel.add(btnCerrar).setBounds               (x+=100,y     ,z,z);

			y = 20;
			panel.add(lblListaCerrar).setBounds          (x+=65 ,y     ,zl,w);
						
			/*LOGIN*////////////////////////////////////////////////////////////
			y=350; x=500;
			
			panel.add(lblfolio).setBounds                (x     ,y     ,100,w);
			panel.add(lblcontrasena_Actual).setBounds    (x     ,y     ,100,w);
			panel.add(lblusuario).setBounds              (x     ,y+=30 ,100,w);
			panel.add(lblcontrasena_Nueva).setBounds     (x     ,y     ,100,w);
			panel.add(lblcontrasena).setBounds           (x     ,y+=30 ,100,w);
			panel.add(lblcontrasena_Confirmar).setBounds (x     ,y     ,100,w);
			
			y=350;
			panel.add(txtFolio).setBounds                (x+=60  ,y    ,220,w);
			panel.add(txtContrasenaActual).setBounds     (x     ,y     ,220,w);
			panel.add(txtUsuario).setBounds              (x     ,y+=30 ,220,w);
			panel.add(txtContrasenaNueva).setBounds      (x     ,y     ,220,w);
			panel.add(txtContrasena).setBounds           (x     ,y+=30 ,220,w);
			panel.add(txtContrasenaConfirmar).setBounds  (x     ,y     ,220,w);
			panel.add(btnCambiarContrasena).setBounds    (x     ,y+=30 ,150,w);
			panel.add(btnGuardarContrasena).setBounds    (x     ,y     ,150,w);
			panel.add(btnValidarContrasena).setBounds    (x     ,y     ,150,w);
			panel.add(btnSalir).setBounds                (x+=150,y     ,75 ,w);
			
			y=325;
			panel.add(btnBuscar).setBounds               (x+35  ,y     ,30 ,w);
			cont.add(panel);
			this.setSize(ancho,alto);
		}
	}
	
	KeyListener enterIn = new KeyListener() {
		public void keyTyped(KeyEvent e){}
		public void keyReleased(KeyEvent e) {}
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				btnAceptar.doClick();
			}
		}
	};
	
	KeyListener enterBuscar = new KeyListener() {
		public void keyTyped(KeyEvent e){}
		public void keyReleased(KeyEvent e) {}
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				btnBuscar.doClick();
			}
		}
	};
	
	KeyListener entervalidar = new KeyListener() {
		public void keyTyped(KeyEvent e){}
		public void keyReleased(KeyEvent e) {}
		public void keyPressed(KeyEvent e) {
			
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					
						if(e.getSource().equals(txtContrasenaActual)){
							btnValidarContrasena.doClick();
						}
						if(e.getSource().equals(txtContrasenaNueva)){
							txtContrasenaConfirmar.requestFocus();
						}
						if(e.getSource().equals(txtContrasenaConfirmar)){
							btnGuardarContrasena.doClick();
						}
				}
				
		}
	};
	

	ActionListener opBuscar = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			if(txtFolio.getText().length()!=0){
				Obj_Usuario user = new Obj_Usuario().buscar(Integer.parseInt(txtFolio.getText()),cmbcolores.getSelectedItem().toString().trim());
				if(user.getFolio() != 0){
					txtFolio.setEditable(false);
					txtUsuario.setText(user.getNombre_completo());
					txtContrasena.requestFocus(true);
					btnAceptar.setEnabled(true);
					txtContrasena.setEnabled(true);
					cmbcolores.setSelectedItem(user.getColor());
				}else{
					JOptionPane.showMessageDialog(null, "El usuario no existe","Aviso",JOptionPane.WARNING_MESSAGE);
					txtFolio.requestFocus(true);
					return;
				}
			}else{
				JOptionPane.showMessageDialog(null, "Ingrese el Folio del Usuario","Aviso",JOptionPane.WARNING_MESSAGE);
				txtFolio.requestFocus(true);
				new Cat_Seleccion_De_Usuario().setVisible(true);
				return;
			}
		}		
	};
	
	ActionListener opCambiarContrasena = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			
			    txtContrasenaActual.setText("");
			    txtContrasenaNueva.setText("");
			    txtContrasenaConfirmar.setText("");
				txtContrasena.setVisible(false);
				txtFolio.setVisible(false);
				txtUsuario.setVisible(false);
				btnBuscar.setVisible(false);
				btnAceptar.setVisible(false);				
				btnCambiarContrasena.setVisible(false);
				lblfolio.setVisible(false);
				lblusuario.setVisible(false);
				lblcontrasena.setVisible(false);
							
				txtContrasenaActual.setVisible(true);
				txtContrasenaNueva.setVisible(true);
				txtContrasenaConfirmar.setVisible(true);
				
				txtContrasenaActual.setEnabled(true);
				txtContrasenaNueva.setEnabled(false);
				txtContrasenaConfirmar.setEnabled(false);

				btnValidarContrasena.setVisible(true);						
				txtContrasenaActual.requestFocus(true);					
				btnValidarContrasena.setEnabled(true);
				lblcontrasena_Actual.setVisible(true);
				lblcontrasena_Nueva.setVisible(true);	
				lblcontrasena_Confirmar.setVisible(true);
										return;
		}		
	};
	Obj_MD5 algoritmo = new Obj_MD5();
	
	ActionListener opValidarContrasena = new ActionListener(){
		@SuppressWarnings({ "deprecation", "static-access" })
		public void actionPerformed(ActionEvent arg0) {
			if(txtContrasenaActual.getText().length() != 0) {
			
			
			Obj_Usuario user = new Obj_Usuario().buscar(Integer.parseInt(txtFolio.getText()), cmbcolores.getSelectedItem().toString().trim());
			if(!algoritmo.cryptMD5(txtContrasenaActual.getText(), "izagar").trim().equals(user.getContrasena().trim())){
				
			JOptionPane.showMessageDialog(null, "La contraseña no es válida...","Aviso",JOptionPane.WARNING_MESSAGE);
			
			}else{
			txtContrasenaActual.setEnabled(false);	
			txtContrasenaNueva.setEnabled(true);
			txtContrasenaConfirmar.setEnabled(true);
			txtContrasenaNueva.requestFocus(true);
			
			btnValidarContrasena.setVisible(false);
			btnGuardarContrasena.setVisible(true);			
			btnGuardarContrasena.setEnabled(true);
			}
		
			}else{
				JOptionPane.showMessageDialog(null, "La contraseña está vacía...","Aviso",JOptionPane.WARNING_MESSAGE);
				txtContrasenaActual.requestFocus(true);
				return;
			}
		}		
	};	
	
	ActionListener opGuardarContrasena = new ActionListener(){
				
		@SuppressWarnings({ "deprecation", "static-access", "unused" })
		public void actionPerformed(ActionEvent arg0) {
			if(txtContrasenaNueva.getText().trim().toLowerCase().equals(txtContrasenaConfirmar.getText().trim().toLowerCase())) {
			String nuevacontrasena = algoritmo.cryptMD5(txtContrasenaNueva.getText().trim(), "izagar").trim();
			boolean user = new Obj_Usuario().CambiarContrasena( Integer.valueOf(txtFolio.getText()),nuevacontrasena);
			txtContrasenaActual.setText("");
			txtContrasenaNueva.setText("");
			txtContrasenaConfirmar.setText("");
			deshabilitarCambiarContrasena();
			cargar_usuariotrue();
			btnCambiarContrasena.setVisible(true);
			btnSalir.doClick();

			}else{
				JOptionPane.showMessageDialog(null, "Las Contraseñas Son Diferentes...","Aviso",JOptionPane.WARNING_MESSAGE);
				txtContrasenaNueva.setText("");
				txtContrasenaConfirmar.setText("");
				txtContrasenaNueva.requestFocus(true);
				return;
			}
		}		
	};	
	
	 
		public void deshabilitarCambiarContrasena (){
	btnValidarContrasena.setVisible(false);
	btnCambiarContrasena.setVisible(false);
	txtContrasenaActual.setVisible(false);
	txtContrasenaNueva.setVisible(false);
	txtContrasenaConfirmar.setVisible(false);
	btnGuardarContrasena.setVisible(false);
	btnAceptar.setVisible(false);
	lblcontrasena_Actual.setVisible(false);
	lblcontrasena_Nueva.setVisible(false);
	lblcontrasena_Confirmar.setVisible(false);
		};
		
		public void cargar_usuariotrue(){
			txtContrasena.setVisible(true);
			txtContrasena.setEnabled(false);
			txtFolio.setVisible(true);
			txtUsuario.setVisible(true);
			btnBuscar.setVisible(true);
			btnAceptar.setVisible(false);				
			lblfolio.setVisible(true);
			lblusuario.setVisible(true);
			lblcontrasena.setVisible(true);
			txtContrasena.setText("");
			txtContrasena.requestFocus(true);
			btnAceptar.setEnabled(true);
			txtFolio.requestFocus(true);
			btnBuscar.setEnabled(false);
					};
					
//CARGA BOTONES EN TRUE
	@SuppressWarnings("rawtypes")
	public void subMenusbotones(){
			Vector SubMenuVector = new Obj_Menus().getSubmenuNivel (Integer.parseInt(txtFolio.getText()));
			ArrayList<Submenusbtns> lsSubMenus = new ArrayList<Submenusbtns>();
			for(int i=0; i<SubMenuVector.size(); i++){
				String[] tmpSTR = String.valueOf(SubMenuVector.get(i)).split(",");
				lsSubMenus.add(new Submenusbtns(tmpSTR[0], tmpSTR[1], tmpSTR[2]));
				
				if(Integer.valueOf(tmpSTR[0].toString().trim()) == 139)
					btnPlaneacionyRevision.setEnabled(true);

				if(Integer.valueOf(tmpSTR[0].toString().trim()) == 206)
					btnOrden_Gasto.setEnabled(true);
				if(Integer.valueOf(tmpSTR[0].toString().trim()) == 141)
					btnAlimentacionPlan_Semanal.setEnabled(true);
				if(Integer.valueOf(tmpSTR[0].toString().trim()) == 41)
					btnFuenteSodasCajeras.setEnabled(true);
				if(Integer.valueOf(tmpSTR[0].toString().trim()) == 59)
					btnCortes_Cajeros.setEnabled(true);
				if(Integer.valueOf(tmpSTR[0].toString().trim()) == 183)
					btnSolSer.setEnabled(true);
				if(Integer.valueOf(tmpSTR[0].toString().trim()) == 14)
					btnColaborador.setEnabled(true);
				if(Integer.valueOf(tmpSTR[0].toString().trim()) == 229)
					btnOCI.setEnabled(true);
				if(Integer.valueOf(tmpSTR[0].toString().trim()) == 91)
					btnRetirosCajeras.setEnabled(true);
				if(Integer.valueOf(tmpSTR[0].toString().trim()) == 50)
					btnPrestamo.setEnabled(true);
				if(Integer.valueOf(tmpSTR[0].toString().trim()) == 51)
					btnListaRaya.setEnabled(true);
				if(Integer.valueOf(tmpSTR[0].toString().trim()) == 184)
					btnServicios.setEnabled(true);
				if(Integer.valueOf(tmpSTR[0].toString().trim()) == 143)
					btnRevision_Jerarquias.setEnabled(true);
				if(Integer.valueOf(tmpSTR[0].toString().trim()) == 198)
					btnChat.setEnabled(true);

		                                            }
	}
	
ActionListener Opciones = new ActionListener(){
		
		public void actionPerformed(ActionEvent click) {
			if(click.getSource().equals(btnPlaneacionyRevision))
				new Cat_Programacion_Y_Revision_Del_Plan_Semanal().setVisible(true);
			
			if(click.getSource().equals(btnOrden_Gasto))
				new Cat_Solicitud_De_Orden_De_Gasto().setVisible(true);
						
			if(click.getSource().equals(btnAlimentacionPlan_Semanal))
				new Cat_Alimentacion_De_Plan_Semanal().setVisible(true);
			
			if(click.getSource().equals(btnFuenteSodasCajeras))
				new Cat_Captura_De_Fuente_De_Sodas_De_Cajeras().setVisible(true);
			
			if(click.getSource().equals(btnCortes_Cajeros))
				new Cat_Cortes_De_Cajeros().setVisible(true);

			if(click.getSource().equals(btnSolSer))
				new Cat_Solicitud_De_Servicios().setVisible(true);
			
			if(click.getSource().equals(btnColaborador))
				new Cat_Empleados().setVisible(true);

			if(click.getSource().equals(btnImpresionCuadrante))
				new Cat_Impresion_Y_Revision_De_Cuadrante().setVisible(true);
			
			if(click.getSource().equals(btnOCI))
				new Cat_Orden_De_Compra_Interna_Solicitud().setVisible(true);
			
			if(click.getSource().equals(btnRetirosCajeras))
				new Cat_Retiros_A_Cajeros().setVisible(true);
			
			if(click.getSource().equals(btnPrestamo))
				new Cat_Prestamos().setVisible(true);
			
			if(click.getSource().equals(btnListaRaya))
				new Cat_Revision_De_Lista_Raya().setVisible(true);
			
			if(click.getSource().equals(btnServicios))
				new Cat_Seguimiento_De_Servicios().setVisible(true);
			
			if(click.getSource().equals(btnRevision_Jerarquias))
				new Cat_Revision_Y_Evidencia_De_Actividades_Por_Nivel_Jerarquico().setVisible(true);
	
			if(click.getSource().equals(btnChecador)){
				try {
	    			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    			if(new BuscarSQL().equipoAutorizadoComoChecador()){
	    				DPFPGlobal.getEnrollmentFactory().createEnrollment();
	    				new Cat_Checador().setVisible(true);	
	    			}else{
	    				JOptionPane.showMessageDialog(null, "Este Equipo No Esta Autorizado Como Checador, Favor De Comunicarse Al Departamente De Sistemas", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
			        	return;
	    			}
					
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException | Error e) {
					System.out.println(e.getMessage());
					if(e.getMessage().equals("com.digitalpersona.onetouch.jni.MatchingLibrary.init()V")){
			        	JOptionPane.showMessageDialog(null, "No Se Encontro EL Driver Del Lector De Huella", "Verificacion de Huella", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			        	return;
					}
				}
			}
			
			if(click.getSource().equals(btnChat))
				new Cat_Chat().setVisible(true);

			if(click.getSource().equals(btnCerrar)){
				dispose();			
			try {
				R.exec("taskkill /f /im javaw.exe");
			} catch (Exception e2){}
			}
		}
	};

	
public class Submenusbtns{
	public int Folio;
	public String Nombre;
	public int Menu_Id;
	public Submenusbtns(String folio, String nombre, String menu_id){
		this.Folio = Integer.valueOf(folio);
		this.Nombre = nombre;
		this.Menu_Id = Integer.valueOf(menu_id);
	}
}



//	filtro de Empleados con usuario en SCOI y con status vigente--------------------------------------------------------------------------------
		public class Cat_Seleccion_De_Usuario extends JDialog {
			
			Container cont = getContentPane();
			JLayeredPane campo = new JLayeredPane();
			
			int checkbox=-1;
			@SuppressWarnings("rawtypes")
			public Class[] tipos(int columnas){
				Class[] tip = new Class[columnas];
				
				for(int i =0; i<columnas; i++){
					if(i==checkbox){
						tip[i]=java.lang.Boolean.class;
					}else{
						tip[i]=java.lang.Object.class;
					}
					
				}
				return tip;
			}
			
			public void init_tabla(){
		    	this.tabla.getColumnModel().getColumn(0).setMinWidth(30);		
		    	this.tabla.getColumnModel().getColumn(1).setMinWidth(300);
		    	this.tabla.getColumnModel().getColumn(2).setMinWidth(410);
		    	
		    	int columnas = modelo.getColumnCount();
		    	
				String comando="exec sp_select_usuarios_scoi";
				String basedatos="26",pintar="si";
				new Obj_tabla().Obj_Refrescar(tabla,modelo, columnas, comando, basedatos,pintar,checkbox);
		    }
			
		 public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"Folio", "Nombre De Colaborador", "Establecimiento"}){
			 @SuppressWarnings("rawtypes")
				Class[] types = tipos(this.getColumnCount());
				
				@SuppressWarnings({ "unchecked", "rawtypes" })
				public Class getColumnClass(int columnIndex) {
		         return types[columnIndex];
		     }
				
		     public boolean isCellEditable(int fila, int columna){
		    	 if(columna ==checkbox)
						return true; return false;
				}
		    };
		    
		    JTable tabla = new JTable(modelo);
			public JScrollPane scroll_tabla = new JScrollPane(tabla);
			
		JTextField txtNombre_Completo2 = new Componentes().text(new JTextField(), "Buscar", 250, "String");
		
		public Cat_Seleccion_De_Usuario(){
			
			this.setModal(true);
			
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("imagen/usuario-busquedaicono-4661-64.png"));
			this.setTitle("Filtro de Empleados");
			campo.setBorder(BorderFactory.createTitledBorder("Filtro De Empleado"));
			
			campo.add(txtNombre_Completo2).setBounds(15,20,300,20);
			campo.add(scroll_tabla).setBounds(15,42,450,565);
			
			cont.add(campo);
			
			init_tabla();
			agregar(tabla);
			
			txtNombre_Completo2.addKeyListener(op_filtro);
			
			this.setSize(490,650);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			
			tabla.addKeyListener(seleccionEmpleadoconteclado);
			
		//      asigna el foco al JTextField del nombre deseado al arrancar la ventana
		    this.addWindowListener(new WindowAdapter() {
		            public void windowOpened( WindowEvent e ){
		            	txtNombre_Completo2.requestFocus();
		         }
		    });
		      
		//     pone el foco en el txtFolio al presionar la tecla scape
		      getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
		         KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "foco");
		      
		      getRootPane().getActionMap().put("foco", new AbstractAction(){
		          @Override
		          public void actionPerformed(ActionEvent e)
		          {
		        	  txtNombre_Completo2.setText("");
		              txtNombre_Completo2.requestFocus();
		          }
		      });
		      
		//        pone el foco en la tabla al presionar f4
		      getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
		         KeyStroke.getKeyStroke(KeyEvent.VK_F4 , 0), "dtabla");
		      
		      getRootPane().getActionMap().put("dtabla", new AbstractAction(){
		          @Override
		          public void actionPerformed(ActionEvent e)
		          {
		        	tabla.requestFocus();
		          }
		      });
			 
			
		}
		
		private void agregar(final JTable tbl) {
		    tbl.addMouseListener(new java.awt.event.MouseAdapter() {
		        public void mouseClicked(MouseEvent e) {
		        	if(e.getClickCount() == 2){
		    			int fila = tabla.getSelectedRow();
		    			Object folio =  tabla.getValueAt(fila, 0).toString().trim();
		    			dispose();
		    			txtFolio.setText(folio+"");
		    			btnBuscar.doClick();
		        	}
		        }
		    });
		}
		
		KeyListener op_filtro = new KeyListener(){
			public void keyReleased(KeyEvent arg0) {
				int[] columnas ={0,1,2};
				new Obj_Filtro_Dinamico_Plus(tabla, txtNombre_Completo2.getText().toString().trim().toUpperCase(), columnas);
			}
			public void keyTyped(KeyEvent arg0)   {}
			public void keyPressed(KeyEvent arg0) {}		
		};
		
		KeyListener seleccionEmpleadoconteclado = new KeyListener() {
			@SuppressWarnings("static-access")
			@Override
			public void keyTyped(KeyEvent e) {
				char caracter = e.getKeyChar();
				
				if(caracter==e.VK_ENTER){
				int fila=tabla.getSelectedRow()-1;
				String folio = tabla.getValueAt(fila,0).toString().trim();
					
				txtFolio.setText(folio);
				btnBuscar.doClick();
				dispose();
				}
			}
			@Override
			public void keyPressed(KeyEvent e){}
			@Override
			public void keyReleased(KeyEvent e){}
									
		};
		
}
					
			
}


