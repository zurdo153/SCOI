package Obj_Xml;

public class LeerXml { 

    public LeerXml(){
    	String cadenaXML = "<fila>    	    <columna_1>1</columna_1>    	    <columna_2>asd</columna_2>    	    <columna_3>ccc</columna_3>    	  </fila>    	  <fila>	    	<columna_1>2</columna_1>	    	<columna_2>qwe</columna_2>	    	<columna_3>aaa</columna_3>	  	  </fila>";    	
    	arregloLleno(cadenaXML);
    } 
     
	public Object[][] arregloLleno(String CadenaXML){

		Object[][] arreglo = null;
		
	    	 	int fin=((int) CadenaXML.length()); 
	    	 	
	    	    String[] palabras = CadenaXML.substring(0,fin ).split("<");
	    	    
	    	    //contador de filas y columnas
	    	    int contador_de_filas = 0;
	    	    int contador_de_columnas = 0;
	    	 	for(int cont=0; cont<palabras.length; cont++){
					 if(palabras[cont].matches("/fila>(.*)")){ 
					 		contador_de_filas++;
					 } 
					 if(palabras[cont].matches("columna_(.*)")){ 
						 contador_de_columnas++;
					 } 
	    	 	}
	    	 	
	    	 	 //arreglo para llenar con registros(con dimenciones asignadas)
	    	 	 arreglo = new Object[contador_de_filas][(contador_de_columnas/contador_de_filas)];
		    	    
	    	 	//toma fila y extrae los valores de las columnas, posteriormente las agrega al arreglo
	    	 	 for(int fila=0; fila<contador_de_filas; fila++){
	    	 		 String filaActual = CadenaXML.substring(0,CadenaXML.indexOf("</fila>"))+"</fila>";
	    	 		 CadenaXML = CadenaXML.substring(filaActual.length(),(CadenaXML.length()) ).trim();
	    	 		 
	    	 		 for(int col=0; col<(contador_de_columnas/contador_de_filas); col++){
	    	 			 String columna = filaActual.substring(filaActual.indexOf("<columna_"+(col+1)+">"),filaActual.indexOf("</columna_"+(col+1)+">"))+"</columna_"+(col+1)+">";
	    	 			 String a = columna.substring(columna.indexOf(">")+1,columna.indexOf("</"));
	    	 			
	    	 			arreglo[fila][col] = a;
	    	 		 }
	    	 		 
	    	 	 }
		return arreglo; 
    } 
    
    public static void main(String [] arg){
    	new LeerXml();
    }
} 