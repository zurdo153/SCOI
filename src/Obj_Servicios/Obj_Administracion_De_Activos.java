package Obj_Servicios;

import java.sql.SQLException;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.GuardarSQL;

public class Obj_Administracion_De_Activos {

	int folio;
	String descripcion;
	String departamento_responsable;
	String status;
	String establecimiento;
	String departamento;
	String tipo;
	String marca;
	String modelo;
	String serie;
	int anio_fabricacion;
	String fecha_compra;
	int garantia;
	String unidad_garantia;
	int vida_util;
	String unidad_vida_util;
	double costo;
	double depreciacion;
	String caracteristicas;
		
	String ruta_factura;		
	String ruta_foto;
	
	int grupo_equipo;
	
	public Obj_Administracion_De_Activos() {
		folio = 0;		descripcion = "";		departamento_responsable="";status = "";	establecimiento = "";		departamento = "";		tipo = "";				
		marca = "";		modelo = "";	serie = "";				anio_fabricacion = 0;		fecha_compra = null;		garantia = 0;			
		unidad_garantia = "";			vida_util = 0;			unidad_vida_util = "";		costo = 0;					depreciacion = 0;			
		caracteristicas = "";			ruta_factura="";		ruta_foto="";   			grupo_equipo=0;
	}

	public int getFolio() {
		return folio;
	}

	public void setFolio(int folio) {
		this.folio = folio;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDepartamento_responsable() {
		return departamento_responsable;
	}

	public void setDepartamento_responsable(String departamento_responsable) {
		this.departamento_responsable = departamento_responsable;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getEstablecimiento() {
		return establecimiento;
	}

	public void setEstablecimiento(String establecimiento) {
		this.establecimiento = establecimiento;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public int getAnio_fabricacion() {
		return anio_fabricacion;
	}

	public void setAnio_fabricacion(int anio_fabricacion) {
		this.anio_fabricacion = anio_fabricacion;
	}

	public String getFecha_compra() {
		return fecha_compra;
	}

	public void setFecha_compra(String fecha_compra) {
		this.fecha_compra = fecha_compra;
	}

	public int getGarantia() {
		return garantia;
	}

	public void setGarantia(int garantia) {
		this.garantia = garantia;
	}

	public String getUnidad_garantia() {
		return unidad_garantia;
	}

	public void setUnidad_garantia(String unidad_garantia) {
		this.unidad_garantia = unidad_garantia;
	}

	public int getVida_util() {
		return vida_util;
	}

	public void setVida_util(int vida_util) {
		this.vida_util = vida_util;
	}

	public String getUnidad_vida_util() {
		return unidad_vida_util;
	}

	public void setUnidad_vida_util(String unidad_vida_util) {
		this.unidad_vida_util = unidad_vida_util;
	}

	public double getCosto() {
		return costo;
	}

	public void setCosto(double costo) {
		this.costo = costo;
	}

	public double getDepreciacion() {
		return depreciacion;
	}

	public void setDepreciacion(double depreciacion) {
		this.depreciacion = depreciacion;
	}

	public String getCaracteristicas() {
		return caracteristicas;
	}

	public void setCaracteristicas(String caracteristicas) {
		this.caracteristicas = caracteristicas;
	}

	public String getRuta_factura() {
		return ruta_factura;
	}

	public void setRuta_factura(String ruta_factura) {
		this.ruta_factura = ruta_factura;
	}

	public String getRuta_foto() {
		return ruta_foto;
	}

	public void setRuta_foto(String ruta_foto) {
		this.ruta_foto = ruta_foto;
	}

	public int getGrupo_equipo() {
		return grupo_equipo;
	}

	public void setGrupo_equipo(int grupo_equipo) {
		this.grupo_equipo = grupo_equipo;
	}

	public boolean guardarActualizarActivos(String movimiento, String rutaFactura, String rutaImagen){
		return new GuardarSQL().Guardar_Administracion_De_Equipos(this,movimiento,rutaFactura,rutaImagen);
	}
	
	public boolean existeActivoBuscar(int folio){
		return new BuscarSQL().existeActivo(folio);
	}
	
	public Obj_Administracion_De_Activos buscar(int folio){
		try {
			return new BuscarSQL().getActivo(folio);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; 
	}
}
