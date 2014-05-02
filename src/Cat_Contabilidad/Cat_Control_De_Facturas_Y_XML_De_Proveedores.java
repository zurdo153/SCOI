package Cat_Contabilidad;

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

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import javax.swing.table.TableRowSorter;

import Conexiones_SQL.Connexion;
import Obj_Lista_de_Raya.Obj_Puestos;
import Obj_Principal.Componentes;

@SuppressWarnings("serial")
public class Cat_Control_De_Facturas_Y_XML_De_Proveedores extends JFrame{
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	Connexion con = new Connexion();
	
	DefaultTableModel modelo       = new DefaultTableModel(0,6)	{
		public boolean isCellEditable(int fila, int columna){
			if(columna < 0)
				return true;
			return false;
		}
	};
	JTable tabla = new JTable(modelo);
	JScrollPane panelScroll = new JScrollPane(tabla);
	
	JTextField txtFolioFiltro = new JTextField();
	JTextField txtPuestoFiltro = new JTextField();
	@SuppressWarnings("rawtypes")
	private TableRowSorter trsfiltro;
	
	JTextField txtFolioFactura = new Componentes().text(new JTextField(), "Folio de La Factura", 25, "String");
	JTextField txtFecha = new Componentes().text(new JTextField(), "Fecha Factura", 25, "String");
	JTextField txtFolioProveedor =new Componentes().text(new JTextField(), "Folio del Proveedor", 25, "String");
	JTextField txtProveedor = new Componentes().text(new JTextField(), "Proveedor", 200, "String");
	
	JCheckBox chStatus = new JCheckBox("Status");
	
	JButton btnBuscar = new JButton(new ImageIcon("imagen/buscar.png"));
	JButton btnSalir = new JButton("Salir");
	JButton btnDeshacer = new JButton("Deshacer");
	JButton btnGuardar = new JButton("Guardar");
	JButton btnEditar = new JButton("Editar");
	JButton btnNuevo = new JButton("Nuevo");
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Cat_Control_De_Facturas_Y_XML_De_Proveedores(String folio, String proveedor){
		
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Toolbox.png"));
		panel.setBorder(BorderFactory.createTitledBorder("Control De Facturas y XML De Proveedores"));
		this.setTitle("Control De Facturas y XML De Proveedores");
		
		
		trsfiltro = new TableRowSorter(modelo); 
		tabla.setRowSorter(trsfiltro);
		txtFolioFiltro.setToolTipText("Filtro Por Cod. Prov");
		txtPuestoFiltro.setToolTipText("Filtro Por Proveedor");
		panel.add(txtFolioFiltro).setBounds(20,175,71,20);
		panel.add(txtPuestoFiltro).setBounds(95,175,260,20);
		panel.add(getPanelTabla()).setBounds(20,200,720,520);
				
		panel.add(chStatus).setBounds(380,30,70,20);
		chStatus.setEnabled(false);
		
		
		panel.add(new JLabel("Factura:")).setBounds(20,30,100,20);
		panel.add(txtFolioFactura).setBounds(80,30,250,20);
		txtFolioFactura.requestFocus();
				
		
		panel.add(new JLabel("Fecha Fact:")).setBounds(20,60,100,20);
		panel.add(txtFecha).setBounds(80,60,250,20);
		txtFecha.setEditable(false);
		
		
		
		panel.add(new JLabel("Cod. Prov:")).setBounds(20,90,100,20);
		panel.add(txtFolioProveedor).setBounds(80,90,250,20);
		txtFolioProveedor.setEditable(false);
		txtFolioProveedor.setText(folio);
		
		panel.add(new JLabel("Proveedor:")).setBounds(20,120,100,20);
		panel.add(txtProveedor).setBounds(80,120,250,20);
		txtProveedor.setEditable(false);
		txtProveedor.setText(proveedor);
		
		
		
		panel.add(btnBuscar).setBounds(470,20,40,20);
		panel.add(btnNuevo).setBounds(470,40,100,20);
		panel.add(btnEditar).setBounds(470,60,100,20);
		panel.add(btnDeshacer).setBounds(470,80,100,20);
		panel.add(btnSalir).setBounds(470,100,100,20);
		panel.add(btnGuardar).setBounds(470,120,100,20);
		
		txtFolioFactura.addKeyListener(buscar_action);
//	----------------------------------------------------------------------------------------------------------------	
		btnGuardar.addActionListener(guardar);
		btnSalir.addActionListener(cerrar);
		btnBuscar.addActionListener(buscar);
		btnDeshacer.addActionListener(deshacer);
		btnNuevo.addActionListener(nuevo);
		btnEditar.addActionListener(editar);
		btnEditar.setEnabled(false);
		btnGuardar.setEnabled(false);
		
		txtFolioFiltro.addKeyListener(opFiltroFolio);
		txtPuestoFiltro.addKeyListener(opFiltroNombre);
		
		cont.add(panel);
		seleccionar_click(tabla);
		
		this.setSize(760,768);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	
	}
	
