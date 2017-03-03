package Cat_Lista_de_Raya;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import Conexiones_SQL.Connexion;
import Obj_Lista_de_Raya.Obj_Fue_Sodas_DH;
import Obj_Principal.JCButton;

@SuppressWarnings("serial")
public class Cat_Lista_De_Comparacion_De_Fuente_De_Sodas extends JFrame{
	
	DecimalFormat decimal = new DecimalFormat("#0.00");
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	Connexion con = new Connexion();
	DefaultTableModel modelRh = new DefaultTableModel(0,3){
		public boolean isCellEditable(int fila, int columna){
			if(columna < 0)
				return true;
			return false;
		}
	};
	JTable tablaRh = new JTable(modelRh);
	JScrollPane scrollRh = new JScrollPane(tablaRh);
	
	DefaultTableModel modelAx = new DefaultTableModel(0,3){
		public boolean isCellEditable(int fila, int columna){
			if(columna < 0)
				return true;
			return false;
		}
	};
	JTable tablaAx = new JTable(modelAx);
	JScrollPane scrollAx = new JScrollPane(tablaAx);
	
	DefaultTableModel modelTotalRH = new DefaultTableModel(0,3){
		public boolean isCellEditable(int fila, int columna){
			if(columna < 0)
				return true;
			return false;
		}
	};
	JTable tablaTotalRH = new JTable(modelTotalRH);
	JScrollPane scrollTotalRH = new JScrollPane(tablaTotalRH);
	
	DefaultTableModel modelTotalAX = new DefaultTableModel(0,3){
		public boolean isCellEditable(int fila, int columna){
			if(columna < 0)
				return true;
			return false;
		}
	};
	JTable tablaTotalAX = new JTable(modelTotalAX);
	JScrollPane scrollTotalAX = new JScrollPane(tablaTotalAX);
	
	JLabel lblTotalRH = new JLabel();
	JLabel lblTotalAX = new JLabel();
	
	JCButton btnAceptar    = new JCButton("Aceptar"    ,"Aplicar.png"    ,"Azul");
	JCButton btnActualizar = new JCButton("Actualizar" ,"Actualizar.png" ,"Azul");
	
	public Cat_Lista_De_Comparacion_De_Fuente_De_Sodas(){
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Imagen/el-simulador-es-igual-a-icono-9686-32.png"));
		this.setTitle("Comparación Fuente de Sodas Desarrollo Humano Vs Finanzas");
		this.setSize(980,750);
		this.setResizable(true);
		this.setLocationRelativeTo(null);
		
		Etiqueta();
		
		panel.add(new JLabel("Tabla Desarrollo Humanos")).setBounds(60,40,250,20);
		panel.add(lblTotalRH).setBounds(370,40,200,20);
		panel.add(scrollRh).setBounds(50,65,400,290);
		panel.add(new JLabel("Tabla de Diferencia Desarrollo Humanos")).setBounds(60,365,250,20);
		panel.add(scrollTotalRH).setBounds(50,390,400,290);

		panel.add(new JLabel("Tabla Auxiliar de Finanzas")).setBounds(530,40,250,20);
		panel.add(lblTotalAX).setBounds(810,40,200,20);
		panel.add(scrollAx).setBounds(530,65,400,290);
		panel.add(new JLabel("Tabla de Diferencia Auxiliar de Finanzas")).setBounds(530,365,250,20);
		panel.add(scrollTotalAX).setBounds(530,390,400,290);
		
		panel.add(btnActualizar).setBounds(310,5,120,20);
		panel.add(btnAceptar).setBounds(550,5,120,20);

		
		btnActualizar.addActionListener(opActualizar);
		btnAceptar.addActionListener(opAceptar);
		
		String[][] TablaRH = getMatrizRH();
		Object[] filaRH = new Object[tablaRh.getColumnCount()]; 
		for(int i=0; i<TablaRH.length; i++){
			modelRh.addRow(filaRH); 
			for(int j=0; j<3; j++){
				modelRh.setValueAt(TablaRH[i][j]+"", i,j);
			}
		}
		
		String[][] TablaAux = getMatrizAux();
		Object[] filaAux = new Object[tablaAx.getColumnCount()]; 
		for(int i=0; i<TablaAux.length; i++){
			modelAx.addRow(filaAux); 
			for(int j=0; j<3; j++){
				modelAx.setValueAt(TablaAux[i][j]+"", i,j);
			}
		}
		
		String[][] TablaDifRH = getMatrizDifRH();
		Object[] filaDifRH = new Object[tablaTotalRH.getColumnCount()]; 
		for(int i=0; i<TablaDifRH.length; i++){
			modelTotalRH.addRow(filaDifRH); 
			for(int j=0; j<3; j++){
				modelTotalRH.setValueAt(TablaDifRH[i][j]+"", i,j);
			}
		}
		
		String[][] TablaDifAX = getMatrizDifAX();
		Object[] filaDifAX = new Object[tablaTotalAX.getColumnCount()]; 
		for(int i=0; i<TablaDifAX.length; i++){
			modelTotalAX.addRow(filaDifAX); 
			for(int j=0; j<3; j++){
				modelTotalAX.setValueAt(TablaDifAX[i][j]+"", i,j);
			}
		}
		sumaRH();
		sumaAX();
		comparacion();
		
		cont.add(panel);
		agregar(tablaRh);
	}

