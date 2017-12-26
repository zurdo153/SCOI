package Cat_Servicios;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Cargar_Combo;
import Obj_Lista_de_Raya.Obj_Departamento;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_Filtro_Dinamico_Plus;
import Obj_Renders.tablaRenderer;
import Obj_Servicios.Obj_Administracion_De_Activos;
import Obj_Servicios.Obj_Marcas_De_Activos;
import Obj_Servicios.Obj_Modelos_De_Activos;
import Obj_Servicios.Obj_Tipos_De_Equipos;

@SuppressWarnings("serial")
public class Cat_Administracion_De_Activos extends JFrame{
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JTextField txtGrupoMayor = new Componentes().text(new JCTextField(), "G. Mayor", 3, "Int");
	
	JTextField txtFolio = new Componentes().text(new JCTextField(), "Folio", 10, "Int");
	JTextField txtDescripcion = new Componentes().text(new JCTextField(), "Descripcion Corta", 100, "String");
	JTextField txtSerie = new Componentes().text(new JCTextField(), "Serie De Activo", 50, "String");
	
	JTextField txtCosto = new Componentes().text(new JCTextField(), "Costo Neto", 15, "Double");
	JTextField txtDepreciacion = new Componentes().text(new JCTextField(), "Depreciacion Anual", 15, "Double");
	
	String[] deptoResponsable = new Obj_Departamento().Combo_Departamentoservicio();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbDepartamentoResponsable = new JComboBox(deptoResponsable);
	
	String[] status = {"Vigente","Baja"};
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbStatus = new JComboBox(status);
	
	String tipo[] =  new Obj_Tipos_De_Equipos().Combo_Tipos_De_Equipo();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbTipo = new JComboBox(tipo);
	
	String establecimiento[] = new Obj_Establecimiento().Combo_Establecimiento();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbEstablecimiento = new JComboBox(establecimiento);
	
	String Departamentos[] = new Obj_Departamento().Combo_Departamento();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbDepartamento = new JComboBox(Departamentos);  
	
	String[] marca = new Obj_Marcas_De_Activos().Combo_Marcas();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbMarca = new JComboBox(marca);
	
	String[] modelo = new Obj_Modelos_De_Activos().Combo_Modelos();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbModelo = new JComboBox(modelo);
	
	String anio = cargar_fecha().toString().substring(5, 10);
	
