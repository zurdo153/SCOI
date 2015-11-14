package Obj_Contabilidad;


public class Obj_Corte_De_Orden_De_Pago {
	
	
	public boolean guardar_corte_de_ordenes_de_pago(Object[] tabla,String folio){
		return new Conexiones_SQL.ActualizarSQL().Actualizar_Pagos_en_un_Corte_de_Ordenes_de_Pago(tabla,folio );
	}
	
}
