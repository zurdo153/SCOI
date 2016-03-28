package Cat_Matrices;

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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import Conexiones_SQL.Connexion;
import Obj_Matrices.Obj_Aspectos_De_La_Etapa;
import Obj_Principal.Componentes;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_Filtro_Dinamico_Plus;
import Obj_Principal.Obj_Refrescar;

@SuppressWarnings("serial")
public class Cat_Aspectos_De_La_Etapa extends JFrame{
	int foliosiguiente=0;
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JTextField txtFolio = new Componentes().text(new JTextField(), "Folio", 9, "Int");
	JTextArea  txtAreaAspectoEtapa= new Componentes().textArea(new JTextArea(), "Aspectos De La Etapa",250);
	JTextField txtAbreviatura = new Componentes().text(new JTextField(), "Abreviatura", 5, "String");
	
	JScrollPane JScrolAreaAspecto = new JScrollPane(txtAreaAspectoEtapa);
	
	String status[] = {"VIGENTE","CANCELADO"};
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmb_status = new JComboBox(status);
	
	JButton btnBuscar = new JButton(new ImageIcon("imagen/buscar.png"));
	JButton btnSalir = new JButton("Salir",new ImageIcon("imagen/salir16.png"));
	JButton btnDeshacer = new JButton("Deshacer",new ImageIcon("imagen/deshacer16.png"));
	JButton btnGuardar = new JButton("Guardar",new ImageIcon("imagen/Guardar.png"));
	JButton btnEditar = new JButton("Editar",new ImageIcon("imagen/editara.png"));
	JButton btnNuevo = new JButton("Nuevo",new ImageIcon("imagen/Nuevo.png"));
	
