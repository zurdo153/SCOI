package Obj_Administracion_del_Sistema;

import java.sql.SQLException;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.SubMenusSQL;

public class Obj_SubMenus {
	String nombre;
	public Obj_SubMenus(){
		this.nombre="";
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre.toString();
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
	
//	public String[] SubMenuAdministracion_del_sistema() {
//		try {
//			return new SubMenusSQL().Administracion_del_Sistema();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return null; 
//	}
//	public String[] SubMenuAuditoria() {
//		try {
//			return new SubMenusSQL().Auditoria();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return null; 
//	}

//	public String[] SubMenuCuadrantes_Alimentacion() {
//		try {
//			return new SubMenusSQL().Cuadrantes_Alimentacion();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return null; 
//	}
//	
//	public String[] SubMenuCuadrantes_Catalogo() {
//		try {
//			return new SubMenusSQL().Cuadrantes_Catalogo();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return null; 
//	}
//	
//	public String[] SubMenuCuadrantes_Reportes() {
//		try {
//			return new SubMenusSQL().Cuadrantes_Reportes();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return null; 
//	}
//	
//	public String[] SubMenuLista_Raya_Alimentacion () {
//		try {
//			return new SubMenusSQL().Lista_Raya_Alimentacion ();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return null; 
//	}
//	
//	public String[] SubMenuLista_Raya_Comparaciones() {
//		try {
//			return new SubMenusSQL().Lista_Raya_Comparaciones();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return null; 
//	}
//	
//	public String[] SubMenuLista_Raya_Autorizaciones() {
//		try {
//			return new SubMenusSQL().Lista_Raya_Autorizaciones();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return null; 
//	}
//	
//	public String[] SubMenuLista_Raya_Departamento_Cortes() {
//		try {
//			return new SubMenusSQL().Lista_Raya_Departamento_Cortes();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return null; 
//	}
//	
//	public String[] SubMenuLista_Raya_Reportes() {
//		try {
//			return new SubMenusSQL().Lista_Raya_Reportes();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return null; 
//	}
//	public String[] SubMenuchecador() {
//		try {
//			return new SubMenusSQL().Checador();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return null; 
//	}
	

}
