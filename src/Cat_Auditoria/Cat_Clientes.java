package Cat_Auditoria;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.KeyAdapter;
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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

import Conexiones_SQL.Connexion;
import Obj_Auditoria.Obj_Movimiento_De_Asignacion;
import Obj_Principal.Componentes;

@SuppressWarnings({ "serial", "unchecked" })
public class Cat_Clientes extends JFrame{
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
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
	
	JButton btnGuardar = new JButton("Guardar");
	JButton btnModificar = new JButton("Modificar");

	JTextField txtFolioCliente = new Componentes().text(new JTextField(), "Folio Del Cliente", 20, "Int");
	JTextField txtNombre = new Componentes().text(new JTextField(), "Nombre de Empleado", 50, "String");
	JTextField txtApPaterno = new Componentes().text(new JTextField(), "Apallido Paterno", 50, "String");
	JTextField txtApMaterno = new Componentes().text(new JTextField(), "Apallido Materno", 50, "String");
	
	JTextArea txaDomicilio = new Componentes().textArea(new JTextArea(), "Domicilio", 90);
	JScrollPane Observasiones = new JScrollPane(txaDomicilio);
	JTextField txtTelefono = new Componentes().text(new JTextField(), "Telefono", 10, "Int");
	
	Border blackline;
	
	int folio = 0;
			
	@SuppressWarnings("rawtypes")
	public Cat_Clientes()	{
		this.setTitle("Alta Clientes");
		
		blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
		this.panel.setBorder(BorderFactory.createTitledBorder(blackline, "Filtrar"));
		
		trsfiltro = new TableRowSorter(model); 
		tabla.setRowSorter(trsfiltro);  
		
		int x=20; int y=15; int ancho=110;
		
		panel.add(new JLabel("Folio Cliente: ")).setBounds( x, y, 100, 20);
		panel.add(txtFolioCliente).setBounds( x+65, y, 70, 20);
		
		panel.add(btnGuardar).setBounds( x+235, y, 85, 20);
		panel.add(btnModificar).setBounds( x+320, y, 85, 20);
		
		panel.add(new JLabel("Nombre: ")).setBounds( x, y+=25, ancho, 20);
		panel.add(txtNombre).setBounds( x+65, y, ancho, 20);
		
		panel.add(new JLabel("Domicilio: ")).setBounds( x+180, y, ancho, 20);
		panel.add(Observasiones).setBounds( x+235, y, ancho+60, 45);
		
		panel.add(new JLabel("Ap. Paterno: ")).setBounds( x, y+=25, 70, 20);
		panel.add(txtApPaterno).setBounds( x+65, y, ancho, 20);
		
		panel.add(new JLabel("Ap. Materno: ")).setBounds( x, y+=25, 70, 20);
		panel.add(txtApMaterno).setBounds( x+65, y, ancho, 20);
		
		panel.add(new JLabel("Telefono: ")).setBounds( x+180, y, 70, 20);
		panel.add(txtTelefono).setBounds( x+235, y, ancho, 20);
		
		panel.add(getPanelTabla()).setBounds(10,y+=25,415,300);
		
		cont.add(panel);
		
		txtFolioCliente.setEditable(false);
		
		txaDomicilio.setLineWrap(true); 
		txaDomicilio.setWrapStyleWord(true);
		
		txtNombre.addKeyListener(opReduccion);
		txtApPaterno.addKeyListener(opReduccion);
		txtApMaterno.addKeyListener(opReduccion);
		
		agregar(tabla);
		
		this.setSize(440,460);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	private void agregar(final JTable tbl) {
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
	        public void mouseClicked(MouseEvent e) {
	        	if(e.getClickCount() == 2){

	        		int fila = tabla.getSelectedRow();
	        			
	        			folio =  Integer.valueOf(tabla.getValueAt(fila, 0).toString().trim());
	        	}
	        }
        });
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
	
