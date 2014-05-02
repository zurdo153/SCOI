package Cat_Lista_de_Raya;

import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.BuscarTablasModel;
import Conexiones_SQL.Connexion;
import Obj_Lista_de_Raya.Obj_Alimentacion_De_Vacaciones;

import com.toedter.calendar.JDateChooser;

@SuppressWarnings("serial")
public class Cat_Alimentacion_De_Vacaciones extends JFrame {
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
//	campos de datos de enpleado	
	JLabel lblMargenEmpleado = new JLabel();
	JTextField txtFolioVacaciones = new JTextField();
	JTextField txtFechaIngreso = new JTextField();
	JTextField txtFechaIngresoIMSS = new JTextField();
	
	JButton btnBuscar = new JButton(new ImageIcon("Iconos/zoom_icon&16.png"));
	JButton btnNuevo = new JButton("Nuevo");
	
	JTextField txtFolioEmpleado = new JTextField();
	JTextField txtEmpleado = new JTextField();
	JTextField txtEstablecimiento = new JTextField();
	JTextField txtPuesto = new JTextField();
	
	JTextField txtSalarioDiarioIn= new JTextField();
	JTextField txtGrupoDeVacaciones = new JTextField();
	JTextField txtProximasVacaciones = new JTextField();
	
	JButton btnFoto = new JButton("");
	
	String fileFoto = System.getProperty("user.dir")+"/Iconos/Un.JPG";
    ImageIcon tmpIconAuxFoto = new ImageIcon(fileFoto);
    
//	campo de vacaciones de empleados
	JLabel lblMargenVacaciones = new JLabel();
	
	JDateChooser fechaInicio = new JDateChooser();
	JDateChooser fechaFin = new JDateChooser();
	
	JCheckBox chbStatus = new JCheckBox("Status",true);
	
	JTextField txtVacaciones = new JTextField();
	JTextField txtPrima = new JTextField();
	JTextField txtSueldoSemana = new JTextField();
	JTextField txtInfonavit = new JTextField();
	JTextField txtPrestamo = new JTextField();
	JTextField txtCorteCaja = new JTextField();
	JTextField txtFSodas = new JTextField();
	JTextField txtPension = new JTextField();
	
	JLabel lblSigno = new JLabel("Pagar $");
	JLabel lblTotal = new JLabel("00000.00");
	
	JButton btnDeshacer = new JButton("Deshacer");
	JButton btnCalcular = new JButton("Calcular");
	JButton btnGuardar = new JButton("Guardar");
	
//	campo de tabla de vacaciones
	JLabel lblMargenTabla = new JLabel();
	
	DecimalFormat DF = new DecimalFormat("#0.00");
	
    public static DefaultTableModel tabla_model = new DefaultTableModel(
    		null,	new String[]{"Folio Vacaciones","Fecha Inicio","Fecha Final","Años"}){
                    @SuppressWarnings("rawtypes")
                    Class[] types = new Class[]{
                               java.lang.Object.class, 
                               java.lang.Object.class, 
                               java.lang.Object.class,  
                               java.lang.Object.class
                };
                    @SuppressWarnings({ "unchecked", "rawtypes" })
					public Class getColumnClass(int columnIndex) {
                            return types[columnIndex];
                    }
                public boolean isCellEditable(int fila, int columna){
                            switch(columna){
                                    case 0  : return false; 
                                    case 1  : return false; 
                                    case 2  : return false; 
                                    case 3  : return false; 
                            }
                             return false;
                     }
            };
            
        	JTable tabla_vacaciones_disfrutadas = new JTable(tabla_model);
        	JScrollPane panelScroll = new JScrollPane(tabla_vacaciones_disfrutadas);

    Border blackline;
    
//    variables alimentadas al calcular y destinadas para validar 
//    que los campos no fueron modificados antes de guardar las vacaciones
    float vacaciones = 0;
	float primaVac = 0;
	float sueldoSem = 0;
	float corteCaja = 0;
	float prestamo = 0;
	float pension = 0;
	float infonavit = 0;
	float fuenteSodas = 0;
    
