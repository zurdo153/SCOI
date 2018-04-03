package Obj_Checador;

import java.sql.SQLException;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.GuardarSQL;

public class Obj_Checador {

	private int folio_empleado;
	private String nombre_empleado;
	private String no_checador;
	private int status;
	private String status_checador;
	private int folio_estab;
	private String establecimiento;
	private int folio_puesto;
	private String puesto;
	private String master_key;
	private boolean valida_descanso;
	private boolean valida_pc;
	private boolean valida_chequeo_duplicado;
	private boolean valida_checar_dia_dobla;
	private boolean valida_checar_salida_a_comer;
	
	private String forma_de_checar;
	private byte[] huella_1;
	private byte[] huella_2;
	
	private Object[][] arreglo_mensaje;
	
	public Obj_Checador() {
		folio_empleado = 0;		nombre_empleado="";		no_checador="";		status = 0;		folio_estab = 0;
		establecimiento="";		folio_puesto = 0;		puesto="";		master_key="";		valida_descanso=false;
		valida_pc=false;		valida_chequeo_duplicado=false;			valida_checar_dia_dobla=false;		
		valida_checar_salida_a_comer=false;		arreglo_mensaje= null;
		
		forma_de_checar="";		huella_1=null;		huella_2=null;
	}

	public int getFolio_empleado() {
		return folio_empleado;
	}

	public void setFolio_empleado(int folio_empleado) {
		this.folio_empleado = folio_empleado;
	}

	public String getNombre_empleado() {
		return nombre_empleado;
	}

	public void setNombre_empleado(String nombre_empleado) {
		this.nombre_empleado = nombre_empleado;
	}

	public String getNo_checador() {
		return no_checador;
	}

	public void setNo_checador(String no_checador) {
		this.no_checador = no_checador;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getStatus_checador() {
		return status_checador;
	}

	public void setStatus_checador(String status_checador) {
		this.status_checador = status_checador;
	}

	public int getFolio_estab() {
		return folio_estab;
	}

	public void setFolio_estab(int folio_estab) {
		this.folio_estab = folio_estab;
	}

	public String getEstablecimiento() {
		return establecimiento;
	}

	public void setEstablecimiento(String establecimiento) {
		this.establecimiento = establecimiento;
	}

	public int getFolio_puesto() {
		return folio_puesto;
	}

	public void setFolio_puesto(int folio_puesto) {
		this.folio_puesto = folio_puesto;
	}

	public String getPuesto() {
		return puesto;
	}

	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}

	public String getMaster_key() {
		return master_key;
	}

	public void setMaster_key(String master_key) {
		this.master_key = master_key;
	}

	public boolean isValida_descanso() {
		return valida_descanso;
	}

	public void setValida_descanso(boolean valida_descanso) {
		this.valida_descanso = valida_descanso;
	}

	public boolean isValida_pc() {
		return valida_pc;
	}

	public void setValida_pc(boolean valida_pc) {
		this.valida_pc = valida_pc;
	}

	public boolean isValida_chequeo_duplicado() {
		return valida_chequeo_duplicado;
	}

	public void setValida_chequeo_duplicado(boolean valida_chequeo_duplicado) {
		this.valida_chequeo_duplicado = valida_chequeo_duplicado;
	}

	public boolean isValida_checar_dia_dobla() {
		return valida_checar_dia_dobla;
	}

	public void setValida_checar_dia_dobla(boolean valida_checar_dia_dobla) {
		this.valida_checar_dia_dobla = valida_checar_dia_dobla;
	}

	public boolean isValida_checar_salida_a_comer() {
		return valida_checar_salida_a_comer;
	}

	public void setValida_checar_salida_a_comer(boolean valida_checar_salida_a_comer) {
		this.valida_checar_salida_a_comer = valida_checar_salida_a_comer;
	}

	public Object[][] getArreglo_mensaje() {
		return arreglo_mensaje;
	}

	public void setArreglo_mensaje(Object[][] arreglo_mensaje) {
		this.arreglo_mensaje = arreglo_mensaje;
	}
	
	public String getForma_de_checar() {
		return forma_de_checar;
	}

	public void setForma_de_checar(String forma_de_checar) {
		this.forma_de_checar = forma_de_checar;
	}

	public byte[] getHuella_1() {
		return huella_1;
	}

	public void setHuella_1(byte[] huella_1) {
		this.huella_1 = huella_1;
	}

	public byte[] getHuella_2() {
		return huella_2;
	}

	public void setHuella_2(byte[] huella_2) {
		this.huella_2 = huella_2;
	}

	public Obj_Checador buscar(int folio_empleado){
		try {
			return new BuscarSQL().buscar_datos_para_checador(folio_empleado);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean insertar_checada(int folio,String t_entrada,int tipo_salida_comer){
		return new GuardarSQL().Insert_Checada(folio,t_entrada,tipo_salida_comer);
	}
}
