package Cat_Planeacion;

import java.awt.Container;
import java.awt.Event;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
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
import javax.swing.table.TableRowSorter;

import Conexiones_SQL.Connexion;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Principal.Componentes;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_Filtro_Dinamico_Plus;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Pendientes_Para_El_Plan extends JFrame{
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
	
	@SuppressWarnings("rawtypes")
	private TableRowSorter trsfiltro;
	
	JTextArea txa_Pendiente = new Componentes().textArea(new JTextArea(), "Detalle Del Pendiente", 250);
	JTextArea txa_Resultado_Seleccion = new Componentes().textArea(new JTextArea(), "Concepto", 135);
	JTextField txtFiltro= new Componentes().text(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<", 300, "String");
	
	JScrollPane pendiente = new JScrollPane(txa_Pendiente);
	JScrollPane Resultado = new JScrollPane(txa_Resultado_Seleccion);
	
	JButton btnAgregar = new JButton("Aplicar",new ImageIcon("imagen/Aplicar.png"));
	JButton btnDeshacer = new JButton("Deshacer",new ImageIcon("imagen/deshacer16.png"));
	
	Obj_Usuario usuario = new Obj_Usuario().LeerSession();
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Cat_Pendientes_Para_El_Plan()	{
		this.setSize(1024,740);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/ayudar-a-ver-el-boton-icono-4900-64.png"));
		this.setTitle("Filtro De Seleccion De Colaboradores");
	    this.panel.setBorder(BorderFactory.createTitledBorder("Selecciona A El (Los) Colaborador(es) A El (Los) Que Aplicara La Actividad"));
		this.trsfiltro = new TableRowSorter(model); 
		this.tabla.setRowSorter(trsfiltro);  
		this.txa_Resultado_Seleccion.setEditable(false);
		this.txa_Resultado_Seleccion.setLineWrap(true); 
		this.txa_Resultado_Seleccion.setWrapStyleWord(true);
		this.txa_Pendiente.setLineWrap(true); 
		this.txa_Pendiente.setWrapStyleWord(true);
		
		int x=15,y=20,width=100,height=20;

        this.panel.add(txa_Pendiente).setBounds                                               (x     ,y     ,width*9+40 ,height);
		this.panel.add(txtFiltro).setBounds                                                   (x     ,y+=100,width*9+40 ,height);
		this.panel.add(scroll).setBounds                                                      (x     ,y+=20 ,width*10-10,width*2);
		
		x=15;
		this.panel.add(new JLabel("     Detalle De La Configuracion Seleccionada:")).setBounds(x     ,y=y+200,width*3    ,height);
		this.panel.add(Resultado  ).setBounds                                                 (x     ,y+=20 ,width*10-10,width);
		this.panel.add(btnDeshacer).setBounds                                                 (x     ,y+=305,width      ,height);
		this.panel.add(btnAgregar).setBounds                                                  (x+892 ,y     ,width      ,height);
		
		this.cont.add(panel);
		this.init_tabla();
		comentario();
		this.tabla.addMouseListener(opcomentario);
		
		this.tabla.addKeyListener(opseleccioncontecladocomentario);
		this.txtFiltro.addKeyListener(opFiltroFolio);
		
		this.btnAgregar.addActionListener(Agregar);
		this.btnDeshacer.addActionListener(deshacer);
		
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

	}
	
	ActionListener Agregar = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			if(tabla.isEditing()){
				tabla.getCellEditor().stopCellEditing();
			}
			if(!txtFiltro.getText().equals("")){
				JOptionPane.showMessageDialog(null, "No se Puede Aplicar En Medio De Una Busqueda, Por Que Podrian Haber Mas Colaboradores Seleccionados "
						                          + "\nA Continuación Se Borrará El Filtro Para Mostrarle Todos Los Colaboradores y Podrá Aplicar De Nuevo"
                                                  , "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
				txtFiltro.setText("");
				int[] columnas = {0,1,2,3,4};
				new Obj_Filtro_Dinamico_Plus(tabla,txtFiltro.getText(), columnas);
				comentario();
				return;
			}
//			usuarios.setUsuarios_nombres(tabla_folio_y_nombre_completo());
								dispose();
		}
	};
	
	ActionListener deshacer = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			txa_Resultado_Seleccion.setText("");
			refrestabla();
			comentario();
		 }
		};
	
	MouseListener opcomentario = new MouseListener() {
		public void mouseReleased(MouseEvent arg0) {}
		public void mousePressed(MouseEvent arg0) {}
		public void mouseExited(MouseEvent arg0) {}
		public void mouseEntered(MouseEvent arg0) {}
		public void mouseClicked(MouseEvent arg0) {
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
	
	public void comentario(){
		txa_Resultado_Seleccion.setText("");
		String Comentario_colaboradores="";
		int testigo=0;
		 if(!txtFiltro.getText().toString().equals("")){
			 Comentario_colaboradores="Esta Filtrando La Tabla No Se Pueden Mostrar Los Empleado Seleccionados:";
			 testigo=1;
		 }else{
			Object[][] colaboradores = tabla_folio_y_nombre_completo();
			 Comentario_colaboradores="Esta actividad Aplica Para Los Siguientes Colaboradores:";
			for(int i=0; i<colaboradores.length; i++){
				Comentario_colaboradores=Comentario_colaboradores+"/ *"+colaboradores[i][1].toString().trim();
				testigo=1;
			}
		 }	
			
		if(testigo>0){
			txa_Resultado_Seleccion.setText(Comentario_colaboradores);
			}else{
				tabla_seleccion_default_usuario();
				txa_Resultado_Seleccion.setText(Comentario_colaboradores+"\n  *"+usuario.getNombre_completo());
			}
	}
	
	public void tabla_seleccion_default_usuario(){
		for(int i=0; i<tabla.getRowCount(); i++){
			 if(Integer.valueOf(tabla.getValueAt(i,0).toString().trim())==(usuario.getFolio())){
				  model.setValueAt("true", i, 5);
		     }
		}
	}
	
	public void Carga_Desde_El_Objeto(){
//		Object[][] colaboradores=usuarios.getUsuarios_nombres();
//			for(int i2=0; i2<colaboradores.length; i2++){
//				for(int i=0; i<tabla.getRowCount(); i++){
//				   if( (tabla.getValueAt(i,1).toString().trim()).equals(colaboradores[i2][1].toString().trim())){
//				  model.setValueAt("true", i, 5);
//		      }
//			}
//		}
	};
	
	
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
		this.tabla.setRowSorter(trsfiltro);  
		refrestabla();
		
//		if(!(usuarios.getUsuarios_nombres()==null)){
//			Carga_Desde_El_Objeto();
//		}
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
			JOptionPane.showMessageDialog(null, "Error en la Subclase Cat_Seleccion_Del_Ususario SQLException: "+e1.getMessage(), "Avisa al Administrador Del Sistema", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
		}
	}
	
	public static void main(String[] args) {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Pendientes_Para_El_Plan().setVisible(true);
		}catch(Exception e){	}	
	}
}