	public Cat_Alimentacion_De_Vacaciones(){
		
//		efectos de bordes
		blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
		
		lblSigno.setFont(new Font("arial", Font.BOLD, 26));
		lblTotal.setFont(new Font("arial", Font.BOLD, 26));
		
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/sun_icon&16.png"));
		this.setTitle("Alimentacion De Vacaciones");
		
		this.lblMargenEmpleado.setBorder(BorderFactory.createTitledBorder(blackline,"Datos Del Empleado"));
		this.lblMargenVacaciones.setBorder(BorderFactory.createTitledBorder(blackline,"Asignacion De Vacaciones"));
		this.lblMargenTabla.setBorder(BorderFactory.createTitledBorder(blackline,"Tabla De Vacaciones Pasadas"));
		
		int y=20;
//		campos de datos de empleado
		panel.add(lblMargenEmpleado).setBounds(3, 0, 675, 205);
		panel.add(new JLabel("Folio Vacaciones: ")).setBounds(20, y, 110, 20);
		panel.add(txtFolioVacaciones).setBounds(130, y, 60, 20);
		
		panel.add(btnBuscar).setBounds(200, y, 30, 20);
		panel.add(btnNuevo).setBounds(240, y, 80, 20);
		panel.add(btnDeshacer).setBounds(330, y, 80, 20);
		
		panel.add(new JLabel("Empleado: ")).setBounds(20, y+=25, 110, 20);
		panel.add(txtFolioEmpleado).setBounds(130, y, 50, 20);
		panel.add(txtEmpleado).setBounds(181, y, 319, 20);
		
		panel.add(new JLabel("Establecimiento: ")).setBounds(20, y+=25, 110, 20);
		panel.add(txtEstablecimiento).setBounds(130, y, 190, 20);
		
		panel.add(new JLabel("Puesto: ")).setBounds(20, y+=25, 60, 20);
		panel.add(txtPuesto).setBounds(130, y, 370, 20);
		
		panel.add(new JLabel("Fecha Ingreso: ")).setBounds(20, y+=25, 130, 20);
		panel.add(txtFechaIngreso).setBounds(150, y, 100, 20);
		
		panel.add(new JLabel("Grupo De Vacaciones: ")).setBounds(290, y, 140, 20);
		panel.add(txtGrupoDeVacaciones).setBounds(400, y, 100, 20);
		
		panel.add(new JLabel("Fecha Ingreso IMSS: ")).setBounds(20, y+=25, 120, 20);
		panel.add(txtFechaIngresoIMSS).setBounds(150, y, 100, 20);
		
		panel.add(new JLabel("Proximas Vacaciones: ")).setBounds(290, y, 140, 20);
		panel.add(txtProximasVacaciones).setBounds(400, y, 100, 20);
		
		panel.add(new JLabel("Salario Diario Integrado: ")).setBounds(20, y+=25, 140, 20);
		panel.add(txtSalarioDiarioIn).setBounds(150, y, 100, 20);
		
		panel.add(btnFoto).setBounds(510, 30, 150, 150);

//		campo de vacaciones de empleados
		panel.add(lblMargenVacaciones).setBounds(3, 205, 675, 150);
		
		panel.add(new JLabel("Fecha Inicial: ")).setBounds(20, y+=55, 140, 20);
		panel.add(fechaInicio).setBounds(125, y, 100, 20);
		
		panel.add(new JLabel("Fecha Final: ")).setBounds(235, y, 140, 20);
		panel.add(fechaFin).setBounds(340, y, 100, 20);

		panel.add(chbStatus).setBounds(560, y, 80, 20);
		
		panel.add(new JLabel("Vacaciones: ")).setBounds(20, y+=25, 140, 20);
		panel.add(txtVacaciones).setBounds(125, y, 100, 20);
		
		panel.add(new JLabel("Prima Vacacional: ")).setBounds(235, y, 140, 20);
		panel.add(txtPrima).setBounds(340, y, 100, 20);
		
		panel.add(new JLabel("Infonavit: ")).setBounds(450, y, 100, 20);
		panel.add(txtInfonavit).setBounds(560, y, 100, 20);
		
		panel.add(new JLabel("Sueldo Semanal: ")).setBounds(20, y+=25, 140, 20);
		panel.add(txtSueldoSemana).setBounds(125, y, 100, 20);
		
		panel.add(new JLabel("Corte De Caja: ")).setBounds(235, y, 140, 20);
		panel.add(txtCorteCaja).setBounds(340, y, 100, 20);
		
		panel.add(new JLabel("Fuente De Sodas: ")).setBounds(450, y, 120, 20);
		panel.add(txtFSodas).setBounds(560, y, 100, 20);

		panel.add(new JLabel("Prestamo: ")).setBounds(20, y+=25, 140, 20);
		panel.add(txtPrestamo).setBounds(125, y, 100, 20);
		
		panel.add(new JLabel("Pension Alimenticia: ")).setBounds(235, y, 140, 20);
		panel.add(txtPension).setBounds(340, y, 100, 20);
		
		panel.add(lblSigno).setBounds(450, y, 450, 50);
		panel.add(lblTotal).setBounds(555, y, 450, 50);
		
		panel.add(btnCalcular).setBounds(20, y+=25, 90, 20);
		panel.add(btnGuardar).setBounds(125, y, 90, 20);
		
//		campo de tabla de vacaciones
		panel.add(lblMargenTabla).setBounds(3, 355, 675, 150);
		panel.add(panelScroll).setBounds(83, 370, 515, 125);
		
        Icon iconoFoto = new ImageIcon(tmpIconAuxFoto.getImage().getScaledInstance(btnFoto.getWidth(), btnFoto.getHeight(), Image.SCALE_DEFAULT));
        btnFoto.setIcon(iconoFoto);
		
        init_tabla();
        
        fechaInicio.setEnabled(false);
        fechaFin.setEnabled(false);
        
        txtFolioVacaciones.setEditable(false);
        txtFolioEmpleado.setEditable(false);
        txtEmpleado.setEditable(false);
        txtEstablecimiento.setEditable(false);
        txtPuesto.setEditable(false);
        txtFechaIngreso.setEditable(false);
        txtFechaIngresoIMSS.setEditable(false);
        txtSalarioDiarioIn.setEditable(false);
        txtGrupoDeVacaciones.setEditable(false);
        txtProximasVacaciones.setEditable(false);
        
        btnCalcular.setEnabled(false);
        btnGuardar.setEnabled(false);
        
        btnBuscar.addActionListener(opBuscar);
        btnNuevo.addActionListener(opNuevo);
        btnDeshacer.addActionListener(opDeshacer);
        btnCalcular.addActionListener(opCalcular);
        btnGuardar.addActionListener(opGuardar);
        
//    	FUNCION PARA AGREGAR UNA ACCION AL SELECCIONAR UNA FECHA
        fechaInicio.getDateEditor().addPropertyChangeListener(opCalcularAutomaticoConFechaIn);

		cont.add(panel);
		this.setSize(700,545);
	}
	
