package Cat_Evaluaciones;

import java.awt.Color;
import java.awt.Container;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Connexion;
import Obj_Evaluaciones.Obj_Descripcion_De_Puestos_y_Responsabilidades;
import Obj_Lista_de_Raya.Obj_Departamento;
import Obj_Lista_de_Raya.Obj_Empleados;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_tabla;
import Obj_Renders.tablaRenderer;
import Obj_Tratamiento_De_Imagenes.Obj_Formato_De_Imagen;
import Obj_Xml.CrearXmlString;

@SuppressWarnings("serial")
public class Cat_Descripcion_De_Puestos_y_Responsabilidades extends JFrame{

	Obj_Formato_De_Imagen formatoDeImagen = new Obj_Formato_De_Imagen();
	Container cont = getContentPane();
	JTabbedPane pestanas = new JTabbedPane();
	
	JLayeredPane panelBase = new JLayeredPane();
	
	JLayeredPane panel1 = new JLayeredPane();
	JLayeredPane panel2 = new JLayeredPane();
	JLayeredPane panel3 = new JLayeredPane();
	
	JToolBar menu_toolbar  = new JToolBar();
	
//	panel base (inicia)--------------------------------------------------------------------------------------------------------------------------------------
	JCButton btnNuevo = new JCButton("Nuevo", "Nuevo.png", "Azul");
	JCButton btnBuscarPuesto = new JCButton("Buscar", "buscar.png", "Azul");
	JCButton btnEditar = new JCButton("Editar", "editara.png", "Azul");
	JCButton btnDeshacer = new JCButton("Deshacer", "deshacer16.png", "Azul");
	JCButton btnGuardar = new JCButton("Guardar", "guardar.png", "Azul");
	
//	panel base (fin)-----------------------------------------------------------------------------------------------------------------------------------------
//pagina 1 (inicia)------------------------------------------------------------------------------------------------------------------------------------------
	JLabel lblFolio = new JLabel("Folio:");
	
	JLabel lblPuesto = new JLabel("Nombre Del Puesto:");
	JLabel lblUnidadDeNegocio = new JLabel("Unidad De Negocio:");
	JLabel lblEstablecimiento = new JLabel("Establecimiento:");
	JLabel lblDepartamento = new JLabel("Departamento:");
	JLabel lblReportaA = new JLabel("Reporta A:");
	
	JLabel lblEdadIn = new JLabel("Rango De Edad De:");
	JLabel lblEdadFin = new JLabel("A:");
	
	JLabel lblSexo = new JLabel("Sexo:");
	JLabel lblEstadoCivil = new JLabel("Estado Civil:");
	
	JLabel lblObjetivoDelPuesto = new JLabel("Objetivo Del Puesto:");
	JLabel lblObjetivoDelPuestoNota = new JLabel("(¿Cuál es la misión o razón por la que existe el puesto?)");
	
	JLabel lblResponsabilidadesDelPuesto = new JLabel("Responsabilidades Del Puesto:");
	JLabel lblResponsabilidadesDelPuestoNota = new JLabel("(Especifique qué hace y para que lo hace)");
	
	JLabel lblNivelDeEduacacionRequerido = new JLabel("Nivel De Educacion Requerido:");
	JLabel lblNivelDeEduacacionRequeridoNota = new JLabel("Seleccione el nivel mínimo de educación formal que es requerido para desempeñar este puesto satisfactoriamente.");

	JLabel lblListaDeRequisitos = new JLabel("Enliste ejemplos específicos de Título(s), área(s), de estudio, y/o licencias denotando (R) si es Requisito o (P) si es Preferible.");
	
//	------------------------------------------------------------------------------------------------------------------------------------------------------
	JTextField txtFolio = new Componentes().text(new JCTextField(), "Folio DPR", 10, "Int");
	JTextField txtFolioPuesto = new Componentes().text(new JCTextField(), "Folio", 10, "Int");
	JTextField txtPuesto = new Componentes().text(new JCTextField(), "Nombre De Puesto", 120, "String");
	JCButton btnPuesto = new JCButton("", "buscar.png", "Azul");
	
	
	String[] UN = new Obj_Descripcion_De_Puestos_y_Responsabilidades().Combo_Unidad_De_Negocio();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbUnidadDeNegocio = new JComboBox(UN);
	
	String[] estab = new Obj_Establecimiento().Combo_Establecimiento();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbEstablecimiento = new JComboBox(estab);
	
	String[] depto = new Obj_Departamento().Combo_Departamento();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbDepartamento = new JComboBox(depto);
	
	JTextField txtFolioReportaA = new Componentes().text(new JCTextField(), "Folio", 10, "Int");
	JTextField txtReporteA = new Componentes().text(new JCTextField(), "Reporta Al Puesto", 120, "String");
	JCButton btnReporteA = new JCButton("", "buscar.png", "Azul");
	
	JTextField txtEdadIn = new Componentes().text(new JCTextField(), "", 2, "Int");
	JTextField txtEdadFin = new Componentes().text(new JCTextField(), "", 2, "Int");
	
	String sexo[] = {"SELECCIONE UN GENERO","MASCULINO","FEMENINO"};
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbSexo = new JComboBox(sexo);
	
	String estado_civil[] = new Obj_Empleados().Combo_Estado_Civil();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbEstadoCivil = new JComboBox(estado_civil);
	
	JTextArea txaObjectivoDelPuesto = new Componentes().textArea(new JTextArea(), "Objectivo Del Puesto", 200);
	JScrollPane scrollObjectivoDelPuesto = new JScrollPane(txaObjectivoDelPuesto);
	
	JTextField txtResponsabilidad = new Componentes().text(new JCTextField(), "Ingresar Responsabilidad", 200, "String");
	JCButton btnAgregarResponsabilidad = new JCButton("Agregar", "", "Azul");
	JCButton btnSubir = new JCButton("", "Up.png", "Azul");
	JCButton btnBajar = new JCButton("", "Down.png", "Azul");
	
	//TABLA MOVIMIENTOS BANCARIOS
		DefaultTableModel modeloResponsabilidadesDelPuesto = new DefaultTableModel(null,new String[]{"Orden", "Descripcion","*" }
				){
		     @SuppressWarnings("rawtypes")
			Class[] types = new Class[]{
		    	java.lang.String.class,
		    	java.lang.String.class,
		    	java.lang.Boolean.class
		    	                       };
		     @SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int columnIndex) {
	             return types[columnIndex];
	         }
	         public boolean isCellEditable(int fila, int columna){
	        	 switch(columna){
	        	 	case 0 : return false; 
	        	 	case 1 : return false; 
	        	 	case 2 : return true;
	        	 	} 				
	 			return false;
	 		}
		};
		JTable tablaResponsabilidadesDelPuesto = new JTable(modeloResponsabilidadesDelPuesto);
	    JScrollPane scrollResponsabilidadesDelPuesto = new JScrollPane(tablaResponsabilidadesDelPuesto);
	    JCButton btnQuitarfila= new JCButton("Eliminar Fila","boton-rojo-menos-icono-5393-16.png","Azul");
	    
		String escolaridad[] = new Obj_Empleados().Combo_Escolaridad();
		@SuppressWarnings({ "rawtypes", "unchecked" })
		JComboBox cmbEscolaridad = new JComboBox(escolaridad);
		
		JTextArea txaRequisitos = new Componentes().textArea(new JTextArea(), "Objectivo Del Puesto", 200);
		JScrollPane scrollRequisitos = new JScrollPane(txaRequisitos);
