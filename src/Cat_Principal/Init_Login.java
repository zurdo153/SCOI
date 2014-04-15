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
import Cat_Evaluaciones.Cat_Captura_Del_Cuadrante_Personal;
import Cat_Lista_de_Raya.Cat_Depositos_A_Bancos;
import Cat_Lista_de_Raya.Cat_Empleados;
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
			

//			btnBanco.addActionListener(Opciones);
//			btnInasistencia.addActionListener(Opciones);
//			btnSolicitudes.addActionListener(Opciones);
//			btnCaja.addActionListener(Opciones);
//			btnFsRH.addActionListener(Opciones);
//			btnFsAux.addActionListener(Opciones);
//			btnPExtras.addActionListener(Opciones);
//			btnPrestamo.addActionListener(Opciones);
//			btnCuadrantepersonal.addActionListener(Opciones);
//			btnCuadranteequipo.addActionListener(Opciones);
//			btnListaRaya.addActionListener(Opciones);
//			btnListaFirma.addActionListener(Opciones);
//			btnFuenteSodasCajeras.addActionListener(Opciones);
//			btnListaComparacion.addActionListener(Opciones);

			if(click.getSource().equals(btnAltaEmp))
				new Cat_Empleados().setVisible(true);
			
			if(click.getSource().equals(btnBanco))
				new Cat_Depositos_A_Bancos().setVisible(true);
						
			if(click.getSource().equals(btnCuadrantepersonal))
				new Cat_Captura_Del_Cuadrante_Personal(txtUsuario.getText()).setVisible(true);
			

			if(click.getSource().equals(btnChecador))
				new Cat_Checador().setVisible(true);

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
				if(Integer.valueOf(tmpSTR[0].toString().trim()) == 24)
					btnBanco.setEnabled(true);
				if(Integer.valueOf(tmpSTR[0].toString().trim()) == 25)
					btnCuadrantepersonal.setEnabled(true);


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
					
			
}


