package Obj_Lista_de_Raya;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.GuardarSQL;

public class Obj_Finiquitos {

	int folio_empleado_scoi = 0;
	String folio_empleado_bms = "";
	String empleado = "";
	String establecimiento = "";
	
//	BMS--------------------------------------------------------------------------------------------------------------------
	String fecha_ingreso_BMS = "";
	String fecha_baja_BMS	 = "";
	
	int dias_trabajados_BMS 					= 0;
	double anios_trabajados_BMS 				= 0;
	int dias_pendientes_de_pago_de_aguinaldo_BMS= 0;
	int dias_pendientes_de_pago_de_semana_BMS	= 0;
	double cuota_diario_BMS						= 0;
	double SDI_BMS								= 0;
	double sueldo_BMS							= 0;
	double aguinaldo_BMS						= 0;
	double vacaciones_pendientes_BMS			= 0;
	double vacaciones_BMS						= 0;
	double prima_vacacional_BMS					= 0;
	double gratificacion_BMS					= 0;
	double tiempo_extra_BMS						= 0;
	double percepciones_BMS						= 0;
	
	int dias_correspondiente_vacaciones		= 0;
	
//	SCOI--------------------------------------------------------------------------------------------------------------------
	String fecha_ingreso_SCOI = "";
	String fecha_baja_SCOI = "";
	
	int dias_trabajados_SCOI 						= 0;
	double anios_trabajados_SCOI 					= 0;
	int dias_pendientes_de_pago_de_aguinaldo_SCOI 	= 0;
	int dias_pendientes_de_pago_de_semana_SCOI 		= 0;
	double cuota_diario_SCOI						= 0;
	
	double sueldo_SCOI								= 0;
	double aguinaldo_SCOI							= 0;
	double vacaciones_pendientes_SCOI				= 0;
	double vacaciones_SCOI							= 0;
	double prima_vacacional_SCOI					= 0;
	double percepciones_SCOI						= 0;
	
//	GRATIFICACION--------------------------------------------------------------------------------------------------------------------
	double sueldo_gratif				= 0;
	double aguinaldo_gratif				= 0;
	double vacaciones_gratif			= 0;
	double vacaciones_pendientes_gratif	= 0;
	double prima_vacacional_gratif		= 0;
	double percepciones_gratif			= 0;
	
//	DEDUCCIONES--------------------------------------------------------------------------------------------------------------------
	double pretamo					= 0;
	double cortes					= 0;
	double infonavit				= 0;
	double fuente_sodas				= 0;
	double otras_deducciones		= 0;
	
	double total_a_pagar			= 0;
	
	public Obj_Finiquitos() {
	}

	public int getFolio_empleado_scoi() {
		return folio_empleado_scoi;
	}

	public void setFolio_empleado_scoi(int folio_empleado_scoi) {
		this.folio_empleado_scoi = folio_empleado_scoi;
	}
	
	public String getFolio_empleado_bms() {
		return folio_empleado_bms;
	}

	public void setFolio_empleado_bms(String folio_empleado_bms) {
		this.folio_empleado_bms = folio_empleado_bms;
	}

	public String getEmpleado() {
		return empleado;
	}

	public void setEmpleado(String empleado) {
		this.empleado = empleado;
	}

	public String getEstablecimiento() {
		return establecimiento;
	}

	public void setEstablecimiento(String establecimiento) {
		this.establecimiento = establecimiento;
	}

	public String getFecha_ingreso_BMS() {
		return fecha_ingreso_BMS;
	}

	public void setFecha_ingreso_BMS(String fecha_ingreso_BMS) {
		this.fecha_ingreso_BMS = fecha_ingreso_BMS;
	}

	public String getFecha_baja_BMS() {
		return fecha_baja_BMS;
	}

	public void setFecha_baja_BMS(String fecha_baja_BMS) {
		this.fecha_baja_BMS = fecha_baja_BMS;
	}

	public int getDias_trabajados_BMS() {
		return dias_trabajados_BMS;
	}

	public void setDias_trabajados_BMS(int dias_trabajados_BMS) {
		this.dias_trabajados_BMS = dias_trabajados_BMS;
	}

	public double getAnios_trabajados_BMS() {
		return anios_trabajados_BMS;
	}

	public void setAnios_trabajados_BMS(double anios_trabajados_BMS) {
		this.anios_trabajados_BMS = anios_trabajados_BMS;
	}

	public int getDias_pendientes_de_pago_de_aguinaldo_BMS() {
		return dias_pendientes_de_pago_de_aguinaldo_BMS;
	}

	public void setDias_pendientes_de_pago_de_aguinaldo_BMS(
			int dias_pendientes_de_pago_de_aguinaldo_BMS) {
		this.dias_pendientes_de_pago_de_aguinaldo_BMS = dias_pendientes_de_pago_de_aguinaldo_BMS;
	}

