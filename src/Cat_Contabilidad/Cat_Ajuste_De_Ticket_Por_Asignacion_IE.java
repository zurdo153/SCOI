package Cat_Contabilidad;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;

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
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Conexiones_SQL.ActualizarSQL;
import Conexiones_SQL.BuscarTablasModel;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Ajuste_De_Ticket_Por_Asignacion_IE extends JFrame{

	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	String establecimiento[] = new Obj_Establecimiento().Combo_Establecimiento201();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbEstablecimiento = new JComboBox(establecimiento);
	JTextField txtAsignacion = new JTextField("");
	JTextField txtieps= new JTextField("");
	JTextField txtAsignacionCorregir= new JTextField("");
	JButton btnAsignacion = new JButton(new ImageIcon("Imagen/Filter-List-icon16.png"));
	JButton btnQuitar = new JButton("Eliminar Tickets",new ImageIcon("imagen/eliminar-bala-icono-7773-32.png"));
	JButton btnBuscarCambio = new JButton("Generar Cambios",new ImageIcon("imagen/refrescar-volver-a-cargar-las-flechas-icono-4094-16.png"));
	JButton btnAsignacionDCambio = new JButton(new ImageIcon("Imagen/Filter-List-icon16.png"));
	JButton btnDevolverCambio = new JButton("Corregir Cambios",new ImageIcon("imagen/actualizar-actualiza-icono-7372-16.png"));
	JLabel JlbLogI = new JLabel(new ImageIcon("Imagen/I1-icon.png")); 
	JLabel JlbLogE = new JLabel(new ImageIcon("Imagen/E1-icon.png"));
	JLabel JlbLogP = new JLabel(new ImageIcon("Imagen/P1-icon.png"));
	JLabel JlbLogS = new JLabel(new ImageIcon("Imagen/S1-icon.png"));
	JLabel JlbLogI2 = new JLabel(new ImageIcon("Imagen/I1-icon.png")); 
	JLabel JlbLogE2 = new JLabel(new ImageIcon("Imagen/E1-icon.png"));
	JLabel JlbLogP2 = new JLabel(new ImageIcon("Imagen/P1-icon.png"));
	JLabel JlbLogS2 = new JLabel(new ImageIcon("Imagen/S1-icon.png"));
	
//  TODO Inicializar (modelo)
  public DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"Folio", "Transaccion", "Fecha", "Importe", "IEPS", "Estatus", "*"} ){
                  
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
	
	double iva=0;
	public Cat_Ajuste_De_Ticket_Por_Asignacion_IE(){
		int anchop = 755;
		int altop = Toolkit.getDefaultToolkit().getScreenSize().height-50;
		
	    this.setSize(anchop,altop);
		setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/actualizar-actualiza-icono-7372-128.png"));
		
		this.setTitle("Ajuste De Ticket Por Asignacion IE");
		this.panel.setBorder(BorderFactory.createTitledBorder( "Ticket IE"));
		int x=15,y=20,ancho=100, height=20;
		panel.add(new JLabel("Establecimiento: ")).setBounds(x,y,ancho,height);
		panel.add(cmbEstablecimiento).setBounds(ancho,y,ancho*2,20);
		panel.add(btnQuitar).setBounds(anchop-180,y,140,height);
		panel.add(new JLabel("Asignacion: ")).setBounds(x,y+=25,ancho,height);
		panel.add(txtAsignacion).setBounds(ancho,y,ancho*2-30,height);
		panel.add(btnAsignacion).setBounds((ancho*3)-30,y,30,height);
		panel.add(JlbLogI).setBounds((ancho*3)+30,y,10,height);
		panel.add(JlbLogE).setBounds((ancho*3)+45,y,10,height);
		panel.add(JlbLogP).setBounds((ancho*3)+60,y,10,height);
		panel.add(JlbLogS).setBounds((ancho*3)+75,y,10,height);

		panel.add(new JLabel("Total IEPS: ")).setBounds(x+(ancho*3)+220,y,ancho,height);
		panel.add(txtieps).setBounds(x+(ancho*3)+284,y,ancho,height);
		panel.add(scroll).setBounds(x,y+=25, anchop-35, altop-130);
		
		y=altop-55;
		panel.add(btnBuscarCambio).setBounds(x,y,ancho+40,height);
		
		panel.add(JlbLogI2).setBounds((ancho*3)+30,y,10,height);
		panel.add(JlbLogE2).setBounds((ancho*3)+45,y,10,height);
		panel.add(JlbLogP2).setBounds((ancho*3)+60,y,10,height);
		panel.add(JlbLogS2).setBounds((ancho*3)+75,y,10,height);
		
		panel.add(btnAsignacionDCambio).setBounds(x+435,y,20,height);
		panel.add(txtAsignacionCorregir).setBounds(x+455,y,90,height);
		panel.add(btnDevolverCambio).setBounds(x+545,y,140,height);
		
		txtAsignacion.setEditable(false);
		txtieps.setEditable(false);
		llamar_render(tabla);
		btnAsignacion.addActionListener(opFiltro);
		btnQuitar.addActionListener(opEliminar_Tickets_Selecionado);
		btnBuscarCambio.addActionListener(opCargar_Cambios_De_Tickets_de_la_Asignacion);
		btnAsignacionDCambio.addActionListener(opFiltro_Asignaciones_Pendientes_corregir);
		btnDevolverCambio.addActionListener(opDevolver_Cambios_De_Tickets_de_la_Asignacion);
		
		btnBuscarCambio.setEnabled(false);
		btnQuitar.setEnabled(false);
		btnDevolverCambio.setEnabled(false);
		txtAsignacionCorregir.setEditable(false);
		cont.add(panel);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
	}
	
	
	ActionListener opFiltro = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			txtAsignacion.setText("");
			txtieps.setText("");
			txtAsignacionCorregir.setText("");
			iva=0;
			 btnBuscarCambio.setEnabled(false);
			 btnQuitar.setEnabled(false);
			
			if(cmbEstablecimiento.getSelectedIndex()==0){
				JOptionPane.showMessageDialog(null, "Para Seleccionar Una Asignacion Es Necesario Especificar Primero Un Establecimiento","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}else{
				
				while(tabla.getRowCount()>0){
					modelo.removeRow(0);
				}
				new filtro_de_asignaciones(cmbEstablecimiento.getSelectedItem().toString().trim()).setVisible(true);
			}
		}
	};
	
	ActionListener opFiltro_Asignaciones_Pendientes_corregir = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			txtAsignacion.setText("");
			txtAsignacionCorregir.setText("");
			txtieps.setText("");
			iva=0;
			 btnBuscarCambio.setEnabled(false);
			 btnDevolverCambio.setEnabled(false);
			 btnQuitar.setEnabled(false);
				while(tabla.getRowCount()>0){
					modelo.removeRow(0);
				}
				new filtro_de_asignaciones_pendientes_de_corregir().setVisible(true);
		}
	};
	
	
	public void llenado_de_tabla_de_tickets(){
		iva=0;
		txtieps.setText("");
		DecimalFormat df = new DecimalFormat("#0.0000");
		
		String[][] lista=new BuscarTablasModel().tabla_de_ajustes_ticket_ieps(txtAsignacion.getText().toString().trim());
		if(lista.length==0){
			 btnBuscarCambio.setEnabled(false);
			 btnQuitar.setEnabled(false);
			JOptionPane.showMessageDialog(null, "No Se Encontraron Tickets Disponibles De La Asignacion Seleccionada","Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("imagen/usuario-de-alerta-icono-4069-64.png"));
			return;
		}else{
		String[] fila = new String[7];
		for(int i=0; i<lista.length; i++){
			fila[0] = lista[i][0]+"";
			fila[1] = lista[i][1]+"";
			fila[2] = lista[i][2]+"";
			fila[3] = lista[i][3]+"";
			fila[4] = lista[i][4]+"";
			fila[5] = lista[i][5]+"";
			fila[6] = lista[i][6]+"";
			iva=iva+Double.valueOf(lista[i][4]);
			modelo.addRow(fila);
		}
		txtieps.setText(df.format(iva));
		 btnBuscarCambio.setEnabled(true);
		 btnQuitar.setEnabled(true);
		}
	};
	
	
//	TODO Funcion (llamar_render())
	public void llamar_render(JTable tbl){
		
					tbl.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","izaquierda","Arial","normal",12)); 
					tbl.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12)); 
					tbl.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12));
					tbl.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12)); 
					tbl.getColumnModel().getColumn(4).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12));
					tbl.getColumnModel().getColumn(5).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
					
		int x=100;
		
    	tbl.getTableHeader().setReorderingAllowed(false) ;
    	tbl.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    	tbl.getColumnModel().getColumn(0 ).setMaxWidth(x+40);   
    	tbl.getColumnModel().getColumn(0 ).setMinWidth(x);	
    	tbl.getColumnModel().getColumn(1 ).setMaxWidth(x+40);   
    	tbl.getColumnModel().getColumn(1 ).setMinWidth(x+40);   
    	tbl.getColumnModel().getColumn(2 ).setMaxWidth(x);
    	tbl.getColumnModel().getColumn(2 ).setMinWidth(x);
    	tbl.getColumnModel().getColumn(3 ).setMaxWidth(x); 
    	tbl.getColumnModel().getColumn(3 ).setMinWidth(x); 
    	tbl.getColumnModel().getColumn(4 ).setMaxWidth(x); 
    	tbl.getColumnModel().getColumn(4 ).setMinWidth(x);


		if(tbl.getColumnCount()==7){
			tbl.getColumnModel().getColumn(5).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12)); 
			tbl.getColumnModel().getColumn(6).setCellRenderer(new tablaRenderer("CHB","derecha","Arial","normal",12));
			tbl.getColumnModel().getColumn(4 ).setMaxWidth(x);
	    	tbl.getColumnModel().getColumn(4 ).setMinWidth(x);
	    	tbl.getColumnModel().getColumn(5 ).setMaxWidth(x);
	    	tbl.getColumnModel().getColumn(5 ).setMinWidth(x);
	    	tbl.getColumnModel().getColumn(6 ).setMaxWidth(x-55);
	    	tbl.getColumnModel().getColumn(6 ).setMinWidth(x-55);
		}else{
	    	tbl.getColumnModel().getColumn(5 ).setMaxWidth(x*3); 
	    	tbl.getColumnModel().getColumn(5 ).setMinWidth(x*3); 
		}	
    	
    }
	
	ActionListener opEliminar_Tickets_Selecionado = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			
			String[][] matriz_tickets_eliminados =  new String[tabla.getRowCount()][2];
			
			for(int i=0; i<tabla.getRowCount(); i++){
					matriz_tickets_eliminados[i][0] = tabla.getValueAt(i,0).toString().trim();
					matriz_tickets_eliminados[i][1] = tabla.getValueAt(i,6).toString().trim();
			}
         
		   new ActualizarSQL().Actualizar_tickets_seleccionados_avieps(matriz_tickets_eliminados);
			while(tabla.getRowCount()>0){
				modelo.removeRow(0);
			}
			llenado_de_tabla_de_tickets();
		}
	};
	
		
	
