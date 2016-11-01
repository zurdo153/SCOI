package Obj_Principal;

import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;

import Conexiones_SQL.Connexion;
import Obj_Renders.tablaRenderer;


public class Obj_tabla {
	
	public void Obj_Refrescar(JTable tabla,DefaultTableModel  modelo,int columnas,String comando,String BasdeDatos, String pintar, Integer checkbox){
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
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public  String[][] tabla_guardar(JTable tabla,DefaultTableModel  modelo,int columnas){ 
			Vector vector = new Vector();
			for(int i=0; i<tabla.getRowCount(); i++){
				for(int c=0; c<columnas; c++){
						  vector.add(modelo.getValueAt(i,c).toString().trim());
						 System.out.println(modelo.getValueAt(i,c).toString().trim());
				}		  
			}
				String[][] matriz = new String[vector.size()/columnas][columnas];
				 int i=0,j =0,columnafor=0;
				while(i<vector.size()){
					columnafor=0;
				      for(int f =0;  f<columnas;  f++,columnafor++,i++  ){	
				  matriz[j][columnafor] = vector.get(i).toString();
				  }
				  j++;
			}
			return matriz;
		}
		
		public boolean validacelda(JTable tabla,DefaultTableModel  modelo,String tipo, int fila,int columna){
			   String valorcelda= modelo.getValueAt(fila,columna).toString().trim();
			   String Aviso="";
				    if(tipo.equals("decimal")){						
				    	 Aviso="Numeros";
					}
					if(tipo.equals("entero")){						
						Aviso="Numeros Enteros";
					}
					
				try {
					if(valorcelda.equals("")){
			    		return true;
					}else{
						
						if(tipo.equals("decimal")){						
						 Double.parseDouble(valorcelda);
						}
						
						if(tipo.equals("entero")){						
							Integer.parseInt(valorcelda);
						}
						
			    		return true;
					}
				} catch (NumberFormatException nfe){
					tabla.getSelectionModel().setSelectionInterval(fila, fila);
					JOptionPane.showMessageDialog(null, "La Fila  "+(fila+1)+"  En La Columna Cantidad Solo Acepta "+Aviso,"Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
					modelo.setValueAt(0, fila, columna);
					tabla.editCellAt(fila, columna);
					Component accion=tabla.getEditorComponent();
					accion.requestFocus();
					return false;
				}
		 	}
		
		@SuppressWarnings("deprecation")
		public String RecorridoFocotabla(JTable tabla,int fila,int columna, String parametrosacarfoco){
			 String sacarFocoDeTabla = "";
			 sacarFocoDeTabla=parametrosacarfoco;
			
			if(sacarFocoDeTabla.equals("no")){
				sacarFocoDeTabla = "no";
				fila=fila-1;
			}else{
				int cantidadDeFilas = tabla.getRowCount();
				if(fila == cantidadDeFilas-1){
					if(columna==3){
						sacarFocoDeTabla="si";
							}
				}else{
					sacarFocoDeTabla = "no";
					fila=fila+1;
				}
			}
			tabla.getSelectionModel().setSelectionInterval(fila, fila);
			tabla.editCellAt(fila, columna);
			Component accion=tabla.getEditorComponent();
			
			final JTextComponent jtc = (JTextComponent)accion;
			  jtc.requestFocus();
			  jtc.selectAll();	
			  scrollposicion(tabla,fila,columna);
			
			if(sacarFocoDeTabla.equals("si")){
				tabla.lostFocus(null, null);
				tabla.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
				tabla.getSelectionModel().clearSelection();
			}
			return sacarFocoDeTabla;
	    };
	
		public static void scrollposicion(JTable table, int fila, int columna) {
	        if (!(table.getParent() instanceof JViewport)) {
	            return;
	        }
	        JViewport viewport = (JViewport)table.getParent();
	        Rectangle rect = table.getCellRect(fila, columna, true);
	        Point pt = viewport.getViewPosition();
	        rect.setLocation(rect.x-pt.x, rect.y-pt.y);
	        table.scrollRectToVisible(rect);
	        // Scroll the area into view
	        viewport.scrollRectToVisible(rect);
	}
	

}
