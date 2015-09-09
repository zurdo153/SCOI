package Obj_Contabilidad;

import java.sql.SQLException;

import Conexiones_SQL.Cargar_Combo;

public class Obj_Cuentas_Contables {
	String folio_cuenta_contable ;
	String cuenta_contable ;
	String naturaleza;
	String grupo;
	String clasificacion;
	String status;
	
	public String getFolio_cuenta_contable() {
		return folio_cuenta_contable;
	}
	public void setFolio_cuenta_contable(String folio_cuenta_contable) {
		this.folio_cuenta_contable = folio_cuenta_contable;
	}
	public String getCuenta_contable() {
		return cuenta_contable;
	}
	public void setCuenta_contable(String cuenta_contable) {
		this.cuenta_contable = cuenta_contable;
	}
	public String getNaturaleza() {
		return naturaleza;
	}
	public void setNaturaleza(String naturaleza) {
		this.naturaleza = naturaleza;
	}
	public String getGrupo() {
		return grupo;
	}
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	public String getClasificacion() {
		return clasificacion;
	}
	public void setClasificacion(String clasificacion) {
		this.clasificacion = clasificacion;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	
	public String[] Combo_clasificacion_contable(){
		try {
			return new Cargar_Combo().Datos_Combo_Clasificacion_Contable();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	return null; }
	
//	public Obj_Cuentas_Contables buscar(int folio){
//		try {
//			return new BuscarSQL().Puesto(folio);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return null; 
//	}
//	
//	public boolean guardar(){ return new GuardarSQL().Guardar_Puesto(this); }
//	
//	public Obj_Cuentas_Contables buscar_nuevo(){
//		try {
//			return new BuscarSQL().Puesto_Nuevo();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return null; 
//	}
//	
//	public boolean actualizar(int folio){ return new ActualizarSQL().Puesto(this,folio); }
//	
//	
//	public Obj_Cuentas_Contables buscar_pues(String nombre){
//		try{
//			return new BuscarSQL().Pues_buscar(nombre); 
//		} catch(SQLException e){
//			
//		}
//		return null;
//	}	
//	
//	public Obj_Cuentas_Contables buscar_pues(int folio){
//		try{
//			return new BuscarSQL().Pues_buscar(folio); 
//		} catch(SQLException e){
//			
//		}
//		return null;
//	}	
}
