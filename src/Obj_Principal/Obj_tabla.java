package Obj_Principal;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Rectangle;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.AbstractButton;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.JTextComponent;
import javax.swing.text.MaskFormatter;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.time.LocalDate;

import Conexiones_SQL.Connexion;
import Obj_Administracion_del_Sistema.Obj_Usuario;

public class Obj_tabla {
	  Obj_Usuario usuario = new Obj_Usuario().LeerSession();
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

     private String alineacion="text";
     private Font fuente;
     private JCheckBox chb = new JCheckBox();
	
	public boolean validacampo(String valorcelda){
		try { Double.parseDouble(valorcelda);
	    		return true;
		}catch (NumberFormatException nfe){
			return false;
		}
 	}
	
public void tabla_mascara(JTable tabla,int columnamask1, int columnamask2 ){
	DefaultTableModel modelo= (DefaultTableModel) tabla.getModel();

	if(columnamask1>-1){
	JFormattedTextField ftext = new JFormattedTextField();
	MaskFormatter mask;
	try {
	    mask = new MaskFormatter("##:##");
	    mask.install(ftext);
	} catch (java.text.ParseException e) {
	    e.printStackTrace();
	}
	tabla.getColumnModel().getColumn(columnamask1).setCellEditor(new DefaultCellEditor(ftext));
	}
	
	if(columnamask2>-1){
	JFormattedTextField ftext = new JFormattedTextField();
	MaskFormatter mask;
	try {
	    mask = new MaskFormatter("##:##");
	    mask.install(ftext);
	} catch (java.text.ParseException e) {
	    e.printStackTrace();
	}
	tabla.getColumnModel().getColumn(columnamask2).setCellEditor(new DefaultCellEditor(ftext));
	}
	
	
	tabla.getTableHeader().setReorderingAllowed(false) ;
	tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	modelo.setRowCount(0);
	
	tabla.getColumnModel().getColumn(0).setMinWidth(55);
	tabla.getColumnModel().getColumn(0).setMaxWidth(55);
	tabla.getColumnModel().getColumn(1).setMinWidth(55);
	tabla.getColumnModel().getColumn(1).setMaxWidth(55);	
	tabla.getColumnModel().getColumn(2).setMinWidth(375);
	tabla.getColumnModel().getColumn(3).setMinWidth(50);
 	tabla.getColumnModel().getColumn(3).setMaxWidth(50);
	tabla.getColumnModel().getColumn(4).setMinWidth(50);
 	tabla.getColumnModel().getColumn(4).setMaxWidth(50);
 	
 	if(tabla.getColumnCount()>5){
	tabla.getColumnModel().getColumn(5).setMinWidth(80);
 	tabla.getColumnModel().getColumn(5).setMaxWidth(80);
	tabla.getColumnModel().getColumn(6).setMinWidth(150);
 	tabla.getColumnModel().getColumn(6).setMaxWidth(150);
 	}
	for(int i = 0; i<tabla.getColumnCount(); i++){
		tabla.getColumnModel().getColumn(i).setMaxWidth(2000);
       }
		
		for(int i = 0; i<tabla.getColumnCount(); i++){
			 tabla.getColumnModel().getColumn(i).setHeaderRenderer(new CabeceraTablaRendererizado(new java.awt.Color(RfilaS,GfilaS,BfilaS),Color.WHITE));
			
				if(tabla.getRowCount()>0){
					if( validacampo(modelo.getValueAt(0,i).toString().trim()) ){
					    tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRendererizado("texto","derecha","Arial","negrita",11));
					}else{
					  tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRendererizado("texto","izquierda","Arial","negrita",11));	
					}
				}else{
					  tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRendererizado("texto","izquierda","Arial","negrita",11));
			  	}
		      }
 }

