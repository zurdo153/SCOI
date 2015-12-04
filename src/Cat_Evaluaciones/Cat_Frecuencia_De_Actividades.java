package Cat_Evaluaciones;

import java.awt.Container;
import java.sql.Time;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.border.Border;

import Obj_Principal.Componentes;

import com.toedter.calendar.JDateChooser;

@SuppressWarnings("serial")
public class Cat_Frecuencia_De_Actividades extends JFrame{
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
//tipo de programacion	
	String[] tipo_de_programacion = {"UNA VEZ","PERIODICA"};
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox chbTipoDeProgramacion = new JComboBox(tipo_de_programacion);
	
	JRadioButton rbHastaQueSeCumpla = new JRadioButton("Hasta Que Se Cumpla");
	JRadioButton rbEnLaFechaIndicada = new JRadioButton("En La Fecha Indicada");
	ButtonGroup grupoTipoDeProgramacion = new ButtonGroup();
	
//unica repeticion
	JDateChooser fh_unica_repeticion = new JDateChooser();
	
	SpinnerDateModel sdmUnicaRepeticion =  new SpinnerDateModel();
	  JSpinner spHoraUnicaRepeticion = new JSpinner(sdmUnicaRepeticion);                                         
	  JSpinner.DateEditor spDHoraUnicaRepeticion = new JSpinner.DateEditor(spHoraUnicaRepeticion,"HH:mm:ss"); 
	
//frecuencia  -----------------------------------------------------------------------------
	  String[] secede = {"DIARIA","SEMANAL","MENSUAL"};
		@SuppressWarnings({ "rawtypes", "unchecked" })
		JComboBox chbSucede = new JComboBox(secede);
	
		JLabel lblSeRepiteCada = new JLabel("Se Repite Cada: ");
		JSpinner spDiasARepetir = new JSpinner(new SpinnerNumberModel( 1, 1, 31, 1 )); 
		JLabel lblDias_Semana = new JLabel("Dias");
		
		
		JCheckBox chLunes = new JCheckBox("Lunes");
		JCheckBox chMartes = new JCheckBox("Martes");
		JCheckBox chMiercoles = new JCheckBox("Miercoles");
		JCheckBox chJueves = new JCheckBox("Jueves");
		JCheckBox chViernes = new JCheckBox("Viernes");
		JCheckBox chSabado = new JCheckBox("Sabado");
		JCheckBox chDomingo = new JCheckBox("Domingo");
		
		JSpinner spMeses = new JSpinner(new SpinnerNumberModel( 1, 1, 12, 1 )); 
		JLabel lblMeses = new JLabel("Meses");
		
//-----------------------------------------------------------------------------------------	 
//frecuencia diaria
		
		JCheckBox chAsignarHora = new JCheckBox("Asignar Hora");
		
		SpinnerDateModel sdmFrecuenciaDiaria =  new SpinnerDateModel();
		  JSpinner spHoraFrecuenciaDiaria = new JSpinner(sdmFrecuenciaDiaria);                                         
		  JSpinner.DateEditor spDHoraFrecuenciaDiaria = new JSpinner.DateEditor(spHoraFrecuenciaDiaria,"H:mm:ss"); 
		
	
//Duracion
		JRadioButton rbFechaDeFinalizacion = new JRadioButton("Fecha De Finalizacion:");
		JRadioButton rbSinFechaDeFinalizacion = new JRadioButton("Sin Fecha De Finalizacion");
		ButtonGroup grupoFechaDeFinalizacion = new ButtonGroup();
		
		JDateChooser fh_inicial_de_duracion = new JDateChooser();
		JDateChooser fh_final_de_duracion = new JDateChooser();

//resumen
	
	JTextArea txaDescripcion = new Componentes().textArea(new JTextArea(), "Observaciones", 980);
	JScrollPane scrollDescripcion = new JScrollPane(txaDescripcion);
	
//botones
	JButton btnAceptar = new JButton("Aceptar");
	JButton btnCancelar = new JButton("Cancelar");
	
	Border blackline;
	
