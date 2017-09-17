package Biblioteca;

import java.util.Vector;

public class CrearYConsultarVectorAnidado {
	
	String[][] matriz = {{"1a","2a","3a","4a"},{"1b","2b","3b","4b"},{"1c","2c","3c","4c"}};

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public CrearYConsultarVectorAnidado() {
		
		Vector vPrincipal = new Vector();//filas
		Vector SubVector;//columnas
		
		//matriz de 3x3
		for(int i =0; i<3; i++){
			SubVector = new Vector();
			for(int j =0; j<4; j++){
					SubVector.add(matriz[i][j]);
			}
			vPrincipal.add(SubVector);
		}
		
		System.out.println("filas -> "+vPrincipal.size()); //filas(cantidad)
		
		System.out.print(vPrincipal.get(0));	//datos de la fila (cada fila almacena otro vector)
		System.out.print(vPrincipal.get(1));
		System.out.println(vPrincipal.get(2));	
		
		System.out.println("columnas -> "+((Vector) vPrincipal.get(0)).size()); //columnas (cantidad de columnas)
		
//		System.out.println(((Vector) vPrincipal.get(0)).get(0)); //acceder al los datos del vectos subvector (dato de columna)
//		System.out.println(((Vector) vPrincipal.get(1)).get(1));
//		System.out.println(((Vector) vPrincipal.get(2)).get(2));
		
//		Vector b = new Vector();
//		b = (Vector) a.get(0);
//		System.out.println(b.get(0));
		
	}

	public static void main(String[] args) {
		new	CrearYConsultarVectorAnidado(); 
	}
}
