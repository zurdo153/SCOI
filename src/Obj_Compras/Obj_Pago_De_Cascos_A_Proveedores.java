package Obj_Compras;

import java.sql.SQLException;

import Conexiones_SQL.BuscarSQL;

public class Obj_Pago_De_Cascos_A_Proveedores {
	public String  cod_prv="";
	public String  folio_pago_casco="";
	public String  nombre_proveedor="";
	public String  nombre_proveedor_recibe="";
	public String  folio_factura="";
	public boolean existe=false;
	public int cantidad_cascos=0;

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

	public String getFolio_factura() {
		return folio_factura;
	}

	public void setFolio_factura(String folio_factura) {
		this.folio_factura = folio_factura;
	}

	public boolean isExiste() {
		return existe;
	}

	public void setExiste(boolean existe) {
		this.existe = existe;
	}

	public int getCantidad_cascos() {
		return cantidad_cascos;
	}

	public void setCantidad_cascos(int cantidad_cascos) {
		this.cantidad_cascos = cantidad_cascos;
	}

	public Obj_Pago_De_Cascos_A_Proveedores(){
	}

	public Obj_Pago_De_Cascos_A_Proveedores buscar(String folio) { 
		try {
			return new BuscarSQL().Pagos_cascos(folio);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; 
	}
//	
//	public boolean guardar(){
//		return new GuardarSQL().Guardar_Productos(this); 
//	}
//	
//	public String[] ComboUnidadDeMedida(){
//		try {
//			return new Cargar_Combo().unidadDeMedida();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}	
//	
//	public String[] ComboUso(){
//		try {
//			return new Cargar_Combo().uso();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}	
	
}
