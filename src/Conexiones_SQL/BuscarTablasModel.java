package Conexiones_SQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import Obj_Administracion_del_Sistema.Obj_Usuario;

public class BuscarTablasModel {
	
	DecimalFormat df = new DecimalFormat("#0.00");
	
	public Object[][] tabla_model_deduccion_y_percepcionesde_lista_de_raya(){
		String query_lista = "exec sp_select_deduccion_y_percepciones_de_lista_de_raya";
		Object[][] matriz = new Object[get_filas(query_lista)][15];
		try {
			Statement stmt = new Connexion().conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query_lista);
			
			int i = 0;
			while(rs.next()){
				
				matriz[i][0] =  rs.getInt(1)+" ";
				matriz[i][1] =  "   "+rs.getString(2);
				matriz[i][2] =  "   "+rs.getString(3); 
				
				matriz[i][3] =  rs.getInt(4)	== 0 ? "":rs.getInt(4);
				matriz[i][4] =  (rs.getInt(5) == 1 && rs.getInt(4)==0 ) ? "true" : "false" ;//bono puntualidad
				matriz[i][5] =  rs.getInt(6)	== 0 ? "":rs.getInt(6);
				matriz[i][6] =  rs.getInt(7)	== 0 ? "":rs.getInt(7);
				matriz[i][7] =  rs.getInt(8)	== 1 ? "true" : "false" ;
				matriz[i][8] =  (rs.getInt(9) == 1 ) ? "true" : "false" ;//bono asistencia
				
				matriz[i][9] =  rs.getInt(10) 	== 0 ? "": rs.getInt(10);
				matriz[i][10] =  rs.getInt(11) 	== 0 ? "": rs.getInt(11);
				matriz[i][11] =  rs.getFloat(12) == 0 ? "" : rs.getFloat(12);
				matriz[i][12] = rs.getFloat(13) == 0 ? "" : rs.getFloat(13);
				matriz[i][13] = rs.getBoolean(14)+"";
				matriz[i][14] = rs.getString(15); 
				
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
		Object[][] matriz = new Object[get_filas(query_lista)][29];
		try {
			Statement stmt = new Connexion().conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query_lista);
			
			int i = 0;
			while(rs.next()){
				matriz[i][0] = Boolean.parseBoolean(rs.getString(1).trim());                                         
				matriz[i][1] = rs.getInt(2)+" ";                                                                     //folio 
				matriz[i][2] = "   "+rs.getString(3);                                                                //Nombre Completo
				matriz[i][3] = "   "+rs.getString(4);                                                                //Establecimiento
				matriz[i][4] = Float.parseFloat(rs.getString(5)) == 0 ? "" : Float.parseFloat(rs.getString(5));      //Sueldo 
				matriz[i][5] = Float.parseFloat(rs.getString(6)) == 0 ? "" : Float.parseFloat(rs.getString(6));      //Bono 
				matriz[i][6] = Float.parseFloat(rs.getString(7)) == 0 ? "" : Float.parseFloat(rs.getString(7));      //P.Saldo inicial
				matriz[i][7] = Float.parseFloat(rs.getString(8)) == 0 ? "" : Float.parseFloat(rs.getString(8));      //Descuento Prestamo
				matriz[i][8] = Float.parseFloat(rs.getString(9)) == 0 ? "" : Float.parseFloat(rs.getString(9));      //P.Saldo  Final 
				matriz[i][9] = Float.parseFloat(rs.getString(10)) == 0 ? "" : Float.parseFloat(rs.getString(10));    //Fuente De Sodas 
				matriz[i][10] = Float.parseFloat(rs.getString(11)) == 0 ? "" : Float.parseFloat(rs.getString(11));   //Impuntualidad
				
				matriz[i][11] = Float.parseFloat(rs.getString(12)) == 0 ? "" : Float.parseFloat(rs.getString(12));   //Bono Puntualidad
				matriz[i][12] = Float.parseFloat(rs.getString(13)) == 0 ? "" : Float.parseFloat(rs.getString(13));   //Omision
				matriz[i][13] = Float.parseFloat(rs.getString(14)) == 0 ? "" : Float.parseFloat(rs.getString(14));   //Faltas
				matriz[i][14] = Float.parseFloat(rs.getString(15)) == 0 ? "" : Float.parseFloat(rs.getString(15));   //Inasistencia
				matriz[i][15] = Float.parseFloat(rs.getString(16)) == 0 ? "" : Float.parseFloat(rs.getString(16));   //Bono Inasistencia
				matriz[i][16] = Float.parseFloat(rs.getString(17)) == 0 ? "" : Float.parseFloat(rs.getString(17));
				matriz[i][17] = Float.parseFloat(rs.getString(18)) == 0 ? "" : Float.parseFloat(rs.getString(18));
				matriz[i][18] = Float.parseFloat(rs.getString(19)) == 0 ? "" : Float.parseFloat(rs.getString(19));
				matriz[i][19] = Float.parseFloat(rs.getString(20)) == 0 ? "" : Float.parseFloat(rs.getString(20));
				matriz[i][20] = Float.parseFloat(rs.getString(21)) == 0 ? "" : Float.parseFloat(rs.getString(21));
				
				matriz[i][21] = rs.getString(22);
				matriz[i][22] = Float.parseFloat(rs.getString(23)) == 0 ? "" : Float.parseFloat(rs.getString(23));
				matriz[i][23] = Float.parseFloat(rs.getString(24)) == 0 ? "" : Float.parseFloat(rs.getString(24));
				matriz[i][24] = Float.parseFloat(rs.getString(25)) == 0 ? "" : Float.parseFloat(rs.getString(25));
				matriz[i][25] = Float.parseFloat(rs.getString(26)) == 0 ? "" : Float.parseFloat(rs.getString(26));
				matriz[i][26] = Float.parseFloat(rs.getString(27)) == 0 ? "" : Decimal(Float.parseFloat(rs.getString(27)));
				
				System.out.println( rs.getInt(2)+" a pagar"+rs.getString(27));
				System.out.println( Decimal(Float.parseFloat(rs.getString(27))));
				
				matriz[i][27] = "   "+rs.getString(28);
				matriz[i][28] = "   "+rs.getString(29);
				
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
			
			if(x<0){
				valor=valor-0.5;
			}else{
				return valor + 0.5;
			}
		}
		
		if(decimal >= 0.75 && decimal <= .99){
			if(x<0){
				valor=valor-1;
			}else{
				return valor + 1;
			}
			
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
					System.out.println("La transacción ha sido abortada");
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
		Object[][] matriz = new Object[get_filas(query)][6];
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
				matriz[i][5] = "   "+rs.getString(6);
				
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
	
	public Object[][] tabla_model_clientes(String cltPrv){

		String query_lista = cltPrv.equals("CLIENTES") ? "select folio_cliente,nombre+' '+ap_paterno+' '+ap_materno as empleado,direccion from tb_clientes where status = 1" : "select folio_proveedor,nombre+' '+ap_paterno+' '+ap_materno as empleado,direccion from tb_proveedores where status = 1";
		
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
					System.out.println("La transacción ha sido abortada");
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
		String query = "sp_select_consideracion_checador '"+fecha_inicio+"','"+fecha_final+"','"+Establecimiento+"','"+Departamento+"','"+folios_empleados+"'";
	
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
//			System.out.println(query);
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion [filtro_impuntualidad_a_considerar] \n  en el procedimiento :"+query+"\n SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
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
		
		String query_lista = "exec sp_select_trabajo_de_cortes_2'"+cadena+"','"+grupo_de_concentrado+"';";		
		String[][] matriz = new String[get_filas(query_lista)][21];
		
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
				matriz[i][18] = " "+df.format(rs.getDouble(19));
				matriz[i][19] = " "+rs.getString(20);
				matriz[i][20] = " "+rs.getString(21);
				i++;
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
			System.out.println(e1);
			System.out.println(query_lista);
			JOptionPane.showMessageDialog(null, "Error en BuscarTablaModel  en la funcion tabla_model_trabajo_de_cortes store procedure sp_select_trabajo_de_cortes  "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);

		}
	    return matriz; 
	}
	
	public String[][] tabla_model_cortes_quitados(String cadena, String grupo_de_concentrado){
		
		String query_lista = "exec sp_select_cortes_quitados_en_trabajo_de_corte '"+cadena+"','"+grupo_de_concentrado+"';";		
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
			JOptionPane.showMessageDialog(null, "Error en BuscarTablaModel  en la funcion tabla_model_cortes_quitados store procedure sp_select_cortes_quitados_en_trabajo_de_corte  "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);

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
	
public String[][] tabla_de_ajustes_ticket_ieps(String asignacion){
		
		String query_lista = "select folio,transaccion,convert(varchar(20),fecha,103)as fecha,importe,ieps,status,'false' from IZAGAR_AVIEP_facremtick where  status=1 and asignacion = '"+asignacion+"';";

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

public String[][] tabla_filtro_de_asignaciones_para_corregir_asignacion_ieps(){
	
	String query_lista = "select asignacion, convert(varchar(10),fecha,103)as fecha, sum(ieps) as ieps"
			+ "   from IZAGAR_AVIEP_facremtick  where status=2"
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

public Object[][] tabla_model_empleados_abonos_y_diferencia_de_cortes(){
	String query_lista = "exec sp_select_fiotro_empleados_para_abonos_y_diferencias_de_cortes";
	Object[][] matriz = new Object[get_filas(query_lista)][4];
	try {
		Statement stmt = new Connexion().conexion().createStatement();
		ResultSet rs = stmt.executeQuery(query_lista);
		
		int i = 0;
		while(rs.next()){
			
			matriz[i][0] =  rs.getInt(1)+"";
			matriz[i][1] =  " "+rs.getString(2);
			matriz[i][2] =  " "+rs.getString(3); 
			matriz[i][3] =  rs.getString(4);
			i++;
		}
	} catch (SQLException e1) {
		e1.printStackTrace();
		JOptionPane.showMessageDialog(null, "Error en BuscarTablasModel  en la funcion tabla_model_empleados_abonos_y_diferencia_de_cortes  procedimiento almacenado sp_select_fiotro_empleados_para_abonos_y_diferencias_de_cortes SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen//usuario-icono-eliminar5252-64.png"));
	}
    return matriz; 
}

public Object[][] filtro_recepcion_de_mercancia_y_proovedores(String fecha){
	
	String query_lista = "declare @fecha varchar(20) "
			+ "	set @fecha='"+fecha+"' "
			+ "	select distinct entysal.folio "
			+ "					,entysal.cod_prv "
			+ "					,proveedores.razon_social "
			+ "	from entysal with (nolock) "
			+ "		left outer join proveedores on proveedores.cod_prv=entysal.cod_prv "
			+ "	where transaccion=44 and convert(varchar(20),fecha,103)=convert(varchar(20),@fecha,103) and entysal.status='V'";
	
	Object[][] matriz = new Object[get_filas_izagar(query_lista)][3];
	try {
		Statement stmt = new Connexion().conexion_IZAGAR().createStatement();
		ResultSet rs = stmt.executeQuery(query_lista);
		
		int i = 0;
		while(rs.next()){
			
			matriz[i][0] =  rs.getString(1)+"";
			matriz[i][1] =  " "+rs.getString(2);
			matriz[i][2] =  " "+rs.getString(3); 
			i++;
		}
	} catch (SQLException e1) {
		e1.printStackTrace();
		JOptionPane.showMessageDialog(null, "Error en BuscarTablasModel  en la funcion filtro_recepcion_de_mercancia_y_proovedores SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen//usuario-icono-eliminar5252-64.png"));
	}
    return matriz; 
}
	
public Object[][] lista_recepcion_de_mercancia(String recepcion){
	
	String query_lista = " declare @folio_recepcion varchar(30) "
			+ " set @folio_recepcion= '"+recepcion+"' "
			+ " select  entysal.cod_prod "
			+ "			,productos.descripcion "
			+ "			,convert(varchar(20),entysal.fecha,103)+' '+convert(varchar(20),entysal.fecha,108) as fecha "
			+ "			, entysal.cantidad as cantidad_factura "
			+ "			,0 cantidad_en_resguardo "
			+ " from entysal with (nolock) "
			+ " left outer join productos on productos.cod_prod=entysal.cod_prod "
			+ " where transaccion=44 and entysal.folio=@folio_recepcion and entysal.status='V'";
	
	Object[][] matriz = new Object[get_filas_izagar(query_lista)][5];
	try {
		Statement stmt = new Connexion().conexion_IZAGAR().createStatement();
		ResultSet rs = stmt.executeQuery(query_lista);
		
		int i = 0;
		while(rs.next()){
			
			matriz[i][0] =  rs.getString(1)+"";
			matriz[i][1] =  " "+rs.getString(2);
			matriz[i][2] =  " "+rs.getString(3); 
			
			matriz[i][3] =  " "+rs.getString(4);
			matriz[i][4] =  " "+rs.getString(5); 
			i++;
		}
	} catch (SQLException e1) {
		e1.printStackTrace();
		JOptionPane.showMessageDialog(null, "Error en BuscarTablasModel  en la funcion lista_recepcion_de_mercancia SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen//usuario-icono-eliminar5252-64.png"));
	}
    return matriz; 
}

public Object[][] filtro_de_proovedores_con_recepciones(){
	
	String query_lista = "select distinct cod_prv,folio_recepcion,CONVERT(VARCHAR(20),fecha_de_captura,103)+' '+CONVERT(VARCHAR(20), fecha_de_captura,108) as fecha from tb_productos_en_resguardo_por_recepcion where estatus_recepcion = 'PE'";
	String query_prv = "select razon_social from proveedores where cod_prv = ";
	
	Object[][] matriz = new Object[get_filas(query_lista)][4];
	try {
		Statement stmt = new Connexion().conexion().createStatement();
		ResultSet rs = stmt.executeQuery(query_lista);
		
		Statement stmtIz = new Connexion().conexion_IZAGAR().createStatement();
		ResultSet rsIz = null;
		
		int i = 0;
		while(rs.next()){
			
			matriz[i][0] =  rs.getString(1)+" ";
			
			rsIz = stmtIz.executeQuery(query_prv+rs.getString(1)+"");
			while(rsIz.next()){	matriz[i][1] =  " "+rsIz.getString(1).trim();}
			
			matriz[i][2] =  " "+rs.getString(2);
			matriz[i][3] =  " "+rs.getString(3);
			i++;
		}
	} catch (SQLException e1) {
		e1.printStackTrace();
		JOptionPane.showMessageDialog(null, "Error en BuscarTablasModel  en la funcion filtro_de_proovedores_con_recepciones SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen//usuario-icono-eliminar5252-64.png"));
	}
    return matriz; 
}

public Object[][] recepcion_de_mercancia_en_resguardo(String recepcion){
	
	String query_lista = "declare @recepcion varchar(15) "
			+ "							set @recepcion = '"+recepcion.trim()+"'"
			+ "							select tb_productos_en_resguardo_por_recepcion.cod_prod "
			+ "									, tb_productos_en_resguardo_por_recepcion.cantidad_factura "
			+ "									,tb_productos_en_resguardo_por_recepcion.cantidad_resguardo "
			+ "									,isnull((select sum(tbresg.cantidad_recibida) "
			+ "												from tb_productos_en_resguardo_por_recepcion tbresg "
			+ "												where tbresg.folio_recepcion=tb_productos_en_resguardo_por_recepcion.folio_recepcion "
			+ "												and tbresg.cod_prod = tb_productos_en_resguardo_por_recepcion.cod_prod and tbresg.estatus_pruducto = 'RB'),0) as recibida "
			+ " 					 			,tb_productos_en_resguardo_por_recepcion.cantidad_resguardo-(isnull((select sum(tbresg.cantidad_recibida) "
			+ "																													from tb_productos_en_resguardo_por_recepcion tbresg "
			+ "																													where tbresg.folio_recepcion=tb_productos_en_resguardo_por_recepcion.folio_recepcion "
			+ "																													and tbresg.cod_prod = tb_productos_en_resguardo_por_recepcion.cod_prod and tbresg.estatus_pruducto = 'RB') "
			+ "																													,0)) as pendiente						"
			+ "									, 0 as recibir "
			+ "							from tb_productos_en_resguardo_por_recepcion "
			+ "							where folio_recepcion=@recepcion "
			+ "							and estatus_recepcion = 'PE' "
			+ "						and estatus_pruducto = 'OR'"; 
	
	String query_desc = "select descripcion from productos where cod_prod = '"; 
	
	
	Object[][] matriz = new Object[get_filas(query_lista)][7];
	try {
		Statement stmt = new Connexion().conexion().createStatement();
		ResultSet rs = stmt.executeQuery(query_lista);
		
		Statement stmtIz = new Connexion().conexion_IZAGAR().createStatement();
		ResultSet rsIz = null;
		
		int i = 0;
		while(rs.next()){
			
			matriz[i][0] =  rs.getString(1)+"";
			
			rsIz = stmtIz.executeQuery(query_desc+rs.getString(1)+"'");
			while(rsIz.next()){	matriz[i][1] = " "+rsIz.getString(1);}
			
			matriz[i][2] =  " "+rs.getString(2); 
			matriz[i][3] =  " "+rs.getString(3);
			matriz[i][4] =  " "+rs.getString(4); 
			matriz[i][5] =  " "+rs.getString(5);
			matriz[i][6] =  " "+rs.getString(6);
			
			i++;
		}
	} catch (SQLException e1) {
		e1.printStackTrace();
		JOptionPane.showMessageDialog(null, "Error en BuscarTablasModel  en la funcion recepcion_de_mercancia_en_resguardo SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen//usuario-icono-eliminar5252-64.png"));
	}
    return matriz; 
}

public boolean reporte_de_recepcion_de_mercancia_en_resguardo(String folio_Recepcion){
	
	boolean generado = false;
	
	String query_truncate = "truncate table tb_reporte_de_movimiento_de_mercancia_en_resguardo_temp"; 
	
	String query_select = "select tb_productos_en_resguardo_por_recepcion.folio_recepcion "
			+ "		,CONVERT(VARCHAR(20),tb_productos_en_resguardo_por_recepcion.fecha_de_captura,103)+' '+CONVERT(VARCHAR(20),tb_productos_en_resguardo_por_recepcion.fecha_de_captura,108) AS fecha_de_captura "
			+ "		,tb_productos_en_resguardo_por_recepcion.cod_prv "
			+ "		,tb_productos_en_resguardo_por_recepcion.cod_prod "
			+ "		,tb_productos_en_resguardo_por_recepcion.cantidad_factura "
			+ "		,tb_productos_en_resguardo_por_recepcion.cantidad_resguardo "
			+ "		,tb_productos_en_resguardo_por_recepcion.cantidad_recibida "
			+ "		,CONVERT(VARCHAR(20),tb_productos_en_resguardo_por_recepcion.fecha_de_recepcion,103)+' '+CONVERT(VARCHAR(20),tb_productos_en_resguardo_por_recepcion.fecha_de_recepcion,108) AS fecha_de_recepcion "
			+ "		,tb_empleado.nombre+' '+tb_empleado.ap_paterno+' '+tb_empleado.ap_materno as empleado "
			+ "		,CASE WHEN (tb_productos_en_resguardo_por_recepcion.estatus_recepcion='PE') THEN 'PENDIENTE' "
			+ "				WHEN (tb_productos_en_resguardo_por_recepcion.estatus_recepcion='CA') THEN 'CANCELADO' "
			+ "				ELSE 'COMPLETO' "
			+ "			END AS estatus_recepcion "
			+ "		,CASE WHEN (tb_productos_en_resguardo_por_recepcion.estatus_pruducto='OR') THEN 'ORIGINAL' "
			+ "				WHEN (tb_productos_en_resguardo_por_recepcion.estatus_pruducto='CA') THEN 'CANCELADO' "
			+ "				ELSE 'RECIBIDO' "
			+ "				END AS estatus_pruducto "
			+ "from tb_productos_en_resguardo_por_recepcion "
			+ "INNER JOIN tb_empleado on tb_empleado.folio = tb_productos_en_resguardo_por_recepcion.folio_usuario_capturo  "
			+folio_Recepcion
			+ "  order by folio_recepcion,cod_prod,estatus_pruducto,fecha_de_recepcion"; 
	
	String query_insert ="exec sp_insert_movimientos_de_mercacion_en_resguardo ?,?,?,?,?,?,?,?,?,?,?,?,?";
	
	
	
	Object[][] matriz = new Object[get_filas(query_select)][13];
	try {
		
//		truncate -----------------------------------------------------
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		
		con.setAutoCommit(false);
		pstmt = con.prepareStatement(query_truncate);
		pstmt.executeUpdate();
//		--------------------------------------------------------------
		
//		select scoi -------------------------------------------------------------------------------------
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(query_select);
//		-------------------------------------------------------------------------------------------------
		
		Statement stmtIz = new Connexion().conexion_IZAGAR().createStatement();
		ResultSet rsIz = null;
		
		int i = 0;
		while(rs.next()){
			
			matriz[i][0] =  rs.getString(1).trim()+"";
			matriz[i][1] =  rs.getString(2).trim();
			
			
			matriz[i][2] =  rs.getString(3);
			rsIz = stmtIz.executeQuery("select razon_social from proveedores where cod_prv = '"+rs.getString(3)+"'");
			while(rsIz.next()){	matriz[i][3] =  " "+rsIz.getString(1).trim();}
			
			
			matriz[i][4] =  rs.getString(4);
			rsIz = stmtIz.executeQuery("select descripcion from productos where cod_prod = '"+rs.getString(4)+"'");
			while(rsIz.next()){	matriz[i][5] =  " "+rsIz.getString(1).trim();}
			
			matriz[i][6]  =  " "+rs.getString(5).trim();
			matriz[i][7]  =  " "+rs.getString(6).trim(); 
			matriz[i][8]  =  " "+rs.getString(7).trim();
			matriz[i][9]  =  " "+rs.getString(8).trim(); 
			matriz[i][10] =  " "+rs.getString(9).trim();
			matriz[i][11] =  " "+rs.getString(10).trim(); 
			matriz[i][12] =  " "+rs.getString(11);
			
			i++;
		}
		
		con.setAutoCommit(false);
		pstmt = con.prepareStatement(query_insert);
		for(int a=0; a<matriz.length; a++){
//			for(int b=0; b<13; b++){
				
//				if(b>=6 && b<=8){
//					pstmt.setFloat(a+1, Float.valueOf(matriz[a][b].toString()));
//				}else{
//					pstmt.setString(a+1, matriz[a][b].toString());
//				}
			
			
				pstmt.setString(1, matriz[a][0].toString());
			    pstmt.setString(2, matriz[a][1].toString());  
		        pstmt.setString(3, matriz[a][2].toString());
		        pstmt.setString(4, matriz[a][3].toString());  
		        pstmt.setString(5, matriz[a][4].toString());
			    pstmt.setString(6, matriz[a][5].toString());  
			    
		        pstmt.setFloat(7, Float.valueOf(matriz[a][6].toString()));
		        pstmt.setFloat(8, Float.valueOf(matriz[a][7].toString()));  
		        pstmt.setFloat(9, Float.valueOf(matriz[a][8].toString()));
		        
			    pstmt.setString(10, matriz[a][9].toString());  
		        pstmt.setString(11, matriz[a][10].toString());
		        pstmt.setString(12, matriz[a][11].toString());  
		        
		        pstmt.setString(13, matriz[a][12].toString());
		        
				
				pstmt.executeUpdate();
				
//			}
		}
		con.commit();
		
		generado = true;
		
	} catch (SQLException e1) {
		
		generado=false;
		e1.printStackTrace();
		JOptionPane.showMessageDialog(null, "Error en BuscarTablasModel  en la funcion recepcion_de_mercancia_en_resguardo SQLException: \n"+query_insert+"\n"+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen//usuario-icono-eliminar5252-64.png"));
	}
    return generado; 
}

public boolean Existr_Recepcion_De_Resguardo(String recepcion){
	
	boolean existe = false;
	
	String query_lista = " if exists(select top 1 * from tb_productos_en_resguardo_por_recepcion where folio_recepcion = '"+recepcion+"')"
						+ "	begin select 'true' as existe end "
						+ "else "
						+ "	begin select 'false' as existe end ";
	try {
		Statement stmt = new Connexion().conexion().createStatement();
		ResultSet rs = stmt.executeQuery(query_lista);
		
		while(rs.next()){
			existe =  rs.getBoolean(1); 
		}
	} catch (SQLException e1) {
		existe = false;
		e1.printStackTrace();
		JOptionPane.showMessageDialog(null, "Error en BuscarTablasModel  en la funcion Existr_Recepcion_De_Resguardo SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen//usuario-icono-eliminar5252-64.png"));
	}
    return existe; 
}

public Object[][] configuracion_de_polizas(){
	
	String query_lista = "select * from tb_configuracion_de_polizas order by tipo"; 
	
	
	Object[][] matriz = new Object[get_filas(query_lista)][5];
	try {
		Statement stmt = new Connexion().conexion().createStatement();
		ResultSet rs = stmt.executeQuery(query_lista);
		
		
		int i = 0;
		while(rs.next()){
			
			matriz[i][0] =  rs.getString(1)+"";
			matriz[i][1] =  " "+rs.getString(2); 
			matriz[i][2] =  " "+rs.getString(3);
			matriz[i][3] =  " "+rs.getString(4); 
			matriz[i][4] =  " "+rs.getString(5);
			
			i++;
		}
	} catch (SQLException e1) {
		e1.printStackTrace();
		JOptionPane.showMessageDialog(null, "Error en BuscarTablasModel  en la funcion recepcion_de_mercancia_en_resguardo SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen//usuario-icono-eliminar5252-64.png"));
	}
    return matriz; 
}

