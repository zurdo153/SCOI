package Cat_Contabilidad;

import java.awt.Color;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import Conexiones_SQL.BuscarTablasModel;
import Conexiones_SQL.Cargar_Combo;
import Obj_Principal.Componentes;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Polizas extends JFrame{
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	public String[] tipo(){
		try {
			return new Cargar_Combo().tipos_de_polizas("tb_configuracion_de_polizas");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; 
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbTipo = new JComboBox(tipo());
	
	JTextField txtFolio = new Componentes().text(new JTextField(), "Tipo", 10, "String");
	JDateChooser fhFecha 	= new JDateChooser();
	
	JLabel lblMovimientos 	= new JLabel("");
	JLabel lblTotales 	= new JLabel("");
	
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
            new String[]{"Tipo","Nombre","Relleno","Asiento Cont.","Status"}
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
	
	JTable tabla = new JTable(modelo);
	JScrollPane scroll = new JScrollPane(tabla,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
	Border borderline;
	public Cat_Polizas(){
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/cajas-de-cajas-de-embalaje-de-envio-de-un-archivo-tar-icono-4009-64.png"));
		this.setTitle("polizas");
		panel.setBorder(BorderFactory.createTitledBorder("polizas"));	
		
		borderline = BorderFactory.createLineBorder(new Color(45,48,48));
		lblMovimientos.setBorder(BorderFactory.createTitledBorder(borderline,"Movimiento"));
		lblTotales.setBorder(BorderFactory.createTitledBorder(borderline,"Totales"));
		
		int x=20,y=20,ancho=80;
		
		panel.add(new JLabel("Tipo:")).setBounds(x+5,y,50,20);
		panel.add(cmbTipo).setBounds(x+35,y,120,20);
		
		panel.add(new JLabel("Fecha:")).setBounds(x*13,y,70,20);
		panel.add(fhFecha  ).setBounds(x*13+60,y,100,20);
		
		panel.add(new JLabel("Folio:")).setBounds(x*23,y,70,20);
		panel.add(txtFolio  ).setBounds(x*23+50,y,80,20);
		
		
		panel.add(lblMovimientos).setBounds(x-5,y+=20,180,95);                  panel.add(lblTotales).setBounds(x*13-10,y,180,95);            
		panel.add(new JLabel("Cargo:")).setBounds(x+5,y+=15,50,20);             panel.add(new JLabel("Cargo:")).setBounds(x*13,y,50,20);       
		panel.add(spCargoMov).setBounds(x+65,y,90,20);                       	panel.add(spCargoTotales).setBounds(x*13+60,y,90,20);                 
		panel.add(new JLabel("Abono:")).setBounds(x+5,y+=25,50,20);            	panel.add(new JLabel("Abono:")).setBounds(x*13,y,50,20);      
		panel.add(spAbonoMov).setBounds(x+65,y,90,20);                       	panel.add(spAbonoTotales).setBounds(x*13+60,y,90,20);                 
		panel.add(new JLabel("Diferencia:")).setBounds(x+5,y+=25,70,20);       	panel.add(new JLabel("Diferencia:")).setBounds(x*13,y,70,20); 
		panel.add(spDiferenciaMov).setBounds(x+65,y,90,20);                  	panel.add(spDiferenciaTotales).setBounds(x*13+60,y,90,20);            
		
		panel.add(new JLabel("Concepto:")).setBounds(x-5,y+=35,70,20);
		panel.add(Concepto).setBounds(x-5,y+=15,ancho*7+40,60);
		
		panel.add(scroll).setBounds(x-5,y+=65,ancho*7+40,220);
		
		cont.add(panel);
		
		txaConcepto.setLineWrap(true); 
		txaConcepto.setWrapStyleWord(true);
		
		llamar_render();
		componentes(false);
//		limpiar();
		
//		llenarConfiguracionPolizas();
		
		cmbTipo.addActionListener(opBuscar);
		fhFecha.addPropertyChangeListener(opBusqueda);
		
		agregar(tabla);
		
		
		this.setSize(645, 500);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
	}
	
	public Object[][] get_tabla(){
		return new BuscarTablasModel().configuracion_de_polizas();
	}
	public void llenarConfiguracionPolizas(){
		
		while(tabla.getRowCount()>0){
			modelo.removeRow(0);
		}
		
		Object[][] conf_pol = get_tabla();
		for(Object[] cp : conf_pol){
			modelo.addRow(cp);
		}
	}
	
	private void agregar(final JTable tbl) {
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
	        public void mouseClicked(MouseEvent e) {
	        	if(e.getClickCount() == 1){
	        		
//	        		componentes(false);
	    			int fila = tbl.getSelectedRow();
					
//					txtTipo.setText(tabla_conf.getValueAt(fila, 0).toString());
//					txtNombre.setText(tabla_conf.getValueAt(fila, 1).toString());
//					spRelleno.setValue(Integer.valueOf(tabla_conf.getValueAt(fila, 2).toString().trim()));
//					cmbAsiento_Cont.setSelectedItem((tabla_conf.getValueAt(fila, 3).toString().trim().equals("I"))?"Ingresos" : ((tabla_conf.getValueAt(fila, 3).toString().trim().equals("E"))?"Egresos" : "Varios") );
//					cmbStatus.setSelectedItem(tabla_conf.getValueAt(fila, 4).toString().trim().equals("V")?("Vigente"):("Cancelado"));
	        	}
	        }
        });
    }
	
	ActionListener opGuardarConf = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			if(cmbTipo.isEditable()){
				
					if(!validaCampos().equals("")){
						JOptionPane.showMessageDialog(null, "Los Siguiente Campos Son Requeridos: "+validaCampos(),"Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
						return;
					}else{
							if(JOptionPane.showConfirmDialog(null, "El Registro Existe, ¿Desea Actualizarlo?") == 0){
								guardar();
							}else{
								return;
							}
					}
			}else{
				JOptionPane.showMessageDialog(null, "Para Poder Realizar Un Movimiento En Necesario Seleccionar Editar Un Registro O Generar Uno Nuevo","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
				return;
			}
		}
	};
	
	public void guardar(){
//		if(new GuardarSQL().Guardar_Configuracion_De_Poliza(txtTipo.getText(),txtNombre.getText(),spRelleno.getValue().toString(),cmbAsiento_Cont.getSelectedItem().toString(),cmbStatus.getSelectedItem().toString())){
////			componentes(false);
//			llenarConfiguracionPolizas();
//			JOptionPane.showMessageDialog(null, "La Configuracion De Poliza Se Guardó Exitosamente","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen//aplicara-el-dialogo-icono-6256-32.png"));
//			return;
//		}else{
//			JOptionPane.showMessageDialog(null, "Ocurrió Un Error Al Intentar Guardar","Error",JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen//usuario-icono-eliminar5252-64.png"));
//			return;
//		}
	}
	
	private String validaCampos(){
		String error="";
		
		if(cmbTipo.getSelectedItem().equals(""))error+= "Tipo\n";
//		if(Integer.valueOf(spRelleno.getValue().toString())<0)error+= "El Relleno Debe Ser Positovo\n";
//		if(cmbAsiento_Cont.getSelectedIndex()==0)error+= "Seleccione Un Asiento Contable\n";
				
		return error;
	}
	
	 @SuppressWarnings("unused")
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
		
		this.tabla.getTableHeader().setReorderingAllowed(false) ;

		int largo=80;
		this.tabla.getColumnModel().getColumn(0).setMaxWidth(largo);
		this.tabla.getColumnModel().getColumn(0).setMinWidth(largo);
		this.tabla.getColumnModel().getColumn(1).setMaxWidth(265);
		this.tabla.getColumnModel().getColumn(1).setMinWidth(265);
		this.tabla.getColumnModel().getColumn(2).setMaxWidth(largo);
		this.tabla.getColumnModel().getColumn(2).setMinWidth(largo);
		          
		this.tabla.getColumnModel().getColumn(3).setMaxWidth(largo);
		this.tabla.getColumnModel().getColumn(3).setMinWidth(largo);
		this.tabla.getColumnModel().getColumn(4).setMaxWidth(largo);
		this.tabla.getColumnModel().getColumn(4).setMinWidth(largo);

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
		
		if(fhFecha.getDate() != null && cmbTipo.getSelectedIndex()!=0){
			txtFolio.setText(new BuscarTablasModel().folio_consecutivo_de_poliza(new SimpleDateFormat("dd/MM/yyyy").format(fhFecha.getDate()),cmbTipo.getSelectedItem().toString().toUpperCase().trim()));
      	}else{
      		txtFolio.setText("");
      	}
	}
	
	public static void main(String[] args) {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Polizas().setVisible(true);
		}catch(Exception e){	}		
	}
}
