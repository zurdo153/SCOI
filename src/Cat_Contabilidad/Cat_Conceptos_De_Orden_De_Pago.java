package Cat_Contabilidad;

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
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Conexiones_SQL.Connexion;
import Obj_Contabilidad.Obj_Conceptos_De_Ordenes_De_Pago;
import Obj_Principal.Componentes;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_Filtro_Dinamico_Plus;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Conceptos_De_Orden_De_Pago extends JFrame{
	int foliosiguiente=0;
	String Activo ="";
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JTextField txtFolio = new Componentes().text(new JCTextField(), "Folio", 9, "Int");
	JTextField txtConcepto= new Componentes().text(new JCTextField(), "Concepto De La Orden De Pago",100,"String");
	JTextField txtfiltro_tabla = new Componentes().text(new JCTextField(), "Teclea Aqui Para Buscar En La Tabla", 30, "String");
	
	JLabel JLBactivo= new JLabel();
	
	String status[] = {"VIGENTE","CANCELADO"};
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmb_status = new JComboBox(status);
	
	JButton btnBuscar = new JButton("Buscar",new ImageIcon("imagen/buscar.png"));
	JButton btnSalir = new JButton("Salir",new ImageIcon("imagen/salir16.png"));
	JButton btnDeshacer = new JButton("Deshacer",new ImageIcon("imagen/deshacer16.png"));
	JButton btnGuardar = new JButton("Guardar",new ImageIcon("imagen/Guardar.png"));
	JButton btnEditar = new JButton("Editar",new ImageIcon("imagen/editara.png"));
	JButton btnNuevo = new JButton("Nuevo",new ImageIcon("imagen/Nuevo.png"));
	
	 public static DefaultTableModel modelo = new DefaultTableModel(null,new String[]{"Folio","Concepto", "Estatus"}){
	            @SuppressWarnings("rawtypes")
	            Class[] types = new Class[]{
	                       java.lang.Object.class,
	                       java.lang.Object.class,  
	                       java.lang.Object.class
	        };
	            @SuppressWarnings({ "rawtypes", "unchecked" })
	            public Class getColumnClass(int columnIndex) {
	                    return types[columnIndex];
	            }
	            
	     public boolean isCellEditable(int fila, int columna){
	                    switch(columna){
	                            case 0  : return false; 
	                            case 1  : return false; 
	                            case 2  : return false; 
	                    }
	                     return false;
	             }
	    };
		JTable tabla = new JTable(modelo);
		JScrollPane scrollAsignado = new JScrollPane(tabla);
		@SuppressWarnings("rawtypes")
		private TableRowSorter trsfiltro;
		
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Cat_Conceptos_De_Orden_De_Pago(){
			this.setSize(640,700);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/tamano-del-guion-menos-icono-8053-64.png"));
			panel.setBorder(BorderFactory.createTitledBorder("Catalogo de Conceptos De Una Orden De Pago"));
			
			this.setTitle("Conceptos De Orden De Pago");
			trsfiltro = new TableRowSorter(modelo); 
			tabla.setRowSorter(trsfiltro);
			
			int x = 10, y=30, width=100,height=20 ;
			panel.add(new JLabel("Folio:")).setBounds   (x      ,y   ,width  ,height);
			panel.add(txtFolio).setBounds               (x+=50  ,y   ,width  ,height);
			panel.add(btnBuscar).setBounds              (x+=115 ,y   ,width  ,height);
			panel.add(btnEditar).setBounds              (x+=115 ,y   ,width  ,height);
			panel.add(btnNuevo).setBounds               (x+=115 ,y   ,width  ,height);
			panel.add(btnDeshacer).setBounds            (x+=115 ,y   ,width  ,height);
			x=10;
			panel.add(new JLabel("Concepto:")).setBounds(x     ,y+=25,width  ,height);
			panel.add(txtConcepto).setBounds            (x+50  ,y    ,width*4,height);
			panel.add(new JLabel("Estatus:")).setBounds (x+460 ,y    ,width  ,height);
			panel.add(cmb_status).setBounds             (x+510 ,y    ,width  ,height);
			panel.add(txtfiltro_tabla).setBounds        (x     ,y+=35,width*5,height);
			panel.add(btnGuardar).setBounds             (x+510 ,y    ,width  ,height);
			panel.add(getPanelTabla()).setBounds        (x     ,y+=20,608    ,550);
			
			txtConcepto.setEditable(false);
			cmb_status.setEnabled(false);
			btnEditar.setEnabled(false);
			
			btnGuardar.addActionListener(guardar);
			btnSalir.addActionListener(salir);
			btnDeshacer.addActionListener(deshacer);
			btnNuevo.addActionListener(nuevo);
			btnEditar.addActionListener(editar);
			btnBuscar.addActionListener(buscar);
			
			txtFolio.addKeyListener(buscar_action);
			
			txtfiltro_tabla.addKeyListener(opFiltroGeneral);
			agregar(tabla);
			cont.add(panel);
			
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
	private JScrollPane getPanelTabla()	{	
			tabla.getTableHeader().setReorderingAllowed(false) ;
			tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			
		    this.tabla.getColumnModel().getColumn(0).setMinWidth(60);
		    this.tabla.getColumnModel().getColumn(0).setMaxWidth(60);
		    this.tabla.getColumnModel().getColumn(1).setMinWidth(445);
		    this.tabla.getColumnModel().getColumn(1).setMaxWidth(450);
		    this.tabla.getColumnModel().getColumn(2).setMinWidth(100);
		    this.tabla.getColumnModel().getColumn(2).setMaxWidth(100);
		    
		    tabla.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 	
		    tabla.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
		    tabla.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
			
			refrestabla();
		    JScrollPane scrol = new JScrollPane(tabla);
		    return scrol; 
	    }

	private void refrestabla(){
		modelo.setRowCount(0);
		Statement s;
		ResultSet rs;
		try {
			Connexion con = new Connexion();
			s = con.conexion().createStatement();
			rs = s.executeQuery("select folio_concepto,concepto_orden_de_pago, case when status='V' then 'VIGENTE' else 'CANCELADO' end  as status from tb_conceptos_de_orden_de_pago order by concepto_orden_de_pago asc");
			while (rs.next())
			{  String [] fila = new String[3];
			   fila[0] = rs.getString(1).trim();
			   fila[1] = rs.getString(2).trim();
			   fila[2] = rs.getString(3).trim(); 
			   modelo.addRow(fila); 
			}	
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en Cat_Etapas en la funcion refrestabla  SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
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
    
	KeyListener opFiltroGeneral = new KeyListener(){
		public void keyReleased(KeyEvent arg0) {
			int[] columnas = {0,1,2};
			new Obj_Filtro_Dinamico_Plus(tabla, txtfiltro_tabla.getText().toUpperCase(), columnas);
		}
		public void keyPressed(KeyEvent arg0) {}
		@Override
		public void keyTyped(KeyEvent arg0) {
		}		
	};
	
	KeyListener enterpasaraNombre_Pc = new KeyListener() {
		public void keyTyped(KeyEvent e){}
		public void keyReleased(KeyEvent e) {}
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				txtConcepto.requestFocus();
			}
		}
	};
	
	ActionListener salir = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			dispose();
		}
	};
	
	ActionListener buscar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(txtFolio.getText().equals("")){
				JOptionPane.showMessageDialog(null, "Seleccione Un Registro De La Tabla A La Derecha Para Editar o Teclee El Folio","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
				txtFolio.requestFocus();
				return;
			}else{
				Obj_Conceptos_De_Ordenes_De_Pago concepto = new Obj_Conceptos_De_Ordenes_De_Pago().buscar(Integer.parseInt(txtFolio.getText()));
			if(concepto.getFolio() != 0){
			txtConcepto.setText(concepto.getConcepto().toString()+"");
			cmb_status.setSelectedItem(concepto.getEstatus().toString());
			btnEditar.setEnabled(true);
			btnGuardar.setEnabled(false);
			txtFolio.setEnabled(false);
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
			txtFolio.setEditable(true);
			txtfiltro_tabla.setText("");
			btnNuevo.setEnabled(true);
			btnEditar.setEnabled(true);
			txtConcepto.setText("");
			txtConcepto.setEditable(false);
			cmb_status.setEnabled(false);
			cmb_status.setSelectedIndex(0);
			txtFolio.requestFocus();
			txtFolio.setEnabled(true);
		}
	};
	
	ActionListener editar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(txtConcepto.getText().equals("")){
				JOptionPane.showMessageDialog(null, "Seleccione Un Registro De La Tabla A La Derecha Para Editar o Teclee El Folio","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}else{
				txtFolio.setEditable(false);
				btnEditar.setEnabled(false);
				btnNuevo.setEnabled(true);
				cmb_status.setEnabled(true);
				btnEditar.setEnabled(false);
				btnGuardar.setEnabled(true);
				txtConcepto.setEditable(true);
				txtConcepto.requestFocus(true);
			}
		}
	};
	
	ActionListener nuevo = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			busqueda_proximo_folio();
			if(foliosiguiente != 0){
				btnDeshacer.doClick();
				txtFolio.setText( busqueda_proximo_folio()+"");
				txtFolio.setEnabled(false);
				btnGuardar.setEnabled(true);
				txtConcepto.setEditable(true);
				txtConcepto.requestFocus();
				btnEditar.setEnabled(false);
				cmb_status.setSelectedIndex(0);
				cmb_status.setEnabled(true);
			}else{
				btnDeshacer.doClick();
				txtFolio.setText(1+"");
				txtFolio.setEditable(false);
				btnGuardar.setEnabled(true);
				txtConcepto.setEditable(true);
				txtConcepto.requestFocus();
				btnEditar.setEnabled(false);
				cmb_status.setSelectedIndex(0);
				cmb_status.setEnabled(true);
			}
		}
	};
	
	ActionListener guardar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
				try {
					if(validaCampos()!="") {
						JOptionPane.showMessageDialog(null, "Los Siguientes Datos Son Requeridos:\n "+validaCampos(), "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
						return;
					} else{
						int[] columnas = {0,1,2};
						new Obj_Filtro_Dinamico_Plus(tabla,"", columnas);
						
						Obj_Conceptos_De_Ordenes_De_Pago concepto = new Obj_Conceptos_De_Ordenes_De_Pago().buscar(Integer.parseInt(txtFolio.getText()));
						
					if(concepto.getFolio() == Integer.parseInt(txtFolio.getText())){
						if(JOptionPane.showConfirmDialog(null, "El registro ya existe, ¿desea cambiarlo?") == 0){
							concepto.setFolio(Integer.parseInt(txtFolio.getText()));
							concepto.setConcepto(txtConcepto.getText().toUpperCase().trim());
							concepto.setEstatus(cmb_status.getSelectedItem().toString().trim());
							if(validaCampos()!="") {
								JOptionPane.showMessageDialog(null, "Los Siguientes Datos Son Requeridos:\n"+validaCampos(), "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
								return;
							}else{
								if(concepto.guardar()){
								    refrestabla();
									JOptionPane.showMessageDialog(null,"El Registró Se Actualizó Correctamente","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//aplicara-el-dialogo-icono-6256-32.png"));
									btnDeshacer.doClick();
									return;
								}else{
									JOptionPane.showMessageDialog(null, "El Registro No Se Actualizó", "Error !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-icono-eliminar5252-64.png"));
									return;
								}
							}
						}else{
							return;
						}
					}else{
						concepto.setFolio(Integer.parseInt(txtFolio.getText()));
						concepto.setConcepto(txtConcepto.getText().toUpperCase().trim());
						concepto.setEstatus(cmb_status.getSelectedItem().toString().trim());
								if(concepto.guardar()){
									
									refrestabla();
									JOptionPane.showMessageDialog(null,"El Registró Se Guardó  Correctamente","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//aplicara-el-dialogo-icono-6256-32.png"));
									btnDeshacer.doClick();
									return;
								}else{
									JOptionPane.showMessageDialog(null, "El Registro No Se Guardó", "Error !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-icono-eliminar5252-64.png"));
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
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
	        	if(e.getClickCount()==1){
	        		int fila = tabla.getSelectedRow();
	        		    txtFolio.setText(tabla.getValueAt(fila,0).toString().substring(0,tabla.getValueAt(fila,0).toString().length()));
						txtConcepto.setText(tabla.getValueAt(fila,1).toString().substring(0,tabla.getValueAt(fila,1).toString().length()));
						cmb_status.setSelectedItem(tabla.getValueAt(fila,2).toString().substring(0,tabla.getValueAt(fila,2).toString().length()));
						btnEditar.setEnabled(true);
						cmb_status.setEnabled(false);
						btnEditar.setEnabled(true);
						txtConcepto.setEditable(false);
						txtConcepto.requestFocus();
						txtFolio.setEditable(true);
						txtFolio.setEnabled(false);
	        	}
	        }
        });
    }
	
	public int  busqueda_proximo_folio() {
		Connexion con = new Connexion();
		String query = "select max(folio_concepto)+1 as 'Maximo' from tb_conceptos_de_orden_de_pago ";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
		    ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				 foliosiguiente =(rs.getInt(1));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error");
			JOptionPane.showMessageDialog(null, "Error en la funcion busqueda_proximo_folio()"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			return foliosiguiente ;
		}
		finally{
			 if (stmt != null) { try {
				stmt.close();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Error en la funcion busqueda_proximo_folio()"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
				e.printStackTrace();
			} }
		}
		return foliosiguiente;
			}
	
	private String validaCampos(){
		String error="";
		if(txtConcepto.getText().equals("")) 		error+= "-Nombre Del Concepto\n";
		return error;
	}
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Conceptos_De_Orden_De_Pago().setVisible(true);
		}catch(Exception e){	}
	}
	
}