public Object[][] folios_de_polizas(){
	
	String query_lista = "SELECT * FROM tb_folios_polizas"; 
	
	
	Object[][] matriz = new Object[get_filas(query_lista)][3];
	try {
		Statement stmt = new Connexion().conexion().createStatement();
		ResultSet rs = stmt.executeQuery(query_lista);
		
		
		int i = 0;
		while(rs.next()){
			
			matriz[i][0] =  rs.getString(1)+"";
			matriz[i][1] =  " "+rs.getString(2); 
			matriz[i][2] =  " "+rs.getString(3);
			
			i++;
		}
	} catch (SQLException e1) {
		e1.printStackTrace();
		JOptionPane.showMessageDialog(null, "Error en BuscarTablasModel  en la funcion recepcion_de_mercancia_en_resguardo SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen//usuario-icono-eliminar5252-64.png"));
	}
    return matriz; 
}

public boolean Existe_configuracion_de_poliza(String poliza){
	
	boolean existe = false;
	
	String query_lista = "	if exists (select * from tb_configuracion_de_polizas where nombre = '"+poliza+"') "
			+ "					begin		select 'true' as existe	end "
			+ "				 else "
			+ "					begin		select 'false' as existe end ";
	try {
		Statement stmt = new Connexion().conexion().createStatement();
		ResultSet rs = stmt.executeQuery(query_lista);
		
		while(rs.next()){
			existe =  rs.getBoolean(1); 
		}
	} catch (SQLException e1) {
		existe = false;
		e1.printStackTrace();
		JOptionPane.showMessageDialog(null, "Error en BuscarTablasModel  en la funcion Existr_Recepcion_De_Resguardo SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen//usuario-icono-eliminar5252-64.png"));
	}
    return existe; 
}

