package Cat_Compras;

import java.awt.Container;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Conexiones_SQL.ActualizarSQL;
import Conexiones_SQL.BuscarTablasModel;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Ajuste_De_Ticket_Por_Asignacion extends JFrame{

	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	String establecimiento[] = new Obj_Establecimiento().Combo_Establecimiento201();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbEstablecimiento = new JComboBox(establecimiento);
	
	JTextField txtAsignacion = new JTextField("");
	
	JButton btnAsignacion = new JButton("icon");
	JButton btnGenerar = new JButton("Generar");
	JButton btnQuitar = new JButton("Eliminar Ticket");
	
//  TODO Inicializar (modelo)
  public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"Folio", "Transaccion", "Fecha", "Importe", "IVA", "Estatus", "*"} ){
                  
		@SuppressWarnings({ "rawtypes" })
		Class[] types = new Class[]{
                 java.lang.Object.class,
                 java.lang.Object.class, 
                 java.lang.Object.class, 
                 java.lang.Object.class, 
                 java.lang.Object.class,
                 java.lang.Object.class, 
                 java.lang.Boolean.class
                  
  };
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Class getColumnClass(int columnIndex) {
              return types[columnIndex];
      }
  public boolean isCellEditable(int row, int column){
              switch(column){
                      case 0  : return false; 
                      case 1  : return false; 
                      case 2  : return false; 
                      case 3  : return false; 
                      case 4  : return false; 
                      case 5  : return false; 
                      case 6  : return true; 
              }
               return false;
       }
  };
	
//  TODO Inicializar (tabla y scroll)
  JTable tabla = new JTable(modelo);
	JScrollPane scroll = new JScrollPane(tabla,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
	
	public Cat_Ajuste_De_Ticket_Por_Asignacion(){
		int anchop = Toolkit.getDefaultToolkit().getScreenSize().width;
		int altop = Toolkit.getDefaultToolkit().getScreenSize().height-50;
		this.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
		
		this.setTitle("Ajuste de ticket por asignacion");
		this.panel.setBorder(BorderFactory.createTitledBorder( "Ajuste de ticket"));
		
		int x=15,y=20,ancho=100;
		
		panel.add(new JLabel("Establecimiento: ")).setBounds(x,y,ancho,20);
		panel.add(cmbEstablecimiento).setBounds(x+ancho+15,y,ancho*2,20);
		
		panel.add(new JLabel("Asignacion: ")).setBounds(x,y+=25,ancho,20);
		panel.add(txtAsignacion).setBounds(x+ancho+15,y,ancho*2-30,20);
		panel.add(btnAsignacion).setBounds(x+(ancho*3)-15,y,30,20);
		panel.add(btnGenerar).setBounds(x+(ancho*3)+30,y,ancho,20);
		
		panel.add(scroll).setBounds(x,y+=25, anchop-45, altop-130);
		
		panel.add(btnQuitar).setBounds(anchop-185,y+=(altop-125),ancho+50,20);
		
		txtAsignacion.setEditable(false);
		llamar_render(tabla);
		
		btnAsignacion.addActionListener(opFiltro);
		btnGenerar.addActionListener(opGenerar);
		
		cont.add(panel);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
	}
	
	
	ActionListener opFiltro = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			
			if(cmbEstablecimiento.getSelectedIndex()==0){
				JOptionPane.showMessageDialog(null, "Para seleccionar una asignacion es necesario especificar un establecimiento","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}else{
				new filtro_de_asignaciones(cmbEstablecimiento.getSelectedItem().toString().trim()).setVisible(true);
			}
		}
	};
	
	ActionListener opGenerar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			
			while(tabla.getRowCount()>0){
				modelo.removeRow(0);
			}
			
			if(txtAsignacion.getText().equals("")){
				JOptionPane.showMessageDialog(null, "No se a seleccionado una asignacion","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}else{
//					TODO si se corre el procedimiento (sp_ajuste_avi @asignacion,'R')
					if(new ActualizarSQL().ajuste_avi(cmbEstablecimiento.getSelectedItem().toString().trim())){
						
//						ejecutar select (select todo menos asignacion from IZAGAR_AVI_facremtick)
						String[][] lista=new BuscarTablasModel().tabla_de_ajustes_ticket(txtAsignacion.getText().toString().trim());
						String[] fila = new String[7];
						for(int i=0; i<lista.length; i++){
							fila[0] = lista[i][0]+"";
							fila[1] = lista[i][1]+"";
							fila[2] = lista[i][2]+"";
							fila[3] = lista[i][3]+"";
							fila[4] = lista[i][4]+"";
							fila[5] = lista[i][5]+"";
							fila[6] = lista[i][6]+"";
							
							modelo.addRow(fila);
						}
							
					}else{
						JOptionPane.showMessageDialog(null, "No se a seleccionado una asignacion","ERROR",JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
						return;
					}
				
			}
		}
	};
	
