package Obj_Planeacion;
import Conexiones_SQL.GuardarSQL;

public class Obj_Actividades_De_Una_Planeacion {
	int folio=0;
	String descripcion_de_la_actividad = "";
	String Estatus_Actividad="";
	String hora_inicia = "00:00:00";
	String hora_termina= "23:59:00";
	String guardar_actualizar= "";
	String status_actividad="VIGENTE";
	
	public String getStatus_actividad() {
		return status_actividad;
	}

	public void setStatus_actividad(String status_actividad) {
		this.status_actividad = status_actividad;
	}

	public String getGuardar_actualizar() {
		return guardar_actualizar;
	}

	public void setGuardar_actualizar(String guardar_actualizar) {
		this.guardar_actualizar = guardar_actualizar;
	}

	public int getFolio() {
		return folio;
	}

	public void setFolio(int folio) {
		this.folio = folio;
	}

	public String getEstatus_Actividad() {
		return Estatus_Actividad;
	}
	
	public void setEstatus_Actividad(String estatus_Actividad) {
		Estatus_Actividad = estatus_Actividad;
	}
	
	public String getHora_inicia() {
		return hora_inicia;
	}

	public void setHora_inicia(String hora_inicia) {
		this.hora_inicia = hora_inicia;
	}

	public String getHora_termina() {
		return hora_termina;
	}

	public void setHora_termina(String hora_termina) {
		this.hora_termina = hora_termina;
	}

	public String getDescripcion_de_la_actividad() {
		return descripcion_de_la_actividad;
	}

	public void setDescripcion_de_la_actividad(String descripcion_de_la_actividad) {
		this.descripcion_de_la_actividad = descripcion_de_la_actividad;
	}

	public boolean guardar(Obj_Opciones_De_Respuesta opRespuesta, Obj_Prioridad_Y_Ponderacion opPonderacion, Obj_Seleccion_De_Usuarios usuarios, Obj_Frecuencia_De_Actividades frecuencia, int folio_empleado){
	        return new GuardarSQL().Guardar_Actividad_Planeacion(this,opRespuesta,opPonderacion,usuarios,frecuencia, folio_empleado); }
	
	public boolean guardar_cuadrantes_actividad_jerarquico(Obj_Opciones_De_Respuesta opRespuesta, Obj_Prioridad_Y_Ponderacion opPonderacion, Obj_Seleccion_De_Usuarios usuarios, Obj_Frecuencia_De_Actividades frecuencia, int folio_empleado){
        return new GuardarSQL().Guardar_Actividad_de_cuadrante_jerarquico(this,opRespuesta,opPonderacion,usuarios,frecuencia, folio_empleado); }
}
