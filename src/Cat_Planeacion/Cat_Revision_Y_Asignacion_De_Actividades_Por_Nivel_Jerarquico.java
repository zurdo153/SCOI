package Cat_Planeacion;

import java.awt.Container;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Conexiones_SQL.Connexion;
import Conexiones_SQL.Generacion_Reportes;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Planeacion.Obj_Seleccion_De_Usuarios;
import Obj_Principal.Componentes;
import Obj_Principal.JCTextField;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Revision_Y_Asignacion_De_Actividades_Por_Nivel_Jerarquico extends JFrame{

	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	DefaultTableModel model = new DefaultTableModel(null, new String[]{"Folio", "Nombre Completo","Establecimiento","Departamento","Puesto"}
			){
	     @SuppressWarnings("rawtypes")
		Class[] types = new Class[]{
	    	java.lang.Integer.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class
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
        	 } 				
 			return false;
 		}
		
	};
	
	JTable tabla = new JTable(model);
    JScrollPane scroll = new JScrollPane(tabla);
	
	@SuppressWarnings("rawtypes")
	private TableRowSorter trsfiltro;
	JTextField txtFiltro= new Componentes().text(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<", 300, "String");
	
	JButton btnReporte_cuadros  = new JButton("Plan Semanal"      ,new ImageIcon("imagen/mensual-de-la-agenda-icono-7455-32.png")           );
	JButton btnReporte_cntestad = new JButton("Plan Contestado "  ,new ImageIcon("imagen/mensual-de-la-agenda-contestado-7455-32.png")      );
	JButton btnReporte_lista  = new JButton("Actividades Con Respuesta");
	
	JButton btnAgregar = new JButton("Agregar Actividad",new ImageIcon("imagen/Aplicar.png"));
	JButton btnDeshacer = new JButton("Deshacer",new ImageIcon("imagen/deshacer16.png"));
	
	Obj_Usuario usuario = new Obj_Usuario().LeerSession();
	Obj_Seleccion_De_Usuarios usuarios= new Obj_Seleccion_De_Usuarios();
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Cat_Revision_Y_Asignacion_De_Actividades_Por_Nivel_Jerarquico()	{
		this.setSize(980,550);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/ayudar-a-ver-el-boton-icono-4900-64.png"));
		this.setTitle("Filtro Revision y Asignacion De Actividades");
	    this.panel.setBorder(BorderFactory.createTitledBorder("Selecciona A El (Los) Colaborador(es) A El (Los) Que Aplicara La Actividad"));
		this.trsfiltro = new TableRowSorter(model); 
		this.tabla.setRowSorter(trsfiltro);  
		
		int x=15,y=20,width=100,height=20;

		this.panel.add(txtFiltro).setBounds(x     ,y     ,width*9+40 ,height);
		this.panel.add(scroll).setBounds   (x     ,y+=20 ,width*9+40 ,width*4);
		
		this.panel.add(btnReporte_cuadros).setBounds(15,y+=420,150,38);
		this.panel.add(btnReporte_cntestad).setBounds(185,y,150,38);
		this.panel.add(btnReporte_lista).setBounds(355,y,200,38);
		
		this.panel.add(btnAgregar).setBounds(x+790,y,150,38);
		
		ImageIcon tmpIconDefault = new ImageIcon(System.getProperty("user.dir")+"/imagen/checklistbtn.png");
	    Icon iconoDefault = new ImageIcon(tmpIconDefault.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
	    btnReporte_lista.setIcon(iconoDefault);
	    
		this.cont.add(panel);
		this.init_tabla();
		
		this.btnReporte_cuadros.addActionListener(Agregar);
		this.btnReporte_cntestad.addActionListener(Agregar);
		this.btnReporte_lista.addActionListener(Agregar);
		this.btnAgregar.addActionListener(Agregar);
//		this.btnDeshacer.addActionListener(deshacer);
		
//		///guardar con control+A
//        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A,Event.CTRL_MASK),"guardar");
//             getRootPane().getActionMap().put("guardar", new AbstractAction(){
//                 public void actionPerformed(ActionEvent e)
//                 {                 	    btnAgregar.doClick();           	    }
//            });
// 	     //deshacer con escape
// 	                 getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
// 	                   getRootPane().getActionMap().put("escape", new AbstractAction(){
// 	                  public void actionPerformed(ActionEvent e)
// 	                  {                btnDeshacer.doClick();           	    }
// 	              });

	}
	
	ActionListener Agregar = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			
			System.out.println(arg0.getActionCommand());
			
			String basedatos="2.26";
			String vista_previa_reporte="no";
			int vista_previa_de_ventana=0;
			String comando=""  ;
			String reporte = "";
			
			if(tabla.getSelectedRow()<0){
				JOptionPane.showMessageDialog(null, "Seleccione Un Empleado Antes De Ejecutar Una Accion", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
//				aviso (selecciones un empleado)
			}else{
				
				if(arg0.getActionCommand().trim().equals("Plan Semanal")){
					comando="exec sp_reporte_de_plan_semanal_por_dia "+tabla.getValueAt(tabla.getSelectedRow(), 0)+",'28/12/2015'"  ;
					reporte = "Obj_Reporte_De_Plan_Semanal_Cuadros.jrxml";
					new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
				}
				if(arg0.getActionCommand().trim().equals("Plan Contestado")){
					comando="exec sp_reporte_de_plan_semanal_por_dia_contestado "+tabla.getValueAt(tabla.getSelectedRow(), 0)+",'28/12/2015 23:59:59'"  ;
					reporte = "Obj_Reporte_De_Plan_Semanal_Cuadros_Contestado.jrxml";
					new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
				}
				if(arg0.getActionCommand().trim().equals("Actividades Con Respuesta")){
					comando="exec sp_Reporte_De_Actividades_Contestadas_Del_Plan_Semanal "+tabla.getValueAt(tabla.getSelectedRow(), 0)+",'28/12/2015'"  ;
					reporte = "Obj_Reportes_De_Actividades_Contestadas.jrxml";
					new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
				}
				if(arg0.getActionCommand().equals("Agregar Actividad")){
//					catalogo para asignar actividad
				}
				 
			}
			

			
		}
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
		this.tabla.setRowSorter(trsfiltro);  
		
		refrestabla();
		
	}
	
	private void refrestabla(){
		model.setRowCount(0);
		Statement s;
		ResultSet rs;
		try {
			Connexion con = new Connexion();
			s = con.conexion().createStatement();
			rs = s.executeQuery("exec sp_select_empleados_por_nivel_jerarquico_para_revision_y_asignacion_de_actividades "+usuario.getFolio());
			while (rs.next())
			{  String [] fila = new String[5];
			   fila[0] = rs.getString(1).trim();
			   fila[1] = rs.getString(2).trim();
			   fila[2] = rs.getString(3).trim(); 
			   fila[3] = rs.getString(4).trim(); 
			   fila[4] = rs.getString(5).trim(); 
			   model.addRow(fila); 
			}	
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en la Subclase Cat_Seleccion_Del_Ususario SQLException: "+e1.getMessage(), "Avisa al Administrador Del Sistema", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
		}
	}
	
	public static void main(String [] arg){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Revision_Y_Asignacion_De_Actividades_Por_Nivel_Jerarquico().setVisible(true);
		}catch(Exception e){	}
	}

}
