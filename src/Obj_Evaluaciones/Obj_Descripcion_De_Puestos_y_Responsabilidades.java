package Obj_Evaluaciones;

import java.io.File;
import java.sql.SQLException;

import Conexiones_SQL.Cargar_Combo;
import Conexiones_SQL.GuardarSQL;

public class Obj_Descripcion_De_Puestos_y_Responsabilidades {

	int 	folio;
	int 	folioPuesto;
	String puesto;
	
	String unidadNegocio;
	String Establecimiento;
	String Departamento;
	
	int folioReportaA;
	String reportaA;
	int edadIn;
	int edadFin;
	
	String sexo;
	String estadoCivil;
	String objetivoPuesto;
	
	Object[][] responsabilidadesPuesto;
	String xmlResponsabilidadesPuesto;
	
	String nivelEstudios;
	String listaDeEspecificaciones;
	String cursosHabilidades;
	String esperienciaGeneral;
	String esperienciaEspecifica;
	
	int facultamientosDirectos;
	int facultamientosIndirectos;
	
	File organigrama;
	
	int interacionDelPuestoExternas;
	String relacionDelPuestoExternas;
	
	int interacionDelPuestoInternas;
	String relacionDelPuestoInternas;
	
	String ambienteDeTrabajo;
	String esfuerzoFisico;
	
	boolean viaje;
	boolean laptop;
	boolean pc;
	boolean celular;
	boolean extencion;
	boolean autoPropio;
	boolean autoEmpresa;
	boolean licencia;
	boolean largaDistancia;
	boolean otro;
	String notaOtro;
	
	public Obj_Descripcion_De_Puestos_y_Responsabilidades() {
		folio=0;
		folioPuesto=0;
		puesto="";
		
		unidadNegocio="";
		Establecimiento="";
		Departamento="";
		
		folioReportaA=0;
		reportaA="";
		edadIn=0;
		edadFin=0;
		
		sexo="";
		estadoCivil="";
		objetivoPuesto="";
		
		responsabilidadesPuesto=null;
		xmlResponsabilidadesPuesto="";
		
		nivelEstudios="";
		listaDeEspecificaciones="";
		cursosHabilidades="";
		esperienciaGeneral="";
		esperienciaEspecifica="";
		
		facultamientosDirectos=0;
		facultamientosIndirectos=0;
		
		organigrama=null;
		
		interacionDelPuestoExternas=0;
		relacionDelPuestoExternas="";
		
		interacionDelPuestoInternas=0;
		relacionDelPuestoInternas="";
		
		ambienteDeTrabajo="";
		esfuerzoFisico="";
		
		viaje=false;
		laptop=false;
		pc=false;
		celular=false;
		extencion=false;
		autoPropio=false;
		autoEmpresa=false;
		licencia=false;
		largaDistancia=false;
		otro=false;
		notaOtro="";
	}

	public int getFolio() {
		return folio;
	}
	public void setFolio(int folio) {
		this.folio = folio;
	}

	public int getFolioPuesto() {
		return folioPuesto;
	}
	public void setFolioPuesto(int folioPuesto) {
		this.folioPuesto = folioPuesto;
	}

	public String getPuesto() {
		return puesto;
	}
	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}
//------------------------------------------------------------------------------------------------
	public String getUnidadNegocio() {
		return unidadNegocio;
	}

	public void setUnidadNegocio(String unidadNegocio) {
		this.unidadNegocio = unidadNegocio;
	}
