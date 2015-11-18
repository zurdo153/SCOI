package Cat_Reportes;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.BuscarTablasModel;
import Conexiones_SQL.Generacion_Reportes;
import Obj_Principal.Componentes;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_Filtro_Dinamico;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Reportes_De_Cortes_De_Lista_De_Raya_Actual extends JFrame {
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JLabel lblFolioEmpleado = new JLabel("Folio Empleado:");
	JTextField txtFolioEmpleado = new Componentes().text(new JTextField(), "Folio De Empleado", 10, "Int");
	JButton btnFiltroEmpleado = new JButton(new ImageIcon("imagen/Filter-List-icon16.png"));
	
	JButton btncortes_Limpio = new JButton(new ImageIcon("imagen/hoja-de-calculo-excel-icono-5223-16.png"));
	JButton btnPrestamos_Por_Establecimiento =new JButton(new ImageIcon("imagen/plan-icono-5073-16.png"));
	
	JLabel lblLinea = new JLabel();
	JDateChooser fechaIn = new JDateChooser();
	JDateChooser fechaFin = new JDateChooser();
	JButton btnAbonos_Cortes = new JButton(new ImageIcon("imagen/plan-icono-5073-16.png"));
	
	Border blackline, etched, raisedbevel, loweredbevel, empty;
	TitledBorder title4; 
	
	public Cat_Reportes_De_Cortes_De_Lista_De_Raya_Actual(String folio_empleado) {
		
		Constructor();
		
		txtFolioEmpleado.setText(folio_empleado);
		
			try {
				Date fechaI = new SimpleDateFormat("dd/MM/yyyy").parse("1/01/2015");
				fechaIn.setDate(fechaI);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			fechaFin.setDate(cargar_fecha_Sugerida(0));
		
	}
	
	public Cat_Reportes_De_Cortes_De_Lista_De_Raya_Actual() {
		Constructor();
	}
	

	public void Constructor(){
		
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/dinero-icono-8797-48.jpg"));
		blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
		panel.setBorder(BorderFactory.createTitledBorder(blackline,"Seleccion Del Reporte de Diferencia de Cortes de Lista de Raya Actual"));
		this.setTitle("Reportes de Abonos y Saldos A Cortes");
		
		lblLinea.setBorder(BorderFactory.createTitledBorder(blackline,"Reporte De Abonos De Cortes"));
		
		btncortes_Limpio.setText(	"<html> <FONT FACE="+"arial"+" SIZE=3 COLOR=BLACk>" +
				"		<p>Impresion De Reporte De Cortes</p>" +
				"		<CENTER><p>Para Exportar a Excel</p></CENTER></FONT>" +
				"</html>"); 
		
		btnPrestamos_Por_Establecimiento.setText(	"<html> <FONT FACE="+"arial"+" SIZE=3 COLOR=BLACk>" +
				"		<p>Impresion De Reporte De Abonos y Saldo </p>" +
				"		<CENTER><p>De Lista de Raya Actual Por Establecimiento</p></CENTER></FONT>" +
				"</html>"); 
		
		btnAbonos_Cortes.setText(	"<html> <FONT FACE="+"arial"+" SIZE=3 COLOR=BLACk>" +
				"		<p>Impresion De Reporte De Abonos </p>" +
				"		<CENTER><p>De Cortes Por Fecha</p></CENTER></FONT>" +
				"</html>"); 
		
		int x= 35,y=30,ancho=300;
		
		
		panel.add(btnPrestamos_Por_Establecimiento).setBounds(x,y,ancho,40);
		
		panel.add(lblLinea			).setBounds(x,y+=65,ancho,130);
		panel.add(lblFolioEmpleado).setBounds(x+10,y+=20,80,20);
		panel.add(txtFolioEmpleado).setBounds(x+90,y,100,20);
		panel.add(btnFiltroEmpleado).setBounds(x+190,y,30,20);
		panel.add(new JLabel("De:")	).setBounds(x+10,y+=30,100,20);
		panel.add(fechaIn			).setBounds(x+30,y,100,20);
		panel.add(new JLabel("A:")	).setBounds(x*4+67,y,100,20);
		panel.add(fechaFin			).setBounds(x*5+47,y,100,20);
		panel.add(btnAbonos_Cortes	).setBounds(x+10,y+=30,ancho-20,40);
		
		Date date1=null;
		try {
			date1 = new SimpleDateFormat("dd/MM/yyyy").parse("01/05/2015 00:00:01");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		fechaIn.setDate(date1);
		fechaIn.setEnabled(false);
		fechaFin.setDate(cargar_fecha_Sugerida(0));
		
		
		btnPrestamos_Por_Establecimiento.addActionListener(Reporte_Cortes_Lista_de_Raya_Actual_Por_Establecimiento);
		btnAbonos_Cortes.addActionListener(Reporte_Abonos_Cortes);
		btncortes_Limpio.addActionListener(Reporte_Cortes_Lista_de_Raya_Actual_limpio);
		
		btnFiltroEmpleado.addActionListener(opFiltro);
		
		txtFolioEmpleado.setHorizontalAlignment(4);

		cont.add(panel);
		this.setSize(375,275);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
	}
	
	public Date cargar_fecha_Sugerida(Integer dias){
		Date date1 = null;
				  try {
					date1 = new SimpleDateFormat("dd/MM/yyyy").parse(new BuscarSQL().fecha(dias));
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		return date1;
	};
	
	
	
	String basedatos="2.26";
	String vista_previa_reporte="no";
	int vista_previa_de_ventana=0;
	String comando="";
	String reporte = "";
	
	
	ActionListener Reporte_Cortes_Lista_de_Raya_Actual_Por_Establecimiento = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			 reporte = "Obj_Reporte_De_Cortes_De_Lista_De_Raya_Actual.jrxml";
			 
			 String folioEmpleado = txtFolioEmpleado.getText().equals("")?"0":txtFolioEmpleado.getText();
			 comando = "exec sp_Reporte_De_Cortes_De_Lista_De_Raya_Actual_Para_Exportar 'status',"+folioEmpleado;
			 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
		}
	};
	
	ActionListener Reporte_Abonos_Cortes = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			String fechaInicio = (fechaIn.getDate()+"").equals("null")?"null":new SimpleDateFormat("dd/MM/yyyy").format(fechaIn.getDate());
			String fechaFinal =  (fechaFin.getDate()+"").equals("null")?"null":new SimpleDateFormat("dd/MM/yyyy").format(fechaFin.getDate());
			
			if(fechaIn.getDate()==null || fechaFin.getDate()==null){
				JOptionPane.showMessageDialog(null, "Alguna de las fechas se encuentra vacia", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
				return;
			}else{
					if(fechaIn.getDate().before(fechaFin.getDate())){
						reporte = "Obj_Reporte_De_Abonos_Y_Diferencia_De_Cortes.jrxml"; // Obj_Abono_De_Cortes.jrxml
						
						 String folioEmpleado = txtFolioEmpleado.getText().equals("")?"0":txtFolioEmpleado.getText();
						comando = "exec sp_Reporte_De_Abono_De_Cortes_Por_Fecha "+folioEmpleado+",'"+fechaInicio+" 00:00:00','"+fechaFinal+" 23:59:00'";
						new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
					}else{
						JOptionPane.showMessageDialog(null, "Verifique que las fechas no esten invertidas", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
						return;
					}
			}
		}
	};
	
	
	ActionListener Reporte_Cortes_Lista_de_Raya_Actual_limpio = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			 reporte = "Obj_Reporte_De_Cortes_De_Lista_De_Raya_Actual_Para_Exportar.jrxml";
			 comando = "exec sp_Reporte_De_Cortes_De_Lista_De_Raya_Actual_Para_Exportar 'NombreCompleto '";
			 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
		}
	};
	
	ActionListener opFiltro = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			new Cat_Filtro_Reporte_De_Cortes_De_Lista_De_Raya_Actual().setVisible(true);
		}
	};
	
	public class Cat_Filtro_Reporte_De_Cortes_De_Lista_De_Raya_Actual extends JDialog {
		
		Container contF = getContentPane();
		JLayeredPane panelF = new JLayeredPane();
		
		JTextField txtFolio = new Componentes().text(new JTextField(), "Folio Empleado", 10, "Int");
		JTextField txtEmpledo = new Componentes().text(new JCTextField(), "Folio Empleado", 100, "String");
		
		public Object[][] get_tabla(){
			return new BuscarTablasModel().tabla_model_empleados_abonos_y_diferencia_de_cortes();
		}
	    private DefaultTableModel modelo = new DefaultTableModel(get_tabla(),
	            new String[]{"Folio", "Nombre Completo", "Establecimiento", "Puesto" }
				){
		     @SuppressWarnings("rawtypes")
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
	        	 	case 0 : return false; 
	        	 	case 1 : return false; 
	        	 	case 2 : return false; 
	        	 	case 3 : return false; 
	        	 }
	 			return false;
	 		}
		};

		public JTable tabla = new JTable(modelo);
		public JScrollPane scroll = new JScrollPane(tabla);
		
		public Cat_Filtro_Reporte_De_Cortes_De_Lista_De_Raya_Actual(){
			this.setModal(true);
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/dinero-icono-8797-48.jpg"));
			blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
			panelF.setBorder(BorderFactory.createTitledBorder(blackline,"Filtro De Empleados"));
			this.setTitle("Seleccione Un Empleado");
			
			int x=15,y=20,ancho=50;
			
			panelF.add(txtFolio).setBounds(x,y,ancho,20);
			panelF.add(txtEmpledo).setBounds(x+ancho,y,ancho*5+10,20);
			panelF.add(scroll).setBounds(x,y+=20,ancho*12,200);
			
			llamar_render();

//          asigna el foco al JTextField del nombre deseado al arrancar la ventana
            this.addWindowListener(new WindowAdapter() {
                    public void windowOpened( WindowEvent e ){
                    	txtEmpledo.requestFocus();
                 }
            });
			
            agregar(tabla);
            
            txtFolio.addKeyListener(opFiltroDinamicoEmpleado);
            txtEmpledo.addKeyListener(opFiltroDinamicoEmpleado);
			
			contF.add(panelF);
			this.setSize(640,280);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		}
		
		KeyListener opFiltroDinamicoEmpleado = new KeyListener(){
			public void keyReleased(KeyEvent arg0) {
				
				if(arg0.getSource().getClass().getSimpleName().equals("JTextField")){
					txtEmpledo.setText("");
					new Obj_Filtro_Dinamico(tabla,"Nombre Completo", "","","", "", "", "", "");
					new Obj_Filtro_Dinamico(tabla,"Folio", txtFolio.getText().toUpperCase(),"","", "", "", "", "");
				}else{
					txtFolio.setText("");
					new Obj_Filtro_Dinamico(tabla,"Folio", "","","", "", "", "", "");
					new Obj_Filtro_Dinamico(tabla,"Nombre Completo", txtEmpledo.getText().toUpperCase(),"","", "", "", "", "");
				}
				
			}
			public void keyTyped(KeyEvent arg0) {}
			public void keyPressed(KeyEvent arg0) {}		
		};
		
		private void agregar(final JTable tbl) {
	        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
		        public void mouseClicked(MouseEvent e) {
		        	
		        	if(e.getClickCount() == 2){
		    			int fila = tbl.getSelectedRow();
		    			String folio =  tbl.getValueAt(fila, 0).toString().trim();
		    			dispose();
		    			txtFolioEmpleado.setText(folio);
		    			
		    			try {
		    				Date fechaI = new SimpleDateFormat("dd/MM/yyyy").parse("1/01/2015");
		    				fechaIn.setDate(fechaI);
		    			} catch (ParseException e2) {
		    				// TODO Auto-generated catch block
		    				e2.printStackTrace();
		    			}
		    			
		    			fechaFin.setDate(cargar_fecha_Sugerida(0));
		        	}
		        }
	        });
	    }
		
		public void llamar_render(){
			
			this.tabla.getTableHeader().setReorderingAllowed(false) ;

			this.tabla.getColumnModel().getColumn(0).setMaxWidth(50);
			this.tabla.getColumnModel().getColumn(0).setMinWidth(50);
			this.tabla.getColumnModel().getColumn(1).setMaxWidth(260);
			this.tabla.getColumnModel().getColumn(1).setMinWidth(260);
			this.tabla.getColumnModel().getColumn(2).setMaxWidth(120);
			this.tabla.getColumnModel().getColumn(2).setMinWidth(120);
			this.tabla.getColumnModel().getColumn(3).setMaxWidth(160);
			this.tabla.getColumnModel().getColumn(3).setMinWidth(160);
			
			tabla.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","derecha","Arial","negrita",12));
			tabla.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",12));
			tabla.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",12));
			tabla.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",12));
		}
		
	}
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Reportes_De_Cortes_De_Lista_De_Raya_Actual().setVisible(true);
		}catch(Exception e){	}
	}


}
