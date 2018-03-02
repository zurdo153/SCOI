package Obj_Evaluaciones;

import java.sql.SQLException;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.GuardarSQL;

public class Obj_Contestacion_De_Cuestionario {

	int folio_programacion;
	String programacion;
	int folio_cuestionario;
	String cuestionario;
		
	int folio_colaborador;
	String colaborador;
	String establecimiento;
	String departamento;
	String puesto;
	String status;
	
	int valor_de_escala;
	
	String cuestionario_xml;
	Object[][] cuestionario_array;
	
	public Obj_Contestacion_De_Cuestionario() {
		folio_programacion=0;
		programacion="";
		folio_cuestionario=0;
		cuestionario="";
			
		folio_colaborador=0;
		colaborador="";
		establecimiento="";
		departamento="";
		puesto="";
		status="";
		
		valor_de_escala=0;
		
		cuestionario_xml="";
		cuestionario_array = null;
	}

	public int getFolio_programacion() {
		return folio_programacion;
	}

	public void setFolio_programacion(int folio_programacion) {
		this.folio_programacion = folio_programacion;
	}

	public String getProgramacion() {
		return programacion;
	}

	public void setProgramacion(String programacion) {
		this.programacion = programacion;
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

	public int getFolio_colaborador() {
		return folio_colaborador;
	}

	public void setFolio_colaborador(int folio_colaborador) {
		this.folio_colaborador = folio_colaborador;
	}

	public String getColaborador() {
		return colaborador;
	}

	public void setColaborador(String colaborador) {
		this.colaborador = colaborador;
	}

	public String getEstablecimiento() {
		return establecimiento;
	}

	public void setEstablecimiento(String establecimiento) {
		this.establecimiento = establecimiento;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getPuesto() {
		return puesto;
	}

	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getValor_de_escala() {
		return valor_de_escala;
	}

	public void setValor_de_escala(int valor_de_escala) {
		this.valor_de_escala = valor_de_escala;
	}

	public String getCuestionario_xml() {
		return cuestionario_xml;
	}

	public void setCuestionario_xml(String cuestionario_xml) {
		this.cuestionario_xml = cuestionario_xml;
	}

	public Object[][] getCuestionario_array() {
		return cuestionario_array;
	}

	public void setCuestionario_array(Object[][] cuestionario_array) {
		this.cuestionario_array = cuestionario_array;
	}

	public Obj_Contestacion_De_Cuestionario buscarColaborador(int folio){
		try {
			return new BuscarSQL().colaborador_para_cuestionario(folio);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean guardarCuestionario(){
		return new GuardarSQL().Guardar_Contestacion_De_Cuestionario(this);
	}
}
