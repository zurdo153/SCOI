package Biblioteca;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Conexiones_SQL.BuscarSQL;
import Obj_Planeacion.Obj_Frecuencia_De_Actividades;
import Obj_Principal.Componentes;

import com.toedter.calendar.JDateChooser;

@SuppressWarnings("serial")
public class Cat_Frecuencia_De_Actividades extends JFrame{
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
//tipo de programacion	
	String[] tipo_de_programacion = {"UNA VEZ","PERIODICA"};
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbTipoDeProgramacion = new JComboBox(tipo_de_programacion);
	
	JRadioButton rbHastaQueSeCumpla = new JRadioButton("Hasta Que Se Cumpla");
	JRadioButton rbEnLaFechaIndicada = new JRadioButton("En La Fecha Indicada");
	ButtonGroup grupoTipoDeProgramacion = new ButtonGroup();
	
//unica repeticion
	JLabel lblUnicarepeticion = new JLabel("Desde");
	JDateChooser fh_unica_repeticion = new JDateChooser();
	
	JCheckBox chbConHora = new JCheckBox();
	SpinnerDateModel sdmUnicaRepeticion =  new SpinnerDateModel();
	  JSpinner spHoraUnicaRepeticion = new JSpinner(sdmUnicaRepeticion);                                         
	  JSpinner.DateEditor spDHoraUnicaRepeticion = new JSpinner.DateEditor(spHoraUnicaRepeticion,"HH:mm:ss"); 
	
//frecuencia  -----------------------------------------------------------------------------
	  String[] secede = {"DIARIA","SEMANAL","MENSUAL"};
		@SuppressWarnings({ "rawtypes", "unchecked" })
		JComboBox cmbSucede = new JComboBox(secede);
		
		JRadioButton rbDiaDelMes = new JRadioButton("El Dia");
		JRadioButton rbDiaDeLaSemana = new JRadioButton("EL");
		ButtonGroup grupoDias = new ButtonGroup();
		
		JLabel lblSeRepiteCada = new JLabel("Se Repite Cada: ");
		JSpinner spDiasARepetir = new JSpinner(new SpinnerNumberModel( 1, 1, 31, 1 )); 
		JLabel lblDias_Semana = new JLabel("Dias");
		
		
		JCheckBox chbLunes = new JCheckBox("Lunes");
		JCheckBox chbMartes = new JCheckBox("Martes");
		JCheckBox chbMiercoles = new JCheckBox("Miercoles");
		JCheckBox chbJueves = new JCheckBox("Jueves");
		JCheckBox chbViernes = new JCheckBox("Viernes");
		JCheckBox chbSabado = new JCheckBox("Sabado");
		JCheckBox chbDomingo = new JCheckBox("Domingo");
		
		JSpinner spMeses = new JSpinner(new SpinnerNumberModel( 1, 1, 12, 1 )); 
		JLabel lblMeses = new JLabel("Meses");
		
//		configuracion por dias del mes
		String[] numeroDeDias = {"PRIMERO","SEGUNDO","TERCERO","CUARTO","ULTIMO"};
		@SuppressWarnings({ "rawtypes", "unchecked" })
		JComboBox cmbNivelDeDias = new JComboBox(numeroDeDias);
		
		String[] diaDeLaSemana = {"DOMINGO","LUNES","MARTES","MIERCOLES","JUEVES","VIERNES","SABADO"};
		@SuppressWarnings({ "rawtypes", "unchecked" })
		JComboBox cmbDiaDeLaSemana = new JComboBox(diaDeLaSemana);
				
		JLabel lblDeCada = new JLabel("De Cada");
		JSpinner spMeses2 = new JSpinner(new SpinnerNumberModel( 1, 1, 12, 1 )); 
		JLabel lblMeses2 = new JLabel("Meses");
		
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
		
		grupoDias.add(rbDiaDelMes);
		grupoDias.add(rbDiaDeLaSemana);
		
		rbHastaQueSeCumpla.setSelected(true);
		rbSinFechaDeFinalizacion.setSelected(true);
		rbDiaDelMes.setSelected(true);
		
		txaDescripcion.setBorder(BorderFactory.createTitledBorder(blackline));
		txaDescripcion.setLineWrap(true); 
		txaDescripcion.setWrapStyleWord(true);
		txaDescripcion.setEditable(false);
		
		int x=50,y=15,ancho=80;
		
