package Cat_Contabilidad;

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
import javax.swing.FocusManager;
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
import Conexiones_SQL.GuardarSQL;
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
	
	JTextField txtFolio = new Componentes().text(new JTextField(), "Tipo", 10, "String");
	JTextField txtCuenta = new Componentes().text(new JTextField(), "Cuenta", 16, "Int");
	JDateChooser fhFecha 	= new JDateChooser();
	
	JButton btnNota = new JButton("Nota");
	JButton btnGuardarPoliza = new JButton("Guardar");
	
	JButton btnQuitar = new JButton("Quitar");
	
	JLabel lblMovimientos 	= new JLabel("");
	JLabel lblTotales 	= new JLabel("");
	
	public String[] referencia(){try {return new Cargar_Combo().cuentas();} catch (SQLException e) {e.printStackTrace();}return null;}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbReferencia = new JComboBox(referencia());
	
	public String[] establecimiento(){try {return new Cargar_Combo().EstablecimientoTb();} catch (SQLException e) {e.printStackTrace();}return null;}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbEstablecimiento = new JComboBox(establecimiento());
	
    SpinnerModel cargoMov = new SpinnerNumberModel(0, 0, 50000, .1);
	JSpinner spCargoMov = new JSpinner(cargoMov);
	SpinnerModel abonoMov = new SpinnerNumberModel(0, 0, 50000, .1);
	JSpinner spAbonoMov = new JSpinner(abonoMov);
	SpinnerModel diferenciaMov = new SpinnerNumberModel(0, 0, 50000, .1);
	JSpinner spDiferenciaMov = new JSpinner(diferenciaMov);
	
    SpinnerModel cargoTotales = new SpinnerNumberModel(0, 0, 50000, .1);
	JSpinner spCargoTotales = new JSpinner(cargoTotales);
	SpinnerModel abonoTotales = new SpinnerNumberModel(0, 0, 50000, .1);
	JSpinner spAbonoTotales = new JSpinner(abonoTotales);
	SpinnerModel diferenciaTotales = new SpinnerNumberModel(0, 0, 50000, .1);
	JSpinner spDiferenciaTotales = new JSpinner(diferenciaTotales);
	
	JTextArea txaConcepto = new Componentes().textArea(new JTextArea(), "Observaciones", 980);
	JScrollPane Concepto = new JScrollPane(txaConcepto);	
	
	DefaultTableModel modelo = new DefaultTableModel(null,
            new String[]{"Cuenta", "SCuenta", "SSCuenta","Nombre","Cargo","Abono","Concepto", "Establecimiento", "Folio"}
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
        	 	case 4 : if(Float.valueOf(tabla.getValueAt(fila, 5).toString().equals("")?"0":tabla.getValueAt(fila, 5).toString())>0){return false;}else{return true;}
        	 	case 5 : if(Float.valueOf(tabla.getValueAt(fila, 4).toString().equals("")?"0":tabla.getValueAt(fila, 4).toString())>0){return false;}else{return true;}
        	 	case 6 : return true;
        	 	case 7 : return true;
        	 	case 8 : return false;
        	 	} 				
 			return false;
 		}
	};
	
	JTable tabla = new JTable(modelo);
	JScrollPane scroll = new JScrollPane(tabla,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
	public TableColumn columnaChb = tabla.getColumnModel().getColumn(7);
	
	String nota = "";
	
	Border borderline;
	public Cat_Polizas(String nta){
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/cajas-de-cajas-de-embalaje-de-envio-de-un-archivo-tar-icono-4009-64.png"));
		this.setTitle("polizas");
		panel.setBorder(BorderFactory.createTitledBorder("polizas"));	
		
		nota=nta;
		
		borderline = BorderFactory.createLineBorder(new Color(45,48,48));
		lblMovimientos.setBorder(BorderFactory.createTitledBorder(borderline,"Movimiento"));
		lblTotales.setBorder(BorderFactory.createTitledBorder(borderline,"Totales"));
		
		this.columnaChb.setCellEditor(new javax.swing.DefaultCellEditor(cmbEstablecimiento));
		
		int x=20,y=20,ancho=80;
		
		panel.add(new JLabel("Tipo:")).setBounds(x+5,y,50,20);
		panel.add(cmbTipo).setBounds(x+35,y,120,20);
		
		panel.add(new JLabel("Fecha:")).setBounds(x*11,y,70,20);
		panel.add(fhFecha  ).setBounds(x*11+60,y,100,20);
		
		panel.add(new JLabel("Folio:")).setBounds(x*23,y,70,20);
		panel.add(txtFolio  ).setBounds(x*23+50,y,80,20);
		
		panel.add(lblMovimientos).setBounds(x-5,y+=20,180,95);                  panel.add(lblTotales).setBounds(x*11-10,y,180,95);            
		panel.add(new JLabel("Cargo:")).setBounds(x+5,y+=15,50,20);             panel.add(new JLabel("Cargo:")).setBounds(x*11,y,50,20);    	panel.add(btnNota  ).setBounds(x*23+50,y-10,80,20);   
		panel.add(spCargoMov).setBounds(x+65,y,90,20);                       	panel.add(spCargoTotales).setBounds(x*11+60,y,90,20);               
		panel.add(new JLabel("Abono:")).setBounds(x+5,y+=25,50,20);            	panel.add(new JLabel("Abono:")).setBounds(x*12,y,50,20);      	 panel.add(new JLabel("Referencia: ")).setBounds(x*17+70,y,80,20);		panel.add(cmbReferencia).setBounds(x*23+20,y,110,20); 
		panel.add(spAbonoMov).setBounds(x+65,y,90,20);                       	panel.add(spAbonoTotales).setBounds(x*11+60,y,90,20);                 
		panel.add(new JLabel("Diferencia:")).setBounds(x+5,y+=25,70,20);       	panel.add(new JLabel("Diferencia:")).setBounds(x*11,y,70,20); 
		panel.add(spDiferenciaMov).setBounds(x+65,y,90,20);                  	panel.add(spDiferenciaTotales).setBounds(x*11+60,y,90,20);      
		
		panel.add(new JLabel("Concepto:")).setBounds(x-5,y+=55,70,20);
		panel.add(Concepto).setBounds(x-5,y+=15,ancho*7+15,45);
		
		panel.add(new JLabel("Cuenta:")).setBounds(x-5,y+=50,ancho,20);
		panel.add(txtCuenta).setBounds(x+ancho-40,y,ancho+20,20);
		
		panel.add(btnQuitar  ).setBounds(x*22-15,y,80,20);
		panel.add(btnGuardarPoliza  ).setBounds(x*23+50,y,80,20);
		
		panel.add(scroll).setBounds(x-5,y+=25,ancho*12+20,190);
		
		cont.add(panel);
		
		txaConcepto.setLineWrap(true); 
		txaConcepto.setWrapStyleWord(true);
		
		llamar_render();
		componentes(false);
		
		fhFecha.setDate(cargar_fecha_Sugerida(0));
		buscarFolioConsecutivo();
		
		cmbTipo.addActionListener(opBuscar);
		fhFecha.addPropertyChangeListener(opBusqueda);
		
		btnGuardarPoliza.addActionListener(opGuardarPoliza);
		btnQuitar.addActionListener(opQuitar);
		
		btnNota.addActionListener(opNota);
		
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
	    					modelo.addRow(p);
	    				}
	    				tabla.setValueAt(txaConcepto.getText().toUpperCase().trim(), 0, 6);
	    				CalcularFoliosTabla();
	    			}else{
	    				JOptionPane.showMessageDialog(null, "No Se Encontraron Registros","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
	    				return;
	    			}
	    		}
	        }
	    });
		
		this.setSize(1024, 500);
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
	        		
	        		if(columna==4){
	        			if(Float.valueOf(tbl.getValueAt(fila, 5).toString().equals("")?"0":tbl.getValueAt(fila, 5).toString())==0){
	        				
	        				tbl.editCellAt(fila, columna);
							Component aComp=tbl.getEditorComponent();
							aComp.requestFocus();
	        		
		        		}
	        		}
	        		
	        		if(columna==5){
	        			if(Float.valueOf(tbl.getValueAt(fila, 4).toString().equals("")?"0":tbl.getValueAt(fila, 4).toString())==0){
	        				
	        				tbl.editCellAt(fila, columna);
							Component aComp=tbl.getEditorComponent();
							aComp.requestFocus();
	        		
		        		}
	        		}
	        		
