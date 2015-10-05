package Cat_Contabilidad;

import java.awt.Color;
import java.awt.Container;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;
import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.BuscarTablasModel;
import Conexiones_SQL.Cargar_Combo;
import Conexiones_SQL.Generacion_Reportes;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Principal.Componentes;
import Obj_Principal.Obj_Filtro_Dinamico;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Auxiliares extends JFrame{
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JTextField txtFolioCuentaIn = new Componentes().text(new JTextField(), "Folio Cuenta", 16, "Int");
	JTextField txtCuentaIn = new Componentes().text(new JTextField(), "Cuenta", 500, "String");
	JTextField txtFolioCuentaFin = new Componentes().text(new JTextField(), "Folio Cuenta", 16, "Int");
	JTextField txtCuentaFin = new Componentes().text(new JTextField(), "Cuenta", 500, "String");
	
	JDateChooser fhIn 	= new JDateChooser();
	JDateChooser fhFin 	= new JDateChooser();
	
	public String[] establecimiento(){
			try {
				String[] lista = new Cargar_Combo().EstablecimientoTb();
				lista[0] = "TODOS";
				return lista;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbEstablecimiento = new JComboBox(establecimiento());
	
	JButton btnGenerar = new JButton("Buscar",new ImageIcon("imagen/buscar.png"));
	JButton btnDeshacer = new JButton("Deshacer",new ImageIcon("imagen/deshacer16.png"));
	
	JButton btnReporteAnalitico = new JButton("Analitico",new ImageIcon("imagen/Sales-report-icon16.png"));
	JButton btnReporteDetalle = new JButton("Detalle",new ImageIcon("imagen/Sales-report-icon16.png"));
	
	DefaultTableModel modelo = new DefaultTableModel(null,
            new String[]{"Cuenta", "SCuenta", "SSCuenta", "Nombre", "Referencia", "Concepto", "Poliza" , "Tipo P", "Fecha", "Cargos", "Abonos","Saldo" }
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
	        	 	case 5 : return false;
	        	 	case 6 : return false;
	        	 	case 7 : return false;
	        	 	case 8 : return false;
	        	 	case 9 : return false;
	        	 	case 10: return false;
	        	 	case 11: return false;
        	 	} 				
 			return false;
 		}
	};

	JTable tabla = new JTable(modelo);
	JScrollPane scroll = new JScrollPane(tabla,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
	Border borderline;
	public Cat_Auxiliares(){
		int anchop = Toolkit.getDefaultToolkit().getScreenSize().width;
		int altop = Toolkit.getDefaultToolkit().getScreenSize().height;
		setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds()); 
		
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Sales-report-icon64.png"));
		this.setTitle("Auxiliares");
		panel.setBorder(BorderFactory.createTitledBorder("Auxiliares"));	
		
		borderline = BorderFactory.createLineBorder(new Color(45,48,48));
		
		int x=20,y=20;
		
		panel.add(new JLabel("De:")	).setBounds(x,y,25,20);
		panel.add(txtFolioCuentaIn	).setBounds(x+=25,y,100,20);
		panel.add(txtCuentaIn		).setBounds(x+=100,y,350,20);
		
		panel.add(new JLabel("A:")	).setBounds(x+=360,y,25,20);
		panel.add(txtFolioCuentaFin	).setBounds(x+=25,y,100,20);
		panel.add(txtCuentaFin		).setBounds(x+=100,y,350,20);
		
		x=20;
		panel.add(new JLabel("De:")	).setBounds(x,y+=25,25,20);     
		panel.add(fhIn  			).setBounds(x+=25,y,100,20);
		                                                        
		panel.add(new JLabel("A:")	).setBounds(x+=110,y,25,20);
		panel.add(fhFin  			).setBounds(x+25,y,100,20); 
		
		x=20;
//		panel.add(new JLabel("Establecimiento:")).setBounds(x,y+=25,90,20);
//		panel.add(cmbEstablecimiento  			).setBounds(x+=90,y,170,20); 
		panel.add(btnGenerar  		).setBounds(x+=190,y+=25,100,20);
		panel.add(btnDeshacer  		).setBounds(x+=120,y,100,20);
		panel.add(btnReporteAnalitico  		).setBounds(x+=300,y,100,20);
		panel.add(btnReporteDetalle   		).setBounds(x+=250,y,100,20);
		
		x=20;
		panel.add(scroll			).setBounds(x,y+=25,anchop-55,altop-y-90);
		
		cont.add(panel);
		
		llamar_render();
		
		fhIn.setDate(cargar_fecha_Sugerida(0));
		fhFin.setDate(cargar_fecha_Sugerida(0));
		
		btnGenerar.addActionListener(opGenerar);
		btnDeshacer.addActionListener(opDeshacer);
		btnReporteAnalitico.addActionListener(ReporteAnalitico);

	    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
	 	       KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "nuevaCuenta");
	 	    
	 	    getRootPane().getActionMap().put("nuevaCuenta", new AbstractAction(){
	 	        @Override
	 	        public void actionPerformed(ActionEvent e)
	 	        {	      
	 	        	btnDeshacer.doClick();
	 	        }
	 	    });
	    
 	   txtCuentaIn.setEditable(false);
 	   txtCuentaFin.setEditable(false);
	 	 
		buscar(txtFolioCuentaIn,txtCuentaIn);
		buscar(txtFolioCuentaFin,txtCuentaFin);
		

	}
	
	
	public void buscar(final JTextField txtF , final JTextField txtT){
		txtF.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			@SuppressWarnings("static-access")
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode()==e.VK_F2){
					new Cat_Filtro_Cuentas(txtF , txtF.getText().trim() , txtT).setVisible(true);
				}
				if(e.getKeyCode()==e.VK_ENTER){
					if(!buscarDirecto(txtF,txtT)){
						txtCuentaFin.requestFocus();
					}
				}
				
				if(txtF==txtFolioCuentaIn){
					txtFolioCuentaFin.setText(txtF.getText());
				}
			}
			public void keyPressed(KeyEvent e) {}
		});
	}
	
	public boolean buscarDirecto(JTextField txtFiledF, JTextField txtFiledT){
		boolean error = false;
		Object[][] pol = Filtro_Cuentas_Con_Parametro(txtFiledF.getText().trim());
		
		if(pol.length>0){
			if(txtFiledF == txtFolioCuentaIn){ txtFolioCuentaFin.requestFocus(); }
			txtFiledF.setBackground(Color.WHITE);
			txtFiledT.setText(pol[0][1].toString());
			error = true;
		}else{
			txtFiledF.requestFocus();
			txtFiledF.setBackground(Color.RED);
			txtFiledF.setText("");
			if(txtFiledF == txtFolioCuentaIn){ txtCuentaIn.setText(""); } txtCuentaFin.setText("");
			error = false;
			JOptionPane.showMessageDialog(null, "No Se Encontraron Registros","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
			txtFolioCuentaFin.requestFocus();
		}
		return error;
	}
	
	public Date cargar_fecha_Sugerida(Integer dias){
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
	
	     
	public Object[][] Filtro_Cuentas_Con_Parametro(String cuenta){
		try {
			return new BuscarSQL().Filtro_De_Cuentas_Con_Parametro(cuenta);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Object[][] get_tabla(){
		return new BuscarTablasModel().configuracion_de_polizas();
	}
	public void llenarConfiguracionPolizas(){
		
		while(tabla.getRowCount()>0){
			modelo.removeRow(0);
		}
		
		Object[][] pol = get_tabla();
		for(Object[] p : pol){
			modelo.addRow(p);
		}
	}
	public String validar_fechas(){
		String error = "";
		String fechainicioNull = fhIn.getDate()+"";
		String fechafinalNull = fhFin.getDate()+"";
	    if(fechainicioNull.equals("null"))error+= "Fecha  inicio\n";
		if(fechafinalNull.equals("null"))error+= "Fecha Final\n";
		return error;
	}
	
	int fila = 0;
	ActionListener opGenerar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(txtCuentaFin.getText().equals("")||txtCuentaIn.getText().equals("")){
				JOptionPane.showMessageDialog(null, "Debe Establecer Un Rango De Cuentas","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}else{
			String fecha_inicio = new SimpleDateFormat("dd/MM/yyyy").format(fhIn.getDate())+" 00:00:01";
			String fecha_final = new SimpleDateFormat("dd/MM/yyyy").format(fhFin.getDate())+" 00:03:03";
			   if(validar_fechas().equals("")){
					  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm"); 
					  Date fecha1 = sdf.parse(fecha_inicio , new ParsePosition(0));
					  Date fecha2 = sdf.parse(fecha_final , new ParsePosition(0));
					 if(fecha1.before(fecha2)){
									try {
										Object[][] rptAux = new BuscarSQL().Reporte_Auxiliares(txtFolioCuentaIn.getText().trim(), txtFolioCuentaFin.getText().trim(), new SimpleDateFormat("dd/MM/yyyy").format(fhIn.getDate()), new SimpleDateFormat("dd/MM/yyyy").format(fhFin.getDate()));
										modelo.setRowCount(0);
										for(Object[] RA: rptAux){
											modelo.addRow(RA);
										}
								} catch (SQLException e1){
									e1.printStackTrace();
								}
					}else{
							JOptionPane.showMessageDialog(null,"El Rango de Fechas Esta Invertido","Aviso!", JOptionPane.WARNING_MESSAGE, new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
							return;
			        }
		     	}else{
				JOptionPane.showMessageDialog(null,"Los siguientes campos están vacíos: "+validar_fechas(),"Aviso!", JOptionPane.ERROR_MESSAGE, new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
				return;
			}
			}
		}
	};
	
	ActionListener opDeshacer = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			txtCuentaIn.setText("");
			txtCuentaFin.setText("");
			txtFolioCuentaIn.setText("");
			txtFolioCuentaFin.setText("");
			fhIn.setDate(cargar_fecha_Sugerida(0));
			fhFin.setDate(cargar_fecha_Sugerida(0));
            modelo.setRowCount(0);;
            txtFolioCuentaIn.requestFocus();
		}
	};
	
	ActionListener ReporteAnalitico = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String fechaini = new SimpleDateFormat("dd/MM/yyyy").format(fhIn.getDate())+" 00:00:00";
			String fechafin = new SimpleDateFormat("dd/MM/yyyy").format(fhFin.getDate())+" 00:00:00";
			
  			   Obj_Usuario usuario = new Obj_Usuario().LeerSession();
			String basedatos="2.26";
			String vista_previa_reporte="no";
			int vista_previa_de_ventana=0;
			String comando="exec sp_Reporte_De_Auxiliar_De_Cuentas '"+txtFolioCuentaIn.getText().toString().trim()+"','"+txtFolioCuentaFin.getText().toString().trim()+"','"+fechaini+"','"+fechafin+"','A','"+usuario.getNombre_completo()+"'" ;
			String reporte = "Obj_Reporte_De_Auxiliar_Analitico.jrxml";
							 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
		}
	};
	
	public void llamar_render(){
		tabla.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","centro","Arial","normal",12));
	    tabla.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","centro","Arial","normal",12)); 
	    tabla.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","centro","Arial","normal",12));
	    tabla.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
	    tabla.getColumnModel().getColumn(4).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
	    tabla.getColumnModel().getColumn(5).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
	    tabla.getColumnModel().getColumn(6).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
	    tabla.getColumnModel().getColumn(7).setCellRenderer(new tablaRenderer("texto","centro","Arial","normal",12)); 
	    tabla.getColumnModel().getColumn(8).setCellRenderer(new tablaRenderer("texto","centro","Arial","normal",12));
	    tabla.getColumnModel().getColumn(9).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12)); 
	    tabla.getColumnModel().getColumn(10).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12));
	    tabla.getColumnModel().getColumn(11).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12));
	    
	    this.tabla.getTableHeader().setReorderingAllowed(false) ;
	    this.tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		int largo=60;
		
		tabla.getColumnModel().getColumn(0).setMinWidth(60);
	    tabla.getColumnModel().getColumn(0).setMaxWidth(largo);
	    tabla.getColumnModel().getColumn(1).setMinWidth(60);
	    tabla.getColumnModel().getColumn(1).setMaxWidth(largo);
	    tabla.getColumnModel().getColumn(2).setMinWidth(60);
	    tabla.getColumnModel().getColumn(2).setMaxWidth(largo);
	    
	    tabla.getColumnModel().getColumn(3).setMinWidth(largo*7);
	    tabla.getColumnModel().getColumn(3).setMaxWidth(largo*10);
	    tabla.getColumnModel().getColumn(4).setMinWidth(largo*3);
	    tabla.getColumnModel().getColumn(4).setMaxWidth(largo*4);
	    tabla.getColumnModel().getColumn(5).setMinWidth(largo*7);
	    tabla.getColumnModel().getColumn(5).setMaxWidth(largo*10);
	    
	    tabla.getColumnModel().getColumn(6).setMinWidth(80);
	    tabla.getColumnModel().getColumn(6).setMaxWidth(100);
	    tabla.getColumnModel().getColumn(7).setMinWidth(80);
	    tabla.getColumnModel().getColumn(7).setMaxWidth(40);
	    tabla.getColumnModel().getColumn(8).setMinWidth(largo*3);
	    tabla.getColumnModel().getColumn(8).setMaxWidth(largo*6);
	    
	    tabla.getColumnModel().getColumn(9).setMinWidth(100);
	    tabla.getColumnModel().getColumn(9).setMaxWidth(largo*2);
	    tabla.getColumnModel().getColumn(10).setMinWidth(100);
	    tabla.getColumnModel().getColumn(10).setMaxWidth(largo*2);
	    tabla.getColumnModel().getColumn(11).setMinWidth(100);
	    tabla.getColumnModel().getColumn(11).setMaxWidth(largo*2);
		
	}
	
	public class Cat_Filtro_Cuentas extends JDialog{

		Container cont = getContentPane();
		JLayeredPane campo = new JLayeredPane();
		
		public Object[][] Filtro_Cuentas(){
				try {
					return new BuscarSQL().Filtro_De_Cuentas();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return null;
		}
		
		DefaultTableModel modelo_Filtro = new DefaultTableModel(Filtro_Cuentas(),
	            new String[]{"Codigo", "Nombre"}
				){
		     @SuppressWarnings("rawtypes")
			Class[] types = new Class[]{
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
	        	 	} 				
	 			return false;
	 		}
		};
		JTable tabla_Filtro = new JTable(modelo_Filtro);
	    JScrollPane scroll_Filtro = new JScrollPane(tabla_Filtro,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		JTextField txtCodigo = new JTextField();
		JTextField txtDescripcion = new JTextField();
		
		public Cat_Filtro_Cuentas(final JTextField ftxt, String codigo, final JTextField txt){
			
			this.setModal(true);
			
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/filter_icon&16.png"));
			this.setTitle("Filtro De Cuentas");
			campo.setBorder(BorderFactory.createTitledBorder("Filtro De Cuentas"));
			
			campo.add(scroll_Filtro).setBounds(15,42,500,565);
			
			campo.add(txtCodigo).setBounds(13,20,100,20);
			campo.add(txtDescripcion).setBounds(112,20,380,20);
			
			render();
			agregar(tabla_Filtro,ftxt,txt);
			
			cont.add(campo);
			
			txtCodigo.setText(codigo);
			new Obj_Filtro_Dinamico(tabla_Filtro,"Codigo", txtCodigo.getText().toUpperCase(),"Nombre",txtDescripcion.getText(), "", "", "", "");
			
			txtCodigo.addKeyListener(opFiltroLoco);
			txtDescripcion.addKeyListener(opFiltroLoco);
			
			this.setSize(540,660);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			
//          asigna el foco al JTextField del nombre deseado al arrancar la ventana
            this.addWindowListener(new WindowAdapter() {
                    public void windowOpened( WindowEvent e ){
                    	txtDescripcion.requestFocus();
                 }
            });
              
//            pone el foco en la tabla al presionar f4
              getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                 KeyStroke.getKeyStroke(KeyEvent.VK_F4 , 0), "dtabla");
              
              getRootPane().getActionMap().put("dtabla", new AbstractAction(){
                  @Override
                  public void actionPerformed(ActionEvent e)
                  {
                	tabla_Filtro.requestFocus();
                  }
              });
              
		}
		
		private void agregar(final JTable tbl, final JTextField jtxtF, final JTextField jtxtT) {
	        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
		        public void mouseClicked(MouseEvent e) {
		        	
		        	if(e.getClickCount() == 2){
		    			fila = tabla_Filtro.getSelectedRow();
		    			dispose();
		    			jtxtF.setBackground(Color.WHITE);
		    			jtxtF.setText(tabla_Filtro.getValueAt(fila, 0).toString().trim());
		    			jtxtT.setText(tabla_Filtro.getValueAt(fila, 1).toString().trim());
		    			txtFolioCuentaFin.requestFocus();
		    			txtCuentaFin.setText("");
		        	}
		        }
	        });
	    }
		
		KeyListener opFiltroLoco = new KeyListener(){
			public void keyReleased(KeyEvent arg0) {
				
				new Obj_Filtro_Dinamico(tabla_Filtro,"Codigo", txtCodigo.getText().toUpperCase(),"Nombre",txtDescripcion.getText(), "", "", "", "");
			}
			public void keyTyped(KeyEvent arg0) {}
			public void keyPressed(KeyEvent arg0) {}		
		};
		
	   	private void render(){		
			
			DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
			tcr.setHorizontalAlignment(SwingConstants.CENTER);
			
			tabla_Filtro.getTableHeader().setReorderingAllowed(false) ;
			
    		tabla_Filtro.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
		    tabla_Filtro.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
			
			tabla_Filtro.getColumnModel().getColumn(0).setMinWidth(0);
		    tabla_Filtro.getColumnModel().getColumn(0).setMaxWidth(100);
			tabla_Filtro.getColumnModel().getColumn(1).setMinWidth(300);
			tabla_Filtro.getColumnModel().getColumn(1).setMaxWidth(400);
			
		}
	}
	
	public static void main(String[] args) {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Auxiliares().setVisible(true);
		}catch(Exception e){	}		
	}
}
