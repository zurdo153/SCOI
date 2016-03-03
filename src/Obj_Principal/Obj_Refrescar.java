package Obj_Principal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Conexiones_SQL.Connexion;
import Obj_Renders.tablaRenderer;

public class Obj_Refrescar {
	
	public Obj_Refrescar(JTable tabla,DefaultTableModel  modelo,int columnas,String comando,String BasdeDatos, String pintar, Integer checkbox){
    	tabla.getTableHeader().setReorderingAllowed(false) ;
    	tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		modelo.setRowCount(0);
		
    	for(int i = 0; i<tabla.getColumnCount(); i++){
    		tabla.getColumnModel().getColumn(i).setMaxWidth(2000);
           }
    	
		if (pintar.equals("si")||checkbox>-1){
		for(int i = 0; i<tabla.getColumnCount(); i++){
			if(checkbox>-1&&i==checkbox){
			 tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("CHB","centro","Arial","negrita",12));
			}else{
			tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRenderer("texto","izquierda","Arial","negrita",11));}
           }
		}

		Connexion con = new Connexion();
		Statement  s =null;
		
		if(BasdeDatos.equals("200")){
			 try {
				s = con.conexion_IZAGAR().createStatement();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			 try {
					s = con.conexion().createStatement();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

		if(checkbox>-1){
			try {
				ResultSet rs = s.executeQuery(comando);
				while (rs.next()){ 
				    	String [] fila = new String[columnas];
						for(int i=0;i<columnas;i++){
							if(i==checkbox){
								 fila[i] = "false";
							}else{
						         fila[i] = rs.getString(i+1).trim();
							}
						 }
				   modelo.addRow(fila); 
				}	
			} catch (SQLException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en la funcion refrescar tabla SQLException: "+e1.getMessage(), "Avisa al Administrador Del Sistema", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			}
		
	    }else{
			try {
				ResultSet rs = s.executeQuery(comando);
				while (rs.next()){ 
				    	String [] fila = new String[columnas];
						for(int i=0;i<columnas;i++){
						   fila[i] = rs.getString(i+1).trim();
						 }
				   modelo.addRow(fila); 
				}	
			} catch (SQLException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en la funcion refrescar tabla SQLException: "+e1.getMessage(), "Avisa al Administrador Del Sistema", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			}
			
			
	     }	
	   }
	

}