//	TODO subclase (filtro de asignaciones por establecimiento)
	public class filtro_de_asignaciones extends JDialog{
		
		Container cont = getContentPane();
		JLayeredPane panel = new JLayeredPane();
		
		JTextField txtAsignacionFiltro = new JTextField("");
		
		  public DefaultTableModel modeloFiltro = new DefaultTableModel(null, new String[]{"Asignacion","Fecha Liquidacion","IVA", "IEPS", "Total", "Cajero"} ){
		                  
				@SuppressWarnings({ "rawtypes" })
				Class[] types = new Class[]{
		                 java.lang.Object.class,
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
		                      case 5  : return false; 
		              }
		               return false;
		       }
		  };
			
		  JTable tablaFiltro = new JTable(modeloFiltro);
		JScrollPane scrollFiltro = new JScrollPane(tablaFiltro,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public TableRowSorter trsfiltro = new TableRowSorter(modeloFiltro);
		
		@SuppressWarnings("unchecked")
		public filtro_de_asignaciones(String establecimiento_seleccionado){
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Filter-List-icon32.png"));
			this.setTitle("Filtro");
			this.panel.setBorder(BorderFactory.createTitledBorder( "Seleccionar una asignacion"));
			this.setModal(true);
			
			tablaFiltro.setRowSorter(trsfiltro);
			
			int x=15,y=25,ancho=100;
			
			panel.add(new JLabel("Establecimiento: "+establecimiento_seleccionado)).setBounds(x,y,ancho*3,20);
			panel.add(txtAsignacionFiltro).setBounds(x,y+=25,ancho,20);
			panel.add(scrollFiltro).setBounds(x,y+=20,825,450);
			cont.add(panel);
			llamar_render(tablaFiltro);
			
			while(tablaFiltro.getRowCount()>0){
				modeloFiltro.removeRow(0);
			}
			String[][] lista=new BuscarTablasModel().tabla_filtro_de_asignaciones_para_ajuste_de_ticket(establecimiento_seleccionado);
			String[] fila = new String[6];
			for(int i=0; i<lista.length; i++){
				fila[0] = lista[i][0]+"";
				fila[1] = lista[i][1]+"";
				fila[2] = lista[i][2]+"";
				fila[3] = lista[i][3]+"";
				fila[4] = lista[i][4]+"";
				fila[5] = lista[i][5]+"";
				modeloFiltro.addRow(fila);
			}
			
			txtAsignacionFiltro.addKeyListener(op_filtro_folio_corte);
			agregar(tablaFiltro);
			
			this.setSize(875,565);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
		}
		
		
		//TODO ( se selecciona la asignacion,se llena las tablas con los productos, se llena la tabla de la ventana con los tickets )	
		private void agregar(final JTable tbl) {
	        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
		        public void mouseClicked(MouseEvent e) {
		        	
		        	if(e.getClickCount() == 2){
		        		int fila= tbl.getSelectedRow();
		        		txtAsignacion.setText(tbl.getValueAt(fila, 0).toString().trim());
		        		dispose();
						if(new ActualizarSQL().ajuste_avi(txtAsignacion.getText().toString().trim(), "I")){
	  			 	        llenado_de_tabla_de_tickets();
						}else{
							JOptionPane.showMessageDialog(null, "Error Al Intentar Llenar La Tabla de Tickets","ERROR",JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
							return;
						}
		        	}
		        }
	        });
	    }

		KeyListener op_filtro_folio_corte = new KeyListener(){
			@SuppressWarnings("unchecked")
			public void keyReleased(KeyEvent arg0) {
				trsfiltro.setRowFilter(RowFilter.regexFilter(txtAsignacionFiltro.getText().toUpperCase().trim(), 0));
			}
			public void keyTyped(KeyEvent arg0) {}
			public void keyPressed(KeyEvent arg0) {}		
		};
	}
	
	
	
	
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
//	TODO subclase (filtro de asignaciones por establecimiento)
	public class filtro_de_asignaciones_pendientes_de_corregir extends JDialog{
		
		Container cont = getContentPane();
		JLayeredPane panel = new JLayeredPane();
		
		JTextField txtAsignacionFiltro = new JTextField("");
		
		  public DefaultTableModel modeloFiltro2 = new DefaultTableModel(null, new String[]{"Asignacion","Fecha Liquidacion","IEPS"} ){
		                  
				@SuppressWarnings({ "rawtypes" })
				Class[] types = new Class[]{
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
		              }
		               return false;
		       }
		  };
			
		//  TODO Inicializar (tablaFiltro y scrollFiltro)
		  JTable tablaFiltro = new JTable(modeloFiltro2);
		JScrollPane scrollFiltro = new JScrollPane(tablaFiltro,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public TableRowSorter trsfiltro = new TableRowSorter(modeloFiltro2);
		
		@SuppressWarnings("unchecked")
		public filtro_de_asignaciones_pendientes_de_corregir(){
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Filter-List-icon32.png"));
			this.setTitle("Filtro");
			this.panel.setBorder(BorderFactory.createTitledBorder( "Seleccionar una asignacion"));
			this.setModal(true);
			
			tablaFiltro.setRowSorter(trsfiltro);
			int x=15,y=25,ancho=100;
			
			panel.add(txtAsignacionFiltro).setBounds(x,y,ancho,20);
			panel.add(scrollFiltro).setBounds(x,y+=20,345,350);
			cont.add(panel);
			llamar_render_filtro_de_asignaciones_pendientes_de_corregir(tablaFiltro);
			modeloFiltro2.setRowCount(0);
			
			String[][] lista=new BuscarTablasModel().tabla_filtro_de_asignaciones_para_corregir_asignacion_ieps();
			String[] fila = new String[3];
			for(int i=0; i<lista.length; i++){
				fila[0] = lista[i][0]+"";
				fila[1] = lista[i][1]+"";
				fila[2] = lista[i][2]+"";
				modeloFiltro2.addRow(fila);
			}
			
			txtAsignacionFiltro.addKeyListener(op_filtro_folio_corte);
			agregar(tablaFiltro);
			
			this.setSize(380,440);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
		}
		
		public void llamar_render_filtro_de_asignaciones_pendientes_de_corregir(JTable tbl){
			tbl.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
			tbl.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12)); 
			tbl.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12));
 	
             int x=100;
            tbl.getTableHeader().setReorderingAllowed(false) ;
            tbl.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            tbl.getColumnModel().getColumn(0 ).setMaxWidth(x+40);   
			tbl.getColumnModel().getColumn(0 ).setMinWidth(x);	
			tbl.getColumnModel().getColumn(1 ).setMaxWidth(x+40);   
			tbl.getColumnModel().getColumn(1 ).setMinWidth(x+40);   
			tbl.getColumnModel().getColumn(2 ).setMaxWidth(x);
			tbl.getColumnModel().getColumn(2 ).setMinWidth(x);
           };
		
		
		private void agregar(final JTable tbl) {
	        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
		        public void mouseClicked(MouseEvent e) {
		        	
		        	if(e.getClickCount() == 2){
		        		int fila= tbl.getSelectedRow();
		        		txtAsignacionCorregir.setText(tbl.getValueAt(fila, 0).toString().trim());
		        		dispose();
		        		btnDevolverCambio.setEnabled(true);
		        	}
		        }
	        });
	    }
		
		KeyListener op_filtro_folio_corte = new KeyListener(){
			@SuppressWarnings("unchecked")
			public void keyReleased(KeyEvent arg0) {
				trsfiltro.setRowFilter(RowFilter.regexFilter(txtAsignacionFiltro.getText().toUpperCase().trim(), 0));
			}
			public void keyTyped(KeyEvent arg0) {}
			public void keyPressed(KeyEvent arg0) {}		
		};
	}
	
