package Cat_Compras;

import java.awt.Container;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SpinnerDateModel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Connexion;
import Obj_Contabilidad.Obj_Indicadores;
import Obj_Principal.JCButton;
import Obj_Principal.Obj_Filtro_Dinamico;
import Obj_Renders.tablaRenderer;

import com.toedter.calendar.JDateChooser;

@SuppressWarnings("serial")
public class Cat_Reporte_De_Ventas_Administrativo extends JFrame {
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	Connexion con = new Connexion();
	
	JDateChooser c_inicio = new JDateChooser();
	JDateChooser c_final = new JDateChooser();
	
	String[] inicioDefault ="00:00:00".split(":");
	String[] finDefault ="23:59:59".split(":");
	
	SpinnerDateModel TiempoInicio =  new SpinnerDateModel();
	  JSpinner sphora_inicio = new JSpinner(TiempoInicio);                                         
	  JSpinner.DateEditor datoini = new JSpinner.DateEditor(sphora_inicio,"H:mm");
	SpinnerDateModel TiempoFin =  new SpinnerDateModel();
	  JSpinner sphora_fin = new JSpinner(TiempoFin);                                         
	  JSpinner.DateEditor datofin = new JSpinner.DateEditor(sphora_fin,"H:mm");
	  
	String indicadores[] =new Obj_Indicadores().Combo_Indicadores();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbIndicadores = new JComboBox(indicadores);
	  
	String presentado[] = {"Producto","Establecimiento","Clase","Categoria","Familia"};
	@SuppressWarnings({ "unchecked", "rawtypes" })
	JComboBox cmbPresentado = new JComboBox(presentado);
	
	String operador[] = {"Todos","Igual","Esta en lista","Menor que","Mayor que","Diferente"};
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbEstablecimiento = new JComboBox(operador);
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbOperador_Clase = new JComboBox(operador);
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbOperador_Categoria= new JComboBox(operador);
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbOperador_Familia = new JComboBox(operador);
	
	JButton btnFiltroEstablecimiento = new JButton(new ImageIcon("Imagen/Filter-List-icon16.png"));
	JButton btnLimpiarFiltroEstablecimiento = new JButton(new ImageIcon("Imagen/clear-brush-broom-sweeping-change-icone-7230-16.png"));
	
	JButton btnFiltroClase = new JButton(new ImageIcon("Imagen/Filter-List-icon16.png"));
	JButton btnLimpiarFiltroClase= new JButton(new ImageIcon("Imagen/clear-brush-broom-sweeping-change-icone-7230-16.png"));
	
	JButton btnFiltroCategoria = new JButton(new ImageIcon("Imagen/Filter-List-icon16.png"));
	JButton btnLimpiarFiltroCategoria = new JButton(new ImageIcon("Imagen/clear-brush-broom-sweeping-change-icone-7230-16.png"));
	
	JButton btnFiltroFamilia = new JButton(new ImageIcon("Imagen/Filter-List-icon16.png"));
	JButton btnLimpiarFiltroFamilia = new JButton(new ImageIcon("Imagen/clear-brush-broom-sweeping-change-icone-7230-16.png"));
	
	JButton btn_generar = new JCButton  ("Generar","buscar.png","Azul");
	
	JLabel JLBlinicio= new JLabel(new ImageIcon("Imagen/iniciar-icono-4628-16.png") );
	JLabel JLBfin= new JLabel(new ImageIcon("Imagen/acabado-icono-7912-16.png") );
	JLabel JLBrelog= new JLabel(new ImageIcon("Imagen/horas-reloj-icono-9852-16.png") );
	JLabel JLBrelog2= new JLabel(new ImageIcon("Imagen/horas-reloj-icono-9852-16.png") );
	JLabel JLBestablecimiento= new JLabel(new ImageIcon("Imagen/folder-home-home-icone-5663-16.png") );
	JLabel JLBTipoPrecio= new JLabel(new ImageIcon("Imagen/precio-marcado-icono-6652-16.png") );
	JLabel JLBPresentado= new JLabel(new ImageIcon("Imagen/las-preferencias-de-tema-de-escritorio-icono-8603-16.png") );
	
	JTextField txtFiltroEstablecimiento = new JTextField("");
	JTextField txtFiltroClase = new JTextField("");
	JTextField txtFiltroCategoria = new JTextField("");
	JTextField txtFiltroFamilia = new JTextField("");
	
