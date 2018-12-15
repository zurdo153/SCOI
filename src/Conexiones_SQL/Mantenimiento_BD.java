package Conexiones_SQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JOptionPane;

 
public class Mantenimiento_BD {
	//se declara la funcion que sera carga en el objeto
	public boolean reducir_log(){
		String query = "exec mantenimiento_base_datos_reducir_log";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
			
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.execute();
 			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
			if (con != null){
				try {
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				} catch(SQLException ex) {
					System.out.println(ex.getMessage());
				}
			} 
			return false;
			
		}finally{
			try {
				pstmt.close();
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	public boolean reducir_log_base_de_datos_BMS_principal(){
		String query = "exec mantenimiento_base_datos_reducir_log_BMS_principal";
		Connection con = new Connexion().conexion_IZAGAR();
		PreparedStatement pstmt = null;
			
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.execute();
 			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
			if (con != null){
				try {
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				} catch(SQLException ex) {
					System.out.println(ex.getMessage());
				}
			} 
			return false;
			
		}finally{
			try {
				pstmt.close();
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	public boolean reducir_log_base_de_datos_Ventas(){
		String query = "exec z_procedimiento_para_reducir_log_de_transacciones";
		Connection con = new Connexion().conexion_ventas();
		PreparedStatement pstmt = null;
			
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.execute();
 			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
			if (con != null){
				try {
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				} catch(SQLException ex) {
					System.out.println(ex.getMessage());
				}
			} 
			return false;
			
		}finally{
			try {
				pstmt.close();
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	public boolean agregar_submenus_nuevos(String nombre_submenu, int menu_id, int menuPrincipal){
	
		String query = "exec sp_insert_submenu_nuevo '"+nombre_submenu+"',"+menu_id+","+menuPrincipal;
		Connection con = new Connexion().conexion();
     	PreparedStatement pstmt = null;
			
		try {
				    	
			con.setAutoCommit(false);
		    pstmt = con.prepareStatement(query);
			pstmt.execute();
 			con.commit();
 			JOptionPane.showMessageDialog(null,"Se Guardo Correctamente el Submenu: "+nombre_submenu+"  y se guardo en los Usuarios No Olvides agregar en el Cat_Usuarios  new Obj_CheckBoxNode(SUBMENUS X[POSICION NUEVO SUBMENU], false)","Aviso",JOptionPane.INFORMATION_MESSAGE);
 		 				
		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
			if (con != null){
				try {
					System.out.println("La transacción agregar_submenus_nuevos ha sido abortada");
					
					con.rollback();
				JOptionPane.showMessageDialog(null,"La transacción agregar_submenus_nuevos ha sido abortada ,procedimiento almacenado sp_insert_submenu_nuevo: SQLException: "+e.getMessage(),"Error al Guardar",JOptionPane.ERROR_MESSAGE);
		
				} catch(SQLException ex) {
					System.out.println(ex.getMessage());
				}
			} 
			return false;
			
		}finally{
			try {
				pstmt.close();
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	
	
}
