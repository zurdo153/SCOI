package Obj_Compras;

import java.io.IOException;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.GuardarSQL;

public class Obj_Orden_De_Compra {
	public String  cod_prv="";
    public int  folio = 0;
    public String folio_orden_de_compra="";
    public String folio_usuario_bms="";
	String [][] tabla_productos=null;           
    public String GuardarActualizar="";
    
    public String getGuardarActualizar() {
		return GuardarActualizar;
	}

	public void setGuardarActualizar(String guardarActualizar) {
		GuardarActualizar = guardarActualizar;
	}

	public String[][] getTabla_productos() {
		return tabla_productos;
	}

	public void setTabla_productos(String[][] tabla_productos) {
		this.tabla_productos = tabla_productos;
	}

	public String getFolio_usuario_bms() {
		return folio_usuario_bms;
	}

	public void setFolio_usuario_bms(String folio_usuario_bms) {
		this.folio_usuario_bms = folio_usuario_bms;
	}

	public String getCod_prv() {
		return cod_prv;
	}

	public void setCod_prv(String cod_prv) {
		this.cod_prv = cod_prv;
	}

	public int getFolio() {
		return folio;
	}

	public void setFolio(int folio) {
		this.folio = folio;
	}

	public String getFolio_orden_de_compra() {
		return folio_orden_de_compra;
	}

	public void setFolio_orden_de_compra(String folio_orden_de_compra) {
		this.folio_orden_de_compra = folio_orden_de_compra;
	}

	public String[][] buscar_usuario_valido (int folio_empleado) throws IOException{
		return new BuscarSQL().usuario_valido(folio_empleado);
	}
	
	public boolean Guardar_Orden(){
		return new GuardarSQL().Guardar_Orden_De_Compra(this);
	}
	
	public boolean Guardar_Orden_solicitud_benchmaking(){
		return new GuardarSQL().Guardar_solicitud_de_benchmarking(this);
	}
}
