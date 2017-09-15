package Obj_Servicios;

import java.sql.SQLException;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.GuardarSQL;

public class Obj_Servicios {
	int folio=0;
	int folio_usuario_solicito=0;
	int folio_usuario_modifico=0;
	int folio_equipo=0;
	int cantidad_de_correos=0;
	int usuario_realizo_servicio=0;
    int colaborador_asignado=0; 
	float costo=0;
	
	String prioridad="";
	String equipo="";
	String servicio="";
	String departamento_solicito="";
	String establecimiento_solicito="";
    String detalle="";
	String estatus_equipo="";
	String estatus="";
	String Guardar_actualizar="";
	String notas_servicio="";
	String correos="";
	String fecha_guardado="";
	String adjunto="";
	String evaluacion="";
	String comentario_evaluacion="";

	public int getColaborador_asignado() {
		return colaborador_asignado;
	}

	public void setColaborador_asignado(int colaborador_asignado) {
		this.colaborador_asignado = colaborador_asignado;
	}

	public int getFolio_equipo() {
		return folio_equipo;
	}

	public void setFolio_equipo(int folio_equipo) {
		this.folio_equipo = folio_equipo;
	}

	public String getComentario_evaluacion() {
		return comentario_evaluacion;
	}

	public void setComentario_evaluacion(String comentario_evaluacion) {
		this.comentario_evaluacion = comentario_evaluacion;
	}

	public int getFolio() {
		return folio;
	}

	public void setFolio(int folio) {
		this.folio = folio;
	}

	public int getFolio_usuario_solicito() {
		return folio_usuario_solicito;
	}

	public void setFolio_usuario_solicito(int folio_usuario_solicito) {
		this.folio_usuario_solicito = folio_usuario_solicito;
	}

	public int getFolio_usuario_modifico() {
		return folio_usuario_modifico;
	}

	public void setFolio_usuario_modifico(int folio_usuario_modifico) {
		this.folio_usuario_modifico = folio_usuario_modifico;
	}

	public int getCantidad_de_correos() {
		return cantidad_de_correos;
	}

	public void setCantidad_de_correos(int cantidad_de_correos) {
		this.cantidad_de_correos = cantidad_de_correos;
	}

	public int getUsuario_realizo_servicio() {
		return usuario_realizo_servicio;
	}

	public void setUsuario_realizo_servicio(int usuario_realizo_servicio) {
		this.usuario_realizo_servicio = usuario_realizo_servicio;
	}

	public float getCosto() {
		return costo;
	}

	public void setCosto(float costo) {
		this.costo = costo;
	}

	public String getPrioridad() {
		return prioridad;
	}

	public void setPrioridad(String prioridad) {
		this.prioridad = prioridad;
	}

	public String getEquipo() {
		return equipo;
	}

	public void setEquipo(String equipo) {
		this.equipo = equipo;
	}

	public String getServicio() {
		return servicio;
	}

	public void setServicio(String servicio) {
		this.servicio = servicio;
	}

	public String getDepartamento_solicito() {
		return departamento_solicito;
	}

	public void setDepartamento_solicito(String departamento_solicito) {
		this.departamento_solicito = departamento_solicito;
	}

	public String getEstablecimiento_solicito() {
		return establecimiento_solicito;
	}

	public void setEstablecimiento_solicito(String establecimiento_solicito) {
		this.establecimiento_solicito = establecimiento_solicito;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public String getEstatus_equipo() {
		return estatus_equipo;
	}

	public void setEstatus_equipo(String estatus_equipo) {
		this.estatus_equipo = estatus_equipo;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public String getGuardar_actualizar() {
		return Guardar_actualizar;
	}

	public void setGuardar_actualizar(String guardar_actualizar) {
		Guardar_actualizar = guardar_actualizar;
	}

	public String getNotas_servicio() {
		return notas_servicio;
	}

	public void setNotas_servicio(String notas_servicio) {
		this.notas_servicio = notas_servicio;
	}

	public String getCorreos() {
		return correos;
	}

	public void setCorreos(String correos) {
		this.correos = correos;
	}

	public String getFecha_guardado() {
		return fecha_guardado;
	}

	public void setFecha_guardado(String fecha_guardado) {
		this.fecha_guardado = fecha_guardado;
	}

	public String getAdjunto() {
		return adjunto;
	}

	public void setAdjunto(String adjunto) {
		this.adjunto = adjunto;
	}

	public String getEvaluacion() {
		return evaluacion;
	}

	public void setEvaluacion(String evaluacion) {
		this.evaluacion = evaluacion;
	}

	public int GuardarActualizar(){ 
	return new GuardarSQL().Guardar_servicios(this); }
	
	public boolean GuardarAsignacion(){ 
	return new GuardarSQL().Guardar_Asignacion(this); }
	
	public String Validad_PC_Guarda(String Establecimiento, String Departamento) throws SQLException{ 
	return new BuscarSQL().Valida_La_PC_Para_Captura(Establecimiento,Departamento);
	}
	
	public int buscar_nuevo(){
		try {return new BuscarSQL().serviciocatalogo();
		}catch (SQLException e) {e.printStackTrace();}
		return 0; 
	}
}
