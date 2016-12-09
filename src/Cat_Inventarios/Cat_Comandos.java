package Cat_Inventarios;

public class Cat_Comandos {
	
	public String Comandos_Maximos_y_Minimos(String Reporte, String Establecimiento){
		String comando="";
		
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
					+ "  from(select p.cod_prod"
					+ " 			 ,productos.descripcion_completa"
					+ " 			 ,p.inventario_minimo"
					+ " 			 ,p.inventario_maximo"
					+ " 			 ,(p.exist_unidades*contenido)+p.exist_piezas as exist_piezas"
					+ " 			 ,isnull((select (prodestab.exist_unidades*contenido)+prodestab.exist_piezas from prodestab where prodestab.cod_prod=p.cod_prod and cod_estab=7),0) as existencia_cedis"
					+ " 			 ,(p.inventario_maximo)-((p.exist_unidades*contenido)+p.exist_piezas) as nresurtido"
					+ " 		  from prodestab p with (nolock)"
					+ "		 INNER JOIN productos ON p.cod_prod = productos.cod_prod"
					+ "		where p.cod_estab=@cod_estab and p.inventario_minimo>1  and ((p.exist_unidades*contenido)+p.exist_piezas)<p.inventario_minimo )a where nresurtido>0 order by descripcion_completa";
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
					+ "  from(select p.cod_prod"
					+ " 			 ,productos.descripcion_completa"
					+ " 			 ,p.inventario_minimo"
					+ " 			 ,p.inventario_maximo"
					+ " 			 ,(p.exist_unidades*contenido)+p.exist_piezas as exist_piezas"
					+ " 			 ,isnull((select (prodestab.exist_unidades*contenido)+prodestab.exist_piezas from prodestab where prodestab.cod_prod=p.cod_prod and cod_estab=7),0) as existencia_cedis"
					+ " 			 ,(p.inventario_maximo)-((p.exist_unidades*contenido)+p.exist_piezas) as nresurtido"
					+ " 		  from prodestab p with (nolock)"
					+ "		 INNER JOIN productos ON p.cod_prod = productos.cod_prod"
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
					+ "  from(select p.cod_prod"
					+ " 			 ,productos.descripcion_completa"
					+ " 			 ,p.inventario_minimo"
					+ " 			 ,p.inventario_maximo"
					+ " 			 ,(p.exist_unidades*contenido)+p.exist_piezas as exist_piezas"
					+ " 			 ,isnull((select (prodestab.exist_unidades*contenido)+prodestab.exist_piezas from prodestab where prodestab.cod_prod=p.cod_prod and cod_estab=7),0) as existencia_cedis"
					+ " 			 ,(p.inventario_maximo)-((p.exist_unidades*contenido)+p.exist_piezas) as nresurtido"
					+ " 		  from prodestab p with (nolock)"
					+ "		 INNER JOIN productos ON p.cod_prod = productos.cod_prod"
					+ "		where p.cod_estab=@cod_estab and p.inventario_minimo>1 )a where a.exist_piezas>a.inventario_maximo order by descripcion_completa";
		}
	
		
		
		
		return comando;
		
		
	}
	

}
