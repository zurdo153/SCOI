package Obj_Compras;

import java.sql.SQLException;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.GuardarSQL;

public class Obj_Venta_De_Cascos_A_Proveedores {
	public String  cod_prv="";
	public String  folio_pago_casco="";
	public String  nombre_proveedor="";
	public String  nombre_proveedor_recibe="";
	public String     Total="";
	public boolean existe=false;
    Object[][] tabla_obj =null;

	public String getCod_prv() {
		return cod_prv;
	}

	public void setCod_prv(String cod_prv) {
		this.cod_prv = cod_prv;
	}

	public String getFolio_pago_casco() {
		return folio_pago_casco;
	}

	public void setFolio_pago_casco(String folio_pago_casco) {
		this.folio_pago_casco = folio_pago_casco;
	}

	public String getNombre_proveedor() {
		return nombre_proveedor;
	}

	public void setNombre_proveedor(String nombre_proveedor) {
		this.nombre_proveedor = nombre_proveedor;
	}

	public String getNombre_proveedor_recibe() {
		return nombre_proveedor_recibe;
	}

	public void setNombre_proveedor_recibe(String nombre_proveedor_recibe) {
		this.nombre_proveedor_recibe = nombre_proveedor_recibe;
	}

	public String getTotal() {
		return Total;
	}

	public void setTotal(String total) {
		Total = total;
	}

	public boolean isExiste() {
		return existe;
	}

	public void setExiste(boolean existe) {
		this.existe = existe;
	}

	public Object[][] getTabla_obj() {
		return tabla_obj;
	}

	public void setTabla_obj(Object[][] tabla_obj) {
		this.tabla_obj = tabla_obj;
	}

	public Obj_Venta_De_Cascos_A_Proveedores(){
	}

	public Obj_Venta_De_Cascos_A_Proveedores buscar(String folio) { 
		try {
			return new BuscarSQL().venta_de_casco(folio);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; 
	}
	
	public boolean guardar(){
		return new GuardarSQL().Guardar_Pago_cascos(this); 
	}
	
}
