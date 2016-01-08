package Obj_Planeacion;

import java.sql.SQLException;
import Conexiones_SQL.BuscarSQL;

public class Obj_Frecuencia_De_Actividades {
	String tipo_de_frecuencia = "UNA VEZ";
	boolean seleccion_hasta_que_se_cumpla = true;
	boolean seleccion_en_la_fecha_indicada = false;
	
//unica repeticion
	String fh_unica_repeticion = cargar_fechas(0);
	
	
	boolean seleccion_con_hora = false; 
	String hora_unica_repeticion = "23:59:00";
	
//frecuencia
	String sucede="DIARIA";
	boolean selecciona_dia_del_mes=true;
	int dias_a_repetir_por_suceso_de_dias=1;		
	int dias_a_repetir_por_suceso_de_semanas=0;	//dependen del tipo de suceso
	int dias_a_repetir_por_suceso_de_meses=0;		
	int mes1=1;
	
	boolean selecciona_dia_de_la_semana=false;
	String nivel_de_dias="Primer";
	int dia_de_la_semana=0;
	int mes2=1;
	
//	semana------
	boolean lunes=false;
	boolean martes=false;
	boolean miercoles=false;
	boolean jueves=false;
	boolean viernes=false;
	boolean sabado=false;
	boolean domingo=false;
//	frecuencia diaria
	boolean seleccion_asignar_hora=false;
	String hora_frecuencia_diaria="12:00:00";
	
//	Duracion
	String fecha_inicio_duracion=cargar_fechas(0);
	boolean seleccion_fecha_finaliza=false;
	String fecha_final_duracion=cargar_fechas(-365);
	boolean seleccion_sin_fecha_final=true;
	
	
	public String getTipo_de_frecuencia() {
		return tipo_de_frecuencia;
	}


	public void setTipo_de_frecuencia(String tipo_de_frecuencia) {
		this.tipo_de_frecuencia = tipo_de_frecuencia;
	}


	public boolean isSeleccion_hasta_que_se_cumpla() {
		return seleccion_hasta_que_se_cumpla;
	}


	public void setSeleccion_hasta_que_se_cumpla(boolean seleccion_hasta_que_se_cumpla) {
		this.seleccion_hasta_que_se_cumpla = seleccion_hasta_que_se_cumpla;
	}


	public boolean isSeleccion_en_la_fecha_indicada() {
		return seleccion_en_la_fecha_indicada;
	}


	public void setSeleccion_en_la_fecha_indicada(boolean seleccion_en_la_fecha_indicada) {
		this.seleccion_en_la_fecha_indicada = seleccion_en_la_fecha_indicada;
	}


	public String getFh_unica_repeticion() {
		return fh_unica_repeticion;
	}


	public void setFh_unica_repeticion(String fh_unica_repeticion) {
		this.fh_unica_repeticion = fh_unica_repeticion;
	}


	public boolean isSeleccion_con_hora() {
		return seleccion_con_hora;
	}


	public void setSeleccion_con_hora(boolean seleccion_con_hora) {
		this.seleccion_con_hora = seleccion_con_hora;
	}


	public String getHora_unica_repeticion() {
		return hora_unica_repeticion;
	}


	public void setHora_unica_repeticion(String hora_unica_repeticion) {
		this.hora_unica_repeticion = hora_unica_repeticion;
	}


	public String getSucede() {
		return sucede;
	}


	public void setSucede(String sucede) {
		this.sucede = sucede;
	}


	public boolean isSelecciona_dia_del_mes() {
		return selecciona_dia_del_mes;
	}


	public void setSelecciona_dia_del_mes(boolean selecciona_dia_del_mes) {
		this.selecciona_dia_del_mes = selecciona_dia_del_mes;
	}


	public int getDias_a_repetir_por_suceso_de_dias() {
		return dias_a_repetir_por_suceso_de_dias;
	}


	public void setDias_a_repetir_por_suceso_de_dias(int dias_a_repetir_por_suceso_de_dias) {
		this.dias_a_repetir_por_suceso_de_dias = dias_a_repetir_por_suceso_de_dias;
	}


	public int getDias_a_repetir_por_suceso_de_semanas() {
		return dias_a_repetir_por_suceso_de_semanas;
	}


