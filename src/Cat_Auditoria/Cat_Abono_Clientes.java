package Cat_Auditoria;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.PrintJob;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.GrayFilter;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

import Conexiones_SQL.Connexion;
import Obj_Auditoria.Obj_Abono_Clientes;
import Obj_Auditoria.Obj_Clientes;
import Obj_Principal.Componentes;

import com.toedter.calendar.JDateChooser;

@SuppressWarnings("serial")
public class Cat_Abono_Clientes extends JFrame{
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	int fila = 0;
	int columna = 2;
	
	JLabel lblF2 = new JLabel("F2 => Abrir Filtro De Clientes");
	JLabel lblF5 = new JLabel("F5 => Imprimir Ticket A Detalle");
	JLabel lblF9 = new JLabel("F9 => Generer Abono");
	
	
	
	 static String[][] matriz = new String[3][4];
     
	 static Object[][] data = {
             {"pesos",new Integer(1), new Integer(0),new Integer(0)},
             {"dolar",new Double(12.92), new Integer(0),new Integer(0)},
         };
	 
	 static Object[][] dataTicket = {
		 {"060000000001", "01/01/1900", "02/01/1900",new Integer(240)},
         {"060000000002", "01/01/1900", "02/01/1900",new Integer(850)},
         {"060000000002", "01/01/1900", "02/01/1900",new Integer(850)},
     };
	 
