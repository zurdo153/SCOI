package Obj_Inventarios;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Cargar_Combo;

public class Obj_Generar_Cargar_Pedido_De_Maximos_Y_Minimos {

	int folio_pedido;
	String usuario;
	int cant_prod;
	int cant_pz;
	
	public Obj_Generar_Cargar_Pedido_De_Maximos_Y_Minimos() {
		folio_pedido=0;
		usuario = "";
		cant_prod=0;
		cant_pz=0;
	}

	public int getFolio_pedido() {
		return folio_pedido;
	}

	public void setFolio_pedido(int folio_pedido) {
		this.folio_pedido = folio_pedido;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public int getCant_prod() {
		return cant_prod;
	}

	public void setCant_prod(int cant_prod) {
		this.cant_prod = cant_prod;
	}

	public int getCant_pz() {
		return cant_pz;
	}

	public void setCant_pz(int cant_pz) {
		this.cant_pz = cant_pz;
	}

	public String[] Areas_tipo_distrib(){
		return new Cargar_Combo().Areas_tipo_distribucion();
	}
	
	public Obj_Generar_Cargar_Pedido_De_Maximos_Y_Minimos buscar(String estab_solicita, String estab_surte,String areas){
		return new BuscarSQL().existe_pedido_de_maximos_y_minimos_vigente(estab_solicita,estab_surte,areas);
	}
	
	public int folio_pedido(String boton, String estab_solicita, String estab_surte,String areas){
		return new BuscarSQL().folio_pedido_por_maximos_y_minimos(boton, estab_solicita, estab_surte, areas);
	}
}
