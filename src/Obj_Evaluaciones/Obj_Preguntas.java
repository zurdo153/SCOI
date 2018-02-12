package Obj_Evaluaciones;

import java.sql.SQLException;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.GuardarSQL;

public class Obj_Preguntas {
	private int folio;
	private String pregunta;
	private String status;

	public Obj_Preguntas(){
		this.folio=0; this.pregunta=""; this.status="";
	}

	public int getFolio() {
		return folio;
	}

	public void setFolio(int folio) {
		this.folio = folio;
	}

	public String getPregunta() {
		return pregunta;
	}

	public void setPregunta(String pregunta) {
		this.pregunta = pregunta;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Obj_Preguntas buscar(int folio){
		try {
			return new BuscarSQL().Preguntas(folio);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; 
	}
	
	public boolean guardar(String movimiento){ return new GuardarSQL().Guardar_Pregunta(this, movimiento); }
	
	public int buscar_nuevo(){
			return new BuscarSQL().Devuelve_ultimo_folio_transaccion("82");
	}
	
}
