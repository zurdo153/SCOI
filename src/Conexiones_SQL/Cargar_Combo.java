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
	public String[] menus() throws SQLException{
		String query = "select nombre from tb_menus";
		
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			int j=0;
			while(rs.next()){
				if(j == 0){
					miVector.add("Seleciona un Menu");
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
	public String[] Establecimiento(String tabla) throws SQLException{
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
					miVector.add("Todos Los Establecimientos");
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
	public String[] Responsables(String tabla) throws SQLException{
		
		String 	query = "select ltrim(rtrim(nombre)) as nombre from " + tabla + " where status = 1 order by nombre asc";
		Statement stmt = null;
		try {
				stmt = con.conexion().createStatement();
			
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
	public String[] Establecimientos_Edo_Resultados() throws SQLException{
		String query = "exec sp_select_establecimientos 'R'";
		
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
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
	public String[] Departamento(String tabla) throws SQLException{
		String query = "select ltrim(rtrim(departamento)) as departamento from " + tabla + " where status = 1 order by departamento asc";
		
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			int j=0;
			while(rs.next()){
				if(j == 0){
					miVector.add("Selecciona un Departamento");
				}
				miVector.add(rs.getString("departamento"));
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
		String query = "select convert(varchar(10),a.folio_establecimiento)+'.- '+a.establecimiento as establecimiento"
		+ "					 from (select distinct tb_establecimiento.folio as folio_establecimiento,tb_establecimiento.nombre as establecimiento "
		+ "							   from tb_empleado "
		+ "							   right join tb_establecimiento on tb_establecimiento.folio = tb_empleado.establecimiento_id "
		+ "							   right join tb_puesto on tb_puesto.folio = tb_empleado.puesto_id "
		+ "							   where tb_empleado.puesto_id=32) a "
		+ "				order by a.folio_establecimiento,a.establecimiento";
		
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
	public String[] Establecimiento_Empleado(String tabla) throws SQLException{
		String query = "select nombre from " + tabla + " order by nombre desc";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
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
	public String[] opRespuesta(String tabla) throws SQLException{
		String query = "select nombre from " + tabla + " order by nombre asc";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			int j=0;
			while(rs.next()){
				if(j == 0){
					miVector.add("Seleccione una opción de Respuesta");
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
	public String[] Jefatura(String tabla) throws SQLException{
		String query = "select descripcion from " + tabla + " order by descripcion asc";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			int j=0;
			while(rs.next()){
				if(j == 0){
					miVector.add("Selecciona una Jefatura");
					miVector.add("");
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
	public String[] EquipoTrabajo(String tabla) throws SQLException{
		String query = "select descripcion from " + tabla + " order by descripcion asc";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			int j=0;
			while(rs.next()){
				if(j == 0){
					miVector.add("Selecciona un Equipo de Trabajo");
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
//					miVector.add("");
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
	
	@SuppressWarnings("unchecked")
	public String[] Sueldo(String tabla) throws SQLException{
		String query = "select sueldo from " + tabla+" order by sueldo asc";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			int j=0;
			while(rs.next()){
				if(j == 0){
					miVector.add("Selecciona un Sueldo");
				}
				miVector.add(rs.getString("sueldo"));
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
	public String[] Bono(String tabla) throws SQLException {
		String query = "select bono from " + tabla +" order by bono asc";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			int j=0;
			while(rs.next()){
				if(j == 0){
					miVector.add("Selecciona un Bono");
				}
				miVector.add(rs.getString("bono"));
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
	public String[] Bono_Puntualidad_y_Asistencia(String tabla) throws SQLException {
		String query = "select round(bono,2) as bono from " + tabla +" order by bono asc";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			int j=0;
			while(rs.next()){
				if(j == 0){
					miVector.add("Selecciona un Bono");
				}
				miVector.add(rs.getString("bono"));
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
	public String[] Rango_Prestamos(String tabla) throws SQLException {
		String query = "select minimo,maximo from " + tabla;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			int j=0;
			while(rs.next()){
				if(j == 0){
					miVector.add("Selecciona un Rango de Prestamo");
				}
				miVector.add(Math.rint(rs.getDouble("minimo")*100)/100+" - "+Math.rint(rs.getDouble("maximo")*100)/100);
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
	public String[] Usuario() throws SQLException{
		String query = "select distinct tb_empleado.folio, tb_empleado.nombre+' '+tb_empleado.ap_paterno+' '+tb_empleado.ap_materno as usuario from tb_permisos_submenus_usuarios inner join tb_empleado on tb_empleado.folio=tb_permisos_submenus_usuarios.folio_empleado where tb_empleado.status in (1,2,3,6) order by usuario";
		
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			int j=0;
			while(rs.next()){
				if(j == 0){
					miVector.add("Selecciona Un Usuario");
				}
				miVector.add(rs.getString("usuario"));
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
					miVector.add("Selecciona una Opción");
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
	public String[] Atributo(String tabla) throws SQLException{
		String query = "select descripcion from " + tabla + " order by descripcion asc";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			int j=0;
			while(rs.next()){
				if(j == 0){
					miVector.add("Selecciona un Atributo");
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
	public String[] Nivel_Critico(String tabla) throws SQLException{
		String query = "select descripcion from " + tabla + " order by descripcion asc";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			int j=0;
			while(rs.next()){
				if(j == 0){
					miVector.add("Selecciona un Nivel Critico");
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
	public String[] Temporada(String tabla) throws SQLException{
		String query = "select descripcion from " + tabla + " order by descripcion asc";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			int j=0;
			while(rs.next()){
				if(j == 0){
					miVector.add("Selecciona una Temporada");
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
	public String[] ComboALimentacionMultiple(int actividad) throws SQLException{
		String query = "exec sp_select_combo_opciones_respuesta "+actividad;
		
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			int j=0;
			while(rs.next()){
				if(j == 0){
					miVector.add("   Respuestas");
				}
				miVector.add("   "+rs.getString("descripcion"));
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
	
	//cargamos el combo equipo
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
	public String[] Personal_Por_Departamento(int folio_departamento) throws SQLException{
		String query =" select nombre+' '+ap_paterno+' '+ap_materno as Empleado"
					+ "	from tb_empleado where status = 1 "
					+ " and departamento = "+folio_departamento;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
//			int j=0;
			while(rs.next()){
//				if(j == 0){
//					miVector.add("Selecciona un Empleado");
//				}
				miVector.add(rs.getString("Empleado"));
//				j++;
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
			
//			int j=0;
			while(rs.next()){
//				if(j == 0){
//					miVector.add("Selecciona un Tipo");
//				}
				miVector.add(rs.getString("nombre"));
//				j++;
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
		String query = "SELECT concepto_orden_de_pago FROM tb_conceptos_de_orden_de_pago WHERE (status = 'V') order by concepto_orden_de_pago asc";
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
			
//			int j=0;
			while(rs.next()){
//				if(j == 0){
//					miVector.add("Selecciona un Tipo");
//				}
				miVector.add(rs.getString("nombre"));
//				j++;
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
	
}