public String folio_consecutivo_de_poliza(String fecha, String tipo){
	
	String query_lista = "exec sp_select_folio_de_poliza_siguiente '"+fecha+"','"+tipo+"'"; 
	
	String folio = "";
	try {
		Statement stmt = new Connexion().conexion().createStatement();
		ResultSet rs = stmt.executeQuery(query_lista);
		
		while(rs.next()){
			folio =  rs.getString(1)+"";
		}
	} catch (SQLException e1) {
		e1.printStackTrace();
		JOptionPane.showMessageDialog(null, "Error en BuscarTablasModel  en la funcion [folio_consecutivo_de_poliza] SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen//usuario-icono-eliminar5252-64.png"));
	}
    return folio; 
}

public Object[][] tabla_model_proveedores(){
//	cambiar consulta
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

public String establecimientoCuenta(String cuenta){
	
	String query_lista =  " declare @folio_establecimiento int "
						+ " set @folio_establecimiento = (select establecimiento_id "
						+ "				 					from tb_cuentas_contables "
						+ "									where folio_cuenta_contable = '"+cuenta+"') "
						+ " if(@folio_establecimiento=0) "
						+ " 	begin	select 'MULTIPLE' as establecimiento 	end "
						+ " else "
						+ " 	begin "
						+ "			if(@folio_establecimiento=999) "
						+ "	 			begin	select 'NO APLICA' as establecimiento 	end "
						+ "			 else "
						+ "	 			begin	select nombre as establecimieto from tb_establecimiento where folio = @folio_establecimiento end "
						+ "		end "; 
	System.out.println(query_lista);
	String folio = "";
	try {
		Statement stmt = new Connexion().conexion().createStatement();
		ResultSet rs = stmt.executeQuery(query_lista);
		
		while(rs.next()){
			folio =  rs.getString(1)+"";
			System.out.println(folio);
		}
	} catch (SQLException e1) {
		e1.printStackTrace();
		JOptionPane.showMessageDialog(null, "Error en BuscarTablasModel  en la funcion [folio_consecutivo_de_poliza] SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen//usuario-icono-eliminar5252-64.png"));
	}
    return folio; 
}

