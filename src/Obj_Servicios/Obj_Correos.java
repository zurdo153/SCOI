package Obj_Servicios;

import java.sql.SQLException;

import Conexiones_SQL.BuscarSQL;

public class Obj_Correos {
	int cantidad_de_correos=0;
	String correos="";
	String fecha_guardado ="";
	public int getCantidad_de_correos() {
		return cantidad_de_correos;
	}
	public void setCantidad_de_correos(int cantidad_de_correos) {
		this.cantidad_de_correos = cantidad_de_correos;
	}
	public String getCorreos() {
		return correos;
	}
	public void setCorreos(String correos) {
		this.correos = correos;
	}
	public String getFecha_guardado() {
		return fecha_guardado;
	}
	public void setFecha_guardado(String fecha_guardado) {
		this.fecha_guardado = fecha_guardado;
	}
	
	public Obj_Correos buscar_correos(int transaccion, String departamento){
		try {
			return new BuscarSQL().correos_por_transaccion_y_departamento(transaccion,departamento);
		}catch (SQLException e) {e.printStackTrace();}
		return this; 
	}
}