	public void init_tabla(){
        this.tabla_vacaciones_disfrutadas.getTableHeader().setReorderingAllowed(false) ;
        
               int x=128;
               
                this.tabla_vacaciones_disfrutadas.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                
                this.tabla_vacaciones_disfrutadas.getColumnModel().getColumn(0).setMaxWidth(x);
                this.tabla_vacaciones_disfrutadas.getColumnModel().getColumn(0).setMinWidth(x);
                this.tabla_vacaciones_disfrutadas.getColumnModel().getColumn(1).setMaxWidth(x);
                this.tabla_vacaciones_disfrutadas.getColumnModel().getColumn(1).setMinWidth(x);
                this.tabla_vacaciones_disfrutadas.getColumnModel().getColumn(2).setMaxWidth(x);
                this.tabla_vacaciones_disfrutadas.getColumnModel().getColumn(2).setMinWidth(x);
                this.tabla_vacaciones_disfrutadas.getColumnModel().getColumn(3).setMaxWidth(x);
                this.tabla_vacaciones_disfrutadas.getColumnModel().getColumn(3).setMinWidth(x);
                
        TableCellRenderer render = new TableCellRenderer() { 
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, 
                boolean hasFocus, int row, int column) { 
                        
                        Component componente = null;
                
                        switch(column){
                                case 0: 
                                        componente = new JLabel(value == null? "": value.toString());
                                        if(row%2==0){
                                                ((JComponent) componente).setOpaque(true); 
                                                componente.setBackground(new java.awt.Color(177,177,177));
                                        }
                                        ((JLabel) componente).setHorizontalAlignment(SwingConstants.RIGHT);
                                        break;
                                case 1:
                                        componente = new JLabel(value == null? "": value.toString());
                                        if(row%2==0){
                                                ((JComponent) componente).setOpaque(true); 
                                                componente.setBackground(new java.awt.Color(177,177,177));        
                                        }
                                        ((JLabel) componente).setHorizontalAlignment(SwingConstants.LEFT);
                                        break;
                                case 2: 
                                        componente = new JLabel(value == null? "": value.toString());
                                        if(row%2==0){
                                                ((JComponent) componente).setOpaque(true); 
                                                componente.setBackground(new java.awt.Color(177,177,177));        
                                        }
                                        ((JLabel) componente).setHorizontalAlignment(SwingConstants.CENTER);
                                        break;
                                case 3: 
                                        componente = new JLabel(value == null? "": value.toString());
                                        if(row%2==0){
                                                ((JComponent) componente).setOpaque(true); 
                                                componente.setBackground(new java.awt.Color(177,177,177));        
                                        }
                                        ((JLabel) componente).setHorizontalAlignment(SwingConstants.CENTER);
                                        break;
                        }
                        return componente;
                } 
        }; 
        for(int i=0; i<tabla_vacaciones_disfrutadas.getColumnCount(); i++){
                this.tabla_vacaciones_disfrutadas.getColumnModel().getColumn(i).setCellRenderer(render); 
        }
}
	
	ActionListener opNuevo = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			new Filtro_Empleado_Para_Vacaciones_Nuevas().setVisible(true);
		}
	};
	
	ActionListener opBuscar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			new Filtro_Ultimas_Vacaciones_De_Empleado().setVisible(true);
		}
	};
	
	PropertyChangeListener opCalcularAutomaticoConFechaIn = new PropertyChangeListener() {
  	  public void propertyChange(PropertyChangeEvent e) {
  	            if ("date".equals(e.getPropertyName())) {
  	            	
	  	            	if(fechaInicio.getDate() != null){
	  	            		
		  	            	Obj_Alimentacion_De_Vacaciones calculo = new Obj_Alimentacion_De_Vacaciones().buscar_vacaciones(Integer.valueOf(txtFolioEmpleado.getText()),fechaInicio.getDate());
		
//  	                System.out.println(e.getPropertyName()+ ": " + (Date) e.getNewValue());
//  calcula rango de fecha-------------------------------------------------------------------------------------------------
		  	                int rangoEnDias = Integer.valueOf(calculo.getFecha_final());
		  	                int tiempo =(24 * 60 * 60 * 1000);
		  	                long dias = rangoEnDias * tiempo;
		  	                
		  	                long tiempoActual = fechaInicio.getDate().getTime();
		  	                Date fechaLimite = new Date(tiempoActual + dias);
// -------------------------------------------------------------------------------------------------------------------------
		  	                fechaFin.setDate(fechaLimite);
		  	                
		  	                txtVacaciones.setText(DF.format(calculo.getVacaciones())+"");
		  	                txtPrima.setText(DF.format(calculo.getPrima_vacacional())+"");
		  	                txtSueldoSemana.setText(DF.format(calculo.getSueldo_semana())+"");
		  	                txtCorteCaja.setText(DF.format(calculo.getCorte_de_caja())+"");
		  	                txtPrestamo.setText(DF.format(calculo.getPrestamo())+"");
		  	                txtPension.setText(DF.format(calculo.getPension_alimenticia())+"");
		  	                txtInfonavit.setText(DF.format(calculo.getInfonavit())+"");
		  	                txtFSodas.setText(DF.format(calculo.getFuente_de_sodas())+"");
		  	            	
		  	                btnCalcular.setEnabled(true);
	  	            	}
  	            }
  	  		}
  	    };
	
  	  ActionListener opCalcular = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if(validaCampos() != ""){
					JOptionPane.showMessageDialog(null, "los siguientes campos son requeridos:\n"+validaCampos(), "Error al guardar registro", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
					return;
				}else{

				     vacaciones = Float.valueOf(txtVacaciones.getText());
			  	     primaVac = Float.valueOf(txtPrima.getText());
			  	     sueldoSem = Float.valueOf(txtSueldoSemana.getText());
			  	     corteCaja = Float.valueOf(txtCorteCaja.getText());
			  	     prestamo = Float.valueOf(txtPrestamo.getText());
			  	     pension = Float.valueOf(txtPension.getText());
			  	     infonavit = Float.valueOf(txtInfonavit.getText());
			  	     fuenteSodas = Float.valueOf(txtFSodas.getText());
			  	        
			  	     float total = ((vacaciones+primaVac+sueldoSem)-(corteCaja+prestamo+pension+infonavit+fuenteSodas));
			  	  	
			  	     lblTotal.setText(DF.format(total)+"");
			  	     
			  	     btnGuardar.setEnabled(true);
			  	     btnCalcular.setEnabled(false);
				}
			}
		};
		
  		ActionListener opDeshacer = new ActionListener() {
  			public void actionPerformed(ActionEvent arg0) {
  				
  				btnBuscar.setEnabled(true);
  		        btnNuevo.setEnabled(true);

  		        limpiarPantalla();
  			}
  		};
  		
  		ActionListener opGuardar = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(txtFolioVacaciones.getText().equals("")){
					JOptionPane.showMessageDialog(null, "El folio es requerido \n", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
				}else{		
					
					Obj_Alimentacion_De_Vacaciones vacaciones = new Obj_Alimentacion_De_Vacaciones();
					
//					checar busqueda, se le deve estar mandando el folio de las vacaciones
					if(new Obj_Alimentacion_De_Vacaciones().buscar_vacaciones_para_update(Integer.valueOf(txtFolioVacaciones.getText()))==true){
						
						if(JOptionPane.showConfirmDialog(null, "El registro existe, ¿desea actualizarlo?") == 0){
							if(validaCamposAlGuardar()!="") {
								JOptionPane.showMessageDialog(null, "Los siguientes campos fueron modificados despues de calcularlos:\n "+validaCamposAlGuardar()+"Para guardar las vacaciones primero debe calcularlas", "Error al guardar registro", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
								return;
							}else{

								vacaciones.setFecha_inicio(new SimpleDateFormat("dd/MM/yyyy").format(fechaInicio.getDate()));
								vacaciones.setFecha_final(new SimpleDateFormat("dd/MM/yyyy").format(fechaFin.getDate()));
								
								vacaciones.setVacaciones(Float.valueOf(txtVacaciones.getText()));
								vacaciones.setPrima_vacacional(Float.valueOf(txtPrima.getText()));
								vacaciones.setInfonavit(Float.valueOf(txtInfonavit.getText()));
								vacaciones.setSueldo_semana(Float.valueOf(txtSueldoSemana.getText()));
								vacaciones.setCorte_de_caja(Float.valueOf(txtCorteCaja.getText()));
								vacaciones.setFuente_de_sodas(Float.valueOf(txtFSodas.getText()));
								vacaciones.setPrestamo(Float.valueOf(txtPrestamo.getText()));
								vacaciones.setPension_alimenticia(Float.valueOf(txtPension.getText()));
								vacaciones.setTotal(Float.valueOf(lblTotal.getText()));
								vacaciones.setStatus(chbStatus.isSelected());
								
//							System.out.println("estos que es "+new BuscarSQL().trae_fecha());
			  	             
			  	                
				  	             try {
									Date fecha_hoy = new SimpleDateFormat("dd/MM/yyyy").parse(new BuscarSQL().trae_fecha());
									
									 int tiempo =(24 * 60 * 60 * 1000);
									long tiempoDB = fecha_hoy.getTime();
					  	            long tiempocalendario = fechaInicio.getDate().getTime();
					  	              
					  	            long diferienciaEnDias = tiempoDB-tiempocalendario;
					  	            long margen_en_dias = diferienciaEnDias/tiempo;
//					  	            System.out.println("estos que es "+margen_en_dias);
					  	            
					  	            if(margen_en_dias <= 15){
					  	            	if(vacaciones.actualizar(Integer.valueOf(txtFolioVacaciones.getText()))){
											limpiarPantalla();
											btnBuscar.setEnabled(true);
									        btnNuevo.setEnabled(true);
									        btnGuardar.setEnabled(false);
											JOptionPane.showMessageDialog(null,"El registró se actualizó de forma segura","Aviso",JOptionPane.INFORMATION_MESSAGE);
											return;
										}else{
											limpiarPantalla();
											btnBuscar.setEnabled(true);
									        btnNuevo.setEnabled(true);
									        btnGuardar.setEnabled(false);
											JOptionPane.showMessageDialog(null,"Error al intentar actualizar los datos","Aviso",JOptionPane.ERROR_MESSAGE);
											return;
										}
					  	            }else{
					  	            	JOptionPane.showMessageDialog(null,"El registro no se puede modificar,\nya que pasaron 15 dias de averse\ngenerado estas vacaciones","Aviso",JOptionPane.ERROR_MESSAGE);
										return;
					  	            }
								} catch (ParseException e) {
									e.printStackTrace();
								}
							}
						}else{
							return;
						}
					}else{
						
						if(validaCamposAlGuardar()!="") {
							JOptionPane.showMessageDialog(null, "Los siguientes campos fueron modificados despues de calcularlos:\n "+validaCamposAlGuardar()+"Para guardar las vacaciones primero debe calcularlas", "Error al guardar registro", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
							return;
						}else{
							
							vacaciones.setFolio_empleado(Integer.valueOf(txtFolioEmpleado.getText()));
							vacaciones.setFecha_inicio(new SimpleDateFormat("dd/MM/yyyy").format(fechaInicio.getDate()));
							vacaciones.setFecha_final(new SimpleDateFormat("dd/MM/yyyy").format(fechaFin.getDate()));
							vacaciones.setAnios_a_disfrutar(Integer.valueOf(txtProximasVacaciones.getText()));
							
							vacaciones.setVacaciones(Float.valueOf(txtVacaciones.getText()));
							vacaciones.setPrima_vacacional(Float.valueOf(txtPrima.getText()));
							vacaciones.setInfonavit(Float.valueOf(txtInfonavit.getText()));
							vacaciones.setSueldo_semana(Float.valueOf(txtSueldoSemana.getText()));
							vacaciones.setCorte_de_caja(Float.valueOf(txtCorteCaja.getText()));
							vacaciones.setFuente_de_sodas(Float.valueOf(txtFSodas.getText()));
							vacaciones.setPrestamo(Float.valueOf(txtPrestamo.getText()));
							vacaciones.setPension_alimenticia(Float.valueOf(txtPension.getText()));
							vacaciones.setTotal(Float.valueOf(lblTotal.getText()));
							vacaciones.setStatus(chbStatus.isSelected());
							
							if(vacaciones.guardar_vacaciones_calculadas()){
								limpiarPantalla();
								btnBuscar.setEnabled(true);
						        btnNuevo.setEnabled(true);
						        btnGuardar.setEnabled(false);
								JOptionPane.showMessageDialog(null,"El registro se guardó de forma segura","Aviso",JOptionPane.INFORMATION_MESSAGE);
								return;
							}else{
								limpiarPantalla();
								btnBuscar.setEnabled(true);
						        btnNuevo.setEnabled(true);
						        btnGuardar.setEnabled(false);
								JOptionPane.showMessageDialog(null, "Ocurrió un problema al almacenar el empleado", "Error", JOptionPane.ERROR_MESSAGE);
								return;
							}
						}
					}
				}	
			}
		};
		
  		private String validaCampos(){
  			String error="";
  			String fechaInNull = fechaInicio.getDate()+"";
  			String fechaFinNull = fechaFin.getDate()+"";
  			
  			if(fechaInNull.equals("null"))error+= "Fecha Inicial\n";	
  			if(fechaFinNull.equals("null"))error += "Fecha Final\n";
  			
  			if(txtVacaciones.getText().equals("")) 	error+= "Vacaciones\n";
  			if(txtPrima.getText().equals("")) 		error+= "Prima Vacacional\n";
  			if(txtInfonavit.getText().equals(""))	error+= "Ifonavit\n";
  			if(txtSueldoSemana.getText().equals(""))error+= "Sueldo Semana\n";
  			if(txtCorteCaja.getText().equals(""))	error+= "Corte De Caja\n";
  			if(txtFSodas.getText().equals("")) 		error+= "Fuente De Sodas\n";
  			if(txtPrestamo.getText().equals("")) 	error+= "Prestamo\n";
  			if(txtPension.getText().equals("")) 	error+= "Pension Alimenticia\n";
  			
  			return error;
  		}
  		
  		private String validaCamposAlGuardar(){
  			String error="";
  			String fechaInNull = fechaInicio.getDate()+"";
  			String fechaFinNull = fechaFin.getDate()+"";
  			
  			if(fechaInNull.equals("null"))error+= "Fecha Inicial\n";	
  			if(fechaFinNull.equals("null"))error += "Fecha Final\n";
  			
  			if(Float.valueOf(txtVacaciones.getText()) != vacaciones) 	error+= "Vacaciones\n";
  			if(Float.valueOf(txtPrima.getText()) != primaVac) 			error+= "Prima Vacacional\n";
  			if(Float.valueOf(txtInfonavit.getText()) != infonavit) 		error+= "Ifonavit\n";
  			if(Float.valueOf(txtSueldoSemana.getText()) != sueldoSem) 	error+= "Sueldo Semana\n";
  			if(Float.valueOf(txtCorteCaja.getText()) != corteCaja) 		error+= "Corte De Caja\n";
  			if(Float.valueOf(txtFSodas.getText()) != fuenteSodas) 		error+= "Fuente De Sodas\n";
  			if(Float.valueOf(txtPrestamo.getText()) != prestamo) 		error+= "Prestamo\n";
  			if(Float.valueOf(txtPension.getText()) != pension)  		error+= "Pension Alimenticia\n";
  			
  			return error;
  		}
	
  		public void limpiarPantalla(){
  			
  			Icon iconoFoto = new ImageIcon(tmpIconAuxFoto.getImage().getScaledInstance(btnFoto.getWidth(), btnFoto.getHeight(), Image.SCALE_DEFAULT));
  	        btnFoto.setIcon(iconoFoto);
  			
  			txtFolioVacaciones.setText("");
  	        txtFolioEmpleado.setText("");
  	        txtEmpleado.setText("");
  	        txtEstablecimiento.setText("");
  	        txtPuesto.setText("");
  	        txtFechaIngreso.setText("");
  	        txtFechaIngresoIMSS.setText("");
  	        txtSalarioDiarioIn.setText("");
  	        txtGrupoDeVacaciones.setText("");
  	        txtProximasVacaciones.setText("");

  	        fechaInicio.setDate(null);
  	        fechaFin.setDate(null);     
  	        txtVacaciones.setText("");
  	        txtPrima.setText("");
  	        txtSueldoSemana.setText("");
  	        txtCorteCaja.setText("");
  	        txtPrestamo.setText("");
  	        txtPension.setText("");
  	        txtInfonavit.setText("");
  	        txtFSodas.setText("");
  	        
  	        fechaInicio.setEnabled(false);
  	        btnCalcular.setEnabled(false);
            btnGuardar.setEnabled(false);
            
            lblTotal.setText("00000.00");
            
            vacaciones = 0;
        	primaVac = 0;
        	sueldoSem = 0;
        	corteCaja = 0;
        	prestamo = 0;
        	pension = 0;
        	infonavit = 0;
        	fuenteSodas = 0;
            
            while(tabla_vacaciones_disfrutadas.getRowCount()>0){
                tabla_model.removeRow(0);
            }
  		}
  		
 //Filtro Empleado para buscar y ediatar sus ultimas vacacioenes----------------------------------------------------------------------------
  		public class Filtro_Ultimas_Vacaciones_De_Empleado extends JDialog{
  			
  			Container cont = getContentPane();
  			JLayeredPane campo = new JLayeredPane();
  			
  			Connexion con = new Connexion();
  			
  			DefaultTableModel model = new DefaultTableModel(0,3){
  				public boolean isCellEditable(int fila, int columna){
  					if(columna < 0)
  						return true;
  					return false;
  				}
  			};
  			JTable tabla = new JTable(model);
  			
  			@SuppressWarnings("rawtypes")
  			private TableRowSorter trsfiltro;
  			
  			JLabel lblBuscar = new JLabel("BUSCAR : ");
  			JTextField txtBuscar = new JTextField();
  			
  			@SuppressWarnings({ "rawtypes", "unchecked" })
  			public Filtro_Ultimas_Vacaciones_De_Empleado()	{
  				this.setModal(true);
  				this.setTitle("Filtro Empleados");
  				txtBuscar.addKeyListener(new KeyAdapter() { 
  					public void keyReleased(final KeyEvent e) { 
  		                filtro(); 
  		            } 
  		        });
  			
  				trsfiltro = new TableRowSorter(model); 
  				tabla.setRowSorter(trsfiltro);  
  				
  				campo.add(lblBuscar).setBounds(30,30,70,20);
  				campo.add(txtBuscar).setBounds(95,30,215,20);
  				campo.add(getPanelTabla()).setBounds(10,70,365,450);
  				
  				cont.add(campo);
  				agregar(tabla);
  				
  				this.setSize(390,570);
  				this.setResizable(false);
  				this.setLocationRelativeTo(null);
  				this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
  				
  				tabla.addKeyListener(seleccionEmpleadoconteclado);
  				
  			}
  			private void agregar(final JTable tbl) {
  		        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
  			        public void mouseClicked(MouseEvent e) {
  			        	if(e.getClickCount() == 2){
  			        		
  			        		int fila = tabla.getSelectedRow();
  			    			String folio =  tabla.getValueAt(fila, 0).toString().trim();
  			    			dispose();
  			    			
  			    			llenarDatosEmpleado(Integer.valueOf(folio));
  			        	}
  			        }
  		        });
  		    }

  			public void llenarDatosEmpleado(int folio){
  				
				try {
					 fechaInicio.setEnabled(true);
	  				 
		  				Obj_Alimentacion_De_Vacaciones vacaciones = new Obj_Alimentacion_De_Vacaciones().buscar_vacaciones_guardadas(folio);
						
		  	            while(tabla_vacaciones_disfrutadas.getRowCount()>0){
		  	                tabla_model.removeRow(0);
		  	        }
		  	        
		  	        Object [][] lista_tabla = new BuscarTablasModel().tabla_de_vacaciones_disfrutadas(folio);
		  	        String[] fila = new String[4];
		  	                for(int i=0; i<lista_tabla.length; i++){
		  	                        fila[0] = lista_tabla[i][0]+"";
		  	                        fila[1] = lista_tabla[i][1]+"";
		  	                        fila[2] = lista_tabla[i][2]+"";
		  	                        fila[3] = lista_tabla[i][3]+"";
		  	                        tabla_model.addRow(fila);
		  	                }
		  	                
		  				txtFolioVacaciones.setText(vacaciones.getFolio_vacaciones()+"");
		  				txtFolioEmpleado.setText(vacaciones.getFolio_empleado()+"");
		  				txtEmpleado.setText(vacaciones.getEmpleado());
		  				txtEstablecimiento.setText(vacaciones.getEstablecimiento());
		  				txtPuesto.setText(vacaciones.getPuesto());
		  				txtFechaIngreso.setText(vacaciones.getFecha_ingreso());
		  				txtFechaIngresoIMSS.setText(vacaciones.getFecha_ingreso_imss());
		  				txtSalarioDiarioIn.setText(vacaciones.getSalario_diario_integrado()+"");
		  				txtGrupoDeVacaciones.setText(vacaciones.getGrupo_vacacional());
		  				txtProximasVacaciones.setText(vacaciones.getProximas_vacaciones()+"");
		  				
					Date dateIn = new SimpleDateFormat("dd/MM/yyyy").parse(vacaciones.getFecha_inicio());
					fechaInicio.setDate(dateIn);
					
	  				Date dateFin = new SimpleDateFormat("dd/MM/yyyy").parse(vacaciones.getFecha_final());
	  				fechaFin.setDate(dateFin);
	  				
	  				txtVacaciones.setText(vacaciones.getVacaciones()+"");
	  				txtPrima.setText(vacaciones.getPrima_vacacional()+"");
	  				txtInfonavit.setText(vacaciones.getInfonavit()+"");
	  				txtSueldoSemana.setText(vacaciones.getSueldo_semana()+"");
	  				txtCorteCaja.setText(vacaciones.getSueldo_semana()+"");
	  				txtFSodas.setText(vacaciones.getFuente_de_sodas()+"");
	  				txtPrestamo.setText(vacaciones.getPrestamo()+"");
	  				txtPension.setText(vacaciones.getPension_alimenticia()+"");
	  				lblTotal.setText(vacaciones.getTotal()+"");
	  				chbStatus.setSelected(vacaciones.isStatus());
	  				
	  				ImageIcon tmpIconDefault = new ImageIcon(System.getProperty("user.dir")+"/tmp/tmp.jpg");
	  		        Icon iconoDefault = new ImageIcon(tmpIconDefault.getImage().getScaledInstance(btnFoto.getWidth(), btnFoto.getHeight(), Image.SCALE_DEFAULT));
	  		        btnFoto.setIcon(iconoDefault);
	  				
				} catch (ParseException e) {
					e.printStackTrace();
				}
  				
  		         btnBuscar.setEnabled(false);
  		         btnNuevo.setEnabled(false);
  			}
  			
  			@SuppressWarnings("unchecked")
  			public void filtro() { 
  					trsfiltro.setRowFilter(RowFilter.regexFilter(txtBuscar.getText().toUpperCase().trim(), 1));
  			}  
  			private JScrollPane getPanelTabla()	{		
  				new Connexion();
  				
  				DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
  				tcr.setHorizontalAlignment(SwingConstants.CENTER);
  				
  				tabla.getColumnModel().getColumn(0).setCellRenderer(tcr);
  				
  				tabla.getColumnModel().getColumn(0).setHeaderValue("Folio");
  				tabla.getColumnModel().getColumn(0).setMaxWidth(70);
  				tabla.getColumnModel().getColumn(0).setMinWidth(70);
  				tabla.getColumnModel().getColumn(1).setHeaderValue("Empleado");
  				tabla.getColumnModel().getColumn(1).setMaxWidth(185);
  				tabla.getColumnModel().getColumn(1).setMinWidth(185);
  				tabla.getColumnModel().getColumn(2).setHeaderValue("Establecimiento");
  				tabla.getColumnModel().getColumn(2).setMaxWidth(100);
  				tabla.getColumnModel().getColumn(2).setMinWidth(100);
  				
  				TableCellRenderer render = new TableCellRenderer() 
  				{ 
  					public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, 
  					boolean hasFocus, int row, int column) { 
  						Component componente = null;
  						
  						componente = new JLabel(value == null? "": value.toString());
  				
  						if(row %2 == 0){
  							((JComponent) componente).setOpaque(true); 
  							componente.setBackground(new java.awt.Color(177,177,177));	
  						}
  						 
  						if(table.getSelectedRow() == row){
  							((JComponent) componente).setOpaque(true); 
  							componente.setBackground(new java.awt.Color(186,143,73));
  						}	
  					return componente; 
  					} 
  				}; 
  								tabla.getColumnModel().getColumn(0).setCellRenderer(render); 
  								tabla.getColumnModel().getColumn(1).setCellRenderer(render); 
  								tabla.getColumnModel().getColumn(2).setCellRenderer(render); 
  				
  				Statement s;
  				ResultSet rs;
  				try {
  					s = con.conexion().createStatement();
  					rs = s.executeQuery("exec sp_select_ultimas_vacaciones_por_empleado" );
  					
  					while (rs.next())
  					{ 
  					   String [] fila = new String[3];
  					   fila[0] = rs.getString(1).trim();
  					   fila[1] = rs.getString(2).trim();
  					   fila[2] = rs.getString(3).trim();
  					   
  					   model.addRow(fila); 
  					}	
  				} catch (SQLException e1) {
  					e1.printStackTrace();
  				}
  				 JScrollPane scrol = new JScrollPane(tabla);
  				   
  			    return scrol; 
  			}
  			
  			KeyListener validaCantidad = new KeyListener() {
  				@Override
  				public void keyTyped(KeyEvent e){
  					char caracter = e.getKeyChar();				
  					if(((caracter < '0') ||	
  					    	(caracter > '9')) && 
  					    	(caracter != '.' )){
  					    	e.consume();
  					    	}
  				}
  				@Override
  				public void keyReleased(KeyEvent e) {	
  				}
  				@Override
  				public void keyPressed(KeyEvent arg0) {
  				}	
  			};
  			
  			KeyListener validaNumericoConPunto = new KeyListener() {
  				@Override
  				public void keyTyped(KeyEvent e) {
  					char caracter = e.getKeyChar();
  					
  				    if(((caracter < '0') ||	
  				    	(caracter > '9')) && 
  				    	(caracter != '.')){
  				    	e.consume();
  				    	}
  				    		    		       	
  				}
  				@Override
  				public void keyPressed(KeyEvent e){}
  				@Override
  				public void keyReleased(KeyEvent e){}
  										
  			};
  			
  			KeyListener seleccionEmpleadoconteclado = new KeyListener() {
  				@SuppressWarnings({ "static-access", "unused" })
  				@Override
  				public void keyTyped(KeyEvent e) {
  					char caracter = e.getKeyChar();
  					
  					if(caracter==e.VK_ENTER){
  					int fila=tabla.getSelectedRow()-1;
  					String folio = tabla.getValueAt(fila,0).toString().trim();
  						
  					btnBuscar.doClick();
  					dispose();
  					}
  				}
  				@Override
  				public void keyPressed(KeyEvent e){}
  				@Override
  				public void keyReleased(KeyEvent e){}
  										
  			};
  			
  			
  		}
		
