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

import Conexiones_SQL.Connexion;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_tabla;
import Obj_Servicios.Obj_Marcas_De_Activos;

@SuppressWarnings("serial")
public class Cat_Marcas_De_Activos extends JFrame{
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	Connexion con = new Connexion();
	
	
	Obj_tabla ObjTab =new Obj_tabla();
	int columnas = 3,checkbox=-1;
	public void init_tablaf(){
    	this.tabla.getColumnModel().getColumn(0).setMinWidth(45);
    	this.tabla.getColumnModel().getColumn(0).setMaxWidth(45);
    	this.tabla.getColumnModel().getColumn(1).setMinWidth(270);
    	this.tabla.getColumnModel().getColumn(2).setMinWidth(55);
    	
		String comandof="select folio,nombre,case when status='V' then 'Vigente' else 'Cancelado' end as estatus from tb_marca_de_equipo order by nombre";
		String basedatos="26",pintar="si";
		ObjTab.Obj_Refrescar(tabla,modelo, columnas, comandof, basedatos,pintar,checkbox);
    }
	
	@SuppressWarnings("rawtypes")
	public Class[] base (){
		Class[] types = new Class[columnas];
		for(int i = 0; i<columnas; i++){types[i]= java.lang.Object.class;}
		 return types;
	}
	
