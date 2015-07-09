package Cat_Lista_de_Raya;

import java.awt.Color;
import java.awt.Container;
import java.awt.Toolkit;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Conexiones_SQL.Connexion;
import Obj_Renders.tablaRenderer;

@SuppressWarnings("serial")
public class Cat_Aviso_Vencimiento_De_Contrato extends JDialog{
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	 public static DefaultTableModel modelo = new DefaultTableModel(null,new String[]{"Folio", "Empleado", "Fecha Ingreso","Contrato","Dias Trab","Establecimiento","Puesto"}){
         @SuppressWarnings("rawtypes")
         Class[] types = new Class[]{
                    java.lang.Object.class,
                    java.lang.Object.class, 
                    java.lang.Object.class,  
                    java.lang.Object.class,
                    java.lang.Object.class,  
                    java.lang.Object.class,
                    java.lang.Object.class  
     };
         @SuppressWarnings({ "rawtypes", "unchecked" })
         public Class getColumnClass(int columnIndex) {
                 return types[columnIndex];
         }
     public boolean isCellEditable(int fila, int columna){
                 switch(columna){
                         case 0  : return false; 
                         case 1  : return false; 
                         case 2  : return false; 
                         case 3  : return false; 
                         case 4  : return false; 
                         case 5  : return false; 
                         case 6  : return false; 
                 }
                  return false;
          }
 };
	JTable tabla = new JTable(modelo);
	JScrollPane scroll= new JScrollPane(tabla);
	
	public Cat_Aviso_Vencimiento_De_Contrato(){
		this.setModal(true);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage("Iconos/user_icon&16.png"));
		panel.setBorder(BorderFactory.createTitledBorder("Contratos Proximos A Terminar"));
		this.setTitle("Empleados Con Contrato Proximo A Terminar");
		
		panel.add(scroll).setBounds(20,25,955,330);
		
		llamarRender();
		buscarRegistros();
		
		cont.setBackground(Color.white);	
	
		cont.add(panel);
		this.setSize(1000,400);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	private void llamarRender(){	
		
		tabla.getTableHeader().setReorderingAllowed(false) ;
		tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
	    this.tabla.getColumnModel().getColumn(0).setMinWidth(58);
	    this.tabla.getColumnModel().getColumn(0).setMaxWidth(58);
	    this.tabla.getColumnModel().getColumn(1).setMinWidth(280);
	    this.tabla.getColumnModel().getColumn(1).setMaxWidth(320);
	    this.tabla.getColumnModel().getColumn(2).setMinWidth(90);
	    this.tabla.getColumnModel().getColumn(2).setMaxWidth(90);
	    this.tabla.getColumnModel().getColumn(3).setMinWidth(80);
	    this.tabla.getColumnModel().getColumn(3).setMaxWidth(80);
	    this.tabla.getColumnModel().getColumn(4).setMinWidth(50);
	    this.tabla.getColumnModel().getColumn(4).setMaxWidth(50);
	    this.tabla.getColumnModel().getColumn(5).setMinWidth(130);
	    this.tabla.getColumnModel().getColumn(5).setMaxWidth(170);
	    
	    this.tabla.getColumnModel().getColumn(6).setMinWidth(250);
	    this.tabla.getColumnModel().getColumn(6).setMaxWidth(250);
					    
		for(int i=0; i<tabla.getColumnCount(); i++){
			this.tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","normal",12));
		}
    }
	
	private void buscarRegistros(){
		while(tabla.getRowCount()>0){ modelo.removeRow(0);}
		
		Statement s;
		ResultSet rs;
		try {
			Connexion con = new Connexion();
			s = con.conexion().createStatement();
			rs = s.executeQuery("exec sp_select_contratos_proximos_a_terminar");
			
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
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en Cat_Establecimientos en la funcion refrestabla  SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void main(String[] args) {
		new Cat_Aviso_Vencimiento_De_Contrato().setVisible(true);
	}

}
