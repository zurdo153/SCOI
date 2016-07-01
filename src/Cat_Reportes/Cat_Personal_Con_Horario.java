package Cat_Reportes;

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
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Conexiones_SQL.Connexion;
import Conexiones_SQL.Generacion_Reportes;
import Obj_Contabilidad.Obj_Indicadores;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.Componentes;
import Obj_Principal.Obj_Filtro_Dinamico_Plus;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Personal_Con_Horario extends JDialog{

	String establecimiento[] = new Obj_Establecimiento().Combo_Establecimiento();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbEstablecimiento = new JComboBox(establecimiento);
	
	String establecimiento2[] = new Obj_Establecimiento().Combo_Establecimiento();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbEstablecimiento2 = new JComboBox(establecimiento);
	
	String status[] = {"Seleccione un Estatus","Vigente","Vacaciones","Incapacidad","Baja","No Contratable","Provisional Checador"};
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbStatus = new JComboBox(status);
	
	Border blackline, etched, raisedbevel, loweredbevel, empty;
	JLabel lblPlantillaHorarioColaboradores = new JLabel();
	JLabel lblPuestosColaboradores = new JLabel();
	JLabel lblStatusColaboradores = new JLabel();
	JLabel lblPlantillaDePersonal = new JLabel();
	
	JButton btngenerar = new JButton("Plantilla De Horario",new ImageIcon("imagen/buscar.png"));
	JButton btngenerarplantilla = new JButton("Plantilla Base de Puestos",new ImageIcon("imagen/buscar.png"));
	JButton btnReporteColaboradores = new JButton("Personal Por Estatus",new ImageIcon("imagen/buscar.png"));
	JButton btngenerarplantillapersonal = new JButton("Plantilla De Personal",new ImageIcon("imagen/buscar.png"));
	public Cat_Personal_Con_Horario(){
	
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/plan-icono-5073-16.png"));
		this.setTitle("Reportes de Colaboradores.");
		this.setModal(true);
		
		Container cont = getContentPane();
		JLayeredPane panel = new JLayeredPane();
		
		blackline = BorderFactory.createLineBorder(new java.awt.Color(105,105,105));
		
		;
		this.lblPlantillaHorarioColaboradores.setBorder(BorderFactory.createTitledBorder(blackline,"Colaboradores por Horario"));
		this.lblPuestosColaboradores.setBorder(BorderFactory.createTitledBorder(blackline,"Colaboradores Base De Puestos Por Establecimiento"));
		this.lblStatusColaboradores.setBorder(BorderFactory.createTitledBorder(blackline,"Colaboradores por Estatus"));
		this.lblPlantillaDePersonal.setBorder(BorderFactory.createTitledBorder(blackline,"Plantilla De Personal Por Puestos"));
		
		panel.setBorder(BorderFactory.createTitledBorder("Seleccione su Tipo de Reporte :"));
		
		
		
		panel.add(lblPlantillaHorarioColaboradores).setBounds(13, 20, 270,95);
		panel.add(cmbEstablecimiento).setBounds(40,40,220,20);
		panel.add(btngenerar).setBounds(40,80,220,20);
		
		panel.add(lblPuestosColaboradores).setBounds(295, 20, 270,95);
		panel.add(cmbEstablecimiento2).setBounds(320,40,220,20);
		panel.add(btngenerarplantilla).setBounds(320,80,220,20);
		
		panel.add(lblStatusColaboradores).setBounds(13, 120, 270, 95);
		panel.add(cmbStatus).setBounds(40,140,220,20);
		panel.add(btnReporteColaboradores).setBounds(50, 180, 200, 20);
		
		panel.add(lblPlantillaDePersonal).setBounds(295, 120, 270,95);
		panel.add(btngenerarplantillapersonal).setBounds(320,150,220,40);
		
		
		cont.add(panel);
		
		btngenerar.addActionListener(opGenerar);
		btngenerarplantilla.addActionListener(opGenerar_Plantilla);
		btnReporteColaboradores.addActionListener(opGenerarreporte2);
		btngenerarplantillapersonal.addActionListener(opFiltroListaDeRaya);
		
		this.setSize(585, 250);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	ActionListener opFiltroListaDeRaya = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			new Cat_Filtro_Lista_de_Raya_Pasadas().setVisible(true);
		}
	};
	
	ActionListener opGenerar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String basedatos="2.26";
			String vista_previa_reporte="no";
			int vista_previa_de_ventana=0;
			String comando="exec sp_Reporte_de_Plantilla_de_Personal_con_Horario '"+cmbEstablecimiento.getSelectedItem()+"';";
			String reporte = "Obj_Reporte_De_Empleados_Plantilla_De_Horario_De_Personal_Por_Establecimiento.jrxml";
					 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
			}
	};
	
	ActionListener opGenerar_Plantilla = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String basedatos="2.26";
			String vista_previa_reporte="no";
			int vista_previa_de_ventana=0;
			String comando="exec sp_Reporte_de_Plantilla_de_Puesto_Por_Establecimiento '"+cmbEstablecimiento2.getSelectedItem()+"';";
			String reporte = "Obj_Reporte_De_Plantilla_Por_Establecimiento.jrxml";
					 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
			}
	};
	
	ActionListener opGenerarreporte2 = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			String basedatos="2.26";
			String vista_previa_reporte="no";
			int vista_previa_de_ventana=0;
			String comando="exec sp_select_reporte_empleados_activos "+cmbStatus.getSelectedIndex();
			String reporte = "Obj_Reporte_Status_de_Colaboradores.jrxml";
					 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
			}
	};
	
	public class Cat_Filtro_Lista_de_Raya_Pasadas extends JDialog {
		
		Container cont = getContentPane();
		JLayeredPane campo = new JLayeredPane();
		
		Connexion con = new Connexion();
		
		DefaultTableModel model = new DefaultTableModel(0,2){
			public boolean isCellEditable(int fila, int columna){
				if(columna < 0)
					return true;
				return false;
			}
		};
		
		JTable tabla = new JTable(model);
		
		@SuppressWarnings("rawtypes")
		private TableRowSorter trsfiltro;
		
		JTextField txtFiltro = new Componentes().text(new JTextField(), "Teclee Folio o Fecha De Lista De Raya", 20, "String");
	    
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Cat_Filtro_Lista_de_Raya_Pasadas()	{
			this.setModal(true);
			this.setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/filter_icon&16.png"));
			this.setTitle("Consulta de Listas de Raya Pasadas");

			campo.setBorder(BorderFactory.createTitledBorder("Listas de Rayas Pasadas"));
			trsfiltro = new TableRowSorter(model); 
			tabla.setRowSorter(trsfiltro);  
			
			campo.add(getPanelTabla()).setBounds(15,42,200,367);
			
			campo.add(txtFiltro).setBounds(15,20,200,20);
			
			agregar(tabla);
			
			cont.add(campo);
			
			txtFiltro.addKeyListener(opFiltro);
			
			this.setSize(240,450);
			this.setResizable(false);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			
		}
		
		private void agregar(final JTable tbl) {
	        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
		        public void mouseClicked(MouseEvent e) {
		        	if(e.getClickCount() == 2){
		        		int fila = tabla.getSelectedRow();

		        		String fecha = tabla.getValueAt(fila, 1).toString().trim()+" 00:00:00";
    					reporte(fecha);
    					dispose();
		    					
		        	}
		        }
	        });
	    }
		
		public void reporte(String fecha){
			String basedatos="2.26";
			String vista_previa_reporte="no";
			int vista_previa_de_ventana=0;
			String comando="";
			String reporte ="";
			
			Obj_Indicadores indicador = new Obj_Indicadores().buscar("Plantilla De Personal");
			comando=indicador.getProcedimiento_almacenado()+" '"+fecha+"'";
			System.out.println(comando);
			reporte =indicador.getReporte();
			
			new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);

		}
		
		KeyListener opFiltro = new KeyListener(){
			public void keyReleased(KeyEvent arg0) {
				
				int[] columnas = {1,2};
				new Obj_Filtro_Dinamico_Plus(tabla, txtFiltro.getText().toString().toLowerCase(), columnas);
			}
			public void keyTyped(KeyEvent arg0) {}	
			public void keyPressed(KeyEvent arg0) {}		
		};
		
	   	@SuppressWarnings("unused")
		private JScrollPane getPanelTabla()	{		
			
			DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
			tcr.setHorizontalAlignment(SwingConstants.CENTER);
			
			tabla.getColumnModel().getColumn(0).setHeaderValue("Número Lista");
			tabla.getColumnModel().getColumn(0).setMaxWidth(60);
			tabla.getColumnModel().getColumn(0).setMinWidth(60);
			tabla.getColumnModel().getColumn(1).setHeaderValue("Fecha Creada");
			tabla.getColumnModel().getColumn(1).setMaxWidth(120);
			tabla.getColumnModel().getColumn(1).setMinWidth(120);
			
			tabla.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","derecha","Arial","normal",12));
			tabla.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","centro","Arial","normal",12));
			
			Statement s;
			ResultSet rs;
			try {
				s = con.conexion().createStatement();
				rs = s.executeQuery("select numero_lista,fecha "
						+ " from tb_lista_raya "
						+ " where numero_lista >= 156 "
						+ " group by numero_lista,fecha "
						+ " order by numero_lista desc");
				String [] fila = new String[2];
				DecimalFormat format =  new DecimalFormat("#0.00");
				while (rs.next()) {
				   fila[0] = rs.getString(1)+"  ";
				   fila[1] = "   "+rs.getString(2);
				   
			  	   model.addRow(fila); 
				}	
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			 JScrollPane scrol = new JScrollPane(tabla);
			   
		    return scrol; 
		}
		
	}
	public static void main(String [] arg){
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Personal_Con_Horario().setVisible(true);
		}catch(Exception e){	}
	}
}