//pagina 1 (fin)------------------------------------------------------------------------------------------------------------------------------------------
//pagina 2 (inicia)------------------------------------------------------------------------------------------------------------------------------------------
		
		JLabel lblHabilidadesYConocimientos = new JLabel("Entrenamiento, habilidades, conocimiento y/o esperiencia:");
		JLabel lblHabilidadesYConocimientosNota = new JLabel("Enlista ejemplos específicos denotando (E) Exelente, (MB) Muy Bueno, (B) Bueno.");
		
		JLabel lblCursosHabilidades1 = new JLabel("Cursos habilidades o entto");
		JLabel lblCursosHabilidades2 = new JLabel("especifico en el campo de:");
		
		JLabel lblExperienciaGeneral = new JLabel("Experiencia general en el campo de:");
		JLabel lblExperienciaEspecifica = new JLabel("Experiencia especifica en el puesto de:");

		JLabel lblFacultamiento = new JLabel("Fecultamiento:");
		JLabel lblFacultamientoNota = new JLabel("(Indique cuantos empleados dependen directa e indirectamente de este puesto)");
		
		JLabel lblDirectos = new JLabel("Directos:");
		JLabel lblIndirectos = new JLabel("Indirectos:");
		
		JLabel lblInteraccionesDelPuesto = new JLabel("Indique las principales interacciones del puesto y seleccione su tipo de relación:");
		JLabel lblExternas = new JLabel("Externas:");
		JLabel lblInternas = new JLabel("Internas:");
		
		JLabel lblCondicionesDeTrabajo = new JLabel("Condiciones de trabajo:");
		JLabel lblCondicionesDeTrabajoNota = new JLabel("Describa las condiciones de trabajo requeridas para ejecutar las tareas principales del puesto.");
		
		JLabel lblAmbienteDeTrabajo = new JLabel("Ambiente de trabajo:");
		JLabel lblEsfuezoFisico = new JLabel("Esfuezo físco:");
		JLabel lblViajes = new JLabel("Viajes nacionales/internacionales:");
		JLabel lblHerramientasYEquipos = new JLabel("Indique las necesidades de herramientas y equipos:");
//		------------------------------------------------------------------------------------------------------------------------------------------------------
		JTextArea txaCursosHabilidades = new Componentes().textArea(new JTextArea(), "", 200);
		JScrollPane scrollCursosHabilidades = new JScrollPane(txaCursosHabilidades);
		
		JTextArea txaExperienciaGeneral = new Componentes().textArea(new JTextArea(), "", 200);
		JScrollPane scrollExperienciaGeneral = new JScrollPane(txaExperienciaGeneral);
		
		JTextArea txaExperienciaEspecifica = new Componentes().textArea(new JTextArea(), "", 200);
		JScrollPane scrollExperienciaEspecifica = new JScrollPane(txaExperienciaEspecifica);
		
		JTextField txtDirectos = new Componentes().text(new JCTextField(), "", 10, "Int");
		JTextField txtIndirectos = new Componentes().text(new JCTextField(), "", 10, "Int");
		
		JTextField txtPorcentajeExterno = new Componentes().text(new JCTextField(), "", 3, "Int");
		JTextField txtInteraccionesEsternasPuesto = new Componentes().text(new JCTextField(), "Seguridad, Logística, Desarrollo Humano, Cedis.", 120, "String");
		
		JTextField txtPorcentajeInterno = new Componentes().text(new JCTextField(), "", 3, "Int");
		JTextField txtInteraccionesInternasPuesto = new Componentes().text(new JCTextField(), "Clientes, Personal a Cargo, Encargado de Tienda.", 120, "String");
		
		JTextField txtAmbienteDeTrabajo = new Componentes().text(new JCTextField(), "Tienda", 120, "String");
		JTextField txtEsfuerzoFisico = new Componentes().text(new JCTextField(), "Moderado", 120, "String");
		
		ButtonGroup GrViajes = new ButtonGroup();
		JRadioButton rbSi = new JRadioButton("Si");
		JRadioButton rbNo = new JRadioButton("No");
		
		JCheckBox chbLaptop = new JCheckBox("Laptop");
		JCheckBox chbPc = new JCheckBox("PC");
		JCheckBox chbCelular = new JCheckBox("Celular");
		JCheckBox chbExtension = new JCheckBox("Extensión");
		JCheckBox chbAutoPropio = new JCheckBox("Auto propio");
		JCheckBox chbCelLargaDistancia = new JCheckBox("Larga distancia/Internacional");
		JCheckBox chbAutoDeCompania = new JCheckBox("Auto de la comañía");
		JCheckBox chbLicenciaConducir = new JCheckBox("Licencia de conducir");
		JCheckBox chbOtro = new JCheckBox("Otro:");
		JTextField txtOtro = new Componentes().text(new JCTextField(), "", 120, "String");
		
//pagina 2 (fin)------------------------------------------------------------------------------------------------------------------------------------------
//pagina 3 (inicia)---------------------------------------------------------------------------------------------------------------------------------------	    
		JCButton btnExaminar = new JCButton("Organigrama", "buscar.png", "Azul");
		JLabel lblRutaOrganigrama = new JLabel("");
		
//		String rutaImagen = "";
//		File fi = null;
		byte[] imagB = null;
		JLabel lblImage = new JLabel("");
		JScrollPane scrollOrganigrama = new JScrollPane(lblImage);
//pagina 3 (fin)------------------------------------------------------------------------------------------------------------------------------------------
	Border blackline;
	
	String movimiento = "";
	
