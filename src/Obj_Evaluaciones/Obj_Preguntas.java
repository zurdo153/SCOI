package Obj_Evaluaciones;

import java.sql.SQLException;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Cargar_Combo;
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

	public String[] Combo_Jefatura(){ 
		try {
			return new Cargar_Combo().jefatu("tb_Jefatura");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; 
	}
	
	
	
	public String[] Combo_Puesto(){ 
		try {
			return new Cargar_Combo().Puesto("tb_puesto");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; 
	}
	
	public String[] Combo_Nivel_De_Puesto(){ 
		try {
			return new Cargar_Combo().Nivel_De_Puesto("nivel_de_puestos");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; 
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