	private void agregar(final JTable tbl) {
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
	        public void mouseClicked(MouseEvent e) {
	        	if(e.getClickCount() == 2){
	        		String[][] TablaDifRH = getMatrizDifRH();
	    			String[][] TablaDifAX = getMatrizDifAX();
	    			if(TablaDifAX.length == 0 && TablaDifRH.length == 0){
	    				JOptionPane.showMessageDialog(null, "La Lista está correcta! \n Haga click en Aceptar para terminar","Aviso",JOptionPane.INFORMATION_MESSAGE);
	    			}else{
	    				if(tablaRh.rowAtPoint(e.getPoint())+1 == tablaRh.getRowCount() ){
	    					return;
	    				}else{
	    					int fila = tablaRh.getSelectedRow();
	    					Object folio =  tablaRh.getValueAt(fila, 0);
	    					new Cat_Fue_Soda_Rh(folio+"").setVisible(true);
	    				}
	    			}    			
	        	}
	        }
        });
    }
	
	ActionListener opAceptar = new ActionListener(){
		public void actionPerformed(ActionEvent arg0){
			Actualizar();
			
			new Obj_Fue_Sodas_DH().actualizar_status_ticket();
			btnAceptar.setEnabled(false);
			JOptionPane.showMessageDialog(null, "Se Comprobaron con exito","Aviso",JOptionPane.INFORMATION_MESSAGE);
			btnActualizar.doClick();
		}
	};
	
	ActionListener opActualizar = new ActionListener(){
		public void actionPerformed(ActionEvent arg0){
			Actualizar();
			comparacion();
		}	
	};
	
	public void comparacion(){
		if(modelTotalRH.getRowCount()==0 && modelTotalAX.getRowCount()==0){
			btnAceptar.setEnabled(true);
		}else{
			btnAceptar.setEnabled(false);
		}
	}
	
	public void Actualizar(){
		int numeroRH = tablaRh.getRowCount();
		while(numeroRH > 0){
			modelRh.removeRow(0);
			numeroRH --;
		}
		
		int numeroAx = tablaAx.getRowCount();
		while(numeroAx > 0){
			modelAx.removeRow(0);
			numeroAx --;
		}
		
		int total_numeroRh = tablaTotalRH.getRowCount();
		while(total_numeroRh > 0){
			modelTotalRH.removeRow(0);
			total_numeroRh --;
		}
		
		int total_numeroAx = tablaTotalAX.getRowCount();
		while(total_numeroAx > 0){
			modelTotalAX.removeRow(0);
			total_numeroAx --;
		}
		
		String[][] TablaRH = getMatrizRH();
		Object[] filaRH = new Object[tablaRh.getColumnCount()]; 
		for(int i=0; i<TablaRH.length; i++){
			modelRh.addRow(filaRH); 
			for(int j=0; j<3; j++){
				modelRh.setValueAt(TablaRH[i][j]+"", i,j);
			}
		}
		
		String[][] TablaAux = getMatrizAux();
		Object[] filaAux = new Object[tablaAx.getColumnCount()]; 
		for(int i=0; i<TablaAux.length; i++){
			modelAx.addRow(filaAux); 
			for(int j=0; j<3; j++){
				modelAx.setValueAt(TablaAux[i][j]+"", i,j);
			}
		}
		
		String[][] TablaDifRH = getMatrizDifRH();
		Object[] filaDifRH = new Object[tablaTotalRH.getColumnCount()]; 
		for(int i=0; i<TablaDifRH.length; i++){
			modelTotalRH.addRow(filaDifRH); 
			for(int j=0; j<3; j++){
				modelTotalRH.setValueAt(TablaDifRH[i][j]+"", i,j);
			}
		}
		sumaRH();
		String[][] TablaDifAX = getMatrizDifAX();
		Object[] filaDifAX = new Object[tablaTotalAX.getColumnCount()]; 
		for(int i=0; i<TablaDifAX.length; i++){
			modelTotalAX.addRow(filaDifAX); 
			for(int j=0; j<3; j++){
				modelTotalAX.setValueAt(TablaDifAX[i][j]+"", i,j);
			}
		}
		sumaAX();
	}
	
	public void Etiqueta(){
		int fila=0;
		tablaRh.getColumnModel().getColumn(fila).setHeaderValue("Folio");
		tablaRh.getColumnModel().getColumn(fila).setMaxWidth(50);
		tablaRh.getColumnModel().getColumn(fila).setMinWidth(50);
		tablaRh.getColumnModel().getColumn(fila+=1).setHeaderValue("Nombre");
		tablaRh.getColumnModel().getColumn(fila).setMinWidth(200);
		tablaRh.getColumnModel().getColumn(fila+=1).setHeaderValue("Totales");
		tablaRh.getColumnModel().getColumn(fila).setMinWidth(70);
		tablaRh.getColumnModel().getColumn(fila).setMaxWidth(70);
		
		tablaAx.getColumnModel().getColumn(fila=0).setHeaderValue("Folio");
		tablaAx.getColumnModel().getColumn(fila).setMaxWidth(50);
		tablaAx.getColumnModel().getColumn(fila).setMinWidth(50);
		tablaAx.getColumnModel().getColumn(fila+=1).setHeaderValue("Nombre");
		tablaAx.getColumnModel().getColumn(fila).setMinWidth(200);
		tablaAx.getColumnModel().getColumn(fila+=1).setHeaderValue("Totales");
		tablaAx.getColumnModel().getColumn(fila).setMinWidth(70);
		tablaAx.getColumnModel().getColumn(fila).setMaxWidth(70);
		
		tablaTotalRH.getColumnModel().getColumn(fila=0).setHeaderValue("Folio");
		tablaTotalRH.getColumnModel().getColumn(fila).setMinWidth(50);
		tablaTotalRH.getColumnModel().getColumn(fila).setMaxWidth(50);
		tablaTotalRH.getColumnModel().getColumn(fila+=1).setHeaderValue("Nombre");
		tablaTotalRH.getColumnModel().getColumn(fila).setMinWidth(200);
		tablaTotalRH.getColumnModel().getColumn(fila+=1).setHeaderValue("Totales");
		tablaTotalRH.getColumnModel().getColumn(fila).setMinWidth(70);
		tablaTotalRH.getColumnModel().getColumn(fila).setMaxWidth(70);
		
		tablaTotalAX.getColumnModel().getColumn(fila=0).setHeaderValue("Folio");
		tablaTotalAX.getColumnModel().getColumn(fila).setMinWidth(50);
		tablaTotalAX.getColumnModel().getColumn(fila).setMaxWidth(50);
		tablaTotalAX.getColumnModel().getColumn(fila+=1).setHeaderValue("Nombre");
		tablaTotalAX.getColumnModel().getColumn(fila).setMinWidth(200);
		tablaTotalAX.getColumnModel().getColumn(fila+=1).setHeaderValue("Totales");
		tablaTotalAX.getColumnModel().getColumn(fila).setMinWidth(70);
		tablaTotalAX.getColumnModel().getColumn(fila).setMaxWidth(70);
		
		
		
	}
	
	public String[][] getMatrizRH(){
		String qry = "sp_select_fuente_de_sodas_comparativo_base 'D'";
		
		String[][] Matriz = new String[getFilas(qry)][3];
		
		Statement s;
		ResultSet rs;
		try {
			s = con.conexion().createStatement();
			rs = s.executeQuery(qry);
			int i=0;
			while(rs.next()){
				Matriz[i][0] = rs.getString(1).trim();
				Matriz[i][1] = rs.getString(2).trim();
				Matriz[i][2] = decimal.format(rs.getFloat(3))+"";

				i++;
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	    return Matriz; 
	}
	
	//finanzas F
	public String[][] getMatrizAux(){
		String qry = "sp_select_fuente_de_sodas_comparativo_base 'F'";
		
		String[][] Matriz = new String[getFilas(qry)][3];
		Statement s;
		ResultSet rs;
		try {
			s = con.conexion().createStatement();
			rs = s.executeQuery(qry);
			int i=0;
			while(rs.next()){
				Matriz[i][0] = rs.getString(1).trim();
				Matriz[i][1] = rs.getString(2).trim();
				Matriz[i][2] = decimal.format(rs.getFloat(3))+"";

				i++;
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	    return Matriz; 
	}
	
	public String[][] getMatrizDifRH(){
		String qry = "sp_select_fuente_de_sodas_comparativo_except 'D'";
		
		String[][] Matriz = new String[getFilas(qry)][3];
		Statement s;
		ResultSet rs;
		try {
			s = con.conexion().createStatement();
			rs = s.executeQuery(qry);
			int i=0;
			while(rs.next()){
				Matriz[i][0] = rs.getString(1).trim();
				Matriz[i][1] = rs.getString(2).trim();
				Matriz[i][2] = decimal.format(rs.getFloat(3))+"";

				i++;
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	    return Matriz; 
	}
	
	public String[][] getMatrizDifAX(){
		String qry = "sp_select_fuente_de_sodas_comparativo_except 'F'";
		
		String[][] Matriz = new String[getFilas(qry)][3];
		Statement s;
		ResultSet rs;
		try {
			s = con.conexion().createStatement();
			rs = s.executeQuery(qry);
			int i=0;
			while(rs.next()){
				Matriz[i][0] = rs.getString(1).trim();
				Matriz[i][1] = rs.getString(2).trim();
				Matriz[i][2] = decimal.format(rs.getFloat(3))+"";

				i++;
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	    return Matriz; 
	}
	
	public int getFilas(String qry){
		int filas=0;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(qry);
			while(rs.next()){
				filas++;
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return filas;
	}	
	
	public void sumaRH(){
		float suma = 0;
		
		for(int i=0;i<modelRh.getRowCount(); i++) {
			float datos= Float.parseFloat(modelRh.getValueAt(i,2).toString());
			suma=(suma+datos); 
		} 
		Object[] filaSuma = new Object[tablaRh.getColumnCount()]; 
		filaSuma[0]=".....";
		filaSuma[1]="Total General";
		filaSuma[2]=suma;
		
		modelRh.addRow(filaSuma);
	}
	
	public void sumaAX(){
		float suma = 0;
		@SuppressWarnings("unused")
		int cont = 0;
			for(int i=0;i<modelAx.getRowCount(); i++) {
				float datos= Float.parseFloat(modelAx.getValueAt(i,2).toString());
				suma=(suma+datos); 
				cont ++;
			} 
		Object[] filaSuma = new Object[tablaAx.getColumnCount()]; 
			filaSuma[0]=".....";
			filaSuma[1]="Total General";
			filaSuma[2]=suma;
			
			modelAx.addRow(filaSuma);
			
	}
	
	public static void main(String args[]){
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Cat_Lista_De_Comparacion_De_Fuente_De_Sodas().setVisible(true);
		}catch(Exception e){	}
	}
			
}
