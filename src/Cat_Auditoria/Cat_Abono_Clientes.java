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
	
    public static DefaultTableModel tabla_model_abonos = new DefaultTableModel(/*new Obj_Clientes().get_tabla_model()*/null,
            new String[]{"Folio", "Usuario", "Abonos", "Fecha"}){
                    
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
	
	JTable tabla_abonos = new JTable(tabla_model_abonos);
	JScrollPane panelScroll_abonos = new JScrollPane(tabla_abonos);
	
	JTextField txtFolioTiket = new JTextField();
	JTextField txtCliente = new JTextField();
	JTextField txtAbono = new JTextField();
	
	JDateChooser fecha = new JDateChooser();
	
	JButton btnGuardarAbono = new JButton("Guardar");
	JButton btnBuscar = new JButton("");
	
	JLabel lblSigno = new JLabel("$");
	JLabel lblTotal = new JLabel("0000.00");
	
	public Cat_Abono_Clientes(){
		this.setTitle("Abonos Clientes");
		
		lblSigno.setFont(new Font("arial", Font.BOLD, 50));
		lblTotal.setFont(new Font("arial", Font.BOLD, 50));
		
		lblSigno.setForeground(new Color(105,105,105));
		lblTotal.setForeground(new Color(105,105,105));
		
		init_tabla();
		
		int x=20; int y=15; int ancho=80;
		
		panel.add(new JLabel("Folio: ")).setBounds(x, y, ancho, 20);
		panel.add(txtFolioTiket).setBounds(x+50,y,ancho,20);
		panel.add(btnBuscar).setBounds(x+130,y,30,20);
		
		panel.add(new JLabel("Fecha Limite: ")).setBounds(x+165, y, ancho, 20);
		panel.add(fecha).setBounds(x+250,y,ancho+20,20);
		
		panel.add(lblSigno).setBounds(x+360,y,ancho,60);
		panel.add(lblTotal).setBounds(x+390,y,ancho*3,60);
		
		panel.add(new JLabel("Cliente: ")).setBounds(x, y+=25, ancho, 20);
		panel.add(txtCliente).setBounds(x+50,y,ancho*3+60,20);
		
		panel.add(new JLabel("Abono: ")).setBounds(x, y+=25, ancho, 20);
		panel.add(txtAbono).setBounds(x+50,y,ancho,20);
		panel.add(btnGuardarAbono).setBounds(x+150, y, ancho, 20);
		
		panel.add(panelScroll_abonos).setBounds(x, y+=30, 600, 300);
		
		txtCliente.setEditable(false);
		
		btnBuscar.addActionListener(opBuscar);
		cont.add(panel);
		this.setSize(650,450);
	}
	
	public void init_tabla(){
    	
    	this.tabla_abonos.getTableHeader().setReorderingAllowed(false) ;
    	
    	this.tabla_abonos.getColumnModel().getColumn(0).setMaxWidth(72);
    	this.tabla_abonos.getColumnModel().getColumn(0).setMinWidth(72);		
    	this.tabla_abonos.getColumnModel().getColumn(1).setMaxWidth(360);
    	this.tabla_abonos.getColumnModel().getColumn(1).setMinWidth(360);
    	this.tabla_abonos.getColumnModel().getColumn(2).setMaxWidth(210);
    	this.tabla_abonos.getColumnModel().getColumn(2).setMinWidth(210);

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
								case 2 : lbl.setHorizontalAlignment(SwingConstants.RIGHT); break;
								case 3 : lbl.setHorizontalAlignment(SwingConstants.RIGHT); break;
							}
							return lbl; 
					} 
			}; 

		for(int x = 0; x<tabla_abonos.getColumnCount(); x++){
			this.tabla_abonos.getColumnModel().getColumn(x).setCellRenderer(render); 
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
			
//			tabla.addKeyListener(seleccionEmpleadoconteclado);
			
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
		
//		KeyListener seleccionEmpleadoconteclado = new KeyListener() {
//			@SuppressWarnings("static-access")
//			@Override
//			public void keyTyped(KeyEvent e) {
//				char caracter = e.getKeyChar();
//				
//				if(caracter==e.VK_ENTER){
//				int fila=tabla.getSelectedRow()-1;
//				String folio = tabla.getValueAt(fila,0).toString().trim();
//					
//				txtFolioTiket.setText(folio);
//				btnBuscar.doClick();
//				dispose();
//				}
//			}
//			@Override
//			public void keyPressed(KeyEvent e){}
//			@Override
//			public void keyReleased(KeyEvent e){}
//									
//		};
	}
}
