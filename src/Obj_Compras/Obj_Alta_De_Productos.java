package Obj_Compras;

import java.sql.SQLException;

import Conexiones_SQL.ActualizarSQL;
import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Cargar_Combo;
import Conexiones_SQL.GuardarSQL;

public class Obj_Alta_De_Productos {
	public String folio;
	public String descripcion;
	public String UnidadDeMedida;
	public String Uso;
	public String codigoDeBarras;
	public double costo;
	public double precioDeVenta;
	public String status;
	
	public Obj_Alta_De_Productos(){
		folio="";     descripcion="";		UnidadDeMedida="";		Uso="";
		codigoDeBarras="";	costo=0;      	precioDeVenta=0; 		status="";     
	}

	public String getFolio() {
			return folio;
		}

		public void setFolio(String folio) {
			this.folio = folio;
		}

		public String getDescripcion() {
			return descripcion;
		}

		public void setDescripcion(String descripcion) {
			this.descripcion = descripcion;
		}

		public String getUnidadDeMedida() {
			return UnidadDeMedida;
		}

		public void setUnidadDeMedida(String unidadDeMedida) {
			UnidadDeMedida = unidadDeMedida;
		}

		public String getUso() {
			return Uso;
		}

		public void setUso(String uso) {
			Uso = uso;
		}

		public String getCodigoDeBarras() {
			return codigoDeBarras;
		}

		public void setCodigoDeBarras(String codigoDeBarras) {
			this.codigoDeBarras = codigoDeBarras;
		}

		public double getCosto() {
			return costo;
		}

		public void setCosto(double costo) {
			this.costo = costo;
		}

		public double getPrecioDeVenta() {
			return precioDeVenta;
		}

		public void setPrecioDeVenta(double precioDeVenta) {
			this.precioDeVenta = precioDeVenta;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}
		

	public Obj_Alta_De_Productos buscar(String folio) { 
		try {
			return new BuscarSQL().Productos(folio);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; 
	}
	
	public boolean actualizar(String folio){ 
		return new ActualizarSQL().Productos(this,folio); 
	}
 
	public boolean guardar(){
		return new GuardarSQL().Guardar_Productos(this); 
	}
	
	public String[] ComboUnidadDeMedida(){
		try {
			return new Cargar_Combo().unidadDeMedida();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}	
	
	public String[] ComboUso(){
		try {
			return new Cargar_Combo().uso();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}	
	
}