	public int anioActual(){
		int anio = 0;
		try {
			anio = Integer.valueOf((new BuscarSQL().fecha(0)).substring(6, 10));
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return anio;
	}
	
	public String[] anios(){
		int AnioIn = 1980;
		int AnioFin= anioActual()+1;
		String[] ls = new String[(AnioFin-AnioIn)];
		for(int i = 0; i<(AnioFin-AnioIn); i++){
			ls[i] = AnioIn+i+"";
		}
		return ls;
	} 
	String[] anioFabricacion = anios();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbAnioFabricacion = new JComboBox(anioFabricacion);
	
	String[] unidad_vida_util = new Cargar_Combo().Unidada_De_Vida_Util_Y_Garantia_De_Equipo();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbVidaUtil = new JComboBox(unidad_vida_util);
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbGarantia = new JComboBox(unidad_vida_util);
	
	// Se define un modelo para el Spinner, consultar 
	// http://java.sun.com/j2se/1.4.2/docs/api/javax/swing/JSpinner.html 

	SpinnerNumberModel modelGrupoEquipo = new SpinnerNumberModel( 
														new Integer(1), 	 // Dato visualizado al inicio en el spinner 
														new Integer(0), 	 // Límite inferior 
														new Integer(999), 	 // Límite superior 
														new Integer(1) 		 // incremento-decremento 
	);
	
	SpinnerNumberModel modelGarantia = new SpinnerNumberModel( 
														new Integer(1), 	 // Dato visualizado al inicio en el spinner 
														new Integer(0), 	 // Límite inferior 
														new Integer(100000), // Límite superior 
														new Integer(1) 		 // incremento-decremento 
														);
	SpinnerNumberModel modelVidaUtil = new SpinnerNumberModel( 
														new Integer(5), 	 // Dato visualizado al inicio en el spinner 
														new Integer(0), 	 // Límite inferior 
														new Integer(100000), // Límite superior 
														new Integer(1) 		 // incremento-decremento 
														);

	JSpinner spnGrupoEquipo = new JSpinner(modelGrupoEquipo);
	JSpinner spnGarantia = new JSpinner(modelGarantia);
	JSpinner spnVidaUtil = new JSpinner(modelVidaUtil);
//	label.setText(((SpinnerNumberModel)spinner.getModel()).getNumber().toString());
	
	JDateChooser fchCompra = new JDateChooser();
	
	JTextArea txaCaracteristicas = new Componentes().textArea(new JTextArea(), "Caracteristicas", 500);
	JScrollPane scrollCaracteristicas = new JScrollPane(txaCaracteristicas);
	
	JCButton btnFactura = new JCButton("Factura", "factura24.png", "Azul");
	JCButton btnExaminar = new JCButton("Foto", "galeria24.png", "Azul");
	
	JCButton btnBuscar   = new JCButton("", "buscar.png", "Azul");
	JCButton btnFiltro   = new JCButton("", "Filter-List-icon16.png", "Azul");
	JCButton btnNuevo    = new JCButton("", "Nuevo.png", "Azul");
	JCButton btnEditar   = new JCButton("Editar", "editara.png", "Azul");
	JCButton btnDeshacer = new JCButton("Deshacer", "deshacer16.png", "Azul");
	JCButton btnGuardar  = new JCButton("Guardar", "guardar.png", "Azul");
	
	JLabel lblImgFactura = new JLabel(new ImageIcon("Imagen/Aplicar.png") );
	JLabel lblImgFoto = new JLabel(new ImageIcon("Imagen/Aplicar.png") );
	
	String rutaFactura = "";
	String rutaImagen = "";

	String movimiento = "";
	
	public Cat_Administracion_De_Activos() {
		this.setSize(590,435);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		this.setTitle("Administracion De Activos");
		
		btnBuscar.setToolTipText("Buscar");
		btnFiltro.setToolTipText("Filtro");
		btnNuevo.setToolTipText("Nuevo Activo");
		
		int x=15,y=20,ancho=80;
		
		
		
		panel.add(new JLabel("Folio:")).setBounds(x, y, ancho, 20);
		panel.add(txtFolio).setBounds(x+ancho, y, ancho, 20);
		
		panel.add(btnBuscar).setBounds(x+ancho*2+2, y, 30, 20);
		panel.add(btnFiltro).setBounds(x+ancho*2+34, y, 30, 20);
		panel.add(btnNuevo).setBounds(x+ancho*2+66, y, 30, 20);
		
		panel.add(new JLabel("Grupo:")).setBounds(x+(ancho*3)+40, y, ancho, 20);
		panel.add(spnGrupoEquipo).setBounds(x+(ancho*4), y, ancho, 20);
		
		panel.add(new JLabel("Grupo Mayor:")).setBounds(x+(ancho*4)+80, y, ancho, 20);
		panel.add(txtGrupoMayor).setBounds(x+(ancho*6)-5, y, ancho, 20);
		
		panel.add(new JLabel("Departamento Responsable:")).setBounds(x, y+=25, ancho*2+10, 20);
		panel.add(cmbDepartamentoResponsable).setBounds(x+ancho*2-10, y, ancho*2+10, 20);
		
		panel.add(new JLabel("Status:")).setBounds(x+(ancho*5)+30, y, ancho, 20);
		panel.add(cmbStatus).setBounds(x+(ancho*6)-5, y, ancho, 20);
		
		panel.add(new JLabel("Descripcion:")).setBounds(x, y+=25, ancho, 20);
		panel.add(txtDescripcion).setBounds(x+ancho, y, ancho*6-5, 20);
		
		panel.add(new JLabel("Establecimiento:")).setBounds(x, y+=25, ancho, 20);
		panel.add(cmbEstablecimiento).setBounds(x+ancho, y, ancho*2+25, 20);
		
		panel.add(new JLabel("Departamento:")).setBounds(x+(ancho*4)-30, y, ancho, 20);
		panel.add(cmbDepartamento).setBounds(x+(ancho*5)-30, y, ancho*2+25, 20);
		
		panel.add(new JLabel("Tipo:")).setBounds(x, y+=25, ancho, 20);
		panel.add(cmbTipo).setBounds(x+ancho, y, ancho*2+25, 20);
		
		panel.add(new JLabel("Caracteristicas:")).setBounds(x+(ancho*4)-30, y, ancho, 20);
		panel.add(scrollCaracteristicas).setBounds(x+(ancho*4)-30, y+15, ancho*3+25, 200);
		
		panel.add(new JLabel("Marca:")).setBounds(x, y+=25, ancho, 20);
		panel.add(cmbMarca).setBounds(x+ancho, y, ancho*2+25, 20);
		
		panel.add(new JLabel("Modelo:")).setBounds(x, y+=25, ancho, 20);
		panel.add(cmbModelo).setBounds(x+ancho, y, ancho*2+25, 20);
		
		panel.add(new JLabel("Serie:")).setBounds(x, y+=25, ancho, 20);
		panel.add(txtSerie).setBounds(x+ancho, y, ancho*2+25, 20);
		
		panel.add(new JLabel("Año Febricacion:")).setBounds(x, y+=25, ancho, 20);
		panel.add(cmbAnioFabricacion).setBounds(x+ancho, y, ancho*2+25, 20);
		
		panel.add(new JLabel("Fecha Compra:")).setBounds(x, y+=25, ancho, 20);
		panel.add(fchCompra).setBounds(x+ancho, y, ancho*2+25, 20);
		
		panel.add(new JLabel("Garantia:")).setBounds(x, y+=25, ancho, 20);
		panel.add(spnGarantia).setBounds(x+ancho, y, 50, 20);
		panel.add(cmbGarantia).setBounds(x+ancho+50, y, ancho*2-25, 20);
		
		panel.add(new JLabel("Vida Util:")).setBounds(x, y+=25, ancho, 20);
		panel.add(spnVidaUtil).setBounds(x+ancho, y, 50, 20);
		panel.add(cmbVidaUtil).setBounds(x+ancho+50, y, ancho*2-25, 20);
		
		panel.add(new JLabel("Costo:")).setBounds(x, y+=25, ancho, 20);
		panel.add(txtCosto).setBounds(x+ancho, y, ancho*2+25, 20);
		
		panel.add(new JLabel("Depresiacion Anual:")).setBounds(x, y+=25, ancho+30, 20);
		panel.add(txtDepreciacion).setBounds(x+ancho+30, y, ancho*2-5, 20);
		
		panel.add(btnFactura).setBounds(x+(ancho*4)-30, y, ancho+20, 20);
		panel.add(lblImgFactura).setBounds(x+(ancho*5)-15, y, 30, 20);
		panel.add(btnExaminar).setBounds(x+(ancho*5)+30, y, ancho+20, 20);
		panel.add(lblImgFoto).setBounds(x+(ancho*6)+10, y, ancho+20, 20);
		
		panel.add(btnEditar).setBounds(x, y+=25, ancho+50, 30);
		panel.add(btnDeshacer).setBounds(x+ancho*2+50, y, ancho+50, 30);
		panel.add(btnGuardar).setBounds(x+ancho*5+25, y, ancho+50, 30);
		
		txaCaracteristicas.setLineWrap(true); 
		txaCaracteristicas.setWrapStyleWord(true);
		
		cont.add(panel);
		
		txtGrupoMayor.setEditable(false);
		
		componentesDefault();
		ArchivosCargados();
		
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened( WindowEvent e ){
            	txtFolio.requestFocus();
//        		cmbEstablecimiento.showPopup();
         }
    });
		
