package Biblioteca;

import java.util.Vector;

public class CrearYConsultarVectorAnidado {
	
	String[][] matriz = {{"1a","2a","3a"},{"1b","2b","3b"},{"1c","2c","3c"}};

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public CrearYConsultarVectorAnidado() {
		
		Vector vPrincipal = new Vector();
		
		Vector SubVector;
		
		//matriz de 3x3
		for(int i =0; i<3; i++){
			SubVector = new Vector();
			for(int j =0; j<3; j++){
					SubVector.add(matriz[i][j]);
			}
			vPrincipal.add(SubVector);
		}
		
		System.out.println(vPrincipal.get(0));
		System.out.println(vPrincipal.get(1));
		System.out.println(vPrincipal.get(2));
		
		System.out.println(((Vector) vPrincipal.get(0)).get(0));
		System.out.println(((Vector) vPrincipal.get(1)).get(1));
		System.out.println(((Vector) vPrincipal.get(2)).get(2));
		
//		Vector b = new Vector();
//		b = (Vector) a.get(0);
//		System.out.println(b.get(0));
		
	}

	public static void main(String[] args) {
		new	CrearYConsultarVectorAnidado(); 
	}
}
