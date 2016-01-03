package Cat_Evaluaciones;

import java.awt.Container;
import java.awt.Event;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Cat_Evaluaciones.Cat_Filtro_Nivel_Jerarquico;
import Cat_Lista_de_Raya.Cat_Puestos;
import Conexiones_SQL.Connexion;
import Obj_Evaluaciones.Obj_Nivel_Jerarquico;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.Componentes;
import Obj_Principal.JCTextField;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Nivel_Jerarquico extends JFrame {
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JTextField txtFolio = new Componentes().text(new JCTextField(), "Folio", 100, "Int");
	JTextField txtDescripcion = new Componentes().text(new JCTextField(), "Descripcion", 100, "String");
	
	JButton btnBuscar = new JButton(new ImageIcon("Imagen/buscar.png"));
	JButton btnFiltro = new JButton(new ImageIcon("Imagen/Filter-List-icon16.png"));
	JButton btnNuevo = new JButton("Nuevo",new ImageIcon("imagen/Nuevo.png"));
	JButton btnSalir = new JButton("Salir",new ImageIcon("imagen/salir16.png"));
	JButton btnGuardar = new JButton("Guardar",new ImageIcon("imagen/Guardar.png"));
	JButton btnDeshacer = new JButton("Deshacer",new ImageIcon("imagen/deshacer16.png"));
	JButton btnEditar = new JButton("Editar",new ImageIcon("imagen/editara.png"));
	JButton btnEliminar = new JButton("Quitar", new ImageIcon("imagen/eliminar-bala-icono-7773-32.png"));
	
	JButton btnAgregar = new JButton("Agregar",new ImageIcon("imagen/double-arrow-icone-3883-16.png"));

	JButton btnAltaPuesto = new JButton("Puesto");
	JButton btnFiltroPuestosPrincipal 	= new JButton("Puestos Principal");
	JButton btnFiltroPuestosDependiente = new JButton("Puesto Dependiente");

	JTextField txtFolioPuestoPrincipal = new Componentes().text(new JCTextField(), "Folio", 5, "Int");
	JTextField txtPuestoPrincipal = new Componentes().text(new JCTextField(), "Puesto Principal", 300, "String");
	JTextField txtFolioP_Dependiente = new Componentes().text(new JCTextField(), "Folio", 5, "Int");
	JTextField txtP_Dependiente = new Componentes().text(new JCTextField(), "Puesto Dependiente", 300, "String");
	
	String lista[] ={"VIGENTE","CANCELADO"};
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmb_status = new JComboBox(lista);
	
	String lista3[] = new Obj_Establecimiento().Combo_Establecimiento_Todos();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmb_Establecimiento = new JComboBox(lista3);
	
	DefaultTableModel modeloP = new DefaultTableModel(0,3)	{
		public boolean isCellEditable(int fila, int columna){
			if(columna < 0)
				return true;
			return false;
		}
	};
	
	JTable tablaP = new JTable(modeloP);
	JScrollPane panelScroll = new JScrollPane(tablaP);
	
	public void getContenedor(){
		this.setSize(560,480);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);		
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/plan-de-organizacion-de-la-red-de-sitio-icono-5788-32.png"));
		this.panel.setBorder(BorderFactory.createTitledBorder("Nivel Jerarquico"));	
		this.setTitle("Nivel Jerarquico");
		
		render();
		
		int y=30,width=100,height=20;
		
		this.panel.add(new JLabel("Folio:")).setBounds(20,y,50,height);
		this.panel.add(txtFolio).setBounds(130,y,width,height);
		this.panel.add(btnBuscar).setBounds(235,y,height,height);
		this.panel.add(btnFiltro).setBounds(255,y,height,height);
		this.panel.add(btnNuevo).setBounds(300,y,width,height);
		this.panel.add(btnAltaPuesto).setBounds(430,y,width,height);
		
		this.panel.add(new JLabel("Estatus:")).setBounds(20,y+=30,width,height);
		this.panel.add(cmb_status).setBounds(130,y,width,height);
		
		this.panel.add(new JLabel("Descripcion:")).setBounds(20,y+=30,width,height);
		this.panel.add(txtDescripcion).setBounds(130,y,410,height);
		
	
		this.panel.add(new JLabel("Puesto Principal:")).setBounds(20,y+=30,120,height);
		this.panel.add(txtFolioPuestoPrincipal).setBounds(130,y,40,height);
		this.panel.add(txtPuestoPrincipal).setBounds(170,y,230,height);
		this.panel.add(btnFiltroPuestosPrincipal).setBounds(410,y,130,height);
		
		this.panel.add(new JLabel("Puesto Dependiente:")).setBounds(20,y+=30,120,height);
		this.panel.add(txtFolioP_Dependiente).setBounds(130,y,40,height);
		this.panel.add(txtP_Dependiente).setBounds(170,y,230,height);
		this.panel.add(btnFiltroPuestosDependiente).setBounds(410,y,130,height);
		
		this.panel.add(new JLabel("Establecimiento:")).setBounds(20,y+=30,120,height);
		this.panel.add(cmb_Establecimiento).setBounds(130,y,190,height);
		
		this.panel.add(btnAgregar).setBounds(340,y,width,height);
		this.panel.add(btnEliminar).setBounds(440,y,width,height);
		
		this.panel.add(panelScroll).setBounds(20,y+=30,520,195);
		
		this.panel.add(btnSalir).setBounds(20,410,width,height);
		this.panel.add(btnDeshacer).setBounds(150,410,width,height);
		this.panel.add(btnEditar).setBounds(290,410,width,height);
		this.panel.add(btnGuardar).setBounds(430,410,width,height);
		
		this.btnSalir.addActionListener(salir);
		this.txtFolio.addKeyListener(guardaAction);
		this.btnEliminar.addActionListener(opRemover);
		this.btnNuevo.addActionListener(opNuevo);
		this.btnDeshacer.addActionListener(opLimpiar);
		this.btnAgregar.addActionListener(opAgregar);
		
		this.btnGuardar.addActionListener(guardar);
		this.btnBuscar.addActionListener(buscar);
		this.btnEditar.addActionListener(modifica);
		
		this.btnAltaPuesto.addActionListener(puesto);
		this.btnFiltro.addActionListener(filtro);
		this.btnFiltroPuestosPrincipal.addActionListener(opFiltroPuestos);
		this.btnFiltroPuestosDependiente.addActionListener(opFiltroPuestos);
		
		this.txtDescripcion.setEditable(false);
		this.btnFiltroPuestosDependiente.setEnabled(false);
		this.txtPuestoPrincipal.setEditable(false);
		this.txtFolioPuestoPrincipal.setEditable(false);
		this.txtP_Dependiente.setEditable(false);
		this.txtFolioP_Dependiente.setEditable(false);
		
		this.cmb_Establecimiento.setEnabled(false);
		this.cmb_status.setEnabled(false);
		this.status_botones(false);
		
		this.cont.add(panel);
		
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_N,Event.CTRL_MASK),"guardar");
             getRootPane().getActionMap().put("guardar", new AbstractAction(){
                 public void actionPerformed(ActionEvent e)
                 {                 	    btnNuevo.doClick();           	    }
            });
             
	     //deshacer con escape
         getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
           getRootPane().getActionMap().put("escape", new AbstractAction(){
          public void actionPerformed(ActionEvent e)
          {                btnDeshacer.doClick();           	    }
      });
	}
	
	public void render(){
		tablaP.getTableHeader().setReorderingAllowed(false) ;
		tablaP.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		
		tablaP.getColumnModel().getColumn(0).setHeaderValue("Folio Puesto");
		tablaP.getColumnModel().getColumn(0).setMinWidth(80);
		tablaP.getColumnModel().getColumn(0).setMaxWidth(80);
		tablaP.getColumnModel().getColumn(1).setHeaderValue("Puesto Dependiente");
		tablaP.getColumnModel().getColumn(1).setMinWidth(367);
		tablaP.getColumnModel().getColumn(1).setMaxWidth(667);
		tablaP.getColumnModel().getColumn(2).setHeaderValue("Establecimiento");
		tablaP.getColumnModel().getColumn(2).setMinWidth(150);
		tablaP.getColumnModel().getColumn(2).setMaxWidth(350);
		
		tablaP.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12));
		tablaP.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
		tablaP.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
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
			txtFolioPuestoPrincipal.setText(nivelbuscar.getFolio_puesto_principal()+"");
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
				JOptionPane.showMessageDialog(null, "El Folio Es Requerido Para Hacer Una Busqueda", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
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
									
									gerarquico.setFolio_puesto_principal(Integer.valueOf(txtFolioPuestoPrincipal.getText()));
									gerarquico.setPuesto_principal(txtPuestoPrincipal.getText());
									gerarquico.setFoliopuesto_dependiente(Integer.valueOf(txtFolioP_Dependiente.getText()));
									gerarquico.setPuesto_dependiente(txtP_Dependiente.getText());
									gerarquico.setEstablecimiento(cmb_Establecimiento.getSelectedItem().toString());
									
//									String[] arreglo = new String[2];
//									
//									arreglo[0] =txtP_Dependiente.getText();
//									arreglo[1] = cmb_Establecimiento.getSelectedItem()+"";
										
										if(gerarquico.actualizar2(listadatos())){
												limpiaGuardar();
												/**/	   		 getTabla(Integer.parseInt(txtFolio.getText()));			/**/
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
								
								gerarquico.setFolio_puesto_principal(Integer.valueOf(txtFolioPuestoPrincipal.getText()));
								gerarquico.setPuesto_principal(txtPuestoPrincipal.getText());
								gerarquico.setFoliopuesto_dependiente(Integer.valueOf(txtFolioP_Dependiente.getText()));
								gerarquico.setPuesto_dependiente(txtP_Dependiente.getText());
								gerarquico.setEstablecimiento(cmb_Establecimiento.getSelectedItem().toString());
								
//								String[] arreglo = new String[2];
//								
//								arreglo[0] =txtP_Dependiente.getText();
//								arreglo[1] = cmb_Establecimiento.getSelectedItem()+"";
								
									if(gerarquico.guardar_multiple2(listadatos())){
										btnFiltroPuestosPrincipal.setEnabled(false);
											limpiaGuardar();
											/**/	   		 getTabla(Integer.parseInt(txtFolio.getText()));			/**/
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
		String[][] matriz=new String[tablaP.getRowCount()][3];
		for (int i = 0; i < tablaP.getRowCount(); i++) {
			for (int j = 0; j < 3; j++) {
				matriz[i][j]=modeloP.getValueAt(i, j).toString();
			}
		}
		return matriz;
	}
	
	public String validacampos(){
		String error="";
		if (txtDescripcion.getText().equals("")){error+="Descripcion\n";}
		if (txtPuestoPrincipal.getText().equals("")) {error+="Puesto Principal\n";}
		if ((tablaP.getRowCount()==0)) {error+="No hay ningun valor agregado en la tabla";}
		return error;
	}
	
	ActionListener opLimpiar = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			 limpia();
			 modeloP.setRowCount(0);
		}
	};
	
	ActionListener opRemover = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			if(tablaP.isRowSelected(tablaP.getSelectedRow())){
				int fila = tablaP.getSelectedRow();
				String nombre =  tablaP.getValueAt(fila, 0).toString().trim();
				String establecimiento =  tablaP.getValueAt(fila, 1).toString().trim();

				if(JOptionPane.showConfirmDialog(null, "¿desea eliminar el puesto dependiente seleccionado?","aviso",JOptionPane.YES_NO_OPTION) == 0){
					if(new Obj_Nivel_Jerarquico().buscarYborraPuestoDependiente(nombre, Integer.parseInt(txtFolio.getText()),establecimiento)){
						JOptionPane.showMessageDialog(null,"Se eliminó exitosamente","Exito", JOptionPane.INFORMATION_MESSAGE);
						modeloP.removeRow(fila);
					}else{
						JOptionPane.showMessageDialog(null,"No se pudo eliminar el registro","Error",JOptionPane.ERROR_MESSAGE);
					}
				}
			}else{
				JOptionPane.showMessageDialog(null, "Necesita Seleccionar Un Renglom Primero De La Tabla", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
			}
		}
	};
	
	ActionListener opAgregar = new ActionListener(){
		public void actionPerformed(ActionEvent arg0) {
			if(txtP_Dependiente.getText().equals(""))
			{
				JOptionPane.showMessageDialog(null, "Necesita Agregar Un Puesto", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}else{
//				if (Integer.valueOf(cmb_Establecimiento.getSelectedIndex())==0){
//					JOptionPane.showMessageDialog(null, "Necesita Seleccionar Un Establecimiento", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
//					cmb_Establecimiento.requestFocus();
//					return;	
//				}else{
					String[] arreglo = new String[3];
					
					arreglo[0] =txtFolioP_Dependiente.getText();
					arreglo[1] =txtP_Dependiente.getText();
					arreglo[2] = cmb_Establecimiento.getSelectedItem()+"";
					
					modeloP.addRow(arreglo);
					
					txtFolioP_Dependiente.setText("");
					txtP_Dependiente.setText("");
					cmb_Establecimiento.setSelectedIndex(0);
//				}
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
				JOptionPane.showMessageDialog(null, "Ingrese Un Folio Primero Para Buscar", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				txtFolio.requestFocus();
				return;
			}else{
				Obj_Nivel_Jerarquico nivelbuscar = new Obj_Nivel_Jerarquico().buscar(Integer.parseInt(txtFolio.getText()));
				if(nivelbuscar.getFolio()!=0)
				{
					txtFolio.setText(nivelbuscar.getFolio()+"");
					txtDescripcion.setText(nivelbuscar.getDescripcion()+"");
					txtFolioPuestoPrincipal.setText(nivelbuscar.getFolio_puesto_principal()+"");
					txtPuestoPrincipal.setText(nivelbuscar.getPuesto_principal());
					
					if(nivelbuscar.isStatus() == true){cmb_status.setSelectedItem("VIGENTE");}
					
					txtDescripcion.setEditable(true);
					btnFiltroPuestosDependiente.setEnabled(true);
					cmb_Establecimiento.setEnabled(true);
				    cmb_status.setEnabled(true);
				    
				    txtP_Dependiente.setText("");
				    cmb_Establecimiento.setSelectedIndex(0);
				    
				    getTabla(Integer.parseInt(txtFolio.getText()));
			        btnFiltroPuestosPrincipal.setEnabled(false);
					panelfalse();
				}
				else{
					JOptionPane.showMessageDialog(null, "El Folio Buscado No Existe", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
					txtFolio.setText("");
					txtFolio.requestFocus();
					return;
				}
			}     
		}
	};
	
	public void getTabla(int folio){
		modeloP.setRowCount(0);
		String todos1 = "exec sp_select_nivel_jerarquico "+folio;
		Statement stmt = null;
		ResultSet rs;
		Connexion con = new Connexion();
		try {
			stmt = con.conexion().createStatement();
			rs = stmt.executeQuery(todos1);
			Object[] vector = new Object[3];
			while(rs.next()){
				vector[0] = (rs.getString(2));
				vector[1] = (rs.getString(3));
				vector[2] = (rs.getString(4));
				modeloP.addRow(vector);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	public void panelselectrue()
	{
		txtFolioPuestoPrincipal.setText("");
		txtPuestoPrincipal.setText("");
		txtFolioP_Dependiente.setText("");
		txtP_Dependiente.setText("");
		cmb_Establecimiento.setSelectedIndex(0);
		txtDescripcion.setText("");
		cmb_status.setEnabled(false);
		 while(modeloP.getRowCount() > 0){
	    	  modeloP.removeRow(0);
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
		txtFolioP_Dependiente.setText("");
		txtP_Dependiente.setText("");
		txtFolioPuestoPrincipal.setText("");
		txtPuestoPrincipal.setText("");
		cmb_Establecimiento.setSelectedIndex(0);
	    cmb_status.setEnabled(false);
	    btnAgregar.setEnabled(false);
	    btnEliminar.setEnabled(false);
	    btnFiltroPuestosDependiente.setEnabled(false);
	    btnFiltroPuestosPrincipal.setEnabled(false);
	    txtFolio.requestFocus();
	    txtFolio.setEditable(true);
	    panelfalse();
	}
	
	public void limpiaGuardar() {
		txtP_Dependiente.setText("");
		cmb_Establecimiento.setSelectedIndex(0);
	    cmb_status.setEnabled(false);
	    txtPuestoPrincipal.setEnabled(false);
	    txtFolio.setEditable(true);
	    btnAgregar.setEnabled(false);
	    btnEliminar.setEnabled(false);
	    btnFiltroPuestosDependiente.setEnabled(false);
	    btnFiltroPuestosPrincipal.setEnabled(false);
	    panelfalseGuardar();
	}
/////TODO filtro de puestos	
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
		JTextField txtNombre_Completo =new Componentes().text(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<", 300, "String");
		
		String botonPresionado = "";
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Cat_Filtro_puestos_jerarquico(String boton)	{
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Filter-List-icon16.png"));
			this.setTitle("Filtro de Puestos");
			campo.setBorder(BorderFactory.createTitledBorder("Filtro de Puestos"));
			trsfiltro = new TableRowSorter(model); 
			tabla.setRowSorter(trsfiltro);  
			botonPresionado = boton;
			
			campo.add(getPanelTabla()).setBounds(10,42,575,440);
			campo.add(txtFolio).setBounds(10,20,53,20);
			campo.add(txtNombre_Completo).setBounds(64,20,500,20);
			
			agregar(tabla);
			cont.add(campo);
			
			txtFolio.addKeyListener(opFiltroFolio);
			txtNombre_Completo.addKeyListener(opFiltroNombre);
			
			this.addWindowListener(new WindowAdapter() {
                 public void windowOpened( WindowEvent e ){
                	 txtNombre_Completo.requestFocus();
              }
            });
			
			this.setSize(600,520);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		}
		
		private void agregar(final JTable tbl) {
	        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
		        public void mouseClicked(MouseEvent e) {
		        	if(e.getClickCount() == 2){
		        		int fila = tabla.getSelectedRow();
		        		Object folioPuesto =  tabla.getValueAt(fila, 0).toString().trim();
		    			Object nombre =  tabla.getValueAt(fila, 1).toString().trim();
		    			dispose();
		    			
						if(botonPresionado.equals("Puestos Principal")){
							txtFolioPuestoPrincipal.setText(folioPuesto+"");
							txtPuestoPrincipal.setText(nombre+"");    				
		    			}
						if(botonPresionado.equals("Puesto Dependiente")){
							txtFolioP_Dependiente.setText(folioPuesto+"");
							txtP_Dependiente.setText(nombre+""); 
						}
//						if(botonPresionado.equals("")){
//							txtPuestoPrincipal.setText(nombre+""); 
//						}
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
			tabla.getColumnModel().getColumn(1).setMinWidth(370);
			tabla.getColumnModel().getColumn(1).setMaxWidth(600);
			tabla.getColumnModel().getColumn(2).setHeaderValue("Abreviatura");
			tabla.getColumnModel().getColumn(2).setMinWidth(135);
			tabla.getColumnModel().getColumn(2).setMaxWidth(300);
			
			tabla.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
			tabla.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
			tabla.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
			
			Statement s;
			ResultSet rs;
			try {
				s = new Connexion().conexion().createStatement();
				
				String consulta = "exec sp_filtro_puesto_jerarquico";
				
				if(botonPresionado.equals("Puesto Dependiente")){
					
					consulta = "select folio, nombre, abreviatura from tb_puesto where folio not in ( ";
					String quitarPuestos = "";
					for(int i=0; i<tablaP.getRowCount(); i++){
						quitarPuestos+=tablaP.getValueAt(i, 0)+",";
					}
					consulta += quitarPuestos.length()==0 ? "0)" : quitarPuestos.substring(0, quitarPuestos.length()-1)+")";
				}
//				System.out.println(consulta);
				
				rs = s.executeQuery(consulta);
				
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