//	boolean mostrarPuestosDisponibles = false;
	public Cat_Descripcion_De_Puestos_y_Responsabilidades() {
		
		this.panelBase.setBorder(BorderFactory.createTitledBorder(blackline, "Ingresar Datos DPR"));
		
		this.menu_toolbar.add(btnNuevo);
		this.menu_toolbar.addSeparator(  );
	    this.menu_toolbar.addSeparator(  );
	    this.menu_toolbar.add(btnBuscarPuesto);
	    this.menu_toolbar.addSeparator(  );
	    this.menu_toolbar.addSeparator(  );
	    this.menu_toolbar.add(btnEditar);
	    this.menu_toolbar.addSeparator(  );
	    this.menu_toolbar.addSeparator(  );
		this.menu_toolbar.add(btnDeshacer);
		this.menu_toolbar.addSeparator(  );
		this.menu_toolbar.addSeparator(  );
		this.menu_toolbar.add(btnGuardar);
		
		menu_toolbar.setEnabled(false);
		
		pestanas.addTab("Pagina 1", panel1);
		pestanas.addTab("Pagina 2", panel2);
		pestanas.addTab("Pagina 3", panel3);
		
		panelBase.add(menu_toolbar).setBounds(5, 15, 940, 20);
		panelBase.add(pestanas).setBounds(5, 35, 950, 600);
		
		this.setSize(965,670);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
//		efectos de bordes
		blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/usuario-grupo-icono-5183-64.png"));
		this.setTitle("Descripción De Puestos Y Responsabilidades");
		
		lblImage.setHorizontalAlignment((int) CENTER_ALIGNMENT);
		
		pagina1();
		pagina2();
		pagina3();
		
		camposActivosDefault();
		
		txtOtro.setEnabled(!chbOtro.isEnabled()?false:(chbOtro.isSelected()?true:false));
		
		init_tabla(tablaResponsabilidadesDelPuesto);
		
		btnNuevo.addActionListener(opNuevo);
		btnEditar.addActionListener(opEdit);
		btnDeshacer.addActionListener(opDeshacer);
		
		btnBuscarPuesto.addActionListener(opFiltroPuestos);
		btnPuesto.addActionListener(opPuesto);
		btnReporteA.addActionListener(opFiltroReporteA);
		
		txtResponsabilidad.addActionListener(opAgregarResponsabilidadesDelPuesto);
		btnAgregarResponsabilidad.addActionListener(opAgregarResponsabilidadesDelPuesto);
		
		btnSubir.addActionListener(opSubirResponsabilidadesDelPuesto);
		btnBajar.addActionListener(opBajarResponsabilidadesDelPuesto);
		btnQuitarfila.addActionListener(opQuitar);
		
		agregar(tablaResponsabilidadesDelPuesto);
		
		btnExaminar.addActionListener(opCargarArchivo);
		btnGuardar.addActionListener(opGuardar);
		chbOtro.addActionListener(opChbOtro);
		
		cont.add(panelBase);
	}
	
	ActionListener opEdit = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			camposActivos(true);
		}
	};
	
	ActionListener opChbOtro = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			txtOtro.setEnabled(!chbOtro.isEnabled()?false:(chbOtro.isSelected()?true:false));
			txtOtro.setText(!chbOtro.isEnabled()?"":(chbOtro.isSelected()?"Lo que tenía":""));
		}
	};
	
	ActionListener opNuevo = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			movimiento = "GUARDAR";
			txtFolio.setText(new BuscarSQL().buscar_folio_consecutivo_por_folio_de_transaccion(89));
			camposActivos(true);
			btnBuscarPuesto.setEnabled(false);
			btnEditar.setEnabled(false);
		}
	};
	
	ActionListener opDeshacer = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			limpiarCampos();
			camposActivos(false);
			btnBuscarPuesto.setEnabled(true);
		}
	};
	
	ActionListener opGuardar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String validarCampos = 	validaCampos();
			
			if(validarCampos.equals("")){
				if(txtFolioPuesto.getText().trim().equals(txtFolioReportaA.getText().trim())){
					limpiarCampos();
					camposActivos(false);
					btnBuscarPuesto.setEnabled(true);
					JOptionPane.showMessageDialog(null, "Los Campos De Puesto Y Reporta A, No Pueden Ser Iguales\n(Los Puestos No Pueden Reportar A Sí Mismos)", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
					return;
				}else{
					guardado();
				}
			}else{
				JOptionPane.showMessageDialog(null, "Los Siguietes Campos Son Requeridos:\n"+validarCampos, "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}
		}
	};
	
	public void guardado(){
		Obj_Descripcion_De_Puestos_y_Responsabilidades dpr = new Obj_Descripcion_De_Puestos_y_Responsabilidades();
		
		dpr.setFolio(Integer.valueOf(txtFolio.getText().trim()));
		dpr.setFolioPuesto(Integer.valueOf(txtFolioPuesto.getText().trim()));
		dpr.setPuesto(txtPuesto.getText().toUpperCase().trim());
		dpr.setUnidadNegocio(cmbUnidadDeNegocio.getSelectedItem().toString().trim().toUpperCase());
		dpr.setEstablecimiento(cmbEstablecimiento.getSelectedItem().toString().trim().toUpperCase());
		dpr.setDepartamento(cmbDepartamento.getSelectedItem().toString().trim().toUpperCase());
		dpr.setFolioReportaA(Integer.valueOf(txtFolioReportaA.getText().trim()));
		dpr.setReportaA(txtReporteA.getText().trim().toUpperCase());
		dpr.setEdadIn(Integer.valueOf(txtEdadIn.getText().trim()));
		dpr.setEdadFin(Integer.valueOf(txtEdadFin.getText().trim()));
		dpr.setSexo(cmbSexo.getSelectedItem().toString().trim().toUpperCase());
		dpr.setEstadoCivil(cmbEstadoCivil.getSelectedItem().toString().trim().toUpperCase());
		dpr.setObjetivoPuesto(txaObjectivoDelPuesto.getText().trim().toUpperCase());
		
		int[] ignorarColumnas = {2};
		dpr.setXmlResponsabilidadesPuesto(new CrearXmlString().CadenaXML(tablaResponsabilidadesDelPuesto, ignorarColumnas));
		
		dpr.setNivelEstudios(cmbEscolaridad.getSelectedItem().toString().trim().toUpperCase());
		dpr.setListaDeEspecificaciones(txaRequisitos.getText().trim().toUpperCase());
		dpr.setCursosHabilidades(txaCursosHabilidades.getText().trim().toUpperCase());
		dpr.setEsperienciaGeneral(txaExperienciaGeneral.getText().trim().toUpperCase());
		dpr.setEsperienciaEspecifica(txaExperienciaEspecifica.getText().trim().toUpperCase());
		dpr.setFacultamientosDirectos(Integer.valueOf(txtDirectos.getText().trim()));
		dpr.setFacultamientosIndirectos(Integer.valueOf(txtIndirectos.getText().trim()));
		
//		dpr.setOrganigrama(fi);
		dpr.setOrganigramaB(imagB);
//		dpr.setOrganigramaB(base64);
		
		dpr.setInteracionDelPuestoExternas(Integer.valueOf(txtPorcentajeExterno.getText().trim()));
		dpr.setRelacionDelPuestoExternas(txtInteraccionesEsternasPuesto.getText().trim().toUpperCase());
		dpr.setInteracionDelPuestoInternas(Integer.valueOf(txtPorcentajeInterno.getText().trim()));
		dpr.setRelacionDelPuestoInternas(txtInteraccionesInternasPuesto.getText().trim().toUpperCase());
		
		dpr.setAmbienteDeTrabajo(txtAmbienteDeTrabajo.getText().trim().toUpperCase());
		dpr.setEsfuerzoFisico(txtEsfuerzoFisico.getText().trim().toUpperCase());
		
		dpr.setViaje(rbSi.isSelected()?true:false);
		
		dpr.setLaptop(chbLaptop.isSelected());
		dpr.setPc(chbPc.isSelected());
		dpr.setCelular(chbCelular.isSelected());
		dpr.setExtencion(chbExtension.isSelected());
		dpr.setAutoPropio(chbAutoPropio.isSelected());
		dpr.setAutoEmpresa(chbAutoDeCompania.isSelected());
		dpr.setLicencia(chbLicenciaConducir.isSelected());
		dpr.setLargaDistancia(chbCelLargaDistancia.isSelected());
		dpr.setOtro(chbOtro.isSelected());
		
		dpr.setNotaOtro(txtOtro.getText().trim().toUpperCase());
		
		if(dpr.guardar(movimiento)){
			limpiarCampos();
			camposActivos(false);
			btnBuscarPuesto.setEnabled(true);
			JOptionPane.showMessageDialog(null, "Registro Guardado Correctamente","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
		}else{
			JOptionPane.showMessageDialog(null,"Error al Intentar Guardar","Aviso",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public String validaCampos(){
		String lista = "";
		
		lista+= txtFolio.getText().equals("")?"- Folio\n":"";
		lista+= txtFolioPuesto.getText().equals("")?"- Puesto\n":"";
		lista+= cmbUnidadDeNegocio.getSelectedIndex()==0?"- Unidad De Negocio\n":"";
		lista+= cmbEstablecimiento.getSelectedIndex()==0?"- Establecimiento\n":"";
		lista+= cmbDepartamento.getSelectedIndex()==0?"- Departamento\n":"";
		
		lista+= txtFolioReportaA.getText().equals("")?"- Reporta A\n":"";
		lista+= txtEdadIn.getText().equals("")?"- Edad In\n":"";
		lista+= txtEdadFin.getText().equals("")?"- Edad Fin\n":"";
		
		lista+= cmbSexo.getSelectedIndex()==0?"- Sexo\n":"";
		lista+= cmbEstadoCivil.getSelectedIndex()==0?"- Estado Civil\n":"";
		
		lista+= txaObjectivoDelPuesto.getText().equals("")?"- Objetivo Del Puesto\n":"";
		
		lista+= modeloResponsabilidadesDelPuesto.getRowCount()==0?"- Se Requiere Lista De Responsabilidades\n":"";
		
		lista+= cmbEscolaridad.getSelectedIndex()==0?"- Escolaridad\n":"";
		
		lista+= txaRequisitos.getText().equals("")?"- Requisitos\n":"";
		lista+= txaCursosHabilidades.getText().equals("")?"- Cursos y Habilidades\n":"";
		lista+= txaExperienciaGeneral.getText().equals("")?"- Experiencia General\n":"";
		lista+= txaExperienciaEspecifica.getText().equals("")?"- Experiencia Especifica\n":"";
		
		lista+= txtDirectos.getText().equals("")?"- Directos\n":"";
		lista+= txtIndirectos.getText().equals("")?"- Indirectos\n":"";
		
//		lista+= rutaImagen.equals("")?"- Se Requiere Seleccionar Organigrama\n":"";
//		lista+= !fi.exists()?"- Se Requiere Seleccionar Organigrama\n":"";
		
		lista+= txtEdadIn.getText().equals("")?"- Edad In\n":"";
		lista+= txtEdadFin.getText().equals("")?"- Edad Fin\n":"";
		
		lista+= txtPorcentajeExterno.getText().equals("")?"- Porcentaje Externo\n":"";
		lista+= txtInteraccionesEsternasPuesto.getText().equals("")?"- Interacciones Externas Puesto\n":"";
		lista+= txtPorcentajeInterno.getText().equals("")?"- Porcentaje Interno\n":"";
		lista+= txtInteraccionesInternasPuesto.getText().equals("")?"- Interacciones Internas Puesto\n":"";
		lista+= txtAmbienteDeTrabajo.getText().equals("")?"- Ambiente De Trabajo\n":"";
		lista+= txtEsfuerzoFisico.getText().equals("")?"- Esfuerzo Fisico\n":"";
		
		lista+= rbSi.isSelected() || rbNo.isSelected()?"":"- Viajes";
		
		return lista;
	}
	
	
	public void limpiarCampos(){
		
		movimiento = "";
		
		txtFolio.setText("");
		txtFolioPuesto.setText("");
		txtPuesto.setText("");
		
		cmbUnidadDeNegocio.setSelectedIndex(0);
		
		cmbEstablecimiento.setSelectedIndex(0);
		cmbDepartamento.setSelectedIndex(0);
		
		txtFolioReportaA.setText("");
		txtReporteA.setText("");
		
		txtEdadIn.setText("");
		txtEdadFin.setText("");
		
		cmbSexo.setSelectedIndex(0);
		cmbEstadoCivil.setSelectedIndex(0);
		txaObjectivoDelPuesto.setText("");
		
		modeloResponsabilidadesDelPuesto.setRowCount(0);
		
		cmbEscolaridad.setSelectedIndex(0);
		txaRequisitos.setText("");
		txaCursosHabilidades.setText("");
		txaExperienciaGeneral.setText("");
		txaExperienciaEspecifica.setText("");
		
		txtDirectos.setText("");
		txtIndirectos.setText("");
		
		txtPorcentajeExterno.setText("");
		txtInteraccionesEsternasPuesto.setText("");
		txtPorcentajeInterno.setText("");
		txtInteraccionesInternasPuesto.setText("");
		
		txtAmbienteDeTrabajo.setText("");
		txtEsfuerzoFisico.setText("");
		
		GrViajes.remove(rbSi);
		GrViajes.remove(rbNo);
		
		rbSi.setSelected(false);
		rbNo.setSelected(false);
		
		GrViajes.add(rbSi);
		GrViajes.add(rbNo);
		
		chbLaptop.setSelected(false);
		chbPc.setSelected(false);
		chbCelular.setSelected(false);
		chbExtension.setSelected(false);
		chbAutoPropio.setSelected(false);
		chbAutoDeCompania.setSelected(false);
		chbLicenciaConducir.setSelected(false);
		chbCelLargaDistancia.setSelected(false);
		chbOtro.setSelected(false);
		
		txtOtro.setText("");
		
		lblRutaOrganigrama.setText("");
		lblImage.setIcon(null);
		imagB = null;
	}
	
	public void camposActivosDefault(){
		
		btnDeshacer.setEnabled(false);
		btnEditar.setEnabled(false);
		btnGuardar.setEnabled(false);
		
		txtFolio.setEditable(false);
		txtFolioPuesto.setEditable(false);
		txtPuesto.setEditable(false);
		
		
		cmbUnidadDeNegocio.setEnabled(false);
		
		cmbEstablecimiento.setEnabled(false);
		cmbDepartamento.setEnabled(false);
		
		txtFolioReportaA.setEditable(false);
		txtReporteA.setEditable(false);
		btnPuesto.setEnabled(false);
		btnReporteA.setEnabled(false);
		
		txtEdadIn.setEditable(false);
		txtEdadFin.setEditable(false);
		
		cmbSexo.setEnabled(false);
		cmbEstadoCivil.setEnabled(false);
		txaObjectivoDelPuesto.setEditable(false);
		
		tablaResponsabilidadesDelPuesto.setEnabled(false);
		btnAgregarResponsabilidad.setEnabled(false);
		btnBajar.setEnabled(false);
		btnSubir.setEnabled(false);
		btnQuitarfila.setEnabled(false);
		
		cmbEscolaridad.setEnabled(false);
		txaRequisitos.setEditable(false);
		txaCursosHabilidades.setEditable(false);
		txaExperienciaGeneral.setEditable(false);
		txaExperienciaEspecifica.setEditable(false);
		
		txtDirectos.setEditable(false);
		txtIndirectos.setEditable(false);
		
		btnExaminar.setEnabled(false);
		
		txtPorcentajeExterno.setEditable(false);
		txtInteraccionesEsternasPuesto.setEditable(false);
		txtPorcentajeInterno.setEditable(false);
		txtInteraccionesInternasPuesto.setEditable(false);
		
		txtAmbienteDeTrabajo.setEditable(false);
		txtEsfuerzoFisico.setEditable(false);
		
		rbSi.setEnabled(false);
		rbNo.setEnabled(false);
		
		chbLaptop.setEnabled(false);
		chbPc.setEnabled(false);
		chbCelular.setEnabled(false);
		chbExtension.setEnabled(false);
		chbAutoPropio.setEnabled(false);
		chbAutoDeCompania.setEnabled(false);
		chbLicenciaConducir.setEnabled(false);
		chbCelLargaDistancia.setEnabled(false);
		chbOtro.setEnabled(false);
		
		txtOtro.setEditable(false);
		
		
	}
	
	public void camposActivos(boolean status){
		btnNuevo.setEnabled(!status);
		btnEditar.setEnabled(status);
		btnDeshacer.setEnabled(status);
//		btnEditar.setEnabled(!status);
		btnGuardar.setEnabled(status);
		
		cmbUnidadDeNegocio.setEnabled(status);
		
		cmbEstablecimiento.setEnabled(status);
		cmbDepartamento.setEnabled(status);
		
		btnPuesto.setEnabled(status);
		btnReporteA.setEnabled(status);
		
		txtEdadIn.setEditable(status);
		txtEdadFin.setEditable(status);
		
		cmbSexo.setEnabled(status);
		cmbEstadoCivil.setEnabled(status);
		txaObjectivoDelPuesto.setEditable(status);
		
		tablaResponsabilidadesDelPuesto.setEnabled(status);
		btnAgregarResponsabilidad.setEnabled(status);
		btnBajar.setEnabled(status);
		btnSubir.setEnabled(status);
		btnQuitarfila.setEnabled(status);
		
		cmbEscolaridad.setEnabled(status);
		txaRequisitos.setEditable(status);
		txaCursosHabilidades.setEditable(status);
		txaExperienciaGeneral.setEditable(status);
		txaExperienciaEspecifica.setEditable(status);
		
		txtDirectos.setEditable(status);
		txtIndirectos.setEditable(status);
		
		btnExaminar.setEnabled(status);
		
		txtPorcentajeExterno.setEditable(status);
		txtInteraccionesEsternasPuesto.setEditable(status);
		txtPorcentajeInterno.setEditable(status);
		txtInteraccionesInternasPuesto.setEditable(status);
		
		txtAmbienteDeTrabajo.setEditable(status);
		txtEsfuerzoFisico.setEditable(status);
		
		rbSi.setEnabled(status);
		rbNo.setEnabled(status);
		
		chbLaptop.setEnabled(status);
		chbPc.setEnabled(status);
		chbCelular.setEnabled(status);
		chbExtension.setEnabled(status);
		chbAutoPropio.setEnabled(status);
		chbAutoDeCompania.setEnabled(status);
		chbLicenciaConducir.setEnabled(status);
		chbCelLargaDistancia.setEnabled(status);
		chbOtro.setEnabled(status);
		
		txtOtro.setEditable(status);
		
		String color = status?"FFFFFF":"EBEBEB";
		txaObjectivoDelPuesto.setBackground(new Color(Integer.parseInt(color,16)));
		txaRequisitos.setBackground(new Color(Integer.parseInt(color,16)));
		txaCursosHabilidades.setBackground(new Color(Integer.parseInt(color,16)));
		txaExperienciaGeneral.setBackground(new Color(Integer.parseInt(color,16)));
		txaExperienciaEspecifica.setBackground(new Color(Integer.parseInt(color,16)));
	}
	
	ActionListener opAgregarResponsabilidadesDelPuesto = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			agregarRegistro();
			txtResponsabilidad.setText("");
			txtResponsabilidad.requestFocus();
		}
	};
	
	public void agregarRegistro(){
		if(!txtResponsabilidad.getText().toString().trim().equals("")){
			
			boolean encontroPalabra = false;
			for(int i=0; i<tablaResponsabilidadesDelPuesto.getRowCount(); i++){
				if(txtResponsabilidad.getText().toString().trim().equals(tablaResponsabilidadesDelPuesto.getValueAt(i, 1).toString().trim())){
					encontroPalabra=true;
				}
			}
			
			if(!encontroPalabra){
				Object[] reg = new Object[3];
				
				reg[0]=modeloResponsabilidadesDelPuesto.getRowCount()+1;
				reg[1]=txtResponsabilidad.getText().toString().trim();
				reg[2]=false;
				modeloResponsabilidadesDelPuesto.addRow(reg);
			}else{
				JOptionPane.showMessageDialog(null, "La Responsabilidad Del Puesto Que Intenta Registrar Ya Se Encuentra En La Lista","Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
		  	  	return;
			}
			
		}else{
			JOptionPane.showMessageDialog(null, "Es Necesario Ingresar Una Responsabilidad Para Poder Agregarla A La Lista","Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
	  	  	return;
		}
	}
	
	int filaResponabilidadSeleccionada = -1;
	int columnaResponabilidadSeleccionada = -1;
	String responsabilidadSeleccionada = "";
	String responsabilidadCambiar = "";
	private void agregar(final JTable tbl) {
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
	        public void mouseClicked(MouseEvent e) {
	        	if(e.getClickCount() == 1){
	        		
	        			if(tbl.isEnabled()){
	        				filaResponabilidadSeleccionada = tbl.getSelectedRow();
			        		columnaResponabilidadSeleccionada = tbl.getSelectedColumn();
			        		
			        		if(columnaResponabilidadSeleccionada<2){
			        			responsabilidadSeleccionada = tbl.getValueAt(filaResponabilidadSeleccionada, 1).toString().trim();
			        			tbl.setRowSelectionInterval(filaResponabilidadSeleccionada, filaResponabilidadSeleccionada);
			        		}
			        	}
	        		}
	        		
	        		
	        }
        });
    }
	
	ActionListener opSubirResponsabilidadesDelPuesto = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(filaResponabilidadSeleccionada<1){
				JOptionPane.showMessageDialog(null, "No Existen Registros Hacia Arriba O No Se A Seleccionado Ningun Registro","Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
		  	  	return;
			}else{
				responsabilidadCambiar=tablaResponsabilidadesDelPuesto.getValueAt(filaResponabilidadSeleccionada-1, 1).toString().trim();
				tablaResponsabilidadesDelPuesto.setValueAt(responsabilidadSeleccionada, filaResponabilidadSeleccionada-1, 1);
				tablaResponsabilidadesDelPuesto.setValueAt(responsabilidadCambiar, filaResponabilidadSeleccionada, 1);
				filaResponabilidadSeleccionada--;
				tablaResponsabilidadesDelPuesto.setRowSelectionInterval(filaResponabilidadSeleccionada, filaResponabilidadSeleccionada);
			}
		}
	};
	
	ActionListener opBajarResponsabilidadesDelPuesto = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(filaResponabilidadSeleccionada==tablaResponsabilidadesDelPuesto.getRowCount()-1){
				JOptionPane.showMessageDialog(null, "No Existen Registros Hacia Abajo O No Se A Seleccionado Ningun Registro","Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
		  	  	return;
			}else{
				responsabilidadCambiar=tablaResponsabilidadesDelPuesto.getValueAt(filaResponabilidadSeleccionada+1, 1).toString().trim();
				tablaResponsabilidadesDelPuesto.setValueAt(responsabilidadSeleccionada, filaResponabilidadSeleccionada+1, 1);
				tablaResponsabilidadesDelPuesto.setValueAt(responsabilidadCambiar, filaResponabilidadSeleccionada, 1);
				filaResponabilidadSeleccionada++;
				tablaResponsabilidadesDelPuesto.setRowSelectionInterval(filaResponabilidadSeleccionada, filaResponabilidadSeleccionada);
			}
		}
	};
	
	ActionListener opQuitar = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			
			int i=0;
			for(i=0; i<tablaResponsabilidadesDelPuesto.getRowCount(); i++){
				if(Boolean.valueOf(tablaResponsabilidadesDelPuesto.getValueAt(i, 2).toString().trim())){
					modeloResponsabilidadesDelPuesto.removeRow(i);
					i=0;
				}
				if(tablaResponsabilidadesDelPuesto.getRowCount()>0){
					modeloResponsabilidadesDelPuesto.setValueAt(i+1, i, 0);
				}
			}
		}
	};
	
	ActionListener opFiltroPuestos = new ActionListener(){
		public void actionPerformed(ActionEvent e){
//			mostrarPuestosDisponibles = txtFolio.getText().equals("")?false:true;
			new Cat_Filtro_Puestos("Filtro").setVisible(true);
		}
	};
	
	ActionListener opPuesto = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			new Cat_Filtro_Puestos("Puesto").setVisible(true);
		}
	};
	
	ActionListener opFiltroReporteA = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			new Cat_Filtro_Puestos("Reporta").setVisible(true);
		}
	};
	
	ActionListener opCargarArchivo = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
