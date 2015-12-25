package Obj_Planeacion;
import Conexiones_SQL.GuardarSQL;

public class Obj_Actividades_De_Una_Planeacion {
	String descripcion_de_la_actividad = "";
	String Estatus_Actividad="";
	String hora_inicia = "00:00:00";
	String hora_termina= "23:59:00";
	
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
	
}
