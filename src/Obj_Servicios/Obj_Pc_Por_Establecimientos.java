package Obj_Servicios;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.GuardarSQL;


public class Obj_Pc_Por_Establecimientos {
	int folio=0;
		
	String nombre_pc="";
	String establecimiento="";
	String estatus="";
	String servicio="";
	String getNuevoModifica="";

	public int getFolio() {
		return folio;
	}

	public void setFolio(int folio) {
		this.folio = folio;
	}

	public String getNombre_pc() {
		return nombre_pc;
	}

	public void setNombre_pc(String nombre_pc) {
		this.nombre_pc = nombre_pc;
	}

	public String getEstablecimiento() {
		return establecimiento;
	}

	public void setEstablecimiento(String establecimiento) {
		this.establecimiento = establecimiento;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public String getServicio() {
		return servicio;
	}

	public void setServicio(String servicio) {
		this.servicio = servicio;
	}

	public String getGetNuevoModifica() {
		return getNuevoModifica;
	}

	public void setGetNuevoModifica(String getNuevoModifica) {
		this.getNuevoModifica = getNuevoModifica;
	}

	public boolean Guardar(){
	return new GuardarSQL().Guardar_PC_Por_Establecimiento(this); }
	
	public int Nuevo(){
		return new BuscarSQL().Devuelve_ultimo_folio_transaccion("75");
	}
}