public String folio_consecutivo_cheque_de_poliza(String cuentaBanco){
	
	String query_lista = "SELECT folio_consecutivo+1 FROM tb_bancos_contabilidad where cuenta = '"+cuentaBanco+"'"; 
	
	String folio = "";
	try {
		Statement stmt = new Connexion().conexion().createStatement();
		ResultSet rs = stmt.executeQuery(query_lista);
		
		while(rs.next()){
			folio =  rs.getString(1);
		}
	} catch (SQLException e1) {
		e1.printStackTrace();
		JOptionPane.showMessageDialog(null, "Error en BuscarTablasModel  en la funcion [folio_consecutivo_cheque_de_poliza] SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen//usuario-icono-eliminar5252-64.png"));
	}
    return folio; 
}

public String folio_ordern_de_pago_en_efectivo(){
	
	String query_lista = "select (folio+1) from tb_folios where folio_transaccion = 16"; 
	
	String folio = "";
	try {
		Statement stmt = new Connexion().conexion().createStatement();
		ResultSet rs = stmt.executeQuery(query_lista);
		
		while(rs.next()){
			folio =  rs.getString(1)+"";
		}
	} catch (SQLException e1) {
		e1.printStackTrace();
		JOptionPane.showMessageDialog(null, "Error en BuscarTablasModel  en la funcion [folio_consecutivo_de_poliza] SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen//usuario-icono-eliminar5252-64.png"));
	}
    return folio; 
}

