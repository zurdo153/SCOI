package Cat_Checador;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

import com.toedter.calendar.JDateChooser;

import Conexiones_SQL.ActualizarSQL;
import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.BuscarTablasModel;
import Conexiones_SQL.Generacion_Reportes;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_Filtro_Dinamico;

@SuppressWarnings("serial")
public class Cat_Consideraciones_De_Impuntualidad_Por_Nivel_Jerarquico extends JFrame {

	Obj_Usuario usuario = new Obj_Usuario().LeerSession();
	
	String status_funcion  =	new BuscarSQL().Op_status_consideracion().toString().trim();
	String existe_nivel_jerarquico =new BuscarSQL().existe_nivel_jerarquico(usuario.getFolio()).toString().trim();
	   
	String Folio           = String.valueOf(usuario.getFolio());
	String empleado        = ""; 	
	String fecha           = ""; 	
	String ent_sal         = ""; 	
	String tipo_checada    = ""; 	
	String Status_Mov      = "";
	String folio           = "";
	String Establecimiento = "Selecciona un Establecimiento";
	String Departamento    = "Selecciona un Departamento";
	String observacion     = "";

	int folio_emp             = 0; 	
	int imp                   = 0; 	
	int fav                   = 0; 	
	int realizo_consideracion = 0;
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JDateChooser c_inicio = new JDateChooser();
	JDateChooser c_final = new JDateChooser();
	
	JLabel JLBlinicio=         new JLabel(new ImageIcon("Imagen/iniciar-icono-4628-16.png")         );
	JLabel JLBfin=             new JLabel(new ImageIcon("Imagen/acabado-icono-7912-16.png")         );
	JLabel JLBStatus=          new JLabel(""                                                        );
	
	JTextField txtNombre = new Componentes().text(new JCTextField(), "Teclea Para Buscar En La Tabla", 300, "String");
	
	JCButton btnGenerar         = new JCButton("Actualizar"              ,"Actualizar.png","Azul");
	JCButton btnConsideraciones = new JCButton("Reporte Consideraciones" ,"Lista.png","Azul"     );
	JCButton btnPermisos        = new JCButton("Reporte Permisos"        ,"Lista.png","Azul"     );
	

	@SuppressWarnings("rawtypes")
	private TableRowSorter trsfiltro;
	