	public JTextField txtFiltro = new Componentes().text(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla <<<", 300, "String");

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
    	this.tabla.getColumnModel().getColumn(1).setMinWidth(430);
    	this.tabla.getColumnModel().getColumn(2).setMinWidth(50);
    	this.tabla.getColumnModel().getColumn(2).setMinWidth(60);
    	
		String comando="select folio_aspecto,aspecto_de_la_etapa,abreviatura,case when status='1' then (select 'VIGENTE') when status=0 then (select 'CANCELADO') end as estatus" +
 				         " from tb_aspectos_de_la_etapa order by aspecto_de_la_etapa asc";
		String basedatos="26",pintar="si";
		new Obj_Refrescar(tabla,modelo, columnas, comando, basedatos,pintar,checkbox);
    }
	
	
 public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"Folio", "Aspectos De La Etapa", "Abreviatura","Estatus"}){
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
	
		
	public Cat_Aspectos_De_La_Etapa(){
		
		
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/catalogo-de-libros-en-blanco-nota-icono-7791-32.png"));
			panel.setBorder(BorderFactory.createTitledBorder("Aspectos De La Etapa"));
			
			this.setTitle("Aspectos");
			int x = 45, y=30, ancho=100;
			
			panel.add(new JLabel("Folio:")).setBounds(x-25,y-15,ancho,20);
			panel.add(txtFolio).setBounds(60,y-15,ancho+60,20);
			panel.add(btnBuscar).setBounds(230,y-15,32,20);
			
			panel.add(cmb_status).setBounds(277,y-15,98,20);
			
			panel.add(new JLabel("Aspecto:")).setBounds(x-25,y+=15,80,20);
			panel.add(JScrolAreaAspecto).setBounds(60,y,ancho+100,200);
			
			txtAreaAspectoEtapa.setLineWrap(true); 
			txtAreaAspectoEtapa.setWrapStyleWord(true);
			
			panel.add(btnNuevo).setBounds(x+(ancho*2)+30,y,100,20);
			panel.add(btnEditar).setBounds(x+(ancho*2)+30,y+=30,100,20);
			panel.add(btnGuardar).setBounds(x+ancho*2+30,y+=30,100,20);
			
			panel.add(btnDeshacer).setBounds(x+(ancho*2)+30,y+=90,100,20);
			panel.add(btnSalir).setBounds(x+(ancho*2)+30,y+=30,100,20);
			
			panel.add(new JLabel("Abreviatura:")).setBounds(x-25,y+=30,ancho,20);
			panel.add(txtAbreviatura).setBounds(90,y,ancho+70,20);
			
			panel.add(txtFiltro).setBounds((x*2)+(ancho*3)-10,15,470,20);
			
			panel.add(scroll_tabla).setBounds((x*2)+(ancho*3)-10,35,623,240);
			
			txtAreaAspectoEtapa.setEditable(false);
			txtAbreviatura.setEditable(false);
			cmb_status.setEnabled(false);
			
			btnEditar.setEnabled(false);
			
			txtFolio.addKeyListener(buscar_action);
			btnGuardar.addActionListener(guardar);
			btnSalir.addActionListener(salir);
			btnBuscar.addActionListener(buscar);
			btnDeshacer.addActionListener(deshacer);
			btnNuevo.addActionListener(nuevo);
			btnEditar.addActionListener(editar);
			
			txtFiltro.addKeyListener(op_filtro);
			
			txtAreaAspectoEtapa.addKeyListener(enterpasaraAbreviatura);
			txtAbreviatura.addKeyListener(enterpasaraEtapa);
			
			agregar(tabla);
			init_tabla();
			
			cont.add(panel);
			
			this.setSize(1024,315);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			
              ///asigna el foco al filtro
						 this.addWindowListener(new WindowAdapter() {
				                public void windowOpened( WindowEvent e ){
				                	txtFiltro.requestFocus();
				             }
				        });
		
             ///deshacer con escape
			             getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
			             getRootPane().getActionMap().put("escape", new AbstractAction(){
			                 public void actionPerformed(ActionEvent e)
			                 {                 	    btnDeshacer.doClick();
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
			                  
		      ///editar con F10
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
	
    
	KeyListener op_filtro = new KeyListener(){
		public void keyReleased(KeyEvent arg0) {
			int[] columnas ={0,1,2};
			new Obj_Filtro_Dinamico_Plus(tabla, txtFiltro.getText().toUpperCase(), columnas);
		}
		public void keyTyped(KeyEvent arg0)   {}
		public void keyPressed(KeyEvent arg0) {}		
	};
	
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
				txtAreaAspectoEtapa.requestFocus();
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
	
	ActionListener salir = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			dispose();
		}
	};
	
	
	ActionListener deshacer = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			txtFolio.setEditable(true);
			txtFolio.requestFocus();
			btnNuevo.setEnabled(true);
			btnEditar.setEnabled(true);
			txtFolio.setText("");
			txtAreaAspectoEtapa.setText("");
			txtAbreviatura.setText("");
			txtAreaAspectoEtapa.setEditable(false);
			txtAbreviatura.setEditable(false);
			cmb_status.setEnabled(false);
		}
	};
	
	ActionListener editar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(txtFolio.getText().equals("")){
				JOptionPane.showMessageDialog(null, "No hay registro que Editar","Error",JOptionPane.WARNING_MESSAGE);
				return;
			}else{
				panelEnabledTrue();
				txtFolio.setEditable(false);
				btnEditar.setEnabled(false);
				btnNuevo.setEnabled(true);
				cmb_status.setEnabled(true);
				btnEditar.setEnabled(false);
				btnGuardar.setEnabled(true);
				txtAreaAspectoEtapa.requestFocus(true);
			}
		}
	};
	
	ActionListener buscar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(txtFolio.getText().equals("")){
				JOptionPane.showMessageDialog(null, "Ingrese el No. de Folio","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
				return;
			}else{
				try {
					Obj_Aspectos_De_La_Etapa aspecto = new Obj_Aspectos_De_La_Etapa().buscar(Integer.parseInt(txtFolio.getText()));
					if(aspecto.getFolio() != 0){
						
						txtFolio.setText(aspecto.getFolio()+"");
						txtAreaAspectoEtapa.setText(aspecto.getAspecto()+"");
						txtAbreviatura.setText(aspecto.getAbreviatura()+"");
						btnNuevo.setEnabled(true);
						btnEditar.setEnabled(true);
						txtFolio.setEditable(false);
						txtFolio.requestFocus();
						txtAreaAspectoEtapa.setEditable(false);
						txtAbreviatura.setEditable(false);
					} else{
						JOptionPane.showMessageDialog(null, "El Registro no existe","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
						return;
					}
				} catch (NumberFormatException e1) {
					e1.printStackTrace();
				} 			
			}
		}
	};
	
	ActionListener nuevo = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			 busqueda_proximo_folio();
			
			if(foliosiguiente != 0){
				btnDeshacer.doClick();
				btnGuardar.setEnabled(true);
				panelEnabledTrue();
				txtFolio.setText( busqueda_proximo_folio()+"");
				txtFolio.setEditable(false);
				txtAreaAspectoEtapa.requestFocus();
				btnEditar.setEnabled(false);
				cmb_status.setSelectedIndex(0);
				cmb_status.setEnabled(true);
			}else{
				btnDeshacer.doClick();
				btnGuardar.setEnabled(true);
				panelEnabledTrue();
				txtFolio.setText(1+"");
				txtFolio.setEditable(false);
				txtAreaAspectoEtapa.requestFocus();
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
						JOptionPane.showMessageDialog(null, "Los Siguientes Campos Son Requeridos:\n "+validaCampos(), "Aviso" ,JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
						return;
					} else{
					Obj_Aspectos_De_La_Etapa aspectos = new Obj_Aspectos_De_La_Etapa().buscar(Integer.parseInt(txtFolio.getText()));
					if(aspectos.getFolio() == Integer.parseInt(txtFolio.getText())){
						if(JOptionPane.showConfirmDialog(null, "El registro ya existe, �desea cambiarlo?") == 0){
							if(validaCampos()!="") {
								JOptionPane.showMessageDialog(null, "Los Siguientes Campos Son Requeridos:\n"+validaCampos(),  "Aviso" ,JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
								return;
							}else{
								aspectos.setAspecto(txtAreaAspectoEtapa.getText().toString().toUpperCase().trim());
								aspectos.setAbreviatura(txtAbreviatura.getText().toString().toUpperCase().trim());
									switch(cmb_status.getSelectedIndex()){
															case 0: aspectos.setStatus(1); break;
															case 1: aspectos.setStatus(0); break;	}
									
														if(aspectos.actualizar(Integer.parseInt(txtFolio.getText()))){
															init_tabla();
									JOptionPane.showMessageDialog(null,"El Registr� Se Actualiz� Correctamente","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
									btnDeshacer.doClick();
									txtFolio.setEditable(true);
									txtFolio.requestFocus();
									return;
								}else{
									JOptionPane.showMessageDialog(null, "El Registro No Se Actualiz�", "Avise Al Administrador Del Sistema !!!",JOptionPane.ERROR_MESSAGE, new ImageIcon("Imagen/usuario-icono-eliminar5252-64.png"));
									return;
								}
							}
						}else{
							return;
						}
					}else{
						
						aspectos.setFolio(Integer.parseInt(txtFolio.getText().trim()));
						aspectos.setAspecto(txtAreaAspectoEtapa.getText().toUpperCase().trim());
						aspectos.setAbreviatura(txtAbreviatura.getText().toUpperCase().trim());
							switch(cmb_status.getSelectedIndex()){
							case 0: aspectos.setStatus(1); break;
							case 1: aspectos.setStatus(0); break;	}
							
								if(aspectos.guardar()){
									init_tabla();
									JOptionPane.showMessageDialog(null,"El Registr� Se Guard� Correctamente","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
									btnDeshacer.doClick();
									txtFolio.setEditable(true);
									txtFolio.requestFocus();
									return;
									
								}else{
									JOptionPane.showMessageDialog(null, "El Registro No Se Guard�", "Avise Al Administrador Del Sistema !!!",JOptionPane.ERROR_MESSAGE, new ImageIcon("Imagen/usuario-icono-eliminar5252-64.png"));
									return;
								}
							}
					}
				} catch (NumberFormatException e1) {
					e1.printStackTrace();
				} 				
					
		}
	};
	
	public int  busqueda_proximo_folio() {
		Connexion con = new Connexion();
		String query = "select max(folio_aspecto)+1 as 'Maximo' from tb_aspectos_de_la_etapa ";
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
			JOptionPane.showMessageDialog(null, "Error en Cat_Aspectos_De_La_Etapa  en la funcion busqueda_proximo_folio()"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			return foliosiguiente ;
		}
		finally{
			 if (stmt != null) { try {
				stmt.close();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Error en Cat_Aspectos_De_La_Etapa  en la funcion busqueda_proximo_folio()"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			} }
		}
		return foliosiguiente;
			}
	
	private void agregar(final JTable tbl) {
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
	        	if(e.getClickCount()==1){
	        		int fila = tabla.getSelectedRow();
	        		Object id = tabla.getValueAt(fila,0).toString().substring(0,tabla.getValueAt(fila,0).toString().length());
	        
						txtFolio.setText(id+"");
						txtAreaAspectoEtapa.setText(tabla.getValueAt(fila,1).toString().substring(0,tabla.getValueAt(fila,1).toString().length()));
						txtAbreviatura.setText(tabla.getValueAt(fila,2).toString().substring(0,tabla.getValueAt(fila,2).toString().length()));
						btnEditar.setEnabled(true);
						cmb_status.setSelectedItem(tabla.getValueAt(fila,3).toString().substring(0,tabla.getValueAt(fila,3).toString().length()));
						cmb_status.setEnabled(false);
						btnEditar.setEnabled(true);
						txtAreaAspectoEtapa.setEditable(false);
						txtAbreviatura.setEditable(false);
						txtAreaAspectoEtapa.requestFocus();
						txtFolio.setEditable(false); 
	        	}
	        }
        });
    }
	
	public void panelEnabledTrue(){	
		txtFolio.setEditable(true);
		txtAreaAspectoEtapa.setEditable(true);
		txtAbreviatura.setEditable(true);
	}
	
	private String validaCampos(){
		String error="";
		if(txtAreaAspectoEtapa.getText().equals("")) 		error+= "Aspecto\n";
				
		return error;
	}
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Aspectos_De_La_Etapa().setVisible(true);
		}catch(Exception e){	}
	}
	
}