	public void setDias_a_repetir_por_suceso_de_semanas(int dias_a_repetir_por_suceso_de_semanas) {
		this.dias_a_repetir_por_suceso_de_semanas = dias_a_repetir_por_suceso_de_semanas;
	}


	public int getDias_a_repetir_por_suceso_de_meses() {
		return dias_a_repetir_por_suceso_de_meses;
	}


	public void setDias_a_repetir_por_suceso_de_meses(int dias_a_repetir_por_suceso_de_meses) {
		this.dias_a_repetir_por_suceso_de_meses = dias_a_repetir_por_suceso_de_meses;
	}


	public int getMes1() {
		return mes1;
	}


	public void setMes1(int mes1) {
		this.mes1 = mes1;
	}


	public boolean isSelecciona_dia_de_la_semana() {
		return selecciona_dia_de_la_semana;
	}


	public void setSelecciona_dia_de_la_semana(boolean selecciona_dia_de_la_semana) {
		this.selecciona_dia_de_la_semana = selecciona_dia_de_la_semana;
	}


	public String getNivel_de_dias() {
		return nivel_de_dias;
	}


	public void setNivel_de_dias(String nivel_de_dias) {
		this.nivel_de_dias = nivel_de_dias;
	}


	public Integer getDia_de_la_semana() {
		return dia_de_la_semana;
	}


	public void setDia_de_la_semana(Integer dia_de_la_semana) {
		this.dia_de_la_semana = dia_de_la_semana;
	}


	public int getMes2() {
		return mes2;
	}


	public void setMes2(int mes2) {
		this.mes2 = mes2;
	}


	public boolean isDomingo() {
		return domingo;
	}


	public void setDomingo(boolean domingo) {
		this.domingo = domingo;
	}


	public boolean isLunes() {
		return lunes;
	}


	public void setLunes(boolean lunes) {
		this.lunes = lunes;
	}


	public boolean isMartes() {
		return martes;
	}


	public void setMartes(boolean martes) {
		this.martes = martes;
	}


	public boolean isMiercoles() {
		return miercoles;
	}


	public void setMiercoles(boolean miercoles) {
		this.miercoles = miercoles;
	}


	public boolean isJueves() {
		return jueves;
	}


	public void setJueves(boolean jueves) {
		this.jueves = jueves;
	}


	public boolean isViernes() {
		return viernes;
	}


	public void setViernes(boolean viernes) {
		this.viernes = viernes;
	}


	public boolean isSabado() {
		return sabado;
	}


	public void setSabado(boolean sabado) {
		this.sabado = sabado;
	}


	public boolean isSeleccion_asignar_hora() {
		return seleccion_asignar_hora;
	}


	public void setSeleccion_asignar_hora(boolean seleccion_asignar_hora) {
		this.seleccion_asignar_hora = seleccion_asignar_hora;
	}


	public String getHora_frecuencia_diaria() {
		return hora_frecuencia_diaria;
	}


	public void setHora_frecuencia_diaria(String hora_frecuencia_diaria) {
		this.hora_frecuencia_diaria = hora_frecuencia_diaria;
	}


	public String getFecha_inicio_duracion() {
		return fecha_inicio_duracion;
	}


	public void setFecha_inicio_duracion(String fecha_inicio_duracion) {
		this.fecha_inicio_duracion = fecha_inicio_duracion;
	}


	public boolean isSeleccion_fecha_finaliza() {
		return seleccion_fecha_finaliza;
	}


	public void setSeleccion_fecha_finaliza(boolean seleccion_fecha_finaliza) {
		this.seleccion_fecha_finaliza = seleccion_fecha_finaliza;
	}


	public String getFecha_final_duracion() {
		return fecha_final_duracion;
	}


	public void setFecha_final_duracion(String fecha_final_duracion) {
		this.fecha_final_duracion = fecha_final_duracion;
	}


	public boolean isSeleccion_sin_fecha_final() {
		return seleccion_sin_fecha_final;
	}


	public void setSeleccion_sin_fecha_final(boolean seleccion_sin_fecha_final) {
		this.seleccion_sin_fecha_final = seleccion_sin_fecha_final;
	}


	public String cargar_fechas(int dias){
		String date = null;
	    	try {
				date = new BuscarSQL().fecha(dias);
				} catch (SQLException e) {
					// catch block
					e.printStackTrace();
					}
		return date;
	};
}
