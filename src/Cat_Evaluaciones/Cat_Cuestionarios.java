package Cat_Evaluaciones;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import Conexiones_SQL.Connexion;
import Obj_Evaluaciones.Obj_Cuestionarios;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_Filtro_Dinamico_Plus;
import Obj_Principal.Obj_tabla;
import Obj_Renders.tablaRenderer;
import Obj_Xml.CrearXmlString;

@SuppressWarnings("serial")
public class Cat_Cuestionarios extends JFrame{
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	Connexion con = new Connexion();
	int columnas = 2,checkbox=-1;
	public void init_tabla(){
    	this.tabla.getColumnModel().getColumn(0).setMinWidth(60);	
    	this.tabla.getColumnModel().getColumn(1).setMinWidth(450);
    	
    	tabla.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","derecha","Arial","negrita",12));
		tabla.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",12));
    }
	
	 public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"Folio","Pregunta"}){
		 @SuppressWarnings("rawtypes")
			Class[] types = new Class[]{
					java.lang.Object.class,
					java.lang.Object.class,
			};
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {
		         return types[columnIndex];
		     }
			public boolean isCellEditable(int fila, int columna){
				return false;
			}
	    };
	
	    JTable tabla = new JTable(modelo);
		public JScrollPane scroll_tabla = new JScrollPane(tabla);
	
	JTextField txtPreguntaFiltro = new JTextField();
	@SuppressWarnings("rawtypes")
	private TableRowSorter trsfiltro;
	
	JTextField txtFolio = new Componentes().text(new JTextField(), "Folio", 9, "Int");
	JTextField txtNombre = new Componentes().text(new JTextField(), "Nombre De Cuestionario", 150, "String");
	
	String[] status = {"Vigente","Cancelado"};
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbStatus = new JComboBox(status);
	
	String[] clasificacion = new Obj_Cuestionarios().clasificaciones();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbClasificacion = new JComboBox(clasificacion);

	String[] escalas = new Obj_Cuestionarios().escalas();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbEscalas = new JComboBox(escalas);
	
	JButton btnBuscar = new JCButton("","buscar.png","Azul");
	JButton btnNuevo = new JCButton("Nuevo","Nuevo.png","Azul");
	JButton btnEditar = new JCButton("Editar","editara.png","Azul");
	JButton btnSalir = new JCButton("Salir","salir16.png","Azul");
	JButton btnGuardar = new JCButton("Guardar","Guardar.png","Azul");
	JButton btnDeshacer = new JCButton("Deshacer","deshacer16.png","Azul");
	
	JButton btnPreguntas = new JCButton("Preguntas","a.png","Azul");
	JButton btnQuitarPreguntas = new JCButton("Eliminar","a.png","Azul");
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Cat_Cuestionarios(){
		
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/encuesta.png"));
		panel.setBorder(BorderFactory.createTitledBorder("Cuestionario"));
		
		this.setTitle("Elaboración De Cuestionario");
		
		trsfiltro = new TableRowSorter(modelo); 
		tabla.setRowSorter(trsfiltro);
		
		txtPreguntaFiltro.setToolTipText("Filtro Por Nombre");
		
		int x = 15, y=30, w=100,l=20;
		
		panel.add(new JLabel("Folio:")).setBounds      	(x     ,y    ,w  ,l);
		panel.add(txtFolio).setBounds                  	(x+=45 ,y    ,w  ,l);
		panel.add(btnBuscar).setBounds                 	(x+=100,y    ,32 ,l);
		
		panel.add(btnNuevo).setBounds                  	(x+=70,y    ,w  ,l);
		panel.add(btnEditar).setBounds                 	(x+=130,y   ,w+10,l);
		panel.add(btnDeshacer).setBounds               	(x+=130,y   ,w+10,l);
		panel.add(btnSalir).setBounds                  	(x+=130,y   ,w  ,l);
		x = 15;
		panel.add(new JLabel("Nombre:")).setBounds   	(x     ,y+=30,w  ,l);
		panel.add(txtNombre).setBounds					(x+=45 ,y    ,w*3,l);
		panel.add(new JLabel("Status:")).setBounds		(x+=320,y    ,w  ,l);
		panel.add(cmbStatus).setBounds                  (x+=50 ,y    ,80 ,l);
		
		panel.add(new JLabel("Clasificacion:")).setBounds(x=15 ,y+=30,w  ,l);
		panel.add(cmbClasificacion).setBounds			(x+=70 ,y    ,w*2,l);
		panel.add(new JLabel("Escala:")).setBounds		(x+=295,y    ,w  ,l);
		panel.add(cmbEscalas).setBounds                 (x+=50 ,y    ,170,l);
		
		panel.add(btnGuardar).setBounds                	(x+=190,y    ,w  ,l);
		x = 15;
		panel.add(txtPreguntaFiltro).setBounds          (x	   ,y+=25,410,l);
		panel.add(btnPreguntas).setBounds          		(x+=435 ,y   ,130,l);
		panel.add(btnQuitarPreguntas).setBounds    		(x+140 ,y    ,130,l);
		x = 15;
		panel.add(scroll_tabla).setBounds           	(x     ,y+20 ,w*7,w*4);
		init_tabla();
		agregar(tabla);
		cmbStatus.setEnabled(false);
		txtNombre.setEditable(false);
		
		txtFolio.requestFocus();
		txtFolio.addKeyListener(buscar_action);
//	----------------------------------------------------------------------------------------------------------------	
		btnGuardar.addActionListener(guardar);
		btnSalir.addActionListener(cerrar);
		btnBuscar.addActionListener(buscar);
		btnDeshacer.addActionListener(deshacer);
		btnNuevo.addActionListener(nuevo);
		btnEditar.addActionListener(editar);
		btnPreguntas.addActionListener(opPregunta);
		btnQuitarPreguntas.addActionListener(opQuitarPregunta);
		btnEditar.setEnabled(false);
		btnQuitarPreguntas.setEnabled(false);
		
		cmbClasificacion.setSelectedIndex(-1);
		cmbEscalas.setSelectedIndex(-1);
		
		txtPreguntaFiltro.addKeyListener(opFiltroNombre);
		cont.add(panel);
		this.setSize(740,570);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened( WindowEvent e ){
            	txtPreguntaFiltro.requestFocus();
           }
        });
	}
	
	ActionListener opPregunta = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			String preguntasUsadas = "";
			for(int i = 0; i<tabla.getRowCount(); i++){
				preguntasUsadas+="'"+tabla.getValueAt(i, 0).toString()+"',";
			}
			preguntasUsadas=preguntasUsadas.length()>2?preguntasUsadas.substring(0, preguntasUsadas.length()-1):"''";
			System.out.println(preguntasUsadas);
			new Cat_Filtro_De_Preguntas(preguntasUsadas).setVisible(true);
		}		
	};
	
	ActionListener opQuitarPregunta = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			int fila = tabla.getSelectedRow();
			if(fila>=0){
				if(JOptionPane.showConfirmDialog(null, "La Siguiente Pregunta Será Eliminada Del Cuestionario\n"+tabla.getValueAt(fila, 1).toString()+"\n ¿Desea Continuar?") == 0){
					modelo.removeRow(fila);
					btnQuitarPreguntas.setEnabled(true);
				}
			}else{
				JOptionPane.showMessageDialog(null, "Es Necesario Seleccionar La Pregunta Que Se Requiere Eliminar", "Aviso", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}
			
			
			
		}		
	};
	
	private void agregar(final JTable tbl) {
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
	        	if(e.getClickCount()==1){
						btnQuitarPreguntas.setEnabled(true);
	        	}
	        }
        });
    }
	
