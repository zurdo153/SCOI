package Cat_Evaluaciones;

import java.awt.Component;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

import Cat_Evaluaciones.Cat_Filtro_Nivel_Jerarquico;
import Cat_Lista_de_Raya.Cat_Puestos;
import Conexiones_SQL.Connexion;
import Obj_Evaluaciones.Obj_Nivel_Jerarquico;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.Componentes;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Nivel_Jerarquico extends JFrame {
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JTextField txtFolio = new JTextField();
	JTextField txtDescripcion = new JTextField();
	
//	JCheckBox chStatus = new JCheckBox("Status",true);
	
	JButton btnNuevo = new JButton("Nuevo");
	JButton btnModificar = new JButton("Modificar");
	JButton btnSalir = new JButton("Salir");
	JButton btnAgregar = new JButton("Agregar");
	JButton btnLimpiar = new JButton("Limpiar");
	JButton btnGuardar = new JButton("Guardar");
	JButton btnEliminar = new JButton("Remover");
	JButton btnBuscar = new JButton(new ImageIcon("Iconos/zoom_icon&16.png"));
	
	JButton btnAltaPuesto = new JButton("Puesto");
	JButton btnFiltro = new JButton(new ImageIcon("Iconos/filter_iconBlue&16.png"));
	JButton btnFiltroPuestosPrincipal 	= new JButton("Puestos Principal");
	JButton btnFiltroPuestosDependiente = new JButton("Puesto Dependiente");

	JTextField txtPuestoPrincipal = new JTextField();
	
	String lista[] ={"VIGENTE","CANCELADO"};
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmb_status = new JComboBox(lista);
	
	JTextField txtP_Dependiente = new Componentes().text(new JTextField(), "SDF", 80, "String");
	
	String lista3[] = new Obj_Establecimiento().Combo_Establecimiento();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmb_Establecimiento = new JComboBox(lista3);
	
	DefaultTableModel modelo = new DefaultTableModel(0,2)	{
		public boolean isCellEditable(int fila, int columna){
			if(columna < 0)
				return true;
			return false;
		}
	};
	
	JTable tabla = new JTable(modelo);
	JScrollPane panelScroll = new JScrollPane(tabla);
	
//	int valor_referencia=0;
	
	public void getContenedor(){
				
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/nivel_jerarquico_icon&16.png"));
		this.panel.setBorder(BorderFactory.createTitledBorder("Nivel Jerarquico"));	
		this.setTitle("Nivel Jerarquico");
		
		render();
		
		int y=30;
		
		this.panel.add(new JLabel("Folio:")).setBounds(20,y,50,20);
		this.panel.add(txtFolio).setBounds(140,y,100,20);
		this.panel.add(btnBuscar).setBounds(270,y,32,20);
		this.panel.add(btnFiltro).setBounds(305,y,32,20);
		this.panel.add(btnNuevo).setBounds(340,y,87,20);
		this.panel.add(btnAltaPuesto).setBounds(450,y,87,20);
		
		this.panel.add(new JLabel("Estatus:")).setBounds(20,y+=30,100,20);
		this.panel.add(cmb_status).setBounds(140,y,100,20);
		
		this.panel.add(new JLabel("Descripcion:")).setBounds(20,y+=30,100,20);
		this.panel.add(txtDescripcion).setBounds(140,y,340,20);
		
		this.panel.add(btnFiltroPuestosPrincipal).setBounds(350,y+=30,130,20);
		this.panel.add(new JLabel("Puesto Pricipal:")).setBounds(20,y,120,20);
		this.panel.add(txtPuestoPrincipal).setBounds(140,y,190,20);
		
		this.panel.add(new JLabel("Puesto Dependiente:")).setBounds(20,y+=30,120,20);
		this.panel.add(txtP_Dependiente).setBounds(140,y,190,20);
		this.panel.add(btnFiltroPuestosDependiente).setBounds(350,y,130,20);
		
		this.panel.add(new JLabel("Establecimiento:")).setBounds(20,y+=30,120,20);
		this.panel.add(cmb_Establecimiento).setBounds(140,y,190,20);
		
		this.panel.add(btnAgregar).setBounds(350,y,85,20);
		this.panel.add(btnEliminar).setBounds(450,y,85,20);
		
		this.panel.add(panelScroll).setBounds(20,y+=30,520,195);
		
		this.panel.add(btnSalir).setBounds(20,410,80,20);
		this.panel.add(btnLimpiar).setBounds(150,410,80,20);
		this.panel.add(btnModificar).setBounds(290,410,80,20);
		this.panel.add(btnGuardar).setBounds(430,410,80,20);
		
		
//		this.tabla.getColumnModel().getColumn(0).setHeaderValue("Puesto Dependiente");
//		this.tabla.getColumnModel().getColumn(0).setMinWidth(150);
//		this.tabla.getColumnModel().getColumn(0).setMinWidth(150);
//		this.tabla.getColumnModel().getColumn(1).setHeaderValue("Establecimiento");
//		this.tabla.getColumnModel().getColumn(1).setMinWidth(150);
//		this.tabla.getColumnModel().getColumn(1).setMinWidth(150);
		
		this.btnSalir.addActionListener(salir);
		this.txtFolio.addKeyListener(guardaAction);
		this.btnEliminar.addActionListener(opRemover);
		this.btnNuevo.addActionListener(opNuevo);
		this.btnLimpiar.addActionListener(opLimpiar);
		this.txtFolio.addKeyListener(valida);
		this.btnAgregar.addActionListener(opAgregar);
		
		this.btnGuardar.addActionListener(guardar);
		this.btnBuscar.addActionListener(buscar);
		this.btnModificar.addActionListener(modifica);
		
		this.btnAltaPuesto.addActionListener(puesto);
		this.btnFiltro.addActionListener(filtro);
		this.btnFiltroPuestosPrincipal.addActionListener(opFiltroPuestos);
		this.btnFiltroPuestosDependiente.addActionListener(opFiltroPuestos);
		
		this.txtDescripcion.setEditable(false);
		this.btnFiltroPuestosDependiente.setEnabled(false);
		this.txtPuestoPrincipal.setEditable(false);
		this.txtP_Dependiente.setEditable(false);
		
		this.cmb_Establecimiento.setEnabled(false);
		this.cmb_status.setEnabled(false);
		this.status_botones(false);
		
		this.cont.add(panel);
		
		this.setSize(560,480);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	public void render(){
		tabla.getTableHeader().setReorderingAllowed(false) ;
		tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		tabla.getColumnModel().getColumn(0).setMaxWidth(250);
		tabla.getColumnModel().getColumn(0).setMinWidth(250);
		tabla.getColumnModel().getColumn(1).setMaxWidth(250);
		tabla.getColumnModel().getColumn(1).setMinWidth(250);
		
		tabla.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
		tabla.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
	}
	
	public void status_botones(boolean variable){
		btnFiltroPuestosPrincipal.setEnabled(variable);
		btnAltaPuesto.setEnabled(variable);
		btnAgregar.setEnabled(variable);
		btnEliminar.setEnabled(variable);		
	}
	
	public Cat_Nivel_Jerarquico(){
		getContenedor();
	}
	
	public Cat_Nivel_Jerarquico(String folio){
		
		getContenedor();
		
		Obj_Nivel_Jerarquico nivelbuscar = new Obj_Nivel_Jerarquico().buscar(Integer.parseInt(folio));
			txtFolio.setText(nivelbuscar.getFolio()+"");
			txtDescripcion.setText(nivelbuscar.getDescripcion()+"");
			txtPuestoPrincipal.setText(nivelbuscar.getPuesto_principal());
		
		getTabla(Integer.parseInt(folio));
	}
	
	ActionListener opFiltroPuestos = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			
			
			new Cat_Filtro_puestos_jerarquico(e.getActionCommand()).setVisible(true);
		}
		
	};
	
	ActionListener guardar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			status_botones(false);
			if(txtFolio.getText().equals("")){
				JOptionPane.showMessageDialog(null, "El Folio Es Requerido", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
				return;
			}else{
				if(validacampos().equals("")){
				
							Obj_Nivel_Jerarquico nivelgerarquico = new Obj_Nivel_Jerarquico().buscar(Integer.parseInt(txtFolio.getText()));
							
							if(nivelgerarquico.getFolio() == Integer.parseInt(txtFolio.getText())){
								if(JOptionPane.showConfirmDialog(null, "El registro ya existe, ¿desea cambiarlo?") == 0)
								{
									Obj_Nivel_Jerarquico gerarquico = new Obj_Nivel_Jerarquico();
										
									gerarquico.setFolio(Integer.parseInt(txtFolio.getText()));
									gerarquico.setDescripcion(txtDescripcion.getText().toUpperCase());
									
									gerarquico.setPuesto_principal(txtPuestoPrincipal.getText());
									gerarquico.setPuesto_dependiente(txtP_Dependiente.getText());
									gerarquico.setEstablecimiento(cmb_Establecimiento.getSelectedItem().toString());
									
									String[] arreglo = new String[2];
									
									arreglo[0] =txtP_Dependiente.getText();
									arreglo[1] = cmb_Establecimiento.getSelectedItem()+"";
										
										if(gerarquico.actualizar2(listadatos())){
												limpiaGuardar();
												////////////////  limpia la tabla antes de acer otra busqueda   ////////////////
												/**/	    while(modelo.getRowCount() > 0){modelo.removeRow(0);}			/**/
												/**/	   		 getTabla(Integer.parseInt(txtFolio.getText()));			/**/
												////////////////////////////////////////////////////////////////////////////////
												JOptionPane.showMessageDialog(null,"El registro se actualizo exitosamente!","Aviso",JOptionPane.INFORMATION_MESSAGE);
												return;
										}else{
												JOptionPane.showMessageDialog(null,"Ocurrió un problema al intentar guardar el registro!","Error",JOptionPane.ERROR_MESSAGE);
												return;
											}
		//								}
								}
							}else{	
								Obj_Nivel_Jerarquico nivelgerarquicoDescripcion = new Obj_Nivel_Jerarquico().buscar(txtDescripcion.getText());
								
								if(nivelgerarquicoDescripcion.getFolio()>0){
									JOptionPane.showMessageDialog(null, "La Descripcion Ya Existe, Intente Con Otra", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
									return;
								}else{
									Obj_Nivel_Jerarquico gerarquico = new Obj_Nivel_Jerarquico().buscar(Integer.parseInt(txtFolio.getText()));
								
								gerarquico.setFolio(Integer.parseInt(txtFolio.getText()));
								gerarquico.setDescripcion(txtDescripcion.getText().toUpperCase());
								
								gerarquico.setPuesto_principal(txtPuestoPrincipal.getText());
								gerarquico.setPuesto_dependiente(txtP_Dependiente.getText());
								gerarquico.setEstablecimiento(cmb_Establecimiento.getSelectedItem().toString());
								
								String[] arreglo = new String[2];
								
								arreglo[0] =txtP_Dependiente.getText();
								arreglo[1] = cmb_Establecimiento.getSelectedItem()+"";
								
									if(gerarquico.guardar_multiple2(listadatos())){
										btnFiltroPuestosPrincipal.setEnabled(false);
											limpiaGuardar();
											////////////////  limpia la tabla antes de acer otra busqueda   ////////////////
											/**/	    while(modelo.getRowCount() > 0){modelo.removeRow(0);}			/**/
											/**/	   		 getTabla(Integer.parseInt(txtFolio.getText()));			/**/
											////////////////////////////////////////////////////////////////////////////////
											JOptionPane.showMessageDialog(null,"El registro se guardó exitosamente!","Aviso",JOptionPane.INFORMATION_MESSAGE);
											return;
									}else{
											JOptionPane.showMessageDialog(null,"Ocurrió un problema al intentar guardar el registro!","Error",JOptionPane.ERROR_MESSAGE);
											return;
										}
							}
					}
				}else{
					JOptionPane.showMessageDialog(null, "los siguientes campos son requeridos: \n"+validacampos(),"Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
					return;
				}
			}
		}
	};
	
	public  String[][] listadatos()
	{
		String[][] matriz=new String[tabla.getRowCount()][2];
		for (int i = 0; i < tabla.getRowCount(); i++) {
			for (int j = 0; j < 2; j++) {
				matriz[i][j]=modelo.getValueAt(i, j).toString();
			}
		}
		return matriz;
	}
	
	public String validacampos(){
		String error="";
		if (txtDescripcion.getText().equals("")){error+="Descripcion\n";}
		if (txtPuestoPrincipal.getText().equals("")) {error+="Puesto Principal\n";}
		if ((tabla.getRowCount()==0)) {error+="No hay ningun valor agregado en la tabla";}
		return error;
	}
	
	ActionListener opLimpiar = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			 limpia();
			 	while(modelo.getRowCount() > 0){
				 	modelo.removeRow(0);
			 	}
		}
	};
	
	ActionListener opRemover = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			if(tabla.isRowSelected(tabla.getSelectedRow())){
				int fila = tabla.getSelectedRow();
				String nombre =  tabla.getValueAt(fila, 0).toString().trim();
				String establecimiento =  tabla.getValueAt(fila, 1).toString().trim();

				if(JOptionPane.showConfirmDialog(null, "¿desea eliminar el puesto dependiente seleccionado?","aviso",JOptionPane.YES_NO_OPTION) == 0){
					if(new Obj_Nivel_Jerarquico().buscarYborraPuestoDependiente(nombre, Integer.parseInt(txtFolio.getText()),establecimiento)){
						JOptionPane.showMessageDialog(null,"Se eliminó exitosamente","Exito", JOptionPane.INFORMATION_MESSAGE);
						modelo.removeRow(fila);
					}else{
						JOptionPane.showMessageDialog(null,"No se pudo eliminar el registro","Error",JOptionPane.ERROR_MESSAGE);
					}
				}
			}else{
				JOptionPane.showMessageDialog(null,"No ha seleccionado ningún renglón", "Aviso!", JOptionPane.WARNING_MESSAGE);
			}
		}
	};
	
	ActionListener opAgregar = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			if(txtP_Dependiente.getText().equals(""))
			{
				JOptionPane.showMessageDialog(null, "Favor de seleccionar un puesto","Aviso",JOptionPane.INFORMATION_MESSAGE);
				return;
			}else{
				if (cmb_Establecimiento.getSelectedItem().equals("Todos")) {
						JOptionPane.showMessageDialog(null, "Favor de seleccionar un establecimiento","Aviso",JOptionPane.INFORMATION_MESSAGE);
						return;
				}else{
					String[] arreglo = new String[2];
					
					arreglo[0] =txtP_Dependiente.getText();
					arreglo[1] = cmb_Establecimiento.getSelectedItem()+"";
					
					modelo.addRow(arreglo);
					
					txtP_Dependiente.setText("");
					cmb_Establecimiento.setSelectedIndex(0);
				}
			}
		}
	};

	ActionListener opNuevo = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			txtFolio.setText(new Obj_Nivel_Jerarquico().Nuevo());
			txtDescripcion.requestFocus();
			
			paneltrue();
			panelselectrue();
			status_botones(true);
			txtDescripcion.setEnabled(true);
		}
	};
	
	ActionListener buscar = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) 
		{
			if(txtFolio.getText().equals(""))
			{
				JOptionPane.showMessageDialog(null, "Ingrese el No. de Folio","Error",JOptionPane.WARNING_MESSAGE);
				return;
			}else{
				Obj_Nivel_Jerarquico nivelbuscar = new Obj_Nivel_Jerarquico().buscar(Integer.parseInt(txtFolio.getText()));
				if(nivelbuscar.getFolio()!=0)
				{
					txtFolio.setText(nivelbuscar.getFolio()+"");
					txtDescripcion.setText(nivelbuscar.getDescripcion()+"");
					txtPuestoPrincipal.setText(nivelbuscar.getPuesto_principal());
					
					if(nivelbuscar.isStatus() == true){cmb_status.setSelectedItem("VIGENTE");}
					
					txtDescripcion.setEditable(true);
					btnFiltroPuestosDependiente.setEnabled(true);
					cmb_Establecimiento.setEnabled(true);
				    cmb_status.setEnabled(true);
//				    chStatus.setSelected(true);
				    
				    txtP_Dependiente.setText("");
				    cmb_Establecimiento.setSelectedIndex(0);
				    
			////////////////  limpia la tabla antes de acer otra busqueda   ////////////////
			/**/	    while(modelo.getRowCount() > 0){modelo.removeRow(0);}			/**/
			/**/	   		 getTabla(Integer.parseInt(txtFolio.getText()));			/**/
			////////////////////////////////////////////////////////////////////////////////
			btnFiltroPuestosPrincipal.setEnabled(false);
					panelfalse();
				}
				else{
					JOptionPane.showMessageDialog(null,"El Folio no existe","Aviso",JOptionPane.ERROR_MESSAGE);
					return;
				}
			}     
		}
	};
	
	public void getTabla(int folio){
		String todos1 = "exec sp_select_nivel_jerarquico "+folio;
		Statement stmt = null;
		ResultSet rs;
		Connexion con = new Connexion();
		try {
			stmt = con.conexion().createStatement();
			rs = stmt.executeQuery(todos1);
			Object[] vector = new Object[2];
			while(rs.next()){
				vector[0] = (rs.getString(2));
				vector[1] = (rs.getString(3));
//				vector[2] = (rs.getString(3));
				modelo.addRow(vector);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	public void panelselectrue()
	{
		txtPuestoPrincipal.setText("");
		txtP_Dependiente.setText("");
		cmb_Establecimiento.setSelectedIndex(0);
		txtDescripcion.setText("");
		cmb_status.setEnabled(false);
		 while(modelo.getRowCount() > 0){
	    	  modelo.removeRow(0);
		}
	}
	
	public void panelfalse()
	{
		txtDescripcion.setEditable(false);
		txtPuestoPrincipal.setEditable(false);
		btnFiltroPuestosDependiente.setEnabled(false);
		cmb_Establecimiento.setEnabled(false);
	}
	
	public void panelfalseGuardar()
	{
		txtFolio.setEditable(false);
		txtDescripcion.setEditable(false);
		txtPuestoPrincipal.setEditable(false);
		btnFiltroPuestosDependiente.setEnabled(true);
		cmb_Establecimiento.setEnabled(true);
	}
	
	public void paneltrue()
	{
		txtDescripcion.setEditable(true);
		btnFiltroPuestosDependiente.setEnabled(true);
		cmb_Establecimiento.setEnabled(true);
		txtFolio.setEditable(false);
	}
	
	ActionListener modifica = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) 
		{
			paneltrue();
			txtPuestoPrincipal.setEditable(false);
			txtDescripcion.setEnabled(false);
			status_botones(true);
			btnFiltroPuestosPrincipal.setEnabled(false);
		}
	};
	
	ActionListener puesto = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) 
		{
			new Cat_Puestos().setVisible(true);
		}
	};
	
	ActionListener filtro = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) 
		{
			dispose();
			new Cat_Filtro_Nivel_Jerarquico().setVisible(true);
		}
	};
	
	ActionListener salir = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			dispose();
		}
	};
	
	KeyListener valida = new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e){
				char caracter = e.getKeyChar();
				int limite=10;

				if(((caracter < '0') ||
			        (caracter > '9')) &&
			        (caracter != KeyEvent.VK_BACK_SPACE)){
			    	e.consume(); 
			    }
					if (txtFolio.getText().length()== limite)
				     e.consume();
			}
			@Override
			public void keyReleased(KeyEvent e) {	
			}
			@Override
			public void keyPressed(KeyEvent e) {}
		};
	
	KeyListener guardaAction = new KeyListener() {
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
				txtFolio.requestFocus();
			}
		}
	};
	
	public void limpia() {
		txtFolio.setText("");
		txtDescripcion.setText("");
		txtP_Dependiente.setText("");
		txtPuestoPrincipal.setText("");
		cmb_Establecimiento.setSelectedIndex(0);
	    cmb_status.setEnabled(false);
	    
	    txtFolio.requestFocus();
	    txtFolio.setEditable(true);
	    panelfalse();
	}
	
	public void limpiaGuardar() {
		txtP_Dependiente.setText("");
		cmb_Establecimiento.setSelectedIndex(0);
	    cmb_status.setEnabled(false);
//	    txtP_Dependiente.requestFocus();
	    txtFolio.setEditable(true);
	    panelfalseGuardar();
	}
	
	public class Cat_Filtro_puestos_jerarquico extends JFrame {
		Container cont = getContentPane();
		JLayeredPane campo = new JLayeredPane();
		
		DefaultTableModel model = new DefaultTableModel(0,3){
			public boolean isCellEditable(int fila, int columna){
				if(columna < 0)
					return true;
				return false;
			}
		};
		
		JTable tabla = new JTable(model);
		
		@SuppressWarnings("rawtypes")
		private TableRowSorter trsfiltro;
		
		JTextField txtFolio = new JTextField();
		JTextField txtNombre_Completo = new JTextField();
		
		String botonPresionado = "";
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Cat_Filtro_puestos_jerarquico(String boton)	{
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/filter_icon&16.png"));
			this.setTitle("Filtro de Puestos");
			campo.setBorder(BorderFactory.createTitledBorder("Filtro de Puestos"));
			trsfiltro = new TableRowSorter(model); 
			tabla.setRowSorter(trsfiltro);  
			
			botonPresionado = boton;
			
			campo.add(getPanelTabla()).setBounds(15,42,400,420);
			
			campo.add(txtFolio).setBounds(15,20,48,20);
			campo.add(txtNombre_Completo).setBounds(64,20,229,20);
			
			agregar(tabla);
			
			cont.add(campo);
			
			txtFolio.addKeyListener(opFiltroFolio);
			txtNombre_Completo.addKeyListener(opFiltroNombre);
			
			this.setSize(435,520);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		}
		
		private void agregar(final JTable tbl) {
	        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
		        public void mouseClicked(MouseEvent e) {
		        	if(e.getClickCount() == 2){
		        		int fila = tabla.getSelectedRow();
		    			Object nombre =  tabla.getValueAt(fila, 1).toString().trim();
		    			dispose();
		    			
					if(botonPresionado.equals("Puestos Principal")){
						txtPuestoPrincipal.setText(nombre+"");    				
	    			}
					if(botonPresionado.equals("Puesto Dependiente")){
						txtP_Dependiente.setText(nombre+""); 
					}
					if(botonPresionado.equals("")){
						txtPuestoPrincipal.setText(nombre+""); 
					}
		    			
		    			
		        	}
		        }
	        });
	    }
		
		KeyListener opFiltroFolio = new KeyListener(){
			@SuppressWarnings("unchecked")
			public void keyReleased(KeyEvent arg0) {
				trsfiltro.setRowFilter(RowFilter.regexFilter(txtFolio.getText(), 0));
			}
			public void keyTyped(KeyEvent arg0) {
				char caracter = arg0.getKeyChar();
				if(((caracter < '0') ||
					(caracter > '9')) &&
				    (caracter != KeyEvent.VK_BACK_SPACE)){
					arg0.consume(); 
				}	
			}
			public void keyPressed(KeyEvent arg0) {}		
		};
		
		KeyListener opFiltroNombre = new KeyListener(){
			@SuppressWarnings("unchecked")
			public void keyReleased(KeyEvent arg0) {
				trsfiltro.setRowFilter(RowFilter.regexFilter(txtNombre_Completo.getText().toUpperCase().trim(), 1));
			}
			public void keyTyped(KeyEvent arg0) {}
			public void keyPressed(KeyEvent arg0) {}		
		};
		
		
		
	   	private JScrollPane getPanelTabla()	{		
	   		
	   		tabla.getTableHeader().setReorderingAllowed(false) ;
			tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			
			tabla.getColumnModel().getColumn(0).setHeaderValue("Folio");
			tabla.getColumnModel().getColumn(0).setMaxWidth(50);
			tabla.getColumnModel().getColumn(0).setMinWidth(50);
			tabla.getColumnModel().getColumn(1).setHeaderValue("Puesto");
			tabla.getColumnModel().getColumn(1).setMaxWidth(230);
			tabla.getColumnModel().getColumn(1).setMinWidth(230);
			tabla.getColumnModel().getColumn(2).setHeaderValue("Abreviatura");
			tabla.getColumnModel().getColumn(2).setMaxWidth(100);
			tabla.getColumnModel().getColumn(2).setMinWidth(100);
			
			tabla.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
			tabla.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
			tabla.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12));
			
			Statement s;
			ResultSet rs;
			try {
				s = new Connexion().conexion().createStatement();
				rs = s.executeQuery("exec sp_filtro_puesto_jerarquico");
				
				while (rs.next()) { 
				   String [] fila = new String[9];
				   fila[0] = rs.getString(1)+"  ";
				   fila[1] = "   "+rs.getString(2);
				   fila[2] = "   "+rs.getString(3).trim();
	
				   model.addRow(fila); 
				}	
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			 JScrollPane scrol = new JScrollPane(tabla);
		    return scrol; 
		}
	   	
	}
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Nivel_Jerarquico().setVisible(true);
			}catch(Exception e){
				System.err.println("Error :"+ e.getMessage());
			}
		
	}
}
