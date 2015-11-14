package Cat_Contabilidad;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import Conexiones_SQL.Connexion;
import Conexiones_SQL.Generacion_Reportes;
import Obj_Contabilidad.Obj_Corte_De_Orden_De_Pago;
import Obj_Principal.Componentes;
import Obj_Principal.JCTextField;
import Obj_Principal.Obj_Filtro_Dinamico_Plus;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Corte_De_Ordenes_De_Pago  extends JFrame{
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
    
    private DefaultTableModel modelo = new DefaultTableModel(null, new String[]{"Folio", "Fecha", "Importe", "Beneficiario", "Concepto", "Detalle", "Establecimiento", "Usuario Elaboro","" }){
	     @SuppressWarnings("rawtypes")
		Class[] types = new Class[]{
	    	java.lang.Object.class,
	    	java.lang.Object.class, 
	    	java.lang.Object.class, 
	    	java.lang.Object.class,
	    	java.lang.Object.class,
	    	java.lang.Object.class,
	    	java.lang.Object.class,
	    	java.lang.Object.class,
	    	java.lang.Boolean.class
         };
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Class getColumnClass(int columnIndex) {   return types[columnIndex]; }
         public boolean isCellEditable(int fila, int columna){
        	 switch(columna){
        	 	case 0 : return false; 
        	 	case 1 : return false; 
        	 	case 2 : return false; 
        	 	case 3 : return false; 
        	 	case 4 : return false;
        	 	case 5 : return false;
        	 	case 6 : return false;
        	 	case 7 : return false;
        	 	case 8 :return  true;
        	 }
 			return false;
 		}
	};
	
	public JTable tabla = new JTable(modelo);
	private JScrollPane scroll_tabla = new JScrollPane(tabla,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public TableRowSorter trsfiltro = new TableRowSorter(modelo); 
	JTextField txtFiltro= new Componentes().text(new JCTextField(), ">>>Teclea Aqui Para Realizar La Busqueda En La Tabla<<<", 300, "String");
	JTextField txtfoliocorte =new Componentes().text(new JTextField(), "Folio Del Corte De Ordenes de Pago", 10, "Integer");
	JTextField txtfolio_corte_consulta= new Componentes().text(new JCTextField(), "Folio Del Corte>>y Click>>", 300, "Int");
	
	JButton btnGuardar = new JButton("Guardar",new ImageIcon("imagen/Guardar.png"));
	JButton btnReporte = new JButton("Reporte",new ImageIcon("imagen/Print.png"));

	public Cat_Corte_De_Ordenes_De_Pago(){
		this.setSize(1024, 768);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/ok-lista-de-verificacion-icono-7906-64.png"));
		this.setTitle("Corte De Ordenes De Pago En Efectivo");
		panel.setBorder(BorderFactory.createTitledBorder("Selecciona Las Ordenes De Pago Que Quieres Incluir En El Corte"));
		this.panel.add(txtFiltro).setBounds                    ( 10 ,20 ,658 ,20 );
		panel.add(new JLabel("Folio Corte Ordenes De Pago:")).setBounds(700 ,20 ,190 ,20 );
		this.panel.add(txtfoliocorte).setBounds                (845 ,20 ,45  ,20 );
		this.panel.add(btnGuardar).setBounds                   (900 ,20 ,95  ,20 );
		this.panel.add(scroll_tabla).setBounds                 ( 10 ,40 ,985 ,650);
		this.panel.add(txtfolio_corte_consulta).setBounds      (750 ,700,145  ,20);
		this.panel.add(btnReporte).setBounds                   (900 ,700,95  ,20);
		this.cont.add(panel);
		this.init_tabla();
		this.txtfoliocorte.setEditable(false);
		txtfoliocorte.setText( busqueda_proximo_folio_Corte_Orden_Pago()+"");
		txtFiltro.addKeyListener(opFiltroGeneral);
		btnGuardar.addActionListener(op_guardar);
		btnReporte.addActionListener(opReporte_De_Corte_De_Ordenes_De_Compra);
	}

	@SuppressWarnings("unchecked")
	public void init_tabla(){
		this.tabla.getTableHeader().setReorderingAllowed(false) ;
		this.tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		tabla.getColumnModel().getColumn(0).setCellRenderer(new tablaRenderer("texto","derecha","Arial","negrita",12));
		tabla.getColumnModel().getColumn(1).setCellRenderer(new tablaRenderer("texto","derecha","Arial","negrita",12));
		tabla.getColumnModel().getColumn(2).setCellRenderer(new tablaRenderer("texto","derecha","Arial","negrita",12));
		tabla.getColumnModel().getColumn(3).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",9));
		tabla.getColumnModel().getColumn(4).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",9));
		tabla.getColumnModel().getColumn(5).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",9));
		tabla.getColumnModel().getColumn(6).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",9));
		tabla.getColumnModel().getColumn(7).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",9));
		tabla.getColumnModel().getColumn(8).setCellRenderer(new tablaRenderer("CHB","centro","Arial","negrita",12));
		
		this.tabla.getColumnModel().getColumn(0).setMaxWidth(40);
		this.tabla.getColumnModel().getColumn(0).setMinWidth(40);
		this.tabla.getColumnModel().getColumn(1).setMinWidth(120);
		this.tabla.getColumnModel().getColumn(1).setMaxWidth(200);
		this.tabla.getColumnModel().getColumn(2).setMinWidth(60);
		this.tabla.getColumnModel().getColumn(2).setMaxWidth(60);
		this.tabla.getColumnModel().getColumn(3).setMinWidth(220);
		this.tabla.getColumnModel().getColumn(3).setMaxWidth(500);
		this.tabla.getColumnModel().getColumn(4).setMinWidth(220);
		this.tabla.getColumnModel().getColumn(4).setMaxWidth(500);
		this.tabla.getColumnModel().getColumn(5).setMinWidth(60);
		this.tabla.getColumnModel().getColumn(5).setMaxWidth(1000);
		this.tabla.getColumnModel().getColumn(6).setMinWidth(100);
		this.tabla.getColumnModel().getColumn(6).setMaxWidth(150);
		this.tabla.getColumnModel().getColumn(7).setMinWidth(100);
		this.tabla.getColumnModel().getColumn(7).setMaxWidth(500);
		this.tabla.getColumnModel().getColumn(8).setMaxWidth(20);
		this.tabla.getColumnModel().getColumn(8).setMinWidth(20);
		this.tabla.setRowSorter(trsfiltro);  
		refrestabla();
	}
	
	private void refrestabla(){
		modelo.setRowCount(0);
		Statement s;
		ResultSet rs;
		try {
			Connexion con = new Connexion();
			s = con.conexion().createStatement();
			rs = s.executeQuery("exec sp_select_ordenes_de_pago_pendientes_de_corte");
			while (rs.next())
			{  String [] fila = new String[9];
			   fila[0] = rs.getString(1).trim();
			   fila[1] = rs.getString(2).trim();
			   fila[2] = rs.getString(3).trim(); 
			   fila[3] = rs.getString(4).trim(); 
			   fila[4] = rs.getString(5).trim(); 
			   fila[5] = rs.getString(6).trim(); 
			   fila[6] = rs.getString(7).trim(); 
			   fila[7] = rs.getString(8).trim(); 
			   fila[8] = "true";
			   modelo.addRow(fila); 
			}	
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en Cat_Etapas en la funcion refrestabla  SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
	}


	ActionListener op_guardar = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			int[] columnas = {0,1,2};
			new Obj_Filtro_Dinamico_Plus(tabla,"", columnas);
			Obj_Corte_De_Orden_De_Pago corte_obj = new Obj_Corte_De_Orden_De_Pago();
			if(tabla.isEditing()){
				tabla.getCellEditor().stopCellEditing();
			}
			if(corte_obj.guardar_corte_de_ordenes_de_pago(tabla_guardar(),txtfoliocorte.getText().toString().trim())){
					JOptionPane.showMessageDialog(null, "Se Guardo El Corte De Ordenes De Pago Correctamente","Aviso",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("imagen/aplicara-el-dialogo-icono-6256-32.png"));
					refrestabla();
					txtfoliocorte.setText( busqueda_proximo_folio_Corte_Orden_Pago()+"");
					btnReporte.doClick();
					return;
				 }else{
					 JOptionPane.showMessageDialog(null, "Ocurrió un Error al Intentar Guardar El Corte","Avisa Al Administrador Del Sistema",JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
				 	return;
				}
		}
	};
	 
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String[] tabla_guardar(){
		Vector vector = new Vector();
		for(int i=0; i<tabla.getRowCount(); i++){
			 if(Boolean.valueOf(tabla.getValueAt(i,8).toString().trim())){
				  vector.add(modelo.getValueAt(i,0).toString().trim());
		     }
		}
		String[] matriz = new String[vector.size()];
		int j =0;
		while(j<vector.size()){
			matriz[j] = vector.get(j).toString();
			j++;
		}
		return matriz;
	}
	
	int foliosiguiente=0;
	public int  busqueda_proximo_folio_Corte_Orden_Pago() {
		Connexion con = new Connexion();
		String query = "select folio+1 from tb_folios where folio_transaccion=15";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
		    ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				 foliosiguiente =(rs.getInt(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error");
			JOptionPane.showMessageDialog(null, "Error en la funcion busqueda_proximo_folio_Corte_Orden_Pago()"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			return foliosiguiente ;
		}
		finally{
			 if (stmt != null) { try {
				stmt.close();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Error en la funcion busqueda_proximo_folio_Corte_Orden_Pago()"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
				e.printStackTrace();
			} }
		}
		return foliosiguiente;
	 };

	 
	   ActionListener opReporte_De_Corte_De_Ordenes_De_Compra = new ActionListener(){
		   public void actionPerformed(ActionEvent arg0) {
			    Integer folio=(Integer.valueOf(txtfoliocorte.getText().toString().trim())-1);
			   if(!txtfolio_corte_consulta.getText().toString().trim().equals("")){
				   folio=Integer.valueOf(txtfolio_corte_consulta.getText().toString().trim());
			   }
				String basedatos="2.26";
				String vista_previa_reporte="no";
				int vista_previa_de_ventana=0;
				String reporte = "Obj_Reporte_De_Corte_Caja_Chica.jrxml";
			    String comando = "exec sp_Reporte_De_Corte_De_Ordenes_de_Pago "+folio;
			 new Generacion_Reportes().Reporte(reporte, comando, basedatos, vista_previa_reporte,vista_previa_de_ventana);
		   }
	};
	
	KeyListener opFiltroGeneral = new KeyListener(){
		public void keyReleased(KeyEvent arg0) {
			int[] columnas = {0,1,2,3,4,5,6,7};
			new Obj_Filtro_Dinamico_Plus(tabla, txtFiltro.getText().toUpperCase(), columnas);
		}
		public void keyPressed(KeyEvent arg0) {}
		@Override
		public void keyTyped(KeyEvent arg0) {
		}		
	};
	
	public static void main(String [] arg){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Corte_De_Ordenes_De_Pago().setVisible(true);
		}catch(Exception e){	}
	}

}