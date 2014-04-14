package Cat_Principal;

import java.awt.Container;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Vector;


import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import Cat_Checador.Cat_Checador;
import Cat_Principal.Init_Menu_Bar.WP_Relation;
import Cat_Principal.Init_Menu_Bar.WP_Submenu;
import Obj_Administracion_del_Sistema.Obj_MD5;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Lista_de_Raya.Obj_Autorizacion_Auditoria;
import Obj_Lista_de_Raya.Obj_Autorizacion_Finanzas;
import Obj_Principal.Obj_Menus;



@SuppressWarnings("serial")
public class Init_Login extends JFrame{

	
	public Container cont = getContentPane();
	public JLayeredPane panel = new JLayeredPane();
	
	/* BOTON DEPOSITOS A BANCO */
	JButton btnBanco= new JButton(new ImageIcon("imagen/banco.png"));
	JLabel lblBanco= new JLabel("Depositos");
	JLabel lblBanco2= new JLabel("Bancos");
	
	/* BOTON INASISTENCIA */
	JButton btnInasistencia= new JButton(new ImageIcon("imagen/inasistencia.png"));
	JLabel lblInasistencia2= new JLabel("Deduccion por");
	JLabel lblInasistencia3= new JLabel("Inasistencia");
	
	/* BOTON DIFERENCIA DE CORTES */
	JButton btnCaja= new JButton(new ImageIcon("imagen/caja2.png"));
	JLabel lblCaja2= new JLabel("Diferencia de");
	JLabel lblCaja3= new JLabel("Cortes");
	
	/* BOTON FUENTE DE SODAS DH*/
	JButton btnFsRH= new JButton(new ImageIcon("imagen/fsRH.png"));
	JLabel lblFsRH2= new JLabel("Fuente de Sodas");
	JLabel lblFsRH3= new JLabel("DH");
	
	/* BOTON FUENTE DE SODAS AUX FIN */
	JButton btnFsAux= new JButton(new ImageIcon("imagen/fsAux.png"));
	JLabel lblFsAux2= new JLabel("Fuente de Sodas");
	JLabel lblFsAux3= new JLabel("Auxiliar y Finanzas");
	
	/* BOTON PERCEPCIONES */
	JButton btnPExtras= new JButton(new ImageIcon("imagen/PExtra.png"));
	JLabel lblPExtras1= new JLabel("Percepciones");
	JLabel lblPExtras2= new JLabel("Extras");
	
	/* BOTON PRESTAMOS */
	JButton btnPrestamo= new JButton(new ImageIcon("imagen/prestamo.png"));
	JLabel lblPrestamo2= new JLabel("Prestamos");
	
	/* BOTON ALTA EMPLEADOS */
	JButton btnAltaEmp= new JButton(new ImageIcon("imagen/altaEmp.png"));
	JLabel lblAltaEmp2= new JLabel("Alta");
	JLabel lblAltaEmp3= new JLabel("Empleados");
	
	/* BOTON SOLICITUDES */
	JButton btnSolicitudes= new JButton(new ImageIcon("imagen/Solicitud-64.png"));
	JLabel lblSolicitudes= new JLabel("Solicitudes");
	
	/* BOTON LISTA DE RAYA */
	JButton btnListaRaya= new JButton(new ImageIcon("imagen/listaR.png"));
	JLabel lblListaRaya2= new JLabel("Lista de");
	JLabel lblListaRaya3= new JLabel("Raya");
	
	/* BOTON LISTA DE FIRMAS */
	JButton btnListaFirma= new JButton(new ImageIcon("imagen/listaF.png"));
	JLabel lblListaFirma2= new JLabel("Lista de");
	JLabel lblListaFirma3= new JLabel("Firmas");
	
	/* BOTON LISTA DE COMPARACION DE FUENTE DE SODAS */
	JButton btnListaComparacion= new JButton(new ImageIcon("imagen/comparacion.png"));
	JLabel lblListaComparacion2= new JLabel("Lista de");
	JLabel lblListaComparacion3= new JLabel("Comparacion FS");
	
	/* BOTON CHECADOR */
	JButton btnChecador= new JButton(new ImageIcon("imagen/checador.png"));
	JLabel lblListaChecador2= new JLabel("Checador");
	
	/*BOTON SALIR DE SCOI*/
	JButton btnCerrar= new JButton(new ImageIcon("imagen/salir_Scoi-3964-64.png"));
	JLabel lblListaCerrar= new JLabel("Cerrar SCOI");
	
	
	/* BOTON CAPTURA DE CUADRANTE PERSONAL */
	JButton btnCuadrantepersonal= new JButton(new ImageIcon("imagen/cuadrante_personal-64.png"));
	JLabel lblCuadrantepersonal = new JLabel("Captura de");
	JLabel lblCuadrantepersonal2 = new JLabel("Cuadrante Personal");
	
	/* BOTON CAPTURA DE CUADRANTE EQUIPO */
    JButton btnCuadranteequipo = new JButton(new ImageIcon("imagen/cuadrante_equipo-64.png"));
	JLabel lblCuadranteequipo = new JLabel("Captura de");
	JLabel lblCuadranteequipo2 = new JLabel("Cuadrante Equipo");
	
	/* BOTON CAPTURA DE FUENTE DE SODAS CAJERAS */
	JButton btnFuenteSodasCajeras= new JButton(new ImageIcon("imagen/captura_fuente_de_Sodas_64.png"));
	JLabel lblFuente_sodascajeras = new JLabel("Captura Fsodas");
	JLabel lblFuente_sodascajeras2 = new JLabel("por Cajeras");
	
	/* BOTONES,ETIQUETAS Y TEXTFIELDS DEL LOGIN*/
    JLabel lblfolio=new JLabel("Folio:");
    JLabel lblusuario=new JLabel("Usuario:");	
    JLabel lblcontrasena=new JLabel("Contraseña:");	

    JLabel lblcontrasena_Actual=new JLabel("Actual:");
    JLabel lblcontrasena_Nueva=new JLabel("Nueva:");	
    JLabel lblcontrasena_Confirmar=new JLabel("Confirmar:");	
	
	JTextField txtFolio = new JTextField("");
	JTextField txtUsuario = new JTextField("");
	JPasswordField txtContrasena = new JPasswordField("");
	
	JPasswordField txtContrasenaActual=new JPasswordField("");
	JPasswordField txtContrasenaNueva=new JPasswordField("");
	JPasswordField txtContrasenaConfirmar=new JPasswordField("");
	
	JButton btnSalir = new JButton("Salir");
	JButton btnAceptar = new JButton("Entrar");
	JButton btnBuscar = new JButton(new ImageIcon("Iconos/zoom_icon&16.png"));
	JButton btnCambiarContrasena =new JButton ("Cambiar Contraseña");
	
	JButton btnGuardarContrasena =new JButton ("Guardar Contraseña");
	JButton btnValidarContrasena =new JButton ("Validar Contraseña");
	
	JLabel lblLogo = new JLabel(new ImageIcon("imagen/LogPrincipal.png"));
	
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
		
