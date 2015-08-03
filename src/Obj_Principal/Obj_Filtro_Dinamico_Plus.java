package Obj_Principal;

import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;

public class Obj_Filtro_Dinamico_Plus {
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Obj_Filtro_Dinamico_Plus(JTable tabla,String contenido1,int[] columnas){
		
		TableRowSorter sorter = new  TableRowSorter(tabla.getModel());
		
//declarecion de variables
		ArrayList arregloDePalabrar = new ArrayList(); 
		RowFilter filtradoDeArregloDePalabras = null; 

		try {
		    String[] listaDePalabras = contenido1.split(" ");
			    for(int i = 0; i < listaDePalabras.length; i++){
			    	arregloDePalabrar.add(RowFilter.regexFilter(listaDePalabras[i],columnas));
			    }
			    filtradoDeArregloDePalabras = RowFilter.andFilter(arregloDePalabrar);
		} catch (java.util.regex.PatternSyntaxException e){
			return;
		}
		
//declaramos ArrayList para guardar una lista de los filtros que se combinaran				
		ArrayList andFilters = new ArrayList(); 	
		
//si agregamos otro filtro lo agregamos de la siguiente manera al ArrayListe
		if(!contenido1.equals("")){andFilters.add(filtradoDeArregloDePalabras);}
		
// asignamos a la tablaModel el rowFilter integredo y lo aplicamos 
		sorter.setRowFilter(RowFilter.andFilter (andFilters));
		tabla.setRowSorter(sorter);
	}
}
