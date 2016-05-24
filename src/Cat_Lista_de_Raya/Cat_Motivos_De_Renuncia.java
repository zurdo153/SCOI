/**
 * 
 */
package Cat_Lista_de_Raya;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.UIManager;

import com.toedter.calendar.JDateChooser;

import Conexiones_SQL.BuscarSQL;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;

/**
 *AUTOR:
 *@author EDGAR EDUARDO JIMÉNEZ MOLINA
 */
@SuppressWarnings("serial")
public class Cat_Motivos_De_Renuncia extends JFrame{

	/** DECLARACION DE COMPONENTES */
	Container cont = getContentPane();
	
	JLayeredPane panel = new JLayeredPane();
	
	JTabbedPane pestanas = new JTabbedPane();
	JLayeredPane pMotivos = new JLayeredPane();
	JLayeredPane pCuestionario = new JLayeredPane();
	
	public JToolBar menu_toolbar = new JToolBar();
	JButton btnGuardar = new JButton("Guardar", new ImageIcon("imagen/guardar-documento-icono-7840-32.png"));
	JButton btnDeshacer = new JButton("Deshacer", new ImageIcon("imagen/deshacer-icono-4321-32.png"));
	
	JButton btnBuscar = new JCButton("Buscar", "buscar.png", "Azul");
	
	JTextField txtFolioEmpleado = new Componentes().text(new JTextField(), "Folio Del Empleado", 10, "Int");
	JTextField txtNombreEmpleado = new Componentes().text(new JTextField(), "Nombre Del Empleado", 200, "String");
	JTextField txtFolioJefeInmediato = new Componentes().text(new JTextField(), "Folio Del Jefe Inmediato", 10, "Int");
	JTextField txtJefeInmediato = new Componentes().text(new JTextField(), "Nombre Del Jefe Inmediato", 200, "String");
	JTextField txtEstablecimiento = new Componentes().text(new JTextField(), "Establecimiento", 200, "String");
	JTextField txtDepartamento = new Componentes().text(new JTextField(), "Departamento", 200, "String");
	JTextField txtPuesto = new Componentes().text(new JTextField(), "Puesto", 200, "String");
	
	JDateChooser fchBaja = new JDateChooser();
	
	JLabel lblLineaMotovos = new JLabel("");
	
	JCheckBox chbSueldo 			= new JCheckBox("Sueldo");
	JCheckBox chbHorario 			= new JCheckBox("Horario");
	JCheckBox chbRealacionConJefe 	= new JCheckBox("Relacion Con Su Jefe/Lider");
	JCheckBox chbAmbienteLaboral 	= new JCheckBox("Ambiente Laboral");
	JCheckBox chbCapacitacion 		= new JCheckBox("Capacitación");
	JCheckBox chbDescuentoNomina 	= new JCheckBox("Descuento De Nomina");
	JCheckBox chbProblemaPersonal 	= new JCheckBox("Probema Personal");
	JCheckBox chbOtros 				= new JCheckBox("Otros");
	
	JTextArea txaDescripcionDelMotivo = new Componentes().textArea(new JTextArea(), "Descripcion Del Motivo De Renuncia", 500);
	JScrollPane ObsDescripcionDelMotivo = new JScrollPane(txaDescripcionDelMotivo);
	
	JTextArea txa1 = new Componentes().textArea(new JTextArea(), "", 500);
	JScrollPane Obs1 = new JScrollPane(txa1);
	JTextArea txa2 = new Componentes().textArea(new JTextArea(), "", 500);
	JScrollPane Obs2 = new JScrollPane(txa2);
	JTextArea txa3 = new Componentes().textArea(new JTextArea(), "", 500);
	JScrollPane Obs3 = new JScrollPane(txa3);
	JTextArea txa4 = new Componentes().textArea(new JTextArea(), "", 500);
	JScrollPane Obs4 = new JScrollPane(txa4);
	JTextArea txa5 = new Componentes().textArea(new JTextArea(), "", 500);
	JScrollPane Obs5 = new JScrollPane(txa5);
		
	ImageIcon ImgMotivos = new ImageIcon("imagen/articulo-icono-9036-48.png");
	ImageIcon ImgEncuestas = new ImageIcon("imagen/articulo-icono-9036-48 -mas.png");
	
