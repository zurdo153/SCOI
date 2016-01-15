package Obj_Reportes;

import java.sql.SQLException;

import Conexiones_SQL.BuscarSQL;

public class Obj_Reportes_De_Ventas {
	String fecha_inicio 	 ;
	String fecha_final 		 ;
	String Establecimiento 	 ;
	String tipo_de_precio 	 ;

	String productos 		 ;
	String clases 			 ;
	String categorias 		 ;
	String familias 		 ;
	String lineas 			 ;
	String presentado        ;
	String tallas        ;
	String asignaciones  ;
	                         
	public Obj_Reportes_De_Ventas(){
		  fecha_inicio 	 ="";
		  fecha_final 	= "";
		  Establecimiento="";
		  tipo_de_precio ="";

		  productos 	= "";
		  clases 		= "";
		  categorias 	= "";
		  familias 		= "";
		  lineas 		= "";
		  presentado    = "";
		  tallas		= "";
	      asignaciones  = "";
	}



	public String getPresentado() {
		return presentado;
	}

	public void setPresentado(String presentado) {
		this.presentado = presentado;
	}

	public String getFecha_inicio() {
		return fecha_inicio;
	}

	public void setFecha_inicio(String fecha_inicio) {
		this.fecha_inicio = fecha_inicio;
	}

	public String getFecha_final() {
		return fecha_final;
	}

	public void setFecha_final(String fecha_final) {
		this.fecha_final = fecha_final;
	}

	public String getEstablecimiento() {
		return Establecimiento;
	}

	public void setEstablecimiento(String establecimiento) {
		Establecimiento = establecimiento;
	}

	public String getTipo_de_precio() {
		return tipo_de_precio;
	}

	public void setTipo_de_precio(String tipo_de_precio) {
		this.tipo_de_precio = tipo_de_precio;
	}

	public String getProductos() {
		return productos;
	}

	public void setProductos(String productos) {
		this.productos = productos;
	}

	public String getClases() {
		return clases;
	}

	public void setClases(String clases) {
		this.clases = clases;
	}

	public String getCategorias() {
		return categorias;
	}

	public void setCategorias(String categorias) {
		this.categorias = categorias;
	}

	public String getFamilias() {
		return familias;
	}

	public void setFamilias(String familias) {
		this.familias = familias;
	}

	public String getLineas() {
		return lineas;
	}

	public void setLineas(String lineas) {
		this.lineas = lineas;
	}
	
	public String getTallas() {
		return tallas;
	}

	public void setTallas(String tallas) {
		this.tallas = tallas;
	}

	public String getAsignaciones() {
		return asignaciones;
	}

	public void setAsignaciones(String asignaciones) {
		this.asignaciones = asignaciones;
	}
	
	public String[][] reporte_de_ventas() throws SQLException{
		return new BuscarSQL().Reporte_De_Ventas(this);
	}
	
	public Object[][] reporte_de_competencias(int cantidad_de_columnas, int tipo) throws SQLException{
		return new BuscarSQL().Reporte_De_Competencia(this,cantidad_de_columnas,tipo);
	}
	public int cantidad_de_competidores(){
		return new BuscarSQL().Numero_De_Competidores();
	}
	public String[] lista_de_competidores() throws SQLException{
		return new BuscarSQL().Vector_De_Competidores();
	}
}