//-----------------------------------------------------------------------------------------------
	public String getEstablecimiento() {
		return Establecimiento;
	}
	public void setEstablecimiento(String establecimiento) {
		Establecimiento = establecimiento;
	}

	public String getDepartamento() {
		return Departamento;
	}
	public void setDepartamento(String departamento) {
		Departamento = departamento;
	}

	public int getFolioReportaA() {
		return folioReportaA;
	}
	public void setFolioReportaA(int folioReportaA) {
		this.folioReportaA = folioReportaA;
	}

	public String getReportaA() {
		return reportaA;
	}
	public void setReportaA(String reportaA) {
		this.reportaA = reportaA;
	}

	public int getEdadIn() {
		return edadIn;
	}
	public void setEdadIn(int edadIn) {
		this.edadIn = edadIn;
	}

	public int getEdadFin() {
		return edadFin;
	}
	public void setEdadFin(int edadFin) {
		this.edadFin = edadFin;
	}

	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getEstadoCivil() {
		return estadoCivil;
	}
	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public String getObjetivoPuesto() {
		return objetivoPuesto;
	}
	public void setObjetivoPuesto(String objetivoPuesto) {
		this.objetivoPuesto = objetivoPuesto;
	}

	public Object[][] getResponsabilidadesPuesto() {
		return responsabilidadesPuesto;
	}
	public void setResponsabilidadesPuesto(Object[][] responsabilidadesPuesto) {
		this.responsabilidadesPuesto = responsabilidadesPuesto;
	}

	public String getXmlResponsabilidadesPuesto() {
		return xmlResponsabilidadesPuesto;
	}

	public void setXmlResponsabilidadesPuesto(String xmlResponsabilidadesPuesto) {
		this.xmlResponsabilidadesPuesto = xmlResponsabilidadesPuesto;
	}

	public String getNivelEstudios() {
		return nivelEstudios;
	}
	public void setNivelEstudios(String nivelEstudios) {
		this.nivelEstudios = nivelEstudios;
	}

	public String getListaDeEspecificaciones() {
		return listaDeEspecificaciones;
	}
	public void setListaDeEspecificaciones(String listaDeEspecificaciones) {
		this.listaDeEspecificaciones = listaDeEspecificaciones;
	}

	public String getCursosHabilidades() {
		return cursosHabilidades;
	}
	public void setCursosHabilidades(String cursosHabilidades) {
		this.cursosHabilidades = cursosHabilidades;
	}

	public String getEsperienciaGeneral() {
		return esperienciaGeneral;
	}
	public void setEsperienciaGeneral(String esperienciaGeneral) {
		this.esperienciaGeneral = esperienciaGeneral;
	}

	public String getEsperienciaEspecifica() {
		return esperienciaEspecifica;
	}
	public void setEsperienciaEspecifica(String esperienciaEspecifica) {
		this.esperienciaEspecifica = esperienciaEspecifica;
	}

	public int getFacultamientosDirectos() {
		return facultamientosDirectos;
	}
	public void setFacultamientosDirectos(int facultamientosDirectos) {
		this.facultamientosDirectos = facultamientosDirectos;
	}

	public int getFacultamientosIndirectos() {
		return facultamientosIndirectos;
	}
	public void setFacultamientosIndirectos(int facultamientosIndirectos) {
		this.facultamientosIndirectos = facultamientosIndirectos;
	}

	public File getOrganigrama() {
		return organigrama;
	}
	public void setOrganigrama(File organigrama) {
		this.organigrama = organigrama;
	}

	public int getInteracionDelPuestoExternas() {
		return interacionDelPuestoExternas;
	}
	public void setInteracionDelPuestoExternas(int interacionDelPuestoExternas) {
		this.interacionDelPuestoExternas = interacionDelPuestoExternas;
	}

	public String getRelacionDelPuestoExternas() {
		return relacionDelPuestoExternas;
	}
	public void setRelacionDelPuestoExternas(String relacionDelPuestoExternas) {
		this.relacionDelPuestoExternas = relacionDelPuestoExternas;
	}

	public int getInteracionDelPuestoInternas() {
		return interacionDelPuestoInternas;
	}
	public void setInteracionDelPuestoInternas(int interacionDelPuestoInternas) {
		this.interacionDelPuestoInternas = interacionDelPuestoInternas;
	}

	public String getRelacionDelPuestoInternas() {
		return relacionDelPuestoInternas;
	}
	public void setRelacionDelPuestoInternas(String relacionDelPuestoInternas) {
		this.relacionDelPuestoInternas = relacionDelPuestoInternas;
	}

	public String getAmbienteDeTrabajo() {
		return ambienteDeTrabajo;
	}
	public void setAmbienteDeTrabajo(String ambienteDeTrabajo) {
		this.ambienteDeTrabajo = ambienteDeTrabajo;
	}

	public String getEsfuerzoFisico() {
		return esfuerzoFisico;
	}
	public void setEsfuerzoFisico(String esfuerzoFisico) {
		this.esfuerzoFisico = esfuerzoFisico;
	}

	public boolean isViaje() {
		return viaje;
	}
	public void setViaje(boolean viaje) {
		this.viaje = viaje;
	}

	public boolean isLaptop() {
		return laptop;
	}
	public void setLaptop(boolean laptop) {
		this.laptop = laptop;
	}

	public boolean isPc() {
		return pc;
	}
	public void setPc(boolean pc) {
		this.pc = pc;
	}

	public boolean isCelular() {
		return celular;
	}
	public void setCelular(boolean celular) {
		this.celular = celular;
	}

	public boolean isExtencion() {
		return extencion;
	}
	public void setExtencion(boolean extencion) {
		this.extencion = extencion;
	}

	public boolean isAutoPropio() {
		return autoPropio;
	}
	public void setAutoPropio(boolean autoPropio) {
		this.autoPropio = autoPropio;
	}

	public boolean isAutoEmpresa() {
		return autoEmpresa;
	}
	public void setAutoEmpresa(boolean autoEmpresa) {
		this.autoEmpresa = autoEmpresa;
	}

	public boolean isLicencia() {
		return licencia;
	}
	public void setLicencia(boolean licencia) {
		this.licencia = licencia;
	}

	public boolean isLargaDistancia() {
		return largaDistancia;
	}
	public void setLargaDistancia(boolean largaDistancia) {
		this.largaDistancia = largaDistancia;
	}

	public boolean isOtro() {
		return otro;
	}
	public void setOtro(boolean otro) {
		this.otro = otro;
	}

	public String getNotaOtro() {
		return notaOtro;
	}
	public void setNotaOtro(String notaOtro) {
		this.notaOtro = notaOtro;
	}
	
	public String[] Combo_Unidad_De_Negocio(){
		try {
			return new Cargar_Combo().UnidadDeNegocio("unidadDeNegocio");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	return null; }

	public boolean guardar(String movimiento){
		return new GuardarSQL().guardarDPR(this, movimiento);
	}
}
