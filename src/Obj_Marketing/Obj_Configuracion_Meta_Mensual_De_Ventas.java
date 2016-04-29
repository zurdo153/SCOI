package Obj_Marketing;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.BuscarTablasModel;
import Conexiones_SQL.GuardarSQL;

public class Obj_Configuracion_Meta_Mensual_De_Ventas {
	String nombre 	 ;
	String establecimiento 	 ;
	String tipo_de_reporte 	 ;

	String productos 		 ;
	String clases 			 ;
	String categorias 		 ;
	String familias 		 ;
	String lineas 			 ;
	String tallas        	 ;
	
	public Obj_Configuracion_Meta_Mensual_De_Ventas(){
		  nombre="";
		  establecimiento="";
		  tipo_de_reporte ="";

		  productos 	= "";
		  clases 		= "";
		  categorias 	= "";
		  familias 		= "";
		  lineas 		= "";
		  tallas		= "";
		  
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEstablecimiento() {
		return establecimiento;
	}

	public void setEstablecimiento(String establecimiento) {
		this.establecimiento = establecimiento;
	}

	public String getTipo_de_reporte() {
		return tipo_de_reporte;
	}

	public void setTipo_de_reporte(String tipo_de_reporte) {
		this.tipo_de_reporte = tipo_de_reporte;
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

	public String[][] configuraciones_de_meta_mensual_de_ventas(){
		return new BuscarTablasModel().lista_de_configuraciones_de_meta_mensual_de_ventas();
	}
	
	public boolean guardar_configuracion_de_meta_mensual_de_ventas(String Movimiento,int folio_a_cancelar){
		return new GuardarSQL().Guardar_Configuracion_De_Meta_Mensual_De_Venta(this,Movimiento,folio_a_cancelar);
	}
	
	public boolean existe_nombre(String nombre){
		return new BuscarSQL().existe_nombre_de_configuracion_de_meta_mensual_de_venta(nombre);
	}
	
}
