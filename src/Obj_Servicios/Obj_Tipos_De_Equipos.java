package Obj_Servicios;

import java.sql.SQLException;

import Conexiones_SQL.Cargar_Combo;
import Conexiones_SQL.GuardarSQL;

public class Obj_Tipos_De_Equipos {
	
	private int folio=0;
	private String Tipos_De_Equipo="";
	private String Serie="";
	private String Estatus="";
	private String GuardaModifica="";

	public int getFolio() {
		return folio;
	}

	public void setFolio(int folio) {
		this.folio = folio;
	}

	public String getTipos_De_Equipo() {
		return Tipos_De_Equipo;
	}

	public void setTipos_De_Equipo(String tipos_De_Equipo) {
		Tipos_De_Equipo = tipos_De_Equipo;
	}

	public String getSerie() {
		return Serie;
	}

	public void setSerie(String serie) {
		Serie = serie;
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

	public boolean guardar(){ return new GuardarSQL().Guardar_Tipos_De_Activo(this); }
			
	public String[] Combo_Tipos_De_Equipo() {
		try {return new Cargar_Combo().Combos("tipos");
		} catch (SQLException e) {e.printStackTrace();}
		return null; 
	    }
}
