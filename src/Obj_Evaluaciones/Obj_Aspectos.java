package Obj_Evaluaciones;

import java.sql.SQLException;

import Conexiones_SQL.Cargar_Combo;
import Conexiones_SQL.GuardarSQL;

public class Obj_Aspectos {
	
	private int folio=0;
	private int Orden=0;
	private int Ponderacion=0;
	private String Aspecto="";
	private String Estatus="";
	private String GuardaModifica="";
	
	public String getGuardaModifica() {
		return GuardaModifica;
	}

	public void setGuardaModifica(String guardaModifica) {
		GuardaModifica = guardaModifica;
	}

	public int getFolio() {
		return folio;
	}

	public void setFolio(int folio) {
		this.folio = folio;
	}

	public int getOrden() {
		return Orden;
	}

	public void setOrden(int orden) {
		Orden = orden;
	}

	public int getPonderacion() {
		return Ponderacion;
	}

	public void setPonderacion(int ponderacion) {
		Ponderacion = ponderacion;
	}

	public String getAspecto() {
		return Aspecto;
	}

	public void setAspecto(String aspecto) {
		Aspecto = aspecto;
	}

	public String getEstatus() {
		return Estatus;
	}

	public void setEstatus(String estatus) {
		Estatus = estatus;
	}

	public boolean guardar(){ return new GuardarSQL().Guardar_Aspectos(this); }
			
	public String[] Combo_Aspecto() {
	  try {return new Cargar_Combo().Cuadrantes_Combos("aspecto");
		   } catch (SQLException e) {
			e.printStackTrace();
		   }
			return null; 
			}
}
