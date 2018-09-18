package Obj_Matrices;
import Conexiones_SQL.GuardarSQL;

public class Obj_Etapas {
	private int folio;
	private String etapa;
	private String abreviatura;
	private String status;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getFolio() {
		return folio;
	}
	public void setFolio(int folio) {
		this.folio = folio;
	}
	public String getEtapa() {
		return etapa;
	}
	public void setEtapa(String etapa) {
		this.etapa = etapa;
	}
	public String getAbreviatura() {
		return abreviatura;
	}
	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}
	
	public boolean guardar(){ 
		 return new GuardarSQL().Guardar_Etapa(this); }
}
