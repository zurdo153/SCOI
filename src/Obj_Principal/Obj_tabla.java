package Obj_Principal;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;

import Conexiones_SQL.Connexion;
import Obj_Administracion_del_Sistema.Obj_Usuario;


public class Obj_tabla {
	  Obj_Usuario usuario = new Obj_Usuario().buscar_Colores();
	 ///fuente 
	 int RFuente =usuario.getRFuente();
	 int GFuente =usuario.getGFuente();
	 int BFuente =usuario.getBFuente();
	 ///fuente Seleccionada
	 int RFuenteS =usuario.getRFuenteS();
	 int GFuenteS =usuario.getGFuenteS();
	 int BFuenteS =usuario.getBFuenteS();
	 ///fila non
	 int Rfila =usuario.getRfila();
	 int Gfila =usuario.getGfila();
	 int Bfila =usuario.getBfila();
	 //fila seleccionada
	 int RfilaS =usuario.getRfilaS();
	 int GfilaS =usuario.getGfilaS();
	 int BfilaS =usuario.getBfilaS();
	 int tamanio_fuente=usuario.getTamanio_fuente();

     private String tipo="text";
     private String alineacion="text";
     private Font fuente;
     private JLabel label = new JLabel();
     private JCheckBox chb = new JCheckBox();
     private ImageIcon salida = new ImageIcon("imagen/Delete.png");
     private ImageIcon entrada = new ImageIcon("imagen/Aplicar.png");
   
     String color_especial_de_columna ="";
	
	public boolean validacampo(String valorcelda){
		try { Double.parseDouble(valorcelda);
	    		return true;
		}catch (NumberFormatException nfe){
			return false;
		}
 	}
	
