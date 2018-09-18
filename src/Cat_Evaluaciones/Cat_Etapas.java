package Cat_Evaluaciones;

import java.awt.Color;
import java.awt.Container;
import java.awt.Event;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import Conexiones_SQL.BuscarSQL;
import Obj_Matrices.Obj_Etapas;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_tabla;

@SuppressWarnings("serial")
public class Cat_Etapas extends JFrame{
	int foliosiguiente=0;
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();

	@SuppressWarnings("rawtypes")
	public Class[] tipos(){
		Class[] tip = new Class[columnas];
		for(int i =0; i<columnas; i++){
			if(i==checkbox){
				tip[i]=java.lang.Boolean.class;
			}else{
				tip[i]=java.lang.Object.class;
			}
		}
		return tip;
	}
	
	int columnas = 4,checkbox=-1;
	public void init_tabla(){
    	this.tabla.getColumnModel().getColumn(0).setMinWidth(30);		
    	this.tabla.getColumnModel().getColumn(1).setMinWidth(400);
    	this.tabla.getColumnModel().getColumn(2).setMinWidth(50);
    	this.tabla.getColumnModel().getColumn(3).setMinWidth(60);
    	
		String comando="matrices_filtro_etapas ";
		
		String basedatos="26",pintar="si";
		new Obj_tabla().Obj_Refrescar(tabla,modelo, columnas, comando, basedatos,pintar,checkbox);
    }
	
	
 public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"Folio", "Etapa", "Abreviatura","Estatus"}){
	 @SuppressWarnings("rawtypes")
		Class[] types = tipos();
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Class getColumnClass(int columnIndex) {
         return types[columnIndex];
     }
		public boolean isCellEditable(int fila, int columna){
			if(columna ==checkbox)
				return true; return false;
		}
    };
    
    JTable tabla = new JTable(modelo);
	public JScrollPane scroll_tabla = new JScrollPane(tabla);
	
	String status[] = {"VIGENTE","CANCELADO"};
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmb_status = new JComboBox(status);
	
	JCButton btnNuevo     = new JCButton("Nuevo"     ,"Nuevo.png"                  ,"Azul");
	JCButton btnEditar    = new JCButton("Modificar" ,"Modify.png"                 ,"Azul");
	JCButton btnGuardar   = new JCButton("Guardar"   ,"Guardar.png"                ,"Azul");
	JCButton btnDeshacer  = new JCButton("Deshacer"  ,"deshacer16.png"             ,"Azul");
	
	JTextField txtFolio       = new Componentes().text      (new JCTextField(), "Folio"      , 9, "Int");
	JTextField txtAbreviatura = new Componentes().text      (new JCTextField(), "Abreviatura", 5, "String");
    JTextField txtFiltro      = new Componentes().textfiltro(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla <<<", 300, "String",tabla,columnas);

	JTextArea txtAreaEtapa= new Componentes().textArea(new JTextArea(), "Etapa",250);
	JScrollPane JScrolAreaEtapa = new JScrollPane(txtAreaEtapa);
	
	JToolBar menu_toolbar = new JToolBar();
	
	String guardar_actualizar="";
	
	public Cat_Etapas(){
		this.setSize(945,280);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setTitle("Etapas");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/catalogo-de-libros-en-blanco-nota-icono-7791-32.png"));
		this.panel.setBorder(BorderFactory.createTitledBorder("Para Editar Un Registro De Doble Click A Un Registro De La Tabla"));
		
	    this.menu_toolbar.add(btnNuevo  );
	    this.menu_toolbar.addSeparator(  );
	    this.menu_toolbar.addSeparator(  );
	    this.menu_toolbar.add(btnEditar   );
	    this.menu_toolbar.addSeparator(  );
	    this.menu_toolbar.addSeparator(  );
		this.menu_toolbar.add(btnGuardar);
		this.menu_toolbar.addSeparator(  );
		this.menu_toolbar.addSeparator(  );
		this.menu_toolbar.add(btnDeshacer );
		this.menu_toolbar.setFloatable(false);
		
		int x = 10, y=15, width=100, height=20;
		
		this.panel.add(menu_toolbar).setBounds              (x     , y     , 350      , height);			
		this.panel.add(new JLabel("Folio:")).setBounds      (x     , y+=25 , width    , height);	
		this.panel.add(txtFolio).setBounds                  (x+=30 , y     , width    , height);	
		this.panel.add(new JLabel("Estatus:")).setBounds    (x+=110, y     , width    , height);	
		this.panel.add(cmb_status).setBounds                (x+=40 , y     , width    , height);	
		this.panel.add(new JLabel("Etapa:")).setBounds      (x=10  , y+=25 , width    , height);
		this.panel.add(JScrolAreaEtapa).setBounds           (x     , y+=15 , 280      , 120   );
		this.panel.add(new JLabel("Abreviatura:")).setBounds(x     , y+=130, width    , height);
		this.panel.add(txtAbreviatura).setBounds            (x+=70 , y     , width    , height);
		this.panel.add(txtFiltro).setBounds                 (x=300 ,y=40   ,627       , height);
		this.panel.add(scroll_tabla).setBounds              (x     ,y+=20  ,627       , 180   );

		this.txtFolio.setEditable(false);
		this.panel(false);
		this.agregar(tabla);
		this.init_tabla();

			btnGuardar.addActionListener(guardar);
			btnDeshacer.addActionListener(deshacer);
			btnNuevo.addActionListener(nuevo);
			btnEditar.addActionListener(editar);
			txtAreaEtapa.addKeyListener(enterpasaraAbreviatura);
			txtAbreviatura.addKeyListener(enterpasaraEtapa);
			
			cont.add(panel);

			this.addWindowListener(new WindowAdapter() {public void windowOpened( WindowEvent e ){
				                	txtFiltro.requestFocus();
				             }});
		
             ///deshacer con escape
			             getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
			             getRootPane().getActionMap().put("escape", new AbstractAction(){
			                 public void actionPerformed(ActionEvent e)
			                 {                 	    btnDeshacer.doClick();
			               	    }
			             });
			             
			             
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_G,Event.CTRL_MASK),"guardar");
		getRootPane().getActionMap().put("guardar", new AbstractAction(){
			                      public void actionPerformed(ActionEvent e)
			                      {                 	    btnGuardar.doClick();
			                    	    }
			                 });

		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0), "guardar");
		getRootPane().getActionMap().put("guardar", new AbstractAction(){
				                      public void actionPerformed(ActionEvent e)
				                      {                 	    btnGuardar.doClick();
					                    	    }
				                 });
			                  
         getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F9, 0            ), "nuevo"                );
         getRootPane().getActionMap().put("nuevo", new AbstractAction(){ public void actionPerformed(ActionEvent e)           {  btnNuevo.doClick();  }});
			                  
         getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F10, 0           ), "editar"               );
         getRootPane().getActionMap().put("editar", new AbstractAction(){public void actionPerformed(ActionEvent e)           {  btnEditar.doClick(); }});

         getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_E,Event.CTRL_MASK), "editar"               );
         getRootPane().getActionMap().put("editar", new AbstractAction(){public void actionPerformed(ActionEvent e)           {  btnEditar.doClick(); }});

		 getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_N,Event.CTRL_MASK), "nuevo"                );
		 getRootPane().getActionMap().put("nuevo", new AbstractAction(){public void actionPerformed(ActionEvent e)            {  btnNuevo.doClick();  }});
	}
	
	KeyListener enterpasaraAbreviatura = new KeyListener() {
		public void keyTyped(KeyEvent e){}
		public void keyReleased(KeyEvent e) {}
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				txtAbreviatura.requestFocus();
			}
		}
	};
	
	KeyListener enterpasaraEtapa = new KeyListener() {
		public void keyTyped(KeyEvent e){}
		public void keyReleased(KeyEvent e) {}
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				txtAreaEtapa.requestFocus();
			}
		}
	};
	
	ActionListener deshacer = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			panel(false);
			panellimpiar();
			init_tabla();
			txtFiltro.requestFocus();
			btnNuevo.setEnabled(true);
			btnEditar.setEnabled(true);
		}
	};
	
	ActionListener editar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(txtFolio.getText().equals("")){
				JOptionPane.showMessageDialog(null, "No hay registro que Editar","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
				return;
			}else{
				panel(true);
				btnEditar.setEnabled(false);
				btnNuevo.setEnabled(false);
				btnGuardar.setEnabled(true);
				cmb_status.setEnabled(true);
				txtAreaEtapa.requestFocus(true);
			}
		}
	};
	
	ActionListener nuevo = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			guardar_actualizar="N";
			String folio="";
			try {
				folio= new BuscarSQL().folio_siguiente(26+"");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			panellimpiar();
			panel(true);
			btnEditar.setEnabled(false);
			cmb_status.setEnabled(true);
			btnGuardar.setEnabled(true);
			txtFolio.setText(folio);
			txtAreaEtapa.requestFocus();
		}
	};
	
	ActionListener guardar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
				       try {
					
					if(validaCampos()!="") {
						JOptionPane.showMessageDialog(null, "Los Siguientes Campos Son Requeridos:\n "+validaCampos(), "Aviso" ,JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
						return;
					} else{
					Obj_Etapas Etapas = new Obj_Etapas();
					
					if(Etapas.getFolio() == Integer.parseInt(txtFolio.getText())){}else{
						Etapas.setFolio(Integer.parseInt(txtFolio.getText()));
						Etapas.setEtapa(txtAreaEtapa.getText().toUpperCase().trim());
						Etapas.setAbreviatura(txtAbreviatura.getText().toUpperCase().trim());
						Etapas.setStatus(cmb_status.getSelectedItem().toString());
						
   				    if(Etapas.guardar()){
                          init_tabla();									
                          JOptionPane.showMessageDialog(null,"El Registró Se Guardó Correctamente","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
					  	  btnDeshacer.doClick();
						 return;
									
   				    }else{
						JOptionPane.showMessageDialog(null, "El Registro No Se Guardó", "Avise Al Administrador Del Sistema !!!",JOptionPane.ERROR_MESSAGE, new ImageIcon("Imagen/usuario-icono-eliminar5252-64.png"));
						return;
   				    }
				    }
				  }
				} catch (NumberFormatException e1) {
					e1.printStackTrace();
				} 				
					
		}
	};
	
	  private void agregar(final JTable tbl) {
		  tbl.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e){
			 if(e.getClickCount() == 2){agregarf(); }
			}
			public void mousePressed(MouseEvent e) {}
			public void mouseExited(MouseEvent e)  {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseClicked(MouseEvent e) {}
		});
		  
		tbl.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e)  {
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					agregarf();	
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {}
			@Override
			public void keyTyped(KeyEvent e)    {}
		});
      }
	    
		private void agregarf() {
			int fila = tabla.getSelectedRow();
            panellimpiar();
            panel(false);
			txtFolio.setText(tabla.getValueAt(fila,0).toString().substring(0,tabla.getValueAt(fila,0).toString().length())+"");
			txtAreaEtapa.setText(tabla.getValueAt(fila,1).toString().substring(0,tabla.getValueAt(fila,1).toString().length()));
			txtAbreviatura.setText(tabla.getValueAt(fila,2).toString().substring(0,tabla.getValueAt(fila,2).toString().length()));
			cmb_status.setSelectedItem(tabla.getValueAt(fila,3).toString().substring(0,tabla.getValueAt(fila,3).toString().length()));
			btnEditar.setEnabled(true);
	    }

		
	public void panel(boolean boleano){	
		txtAreaEtapa.setEditable(boleano);
		txtAbreviatura.setEditable(boleano);
		cmb_status.setEnabled(boleano);
		btnEditar.setEnabled(boleano);
		btnGuardar.setEnabled(boleano);
		
		if(boleano) {
			txtAreaEtapa.setBackground(new Color(Integer.parseInt("FFFFFF",16)));
		}else {
			txtAreaEtapa.setBackground(new Color(Integer.parseInt("EBEBEB",16)));
		}
	}
	
	public void panellimpiar(){	
		txtAreaEtapa.setText("");
		txtAbreviatura.setText("");
		txtFiltro.setText("");
		txtFolio.setText("");
		cmb_status.setSelectedIndex(0);
	}
	
	private String validaCampos(){
		String error="";
		if(txtAreaEtapa.getText().equals("")) 		error+= "Etapa\n";
		return error;
	}
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Etapas().setVisible(true);
		}catch(Exception e){	}
	}
	
}
