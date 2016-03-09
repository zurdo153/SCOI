package Obj_Evaluaciones;

import java.sql.SQLException;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Cargar_Combo;
import Conexiones_SQL.GuardarSQL;

public class Obj_Nivel_Jerarquico {
	private int folio;
	private String descripcion;
	private int folio_puesto_principal;
	private String puesto_principal;
	private int foliopuesto_dependiente;
	private String puesto_dependiente;
	private String puesto;
	private String establecimiento;
	private boolean status;
	
	public Obj_Nivel_Jerarquico()
	{
		this.folio=0; this.descripcion=""; 
		this.folio_puesto_principal = 0; this.puesto_principal=""; 
		this.foliopuesto_dependiente = 0; this.puesto_dependiente="";
		this.puesto=""; this.establecimiento=""; this.status=false;
	}

	public int getFolio() {
		return folio;
	}

	public void setFolio(int folio) {
		this.folio = folio;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getPuesto() {
		return puesto;
	}

	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}
	
	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public int getFolio_puesto_principal() {
		return folio_puesto_principal;
	}

	public void setFolio_puesto_principal(int folio_puesto_principal) {
		this.folio_puesto_principal = folio_puesto_principal;
	}

	public String getPuesto_principal() {
		return puesto_principal;
	}

	public void setPuesto_principal(String puesto_principal) {
		this.puesto_principal = puesto_principal;
	}

	public int getFoliopuesto_dependiente() {
		return foliopuesto_dependiente;
	}

	public void setFoliopuesto_dependiente(int foliopuesto_dependiente) {
		this.foliopuesto_dependiente = foliopuesto_dependiente;
	}

	public String getPuesto_dependiente() {
		return puesto_dependiente;
	}

	public void setPuesto_dependiente(String puesto_dependiente) {
		this.puesto_dependiente = puesto_dependiente;
	}

	public String getEstablecimiento() {
		return establecimiento;
	}

	public void setEstablecimiento(String establecimiento) {
		this.establecimiento = establecimiento;
	}

	public String Nuevo(){
		try {
			return new BuscarSQL().OpNivel();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/*guarda la segunda parte del catalogo*/
	public boolean guardar_multiple2(String[][] tabla){ return new GuardarSQL().Guardar_Tabla_Nivel2(this,tabla); }
//	public boolean actualizar2(String[][] tabla){ return new ActualizarSQL().nivelGerarquico2(this,tabla); }
	
	/*buscamos la primer parte del catalogo*/
	public Obj_Nivel_Jerarquico buscar(int folio){
		try {
			return new BuscarSQL().buscarnivel(folio);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; 
	}
	
	public Obj_Nivel_Jerarquico buscar(String descripcion){
		try {
			return new BuscarSQL().buscarDescripcion(descripcion);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; 
	}
	
	public boolean buscarYborraPuestoDependiente(int folio_puesto_principal, int folio_tabla,String establecimiento){ return new GuardarSQL().buscarBorrarPDependiente(folio_puesto_principal, folio_tabla,establecimiento); }
	
	/*buscamos la segunda parte del catalogo*/
	public Obj_Nivel_Jerarquico buscartabla(int folio){
		try {
			return new BuscarSQL().buscartablanivel(folio);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; 
	}
	

	
	public String[] combo_nivel_jerarquico() {
		try {
			return new Cargar_Combo().niveljerarquico("tb_nivel_jerarquico");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; 
	}
}
