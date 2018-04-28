package Obj_Cuadrantes;

import java.sql.SQLException;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Cargar_Combo;
import Conexiones_SQL.GuardarSQL;

public class Obj_Cuadrantes {
	
	int folio;
	int folio_colaborador;
	int folio_cuadrante;
	String cuadrante;
	String establecimiento;
	String departamento;
	String puesto;
	String puesto_reporta;
	String objetivo;
	String responsabilidades;
	String estatus;
	String NuevoModifica;
	String Turno;
	String [][] tabla_actividades;
	 
	public Obj_Cuadrantes(){
		this.folio=0;
		this.folio_colaborador=0;
		this.folio_cuadrante=0;
		this.cuadrante="";
		this.establecimiento="";
		this.departamento="";
		this.puesto="";
		this.puesto_reporta="";
		this.objetivo="";
		this.responsabilidades="";
		this.estatus="";
		this.NuevoModifica="";
		this.tabla_actividades=null;
	}

	public String getTurno() {
		return Turno;
	}

	public void setTurno(String turno) {
		Turno = turno;
	}

	public int getFolio_cuadrante() {
		return folio_cuadrante;
	}

	public void setFolio_cuadrante(int folio_cuadrante) {
		this.folio_cuadrante = folio_cuadrante;
	}

	public int getFolio_colaborador() {
		return folio_colaborador;
	}

	public void setFolio_colaborador(int folio_colaborador) {
		this.folio_colaborador = folio_colaborador;
	}

	public String[][] getTabla_actividades() {
		return tabla_actividades;
	}

	public void setTabla_actividades(String[][] tabla_actividades) {
		this.tabla_actividades = tabla_actividades;
	}

	public String getNuevoModifica() {
		return NuevoModifica;
	}

	public void setNuevoModifica(String nuevoModifica) {
		NuevoModifica = nuevoModifica;
	}

	public int getFolio() {
		return folio;
	}

	public void setFolio(int folio) {
		this.folio = folio;
	}

	public String getCuadrante() {
		return cuadrante;
	}

	public void setCuadrante(String cuadrante) {
		this.cuadrante = cuadrante;
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

	public String getPuesto_reporta() {
		return puesto_reporta;
	}

	public void setPuesto_reporta(String puesto_reporta) {
		this.puesto_reporta = puesto_reporta;
	}

	public String getObjetivo() {
		return objetivo;
	}

	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}

	public String getResponsabilidades() {
		return responsabilidades;
	}

	public void setResponsabilidades(String responsabilidades) {
		this.responsabilidades = responsabilidades;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
	
	public String[][] refrescar_tablas(int folio_cuadrante){
		return new BuscarSQL().TablaActividades_Cuadrante(folio_cuadrante);
	}
	
	public int Nuevo(){
		return new BuscarSQL().Devuelve_ultimo_folio_transaccion("74");
	}

	public boolean Guardar(){
		return new GuardarSQL().Guardar_Cuadrante(this);
	}
	
	
	public String[][] refrescar_tabla_captura_cuadrante(String clave_checador){
		return new BuscarSQL().TablaActividades_Cuadrante_captura(clave_checador);
	}
	
	public boolean Validacion_captura_existe_cuadrante(String clave_checador){
		return new BuscarSQL().Valida_Clave_Checador_Si_Existe_Cuadrante(clave_checador);
	}
	
	public String devuelve_clave_checador(){
		return new BuscarSQL().clave_checador();
	}
	
	public boolean Guardar_Captura(){
		return new GuardarSQL().Guardar_Captura_Cuadrante(this);
	}
	
	public String[] Combo_Turnos() {
		try {
			return new Cargar_Combo().Combos("turnos");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; 
	}
	
}
