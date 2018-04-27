package Obj_Lista_de_Raya;

import java.sql.SQLException;

import Conexiones_SQL.ActualizarSQL;
import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Cargar_Combo;
import Conexiones_SQL.GuardarSQL;

public class Obj_Bono_Complemento_Sueldo {
	private int folio;
	private float bono;
	private String abreviatura;
	private boolean status;
	
	public Obj_Bono_Complemento_Sueldo(){
		this.folio=0; this.bono=0; this.abreviatura=""; this.status=false;
	}

	public int getFolio() {
		return folio;
	}

	public void setFolio(int folio) {
		this.folio = folio;
	}

	public float getBono() {
		return bono;
	}

	public void setBono(float bono) {
		this.bono = bono;
	}

	public String getAbreviatura() {
		return abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	public String[] Combo_Bono() { 
		try {
			return new Cargar_Combo().Combos("bonos");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; 
	}

	
	public Obj_Bono_Complemento_Sueldo buscar(int folio) {
		try {
			return new BuscarSQL().Bono(folio);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; 
	}
	
	public Obj_Bono_Complemento_Sueldo buscarValor(float valor) {
		try {
			return new BuscarSQL().BonoValor(valor);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; 
	}
	public boolean guardar(){ return new GuardarSQL().Guardar_Bono(this); }
	
	public Obj_Bono_Complemento_Sueldo buscar_nuevo() throws SQLException{ return new BuscarSQL().Bono_Nuevo(); }
	
	public boolean actualizar(int folio){ return new ActualizarSQL().Bono(this,folio); }
	
}
