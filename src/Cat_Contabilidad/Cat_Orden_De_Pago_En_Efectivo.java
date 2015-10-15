package Cat_Contabilidad;

import java.awt.AWTException;
import java.awt.Container;
import java.awt.Event;
import java.awt.Robot;
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
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
import Conexiones_SQL.GuardarSQL;
import Obj_Principal.Componentes;
import Obj_Principal.Obj_Filtro_Dinamico;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Orden_De_Pago_En_Efectivo extends JFrame{

	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JTextField txtFolio= new Componentes().text(new JTextField(), "Folio", 15, "Int");
	JTextField txtCantidad= new Componentes().text(new JTextField(), "Cantidad", 20, "Double");
	
	JTextField txtBeneficiario= new Componentes().text(new JTextField(), "Beneficiario", 250, "String");
	
	JDateChooser fhFecha 	= new JDateChooser();
	
	JTextArea txaConcepto = new Componentes().textArea(new JTextArea(), "Concepto", 135);
	JScrollPane Concepto = new JScrollPane(txaConcepto);
	
	public String[] autorizados(){
		try {
			return new Cargar_Combo().autorizados_para_pago_efectivo();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; 
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbAutorizados = new JComboBox(autorizados());
	
	JLabel lblPersona 	= new JLabel("");
	JRadioButton rbProveedor 			= new JRadioButton("Proveedor");
	JRadioButton rbEmpleado 		= new JRadioButton("Empleado");
	ButtonGroup grupo = new ButtonGroup();
	
	JButton btnBuscar = new JButton(new ImageIcon("imagen/Filter-List-icon16.png"));
	JButton btnGuardar = new JButton("Guardar",new ImageIcon("imagen/Guardar.png"));
	JButton btnDeshacer = new JButton("Deshacer",new ImageIcon("imagen/deshacer16.png"));
	JButton btnImprimir = new JButton("Imprimir",new ImageIcon("imagen/Print.png"));
	
	JButton btnAltaproveedor = new JButton("Nuevo Proveedor",new ImageIcon("imagen/tarjeta-de-informacion-del-usuario-icono-7370-16.png"));
	Border borderline;
	
	int folioBeneficiario = 0;
	int folio_impesion = 0;
	public Cat_Orden_De_Pago_En_Efectivo(){
		this.setSize(430, 310);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Pay-Per-Click-icon64px.png"));
		this.setTitle("Orden De Pago En Efectivo");
		panel.setBorder(BorderFactory.createTitledBorder("Llena o Selecciona Los Datos Correspondientes"));
		lblPersona.setBorder(BorderFactory.createTitledBorder(borderline,"Beneficiario"));
		grupo.add(rbProveedor);
		grupo.add(rbEmpleado);
		rbProveedor.setSelected(true);
		
		int y=20;
		panel.add(new JLabel("Folio:")).setBounds(15,y,50,20);
		panel.add(txtFolio).setBounds(85,y,90,20);
		panel.add(new JLabel("Fecha:")).setBounds(260,y,70,20);
		panel.add(fhFecha  ).setBounds(300,y,100,20);
		panel.add(new JLabel("Cantidad:  $")).setBounds(15,y+=25,70,20);
		panel.add(txtCantidad).setBounds(85,y,90,20);
		panel.add(new JLabel("Concepto:")).setBounds(15,y+=25,70,20);
		panel.add(Concepto  ).setBounds(15,y+=20,385,50);
		panel.add(new JLabel("Autorizo: ")).setBounds(15,y+=60,70,20);
		panel.add(cmbAutorizados).setBounds(85,y,250,20);
		panel.add(lblPersona).setBounds(15,y+=30,385,60);
		panel.add(rbProveedor).setBounds(25,y+=15,90,20);
		panel.add(btnBuscar).setBounds(115,y,30,20);
		panel.add(btnAltaproveedor).setBounds(258,y,137,20);
		panel.add(rbEmpleado).setBounds(25,y+=20,90,20);
		panel.add(txtBeneficiario).setBounds(115,y,280,20);
		panel.add(btnImprimir).setBounds(15,y+=30,100,20);
		panel.add(btnDeshacer).setBounds(155,y,100,20);
		panel.add(btnGuardar).setBounds(295,y,100,20);
		
		cont.add(panel);
		
		txaConcepto.setLineWrap(true); 
		txaConcepto.setWrapStyleWord(true);
		
		txtFolio.setEditable(false);
		txtBeneficiario.setEditable(false);
		txtFolio.setHorizontalAlignment(4);
		txtFolio.setText(new BuscarTablasModel().folio_ordern_de_pago_en_efectivo());
		fhFecha.setDate(cargar_fecha_Sugerida(0));
		
		rbProveedor.addActionListener(opRButton);
		rbEmpleado.addActionListener(opRButton);
		btnBuscar.addActionListener(opFiltro);
		btnGuardar.addActionListener(opGuardar);
		btnDeshacer.addActionListener(deshacer);
		btnImprimir.addActionListener(opimprimir);
		btnAltaproveedor.addActionListener(opproveedor);
		txtCantidad.addKeyListener(enterpasarafecha);
		txaConcepto.addKeyListener(enterpasaratexareaConcepto);	
		///guardar con control+G
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_G,Event.CTRL_MASK),"guardar");
             getRootPane().getActionMap().put("guardar", new AbstractAction(){
                 public void actionPerformed(ActionEvent e)
                 {                 	    btnGuardar.doClick();
               	    }
            });
             
        ///guardar con control+P
             getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_P,Event.CTRL_MASK),"proveedor");
                  getRootPane().getActionMap().put("proveedor", new AbstractAction(){
                      public void actionPerformed(ActionEvent e)
                      {                 	    btnAltaproveedor.doClick();
                    	    }
                 });
        ///guardar con F12
             getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0), "guardar");
                 getRootPane().getActionMap().put("guardar", new AbstractAction(){
                     public void actionPerformed(ActionEvent e)
                     {                 	    btnGuardar.doClick();
	                    	    }
                });
        ///buscar Referencia f2
                 getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), "referencia");
 	                  getRootPane().getActionMap().put("referencia", new AbstractAction(){
 	                      public void actionPerformed(ActionEvent e)
 	                      {                 	    btnBuscar.doClick();
 		                    	    }
 	                 });
 	     //deshacer con escape
 	                 getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
 	                   getRootPane().getActionMap().put("escape", new AbstractAction(){
 	                  public void actionPerformed(ActionEvent e)
 	                  {                 	    btnDeshacer.doClick();
 	                	    }
 	              });
                 
		 this.addWindowListener(new WindowAdapter() {
             public void windowOpened( WindowEvent e ){
            	 txtCantidad.requestFocus();
          }
     });
		 
	}
	
	ActionListener deshacer = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			txtFolio.setText("");
			txtCantidad.setText("");
			txtBeneficiario.setText("");
			txaConcepto.setText("");
			rbProveedor.setSelected(true);
			btnAltaproveedor.setSelected(true);
			txtFolio.setText(new BuscarTablasModel().folio_ordern_de_pago_en_efectivo());
			fhFecha.setDate(cargar_fecha_Sugerida(0));
			cmbAutorizados.setSelectedIndex(0);
		 }
		};
		
	 ActionListener opRButton = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			folioBeneficiario = 0;
			txtBeneficiario.setText("");
			if(rbEmpleado.isSelected()){
				btnAltaproveedor.setEnabled(false);
			}else{
				btnAltaproveedor.setEnabled(true);
			}
		}
	};
	
	 ActionListener opFiltro = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			try {
				new  Cat_Filtro_Referencia().setVisible(true);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	};
	
	 ActionListener opproveedor = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			new  Cat_Proveedores().setVisible(true);
		}
	};
	
	 ActionListener opimprimir = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			folio_impesion=Integer.valueOf(txtFolio.getText())-1;
			imprimir_ultimo_guardado();
		}
	};
	
	
	
	ActionListener opGuardar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			if(!validaCampos().equals("")){
				JOptionPane.showMessageDialog(null, "Los Siguiente Campos Son Requeridos:\n"+validaCampos(),"Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
				return;
			}else{
				   fhFecha.setDate(cargar_fecha_Sugerida(0));
				
					if(new GuardarSQL().Guardar_Ordern_De_Pago_En_Efectivo(Float.valueOf(txtCantidad.getText().toString().trim()), new SimpleDateFormat("dd/MM/yyyy").format(fhFecha.getDate()),txaConcepto.getText().toUpperCase().trim(),cmbAutorizados.getSelectedItem().toString(),rbProveedor.isSelected()?"P":"E",folioBeneficiario)){
						folio_impesion=Integer.valueOf(txtFolio.getText());
						imprimir_ultimo_guardado();
						btnDeshacer.doClick();
					}else{
						JOptionPane.showMessageDialog(null, "Ocurrió Un Error Al Intentar Guardar","Error",JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen//usuario-icono-eliminar5252-64.png"));
						return;
					}
			}
			
		}
	};

	
	KeyListener enterpasarafecha = new KeyListener() {
		public void keyTyped(KeyEvent e){}
		public void keyReleased(KeyEvent e) {}
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				txaConcepto.requestFocus();
			}
		}
	};
	
	KeyListener enterpasaratexareaConcepto = new KeyListener() {
		public void keyTyped(KeyEvent e){}
		public void keyReleased(KeyEvent e) {}
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				cmbAutorizados.requestFocus();
			}
		}
	};
	
	private String validaCampos(){
		String error="";
		String fecha = "";
		fecha = fhFecha.getDate()+"";
		if(txtCantidad.getText().equals(""))error+= "Cantidad\n";
		if(fecha.equals("null"))error+= "Fecha\n";
		if(txaConcepto.getText().equals(""))error+= "Concepto\n";
		if(txtBeneficiario.getText().equals(""))error+= "Seleccione Un Beneficiario\n";
		return error;
	}
	
	public void imprimir_ultimo_guardado(){
		String basedatos="2.26";
		String vista_previa_reporte="no";
		int vista_previa_de_ventana=0;
		String comando="exec sp_select_orden_de_pago_en_efectivo "+folio_impesion ;
		String reporte = "Obj_Reporte_De_Orden_De_Pago_En_Efectivo.jrxml";
		new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
	} ;
	
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
	