	public Cat_Motivos_De_Renuncia(String folioEmpleado, String nombreEmpleado, String Establecimiento, String Departamento, String Puesto) {
		this.setTitle("Entrevista De Renuncia");
		pMotivos.setBorder(BorderFactory.createTitledBorder("Motivos De Renuncia"));
		pCuestionario.setBorder(BorderFactory.createTitledBorder("Cuentionario De Renuncia"));
		
		fchBaja.setIcon(new ImageIcon("Iconos/calendar_icon&16.png"));
		
		lblLineaMotovos.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),"Motivos"));
		
		//meter botones al MenuToolbar
		menu_toolbar.add(btnDeshacer);
		menu_toolbar.add(btnGuardar);
		
		//CAMBIAR DIMENCIONES DE LA IMAGEN
		ImgMotivos=new ImageIcon(ImgMotivos.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
		ImgEncuestas=new ImageIcon(ImgEncuestas.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
		
		//AGRUPAR LOS PANEL A LAS PESTAÑAS 
		//AGREGAR NOMBRE E IMAGEN A LAS PESTAÑAS
		pestanas.addTab("Motivos De Renuncia"	, ImgMotivos	, pMotivos);
		pestanas.addTab("Encuesta"				,ImgEncuestas	, pCuestionario);
		
		txaDescripcionDelMotivo.setLineWrap(true); 
		txaDescripcionDelMotivo.setWrapStyleWord(true);
		
		txa1.setLineWrap(true); 
		txa1.setWrapStyleWord(true);
		txa2.setLineWrap(true); 
		txa2.setWrapStyleWord(true);
		txa3.setLineWrap(true); 
		txa3.setWrapStyleWord(true);
		txa4.setLineWrap(true); 
		txa4.setWrapStyleWord(true);
		txa5.setLineWrap(true); 
		txa5.setWrapStyleWord(true);
		
		Color CFondo =Color.WHITE;
//PINTAR FONDO DE PANELES
		pMotivos.setOpaque(true);
		pCuestionario.setOpaque(true);
		pMotivos.setBackground(CFondo);
		pCuestionario.setBackground(CFondo);
//PINTAR FONDOS DE CHB'S
		chbSueldo.setBackground(CFondo);
		chbHorario.setBackground(CFondo); 			
		chbRealacionConJefe.setBackground(CFondo); 	
		chbAmbienteLaboral.setBackground(CFondo); 	
		chbCapacitacion.setBackground(CFondo); 		
		chbDescuentoNomina.setBackground(CFondo); 	
		chbProblemaPersonal.setBackground(CFondo); 	
		chbOtros.setBackground(CFondo); 				
		
		menu_toolbar.setEnabled(true);
//		this.cont.add(menu_toolbar).setBounds(0,0,800,25);
		add(menu_toolbar, BorderLayout.NORTH);
		
		int x = 15, y = 20, ancho = 80; 
		//AGREGAR COMPONENTES A LA PESTAÑA DE MOTIVOS
		pMotivos.add(new JLabel("Empleado:")).setBounds(x, y, ancho, 20);
		pMotivos.add(txtFolioEmpleado).setBounds(x+ancho+20, y, ancho-20, 20);
		pMotivos.add(txtNombreEmpleado).setBounds(x+(ancho*2), y, ancho*3+20, 20);
		
		pMotivos.add(new JLabel("Establecimiento:")).setBounds(x, y+=25, ancho+40, 20);
		pMotivos.add(txtEstablecimiento).setBounds(x+ancho+20, y, ancho*2, 20);
		
		pMotivos.add(new JLabel("Departamento:")).setBounds(x+(ancho*3)+30, y, ancho+20, 20);
		pMotivos.add(txtDepartamento).setBounds(x+(ancho*4)+40, y, ancho*2, 20);
		
		pMotivos.add(new JLabel("Puesto:")).setBounds(x+(ancho*6)+50, y, ancho, 20);
		pMotivos.add(txtPuesto).setBounds(x+(ancho*7)+20, y, ancho*2+20, 20);
		
		pMotivos.add(new JLabel("Jefe Inmediato:")).setBounds(x, y+=25, ancho, 20);
		pMotivos.add(txtFolioJefeInmediato).setBounds(x+ancho+20, y, ancho-20, 20);
		pMotivos.add(txtJefeInmediato).setBounds(x+(ancho*2), y, ancho*3+20, 20);
		pMotivos.add(btnBuscar).setBounds(x+(ancho*5)+20, y, ancho+20, 20);
		
		pMotivos.add(new JLabel("Fecha Baja:")).setBounds(x, y+=25, ancho, 20);
		pMotivos.add(fchBaja).setBounds(x+ancho+20, y, ancho*2, 20);
		
		x+=50;
		
		pMotivos.add(lblLineaMotovos).setBounds(x-50, y+=25, ancho*9+40, 110);
		pMotivos.add(chbSueldo).setBounds(x, y+=20, ancho-20, 20);
		pMotivos.add(chbHorario).setBounds(x+ancho*3, y, ancho-20, 20);
		pMotivos.add(chbRealacionConJefe).setBounds(x+ancho*6, y, ancho*2-5, 20);
		
		pMotivos.add(chbAmbienteLaboral).setBounds(x, y+=25, ancho*2-50, 20);
		pMotivos.add(chbCapacitacion).setBounds(x+ancho*3, y, ancho+10, 20);
		pMotivos.add(chbDescuentoNomina).setBounds(x+ancho*6, y, ancho*2-30, 20);
		
		pMotivos.add(chbProblemaPersonal).setBounds(x, y+=25, ancho*2-45, 20);
		pMotivos.add(chbOtros).setBounds(x+ancho*3, y, ancho-25, 20);
		
		x-=50;
		
		pMotivos.add(new JLabel("Descripcion:")).setBounds(x, y+=45, ancho*2, 20);
		pMotivos.add(ObsDescripcionDelMotivo).setBounds(x, y+=20, ancho*9+35, 100);
		
//AGREGAR COMPONENTES A LA PESTAÑA DE CUESTIONARIO
		y=20;
		
		pCuestionario.add(new JLabel("1.- Recibio Usted Capacitacion U Orientacion Respecto A La Actividad A Desempeñar? ¿Quien Lo(a) Capacito?")).setBounds(x, y, ancho*9+35, 20);
		pCuestionario.add(Obs1).setBounds(x, y+=20, ancho*9+35, 60);
		
		pCuestionario.add(new JLabel("2.- Que Te Parecieron Las Prestaciones Laborales De La Empresa?")).setBounds(x, y+=70, ancho*9+35, 20);
		pCuestionario.add(Obs2).setBounds(x, y+=20, ancho*9+35, 60);
		
		pCuestionario.add(new JLabel("3.- Las Relaciones Entre Jefes y Subordinados Se Desarrollan En Un Ambiente De Compañerismo Y Trabajo En Equipo?")).setBounds(x, y+=70, ancho*9+35, 20);
		pCuestionario.add(Obs3).setBounds(x, y+=20, ancho*9+35, 60);
		
		pCuestionario.add(new JLabel("4.- Si Pudieras Cambiar Algo o Mejorar Algo De La Empresa, Que Cambiarias O Mejorarias?")).setBounds(x, y+=70, ancho*9+35, 20);
		pCuestionario.add(Obs4).setBounds(x, y+=20, ancho*9+35, 60);
		
		pCuestionario.add(new JLabel("5.- Que Opinion Tienes De Tu Jefe Directo?")).setBounds(x, y+=70, ancho*9+35, 20);
		pCuestionario.add(Obs5).setBounds(x, y+=20, ancho*9+35, 60);
		
		txtFolioEmpleado.setEditable(false);
		txtNombreEmpleado.setEditable(false);
		txtFolioJefeInmediato.setEditable(false);
		txtJefeInmediato.setEditable(false);
		txtEstablecimiento.setEditable(false);
		txtDepartamento.setEditable(false);
		txtPuesto.setEditable(false);
		
		txtFolioEmpleado.setText(folioEmpleado);
		txtNombreEmpleado.setText(nombreEmpleado);
		txtEstablecimiento.setText(Establecimiento);
		txtDepartamento.setText(Departamento);
		txtPuesto.setText(Puesto);
		fchBaja.setDate(cargar_fechas());
		
		btnDeshacer.addActionListener(opDeshacer);
		
		cont.add(pestanas);
		
		this.setSize(800,600);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
	}

	
	public Date cargar_fechas(){
					
	    Date date = null;
					  try {
						date = new SimpleDateFormat("dd/MM/yyyy").parse(new BuscarSQL().fecha(0));
					} catch (ParseException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					}
		return date;
	};
	
	ActionListener opDeshacer = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			Limpiar();
		}
	};
	
	public void Limpiar(){
		txtFolioJefeInmediato.setText("");
		txtJefeInmediato.setText("");
		
		chbSueldo.setSelected(false);
		chbHorario.setSelected(false);
		chbRealacionConJefe.setSelected(false); 	
		chbAmbienteLaboral.setSelected(false); 	
		chbCapacitacion.setSelected(false); 		
		chbDescuentoNomina.setSelected(false); 	
		chbProblemaPersonal.setSelected(false); 	
		chbOtros.setSelected(false); 
		
		txaDescripcionDelMotivo.setText("");
		txa1.setText("");
		txa2.setText("");
		txa3.setText("");
		txa4.setText("");
		txa5.setText("");
	}
	
	public static void main(String[] args) {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Motivos_De_Renuncia("491","EDGAR EDUARDO JIMENEZ MOLINA","SISTEMAS","SISEMAS","PROGRAMADOR").setVisible(true);
		}catch(Exception e){
			System.err.println("Error :"+ e.getMessage());
		}
	}

}