public String checar_Pedido_De_Monedas_Cajero(){	
	Obj_Usuario usuario = new Obj_Usuario().LeerSession();
	String query_lista = " select top 1 status_pedido from tb_pedido_de_monedas where folio_cajera = "+usuario.getFolio()+" order by fecha_pedido_cajera desc ";
	
	String tipoPedido = "";
	try {
		Statement stmt = new Connexion().conexion().createStatement();
		ResultSet rs = stmt.executeQuery(query_lista);
		
		
		while(rs.next()){
			tipoPedido =  rs.getString(1);
		}
	} catch (SQLException e1) {
		e1.printStackTrace();
		JOptionPane.showMessageDialog(null, "Error en BuscarTablasModel  en la funcion checar_Pedido_De_Monedas_Cajero() SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen//usuario-icono-eliminar5252-64.png"));
	}
    return tipoPedido; 
}

public String tipoDeUsuarioParaPedidoDeMonedas(){
	
	Obj_Usuario usuario = new Obj_Usuario().LeerSession();
	String query_lista = "exec sp_select_tipo_de_usuario_para_filtro_de_pedido__de_monedas "+usuario.getFolio(); 
	
	
	String tipoPedido = "";
	try {
		Statement stmt = new Connexion().conexion().createStatement();
		ResultSet rs = stmt.executeQuery(query_lista);
		
		
		while(rs.next()){
			tipoPedido =  rs.getString(1);
		}
	} catch (SQLException e1) {
		e1.printStackTrace();
		JOptionPane.showMessageDialog(null, "Error en BuscarTablasModel  en la funcion tipoDeUsuarioParaPedidoDeMonedas() SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen//usuario-icono-eliminar5252-64.png"));
	}
	System.out.println(tipoPedido);
    return tipoPedido; 
}

