package Cat_Servicios;

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

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Obj_Checador.Obj_Asignacion_De_Computadoras_Para_Checador_Por_Establecimiento;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_tabla;
import Obj_Servicios.Obj_Pc_Por_Establecimientos;

@SuppressWarnings("serial")
public class Cat_Asignacion_De_Computadoras_Por_Establecimiento extends JFrame{
	int foliosiguiente=0;
	String Activo ="";
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JTextField txtFolio                = new Componentes().text(new JTextField(), "Folio", 9, "Int");
	JTextField txtNombre_pc            = new Componentes().text(new JCTextField(), "Teclee El Nombre De La Computadora", 80, "String");
	JTextField txtEstablecimientoFiltro= new Componentes().text(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla <<<"    , 400, "String");
	
	JLabel JLBactivo= new JLabel();
    JToolBar menu_toolbar  = new JToolBar();
    
	String status[] = {"VIGENTE","CANCELADO"};
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmb_status = new JComboBox(status);
	
	String establecimiento[] = new Obj_Establecimiento().Combo_Establecimiento();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbEstablecimiento = new JComboBox(establecimiento);
	
	JCButton btnBuscar    = new JCButton("Buscar"  ,"Filter-List-icon16.png","Azul"); 
	JCButton btnEditar    = new JCButton("Editar"       ,"editara.png","Azul");
	JCButton btnGuardar   = new JCButton("Guardar"      ,"Guardar.png","Azul");
	JCButton btnDeshacer  = new JCButton("Deshacer"     ,"deshacer16.png","Azul");
	JCButton btnNuevo     = new JCButton("Nuevo"     ,"Nuevo.png","Azul");
	
	Obj_tabla ObjTab =new Obj_tabla();
	
	int columnas = 5,checkbox=-1;
	public void init_tablafp(){
	    this.tabla.getColumnModel().getColumn(0).setMinWidth(50);
	    this.tabla.getColumnModel().getColumn(0).setMaxWidth(50);
	    this.tabla.getColumnModel().getColumn(1).setMinWidth(150);
	    this.tabla.getColumnModel().getColumn(2).setMinWidth(150);
	    this.tabla.getColumnModel().getColumn(3).setMinWidth(50);
	    this.tabla.getColumnModel().getColumn(3).setMaxWidth(50);
	    this.tabla.getColumnModel().getColumn(4).setMaxWidth(50);
	    this.tabla.getColumnModel().getColumn(4).setMaxWidth(50);
    	String comandof="exec sp_pc_por_establecimientos ";
		String basedatos="26",pintar="si";
		ObjTab.Obj_Refrescar(tabla,modelo, columnas, comandof, basedatos,pintar,checkbox);
    }
	
	@SuppressWarnings("rawtypes")
	public Class[] base (){
		Class[] types = new Class[columnas];
		for(int i = 0; i<columnas; i++){types[i]= java.lang.Object.class;}
		 return types;
	}
	
	public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"Folio","Establecimiento", "Computadora","Estatus","Servicio"}){
		 @SuppressWarnings("rawtypes")
			Class[] types = base();
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
			public boolean isCellEditable(int fila, int columna){return false;}
    };
	            
		JTable tabla = new JTable(modelo);
		JScrollPane ScrollTabla = new JScrollPane(tabla);
		@SuppressWarnings("rawtypes")
		private TableRowSorter trsfiltro;
		String NuevoModifica ="";
		
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Cat_Asignacion_De_Computadoras_Por_Establecimiento(){
			this.setSize(480,400);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/pantalla-de-monitor-de-ordenador-icono-9301-64.png"));
			this.panel.setBorder(BorderFactory.createTitledBorder("Escribe El Nombre De Una Computadora y Selecciona El Establecimiento"));
			this.setTitle("Asignacion De Computadoras A Establecimientos");
			this.trsfiltro = new TableRowSorter(modelo); 
			this.tabla.setRowSorter(trsfiltro);
			
			int x=10, y=20,width=200,height=20, sep=90;
			panel.add(menu_toolbar).setBounds                     (x     ,y     ,width+200 ,height );
			panel.add(new JLabel("Folio Del Registro:")).setBounds(x     ,y+=30 ,width     ,height );
			panel.add(txtFolio).setBounds                         (x+=sep,y     ,100       ,height );
			panel.add(new JLabel("Estatus:")).setBounds           (x+=110,y     ,width     ,height );
			panel.add(cmb_status).setBounds                       (x+=47 ,y     ,98        ,height );
			x=10;
			panel.add(new JLabel("Computadora:")).setBounds       (x     ,y+=30 ,width     ,height );
			panel.add(txtNombre_pc).setBounds                     (x+sep ,y     ,width+55  ,height );
			panel.add(new JLabel("Establecimiento:")).setBounds   (x     ,y+=30 ,150       ,height );
			panel.add(cmbEstablecimiento).setBounds               (x+sep ,y     ,width+55  ,height );
			panel.add(txtEstablecimientoFiltro).setBounds         (x     ,y+=30 ,455       ,height );
			panel.add(ScrollTabla).setBounds                      (x     ,y+=20 ,455       ,width  );
			
			this.menu_toolbar.add(btnBuscar);
		    this.menu_toolbar.addSeparator( );
		    this.menu_toolbar.addSeparator( );
		    this.menu_toolbar.add(btnNuevo);
		    this.menu_toolbar.addSeparator();
		    this.menu_toolbar.addSeparator( );
			this.menu_toolbar.add(btnDeshacer);
			this.menu_toolbar.addSeparator();
			this.menu_toolbar.addSeparator( );
			this.menu_toolbar.add(btnEditar);
			this.menu_toolbar.addSeparator();
			this.menu_toolbar.addSeparator( );
			this.menu_toolbar.add(btnGuardar);
			this.menu_toolbar.setFloatable(false);
			
			txtNombre_pc.setEditable(false);
			cmb_status.setEnabled(false);
			cmbEstablecimiento.setEnabled(false);
			btnEditar.setEnabled(false);
			
			btnGuardar.addActionListener(guardar);
			btnDeshacer.addActionListener(deshacer);
			btnNuevo.addActionListener(nuevo);
			btnEditar.addActionListener(editar);
			btnBuscar.addActionListener(buscar);
			
			txtFolio.addKeyListener(buscar_action);
			txtEstablecimientoFiltro.addKeyListener(opFiltroFolio);
			txtNombre_pc.addKeyListener(enterpasaraEstablecimiento);
			cmbEstablecimiento.addKeyListener(enterpasaraNombre_Pc);

			agregar(tabla);
			cont.add(panel);
            init_tablafp();			
              ///asigna el foco al filtro
						 this.addWindowListener(new WindowAdapter() {
				                public void windowOpened( WindowEvent e ){
				                	txtFolio.requestFocus();
				             }
				        });
             ///deshacer con escape
			             getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
			             getRootPane().getActionMap().put("escape", new AbstractAction(){
			                 public void actionPerformed(ActionEvent e)
			                 {                 	    btnDeshacer.doClick();
			               	    }
			             });
			///guardar con F2
			              getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), "buscar");
			                  getRootPane().getActionMap().put("buscar", new AbstractAction(){
			                      public void actionPerformed(ActionEvent e)
			                      {                 	    btnBuscar.doClick();
				                    	    }
			                 });
             ///guardar con control+G
			             getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_G,Event.CTRL_MASK),"guardar");
			                  getRootPane().getActionMap().put("guardar", new AbstractAction(){
			                      public void actionPerformed(ActionEvent e)
			                      {                 	    btnGuardar.doClick();
			                    	    }
			                 });
		    ///guardar con F12
				              getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0), "guardar");
				                  getRootPane().getActionMap().put("guardar", new AbstractAction(){
				                      public void actionPerformed(ActionEvent e)
				                      {                 	    btnGuardar.doClick();
					                    	    }
				                 });
			///nuevo con F9
			              getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F9, 0), "nuevo");
			                  getRootPane().getActionMap().put("nuevo", new AbstractAction(){
			                      public void actionPerformed(ActionEvent e)
			                      {                 	    btnNuevo.doClick();
				                    	    }
			                 });
		      ///nuevo con F10
				              getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F10, 0), "editar");
				                  getRootPane().getActionMap().put("editar", new AbstractAction(){
				                      public void actionPerformed(ActionEvent e)
				                      {                 	    btnEditar.doClick();
					                    	    }
				                 });
			///editar con Ctrl+E
				              getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_E,Event.CTRL_MASK), "editar");
				                  getRootPane().getActionMap().put("editar", new AbstractAction(){
				                      public void actionPerformed(ActionEvent e)
				                      {                 	    btnEditar.doClick();
					                    	    }
				                 });
			                  
			 ///nuevo con control+N
			              getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_N,Event.CTRL_MASK),"nuevo");
			                   getRootPane().getActionMap().put("nuevo", new AbstractAction(){
			                       public void actionPerformed(ActionEvent e)
			                       {                 	    btnNuevo.doClick();
				                    	    }
			                 });
	}
	
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
    
	KeyListener opFiltroFolio = new KeyListener(){
		public void keyReleased(KeyEvent arg0) {
			ObjTab.Obj_Filtro(tabla, txtEstablecimientoFiltro.getText(), columnas);
		}
		public void keyPressed(KeyEvent arg0) {}
		@Override
		public void keyTyped(KeyEvent arg0) {
		}		
	};
	
	KeyListener enterpasaraEstablecimiento = new KeyListener() {
		public void keyTyped(KeyEvent e){}
		public void keyReleased(KeyEvent e) {}
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				cmbEstablecimiento.requestFocus();
			}
		}
	};
	
	KeyListener enterpasaraNombre_Pc = new KeyListener() {
		public void keyTyped(KeyEvent e){}
		public void keyReleased(KeyEvent e) {}
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				txtNombre_pc.requestFocus();
			}
		}
	};
	
	ActionListener buscar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(txtFolio.getText().equals("")){
				JOptionPane.showMessageDialog(null, "Seleccione Un Registro De La Tabla A La Derecha Para Editar o Teclee El Folio","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}else{
			Obj_Asignacion_De_Computadoras_Para_Checador_Por_Establecimiento tpc = new Obj_Asignacion_De_Computadoras_Para_Checador_Por_Establecimiento().buscar(Integer.parseInt(txtFolio.getText()));
			if(tpc.getFolio() != 0){
			txtNombre_pc.setText(tpc.getNombre_Pc().toString()+"");
			cmbEstablecimiento.setSelectedItem(tpc.getEstablecimiento().toString()+"");
			btnEditar.setEnabled(true);
			btnGuardar.setEnabled(false);
			}else{
				JOptionPane.showMessageDialog(null, "El Folio Buscado No Existe","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
				return;
			}
		  }
		}
		};
	
	ActionListener deshacer = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			txtFolio.setText("");
			txtFolio.setEnabled(true);
			txtEstablecimientoFiltro.setText("");
			btnNuevo.setEnabled(true);
			btnEditar.setEnabled(true);
			txtNombre_pc.setText("");
			txtNombre_pc.setEditable(false);
			cmbEstablecimiento.setEnabled(false);
			cmb_status.setEnabled(false);
			cmb_status.setSelectedIndex(0);
			cmbEstablecimiento.setSelectedIndex(0);
			txtFolio.requestFocus();
		}
	};
	
	ActionListener editar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(txtNombre_pc.getText().equals("")){
				JOptionPane.showMessageDialog(null, "Seleccione Un Registro De La Tabla A La Derecha Para Editar o Teclee El Folio","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}else{
				txtFolio.setEnabled(false);
				btnEditar.setEnabled(false);
				btnNuevo.setEnabled(true);
				cmb_status.setEnabled(true);
				cmbEstablecimiento.setEnabled(true);
				btnEditar.setEnabled(false);
				btnGuardar.setEnabled(true);
				txtNombre_pc.setEditable(true);
				txtNombre_pc.requestFocus(true);
				NuevoModifica="M";
			}
		}
	};
	
	ActionListener nuevo = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
				btnDeshacer.doClick();
				txtFolio.setText(new Obj_Pc_Por_Establecimientos().Nuevo()+1+"");
				txtFolio.setEnabled(false);
				btnGuardar.setEnabled(true);
				txtNombre_pc.setEditable(true);
				txtNombre_pc.requestFocus();
				btnEditar.setEnabled(false);
				cmb_status.setSelectedIndex(0);
				cmb_status.setEnabled(true);
				cmbEstablecimiento.setSelectedIndex(0);
				cmbEstablecimiento.setEnabled(true);
				NuevoModifica="N";
		}
	};
	
    ActionListener guardar = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				    String Mensaje =Valida();
					if(!Mensaje.equals("Para Poder Guardar Es Requerido Alimente:")){
						JOptionPane.showMessageDialog(null, Mensaje, "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
					}else{
						Obj_Pc_Por_Establecimientos Pc_x_Estab =new Obj_Pc_Por_Establecimientos();
						Pc_x_Estab.setFolio(Integer.valueOf(txtFolio.getText().toString().trim()));
						Pc_x_Estab.setNombre_pc(txtNombre_pc.getText().toString().trim());
						Pc_x_Estab.setEstablecimiento(cmbEstablecimiento.getSelectedItem().toString());
						Pc_x_Estab.setEstatus(cmb_status.getSelectedItem().toString().trim());
						Pc_x_Estab.setGetNuevoModifica(NuevoModifica);
        			if(Pc_x_Estab.Guardar()){
    					JOptionPane.showMessageDialog(null,"El Registro Se Guardó Correctamente!","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/aplicara-el-dialogo-icono-6256-32.png"));
    					btnDeshacer.doClick();
    					init_tablafp();
    					return;
    				}else{
    					JOptionPane.showMessageDialog(null,"Error Al Guardar Avise al Administrador del Sistema","Aviso",JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-icono-eliminar5252-64.png"));
    					return;
    				}		
				}
		 }
	 };
	
	private void agregar(final JTable tbl) {
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
	        	if(e.getClickCount()==1){
	        		int fila = tabla.getSelectedRow();
	        		    txtFolio.setText(tabla.getValueAt(fila,0).toString().substring(0,tabla.getValueAt(fila,0).toString().length()));
					    cmbEstablecimiento.setSelectedItem(tabla.getValueAt(fila,1).toString().substring(0,tabla.getValueAt(fila,1).toString().length()));
						txtNombre_pc.setText(tabla.getValueAt(fila,2).toString().substring(0,tabla.getValueAt(fila,2).toString().length()));
						cmb_status.setSelectedItem(tabla.getValueAt(fila,3).toString().substring(0,tabla.getValueAt(fila,3).toString().length()));
						btnEditar.setEnabled(true);
						cmb_status.setEnabled(false);
                        cmbEstablecimiento.setEnabled(false);
						btnEditar.setEnabled(true);
						txtNombre_pc.setEditable(false);
						txtNombre_pc.requestFocus();
						txtFolio.setEnabled(true);
	        	}
	        }
        });
    }
	
	public String Valida(){
	    String Mensaje ="Para Poder Guardar Es Requerido Alimente:";
		if(txtNombre_pc.getText().equals("")) 	      Mensaje+= "-Nombre De La Computadora\n";
		if(cmbEstablecimiento.getSelectedIndex()==(0))Mensaje+= " -Nombre Establecimiento\n";
		return Mensaje;
	}	
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Asignacion_De_Computadoras_Por_Establecimiento().setVisible(true);
		}catch(Exception e){	}
	}
	
}
