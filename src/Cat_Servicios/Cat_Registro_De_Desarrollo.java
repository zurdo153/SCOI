package Cat_Servicios;

import java.awt.Component;
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.table.DefaultTableModel;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Connexion;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_Filtro_Dinamico_Plus;
import Obj_Renders.tablaRenderer;
import Obj_Servicios.Obj_Registro_De_Desarrollo;

@SuppressWarnings("serial")
public class Cat_Registro_De_Desarrollo extends JFrame {
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JTextField txtFolioRegistro 	= new Componentes().text(new JCTextField(), "Folio Registro", 10, "Int");
	JCButton   btnBuscarRegistro 	= new JCButton("","buscar.png","Azul");
	JCButton   btnDeshacerRegistro 	= new JCButton  ("","deshacer16.png","Azul");
	JCButton   btnNuevoRegistro 	= new JCButton  ("Nuevo","aa.png","Azul");
	
	JTextField txtFolioSolicitante = new Componentes().text(new JCTextField(), "Folio", 10, "Int");
	JTextField txtSolicitante = new Componentes().text(new JCTextField(), "Persona Solicitante", 180, "String");
	JCButton btnBuscarSolicitante = new JCButton("","buscar.png","Azul");
	JCButton btnLimpiarSolicitante = new JCButton("","deshacer16.png","Azul");
	
	JTextField txtFolioAtendio = new Componentes().text(new JCTextField(), "Folio", 10, "Int");
	JTextField txtAtendio = new Componentes().text(new JCTextField(), "Persona Que Atendio", 180, "String");
	JCButton btnBuscarAtendio = new JCButton("","buscar.png","Azul");
	JCButton btnLimpiarAtendio = new JCButton  ("","deshacer16.png","Azul");
	
	JCButton btnBuscarAfectados = new JCButton("","buscar.png","Azul");
	JCButton btnLimpiarAfectados = new JCButton  ("","deshacer16.png","Azul");
	
	JCButton btnGuardar = new JCButton  ("Guardar","guardar.png","Azul");
	
	DefaultTableModel modelo = new DefaultTableModel(null,new String[]{"folio","Colaborador","Sueldo","Bonos","Tiempo Antes Del Desarollo (Min)","Tiempo Despues Del Desarollo (Min)","Costo Antes Del Desarollo","Costo Despues Del Desarollo"}){
		
		@SuppressWarnings("rawtypes")
		Class[] types = new Class[]{
				java.lang.Object.class,
				java.lang.Object.class,
				java.lang.Object.class,
				java.lang.Object.class,
				java.lang.Object.class,
				java.lang.Object.class,
				java.lang.Object.class,
				java.lang.Object.class
		};
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Class getColumnClass(int columnIndex) {
            return types[columnIndex];
        }
        public boolean isCellEditable(int fila, int columna){
       	 
       	 switch(columna){
       	 	case 0 : return false; 
       	 	case 1 : return false; 
       	 	case 2 : return false;
	       	case 3 : return false;
	       	case 4 : return true;
	       	case 5 : return true;
	       	case 6 : return false;
	       	case 7 : return false;
       	 	
       	 }
			return false;
		}
	};
	
	JTable tabla = new JTable(modelo);
	JScrollPane scrollTabla = new JScrollPane(tabla);
	
	JTextArea txaMejoras_A_Optener = new Componentes().textArea(new JTextArea(), "", 1100);
	JScrollPane scrollMejoras_A_Optener = new JScrollPane(txaMejoras_A_Optener);
	
	JTextArea txaFuncionalidadDelPrograma = new Componentes().textArea(new JTextArea(), "", 1100);
	JScrollPane scrollFuncionalidad = new JScrollPane(txaFuncionalidadDelPrograma);
	
	JTextArea txaSujerenciasExtras = new Componentes().textArea(new JTextArea(), "", 1100);
	JScrollPane scrollSujerenciasExtras = new JScrollPane(txaSujerenciasExtras);
	
	String bandera = "";
	
