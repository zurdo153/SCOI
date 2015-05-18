package Obj_Lista_de_Raya;

import java.sql.SQLException;

import Conexiones_SQL.BuscarSQL;

public class Obj_Diferencia_De_Cortes_Calculado {
	private int folio_empleado;
	private String nombre_completo;
    private double saldo_a_favor;
    private double total_acumulado;
    private double diferencia_total;
    private double abono;
    private int status_cobro;
    
    public Obj_Diferencia_De_Cortes_Calculado(){
    	this.folio_empleado=0;
    	this.nombre_completo = "";
        this.saldo_a_favor=0;
        this.total_acumulado=0;
        this.diferencia_total=0;
        this.abono=0;
        this.status_cobro=0;
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

	public double getSaldo_a_favor() {
		return saldo_a_favor;
	}

	public void setSaldo_a_favor(double saldo_a_favor) {
		this.saldo_a_favor = saldo_a_favor;
	}

	public double getTotal_acumulado() {
		return total_acumulado;
	}

	public void setTotal_acumulado(double total_acumulado) {
		this.total_acumulado = total_acumulado;
	}

	public double getDiferencia_total() {
		return diferencia_total;
	}

	public void setDiferencia_total(double diferencia_total) {
		this.diferencia_total = diferencia_total;
	}

	public double getAbono() {
		return abono;
	}

	public void setAbono(double abono) {
		this.abono = abono;
	}

	public int getStatus_cobro() {
		return status_cobro;
	}

	public void setStatus_cobro(int status_cobro) {
		this.status_cobro = status_cobro;
	}
	
	

	public Obj_Diferencia_De_Cortes_Calculado buscar(int folio){ 
		try {
			return new BuscarSQL().corte_calc(folio);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	return null; 
	}

//	public boolean guardar(){ return new GuardarSQL().Guardar(this); }
	
//	public boolean actualizar(int folio){ return new ActualizarSQL().Actualizar(this,folio); }
	
//	public boolean actualizar_abono_de_cortes(int folio,String statusCobro, double abono){ return new ActualizarSQL().Actualizar_abono_de_cortes(folio, statusCobro, abono); }
	
//	public Obj_Diferencia_De_Cortes_Calculado maximo() {
//		try {
//			return new BuscarSQL().maximo_diferencia_cortes();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}

}