	 static Object[][] dataAbono = {
         {new Double(45.5), "01/01/1900", "depa","edgar"},
         {new Double(150), "01/01/1900", "ferre","edgar"},
         {new Double(45.5), "01/01/1900", "depa","edgar"},
         {new Double(150), "01/01/1900", "ferre","edgar"},
         {new Double(150), "01/01/1900", "ferre","edgar"},
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

	
	JLabel lblAsignacion = new JLabel("Asignacion: ");
	JLabel lblCajero = new JLabel("Cajera (o): ");
	JLabel lblFolioCli = new JLabel("Folio Cliente: ");
	JLabel lblCliente = new JLabel("Cliente: ");
	JLabel lblTicket = new JLabel("Ticket: ");
	JLabel lblDomicilio = new JLabel("Domicilio: ");
	JLabel lblAbono = new JLabel("Abono: ");
	JLabel lblFechaLim = new JLabel("Fecha Limite: ");
	
	
	JTextField txtAsignacion = new JTextField("011");
	JTextField txtCajera = new JTextField("EDGAR EDUARDO JIMENEZ MOLINA");
	
	JTextField txtFolioCliente = new Componentes().text(new JTextField(), "Folio de Cliente", 10, "Int");
	JTextField txtCliente = new JTextField();
	JTextField txtDomicilio = new JTextField();
	
	JTextField txtTiket = new JTextField();
	JTextField txtAbono = new Componentes().text(new JTextField(), "Cantidad a Abonar", 15, "Double");
	
	JDateChooser fecha = new JDateChooser();
	
	JButton btnBuscar = new JButton("");
	JButton btnGuardarAbono = new JButton("Guardar");
	JButton btnNuevaCuenta = new JButton("Cuenta Nueva");
	
	JLabel lblDineroFaltente = new JLabel("Faltante: $");
	JLabel lblFaltente = new JLabel("0.0");
	
	JLabel lblSignoSaldo = new JLabel("Saldo: $");
	JLabel lblSaldo = new JLabel("0.0");
	
	JLabel lblImporteTotal = new JLabel("Importe Total: $");
	JLabel lblImporte = new JLabel("0.0");
	
	JLabel lblSignoCambio = new JLabel("Su Cambio: $");
	JLabel lblCambio = new JLabel("0.0");
	
	DecimalFormat formato = new DecimalFormat("#0.00");
	Border blackline;
	public Cat_Abono_Clientes(){
		
		cont.setBackground(Color.black);
		
		blackline = BorderFactory.createLineBorder(new java.awt.Color(115,148,255));
		this.setTitle("Abonos Clientes");
		this.panel.setBorder(BorderFactory.createTitledBorder(blackline, "Alta de Empleados"));

		tabla_cobros.setFont(new Font("arial", Font.BOLD, 25));
    	tabla_cobros.setRowHeight(30);//tamaño de fila
    	tabla_ticket.setRowHeight(30);//tamaño de fila
    	tabla_abonos.setRowHeight(30);//tamaño de fila

		lblSignoCambio.setFont(new Font("arial", Font.BOLD, 25));
		lblCambio.setFont(new Font("arial", Font.BOLD, 25));
		
		lblSignoSaldo.setFont(new Font("arial", Font.BOLD, 25));
		lblSaldo.setFont(new Font("arial", Font.BOLD, 25));
		
		lblImporteTotal.setFont(new Font("arial", Font.BOLD, 25));
		lblImporte.setFont(new Font("arial", Font.BOLD, 25));
		
		lblDineroFaltente.setFont(new Font("arial", Font.BOLD, 25));
		lblFaltente.setFont(new Font("arial", Font.BOLD, 25));
		
		lblSignoCambio.setForeground(Color.white);
		lblCambio.setForeground(Color.white);
		
		lblSignoSaldo.setForeground(Color.white);
		lblSaldo.setForeground(Color.white);
		
		lblImporteTotal.setForeground(Color.white);
		lblImporte.setForeground(Color.white);
		
		lblDineroFaltente.setForeground(Color.white);
		lblFaltente.setForeground(Color.white);
		
		lblAsignacion.setForeground(Color.white);
		lblCajero.setForeground(Color.white);
		lblFolioCli.setForeground(Color.white);
		lblCliente.setForeground(Color.white);
		lblTicket.setForeground(Color.white);
		lblDomicilio.setForeground(Color.white);
		lblAbono.setForeground(Color.white);
		lblFechaLim.setForeground(Color.white);
		
		lblF2.setForeground(Color.white);
		lblF5.setForeground(Color.white);
		lblF9.setForeground(Color.white);
		
		init_tabla();
		
		int x=20; int y=15; int ancho=80;
		
		JButton btnBuscar = new JButton(new ImageIcon("Iconos/zoom_icon&16.png"));
		JButton btnGuardarAbono = new JButton("Guardar");
		JButton btnNuevaCuenta = new JButton("Cuenten Nueva");
		
		panel.add(lblAsignacion).setBounds(x, y, ancho, 20);
		panel.add(txtAsignacion).setBounds(x+70,y,ancho,20);
		
		panel.add(lblCajero).setBounds(x+195, y, ancho, 20);
		panel.add(txtCajera).setBounds(x+260,y,(ancho*4)+20,20);
		
		panel.add(lblF2).setBounds(x+620,y,ancho*4,20);
		
		panel.add(lblFolioCli).setBounds(x, y+=25, ancho, 20);
		panel.add(txtFolioCliente).setBounds(x+70,y,ancho,20);
		panel.add(btnBuscar).setBounds(x+150,y,30,20);
		
		panel.add(lblCliente).setBounds(x+195, y, ancho, 20);
		panel.add(txtCliente).setBounds(x+260,y,(ancho*4)+20,20);
		
		panel.add(lblF5).setBounds(x+620,y,ancho*4,20);
		
		panel.add(lblTicket).setBounds(x, y+=25, ancho, 20);
		panel.add(txtTiket).setBounds(x+70,y,ancho,20);
		
		panel.add(lblDomicilio).setBounds(x+195, y, ancho, 20);
		panel.add(txtDomicilio).setBounds(x+260,y,(ancho*4)+20,20);
		
		panel.add(lblF9).setBounds(x+620,y,ancho*4,20);

		
		panel.add(lblAbono).setBounds(x, y+=25, ancho, 20);
		panel.add(txtAbono).setBounds(x+70,y,ancho,20);
		
		panel.add(lblFechaLim).setBounds(x+195, y, ancho, 20);
		panel.add(fecha).setBounds(x+260, y, ancho+20, 20);
		panel.add(btnGuardarAbono).setBounds(x+365,y,ancho+20,20);
		panel.add(btnNuevaCuenta).setBounds(x+470,y,ancho+50,20);
		
		panel.add(panelScroll_cobros).setBounds(x, y+=30, 970, 170);
		panel.add(panelScroll_ticket).setBounds(x, y+=240, 970, 117);
		panel.add(panelScroll_abonos).setBounds(x, y+=130, 970, 177);

		panel.add(lblDineroFaltente).setBounds(x, 290, ancho*3+20, 40);
		panel.add(lblFaltente).setBounds(x+290,290,ancho*2+30,40);
		
		panel.add(lblSignoSaldo).setBounds(x, 320, ancho*3+20, 40);
		panel.add(lblSaldo).setBounds(x+290,320,ancho*2+30,40);
		
		panel.add(lblImporteTotal).setBounds(x+530, 290, ancho*3+20, 40);
		panel.add(lblImporte).setBounds(x+820,290,ancho*2+30,40);
		
		panel.add(lblSignoCambio).setBounds(x+530, 320, ancho*3+20, 40);
		panel.add(lblCambio).setBounds(x+820,320,ancho*2+30,40);
		
		tabla_cobros.setEnabled(false);

		txtAsignacion.setEditable(false);
		txtCajera.setEditable(false);
		txtCliente.setEditable(false);
		txtDomicilio.setEditable(false);
		txtTiket.setEditable(false);
		txtAbono.setEditable(false);
		
//      asigna el foco al JTextField deseado al arrancar la ventana
        this.addWindowListener(new WindowAdapter() {
                public void windowOpened( WindowEvent e ){
               	 txtFolioCliente.requestFocus();
             }
        });

		txtAbono.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(txtTiket.getText().equals("")){
					
					JOptionPane.showMessageDialog(null, "Genere ticket nuevo o seleccione uno de la tabla","Aviso",JOptionPane.INFORMATION_MESSAGE);
					return;
					
				}else{
					
					if(txtAbono.getText().equals("")){
						JOptionPane.showMessageDialog(null, "Ingrese Cantidad Que Desea Abonar","Aviso",JOptionPane.INFORMATION_MESSAGE);
						return;
					}else{
						tabla_cobros.setEnabled(true);
						tabla_cobros.editCellAt(fila, columna);
						Component aComp=tabla_cobros.getEditorComponent();
						aComp.requestFocus();
					}
				}
			}
		});
		
		tabla_cobros.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent arg0) {}
			public void keyReleased(KeyEvent arg0) {
						if(CalcularImporte()==false){
								JOptionPane.showMessageDialog(null, "Se introdujo un valor no valido","Aviso",JOptionPane.INFORMATION_MESSAGE);
								tabla_cobros.setValueAt(0, fila, columna);
								return;
						}
			}
			public void keyPressed(KeyEvent arg0) {}
		});
		
		//  filtro cliente
	    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
	       KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), "filtrar");
	    
	    getRootPane().getActionMap().put("filtrar", new AbstractAction(){
	        @Override
	        public void actionPerformed(ActionEvent e)
	        {
	        	new Cat_Filtro_Clientes().setVisible(true);
	        }	    
	    });
	    
		//  cobrar 
	    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
	       KeyStroke.getKeyStroke(KeyEvent.VK_F9, 0), "cobrar");
	    
	    getRootPane().getActionMap().put("cobrar", new AbstractAction(){
	        @Override
	        public void actionPerformed(ActionEvent e)
	        {
				if(CalcularImporte()==false){
					
					JOptionPane.showMessageDialog(null, "Se introdujo un valor no valido","Aviso",JOptionPane.INFORMATION_MESSAGE);
					tabla_cobros.setValueAt(0, fila, columna);
					return;
					
				}else{
					
		        	tabla_cobros.setEnabled(false);
		        	double abono = Double.valueOf(txtAbono.getText());
		        	double importeTotal = Double.valueOf(lblImporte.getText());
		        	
		        	
//                  quite edicion de celda de jtable
                    tabla_cobros.putClientProperty ("terminateEditOnFocusLost", Boolean.TRUE) ;
                    
		        	if(importeTotal<abono){
		        		JOptionPane.showMessageDialog(null, "El importe es insuficiente","Aviso",JOptionPane.INFORMATION_MESSAGE);
						return;
		        	}else{
		        		lblCambio.setText((importeTotal-abono)+"");
		        	}
		        	
		        	Obj_Abono_Clientes abonar = new Obj_Abono_Clientes();
		        	
		        	abonar.setAsignacion(txtAsignacion.getText().toUpperCase().trim());
		        	abonar.setCajero(txtCajera.getText().toUpperCase().trim());
		        	abonar.setFolio_cliente(Integer.valueOf(txtFolioCliente.getText().trim()));
		        	abonar.setTicket(txtTiket.getText().toUpperCase().trim());
		        	abonar.setAbono(Double.valueOf(txtAbono.getText().trim()));
		        	
		        	if(fecha.getDate() == null){
		        		abonar.setFecha_fin("01/01/1900 00:00");
		        	}else{
		        		abonar.setFecha_fin(new SimpleDateFormat("dd/MM/yyyy").format(fecha.getDate()));
		        	}
		        	
		        	if(abonar.guardarTickets()){

		        		
		        		
		        		
//		        	imprimir ticket 
						 new Imprime_Ticket_abono(txtTiket.getText().toUpperCase()).setVisible(true);
		        		
		        		
		        		
		        		
//                    quite edicion de celda de jtable
                      tabla_cobros.putClientProperty ("terminateEditOnFocusLost", Boolean.TRUE) ;

		        		txtFolioCliente.setText("");
		        		txtCliente.setText("");
		        		txtDomicilio.setText("");
		        		txtTiket.setText("");
		        		txtAbono.setText("");
		        		fecha.setDate(null);
		        		txtCliente.requestFocus();
		        		
		        		while(tabla_cobros.getRowCount()>0)
		        			tabla_model_cobro.removeRow(0);
		        		while(tabla_ticket.getRowCount()>0)
		        			tabla_model_ticket.removeRow(0);
		        		while(tabla_abonos.getRowCount()>0)
		        			tabla_model_abonos.removeRow(0);
		        		
//                        Object [][] lista_tabla = new Obj_Traer_Checador().get_tabla_model();
                        Object [][] lista_tabla = data;

		        		String[] fila = new String[4];
                                for(int i=0; i<lista_tabla.length; i++){
                                        fila[0] = lista_tabla[i][0]+"";
                                        fila[1] = lista_tabla[i][1]+"";
                                        fila[2] = lista_tabla[i][2]+"";
                                        fila[3] = lista_tabla[i][3]+"";
                                        tabla_model_cobro.addRow(fila);
                                }
                                
                        txtFolioCliente.requestFocus();
                                
		        	}else{
		        		JOptionPane.showMessageDialog(null, "El abono no a sido realizado con exito","Error",JOptionPane.ERROR_MESSAGE);
						return;
		        	}
				}
	        }
	    });
	    
	//  Ticket a detalle 
	    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
	       KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0), "ticketDetalle");
	    
	    getRootPane().getActionMap().put("ticketDetalle", new AbstractAction(){
	        @Override
	        public void actionPerformed(ActionEvent e)
	        {
	        	if(txtTiket.getText().equals("")){
	        		System.out.println("aviso");
	        	}else{
		        	System.out.println("generar consulta para imprimir ticket detallado con abonos y fechas");
	        	}
	        }
	    });
	    
	    txtFolioCliente.addActionListener(opBuscar);
		btnBuscar.addActionListener(opBuscar);
		btnNuevaCuenta.addActionListener(opGenerarNuevaCuenta);
		
		SELECCION_TICKET(tabla_ticket);
		
		cont.add(panel);

		this.setSize(1024,720);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
	}
	
	@SuppressWarnings("unused")
	private boolean Validar(int fila, int columna) { 
		String valor=""; 
		
			if(tabla_cobros.getValueAt(fila,columna)==null) { 
				return false; 
			}else{ 
				
//				double numero =0;
				try{
//					numero = Double.valueOf(tabla_cobros.getValueAt(fila, columna).toString().trim());
					return true;
				}catch(NumberFormatException e){
				 return false;
				}
			} 
	}
	
	public boolean CalcularImporte(){
		boolean valor=false;
		if(Validar(fila,columna)){
			
			int cantidadDeFilas = tabla_cobros.getRowCount();
			fila+=1;

				if(fila == cantidadDeFilas){	fila=0;		}
		
				tabla_cobros.editCellAt(fila, columna);
				Component aComp=tabla_cobros.getEditorComponent();
				aComp.requestFocus();
		
				double totalDelImporte=0;
				for(int i=0; i<=tabla_cobros.getRowCount()-1; i++){
						tabla_cobros.setValueAt((Double.valueOf(tabla_cobros.getValueAt(i, 1).toString().trim())*Double.valueOf(tabla_cobros.getValueAt(i, 2).toString().trim())), i, 3);
						totalDelImporte = totalDelImporte + Double.valueOf(tabla_cobros.getValueAt(i, 3).toString().trim());
				}
				lblImporte.setText(totalDelImporte+"");
				
			
				
				
				if(totalDelImporte - Double.valueOf(txtAbono.getText()) < 0){
					lblDineroFaltente.setForeground(Color.red);
					lblFaltente.setForeground(Color.red);
					lblSignoCambio.setForeground(Color.white);
					lblCambio.setForeground(Color.white);
					
					 lblCambio.setText("0.0");
					 lblFaltente.setText(""+formato.format((totalDelImporte - Double.valueOf(txtAbono.getText()))*-1));
				}else{
					lblDineroFaltente.setForeground(Color.white);
					lblFaltente.setForeground(Color.white);
					lblSignoCambio.setForeground(Color.green);
					lblCambio.setForeground(Color.green);
					 lblCambio.setText(""+(totalDelImporte - Double.valueOf(txtAbono.getText())));
					 lblFaltente.setText("0.0");
				}
				valor = true;
		}
		return valor;
	}
	
	ActionListener opBuscar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			if(txtFolioCliente.getText().equals("")){
				new Cat_Filtro_Clientes().setVisible(true);
			}else{
//				ingresar folio_cliente directo
				buscar_cliente(Integer.valueOf(txtFolioCliente.getText()));
			}
		}
	};
	
	ActionListener opGenerarNuevaCuenta = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