	public int getDias_pendientes_de_pago_de_semana_BMS() {
		return dias_pendientes_de_pago_de_semana_BMS;
	}

	public void setDias_pendientes_de_pago_de_semana_BMS(
			int dias_pendientes_de_pago_de_semana_BMS) {
		this.dias_pendientes_de_pago_de_semana_BMS = dias_pendientes_de_pago_de_semana_BMS;
	}

	public double getCuota_diario_BMS() {
		return cuota_diario_BMS;
	}

	public void setCuota_diario_BMS(double cuota_diario_BMS) {
		this.cuota_diario_BMS = cuota_diario_BMS;
	}

	public double getSDI_BMS() {
		return SDI_BMS;
	}

	public void setSDI_BMS(double sDI_BMS) {
		SDI_BMS = sDI_BMS;
	}

	public double getSueldo_BMS() {
		return sueldo_BMS;
	}

	public void setSueldo_BMS(double sueldo_BMS) {
		this.sueldo_BMS = sueldo_BMS;
	}

	public double getAguinaldo_BMS() {
		return aguinaldo_BMS;
	}

	public void setAguinaldo_BMS(double aguinaldo_BMS) {
		this.aguinaldo_BMS = aguinaldo_BMS;
	}

	public double getVacaciones_pendientes_BMS() {
		return vacaciones_pendientes_BMS;
	}

	public void setVacaciones_pendientes_BMS(double vacaciones_pendientes_BMS) {
		this.vacaciones_pendientes_BMS = vacaciones_pendientes_BMS;
	}

	public double getVacaciones_BMS() {
		return vacaciones_BMS;
	}

	public void setVacaciones_BMS(double vacaciones_BMS) {
		this.vacaciones_BMS = vacaciones_BMS;
	}

	public double getPrima_vacacional_BMS() {
		return prima_vacacional_BMS;
	}

	public void setPrima_vacacional_BMS(double prima_vacacional_BMS) {
		this.prima_vacacional_BMS = prima_vacacional_BMS;
	}

	public double getGratificacion_BMS() {
		return gratificacion_BMS;
	}

	public void setGratificacion_BMS(double gratificacion_BMS) {
		this.gratificacion_BMS = gratificacion_BMS;
	}

	public double getTiempo_extra_BMS() {
		return tiempo_extra_BMS;
	}

	public void setTiempo_extra_BMS(double tiempo_extra_BMS) {
		this.tiempo_extra_BMS = tiempo_extra_BMS;
	}

	public double getPercepciones_BMS() {
		return percepciones_BMS;
	}

	public void setPercepciones_BMS(double percepciones_BMS) {
		this.percepciones_BMS = percepciones_BMS;
	}

	public String getFecha_ingreso_SCOI() {
		return fecha_ingreso_SCOI;
	}

	public void setFecha_ingreso_SCOI(String fecha_ingreso_SCOI) {
		this.fecha_ingreso_SCOI = fecha_ingreso_SCOI;
	}

	public String getFecha_baja_SCOI() {
		return fecha_baja_SCOI;
	}

	public void setFecha_baja_SCOI(String fecha_baja_SCOI) {
		this.fecha_baja_SCOI = fecha_baja_SCOI;
	}

	public int getDias_trabajados_SCOI() {
		return dias_trabajados_SCOI;
	}

	public void setDias_trabajados_SCOI(int dias_trabajados_SCOI) {
		this.dias_trabajados_SCOI = dias_trabajados_SCOI;
	}

	public double getAnios_trabajados_SCOI() {
		return anios_trabajados_SCOI;
	}

	public void setAnios_trabajados_SCOI(double anios_trabajados_SCOI) {
		this.anios_trabajados_SCOI = anios_trabajados_SCOI;
	}

	public int getDias_pendientes_de_pago_de_aguinaldo_SCOI() {
		return dias_pendientes_de_pago_de_aguinaldo_SCOI;
	}

	public void setDias_pendientes_de_pago_de_aguinaldo_SCOI(
			int dias_pendientes_de_pago_de_aguinaldo_SCOI) {
		this.dias_pendientes_de_pago_de_aguinaldo_SCOI = dias_pendientes_de_pago_de_aguinaldo_SCOI;
	}

	public int getDias_pendientes_de_pago_de_semana_SCOI() {
		return dias_pendientes_de_pago_de_semana_SCOI;
	}

	public void setDias_pendientes_de_pago_de_semana_SCOI(
			int dias_pendientes_de_pago_de_semana_SCOI) {
		this.dias_pendientes_de_pago_de_semana_SCOI = dias_pendientes_de_pago_de_semana_SCOI;
	}

	public double getCuota_diario_SCOI() {
		return cuota_diario_SCOI;
	}