	public Cat_Registro_De_Desarrollo() {
		this.setSize(1024,660);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/de-configuracion-de-usuario-icono-7374-32.png"));
		this.setTitle("Registro De Desarollo");
		Border blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
		this.panel.setBorder(BorderFactory.createTitledBorder(blackline, "Captura De Registro De Desarrollo"));
		
		txaMejoras_A_Optener.setLineWrap(true); 
		txaMejoras_A_Optener.setWrapStyleWord(true);
		
		txaFuncionalidadDelPrograma.setLineWrap(true); 
		txaFuncionalidadDelPrograma.setWrapStyleWord(true);
		
		txaSujerenciasExtras.setLineWrap(true); 
		txaSujerenciasExtras.setWrapStyleWord(true);
		
		int x=15,y=20,ancho=100;
		
		panel.add(new JLabel("Folio:")).setBounds(x, y, ancho, 20);
		panel.add(txtFolioRegistro).setBounds(x+ancho, y, 100, 20);
		panel.add(btnBuscarRegistro).setBounds(x+ancho*2+2, y, 30, 20);	
		panel.add(btnDeshacerRegistro).setBounds(x+ancho*2+35, y, 30, 20);
		panel.add(btnNuevoRegistro).setBounds(x+ancho*2+68, y, 90, 20);
		
		panel.add(new JLabel("Solicitante:")).setBounds(x, y+=25, ancho, 20);
		panel.add(txtFolioSolicitante).setBounds(x+ancho, y, 50, 20);
		panel.add(txtSolicitante).setBounds(x+ancho+50, y, ancho*4, 20);
		panel.add(btnBuscarSolicitante).setBounds(x+ancho+52+(ancho*4), y, 30, 20);
		panel.add(btnLimpiarSolicitante).setBounds(x+ancho+84+(ancho*4), y, 30, 20);
		
		panel.add(new JLabel("Atendio:")).setBounds(x, y+=25, ancho, 20);
		panel.add(txtFolioAtendio).setBounds(x+ancho, y, 50, 20);
		panel.add(txtAtendio).setBounds(x+ancho+50, y, ancho*4, 20);
		panel.add(btnBuscarAtendio).setBounds(x+ancho+52+(ancho*4), y, 30, 20);
		panel.add(btnLimpiarAtendio).setBounds(x+ancho+84+(ancho*4), y, 30, 20);

		panel.add(new JLabel("Personal Afectado:")).setBounds(x, y+=25, ancho, 20);
		panel.add(btnBuscarAfectados).setBounds(x+100, y, 30, 20);
		panel.add(btnLimpiarAfectados).setBounds(x+132, y, 30, 20);
		panel.add(scrollTabla).setBounds(x, y+=20, ancho*10-10, 90);
		
		
		panel.add(new JLabel("Mejoras A Obtener:")).setBounds(x, y+=90, ancho*10-10, 20);
		panel.add(scrollMejoras_A_Optener).setBounds(x, y+=15, ancho*10-10, 40);
		
		panel.add(new JLabel("Funcionalidad Del Programa:")).setBounds(x, y+=50, ancho*10-10, 20);
		panel.add(scrollFuncionalidad).setBounds(x, y+=15, ancho*10-10, 180);
		
		panel.add(new JLabel("Sujerencia Y Modificaciones Extras:")).setBounds(x, y+=190, ancho*10-10, 20);
		panel.add(scrollSujerenciasExtras).setBounds(x, y+=15, ancho*10-10, 100);
		
		panel.add(btnGuardar).setBounds(x+ ancho*9-30, y+=105, ancho+20, 30);
		
		txtFolioSolicitante.setEditable(false);
		txtSolicitante.setEditable(false);
		txtFolioAtendio.setEditable(false);
		txtAtendio.setEditable(false);
		
		render();
		agregar(tabla);
		tabla.addKeyListener(seleccionConTeclado);
		
		btnBuscarRegistro.addActionListener(opFolio);
		btnDeshacerRegistro.addActionListener(opFolio);
		btnNuevoRegistro.addActionListener(opFolio);
		
		btnBuscarSolicitante.addActionListener(opFiltro);
		btnBuscarAtendio.addActionListener(opFiltro);
		btnBuscarAfectados.addActionListener(opFiltro);
		
		btnLimpiarSolicitante.addActionListener(opLimpiar);
		btnLimpiarAtendio.addActionListener(opLimpiar);
		btnLimpiarAfectados.addActionListener(opLimpiar);
		
        txaMejoras_A_Optener.addAncestorListener(tranferir);
        txaFuncionalidadDelPrograma.addAncestorListener(tranferir);
        txaSujerenciasExtras.addAncestorListener(tranferir);
        
        btnGuardar.addActionListener(opGuardar);
        
        btnBuscarSolicitante.setEnabled(false);
		btnBuscarAtendio.setEnabled(false);
		btnBuscarAfectados.setEnabled(false);
		btnGuardar.setEnabled(false);
		
		cont.add(panel);
	}
	
