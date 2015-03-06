package Cat_Lista_de_Raya;

import java.awt.Component;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.lang.reflect.Method;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

import Conexiones_SQL.ActualizarSQL;
import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.BuscarTablasModel;
import Conexiones_SQL.Connexion;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.Componentes;

@SuppressWarnings({ "serial", "unchecked" })
public class Cat_Traspaso_A_Cobro_De_Fuente_De_Sodas_AUXF extends JFrame {
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	Connexion con = new Connexion();
	
	public static Object[][] get_tabla(){
		return new BuscarTablasModel().tabla_model_empleados_conpendiente_en_fuente_de_sodas_auxf();
	}
	
    public static DefaultTableModel tabla_model = new DefaultTableModel(
    		get_tabla(),	new String[]{	"Folio",	"Nombre Completo", "Establecimiento", "Puesto", "Saldo"}){
                    @SuppressWarnings("rawtypes")
                    Class[] types = new Class[]{
                               java.lang.Object.class, 
                               java.lang.Object.class, 
                               java.lang.Object.class,  
                               java.lang.Object.class,  
                               java.lang.Object.class
                };
                    @SuppressWarnings({ "rawtypes" })
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
                            }
                             return false;
                     }
            };
//	DefaultTableModel model = new DefaultTableModel(0,5){
//		public boolean isCellEditable(int fila, int columna){
//			if(columna < 0)
//				return true;
//			return false;
//		}
//	};
	
	JTable tabla = new JTable(tabla_model);
	JScrollPane panelScroll = new JScrollPane(tabla);
	
	@SuppressWarnings("rawtypes")
	private TableRowSorter trsfiltro;
	
	JTextField txtFolio = new JTextField();
	JTextField txtNombre_Completo = new JTextField();
	String establecimientos[] = new Obj_Establecimiento().Combo_Establecimiento();
	@SuppressWarnings("rawtypes")
	JComboBox cmbEstablecimientos = new JComboBox(establecimientos);
	
	JButton btnPeriodo = new JButton("Periodo");
	JButton btnRecorrer = new JButton("Recorrer");
	JTextField txtPeriodo = new Componentes().text(new JTextField(), "Periodo", 2, "Int");
	
	@SuppressWarnings({ "rawtypes" })
	public Cat_Traspaso_A_Cobro_De_Fuente_De_Sodas_AUXF()	{
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/sun_icon&16.png"));
		this.setTitle("Filtro de empleados con Saldo en fuente de sodas (Auxiliar y Finanzas)");
		panel.setBorder(BorderFactory.createTitledBorder("Empleados consaldo en fuente de sodas"));

		this.init_tabla();
		
		trsfiltro = new TableRowSorter(tabla_model); 
		tabla.setRowSorter(trsfiltro);  
		
		panel.add(panelScroll).setBounds(15,42,800,327);
		
		panel.add(txtFolio).setBounds(15,20,68,20);
		panel.add(txtNombre_Completo).setBounds(85,20,300,20);
		panel.add(cmbEstablecimientos).setBounds(387,20, 180, 20);
		
		panel.add(btnPeriodo).setBounds(572, 20, 90, 20);
		panel.add(txtPeriodo).setBounds(665, 20, 50, 20);
		panel.add(btnRecorrer).setBounds(725, 20, 90, 20);
		
		agregar(tabla);
		
		cont.add(panel);
		
		txtFolio.addKeyListener(opFiltroFolio);
		txtNombre_Completo.addKeyListener(opFiltroNombre);
		cmbEstablecimientos.addActionListener(opFiltro);
		
		btnPeriodo.addActionListener(opPeriodo);
		btnRecorrer.addActionListener(opPeriodo);
		
		txtPeriodo.setEditable(false);
		txtPeriodo.setHorizontalAlignment(0);
		txtPeriodo.setText(seleccionarPeriodo()+"");
		
		this.setSize(850,415);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
	}
	
	public int seleccionarPeriodo(){
		int periodo = 0;
		try {
			periodo = new BuscarSQL().folio_periodo();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return periodo;
	}
	
	private void agregar(final JTable tbl) {
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
	        public void mouseClicked(MouseEvent e) {
	        	if(e.getClickCount() == 2){
	    			
	        		int fila = tabla.getSelectedRow();
	    			int folio =  Integer.parseInt(tabla.getValueAt(fila, 0)+"");
	    			Object empleado =  tabla.getValueAt(fila, 1);
	    			
	    			new Cat_Filtro_Ticket_Fuente_Sodas_AUXF(folio,empleado+"",Integer.valueOf(txtPeriodo.getText())).setVisible(true);
	        	}
	        }
        });
    }
	
	ActionListener opPeriodo = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			
			if(Double.valueOf(txtPeriodo.getText())==20 && e.getActionCommand().equals("Periodo")){
				JOptionPane.showMessageDialog(null, "El limite de periodos es 20", "Aviso", JOptionPane.WARNING_MESSAGE);
				return;
			}else{
				
				if(Double.valueOf(txtPeriodo.getText())==1 && e.getActionCommand().equals("Recorrer")){
					JOptionPane.showMessageDialog(null, "El limite de periodos es 1", "Aviso", JOptionPane.WARNING_MESSAGE);
					return;
				}else{
					
					if(new ActualizarSQL().actualizar_folio_periodo_fs(e.getActionCommand().equals("Periodo")?1:-1)){
						txtPeriodo.setText(seleccionarPeriodo()+"");
					}else{
						JOptionPane.showMessageDialog(null, "No se pudo generar el periodo", "Aviso", JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				
			}
		}
	};
	
	ActionListener opReporte = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			
			
		}
	};
	
	KeyListener opFiltroFolio = new KeyListener(){
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
			trsfiltro.setRowFilter(RowFilter.regexFilter(txtNombre_Completo.getText().toUpperCase().trim(), 1));
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}
		
	};
	
	ActionListener opFiltro = new ActionListener(){
		public void actionPerformed(ActionEvent arg0){
			if(cmbEstablecimientos.getSelectedIndex() != 0){
				trsfiltro.setRowFilter(RowFilter.regexFilter(cmbEstablecimientos.getSelectedItem()+"", 2));
			}else{
				trsfiltro.setRowFilter(RowFilter.regexFilter("", 2));
			}
		}
	};
	
	public void init_tabla(){
        this.tabla.getTableHeader().setReorderingAllowed(false) ;
        
        	   int w=60;
               int x=330;
               int y=160;
               int z=80;
               
                this.tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                
                this.tabla.getColumnModel().getColumn(0).setMaxWidth(w);
                this.tabla.getColumnModel().getColumn(0).setMinWidth(w);
                this.tabla.getColumnModel().getColumn(1).setMaxWidth(x);
                this.tabla.getColumnModel().getColumn(1).setMinWidth(x);
                this.tabla.getColumnModel().getColumn(2).setMaxWidth(y);
                this.tabla.getColumnModel().getColumn(2).setMinWidth(y);
                this.tabla.getColumnModel().getColumn(3).setMaxWidth(y);
                this.tabla.getColumnModel().getColumn(3).setMinWidth(y);
                this.tabla.getColumnModel().getColumn(4).setMaxWidth(z);
                this.tabla.getColumnModel().getColumn(4).setMinWidth(z);
                
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
                                case 4: 
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
        for(int i=0; i<tabla.getColumnCount(); i++){
                this.tabla.getColumnModel().getColumn(i).setCellRenderer(render); 
        }
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
	public static void main(String [] args){
		try{
			UIManager.setLookAndFeel(
					UIManager.getSystemLookAndFeelClassName());
		}catch(Exception e){}
		
		Cat_Traspaso_A_Cobro_De_Fuente_De_Sodas_AUXF thisClass = new Cat_Traspaso_A_Cobro_De_Fuente_De_Sodas_AUXF();
		thisClass.setVisible(true);

		//utilizacion del AWTUtilities con el metodo opaque
		try {
			   @SuppressWarnings("rawtypes")
			Class clazz =  Class.forName("com.sun.awt.AWTUtilities");
			   Method method = clazz.getMethod("setWindowOpaque", java.awt.Window.class, Boolean.TYPE);
			   method.invoke(clazz,thisClass , false);
			   } catch (Exception e) 
			   { }	
	}
}


