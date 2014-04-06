package Obj_Principal;

import java.util.Vector;
import Conexiones_SQL.MenuSQL;

public class Obj_Menus {
	
	public Obj_Menus(){}
	
	@SuppressWarnings("rawtypes")
	public Vector getMenus(){
		return new MenuSQL().getMenus();
	}
	
	@SuppressWarnings("rawtypes")
	public Vector getMenusNivel(){
		return new MenuSQL().getMenusNivel();
	}
	
	@SuppressWarnings("rawtypes")
	public Vector getSubmenuNivel(){
		return new MenuSQL().getSubMenusNivel();
	}
	public int getMaxNivel(){
		return new MenuSQL().getMaxNivel();
	}
	@SuppressWarnings("rawtypes")
	public Vector getSubMenus(int key){
		return new MenuSQL().getSubMenus(key);
	}
}
