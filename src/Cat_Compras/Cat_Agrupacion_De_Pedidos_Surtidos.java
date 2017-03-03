package Cat_Compras;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Connexion;
import Conexiones_SQL.Generacion_Reportes;
import Obj_Principal.JCButton;
import Obj_Principal.Obj_Filtro_Dinamico;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Agrupacion_De_Pedidos_Surtidos extends JFrame{
	Object[][] Matriz_pedidos_ctes ;
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	Connexion con = new Connexion();
	Runtime R = Runtime.getRuntime();
	
	DefaultTableModel modelo = new DefaultTableModel(null,new String[]{"*","Folio", "Usuario Capturo","Estab Solicitante","Estab Surte","Fecha Elaboracion","Fecha Modificacion","Status","F.Agrupacion"}){
	     @SuppressWarnings("rawtypes")
		Class[] types = new Class[]{java.lang.Boolean.class,
							    	java.lang.String.class,
							    	java.lang.String.class,
							    	java.lang.String.class,
							    	java.lang.String.class,
							    	java.lang.String.class,
							    	java.lang.String.class,
							    	java.lang.String.class,
							    	java.lang.String.class, };
	     
	     @SuppressWarnings({ "rawtypes", "unchecked" })
		public Class getColumnClass(int columnIndex) {
             return types[columnIndex];
         }
         public boolean isCellEditable(int fila, int columna){
        	 switch(columna){
        	 	case 0 : return (modelo.getValueAt(fila, 8).equals("")&&modelo.getValueAt(fila, 7).equals("SURTIDO"))?true:false; 
        	 	case 1 : return false; 
        	 	case 2 : return false;
        	 	case 3 : return false;
        	 	case 4 : return false;
        	 	case 5 : return false;
        	 	case 6 : return false;
        	 	case 7 : return false;
        	 	case 8 : return false;
        	 } 				
 			return false;
 		}
	};
	
    JTable tabla = new JTable(modelo);
    JScrollPane scrollAsignado = new JScrollPane(tabla,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	@SuppressWarnings("rawtypes")
	private TableRowSorter trsfiltro;
	
	JTextField txtFolio= new JTextField();
	JTextField txtFolioProveedor = new JTextField();
	
	JButton btnActualizarFiltro = new JCButton("ACTUALIZAR","refrescar-volver-a-cargar-las-flechas-icono-4094-32.png","Azul");
	JButton btnGuardar = new JCButton("GUARDAR","guardar-documento-icono-7840-32.png","Azul");
	JButton btnReporte = new JCButton("Reporte","Lista-32.png","Azul");
	
	Border blackline, etched, raisedbevel, loweredbevel, empty;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Cat_Agrupacion_De_Pedidos_Surtidos(){
		int ancho = 1024;//Toolkit.getDefaultToolkit().getScreenSize().width;
		int alto = Toolkit.getDefaultToolkit().getScreenSize().height-50;
		
		this.setSize(ancho, alto);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setTitle("Agrupacion De Pedidos Surtidos");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/lista-icono-7220-32.png"));
		blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
		panel.setBorder(BorderFactory.createTitledBorder(blackline,"Seleccion De Pedido Para Agrupar"));
		this.cont.add(panel);

		btnActualizarFiltro.setEnabled(true);
		btnActualizarFiltro.setToolTipText("<F5> Tecla Directa");
		
		trsfiltro = new TableRowSorter(modelo); 
		tabla.setRowSorter(trsfiltro);
		txtFolio.setToolTipText("Filtro Por Folio De Entrada");
		txtFolioProveedor.setToolTipText("Filtro Por Codigo De Proveedor");

		txtFolio.addKeyListener(opFiltro);
		txtFolioProveedor.addKeyListener(opFiltro);
		
		int y = 20;
		panel.add(btnReporte).setBounds(420,y-12,180,32);
		panel.add(btnActualizarFiltro).setBounds(625,y-12,180,32);
		panel.add(btnGuardar).setBounds(830,y-12,180,32);
		panel.add(txtFolio).setBounds(65,y,80,20);
		panel.add(txtFolioProveedor).setBounds(145,y,270,20);
		panel.add(scrollAsignado).setBounds(15,y+=20,ancho-30,alto-75);
             
		llamarRender();
		consultarfiltro();

		btnActualizarFiltro.addActionListener(Buscar_Cambios);
		btnGuardar.addActionListener(opGuardar);
		btnReporte.addActionListener(opReporte);
		
//     Buscar Con F5
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                     KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0), "Actualizar");
                  getRootPane().getActionMap().put("Actualizar", new AbstractAction(){
                      public void actionPerformed(ActionEvent e)
                      {        	    btnActualizarFiltro.doClick();          	    }
                  });
//     asigna el foco al JTextField fecha al arrancar la ventana
                  this.addWindowListener(new WindowAdapter() {
                          public void windowOpened( WindowEvent e ){
                        	  txtFolioProveedor.requestFocus();
                       }
                  });
	}
	
	ActionListener opGuardar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			String cadenaTransferencias="";
			
			for(int i= 0; i<modelo.getRowCount(); i++){
				if(Boolean.valueOf(modelo.getValueAt(i, 0).toString().trim())){
					cadenaTransferencias+=modelo.getValueAt(i, 1)+",";
				}
			}
			System.out.println(cadenaTransferencias);
			if(cadenaTransferencias.length()>0){
				
				String folioGenerado = new BuscarSQL().folio_Pedido_Agrupado(cadenaTransferencias);
				
				if(!folioGenerado.equals("")){
					consultarfiltro();
					JOptionPane.showMessageDialog(null,"La Agrupacion Se Realizo Satisfactoriamente con el Folio: [ "+folioGenerado+" ].","Aviso", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
					return;
				}else{
					JOptionPane.showMessageDialog(null, cadenaTransferencias+"No Se Ha Generado Nunguna Agrupacion","Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
					return;
				}
				
			}else{
				JOptionPane.showMessageDialog(null, "No Se Han Seleccionado Pedidos Para Agrupar","Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}
		}
	};
	
	ActionListener opReporte = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String basedatos="2.26";
			String vista_previa_reporte="no";
			int vista_previa_de_ventana=0;
			String comando="exec sp_select_pedidos_agrupados";
			String reporte = "Obj_Reporte_De_Pedidos_Agrupados.jrxml";
					 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
			}
	};
	
	ActionListener Buscar_Cambios = new ActionListener(){
		@SuppressWarnings({ })
		public void actionPerformed(ActionEvent e){
			consultarfiltro();
		}
	};
	
	public void consultarfiltro(){
		
		modelo.setRowCount(0);
		Statement s;
		ResultSet rs;
		try {
			String query = "exec sp_select_pedidos_para_agrupar"; 
			s = con.conexion().createStatement();
			rs = s.executeQuery(query);
			while (rs.next())
			{ 
			   Object [] fila = new Object[9];
			   fila[0] = rs.getBoolean(1);
			   fila[1] = rs.getString(2).trim();
			   fila[2] = rs.getString(3).trim(); 
			   fila[3] = rs.getString(4).trim(); 
			   fila[4] = rs.getString(5).trim(); 
			   fila[5] = rs.getString(6).trim(); 
			   fila[6] = rs.getString(7).trim();
			   fila[7] = rs.getString(8).trim();
			   fila[8] = rs.getString(9).trim();

			   modelo.addRow(fila); 
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en consultarfiltro SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	KeyListener opFiltro = new KeyListener(){
		@SuppressWarnings("unchecked")
		public void keyReleased(KeyEvent arg0) {
			
			new Obj_Filtro_Dinamico(tabla,"Folio", txtFolio.getText().toUpperCase().trim(),"Usuario Capturo",txtFolioProveedor.getText().toUpperCase().trim(), "", "", "", "");
			trsfiltro.setRowFilter(RowFilter.regexFilter(txtFolio.getText(), 0));
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}	
	};
	
   	private void llamarRender()	{		
		tabla.getTableHeader().setReorderingAllowed(false) ;
		tabla.getColumnModel().getColumn(0).setMinWidth(30);
		tabla.getColumnModel().getColumn(1).setMinWidth(80);
		tabla.getColumnModel().getColumn(2).setMinWidth(270);
		tabla.getColumnModel().getColumn(3).setMinWidth(130);
		tabla.getColumnModel().getColumn(4).setMinWidth(130);
		tabla.getColumnModel().getColumn(5).setMinWidth(85);
		tabla.getColumnModel().getColumn(6).setMinWidth(85);
		tabla.getColumnModel().getColumn(7).setMinWidth(80);
		tabla.getColumnModel().getColumn(8).setMinWidth(80);
		
		tabla.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("CHB","centro","Arial","normal",12)); 	
		tabla.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
		tabla.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 	
		tabla.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
		tabla.getColumnModel().getColumn(4).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 	
		tabla.getColumnModel().getColumn(5).setCellRenderer(new tablaRenderer("texto","centro","Arial","normal",12)); 
		tabla.getColumnModel().getColumn(6).setCellRenderer(new tablaRenderer("texto","centro","Arial","normal",12)); 
		tabla.getColumnModel().getColumn(7).setCellRenderer(new tablaRenderer("texto","centro","Arial","normal",12)); 
		tabla.getColumnModel().getColumn(8).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
   	}
   	
   	public static void main(String [] arg){
   		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Agrupacion_De_Pedidos_Surtidos().setVisible(true);
		}catch(Exception e){	}
   	}
}	