package Obj_Compras;


import Conexiones_SQL.GuardarSQL;

public class Obj_Alimentacion_De_Productos_Proximos_A_Caducar {
	String cod_prod="";
	String descripcion_Prod="";
	String fecha_caducidad="";
	String folio="";
	String Notas="";
	String establecimiento="";
	String guardar_actualizar="";
	String clasificacion="";
	String codigo_nuevo="";
	double cantidad=0;
	double ultimo_costo=0;
	double costo_promedio=0;
	double precio_venta=0;
	double precio_remate=0;
	
    Object[][] tabla_obj =null;
    String status;
    
	public String getGuardar_actualizar() {
		return guardar_actualizar;
	}
	public void setGuardar_actualizar(String guardar_actualizar) {
		this.guardar_actualizar = guardar_actualizar;
	}
	public String getCod_prod() {
		return cod_prod;
	}
	public void setCod_prod(String cod_prod) {
		this.cod_prod = cod_prod;
	}
	public String getDescripcion_Prod() {
		return descripcion_Prod;
	}
	public void setDescripcion_Prod(String descripcion_Prod) {
		this.descripcion_Prod = descripcion_Prod;
	}
	public String getFecha_caducidad() {
		return fecha_caducidad;
	}
	public void setFecha_caducidad(String fecha_caducidad) {
		this.fecha_caducidad = fecha_caducidad;
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
		return establecimiento;
	}
	public void setEstablecimiento(String establecimiento) {
		this.establecimiento = establecimiento;
	}
	public double getCantidad() {
		return cantidad;
	}
	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}
	public double getUltimo_costo() {
		return ultimo_costo;
	}
	public void setUltimo_costo(double ultimo_costo) {
		this.ultimo_costo = ultimo_costo;
	}
	public double getCosto_promedio() {
		return costo_promedio;
	}
	public void setCosto_promedio(double costo_promedio) {
		this.costo_promedio = costo_promedio;
	}
	public double getPrecio_venta() {
		return precio_venta;
	}
	public void setPrecio_venta(double precio_venta) {
		this.precio_venta = precio_venta;
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

//	public Obj_Alimentacion_De_Productos_Proximos_A_Caducar buscardatos_producto(String cod_prod, String Establecimiento){ 
//		try {
//			return new BuscarSQL().datos_producto_existencia(cod_prod, Establecimiento);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	return null; 
//	}
	
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

	public boolean Guardar_Alimentacion_Proximos_A_Caducar(){ 
		return new GuardarSQL().Guardar_Alimentacion_De_Productos_Proximos_A_Caducar(this); }


}
