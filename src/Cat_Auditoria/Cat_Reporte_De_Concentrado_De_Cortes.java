package Cat_Auditoria;

import java.awt.Container;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import com.toedter.calendar.JDateChooser;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.BuscarTablasModel;
import Conexiones_SQL.Connexion;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Principal.Componentes;
import Obj_Renders.tablaRenderer;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.swing.JRViewer;

@SuppressWarnings("serial")
public class Cat_Reporte_De_Concentrado_De_Cortes extends JDialog{
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	String[] vector = new Obj_Establecimiento().Combo_Establecimiento_Concentrado();
	@SuppressWarnings({ "rawtypes", "unchecked" })
	JComboBox cmbConcentrado = new JComboBox(vector);

	JDateChooser fchTrabajo = new Componentes().jchooser(new JDateChooser()  ,"Fecha Trabajo"  ,0);
	JButton btnGenerar = new JButton("Generar", new ImageIcon("imagen/buscar.png"));
	
	DefaultTableModel modelo_establecimiento_para_concentrado = new DefaultTableModel(new BuscarTablasModel().tabla_establecimientos_para_concentrado(), new String[]{"Establecimiento", "Grupo"}){
	     @SuppressWarnings("rawtypes")
		Class[] types = new Class[]{
	    	java.lang.String.class,
	    	java.lang.String.class
         };
	     
	     @SuppressWarnings({ "rawtypes", "unchecked" })
		public Class getColumnClass(int columnIndex) {
             return types[columnIndex];
         }
         public boolean isCellEditable(int fila, int columna){
        	 switch(columna){
	        	 	case 0 : return false; 
	        	 	case 1 : return false;
        	 	} 				
 			return false;
 		}
	};
	
	JTable tabla_establecimiento_para_concentrado = new JTable(modelo_establecimiento_para_concentrado);
	JScrollPane scroll_establecimiento_para_concentrado = new JScrollPane(tabla_establecimiento_para_concentrado,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

	@SuppressWarnings("rawtypes")
	private TableRowSorter trsfiltro;
	public Cat_Reporte_De_Concentrado_De_Cortes(){
		Contructor();
	}
	
	public Cat_Reporte_De_Concentrado_De_Cortes(String concentrado){
		Contructor();
		cmbConcentrado.setSelectedItem(concentrado.toString());
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void Contructor(){
		this.setModal(true);
		this.setSize(370,300);
		this.setResizable(true);
		this.setLocationRelativeTo(null);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Filter-List-icon32.png"));
		this.setTitle("Reporte De Concentrado De Cortes");
		panel.setBorder(BorderFactory.createTitledBorder("Concentrado De Cortes"));
		trsfiltro = new TableRowSorter(modelo_establecimiento_para_concentrado); 
		tabla_establecimiento_para_concentrado.setRowSorter(trsfiltro);
		
		panel.add(new JLabel("Concentrado: ")).setBounds(20,20,80,20);
		panel.add(cmbConcentrado).setBounds(110,20,220,20);
		
		panel.add(new JLabel("Fecha:")).setBounds(20,45,60,20);
		panel.add(fchTrabajo).setBounds(60,45,100,20);
		panel.add(btnGenerar).setBounds(230,45,100,20);
		
		panel.add(scroll_establecimiento_para_concentrado).setBounds(20,70,310,180);
		
		fchTrabajo.setDate(cargar_fecha(0));
		tablaRender();
		
		cmbConcentrado.addActionListener(opFiltro);
		btnGenerar.addActionListener(opGenerar);
		cont.add(panel);
	}
	
	public Date cargar_fecha(int dias){
		Date date1 = null;
				  try {
					date1 = new SimpleDateFormat("dd/MM/yyyy").parse(new BuscarSQL().fecha(dias));
				} catch (ParseException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				  return (date1);
	};
	
	ActionListener opFiltro = new ActionListener(){
		@SuppressWarnings("unchecked")
		public void actionPerformed(ActionEvent arg0){
			if(cmbConcentrado.getSelectedIndex() != 0){
				trsfiltro.setRowFilter(RowFilter.regexFilter(cmbConcentrado.getSelectedItem()+"", 1));
			}else{
				trsfiltro.setRowFilter(RowFilter.regexFilter("", 1));
			}
		}
	};
	
	ActionListener opGenerar = new ActionListener(){
		@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
		public void actionPerformed(ActionEvent arg0){
			if(cmbConcentrado.getSelectedIndex() == 0 || tabla_establecimiento_para_concentrado.getRowCount() == 0){
				JOptionPane.showMessageDialog(null, "Seleccionar un concentrado que contenga establecimientos clasificados", "Aviso", JOptionPane.WARNING_MESSAGE);
				return;
			}else{
				
				if(fchTrabajo.getDate()!=null){					

//--REPORTE MODAL CON PARAMETRO-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

						String fecha = new SimpleDateFormat("dd/MM/yyyy").format(fchTrabajo.getDate());	
						int folio_trabajo_de_corte = new BuscarSQL().folio_de_ultimo_trabajo_de_corte(cmbConcentrado.getSelectedItem().toString().trim(),fecha);
						String reporte = "Obj_Reporte_Para_Trabajo_De_Cortes.jrxml";
					
						 try{
								JDialog viewer = new JDialog(new JFrame(),reporte, true);
								viewer.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Report.png"));
								viewer.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds()); 
								viewer.setLocationRelativeTo(null);

								JasperReport report = JasperCompileManager.compileReport(System.getProperty("user.dir")+"\\src\\Obj_Reportes\\"+reporte);
								
								Map parametro = new HashMap();
								parametro.put("folio_trabajo", folio_trabajo_de_corte);

								// En mapa se especifican los parametros del reporte
								JasperPrint print = JasperFillManager.fillReport(report,parametro, new Connexion().conexion());

										JRViewer jrv = new JRViewer(print);
										jrv.setZoomRatio(1);//zoom default del reporte
										viewer.getContentPane().add(jrv);
										viewer.show();

							}
							catch(Exception ex){
								System.out.println(ex.getMessage());											
								JOptionPane.showMessageDialog(null, "Error Al Intentar Generar El Reporte: \n En La Clase Generacion Reportes Reporte:"+reporte+"\n Mensaje Exception: "+ex.getMessage(), "Avisa Al Administrador Del Sistema", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/configuracion-de-usuario-de-configuracion-de-la-herramienta-de-ocio-icono-7245-64.png"));
							}
//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//--TERMINA METODO DE REPORTE MODAL CON PARAMETRO-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

				}else{
					JOptionPane.showMessageDialog(null, "La Fecha Esta Vacia O Es Incorrecta", "Aviso", JOptionPane.WARNING_MESSAGE);
					return;
				}
			}
		}
	};
	
	public void tablaRender(){
		tabla_establecimiento_para_concentrado.getTableHeader().setReorderingAllowed(false) ;
		tabla_establecimiento_para_concentrado.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tabla_establecimiento_para_concentrado.getColumnModel().getColumn(0).setMaxWidth(160);
		tabla_establecimiento_para_concentrado.getColumnModel().getColumn(0).setMinWidth(160);
		tabla_establecimiento_para_concentrado.getColumnModel().getColumn(1).setMaxWidth(140);
		tabla_establecimiento_para_concentrado.getColumnModel().getColumn(1).setMinWidth(140);
		tabla_establecimiento_para_concentrado.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 	
		tabla_establecimiento_para_concentrado.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12)); 	
	}
	
	public static void main(String[] args) {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Reporte_De_Concentrado_De_Cortes().setVisible(true);
		}catch(Exception e){
			System.err.println("Error :"+ e.getMessage());
		}
	}

}



























