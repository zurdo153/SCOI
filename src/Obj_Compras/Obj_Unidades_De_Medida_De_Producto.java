package Obj_Compras;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.GuardarSQL;



public class Obj_Unidades_De_Medida_De_Producto {
	private int folio;
	private String Unidad;
	private String Estatus;
	
	public int getFolio() {
		return folio;
	}
	public void setFolio(int folio) {
		this.folio = folio;
	}
	public String getUnidad() {
		return Unidad;
	}
	public void setUnidad(String unidad) {
		Unidad = unidad;
	}
	public String getEstatus() {
		return Estatus;
	}
	public void setEstatus(String estatus) {
		Estatus = estatus;
	}
	
	

	public Obj_Unidades_De_Medida_De_Producto buscar(int folio) {
	return new BuscarSQL().existe_unidad_de_medida(folio);	}
	
	public boolean guardar(){ 
		 return new GuardarSQL().Guardar_Unidades_De_Medida_De_Producto(this); }
	
	
}
