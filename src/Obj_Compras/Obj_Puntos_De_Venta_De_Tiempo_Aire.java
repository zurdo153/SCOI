package Obj_Compras;

import Conexiones_SQL.ActualizarSQL;
import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.GuardarSQL;

public class Obj_Puntos_De_Venta_De_Tiempo_Aire {
	private int folio;
	private String Nombre_Pc;
	private String Establecimiento;
	private String Nombre_Punto_Venta_TA;
	private int status;
	public int getFolio() {
		return folio;
	}
	public void setFolio(int folio) {
		this.folio = folio;
	}
	public String getNombre_Pc() {
		return Nombre_Pc;
	}
	public void setNombre_Pc(String nombre_Pc) {
		Nombre_Pc = nombre_Pc;
	}
	public String getEstablecimiento() {
		return Establecimiento;
	}
	public void setEstablecimiento(String establecimiento) {
		Establecimiento = establecimiento;
	}
	public String getNombre_Punto_Venta_TA() {
		return Nombre_Punto_Venta_TA;
	}
	public void setNombre_Punto_Venta_TA(String nombre_Punto_Venta_TA) {
		Nombre_Punto_Venta_TA = nombre_Punto_Venta_TA;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	public Obj_Puntos_De_Venta_De_Tiempo_Aire buscar(int folio) {
		return new BuscarSQL().Existepunto_de_venta_establecimiento(folio);	}
	
	public boolean actualizar(int folio){ 
		return new 	ActualizarSQL().PCAPunto_De_Venta_TA_Estab(this,folio); }
	

	public boolean guardar(){ 
		 return new GuardarSQL().Guardar_Punto_Venta_TA(this); }
	
}