	String banderaGuardarModificar="";
	ActionListener opFolio = new ActionListener(){
		@SuppressWarnings("unused")
		public void actionPerformed(ActionEvent e){
			if(e.getSource().equals(btnBuscarRegistro)){
				
				if(!txtFolioRegistro.getText().trim().equals("")){//si es diferente de vacio 
					if(true){//si se encuentra el registro
						banderaGuardarModificar="MODIFICAR";
						validarFolioRegistro("si");
						
//						aqui va el metodo de busqueda
					}else{
						banderaGuardarModificar="";
						validarFolioRegistro("no");
//						aviso (el registro que se busco no existe)					
					}
				}else{
					banderaGuardarModificar="";
					validarFolioRegistro("no");
//					aviso (El Campo Folio Se Encuentra Vacio)						
				}
			}
			
			if(e.getSource().equals(btnDeshacerRegistro)){
				banderaGuardarModificar="";
				validarFolioRegistro("no");				
			}
			
			if(e.getSource().equals(btnNuevoRegistro)){
				banderaGuardarModificar="GUARDAR";
				validarFolioRegistro("si");
				txtFolioRegistro.setText(new BuscarSQL().buscar_folio_consecutivo_por_folio_de_transaccion(33));
				btnBuscarSolicitante.requestFocus();
			}
		}
	};
	
	public void validarFolioRegistro(String encontrado){
		
		if(encontrado.equals("si")){
			txtFolioRegistro.setEditable(false);
			btnBuscarRegistro.setEnabled(false);
			btnDeshacerRegistro.setEnabled(true);
			btnNuevoRegistro.setEnabled(false);
			
			btnBuscarSolicitante.setEnabled(true);
			btnBuscarAtendio.setEnabled(true);
			btnBuscarAfectados.setEnabled(true);
			btnGuardar.setEnabled(true);
		}else{
			txtFolioRegistro.setEditable(true);
			btnBuscarRegistro.setEnabled(true);
			btnDeshacerRegistro.setEnabled(true);
			btnNuevoRegistro.setEnabled(true);
			txtFolioRegistro.setText("");
			
			btnBuscarSolicitante.setEnabled(false);
			btnBuscarAtendio.setEnabled(false);
			btnBuscarAfectados.setEnabled(false);
			btnGuardar.setEnabled(false);
		}

	}
	
	AncestorListener tranferir = new AncestorListener() {
		public void ancestorRemoved(AncestorEvent event) {
		}
		public void ancestorMoved(AncestorEvent event) {
            ((Component) event.getSource()).transferFocus();
		}
		public void ancestorAdded(AncestorEvent event) {
		}
	};
	
	ActionListener opFiltro = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String botonFiltro = "";
			
