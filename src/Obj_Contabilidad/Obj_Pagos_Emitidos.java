package Obj_Contabilidad;

import java.sql.SQLException;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Cargar_Combo;
import Conexiones_SQL.GuardarSQL;

public class Obj_Pagos_Emitidos {

	int folio;
	String cuenta;
	String cheque;
	String fecha;
	double importe;
	String status_cobro;
	String observacion;
	String bandera;
	
	public Obj_Pagos_Emitidos() {
		folio = 0;			cuenta = "";			cheque = "";			fecha = "";			
		importe = 0;		status_cobro = "";		observacion = "";		bandera = "";
	}

	public int getFolio() {
		return folio;
	}

	public void setFolio(int folio) {
		this.folio = folio;
	}

	public String getCuenta() {
		return cuenta;
	}

	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}

	public String getCheque() {
		return cheque;
	}

	public void setCheque(String cheque) {
		this.cheque = cheque;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public double getImporte() {
		return importe;
	}

	public void setImporte(double importe) {
		this.importe = importe;
	}

	public String getStatus_cobro() {
		return status_cobro;
	}

	public void setStatus_cobro(String status_cobro) {
		this.status_cobro = status_cobro;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public String getBandera() {
		return bandera;
	}

	public void setBandera(String bandera) {
		this.bandera = bandera;
	}
	
	public boolean guardar(){
		return new GuardarSQL().Guardar_Pagos_Emitidos(this);
	}
	
	public Object[] cuentas(){
		try {
			return new Cargar_Combo().movimientoEnCuentas();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Double TotalPagosEmitidos(String fCuenta){
			return new BuscarSQL().TotalDePagosEmitidos(fCuenta);
	}
	
	public boolean cancelarPagosEmitidos(String xml){
		return new GuardarSQL().Cancelacion_De_Pagos_Emitidos(xml);
	}

}