	public void Obj_Refrescar(JTable tabla,DefaultTableModel  modelo,int columnas,String comando,String BasdeDatos, String pintar, Integer checkbox){
    	tabla.getTableHeader().setReorderingAllowed(false) ;
    	tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		modelo.setRowCount(0);
		Connexion con = new Connexion();
		Statement  s =null;
		
		if(BasdeDatos.equals("200")){
			 try { s = con.conexion_IZAGAR().createStatement();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else{
			 try { s = con.conexion().createStatement();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}

		if(checkbox>-1){
			try{
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
		
		for(int i = 0; i<tabla.getColumnCount(); i++){
    		tabla.getColumnModel().getColumn(i).setMaxWidth(2000);
           }
    	
		if (pintar.equals("si")||checkbox>-1){
		for(int i = 0; i<tabla.getColumnCount(); i++){
			if(checkbox>-1&&i==checkbox){
			 tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRendererizado("CHB","centro","Arial","negrita",12));
			}else{
				if(tabla.getRowCount()>0){
					if( validacampo(modelo.getValueAt(0,i).toString().trim()) ){
					    tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRendererizado("texto","derecha","Arial","negrita",11));
					    tabla.getColumnModel().getColumn(i).setHeaderRenderer(new CabeceraTablaRendererizado(new java.awt.Color(RfilaS,GfilaS,BfilaS),Color.WHITE));
					}else{
					  tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRendererizado("texto","izquierda","Arial","negrita",11));	
					  tabla.getColumnModel().getColumn(i).setHeaderRenderer(new CabeceraTablaRendererizado(new java.awt.Color(RfilaS,GfilaS,BfilaS),Color.WHITE));
					}
				}else{
					  tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRendererizado("texto","izquierda","Arial","negrita",11));
					  tabla.getColumnModel().getColumn(i).setHeaderRenderer(new CabeceraTablaRendererizado(new java.awt.Color(RfilaS,GfilaS,BfilaS),Color.WHITE));
			  	}
		      }
           }
		}
	   }
		
//	public class Obj_Filtro{
//		@SuppressWarnings({ "unchecked", "rawtypes" })
//		public Obj_Filtro(JTable tabla,String contenido,int columnas){
//			int[] columnasa= new int [columnas];
//			for(int i=0;i<columnas;i++){
//				columnasa[i]=i;
//			}
//			
//			TableRowSorter sorter = new  TableRowSorter(tabla.getModel());
//			
//			ArrayList arregloDePalabrar = new ArrayList(); 
//			RowFilter filtradoDeArregloDePalabras = null; 
//
//			try {
//			    String[] listaDePalabras = contenido.split(" ");
//				    for(int i = 0; i < listaDePalabras.length; i++){
//				    	arregloDePalabrar.add(RowFilter.regexFilter(listaDePalabras[i],columnas));
//				    }
//				    filtradoDeArregloDePalabras = RowFilter.andFilter(arregloDePalabrar);
//			} catch (java.util.regex.PatternSyntaxException e){
//				return;
//			}
//			ArrayList andFilters = new ArrayList(); 	
//			if(!contenido.equals("")){andFilters.add(filtradoDeArregloDePalabras);}
//			sorter.setRowFilter(RowFilter.andFilter (andFilters));
//			tabla.setRowSorter(sorter);
//		}
//	};
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public  String[][] tabla_guardar(JTable tabla){ 
			int columnas=tabla.getColumnCount();
			DefaultTableModel  modelo = (DefaultTableModel) tabla.getModel();
			String[][] matriz = null ;
			if(tabla.isEditing()){
				tabla.getCellEditor().stopCellEditing();
			}
			
			if(tabla.getRowCount()==0){
				 JOptionPane.showMessageDialog(null, "No hay Datos que Guardar", "Aviso", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
				 matriz = new String[0][0];
			}else{ 
			
				Vector vector = new Vector();
				for(int i=0; i<tabla.getRowCount(); i++){
					for(int c=0; c<columnas; c++){
							  vector.add(modelo.getValueAt(i,c).toString().trim());
					}		  
				}
							matriz = new String[vector.size()/columnas][columnas];
					 int i=0,j =0,columnafor=0;
					while(i<vector.size()){
						columnafor=0;
					      for(int f =0;  f<columnas;  f++,columnafor++,i++  ){	
					  matriz[j][columnafor] = vector.get(i).toString();
					  }
					  j++;
				}
			}	
				
			return matriz;
		}
		
		public boolean validacelda(JTable tabla,String tipo, int fila,int columna){
			    DefaultTableModel  modelo = (DefaultTableModel) tabla.getModel();
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
						modelo.setValueAt(0, fila, columna);
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
//					if(columna==3){
						sacarFocoDeTabla="si";
//							}
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
	

		@SuppressWarnings("serial")
		public class tablaRendererizado extends DefaultTableCellRenderer {
		     public tablaRendererizado( String tipo ,String alineacionTexto, String tipoDeLetra, String estilo, int tamanio){
		         alineacion = alineacionTexto;
		         this.alineacionHorizontal( alineacion );
		         if(tamanio_fuente==0){
		            fuente(tipoDeLetra,estilo,tamanio);
		         }else{
		        	 fuente(tipoDeLetra,estilo,tamanio_fuente);
		         }
		         if(estilo.equals("Verde") ){
		         color_especial_de_columna=estilo;
		         }
		     }
		     
		     public Component getTableCellRendererComponent ( JTable table, Object value, boolean selected, boolean focused, int row, int column ){ 
		    	 if(color_especial_de_columna.equals("Verde")){
		    		 if(row %2 == 0){
							this.setBackground(new java.awt.Color(80,245,234));	
					  }else{
					      this.setBackground(Color.white);
					  }
		    	 }else{
			    	  if(row %2 == 0){
							this.setBackground(new java.awt.Color(Rfila,Gfila,Bfila));	
					  }else{
					      this.setBackground(Color.white);
					  }
		    	 }
		    	 
		    	 if (selected) {  
		        	 this.setBackground(new java.awt.Color(RfilaS,GfilaS,BfilaS));
		         }

		         if( tipo.toUpperCase().trim().equals("IMAGEN") || tipo.toUpperCase().trim().equals("IMAGENES") || tipo.toUpperCase().trim().equals("ICONO") || tipo.toUpperCase().trim().equals("ICONOS")){
		        	 label.setHorizontalAlignment(JLabel.CENTER);
		             if( String.valueOf(value).equals("1")){
		                 label.setIcon(entrada);
		             }else {
		                 label.setIcon(salida);
		             }
		             return label;
		         }
		         
		         if( tipo.toUpperCase().trim().equals("VENTA")){
						JLabel lbl = new JLabel(value == null? "": value.toString());
						lbl.setFont(fuente);
						lbl.setOpaque(true); 
						lbl.setBackground(new java.awt.Color(182,211,255));
					if(selected){
						lbl.setOpaque(true); 
						lbl.setBackground(new java.awt.Color(100,181,255));
					}
					lbl.setHorizontalAlignment(JLabel.CENTER);
		             return lbl;
		         }

		         if(tipo.toUpperCase().trim().equals("CHB")){
						chb = new JCheckBox("",Boolean.parseBoolean(value.toString()));
						if(row%2==0){
							((JComponent) chb).setOpaque(true); 
							chb.setBackground(new java.awt.Color(Rfila,Gfila,Bfila));	
						}
						if(table.getSelectedRow() == row){
							((JComponent) chb).setOpaque(true); 
							chb.setBackground(new java.awt.Color(RfilaS,GfilaS,BfilaS));
						}
						if(selected){
							((JComponent) chb).setOpaque(true); 
							chb.setBackground(new java.awt.Color(RfilaS,GfilaS,BfilaS));
						}
						((AbstractButton) chb).setHorizontalAlignment(SwingConstants.CENTER);	
						return chb;
					
		         }else{
		        	
		             this.setText( value.toString() );
		             this.setForeground( (selected)?new Color(RFuenteS,GFuenteS,BFuenteS):new Color(RFuente,GFuente,BFuente) ); 
		             this.setFont(fuente);            
		         }
		         return this;
		     }

			private void alineacionHorizontal(String alinear) {
				switch(alinear){
					case "centro":	setHorizontalAlignment(JLabel.CENTER); break;
					case "derecha":	setHorizontalAlignment(JLabel.RIGHT); break;
					default:		setHorizontalAlignment(JLabel.LEFT); break;//izquierda
				}
			}
			
			private void fuente(String tipografia,String apariencia,int tamanio) {
				switch(apariencia){
					case "negrita":	fuente = new Font( tipografia,Font.BOLD ,tamanio ); break;
					case "cursiva":	fuente = new Font( tipografia,Font.ITALIC ,tamanio ); break;
					default:		fuente = new Font( tipografia,Font.PLAIN ,tamanio ); break;//normal
				}
			}
		 }
		
		@SuppressWarnings("serial")
		public class CabeceraTablaRendererizado extends DefaultTableCellRenderer {
			Color background;
			Color foreground;
			
			public CabeceraTablaRendererizado (Color background, Color foreground) {
				super();
				this.background = background;
				this.foreground = foreground;
			}
			
			public Component getTableCellRendererComponent(JTable table, Object value,boolean isSelected, boolean hasFocus, int row, int column) {
				Component comp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				comp.setBackground(background);
				comp.setForeground(foreground);
				comp.setFont(fuente);
				this.setHorizontalAlignment(JLabel.CENTER);
				
				return comp;
			
			}
		}
		
}
