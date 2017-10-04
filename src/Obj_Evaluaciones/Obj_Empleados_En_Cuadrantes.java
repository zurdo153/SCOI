package Obj_Evaluaciones;

import java.sql.SQLException;

import Conexiones_SQL.ActualizarSQL;
import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.GuardarSQL;

public class Obj_Empleados_En_Cuadrantes {
	private int folio;
	private String cuadrante;
	private boolean status;

	
	public Obj_Empleados_En_Cuadrantes(){
		this.folio=0; this.cuadrante=""; this.status=false;
	}
	
	public int getFolio() {
		return folio;
	}

	public void setFolio(int folio) {
		this.folio = folio;
	}

	public String getCuadrante() {
		return cuadrante;
	}

	public void setCuadrante(String cuadrante) {
		this.cuadrante = cuadrante;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Obj_Empleados_En_Cuadrantes buscar(int folio){
		try {
			return new BuscarSQL().buscar_empleado_cuadrante(folio);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; 
	}
	
	public Obj_Empleados_En_Cuadrantes buscar_cuadrante(int folio){
		try {
			return new BuscarSQL().buscar_cuadrante_con_empleado(folio);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; 
	}
	
	public int nuevoEmpleadoCuadrante(){
		try {
			return new BuscarSQL().NuevoEmpleadoCuadrante();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}
	
	public boolean existe(int folio){
		try {
			return new BuscarSQL().existeEmpleadoCuadrante(folio);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public boolean guardar(String[] lista){
		boolean registro = new GuardarSQL().EmpleadoCuadrante(this);
		boolean tabla = new GuardarSQL().EmpleadoCuadranteTabla(this,lista);
		
		if(registro == true && tabla == true){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean actualizar(String[] tabla){
		return new ActualizarSQL().EmpleadoCuadrante(this,tabla);
	}
	
}