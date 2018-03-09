package Obj_Evaluaciones;

import java.sql.SQLException;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.GuardarSQL;


public class Obj_Asignacion_De_Cuestionarios {
	private int folio;
	private String nombre_asignacion;
	private int folio_cuestionario;
	private String cuestionario;
	private String fecha_in;
	private String fecha_fin;
	private String cadena_xml;
	private Object[][] arreglo;

	private String[][] preguntas;
	
	public Obj_Asignacion_De_Cuestionarios(){
		this.folio=0; this.nombre_asignacion=""; this.folio_cuestionario=0;	
		this.fecha_in=""; this.fecha_fin=""; this.cadena_xml="";	this.arreglo = null;
	}

	public int getFolio() {
		return folio;
	}

	public void setFolio(int folio) {
		this.folio = folio;
	}

	public String getNombre_asignacion() {
		return nombre_asignacion;
	}

	public void setNombre_asignacion(String nombre_asignacion) {
		this.nombre_asignacion = nombre_asignacion;
	}

	public int getFolio_cuestionario() {
		return folio_cuestionario;
	}

	public void setFolio_cuestionario(int folio_cuestionario) {
		this.folio_cuestionario = folio_cuestionario;
	}

	public String getCuestionario() {
		return cuestionario;
	}

	public void setCuestionario(String cuestionario) {
		this.cuestionario = cuestionario;
	}

	public String getFecha_in() {
		return fecha_in;
	}

	public void setFecha_in(String fecha_in) {
		this.fecha_in = fecha_in;
	}

	public String getFecha_fin() {
		return fecha_fin;
	}

	public void setFecha_fin(String fecha_fin) {
		this.fecha_fin = fecha_fin;
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

	public Obj_Asignacion_De_Cuestionarios buscar(int folio){
		try {
			return new BuscarSQL().prog_cuest(folio);
					
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; 
	}
	
	public boolean guardar(String movimiento){ 
		return new GuardarSQL().Guardar_Asignacion_De_Cuestionario(this, movimiento);  
	}
	
	public int buscar_nuevo(){
			return new BuscarSQL().Devuelve_ultimo_folio_transaccion("88");
	}
	
}