//Filtro Empleado----------------------------------------------------------------------------
	public class Filtro_Empleado_Para_Vacaciones_Nuevas extends JDialog{
		
		Container cont = getContentPane();
		JLayeredPane campo = new JLayeredPane();
		
		Connexion con = new Connexion();
		
		DefaultTableModel model = new DefaultTableModel(0,3){
			public boolean isCellEditable(int fila, int columna){
				if(columna < 0)
					return true;
				return false;
			}
		};
		JTable tabla = new JTable(model);
		
		@SuppressWarnings("rawtypes")
		private TableRowSorter trsfiltro;
		
		JLabel lblBuscar = new JLabel("BUSCAR : ");
		JTextField txtBuscar = new JTextField();
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Filtro_Empleado_Para_Vacaciones_Nuevas(){
			this.setModal(true);
			this.setTitle("Filtro Empleados");
			txtBuscar.addKeyListener(new KeyAdapter() { 
				public void keyReleased(final KeyEvent e) { 
	                filtro(); 
	            } 
	        });
		
			trsfiltro = new TableRowSorter(model); 
			tabla.setRowSorter(trsfiltro);  
			
			campo.add(lblBuscar).setBounds(30,30,70,20);
			campo.add(txtBuscar).setBounds(95,30,215,20);
			campo.add(getPanelTabla()).setBounds(10,70,365,450);
			
			cont.add(campo);
			agregar(tabla);
			
			this.setSize(390,570);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			
			tabla.addKeyListener(seleccionEmpleadoconteclado);
			
		}
		private void agregar(final JTable tbl) {
	        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
		        public void mouseClicked(MouseEvent e) {
		        	if(e.getClickCount() == 2){
		        		
		        		int fila = tabla.getSelectedRow();
		    			String folio =  tabla.getValueAt(fila, 0).toString().trim();
		    			dispose();
		    			
		    			llenarDatosEmpleado(Integer.valueOf(folio));
		        	}
		        }
	        });
	    }
		public void llenarDatosEmpleado(int folio){
			
				 fechaInicio.setEnabled(true);
 				 
	  				Obj_Alimentacion_De_Vacaciones vacaciones = new Obj_Alimentacion_De_Vacaciones().buscar(folio);
					
	  	            while(tabla_vacaciones_disfrutadas.getRowCount()>0){
	  	                tabla_model.removeRow(0);
	  	        }
	  	        
	  	        Object [][] lista_tabla = new BuscarTablasModel().tabla_de_vacaciones_disfrutadas(folio);
	  	        String[] fila = new String[4];
	  	                for(int i=0; i<lista_tabla.length; i++){
	  	                        fila[0] = lista_tabla[i][0]+"";
	  	                        fila[1] = lista_tabla[i][1]+"";
	  	                        fila[2] = lista_tabla[i][2]+"";
	  	                        fila[3] = lista_tabla[i][3]+"";
	  	                        tabla_model.addRow(fila);
	  	                }
	  	                
	  				txtFolioVacaciones.setText(vacaciones.getFolio_vacaciones()+"");
	  				txtFolioEmpleado.setText(vacaciones.getFolio_empleado()+"");
	  				txtEmpleado.setText(vacaciones.getEmpleado());
	  				txtEstablecimiento.setText(vacaciones.getEstablecimiento());
	  				txtPuesto.setText(vacaciones.getPuesto());
	  				txtFechaIngreso.setText(vacaciones.getFecha_ingreso());
	  				txtFechaIngresoIMSS.setText(vacaciones.getFecha_ingreso_imss());
	  				txtSalarioDiarioIn.setText(vacaciones.getSalario_diario_integrado()+"");
	  				txtGrupoDeVacaciones.setText(vacaciones.getGrupo_vacacional());
	  				txtProximasVacaciones.setText(vacaciones.getProximas_vacaciones()+"");
	  				
 				ImageIcon tmpIconDefault = new ImageIcon(System.getProperty("user.dir")+"/tmp/tmp.jpg");
 		         Icon iconoDefault = new ImageIcon(tmpIconDefault.getImage().getScaledInstance(btnFoto.getWidth(), btnFoto.getHeight(), Image.SCALE_DEFAULT));
 		         btnFoto.setIcon(iconoDefault);
 				
	         btnBuscar.setEnabled(false);
	         btnNuevo.setEnabled(false);
		}
		
		@SuppressWarnings("unchecked")
		public void filtro() { 
				trsfiltro.setRowFilter(RowFilter.regexFilter(txtBuscar.getText().toUpperCase().trim(), 1));
		}  
		private JScrollPane getPanelTabla()	{		
			new Connexion();
			
			DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
			tcr.setHorizontalAlignment(SwingConstants.CENTER);
			
			tabla.getColumnModel().getColumn(0).setCellRenderer(tcr);
			
			tabla.getColumnModel().getColumn(0).setHeaderValue("Folio");
			tabla.getColumnModel().getColumn(0).setMaxWidth(70);
			tabla.getColumnModel().getColumn(0).setMinWidth(70);
			tabla.getColumnModel().getColumn(1).setHeaderValue("Empleado");
			tabla.getColumnModel().getColumn(1).setMaxWidth(185);
			tabla.getColumnModel().getColumn(1).setMinWidth(185);
			tabla.getColumnModel().getColumn(2).setHeaderValue("Establecimiento");
			tabla.getColumnModel().getColumn(2).setMaxWidth(100);
			tabla.getColumnModel().getColumn(2).setMinWidth(100);
			
			TableCellRenderer render = new TableCellRenderer() 
			{ 
				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, 
				boolean hasFocus, int row, int column) { 
					Component componente = null;
					
					componente = new JLabel(value == null? "": value.toString());
			
					if(row %2 == 0){
						((JComponent) componente).setOpaque(true); 
						componente.setBackground(new java.awt.Color(177,177,177));	
					}
					 
					if(table.getSelectedRow() == row){
						((JComponent) componente).setOpaque(true); 
						componente.setBackground(new java.awt.Color(186,143,73));
					}	
				return componente; 
				} 
			}; 
							tabla.getColumnModel().getColumn(0).setCellRenderer(render); 
							tabla.getColumnModel().getColumn(1).setCellRenderer(render); 
							tabla.getColumnModel().getColumn(2).setCellRenderer(render); 
			
			Statement s;
			ResultSet rs;
			try {
				s = con.conexion().createStatement();
				rs = s.executeQuery("exec sp_select_permiso_checador_filtro_empleados" );
				
				while (rs.next())
				{ 
				   String [] fila = new String[3];
				   fila[0] = rs.getString(1).trim();
				   fila[1] = rs.getString(2).trim();
				   fila[2] = rs.getString(3).trim();
				   
				   model.addRow(fila); 
				}	
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			 JScrollPane scrol = new JScrollPane(tabla);
			   
		    return scrol; 
		}
		
		KeyListener validaCantidad = new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e){
				char caracter = e.getKeyChar();				
				if(((caracter < '0') ||	
				    	(caracter > '9')) && 
				    	(caracter != '.' )){
				    	e.consume();
				    	}
			}
			@Override
			public void keyReleased(KeyEvent e) {	
			}
			@Override
			public void keyPressed(KeyEvent arg0) {
			}	
		};
		
		KeyListener validaNumericoConPunto = new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				char caracter = e.getKeyChar();
				
			    if(((caracter < '0') ||	
			    	(caracter > '9')) && 
			    	(caracter != '.')){
			    	e.consume();
			    	}
			    		    		       	
			}
			@Override
			public void keyPressed(KeyEvent e){}
			@Override
			public void keyReleased(KeyEvent e){}
									
		};
		
		KeyListener seleccionEmpleadoconteclado = new KeyListener() {
			@SuppressWarnings({ "static-access", "unused" })
			@Override
			public void keyTyped(KeyEvent e) {
				char caracter = e.getKeyChar();
				
				if(caracter==e.VK_ENTER){
				int fila=tabla.getSelectedRow()-1;
				String folio = tabla.getValueAt(fila,0).toString().trim();
					
				btnBuscar.doClick();
				dispose();
				}
			}
			@Override
			public void keyPressed(KeyEvent e){}
			@Override
			public void keyReleased(KeyEvent e){}
									
		};
		
		
	}
	
	public static void main(String [] arg){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Alimentacion_De_Vacaciones().setVisible(true);
		}catch(Exception e){
			System.err.println("Error :"+ e.getMessage());
		}
	}
}
