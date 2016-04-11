package Obj_Checador;

import java.sql.SQLException;
import java.util.Vector;

import Conexiones_SQL.ActualizarSQL;
import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.GuardarSQL;


public class Obj_Alimentacion_De_Permisos_A_Empleados {
	int folio;
	int folio_empleado;
	String nombre_empleado;
	int folio_usuario;
	String fecha;
	int tipo_de_permiso;
	String motivo;
	boolean status;
	int descanso;
	String tiempo_comida;
	String Establecimiento;
	int folio_empleado_optener_turno;
	int solicito;
	public Obj_Alimentacion_De_Permisos_A_Empleados(){
		
		this.folio = 0;
		this.folio_empleado = 0;
		this.nombre_empleado = "";
		this.folio_usuario = 0;
		this.fecha = "";
		this.tipo_de_permiso = 0;
		this.motivo = "";
		this.status = false;
		this.descanso = 0;
		this.tiempo_comida = "";
		this.folio_empleado_optener_turno=0;
		this.solicito = 0;
		this.Establecimiento="";

	}

	public String getEstablecimiento() {
		return Establecimiento;
	}

	public void setEstablecimiento(String establecimiento) {
		Establecimiento = establecimiento;
	}

	public int getFolio() {
		return folio;
	}

	public void setFolio(int folio) {
		this.folio = folio;
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

	public int getFolio_usuario() {
		return folio_usuario;
	}

	public void setFolio_usuario(int folio_usuario) {
		this.folio_usuario = folio_usuario;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public int getTipo_de_permiso() {
		return tipo_de_permiso;
	}

	public void setTipo_de_permiso(int tipo_de_permiso) {
		this.tipo_de_permiso = tipo_de_permiso;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public int getDescanso() {
		return descanso;
	}

	public void setDescanso(int descanso) {
		this.descanso = descanso;
	}

	public String getTiempo_comida() {
		return tiempo_comida;
	}

	public void setTiempo_comida(String tiempo_comida) {
		this.tiempo_comida = tiempo_comida;
	}

	public int getFolio_empleado_optener_turno() {
		return folio_empleado_optener_turno;
	}

	public void setFolio_empleado_optener_turno(int folio_empleado_optener_turno) {
		this.folio_empleado_optener_turno = folio_empleado_optener_turno;
	}

	public int getSolicito() {
		return solicito;
	}

	public void setSolicito(int solicito) {
		this.solicito = solicito;
	}

	public Obj_Alimentacion_De_Permisos_A_Empleados buscar(int folio) {
		try {
			return new BuscarSQL().buscar_permiso(folio);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Obj_Alimentacion_De_Permisos_A_Empleados ComparacionFecha(String fecha) {
		try {
			return new BuscarSQL().comparacionDeFecha(fecha);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean guardar_permiso(int tiene_dia_dobla) {
		return new GuardarSQL().Guardar_Permiso_Checador(this,tiene_dia_dobla);
	}

	public boolean actualizar(int folio) {
		return new ActualizarSQL().permiso(this, folio);
	}

	public int nuevoPermiso() {
		try {
			return new BuscarSQL().NuevoPermisoChecador();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1;
	}

	@SuppressWarnings("rawtypes")
	public Vector Obj_Mensaje_respuesta(int folio) {
		try {

			return new BuscarSQL().obtener_empleado_y_turno(folio);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
//	busca cuantos dias a doblar tiene
	public int buscar_doblada(int folio) {
			return new BuscarSQL().buscar_si_dobla(folio);
	}
	
//	busca si tiene la doblada default
	public boolean b_doblada(int folio) {
		return new BuscarSQL().buscar_si_dobla_default(folio);
	}
	
	// public boolean buscarYborraPermiso(int folio){ return new
	// GuardarSQL().buscarBorrarPermiso(folio); }
}
