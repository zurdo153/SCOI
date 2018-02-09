package Obj_Auditoria;

import Conexiones_SQL.ActualizarSQL;
import Conexiones_SQL.GuardarSQL;

public class Obj_Alimentacion_Denominacion {
	String folio_corte;
	String empleado;
	String establecimiento;
	
	public Obj_Alimentacion_Denominacion(){
		this.folio_corte=""; this.empleado=""; 
		this.establecimiento="";
	}

	public String getFolio_corte() {
		return folio_corte;
	}

	public void setFolio_corte(String folio_corte) {
		this.folio_corte = folio_corte;
	}

	
	public String getEmpleado() {
		return empleado;
	}

	public void setEmpleado(String empleado) {
		this.empleado = empleado;
	}

	public String getEstablecimiento() {
		return establecimiento;
	}

	public void setEstablecimiento(String establecimiento) {
		this.establecimiento = establecimiento;
	}
	
//	denominacion
	public boolean guardar(int folio_usuario,Object[][] tabla){ 
		return new GuardarSQL().Guardar_Alimentacion_denominacio(this, folio_usuario, tabla);
	}
//	denominacion	
	public boolean actualizar(int folio_usuario, Object[][] tabla){ 
		return new ActualizarSQL().Actualizar_Alimentacion_Efectivo(this, folio_usuario, tabla);
	}
	
//	deposito
	public boolean guardar_deposito(int folio_usuario, Object[][] tabla){ 
		return new GuardarSQL().Guardar_Alimentacion_deposito(this, folio_usuario, tabla);
	}
//	deposito	
	public boolean actualizar_deposito(int folio_usuario, Object[][] tabla){ 
		return new ActualizarSQL().Actualizar_Alimentacion_deposito(this, folio_usuario, tabla);
	}
}