	KeyListener opFiltroFolio = new KeyListener(){
		@SuppressWarnings("unchecked")
		public void keyReleased(KeyEvent arg0) {
			trsfiltro.setRowFilter(RowFilter.regexFilter(txtFolioFiltro.getText(), 0));
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
		@SuppressWarnings("unchecked")
		public void keyReleased(KeyEvent arg0) {
			trsfiltro.setRowFilter(RowFilter.regexFilter(txtPuestoFiltro.getText().toUpperCase().trim(), 1));
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}		
	};
	
	private JScrollPane getPanelTabla()	{		

		tabla.getColumnModel().getColumn(0).setHeaderValue("Cod. P");
		tabla.getColumnModel().getColumn(0).setMaxWidth(50);
		tabla.getColumnModel().getColumn(0).setMinWidth(90);
		tabla.getColumnModel().getColumn(1).setHeaderValue("Proveedor");
		tabla.getColumnModel().getColumn(1).setMaxWidth(460);
		tabla.getColumnModel().getColumn(1).setMinWidth(140);
		tabla.getColumnModel().getColumn(2).setHeaderValue("Factura");
		tabla.getColumnModel().getColumn(2).setMaxWidth(350);
		tabla.getColumnModel().getColumn(2).setMinWidth(90);
		tabla.getColumnModel().getColumn(3).setHeaderValue("Fecha Factura");
		tabla.getColumnModel().getColumn(3).setMaxWidth(100);
		tabla.getColumnModel().getColumn(3).setMinWidth(60);
		tabla.getColumnModel().getColumn(4).setHeaderValue("Fecha Ult Mod");
		tabla.getColumnModel().getColumn(4).setMaxWidth(100);
		tabla.getColumnModel().getColumn(4).setMinWidth(60);
		tabla.getColumnModel().getColumn(5).setHeaderValue("Modifico");
		tabla.getColumnModel().getColumn(5).setMaxWidth(350);
		tabla.getColumnModel().getColumn(5).setMinWidth(90);

		
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
		tcr.setHorizontalAlignment(SwingConstants.LEFT);
		
		tabla.getColumnModel().getColumn(0).setCellRenderer(tcr);
		tabla.getColumnModel().getColumn(1).setCellRenderer(tcr);
		tabla.getColumnModel().getColumn(2).setCellRenderer(tcr);
		tabla.getColumnModel().getColumn(3).setCellRenderer(tcr);
		tabla.getColumnModel().getColumn(4).setCellRenderer(tcr);
		tabla.getColumnModel().getColumn(5).setCellRenderer(tcr);
		
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
						tabla.getColumnModel().getColumn(3).setCellRenderer(render);
						tabla.getColumnModel().getColumn(4).setCellRenderer(render);
						tabla.getColumnModel().getColumn(5).setCellRenderer(render);

						
		Statement s;
		ResultSet rs;
		try {
			s = con.conexion().createStatement();
			rs = s.executeQuery("SELECT cod_prv,proveedor,folio_factura,convert (varchar(20),[fecha_factura],103)as fecha_factura,convert(varchar(20),[fecha_modificacion],103)as fecha_modificacion "+
				                        ",(select nombre+' '+ap_paterno+' 'ap_materno from tb_empleado where folio=folio_empleado_modifico)as empleado_modifico,Status FROM tb_control_de_facturas_y_xml");
			while (rs.next())
			{ 
			   String [] fila = new String[6];
			   fila[0] = rs.getString(1).trim();
			   fila[1] = rs.getString(2).trim();
			   fila[2] = rs.getString(3).trim(); 
			   fila[3] = rs.getString(4).trim(); 
			   fila[4] = rs.getString(5).trim(); 
			   fila[5] = rs.getString(6).trim(); 
			   
			   modelo.addRow(fila); 
			}	
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en Cat_Control_De_Facturas_Y_XML_De_Proveedores en la funcion getPanelTabla  SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
		 JScrollPane scrol = new JScrollPane(tabla);
		   
	    return scrol; 
	}
	
	private void seleccionar_click(final JTable tbl) {
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
	        	if(e.getClickCount()==1){
	        		
	        		int fila = tabla.getSelectedRow();
	        		Object id = tabla.getValueAt(fila,2);
	        
	        		
	        		    txtFolioFactura.setText(id+"");
						txtFecha.setText(tabla.getValueAt(fila,3)+"");
						txtFolioProveedor.setText(tabla.getValueAt(fila,0)+"");
						txtProveedor.setText(tabla.getValueAt(fila,1)+"");
						btnEditar.setEnabled(true);
						chStatus.setSelected(true);
						
						
	        	}
	        }
        });
    }
	
	ActionListener guardar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(txtFolioFactura.getText().equals("")){
				JOptionPane.showMessageDialog(null, "El folio es requerido \n", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
			}else{			
				Obj_Puestos puesto = new Obj_Puestos().buscar(Integer.parseInt(txtFolioFactura.getText()));
				
				if(puesto.getFolio() == Integer.parseInt(txtFolioFactura.getText())){
					if(JOptionPane.showConfirmDialog(null, "El registro ya existe, ¿desea cambiarlo?") == 0){
						if(validaCampos()!="") {
							JOptionPane.showMessageDialog(null, "los siguientes campos son requeridos:\n"+validaCampos(), "Error al guardar registro", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
							return;
						}else{
//							int nroFila = tabla.getSelectedRow();
//							
//							puesto.setPuesto(txtPuesto.getText());
//							puesto.setAbreviatura(txtAbreviatura.getText());
//							puesto.setStatus(chStatus.isSelected());
//							
//							puesto.actualizar(Integer.parseInt(txtFolio.getText()));
//							
//							modelo.setValueAt(txtFolio.getText(),nroFila,0);
//							modelo.setValueAt(txtPuesto.getText(),nroFila,1);
//							modelo.setValueAt(txtAbreviatura.getText(), nroFila, 2);
//							
//							panelLimpiar();
//							panelEnabledFalse();
//							txtFolio.setEditable(true);
//							txtFolio.requestFocus();
							System.out.println("Recibio Informacion 22");
						}
						
						JOptionPane.showMessageDialog(null,"El registró se actualizó de forma segura","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//Exito.png"));
					}else{
						return;
					}
				}else{
					if(validaCampos()!="") {
						JOptionPane.showMessageDialog(null, "los siguientes campos son requeridos:\n "+validaCampos(), "Error al guardar registro", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
						return;
					}else{
						System.out.println("Recibio Informacio");
//						puesto.setPuesto(txtPuesto.getText());
//						puesto.setAbreviatura(txtAbreviatura.getText());
//						puesto.setStatus(chStatus.isSelected());
//						puesto.guardar();
//						
//						Object[] fila = new Object[tabla.getColumnCount()]; 
//							
//						fila[0]=txtFolio.getText();
//						fila[1]=txtPuesto.getText();
//						fila[2]=txtAbreviatura.getText();
//						modelo.addRow(fila); 
//						
//						panelLimpiar();
//						panelEnabledFalse();
//						txtFolio.setEditable(true);
//						txtFolio.requestFocus();
//						JOptionPane.showMessageDialog(null,"El registró se guardó de forma segura","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//Exito.png"));
					}
				}
			}			
		}
	};
	
	KeyListener buscar_action = new KeyListener() {
		@Override
		public void keyTyped(KeyEvent e){
		}
		@Override
		public void keyReleased(KeyEvent e) {	
		}
		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				btnBuscar.doClick();
			}
		}
	};
	
	ActionListener buscar = new ActionListener()
	{
		public void actionPerformed(ActionEvent e)
		{
			if(txtFolioFactura.getText().equals("")){
				JOptionPane.showMessageDialog(null, "Ingrese el No. de Folio","Error",JOptionPane.WARNING_MESSAGE);
				return;
			}else{
			Obj_Puestos puesto = new Obj_Puestos();
			puesto = puesto.buscar(Integer.parseInt(txtFolioFactura.getText()));
			
			if(puesto.getFolio() != 0){
			
			txtFolioFactura.setText(puesto.getFolio()+"");
			txtFecha.setText(puesto.getPuesto()+"");
			txtProveedor.setText(puesto.getAbreviatura()+"");
			
			
			if(puesto.getStatus() == true){chStatus.setSelected(true);}
			else{chStatus.setSelected(false);}
			
			btnNuevo.setEnabled(false);
			btnEditar.setEnabled(true);
	
			txtFolioFactura.setEditable(true);
			txtFolioFactura.requestFocus();
			
			}
			else{
				JOptionPane.showMessageDialog(null, "El Registro no existe","Error",JOptionPane.WARNING_MESSAGE);
				return;
				}
			}
		}
	};
	
	ActionListener cerrar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			dispose();
		}
		
	};
	
	private String validaCampos(){
		String error="";
		if(txtFecha.getText().equals("")) 			error+= "Bono\n";
		if(txtProveedor.getText().equals(""))		error+= "Abreviatura\n";
				
		return error;
	}
	
	ActionListener nuevo = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			    txtFolioFactura.setText("");
			    txtFecha.setText("");
			    chStatus.setSelected(true);
			    txtFecha.setEditable(true);
			   	txtFolioFactura.setEditable(true);
				txtFecha.requestFocus();
				btnGuardar.setEnabled(true); 
				btnEditar.setEnabled(false);
				btnNuevo.setEnabled(false);
		}
	};
	
	ActionListener deshacer = new ActionListener(){
		public void actionPerformed(ActionEvent e){

			
			txtFolioFactura.setEditable(true);
			txtFolioFactura.requestFocus();
			btnNuevo.setEnabled(true);
			btnGuardar.setEnabled(false);
			btnEditar.setEnabled(false);
			chStatus.setSelected(false);
			
		}
	};
	
	ActionListener editar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			
			btnGuardar.setEnabled(true);
			txtFolioFactura.setEditable(false);
			btnEditar.setEnabled(false);
			btnNuevo.setEnabled(false);
		}		
	};
	

	
	public void panelLimpiar(){	
		txtFolioFactura.setText("");
		txtFecha.setText("");
		txtProveedor.setText("");
		chStatus.setSelected(true);
	}
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Control_De_Facturas_Y_XML_De_Proveedores("1253","ELDORADO").setVisible(true);
		}catch(Exception e){	}
	}
}