//	TODO (KeyListener Hacer Cambio de valores del tickets antes de fac)
	ActionListener opCargar_Cambios_De_Tickets_de_la_Asignacion = new ActionListener(){
		public void actionPerformed(ActionEvent e){
		  if( new ActualizarSQL().Cargar_Cambios_De_Tickets_de_la_Asignacion_del_ieps(txtAsignacion.getText()+"")){
			  modelo.setRowCount(0);
			  txtAsignacion.setText("");
			  txtieps.setText("");
				JOptionPane.showMessageDialog(null, "Se Cambiaron Correctamente Los Datos", "Aviso", JOptionPane.OK_OPTION,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
		  };
		}
	};
	
//	TODO (KeyListener Hacer Cambio de valores del tickets antes de fac)
	ActionListener opDevolver_Cambios_De_Tickets_de_la_Asignacion = new ActionListener(){
		public void actionPerformed(ActionEvent e){
		  if( new ActualizarSQL().Devolver_Cambios_De_Tickets_de_la_Asignacion(txtAsignacionCorregir.getText()+"")){
			  txtAsignacionCorregir.setText("");
				JOptionPane.showMessageDialog(null, "Se Devolvieron Correctamente Los Datos", "Aviso", JOptionPane.OK_OPTION,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
		  };
		}
	};
	
	public static void main(String [] arg){
		try{
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			new Cat_Ajuste_De_Ticket_Por_Asignacion_IE().setVisible(true);
		}catch(Exception e){}
		

	}
}
