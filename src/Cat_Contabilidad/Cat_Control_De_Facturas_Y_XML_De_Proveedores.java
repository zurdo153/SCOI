package Cat_Contabilidad;

import java.awt.Component;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
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

import com.toedter.calendar.JDateChooser;

import Conexiones_SQL.BuscarTablasModel;
import Conexiones_SQL.Connexion;
import Obj_Contabilidad.Obj_Proveedores;

import Obj_Principal.Componentes;

@SuppressWarnings("serial")
public class Cat_Control_De_Facturas_Y_XML_De_Proveedores extends JFrame{
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	Connexion con = new Connexion();
	
//	DefaultTableModel modelo       = new DefaultTableModel(0,7)	{
//		public boolean isCellEditable(int fila, int columna){
//			if(columna < 0)
//				return true;
//			return false;
//		}
//	};
//	JTable tabla = new JTable(modelo);
//	JScrollPane panelScroll = new JScrollPane(tabla);

	
	DefaultTableModel modelo = new DefaultTableModel(null,
            new String[]{"Cod. P.", "Proveedor","Factura","Fecha Factura","Fecha Ult Mod","Modifico",""}
			){
	     @SuppressWarnings("rawtypes")
		Class[] types = new Class[]{
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
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
        	 	case 2 : return false;
        	 	case 3 : return false;
        	 	case 4 : return false;
        	 	case 5 : return false;
        	 	case 6 : return true;
        	 } 				
 			return false;
 		}
	};
	
    JTable tabla = new JTable(modelo);
    JScrollPane scrollAsignado = new JScrollPane(tabla);
	JTextField txtFolioFiltro = new JTextField();
	JTextField txtProveedorFiltro = new JTextField();
	@SuppressWarnings("rawtypes")
	private TableRowSorter trsfiltro;
	
	JTextField txtFolioFactura = new Componentes().text(new JTextField(), "Folio de La Factura", 25, "String");
