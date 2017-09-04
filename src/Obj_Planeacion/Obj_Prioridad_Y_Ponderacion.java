package Obj_Planeacion;

public class Obj_Prioridad_Y_Ponderacion {
	//Default
	String Importante ="false",Urgente="false",Preventivo="false",Normal="true";
	Integer Ponderacion=1;
	public String getImportante() {
		return Importante;
	}
	public void setImportante(String importante) {
		Importante = importante;
	}
	public String getUrgente() {
		return Urgente;
	}
	public void setUrgente(String urgente) {
		Urgente = urgente;
	}
	public String getPreventivo() {
		return Preventivo;
	}
	public void setPreventivo(String preventivo) {
		Preventivo = preventivo;
	}
	public String getNormal() {
		return Normal;
	}
	public void setNormal(String normal) {
		Normal = normal;
	}
	public Integer getPonderacion() {
		return Ponderacion;
	}
	public void setPonderacion(Integer ponderacion) {
		Ponderacion = ponderacion;
	}

}
