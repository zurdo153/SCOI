package Obj_Marketing;

import Conexiones_SQL.GuardarSQL;

public class Obj_Alimentacion_De_Meta_Mensual_De_Venta {
	
	int anios = 0;
	String mes = "";
	String establecimiento = "";
	int	folio = 0;
	double meta_mensual = 0; 
	
	public Obj_Alimentacion_De_Meta_Mensual_De_Venta() {/*Constructor*/}

	public int getAnios() {
		return anios;
	}

	public void setAnios(int anios) {
		this.anios = anios;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public String getEstablecimiento() {
		return establecimiento;
	}

	public void setEstablecimiento(String establecimiento) {
		this.establecimiento = establecimiento;
	}

	public int getFolio() {
		return folio;
	}

	public void setFolio(int folio) {
		this.folio = folio;
	}

	public double getMeta_mensual() {
		return meta_mensual;
	}

	public void setMeta_mensual(double meta_mensual) {
		this.meta_mensual = meta_mensual;
	}
	
	public boolean guardar_meta_mensual_de_venta(String mov){
		return new GuardarSQL().Guardar_Meta_Mensual_De_Venta(this, mov);
	}

}
