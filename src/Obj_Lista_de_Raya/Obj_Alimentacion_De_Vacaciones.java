package Obj_Lista_de_Raya;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.GuardarSQL;

public class Obj_Alimentacion_De_Vacaciones {
//	datos del empleado
	int folio_vacaciones;
	int folio_empleado;//
	String empleado;
	String establecimiento;
	String puesto;
	String fecha_ingreso;
	String fecha_ingreso_imss;
//	float salario_diario_integrado;
	String grupo_vacacional;
	int proximas_vacaciones;
	byte[] imagen;
	
//	alimentacion de vacaciones
	String fecha_inicio;//
	String fecha_regresa;//
	
//	int anios_a_disfrutar;//
	int dias_de_vacaciones;//
	int dias_de_descanso_pagados;//
	int dias_trabajados_de_la_ultima_semana;//
	
	float mensualidad;
	float saldo_restante_de_prestamo;
	
//	vacaciones nc
	float sd_nc;//
	float sueldo_nc;
	float vacaciones_nc;
	float descansos_pagados_nc;
	float prima_vacacional_nc;
	float total_percepciones_nc;
	
//	deducciones nc
	float prestamo_nc;
	float infonavit_nc;
	float infonacot_nc;
	float pension_alimenticia_nc;
//	float fuente_de_sodas_nc;
//	float corte_de_caja_nc;
//	float cortes_nc;
	float otras_deducciones;
	float otras_percepciones;
	float cheque_nc;
	
	float efectivo_nc;
	
//	vacaciones c
	float sd_c;//
	float SDI_c;//
	float sueldo_c;
	float vacaciones_c;
	float descansos_pagados_c;
	float prima_vacacional_c;
	float total_percepciones_c;
	
//	deducciones c
//	float prestamo_c;
//	float infonavit_c;
//	float infonacot_c;
//	float pension_alimenticia_c;
	
	float prima_dominical_c;
	float bono_despensa_c;
	float premio_por_puntualidad_c;
	float premio_por_asistencia_c;
	float subsidio_c;
	
	float imss_c;
	float ispt_c;
	
	String observacion_vacaciones;
	String observacion_autorizacion;
	
//	float total_a_pagar_c;
	
	public Obj_Alimentacion_De_Vacaciones() {

//		datos del empleado
		folio_vacaciones=0;		folio_empleado=0;			empleado="";		establecimiento="";		puesto="";					
		fecha_ingreso="";		fecha_ingreso_imss="";	/*salario_diario_integrado=0;*/	grupo_vacacional="";
		proximas_vacaciones=0;	imagen=null;					
		
//		vacaciones
		fecha_inicio="";		fecha_regresa="";		/*anios_a_disfrutar=0;*/
		dias_de_vacaciones=0;	dias_de_descanso_pagados=0;		dias_trabajados_de_la_ultima_semana=0;
		
		mensualidad=0;			saldo_restante_de_prestamo=0;
		
//		alimentacion de vacaciones nc.
		sd_nc=0;		sueldo_nc=0;		vacaciones_nc=0;		descansos_pagados_nc=0;
		prima_vacacional_nc=0;		total_percepciones_nc=0;
//		deducciones nc
		prestamo_nc=0;		infonavit_nc=0;		infonacot_nc=0;		pension_alimenticia_nc=0;		/*fuente_de_sodas_nc=0;
		corte_de_caja_nc=0;		cortes_nc=0;		*/otras_deducciones=0;		otras_deducciones=0;		cheque_nc=0;		efectivo_nc=0;	     
		
//		alimentacion de vacaciones c.
		sd_c=0;		SDI_c=0;		sueldo_c=0;		vacaciones_c=0;		descansos_pagados_c=0;
		prima_vacacional_c=0;		total_percepciones_c=0;
//		deducciones c
//		prestamo_c=0;		infonavit_c=0;		infonacot_c=0;		pension_alimenticia_c=0;		
		imss_c=0;			ispt_c=0;		/*total_a_pagar_c=0;*/
		
		observacion_vacaciones = "";	observacion_autorizacion="";
	}
	
	public int getFolio_vacaciones() {
		return folio_vacaciones;
	}

	public void setFolio_vacaciones(int folio_vacaciones) {
		this.folio_vacaciones = folio_vacaciones;
	}

	public int getFolio_empleado() {
		return folio_empleado;
	}

