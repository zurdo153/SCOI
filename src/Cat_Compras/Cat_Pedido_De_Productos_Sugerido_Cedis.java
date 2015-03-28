package Cat_Compras;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import com.toedter.calendar.JDateChooser;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Connexion;
import Conexiones_SQL.Generacion_Reportes;
import Conexiones_SQL.GuardarSQL;
import Obj_Renders.CaveceraTablaRenderer;
import Obj_Renders.tablaRenderer;


@SuppressWarnings("serial")
public class Cat_Pedido_De_Productos_Sugerido_Cedis extends JFrame{
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	String[] columnas = new String[]{"Cod.Prod.","Descripcion","Exist.Cedis", "Transferencia", "Sugerido", "*", "Sugerido Cedis", "Observaciones"};
   
	public DefaultTableModel modelo = new DefaultTableModel(null,  columnas){
                    
		@SuppressWarnings({ "rawtypes" })
		Class[] types = new Class[]{
                   java.lang.Integer.class, 
                   java.lang.Object.class, 
                   java.lang.Double.class, 
                   java.lang.Float.class,
                   java.lang.Integer.class, 
                   java.lang.Boolean.class,
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
                        case 4  : return false; 
                        case 5  : return true; 
                        case 6  : if(modelo.getValueAt(fila, 5).toString().equals("true")){
                        	return true;
                        }else{
                        	return false;
                        }
                        case 7  : if(modelo.getValueAt(fila, 5).toString().equals("true")){
                        	return true;
                        }else{
                        	return false;
                        } 
                }
                 return false;
         }
    };
    
	public DefaultTableModel modeloEliminados = new DefaultTableModel(null,  columnas){
        
		@SuppressWarnings({ "rawtypes" })
		Class[] types = new Class[]{
				java.lang.Integer.class, 
	            java.lang.Object.class, 
	            java.lang.Double.class, 
	            java.lang.Float.class,
	            java.lang.Integer.class, 
	            java.lang.Boolean.class,
	            java.lang.String.class, 
	            java.lang.String.class
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
	                    case 4  : return false; 
	                    case 5  : return true; 
	                    case 6  : if(modeloEliminados.getValueAt(fila, 5).toString().equals("true")){
	                    	return true;
	                    }else{
	                    	return false;
	                    }
	                    case 7  : if(modeloEliminados.getValueAt(fila, 5).toString().equals("true")){
	                    	return true;
	                    }else{
	                    	return false;
	                    } 
	            }
	             return false;
	     }
	};

	JTable tabla = new JTable(modelo);
	JScrollPane scroll = new JScrollPane(tabla,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    JTable tablaEliminados = new JTable(modeloEliminados);
    JScrollPane scrollEliminados = new JScrollPane(tablaEliminados,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    JButton btnConsultar = new JButton("Consultar");
    
    JTextField txtCodProd = new JTextField();
    JTextField txtDesc = new JTextField();
    
    JDateChooser c_fecha = new JDateChooser();
    
    JButton btnQuitarFila = new JButton("Quitar",new ImageIcon("imagen/eliminar-bala-icono-7773-32.png"));
    JButton btnGuardar = new JButton("Guardar Sugerido",new ImageIcon("imagen/Guardar.png"));
    JButton btnReporte = new JButton("Reporte De Sugerido",new ImageIcon("imagen/checklist-icon16.png"));
    JButton btnReporteNSugerido = new JButton("Reporte De N/Sugerido",new ImageIcon("imagen/checklist-icon16.png"));
    
    JTextField txtCodProdRestaurar = new JTextField();
    JTextField txtDescRestaurar = new JTextField();
    JButton btnRestaurarFila = new JButton("Restaurar",new ImageIcon("imagen/Up.png"));
    
    int filaDep =0;
    int columnaDep = 6;

	@SuppressWarnings("rawtypes")
	private TableRowSorter trsfiltro;
	@SuppressWarnings("rawtypes")
	private TableRowSorter trsfiltroRestaurar;
    
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Cat_Pedido_De_Productos_Sugerido_Cedis(){
		this.setTitle("Sugerido Cedis");
		this.panel.setBorder(BorderFactory.createTitledBorder( "Captura De Sugerido Cedis"));
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/checklist-icon128.png"));
		
		trsfiltro = new TableRowSorter(modelo); 
		tabla.setRowSorter(trsfiltro);
		
		trsfiltroRestaurar = new TableRowSorter(modeloEliminados); 
		tablaEliminados.setRowSorter(trsfiltroRestaurar);
		
		panel.add(btnConsultar).setBounds(15,20,100,20);
		panel.add(btnGuardar).setBounds(257,20,160,20);
		
		panel.add(new JLabel("Fecha Del Pedido Para Reporte:")).setBounds(550,20,200,20);
		panel.add(c_fecha).setBounds(740,20,90,20);
		panel.add(btnReporte).setBounds(840,20,165,20);
		panel.add(btnReporteNSugerido).setBounds(840,40,165,20);
		
		panel.add(txtCodProd).setBounds(15, 45, 80, 20);
		panel.add(txtDesc).setBounds(95, 45, 320, 20);
		panel.add(btnQuitarFila).setBounds(415,45,100,20);
		
		panel.add(scroll).setBounds(15, 65, 990, 525);
		
		panel.add(txtCodProdRestaurar).setBounds(15, 595, 80, 20);
		panel.add(txtDescRestaurar).setBounds(95, 595, 320, 20);
		panel.add(btnRestaurarFila).setBounds(415,595,100,20);
		
		panel.add(scrollEliminados).setBounds(15, 615, 990, 115);
		
		cont.add(panel);
		
		btnGuardar.setEnabled(false);
		
		llamarRender(tabla);
		llamarRender(tablaEliminados);
		
		filaDep=0;
	    agregar(tabla);
		
		btnConsultar.addActionListener(opBuscar);
		btnQuitarFila.addActionListener(opQuitar);
		btnRestaurarFila.addActionListener(opQuitar);
		btnGuardar.addActionListener(opGuardar);
		btnReporte.addActionListener(opReporte);
		btnReporteNSugerido.addActionListener(opReporte);
		
		txtCodProd.addKeyListener(opFiltroCodProd);
		txtDesc.addKeyListener(opFiltroDesc);
		
		txtCodProdRestaurar.addKeyListener(opFiltroCodProdRestaurar);
		txtDescRestaurar.addKeyListener(opFiltroDescRestaurar);
		
		tabla.addKeyListener(op_key);
		
		this.setSize(1024,768);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		
		c_fecha.setDate(cargar_fecha());
	}
	
	private void agregar(final JTable tbl) {
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
	        public void mouseClicked(MouseEvent e) {
	        	filaDep= tbl.getSelectedRow();
	        	
	        	if(tabla.getValueAt(filaDep, columnaDep-1).toString().equals("true")){
	        		tabla.setEnabled(true);
	    			tabla.editCellAt(filaDep, columnaDep);
	    			Component aComp=tabla.getEditorComponent();
	    			aComp.requestFocus();
	        	}else{
	        		tabla.setValueAt("", filaDep, columnaDep);
	        		tabla.setValueAt("", filaDep, columnaDep+1);
	        	}
	        }
        });
    }
	
	KeyListener op_key = new KeyListener() {
		public void keyTyped(KeyEvent e) {}
		public void keyReleased(KeyEvent e) {
		
			if(!isNumeric(modelo.getValueAt(filaDep,columnaDep).toString().trim())){
				
				JOptionPane.showMessageDialog(null, "El Sugerido Cedis en el Cod. Prod. "+modelo.getValueAt(filaDep,0).toString()+"\nestán mal en su formato o esta vacio","Error",JOptionPane.ERROR_MESSAGE);
				modelo.setValueAt("", filaDep,columnaDep);
				tabla.setEnabled(true);
    			tabla.editCellAt(filaDep, columnaDep);
    			Component aComp=tabla.getEditorComponent();
    			aComp.requestFocus();
				return;
				
			}else{
				if(!tabla.getValueAt(filaDep, columnaDep+1).equals("")){
					tabla.setEnabled(false);
				}else{
					modelo.setValueAt("", filaDep,columnaDep+1);
					tabla.setEnabled(true);
	    			tabla.editCellAt(filaDep, columnaDep+1);
	    			Component aComp=tabla.getEditorComponent();
	    			aComp.requestFocus();
				}
			}
		}
		public void keyPressed(KeyEvent e) {}
	};
	
	
	@SuppressWarnings("unused")
	private boolean Validar(int fila, int columna) { 
		String valor=""; 
		
			if(tabla.getValueAt(fila,columna)==null) { 
				return false; 
			}else{ 
				try{
					return true;
				}catch(NumberFormatException e){
				 return false;
				}
			} 
	}
	
    private boolean isNumeric(String cadena){
    	try {
    		if(cadena.equals("")){
        		return false;
    		}else{
    			Float.parseFloat(cadena);
        		return true;
    		}
    	} catch (NumberFormatException nfe){
    		return false;
    	}
    }
	
	public Date cargar_fecha(){
			
			Date date1 = null;
					  try {
						date1 = new SimpleDateFormat("dd/MM/yyyy").parse(new BuscarSQL().fecha(0));
					} catch (ParseException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					}
			return date1;
						
		};
	
	
	public void llamarRender(JTable table){
		Color fondoEncabezado = new Color(255,171,0);
		Color textoEncabezado = Color.black;
		
		for(int i=0; i<table.getColumnCount(); i++){
			
			table.getColumnModel().getColumn(i).setHeaderRenderer(new CaveceraTablaRenderer(fondoEncabezado,textoEncabezado,"centro","Arial","negrita",10));
			
			switch(tabla.getColumnClass(i).getSimpleName()){
				case "String": tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",11)); break;
//				Object = fechas
				case "Object": 		table.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",11))	;  break;
				case "Double": 		table.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","derecha","Arial","negrita",11))	;  break;
				case "Float": 		table.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","derecha","Arial","negrita",11))	;  break;
				case "Integer": 	table.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","derecha","Arial","negrita",11))	;  break;
				case "Deprecated": 	table.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","centro","Arial","negrita",11))	;  break;
				case "Boolean": 	table.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("CHB","centro","Arial","negrita",11))		;  break;
				default: 			table.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",11));  break;
			}
		}
		int x=80;
		
    	table.getTableHeader().setReorderingAllowed(false);
    	table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    	    
    	table.getColumnModel().getColumn(0 ).setMaxWidth(x-15);
    	table.getColumnModel().getColumn(0 ).setMinWidth(x-15);
    	table.getColumnModel().getColumn(1 ).setMaxWidth(x*4);
    	table.getColumnModel().getColumn(1 ).setMinWidth(x*4);
    	table.getColumnModel().getColumn(2 ).setMaxWidth(x);
    	table.getColumnModel().getColumn(2 ).setMinWidth(x);
    	                                         
    	table.getColumnModel().getColumn(3 ).setMaxWidth(x);
    	table.getColumnModel().getColumn(3 ).setMinWidth(x);		
    	table.getColumnModel().getColumn(4 ).setMaxWidth(x);
    	table.getColumnModel().getColumn(4 ).setMinWidth(x);
    	table.getColumnModel().getColumn(5 ).setMaxWidth(x/2);
    	table.getColumnModel().getColumn(5 ).setMinWidth(x/2);
    	table.getColumnModel().getColumn(6 ).setMaxWidth(x+20);
    	table.getColumnModel().getColumn(6 ).setMinWidth(x+20);
    	                                               
    	table.getColumnModel().getColumn(7 ).setMaxWidth(x*3);
    	table.getColumnModel().getColumn(7 ).setMinWidth(x*3);	
	}
	
	 ActionListener opBuscar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			Llenar_Tabla_Sugerido_Cedis();
			btnGuardar.setEnabled(true);
		} 
	 };
	 
	 
	 
		ActionListener opQuitar = new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent arg0) {
				
//	          quite edicion de celda de jtable
	            tabla.putClientProperty ("terminateEditOnFocusLost", Boolean.TRUE) ;
	            tablaEliminados.putClientProperty ("terminateEditOnFocusLost", Boolean.TRUE) ;
	            
				txtCodProd.setText("");
				txtDesc.setText("");
				trsfiltro.setRowFilter(RowFilter.regexFilter("", 0));
				trsfiltro.setRowFilter(RowFilter.regexFilter("", 1));
				
				txtCodProdRestaurar.setText("");
				txtDescRestaurar.setText("");
				trsfiltroRestaurar.setRowFilter(RowFilter.regexFilter("", 0));
				trsfiltroRestaurar.setRowFilter(RowFilter.regexFilter("", 1));
				
				if(arg0.getActionCommand().equals("Quitar")){
					movimiento(tabla, modeloEliminados, modelo);
				}else{
					movimiento(tablaEliminados, modelo, modeloEliminados);
				}

			}
		};
		
		ActionListener opReporte = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String fecha = new SimpleDateFormat("dd/MM/yyyy").format(c_fecha.getDate());	
				
				if(arg0.getActionCommand().equals("Reporte De Sugerido")){
					reporte(fecha,1,"Productos Del Pedido Sugerido Por Cedis");
				}else{
					reporte(fecha,0,"Productos Del Pedido Sugerido Negados Por Cedis");
				}
			}
		};
		
		
		
		public void reporte(String fecha,int status, String nombre_reporte){
			
			String basedatos="2.26";
			String vista_previa_reporte="no";
			int vista_previa_de_ventana=0;
			String comando="";
			String reporte = "";
			
				comando = "exec sp_Reporte_De_Sugeridos "+status+",'"+fecha+"','"+nombre_reporte+"'"  ;
				 reporte="Obj_Reporte_De_Pedido_De_Productos_Cedis.jrxml";
				 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
		}
		
		
		
		public void movimiento(JTable tb, DefaultTableModel model1, DefaultTableModel model2){
			
			for(int i=0; i<tb.getRowCount();i++){
				if(tb.isRowSelected(i)){
					
					Object[] vector = new Object[tb.getColumnCount()];
					for(int x=0; x<tb.getColumnCount(); x++){
						vector[x] = tb.getValueAt(i, x);
					}
					
					model1.addRow(vector);
					model2.removeRow(i);
					i--;
				}
			}
		}
	
	ActionListener opGuardar = new ActionListener(){
		@SuppressWarnings("unchecked")
		public void actionPerformed(ActionEvent e){
			
			try{
				
				trsfiltro.setRowFilter(RowFilter.regexFilter("", 0));
				trsfiltro.setRowFilter(RowFilter.regexFilter("", 1));
				
				Object[][] matriz1 = funcionTb(tabla);
				Object[][] matriz2 = funcionTb(tablaEliminados);
				
//		          quite edicion de celda de jtable
		            tabla.putClientProperty ("terminateEditOnFocusLost", Boolean.TRUE) ;
		            tablaEliminados.putClientProperty ("terminateEditOnFocusLost", Boolean.TRUE) ;
				
				if(tabla.getRowCount()<=0){
					JOptionPane.showMessageDialog(null, "No ha podido guardar, la tabla principal debe contener registros","Aviso",JOptionPane.WARNING_MESSAGE);
					return;
				}else{
					
					if(matriz1.toString().equals("null") || matriz2.toString().equals("null")){
						JOptionPane.showMessageDialog(null, "Los datos no pueden ser guardados por que algunas columnas seleccionadas estan vacias","Aviso",JOptionPane.WARNING_MESSAGE);
						return;
					}else{
						
						if(new GuardarSQL().Guardar_Sugerido_Sistema(matriz1,matriz2,folio_sugerido)){
							btnGuardar.setEnabled(false);
							String fecha = new SimpleDateFormat("dd/MM/yyyy").format(cargar_fecha());		
							reporte(fecha,1,"Productos Del Pedido Sugerido Por Cedis");
							limpiarTablas();
						}else{
							JOptionPane.showMessageDialog(null, "Los datos no pueden ser guardados","Aviso",JOptionPane.WARNING_MESSAGE);
							return;
						}
					}
				}
				
			}catch(Exception e1){
				e1.getMessage();
				return;
			}
		}
	};
	
	public Object[][] funcionTb(JTable llenar_tabla){
		
		Object[][] matriz = new Object[llenar_tabla.getRowCount()][llenar_tabla.getColumnCount()];
		
		for(int i=0; i<llenar_tabla.getRowCount(); i++){
			for(int j=0; j<llenar_tabla.getColumnCount(); j++){
				
				if(llenar_tabla.getValueAt(i, 5).toString().equals("true")){
					if(llenar_tabla.getValueAt(i, 6).toString().equals("")){
					
						matriz=null;
						JOptionPane.showMessageDialog(null, "La fila con el codigo de producto   '"+llenar_tabla.getValueAt(i, 0)+"'   esta seleccionada y no asigno valor en:   'Sugerido Cedis'","Aviso",JOptionPane.WARNING_MESSAGE);
						return null;
					}else{
						
						if(llenar_tabla.getValueAt(i, 7).toString().equals("")){
								matriz=null;
								JOptionPane.showMessageDialog(null, "La fila con el codigo de producto   '"+llenar_tabla.getValueAt(i, 0)+"'   esta seleccionada y no se agrego:   'Observaciones'","Aviso",JOptionPane.WARNING_MESSAGE);
								return null;
						}else{
								matriz[i][j] = llenar_tabla.getValueAt(i, j);
						}
					}
				}else{
						matriz[i][j] = llenar_tabla.getValueAt(i, j);
				}
			}
		}
		return matriz;
	}
	
	public void limpiarTablas(){
		while(tabla.getRowCount()>0){
			modelo.removeRow(0);
		}
		while(tablaEliminados.getRowCount()>0){
			modeloEliminados.removeRow(0);
		}
	}
	
	int folio_sugerido=0;
	@SuppressWarnings("resource")
	public void Llenar_Tabla_Sugerido_Cedis(){
		
		limpiarTablas();
		
		Statement s;
		ResultSet rs;
		
		try {
			String queryDeEleccionDeConsulta = ("if exists(select folio from tb_sugeridos_cedis where convert(smalldatetime,convert(varchar(20),fecha_mov,103)) =  convert(smalldatetime,convert(varchar(20),getdate(),103))) "
												+ "		begin	select 'true' as existe		end "
												+ "	else "
												+ "		begin 	select 'false' as existe	end ");
			
			s = new Connexion().conexion().createStatement();
			rs = s.executeQuery(queryDeEleccionDeConsulta);
			
			boolean existe = false;
			while (rs.next()){
				existe = rs.getBoolean("existe");
			}
			
			folio_sugerido=0;
			if(existe){
				rs = s.executeQuery("exec sp_select_sugeridos_cedis_SCOI");
				
				Object [] fila = new Object[9];
				while (rs.next()){
					
					folio_sugerido=rs.getInt("folio           ".trim());
					
					fila[0]=rs.getString("codigo_producto ".trim());
					fila[1]=rs.getString("descripcion     ".trim());
					fila[2]=rs.getInt("existencia_cedis".trim());
					fila[3]=rs.getInt("transferencia   ".trim());
					fila[4]=rs.getInt("sugerido_sistema".trim());
					fila[5]=rs.getBoolean("selector        ".trim());
					fila[6]=rs.getInt("segerido_cedis  ".trim());
					fila[7]=rs.getString("observacion     ".trim());
					fila[8]=rs.getInt("status     ".trim());
					
					if(Integer.valueOf(fila[8].toString())==1){
						modelo.addRow(fila);
					}else{
						modeloEliminados.addRow(fila);
					}
				}
				
			}else{
				
				s = new Connexion().conexion_IZAGAR().createStatement();
				
				rs = s.executeQuery("select * from (select productos.cod_prod " +
						"		       					,productos.descripcion " +
						"		      				 	,prodestab.exist_piezas " +
						"		     				 	,sum(isnull(entysal.cantidad,0)) as transferidos_desde_cedis " +
						"		    				  	,sum(isnull(entysal.cantidad,0))- prodestab.exist_piezas as sugerido " +
						"							 from productos " +
						"							inner join prodestab on prodestab.cod_prod=productos.cod_prod " +
						"							left outer join entysal with (nolock) on entysal.cod_prod=productos.cod_prod and entysal.transaccion='35' " +
						"							and entysal.fecha>(getdate()-30) and entysal.cod_estab=7 " +
						"							where prodestab.cod_estab=7 and productos.clase_producto = 1 " +
						"							group by  productos.cod_prod,productos.descripcion,prodestab.exist_piezas) a " +
						"			where  (a.sugerido >= 10) and not (a.exist_piezas = a.transferidos_desde_cedis) or (a.exist_piezas = 0 and a.transferidos_desde_cedis = 0 ) " +
						"			order by a.sugerido desc");
				
				Object [] fila = new Object[8];
				while (rs.next())
				{ 
				   fila[0] = rs.getString(1).trim()+"  ";
				   fila[1] = "  "+rs.getString(2).trim();
				   fila[2] = rs.getString(3).trim(); 
				   fila[3] = rs.getString(4).trim(); 
				   fila[4] = rs.getString(5).trim(); 
				   fila[5] = "false"; 
				   fila[6] = "";
				   fila[7] = "";
				   
				   modelo.addRow(fila); 
				}
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en: Cat_Sugerido_Sistema en la funcion (Llenar_Tabla_Sugerido_Cedis)  SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	KeyListener opFiltroCodProd = new KeyListener(){
		@SuppressWarnings("unchecked")
		public void keyReleased(KeyEvent arg0) {
			trsfiltro.setRowFilter(RowFilter.regexFilter(txtCodProd.getText().toUpperCase(), 0));
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}		
	};
	
	KeyListener opFiltroDesc = new KeyListener(){
		@SuppressWarnings("unchecked")
		public void keyReleased(KeyEvent arg0) {
			trsfiltro.setRowFilter(RowFilter.regexFilter(txtDesc.getText().toUpperCase(), 1));
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}		
	};
	
	KeyListener opFiltroCodProdRestaurar = new KeyListener(){
		@SuppressWarnings("unchecked")
		public void keyReleased(KeyEvent arg0) {
			trsfiltroRestaurar.setRowFilter(RowFilter.regexFilter(txtCodProdRestaurar.getText().toUpperCase(), 0));
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}		
	};
	
	KeyListener opFiltroDescRestaurar = new KeyListener(){
		@SuppressWarnings("unchecked")
		public void keyReleased(KeyEvent arg0) {
			trsfiltroRestaurar.setRowFilter(RowFilter.regexFilter(txtDescRestaurar.getText().toUpperCase(), 1));
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}		
	};
	
	public static void main(String [] arg){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Pedido_De_Productos_Sugerido_Cedis().setVisible(true);
		}catch(Exception e){
			System.err.println("Error :"+ e.getMessage());
		}
	}
}