		panel.add(new JLabel("Tipo De Programación: ")).setBounds(x, y, ancho+50, 20);
		panel.add(cmbTipoDeProgramacion).setBounds(x+ancho+60, y, ancho*2, 20);
		
		panel.add(rbHastaQueSeCumpla).setBounds(x+ancho*4+60, y, ancho*2, 20);
		panel.add(rbEnLaFechaIndicada).setBounds(x+ancho*6+60, y, ancho*2, 20);
				
	//unica repeticion
		panel.add(lblUnicarepeticion).setBounds(x-40, y+=25, ancho, 20);
		panel.add(new JSeparator()).setBounds(x+ancho-60, y+11, ancho*8+70, 20);
		
		panel.add(new JLabel("Fecha: ")).setBounds(x, y+=25, ancho, 20);
		panel.add(fh_unica_repeticion).setBounds(x+ancho-10, y, ancho+40, 20);
		panel.add(new JLabel("Hora: ")).setBounds(x+ancho*3, y, ancho, 20);
		panel.add(chbConHora).setBounds(x+ancho*4-40, y, 30, 20);
		panel.add(spHoraUnicaRepeticion).setBounds(x+ancho*4-10, y, ancho+20, 20);
		
	//frecuencia
		panel.add(new JLabel("Frecuencia")).setBounds(x-40, y+=25, ancho, 20);
		panel.add(new JSeparator()).setBounds(x+ancho-50, y+11, ancho*8+60, 20);
		
		panel.add(new JLabel("Sucede: ")).setBounds(x, y+=25, ancho, 20);
		panel.add(cmbSucede).setBounds(x+ancho, y, ancho+30, 20);
		
		panel.add(rbDiaDelMes).setBounds(x-40, y+=25, 60, 20);
		panel.add(lblSeRepiteCada).setBounds(x, y, ancho+30, 20);
		panel.add(spDiasARepetir).setBounds(x+ancho+30, y, ancho, 20);
		panel.add(lblDias_Semana).setBounds(x+ancho*2+50, y, ancho, 20);
		
		panel.add(spMeses).setBounds(x+ancho*3+20, y, ancho, 20);
		panel.add(lblMeses).setBounds(x+ancho*4+40, y, ancho, 20);
		
		panel.add(rbDiaDeLaSemana).setBounds(x-40, y+=25, 60, 20);
		
		panel.add(cmbNivelDeDias).setBounds(x+110, y, ancho, 20);
		panel.add(cmbDiaDeLaSemana).setBounds(x+210, y, ancho, 20);
		panel.add(lblDeCada).setBounds(x+300, y, ancho, 20);
		panel.add(spMeses2).setBounds(x+350, y, ancho, 20);
		panel.add(lblMeses2).setBounds(x+450, y, ancho, 20);
		
//		semana------
		panel.add(chbLunes).setBounds(x+110, y, ancho, 20);
		panel.add(chbMartes).setBounds(x+210, y, ancho, 20);
		panel.add(chbMiercoles).setBounds(x+310, y, ancho, 20);
		panel.add(chbJueves).setBounds(x+410, y, ancho, 20);
		
		panel.add(chbViernes).setBounds(x+110, y+=25, ancho, 20);
		panel.add(chbSabado).setBounds(x+210, y, ancho, 20);
		panel.add(chbDomingo).setBounds(x+310, y, ancho, 20);
		
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
		panel.add(scrollDescripcion).setBounds(x, y+=25, ancho*8+50, 100);
		
//		botones
		panel.add(btnAceptar).setBounds(560, y+=105, ancho, 20);
		panel.add(btnCancelar).setBounds(660, y, ancho, 20);
		
		fh_unica_repeticion.setDate(cargar_fechas(0));
		fh_inicial_de_duracion.setDate(cargar_fechas(0));
		fh_final_de_duracion.setDate(cargar_fechas(-7));
		programacion();
		txaDescripcion.setText(	DescripcionDeConfiguaracionDeFrecuencia());
		
		cmbTipoDeProgramacion.addActionListener(opProgramacion);
		
		rbHastaQueSeCumpla.addActionListener(opRbTipoPRogramacion);
		rbEnLaFechaIndicada.addActionListener(opRbTipoPRogramacion);
		
		chbConHora.addActionListener(opConHora);
		
		rbDiaDeLaSemana.addActionListener(opRbFrecuencia);
		rbDiaDelMes.addActionListener(opRbFrecuencia);
		