	public void setFolio_empleado(int folio_empleado) {
		this.folio_empleado = folio_empleado;
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

	public String getPuesto() {
		return puesto;
	}

	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}

	public String getFecha_ingreso() {
		return fecha_ingreso;
	}

	public void setFecha_ingreso(String fecha_ingreso) {
		this.fecha_ingreso = fecha_ingreso;
	}

	public String getFecha_ingreso_imss() {
		return fecha_ingreso_imss;
	}

	public void setFecha_ingreso_imss(String fecha_ingreso_imss) {
		this.fecha_ingreso_imss = fecha_ingreso_imss;
	}

//	public float getSalario_diario_integrado() {
//		return salario_diario_integrado;
//	}
//
//	public void setSalario_diario_integrado(float salario_diario_integrado) {
//		this.salario_diario_integrado = salario_diario_integrado;
//	}

	public String getGrupo_vacacional() {
		return grupo_vacacional;
	}

	public void setGrupo_vacacional(String grupo_vacacional) {
		this.grupo_vacacional = grupo_vacacional;
	}

	public int getProximas_vacaciones() {
		return proximas_vacaciones;
	}

	public void setProximas_vacaciones(int proximas_vacaciones) {
		this.proximas_vacaciones = proximas_vacaciones;
	}

	public byte[] getImagen() {
		return imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	public String getFecha_inicio() {
		return fecha_inicio;
	}

	public void setFecha_inicio(String fecha_inicio) {
		this.fecha_inicio = fecha_inicio;
	}

	public String getFecha_regresa() {
		return fecha_regresa;
	}

	public void setFecha_final(String fecha_regresa) {
		this.fecha_regresa = fecha_regresa;
	}

//	public int getAnios_a_disfrutar() {
//		return anios_a_disfrutar;
//	}
//
//	public void setAnios_a_disfrutar(int anios_a_disfrutar) {
//		this.anios_a_disfrutar = anios_a_disfrutar;
//	}

	public int getDias_de_vacaciones() {
		return dias_de_vacaciones;
	}

	public void setDias_de_vacaciones(int dias_de_vacaciones) {
		this.dias_de_vacaciones = dias_de_vacaciones;
	}

	public int getDias_de_descanso_pagados() {
		return dias_de_descanso_pagados;
	}

	public void setDias_de_descanso_pagados(int dias_de_descanso_pagados) {
		this.dias_de_descanso_pagados = dias_de_descanso_pagados;
	}

	public float getMensualidad() {
		return mensualidad;
	}

	public void setMensualidad(float mensualidad) {
		this.mensualidad = mensualidad;
	}

	public float getSaldo_restante_de_prestamo() {
		return saldo_restante_de_prestamo;
	}

	public void setSaldo_restante_de_prestamo(float saldo_restante_de_prestamo) {
		this.saldo_restante_de_prestamo = saldo_restante_de_prestamo;
	}

	public int getDias_trabajados_de_la_ultima_semana() {
		return dias_trabajados_de_la_ultima_semana;
	}

	public void setDias_trabajados_de_la_ultima_semana(
			int dias_trabajados_de_la_ultima_semana) {
		this.dias_trabajados_de_la_ultima_semana = dias_trabajados_de_la_ultima_semana;
	}

	public float getSd_nc() {
		return sd_nc;
	}

	public void setSd_nc(float sd_nc) {
		this.sd_nc = sd_nc;
	}

	public float getSueldo_nc() {
		return sueldo_nc;
	}

	public void setSueldo_nc(float sueldo_nc) {
		this.sueldo_nc = sueldo_nc;
	}

	public float getVacaciones_nc() {
		return vacaciones_nc;
	}

	public void setVacaciones_nc(float vacaciones_nc) {
		this.vacaciones_nc = vacaciones_nc;
	}

	public float getDescansos_pagados_nc() {
		return descansos_pagados_nc;
	}

	public void setDescansos_pagados_nc(float descansos_pagados_nc) {
		this.descansos_pagados_nc = descansos_pagados_nc;
	}

	public float getPrima_vacacional_nc() {
		return prima_vacacional_nc;
	}

	public void setPrima_vacacional_nc(float prima_vacacional_nc) {
		this.prima_vacacional_nc = prima_vacacional_nc;
	}

	public float getTotal_percepciones_nc() {
		return total_percepciones_nc;
	}

	public void setTotal_percepciones_nc(float total_percepciones_nc) {
		this.total_percepciones_nc = total_percepciones_nc;
	}

	public float getPrestamo_nc() {
		return prestamo_nc;
	}

	public void setPrestamo_nc(float prestamo_nc) {
		this.prestamo_nc = prestamo_nc;
	}

	public float getInfonavit_nc() {
		return infonavit_nc;
	}

	public void setInfonavit_nc(float infonavit_nc) {
		this.infonavit_nc = infonavit_nc;
	}

	public float getInfonacot_nc() {
		return infonacot_nc;
	}

	public void setInfonacot_nc(float infonacot_nc) {
		this.infonacot_nc = infonacot_nc;
	}

	public float getPension_alimenticia_nc() {
		return pension_alimenticia_nc;
	}

	public void setPension_alimenticia_nc(float pension_alimenticia_nc) {
		this.pension_alimenticia_nc = pension_alimenticia_nc;
	}

//	public float getFuente_de_sodas_nc() {
//		return fuente_de_sodas_nc;
//	}
//
//	public void setFuente_de_sodas_nc(float fuente_de_sodas_nc) {
//		this.fuente_de_sodas_nc = fuente_de_sodas_nc;
//	}
//
//	public float getCorte_de_caja_nc() {
//		return corte_de_caja_nc;
//	}
//
//	public void setCorte_de_caja_nc(float corte_de_caja_nc) {
//		this.corte_de_caja_nc = corte_de_caja_nc;
//	}
//
//	public float getCortes_nc() {
//		return cortes_nc;
//	}
//
//	public void setCortes_nc(float cortes_nc) {
//		this.cortes_nc = cortes_nc;
//	}

	public float getCheque_nc() {
		return cheque_nc;
	}

	public float getOtras_deducciones() {
		return otras_deducciones;
	}

	public void setOtras_deducciones(float otras_deducciones) {
		this.otras_deducciones = otras_deducciones;
	}

	public float getOtras_percepciones() {
		return otras_percepciones;
	}

	public void setOtras_percepciones(float otras_percepciones) {
		this.otras_percepciones = otras_percepciones;
	}

	public void setCheque_nc(float cheque_nc) {
		this.cheque_nc = cheque_nc;
	}

	public float getEfectivo_nc() {
		return efectivo_nc;
	}

	public void setEfectivo_nc(float efectivo_nc) {
		this.efectivo_nc = efectivo_nc;
	}

	public float getSd_c() {
		return sd_c;
	}

	public void setSd_c(float sd_c) {
		this.sd_c = sd_c;
	}

	public void setFecha_regresa(String fecha_regresa) {
		this.fecha_regresa = fecha_regresa;
	}

	public float getSDI_c() {
		return SDI_c;
	}

	public void setSDI_c(float sDI_c) {
		SDI_c = sDI_c;
	}

	public float getSueldo_c() {
		return sueldo_c;
	}

	public void setSueldo_c(float sueldo_c) {
		this.sueldo_c = sueldo_c;
	}

	public float getVacaciones_c() {
		return vacaciones_c;
	}

	public void setVacaciones_c(float vacaciones_c) {
		this.vacaciones_c = vacaciones_c;
	}

	public float getDescansos_pagados_c() {
		return descansos_pagados_c;
	}

	public void setDescansos_pagados_c(float descansos_pagados_c) {
		this.descansos_pagados_c = descansos_pagados_c;
	}

	public float getPrima_vacacional_c() {
		return prima_vacacional_c;
	}

	public void setPrima_vacacional_c(float prima_vacacional_c) {
		this.prima_vacacional_c = prima_vacacional_c;
	}

	public float getTotal_percepciones_c() {
		return total_percepciones_c;
	}

	public void setTotal_percepciones_c(float total_percepciones_c) {
		this.total_percepciones_c = total_percepciones_c;
	}

//	public float getPrestamo_c() {
//		return prestamo_c;
//	}
//
//	public void setPrestamo_c(float prestamo_c) {
//		this.prestamo_c = prestamo_c;
//	}
//
//	public float getInfonavit_c() {
//		return infonavit_c;
//	}
//
//	public void setInfonavit_c(float infonavit_c) {
//		this.infonavit_c = infonavit_c;
//	}
//
//	public float getInfonacot_c() {
//		return infonacot_c;
//	}
//
//	public void setInfonacot_c(float infonacot_c) {
//		this.infonacot_c = infonacot_c;
//	}
//
//	public float getPension_alimenticia_c() {
//		return pension_alimenticia_c;
//	}
//
//	public void setPension_alimenticia_c(float pension_alimenticia_c) {
//		this.pension_alimenticia_c = pension_alimenticia_c;
//	}

	public float getImss_c() {
		return imss_c;
	}

	public float getPrima_dominical_c() {
		return prima_dominical_c;
	}

	public void setPrima_dominical_c(float prima_dominical_c) {
		this.prima_dominical_c = prima_dominical_c;
	}

	public float getBono_despensa_c() {
		return bono_despensa_c;
	}

	public void setBono_despensa_c(float bono_despensa_c) {
		this.bono_despensa_c = bono_despensa_c;
	}

	public float getPremio_por_puntualidad_c() {
		return premio_por_puntualidad_c;
	}

	public void setPremio_por_puntualidad_c(float premio_por_puntualidad_c) {
		this.premio_por_puntualidad_c = premio_por_puntualidad_c;
	}

	public float getPremio_por_asistencia_c() {
		return premio_por_asistencia_c;
	}

	public void setPremio_por_asistencia_c(float premio_por_asistencia_c) {
		this.premio_por_asistencia_c = premio_por_asistencia_c;
	}

	public float getSubsidio_c() {
		return subsidio_c;
	}

	public void setSubsidio_c(float subsidio_c) {
		this.subsidio_c = subsidio_c;
	}

	public void setImss_c(float imss_c) {
		this.imss_c = imss_c;
	}

	public float getIspt_c() {
		return ispt_c;
	}

	public void setIspt_c(float ispt_c) {
		this.ispt_c = ispt_c;
	}

	public String getObservacion_vacaciones() {
		return observacion_vacaciones;
	}

	public void setObservacion_vacaciones(String observacion_vacaciones) {
		this.observacion_vacaciones = observacion_vacaciones;
	}

	public String getObservacion_autorizacion() {
		return observacion_autorizacion;
	}

	public void setObservacion_autorizacion(String observacion_autorizacion) {
		this.observacion_autorizacion = observacion_autorizacion;
	}

	//	resive parametro del filtro para un nuevo regitro y lo busca aqui solo alimenta la informacion del empleado
	public Obj_Alimentacion_De_Vacaciones buscar(int folio, String movimiento){ 
		try {
			return new BuscarSQL().Empleado_En_Vacaciones(folio,movimiento);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	return null; 
	}
	
//	busca al empleado y calcula sus vacaciones automaticas desde el calendario
	public Obj_Alimentacion_De_Vacaciones buscar_vacaciones(int folio_empleado, Date fecha_inicio_vacaciones,int anios_de_proximas_vacaciones){ 
		try {
			return new BuscarSQL().calcular_vacaciones(folio_empleado,  new SimpleDateFormat("dd/MM/yyyy").format(fecha_inicio_vacaciones),anios_de_proximas_vacaciones);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	return null; 
	}
	
//	mandar folio del empleado para buscar y ediatar sus ultimas vacacioenes
	public Obj_Alimentacion_De_Vacaciones buscar_vacaciones_guardadas(int folio_empleado){ 
		try {
			return new BuscarSQL().vacaciones_guardadas(folio_empleado);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	return null; 
	}
	
//	guardar vacaciones calculadas
	public boolean guardar_vacaciones_calculadas(String movimiento){
		return new GuardarSQL().Guardar_Vacaciones_Calculadas(this,movimiento); 
	}
	
	
	public boolean buscar_vacaciones_para_update(int folio_vacaciones){
		return new BuscarSQL().validacion_de_vacaciones_para_btnGuardar(folio_vacaciones);
	}
	
//	public boolean actualizar(int folio_vacaciones){ return new ActualizarSQL().Actualizar_Vacaciones(this,folio_vacaciones); }
	
//	buscar empleado para alimentar ultimas vacaciones
	public Obj_Alimentacion_De_Vacaciones buscar_empleado_para_vacaciones(int folio_empleado){ 
		try {
			return new BuscarSQL().empleado_para_ultimas_vacaciones(folio_empleado);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	return null; 
	}
	
//	guardar vacaciones pasadas manualmente
	public boolean guardar_ultimas_vacaciones(){
		return new GuardarSQL().Guardar_Vacaciones_Pasadas(this); 
	}


}
