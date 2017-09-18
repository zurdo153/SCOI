package Cat_Chat;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Clase encargada del análisis del texto a enviar.
 * */
class LexicoAnalyzer{
		
	private ArrayList<String> elementos;
	private int length;
	
	/**
	 * Constructor. 
	 * 
	 * @param texto El texto a analizar.
	 * */
	public LexicoAnalyzer(String texto){
		
		elementos = new ArrayList<String>();
				
		cargaElementos(texto);		
	}
	
	/**
	 * Carga elementos al ArrayList.
	 * */
	public void cargaElementos(String texto){
		
		StringTokenizer textoSTkzr = new StringTokenizer(texto, " ");
		
		while( textoSTkzr.hasMoreTokens() ){
			elementos.add(textoSTkzr.nextToken());
			elementos.add(" ");
		}
		
		preanalisis();		
	}
	
	public void preanalisis(){
		
		String tmp;
		String cad;
		String pre;
		String newCad;
		String resto;
		String [] cadSplit;
		
		for( int y = 0; y < elementos.size(); y++ ){
			
			cad = elementos.get(y);
			
			tmp = Emoticons.emoticonos.get(cad);
			
			// Si no es emoticon
			if( tmp == null ){
				
				// Se verifica si está contenido un emoticon
				int pos = containsEmoticon(cad);
				
				if( pos != -1 ){
									
					cadSplit = cad.split("");
					
					pre    = "";
					newCad = "";
					resto  = "";
										
					if( pos == 0 ){
						
						for( int j = pos; j <= length; j++ )				
							newCad += cadSplit[j];
						
						for( int x = length + 1; x < cadSplit.length; x++ )				
							resto += cadSplit[x];
						
						delCadena(y);
						addCadena(y, resto);
						addCadena(y, newCad);						
					}
					
					else{
						
						for( int i = 0; i <= pos; i++ )
							pre += cadSplit[i];
						
						for( int j = pos + 1; j <= pos + length; j++ )				
							newCad += cadSplit[j];
						
						for( int k = pos + length + 1; k < cadSplit.length; k++ )				
							resto += cadSplit[k];
												
						delCadena(y);
						addCadena(y, resto);
						addCadena(y, newCad);
						addCadena(y, pre);
					}					
										
				}				
			
			}			
		}		
		
	}
	
	/** Regresa primera cadena de la lista */
	public String getCadena(){ return elementos.get(0); }
	
	/**
	 * Inserta una cadena del fuente en la posición especificada.
	 * */
	public void addCadena(int pos, String cadena){ elementos.add( pos, cadena); }
	
	/** Elimina la cadena del inicio de la lista */
	public void delCadena(){ elementos.remove(0); }
	
	public void delCadena(int pos){ elementos.remove(pos); }
		
	/** Verifica si aún existen CADENAS por analizar */
	public boolean hasToken(){
		
		if( elementos.size() >= 1 )
			return true;
	
		return false;
	
	}
	
	/**
	 * Devuelve el sigueinte elemento de la lista.
	 * */
	public String nextToken(){
		
		String retorna = getCadena();
			
		delCadena();
				
		return retorna;
		
	}
		
	private int containsEmoticon(String string){
		
		int pos = -1;
		
		for( int i = 0; i < Emoticons.shortCuts.size(); i++  ){
			pos = string.indexOf(Emoticons.shortCuts.get(i));
			if( pos != -1 ){
				length = Emoticons.shortCuts.get(i).length();
				return pos;
			}
		}
			
		return pos;
	}
	
	public void mostrarTokens(){
		
		for(String str: elementos)
			System.out.println("Elemento:"+str);
		
	}

}