ActionListener guardar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(txtFolio.getText().equals("")){
				JOptionPane.showMessageDialog(null, "El Folio Es Requerido \n", "Aviso", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
			}else{			
				Obj_Cuestionarios cuestionario = new Obj_Cuestionarios().buscar(Integer.parseInt(txtFolio.getText()));
				
				if(cuestionario.getFolio() == Integer.parseInt(txtFolio.getText())){
					if(JOptionPane.showConfirmDialog(null, "El Registro Ya Existe, ¿Desea Cambiarlo?") == 0){
						if(validaCampos()!="") {
							JOptionPane.showMessageDialog(null, "Los Siguientes Campos Son Requeridos:\n"+validaCampos(), "Aviso", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
							return;
						}else{
							
							
							
							cuestionario.setFolio(Integer.parseInt(txtFolio.getText()));
							cuestionario.setCuestionario(txtNombre.getText());
							cuestionario.setStatus(cmbStatus.getSelectedItem().toString().trim());
							cuestionario.setClasificacion(cmbClasificacion.getSelectedItem().toString().trim());
							cuestionario.setEscala(cmbEscalas.getSelectedItem().toString().trim());
							
							int[] ignorarColumnas = {2};
							cuestionario.setCadena_xml(new CrearXmlString().CadenaXML(tabla, ignorarColumnas));
							
							if(cuestionario.guardar("actualizar")){
								
								init_tabla();
								panelLimpiar();
								panelEnabledFalse();
								txtFolio.setEditable(true);
								txtFolio.requestFocus();
								JOptionPane.showMessageDialog(null,"El Registró se Actualizó de Forma Segura","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/aplicara-el-dialogo-icono-6256-32.png"));
								}else{
									JOptionPane.showMessageDialog(null,"Error Al Guarda El Cuestionario Avise al Administrador del Sistema","Aviso",JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-icono-eliminar5252-64.png"));
								}
						}
					}else{
						return;
					}
				}else{
					if(validaCampos()!="") {
						JOptionPane.showMessageDialog(null, "Los Siguientes Campos Son Requeridos:\n "+validaCampos(), "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
						return;
					}else{
						cuestionario.setFolio(Integer.parseInt(txtFolio.getText()));
						cuestionario.setCuestionario(txtNombre.getText());
						cuestionario.setStatus(cmbStatus.getSelectedItem().toString().trim());
						cuestionario.setClasificacion(cmbClasificacion.getSelectedItem().toString().trim());
						cuestionario.setEscala(cmbEscalas.getSelectedItem().toString().trim());
						
						int[] ignorarColumnas = {2};
						cuestionario.setCadena_xml(new CrearXmlString().CadenaXML(tabla, ignorarColumnas));
						
						if(cuestionario.guardar("guardar")){
						
						init_tabla();
						panelLimpiar();
						panelEnabledFalse();
						txtFolio.setEditable(true);
						txtFolio.requestFocus();
						JOptionPane.showMessageDialog(null,"El Registró se Guardó de Forma Segura","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/aplicara-el-dialogo-icono-6256-32.png"));
						}else{
							JOptionPane.showMessageDialog(null,"Error Al Guarda El Cuestionario Avise al Administrador del Sistema","Aviso",JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-icono-eliminar5252-64.png"));
						}
					}
				}
			}			
		}
	};
	
	
	KeyListener opFiltroNombre = new KeyListener(){
		public void keyReleased(KeyEvent arg0) {
			int[] columnas = {1,2,3};
			new Obj_Filtro_Dinamico_Plus(tabla, txtPreguntaFiltro.getText().toUpperCase(), columnas);
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}		
	};
	
	KeyListener buscar_action = new KeyListener() {
		@Override
		public void keyTyped(KeyEvent e){
		}
		@Override
		public void keyReleased(KeyEvent e) {	
		}
		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				btnBuscar.doClick();
			}
		}
	};
	
	ActionListener buscar = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			if(txtFolio.getText().equals("")){
				new Cat_Filtro_De_Cuestionarios().setVisible(true);
			}else{
			Obj_Cuestionarios cuestionario = new Obj_Cuestionarios().buscar(Integer.parseInt(txtFolio.getText()));
//			preguntas = 
//			cuestionario.buscar(Integer.parseInt(txtFolio.getText()));
			
			if(cuestionario.getFolio() != 0){
			
			txtFolio.setText(cuestionario.getFolio()+"");
			txtNombre.setText(cuestionario.getCuestionario()+"");
			cmbClasificacion.setSelectedItem(cuestionario.getClasificacion());
			cmbEscalas.setSelectedItem(cuestionario.getEscala());
			cmbStatus.setSelectedItem(cuestionario.getStatus().toString().trim());
			
			for(Object[] fila: cuestionario.getArreglo()){
				modelo.addRow(fila);
			}
			
			btnNuevo.setEnabled(false);
			btnEditar.setEnabled(true);
			panelEnabledFalse();
			txtFolio.setEditable(true);
			txtFolio.requestFocus();
			
			}
			else{
				JOptionPane.showMessageDialog(null, "El Registro no existe","Error",JOptionPane.WARNING_MESSAGE);
				return;
				}
			}
		}
	};
	
	ActionListener cerrar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			dispose();
		}
		
	};
	
	private String validaCampos(){
		String error="";
		if(txtNombre.getText().equals("")) 			error+= "Pregunta\n";
		return error;
	}
	
	ActionListener nuevo = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
				panelLimpiar();
				panelEnabledTrue();
				txtFolio.setText(new Obj_Cuestionarios().buscar_nuevo()+"");
				txtFolio.setEditable(false);
				txtNombre.requestFocus();
		}
	};
	
	ActionListener deshacer = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			panelLimpiar();
			panelEnabledFalse();
			txtFolio.setEditable(true);
			txtFolio.requestFocus();
			btnNuevo.setEnabled(true);
			btnEditar.setEnabled(false);
			cmbStatus.setSelectedItem("VIGENTE");
		}
	};
	
	ActionListener editar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			panelEnabledTrue();
			txtFolio.setEditable(false);
			btnEditar.setEnabled(false);
			btnNuevo.setEnabled(true);
		}		
	};
	
	public void panelEnabledFalse(){	
		txtFolio.setEditable(false);
		txtNombre.setEditable(false);
		cmbStatus.setEnabled(false);
	}		
	
	public void panelEnabledTrue(){	
		txtFolio.setEditable(true);
		txtNombre.setEditable(true);
		cmbStatus.setEnabled(true);	
	}
	
	public void panelLimpiar(){	
		txtFolio.setText("");
		txtNombre.setText("");
		cmbStatus.setSelectedItem("VIGENTE");
		modelo.setRowCount(0);
	}
	
	public class Cat_Filtro_De_Preguntas extends JFrame{
		
		Container cont = getContentPane();
		JLayeredPane panel = new JLayeredPane();
		
		int columnasFiltro = 3,checkboxFiltro=3;
		public void init_tablaFiltro(String condicion){
	    	this.tablaFiltro.getColumnModel().getColumn(0).setMinWidth(60);	
	    	this.tablaFiltro.getColumnModel().getColumn(1).setMinWidth(450);
	    	this.tablaFiltro.getColumnModel().getColumn(2).setMinWidth(153);
	    	
			String comando="select folio,nombre,'false' as seleccion from tb_preguntas where estatus = 'V' and folio not in ("+condicion+") order by nombre";
			System.out.println(comando);
			String basedatos="26",pintar="si";
			new Obj_tabla().Obj_Refrescar(tablaFiltro,modeloFitlro, columnasFiltro, comando, basedatos,pintar,checkboxFiltro);
	    }
		
		 public DefaultTableModel modeloFitlro = new DefaultTableModel(null, new String[]{"Folio","Pregunta","*"}){
			 @SuppressWarnings("rawtypes")
				Class[] types = new Class[]{
						java.lang.Object.class,
						java.lang.Object.class,
						java.lang.Boolean.class,
				};
				@SuppressWarnings({ "unchecked", "rawtypes" })
				public Class getColumnClass(int columnIndex) {
			         return types[columnIndex];
			     }
				public boolean isCellEditable(int fila, int columna){
					if(columna == 2)
						return true;
					return false;
				}
		    };
		
		    JTable tablaFiltro = new JTable(modeloFitlro);
			public JScrollPane scroll_tabla = new JScrollPane(tablaFiltro);
		
		@SuppressWarnings("rawtypes")
		private TableRowSorter trsfiltro;
		
		JTextField txtPreguntaFiltro = new Componentes().text(new JTextField(), "Busqueda De Pregunta",	20, "String");
		JButton btnAgregar = new JCButton("Agregar", "", "Azul");
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Cat_Filtro_De_Preguntas(String foliosPregunstasUsadas){
			
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/encuesta.png"));
			panel.setBorder(BorderFactory.createTitledBorder("Preguntas"));
			this.setTitle("Filtro De Preguntas");
			
			trsfiltro = new TableRowSorter(modelo); 
			tabla.setRowSorter(trsfiltro);
			
			int x = 15, y=30, w=100,l=20;
			panel.add(txtPreguntaFiltro).setBounds          (x	   ,y    ,410,l);
			panel.add(btnAgregar).setBounds          		(x+580 ,y    ,120,l);
			panel.add(scroll_tabla).setBounds           	(x     ,y+20 ,w*7,w*4);
			
			init_tablaFiltro(foliosPregunstasUsadas);
			agregar(tablaFiltro);
			
			btnAgregar.addActionListener(opAgregar);
			txtPreguntaFiltro.addKeyListener(opFiltroNombre);
			cont.add(panel);
			this.setSize(740,570);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	        this.addWindowListener(new WindowAdapter() {
	            public void windowOpened( WindowEvent e ){
	            	txtPreguntaFiltro.requestFocus();
	           }
	        });
		}
		
		ActionListener opAgregar = new ActionListener(){
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public void actionPerformed(ActionEvent e){
				int registrosAgregados=0;
				
				for(int i=0; i<tablaFiltro.getRowCount(); i++){
					
					if(Boolean.valueOf(tablaFiltro.getValueAt(i, 2).toString())){
						
						Vector vec = new Vector();
		        		vec.add(tablaFiltro.getValueAt(i, 0).toString());
		        		vec.add(tablaFiltro.getValueAt(i, 1).toString());
		        		modelo.addRow(vec);
		        		
						registrosAgregados++;
					}
				}
				
				if(registrosAgregados==0){
					JOptionPane.showMessageDialog(null, "No Se Seleccionaron Preguntas", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
					return;
				}else{
					dispose();
				}
			}
		};
		
		private void agregar(final JTable tbl) {
	        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
				@SuppressWarnings({ "rawtypes", "unchecked" })
				public void mouseClicked(MouseEvent e) {
		        	if(e.getClickCount()==2){
		        		int fila = tablaFiltro.getSelectedRow();
		        		
		        		Vector vec = new Vector();
		        		vec.add(tablaFiltro.getValueAt(fila, 0).toString());
		        		vec.add(tablaFiltro.getValueAt(fila, 1).toString());
		        		
		        		modelo.addRow(vec);
		        		dispose();
		        	}
		        }
	        });
	    }
		
		KeyListener opFiltroNombre = new KeyListener(){
			public void keyReleased(KeyEvent arg0) {
				int[] columnasFiltro = {1,2,3};
				new Obj_Filtro_Dinamico_Plus(tabla, txtPreguntaFiltro.getText().toUpperCase(),columnasFiltro);
			}
			public void keyTyped(KeyEvent arg0) {}
			public void keyPressed(KeyEvent arg0) {}		
		};

	}
	
	//TODO Filtro De cuestionarios
	public class Cat_Filtro_De_Cuestionarios extends JDialog{
		  Container cont = getContentPane();
		  JLayeredPane panel = new JLayeredPane();
		  Connexion con = new Connexion();
		  Runtime R = Runtime.getRuntime();
		 Obj_tabla  Objetotabla = new Obj_tabla();
			
			int columnas = 5,checkbox=-1;
			public void init_tabla_f(){
		    	this.tabla2.getColumnModel().getColumn(0).setMinWidth(90);	
		    	this.tabla2.getColumnModel().getColumn(1).setMinWidth(410);
		    	this.tabla2.getColumnModel().getColumn(2).setMinWidth(150);
		    	this.tabla2.getColumnModel().getColumn(3).setMinWidth(190);
		    	this.tabla2.getColumnModel().getColumn(4).setMinWidth(100);
		    	
				String comando="exec filtro_cuestionarios" ;
				String basedatos="26",pintar="si";
				Objetotabla.Obj_Refrescar(tabla2,modelo2, columnas, comando, basedatos,pintar,checkbox);
		    }
			
		  public DefaultTableModel modelo2 = new DefaultTableModel(null, new String[]{"Folio","Cuestionario","Clasificacion","Escala","Status"}){
			 @SuppressWarnings("rawtypes")
				Class[] types = new Class[]{
						java.lang.Object.class,
						java.lang.Object.class,
						java.lang.Object.class,
						java.lang.Object.class,
						java.lang.Object.class,
				};
				
				@SuppressWarnings({ "unchecked", "rawtypes" })
				public Class getColumnClass(int columnIndex) {
		         return types[columnIndex];
		     }
				public boolean isCellEditable(int fila, int columna){
					return false;
				}
		    };
		    
	    JTable tabla2 = new JTable(modelo2);
		public JScrollPane scroll_tabla = new JScrollPane(tabla2);
	    JScrollPane scrollAsignado = new JScrollPane(tabla2,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    
		@SuppressWarnings("rawtypes")
		private TableRowSorter trsfiltro;
		
		JTextField txtFiltrop = new Componentes().text(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<", 300, "String");
		Border blackline, etched, raisedbevel, loweredbevel, empty;
	    
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Cat_Filtro_De_Cuestionarios(){
			int ancho = 1024;//Toolkit.getDefaultToolkit().getScreenSize().width;
			int alto = Toolkit.getDefaultToolkit().getScreenSize().height-50;
			this.setSize(ancho, alto);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setTitle("Filtro De Busqueda De Cuestionarios");
			this.setModal(true);
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/lista-icono-7220-32.png"));
			blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
			panel.setBorder(BorderFactory.createTitledBorder(blackline,"Doble Click A El Cuestionario Deseado"));
			this.cont.add(panel);

			trsfiltro = new TableRowSorter(modelo2); 
			tabla2.setRowSorter(trsfiltro);
			txtFiltrop.setToolTipText("Filtro Por Producto");
			txtFiltrop.addKeyListener(opFiltro);

			int y = 20;
			panel.add(txtFiltrop).setBounds(15,y,500,20);
			panel.add(scrollAsignado).setBounds(15,y+=20,ancho-30,alto-70);
	        
			init_tabla_f();
			agregar(tabla2);
		}

		KeyListener opFiltro = new KeyListener(){
			@SuppressWarnings("unchecked")
			public void keyReleased(KeyEvent arg0) {
				int[] columnas ={0,1,2,3,4,5};
				new Obj_Filtro_Dinamico_Plus(tabla2 , txtFiltrop.getText().toString().trim().toUpperCase(), columnas  );
				trsfiltro.setRowFilter(RowFilter.regexFilter(txtFiltrop.getText(), 0));
			}
			public void keyTyped(KeyEvent arg0) {}
			public void keyPressed(KeyEvent arg0) {}	
	    };
		
		private void agregar(final JTable tbl) {
			tbl.addMouseListener(new MouseListener() {
				public void mouseReleased(MouseEvent e) {
						if(e.getClickCount() == 2){
							int fila_Select = tabla2.getSelectedRow();
			    			String folio =  tabla2.getValueAt(fila_Select, 0).toString().trim();
			    			dispose();
			    			txtFolio.setText(folio);
			    			btnBuscar.doClick();
						}
				}
				public void mousePressed(MouseEvent e) {}
				public void mouseExited(MouseEvent e) {}
				public void mouseEntered(MouseEvent e) {}
				public void mouseClicked(MouseEvent e) {}
			});
		}
	}
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Cuestionarios().setVisible(true);
		}catch(Exception e){	}
	}
	
}