		btnFactura.addActionListener(opCargarArchivo);
		btnExaminar.addActionListener(opCargarArchivo);
		lblImgFactura.addMouseListener(opQuitarArchivoFactura);
		lblImgFoto.addMouseListener(opQuitarArchivoFoto);
		
		btnNuevo.addActionListener(opNuevo);
		btnBuscar.addActionListener(opBuscar);
		txtFolio.addActionListener(opBuscar);
		btnFiltro.addActionListener(opFiltro);
		btnEditar.addActionListener(opEditar);
		btnDeshacer.addActionListener(opDeshacer);
		btnGuardar.addActionListener(opGuardar);
		cmbEstablecimiento.addActionListener(opEstablecimeinto);
		
		//  abre el filtro de busqueda de activos al presionar la tecla f2
	    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
	       KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), "foco");
	    
	    getRootPane().getActionMap().put("foco", new AbstractAction(){
	        @Override
	        public void actionPerformed(ActionEvent e)
	        {	        	btnFiltro.doClick();    	     }
	    });
		
	}
	
	ActionListener opEstablecimeinto = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			txtGrupoMayor.setText(cmbEstablecimiento.getSelectedIndex()==0?"0":(new BuscarSQL().maximoDeActivoPorEstablecimiento(cmbEstablecimiento.getSelectedItem().toString()))+"");
		}
	};
	
	ActionListener opNuevo = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			txtFolio.setText(new BuscarSQL().folio_Administracion_De_Equipos());
			CamposEditables(true);
			btnBuscar.setEnabled(false);
			btnFiltro.setEnabled(false);
			btnEditar.setEnabled(false);
			btnGuardar.setEnabled(true);
			txtDescripcion.requestFocus();
			movimiento = "GUARDAR";
		}
	};
	
	ActionListener opBuscar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(!txtFolio.getText().equals("")){
				buscar(Integer.valueOf(txtFolio.getText().trim()));
			}else{
				JOptionPane.showMessageDialog(null, "Favor De Ingresar El Folio Que Desee Buscar", "Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}
		}
	};
	
	public void buscar(int folio_activo){
			
			if(new Obj_Administracion_De_Activos().existeActivoBuscar(folio_activo)){
				
				Obj_Administracion_De_Activos activos = new Obj_Administracion_De_Activos().buscar(folio_activo);
				
				if(!activos.getSerie().equals("")){
					
					txtFolio.setText(activos.getFolio()+"");
					txtDescripcion.setText(activos.getDescripcion());
					cmbDepartamentoResponsable.setSelectedItem(activos.getDepartamento_responsable());
					cmbStatus.setSelectedItem(activos.getStatus());
					cmbEstablecimiento.setSelectedItem(activos.getEstablecimiento());
					cmbDepartamento.setSelectedItem(activos.getDepartamento());
					
					cmbTipo.setSelectedItem(activos.getTipo());
					cmbMarca.setSelectedItem(activos.getMarca());
					cmbModelo.setSelectedItem(activos.getModelo());
					txtSerie.setText(activos.getSerie());
					
					cmbAnioFabricacion.setSelectedItem(activos.getAnio_fabricacion());
					
					try {
						fchCompra.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(activos.getFecha_compra()));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					
					spnGrupoEquipo.setValue(activos.getGrupo_equipo());
					spnGarantia.setValue(activos.getGarantia());
					cmbGarantia.setSelectedItem(activos.getUnidad_garantia());
					spnVidaUtil.setValue(activos.getVida_util());
					cmbVidaUtil.setSelectedItem(activos.getUnidad_vida_util());
					
					txtCosto.setText(activos.getCosto()+"");
					txtDepreciacion.setText(activos.getDepreciacion()+"");
					txaCaracteristicas.setText(activos.getCaracteristicas());
					
					CamposEditables(false);
					btnBuscar.setEnabled(false);
					btnFiltro.setEnabled(false);
					btnNuevo.setEnabled(false);
					btnGuardar.setEnabled(false);
					btnEditar.setEnabled(true);
					txtFolio.setEditable(false);
					movimiento = "";
				
				}else{
					JOptionPane.showMessageDialog(null, "No Se Encortro El Folio Especificado", "Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
					return;
				}
				
			}else{
				JOptionPane.showMessageDialog(null, "El Folio Del Activo No Existe", "Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}
	}
	
	ActionListener opEditar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			movimiento = "ACTUALIZAR";
			System.out.println("Editar"+"  "+movimiento);
			CamposEditables(true);
			btnEditar.setEnabled(false);
			btnGuardar.setEnabled(true);
			
		}
	};
	
	ActionListener opFiltro = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
//			System.out.println("Filtro");
			CamposEditables(false);
			new Cat_Filtro_De_Administracion_De_Activos().setVisible(true);
		}
	};
	
	ActionListener opDeshacer = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			System.out.println("Deshacer");
			componentesDefault();
			movimiento = "";
		}
	};
	
	public void componentesDefault(){
		CamposEditables(false);
		btnBuscar.setEnabled(true);
		btnFiltro.setEnabled(true);
		btnNuevo.setEnabled(true);
		btnEditar.setEnabled(false);
		btnGuardar.setEnabled(false);
		
		txtFolio.setText("");
		txtDescripcion.setText("");
		cmbEstablecimiento.setSelectedIndex(0);
		cmbDepartamento.setSelectedIndex(0);
		cmbTipo.setSelectedIndex(0);
		cmbMarca.setSelectedIndex(0);
		cmbModelo.setSelectedIndex(0);
		txtSerie.setText("");
		cmbAnioFabricacion.setSelectedItem(anioActual()+"");
		fchCompra.setDate(cargar_fecha());
		spnGrupoEquipo.setValue(1);
		spnGarantia.setValue(1);
		cmbGarantia.setSelectedItem("AÑOS");
		spnVidaUtil.setValue(5);
		cmbVidaUtil.setSelectedItem("AÑOS");
		txtCosto.setText("");
		txtDepreciacion.setText("");
		txaCaracteristicas.setText("");
		
		txtGrupoMayor.setText("0");
		
		rutaFactura = "";
		rutaImagen = "";
		movimiento = "";
		ArchivosCargados();
		
		txtFolio.requestFocus();
	}
	
	ActionListener opGuardar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(validaCampos().equals("")){
			
				Obj_Administracion_De_Activos activos = new Obj_Administracion_De_Activos();//.buscar(Integer.valueOf(txtFolio.getText().trim()));
				
				if(!movimiento.equals("")){
						activos.setFolio(Integer.parseInt(txtFolio.getText().trim()));
						activos.setDescripcion(txtDescripcion.getText().toUpperCase().trim());
						activos.setDepartamento_responsable(cmbDepartamentoResponsable.getSelectedItem().toString().toUpperCase().trim());
						activos.setStatus(cmbStatus.getSelectedItem().toString().toUpperCase().trim());
						activos.setEstablecimiento(cmbEstablecimiento.getSelectedItem().toString().toUpperCase().trim());
						activos.setDepartamento(cmbDepartamento.getSelectedItem().toString().toUpperCase().trim());
						
						activos.setTipo(cmbTipo.getSelectedItem().toString().toUpperCase().trim());
						activos.setMarca(cmbMarca.getSelectedItem().toString().toUpperCase().trim());
						activos.setModelo(cmbModelo.getSelectedItem().toString().toUpperCase().trim());
						activos.setSerie(txtSerie.getText().toUpperCase().trim());
						activos.setAnio_fabricacion(Integer.valueOf(cmbAnioFabricacion.getSelectedItem().toString().trim()));
						
						activos.setFecha_compra(new SimpleDateFormat("dd/MM/yyyy").format(fchCompra.getDate()));
						activos.setGarantia(Integer.valueOf(spnGarantia.getValue().toString()));
						activos.setUnidad_garantia(cmbGarantia.getSelectedItem().toString().toUpperCase().trim());
						activos.setVida_util(Integer.valueOf(spnVidaUtil.getValue().toString()));
						activos.setUnidad_vida_util(cmbVidaUtil.getSelectedItem().toString().toUpperCase().trim());
						
						activos.setCosto(Double.valueOf(txtCosto.getText().trim()));
						activos.setDepreciacion(Double.valueOf(txtDepreciacion.getText().trim()));
						activos.setCaracteristicas(txaCaracteristicas.getText().toUpperCase().trim());
						
						activos.setGrupo_equipo(Integer.valueOf(spnGrupoEquipo.getValue().toString()));
						
						activos.setRuta_factura(rutaFactura.equals("")?"C:\\SCOI\\imagen\\SIN EVIDENCIA.jpg":rutaFactura);
						activos.setRuta_foto(rutaImagen.equals("")?"C:\\SCOI\\imagen\\SIN EVIDENCIA.jpg":rutaImagen);
						
						if(activos.guardarActualizarActivos(movimiento,rutaFactura,rutaImagen)){
							
							JOptionPane.showMessageDialog(null,"El registró se "+(movimiento.equals("GUARDAR")?"Guardo":"Actualizo")+" de forma segura","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//Exito.png"));
							componentesDefault();
							movimiento = "";
							return;
						}else{
							JOptionPane.showMessageDialog(null, "Problemas Al "+(movimiento.equals("GUARDAR")?"Guardar":"Actualizar")+" El Registro", "Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
							movimiento = "";
							return;
						}

				}else{
					JOptionPane.showMessageDialog(null, "No Se Pueden Realizar Cambios", "Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
					return;
				}
			}else{
				JOptionPane.showMessageDialog(null, "Los Suguientes Campos Son Requeridos: \n"+validaCampos(), "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}
		}
	};
	
	ActionListener opIdentificarEstab = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			cmbDepartamento.requestFocus();
			cmbDepartamento.showPopup();
		}
	};
	ActionListener opIdentificarDepto = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			cmbTipo.requestFocus();
			cmbTipo.showPopup();
		}
	};
	ActionListener opIdentificarTipo = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			cmbMarca.requestFocus();
			cmbMarca.showPopup();
		}
	};
	ActionListener opIdentificarMarca = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			cmbModelo.requestFocus();
			cmbModelo.showPopup();
		}
	};
	ActionListener opIdentificarModelo = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			txtSerie.requestFocus();
		}
	};
	ActionListener opIdentificarSerie = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			cmbAnioFabricacion.requestFocus();
			cmbAnioFabricacion.showPopup();
		}
	};
	ActionListener opIdentificarAnioFab = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			fchCompra.getCalendarButton().doClick();
		}
	};
	ActionListener opIdentificarCosto = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			txtDepreciacion.requestFocus();
		}
	};
	ActionListener opIdentificarDepreciacion = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			txaCaracteristicas.requestFocus();
		}
	};
	
	ActionListener opCargarArchivo = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			String btn = e.getActionCommand().trim();
			
			JFileChooser elegir = new JFileChooser();
        	int opcion = elegir.showOpenDialog(null);
        	
             //Si presionamos el boton ABRIR en pathArchivo obtenemos el path del archivo
             if(opcion == JFileChooser.APPROVE_OPTION){
                String pathArchivo = elegir.getSelectedFile().getPath(); //Obtiene path del archivo
                
                File mi_fichero = new File ( pathArchivo );
                double tamano_bytes = mi_fichero.length ( );
                double tamano_megas = tamano_bytes/(1024*1024);
                
                if(tamano_megas>3){
                	JOptionPane.showMessageDialog(null, "El Archivo Que Intenta Agregar Es Muy Grande,\nEl Archivo Debe Medir Maximo 3 MB", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
    				return;
                }else{
                	if(btn.equals("Factura")){
         				rutaFactura = pathArchivo;
         			}else{
         				if(pathArchivo.substring(pathArchivo.indexOf(".")+1, pathArchivo.length()).trim().toUpperCase().equals("JPG") || pathArchivo.substring(pathArchivo.indexOf(".")+1, pathArchivo.length()).trim().toUpperCase().equals("PNG")){
         					rutaImagen = pathArchivo;
         				}else{
         					JOptionPane.showMessageDialog(null, "Solo Se Pueden Cargar Imgagenes Con Extencion JPG y PNG.", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
            				return;
         				}
         			}
                	ArchivosCargados();
                }
             }
		}
	};
	
	MouseListener opQuitarArchivoFactura = new MouseListener() {
		public void mouseReleased(MouseEvent e) {
			rutaFactura="";
			ArchivosCargados();
		}
		public void mousePressed(MouseEvent e) {		}
		public void mouseExited(MouseEvent e) {		}
		public void mouseEntered(MouseEvent e) {		}
		public void mouseClicked(MouseEvent e) {		}
	};
	
	MouseListener opQuitarArchivoFoto = new MouseListener() {
		public void mouseReleased(MouseEvent e) {
			rutaImagen="";
			ArchivosCargados();
		}
		public void mousePressed(MouseEvent e) {		}
		public void mouseExited(MouseEvent e) {		}
		public void mouseEntered(MouseEvent e) {		}
		public void mouseClicked(MouseEvent e) {		}
	};
	
	public void ArchivosCargados(){
		lblImgFactura.setEnabled(rutaFactura.equals("")?false:true);
		lblImgFoto.setEnabled(rutaImagen.equals("")?false:true);
	}
	
	public Date cargar_fecha(){
		Date fh = null;
				  try {
					  fh = new SimpleDateFormat("dd/MM/yyyy").parse(new BuscarSQL().fecha(0));
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		return fh;
	};
	
	public String validaCampos(){
		String error = "";
		
		error += txtFolio.getText().equals("")?"Folio/n":"";
		error += txtDescripcion.getText().equals("")?"Descripcion/n":"";
		error += cmbDepartamentoResponsable.getSelectedIndex()==0?"Departamento Responsable/n":"";
		error += cmbEstablecimiento.getSelectedIndex()==0?"Establecimiento/n":"";
		error += cmbDepartamento.getSelectedIndex()==0?"Departamento/n":"";
		error += cmbTipo.getSelectedIndex()==0?"Tipo/n":"";
		error += cmbMarca.getSelectedIndex()==0?"Marca/n":"";
		error += cmbModelo.getSelectedIndex()==0?"Modelo/n":"";
		error += txtSerie.getText().equals("")?"Serie/n":"";
		error += cmbAnioFabricacion.getSelectedIndex()==0?"AnioFabricacion/n":"";
		error += fchCompra.getDate()==null?"Fecha De Compra/n":"";
		error += cmbGarantia.getSelectedIndex()==0?"Garantia/n":"";
		error += cmbVidaUtil.getSelectedIndex()==0?"VidaUtil/n":"";
		error += txtCosto.getText().equals("")?"Costo/n":"";
		error += txtDepreciacion.getText().equals("")?"Depreciacion/n":"";
		error += txaCaracteristicas.getText().equals("")?"Caracteristicas/n":"";
		
		return error;
	}
	
	public void CamposEditables(boolean block){
		
		txtFolio.setEditable(!block);
		txtDescripcion.setEditable(block);
		cmbDepartamentoResponsable.setEnabled(block);
		cmbStatus.setEnabled(block);
		cmbEstablecimiento.setEnabled(block);
		cmbDepartamento.setEnabled(block);
		cmbTipo.setEnabled(block);
		cmbMarca.setEnabled(block);
		cmbModelo.setEnabled(block);
		txtSerie.setEditable(block);
		cmbAnioFabricacion.setEnabled(block);
		fchCompra.setEnabled(block);
		spnGarantia.setEnabled(block);
		cmbGarantia.setEnabled(block);
		spnVidaUtil.setEnabled(block);
		cmbVidaUtil.setEnabled(block);
		txtCosto.setEditable(block);
		txtDepreciacion.setEditable(block);
		txaCaracteristicas.setEditable(block);
		
		spnGrupoEquipo.setEnabled(block);
		
		btnFactura.setEnabled(block);
		btnExaminar.setEnabled(block);
		
	}
	
	public static void main(String[] args) {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Administracion_De_Activos().setVisible(true);
		}catch(Exception e){	}
	}
	
	public class Cat_Filtro_De_Administracion_De_Activos extends JFrame{
		
		Container contFiltro = getContentPane();
		JLayeredPane panelFiltro = new JLayeredPane();
		
		JTextField txtFiltro = new Componentes().text(new JCTextField(), "Fitlro De Busqueda", 80, "String");
		
		DefaultTableModel modelo = new DefaultTableModel(new BuscarSQL().getFiltro_De_Administracion_De_Activos(),
    		new String[]{	"Folio", "Descripcion", "Tipo", "Marca", "Modelo", "Serie", "Grupo", "Depto Responsable", "Establecimiento", "Departamento"}){
    	
	        	@SuppressWarnings("rawtypes")
				public Class[] tipos(){
	        		Class[] type = new Class[this.getColumnCount()];
	        		
	        			for(int i = 0 ; i<this.getColumnCount(); i++){
	        				type[i] = java.lang.Object.class;
	        			}
	        		return type;
	        	}
                @SuppressWarnings("rawtypes")
                Class[] types = tipos();
                    
                @SuppressWarnings({ "rawtypes", "unchecked" })
                public Class getColumnClass(int columnIndex) {
                        return types[columnIndex];
                }
                
                public boolean isCellEditable(int fila, int columna){
                         return false;
                 }
        };
        
        JTable tabla = new JTable(modelo);
        JScrollPane Scroll = new JScrollPane(tabla,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		public Cat_Filtro_De_Administracion_De_Activos(){
	        
	        int x = 10, y = 15 , ancho = 80;
			
	        panelFiltro.add(txtFiltro).setBounds(x, y, ancho*13-25, 20);
	        panelFiltro.add(Scroll).setBounds(x, y+=20, ancho*13-25, 520);
	        
	        llamarRender();
	        txtFiltro.addKeyListener(opFiltro);
	        agregar(tabla);
	        
	        this.contFiltro.add(panelFiltro);
	        
			this.setSize(1040,590);
	        this.setResizable(false);
	        this.setLocationRelativeTo(null);
		}
		
        public void llamarRender(){
                this.tabla.getTableHeader().setReorderingAllowed(false) ;
                this.tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                
    			tabla.getColumnModel().getColumn(0).setMinWidth(30);
    			tabla.getColumnModel().getColumn(1).setMinWidth(220);
    			tabla.getColumnModel().getColumn(2).setMinWidth(160);
    			tabla.getColumnModel().getColumn(3).setMinWidth(160);
    			tabla.getColumnModel().getColumn(4).setMinWidth(160);
    			tabla.getColumnModel().getColumn(5).setMinWidth(120);
    			tabla.getColumnModel().getColumn(6).setMinWidth(120);
    			tabla.getColumnModel().getColumn(7).setMinWidth(50);
    			tabla.getColumnModel().getColumn(8).setMinWidth(120);
    			tabla.getColumnModel().getColumn(9).setMinWidth(120);
    			
    			tabla.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12)); 
    			tabla.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
    			tabla.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
    			tabla.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
    			tabla.getColumnModel().getColumn(4).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
    			tabla.getColumnModel().getColumn(5).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
    			tabla.getColumnModel().getColumn(6).setCellRenderer(new tablaRenderer("texto","centro","Arial","normal",12));
    			tabla.getColumnModel().getColumn(7).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
    			tabla.getColumnModel().getColumn(8).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
    			tabla.getColumnModel().getColumn(9).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
        }
        
        KeyListener opFiltro = new KeyListener() {
			public void keyTyped(KeyEvent e) {			}
			
			public void keyReleased(KeyEvent e) {
				int[] columnas = {0,1,2,3,4,5,6,7,8,9};
				new Obj_Filtro_Dinamico_Plus(tabla, txtFiltro.getText().toString().trim().toUpperCase(), columnas);
			}
			public void keyPressed(KeyEvent e) {			}
		};
		
		private void agregar(final JTable tbl) {
	        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
		        public void mouseClicked(MouseEvent e) {
		        	if(e.getClickCount() == 2){
		    			int folio =  Integer.valueOf(tabla.getValueAt(tabla.getSelectedRow(), 0).toString().trim());
		    			txtFolio.setText(folio+"");	
		    			dispose();
		    			btnBuscar.doClick();
		        	}
		        }
	        });
	    }
		
	}

}
