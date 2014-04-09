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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Cat_Checador.Cat_Checador;
import Obj_Administracion_del_Sistema.Obj_MD5;
import Obj_Administracion_del_Sistema.Obj_Usuario;



@SuppressWarnings("serial")
public class Init_Login extends JFrame{
	public Container cont = getContentPane();
	public JLayeredPane panel = new JLayeredPane();
	
	/* BOTON BANCO */
	JButton btnBanco= new JButton(new ImageIcon("imagen/banco.png"));
	JLabel lblBanco= new JLabel("Banco");
	
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
	
	/* BOTON PUESTO */
	JButton btnPuesto= new JButton(new ImageIcon("imagen/puesto.png"));
	JLabel lblPuesto2= new JLabel("Puesto");
	
	/* BOTON SUELDOS */
	JButton btnSueldo= new JButton(new ImageIcon("imagen/sueldo.png"));
	JLabel lblSueldo2= new JLabel("Sueldo");
	
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
	
	public Init_Login(){
		int ancho = Toolkit.getDefaultToolkit().getScreenSize().width;
		int alto = Toolkit.getDefaultToolkit().getScreenSize().height;
		
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
		btnCaja.addActionListener(Opciones);
		btnFsRH.addActionListener(Opciones);
		btnFsAux.addActionListener(Opciones);
		btnPExtras.addActionListener(Opciones);
		btnPrestamo.addActionListener(Opciones);
		btnAltaEmp.addActionListener(Opciones);
		btnPuesto.addActionListener(Opciones);
		btnSueldo.addActionListener(Opciones);
		btnListaRaya.addActionListener(Opciones);
		btnListaFirma.addActionListener(Opciones);
		btnListaComparacion.addActionListener(Opciones);
		btnChecador.addActionListener(Opciones);
		
		btnBanco.setEnabled(false);
		btnInasistencia.setEnabled(false);
		btnCaja.setEnabled(false);
		btnFsRH.setEnabled(false);
		btnFsAux.setEnabled(false);
		btnPExtras.setEnabled(false);
		btnPrestamo.setEnabled(false);
		btnAltaEmp.setEnabled(false);
		btnPuesto.setEnabled(false);
		btnSueldo.setEnabled(false);
		btnListaRaya.setEnabled(false);
		btnListaFirma.setEnabled(false);
		btnListaComparacion.setEnabled(false);
		btnChecador.setEnabled(true);
	}
	
