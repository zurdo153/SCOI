package Cat_Servicios;

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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import Conexiones_SQL.Connexion;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Lista_de_Raya.Obj_Departamento;
import Obj_Lista_de_Raya.Obj_Puestos;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_Filtro_Dinamico;
import Obj_Principal.Obj_tabla;
import Obj_Servicios.Obj_Servicios;

@SuppressWarnings("serial")
public class Cat_Servicios extends JFrame{
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	Connexion con = new Connexion();
	int columnas = 3,checkbox=-1;
	public void init_tabla(){
    	this.tabla.getColumnModel().getColumn(0).setMinWidth(60);	
    	this.tabla.getColumnModel().getColumn(1).setMinWidth(450);
    	this.tabla.getColumnModel().getColumn(2).setMinWidth(153);
    	
		String comando="select folio,nombre,abreviatura from tb_puesto order by nombre";
		String basedatos="26",pintar="si";
		new Obj_tabla().Obj_Refrescar(tabla,modelo, columnas, comando, basedatos,pintar,checkbox);
    }
	
	 public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"Folio","Puesto","Abreviatura"}){
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
	
	    JTable tabla = new JTable(modelo);
		public JScrollPane scroll_tabla = new JScrollPane(tabla);

	     @SuppressWarnings("rawtypes")
	    private TableRowSorter trsfiltro;
	
	JTextField txtFolio        = new Componentes().text(new JCTextField(), "Folio", 9, "Int");
	JTextField txtServicio       = new Componentes().text(new JCTextField(), "Servicio", 150, "String");
	JTextField txtServicioFiltro = new Componentes().text(new JCTextField(), "Teclea Aqui Para Buscar En La Tabla", 500, "String");
	
	JCButton btnBuscar         = new JCButton(""              ,"buscar.png"            ,"Azul");
	JCButton btnFiltro         = new JCButton(""              ,"Filter-List-icon16.png","Azul");
	JCButton btnSalir          = new JCButton("Salir"         ,"salir16.png"      ,"Azul");
	JCButton btnDeshacer       = new JCButton("Deshacer"      ,"deshacer16.png"   ,"Azul");
	JCButton btnGuardar        = new JCButton("Guardar"       ,"Guardar.png"      ,"Azul");
	JCButton btnEditar         = new JCButton("Editar"        ,"editara.png"      ,"Azul");
	JCButton btnNuevo          = new JCButton("Nuevo"         ,"Nuevo.png"        ,"Azul");
	
	JTextArea txaDetalle       = new Componentes().textArea(new JTextArea(), "", 1000);
	JScrollPane scrollDetalle  = new JScrollPane(txaDetalle);
	
	Obj_Usuario usuario = new Obj_Usuario().LeerSession();

	String[] status = {"VIGENTE","CANCELADO"};
	  @SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbEstatus = new JComboBox(status);
	
	 String Departamentos[] = new Obj_Departamento().Combo_Departamento();
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbDepartamento = new JComboBox(Departamentos);  
	 
	 String Prioridades[] = new Obj_Servicios().Prioridad();
	 @SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbPrioridades = new JComboBox(Prioridades);  
	 
			
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Cat_Servicios(){
		this.setSize(1035,768);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/utilidades-agt-icono-6387-32.png"));
		this.panel.setBorder(BorderFactory.createTitledBorder("Servicios"));
		this.setTitle("Alimentacion y Modificacion De Servicios");
		
		trsfiltro = new TableRowSorter(modelo); 
		tabla.setRowSorter(trsfiltro);
		
		 int x = 15, y=15, width=100, height=20, sep=100;		
		this.panel.add(new JLabel("Folio:")).setBounds      (x      ,y      ,width    ,height  );
		this.panel.add(txtFolio).setBounds                  (x+=45  ,y      ,width    ,height  );
		this.panel.add(btnBuscar).setBounds                 (x+=100 ,y      ,32       ,height  );
		this.panel.add(cmbEstatus).setBounds                (x+=60  ,y      ,width    ,height  );
		this.panel.add(cmbPrioridades).setBounds            (x+=120 ,y      ,width+45 ,height  );
		this.panel.add(cmbDepartamento).setBounds           (x+=440 ,y      ,width*2  ,height  );
		
		  x=15;
		this.panel.add(new JLabel("Servicio:")).setBounds   (x      ,y+=30  ,width    ,height  );
		this.panel.add(txtServicio).setBounds               (x+=45  ,y      ,width+325,height  );
		
		  x=15;
		this.panel.add(new JLabel("Detalle:")).setBounds    (x      ,y+=30  ,width    ,height  );
		this.panel.add(scrollDetalle).setBounds             (x+=45  ,y      ,953      ,height*4); 
		  x=15;
		this.panel.add(txtServicioFiltro).setBounds         (x      ,y+=90  ,width*10 ,height  );
		this.panel.add(scroll_tabla).setBounds              (x      ,y+20   ,width*10 ,width*5 );
		  width=120;sep=180;
		this.panel.add(btnNuevo).setBounds                  (x      ,y+=540 ,width    ,height  );
		this.panel.add(btnSalir).setBounds                  (x+=sep ,y      ,width    ,height  );
		this.panel.add(btnDeshacer).setBounds               (x+=sep ,y      ,width    ,height  );
		this.panel.add(btnEditar).setBounds                 (x+=sep ,y      ,width    ,height  );
		this.panel.add(btnGuardar).setBounds                (x+=sep ,y      ,width    ,height  );
		
		init_tabla();
		agregar(tabla);
		panelEnabledFalse();
		
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
		
		txtServicioFiltro.addKeyListener(opFiltroNombre);
		cont.add(panel);
		
        this.addWindowListener(new WindowAdapter() {
            public void windowOpened( WindowEvent e ){
           	 txtServicioFiltro.requestFocus();
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
						txtServicio.setText(tabla.getValueAt(fila,1)+"");
						btnEditar.setEnabled(true);
	        	}
	        }
        });
    }
	
