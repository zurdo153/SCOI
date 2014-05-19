package Cat_Auditoria;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

import Obj_Auditoria.Obj_Clientes;
import Obj_Principal.Componentes;

@SuppressWarnings({ "serial", "unchecked" })
public class Cat_Clientes extends JDialog{
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
    public static DefaultTableModel tabla_model = new DefaultTableModel(new Obj_Clientes().get_tabla_model(),
            new String[]{"Folio", "Cliente", "Direccion"}){
                    
            @SuppressWarnings("rawtypes")
            Class[] types = new Class[]{
                       java.lang.Object.class,
                       java.lang.Object.class, 
                       java.lang.Object.class
                        
        };
            @SuppressWarnings("rawtypes")
			public Class getColumnClass(int columnIndex) {
                    return types[columnIndex];
            }
        public boolean isCellEditable(int fila, int columna){
                    switch(columna){
                            case 0  : return false; 
                            case 1  : return false; 
                            case 2  : return false; 
                    }
                     return false;
             }
    };
	
	JTable tabla = new JTable(tabla_model);
	JScrollPane panelScroll = new JScrollPane(tabla);
	
	@SuppressWarnings("rawtypes")
	private TableRowSorter trsfiltro;
	
	JButton btnNuevo = new JButton("Nuevo");
	JButton btnGuardar = new JButton("Guardar");
	JButton btnModificar = new JButton("Modificar");

	JTextField txtFolioCliente = new Componentes().text(new JTextField(), "Folio Del Cliente", 20, "Int");
	JTextField txtNombre = new Componentes().text(new JTextField(), "Nombre de Empleado", 50, "String");
	JTextField txtApPaterno = new Componentes().text(new JTextField(), "Apallido Paterno", 50, "String");
	JTextField txtApMaterno = new Componentes().text(new JTextField(), "Apallido Materno", 50, "String");
	
	JTextField txtDomicilio = new Componentes().text(new JTextField(), "Apallido Materno", 50, "String");
	JTextField txtTelefono = new Componentes().text(new JTextField(), "Telefono", 10, "Int");
	
	Border blackline;
	