	public Cat_Frecuencia_De_Actividades() {
		
		tiempodefault();

		grupoTipoDeProgramacion.add(rbHastaQueSeCumpla);
		grupoTipoDeProgramacion.add(rbEnLaFechaIndicada);
		
		grupoFechaDeFinalizacion.add(rbFechaDeFinalizacion);
		grupoFechaDeFinalizacion.add(rbSinFechaDeFinalizacion);
		
		rbHastaQueSeCumpla.setSelected(true);
		rbSinFechaDeFinalizacion.setSelected(true);
		
		txaDescripcion.setBorder(BorderFactory.createTitledBorder(blackline));
		txaDescripcion.setLineWrap(true); 
		txaDescripcion.setWrapStyleWord(true);
		txaDescripcion.setEditable(false);
		
		int x=50,y=15,ancho=80;
		
		panel.add(new JLabel("Tipo De Programación: ")).setBounds(x, y, ancho+50, 20);
		panel.add(chbTipoDeProgramacion).setBounds(x+ancho+60, y, ancho*2, 20);
		
		panel.add(rbHastaQueSeCumpla).setBounds(x+ancho*4+60, y, ancho*2, 20);
		panel.add(rbEnLaFechaIndicada).setBounds(x+ancho*6+60, y, ancho*2, 20);
				
	//unica repeticion
		panel.add(new JLabel("Unica Repeticion")).setBounds(x-40, y+=25, ancho, 20);
		panel.add(new JSeparator()).setBounds(x+ancho-20, y+11, ancho*8+30, 20);
		
		panel.add(new JLabel("Fecha: ")).setBounds(x, y+=25, ancho, 20);
		panel.add(fh_unica_repeticion).setBounds(x+ancho-10, y, ancho+40, 20);
		panel.add(new JLabel("Hora: ")).setBounds(x+ancho*3, y, ancho, 20);
		panel.add(spHoraUnicaRepeticion).setBounds(x+ancho*4-10, y, ancho+20, 20);
		
	//frecuencia
		panel.add(new JLabel("Frecuencia")).setBounds(x-40, y+=25, ancho, 20);
		panel.add(new JSeparator()).setBounds(x+ancho-50, y+11, ancho*8+60, 20);
		
		panel.add(new JLabel("Sucede: ")).setBounds(x, y+=25, ancho, 20);
		panel.add(chbSucede).setBounds(x+ancho, y, ancho+30, 20);
		
		panel.add(lblSeRepiteCada).setBounds(x, y+=25, ancho+30, 20);
		panel.add(spDiasARepetir).setBounds(x+ancho+30, y, ancho, 20);
		panel.add(lblDias_Semana).setBounds(x+ancho*2+50, y, ancho, 20);
		
		panel.add(spMeses).setBounds(x+ancho*3+20, y, ancho, 20);
		panel.add(lblMeses).setBounds(x+ancho*4+40, y, ancho, 20);
		
//			semana
		panel.add(chLunes).setBounds(x+110, y+=25, ancho, 20);
		panel.add(chMartes).setBounds(x+210, y, ancho, 20);
		panel.add(chMiercoles).setBounds(x+310, y, ancho, 20);
		panel.add(chJueves).setBounds(x+410, y, ancho, 20);
		
		panel.add(chViernes).setBounds(x+110, y+=25, ancho, 20);
		panel.add(chSabado).setBounds(x+210, y, ancho, 20);
		panel.add(chDomingo).setBounds(x+310, y, ancho, 20);
		
//		frecuencia diaria
		panel.add(new JLabel("Frecuencia Diaria")).setBounds(x-40, y+=25, ancho+20, 20);
		panel.add(new JSeparator()).setBounds(x+ancho-20, y+11, ancho*8+30, 20);
		
		panel.add(chAsignarHora).setBounds(x, y+=25, ancho+40, 20);
		panel.add(spHoraFrecuenciaDiaria).setBounds(x+ancho+40, y, ancho+20, 20);
		
//		Duracion
		panel.add(new JLabel("Duracion")).setBounds(x-40, y+=25, ancho, 20);
		panel.add(new JSeparator()).setBounds(x+ancho-60, y+11, ancho*8+70, 20);
		
		
		panel.add(new JLabel("Fecha De Inicio: ")).setBounds(x+30, y+=25, ancho, 20);
		panel.add(fh_inicial_de_duracion).setBounds(x+ancho+40, y, ancho+40, 20);
		panel.add(rbFechaDeFinalizacion).setBounds(x+ancho*3+30, y, ancho+60, 20);
		panel.add(fh_final_de_duracion).setBounds(x+ancho*5+10, y, ancho+40, 20);
		panel.add(rbSinFechaDeFinalizacion).setBounds(x+ancho*3+30, y+=25, ancho*2, 20);


		
//		resumen
		panel.add(new JLabel("Resumen")).setBounds(x-40, y+=25, ancho, 20);
		panel.add(new JSeparator()).setBounds(x+ancho-60, y+11, ancho*8+70, 20);
		panel.add(scrollDescripcion).setBounds(x, y+=25, ancho*8+50, 60);
		
//		botones
		panel.add(btnAceptar).setBounds(560, y+=65, ancho, 20);
		panel.add(btnCancelar).setBounds(660, y, ancho, 20);
		
		
		cont.add(panel);
		
		this.setSize(800,485);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
	}
	
	@SuppressWarnings("deprecation")
	public void tiempodefault(){
		
//		String[] fechas = new BuscarSQL().fechas_de_movimiento_de_cascos();
//		
//		try {
//			fh_in.setDate((Date) new SimpleDateFormat("dd/MM/yyyy").parse(fechas[0].toString()));
//			fechaFinal_principal=fechas[2].toString();
//			fh_fin.setDate((Date) new SimpleDateFormat("dd/MM/yyyy").parse(fechaFinal_principal));
//			
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		
//		fechas[1].toString().split(":");
//		String[] inicioDefault = fechas[1].toString().split(":");
		String[] inicioDefault = "00:00:00".split(":");
		spHoraUnicaRepeticion.setValue(new Time(Integer.parseInt(inicioDefault[0]),Integer.parseInt(inicioDefault[1]),Integer.parseInt(inicioDefault[2])));
		spHoraUnicaRepeticion.setEditor(spDHoraUnicaRepeticion);
		
		
		
//		String[] FinDefault = fechas[3].toString().split(":");
		String[] FinDefault = "23:59:00".split(":");
		spHoraFrecuenciaDiaria.setValue(new Time(Integer.parseInt(FinDefault[0]),Integer.parseInt(FinDefault[1]),Integer.parseInt(inicioDefault[2])));
		spHoraFrecuenciaDiaria.setEditor(spDHoraFrecuenciaDiaria);
		
	}

	public static void main(String[] args) {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Frecuencia_De_Actividades().setVisible(true);
		}catch(Exception e){
			System.err.println("Error :"+ e.getMessage());
		}
	}

}
