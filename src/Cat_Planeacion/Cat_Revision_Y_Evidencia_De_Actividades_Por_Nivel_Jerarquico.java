package Cat_Planeacion;

import java.awt.Container;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import com.toedter.calendar.JDateChooser;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Connexion;
import Conexiones_SQL.Generacion_Reportes;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Planeacion.Obj_Seleccion_De_Usuarios;
import Obj_Principal.Componentes;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_Filtro_Dinamico_Plus;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Revision_Y_Evidencia_De_Actividades_Por_Nivel_Jerarquico extends JFrame{

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
    
	DefaultTableModel model_actividades = new DefaultTableModel(null, new String[]{"Folio", "Actividad", "Respuesta", "Observacion", "Tiene Evidencia"}
			){
	     @SuppressWarnings("rawtypes")
		Class[] types = new Class[]{
	    	java.lang.String.class,
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
	
	JTable tabla_actividades = new JTable(model_actividades);
    JScrollPane scroll_actividades = new JScrollPane(tabla_actividades,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
	
	@SuppressWarnings("rawtypes")
	private TableRowSorter trsfiltro;
	JTextField txtFiltro= new Componentes().text(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<", 300, "String");
	
	JButton btnReporte_cuadros  = new JButton("Plan Semanal"        ,new ImageIcon("imagen/mensual-de-la-agenda-icono-7455-32.png")           );
	JButton btnReporte_cntestad = new JButton("Plan Contestado "    ,new ImageIcon("imagen/mensual-de-la-agenda-contestado-7455-32.png")      );
	JButton btnReporte_act_vig  = new JButton("Actividades Vigentes" ,new ImageIcon("imagen/reinicio-pelota-cute-icono-7443-32.png")          );
	JButton btnReporte_lista    = new JButton("Lista De Actividades");
	
	JRadioButton rbActividadesRevisadasFecha = new JRadioButton("Reporte De Actividades Con Respuesta Por Fecha");
	JRadioButton rbActividadesRevisadasPeriodo = new JRadioButton("Reporte De Actividades Con Respuesta Por Periodo");
	JRadioButton rbActividadesCanceladasFecha = new JRadioButton("Reporte De Actividades Caceladas Y Terminadas Por Fecha");
	JRadioButton rbActividadesCanceladasPeriodo = new JRadioButton("Reporte De Actividades Caceladas Y Terminadas Por Periodo");
	JRadioButton rbActividadesCanceladasTodas = new JRadioButton("Reporte De Actividades Caceladas Y Terminadas");
	ButtonGroup AgruparRb = new ButtonGroup();
	
	JButton btnGenerarArchivo = new JButton("Generar Evidencia",new ImageIcon("imagen/Aplicar.png"));
	JButton btnDeshacer = new JButton("Deshacer",new ImageIcon("imagen/deshacer16.png"));
	
	JDateChooser fch_busqueda = new JDateChooser();
	
	Obj_Usuario usuario = new Obj_Usuario().LeerSession();
	Obj_Seleccion_De_Usuarios usuarios= new Obj_Seleccion_De_Usuarios();
	
	Border blackline;
	JLabel lblContorno = new JLabel();
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Cat_Revision_Y_Evidencia_De_Actividades_Por_Nivel_Jerarquico(){
		this.setSize(980,660);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/revision.png"));
		this.setTitle("Revision y Evidencia De Actividades");
	    this.panel.setBorder(BorderFactory.createTitledBorder("Selecciona El Colaborador Al Que Aplicara La Revision"));
		this.trsfiltro = new TableRowSorter(model); 
		this.tabla.setRowSorter(trsfiltro);  
		
		blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
		this.lblContorno.setBorder(BorderFactory.createTitledBorder(blackline,"Seleccionar Tipo De Reporte"));
		
		this.fch_busqueda.setIcon(new ImageIcon("Iconos/calendar_icon&16.png"));
		
		AgruparRb.add(rbActividadesRevisadasFecha	);
		AgruparRb.add(rbActividadesRevisadasPeriodo	);
		AgruparRb.add(rbActividadesCanceladasFecha	);
		AgruparRb.add(rbActividadesCanceladasPeriodo);
		AgruparRb.add(rbActividadesCanceladasTodas	);
		
		rbActividadesRevisadasPeriodo.setSelected(true);
		
		int x=15,y=20,width=100,height=20;

		this.panel.add(txtFiltro).setBounds(x     ,y     ,width*9+40 ,height);
		this.panel.add(scroll).setBounds   (x     ,y+=20 ,width*9+40 ,width*2-20);
		
		this.panel.add(new JLabel("Fecha: ")).setBounds (x     ,y+=(width*2-10) ,60 ,20);
		this.panel.add(fch_busqueda).setBounds   		(x+50  ,y				,120,20);
		
		this.panel.add(scroll_actividades).setBounds   (x      ,y+=30 ,width*9+40 ,width*2-20);
		
		this.panel.add(lblContorno						).setBounds(x,y+=185,330,185);
		this.panel.add(rbActividadesRevisadasFecha		).setBounds(20,y+=15,320,20);
		this.panel.add(rbActividadesRevisadasPeriodo	).setBounds(20,y+=25,320,20);
		this.panel.add(rbActividadesCanceladasFecha		).setBounds(20,y+=25,320,20);
		this.panel.add(rbActividadesCanceladasPeriodo	).setBounds(20,y+=25,320,20);
		this.panel.add(rbActividadesCanceladasTodas		).setBounds(20,y+=25,320,20);
		this.panel.add(btnReporte_lista					).setBounds(20,y+=25,320,38);
		y=450;
		this.panel.add(btnReporte_cuadros).setBounds (365  ,y    ,180,38);
		this.panel.add(btnGenerarArchivo).setBounds(x+760  ,y    ,180,38);
		this.panel.add(btnReporte_cntestad).setBounds(365  ,y+=45,180,38);
		this.panel.add(btnReporte_act_vig).setBounds (365  ,y+=45,180,38);
		
		ImageIcon tmpIconDefault = new ImageIcon(System.getProperty("user.dir")+"/imagen/checklistbtn.png");
	    Icon iconoDefault = new ImageIcon(tmpIconDefault.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT));
	    btnReporte_lista.setIcon(iconoDefault);
	    
		this.cont.add(panel);
		this.init_tabla();
		
		renders(tabla_actividades);
		
		txtFiltro.addKeyListener(opFiltroFolio);
		
		fch_busqueda.setDate(cargar_fecha(0));
		fch_busqueda.addPropertyChangeListener(calendario);
		buscarDeTabla(tabla);
		
		this.btnReporte_cuadros.addActionListener(Agregar);
		this.btnReporte_cntestad.addActionListener(Agregar);
		this.btnReporte_lista.addActionListener(Agregar);
		this.btnGenerarArchivo.addActionListener(Agregar);
		this.btnReporte_act_vig.addActionListener(Agregar);
		
	}
	
	public Date cargar_fecha(int dias){
		
		Date date1 = null;
				  try {
					date1 = new SimpleDateFormat("dd/MM/yyyy").parse(new BuscarSQL().fecha(dias));
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		return date1;
					
	};
	
	public void buscarDeTabla(final JTable tb){
		tb.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent arg0) {		}
			public void mousePressed(MouseEvent arg0) {			
				buscarActividades();
			}
			public void mouseExited(MouseEvent arg0) {			}
			public void mouseEntered(MouseEvent arg0) {			}
			public void mouseClicked(MouseEvent arg0) {			}
		});
	}
	
	PropertyChangeListener calendario = new PropertyChangeListener() {
		public void propertyChange(PropertyChangeEvent evt) {
			
			if ("date".equals(evt.getPropertyName())){
				buscarActividades();
			}
		}
	};
	
	public void buscarActividades(){
		
		int fila = tabla.getSelectedRow();
		
		if(fila<0){
			JOptionPane.showMessageDialog(null, "Seleccione Un Empleado Antes De Ejecutar Una Accion", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
			return;
		}else{
			int folio_empleado = Integer.valueOf(tabla.getValueAt(fila, 0).toString().trim());
			String fecha = new SimpleDateFormat("dd/MM/yyyy").format(fch_busqueda.getDate());

			model_actividades.setRowCount(0);
			String[][] actividadesCont = new BuscarSQL().actividadesConEvidencia(fecha, folio_empleado);
			for(String[] reg : actividadesCont){
				model_actividades.addRow(reg);
			}
			
		}
		
	}
	
	public void renders(final JTable tb){
		
		tb.getTableHeader().setReorderingAllowed(false) ;
		tb.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
			tb.getColumnModel().getColumn(0).setMaxWidth(50);
			tb.getColumnModel().getColumn(0).setMinWidth(50);
			tb.getColumnModel().getColumn(1).setMaxWidth(500);
			tb.getColumnModel().getColumn(1).setMinWidth(330);
			tb.getColumnModel().getColumn(2).setMaxWidth(100);
			tb.getColumnModel().getColumn(2).setMinWidth(100);
			tb.getColumnModel().getColumn(3).setMaxWidth(500);
			tb.getColumnModel().getColumn(3).setMinWidth(350);
			tb.getColumnModel().getColumn(4).setMaxWidth(90);
			tb.getColumnModel().getColumn(4).setMinWidth(90);
			
			tb.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12)); 
			tb.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
			tb.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","centro","Arial","normal",12));
			tb.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
			tb.getColumnModel().getColumn(4).setCellRenderer(new tablaRenderer("texto","centro","Arial","normal",12)); 
	}
	
	ActionListener Agregar = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			
			String basedatos="2.26";
			String vista_previa_reporte="no";
			int vista_previa_de_ventana=0;
			String comando=""  ;
			String reporte = "";
			
			if(tabla.getSelectedRow()<0){
						JOptionPane.showMessageDialog(null, "Seleccione Un Empleado Antes De Ejecutar Una Accion", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
						return;
			}else{
				
						String fecha = new SimpleDateFormat("dd/MM/yyyy").format(fch_busqueda.getDate());
						int folio_empleado = Integer.valueOf(tabla.getValueAt(tabla.getSelectedRow(),0).toString());
						
						if(arg0.getActionCommand().trim().equals("Plan Semanal")){
							comando="exec sp_reporte_de_plan_semanal_por_dia "+tabla.getValueAt(tabla.getSelectedRow(), 0)+",'"+fecha+"'"  ;
							reporte = "Obj_Reporte_De_Plan_Semanal_Cuadros.jrxml";
							new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
						}
						
						if(arg0.getActionCommand().trim().equals("Plan Contestado")){
							comando="exec sp_reporte_de_plan_semanal_por_dia_contestado "+tabla.getValueAt(tabla.getSelectedRow(), 0)+",'"+fecha+" 23:59:59'"  ;
							reporte = "Obj_Reporte_De_Plan_Semanal_Cuadros_Contestado.jrxml";
							new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
						}
						
						if(arg0.getActionCommand().trim().equals("Actividades Vigentes")){
							comando="exec sp_Reporte_De_Actividades_Vigentes_De_Un_Colaborador "+tabla.getValueAt(tabla.getSelectedRow(), 0);
							reporte = "Obj_Reportes_De_Actividades_Vigentes.jrxml";
							new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
						}
						
						
						if(arg0.getActionCommand().trim().equals("Lista De Actividades")){
							
							String nombreReporte = rbActividadesRevisadasFecha.isSelected()?rbActividadesRevisadasFecha.getActionCommand():(
							rbActividadesRevisadasPeriodo.isSelected()?rbActividadesRevisadasPeriodo.getActionCommand():(
							rbActividadesCanceladasFecha.isSelected()?rbActividadesCanceladasFecha.getActionCommand():(
							rbActividadesCanceladasPeriodo.isSelected()?rbActividadesCanceladasPeriodo.getActionCommand():(
							rbActividadesCanceladasTodas.isSelected()?rbActividadesCanceladasTodas.getActionCommand():"Otro Posible Reporte"))));
							
							comando="exec sp_Reporte_De_Actividades_Contestadas_Del_Plan_Semanal "+tabla.getValueAt(tabla.getSelectedRow(), 0)+",'"+fecha+"','"+nombreReporte+"'"  ;
							reporte = "Obj_Reportes_De_Actividades_Contestadas.jrxml";
							new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
						}
						
						if(arg0.getActionCommand().equals("Generar Evidencia")){
							
							if(folio_empleado<0){
								JOptionPane.showMessageDialog(null, "Es Necesario Seleccionar Un Empleado", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
								return;
							}else{
								if(tabla_actividades.getRowCount()==0){
									JOptionPane.showMessageDialog(null, "El Empleado No Tiene Actidades Capturadas En La Fecha Especificada,\nPor Lo Cual No Se Generaran Archivos De Evidencia", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
									return;
								}else{
									
									int registros = new BuscarSQL().Evidencia_De_Actividades_Capturadas(fecha, folio_empleado);
									JOptionPane.showMessageDialog(null, "Se Generaron ["+registros+"] Archivos En La Ruta:\nC:\\EVIDENCIA DE ACTIVIDADES PLAN SEMANAL SCOI\nCon El Nombre Del Empleado Seleccionado ","Aviso", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/aplicara-el-dialogo-icono-6256-32.png"));
									return;
								}
							}
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
			rs = s.executeQuery("exec sp_filtro_empleado_actividades_status_vigente "+usuario.getFolio());
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
			JOptionPane.showMessageDialog(null, "Error en la Clase Cat_Revision_Y_Evidencia_De_Actividades_Por_Nivel_Jerarquico SQLException: "+e1.getMessage(), "Avisa al Administrador Del Sistema", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
		}
	}
	
	KeyListener opFiltroFolio = new KeyListener(){
		public void keyReleased(KeyEvent arg0) {
			int[] columnas = {0,1,2,3,4};
			new Obj_Filtro_Dinamico_Plus(tabla, txtFiltro.getText().toUpperCase(), columnas);
		}
		public void keyTyped(KeyEvent arg0)   {}
		public void keyPressed(KeyEvent arg0) {}		
	};
//	private void refrestabla(){
//		model.setRowCount(0);
//		Statement s;
//		ResultSet rs;
//		try {
//			Connexion con = new Connexion();
//			s = con.conexion().createStatement();
//			rs = s.executeQuery("exec sp_select_empleados_por_nivel_jerarquico_para_revision_y_asignacion_de_actividades "+usuario.getFolio());
//			while (rs.next())
//			{  String [] fila = new String[5];
//			   fila[0] = rs.getString(1).trim();
//			   fila[1] = rs.getString(2).trim();
//			   fila[2] = rs.getString(3).trim(); 
//			   fila[3] = rs.getString(4).trim(); 
//			   fila[4] = rs.getString(5).trim(); 
//			   model.addRow(fila); 
//			}	
//		} catch (SQLException e1) {
//			e1.printStackTrace();
//			JOptionPane.showMessageDialog(null, "Error en la Clase Cat_Revision_Y_Evidencia_De_Actividades_Por_Nivel_Jerarquico SQLException: "+e1.getMessage(), "Avisa al Administrador Del Sistema", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
//		}
//	}
	
	public static void main(String [] arg){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Revision_Y_Evidencia_De_Actividades_Por_Nivel_Jerarquico().setVisible(true);
		}catch(Exception e){	}
	}

}
