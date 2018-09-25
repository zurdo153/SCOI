package Obj_Matrices;

import Conexiones_SQL.GuardarSQL;

public class Obj_Matrices_Conceptos_Etapas_UnidadDeInspeccion {
	
	private int folio=0;
	private String Descripcion="";
	private String Abreviatura="";
	private String Estatus="";
	private String Catalogo="";
	private String GuardaModifica="";
	
	public int getFolio() {
		return folio;
	}

	public void setFolio(int folio) {
		this.folio = folio;
	}

	public String getDescripcion() {
		return Descripcion;
	}

	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}

	public String getAbreviatura() {
		return Abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		Abreviatura = abreviatura;
	}

	public String getEstatus() {
		return Estatus;
	}

	public void setEstatus(String estatus) {
		Estatus = estatus;
	}

	public String getCatalogo() {
		return Catalogo;
	}

	public void setCatalogo(String catalogo) {
		Catalogo = catalogo;
	}

	public String getGuardaModifica() {
		return GuardaModifica;
	}

	public void setGuardaModifica(String guardaModifica) {
		GuardaModifica = guardaModifica;
	}

	public boolean guardar(){ return new GuardarSQL().Guardar_Matrices_Conceptos_Etapas_UnidadDeInspeccion(this); }
			
//	public String[] Combo_Aspecto() {
//	  try {return new Cargar_Combo().Combos("aspecto");
//		   } catch (SQLException e) {
//			e.printStackTrace();
//		   }
//			return null; 
//			}
}
