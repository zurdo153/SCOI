package Cat_Principal;

import Obj_Administracion_del_Sistema.Obj_Usuario;
 
public class Cat_Comandos {
	
	String comando="";
	Obj_Usuario usuario = new Obj_Usuario().LeerSession();
	
public String maximos_y_minimos(String Reporte, String Establecimiento){	
		
		if(Reporte.equals("Reporte De Pedido Sugerido De Maximos y Minimos Del Establecimiento")){
			comando="declare @establecimiento varchar(250) set @establecimiento='"+Establecimiento+"'"
					+ " declare @cod_estab int"
					+ " set @cod_estab= (select cod_estab  from establecimientos where nombre=@establecimiento)"
					+ " select cod_prod"
					+ "        ,descripcion_completa"
					+ " 	   ,inventario_minimo"
					+ " 	   ,inventario_maximo"
					+ " 	   ,convert(numeric(10,2),exist_piezas) as  exist_piezas_de_la_tienda"
					+ " 	   ,convert(numeric(10,2),existencia_cedis) as existencia_cedis"
					+ " 	   ,convert(numeric(10,2),nresurtido ) as nresurtido"
					+ " 	   ,convert(numeric(10,2),case when existencia_cedis>nresurtido then nresurtido"
					+ "								   when existencia_cedis<nresurtido then existencia_cedis"
					+ "								   else existencia_cedis-nresurtido end) as disponible_para_surtido"
					+ "        ,@establecimiento as establecimiento"
					+ "  	   ,convert(varchar(15),getdate(),103)+' '+ convert(varchar(15),getdate(),108) as fecha" 
					+"         , 'Pedido Sugerido Por Maximos y Minimos Del Establecimiento '+@establecimiento+' Del  Dia:'+convert(varchar(15),getdate(),103) as reporte"
					+ "        ,area "
					+ "        ,estatus_producto "	
					+ "  from(select p.cod_prod"
					+ " 			 ,productos.descripcion_completa"
					+ " 			 ,p.inventario_minimo"
					+ " 			 ,p.inventario_maximo"
					+ " 			 ,(p.exist_unidades*contenido)+p.exist_piezas as exist_piezas"
					+ " 			 ,isnull((select (prodestab.exist_unidades*contenido)+prodestab.exist_piezas from prodestab where prodestab.cod_prod=p.cod_prod and cod_estab=28),0) as existencia_cedis"
					+ " 			 ,(p.inventario_maximo)-((p.exist_unidades*contenido)+p.exist_piezas) as nresurtido"
					+ "              ,isnull(areas.nombre,'Sin Clasificacion de Distribucion') as area"
					+ "              ,case when productos.status_producto=1 then 'VIGENTE' when productos.status_producto=2 then 'CANCELADO' when productos.status_producto=3 then 'DESCONTINUADO'  end as estatus_producto"								 
					+ " 		  from prodestab p with (nolock)"
					+ "		 INNER JOIN productos with (nolock) ON p.cod_prod = productos.cod_prod "
					+ "      LEFT OUTER JOIN areas with (nolock) on areas.area=productos.area "
					+ "		where p.cod_estab=@cod_estab and p.inventario_minimo>1  and ((p.exist_unidades*contenido)+p.exist_piezas)<p.inventario_minimo and productos.status_producto=1 and productos.area='1' )a where nresurtido>0 order by descripcion_completa";
		}
		
		if(Reporte.equals(	"Reporte De Maximos y Minimos Del Establecimiento")){
			comando="declare @establecimiento varchar(250) set @establecimiento='"+Establecimiento+"'"
					+ " declare @cod_estab int"
					+ " set @cod_estab= (select cod_estab  from establecimientos where nombre=@establecimiento)"
					+ " select cod_prod"
					+ "        ,descripcion_completa"
					+ " 	   ,inventario_minimo"
					+ " 	   ,inventario_maximo"
					+ " 	   ,convert(numeric(10,2),exist_piezas) as  exist_piezas_de_la_tienda"
					+ " 	   ,convert(numeric(10,2),existencia_cedis) as existencia_cedis"
					+ " 	   ,convert(numeric(10,2),nresurtido ) as nresurtido"
					+ " 	   ,convert(numeric(10,2),case when existencia_cedis>nresurtido then nresurtido"
					+ "								   when existencia_cedis<nresurtido then existencia_cedis"
					+ "								   else existencia_cedis-nresurtido end) as disponible_para_surtido"
					+ "        ,@establecimiento as establecimiento"
					+ "  	   ,convert(varchar(15),getdate(),103)+' '+ convert(varchar(15),getdate(),108) as fecha" 
					+"         ,'Reporte De Maximos y Minimos Del Establecimiento '+@establecimiento+' Del  Dia:'+convert(varchar(15),getdate(),103) as reporte"
					+ "        ,area "
					+ "        ,estatus_producto "	
					+ "  from(select p.cod_prod"
					+ " 			 ,productos.descripcion_completa"
					+ " 			 ,p.inventario_minimo"
					+ " 			 ,p.inventario_maximo"
					+ " 			 ,(p.exist_unidades*contenido)+p.exist_piezas as exist_piezas"
					+ " 			 ,isnull((select (prodestab.exist_unidades*contenido)+prodestab.exist_piezas from prodestab where prodestab.cod_prod=p.cod_prod and cod_estab=28),0) as existencia_cedis"
					+ " 			 ,(p.inventario_maximo)-((p.exist_unidades*contenido)+p.exist_piezas) as nresurtido"
					+ "              ,isnull(areas.nombre,'Sin Clasificacion de Distribucion') as area"
					+ "              ,case when productos.status_producto=1 then 'VIGENTE' when productos.status_producto=2 then 'CANCELADO' when productos.status_producto=3 then 'DESCONTINUADO'  end as estatus_producto"
					+ " 		  from prodestab p with (nolock)"
					+ "		 INNER JOIN productos with (nolock) ON p.cod_prod = productos.cod_prod "
					+ "      LEFT OUTER JOIN areas with (nolock) on areas.area=productos.area "
					+ "		where p.cod_estab=@cod_estab and p.inventario_minimo>1 )a order by descripcion_completa";
		}
		
		if(Reporte.equals(	"Reporte De Exceso De Acuerdo A Los Maximos Del Establecimiento")){
			comando="declare @establecimiento varchar(250) set @establecimiento='"+Establecimiento+"'"
					+ " declare @cod_estab int"
					+ " set @cod_estab= (select cod_estab  from establecimientos where nombre=@establecimiento)"
					+ " select cod_prod"
					+ "        ,descripcion_completa"
					+ " 	   ,inventario_minimo"
					+ " 	   ,inventario_maximo"
					+ " 	   ,convert(numeric(10,2),exist_piezas) as  exist_piezas_de_la_tienda"
					+ " 	   ,convert(numeric(10,2),existencia_cedis) as existencia_cedis"
					+ " 	   ,convert(numeric(10,2),nresurtido ) as nresurtido"
					+ " 	   ,convert(numeric(10,2),case when existencia_cedis>nresurtido then nresurtido"
					+ "								   when existencia_cedis<nresurtido then existencia_cedis"
					+ "								   else existencia_cedis-nresurtido end) as disponible_para_surtido"
					+ "        ,@establecimiento as establecimiento"
					+ "  	   ,convert(varchar(15),getdate(),103)+' '+ convert(varchar(15),getdate(),108) as fecha" 
					+"         ,'Reporte De Exceso De Acuerdo A Los Maximos Del Establecimiento '+@establecimiento+' Del  Dia:'+convert(varchar(15),getdate(),103) as reporte"
					+ "        ,area "
					+ "        ,estatus_producto "	
					+ "  from(select p.cod_prod"
					+ " 			 ,productos.descripcion_completa"
					+ " 			 ,p.inventario_minimo"
					+ " 			 ,p.inventario_maximo"
					+ " 			 ,(p.exist_unidades*contenido)+p.exist_piezas as exist_piezas"
					+ " 			 ,isnull((select (prodestab.exist_unidades*contenido)+prodestab.exist_piezas from prodestab where prodestab.cod_prod=p.cod_prod and cod_estab=28),0) as existencia_cedis"
					+ " 			 ,(p.inventario_maximo)-((p.exist_unidades*contenido)+p.exist_piezas) as nresurtido"
					+ "              ,isnull(areas.nombre,'Sin Clasificacion de Distribucion') as area"
					+ "              ,case when productos.status_producto=1 then 'VIGENTE' when productos.status_producto=2 then 'CANCELADO' when productos.status_producto=3 then 'DESCONTINUADO'  end as estatus_producto"
					+ " 		  from prodestab p with (nolock)"
					+ "		 INNER JOIN productos with (nolock) ON p.cod_prod = productos.cod_prod "
					+ "      LEFT OUTER JOIN areas with (nolock) on areas.area=productos.area "
					+ "		where p.cod_estab=@cod_estab and p.inventario_minimo>1 )a where a.exist_piezas>a.inventario_maximo order by descripcion_completa";
		}
	
		return comando;
	}	
	

public String dinero_electronico(String Reporte, String tarjeta_Pyde, String Asignacion,String Cajero, String fecha_inicial, String fecha_final, String Establecimiento, String operacion){	
	
	if(Reporte.equals(	"Reporte De Dinero Electronico Usado En Una Asignacion")){
		comando=" declare @asignacion varchar(20) set @asignacion='"+ tarjeta_Pyde+"'"
			 + " select   bonificaciones_analiticas.folio as ticket"
			 + "          ,bonificaciones_analiticas.cantidad"
			 + " 		  ,bonificaciones_analiticas.tarjeta_pyde"
			 + " 		  ,f.fecha"
			 + "  		  ,cajero"
			 + " 		  ,IZAGAR_Relacion_de_Asignaciones_Liquidadas.Nombre_Cajero"
			 + " 		  ,tarjetas_pyde.nombre"
			 + "		  ,IZAGAR_Relacion_de_Asignaciones_Liquidadas.Asignacion"
			 + "		  ,IZAGAR_Relacion_de_Asignaciones_Liquidadas.Establecimiento"
			 + "		  ,IZAGAR_Relacion_de_Asignaciones_Liquidadas.dinero_electronico"
			 + "		  ,convert(varchar(15),Fecha_Asignacion,103)+' '+convert(varchar(15),Fecha_Asignacion,108) as Fecha_Asignacion"
			 + "          ,convert(varchar(15),Fecha_Liquidacion,103)+' '+convert(varchar(15),Fecha_Liquidacion,108) as Fecha_Liquidacion"
			 + "    from dbo.bonificaciones_analiticas with (nolock)"
			 + "   INNER JOIN tarjetas_pyde on tarjetas_pyde.tarjeta_pyde=bonificaciones_analiticas.tarjeta_pyde"
			 + "   INNER JOIN (select folio, convert(varchar(15),fecha,103)+' '+convert(varchar(15),fecha,108) as fecha ,folio_cajero from  dbo.facremtick with (nolock) where transaccion in ('36','37','38') and facremtick.folio_cajero=@asignacion) f on f.folio=bonificaciones_analiticas.folio"
			 + "   LEFT OUTER JOIN  dbo.IZAGAR_Relacion_de_Asignaciones_Liquidadas IZAGAR_Relacion_de_Asignaciones_Liquidadas ON IZAGAR_Relacion_de_Asignaciones_Liquidadas.Asignacion=f.folio_cajero"
			 + "	  where operacion='U' "; 
	}
	
	
	if(Reporte.equals(	"Reporte De Dinero Electronico Acumulado De La Tarjeta")){
		comando="declare @tarjeta_pyde varchar(20) , @operacion char(1)   set @tarjeta_pyde='"+ tarjeta_Pyde+"'"
				+ "   set @operacion='B'"
				+ " select facremtick.folio as ticket"
				+ "        ,IZAGAR_Relacion_de_Asignaciones_Liquidadas.Nombre_Cajero"
				+ " 	   ,folio_cajero as asignacion"
				+ "	       ,IZAGAR_Relacion_de_Asignaciones_Liquidadas.Fecha_Asignacion"
				+ "  	   ,IZAGAR_Relacion_de_Asignaciones_Liquidadas.Fecha_Liquidacion"
				+ " 	   ,rtrim(ltrim(establecimientos.nombre)) as Establecimiento"
				+ " 	   ,establecimientos.nombre as establecimiento"
				+ "        ,convert(varchar(15),facremtick.fecha,103)+' '+convert(varchar(15),facremtick.fecha,108) as fecha"
				+ " 	   ,facremtick.total as total_ticket"
				+ " 	   ,(select top 1 cantidad from bonificaciones_analiticas with (nolock)  where folio=facremtick.folio and transaccion in ('36','37','38') and operacion=@operacion ) as cantidad_dinero_electronico"
				+ " 	   ,case when @operacion='B' then 'Dinero Electronico Acumulado De La Tarjeta '+@tarjeta_pyde+' A Nombre De '+tarjetas_pyde.nombre when @operacion='U' then 'Dinero Electronico Usado De La Tarjeta '+@tarjeta_pyde+' A Nombre De '+tarjetas_pyde.nombre  end as reporte"
				+ "   from facremtick with (nolock)"
				+ "    left outer join IZAGAR_Relacion_de_Asignaciones_Liquidadas with (nolock) on IZAGAR_Relacion_de_Asignaciones_Liquidadas.Asignacion=facremtick.folio_cajero"
				+ "    inner join establecimientos with (nolock) on establecimientos.cod_estab=facremtick.cod_estab"
				+ "    left outer join tarjetas_pyde with (nolock) on tarjetas_pyde.tarjeta_pyde=@tarjeta_pyde"
				+ "  where transaccion in ('36','37','38') and folio in (select folio from bonificaciones_analiticas where tarjeta_pyde=@tarjeta_pyde AND transaccion in ('36','37','38') AND operacion=@operacion ) order by facremtick.fecha";
	}

	if(Reporte.equals("Reporte De Dinero Electronico En Un Periodo Por Establecimiento Acumulado")||Reporte.equals("Reporte De Dinero Electronico En Un Periodo Por Establecimiento Usado")){	
		comando="declare @fi datetime,@ff datetime, @tarjeta_pyde varchar(20) , @operacion char(1),@establecimiento varchar(80)   ,@cod_estab int"
				+ " 	set @tarjeta_pyde='"+ tarjeta_Pyde+"'"+" set @operacion='"+operacion+"'"
				+ "	    set @fi ='"+fecha_inicial+"' set @ff='"+fecha_final+"' set @establecimiento='"+Establecimiento+"' "
				+ " set @cod_estab =( select cod_estab from establecimientos where nombre =@establecimiento )  if @cod_estab is null set @cod_estab=0 "
				+ "	 select  facremtick.folio as ticket"
				+ "	        ,IZAGAR_Relacion_de_Asignaciones_Liquidadas.Nombre_Cajero"
				+ " 	    ,folio_cajero as asignacion"
				+ " 	    ,IZAGAR_Relacion_de_Asignaciones_Liquidadas.fecha_asignacion"
				+ "	  	    ,IZAGAR_Relacion_de_Asignaciones_Liquidadas.fecha_liquidacion"
				+ "	 	    ,rtrim(ltrim(establecimientos.nombre)) as establecimiento"
				+ "	        ,convert(varchar(15),facremtick.fecha,103)+' '+convert(varchar(15),facremtick.fecha,108) as fecha"
				+ "			,bonificaciones_analiticas.tarjeta_pyde"
				+ "			,tarjetas_pyde.nombre"
				+ "			,dbo.edad(tarjetas_pyde.fecha_nacimiento,getdate()) as edad"
				+ "			,tarjetas_pyde.calle"
				+ " 		,tarjetas_pyde.colonia"
				+ "			,tarjetas_pyde.pobmunedo"
				+ "	 	    ,facremtick.total as total_ticket"
				+ "	 	    ,(select top 1 cantidad from bonificaciones_analiticas with (nolock)  where folio=facremtick.folio and transaccion in ('36','37','38') and operacion=@operacion ) as cantidad_dinero_electronico"
				+ "	 	    ,case when @operacion='B' then 'Dinero Electronico Acumulado De Las Tarjeta En El Periodo de '+convert(varchar(10),@fi,103)+' al '+convert(varchar(10),@ff,103) end as reporte"
				+ "	   from facremtick with (nolock)"
				+ "      inner join bonificaciones_analiticas with (nolock) on bonificaciones_analiticas.folio=facremtick.folio"
				+ "      left join tarjetas_pyde with (nolock) on tarjetas_pyde.tarjeta_pyde=bonificaciones_analiticas.tarjeta_pyde"
				+ "      left outer join IZAGAR_Relacion_de_Asignaciones_Liquidadas with (nolock) on IZAGAR_Relacion_de_Asignaciones_Liquidadas.Asignacion=facremtick.folio_cajero"
				+ "      inner join establecimientos with (nolock) on establecimientos.cod_estab=facremtick.cod_estab"
				+ "	  where facremtick.transaccion in ('36','37','38') and facremtick.fecha between @fi and @ff and facremtick.cod_estab = case when @cod_estab =0 then facremtick.cod_estab else @cod_estab end"
				+ "			    and bonificaciones_analiticas.tarjeta_pyde= case when @tarjeta_pyde='' then bonificaciones_analiticas.tarjeta_pyde else @tarjeta_pyde end AND operacion=@operacion"
				+ "					  order by establecimientos.nombre,facremtick.fecha ";
					     
	}
	
	if(Reporte.equals(	"Reporte De Dinero Electronico Usado De La Tarjeta")){
		comando="declare @tarjeta_pyde varchar(20) , @operacion char(1)   set @tarjeta_pyde='"+ tarjeta_Pyde+"'"
				+ "   set @operacion='U'"
				+ " select facremtick.folio as ticket"
				+ "        ,IZAGAR_Relacion_de_Asignaciones_Liquidadas.Nombre_Cajero"
				+ " 	   ,folio_cajero as asignacion"
				+ "	       ,IZAGAR_Relacion_de_Asignaciones_Liquidadas.Fecha_Asignacion"
				+ "  	   ,IZAGAR_Relacion_de_Asignaciones_Liquidadas.Fecha_Liquidacion"
				+ " 	   ,rtrim(ltrim(establecimientos.nombre)) as Establecimiento"
				+ " 	   ,establecimientos.nombre as establecimiento"
				+ "        ,convert(varchar(15),facremtick.fecha,103)+' '+convert(varchar(15),facremtick.fecha,108) as fecha"
				+ " 	   ,facremtick.total as total_ticket"
				+ " 	   ,(select cantidad from bonificaciones_analiticas with (nolock)  where folio=facremtick.folio and transaccion in ('36','37','38') and operacion=@operacion ) as cantidad_dinero_electronico"
				+ " 	   ,case when @operacion='B' then 'Dinero Electronico Acumulado De La Tarjeta '+@tarjeta_pyde+' A Nombre De '+tarjetas_pyde.nombre when @operacion='U' then 'Dinero Electronico Usado De La Tarjeta '+@tarjeta_pyde+' A Nombre De '+tarjetas_pyde.nombre  end as reporte"
				+ "   from facremtick with (nolock)"
				+ "    left outer join IZAGAR_Relacion_de_Asignaciones_Liquidadas with (nolock) on IZAGAR_Relacion_de_Asignaciones_Liquidadas.Asignacion=facremtick.folio_cajero"
				+ "    inner join establecimientos with (nolock) on establecimientos.cod_estab=facremtick.cod_estab"
				+ "    left outer join tarjetas_pyde with (nolock) on tarjetas_pyde.tarjeta_pyde=@tarjeta_pyde"
				+ "  where transaccion in ('36','37','38') and folio in (select folio from bonificaciones_analiticas where tarjeta_pyde=@tarjeta_pyde AND transaccion in ('36','37','38') AND operacion=@operacion ) order by facremtick.fecha";
	}
	
	
	if(Reporte.equals(	"Reporte De Dinero Electronico De Tarjetas Acumulado, Usado y Saldo")){
		comando=" select bonificaciones_analiticas.tarjeta_pyde,tarjetas_pyde.nombre"
				+ "		 ,isnull(sum(case when bonificaciones_analiticas.operacion = 'B' then bonificaciones_analiticas.cantidad end ), 0)  as dinero_electronico_acumnulado"
				+ "		 ,isnull(sum(case when bonificaciones_analiticas.operacion = 'U' then bonificaciones_analiticas.cantidad end ), 0)  as dinero_electronico_gastado"
				+ "		 ,isnull(sum(case when bonificaciones_analiticas.tipo_bonificacion = 'D' then case when bonificaciones_analiticas.operacion = 'U' then bonificaciones_analiticas.cantidad * -1 else bonificaciones_analiticas.cantidad end end), 0)  as dinero_electronico_actual"
				+ "		 ,count(folio) as cantidad_de_tickets"
				+ "		 ,fecha_nacimiento"
				+ "		 ,calle"
				+ "		 ,case when sexo='F' then 'FEMENINO'  when sexo='M' then 'MASCULINO' else 'NO TIENE' end as sexo"
				+ "		 ,colonia"
				+ "		 ,pobmunedo as poblacion"
				+ "		 ,dbo.edad(fecha_nacimiento,getdate()) as edad"
				+ "      ,'Reporte De Dinero Electronico De Tarjetas Acumulado, Usado y Saldo' as reporte"
				+ "  from bonificaciones_analiticas"
				+ "     LEFT OUTER JOIN	tarjetas_pyde on tarjetas_pyde.tarjeta_pyde=bonificaciones_analiticas.tarjeta_pyde"
				+ "   group by bonificaciones_analiticas.tarjeta_pyde,tarjetas_pyde.nombre,fecha_nacimiento,calle,sexo,colonia,pobmunedo order by sum(cantidad)";
	}
	
	
	
	
	
	return comando;
}

public String Ubicaciones(String Reporte, String folio){	
	
	if(Reporte.equals("Consulta De Recepcion De Mercancia")){
		comando=     "   SELECT     RTRIM(LTRIM(E.cod_prod))  AS cod_prod"
					+ "	          ,P.codigo_barras_pieza"
					+ "	          ,P.descripcion_completa"
					+ "			  ,isnull((select top 1 upper(localizacion)+'      ' from localizaciones_surtido_productos with(nolock) where cod_prod = E.cod_prod and cod_estab = E.cod_estab),'SIN LOCALIZACION') as localicacion"
					+ "			  ,isnull((select top 1 SUBSTRING(upper(localizacion),0,3) from localizaciones_surtido_productos with(nolock) where cod_prod = E.cod_prod and cod_estab = E.cod_estab),0) as zona"
					+ "			  ,isnull((select top 1 SUBSTRING(upper(localizacion),3,3) from localizaciones_surtido_productos with(nolock) where cod_prod = E.cod_prod and cod_estab = E.cod_estab),0) as pasillo"
					+ "			  ,isnull((select top 1 SUBSTRING(upper(localizacion),6,3) from localizaciones_surtido_productos with(nolock) where cod_prod = E.cod_prod and cod_estab = E.cod_estab),0) as rack"
					+ "			  ,isnull((select top 1 SUBSTRING(upper(localizacion),9,1) from localizaciones_surtido_productos with(nolock) where cod_prod = E.cod_prod and cod_estab = E.cod_estab),0) as nivel"
					+ "			  ,E.cantidad"
					+ "           ,E.abreviatura_unidad"
					+ "			  ,(select rtrim(ltrim(nombre)) from establecimientos Est where Est.cod_estab=E.cod_estab) as establecimiento"
					+ "			  ,E.folio as recepcion"
					+ "   		  ,E.status"
					+ "			  ,E.fecha"
					+ "			  ,E.precio_lista"
					+ "			  ,E.descuento_porcentual"
					+ "			  ,E.importe_descuento"
					+ "			  ,E.importe"
					+ "			  ,E.iva"
					+ "			  ,E.ieps"
					+ "			  ,E.costo"
					+ "			  ,E.total"
					+ "			  ,PRV.razon_social"
					+ "			  ,'"+usuario.getNombre_completo()+"' as usuario"
					+ "		 	  ,'' as ubicador"
					+ "     FROM  entysal E"
					+ "	  LEFT OUTER JOIN  productos P WITH (nolock) ON E.cod_prod = P.cod_prod"
					+ "	  LEFT OUTER JOIN  proveedores PRV WITH (nolock) ON E.cod_prv=PRV.cod_prv "
					+ " WHERE   E.folio = '"+folio+"' AND (E.transaccion ='44') "
					+ " ORDER BY ZONA,PASILLO,RACK,NIVEL,descripcion_completa";
	}
	
	if(Reporte.equals("Consulta De Entrada De Mercancia")){
		comando=     " SELECT      RTRIM(LTRIM(P.cod_prod))  AS cod_prod"
				+ "		   ,P.codigo_barras_pieza"
				+ "		   ,P.descripcion_completa"
				+ "        ,isnull((select top 1 upper(localizacion)+'      ' from localizaciones_surtido_productos with(nolock) where cod_prod = m_eos_mercancia.cod_prod and cod_estab = m_eos_mercancia.cod_estab),'SIN LOCALIZACION') as localicacion"
				+ "		   ,isnull((select top 1 SUBSTRING(upper(localizacion),0,3) from localizaciones_surtido_productos with(nolock) where cod_prod = m_eos_mercancia.cod_prod and cod_estab = m_eos_mercancia.cod_estab),0) as zona"
				+ "		   ,isnull((select top 1 SUBSTRING(upper(localizacion),3,3) from localizaciones_surtido_productos with(nolock) where cod_prod = m_eos_mercancia.cod_prod and cod_estab = m_eos_mercancia.cod_estab),0) as pasillo"
				+ "		   ,isnull((select top 1 SUBSTRING(upper(localizacion),6,3) from localizaciones_surtido_productos with(nolock) where cod_prod = m_eos_mercancia.cod_prod and cod_estab = m_eos_mercancia.cod_estab),0) as rack"
				+ "		   ,isnull((select top 1 SUBSTRING(upper(localizacion),9,1) from localizaciones_surtido_productos with(nolock) where cod_prod = m_eos_mercancia.cod_prod and cod_estab = m_eos_mercancia.cod_estab),0) as nivel"
				+ "        ,m_eos_mercancia.cantidad"
				+ "		   ,m_eos_mercancia.abreviatura_unidad"
				+ "		   ,(select rtrim(ltrim(nombre)) from establecimientos Est where Est.cod_estab=m_eos_mercancia.cod_estab) as establecimiento"
				+ "		   ,m_eos_mercancia.folio"
				+ "		   ,m_eos_mercancia.status"
				+ "		   ,m_eos_mercancia.fecha"
				+ " 		   ,PRV.razon_social"
				+ "		   ,'+usuario.getNombre_completo()+' as usuario"
				+ "		   ,'' as ubicador"
				+ "  FROM  m_eos_mercancia WITH (nolock)"
				+ "      LEFT OUTER JOIN  productos P WITH (nolock) ON  m_eos_mercancia.cod_prod = P.cod_prod"
				+ "      LEFT OUTER JOIN  proveedores PRV WITH (nolock) ON m_eos_mercancia.cod_prv=PRV.cod_prv"
				+ "   WHERE (m_eos_mercancia.folio = '"+folio+"') AND (m_eos_mercancia.transaccion = '56')"
				+ "     ORDER BY ZONA,PASILLO,RACK,NIVEL,descripcion_completa";
	}
	
	return comando;

}
public String markup(String Reporte, String establecimiento){	
	
	if(Reporte.equals("Reporte De Markup De Productos Por Establecimiento")){
		comando=     " declare @cod_estab int set @cod_estab=(SELECT cod_estab from establecimientos  where nombre='"+establecimiento+"')"
				   + "    SELECT"
				   + "        productos.cod_prod"
				   + "        ,productos.descripcion"
				   + "  	  ,isnull(clases_productos.nombre,'NO TIENE') as clases_productos"
				   + " 		  ,isnull(categorias.nombre,'NO TIENE') as categorias"
				   + "        ,isnull(familias.nombre,'NO TIENE') as familia"
				   + "        ,convert(numeric(10,2),isnull( (case when (productos.contenido)<>1 then((productos.contenido*prodestab.exist_unidades)+exist_piezas) else (prodestab.exist_piezas)end),0 )) as existencia_pz"
				   + "        ,convert(numeric (10,2),case when(productos.contenido<>1) then (case when productos.iva_interior=16 then (prodestab.ultimo_costo*1.16)/productos.contenido else (prodestab.ultimo_costo)/productos.contenido end)"
				   + "		                             else (case when productos.iva_interior=16 then  prodestab.ultimo_costo*1.16 else prodestab.ultimo_costo end ) end  )as ultimo_costo_con_iva"
				   + "        ,case when productos.tasa_ieps=1 then 0.08 else 0 end as ieps"
				   + "		  ,productos.contenido"
				   + "        ,productos.iva_interior"
				   + "        ,convert(numeric (10,2),case 1 WHEN 0 then dbo.precio_venta_rpt(productos.cod_prod, 'P', 1, GETDATE(), '', 1, 0, 0)"
				   + "		     ELSE	dbo.precio_venta_rpt(productos.cod_prod, 'P', 1, GETDATE(), '', 1, 0, 0) * (1 + dbo.Ieps(productos.cod_prod)/100)"
				   + "				* (1 + (CASE 'I' WHEN 'I' THEN productos.iva_interior ELSE productos.iva_fronterizo END/100))  END) as precio_pieza"
				   + "        ,'Reporte De Markup y Margen De Productos Por Establecimiento '+'"+establecimiento+"' as reporte"
				   + "       FROM prodestab with (nolock)"
				   + "           inner join productos with (nolock) on productos.cod_prod=prodestab.cod_prod"
				   + "	         inner join establecimientos on establecimientos.cod_estab=prodestab.cod_estab"
				   + "           left outer join familias on familias.familia=productos.familia"
				   + "   		 left outer join clases_productos on clases_productos.clase_producto=productos.clase_producto"
				   + "			 left outer join categorias on categorias.categoria=productos.categoria"
				   + "    where prodestab.cod_estab= @cod_estab order by productos.cod_prod";
	}
	

	
	return comando;

   }

}
