package Obj_Servicios;

import java.sql.SQLException;

import Conexiones_SQL.Cargar_Combo;
import Conexiones_SQL.GuardarSQL;

public class Obj_Modelos_De_Activos {
	
	private int folio=0;
	private String Modelo_De_Activo="";
	private String Estatus="";
	private String GuardaModifica="";

	public int getFolio() {
		return folio;
	}

	public void setFolio(int folio) {
		this.folio = folio;
	}

	public String getModelo_De_Activo() {
		return Modelo_De_Activo;
	}

	public void setModelo_De_Activo(String modelo_De_Activo) {
		Modelo_De_Activo = modelo_De_Activo;
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

	public boolean guardar(){ return new GuardarSQL().Guardar_Modelo_De_Activo(this); }
			
	public String[] Combo_Modelos() {
		try {return new Cargar_Combo().Servicios_Combos("modelos");
		   } catch (SQLException e) {
			e.printStackTrace();
		   }
			return null; 
			}
}