	public void setCuota_diario_SCOI(double cuota_diario_SCOI) {
		this.cuota_diario_SCOI = cuota_diario_SCOI;
	}

	public double getSueldo_SCOI() {
		return sueldo_SCOI;
	}

	public void setSueldo_SCOI(double sueldo_SCOI) {
		this.sueldo_SCOI = sueldo_SCOI;
	}

	public double getAguinaldo_SCOI() {
		return aguinaldo_SCOI;
	}

	public void setAguinaldo_SCOI(double aguinaldo_SCOI) {
		this.aguinaldo_SCOI = aguinaldo_SCOI;
	}

	public double getVacaciones_pendientes_SCOI() {
		return vacaciones_pendientes_SCOI;
	}

	public void setVacaciones_pendientes_SCOI(double vacaciones_pendientes_SCOI) {
		this.vacaciones_pendientes_SCOI = vacaciones_pendientes_SCOI;
	}

	public double getVacaciones_SCOI() {
		return vacaciones_SCOI;
	}

	public void setVacaciones_SCOI(double vacaciones_SCOI) {
		this.vacaciones_SCOI = vacaciones_SCOI;
	}

	public double getPrima_vacacional_SCOI() {
		return prima_vacacional_SCOI;
	}

	public void setPrima_vacacional_SCOI(double prima_vacacional_SCOI) {
		this.prima_vacacional_SCOI = prima_vacacional_SCOI;
	}

	public double getPercepciones_SCOI() {
		return percepciones_SCOI;
	}

	public void setPercepciones_SCOI(double percepciones_SCOI) {
		this.percepciones_SCOI = percepciones_SCOI;
	}

	public double getSueldo_gratif() {
		return sueldo_gratif;
	}

	public void setSueldo_gratif(double sueldo_gratif) {
		this.sueldo_gratif = sueldo_gratif;
	}

	public double getAguinaldo_gratif() {
		return aguinaldo_gratif;
	}

	public void setAguinaldo_gratif(double aguinaldo_gratif) {
		this.aguinaldo_gratif = aguinaldo_gratif;
	}

	public double getVacaciones_gratif() {
		return vacaciones_gratif;
	}

	public void setVacaciones_gratif(double vacaciones_gratif) {
		this.vacaciones_gratif = vacaciones_gratif;
	}

	public double getVacaciones_pendientes_gratif() {
		return vacaciones_pendientes_gratif;
	}

	public void setVacaciones_pendientes_gratif(double vacaciones_pendientes_gratif) {
		this.vacaciones_pendientes_gratif = vacaciones_pendientes_gratif;
	}

	public double getPrima_vacacional_gratif() {
		return prima_vacacional_gratif;
	}

	public void setPrima_vacacional_gratif(double prima_vacacional_gratif) {
		this.prima_vacacional_gratif = prima_vacacional_gratif;
	}

	public double getPercepciones_gratif() {
		return percepciones_gratif;
	}

	public void setPercepciones_gratif(double percepciones_gratif) {
		this.percepciones_gratif = percepciones_gratif;
	}

	public double getPretamo() {
		return pretamo;
	}

	public void setPretamo(double pretamo) {
		this.pretamo = pretamo;
	}

	public double getCortes() {
		return cortes;
	}

	public void setCortes(double cortes) {
		this.cortes = cortes;
	}

	public double getInfonavit() {
		return infonavit;
	}

	public void setInfonavit(double infonavit) {
		this.infonavit = infonavit;
	}

	public double getFuente_sodas() {
		return fuente_sodas;
	}

	public void setFuente_sodas(double fuente_sodas) {
		this.fuente_sodas = fuente_sodas;
	}

	public double getOtras_deducciones() {
		return otras_deducciones;
	}

	public void setOtras_deducciones(double otras_deducciones) {
		this.otras_deducciones = otras_deducciones;
	}

	public double getTotal_a_pagar() {
		return total_a_pagar;
	}

	public void setTotal_a_pagar(double total_a_pagar) {
		this.total_a_pagar = total_a_pagar;
	}

	public int getDias_correspondiente_vacaciones() {
		return dias_correspondiente_vacaciones;
	}

	public void setDias_correspondiente_vacaciones(
			int dias_correspondiente_vacaciones) {
		this.dias_correspondiente_vacaciones = dias_correspondiente_vacaciones;
	}

	//	buscar 
	public Obj_Finiquitos buscar_finiquito(String folio_empleado_bms,int folio_empleado_scoi, String fecha_baja_scoi, String fecha_baja_bms){
		return new BuscarSQL().finiquito_base(folio_empleado_bms, folio_empleado_scoi, fecha_baja_scoi, fecha_baja_bms);
	}
	
	public boolean guardar(String status_emplead,String observacion){ return new GuardarSQL().Guardar_Finiquito(this, status_emplead, observacion); }
}
