package Obj_Contabilidad;

import java.sql.SQLException;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Cargar_Combo;
import Conexiones_SQL.GuardarSQL;

public class Obj_Revision_De_Programacion_de_Pago {
	float total_programacion=0;
	float total_presupuesto=0;
	float total_propuesto=0;
	String [][] tabla_programacion;

	public float getTotal_programacion() {
		return total_programacion;
	}

	public void setTotal_programacion(float total_programacion) {
		this.total_programacion = total_programacion;
	}

	public float getTotal_presupuesto() {
		return total_presupuesto;
	}

	public void setTotal_presupuesto(float total_presupuesto) {
		this.total_presupuesto = total_presupuesto;
	}

	public float getTotal_propuesto() {
		return total_propuesto;
	}

	public void setTotal_propuesto(float total_propuesto) {
		this.total_propuesto = total_propuesto;
	}

	public String[][] getTabla_programacion() {
		return tabla_programacion;
	}

	public void setTabla_programacion(String[][] tabla_programacion) {
		this.tabla_programacion = tabla_programacion;
	}

	public String[][] refrescar_tabla_programacion(String folio_programacion){
		return new BuscarSQL().Tabla_Programacion_de_pago(folio_programacion);
	}
	
	public String[][] Tabla_Programacion_de_pago_clasificadores(){
		return new BuscarSQL().Tabla_Programacion_de_pago_clasificadores();
	}
	
	public String[] combo_estatus_programacion() {
		try {
			return new Cargar_Combo().Combos("programacionestatus");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; 
	}
	
	
//	public boolean Guardar(){
//		return new GuardarSQL().Guardar_Cuadrante(this);
//	}
	
//	
//	
//	public String[][] refrescar_tabla_captura_cuadrante(String clave_checador){
//		return new BuscarSQL().TablaActividades_Cuadrante_captura(clave_checador);
//	}
//	
//	public boolean Validacion_captura_existe_cuadrante(String clave_checador){
//		return new BuscarSQL().Valida_Clave_Checador_Si_Existe_Cuadrante(clave_checador);
//	}
//	
//	public String devuelve_clave_checador(){
//		return new BuscarSQL().clave_checador();
//	}
//	
//	public boolean Guardar_Captura(){
//		return new GuardarSQL().Guardar_Captura_Cuadrante(this);
//	}
//	
//	public String[] Combo_Turnos() {
//		try {
//			return new Cargar_Combo().Combos("turnos");
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return null; 
//	}
	
}