		/* MANEJO DE LA RESOLUCIONES */ 
		Resolucion(ancho, alto);
		
		btnBuscar.addActionListener(opBuscar);
		txtFolio.addKeyListener(enterBuscar);
		txtContrasena.addKeyListener(enterIn);
		txtContrasenaActual.addKeyListener(entervalidar);
		
		deshabilitarCambiarContrasena();
		cargar_usuariotrue();
		
		btnCambiarContrasena.addActionListener(opCambiarContrasena);
		btnValidarContrasena.addActionListener(opValidarContrasena);
		btnGuardarContrasena.addActionListener(opGuardarContrasena);
		txtUsuario.setEditable(false);
		btnAceptar.setEnabled(false);
		
		btnBanco.addActionListener(Opciones);
		btnInasistencia.addActionListener(Opciones);
		btnSolicitudes.addActionListener(Opciones);
		btnCaja.addActionListener(Opciones);
		btnFsRH.addActionListener(Opciones);
		btnFsAux.addActionListener(Opciones);
		btnPExtras.addActionListener(Opciones);
		btnPrestamo.addActionListener(Opciones);
		btnAltaEmp.addActionListener(Opciones);
		btnCuadrantepersonal.addActionListener(Opciones);
		btnCuadranteequipo.addActionListener(Opciones);
		btnListaRaya.addActionListener(Opciones);
		btnListaFirma.addActionListener(Opciones);
		btnFuenteSodasCajeras.addActionListener(Opciones);
		btnListaComparacion.addActionListener(Opciones);
		btnChecador.addActionListener(Opciones);
		btnCerrar.addActionListener(Opciones);
		
		btnBanco.setEnabled(false);
		btnInasistencia.setEnabled(false);
		btnCaja.setEnabled(false);
		btnFsRH.setEnabled(false);
		btnFsAux.setEnabled(false);
		btnPExtras.setEnabled(false);
		btnPrestamo.setEnabled(false);
		btnAltaEmp.setEnabled(false);
		btnSolicitudes.setEnabled(false);
		btnListaRaya.setEnabled(false);
		btnListaFirma.setEnabled(false);
		btnListaComparacion.setEnabled(false);
		btnCuadrantepersonal.setEnabled(false);
		btnCuadranteequipo.setEnabled(false);
		btnFuenteSodasCajeras.setEnabled(false);
		btnChecador.setEnabled(true);
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	ActionListener Opciones = new ActionListener(){
		
		public void actionPerformed(ActionEvent click) {
			

			
//			if(arg0.getSource().equals(btnBanco))
//				new Cat_Depositos_A_Bancos().setVisible(true);
//			if(arg0.getSource().equals(btnInasistencia))
//				new Cat_Deduccion_Inasistencia().setVisible(true);
//			if(arg0.getSource().equals(btnCaja))
//				new Cat_Filtro_Diferiencia_Cortes().setVisible(true);
//			if(arg0.getSource().equals(btnFsAux))
//				new Cat_Filtro_Fue_Soda_Auxf().setVisible(true);
//			if(arg0.getSource().equals(btnFsRH))
//				new Cat_Filtro_Fue_Soda_Rh().setVisible(true);
//			if(arg0.getSource().equals(btnPExtras))
//				new Cat_Percepciones_Extra().setVisible(true);
//			if(arg0.getSource().equals(btnPrestamo))
//				new Cat_Filtro_Prestamo().setVisible(true);
//			if(arg0.getSource().equals(btnAltaEmp))
//				new Cat_Empleado().setVisible(true);
//			if(arg0.getSource().equals(btnPuesto))
//				new Cat_Puesto().setVisible(true);
//			if(arg0.getSource().equals(btnSueldo))
//				new Cat_Sueldo().setVisible(true);
//			if(arg0.getSource().equals(btnListaRaya))
//				new Cat_Revision_Lista_Raya().setVisible(true);
//			if(arg0.getSource().equals(btnListaFirma))
//				new Cat_Lista_Pago().setVisible(true);
//			if(arg0.getSource().equals(btnListaComparacion))
//				new Cat_Comprobar_Fuente_Sodas_RH().setVisible(true);
			if(click.getSource().equals(btnChecador))
				new Cat_Checador().setVisible(true);
			
			/*SALIR DE SCOI*/
			if(click.getSource().equals(btnCerrar)){
				dispose();			
			try {
				R.exec("taskkill /f /im javaw.exe");
			} catch (Exception e2){}
			}
		}
	};
	
