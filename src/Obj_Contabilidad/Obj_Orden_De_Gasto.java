package Obj_Contabilidad;

import java.sql.SQLException;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Cargar_Combo;
import Conexiones_SQL.GuardarSQL;


public class Obj_Orden_De_Gasto {
	int folio=0;
	int folio_usuario_solicito=0;
	float total_gasto=0;
	int cantidad_de_correos=0;
	int folio_servicio=0;
	
	String establecimiento_solicito="";
    String cod_prv="";
	String tipo_proveedor="";
	String descripcion_gasto="";
	String Guardar_actualizar="";
	String correos="";
	String concepto_gasto="";
	String tipo="";
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public int getFolio_servicio() {
		return folio_servicio;
	}
	public void setFolio_servicio(int folio_servicio) {
		this.folio_servicio = folio_servicio;
	}
	public String getConcepto_gasto() {
		return concepto_gasto;
	}
	public void setConcepto_gasto(String concepto_gasto) {
		this.concepto_gasto = concepto_gasto;
	}

	Object[][] tabla_obj =null;
	
	public Object[][] getTabla_obj() {
		return tabla_obj;
	}
	public void setTabla_obj(Object[][] tabla_obj) {
		this.tabla_obj = tabla_obj;
	}
	public int getFolio() {
		return folio;
	}
	public void setFolio(int folio) {
		this.folio = folio;
	}
	public int getFolio_usuario_solicito() {
		return folio_usuario_solicito;
	}
	public void setFolio_usuario_solicito(int folio_usuario_solicito) {
		this.folio_usuario_solicito = folio_usuario_solicito;
	}
	public float getTotal_gasto() {
		return total_gasto;
	}
	public void setTotal_gasto(float total_gasto) {
		this.total_gasto = total_gasto;
	}
	public int getCantidad_de_correos() {
		return cantidad_de_correos;
	}
	public void setCantidad_de_correos(int cantidad_de_correos) {
		this.cantidad_de_correos = cantidad_de_correos;
	}
	public String getEstablecimiento_solicito() {
		return establecimiento_solicito;
	}
	public void setEstablecimiento_solicito(String establecimiento_solicito) {
		this.establecimiento_solicito = establecimiento_solicito;
	}
	public String getCod_prv() {
		return cod_prv;
	}
	public void setCod_prv(String cod_prv) {
		this.cod_prv = cod_prv;
	}
	public String getTipo_proveedor() {
		return tipo_proveedor;
	}
	public void setTipo_proveedor(String tipo_proveedor) {
		this.tipo_proveedor = tipo_proveedor;
	}
	public String getDescripcion_gasto() {
		return descripcion_gasto;
	}
	public void setDescripcion_gasto(String descripcion_gasto) {
		this.descripcion_gasto = descripcion_gasto;
	}
	public String getGuardar_actualizar() {
		return Guardar_actualizar;
	}
	public void setGuardar_actualizar(String guardar_actualizar) {
		Guardar_actualizar = guardar_actualizar;
	}
	public String getCorreos() {
		return correos;
	}
	public void setCorreos(String correos) {
		this.correos = correos;
	}
	
	public String[] Combo_Cuentas() {
		try {
			return new Cargar_Combo().Combos("estatusorden");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; 
	}
	
	public Obj_Orden_De_Gasto GuardarActualizar(){ 
	return new GuardarSQL().Guardar_Solicitud_Orden_De_Gasto(this); }

	public String[][] consulta_orden_de_gasto(int folio_orden_gasto){
		return new BuscarSQL().Tabla_Orden_Gasto(folio_orden_gasto);
	}
	
	public boolean validacion_existe (String folio){
		return new BuscarSQL().Validaciones("EOP",folio,"");
	}
}