public void tabla_programacion_proveedores_mascara(JTable tabla,int columnamask1, int columnamask2 ){
	DefaultTableModel modelo= (DefaultTableModel) tabla.getModel();

	if(columnamask1>-1){
	JFormattedTextField ftext = new JFormattedTextField();
	MaskFormatter mask;
	try {
	    mask = new MaskFormatter("##:##");
	    mask.install(ftext);
	} catch (java.text.ParseException e) {
	    e.printStackTrace();
	}
	tabla.getColumnModel().getColumn(columnamask1).setCellEditor(new DefaultCellEditor(ftext));
	}
	
	if(columnamask2>-1){
	JFormattedTextField ftext = new JFormattedTextField();
	MaskFormatter mask;
	try {
	    mask = new MaskFormatter("##:##");
	    mask.install(ftext);
	} catch (java.text.ParseException e) {
	    e.printStackTrace();
	}
	tabla.getColumnModel().getColumn(columnamask2).setCellEditor(new DefaultCellEditor(ftext));
	}
	
	
	tabla.getTableHeader().setReorderingAllowed(false) ;
	tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	modelo.setRowCount(0);
	
	tabla.getColumnModel().getColumn(0).setMinWidth(55);
	tabla.getColumnModel().getColumn(0).setMaxWidth(55);
	tabla.getColumnModel().getColumn(1).setMinWidth(65);
	tabla.getColumnModel().getColumn(1).setMaxWidth(65);	
	tabla.getColumnModel().getColumn(2).setMinWidth(55);
	tabla.getColumnModel().getColumn(3).setMinWidth(300);
	tabla.getColumnModel().getColumn(4).setMinWidth(120);
 	tabla.getColumnModel().getColumn(4).setMaxWidth(50);
	tabla.getColumnModel().getColumn(5).setMinWidth(50);
 	tabla.getColumnModel().getColumn(5).setMaxWidth(50);
	tabla.getColumnModel().getColumn(6).setMinWidth(50);
 	tabla.getColumnModel().getColumn(7).setMinWidth(150);
	tabla.getColumnModel().getColumn(8).setMinWidth(300);
	tabla.getColumnModel().getColumn(9).setMinWidth(150);
 	
	for(int i = 0; i<tabla.getColumnCount(); i++){
		tabla.getColumnModel().getColumn(i).setMaxWidth(2000);
       }
		
		for(int i = 0; i<tabla.getColumnCount(); i++){
			 tabla.getColumnModel().getColumn(i).setHeaderRenderer(new CabeceraTablaRendererizado(new java.awt.Color(RfilaS,GfilaS,BfilaS),Color.WHITE));
			
				if(tabla.getRowCount()>0){
					if( validacampo(modelo.getValueAt(0,i).toString().trim()) ){
					    tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRendererizado("texto","derecha","Arial","negrita",11));
					}else{
					  tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRendererizado("texto","izquierda","Arial","negrita",11));	
					}
				}else{
					  tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRendererizado("texto","izquierda","Arial","negrita",11));
			  	}
		      }
 }
  public void tabla_precargada(JTable tabla){
	DefaultTableModel modelo= (DefaultTableModel) tabla.getModel();
	tabla.getTableHeader().setReorderingAllowed(false) ;
	tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	modelo.setRowCount(0);

	for(int i = 0; i<tabla.getColumnCount(); i++){
		tabla.getColumnModel().getColumn(i).setMaxWidth(2000);
       }
		
		for(int i = 0; i<tabla.getColumnCount(); i++){
			 tabla.getColumnModel().getColumn(i).setHeaderRenderer(new CabeceraTablaRendererizado(new java.awt.Color(RfilaS,GfilaS,BfilaS),Color.WHITE));
			
				if(tabla.getRowCount()>0){
					if( validacampo(modelo.getValueAt(0,i).toString().trim()) ){
					    tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRendererizado("texto","derecha","Arial","negrita",11));
					}else{
					  tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRendererizado("texto","izquierda","Arial","negrita",11));	
					}
				}else{
					  tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRendererizado("texto","izquierda","Arial","negrita",11));
			  	}
		      }
   }

  public void tabla_precargada_derecha(JTable tabla){
		DefaultTableModel modelo= (DefaultTableModel) tabla.getModel();
		tabla.getTableHeader().setReorderingAllowed(false) ;
		tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		modelo.setRowCount(0);

		for(int i = 0; i<tabla.getColumnCount(); i++){
			tabla.getColumnModel().getColumn(i).setMaxWidth(2000);
	       }
			
			for(int i = 0; i<tabla.getColumnCount(); i++){
				 tabla.getColumnModel().getColumn(i).setHeaderRenderer(new CabeceraTablaRendererizado(new java.awt.Color(RfilaS,GfilaS,BfilaS),Color.WHITE));
						if( i==0 ){	
				            tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRendererizado("texto","izquierda","Arial","negrita",11));	
						  }else{
						    tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRendererizado("texto","derecha","Arial","negrita",11));
			              }
			      }
	   }

  @SuppressWarnings({ "unchecked", "rawtypes" })