			botonFiltro = e.getSource().equals(btnBuscarSolicitante)?"solicitante":(e.getSource().equals(btnBuscarAtendio)?"atendio":"afectados");
			new Cat_Filtro_Empleado(e.getSource().equals(btnBuscarAtendio)?" and empleado.departamento = 1":"",botonFiltro).setVisible(true);
		}
	};
	
	ActionListener opLimpiar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			if(e.getSource().equals(btnLimpiarSolicitante)){
				txtFolioSolicitante.setText(""); 
				txtSolicitante.setText("");
			}
			if(e.getSource().equals(btnLimpiarAtendio)){
				txtFolioSolicitante.setText(""); 
				txtSolicitante.setText("");			
			}
			if(e.getSource().equals(btnLimpiarAfectados)){
				modelo.setRowCount(0);
			}
		}
	};
	
	ActionListener opGuardar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			if(tabla.isEditing()){
				tabla.getCellEditor().stopCellEditing();
			}
			
			Obj_Registro_De_Desarrollo reg = new Obj_Registro_De_Desarrollo();
			
			reg.buscar_Registro(Integer.valueOf(txtFolioRegistro.getText().trim()));
			
			if(banderaGuardarModificar.equals("MODIFICAR")){
//					modifiar
					if(JOptionPane.showConfirmDialog(null, "El registro existe, ¿desea actualizarlo?") == 0){
						
							if(ValidaCampos().equals("")){
								
									reg.setFolio_registro(Integer.valueOf(txtFolioRegistro.getText().trim()));
									reg.setUsuario_solicitante(Integer.valueOf( txtFolioSolicitante.getText().trim()));
									reg.setUsuario_atendio(Integer.valueOf(txtFolioAtendio.getText().trim()));
									reg.setAfectados(afectados());
									
									reg.setMejoras(txaMejoras_A_Optener.getText().trim());
									reg.setFuncionalidad(txaFuncionalidadDelPrograma.getText().trim());
									reg.setSugerencias(txaSujerenciasExtras.getText().trim());
									
									if(reg.guardar_solicitud(banderaGuardarModificar)){
										System.out.print("MODIFICADO");
									}else{
										JOptionPane.showMessageDialog(null,"El Registro No Se Pudo Guardar, Avise Al Administrador","Aviso",JOptionPane.ERROR_MESSAGE);
										return;
									}
								
							}else{
								JOptionPane.showMessageDialog(null,"Los Siguientes Campos Son Requeridos: \n"+ValidaCampos(),"Aviso",JOptionPane.ERROR_MESSAGE);
								return;
							}
					}
				
			}else{
//					guardar
					if(ValidaCampos().equals("")){
						
//						System.out.println(Integer.valueOf(txtFolioRegistro.getText().trim()));
//						System.out.println(Integer.valueOf(txtFolioSolicitante.getText().trim()));
//						System.out.println(Integer.valueOf(txtFolioAtendio.getText().trim()));
//						System.out.println(txaMejoras_A_Optener.getText().trim());
//						System.out.println(txaFuncionalidadDelPrograma.getText().trim());
//						System.out.println(txaSujerenciasExtras.getText().trim());
						
							reg.setFolio_registro(Integer.valueOf(txtFolioRegistro.getText()));
							reg.setUsuario_solicitante(Integer.valueOf( txtFolioSolicitante.getText().trim()));
							reg.setUsuario_atendio(Integer.valueOf(txtFolioAtendio.getText().trim()));
							reg.setAfectados(afectados());
							
							reg.setMejoras(txaMejoras_A_Optener.getText().trim());
							reg.setFuncionalidad(txaFuncionalidadDelPrograma.getText().trim());
							reg.setSugerencias(txaSujerenciasExtras.getText().trim());
							
							if(reg.guardar_solicitud("GUARDAR")){
								System.out.print("Gaurdar");
							}else{
								JOptionPane.showMessageDialog(null,"El Registro No Se Pudo Guardar, Avise Al Administrador","Aviso",JOptionPane.ERROR_MESSAGE);
								return;
							}
						
					}else{
						JOptionPane.showMessageDialog(null,"Los Siguientes Campos Son Requeridos: \n"+ValidaCampos(),"Aviso",JOptionPane.ERROR_MESSAGE);
						return;
					}
			}
		}
	};
	
	public Object[][] afectados(){
		Object[][] afect = new Object[tabla.getRowCount()][tabla.getColumnCount()];
		
			for(int i=0; i<tabla.getRowCount(); i++){
				for(int j=0; j<tabla.getRowCount(); j++){
					afect[i][j] = tabla.getValueAt(i, j);
				}
			}
		
		return afect;
	}
	
	public String ValidaCampos(){
		String vacios = "";
		
		
		vacios += txtFolioSolicitante.getText().equals("")?"Solicitante\n":"";
		vacios += txtFolioAtendio.getText().equals("")?"Atendio\n":"";
		vacios += tabla.getRowCount()==0?"Afectados\n":"";
		vacios += txaMejoras_A_Optener.getText().trim().equals("")?"Mejoras A Optener\n":"";
		vacios += txaFuncionalidadDelPrograma.getText().trim().equals("")?"Funcionalidad Del Programa\n":"";
		vacios += txaSujerenciasExtras.getText().equals("")?"Sugerencias Extras\n":"";
		
		return vacios;
	}
	
	public void render(){
			tabla.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","derecha","Arial","negrita",12));
			tabla.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",12));
			tabla.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","derecha","Arial","negrita",12));
			tabla.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","derecha","Arial","negrita",12));
			tabla.getColumnModel().getColumn(4).setCellRenderer(new tablaRenderer("texto","derecha","Arial","negrita",12));
			tabla.getColumnModel().getColumn(5).setCellRenderer(new tablaRenderer("texto","derecha","Arial","negrita",12));
			tabla.getColumnModel().getColumn(6).setCellRenderer(new tablaRenderer("texto","derecha","Arial","negrita",12));
			tabla.getColumnModel().getColumn(7).setCellRenderer(new tablaRenderer("texto","derecha","Arial","negrita",12));
			
			this.tabla.getTableHeader().setReorderingAllowed(false) ;
			this.tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

			this.tabla.getColumnModel().getColumn(0).setMaxWidth(50);
			this.tabla.getColumnModel().getColumn(0).setMinWidth(50);
			this.tabla.getColumnModel().getColumn(1).setMaxWidth(280);
			this.tabla.getColumnModel().getColumn(1).setMinWidth(280);
			this.tabla.getColumnModel().getColumn(2).setMaxWidth(50);
			this.tabla.getColumnModel().getColumn(2).setMinWidth(50);
			this.tabla.getColumnModel().getColumn(3).setMaxWidth(50);
			this.tabla.getColumnModel().getColumn(3).setMinWidth(50);
			this.tabla.getColumnModel().getColumn(4).setMaxWidth(180);
			this.tabla.getColumnModel().getColumn(4).setMinWidth(180);
			this.tabla.getColumnModel().getColumn(5).setMaxWidth(180);
			this.tabla.getColumnModel().getColumn(5).setMinWidth(180);
			this.tabla.getColumnModel().getColumn(6).setMaxWidth(180);
			this.tabla.getColumnModel().getColumn(6).setMinWidth(180);
			this.tabla.getColumnModel().getColumn(7).setMaxWidth(180);
			this.tabla.getColumnModel().getColumn(7).setMinWidth(180);
		}
	
	int columna=0;
	private void agregar(final JTable tbl) {
		tbl.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent arg0) {}
			
			public void mousePressed(MouseEvent arg0) {
				fila = tbl.getSelectedRow();
				columna = tbl.getSelectedColumn();
	        	if(columna==4 || columna==5){
	        		editarCelda();
	        	}
			}
			
			public void mouseExited(MouseEvent arg0) {}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseClicked(MouseEvent arg0) {}
		});
    }
	
	int fila=0;
	KeyListener seleccionConTeclado = new KeyListener() {
		public void keyTyped(KeyEvent e) {}
		public void keyPressed(KeyEvent e){}
		public void keyReleased(KeyEvent e){
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				fila+=1;
				if(fila == tabla.getRowCount()){	
					fila=0;		
				}
				editarCelda();
			}
		}
	};
	
	public void editarCelda(){
			tabla.setRowSelectionInterval(fila, fila);
			tabla.editCellAt(fila, columna);
			Component aComp=tabla.getEditorComponent();
			aComp.requestFocus();
	}

	public class Cat_Filtro_Empleado extends JDialog {
		
		Container cont = getContentPane();
		JLayeredPane campo = new JLayeredPane();
		
		boolean validaSeleccionDeTabla = false;
		DefaultTableModel modeloFiltro = new DefaultTableModel(null,new String[]{"folio","Colaborador","Sueldo","Bonos","*"}){
			
			@SuppressWarnings("rawtypes")
			Class[] types = new Class[]{
					java.lang.Object.class,
					java.lang.Object.class,
					java.lang.Object.class,
					java.lang.Object.class,
					java.lang.Boolean.class
			};
			
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int columnIndex) {
	            return types[columnIndex];
	        }
	        public boolean isCellEditable(int fila, int columna){
	       	 
	       	 switch(columna){
	       	 	case 0 : return false; 
	       	 	case 1 : return false; 
	       	 	case 2 : return false;
		       	case 3 : return false;
		       	case 4 : return true;
	       	 	
	       	 }
				return false;
			}
	        
            @Override
            public void setValueAt(Object value, int row, int col) {
                super.setValueAt(value, row, col);
                if(validaSeleccionDeTabla){
                	if (col == 4 && value.equals(Boolean.TRUE))
                    deselectValues(row, col);
                }
            }

            private void deselectValues(int selectedRow, int col) {
                for (int row = 0; row < getRowCount(); row++) {
                    if (getValueAt(row, col).equals(Boolean.TRUE)
                            && row != selectedRow) {
                        setValueAt(Boolean.FALSE, row, col);
                        fireTableCellUpdated(row, col);
                    }
                }
            }
		};
		
		JTable tablaFiltro = new JTable(modeloFiltro);
		JScrollPane scrollTablaFiltro = new JScrollPane(tablaFiltro);
		
		JTextField txtNombre_Completo = new Componentes().text(new JCTextField(), "Filtro De Colaboradores", 100, "String");
		JCButton btnGenerar = new JCButton("Generar", "", "Azul");
		
		public Cat_Filtro_Empleado(String condicionAnd, String boton){
			this.setModal(true);
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/filter_icon&16.png"));
			this.setTitle("Filtro de Empleados");
			campo.setBorder(BorderFactory.createTitledBorder("Filtro De Empleado"));
			
			validaSeleccionDeTabla = (!boton.equals("afectados"))?true:false;
			bandera = boton;
			
			campo.add(txtNombre_Completo).setBounds(15,20,400,20);
			campo.add(btnGenerar).setBounds(415, 20, 100, 20);
			campo.add(scrollTablaFiltro).setBounds(15,42,520,565);
			
			cont.add(campo);
			
			renderFiltro();
			llenarFiltro(condicionAnd);			
			
			txtNombre_Completo.addKeyListener(opFiltroLoco);
			btnGenerar.addActionListener(opAgregar);
			
			this.setSize(560,650);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			
//          asigna el foco al JTextField del nombre deseado al arrancar la ventana
            this.addWindowListener(new WindowAdapter() {
                    public void windowOpened( WindowEvent e ){
                    	txtNombre_Completo.requestFocus();
                 }
            });
              
//         pone el foco en el txtFolio al presionar la tecla scape
              getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                 KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "foco");
              
              getRootPane().getActionMap().put("foco", new AbstractAction(){
                  @Override
                  public void actionPerformed(ActionEvent e)
                  {
                	  txtNombre_Completo.setText("");
                      txtNombre_Completo.requestFocus();
                  }
              });
              
		}
		
		int[] columnas = {0,1,2,3};
		ActionListener opAgregar = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(bandera.equals("afectados")){modelo.setRowCount(0);}
				
				Object[] registro = new Object[8];
				for(int i=0; i<modeloFiltro.getRowCount(); i++){
					
					if(modeloFiltro.getValueAt(i, 4).toString().trim().equals("true")){
						
						if(bandera.equals("solicitante")){
							txtFolioSolicitante.setText(modeloFiltro.getValueAt(i, 0).toString());
							txtSolicitante.setText(modeloFiltro.getValueAt(i, 1).toString());
							dispose();
						}
						if(bandera.equals("atendio")){
							txtFolioAtendio.setText(modeloFiltro.getValueAt(i, 0).toString());
							txtAtendio.setText(modeloFiltro.getValueAt(i, 1).toString());
							dispose();
						}
						if(bandera.equals("afectados")){
								for(int j=0; j<modeloFiltro.getColumnCount()-1; j++){
									registro[j]= modeloFiltro.getValueAt(i, j);
								}
								registro[4]= "";
								registro[5]= "";
								registro[6]= "";
								registro[7]= "";
								
								modelo.addRow(registro);
								dispose();
								fila =0;
								columna = 4;
								
								editarCelda();
						}
					}
				}
			}
		};
		
		KeyListener opFiltroLoco = new KeyListener(){
			public void keyReleased(KeyEvent arg0) {
				new Obj_Filtro_Dinamico_Plus(tablaFiltro, txtNombre_Completo.getText().toUpperCase(), columnas);
			}
			public void keyTyped(KeyEvent arg0) {}
			public void keyPressed(KeyEvent arg0) {}		
		};
		
		public void renderFiltro(){
			tablaFiltro.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","derecha","Arial","negrita",12));
			tablaFiltro.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",12));
			tablaFiltro.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","derecha","Arial","negrita",12));
			tablaFiltro.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","derecha","Arial","negrita",12));
			tablaFiltro.getColumnModel().getColumn(4).setCellRenderer(new tablaRenderer("CHB","centro","Arial","negrita",12));
			
			tablaFiltro.getTableHeader().setReorderingAllowed(false) ;

			tablaFiltro.getColumnModel().getColumn(0).setMaxWidth(50);
			tablaFiltro.getColumnModel().getColumn(0).setMinWidth(50);
			tablaFiltro.getColumnModel().getColumn(1).setMaxWidth(320);
			tablaFiltro.getColumnModel().getColumn(1).setMinWidth(320);
			tablaFiltro.getColumnModel().getColumn(2).setMaxWidth(50);
			tablaFiltro.getColumnModel().getColumn(2).setMinWidth(50);
			tablaFiltro.getColumnModel().getColumn(3).setMaxWidth(50);
			tablaFiltro.getColumnModel().getColumn(3).setMinWidth(50);
			tablaFiltro.getColumnModel().getColumn(4).setMaxWidth(30);
			tablaFiltro.getColumnModel().getColumn(4).setMinWidth(30);
		}
	   	private void llenarFiltro(String condicionAnd){	
			Statement s;
			ResultSet rs;
			try {
				s = new Connexion().conexion().createStatement();
				rs = s.executeQuery("select empleado.folio "
						+ " ,empleado.nombre+' '+empleado.ap_paterno+' '+empleado.ap_materno as colaborador "
						+ " ,sueldo.sueldo "
						+ " ,bono.bono+asist.bono+punt.bono as bonos "
						+ " ,'false' as chb "
						+ " from tb_empleado empleado "
						+ " inner join tb_sueldo sueldo on sueldo.folio = empleado.sueldo_id "
						+ " inner join tb_bono bono on bono.folio = empleado.bono_id "
						+ " inner join tb_bono_puntualidad_y_asistencia asist on asist.folio = empleado.bono_asistencia "
						+ " inner join tb_bono_puntualidad_y_asistencia punt on punt.folio = empleado.bono_puntualidad "
						+ " where empleado.status in (1,2,3,6) "
						+ condicionAnd);
				
				while (rs.next())
				{ 
				   Object[] fila = new Object[5];
				   fila[0] = rs.getString(1);
				   fila[1] = rs.getString(2);
				   fila[2] = rs.getString(3).trim();
				   fila[3] = rs.getString(4).trim();
				   fila[4] = Boolean.valueOf(rs.getString(4).trim());
				
				   modeloFiltro.addRow(fila); 
				}	
			} catch (SQLException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null,"Error Al Abrir El Filtro De El Empleado Error en El Procedimiento sp_filtro_empleado"+e1, "Avisa Al Administrador Del Sistema!",JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	
	public static void main(String[] args) {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Registro_De_Desarrollo().setVisible(true);
		}catch(Exception e){
			System.err.println("Error :"+ e.getMessage());
		}

	}

}
