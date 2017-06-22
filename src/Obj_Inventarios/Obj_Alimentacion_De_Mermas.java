package Obj_Inventarios;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.GuardarSQL;

public class Obj_Alimentacion_De_Mermas {

	int folio;
	String establecimiento;
	String nota;
	String RutaFoto;
	Object[][] arreglo;
	public Obj_Alimentacion_De_Mermas() {
		folio = 0;	establecimiento="";		nota="";	arreglo = null;		RutaFoto = "";
	}
	public int getFolio() {
		return folio;
	}
	public void setFolio(int folio) {
		this.folio = folio;
	}
	public String getEstablecimiento() {
		return establecimiento;
	}
	public void setEstablecimiento(String establecimiento) {
		this.establecimiento = establecimiento;
	}
	public String getNota() {
		return nota;
	}
	public void setNota(String nota) {
		this.nota = nota;
	}
	public Object[][] getArreglo() {
		return arreglo;
	}
	public void setArreglo(Object[][] arreglo) {
		this.arreglo = arreglo;
	}
	
	public String getRutaFoto() {
		return RutaFoto;
	}
	public void setRutaFoto(String rutaFoto) {
		RutaFoto = rutaFoto;
	}
	public String[] Razones_De_Mermas(){
		return new BuscarSQL().Razones_De_Mermas();
	}
	
	public String[] Destino_De_Mermas(String filtro){
		return new BuscarSQL().Destino_De_Mermas(filtro);
	}

	public boolean Guardar_Merma(String movimiento){
		return new GuardarSQL().Guardar_Mermas(this, movimiento);
	}
	
	public boolean validaUsuarioGuardo_vs_usuarioSeguridad(int folio, int userGafete){
		return new BuscarSQL().valida_usuarioGuardo_vs_usuario_seguridad(folio, userGafete);
	}
}
