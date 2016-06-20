package Obj_Compras;

import java.sql.SQLException;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Cargar_Combo;

public class Obj_Metas_Establecimiento_periodo {
	String establecimiento,mes,grupo;
	int anio;
	public Obj_Metas_Establecimiento_periodo(){}
	public Obj_Metas_Establecimiento_periodo(String establecimiento, String mes,String grupo, int anio){
		
		this.anio=anio;
		this.establecimiento=establecimiento;
		this.grupo=grupo;
		this.mes=mes;
		
		}

	public String getEstablecimiento() {
		return establecimiento;
	}

	public void setEstablecimiento(String establecimiento) {
		this.establecimiento = establecimiento;
	}

	public String getMes() {
		return mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

		public String[] Combo_Respuesta_anio(){
			try {
				return new Cargar_Combo().Combo_meta_anio();
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
	}
		public boolean guardar_matriz(Object[][] tabla,String Establecimiento,int Mes,int anio){
			return new Conexiones_SQL.GuardarTablasModel().Guardar_Metas(tabla,Establecimiento,Mes,anio);
		}
		public String buscar_metas_periodo(int anio,int mes,String cod_est){
			try {
			return new BuscarSQL().busca_metas_periodo(anio,mes,cod_est);
			
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
		public String buscar_metas_a_generar(int anio,int mes,String estab, int cod_meta){
			try {
			return new BuscarSQL().busca_metas_a_generar(anio,mes,estab,cod_meta);
			
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
	

}
