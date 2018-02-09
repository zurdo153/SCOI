package Obj_Auditoria;

import java.sql.SQLException;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.GuardarSQL;



public class Obj_Retiros_Cajeros {
	
	Integer folio_empleado;
	Integer folio_supervisor;
	
	String nombre;
	String puesto;
	String establecimiento;
	String asignacion_turno;
	String pc;
	String nombre_Supervisor;
	String existe_supervisor;
	String clave;
	String folio_retiro;
	String cantidad_asignaciones_nombreturno;
	
	float retiro_cajero;
	float  importe_total;
	
	public Integer getFolio_empleado() {
		return folio_empleado;
	}

	public void setFolio_empleado(Integer folio_empleado) {
		this.folio_empleado = folio_empleado;
	}

	public Integer getFolio_supervisor() {
		return folio_supervisor;
	}

	public void setFolio_supervisor(Integer folio_supervisor) {
		this.folio_supervisor = folio_supervisor;
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

	public String getAsignacion_turno() {
		return asignacion_turno;
	}

	public void setAsignacion_turno(String asignacion_turno) {
		this.asignacion_turno = asignacion_turno;
	}

	public String getPc() {
		return pc;
	}

	public void setPc(String pc) {
		this.pc = pc;
	}

	public String getNombre_Supervisor() {
		return nombre_Supervisor;
	}

	public void setNombre_Supervisor(String nombre_Supervisor) {
		this.nombre_Supervisor = nombre_Supervisor;
	}

	public String getExiste_supervisor() {
		return existe_supervisor;
	}

	public void setExiste_supervisor(String existe_supervisor) {
		this.existe_supervisor = existe_supervisor;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getFolio_retiro() {
		return folio_retiro;
	}

	public void setFolio_retiro(String folio_retiro) {
		this.folio_retiro = folio_retiro;
	}

	public String getCantidad_asignaciones_nombreturno() {
		return cantidad_asignaciones_nombreturno;
	}

	public void setCantidad_asignaciones_nombreturno(String cantidad_asignaciones_nombreturno) {
		this.cantidad_asignaciones_nombreturno = cantidad_asignaciones_nombreturno;
	}

	public float getRetiro_cajero() {
		return retiro_cajero;
	}

	public void setRetiro_cajero(float retiro_cajero) {
		this.retiro_cajero = retiro_cajero;
	}

	public float getImporte_total() {
		return importe_total;
	}

	public void setImporte_total(float importe_total) {
		this.importe_total = importe_total;
	}

	public Obj_Retiros_Cajeros buscarEmpleado(Integer folio_empleado){ 
		try {
			return new BuscarSQL().datos_cajero(folio_empleado);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	return null; 
	}
	
	public Obj_Retiros_Cajeros buscarEmpleado_para_ahorro_cte(Integer folio_empleado){ 
		try {
			return new BuscarSQL().datos_cajero_para_ahorro_cte(folio_empleado);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	return null; 
	}
	
	public Obj_Retiros_Cajeros buscarSupervisor(String clave){ 
		try {
			return new BuscarSQL().datos_supervisor_retiro(clave);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	return null; 
	}
	
	public boolean Guardar_Retiro_Cajeros (String Establecimiento,int Folio_empleado,int folio_supervisor,float importe_retiro,String Asignacion){
		      return new GuardarSQL().Guardar_Retiro_Cajero(Establecimiento,Folio_empleado,folio_supervisor, importe_retiro,Asignacion); 
		   }
	
	public String guardar_sesion(String Establecimiento,int Folio_empleado){
		return new GuardarSQL().Guardar_Sesion_Cajero(Establecimiento,Folio_empleado); 
		   }



}
