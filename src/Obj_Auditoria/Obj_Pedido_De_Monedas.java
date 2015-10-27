package Obj_Auditoria;

import Conexiones_SQL.GuardarSQL;

public class Obj_Pedido_De_Monedas {
	
	int folioUsuario ;
	String status_pedido;
	Object[][] matriz;
	
	public Obj_Pedido_De_Monedas(){
		folioUsuario = 0;	status_pedido="";		matriz = null;
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
	
//	deposito
	public boolean guardar(){ 
		return new GuardarSQL().Guardar_Pedido_De_Monedas(this);
	}

}
