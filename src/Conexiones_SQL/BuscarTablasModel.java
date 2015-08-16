package Conexiones_SQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;

import javax.swing.JOptionPane;

import Obj_Administracion_del_Sistema.Obj_Usuario;

public class BuscarTablasModel {
	
	DecimalFormat df = new DecimalFormat("#0.00");
	
	public Object[][] tabla_model_bancos(){
		String query_lista = "exec sp_lista_banco";
		Object[][] matriz = new Object[get_filas(query_lista)][6];
		try {
			Statement stmt = new Connexion().conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query_lista);
			
			int i = 0;
			while(rs.next()){
				matriz[i][0] = rs.getInt(1)+" ";
				matriz[i][1] = "   "+rs.getString(2);
				matriz[i][2] = "   "+rs.getString(3);
				
				matriz[i][3] = rs.getString(4);
				matriz[i][4] = rs.getFloat(5) ==  Float.parseFloat("0.0") ? "" : rs.getFloat(5) ;
				matriz[i][5] = rs.getFloat(6) == Float.parseFloat("0.0") ? "" : rs.getFloat(6) ;
//				matriz[i][5] = rs.getFloat(6) == Float.parseFloat("0.0") ? "" :Decimal( rs.getFloat(6)) ;
				i++;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarTablasModel  en la funcion tabla_model_bancos  procedimiento almacenado sp_lista_banco SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
	    return matriz; 
	}
	
	public Object[][] tabla_model_bancos_empleados(){
		String query_lista = "select nombre from tb_tipo_banco";
		Object[][] matriz = new Object[get_filas(query_lista)+1][2];
		try {
			Statement stmt = new Connexion().conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query_lista);
			
			int i = 0;
			while(rs.next()){
				matriz[i][0] = rs.getString(1);
				matriz[i][1] = "";
				i++;
			}
			matriz[i][0] = "TOTAL";
			matriz[i][1] = "";
			
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarTablasModel  en la funcion tabla_model_bancos  procedimiento almacenado sp_lista_banco SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
	    return matriz; 
	}
	
	
	public Object[][] tabla_model_deduccion_y_percepcionesde_lista_de_raya(){
		String query_lista = "exec sp_select_deduccion_y_percepciones_de_lista_de_raya";
		Object[][] matriz = new Object[get_filas(query_lista)][13];
		try {
			Statement stmt = new Connexion().conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query_lista);
			
			int i = 0;
			while(rs.next()){
				
				matriz[i][0] =  rs.getInt(1)+" ";
				matriz[i][1] =  "   "+rs.getString(2);
				matriz[i][2] =  "   "+rs.getString(3); 
				
				matriz[i][3] =  rs.getInt(4)	== 0 ? "":rs.getInt(4);
				matriz[i][4] =  rs.getInt(5)	== 0 ? "":rs.getInt(5);
				matriz[i][5] =  rs.getInt(6)	== 0 ? "":rs.getInt(6);
				matriz[i][6] =  rs.getInt(7)	== 1 ? "true" : "false" ;
				
				matriz[i][7] =  rs.getInt(8) 	== 0 ? "": rs.getInt(8);
				matriz[i][8] =  rs.getInt(9) 	== 0 ? "": rs.getInt(9);
				matriz[i][9] =  rs.getFloat(10) == 0 ? "" : rs.getFloat(10);
				matriz[i][10] = rs.getFloat(11) == 0 ? "" : rs.getFloat(11);
				
				matriz[i][11] = rs.getBoolean(12)+"";
				matriz[i][12] = rs.getString(13);
				i++;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarTablasModel  en la funcion tabla_model_deduccion_inasistencia  procedimiento almacenado sp_buscar_deduccion_inasistencia SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
	    return matriz; 
	}
	
	public Object[][] tabla_model_persecciones(){
		String query_lista = "exec sp_lista_persecciones";
		Object[][] matriz = new Object[get_filas(query_lista)][6];
		try {
			Statement stmt = new Connexion().conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query_lista);
			
			int i = 0;
			while(rs.next()){
				matriz[i][0] = rs.getInt(1)+" ";
				matriz[i][1] = "   "+rs.getString(2);
				matriz[i][2] = "   "+rs.getString(3);
				matriz[i][3] = Float.parseFloat(rs.getString(4)) == 0 ? "" : Float.parseFloat(rs.getString(4));
				matriz[i][4] = rs.getString(5).trim().equals("true") ? true : false;
				matriz[i][5] = Integer.parseInt(rs.getString(6).trim()) == 0 ? "" : Integer.parseInt(rs.getString(6).trim());
				i++;
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarTablasModel  en la funcion tabla_model_persecciones  procedimiento almacenado sp_lista_persecciones SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
	    return matriz; 
	}
	
	public Object[][] tabla_model_lista_raya(){
		String query_lista = "exec sp_get_lista_raya";
		Object[][] matriz = new Object[get_filas(query_lista)][26];
		try {
			Statement stmt = new Connexion().conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query_lista);
			
			int i = 0;
			while(rs.next()){
				matriz[i][0] = Boolean.parseBoolean(rs.getString(1).trim());
				matriz[i][1] = rs.getInt(2)+" ";
				matriz[i][2] = "   "+rs.getString(3);
				matriz[i][3] = "   "+rs.getString(4);
				matriz[i][4] = Float.parseFloat(rs.getString(5)) == 0 ? "" : Float.parseFloat(rs.getString(5));
				matriz[i][5] = Float.parseFloat(rs.getString(6)) == 0 ? "" : Float.parseFloat(rs.getString(6));
				matriz[i][6] = Float.parseFloat(rs.getString(7)) == 0 ? "" : Float.parseFloat(rs.getString(7));
				matriz[i][7] = Float.parseFloat(rs.getString(8)) == 0 ? "" : Float.parseFloat(rs.getString(8));
				matriz[i][8] = Float.parseFloat(rs.getString(9)) == 0 ? "" : Float.parseFloat(rs.getString(9));
				matriz[i][9] = Float.parseFloat(rs.getString(10)) == 0 ? "" : Float.parseFloat(rs.getString(10));
				matriz[i][10] = Float.parseFloat(rs.getString(11)) == 0 ? "" : Float.parseFloat(rs.getString(11));
				matriz[i][11] = Float.parseFloat(rs.getString(12)) == 0 ? "" : Float.parseFloat(rs.getString(12));
				matriz[i][12] = Float.parseFloat(rs.getString(13)) == 0 ? "" : Float.parseFloat(rs.getString(13));
				matriz[i][13] = Float.parseFloat(rs.getString(14)) == 0 ? "" : Float.parseFloat(rs.getString(14));
				matriz[i][14] = Float.parseFloat(rs.getString(15)) == 0 ? "" : Float.parseFloat(rs.getString(15));
				matriz[i][15] = Float.parseFloat(rs.getString(16)) == 0 ? "" : Float.parseFloat(rs.getString(16));
				matriz[i][16] = Float.parseFloat(rs.getString(17)) == 0 ? "" : Float.parseFloat(rs.getString(17));
				matriz[i][17] = Float.parseFloat(rs.getString(18)) == 0 ? "" : Float.parseFloat(rs.getString(18));
				matriz[i][18] = rs.getString(19);
				matriz[i][19] = Float.parseFloat(rs.getString(20)) == 0 ? "" : Float.parseFloat(rs.getString(20));
				matriz[i][20] = Float.parseFloat(rs.getString(21)) == 0 ? "" : Float.parseFloat(rs.getString(21));
				matriz[i][21] = Float.parseFloat(rs.getString(22)) == 0 ? "" : Float.parseFloat(rs.getString(22));
				matriz[i][22] = Float.parseFloat(rs.getString(23)) == 0 ? "" : Float.parseFloat(rs.getString(23));
				matriz[i][23] = Float.parseFloat(rs.getString(24)) == 0 ? "" : Decimal(Float.parseFloat(rs.getString(24)));
				matriz[i][24] = "   "+rs.getString(25);
				matriz[i][25] = "   "+rs.getString(26);
				
				i++;
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarTablasModel  en la funcion tabla_model_lista_raya  procedimiento almacenado sp_get_lista_raya SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
	    return matriz; 
	}
	
	public String tabla_model_lista_raya_fecha(){
		String valor = "";
		try {
			Connexion con = new Connexion();
			Statement s = con.conexion().createStatement();
			ResultSet rs = s.executeQuery("exec sp_fecha_lista_pre_raya");
			while(rs.next()){
				valor = rs.getString(1);			
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return valor;
	}
	
	public boolean tabla_model_lista_raya_revision_totales(){
		boolean varlor = false;
		try {
			Connexion con = new Connexion();
			Statement s = con.conexion().createStatement();
			ResultSet rs = s.executeQuery("exec sp_init_revision_totales");
			while(rs.next()){
				varlor = rs.getString(1).trim().equals("true") ? true : false;			
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return varlor;
	}
	
	public boolean tabla_model_lista_raya_autorizacion(){
		boolean varlor = false;
		try {
			Connexion con = new Connexion();
			Statement s = con.conexion().createStatement();
			ResultSet rs = s.executeQuery("exec sp_autorizacion");
			while(rs.next()){
				varlor = rs.getString(1).trim().equals("true") ? true : false;			
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return varlor;
	}
	
	public Object[][] tabla_model_alimentacion_totales(){
		String query_lista = "exec sp_select_captura_totales_nomina";
		Object[][] matriz = new Object[get_filas(query_lista)][10];
		try {
			Statement stmt = new Connexion().conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query_lista);
			
			int i = 0;
			while(rs.next()){
				matriz[i][0] = "   "+rs.getString(1);
				matriz[i][1] = rs.getString(2).trim().equals("0.0") ? "" : Float.parseFloat(rs.getString(2).trim());
				i++;
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	    return matriz; 
	}
	
	public Object[][] tabla_model_alimentacion_denominaciones(){
		String query_lista = "exec sp_select_denominaciones";
		Object[][] matriz = new Object[get_filas(query_lista)][10];
		try {
			Statement stmt = new Connexion().conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query_lista);
			
			int i = 0;
			while(rs.next()){
				matriz[i][0] = "   "+rs.getString(1);
				matriz[i][1] = "   "+rs.getString(2);
				matriz[i][2] = "   "+rs.getString(3);
				matriz[i][3] = "   "+rs.getString(4);
				matriz[i][4] = rs.getString(5).trim().equals("0.0") ? "" : Float.parseFloat(rs.getString(5).trim());
				i++;
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	    return matriz; 
	}
	
	public Object[][] tabla_model_alimentacion_denominaciones_modificar(String folio_corte){
		String query_lista = "exec sp_select_tabla_alimentacion_efectivo_cortes "+folio_corte;
		
		Object[][] matriz = new Object[get_filas(query_lista)][10];
		try {
			Statement stmtl = new Connexion().conexion().createStatement();
			
			ResultSet rs = stmtl.executeQuery(query_lista);

			int i = 0;
			while(rs.next()){
				matriz[i][0] = "   "+rs.getString(1);
				matriz[i][1] = "   "+rs.getString(2);
				matriz[i][2] = "   "+rs.getString(3);
				matriz[i][3] = "   "+rs.getString(4);
				matriz[i][4] = rs.getString(5).toString().trim().equals("0.0") || rs.getString(5).toString().trim().equals("0") ? "" : rs.getString(5).trim();
				i++;
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	    return matriz; 
	}
	
	public Object[][] tabla_model_alimentacion_cheques(String folio_corte){
		String query_lista = "exec sp_select_tabla_cheques '"+folio_corte+"'";
		
		Object[][] matriz = new Object[get_filas(query_lista)][10];
		try {
			Statement stmtl = new Connexion().conexion().createStatement();
			
			ResultSet rs = stmtl.executeQuery(query_lista);

			int i = 0;
			while(rs.next()){
				matriz[i][0] = rs.getString(1);
				matriz[i][1] = rs.getString(2);
				i++;
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	    return matriz; 
	}
	
	public Object[][] tabla_model_alimentacion_deposito_modificar(String folio_corte){
		String query_lista = "exec sp_select_tabla_alimentacion_deposito "+folio_corte;
		
		Object[][] matriz = new Object[get_filas(query_lista)][2];
		try {
			Statement stmtl = new Connexion().conexion().createStatement();
			
			ResultSet rs = stmtl.executeQuery(query_lista);

			int i = 0;
			while(rs.next()){
				matriz[i][0] = rs.getString(1).toString().trim();
				matriz[i][1] = rs.getString(2).toString().trim().equals("0") ? "" : rs.getString(2).toString().trim();
				i++;
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	    return matriz; 
	}
	
	public Object[][] tabla_model_filtro_cuadrante(){
		String query_lista = "exec sp_select_filtro_relacion_empleados_en_cuadrantes";
		
		Object[][] matriz = new Object[get_filas(query_lista)][2];
		try {
			Statement stmtl = new Connexion().conexion().createStatement();
			
			ResultSet rs = stmtl.executeQuery(query_lista);

			int i = 0;
			while(rs.next()){
				matriz[i][0] = rs.getString(1).toString().trim()+"  ";
				matriz[i][1] = "  "+rs.getString(2).toString().trim();
				i++;
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	    return matriz; 
	}
	
	public Object[][] tabla_filtro_empleados_en_cuadrantes(){
		String query_lista = "exec sp_select_filtro_empleados_en_cuadreantes";
		
		Object[][] matriz = new Object[get_filas(query_lista)][2];
		try {
			Statement stmtl = new Connexion().conexion().createStatement();
			
			ResultSet rs = stmtl.executeQuery(query_lista);

			int i = 0;
			while(rs.next()){
				matriz[i][0] = rs.getString(1).toString().trim()+"  ";
				matriz[i][1] = "  "+rs.getString(2).toString().trim();
				i++;
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	    return matriz; 
	}
	
	public boolean tabla_model_lista_raya_init_totales_nomina(){
		boolean varlor = false;
		try {
			Connexion con = new Connexion();
			Statement s = con.conexion().createStatement();
			ResultSet rs = s.executeQuery("exec sp_init_totales_nomina");
			while(rs.next()){
				varlor = rs.getString(1).trim().equals("true") ? true : false;			
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return varlor;
	}
	
	public Object[][] filtro_actividad(){
		String query_lista = "exec sp_filtro_actividad";
		Object[][] matriz = new Object[get_filas(query_lista)][3];
		try {
			Statement stmt = new Connexion().conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query_lista);
			
			int i = 0;
			while(rs.next()){
				matriz[i][0] = rs.getInt(1)+" ";
				matriz[i][1] = "   "+rs.getString(2);
				matriz[i][2] = "   "+rs.getString(3);
				i++;
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	    return matriz; 
	}
	
	public Object[][] filtro_actividad_nivel_jerarquico(){
		String query_lista = "exec sp_filtro_actividad_nivel_jerarquico";
		Object[][] matriz = new Object[get_filas(query_lista)][3];
		try {
			Statement stmt = new Connexion().conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query_lista);
			
			int i = 0;
			while(rs.next()){
				matriz[i][0] = rs.getInt(1)+" ";
				matriz[i][1] = "   "+rs.getString(2);
				matriz[i][2] = "   "+rs.getString(3);
				i++;
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	    return matriz; 
	}
	
	public Object[][] tabla_model_respuesta(String nombre){
		String query_lista = "exec sp_select_tabla_respuesta '"+nombre+"'";
		Object[][] matriz = new Object[get_filas(query_lista)][2];
		try {
			Statement stmt = new Connexion().conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query_lista);
			
			int i = 0;
			while(rs.next()){
				matriz[i][0] = rs.getInt(1)+" ";
				matriz[i][1] = "   "+rs.getString(2);

				i++;
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	    return matriz; 
	}
	
	public int get_filas(String sentencia){
		int filas = 0;
		try {
			Statement stmt = new Connexion().conexion().createStatement();
			ResultSet rs = stmt.executeQuery(sentencia);
			while(rs.next())
				filas++;
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return filas;
	}	
	
	public double Decimal(double x){
		String numeroString = x+"";
		String[] dec = numeroString.split("\\.");
	
		double valor = Integer.parseInt(dec[0]);
		double decimal = Double.parseDouble("."+dec[1]);
		
		if(decimal <= 0.25){
			return valor;
		}
		if(decimal > 0.25 && decimal <= 0.74){
			return valor + 0.5;
		}
		if(decimal >= 0.75 && decimal <= .9){
			return valor + 1;
		}
		return valor;
	}

	public boolean Lista_Raya_Obtener(int parseInt) {
		String query = "update tb_folio_lista_raya_pasada set folio=?";
		Connection con = new Connexion().conexion();
		
		try {
			
			PreparedStatement pstmt = con.prepareStatement(query);

			con.setAutoCommit(false);
		
			pstmt.setInt(1, parseInt);
	
			pstmt.executeUpdate();
		
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacci�n ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	public Object[][] tabla_model_filtro_gafetes(){
		String query_lista = "exec sp_select_empleados_gafete";
		Object[][] matriz = new Object[get_filas(query_lista)][5];
		try {
			Statement stmt = new Connexion().conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query_lista);
			
			int i = 0;
			while(rs.next()){
				matriz[i][0] = rs.getInt(1)+" ";
				matriz[i][1] = "   "+rs.getString(2);
				matriz[i][2] = "   "+rs.getString(3);
				matriz[i][3] = "   "+rs.getString(4);
				matriz[i][4] = "   "+rs.getString(5);
				
				i++;
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	    return matriz; 
	}
	public Object[][] tabla_model_filtro_Obtener_Emp_imprimir_cuadrantes(){
		Obj_Usuario usuario = new Obj_Usuario().LeerSession();
		
		String query_lista = "exec sp_select_impresion_cuadrante "+"'"+usuario.getNombre_completo()+"'" ;
		
		Object[][] matriz = new Object[get_filas(query_lista)][6];
		try {
			Statement stmt = new Connexion().conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query_lista);
			
			int i = 0;
			while(rs.next()){
				matriz[i][0] = rs.getInt(1);
				matriz[i][1] = "   "+rs.getString(2);
				matriz[i][2] = "   "+rs.getString(3);
				matriz[i][3] = "   "+rs.getString(4);
				matriz[i][4] = "   "+rs.getString(5);
				matriz[i][5] = rs.getString(6);
				i++;
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	    return matriz; 
	}
	
	public Object[][] tabla_model_checador(){
		String query_lista = "exec sp_select_tabla_checador";
		Object[][] matriz = new Object[get_filas(query_lista)][9];
		try {
			Statement stmt = new Connexion().conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query_lista);
			
			int i = 0;
			while(rs.next()){
				matriz[i][0] = "   "+rs.getString(1);
				matriz[i][1] = "   "+rs.getString(2);
				matriz[i][2] = "   "+rs.getString(3);
				matriz[i][3] = "   "+rs.getString(4);
				matriz[i][4] = "   "+rs.getString(5);
				matriz[i][5] = "   "+rs.getString(6);
				matriz[i][6] = "   "+rs.getString(7);
				matriz[i][7] = "   "+rs.getString(8);
				matriz[i][8] = "   "+rs.getString(9);
				
				i++;
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarTablaModel  en la funcion tabla_model_checador store procedure sp_select_tabla_checador  "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);

		}
	    return matriz; 
	}
	
	public Object[][] tabla_model_empleados_conpendiente_en_fuente_de_sodas_auxf(){
		String query = "exec sp_select_filtro_empleados_con_pendiente_en_fuente_de_sodas";
		Object[][] matriz = new Object[get_filas(query)][5];
		try {
			Statement stmt = new Connexion().conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			int i = 0;
			while(rs.next()){
				matriz[i][0] =rs.getString(1);
				matriz[i][1] = "   "+rs.getString(2);
				matriz[i][2] = "   "+rs.getString(3);
				matriz[i][3] = "   "+rs.getString(4);
				matriz[i][4] = "   "+rs.getString(5);
				
				i++;
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	    return matriz; 
	}
	
	public Object[][] tabla_model_empleados_conpendiente_en_fuente_de_sodas_dh(){
		String query = "exec sp_select_filtro_empleados_con_pendiente_en_fuente_de_sodas_dh";
		Object[][] matriz = new Object[get_filas(query)][5];
		try {
			Statement stmt = new Connexion().conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			int i = 0;
			while(rs.next()){
				matriz[i][0] =rs.getString(1);
				matriz[i][1] = "   "+rs.getString(2);
				matriz[i][2] = "   "+rs.getString(3);
				matriz[i][3] = "   "+rs.getString(4);
				matriz[i][4] = "   "+rs.getString(5);
				
				i++;
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	    return matriz; 
	}
	
	public Object[][] tabla_model_departamento(){
		String query_lista = "exec sp_select_tabla_departamento";
		Object[][] matriz = new Object[get_filas(query_lista)][9];
		try {
			Statement stmt = new Connexion().conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query_lista);
			
			int i = 0;
			while(rs.next()){
				matriz[i][0] = "   "+rs.getString(1);
				matriz[i][1] = "   "+rs.getString(2);
				matriz[i][2] = "   "+rs.getString(3);
				i++;
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	    return matriz; 
	}
	
	public boolean tablas_status(String nombre){
		boolean varlor = false;
		try {
			Connexion con = new Connexion();
			Statement s = con.conexion().createStatement();
			ResultSet rs = s.executeQuery("exec sp_status_tabla_cuadrantes '" + nombre + "';");
			while(rs.next()){
				varlor = rs.getString(1).trim().equals("true") ? true : false;			
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return varlor;
	}

	public Object[][] tabla_model_filtro_solicitudes(String status){
		String query_lista = "exec sp_filtro_solicitudes_empleados '"+status+"';";
		
		Object[][] matriz = new Object[get_filas(query_lista)][4];
		try {
			Statement stmt = new Connexion().conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query_lista);
			
			int i = 0;
			while(rs.next()){
				matriz[i][0] = "   "+rs.getString(1);
				matriz[i][1] = "   "+rs.getString(2);
				matriz[i][2] = "   "+rs.getString(3);
				matriz[i][3] = "   "+rs.getString(4);
				i++;
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	    return matriz; 
	}
	
	public Object[][] tabla_model_rango_de_vacaciones(){
		String query_lista = "exec sp_select_tabla_de_rangos_de_vacaciones";
		Object[][] matriz = new Object[get_filas(query_lista)][4];
		try {
			Statement stmt = new Connexion().conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query_lista);
			
			int i = 0;
			while(rs.next()){
				matriz[i][0] = "   "+rs.getString(1);
				matriz[i][1] = "   "+rs.getInt(2);
				matriz[i][2] = "   "+rs.getInt(3);
				matriz[i][3] = "   "+rs.getInt(4);
				i++;
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	    return matriz; 
	}
	
	public Object[][] tabla_grupo_vacaciones(){
		String query_lista = "select * from tb_grupo_de_vacaciones";
		Object[][] matriz = new Object[get_filas(query_lista)][2];
		try {
			Statement stmt = new Connexion().conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query_lista);
			
			int i = 0;
			while(rs.next()){
				matriz[i][0] = "   "+rs.getString(1);
				matriz[i][1] = "   "+rs.getString(2);
				i++;
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	    return matriz; 
	}
	
	public Object[][] tabla_model_lay_out(String tipo_banco,String fecha_mov){
		String query_lista = "exec sp_select_lay_out_banorte '"+tipo_banco+"','"+fecha_mov+"';";
		Object[][] matriz = new Object[get_filas(query_lista)][1];
		try {
			Statement stmt = new Connexion().conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query_lista);
			
			int i = 0;
			while(rs.next()){
				matriz[i][0] = "   "+rs.getString(1);
				i++;
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	    return matriz; 
	}
	
	public Object[][] tabla_model_empleados_vacaciones(){
		String query = "exec sp_select_filtro_empleados_para_vacaciones";
		Object[][] matriz = new Object[get_filas(query)][4];
		try {
			Statement stmt = new Connexion().conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			int i = 0;
			while(rs.next()){
				matriz[i][0] =rs.getString(1);
				matriz[i][1] = "   "+rs.getString(2);
				matriz[i][2] = "   "+rs.getString(3);
				matriz[i][3] = "   "+rs.getString(4);
				i++;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en tabla_model_empleados_vacaciones en la funcion sp_select_filtro_empleados_para_vacaciones  SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
	    return matriz; 
	}
	
	public Object[][] tabla_de_vacaciones_disfrutadas(int folio_empleado){
		String query_lista = "exec sp_select_tabla_de_vacaciones_disfrutadas "+folio_empleado;
		Object[][] matriz = new Object[get_filas(query_lista)][4];
		try {
			Statement stmt = new Connexion().conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query_lista);
			
			int i = 0;
			while(rs.next()){
				matriz[i][0] = "   "+rs.getString(1);
				matriz[i][1] = "   "+rs.getString(2);
				matriz[i][2] = "   "+rs.getString(3);
				matriz[i][3] = "   "+rs.getString(4);
				i++;
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en tabla_de_vacaciones_disfrutadas en la funcion sp_select_tabla_de_vacaciones_disfrutadas  SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
	    return matriz; 
	}
	
	public Object[][] tabla_model_proveedores_guardados(){
		String query_lista = ("SELECT cod_prv" +
									"	,proveedor " +
									"	,folio_factura " +
									"	,convert (varchar(20),[fecha_factura],103)as fecha_factura" +
									"	,convert(varchar(20),[fecha_modificacion],103)as fecha_modificacion "+
					                "	,(select nombre+' '+ap_paterno+' 'ap_materno from tb_empleado where folio=folio_empleado_modifico)as empleado_modifico " +
					                " 	,case when len(xml) is null" +
					                "		then 0 " +
					                "		else 1 " +
					                "	end as xml " +
					                ",case when len(pdf)is null " +
					                "		then 0 " +
					                "		else 1 	" +
					                "	end as pdf" +
					                " 	,Status " +
					                " FROM tb_control_de_facturas_y_xml " +
					                " where status=1 " +
								    " order by fecha_factura desc");
		
	Object[][] matriz = new Object[get_filas(query_lista)][8];
		try {
			Statement stmt = new Connexion().conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query_lista);
			
			int i = 0;
			while(rs.next()){
				matriz[i][0] = rs.getString(1);
				matriz[i][1] = rs.getString(2);
				matriz[i][2] = rs.getString(3);
				matriz[i][3] = rs.getString(4);
				matriz[i][4] = rs.getString(5);
				matriz[i][5] = rs.getString(6);
				matriz[i][6] = rs.getString(7);
				matriz[i][7] = rs.getString(8);
				i++;
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarTablasModel en la funcion tabla_model_proveedores_guardados  SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
	    return matriz; 
	}
	
	public Object[][] tabla_model_cliente(){
//		cambiar consulta
		String query_lista = "select folio_cliente,nombre+' '+ap_paterno+' '+ap_materno as empleado,direccion from tb_clientes where status = 1";
		Object[][] matriz = new Object[get_filas(query_lista)][3];
		try {
			Statement stmt = new Connexion().conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query_lista);
			
			int i = 0;
			while(rs.next()){
				matriz[i][0] = rs.getString(1)+"   ";
				matriz[i][1] = "   "+rs.getString(2);
				matriz[i][2] = "   "+rs.getString(3);
				
				i++;
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	    return matriz; 
	}
	
	public Object[][] tabla_model_tickets(int folio_cliente){
		String query_lista = "exec sp_select_tabla_ticket_c_ahorro_cte "+folio_cliente;
		Object[][] matriz = new Object[get_filas(query_lista)][4];
		try {
			Statement stmt = new Connexion().conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query_lista);
			
			int i = 0;
			while(rs.next()){
				matriz[i][0] = "   "+rs.getString(1);
				matriz[i][1] = "   "+rs.getString(2);
				matriz[i][2] = "   "+rs.getString(3);
				matriz[i][3] = "   "+rs.getString(4);
				
				i++;
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarTablaModel  en la funcion tabla_model_tickets store procedure sp_select_tabla_ticket_c_ahorro_cte  "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);

		}
	    return matriz; 
	}
	
	public Object[][] tabla_model_abonos(String ticket){
		//cambiar procedimiento para que traiga los abonos de el cliente del parametro
		String query_lista = "exec sp_select_abonos_c_ahorro_cliente '"+ticket+"';";
		Object[][] matriz = new Object[get_filas(query_lista)][5];
		try {
			Statement stmt = new Connexion().conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query_lista);
			
			int i = 0;
			while(rs.next()){
				matriz[i][0] = "   "+rs.getString(1);
				matriz[i][1] = "   "+rs.getString(2);
				matriz[i][2] = "   "+rs.getString(3);
				matriz[i][3] = "   "+rs.getString(4);
				matriz[i][4] = "   "+rs.getString(5);
				
				i++;
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarTablaModel  en la funcion tabla_model_abonos store procedure sp_select_abonos_c_ahorro_cliente  "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);

		}
	    return matriz; 
	}
	
	public Object[][] tabla_establecimientos_para_concentrado(){
		
		String query_lista = "select tb_establecimiento.nombre as establecimiento " +
				"					, isnull(tb_grupos_para_cortes.grupo_para_cortes,'SIN ASIGNAR') as grupo " +
				"				from tb_establecimiento " +
				"				left outer join tb_grupos_para_cortes on tb_grupos_para_cortes.folio_grupo_para_cortes = tb_establecimiento.folio_grupo_para_cortes";
		
		Object[][] matriz = new Object[get_filas(query_lista)][2];
		try {
			Statement stmt = new Connexion().conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query_lista);
			
			int i = 0;
			while(rs.next()){
				matriz[i][0] = "   "+rs.getString(1);
				matriz[i][1] = "   "+rs.getString(2);
				i++;
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarTablaModel  en la funcion tabla_establecimientos_para_concentrado  "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);

		}
	    return matriz; 
	}
	
	public String[][] denominaciones_apartados(){
		String[][] Matriz = null;
		
		String query = "SELECT NOMBRE_DIVISAS AS MONEDA " +
						 "		,VALOR AS VALOR " +
						 "		,'' AS PAGO " +
						 "		,0 AS IMPORTE " +
						 " FROM tb_divisas_tipo_de_cambio " +
						 " WHERE STATUS = 1 " +
						 " ORDER BY FOLIO DESC";
		
		Matriz = new String[get_filas(query)][4];
		
		try {	
			Statement stmt = new Connexion().conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			int i = 0;
			while(rs.next()){
				Matriz[i][0] = rs.getString(1);
				Matriz[i][1] = rs.getString(2);
				Matriz[i][2] = rs.getString(3);
				Matriz[i][3] = rs.getString(4);
				
				i++;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion getRetiros_a_detalle \n  en el procedimiento : sp_select_retiro_de_cajero_a_detalle  \n SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
		
		return Matriz;
	}
	
	public int get_filas_izagar(String sentencia){
		int filas = 0;
		try {
			Statement stmt = new Connexion().conexion_IZAGAR().createStatement();
			ResultSet rs = stmt.executeQuery(sentencia);
			while(rs.next())
				filas++;
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return filas;
	}
	
	public String[][] traer_tabla_base_calculos(double porcentaje,String fecha, String establecimiento){
		
		String query1 = "exec sp_Recopilacion_IZAGAR_de_Asignaciones_y_cajeros";
		String query2 = "exec sp_Reporte_IZAGAR_de_Valores_por_Tasa_por_asignacion";
		
		Connection con = new Connexion().conexion_IZAGAR();
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		try {
			con.setAutoCommit(false);
			
			pstmt1 = con.prepareStatement(query1);
			pstmt1.executeUpdate();
			
			pstmt2 = con.prepareStatement(query2);
			pstmt2.executeUpdate();
			
			
			con.commit();
			
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacci�n ha sido abortada");
					JOptionPane.showMessageDialog(null, "Error en ActualizarSQL  en la funcion Empleado  procedimiento almacenado sp_update_alta_empleado SQLException: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
				}
			}
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		

		
		
		String[][] Matriz = null;
		
		String query = "exec sp_cortes_de_una_fecha_determinada '"+fecha+"','"+establecimiento+"';";
		
		System.out.println(query);
		
		Matriz = new String[get_filas_izagar(query)][16];
		
		try {	
			Statement stmtIZ = new Connexion().conexion_IZAGAR().createStatement();
			ResultSet rs = stmtIZ.executeQuery(query);
			
			int i = 0;
			while(rs.next()){
				Matriz[i][0] = rs.getString(1);
				Matriz[i][1] = rs.getString(2);
				Matriz[i][2] = (df.format(rs.getDouble(3)*porcentaje)+"");
				Matriz[i][3] = (df.format(rs.getDouble(4)*porcentaje)+"");
				Matriz[i][4] = (df.format(rs.getDouble(5)*porcentaje)+"");
				Matriz[i][5] = (df.format(rs.getDouble(6)*porcentaje)+"");
				Matriz[i][6] = (df.format(rs.getDouble(7)*porcentaje)+"");
				Matriz[i][7] = (df.format(rs.getDouble(8)*porcentaje)+"");
				Matriz[i][8] = (df.format(rs.getDouble(9)*porcentaje)+"");
				Matriz[i][9] = (df.format(rs.getDouble(10)*porcentaje)+"");
				Matriz[i][10] = (df.format(rs.getDouble(11)*porcentaje)+"");
				Matriz[i][11] = (df.format(rs.getDouble(12)*porcentaje)+"");
				Matriz[i][12] = (df.format(rs.getDouble(13)*porcentaje)+"");
				Matriz[i][13] = (df.format(rs.getDouble(14)*porcentaje)+"");
				Matriz[i][14] = rs.getString(15);
				Matriz[i][15] = rs.getString(16);
				
				i++;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion getRetiros_a_detalle \n  en el procedimiento : sp_select_retiro_de_cajero_a_detalle  \n SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
		
		return Matriz;
	}
	
	public String[][] filtro_cortes_guardados(){
		String[][] Matriz = null;
		
		String query = "sp_select_cortes_guardados";
		
		Matriz = new String[get_filas(query)][6];
		
		try {	
			Statement stmt = new Connexion().conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			int i = 0;
			while(rs.next()){
				Matriz[i][0] = rs.getString(1);
				Matriz[i][1] = "  "+rs.getString(2);
				Matriz[i][2] = rs.getString(3);
				Matriz[i][3] = df.format(Float.valueOf(rs.getString(4)));
				Matriz[i][4] = rs.getString(5);
				Matriz[i][5] = rs.getString(6);
				
				i++;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion [filtro_cortes_guardados] \n  en el procedimiento : sp_select_cortes_guardados  \n SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
		
		return Matriz;
	}
	
	public String[][] filtro_impuntualidad_a_considerar(String fecha_inicio,String fecha_final,String Establecimiento,String Departamento,String folios_empleados){
		String[][] Matriz = null;
		
		String query = "sp_select_consideracion_checador '"+fecha_inicio+"','"+fecha_final+"','"+Establecimiento+"','"+Departamento+"','"+folios_empleados+"';";
		
		Matriz = new String[get_filas(query)][17];
		
		try {	
			Statement stmt = new Connexion().conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			int i = 0;
			while(rs.next()){
				Matriz[i][0] = rs.getString(2);
				Matriz[i][1] = "  "+rs.getString(3);
				Matriz[i][2] = rs.getString(4);
				Matriz[i][3] = rs.getString(5);
				Matriz[i][4] = rs.getString(6);
				Matriz[i][5] = rs.getString(7);
				
				Matriz[i][6]  = rs.getString(8);
				Matriz[i][7]  = rs.getString(9);
				Matriz[i][8]  = rs.getString(10);
				Matriz[i][9]  = rs.getString(11);
				Matriz[i][10] = rs.getString(12);
				Matriz[i][11] = rs.getString(13);
				Matriz[i][12] = rs.getString(14);
				Matriz[i][13] = rs.getString(15);
				Matriz[i][14] = rs.getString(16);
				Matriz[i][15] = rs.getString(17);
				Matriz[i][16] = rs.getInt(18)==0?"false":"true";
				
				i++;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion [filtro_impuntualidad_a_considerar] \n  en el procedimiento : sp_Select_Consideracion_Checador  \n SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
		
		return Matriz;
	}
	
	public String[][] lista_de_establecimiento(){
		String[][] Matriz = null;
		
		String query = "select folio as folio_establecimiento" +
				"						,nombre as establecimiento " +
				"		from tb_establecimiento " +
				"		where status = 1";
		
		Matriz = new String[get_filas(query)][2];
		
		try {	
			Statement stmt = new Connexion().conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			int i = 0;
			while(rs.next()){
				Matriz[i][0] = rs.getString(1);
				Matriz[i][1] = "  "+rs.getString(2);

				i++;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion [lista_de_establecimiento] \n SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
		
		return Matriz;
	}
	
	public Object[][] tabla_puestos_por_establecimiento(int folio_establecimiento, int folio_departamento){
		String query = "exec sp_select_puestos_por_establecimiento '"+folio_establecimiento+"','"+folio_departamento+"';";
		Object[][] matriz = new Object[get_filas(query)][3];
		try {
			Statement stmt = new Connexion().conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			int i = 0;
			while(rs.next()){
				matriz[i][0] =rs.getString(1);
				matriz[i][1] =rs.getString(2);
				matriz[i][2] =rs.getString(3);
				
				i++;
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	    return matriz; 
	}
	
	public Object[][] tabla_departamentos_por_establecimiento(int folio_establecimiento){
		String query = "exec sp_select_departamentos_por_establecimiento "+folio_establecimiento+";";
		Object[][] matriz = new Object[get_filas(query)][2];
		try {
			Statement stmt = new Connexion().conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			int i = 0;
			while(rs.next()){
				matriz[i][0] =rs.getString(1);
				matriz[i][1] =rs.getString(2);
				
				i++;
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	    return matriz; 
	}
	
	public Object[][] tabla_puestos_disponibles(String listaEnUso, String ventana){
		
		String query = "";
		
		if(ventana.equals("Departamentos")){
			query = "select tb_departamento.folio as folio_departamento" +
					"				,tb_departamento.departamento as departamento" +
					"				,'false' as status" +
					"		from tb_departamento" +
					"		where tb_departamento.status = 1" +
					"		and tb_departamento.folio not in("+listaEnUso+") order by departamento;";
		}else{
			query = "select tb_puesto.folio as folio_puesto" +
					"				,tb_puesto.nombre as puesto" +
					"				,'false' as status" +
					"		from tb_puesto" +
					"		where tb_puesto.status = 1" +
					"		and tb_puesto.folio not in("+listaEnUso+") order by  nombre ;";
		}
		
		Object[][] matriz = new Object[get_filas(query)][3];
		try {
			Statement stmt = new Connexion().conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			int i = 0;
			while(rs.next()){
				matriz[i][0] =rs.getString(1);
				matriz[i][1] =rs.getString(2);
				matriz[i][2] =rs.getString(3);
				
				i++;
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	    return matriz; 
	}
	
	public Object[][] tabla_model_asignaciones(){
		
		String query = " SELECT     asignacion, folio_corte, nombre_cajero, establecimiento " +
						"			FROM         tb_tabla_de_asignaciones_para_cortes " +
						"			order by fecha_de_liquidacion desc";
		
		Object[][] matriz = new Object[get_filas(query)][5];
		try {
			Statement stmt = new Connexion().conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			int i = 0;
			while(rs.next()){
				matriz[i][0] =rs.getString(1);
				matriz[i][1] = "   "+rs.getString(2);
				matriz[i][2] = "   "+rs.getString(3);
				matriz[i][3] = "   "+rs.getString(4);
				matriz[i][4] = "false";
				i++;
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	    return matriz; 
	}
	
	public String[][] tabla_model_trabajo_de_cortes(String cadena, String grupo_de_concentrado){
		
		String query_lista = "exec sp_select_trabajo_de_cortes'"+cadena+"','"+grupo_de_concentrado+"';";		
		String[][] matriz = new String[get_filas(query_lista)][20];
		
		Connection con = new Connexion().conexion();
		try {
			
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query_lista);
			
			int i = 0;
			while(rs.next()){
				matriz[i][0] = " "+rs.getString(1);
				matriz[i][1] = " "+rs.getString(2);
				matriz[i][2] = " "+rs.getString(3);
				matriz[i][3] = " "+rs.getString(4);
				matriz[i][4] = " "+rs.getString(5);
				matriz[i][5] = " "+df.format(rs.getDouble(6));
				matriz[i][6] = " "+df.format(rs.getDouble(7));
				matriz[i][7] = " "+df.format(rs.getDouble(8));
				matriz[i][8] = " "+df.format(rs.getDouble(9));
				matriz[i][9] = " "+df.format(rs.getDouble(10));
				matriz[i][10] = " "+df.format(rs.getDouble(11));
				matriz[i][11] = " "+df.format(rs.getDouble(12));
				matriz[i][12] = " "+df.format(rs.getDouble(13));
				matriz[i][13] = " "+df.format(rs.getDouble(14));
				matriz[i][14] = " "+df.format(rs.getDouble(15));
				matriz[i][15] = " "+df.format(rs.getDouble(16));
				matriz[i][16] = " "+df.format(rs.getDouble(17));
				matriz[i][17] = " "+df.format(rs.getDouble(18));
				matriz[i][18] = " "+rs.getString(19);
				matriz[i][19] = " "+rs.getString(20);
				i++;
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarTablaModel  en la funcion tabla_model_trabajo_de_cortes store procedure sp_select_trabajo_de_cortes  "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);

		}
	    return matriz; 
	}
	
	public String[][] tabla_model_revision_de_cortes_por_auditoria(String fecha_in, String fecha_fin,String seleccionar_todos){
		
		Obj_Usuario usuario = new Obj_Usuario().LeerSession();
		
		String query_lista = "exec sp_Revision_De_Cortes_Auditoria '"+fecha_in+"','"+fecha_fin+"',"+usuario.getFolio()+","+seleccionar_todos;
		String[][] matriz = new String[get_filas(query_lista)][35];
		
		Connection con = new Connexion().conexion();
		try {
			
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query_lista);
			
			int i = 0;
			while(rs.next()){
				
				matriz[i][0 ] = " "+rs.getString(1);
				matriz[i][1 ] = " "+rs.getString(2);
				matriz[i][2 ] = " "+rs.getString(3);
				matriz[i][3 ] = " "+rs.getString(4);
				matriz[i][4 ] = " "+rs.getString(5);
				            
				matriz[i][5 ] = " "+df.format(rs.getDouble(6));
				matriz[i][6 ] = " "+df.format(rs.getDouble(7));
				matriz[i][7 ] = " "+df.format(rs.getDouble(8));
				matriz[i][8 ] = " "+df.format(rs.getDouble(9));
				matriz[i][9 ] = " "+df.format(rs.getDouble(10));
				matriz[i][10] = " "+df.format(rs.getDouble(11));
				matriz[i][11] = " "+df.format(rs.getDouble(12));
				matriz[i][12] = " "+df.format(rs.getDouble(13));
				matriz[i][13] = " "+df.format(rs.getDouble(14));
				matriz[i][14] = " "+df.format(rs.getDouble(15));
				matriz[i][15] = " "+df.format(rs.getDouble(16));
				matriz[i][16] = " "+df.format(rs.getDouble(17));
				matriz[i][17] = " "+df.format(rs.getDouble(18));
				matriz[i][18] = " "+df.format(rs.getDouble(19));
				
				matriz[i][19] = " "+rs.getString(20);
				matriz[i][20] = " "+rs.getString(21);
				matriz[i][21] = " "+rs.getString(22);
				matriz[i][22] = " "+rs.getString(23);
				matriz[i][23] = " "+rs.getString(24);
				matriz[i][24] = " "+rs.getString(25);
				matriz[i][25] = " "+rs.getString(26);
				matriz[i][26] = " "+df.format(rs.getDouble(27));
				matriz[i][27] = " "+rs.getString(28);
				matriz[i][28] = " "+rs.getString(29);
				matriz[i][29] = " "+rs.getString(30);
				matriz[i][30] = " "+rs.getString(31);
				matriz[i][31] = " "+df.format(rs.getDouble(32));
				matriz[i][32] = " "+rs.getString(33);
				matriz[i][33] = " "+rs.getString(34);
				matriz[i][34] = " "+rs.getString(35);
				
				i++;
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarTablaModel  en la funcion tabla_model_trabajo_de_cortes \n store procedure sp_Revision_De_Cortes_Auditoria  "+e1.getMessage()+query_lista, "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
	    return matriz; 
	}
	
	public String[] permiso_para_revision_de_cortes(){
		Obj_Usuario usuario = new Obj_Usuario().LeerSession();
		
		String query_lista = "exec sp_Permiso_Revision_De_Cortes "+usuario.getFolio();
		String[] matriz = new String[2];
		
		Connection con = new Connexion().conexion();
		try {
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query_lista);
			
				while(rs.next()){
					matriz[0 ] = " "+rs.getString(1);
					matriz[1 ] = " "+rs.getString(2);
				}

		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarTablaModel  en la funcion tabla_model_trabajo_de_cortes \n store procedure sp_Permiso_Revision_De_Cortes  "+e1.getMessage()+query_lista, "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
	    return matriz; 
	}
	
	public String[] Responsable_de_error_en_corte(){
		String query_lista = "SELECT responsable FROM tb_responsable_de_errores_en_corte WHERE status = 1";
		String[] matriz = new String[get_filas(query_lista)];
		
		Connection con = new Connexion().conexion();
		try {
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery(query_lista);
				
				int i = 0;
				while(rs.next()){
					matriz[i ] = " "+rs.getString(1);
					i++;
				}

		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarTablaModel  en la funcion Responsable_de_error_en_corte "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
	    return matriz; 
	}
	
	public String[][] tabla_filtro_de_asignaciones_para_ajuste_de_ticket(String asignacion){
		
		String query_lista = "declare @folio_establecimiento int "
				+ "		set @folio_establecimiento = (select cod_estab from establecimientos where nombre = '"+asignacion+"'); "
				+ "		select Asignacion,convert(varchar(20),Fecha_liquidacion,103)+' '+convert(varchar(20),Fecha_liquidacion,108) as fecha_liquidacion,Iva,IEPS,Total,Nombre_Cajero "
				+ " from IZAGAR_Relacion_de_Asignaciones_Liquidadas "
				+ " where Cod_Estab = @folio_establecimiento "
				+ "	and Fecha_Liquidacion > getdate()-8 order by asignacion";

		String[][] matriz = new String[get_filas_izagar(query_lista)][6];
		
		Connection con = new Connexion().conexion_IZAGAR();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query_lista);
			
			int i = 0;
			while(rs.next()){
				
				matriz[i][0 ] = " "+rs.getString(1);
				matriz[i][1 ] = " "+rs.getString(2);
				matriz[i][2 ] = " "+rs.getString(3);
				matriz[i][3 ] = " "+rs.getString(4);
				matriz[i][4 ] = " "+rs.getString(5);
				matriz[i][5 ] = " "+rs.getString(6);
				
				i++;
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarTablaModel  en la funcion tabla_filtro_de_asignaciones_para_ajuste_de_ticket "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
	    return matriz; 
	}
	
	
	public String[][] tabla_de_ajustes_ticket(String asignacion){
		
		String query_lista = "select folio,transaccion,convert(varchar(20),fecha,103)as fecha,importe,iva,status,'false' from IZAGAR_AVi_facremtick where  status=1 and asignacion = '"+asignacion+"';";

		String[][] matriz = new String[get_filas_izagar(query_lista)][7];
		
		Connection con = new Connexion().conexion_IZAGAR();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query_lista);
			int i = 0;
			while(rs.next()){
				matriz[i][0 ] = " "+rs.getString(1);
				matriz[i][1 ] = " "+rs.getString(2);
				matriz[i][2 ] = " "+rs.getString(3);
				matriz[i][3 ] = " "+rs.getString(4);
				matriz[i][4 ] = " "+rs.getString(5);
				matriz[i][5 ] = " "+rs.getString(6);
				matriz[i][6 ] = " "+rs.getString(7);
				
				i++;
				

			}

		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarTablaModel  en la funcion tabla_filtro_de_asignaciones_para_ajuste_de_ticket \n "+query_lista+" "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
	    return matriz; 
	}
	
public String[][] tabla_filtro_de_asignaciones_para_corregir_asignacion(){
		
		String query_lista = "select asignacion, convert(varchar(10),fecha,103)as fecha, sum(iva) as iva"
				+ "   from IZAGAR_AVI_facremtick  where status=2"
				+ " group by asignacion,convert(varchar(10),fecha,103) ";
		
		String[][] matriz = new String[get_filas_izagar(query_lista)][3];
		
		Connection con = new Connexion().conexion_IZAGAR();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query_lista);
			
			int i = 0;
			while(rs.next()){
				matriz[i][0 ] = " "+rs.getString(1);
				matriz[i][1 ] = " "+rs.getString(2);
				matriz[i][2 ] = " "+rs.getString(3);
				i++;
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarTablaModel  en la funcion tabla_filtro_de_asignaciones_para_ajuste_de_ticket "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
	    return matriz; 
	}

public Object[][] tabla_model_competencia(){
	
	String query = "select folio_competencia, competencia  from tb_competencias where status = 1 order by folio_competencia asc";
	
	Object[][] matriz = new Object[get_filas(query)][3];
	try {
		Statement stmt = new Connexion().conexion().createStatement();
		ResultSet rs = stmt.executeQuery(query);
		
		int i = 0;
		while(rs.next()){
			matriz[i][0] =rs.getString(1);
			matriz[i][1] = "   "+rs.getString(2);
			matriz[i][2] = "";
			
			i++;
		}

	} catch (SQLException e1) {
		e1.printStackTrace();
	}
    return matriz; 
}
	
}




	