	ActionListener Opciones = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			
//			if(arg0.getSource().equals(btnBanco))
//				new Cat_Bancos().setVisible(true);
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
//			if(arg0.getSource().equals(btnChecador))
//				new Cat_Checador().setVisible(true);

		}
		
	};
	
	public void Resolucion(int ancho, int alto){
		if(ancho >= 1360){
			panel.add(lblLogo).setBounds(980,0,400,218);
			int x = 30, y = 40, z = 65;
			int yl = 60, zl = 120;
					
			/* FILA 1 */
			panel.add(btnBanco).setBounds( x, y, z, z);
			panel.add(btnPExtras).setBounds(220, y, z, z);
			panel.add(btnAltaEmp).setBounds(405, y, z, z);
			panel.add(btnListaRaya).setBounds(594, y, z, z);
			
			panel.add(lblBanco).setBounds(112, yl, zl, 20);
			panel.add(lblPExtras1).setBounds(300, yl, zl, 20);
			panel.add(lblPExtras2).setBounds(300, yl+10, zl, 20);
			panel.add(lblAltaEmp2).setBounds(488, yl, zl, 20);
			panel.add(lblAltaEmp3).setBounds(488, yl+10, zl, 20);
			panel.add(lblListaRaya2).setBounds(676, yl, zl, 20);
			panel.add(lblListaRaya3).setBounds(676, yl+10, zl, 20);
			
			/* FILA 2 */
			panel.add(btnInasistencia).setBounds(x, y+=134, z, z);
			panel.add(btnPrestamo).setBounds(220, y, z, z);
			panel.add(btnPuesto).setBounds(405, y, z, z);
			panel.add(btnListaFirma).setBounds(594, y, z, z);
			
			panel.add(lblInasistencia2).setBounds(112, y+=20, zl, 20);
			panel.add(lblInasistencia3).setBounds(112, y+10, zl, 20);
			panel.add(lblPrestamo2).setBounds(300, y, zl, 20);
			panel.add(lblPuesto2).setBounds(488, y, zl, 20);
			panel.add(lblListaFirma2).setBounds(676, y, zl, 20);
			panel.add(lblListaFirma3).setBounds(676, y+10, zl, 20);
			
			/* FILA 3 */
			panel.add(btnCaja).setBounds(x, y+=115, z, z);
			panel.add(btnSueldo).setBounds(405, y, z, z);
			panel.add(btnListaComparacion).setBounds(594, y, z, z);
			
			panel.add(lblCaja2).setBounds(112, y+=20, zl, 20);
			panel.add(lblCaja3).setBounds(112, y+10, zl, 20);
			panel.add(lblSueldo2).setBounds(488, y, zl, 20);
			panel.add(lblListaComparacion2).setBounds(676, y, zl, 20);
			panel.add(lblListaComparacion3).setBounds(676, y+10, zl, 20);
			
			/* FILA 4 */
			panel.add(btnFsRH).setBounds(x, y+=115, z, z);

			panel.add(lblFsRH2).setBounds(112, y+=20, zl, 20);
			panel.add(lblFsRH3).setBounds(112, y+10, zl, 20);
			
			/* FILA 5 */
			panel.add(btnFsAux).setBounds(x, y+=115, z, z);		
			panel.add(btnChecador).setBounds(594, y, z, z);
			
			panel.add(lblFsAux2).setBounds(112, y+=20, zl, 20);
			panel.add(lblFsAux3).setBounds(112, y+10, zl, 20);
			panel.add(lblListaChecador2).setBounds(676, y+10, zl, 20);
			

			
			panel.add(lblfolio).setBounds(1000, 490, 80, 20);
			panel.add(lblcontrasena_Actual).setBounds(1000, 490, 80, 20);
			panel.add(txtFolio).setBounds(1060, 490, 220, 20);
			panel.add(txtContrasenaActual).setBounds(1060, 490, 220, 20);
			
			panel.add(lblusuario).setBounds(1000, 520, 80, 20);
			panel.add(lblcontrasena_Nueva).setBounds(1000, 520, 80, 20);
			panel.add(txtUsuario).setBounds(1060, 520, 220, 20);
			panel.add(txtContrasenaNueva).setBounds(1060, 520, 220, 20);
			
			panel.add(lblcontrasena).setBounds(980, 550, 100, 20);
			panel.add(lblcontrasena_Confirmar).setBounds(980, 550, 100, 20);
			panel.add(txtContrasena).setBounds(1060, 550, 220, 20);
			
			panel.add(txtContrasenaConfirmar).setBounds(1060, 550, 220, 20);
			panel.add(btnBuscar).setBounds(1290, 490, 30, 20);
			panel.add(btnSalir).setBounds(1215, 580, 65, 20);
			
			panel.add(btnCambiarContrasena).setBounds(1060,580,130,20);
			panel.add(btnGuardarContrasena).setBounds(1060, 580,150, 20);
			panel.add(btnValidarContrasena).setBounds(1060, 580,150, 20);
			panel.add(btnAceptar).setBounds(1150, 580, 65, 20);
			
			
			cont.add(panel);
			
			this.setSize(ancho,alto);

		}
		if(ancho == 1280){
			switch(alto){
				case 768 : 
					panel.add(new JLabel(new ImageIcon("imagen/LogPrincipal.png"))).setBounds(780,0,400,218);
					int x = 30, y = 40, z = 65;
					int yl = 60, zl = 120;
							
					/* FILA 1 */
					panel.add(btnBanco).setBounds( x, y, z, z);
					panel.add(btnPExtras).setBounds(220, y, z, z);
					panel.add(btnAltaEmp).setBounds(405, y, z, z);
					panel.add(btnListaRaya).setBounds(594, y, z, z);
					
					panel.add(lblBanco).setBounds(112, yl, zl, 20);
					panel.add(lblPExtras1).setBounds(300, yl, zl, 20);
					panel.add(lblPExtras2).setBounds(300, yl+10, zl, 20);
					panel.add(lblAltaEmp2).setBounds(488, yl, zl, 20);
					panel.add(lblAltaEmp3).setBounds(488, yl+10, zl, 20);
					panel.add(lblListaRaya2).setBounds(676, yl, zl, 20);
					panel.add(lblListaRaya3).setBounds(676, yl+10, zl, 20);
					
					/* FILA 2 */
					panel.add(btnInasistencia).setBounds(x, y+=134, z, z);
					panel.add(btnPrestamo).setBounds(220, y, z, z);
					panel.add(btnPuesto).setBounds(405, y, z, z);
					panel.add(btnListaFirma).setBounds(594, y, z, z);
					
					panel.add(lblInasistencia2).setBounds(112, y+=20, zl, 20);
					panel.add(lblInasistencia3).setBounds(112, y+10, zl, 20);
					panel.add(lblPrestamo2).setBounds(300, y, zl, 20);
					panel.add(lblPuesto2).setBounds(488, y, zl, 20);
					panel.add(lblListaFirma2).setBounds(676, y, zl, 20);
					panel.add(lblListaFirma3).setBounds(676, y+10, zl, 20);
					
					/* FILA 3 */
					panel.add(btnCaja).setBounds(x, y+=115, z, z);
					panel.add(btnSueldo).setBounds(405, y, z, z);
					panel.add(btnListaComparacion).setBounds(594, y, z, z);
					
					panel.add(lblCaja2).setBounds(112, y+=20, zl, 20);
					panel.add(lblCaja3).setBounds(112, y+10, zl, 20);
					panel.add(lblSueldo2).setBounds(488, y, zl, 20);
					panel.add(lblListaComparacion2).setBounds(676, y, zl, 20);
					panel.add(lblListaComparacion3).setBounds(676, y+10, zl, 20);
					
					/* FILA 4 */
					panel.add(btnFsRH).setBounds(x, y+=115, z, z);

					panel.add(lblFsRH2).setBounds(112, y+=20, zl, 20);
					panel.add(lblFsRH3).setBounds(112, y+10, zl, 20);
					
					/* FILA 5 */
					panel.add(btnFsAux).setBounds(x, y+=115, z, z);		
					panel.add(btnChecador).setBounds(594, y+=10, z, z);
					
					panel.add(lblFsAux2).setBounds(112, y+=20, zl, 20);
					panel.add(lblFsAux3).setBounds(112, y+10, zl, 20);
					panel.add(lblListaChecador2).setBounds(676, y, zl, 20);
					
					panel.add(lblfolio).setBounds(880, 470, 80, 20);
					panel.add(lblcontrasena_Actual).setBounds(880, 470, 80, 20);
					panel.add(lblusuario).setBounds(880, 500, 80, 20);
					panel.add(lblcontrasena_Nueva).setBounds(880, 500, 80, 20);
					panel.add(lblcontrasena).setBounds(880, 530, 100, 20);
					panel.add(lblcontrasena_Confirmar).setBounds(880, 530, 100, 20);
					
					panel.add(txtFolio).setBounds(960, 470, 220, 20);
					panel.add(txtContrasenaActual).setBounds(960, 470, 220, 20);
					panel.add(txtUsuario).setBounds(960, 500, 220, 20);
					panel.add(txtContrasenaNueva).setBounds(960, 500, 220, 20);
					panel.add(txtContrasena).setBounds(960, 530, 220, 20);
					panel.add(txtContrasenaConfirmar).setBounds(960, 530, 220, 20);
				
				
					panel.add(btnBuscar).setBounds(1190, 470, 30, 20);
					panel.add(btnSalir).setBounds(1115, 560, 65, 20);
					panel.add(btnCambiarContrasena).setBounds(960,560,130,20);
					panel.add(btnGuardarContrasena).setBounds(960, 560,150, 20);
					panel.add(btnValidarContrasena).setBounds(960, 560,150, 20);
					panel.add(btnAceptar).setBounds(1115, 560, 65, 20);
					
					cont.add(panel);		
					this.setSize(ancho,alto);
					
				break;
				case 720 : 
					
					panel.add(new JLabel(new ImageIcon("imagen/LogPrincipal.png"))).setBounds(870,0,400,218);
					 x = 30; y = 30; z = 65;
					 yl = 50; zl = 120;
							
					/* FILA 1 */
					panel.add(btnBanco).setBounds( x, y, z, z);
					panel.add(btnPExtras).setBounds(220, y, z, z);
					panel.add(btnAltaEmp).setBounds(405, y, z, z);
					panel.add(btnListaRaya).setBounds(594, y, z, z);
					
					panel.add(lblBanco).setBounds(112, yl, zl, 20);
					panel.add(lblPExtras1).setBounds(300, yl, zl, 20);
					panel.add(lblPExtras2).setBounds(300, yl+10, zl, 20);
					panel.add(lblAltaEmp2).setBounds(488, yl, zl, 20);
					panel.add(lblAltaEmp3).setBounds(488, yl+10, zl, 20);
					panel.add(lblListaRaya2).setBounds(676, yl, zl, 20);
					panel.add(lblListaRaya3).setBounds(676, yl+10, zl, 20);
					
					/* FILA 2 */
					panel.add(btnInasistencia).setBounds(x, y+=125, z, z);
					panel.add(btnPrestamo).setBounds(220, y, z, z);
					panel.add(btnPuesto).setBounds(405, y, z, z);
					panel.add(btnListaFirma).setBounds(594, y, z, z);
					
					panel.add(lblInasistencia2).setBounds(112, y+=20, zl, 20);
					panel.add(lblInasistencia3).setBounds(112, y+10, zl, 20);
					panel.add(lblPrestamo2).setBounds(300, y, zl, 20);
					panel.add(lblPuesto2).setBounds(488, y, zl, 20);
					panel.add(lblListaFirma2).setBounds(676, y, zl, 20);
					panel.add(lblListaFirma3).setBounds(676, y+10, zl, 20);
					
					/* FILA 3 */
					panel.add(btnCaja).setBounds(x, y+=105, z, z);
					panel.add(btnSueldo).setBounds(405, y, z, z);
					panel.add(btnListaComparacion).setBounds(594, y, z, z);
					
					panel.add(lblCaja2).setBounds(112, y+=20, zl, 20);
					panel.add(lblCaja3).setBounds(112, y+10, zl, 20);
					panel.add(lblSueldo2).setBounds(488, y, zl, 20);
					panel.add(lblListaComparacion2).setBounds(676, y, zl, 20);
					panel.add(lblListaComparacion3).setBounds(676, y+10, zl, 20);
					
					/* FILA 4 */
					panel.add(btnFsRH).setBounds(x, y+=105, z, z);

					panel.add(lblFsRH2).setBounds(112, y+=20, zl, 20);
					panel.add(lblFsRH3).setBounds(112, y+10, zl, 20);
					
					/* FILA 5 */
					panel.add(btnFsAux).setBounds(x, y+=105, z, z);	
					panel.add(btnChecador).setBounds(594, y+=10, z, z);
					
					panel.add(lblFsAux2).setBounds(112, y+=20, zl, 20);
					panel.add(lblFsAux3).setBounds(112, y+10, zl, 20);
					panel.add(lblListaChecador2).setBounds(676, y, zl, 20);
					
					panel.add(lblfolio).setBounds(880, 470, 80, 20);
					panel.add(lblcontrasena_Actual).setBounds(880, 470, 80, 20);
					panel.add(lblusuario).setBounds(880, 500, 80, 20);
					panel.add(lblcontrasena_Nueva).setBounds(880, 500, 80, 20);
					panel.add(lblcontrasena).setBounds(880, 530, 100, 20);
					panel.add(lblcontrasena_Confirmar).setBounds(880, 530, 100, 20);

					panel.add(txtFolio).setBounds(960, 470, 220, 20);
					panel.add(txtContrasenaActual).setBounds(960, 470, 220, 20);
					panel.add(txtUsuario).setBounds(960, 500, 220, 20);
					panel.add(txtContrasenaNueva).setBounds(960, 500, 220, 20);
					panel.add(txtContrasena).setBounds(960, 530, 220, 20);
					panel.add(txtContrasenaConfirmar).setBounds(960, 530, 220, 20);
								
					panel.add(btnBuscar).setBounds(1190, 470, 30, 20);
					panel.add(btnSalir).setBounds(1115, 560, 65, 20);
					panel.add(btnCambiarContrasena).setBounds(960,560,130,20);
					panel.add(btnGuardarContrasena).setBounds(960, 560,150, 20);
					panel.add(btnValidarContrasena).setBounds(960, 560,150, 20);
					panel.add(btnAceptar).setBounds(1115, 560, 65, 20);
					
				
					
					cont.add(panel);
					
					this.setSize(ancho,alto);

				break;
				case 600 : 
				
					panel.add(new JLabel(new ImageIcon("imagen/LogPrincipal.png"))).setBounds(870,0,400,218);
					 x = 10; y = 10; z = 65;
					 yl = 50; zl = 120;
							
					/* FILA 1 */
					panel.add(btnBanco).setBounds( x, y, z, z);
					panel.add(btnPExtras).setBounds(220, y, z, z);
					panel.add(btnAltaEmp).setBounds(405, y, z, z);
					panel.add(btnListaRaya).setBounds(594, y, z, z);
					
					panel.add(lblBanco).setBounds(112, yl, zl, 20);
					panel.add(lblPExtras1).setBounds(300, yl, zl, 20);
					panel.add(lblPExtras2).setBounds(300, yl+10, zl, 20);
					panel.add(lblAltaEmp2).setBounds(488, yl, zl, 20);
					panel.add(lblAltaEmp3).setBounds(488, yl+10, zl, 20);
					panel.add(lblListaRaya2).setBounds(676, yl, zl, 20);
					panel.add(lblListaRaya3).setBounds(676, yl+10, zl, 20);
					
					/* FILA 2 */
					panel.add(btnInasistencia).setBounds(x, y+=100, z, z);
					panel.add(btnPrestamo).setBounds(220, y, z, z);
					panel.add(btnPuesto).setBounds(405, y, z, z);
					panel.add(btnListaFirma).setBounds(594, y, z, z);
					
					panel.add(lblInasistencia2).setBounds(112, y+=20, zl, 20);
					panel.add(lblInasistencia3).setBounds(112, y+10, zl, 20);
					panel.add(lblPrestamo2).setBounds(300, y, zl, 20);
					panel.add(lblPuesto2).setBounds(488, y, zl, 20);
					panel.add(lblListaFirma2).setBounds(676, y, zl, 20);
					panel.add(lblListaFirma3).setBounds(676, y+10, zl, 20);
					
					/* FILA 3 */
					panel.add(btnCaja).setBounds(x, y+=80, z, z);
					panel.add(btnSueldo).setBounds(405, y, z, z);
					panel.add(btnListaComparacion).setBounds(594, y, z, z);
					
					panel.add(lblCaja2).setBounds(112, y+=20, zl, 20);
					panel.add(lblCaja3).setBounds(112, y+10, zl, 20);
					panel.add(lblSueldo2).setBounds(488, y, zl, 20);
					panel.add(lblListaComparacion2).setBounds(676, y, zl, 20);
					panel.add(lblListaComparacion3).setBounds(676, y+10, zl, 20);
					
					/* FILA 4 */
					panel.add(btnFsRH).setBounds(x, y+=80, z, z);

					panel.add(lblFsRH2).setBounds(112, y+=20, zl, 20);
					panel.add(lblFsRH3).setBounds(112, y+10, zl, 20);
					
					/* FILA 5 */
					panel.add(btnFsAux).setBounds(x, y+=80, z, z);	
					panel.add(btnChecador).setBounds(594, y+80, z, z);
		
					panel.add(lblFsAux2).setBounds(112, y+=20, zl, 20);
					panel.add(lblFsAux3).setBounds(112, y+10, zl, 20);
					panel.add(lblListaChecador2).setBounds(676, y, zl, 20);

					
					panel.add(new JLabel("Folio:")).setBounds(880, 370, 80, 20);
					panel.add(new JLabel("Usuario:")).setBounds(880, 400, 80, 20);
					panel.add(new JLabel("Contraseña:")).setBounds(880, 430, 100, 20);
					
					panel.add(txtFolio).setBounds(960, 370, 220, 20);
					panel.add(txtUsuario).setBounds(960, 400, 220, 20);
					panel.add(txtContrasena).setBounds(960, 430, 220, 20);
				
					panel.add(btnBuscar).setBounds(1190, 370, 30, 20);
					panel.add(btnSalir).setBounds(1050, 460, 65, 20);
					panel.add(btnAceptar).setBounds(1115, 460, 65, 20);
					
					cont.add(panel);
					
					this.setSize(ancho,alto);
					
				break;
				default :
					panel.add(new JLabel(new ImageIcon("imagen/LogPrincipal.png"))).setBounds(870,0,400,218);
					 x = 10; y = 10; z = 65;
					 yl = 50; zl = 120;
							
					/* FILA 1 */
					panel.add(btnBanco).setBounds( x, y, z, z);
					panel.add(btnPExtras).setBounds(220, y, z, z);
					panel.add(btnAltaEmp).setBounds(405, y, z, z);
					panel.add(btnListaRaya).setBounds(594, y, z, z);
					
					panel.add(lblBanco).setBounds(112, yl, zl, 20);
					panel.add(lblPExtras1).setBounds(300, yl, zl, 20);
					panel.add(lblPExtras2).setBounds(300, yl+10, zl, 20);
					panel.add(lblAltaEmp2).setBounds(488, yl, zl, 20);
					panel.add(lblAltaEmp3).setBounds(488, yl+10, zl, 20);
					panel.add(lblListaRaya2).setBounds(676, yl, zl, 20);
					panel.add(lblListaRaya3).setBounds(676, yl+10, zl, 20);
					
					/* FILA 2 */
					panel.add(btnInasistencia).setBounds(x, y+=100, z, z);
					panel.add(btnPrestamo).setBounds(220, y, z, z);
					panel.add(btnPuesto).setBounds(405, y, z, z);
					panel.add(btnListaFirma).setBounds(594, y, z, z);
					
					panel.add(lblInasistencia2).setBounds(112, y+=20, zl, 20);
					panel.add(lblInasistencia3).setBounds(112, y+10, zl, 20);
					panel.add(lblPrestamo2).setBounds(300, y, zl, 20);
					panel.add(lblPuesto2).setBounds(488, y, zl, 20);
					panel.add(lblListaFirma2).setBounds(676, y, zl, 20);
					panel.add(lblListaFirma3).setBounds(676, y+10, zl, 20);
					
					/* FILA 3 */
					panel.add(btnCaja).setBounds(x, y+=80, z, z);
					panel.add(btnSueldo).setBounds(405, y, z, z);
					panel.add(btnListaComparacion).setBounds(594, y, z, z);
					
					panel.add(lblCaja2).setBounds(112, y+=20, zl, 20);
					panel.add(lblCaja3).setBounds(112, y+10, zl, 20);
					panel.add(lblSueldo2).setBounds(488, y, zl, 20);
					panel.add(lblListaComparacion2).setBounds(676, y, zl, 20);
					panel.add(lblListaComparacion3).setBounds(676, y+10, zl, 20);
					
					/* FILA 4 */
					panel.add(btnFsRH).setBounds(x, y+=80, z, z);

					panel.add(lblFsRH2).setBounds(112, y+=20, zl, 20);
					panel.add(lblFsRH3).setBounds(112, y+10, zl, 20);
					
					/* FILA 5 */
					panel.add(btnFsAux).setBounds(x, y+=80, z, z);		

					panel.add(lblFsAux2).setBounds(112, y+=20, zl, 20);
					panel.add(lblFsAux3).setBounds(112, y+10, zl, 20);

					
					panel.add(new JLabel("Folio:")).setBounds(880, 370, 80, 20);
					panel.add(new JLabel("Usuario:")).setBounds(880, 400, 80, 20);
					panel.add(new JLabel("Contraseña:")).setBounds(880, 430, 100, 20);
					
					panel.add(txtFolio).setBounds(960, 370, 220, 20);
					panel.add(txtUsuario).setBounds(960, 400, 220, 20);
					panel.add(txtContrasena).setBounds(960, 430, 220, 20);
				
					panel.add(btnBuscar).setBounds(1190, 370, 30, 20);
					panel.add(btnSalir).setBounds(1050, 460, 65, 20);
					panel.add(btnAceptar).setBounds(1115, 460, 65, 20);
					
					cont.add(panel);
					
					this.setSize(ancho,alto);
					
				break;
			}
		}
		
		if(ancho == 1024){
			ImageIcon tmpIconAux = new ImageIcon("imagen/LogPrincipal.png");
			panel.add(new JLabel(new ImageIcon(tmpIconAux.getImage().getScaledInstance(150,150, Image.SCALE_DEFAULT)))).setBounds(680,0,400,218);
			
//			getImage().getScaledInstance(230, 195, Image.SCALE_DEFAULT)
			int x = 10, y = 10, z = 65;
			int yl = 50, zl = 120;
					
			/* FILA 1 */
			panel.add(btnBanco).setBounds( x, y, z, z);
			panel.add(btnPExtras).setBounds(220, y, z, z);
			panel.add(btnAltaEmp).setBounds(405, y, z, z);
			panel.add(btnListaRaya).setBounds(594, y, z, z);
			
			panel.add(lblBanco).setBounds(112, yl, zl, 20);
			panel.add(lblPExtras1).setBounds(300, yl, zl, 20);
			panel.add(lblPExtras2).setBounds(300, yl+10, zl, 20);
			panel.add(lblAltaEmp2).setBounds(488, yl, zl, 20);
			panel.add(lblAltaEmp3).setBounds(488, yl+10, zl, 20);
			panel.add(lblListaRaya2).setBounds(676, yl, zl, 20);
			panel.add(lblListaRaya3).setBounds(676, yl+10, zl, 20);
			
			/* FILA 2 */
			panel.add(btnInasistencia).setBounds(x, y+=100, z, z);
			panel.add(btnPrestamo).setBounds(220, y, z, z);
			panel.add(btnPuesto).setBounds(405, y, z, z);
			panel.add(btnListaFirma).setBounds(594, y, z, z);
			
			panel.add(lblInasistencia2).setBounds(112, y+=20, zl, 20);
			panel.add(lblInasistencia3).setBounds(112, y+10, zl, 20);
			panel.add(lblPrestamo2).setBounds(300, y, zl, 20);
			panel.add(lblPuesto2).setBounds(488, y, zl, 20);
			panel.add(lblListaFirma2).setBounds(676, y, zl, 20);
			panel.add(lblListaFirma3).setBounds(676, y+10, zl, 20);
			
			/* FILA 3 */
			panel.add(btnCaja).setBounds(x, y+=80, z, z);
			panel.add(btnSueldo).setBounds(405, y, z, z);
			panel.add(btnListaComparacion).setBounds(594, y, z, z);
			
			panel.add(lblCaja2).setBounds(112, y+=20, zl, 20);
			panel.add(lblCaja3).setBounds(112, y+10, zl, 20);
			panel.add(lblSueldo2).setBounds(488, y, zl, 20);
			panel.add(lblListaComparacion2).setBounds(676, y, zl, 20);
			panel.add(lblListaComparacion3).setBounds(676, y+10, zl, 20);
			
			/* FILA 4 */
			panel.add(btnFsRH).setBounds(x, y+=80, z, z);

			panel.add(lblFsRH2).setBounds(112, y+=20, zl, 20);
			panel.add(lblFsRH3).setBounds(112, y+20, zl, 20);
			
			/* FILA 5 */
			panel.add(btnFsAux).setBounds(x, y+=80, z, z);	

			panel.add(lblFsAux2).setBounds(112, y, zl, 20);
			panel.add(lblFsAux3).setBounds(112, y+10, zl, 20);
			
			panel.add(btnChecador).setBounds(x,y+=80, z, z);
			panel.add(lblListaChecador2).setBounds(112, y+10, zl, 20);

			panel.add(lblfolio).setBounds(580, 470, 80, 20);
			panel.add(lblcontrasena_Actual).setBounds(580, 470, 80, 20);
			panel.add(lblusuario).setBounds(580, 500, 80, 20);
			panel.add(lblcontrasena_Nueva).setBounds(580, 500, 80, 20);
			panel.add(lblcontrasena).setBounds(580, 530, 100, 20);
			panel.add(lblcontrasena_Confirmar).setBounds(580, 530, 100, 20);

			panel.add(txtFolio).setBounds(660, 470, 220, 20);
			panel.add(txtContrasenaActual).setBounds(660, 470, 220, 20);
			panel.add(txtUsuario).setBounds(660, 500, 220, 20);
			panel.add(txtContrasenaNueva).setBounds(660, 500, 220, 20);
			panel.add(txtContrasena).setBounds(660, 530, 220, 20);
			panel.add(txtContrasenaConfirmar).setBounds(660, 530, 220, 20);
						
			panel.add(btnBuscar).setBounds(890, 470, 30, 20);
			panel.add(btnSalir).setBounds(820, 560, 65, 20);
			panel.add(btnCambiarContrasena).setBounds(660,560,130,20);
			panel.add(btnGuardarContrasena).setBounds(660, 560,150, 20);
			panel.add(btnValidarContrasena).setBounds(660, 560,150, 20);
			panel.add(btnAceptar).setBounds(815, 560, 65, 20);
			
			
			cont.add(panel);
			
			this.setSize(ancho,alto);
			
		}
		if(ancho == 800){
			ImageIcon tmpIconAux = new ImageIcon("imagen/LogPrincipal.png");
			panel.add(new JLabel(new ImageIcon(tmpIconAux.getImage().getScaledInstance(150,150, Image.SCALE_DEFAULT)))).setBounds(500,0,400,218);
			
//			getImage().getScaledInstance(230, 195, Image.SCALE_DEFAULT)
			int x = 10, y = 10, z = 55;
			int yl = 30, zl = 120;
					
			/* FILA 1 */
			panel.add(btnBanco).setBounds( x, y, z, z);
			panel.add(btnPExtras).setBounds(180, y, z, z);
			panel.add(btnAltaEmp).setBounds(350, y, z, z);
			panel.add(btnListaRaya).setBounds(490, y, z, z);
			
			panel.add(lblBanco).setBounds(70, yl, zl, 20);
			panel.add(lblPExtras1).setBounds(248, yl, zl, 20);
			panel.add(lblPExtras2).setBounds(248, yl+10, zl, 20);
			panel.add(lblAltaEmp2).setBounds(418, yl, zl, 20);
			panel.add(lblAltaEmp3).setBounds(418, yl+10, zl, 20);
			panel.add(lblListaRaya2).setBounds(560, yl, zl, 20);
			panel.add(lblListaRaya3).setBounds(560, yl+10, zl, 20);
			
			/* FILA 2 */
			panel.add(btnInasistencia).setBounds(x, y+=70, z, z);
			panel.add(btnPrestamo).setBounds(180, y, z, z);
			panel.add(btnPuesto).setBounds(350, y, z, z);
			panel.add(btnListaFirma).setBounds(490, y, z, z);
			
			panel.add(lblInasistencia2).setBounds(70, y+=20, zl, 20);
			panel.add(lblInasistencia3).setBounds(70, y+10, zl, 20);
			panel.add(lblPrestamo2).setBounds(248, y, zl, 20);
			panel.add(lblPuesto2).setBounds(418, y, zl, 20);
			panel.add(lblListaFirma2).setBounds(560, y, zl, 20);
			panel.add(lblListaFirma3).setBounds(560, y+10, zl, 20);
			
			/* FILA 3 */
			panel.add(btnCaja).setBounds(x, y+=50, z, z);
			panel.add(btnSueldo).setBounds(350, y, z, z);
			panel.add(btnListaComparacion).setBounds(490, y, z, z);
			
			panel.add(lblCaja2).setBounds(70, y+=20, zl, 20);
			panel.add(lblCaja3).setBounds(70, y+10, zl, 20);
			panel.add(lblSueldo2).setBounds(418, y, zl, 20);
			panel.add(lblListaComparacion2).setBounds(560, y, zl, 20);
			panel.add(lblListaComparacion3).setBounds(560, y+10, zl, 20);
			
			/* FILA 4 */
			panel.add(btnFsRH).setBounds(x, y+=50, z, z);

			panel.add(lblFsRH2).setBounds(70, y+=20, zl, 20);
			panel.add(lblFsRH3).setBounds(70, y+10, zl, 20);
			
			/* FILA 5 */
			panel.add(btnFsAux).setBounds(x, y+=50, z, z);		

			panel.add(lblFsAux2).setBounds(70, y+=20, zl, 20);
			panel.add(lblFsAux3).setBounds(70, y+10, zl, 20);

			
			panel.add(lblfolio).setBounds(380, 340, 80, 20);
			panel.add(lblcontrasena_Actual).setBounds(380, 340, 80, 20);
			panel.add(lblusuario).setBounds(380, 370, 80, 20);
			panel.add(lblcontrasena_Nueva).setBounds(380, 370, 80, 20);
			panel.add(lblcontrasena).setBounds(380, 400, 100, 20);
			panel.add(lblcontrasena_Confirmar).setBounds(380, 400, 100, 20);

			panel.add(txtFolio).setBounds(460, 340, 220, 20);
			panel.add(txtContrasenaActual).setBounds(460, 340, 220, 20);
			panel.add(txtUsuario).setBounds(460, 370, 220, 20);
			panel.add(txtContrasenaNueva).setBounds(460, 370, 220, 20);
			panel.add(txtContrasena).setBounds(460, 400, 220, 20);
			panel.add(txtContrasenaConfirmar).setBounds(460, 400, 220, 20);
						
			panel.add(btnBuscar).setBounds(690, 340, 30, 20);
			panel.add(btnSalir).setBounds(550, 430, 65, 20);
			panel.add(btnCambiarContrasena).setBounds(660,560,130,20);
			panel.add(btnGuardarContrasena).setBounds(660, 560,150, 20);
			panel.add(btnValidarContrasena).setBounds(660, 560,150, 20);
			panel.add(btnAceptar).setBounds(815, 560, 65, 20);
			
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
			
					};

}
