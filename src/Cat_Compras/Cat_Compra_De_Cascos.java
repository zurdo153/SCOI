package Cat_Compras;

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
public class Cat_Compra_De_Cascos extends JFrame{
	int foliosiguiente=0;
	String Activo ="";
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JTextField txtFolio = new Componentes().text(new JCTextField(), "Folio", 9, "Int");
	JTextField txtBeneficiario= new Componentes().text(new JCTextField(), "Captura El Nombre Del Beneficiario",100,"String");
	
	JLabel JLBactivo= new JLabel();
	
	
	JButton btnDeshacer = new JButton("Deshacer",new ImageIcon("imagen/deshacer16.png"));
	JButton btnGuardar = new JButton("Guardar",new ImageIcon("imagen/Guardar.png"));
	JButton btnNuevo = new JButton("Nuevo",new ImageIcon("imagen/Nuevo.png"));
	
	 public static DefaultTableModel modelo = new DefaultTableModel(null,new String[]{"Folio Prod","Descripcion", "Costo","Cantidad"}){
	            @SuppressWarnings("rawtypes")
	            Class[] types = new Class[]{
	                       java.lang.Object.class,
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
	                            case 3  : return true; 
	                    }
	                     return false;
	             }
	    };
		JTable tabla = new JTable(modelo);
		JScrollPane scrollAsignado = new JScrollPane(tabla);
		@SuppressWarnings("rawtypes")
		private TableRowSorter trsfiltro;
		
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Cat_Compra_De_Cascos(){
			this.setSize(530,250);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/tamano-del-guion-menos-icono-8053-64.png"));
			panel.setBorder(BorderFactory.createTitledBorder("Alimenta La Cantidad De Cascos y Teclea el Nombre Del Beneficiario"));
			this.setTitle("Compras De Cascos");
			trsfiltro = new TableRowSorter(modelo); 
			tabla.setRowSorter(trsfiltro);
			
			int x = 10, y=30, width=100,height=20 ;
			
			panel.add(new JLabel("Folio:")).setBounds   (x      ,y   ,width  ,height);
			panel.add(txtFolio).setBounds               (x+=60  ,y   ,width  ,height);
			panel.add(btnNuevo).setBounds               (x+=115 ,y   ,width  ,height);
			panel.add(btnDeshacer).setBounds            (x+=115 ,y   ,width  ,height);
			panel.add(btnGuardar).setBounds             (x+=115 ,y   ,width  ,height);
			x=10;
			panel.add(new JLabel("Beneficiario:")).setBounds(x     ,y+=25,width  ,height);
			panel.add(txtBeneficiario).setBounds            (x+60  ,y    ,width*4+42,height);
			panel.add(getPanelTabla()).setBounds        (x     ,y+=25,503    ,130);
			
			txtBeneficiario.setEditable(false);
			txtFolio.setEditable(false);
			
			btnGuardar.addActionListener(guardar);
			btnDeshacer.addActionListener(deshacer);
			btnNuevo.addActionListener(nuevo);
			cont.add(panel);

			
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
			                  
			                  
	}
	private JScrollPane getPanelTabla()	{	
			tabla.getTableHeader().setReorderingAllowed(false) ;
			tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			
		    this.tabla.getColumnModel().getColumn(0).setMinWidth(90);
		    this.tabla.getColumnModel().getColumn(0).setMaxWidth(90);
		    this.tabla.getColumnModel().getColumn(1).setMinWidth(240);
		    this.tabla.getColumnModel().getColumn(1).setMaxWidth(500);
		    this.tabla.getColumnModel().getColumn(2).setMinWidth(85);
		    this.tabla.getColumnModel().getColumn(2).setMaxWidth(80);
		    this.tabla.getColumnModel().getColumn(3).setMinWidth(86);
		    this.tabla.getColumnModel().getColumn(3).setMaxWidth(80);
		    
		    tabla.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 	
		    tabla.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
		    tabla.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
		    tabla.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
			
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
			rs = s.executeQuery("select folio_producto, descripcion, costo,'' as cantidad  from tb_productos where status='V' and folio_uso=1");
			while (rs.next())
			{  String [] fila = new String[4];
			   fila[0] = rs.getString(1).trim();
			   fila[1] = rs.getString(2).trim();
			   fila[2] = rs.getString(3).trim(); 
			   fila[3] = rs.getString(4).trim(); 
			   modelo.addRow(fila); 
			}	
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en Cat_Compras_De_Cascos en la funcion refrestabla  SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	ActionListener deshacer = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			txtFolio.setText("");
			btnNuevo.setEnabled(true);
			txtBeneficiario.setText("");
			txtBeneficiario.setEditable(false);
			txtFolio.requestFocus();
		}
	};
	
	ActionListener nuevo = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
				btnDeshacer.doClick();
				txtFolio.setText( busqueda_proximo_folio()+"");
				btnGuardar.setEnabled(true);
				txtBeneficiario.setEditable(true);
				txtBeneficiario.requestFocus();
		}
	};
	
	public int  busqueda_proximo_folio() {
		Connexion con = new Connexion();
		String query = "select folio+1 from tb_folios where status=1 and folio_transaccion=18";
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
							concepto.setConcepto(txtBeneficiario.getText().toUpperCase().trim());
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
						concepto.setConcepto(txtBeneficiario.getText().toUpperCase().trim());
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
	
	private String validaCampos(){
		String error="";
		if(txtBeneficiario.getText().equals("")) 		error+= "-Nombre Del Concepto\n";
		return error;
	}
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Compra_De_Cascos().setVisible(true);
		}catch(Exception e){	}
	}
	
}