	Border blackline, etched, raisedbevel, loweredbevel, empty;
	
	String parametroGeneral = "";
	String Lista="";
	JLabel JLBdescripcion= new JLabel();
	JLabel lblmarco= new JLabel();
	
    static JLabel lblSemaforoRojo = new JLabel("");
    static JLabel lblSemaforoVerde = new JLabel("");
    Icon iconoSemaforoR;
	String semaforoR = System.getProperty("user.dir")+"/Imagen/semaforo_rojo_chica.png";
    ImageIcon tmpIconSemR = new ImageIcon(semaforoR);
    
    Icon iconoSemaforoV;
    String semaforoV = System.getProperty("user.dir")+"/Imagen/semaforo_verde_chica.png";
    ImageIcon tmpIconSemV = new ImageIcon(semaforoV);
	
	public Cat_Reporte_De_Ventas_Administrativo(String parametro, String operador){
		
		setSize(760, 260);
		cont.add(panel);
		setResizable(false);
		setLocationRelativeTo(null);
		setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Sales-by-payment-method-icon-64.png"));
		setTitle("Reportes de  Ventas Administrativo");
		panel.setBorder(BorderFactory.createTitledBorder("Reportes de Venta Administrativo"));
		lblmarco.setBorder(BorderFactory.createTitledBorder(""));
		
		int x=15 ;
		int y=20 ;
		int l=100;
		int a=20;

		panel.add(new JLabel("Fecha Inicio:")).setBounds(x,y,l,a);
		panel.add(JLBlinicio).setBounds(x+=60,y,a,a);
		panel.add(c_inicio).setBounds(x+=20,y,l-10,a);
		panel.add(sphora_inicio).setBounds(x+=95,y,l-50,a);
		panel.add(JLBrelog).setBounds(x+=50,y,a,a);
		panel.add(new JLabel("Fecha Final:")).setBounds(x+=40,y,l,a);
		panel.add(JLBfin).setBounds(x+=60,y,a,a);
		panel.add(c_final).setBounds(x+=20,y,l-10,a);
		panel.add(sphora_fin).setBounds(x+=95,y,l-50,a);
		panel.add(JLBrelog2).setBounds(x+=50,y,a,a);
		      
		x=100;
        panel.add(new JLabel("Establecimiento:")).setBounds(x-85,y+=30,l+50,a); 
		panel.add(cmbEstablecimiento						 ).setBounds(x+80,y,l-12,a);  
        panel.add(txtFiltroEstablecimiento					 ).setBounds(x+170,y,l*4+20,a);  
        panel.add(btnFiltroEstablecimiento					 ).setBounds(x+590,y,a,a);    
        panel.add(btnLimpiarFiltroEstablecimiento			 ).setBounds(x+613,y,a,a);
        
		panel.add(new JLabel("Filtro De Clase De Productos:")).setBounds(x-85,y+=30,l+50,a); 
		panel.add(cmbOperador_Clase							 ).setBounds(x+80,y,l-12,a);  
        panel.add(txtFiltroClase							 ).setBounds(x+170,y,l*4+20,a);  
        panel.add(btnFiltroClase							 ).setBounds(x+590,y,a,a);    
        panel.add(btnLimpiarFiltroClase						 ).setBounds(x+613,y,a,a);
        
		panel.add(new JLabel("Filtro De Categoria De Productos:")).setBounds(x-85,y+=30,l+70,a); 
		panel.add(cmbOperador_Categoria							 ).setBounds(x+80,y,l-12,a);  
        panel.add(txtFiltroCategoria							 ).setBounds(x+170,y,l*4+20,a);  
        panel.add(btnFiltroCategoria							 ).setBounds(x+590,y,a,a);    
        panel.add(btnLimpiarFiltroCategoria						 ).setBounds(x+613,y,a,a);   

      	panel.add(new JLabel("Filtro Familia De Productos:")).setBounds(x-85,y+=30,l+50,a); 
		panel.add(cmbOperador_Familia						).setBounds(x+80,y,l-12,a);  
		panel.add(txtFiltroFamilia							).setBounds(x+170,y,l*4+20,a);  
        panel.add(btnFiltroFamilia							).setBounds(x+590,y,a,a);    
        panel.add(btnLimpiarFiltroFamilia					).setBounds(x+613,y,a,a);
        
        panel.add(new JLabel("Tipo De Reporte: ")).setBounds(x-85,y+=30,l+50,a);
//	    panel.add(JLBPresentado).setBounds(x+740,y,a,a);
		panel.add(cmbIndicadores).setBounds(x+80,y,l+120,a);
		
        panel.add(lblmarco									 ).setBounds(x+305,y-5,a+263,a+35); 
        panel.add(lblSemaforoVerde							 ).setBounds(x+465,y-3,a+30,a+30); 
        panel.add(lblSemaforoRojo							 ).setBounds(x+525,y-3,a+30,a+30); 
        
        panel.add(btn_generar								 ).setBounds(x+312,y,l+40,a*2+5);
        
		panel.add(new JLabel("Presentado Por:")).setBounds(x-85,y+=25,l+50,a);
	    panel.add(JLBPresentado).setBounds(x+60,y,a,a);
		panel.add(cmbPresentado).setBounds(x+80,y,l+120,a);
        
        cargar_fechas();
        
        iconoSemaforoR = new ImageIcon(tmpIconSemR.getImage().getScaledInstance(lblSemaforoRojo.getWidth(), lblSemaforoRojo.getHeight(), Image.SCALE_DEFAULT));
        lblSemaforoRojo.setIcon(iconoSemaforoR);
        iconoSemaforoV = new ImageIcon(tmpIconSemV.getImage().getScaledInstance(lblSemaforoVerde.getWidth(), lblSemaforoVerde.getHeight(), Image.SCALE_DEFAULT));
        lblSemaforoVerde.setIcon(iconoSemaforoV);
        
        txtFiltroEstablecimiento.setEditable(false);
        txtFiltroClase.setEditable(false);
        txtFiltroCategoria.setEditable(false);
        txtFiltroFamilia.setEditable(false);

        if(!parametro.equals("")){
        	
        	panelEnableFalse();
        	btnLimpiarFiltroClase.setEnabled(true);
        }
        
        btnFiltroEstablecimiento.addActionListener(op_filtro_establecimientos);
        btnFiltroClase.addActionListener(op_filtro_clases);
        btnFiltroCategoria.addActionListener(op_filtro_categorias);
        btnFiltroFamilia.addActionListener(op_filtro_familias);
		
        btnLimpiarFiltroEstablecimiento.addActionListener(limpiar_filtro_establecimiento);
		btnLimpiarFiltroClase.addActionListener(limpiar_filtro_claces);
        btnLimpiarFiltroCategoria.addActionListener(limpiar_filtro_categorias);
        btnLimpiarFiltroFamilia.addActionListener(limpiar_filtro_familias);
        
        btn_generar.addActionListener(op_generar);
		
		tiempodefault();
		semaforo();
	}
	
