package Obj_Checador;

import java.sql.SQLException;

import Conexiones_SQL.ActualizarSQL;
import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.GuardarSQL;

public class Obj_Chacador_De_Embarque_De_Pedidos {
	
	int folio;
	String folio_transferencia;
	String folio_pedido;
	String estab_surte;
	String estab_recibe;			
	int folio_encagado_asigno_embarque;
	int folio_chofer;
	String no_cincho;
	int no_carro;
	String status;
//	private fecha_salida datetime DEFAULT (GETDATE()),
//	private fecha_llegada datetime DEFAULT ('01/01/1900'),
	String pc_salida_ip;
	String pc_salida_nombre;
	String pc_llegada_ip;
	String pc_llegada_nombre;
    
	public Obj_Chacador_De_Embarque_De_Pedidos(){
		folio =0;		folio_transferencia="";		folio_pedido="";		estab_surte="";		estab_recibe="";		folio_encagado_asigno_embarque=0;
		folio_chofer=0;		no_cincho="";		no_carro=0;		status="";		pc_salida_ip="";		pc_salida_nombre="";		pc_llegada_ip="";
		pc_llegada_nombre="";
	}

	public int getFolio() {
		return folio;
	}

	public void setFolio(int folio) {
		this.folio = folio;
	}

	public String getFolio_transferencia() {
		return folio_transferencia;
	}

	public void setFolio_transferencia(String folio_transferencia) {
		this.folio_transferencia = folio_transferencia;
	}

	public String getFolio_pedido() {
		return folio_pedido;
	}

	public void setFolio_pedido(String folio_pedido) {
		this.folio_pedido = folio_pedido;
	}

	public String getEstab_surte() {
		return estab_surte;
	}

	public void setEstab_surte(String estab_surte) {
		this.estab_surte = estab_surte;
	}

	public String getEstab_recibe() {
		return estab_recibe;
	}

	public void setEstab_recibe(String estab_recibe) {
		this.estab_recibe = estab_recibe;
	}

	public int getFolio_encagado_asigno_embarque() {
		return folio_encagado_asigno_embarque;
	}

	public void setFolio_encagado_asigno_embarque(int folio_encagado_asigno_embarque) {
		this.folio_encagado_asigno_embarque = folio_encagado_asigno_embarque;
	}

	public int getFolio_chofer() {
		return folio_chofer;
	}

	public void setFolio_chofer(int folio_chofer) {
		this.folio_chofer = folio_chofer;
	}

	public String getNo_cincho() {
		return no_cincho;
	}

	public void setNo_cincho(String no_cincho) {
		this.no_cincho = no_cincho;
	}

	public int getNo_carro() {
		return no_carro;
	}

	public void setNo_carro(int no_carro) {
		this.no_carro = no_carro;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPc_salida_ip() {
		return pc_salida_ip;
	}

	public void setPc_salida_ip(String pc_salida_ip) {
		this.pc_salida_ip = pc_salida_ip;
	}

	public String getPc_salida_nombre() {
		return pc_salida_nombre;
	}

	public void setPc_salida_nombre(String pc_salida_nombre) {
		this.pc_salida_nombre = pc_salida_nombre;
	}

	public String getPc_llegada_ip() {
		return pc_llegada_ip;
	}

	public void setPc_llegada_ip(String pc_llegada_ip) {
		this.pc_llegada_ip = pc_llegada_ip;
	}

	public String getPc_llegada_nombre() {
		return pc_llegada_nombre;
	}

	public void setPc_llegada_nombre(String pc_llegada_nombre) {
		this.pc_llegada_nombre = pc_llegada_nombre;
	}

//	public Obj_Chacador_De_Embarque_De_Pedidos buscar(int folio){
//	try {
//		return new BuscarSQL().diaInA(folio);
//	} catch (SQLException e) {
//		e.printStackTrace();
//	}
//	return null; 
//}

	public boolean guardar(){ return new GuardarSQL().Guardar_Salida_De_Embarque_De_Pedido(this); }
	
//	public boolean actualizar(int folio){ return new ActualizarSQL().DiaInHabil(this,folio); }
	
//	public Obj_Chacador_De_Embarque_De_Pedidos buscar_nuevo(){
//		try {
//			return new BuscarSQL().DiaInA_Nuevo();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return null; 
//	}
	
//	public Obj_Dias_Inhabiles buscar_pues(String nombre){
//		try{
//			return new BuscarSQL().Pues_buscar(nombre); 
//		} catch(SQLException e){
//			
//		}
//		return null;
//	}	
//	
//	public Obj_Dias_Inhabiles buscar_pues(int folio){
//		try{
//			return new BuscarSQL().Pues_buscar(folio); 
//		} catch(SQLException e){
//			
//		}
//		return null;
//	}	
}
