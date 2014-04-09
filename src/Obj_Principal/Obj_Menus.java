package Obj_Principal;

import java.util.Vector;
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
	
//	@SuppressWarnings("rawtypes")
//	public Vector getMenus(){
//		return new MenuSQL().getMenus();
//	}
	
//	public int getMaxNivel(){
//		return new MenuSQL().getMaxNivel();
//	}
//	@SuppressWarnings("rawtypes")
//	public Vector getSubMenus(int key){
//		return new MenuSQL().getSubMenus(key);
//	}
}