public String[][] listaDePedidoDeMonedas(String status){
	
	String query_lista = "exec sp_select_filtro_de_pedidos_de_monedas "+status; 
	
	String[][] matriz = new String[get_filas(query_lista)][7];
	try {
		Statement stmt = new Connexion().conexion().createStatement();
		ResultSet rs = stmt.executeQuery(query_lista);
		
		int i = 0;
		while(rs.next()){
			matriz[i][0] =  rs.getString(1);
			matriz[i][1] =  " "+rs.getString(2); 
			matriz[i][2] =  " "+rs.getString(3); 
			matriz[i][3] =  " "+rs.getString(4); 
			matriz[i][4] =  " "+rs.getString(5); 
			matriz[i][5] =  " "+rs.getString(6); 
			matriz[i][6] =  " "+df.format(rs.getDouble(7)); 
			
			i++;
		}
	} catch (SQLException e1) {
		e1.printStackTrace();
		JOptionPane.showMessageDialog(null, "Error en BuscarTablasModel  en la funcion listaDePedidoDeMponedas() SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen//usuario-icono-eliminar5252-64.png"));
	}
    return matriz; 
}

public String[] observacionesPedidoDeMonedas(int folio_empleado){
	
	String query_lista = "exec sp_select_observaciones_de_pedido_de_monedas_por_usuario "+ (folio_empleado); 
	System.out.println(query_lista);
	String[] matriz = new String[3];
	try {
		Statement stmt = new Connexion().conexion().createStatement();
		ResultSet rs = stmt.executeQuery(query_lista);
		
		
		while(rs.next()){
			
			matriz[0] = "Observacion Cajero(a): "+rs.getString(1);
			matriz[1] = "Observacion Cortes: "+rs.getString(2); 
			matriz[2] = "Observacion Encargado: "+rs.getString(3);
		}
	} catch (SQLException e1) {
		e1.printStackTrace();
		JOptionPane.showMessageDialog(null, "Error en BuscarTablasModel  en la funcion recepcion_de_mercancia_en_resguardo SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen//usuario-icono-eliminar5252-64.png"));
	}
    return matriz; 
}

@SuppressWarnings("resource")
public String[][] filtro_empleado_finiquito(String coneccion){
	
	String query_lista = "";
	
	if(coneccion.equals("SCOI")){
		query_lista = "select empleado.folio"
				+ " ,empleado.nombre+' '+empleado.ap_paterno+' '+empleado.ap_materno as empleado"
				+ " ,estab.nombre as establecimiento"
				+ " ,puesto.nombre as puesto"
				+ " ,estado.nombre as status"
				+ " from tb_empleado empleado"
				+ " inner join tb_establecimiento estab on estab.folio = empleado.establecimiento_id"
				+ " inner join tb_puesto puesto on puesto.folio = empleado.puesto_id "
				+ " inner join tb_status_de_colaboradores estado on estado.folio = empleado.status "
				+ " where empleado.status = 7"; 
//				+ " where empleado.status not in (4,5)"; 
	}else{
		query_lista = "select ltrim(rtrim(empleados.empleado)) as folio"
				+ " ,ltrim(rtrim(empleados.nombre))+' '+ltrim(rtrim(empleados.ap_paterno))+' '+ltrim(rtrim(empleados.ap_materno)) as empleado"
				+ " ,ltrim(rtrim(establecimientos.nombre)) as establecimiento"
				+ " ,ltrim(rtrim(puestos.nombre)) as puesto"
				+ " ,case when empleados.status_empleado = '1' then 'VIGENTE' end as status"
				+ " from empleados"
				+ " inner join establecimientos on establecimientos.cod_estab = empleados.cod_estab"
				+ " inner join puestos on puestos.puesto = empleados.puesto"
				+ " where empleados.status_empleado ='1'"; 
	}
	
	
	String[][] matriz = new String[coneccion.equals("SCOI")?get_filas(query_lista):get_filas_izagar(query_lista)][5];
	try {
		Statement stmt =coneccion.equals("SCOI")?new Connexion().conexion().createStatement():new Connexion().conexion_IZAGAR().createStatement();
		ResultSet rs = stmt.executeQuery(query_lista);
		
		int i=0;
		while(rs.next()){
			
			matriz[i][0] = rs.getString(1);
			matriz[i][1] = rs.getString(2); 
			matriz[i][2] = rs.getString(3);
			matriz[i][3] = rs.getString(4);
			matriz[i][4] = rs.getString(5); 
			
			i++;
		}
	} catch (SQLException e1) {
		e1.printStackTrace();
		JOptionPane.showMessageDialog(null, "Error en BuscarTablasModel  en la funcion filtro_empleado_finiquito SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen//usuario-icono-eliminar5252-64.png"));
	}
    return matriz; 
}

public String[][] filtro_de_finiquito_no_autorizados(){
	
	String query_lista = "exec sp_select_finiquitos_no_autorizados"; 
	
	String[][] matriz = new String[get_filas(query_lista)][7];
	try {
		Statement stmt =new Connexion().conexion().createStatement();
		ResultSet rs = stmt.executeQuery(query_lista);
		
		int i=0;
		while(rs.next()){
			
			matriz[i][0] = rs.getString(1);
			matriz[i][1] = rs.getString(2); 
			matriz[i][2] = rs.getString(3);
			matriz[i][3] = rs.getString(4);
			matriz[i][4] = rs.getString(5); 
			matriz[i][5] = rs.getString(6); 
			matriz[i][6] = rs.getString(7); 
			
			i++;
		}
	} catch (SQLException e1) {
		e1.printStackTrace();
		JOptionPane.showMessageDialog(null, "Error en BuscarTablasModel  en la funcion filtro_de_finiquito_no_autorizados SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen//usuario-icono-eliminar5252-64.png"));
	}
    return matriz; 
}

