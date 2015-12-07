package Cat_Compras;

import java.awt.Component;
import java.awt.Container;
import java.awt.Event;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

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
import Conexiones_SQL.Generacion_Reportes;
import Obj_Compras.Obj_Compra_De_Cascos;
import Obj_Principal.Componentes;
import Obj_Principal.JCTextField;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Compra_De_Cascos extends JFrame{
	int foliosiguiente=0;
	String Activo ="";
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JTextField txtFolio = new Componentes().text(new JCTextField(), "Folio", 9, "Int");
	JTextField txtBeneficiario= new Componentes().text(new JCTextField(), "Captura El Nombre Del Beneficiario",100,"String");
	JTextField txtTotalAPagar= new Componentes().text(new JCTextField(), "Total A Pagar",100,"String");
	
	JLabel JLBactivo= new JLabel();
	
	JButton btnDeshacer = new JButton("Deshacer",new ImageIcon("imagen/deshacer16.png"));
	JButton btnGuardar = new JButton("Guardar",new ImageIcon("imagen/Guardar.png"));
	JButton btnNuevo = new JButton("Nuevo",new ImageIcon("imagen/Nuevo.png"));
	JButton btnReporte = new JButton("Reporte",new ImageIcon("imagen/Print.png"));
	
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
		int fila=0,columna=3;
		
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
			
			int x = 10, y=35, width=100,height=20 ;
			panel.add(btnReporte).setBounds             (415  ,10    ,width  ,height);
			panel.add(new JLabel("Folio:")).setBounds   (x      ,y   ,width  ,height);
			panel.add(txtFolio).setBounds               (x+=60  ,y   ,width  ,height);
			panel.add(btnNuevo).setBounds               (x+=115 ,y   ,width  ,height);
			panel.add(btnDeshacer).setBounds            (x+=115 ,y   ,width  ,height);
			panel.add(btnGuardar).setBounds             (x+=115 ,y   ,width  ,height);
			x=10;
			panel.add(new JLabel("Beneficiario:")).setBounds(x     ,y+=25,width  ,height);
			panel.add(txtBeneficiario).setBounds            (x+60  ,y    ,width*3+42,height);
			panel.add(txtTotalAPagar).setBounds             (x+415  ,y    ,width-12,height);
			panel.add(getPanelTabla()).setBounds        (x     ,y+=25,503    ,130);
			
			txtBeneficiario.setEditable(false);
			txtFolio.setEditable(false);
			txtTotalAPagar.setEditable(false);
			btnGuardar.setEnabled(false);
			
			btnGuardar.addActionListener(guardar);
			btnDeshacer.addActionListener(deshacer);
			btnNuevo.addActionListener(nuevo);
			btnReporte.addActionListener(opReporte);
			cont.add(panel);
			tabla.addKeyListener(op_key);
			txtFolio.setText( busqueda_proximo_folio()+"");
			
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
			                  
		  ///DEL JTEXTFIELD A LA TABLA
			          	    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
			          	       KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "BUSCA");
			     	        getRootPane().getActionMap().put("BUSCA", new AbstractAction(){
			        	        @Override
			        	        public void actionPerformed(ActionEvent e)
			        	        {
			        	                   fila=0;
			        	                   columna=3;			        	        
			        	    				tabla.getSelectionModel().setSelectionInterval(fila, fila);
			        	    				tabla.editCellAt(fila, columna);
			        	    				Component aComp=tabla.getEditorComponent();
			        	    				aComp.requestFocus();
			        	    		}
			        	    });
	}
	
  KeyListener op_key = new KeyListener() {
		public void keyTyped(KeyEvent e) {}
		public void keyReleased(KeyEvent e) {
			ValidaValor();	
		}
		public void keyPressed(KeyEvent e) {}
	};
	
  public boolean ValidaValor(){
			boolean valor=false;
						if(isNumeric(modelo.getValueAt(fila,3).toString().trim())){
									RecorridoFoco();
									valor = true;
							}else{
									tabla.getSelectionModel().setSelectionInterval(fila, fila);
									JOptionPane.showMessageDialog(null, "La Fila  [ "+(fila+1)+" ] En La Columna Cantidad Solo Acepta Numeros Enteros","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
									modelo.setValueAt(0, fila, columna);
									tabla.editCellAt(fila, columna);
									Component aComp=tabla.getEditorComponent();
									aComp.requestFocus();
							}
			return valor;
		}

   private boolean isNumeric(String cadena){
			try {
				if(cadena.equals("")){
		    		return true;
				}else{
					Integer.parseInt(cadena);
		    		return true;
				}
			} catch (NumberFormatException nfe){
				return false;
			}
		}
	
	@SuppressWarnings("deprecation")
	public void RecorridoFoco(){
		int cantidadDeFilas = tabla.getRowCount();
		String sacarFocoDeTabla = "no";
		if(fila == cantidadDeFilas-1){
				if(columna==3){
					double total=0;
					 for(int i =0; i<tabla.getRowCount(); i++){
						total=total+(Float.valueOf(tabla.getValueAt(i, 2).toString().trim())*( (tabla.getValueAt(i, 3).toString().trim().equals(""))?0:(Float.valueOf(tabla.getValueAt(i, 3).toString().trim()))));
					 }
					txtTotalAPagar.setText(total+"");
					sacarFocoDeTabla="si";
						}
		}else{
			sacarFocoDeTabla = "no";
			double total=0;
			for(int i =0; i<tabla.getRowCount(); i++){
				total=total+(Float.valueOf(tabla.getValueAt(i, 2).toString().trim())*( (tabla.getValueAt(i, 3).toString().trim().equals(""))?0:(Float.valueOf(tabla.getValueAt(i, 3).toString().trim()))));
			}
			txtTotalAPagar.setText(total+"");
			fila=fila+1;
		}
		tabla.getSelectionModel().setSelectionInterval(fila, fila);
		tabla.editCellAt(fila, columna);
		Component aComp=tabla.getEditorComponent();
		aComp.requestFocus();
		if(sacarFocoDeTabla.equals("si")){
			tabla.lostFocus(null, null);
			txtBeneficiario.requestFocus();
		}
	};

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
			rs = s.executeQuery("select folio_producto, descripcion, costo,'' as cantidad  from tb_productos where status='V' and folio_uso=2 ORDER BY costo");
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
			txtFolio.setText( busqueda_proximo_folio()+"");
			btnNuevo.setEnabled(true);
			txtBeneficiario.setText("");
			txtBeneficiario.setEditable(false);
			btnGuardar.setEnabled(false);
			txtTotalAPagar.setText("");
			txtFolio.requestFocus();
			refrestabla();
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
	
	ActionListener opReporte = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			int folio=Integer.valueOf(txtFolio.getText().toString())-1;
			String basedatos="2.26";
			String vista_previa_reporte="no";
			int vista_previa_de_ventana=0;
			String reporte = "Obj_Reporte_De_Compra_De_Cascos.jrxml";
			String comando = "exec sp_Reporte_De_Compra_De_Casco "+folio;
	   	    new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String[][] tabla_guardar(){
		Vector vector = new Vector();
		for(int i=0; i<tabla.getRowCount(); i++){
			 if(Integer.valueOf(tabla.getValueAt(i,3).toString().trim().equals("")?"0":tabla.getValueAt(i,3).toString().trim())>0){
				  vector.add(modelo.getValueAt(i,0).toString().trim());
				  vector.add(modelo.getValueAt(i,2).toString().trim());
				  vector.add(modelo.getValueAt(i,3).toString().trim());
		     }
		}
		String[][] matriz = new String[vector.size()/3][3];
		int j =0,i=0;
		while(i<vector.size()){
			matriz[j][0] = vector.get(i).toString();
			i++;
			matriz[j][1] = vector.get(i).toString();
			i++;
			matriz[j][2] = vector.get(i).toString();
			i++;
			j++;
		}
		return matriz;
	}
	
	ActionListener guardar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
				try {
					if(tabla.isEditing()){
						tabla.getCellEditor().stopCellEditing();
					}
					
					double total=0;
					for(int i =0; i<tabla.getRowCount(); i++){
						total=total+(Float.valueOf(tabla.getValueAt(i, 2).toString().trim())*( (tabla.getValueAt(i, 3).toString().trim().equals(""))?0:(Float.valueOf(tabla.getValueAt(i, 3).toString().trim()))));
					}
					txtTotalAPagar.setText(total+"");
					
					if(validaCampos()!="") {
						JOptionPane.showMessageDialog(null, "Los Siguientes Datos Son Requeridos:\n "+validaCampos(), "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
						return;
					} else{
						 Obj_Compra_De_Cascos compracascos = new Obj_Compra_De_Cascos();
						 
						 compracascos.setFolio_compra(busca_y_actualiza_proximo_folio());
						 compracascos.setBeneficiario(txtBeneficiario.getText().toUpperCase().trim());
						 compracascos.setTotal(Double.valueOf(txtTotalAPagar.getText().toUpperCase().trim()));
					   if(compracascos.guardar(tabla_guardar())){
						           refrestabla();
									JOptionPane.showMessageDialog(null,"El Registró Se Guardó  Correctamente","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//aplicara-el-dialogo-icono-6256-32.png"));
									btnDeshacer.doClick();
									btnReporte.doClick();
									return;
								}else{
									JOptionPane.showMessageDialog(null, "El Registro No Se Guardó", "Error !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-icono-eliminar5252-64.png"));
									return;
								}
					}
				} catch (NumberFormatException e1) {
					e1.printStackTrace();
				} 				
		}
	};
	
	public int  busca_y_actualiza_proximo_folio() {
		Connexion con = new Connexion();
		String query = "declare @sqlqry nvarchar(max),@folio varchar(7)                        "
	 		     + "   set nocount on"
	 		     + "   set @folio=(select folio+1 from tb_folios Where folio_transaccion='18' and status=1)"
	 		     + "  set @sqlqry='update tb_folios set folio='+@folio+' Where folio_transaccion=''18'' and status=1'"
	 		     + "  exec sp_executesql @sqlqry"
	 		     + "  set nocount off"
	 		     + " set @sqlqry='select '+@folio"
	 		     + " exec sp_executesql @sqlqry";
		
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
			JOptionPane.showMessageDialog(null, "Error en la funcion busca_y_actualiza_proximo_folio()"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			return foliosiguiente ;
		}
		finally{
			 if (stmt != null) { try {
				stmt.close();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Error en la funcion busca_y_actualiza_proximo_folio()"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
				e.printStackTrace();
			} }
		}
		return foliosiguiente;
			}
	
	private String validaCampos(){
		String error="";
		if(txtBeneficiario.getText().equals("")) 		error+= "-Nombre Del Beneficiario\n";
		if(txtTotalAPagar.getText().equals("0.0")) 		error+= "-Cantidad A Comprar\n";
		return error;
	}
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Compra_De_Cascos().setVisible(true);
		}catch(Exception e){	}
	}
	
}
