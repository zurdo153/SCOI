package Cat_Chat;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * @Emoticons.java
 * 
 * Clase encargada de gestionar la lista 
 * de emoticonos por su clave, en este caso
 * un atajo.
 * 
 * */
public class Emoticons{
	public static final String pathe ="/imagen/emoticones/";
	
	/**
	 * Cada emoticono tendrá como clave un atajo y como
	 * valor su ruta.
	 * */
	public static Hashtable<String,String> emoticonos;
	
	public static Hashtable<String,String> atajos;
	
	public static ArrayList<String>        shortCuts;
		
	private static boolean                 status = false;
	
	/**
	 * Constructor.
	 * */
	public Emoticons(){
		init();
	}

	public void init(){
		if( !status ){
			
			emoticonos = new Hashtable<String,String>();
			
			atajos     = new Hashtable<String,String>();
			
			shortCuts  = new ArrayList<String>();
			
			atajos();
			
			status = true;
		}
	}
	/**
	 * Agrega un emoticono.
	 * 
	 * @param atajo clave en la tabla hash.
	 * @param url valor en la tabla hash.
	 * */
	public void addEmoticono(String atajo, String ruta){
		emoticonos.put(atajo, ruta);
		atajos.put(ruta, atajo);
	}
	
	/**
	 * Determina si un emoticono existe o no.
	 * 
	 * */
	public boolean existeEmoticono(String atajo){
		String ruta = emoticonos.get(atajo);
		if( ruta != null )
			return true;
		return false;
	}
	
	private void atajos(){
		addShortCut(";)");
		addShortCut("xD");
		addShortCut(";O");
		addShortCut(";bs");
		addShortCut(">)");
		addShortCut(";=)");
		addShortCut("*_*");
		addShortCut(";D");
		addShortCut(";S");
		addShortCut(";P");
		addShortCut(";'(");
		addShortCut("><)");
		addShortCut(";v");
		for( int i = 0; i < shortCuts.size(); i++ )						
			addEmoticono(shortCuts.get(i), pathe + i + ".png");
	}
	
	private void addShortCut(String atajo){		
		shortCuts.add(atajo);
	}
	
	static boolean esEmoticon(String atajo){
		
		String tmp = emoticonos.get(atajo);
		
		if( tmp != null )
			return true;
		
		return false;
	}
	
}
