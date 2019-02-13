package Obj_Contabilidad;

import java.sql.SQLException;

import Conexiones_SQL.Cargar_Combo;
import Conexiones_SQL.GuardarSQL;

public class Obj_Saldo_Banco_Interno {
	
	int	folio =0;
	String observaciones="";
	int folio_usuario=0;
	float importe=0;
	String cuenta="";
    String estatus="";	  
	String guardar_actualizar="";
	String[][] tabla=null;
	String  tipo_movimiento="";
	
	public String getTipo_movimiento() {
		return tipo_movimiento;
	}

	public void setTipo_movimiento(String tipo_movimiento) {
		this.tipo_movimiento = tipo_movimiento;
	}

	public float getImporte() {
		return importe;
	}

	public void setImporte(float importe) {
		this.importe = importe;
	}

	public int getFolio() {
		return folio;
	}

	public void setFolio(int folio) {
		this.folio = folio;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public int getFolio_usuario() {
		return folio_usuario;
	}

	public void setFolio_usuario(int folio_usuario) {
		this.folio_usuario = folio_usuario;
	}

	public String getCuenta() {
		return cuenta;
	}

	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public String getGuardar_actualizar() {
		return guardar_actualizar;
	}

	public void setGuardar_actualizar(String guardar_actualizar) {
		this.guardar_actualizar = guardar_actualizar;
	}

	public String[][] getTabla() {
		return tabla;
	}

	public void setTabla(String[][] tabla) {
		this.tabla = tabla;
	}

	public String[] Combo_Cuentas_Reporte() {
		try {
			return new Cargar_Combo().Combos("Cuentas_Reporte_Banco_Interno");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; 
	}
	
	public String[] Combo_Cuentas() {
		try {
			return new Cargar_Combo().Combos("cuentas");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; 
	}

	public String[] Combo_Cuentaspago() {
		try {
			return new Cargar_Combo().Combos("cuentaspago");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; 
	}
	
	public String[] Combo_Cuentas_reposicion_caja_chica() {
		try {
			return new Cargar_Combo().Combos("reposicioncajachica");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; 
	}
	
	public String[] Combo_Cuentas_Vale_Dola() {
		try {
			return new Cargar_Combo().Combos("valedola");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; 
	}
	
	
	public Obj_Saldo_Banco_Interno GuardarActualizar(){ 
		return new GuardarSQL().Guardar_Saldo_Banco_Interno(this); }
	
	public Obj_Saldo_Banco_Interno GuardarActualizar_Egreso(){ 
		return new GuardarSQL().Guardar_Saldo_Banco_Interno_Egreso(this); }

}

