package Obj_Punto_De_Venta;

import java.io.IOException;
import java.sql.SQLException;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Cargar_Combo;
import Conexiones_SQL.GuardarSQL;

public class Obj_Ventas_Express {

	int folio=0;
	String establecimiento="";
	String tipo_de_cliente="";
	String folio_cliente="";
	String notas="";
	String[][] tabla_prodcutos=null;
	String folio_vendedor="";
	double total_venta=0;
	String folio_proveedor="";
	String folio_supervisor_autoriza="";
	String Guardar_actualizar="";
	String estatus="";
	
	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public int getFolio() {
		return folio;
	}

	public void setFolio(int folio) {
		this.folio = folio;
	}

	public String getEstablecimiento() {
		return establecimiento;
	}

	public void setEstablecimiento(String establecimiento) {
		this.establecimiento = establecimiento;
	}

	public String getTipo_de_cliente() {
		return tipo_de_cliente;
	}

	public void setTipo_de_cliente(String tipo_de_cliente) {
		this.tipo_de_cliente = tipo_de_cliente;
	}

	public String getFolio_cliente() {
		return folio_cliente;
	}

	public void setFolio_cliente(String folio_cliente) {
		this.folio_cliente = folio_cliente;
	}

	public String getNotas() {
		return notas;
	}

	public void setNotas(String notas) {
		this.notas = notas;
	}

	public String[][] getTabla_prodcutos() {
		return tabla_prodcutos;
	}

	public void setTabla_prodcutos(String[][] tabla_prodcutos) {
		this.tabla_prodcutos = tabla_prodcutos;
	}

	public String getFolio_vendedor() {
		return folio_vendedor;
	}

	public void setFolio_vendedor(String folio_vendedor) {
		this.folio_vendedor = folio_vendedor;
	}

	public double getTotal_venta() {
		return total_venta;
	}

	public void setTotal_venta(double total_venta) {
		this.total_venta = total_venta;
	}

	public String getFolio_proveedor() {
		return folio_proveedor;
	}

	public void setFolio_proveedor(String folio_proveedor) {
		this.folio_proveedor = folio_proveedor;
	}

	public String getFolio_supervisor_autoriza() {
		return folio_supervisor_autoriza;
	}

	public void setFolio_supervisor_autoriza(String folio_supervisor_autoriza) {
		this.folio_supervisor_autoriza = folio_supervisor_autoriza;
	}

	public String getGuardar_actualizar() {
		return Guardar_actualizar;
	}

	public void setGuardar_actualizar(String guardar_actualizar) {
		Guardar_actualizar = guardar_actualizar;
	}

	public Obj_Ventas_Express GuardarActualizar(){ 
	return new GuardarSQL().Guardar_Venta_Express(this); 
	}
	
	public String[][] consulta_venta_express(int folio){
		return new BuscarSQL().Tabla_Venta_Express(folio);
	}
	
	public String[] Combo_Establecimientos() {try {
			return new Cargar_Combo().Combos("establecimientos_ventas_express");
		} catch (SQLException e) {e.printStackTrace();}
		return null; 
	}
	
	public String[][] vededores_express(String establecimiento) throws IOException{
		return new BuscarSQL().vendedor_catalogo_bms(establecimiento);
	}
	
	public String[][] supervisores_express(String establecimiento) throws IOException{
		return new BuscarSQL().supervisor_ventas_express(establecimiento);
	}
	
}
