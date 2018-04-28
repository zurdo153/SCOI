package Obj_Lista_de_Raya;

import java.sql.SQLException;

import Conexiones_SQL.ActualizarSQL;
import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Cargar_Combo;
import Conexiones_SQL.GuardarSQL;

public class Obj_Rango_De_Prestamos {
	
	private int folio;
	private double prestamo_minimo;
	private double prestamo_maximo;
	private double descuento;
	private boolean status;
	
	public Obj_Rango_De_Prestamos(){
		folio=0;
		prestamo_minimo=0;
		prestamo_maximo=0;
		descuento=0;
		status=false;
	}

	public int getFolio() {
		return folio;
	}

	public void setFolio(int folio) {
		this.folio = folio;
	}

	public double getPrestamo_minimo() {
		return prestamo_minimo;
	}

	public void setPrestamo_minimo(double prestamoMinimo) {
		prestamo_minimo = prestamoMinimo;
	}

	public double getPrestamo_maximo() {
		return prestamo_maximo;
	}

	public void setPrestamo_maximo(double prestamoMaximo) {
		prestamo_maximo = prestamoMaximo;
	}

	public double getDescuento() {
		return descuento;
	}

	public void setDescuento(double descuento) {
		this.descuento = descuento;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public Obj_Rango_De_Prestamos buscar(int folio){
		try {
			return new BuscarSQL().Rango_Prestamos(folio);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean actualizar(int folio){ return new ActualizarSQL().Rango_Prestamos(this,folio); }
	
	public boolean guardar(){ return new GuardarSQL().Guardar_Rango_Prestamos(this); }
	
	public Obj_Rango_De_Prestamos buscar_nuevo(){ 
		try {
			return new BuscarSQL().Rango_Prestamos_Nuevo();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; 
	}
	
	public String[] Combo_Prestamos(){ 
		try {return new Cargar_Combo().Combos("rango_prestamos");
		} catch (SQLException e) {e.printStackTrace();}
		return null; 
	    }

}
