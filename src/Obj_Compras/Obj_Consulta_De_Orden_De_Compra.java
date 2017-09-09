package Obj_Compras;

import java.sql.SQLException;

import Conexiones_SQL.Cargar_Combo;

public class Obj_Consulta_De_Orden_De_Compra {
	
	public String[] Combo_Grupo_De_Personal(int folio_departamento){ 
		try {
			return new Cargar_Combo().Personal_Por_Departamento(folio_departamento);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
