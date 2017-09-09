package Obj_Evaluaciones;

import java.sql.SQLException;

import Conexiones_SQL.BuscarSQL;


public class  Obj_Imprimir_Cuadrante {
	

	
	public Object Obj_Obtener_folio_empleado_buscar (String nombre) {
		try {
			return new BuscarSQL().Obj_Obtener_Folio_Empleado(nombre);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; 
	}
}
	
	

	

