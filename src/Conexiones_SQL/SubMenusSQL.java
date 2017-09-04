package Conexiones_SQL;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JOptionPane;

import Obj_Administracion_del_Sistema.Obj_SubMenus;


public class SubMenusSQL {
	Connexion con = new Connexion();
	@SuppressWarnings("rawtypes")
	Vector miVector = new Vector();
	
	public Obj_SubMenus BuscarUsuariosusua(int folio_empleado) throws SQLException{
		Obj_SubMenus usuario = new Obj_SubMenus();
		String query = "select folio,nombre+''+ap_paterno+''+ap_materno as nombre_completo,case when contrasena='' then '0' else contrasena end as contrasena,status,foto as Foto from tb_empleado where folio="+folio_empleado;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){			
				usuario.setFolio(rs.getInt("folio"));
				usuario.setNombre_completo(rs.getString("nombre_completo").trim());
				usuario.setContrasena(rs.getString("contrasena").trim());
				usuario.setStatus(rs.getInt("status"));
				
				File photo = new File(System.getProperty("user.dir")+"/tmp/tmp_usuario/usuariotmpcat.jpg");
				FileOutputStream fos = new FileOutputStream(photo);
				        byte[] buffer = new byte[1];
				        InputStream is = rs.getBinaryStream("Foto");
				        while (is.read(buffer) > 0) {
				        	fos.write(buffer);
				        }
				        fos.close();
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion BuscarUsuarios \n  en select folio,nombre+''+ap_paterno+''+ap_materno as nombre_completo,\n case when contrasena='' then '0' else contrasena end as contrasena,status,foto as Foto \n from tb_empleado where folio="+folio_empleado+"  \n SQLException: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);

			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return usuario;
	}
	
	@SuppressWarnings("unchecked")
	public String[] Relacion_de_SubMenus(int menu_principal) throws SQLException{
		String query = "select nombre from tb_submenu where menu_principal = "+menu_principal +" order by nombre asc";
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
	
	public int  cantidad_submenus(int menu_principal) throws SQLException{
		int submenus=0;
		String query = "select count(nombre) from tb_submenu where menu_principal = "+menu_principal;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				submenus=(rs.getInt(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return submenus;
	}
	

}