	private JScrollPane getPanelTabla()	{		
		new Connexion();
		
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
		tcr.setHorizontalAlignment(SwingConstants.CENTER);
		
		tabla.getColumnModel().getColumn(0).setCellRenderer(tcr);
		
		tabla.getColumnModel().getColumn(0).setHeaderValue("Folio");
		tabla.getColumnModel().getColumn(0).setMaxWidth(70);
		tabla.getColumnModel().getColumn(0).setMinWidth(70);
		tabla.getColumnModel().getColumn(1).setHeaderValue("Nombre Completo");
		tabla.getColumnModel().getColumn(1).setMaxWidth(310);
		tabla.getColumnModel().getColumn(1).setMinWidth(310);
		tabla.getColumnModel().getColumn(2).setHeaderValue("Direccion");
		tabla.getColumnModel().getColumn(2).setMaxWidth(150);
		tabla.getColumnModel().getColumn(2).setMinWidth(150);
		
		TableCellRenderer render = new TableCellRenderer() 
		{ 
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, 
			boolean hasFocus, int row, int column) { 
				JLabel lbl = new JLabel(value == null? "": value.toString());
		
				if(row%2==0){
						lbl.setOpaque(true); 
						lbl.setBackground(new java.awt.Color(177,177,177));
				} 
			return lbl; 
			} 
		}; 
						tabla.getColumnModel().getColumn(0).setCellRenderer(render); 
						tabla.getColumnModel().getColumn(1).setCellRenderer(render);
						tabla.getColumnModel().getColumn(2).setCellRenderer(render);
		
		Statement s;
		ResultSet rs;
		try {
			s = con.conexion().createStatement();
			rs = s.executeQuery("select tb_empleado.folio as Folio," +
					"					tb_empleado.nombre + ' ' +	tb_empleado.ap_paterno + ' ' +  tb_empleado.ap_materno as NombreCompleto" +
					"					from tb_empleado where tb_empleado.status=1 and puesto_id=32" ); // 32 = puesto de cajera(o)
			
			while (rs.next())
			{ 
			   String [] fila = new String[2];
			   fila[0] = rs.getString(1).trim();
			   fila[1] = rs.getString(2).trim();
			   
			   model.addRow(fila); 
			}	
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		 JScrollPane scrol = new JScrollPane(tabla);
		   
	    return scrol; 
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
	
	public class Cat_Estender_Movimientos_De_Asignacion extends Cat_Movimiento_De_Asignacion{

		public Cat_Estender_Movimientos_De_Asignacion(int folio,String establecimiento) {
//			super(folio, establecimiento);
			
			try {
				
				Obj_Movimiento_De_Asignacion asignacion = new Obj_Movimiento_De_Asignacion().buscarAsignacion(folio, establecimiento);
				
				txtEmpleado.setText(asignacion.getEmpleado());
				txtAsignacion.setText(asignacion.getAsignacion());
				
				Date fechaIn = new SimpleDateFormat("dd/MM/yyyy").parse(asignacion.getFechaIn());
				fechaInicio.setDate(fechaIn);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
//			btnGuardar.addActionListener(opGuardar);
		}
		
//		ActionListener opGuardar = new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				if(validaCampos() != ""){
//					JOptionPane.showMessageDialog(null, "Los Siguientes Campos Son Requeridos:\n"+validaCampos(), "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
//					return;
//				}else{
//					
//					Obj_Movimiento_De_Asignacion movimiento = new Obj_Movimiento_De_Asignacion();
//					movimiento.setFolio_empleado(folio);
//					movimiento.setEstablecimiento(cmbEstablecimiento.getSelectedItem()+"");
//					movimiento.setAsignacion(txtAsignacion.getText());
//					movimiento.setFechaIn(new SimpleDateFormat("dd/MM/yyyy").format(fechaInicio.getDate()));
//					movimiento.setFechaFin(new SimpleDateFormat("dd/MM/yyyy").format(fechaFinal.getDate()));
//					
//					if(movimiento.buscar_empleado_vigente_en_asignacion(folio,cmbEstablecimiento.getSelectedItem().toString().trim())){
//						
//						JOptionPane.showMessageDialog(null,"No Puede Realizar La Asignacion\n Ya Que El Empleado Se Encuentra Asignado","Aviso",JOptionPane.INFORMATION_MESSAGE);
//						return;
//						
//					}else{
//					
//						if(movimiento.guardarAsignacion()){
//							
//							JOptionPane.showMessageDialog(null,"Asignacion Guardada","Aviso",JOptionPane.INFORMATION_MESSAGE);
//							return;
//							
//						}else{
//							
//							JOptionPane.showMessageDialog(null, "La Asignacion No Pudo Darse De Alta", "Error", JOptionPane.ERROR_MESSAGE);
//							return;
//							
//						}
//					}
//				}
//			}
//		};
	}
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Clientes().setVisible(true);
		}catch(Exception e){
			System.err.println("Error :"+ e.getMessage());
		}
	}
}