//			enviar de parametro el establecimiento donde se capturo ("NS")
			String nuevoTicket = new Obj_Abono_Clientes().nuevoTicket("NS");
			txtTiket.setText(nuevoTicket);
			txtAbono.requestFocus();
		}
	};
	
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
							
								lbl.setOpaque(true); 
								lbl.setBackground(new java.awt.Color(182,211,255));
								
								if(isSelected){
									lbl.setOpaque(true); 
									lbl.setBackground(new java.awt.Color(100,181,255));
								}
								
							lbl.setHorizontalAlignment(SwingConstants.CENTER);
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
	
	public static void main(String [] arg){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Abono_Clientes().setVisible(true);
		}catch(Exception e){
			System.err.println("Error :"+ e.getMessage());
		}
	}
	
	public void buscar_cliente(int folio_cliente){
			try {
				Obj_Clientes cliente = new Obj_Clientes().buscar(folio_cliente);
			
				if(cliente.getFolio_cliente() == folio_cliente){
					
//BUSCAR CAUNTAS CUENTAS TIENE 			(EL PUNTO (1.-) ACER UNA FUNCION PORQUE TAMBIEN FUNCIONARA CON BTNNUEVOTICKET)
//1.- SI NO TIENE CUENTE GENERAR NUMERO DE TIKET NUEVO AUTOMATICO       				Y DESBLOQUEAR EL TXTABONO	PASAR EL FOCO AL ABONO
//2.- SI TIENE 1 PONERLO EN EL TXTFOLIOTICKET   (LLENAR TABLA TICKET Y TABLA ABONOS)   Y DESBLOQUEAR EL TXTABONO  PASAR EL FOCO AL ABONO
//3.- SI TIENE MAS    TXTFOLIOTICKET=""     (LLENAR TABLA TICKET Y TABLA ABONOS=vacio)      Y    BLOQUEAR EL TXTABONO	NO PASAR EL FOCO AL ABONO
					
//AL SELECCIONAR UN TICKET DE DICHA TABLA   PASAR EL PARAMETRO AL TXTFOLIOTICKET  Y DESBLOQUEAR TXTaBONO (LLENAR TABLA ABONOS) 
					txtFolioCliente.setText(cliente.getFolio_cliente()+"");
					txtCliente.setText(cliente.getNombre()+" "+cliente.getAp_paterno()+" "+cliente.getAp_materno());
					txtDomicilio.setText(cliente.getDireccion());
					
	        		while(tabla_ticket.getRowCount()>0)
	        			tabla_model_ticket.removeRow(0);
	        		while(tabla_abonos.getRowCount()>0)
	        			tabla_model_abonos.removeRow(0);
	        		
//	        buscar ticket del cliente
	            Object [][] lista_ticket = new Obj_Abono_Clientes().get_tabla_tickets(folio_cliente);
	      		String[] filaT = new String[4];
	                      for(int i=0; i<lista_ticket.length; i++){
		                    	  filaT[0] = lista_ticket[i][0]+"";
		                    	  filaT[1] = lista_ticket[i][1]+"";
		                    	  filaT[2] = lista_ticket[i][2]+"";
		                    	  filaT[3] = lista_ticket[i][3]+"";
	                              tabla_model_ticket.addRow(filaT);
	                      }
	                      
					switch(tabla_ticket.getRowCount()){
						case 0: 
//							enviar de parametro el establecimiento donde se capturo ("NS")
							String nuevoTicket = new Obj_Abono_Clientes().nuevoTicket("NS");
							txtTiket.setText(nuevoTicket);
							
							while(tabla_ticket.getRowCount()>0){tabla_model_ticket.removeRow(0);}
							while(tabla_abonos.getRowCount()>0){tabla_model_abonos.removeRow(0);}
						break;
						case 1: 
			    			txtTiket.setText(tabla_ticket.getValueAt(0, 0).toString().trim());	
			    			
							while(tabla_abonos.getRowCount()>0){tabla_model_abonos.removeRow(0);}
//			              	buscar abonos del cliente
		                    Object [][] lista_abonos = new Obj_Abono_Clientes().get_tabla_abonos(txtTiket.getText());
		            		String[] filaA = new String[4];
		                            for(int i=0; i<lista_abonos.length; i++){
			                            	filaA[0] = lista_abonos[i][0]+"";
			                            	filaA[1] = lista_abonos[i][1]+"";
			                            	filaA[2] = lista_abonos[i][2]+"";
			                            	filaA[3] = lista_abonos[i][3]+"";
		                                    tabla_model_abonos.addRow(filaA);
		                            }
		                            

							System.out.println("borrar y Cargar tabla_abonos de ese ticket");
						break;
						default: 
							txtTiket.setText("");
							while(tabla_abonos.getRowCount()>0){tabla_model_abonos.removeRow(0);}
						break;
					}
					
					txtAbono.setEditable(true);
					txtAbono.requestFocus();
				}else{
					JOptionPane.showMessageDialog(null,"El Cliente No Esta Dado De Alta","Aviso",JOptionPane.INFORMATION_MESSAGE);
					txtFolioCliente.setText("");
					return;
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	private void SELECCION_TICKET(final JTable tbl) {
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
	        public void mouseClicked(MouseEvent e) {
	        	
	        	if (e.getButton() == MouseEvent.BUTTON1) {
    				
//	    			Point p = e.getPoint();
//                    int row = tabla_ticket.rowAtPoint( p );
	        		int rowButton1 = tabla_ticket.getSelectedRow();
	        		
                    String ticket =  tabla_ticket.getValueAt(rowButton1, 0).toString().trim();
	    			txtTiket.setText(ticket);
	    			
					while(tabla_abonos.getRowCount()>0){tabla_model_abonos.removeRow(0);}
//	              	buscar abonos del cliente
                    Object [][] lista_abonos = new Obj_Abono_Clientes().get_tabla_abonos(txtTiket.getText());
            		String[] filaA = new String[4];
                            for(int i=0; i<lista_abonos.length; i++){
	                            	filaA[0] = lista_abonos[i][0]+"";
	                            	filaA[1] = lista_abonos[i][1]+"";
	                            	filaA[2] = lista_abonos[i][2]+"";
	                            	filaA[3] = lista_abonos[i][3]+"";
                                    tabla_model_abonos.addRow(filaA);
                            }
                      txtAbono.requestFocus();
	        	}
	        	
	        	
//	        	if (e.getButton() == MouseEvent.BUTTON3) {
//                            
//	        		rowButton3 = tabla_ticket.getSelectedRow();
//	        		
//
//	        		
//	        		System.out.println("cl1 "+rowButton1);
//	        		System.out.println("cl3 "+rowButton3);
////	        		seleccionTicket  
//	        		
//                            
//                            
//                            
//                      txtAbono.requestFocus();
//	        	}
	        }
//	        public void mouseReleased( MouseEvent e ){
//	        	if(seleccionTicket){
//	        		
//	        	}else{
//	        		
//	        	}
//				if ( e.isPopupTrigger() ) cambios.show(tabla_ticket,100,0 ); 
//			} 
        });
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
			
			cont.setBackground(Color.black);
			lblBuscar.setForeground(Color.white);
			
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
		    			dispose();

		    			//buscar cliente para abonos
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
						componente.setBackground(new java.awt.Color(182,211,255));	
					}
					
					if(isSelected){
						((JComponent) componente).setOpaque(true); 
						componente.setBackground(new java.awt.Color(100,181,255));
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
				rs = s.executeQuery("select folio_cliente as folio_cliente, " +
									"nombre+' '+ap_paterno+' '+ap_materno as cliente, " +
									"direccion as direccion " +
									"from tb_clientes" );
				
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
	
	
	
	
	
	
	
	public class Imprime_Ticket_abono extends JDialog
	{
		Container container = getContentPane();
		JLayeredPane panel2 = new JLayeredPane();
		
	//Declarar Imagen para Txa	
		ImageIcon img = new ImageIcon("imagen/fuenteSodasTicket2.png");
		
		JScrollPane jScrollPane1 = new JScrollPane();
		JButton jButImprime = new JButton("Imprimir");
		
		private JTextArea jTextArea1=new JTextArea(){
			
			Image image = img.getImage();
			
			Image grayImage = GrayFilter.createDisabledImage(image);{
				setOpaque(false);
			}
			
			public void paint(Graphics g){
					g.drawImage(grayImage,0,0,this);
					super.paint(g);
			}
		};
//variables para ticket -----------------------------------------------------------------------------------
		String usuario= "Le Atiende: ";
		String fecha ="Fecha: ";
		String lblEmpleado="Cliente: ";
		
		String establecimiento="Establecimiento: ";
		String puesto="Puesto: ";
		String ticket="Ticket: ";
		String importe="Importe: $";
		String linea = "_____________________________________________________";
		String firma=" Firma: (  ";
//-----------------------------------------------------------------------------------------------------------
		JFrame frame = null;
		public Imprime_Ticket_abono(String pass)
		{
			
			frame = new JFrame();
			this.setModal(true);
			this.setTitle("Imprimir Ticket");
			Font font = new Font("ARIAL",Font.PLAIN,8);
			jTextArea1.setFont(font);
			
			Obj_Abono_Clientes ultimiTicket = new Obj_Abono_Clientes().buscar_ultimo_abono(txtTiket.getText().toUpperCase());
			
			usuario=		usuario+ultimiTicket.getCajero();
			fecha= 			fecha+ultimiTicket.getFecha_in();
			lblEmpleado=	lblEmpleado+ultimiTicket.getTicket();
//			establecimiento=establecimiento+ultimiTicket.getEstablecimiento();
//			puesto=			puesto+ultimiTicket.getPuesto();
//			ticket=			ticket+ultimiTicket.getTicket();
//			importe=		importe+ultimiTicket.getImporte();
//			firma=			firma+ultimiTicket.getEmpleado()+"  )";
			
			panel2.add(jButImprime).setBounds(134,10,100,20);
			panel2.add(jTextArea1).setBounds(14,50,210,310);
			
			jTextArea1.setText(
        		new String ("\n\n\n\n\n\n\n"+usuario
        				+"\n\n                                       "//espacio para acomodar fecha
        				+"                   "+fecha+"\n\n"
						+lblEmpleado+"\n\n"+establecimiento+"\n\n"
						+puesto+"\n\n"+ticket+"\n\n"+importe+"\n\n\n\n\n"
						+linea+"\n"+firma+"\n\n\n\n.")
	        );
			
			jTextArea1.setEditable(false);
			
			jButImprime.requestFocus();
			jButImprime.addActionListener(opImprimir);
			
			frame.add(panel2);
			this.setSize(260, 400);
			this.setLocationRelativeTo(null);
			container.add(panel2);
		}
		
		ActionListener opImprimir = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					imprimir();
					imprimir();
					dispose();
					
//					limpiar 
//					btnCancelar.doClick();
			}
		};
		
		public void imprimir() {
			
			PrintJob print = Toolkit.getDefaultToolkit().getPrintJob(frame, "", null); 
					try { 
						Graphics g = print.getGraphics(); 
						jTextArea1.print(g); 
						print.end();
						g.dispose();
					}catch(Exception e) {
						System.out.println("LA IMPRESION HA SIDO CANCELADA..."); 
					} 
		}
	}
}
