package Obj_Administracion_del_Sistema;

import java.sql.SQLException;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Cargar_Combo;
import Conexiones_SQL.SubMenusSQL;

public class Obj_SubMenus {
	private int folio;
	private String nombre_completo;
	private String contrasena;
	private int status;
	private String nombre;
	private int cantidad_submenus;

	public int getFolio() {
		return folio;
	}

	public void setFolio(int folio) {
		this.folio = folio;
	}

	public String getNombre_completo() {
		return nombre_completo;
	}

	public void setNombre_completo(String nombre_completo) {
		this.nombre_completo = nombre_completo;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getCantidad_submenus() {
		return cantidad_submenus;
	}

	public void setCantidad_submenus(int cantidad_submenus) {
		this.cantidad_submenus = cantidad_submenus;
	}

	public Obj_SubMenus(){
		this.nombre="";nombre_completo=""; contrasena="";status=0;
	}
	
	public String[] Relacion_de_SubMenus(int menu_pricipal) {
		try {
			return new SubMenusSQL().Relacion_de_SubMenus(menu_pricipal);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; 
	}
	public String[][] UsuarioMatriz(){
		return new BuscarSQL().getUsuarioPermisos();
	}
	
	public String[] Combo_Usuarios(){
		try {
			return new Cargar_Combo().Usuario();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	return null; }
	
	public Obj_SubMenus BuscarUsuariosua(int folio_empleado){
		try {
			return new SubMenusSQL().BuscarUsuariosusua(folio_empleado);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; 
	}
	

}
