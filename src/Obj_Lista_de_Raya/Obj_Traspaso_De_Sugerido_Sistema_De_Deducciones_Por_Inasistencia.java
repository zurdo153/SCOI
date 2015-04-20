package Obj_Lista_de_Raya;

import java.sql.SQLException;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.GuardarTablasModel;

public class Obj_Traspaso_De_Sugerido_Sistema_De_Deducciones_Por_Inasistencia {
	Object[][] tabla_model;
	
	public Obj_Traspaso_De_Sugerido_Sistema_De_Deducciones_Por_Inasistencia() {
		this.tabla_model = null;
	}

//	public Object[][] getTabla_model() {
//		return tabla_model;
//	}

	public void setTabla_model(Object[][] tabla_model) {
		this.tabla_model = tabla_model;
	}
	public boolean guardar(Object[][] tabla){
		return new GuardarTablasModel().tabla_model_inasistencia(tabla);
	}
	
	
	
	
	public String[][] buscar_datos_sugerido_inasistencia()throws SQLException{
		return new BuscarSQL().llenar_tabla_deduccion_inasistencia_sugerido_sistema(this);
	}
	

}
