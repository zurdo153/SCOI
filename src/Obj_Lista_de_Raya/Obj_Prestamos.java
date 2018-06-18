package Obj_Lista_de_Raya;

import java.sql.SQLException;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.GuardarSQL;

public class Obj_Prestamos {
	private int folio;
	private int folio_empleado;
	private String nombre_completo;
    private double cantidad;
    private double descuento;
    private double saldo;
    private double abonos;
    private String fecha;
    private String status;
    private int status_descuento;
    private int tipo_prestamo;
    private String Nuevo_Modificar;

	public Obj_Prestamos(){
    	this.folio_empleado=0;
    	this.nombre_completo = "";
    	this.cantidad =0.0;
    	this.descuento=0.0;
    	this.fecha = "";
    	this.status="";
    	this.saldo=0.0;
    	this.abonos=0.0;
    	this.status_descuento=0;
    	this.tipo_prestamo=0;
    	this.Nuevo_Modificar="";
    }

	public String getNuevo_Modificar() {
		return Nuevo_Modificar;
	}

	public void setNuevo_Modificar(String nuevo_Modificar) {
		Nuevo_Modificar = nuevo_Modificar;
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

	public void setFolio_empleado(int folioEmpleado) {
		folio_empleado = folioEmpleado;
	}

	public String getNombre_completo() {
		return nombre_completo;
	}

	public void setNombre_completo(String nombreCompleto) {
		nombre_completo = nombreCompleto;
	}

	public String getNombre_Completo() {
		return nombre_completo;
	}

	public void setNombre_Completo(String nombreCompleto) {
		nombre_completo = nombreCompleto;
	}

	public double getCantidad() {
		return cantidad;
	}

	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}

	public double getDescuento() {
		return descuento;
	}

	public void setDescuento(double descuento) {
		this.descuento = descuento;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	
	public double getAbonos() {
		return abonos;
	}

	public void setAbonos(double abonos) {
		this.abonos = abonos;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getStatus_descuento() {
		return status_descuento;
	}

	public void setStatus_descuento(int statusDescuento) {
		status_descuento = statusDescuento;
	}

    public int getTipo_prestamo() {
		return tipo_prestamo;
	}

	public void setTipo_prestamo(int tipo_prestamo) {
		this.tipo_prestamo = tipo_prestamo;
	}
	
	public boolean guardar(){ return new GuardarSQL().Guardar_prestamo(this); }
	public Obj_Prestamos maximo() throws SQLException{ return new BuscarSQL().maximoPrestamo(); }

}