	@SuppressWarnings("deprecation")
	public void tiempodefault(){
		sphora_inicio.setValue(new Time(Integer.parseInt(inicioDefault[0]),Integer.parseInt(inicioDefault[1]),Integer.parseInt(inicioDefault[2])));
		sphora_inicio.setEditor(datoini);
		sphora_fin.setValue(new Time(Integer.parseInt(finDefault[0]),Integer.parseInt(finDefault[1]),Integer.parseInt(finDefault[2])));
		sphora_fin.setEditor(datofin);
	}
	
	ActionListener op_filtro_establecimientos = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(!cmbEstablecimiento.getSelectedItem().toString().equals("Todos")){
				new Cat_Filtro_Dinamico(cmbEstablecimiento.getSelectedItem().toString(),"establecimientos","cod_estab").setVisible(true);			
			}else{
				JOptionPane.showMessageDialog(null, "El Operador Para Este Filtro Es ( Todos ) Por Lo Que No Es Necesario Abrir El Filtro", "Aviso !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}
		}
	};
	
	ActionListener op_filtro_clases = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(!cmbOperador_Clase.getSelectedItem().toString().equals("Todos")){
				Lista="";
				new Cat_Filtro_Dinamico(cmbOperador_Clase.getSelectedItem().toString(),"clases_productos","clase_producto").setVisible(true);
			}else{
				JOptionPane.showMessageDialog(null, "El Operador Para Este Filtro Es ( Todos ) Por Lo Que No Es Necesario Abrir El Filtro", "Aviso !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}
		}
	};
	ActionListener op_filtro_categorias = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(!cmbOperador_Categoria.getSelectedItem().toString().equals("Todos")){
				new Cat_Filtro_Dinamico(cmbOperador_Categoria.getSelectedItem().toString(),"categorias","categoria").setVisible(true);
			}else{
				JOptionPane.showMessageDialog(null, "El Operador Para Este Filtro Es ( Todos ) Por Lo Que No Es Necesario Abrir El Filtro", "Aviso !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}
		}
	};
	
