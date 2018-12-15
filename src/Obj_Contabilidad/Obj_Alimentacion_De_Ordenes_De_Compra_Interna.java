package Obj_Contabilidad;

import java.sql.SQLException;

import Conexiones_SQL.ActualizarSQL;
import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.GuardarSQL;

public class Obj_Alimentacion_De_Ordenes_De_Compra_Interna {

	int folio;
	int folio_persona_solicita;
	String persona_solicita;//------------------------------------------
	String tipo_de_solicitante;
	int folio_servicio;
	String servicio;//------------------------------------------
	String uso_de_mercancia;

	String lista_de_productos;// xml,--cod_prod, descripcionManual, unidad, cantidad_solicitada, cantidad_surtida
	Object[][] arreglo_de_productos;
	
	String status;
	String  estab_surte;
	String estab_destino;
	int folio_chofer;
	String ObservacionSurte;
	String tipo_de_chofer;
    String tipo_orden;

	

	public Obj_Alimentacion_De_Ordenes_De_Compra_Interna() {
		folio = 0;
		folio_persona_solicita = 0;
		persona_solicita = "";//------------------------------------------
		tipo_de_solicitante = "";
		folio_servicio = 0;
		servicio = "";//------------------------------------------
		uso_de_mercancia = "";
		status="";
		tipo_orden="";
		lista_de_productos = "";// xml,--cod_prod, descripcionManual, unidad, cantidad_solicitada, cantidad_surtida
		arreglo_de_productos = null;
		estab_destino = "";
		estab_surte= "";
		folio_chofer =0;
		ObservacionSurte="";
		tipo_de_chofer="";
	}

	public String getTipo_orden() {
		return tipo_orden;
	}

	public void setTipo_orden(String tipo_orden) {
		this.tipo_orden = tipo_orden;
	}

	public int getFolio() {
		return folio;
	}

	public void setFolio(int folio) {
		this.folio = folio;
	}

	public int getFolio_persona_solicita() {
		return folio_persona_solicita;
	}

	public void setFolio_persona_solicita(int folio_persona_solicita) {
		this.folio_persona_solicita = folio_persona_solicita;
	}

	public String getPersona_solicita() {
		return persona_solicita;
	}

	public void setPersona_solicita(String persona_solicita) {
		this.persona_solicita = persona_solicita;
	}

	public String getTipo_de_solicitante() {
		return tipo_de_solicitante;
	}

	public void setTipo_de_solicitante(String tipo_de_solicitante) {
		this.tipo_de_solicitante = tipo_de_solicitante;
	}

	public int getFolio_servicio() {
		return folio_servicio;
	}

	public void setFolio_servicio(int folio_servicio) {
		this.folio_servicio = folio_servicio;
	}

	public String getServicio() {
		return servicio;
	}

	public void setServicio(String servicio) {
		this.servicio = servicio;
	}

	public String getUso_de_mercancia() {
		return uso_de_mercancia;
	}

	public void setUso_de_mercancia(String uso_de_mercancia) {
		this.uso_de_mercancia = uso_de_mercancia;
	}

	public String getLista_de_productos() {
		return lista_de_productos;
	}

	public void setLista_de_productos(String lista_de_productos) {
		this.lista_de_productos = lista_de_productos;
	}

	public Object[][] getArreglo_de_productos() {
		return arreglo_de_productos;
	}

	public void setArreglo_de_productos(Object[][] arreglo_de_productos) {
		this.arreglo_de_productos = arreglo_de_productos;
	}

	public String getEstab_destino() {
		return estab_destino;
	}

	public void setEstab_destino(String estab_destino) {
		this.estab_destino = estab_destino;
	}
	
	public String getEstab_surte() {
		return estab_surte;
	}

	public int getFolio_chofer() {
		return folio_chofer;
	}

	public void setFolio_chofer(int folio_chofer) {
		this.folio_chofer = folio_chofer;
	}

	public void setEstab_surte(String estab_surte) {
		this.estab_surte = estab_surte;
	}

	public String getObservacionSurte() {
		return ObservacionSurte;
	}

	public void setObservacionSurte(String observacionSurte) {
		ObservacionSurte = observacionSurte;
	}

	public String getTipo_de_chofer() {
		return tipo_de_chofer;
	}

	public void setTipo_de_chofer(String tipo_de_chofer) {
		this.tipo_de_chofer = tipo_de_chofer;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Obj_Alimentacion_De_Ordenes_De_Compra_Interna buscar(int folio) { 
		try {
			return new BuscarSQL().ordenDeCompraInterna(folio);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; 
	}
	
	public boolean guardar(String movimiento){
		return new GuardarSQL().Guardar_Orden_De_Compra_Interna(this, movimiento);
	}
	
	public String surtir(){
		try {
			return new BuscarSQL().Surtir_Orden_De_Compra_Interna(this);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public boolean autorizacion(int folio, String status){
		return new ActualizarSQL().Autorizar_Orden_De_Compra_Interna(folio, status);
	}
}
