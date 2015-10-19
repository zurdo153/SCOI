package Cat_Contabilidad;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import Conexiones_SQL.BuscarTablasModel;
import Conexiones_SQL.GuardarSQL;
import Obj_Principal.Componentes;
import Obj_Principal.Obj_Filtro_Dinamico;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Tipos_Y_Folios_De_Polizas extends JFrame{
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JButton btnNuevaConf = new JButton("Nuevo");
	JButton btnGuardarConf = new JButton("Guardar");
	JButton btnEditarConf = new JButton("Editar");
	JButton btnGuardarFolio = new JButton("Guardar");
	JButton btnEditarFolio = new JButton("Editar");
	
	JButton btnLimpiar = new JButton("Limpiar");
	
	JTextField txtTipo = new Componentes().text(new JTextField(), "Tipo", 1, "String");
	JTextField txtNombre 	= new Componentes().text(new JTextField(), "Codigo De Proveedor", 15, "String");
	
	JTextField txtTipoPoliza 	= new Componentes().text(new JTextField(), "Tipo De Poliza", 5, "String");
	JTextField txtMesAnio 		= new Componentes().text(new JTextField(), "Mes Año", 6, "String");
	JTextField txtFolio 		= new Componentes().text(new JTextField(), "Folio", 10, "Int");
	
    SpinnerModel relleno = new SpinnerNumberModel(2015, 0, 2050, 1);
	JSpinner spRelleno = new JSpinner(relleno);
	
	String[] Asiento_Contable = {"Seleccione un Asiento Cont.","Egresos","Ingresos","Varios"};
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbAsiento_Cont =  new JComboBox(Asiento_Contable);
	
	String[] estatus = {"Vigente","Cancelado"};
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbStatus =  new JComboBox(estatus);
	
//	int an =  Calendar.getInstance().get(Calendar.YEAR);
//	SpinnerModel  anio = new SpinnerNumberModel(/*an,an-100,an+100,1*/);
	
	JSpinner spAnio = new JSpinner();
	JComponent editor = new JSpinner.NumberEditor(spRelleno, "###0");
	
	DefaultTableModel modelo_conf = new DefaultTableModel(null,
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
	
	JTable tabla_conf = new JTable(modelo_conf);
	JScrollPane scroll_conf = new JScrollPane(tabla_conf,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
	DefaultTableModel modelo_folios = new DefaultTableModel(null,new String[]{"Tipo","Mes y año","Siguiente folio"}
			){
	     @SuppressWarnings("rawtypes")
		Class[] types = new Class[]{
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
        	 	} 				
 			return false;
 		}
	};
	
	JTable tabla_folios = new JTable(modelo_folios);
	JScrollPane scroll_folios = new JScrollPane(tabla_folios,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);


	public Cat_Tipos_Y_Folios_De_Polizas(){
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/cajas-de-cajas-de-embalaje-de-envio-de-un-archivo-tar-icono-4009-64.png"));
		this.setTitle("Tipos y folios de polizas");
		panel.setBorder(BorderFactory.createTitledBorder("Configuracion de polizas y folios"));	
		
		int x=15,y=20,ancho=80;
		
		panel.add(new JLabel("Tipo:")).setBounds(x,y,50,20);
		panel.add(txtTipo).setBounds(x+30,y,50,20);
		
		panel.add(new JLabel("Nombre:")).setBounds(x+100,y,70,20);
		panel.add(txtNombre  ).setBounds(x+160,y,120,20);
		
		panel.add(new JLabel("Relleno:")).setBounds(x+300,y,70,20);
		panel.add(spRelleno  ).setBounds(x+360,y,50,20);
		
	panel.add(btnNuevaConf  ).setBounds(x+420,y,85,20);
	panel.add(btnGuardarConf  ).setBounds(x+515,y,85,20);
		
		panel.add(new JLabel("Asiento Cont:")).setBounds(x,y+=25,80,20);
		panel.add(cmbAsiento_Cont).setBounds(x+80,y,170,20);
		
		panel.add(new JLabel("Status:")).setBounds(x+270,y,70,20);
		panel.add(cmbStatus  ).setBounds(x+320,y,90,20);
		
	panel.add(btnEditarConf).setBounds(x+420,y,85,20);
	panel.add(btnLimpiar).setBounds(x+515,y,85,20);
		
		panel.add(scroll_conf).setBounds(x,y+=25,ancho*7+40,200);
		
		panel.add(new JLabel("Año:")).setBounds(x,y+=(ancho*3)-30,70,20);
		panel.add(spAnio  ).setBounds(x+40,y,70,20);
		
		panel.add(scroll_folios).setBounds(x,y+=25,ancho*4,200);
		
		panel.add(new JLabel("Tipo:")).setBounds(x+=((ancho*4)+35),y,ancho,20);
		panel.add(txtTipoPoliza).setBounds(x+45,y,ancho,20);
		panel.add(new JLabel("Mes Año:")).setBounds(x-20,y+=25,ancho,20);
		panel.add(txtMesAnio).setBounds(x+45,y,ancho,20);
		panel.add(btnEditarFolio).setBounds(x+ancho*2-35,y,85,20);
		
		panel.add(new JLabel("Folio:")).setBounds(x,y+=25,ancho,20);
		panel.add(txtFolio).setBounds(x+45,y,ancho,20);
		panel.add(btnGuardarFolio).setBounds(x+ancho*2-35,y,85,20);
		
		cont.add(panel);
		
//		spAnio.setEditor(editor);
		spAnio.setValue(2015);
		
		llamar_render();
		componentes(false);
		componentesFolios(false);
		limpiar();
		
		llenarConfiguracionPolizas();
		new Obj_Filtro_Dinamico(tabla_folios,"Mes y año",  spAnio.getValue().toString(),"","", "", "", "", "");
		
		btnNuevaConf.addActionListener(opNuevaConf);
		btnEditarConf.addActionListener(opEditarConf);
		agregar(tabla_conf,"config");
		agregar(tabla_folios,"");
		
		btnGuardarConf.addActionListener(opGuardarConf);
		btnEditarFolio.addActionListener(opEditarFolios);
		btnGuardarFolio.addActionListener(opGuardarFolios);
		
		spAnio.addChangeListener(opAnio);
		
		btnLimpiar.addActionListener(opLimpiar);		
		
		this.setSize(645, 560);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
	}
	
	public boolean existe_conf_poliza(){
		return new BuscarTablasModel().Existe_configuracion_de_poliza(txtNombre.getText().toUpperCase().trim());
	}
	
	public Object[][] get_tabla_conf(){
		return new BuscarTablasModel().configuracion_de_polizas();
	}
	public Object[][] get_tabla_folios(){
		return new BuscarTablasModel().folios_de_polizas();
	}
	public void llenarConfiguracionPolizas(){
		
		while(tabla_conf.getRowCount()>0){
			modelo_conf.removeRow(0);
		}
		
		Object[][] conf_pol = get_tabla_conf();
		for(Object[] cp : conf_pol){
			modelo_conf.addRow(cp);
		}
		
		while(tabla_folios.getRowCount()>0){
			modelo_folios.removeRow(0);
		}
		
		Object[][] folios_pol = get_tabla_folios();
		for(Object[] fp : folios_pol){
			modelo_folios.addRow(fp);
		}
	}
	
	ChangeListener opAnio = new ChangeListener() {
		public void stateChanged(ChangeEvent e) {
			new Obj_Filtro_Dinamico(tabla_folios,"Mes y año",  spAnio.getValue().toString(),"Tipo",txtTipo.getText(), "", "", "", "");
		}
	};
	
	ActionListener opLimpiar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			limpiar();
			txtTipoPoliza.setText("");
			txtMesAnio.setText("");
			txtFolio.setText("");
			new Obj_Filtro_Dinamico(tabla_folios,"Mes y año",  spAnio.getValue().toString(),"Tipo",txtTipo.getText(), "", "", "", "");
		}
	};
	
	
	ActionListener opNuevaConf = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			componentes(true);
			limpiar();
			txtTipo.requestFocus();
		}
	};
	
	private void agregar(final JTable tbl,final String condicion) {
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
	        public void mouseClicked(MouseEvent e) {
	        	if(e.getClickCount() == 1){
	        		
	        		int fila = tbl.getSelectedRow();
	        		
	        		if(condicion.equals("config")){
		        			componentes(false);
			    			
							
							txtTipo.setText(tabla_conf.getValueAt(fila, 0).toString());
							txtNombre.setText(tabla_conf.getValueAt(fila, 1).toString());
							spRelleno.setValue(Integer.valueOf(tabla_conf.getValueAt(fila, 2).toString().trim()));
							cmbAsiento_Cont.setSelectedItem((tabla_conf.getValueAt(fila, 3).toString().trim().equals("I"))?"Ingresos" : ((tabla_conf.getValueAt(fila, 3).toString().trim().equals("E"))?"Egresos" : "Varios") );
							cmbStatus.setSelectedItem(tabla_conf.getValueAt(fila, 4).toString().trim().equals("V")?("Vigente"):("Cancelado"));
							
							new Obj_Filtro_Dinamico(tabla_folios,"Mes y año",  spAnio.getValue().toString(),"Tipo",txtTipo.getText(), "", "", "", "");
					
	        		}else{
	        			
	        			componentesFolios(false);
	        			btnEditarFolio.setEnabled(true);
	        			
	        			txtTipoPoliza.setText(tbl.getValueAt(fila, 0).toString());
	        			txtMesAnio.setText(tbl.getValueAt(fila, 1).toString());
	        			txtFolio.setText(tbl.getValueAt(fila, 2).toString());
	        		}
	        	}
	        }
        });
    }
	
	public void componentesFolios(boolean valor){
		txtTipoPoliza.setEditable(valor);
		txtMesAnio.setEditable(valor);
		txtFolio.setEditable(valor);
		
		btnGuardarFolio.setEnabled(valor);
		btnEditarFolio.setEnabled(valor);
	}
	
	ActionListener opEditarConf = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			if(tabla_conf.getSelectedRow()<0){
//				aviso(seleccione un registro de la tabla)
			}else{
				componentes(true);
				txtTipo.requestFocus();
			}
		}
	};
	
	ActionListener opGuardarConf = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			if(txtTipo.isEditable()){
				
					if(!validaCampos().equals("")){
						JOptionPane.showMessageDialog(null, "Los Siguiente Campos Son Requeridos: "+validaCampos(),"Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
						return;
					}else{
						
						if(isNumeric(spRelleno.getValue().toString())){
							if(existe_conf_poliza()){
									if(JOptionPane.showConfirmDialog(null, "El Registro Existe, ¿Desea Actualizarlo?") == 0){
										guardar();
									}else{
										return;
									}
							}else{
									guardar();
							}
							
						}else{
							JOptionPane.showMessageDialog(null, "El Relleno No Es Un Dato Numerico","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
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
		if(new GuardarSQL().Guardar_Configuracion_De_Poliza(txtTipo.getText(),txtNombre.getText(),spRelleno.getValue().toString(),cmbAsiento_Cont.getSelectedItem().toString(),cmbStatus.getSelectedItem().toString())){
			componentes(false);
			llenarConfiguracionPolizas();
			JOptionPane.showMessageDialog(null, "La Configuracion De Poliza Se Guardó Exitosamente","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen//aplicara-el-dialogo-icono-6256-32.png"));
			return;
		}else{
			JOptionPane.showMessageDialog(null, "Ocurrió Un Error Al Intentar Guardar","Error",JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen//usuario-icono-eliminar5252-64.png"));
			return;
		}
	}
	
	private String validaCampos(){
		String error="";
		
		if(txtTipo.getText().equals(""))error+= "Tipo\n";
		if(txtNombre.getText().equals(""))error+= "Nombre\n";
		if(Integer.valueOf(spRelleno.getValue().toString())<0)error+= "El Relleno Debe Ser Positovo\n";
		if(cmbAsiento_Cont.getSelectedIndex()==0)error+= "Seleccione Un Asiento Contable\n";
				
		return error;
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
	
	ActionListener opEditarFolios = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			componentesFolios(false);
			btnGuardarFolio.setEnabled(true);
			txtFolio.setEditable(true);
			
		}
	};
	
	ActionListener opGuardarFolios = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
				
				if(!txtTipoPoliza.getText().equals("") && !txtFolio.getText().equals("")){
							if( new GuardarSQL().Modificar_Folio_De_Poliza( txtTipoPoliza.getText().trim(), txtMesAnio.getText().trim(), Integer.valueOf(txtFolio.getText().trim()) ) ){
									componentesFolios(false);
									txtTipoPoliza.setText("");
									txtMesAnio.setText("");
									txtFolio.setText("");
									llenarConfiguracionPolizas();
									
									JOptionPane.showMessageDialog(null, "El Folio De Poliza Se Modificó Exitosamente","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen//aplicara-el-dialogo-icono-6256-32.png"));
									return;
							}else{
								JOptionPane.showMessageDialog(null, "No Se Pudo Guardar El Registro","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
								return;
							}
				}else{
					JOptionPane.showMessageDialog(null, "Es Necesario Seleccionar Un Registro De La Tabla De Folios E Ingresar Un Folio En El Campo Habilitado","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen//usuario-de-alerta-icono-4069-64.png"));
					return;
				}
		}
	};
	
	public void componentes(boolean validar){
		txtTipo.setEditable(validar);
		txtNombre.setEditable(validar);
		cmbAsiento_Cont.setEnabled(validar);
		cmbStatus.setEnabled(validar);
		spRelleno.setEnabled(validar);
	}
	
	public void limpiar(){
		txtTipo.setText("");
		txtNombre.setText("");
		cmbAsiento_Cont.setSelectedIndex(0);
		cmbStatus.setSelectedIndex(0);
		
		spRelleno.setValue(2);
		spAnio.setValue(2015);
	}
	
	public void llamar_render(){
		tabla_conf.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",12));
		tabla_conf.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",12));
		tabla_conf.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",12));
		tabla_conf.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",12));
		tabla_conf.getColumnModel().getColumn(4).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",12));
		
		this.tabla_conf.getTableHeader().setReorderingAllowed(false) ;

		int largo=80;
		this.tabla_conf.getColumnModel().getColumn(0).setMaxWidth(largo);
		this.tabla_conf.getColumnModel().getColumn(0).setMinWidth(largo);
		this.tabla_conf.getColumnModel().getColumn(1).setMaxWidth(265);
		this.tabla_conf.getColumnModel().getColumn(1).setMinWidth(265);
		this.tabla_conf.getColumnModel().getColumn(2).setMaxWidth(largo);
		this.tabla_conf.getColumnModel().getColumn(2).setMinWidth(largo);
		          
		this.tabla_conf.getColumnModel().getColumn(3).setMaxWidth(largo);
		this.tabla_conf.getColumnModel().getColumn(3).setMinWidth(largo);
		this.tabla_conf.getColumnModel().getColumn(4).setMaxWidth(largo);
		this.tabla_conf.getColumnModel().getColumn(4).setMinWidth(largo);


		
		tabla_folios.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",12));
		tabla_folios.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",12));
		tabla_folios.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",12));
		
		this.tabla_folios.getTableHeader().setReorderingAllowed(false) ;

		largo = 102;
		this.tabla_folios.getColumnModel().getColumn(0).setMaxWidth(largo);
		this.tabla_folios.getColumnModel().getColumn(0).setMinWidth(largo);
		this.tabla_folios.getColumnModel().getColumn(1).setMaxWidth(largo);
		this.tabla_folios.getColumnModel().getColumn(1).setMinWidth(largo);
		this.tabla_folios.getColumnModel().getColumn(2).setMaxWidth(largo);
		this.tabla_folios.getColumnModel().getColumn(2).setMinWidth(largo);
		          
	}
	
	public static void main(String[] args) {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Tipos_Y_Folios_De_Polizas().setVisible(true);
		}catch(Exception e){	}		
	}
}
