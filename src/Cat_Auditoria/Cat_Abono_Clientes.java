package Cat_Auditoria;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

import Conexiones_SQL.Connexion;
import Obj_Principal.Componentes;

import com.toedter.calendar.JDateChooser;

@SuppressWarnings("serial")
public class Cat_Abono_Clientes extends JFrame{
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	int fila = 0;
	int columna = 2;
	
	 static String[][] matriz = new String[3][4];
     static Object[][] data = {
             {"pesos",new Integer(1), new Integer(5), new Boolean(false)},
             {"dolar",new Double(12.5), new Integer(3), new Boolean(true)},
         };
    public static DefaultTableModel tabla_model_cobro = new DefaultTableModel(/*new Obj_Clientes().get_tabla_model()null*/data,
            new String[]{"Efectivo", "Valor", "Pago", "Importe"}){
                    
			@SuppressWarnings({ "rawtypes" })
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
                            case 2  : return true; 
                            case 3  : return false; 
                    }
                     return false;
             }
    };
	
    public static DefaultTableModel tabla_model_ticket = new DefaultTableModel(/*new Obj_Clientes().get_tabla_model()*/null,
            new String[]{"Ticket", "Fecha Inicial", "Fecha Limite", "Saldo"}){
                    
			@SuppressWarnings({ "rawtypes" })
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
    
    public static DefaultTableModel tabla_model_abonos = new DefaultTableModel(/*new Obj_Clientes().get_tabla_model()*/null,
            new String[]{"Cantidad", "Fecha De Abono", "Establecimiento", "Recibio"}){
                    
			@SuppressWarnings({ "rawtypes" })
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
    
    JTable tabla_cobros = new JTable(tabla_model_cobro);
	JScrollPane panelScroll_cobros = new JScrollPane(tabla_cobros);
	
    JTable tabla_ticket = new JTable(tabla_model_ticket);
	JScrollPane panelScroll_ticket = new JScrollPane(tabla_ticket);
	
    JTable tabla_abonos = new JTable(tabla_model_abonos);
	JScrollPane panelScroll_abonos = new JScrollPane(tabla_abonos);

	JTextField txtAsignacion = new JTextField();
	JTextField txtCajera = new JTextField();
	
	JTextField txtFolioCliente = new JTextField();
	JTextField txtCliente = new JTextField();
	JTextField txtDomicilio = new JTextField();
	
	JTextField txtFolioTiket = new JTextField();
	JTextField txtAbono = new JTextField();
	
	JDateChooser fecha = new JDateChooser();
	
	JButton btnBuscar = new JButton("");
	JButton btnGuardarAbono = new JButton("Guardar");
	JButton btnNuevaCuenta = new JButton("Cuenten Nueva");
	
	JLabel lblSignoCambio = new JLabel("Su Cambio: $");
	JLabel lblCambio = new JLabel("0000.00");
	
	JLabel lblSignoSaldo = new JLabel("Saldo: $");
	JLabel lblSaldo = new JLabel("0000.00");
	
	public Cat_Abono_Clientes(){
		this.setTitle("Abonos Clientes");
		
    	
		
		tabla_cobros.setFont(new Font("arial", Font.BOLD, 25));
    	tabla_cobros.setRowHeight(30);//tamaño de fila

		lblSignoCambio.setFont(new Font("arial", Font.BOLD, 35));
		lblCambio.setFont(new Font("arial", Font.BOLD, 35));
		
		lblSignoSaldo.setFont(new Font("arial", Font.BOLD, 35));
		lblSaldo.setFont(new Font("arial", Font.BOLD, 35));
		
		lblSignoCambio.setForeground(new Color(105,105,105));
		lblCambio.setForeground(new Color(105,105,105));
		
		lblSignoSaldo.setForeground(new Color(105,105,105));
		lblSaldo.setForeground(new Color(105,105,105));
		
		init_tabla();
		
		int x=20; int y=15; int ancho=80;
		
		JTextField txtCajera = new JTextField();
		
		JTextField txtFolioCliente = new JTextField();
		final JTextField txtCliente = new JTextField();
		JTextField txtDomicilio = new JTextField();
		
		JTextField txtFolioTiket = new JTextField();
		final JTextField txtAbono = new JTextField();
		
		JDateChooser fecha = new JDateChooser();
		
		JButton btnBuscar = new JButton(new ImageIcon("Iconos/zoom_icon&16.png"));
		JButton btnGuardarAbono = new JButton("Guardar");
		JButton btnNuevaCuenta = new JButton("Cuenten Nueva");
		
		panel.add(lblSignoCambio).setBounds(x+610, 15, ancho*3+20, 40);
		panel.add(lblCambio).setBounds(x+850,15,ancho*2+30,40);
		
		panel.add(lblSignoSaldo).setBounds(x+610, 60, ancho*3+20, 40);
		panel.add(lblSaldo).setBounds(x+760,60,ancho*2+30,40);
		
		panel.add(new JLabel("Asignacion: ")).setBounds(x, y, ancho, 20);
		panel.add(txtAsignacion).setBounds(x+70,y,ancho,20);
		
		panel.add(new JLabel("Cajera (o): ")).setBounds(x+195, y, ancho, 20);
		panel.add(txtCajera).setBounds(x+260,y,(ancho*4)+20,20);
		
		panel.add(new JLabel("Folio Cliente: ")).setBounds(x, y+=25, ancho, 20);
		panel.add(txtFolioCliente).setBounds(x+70,y,ancho,20);
		panel.add(btnBuscar).setBounds(x+150,y,30,20);
		
		panel.add(new JLabel("Cliente: ")).setBounds(x+195, y, ancho, 20);
		panel.add(txtCliente).setBounds(x+260,y,(ancho*4)+20,20);
		
		panel.add(new JLabel("Ticket: ")).setBounds(x, y+=25, ancho, 20);
		panel.add(txtFolioTiket).setBounds(x+70,y,ancho,20);
		
		panel.add(new JLabel("Domicilio: ")).setBounds(x+195, y, ancho, 20);
		panel.add(txtDomicilio).setBounds(x+260,y,(ancho*4)+20,20);
		
		panel.add(new JLabel("Abono: ")).setBounds(x, y+=25, ancho, 20);
		panel.add(txtAbono).setBounds(x+70,y,ancho,20);
		
		panel.add(new JLabel("Fecha Limite: ")).setBounds(x+195, y, ancho, 20);
		panel.add(fecha).setBounds(x+260, y, ancho+20, 20);
		panel.add(btnGuardarAbono).setBounds(x+365,y,ancho+20,20);
		panel.add(btnNuevaCuenta).setBounds(x+470,y,ancho+50,20);
		
		panel.add(panelScroll_cobros).setBounds(x, y+=40, 970, 200);
		panel.add(panelScroll_ticket).setBounds(x, y+=220, 970, 120);
		panel.add(panelScroll_abonos).setBounds(x, y+=140, 970, 200);
		
		txtAsignacion.setEditable(false);
		txtCajera.setEditable(false);
		txtCliente.setEditable(false);
		txtDomicilio.setEditable(false);
		
		

		txtAbono.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				if(txtAbono.getText().equals("")){
//					aviso
				}else{
//					validar ke tenga cantidad en txtAbono
					tabla_cobros.editCellAt(fila, columna);
					Component aComp=tabla_cobros.getEditorComponent();
					aComp.requestFocus();
				}
			}
		});
		
		tabla_cobros.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent arg0) {}
			@Override
			public void keyReleased(KeyEvent arg0) {
						
				int cantidadDeFilas = tabla_cobros.getRowCount();
				fila+=1;
				
				if(fila == cantidadDeFilas){
					fila=0;
				}
				
				tabla_cobros.editCellAt(fila, columna);
				Component aComp=tabla_cobros.getEditorComponent();
				aComp.requestFocus();

				
//				if(fila<tabla_cobros.getRowCount()){
//					fila=fila+1;
//				}else{
//					fila=0;
//				}
//				tabla_cobros.requestFocus();
//				tabla_cobros.editCellAt(fila, columna);
			}
			@Override
			public void keyPressed(KeyEvent arg0) {}
		});
		
		btnBuscar.addActionListener(opBuscar);
		cont.add(panel);
		this.setSize(1024,768);
	}
	
	public void init_tabla(){
    	
		int x=250;
    	this.tabla_cobros.getTableHeader().setReorderingAllowed(false) ;
    	
    	this.tabla_cobros.getColumnModel().getColumn(0).setMaxWidth(x);
    	this.tabla_cobros.getColumnModel().getColumn(0).setMinWidth(x);		
    	this.tabla_cobros.getColumnModel().getColumn(1).setMaxWidth(x);
    	this.tabla_cobros.getColumnModel().getColumn(1).setMinWidth(x);
    	this.tabla_cobros.getColumnModel().getColumn(2).setMaxWidth(x);
    	this.tabla_cobros.getColumnModel().getColumn(2).setMinWidth(x);
    	this.tabla_cobros.getColumnModel().getColumn(3).setMaxWidth(x);
    	this.tabla_cobros.getColumnModel().getColumn(3).setMinWidth(x);

    	TableCellRenderer render = new TableCellRenderer() {
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, 
					boolean hasFocus, int row, int column) {
					
							JLabel lbl = new JLabel(value == null? "": value.toString());
							
							lbl.setFont(new Font("arial", Font.BOLD, 25));
							
							if(row%2==0){
									lbl.setOpaque(true); 
									lbl.setBackground(new java.awt.Color(177,177,177));
							} 
							
//							alinear los valores de la celda dependiendo el tipo de dato que contenga
							if(value instanceof Integer){ 
								lbl.setText(""+(Integer)value);   
								lbl.setHorizontalAlignment(SwingConstants.CENTER); 
					        }
							if(value instanceof Double){ 
								lbl.setText(""+(Double)value);   
								lbl.setHorizontalAlignment(SwingConstants.CENTER); 
					        }
							if(value instanceof Float){ 
								lbl.setText(""+(Float)value);   
								lbl.setHorizontalAlignment(SwingConstants.CENTER); 
					        }
							if (value instanceof String){ 
					            lbl.setText((String)value); 
					            lbl.setHorizontalAlignment(SwingConstants.LEFT); 
							}
//							si esta seleccionara pinta color naranja
							if(isSelected){ 
								lbl.setOpaque(true);
								lbl.setBackground(new java.awt.Color(186,143,73));
								}	
							
							return lbl; 
					} 
			}; 

		for(int i = 0; i<tabla_cobros.getColumnCount(); i++){
			this.tabla_cobros.getColumnModel().getColumn(i).setCellRenderer(render); 
		}
		for(int i = 0; i<tabla_ticket.getColumnCount(); i++){
			this.tabla_ticket.getColumnModel().getColumn(i).setCellRenderer(render); 
		}
		for(int i = 0; i<tabla_abonos.getColumnCount(); i++){
			this.tabla_abonos.getColumnModel().getColumn(i).setCellRenderer(render); 
		}
    }
	
	ActionListener opBuscar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			if(txtFolioTiket.getText().equals("")){
				new Cat_Filtro_Clientes().setVisible(true);
			}else{
//				ingresar folio_cliente directo
				buscar_cliente(Integer.valueOf(txtFolioTiket.getText()));
			}
		}
	};
	
	public static void main(String [] arg){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Abono_Clientes().setVisible(true);
		}catch(Exception e){
			System.err.println("Error :"+ e.getMessage());
		}
	}
	
	public void buscar_cliente(int folio_cliente){
		
	}
	
	//Filtro Clientes
	public class Cat_Filtro_Clientes extends JFrame{
		
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
		JTextField txtBuscar = new Componentes().text(new JTextField(), "Buscar", 130, "String");
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Cat_Filtro_Clientes(){
			this.setTitle("Filtro Empleados");
			
			txtBuscar.addKeyListener(new KeyAdapter() { 
				public void keyReleased(final KeyEvent e) { 
	                filtro(); 
	            } 
	        });
		
			trsfiltro = new TableRowSorter(model); 
			tabla.setRowSorter(trsfiltro);  
			
			campo.add(getPanelTabla()).setBounds(10,70,365,450);
			
			agregar(tabla);
			
			campo.add(lblBuscar).setBounds(30,30,70,20);
			campo.add(txtBuscar).setBounds(95,30,215,20);
			
			cont.add(campo);
			
			this.setSize(390,570);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			
			
		}
		private void agregar(final JTable tbl) {
	        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
		        public void mouseClicked(MouseEvent e) {
		        	if(e.getClickCount() == 2){
		        		
		        		int fila = tabla.getSelectedRow();
		    			String folio =  tabla.getValueAt(fila, 0).toString().trim();
		    			String nombre =  tabla.getValueAt(fila, 1).toString().trim();
		    			dispose();
		    			
		    			txtFolioTiket.setText(folio);
		    			txtCliente.setText(nombre);
		    			
//		    			buscar cliente para abonos
		    			buscar_cliente(Integer.valueOf(folio));
		        	}
		        }
	        });
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
			tabla.getColumnModel().getColumn(1).setHeaderValue("Cliente");
			tabla.getColumnModel().getColumn(1).setMaxWidth(185);
			tabla.getColumnModel().getColumn(1).setMinWidth(185);
			tabla.getColumnModel().getColumn(2).setHeaderValue("Direccion");
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
				rs = s.executeQuery("sp_select_permiso_checador_filtro_empleados" );
				
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
	}
}