	public void Resolucion(int ancho, int alto){
		if(ancho >= 1280){
			panel.add(lblLogo).setBounds(920,0,400,218);
			int   x = 30  ,y = 40, z = 65;
			int  zl = 120 ,w = 20;
					
			/* COLUMNA 1 *//////////////////////////////////////////////////////
			panel.add(btnFuenteSodasCajeras).setBounds   (x     ,y     ,z,z);
			panel.add(btnFsAux).setBounds                (x     ,y+=115,z,z);	
			panel.add(btnFsRH).setBounds                 (x     ,y+=115,z,z);
			panel.add(btnInasistencia).setBounds         (x     ,y+=115,z,z);
			panel.add(btnPExtras).setBounds              (x     ,y+=115,z,z);

			y = 60;
			panel.add(lblFuente_sodascajeras).setBounds  (x+=75 ,y     ,zl,w);
			panel.add(lblFuente_sodascajeras2).setBounds (x     ,y+10  ,zl,w);
			panel.add(lblFsAux2).setBounds               (x     ,y+=115,zl,w);
			panel.add(lblFsAux3).setBounds               (x     ,y+10  ,zl,w);
			panel.add(lblFsRH2).setBounds                (x     ,y+=115,zl,w);
			panel.add(lblFsRH3).setBounds                (x     ,y+10  ,zl,w);
			panel.add(lblInasistencia2).setBounds        (x     ,y+=115,zl,w);
			panel.add(lblInasistencia3).setBounds        (x     ,y+10  ,zl,w);
			panel.add(lblPExtras1).setBounds             (x     ,y+=115,zl,w);
			panel.add(lblPExtras2).setBounds             (x     ,y+10  ,zl,w);
			
			/* COLUMNA 2 *//////////////////////////////////////////////////////
			y=40;			
			panel.add(btnBanco).setBounds                (x+=140,y     ,z,z);
			panel.add(btnCaja).setBounds                 (x     ,y+=115,z,z);
			panel.add(btnPrestamo).setBounds             (x     ,y+=115,z,z);
			panel.add(btnListaComparacion).setBounds     (x     ,y+=115,z,z);
			panel.add(btnListaRaya).setBounds            (x     ,y+=115,z,z);
			
			y = 60;		
			panel.add(lblBanco).setBounds                (x+=75 ,y     ,zl,w);
			panel.add(lblBanco2).setBounds               (x     ,y+10  ,zl,w);
			panel.add(lblCaja2).setBounds                (x     ,y+=115,zl,w);
			panel.add(lblCaja3).setBounds                (x     ,y+10  ,zl,w);
			panel.add(lblPrestamo2).setBounds            (x     ,y+=115,zl,w);
			panel.add(lblListaComparacion2).setBounds    (x     ,y+=115,zl,w);
			panel.add(lblListaComparacion3).setBounds    (x     ,y+10  ,zl,w);
			panel.add(lblListaRaya2).setBounds           (x     ,y+=115,zl,w);
			panel.add(lblListaRaya3).setBounds           (x     ,y+10  ,zl,w);
			
			/* COLUMNA 3 *///////////////////////////////////////////////////////
			y=40;			
			panel.add(btnAltaEmp).setBounds              (x+=140, y    ,z,z);
			panel.add(btnSolicitudes).setBounds          (x     ,y+=115,z,z);
			panel.add(btnCuadrantepersonal).setBounds    (x     ,y+=115,z,z);
			panel.add(btnCuadranteequipo).setBounds      (x     ,y+=115,z,z);
			panel.add(btnChecador).setBounds             (x     ,y+=115,z,z);
			
			y = 60;	
			panel.add(lblAltaEmp3).setBounds             (x+=75 ,y     ,zl,w);
			panel.add(lblSolicitudes).setBounds          (x     ,y+=115,zl,w);
			panel.add(lblCuadrantepersonal).setBounds    (x     ,y+=115,zl,w);
			panel.add(lblCuadrantepersonal2).setBounds   (x     ,y+10  ,zl,w);
			panel.add(lblCuadranteequipo).setBounds      (x     ,y+=115,zl,w);
			panel.add(lblCuadranteequipo2).setBounds     (x     ,y+10  ,zl,w);
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
			panel.add(txtFolio).setBounds                (x+=60  ,y    ,220,w);
			panel.add(txtContrasenaActual).setBounds     (x     ,y     ,220,w);
			panel.add(txtUsuario).setBounds              (x     ,y+=30 ,220,w);
			panel.add(txtContrasenaNueva).setBounds      (x     ,y     ,220,w);
			panel.add(txtContrasena).setBounds           (x     ,y+=30 ,220,w);
			panel.add(txtContrasenaConfirmar).setBounds  (x     ,y     ,220,w);
			panel.add(btnCambiarContrasena).setBounds    (x     ,y+=30 ,150,w);
			panel.add(btnGuardarContrasena).setBounds    (x     ,y     ,150,w);
			panel.add(btnValidarContrasena).setBounds    (x     ,y     ,150,w);
			panel.add(btnSalir).setBounds                (x+=155,y     ,65 ,w);
			
			y=490;
			panel.add(btnBuscar).setBounds               (x+70  ,y     ,30,w);
					
			cont.add(panel);
			this.setSize(ancho,alto);
		}

		if(ancho == 1024){
			ImageIcon tmpIconAux = new ImageIcon("imagen/LogPrincipal.png");
			panel.add(new JLabel(new ImageIcon(tmpIconAux.getImage().getScaledInstance(150,150, Image.SCALE_DEFAULT)))).setBounds(690,0,400,218);
					
			int   x = 10  ,y = 10, z = 65;
			int  zl = 120 ,w = 20;
					
			/* COLUMNA 1 *//////////////////////////////////////////////////////
			panel.add(btnFuenteSodasCajeras).setBounds   (x     ,y     ,z,z);
			panel.add(btnFsAux).setBounds                (x     ,y+=115,z,z);	
			panel.add(btnFsRH).setBounds                 (x     ,y+=115,z,z);
			panel.add(btnInasistencia).setBounds         (x     ,y+=115,z,z);
			panel.add(btnPExtras).setBounds              (x     ,y+=115,z,z);

			y = 30;
			panel.add(lblFuente_sodascajeras).setBounds  (x+=75 ,y     ,zl,w);
			panel.add(lblFuente_sodascajeras2).setBounds (x     ,y+10  ,zl,w);
			panel.add(lblFsAux2).setBounds               (x     ,y+=115,zl,w);
			panel.add(lblFsAux3).setBounds               (x     ,y+10  ,zl,w);
			panel.add(lblFsRH2).setBounds                (x     ,y+=115,zl,w);
			panel.add(lblFsRH3).setBounds                (x     ,y+10  ,zl,w);
			panel.add(lblInasistencia2).setBounds        (x     ,y+=115,zl,w);
			panel.add(lblInasistencia3).setBounds        (x     ,y+10  ,zl,w);
			panel.add(lblPExtras1).setBounds             (x     ,y+=115,zl,w);
			panel.add(lblPExtras2).setBounds             (x     ,y+10  ,zl,w);
			
			/* COLUMNA 2 *//////////////////////////////////////////////////////
			y=10;			
			panel.add(btnBanco).setBounds                (x+=140,y     ,z,z);
			panel.add(btnCaja).setBounds                 (x     ,y+=115,z,z);
			panel.add(btnPrestamo).setBounds             (x     ,y+=115,z,z);
			panel.add(btnListaComparacion).setBounds     (x     ,y+=115,z,z);
			panel.add(btnListaRaya).setBounds            (x     ,y+=115,z,z);
			
			y = 30;		
			panel.add(lblBanco).setBounds                (x+=75 ,y     ,zl,w);
			panel.add(lblBanco2).setBounds               (x     ,y+10  ,zl,w);
			panel.add(lblCaja2).setBounds                (x     ,y+=115,zl,w);
			panel.add(lblCaja3).setBounds                (x     ,y+10  ,zl,w);
			panel.add(lblPrestamo2).setBounds            (x     ,y+=115,zl,w);
			panel.add(lblListaComparacion2).setBounds    (x     ,y+=115,zl,w);
			panel.add(lblListaComparacion3).setBounds    (x     ,y+10  ,zl,w);
			panel.add(lblListaRaya2).setBounds           (x     ,y+=115,zl,w);
			panel.add(lblListaRaya3).setBounds           (x     ,y+10  ,zl,w);
			
			/* COLUMNA 3 *///////////////////////////////////////////////////////
			y=10;			
			panel.add(btnAltaEmp).setBounds              (x+=140, y    ,z,z);
			panel.add(btnSolicitudes).setBounds          (x     ,y+=115,z,z);
			panel.add(btnCuadrantepersonal).setBounds    (x     ,y+=115,z,z);
			panel.add(btnCuadranteequipo).setBounds      (x     ,y+=115,z,z);
			panel.add(btnChecador).setBounds             (x     ,y+=115,z,z);
			
			y = 30;	
			panel.add(lblAltaEmp3).setBounds             (x+=75 ,y     ,zl,w);
			panel.add(lblSolicitudes).setBounds          (x     ,y+=115,zl,w);
			panel.add(lblCuadrantepersonal).setBounds    (x     ,y+=115,zl,w);
			panel.add(lblCuadrantepersonal2).setBounds   (x     ,y+10  ,zl,w);
			panel.add(lblCuadranteequipo).setBounds      (x     ,y+=115,zl,w);
			panel.add(lblCuadranteequipo2).setBounds     (x     ,y+10  ,zl,w);
			panel.add(lblListaChecador2).setBounds       (x     ,y+=115,zl,w);

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
			panel.add(txtFolio).setBounds                (x+=60  ,y    ,220,w);
			panel.add(txtContrasenaActual).setBounds     (x     ,y     ,220,w);
			panel.add(txtUsuario).setBounds              (x     ,y+=30 ,220,w);
			panel.add(txtContrasenaNueva).setBounds      (x     ,y     ,220,w);
			panel.add(txtContrasena).setBounds           (x     ,y+=30 ,220,w);
			panel.add(txtContrasenaConfirmar).setBounds  (x     ,y     ,220,w);
			panel.add(btnCambiarContrasena).setBounds    (x     ,y+=30 ,150,w);
			panel.add(btnGuardarContrasena).setBounds    (x     ,y     ,150,w);
			panel.add(btnValidarContrasena).setBounds    (x     ,y     ,150,w);
			panel.add(btnSalir).setBounds                (x+=155,y     ,65 ,w);
			
			y=400;
			panel.add(btnBuscar).setBounds               (x+70  ,y     ,30 ,w);
			
			cont.add(panel);
			this.setSize(ancho,alto);
		}
		if(ancho == 800){
			ImageIcon tmpIconAux = new ImageIcon("imagen/LogPrincipal.png");
			panel.add(new JLabel(new ImageIcon(tmpIconAux.getImage().getScaledInstance(150,150, Image.SCALE_DEFAULT)))).setBounds(500,0,400,218);
			
//			getImage().getScaledInstance(230, 195, Image.SCALE_DEFAULT)
			int   x = 10  ,y = 10, z = 65;
			int  zl = 120 ,w = 20;
					
			/* COLUMNA 1 *//////////////////////////////////////////////////////
			panel.add(btnFuenteSodasCajeras).setBounds   (x     ,y     ,z,z);
			panel.add(btnFsAux).setBounds                (x     ,y+=90,z,z);	
			panel.add(btnFsRH).setBounds                 (x     ,y+=90,z,z);
			panel.add(btnInasistencia).setBounds         (x     ,y+=90,z,z);
			panel.add(btnPExtras).setBounds              (x     ,y+=90,z,z);

			y = 20;
			panel.add(lblFuente_sodascajeras).setBounds  (x+=65 ,y     ,zl,w);
			panel.add(lblFuente_sodascajeras2).setBounds (x     ,y+10  ,zl,w);
			panel.add(lblFsAux2).setBounds               (x     ,y+=90,zl,w);
			panel.add(lblFsAux3).setBounds               (x     ,y+10  ,zl,w);
			panel.add(lblFsRH2).setBounds                (x     ,y+=90,zl,w);
			panel.add(lblFsRH3).setBounds                (x     ,y+10  ,zl,w);
			panel.add(lblInasistencia2).setBounds        (x     ,y+=90,zl,w);
			panel.add(lblInasistencia3).setBounds        (x     ,y+10  ,zl,w);
			panel.add(lblPExtras1).setBounds             (x     ,y+=90,zl,w);
			panel.add(lblPExtras2).setBounds             (x     ,y+10  ,zl,w);
			
			/* COLUMNA 2 *//////////////////////////////////////////////////////
			y=10;			
			panel.add(btnBanco).setBounds                (x+=100,y     ,z,z);
			panel.add(btnCaja).setBounds                 (x     ,y+=90,z,z);
			panel.add(btnPrestamo).setBounds             (x     ,y+=90,z,z);
			panel.add(btnListaComparacion).setBounds     (x     ,y+=90,z,z);
			panel.add(btnListaRaya).setBounds            (x     ,y+=90,z,z);
			
			y = 20;		
			panel.add(lblBanco).setBounds                (x+=65 ,y     ,zl,w);
			panel.add(lblBanco2).setBounds               (x     ,y+10  ,zl,w);
			panel.add(lblCaja2).setBounds                (x     ,y+=90,zl,w);
			panel.add(lblCaja3).setBounds                (x     ,y+10  ,zl,w);
			panel.add(lblPrestamo2).setBounds            (x     ,y+=90,zl,w);
			panel.add(lblListaComparacion2).setBounds    (x     ,y+=90,zl,w);
			panel.add(lblListaComparacion3).setBounds    (x     ,y+10  ,zl,w);
			panel.add(lblListaRaya2).setBounds           (x     ,y+=90,zl,w);
			panel.add(lblListaRaya3).setBounds           (x     ,y+10  ,zl,w);
			
			/* COLUMNA 3 *///////////////////////////////////////////////////////
			y=10;			
			panel.add(btnAltaEmp).setBounds              (x+=100, y    ,z,z);
			panel.add(btnSolicitudes).setBounds          (x     ,y+=90,z,z);
			panel.add(btnCuadrantepersonal).setBounds    (x     ,y+=90,z,z);
			panel.add(btnCuadranteequipo).setBounds      (x     ,y+=90,z,z);
			panel.add(btnChecador).setBounds             (x     ,y+=90,z,z);
			
			y = 20;	
			panel.add(lblAltaEmp3).setBounds             (x+=65 ,y     ,zl,w);
			panel.add(lblSolicitudes).setBounds          (x     ,y+=90,zl,w);
			panel.add(lblCuadrantepersonal).setBounds    (x     ,y+=90,zl,w);
			panel.add(lblCuadrantepersonal2).setBounds   (x     ,y+10  ,zl,w);
			panel.add(lblCuadranteequipo).setBounds      (x     ,y+=90,zl,w);
			panel.add(lblCuadranteequipo2).setBounds     (x     ,y+10  ,zl,w);
			panel.add(lblListaChecador2).setBounds       (x     ,y+=90,zl,w);

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
			panel.add(btnSalir).setBounds                (x+=155,y     ,65 ,w);
			
			y=325;
			panel.add(btnBuscar).setBounds               (x+35  ,y     ,30 ,w);

			cont.add(panel);
			this.setSize(ancho,alto);
		}
		lblLogo.addMouseListener ( new  MouseAdapter ()  
		{  
			public void mouseReleased (MouseEvent e)  
			{  
	    		new Cat_Checador().setVisible(true);
	    	}  
		});
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
				btnValidarContrasena.doClick();
			}
		}
	};
	

	ActionListener opBuscar = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			if(txtFolio.getText().length()!=0){
				Obj_Usuario user = new Obj_Usuario().buscar(Integer.parseInt(txtFolio.getText()));
				if(user.getFolio() != 0){
					txtFolio.setEditable(false);
					txtUsuario.setText(user.getNombre_completo());
					txtContrasena.requestFocus(true);
					btnAceptar.setEnabled(true);
					txtContrasena.setEnabled(true);
					
				}else{
					JOptionPane.showMessageDialog(null, "El usuario no existe","Aviso",JOptionPane.WARNING_MESSAGE);
					txtFolio.requestFocus(true);
					return;
				}
			}else{
				JOptionPane.showMessageDialog(null, "Ingrese el Folio del Usuario","Aviso",JOptionPane.WARNING_MESSAGE);
				txtFolio.requestFocus(true);
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
			
			
			Obj_Usuario user = new Obj_Usuario().buscar(Integer.parseInt(txtFolio.getText()));
			if(!algoritmo.cryptMD5(txtContrasenaActual.getText(), "izagar").trim().toLowerCase().equals(user.getContrasena().trim().toLowerCase())){
				
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
				
			String nuevacontrasena = algoritmo.cryptMD5(txtContrasenaNueva.getText().trim().toLowerCase(), "izagar").trim().toLowerCase();
			boolean user = new Obj_Usuario().CambiarContrasena( Integer.valueOf(txtFolio.getText()),nuevacontrasena);
			
			System.out.println("valido"+nuevacontrasena);
			txtContrasenaActual.setText("");
			txtContrasenaNueva.setText("");
			txtContrasenaConfirmar.setText("");
			
			
			deshabilitarCambiarContrasena();
			cargar_usuariotrue();
			btnCambiarContrasena.setVisible(true);

			}else{
				
				JOptionPane.showMessageDialog(null, "Las contraseñas son diferentes...","Aviso",JOptionPane.WARNING_MESSAGE);
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
			btnBuscar.setEnabled(true);
			btnAceptar.setEnabled(true);
			txtFolio.requestFocus(true);
			
					};
					
			
	@SuppressWarnings("rawtypes")
	public void subMenusbotones(){
		Obj_Autorizacion_Auditoria auditoria = new Obj_Autorizacion_Auditoria().buscar();
		Obj_Autorizacion_Finanzas finanzas = new Obj_Autorizacion_Finanzas().buscar();
					
		boolean auditoriaBoolean = auditoria.isAutorizar();
		boolean finanzasBoolean = finanzas.isAutorizar();
						
		if((auditoriaBoolean == false)  || (finanzasBoolean == true)){
			
			Vector SubMenuVector = new Obj_Menus().getSubmenuNivel (Integer.parseInt(txtFolio.getText()));
			ArrayList<Submenusbtns> lsSubMenus = new ArrayList<Submenusbtns>();
			for(int i=0; i<SubMenuVector.size(); i++){
				String[] tmpSTR = String.valueOf(SubMenuVector.get(i)).split(",");
				lsSubMenus.add(new Submenusbtns(tmpSTR[0], tmpSTR[1], tmpSTR[2]));
				
				if(Integer.valueOf(tmpSTR[0].toString().trim()) == 14)
					btnAltaEmp.setEnabled(true);

					}
				    
			     {			   
			}
		}

	}

	
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
					
			
				
//								for(int i=0; i<SubMenuVectorbtn.length; i++){
//					               /*AUDITORIA*/
//									if(permisos[i].equals("Reporte de Movimientos Operados"))
//										Reporte_de_Movimientos_Operados.setEnabled(true);
//									
//									if(permisos[i].equals("Captura de Cortes de Cajeras"))
//										Captura_Cortes .setEnabled(true);
//									
//									
//									/* CATALOGO */
//									if(permisos[i].equals("Nuevo Departamento")){
//										Catalogo_Departamento.setEnabled(false);
//									}
//									if(permisos[i].equals("Nuevo Empleado")){
//										Catalogo_Empleado.setEnabled(false);
//										btnAltaEmp.setEnabled(false);
//									}
//									if(permisos[i].equals("Nuevo Establecimiento"))
//										Catalogo_Establecimiento.setEnabled(true);
//									if(permisos[i].equals("Nuevo Puesto")){
//										Catalogo_Puesto.setEnabled(true);
//										btnPuesto.setEnabled(true);
//									}
//									if(permisos[i].equals("Nuevo Rango de Prestamo"))
//										Catalogo_Rango_Prestamo.setEnabled(true);
//									if(permisos[i].equals("Nuevo Sueldo")){
//										Catalogo_Sueldo.setEnabled(false);
//										btnSueldo.setEnabled(false);
//									}
//									if(permisos[i].equals("Nuevo Tipos de Bancos"))
//										Catalogo_Tipo_Banco.setEnabled(true);
//									if(permisos[i].equals("Nuevo Turno"))
//										Catalogo_Turno.setEnabled(true);
//									
//									/* CONFIGURACION */
//									if(permisos[i].equals("Configuración de Asistencia y Puntualidad"))
//										Configuracion_Asistencia_Puntualidad.setEnabled(false);
//									if(permisos[i].equals("Configuración de Base de Datos"))
//										Configuracion_ConexionBD.setEnabled(true);
//									if(permisos[i].equals("Configuración de Bono"))
//										Configuracion_Bono.setEnabled(false);
//									if(permisos[i].equals("Configuración de Denominaciones"))
//										Configuracion_Denominaciones.setEnabled(true);
//									if(permisos[i].equals("Configuración de Divisas y Tipo de Cambio"))
//										Configuracion_Divisas.setEnabled(true);
//									if(permisos[i].equals("Configuración Mantenimiento Base de Datos"))
//										Configuracion_Mantenimiento.setEnabled(true);
//									if(permisos[i].equals("Configuración de Sistema"))
//										Configuracion_Sistema.setEnabled(true);
//									if(permisos[i].equals("Configuración de Usuarios"))
//										Configuracion_Usuario.setEnabled(true);
//									
//									/* CONTABILIDAD */
//									if(permisos[i].equals("Importar Auxiliar"))
//										Importar_Auxiliar.setEnabled(true);
//									if(permisos[i].equals("Importar Cheques"))
//										Importar_Cheques.setEnabled(true);
//									if(permisos[i].equals("Importar Conciliación AuxF"))
//										Importar_Consiliacion.setEnabled(true);
//									if(permisos[i].equals("Importar Voucher"))
//										Importar_Voucher.setEnabled(true);
//									if(permisos[i].equals("Reporte de Apartados y Abonos en una Asignacion"))
//										Egresos_Reporte_de_apartados_y_abonos.setEnabled(true);
//																	
//								
//									/* CUADRANTES 
//									*		ALIMENTACION */
//									if(permisos[i].equals("Alimentación de Cuadrantes"))
//										Cuadrantes_Alimentacion_Actividades_Cuadrantes.setEnabled(true);
//									if(permisos[i].equals("Cuadrante"))
//										Cuadrantes_Alimentacion_Cuadrante.setEnabled(true);
//									if(permisos[i].equals("Empleados en Cuadrantes"))
//										Cuadrantes_Alimentacion_Empleados_Cuadrantes.setEnabled(true);	
//									if(permisos[i].equals("Asignación de Actividades por Nivel Jerarquico"))
//										Cuadrantes_Alimentacion_Asignacion_Actividades_Nivel_Jerarquico.setEnabled(true);
//									
//									/* CUADRANTES
//									 * 		CATALOGO */
//									if(permisos[i].equals("Actividades"))
//										Cuadrantes_Catalogo_Actividades.setEnabled(true);
//									if(permisos[i].equals("Asignación de Telefonos"))
//										Cuadrantes_Catalogo_Telefono.setEnabled(true);
//									if(permisos[i].equals("Atributos"))
//										Cuadrantes_Catalogo_Atributos.setEnabled(true);
//									if(permisos[i].equals("Equipo de Trabajo"))
//										Cuadrantes_Catalogo_Equipo_Trabajo.setEnabled(true);
//									if(permisos[i].equals("Jefatura"))
//										Cuadrantes_Catalogo_Jefatura.setEnabled(true);
//									if(permisos[i].equals("Nivel Crítico"))
//										Cuadrantes_Catalogo_Nivel_Critico.setEnabled(true);
//									if(permisos[i].equals("Nivel Jerarquico"))
//										Cuadrantes_Catalogo_Nivel_Jerarquico.setEnabled(true);
//									if(permisos[i].equals("Opciones de Respuesta"))
//										Cuadrantes_Catalogo_Respuesta.setEnabled(true);
//									if(permisos[i].equals("Opciones Múltiple de Respuesta"))
//										Cuadrantes_Catalogo_Respuesta_Multiple.setEnabled(true);
//									if(permisos[i].equals("Ponderacion"))
//										Cuadrantes_Catalogo_Ponderacion.setEnabled(true);
//									/* CUADRANTES
//									*		REPORTE */
//								
//								    if(permisos[i].equals("Impresion de Cuadrante Personal"));
//									 Impresion_Cuadrante_Personal.setEnabled(true);
//								    if(permisos[i].equals("Reportes Directivo"))
//										Cuadrantes_Reportes_Directivo.setEnabled(true);
//									if(permisos[i].equals("Reporte Dinamico de Cuadrantes"))
//										
//										Cuadrantes_Reportes_Dinamico.setEnabled(true);
//									if(permisos[i].equals("Reportes Usuario"))
//										Cuadrantes_Reportes_Usuario.setEnabled(true);
//													
//									/* LISTA DE RAYA 
//									*		ALIMENTACION */
//									if(permisos[i].equals("Alimentación Bancos")){
//										Alimentacion_Bancos.setEnabled(false);
//										btnBanco.setEnabled(false);
//									}
//									if(permisos[i].equals("Alimentación de Fuente de Sodas Por Cajeras"))
//										Alimentacion_Cajeras_de_Fuente_Sodas.setEnabled(true);
//									
//									if(permisos[i].equals("Alimentación de Totales de Nómina"))
//										Alimentacion_Captura_Totales_Nomina.setEnabled(false);
//									if(permisos[i].equals("Alimentación Deducción por Inasistencia")){
//										Alimentacion_Deducciones_Asistencia.setEnabled(false);
//										btnInasistencia.setEnabled(false);
//									}
//									if(permisos[i].equals("Alimentación Diferencia de Cortes")){
//										Alimentacion_Diferencia_Cortes.setEnabled(false);
//										btnCaja.setEnabled(false);
//									}
//									if(permisos[i].equals("Alimentación Fuente de Sodas AUXF")){
//										Alimentacion_Fuente_Sodas_auxf.setEnabled(false);
//										btnFsAux.setEnabled(false);
//									}
//									if(permisos[i].equals("Alimentación Fuente de Sodas AUXF Selecionable")){
//										Alimentacion_FS_auxf_seleccionable.setEnabled(false);
//										btnFsAux.setEnabled(false);
//									}
//									if(permisos[i].equals("Alimentación Fuente de Sodas DH")){
//										Alimentacion_Fuente_Sodas_rh.setEnabled(false);
//										btnFsRH.setEnabled(false);
//									}
//									if(permisos[i].equals("Alimentación Fuente de Sodas DH Selecionable")){
//										Alimentacion_FS_dh_seleccionable.setEnabled(false);
//										btnFsRH.setEnabled(false);
//									}
//									if(permisos[i].equals("Alimentación Percepciones Extras")){
//										Alimentacion_Percepciones_Extra.setEnabled(false);
//										btnPExtras.setEnabled(false);
//									}
//									if(permisos[i].equals("Alimentación Prestamos")){
//										Alimentacion_Prestamos.setEnabled(false);
//										btnPrestamo.setEnabled(false);
//									}
//									/* LISTA DE RAYA 	
//									*		AUTORIZACIONES */
//									if(permisos[i].equals("Autorizacion Auditoria"))
//										Autorizacion_Auditoria.setEnabled(true);
//									if(permisos[i].equals("Autorizacion Finanzas"))
//										Autorizacion_Finanzas.setEnabled(true);
//									/* LISTA DE RAYA 	
//									*		COMPARACIONES */
//									if(permisos[i].equals("Lista de Comparación FS.")){
//										Comparaciones_Listas_Fuente_Sodas.setEnabled(false);
//										btnListaComparacion.setEnabled(false);
//									}
//									if(permisos[i].equals("Lista de Raya")){
//										Comparaciones_Listas_Raya.setEnabled(true);
//										btnListaRaya.setEnabled(true);
//									}
//									/* LISTA DE RAYA 	
//									*		CHECADOR */
//									if(permisos[i].equals("Asignacion de Horario de Temporada")){
//										Asignacion_Horario_Temporada.setEnabled(true);
//									   }
//									if(permisos[i].equals("Checador")){
//										Checador_Menu.setEnabled(true);
//									   }
//									if(permisos[i].equals("Dias Inhabiles")){
//										Dias_Inhabiles.setEnabled(false);
//												}
//									if(permisos[i].equals("Generacion de Gafetes de Empleados")){
//										Generacion_Gafetes_Empleados.setEnabled(true);
//												}
//									if(permisos[i].equals("Horarios")){
//										Horarios.setEnabled(true);
//												}
//									if(permisos[i].equals("Mensajes Personales a Empleados")){
//										Mensajes_Personales.setEnabled(true);
//												}
//									if(permisos[i].equals("Permisos a Empleados")){
//										Permisos_Empleados.setEnabled(true);
//												}
//									/* LISTA DE RAYA 
//									*		DEPARTAMENTO DE CORTES */
//									if(permisos[i].equals("Alimentación de Cortes"))
//										Departamento_Cortes_Alimentacion.setEnabled(false);
//									/* LISTA DE RAYA 
//									*		REPORTES */
//									if(permisos[i].equals("Reportes de Asistencia y Retardos del Dia")){
//										Reportes_del_Dia.setEnabled(true);
//												}
//									if(permisos[i].equals("Reporte General de Asistencia")){
//										Reportes_Checador_Gral.setEnabled(true);}
//									if(permisos[i].equals("Reporte Deducciones Por Inasistencia"))
//										Reporte_Deducciones_Inasistencia.setEnabled(true);
//									if(permisos[i].equals("Reporte de  Plantilla de Personal con Horario"))
//										Reporte_de_Plantilla_de_Personal_con_Horario.setEnabled(true);
//									
//									
//									if(permisos[i].equals("Reporte Depositos A Bancos"))
//										Reporte_Bancos.setEnabled(true);
//									if(permisos[i].equals("Reporte Fuente Sodas"))
//										Reporte_Fuente_Sodas.setEnabled(true);
//									if(permisos[i].equals("Reporte Lista de Firmas")){
//										Reporte_Lista_Firma.setEnabled(true);
//										btnListaFirma.setEnabled(true);	}
//									if(permisos[i].equals("Reporte Lista de Raya"))
//										Reporte_Lista_Raya.setEnabled(true);
//					
//									if(permisos[i].equals("Reporte Prestamos"))
//										Reporte_Prestamos.setEnabled(true);
//									
//									/*SOLICITUDES*/	
//									if(permisos[i].equals("Revisión de Solicitudes por Consejo"))
//										Revision_de_Consejo.setEnabled(true);
//									if(permisos[i].equals("Revisión de Solicitudes por Jefe de Operaciones"))
//										Revision_de_Jefe_de_Operaciones.setEnabled(true);
//									if(permisos[i].equals("Solicitud de Empleados"))
//										Solicitud_de_Empleados.setEnabled(true);
//									/*VACACIONES*/
//									if(permisos[i].equals("Grupos de Vacaciones"))
//										Grupos_de_Vacaciones.setEnabled(true);
//								}
//							}else{
//								Object[] permisos = new Obj_Principal.Obj_Submenus_Login().Permisos (Integer.valueOf(txtFolio.getText()));
//								for(int i=0; i<permisos.length; i++){
									
//						           /*AUDITORIA*/
//								    if(permisos[i].equals("Reporte de Movimientos Operados"))
//											Reporte_de_Movimientos_Operados.setEnabled(true);
//									if(permisos[i].equals("Captura de Cortes de Cajeras"))
//											Captura_Cortes .setEnabled(true);
//									
//									/* CATALOGO */
//									if(permisos[i].equals("Nuevo Departamento")){
//										Catalogo_Departamento.setEnabled(true);
//									}
//									if(permisos[i].equals("Nuevo Empleado")){
//										Catalogo_Empleado.setEnabled(true);
//										btnAltaEmp.setEnabled(true);
//									}
//									if(permisos[i].equals("Nuevo Establecimiento"))
//										Catalogo_Establecimiento.setEnabled(true);
//									if(permisos[i].equals("Nuevo Puesto")){
//										Catalogo_Puesto.setEnabled(true);
//										btnPuesto.setEnabled(true);
//									}
//									if(permisos[i].equals("Nuevo Rango de Prestamo"))
//										Catalogo_Rango_Prestamo.setEnabled(true);
//									if(permisos[i].equals("Nuevo Sueldo")){
//										Catalogo_Sueldo.setEnabled(true);
//										btnSueldo.setEnabled(true);
//									}
//									if(permisos[i].equals("Nuevo Tipos de Bancos"))
//										Catalogo_Tipo_Banco.setEnabled(true);
//									if(permisos[i].equals("Nuevo Turno"))
//										Catalogo_Turno.setEnabled(true);
//									
//									/* CONFIGURACION */
//									if(permisos[i].equals("Configuración de Asistencia y Puntualidad"))
//										Configuracion_Asistencia_Puntualidad.setEnabled(true);
//									if(permisos[i].equals("Configuración de Base de Datos"))
//										Configuracion_ConexionBD.setEnabled(true);
//									if(permisos[i].equals("Configuración de Bono"))
//										Configuracion_Bono.setEnabled(true);
//									if(permisos[i].equals("Configuración de Denominaciones"))
//										Configuracion_Denominaciones.setEnabled(true);
//									if(permisos[i].equals("Configuración de Divisas y Tipo de Cambio"))
//										Configuracion_Divisas.setEnabled(true);
//									if(permisos[i].equals("Configuración Mantenimiento Base de Datos"))
//										Configuracion_Mantenimiento.setEnabled(true);
//									if(permisos[i].equals("Configuración de Sistema"))
//										Configuracion_Sistema.setEnabled(true);
//									if(permisos[i].equals("Configuración de Usuarios"))
//										Configuracion_Usuario.setEnabled(true);
//									
//									/* CONTABILIDAD */
//									if(permisos[i].equals("Importar Auxiliar"))
//										Importar_Auxiliar.setEnabled(true);
//									if(permisos[i].equals("Importar Cheques"))
//										Importar_Cheques.setEnabled(true);
//									if(permisos[i].equals("Importar Conciliación AuxF"))
//										Importar_Consiliacion.setEnabled(true);
//									if(permisos[i].equals("Importar Voucher"))
//										Importar_Voucher.setEnabled(true);
//									if(permisos[i].equals("Reporte de Apartados y Abonos en una Asignacion"))
//										Egresos_Reporte_de_apartados_y_abonos.setEnabled(true);
//									if(permisos[i].equals("Reporte de Apartados y Abonos en una Asignacion"))
//										Egresos_Reporte_de_apartados_y_abonos.setEnabled(true);
//
//								
//											
//									/* CUADRANTES 
//									*		ALIMENTACION */
//								    if(permisos[i].equals("Impresion de Cuadrante Personal"));
//									 Impresion_Cuadrante_Personal.setEnabled(true);
//									if(permisos[i].equals("Alimentación de Cuadrantes"))
//										Cuadrantes_Alimentacion_Actividades_Cuadrantes.setEnabled(true);
//									if(permisos[i].equals("Cuadrante"))
//										Cuadrantes_Alimentacion_Cuadrante.setEnabled(true);
//									if(permisos[i].equals("Empleados en Cuadrantes"))
//										Cuadrantes_Alimentacion_Empleados_Cuadrantes.setEnabled(true);	
//									if(permisos[i].equals("Asignación de Actividades por Nivel Jerarquico"))
//										Cuadrantes_Alimentacion_Asignacion_Actividades_Nivel_Jerarquico.setEnabled(true);	
//									
//							
//									/* CUADRANTES
//									 * 		CATALOGO */
//									if(permisos[i].equals("Actividades"))
//										Cuadrantes_Catalogo_Actividades.setEnabled(true);
//									if(permisos[i].equals("Asignación de Telefonos"))
//										Cuadrantes_Catalogo_Telefono.setEnabled(true);
//									if(permisos[i].equals("Atributos"))
//										Cuadrantes_Catalogo_Atributos.setEnabled(true);
//									if(permisos[i].equals("Equipo de Trabajo"))
//										Cuadrantes_Catalogo_Equipo_Trabajo.setEnabled(true);
//									if(permisos[i].equals("Jefatura"))
//										Cuadrantes_Catalogo_Jefatura.setEnabled(true);
//									if(permisos[i].equals("Nivel Crítico"))
//										Cuadrantes_Catalogo_Nivel_Critico.setEnabled(true);
//									if(permisos[i].equals("Nivel Jerarquico"))
//										Cuadrantes_Catalogo_Nivel_Jerarquico.setEnabled(true);
//									if(permisos[i].equals("Opciones de Respuesta"))
//										Cuadrantes_Catalogo_Respuesta.setEnabled(true);
//									if(permisos[i].equals("Opciones Múltiple de Respuesta"))
//										Cuadrantes_Catalogo_Respuesta_Multiple.setEnabled(true);
//									if(permisos[i].equals("Ponderacion"))
//										Cuadrantes_Catalogo_Ponderacion.setEnabled(true);
//									/* CUADRANTES
//									*		REPORTE */
//									if(permisos[i].equals("Reportes Directivo"))
//										Cuadrantes_Reportes_Directivo.setEnabled(true);
//									 if(permisos[i].equals("Reporte Dinamico de Cuadrantes"))
//										 Cuadrantes_Reportes_Dinamico.setEnabled(true);
//									if(permisos[i].equals("Reportes Usuario"))
//										Cuadrantes_Reportes_Usuario.setEnabled(true);
//													
//									/* LISTA DE RAYA 
//									*		ALIMENTACION */
//									if(permisos[i].equals("Alimentación Bancos")){
//										Alimentacion_Bancos.setEnabled(true);
//										btnBanco.setEnabled(true);
//									}
//									if(permisos[i].equals("Alimentación de Fuente de Sodas Por Cajeras"))
//										Alimentacion_Cajeras_de_Fuente_Sodas.setEnabled(true);
//									
//									if(permisos[i].equals("Alimentación de Totales de Nómina"))
//										Alimentacion_Captura_Totales_Nomina.setEnabled(true);
//									if(permisos[i].equals("Alimentación Deducción por Inasistencia")){
//										Alimentacion_Deducciones_Asistencia.setEnabled(true);
//										btnInasistencia.setEnabled(true);
//									}
//									if(permisos[i].equals("Alimentación Diferencia de Cortes")){
//										Alimentacion_Diferencia_Cortes.setEnabled(true);
//										btnCaja.setEnabled(true);
//									}
//									if(permisos[i].equals("Alimentación Fuente de Sodas AUXF")){
//										Alimentacion_Fuente_Sodas_auxf.setEnabled(true);
//										btnFsAux.setEnabled(true);
//									}
//									if(permisos[i].equals("Alimentación Fuente de Sodas AUXF Selecionable")){
//										Alimentacion_FS_auxf_seleccionable.setEnabled(true);
//										btnFsAux.setEnabled(true);
//									}
//									if(permisos[i].equals("Alimentación Fuente de Sodas DH")){
//										Alimentacion_Fuente_Sodas_rh.setEnabled(true);
//										btnFsRH.setEnabled(true);
//									}
//									if(permisos[i].equals("Alimentación Fuente de Sodas DH Selecionable")){
//										Alimentacion_FS_dh_seleccionable.setEnabled(true);
//										btnFsRH.setEnabled(true);
//									}
//									if(permisos[i].equals("Alimentación Percepciones Extras")){
//										Alimentacion_Percepciones_Extra.setEnabled(true);
//										btnPExtras.setEnabled(true);
//									}
//									if(permisos[i].equals("Alimentación Prestamos")){
//										Alimentacion_Prestamos.setEnabled(true);
//										btnPrestamo.setEnabled(true);
//									}
//									/* LISTA DE RAYA 	
//									*		AUTORIZACIONES */
//									if(permisos[i].equals("Autorizacion Auditoria"))
//										Autorizacion_Auditoria.setEnabled(true);
//									if(permisos[i].equals("Autorizacion Finanzas"))
//										Autorizacion_Finanzas.setEnabled(true);
//									/* LISTA DE RAYA 	
//									*		COMPARACIONES */
//									if(permisos[i].equals("Lista de Comparación FS.")){
//										Comparaciones_Listas_Fuente_Sodas.setEnabled(true);
//										btnListaComparacion.setEnabled(true);
//									}
//									if(permisos[i].equals("Lista de Raya")){
//										Comparaciones_Listas_Raya.setEnabled(true);
//										btnListaRaya.setEnabled(true);
//									}
//									/* LISTA DE RAYA 	
//									*		CHECADOR */
//									if(permisos[i].equals("Asignacion de Horario de Temporada")){
//										Asignacion_Horario_Temporada.setEnabled(true);
//									   }
//									if(permisos[i].equals("Checador")){
//										Checador_Menu.setEnabled(true);
//									   }
//									if(permisos[i].equals("Dias Inhabiles")){
//										Dias_Inhabiles.setEnabled(true);
//									}
//									if(permisos[i].equals("Generacion de Gafetes de Empleados")){
//										Generacion_Gafetes_Empleados.setEnabled(true);
//												}
//									if(permisos[i].equals("Horarios")){
//										Horarios.setEnabled(true);
//												}
//									if(permisos[i].equals("Mensajes Personales a Empleados")){
//										Mensajes_Personales.setEnabled(true);
//												}
//									if(permisos[i].equals("Permisos a Empleados")){
//										Permisos_Empleados.setEnabled(true);
//									            }
//
//									/* LISTA DE RAYA 
//									*		DEPARTAMENTO DE CORTES */
//									if(permisos[i].equals("Alimentación de Cortes"))
//										Departamento_Cortes_Alimentacion.setEnabled(true);
//									/* LISTA DE RAYA 
//									*		REPORTES */
//									if(permisos[i].equals("Reportes de Asistencia y Retardos del Dia")){
//										Reportes_del_Dia.setEnabled(true); 	}  
//									if(permisos[i].equals("Reporte General de Asistencia")){
//										Reportes_Checador_Gral.setEnabled(true);}
//									if(permisos[i].equals("Reporte Deducciones Por Inasistencia"))
//										Reporte_Deducciones_Inasistencia.setEnabled(true);
//									if(permisos[i].equals("Reporte de  Plantilla de Personal con Horario"))
//										Reporte_de_Plantilla_de_Personal_con_Horario.setEnabled(true);				
//									
//									if(permisos[i].equals("Reporte Depositos A Bancos"))
//										Reporte_Bancos.setEnabled(true);
//									if(permisos[i].equals("Reporte Fuente Sodas"))
//										Reporte_Fuente_Sodas.setEnabled(true);
//									if(permisos[i].equals("Reporte Lista de Firmas")){
//										Reporte_Lista_Firma.setEnabled(true);
//										btnListaFirma.setEnabled(true);	}
//									if(permisos[i].equals("Reporte Lista de Raya"))
//										Reporte_Lista_Raya.setEnabled(true);
//									if(permisos[i].equals("Reporte Prestamos"))
//										Reporte_Prestamos.setEnabled(true);
//									/*SOLICITUDES*/	
//									if(permisos[i].equals("Revisión de Solicitudes por Consejo"))
//										Revision_de_Consejo.setEnabled(true);
//									if(permisos[i].equals("Revisión de Solicitudes por Jefe de Operaciones"))
//										Revision_de_Jefe_de_Operaciones.setEnabled(true);
//									if(permisos[i].equals("Solicitud de Empleados"))
//										Solicitud_de_Empleados.setEnabled(true);
//									/*VACACIONES*/
//									if(permisos[i].equals("Grupos de Vacaciones"))
//										Grupos_de_Vacaciones.setEnabled(true);
								
	




			
		
}


