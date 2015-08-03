package IZAGAR_Obj;

import java.sql.SQLException;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Cargar_Combo;

public class Obj_Conciliacion_de_Movimientos_Bancarios_Contra_Contabilidad {
	
	String Cuenta_Bancaria;
	String Banco;
	String Cuenta_Contable;
	String Fecha;
	String Fecha_Conciliacion;

	public String getCuenta_Bancaria() {
		return Cuenta_Bancaria;
	}


	public void setCuenta_Bancaria(String cuenta_Bancaria) {
		Cuenta_Bancaria = cuenta_Bancaria;
	}


	public String getBanco() {
		return Banco;
	}


	public void setBanco(String banco) {
		Banco = banco;
	}


	public String getCuenta_Contable() {
		return Cuenta_Contable;
	}


	public void setCuenta_Contable(String cuenta_Contable) {
		Cuenta_Contable = cuenta_Contable;
	}


	public String getFecha() {
		return Fecha;
	}


	public void setFecha(String fecha) {
		Fecha = fecha;
	}


	public String getFecha_Conciliacion() {
		return Fecha_Conciliacion;
	}


	public void setFecha_Conciliacion(String fecha_Conciliacion) {
		Fecha_Conciliacion = fecha_Conciliacion;
	}


	public String[] Combo_CuentasBancarias(){
		try {
			return new Cargar_Combo().Datos_Combo_CuentasBancarias();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	return null; }
	
	
	public Obj_Conciliacion_de_Movimientos_Bancarios_Contra_Contabilidad buscar_datos_cuenta_Bancaria(String Cuenta_Bancaria) {
		try {
			return new BuscarSQL().datos_Cuenta_Bancaria(Cuenta_Bancaria);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	return null; 
	}
	
	
}
