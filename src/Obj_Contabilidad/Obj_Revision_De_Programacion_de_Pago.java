package Obj_Contabilidad;

import java.sql.SQLException;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Cargar_Combo;

public class Obj_Revision_De_Programacion_de_Pago {
	
	int folio_programacion;
	int folio_colaborador;
	
	String Turno;
	String [][] tabla_programacion;
	 
	public Obj_Revision_De_Programacion_de_Pago(){
		this.folio_programacion=0;
		this.folio_colaborador=0;
		this.Turno="";
		
		this.tabla_programacion=null;
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
