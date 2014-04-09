package Conexiones_SQL;

import java.sql.*;
import java.util.*;

public class MenuSQL {
	
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	public Vector getMenus(){
//		Vector vec = new Vector();
//		String query = "select folio, nombre, nivel from tb_menu where nivel = 1 order by nombre";
//		try {				
//			Statement s = new Connexion().conexion().createStatement();
//			ResultSet rs = s.executeQuery(query);
//			while(rs.next())
//				vec.add(rs.getString("nombre"));
//			
//			s.close();
//		} catch (SQLException e1) {
//			e1.printStackTrace();
//		}
//		return vec;
//	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Vector getMenusNivel (int folio){
		Vector vec = new Vector();
		String query = "exec sp_obtener_menu_nivel "+folio ;
		try {				
			Statement s = new Connexion().conexion().createStatement();
			ResultSet rs = s.executeQuery(query);
			while(rs.next())
				vec.add(rs.getString("nivel")+","+rs.getString("folio")+","+rs.getString("nombre")+ "," + rs.getString("Dependiente_Id")+ "," + rs.getString("Dependiente"));

			s.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return vec;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Vector getSubMenusNivel  (int folio){
		Vector vec = new Vector();
		
		String query = "sp_obtener_sub_menus_de_usuario "+folio;
		try {				
			Statement s = new Connexion().conexion().createStatement();
			ResultSet rs = s.executeQuery(query);
			while(rs.next())
				vec.add(rs.getString("folio")+","+rs.getString("nombre")+","+rs.getString("menu_id"));

			s.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return vec;
	}
	
	
	
//	public int getMaxNivel(){
//		int vec = 0;
//		String query = "select max(nivel)as nivel  from tb_menu";
//		try {				
//			Statement s = new Connexion().conexion().createStatement();
//			ResultSet rs = s.executeQuery(query);
//			while(rs.next())
//				vec = rs.getInt("nivel");
//			
//			s.close();
//		} catch (SQLException e1) {
//			e1.printStackTrace();
//		}
//		return vec;
//	}
	
	
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	public Vector getSubMenus(int key){
//		Vector vec = new Vector();
//		String query = "select "+ 
//							"tb_menu.nombre as Menu, "+ 
//							"tb_submenu.nombre as SubMenu "+ 
//					   "from tb_submenu "+ 
//					   		"inner join tb_menu on tb_menu.folio = tb_submenu.menu_id "+
//					   "where tb_submenu.menu_id =" + key +
//					   " order by tb_submenu.nombre";
//		
//		try {				
//			Statement s = new Connexion().conexion().createStatement();
//			ResultSet rs = s.executeQuery(query);
//			while(rs.next())
//				vec.add(rs.getString("Menu")+","+rs.getString("SubMenu"));
//			
//			s.close();
//		} catch (SQLException e1) {
//			e1.printStackTrace();
//		}
//		return vec;
//	}
}
