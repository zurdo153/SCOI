package Obj_Contabilidad;

import java.sql.SQLException;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.GuardarSQL;

public class Obj_Alta_Proveedores_Polizas{

	int folio_proveedor;
	String nombre;
	String ap_paterno;
	String ap_materno;
	String direccion;
	String telefono;
	String Plazo;
	String GuardarActualizar;
	
	public String getGuardarActualizar() {
		return GuardarActualizar;
	}

	public void setGuardarActualizar(String guardarActualizar) {
		GuardarActualizar = guardarActualizar;
	}

	public String getPlazo() {
		return Plazo;
	}

	public void setPlazo(String plazo) {
		Plazo = plazo;
	}

	public Obj_Alta_Proveedores_Polizas() {
		this.folio_proveedor=0;	this.nombre="";	this.ap_paterno="";	this.ap_materno="";	this.direccion="";	this.telefono="";
	}

	public int getFolio_proveedor() {
		return folio_proveedor;
	}

	public void setFolio_proveedor(int folio_proveedor) {
		this.folio_proveedor = folio_proveedor;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getAp_paterno() {
		return ap_paterno;
	}

	public void setAp_paterno(String ap_paterno) {
		this.ap_paterno = ap_paterno;
	}

	public String getAp_materno() {
		return ap_materno;
	}

	public void setAp_materno(String ap_materno) {
		this.ap_materno = ap_materno;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public Obj_Alta_Proveedores_Polizas buscar(int folio_prv) throws SQLException{ return new BuscarSQL().Proveedor(folio_prv); }
	
	public boolean guardar(){ return new GuardarSQL().Guardar_Proveedor(this); }
	
}
