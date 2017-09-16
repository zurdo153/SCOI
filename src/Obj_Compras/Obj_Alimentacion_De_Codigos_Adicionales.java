package Obj_Compras;

import java.sql.SQLException;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.GuardarSQL;


public class Obj_Alimentacion_De_Codigos_Adicionales {
	
	String Folio_Producto;
	String Descripcion;
	String UDM;
	String Uso;
	double Costo;
	double Precio_Venta;
	String Estatus;
	String Codigo_Barras;
	String Codigo_Tecleado;
	
	public String getFolio_Producto() {
		return Folio_Producto;
	}
	public void setFolio_Producto(String folio_Producto) {
		Folio_Producto = folio_Producto;
	}
	public String getDescripcion() {
		return Descripcion;
	}
	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}
	public String getUDM() {
		return UDM;
	}
	public void setUDM(String uDM) {
		UDM = uDM;
	}
	public String getUso() {
		return Uso;
	}
	public void setUso(String uso) {
		Uso = uso;
	}
	public double getCosto() {
		return Costo;
	}
	public void setCosto(double costo) {
		Costo = costo;
	}
	public double getPrecio_Venta() {
		return Precio_Venta;
	}
	public void setPrecio_Venta(double precio_Venta) {
		Precio_Venta = precio_Venta;
	}
	public String getEstatus() {
		return Estatus;
	}
	public void setEstatus(String estatus) {
		Estatus = estatus;
	}
	public String getCodigo_Barras() {
		return Codigo_Barras;
	}
	public void setCodigo_Barras(String codigo_Barras) {
		Codigo_Barras = codigo_Barras;
	}
	public String getCodigo_Tecleado() {
		return Codigo_Tecleado;
	}
	public void setCodigo_Tecleado(String codigo_Tecleado) {
		Codigo_Tecleado = codigo_Tecleado;
	}
	
	public boolean Existe_Producto(String cod_prod) throws SQLException{ 
		return new BuscarSQL().existe_Producto_SCOI(cod_prod);
	}
		
		
	public Obj_Alimentacion_De_Codigos_Adicionales buscardatos_producto(String cod_prod){ 
		try {
			return new BuscarSQL().datos_producto_scoi(cod_prod);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	return null; 
	}

		public boolean Guardar_Codigo_Adicional(){ return new GuardarSQL().Guardar_Codigo_Adicional(this); }
//	
//	public boolean Guardar_Captura_competencia(String[][] comp){ return new GuardarSQL().Guardar_captura_de_competencia(this,comp); }

}
