package Cat_Evaluaciones;

import java.awt.Color;
import java.awt.Container;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Conexiones_SQL.ActualizarSQL;
import Conexiones_SQL.Connexion;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_tabla;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.swing.JRViewer;

@SuppressWarnings("serial")
public class Cat_Revision_DPR extends JDialog{
	Container contf = getContentPane();
	JLayeredPane panelf = new JLayeredPane();
	Connexion con = new Connexion();
	
	JTextField txtFolio = new Componentes().text(new JTextField(), "Folio", 5, "Int");
	JTextField txtPuesto = new Componentes().text(new JTextField(), "Puesto", 100, "String");
	
	JTextArea txaObservacion = new Componentes().textArea(new JTextArea(), "", 350);
	JScrollPane scrollObservacion = new JScrollPane(txaObservacion);
	
	JCButton btnReporte = new JCButton("Reporte", "", "Azul");
	JCButton btnGuardar = new JCButton("Autorizar", "", "Azul");
	JCButton btnNegar = new JCButton("Negar", "", "Azul");
	
	Obj_tabla ObjTab =new Obj_tabla();
	int columnasp = 2,checkbox=-1;
	public void init_tablafp(){
    	this.tablafp.getColumnModel().getColumn(0).setMinWidth(55);
    	this.tablafp.getColumnModel().getColumn(1).setMinWidth(375);
    	
    	String comandof="exec dpr_revision 'V'";
		String basedatos="26",pintar="si";
		ObjTab.Obj_Refrescar(tablafp,modelof, columnasp, comandof, basedatos,pintar,checkbox);
    }
	
	@SuppressWarnings("rawtypes")
	public Class[] base (){
		Class[] types = new Class[columnasp];
		for(int i = 0; i<columnasp; i++){types[i]= java.lang.Object.class;}
		 return types;
	}
	
	public DefaultTableModel modelof = new DefaultTableModel(null, new String[]{"Folio","Puesto"}){
		 @SuppressWarnings("rawtypes")
			Class[] types = base();
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Class getColumnClass(int columnIndex) {return types[columnIndex]; }
			public boolean isCellEditable(int fila, int columna){return false;}
	};
	
	JTable tablafp = new JTable(modelof);
	public JScrollPane scroll_tablafp = new JScrollPane(tablafp);
     @SuppressWarnings({ "rawtypes" })
     public TableRowSorter trsfiltro;
     
