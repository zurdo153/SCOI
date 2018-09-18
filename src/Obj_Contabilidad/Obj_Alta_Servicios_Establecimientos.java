package Obj_Contabilidad;

import java.sql.SQLException;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Cargar_Combo;

public class Obj_Alta_Servicios_Establecimientos {
	int folio;
	String Descipcion;
	String NumeroControl;
	String Clasificacion;
	String Status;
	String Establacimiento;
	public int getFolio() {
		return folio;
	}
	public void setFolio(int folio) {
		this.folio = folio;
	}
	public String getDescipcion() {
		return Descipcion;
	}
	public void setDescipcion(String descipcion) {
		Descipcion = descipcion;
	}
	public String getNumeroControl() {
		return NumeroControl;
	}
	public void setNumeroControl(String numeroControl) {
		NumeroControl = numeroControl;
	}
	public String getClasificacion() {
		return Clasificacion;
	}
	public void setClasificacion(String clasificacion) {
		Clasificacion = clasificacion;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getEstablacimiento() {
		return Establacimiento;
	}
	public void setEstablacimiento(String establacimiento) {
		Establacimiento = establacimiento;
	}
	
	public Obj_Alta_Servicios_Establecimientos(){}
	public Obj_Alta_Servicios_Establecimientos(int folio, String Descripcion, String NumeroControl, String Clasificacion,String Status,String Establecimiento){

	this.folio=folio;
	this.Descipcion=Descripcion;
	this.NumeroControl=NumeroControl;
	this.Clasificacion=Clasificacion;
	this.Status=Status;
	this.Establacimiento=Establecimiento;
	}
	
	
	public String[] Combo_Respuesta_Establecimiento(){
		try {
			return new Cargar_Combo().Establecimiento_Todos("tb_establecimientos");
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String[] Combo_Respuesta_Clasificacion(){
		try {
			return new Cargar_Combo().Combo_Respuesta_Clasificacion();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	public String FolioSiguiente(){
		try {
			return new BuscarSQL().Folio_Siguiente_alta_Servicios();
			} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
		
		public boolean guardar_servios_establcimientos(String Descrpcion,String NumCtrol,String Clasificador,String status,String Establecimiento){
			return new Conexiones_SQL.GuardarSQL().Guardar_servicios_establecimientos(Descrpcion,NumCtrol,Clasificador,status,Establecimiento);
		}
		public boolean modificar_servios_establcimientos(String folio,String Descrpcion,String NumCtrol,String Clasificador,String status,String Establecimiento){
			return new Conexiones_SQL.GuardarSQL().Modificar_servios_establecimientos(folio, Descrpcion, NumCtrol, Clasificador, status, Establecimiento);
					}
		

		public String Buscar_Servicios_establecimientos_numCtrol(String num){
			try {
				return new BuscarSQL().Buscar_Servicios_establecimientos_NumCtrol(num);
				} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
	}
	
	
	
	
	
	
	

