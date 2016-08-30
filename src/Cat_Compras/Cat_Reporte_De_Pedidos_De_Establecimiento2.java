package Cat_Compras;

import java.applet.AudioClip;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Reporte_De_Pedidos_De_Establecimiento2 extends JFrame{
	
	Object[][] Matriz_pedidos_ctes ;
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	Connexion con = new Connexion();
	Runtime R = Runtime.getRuntime();
	
//	JDateChooser fh_inicial = new JDateChooser();
//	JDateChooser fh_final = new JDateChooser();
	
	DefaultTableModel modelo = new DefaultTableModel(null,new String[]{"Folio", "Usuario Capturo","Estab Solicitante","Estab Surte","Fecha Elevoracion","Fecha Modificacion","Status"}
			){
	     @SuppressWarnings("rawtypes")
		Class[] types = new Class[]{
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
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
        	 	case 6 : return false;
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
//	JTextField txtRazon_Social = new JTextField();
	
	JButton btnBuscar = new JButton("ACTUALIZAR MANUAL",new ImageIcon("imagen/refrescar-volver-a-cargar-las-flechas-icono-4094-32.png"));;

	Border blackline, etched, raisedbevel, loweredbevel, empty;
	
    String FechaIn = "";
	String FechaFin = "";
    
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Cat_Reporte_De_Pedidos_De_Establecimiento2(){
		int ancho = 890;//Toolkit.getDefaultToolkit().getScreenSize().width;
		int alto = Toolkit.getDefaultToolkit().getScreenSize().height-50;
		
		this.setSize(ancho, alto);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setTitle("Supervision De Pedidos De Establecimientos");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/lista-icono-7220-32.png"));
		blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
		panel.setBorder(BorderFactory.createTitledBorder(blackline,"Status Revision De Pedidos De Establecimientos"));
		this.cont.add(panel);
		this.addWindowListener(op_cerrar);

		btnBuscar.setEnabled(true);
		btnBuscar.setToolTipText("<F5> Tecla Directa");
		
		trsfiltro = new TableRowSorter(modelo); 
		tabla.setRowSorter(trsfiltro);
		txtFolio.setToolTipText("Filtro Por Folio De Entrada");
		txtFolioProveedor.setToolTipText("Filtro Por Codigo De Proveedor");

		txtFolio.addKeyListener(opFiltroEstablecimiento);
		txtFolioProveedor.addKeyListener(opFiltroFolio);
		
		buscarEntradas("manual");
		
		llamarRender();
		
		int y = 20;
		panel.add(btnBuscar).setBounds(695,y-12,180,32);
		panel.add(txtFolio).setBounds(15,y,60,20);
		panel.add(txtFolioProveedor).setBounds(75,y,260,20);
		panel.add(getPanelTabla()).setBounds(15,y+=20,ancho-30,alto-70);
             
		btnBuscar.addActionListener(Buscar_Cambios);
		
//     Buscar Con F5
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                     KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0), "Actualizar");
                  getRootPane().getActionMap().put("Actualizar", new AbstractAction(){
                      public void actionPerformed(ActionEvent e)
                      {        	    btnBuscar.doClick();          	    }
                  });
//     asigna el foco al JTextField fecha al arrancar la ventana
                  this.addWindowListener(new WindowAdapter() {
                          public void windowOpened( WindowEvent e ){
                        	  txtFolioProveedor.requestFocus();
                       }
                  });
	}
	
	private JScrollPane getPanelTabla()	{	
		
	tabla.getTableHeader().setReorderingAllowed(false) ;
	tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	
	tabla.getColumnModel().getColumn(0).setMinWidth(60);
	tabla.getColumnModel().getColumn(0).setMaxWidth(60);
	tabla.getColumnModel().getColumn(1).setMinWidth(260);
	tabla.getColumnModel().getColumn(1).setMaxWidth(260);
	tabla.getColumnModel().getColumn(2).setMinWidth(125);
	tabla.getColumnModel().getColumn(2).setMaxWidth(125);
	tabla.getColumnModel().getColumn(3).setMinWidth(125);
	tabla.getColumnModel().getColumn(3).setMaxWidth(125);
	tabla.getColumnModel().getColumn(4).setMinWidth(70);
	tabla.getColumnModel().getColumn(4).setMaxWidth(70);
	tabla.getColumnModel().getColumn(5).setMinWidth(115);
	tabla.getColumnModel().getColumn(5).setMaxWidth(115);
	tabla.getColumnModel().getColumn(6).setMinWidth(85);
	tabla.getColumnModel().getColumn(6).setMaxWidth(85);
	
		JScrollPane scrol = new JScrollPane(tabla);
	    return scrol; 
	}
	
	public void buscarEntradas(String tipoDeBusqueda){
		modelo.setRowCount(0);
		
		Statement s;
		ResultSet rs;
		try {
			
			String query = "select pedestab.folio "
					+ "		,usuarios.nombre as usuario_captura "
					+ "		,estab.nombre as estab "
					+ "		,estab_alt.nombre as estab_alterno "
					+ "		,convert(varchar(20),pedestab.fecha_elaboracion,103) AS fecha_elaboracion "
					+ "		,convert(varchar(20),pedestab.ultima_modificacion,103)+' '+convert(varchar(20),pedestab.ultima_modificacion,108) as ultima_modificacion "
					+ "		,case when (pedestab.status_surtido)='N' then 'NUEVO' "
					+ "				when (pedestab.status_surtido)='T' then 'TRANSFERIDO' "
					+ "				else 'RECEPCIONADO' end as status_surtido "
					+ " from pedestab "
					+ " inner join establecimientos estab on estab.cod_estab = pedestab.cod_estab "
					+ " inner join establecimientos estab_alt on estab_alt.cod_estab = pedestab.cod_estab_alterno "
					+ " inner join usuarios on usuarios.usuario = pedestab.usuario_captura "
					+ " where pedestab.ultima_modificacion > CONVERT(DATETIME, convert(varchar(20),getdate()-1,103) ) "
					+ " and pedestab.status_surtido = 'N' "
					+ " order by ultima_modificacion desc";
			
			s = con.conexion_IZAGAR().createStatement();
			rs = s.executeQuery(query);
			
			while (rs.next())
			{ 
			   String [] fila = new String[7];
			   fila[0] = rs.getString(1).trim();
			   fila[1] = rs.getString(2).trim();
			   fila[2] = rs.getString(3).trim(); 
			   fila[3] = rs.getString(4).trim(); 
			   fila[4] = rs.getString(5).trim(); 
			   fila[5] = rs.getString(6).trim(); 
			   fila[6] = rs.getString(7).trim(); 

			   modelo.addRow(fila); 
			}
			System.out.println(query);
			
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en buscarEntradas SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public int getFilas(String qry){
		int filas=0;
		try {
			Statement s = con.conexion_IZAGAR().createStatement();
			ResultSet rs = s.executeQuery(qry);
			while(rs.next()){
				filas++;
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return filas;
	}
	
	KeyListener opFiltroEstablecimiento = new KeyListener(){
		@SuppressWarnings("unchecked")
		public void keyReleased(KeyEvent arg0) {
			trsfiltro.setRowFilter(RowFilter.regexFilter(txtFolio.getText(), 0));
			
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}	
	    };
	
	KeyListener opFiltroFolio = new KeyListener(){
		@SuppressWarnings("unchecked")
		public void keyReleased(KeyEvent arg0) {
			trsfiltro.setRowFilter(RowFilter.regexFilter(txtFolioProveedor.getText().toUpperCase().trim(), 1));
		}
		public void keyTyped(KeyEvent arg0) {}
		public void keyPressed(KeyEvent arg0) {}		
	    };
	   
	public String cargar_fechas(int dias){
		String date = null;
	    	try {
				date = new BuscarSQL().fecha(dias);
				} catch (SQLException e) {
					// catch block
					e.printStackTrace();
					}
		return date;
	};
		    
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
////DISPOSE MATA EL HILO Y DETIENE EL SONIDO SI SONO ALGUNA VEZ		    
 AudioClip sonido;
WindowListener op_cerrar = new WindowListener() {
				public void windowOpened(WindowEvent e) {}
				public void windowIconified(WindowEvent e) {}
				public void windowDeiconified(WindowEvent e) {}
				public void windowDeactivated(WindowEvent e) {}
				@SuppressWarnings({ "unchecked" })
				public void windowClosing(WindowEvent e) {
						trsfiltro.setRowFilter(RowFilter.regexFilter("", 0));
						trsfiltro.setRowFilter(RowFilter.regexFilter("", 1));
						trsfiltro.setRowFilter(RowFilter.regexFilter("", 2));
						
						txtFolio.setText("");
						txtFolioProveedor.setText("");
						
						dispose();
				}
				public void windowClosed(WindowEvent e) {}
				public void windowActivated(WindowEvent e) {}
			};
			
			
	////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////ACTUALIZAR

	ActionListener Buscar_Cambios = new ActionListener(){
		@SuppressWarnings({ })
		public void actionPerformed(ActionEvent e){
			buscarEntradas("manual");
		}
	};
	
   	private void llamarRender()	{		
		tabla.getTableHeader().setReorderingAllowed(false) ;
		tabla.getColumnModel().getColumn(0).setMinWidth(80);
		tabla.getColumnModel().getColumn(1).setMinWidth(420);
		tabla.getColumnModel().getColumn(2).setMinWidth(80);
		tabla.getColumnModel().getColumn(3).setMinWidth(80);
		tabla.getColumnModel().getColumn(4).setMinWidth(80);
		tabla.getColumnModel().getColumn(5).setMinWidth(80);
		tabla.getColumnModel().getColumn(6).setMinWidth(80);
		
		tabla.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12)); 	
		tabla.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 
		tabla.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12)); 	
		tabla.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12)); 
		tabla.getColumnModel().getColumn(4).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12)); 	
		tabla.getColumnModel().getColumn(5).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12)); 
		tabla.getColumnModel().getColumn(6).setCellRenderer(new tablaRenderer("texto","centro","Arial","normal",12)); 	
   	}
	
//	  	pintado De tabla
//		public void PintarEstatusTabla(final JTable tb, String tipo_de_tabla, int columnas){
//			//se crea instancia a clase FormatoTable y se indica columna patron
//	        ColorCeldas ft = new ColorCeldas(tipo_de_tabla,columnas);
//	        tb.setDefaultRenderer (Object.class, ft );
//		}
		    	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Reporte_De_Pedidos_De_Establecimiento2().setVisible(true);
		}catch(Exception e){	}
	}
}