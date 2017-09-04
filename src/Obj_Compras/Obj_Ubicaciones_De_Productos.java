package Obj_Compras;

import java.sql.SQLException;

import Conexiones_SQL.BuscarSQL;


public class Obj_Ubicaciones_De_Productos {
	String Cod_Prod;
	String Descripcion_Prod;
	String Localizacion;
	String Clase_De_Producto;
	String Categoria;
	String Familia;
	String Area;
	String Fecha_Ultima_Vez_Se_Agoto;
	
	double Ultimo_Costo;
	double Costo_Promedio;
	double Existencia_Cedis;
	double Existencia_Total;
	double Cantidad_Negada_Ultimos_7_dias;
	double precio_de_venta;
	double Minimo;
	double Maximo;
		
	public String getFecha_Ultima_Vez_Se_Agoto() {
		return Fecha_Ultima_Vez_Se_Agoto;
	}
	public void setFecha_Ultima_Vez_Se_Agoto(String fecha_Ultima_Vez_Se_Agoto) {
		Fecha_Ultima_Vez_Se_Agoto = fecha_Ultima_Vez_Se_Agoto;
	}
	public double getMinimo() {
		return Minimo;
	}
	public void setMinimo(double minimo) {
		Minimo = minimo;
	}
	public double getMaximo() {
		return Maximo;
	}
	public void setMaximo(double maximo) {
		Maximo = maximo;
	}
	public String getCod_Prod() {
		return Cod_Prod;
	}
	public void setCod_Prod(String cod_Prod) {
		Cod_Prod = cod_Prod;
	}
	public String getDescripcion_Prod() {
		return Descripcion_Prod;
	}
	public void setDescripcion_Prod(String descripcion_Prod) {
		Descripcion_Prod = descripcion_Prod;
	}
	public String getLocalizacion() {
		return Localizacion;
	}
	public void setLocalizacion(String localizacion) {
		Localizacion = localizacion;
	}
	public String getClase_De_Producto() {
		return Clase_De_Producto;
	}
	public void setClase_De_Producto(String clase_De_Producto) {
		Clase_De_Producto = clase_De_Producto;
	}
	public String getCategoria() {
		return Categoria;
	}
	public void setCategoria(String categoria) {
		Categoria = categoria;
	}
	public String getFamilia() {
		return Familia;
	}
	public void setFamilia(String familia) {
		Familia = familia;
	}
	public String getArea() {
		return Area;
	}
	public void setArea(String area) {
		Area = area;
	}
	public double getUltimo_Costo() {
		return Ultimo_Costo;
	}
	public void setUltimo_Costo(double ultimo_Costo) {
		Ultimo_Costo = ultimo_Costo;
	}
	public double getCosto_Promedio() {
		return Costo_Promedio;
	}
	public void setCosto_Promedio(double costo_Promedio) {
		Costo_Promedio = costo_Promedio;
	}
	public double getExistencia_Cedis() {
		return Existencia_Cedis;
	}
	public void setExistencia_Cedis(double existencia_Cedis) {
		Existencia_Cedis = existencia_Cedis;
	}
	public double getExistencia_Total() {
		return Existencia_Total;
	}
	public void setExistencia_Total(double existencia_Total) {
		Existencia_Total = existencia_Total;
	}
	public double getCantidad_Negada_Ultimos_7_dias() {
		return Cantidad_Negada_Ultimos_7_dias;
	}
	public void setCantidad_Negada_Ultimos_7_dias(double cantidad_Negada_Ultimos_7_dias) {
		Cantidad_Negada_Ultimos_7_dias = cantidad_Negada_Ultimos_7_dias;
	}
	public double getPrecio_de_venta() {
		return precio_de_venta;
	}
	public void setPrecio_de_venta(double precio_de_venta) {
		this.precio_de_venta = precio_de_venta;
	}

	public Obj_Ubicaciones_De_Productos buscardatos_producto(String cod_prod, String establecimiento){ 
		try {
			return new BuscarSQL().datos_producto_ubicacion(cod_prod,establecimiento);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	return null; 
	}
	
}