	ActionListener op_filtro_familias = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(!cmbOperador_Familia.getSelectedItem().toString().equals("Todos")){
				new Cat_Filtro_Dinamico(cmbOperador_Familia.getSelectedItem().toString(),"familias","familia").setVisible(true);
			}else{
				JOptionPane.showMessageDialog(null, "El Operador Para Este Filtro Es ( Todos ) Por Lo Que No Es Necesario Abrir El Filtro", "Aviso !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}
		}
	};
	
//LIMPIAR ----------------------------------------------------------------------------------------------------------------------------	
	ActionListener limpiar_filtro_establecimiento = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
            txtFiltroEstablecimiento.setText("");
            cmbEstablecimiento.setSelectedIndex(0);
		}
	};
	
	ActionListener limpiar_filtro_productos = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
            txtFiltroClase.setText("");
            txtFiltroCategoria.setText("");
            txtFiltroFamilia.setText("");
        	panelEnableTrue();
        	Lista="";
        	limpiar_vacios("");
		}
	};
	
	ActionListener limpiar_filtro_claces = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
            txtFiltroClase.setText("");
            txtFiltroCategoria.setText("");
            txtFiltroFamilia.setText("");
        	panelEnableTrue();
        	Lista="";
        	limpiar_vacios("");
		}
	};
	
	ActionListener limpiar_filtro_categorias = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
            
			panelEnableFalse();
			txtFiltroCategoria.setText("");
			limpiar_vacios("");
		}
	};
	
	ActionListener limpiar_filtro_familias = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
           
			panelEnableFalse();
			txtFiltroFamilia.setText("");
			limpiar_vacios("");
		}
	};
	
	public void limpiar_vacios(String boton){
		
		if(!boton.equals("boton talla")){
			panelEnableFalse();
		}
		
		
        btnLimpiarFiltroFamilia.setEnabled(true);
        
        	if(txtFiltroFamilia.getText().equals("")){
        		btnFiltroFamilia.setEnabled(true);
        		if(txtFiltroCategoria.getText().equals("")){
            		btnFiltroCategoria.setEnabled(true);
            		btnLimpiarFiltroCategoria.setEnabled(true);
            		if(txtFiltroClase.getText().equals("")){
	            		btnFiltroClase.setEnabled(true);
	            		btnLimpiarFiltroClase.setEnabled(true);
	            	}
            		btnLimpiarFiltroClase.setEnabled(true);
            	}
        		btnLimpiarFiltroCategoria.setEnabled(true);
        	}
        	btnLimpiarFiltroFamilia.setEnabled(true);
        	
        	cmbOperador_Clase.setSelectedIndex(0);
        	cmbOperador_Categoria.setSelectedIndex(0);
        	cmbOperador_Familia.setSelectedIndex(0);
        	
        	Lista="";
	}
	
