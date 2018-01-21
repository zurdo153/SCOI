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
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import Conexiones_SQL.Connexion;
import Obj_Evaluaciones.Obj_Preguntas;
import Obj_Principal.Componentes;
import Obj_Principal.Obj_Filtro_Dinamico;
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
	
	JTextField txtFolioFiltro = new JTextField();
	JTextField txtPreguntaFiltro = new JTextField();
	@SuppressWarnings("rawtypes")
	private TableRowSorter trsfiltro;
	
	JTextField txtFolio = new Componentes().text(new JTextField(), "Folio", 9, "Int");
	JTextField txtPregunta = new Componentes().text(new JTextField(), "Pregunta", 150, "String");
	
	String[] status = {"Vigente","Cancelado"};
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbStatus = new JComboBox(status);
	
	JButton btnBuscar = new JButton(new ImageIcon("Iconos/zoom_icon&16.png"));
	JButton btnNuevo = new JButton("Nuevo",new ImageIcon("imagen/Nuevo.png"));
	JButton btnEditar = new JButton("Editar",new ImageIcon("imagen/editara.png"));
	JButton btnSalir = new JButton("Salir",new ImageIcon("imagen/salir16.png"));
	JButton btnGuardar = new JButton("Guardar",new ImageIcon("imagen/Guardar.png"));
	JButton btnDeshacer = new JButton("Deshacer",new ImageIcon("imagen/deshacer16.png"));
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Cat_Preguntas(){
		
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/encuesta.png"));
		panel.setBorder(BorderFactory.createTitledBorder("Preguntas"));
		
		this.setTitle("Captura De Preguntas");
		
		trsfiltro = new TableRowSorter(modelo); 
		tabla.setRowSorter(trsfiltro);
		
		txtFolioFiltro.setToolTipText("Filtro Por Folio");
		txtPreguntaFiltro.setToolTipText("Filtro Por Nombre");
		
		int x = 15, y=30, w=100,l=20;
		
		panel.add(new JLabel("Folio:")).setBounds      	(x     ,y    ,w  ,l);
		panel.add(txtFolio).setBounds                  	(x+=45 ,y    ,w  ,l);
		panel.add(btnBuscar).setBounds                 	(x+=100,y    ,32 ,l);
		
		panel.add(btnNuevo).setBounds                  	(x+=100,y    ,w  ,l);
		panel.add(btnEditar).setBounds                 	(x+=120,y    ,w  ,l);
		panel.add(btnDeshacer).setBounds               	(x+=120,y    ,w  ,l);
		panel.add(btnSalir).setBounds                  	(x+=120,y    ,w  ,l);
		x = 15;
		panel.add(new JLabel("Pregunta:")).setBounds   	(x     ,y+=30,w  ,l);
		panel.add(txtPregunta).setBounds				(x+=45 ,y    ,w*3,l);
		panel.add(new JLabel("Status:")).setBounds		(x+=320,y    ,w  ,l);
		panel.add(cmbStatus).setBounds                  (x+=50 ,y    ,80 ,l);
		panel.add(btnGuardar).setBounds                	(x+=190,y    ,w  ,l);
		x = 15;
		panel.add(txtFolioFiltro).setBounds            	(x     ,y+=35,60 ,l);
		panel.add(txtPreguntaFiltro).setBounds          (x+60  ,y    ,350,l);
		
		panel.add(scroll_tabla).setBounds           	(x     ,y+20 ,w*7,w*4);
		init_tabla();
		agregar(tabla);
		cmbStatus.setEnabled(false);
		txtPregunta.setEditable(false);
		
		txtFolio.requestFocus();
		txtFolio.addKeyListener(buscar_action);
//	----------------------------------------------------------------------------------------------------------------	
		btnGuardar.addActionListener(guardar);
		btnSalir.addActionListener(cerrar);
		btnBuscar.addActionListener(buscar);
		btnDeshacer.addActionListener(deshacer);
		btnNuevo.addActionListener(nuevo);
		btnEditar.addActionListener(editar);
		btnEditar.setEnabled(false);
		
		txtFolioFiltro.addKeyListener(opFiltroFolio);
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
	
	private void agregar(final JTable tbl) {
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
	        	if(e.getClickCount()==1){
	        		int fila = tabla.getSelectedRow();
	        		Object id = tabla.getValueAt(fila,0);
						txtFolio.setText(id+"");
						txtPregunta.setText(tabla.getValueAt(fila,1)+"");
						btnEditar.setEnabled(true);
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
							panelEnabledFalse();
							txtFolio.setEditable(true);
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
						panelEnabledFalse();
						txtFolio.setEditable(true);
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
	
	KeyListener opFiltroFolio = new KeyListener(){
		@SuppressWarnings("unchecked")
		public void keyReleased(KeyEvent arg0) {
			trsfiltro.setRowFilter(RowFilter.regexFilter(txtFolioFiltro.getText(), 0));
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
		public void keyReleased(KeyEvent arg0) {
			new Obj_Filtro_Dinamico(tabla,"Pregunta", txtPreguntaFiltro.getText().toUpperCase(), "", "", "", "", "", "");
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
				JOptionPane.showMessageDialog(null, "Ingrese el No. de Folio","Error",JOptionPane.WARNING_MESSAGE);
				return;
			}else{
			Obj_Preguntas preguntas = new Obj_Preguntas();
//			preguntas = 
			preguntas.buscar(Integer.parseInt(txtFolio.getText()));
			
			if(preguntas.getFolio() != 0){
			
			txtFolio.setText(preguntas.getFolio()+"");
			txtPregunta.setText(preguntas.getPregunta()+"");
			cmbStatus.setSelectedItem(preguntas.getStatus().toString().trim());
			
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
		if(txtPregunta.getText().equals("")) 			error+= "Pregunta\n";
		return error;
	}
	
	ActionListener nuevo = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
				panelLimpiar();
				panelEnabledTrue();
				txtFolio.setText(new Obj_Preguntas().buscar_nuevo()+"");
				txtFolio.setEditable(false);
				txtPregunta.requestFocus();
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
		txtPregunta.setEditable(false);
		cmbStatus.setEnabled(false);
	}		
	
	public void panelEnabledTrue(){	
		txtFolio.setEditable(true);
		txtPregunta.setEditable(true);
		cmbStatus.setEnabled(true);	
	}
	
	public void panelLimpiar(){	
		txtFolio.setText("");
		txtPregunta.setText("");
		cmbStatus.setSelectedItem("VIGENTE");
	}
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Preguntas().setVisible(true);
		}catch(Exception e){	}
	}
	
}