	DefaultTableModel modelo = new DefaultTableModel(null,
            new String[]{ "Folio", "Nombre", "Fecha", "Hora", "Dia",
							"Entrada-Salida", "Tipo Mov", "15m/Comida", "Impuntualidad", "Favor", "Tipo Permiso",
							"Min.Imp.Considerados", "Min.Fav.Considerados", "Observaciones", "Realizo Consideracion", "Estatus Registro","Estatus Modificacion"}
			){
	     @SuppressWarnings("rawtypes")
		Class[] types = new Class[]{
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
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
        	 	case 0  : return false; 
        	 	case 1  : return false; 
        	 	case 2  : return false;
        	 	case 3  : return false;
        	 	case 4  : return false;
        	 	case 5  : return false;
        	 	case 6  : return false; 
        	 	case 7  : return false;
        	 	case 8  : return false;
        	 	case 9  : return false;
        	 	case 10 : return false;
        	 	case 11 : return false; 
        	 	case 12 : return false;
        	 	case 13 : return false;
        	 	case 14 : return false;
        	 	case 15 : return false;
        	 	case 16 : return false;
        	 	} 				
 			return false;
 		}
	};
	    
	JTable tabla = new JTable(modelo);
	JScrollPane scroll = new JScrollPane(tabla,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

	Border blackline;
	@SuppressWarnings({ "unchecked", "rawtypes" })
	
	public Cat_Consideraciones_De_Impuntualidad_Por_Nivel_Jerarquico(){
		int ancho = Toolkit.getDefaultToolkit().getScreenSize().width;
		int alto = Toolkit.getDefaultToolkit().getScreenSize().height;
		
		setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds()); 
		
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/check-vcard-icone-9025-32.png"));
		this.setTitle("Consideracion De Impuntualidad Por Nivel Jerarquico");
		this.panel.setBorder(BorderFactory.createTitledBorder(blackline, "Filtrar"));
		this.c_inicio.setIcon(new ImageIcon("Imagen/Calendar.png"));
		this.c_final.setIcon(new ImageIcon("Imagen/Calendar.png"));
		
		int x=15,y=20,width=100,height=20;
		this.panel.add(new JLabel("Fecha Inicio:")).setBounds (x       ,y    ,width   ,height     );
		this.panel.add(JLBlinicio).setBounds                  (x+=60   ,y    ,height  ,height     );
		this.panel.add(c_inicio).setBounds                    (x+=20   ,y    ,width   ,height     );
		this.panel.add(new JLabel("Fecha Final:")).setBounds  (x+=130  ,y    ,width   ,height     );
		this.panel.add(JLBfin).setBounds                      (x+=60   ,y    ,height  ,height     );
		this.panel.add(c_final).setBounds                     (x+=20   ,y    ,width   ,height     );
		this.panel.add(btnGenerar).setBounds                  (x+=252  ,y-10 ,width+50,height*2   );
		this.panel.add(btnConsideraciones).setBounds          (x+=180  ,y-10 ,width+110,height*2   );
		this.panel.add(btnPermisos).setBounds                 (x+=230  ,y-10 ,width+60 ,height*2   );
		
		x=10;
		this.panel.add(JLBStatus).setBounds                   (x       ,y+17  ,500   ,height      );
		this.panel.add(txtNombre).setBounds                   (x       ,y+=35,545     ,height     );
        this.panel.add(scroll).setBounds                      (x       ,y+=20,ancho-25,alto-(y+75));
		cont.add(panel);
		
		trsfiltro = new TableRowSorter(modelo); 
		tabla.setRowSorter(trsfiltro);  
		c_inicio.setEnabled(false);
		agregar(tabla);
		llamar_render();
		cargar_fechas();
		
		if(existe_nivel_jerarquico.equals("T")){
			btnGenerar.setEnabled(false);
			c_final.setEnabled(false);
			txtNombre.setEditable(false);
			JLBStatus.setText("<html> <FONT FACE="+"arial"+" SIZE=3 COLOR=RED><CENTER><b><p>La Consideracion Para su Puesto No Es Posible Comuniquese a Recursos Humanos y Mensione Que Alimenten El Nivel  Jerarquico De Su Puesto</p></b></CENTER></FONT></html>");	 
			JOptionPane.showMessageDialog(null,"La Consideracion Para su Puesto No Es Posible Comuniquese a Recursos Humanos y Mensione Que Alimenten El Nivel  Jerarquico De Su Puesto","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
		}else{
			if(status_funcion.equals("F")){
				btnGenerar.setEnabled(false);
				c_final.setEnabled(false);
				txtNombre.setEditable(false);
				JLBStatus.setText("<html> <FONT FACE="+"arial"+" SIZE=3 COLOR=RED><CENTER><b><p>La Consideracion Esta Desactivada  Hasta La Generacion De La Nueva Lista De Raya</p></b></CENTER></FONT></html>");	 
				JOptionPane.showMessageDialog(null,"La Consideracion Esta Desactivada  Hasta La Generacion De La Nueva Lista De Raya \n Si Tiene Pendientes De Considerar Comuniquese A Recursos Humanos","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
			}else{
				
				refrescar();
			}
		}
		txtNombre.addKeyListener(opFiltroFolioCajero);
		btnGenerar.addActionListener(opGenerarTabla);
		btnConsideraciones.addActionListener(op_generar_Consideraciones);
		btnPermisos.addActionListener(op_generar_permisos);
	}
	
	String basedatos="2.26";
	String comando="";
	String reporte = "";
	
	ActionListener op_generar_Consideraciones = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
				String fecha_inicio = new SimpleDateFormat("dd/MM/yyyy").format(c_inicio.getDate())+" 00:00:00";
				String fecha_final = new SimpleDateFormat("dd/MM/yyyy").format(c_final.getDate())+" 23:59:59";
				 reporte = "Obj_Reporte_De_Asistencia_General.jrxml";
				 comando = "exec sp_Reporte_De_Asistencia_Por_Establecimiento_Con_Consideraciones '"+fecha_inicio+"','"+fecha_final+"','"+Establecimiento+"','"+Departamento+"','"+Folio+"','SI'";
				 new Generacion_Reportes().Reporte(reporte, comando, basedatos, "no",0);
		}
	};
	
	
	ActionListener op_generar_permisos = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
				String fecha_inicio = new SimpleDateFormat("dd/MM/yyyy").format(c_inicio.getDate())+" 00:00:00";
				String fecha_final = new SimpleDateFormat("dd/MM/yyyy").format(c_final.getDate())+" 23:59:59";
				reporte = "Obj_Reporte_De_Permisos_A_Empleados.jrxml";
				comando = "exec sp_Reporte_De_Permisos_A_Empleados '"+fecha_inicio+"','"+fecha_final+"','"+Establecimiento+"','"+Folio+"'";
				 new Generacion_Reportes().Reporte(reporte, comando, basedatos, "no",0);
		}
	};
	
	public void cargar_fechas(){
			Date date1 = null;
					  try {
						date1 = new SimpleDateFormat("dd/MM/yyyy").parse(new BuscarSQL().dias_para_fecha_revision_consideracion());
					} catch (ParseException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					}
			c_inicio.setDate(date1);
		    Date date2 = null;
						  try {
							date2 = new SimpleDateFormat("dd/MM/yyyy").parse(new BuscarSQL().fecha(0));
						} catch (ParseException e) {
							e.printStackTrace();
						} catch (SQLException e) {
							e.printStackTrace();
						}
			c_final.setDate(date2);
		};

	@SuppressWarnings("unchecked")
	public void llamar_render(){
		this.tabla.getTableHeader().setReorderingAllowed(false) ;
		this.tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		int x = 40;
		
		this.tabla.getColumnModel().getColumn(0).setMinWidth(x);		
		this.tabla.getColumnModel().getColumn(1).setMinWidth(270);
		this.tabla.getColumnModel().getColumn(2).setMinWidth(x+20);
		this.tabla.getColumnModel().getColumn(3).setMinWidth(x);		
		this.tabla.getColumnModel().getColumn(4).setMinWidth(x+10);
		this.tabla.getColumnModel().getColumn(5).setMinWidth(x+40);
		this.tabla.getColumnModel().getColumn(6).setMinWidth(x);
		this.tabla.getColumnModel().getColumn(7).setMinWidth(x+10);
		this.tabla.getColumnModel().getColumn(8).setMinWidth(x+40);
		this.tabla.getColumnModel().getColumn(9).setMinWidth(x-20);
		this.tabla.getColumnModel().getColumn(10).setMinWidth(x+150);
		this.tabla.getColumnModel().getColumn(11).setMinWidth(x+15);
		this.tabla.getColumnModel().getColumn(12).setMinWidth(x+15);
		this.tabla.getColumnModel().getColumn(13).setMinWidth(x+220);
		this.tabla.getColumnModel().getColumn(14).setMinWidth(250);
		this.tabla.getColumnModel().getColumn(15).setMinWidth(x+20);
		this.tabla.getColumnModel().getColumn(16).setMinWidth(20);

		this.tabla.setRowSorter(trsfiltro);  
		
		TableCellRenderer render = new TableCellRenderer() { 
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, 
			boolean hasFocus, int row, int column) { 
				Component lbl = null;
				
				if(column==tabla.getColumnCount()-1){
					lbl = new JCheckBox("",Boolean.parseBoolean(value.toString()));
				}else{
					lbl = new JLabel(value == null? "": value.toString());
				}
				
				Color base = Color.white;
					if(row==0){
                      	base = Color.gray;
                      }else{
                      	if (!tabla.getValueAt(row, 0).toString().equals(tabla.getValueAt(row-1, 0).toString())) {
                      		if(base.getRGB()==Color.orange.getRGB()){
                          		base = Color.white;
                      		}else{
                      			base = Color.gray;
                      		}
                          }
                      }
                      
                      ((JComponent) lbl).setOpaque(true);
                      	lbl.setBackground(base);
				if(table.getValueAt(row,16).equals("true")){
					((JComponent)lbl).setOpaque(true); 
					lbl.setBackground(Color.red);
				}
				if(isSelected){
					((JComponent)lbl).setOpaque(true);
					lbl.setBackground(new java.awt.Color(77,135,237));
				}
				if(isSelected && table.getValueAt(row,16).equals("true")){
					((JComponent)lbl).setOpaque(true);
					lbl.setBackground(new java.awt.Color(254,4,13));
				}
			return lbl; 
			} 
		}; 

		for(int i = 0; i<tabla.getColumnCount(); i++){
			this.tabla.getColumnModel().getColumn(i).setCellRenderer(render); 
		}
	}
    
	private void agregar(final JTable tbl) {
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
	        public void mouseClicked(MouseEvent e) {
	        	if(e.getClickCount() == 2){
	    			int fila = tabla.getSelectedRow();
	    			
	    			if(tabla.getValueAt(fila, tabla.getColumnCount()-1).toString().equals("true")){
 	  				  JOptionPane.showMessageDialog(null, "Solo Esta Permitido Modificar El Registro Una Vez \n Este Registro Ya A Sido Modificado","Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
	    				return;
	    			}else{
		    					folio_emp = 	Integer.valueOf(tabla.getValueAt(fila, 0).toString().trim());
	    						 empleado = 	tabla.getValueAt(fila, 1).toString().trim();
	    						 	fecha = 	tabla.getValueAt(fila, 2).toString().trim()+" "+tabla.getValueAt(fila, 3).toString().trim();
		    					  ent_sal = 	tabla.getValueAt(fila, 6).toString().trim().equals("-")? tabla.getValueAt(fila, 5).toString().trim() : tabla.getValueAt(fila, 6).toString().trim();
					   	     tipo_checada = 	tabla.getValueAt(fila, 7).toString().trim();	    					  
		    					   	  imp = 	Integer.valueOf(tabla.getValueAt(fila, 8).toString().trim());
		    					   	  fav = 	Integer.valueOf(tabla.getValueAt(fila, 9).toString().trim());
		    				  observacion = 	tabla.getValueAt(fila, 13).toString().trim();
		    				   Status_Mov =     tabla.getValueAt(fila, 15).toString().trim();
		    			new Cat_Ventana_Consideracion().setVisible(true);
	    			}
	        	}
	        }
        });
    }

	public void refrescar(){
		String fecha_inicio = new SimpleDateFormat("dd/MM/yyyy").format(c_inicio.getDate())+" 00:00:00";
		String fecha_final = new SimpleDateFormat("dd/MM/yyyy").format(c_final.getDate())+" 23:59:59";
		modelo.setRowCount(0);
		Object[][] recargarTabla = new BuscarTablasModel().filtro_impuntualidad_a_considerar(fecha_inicio,fecha_final,Establecimiento,Departamento,Folio);
		 String[] fila = new String[17];
         for(int i=0; i<recargarTabla.length; i++){
                 fila[0] = recargarTabla[i][0]+"";
                 fila[1] = recargarTabla[i][1]+"";
                 fila[2] = recargarTabla[i][2]+"";
                 fila[3] = recargarTabla[i][3]+"";
                 fila[4] = recargarTabla[i][4]+"";
                 fila[5] = recargarTabla[i][5]+"";
                 fila[6] = recargarTabla[i][6]+"";
                 fila[7] = recargarTabla[i][7]+"";
                 fila[8] = recargarTabla[i][8]+"";
                 fila[9] = recargarTabla[i][9]+"";
                 fila[10] = recargarTabla[i][10]+"";
                 fila[11] = recargarTabla[i][11]+"";
                 fila[12] = recargarTabla[i][12]+"";
                 fila[13] = recargarTabla[i][13]+"";
                 fila[14] = recargarTabla[i][14]+"";
                 fila[15] = recargarTabla[i][15]+"";
                 fila[16] = recargarTabla[i][16]+"";
                 modelo.addRow(fila);
         }
	}
	
	ActionListener opGenerarTabla = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(c_inicio.getDate()==null || c_final.getDate()==null){
				
				JOptionPane.showMessageDialog(null, "campos nulos", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
				return;
			}else{
				if(c_final.getDate().before(c_inicio.getDate())){
					JOptionPane.showMessageDialog(null, "El rango de fechas esta invertido", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
					return;
				}else{
					refrescar();
				}
			}
		}
	};
	
	
	KeyListener opFiltroFolioCajero = new KeyListener(){
		public void keyReleased(KeyEvent arg0) {
			new Obj_Filtro_Dinamico(tabla, "Nombre", txtNombre.getText().toString().trim().toUpperCase(), "", "", "", "", "", "");
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}		
	};
	
	 ///////TODO VENTANA DE CONSIDERACION
	public class Cat_Ventana_Consideracion extends JDialog {
		Container cont = getContentPane();
		JLayeredPane panel = new JLayeredPane();
		
		JLabel lblFolio_corte = new JLabel("");
		JLabel lblCajero = new JLabel("");
		JLabel lblFecha = new JLabel("");
		JLabel lblMovimiento = new JLabel("");
		
		JTextField txtImp = new Componentes().text(new JTextField(), "Observaciones", 4, "Double");
		JTextField txtFav = new Componentes().text(new JTextField(), "Observaciones", 4, "Double");
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		JComboBox cmbTipo_checada =new JComboBox(new String[]{"No Aplica","Aplica"});
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		JComboBox cmbOmision =new JComboBox(new String[]{"No Aplica","Aplica"});
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		JComboBox cmbstatus_checada =new JComboBox(new String[]{"Vigente","Cancelado"});
		
//		String justificar[] = {"SELECCIONAR UNA OPCION","JUSTUFICO","NO JUSTUFICO"};
//		@SuppressWarnings({ "unchecked", "rawtypes" })
//		JComboBox cmbJustificar = new JComboBox(justificar);
		
		JRadioButton rbSi = new JRadioButton("SI");
		JRadioButton rbNo = new JRadioButton("NO");
		ButtonGroup GRb = new ButtonGroup();
		
		String tipo_de_falta[] = new BuscarTablasModel().tipos_de_falta();
		@SuppressWarnings({ "unchecked", "rawtypes" })
		JComboBox cmbTipoDeFalta = new JComboBox(tipo_de_falta);
		
		JTextArea txaObservacion =new Componentes().textArea(new JTextArea(), "Observaciones", 150);
		JScrollPane scrollObservacion = new JScrollPane(txaObservacion,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		JCButton btnGuardar = new JCButton("Guardar"       ,"Guardar.png"      ,"Azul");
		
		public Cat_Ventana_Consideracion(){
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setModal(true);
			this.setTitle("Consideracion checador");
			this.panel.setBorder(BorderFactory.createTitledBorder(blackline, "Guardar consideracion de checador seleccionado"));
			
			rbSi.setSelected(true);
			if(ent_sal.equals("FALT.REG.") && fecha.substring(fecha.indexOf(" ")+1, fecha.length()).equals("00:00:00")){
				cmbTipoDeFalta.setEnabled(true);
				rbSi.setEnabled(true);
				rbNo.setEnabled(true);
//				cmbJustificar.setEnabled(true);
			}else{
				cmbTipoDeFalta.setEnabled(false);
				rbSi.setEnabled(false);
				rbNo.setEnabled(false);
//				cmbJustificar.setEnabled(false);
			}
			
			GRb.add(rbSi);
			GRb.add(rbNo);

			lblFolio_corte.setText("Folio Empleado:  "+folio_emp);
			lblCajero.setText("Empleado:  "+empleado);
			lblFecha.setText("Fecha:  "+fecha);
			lblMovimiento.setText("Movimiento: "+ent_sal);
			
			txtImp.setText(imp+"");
			txtFav.setText(fav+"");
			txaObservacion.setText(observacion);
			
			if(tipo_checada.equals("-")){
				cmbTipo_checada.setEnabled(false);
				cmbTipo_checada.setSelectedItem("No Aplica");
			}else{
				cmbTipo_checada.setEnabled(true);
				cmbTipo_checada.setSelectedItem("Aplica");
			}
			
			if(ent_sal.equals("FALT.REG.")){
				cmbOmision.setEnabled(true);
				cmbOmision.setSelectedItem("Aplica");
			}else{
				cmbOmision.setEnabled(false);
				cmbOmision.setSelectedItem("No Aplica");
			}
			
			int y=20;
			
			panel.add(lblFolio_corte).setBounds(15,y,150,20);
			panel.add(lblFecha).setBounds(320,y,180,20);
			
			panel.add(lblCajero).setBounds(15,y+=25,500,20);
			panel.add(lblMovimiento).setBounds(320,y,440,20);
			
			panel.add(new JLabel("Observacion:")).setBounds(200,y+=25,70,20);
			panel.add(scrollObservacion).setBounds(200,y+15,270,100);
			panel.add(new JLabel("Impuntualidad: ")).setBounds(15,y+=15,90,20);
			panel.add(txtImp).setBounds(100,y,80,20);
			panel.add(new JLabel("A Favor: ")).setBounds(15,y+=25,90,20);
			panel.add(txtFav).setBounds(100,y,80,20);
			panel.add(new JLabel("Clave Master: ")).setBounds(15,y+=25,90,20);
			panel.add(cmbTipo_checada).setBounds(100,y,80,20);
			panel.add(new JLabel("Omision: ")).setBounds(15,y+=25,90,20);
			panel.add(cmbOmision).setBounds(100,y,80,20);
			panel.add(new JLabel("Estatus Checada: ")).setBounds(15,y+=25,90,20);
			panel.add(cmbstatus_checada).setBounds(100,y,80,20);
			panel.add(btnGuardar).setBounds(370,y+15,100,30);
			panel.add(new JLabel("Tipo De Falta: ")).setBounds(15,y+=25,90,20);
			panel.add(cmbTipoDeFalta).setBounds(100, y, 210, 20);
			
			panel.add(new JLabel("Aplica Descuento: ")).setBounds(15,y+=25,90,20);
			panel.add(rbSi).setBounds(120,y,40,20);
			panel.add(rbNo).setBounds(190,y,40,20);
			
			txaObservacion.setLineWrap(true); 
			txaObservacion.setWrapStyleWord(true);
			
			cont.add(panel);
			
			cmbTipoDeFalta.setSelectedItem("INJUSTIFICADA");
			
			btnGuardar.addActionListener(opGuardarObservacion);
			
			this.setSize(500,310);
			this.setLocationRelativeTo(null);
		}
		
		ActionListener opGuardarObservacion = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				status_funcion  =	new BuscarSQL().Op_status_consideracion().toString().trim();
				if(status_funcion.equals("F")){
					modelo.setRowCount(0);
					btnGenerar.setEnabled(false);
					c_final.setEnabled(false);
					txtNombre.setEditable(false);
					JLBStatus.setText("<html> <FONT FACE="+"arial"+" SIZE=3 COLOR=RED><CENTER><b><p>La Consideracion Esta Desactivada  Hasta La Generacion De La Nueva Lista De Raya</p></b></CENTER></FONT></html>");	 
					JOptionPane.showMessageDialog(null,"La Consideracion Esta Desactivada  Hasta La Generacion De La Nueva Lista De Raya \n Si Tiene Pendientes De Considerar Comuniquese A Recursos Humanos","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
					dispose();
				}else{
				if(!txaObservacion.getText().equals("")){
//							if(!rbNo.isEnabled()){
								actualizar();
//							}else{
//								if(cmbJustificar.getSelectedItem().equals("SELECCIONAR UNA OPCION")){
//									JOptionPane.showMessageDialog(null,"Se Requiere Seleccionar Una Opcion En El Campo Falta","Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
//									cmbJustificar.showPopup();
//									return;
//								}else{
//									actualizar();
//								}
//							}
				}else{
	  			  JOptionPane.showMessageDialog(null, "Es Necesario Teclear Una Observacion \n El Porque Se Modifico El Registro","Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
	  		        txaObservacion.requestFocus();
					return;
				}
			 }
			}	
		};
		
		public void actualizar(){
			int consid_imp = (imp - Integer.valueOf(txtImp.getText()))*-1;
			int consid_fav = (fav - Integer.valueOf(txtFav.getText()))*-1;
			
			String clave_master = "";
			if(tipo_checada.equals("-")){
					clave_master="";
			}else{
					clave_master = cmbTipo_checada.getSelectedItem().toString().trim().equals("Aplica")?"":cmbTipo_checada.getSelectedItem().toString().trim();
			}
			
			String omision_mod = "";
			if(ent_sal.equals("FALT.REG.")){
				   omision_mod="";
				   
			}else{ omision_mod = cmbOmision.getSelectedItem().toString().trim().equals("Aplica")?"":
				cmbOmision.getSelectedItem().toString().trim();
			if(cmbOmision.isEnabled()){consid_imp=imp*-1;}}
			
			String status_mod = "";
			status_mod = cmbstatus_checada.getSelectedItem().toString().trim();

			if(status_mod.equals("Cancelado")){
				consid_imp=imp*-1;
				consid_fav=fav*-1;
				omision_mod="No Aplica";
				clave_master="";    			}
			
			
			
		if(new ActualizarSQL().consideracion_para_checador(folio_emp, fecha, consid_imp, consid_fav, clave_master, txaObservacion.getText().toUpperCase().trim(),omision_mod,status_mod,rbSi.isSelected()?"JUSTUFICO":"NO JUSTUFICO",ent_sal,cmbTipoDeFalta.getSelectedItem().toString().trim())){
			
			 folio_emp    = 0; 	
			 empleado     = ""; 	
			 fecha        = ""; 	
			 ent_sal      = ""; 	
			 tipo_checada = ""; 	
			 imp          = 0; 	
			 fav          = 0; 	
			 observacion  = "";
			 Status_Mov   = "";
			 refrescar();
             dispose();
			JOptionPane.showMessageDialog(null, "Consideracion Guardada Correctamente", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/aplicara-el-dialogo-icono-6256-32.png"));
			return;
		}else{
			dispose();
  			JOptionPane.showMessageDialog(null,"No Se Pudo Guardar La Consideracion","Aviso", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-icono-eliminar5252-64.png"));
			return;
		}
		}
	}
	
	public static void main(String[] args) {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Consideraciones_De_Impuntualidad_Por_Nivel_Jerarquico().setVisible(true);
		}catch(Exception e){
			System.err.println("Error :"+ e.getMessage());
		}
	}
}
