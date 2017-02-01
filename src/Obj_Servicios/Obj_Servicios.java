package Obj_Servicios;

import java.sql.SQLException;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Cargar_Combo;
import Conexiones_SQL.GuardarSQL;

public class Obj_Servicios {
	int folio=0;
	int dias_estimado=0;
	int usuario=0;
	int usuario_modifico=0;
	String prioridad="";
	String departamento="";
	String establecimiento="";
	String servicio="";
	String detalle="";
	String tiempo_estimado="";
	String estatus="";
	String fecha=""; 
	String fecha_ultima_modificacion="";
    String guardar_actualizar="";
    		
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

	public int getDias_estimado() {
		return dias_estimado;
	}

	public void setDias_estimado(int dias_estimado) {
		this.dias_estimado = dias_estimado;
	}

	public int getUsuario() {
		return usuario;
	}

	public void setUsuario(int usuario) {
		this.usuario = usuario;
	}

	public int getUsuario_modifico() {
		return usuario_modifico;
	}

	public void setUsuario_modifico(int usuario_modifico) {
		this.usuario_modifico = usuario_modifico;
	}

	public String getPrioridad() {
		return prioridad;
	}

	public void setPrioridad(String prioridad) {
		this.prioridad = prioridad;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getEstablecimiento() {
		return establecimiento;
	}

	public void setEstablecimiento(String establecimiento) {
		this.establecimiento = establecimiento;
	}

	public String getServicio() {
		return servicio;
	}

	public void setServicio(String servicio) {
		this.servicio = servicio;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public String getTiempo_estimado() {
		return tiempo_estimado;
	}

	public void setTiempo_estimado(String tiempo_estimado) {
		this.tiempo_estimado = tiempo_estimado;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getFecha_ultima_modificacion() {
		return fecha_ultima_modificacion;
	}

	public void setFecha_ultima_modificacion(String fecha_ultima_modificacion) {
		this.fecha_ultima_modificacion = fecha_ultima_modificacion;
	}

	public String[] Prioridad(){
		try {
			return new Cargar_Combo().Prioridad();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	return null; }
	
	public String cargar_fechas(int dias){
		String date = null;
	    	try {
				date = new BuscarSQL().fecha(dias);
				} catch (SQLException e) {
					// catch block
					e.printStackTrace();
					}
		return date;
	};
	
	public boolean GuardarActualizar(){ 
		return new GuardarSQL().Guardar_servicios_catalogo(this); }
	
	
	public int buscar_nuevo(){
		try {return new BuscarSQL().serviciocatalogo();
		}catch (SQLException e) {e.printStackTrace();}
		return 0; 
	}
	
	public Obj_Servicios buscar_Departamento(int folio){
		try {
			return new BuscarSQL().serviciodepartamento(folio);
		}catch (SQLException e) {e.printStackTrace();}
		return null; 
	}
	
	
}
