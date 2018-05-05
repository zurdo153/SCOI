package Cat_Lista_de_Raya;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
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
import Obj_Lista_de_Raya.Obj_Puestos;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_tabla;

@SuppressWarnings("serial")
public class Cat_Puestos extends JFrame{
	
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
	
	JTextField       txtFolio      = new Componentes().text(new JCTextField(), "Folio"                                                        ,9   , "Int"                   ); 
	JTextField       txtPuesto     = new Componentes().text(new JCTextField(), "Nombre Del Puesto"                                            ,100 , "String"                ); 
	JTextField       txtAbreviatura= new Componentes().text(new JCTextField(), "Abreviatura"                                                  ,20  , "String"                ); 
	
	JTextField txtFiltro           = new Componentes().textfiltro(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<",500 , "String",tabla,columnas );
	
	JCheckBox chStatus = new JCheckBox("Status");
	
	JCButton btnBuscar      = new JCButton("Buscar"    ,"Filter-List-icon16.png"              ,"Azul"); 
	JCButton btnNuevo       = new JCButton("Nuevo"     ,"Nuevo.png"                           ,"Azul");
	JCButton btnGuardar     = new JCButton("Guardar"   ,"Guardar.png"                         ,"Azul");
	JCButton btnDeshacer    = new JCButton("Deshacer"  ,"deshacer16.png"                      ,"Azul");
	JCButton btnSalir    = new JCButton("Salir" ,"salir16.png"              ,"Azul");
	JCButton btnEditar   = new JCButton("Editar" ,"editara.png"                    ,"Azul");	
	
	JToolBar menu_toolbar   = new JToolBar();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Cat_Puestos(){
		
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/puesto.png"));
		panel.setBorder(BorderFactory.createTitledBorder("Puestos"));
		
		this.setTitle("Puesto");
		
		trsfiltro = new TableRowSorter(modelo); 
		tabla.setRowSorter(trsfiltro);
		
		this.menu_toolbar.add(btnNuevo    );
	    this.menu_toolbar.addSeparator(   );
	    this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.add(btnBuscar   );
	    this.menu_toolbar.addSeparator(   );
	    this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.add(btnEditar   );
	    this.menu_toolbar.addSeparator(   );
	    this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.add(btnDeshacer );
		this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.add(btnGuardar  );
		this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.addSeparator(   );
		this.menu_toolbar.add(btnSalir );
		
		this.menu_toolbar.setFloatable(false);
		
		int x = 10, y=30, w=100,l=20;		
		this.panel.add(menu_toolbar).setBounds         (x     ,y    ,600,l);
		panel.add(chStatus).setBounds                  (x+=600,y    ,60 ,l);
		panel.add(new JLabel("Folio:")).setBounds      (x=10  ,y+=30,w  ,l);
		panel.add(txtFolio).setBounds                  (x+=30 ,y    ,w  ,l);
		panel.add(new JLabel("Puesto:")).setBounds     (x+=110,y    ,w  ,l);
		panel.add(txtPuesto).setBounds                 (x+=45 ,y    ,w*3,l);
		panel.add(new JLabel("Abreviatura:")).setBounds(x+=320,y    ,w  ,l);
		panel.add(txtAbreviatura).setBounds            (x+=70 ,y    ,w  ,l);
		panel.add(txtFiltro).setBounds                 (x=10  ,y+=35,w*7,l);
		panel.add(scroll_tabla).setBounds              (x     ,y+20 ,w*7,w*4);

		init_tabla();
		agregar(tabla);
		chStatus.setEnabled(false);
		txtPuesto.setEditable(false);
		txtAbreviatura.setEditable(false);
		
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
		
		cont.add(panel);
		this.setSize(740,570);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	private void agregar(final JTable tbl) {
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
	        	if(e.getClickCount()==1){
	        		int fila = tabla.getSelectedRow();
	        		Object id = tabla.getValueAt(fila,0);
						txtFolio.setText(id+"");
						txtPuesto.setText(tabla.getValueAt(fila,1)+"");
						txtAbreviatura.setText(tabla.getValueAt(fila,2)+"");
						btnEditar.setEnabled(true);
						chStatus.setSelected(true);
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
							puesto.setPuesto(txtPuesto.getText());
							puesto.setAbreviatura(txtAbreviatura.getText());
							puesto.setStatus(chStatus.isSelected());
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
						puesto.setPuesto(txtPuesto.getText());
						puesto.setAbreviatura(txtAbreviatura.getText());
						puesto.setStatus(chStatus.isSelected());
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
			txtPuesto.setText(puesto.getPuesto()+"");
			txtAbreviatura.setText(puesto.getAbreviatura()+"");
			if(puesto.getStatus() == true){chStatus.setSelected(true);}
			else{chStatus.setSelected(false);}
			
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
		if(txtPuesto.getText().equals("")) 			error+= "Bono\n";
		if(txtAbreviatura.getText().equals(""))		error+= "Abreviatura\n";
				
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
				txtPuesto.requestFocus();
			}else{
				panelLimpiar();
				panelEnabledTrue();
				txtFolio.setText(1+"");
				txtFolio.setEditable(false);
				txtPuesto.requestFocus();
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
			chStatus.setSelected(false);
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
		txtPuesto.setEditable(false);
		txtAbreviatura.setEditable(false);
		chStatus.setEnabled(false);
	}		
	
	public void panelEnabledTrue(){	
		txtFolio.setEditable(true);
		txtPuesto.setEditable(true);
		txtAbreviatura.setEditable(true);
		chStatus.setEnabled(true);	
	}
	
	public void panelLimpiar(){	
		txtFolio.setText("");
		txtPuesto.setText("");
		txtAbreviatura.setText("");
		chStatus.setSelected(true);
	}
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Puestos().setVisible(true);
		}catch(Exception e){	}
	}
	
}