public void Obj_Refrescar(JTable tabla,DefaultTableModel  modelo,int columnas,String comando,String BasdeDatos, String pintar, Integer checkbox){
		trsfiltro = new TableRowSorter(modelo); 
		tabla.setRowSorter(trsfiltro);
		
    	tabla.getTableHeader().setReorderingAllowed(false) ;
    	tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		modelo.setRowCount(0);
		Connexion con = new Connexion();
		Statement  s =null;
	 if(!BasdeDatos.equals("0")){
		
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
//							if(i==checkbox){
//								 fila[i] = "false";
//							}else{
						         fila[i] = rs.getString(i+1).trim();
//							}
						 }
				   modelo.addRow(fila); 
				}	
			} catch (SQLException e1) {
				e1.printStackTrace();
				System.out.println("Comando:"+comando);
				JOptionPane.showMessageDialog(null, "Error en la funcion refrescar tabla SQLException: "+e1.getMessage()+" \n  Comando:"+comando, "Avisa al Administrador Del Sistema", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
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
				System.out.println("Comando:"+comando);
				JOptionPane.showMessageDialog(null, "Error en la funcion refrescar tabla SQLException: "+e1.getMessage()+" \n Comando"+comando, "Avisa al Administrador Del Sistema", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			}
	     }	
		scrollposicion(tabla, 0, 0);
	}
		
		for(int i = 0; i<tabla.getColumnCount(); i++){
    		tabla.getColumnModel().getColumn(i).setMaxWidth(2000);
           }
    	
		if (pintar.equals("si")||checkbox>-1){
			for(int i = 0; i<tabla.getColumnCount(); i++){
				 tabla.getColumnModel().getColumn(i).setHeaderRenderer(new CabeceraTablaRendererizado(new java.awt.Color(RfilaS,GfilaS,BfilaS),Color.WHITE));
				if(checkbox>-1&&i==checkbox-1){
				 tabla.getColumnModel().getColumn(checkbox-1).setCellRenderer(new tablaRendererizado("CHB","centro","Arial","negrita",12));
				}else{
					if(tabla.getRowCount()>0){
						if( validacampo(modelo.getValueAt(0,i).toString().trim()) ){
						    tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRendererizado("texto","derecha","Arial","negrita",11));
						}else{
						  tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRendererizado("texto","izquierda","Arial","negrita",11));	
						}
					}else{
						  tabla.getColumnModel().getColumn(i).setCellRenderer(new tablaRendererizado("texto","izquierda","Arial","negrita",11));
				  	}
			      }
	           }
		 }
		
	   }
	
	@SuppressWarnings("serial")
	public class tablaRendererizado extends DefaultTableCellRenderer {
	     private String tipo="";
	     public tablaRendererizado( String tipop ,String alineacionTexto, String tipoDeLetra, String estilo, int tamanio){
	    	 tipo=tipop;
	         alineacion = alineacionTexto;
	         alineacionHorizontal( alineacion );
	         if(tamanio_fuente==0){
	            fuente(tipoDeLetra,estilo,tamanio);
	         }else{
	        	 fuente(tipoDeLetra,estilo,tamanio_fuente);
	         } 	 
	     }
	     
	     public Component getTableCellRendererComponent ( JTable table, Object value, boolean selected, boolean focused, int row, int column ){ 
		     if(row %2 == 0){this.setBackground(new java.awt.Color(Rfila,Gfila,Bfila));}else{ this.setBackground(Color.white); }
	    	 if (selected){ this.setBackground(new java.awt.Color(RfilaS,GfilaS,BfilaS)); }

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
	
	 @SuppressWarnings("rawtypes")
	private TableRowSorter trsfiltro;
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void Obj_Filtro(JTable tabla,String contenido,int columnasa, JComponent txt) {
		DefaultTableModel modelo= (DefaultTableModel) tabla.getModel();
			trsfiltro = new TableRowSorter(modelo); 
			tabla.setRowSorter(trsfiltro);
		
			int[] columnas= new int [columnasa];
			for(int i=0;i<columnasa;i++){
				columnas[i]=i;
			}
			TableRowSorter sorter = new  TableRowSorter(tabla.getModel());
			ArrayList arregloDePalabrar = new ArrayList(); 
			RowFilter filtradoDeArregloDePalabras = null; 

			try {
			    String[] listaDePalabras = contenido.split(" ");
				    for(int i = 0; i < listaDePalabras.length; i++){
				    	arregloDePalabrar.add(RowFilter.regexFilter(listaDePalabras[i],columnas));
				    }
				    filtradoDeArregloDePalabras = RowFilter.andFilter(arregloDePalabrar);
			} catch (java.util.regex.PatternSyntaxException e){
				return;
			}
			ArrayList andFilters = new ArrayList(); 	
			if(!contenido.equals("")){andFilters.add(filtradoDeArregloDePalabras);}
			sorter.setRowFilter(RowFilter.andFilter (andFilters));
			tabla.setRowSorter(sorter);
			txt.addKeyListener(new PasarATabla(tabla));
			
		};
	
		class PasarATabla implements KeyListener{   
			JTable tablaparametro;
			int filak=0,columnak=0;
		    public PasarATabla (final JTable tblp){
		    	tablaparametro = tblp;
		    }
		    public void actionPerformed(ActionEvent evt){}
			@Override
			public void keyPressed(KeyEvent arg0) {}
			@Override
			public void keyReleased(KeyEvent e) {
				
				if(e.getKeyCode()==KeyEvent.VK_DOWN){
					tablaparametro.requestFocus();
					tablaparametro.getSelectionModel().setSelectionInterval(0,0);
				}
			}
			@Override
			public void keyTyped(KeyEvent arg0) {}
		};
		
	
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
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public  String[][] tabla_guardar_sin_validacion(JTable tabla){ 
			int columnas=tabla.getColumnCount();
			DefaultTableModel  modelo = (DefaultTableModel) tabla.getModel();
			String[][] matriz = null ;
			if(tabla.isEditing()){
				tabla.getCellEditor().stopCellEditing();
			}
			if(tabla.getRowCount()==0){
				 matriz = new String[0][0];
				 return matriz;
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
			  
			   if(tipo.equals("decimal")|| tipo.equals("entero")){
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
			   
			   if(tipo.equals("hora")){
				   if(valorcelda.length()==5){
				    String hora=valorcelda.substring(0, 2);
				    String separador=valorcelda.substring(2, 3);
				    String minuto=valorcelda.substring(3, 5);
				    if(separador.equals(":")){
				    	if(validarHora(Integer.valueOf(hora)) ){
				        	if(validarMinuto(Integer.valueOf(minuto)) ){
				        		return true;
				        	}else{
				        		 JOptionPane.showMessageDialog(null, "La Fila  "+(fila+1)+"\nEl Rango De Minutos Debe De Ser De 0 a 59 \nRequiere Teclear Una Hora Valida en formato 24 hr \n Ejemplos:\nPara La 7 de La Mañana 07:00 \nPara Las 7 De La Tarde 19:00   "+Aviso,"Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
								 tabla.editCellAt(fila, columna);
								 Component accion=tabla.getEditorComponent();
								 accion.requestFocus();
								 return false;
				        	}
				    	}else{
						    JOptionPane.showMessageDialog(null, "La Fila  "+(fila+1)+"\nEl Rango De Hora Debe De Ser De 0 a 23 \nRequiere Teclear Una Hora Valida en formato 24 hr \n Ejemplos:\nPara La 7 de La Mañana 07:00 \nPara Las 7 De La Tarde 19:00   "+Aviso,"Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
						    tabla.editCellAt(fila, columna);
						    Component accion=tabla.getEditorComponent();
							accion.requestFocus();
							return false;
		                 }

				    }else{
								    JOptionPane.showMessageDialog(null, "La Fila  "+(fila+1)+"\nEs un Formato Invalido De Hora \nRequiere Teclear Una Hora Valida en formato 24 hr \n Ejemplos:\nPara La 7 de La Mañana 07:00 \nPara Las 7 De La Tarde 19:00   "+Aviso,"Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
								    tabla.editCellAt(fila, columna);
								    Component accion=tabla.getEditorComponent();
									accion.requestFocus();
									return false;
				      }
				   }else{
					    JOptionPane.showMessageDialog(null, "La Fila  "+(fila+1)+"\nTiene Mas De 5 o Menos De 4 Caracteres \nRequiere Teclear Una Hora Valida en formato 24 hr \n Ejemplos:\nPara La 7 de La Mañana 07:00 \nPara Las 7 De La Tarde 19:00   "+Aviso,"Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
					    tabla.editCellAt(fila, columna);
					    Component accion=tabla.getEditorComponent();
						accion.requestFocus();
						return false;
				   }
			     }
			   
				if(tipo.equals("fecha")){
                 if(validarfecha(valorcelda).equals("Fecha Valida")){
                	  return true;
                 }else{
							    JOptionPane.showMessageDialog(null, "La Fila  "+(fila+1)+" Requiere Teclear Una Fecha Valida"+Aviso,"Aviso",JOptionPane.WARNING_MESSAGE,new ImageIcon("Imagen/usuario-de-alerta-icono-4069-64.png"));
							    tabla.editCellAt(fila, columna);
							    Component accion=tabla.getEditorComponent();
								accion.requestFocus();
								return false;
							}
			         }
					return false;
		 	};
		 	
		 	 public boolean validarHora(int hora){
		             boolean valida = false;
		            if( (hora >= 0) && (hora < 24) ){
		            	valida = true;            
		            }  
		            return valida;
		        }  
		 	 
		 	 public boolean validarMinuto(int hora){
	             boolean valida = false;
	            if( (hora >= 0) && (hora < 60) ){
	            	valida = true;            
	            }  
	            return valida;
	        }    
		 	 
		 	public String validarfecha(String fecha){
				String aviso = "";
				int dayOfMonth 		=0;
				int month 		=0;
				int year 	=0;
				
				int contarChar = 0 ;
				char[] arrayChar = fecha.toCharArray();
		 
				for(int i=0; i<arrayChar.length; i++){
					if( arrayChar[i] == '/')
						contarChar++;
				}
				
				if(contarChar==2){
					
							if(validaNumero(fecha.substring(0,fecha.indexOf("/")))){
								dayOfMonth = Integer.valueOf(fecha.substring(0,fecha.indexOf("/")));
								fecha = fecha.substring(fecha.indexOf("/")+1,fecha.length());
							}
							if(validaNumero(fecha.substring(0,fecha.indexOf("/")))){
								month 		= Integer.valueOf(fecha.substring(0,fecha.indexOf("/")));
								fecha = fecha.substring(fecha.indexOf("/")+1,fecha.length());
							}
							if(validaNumero(fecha)){
								year 	= Integer.valueOf(fecha);
							}
							if (year < 1900) {
								aviso = "Fecha Invalida";
					        } else{
						        try {
					        		LocalDate.of(year, month, dayOfMonth);
					        		aviso = "Fecha Valida";
								} catch (Exception e) {
									aviso = "Fecha Invalida";
								}
					        }
				}else{
					aviso = "Fecha Invalida";
				}
				return aviso;
			}
			
			public boolean validaNumero(String dia){
				boolean validar = false;
				try {
					Integer.valueOf(dia);
					validar = true;
					
				} catch (Exception e) {
					validar = false;
				}
				return validar;
			}
			
		@SuppressWarnings("deprecation")
		public String RecorridoFocotabla(JTable tabla,int fila,int columna, String parametrosacarfoco){
			 String sacarFocoDeTabla = "";
			 sacarFocoDeTabla=parametrosacarfoco;
			
			if(sacarFocoDeTabla.equals("seguir")){
				int Cantidad_filas =tabla.getRowCount();
				sacarFocoDeTabla = "no";
				if(Cantidad_filas==fila+1){
					fila=0;
				}else{
				 fila=fila+1;
				}
			}else{	
	            if(sacarFocoDeTabla.equals("no")){	
					sacarFocoDeTabla = "no";
					fila=fila-1;
				}else{
					int cantidadDeFilas = tabla.getRowCount();
					if(fila == cantidadDeFilas-1){
							sacarFocoDeTabla="si";
					}else{
						sacarFocoDeTabla = "no";
						fila=fila+1;
					}
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
	    
	    @SuppressWarnings("deprecation")
		public String RecorridoFocotabla_con_evento(JTable tabla,int fila,int columna, String parametrosacarfoco,KeyEvent evento_teclado){
	    	 int cantidad_maxima_de_columnas=tabla.getColumnCount();
			 String sacarFocoDeTabla = "";
			 sacarFocoDeTabla=parametrosacarfoco;
				
			if(sacarFocoDeTabla.equals("ciclo")){
				int Cantidad_filas =tabla.getRowCount();
				sacarFocoDeTabla = "no";
				if(Cantidad_filas==fila+1){
					fila=0;
				}else{
					if(evento_teclado.getKeyCode()==38||evento_teclado.getKeyCode()==40) {
					}else {
				    fila=fila+1;
					}
				}
			}else{	
	            if(sacarFocoDeTabla.equals("no")){	
					sacarFocoDeTabla = "no";
					fila=fila-1;
				}else{
					int cantidadDeFilas = tabla.getRowCount();
					if(fila == cantidadDeFilas-1){
							sacarFocoDeTabla="si";
					}else{
						
						sacarFocoDeTabla = "no";
						if(evento_teclado.getKeyCode()==38||evento_teclado.getKeyCode()==40) {
						}else{
							if(evento_teclado.getKeyCode()==9) {
								if(cantidad_maxima_de_columnas<columna) {
								}else {
								 columna=columna+1;
								}
							}else
						     fila=fila+1;
							}
				 	   }
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
	        table.scrollRectToVisible(new Rectangle(table.getCellRect(fila, columna, true)));
	}
		
}
