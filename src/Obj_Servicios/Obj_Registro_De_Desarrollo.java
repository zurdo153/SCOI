package Obj_Servicios;

import java.sql.SQLException;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.GuardarSQL;

public class Obj_Registro_De_Desarrollo {

	int folio_registro=0;
	int usuario_solicitante=0;
	int usuario_atendio=0;
	String mejoras = "";
	String funcionalidad = "";
	String sugerencias = "";
	Object[][] afectados= null;
			
	public Obj_Registro_De_Desarrollo() {
	}

	public int getFolio_registro() {
		return folio_registro;
	}

	public void setFolio_registro(int folio_registro) {
		this.folio_registro = folio_registro;
	}

	public int getUsuario_solicitante() {
		return usuario_solicitante;
	}

	public void setUsuario_solicitante(int usuario_solicitante) {
		this.usuario_solicitante = usuario_solicitante;
	}

	public int getUsuario_atendio() {
		return usuario_atendio;
	}

	public void setUsuario_atendio(int usuario_atendio) {
		this.usuario_atendio = usuario_atendio;
	}

	public String getMejoras() {
		return mejoras;
	}

	public void setMejoras(String mejoras) {
		this.mejoras = mejoras;
	}

	public String getFuncionalidad() {
		return funcionalidad;
	}

	public void setFuncionalidad(String funcionalidad) {
		this.funcionalidad = funcionalidad;
	}

	public String getSugerencias() {
		return sugerencias;
	}

	public void setSugerencias(String sugerencias) {
		this.sugerencias = sugerencias;
	}

	public Object[][] getAfectados() {
		return afectados;
	}

	public void setAfectados(Object[][] afectados) {
		this.afectados = afectados;
	}

	public boolean guardar_solicitud(String movimiento){
		return new GuardarSQL().Guardar_Registro_De_Desarollo(this,movimiento);
	}
	public Obj_Registro_De_Desarrollo buscar_Registro(int folio_registro){
		try {
			new BuscarSQL().buscar_respuesta_folio(folio_registro);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