//			String btn = e.getActionCommand().trim();
			
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
//         				if(pathArchivo.substring(pathArchivo.indexOf(".")+1, pathArchivo.length()).trim().toUpperCase().equals("JPG") || pathArchivo.substring(pathArchivo.indexOf(".")+1, pathArchivo.length()).trim().toUpperCase().equals("PNG")){
     					if(pathArchivo.substring(pathArchivo.indexOf(".")+1, pathArchivo.length()).trim().toUpperCase().equals("JPG") ){

             					lblRutaOrganigrama.setText(pathArchivo);

             					try {
									imagB  = Files.readAllBytes(Paths.get(pathArchivo));
									lblImage.setIcon(crearIcon(imagB));
								} catch (IOException e1) {
									e1.printStackTrace();
								}
             					
        						
         					
         				}else{
         					JOptionPane.showMessageDialog(null, "Solo Se Pueden Cargar Imagenes Con Extencion JPG.", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
            				return;
         				}
                }
             }
		}
	};
	
	public Icon crearIcon(byte[] bs){
		ImageIcon tmpIconDefault= new ImageIcon(bs);
		
			int anchoRealDeImagen = tmpIconDefault.getIconWidth();
			int altoRealDeImg = tmpIconDefault.getIconHeight();
			int anchoDeImagenEscala = (txaObjectivoDelPuesto.getWidth()-20);
			int altoDeImgagenEscala = (altoRealDeImg*anchoDeImagenEscala)/anchoRealDeImagen;
		 
			return new ImageIcon(tmpIconDefault.getImage().getScaledInstance(anchoDeImagenEscala, altoDeImgagenEscala, Image.SCALE_DEFAULT));
	}
	
	public void pagina1(){
		
		int x=10, y=5, ancho=80;
		
		panel1.add(lblFolio).setBounds(x, y, ancho, 20);
		panel1.add(txtFolio).setBounds(x+ancho+20, y, ancho*2, 20);
		panel1.add(lblPuesto).setBounds(x, y+=25, ancho+20, 20);
		panel1.add(txtFolioPuesto).setBounds(x+ancho+20, y, 40, 20);
		panel1.add(txtPuesto).setBounds(x+ancho+60, y, ancho*5-40, 20);
		panel1.add(btnPuesto).setBounds(x+ancho*6+20, y, 30, 20);
		
		panel1.add(lblUnidadDeNegocio).setBounds(x, y+=25, ancho*3, 20);
		panel1.add(cmbUnidadDeNegocio).setBounds(x+ancho+20, y, ancho*3, 20);
		
		panel1.add(lblReportaA).setBounds(x+ancho*5, y, ancho*2, 20);
		panel1.add(txtFolioReportaA).setBounds(x+ancho*6-20, y, 40, 20);
		panel1.add(txtReporteA).setBounds(x+ancho*6+20, y, ancho*5, 20);
		panel1.add(btnReporteA).setBounds(x+ancho*11+20, y, 30, 20);
		
		panel1.add(lblEstablecimiento).setBounds(x, y+=25, ancho*3, 20);
		panel1.add(cmbEstablecimiento).setBounds(x+ancho+20, y, ancho*3, 20);
		
		panel1.add(lblEdadIn).setBounds(x+ancho*5, y, ancho+20, 20);
		panel1.add(txtEdadIn).setBounds(x+ancho*6+20, y, 40, 20);
		panel1.add(lblEdadFin).setBounds(x+ancho*7-10, y, 30, 20);
		panel1.add(txtEdadFin).setBounds(x+ancho*7+10, y, 40, 20);
		
		panel1.add(lblDepartamento).setBounds(x, y+=25, ancho*3, 20);		
		panel1.add(cmbDepartamento).setBounds(x+ancho+20, y, ancho*3, 20);
		
		panel1.add(lblSexo).setBounds(x+ancho*5, y, 50, 20);
		panel1.add(cmbSexo).setBounds(x+ancho*6+20, y, ancho*2, 20);
		panel1.add(lblEstadoCivil).setBounds(x+ancho*8+50, y, ancho, 20);
		panel1.add(cmbEstadoCivil).setBounds(x+ancho*9+50, y, ancho*2, 20);
		
		panel1.add(lblObjetivoDelPuesto).setBounds(x, y+=30, ancho+30, 20);
		panel1.add(lblObjetivoDelPuestoNota).setBounds(x+(ancho+30), y, ancho*4, 20);
		panel1.add(scrollObjectivoDelPuesto).setBounds(x, y+=20, ancho*12-30, 100);
		
		panel1.add(lblResponsabilidadesDelPuesto).setBounds(x, y+=110, ancho*2, 20);
		panel1.add(lblResponsabilidadesDelPuestoNota).setBounds(x+(ancho*2), y, ancho*4, 20);
		
		panel1.add(txtResponsabilidad).setBounds(x, y+=20, ancho*5, 20);
		panel1.add(btnAgregarResponsabilidad).setBounds(x+(ancho*5), y , ancho+20, 20);
		
		panel1.add(new JLabel("Mover:")).setBounds(x+(ancho*7), y , ancho, 20);
		panel1.add(btnSubir).setBounds(x+(ancho*7)+40, y , 30, 20);
		panel1.add(btnBajar).setBounds(x+(ancho*8), y , 30, 20);
		panel1.add(btnQuitarfila).setBounds(x+(ancho*10), y , ancho+50, 20);
		
		panel1.add(scrollResponsabilidadesDelPuesto).setBounds(x, y+=20, ancho*12-30, 170);
		
		panel1.add(lblNivelDeEduacacionRequerido).setBounds(x, y+=180, ancho*2, 20);
		panel1.add(cmbEscolaridad).setBounds(x+(ancho*2), y, ancho*2, 20);
		panel1.add(lblNivelDeEduacacionRequeridoNota).setBounds(x+(ancho*4)+10, y, ancho*7, 20);
	}
	
	public void pagina2(){
		
		GrViajes.add(rbSi);
		GrViajes.add(rbNo);
		
		int x=10, y=5, ancho=80;
		
		panel2.add(lblListaDeRequisitos).setBounds(x, y, ancho*8, 20);
		panel2.add(scrollRequisitos).setBounds(x, y+=20, ancho*12-30, 100);
		
		panel2.add(lblHabilidadesYConocimientos).setBounds(x, y+=110, ancho*3+50, 20);
		panel2.add(lblHabilidadesYConocimientosNota).setBounds(x+ancho*3+70, y, ancho*6, 20);
		panel2.add(lblCursosHabilidades1).setBounds(x, y+=20, ancho*2, 20);
		panel2.add(scrollCursosHabilidades).setBounds(x+ancho*2-30, y, ancho*10, 30);
		panel2.add(lblCursosHabilidades2).setBounds(x, y+=10, ancho*2, 20);
		
		panel2.add(lblExperienciaGeneral).setBounds(x, y+=30, ancho*5, 20);
		panel2.add(scrollExperienciaGeneral).setBounds(x+ancho*3-60, y-5, ancho*9+30, 30);
		
		panel2.add(lblExperienciaEspecifica).setBounds(x, y+=35, ancho*5, 20);
		panel2.add(scrollExperienciaEspecifica).setBounds(x+ancho*3-50, y-5, ancho*9+20, 30);
		
		panel2.add(lblFacultamiento).setBounds(x, y+=35, ancho*2, 20);
		panel2.add(lblFacultamientoNota).setBounds(x+ancho+20, y, ancho*5, 20);
		
		panel2.add(lblDirectos).setBounds(x, y+=20, ancho, 20);
		panel2.add(txtDirectos).setBounds(x+50, y, 35, 20);
		panel2.add(lblIndirectos).setBounds(x+ancho+20, y, ancho, 20);
		panel2.add(txtIndirectos).setBounds(x+ancho*2, y, 35, 20);
		
		panel2.add(lblInteraccionesDelPuesto).setBounds(x, y+=35, ancho*5, 20);
		
		panel2.add(txtPorcentajeExterno).setBounds(x, y+=25, 30, 20);
		panel2.add(lblExternas).setBounds(x+40, y, ancho, 20);
		panel2.add(txtInteraccionesEsternasPuesto).setBounds(x+ancho+20, y, ancho*10+30, 20);
		
		panel2.add(txtPorcentajeInterno).setBounds(x, y+=25, 30, 20);
		panel2.add(lblInternas).setBounds(x+40, y, ancho, 20);
		panel2.add(txtInteraccionesInternasPuesto).setBounds(x+ancho+20, y, ancho*10+30, 20);
		
		panel2.add(lblCondicionesDeTrabajo).setBounds(x, y+=25, ancho*2, 20);
		panel2.add(lblCondicionesDeTrabajoNota).setBounds(x+ancho*2-10, y, ancho*6, 20);
		
		panel2.add(lblAmbienteDeTrabajo).setBounds(x, y+=25, ancho*2, 20);
		panel2.add(txtAmbienteDeTrabajo).setBounds(x+ancho+30, y, ancho*10+20, 20);
		
		panel2.add(lblEsfuezoFisico).setBounds(x, y+=25, ancho*2, 20);
		panel2.add(txtEsfuerzoFisico).setBounds(x+ancho+30, y, ancho*10+20, 20);
		
		panel2.add(lblViajes).setBounds(x, y+=25, ancho*2+20, 20);
		panel2.add(rbSi).setBounds(x+ancho*2+20, y, 50, 20);
		panel2.add(rbNo).setBounds(x+ancho*3, y, 50, 20);
		
		panel2.add(lblHerramientasYEquipos).setBounds(x, y+=25, ancho*3+20, 20);
		
		panel2.add(chbLaptop).setBounds(x, y+=25, ancho, 20);
		panel2.add(chbCelular).setBounds(x+=ancho, y, ancho, 20);
		panel2.add(chbAutoPropio).setBounds(x+=ancho, y, ancho+30, 20);
		panel2.add(chbAutoDeCompania).setBounds(x+=(ancho+30), y, ancho+50, 20);
		panel2.add(chbLicenciaConducir).setBounds(x+=(ancho+50), y, ancho+50, 20);
		
		panel2.add(chbPc).setBounds(x=10, y+=25, ancho, 20);
		panel2.add(chbExtension).setBounds(x+=ancho, y, ancho, 20);
		panel2.add(chbCelLargaDistancia).setBounds(x+=ancho, y, ancho+30, 20);
		panel2.add(chbOtro).setBounds(x+=(ancho+30), y, 55, 20);
		panel2.add(txtOtro).setBounds(x+=(55), y, ancho*3-35, 20);
	}
	
	public void pagina3(){
		
		int x=10, y=5, ancho=80;
		
		panel3.add(btnExaminar).setBounds(x, y, ancho+60, 20);
		panel3.add(lblRutaOrganigrama).setBounds(x+ancho*2, y, ancho*10, 20);
		
		panel3.add(scrollOrganigrama).setBounds(x, y+=20, ancho*11+50, 540);
	}
	
	public void init_tabla(JTable tabla){
    	tabla.getColumnModel().getColumn(0).setMinWidth(30);	
    	tabla.getColumnModel().getColumn(1).setMinWidth(800);
    	tabla.getColumnModel().getColumn(2).setMinWidth(60);	
    	
    	tabla.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","derecha","Arial","negrita",12));
    	tabla.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",12));
    	tabla.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("CHB","centro","Arial","negrita",12));
    }
	
	//TODO inicia filtro_puestos	
		public class Cat_Filtro_Puestos extends JDialog{
			Container contf = getContentPane();
			JLayeredPane panelf = new JLayeredPane();
			Connexion con = new Connexion();
			
			Obj_tabla ObjTab =new Obj_tabla();
			int columnasp = 2,checkbox=-1;
			String parametro="";
			public void init_tablafp(){
		    	this.tablafp.getColumnModel().getColumn(0).setMinWidth(55);
		    	this.tablafp.getColumnModel().getColumn(1).setMinWidth(375);
		    	
		    	String filtrar = parametro.equals("Filtro")? "Filtro" : (parametro.equals("Puesto") ? "DISPONIBLES" : "TODOS");
		    	
		    	String comandof="exec filtro_puestos_para_dpr '"+filtrar+"'";
		    	System.out.println(comandof);
				String basedatos="26",pintar="si";
				ObjTab.Obj_Refrescar(tablafp,modelof, columnasp, comandof, basedatos,pintar,checkbox);
		    }
			
			@SuppressWarnings("rawtypes")
			public Class[] base (){
				Class[] types = new Class[columnasp];
				for(int i = 0; i<columnasp; i++){types[i]= java.lang.Object.class;}
				 return types;
			}
			
			public DefaultTableModel modelof = new DefaultTableModel(null, new String[]{"Folio","Puesto"}){
				 @SuppressWarnings("rawtypes")
					Class[] types = base();
					@SuppressWarnings({ "rawtypes", "unchecked" })
					public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
					public boolean isCellEditable(int fila, int columna){return false;}
			};
			
			JTable tablafp = new JTable(modelof);
			public JScrollPane scroll_tablafp = new JScrollPane(tablafp);
		     @SuppressWarnings({ "rawtypes" })
		    private TableRowSorter trsfiltro;
		     
			JTextField txtBuscarfp  = new Componentes().textfiltro(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<", 300, "String",tablafp,columnasp);
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Cat_Filtro_Puestos(String btnparametro){
				parametro=btnparametro;
				
				this.setSize(500,500);
				trsfiltro = new TableRowSorter(modelof); 
				tablafp.setRowSorter(trsfiltro);
				this.panelf.add(txtBuscarfp).setBounds      (10 ,20 ,470 , 20 );
				this.panelf.add(scroll_tablafp).setBounds   (10 ,40 ,470 ,415 );
				this.init_tablafp();
				this.agregar(tablafp);
				
				this.setResizable(false);
				this.setLocationRelativeTo(null);
				this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				this.setModal(true);
				this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Filter-List-icon32.png"));
				this.panelf.setBorder(BorderFactory.createTitledBorder("Selecione Un Puesto Con Doble Click"));
				this.setTitle("Filtro De Puestos");
				
				contf.add(panelf);
			}
			
			private void agregar(final JTable tbl) {
		        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
			        	if(e.getClickCount()==2){
			        			int fila = tablafp.getSelectedRow();
							    
			        			if(parametro.equals("Filtro")){
			        				
			        						movimiento = "MODIFICAR";
			        						btnEditar.setEnabled(true);
			        						Obj_Descripcion_De_Puestos_y_Responsabilidades dpr = new Obj_Descripcion_De_Puestos_y_Responsabilidades().buscar(Integer.valueOf(tablafp.getValueAt(fila,0).toString().trim()));
			        						
			        						txtFolio.setText(dpr.getFolio()+"");
			        						
			        						txtFolioPuesto.setText (dpr.getFolioPuesto()+"");
			        						txtPuesto.setText   (dpr.getPuesto()+"");
			        						
			        						cmbUnidadDeNegocio.setSelectedItem(dpr.getUnidadNegocio());
			        						cmbEstablecimiento.setSelectedItem(dpr.getEstablecimiento());
			        						cmbDepartamento.setSelectedItem(dpr.getDepartamento());
			        						
			        						txtFolioReportaA.setText(dpr.getFolioReportaA()+"");
			        						txtReporteA.setText(dpr.getReportaA());
			        						
			        						txtEdadIn.setText(dpr.getEdadIn()+"");
			        						txtEdadFin.setText(dpr.getEdadFin()+"");

			        						cmbSexo.setSelectedItem(dpr.getSexo());
			        						cmbEstadoCivil.setSelectedItem(dpr.getEstadoCivil());
			        						txaObjectivoDelPuesto.setText(dpr.getObjetivoPuesto());

			        						modeloResponsabilidadesDelPuesto.setRowCount(0);
			        						for(Object[] reg: dpr.getResponsabilidadesPuesto()){
			        							modeloResponsabilidadesDelPuesto.addRow(reg);
			        						}
			        						
			        						cmbEscolaridad.setSelectedItem(dpr.getNivelEstudios());
			        						txaRequisitos.setText(dpr.getListaDeEspecificaciones());
			        						txaCursosHabilidades.setText(dpr.getCursosHabilidades());
			        						txaExperienciaGeneral.setText(dpr.getEsperienciaGeneral());
			        						txaExperienciaEspecifica.setText(dpr.getEsperienciaEspecifica());
			        						txtDirectos.setText(dpr.getFacultamientosDirectos()+"");
			        						txtIndirectos.setText(dpr.getFacultamientosIndirectos()+"");
			        						
			        						imagB = dpr.getOrganigramaB();
			        						lblImage.setIcon(crearIcon(imagB));
			        						
			        						txtPorcentajeExterno.setText(dpr.getInteracionDelPuestoExternas()+"");
			        						txtInteraccionesEsternasPuesto.setText(dpr.getRelacionDelPuestoExternas());
			        						txtPorcentajeInterno.setText(dpr.getInteracionDelPuestoInternas()+"");
			        						txtInteraccionesInternasPuesto.setText(dpr.getRelacionDelPuestoInternas());
			        						
			        						txtAmbienteDeTrabajo.setText(dpr.getAmbienteDeTrabajo());
			        						txtEsfuerzoFisico.setText(dpr.getEsfuerzoFisico());
			        						
			        						rbSi.setSelected(dpr.isViaje());  
			        						rbNo.setSelected(!dpr.isViaje());  
			        						
			        						chbLaptop.setSelected(dpr.isLaptop());  
			        						chbPc.setSelected(dpr.isPc());  
			        						chbCelular.setSelected(dpr.isCelular());  
			        						chbExtension.setSelected(dpr.isExtencion());  
			        						chbAutoPropio.setSelected(dpr.isAutoPropio());  
			        						chbAutoDeCompania.setSelected(dpr.isAutoEmpresa());  
			        						chbLicenciaConducir.setSelected(dpr.isLicencia());  
			        						chbCelLargaDistancia.setSelected(dpr.isLargaDistancia());  
			        						chbOtro.setSelected(dpr.isOtro());  
			        						txtOtro.setText(dpr.getNotaOtro());  
			        				
			        				btnNuevo.setEnabled(false);
			        				btnDeshacer.setEnabled(true);
			        			}
			        			if(parametro.equals("Puesto")){
			        				txtFolioPuesto.setText   (tablafp.getValueAt(fila,0)+"");
			        				txtPuesto.setText   (tablafp.getValueAt(fila,1)+"");
			        			}
			        			if(parametro.equals("Reporta")){
			        				txtFolioReportaA.setText   (tablafp.getValueAt(fila,0)+"");
			        				txtReporteA.setText   (tablafp.getValueAt(fila,1)+"");
			        			}
							dispose();
			        	}
			        }
		        });
		    }
		}//termina filtro puestos
		
	public static void main(String[] args) {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Descripcion_De_Puestos_y_Responsabilidades().setVisible(true);
		}catch(Exception e){	}
	}
}
