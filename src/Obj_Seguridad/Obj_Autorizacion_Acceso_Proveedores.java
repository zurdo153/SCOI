package Obj_Seguridad;

import java.sql.SQLException;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Cargar_Combo;
import Conexiones_SQL.GuardarSQL;

public class Obj_Autorizacion_Acceso_Proveedores {
	int folio;
	int usuario_solicita;
	String nombre_usuario_solicita;
	String establecimiento;
	String proveedor;
	String chofer;
	String estatus;
	int usuario_autoriza;
	String nombre_usuario_autoriza;
	String motivo_negado_acceso;
	String observaciones;
	String NuevoModifica;

	public Obj_Autorizacion_Acceso_Proveedores(){
		this.folio=0;
		this.usuario_solicita=0;
		this.nombre_usuario_solicita="";
		this.establecimiento="";
		this.proveedor="";
		this.chofer="";
		this.estatus="";
		this.usuario_autoriza=0;
		this.nombre_usuario_autoriza="";
		this.motivo_negado_acceso="";
		this.observaciones="";
		this.NuevoModifica="";
	}

	public int getFolio() {
		return folio;
	}

	public void setFolio(int folio) {
		this.folio = folio;
	}

	public int getUsuario_solicita() {
		return usuario_solicita;
	}

	public void setUsuario_solicita(int usuario_solicita) {
		this.usuario_solicita = usuario_solicita;
	}

	public String getNombre_usuario_solicita() {
		return nombre_usuario_solicita;
	}

	public void setNombre_usuario_solicita(String nombre_usuario_solicita) {
		this.nombre_usuario_solicita = nombre_usuario_solicita;
	}

	public String getEstablecimiento() {
		return establecimiento;
	}

	public void setEstablecimiento(String establecimiento) {
		this.establecimiento = establecimiento;
	}

	public String getProveedor() {
		return proveedor;
	}

	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}

	public String getChofer() {
		return chofer;
	}

	public void setChofer(String chofer) {
		this.chofer = chofer;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public int getUsuario_autoriza() {
		return usuario_autoriza;
	}

	public void setUsuario_autoriza(int usuario_autoriza) {
		this.usuario_autoriza = usuario_autoriza;
	}

	public String getNombre_usuario_autoriza() {
		return nombre_usuario_autoriza;
	}

	public void setNombre_usuario_autoriza(String nombre_usuario_autoriza) {
		this.nombre_usuario_autoriza = nombre_usuario_autoriza;
	}

	public String getMotivo_negado_acceso() {
		return motivo_negado_acceso;
	}

	public void setMotivo_negado_acceso(String motivo_negado_acceso) {
		this.motivo_negado_acceso = motivo_negado_acceso;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getNuevoModifica() {
		return NuevoModifica;
	}

	public void setNuevoModifica(String nuevoModifica) {
		NuevoModifica = nuevoModifica;
	}
	
	public Obj_Autorizacion_Acceso_Proveedores Valida_autorizacion(String folio_solicitud){
		return new BuscarSQL().buscar_Datos_Autorizacion_Proveedor(folio_solicitud);
	}

	
//	public String[][] refrescar_tablas(){
//		return new BuscarSQL().TablaProveedores();
//	}
	
	public boolean Guardar_Captura(){
		return new GuardarSQL().Guardar_Solicitud_De_Acceso_A_Proveedores(this);
	}

	public String[] Combo_Estatus(){
		try {
			return new Cargar_Combo().Combos("proveedores_estatus_autorizacion");
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String[] Combo_Negados(){
		try {
			return new Cargar_Combo().Combos("proveedores_motivos_negado");
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String[] Combo_Estatus_Todos(){
		try {
			return new Cargar_Combo().Combos("proveedores_estatus_autorizacion_todos");
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
}
