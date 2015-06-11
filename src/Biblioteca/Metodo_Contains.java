package Biblioteca;

public class Metodo_Contains {

	public static void main(String[] args) {
		   String str1 = "tutorials point", str2 = "http://";

		   CharSequence cs1 = "int";
		    
		   // string contains the specified sequence of char values
		   boolean retval = str1.contains(cs1);
		   System.out.println("Method returns : " + retval);
		    
		   // string does not contain the specified sequence of char value
		   retval = str2.contains("_");   
		   System.out.println("Methods returns: " + retval);
	}

}
