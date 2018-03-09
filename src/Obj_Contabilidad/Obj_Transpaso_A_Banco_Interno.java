package Obj_Contabilidad;

import java.sql.SQLException;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Cargar_Combo;
import Conexiones_SQL.GuardarSQL;

public class Obj_Transpaso_A_Banco_Interno {
	
	int	folio =0;
	int folio_empleado_destinatario=0;
	String observaciones="";
	int usuario_realiza_transpaso=0;
	String cuenta="";
    String estatus="";	  
	String guardar_actualizar="";
	String[][] datos=null;
	
	public String getCuenta() {
		return cuenta;
	}
	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}
	public String getGuardar_actualizar() {
		return guardar_actualizar;
	}
	public void setGuardar_actualizar(String guardar_actualizar) {
		this.guardar_actualizar = guardar_actualizar;
	}
	public int getFolio() {
		return folio;
	}
	public void setFolio(int folio) {
		this.folio = folio;
	}
	public int getFolio_empleado_destinatario() {
		return folio_empleado_destinatario;
	}
	public void setFolio_empleado_destinatario(int folio_empleado_destinatario) {
		this.folio_empleado_destinatario = folio_empleado_destinatario;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public int getUsuario_realiza_transpaso() {
		return usuario_realiza_transpaso;
	}
	public void setUsuario_realiza_transpaso(int usuario_realiza_transpaso) {
		this.usuario_realiza_transpaso = usuario_realiza_transpaso;
	}
	public String getEstatus() {
		return estatus;
	}
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
	public String[][] getDatos() {
		return datos;
	}
	public void setDatos(String[][] datos) {
		this.datos = datos;
	}

	public Obj_Transpaso_A_Banco_Interno GuardarActualizar(){ 
		return new GuardarSQL().Guardar_Traspaso_A_Banco_Interno(this); }
	
	public String[][] consulta_movimiento_banco(int folio_orden_gasto){
		return new BuscarSQL().Tabla_Movimiento_banco(folio_orden_gasto);
	}
	
	public String[] Combo_Cuentas() {
		try {return new Cargar_Combo().Servicios_Combos("cuentas");
		   } catch (SQLException e) {
			e.printStackTrace();
		   }
			return null; 
			}
	
}

