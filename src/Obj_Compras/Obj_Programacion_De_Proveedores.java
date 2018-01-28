package Obj_Compras;

import java.sql.SQLException;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Cargar_Combo;
import Conexiones_SQL.GuardarSQL;

public class Obj_Programacion_De_Proveedores {
	public int folio         = 0;
	public int semana_de_anio= 0;
	public int anio          = 0;
	String Establecimiento   ="";
	String NuevoModifica     ="";
	String [][] tabla_programacion=null;
	
	public String getEstablecimiento() {
		return Establecimiento;
	}

	public void setEstablecimiento(String establecimiento) {
		Establecimiento = establecimiento;
	}

	public String getNuevoModifica() {
		return NuevoModifica;
	}

	public void setNuevoModifica(String nuevoModifica) {
		NuevoModifica = nuevoModifica;
	}

	public String[][] getTabla_programacion() {
		return tabla_programacion;
	}

	public void setTabla_programacion(String[][] tabla_programacion) {
		this.tabla_programacion = tabla_programacion;
	}

	public int getFolio() {
		return folio;
	}

	public void setFolio(int folio) {
		this.folio = folio;
	}

	public int getSemana_de_anio() {
		return semana_de_anio;
	}

	public void setSemana_de_anio(int semana_de_anio) {
		this.semana_de_anio = semana_de_anio;
	}

	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

	public boolean Guardar(){
		return new GuardarSQL().Guardar_Programacion(this);
	}
	
	public int Nuevo(){
		return new BuscarSQL().Devuelve_ultimo_folio_transaccion("80");
	}
	
	public boolean validacion_existe (String semana, String Establecimiento){
		return new BuscarSQL().Validaciones("EPP",semana, Establecimiento);
	}
	
	public String[] Combo_Anio(int año, String tipo_conc ) {
		  try {return new Cargar_Combo().Combos_Tiempo(año, tipo_conc, "");
		  }catch (SQLException e) {e.printStackTrace();}
		  return null; 
	}
	
	public String[] Combo_Semanas_Del_Año(int año,String Periodo, String Establecimiento) {
		  try {return new Cargar_Combo().Combos_Tiempo(año,Periodo,Establecimiento);
			   } catch (SQLException e) {
				e.printStackTrace();
			   }
		  return null; 
    }

	public String[][] refrescar_tablas(int folio_programacione){
		return new BuscarSQL().TablaProgramacion_De_Proveedores(folio_programacione);
	}
	
}
