package Cat_Evaluaciones;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import Conexiones_SQL.Connexion;
import Obj_Evaluaciones.Obj_Preguntas;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.Obj_Filtro_Dinamico_Plus;
import Obj_Principal.Obj_tabla;

@SuppressWarnings("serial")
public class Cat_Preguntas extends JFrame{
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	Connexion con = new Connexion();
	int columnas = 3,checkbox=-1;
	public void init_tabla(){
    	this.tabla.getColumnModel().getColumn(0).setMinWidth(60);	
    	this.tabla.getColumnModel().getColumn(1).setMinWidth(450);
    	this.tabla.getColumnModel().getColumn(2).setMinWidth(153);
    	
		String comando="select folio,nombre,case when estatus = 'V' then 'VIGENTE' else 'CANCELADO' end from tb_preguntas order by nombre";
		String basedatos="26",pintar="si";
		new Obj_tabla().Obj_Refrescar(tabla,modelo, columnas, comando, basedatos,pintar,checkbox);
    }
	
	 public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"Folio","Pregunta","Estatus"}){
		 @SuppressWarnings("rawtypes")
			Class[] types = new Class[]{
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
	
	    JTable tabla = new JTable(modelo);
		public JScrollPane scroll_tabla = new JScrollPane(tabla);
	
	JTextField txtPreguntaFiltro = new JTextField();
	@SuppressWarnings("rawtypes")
	private TableRowSorter trsfiltro;
	
	JTextField txtFolio = new Componentes().text(new JTextField(), "Folio", 9, "Int");
	JTextField txtPregunta = new Componentes().text(new JTextField(), "Pregunta", 150, "String");
	
	String[] status = {"VIGENTE","CANCELADO"};
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbStatus = new JComboBox(status);
	
	JToolBar menu_toolbar  = new JToolBar();
	JButton btnBuscar = new JCButton("","buscar.png","Azul");
	JButton btnNuevo = new JCButton("Nuevo","Nuevo.png","Azul");
	JButton btnEditar = new JCButton("Editar","editara.png","Azul");
	JButton btnSalir = new JCButton("Salir","salir16.png","Azul");
	JButton btnGuardar = new JCButton("Guardar","Guardar.png","Azul");
	JButton btnDeshacer = new JCButton("Deshacer","deshacer16.png","Azul");
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Cat_Preguntas(){
		
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/encuesta.png"));
		panel.setBorder(BorderFactory.createTitledBorder("Preguntas"));
		
		this.setTitle("Captura De Preguntas");
		
		trsfiltro = new TableRowSorter(modelo); 
		tabla.setRowSorter(trsfiltro);
		
		txtPreguntaFiltro.setToolTipText("Filtro Por Nombre");
		
		int x = 15, y=15, w=100,l=20;
		
		this.panel.add(menu_toolbar).setBounds     (x,y     , w*4+50,l);
		
		panel.add(new JLabel("Folio:")).setBounds      	(x     ,y+=25,w  ,l);
		panel.add(txtFolio).setBounds                  	(x+=45 ,y    ,w  ,l);
		panel.add(btnBuscar).setBounds                 	(x+=100,y    ,32 ,l);
		
		x = 15;
		panel.add(new JLabel("Pregunta:")).setBounds   	(x     ,y+=30,w  ,l);
		panel.add(txtPregunta).setBounds				(x+=45 ,y    ,w*3,l);
		panel.add(new JLabel("Status:")).setBounds		(x+=320,y    ,w  ,l);
		panel.add(cmbStatus).setBounds                  (x+=50 ,y    ,80 ,l);
		
		x = 15;
		panel.add(txtPreguntaFiltro).setBounds          (x	   ,y+=25,410,l);
		
		panel.add(scroll_tabla).setBounds           	(x     ,y+20 ,w*7,w*4);
		
	    this.menu_toolbar.add(btnNuevo);
	    this.menu_toolbar.addSeparator();
	    this.menu_toolbar.addSeparator( );
	    this.menu_toolbar.add(btnEditar);
	    this.menu_toolbar.addSeparator();
	    this.menu_toolbar.addSeparator( );
		this.menu_toolbar.add(btnDeshacer);
		this.menu_toolbar.addSeparator();
		this.menu_toolbar.addSeparator( );
		this.menu_toolbar.add(btnGuardar);
		this.menu_toolbar.addSeparator();
		this.menu_toolbar.addSeparator( );
		this.menu_toolbar.add(btnSalir);
		this.menu_toolbar.setFloatable(false);
		
		init_tabla();
		agregar(tabla);
		
		txtFolio.addKeyListener(buscar_action);

		btnGuardar.addActionListener(guardar);
		btnSalir.addActionListener(cerrar);
		btnBuscar.addActionListener(buscar);
		btnDeshacer.addActionListener(deshacer);
		btnNuevo.addActionListener(nuevo);
		btnEditar.addActionListener(editar);
		camposActivos();
		
		txtPreguntaFiltro.addKeyListener(opFiltroNombre);
		cont.add(panel);
		this.setSize(740,570);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened( WindowEvent e ){
            	txtFolio.requestFocus();
           }
        });
	}
	
	private void agregar(final JTable tbl) {
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				
	        	if(e.getClickCount()==1){
	        		int fila = tabla.getSelectedRow();
	        		Object id = tabla.getValueAt(fila,0);
						txtFolio.setText(id+"");
						txtPregunta.setText(tabla.getValueAt(fila,1)+"");
						btnEditar.setEnabled(true);
						txtFolio.setEditable(false);
						cmbStatus.setSelectedItem(tabla.getValueAt(fila,2).toString().trim());
	        	}
	        }
        });
    }
	
