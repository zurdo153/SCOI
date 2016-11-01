package Obj_Compras;


import java.sql.SQLException;

import Conexiones_SQL.BuscarSQL;



public class Obj_Alimentacion_De_Inventarios_Parciales {
	
	String Cod_Prod;
	String Descripcion_Prod;
	double Existencia;
	double Existencia_Fisica;
	double Diferencia;
	double Ultimo_Costo;
	double Costo_Promedio;
	String folio;
	String Notas;
	String Establecimiento;
	
	public double getDiferencia() {
		return Diferencia;
	}

	public void setDiferencia(double diferencia) {
		Diferencia = diferencia;
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
//	public Obj_Alimentacion_De_Inventarios_Parciales Hoymenos3meses(){ 
//		try {
//			return new BuscarSQL().Hoymenos3meses();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	return null; 
//	}
//	public boolean Guardar_Cotizacion(){ return new GuardarSQL().Guardar_Cotizacion_Producto(this); }
//	
//	public boolean Guardar_Captura_competencia(String[][] comp){ return new GuardarSQL().Guardar_captura_de_competencia(this,comp); }

}
