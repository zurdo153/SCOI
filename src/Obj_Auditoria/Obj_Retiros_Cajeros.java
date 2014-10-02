package Obj_Auditoria;

import java.sql.SQLException;

import Conexiones_SQL.BuscarSQL;

public class Obj_Retiros_Cajeros {
	
	Integer folio_empleado;
	String nombre;
	String puesto;
	String establecimiento;
	String asignacion;
	String pc;
    

	
	
	
	public Integer getFolio_empleado() {
		return folio_empleado;
	}





	public void setFolio_empleado(Integer folio_empleado) {
		this.folio_empleado = folio_empleado;
	}





	public String getNombre() {
		return nombre;
	}





	public void setNombre(String nombre) {
		this.nombre = nombre;
	}





	public String getPuesto() {
		return puesto;
	}





	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}





	public String getEstablecimiento() {
		return establecimiento;
	}





	public void setEstablecimiento(String establecimiento) {
		this.establecimiento = establecimiento;
	}





	public String getAsignacion() {
		return asignacion;
	}





	public void setAsignacion(String asignacion) {
		this.asignacion = asignacion;
	}





	public String getPc() {
		return pc;
	}





	public void setPc(String pc) {
		this.pc = pc;
	}





	public Obj_Retiros_Cajeros buscarEmpleado(Integer folio_empleado){ 
		try {
			return new BuscarSQL().datos_cajero(folio_empleado);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	return null; 
	}
	



}
