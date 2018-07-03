package Cat_Evaluaciones;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Conexiones_SQL.ActualizarSQL;
import Conexiones_SQL.Connexion;
import Conexiones_SQL.Generacion_Reportes;
import Obj_Principal.Componentes;
import Obj_Principal.JCButton;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_tabla;

@SuppressWarnings("serial")
public class Cat_Autorizar_DPR extends JDialog{
	Container contf = getContentPane();
	JLayeredPane panelf = new JLayeredPane();
	Connexion con = new Connexion();
	
	JTextField txtFolio = new Componentes().text(new JTextField(), "Folio", 5, "Int");
	JTextField txtPuesto = new Componentes().text(new JTextField(), "Puesto", 100, "String");
	
	JCButton btnReporte = new JCButton("Reporte", "", "Azul");
	JCButton btnGuardar = new JCButton("Autorizar", "", "Azul");
	JCButton btnNegar = new JCButton("Negar", "", "Azul");
	
	Obj_tabla ObjTab =new Obj_tabla();
	int columnasp = 2,checkbox=-1;
	public void init_tablafp(){
    	this.tablafp.getColumnModel().getColumn(0).setMinWidth(55);
    	this.tablafp.getColumnModel().getColumn(1).setMinWidth(375);
    	
    	String comandof="exec dpr_revision 'R'";
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
	
	public Cat_Autorizar_DPR(){
		this.setSize(500,500);
		trsfiltro = new TableRowSorter(modelof); 
		tablafp.setRowSorter(trsfiltro);
		this.panelf.add(txtBuscarfp).setBounds      (10 ,20 ,470 , 20 );
		this.panelf.add(scroll_tablafp).setBounds   (10 ,40 ,470 ,255 );
		
		this.panelf.add(new JLabel("Puesto: ")).setBounds   (10 ,295 ,50 ,20 );
		this.panelf.add(txtFolio).setBounds   (60 ,295 ,50 ,20 );
		this.panelf.add(txtPuesto).setBounds   (110 ,295 ,370 ,20 );
		this.panelf.add(btnReporte).setBounds   (110 ,320 ,110 ,20 );
		this.panelf.add(btnGuardar).setBounds   (225 ,320 ,110 ,20 );
		this.panelf.add(btnNegar).setBounds   (340 ,320 ,110 ,20 );
		
		this.init_tablafp();
		
		txtFolio.setEditable(false);
		txtPuesto.setEditable(false);
		
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
		this.setTitle("Filtro De Puestos (Autorización DPR)");
		
		contf.add(panelf);
	}
	
	private void agregar(final JTable tbl) {
		tbl.addMouseListener(new MouseListener() {
			public void mouseClicked(java.awt.event.MouseEvent e) {
				int fila = tbl.getSelectedRow();
	    		txtFolio.setText(tbl.getValueAt(fila, 0)+"");
	    		txtPuesto.setText(tbl.getValueAt(fila, 1)+"");
			}
			public void mouseEntered(java.awt.event.MouseEvent e) {			}
			public void mouseExited(java.awt.event.MouseEvent e) {			}
			public void mousePressed(java.awt.event.MouseEvent e) {			}
			public void mouseReleased(java.awt.event.MouseEvent e) {		}
		});
    }
	
	ActionListener opReporte = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(!txtFolio.getText().trim().equals("")){
				String basedatos="2.26";
				String vista_previa_reporte="si";
				int vista_previa_de_ventana=1;
				
				String comando="exec dpr_buscar "+txtFolio.getText().trim()+"";
				String reporte = "Obj_Libro_DPR_toc.jrxml";
				 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
			}else{
				JOptionPane.showMessageDialog(null, "Es Necesario Seleccionar El Puesto Que Desea Revisar", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				return;
			}
		}
	};
	
	ActionListener opGuardar = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(!txtFolio.getText().equals("")){
				
				if(new ActualizarSQL().Autorizar_DPR(Integer.valueOf(txtFolio.getText().trim()), "A")){//V= Elaborado  R= Revisado  A= Autorizaado  N= Negar
					//guardado correctamente
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
	
	ActionListener opNegar = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(!txtFolio.getText().equals("")){
				
				if(new ActualizarSQL().Autorizar_DPR(Integer.valueOf(txtFolio.getText().trim()), "N")){//V= Elaborado  R= Revisado  A= Autorizaado  N= Negar
					//guardado correctamente
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
			new Cat_Autorizar_DPR().setVisible(true);
		}catch(Exception e){	}
	}
}
	