	JTextField txtBuscarfp  = new Componentes().textfiltro(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<", 300, "String",tablafp,columnasp);
	@SuppressWarnings({ "rawtypes", "unchecked" })
	
	public Cat_Revision_DPR(){
		this.setSize(500,500);
		trsfiltro = new TableRowSorter(modelof); 
		tablafp.setRowSorter(trsfiltro);
		this.panelf.add(txtBuscarfp).setBounds      (10 ,20 ,470 , 20 );
		this.panelf.add(scroll_tablafp).setBounds   (10 ,40 ,470 ,255 );
		
		this.panelf.add(new JLabel("Puesto: ")).setBounds   (10 ,295 ,50 ,20 );
		this.panelf.add(txtFolio).setBounds   (60 ,295 ,50 ,20 );
		this.panelf.add(txtPuesto).setBounds   (110 ,295 ,370 ,20 );
		this.panelf.add(new JLabel("Observacion: ")).setBounds   (10 ,320 ,80 ,20 );
		this.panelf.add(scrollObservacion).setBounds   (90 ,320 ,390 ,60 );		
		this.panelf.add(btnReporte).setBounds   (110 ,390 ,110 ,20 );
		this.panelf.add(btnGuardar).setBounds   (225 ,390 ,110 ,20 );
		this.panelf.add(btnNegar).setBounds   (340 ,390 ,110 ,20 );
		
		this.init_tablafp();
		
		txtFolio.setEditable(false);
		txtPuesto.setEditable(false);
		
//		String color = status?"FFFFFF":"EBEBEB";
		txaObservacion.setBackground(new Color(Integer.parseInt("FFFFFF",16)));
		
		this.agregar(tablafp);
		
		btnReporte.addActionListener(opReporte);
		btnGuardar.addActionListener(opGuardar);
		btnNegar.addActionListener(opNegar);
		
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setModal(true);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Filter-List-icon32.png"));
		this.panelf.setBorder(BorderFactory.createTitledBorder("Selecione Un Puesto Con Un Click"));
		this.setTitle("Filtro De Puestos (Revisión DPR)");
		
		contf.add(panelf);
	}
	
	private void agregar(final JTable tbl) {
		tbl.addMouseListener(new MouseListener() {
			public void mouseClicked(java.awt.event.MouseEvent e) {
				int fila = tbl.getSelectedRow();
	    		txtFolio.setText(tbl.getValueAt(fila, 0)+"");
	    		txtPuesto.setText(tbl.getValueAt(fila, 1)+"");
	    		txaObservacion.requestFocus();
			}
			public void mouseEntered(java.awt.event.MouseEvent e) {			}
			public void mouseExited(java.awt.event.MouseEvent e) {			}
			public void mousePressed(java.awt.event.MouseEvent e) {			}
			public void mouseReleased(java.awt.event.MouseEvent e) {		}
		});
    }
	
	ActionListener opReporte = new ActionListener() {
		@SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
		@Override
		public void actionPerformed(ActionEvent e) {
			if(!txtFolio.getText().trim().equals("")){
				//--REPORTE MODAL CON PARAMETRO-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
				//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

										String reporte = "Obj_Libro_DPR_toc.jrxml";
									
										 try{
												JDialog viewer = new JDialog(new JFrame(),reporte, true);
												viewer.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/Report.png"));
												viewer.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds()); 
												viewer.setLocationRelativeTo(null);

												JasperReport report = JasperCompileManager.compileReport(System.getProperty("user.dir")+"\\src\\Obj_Reportes\\"+reporte);
												
												Map parametro = new HashMap();
												parametro.put("folioPuesto", Integer.valueOf(txtFolio.getText().trim()));

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
				JOptionPane.showMessageDialog(null, "Es Necesario Seleccionar El Puesto Que Desea Revisar", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}
		}
	};
	
	ActionListener opGuardar = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(!txtFolio.getText().trim().equals("")){
				
				if(new ActualizarSQL().Autorizar_DPR(Integer.valueOf(txtFolio.getText().trim()), "R", txaObservacion.getText().toString().trim().toUpperCase())){//V= Elaborado  R= Revisado  A= Autorizaado  N= Negar
					//guardado correctamente
					init_tablafp();
					txtFolio.setText("");
					txtPuesto.setText("");
					txaObservacion.setText("");
					JOptionPane.showMessageDialog(null, "Se Actualizaron Los Datos Correctamente","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
					return;
				}else{
					//error al guardar
					JOptionPane.showMessageDialog(null, "No Se Pudo Realizar El Registro", "Error!", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}else{
				JOptionPane.showMessageDialog(null, "Es Necesario Seleccionar El Puesto Que Desea Revisar","Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}
			
		}
	};
	
	ActionListener opNegar = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(!txtFolio.getText().equals("")){
				
				if(new ActualizarSQL().Autorizar_DPR(Integer.valueOf(txtFolio.getText().trim()), "N", txaObservacion.getText().toString().trim().toUpperCase())){//V= Elaborado  R= Revisado  A= Autorizaado  N= Negar
					//guardado correctamente
					init_tablafp();
					txtFolio.setText("");
					txtPuesto.setText("");
					txaObservacion.setText("");
					JOptionPane.showMessageDialog(null, "Se Actualizaron Los Datos Correctamente","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
					return;
				}else{
					//error al guardar
					JOptionPane.showMessageDialog(null, "No Se Pudo Realizar El Registro", "Error!", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}else{
				JOptionPane.showMessageDialog(null, "Es Necesario Seleccionar El Puesto Que Desea Revisar","Aviso", JOptionPane.WARNING_MESSAGE);
				return;
			}
			
		}
	};
	
	public static void main(String [] arg){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Revision_DPR().setVisible(true);
		}catch(Exception e){	}
	}
}
	
