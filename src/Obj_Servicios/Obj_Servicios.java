package Obj_Servicios;

import java.sql.SQLException;
import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Cargar_Combo;

public class Obj_Servicios {
	String tipo_de_frecuencia = "UNA VEZ";
	boolean seleccion_hasta_que_se_cumpla = true;
	int mes1=1;
	String nivel_de_dias="Primer";
	String hora_frecuencia_diaria="12:00:00";	
//	Duracion
	String fecha_inicio_duracion=cargar_fechas(0);

	
	public String[] Prioridad(){
		try {
			return new Cargar_Combo().Prioridad();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	return null; }
	
	public String cargar_fechas(int dias){
		String date = null;
	    	try {
				date = new BuscarSQL().fecha(dias);
				} catch (SQLException e) {
					// catch block
					e.printStackTrace();
					}
		return date;
	};
}
