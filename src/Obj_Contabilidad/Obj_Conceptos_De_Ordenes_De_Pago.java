package Obj_Contabilidad;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.GuardarSQL;

public class Obj_Conceptos_De_Ordenes_De_Pago {
	public int getFolio() {
		return folio;
	}


	public void setFolio(int folio) {
		this.folio = folio;
	}


	public String getConcepto() {
		return Concepto;
	}


	public void setConcepto(String concepto) {
		Concepto = concepto;
	}


	public String getEstatus() {
		return Estatus;
	}


	public void setEstatus(String estatus) {
		Estatus = estatus;
	}


	private int folio;
	private String Concepto;
	private String Estatus;

	

	
//	public boolean actualizar(int folio){ 
//		return new 	ActualizarSQL().PCAPunto_De_Venta_TA_Estab(this,folio); }
//	

	public Obj_Conceptos_De_Ordenes_De_Pago buscar(int folio) {
	return new BuscarSQL().Existe_concepto_de_Orden_de_Compra(folio);	}
	
	public boolean guardar(){ 
		 return new GuardarSQL().Guardar_Concepto_De_Orden_De_Pago(this); }
	
	
}
