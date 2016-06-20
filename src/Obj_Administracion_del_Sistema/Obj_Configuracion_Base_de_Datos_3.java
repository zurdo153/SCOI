package Obj_Administracion_del_Sistema;

import java.io.IOException;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.GuardarSQL;

public class Obj_Configuracion_Base_de_Datos_3 {
	private String direccionIPV4;
	private String nombreBD;
	private String usuario;
	private String contrasena;
	
	public Obj_Configuracion_Base_de_Datos_3(){
		this.direccionIPV4=""; this.nombreBD=""; this.usuario=""; this.contrasena="";
	}

	public String getDireccionIPV4() {
		return direccionIPV4;
	}

	public void setDireccionIPV4(String direccionIPV4) {
		this.direccionIPV4 = direccionIPV4;
	}

	public String getNombreBD() {
		return nombreBD;
	}

	public void setNombreBD(String nombreBD) {
		this.nombreBD = nombreBD;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	
	public boolean guardar(){ 
		return new GuardarSQL().Guardar_ConfigBD_3(this); 
	}
	
	public Obj_Configuracion_Base_de_Datos_3 buscar()
    {
		Obj_Configuracion_Base_de_Datos_3 config = new Obj_Configuracion_Base_de_Datos_3();
    	try{
    		config = new BuscarSQL().Conexion_BD_3();
    	}catch(IOException e){
    		e.printStackTrace();
    	}
    	return config;
  }
}