	public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"Folio","Marca","Estatus"}){
		 @SuppressWarnings("rawtypes")
			Class[] types = base();
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
			public boolean isCellEditable(int fila, int columna){return false;}
	};
	
	JTable tabla = new JTable(modelo);
	public JScrollPane scroll_tabla = new JScrollPane(tabla);
     @SuppressWarnings({ "rawtypes" })
    private TableRowSorter trsfiltro;
	     
	JTextField txtBuscar  = new Componentes().text(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<", 300, "String");
	JTextField txtFolio   = new Componentes().text(new JCTextField(), "Folio", 10, "Int");
	JTextField txtMarca = new Componentes().text(new JCTextField(), "Marca", 100, "String");
	
	String status[] = {"Vigente","Cancelado"};
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmb_status = new JComboBox(status);
	
	JCButton btnNuevo     = new JCButton("Nuevo"     ,"Nuevo.png"                  ,"Azul");
	JCButton btnEditar    = new JCButton("Modificar" ,"Modify.png"                 ,"Azul");
	JCButton btnGuardar   = new JCButton("Guardar"   ,"Guardar.png"                ,"Azul");
	JCButton btnDeshacer  = new JCButton("Deshacer"  ,"deshacer16.png"             ,"Azul");
	
	JToolBar menu_toolbar = new JToolBar();
	
	String NuevoModifica ="";
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Cat_Marcas_De_Activos(){
		this.setSize(445,500);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/favoritos-ver-boton-icono-8318-32.png"));
		this.panel.setBorder(BorderFactory.createTitledBorder("Marcas"));
		this.setTitle("Marcas");
		
	    this.menu_toolbar.add(btnEditar  );
	    this.menu_toolbar.addSeparator(  );
	    this.menu_toolbar.addSeparator(  );
	    this.menu_toolbar.add(btnNuevo   );
	    this.menu_toolbar.addSeparator(  );
	    this.menu_toolbar.addSeparator(  );
		this.menu_toolbar.add(btnDeshacer);
		this.menu_toolbar.addSeparator(  );
		this.menu_toolbar.addSeparator(  );
		this.menu_toolbar.add(btnGuardar );
		
		this.menu_toolbar.setFloatable(false);
		
		 int x=15, y=20,width=120,height=20,sep=50;
		this.panel.add(menu_toolbar).setBounds               (x     ,y      ,width*4-60 ,height );
		this.panel.add(new JLabel("Folio:")).setBounds       (x     ,y+=40  ,width      ,height );
		this.panel.add(txtFolio).setBounds                   (x+=sep,y      ,width      ,height );
		this.panel.add(new JLabel("Estatus:")).setBounds     (x+=170,y      ,width      ,height );
		this.panel.add(cmb_status).setBounds                 (x+=70 ,y      ,width      ,height );
		 x=15;width=120;
		this.panel.add(new JLabel("Marca:")).setBounds       (x     ,y+=30  ,width      ,height );
		this.panel.add(txtMarca).setBounds                   (x+=sep,y      ,width*3    ,height );
         width=410;
		this.panel.add(txtBuscar).setBounds                  (x=15  ,y+=30  ,width      ,height );
		this.panel.add(scroll_tabla).setBounds               (x     ,y+=20  ,width      ,320    );
		
		this.init_tablaf();
		this.panelEnabledFalse();
		
		this.agregar(tabla);
		this.txtBuscar.addKeyListener  (opFiltro );
		this.btnNuevo.addActionListener(nuevo    );
		
		trsfiltro = new TableRowSorter(modelo); 
		tabla.setRowSorter(trsfiltro);
		
		btnGuardar.addActionListener(guardar);
		btnDeshacer.addActionListener(deshacer);
		
		btnEditar.addActionListener(editar);
		
		cont.add(panel);
		
		 getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape"                );
         getRootPane().getActionMap().put("escape", new AbstractAction(){ public void actionPerformed(ActionEvent e){ btnDeshacer.doClick(); }  });
         
         getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F9, 0), "nuevo"                     );
         getRootPane().getActionMap().put("nuevo", new AbstractAction(){ public void actionPerformed(ActionEvent e){ btnNuevo.doClick();     }  });
         getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_N,Event.CTRL_MASK),"nuevo"          );
         getRootPane().getActionMap().put("nuevo", new AbstractAction(){ public void actionPerformed(ActionEvent e) { btnNuevo.doClick();    }  });
         
         getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_G,Event.CTRL_MASK),"guardar"        );
         getRootPane().getActionMap().put("guardar", new AbstractAction(){public void actionPerformed(ActionEvent e){btnGuardar.doClick();   }  });
         getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0), "guardar"                  );
         getRootPane().getActionMap().put("guardar", new AbstractAction(){public void actionPerformed(ActionEvent e) { btnGuardar.doClick(); }  });
             
	}
	
	public void panelEnabledFalse(){
		txtFolio.setEditable  (false);
		txtMarca.setEditable(false);
		btnEditar.setEnabled  (false);
        btnGuardar.setEnabled (false);		
		cmb_status.setEnabled (false);
		this.addWindowListener(new WindowAdapter() {
            public void windowOpened( WindowEvent e ){
            	txtBuscar.requestFocus();
           }
        });
	}		
	
	public void panelEnabledTrue(){	
		txtMarca.setEditable(true );
		btnEditar.setEnabled  (false);
		btnNuevo.setEnabled   (false);
		cmb_status.setEnabled(true);
		this.addWindowListener(new WindowAdapter() {
            public void windowOpened( WindowEvent e ){
             txtMarca.requestFocus();
           }
        });
	}
	
	public void panelLimpiar(){	
		txtFolio.setText("");
		txtMarca.setText("");
		txtBuscar.setText("");
		cmb_status.setSelectedIndex(0);
		ObjTab.Obj_Filtro(tabla, txtBuscar.getText(), columnas);
	}	
	
	private void agregar(final JTable tbl) {
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
	        	if(e.getClickCount()==1){
	        		int fila = tabla.getSelectedRow();
	        		        		
					txtFolio.setText  (tabla.getValueAt(fila,0)+"");
					txtMarca.setText(tabla.getValueAt(fila,1)+"");
				    cmb_status.setSelectedItem(tabla.getValueAt(fila,2));
					btnEditar.setEnabled(true);
	        	}
	        }
        });
    }
	
	KeyListener opFiltro = new KeyListener(){
		public void keyReleased(KeyEvent arg0) {
			ObjTab.Obj_Filtro(tabla, txtBuscar.getText().toUpperCase(), columnas);
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}		
	};

	ActionListener nuevo = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			int CantFilas=modelo.getRowCount();
			if(CantFilas != 0){
				panelEnabledTrue();
				panelLimpiar();
				txtFolio.setText(CantFilas+"");
				txtMarca.requestFocus();
				btnGuardar.setEnabled(true);
			}else{
				panelLimpiar();
				panelEnabledTrue();
				txtFolio.setText(CantFilas+"");
				btnGuardar.setEnabled(true);
			}
			NuevoModifica="N";
		}
	};
	
	ActionListener deshacer = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			panelLimpiar();
			panelEnabledFalse();
			init_tablaf();
			btnNuevo.setEnabled(true);
		}
	};
	
	ActionListener editar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			panelEnabledTrue();
			cmb_status.setEnabled(true);
			txtFolio.setEditable(false);
			btnEditar.setEnabled(false);
			btnGuardar.setEnabled(true);
			NuevoModifica="M";
		}		
		};
	
	public String Valida(){
		String Folio   = txtFolio.getText().trim();
		String Marca = txtMarca.getText().trim();
	    String Mensaje ="";
	    if(txtMarca.getText().equals("")){Mensaje+="\nEs Requerido Para Guardar Que Se Alimente Un Marca"; }
		for(int i =0; i<tabla.getRowCount(); i++){
			    	  if(!Folio.equals(tabla.getValueAt(i, 0).toString().trim()))  {   	
			    		  if(Marca.equals(tabla.getValueAt(i, 1).toString().trim())){	Mensaje+="\nEl Marca :"+Marca+" Existe En El Folio:"+tabla.getValueAt(i, 0).toString().trim();	    	  };
			    	  };
        }
		return Mensaje;
	}	
	
	ActionListener guardar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			Obj_Marcas_De_Activos Marca = new Obj_Marcas_De_Activos();
				
			    String Mensaje =Valida();
			if(!Mensaje.equals("")){
				JOptionPane.showMessageDialog(null, Mensaje, "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
			}else{
				Marca.setFolio(Integer.valueOf(txtFolio.getText()));
				Marca.setMarca_De_Activo(txtMarca.getText().toString().toUpperCase());
				Marca.setEstatus(cmb_status.getSelectedItem().toString());
				Marca.setGuardaModifica(NuevoModifica);
				if(Marca.guardar()){
					JOptionPane.showMessageDialog(null,"El Registro Se Guardó Correctamente!","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/aplicara-el-dialogo-icono-6256-32.png"));
					btnDeshacer.doClick();
					return;
				}else{
					JOptionPane.showMessageDialog(null,"Error Al Guardar Avise al Administrador del Sistema","Aviso",JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-icono-eliminar5252-64.png"));
					return;
				}		
			}
		}
	};
	
	public static void main(String args[]){
		try{UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Marcas_De_Activos().setVisible(true);
		}catch(Exception e){System.err.println("Error en Main: "+e.getMessage());
		}
	}
	
}