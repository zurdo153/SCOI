package Obj_Auditoria;

import Conexiones_SQL.GuardarSQL;

public class Obj_Pedido_De_Monedas {
	
	int folioUsuario ;
	String status_pedido;
	Object[][] matriz;
	String observacion;
	String empleado_entrego;
	
	public Obj_Pedido_De_Monedas(){
		folioUsuario = 0;	status_pedido="";		matriz = null;
		observacion="";		empleado_entrego="";
	}

	public int getFolioUsuario() {
		return folioUsuario;
	}

	public void setFolioUsuario(int folioUsuario) {
		this.folioUsuario = folioUsuario;
	}

	public String getStatus_pedido() {
		return status_pedido;
	}

	public void setStatus_pedido(String status_pedido) {
		this.status_pedido = status_pedido;
	}

	public Object[][] getMatriz() {
		return matriz;
	}

	public void setMatriz(Object[][] matriz) {
		this.matriz = matriz;
	}
	
	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public String getEmpleado_entrego() {
		return empleado_entrego;
	}

	public void setEmpleado_entrego(String empleado_entrego) {
		this.empleado_entrego = empleado_entrego;
	}

	//	deposito
	public boolean guardar(String folio_Guardado){ 
		return new GuardarSQL().Guardar_Pedido_De_Monedas(this,folio_Guardado);
	}

}