	@SuppressWarnings("rawtypes")
	public Cat_Clientes(){
		this.setModal(true);
		this.setTitle("Alta Clientes");
		
		blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
		this.panel.setBorder(BorderFactory.createTitledBorder(blackline, "Filtrar"));
		
		trsfiltro = new TableRowSorter(tabla_model); 
		tabla.setRowSorter(trsfiltro);  
		
		init_tabla();
		
		int x=20; int y=15; int ancho=110;
		
		panel.add(new JLabel("Nombre: ")).setBounds( x, y, ancho, 20);
		panel.add(txtNombre).setBounds( x+65, y, ancho+20, 20);
		
		panel.add(new JLabel("Cliente #: ")).setBounds( x+210, y, 100, 20);
		panel.add(txtFolioCliente).setBounds( x+265, y, 70, 20);
		
		panel.add(btnNuevo).setBounds( x+335 , y, 75, 20);
		panel.add(btnGuardar).setBounds( x+430 , y, 75, 20);
		
		panel.add(new JLabel("Ap. Paterno: ")).setBounds( x, y+=25, 70, 20);
		panel.add(txtApPaterno).setBounds( x+65, y, ancho+20, 20);
		
		panel.add(new JLabel("Telefono: ")).setBounds( x+210, y, 70, 20);
		panel.add(txtTelefono).setBounds( x+265, y, ancho+34, 20);
		
		panel.add(btnModificar).setBounds( x+430, y, 75, 20);
		
		panel.add(new JLabel("Ap. Materno: ")).setBounds( x, y+=25, 70, 20);
		panel.add(txtApMaterno).setBounds( x+65, y, ancho+20, 20);
		
		panel.add(new JLabel("Domicilio: ")).setBounds( x+210, y, ancho, 20);
		panel.add(txtDomicilio).setBounds( x+265, y, ancho+130, 20);
		
		panel.add(panelScroll).setBounds(10,y+=25,515,330);
		
		cont.add(panel);
		
		txtFolioCliente.setEditable(false);
		btnGuardar.setEnabled(false);
		btnModificar.setEnabled(false);
		
		txtNombre.addKeyListener(opReduccion);
		txtApPaterno.addKeyListener(opReduccion);
		txtApMaterno.addKeyListener(opReduccion);
		
		btnNuevo.addActionListener(opNuevo);
		btnGuardar.addActionListener(opGuardar);
		btnModificar.addActionListener(opModificar);
		
		agregar(tabla);
		
		this.setSize(540,460);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	private void agregar(final JTable tbl) {
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
	        	
				if(e.getClickCount() == 1){

	        		int fila = tabla.getSelectedRow();
	        		int folio =  Integer.valueOf(tabla.getValueAt(fila, 0).toString().trim());
	        		btnNuevo.setEnabled(true);
					btnGuardar.setEnabled(false);
					btnModificar.setEnabled(true);
					txtNombre.requestFocus();
					
					try {
						Obj_Clientes cliente = new Obj_Clientes().buscar(folio);
						
						txtFolioCliente.setText(cliente.getFolio_cliente()+"");
						txtNombre.setText(cliente.getNombre());
						txtApPaterno.setText(cliente.getAp_paterno());
						txtApMaterno.setText(cliente.getAp_materno());
						txtDomicilio.setText(cliente.getDireccion());
						txtTelefono.setText(cliente.getTelefono());
						
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
	        	}
				
	        	if(e.getClickCount() == 2){

	        		int fila = tabla.getSelectedRow();
	        		int folio =  Integer.valueOf(tabla.getValueAt(fila, 0).toString().trim());
	        		
	        		System.out.println("cerrar y buscar tiket de ese cliente si es 1 tiket mandarlo a la pantalla de abono, si son mas abrir filtro de tiket por empleado y seleccionar uno");
	        		new Cat_Abono_Clientes().txtFolioTiket.setText(folio+"");
	        		dispose();
	        	}
	        	
	        }
        });
    }
	
	ActionListener opNuevo = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			try {
				Obj_Clientes cliente = new Obj_Clientes().buscar_nuevo();
				
				limpiarPantella();
				txtFolioCliente.setText(cliente.getFolio_cliente()+"");
				
				btnNuevo.setEnabled(false);
				btnGuardar.setEnabled(true);
				btnModificar.setEnabled(false);
				txtNombre.requestFocus();
				
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	};
	
	public void limpiarPantella(){
		txtFolioCliente.setText("");
		txtNombre.setText("");
		txtApPaterno.setText("");
		txtApMaterno.setText("");
		txtDomicilio.setText("");
		txtTelefono.setText("");
	}
	
	ActionListener opGuardar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
				if(validaCampos() != ""){
						txtNombre.requestFocus();
						JOptionPane.showMessageDialog(null, "Los Siguientes Campos Son Requeridos:\n"+validaCampos(), "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
						return;
				}else{
					
						if(tabla.getRowCount()>0){
							txtNombre.requestFocus();
							JOptionPane.showMessageDialog(null, "El Nombre Del Cliente Coinside Con Otro Ya Registrado", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
							return;
						}else{
							
							Obj_Clientes cliente = new Obj_Clientes();
							
							cliente.setNombre(txtNombre.getText().toUpperCase().trim());
							cliente.setAp_paterno(txtApPaterno.getText().toUpperCase().trim());
							cliente.setAp_materno(txtApMaterno.getText().toUpperCase().trim());
							cliente.setDireccion(txtDomicilio.getText().toUpperCase().trim());
							cliente.setTelefono(txtTelefono.getText().trim());
							
								if(cliente.guardar()){
									
									limpiarPantella();
									btnNuevo.setEnabled(true);
									btnGuardar.setEnabled(false);
									btnModificar.setEnabled(false);
									txtNombre.requestFocus();
									
				                        while(tabla.getRowCount()>0){
			                                tabla_model.removeRow(0);
				                        }
			                        
				                        Object [][] lista_tabla = new Obj_Clientes().get_tabla_model();
				                        String[] fila = new String[3];
		                                for(int i=0; i<lista_tabla.length; i++){
		                                        fila[0] = lista_tabla[i][0]+"   ";
		                                        fila[1] = "   "+lista_tabla[i][1];
		                                        fila[2] = "   "+lista_tabla[i][2];
		                                        tabla_model.addRow(fila);
		                                }
									
									JOptionPane.showMessageDialog(null,"Cliente Guardado","Aviso",JOptionPane.INFORMATION_MESSAGE);
									return;
								}else{
									JOptionPane.showMessageDialog(null, "El Cliente No Pudo Darse De Alta", "Error", JOptionPane.ERROR_MESSAGE);
									return;
								}
						}
				}
		}
	};
	
	ActionListener opModificar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
				if(validaCampos() != ""){
						txtNombre.requestFocus();
						JOptionPane.showMessageDialog(null, "Los Siguientes Campos Son Requeridos:\n"+validaCampos(), "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
						return;
				}else{
					
							Obj_Clientes cliente = new Obj_Clientes();
							
							cliente.setFolio_cliente(Integer.valueOf(txtFolioCliente.getText()));
							cliente.setNombre(txtNombre.getText().toUpperCase().trim());
							cliente.setAp_paterno(txtApPaterno.getText().toUpperCase().trim());
							cliente.setAp_materno(txtApMaterno.getText().toUpperCase().trim());
							cliente.setDireccion(txtDomicilio.getText().toUpperCase().trim());
							cliente.setTelefono(txtTelefono.getText().trim());
							
								if(cliente.actualizar()){
									
									limpiarPantella();
									btnNuevo.setEnabled(true);
									btnGuardar.setEnabled(false);
									btnModificar.setEnabled(false);
									txtNombre.requestFocus();
									
				                        while(tabla.getRowCount()>0){
			                                tabla_model.removeRow(0);
				                        }
			                        
				                        Object [][] lista_tabla = new Obj_Clientes().get_tabla_model();
				                        String[] fila = new String[3];
		                                for(int i=0; i<lista_tabla.length; i++){
		                                        fila[0] = lista_tabla[i][0]+"   ";
		                                        fila[1] = "   "+lista_tabla[i][1];
		                                        fila[2] = "   "+lista_tabla[i][2];
		                                        tabla_model.addRow(fila);
		                                }
									
									JOptionPane.showMessageDialog(null,"Cliente Modificado","Aviso",JOptionPane.INFORMATION_MESSAGE);
									return;
								}else{
									JOptionPane.showMessageDialog(null, "El Cliente No Pudo Modificarse", "Error", JOptionPane.ERROR_MESSAGE);
									return;
								}
				}
		}
	};
	
	public String validaCampos(){
		String error="";
		
		if(txtNombre.getText().equals("")) 		error+="Nombre\n";
		if(txtApPaterno.getText().equals("")) 	error+="Ap Paterno\n";
		if(txtApMaterno.getText().equals("")) 	error+="Ap Materno\n";
		
		if(txtTelefono.getText().equals("")) 	error+="Telefono\n";
		if(txtDomicilio.getText().equals("")) 	error+="Direccion\n";
		
		return error;
	}
	
	KeyAdapter opReduccion = new KeyAdapter() { 
		public void keyReleased(final KeyEvent e) {
			String nombre = "";

			switch(txtApPaterno.getText().toUpperCase().trim().length()){
				case 0:	nombre = txtNombre.getText().toUpperCase().trim()+" "+txtApMaterno.getText().toUpperCase().trim(); break;
				default:nombre = txtNombre.getText().toUpperCase().trim()+" "+txtApPaterno.getText().toUpperCase().trim()+" "+txtApMaterno.getText().toUpperCase().trim(); break;
			}
			trsfiltro.setRowFilter(RowFilter.regexFilter(nombre.trim(), 1));
        } 
    };
	
	public void init_tabla(){
    	
    	this.tabla.getTableHeader().setReorderingAllowed(false) ;
    	
    	this.tabla.getColumnModel().getColumn(0).setMaxWidth(72);
    	this.tabla.getColumnModel().getColumn(0).setMinWidth(72);		
    	this.tabla.getColumnModel().getColumn(1).setMaxWidth(360);
    	this.tabla.getColumnModel().getColumn(1).setMinWidth(360);
    	this.tabla.getColumnModel().getColumn(2).setMaxWidth(210);
    	this.tabla.getColumnModel().getColumn(2).setMinWidth(210);

    	TableCellRenderer render = new TableCellRenderer() {
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, 
					boolean hasFocus, int row, int column) {
					
							JLabel lbl = new JLabel(value == null? "": value.toString());
							
							if(row%2==0){
									lbl.setOpaque(true); 
									lbl.setBackground(new java.awt.Color(177,177,177));
							} 
							
							if(table.getSelectedRow() == row){
								lbl.setOpaque(true); 
								lbl.setBackground(new java.awt.Color(186,143,73));
							}
							
							switch(column){
								case 0 : lbl.setHorizontalAlignment(SwingConstants.RIGHT); break;
								case 1 : lbl.setHorizontalAlignment(SwingConstants.LEFT); break;
								case 2 : lbl.setHorizontalAlignment(SwingConstants.LEFT); break;
								case 3 : lbl.setHorizontalAlignment(SwingConstants.RIGHT); break;
								case 4 : lbl.setHorizontalAlignment(SwingConstants.RIGHT); break;
								case 5 : lbl.setHorizontalAlignment(SwingConstants.RIGHT); break;
							}
							return lbl; 
					} 
			}; 

		for(int x = 0; x<tabla.getColumnCount(); x++){
			this.tabla.getColumnModel().getColumn(x).setCellRenderer(render); 
		}
		
		this.tabla.setRowSorter(trsfiltro);  
		
    }
	
	KeyListener validaCantidad = new KeyListener() {
		public void keyTyped(KeyEvent e){
			char caracter = e.getKeyChar();				
			if(((caracter < '0') ||	
			    	(caracter > '9')) && 
			    	(caracter != '.' )){
			    	e.consume();
			    	}
		}
		public void keyReleased(KeyEvent e) {}
		public void keyPressed(KeyEvent arg0) {}	
	};
	
	KeyListener validaNumericoConPunto = new KeyListener() {
		public void keyTyped(KeyEvent e) {
			char caracter = e.getKeyChar();
			
		    if(((caracter < '0') ||	
		    	(caracter > '9')) && 
		    	(caracter != '.')){
		    	e.consume();
		    	}
		}
		public void keyPressed(KeyEvent e){}
		public void keyReleased(KeyEvent e){}
	};
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Clientes().setVisible(true);
		}catch(Exception e){
			System.err.println("Error :"+ e.getMessage());
		}
	}
}