ActionListener guardar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(txtFolio.getText().equals("")){
				JOptionPane.showMessageDialog(null, "El Folio Es Requerido \n", "Aviso", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
			}else{			
				Obj_Puestos puesto = new Obj_Puestos().buscar(Integer.parseInt(txtFolio.getText()));
				
				if(puesto.getFolio() == Integer.parseInt(txtFolio.getText())){
					if(JOptionPane.showConfirmDialog(null, "El Registro Ya Existe, ¿Desea Cambiarlo?") == 0){
						if(validaCampos()!="") {
							JOptionPane.showMessageDialog(null, "Los Siguientes Campos Son Requeridos:\n"+validaCampos(), "Aviso", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
							return;
						}else{
							puesto.setPuesto(txtServicio.getText());
							puesto.actualizar(Integer.parseInt(txtFolio.getText()));
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
						puesto.setPuesto(txtServicio.getText());
						if(puesto.guardar()){
						
						init_tabla();
						panelLimpiar();
						panelEnabledFalse();
						txtFolio.setEditable(true);
						txtFolio.requestFocus();
						JOptionPane.showMessageDialog(null,"El Registró se Guardó de Forma Segura","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/aplicara-el-dialogo-icono-6256-32.png"));
						}else{
							JOptionPane.showMessageDialog(null,"Error Al Guarda el Puesto Avise al Administrador del Sistema","Aviso",JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-icono-eliminar5252-64.png"));
						}
					}
				}
			}			
		}
	};
	
	
	KeyListener opFiltroNombre = new KeyListener(){
		public void keyReleased(KeyEvent arg0) {
			new Obj_Filtro_Dinamico(tabla,"Puesto", txtServicioFiltro.getText().toUpperCase(), "", "", "", "", "", "");
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
			Obj_Puestos puesto = new Obj_Puestos();
			puesto = puesto.buscar(Integer.parseInt(txtFolio.getText()));
			
			if(puesto.getFolio() != 0){
			
			txtFolio.setText(puesto.getFolio()+"");
			txtServicio.setText(puesto.getPuesto()+"");
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
		if(txtServicio.getText().equals("")) 			error+= "Bono\n";
				
		return error;
	}
	
	ActionListener nuevo = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			Obj_Puestos puesto = new Obj_Puestos().buscar_nuevo();
			if(puesto.getFolio() != 0){
				panelLimpiar();
				panelEnabledTrue();
				txtFolio.setText(puesto.getFolio()+1+"");
				txtFolio.setEditable(false);
				txtServicio.requestFocus();
			}else{
				panelLimpiar();
				panelEnabledTrue();
				txtFolio.setText(1+"");
				txtFolio.setEditable(false);
				txtServicio.requestFocus();
			}
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
		txtFolio.setEditable(true);
		txtServicio.setEditable(false);
		txaDetalle.setEditable(false);
	}		
	
	public void panelEnabledTrue(){	
		txtFolio.setEditable(false);
		txtServicio.setEditable(true);
		txaDetalle.setEditable(true);
	}
	
	public void panelLimpiar(){	
		txtFolio.setText("");
		txtServicio.setText("");
		txaDetalle.setText("");
	}
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Servicios().setVisible(true);
		}catch(Exception e){	}
	}
	
}