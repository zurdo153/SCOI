package Obj_Compras;


import java.sql.SQLException;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.GuardarSQL;



public class Obj_Alimentacion_De_Inventarios_Parciales {
	
	String Cod_Prod;
	String Descripcion_Prod;
	double Existencia;
	double Existencia_Fisica;
	double Diferencia;
	double Ultimo_Costo;
	double Costo_Promedio;
	double precio_venta;
	String folio;
	String Notas;
	String Establecimiento;
    Object[][] tabla_obj =null;
    String status;
    double Precio_Lista;

	public double getPrecio_venta() {
		return precio_venta;
	}

	public void setPrecio_venta(double precio_venta) {
		this.precio_venta = precio_venta;
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

	public double getExistencia() {
		return Existencia;
	}

	public void setExistencia(double existencia) {
		Existencia = existencia;
	}

	public double getExistencia_Fisica() {
		return Existencia_Fisica;
	}

	public void setExistencia_Fisica(double existencia_Fisica) {
		Existencia_Fisica = existencia_Fisica;
	}

	public double getDiferencia() {
		return Diferencia;
	}

	public void setDiferencia(double diferencia) {
		Diferencia = diferencia;
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

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getNotas() {
		return Notas;
	}

	public void setNotas(String notas) {
		Notas = notas;
	}

	public String getEstablecimiento() {
		return Establecimiento;
	}

	public void setEstablecimiento(String establecimiento) {
		Establecimiento = establecimiento;
	}

	public Object[][] getTabla_obj() {
		return tabla_obj;
	}

	public void setTabla_obj(Object[][] tabla_obj) {
		this.tabla_obj = tabla_obj;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public double getPrecio_Lista() {
		return Precio_Lista;
	}

	public void setPrecio_Lista(double precio_Lista) {
		Precio_Lista = precio_Lista;
	}

	public Obj_Alimentacion_De_Inventarios_Parciales buscardatos_producto(String cod_prod, String Establecimiento){ 
		try {
			return new BuscarSQL().datos_producto_existencia(cod_prod, Establecimiento);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	return null; 
	}
	
//	
//	public String buscardatos_productos_en_pedidos(String cod_prod){ 
//			try {
//				return new BuscarSQL().datos_pedido(cod_prod);
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//	return null; 
//	}
//	

	public boolean Guardar_inventario_parcial(){ return new GuardarSQL().Guardar_inventarios_parciales(this); }


}