///////////////////////////////////////////////////////////////////////////////////////Filtro de Referencia ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	public class Cat_Filtro_Referencia extends JDialog{
		Container cont = getContentPane();
		JLayeredPane campo = new JLayeredPane();
		
		DefaultTableModel modelo_Filtro_Ref = new DefaultTableModel(null,
	            new String[]{"Folio", "Nombre"}
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
		JTable tabla_Filtro_Ref = new JTable(modelo_Filtro_Ref);
	    JScrollPane scroll_Filtro_Ref = new JScrollPane(tabla_Filtro_Ref);
		
		JTextField txtCodigo = new JTextField();
		JTextField txtDescripcion = new JTextField();
		
		public Cat_Filtro_Referencia() throws SQLException{
			
			this.setModal(true);
			
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/filter_icon&16.png"));
			this.setTitle("Filtro De Referencias");
			campo.setBorder(BorderFactory.createTitledBorder("Filtro De Referencias"));
			
			campo.add(scroll_Filtro_Ref).setBounds(15,42,470,565);
			
			campo.add(txtCodigo).setBounds(15,20,90,20);
			campo.add(txtDescripcion).setBounds(104,20,360,20);
			
			llenarFiltro();
			render();
			agregar(tabla_Filtro_Ref);
			
			cont.add(campo);
			
			new Obj_Filtro_Dinamico(tabla_Filtro_Ref,"Codigo", txtCodigo.getText().toUpperCase(),"Nombre",txtDescripcion.getText(), "", "", "", "");
			
			txtCodigo.addKeyListener(opFiltroLoco);
			txtDescripcion.addKeyListener(opFiltroLoco);
			
			this.setSize(510,650);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			
//          asigna el foco al JTextField del nombre deseado al arrancar la ventana
            this.addWindowListener(new WindowAdapter() {
                    public void windowOpened( WindowEvent e ){
                    	txtDescripcion.requestFocus();
                 }
            });
              
		}
		
		public void llenarFiltro() throws SQLException{
			
			modelo_Filtro_Ref.setRowCount(0);
			
			Object[][] datos = new BuscarSQL().Filtro_De_Referencia_Polizas(rbProveedor.isSelected()?"Proveedor":"Empleado");
			for(Object[] d : datos){
				modelo_Filtro_Ref.addRow(d);
			}
		}
		
		public Object[][] Filtro_Cuentas( ){
			try {
				return new BuscarSQL().Filtro_De_Cuentas_polizas();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
	}
		
		private void agregar(final JTable tbl) {
	        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
		        public void mouseClicked(MouseEvent e) {
		        	
		        	if(e.getClickCount() == 2){
		    			fila = tabla_Filtro_Ref.getSelectedRow();
		    			int folio =  Integer.valueOf(tabla_Filtro_Ref.getValueAt(fila, 0).toString().trim());
		    			folioBeneficiario= folio;
		    			txtBeneficiario.setText(tabla_Filtro_Ref.getValueAt(fila, 1).toString());
		    			dispose();
		        	}
		        }
	        });
	    }
		
		int fila=0;
		public void iniciarSeleccionConTeclado(){
			Robot robot;
			try {
	            robot = new Robot();
	            robot.keyPress(KeyEvent.VK_A);
	            robot.keyRelease(KeyEvent.VK_A);
	        } catch (AWTException e) {
	            e.printStackTrace();
	        }
 	     };
 	     
		KeyListener opFiltroLoco = new KeyListener(){
			public void keyReleased(KeyEvent arg0) {
				
				new Obj_Filtro_Dinamico(tabla_Filtro_Ref,"Folio", txtCodigo.getText().toUpperCase(),"Nombre",txtDescripcion.getText(), "", "", "", "");
			}
			public void keyTyped(KeyEvent arg0) {}
			public void keyPressed(KeyEvent arg0) {}		
		};
		
	   	private void render(){		
			
			DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
			tcr.setHorizontalAlignment(SwingConstants.CENTER);
			
			tabla_Filtro_Ref.getTableHeader().setReorderingAllowed(false) ;
			
    		tabla_Filtro_Ref.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
		    tabla_Filtro_Ref.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
			
			tabla_Filtro_Ref.getColumnModel().getColumn(0).setMinWidth(0);
		    tabla_Filtro_Ref.getColumnModel().getColumn(0).setMaxWidth(100);
			tabla_Filtro_Ref.getColumnModel().getColumn(1).setMinWidth(300);
			tabla_Filtro_Ref.getColumnModel().getColumn(1).setMaxWidth(400);
		}
	}
	
	public static void main(String[] args) {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Orden_De_Pago_En_Efectivo().setVisible(true);
		}catch(Exception e){	}	
	}
}
