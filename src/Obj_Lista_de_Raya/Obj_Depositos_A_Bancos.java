package Obj_Lista_de_Raya;

import java.io.IOException;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.GuardarTablasModel;

public class Obj_Depositos_A_Bancos {

	Object[][] tabla_model;
	
	public Obj_Depositos_A_Bancos(){
		this.tabla_model = null;
	}

	public Object[][] getTabla_model() {
		return tabla_model;
	}

	public void setTabla_model(Object[][] tabla_model) {
		this.tabla_model = tabla_model;
	}
	
	public boolean guardar(Object[][] tabla){
		return new GuardarTablasModel().tabla_model_bancos(tabla);
	}
	
	public String[][] buscar_bancos () throws IOException{
		return new BuscarSQL().Tabla_Venta_Bancos_Para_Depositos_Nomina();
	}
}
