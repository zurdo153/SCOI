package Obj_Cuadrantes;

import java.sql.SQLException;

import Conexiones_SQL.Cargar_Combo;
import Conexiones_SQL.GuardarSQL;

public class Obj_Nivel_Critico {
	
	private int folio=0;
	private String nivel_critico="";
	private String Estatus="";
	private String NuevoModifica="";
	private int ponderacion=0;
	private int orden=0;
	
	public String getEstatus() {
		return Estatus;
	}

	public void setEstatus(String estatus) {
		Estatus = estatus;
	}

	public String getNuevoModifica() {
		return NuevoModifica;
	}

	public void setNuevoModifica(String nuevoModifica) {
		NuevoModifica = nuevoModifica;
	}

			public int getFolio() {
		return folio;
	}

	public void setFolio(int folio) {
		this.folio = folio;
	}

	public String getNivel_critico() {
		return nivel_critico;
	}

	public void setNivel_critico(String nivel_critico) {
		this.nivel_critico = nivel_critico;
	}

	public int getPonderacion() {
		return ponderacion;
	}

	public void setPonderacion(int ponderacion) {
		this.ponderacion = ponderacion;
	}

	public int getOrden() {
		return orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}

	public boolean guardar(){ return new GuardarSQL().Guardar_Nivel_Critico(this); }
			
	public String[] Combo_Nivel_Critico() {
				try {
					return new Cargar_Combo().Cuadrantes_Combos("nivel_critico");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return null; 
			}
}
