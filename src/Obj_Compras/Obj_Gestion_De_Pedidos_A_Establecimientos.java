package Obj_Compras;

import java.sql.SQLException;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.GuardarSQL;

public class Obj_Gestion_De_Pedidos_A_Establecimientos {

	String folio_pedido = "";
	String origen="";
	String destino ="";
	String folio_usuario = "";
	String Usuario = "";
	String clasificador="";
	String status_pedido="";
	
	Object[][] matriz = null;
	
	public Obj_Gestion_De_Pedidos_A_Establecimientos() {
	}

	public String getFolio_pedido() {
		return folio_pedido;
	}

	public void setFolio_pedido(String folio_pedido) {
		this.folio_pedido = folio_pedido;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public String getFolio_usuario() {
		return folio_usuario;
	}

	public void setFolio_usuario(String folio_usuario) {
		this.folio_usuario = folio_usuario;
	}

	public String getUsuario() {
		return Usuario;
	}

	public void setUsuario(String usuario) {
		Usuario = usuario;
	}

	public Object[][] getMatriz() {
		return matriz;
	}

	public void setMatriz(Object[][] matriz) {
		this.matriz = matriz;
	}
	
	public String getClasificador() {
		return clasificador;
	}

	public void setClasificador(String clasificador) {
		this.clasificador = clasificador;
	}

	public String getStatus_pedido() {
		return status_pedido;
	}

	public void setStatus_pedido(String status_pedido) {
		this.status_pedido = status_pedido;
	}

	public Obj_Gestion_De_Pedidos_A_Establecimientos buscar(String folio_pedido){
		try {
			return new BuscarSQL().datosDePedido(folio_pedido);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean guardar_actualizar(String movimiento){
		return new GuardarSQL().GuardarPedido(this,movimiento);
	}

}