//	----------------------------------------------------------------------------------------------------------------------------------------
	
	ActionListener op_generar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			semaforo();
			
			if(validar_fechas().equals("")){
				
				String fecha_inicio = new SimpleDateFormat("dd/MM/yyyy").format(c_inicio.getDate())+" "+new SimpleDateFormat("HH:mm:ss").format(sphora_inicio.getValue());
				String fecha_final = new SimpleDateFormat("dd/MM/yyyy").format(c_final.getDate())+" "+new SimpleDateFormat("HH:mm:ss").format(sphora_fin.getValue());
				
				  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm"); 

				  Date fecha1 = sdf.parse(fecha_inicio , new ParsePosition(0));
				  Date fecha2 = sdf.parse(fecha_final , new ParsePosition(0));

				if(fecha1.before(fecha2)){
					
					String Indic = cmbIndicadores.getSelectedItem().toString();
					String presen_por = cmbPresentado.getSelectedItem().toString();
					
					
					
					
					JOptionPane.showMessageDialog(null,"EN ESTA PARTE VA EL REOPERTE SOLICITADO Ó\nEL AVISO DE QUE NO SE ENCONTRO ","Aviso!", JOptionPane.WARNING_MESSAGE);
					return;
					
				}else{
					JOptionPane.showMessageDialog(null,"El Rango de Fechas Esta Invertido","Aviso!", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
			}else{
				JOptionPane.showMessageDialog(null,"Los siguientes campos están vacíos: "+validar_fechas(),"Aviso!", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}
	};
	
	public void panelEnableFalse(){
		btnFiltroClase.setEnabled(false);
    	btnFiltroCategoria.setEnabled(false);
//    	btnFiltroFamilia.setEnabled(false);
    	btnLimpiarFiltroClase.setEnabled(false);
    	btnLimpiarFiltroCategoria.setEnabled(false);
//    	btnLimpiarFiltroFamilia.setEnabled(false);
	}
	
	public void panelEnableTrue(){
		btnFiltroClase.setEnabled(true);
    	btnFiltroCategoria.setEnabled(true);
//    	btnFiltroFamilia.setEnabled(true);
    	btnLimpiarFiltroClase.setEnabled(true);
    	btnLimpiarFiltroCategoria.setEnabled(true);
//    	btnLimpiarFiltroFamilia.setEnabled(true);
	}
	
	public String validar_fechas(){
		String error = "";
		String fechainicioNull = c_inicio.getDate()+"";
		String fechafinalNull = c_final.getDate()+"";
	    if(fechainicioNull.equals("null"))error+= "Fecha  inicio\n";
		if(fechafinalNull.equals("null"))error+= "Fecha Final\n";
		
		return error;
	}
	
	public void cargar_fechas(){
		Date date1 = null;
				  try {
					date1 = new SimpleDateFormat("dd/MM/yyyy").parse(new BuscarSQL().fecha(0));
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
	
	public void semaforo(){
		if(new BuscarSQL().semaforo_rptVentas()){
			lblSemaforoRojo.setEnabled(false);
			lblSemaforoVerde.setEnabled(true);
		}else{
			lblSemaforoRojo.setEnabled(true);
			lblSemaforoVerde.setEnabled(false);
		}
	}
	
	
// FILTRO
		 	public class Cat_Filtro_Dinamico extends JDialog {
				
				Container cont = getContentPane();
				JLayeredPane campo = new JLayeredPane();
				
				Object[][] MatrizFiltro ;
				
				String Operador = "";
				
				DefaultTableModel modeloFiltro = new DefaultTableModel(null,
			            new String[]{"Folio", "Nombre",""}
						){
				     @SuppressWarnings("rawtypes")
					Class[] types = new Class[]{
				    	java.lang.Integer.class,
				    	java.lang.String.class,
				    	java.lang.Boolean.class
			         };
				     @SuppressWarnings({ "rawtypes", "unchecked" })
					public Class getColumnClass(int columnIndex) {
			             return types[columnIndex];
			         }
			         public boolean isCellEditable(int fila, int columna){
			        	 switch(columna){
			        	 	case 0 : return false; 
			        	 	case 1 : return false; 
			        	 	case 2 : return true;
			        	 		
			        	 } 				
			 			return false;
			 		}
			         
			            @Override
			            public void setValueAt(Object value, int row, int col) {
			                super.setValueAt(value, row, col);
			                if(!Operador.equals("Esta en lista")){
			                	if (col == 2 && value.equals(Boolean.TRUE))
			                    deselectValues(row, col);
			                }
			            }

			            private void deselectValues(int selectedRow, int col) {
			                for (int row = 0; row < getRowCount(); row++) {
			                    if (getValueAt(row, col).equals(Boolean.TRUE)
			                            && row != selectedRow) {
			                        setValueAt(Boolean.FALSE, row, col);
			                        fireTableCellUpdated(row, col);
			                    }
			                }
			            }
				};
				
				JTable tablaFiltro = new JTable(modeloFiltro);
			    JScrollPane scroll = new JScrollPane(tablaFiltro);
				
				@SuppressWarnings("rawtypes")
				private TableRowSorter trsfiltro;
				
				JTextField txtFolio = new JTextField();
				JTextField txtNombre_Completo = new JTextField();
				
				JButton btnAgregar = new JButton(new ImageIcon("Iconos/agregar.png"));
				
				String folio_columna = "";
				@SuppressWarnings({ "unchecked", "rawtypes" })
				public Cat_Filtro_Dinamico(String operad, String nombre_de_tabla,String folio_colum){
					
					this.setModal(true);
					setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/filter_icon&16.png"));
					setTitle("Filtro de "+nombre_de_tabla);
					campo.setBorder(BorderFactory.createTitledBorder("Seleccion "+folio_colum));
					trsfiltro = new TableRowSorter(modeloFiltro); 
					tablaFiltro.setRowSorter(trsfiltro);  
					
					Operador = operad;
					folio_columna=folio_colum;
					
					System.out.print(Operador+"      "+folio_columna);

					btnAgregar.setToolTipText("Agregar");
					
					campo.add(scroll).setBounds(15,43,364,360);
					
					campo.add(txtFolio).setBounds(15,20,40,20);
					campo.add(txtNombre_Completo).setBounds(56,20,280,20);
					campo.add(btnAgregar).setBounds(340,20,50,20);
					
					cont.add(campo);
					
					modeloFiltro.setRowCount(0);
					Object[][] getTablaFiltro = getTablaFiltro(operad,nombre_de_tabla);
                    for(Object[] reg: getTablaFiltro){
                            modeloFiltro.addRow(reg);
                    }
					
					llamar_render();
					
					txtFolio.addKeyListener(opFiltroFolio);
					txtNombre_Completo.addKeyListener(opFiltroNombre);
					
					btnAgregar.addActionListener(opAgregar);
					
					setSize(405,450);
					setResizable(false);
					setLocationRelativeTo(null);
					
					 this.addWindowListener(new WindowAdapter() {
		                    public void windowOpened( WindowEvent e ){
		                    	txtNombre_Completo.requestFocus();
		                 }
		            });
				}
				
				public void llamar_render(){
					
					tablaFiltro.getColumnModel().getColumn(0).setMaxWidth(40);
					tablaFiltro.getColumnModel().getColumn(0).setMinWidth(40);
					tablaFiltro.getColumnModel().getColumn(1).setMaxWidth(280);
					tablaFiltro.getColumnModel().getColumn(1).setMinWidth(280);
					tablaFiltro.getColumnModel().getColumn(2).setMaxWidth(30);
					tablaFiltro.getColumnModel().getColumn(2).setMinWidth(30);
					
					tablaFiltro.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",10)); 
					tablaFiltro.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
					tablaFiltro.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("CHB","centro","Arial","normal",12));
				}
				
				ActionListener opAgregar = new ActionListener() {
					@SuppressWarnings({ })
					public void actionPerformed(ActionEvent arg0) {
						txtNombre_Completo.setText("");
				 		new Obj_Filtro_Dinamico(tablaFiltro, "Nombre", "", "", "", "", "", "", "");
				 		
//						if(tablaFiltro.isEditing()){
//				 			tablaFiltro.getCellEditor().stopCellEditing();
//						}
//						trsfiltro.setRowFilter(RowFilter.regexFilter("", 0));
						txtFolio.setText("");
						
						int contador=0;
				 		 Lista="('";	
				 			for(int i=0; i<tablaFiltro.getRowCount(); i++){
				 				if(Boolean.parseBoolean(modeloFiltro.getValueAt(i, 2).toString()) == true){
				 					String posicion = modeloFiltro.getValueAt(i, 0).toString().trim();
				 					
				 					contador=contador+=1;
				 							if(contador == 1){
				 								Lista=Lista+"'"+posicion+"'";
						 					}else{
						 						Lista=Lista+"',''"+posicion+"'";
						 					}
				 				}
				 			}
				 			Lista=Lista+"')";

				 			if(Lista.equals("('')")){
				 				JOptionPane.showMessageDialog(null, "Es necesario seleccionar un argunemto", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
	 							return;
				 			}else{
				 		        String operador_simbolo = "";
				 		        
				 		        if(!folio_columna.equals("cod_estab")){
				 		        	 panelEnableFalse();
				 		        }
				 		      
				 		            switch(Operador){
				 			    		case "Igual"		:operador_simbolo=" = "; 
				 			    		parametroGeneral=Lista;
				 			    		
				 			    		break;
				 			    		case "Esta en lista":operador_simbolo=" in "; 
				 			    		
				 			    		break;
				 			    		case "Menor que"	:operador_simbolo=" < "; 
				 			    		
				 			    		break;
				 			    		case "Mayor que"	:operador_simbolo=" > "; 
				 			    		
				 			    		break;
				 			    		case "Diferente"	:operador_simbolo=" <> "; 
				 			    		
				 			    		break;
				 		    		}
				 				Lista=operador_simbolo+Lista;
				 				if(folio_columna.equals("cod_estab")){
				 					txtFiltroEstablecimiento.setText(Lista);
				 					dispose();
				 				}else{
					 				switch(folio_columna){
					 				           
								 				case "clase_producto":	txtFiltroClase.setText(Lista)		;
								 										if(Operador.equals("Igual")||Operador.equals("Esta en lista")){
								 											btnFiltroCategoria.setEnabled(true);
								 											btnLimpiarFiltroCategoria.setEnabled(true);
								 										}
								 										
								 										btnLimpiarFiltroClase.setEnabled(true);
								 				break;
								 				
								 				case "categoria":		txtFiltroCategoria.setText(Lista)	;
														 				if(Operador.equals("Igual")||Operador.equals("Esta en lista")){
								 											btnFiltroFamilia.setEnabled(true);
								 											btnLimpiarFiltroFamilia.setEnabled(true);
								 										}
														 				btnLimpiarFiltroCategoria.setEnabled(true);
								 				break;
								 				
								 				
								 				case "familia":			txtFiltroFamilia.setText(Lista)		;
//														 				if(Operador.equals("Igual")||Operador.equals("Esta en lista")){
//								 											btnFiltroLinea.setEnabled(true);
//								 											btnLimpiarFiltroLinea.setEnabled(true);
//								 										}
														 				btnLimpiarFiltroFamilia.setEnabled(true);
								 				break;
								 				
												
								 				case "cod_estab":			txtFiltroEstablecimiento.setText(Lista);	
								 				break;
					 				}
					 				
						 			dispose();
					 			}
				 			}
					}
				};
				
				
				KeyListener opFiltroFolio = new KeyListener(){
					@SuppressWarnings("unchecked")
					public void keyReleased(KeyEvent arg0) {
						trsfiltro.setRowFilter(RowFilter.regexFilter(txtFolio.getText(), 0));
					}
					public void keyTyped(KeyEvent arg0) {
						char caracter = arg0.getKeyChar();
						if(((caracter < '0') ||
							(caracter > '9')) &&
						    (caracter != KeyEvent.VK_BACK_SPACE)){
							arg0.consume(); 
						}	
					}
					public void keyPressed(KeyEvent arg0) {}		
				};
				
				KeyListener opFiltroNombre = new KeyListener(){
					public void keyReleased(KeyEvent arg0) {
						new Obj_Filtro_Dinamico(tablaFiltro,"Nombre", txtNombre_Completo.getText().toUpperCase(),"","", "", "", "", "");
					}
					public void keyTyped(KeyEvent arg0) {}
					public void keyPressed(KeyEvent arg0) {}		
				};
				
				
			   	public Object[][] getTablaFiltro(String operador, String nombre_de_tabla){
			   		String condicion = "";
			   		
			   		if(!Lista.equals("")){
			   			if(nombre_de_tabla.equals("establecimientos")){
				   			condicion = " where cod_estab not "+Lista.replace("''","'");
				   		}else{
				   			condicion = " where jerarquia "+Lista.replace("''","'");
				   		}
			   			
			   		}
			   		
					String todos = "select "+folio_columna+" as folio,upper(nombre) from "+nombre_de_tabla+condicion+" order by nombre";
					Statement s;
					ResultSet rs;
					try {
						s = new Connexion().conexion_IZAGAR().createStatement();
						rs = s.executeQuery(todos);
						
						MatrizFiltro = new Object[getFilas(todos)][3];
						int i=0;
						while(rs.next()){
							String folio = rs.getString(1);
							MatrizFiltro[i][0] = folio+"  ";
							MatrizFiltro[i][1] = "   "+rs.getString(2).trim();
							MatrizFiltro[i][2] = false;
							i++;
						}
						Lista="";
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				    return MatrizFiltro; 
				}
			   	
			   	public int getFilas(String qry){
					int filas=0;
					Statement stmt = null;
					try {
						stmt = new Connexion().conexion_IZAGAR().createStatement();
						ResultSet rs = stmt.executeQuery(qry);
						while(rs.next()){
							filas++;
						}
						
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					return filas;
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
				
			}
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Reporte_De_Ventas_Administrativo("","Todos").setVisible(true);
		}catch(Exception e){	}
	}

}
