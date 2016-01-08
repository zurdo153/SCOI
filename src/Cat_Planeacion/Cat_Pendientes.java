package Cat_Planeacion;

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
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Conexiones_SQL.ActualizarSQL;
import Conexiones_SQL.Connexion;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Planeacion.Obj_Pendientes;
import Obj_Principal.Componentes;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_Filtro_Dinamico_Plus;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Pendientes extends JFrame{
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	DefaultTableModel model = new DefaultTableModel(null, new String[]{"Folio", "Nombre Completo","Establecimiento","Departamento","Puesto", " *"}
			){
	     @SuppressWarnings("rawtypes")
		Class[] types = new Class[]{
	    	java.lang.Integer.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.Boolean.class	    	
         };
	     @SuppressWarnings({ "rawtypes", "unchecked" })
		public Class getColumnClass(int columnIndex) {
             return types[columnIndex];
         }
         public boolean isCellEditable(int fila, int columna){
        	 switch(columna){
        	 	case 0 : return false; 
        	 	case 1 : return false; 
        	 	case 2 : return false; 
        	 	case 3 : return false; 
        	 	case 4 : return false; 
        	 	case 5 : return true; 
        	 } 				
 			return false;
 		}
		
	};
	
	JTable tabla = new JTable(model);
    JScrollPane scroll = new JScrollPane(tabla);
	
	DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"Folio", "Pendiente","Fecha","Colaboradores"}
			){
	     @SuppressWarnings("rawtypes")
		Class[] types = new Class[]{
	    	java.lang.Integer.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
         };
	     @SuppressWarnings({ "rawtypes", "unchecked" })
		public Class getColumnClass(int columnIndex) {
             return types[columnIndex];
         }
         public boolean isCellEditable(int fila, int columna){
        	 switch(columna){
        	 	case 0 : return false; 
        	 	case 1 : return false; 
        	 	case 2 : return false; 
        	 	case 3 : return false; 
        	 } 				
 			return false;
 		}
	};
	
	JTable tablaPendientes = new JTable(modelo);
    JScrollPane scroll2 = new JScrollPane(tablaPendientes);
	@SuppressWarnings("rawtypes")
	private TableRowSorter trsfiltroP;
	
	JTextArea txa_Pendiente = new Componentes().textArea(new JTextArea(), "Detalle Del Pendiente", 300);
	JTextArea txa_Resultado_Seleccion = new Componentes().textArea(new JTextArea(), "Concepto", 300);
	JTextField txtFiltro_Pendientes= new Componentes().text(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla De los Pendientes<<<", 300, "String");
	JTextField txtFiltro= new Componentes().text(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla De Los Colaboradores Que Quieres Agregar<<<", 300, "String");
	
	JScrollPane Pendiente = new JScrollPane(txa_Pendiente);
	JScrollPane Resultado = new JScrollPane(txa_Resultado_Seleccion);
	
	JButton btnAgregar  = new JButton("Guardar",new ImageIcon("imagen/Guardar.png"));
	JButton btnDeshacer = new JButton("Deshacer",new ImageIcon("imagen/deshacer16.png"));
	JButton btnCancelar = new JButton("Eliminar Pendiente",new ImageIcon("imagen/eliminar-bala-icono-7773-32.png"));
	
	JSeparator separador = new JSeparator(SwingConstants.HORIZONTAL);
	
	Obj_Usuario usuario = new Obj_Usuario().LeerSession();
	Obj_Pendientes ObjPendientes= new Obj_Pendientes();
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Cat_Pendientes()	{
		this.setSize(1024,740);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/equipos-de-tarea-asignada-icono-7668-64.png"));
		this.setTitle("Pendientes");
	    this.panel.setBorder(BorderFactory.createTitledBorder("         PENDIENTES DEL COLABORADOR: "+usuario.getNombre_completo()));
		this.trsfiltroP = new TableRowSorter(modelo); 
		this.tablaPendientes.setRowSorter(trsfiltroP);  
		this.txa_Pendiente.setLineWrap(true); 
		this.txa_Pendiente.setWrapStyleWord(true);
		this.txa_Resultado_Seleccion.setEditable(false);
		this.txa_Resultado_Seleccion.setLineWrap(true); 
		this.txa_Resultado_Seleccion.setWrapStyleWord(true);

		separador.setBackground(Color.blue);
        
		int x=15,y=15,width=100,height=20;
		
		this.panel.add(txtFiltro_Pendientes).setBounds                                                  (x     ,y+5     ,width*8 ,height   );
		this.panel.add(btnCancelar).setBounds                                                           (x+830 ,y     ,width+60   ,height   );
		this.panel.add(scroll2).setBounds                                                               (x     ,y+=25 ,width*10-10,width*2  );
		panel.add(separador).setBounds                                                                  (0     ,y+=217,width*11   ,20       );
		
		this.panel.add(new JLabel("     Detalle Del Pendiente Nuevo:")).setBounds                       (x     ,y+=5  ,width*3    ,height   );
        this.panel.add(Pendiente).setBounds                                                             (x     ,y+=15 ,width*10-10,width-20 );
		this.panel.add(txtFiltro).setBounds                                                             (x     ,y+=90 ,width*10-10,height   );
		this.panel.add(scroll).setBounds                                                                (x     ,y+=20 ,width*10-10,width*2  );
		
		x=15;
		this.panel.add(new JLabel("     Colaboradores Seleccionados:")).setBounds                       (x     ,y=y+200,width*3    ,height  );
		this.panel.add(Resultado  ).setBounds                                                           (x     ,y+=15  ,width*10-10,width-30);
		this.panel.add(btnDeshacer).setBounds                                                           (x     ,y+=75  ,width      ,height  );
		this.panel.add(btnAgregar).setBounds                                                            (x+892 ,y      ,width      ,height  );
		
		this.cont.add(panel);
		this.init_tabla();
		this.init_tabla_pendientes();
		
//		comentario();
		tabla_seleccion_default_usuario();
		this.tabla.addMouseListener(opcomentario);
		
		this.tabla.addKeyListener(opseleccioncontecladocomentario);
		
		this.txtFiltro.addKeyListener(opFiltroFolio);
		
		this.txtFiltro_Pendientes.addKeyListener(opFiltroPendientes);
		
		this.btnAgregar.addActionListener(Agregar);
		this.btnDeshacer.addActionListener(deshacer);
		this.btnCancelar.addActionListener(opCacelarObjetivo);
		
		///guardar con control+A
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A,Event.CTRL_MASK),"guardar");
             getRootPane().getActionMap().put("guardar", new AbstractAction(){
                 public void actionPerformed(ActionEvent e)
                 {                 	    btnAgregar.doClick();           	    }
            });
             
 	     //deshacer con escape
 	                 getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
 	                   getRootPane().getActionMap().put("escape", new AbstractAction(){
 	                  public void actionPerformed(ActionEvent e)
 	                  {                btnDeshacer.doClick();           	    }
 	              });
 	                   
 	     this.addWindowListener(new WindowAdapter() {
  	                     public void windowOpened( WindowEvent e ){
  	                    	 txtFiltro_Pendientes.requestFocus();
  	                  }
  	             });
 	                  
	}
	
	ActionListener Agregar = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			if(tabla.isEditing()){
				tabla.getCellEditor().stopCellEditing();
			}
			if(txa_Pendiente.getText().equals("")){
				JOptionPane.showMessageDialog(null, "Necesita Escribir El Pendiente Antes De Guardar", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
				txa_Pendiente.requestFocus();
				return;
			}
			if(!txtFiltro.getText().equals("")){
				JOptionPane.showMessageDialog(null, "No se Puede Guardar En Medio De Una Busqueda, Por Que Podrian Haber Mas Colaboradores Seleccionados "
						                          + "\nA Continuación Se Borrará El Filtro Para Mostrarle Todos Los Colaboradores y Podrá Aplicar De Nuevo"
                                                  , "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
				txtFiltro.setText("");
				int[] columnas = {0,1,2,3,4};
				new Obj_Filtro_Dinamico_Plus(tabla,txtFiltro.getText(), columnas);
				comentario();
				return;
			}
			
			ObjPendientes.setPendiente(txa_Pendiente.getText().toString().toUpperCase().trim());
			ObjPendientes.setColaboradores(txa_Resultado_Seleccion.getText().toString().toUpperCase().trim());
			if(ObjPendientes.guardar()){
				btnDeshacer.doClick();
				
			}
		}
	};
	
	ActionListener opCacelarObjetivo = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {

			int filaSeleccionada = tablaPendientes.getSelectedRow();
			if(filaSeleccionada<0){
				JOptionPane.showMessageDialog(null, "Seleccione El Pendiente Que Desea Cancelar", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}else{
					if(new ActualizarSQL().Cancelar_pendiente_Del_Usuario(Integer.valueOf(tablaPendientes.getValueAt(filaSeleccionada, 0).toString().trim()))){
						
						JOptionPane.showMessageDialog(null, "El Pendiente Seleccionado Se Cancelo Correctamente", "Aviso", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/aplicara-el-dialogo-icono-6256-32.png"));
						refrestabla_pendientes();
						return;
					}
			}
		}
	};
	
	ActionListener deshacer = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			txa_Resultado_Seleccion.setText("");
			txa_Pendiente.setText("");
			txtFiltro.setText("");
			txtFiltro_Pendientes.setText("");
			refrestabla();
			refrestabla_pendientes();
			comentario();
			 txtFiltro_Pendientes.requestFocus();
		 }
		};
	
	MouseListener opcomentario = new MouseListener() {
		public void mouseReleased(MouseEvent arg0) {}
		public void mousePressed(MouseEvent arg0) {}
		public void mouseExited(MouseEvent arg0) {}
		public void mouseEntered(MouseEvent arg0) {}
		public void mouseClicked(MouseEvent arg0) {
//			llenar_arreglo();
			comentario();
		}
	};
	
	KeyListener opseleccioncontecladocomentario = new KeyListener(){
		public void keyReleased(KeyEvent arg0) {
			comentario();
		}
		public void keyTyped(KeyEvent arg0)   {}
		public void keyPressed(KeyEvent arg0) {}		
	};
	
	KeyListener opFiltroFolio = new KeyListener(){
		public void keyReleased(KeyEvent arg0) {
			int[] columnas = {0,1,2,3,4};
			new Obj_Filtro_Dinamico_Plus(tabla, txtFiltro.getText().toUpperCase(), columnas);
			comentario();
		}
		public void keyTyped(KeyEvent arg0)   {}
		public void keyPressed(KeyEvent arg0) {}		
	};
	
	KeyListener opFiltroPendientes = new KeyListener(){
		public void keyReleased(KeyEvent arg0) {
			int[] columnas = {0,1,2,3,};
			new Obj_Filtro_Dinamico_Plus(tablaPendientes, txtFiltro_Pendientes.getText().toUpperCase(), columnas);
		}
		public void keyTyped(KeyEvent arg0)   {}
		public void keyPressed(KeyEvent arg0) {}		
	};
	
	
	public void comentario(){
		txa_Resultado_Seleccion.setText("");
		String Comentario_colaboradores="";
		int testigo=0;
		 if(!txtFiltro.getText().toString().equals("")){
			 Comentario_colaboradores="Esta Filtrando La Tabla No Se Pueden Mostrar Los Empleado Seleccionados:";
			 testigo=1;
		 }else{
			Object[][] colaboradores = tabla_folio_y_nombre_completo();
			for(int i=0; i<colaboradores.length; i++){
				Comentario_colaboradores=Comentario_colaboradores+" / "+colaboradores[i][1].toString().trim();
				testigo=1;
			}
		 }	
			
		if(testigo>0){
			txa_Resultado_Seleccion.setText(Comentario_colaboradores);
			}else{
				tabla_seleccion_default_usuario();
				txa_Resultado_Seleccion.setText(Comentario_colaboradores+"\n"+usuario.getNombre_completo());
			}
	}
	
	@SuppressWarnings("rawtypes")
	Vector vectorFolios = new Vector();
	@SuppressWarnings("unchecked")
	public void tabla_seleccion_default_usuario(){
		String Comentario_colaboradores_default="";
		for(int i=0; i<tabla.getRowCount(); i++){
			 if(Integer.valueOf(tabla.getValueAt(i,0).toString().trim())==(usuario.getFolio())){
				  model.setValueAt("true", i, 5);
				  vectorFolios.add(tabla.getValueAt(i, 1));
				  Comentario_colaboradores_default=Comentario_colaboradores_default+" / "+tabla.getValueAt(i, 1);
				  txa_Resultado_Seleccion.setText(Comentario_colaboradores_default);
		     }
		}
	}
