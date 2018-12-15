package Obj_Contabilidad;

import java.sql.SQLException;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Cargar_Combo;
import Conexiones_SQL.GuardarSQL;

public class Obj_Revision_De_Programacion_de_Pago {
	int folio_programacion=0; 
	float total_programacion=0;
	float total_presupuesto=0;
	float total_propuesto=0;
	int presupuesto_propuesto=0;
	String estatus=""; 
	boolean estatus_programacion=false;
	
	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public int getPresupuesto_propuesto() {
		return presupuesto_propuesto;
	}

	public void setPresupuesto_propuesto(int presupuesto_propuesto) {
		this.presupuesto_propuesto = presupuesto_propuesto;
	}

	String [][] tabla_programacion;
	String GuardarActualizar="";

	public int getFolio_programacion() {
		return folio_programacion;
	}

	public void setFolio_programacion(int folio_programacion) {
		this.folio_programacion = folio_programacion;
	}

	public String getGuardarActualizar() {
		return GuardarActualizar;
	}

	public void setGuardarActualizar(String guardarActualizar) {
		GuardarActualizar = guardarActualizar;
	}

	public float getTotal_programacion() {
		return total_programacion;
	}

	public void setTotal_programacion(float total_programacion) {
		this.total_programacion = total_programacion;
	}

	public float getTotal_presupuesto() {
		return total_presupuesto;
	}

	public void setTotal_presupuesto(float total_presupuesto) {
		this.total_presupuesto = total_presupuesto;
	}

	public float getTotal_propuesto() {
		return total_propuesto;
	}

	public void setTotal_propuesto(float total_propuesto) {
		this.total_propuesto = total_propuesto;
	}

	public String[][] getTabla_programacion() {
		return tabla_programacion;
	}

	public void setTabla_programacion(String[][] tabla_programacion) {
		this.tabla_programacion = tabla_programacion;
	}

	public String[][] refrescar_tabla_programacion(String folio_programacion, String folio_usuario){
		return new BuscarSQL().Tabla_Programacion_de_pago(folio_programacion, folio_usuario);
	}
	
	public String[][] Tabla_Programacion_de_pago_clasificadores(){
		return new BuscarSQL().Tabla_Programacion_de_pago_clasificadores();
	}
	
	public String[] combo_estatus_programacion() {
		try {
			return new Cargar_Combo().Combos("programacionestatus");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; 
	}
	
	
	public boolean Guardar(){
		return new GuardarSQL().Guardar_Programacion(this);
	}
	
	
	public boolean BucarEstatus(String folio_programacion){
		return new BuscarSQL().Status_Programacion_de_Pago(folio_programacion);
	}
	
	public boolean Guardar_presupuesto(){
		return new GuardarSQL().Guardar_Programacion_Presupuesto(this);
	}
	

	
}
