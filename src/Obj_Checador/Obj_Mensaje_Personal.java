package Obj_Checador;

import java.sql.SQLException;

import Conexiones_SQL.ActualizarSQL;
import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.GuardarSQL;

public class Obj_Mensaje_Personal {
	
	int folioMensaje;
	String fechaInicial;
	String fechaFin;
	String asunto;
	String mensaje;
	String ruta_imagen_mensaje;
	String color_fuente;
	int status;
	
	public Obj_Mensaje_Personal(){
		folioMensaje=0;		fechaInicial="";	fechaFin="";	asunto="";		mensaje="";		status=0;	color_fuente="";ruta_imagen_mensaje="";
	}


	public String getRuta_imagen_mensaje() {
		return ruta_imagen_mensaje;
	}


	public void setRuta_imagen_mensaje(String ruta_imagen_mensaje) {
		this.ruta_imagen_mensaje = ruta_imagen_mensaje;
	}


	public String getColor_fuente() {
		return color_fuente;
	}


	public void setColor_fuente(String color_fuente) {
		this.color_fuente = color_fuente;
	}


	public int getFolioMensaje() {
		return folioMensaje;
	}


	public void setFolioMensaje(int folioMensaje) {
		this.folioMensaje = folioMensaje;
	}


	public String getFechaInicial() {
		return fechaInicial;
	}


	public void setFechaInicial(String fechaInicial) {
		this.fechaInicial = fechaInicial;
	}


	public String getFechaFin() {
		return fechaFin;
	}


	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}


	public String getAsunto() {
		return asunto;
	}


	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}


	public String getMensaje() {
		return mensaje;
	}


	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	public boolean guardar_mensaje(){ return new GuardarSQL().Guardar_Mensaje_Personal(this); }
	
	public boolean guardar_Empleado_Mensaje(String[] tabla){ return new GuardarSQL().Guardar_Empleado_Msj(this,tabla); }
	
	public boolean actualizar(int folio){ return new ActualizarSQL().mensajePersonal(this,folio); }
	
	public boolean actualizar2(String[] tabla){ return new ActualizarSQL().mensajePersonal2(this,tabla); }
	
	public int nuevoMensaje(){
		try {
			return new BuscarSQL().NuevoMensajePersonal();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}
	
	public Obj_Mensaje_Personal buscar(int folio){
		try {
			return new BuscarSQL().buscar_mensaje(folio);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; 
	}
}
