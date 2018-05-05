package Obj_Punto_De_Venta;

import Conexiones_SQL.GuardarSQL;

public class Obj_Clientes_Ventas {

	int    folio              = 0  ;
	String nombre             = "" ;
	String ap_paterno         = "" ;
	String ap_materno         = "" ;
	String domicilio          = "" ;
	String telefono           = "" ;
	String Guardar_actualizar = "" ;
	String estatus            = "" ;
	
	public String getEstatus() {
		return estatus;
	}
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
	public String getGuardar_actualizar() {
		return Guardar_actualizar;
	}
	public void setGuardar_actualizar(String guardar_actualizar) {
		Guardar_actualizar = guardar_actualizar;
	}
	public int getFolio() {
		return folio;
	}
	public void setFolio(int folio) {
		this.folio = folio;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getAp_paterno() {
		return ap_paterno;
	}
	public void setAp_paterno(String ap_paterno) {
		this.ap_paterno = ap_paterno;
	}
	public String getAp_materno() {
		return ap_materno;
	}
	public void setAp_materno(String ap_materno) {
		this.ap_materno = ap_materno;
	}
	public String getDomicilio() {
		return domicilio;
	}
	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}	
	

	public Obj_Clientes_Ventas GuardarActualizar(){ 
	return new GuardarSQL().Guardar_Venta_Express(this); 
	}
	
}
