package Cat_Lista_de_Raya;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import Cat_Reportes.Cat_Reportes_De_Totales_De_Cheques_De_Lista_De_Raya;
import Conexiones_SQL.Connexion;
import Obj_Lista_de_Raya.Obj_Totales_De_Cheque;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Totales_De_Cheque extends Cat_Root {
	
	public JButton btn_imprimir = new JButton(new ImageIcon("Iconos/print_icon&16.png"));
	
	public int folio = new Obj_Totales_De_Cheque().MaxListaRaya();
	
	DefaultTableModel model = new DefaultTableModel(null, new String[]{"Establecimiento","Nomina", "Pago En Linea", "T.Cheque Nomina", "Lista Raya", "Diferencia"}){
		@SuppressWarnings("rawtypes")
		Class[] types = new Class[]{
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
	    	java.lang.String.class,
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
        	 	case 2 : return false; 
        	 	case 3 : return false; 
        	 	case 4 : return false;
        	 	case 5 : return false;
        	 } 				
 			return false;
 		}
	};
	
	JTable tabla = new JTable(model);
	JScrollPane scroll = new JScrollPane(tabla);
	
	public Cat_Totales_De_Cheque(){
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/cat_nomina_icon&16.png"));
		this.setTitle("Totales de Cheque");
		
		this.txtFolio.setVisible(false);
		this.txtNombre_Completo.setVisible(false);
		this.btn_refrescar.setVisible(false);
		
		panel.add(getPanelTabla()).setBounds(20,25,623,450);
		
		this.menu_toolbar.add(btn_imprimir);
			this.btn_imprimir.setToolTipText("Imprimir");
		
		btn_imprimir.addActionListener(opImprimir);
		this.btn_guardar.addActionListener(opGuardar);
		btn_imprimir.setEnabled(false);
		
		
		cont.add(panel);
		
		this.setSize(680,530);
		this.setResizable(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
	}
	
	private JScrollPane getPanelTabla()	{	
		
		tabla.getTableHeader().setReorderingAllowed(false) ;
		tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
	    this.tabla.getColumnModel().getColumn(0).setMinWidth(100);
	    this.tabla.getColumnModel().getColumn(0).setMaxWidth(300);
	    this.tabla.getColumnModel().getColumn(1).setMinWidth(100);
	    this.tabla.getColumnModel().getColumn(1).setMaxWidth(300);
	    this.tabla.getColumnModel().getColumn(2).setMinWidth(100);
	    this.tabla.getColumnModel().getColumn(2).setMaxWidth(300);
	    this.tabla.getColumnModel().getColumn(3).setMinWidth(100);
	    this.tabla.getColumnModel().getColumn(3).setMaxWidth(300);
	    this.tabla.getColumnModel().getColumn(4).setMinWidth(100);
	    this.tabla.getColumnModel().getColumn(4).setMaxWidth(300);
	    this.tabla.getColumnModel().getColumn(5).setMinWidth(100);
	    this.tabla.getColumnModel().getColumn(5).setMaxWidth(300);
					    
	    for(int i = 0; i<tabla.getColumnCount(); i++){
	    	if(i==0){
	    		tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",11));
	    	}else{
			    tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","derecha","Arial","negrita",11));
	    	}
		};
						refrestabla();
				 JScrollPane scrol = new JScrollPane(tabla);
			    return scrol; 
    
    }
private void refrestabla(){
	Statement s;
	ResultSet rs;
	try {
		Connexion con = new Connexion();
		s = con.conexion().createStatement();
		rs = s.executeQuery("exec sp_Reporte_De_Totales_De_Cheque_De_Lista_De_Raya_Actual");
		while (rs.next())
		{ 
		   String [] fila = new String[6];
		   fila[0] = rs.getString(2).trim();
		   fila[1] = rs.getString(3).trim();
		   fila[2] = rs.getString(4).trim(); 
		   fila[3] = rs.getString(5).trim(); 
		   fila[4] = rs.getString(6).trim(); 
		   fila[5] = rs.getString(7).trim(); 
		   model.addRow(fila); 
		}	
	} catch (SQLException e1) {
		e1.printStackTrace();
		JOptionPane.showMessageDialog(null, "Error en Cat_Totales_De_Cheque en la funcion refrestabla  SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
	}
}

	
	ActionListener opImprimir = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			dispose();
			new Cat_Revision_De_Lista_Raya().setVisible(true);
			new Cat_Reportes_De_Totales_De_Cheques_De_Lista_De_Raya().setVisible(true);
		
			}
	};
	
	ActionListener opGuardar = new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(tabla.isEditing()){
				tabla.getCellEditor().stopCellEditing();
			}

			new Guardar().setVisible(true);

			
		}
	};
	
	public class Guardar extends JDialog {
		Container cont = getContentPane();
		JLayeredPane panel = new JLayeredPane();
		JProgressBar barra = new JProgressBar();
		
		public Guardar() {
			barra.setStringPainted(true);
			Thread hilo = new Thread(new Hilo());
			hilo.start();
			panel.setBorder(BorderFactory.createTitledBorder("- ..."));
			
			panel.add(barra).setBounds(20,25,350,20);
			
			cont.add(panel);
			
			this.setUndecorated(true);
			this.setSize(400,100);
			this.setModal(true);
			this.setLocationRelativeTo(null);
			this.setResizable(false);
		
		}

		class Hilo implements Runnable {
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public void run() {
				
				int total = model.getRowCount();
				
				Vector miVector = new Vector();
				Obj_Totales_De_Cheque maximo = new Obj_Totales_De_Cheque();
				
				if(folio != maximo.returnMaximo()){
					for(int i=0; i<model.getRowCount(); i++){
						for(int j=0; j<model.getColumnCount(); j++){
							miVector.add(model.getValueAt(i,j));							
						}
						
						Obj_Totales_De_Cheque Totales_cheques = new Obj_Totales_De_Cheque();
							
						Totales_cheques.setNumero_listaraya(folio);
						Totales_cheques.setEstablecimiento(miVector.get(0).toString());
						Totales_cheques.setNomina(miVector.get(1).toString());
						Totales_cheques.setPago_linea(miVector.get(2).toString());
						Totales_cheques.setCheque_nomina(miVector.get(3).toString());
						Totales_cheques.setLista_raya(miVector.get(4).toString());
						Totales_cheques.setDiferencia(miVector.get(5).toString());
						Totales_cheques.Guardar();
							
						int porcent = (i*100)/total;
						barra.setValue(porcent+1);
						btn_imprimir.setEnabled(true);
						try {
							Thread.sleep(0);
						} catch (InterruptedException e) {
							e.printStackTrace();	
						}
							
						miVector.clear();
					}
					
					Obj_Totales_De_Cheque autorizacion = new Obj_Totales_De_Cheque();
					autorizacion.setAutorizar(true);
					if(autorizacion.actualizar()){
						dispose();
					JOptionPane.showMessageDialog(null,"Los Totales De Cheque Se Guardaron Exitosamente! \n Ahora Se Abrira La Ventana Para Impresion del Reporte  ", "Mensaje", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/aplicara-el-dialogo-icono-6256-32.png"));
					 btn_imprimir.doClick();	
					}else{
						JOptionPane.showMessageDialog(null, "Error Al Guardar ", "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
					}
				}else{
					if(JOptionPane.showConfirmDialog(null, "Los Totales de Cheque ya existen, ¿desea actualizarlos?") == 0){
						for(int i=0; i<model.getRowCount(); i++){
							for(int j=0; j<model.getColumnCount(); j++){
								miVector.add(model.getValueAt(i,j));							
							}
							
							Obj_Totales_De_Cheque Totales_cheques = new Obj_Totales_De_Cheque();
								
							Totales_cheques.setNomina(miVector.get(1).toString());
							Totales_cheques.setPago_linea(miVector.get(2).toString());
							Totales_cheques.setCheque_nomina(miVector.get(3).toString());
							Totales_cheques.setLista_raya(miVector.get(4).toString());
							Totales_cheques.setDiferencia(miVector.get(5).toString());
							Totales_cheques.Actualizar(miVector.get(0).toString(),folio);
								
							int porcent = (i*100)/total;
							barra.setValue(porcent+1);
							btn_imprimir.setEnabled(true);
							try {
								Thread.sleep(0);
							} catch (InterruptedException e) {
								e.printStackTrace();	
							}
								
							miVector.clear();
						}	
						Obj_Totales_De_Cheque autorizacion = new Obj_Totales_De_Cheque();
						autorizacion.setAutorizar(true);
						if(autorizacion.actualizar()){
							dispose();
						JOptionPane.showMessageDialog(null,"Los Totales De Cheque Se Guardaron Exitosamente! \n Ahora Se Abrira La Ventana Para Impresion del Reporte ", "Mensaje", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/aplicara-el-dialogo-icono-6256-32.png"));
						btn_imprimir.doClick();	
						}else{
							JOptionPane.showMessageDialog(null, "Error Al Actualizar ", "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);

						}
					}else{
						return;
					
					}
				}
			}
		}
	}
	public static void main(String args[]){
	try{
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		new Cat_Totales_De_Cheque().setVisible(true);
	}catch(Exception e){	}
}
	
}