		cmbSucede.addActionListener(opFrecuenciaSuceso);
		
		rbFechaDeFinalizacion.addActionListener(opRbDuracion);
		rbSinFechaDeFinalizacion.addActionListener(opRbDuracion);
		
		chAsignarHora.addActionListener(opFrecuenciaDiaria);
		
		spDiasARepetir.addChangeListener(opDescripcion);
		spHoraFrecuenciaDiaria.addChangeListener(opDescripcion);
		spMeses.addChangeListener(opDescripcion);
		spMeses2.addChangeListener(opDescripcion);
		
		fh_inicial_de_duracion.addPropertyChangeListener(opDescripcion2);
		fh_final_de_duracion.addPropertyChangeListener(opDescripcion2);
		
		chbDomingo.addActionListener(opDescripcion3);
		chbLunes.addActionListener(opDescripcion3);
		chbMartes.addActionListener(opDescripcion3);
		chbMiercoles.addActionListener(opDescripcion3);
		chbJueves.addActionListener(opDescripcion3);
		chbViernes.addActionListener(opDescripcion3);
		chbSabado.addActionListener(opDescripcion3);
		
		cmbNivelDeDias.addActionListener(opDescripcion3);
		cmbDiaDeLaSemana.addActionListener(opDescripcion3);

		btnAceptar.addActionListener(opGuardar);
		
		cont.add(panel);
		