public Object[][] productoEnTranferencia(String cod_prod, String establecimiento){
	
	String query_lista = "declare @producto varchar(10), @establecimiento_destino varchar(5) "
			+ " set @producto = '"+cod_prod+"' "
			+ " set @establecimiento_destino = '"+establecimiento+"' "
			+ " if (select 'S' from prodestab "
			+ "		inner join productos on productos.cod_prod = prodestab.cod_prod "
			+ "		where prodestab.cod_prod = @producto and prodestab.cod_estab = @establecimiento_destino)='S' "
			+ "	begin "
			+ "			select '' "
			+ "				,isnull(prodestab.cod_prod ,'') "
			+ "				,isnull(productos.descripcion ,'No Existe En El Establecimiento') "
			+ "				,1.00 as cantidad "
			+ "				,'PZ' as unidad "
			+ "				,convert(decimal(10,2),isnull(prodestab.costo_promedio,0)) as costo_unitario "
			+ "				,convert(decimal(10,2),isnull(prodestab.costo_promedio,0)*1) as importe "
			+ "				,convert(decimal(10,2),(isnull(prodestab.costo_promedio,0)*1)*(isnull(iva_interior,0)/100)) as iva "
			+ "				,convert(decimal(10,2),isnull(prodestab.costo_promedio,0)*1) as costo_total "
			+ "				,convert(decimal(10,2),(isnull(prodestab.costo_promedio,0)*1))+convert(decimal(10,2),(isnull(prodestab.costo_promedio,0)*1)*(isnull(iva_interior,0)/100)) as importe_total "
			+ "			from prodestab "
			+ "			inner join productos on productos.cod_prod = prodestab.cod_prod "
			+ "			where prodestab.cod_prod = @producto and prodestab.cod_estab = @establecimiento_destino"
			+ "	end "
			+ "else "
			+ "	begin "
			+ "			select '',0,'No Existe En El Establecimiento',0,'PZ',0,0,0,0,0 "
			+ "	end";  
	
	System.out.println(query_lista);
	String[][] matriz = new String[1][10];
	
	try {
		Statement stmt = new Connexion().conexion_IZAGAR().createStatement();
		ResultSet rs = stmt.executeQuery(query_lista);
		
		while(rs.next()){
			
			matriz[0][0] = rs.getString(1);
			matriz[0][1] = rs.getString(2); 
			matriz[0][2] = rs.getString(3);
			matriz[0][3] = rs.getString(4);
			matriz[0][4] = rs.getString(5); 
			matriz[0][5] = rs.getString(6);
			matriz[0][6] = rs.getString(7);
			matriz[0][7] = rs.getString(8); 
			matriz[0][8] = rs.getString(9);
			matriz[0][9] = rs.getString(10);
		}
	} catch (SQLException e1) {
		e1.printStackTrace();
		JOptionPane.showMessageDialog(null, "Error en BuscarTablasModel  en la funcion recepcion_de_mercancia_en_resguardo SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen//usuario-icono-eliminar5252-64.png"));
	}
    return matriz; 
}

public String[][] listaDeMetas_Mensuales_Registradas(){
	
	String query_lista = "  select meta.folio  "
			+ "		,meta.anio "
			+ "		,case when (meta.mes)= 1 then 'ENERO' "
			+ "					when (meta.mes)= 2 then 'FEBRERO' "
			+ "					when (meta.mes)= 3 then 'MARZO' "
			+ "					when (meta.mes)= 4 then 'ABRIL' "
			+ "					when (meta.mes)= 5 then 'MAYO' "
			+ "					when (meta.mes)= 6 then 'JUNIO' "
			+ "					when (meta.mes)= 7 then 'JULIO' "
			+ "					when (meta.mes)= 8 then 'AGOSTO' "
			+ "					when (meta.mes)= 9 then 'SEPTIEMBRE' "
			+ "					when (meta.mes)= 10 then 'OCTUBRE' "
			+ "					when (meta.mes)= 11 then 'NOVIEMBRE' "
			+ "					when (meta.mes)= 12 then 'DICIEMBRE' "
			+ "				else 'Selecciona Un Establecimiento' end as mes  "
			+ "		,isnull(estab.nombre,'Sin Establecimiento') as establecimiento "
			+ "		,usuario.nombre+' '+usuario.ap_paterno+' '+usuario.ap_materno as usuario "
			+ "		,meta.meta_del_mes  "
			+ "		,convert(varchar(20),meta.fecha_guardado  ,103) as fecha_guardado "
			+ "		,meta.status "
			+ "		,case when (meta.fecha_cancelado)='01/01/1900'then '' "
			+ "		else convert(varchar(20),meta.fecha_cancelado,103) end fecha_cancelacion "
			+ "		,isnull(usuario_modif.nombre+' '+usuario_modif.ap_paterno+' '+usuario_modif.ap_materno,'') as usuario_modifico "
			+ "from tb_alimentacion_de_meta_mensual_de_venta as meta "
			+ "left outer join tb_establecimiento estab on estab.folio = meta.cod_estab "
			+ "inner join tb_empleado usuario on usuario.folio = meta.usuario "
			+ "left outer join tb_empleado usuario_modif on usuario_modif.folio = meta.usuario_cancelo "; 
	
	System.out.println(query_lista);
	
	String[][] matriz = new String[get_filas(query_lista)][10];
	try {
		Statement stmt = new Connexion().conexion().createStatement();
		ResultSet rs = stmt.executeQuery(query_lista);
		
		int i = 0;
		while(rs.next()){
			matriz[i][0] =  rs.getString(1);
			matriz[i][1] =  rs.getString(2); 
			matriz[i][2] =  rs.getString(3); 
			matriz[i][3] =  rs.getString(4); 
			matriz[i][4] =  rs.getString(5); 
			matriz[i][5] =  rs.getString(6); 
			matriz[i][6] =  rs.getString(7); 
			matriz[i][7] =  rs.getString(8); 
			matriz[i][8] =  rs.getString(9); 
			matriz[i][9] =  rs.getString(10); 
			
			i++;
		}
	} catch (SQLException e1) {
		e1.printStackTrace();
		JOptionPane.showMessageDialog(null, "Error en BuscarTablasModel  en la funcion listaDeMetas_Mensuales_Registradas() SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen//usuario-icono-eliminar5252-64.png"));
	}
    return matriz; 
}

