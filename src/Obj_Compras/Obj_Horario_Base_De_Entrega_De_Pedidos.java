package Obj_Compras;

import java.sql.SQLException;
import java.util.Vector;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.BuscarTablasModel;
import Conexiones_SQL.GuardarSQL;

public class Obj_Horario_Base_De_Entrega_De_Pedidos {
	private int folio;
	private String establecimientoOrigen;
	private String establecimientoDestino;
	private String status;
	private int lunes;
	private int martes;
	private int miercoles;
	private int jueves;
	private int viernes;
	private int sabado;
	private int domingo;
	private String hora;
	
	public Obj_Horario_Base_De_Entrega_De_Pedidos(){
		this.folio=0; this.establecimientoOrigen ="";  this.establecimientoDestino=""; this.status = "";
		this.lunes=0; this.martes=0; this.miercoles=0; this.jueves=0; this.viernes=0;  this.sabado=0; this.domingo=0;
		this.hora="0:00";
	}

	public int getFolio() {
		return folio;
	}

	public void setFolio(int folio) {
		this.folio = folio;
	}

	public String getEstablecimientoOrigen() {
		return establecimientoOrigen;
	}

	public void setEstablecimientoOrigen(String establecimientoOrigen) {
		this.establecimientoOrigen = establecimientoOrigen;
	}
	
	public String getEstablecimientoDestino() {
		return establecimientoDestino;
	}

	public void setEstablecimientoDestino(String establecimientoDestino) {
		this.establecimientoDestino = establecimientoDestino;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getLunes() {
		return lunes;
	}

	public void setLunes(int lunes) {
		this.lunes = lunes;
	}

	public int getMartes() {
		return martes;
	}

	public void setMartes(int martes) {
		this.martes = martes;
	}

	public int getMiercoles() {
		return miercoles;
	}

	public void setMiercoles(int miercoles) {
		this.miercoles = miercoles;
	}

	public int getJueves() {
		return jueves;
	}

	public void setJueves(int jueves) {
		this.jueves = jueves;
	}

	public int getViernes() {
		return viernes;
	}

	public void setViernes(int viernes) {
		this.viernes = viernes;
	}

	public int getSabado() {
		return sabado;
	}

	public void setSabado(int sabado) {
		this.sabado = sabado;
	}

	public int getDomingo() {
		return domingo;
	}

	public void setDomingo(int domingo) {
		this.domingo = domingo;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String buscar_nuevo(){ 
		return new BuscarSQL().folio_Horario_De_Entrega_De_Pedidos(); 
	}
	
	public Obj_Horario_Base_De_Entrega_De_Pedidos buscar(int folio) {
		try {
			return new BuscarSQL().horario_entrega_pedido(folio);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; 
	}
	
	public int buscar_establecimiento_y_hora_con_otro_registro(String establecimientoOrigen,String establecimientoDestino,String hora){ 
		return new BuscarSQL().validar_coincidencia(establecimientoOrigen, establecimientoDestino, hora); 
	}
	
	@SuppressWarnings("rawtypes")
	public Vector validar_dias_disponibles(String cadenaDias,String establecimientoOrigen, String establecimientoDestino,int folio_reg){ 
		return new BuscarSQL().validar_coincidencia_de_dias_para_pedidos(cadenaDias, establecimientoOrigen, establecimientoDestino, folio_reg); 
	}
	
	public boolean guardar(String movimiento){ 
		return new GuardarSQL().Guardar_Horario_De_Surtido_De_Pedido(this,movimiento); 
	}
	
	public Object[][] get_tabla_model_horario_base_de_entrega(){
		return new BuscarTablasModel().tabla_model_horarios_de_entrega_de_pedidos();
	}
	
}