//			
//	@SuppressWarnings("unchecked")
//	public void llenar_arreglo(){
//		int fila = tabla.getSelectedRow();
//		
//		if(Boolean.valueOf(tabla.getValueAt(fila, 5).toString())){
//			vectorFolios.add(tabla.getValueAt(fila, 1));
//		}else{
//			
//			for(int i = 0; i < vectorFolios.size(); i++){
//				if(vectorFolios.get(i).toString().equals(tabla.getValueAt(fila, 1).toString())){
//					vectorFolios.remove(i);
//				}
//			}
//		}
//		String Comentario_colaboradores="";
//		for(int i = 0; i < vectorFolios.size(); i++){
//			Comentario_colaboradores=Comentario_colaboradores+" / "+vectorFolios.get(i);
//		}
//		
//		if(Comentario_colaboradores.equals("")){
//			txa_Resultado_Seleccion.setText(Comentario_colaboradores+" / "+usuario.getNombre_completo());
//		}else{
//			txa_Resultado_Seleccion.setText(Comentario_colaboradores);
//		}
//	}
	
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String[][] tabla_folio_y_nombre_completo(){ 
		int cantidad_de_columnas_matriz=2;
		Vector vector = new Vector();
		for(int i=0; i<tabla.getRowCount(); i++){
			 if(Boolean.valueOf(tabla.getValueAt(i,5).toString().trim())){
				  vector.add(model.getValueAt(i,0).toString().trim());
				  vector.add(model.getValueAt(i,1).toString().trim());
		     }
		}
			String[][] matriz = new String[vector.size()/cantidad_de_columnas_matriz][cantidad_de_columnas_matriz];
			 int i=0,j =0,columnafor=0;
			while(i<vector.size()){
				columnafor=0;
			      for(int f =0;  f<cantidad_de_columnas_matriz;  f++,columnafor++,i++  ){	
			  matriz[j][columnafor] = vector.get(i).toString();
			  }
			  j++;
		}
		return matriz;
	}

	@SuppressWarnings("unchecked")
	public void init_tabla_pendientes(){
		this.tablaPendientes.getTableHeader().setReorderingAllowed(false) ;
		this.tablaPendientes.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		this.tablaPendientes.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",12));
		this.tablaPendientes.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",12));
		this.tablaPendientes.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",12));
		this.tablaPendientes.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",9));
		
		this.tablaPendientes.getColumnModel().getColumn(0).setMaxWidth(60);
		this.tablaPendientes.getColumnModel().getColumn(0).setMinWidth(270);
		this.tablaPendientes.getColumnModel().getColumn(1).setMinWidth(580);
		this.tablaPendientes.getColumnModel().getColumn(1).setMaxWidth(1100);
		this.tablaPendientes.getColumnModel().getColumn(2).setMinWidth(120);
		this.tablaPendientes.getColumnModel().getColumn(2).setMaxWidth(120);
		this.tablaPendientes.getColumnModel().getColumn(3).setMinWidth(2000);
		this.tablaPendientes.getColumnModel().getColumn(3).setMaxWidth(5000);

		this.tablaPendientes.setRowSorter(trsfiltroP);  
		refrestabla_pendientes();
	}
	
	private void refrestabla_pendientes(){
		modelo.setRowCount(0);
		Statement s;
		ResultSet rs;
		try {
			Connexion con = new Connexion();
			s = con.conexion().createStatement();
			rs = s.executeQuery("exec sp_select_pendientes_de_colaboradores "+usuario.getFolio());
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
			JOptionPane.showMessageDialog(null, "Error en la funcion refrestabla_pendientes SQLException: "+e1.getMessage(), "Avisa al Administrador Del Sistema", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
		}
	}
	
	public void init_tabla(){
		this.tabla.getTableHeader().setReorderingAllowed(false) ;
		this.tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		this.tabla.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",12));
		this.tabla.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",12));
		this.tabla.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",12));
		this.tabla.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",12));
		this.tabla.getColumnModel().getColumn(4).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",12));
		this.tabla.getColumnModel().getColumn(5).setCellRenderer(new tablaRenderer("CHB","centro","Arial","negrita",12));
		
		this.tabla.getColumnModel().getColumn(0).setMaxWidth(40);
		this.tabla.getColumnModel().getColumn(0).setMinWidth(40);
		this.tabla.getColumnModel().getColumn(1).setMinWidth(335);
		this.tabla.getColumnModel().getColumn(1).setMaxWidth(335);
		this.tabla.getColumnModel().getColumn(2).setMinWidth(140);
		this.tabla.getColumnModel().getColumn(2).setMaxWidth(140);
		this.tabla.getColumnModel().getColumn(3).setMinWidth(185);
		this.tabla.getColumnModel().getColumn(3).setMaxWidth(500);
		this.tabla.getColumnModel().getColumn(4).setMinWidth(240);
		this.tabla.getColumnModel().getColumn(4).setMaxWidth(500);
		this.tabla.getColumnModel().getColumn(5).setMinWidth(20);
		this.tabla.getColumnModel().getColumn(5).setMaxWidth(30);
		refrestabla();
	}
	
	private void refrestabla(){
		model.setRowCount(0);
		Statement s;
		ResultSet rs;
		try {
			Connexion con = new Connexion();
			s = con.conexion().createStatement();
			rs = s.executeQuery("exec sp_filtro_empleado_para_pendientes "+usuario.getFolio());
			while (rs.next())
			{  String [] fila = new String[6];
			   fila[0] = rs.getString(1).trim();
			   fila[1] = rs.getString(2).trim();
			   fila[2] = rs.getString(3).trim(); 
			   fila[3] = rs.getString(4).trim(); 
			   fila[4] = rs.getString(5).trim(); 
			   fila[5] = "false";
			   model.addRow(fila); 
			}	
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en la funcion  Subclase Cat_Seleccion_Del_Ususario SQLException: "+e1.getMessage(), "Avisa al Administrador Del Sistema", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
		}
	}
	
	
	public static void main(String[] args) {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Pendientes().setVisible(true);
		}catch(Exception e){	}	
	}
}