public String[][] lista_de_configuraciones_de_meta_mensual_de_ventas(){
	
	String query_lista = " select conf_meta_mens.folio "
						+ " 		,conf_meta_mens.nombre "
						+ " 		,conf_meta_mens.folio_estableciminetos "
						+ " 		,tipo_reporte.nombre as tipo_de_reporte "
						+ " 		,conf_meta_mens.filtro_productos "
						+ " 		,conf_meta_mens.filtro_clase "
						+ " 		,conf_meta_mens.filtro_categoria "
						+ " 		,conf_meta_mens.filtro_familia "
						+ " 		,conf_meta_mens.filtro_linea "
						+ " 		,conf_meta_mens.filtro_talla "
						+ " 		,conf_meta_mens.status "
						+ " 		,emp_us.nombre+' '+emp_us.ap_paterno+' '+emp_us.ap_materno as usuario "
						+ " 		,convert(varchar(20),conf_meta_mens.fecha_guardado,103)+' '+convert(varchar(20),conf_meta_mens.fecha_guardado,108) AS fecha_guardado "
						+ " 		,isnull(emp_us_canc.nombre+' '+emp_us_canc.ap_paterno+' '+emp_us_canc.ap_materno,'') as usuario_cancelo "
						+ " 		,case when (convert(varchar(20),conf_meta_mens.fecha_cancelo,103)='01/01/1900') then '' "
						+ "				else convert(varchar(20),conf_meta_mens.fecha_cancelo,103)+' '+convert(varchar(20),conf_meta_mens.fecha_cancelo,108) end AS fecha_cancelo "
						+ " from tb_configuracion_de_meta_mensual_de_ventas conf_meta_mens "
						+ " inner join tb_tipo_de_reporte_de_meta__mensual_de_venta tipo_reporte on tipo_reporte.folio = conf_meta_mens.tipo_de_reporte "
						+ " inner join tb_empleado emp_us on emp_us.folio = conf_meta_mens.usuario "
						+ " left outer join tb_empleado emp_us_canc on emp_us_canc.folio = conf_meta_mens.usuario_cancelo "; 
	
	System.out.println(query_lista);
	
	String[][] matriz = new String[get_filas(query_lista)][15];
	try {
		Statement stmt = new Connexion().conexion().createStatement();
		ResultSet rs = stmt.executeQuery(query_lista);
		
		int i = 0;
		while(rs.next()){
			matriz[i][0] =  rs.getString(1);
			matriz[i][1] =  rs.getString(2); 
			matriz[i][2] =  rs.getString(3); 
			matriz[i][3] =  rs.getString(4); 
			matriz[i][4] =  rs.getString(5); 
			matriz[i][5] =  rs.getString(6); 
			matriz[i][6] =  rs.getString(7); 
			matriz[i][7] =  rs.getString(8); 
			matriz[i][8] =  rs.getString(9); 
			matriz[i][9] =  rs.getString(10); 
			matriz[i][10] =  rs.getString(11); 
			matriz[i][11] =  rs.getString(12); 
			matriz[i][12] =  rs.getString(13);  
			matriz[i][13] =  rs.getString(14); 
			matriz[i][14] =  rs.getString(15); 
			
			i++;
		}
	} catch (SQLException e1) {
		e1.printStackTrace();
		JOptionPane.showMessageDialog(null, "Error en BuscarTablasModel  en la funcion listaDeMetas_Mensuales_Registradas() SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen//usuario-icono-eliminar5252-64.png"));
	}
    return matriz; 
}

public Object[][] tabla_concentrados_con_movimiento_de_ahorros(){
	
	String query_lista = "exec sp_select_trabajos_de_corte_con_ahorro_de_clientes";
	
	Object[][] matriz = new Object[get_filas(query_lista)][4];
	try {
		Statement stmt = new Connexion().conexion().createStatement();
		ResultSet rs = stmt.executeQuery(query_lista);
		
		int i = 0;
		while(rs.next()){
			matriz[i][0] = rs.getString(1)+"  ";
			matriz[i][1] = rs.getString(2);
			matriz[i][2] = rs.getString(3);
			matriz[i][3] = rs.getString(4);
			i++;
		}

	} catch (SQLException e1) {
		e1.printStackTrace();
		JOptionPane.showMessageDialog(null, "Error en BuscarTablaModel  en la funcion tabla_establecimientos_para_concentrado  "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);

	}
    return matriz; 
}

public Object[][] filtro_de_perfiles_de_puestos(int folio_empledo){
	
	String query_lista = "exec sp_select_filtro_de_perfiles_de_puestos "+folio_empledo;
	
	Object[][] matriz = new Object[get_filas(query_lista)][5];
	try {
		Statement stmt = new Connexion().conexion().createStatement();
		ResultSet rs = stmt.executeQuery(query_lista);
		
		int i = 0;
		while(rs.next()){
			matriz[i][0] = rs.getString(1)+"  ";
			matriz[i][1] = rs.getString(2);
			matriz[i][2] = rs.getString(3);
			matriz[i][3] = rs.getString(4);
			matriz[i][4] = rs.getString(5);
			i++;
		}

	} catch (SQLException e1) {
		e1.printStackTrace();
		JOptionPane.showMessageDialog(null, "Error en BuscarTablaModel  en la funcion filtro_de_perfiles_de_puestos: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);

	}
    return matriz; 
}

public Object[][] Buscar_Pedido(String folio_pedido,String consulta){
	
	String query_lista = "exec sp_select_consulta_de_pedido '"+folio_pedido+"','"+consulta+"'";
	
	Object[][] matriz = new Object[get_filas(query_lista)][9];
	try {
		Statement stmt = new Connexion().conexion().createStatement();
		ResultSet rs = stmt.executeQuery(query_lista);
		
		int i = 0;
		while(rs.next()){
			matriz[i][0] = rs.getString(1)+"  ";
			matriz[i][1] = rs.getString(2);
			matriz[i][2] = rs.getString(3);
			matriz[i][3] = rs.getString(4);
			matriz[i][4] = rs.getString(5);
			matriz[i][5] = rs.getString(6);
			matriz[i][6] = rs.getString(7);
			matriz[i][7] = rs.getString(8);
			matriz[i][8] = rs.getString(9);
			i++;
		}

	} catch (SQLException e1) {
		e1.printStackTrace();
		JOptionPane.showMessageDialog(null, "Error en BuscarTablaModel  en la funcion Buscar_Pedido: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);

	}
    return matriz; 
}

public Object[][] tabla_model_horarios_de_entrega_de_pedidos(){
	String query_lista = "exec sp_select_horarios_base_de_entrega_de_pedidos";
	Object[][] matriz = new Object[get_filas(query_lista)][6];
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
			i++;
		}

	} catch (SQLException e1) {
		e1.printStackTrace();
	}
    return matriz; 
}

public String[] tipos_de_falta(){
	String query_lista = "SELECT nombre AS tipo_de_falta FROM tb_tipos_de_falta where status = 'V' order by nombre";
	String[] matriz = new String[get_filas(query_lista)];
	
	Connection con = new Connexion().conexion();
	try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query_lista);
			
			int i = 0;
			while(rs.next()){
				matriz[i ] = rs.getString(1);
				i++;
			}

	} catch (SQLException e1) {
		e1.printStackTrace();
		JOptionPane.showMessageDialog(null, "Error en BuscarTablaModel  en la funcion tipos_de_falta() "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
	}
    return matriz; 
}

public Object[][] preguntas(int folio_cuestionario,int cantidad_de_columnas){
	String query_lista = "exec sp_preguntas_en_cuestionario "+folio_cuestionario;
	Object[][] matriz = new Object[get_filas(query_lista)][cantidad_de_columnas];
	try {
		Statement stmt = new Connexion().conexion().createStatement();
		ResultSet rs = stmt.executeQuery(query_lista);
		
		int i = 0;
		while(rs.next()){
			matriz[i][0] = rs.getString(1);
			matriz[i][1] = rs.getString(2);
			for(int j=2; j<cantidad_de_columnas; j++){
				matriz[i][j] = false;//rs.getBoolean(j+1);
			}
			i++;
		}

	} catch (SQLException e1) {
		e1.printStackTrace();
	}
    return matriz; 
}

}




	