//	        		componentes(false);
//	    			int fila = tbl.getSelectedRow();
					
//					txtTipo.setText(tabla_conf.getValueAt(fila, 0).toString());
//					txtNombre.setText(tabla_conf.getValueAt(fila, 1).toString());
//					spRelleno.setValue(Integer.valueOf(tabla_conf.getValueAt(fila, 2).toString().trim()));
//					cmbAsiento_Cont.setSelectedItem((tabla_conf.getValueAt(fila, 3).toString().trim().equals("I"))?"Ingresos" : ((tabla_conf.getValueAt(fila, 3).toString().trim().equals("E"))?"Egresos" : "Varios") );
//					cmbStatus.setSelectedItem(tabla_conf.getValueAt(fila, 4).toString().trim().equals("V")?("Vigente"):("Cancelado"));
	        	}
	        }
        });
    }
	
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
	
	ActionListener opNota = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			new Cat_notas(nota).setVisible(true);
		}
	};
	
	ActionListener opGuardarPoliza = new ActionListener() {
		public void actionPerformed(ActionEvent e) {

			verificarTabla();
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
			}
		if(Float.valueOf(spDiferenciaTotales.getValue().toString())==0){
			guardarPolizas();
		}else{
			JOptionPane.showMessageDialog(null, "La Poliza No Esta Cuadrada","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
			return;
		}
		
	}
	
	public void guardarPolizas(){
		
		Object[][] matriz_movPolizas = MovPolizas();
		
				if(matriz_movPolizas.length>0){
				
						if(new GuardarSQL().Guardar_Poliza(txtFolio.getText().trim(), cmbTipo.getSelectedItem().toString().trim(), new SimpleDateFormat("dd/MM/yyyy").format(fhFecha.getDate()),cmbReferencia.getSelectedItem().toString(),0, nota, matriz_movPolizas)){
								buscarFolioConsecutivo();
								
								while(tabla.getRowCount()>0){
									modelo.removeRow(0);
								}
								
								CalcularCuadreDePoliza();
								
								JOptionPane.showMessageDialog(null, "La Poliza Se Guardó Exitosamente","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen//aplicara-el-dialogo-icono-6256-32.png"));
								return;
						}else{
								JOptionPane.showMessageDialog(null, "Ocurrió Un Error Al Intentar Guardar","Error",JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen//usuario-icono-eliminar5252-64.png"));
								return;
						}
				
				}else{
						JOptionPane.showMessageDialog(null, "En Necesario Que Agregue Movimientos De Polizas","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
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
					if(isNumeric(modelo.getValueAt(fila,4).toString().trim()) && isNumeric(modelo.getValueAt(fila,5).toString().trim())){
						
						int cantidadDeFilas = tabla.getRowCount();
						fila+=1;
						
						if(fila == cantidadDeFilas){ fila=0;	}
						if(columna==4 && !tabla.getValueAt(fila, 5).toString().equals("")){	fila+=1; }
						if(columna==5 && !tabla.getValueAt(fila, 4).toString().equals("")){	fila+=1; }
						
							tabla.getSelectionModel().setSelectionInterval(fila, fila);
							tabla.editCellAt(fila, columna);
							Component aComp=tabla.getEditorComponent();
							aComp.requestFocus();
							valor = true;
							
					}else{
							tabla.getSelectionModel().setSelectionInterval(fila, fila);
							
							JOptionPane.showMessageDialog(null, "La Fila  [ "+(fila+1)+" ]  Esta Mal En Su Formato En La Columna [  "+((columna==4)?"Cargo":"Abono")+"  ]","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
							modelo.setValueAt(0, fila, columna);
							
							tabla.editCellAt(fila, columna);
							Component aComp=tabla.getEditorComponent();
							aComp.requestFocus();
					}
					
					CalcularCuadreDePoliza();
					
		return valor;
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
		spCargoMov.setEnabled(validar);
		spAbonoMov.setEnabled(validar);
		spDiferenciaMov.setEnabled(validar);
		spCargoTotales.setEnabled(validar);
		spAbonoTotales.setEnabled(validar);
		spDiferenciaTotales.setEnabled(validar);
		
		txtFolio.setEditable(validar);
	}
	
//	public void limpiar(){
//		txtTipo.setText("");
//		txtNombre.setText("");
//		cmbAsiento_Cont.setSelectedIndex(0);
//		cmbStatus.setSelectedIndex(0);
//		
//		spRelleno.setValue(2);
//		spAnio.setValue(2015);
//	}
	
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
			buscarFolioConsecutivo();
		}
	};
	
	PropertyChangeListener opBusqueda = new PropertyChangeListener() {
	  	  public void propertyChange(PropertyChangeEvent e) {
	  		  
  	            if ("date".equals(e.getPropertyName())) {
  	            	buscarFolioConsecutivo();
  	            }
	  	  }
	};
	
	
//	public String folio_concecutivo(){
//		return new BuscarTablasModel().folio_consecutivo_de_poliza();
//	}
	public void buscarFolioConsecutivo(){
		
		if(fhFecha.getDate() != null){
			txtFolio.setText(new BuscarTablasModel().folio_consecutivo_de_poliza(new SimpleDateFormat("dd/MM/yyyy").format(fhFecha.getDate()),cmbTipo.getSelectedItem().toString().toUpperCase().trim()));
      	}else{
      		txtFolio.setText("");
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
			this.setTitle("Filtro de Empleados");
			campo.setBorder(BorderFactory.createTitledBorder("Filtro De Empleado"));
			
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
	
	public static void main(String[] args) {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Polizas("").setVisible(true);
		}catch(Exception e){	}		
	}
}
