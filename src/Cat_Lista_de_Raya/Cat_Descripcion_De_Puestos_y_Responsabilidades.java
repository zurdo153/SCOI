package Cat_Lista_de_Raya;

import java.awt.Container;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;

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
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Conexiones_SQL.Connexion;
import Obj_Lista_de_Raya.Obj_Departamento;
import Obj_Lista_de_Raya.Obj_Empleados;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_tabla;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Descripcion_De_Puestos_y_Responsabilidades extends JFrame{

	Container cont = getContentPane();
	JTabbedPane pestanas = new JTabbedPane();
	
	JLayeredPane panel1 = new JLayeredPane();
	JLayeredPane panel2 = new JLayeredPane();
	
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
	JCButton btnBuscarPuesto = new JCButton("", "buscar.png", "Azul");
	
	String[] UN = {"Alimentos","Ferreteria","Refaccionaria","Retail","Staff"};
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
	
	JTextField txtResponsabilidad = new Componentes().text(new JCTextField(), "Ingresar Responsabilidad", 200, "String");//TODO se agregara a una tabla
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
		
		JCButton btnExaminar = new JCButton("Organigrama", "buscar.png", "Azul");
		JLabel lblRutaOrganigrama = new JLabel("");
		
//		JTextArea txaOrganigrama = new Componentes().textArea(new JTextArea(), "", 200);
		
		String rutaImagen = "";
//		ImageIcon tmpIconDefault = new ImageIcon(System.getProperty("user.dir")+"/Iconos/Un.JPG");
//		Icon iconoDefault = new ImageIcon(tmpIconDefault.getImage().getScaledInstance(1000, 400, Image.SCALE_DEFAULT));
		JLabel lblImage = new JLabel(rutaImagen);
		JScrollPane scrollOrganigrama = new JScrollPane(lblImage);
		
		JTextField txtPorcentajeExterno = new Componentes().text(new JCTextField(), "", 10, "Int");
		JTextField txtInteraccionesEsternasPuesto = new Componentes().text(new JCTextField(), "Seguridad, Logística, Desarrollo Humano, Cedis.", 120, "String");
		
		JTextField txtPorcentajeInterno = new Componentes().text(new JCTextField(), "", 10, "Int");
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
		
		JCButton btnGuardar = new JCButton("Guardar", "guardar.png", "Azul");
		
//pagina 2 (fin)------------------------------------------------------------------------------------------------------------------------------------------
	    
	Border blackline;
	public Cat_Descripcion_De_Puestos_y_Responsabilidades() {
		
		pestanas.addTab("Pagina 1", panel1);
		pestanas.addTab("Pagina 2", panel2);
		
		this.setSize(965,700);
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
		
		cont.add(pestanas);
	}
	
	
	
	public void pagina1(){
		
		this.panel1.setBorder(BorderFactory.createTitledBorder(blackline, "Ingresar Datos DPR (Pagina 1)"));
		
		int x=10, y=15, ancho=80;
		
		panel1.add(lblFolio).setBounds(x, y, ancho, 20);
		panel1.add(txtFolio).setBounds(x+ancho+20, y, ancho*2, 20);
		panel1.add(lblPuesto).setBounds(x, y+=25, ancho+20, 20);
		panel1.add(txtFolioPuesto).setBounds(x+ancho+20, y, 40, 20);
		panel1.add(txtPuesto).setBounds(x+ancho+60, y, ancho*5-40, 20);
		panel1.add(btnBuscarPuesto).setBounds(x+(ancho*6)+20, y, 30, 20);
		
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
		
		panel1.add(lblObjetivoDelPuesto).setBounds(x, y+=25, ancho+30, 20);
		panel1.add(lblObjetivoDelPuestoNota).setBounds(x+(ancho+30), y, ancho*4, 20);
		
		panel1.add(scrollObjectivoDelPuesto).setBounds(x, y+=20, ancho*12-30, 100);
		
		panel1.add(lblResponsabilidadesDelPuesto).setBounds(x, y+=105, ancho*2, 20);
		panel1.add(lblResponsabilidadesDelPuestoNota).setBounds(x+(ancho*2), y, ancho*4, 20);
		
		panel1.add(txtResponsabilidad).setBounds(x, y+=20, ancho*5, 20);
		panel1.add(btnAgregarResponsabilidad).setBounds(x+(ancho*5), y , ancho+20, 20);
		
		panel1.add(new JLabel("Mover:")).setBounds(x+(ancho*7), y , ancho, 20);
		panel1.add(btnSubir).setBounds(x+(ancho*7)+40, y , 30, 20);
		panel1.add(btnBajar).setBounds(x+(ancho*8), y , 30, 20);
		panel1.add(btnQuitarfila).setBounds(x+(ancho*10), y , ancho+50, 20);
		
		panel1.add(scrollResponsabilidadesDelPuesto).setBounds(x, y+=20, ancho*12-30, 180);
		
		panel1.add(lblNivelDeEduacacionRequerido).setBounds(x, y+=185, ancho*2, 20);
		panel1.add(cmbEscolaridad).setBounds(x+(ancho*2), y, ancho*2, 20);
		panel1.add(lblNivelDeEduacacionRequeridoNota).setBounds(x+(ancho*4)+10, y, ancho*7, 20);
		
		panel1.add(lblListaDeRequisitos).setBounds(x, y+=25, ancho*8, 20);
		panel1.add(scrollRequisitos).setBounds(x, y+=20, ancho*12-30, 100);
		
		
		init_tabla(tablaResponsabilidadesDelPuesto);
		
		btnBuscarPuesto.addActionListener(opFiltroPuestos);
		btnReporteA.addActionListener(opFiltroReporteA);
		
		txtResponsabilidad.addActionListener(opAgregarResponsabilidadesDelPuesto);
		btnAgregarResponsabilidad.addActionListener(opAgregarResponsabilidadesDelPuesto);
		
		btnSubir.addActionListener(opSubirResponsabilidadesDelPuesto);
		btnBajar.addActionListener(opBajarResponsabilidadesDelPuesto);
		btnQuitarfila.addActionListener(opQuitar);
		
		agregar(tablaResponsabilidadesDelPuesto);
	}
	
	public void pagina2(){
		this.panel2.setBorder(BorderFactory.createTitledBorder(blackline, "Ingresar Datos DPR (Pagina 2)"));
		
		GrViajes.add(rbSi);
		GrViajes.add(rbNo);
		
		int x=10, y=15, ancho=80;
		
		panel2.add(lblHabilidadesYConocimientos).setBounds(x, y, ancho*3+50, 20);
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
		
		panel2.add(btnExaminar).setBounds(x, y+=25, ancho+60, 20);
		panel2.add(lblRutaOrganigrama).setBounds(x+ancho*2, y, ancho*10, 20);
		
		panel2.add(scrollOrganigrama).setBounds(x, y+=20, ancho*11+50, 180);
		
		panel2.add(lblInteraccionesDelPuesto).setBounds(x, y+=180, ancho*5, 20);
		
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
		
		panel2.add(btnGuardar).setBounds(x+(ancho*5), y+=25, ancho*2-35, 20);	
		panel2.add(chbPc).setBounds(x=10, y, ancho, 20);
		panel2.add(chbExtension).setBounds(x+=ancho, y, ancho, 20);
		panel2.add(chbCelLargaDistancia).setBounds(x+=ancho, y, ancho+30, 20);
		panel2.add(chbOtro).setBounds(x+=(ancho+30), y, 55, 20);
		panel2.add(txtOtro).setBounds(x+=(55), y, ancho*3-35, 20);
		
		btnExaminar.addActionListener(opCargarArchivo);
	}
	
	ActionListener opAgregarResponsabilidadesDelPuesto = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			agregarRegistro();
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
	        		
	        		filaResponabilidadSeleccionada = tbl.getSelectedRow();
	        		columnaResponabilidadSeleccionada = tbl.getSelectedColumn();
	        		
	        		if(columnaResponabilidadSeleccionada<2){
	        			responsabilidadSeleccionada = tbl.getValueAt(filaResponabilidadSeleccionada, 1).toString().trim();
	        			tbl.setRowSelectionInterval(filaResponabilidadSeleccionada, filaResponabilidadSeleccionada);
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
         				if(pathArchivo.substring(pathArchivo.indexOf(".")+1, pathArchivo.length()).trim().toUpperCase().equals("JPG") || pathArchivo.substring(pathArchivo.indexOf(".")+1, pathArchivo.length()).trim().toUpperCase().equals("PNG")){
         					rutaImagen = pathArchivo;
         					lblRutaOrganigrama.setText(rutaImagen);
         					ImageIcon tmpIconDefault = new ImageIcon(rutaImagen);
         					
//         					int anchoRealDeImagen = tmpIconDefault.getIconWidth();
         					int altoRealDeImg = tmpIconDefault.getIconHeight();
         					int anchoDeImagenEscala = txaObjectivoDelPuesto.getWidth()-20;
         					int altoDeImgagenEscala = (altoRealDeImg*anchoDeImagenEscala)/anchoDeImagenEscala;
         					
         					Icon iconoDefault = new ImageIcon(tmpIconDefault.getImage().getScaledInstance(anchoDeImagenEscala, altoDeImgagenEscala, Image.SCALE_DEFAULT));
         					lblImage.setIcon(iconoDefault);
         					
         				}else{
         					JOptionPane.showMessageDialog(null, "Solo Se Pueden Cargar Imgagenes Con Extencion JPG y PNG.", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
            				return;
         				}
//                	ArchivosCargados();
                }
             }
		}
	};

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
		    	String comandof="exec cuadrantes_puestos_para_nuevos_cuadrantes ";
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
							    
			        			if(parametro.equals("Puesto")){
			        				txtFolioPuesto.setText   (tablafp.getValueAt(fila,0)+"");
			        				txtPuesto.setText   (tablafp.getValueAt(fila,1)+"");
			        			}else{
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
