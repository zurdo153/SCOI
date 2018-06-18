package Obj_Principal;

import java.sql.SQLException;
import java.util.Vector;

import Conexiones_SQL.Cargar_Combo;
import Conexiones_SQL.MenuSQL;

public class Obj_Menus {
	
	public Obj_Menus(){}
	

	@SuppressWarnings("rawtypes")
	public Vector getMenusNivel (int folio){
		return new MenuSQL().getMenusNivel(folio);
	}
	
	@SuppressWarnings("rawtypes")
	public Vector getSubmenuNivel (int folio){
		return new MenuSQL().getSubMenusNivel(folio);
	}
	
	public String[] Combo_Colores() {
		try {
			return new Cargar_Combo().Combos("colores");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; 
	}
	
}
