package Obj_Contabilidad;

import java.sql.SQLException;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Cargar_Combo;

public class Obj_Indicadores {
	 String reporte="";
	 String procedimiento_almacenado="";
	 
	 public String getReporte() {
		return reporte;
	}

	public void setReporte(String reporte) {
		this.reporte = reporte;
	}

	public String getProcedimiento_almacenado() {
		return procedimiento_almacenado;
	}

	public void setProcedimiento_almacenado(String procedimiento_almacenado) {
		this.procedimiento_almacenado = procedimiento_almacenado;
	}


	public Obj_Indicadores(){
	}
	
	
	 public String[] Combo_Indicadores(){
			try {return new Cargar_Combo().Combos("indicadores");
			} catch (SQLException e) {e.printStackTrace();}
			return null; 
		    }
	 
	 
		public Obj_Indicadores buscar(String indicador){
		try {
			return new BuscarSQL().indicador(indicador);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; 
	}
	
	 
//	public boolean guardar(){
//		return new GuardarSQL().Guardar_Control_Factura_xml(this);
//		}
	 
//	
//	public boolean actualizar(String folio_Factura_Editada){
//		return new ActualizarSQL().Factura_Provedores_xml(this,folio_Factura_Editada); 
//		}
//	
//	public boolean marcar_recibido_factura(String cod_prov_recibido, String folio_factura_recibido,String tipo_archivo,File xml_pdf){
//		return new ActualizarSQL().marcar_c_recibido_factura(cod_prov_recibido, folio_factura_recibido,tipo_archivo,xml_pdf); 
//		}
	
}
