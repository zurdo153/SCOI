package Cat_Lista_de_Raya;

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

import Conexiones_SQL.Connexion;
import Obj_Lista_de_Raya.Obj_Bono_Puntualidad_Y_Asistencia;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_Filtro_Dinamico_Plus;
import Obj_Principal.Obj_tabla;

@SuppressWarnings("serial")
public class Cat_Bono_Puntualidad_Y_Asistencia extends JFrame{
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
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
    	this.tabla.getColumnModel().getColumn(1).setMinWidth(100);
    	this.tabla.getColumnModel().getColumn(2).setMinWidth(80);
    	this.tabla.getColumnModel().getColumn(2).setMinWidth(80);
    	
		String comando="select folio,bono,abreviatura,case when status=1 then 'VIGENTE' else 'CANCELADO' end as estatus from [tb_bono_puntualidad_y_asistencia] order by bono ";
		String basedatos="26",pintar="si";
		new Obj_tabla().Obj_Refrescar(tabla,modelo, columnas, comando, basedatos,pintar,checkbox);
    }
	
	
 public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"Folio", "Bono", "Abreviatura","Estatus"}){
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
	
	
	JTextField txtFolio = new Componentes().text(new JCTextField(), "Folio", 9, "Int");
	JTextField txtBono = new Componentes().text(new JCTextField(), "Cantidad del Bono", 8, "Double");
	JTextField txtAbreviatura = new Componentes().text(new JCTextField(), "Abreviatura", 5, "String");
	
	String status[] = {"VIGENTE","CANCELADO"};
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmb_status = new JComboBox(status);
	
	JCButton btnBuscar = new JCButton("Buscar","buscar.png","Azul");
	JCButton btnSalir = new JCButton("Salir","salir16.png","Azul");
	JCButton btnDeshacer = new JCButton("Deshacer","deshacer16.png","Azul"); 
	JCButton btnGuardar = new JCButton("Guardar","Guardar.png","Azul"); 
	JCButton btnEditar = new JCButton("Editar","editara.png","Azul"); 
	JCButton btnNuevo = new JCButton("Nuevo","Nuevo.png","Azul"); 
	
	public Cat_Bono_Puntualidad_Y_Asistencia(){
		this.setSize(760,220);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		cont.setBackground(new java.awt.Color(255, 255, 255));
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Dollar.png"));
		panel.setBorder(BorderFactory.createTitledBorder("Bono Puntualidad y Asistencia"));
		this.setTitle("Modificaciones, Altas y Consultas de Bonos De Puntualidad y Asistencia");
		
		int x = 10, y=15,width=150,height=20,sep=80;
		panel.add(new JLabel("Folio:")).setBounds      (x      ,y     ,width,height);
		panel.add(txtFolio).setBounds                  (x+sep  ,y     ,width,height);
		panel.add(btnBuscar).setBounds                 (x+sep*3,y     ,125  ,height);
		panel.add(txtFiltro).setBounds                 (x+380  ,y     ,350,height);;
		
		panel.add(new JLabel("Bono:")).setBounds       (x      ,y+=30 ,width,height);	
		panel.add(txtBono).setBounds                   (x+sep  ,y     ,width,height);	
		panel.add(btnEditar).setBounds                 (x+sep*3,y     ,125  ,height);
		
		panel.add(new JLabel("Abreviatura:")).setBounds(x      ,y+=30 ,width,height);
		panel.add(txtAbreviatura).setBounds            (x+sep  ,y     ,width,height);	
		panel.add(btnNuevo).setBounds                  (x+sep*3,y     ,125  ,height);
		
		panel.add(new JLabel("Estatus:")).setBounds    (x      ,y+=30 ,width,height);
		panel.add(cmb_status).setBounds                  (x+sep  ,y     ,width,height);	
		panel.add(btnGuardar).setBounds                (x+sep*3,y     ,125  ,height);

		panel.add(btnDeshacer).setBounds               (x      ,y+=50 ,125,height);
		panel.add(btnSalir).setBounds                  (x+sep*3,y     ,125,height);
		
		panel.add(scroll_tabla).setBounds              (x+380,35     ,350,140);
		
		init_tabla();
		cont.add(panel);
		agregar(tabla);

		txtFolio.addKeyListener(buscar_action);
		btnGuardar.addActionListener(guardar);
		btnSalir.addActionListener(cerrar);
		btnBuscar.addActionListener(buscar);
		btnDeshacer.addActionListener(deshacer);
		btnNuevo.addActionListener(nuevo);
		btnEditar.addActionListener(editar);
		txtFiltro.addKeyListener(op_filtro);
		txtBono.addKeyListener(enterpasaraabreviatura);
		txtAbreviatura.addKeyListener(enterpasarastatus);
		cmb_status.addKeyListener(enterpasaconcepto);
		
		cmb_status.setEnabled(false);
		txtBono.setEditable(false);
		txtAbreviatura.setEditable(false);
		btnEditar.setEnabled(false);
		
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
	
	private void agregar(final JTable tbl) {
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
	        	if(e.getClickCount()==1){
	        		int fila = tabla.getSelectedRow();
	        		int id = Integer.parseInt(modelo.getValueAt(fila,0)+"");
	        
	        		Obj_Bono_Puntualidad_Y_Asistencia bono = new Obj_Bono_Puntualidad_Y_Asistencia().buscar(id);
						txtFolio.setText(id+"");
						txtBono.setText(bono.getBono()+"");
						txtAbreviatura.setText(bono.getAbreviatura()+"");
						if(bono.getStatus() == true){cmb_status.setSelectedItem("VIGENTE");}
						else{cmb_status.setSelectedItem("CANCELADO");}
						btnEditar.setEnabled(true);
	        	}
	        }
        });
    }
	
	
	ActionListener guardar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(txtFolio.getText().equals("")){
				JOptionPane.showMessageDialog(null, "El Folio Es Requerido \n","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
			}else{			
				try {
					Obj_Bono_Puntualidad_Y_Asistencia bono = new Obj_Bono_Puntualidad_Y_Asistencia().buscar(Integer.parseInt(txtFolio.getText()));
					
					
					if(bono.getFolio() == Integer.parseInt(txtFolio.getText())){
						if(JOptionPane.showConfirmDialog(null, "El registro ya existe, ¿desea cambiarlo?") == 0){
							if(validaCampos()!="") {
								JOptionPane.showMessageDialog(null, "Los Siguientes Campos Son Requeridos:\n"+validaCampos(),  "Aviso" ,JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
								return;
							}else{
								bono.setBono(Float.parseFloat(txtBono.getText()));
								bono.setAbreviatura(txtAbreviatura.getText());
								bono.setStatus(cmb_status.getSelectedItem().toString().equals("VIGENTE")?true:false);
								
								if(bono.actualizar(Integer.parseInt(txtFolio.getText()))){
									init_tabla();
									panelLimpiar();
									panelEnabledFalse();
									txtFolio.setEditable(true);
									txtFolio.requestFocus();
									btnEditar.setEnabled(false);
									JOptionPane.showMessageDialog(null,"El Registró Se Actualizó Correctamente","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
									return;
								}else{
									JOptionPane.showMessageDialog(null, "El Registro No Se Actualizó", "Avise Al Administrador Del Sistema !!!",JOptionPane.ERROR_MESSAGE, new ImageIcon("Imagen/usuario-icono-eliminar5252-64.png"));
									return;
								}	
							}
						}else{
							return;
						}
					}else{
						if(validaCampos()!="") {
							JOptionPane.showMessageDialog(null, "El Registro No Se Actualizó", "Avise Al Administrador Del Sistema !!!",JOptionPane.ERROR_MESSAGE, new ImageIcon("Imagen/usuario-icono-eliminar5252-64.png"));
							return;
						}else{
							bono.setBono(Float.parseFloat(txtBono.getText()));
							bono.setAbreviatura(txtAbreviatura.getText());
							bono.setStatus(cmb_status.getSelectedItem().toString().equals("VIGENTE")?true:false);
							if(bono.guardar()){
								panelLimpiar();
								panelEnabledFalse();
								txtFolio.setEditable(true);
								txtFolio.requestFocus();
								btnEditar.setEnabled(false);
								init_tabla();
								JOptionPane.showMessageDialog(null,"El Registró Se Guardó Correctamente","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
								return;
							}else{
								JOptionPane.showMessageDialog(null, "El Registro No Se Guardo", "Avise Al Administrador Del Sistema !!!",JOptionPane.ERROR_MESSAGE, new ImageIcon("Imagen/usuario-icono-eliminar5252-64.png"));
								return;
							}
						}
					}
				} catch (NumberFormatException e1) {
					e1.printStackTrace();
				} 				
			}			
		}
	};
	public void panelEnabledFalse(){	
		txtFolio.setEditable(false);
		txtBono.setEditable(false);
		txtAbreviatura.setEditable(false);
		cmb_status.setEnabled(false);
	}		
	
	public void panelEnabledTrue(){	
		txtFolio.setEditable(true);
		txtBono.setEditable(true);
		txtAbreviatura.setEditable(true);
		cmb_status.setEnabled(true);	
	}
	
	public void panelLimpiar(){	
		txtFolio.setText("");
		txtBono.setText("");
		txtAbreviatura.setText("");
		cmb_status.setSelectedIndex(0);
	}
	
	KeyListener enterpasaraabreviatura = new KeyListener() {
		public void keyTyped(KeyEvent e){}
		public void keyReleased(KeyEvent e) {}
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				txtAbreviatura.requestFocus();
			}
		}
	};
	
	
	
	KeyListener enterpasarastatus = new KeyListener() {
		public void keyTyped(KeyEvent e){}
		public void keyReleased(KeyEvent e) {}
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				cmb_status.requestFocus();
			}
		}
	};
	
	KeyListener enterpasaconcepto = new KeyListener() {
		public void keyTyped(KeyEvent e){}
		public void keyReleased(KeyEvent e) {}
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				txtBono.requestFocus();
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
	
	KeyListener op_filtro = new KeyListener(){
		public void keyReleased(KeyEvent arg0) {
			int[] columnas ={0,1,2};
			new Obj_Filtro_Dinamico_Plus(tabla, txtFiltro.getText().toUpperCase(), columnas);
		}
		public void keyTyped(KeyEvent arg0)   {}
		public void keyPressed(KeyEvent arg0) {}		
	};
	
	ActionListener buscar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(txtFolio.getText().equals("")){
				JOptionPane.showMessageDialog(null, "Ingrese El Folio A Buscar","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
				txtFolio.requestFocus();
				return;
			}else{
				try {
					Obj_Bono_Puntualidad_Y_Asistencia re = new Obj_Bono_Puntualidad_Y_Asistencia().buscar(Integer.parseInt(txtFolio.getText()));
					
					if(re.getFolio() != 0){
						
						txtFolio.setText(re.getFolio()+"");
						txtBono.setText(re.getBono()+"");
						txtAbreviatura.setText(re.getAbreviatura()+"");
						if(re.getStatus() == true){cmb_status.setSelectedItem("VIGENTE");}
						else{cmb_status.setSelectedItem("CANCELADO");}
						
						panelEnabledFalse();
						btnNuevo.setEnabled(true);
						btnEditar.setEnabled(true);
						txtFolio.setEditable(true);
						txtFolio.requestFocus();
						
					} else{
						JOptionPane.showMessageDialog(null, "El Registro no existe","Error",JOptionPane.WARNING_MESSAGE);
						return;
					}
				
				} catch (NumberFormatException e1) {
					e1.printStackTrace();
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
		if(txtBono.getText().equals("")) 			error+= "Bono\n";
		if(txtAbreviatura.getText().equals(""))		error+= "Abreviatura\n";
				
		return error;
	}
	
	ActionListener nuevo = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			busqueda_proximo_folio();
			
			if(foliosiguiente != 0){
				panelLimpiar();
				panelEnabledTrue();
				txtFolio.setText(foliosiguiente+"");
				txtFolio.setEditable(false);
				txtBono.requestFocus();
			}else{
				panelLimpiar();
				panelEnabledTrue();
				txtFolio.setText(1+"");
				txtFolio.setEditable(false);
				txtBono.requestFocus();
			}
			
		}
	};
	
	int foliosiguiente=0;
	public int  busqueda_proximo_folio() {
		Connexion con = new Connexion();
		String query = "select isnull(max(folio)+1,0) as 'Maximo' from tb_bono_puntualidad_y_asistencia ";
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
			JOptionPane.showMessageDialog(null, "Error en la funcion busqueda_proximo_folio()"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			return foliosiguiente ;
		}
		finally{
			 if (stmt != null) { try {
				stmt.close();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Error en la funcion busqueda_proximo_folio()"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			} }
		}
		return foliosiguiente;
			}
	
	
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
			if(txtFolio.getText().equals("")){
				JOptionPane.showMessageDialog(null, "No hay registro que Editar","Error",JOptionPane.WARNING_MESSAGE);
				return;
			}else{
				panelEnabledTrue();
				txtFolio.setEditable(false);
				btnEditar.setEnabled(false);
				btnNuevo.setEnabled(true);
			}
			
		}		
	};
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Bono_Puntualidad_Y_Asistencia().setVisible(true);
		}catch(Exception e){	}
	}
}
