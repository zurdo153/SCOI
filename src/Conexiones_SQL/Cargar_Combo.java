package Conexiones_SQL;
 
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import Obj_Administracion_del_Sistema.Obj_Usuario;

public class Cargar_Combo {
	Connexion con = new Connexion();
	@SuppressWarnings("rawtypes")
	Vector miVector = new Vector();
	Obj_Usuario usuario = new Obj_Usuario().LeerSession();
	
	@SuppressWarnings("unchecked")
	public String[] Establecimiento_Todos(String tabla) throws SQLException{
		String query = "";
		if(tabla.equals("tb_establecimiento")){
			query = "select ltrim(rtrim(nombre)) as nombre from " + tabla + " where status = 1 order by nombre asc";
		}else{
			query = "select ltrim(rtrim(nombre)) as nombre from " + tabla + " where status = 'V' order by nombre asc";
		}
		Statement stmt = null;
		try {
			if(tabla.equals("tb_establecimiento")){
				stmt = con.conexion().createStatement();
			}else{
				stmt = con.conexion_IZAGAR().createStatement();
			}
			ResultSet rs = stmt.executeQuery(query);
			int j=0;
			while(rs.next()){
				if(j == 0){
					miVector.add("Selecciona un Establecimiento");
				}
				miVector.add(rs.getString("nombre"));
				j++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(stmt!=null){stmt.close();}
		}
		int i=0;
		String[] pila= new String[miVector.size()];
		
		while(i < miVector.size()){
			pila[i]= miVector.get(i).toString();
			i++;
		}
		return pila;
	}
	
	@SuppressWarnings("unchecked")
	public String[] EstablecimientoPoliza() throws SQLException{
		String query = "select nombre from tb_establecimiento order by nombre desc";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			int j=0;
			while(rs.next()){
				if(j == 0){
					miVector.add("MULTIPLE");
					miVector.add("NO APLICA");
				}
				miVector.add(rs.getString("nombre"));
				j++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(stmt!=null){stmt.close();}
		}
		int i=0;
		String[] pila= new String[miVector.size()];
		
		while(i < miVector.size()){
			pila[i]= miVector.get(i).toString();
			i++;
		}
		return pila;
	}
	
	@SuppressWarnings("unchecked")
	public String[] Establecimiento_desc(String tabla) throws SQLException{
		String query = "select ltrim(rtrim(nombre)) as nombre from " + tabla + " where status = 'V' order by nombre desc";
		
		Statement stmt = null;
		try {
			stmt = con.conexion_IZAGAR().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			int j=0;
			while(rs.next()){
				if(j == 0){
					miVector.add("Selecciona un Establecimiento");
				}
				miVector.add(rs.getString("nombre"));
				j++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(stmt!=null){stmt.close();}
		}
		int i=0;
		String[] pila= new String[miVector.size()];
		
		while(i < miVector.size()){
			pila[i]= miVector.get(i).toString();
			i++;
		}
		return pila;
			
	}
	
	@SuppressWarnings("unchecked")
	public String[] Establecimientos(String parametro) throws SQLException{
		String query = "exec sp_select_establecimientos '"+parametro+"'";
		
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			int j=0;
			while(rs.next()){
				if(j == 0){
					miVector.add("Selecciona un Establecimiento");
				}
				miVector.add(rs.getString(1));
				j++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(stmt!=null){stmt.close();}
		}
		int i=0;
		String[] pila= new String[miVector.size()];
		
		while(i < miVector.size()){
			pila[i]= miVector.get(i).toString();
			i++;
		}
		return pila;
	}
	
	@SuppressWarnings("unchecked")
	public String[] Establecimiento_Caja() throws SQLException{
		String query = "select " + 
					           "distinct tb_establecimiento.nombre as establecimiento " +
					   "from tb_empleado " +
					   "right join tb_establecimiento on tb_establecimiento.folio = tb_empleado.establecimiento_id " +
					   "right join tb_puesto on tb_puesto.folio = tb_empleado.puesto_id " +
					   "where tb_empleado.puesto_id=32 order by tb_establecimiento.nombre asc";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			int j=0;
			while(rs.next()){
				if(j == 0){
					miVector.add("Todos");
				}
				miVector.add(rs.getString("establecimiento"));
				j++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(stmt!=null){stmt.close();}
		}
		int i=0;
		String[] pila= new String[miVector.size()];
		
		while(i < miVector.size()){
			pila[i]= miVector.get(i).toString();
			i++;
		}
		return pila;
	}

	@SuppressWarnings("unchecked")
	public String[] Establecimiento_para_revision_de_cortes() throws SQLException{
		String query = "select convert(varchar(10),a.folio_establecimiento)+'.- '+a.establecimiento as establecimiento "
				+ "			 from (select distinct tb_establecimiento.folio as folio_establecimiento"
				+ "                               ,tb_establecimiento.nombre as establecimiento"
				+ "			              from  tb_establecimiento "
				+ "                   where folio_grupo_para_cortes<>0 and status=1)a"
				+ "     order by a.folio_establecimiento,a.establecimiento";
		
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			int j=0;
			while(rs.next()){
				if(j == 0){
					miVector.add("Todos");
				}
				miVector.add(rs.getString("establecimiento"));
				j++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(stmt!=null){stmt.close();}
		}
		int i=0;
		String[] pila= new String[miVector.size()];
		
		while(i < miVector.size()){
			pila[i]= miVector.get(i).toString();
			i++;
		}
		return pila;
	}

	@SuppressWarnings("unchecked")
	public String[] Ponderacion(String tabla) throws SQLException{
		String query = "select descripcion from " + tabla + " order by descripcion asc";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			int j=0;
			while(rs.next()){
				if(j == 0){
					miVector.add("Seleccione Pondercion");
				}
				miVector.add(rs.getString("descripcion"));
				j++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(stmt!=null){stmt.close();}
		}
		
		int i=0;
		String[] pila= new String[miVector.size()];
		
		while(i < miVector.size()){
			pila[i]= miVector.get(i).toString();
			i++;
		}
		return pila;
	}
	
	@SuppressWarnings("unchecked")
	public String[] Puesto(String tabla) throws SQLException{
		String query = "select nombre from " + tabla+" where status=1 order by nombre asc";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			int j=0;
			while(rs.next()){
				if(j == 0){
					miVector.add("Selecciona un Puesto");
				}
				miVector.add(rs.getString("nombre").toUpperCase());
				j++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(stmt!=null){stmt.close();}
		}
		
		int i=0;
		String[] pila= new String[miVector.size()];
		
		while(i < miVector.size()){
			
			pila[i]= miVector.get(i).toString();
			i++;
		}
		return pila;
	}
	
	@SuppressWarnings("unchecked")
	public String[] jefatu(String tabla) throws SQLException{
		String query = "select descripcion from " + tabla+" order by descripcion asc";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			int j=0;
			while(rs.next()){
				if(j == 0){
					miVector.add("Selecciona una Jefatura");
				}
				miVector.add(rs.getString("descripcion").toUpperCase());
				j++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(stmt!=null){stmt.close();}
		}
		
		int i=0;
		String[] pila= new String[miVector.size()];
		
		while(i < miVector.size()){
			
			pila[i]= miVector.get(i).toString();
			i++;
		}
		return pila;
	}
	
	@SuppressWarnings("unchecked")
	public String[] Divisas(String tabla) throws SQLException{
		String query = "select nombre_divisas from " + tabla+" order by nombre_divisas asc";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			int j=0;
			while(rs.next()){
				if(j == 0){
					miVector.add("Selecciona un valor");
				}
				miVector.add(rs.getString("nombre_divisas").toUpperCase());
				j++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(stmt!=null){stmt.close();}
		}
		int i=0;
		String[] pila= new String[miVector.size()];
		
		while(i < miVector.size()){
			
			pila[i]= miVector.get(i).toString();
			i++;
		}
		return pila;
	}
	
	@SuppressWarnings("unchecked")
	public String[] Denominaciones(String tabla) throws SQLException{
		String query = "select nombre_divisas from " + tabla+" order by nombre_divisas asc";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			int j=0;
			while(rs.next()){
				if(j == 0){
					miVector.add("Selecciona un valor");
				}
				miVector.add(rs.getString("nombre_divisas").toUpperCase());
				j++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(stmt!=null){stmt.close();}
		}
		
		int i=0;
		String[] pila= new String[miVector.size()];
		
		while(i < miVector.size()){
			
			pila[i]= miVector.get(i).toString();
			i++;
		}
		return pila;
	}
	
//	@SuppressWarnings("unchecked")
//	public String[] Bono_Puntualidad_y_Asistencia(String tabla) throws SQLException {
//		String query = "select round(bono,2) as bono from " + tabla +" order by bono asc";
//		Statement stmt = null;
//		try {
//			stmt = con.conexion().createStatement();
//			ResultSet rs = stmt.executeQuery(query);
//			int j=0;
//			while(rs.next()){
//				if(j == 0){
//					miVector.add("Selecciona un Bono");
//				}
//				miVector.add(rs.getString("bono"));
//				j++;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}finally{
//			if(stmt!=null){stmt.close();}
//		}
//		
//		int i=0;
//		String[] pila= new String[miVector.size()];
//		
//		while(i < miVector.size()){
//			
//			pila[i]= miVector.get(i).toString();
//			i++;
//		}
//		return pila;	
//	}
	
	
	@SuppressWarnings("unchecked")
	public String[] Permiso(String tabla) throws SQLException{
		String query = "select nombre from " + tabla;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()){
				miVector.add(rs.getString("nombre"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(stmt!=null){	stmt.close(); }
		}
		
		int i=0;
		String[] pila= new String[miVector.size()];
		
		while(i < miVector.size()){
			
			pila[i]= miVector.get(i).toString();
			i++;
		}
		return pila;	
	}
	
	@SuppressWarnings("unchecked")
	public String[] GruposDeCortes() throws SQLException {
		String query = "select grupo_para_cortes from tb_grupos_para_cortes";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			int j=0;
			while(rs.next()){
				if(j == 0){
					miVector.add("SELECCIONE UNA OPCION");
				}
				miVector.add(rs.getString("grupo_para_cortes"));
				j++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(stmt!=null){stmt.close();}
		}
		int i=0;
		String[] pila= new String[miVector.size()];
		
		while(i < miVector.size()){
			
			pila[i]= miVector.get(i).toString();
			i++;
		}
		return pila;
	}
	
	@SuppressWarnings("unchecked")
	public String[] EstablecimientoConcentrado() throws SQLException {
		String query = "select grupo_para_cortes from tb_grupos_para_cortes";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			int j=0;
			while(rs.next()){
				if(j == 0){
					miVector.add("SELECCIONE UN CONCENTRADO");
				}
				miVector.add(rs.getString("grupo_para_cortes"));
				j++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(stmt!=null){stmt.close();}
		}
		
		int i=0;
		String[] pila= new String[miVector.size()];
		
		while(i < miVector.size()){
			
			pila[i]= miVector.get(i).toString();
			i++;
		}
		return pila;
	}
	
	@SuppressWarnings("unchecked")
	public String[] Tipo_Banco(String tabla) throws SQLException{
		String query = "select nombre from " + tabla;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			int j=0;
			while(rs.next()){
				if(j == 0){
					miVector.add("Selecciona un Banco");
				}
				miVector.add(rs.getString("nombre"));
				j++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(stmt!=null){stmt.close();}
		}
		
		int i=0;
		String[] pila= new String[miVector.size()];
		
		while(i < miVector.size()){
			
			pila[i]= miVector.get(i).toString();
			i++;
		}
		return pila;
	}
	
	@SuppressWarnings("unchecked")
	public String[] Tipo_Banco_Empleado(String tabla) throws SQLException{
		String query = "select nombre from " + tabla+ " where status=1 and status_empleado=1";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			int j=0;
			while(rs.next()){
				if(j == 0){
					miVector.add("Selecciona un Banco");
				}
				miVector.add(rs.getString("nombre"));
				j++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(stmt!=null){stmt.close();}
		}
		
		int i=0;
		String[] pila= new String[miVector.size()];
		
		while(i < miVector.size()){
			
			pila[i]= miVector.get(i).toString();
			i++;
		}
		return pila;
	}
	
	@SuppressWarnings("unchecked")
	public String[] Respuesta(String tabla) throws SQLException{
		String query = "select nombre as nombre from " + tabla + " order by nombre asc";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			int j=0;
			while(rs.next()){
				if(j == 0){
					miVector.add("Selecciona una Opci�n");
				}
				miVector.add(rs.getString("nombre"));
				j++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(stmt!=null){stmt.close();}
		}
		
		int i=0;
		String[] pila= new String[miVector.size()];
		
		while(i < miVector.size()){
			pila[i]= miVector.get(i).toString();
			i++;
		}
		return pila;
			
	}
	
	@SuppressWarnings("unchecked")
	public String[] Combos(String tabla) throws SQLException{
		String query = "exec combos "+ tabla ;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				miVector.add(rs.getString(1).trim());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(stmt!=null){stmt.close();}
		}
		
		int i=0;
		String[] pila= new String[miVector.size()];
		
		while(i < miVector.size()){
			pila[i]= miVector.get(i).toString();
			i++;
		}
		return pila;
	}
	
	@SuppressWarnings("unchecked")
	public String[] Combos_Tiempo(int anio, String parametro, String parametro2) throws SQLException{
		String query = "exec tiempo_por_parametros "+anio+",'"+parametro+"','"+parametro2+"'" ;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				miVector.add(rs.getString(1).trim());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(stmt!=null){stmt.close();}
		}
		
		int i=0;
		String[] pila= new String[miVector.size()];
		
		while(i < miVector.size()){
			pila[i]= miVector.get(i).toString();
			i++;
		}
		return pila;
	}
	
	
	@SuppressWarnings("unchecked")
	public String[] Establecimiento_Empleado_Entysal(String tabla) throws SQLException{
		String query = "select nombre from " + tabla + " order by nombre asc";
		Statement stmt = null;
		try {
			stmt = new Connexion().conexion_IZAGAR().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			int j=0;
			while(rs.next()){
				if(j == 0){
					miVector.add("Selecciona un Establecimiento");
				}
				miVector.add(rs.getString("nombre"));
				j++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(stmt!=null){stmt.close();}
		}
		
		int i=0;
		String[] pila= new String[miVector.size()];
		
		while(i < miVector.size()){
			pila[i]= miVector.get(i).toString();
			i++;
		}
		return pila;
			
	}
	
	@SuppressWarnings("unchecked")
	public String[] niveljerarquico(String tabla) throws SQLException{
		String query = "select descripcion from " + tabla + " order by descripcion asc";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			int j=0;
			while(rs.next()){
				if(j == 0){
					miVector.add("Selecciona un Nivel Jerarquico");
				}
				miVector.add(rs.getString("descripcion"));
				j++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(stmt!=null){stmt.close();}
		}
		int i=0;
		String[] pila= new String[miVector.size()];
		
		while(i < miVector.size()){
			pila[i]= miVector.get(i).toString();
			i++;
		}
		return pila;
			
	}
	
			@SuppressWarnings("unchecked")
			public String[] Equipo(String tabla) throws SQLException{
				String query = "select descripcion from " + tabla+" order by descripcion asc";
				Statement stmt = null;
				try {
					stmt = con.conexion().createStatement();
					ResultSet rs = stmt.executeQuery(query);
					int j=0;
					while(rs.next()){
						if(j == 0){
							miVector.add("Selecciona un Equipo");
							miVector.add("");
						}
						miVector.add(rs.getString("descripcion").toUpperCase());
						j++;
					}
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}finally{
					if(stmt!=null){stmt.close();}
				}
				
				int i=0;
				String[] pila= new String[miVector.size()];
				
				while(i < miVector.size()){
					
					pila[i]= miVector.get(i).toString();
					i++;
				}
				
				return pila;
					
			}
			
			//cargamos el combo Empleado
			@SuppressWarnings("unchecked")
			public String[] Empleado(String tabla) throws SQLException{
				String query ="select nombre+' '+ap_paterno+' '+ap_materno as nombre from " +tabla+ " order by nombre asc";
				
				Statement stmt = null;
				try {
					stmt = con.conexion().createStatement();
					ResultSet rs = stmt.executeQuery(query);
					int j=0;
					while(rs.next()){
						if(j == 0){
							miVector.add("Selecciona un Empleado");
							miVector.add("");
						}
						miVector.add(rs.getString("nombre").toUpperCase());
						j++;
					}
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}finally{
					if(stmt!=null){stmt.close();}
				}
				
				int i=0;
				String[] pila= new String[miVector.size()];
				
				while(i < miVector.size()){
					
					pila[i]= miVector.get(i).toString();
					i++;
				}
				
				return pila;
					
			}
			
	@SuppressWarnings("unchecked")
	public String[] Grupo_De_Vacaciones(String tabla) throws SQLException{
		String query = "select descripcion from " + tabla + " where status = 1 order by descripcion asc";
		
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			int j=0;
			while(rs.next()){
				if(j == 0){
					miVector.add("Selecciona un grupo");
				}
				miVector.add(rs.getString("descripcion"));
				j++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(stmt!=null){stmt.close();}
		}
		int i=0;
		String[] pila= new String[miVector.size()];
		
		while(i < miVector.size()){
			pila[i]= miVector.get(i).toString();
			i++;
		}
		return pila;
	}
	
	@SuppressWarnings("unchecked")
	public String[] Conseptos() throws SQLException {
		String query = "select concepto_extra from tb_conceptos_de_extra_de_lista_de_raya";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			int j=0;
			while(rs.next()){
				if(j == 0){
					miVector.add("");
				}
				miVector.add(rs.getString("concepto_extra"));
				j++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(stmt!=null){stmt.close();}
		}
		
		int i=0;
		String[] pila= new String[miVector.size()];
		
		while(i < miVector.size()){
			
			pila[i]= miVector.get(i).toString();
			i++;
		}
		return pila;
	}
	
	@SuppressWarnings("unchecked")
	public String[] Tipo_De_Sangre(String tabla) throws SQLException{
		String query = "select tipo from " + tabla+ " where status=1";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			int j=0;
			while(rs.next()){
				if(j == 0){
					miVector.add("Selecciona un Tipo");
				}
				miVector.add(rs.getString("tipo"));
				j++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(stmt!=null){stmt.close();}
		}
		
		int i=0;
		String[] pila= new String[miVector.size()];
		
		while(i < miVector.size()){
			
			pila[i]= miVector.get(i).toString();
			i++;
		}
		return pila;
			
	}
	
	@SuppressWarnings("unchecked")
	public String[] Estado_Civil(String tabla) throws SQLException{
		String query = "select estado from " + tabla+ " where status=1";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			int j=0;
			while(rs.next()){
				if(j == 0){
					miVector.add("Selecciona un Estado");
				}
				miVector.add(rs.getString("estado"));
				j++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(stmt!=null){stmt.close();}
		}
		
		int i=0;
		String[] pila= new String[miVector.size()];
		
		while(i < miVector.size()){
			
			pila[i]= miVector.get(i).toString();
			i++;
		}
		return pila;
			
	}
	
	@SuppressWarnings("unchecked")
	public String[] Parentesco() throws SQLException{
		String query = "select parentesco from  tb_parentesco order by folio_parentesco";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			@SuppressWarnings("unused")
			int j=0;
			while(rs.next()){
				miVector.add(rs.getString("parentesco"));
				j++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(stmt!=null){stmt.close();}
		}
		
		int i=0;
		String[] pila= new String[miVector.size()];
		
		while(i < miVector.size()){
			
			pila[i]= miVector.get(i).toString();
			i++;
		}
		return pila;
	}
	
	@SuppressWarnings("unchecked")
	public String[] Escolaridad(String tabla) throws SQLException{
		String query = "select escolaridad from " + tabla+ " where status=1";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			int j=0;
			while(rs.next()){
				if(j == 0){
					miVector.add("Selecciona un Nivel");
				}
				miVector.add(rs.getString("escolaridad"));
				j++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(stmt!=null){stmt.close();}
		}
		
		int i=0;
		String[] pila= new String[miVector.size()];
		
		while(i < miVector.size()){
			
			pila[i]= miVector.get(i).toString();
			i++;
		}
		return pila;
			
	}
	
	@SuppressWarnings("unchecked")
	public String[] Personal_Por_Departametno(int folio_departamento) throws SQLException{
		String query =" select nombre+' '+ap_paterno+' '+ap_materno as Empleado"
					+ "	from tb_empleado where status = 1 "
					+ " and departamento = "+folio_departamento;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			int j=0;
			while(rs.next()){
				if(j == 0){
					miVector.add("Selecciona un Empleado");
				}
				miVector.add(rs.getString("Empleado"));
				j++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(stmt!=null){stmt.close();}
		}
		
		int i=0;
		String[] pila= new String[miVector.size()];
		
		while(i < miVector.size()){
			
			pila[i]= miVector.get(i).toString();
			i++;
		}
		return pila;
	}
	
	@SuppressWarnings("unchecked")
	public String[] Datos_Combo_CuentasBancarias() throws SQLException{
		String query = "select cuenta_bancaria from  cuentas_bancarias where status='V'" ;
		
		Statement stmt = null;
		try {
			stmt = con.conexion_IZAGAR().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			int j=0;
			while(rs.next()){
				if(j == 0){
				}
				miVector.add(rs.getString("cuenta_bancaria"));
				j++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(stmt!=null){stmt.close();}
		}
		int i=0;
		String[] pila= new String[miVector.size()];
		
		while(i < miVector.size()){
			pila[i]= miVector.get(i).toString();
			i++;
		}
		return pila;
			
	}
	
	@SuppressWarnings("unchecked")
	public String[] tipos_de_polizas() throws SQLException{
		String query = "select ltrim(rtrim(nombre)) as nombre from tb_configuracion_de_polizas where estatus = 'V' order by nombre asc";
		
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				miVector.add(rs.getString("nombre"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(stmt!=null){stmt.close();}
		}
		int i=0;
		String[] pila= new String[miVector.size()];
		
		while(i < miVector.size()){
			pila[i]= miVector.get(i).toString();
			i++;
		}
		return pila;
			
	}
		
	@SuppressWarnings("unchecked")
	public String[] Datos_Combo_Clasificacion_Contable() throws SQLException{
		String query = " select rtrim(ltrim(clasificacion_contable)) as clasificacion_contable from tb_clasificaciones_contables where status='V' " ;
		
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			int j=0;
			while(rs.next()){
				if(j == 0){
					miVector.add("No Aplica");
				}
				miVector.add(rs.getString("clasificacion_contable"));
				j++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(stmt!=null){stmt.close();}
		}
		int i=0;
		String[] pila= new String[miVector.size()];
		
		while(i < miVector.size()){
			pila[i]= miVector.get(i).toString();
			i++;
		}
		return pila;
	}
	
	@SuppressWarnings("unchecked")
	public String[] cuentas() throws SQLException{
		String query = "select ltrim(rtrim(transaccion)) as transaccion from tb_folios where status=1 and aplicacion='polizas' order by transaccion";
		
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			int j=0;
			while(rs.next()){
				if(j == 0){
					miVector.add("No Aplica");
				}
				miVector.add(rs.getString("transaccion"));
				j++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(stmt!=null){stmt.close();}
		}
		int i=0;
		String[] pila= new String[miVector.size()];
		
		while(i < miVector.size()){
			pila[i]= miVector.get(i).toString();
			i++;
		}
		return pila;
			
	}
	
	@SuppressWarnings("unchecked")
	public String[] EstablecimientoTb() throws SQLException{
		String query = "select nombre from tb_establecimiento order by nombre desc";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			int j=0;
			while(rs.next()){
				if(j == 0){
					miVector.add("");
				}
				miVector.add(rs.getString("nombre"));
				j++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(stmt!=null){stmt.close();}
		}
		int i=0;
		String[] pila= new String[miVector.size()];
		
		while(i < miVector.size()){
			pila[i]= miVector.get(i).toString();
			i++;
		}
		return pila;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public String[] tipos_de_banco_polizas() throws SQLException{
		String query = "SELECT cuenta as nombre FROM tb_bancos_contabilidad WHERE (estatus = 'V') order by nombre asc";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				miVector.add(rs.getString("nombre"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(stmt!=null){stmt.close();}
		}
		int i=0;
		String[] pila= new String[miVector.size()];
		
		while(i < miVector.size()){
			pila[i]= miVector.get(i).toString();
			i++;
		}
		return pila;
	}
	
	@SuppressWarnings("unchecked")
	public String[] conceptos_de_ordenes_de_pago() throws SQLException{
		String query = "SELECT concepto_orden_de_pago FROM tb_conceptos_de_orden_de_pago WHERE (status <> 'C') order by concepto_orden_de_pago asc";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			int j=0;
			while(rs.next()){
				if(j == 0){
					miVector.add("");
				}
				miVector.add(rs.getString("concepto_orden_de_pago"));
				j++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(stmt!=null){stmt.close();}
		}
		int i=0;
		String[] pila= new String[miVector.size()];
		
		while(i < miVector.size()){
			pila[i]= miVector.get(i).toString();
			i++;
		}
		return pila;
	}
	
	@SuppressWarnings("unchecked")
	public String[] autorizados_para_pago_efectivo() throws SQLException{
		String query = "select nombre_completo as nombre from tb_autorizan_orden_de_pago where status = 'V' order by orden";
		
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()){
				miVector.add(rs.getString("nombre"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(stmt!=null){stmt.close();}
		}
		int i=0;
		String[] pila= new String[miVector.size()];
		
		while(i < miVector.size()){
			pila[i]= miVector.get(i).toString();
			i++;
		}
		return pila;
			
	}
	
	@SuppressWarnings("unchecked")
	public String[] entregoMonedas() throws SQLException{
		
		String query = " declare @sqlquery nvarchar(max) "
					+ " set @sqlquery = ('select nombre+'' ''+ap_paterno+'' ''+ap_materno as empleado from tb_empleado where puesto_id in ('+(select puestos_supervisores_de_retiros_a_cajeros "
					+ "																															from tb_configuracion_sistema)+') and status in (1,6) "
					+ " order by nombre+'' ''+ap_paterno+'' ''+ap_materno asc ') "
					+ " execute sp_executesql @sqlquery ";

		
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			int j=0;
			while(rs.next()){
				if(j == 0){
					miVector.add("");
				}
				miVector.add(rs.getString("empleado"));
				j++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(stmt!=null){stmt.close();}
		}
		int i=0;
		String[] pila= new String[miVector.size()];
		
		while(i < miVector.size()){
			pila[i]= miVector.get(i).toString();
			i++;
		}
		return pila;
			
	}	
	
	@SuppressWarnings("unchecked")
	public String[] unidadDeMedida() throws SQLException {
		String query = "SELECT descripcion as unidadDeMedida from tb_unidad_de_medida_de_productos where status = 'V'";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			int j=0;
			while(rs.next()){
				if(j == 0){
					miVector.add("");
				}
				miVector.add(rs.getString("unidadDeMedida"));
				j++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(stmt!=null){stmt.close();}
		}
		
		int i=0;
		String[] pila= new String[miVector.size()];
		
		while(i < miVector.size()){
			pila[i]= miVector.get(i).toString();
			i++;
		}
		return pila;
	}
	
	@SuppressWarnings("unchecked")
	public String[] uso() throws SQLException {
		String query = "SELECT descripcion as uso from tb_uso_de_productos where status = 'V'";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			int j=0;
			while(rs.next()){
				if(j == 0){
					miVector.add("");
				}
				miVector.add(rs.getString("uso"));
				j++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(stmt!=null){stmt.close();}
		}
		
		int i=0;
		String[] pila= new String[miVector.size()];
		
		while(i < miVector.size()){
			pila[i]= miVector.get(i).toString();
			i++;
		}
		return pila;
	}
	
	@SuppressWarnings("unchecked")
	public String[] tipos_de_concentrados(){
		String query = "select grupo_para_cortes from tb_grupos_para_cortes where status = 1";
		
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()){
				miVector.add(rs.getString("grupo_para_cortes"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(stmt!=null){try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}}
		}
		int i=0;
		String[] pila= new String[miVector.size()];
		
		while(i < miVector.size()){
			pila[i]= miVector.get(i).toString();
			i++;
		}
		return pila;
			
	}
	
	
	
	@SuppressWarnings("unchecked")
	public String[] ComboTiposDeRespuesta_Plan_Semanal(int actividad, int dia) throws SQLException{
		String query = "select * from dbo.tipos_de_respuesta_plan_semanal("+actividad+","+dia+","+usuario.getFolio()+")";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while(rs.next()){
				miVector.add(rs.getString("item").trim());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(stmt!=null){stmt.close();}
		}
		int i=0;
		String[] pila= new String[miVector.size()];
		
		while(i < miVector.size()){
			pila[i]= miVector.get(i).toString();
			i++;
		}
		return pila;
			
	}
	
	@SuppressWarnings("unchecked")
	public String[] Combo_meta_anio() throws SQLException{
		String query = "declare @table table(a�o int) declare @contador int=2015"
				+ "    set nocount on"
				+ "      while @contador<2090"
				+ "       BEGIN"
				+ "         set @contador=@contador+1"
				+ "         INSERT INTO @table"
				+ "         select @contador"
				+ "       END"
				+ "     set nocount off"
				+ " select * from @table order by a�o";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while(rs.next()){
				miVector.add(rs.getString(1).trim());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(stmt!=null){stmt.close();}
		}
		int i=0;
		String[] pila= new String[miVector.size()];
		
		while(i < miVector.size()){
			pila[i]= miVector.get(i).toString();
			i++;
		}
		return pila;
			
	}
	
	@SuppressWarnings("unchecked")
	public String[] Combo_Respuesta_Clasificacion() throws SQLException{
		String query = "select descripcion from tb_clasificacion_del_servicio";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while(rs.next()){
				miVector.add(rs.getString(1).trim());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(stmt!=null){stmt.close();}
		}
		int i=0;
		String[] pila= new String[miVector.size()];
		
		while(i < miVector.size()){
			pila[i]= miVector.get(i).toString();
			i++;
		}
		return pila;
			
	}
	
	@SuppressWarnings("unchecked")
	public String[] Nivel_De_Puesto(String tabla) throws SQLException{
		String query = "select nivel_de_puesto as nivel_de_puesto from " + tabla+" where status='V' order by nivel_de_puesto asc";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			int j=0;
			while(rs.next()){
				if(j == 0){
					
					miVector.add("Selecciona un Nivel De Puesto");
//					miVector.add("");
				}
				miVector.add(rs.getString("nivel_de_puesto").toUpperCase());
				j++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(stmt!=null){stmt.close();}
		}
		
		int i=0;
		String[] pila= new String[miVector.size()];
		
		while(i < miVector.size()){
			
			pila[i]= miVector.get(i).toString();
			i++;
		}
		
		return pila;
			
	}
	
	@SuppressWarnings("unchecked")
	public String[] clasificador_de_pedidos_de_establecimientos(){
		String query = "SELECT descripcion "
				+ " FROM tb_clasificacion_de_pedidos_de_establecimientos "
				+ " WHERE status = 'V' ORDER BY descripcion";
		
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			int j=0;
			while(rs.next()){
				if(j == 0){
					miVector.add("Selecciona un clasificador");
				}
				miVector.add(rs.getString("descripcion"));
				j++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(stmt!=null){try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}}
		}
		int i=0;
		String[] pila= new String[miVector.size()];
		
		while(i < miVector.size()){
			pila[i]= miVector.get(i).toString();
			i++;
		}
		return pila;
			
	}
	
	
	@SuppressWarnings("unchecked")
	public String[] Prioridad() throws SQLException{
		String query = "select nombre from tb_prioridad  where estatus = 'V'";
		
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			int j=0;
			while(rs.next()){
				if(j == 0){
					miVector.add("SELECCIONA UNA PRIORIDAD");
				}
				miVector.add(rs.getString("nombre"));
				j++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(stmt!=null){stmt.close();}
		}
		int i=0;
		String[] pila= new String[miVector.size()];
		
		while(i < miVector.size()){
			pila[i]= miVector.get(i).toString();
			i++;
		}
		return pila;
			
	}
	
	@SuppressWarnings("unchecked")
	public String[] Unidada_De_Vida_Util_Y_Garantia_De_Equipo(){
		String query = "select nombre from tb_unidad_vida_util_y_garantia  where status = 'V'";
		
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			int j=0;
			while(rs.next()){
				if(j == 0){
					miVector.add("Selecciona Unidad");
				}
				miVector.add(rs.getString("nombre"));
				j++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(stmt!=null){try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}}
		}
		int i=0;
		String[] pila= new String[miVector.size()];
		
		while(i < miVector.size()){
			pila[i]= miVector.get(i).toString();
			i++;
		}
		return pila;
	}
	
	@SuppressWarnings("unchecked")
	public String[] tipo_de_equipo(){
		String query = "select ltrim(rtrim(nombre)) as equipo from tb_tipos_de_equipo where status = 'V' order by nombre asc";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			int j=0;
			while(rs.next()){
				if(j == 0){
					miVector.add("Selecciona un Equipo");
				}
				miVector.add(rs.getString("equipo"));
				j++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(stmt!=null){try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}}
		}
		int i=0;
		String[] pila= new String[miVector.size()];
		
		while(i < miVector.size()){
			pila[i]= miVector.get(i).toString();
			i++;
		}
		return pila;
	}
	
	@SuppressWarnings("unchecked")
	public String[] Establecimiento_valida_permiso_para_captura_merma() throws SQLException{
		
		int folio_usuario = new Obj_Usuario().LeerSession().getFolio();
		System.out.println(folio_usuario);
		String query = "exec sp_consulta_establecimiento_de_merma_por_usuario "+folio_usuario+"";
		
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			System.out.println(rs.getRow());
			
			int j=0;
			while(rs.next()){
				if(j == 0){
					miVector.add("Selecciona un Establecimiento");
				}
				System.out.println(rs.getString("nombre"));
				miVector.add(rs.getString("nombre"));
				j++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(stmt!=null){stmt.close();}
		}
		int i=0;
		String[] pila= new String[miVector.size()];
		
		while(i < miVector.size()){
			pila[i]= miVector.get(i).toString();
			i++;
		}
		return pila;
	}
	
	@SuppressWarnings("unchecked")
	public String[] Areas_tipo_distribucion(){
		String query = "select LTRIM(RTRIM(nombre)) as nombre from Areas where status = 'V' order by nombre";
		
		Statement stmt = null;
		try {
			stmt = con.conexion_IZAGAR().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			int j=0;
			while(rs.next()){
				if(j == 0){
					miVector.add("Selecciona un Area");
				}
				miVector.add(rs.getString("nombre"));
				j++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(stmt!=null){try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}}
		}
		int i=0;
		String[] pila= new String[miVector.size()];
		
		while(i < miVector.size()){
			pila[i]= miVector.get(i).toString();
			i++;
		}
		return pila;
			
	}
	
	@SuppressWarnings("unchecked")
	public String[] razonesDeMovimientoDeInventario(String movimiento){
		
		@SuppressWarnings("unused")
		int folio_usuario = new Obj_Usuario().LeerSession().getFolio();
		String query = "SELECT nombre from razones_de_movimiento_de_inventario where status in ('T','"+movimiento+"')";
		
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			System.out.println(rs.getRow());
			
			int j=0;
			while(rs.next()){
				if(j == 0){
					miVector.add("Selecciona una Razon");
				}
				miVector.add(rs.getString("nombre"));
				j++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(stmt!=null){try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}}
		}
		int i=0;
		String[] pila= new String[miVector.size()];
		
		while(i < miVector.size()){
			pila[i]= miVector.get(i).toString();
			i++;
		}
		return pila;
	}
	
	@SuppressWarnings("unchecked")
	public String[] Combo_Clasificaciones(){
		String query = "select nombre from dbo.clasificaciones_de_cuestionarion where status='V'";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while(rs.next()){
				miVector.add(rs.getString(1).trim());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(stmt!=null){try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}}
		}
		int i=0;
		String[] pila= new String[miVector.size()];
		
		while(i < miVector.size()){
			pila[i]= miVector.get(i).toString();
			i++;
		}
		return pila;
			
	}
	
	@SuppressWarnings("unchecked")
	public String[] Combo_Escalas(){
		String query = "select nombre from dbo.escalas where status='V'";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while(rs.next()){
				miVector.add(rs.getString(1).trim());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(stmt!=null){try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}}
		}
		int i=0;
		String[] pila= new String[miVector.size()];
		
		while(i < miVector.size()){
			pila[i]= miVector.get(i).toString();
			i++;
		}
		return pila;
			
	}
	
	@SuppressWarnings("unchecked")
	public String[] UnidadDeNegocio(String tabla) throws SQLException{
		String query = "select ltrim(rtrim(nombre)) as nombre from " + tabla + " where status = 'V' order by nombre asc";
		Statement stmt = null;
		try {
				stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			int j=0;
			while(rs.next()){
				if(j == 0){
					miVector.add("Selecciona una Unidad De Negocio");
				}
				miVector.add(rs.getString("nombre"));
				j++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(stmt!=null){stmt.close();}
		}
		int i=0;
		String[] pila= new String[miVector.size()];
		
		while(i < miVector.size()){
			pila[i]= miVector.get(i).toString();
			i++;
		}
		return pila;
	}
	
	@SuppressWarnings("unchecked")
	public Object[] movimientoEnCuentas() throws SQLException{
		String query = "select numero_de_cuenta from movimientos_en_cuentas_saldos where observaciones = 'SALDO INICIAL' order by numero_de_cuenta";
		Statement stmt = null;
		try {
				stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			int j=0;
			while(rs.next()){
				if(j == 0){
					miVector.add("Selecciona una Cuenta");
				}
				miVector.add(rs.getString("numero_de_cuenta"));
				j++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(stmt!=null){stmt.close();}
		}
		int i=0;
		String[] pila= new String[miVector.size()];
		
		while(i < miVector.size()){
			pila[i]= miVector.get(i).toString();
			i++;
		}
		return pila;
	}
	
}
