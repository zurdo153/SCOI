package Obj_Evaluaciones;

import java.sql.SQLException;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Cargar_Combo;
import Conexiones_SQL.GuardarSQL;

public class Obj_Cuestionarios {
	private int folio;
	private String cuestionario;
	private String clasificacion;
	private String escala;
	private String status;
	private String cadena_xml;
	private Object[][] arreglo;

	private String[][] preguntas;
	
	public Obj_Cuestionarios(){
		this.folio=0; this.cuestionario=""; this.status="";
		this.clasificacion=""; this.escala=""; this.status=""; this.cadena_xml="";
		this.arreglo = null;
	}

	public int getFolio() {
		return folio;
	}

	public void setFolio(int folio) {
		this.folio = folio;
	}

	public String getCuestionario() {
		return cuestionario;
	}

	public void setCuestionario(String cuestionario) {
		this.cuestionario = cuestionario;
	}

	public String getClasificacion() {
		return clasificacion;
	}

	public void setClasificacion(String clasificacion) {
		this.clasificacion = clasificacion;
	}

	public String getEscala() {
		return escala;
	}

	public void setEscala(String escala) {
		this.escala = escala;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCadena_xml() {
		return cadena_xml;
	}

	public void setCadena_xml(String cadena_xml) {
		this.cadena_xml = cadena_xml;
	}

	public Object[][] getArreglo() {
		return arreglo;
	}

	public void setArreglo(Object[][] arreglo) {
		this.arreglo = arreglo;
	}

	public String[][] getPreguntas() {
		return preguntas;
	}

	public void setPreguntas(String[][] preguntas) {
		this.preguntas = preguntas;
	}

	public String[] clasificaciones(){
		return new Cargar_Combo().Combo_Clasificaciones();
	}
	
	public String[] escalas(){
		return new Cargar_Combo().Combo_Escalas();
	}
	
	public Obj_Cuestionarios buscar(int folio){
		try {
			return new BuscarSQL().cuestionario(folio);
					
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; 
	}
	
	public boolean guardar(String movimiento){ 
		return new GuardarSQL().Guardar_Cuestionario(this, movimiento);  
	}
	
	public int buscar_nuevo(){
			return new BuscarSQL().Devuelve_ultimo_folio_transaccion("83");
	}
	
}
