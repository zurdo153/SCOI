package Obj_Matrices;

import java.sql.SQLException;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Cargar_Combo;

public class Obj_Matriz {
	int Folio;
	String Descripcion;
	String Etapa;
	String Aspecto_De_Inspeccion;
	String Departamento;
	String Unidad_De_Inspeccion;
	String Establecimiento;
	 Object[][] matriz_llena =null;
	
	public Object[][] getMatriz_llena() {
		return matriz_llena;
	}

	public void setMatriz_llena(Object[][] matriz_llena) {
		this.matriz_llena = matriz_llena;
	}

	public Obj_Matriz(){}
	
	public Obj_Matriz(int folio, String Etapa,String Aspecto_De_Inspeccion, String Departamento, String Unidad_De_Inspeccion, String Establecimiento )
	{
	this.Folio=folio;
	this.Etapa=Etapa;
	this.Aspecto_De_Inspeccion=Aspecto_De_Inspeccion;
	this.Departamento=Departamento;
	this.Unidad_De_Inspeccion=Unidad_De_Inspeccion;
	this.Establecimiento=Establecimiento;
	
	}
	public int getFolio() {
		return Folio;
	}

	public void setFolio(int folio) {
		this.Folio = folio;
	}
	public String getDescripcion()
	{
		return Descripcion;
		
	}
	public void setDescripcion(String descripcion)
	{
		
		this.Descripcion=descripcion;
		
		System.out.print(Descripcion+"\n");
		
	}
	public String getEtapa(){
			return Etapa;
	}
	public void setEtapa(String etapa)
	{
		this.Etapa=etapa;
		
	}

	public String getAspecto_De_Inspeccion() {
		return Aspecto_De_Inspeccion;
	}

	public void setAspecto_De_Inspeccion(String aspecto_De_Inspeccion) {
		Aspecto_De_Inspeccion = aspecto_De_Inspeccion;
	}

	public String getDepartamento() {
		return Departamento;
	}

	public void setDepartamento(String departamento) {
		Departamento = departamento;
	}

	public String getUnidad_De_Inspeccion() {
		return Unidad_De_Inspeccion;
	}

	public void setUnidad_De_Inspeccion(String unidad_De_Inspeccion) {
		Unidad_De_Inspeccion = unidad_De_Inspeccion;
	}

	public String getEstablecimiento() {
		return Establecimiento;
	}

	public void setEstablecimiento(String establecimiento) {
		Establecimiento = establecimiento;
	}
	

	//departamento
		public String[] Combo_Respuesta_Departamento() {
			try {return new Cargar_Combo().Combos("departamentos");
			} catch (SQLException e) {e.printStackTrace();}
			return null; 
		}
			
	
	public String FolioSiguiente(){
		try {
			return new BuscarSQL().Folio_Siguiente();
			} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean guardar_matriz(String Estab,Object[][] tabla,String des){
		return new Conexiones_SQL.GuardarTablasModel().Guardar_matriz(Estab,tabla,des);
	}
	public String Buscar_Descripciones(int folio){
		try {
		return new BuscarSQL().Buscar_Descripcion(folio);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean Modidicar_matriz(String Estab,Object[][] tabla, int folio, String descricion){
		return new Conexiones_SQL.GuardarTablasModel().Modificar_matriz(Estab,tabla,folio,descricion);
		
	}


}
	

//}
