package Obj_Lista_de_Raya;

import java.io.File;

import Conexiones_SQL.BuscarSQL;

public class Obj_Revision_De_Horario_Por_Nivel_Jerarquico {

	int folio_colaborador;
	String Colaborador;
	String establecimiento;
	String departamento;
	String puesto;
	
	int folio_h1;
	int folio_h2;
	int folio_h3;
	
	String horario_1;
	String horario_2;
	String horario_3;
	
	boolean status_h1;
	boolean status_h2;
	boolean status_h3;
	
	String descanso_h1;
	String dobla_h1;
	String descanso_h2;
	String dobla_h2;
	String descanso_h3;
	String dobla_h3;
	
	int status_rotativo;
	
	String turno_cuadrante;
	File foto;
	
	
	public Obj_Revision_De_Horario_Por_Nivel_Jerarquico() {
		folio_colaborador=0;		Colaborador="";		establecimiento="";		departamento="";		puesto="";
		
		folio_h1=0;			folio_h2=0;			folio_h3=0;
		horario_1="";		horario_2="";		horario_3="";
		status_h1=false;	status_h2=false;	status_h3=false;
		
		status_rotativo=0;		turno_cuadrante="";		foto=null;
	}

	public int getFolio_colaborador() {
		return folio_colaborador;
	}

	public void setFolio_colaborador(int folio_colaborador) {
		this.folio_colaborador = folio_colaborador;
	}

	public String getColaborador() {
		return Colaborador;
	}

	public void setColaborador(String colaborador) {
		Colaborador = colaborador;
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

	public int getFolio_h1() {
		return folio_h1;
	}

	public void setFolio_h1(int folio_h1) {
		this.folio_h1 = folio_h1;
	}

	public int getFolio_h2() {
		return folio_h2;
	}

	public void setFolio_h2(int folio_h2) {
		this.folio_h2 = folio_h2;
	}

	public int getFolio_h3() {
		return folio_h3;
	}

	public void setFolio_h3(int folio_h3) {
		this.folio_h3 = folio_h3;
	}

	public String getHorario_1() {
		return horario_1;
	}

	public void setHorario_1(String horario_1) {
		this.horario_1 = horario_1;
	}

	public String getHorario_2() {
		return horario_2;
	}

	public void setHorario_2(String horario_2) {
		this.horario_2 = horario_2;
	}

	public String getHorario_3() {
		return horario_3;
	}


	public void setHorario_3(String horario_3) {
		this.horario_3 = horario_3;
	}

	public boolean isStatus_h1() {
		return status_h1;
	}

	public void setStatus_h1(boolean status_h1) {
		this.status_h1 = status_h1;
	}

	public boolean isStatus_h2() {
		return status_h2;
	}

	public void setStatus_h2(boolean status_h2) {
		this.status_h2 = status_h2;
	}

	public boolean isStatus_h3() {
		return status_h3;
	}

	public void setStatus_h3(boolean status_h3) {
		this.status_h3 = status_h3;
	}

	public String getDescanso_h1() {
		return descanso_h1;
	}

	public void setDescanso_h1(String descanso_h1) {
		this.descanso_h1 = descanso_h1;
	}

	public String getDobla_h1() {
		return dobla_h1;
	}

	public void setDobla_h1(String dobla_h1) {
		this.dobla_h1 = dobla_h1;
	}

	public String getDescanso_h2() {
		return descanso_h2;
	}

	public void setDescanso_h2(String descanso_h2) {
		this.descanso_h2 = descanso_h2;
	}

	public String getDobla_h2() {
		return dobla_h2;
	}

	public void setDobla_h2(String dobla_h2) {
		this.dobla_h2 = dobla_h2;
	}

	public String getDescanso_h3() {
		return descanso_h3;
	}

	public void setDescanso_h3(String descanso_h3) {
		this.descanso_h3 = descanso_h3;
	}

	public String getDobla_h3() {
		return dobla_h3;
	}

	public void setDobla_h3(String dobla_h3) {
		this.dobla_h3 = dobla_h3;
	}

	public int getStatus_rotativo() {
		return status_rotativo;
	}

	public void setStatus_rotativo(int status_rotativo) {
		this.status_rotativo = status_rotativo;
	}

	public String getTurno_cuadrante() {
		return turno_cuadrante;
	}

	public void setTurno_cuadrante(String turno_cuadrante) {
		this.turno_cuadrante = turno_cuadrante;
	}

	public File getFoto() {
		return foto;
	}

	public void setFoto(File foto) {
		this.foto = foto;
	}

	public Obj_Revision_De_Horario_Por_Nivel_Jerarquico buscar(int folioColaborador){
		return new BuscarSQL().buscar_colaborador(folioColaborador);
	}
}
