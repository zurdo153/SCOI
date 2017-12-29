package Obj_Servicios;

import java.sql.SQLException;

import Conexiones_SQL.Cargar_Combo;
import Conexiones_SQL.GuardarSQL;

public class Obj_Marcas_De_Activos {
	
	private int folio=0;
	private String Marca_De_Activo="";
	private String Estatus="";
	private String GuardaModifica="";
	public int getFolio() {
		return folio;
	}
	public void setFolio(int folio) {
		this.folio = folio;
	}
	public String getMarca_De_Activo() {
		return Marca_De_Activo;
	}
	public void setMarca_De_Activo(String marca_De_Activo) {
		Marca_De_Activo = marca_De_Activo;
	}
	public String getEstatus() {
		return Estatus;
	}
	public void setEstatus(String estatus) {
		Estatus = estatus;
	}
	public String getGuardaModifica() {
		return GuardaModifica;
	}
	public void setGuardaModifica(String guardaModifica) {
		GuardaModifica = guardaModifica;
	}

	public boolean guardar(){ return new GuardarSQL().Guardar_Marca_De_Activo(this); }
			
	public String[] Combo_Marcas() {
		try {return new Cargar_Combo().Servicios_Combos("marcas");
		   } catch (SQLException e) {
			e.printStackTrace();
		   }
			return null; 
			}
}
