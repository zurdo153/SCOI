package Cat_Contabilidad;
//txtCheque.setText(new BuscarTablasModel().folio_consecutivo_cheque_de_poliza());
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.FocusManager;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.toedter.calendar.JDateChooser;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.BuscarTablasModel;
import Conexiones_SQL.Cargar_Combo;
import Conexiones_SQL.Generacion_Reportes;
import Conexiones_SQL.GuardarSQL;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Principal.Componentes;
import Obj_Principal.Obj_Filtro_Dinamico;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Polizas extends JFrame{
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	public String[] tipo(){
		try {
			return new Cargar_Combo().tipos_de_polizas();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; 
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbTipo = new JComboBox(tipo());
	
	public String[] cuentaBanco(){
		try {
			return new Cargar_Combo().tipos_de_banco_polizas();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; 
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbDepositoBanco = new JComboBox(cuentaBanco());
	
	JTextField txtFolio = new Componentes().text(new JTextField(), "Tipo", 10, "String");
	JTextField txtCuenta = new Componentes().text(new JTextField(), "Cuenta", 16, "Int");
	JDateChooser fhFecha 	= new JDateChooser();
	
	JButton btnNota = new JButton("Nota", new ImageIcon("imagen/nota16.png"));
	JButton btnReferencia = new JButton("Referencia", new ImageIcon("imagen/tarjeta-de-informacion-del-usuario-icono-7370-16.png"));
	JButton btnGuardarPoliza = new JButton("Guardar",new ImageIcon("imagen/Guardar.png"));
	JButton btnQuitar = new JButton("Quitar",new ImageIcon("imagen/eliminar-bala-icono-7773-32.png"));
	JButton btnImprimir = new JButton("Imprimir",new ImageIcon("imagen/Print.png"));
	JButton btnDeshacer = new JButton("Deshacer",new ImageIcon("imagen/deshacer16.png"));
	
	JButton btnFiltroPoliza = new JButton("Modificar Poliza", new ImageIcon("imagen/tarjeta-de-informacion-del-usuario-icono-7370-16.png"));
	
	
	String[] formaDePago = {"Forma De Pago","Cheque","Cheque Banco Interno","Transpaso","Vale"};
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbFormaDePago = new JComboBox(formaDePago);
	
	JLabel lblTotales 	= new JLabel("");
	JLabel lblPagos 	= new JLabel("");
	JLabel lblImprime 	= new JLabel("");
	
	JTextField txtReferencia= new Componentes().text(new JTextField(), "Referencia", 150, "String");
	JTextField txtTotal= new Componentes().text(new JTextField(), "Referencia", 20, "Double");
	
	public String[] referencia(){try {return new Cargar_Combo().cuentas();} catch (SQLException e) {e.printStackTrace();}return null;}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbReferencia = new JComboBox(referencia());
	
	public String[] establecimiento(){try {return new Cargar_Combo().EstablecimientoTb();} catch (SQLException e) {e.printStackTrace();}return null;}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbEstablecimiento = new JComboBox(establecimiento());
	
    SpinnerModel cargoTotales = new SpinnerNumberModel(0, 0, 50000, .1);
	JSpinner spCargoTotales = new JSpinner(cargoTotales);
	SpinnerModel abonoTotales = new SpinnerNumberModel(0, 0, 50000, .1);
	JSpinner spAbonoTotales = new JSpinner(abonoTotales);
	SpinnerModel diferenciaTotales = new SpinnerNumberModel(0, 0, 50000, .1);
	JSpinner spDiferenciaTotales = new JSpinner(diferenciaTotales);
	
	JTextField txtCheque = new Componentes().text(new JTextField(), "Cheque", 15, "String");
	JCheckBox chbPago = new JCheckBox();
	
	JTextArea txaConcepto = new Componentes().textArea(new JTextArea(), "Observaciones", 980);
	JScrollPane Concepto = new JScrollPane(txaConcepto);
	
	JRadioButton rbPagoPrv 			= new JRadioButton("Pago a proveedores");
	JRadioButton rbAnticipoPrv 		= new JRadioButton("Anticipo a proveedores");
	ButtonGroup grupo = new ButtonGroup();
	
	JRadioButton rbPoliza 		= new JRadioButton("Poliza");
	JRadioButton rbCheque 			= new JRadioButton("Cheque");
	JRadioButton rbAnticipo 	= new JRadioButton("Anticipo");
	ButtonGroup grupoImprimir	= new ButtonGroup();
	
	DefaultTableModel modelo = new DefaultTableModel(null,
            new String[]{"Cuenta", "SCuenta", "SSCuenta","Nombre","Cargo","Abono","Concepto", "Establecimiento", "Fila"}
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
//        	 	case 4 : if(Float.valueOf(tabla.getValueAt(fila, 5).toString().equals("")?"0":tabla.getValueAt(fila, 5).toString())>0){return false;}else{return true;}
//        	 	case 5 : if(Float.valueOf(tabla.getValueAt(fila, 4).toString().equals("")?"0":tabla.getValueAt(fila, 4).toString())>0){return false;}else{return true;}
        	 	case 4 : return true;
        	 	case 5 : return true;
        	 	case 6 : return true;
        	 	case 7 : return new BuscarSQL().activar_establecimiento_en_mpolizas(tabla.getValueAt(fila, 0).toString());
        	 	case 8 : return false;
        	 	} 				
 			return false;
 		}
	};

	JTable tabla = new JTable(modelo);
	JScrollPane scroll = new JScrollPane(tabla,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
	public TableColumn columnaChb = tabla.getColumnModel().getColumn(7);
	
	String nota = "";
	int folioReferencia = 0;
	String folio_poliza_a_modificar="";
	String tipo_poliza_a_modificar="";
	String fecha_a_modificar="";
	
	String titulo = "Generar Poliza";
	
	Date fechaRef = null;
	
	Border borderline;
	
	public Cat_Polizas(){
		Contructor();
	}
	
	public Cat_Polizas(String nta){
		nota=nta;
		Contructor();
	}
	
	public void Contructor(){
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/cajas-de-cajas-de-embalaje-de-envio-de-un-archivo-tar-icono-4009-64.png"));
		this.setTitle("polizas");
		panel.setBorder(BorderFactory.createTitledBorder(titulo));	
		
		grupo.add(rbPagoPrv);
		grupo.add(rbAnticipoPrv);
		
		grupoImprimir.add(rbAnticipo );
		grupoImprimir.add(rbPoliza );
		grupoImprimir.add(rbCheque 	);
		
		borderline = BorderFactory.createLineBorder(new Color(45,48,48));
		lblTotales.setBorder(BorderFactory.createTitledBorder(borderline,"Totales"));
		lblPagos.setBorder(BorderFactory.createTitledBorder(borderline,"Pagos"));
		lblImprime.setBorder(BorderFactory.createTitledBorder(borderline,"Imprimir"));
		
		this.columnaChb.setCellEditor(new javax.swing.DefaultCellEditor(cmbEstablecimiento));
		
		int x=20,y=20,ancho=80;
		
		panel.add(new JLabel("Tipo:")).setBounds(x+5,y,50,20);
		panel.add(cmbTipo).setBounds(x+35,y,120,20);
		panel.add(new JLabel("Fecha:")).setBounds(x*11-10,y,70,20);
		panel.add(fhFecha  ).setBounds(x*11+40,y,90,20);
		panel.add(new JLabel("Folio Poliza:")).setBounds(x*20-10,y,70,20);
		panel.add(txtFolio  ).setBounds(x*23,y,80,20);
		panel.add(btnNota  ).setBounds(x*28,y,80,22);
		
        panel.add(lblTotales).setBounds(x-5,y+=25,180,95);            
        panel.add(new JLabel("Cargo:")).setBounds(x+5,y+=15,50,20);  
        panel.add(spCargoTotales).setBounds(x+70,y,90,20);   
        
        panel.add(lblPagos).setBounds(x*10,y-15,460,130); 
        panel.add(new JLabel("Pago: ")).setBounds(x*11+5,y,80,20);	
      	panel.add(chbPago).setBounds(x*13,y,20,20);
      	panel.add(cmbFormaDePago).setBounds(x*15,y,140,20);
      	panel.add(new JLabel("C.Bancaria: ")).setBounds(x*23,y,70,20);
      	panel.add(cmbDepositoBanco).setBounds(x*26+5,y,125,20);
    	            
      	panel.add(new JLabel("Abono:")).setBounds(x+5,y+=25,50,20);   
      	panel.add(spAbonoTotales).setBounds(x+70,y,90,20);
      	
        panel.add(rbPagoPrv).setBounds(x*11-15,y+5,130,20);																										
      	panel.add(rbAnticipoPrv).setBounds(x*17,y+5,136,20);
      	panel.add(new JLabel("Folio Cheque: ")).setBounds(x*24+10,y+5,80,20);
      	panel.add(txtCheque).setBounds(x*28+10,y+5,80,20);
      	
      	panel.add(new JLabel("Diferencia:")).setBounds(x+5,y+=25,70,20); 
    	panel.add(spDiferenciaTotales).setBounds(x+70,y,90,20);    
    	
    	panel.add(new JLabel("Referencia: ")).setBounds(x*11-10,y+10,80,20);																										
      	panel.add(cmbReferencia).setBounds(x*11+50,y+10,135,20); 
      	panel.add(btnReferencia).setBounds(x*20+10,y+10,105,20);
      	
      	panel.add(btnFiltroPoliza).setBounds(x*26+5,y+10,125,20);
      	
      	panel.add(txtReferencia).setBounds(x*10+5,y+=35,308,20);
    	panel.add(new JLabel("Total:")).setBounds(x*26+5,y,70,20);
		panel.add(txtTotal).setBounds(x*28,y,90,20);
		
		panel.add(new JLabel("Concepto:")).setBounds(x-5,y+=20,70,20);
		panel.add(Concepto).setBounds(x-5,y+=15,ancho*3+40,80);
		
		panel.add(lblImprime).setBounds(x*15,y,360,35);
		panel.add(rbPoliza).setBounds(x*15+5,y+=10,67,20);																										
      	panel.add(rbCheque).setBounds(x*18+10,y,75,20); 
      	panel.add(rbAnticipo).setBounds(x*22+5,y,80,20);
		panel.add(btnImprimir).setBounds(x+525,y,100,20);
		
		panel.add(btnQuitar  ).setBounds(x*15+10,y+=40,100,20);
		panel.add(btnGuardarPoliza  ).setBounds(x*19+47,y,100,20);
		panel.add(btnDeshacer).setBounds(x+525,y,100,20);
		
		panel.add(new JLabel("Cuenta:")).setBounds(x-5,y+=35,ancho,20);
		panel.add(txtCuenta).setBounds(x+ancho-40,y,ancho+20,20);
		
		panel.add(scroll).setBounds(x-5,y+=25,ancho*12+20,260);
		
		cont.add(panel);
		
		txaConcepto.setLineWrap(true); 
		txaConcepto.setWrapStyleWord(true);
		
		llamar_render();
		componentes(false);
		rbPoliza.setSelected(true);
		txtReferencia.setEditable(false);
		btnReferencia.setEnabled(false);
		txtCheque.setEditable(false);
		
		fechaRef = cargar_fecha_Sugerida(0);
		fhFecha.setDate(cargar_fecha_Sugerida(0));
		buscarFolioPolizaConsecutivo();
		
		txtCheque.setText(new BuscarTablasModel().folio_consecutivo_cheque_de_poliza(cmbDepositoBanco.getSelectedItem().toString()));
		
		cmbReferencia.addActionListener(tipoReferencia);
		cmbTipo.addActionListener(opBuscar);
		fhFecha.addPropertyChangeListener(opBusqueda);
		
		btnGuardarPoliza.addActionListener(opGuardarPoliza);
		btnQuitar.addActionListener(opQuitar);
		btnDeshacer.addActionListener(opDeshacer);
		
		btnNota.addActionListener(opNota);
		chbPago.addActionListener(opPago);
		btnReferencia.addActionListener(opFiltroRef);
		btnFiltroPoliza.addActionListener(opFiltroPolizas);
		btnImprimir.addActionListener(opImprimir);
		
		rbPagoPrv.addActionListener(opValidarImpresion);
		rbAnticipoPrv.addActionListener(opValidarImpresion);
		
		cmbDepositoBanco.addActionListener(opCuentaBanco);
		
		agregar(tabla);
		tabla.addKeyListener(op_key);

		//  abre el filtro de busqueda de empleado al presionar la tecla f2
	    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
	       KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), "foco");
	    
	    getRootPane().getActionMap().put("foco", new AbstractAction(){
	        @Override
	        public void actionPerformed(ActionEvent e)
	        {	        	  
	    		if(FocusManager.getCurrentManager().getFocusOwner().getClass().getSimpleName().equals("JTextField")){
	    			new Cat_Filtro_Cuentas(txtCuenta.getText().trim()).setVisible(true);
	    		}
	        }
	    });
	    
	    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
	 	       KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "nuevaCuenta");
	 	    
	 	    getRootPane().getActionMap().put("nuevaCuenta", new AbstractAction(){
	 	        @Override
	 	        public void actionPerformed(ActionEvent e)
	 	        {	      
	 	        	txtCuenta.setText("");
	 	        	txtCuenta.requestFocus();
	 	        	
	 	        }
	 	    });
	    
		//  BBUSCAR CUENTA CON PARAMETRO Y AGREGAR A LA TABLA
	    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
	       KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "BUSCA");
	    
	    getRootPane().getActionMap().put("BUSCA", new AbstractAction(){
	        @Override
	        public void actionPerformed(ActionEvent e)
	        {	        	  
	    		if(FocusManager.getCurrentManager().getFocusOwner().getClass().getSimpleName().equals("JTextField")){
	    			
	    			Object[][] pol = Filtro_Cuentas(txtCuenta.getText().trim());
	    			if(pol.length>0){
	    				
	    				for(Object[] p : pol){
	    					p[6]=txaConcepto.getText().toUpperCase().trim();
	    					modelo.addRow(p);
	    				}
	    				CalcularFoliosTabla();
	    				
	    				fila = tabla.getRowCount()-1;
	    				columna = 4;
	    				
	    				tabla.getSelectionModel().setSelectionInterval(fila, fila);
	    				tabla.editCellAt(fila, columna);
	    				Component aComp=tabla.getEditorComponent();
	    				aComp.requestFocus();
	    				
	    			}else{
	    				JOptionPane.showMessageDialog(null, "No Se Encontraron Registros","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
	    				return;
	    			}
	    		}
	        }
	    });
		
		this.setSize(1024, 600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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
	
	public void buscarAutomatico(){
		Robot robot;
		try {
            robot = new Robot();
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
        } catch (AWTException e) {
            e.printStackTrace();
        }
	 };
	     
	public Object[][] Filtro_Cuentas(String cuenta){
		try {
			return new BuscarSQL().Filtro_De_Cuentas_polizas_con_parametro(cuenta);
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
	int fila = 0;
	int columna = 0;
	private void agregar(final JTable tbl) {
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
	        public void mouseClicked(MouseEvent e) {
	        	if(e.getClickCount() == 1){
	        		
//	        		45
	        		
	        		fila= tbl.getSelectedRow();
	        		columna= tbl.getSelectedColumn()<=4?4:tbl.getSelectedColumn();
	        		
//	        		if(columna==4){
//	        			if(Float.valueOf(tbl.getValueAt(fila, 5).toString().equals("")?"0":tbl.getValueAt(fila, 5).toString())==0){
	        				
	        				tbl.editCellAt(fila, columna);
							Component aComp=tbl.getEditorComponent();
							aComp.requestFocus();
	        		
//		        		}
//	        		}
//	        		
//	        		if(columna==5){
//	        			if(Float.valueOf(tbl.getValueAt(fila, 4).toString().equals("")?"0":tbl.getValueAt(fila, 4).toString())==0){
//	        				
//	        				tbl.editCellAt(fila, columna);
//							Component aComp=tbl.getEditorComponent();
//							aComp.requestFocus();
//	        		
//		        		}
//	        		}
	        	}
	        }
        });
    }
	
	ActionListener opCuentaBanco = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			
			if(folio_poliza_a_modificar.equals("")/*cmbTipo.getSelectedItem().equals("EGRESOS")*/){
				txtCheque.setEditable(true);
			}else{
				txtCheque.setEditable(false);
			}
			
			txtCheque.requestFocus();
			txtCheque.setText(new BuscarTablasModel().folio_consecutivo_cheque_de_poliza(cmbDepositoBanco.getSelectedItem().toString()));
		}
	};
	
	ActionListener opQuitar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			int seleccion = tabla.getSelectedRow();
			
			if(seleccion<0){
				JOptionPane.showMessageDialog(null, "Debe seleccionar la fila que desea quitar","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
				return;
			}else{
				if(seleccion < tabla.getRowCount()){
					modelo.removeRow(seleccion);
					tabla.getSelectionModel().setSelectionInterval(seleccion, seleccion);
					CalcularFoliosTabla();
				}
			}
		}
	};
	
	ActionListener opDeshacer = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			limpiar();
		}
	};
	
	ActionListener opNota = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			new Cat_notas(nota).setVisible(true);
		}
	};
	
	ActionListener opPago = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			funcionDeChbPago();
		}
	};
	
	@SuppressWarnings("unchecked")
	public void funcionDeChbPago(){

		
		cmbFormaDePago.setSelectedIndex(0);
		cmbDepositoBanco.setSelectedIndex(0);
		
		if(chbPago.isSelected()){
			
			
			cmbReferencia.removeAllItems();
			cmbReferencia.addItem("Selecciona Beneficiario");
			cmbReferencia.addItem("Proveedor");
			cmbReferencia.addItem("Empleado");
			
			componentes(true);
			
			rbPagoPrv.setSelected(true);
			cmbTipo.setSelectedItem("EGRESOS");
			cmbTipo.setEnabled(false);
			
			ValidaImprimir();
		}else{
			
			txtReferencia.setText("");
			txtTotal.setText("");
			
			folioReferencia = 0;
			
			cmbFormaDePago.setEnabled(false);
			cmbDepositoBanco.setEnabled(false);
			
			cmbReferencia.removeAllItems();
			cmbReferencia.addItem("No Aplica");
			cmbReferencia.addItem("Departamento");
			cmbReferencia.addItem("Empleado");
			cmbReferencia.addItem("Establecimiento");
			cmbReferencia.addItem("Proveedor");
			
			cmbDepositoBanco.setSelectedIndex(0);
			
			componentes(false);
			
			if(tipo_poliza_a_modificar.equals("")){
				cmbTipo.setSelectedIndex(0);	
			}		
			cmbTipo.setEnabled(true);
			
			resetRButton();
		}
	
	}
	
	ActionListener opValidarImpresion = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			ValidaImprimir();
		}
	};
	
	 public void resetRButton(){
		 
			grupo.remove(rbPagoPrv);
			grupo.remove(rbAnticipoPrv);
			
			rbPagoPrv.setSelected(false);
			rbAnticipoPrv.setSelected(false);
			
			grupo.add(rbPagoPrv);
			grupo.add(rbAnticipoPrv);
			
			rbPoliza.setSelected(true);
	 }
	 
	 public void ValidaImprimir(){
		if(rbAnticipoPrv.isSelected()){
			rbAnticipo.setEnabled(true);
		}else{
			rbPoliza.setSelected(true);
			rbAnticipo.setEnabled(false);
		}
	 }
	
	ActionListener tipoReferencia = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			folioReferencia=0;
			txtReferencia.setText("");
			if(cmbReferencia.getSelectedIndex()==0){
				btnReferencia.setEnabled(false);
			}else{
				btnReferencia.setEnabled(true);
			}
		}
	};
	
	ActionListener opFiltroRef = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			try {
				new Cat_Filtro_Referencia().setVisible(true);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	};
	
	ActionListener opFiltroPolizas = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			try {
				new Cat_Filtro_De_Polizas().setVisible(true);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	};
	
	ActionListener opGuardarPoliza = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			String fecha = fhFecha.getDate()+"";
			if(fecha.equals("null")){
				JOptionPane.showMessageDialog(null, "La Fecha Esta Vacia O Esta Mal En Su Formato","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
				return;
			}else{
				
				if(fechaRef.before(fhFecha.getDate())){
					JOptionPane.showMessageDialog(null, "No Se Permite Alimentar Una Poliza Con Fechas Mayores Al Dia De Hoy","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
					return;
				}else{
					if(txaConcepto.getText().equals("")){
						JOptionPane.showMessageDialog(null, "Todas Las Polizas Deben Contar Con Un Concepto General","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
						return;
					}else{
						verificarTabla();
					}
				}
			}
		}
	};
	

	public void verificarTabla(){
		
		if(tabla.isEditing()){
			tabla.getCellEditor().stopCellEditing();
		}
		
		CalcularCuadreDePoliza();
		
		double cargo = 0;
		double abono = 0;
		
		for(int i =0; i<tabla.getRowCount(); i++){
			
				cargo = Double.valueOf(tabla.getValueAt(i, 4).toString().equals("")?"0":tabla.getValueAt(i, 4).toString());
				abono = Double.valueOf(tabla.getValueAt(i, 5).toString().equals("")?"0":tabla.getValueAt(i, 5).toString());
				
				if((cargo+abono)==0){
					JOptionPane.showMessageDialog(null, "La Fila [ "+(i+1)+" ] Requiere Un Cargo O Abono","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
					return;
				}
				
				if(!tabla.getValueAt(i,4).toString().equals("") && !tabla.getValueAt(i,5).toString().equals("")){
					JOptionPane.showMessageDialog(null, "La Fila [ "+(i+1)+" ] Tiene Cargo y Abono, Solo Se Permite Ingresar Uno De Los Dos Por Cuenta","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
					return;
				}
				
				if(tabla.getValueAt(i, 7).toString().equals("")){
					JOptionPane.showMessageDialog(null, "La Fila [ "+(i+1)+" ] Requiere El Establecimiento","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
					return;
				}
		}
		
		if(Float.valueOf(spCargoTotales.getValue().toString().trim())-Float.valueOf(spAbonoTotales.getValue().toString().trim())==0){
			guardarPolizas();
		}else{
			JOptionPane.showMessageDialog(null, "La Poliza No Esta Cuadrada","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
			return;
		}
		
	}
	
	public void guardarPolizas(){
		
		Object[][] matriz_movPolizas = MovPolizas();
		
			if(matriz_movPolizas.length>0){
				
//					if((cmbReferencia.getSelectedIndex()>0 && folioReferencia>0) || (cmbReferencia.getSelectedIndex()==0 && folioReferencia==0)){
						
							if(chbPago.isSelected()){
								
									if(!txtCheque.getText().equals("")){
										
											if(folio_poliza_a_modificar.equals("")  && (new BuscarSQL().existe_folio_cheque(cmbDepositoBanco.getSelectedItem().toString(),txtCheque.getText().toString().trim()))){
													JOptionPane.showMessageDialog(null, "El Folio De Cheque Ya Fue Registrado","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
													return;
											}else{
												
													if(cmbReferencia.getSelectedIndex()!=0){
														
															if(folioReferencia!=0){
																
																	if(!txtTotal.getText().equals("")){
																			guardar(matriz_movPolizas);
																	}else{
																			JOptionPane.showMessageDialog(null, "El Pago No Cuenta Con Un Total","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
																			return;
																	}
																	
															}else{
																JOptionPane.showMessageDialog(null, "El Tipo De Referencia Especificado Requiere Seleccionar Un "+cmbReferencia.getSelectedItem().toString(),"Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
																return;
															}
															
													}else{
														JOptionPane.showMessageDialog(null, "El Pago Requiere Un Tipo De Referencia","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
														return;
													}
												
											}
										
									}else{
										JOptionPane.showMessageDialog(null, "El Cheque No Cuenta Con Folio, Si No Se Ingresara Un Folio Desactive La Casilla De Pago","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
										return;
									}

							}else{
								guardar(matriz_movPolizas);
							}

//					}else{
//							JOptionPane.showMessageDialog(null, "Es Necesario Que Agregue Un [ "+cmbReferencia.getSelectedItem().toString()+" ] Ya Que Es La Referencia Seleccionada","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
//							return;
//					}
								
			}else{
					JOptionPane.showMessageDialog(null, "Es Necesario Que Agregue Movimientos De Polizas","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
					return;
			}
	}
	
//	variables para reporte
	String fecha = "";
	String tipo = "";
	String folio = "";
	
	public void guardar(Object[][] matriz){
				
				String tipo_documento = "";
				if(rbPagoPrv.isSelected())
					tipo_documento = "P";
				if(rbAnticipoPrv.isSelected())
					tipo_documento = "A";
		
			if(new GuardarSQL().Guardar_Poliza(txtFolio.getText().trim(), cmbTipo.getSelectedItem().toString().trim(), new SimpleDateFormat("dd/MM/yyyy").format(fhFecha.getDate()),cmbReferencia.getSelectedItem().toString(),folioReferencia, nota, txaConcepto.getText().toUpperCase().trim(), txtCheque.getText().toUpperCase().trim(), matriz, txtReferencia.getText().toUpperCase().trim(), cmbFormaDePago.getSelectedItem().toString().toUpperCase(),cmbDepositoBanco.getSelectedItem().toString().trim(),Float.valueOf(txtTotal.getText().toString().equals("")?"0":txtTotal.getText().toString()),tipo_documento,folio_poliza_a_modificar, tipo_poliza_a_modificar, fecha_a_modificar)){
				
				fecha = new SimpleDateFormat("dd/MM/yyyy").format(fhFecha.getDate())+" 00:00:00";
				tipo = cmbTipo.getSelectedItem().toString().toUpperCase();
				folio = txtFolio.getText();

				imprimir();
				limpiar();
				
//					return;
			}else{
					JOptionPane.showMessageDialog(null, "Ocurrió Un Error Al Intentar Guardar","Error",JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen//usuario-icono-eliminar5252-64.png"));
					return;
			}
	}
	
//	que pasara si el cargo y el abono tienen cantidad  =  0  ??????????????????
	public Object[][] MovPolizas(){
		Object[][] matriz = new Object[tabla.getRowCount()][tabla.getColumnCount()];
		
		for(int i =0; i<tabla.getRowCount(); i++){
			for(int j =0; j<tabla.getColumnCount(); j++){
				matriz[i][j] = tabla.getValueAt(i, j);
			}
		}
		return matriz;
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
		
				if(columna!=8){
					
						if(isNumeric(modelo.getValueAt(fila,4).toString().trim()) && isNumeric(modelo.getValueAt(fila,5).toString().trim())){
							
								RecorridoFoco();
								valor = true;
								
						}else{
								tabla.getSelectionModel().setSelectionInterval(fila, fila);
								
								JOptionPane.showMessageDialog(null, "La Fila  [ "+(fila+1)+" ]  Esta Mal En Su Formato En La Columna [  "+((columna==4)?"Cargo":"Abono")+"  ]","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
								modelo.setValueAt(0, fila, columna);
								
								tabla.editCellAt(fila, columna);
								Component aComp=tabla.getEditorComponent();
								aComp.requestFocus();
						}
				}	
			
				CalcularCuadreDePoliza();
					
		return valor;
	}
	
	@SuppressWarnings("deprecation")
	public void RecorridoFoco(){
		
		int cantidadDeFilas = tabla.getRowCount();
		String sacarFocoDeTabla = "no";
		
		if(fila == cantidadDeFilas-1){
				if(columna==7){
					sacarFocoDeTabla="si";
				}else{
						if(columna==6  && tabla.isCellEditable(fila, 7)){
							columna+=1;
						}else{
							if(columna==6 && !tabla.isCellEditable(fila, 7)){
								sacarFocoDeTabla="si";
							}else{
								columna+=1;
							}
						}
				}
		}else{
			sacarFocoDeTabla = "no";
			
				if(columna==7){
					fila+=1;
					columna = 4;
				}else{
						if(columna==6  && tabla.isCellEditable(fila, 7)){
							columna+=1;
						}else{
							if(columna==6 && !tabla.isCellEditable(fila, 7)){
								fila+=1;
								columna = 4;
							}else{
								columna+=1;
							}
						}
				}
		}
		
		tabla.getSelectionModel().setSelectionInterval(fila, fila);
		tabla.editCellAt(fila, columna);
		Component aComp=tabla.getEditorComponent();
		aComp.requestFocus();
		
		if(sacarFocoDeTabla.equals("si")){
			tabla.lostFocus(null, null);
			txtCuenta.requestFocus();
		}
		
		
//		if(columna==7 && fila == cantidadDeFilas-1){
////			regresar foco a txtCuenta
//			tabla.lostFocus(null, null);
//			txtCuenta.requestFocus();
//			
//		}else{
//			
//			if(columna==7){
////				saltar fila y resetear columna
//				fila+=1;
//				columna = 4;
//			}else{
////				saltar columna
//				columna+=1;
//			}
//			
//			tabla.getSelectionModel().setSelectionInterval(fila, fila);
//			tabla.editCellAt(fila, columna);
//			Component aComp=tabla.getEditorComponent();
//			aComp.requestFocus();
//			
//		}
	}
	
	public void CalcularCuadreDePoliza(){
		double total_cargo = 0;
		double total_abono = 0;
		
		for(int i =0; i<tabla.getRowCount(); i++){
			
			total_cargo += Double.valueOf(tabla.getValueAt(i, 4).toString().equals("")?"0":tabla.getValueAt(i, 4).toString());
			total_abono += Double.valueOf(tabla.getValueAt(i, 5).toString().equals("")?"0":tabla.getValueAt(i, 5).toString());
				
		}
		
		spCargoTotales.setValue(total_cargo);
		spAbonoTotales.setValue(total_abono);
		spDiferenciaTotales.setValue(total_cargo-total_abono);
	}
	
	 private boolean isNumeric(String cadena){
	    	try {
	    		if(cadena.equals("")){
	        		return true;
	    		}else{
	    			Float.parseFloat(cadena);
	        		return true;
	    		}
	    	} catch (NumberFormatException nfe){
	    		return false;
	    	}
	    }
	
	public void componentes(boolean validar){
		spCargoTotales.setEnabled(false);
		spAbonoTotales.setEnabled(false);
		spDiferenciaTotales.setEnabled(false);
		txtFolio.setEditable(false);
		
		txtCheque.setEditable(validar);
		
		txtTotal.setEditable(validar);
		cmbFormaDePago.setEnabled(validar);
		cmbDepositoBanco.setEnabled(validar);
		
		rbPagoPrv.setEnabled(validar);
		rbAnticipoPrv.setEnabled(validar);
		
//		rbAnticipo.setEnabled(validar);
//		rbPoliza.setEnabled(validar);
//		rbCheque.setEnabled(validar);
		
	}
	
	@SuppressWarnings("unchecked")
	public void limpiar(){
		
		panel.setBorder(BorderFactory.createTitledBorder("Generar Poliza"));	
		modelo.setRowCount(0);
		
		spAbonoTotales.setValue(0);
		spCargoTotales.setValue(0);
		spDiferenciaTotales.setValue(0);
		
		txtCheque.setText("");
		txtReferencia.setText("");
		txtTotal.setText("");
		txaConcepto.setText("");
		txtCuenta.setText("");
		
		folioReferencia = 0;
		nota="";
		
		chbPago.setSelected(false);
		cmbFormaDePago.setEnabled(false);
		cmbDepositoBanco.setEnabled(false);
		
		cmbReferencia.removeAllItems();
		cmbReferencia.addItem("No Aplica");
		cmbReferencia.addItem("Departamento");
		cmbReferencia.addItem("Empleado");
		cmbReferencia.addItem("Establecimiento");
		cmbReferencia.addItem("Proveedor");
		
		cmbTipo.setSelectedIndex(0);
		cmbDepositoBanco.setSelectedIndex(0);
		
		componentes(false);
		txtReferencia.setEditable(false);
		
		cmbTipo.setEnabled(true);
		
		fhFecha.setDate(cargar_fecha_Sugerida(0));
		buscarFolioPolizaConsecutivo();
		
		resetRButton();
		
		folio_poliza_a_modificar="";
		tipo_poliza_a_modificar="";
		fecha_a_modificar="";
		
	}
	
	public void llamar_render(){
		tabla.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",12));
		tabla.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",12));
		tabla.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",12));
		tabla.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",12));
		tabla.getColumnModel().getColumn(4).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",12));
		tabla.getColumnModel().getColumn(5).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",12));
		tabla.getColumnModel().getColumn(6).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",12));
		tabla.getColumnModel().getColumn(7).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",12));
		
		this.tabla.getTableHeader().setReorderingAllowed(false) ;

		int largo=75;
		this.tabla.getColumnModel().getColumn(0).setMaxWidth(largo);
		this.tabla.getColumnModel().getColumn(0).setMinWidth(largo);
		this.tabla.getColumnModel().getColumn(1).setMaxWidth(largo);
		this.tabla.getColumnModel().getColumn(1).setMinWidth(largo);
		this.tabla.getColumnModel().getColumn(2).setMaxWidth(largo);
		this.tabla.getColumnModel().getColumn(2).setMinWidth(largo);
		          
		this.tabla.getColumnModel().getColumn(3).setMaxWidth(300);
		this.tabla.getColumnModel().getColumn(3).setMinWidth(300);
		this.tabla.getColumnModel().getColumn(4).setMaxWidth(largo);
		this.tabla.getColumnModel().getColumn(4).setMinWidth(largo);
		
		this.tabla.getColumnModel().getColumn(5).setMaxWidth(largo);
		this.tabla.getColumnModel().getColumn(5).setMinWidth(largo);
		this.tabla.getColumnModel().getColumn(6).setMaxWidth(largo+50);
		this.tabla.getColumnModel().getColumn(6).setMinWidth(largo+50);
		this.tabla.getColumnModel().getColumn(7).setMaxWidth(largo+50);
		this.tabla.getColumnModel().getColumn(7).setMinWidth(largo+50);
		this.tabla.getColumnModel().getColumn(8).setMaxWidth(largo-35);
		this.tabla.getColumnModel().getColumn(8).setMinWidth(largo-35);
		
	}
	
	ActionListener opBuscar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			buscarFolioPolizaConsecutivo();
		}
	};
	
	PropertyChangeListener opBusqueda = new PropertyChangeListener() {
	  	  public void propertyChange(PropertyChangeEvent e) {
	  		  
  	            if ("date".equals(e.getPropertyName())){
  	            	buscarFolioPolizaConsecutivo();
  	            }
	  	  }
	};
	
	
	public void buscarFolioPolizaConsecutivo(){
		
		if(fhFecha.getDate() != null){
			txtFolio.setText(new BuscarTablasModel().folio_consecutivo_de_poliza(new SimpleDateFormat("dd/MM/yyyy").format(fhFecha.getDate()),cmbTipo.getSelectedItem().toString().toUpperCase().trim()));
      	}else{
      		txtFolio.setText("");
      	}
	}
	
	ActionListener opImprimir = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			imprimir();
		}
	};
	
	public void imprimir(){
//		String fecha = new SimpleDateFormat("dd/MM/yyyy").format(fhFecha.getDate())+" 00:00:00";
		Obj_Usuario usuario = new Obj_Usuario().LeerSession();
		String basedatos="2.26";
		String vista_previa_reporte="no";
		int vista_previa_de_ventana=0;
		String comando="" ;
		String reporte = "";
						 
							if(rbPoliza.isSelected()){
								comando="exec sp_consulta_de_poliza '"+tipo+"','"+fecha+"','"+folio+"','"+usuario.getNombre_completo()+"'" ;
								reporte = "Obj_Reporte_De_Consulta_De_Poliza.jrxml";
								new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
							}
							if(rbCheque.isSelected()){
								comando="exec sp_consulta_de_poliza_cheque '"+tipo+"','"+fecha+"','"+folio+"','"+usuario.getNombre_completo()+"'" ;
								reporte = "Obj_Reporte_De_Consulta_De_Poliza_De_Cheque.jrxml";
								new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
							}
							if(rbAnticipo.isSelected()){
								
							}
							else{
//								aviso
							}
	}
	
	
	
	public class Cat_Filtro_Cuentas extends JDialog{

		Container cont = getContentPane();
		JLayeredPane campo = new JLayeredPane();
		
		public Object[][] Filtro_Cuentas( ){
				try {
					return new BuscarSQL().Filtro_De_Cuentas_polizas();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return null;
		}
		
		DefaultTableModel modelo_Filtro = new DefaultTableModel(Filtro_Cuentas(),
	            new String[]{"Codigo", "Nombre","Cuenta","SubCuenta" }
				){
		     @SuppressWarnings("rawtypes")
			Class[] types = new Class[]{
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
	        	 	} 				
	 			return false;
	 		}
		};
		JTable tabla_Filtro = new JTable(modelo_Filtro);
	    JScrollPane scroll_Filtro = new JScrollPane(tabla_Filtro);
		
		JTextField txtCodigo = new JTextField();
		JTextField txtDescripcion = new JTextField();
		
		public Cat_Filtro_Cuentas(String codigo){
			
			this.setModal(true);
			
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/filter_icon&16.png"));
			this.setTitle("Filtro De Cuentas");
			campo.setBorder(BorderFactory.createTitledBorder("Filtro De Cuentas"));
			
			campo.add(scroll_Filtro).setBounds(15,42,1000,565);
			
			campo.add(txtCodigo).setBounds(15,20,100,20);
			campo.add(txtDescripcion).setBounds(114,20,400,20);
			
			render();
			agregar(tabla_Filtro);
			
			cont.add(campo);
			
			txtCodigo.setText(codigo);
			new Obj_Filtro_Dinamico(tabla_Filtro,"Codigo", txtCodigo.getText().toUpperCase(),"Nombre",txtDescripcion.getText(), "", "", "", "");
			
			txtCodigo.addKeyListener(opFiltroLoco);
			txtDescripcion.addKeyListener(opFiltroLoco);
			
			this.setSize(1040,650);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			
//          asigna el foco al JTextField del nombre deseado al arrancar la ventana
            this.addWindowListener(new WindowAdapter() {
                    public void windowOpened( WindowEvent e ){
                    	txtDescripcion.requestFocus();
                 }
            });
              
              tabla.addKeyListener(seleccionCuentacConTeclado);
              
//            pone el foco en la tabla al presionar f4
              getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                 KeyStroke.getKeyStroke(KeyEvent.VK_F4 , 0), "dtabla");
              
              getRootPane().getActionMap().put("dtabla", new AbstractAction(){
                  @Override
                  public void actionPerformed(ActionEvent e)
                  {
                	tabla_Filtro.requestFocus();
                	iniciarSeleccionConTeclado();
                  }
              });
              
				KeyStroke tab = KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0);
				tabla_Filtro.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(tab, "TAB");
				
				tabla_Filtro.getActionMap().put("TAB", new AbstractAction(){
	                 public void actionPerformed(ActionEvent e)
	                 {
	                	iniciarSeleccionConTeclado();
	                 }
	            });
		}
		
		private void agregar(final JTable tbl) {
	        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
		        public void mouseClicked(MouseEvent e) {
		        	
		        	if(e.getClickCount() == 1){
		        		iniciarSeleccionConTeclado();
		        	}
		        	if(e.getClickCount() == 2){
		    			fila = tabla_Filtro.getSelectedRow();
		    			Object folio =  tabla_Filtro.getValueAt(fila, 0).toString().trim();
		    			dispose();
		    			txtCuenta.setText(folio+"");
		    			buscarAutomatico();
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
 	     
		KeyListener seleccionCuentacConTeclado = new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				
				KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
				tabla_Filtro.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(enter, "Enter");
				
				tabla_Filtro.getActionMap().put("Enter", new AbstractAction(){
	                 public void actionPerformed(ActionEvent e)
	                 {
	                	iniciarSeleccionConTeclado();
	                	
	                	fila=tabla_Filtro.getSelectedRow();
	     				String folio = tabla_Filtro.getValueAt(fila,0).toString().trim();
	     					
	     				txtCuenta.setText(folio);
	     				dispose();
	                 }
	            });
			}
			public void keyPressed(KeyEvent e){}
			public void keyReleased(KeyEvent e){}
		};
		
		
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
		    tabla_Filtro.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
		    tabla_Filtro.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
			
			tabla_Filtro.getColumnModel().getColumn(0).setMinWidth(0);
		    tabla_Filtro.getColumnModel().getColumn(0).setMaxWidth(100);
			tabla_Filtro.getColumnModel().getColumn(1).setMinWidth(300);
			tabla_Filtro.getColumnModel().getColumn(1).setMaxWidth(400);
			tabla_Filtro.getColumnModel().getColumn(2).setMinWidth(0);
			tabla_Filtro.getColumnModel().getColumn(2).setMaxWidth(230);
			tabla_Filtro.getColumnModel().getColumn(3).setMinWidth(0);
			tabla_Filtro.getColumnModel().getColumn(3).setMaxWidth(230);
			
		}
	}
	
	public void CalcularFoliosTabla(){
		for(int i = 0; i<tabla.getRowCount(); i++){
			tabla.setBackground(Color.blue);
			tabla.setForeground(Color.white);
			tabla.setValueAt((i+1)+"", i, 8);
		}
	}
	
	public class Cat_notas extends Cat_Notas{
		
		public Cat_notas(String nta){
			txaNota.setText(nta);
			
			btnAgregar.addActionListener(opAgregar);
		}
		
		ActionListener opAgregar = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				nota = txaNota.getText().toString().trim();
				dispose();
			}
		};
	}
	
	
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
			
			Object[][] datos = new BuscarSQL().Filtro_De_Referencia_Polizas(cmbReferencia.getSelectedItem().toString());
			for(Object[] d : datos){
				modelo_Filtro_Ref.addRow(d);
			}
		}
		
//		public Object[][] Filtro_Cuentas( ){
//			try {
//				return new BuscarSQL().Filtro_De_Cuentas_polizas();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//			return null;
//	}
		
		private void agregar(final JTable tbl) {
	        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
		        public void mouseClicked(MouseEvent e) {
		        	
		        	if(e.getClickCount() == 2){
		    			fila = tabla_Filtro_Ref.getSelectedRow();
		    			int folio =  Integer.valueOf(tabla_Filtro_Ref.getValueAt(fila, 0).toString().trim());
		    			folioReferencia= folio;
		    			txtReferencia.setText(tabla_Filtro_Ref.getValueAt(fila, 1).toString());
		    			txtReferencia.setEditable(true);
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
	
	
	public class Cat_Filtro_De_Polizas extends JDialog{

	Container cont = getContentPane();
	JLayeredPane campo = new JLayeredPane();
	
	DefaultTableModel modelo_poliza = new DefaultTableModel(null,
            new String[]{"Folio", "Tipo","Fecha De Poliza", "Establecimiento", "Concepto"}
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
	JTable tabla_poliza = new JTable(modelo_poliza);
    JScrollPane scroll_polina = new JScrollPane(tabla_poliza);
	
	JTextField txtCodigo = new JTextField();
	JTextField txtDescripcion = new JTextField();
	
	JDateChooser fhFechaBusquedaDePolizas = new JDateChooser();
	
	public Cat_Filtro_De_Polizas() throws SQLException{
		
		this.setModal(true);
		
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/filter_icon&16.png"));
		this.setTitle("Filtro De Referencias");
		campo.setBorder(BorderFactory.createTitledBorder("Filtro De Referencias"));
		
		campo.add(new JLabel("<html><FONT color='BLUE'>Buscar Por Fecha: </FONT></html>")).setBounds(15,20,90,20);
		campo.add(fhFechaBusquedaDePolizas).setBounds(104,20,120,20);
		
		campo.add(txtCodigo).setBounds(15,45,60,20);
		campo.add(txtDescripcion).setBounds(414,45,440,20);

		campo.add(scroll_polina).setBounds(15,65,860,535);
		
		render();
		fhFechaBusquedaDePolizas.setDate(cargar_fecha_Sugerida(0));
		buscarMPolizas();
		
		agregar(tabla_poliza);
		fhFechaBusquedaDePolizas.addPropertyChangeListener(opBusquedaPolizasPorFecha);
		
		cont.add(campo);
		
//		new Obj_ Filtro_Dinamico(tabla_poliza,"Codigo", txtCodigo.getText().toUpperCase(),"Nombre",txtDescripcion.getText(), "", "", "", "");
		
		txtCodigo.addKeyListener(opFiltroLoco);
		txtDescripcion.addKeyListener(opFiltroLoco);
		
		this.setSize(900,650);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
//      asigna el foco al JTextField del nombre deseado al arrancar la ventana
        this.addWindowListener(new WindowAdapter() {
                public void windowOpened( WindowEvent e ){
                	txtDescripcion.requestFocus();
             }
        });
          
	}
	
	PropertyChangeListener opBusquedaPolizasPorFecha = new PropertyChangeListener() {
	  	  public void propertyChange(PropertyChangeEvent e) {
	  		  
	            if ("date".equals(e.getPropertyName())){
	            	buscarMPolizas();
	            }
	  	  }
	};
	
	public void buscarMPolizas(){
		
		modelo_poliza.setRowCount(0);
		try {
			String fecha = new SimpleDateFormat("dd/MM/yyyy").format(fhFechaBusquedaDePolizas.getDate());
			System.out.println(fecha);
			
			Object[][] llenarFiltro = new BuscarSQL().Filtro_Polizas_Guardadas(fecha);
			for(Object[] filaMPoliza  : llenarFiltro){
				modelo_poliza.addRow(filaMPoliza);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
//	public Object[][] Filtro_Cuentas( ){
//		try {
//			return new BuscarSQL().Filtro_De_Cuentas_polizas();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return null;
//}
	
	private void agregar(final JTable tbl) {
        tbl.addMouseListener(new java.awt.event.MouseAdapter(){
	        public void mouseClicked(MouseEvent e) {
	        	
	        	if(e.getClickCount() == 2){
	        		
	    			fila = tabla_poliza.getSelectedRow();
	    			String folio =  tabla_poliza.getValueAt(fila, 0).toString().trim();
    				Object[] poliza = new BuscarSQL().registro_Polizas_Guardadas(folio,tabla_poliza.getValueAt(fila, 1).toString().trim(),tabla_poliza.getValueAt(fila, 2).toString().trim());

//	    			System.out.println(poliza[0].toString().trim());
//	    			System.out.println(poliza[1].toString().trim());
//	    			System.out.println(poliza[2].toString().trim());
//	    			System.out.println(poliza[3].toString().trim());
//	    			System.out.println(poliza[4].toString().trim());
//	    			System.out.println(poliza[5].toString().trim());
//	    			System.out.println(poliza[6].toString().trim());
//	    			System.out.println(poliza[7].toString().trim());
//	    			System.out.println(poliza[8].toString().trim());
//	    			System.out.println(poliza[9].toString().trim());
//	    			System.out.println(poliza[10].toString().trim());
//	    			System.out.println(poliza[11].toString().trim());
//	    			System.out.println(poliza[12].toString().trim());
    				
    				panel.setBorder(BorderFactory.createTitledBorder("Modificar Poliza"));
    				
	    			cmbTipo.setSelectedItem(poliza[1].toString());
    				System.out.println(poliza[1].toString());
    				
    				try {
						fhFecha.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(poliza[2].toString()));
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
						
    				nota = poliza[3].toString();
    				chbPago.setSelected(cmbTipo.getSelectedItem().equals("EGRESOS")?true:false);
    				funcionDeChbPago();
    				
    				folio_poliza_a_modificar=poliza[0].toString();
    				tipo_poliza_a_modificar=poliza[1].toString();
    				fecha_a_modificar=poliza[2].toString();
    				
    				txtFolio.setText(poliza[0].toString());
    				
    				txaConcepto.setText(poliza[4].toString().trim());
    				txtTotal.setText(poliza[11].toString().trim());
    				
    				
    				
	    			if(poliza[1].toString().equals("EGRESOS")){
	    				    				
	    				if(poliza[5].toString().trim().equals("No Aplica")){
	    					cmbReferencia.setSelectedIndex(0);
	    				}else{
	    					cmbReferencia.setSelectedItem(poliza[5].toString().trim());
	    				}
	    				
	    				folioReferencia = Integer.valueOf(poliza[6].toString().trim());
	    				txtReferencia.setText(poliza[7].toString().trim());
	    				
	    				txtCheque.setText(poliza[8].toString().trim());
	    				
	    				cmbDepositoBanco.setSelectedItem(poliza[9].toString().trim());
	    				cmbFormaDePago.setSelectedItem(poliza[10].toString().trim());
	    				
	    				
						if(poliza[12].toString().trim().equals("P")){ rbPagoPrv.setSelected(true); }
						if(poliza[12].toString().trim().equals("A")){ rbAnticipoPrv.setSelected(true); }
						
						txtReferencia.setEditable(true);
    				}else{
    					cmbReferencia.setSelectedIndex(0);
    					folioReferencia = 0;
	    				txtReferencia.setText("");
	    				txtCheque.setText("");
	    				cmbDepositoBanco.setSelectedIndex(0);
	    				cmbFormaDePago.setSelectedIndex(0);
	    				
	    				grupo.remove(rbAnticipoPrv);
	    				grupo.remove(rbPagoPrv);
	    				rbAnticipoPrv.setSelected(false);
	    				rbPagoPrv.setSelected(false);
	    				grupo.add(rbAnticipoPrv);
	    				grupo.add(rbPagoPrv);
    				}

    				
    				
	    			
	    			
	    				modelo.setRowCount(0);
	    				Object[][] mpolizas = new BuscarSQL().registro_mPolizas_Guardadas(folio,tabla_poliza.getValueAt(fila, 1).toString().trim(),tabla_poliza.getValueAt(fila, 2).toString().trim());
	    				for(Object[] d : mpolizas){
	    					modelo.addRow(d);
	    				}
	    				CalcularFoliosTabla();
	    				CalcularCuadreDePoliza();
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
			
			new Obj_Filtro_Dinamico(tabla_poliza,"Folio", txtCodigo.getText().toUpperCase(),"Concepto",txtDescripcion.getText(), "", "", "", "");
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}		
	};
	
   	private void render(){	
		
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
		tcr.setHorizontalAlignment(SwingConstants.CENTER);
		
		tabla_poliza.getTableHeader().setReorderingAllowed(false) ;
		
		tabla_poliza.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
		tabla_poliza.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","centro","Arial","normal",12)); 
		tabla_poliza.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","centro","Arial","normal",12)); 
		tabla_poliza.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
		tabla_poliza.getColumnModel().getColumn(4).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
		
		tabla_poliza.getColumnModel().getColumn(0).setMinWidth(60);
		tabla_poliza.getColumnModel().getColumn(0).setMaxWidth(60);
		tabla_poliza.getColumnModel().getColumn(1).setMinWidth(80);
		tabla_poliza.getColumnModel().getColumn(1).setMaxWidth(80);
		tabla_poliza.getColumnModel().getColumn(2).setMinWidth(140);
		tabla_poliza.getColumnModel().getColumn(2).setMaxWidth(140);
		tabla_poliza.getColumnModel().getColumn(3).setMinWidth(120);
		tabla_poliza.getColumnModel().getColumn(3).setMaxWidth(120);
		tabla_poliza.getColumnModel().getColumn(4).setMinWidth(500);
		tabla_poliza.getColumnModel().getColumn(4).setMaxWidth(500);
		
	}
}
	
	public static void main(String[] args) {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Polizas().setVisible(true);
		}catch(Exception e){	}		
	}
}
