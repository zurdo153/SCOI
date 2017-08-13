package Obj_Evaluaciones;

import java.sql.SQLException;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.BuscarTablasModel;
import Conexiones_SQL.GuardarSQL;

public class Obj_Actividad {
	 int folio=0;
	 String Actividad="";
	 String Descripcion="";
	 String Respuesta="";
	 String Aspecto= "";
	 String Nivel_Critico="";
	 String Temporada="";
	 String Exige_Evidencia="";
	 String Exige_Observacion="";
	 String Estatus="";
	 String Genera_Alerta=""; 
	 int tolerancia_minutos=0; 
	 String NuevoModifica="";
	
	public int getFolio() {
		return folio;
	}


	public void setFolio(int folio) {
		this.folio = folio;
	}


	public String getActividad() {
		return Actividad;
	}


	public void setActividad(String actividad) {
		Actividad = actividad;
	}


	public String getDescripcion() {
		return Descripcion;
	}


	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}


	public String getRespuesta() {
		return Respuesta;
	}


	public void setRespuesta(String respuesta) {
		Respuesta = respuesta;
	}


	public String getAspecto() {
		return Aspecto;
	}


	public void setAspecto(String aspecto) {
		Aspecto = aspecto;
	}


	public String getNivel_Critico() {
		return Nivel_Critico;
	}


	public void setNivel_Critico(String nivel_Critico) {
		Nivel_Critico = nivel_Critico;
	}


	public String getTemporada() {
		return Temporada;
	}


	public void setTemporada(String temporada) {
		Temporada = temporada;
	}


	public String getExige_Evidencia() {
		return Exige_Evidencia;
	}


	public void setExige_Evidencia(String exige_Evidencia) {
		Exige_Evidencia = exige_Evidencia;
	}


	public String getExige_Observacion() {
		return Exige_Observacion;
	}


	public void setExige_Observacion(String exige_Observacion) {
		Exige_Observacion = exige_Observacion;
	}


	public String getEstatus() {
		return Estatus;
	}


	public void setEstatus(String estatus) {
		Estatus = estatus;
	}


	public String getGenera_Alerta() {
		return Genera_Alerta;
	}


	public void setGenera_Alerta(String genera_Alerta) {
		Genera_Alerta = genera_Alerta;
	}


	public int getTolerancia_minutos() {
		return tolerancia_minutos;
	}


	public void setTolerancia_minutos(int tolerancia_minutos) {
		this.tolerancia_minutos = tolerancia_minutos;
	}


	public String getNuevoModifica() {
		return NuevoModifica;
	}


	public void setNuevoModifica(String nuevoModifica) {
		NuevoModifica = nuevoModifica;
	}


	public int Nuevo(){
		return new BuscarSQL().Devuelve_ultimo_folio_transaccion("73");
	}
	
	
	public boolean Guardar(){
		return new GuardarSQL().Guardar_Actividad(this);
	}
	
	public Obj_Actividad Buscar(int folio){
		try {
			return new BuscarSQL().Buscar_Actividad(folio);		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	return null; 
	}
	
	public Object[][] filtro(){
		return new BuscarTablasModel().filtro_actividad();
	}

}