//	JTextField txtFecha = new Componentes().text(new JTextField(), "Fecha Factura", 25, "String");
	JDateChooser txtFecha = new JDateChooser();
	
	JTextField txtFolioProveedor =new Componentes().text(new JTextField(), "Folio del Proveedor", 25, "String");
	JTextField txtProveedor = new Componentes().text(new JTextField(), "Proveedor", 200, "String");
	
	JCheckBox chStatus = new JCheckBox("Status");
	
	JButton btnCambProveedorr = new JButton("Cambiar de Proveedor ");
	JButton btnDeshacer = new JButton("Deshacer");
	JButton btnGuardar = new JButton("Guardar");
	JButton btnEditar = new JButton("Editar");
	JButton btnNuevo = new JButton("Agregar");
	
	Border blackline, etched, raisedbevel, loweredbevel, empty;

	String folio_factura_editada=""; 
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Cat_Control_De_Facturas_Y_XML_De_Proveedores(String folio, String proveedor){
		this.setTitle("Control De Facturas y XML De Proveedores");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Toolbox.png"));
		blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
		panel.setBorder(BorderFactory.createTitledBorder(blackline,"Control De Facturas y XML De Proveedores"));
		
		trsfiltro = new TableRowSorter(modelo); 
		tabla.setRowSorter(trsfiltro);
		txtFolioFiltro.setToolTipText("Filtro Por Cod. Prov");
		txtProveedorFiltro.setToolTipText("Filtro Por Proveedor");
		panel.add(txtFolioFiltro).setBounds(20,175,50,20);
		panel.add(txtProveedorFiltro).setBounds(70,175,210,20);
		panel.add(getPanelTabla()).setBounds(20,200,720,520);
				
		panel.add(chStatus).setBounds(360,30,60,20);
		chStatus.setEnabled(true);
		
		panel.add(new JLabel("Folio Factura:")).setBounds(20,30,100,20);
		panel.add(txtFolioFactura).setBounds(105,30,250,20);
		txtFolioFactura.setEditable(false);
				
		panel.add(new JLabel("Fecha Factura:")).setBounds(20,60,100,20);
		panel.add(txtFecha).setBounds(105,60,250,20);
		txtFecha.setEnabled(false);
		
		panel.add(new JLabel("Cod. Proveedor:")).setBounds(20,90,100,20);
		panel.add(txtFolioProveedor).setBounds(105,90,250,20);
		txtFolioProveedor.setEditable(false);
		txtFolioProveedor.setText(folio);
		
		panel.add(new JLabel("Nom. Proveedor:")).setBounds(20,120,100,20);
		panel.add(txtProveedor).setBounds(105,120,250,20);
		txtProveedor.setEditable(false);
		txtProveedor.setText(proveedor);
		
		panel.add(btnCambProveedorr).setBounds(440,30,170,20);
		panel.add(btnNuevo).setBounds(620,30,100,20);
		panel.add(btnEditar).setBounds(440,60,80,20);
		panel.add(btnDeshacer).setBounds(540,60,80,20);
		panel.add(btnGuardar).setBounds(640,60,80,20);

		btnEditar.setEnabled(false);
		btnGuardar.setEnabled(false);
		
		btnCambProveedorr.setToolTipText("<F2> Tecla Directa");
		btnEditar.setToolTipText("<ENTER> Despues De Selecionar 1 de La Tabla");
		btnDeshacer.setToolTipText("<ESC> Tecla Directa");
		btnGuardar.setToolTipText("<F12> Tecla Directa");
		
		btnGuardar.addActionListener(guardar);
		btnCambProveedorr.addActionListener(Cambio_de_Proveedor);
		btnDeshacer.addActionListener(deshacer);
		btnNuevo.addActionListener(nuevo);
		btnEditar.addActionListener(editar);

//     autofiltros de la tabla
		txtFolioFiltro.addKeyListener(opFiltroFolio);
		txtProveedorFiltro.addKeyListener(opFiltroNombre);
		tabla.addMouseListener(opTablaFiltroSeleccion);
		seleccionar_click(tabla);
		
		
		txtFolioFactura.addKeyListener(enterpasarafecha);
		txtFecha.addKeyListener(enter_volver_folio_factura);
		
		
//      asigna el foco al JTextField folio_factura al arrancar la ventana y agrega al proveedor como nuevo
        this.addWindowListener(new WindowAdapter() {
                public void windowOpened( WindowEvent e ){
                    btnNuevo.doClick();
                	txtFolioFactura.requestFocus();
             }
        });
		
//      limpia y habre filtro para cambio de proveedor
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
           KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), "filtro");
        getRootPane().getActionMap().put("filtro", new AbstractAction(){
            public void actionPerformed(ActionEvent e)
            {   txtFolioFactura.setText("");
          	    txtFecha.setDate(null);
          	    btnCambProveedorr.doClick();
          	    }
        });
        
        ///guardar con f12
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0), "guardar");
             getRootPane().getActionMap().put("guardar", new AbstractAction(){
                 public void actionPerformed(ActionEvent e)
                 {                 	    btnGuardar.doClick();
               	    }
             });
             
             ///guardar con f12
             getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                     KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "escape");
                  getRootPane().getActionMap().put("escape", new AbstractAction(){
                      public void actionPerformed(ActionEvent e)
                      {                 	    btnDeshacer.doClick();
                    	    }
                  });
             
        
        this.cont.add(panel);
		this.setSize(760,768);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	
	}

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
		tabla.getColumnModel().getColumn(6).setHeaderValue("");
		tabla.getColumnModel().getColumn(6).setMaxWidth(18);
		tabla.getColumnModel().getColumn(6).setMinWidth(18);
		
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
				
					        		    txtFolioFactura.setText(id.toString().trim());
					        		    
										try {
											Date fecha = new SimpleDateFormat("dd/mm/yyyy").parse(tabla.getValueAt(fila,3).toString().trim());
											txtFecha.setDate(fecha);
										} catch (ParseException e1) {
											e1.printStackTrace();
										}
										
										txtFolioProveedor.setText(tabla.getValueAt(fila,0).toString().trim());
										txtProveedor.setText(tabla.getValueAt(fila,1).toString().trim());
										folio_factura_editada=id.toString().trim();
										txtFolioFactura.setEditable(false);
										txtFecha.setEnabled(false);
										btnEditar.setEnabled(true);
										chStatus.setSelected(true);
										btnEditar.requestFocus();
	        	}
	        }
        });
    }
	
	ActionListener guardar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(txtFolioFactura.getText().equals("")){
				JOptionPane.showMessageDialog(null, "El folio es requerido \n", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
			}else{	
		       Obj_Proveedores proveedor = new Obj_Proveedores().buscar(txtFolioFactura.getText().toUpperCase().trim(),txtFolioProveedor.getText().toUpperCase().trim()) ;

		       if(proveedor.getFolio_factura().equals(txtFolioFactura.getText().toUpperCase().trim())){
		    	   if(folio_factura_editada.equals("")){
		   			JOptionPane.showMessageDialog(null, "La Factura con el Proveedor Selecionado Ya Existe, \n Si Desea Modificarla Seleccionela de la tabla inferior y Editela", "Avisa al Administrador", JOptionPane.WARNING_MESSAGE);
		    		   return;
     		       }else{
				
					if(JOptionPane.showConfirmDialog(null, "La Factura En El Proveedor Ya Existe, ¿Desea Actualizarla?") == 0){
						
						if(validaCampos()!="") {
							JOptionPane.showMessageDialog(null, "los siguientes campos son requeridos:\n"+validaCampos(), "Error al guardar registro", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
							return;
						}else{
							
							proveedor.setFolio_factura(txtFolioFactura.getText().toUpperCase().trim());
							proveedor.setFecha(new SimpleDateFormat("dd/MM/yyyy").format(txtFecha.getDate()));
							proveedor.setCod_prv(txtFolioProveedor.getText().toUpperCase().trim());
							proveedor.setProveedor(txtProveedor.getText().toUpperCase().trim());

						}
						if(proveedor.actualizar(folio_factura_editada)){
							
							while(tabla.getRowCount()>0){
								modelo.removeRow(0);  }
	                    Object [][] lista_proveedores = new BuscarTablasModel().tabla_model_proveedores_guardados();;
	                    String[] fila = new String[6];
	                            for(int i=0; i<lista_proveedores.length; i++){
	                                    fila[0] = lista_proveedores[i][0]+"";
	                                    fila[1] = lista_proveedores[i][1]+"";
	                                    fila[2] = lista_proveedores[i][2]+"";
	                                    fila[3] = lista_proveedores[i][3]+"";
	                                    fila[4] = lista_proveedores[i][4]+"";
	                                    fila[5] = lista_proveedores[i][5]+"";
//	                                    fila[6] = lista_proveedores[i][6]+"";
	                                    modelo.addRow(fila);}
						JOptionPane.showMessageDialog(null,"El registró se actualizó de forma segura","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//Exito.png"));
						}else{
							JOptionPane.showMessageDialog(null,"Error al Actualizar","Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
							return;
						}					
						}else{
						return;
					}}
				}else{
					if(validaCampos()!="") {
						JOptionPane.showMessageDialog(null, "los siguientes campos son requeridos:\n "+validaCampos(), "Error al guardar registro", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
						return;
					}else{
						
						System.out.println("Recibio Informacion GUARDADO");
						proveedor.setFolio_factura(txtFolioFactura.getText().toUpperCase().trim());
						proveedor.setFecha(new SimpleDateFormat("dd/MM/yyyy").format(txtFecha.getDate()));
						proveedor.setCod_prv(txtFolioProveedor.getText().toUpperCase().trim());
						proveedor.setProveedor(txtProveedor.getText().toUpperCase().trim());
						txtFolioFactura.setText("");
						txtFolioFactura.requestFocus();
                       if(proveedor.guardar()){
							while(tabla.getRowCount()>0){
								modelo.removeRow(0);  }
			                    Object [][] lista_proveedores = new BuscarTablasModel().tabla_model_proveedores_guardados();;
			                    String[] fila = new String[6];
			                            for(int i=0; i<lista_proveedores.length; i++){
			                                    fila[0] = lista_proveedores[i][0]+"";
			                                    fila[1] = lista_proveedores[i][1]+"";
			                                    fila[2] = lista_proveedores[i][2]+"";
			                                    fila[3] = lista_proveedores[i][3]+"";
			                                    fila[4] = lista_proveedores[i][4]+"";
			                                    fila[5] = lista_proveedores[i][5]+"";
//			                                    fila[6] = false+"";
			                                    modelo.addRow(fila);}
			                            
						JOptionPane.showMessageDialog(null,"El registró se Guardo Correctamente","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//Exito.png"));
						}else{
							JOptionPane.showMessageDialog(null,"Error al Guardar","Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
							return;
                            }
					}
				}
			}			
		}
	};
	
	private String validaCampos(){
		String error="";
		
		String fechanull = txtFecha.getDate()+"";
		
		if(txtFolioFactura.getText().equals("")) error+="Folio Factura\n";
		if(fechanull.equals("null")) 			error+= "Fecha de Factura\n";
		if(txtProveedor.getText().equals(""))		error+= "Proveedor\n";
		return error;
	}
	
	MouseListener opTablaFiltroSeleccion = new MouseListener() {
		public void mousePressed(MouseEvent e) {
			int fila = tabla.getSelectedRow();
			int columna = tabla.getSelectedColumn();
			
			if(columna==6){
				for(int i=0; i<=tabla.getRowCount()-1; i++){
					tabla.setValueAt(false, i, 6);
				}
				tabla.setValueAt(true, fila, columna);
				}
			}
			public void mouseClicked(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
	};
	
	
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
			trsfiltro.setRowFilter(RowFilter.regexFilter(txtProveedorFiltro.getText().toUpperCase().trim(), 1));
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}		
	    };
	
	KeyListener enterpasarafecha = new KeyListener() {
		public void keyTyped(KeyEvent e){}
		public void keyReleased(KeyEvent e) {}
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				txtFecha.transferFocus();
			}
		}
	};
	
	KeyListener enter_volver_folio_factura = new KeyListener() {
		public void keyTyped(KeyEvent e){}
		public void keyReleased(KeyEvent e) {}
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER){
				txtFecha.transferFocusBackward();
			}
		}
	};
	
	ActionListener Cambio_de_Proveedor = new ActionListener(){
		public void actionPerformed(ActionEvent e){
		    btnDeshacer.doClick();
		    dispose();
			new Cat_Filtro_Proveedores().setVisible(true);
		}
	};
	
	ActionListener nuevo = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			    txtFolioFactura.setText("");
			    txtFecha.setDate(null);
			    chStatus.setSelected(true);
			    txtFecha.setEnabled(true);
			   	txtFolioFactura.setEditable(true);
				txtFolioFactura.requestFocus();
				btnGuardar.setEnabled(true); 
				btnEditar.setEnabled(false);
				btnNuevo.setEnabled(false);
		}
	};
	
	ActionListener deshacer = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			txtFolioFactura.setText("");
			txtFecha.setDate(null);
			txtFolioFactura.setEditable(true);
			txtFolioFactura.requestFocus();
			btnNuevo.setEnabled(true);
			btnGuardar.setEnabled(false);
			btnEditar.setEnabled(false);
			chStatus.setSelected(true);
			
		}
	};
	
	ActionListener editar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			btnGuardar.setEnabled(true);
			txtFolioFactura.setEditable(true);
			txtFecha.setEnabled(true);
			btnEditar.setEnabled(false);
			btnNuevo.setEnabled(false);
			txtFolioFactura.requestFocus();
		}		
	};

	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Control_De_Facturas_Y_XML_De_Proveedores("1253","ELDORADO").setVisible(true);
		}catch(Exception e){	}
	}
}