		this.setSize(800,625);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
	}
	

	
	ActionListener opGuardar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			llenarObjeto();
			leerObjeto();
		}
	};
	ActionListener opProgramacion = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			programacion();
			txaDescripcion.setText(	DescripcionDeConfiguaracionDeFrecuencia());
		}
	};
	
	ActionListener opRbFrecuencia = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			diasDeFrecuenciaPorMes();
			txaDescripcion.setText(	DescripcionDeConfiguaracionDeFrecuencia());
		}
	};
	
	ActionListener opFrecuenciaSuceso = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			frecuenciaSucede();
			txaDescripcion.setText(	DescripcionDeConfiguaracionDeFrecuencia());
		}
	};
	
	ActionListener opRbDuracion = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			fechaDuracion();
			txaDescripcion.setText(	DescripcionDeConfiguaracionDeFrecuencia());
		}
	};
	
	ActionListener opFrecuenciaDiaria = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			frecuenciaDiaria();
			txaDescripcion.setText(	DescripcionDeConfiguaracionDeFrecuencia());
		}
	};
	
	ActionListener opRbTipoPRogramacion = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			rbTipoPRogramacion();
			txaDescripcion.setText(	DescripcionDeConfiguaracionDeFrecuencia());
		}
	};
	
	ActionListener opConHora = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			 conHora();
			 txaDescripcion.setText(	DescripcionDeConfiguaracionDeFrecuencia());
		}
	};
	
	ActionListener opDescripcion3 = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			 txaDescripcion.setText(	DescripcionDeConfiguaracionDeFrecuencia());
		}
	};
	ChangeListener opDescripcion = new ChangeListener() {
		public void stateChanged(ChangeEvent e) {
			txaDescripcion.setText(	DescripcionDeConfiguaracionDeFrecuencia());
		}
	};
	PropertyChangeListener opDescripcion2 = new PropertyChangeListener() {
		public void propertyChange(PropertyChangeEvent evt) {
			txaDescripcion.setText(	DescripcionDeConfiguaracionDeFrecuencia());
		}
	};
	
	public void programacion(){

		if(cmbTipoDeProgramacion.getSelectedItem().toString().equals("UNA VEZ")){
			  
			  rbHastaQueSeCumpla.setEnabled(true);
			  rbEnLaFechaIndicada.setEnabled(true);
			  fh_unica_repeticion.setEnabled(true);
			  chbConHora.setEnabled(true);
			  spHoraUnicaRepeticion.setEnabled(true);
			  
			  cmbSucede					.setEnabled(false);	
			  rbDiaDelMes					.setEnabled(false);
			  spDiasARepetir				.setEnabled(false);
			  spMeses						.setEnabled(false);
			  rbDiaDeLaSemana				.setEnabled(false);
			  cmbNivelDeDias				.setEnabled(false);
			  cmbDiaDeLaSemana			.setEnabled(false);	
			  spMeses2					.setEnabled(false);	
			  chbLunes					.setEnabled(false);	
			  chbMartes					.setEnabled(false);	
			  chbMiercoles				.setEnabled(false);	
			  chbJueves					.setEnabled(false);	
			  chbViernes					.setEnabled(false);
			  chbSabado					.setEnabled(false);	
			  chbDomingo					.setEnabled(false);
			  chAsignarHora				.setEnabled(false);	
			  spHoraFrecuenciaDiaria		.setEnabled(false);
			  fh_inicial_de_duracion		.setEnabled(false);
			  rbFechaDeFinalizacion		.setEnabled(false);	
			  fh_final_de_duracion		.setEnabled(false);	
			  rbSinFechaDeFinalizacion	.setEnabled(false);
				
			  conHora();
			  frecuenciaSucede();
			  
		  }else{
		    rbHastaQueSeCumpla.setEnabled(false);
		    rbEnLaFechaIndicada.setEnabled(false);
		    fh_unica_repeticion.setEnabled(false);
		    chbConHora.setEnabled(false);
		    spHoraUnicaRepeticion.setEnabled(false);
			  
			cmbSucede					.setEnabled(true);	
			rbDiaDelMes					.setEnabled(true);
			spDiasARepetir				.setEnabled(true);
			spMeses						.setEnabled(true);
			rbDiaDeLaSemana				.setEnabled(true);
			cmbNivelDeDias				.setEnabled(true);
			cmbDiaDeLaSemana			.setEnabled(true);	
			spMeses2					.setEnabled(true);	
			chbLunes					.setEnabled(true);	
			chbMartes					.setEnabled(true);	
			chbMiercoles				.setEnabled(true);	
			chbJueves					.setEnabled(true);	
			chbViernes					.setEnabled(true);
			chbSabado					.setEnabled(true);	
			chbDomingo					.setEnabled(true);
			chAsignarHora				.setEnabled(true);	
			spHoraFrecuenciaDiaria		.setEnabled(true);
			fh_inicial_de_duracion		.setEnabled(true);
			rbFechaDeFinalizacion		.setEnabled(true);	
			fh_final_de_duracion		.setEnabled(true);	
			rbSinFechaDeFinalizacion	.setEnabled(true);
			  
				rbTipoPRogramacion();
				conHora();
				frecuenciaSucede();
				frecuenciaDiaria();
				fechaDuracion();
		  }
	}
	
	public void frecuenciaSucede(){
		
		switch(cmbSucede.getSelectedItem().toString()){
			case "DIARIA":
				
				lblSeRepiteCada.setVisible(true);
				
				spMeses.setVisible(false);
				lblMeses.setVisible(false);
				
				chbLunes.setVisible(false);
				chbMartes.setVisible(false);
				chbMiercoles.setVisible(false);
				chbJueves.setVisible(false);
				chbViernes.setVisible(false);
				chbSabado.setVisible(false);
				chbDomingo.setVisible(false); 
				
				rbDiaDelMes.setVisible(false);
				rbDiaDeLaSemana.setVisible(false);
				
				cmbNivelDeDias.setVisible(false);
				cmbDiaDeLaSemana.setVisible(false);
				lblDeCada.setVisible(false);
				spMeses2.setVisible(false);
				lblMeses2.setVisible(false);
				
				lblDias_Semana.setText("Dias");
				
			break;
			case "SEMANAL":
				
				lblSeRepiteCada.setVisible(true);
				
				spMeses.setVisible(false);
				lblMeses.setVisible(false);
				
				chbLunes.setVisible(true);
				chbMartes.setVisible(true);
				chbMiercoles.setVisible(true);
				chbJueves.setVisible(true);
				chbViernes.setVisible(true);
				chbSabado.setVisible(true);
				chbDomingo.setVisible(true); 
				
				rbDiaDelMes.setVisible(false);
				rbDiaDeLaSemana.setVisible(false);
				
				cmbNivelDeDias.setVisible(false);
				cmbDiaDeLaSemana.setVisible(false);
				lblDeCada.setVisible(false);
				spMeses2.setVisible(false);
				lblMeses2.setVisible(false);
				
				lblDias_Semana.setText("Semanas, el");
				
			break;
			case "MENSUAL":
				
				lblSeRepiteCada.setVisible(false);
				
				spMeses.setVisible(true);
				lblMeses.setVisible(true);
				
				chbLunes.setVisible(false);
				chbMartes.setVisible(false);
				chbMiercoles.setVisible(false);
				chbJueves.setVisible(false);
				chbViernes.setVisible(false);
				chbSabado.setVisible(false);
				chbDomingo.setVisible(false); 
				
				rbDiaDelMes.setVisible(true);
				rbDiaDeLaSemana.setVisible(true);
				
				cmbNivelDeDias.setVisible(true);
				cmbDiaDeLaSemana.setVisible(true);
				lblDeCada.setVisible(true);
				spMeses2.setVisible(true);
				lblMeses2.setVisible(true);
				
				lblDias_Semana.setText("De Cada");
				
			break;
		}
		rbDiaDelMes.doClick();
	}
	
	public void diasDeFrecuenciaPorMes(){
		
		if(rbDiaDelMes.isSelected()){
			spDiasARepetir.setEnabled(true);
			spMeses.setEnabled(true);
			
			cmbNivelDeDias.setEnabled(false);
			cmbDiaDeLaSemana.setEnabled(false);
			spMeses2.setEnabled(false);
		}else{
			spDiasARepetir.setEnabled(false);
			spMeses.setEnabled(false);
			
			cmbNivelDeDias.setEnabled(true);
			cmbDiaDeLaSemana.setEnabled(true);
			spMeses2.setEnabled(true);
		}
	}
	
	public void fechaDuracion(){
		if(rbFechaDeFinalizacion.isSelected()){
			fh_final_de_duracion.setEnabled(true);
		}else{
			fh_final_de_duracion.setEnabled(false);
		}
	}
	
	public void frecuenciaDiaria(){
		if(chAsignarHora.isSelected()){
			spHoraFrecuenciaDiaria.setEnabled(true);
		}else{
			spHoraFrecuenciaDiaria.setEnabled(false);
		}
	}
	
	public void rbTipoPRogramacion(){
		if(rbHastaQueSeCumpla.isSelected()){
			lblUnicarepeticion.setText("Desde");
		}else{
			lblUnicarepeticion.setText("Antes de");
		}
	}
	
	public void conHora(){
		if(chbConHora.isSelected()){
			spHoraUnicaRepeticion.setEnabled(true);
		}else{
			spHoraUnicaRepeticion.setEnabled(false);
		}
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
		String[] inicioDefault = "23:59:00".split(":");
		spHoraUnicaRepeticion.setValue(new Time(Integer.parseInt(inicioDefault[0]),Integer.parseInt(inicioDefault[1]),Integer.parseInt(inicioDefault[2])));
		spHoraUnicaRepeticion.setEditor(spDHoraUnicaRepeticion);
		
//		String[] FinDefault = fechas[3].toString().split(":");
		String[] FinDefault = "12:00:00".split(":");
			 spHoraFrecuenciaDiaria.setValue(new Time(Integer.parseInt(FinDefault[0]),Integer.parseInt(FinDefault[1]),Integer.parseInt(inicioDefault[2])));
				spHoraFrecuenciaDiaria.setEditor(spDHoraFrecuenciaDiaria);
	}

	public Date cargar_fechas(int dias){
		Date date = null;
				  try {
					date = new SimpleDateFormat("dd/MM/yyyy").parse(new BuscarSQL().fecha(dias));
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		return date;
	};
	
	public String DescripcionDeConfiguaracionDeFrecuencia(){
		String cadena = "";
		
		if(cmbTipoDeProgramacion.getSelectedItem().equals("UNA VEZ")){
			
			cadena += rbHastaQueSeCumpla.isSelected()?
					("Sucedes Desde El "+(new SimpleDateFormat("dd/MM/yyyy").format(fh_unica_repeticion.getDate()))+" A Las "+(chbConHora.isSelected()?
																																		(new SimpleDateFormat("HH:mm:ss").format(spHoraUnicaRepeticion.getValue())):
																																		"00:00:00") +" Hasta Que Se Cumpla."):
						("Sucedes EL "+(new SimpleDateFormat("dd/MM/yyyy").format(fh_unica_repeticion.getDate()))+" Hasta Antes De Las "+(chbConHora.isSelected()?
																																					(new SimpleDateFormat("HH:mm:ss").format(spHoraUnicaRepeticion.getValue())):
																																					"23:59:00.") );
		}else{
			
			if(cmbSucede.getSelectedItem().toString().equals("DIARIA")){
				
				cadena += "Sucede Cada "+(Integer.valueOf(spDiasARepetir.getValue().toString())==1?
						"Dia":
						spDiasARepetir.getValue()+" Dias")+(chAsignarHora.isSelected()?" A Las "+(new SimpleDateFormat("HH:mm:ss").format(spHoraFrecuenciaDiaria.getValue()))+".":" Sin Importar Hora.");
			}
			if(cmbSucede.getSelectedItem().toString().equals("SEMANAL")){
				String dias = "";
				if(chbDomingo.isSelected()){dias+=" Domingo,";};
				if(chbLunes.isSelected()){dias+=" Lunes,";};
				if(chbMartes.isSelected()){dias+=" Martes,";};
				if(chbMiercoles.isSelected()){dias+=" Miercoles,";};
				if(chbJueves.isSelected()){dias+=" Jueves,";};
				if(chbViernes.isSelected()){dias+=" Viernes,";};
				if(chbSabado.isSelected()){dias+=" Sabado,";};
				
				cadena += "Sucede Cada "+(Integer.valueOf(spDiasARepetir.getValue().toString())==1?
						"Semana":
						spDiasARepetir.getValue()+" Semanas")+(dias.equals("")?"":" Los Dias "+dias.substring(0, dias.length()-1))+(chAsignarHora.isSelected()?" A Las "+(new SimpleDateFormat("HH:mm:ss").format(spHoraFrecuenciaDiaria.getValue()))+".":" Sin Importar Hora.");

			}
			if(cmbSucede.getSelectedItem().toString().equals("MENSUAL")){
				
				cadena +=rbDiaDelMes.isSelected()?(
						"Sucede El Dia "+(spDiasARepetir.getValue().toString())+
											(Integer.valueOf(spMeses.getValue().toString())==1 ? " De Cada Mes"	: " Cada "+spMeses.getValue()+" Meses")+
																																	(chAsignarHora.isSelected() ? " A Las "+(new SimpleDateFormat("HH:mm:ss").format(spHoraFrecuenciaDiaria.getValue()))+"." : ".")
						):
				"Sucede Cada "+(cmbNivelDeDias.getSelectedItem())+" "+(cmbDiaDeLaSemana.getSelectedItem())+
																	( Integer.valueOf(spMeses2.getValue().toString())==1 ? " De Cada Mes"+( chAsignarHora.isSelected() ? " A Las "+(new SimpleDateFormat("HH:mm:ss").format(spHoraFrecuenciaDiaria.getValue())+".") : ".")
																														: " De Cada "+spMeses2.getValue()+" Meses"+(chAsignarHora.isSelected() ? " A Las "+(new SimpleDateFormat("HH:mm:ss").format(spHoraFrecuenciaDiaria.getValue())+".") : ".")
						);
			}
			
			cadena += " La frecuencia De La Actividad Inicia A Partir Del Dia "+(new SimpleDateFormat("dd/MM/yyyy").format(fh_inicial_de_duracion.getDate()))+(rbFechaDeFinalizacion.isSelected()?
																																									" Y Termina El Dia "+(new SimpleDateFormat("dd/MM/yyyy").format(fh_final_de_duracion.getDate()))+".":
																																									" Hasta Que Se Cumpla.");
		}
		return cadena;
	}
	
	Obj_Frecuencia_De_Actividades frecuencia = new Obj_Frecuencia_De_Actividades();
	public void llenarObjeto(){
		
		frecuencia.setTipo_de_frecuencia(cmbTipoDeProgramacion.getSelectedItem().toString());
		frecuencia.setSeleccion_hasta_que_se_cumpla(rbHastaQueSeCumpla.isSelected());
		frecuencia.setSeleccion_en_la_fecha_indicada(rbEnLaFechaIndicada.isSelected());
		
	//unica repeticion
		frecuencia.setFh_unica_repeticion(new SimpleDateFormat("dd/MM/yyyy").format(fh_unica_repeticion.getDate()));
		frecuencia.setSeleccion_con_hora(chbConHora.isSelected());
		frecuencia.setHora_unica_repeticion(new SimpleDateFormat("HH:mm:ss").format(spHoraUnicaRepeticion.getValue()));
		
	//frecuencia
		frecuencia.setSucede(cmbSucede.getSelectedItem().toString());
		frecuencia.setSelecciona_dia_del_mes(rbDiaDelMes.isSelected());
		frecuencia.setDias_a_repetir_por_suceso_de_dias(cmbSucede.getSelectedItem().toString().equals("DIARIA")?Integer.valueOf(spDiasARepetir.getValue().toString()):0);
		frecuencia.setDias_a_repetir_por_suceso_de_dias(cmbSucede.getSelectedItem().toString().equals("SEMANAL")?Integer.valueOf(spDiasARepetir.getValue().toString()):0);
		frecuencia.setDias_a_repetir_por_suceso_de_dias(cmbSucede.getSelectedItem().toString().equals("MENSUAL")?Integer.valueOf(spDiasARepetir.getValue().toString()):0);
		frecuencia.setMes1(Integer.valueOf(spMeses.getValue().toString()));
		
		frecuencia.setSelecciona_dia_de_la_semana(rbDiaDeLaSemana.isSelected());
		frecuencia.setNivel_de_dias(cmbNivelDeDias.getSelectedItem().toString());
//		frecuencia.setDia_de_la_semana(cmbDiaDeLaSemana.getSelectedItem().toString());
		frecuencia.setMes2(Integer.valueOf(spMeses2.getValue().toString()));
		
//		semana------
		frecuencia.setDomingo(chbDomingo.isSelected());
		frecuencia.setLunes(chbLunes.isSelected());
		frecuencia.setMartes(chbMartes.isSelected());
		frecuencia.setMiercoles(chbMiercoles.isSelected());
		frecuencia.setJueves(chbJueves.isSelected());
		frecuencia.setViernes(chbViernes.isSelected());
		frecuencia.setSabado(chbSabado.isSelected());
		
//		frecuencia diaria
		frecuencia.setSeleccion_asignar_hora(chAsignarHora.isSelected());
		frecuencia.setHora_frecuencia_diaria(new SimpleDateFormat("HH:mm:ss").format(spHoraFrecuenciaDiaria.getValue()));
		
//		Duracion
		frecuencia.setFecha_inicio_duracion(new SimpleDateFormat("dd/MM/yyyy").format(fh_inicial_de_duracion.getDate()));
		
		frecuencia.setSeleccion_fecha_finaliza(rbFechaDeFinalizacion.isSelected());
		frecuencia.setFecha_final_duracion(rbFechaDeFinalizacion.isSelected()?(new SimpleDateFormat("dd/MM/yyyy").format(fh_inicial_de_duracion.getDate())):"01/01/1900");
		frecuencia.setSeleccion_sin_fecha_final(rbSinFechaDeFinalizacion.isSelected());
	}
	
	public void leerObjeto(){
		
		System.out.println(frecuencia.getTipo_de_frecuencia());
		System.out.println(frecuencia.isSeleccion_hasta_que_se_cumpla()+"");
		System.out.println(frecuencia.isSeleccion_en_la_fecha_indicada()+"");
		
	//unica repeticion
		System.out.println(frecuencia.getFh_unica_repeticion());
		System.out.println(frecuencia.isSeleccion_con_hora());
		System.out.println(frecuencia.getHora_unica_repeticion());
		
	//frecuencia
		System.out.println(frecuencia.getSucede());
		System.out.println(frecuencia.isSelecciona_dia_del_mes());
		System.out.println(frecuencia.getDias_a_repetir_por_suceso_de_dias());
		System.out.println(frecuencia.getDias_a_repetir_por_suceso_de_dias());
		System.out.println(frecuencia.getDias_a_repetir_por_suceso_de_dias());
		System.out.println(frecuencia.getMes1());
		
		System.out.println(frecuencia.isSelecciona_dia_de_la_semana());
		System.out.println(frecuencia.getNivel_de_dias());
		System.out.println(frecuencia.getDia_de_la_semana());
		System.out.println(frecuencia.getMes2());
		
//		semana------
		System.out.println(frecuencia.isDomingo());
		System.out.println(frecuencia.isLunes());
		System.out.println(frecuencia.isMartes());
		System.out.println(frecuencia.isMiercoles());
		System.out.println(frecuencia.isJueves());
		System.out.println(frecuencia.isViernes());
		System.out.println(frecuencia.isSabado());
		
//		frecuencia diaria
		System.out.println(frecuencia.isSeleccion_asignar_hora());
		System.out.println(frecuencia.getHora_frecuencia_diaria());
		
//		Duracion
		System.out.println(frecuencia.getFecha_inicio_duracion());
		
		System.out.println(frecuencia.isSeleccion_fecha_finaliza());
		System.out.println(frecuencia.getFecha_final_duracion());
		System.out.println(frecuencia.isSeleccion_sin_fecha_final());
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
