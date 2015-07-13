package Obj_Principal;

import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;

public class Obj_Filtro_Dinamico {
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Obj_Filtro_Dinamico(JTable tabla,String nom_Columna1,String contenido1,String nom_Columna2,String contenido2, String nom_Columna3,String contenido3,String nom_Columna4,String contenido4){
		
		if(contenido1.equals("Selecciona un Establecimiento")){	contenido1="";}
		if(contenido2.equals("Selecciona un Establecimiento")){	contenido2="";}
		if(contenido3.equals("Selecciona un Establecimiento")){	contenido3="";}
		if(contenido4.equals("Selecciona un Establecimiento")){	contenido4="";}
		
		
		TableRowSorter sorter = new  TableRowSorter(tabla.getModel());
		
		StringTokenizer tokens1 = new StringTokenizer(contenido1);
		
//		declarecion de variables
		ArrayList arregloDePalabrar = new ArrayList(); 
		RowFilter filtradoDeArregloDePalabras; 

//		recorremos la cadena y la separamos por cada espacion en blanco
		while(tokens1.hasMoreTokens()){
//			declaramos una variable para capturar la palabra en la que ba la funcion while y la agregamos al arreglo
			RowFilter palabra = RowFilter.regexFilter(tokens1.nextToken().toString().toUpperCase(), tabla.getColumnModel().getColumnIndex(nom_Columna1));
			arregloDePalabrar.add(palabra); 
		}
		filtradoDeArregloDePalabras = RowFilter.andFilter(arregloDePalabrar);
		
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//si queremos combinarlo con otro filtro con una columna distinta, agregamos otro procedimiento igual a lo anterior aqui, cambiando los nombres de las variables		
		
		StringTokenizer tokens2 = new StringTokenizer(contenido2);
		
		ArrayList arregloDePalabrar2 = new ArrayList(); 
		RowFilter filtradoDeArregloDePalabras2; 

		while(tokens2.hasMoreTokens()){
			RowFilter palabra = RowFilter.regexFilter (tokens2.nextToken().toString().toUpperCase(), tabla.getColumnModel().getColumnIndex(nom_Columna2));
			arregloDePalabrar2.add(palabra); 
		}
		filtradoDeArregloDePalabras2 = RowFilter.andFilter(arregloDePalabrar2);
		
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		StringTokenizer tokens3 = new StringTokenizer(contenido3);
		
		ArrayList arregloDePalabrar3 = new ArrayList(); 
		RowFilter filtradoDeArregloDePalabras3; 

		while(tokens3.hasMoreTokens()){
			RowFilter palabra = RowFilter.regexFilter (tokens3.nextToken().toString().toUpperCase(), tabla.getColumnModel().getColumnIndex(nom_Columna3));
			arregloDePalabrar3.add(palabra); 
		}
		filtradoDeArregloDePalabras3 = RowFilter.andFilter(arregloDePalabrar3);
		
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		
		StringTokenizer tokens4 = new StringTokenizer(contenido4);
		
		ArrayList arregloDePalabrar4 = new ArrayList(); 
		RowFilter filtradoDeArregloDePalabras4; 

		while(tokens4.hasMoreTokens()){
			RowFilter palabra = RowFilter.regexFilter (tokens4.nextToken().toString().toUpperCase(), tabla.getColumnModel().getColumnIndex(nom_Columna4));
			arregloDePalabrar4.add(palabra); 
		}
		filtradoDeArregloDePalabras4 = RowFilter.andFilter(arregloDePalabrar4);
		
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		//declaramos ArrayList para guardar una lista de los filtros que se combinaran				
		ArrayList andFilters = new ArrayList(); 		
		//si agregamos otro filtro lo agregamos de la siguiente manera al ArrayListe
		if(!contenido1.equals("")){andFilters.add(filtradoDeArregloDePalabras);}
		if(!contenido2.equals("")){andFilters.add(filtradoDeArregloDePalabras2);}
		if(!contenido3.equals("")){andFilters.add(filtradoDeArregloDePalabras3);}
		if(!contenido4.equals("")){andFilters.add(filtradoDeArregloDePalabras4);}
		
		
		// asignamos a la tablaModel el rowFilter integredo y lo aplicamos 
		sorter.setRowFilter(RowFilter.andFilter (andFilters));
		tabla.setRowSorter(sorter);
	}
	
}