ActionListener guardar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(txtFolio.getText().equals("")){
				JOptionPane.showMessageDialog(null, "El Folio Es Requerido \n", "Aviso", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
			}else{			
				Obj_Preguntas preguntas = new Obj_Preguntas().buscar(Integer.parseInt(txtFolio.getText()));
				
				if(preguntas.getFolio() == Integer.parseInt(txtFolio.getText())){
					if(JOptionPane.showConfirmDialog(null, "El Registro Ya Existe, ¿Desea Cambiarlo?") == 0){
						if(validaCampos()!="") {
							JOptionPane.showMessageDialog(null, "Los Siguientes Campos Son Requeridos:\n"+validaCampos(), "Aviso", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
							return;
						}else{
							preguntas.setFolio(Integer.parseInt(txtFolio.getText()));
							preguntas.setPregunta(txtPregunta.getText());
							preguntas.setStatus(cmbStatus.getSelectedItem().toString().trim());
							preguntas.guardar("actualizar");
							init_tabla();
							panelLimpiar();
							camposActivos();
							txtFolio.requestFocus();
						}
						JOptionPane.showMessageDialog(null,"El Registró se Guardó de Forma Segura","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/aplicara-el-dialogo-icono-6256-32.png"));
					}else{
						return;
					}
				}else{
					if(validaCampos()!="") {
						JOptionPane.showMessageDialog(null, "Los Siguientes Campos Son Requeridos:\n "+validaCampos(), "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
						return;
					}else{
						preguntas.setFolio(Integer.parseInt(txtFolio.getText()));
						preguntas.setPregunta(txtPregunta.getText());
						preguntas.setStatus(cmbStatus.getSelectedItem().toString().trim());
						if(preguntas.guardar("guardar")){
						
						init_tabla();
						panelLimpiar();
						camposActivos();
						txtFolio.requestFocus();
						JOptionPane.showMessageDialog(null,"El Registró se Guardó de Forma Segura","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/aplicara-el-dialogo-icono-6256-32.png"));
						}else{
							JOptionPane.showMessageDialog(null,"Error Al Guarda La Pregunta Avise al Administrador del Sistema","Aviso",JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-icono-eliminar5252-64.png"));
						}
					}
				}
			}			
		}
	};
	
	KeyListener opFiltroNombre = new KeyListener(){
		public void keyReleased(KeyEvent arg0) {
			int[] columnas = {1,2,3};
			new Obj_Filtro_Dinamico_Plus(tabla, txtPreguntaFiltro.getText().toUpperCase(),columnas);
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}		
	};
	
	KeyListener buscar_action = new KeyListener() {
		public void keyTyped(KeyEvent e){}
		public void keyReleased(KeyEvent e) {}
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
				JOptionPane.showMessageDialog(null, "Ingrese el No. de Folio","Error",JOptionPane.WARNING_MESSAGE);
				return;
			}else{
				Obj_Preguntas preguntas = new Obj_Preguntas().buscar(Integer.parseInt(txtFolio.getText()));
				
				if(preguntas.getFolio() != 0){
				
					txtFolio.setText(preguntas.getFolio()+"");
					txtPregunta.setText(preguntas.getPregunta()+"");
					cmbStatus.setSelectedItem(preguntas.getStatus().toString().trim());
					
					btnEditar.setEnabled(true);
					txtFolio.setEditable(false);
					btnBuscar.setEnabled(false);
				}else{
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
		if(txtPregunta.getText().equals("")) 			error+= "Pregunta\n";
		return error;
	}
	
	public void panelLimpiar(){	
		txtFolio.setText("");
		txtPregunta.setText("");
		cmbStatus.setSelectedItem("VIGENTE");
	}
	
	public void camposActivos(){
		
		btnNuevo.setEnabled(true);
		btnEditar.setEnabled(false);
		btnGuardar.setEnabled(false);
		txtFolio.setEditable(true);
		btnBuscar.setEnabled(true);
		txtPregunta.setEditable(false);
		cmbStatus.setEnabled(false);
	}
	
	ActionListener nuevo = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
				
			panelLimpiar();
			txtFolio.setText(new Obj_Preguntas().buscar_nuevo()+"");
			
			btnNuevo.setEnabled(false);
			btnEditar.setEnabled(false);
			btnGuardar.setEnabled(true);
			txtFolio.setEditable(false);
			btnBuscar.setEnabled(false);
			txtPregunta.setEditable(true);
			cmbStatus.setEnabled(true);
			txtPregunta.requestFocus();
		}
	};
	
	ActionListener deshacer = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			panelLimpiar();
			camposActivos();
			txtFolio.requestFocus();
		}
	};
	
	ActionListener editar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			btnNuevo.setEnabled(false);
			btnEditar.setEnabled(false);
			btnGuardar.setEnabled(true);
			txtFolio.setEditable(false);
			btnBuscar.setEnabled(false);
			txtPregunta.setEditable(true);
			cmbStatus.setEnabled(true);
		}		
	};
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Preguntas().setVisible(true);
		}catch(Exception e){	}
	}
	
}