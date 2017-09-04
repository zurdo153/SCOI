package Obj_Compras;

import java.sql.SQLException;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.GuardarSQL;

public class Obj_Registrar_Zona_Completada {

	String folio_pedido;
	String zona;
	int folio_surtidor;
	String nombre_surtidor;
	String status;
	int folio_emp_recibe;
	
	public Obj_Registrar_Zona_Completada() {
		folio_pedido = "";
		zona = "";
		folio_surtidor = 0;
		nombre_surtidor = "";
		status = "" ;
		folio_emp_recibe = 0;
	}

	public String getFolio_pedido() {
		return folio_pedido;
	}

	public void setFolio_pedido(String folio_pedido) {
		this.folio_pedido = folio_pedido;
	}

	public String getZona() {
		return zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}

	public int getFolio_surtidor() {
		return folio_surtidor;
	}

	public void setFolio_surtidor(int folio_surtidor) {
		this.folio_surtidor = folio_surtidor;
	}

	public String getNombre_surtidor() {
		return nombre_surtidor;
	}

	public void setNombre_surtidor(String nombre_surtidor) {
		this.nombre_surtidor = nombre_surtidor;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public int getFolio_emp_recibe() {
		return folio_emp_recibe;
	}

	public void setFolio_emp_recibe(int folio_emp_recibe) {
		this.folio_emp_recibe = folio_emp_recibe;
	}

	public Obj_Registrar_Zona_Completada buscar(String codigo_de_barras){
		try {
			return new BuscarSQL().buscar_codigo_surtidor(codigo_de_barras);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean guardar(){
		return new GuardarSQL().Guardar_Recibir_Pedido(this);
	}
	
}