//	TODO Funcion (llamar_render())
	public void llamar_render(JTable tbl){
		
					tbl.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12)); 
					tbl.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12)); 
					tbl.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12));
					tbl.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12)); 
					tbl.getColumnModel().getColumn(4).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12));
					
		int x=100;
		
    	tbl.getTableHeader().setReorderingAllowed(false) ;
    	tbl.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

    	tbl.getColumnModel().getColumn(0 ).setMaxWidth(x);   
    	tbl.getColumnModel().getColumn(0 ).setMinWidth(x);	
    	tbl.getColumnModel().getColumn(1 ).setMaxWidth(x);   
    	tbl.getColumnModel().getColumn(1 ).setMinWidth(x);   
    	tbl.getColumnModel().getColumn(2 ).setMaxWidth(x);
    	tbl.getColumnModel().getColumn(2 ).setMinWidth(x);
    	tbl.getColumnModel().getColumn(3 ).setMaxWidth(x); 
    	tbl.getColumnModel().getColumn(3 ).setMinWidth(x); 

    			

    	
		if(tbl.getColumnCount()==7){
			tbl.getColumnModel().getColumn(5).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12)); 
			tbl.getColumnModel().getColumn(6).setCellRenderer(new tablaRenderer("CHB","derecha","Arial","normal",12));
			
			tbl.getColumnModel().getColumn(4 ).setMaxWidth(x);
	    	tbl.getColumnModel().getColumn(4 ).setMinWidth(x);
	    	
	    	tbl.getColumnModel().getColumn(5 ).setMaxWidth(x);
	    	tbl.getColumnModel().getColumn(5 ).setMinWidth(x);
	    	tbl.getColumnModel().getColumn(6 ).setMaxWidth(x);
	    	tbl.getColumnModel().getColumn(6 ).setMinWidth(x);
		}else{
			tbl.getColumnModel().getColumn(4 ).setMaxWidth(x*3);
	    	tbl.getColumnModel().getColumn(4 ).setMinWidth(x*3);
		}	
    	
    }
	
	
	
	
	
//	TODO subclase (filtro de asignaciones por establecimiento)
	public class filtro_de_asignaciones extends JDialog{
		
		Container cont = getContentPane();
		JLayeredPane panel = new JLayeredPane();
		
		JTextField txtAsignacionFiltro = new JTextField("");
		
	//  TODO Inicializar (modelo)
		  public DefaultTableModel modeloFiltro = new DefaultTableModel(null, new String[]{"Asignacion", "IVA", "IEPS", "Total", "Empleado"} ){
		                  
				@SuppressWarnings({ "rawtypes" })
				Class[] types = new Class[]{
		                 java.lang.Object.class,
		                 java.lang.Object.class, 
		                 java.lang.Object.class, 
		                 java.lang.Object.class, 
		                 java.lang.Object.class
		                  
		  };
				@SuppressWarnings({ "unchecked", "rawtypes" })
				public Class getColumnClass(int columnIndex) {
		              return types[columnIndex];
		      }
		  public boolean isCellEditable(int row, int column){
		              switch(column){
		                      case 0  : return false; 
		                      case 1  : return false; 
		                      case 2  : return false; 
		                      case 3  : return false; 
		                      case 4  : return false; 
		              }
		               return false;
		       }
		  };
			
		//  TODO Inicializar (tablaFiltro y scrollFiltro)
		  JTable tablaFiltro = new JTable(modeloFiltro);
		JScrollPane scrollFiltro = new JScrollPane(tablaFiltro,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public TableRowSorter trsfiltro = new TableRowSorter(modeloFiltro);
		
		@SuppressWarnings("unchecked")
		public filtro_de_asignaciones(String establecimiento_seleccionado){
			this.setTitle("Filtro");
			this.panel.setBorder(BorderFactory.createTitledBorder( "Seleccionar una asignacion"));
			this.setModal(true);
			
			tablaFiltro.setRowSorter(trsfiltro);
			
			int x=15,y=25,ancho=100;
			
			panel.add(new JLabel("Establecimiento: "+establecimiento_seleccionado)).setBounds(x,y,ancho*3,20);
			panel.add(txtAsignacionFiltro).setBounds(x,y+=25,ancho,20);
			
			panel.add(scrollFiltro).setBounds(x,y+=20,705,350);
			
			cont.add(panel);
			
			llamar_render(tablaFiltro);
			
			while(tablaFiltro.getRowCount()>0){
				modeloFiltro.removeRow(0);
			}
			String[][] lista=new BuscarTablasModel().tabla_filtro_de_asignaciones_para_ajuste_de_ticket(establecimiento_seleccionado);
			String[] fila = new String[5];
			for(int i=0; i<lista.length; i++){
				fila[0] = lista[i][0]+"";
				fila[1] = lista[i][1]+"";
				fila[2] = lista[i][2]+"";
				fila[3] = lista[i][3]+"";
				fila[4] = lista[i][4]+"";
				
				modeloFiltro.addRow(fila);
			}
			
			txtAsignacionFiltro.addKeyListener(op_filtro_folio_corte);
			agregar(tablaFiltro);
			
			this.setSize(745,470);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
		}
		
		private void agregar(final JTable tbl) {
	        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
		        public void mouseClicked(MouseEvent e) {
		        	
		        	if(e.getClickCount() == 2){
		        		int fila= tbl.getSelectedRow();
		        		txtAsignacion.setText(tbl.getValueAt(fila, 0).toString().trim());
		        		dispose();
		        	}
		        }
	        });
	    }
		
//		TODO (KeyListener para filtro)
		KeyListener op_filtro_folio_corte = new KeyListener(){
			@SuppressWarnings("unchecked")
			public void keyReleased(KeyEvent arg0) {
				trsfiltro.setRowFilter(RowFilter.regexFilter(txtAsignacionFiltro.getText().toUpperCase().trim(), 0));
			}
			public void keyTyped(KeyEvent arg0) {}
			public void keyPressed(KeyEvent arg0) {}		
		};
	}
	
	public static void main(String [] arg){
		new Cat_Ajuste_De_Ticket_Por_Asignacion().setVisible(true);
	}
}
