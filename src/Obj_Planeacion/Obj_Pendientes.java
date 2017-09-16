package Obj_Planeacion;

import Conexiones_SQL.GuardarSQL;


public class Obj_Pendientes {
  String Pendiente     ="";
  String Colaboradores ="";
public String getPendiente() {
	return Pendiente;
}
public void setPendiente(String pendiente) {
	Pendiente = pendiente;
}
public String getColaboradores() {
	return Colaboradores;
}
public void setColaboradores(String colaboradores) {
	Colaboradores = colaboradores;
}

public boolean guardar(){
    return new GuardarSQL().Guardar_Pendiente(this); }
	
}
