package Conexiones_SQL;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import IZAGAR_Obj.Obj_Conciliacion_de_Movimientos_Bancarios_Contra_Contabilidad;
import Obj_Administracion_del_Sistema.Obj_Asistencia_Y_Puntualidad;
import Obj_Administracion_del_Sistema.Obj_Configuracion_Base_de_Datos;
import Obj_Administracion_del_Sistema.Obj_Configuracion_Base_de_Datos_2;
import Obj_Administracion_del_Sistema.Obj_Configuracion_Base_de_Datos_3;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Arduino.Obj_Arduino;
import Obj_Auditoria.Obj_Actividades_Por_Proyecto;
import Obj_Auditoria.Obj_Actividades_Relacionadas;
import Obj_Auditoria.Obj_Alimentacion_Cortes;
import Obj_Auditoria.Obj_Alimentacion_Por_Denominacion;
import Obj_Auditoria.Obj_Denominaciones;
import Obj_Auditoria.Obj_Divisas_Y_Tipo_De_Cambio;
import Obj_Auditoria.Obj_Movimiento_De_Asignacion;
import Obj_Auditoria.Obj_Retiros_Cajeros;
import Obj_Checador.Obj_Alimentacion_De_Permisos_A_Empleados;
import Obj_Checador.Obj_Asignacion_De_Computadoras_Para_Checador_Por_Establecimiento;
import Obj_Checador.Obj_Dias_Inhabiles;
import Obj_Checador.Obj_Entosal;
import Obj_Checador.Obj_Gen_Code_Bar;
import Obj_Checador.Obj_Horarios;
import Obj_Checador.Obj_Base_De_Solicitud_De_Empleado;
import Obj_Checador.Obj_Encargado_De_Solicitudes;
import Obj_Checador.Obj_Horario_Empleado;
import Obj_Checador.Obj_Mensaje_Personal;
import Obj_Checador.Obj_Mensajes;
import Obj_Compras.Obj_Alimentacion_De_Codigos_Adicionales;
import Obj_Compras.Obj_Alimentacion_De_Inventarios_Parciales;
import Obj_Compras.Obj_Alta_De_Productos;
import Obj_Compras.Obj_Cotizaciones_De_Un_Producto;
import Obj_Compras.Obj_Gestion_De_Pedidos_A_Establecimientos;
import Obj_Compras.Obj_Venta_De_Cascos_A_Proveedores;
import Obj_Compras.Obj_Puntos_De_Venta_De_Tiempo_Aire;
import Obj_Compras.Obj_Unidades_De_Medida_De_Producto;
import Obj_Contabilidad.Obj_Alta_Proveedores_Polizas;
import Obj_Contabilidad.Obj_Conceptos_De_Ordenes_De_Pago;
import Obj_Contabilidad.Obj_Indicadores;
import Obj_Contabilidad.Obj_Proveedores;
import Obj_Evaluaciones.Obj_Actividad;
import Obj_Evaluaciones.Obj_Actividad_Asignadas_Nivel_Jerarquico;
import Obj_Evaluaciones.Obj_Atributos;
import Obj_Evaluaciones.Obj_Captura_Del_Cuadrante_Personal;
import Obj_Evaluaciones.Obj_Cuadrante;
import Obj_Evaluaciones.Obj_Directorios;
import Obj_Evaluaciones.Obj_Empleados_En_Cuadrantes;
import Obj_Evaluaciones.Obj_Equipo_De_Trabajo;
import Obj_Evaluaciones.Obj_Jefatura;
import Obj_Evaluaciones.Obj_Nivel_Critico;
import Obj_Evaluaciones.Obj_Nivel_Jerarquico;
import Obj_Evaluaciones.Obj_Opciones_De_Respuestas;
import Obj_Evaluaciones.Obj_Ponderacion;
import Obj_Evaluaciones.Obj_Temporada;
import Obj_Lista_de_Raya.Obj_Alimentacion_De_Vacaciones;
import Obj_Lista_de_Raya.Obj_Asignacion_Mensajes;
import Obj_Lista_de_Raya.Obj_Autorizacion_Auditoria;
import Obj_Lista_de_Raya.Obj_Autorizacion_Finanzas;
import Obj_Lista_de_Raya.Obj_Bono_Complemento_Sueldo;
import Obj_Lista_de_Raya.Obj_Bono_Puntualidad_Y_Asistencia;
import Obj_Lista_de_Raya.Obj_Captura_Fuente_Sodas;
import Obj_Lista_de_Raya.Obj_Conceptos_De_Extras_Para_Lista_De_Raya;
import Obj_Lista_de_Raya.Obj_Departamento;
import Obj_Lista_de_Raya.Obj_Diferencia_De_Cortes;
import Obj_Lista_de_Raya.Obj_Diferencia_De_Cortes_Calculado;
import Obj_Lista_de_Raya.Obj_Empleados;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Lista_de_Raya.Obj_Finiquitos;
import Obj_Lista_de_Raya.Obj_Grupo_De_Vacaciones;
import Obj_Lista_de_Raya.Obj_Perfil_De_Puestos;
import Obj_Lista_de_Raya.Obj_Prestamos;
import Obj_Lista_de_Raya.Obj_Puestos;
import Obj_Lista_de_Raya.Obj_Rango_De_Prestamos;
import Obj_Lista_de_Raya.Obj_Sueldos;
import Obj_Lista_de_Raya.Obj_Tabla_De_Vacaciones;
import Obj_Lista_de_Raya.Obj_Tipo_De_Bancos;
import Obj_Lista_de_Raya.Obj_Fue_Sodas_AUXF;
import Obj_Lista_de_Raya.Obj_Fue_Sodas_DH;
import Obj_Lista_de_Raya.Obj_Totales_De_Cheque;
import Obj_Matrices.Obj_Aspectos_De_La_Etapa;
import Obj_Matrices.Obj_Etapas;
import Obj_Matrices.Obj_Unidades_de_Inspeccion;
import Obj_Punto_De_Venta.Obj_Abono_Clientes;
import Obj_Punto_De_Venta.Obj_Clientes;
import Obj_Reportes.Obj_Reportes_De_Ventas;

public class BuscarSQL {
	
	Connexion con = new Connexion();
	
	@SuppressWarnings({ "unchecked", "rawtypes", "resource" })
	public Obj_Configuracion_Base_de_Datos_3 Conexion_BD_3() throws IOException {
		Vector myVector = new Vector();
		Obj_Configuracion_Base_de_Datos_3 config = new Obj_Configuracion_Base_de_Datos_3();
		
		try{
			FileReader archivo = new FileReader(System.getProperty("user.dir")+"\\Config\\config3");
			BufferedReader bufferedWriter = new BufferedReader(archivo);
			String cadena = "";
			while( (cadena = bufferedWriter.readLine()) !=null)
				myVector.addElement(cadena);
				
				config.setDireccionIPV4(myVector.get(0).toString());
				config.setNombreBD(myVector.get(1).toString());
				config.setUsuario(myVector.get(2).toString());
				config.setContrasena(myVector.get(3).toString());
				
		}catch(FileNotFoundException e) {
			System.out.println(e.getMessage());
			return config=null;
		}
		return config;
			
	}

	public int  dias_para_fecha_revision_consideracion() throws SQLException{
		int dias=0;
		String query = "select dias_atras_para_fecha_consideracion_asistencia as dias from tb_configuracion_sistema";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				dias=(rs.getInt("dias"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return dias;
	}
	
	public String fecha(int dias) throws SQLException{
		String fecha="";
		String query = "     select convert(varchar(15),getdate()-("+dias+"),103) as fecha";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				fecha=(rs.getString("fecha"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return fecha;
	}
	
	public String fecha_guardado() throws SQLException{
		String fecha="";
		String query = "     select convert(varchar(15),getdate(),23) as fecha";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				fecha=(rs.getString("fecha"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return fecha;
	}
	
	public String fecha_mas_dias(String fechaant,int dias) throws SQLException{
		String fecha="";
		String query = " declare @fecha datetime='"+fechaant+"', @dia int="+dias
				+ "        select convert(varchar(15),@fecha+@dia,103) as fecha";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				fecha=(rs.getString("fecha"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return fecha;
	}
	
	public int dias_de_la_semana() throws SQLException{
		int dia=0;
		String query = " select  case when datepart(dw, getdate())=0 then 0 else datepart(dw, getdate())-1 end";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				dia=(rs.getInt(1));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return dia;
	}
	
	public int dia_descanso_colaborador(int folio_colaborador) throws SQLException{
		int dia=0;
		String query = " select dbo.dia_de_descanso_del_colaborador("+folio_colaborador+")-1";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				dia=(rs.getInt(1));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return dia;
	}
	
	
	public String inicio_final_TA(int cajero) throws SQLException{
		String nombrepc="";
		try { nombrepc = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		
		String inicio_final_TA="";
		String query = "     SELECT top 1 isnull(traspaso_saldoini_saldofin,'I') FROM tb_traspaso_de_saldo_TA_a_punto_de_venta where estatus='V' and convert(varchar(20),fecha_mov,103)=convert(varchar(20),getdate(),103) "
				+ "      and usuario="+cajero+" and folio_punto_de_venta_TA=(select folio from tb_pc_asignadas_a_un_punto_de_venta_TA where nombre_pc='"+nombrepc+"') and traspaso_saldoini_saldofin in('I','S') order by fecha_mov desc";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				inicio_final_TA=(rs.getString(""));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al Buscar en inicio_final_TA \nSQLServerException:"+e,"Avise Al Administrador del Sistema",JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return inicio_final_TA;
	}
	
	public String edad(String fecha_nacimiento) throws SQLException{
		String fecha="";
		String query = " select (datediff (d, '"+fecha_nacimiento+"', getdate ())) / 365 as edad";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				fecha=(rs.getString("edad"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return fecha;
	}
	
	public String numero_a_letra(double numero) throws SQLException{
		String Cantidad_Letra="";
		String query = " Select dbo.CantidadConLetra("+numero+") as cant_letra";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				Cantidad_Letra=(rs.getString("cant_letra"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return Cantidad_Letra;
	}
	
	
	
	public Obj_Fue_Sodas_DH buscarautoizacionfs(){
		Obj_Fue_Sodas_DH fs_autorizacion = new Obj_Fue_Sodas_DH();
		String query = "select autorizar_comparacion_fuente_sodas from tb_autorizaciones";
		try {				
			Statement s = con.conexion().createStatement();
			ResultSet rs = s.executeQuery(query);
			
			while(rs.next())
				fs_autorizacion.setStatus_autorizacion(Boolean.valueOf(rs.getString(1).trim()));
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return fs_autorizacion;
	}
	
	public Obj_Establecimiento Establecimiento(int folio) throws SQLException{
		Obj_Establecimiento establecimiento = new Obj_Establecimiento();
		String query = "select * from tb_establecimiento where folio ="+ folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
		    ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				establecimiento.setFolio(rs.getInt("folio"));
				establecimiento.setEstablecimiento(rs.getString("nombre").trim());
				establecimiento.setAbreviatura(rs.getString("abreviatura").trim());
				establecimiento.setSerie(rs.getString("serie").trim());
				establecimiento.setGrupo_cheque(rs.getInt("grupo_para_cheque"));
				establecimiento.setStatus(rs.getInt("status"));
				establecimiento.setGrupo_cortes(rs.getInt("folio_grupo_para_cortes"));
				establecimiento.setGrupo_permitir_nc(rs.getInt("permitir_nc"));
				
				establecimiento.setDomicilio(rs.getString("domicilio"));
				establecimiento.setRazon_social(rs.getString("razon_social"));
				establecimiento.setRfc(rs.getString("rfc"));
				establecimiento.setTelefono(rs.getString("telefono"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error");
			JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion [ Establecimiento ] SQLException: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		finally{
			 if (stmt != null) { stmt.close(); }
		}
		return establecimiento;
	}
	
	public boolean nombre_disponible(String nombre){
		String query = "exec sp_existe_empleado '" + nombre + "';";
		boolean disponible = false;
		try {				
			Statement s = con.conexion().createStatement();
			ResultSet rs = s.executeQuery(query);
			
			while(rs.next()){
				disponible = rs.getBoolean(1);
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
			
		return disponible;
	}
	
	public String Folio_Nuevo(String establecimiento){
		String folio_corte="";
		
		String query = "exec sp_select_folio_corte '" + establecimiento + "';";
		try {				
			Statement s = con.conexion().createStatement();
			ResultSet rs = s.executeQuery(query);
			
			while(rs.next()){
				folio_corte = rs.getString("folio_corte");
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
			
		return folio_corte;
	}
	
	public double total_retiro_cajero(int cajero,String establecimiento){
		double total_retiro=0;
		String queryupdate = "declare @folio_establecimiento int"+
		          "  set @folio_establecimiento = (select folio from tb_establecimiento where nombre ='"+establecimiento+"') "
				+ " update tb_retiros_a_cajeros set status_recibido=1 where folio_cajero ="+cajero+" and folio_establecimiento=@folio_establecimiento  and status_retiro_corte = 1 ";
		
		String query = "exec sp_select_total_de_retiro_de_cajero " + cajero + ",'" + establecimiento + "';";
		
		Connection con1 = new Connexion().conexion();
		PreparedStatement pstmt = null;
		
		try {	
			con1.setAutoCommit(false);
			pstmt = con1.prepareStatement(queryupdate);
			pstmt.executeUpdate();
			con1.commit();
			
			
			
			Statement s = con.conexion().createStatement();
			ResultSet rs = s.executeQuery(query);
			
			while(rs.next()){
				total_retiro = rs.getDouble("total_retiro_cajero");
			}
			
			

			
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
			
		return total_retiro;
	}
	
	public boolean Folio_Corte(String folio_corte){
		String query = "exec sp_select_comprobar_folio_corte_efectivo '" + folio_corte + "';";
		boolean existe = false;
		try {				
			Statement s = con.conexion().createStatement();
			ResultSet rs = s.executeQuery(query);
			
			while(rs.next()){
				existe = Boolean.valueOf(rs.getString("folio_corte"));
//				folio.setFolio_corte(rs.getString("folio_corte"));
//				System.out.print(rs.getString("folio_corte"));
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion [ Folio_Corte ]   SQLException: sp_select_comprobar_folio_corte_efectivo "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
			
		return existe;
	}
	
	public Obj_Alimentacion_Cortes Folio_Corte_Deposito(String folio_corte){
		Obj_Alimentacion_Cortes folio = new Obj_Alimentacion_Cortes();
		String query = "exec sp_select_comprobar_folio_corte_deposito '" + folio_corte + "';";
		try {				
			Statement s = con.conexion().createStatement();
			ResultSet rs = s.executeQuery(query);
			
			while(rs.next()){
				folio.setFolio_corte(rs.getString("folio_corte"));
			}
			rs.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion [ Obj_Alimentacion_Cortes ]   SQLException: sp_select_comprobar_folio_corte_deposito "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
			
		return folio;
	}
	
	public boolean Folio_Corte_Cheques(String folio_corte){
//		Obj_Alimentacion_Cortes folio = new Obj_Alimentacion_Cortes();
		boolean existe = false;
		String query = "exec sp_select_comprobar_folio_corte_cheques '" + folio_corte + "';";
		try {				
			Statement s = con.conexion().createStatement();
			ResultSet rs = s.executeQuery(query);
			
			while(rs.next()){
			existe=Boolean.valueOf(rs.getString("folio_corte"));
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion [ Folio_Corte_Cheques ]   SQLException: sp_select_comprobar_folio_corte_cheques "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
			
		return existe;
	}
	
	public Obj_Alimentacion_Por_Denominacion Datos_Denominacion(String folio_corte){
		Obj_Alimentacion_Por_Denominacion alimentacion = new Obj_Alimentacion_Por_Denominacion();
		String query = "exec sp_select_datos_alimentacion_denominaciones "+folio_corte;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()){
				alimentacion.setFolio_corte(rs.getString("folio_corte"));
				alimentacion.setAsignacion(rs.getString("asignacion"));
				alimentacion.setEmpleado(rs.getString("empleado"));
				alimentacion.setFecha(rs.getString("fecha"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return alimentacion;
	}
	
	public Obj_Sueldos Sueldo(int folio) throws SQLException{
		Obj_Sueldos sueldo = new Obj_Sueldos();
		String query = "select * from tb_sueldo where folio ="+ folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				sueldo.setFolio(rs.getInt("folio"));
				sueldo.setSueldo(rs.getFloat("sueldo"));
				sueldo.setPuesto(rs.getInt("puesto_id"));
				sueldo.setStatus((rs.getString("status").equals("1"))?true:false);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt != null){stmt.close();}
		}
		return sueldo;
	}

	public Obj_Sueldos Sueldo_Nuevo() throws SQLException{
		Obj_Sueldos sueldo = new Obj_Sueldos();
		String query = "select max(folio) as 'Maximo' from tb_sueldo";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				sueldo.setFolio(rs.getInt("Maximo"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return sueldo;
	}
	
	public Obj_Bono_Complemento_Sueldo Bono(int folio) throws SQLException{
		Obj_Bono_Complemento_Sueldo bono = new Obj_Bono_Complemento_Sueldo();
		String query = "select * from tb_bono where folio ="+ folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				bono.setFolio(rs.getInt("folio"));
				bono.setBono(rs.getFloat("bono"));
				bono.setAbreviatura(rs.getString("abreviatura").trim());
				bono.setStatus((rs.getString("status").equals("1"))?true:false);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return bono;
	}
	
	
	public Obj_Bono_Puntualidad_Y_Asistencia Bono_Puntualidad_Y_Asistencia(int folio) throws SQLException{
		Obj_Bono_Puntualidad_Y_Asistencia bono = new Obj_Bono_Puntualidad_Y_Asistencia();
		String query = "select folio,bono,abreviatura,status from [tb_bono_puntualidad_y_asistencia] where folio ="+ folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				bono.setFolio(rs.getInt("folio"));
				bono.setBono(rs.getFloat("bono"));
				bono.setAbreviatura(rs.getString("abreviatura").trim());
				bono.setStatus((rs.getString("status").equals("1"))?true:false);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al Buscar \nSQLServerException:"+e+" \n Parametros:"+query,"Avise Al Administrador del Sistema",JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return bono;
	}
	
	
	
	public Obj_Bono_Complemento_Sueldo BonoValor(float valor) throws SQLException{
		Obj_Bono_Complemento_Sueldo bono = new Obj_Bono_Complemento_Sueldo();
		String query = "select * from tb_bono where bono = "+ valor;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				bono.setFolio(rs.getInt("folio"));
				bono.setBono(rs.getFloat("bono"));
				bono.setAbreviatura(rs.getString("abreviatura").trim());
				bono.setStatus((rs.getString("status").equals("1"))?true:false);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return bono;
	}
	

	
	public Obj_Bono_Complemento_Sueldo Bono_Nuevo() throws SQLException{
		Obj_Bono_Complemento_Sueldo bono = new Obj_Bono_Complemento_Sueldo();
		String query = "select max(folio) as 'Maximo' from tb_bono";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				bono.setFolio(rs.getInt("Maximo"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return bono;
	}
	
	public Obj_Puestos Puesto(int folio) throws SQLException{
		Obj_Puestos puesto = new Obj_Puestos();
		String query = "select * from tb_puesto where folio ="+ folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				puesto.setFolio(rs.getInt("folio"));
				puesto.setPuesto(rs.getString("nombre").trim());
				puesto.setAbreviatura(rs.getString("abreviatura").trim());
				puesto.setStatus((rs.getString("status").equals("1"))?true:false);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return puesto;
	}
	
	public Obj_Dias_Inhabiles diaInA(int folio) throws SQLException{
		Obj_Dias_Inhabiles diaIA = new Obj_Dias_Inhabiles();
		String query = "select * from tb_dias_inhabiles where folio ="+ folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				diaIA.setFolio(rs.getInt("folio"));
				diaIA.setFecha(rs.getString("fecha").trim());
				diaIA.setDescripcion(rs.getString("descripcion").trim());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return diaIA;
	}
	
	public Obj_Atributos Atributos(int folio) throws SQLException{
		Obj_Atributos atrib = new Obj_Atributos();
		String query = "select * from tb_atributo where folio ="+ folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				atrib.setFolio(rs.getInt("folio"));
				atrib.setDescripcion(rs.getString("Descripcion").trim());
				atrib.setValor(rs.getFloat("Valor"));
				atrib.setStatus((rs.getString("status").equals("1"))?true:false);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return atrib;
	}
	
	public Obj_Jefatura Jefatura(int folio) throws SQLException{
		Obj_Jefatura jefat = new Obj_Jefatura();
		String query = "select * from tb_jefatura where folio ="+ folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				jefat.setFolio(rs.getInt("folio"));
				jefat.setDescripcion(rs.getString("Descripcion").trim());
				jefat.setStatus((rs.getString("status").equals("1"))?true:false);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return jefat;
	}
	
	public Obj_Equipo_De_Trabajo Eq_Tabajo(int folio) throws SQLException{
		Obj_Equipo_De_Trabajo EqTrabajo = new Obj_Equipo_De_Trabajo();
		String query = "select * from tb_equipo_trabajo where folio ="+ folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				EqTrabajo.setFolio(rs.getInt("folio"));
				EqTrabajo.setDescripcion(rs.getString("Descripcion").trim());
				EqTrabajo.setStatus((rs.getString("status").equals("1"))?true:false);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return EqTrabajo;
	}
	
	public Obj_Ponderacion Ponderacion(int folio) throws SQLException{
		Obj_Ponderacion pond = new Obj_Ponderacion();
		String query = "exec sp_select_ponderado "+ folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				pond.setFolio(rs.getInt("folio"));
				pond.setDescripcion(rs.getString("Descripcion").trim());
				pond.setValor(rs.getFloat("Valor"));
				pond.setFechaIn(rs.getString("FechaInicio").trim());
				pond.setFechaFin(rs.getString("FechaFin").trim());
				pond.setStatus((rs.getString("status").equals("1"))?true:false);
				
				pond.setDomingo(rs.getString("Domingo").equals("1")?true:false);
				pond.setLunes((rs.getString("Lunes").equals("1"))?true:false);
				pond.setMartes((rs.getString("Martes").equals("1"))?true:false);
				pond.setMiercoles((rs.getString("Miercoles").equals("1"))?true:false);
				pond.setJueves((rs.getString("Jueves").equals("1"))?true:false);
				pond.setViernes((rs.getString("Viernes").equals("1"))?true:false);
				pond.setSabado((rs.getString("Sabado").equals("1"))?true:false);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return pond;
	}
	
	public Obj_Nivel_Critico Nivel(int folio) throws SQLException{
		Obj_Nivel_Critico nc = new Obj_Nivel_Critico();
		String query = "select * from tb_nivel_critico where folio ="+ folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				nc.setFolio(rs.getInt("folio"));
				nc.setDescripcion(rs.getString("Descripcion").trim());
				nc.setValor(rs.getInt("Valor"));
				nc.setStatus((rs.getString("status").equals("1"))?true:false);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return nc;
	}
	
	public Obj_Opciones_De_Respuestas OpRespuesta(int folio) throws SQLException{
		Obj_Opciones_De_Respuestas opR = new Obj_Opciones_De_Respuestas();
		String query = "exec sp_select_opLibre "+ folio;
		
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
//				opR.setOpcion(rs.getString("opciones"));
//				opR.setNombre(rs.getString("nombre"));
//				opR.setStatus((rs.getString("status").equals("1"))?true:false);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return opR;
	}
	
	public Obj_Tipo_De_Bancos Tipo_Banco(int folio) throws SQLException{
		Obj_Tipo_De_Bancos banck = new Obj_Tipo_De_Bancos();
		String query = "select * from tb_tipo_banco where folio ="+ folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				banck.setFolio(rs.getInt("folio"));
				banck.setBanco(rs.getString("nombre").trim());
				banck.setAbreviatura(rs.getString("abreviatura").trim());
				banck.setStatus((rs.getString("status").equals("1"))?true:false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return banck;
	}
		

	public Obj_Alimentacion_Cortes corte(int folio) throws SQLException{
		Obj_Alimentacion_Cortes corte = new Obj_Alimentacion_Cortes();
		String query = "select * from tb_alimentacion_cortes where folio ="+ folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				corte.setFolio_corte(rs.getString("folio_corte"));
				corte.setFolio_empleado(rs.getInt("folio_empleado"));
				corte.setPuesto(rs.getString("nombre_empleado").trim());
				corte.setPuesto(rs.getString("puesto").trim());
				corte.setEstablecimiento(rs.getString("establecimiento").trim());
//				corte.setAsignacion(rs.getString("asignacion").trim());
				corte.setCorte_sistema(rs.getFloat("corte_del_sistema"));
				corte.setDeposito(rs.getFloat("deposito"));
				corte.setEfectivo(rs.getFloat("efectivo"));
				corte.setDiferencia_corte(rs.getFloat("diferencia_corte"));
				corte.setFecha_de_corte(rs.getString("fecha").trim());
				corte.setStatus((rs.getString("status").equals("1"))?true:false);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return corte;
	}
	
	public Object Obj_Obtener_Folio_Empleado (String nombre) throws SQLException{
		int folio=0;
				
		String query = "select folio from tb_empleado where RTRIM(LTRIM(nombre))+' '+RTRIM(LTRIM(ap_paterno))+' '+RTRIM(LTRIM(ap_materno))='"+ nombre+"'";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){

                folio=(rs.getInt("folio"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return folio;
	}
	
	public Obj_Alimentacion_Cortes Corte(String asignacion) throws SQLException{
		Obj_Alimentacion_Cortes corte = new Obj_Alimentacion_Cortes();
		String query = "select * from tb_alimentacion_denominaciones where asignacion ="+ asignacion;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				corte.setFolio_corte(rs.getString("folio_corte"));
				corte.setFolio_empleado(rs.getInt("folio_empleado"));
				corte.setNombre(rs.getString("nombre_empleado").trim());
				corte.setPuesto(rs.getString("puesto").trim());
				corte.setEstablecimiento(rs.getString("establecimiento").trim());
//				corte.setAsignacion(rs.getString("asignacion").trim());
				corte.setCorte_sistema(rs.getFloat("corte_del_sistema"));
				corte.setDeposito(rs.getFloat("deposito"));
				corte.setEfectivo(rs.getFloat("efectivo"));
				corte.setDiferencia_corte(rs.getFloat("diferencia_corte"));
				corte.setFecha_de_corte(rs.getString("fecha").trim());
				corte.setStatus((rs.getString("status").equals("1"))?true:false);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return corte;
	}
	
	public Obj_Divisas_Y_Tipo_De_Cambio divisas(int folio) throws SQLException{
		Obj_Divisas_Y_Tipo_De_Cambio divisas = new Obj_Divisas_Y_Tipo_De_Cambio();
		String query = "select * from tb_divisas_tipo_de_cambio where folio ="+ folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				divisas.setFolio(rs.getInt("folio"));
				divisas.setNombre(rs.getString("nombre_divisas").trim());
				divisas.setValor(rs.getFloat("valor"));
				divisas.setStatus((rs.getString("status").equals("1"))?true:false);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return divisas;
	}
	
	public Obj_Denominaciones denominaciones(int folio) throws SQLException{
		Obj_Denominaciones denominaciones = new Obj_Denominaciones();
		String query = "select * from tb_denominaciones where folio ="+ folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				denominaciones.setFolio(rs.getInt("folio"));
				denominaciones.setDenominacion(rs.getString("denominacion").trim());
				denominaciones.setMoneda(rs.getString("moneda"));
				denominaciones.setStatus((rs.getString("status").equals("1"))?true:false);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return denominaciones;
	}
	
	public Obj_Puestos Puesto_Nuevo() throws SQLException{
		Obj_Puestos puesto = new Obj_Puestos();
		String query = "select max(folio) as 'Maximo' from tb_puesto";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				puesto.setFolio(rs.getInt("Maximo"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return puesto;
	}
	
	public Obj_Dias_Inhabiles DiaInA_Nuevo() throws SQLException{
		Obj_Dias_Inhabiles diaIA = new Obj_Dias_Inhabiles();
		String query = "select max(folio) as 'Maximo' from tb_dias_inhabiles";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				diaIA.setFolio(rs.getInt("Maximo"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return diaIA;
	}
	
	public Obj_Atributos Atributo_Nuevo() throws SQLException{
		Obj_Atributos atrib = new Obj_Atributos();
		String query = "select max(folio) as 'Maximo' from tb_atributo";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				atrib.setFolio(rs.getInt("Maximo"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return atrib;
	}
	
	public Obj_Jefatura Jefatura_Nuevo() throws SQLException{
		Obj_Jefatura jefat = new Obj_Jefatura();
		String query = "select max(folio) as 'Maximo' from tb_jefatura";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				jefat.setFolio(rs.getInt("Maximo"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return jefat;
	}
	
	public Obj_Equipo_De_Trabajo Eq_Trabajo_Nuevo() throws SQLException{
		Obj_Equipo_De_Trabajo EqTrajajo = new Obj_Equipo_De_Trabajo();
		String query = "select max(folio) as 'Maximo' from tb_equipo_trabajo";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				EqTrajajo.setFolio(rs.getInt("Maximo"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return EqTrajajo;
	}
	
	public Obj_Ponderacion Ponderacion_Nuevo() throws SQLException{
		Obj_Ponderacion pond = new Obj_Ponderacion();
		String query = "select max(folio) as 'Maximo' from tb_ponderacion";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				pond.setFolio(rs.getInt("Maximo"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return pond;
	}
	
	public Obj_Nivel_Critico Nivel_Nuevo() throws SQLException{
		Obj_Nivel_Critico nc = new Obj_Nivel_Critico();
		String query = "select max(folio) as 'Maximo' from tb_nivel_critico";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				nc.setFolio(rs.getInt("Maximo"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return nc;
	}
	
	public int Cuadrante_Nuevo() throws SQLException{
		int folio = 0;
		String query = "exec sp_nuevo_cuadrante";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				folio =  rs.getInt("Maximo");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return folio;
	}
	
	public int Relacion_Actividad_Nueva() throws SQLException{
		int folio = 0;
		String query = "exec sp_nueva_relacion_actividad";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				folio =  rs.getInt("Maximo");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return folio;
	}
	
	public int Proyecto_Nuevo() throws SQLException{
		int folio = 0;
		String query = "exec sp_nuevo_proyecto";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				folio =  rs.getInt("Maximo");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return folio;
	}
	
	public Obj_Actividad Actividad_Nuevo() throws SQLException{
		Obj_Actividad actividad = new Obj_Actividad();
		String query = "select max(folio) as 'Maximo' from tb_actividad";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				actividad.setFolio(rs.getInt("Maximo"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return actividad;
	}
	
	public int OpRespuesta_Nuevo() throws SQLException{
		int numero  = 0;
		String query = "exec sp_nuevo_opciones_respuesta";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				numero = rs.getInt("Maximo");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return numero;
	}
	
	public Obj_Tipo_De_Bancos Tipo_Banco_Nuevo() throws SQLException{
		Obj_Tipo_De_Bancos banco = new Obj_Tipo_De_Bancos();
		String query = "select max(folio) as 'Maximo' from tb_tipo_banco";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				banco.setFolio(rs.getInt("Maximo"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return banco;
	}
		
		
	public Obj_Divisas_Y_Tipo_De_Cambio Divisas_Nuevo() throws SQLException{
		Obj_Divisas_Y_Tipo_De_Cambio divisas = new Obj_Divisas_Y_Tipo_De_Cambio();
		String query = "select max(folio) as 'Maximo' from tb_divisas_tipo_de_cambio";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				divisas.setFolio(rs.getInt("Maximo"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return divisas;
	}
	
	public Obj_Denominaciones Denominaciones_Nuevo() throws SQLException{
		Obj_Denominaciones denominaciones = new Obj_Denominaciones();
		String query = "select max(folio) as 'Maximo' from tb_denominaciones";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				denominaciones.setFolio(rs.getInt("Maximo"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return denominaciones;
	}
	
	public Obj_Empleados Empleado(int folio) throws SQLException{
		Obj_Empleados empleado = new Obj_Empleados();
		String query = "exec sp_empleados "+ folio;
		Statement stmt = null;

		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while(rs.next()){
//				datos personales	
				empleado.setFolio(rs.getInt("folio"));
				empleado.setNo_checador(rs.getString("no_checador").trim());
				empleado.setNombre(rs.getString("nombre").trim());
				empleado.setAp_paterno(rs.getString("ap_paterno").trim());
				empleado.setAp_materno(rs.getString("ap_materno").trim());
				empleado.setFecha_nacimiento(rs.getString("fecha_nacimiento").trim());
				empleado.setCalle(rs.getString("calle").trim());
				empleado.setColonia(rs.getString("colonia").trim());
				empleado.setPoblacion(rs.getString("poblacion").trim());
				empleado.setTelefono_familiar(rs.getString("telefono_familiar").trim());
				empleado.setTelefono_propio(rs.getString("telefono_propio").trim());
				empleado.setTelefono_cuadrante(rs.getString("telefono_cuadrante"));
				empleado.setRfc(rs.getString("rfc").trim());
				empleado.setCurp(rs.getString("curp").trim());
				empleado.setSexo(rs.getInt("sexo"));
				
//				laboral
				empleado.setHorario(rs.getInt("horario"));
				empleado.setHorario2(rs.getInt("horario2"));
				empleado.setStatus_h1(rs.getInt("status_h1"));
				empleado.setStatus_h2(rs.getInt("status_h2"));
				empleado.setStatus_rotativo(rs.getInt("status_rotativo"));
				empleado.setFecha_ingreso(rs.getString("fecha_ingreso"));
				empleado.setStatus(rs.getInt("status"));
				empleado.setFecha_baja(rs.getString("fecha_baja"));
				empleado.setCuadrante_parcial(rs.getInt("cuadrante_parcial") == 1 ? true : false);
				empleado.setDepartameto(rs.getInt("departamento"));
				empleado.setImss(rs.getString("imss"));
				empleado.setStatus_imss(rs.getInt("status_imss"));
				empleado.setNumero_infonavit(rs.getString("numero_infonavit"));
				empleado.setEstablecimiento(rs.getInt("establecimiento_id"));
				empleado.setPuesto(rs.getInt("puesto_id"));
				empleado.setDescanso(rs.getString("descanso"));
				empleado.setDobla(rs.getString("dobla"));
				empleado.setStatus_checador(rs.getString("status_checador"));
				
//				percepciones y deducciones
				empleado.setSalario_diario(rs.getFloat("salario_diario"));
				empleado.setSalario_diario_integrado(rs.getFloat("salario_diario_integrado"));
				empleado.setForma_pago(rs.getString("forma_pago"));
				empleado.setSueldo(rs.getFloat("sueldo_id"));				
				empleado.setBono(rs.getInt("bono_id"));
				empleado.setBono_asistencia(rs.getFloat("bono_asistencia"));
				empleado.setBono_puntualidad(rs.getFloat("bono_puntualidad"));
				empleado.setPrestamo(rs.getInt("rango_prestamo_id"));
				empleado.setPension_alimenticia(rs.getFloat("pension_alimenticia"));
				empleado.setInfonavit(rs.getFloat("infonavit"));
				empleado.setInfonacot(rs.getFloat("infonacot"));
				
				empleado.setTargeta_nomina(rs.getString("targeta_nomina"));
				empleado.setTipo_banco(rs.getInt("tipo_banco_id"));
				empleado.setGafete(rs.getBoolean("gafete") ? true : false);
				empleado.setFuente_sodas(rs.getBoolean("fuente_sodas") ? true : false);
				empleado.setObservasiones(rs.getString("observaciones"));
				empleado.setFecha_actualizacion(rs.getString("fecha_actualizacion"));
				empleado.setUltimo_usuario_modifico(rs.getString("ultimo_usuario_modifico"));
				
				empleado.setHorario3(rs.getInt("horario3"));
				empleado.setStatus_h3(rs.getInt("status_h3"));
				
				empleado.setFecha_ingreso_imss(rs.getString("fecha_ingreso_imss"));
				empleado.setFecha_vencimiento_licencia(rs.getString("fecha_vencimiento_licencia"));
				
				empleado.setEstado_civil(rs.getString("estado_civil"));
				empleado.setTipo_sangre(rs.getString("tipo_de_sangre"));
				empleado.setEscolaridad(rs.getString("escolaridad"));
				empleado.setContrato(rs.getInt("contrato"));
				empleado.setPresencia_fisica(rs.getInt("presencia_fisica"));
				empleado.setBonocomplemento(rs.getInt("bono_complemento"));
				empleado.setPerfil(rs.getInt("folio_perfil"));
				
				File photo = new File(System.getProperty("user.dir")+"/tmp/tmp.jpg");
				FileOutputStream fos = new FileOutputStream(photo);
				
		            byte[] buffer = new byte[1];
		            InputStream is = rs.getBinaryStream("foto");
		            while (is.read(buffer) > 0) {
		                fos.write(buffer);
		            }
		            fos.close();
		            
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return empleado;
	}
	
	public Obj_Captura_Fuente_Sodas CapturaFuenteSodas(String clave) throws SQLException{
		Obj_Captura_Fuente_Sodas empleado = new Obj_Captura_Fuente_Sodas();
		String query = "exec sp_select_empleado_fuente_sodas '"+clave+"'";
		Statement stmt = null;

		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while(rs.next()){
//				datos personales	
				
				empleado.setFolio_empleado(rs.getInt("folio"));
				empleado.setClave(rs.getString("clave"));
				empleado.setEmpleado(rs.getString("empleado"));
				empleado.setEstablecimiento(rs.getString("establecimiento"));
				empleado.setPuesto(rs.getString("puesto"));
				empleado.setTotal(rs.getFloat("total"));
				
				
				File photo = new File(System.getProperty("user.dir")+"/tmp/tmp.jpg");
				FileOutputStream fos = new FileOutputStream(photo);
				
		            byte[] buffer = new byte[1];
		            InputStream is = rs.getBinaryStream("foto");
		            while (is.read(buffer) > 0) {
		                fos.write(buffer);
		            }
		            fos.close();
		            
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return empleado;
	}
	
	public Obj_Captura_Fuente_Sodas CapturaFuenteSodas_UltimiTicket(String clave) throws SQLException{
		Obj_Captura_Fuente_Sodas empleado = new Obj_Captura_Fuente_Sodas();
		String query = "exec sp_reporte_ultimo_ticket_por_empleado '"+clave+"';";
		Statement stmt = null;

		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while(rs.next()){
//				datos personales	
				empleado.setUsuario(rs.getString("usuario"));
				empleado.setFecha(rs.getString("fecha"));
				empleado.setEmpleado(rs.getString("empleado"));
				empleado.setEstablecimiento(rs.getString("establecimiento"));
				empleado.setPuesto(rs.getString("puesto"));
				empleado.setTicket(rs.getString("ticket"));
				empleado.setImporte(rs.getFloat("importe"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return empleado;
	}
	
	public Obj_Prestamos Prestamo(int folio) throws SQLException{
		Obj_Prestamos pre = new Obj_Prestamos();
		String query = "select * from tb_prestamo where folio_empleado ="+ folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				pre.setFolio(rs.getInt("folio"));
				pre.setFolio(rs.getInt("folio_empleado"));
				pre.setNombre_Completo(rs.getString("nombre_completo").trim());
				pre.setFecha(rs.getString("fecha"));
				pre.setCantidad(rs.getDouble("cantidad"));
				pre.setDescuento(rs.getDouble("descuento"));
				pre.setSaldo(rs.getDouble("saldo"));
				pre.setAbonos(rs.getInt("abonos"));
				pre.setStatus(rs.getInt("status"));
				pre.setStatus(rs.getInt("status_descuento"));
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return pre;
	}
	
	
	
	public Obj_Empleados Empleado_Nuevo() throws SQLException{
		Obj_Empleados empleado = new Obj_Empleados();
		String query = "select max(folio) as 'Maximo' from tb_empleado";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				empleado.setFolio(rs.getInt("Maximo"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return empleado;
	}
	
	public Obj_Usuario Usuario(int folio) throws SQLException{
		Obj_Usuario usuario = new Obj_Usuario();
		String query = "exec sp_select_usuario_login "+folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){			
				usuario.setFolio(rs.getInt("folio"));
				usuario.setNombre_completo(rs.getString("nombre_completo").trim());
				usuario.setContrasena(rs.getString("contrasena").trim());
				usuario.setPermiso_id(rs.getInt("permiso_id"));
				usuario.setFecha_alta(rs.getString("fecha").trim());
				usuario.setVista_previa_impresion(rs.getString("vista_previa_reportes"));
				usuario.setStatus(rs.getInt("status"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return usuario;
	}
	
	public Obj_Usuario BuscarUsuarios(int folio_empleado) throws SQLException{
		Obj_Usuario usuario = new Obj_Usuario();
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
				
				File photo = new File(System.getProperty("user.dir")+"/tmp/tmp_usuario/usuariotmp.jpg");
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
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Vector returnPermiso(int folio_empleado, int menu) throws SQLException{
		Vector prueba = new Vector();
		String query = "exec sp_obtener_status_de_permisos "+ folio_empleado +" , "+menu ;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){			
				prueba.add(rs.getBoolean(1));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion returnPermiso \n  en el procedimiento : sp_obtener_status_de_permisos  \n SQLException: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return prueba;
	}
	
	public boolean existeUsuario(int folio) throws SQLException{
		boolean existe;
		int filas = getFilas("select * from tb_permisos_submenus_usuarios where folio_empleado= "+folio);
		if(filas > 1){
			existe = true;
		}else{
			existe = false;
		}
		return existe;
	}
	
	
	public Obj_Usuario Maximo() throws SQLException{
		Obj_Usuario usuario = new Obj_Usuario();
		String query = "select max(folio) as 'Maximo' from tb_usuario";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				usuario.setFolio(rs.getInt("Maximo"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error");
			JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion Obj_Usuario Maximo() \n  en select max(folio) as 'Maximo' from tb_usuario \n SQLException: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		finally{
			 if (stmt != null) { stmt.close(); }
		}
		return usuario;
	}
	
	public Obj_Usuario colores_usuario() throws SQLException{
		Obj_Usuario usuario = new Obj_Usuario();
		Obj_Usuario usuario_folio = new Obj_Usuario().LeerSession();
				
		String query = "exec sp_select_colores_del_usuario "+usuario_folio.getFolio();
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				usuario.setRFuente(rs.getInt("RFuente"));
				usuario.setGFuente(rs.getInt("GFuente"));
	          	usuario.setBFuente(rs.getInt("BFuente"));
				usuario.setRFuenteS(rs.getInt("RFuenteS"));
				usuario.setGFuenteS(rs.getInt("GFuenteS"));
	          	usuario.setBFuenteS(rs.getInt("BFuenteS"));
				usuario.setRfila(rs.getInt("RFila"));
				usuario.setGfila(rs.getInt("GFila"));
				usuario.setBfila(rs.getInt("BFila"));
				usuario.setRfilaS(rs.getInt("RFilaS"));
				usuario.setGfilaS(rs.getInt("GFilaS"));
				usuario.setBfilaS(rs.getInt("BFilaS"));
				usuario.setTamanio_fuente(rs.getInt("tamanio_fuente"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error");
			JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion Obj_Usuario colores_usuario \n SQLException: "+e.getMessage()+" \nprocedure: "+query, "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		finally{
			 if (stmt != null) { stmt.close(); }
		}
		return usuario;
	}
	
	
	public Obj_Fue_Sodas_DH MaximoFuente() throws SQLException{
		Obj_Fue_Sodas_DH bono = new Obj_Fue_Sodas_DH();
		String query = "select max(folio) as 'Maximo' from tb_fuente_sodas_rh";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				bono.setFolio(rs.getInt("Maximo"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return bono;
	}
	
	public Obj_Fue_Sodas_AUXF MaximoFuente_auxf() throws SQLException{
		Obj_Fue_Sodas_AUXF bono = new Obj_Fue_Sodas_AUXF();
		String query = "select max(folio) as 'Maximo' from tb_fuente_sodas_auxf";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				bono.setFolio(rs.getInt("Maximo"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return bono;
	}
	
	public Obj_Prestamos maximoPrestamo() throws SQLException{
		Obj_Prestamos bono = new Obj_Prestamos();
		String query = "select max(folio) as 'Maximo' from tb_prestamo";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				bono.setFolio(rs.getInt("Maximo"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return bono;
	}
	
	public Obj_Rango_De_Prestamos Rango_Prestamos(int folio) throws SQLException{
		Obj_Rango_De_Prestamos prestamos = new Obj_Rango_De_Prestamos();
		String query = "select * from tb_rango_prestamos where folio ="+ folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				prestamos.setFolio(rs.getInt("folio"));
				prestamos.setPrestamo_minimo(rs.getDouble("minimo"));
				prestamos.setPrestamo_maximo(rs.getDouble("maximo"));
				prestamos.setDescuento(rs.getDouble("descuento"));
				prestamos.setStatus((rs.getString("status").equals("1"))?true:false);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return prestamos;
	}
	
	public Obj_Rango_De_Prestamos Rango_Prestamos_Nuevo() throws SQLException{
		Obj_Rango_De_Prestamos rango_nuevo = new Obj_Rango_De_Prestamos();
		String query = "select max(folio) as 'Maximo' from tb_rango_prestamos";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				rango_nuevo.setFolio(rs.getInt("Maximo"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return rango_nuevo;
	}
	
	public Obj_Asistencia_Y_Puntualidad Asistencia_Puntualidad() throws SQLException{
		Obj_Asistencia_Y_Puntualidad numero = new Obj_Asistencia_Y_Puntualidad();
		String query = "select max(folio) as 'Maximo' from tb_asistencia_puntualidad";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				numero.setExiste(rs.getInt("Maximo"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return numero;
	}
	
	public Obj_Asistencia_Y_Puntualidad Asistencia_Puntualidad(int folio) throws SQLException{
		Obj_Asistencia_Y_Puntualidad numero = new Obj_Asistencia_Y_Puntualidad();
		String query = "select * from tb_asistencia_puntualidad where folio="+folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				numero.setValorAsistencia(rs.getFloat("asistencia"));
				numero.setValorPuntualidad(rs.getFloat("impuntualidad"));
				numero.setValorGafete(rs.getFloat("gafete"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return numero;
	}
	
	public Obj_Fue_Sodas_DH fuente_sodas_rh(int folio) throws SQLException {
		Obj_Fue_Sodas_DH fuente_sodas = new Obj_Fue_Sodas_DH();
		String query = "select status_ticket from tb_fuente_sodas_rh where folio ="+ folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				fuente_sodas.setStatus_ticket(rs.getInt("status_ticket"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return fuente_sodas;
	}
	
	public Obj_Fue_Sodas_AUXF fuente_sodas_ax(int folio) throws SQLException{
		Obj_Fue_Sodas_AUXF fuente_sodas = new Obj_Fue_Sodas_AUXF();
		String query = "select status_ticket from tb_fuente_sodas_auxf where folio ="+ folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				fuente_sodas.setStatus_ticket(rs.getInt("status_ticket"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return fuente_sodas;
	}
	
	public Obj_Diferencia_De_Cortes maximo_diferencia_cortes() throws SQLException{
		Obj_Diferencia_De_Cortes bono = new Obj_Diferencia_De_Cortes();
		String query = "select max(folio) as 'Maximo' from tb_prestamo";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				bono.setFolio(rs.getInt("Maximo"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return bono;
	}
	
	public Obj_Departamento Departamento(int folio) throws SQLException{
		Obj_Departamento departamento = new Obj_Departamento();
		String query = "select * from tb_departamento where folio ="+ folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				departamento.setFolio(rs.getInt("folio"));
				departamento.setDepartamento(rs.getString("departamento").trim());
				departamento.setAbreviatura(rs.getString("abreviatura").trim());
				departamento.setStatus(rs.getBoolean("status"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt != null){stmt.close();}
		}
		return departamento;
	}
	
	public Obj_Departamento Departamento_Nuevo() throws SQLException{
		Obj_Departamento departamento = new Obj_Departamento();
		String query = "select max(folio) as 'Maximo' from tb_departamento";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				departamento.setFolio(rs.getInt("Maximo"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return departamento;
	}
	
	@SuppressWarnings({ "rawtypes", "resource", "unchecked" })
	public Obj_Configuracion_Base_de_Datos Conexion_BD() throws IOException {
		Vector myVector = new Vector();
		Obj_Configuracion_Base_de_Datos config = new Obj_Configuracion_Base_de_Datos();
		
		try{
			FileReader archivo = new FileReader(System.getProperty("user.dir")+"\\Config\\config");
			BufferedReader bufferedWriter = new BufferedReader(archivo);
			String cadena = "";
			while( (cadena = bufferedWriter.readLine()) !=null)
				myVector.addElement(cadena);
				
				config.setDireccionIPV4(myVector.get(0).toString());
				config.setNombreBD(myVector.get(1).toString());
				config.setUsuario(myVector.get(2).toString());
				config.setContrasena(myVector.get(3).toString());
				
		}catch(FileNotFoundException e) {
			System.out.println(e.getMessage());
			return config=null;
		}
		return config;
			
	}
	
	@SuppressWarnings({ "rawtypes", "resource", "unchecked" })
	public Obj_Configuracion_Base_de_Datos_2 Conexion_BD_2() throws IOException {
		Vector myVector = new Vector();
		Obj_Configuracion_Base_de_Datos_2 config = new Obj_Configuracion_Base_de_Datos_2();
		
		try{
			FileReader archivo = new FileReader(System.getProperty("user.dir")+"\\Config\\config2");
			BufferedReader bufferedWriter = new BufferedReader(archivo);
			String cadena = "";
			while( (cadena = bufferedWriter.readLine()) !=null)
				myVector.addElement(cadena);
				
				config.setDireccionIPV4(myVector.get(0).toString());
				config.setNombreBD(myVector.get(1).toString());
				config.setUsuario(myVector.get(2).toString());
				config.setContrasena(myVector.get(3).toString());
				
		}catch(FileNotFoundException e) {
			System.out.println(e.getMessage());
			return config=null;
		}
		return config;
			
	}
	
	public Obj_Establecimiento buscar_nombre_establecimiento(String nombre) throws SQLException{
		Obj_Establecimiento estab = new Obj_Establecimiento();
		String query = "if(select top 1 folio from tb_establecimiento where nombre='"+nombre+"' )is null select 0 as folio,0 as nombre"+
		               "     else (select top 1 folio,nombre from tb_establecimiento where nombre='"+nombre+"')";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				estab.setEstablecimiento(rs.getString("nombre"));
				estab.setFolio(rs.getInt("folio"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion buscar_nombre_establecimiento SQLException: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return estab;
	}
	
	public Obj_Establecimiento buscar_existe_abreviatura_establecimiento(String abreviatura) throws SQLException{
		Obj_Establecimiento Abreviatura = new Obj_Establecimiento();
		String query = "if(select top 1 folio from tb_establecimiento where abreviatura='"+abreviatura+"' )is null select 0 as folio,0 as nombre,0 as abreviatura"+
	               "     else (select top 1 folio,nombre,abreviatura from tb_establecimiento where abreviatura='"+abreviatura+"')";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				Abreviatura.setFolio(rs.getInt("folio"));
				Abreviatura.setEstablecimiento(rs.getString("nombre"));
				Abreviatura.setAbreviatura(rs.getString("abreviatura"));
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion buscar_existe_abreviatura_establecimiento SQLException: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return Abreviatura;
	}
	
	public Obj_Establecimiento buscar_existe_serie_establecimiento(String serie) throws SQLException{
		Obj_Establecimiento Serie = new Obj_Establecimiento();
		String query = "if(select top 1 folio from tb_establecimiento where serie='"+serie+"' )is null select 0 as folio,0 as nombre,0 as abreviatura,0 as serie"+
	               "     else (select top 1 folio,nombre,abreviatura,serie from tb_establecimiento where serie='"+serie+"')";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				Serie.setFolio(rs.getInt("folio"));
				Serie.setEstablecimiento(rs.getString("nombre"));
				Serie.setAbreviatura(rs.getString("abreviatura"));
				Serie.setSerie(rs.getString("serie"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion buscar_existe_serie_establecimiento SQLException: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return Serie;
	}
	
	
	
	public Obj_Horarios Horario_buscar(String nombre) throws SQLException{
		Obj_Horarios horario = new Obj_Horarios();
		String query = "select folio from tb_horarios where nombre='"+nombre+"'";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()){
				horario.setFolio(rs.getInt("folio"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return horario;
	}
	
	public Obj_Puestos Pues_buscar(String nombre) throws SQLException{
		Obj_Puestos puest = new Obj_Puestos();
		String query = "select folio from tb_puesto where nombre='"+nombre+"'";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				puest.setFolio(rs.getInt("folio"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return puest;
	}
	
	public Obj_Tipo_De_Bancos Banco_Buscar(String nombre) throws SQLException{
		Obj_Tipo_De_Bancos banck = new Obj_Tipo_De_Bancos();
		String query = "select folio from tb_tipo_banco where nombre='"+nombre+"'";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				banck.setFolio(rs.getInt("folio"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return banck;
	}
		
	public Obj_Divisas_Y_Tipo_De_Cambio Divisas_buscar(String nombre) throws SQLException{
		Obj_Divisas_Y_Tipo_De_Cambio divisas = new Obj_Divisas_Y_Tipo_De_Cambio();
		String query = "select valor,nombre_divisas from tb_divisas_tipo_de_cambio where nombre_divisas='"+nombre+"'";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				divisas.setNombre(rs.getString("nombre_divisas"));
				divisas.setValor(rs.getFloat("valor"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return divisas;
	}
	
	public Obj_Horario_Empleado Turn_buscar(String nombre) throws SQLException{
		Obj_Horario_Empleado turno = new Obj_Horario_Empleado();
		String query = "exec sp_select_horario_desc_dobla '"+nombre+"'";
		
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				turno.setFolio(rs.getInt("folio"));
				turno.setDescanso(rs.getString("descanso"));
				turno.setDobla(rs.getString("dobla"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return turno;
	}
	
	public Obj_Horario_Empleado Turn_buscar2(String nombre) throws SQLException{
		Obj_Horario_Empleado turno2 = new Obj_Horario_Empleado();
		String query = "exec sp_select_horario_desc_dobla '"+nombre+"'";
		
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				turno2.setFolio(rs.getInt("folio"));
				turno2.setDescanso(rs.getString("descanso"));
				turno2.setDobla(rs.getString("dobla"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return turno2;
	}
	
	public Obj_Horario_Empleado Turn_buscar3(String nombre) throws SQLException{
		Obj_Horario_Empleado turno3 = new Obj_Horario_Empleado();
		String query = "exec sp_select_horario_desc_dobla '"+nombre+"'";
		
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				turno3.setFolio(rs.getInt("folio"));
				turno3.setDescanso(rs.getString("descanso"));
				turno3.setDobla(rs.getString("dobla"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return turno3;
	}
	

	
	public Obj_Establecimiento Establ_buscar_nombre(int folio) throws SQLException{
		Obj_Establecimiento estab = new Obj_Establecimiento();
		String query = "select nombre from tb_establecimiento where folio="+folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				estab.setEstablecimiento(rs.getString("nombre"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return estab;
	}
	
	public Obj_Establecimiento Establecimiento_buscar_folio_por_nombre(String nombre) throws SQLException{
		Obj_Establecimiento estab = new Obj_Establecimiento();
		String query = "select folio from tb_establecimiento where nombre='"+nombre+"'";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				estab.setFolio(rs.getInt("folio"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion Establecimiento_buscar_folio_por_nombre en select folio from tb_establecimiento where nombre= SQLException: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);

			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return estab;
	}
	
	public Obj_Puestos Pues_buscar(int folio) throws SQLException{
		Obj_Puestos puest = new Obj_Puestos();
		String query = "select nombre from tb_puesto where folio="+folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				puest.setPuesto(rs.getString("nombre"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return puest;
	}
	
	public Obj_Tipo_De_Bancos buscar_Banck(int folio) throws SQLException{
		Obj_Tipo_De_Bancos banck = new Obj_Tipo_De_Bancos();
		String query = "select nombre from tb_tipo_banco where folio="+folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				banck.setBanco(rs.getString("nombre"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return banck;
	}
	
	public Obj_Divisas_Y_Tipo_De_Cambio Divisas_buscar(int folio) throws SQLException{
		Obj_Divisas_Y_Tipo_De_Cambio denominaciones = new Obj_Divisas_Y_Tipo_De_Cambio();
		String query = "select nombre from tb_divisas_tipo_de_cambio where folio="+folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				denominaciones.setNombre(rs.getString("nombre_divisas"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return denominaciones;
	}
	
	public Obj_Horario_Empleado Turn_buscar(int folio) throws SQLException{
		Obj_Horario_Empleado turno = new Obj_Horario_Empleado();
		String query = "select tb_horarios.nombre from tb_horarios where folio="+folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				turno.setNombre(rs.getString("nombre"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return turno;
	}
	
	public Obj_Horario_Empleado Turn_buscar2(int folio) throws SQLException{
		Obj_Horario_Empleado turno2 = new Obj_Horario_Empleado();
		String query = "select tb_horarios.nombre from tb_horarios where folio="+folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				turno2.setNombre(rs.getString("nombre"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return turno2;
	}
	
	public Obj_Horario_Empleado Turn_buscar3(int folio) throws SQLException{
		Obj_Horario_Empleado turno3 = new Obj_Horario_Empleado();
		String query = "select tb_horarios.nombre from tb_horarios where folio="+folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				turno3.setNombre(rs.getString("nombre"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return turno3;
	}
	
	
	public Obj_Configuracion_Del_Sistema Configuracion_sistema() throws SQLException{
		Obj_Configuracion_Del_Sistema configs = new Obj_Configuracion_Del_Sistema();
		String query ="select count(bono_10_12) as 'Couns' from tb_configuracion_sistema";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
		    ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				configs.setCouns(rs.getInt("Couns"));
			
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error");
			return null;
		}
		finally{
			 if (stmt != null) { stmt.close(); }
		}
		return configs;
	}
	
	public Obj_Configuracion_Del_Sistema Configuracion_sistema2() throws SQLException{
		Obj_Configuracion_Del_Sistema configs = new Obj_Configuracion_Del_Sistema();
		String query ="exec sp_select_configuracion_del_sistema";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
		    ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				configs.setBono_10_12(rs.getBoolean("bono_10_12"));
				configs.setBono_dia_extra(rs.getBoolean("bono_dia_extra"));
				configs.setGuardar_horario(rs.getBoolean("guardar_horario"));
				configs.setGuardar_departamento(rs.getBoolean("guardar_departamento"));
				configs.setPorcentaje_fs(rs.getInt("porcentaje_fuente_sodas"));
				configs.setFechaLR(rs.getString("fecha_lista_raya_pasada"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error");
			return null;
		}
		finally{
			 if (stmt != null) { stmt.close(); }
		}
		return configs;
	}
	
	public Obj_Departamento Departamento_buscar(int folio) throws SQLException{
		Obj_Departamento departamento = new Obj_Departamento();
		String query = "select horario from tb_departamento where folio="+folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				departamento.setDepartamento(rs.getString("departamento"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return departamento;
	}
	
	public Obj_Departamento Departamento_buscar_nombre(String dep) throws SQLException{
		Obj_Departamento departamento = new Obj_Departamento();
		String query = "select folio from tb_departamento where departamento='"+dep+"'";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				departamento.setFolio(rs.getInt("folio"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return departamento;
	}
	
	public Obj_Autorizacion_Auditoria Autorizar_Audi() throws SQLException{
		Obj_Autorizacion_Auditoria auditoria = new Obj_Autorizacion_Auditoria();
		String query = "select autorizar_auditoria from tb_autorizaciones";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
		    ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				auditoria.setAutorizar(rs.getBoolean(1));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error");
			return null;
		}
		finally{
			 if (stmt != null) { stmt.close(); }
		}
		return auditoria;
	}
	
	public Obj_Autorizacion_Finanzas Autorizar_Finanzas() throws SQLException{
		Obj_Autorizacion_Finanzas auditoria = new Obj_Autorizacion_Finanzas();
		String query = "select autorizar_finanzas from tb_autorizaciones";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
		    ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				auditoria.setAutorizar(rs.getBoolean(1));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error");
			return null;
		}
		finally{
			 if (stmt != null) { stmt.close(); }
		}
		return auditoria;
	}
	
	public Obj_Totales_De_Cheque Autorizacion_totales_nomina () throws SQLException{
		Obj_Totales_De_Cheque nomina = new Obj_Totales_De_Cheque();
		String query = "select autorizar_nomina from tb_autorizaciones";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
		    ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				nomina.setAutorizar(rs.getBoolean(1));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error");
			return null;
		}
		finally{
			 if (stmt != null) { stmt.close(); }
		}
		return nomina;
	}
	

	public String[][] getUsuarioPermisos(){
		String datos = "exec sp_lista_usuarios_para_administracion_permisos";
		String[][] Matriz = new String[getFilas(datos)][4];
		Statement s;
		ResultSet rs;
		try {			
			s = con.conexion().createStatement();
			rs = s.executeQuery(datos);
			int i=0;
			while(rs.next()){
				Matriz[i][0] = rs.getString(1);
				Matriz[i][1] = rs.getString(2);
				Matriz[i][2] = rs.getString(3);
				Matriz[i][2] = rs.getString(4);
				i++;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return Matriz; 
	}
	
	public int getFilas(String qry){
		int filas=0;
		try {
			Statement s = con.conexion().createStatement();
			ResultSet rs = s.executeQuery(qry);
			while(rs.next()){
				filas++;
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return filas;
	}
	
	public int getFilasExterno(String qry){
		int filas=0;
		try {
			Statement s = con.conexion_IZAGAR().createStatement();
			ResultSet rs = s.executeQuery(qry);
			while(rs.next()){
				filas++;
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return filas;
	}
	
	public int MaximoListaRaya(){
		String datos = "select	max(numero_lista)+1 from tb_lista_raya";
		int valor = 0;
		Statement s;
		ResultSet rs;
		try {			
			s = con.conexion().createStatement();
			rs = s.executeQuery(datos);
			while(rs.next()){
				valor = rs.getInt(1);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion MaximoListaRaya en select	max(numero_lista)+1 from tb_lista_raya: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
		return valor; 
	}
	
	
	public int MaximoListaNomina(){
		String datos = "select max(lista_raya) from tb_captura_totales_nomina";
		int valor = 0;
		Statement s;
		ResultSet rs;
		try {			
			s = con.conexion().createStatement();
			rs = s.executeQuery(datos);
			while(rs.next()){
				valor = rs.getInt(1);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion MaximoListaNomina en select max(lista_raya) from tb_captura_totales_nomina: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
		return valor; 
	}
	
	
	public int getMaximoNomina(){
		String sp_total_cheques = "select  case when max(folio_lista) is null then 0  else max(folio_lista)	end  from  tb_totales_cheques_lista_raya";

		int resultado = 0;
		Statement s;
		ResultSet rs;
		try {		
			s = con.conexion().createStatement();
			rs = s.executeQuery(sp_total_cheques);
			
			while(rs.next()){
				resultado = rs.getInt(1);
				
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion getMaximoNomina \n en: select  case when max(folio_lista) is null then 0  else max(folio_lista)	end  from tb_nomina "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
		return resultado; 
	}
	
	public int TemporadaNuevo(){
		String sp_total_cheques = "exec sp_temporada_nuevo";
		int resultado = 0;
		Statement s;
		ResultSet rs;
		try {		
			s = con.conexion().createStatement();
			rs = s.executeQuery(sp_total_cheques);
			
			while(rs.next()){
				resultado = rs.getInt(1);
				
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return resultado; 
	}
	
	public Obj_Temporada TemporadaBuscar(int folio) throws SQLException{
		Obj_Temporada temporada = new Obj_Temporada();
		String query = "select * from tb_temporada where folio ="+ folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				temporada.setFolio(rs.getInt("folio"));
				temporada.setDescripcion(rs.getString("descripcion"));
				temporada.setFecha_in(rs.getString("fecha_in"));
				temporada.setFecha_fin(rs.getString("fecha_fin"));
				temporada.setDia(rs.getString("dia"));	
				temporada.setStatus(rs.getBoolean("status") ? true : false);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return temporada;
	}

	public int Nueva_Actividad(){
		String sp_total_cheques = "exec sp_nueva_actividad";
		int resultado = 0;
		Statement s;
		ResultSet rs;
		try {		
			s = con.conexion().createStatement();
			rs = s.executeQuery(sp_total_cheques);
			
			while(rs.next()){
				resultado = rs.getInt(1);
				
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return resultado; 
	}
	
	public int Nueva_Actividad_Jerarquico(){
		String sp_total_cheques = "exec [sp_nueva_actividad_jerarquico]";
		int resultado = 0;
		Statement s;
		ResultSet rs;
		try {		
			s = con.conexion().createStatement();
			rs = s.executeQuery(sp_total_cheques);
			
			while(rs.next()){
				resultado = rs.getInt(1);
				
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return resultado; 
	}

	public boolean ActividadExiste(int actividad){
		String query = "exec sp_folio_actividad "+actividad;
		
		boolean existe = false;
		Statement s;
		ResultSet rs;
		
		try {				
			s = con.conexion().createStatement();
			rs = s.executeQuery(query);
			
			while(rs.next()){
				existe = Boolean.parseBoolean(rs.getString("Existe").trim());
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
			
		return existe;
	}
	
	public boolean ActividadExiste(String actividad){
		String query = "exec sp_actividad_duplicada '"+actividad+"'";
		
		boolean existe = false;
		Statement s;
		ResultSet rs;
		
		try {				
			s = con.conexion().createStatement();
			rs = s.executeQuery(query);
			
			while(rs.next()){
				existe = Boolean.parseBoolean(rs.getString("Existe").trim());
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
			
		return existe;
	}
	
	
	public String ActividadExisteNameOld(int actividad){
		String query = "exec sp_select_actividad_nombre "+actividad;
		
		String nombre = "";
		Statement s;
		ResultSet rs;
		
		try {				
			s = con.conexion().createStatement();
			rs = s.executeQuery(query);
			
			while(rs.next()){
				nombre = rs.getString("actividad").trim();
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
			
		return nombre;
	}
	
	public String ActividadExisteNameOldJerarquica(int actividad){
		String query = "exec sp_select_actividad_nombre_jerarquico "+actividad;
		String nombre = "";
		Statement s;
		ResultSet rs;
		
		try {				
			s = con.conexion().createStatement();
			rs = s.executeQuery(query);
			
			while(rs.next()){
				nombre = rs.getString("actividad").trim();
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
			
		return nombre;
	}
	
	public boolean ActividadExisteJerarquico(int actividad){
		String query = "exec [sp_folio_actividad_jerarquico] "+actividad;
		
		boolean existe = false;
		Statement s;
		ResultSet rs;
		
		try {				
			s = con.conexion().createStatement();
			rs = s.executeQuery(query);
			
			while(rs.next()){
				existe = Boolean.parseBoolean(rs.getString("Existe").trim());
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
			
		return existe;
	}
	
	public boolean ActividadExisteJerarquico(String actividad){
		String query = "exec [sp_actividad_jerarquico_duplicada] '"+actividad+"'";
		
		boolean existe = false;
		Statement s;
		ResultSet rs;
		
		try {				
			s = con.conexion().createStatement();
			rs = s.executeQuery(query);
			
			while(rs.next()){
				existe = Boolean.parseBoolean(rs.getString("Existe").trim());
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
			
		return existe;
	}
	
	public String ActividadExisteJerarquicoNameOld(int actividad){
		String query = "exec [sp_select_actividad_jerarquica_nombre] '"+actividad+"'";
		
		String existe = "";
		Statement s;
		ResultSet rs;
		
		try {				
			s = con.conexion().createStatement();
			rs = s.executeQuery(query);
			
			while(rs.next()){
				existe = rs.getString("actividad").trim();
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
			
		return existe;
	}
	
	public String puestosDependientes(){
		Obj_Usuario user = new Obj_Usuario().LeerSession();
		String query = "exec sp_puestos_dependientes '"+ procesa_texto(user.getNombre_completo()) +"';";
		
		String existe = "";
		Statement s;
		ResultSet rs;
		
		try {				
			s = con.conexion().createStatement();
			rs = s.executeQuery(query);
			
			while(rs.next()){
				existe = rs.getString("puesto_dependiente") + ", ";
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		if(existe.length() > 0){
			existe = existe.substring(0, existe.length()-2);
		}else{
			existe = "";
		}
		return existe;
	}
	
	public String procesa_texto(String texto) {
        StringTokenizer tokens = new StringTokenizer(texto);
        texto = "";
        while(tokens.hasMoreTokens()){
            texto += " "+tokens.nextToken();
        }
        texto = texto.toString();
        texto = texto.trim().toUpperCase();
        return texto;
    }

	public Obj_Directorios BucarDirectorios(int folio) throws SQLException{
		Obj_Directorios directorio = new Obj_Directorios();
		String query = "select * from tb_direccion_telefonicos where folio_empleado ="+ folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				directorio.setFolio(rs.getInt("folio"));
				directorio.setFolio_empleado(rs.getInt("folio_empleado"));
				directorio.setNombre(rs.getString("nombre"));
				directorio.setTelefono(rs.getString("numero"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt != null){stmt.close();}
		}
		return directorio;
	}
	
	//agregar buscar nivel_gerarquico
	public Obj_Nivel_Jerarquico Gerarquico(int folio) throws SQLException{
		Obj_Nivel_Jerarquico pond = new Obj_Nivel_Jerarquico();
		String query = "exec sp_nivel_gerarquico "+ folio;
		
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				pond.setFolio(rs.getInt("folio"));
				pond.setDescripcion(rs.getString("descripcion"));
				pond.setPuesto_dependiente(rs.getString("puestodependiente"));
				pond.setPuesto_principal(rs.getString("puestoprincipal"));
				pond.setPuesto(rs.getString("puesto"));
				pond.setEstablecimiento(rs.getString("establecimiento"));
				pond.setStatus((rs.getString("status").equals("1"))?true:false);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
			
		}
		return pond;
	}

	public boolean OpRespuesta_Existe(String Nombre) throws SQLException{
		boolean respuesta = false;
		String query = "exec sp_existe_opcion_respuesta '"+Nombre+"';";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				respuesta = rs.getBoolean("Existe");

			}
		
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		finally{
			if(stmt!=null){stmt.close();
			}
		}
		return respuesta;
	}

	public Obj_Nivel_Jerarquico Gerarquico_nuevo() throws SQLException{
		Obj_Nivel_Jerarquico pond = new Obj_Nivel_Jerarquico();
		String query = "select max(folio) as 'Maximo' from tb_Nivel_gerarquico";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				pond.setFolio(rs.getInt("Maximo"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return pond;
	}
	
	public Obj_Actividad Buscar_Actividad(int folio) throws SQLException{
		Obj_Actividad actividad = new Obj_Actividad();
		String query = "exec sp_select_actividad "+folio+";";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				actividad.setActividad(rs.getString("actividad"));
				actividad.setDescripcion(rs.getString("descripcion"));
				actividad.setRespuesta(rs.getString("respuesta"));
				actividad.setAtributos(rs.getString("atributo"));
				actividad.setNivel_critico(rs.getString("critico"));
				actividad.setTemporada(rs.getString("temporada"));
				actividad.setCarga(rs.getInt("carga") == 1 ? true : false);
				actividad.setRepetir(rs.getInt("repetir"));
				actividad.setStatus(rs.getString("status").equals("1") ? true : false);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return actividad;
	}
	
	public Obj_Actividad_Asignadas_Nivel_Jerarquico Buscar_Actividad_Nivel_Jerarquico(int folio) throws SQLException{
		Obj_Actividad_Asignadas_Nivel_Jerarquico actividad = new Obj_Actividad_Asignadas_Nivel_Jerarquico();
		String query = "exec sp_select_actividad_nivel_jerarquico "+folio+";";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				actividad.setActividad(rs.getString("actividad"));
				actividad.setDescripcion(rs.getString("descripcion"));
				actividad.setRespuesta(rs.getString("respuesta"));
				actividad.setAtributos(rs.getString("atributo"));
				actividad.setNivel_critico(rs.getString("critico"));
				actividad.setTemporada(rs.getString("temporada"));
				actividad.setCarga(rs.getInt("carga") == 1 ? true : false);
				actividad.setRepetir(rs.getInt("repetir"));
				actividad.setStatus(rs.getString("status").equals("1") ? true : false);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return actividad;
	}
	
	public boolean existeActividadRelacionada(int relacion) throws SQLException{
		boolean resultado = false;
		String query = "exec sp_existe_relacion_actividad "+relacion+";";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
		    ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				resultado = rs.getBoolean(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error");
			return false;
		}
		finally{
			 if (stmt != null) { stmt.close(); }
		}
		return resultado;
	}
	
	public boolean existeActividadRelacionada(String relacion) throws SQLException{
		boolean resultado = false;
		String query = "exec sp_existe_relacion_actividad_nombre '"+relacion+"';";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
		    ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				resultado = rs.getBoolean(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error");
			return false;
		}
		finally{
			 if (stmt != null) { stmt.close(); }
		}
		return resultado;
	}
	
	public boolean existeProyecto(int proyecto) throws SQLException{
		boolean resultado = false;
		String query = "exec sp_existe_proyecto "+proyecto+";";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
		    ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				resultado = rs.getBoolean(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error");
			return false;
		}
		finally{
			 if (stmt != null) { stmt.close(); }
		}
		return resultado;
	}
	
	public boolean existeProyecto(String proyecto) throws SQLException{
		boolean resultado = false;
		String query = "exec sp_existe_proyecto_nombre '"+proyecto+"';";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
		    ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				resultado = rs.getBoolean(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error");
			return false;
		}
		finally{
			 if (stmt != null) { stmt.close(); }
		}
		return resultado;
	}
	
	public boolean existeCuadrante(int cuadrante) throws SQLException{
		boolean resultado = false;
		String query = "exec sp_existe_cuadrante "+cuadrante+";";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
		    ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				resultado = rs.getBoolean(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error");
			return false;
		}
		finally{
			 if (stmt != null) { stmt.close(); }
		}
		return resultado;
	}
	
	public boolean existeCuadrante(String cuadrante) throws SQLException{
		boolean resultado = false;
		String query = "exec sp_existe_cuadrante_nombre '"+cuadrante+"';";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
		    ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				resultado = rs.getBoolean(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error");
			return false;
		}
		finally{
			 if (stmt != null) { stmt.close(); }
		}
		return resultado;
	}
	
	public Obj_Actividades_Relacionadas Actividades_Relacionadas(int folio) throws SQLException{
		Obj_Actividades_Relacionadas relacion = new Obj_Actividades_Relacionadas();
		String query = "exec sp_select_relacion_actividad_folio "+ folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
		    ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				relacion.setProyecto(rs.getString(1));
				relacion.setDescripcion(rs.getString(2));
				relacion.setNivel_critico(rs.getString(3));
				relacion.setStatus(rs.getInt(4));
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error");
			return null;
		}
		finally{
			 if (stmt != null) { stmt.close(); }
		}
		return relacion;
	}

	public Obj_Actividades_Por_Proyecto ProyectoCuadrante(int folio) throws SQLException{
		Obj_Actividades_Por_Proyecto proyect = new Obj_Actividades_Por_Proyecto();
		String query = "exec sp_select_proyecto_cuadrante_folio "+ folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
		    ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				proyect.setProyecto(rs.getString(1));
				proyect.setDescripcion(rs.getString(2));
				proyect.setNivel_critico(rs.getString(3));
				proyect.setStatus(rs.getInt(4));
				proyect.setFecha_inicial(rs.getString(5));
				proyect.setFecha_final(rs.getString(6));
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error");
			return null;
		}
		finally{
			 if (stmt != null) { stmt.close(); }
		}
		return proyect;
	}
	
	public String[][] getTablaActividadesRelacionadas(int folio_proyecto){
		String[][] Matriz = null;
		
		String datosif = "exec sp_select_tabla_relacion_actividad " + folio_proyecto;
		
		Matriz = new String[getFilas(datosif)][5];
		Statement s;
		ResultSet rs;
		try {			
			s = con.conexion().createStatement();
			rs = s.executeQuery(datosif);
			int i=0;
			while(rs.next()){
				Matriz[i][0] = rs.getString(1);
				Matriz[i][1] = rs.getString(2);
				Matriz[i][2] = rs.getString(3);
				Matriz[i][3] = rs.getString(4);
				Matriz[i][4] = rs.getString(5);
				
				i++;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		return Matriz;
	}
	
	public String[][] getTabla(int folio_proyecto){
		String[][] Matriz = null;
		
		String datosif = "exec sp_select_tabla_proyecto_cuadrante " + folio_proyecto;
		
		Matriz = new String[getFilas(datosif)][5];
		Statement s;
		ResultSet rs;
		try {			
			s = con.conexion().createStatement();
			rs = s.executeQuery(datosif);
			int i=0;
			while(rs.next()){
				Matriz[i][0] = rs.getString(1);
				Matriz[i][1] = rs.getString(2);
				Matriz[i][2] = rs.getString(3);
				Matriz[i][3] = rs.getString(4);
				Matriz[i][4] = rs.getString(5);
				
				i++;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		return Matriz;
	}
	
	public String[][] getTablaCapturaFuenteSodas(String clave){
		String[][] Matriz = null;
		
		String datosif = "exec sp_select_tabla_captura_fuente_sodas '"+clave+"'";
		
		Matriz = new String[getFilas(datosif)][5];
		Statement s;
		ResultSet rs;
		try {			
			s = con.conexion().createStatement();
			rs = s.executeQuery(datosif);
			int i=0;
			while(rs.next()){
				Matriz[i][0] = rs.getString(1);
				Matriz[i][1] = rs.getString(2);
				Matriz[i][2] = rs.getString(3);
				Matriz[i][3] = rs.getString(4);
				Matriz[i][4] = rs.getString(5);
				
				i++;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		return Matriz;
	}
	
	public String[][] getTablaTicketFuenteSodas(int folio){
		String[][] Matriz = null;
		
		String datosif = "exec sp_acumulado_ticket_fuente_de_sodas_por_empleado "+folio;
		
		Matriz = new String[getFilas(datosif)][3];
		Statement s;
		ResultSet rs;
		try {			
			s = con.conexion().createStatement();
			rs = s.executeQuery(datosif);
			int i=0;
			while(rs.next()){
				Matriz[i][0] = rs.getString(1);
				Matriz[i][1] = rs.getString(2);
				Matriz[i][2] = rs.getString(3);
				
				i++;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		return Matriz;
	}
	
	public String[][] getTablaTicketFuenteSodas_dh(int folio){
		String[][] Matriz = null;
		
		String datosif = "exec sp_acumulado_ticket_fuente_de_sodas_por_empleado_dh "+folio;
		
		Matriz = new String[getFilas(datosif)][4];
		Statement s;
		ResultSet rs;
		try {			
			s = con.conexion().createStatement();
			rs = s.executeQuery(datosif);
			int i=0;
			while(rs.next()){
				Matriz[i][0] = rs.getString(1);
				Matriz[i][1] = rs.getString(2);
				Matriz[i][2] = rs.getString(3);
				Matriz[i][3] = rs.getString(4);
				
				i++;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en Buscarsql  en la funcion getTablaTicketFuenteSodas_dh store procedure sp_acumulado_ticket_fuente_de_sodas_por_empleado_dh  "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);

		}
		
		return Matriz;
	}
	
	public String[][] getTicket_FS_Para_Cortes(int folio){
		String[][] Matriz = null;
		
		String datosif = "exec sp_ticket_fuente_de_sodas_por_cajero "+folio;
		
		Matriz = new String[getFilas(datosif)][4];
		Statement s;
		ResultSet rs;
		try {			
			s = con.conexion().createStatement();
			rs = s.executeQuery(datosif);
			int i=0;
			while(rs.next()){
				Matriz[i][0] = rs.getString(1);
				Matriz[i][1] = rs.getString(2);
				Matriz[i][2] = rs.getString(3);
				Matriz[i][3] = rs.getString(4);
				
				i++;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		return Matriz;
	}
	
	public Obj_Cuadrante Cuadrante(int folio) throws SQLException{
		Obj_Cuadrante cuadrante = new Obj_Cuadrante();
		String query = "exec sp_select_cuadrante_folio "+ folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
		    ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				cuadrante.setCuadrante(rs.getString(1));
				cuadrante.setPerfil(rs.getString(2));
				cuadrante.setJefatura(rs.getString(3));
				cuadrante.setNivel_gerarquico(rs.getString(4));
				cuadrante.setEquipo_trabajo(rs.getString(5));
				cuadrante.setEstablecimiento(rs.getString(6));
				cuadrante.setDomingo(rs.getInt(7));
				cuadrante.setLunes(rs.getInt(8));
				cuadrante.setMartes(rs.getInt(9));
				cuadrante.setMiercoles(rs.getInt(10));
				cuadrante.setJueves(rs.getInt(11));
				cuadrante.setViernes(rs.getInt(12));
				cuadrante.setSabado(rs.getInt(13));
				cuadrante.setStatus(rs.getInt(14));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error");
			return null;
		}
		finally{
			 if (stmt != null) { stmt.close(); }
		}
		return cuadrante;
	}
	
	
	public String[][] getTablaDias(int cuadrante){
		String[][] Matriz = null;
		
		String datosif = "exec sp_select_tabla_cuadrante " + cuadrante;
		
		Matriz = new String[getFilas(datosif)][7];
		Statement s;
		ResultSet rs;
		try {			
			s = con.conexion().createStatement();
			rs = s.executeQuery(datosif);
			int i=0;
			while(rs.next()){
				Matriz[i][0] = rs.getString(1);
				Matriz[i][1] = rs.getString(2)+"  ";
				Matriz[i][2] = "   "+rs.getString(3);
				Matriz[i][3] = "   "+rs.getString(4);
				Matriz[i][4] = rs.getString(5);
				Matriz[i][5] = rs.getString(6);
				Matriz[i][6] = rs.getString(7);
				i++;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		return Matriz;
	}
	
	public String[][] getTablaActividadesCuadrante(int folio){
		String[][] Matriz = null;
		
		String datosif = "exec sp_select_tabla_cuadrante_actividad " + folio;
		
		Matriz = new String[getFilas(datosif)][7];
		Statement s;
		ResultSet rs;
		try {			
			s = con.conexion().createStatement();
			rs = s.executeQuery(datosif);
			int i=0;
			while(rs.next()){
				Matriz[i][0] = rs.getString(1);
				Matriz[i][1] = rs.getString(2)+"  ";
				Matriz[i][2] = "   "+rs.getString(3);
				Matriz[i][3] = "   "+rs.getString(4);
				Matriz[i][4] = rs.getString(5);
				Matriz[i][5] = rs.getString(6);
				Matriz[i][6] = rs.getString(7);
				i++;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		return Matriz;
	}
	
	public String OpNivel() throws SQLException{
		String numero  ="";
		String query = "exec sp_nivel_jerarquico";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				numero = rs.getString("Maximo");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return numero;
	}
	
	public Obj_Nivel_Jerarquico buscarnivel(int folio) throws SQLException{
		Obj_Nivel_Jerarquico nivel_gerarquico = new Obj_Nivel_Jerarquico();
		String query = "select tb_nivel_jerarquico.folio as folio" +
						",tb_nivel_jerarquico.descripcion as descripcion" +
						",tb_puesto.folio as folio_puesto_principal " +
						",tb_puesto.nombre as puesto_principal " +
						"from tb_nivel_jerarquico " +
						"inner join tb_puesto on tb_puesto.folio=tb_nivel_jerarquico.puesto_principal " +
						"where tb_nivel_jerarquico.folio ="+ folio;
		
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				nivel_gerarquico.setFolio(rs.getInt("folio"));
				
				nivel_gerarquico.setDescripcion(rs.getString("descripcion"));
				nivel_gerarquico.setFolio_puesto_principal(rs.getInt("folio_puesto_principal"));
				nivel_gerarquico.setPuesto_principal(rs.getString("puesto_principal"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return nivel_gerarquico;
	}

	public Obj_Nivel_Jerarquico buscarDescripcion(String descripcion) throws SQLException{
		Obj_Nivel_Jerarquico nivel_gerarquico = new Obj_Nivel_Jerarquico();
		String query = "select tb_nivel_jerarquico.folio as folio, descripcion " +
		                                            
					   "from tb_nivel_jerarquico " +
					   "where tb_nivel_jerarquico.descripcion = "+"'"+descripcion+"'";
				
		
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				nivel_gerarquico.setFolio(rs.getInt("folio"));
				nivel_gerarquico.setDescripcion(rs.getString("descripcion"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return nivel_gerarquico;
	}
	
	public Obj_Nivel_Jerarquico buscarPDependiente(Object nombrePuestoDependiente) throws SQLException{
		Obj_Nivel_Jerarquico nivel_gerarquico = new Obj_Nivel_Jerarquico();
//		String query2 = "select tb_nivel_jerarquico.folio as folio" +
//						",tb_nivel_jerarquico.descripcion as descripcion" +
//						",tb_puesto.nombre as puesto_principal " +
//						"from tb_nivel_jerarquico " +
//						"inner join tb_puesto on tb_puesto.folio=tb_nivel_jerarquico.puesto_principal " +
//						"where tb_nivel_jerarquico.folio ="+ folio;
		
		String query = "select tb_tabla_nivel_jerarquico where nombre = "+nombrePuestoDependiente;
//		String query = "delete tb_tabla_nivel_jerarquico where nombre = "+nombrePuestoDependiente;
		
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				nivel_gerarquico.setFolio(rs.getInt("folio"));
				
				nivel_gerarquico.setDescripcion(rs.getString("descripcion"));
				nivel_gerarquico.setPuesto_principal(rs.getString("puesto_principal"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return nivel_gerarquico;
	}
	
	public Obj_Nivel_Jerarquico buscartablanivel(int folio) throws SQLException{
		Obj_Nivel_Jerarquico nivel_gerarquico = new Obj_Nivel_Jerarquico();
		String query = "select * from tb_tabla_nivel_jerarquico where folio ="+ folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				nivel_gerarquico.setFolio(rs.getInt("folio"));
				
				nivel_gerarquico.setPuesto_dependiente(rs.getString("puesto"));
				nivel_gerarquico.setEstablecimiento(rs.getString("establecimiento"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return nivel_gerarquico;
	}
	
	public Obj_Empleados_En_Cuadrantes buscar_empleado_cuadrante(int folio) throws SQLException{
		Obj_Empleados_En_Cuadrantes empleado_cuadrante = new Obj_Empleados_En_Cuadrantes();
		String query = "exec sp_select_buscar_empleado_cuadrante "+ folio;
		
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				empleado_cuadrante.setFolio(rs.getInt("folio"));
				empleado_cuadrante.setCuadrante(rs.getString("cuadrante"));
				empleado_cuadrante.setStatus(rs.getInt("status")==1 ? true : false);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return empleado_cuadrante;
	}
	
	public Obj_Empleados_En_Cuadrantes buscar_cuadrante_con_empleado(int folio) throws SQLException{
		Obj_Empleados_En_Cuadrantes empleado_cuadrante = new Obj_Empleados_En_Cuadrantes();
		String query = "exec sp_select_empleado_con_cuadrante "+ folio;
		
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				empleado_cuadrante.setFolio(rs.getInt("folio"));
				empleado_cuadrante.setCuadrante(rs.getString("cuadrante"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return empleado_cuadrante;
	}
	
	public Obj_Mensaje_Personal buscar_mensaje(int folio) throws SQLException{
		Obj_Mensaje_Personal MsjPersonal = new Obj_Mensaje_Personal();
		String query = "exec sp_select_mensaje "+ folio;
		
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				MsjPersonal.setFolioMensaje(rs.getInt("folio_mensaje"));
				MsjPersonal.setAsunto(rs.getString("asunto"));
				MsjPersonal.setFechaInicial(rs.getString("fecha_inicio"));
				MsjPersonal.setFechaFin(rs.getString("fecha_fin"));
				MsjPersonal.setMensaje(rs.getString("mensaje"));
				MsjPersonal.setColor_fuente(rs.getString("color_fuente"));
				MsjPersonal.setRuta_imagen_mensaje(rs.getString("ruta_imagen_mensaje"));
				MsjPersonal.setStatus(rs.getInt("status") );
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return MsjPersonal;
	}
	
	public Obj_Alimentacion_De_Permisos_A_Empleados buscar_permiso(int folio) throws SQLException{
		Obj_Alimentacion_De_Permisos_A_Empleados permisoChecador = new Obj_Alimentacion_De_Permisos_A_Empleados();
            String query = "exec sp_select_permiso_checador "+ folio;
		
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				
				permisoChecador.setFolio(rs.getInt("folio"));
				permisoChecador.setFolio_empleado(rs.getInt("folio_empleado"));
				permisoChecador.setNombre_empleado(rs.getString("nombre_empleado"));
				permisoChecador.setFolio_usuario(rs.getInt("folio_usuario"));
				permisoChecador.setFecha(rs.getString("fecha_permiso"));
				permisoChecador.setTipo_de_permiso(rs.getInt("tipo_de_permiso"));
				permisoChecador.setDescanso(rs.getInt("trabajar_como_el_dia"));
				permisoChecador.setTiempo_comida(rs.getString("tiempo_comida"));
				permisoChecador.setMotivo(rs.getString("motivo"));
				permisoChecador.setStatus(rs.getInt("status")==1?true:false);
				permisoChecador.setFolio_empleado_optener_turno(rs.getInt("folio_empleado_usar_turno"));
				permisoChecador.setSolicito(rs.getInt("solicito"));
				permisoChecador.setEstablecimiento(rs.getString("establecimiento"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion [ buscar_permiso ] update  SQLException: sp_select_permiso_checador "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return permisoChecador;
	}
	
	public Obj_Alimentacion_De_Permisos_A_Empleados comparacionDeFecha(String fecha) throws SQLException{
		Obj_Alimentacion_De_Permisos_A_Empleados permisoChecador = new Obj_Alimentacion_De_Permisos_A_Empleados();
        String query = "exec sp_comparar_fecha_permiso "+ "'"+fecha+"'";
		
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				permisoChecador.setFecha(rs.getString("trae_fecha"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return permisoChecador;
	}
	
	public int NuevoEmpleadoCuadrante() throws SQLException{
		int folio = 0;
		String query = "exec sp_nuevo_empleado_cuadrante";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				folio =  rs.getInt("Maximo");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return folio;
	}
	
	public int NuevoMensajePersonal() throws SQLException{
		int folio = 0;
		String query = "exec sp_nuevo_mensaje";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				folio =  rs.getInt("Maximo");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return folio;
	}
	
	public int NuevoPermisoChecador() throws SQLException{
		int folio = 0;
		String query = "exec sp_nuevo_permiso_checador";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				folio =  rs.getInt("Maximo");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return folio;
	}
	
	public boolean existeEmpleadoCuadrante(int folio) throws SQLException{
		boolean existe = false;
		String query = "exec sp_existe_empleado_cuadrante "+folio;
		
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				existe = rs.getBoolean("existe");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return existe;
	}
	
	public String[][] getTablaEmpleadoCuadrante(int folio){
		String datos = "exec sp_select_tabla_empleado_cuadrante "+folio;
		
		String[][] Matriz = new String[getFilas(datos)][2];
		Statement s;
		ResultSet rs;
		try {			
			s = con.conexion().createStatement();
			rs = s.executeQuery(datos);
			int i=0;
			while(rs.next()){
				Matriz[i][0] = rs.getString(1);
				Matriz[i][1] = rs.getString(2);
				i++;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return Matriz; 
	}
	
	@SuppressWarnings({ "rawtypes", "resource", "unchecked" })
	public Obj_Usuario getSession() throws IOException {
		Vector myVector = new Vector();
		Obj_Usuario usuario = new Obj_Usuario();
		try{
			FileReader archivo = new FileReader(System.getProperty("user.dir")+"\\Config\\users");
			BufferedReader bufferedWriter = new BufferedReader(archivo);
			String cadena = "";
			while( (cadena = bufferedWriter.readLine()) !=null)
				myVector.addElement(cadena);
				usuario.setFolio(Integer.parseInt(myVector.get(0).toString()));
				usuario.setNombre_completo(myVector.get(1).toString());
		}catch(FileNotFoundException e) {
			System.out.println(e.getMessage());
			return usuario=null;
		}
		return usuario;
	}
	
	public Obj_Captura_Del_Cuadrante_Personal EmpleadoNombre(String nombre) throws SQLException{
		Obj_Captura_Del_Cuadrante_Personal empleado_cuadrante = new Obj_Captura_Del_Cuadrante_Personal();
		String query = "exec sp_select_cuadrante_empleado '"+nombre+"'";

		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				empleado_cuadrante.setNombre(rs.getString("Nombre"));
				empleado_cuadrante.setPuesto(rs.getString("Puesto"));
				empleado_cuadrante.setEstablecimiento(rs.getString("Establecimiento"));
				empleado_cuadrante.setEquipo_trabajo(rs.getString("Equipo_Trabajo"));
				empleado_cuadrante.setJefatura(rs.getString("Jefatura"));
				empleado_cuadrante.setDia(rs.getString("Dia"));
				empleado_cuadrante.setFecha(rs.getString("Fecha"));
				empleado_cuadrante.setCuadrante(rs.getString("Cuadrante"));
				
				File photo = new File(System.getProperty("user.dir")+"/tmp/tmp_cuadrante/tmp.jpg");
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
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return empleado_cuadrante;
	}
	
	public String[][] tabla_alimentacion_cuadrante_libre(String nombre, String dia){
		String[][] Matriz = null;
		
		String datosif = "exec sp_select_tabla_alimentacion_libre '"+nombre+"', '"+dia+"';";
			
		Matriz = new String[getFilas(datosif)][3];
		Statement s;
		ResultSet rs;
		try {			
			s = con.conexion().createStatement();
			rs = s.executeQuery(datosif);
			int i=0;
			while(rs.next()){

				Matriz[i][0] = rs.getString(1);
				Matriz[i][1] = rs.getString(2);
				
				i++;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return Matriz;
	}
	
	public String[][] tabla_alimentacion_cuadrante_multiple_jerarquico(String nomgbre){
		String[][] Matriz = null;
		
		String datosif = "exec sp_select_tabla_alimentacion_multiple_jerar '"+nomgbre+"';";

		Matriz = new String[getFilas(datosif)][4];
		Statement s;
		ResultSet rs;
		try {			
			s = con.conexion().createStatement();
			rs = s.executeQuery(datosif);
			int i=0;
			while(rs.next()){
				Matriz[i][0] = rs.getString(1);
				Matriz[i][1] = rs.getString(2);
				Matriz[i][2] = rs.getString(3);
				i++;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return Matriz;
	}
	
	public String[][] tabla_alimentacion_cuadrante_multiple(String nomgbre){
		String[][] Matriz = null;
		
		String datosif = "exec sp_select_tabla_alimentacion_multiple '"+nomgbre+"';";
		String datosfi = "exec sp_select_tabla_alimentacion_multiple_por_proyecto '"+nomgbre+"';";
		
		Matriz = new String[getFilas(datosif)+getFilas(datosfi)][4];
		Statement s;
		Statement s1;
		ResultSet rs;
		ResultSet rs1;
		
		try {			
			s = con.conexion().createStatement();
			rs = s.executeQuery(datosif);
			
			s1 = con.conexion().createStatement();
			rs1 = s1.executeQuery(datosfi);
			
			int i=0;
			while(rs.next()){

				Matriz[i][0] = rs.getString(1);
				Matriz[i][1] = rs.getString(2);
				Matriz[i][2] = rs.getString(3);
				
				i++;
			}
			while(rs1.next()){
				Matriz[i][0] = rs1.getString(1);
				Matriz[i][1] = "N/A";
				Matriz[i][2] = rs1.getString(3);
				
				i++;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return Matriz;
	}
	
	public String[][] tabla_alimentacion_cuadrante_multiple_capturada(String nombre){
		String[][] Matriz = null;
		String datosif = "exec sp_select_tabla_alimentacion_multiple_capturada '"+nombre+"';";

		Matriz = new String[getFilas(datosif)][6];
		Statement s;
		ResultSet rs;
		try {			
			s = con.conexion().createStatement();
			rs = s.executeQuery(datosif);
			int i=0;
			while(rs.next()){

				Matriz[i][0] = rs.getString(1);
				Matriz[i][1] = rs.getString(2).equals("0") ? "N/A" : rs.getString(2);
				Matriz[i][2] = rs.getString(3);
				Matriz[i][3] = rs.getString(4);
				Matriz[i][4] = rs.getString(5);
				i++;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return Matriz;
	}
	
	public String[][] tabla_alimentacion_cuadrante_multiple_capturada_jerarquica(String nombre){
		String[][] Matriz = null;
		String datosif = "exec sp_select_tabla_alimentacion_multiple_capturada_jerarquica '"+nombre+"';";

		Matriz = new String[getFilas(datosif)][6];
		Statement s;
		ResultSet rs;
		try {			
			s = con.conexion().createStatement();
			rs = s.executeQuery(datosif);
			int i=0;
			while(rs.next()){

				Matriz[i][0] = rs.getString(1);
				Matriz[i][1] = rs.getString(2).equals("0") ? "N/A" : rs.getString(2);
				Matriz[i][2] = rs.getString(3);
				Matriz[i][3] = rs.getString(4);
				Matriz[i][4] = rs.getString(5);
				i++;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return Matriz;
	}
	
	public String[][] tabla_alimentacion_cuadrante_primera_parte(String nombre){
		String[][] Matriz = null;
		String datosif = "exec sp_pre_captura_cuadrante '"+nombre+"';";
		System.out.println(datosif);
		Matriz = new String[getFilas(datosif)][5];
		Statement s;
		ResultSet rs;
		try {			
			s = con.conexion().createStatement();
			rs = s.executeQuery(datosif);
			int i=0;
			while(rs.next()){

				Matriz[i][0] = rs.getString(1);
				Matriz[i][1] = rs.getString(2);
				Matriz[i][2] = rs.getString(3);
				Matriz[i][3] = rs.getString(4);
				Matriz[i][4] = rs.getString(5);
				i++;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return Matriz;
	}
	

	
	public Obj_Opciones_De_Respuestas buscar_opcion_respuesta(int folio) throws SQLException{
		Obj_Opciones_De_Respuestas respuesta = new Obj_Opciones_De_Respuestas();
		String query = "exec sp_select_opcion_respuesta "+folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				respuesta.setFolio(rs.getInt("folio"));
				respuesta.setNombre(rs.getString("nombre"));
				respuesta.setTipo_opcion(rs.getInt("tipo_opcion") == 0 ? "Opci�n Libre" : "Opci�n M�ltiple");
				respuesta.setStatus(rs.getInt("status") == 0 ? false : true);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return respuesta;
	}
	
	public boolean buscar_respuesta_folio(int folio) throws SQLException{
		String query = "exec sp_select_folio_opcion_respuesta "+ folio;
		boolean sentencia = false;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
		    ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				sentencia = rs.getBoolean(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error");
			return false;
		}
		finally{
			 if (stmt != null) { stmt.close(); }
		}
		return sentencia;
	}
	
	
	public boolean isFoto(int folio) throws SQLException{
		String query = "exec sp_existe_foto"+ folio;
		boolean sentencia = false;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				sentencia = rs.getBoolean(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		finally{
			if(stmt != null){stmt.close();}
		}
		return sentencia;
	}

	public boolean existe_submenu(String nombre) {
		//se manda el nombre para el procedimiento almacenado
		String query = "exec sp_comprobar_submenus '" + nombre+"'";
		boolean sentencia = false;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				sentencia = rs.getBoolean(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		finally{
			if(stmt != null){try {
				stmt.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}}
		}
		return sentencia;
	
	}
	
	public int[]  hora_minut_segundo(){
		int[] horas = new int[3];
		
		String query = "exec sp_hora_sincronizacion";
		Statement stmt = null;
		
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				horas[0] = rs.getInt(1);
				horas[1] = rs.getInt(2);
				horas[2] = rs.getInt(3);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(stmt != null){try {
				stmt.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}}
		}
		return horas;
	}
	
	public boolean Existe_Cuadrante_Muliple(String nombre){
		String query = "exec sp_existe_respuesta_cuadrante '" + nombre + "';";
		boolean disponible = false;
		try {				
			Statement s = con.conexion().createStatement();
			ResultSet rs = s.executeQuery(query);
			
			while(rs.next()){
				disponible = rs.getBoolean(1);
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
			
		return disponible;
	}
	
	//Buscar Mensaje
	
		public Obj_Mensajes mensaje(int folio) throws SQLException{
			Obj_Mensajes mensaje = new Obj_Mensajes();
			String query = "select * from tb_mensajes where folio ="+ folio;
			Statement stmt = null;
			try {
				stmt = con.conexion().createStatement();
				ResultSet rs = stmt.executeQuery(query);
				while(rs.next()){
					mensaje.setFolio(rs.getInt("folio"));
					mensaje.setMensaje(rs.getString("mensaje"));
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			finally{
				if(stmt!=null){stmt.close();}
			}
			return mensaje;
		}
		
		//agregar nuevo mensaje
		public Obj_Mensajes Mensaje_Nuevo() throws SQLException{
			Obj_Mensajes mensaje = new Obj_Mensajes();
			String query = "select max(folio) as 'Maximo' from tb_mensajes";
			Statement stmt = null;
			try {
				stmt = con.conexion().createStatement();
				ResultSet rs = stmt.executeQuery(query);
				while(rs.next()){
					mensaje.setFolio(rs.getInt("Maximo"));
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			finally{
				if(stmt!=null){stmt.close();}
			}
			return mensaje;
		}
		
		/////
		public Obj_Asignacion_Mensajes Mensaje_New() throws SQLException{
			Obj_Asignacion_Mensajes mensajes = new Obj_Asignacion_Mensajes();
			String query = "select max(folio) as 'Maximo' from tb_asignacion_mensaje";
			Statement stmt = null;
			try {
				stmt = con.conexion().createStatement();
				ResultSet rs = stmt.executeQuery(query);
				while(rs.next()){
					mensajes.setFolio(rs.getInt("Maximo"));
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			finally{
				if(stmt!=null){stmt.close();}
			}
			return mensajes;
		}
		
		//Buscar el folio de la asignacion del mensaje
		
			public Obj_Asignacion_Mensajes Asignacion(int folio) throws SQLException{
				Obj_Asignacion_Mensajes mensaje = new Obj_Asignacion_Mensajes();
				String query = "select * from tb_asignacion_mensaje where folio ="+ folio;
				Statement stmt = null;
				try {
					stmt = con.conexion().createStatement();
					ResultSet rs = stmt.executeQuery(query);
					while(rs.next()){
						mensaje.setFolio(rs.getInt("folio"));
						mensaje.setNo_mensajes(rs.getInt("mensaje"));
						mensaje.setMensaje(rs.getString("mensajearea"));
						mensaje.setPuesto(rs.getString("puesto"));
						mensaje.setEquipo(rs.getString("equipo"));
						mensaje.setJefatura(rs.getString("jefatura"));
						mensaje.setEmpleado(rs.getString("empleado"));
						
						
						mensaje.setRbpuesto(rs.getBoolean("rbpuesto"));
						mensaje.setRbequipo(rs.getBoolean("rbequipo"));
						mensaje.setRbjefatura(rs.getBoolean("rbjefatura"));
						mensaje.setRbempleado(rs.getBoolean("rbempleado"));
					}
					
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
				finally{
					if(stmt!=null){stmt.close();}
				}
				return mensaje;
			}
	
//	buscar entosal
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Vector buscar_entosal(int folio) throws SQLException{
		Vector fila = new Vector();
		String query = "exec sp_select_entosal "+ folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
		    ResultSet rs = stmt.executeQuery(query);
		    
			while(rs.next()){
				fila.add(rs.getObject(1));
				fila.add(rs.getObject(2));
				fila.add(rs.getObject(3));
				fila.add(rs.getObject(4));
				fila.add(rs.getObject(5));
				fila.add(rs.getObject(6));
				fila.add(rs.getObject(7));
				fila.add(rs.getObject(8));
				fila.add(rs.getObject(9));
				fila.add(rs.getObject(10));
						
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error");
			JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion Vector buscar_entosal  store procedure sp_select_entosal: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		finally{
			 if (stmt != null) { stmt.close(); }
		}
		return fila;
	}
	
	public Obj_Entosal Entosal() throws SQLException{
		Obj_Entosal entosal = new Obj_Entosal();
		String query = "select * from tb_key_check_master";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				entosal.setClave(rs.getString("keyCheckMaster"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion Obj_Entosal  select * from tb_key_check_master: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return entosal;
	}
	
	public boolean existeColisionTiempo(int folio)throws SQLException{
		boolean existe = false;
		String query = "exec sp_valida_hora_recurrente_minuto "+folio;
		
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				existe = rs.getBoolean("valor");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion existeColisionTiempo buscar_entosal  store procedure sp_valida_hora_recurrente_minuto: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			return false;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return existe;
	}
	

	
	
	public boolean obtener_checadas_dia_dobla(int folio)throws SQLException{
		boolean descanso = false;
		String query = "sp_valida_cantidad_de_checadas_en_dia_dobla "+folio;
		
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				descanso = rs.getBoolean("valor");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion obtener_checadas_dia_dobla store procedure sp_valida_cantidad_de_checadas_en_dia_dobla: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return descanso;
	}
	public boolean valida_si_dobla_y_esta_saliendo_a_comer(int folio)throws SQLException{
		boolean dobla = false;
		String query = "exec sp_valida_checadas_vs_dia_dobla "+folio;
		
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				dobla = rs.getBoolean("valor");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion valida_si_dobla_y_esta_saliendo_a_comer store procedure sp_valida_checadas_vs_dia_dobla: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return dobla;
	}
	
	public Obj_Entosal IntentaChecarDiaDescanso(int folio)throws SQLException{
		Obj_Entosal desc_pc = new Obj_Entosal();
		String pc_nombre ="";
		
		try {
			pc_nombre = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
				
		String query = "exec sp_valida_checada_dia_descanso_y_Pc_Autorizada "+folio+",'"+pc_nombre+"'";
		
			Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				     desc_pc.setValor_Descanso(rs.getString("valor_descanso"));
				     desc_pc.setValor_Pc(rs.getString("valor_pc"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion IntentaChecarDiaDescanso y Validar PC store procedure sp_valida_checada_dia_descanso: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return desc_pc;
		  
	}
	
	//Buscamos el horario por su nombre
	public Obj_Horarios buscahorario(int folio) throws SQLException{
		Obj_Horarios horaa = new Obj_Horarios();
		String query = "exec sp_select_horarios "+folio;
		
		Statement stmt = null;
		try {
			
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				horaa.setFolio(rs.getInt("folio"));
				horaa.setNombre(rs.getString("nombre").trim());
				
				horaa.setDiaD(rs.getString("diaD").trim());
				horaa.setDomingo1(rs.getString("inicioD"));
				horaa.setDomingo2(rs.getString("finD"));
				horaa.setDomingo3(rs.getString("entradaD"));
				horaa.setDomingo4(rs.getString("salidaD"));
				horaa.setDomingo5(rs.getString("recesoD"));
				
				horaa.setDiaL(rs.getString("diaL").trim());
				horaa.setLunes1(rs.getString("inicioL"));
				horaa.setLunes2(rs.getString("finL"));
				horaa.setLunes3(rs.getString("entradaL"));
				horaa.setLunes4(rs.getString("salidaL"));
				horaa.setLunes5(rs.getString("recesoL"));
				
				horaa.setDiaM(rs.getString("diaM").trim());
				horaa.setMartes1(rs.getString("inicioM"));
				horaa.setMartes2(rs.getString("finM"));
				horaa.setMartes3(rs.getString("entradaM"));
				horaa.setMartes4(rs.getString("salidaM"));
				horaa.setMartes5(rs.getString("recesoM"));
				
				horaa.setDiaMI(rs.getString("diaMI").trim());
				horaa.setMiercoles1(rs.getString("inicioMI"));
				horaa.setMiercoles2(rs.getString("finMI"));
				horaa.setMiercoles3(rs.getString("entradaMI"));
				horaa.setMiercoles4(rs.getString("salidaMI"));
				horaa.setMiercoles5(rs.getString("recesoMI"));
				
				horaa.setDiaJ(rs.getString("diaJ").trim());
				horaa.setJueves1(rs.getString("inicioJ"));
				horaa.setJueves2(rs.getString("finJ"));
				horaa.setJueves3(rs.getString("entradaJ"));
				horaa.setJueves4(rs.getString("salidaJ"));
				horaa.setJueves5(rs.getString("recesoJ"));
				
				horaa.setDiaV(rs.getString("diaV").trim());
				horaa.setViernes1(rs.getString("inicioV"));
				horaa.setViernes2(rs.getString("finV"));
				horaa.setViernes3(rs.getString("entradaV"));
				horaa.setViernes4(rs.getString("salidaV"));
				horaa.setViernes5(rs.getString("recesoV"));
				
				horaa.setDiaS(rs.getString("diaS").trim());
				horaa.setSabado1(rs.getString("inicioS"));
				horaa.setSabado2(rs.getString("finS"));
				horaa.setSabado3(rs.getString("entradaS"));
				horaa.setSabado4(rs.getString("salidaS"));
				horaa.setSabado5(rs.getString("recesoS"));
				
				horaa.setDescanso(rs.getInt("descanso"));
				horaa.setDiaDobla(rs.getInt("dobla"));
				horaa.setDiaDobla2(rs.getInt("doblaExtra1"));
				horaa.setDiaDobla3(rs.getInt("doblaExtra2"));
				
				horaa.setHorarioDeposito(rs.getInt("horario_deposito"));
				horaa.setRecesoDiarioExtra(rs.getInt("receso_extra_diario"));
				
				horaa.setEntrada_doblada_extra1(rs.getString("entrada_doblada_extra1"));
				horaa.setSalida_doblada_extra1(rs.getString("salida_doblada_extra1"));
				horaa.setComida_doblada_extra1(rs.getString("comida_doblada_extra1"));
				horaa.setEntrada_doblada_extra2(rs.getString("entrada_doblada_extra2"));
				horaa.setSalida_doblada_extra2(rs.getString("salida_doblada_extra2"));
				horaa.setComida_doblada_extra2(rs.getString("comida_doblada_extra2"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion Obj_Horarios store procedure sp_select_horarios: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return horaa;
	}
	
//	buscarHorario
	public boolean HorarioExiste(int horario){
		String query = "exec sp_folio_horario "+horario;
		
		boolean existe = false;
		Statement s;
		ResultSet rs;
		try {				
			s = con.conexion().createStatement();
			rs = s.executeQuery(query);
			while(rs.next()){
				existe = Boolean.parseBoolean(rs.getString("Existe").trim());
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion HorarioExiste store procedure sp_folio_horario: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
			
		return existe;
	}
	
	public Obj_Horarios Horario_Nuevo() throws SQLException{
		Obj_Horarios horario = new Obj_Horarios();
		String query = "select max(folio) as 'Maximo' from tb_horarios";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()){
				horario.setFolio(rs.getInt("Maximo"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion Obj_Horarios store procedure select max(folio) as 'Maximo' from tb_horarios: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return horario;
	}
	
			
			/**buscamos los campos llenos del arduino**/
			public Obj_Arduino Buscar_Arduino() throws SQLException{
				Obj_Arduino arduino = new Obj_Arduino();
				String query = "exec sp_hora_arduino";
				
				Statement stmt = null;
				try {
					stmt = con.conexion().createStatement();
					ResultSet rs = stmt.executeQuery(query);
					while(rs.next()){
						arduino.setMa�ana(rs.getString("ma�ana"));
						arduino.setMediodia(rs.getString("medio"));
						arduino.setTarde(rs.getString("tarde"));
					}
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
				finally{
					if(stmt!=null){stmt.close();}
				}
				return arduino;
			}
			
			
			//Buscar el nombre de la cajera de fuente de sodas
			
			public Obj_Captura_Fuente_Sodas fuente(int folio) throws SQLException{
				Obj_Captura_Fuente_Sodas sodas = new Obj_Captura_Fuente_Sodas();
				String query = "select * from tb_captura_fuente_sodas where folio ="+ folio;
				Statement stmt = null;
				try {
					stmt = con.conexion().createStatement();
					ResultSet rs = stmt.executeQuery(query);
					while(rs.next()){
						File photo = new File(System.getProperty("user.dir")+"/tmp/tmp.jpg");
						FileOutputStream fos = new FileOutputStream(photo);
						
						 byte[] buffer = new byte[1];
				            InputStream is = rs.getBinaryStream("foto");
				            while (is.read(buffer) > 0) {
				                fos.write(buffer);
				            }
				            fos.close();
						
					}
					
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
				finally{
					if(stmt!=null){stmt.close();}
				}
				return sodas;
			}
//trae el nombre del empleado para el codigo
	public void Gafetes_masivos(String listas) throws SQLException{
		String insertIds = "exec sp_insert_ids "+listas.substring(0, listas.length()-1)+";";
		System.out.println(listas.substring(0, listas.length()-1));
//		String query =  "exec sp_genera_clave_checador "+listas.substring(0, listas.length()-1)+";";

		Statement stmt = null;
		
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;

		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(insertIds);
			pstmt.execute();
			con.commit();
			
//			stmt = con.createStatement();
//			ResultSet rs = stmt.executeQuery(query);
//			int j = 1;
//			while(rs.next()){
//				new Obj_Gen_Code_Bar().Generar_Code(rs.getString("codigo"),j+"".trim());
//				File photo = new File(System.getProperty("user.dir")+"/AssetGafete/Users_Images/"+j+".png");
//				FileOutputStream fos = new FileOutputStream(photo);
//
//				byte[] buffer = new byte[1];
//		            InputStream is = rs.getBinaryStream("foto");
//		            while (is.read(buffer) > 0) {
//		                fos.write(buffer);
//		            }
//		            fos.close();
//		    	j++;
//		           
//			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
	}
	
	public void Generar_2_Gafetes(String listas) throws SQLException{
		String insertIds = "exec sp_insert_ids_2 "+listas.substring(0, listas.length()-1)+";";
		System.out.println(listas.substring(0, listas.length()-1));
		String query =  "exec sp_genera_clave_checador_2_gafetes "+listas.substring(0, listas.length()-1)+";";

		Statement stmt = null;
		
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;

		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(insertIds);
			pstmt.execute();
			con.commit();
			
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			int j = 1;
			while(rs.next()){
				new Obj_Gen_Code_Bar().Generar_Code(rs.getString("codigo"),j+"".trim());
				File photo = new File(System.getProperty("user.dir")+"/AssetGafete/Users_Images/"+j+".png");
				FileOutputStream fos = new FileOutputStream(photo);

				byte[] buffer = new byte[1];
		            InputStream is = rs.getBinaryStream("foto");
		            while (is.read(buffer) > 0) {
		                fos.write(buffer);
		            }
		            fos.close();
		    	j++;
		           
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
	}
	
	
	public String[][] tabla_libre_jerarquico(String nomgbre){
		String datos = "exec sp_select_tabla_alimentacion_jerarquico_libre '"+nomgbre+"';";

		String[][] Matriz = new String[getFilas(datos)][4];
		
		Statement s;
		ResultSet rs;
		try {			
			s = con.conexion().createStatement();
			rs = s.executeQuery(datos);
			
			int i=0;
			while(rs.next()){
				Matriz[i][0] = String.valueOf(i+1)+"  ";
				Matriz[i][1] = "  "+rs.getString(2);
				Matriz[i][2] = "  ";
				Matriz[i][3] = "  ";
				i++;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return Matriz;
	}
	
	public String[][] tabla_libre_jerarquico_contestada(String nomgbre){
		String datos = "exec sp_select_tabla_alimentacion_libre_jerarquico_contestada '"+nomgbre+"';";

		String[][] Matriz = new String[getFilas(datos)][4];
		
		Statement s;
		ResultSet rs;
		try {			
			s = con.conexion().createStatement();
			rs = s.executeQuery(datos);
			
			int i=0;
			while(rs.next()){
				Matriz[i][0] = String.valueOf(i+1)+"  ";
				Matriz[i][1] = "  "+rs.getString(2);
				Matriz[i][2] = "  "+rs.getString(3);
				Matriz[i][3] = "  "+rs.getString(4);
				i++;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return Matriz;
	}
	
	public String[][] tabla_libre(String nomgbre){
		String datos = "exec sp_select_tabla_alimentacion_libre '"+nomgbre+"';";

		String[][] Matriz = new String[getFilas(datos)][4];
		
		Statement s;
		ResultSet rs;
		try {			
			s = con.conexion().createStatement();
			rs = s.executeQuery(datos);
			
			int i=0;
			while(rs.next()){
				Matriz[i][0] = String.valueOf(i+1)+"  ";
				Matriz[i][1] = "  "+rs.getString(2);
				Matriz[i][2] = "  ";
				Matriz[i][3] = "  ";
				i++;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return Matriz;
	}
	
	public String[][] tabla_libre_contestada(String nomgbre){
		String datos = "exec sp_select_tabla_alimentacion_libre_contestada '"+nomgbre+"';";

		String[][] Matriz = new String[getFilas(datos)][4];
		
		Statement s;
		ResultSet rs;
		try {			
			s = con.conexion().createStatement();
			rs = s.executeQuery(datos);
			
			int i=0;
			while(rs.next()){
				Matriz[i][0] = String.valueOf(i+1)+"  ";
				Matriz[i][1] = "  "+rs.getString(2);
				Matriz[i][2] = "  "+rs.getString(3);
				Matriz[i][3] = "  "+rs.getString(4);
				i++;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return Matriz;
	}

	public Obj_Base_De_Solicitud_De_Empleado Solicitud_empleado(int folio) throws SQLException{
		Obj_Base_De_Solicitud_De_Empleado solicitud = new Obj_Base_De_Solicitud_De_Empleado();
		String query = "exec sp_select_solicitud_permiso_empleado_operaciones "+ folio;
		Statement stmt = null;

		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while(rs.next()){
				
				solicitud.setFolio_solicitud(rs.getInt("folio_solicitud"));
				solicitud.setFolio_empleado(rs.getInt("folio_empleado"));
				solicitud.setNombre_empleado(rs.getString("nombre_empleado"));
				solicitud.setEstablecimiento(rs.getString("establecimiento"));
				solicitud.setPuesto(rs.getString("puesto"));
				solicitud.setDescanso(rs.getString("descanso"));
				solicitud.setDobla(rs.getString("dobla"));
				solicitud.setStatus(rs.getString("status"));
				solicitud.setTelefono(rs.getString("telefono_propio"));
				solicitud.setNombre_encargado(rs.getString("encargado"));
				solicitud.setSopeca(rs.getString("SoPeCa"));
				solicitud.setCambio(rs.getString("Cambio"));
				
				solicitud.setFecha_solicitada(rs.getString("fecha_solicitada"));
				solicitud.setTipo_solicitud(rs.getString("tipo_solicitud"));
				solicitud.setPuntualidad(rs.getInt("punt"));
				solicitud.setCumplimiento(rs.getInt("cumplimiento"));
				solicitud.setDiciplina(rs.getInt("diciplina"));
				solicitud.setRespeto(rs.getInt("respeto_gral"));
				solicitud.setMotivo(rs.getString("motivo"));
							
				File photo = new File(System.getProperty("user.dir")+"/tmp/tmp.jpg");
				FileOutputStream fos = new FileOutputStream(photo);
				
		            byte[] buffer = new byte[1];
		            InputStream is = rs.getBinaryStream("foto");
		            while (is.read(buffer) > 0) {
		                fos.write(buffer);
		            }
		            fos.close(); 
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return solicitud;
	}
	
	public Obj_Encargado_De_Solicitudes Emcargado_de_solicitudes(String encargado) throws SQLException{
		Obj_Encargado_De_Solicitudes objEncargado = new Obj_Encargado_De_Solicitudes();
		String query = "exec sp_select_seleccion_empleados_por_encargado '"+ encargado +"';";
		Statement stmt = null;

		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while(rs.next()){
				objEncargado.setFolio(rs.getInt("folio_encargado"));
				objEncargado.setEstablecimiento(rs.getInt("establecimiento_id"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return objEncargado;
	}
	
	public boolean Permiso_de_usuario_para_horario(int usuario){
		String query = "exec sp_permiso_a_horarios "+usuario;
		
		boolean permiso = false;
		Statement s;
		ResultSet rs;
		
		try {				
			s = con.conexion().createStatement();
			rs = s.executeQuery(query);
			
			while(rs.next()){
				permiso = Boolean.parseBoolean(rs.getString("permiso").trim());
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
			
		return permiso;
	}
	
	public boolean Buscar_Grupo_De_Vacaciones(Obj_Tabla_De_Vacaciones grupo_vacacionesObj){
		String query = "exec sp_existe_tabla_grupos_de_vacaciones "+grupo_vacacionesObj.getGrupo().toUpperCase().trim()+","+
				grupo_vacacionesObj.getAnios_trabajados()+","+grupo_vacacionesObj.getDias_correspondientes()+","+
				grupo_vacacionesObj.getPrima_vacacional()+";";
		
		boolean existe = false;
		Statement s;
		ResultSet rs;
		
		try {				
			s = con.conexion().createStatement();
			rs = s.executeQuery(query);
			
			while(rs.next()){
				existe = Boolean.valueOf(rs.getString("existe").trim());
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return existe;
	}
	
	public Obj_Grupo_De_Vacaciones Grupo_Nuevo() throws SQLException{
		Obj_Grupo_De_Vacaciones grupo = new Obj_Grupo_De_Vacaciones();
		String query = "select max(folio) as 'Maximo' from tb_grupo_de_vacaciones";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				grupo.setFolio(rs.getInt("Maximo"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return grupo;
	}
	
	public Obj_Grupo_De_Vacaciones Grupo(int folio) throws SQLException{
		Obj_Grupo_De_Vacaciones grupo = new Obj_Grupo_De_Vacaciones();
		String query = "select * from tb_grupo_de_vacaciones where folio ="+ folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				grupo.setFolio(rs.getInt("folio"));
				grupo.setDescripcion(rs.getString("descripcion").trim());
				grupo.setStatus(rs.getBoolean("status"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt != null){stmt.close();}
		}
		return grupo;
	}
	
//	busca los empleados para posteriormente calcularle sus vacaciones-------------------------------
//	se le envia el folio del empleado y el parametro 1 para indicarle al procedimiento que 
//  se generara  un registro nuevo
	public Obj_Alimentacion_De_Vacaciones Empleado_En_Vacaciones(int folio) throws SQLException{
		Obj_Alimentacion_De_Vacaciones alimentacion_vacaciones = new Obj_Alimentacion_De_Vacaciones();
		String query = "exec sp_select_empleado_para_vacaciones_nuevas "+ folio;
		Statement stmt = null;

		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while(rs.next()){
//				datos empleado	
				alimentacion_vacaciones.setFolio_vacaciones(rs.getInt("folio_vacaciones"));
				alimentacion_vacaciones.setFolio_empleado(rs.getInt("folio_empleado"));
				alimentacion_vacaciones.setEmpleado(rs.getString("empleado").trim());
				alimentacion_vacaciones.setEstablecimiento(rs.getString("establecimiento").trim());
				alimentacion_vacaciones.setPuesto(rs.getString("puesto").trim());
				alimentacion_vacaciones.setFecha_ingreso(rs.getString("fecha_ingreso").trim());
				alimentacion_vacaciones.setFecha_ingreso_imss(rs.getString("fecha_ingreso_imss").trim());
				alimentacion_vacaciones.setSalario_diario_integrado(rs.getFloat("salario_diario_integrado"));
				alimentacion_vacaciones.setGrupo_vacacional(rs.getString("grupo"));
				alimentacion_vacaciones.setProximas_vacaciones(rs.getInt("proximas_vacaciones")+1);//trae los a�os de las ultimas vacaciones disfrutadas y le suma 1 que corresponde a las vacaciones siguientes
				
				File photo = new File(System.getProperty("user.dir")+"/tmp/tmp.jpg");
				FileOutputStream fos = new FileOutputStream(photo);
				
		            byte[] buffer = new byte[1];
		            InputStream is = rs.getBinaryStream("foto");
		            while (is.read(buffer) > 0) {
		                fos.write(buffer);
		            }
		            fos.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion Empleado_En_Vacaciones en el procedimiento sp_select_empleado_para_vacaciones_nuevas SQLException: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return alimentacion_vacaciones;
	}
	
//	calcular las vacaciones correspondientes a los a�os
	public Obj_Alimentacion_De_Vacaciones calcular_vacaciones(int folio_empleado, String fecha_inicio, int anios_de_proximas_vacaciones) throws SQLException{
		Obj_Alimentacion_De_Vacaciones alimentacion_vacaciones = new Obj_Alimentacion_De_Vacaciones();
		String query = "exec sp_calculo_de_vacaciones "+ folio_empleado + ",'" + fecha_inicio+"',"+anios_de_proximas_vacaciones;
		Statement stmt = null;

		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while(rs.next()){
				
//				alimentacion de vacaciones
				alimentacion_vacaciones.setFecha_final(rs.getString("fecha_final"));//se optienen los dias que le corresponden al empleado con respecto al tiempo trabajado
				alimentacion_vacaciones.setDias_de_vacaciones(rs.getInt("dias_correspondientes_a_los_anios_vacaciones"));
				alimentacion_vacaciones.setDias_de_descanso_pagados(rs.getInt("dias_de_descanso_pagados"));
				alimentacion_vacaciones.setDias_trabajados_de_la_ultima_semana(rs.getInt("dias_pendientes_de_pago_en_la_ultima_semana"));
				alimentacion_vacaciones.setCuota_diaria_nc(rs.getFloat("cuota_diaria_nc"));
				alimentacion_vacaciones.setCuota_diaria_c(rs.getFloat("cuota_diaria"));
				alimentacion_vacaciones.setSDI_c(rs.getFloat("cuota_diaria_integrada"));
				
				alimentacion_vacaciones.setPrestamo_nc(rs.getFloat("desc_prestamo"));
				alimentacion_vacaciones.setPension_alimenticia_nc(rs.getFloat("pension_alimenticia"));
				alimentacion_vacaciones.setInfonacot_nc(rs.getFloat("infonavit"));
				alimentacion_vacaciones.setInfonacot_nc(rs.getFloat("infonacot"));
//				System.out.println(rs.getFloat(""));
				alimentacion_vacaciones.setFuente_de_sodas_nc(rs.getFloat("descuento_de_fuente_de_sodas"));
				alimentacion_vacaciones.setCorte_de_caja_nc(rs.getFloat("corte_de_caja"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion calcular_vacaciones en el procedimiento sp_calculo_de_vacaciones SQLException: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return alimentacion_vacaciones;
	}
	
//	busca empleado para asignarle sus ultimas vacaciones --------------------------------------------------------------
	public Obj_Alimentacion_De_Vacaciones empleado_para_ultimas_vacaciones(int folio_empleado) throws SQLException{
		Obj_Alimentacion_De_Vacaciones alimentacion_vacaciones = new Obj_Alimentacion_De_Vacaciones();
		String query = "exec sp_select_empleado_para_vacaciones_pasadas "+ folio_empleado;
		Statement stmt = null;

		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while(rs.next()){
//				datos empleado	
				alimentacion_vacaciones.setFolio_empleado(rs.getInt("folio_empleado"));
				alimentacion_vacaciones.setEmpleado(rs.getString("empleado").trim());
				alimentacion_vacaciones.setEstablecimiento(rs.getString("establecimiento").trim());
				alimentacion_vacaciones.setPuesto(rs.getString("puesto").trim());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion empleado_para_ultimas_vacaciones en el procedimiento sp_select_empleado_para_vacaciones_pasadas SQLException: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return alimentacion_vacaciones;
	}
	
//	funcion utilizada para dar de alta solo un registro por empleado en el catalogo de alimentacion de ultimas vacaciones
	public boolean Buscar_Si_Cuenta_Con_Vacaciones(int folio){
		String query = "exec sp_existe_empleado_con_vacaciones "+folio+";";
		
		boolean existe = false;
		Statement s;
		ResultSet rs;
		
		try {				
			s = con.conexion().createStatement();
			rs = s.executeQuery(query);
			
			while(rs.next()){
				existe = Boolean.valueOf(rs.getString("existe").trim());
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion Buscar_Si_Cuenta_Con_Vacaciones en el procedimiento sp_existe_empleado_con_vacaciones SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
		return existe;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Vector obtener_mensaje_checador(int folio) throws SQLException{
		Vector fila = new Vector();
		String query = "sp_select_existe_mensaje_empleado_checador "+folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
		    ResultSet rs = stmt.executeQuery(query);
		    
			while(rs.next()){
				fila.add(rs.getObject(1));
				fila.add(rs.getObject(2));
				fila.add(rs.getObject(3));
				fila.add(rs.getObject(4));
				fila.add(rs.getObject(5));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error");
			JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion obtener_mensaje_checador en el procedimiento sp_select_existe_mensaje_empleado_checador SQLException: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		finally{
			 if (stmt != null) { stmt.close(); }
		}
		return fila;
	}
	
	public Obj_Alimentacion_De_Vacaciones vacaciones_guardadas(int folio_empleado) throws SQLException{
		Obj_Alimentacion_De_Vacaciones vacaciones = new Obj_Alimentacion_De_Vacaciones();
		
		String query = "exec sp_select_empleado_en_vacaciones "+ folio_empleado;
		Statement stmt = null;

		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while(rs.next()){

				vacaciones.setFolio_vacaciones(rs.getInt("folio_vacaciones"));
				vacaciones.setFolio_empleado(rs.getInt("folio_empleado"));
				vacaciones.setEmpleado(rs.getString("empleado"));
				vacaciones.setEstablecimiento(rs.getString("establecimiento"));
				vacaciones.setPuesto(rs.getString("puesto"));
				vacaciones.setFecha_ingreso(rs.getString("fecha_ingreso"));
				vacaciones.setFecha_ingreso_imss(rs.getString("fecha_ingreso_imss"));
				vacaciones.setSalario_diario_integrado(rs.getFloat("salario_diarioc"));
				vacaciones.setGrupo_vacacional(rs.getString("grupo"));
				vacaciones.setProximas_vacaciones(rs.getInt("proximas_vacaciones"));
				vacaciones.setFecha_inicio(rs.getString("fecha_inicio"));
				vacaciones.setFecha_final(rs.getString("fecha_final"));
//				vacaciones.setVacaciones(rs.getFloat("importe_vacaciones_nc"));
//				vacaciones.setPrima_vacacional(rs.getFloat("importe_prima_vacacional_nc"));
//				vacaciones.setDias_descanso_vacaciones(rs.getFloat("importe_dias_descanso_vacaciones_nc"));
//				vacaciones.setInfonavit(rs.getFloat("importe_infonavit"));
//				vacaciones.setSueldo_semana(rs.getFloat("importe_sueldo_semana_nc"));
//				vacaciones.setCorte_de_caja(rs.getFloat("importe_corte_de_caja"));
//				vacaciones.setFuente_de_sodas(rs.getFloat("importe_fuente_de_sodas"));
//				vacaciones.setPrestamo(rs.getFloat("importe_prestamo"));
//				vacaciones.setPension_alimenticia(rs.getFloat("importe_pension_alimenticia"));
//				vacaciones.setTotal(rs.getFloat("total"));
//				vacaciones.setStatus(rs.getBoolean("status"));
//				vacaciones.setVacaciones_c(rs.getFloat("importe_vacacionesc"));
//				vacaciones.setPrima_vacacional_c(rs.getFloat("importe_prima_vacacionalc")) ;
//				vacaciones.setSueldo_semana_c(rs.getFloat("importe_sueldo_semanac"));
//				vacaciones.setGratificacion(rs.getFloat("gratificacion"));
				File photo = new File(System.getProperty("user.dir")+"/tmp/tmp.jpg");
				FileOutputStream fos = new FileOutputStream(photo);
				
		            byte[] buffer = new byte[1];
		            InputStream is = rs.getBinaryStream("foto");
		            while (is.read(buffer) > 0) {
		                fos.write(buffer);
		            }
		            fos.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion vacaciones_guardadas en el procedimiento sp_select_empleado_en_vacaciones SQLException: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return vacaciones;
	}
	
	public boolean validacion_de_vacaciones_para_btnGuardar(int folio_vacaciones){
		String query = "exec sp_existe_folio_vacaciones "+folio_vacaciones+";";
		
		boolean existe = false;
		Statement s;
		ResultSet rs;
		
		try {				
			s = con.conexion().createStatement();
			rs = s.executeQuery(query);
			
			while(rs.next()){
				existe = Boolean.valueOf(rs.getString("existe").trim());
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion validacion_de_vacaciones_para_btnGuardar SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
		return existe;
	}
	
	public String trae_fecha(){
		String fecha_actian = "select convert(varchar(20),getdate(),103)";
		String resultado = "";
		Statement s;
		ResultSet rs;
		try {		
			s = con.conexion().createStatement();
			rs = s.executeQuery(fecha_actian);
			
			while(rs.next()){
				resultado = rs.getString(1);
				
			}
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion trae_fecha SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			e1.printStackTrace();
		}
		return resultado; 
	}
	public Obj_Proveedores Buscar_factura_proveedor_control_xml(String folio_factura,String cod_prv) throws SQLException{
		Obj_Proveedores facturas_control = new Obj_Proveedores();
		String query = "select * from tb_control_de_facturas_y_xml where cod_prv='"+cod_prv +"' and folio_factura= '"+folio_factura+"'";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				facturas_control.setFolio_factura(rs.getString("folio_factura"));
				facturas_control.setCod_prv(rs.getString("cod_prv").trim());
				facturas_control.setProveedor(rs.getString("proveedor").trim());
				facturas_control.setStatus((rs.getString("status").equals("1"))?true:false);
				}
				
			} 	catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion Buscar_factura_proveedor_control_xml SQLException: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				return null;
			}
			finally{				if(stmt!=null){stmt.close();}
			}
			return facturas_control;
				
			};
			
			
	
	public Obj_Movimiento_De_Asignacion buscar_asignacion_para_asignar(int folio_empleado,String establecimiento) throws SQLException{
		Obj_Movimiento_De_Asignacion alimentacion_vacaciones = new Obj_Movimiento_De_Asignacion();
		String query = "exec sp_select_asignacion_de_cajero "+folio_empleado+",'"+establecimiento+"';";
		Statement stmt = null;

		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while(rs.next()){
				alimentacion_vacaciones.setEmpleado(rs.getString("empleado").trim());
				alimentacion_vacaciones.setAsignacion(rs.getString("asignacion").trim());
				alimentacion_vacaciones.setFechaIn(rs.getString("fecha_inicio").trim());
			}
		} 	catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion Obj_Movimiento_De_Asignacion SQLException: sp sp_select_asignacion_de_cajero "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		finally{				if(stmt!=null){stmt.close();}
		}

		return alimentacion_vacaciones;
	};
	
	
	public boolean buscar_emp_vigente_en_asignacion(int folio_empleado, String establecimiento){
		String query = "exec sp_verificar_status_de_asignacion " + folio_empleado + ",'" + establecimiento + "';";
		
		boolean existe = false;
		Statement s;
		ResultSet rs;
		
		try {				
			s = con.conexion().createStatement();
			rs = s.executeQuery(query);
			
			while(rs.next()){
				existe = Boolean.valueOf(rs.getString("existe_status_asignacion").trim());
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return existe;
	}
	
	public Obj_Clientes Cliente_Nuevo() throws SQLException{
		Obj_Clientes cliente = new Obj_Clientes();
		String query = "exec sp_nuevo_cliente";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				cliente.setFolio_cliente(rs.getInt("Maximo"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return cliente;
	}
	
	public Obj_Clientes Cliente(int folio_cliente) throws SQLException{
		Obj_Clientes cliente = new Obj_Clientes();
		
		String query = "select folio_cliente,nombre,ap_paterno,ap_materno,direccion,telefono from tb_clientes where folio_cliente = "+folio_cliente;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				cliente.setFolio_cliente(rs.getInt("folio_cliente"));
				cliente.setNombre(rs.getString("nombre"));
				cliente.setAp_paterno(rs.getString("ap_paterno"));
				cliente.setAp_materno(rs.getString("ap_materno"));
				cliente.setDireccion(rs.getString("direccion"));
				cliente.setTelefono(rs.getString("telefono"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return cliente;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Vector obtener_empleado_y_turno(int folio) throws SQLException{
		Vector fila = new Vector();
		String query = "exec sp_buscar_empleado_a_copiarle_turno "+folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
		    ResultSet rs = stmt.executeQuery(query);
		    
			while(rs.next()){
				fila.add(rs.getObject(1));
				fila.add(rs.getObject(2));
				fila.add(rs.getObject(3));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error");
			JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion obtener_empleado_y_turno en el procedimiento sp_buscar_empleado_a_copiarle_turno SQLException: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		finally{
			 if (stmt != null) { stmt.close(); }
		}
		return fila;
	}
	
	public int buscar_si_dobla(int folio_empleado){
		String query = "exec sp_select_existe_doblada " + folio_empleado+";";
		
		int valor = 0;
		Statement s;
		ResultSet rs;
		
		try {				
			s = con.conexion().createStatement();
			rs = s.executeQuery(query);
			
			while(rs.next()){
				valor = Integer.valueOf(rs.getString("dobladas").trim());
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return valor;
	}
	
	public boolean buscar_si_dobla_default(int folio_empleado){
		String query = "exec sp_select_existe_doblada_default " + folio_empleado+";";
		
		boolean existe = false;
		Statement s;
		ResultSet rs;
		
		try {				
			s = con.conexion().createStatement();
			rs = s.executeQuery(query);
			
			while(rs.next()){
				existe = Boolean.valueOf(rs.getString("doblada_default").trim());
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return existe;
	}
	
	public Obj_Etapas ExisteEtapa(int folio){
		Obj_Etapas etapas = new Obj_Etapas();
		String query = "select * from tb_etapas where folio_etapa ="+ folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				etapas.setFolio(rs.getInt("folio_etapa"));
				etapas.setEtapa(rs.getString("etapa").trim());
				etapas.setAbreviatura(rs.getString("abreviatura").trim());
				etapas.setStatus(rs.getInt("status"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion ExisteEtapa en select * from tb_etapas where folio ="+ folio+" SQLException: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		return etapas;
	}
	
	
	
	
	public Obj_Aspectos_De_La_Etapa ExisteAspecto(int folio){
		Obj_Aspectos_De_La_Etapa aspecto = new Obj_Aspectos_De_La_Etapa();
		String query = "select * from tb_aspectos_de_la_etapa where folio_aspecto ="+ folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				aspecto.setFolio(rs.getInt("folio_aspecto"));
				aspecto.setAspecto(rs.getString("aspecto_de_la_etapa").trim());
				aspecto.setAbreviatura(rs.getString("abreviatura").trim());
				aspecto.setStatus(rs.getInt("status"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion ExisteEtapa en select * from tb_aspectos_de_la_etapa where folio_aspecto ="+ folio+" SQLException: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		return aspecto;
	}
	
	public Obj_Unidades_de_Inspeccion ExisteUnidaddeInspeccion(int folio){
		Obj_Unidades_de_Inspeccion unidad = new Obj_Unidades_de_Inspeccion();
		String query = "select * from tb_unidades_de_inspeccion where folio_unidad_de_inspeccion ="+ folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				unidad.setFolio(rs.getInt("folio_unidad_de_inspeccion"));
				unidad.setunidades_de_inspeccion(rs.getString("unidad_de_inspeccion").trim());
				unidad.setAbreviatura(rs.getString("abreviatura").trim());
				unidad.setStatus(rs.getInt("status"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion ExisteUnidaddeInspeccion en select * from tb_unidades_de_inspeccion where folio_unidad_de_inspeccion ="+ folio+" SQLException: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		return unidad;
	}

	public String nuevo_ticket(String cavecera){
		
		String queryUpdate = "update tb_folios set folio = (select folio+1 from tb_folios where transaccion = 'Tickets Caja De Ahorro Clientes') where transaccion = 'Tickets Caja De Ahorro Clientes';";

		Connection conec = new Connexion().conexion();
		PreparedStatement pstmt = null;
		
		try {
			conec.setAutoCommit(false);
			pstmt = conec.prepareStatement(queryUpdate);
			
			pstmt.executeUpdate();
			conec.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(conec != null){
				try{
					System.out.println("La transacci�n ha sido abortada");
					conec.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en ActualizarSQL  en la funcion [ nuevo_ticket ]  SQLException: "+e.getMessage()+" "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		
//--------------------------------------------------------------------------------------------------------------------------------		
		
		String query = "exec sp_select_ticket_nuevo_c_ahorro_clientes '" + cavecera+"';";
		
		String cadena = "";
		Statement s;
		ResultSet rs;
		
		try {				
			s = con.conexion().createStatement();
			rs = s.executeQuery(query);
			
			while(rs.next()){
				cadena = rs.getString("nuevo_ticket").trim();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return cadena;
	}
	
	
	public Obj_Abono_Clientes CapturaAbonoCliente_UltimiAbono(String ticket) throws SQLException{
		Obj_Abono_Clientes empleado = new Obj_Abono_Clientes();

		//		cambiar procedimiento almacenado
		
		String query = "exec sp_reporte_ultimo_ticket_por_empleado '"+ticket+"';";
		Statement stmt = null;

		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while(rs.next()){
//				datos personales	
				
//				empleado.setUsuario(rs.getString("usuario"));
//				empleado.setFecha(rs.getString("fecha"));
//				empleado.setEmpleado(rs.getString("empleado"));
//				empleado.setEstablecimiento(rs.getString("establecimiento"));
//				empleado.setPuesto(rs.getString("puesto"));
//				empleado.setTicket(rs.getString("ticket"));
//				empleado.setImporte(rs.getFloat("importe"));
		            
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return empleado;
	}

	public Obj_Retiros_Cajeros datos_cajero(Integer folio_empleado) throws SQLException{
		Obj_Retiros_Cajeros datos_empleado = new Obj_Retiros_Cajeros();
		
	   String pc_nombre="";
//	 	pc_nombre ="SIV_CAJA1";
	   
					try {
	   pc_nombre = InetAddress.getLocalHost().getHostName();
					    			InetAddress.getLocalHost().getHostName();
					} catch (UnknownHostException e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion datos_cajero \n no se pudo obtener el nombre de la pc "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
					}
					
		String query = "exec sp_select_datos_cajero '"+folio_empleado+"'";
		
		String query_2="SELECT   isnull(sum(liquidaciones_tickets.importe),0)as importe "
				+ "						        ,asignaciones_cajeros.folio as folio_asignacion"
				+ "						        ,(select nombre from establecimientos where cod_estab=(select cod_estab from cajas where caja=(select caja from equipos_bms where nombre='"+pc_nombre+"')))as establecimiento"
				+ "					  FROM asignaciones_cajeros"
				+ "				        LEFT OUTER JOIN  liquidaciones_tickets on asignaciones_cajeros.folio = liquidaciones_tickets.folio_asignacion AND liquidaciones_tickets.afectacion='+' AND liquidaciones_tickets.forma_pago=1"
				+ "						          and (liquidaciones_tickets.folio_asignacion = (select folio_asignacion from cajeros where cod_estab=(select cod_estab from cajas where caja=(select caja from equipos_bms where nombre='"+pc_nombre+"')) and e_mail='"+folio_empleado+"'))"
				+ "				      WHERE asignaciones_cajeros.status='V' and folio=(select folio_asignacion from cajeros where cod_estab=(select cod_estab from cajas where caja=(select caja from equipos_bms where nombre='"+pc_nombre+"')) and e_mail='"+folio_empleado+"')"
				+ "						         		 group by asignaciones_cajeros.folio ";
				
		Statement stmt = null;
		Statement stmt2= null;
		
						try {
							stmt = con.conexion().createStatement();
							stmt2= con.conexion_IZAGAR().createStatement();
								
							ResultSet rs = stmt.executeQuery(query);
							ResultSet rs2= stmt2.executeQuery(query_2);
									while(rs.next()){
										datos_empleado.setFolio_empleado(rs.getInt("Folio_Empleado"));
										datos_empleado.setNombre(rs.getString("Nombre"));
										datos_empleado.setPuesto(rs.getString("Puesto"));
										datos_empleado.setPc(pc_nombre);
										File photo = new File(System.getProperty("user.dir")+"/tmp/tmp_cajero/cajerotmp.jpg");
										FileOutputStream fos = new FileOutputStream(photo);
										        byte[] buffer = new byte[1];
										        InputStream is = rs.getBinaryStream("Foto");
										        while (is.read(buffer) > 0) {
										        	fos.write(buffer);
										        }
										        fos.close();
							    	}
								   while(rs2.next()){
								 			datos_empleado.setEstablecimiento(rs2.getString("establecimiento"));
											datos_empleado.setImporte_total(rs2.getFloat("importe"));
											datos_empleado.setAsignacion(rs2.getString("folio_asignacion"));
								    }
						} catch (Exception e) {
							JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion datos_cajero \n  en el procedimiento : sp_select_datos_cajero  \n SQLException: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
							e.printStackTrace();
							return null;
						}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return datos_empleado;
	}

	public Obj_Retiros_Cajeros datos_supervisor_retiro(String clave) throws SQLException{
		Obj_Retiros_Cajeros datos_empleado = new Obj_Retiros_Cajeros();
		
		String query = "exec sp_select_datos_supervisor_retiro '"+clave+"'";
		
		Statement stmt = null;
		
						try {
					
							stmt = con.conexion().createStatement();
							ResultSet rs = stmt.executeQuery(query);
							
									while(rs.next()){
										
										datos_empleado.setFolio_supervisor(rs.getInt("Folio_Empleado"));
										datos_empleado.setNombre_Supervisor(rs.getString("Nombre"));
										datos_empleado.setExiste_supervisor(rs.getString("Existe"));
										datos_empleado.setClave(rs.getString("Clave"));
										
										
										if(rs.getString("Existe").equals("EXISTE")){
										File photo = new File(System.getProperty("user.dir")+"/tmp/tmp_supervisor/supervisortmp.jpg");
										FileOutputStream fos = new FileOutputStream(photo);
										        byte[] buffer = new byte[1];
										        InputStream is = rs.getBinaryStream("Foto");
										        while (is.read(buffer) > 0) {
										        	fos.write(buffer);
										        }
										        fos.close();
										}
										
							    	}
							
						} catch (Exception e) {
							JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion datos_cajero \n  en el procedimiento : sp_select_datos_cajero  \n SQLException: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
							e.printStackTrace();
							return null;
						}
		
		
		finally{
			if(stmt!=null){stmt.close();}
		}
		return datos_empleado;
	}

	
	public String[][] getRetiros_a_detalle(int folio_cajero,String establecimiento){
		String[][] Matriz = null;
		
		String datosif = "exec sp_select_retiro_de_cajero_a_detalle "+folio_cajero+",'"+establecimiento+"';";
		
		Matriz = new String[getFilas(datosif)][5];
		Statement s;
		ResultSet rs;
		try {			
			s = con.conexion().createStatement();
			rs = s.executeQuery(datosif);
			int i=0;
			while(rs.next()){
				Matriz[i][0] = rs.getString(1);
				Matriz[i][1] = rs.getString(2);
				Matriz[i][2] = rs.getString(3);
				Matriz[i][3] = rs.getString(4);
				Matriz[i][4] = rs.getString(5);
				
				i++;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion getRetiros_a_detalle \n  en el procedimiento : sp_select_retiro_de_cajero_a_detalle  \n SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
		
		return Matriz;
	}
	
	public int AnioActual() throws SQLException{
		int anio_actual  = 0;
		String query = "SELECT year(dateadd(ms,-3,DATEADD(mm, DATEDIFF(m,0,getdate() )+1, 0))) as anio;";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				anio_actual = rs.getInt("anio");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return anio_actual;
	}
	
	public void buscar_xml_pdf(int bandera, String fecha,String folio_factota) throws SQLException, IOException{
		
//		Object[][] matriz = null;
		
		String cod_prv="";
		String folio_fact="";
		String dia="";
		String mes="";
		String anio="";
		
		String query = "exec sp_select_fecha_de_referencia_para_generear_xml_y_pdf "+bandera+",'"+fecha+"','"+folio_factota+"'";
		
//		matriz = new Object[getFilas(query)][2];
		
		if(getFilas(query)>0){
			
						Statement s;
						ResultSet rs;
						
								try {			
										s = con.conexion().createStatement();
										rs = s.executeQuery(query);
									
												while(rs.next()){
													
													cod_prv=	rs.getString(1);
													folio_fact=	rs.getString(2);
													anio=		rs.getString(3);
													mes=		rs.getString(4);
													dia=		rs.getString(5);
													
													String ruta = "c:\\Concentrado_xml_pdf\\"+anio+"\\"+mes+"\\"+dia+"\\"+cod_prv;
													File archivos = new File(ruta);
													
													
													
													// creamos fichero si no existe y escribimos archivo 
													if (!archivos.exists()) { 
														
															archivos.mkdirs();
													
																	Blob blob_xml = rs.getBlob("xml");
												            		if(blob_xml.length() > 4){
												            			
															            	File archivo_xml = new File(ruta+"//"+folio_fact+".xml");
															            	FileOutputStream fos_xml = new FileOutputStream(archivo_xml);
														            		
														            			byte[] buffer_xml = new byte[1]; 
														            	 
														            			InputStream is_xml = rs.getBinaryStream("xml");
																	            while (is_xml.read(buffer_xml) > 0) {
																	            	
																	            	fos_xml.write(buffer_xml);
																	            }
																	            fos_xml.close();
														            }
														           
												            		Blob blob_pdf = rs.getBlob("pdf");
														            if(blob_pdf.length() > 4){
														            	 
													            		File archivo_pdf = new File(ruta+"//"+folio_fact+".pdf");
													            		FileOutputStream fos_pdf = new FileOutputStream(archivo_pdf);
																
													            		byte[] buffer_pdf = new byte[1];
														            		
														            		InputStream is_pdf = rs.getBinaryStream("pdf");
														            		while (is_pdf.read(buffer_pdf) > 0) {
														            			
														            			fos_pdf.write(buffer_pdf);
														            		}
														            		fos_pdf.close();
														            }
													}
													
												}
								} catch (SQLException e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion buscar_xml_pdf : sp_select_fecha_de_referencia_para_generear_xml_y_pdf  \n SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
					}
		}else{
					JOptionPane.showMessageDialog(null, "No se encontraron archivos con los datos ingresados", "Aviso", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
					return;
		}
	}
	
	public boolean validacion_cajero_fuente_sodas(String clavecajero,String nombrecajero){
		String query = "exec sp_validar_cajero '" + clavecajero + "','"+nombrecajero+"';";
		
		boolean disponible = false;
		try { Statement s = con.conexion().createStatement();
			  ResultSet rs = s.executeQuery(query);
			while(rs.next()){
			    	disponible = rs.getBoolean(1);
			      }
			
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion validacion_cajero_fuente_sodas \n  en el procedimiento : sp_validar_cajero  \n SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
    return disponible;
	}
	
	public boolean validacion_ticket_fuente_sodas(String nombre_usuario,String folioticket){
		
		String query = "exec sp_validar_folio_ticket_fte_sodas '"+ nombre_usuario +"','"+folioticket+"'";
		
		boolean disponible = false;
		try { Statement s = con.conexion().createStatement();
			  ResultSet rs = s.executeQuery(query);
			while(rs.next()){
			    	disponible = rs.getBoolean(1);
			      }
			
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion validacion_ticket_fuente_sodas \n  en el procedimiento : sp_validar_cajero  \n SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
    return disponible;
	}
	
	public Obj_Cotizaciones_De_Un_Producto datos_producto(String cod_prod) throws SQLException{
		Obj_Cotizaciones_De_Un_Producto datosproducto = new Obj_Cotizaciones_De_Un_Producto();
		String query = " declare @exist_cedis real,@cod_prod varchar(35),@Producto varchar(35)  set @Producto='"+cod_prod+"' "
				+ "				  set @cod_prod= (select top 1 cod_prod from productos with (nolock) where (cod_prod = @Producto)) "
				+ "				  if @cod_prod is null begin set @cod_prod= (select top 1 cod_prod from productos with (nolock) where (codigo_barras_pieza = @Producto)) end "
				+ "				  if @cod_prod is null begin set @cod_prod= (select top 1 cod_prod from productos with (nolock) where (codigo_barras_unidad = @Producto)) end "
				+ "				  if @cod_prod is null begin set @cod_prod=(select top 1 cod_prod from codigos_barras_adicionales_productos A with (nolock) where (codigo_barras_unidad=@Producto)) end "
				+ "				  if @cod_prod is null begin set @cod_prod=(select top 1 cod_prod from codigos_barras_adicionales_productos A with (nolock) where (codigo_barras_pieza=@Producto))end "
				+ "	SELECT   productos.cod_prod "
				+ "         ,productos.descripcion "
				+ "				          ,convert(numeric(10,2),prodestab.ultimo_costo) as ultimo_costo "
				+ "				          ,convert(numeric(10,2),prodestab.costo_promedio) as costo_promedio "
				+ "				          ,convert(numeric(10,2),isnull(sum(case when (productos.contenido)<>1 then((productos.contenido*prodestab.exist_unidades)+exist_piezas) else (prodestab.exist_piezas) end),0)) as existencia_total "
				+ "						  ,convert(numeric (10,2),case 1 WHEN 0 then dbo.precio_venta_rpt(productos.cod_prod, 'P', 1, GETDATE(), '', 1, 0, 0) "
				+ "												  ELSE 	dbo.precio_venta_rpt(productos.cod_prod, 'P', 1, GETDATE(), '', 1, 0, 0) "
				+ "													* (1 + dbo.Ieps(productos.cod_prod)/100) * (1 + (CASE 'I' WHEN 'I' THEN productos.iva_interior ELSE productos.iva_fronterizo END/100))  END) as precio_de_venta "
				+ "						  ,convert(numeric (10,2),case 1 WHEN 0 then dbo.precio_venta_rpt(productos.cod_prod, 'P', 1, GETDATE(), '1', 1, 0, 0) "
				+ "												  ELSE 	dbo.precio_venta_rpt(productos.cod_prod, 'P', 1, GETDATE(), '1', 1, 0, 0) "
				+ "													* (1 + dbo.Ieps(productos.cod_prod)/100) * (1 + (CASE 'I' WHEN 'I' THEN productos.iva_interior ELSE productos.iva_fronterizo END/100))  END) as precio_de_venta_normal "
				
				+ " from productos "
				+ "				    left outer join prodestab with (nolock) on prodestab.cod_prod=productos.cod_prod "
				+ "				  where productos.cod_prod=@cod_prod "
				+ "				  group by  productos.cod_prod,productos.descripcion,prodestab.ultimo_costo,prodestab.costo_promedio,productos.iva_interior";
		
		Statement stmt2= null;
		
						try {
							stmt2= con.conexion_IZAGAR().createStatement();
									
							ResultSet rs2= stmt2.executeQuery(query);
							
								   while(rs2.next()){
									   datosproducto.setCod_Prod(rs2.getString("cod_prod"));
									   datosproducto.setDescripcion_Prod(rs2.getString("descripcion"));
									   datosproducto.setUltimo_Costo(rs2.getDouble("ultimo_costo"));
									   datosproducto.setCosto_Promedio(rs2.getDouble("costo_promedio"));
									   datosproducto.setExistencia_Total(rs2.getDouble("existencia_total"));
									   datosproducto.setPrecio_de_venta(rs2.getDouble("precio_de_venta"));
									   datosproducto.setPrecio_de_venta_normal(rs2.getDouble("precio_de_venta_normal"));
									   
								   }
							
						} catch (Exception e) {
							JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion datos_producto \n SQLException: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
							e.printStackTrace();
							return null;
						}

		finally{
			if(stmt2!=null){stmt2.close();}
		}
		return datosproducto;
	}
	
	public boolean existe_Producto(String cod_prod){
		
		String query = "declare @exist_cedis real,@cod_prod varchar(35),@Producto varchar(35)  set @Producto='"+cod_prod+"' "+
				"  set @cod_prod= (select top 1 cod_prod from productos with (nolock) where (cod_prod = @Producto))" +
				"  if @cod_prod is null begin set @cod_prod= (select top 1 cod_prod from productos with (nolock) where (codigo_barras_pieza = @Producto)) end" +
				"  if @cod_prod is null begin set @cod_prod= (select top 1 cod_prod from productos with (nolock) where (codigo_barras_unidad = @Producto)) end" +
				"  if @cod_prod is null begin set @cod_prod=(select top 1 cod_prod from codigos_barras_adicionales_productos A with (nolock) where (codigo_barras_unidad=@Producto)) end" +
				"  if @cod_prod is null begin set @cod_prod=(select top 1 cod_prod from codigos_barras_adicionales_productos A with (nolock) where (codigo_barras_pieza=@Producto))end" +
				"  if @cod_prod is null select 'false' else select 'true'";
		
		boolean existe = false;
		try { Statement s = con.conexion_IZAGAR().createStatement();
			  ResultSet rs = s.executeQuery(query);
			while(rs.next()){
			    	existe = rs.getBoolean(1);
			      }
			
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion existe_Producto \n SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
    return existe;
	}
	
	
	public String datos_pedido(String cod_prod) throws SQLException{
		
		String cadena = "''";
		
		String query = " declare @folio_pedido varchar(20), @cantidadProductos int, @contador int, @cadena nvarchar(max) "
					+ " set @folio_pedido = '"+cod_prod+"' "
					+ " set @cantidadProductos = (select count(FOLIO) from mpedestab where folio = @folio_pedido); "
					+ " set @contador = 1; "
					+ " set @cadena=''; "
					+ " while(@contador <= @cantidadProductos) "
					+ " begin "
					+ "	 set @cadena=@cadena+(select a.cod_prod+'#' "
					+ "							from (select RANK() OVER (ORDER BY cod_prod) as contador, ltrim(rtrim(cod_prod)) as cod_prod "
					+ " 										from mpedestab mp where mp.folio = @folio_pedido ) a "
					+ " 							where a.contador = @contador) "
					+ "	 set @contador=@contador+1; "
					+ " end "
					+ " select @cadena as cadena ";
		
		Statement stmt= null;
						try {
							stmt= con.conexion_IZAGAR().createStatement();
							ResultSet rs= stmt.executeQuery(query);
							
								   while(rs.next()){
									   cadena += rs.getString(1).replace("#", "'',''");
								   }
								   cadena = cadena.substring(0, cadena.length()-3);
							
						} catch (Exception e) {
							JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion datos_producto \n SQLException: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
							e.printStackTrace();
							return null;
						}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return cadena;
	}
	
	public boolean existe_Pedido(String cod_prod){
		
		String query = "	declare @folio_pedido varchar(20), @cantidadProductos int, @contador int, @cadena nvarchar(max) "
					+ "	 set @folio_pedido = '"+cod_prod+"' " 
					+ " 	if exists (select folio from mpedestab where folio = @folio_pedido) "
					+ "		begin	select 'true' as existe end"
					+ "		else begin	select 'false' as existe end";
		
		boolean existe = false;
		try { Statement s = con.conexion_IZAGAR().createStatement();
			  ResultSet rs = s.executeQuery(query);
			while(rs.next()){
			    	existe = rs.getBoolean(1);
			      }
			
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion existe_Producto \n SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
    return existe;
	}
	
	public String  permiso_cancelar_ticket_o_abono(String clave){
		String permiso_cancelarApartados ="";
		
		String query = "exec sp_select_supervisores_para_cancelar_c_ahorro_clientes '"+clave+"'";
		Statement stmt = null;
		
		try {
				stmt = con.conexion().createStatement();
				ResultSet rs = stmt.executeQuery(query);
				while(rs.next()){
					permiso_cancelarApartados = rs.getString(1);
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(stmt != null){try {
				stmt.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}}
		}
		return permiso_cancelarApartados;
	}

	
	public Obj_Cotizaciones_De_Un_Producto Hoymenos3meses() throws SQLException{
		Obj_Cotizaciones_De_Un_Producto fecha = new Obj_Cotizaciones_De_Un_Producto();
		
		String query = "select convert (varchar(15),getdate()-90,103) as fecha ";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				fecha.setFecha(rs.getString("fecha"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion Hoymenos3meses \n SQLException: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);

			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return fecha;
	}
	
	public Obj_Conceptos_De_Extras_Para_Lista_De_Raya Concepto_extra_nuevo() throws SQLException{
		Obj_Conceptos_De_Extras_Para_Lista_De_Raya concepto_extra = new Obj_Conceptos_De_Extras_Para_Lista_De_Raya();
		String query = "select max(folio_concepto_extra) as 'Maximo' from tb_conceptos_de_extra_de_lista_de_raya";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				concepto_extra.setFolio(rs.getInt("Maximo"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion Concepto_extra_nuevo \n SQLException: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return concepto_extra;
	}
	
	public Obj_Conceptos_De_Extras_Para_Lista_De_Raya Concepto_Extra(int folio) throws SQLException{
		Obj_Conceptos_De_Extras_Para_Lista_De_Raya concepto_extra = new Obj_Conceptos_De_Extras_Para_Lista_De_Raya();
		String query = "select folio_concepto_extra,concepto_extra,abreviatura,case when status=1 then 'VIGENTE' ELSE 'CANCELADO' END as status "+
		                "   from tb_conceptos_de_extra_de_lista_de_raya where folio_concepto_extra="+folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				concepto_extra.setFolio(rs.getInt("folio_concepto_extra"));
				concepto_extra.setConcepto(rs.getString("concepto_extra").trim());
				concepto_extra.setAbreviatura(rs.getString("abreviatura").trim());
				concepto_extra.setStatus((rs.getString("status").trim()));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion Concepto_Extra \n SQLException: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return concepto_extra;
	}
	
	public String establecimiento_ticket_selecionado(String ticket) throws SQLException{
		String establecimiento = "";

		//		cambiar procedimiento almacenado
		
		String query = "	SELECT     LTRIM(RTRIM(tb_establecimiento.nombre)) AS establecimiento_del_ticket_seleccionado " +
						" 	FROM         tb_captura_tickets_c_ahorro_cte INNER JOIN " +
						"                      tb_establecimiento ON tb_establecimiento.folio = tb_captura_tickets_c_ahorro_cte.cod_establecimiento " +
						" 	WHERE     (tb_captura_tickets_c_ahorro_cte.ticket = '"+ticket+"')";
		Statement stmt = null;

		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while(rs.next()){
				
				establecimiento = rs.getString("establecimiento_del_ticket_seleccionado");
		            
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return establecimiento;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return establecimiento;
	}
	
	public Obj_Retiros_Cajeros datos_cajero_para_ahorro_cte(Integer folio_empleado) throws SQLException{
		Obj_Retiros_Cajeros datos_empleado = new Obj_Retiros_Cajeros();
		
	   String pc_nombre="";
//	 	pc_nombre ="SIV_CAJA1";
	   
					try {
	   pc_nombre = InetAddress.getLocalHost().getHostName();
					    			InetAddress.getLocalHost().getHostName();
					} catch (UnknownHostException e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion datos_cajero \n no se pudo obtener el nombre de la pc "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
					}
		
					
		String query = "exec sp_select_datos_cajero '"+folio_empleado+"'";
		
		String query_2=" declare @folio_establecimiento int, @nombre_caja varchar(20), @folio_empleado int " +
						" set @nombre_caja= '"+pc_nombre+"'; " +
						" set @folio_empleado = '"+folio_empleado+"'; " +
						" set @folio_establecimiento =(select cod_estab from cajas where caja= (select caja from equipos_bms where nombre=@nombre_caja)); " +
						"	select establecimientos.nombre as establecimiento " +
						"			,Asignaciones_cajeros.Folio as folio_asignacion " +
						"	from Asignaciones_cajeros " +
						"		inner join establecimientos on establecimientos.cod_estab = Asignaciones_cajeros.cod_estab " +
						"	where Asignaciones_cajeros.status = 'V' and Asignaciones_cajeros.Folio = (select folio_asignacion " +
						"									from cajeros " +
						"									where cod_estab=@folio_establecimiento" +
						" 									and status_asignacion='A' " +
						"									and e_mail=@folio_empleado) " +
						"	and fecha_inicial<=getdate() " +
						"	and fecha_final>=getdate()";
		
				
		Statement stmt = null;
		Statement stmt2= null;
		
						try {
							stmt = con.conexion().createStatement();
							stmt2= con.conexion_IZAGAR().createStatement();
									
							ResultSet rs = stmt.executeQuery(query);
							ResultSet rs2= stmt2.executeQuery(query_2);
							
									while(rs.next()){
										datos_empleado.setFolio_empleado(rs.getInt("Folio_Empleado"));
										datos_empleado.setNombre(rs.getString("Nombre"));
										datos_empleado.setPuesto(rs.getString("Puesto"));
										datos_empleado.setPc(pc_nombre);
										
										File photo = new File(System.getProperty("user.dir")+"/tmp/tmp_cajero/cajerotmp.jpg");
										FileOutputStream fos = new FileOutputStream(photo);
										        byte[] buffer = new byte[1];
										        InputStream is = rs.getBinaryStream("Foto");
										        while (is.read(buffer) > 0) {
										        	fos.write(buffer);
										        }
										        fos.close();
							    	}

									
								   while(rs2.next()){
								 			datos_empleado.setEstablecimiento(rs2.getString("establecimiento"));
//											datos_empleado.setImporte_total(rs2.getFloat("importe"));
											datos_empleado.setAsignacion(rs2.getString("folio_asignacion"));
								    }
							
						} catch (Exception e) {
							JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion datos_cajero_para_ahorro_cte \n  en el procedimiento : sp_select_datos_cajero  \n SQLException: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
							e.printStackTrace();
							return null;
						}

		finally{
			if(stmt!=null){stmt.close();}
		}
		return datos_empleado;
	}
	
	public int folio_periodo() throws SQLException{
		
		String query = "select folio as folio_periodo_fs from  tb_folios where transaccion = 'Periodos Captura Fuente de Sodas'";

		int folio = 0;
		try {				
			Statement s = con.conexion().createStatement();
			ResultSet rs = s.executeQuery(query);
			
			while(rs.next()){
				folio = rs.getInt(1);
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
			
		return folio;
	}
	
	public Obj_Asignacion_De_Computadoras_Para_Checador_Por_Establecimiento Existepc_establecimiento(int folio){
		Obj_Asignacion_De_Computadoras_Para_Checador_Por_Establecimiento nombrepc = new Obj_Asignacion_De_Computadoras_Para_Checador_Por_Establecimiento();
		String query = " select tpc.folio,tpc.nombre_pc_checador,tb_establecimiento.nombre as establecimiento,tpc.status from tb_pc_asignadas_a_establecimiento_para_checador tpc" +
				" inner join tb_establecimiento on tb_establecimiento.folio=tpc.folio_establecimiento where tpc.folio ="+ folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				nombrepc.setFolio(rs.getInt("folio"));
				nombrepc.setNombre_Pc(rs.getString("nombre_pc_checador").trim());
				nombrepc.setEstablecimiento(rs.getString("establecimiento").trim());
				nombrepc.setStatus(rs.getInt("status"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion Existepc_establecimiento en select * from tb_etapas where folio ="+ folio+" SQLException: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		return nombrepc;
	}
	
	public Obj_Puntos_De_Venta_De_Tiempo_Aire Existepunto_de_venta_establecimiento(int folio){
		Obj_Puntos_De_Venta_De_Tiempo_Aire nombrepc = new Obj_Puntos_De_Venta_De_Tiempo_Aire();
		String query = "select tb_pc_asignadas_a_un_punto_de_venta_TA.folio"
				+ "      ,tb_establecimiento.nombre as establecimiento"
				+ "	  ,tb_pc_asignadas_a_un_punto_de_venta_TA.nombre_pc"
				+ "	  ,tb_pc_asignadas_a_un_punto_de_venta_TA.nombre_punto_de_venta"
				+ "	  ,tb_pc_asignadas_a_un_punto_de_venta_TA.status"
				+ "	   from tb_pc_asignadas_a_un_punto_de_venta_TA"
				+ " inner join tb_establecimiento on tb_establecimiento.folio=tb_pc_asignadas_a_un_punto_de_venta_TA.folio_establecimiento  where  tb_pc_asignadas_a_un_punto_de_venta_TA.folio="+ folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				nombrepc.setFolio(rs.getInt("folio"));
				nombrepc.setNombre_Pc(rs.getString("nombre_pc").trim());
				nombrepc.setNombre_Punto_Venta_TA(rs.getString("nombre_punto_de_venta").trim());
				nombrepc.setEstablecimiento(rs.getString("establecimiento").trim());
				nombrepc.setStatus(rs.getInt("status"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion Existepunto_de_venta_establecimiento En"+query+" SQLException: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		return nombrepc;
	}
	
	public Obj_Conceptos_De_Ordenes_De_Pago Existe_concepto_de_Orden_de_Compra(int folio){
		Obj_Conceptos_De_Ordenes_De_Pago concepto = new Obj_Conceptos_De_Ordenes_De_Pago();
		String query = "SELECT [folio_concepto]"
				+ "           ,[concepto_orden_de_pago]"
				+ "           ,case when status='V'then 'VIGENTE'else 'CANCELADO' end as estatus"
				+ "     FROM  tb_conceptos_de_orden_de_pago"
				+ "  where folio_concepto="+folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				concepto.setFolio(rs.getInt("folio_concepto"));
				concepto.setConcepto(rs.getString("concepto_orden_de_pago").trim());
				concepto.setEstatus(rs.getString("estatus").trim());
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion Existe_concepto_de_Orden_de_Compra En"+query+" SQLException: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		return concepto;
	}
	
	public int periodos() throws SQLException{
		int periodos = 0;

		String query = "select periodos_fuente_sodas as periodos from tb_configuracion_sistema";
		
		Statement stmt = null;

		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while(rs.next()){
				periodos = rs.getInt("periodos");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return periodos;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return periodos;
	}
	
	public String cadenaDeAsignacion(String fhIn,String fhFin) throws SQLException{
		String cadena = "";

		String query = "select asignacion as asignaciones from tb_relacion_por_pagos_de_servicios " +
				"		where concepto = 'LUZ' " +
				"		and fecha >= convert(smalldatetime,'"+fhIn+"',103) " +
				"		and fecha <= convert(smalldatetime,'"+fhFin+"',103)";
		
		Statement stmt = null;

		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while(rs.next()){
				cadena = cadena+"'"+rs.getString("asignaciones")+"'','";
			}
			cadena= cadena.substring(0,cadena.length()-3);
			
		} catch (Exception e) {
			e.printStackTrace();
			return cadena;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return cadena;
	}
	
//	public String[][] llenar_tabla_deduccion_inasistencia_sugerido_sistema(Obj_Traspaso_De_Sugerido_Sistema_De_Deducciones_Por_Inasistencia Traspaso_De_Sugerido_Sistema_De_Deducciones_Por_Inasistencia)throws SQLException{
//		String query = "exec sp_buscar_sugerido_sistemas_inasistencia";
//		String[][] matriz = new String[getFilas(query)][10];
//		
//		try {
//			Statement stmt = new Connexion().conexion().createStatement();
//			ResultSet rs = stmt.executeQuery(query);
////			System.out.println(rs.getString(2));
//			
//			int i = 0;
//			while(rs.next()){
//				matriz[i][0] = rs.getInt(1)+" ";
//				matriz[i][1] = rs.getString(2).trim();
//				matriz[i][2] = rs.getString(3).trim();
//				matriz[i][3] = rs.getString(4).trim();
//				matriz[i][4] = rs.getString(5).trim().equals("0")?"":rs.getString(5).trim();
//				matriz[i][5] = rs.getString(6).trim().equals("0")?"":rs.getString(6).trim();
//				matriz[i][6] = rs.getString(7).trim().equals("0")?"":rs.getString(7).trim();
//				matriz[i][7] = rs.getString(8).trim().equals("0")?"":rs.getString(8).trim();;
//				matriz[i][8] = "";
//				matriz[i][9] = "true";
//				i++;
//			}
//		} catch (SQLException e1) {
//			e1.printStackTrace();
//			JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion llenar_tabla_deduccion_inasistencia_sugerido_sistema \nprocedimiento almacenado sp_buscar_sugerido_sistemas_inasistencia \n SQL Server Exception: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
//		}
//	    return matriz; 
//	}
//	
	
	
	public String[][] Reporte_De_Ventas(Obj_Reportes_De_Ventas ventas) throws SQLException{
		Statement stmt = null;
		Obj_Usuario usuario = new Obj_Usuario();
		
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery("select acceso_a_costos_y_precio_de_venta from tb_empleado where folio = "+ usuario.LeerSession().getFolio());
			
				while(rs.next()){
						usuario.setAcceso_a_costos_y_precio_de_venta(rs.getInt(1));
				}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	String query = "exec sp_Reporte_IZAGAR_de_ventas '"+ventas.getFecha_inicio()+"','"+ventas.getFecha_final()+"','"+(ventas.getEstablecimiento().equals("''''")?0:ventas.getEstablecimiento())
				          +"','"+(ventas.getTipo_de_precio().equals("''Todos''")?0:ventas.getTipo_de_precio())+"','"+(ventas.getProductos().equals("")?0:ventas.getProductos())+"','"+(ventas.getClases().equals("")?0:ventas.getClases())
				          +"','"+(ventas.getCategorias().equals("")?0:ventas.getCategorias())+"','"+(ventas.getFamilias().equals("")?0:ventas.getFamilias())+"','"+(ventas.getLineas().equals("")?0:ventas.getLineas())
				          +"','"+usuario.getAcceso_a_costos_y_precio_de_venta()+"',"+ventas.getPresentado()+",'"+(ventas.getTallas().equals("")?0:ventas.getTallas())+"','"+(ventas.getAsignaciones().equals("")?0:ventas.getTallas())+"'";

	System.out.println(query);
	
	
	String[][] rp_ventas = new String[getFilasExterno(query)][20];
		try {
			stmt = con.conexion_IZAGAR().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			int i=0;
				while(rs.next()){
						rp_ventas[i][0 ]= rs.getString(1 ).trim();
						rp_ventas[i][1 ]= rs.getString(2 ).trim();
						rp_ventas[i][2 ]= rs.getString(3 ).trim()+"  ";
						rp_ventas[i][3 ]= rs.getString(4 ).trim();
						rp_ventas[i][4 ]= rs.getString(5 ).trim()+"  ";
						rp_ventas[i][5 ]= rs.getString(6 ).trim()+"  ";
						rp_ventas[i][6 ]= rs.getString(7 ).trim();
						rp_ventas[i][7 ]= rs.getString(8 ).trim();
						rp_ventas[i][8 ]= rs.getString(9 ).trim();
						rp_ventas[i][9 ]= rs.getString(10).trim();
						rp_ventas[i][10]= rs.getString(11).trim();
						rp_ventas[i][11]= rs.getString(12).trim()+"  ";
						rp_ventas[i][12]= rs.getString(13).trim()+"  ";
						rp_ventas[i][13]= rs.getString(14).trim()+"  ";
						rp_ventas[i][14]= rs.getString(15).trim();
						rp_ventas[i][15]= rs.getString(16).trim()+"  ";
						rp_ventas[i][16]= rs.getString(17).trim();
						rp_ventas[i][17]= rs.getString(18).trim();
						rp_ventas[i][18]= rs.getString(19).trim();
						rp_ventas[i][19]= rs.getString(20).trim();
					i++;
				}
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion Reporte_De_Ventas \n "+query+"\nSQLException:"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt != null){stmt.close();}
		}
		return rp_ventas;
	}
	
	public Object[][] Reporte_De_Competencia(Obj_Reportes_De_Ventas ventas, int cantidad_de_columnas,int tipo) throws SQLException{
		Statement stmt = null;
		
		String query = "exec sp_Reporte_IZAGAR_analisis_competidores '"+(ventas.getFecha_inicio()+"','"+(ventas.getProductos().equals("")?0:ventas.getProductos()))+"','"+(ventas.getClases().equals("")?0:ventas.getClases())+
				                                                      "','"+(ventas.getCategorias().equals("")?0:ventas.getCategorias())+"','"+(ventas.getFamilias().equals("")?0:ventas.getFamilias())+
				                                                      "','"+(ventas.getLineas().equals("")?0:ventas.getLineas())+"','"+(ventas.getTallas().equals("")?0:ventas.getTallas())+"',"+tipo;
		Object[][] rp_competencia = new Object[getFilasExterno(query)][cantidad_de_columnas];
		
		try {
			
			stmt = con.conexion_IZAGAR().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			int i=0;
			while(rs.next()){
				for(int j=0; j<cantidad_de_columnas; j++){
					rp_competencia[i][j] = rs.getObject(j+1);
				}
				i++;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al Buscar en el procedimiento sp_Reporte_IZAGAR_analisis_competidores \nSQLServerException:"+e+" \n Parametros:"+query,"Avise Al Administrador del Sistema",JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			
			return null;
		}
		finally{
			if(stmt != null){stmt.close();}
		}
		
		return rp_competencia;
	}	
	
	public int Numero_De_Competidores(){
		Statement stmt = null;
		String query = "select count(folio_competencia) from tb_competencias";
		int cantidad = 0;
		
		try {
			stmt = con.conexion_IZAGAR().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
				while(rs.next()){
					cantidad = rs.getInt(1);
				}
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return cantidad;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String[] Vector_De_Competidores() throws SQLException{
		Statement stmt = null;
		
		String query = "select competencia as competidor from tb_competencias";
		Vector miVector = new Vector();
		try {
			stmt = con.conexion_IZAGAR().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
				while(rs.next()){
					miVector.add(rs.getString("competidor"));
				}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		int i=0;
		String[] lista= new String[miVector.size()];
		
		while(i < miVector.size()){
			lista[i]= miVector.get(i).toString();
			i++;
		}
		return lista;
	}	
	
	public Obj_Diferencia_De_Cortes_Calculado corte_calc(int folio) throws SQLException{
		Obj_Diferencia_De_Cortes_Calculado corte_calculado = new Obj_Diferencia_De_Cortes_Calculado();
		String query = "exec sp_select_datos_diferencias_de_cortes "+ folio;
		Statement stmt = null;

		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while(rs.next()){
				
//				System.out.println(rs.getInt("folio_empleado"));
//				System.out.println(rs.getString("nombre_completo").trim());
//				System.out.println(rs.getDouble("saldo_a_favor"));
//				System.out.println(rs.getDouble("total_acumulado"));
//				System.out.println(rs.getDouble("diferencia_total"));
//				System.out.println(rs.getDouble("abono"));
//				System.out.println(rs.getInt("status_cobro"));

				corte_calculado.setFolio_empleado(rs.getInt("folio_empleado"));
				corte_calculado.setNombre_Completo(rs.getString("nombre_completo").trim());
				
				corte_calculado.setSaldo_a_favor(rs.getDouble("saldo_a_favor"));
				corte_calculado.setTotal_acumulado(rs.getDouble("total_acumulado"));
				corte_calculado.setDiferencia_total(rs.getDouble("diferencia_total"));
				corte_calculado.setAbono(rs.getDouble("abono"));
				corte_calculado.setStatus_cobro(rs.getInt("status_cobro"));
				
				File photo = new File(System.getProperty("user.dir")+"/tmp/tmp.jpg");
				FileOutputStream fos = new FileOutputStream(photo);
				
		            byte[] buffer = new byte[1];
		            InputStream is = rs.getBinaryStream("foto");
		            while (is.read(buffer) > 0) {
		                fos.write(buffer);
		            }
		            fos.close();
		            
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return corte_calculado;
	}
	
	public boolean semaforo_rptVentas(){
		String query = " select case when (count(asignacion))=0 then 'true' else 'false' end from IZAGAR_AVI_facremtick  where status=2 ";
		boolean semaforo = false;
		try {
			Statement stmt = new Connexion().conexion_IZAGAR().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()){ semaforo = rs.getBoolean(1); }
	
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	    return semaforo; 
	}
	
	public Obj_Conciliacion_de_Movimientos_Bancarios_Contra_Contabilidad datos_Cuenta_Bancaria(String Cuenta_Bancaria ) throws SQLException{
		Obj_Conciliacion_de_Movimientos_Bancarios_Contra_Contabilidad cargar_datosCuentaBancaria = new Obj_Conciliacion_de_Movimientos_Bancarios_Contra_Contabilidad();
		String query = "select rtrim(bancos.nombre) as banco,cuentas_bancarias.cuenta_contable from  cuentas_bancarias"
				+ "  	    left outer join bancos on bancos.banco=cuentas_bancarias.banco where cuentas_bancarias.cuenta_bancaria='"+Cuenta_Bancaria+"'";
		Statement stmt2= null;
						try {
							stmt2= con.conexion_IZAGAR().createStatement();
							ResultSet rs= stmt2.executeQuery(query);
							   while(rs.next()){
								   cargar_datosCuentaBancaria.setBanco(rs.getString("banco"));
								   cargar_datosCuentaBancaria.setCuenta_Contable(rs.getString("cuenta_contable"));
								   }
						} catch (Exception e) {
							JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion datos_Cuenta_Bancaria \n en la consulta: \n"+query+" \nSQLException: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
							e.printStackTrace();
							return null;
						}
		finally{
			if(stmt2!=null){stmt2.close();}
		}
		return cargar_datosCuentaBancaria;
	}
	
	
	public int Archivos_Empleado(int folio) throws SQLException{
		int contadorDeArchivosGenerados=0;
		String query = "SELECT t_emp.nombre+' '+t_emp.ap_paterno+' '+t_emp.ap_materno as empleado "
				+ "	,t_estab.nombre as establecimiento "
				+ "	,isnull(t_aEmp.solicitud,'') "
				+ "	,isnull(t_aEmp.acta_de_nacimiento,'') "
				+ "	,isnull(t_aEmp.curp,'') "
				+ "	,isnull(t_aEmp.hoja_de_seguro_social,'') "
				+ "	,isnull(t_aEmp.hoja_de_retencion_infonavit,'') "
				+ "	,isnull(t_aEmp.hoja_de_retencion_de_pension_alimenticia,'') "
				+ "	,isnull(t_aEmp.credencial_de_identificacion,'') "
				+ "	,isnull(t_aEmp.comprobante_de_domicilio,'') "
		+ " FROM tb_archivos_empleados t_aEmp "
		+ " left outer join tb_empleado t_emp on t_emp.folio = t_aEmp.folio_empleado "
		+ " inner join tb_establecimiento t_estab on t_estab.folio = t_emp.establecimiento_id "
		+ " where t_aEmp.folio_empleado = "+folio;

		Statement stmt = null;

		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while(rs.next()){
				
				String ruta = "C:\\DOCUMENTACION DE EMPLEADOS GENERADA EN SCOI\\"+rs.getString(1)+" ("+rs.getString(2)+")\\";
				File archivos = new File(ruta);
				
					// creamos fichero si no existe y escribimos archivo 
					if(!archivos.exists()){ 
						archivos.mkdirs();
					}
					
					for(int i=1; i<9; i++){
	            		
	            		Blob blob_pdf = rs.getBlob(i+2);
	            		
	            		if(blob_pdf.length() > 0){
								File photo = new File(ruta+i+".pdf");
								FileOutputStream fos = new FileOutputStream(photo);
							
					            byte[] buffer = new byte[1];
					            InputStream is = rs.getBinaryStream(i+2);
					            
					            while (is.read(buffer) > 0) {
					                fos.write(buffer);
					            }fos.close();
					            
					            contadorDeArchivosGenerados++;
	            		}
					}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return contadorDeArchivosGenerados;
	}
	
	public String[] Lista_Archivos_Empleado(int folio) throws SQLException{
		
		String[] lista = new String[8];
		
		String query = "SELECT t_emp.nombre+' '+t_emp.ap_paterno+' '+t_emp.ap_materno as empleado "
				+ "	,t_estab.nombre as establecimiento "
				+ "	,isnull(t_aEmp.solicitud,'') "
				+ "	,isnull(t_aEmp.acta_de_nacimiento,'') "
				+ "	,isnull(t_aEmp.curp,'') "
				+ "	,isnull(t_aEmp.hoja_de_seguro_social,'') "
				+ "	,isnull(t_aEmp.hoja_de_retencion_infonavit,'') "
				+ "	,isnull(t_aEmp.hoja_de_retencion_de_pension_alimenticia,'') "
				+ "	,isnull(t_aEmp.credencial_de_identificacion,'') "
				+ "	,isnull(t_aEmp.comprobante_de_domicilio,'') "
		+ " FROM tb_archivos_empleados t_aEmp "
		+ " left outer join tb_empleado t_emp on t_emp.folio = t_aEmp.folio_empleado "
		+ " inner join tb_establecimiento t_estab on t_estab.folio = t_emp.establecimiento_id "
		+ " where t_aEmp.folio_empleado = "+folio;

		Statement stmt = null;

		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while(rs.next()){
				
				String ruta = "C:\\DOCUMENTACION DE EMPLEADOS GENERADA EN SCOI\\"+rs.getString(1)+" ("+rs.getString(2)+")\\";
				File archivos = new File(ruta);
				
					// creamos fichero si no existe y escribimos archivo 
					if(!archivos.exists()){ 
						archivos.mkdirs();
					}
					
					
					for(int i=0; i<lista.length; i++){
	            			lista[i] = rs.getString(i+3).length()>0 ?"Capturado":"";
					}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return lista;
	}
	
	public boolean existe_cuenta(String fol_cuenta){
		String query = " if exists (select * from tb_cuentas_contables where folio_cuenta_contable = '"+fol_cuenta+"') "
						+ " begin	select 'true' as existe end "
						+ " else begin 	select 'false' as existe end ";
		boolean existe = false;
		try {
			Statement stmt = new Connexion().conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()){ existe = rs.getBoolean(1); }
	
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	    return existe; 
	}
	
	public boolean existe_subcuenta(String fol_cuenta, String fol_subcuenta){
		String query = " if exists (select * from tb_subcuentas_contables where folio_cuenta_contable= '"+fol_cuenta+"' and folio_subcuenta_contable = '"+fol_subcuenta+"') "
						+ "	begin	select 'true' as existe end "
						+ " else begin 	select 'false' as existe end ";
		boolean existe = false;
		try {
			Statement stmt = new Connexion().conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()){ existe = rs.getBoolean(1); }
	
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	    return existe; 
	}
	
	public boolean existe_subsubcuenta(String fol_cuenta, String fol_subcuenta, String fol_subsubcuenta){
		String query = " if exists (select * from tb_subsubcuentas_contables where folio_cuenta_contable= '"+fol_cuenta+"' and folio_subcuenta_contable = '"+fol_subcuenta+"' and folio_subsubcuenta_contable = '"+fol_subsubcuenta+"') "
						+ " begin	select 'true' as existe end "
						+ " else begin 	select 'false' as existe end";
		boolean existe = false;
		try {
			Statement stmt = new Connexion().conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()){ existe = rs.getBoolean(1); }
	
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	    return existe; 
	}
	
	public Object[][] Filtro_De_Cuentas_polizas() throws SQLException{
		Statement stmt = null;
		
		String query = "exec sp_select_filtro_de_polizas_contables ";
		Object[][] rp_cuenta = new Object[getFilas(query)][4];
		
		try {
			
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			int i=0;
			while(rs.next()){
				for(int j=0; j<4; j++){
					rp_cuenta[i][j] = rs.getObject(j+1);
				}
				i++;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al Buscar en el procedimiento sp_select_filtro_de_polizas_contables \nSQLServerException:"+e,"Avise Al Administrador del Sistema",JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			
			return null;
		}
		finally{
			if(stmt != null){stmt.close();}
		}
		return rp_cuenta;
	}
	
	public Object[][] Filtro_De_Cuentas_polizas_con_parametro(String cuenta) throws SQLException{
		Statement stmt = null;
		
		String query = "exec sp_select_filtro_de_polizas_contables_con_parametro '"+cuenta+"'";
		Object[][] rp_cuenta = new Object[getFilas(query)][8];
		
		try {
			
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			int i=0;
			while(rs.next()){
				for(int j=0; j<8; j++){
					rp_cuenta[i][j] = rs.getObject(j+1);
				}
				i++;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al Buscar en el procedimiento sp_select_filtro_de_polizas_contables \nSQLServerException:"+e,"Avise Al Administrador del Sistema",JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			
			return null;
		}
		finally{
			if(stmt != null){stmt.close();}
		}
		return rp_cuenta;
	}
	
	public boolean activar_establecimiento_en_mpolizas(String fol_cuenta){
		String query = "if(select establecimiento_id from tb_cuentas_contables where folio_cuenta_contable = '"+fol_cuenta+"')=0"
					+ "	 		begin select 'true' as activo end"
					+ " else 	begin select 'false' as activo end";
		boolean existe = false;
		try {
			Statement stmt = new Connexion().conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()){ existe = rs.getBoolean(1); }
	
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	    return existe; 
	}
	
	public Object[][] Filtro_De_Referencia_Polizas(String ref) throws SQLException{
		Statement stmt = null;
		
		String query = "";
		switch(ref){
//			case "Departamento": 	query = "select folio,departamento as nombre from tb_departamento where status = 1 order by  nombre"; break;
//			case "Establecimiento": query = "select folio,nombre as nombre from tb_establecimiento where status = 1  order by nombre"; break;
			case "Empleado": 		query = "select folio,nombre+' '+ap_paterno+' '+ap_materno as nombre from tb_empleado order by nombre"; break;
			case "Proveedor": 		query = "select folio_proveedor,nombre+' '+ap_paterno+' '+ap_materno as nombre from tb_proveedores where status=1  order by nombre"; break;
		}
		
		Object[][] referencia = new Object[getFilas(query)][2];
		
		try {
			
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			int i=0;
			while(rs.next()){
				for(int j=0; j<2; j++){
					referencia[i][j] = rs.getObject(j+1);
				}
				i++;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al Buscar en [Filtro_De_Referencia_Polizas] \nSQLServerException:"+e,"Avise Al Administrador del Sistema",JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			
			return null;
		}
		finally{
			if(stmt != null){stmt.close();}
		}
		return referencia;
	}
	
	public boolean existe_folio_cheque(String cuenta, String folio_cheque){
		String query = " if exists (select folio_documento_pago as folio_consecutivo from tb_pagos_contabilidad where cuenta_bancaria = '"+cuenta+"' and folio_documento_pago = '"+folio_cheque+"') "
					+ "		select 'true' as existe "
					+ "	 else "
					+ "		select 'false' as existe ";
		boolean existe = false;
		try {
			Statement stmt = new Connexion().conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()){ existe = rs.getBoolean(1); }
	
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	    return existe; 
	}
	
	public Object[][] Filtro_De_Cuentas() throws SQLException{
		Statement stmt = null;
		
		String query = " select a.* from (select folio_cuenta_contable as folio "
				+ "						,cuenta_contable as cuenta "
				+ "				from tb_cuentas_contables "
				+ "				union "
				+ "				select folio_cuenta_contable+folio_subcuenta_contable as folio "
				+ "					,subcuenta_contable as cuenta "
				+ "				from tb_subcuentas_contables "
				+ "				union "
				+ "				select folio_cuenta_contable+folio_subcuenta_contable+folio_subsubcuenta_contable as folio "
				+ "						,subsubcuenta_contable as cuenta "
				+ "				from tb_subsubcuentas_contables ) a";

		Object[][] rp_cuenta = new Object[getFilas(query)][2];
		
		try {
			
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			int i=0;
			while(rs.next()){
				for(int j=0; j<2; j++){
					rp_cuenta[i][j] = rs.getObject(j+1);
				}
				i++;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarSQL en la funcion [ Filtro_De_Cuentas ] \nSQLServerException:"+e,"Avise Al Administrador del Sistema",JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			
			return null;
		}
		finally{
			if(stmt != null){stmt.close();}
		}
		return rp_cuenta;
	}
	
	public Object[][] Filtro_De_Cuentas_Con_Parametro(String codigo) throws SQLException{
		Statement stmt = null;
		
		String query = " select a.* from (select folio_cuenta_contable as folio "
						+ "						,cuenta_contable as cuenta "
						+ "				from tb_cuentas_contables "
						+ "				union "
						+ "				select folio_cuenta_contable+folio_subcuenta_contable as folio "
						+ "					,subcuenta_contable as cuenta "
						+ "				from tb_subcuentas_contables "
						+ "				union "
						+ "				select folio_cuenta_contable+folio_subcuenta_contable+folio_subsubcuenta_contable as folio "
						+ "						,subsubcuenta_contable as cuenta "
						+ "				from tb_subsubcuentas_contables ) a "
						+ " where a.folio= '"+codigo+"' order by a.folio " ;
		
		Object[][] rp_cuenta = new Object[getFilas(query)][2];
		
		try {
			
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			int i=0;
			while(rs.next()){
				for(int j=0; j<2; j++){
					rp_cuenta[i][j] = rs.getObject(j+1);
				}
				i++;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarSQL en la funcion [ Filtro_De_Cuentas_Con_Parametro ] \nSQLServerException:"+e,"Avise Al Administrador del Sistema",JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			
			return null;
		}
		finally{
			if(stmt != null){stmt.close();}
		}
		return rp_cuenta;
	}
	
	public Object[][] Reporte_Auxiliares(String cuentaIn, String cuentaFin, String fechaIn, String fechaFin) throws SQLException{
		Statement stmt = null;
		
		String query = "exec sp_Reporte_De_Auxiliar_De_Cuentas '"+cuentaIn+"','"+cuentaFin+"','"+fechaIn+" 00:00:00"+"','"+fechaFin+" 23:59:59','N','MARCO ANTONIO BODART GUZMAN'";

		Object[][] rp_cuenta = new Object[getFilas(query)][12];
		
		try {
			
			stmt = new Connexion().conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			int i=0;
			while(rs.next()){
				for(int j=0; j<12; j++){
					rp_cuenta[i][j] = rs.getObject(j+1);
				}
				i++;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarSQL en la funcion [ Reporte_Auxiliares ] \nSQLServerException:"+e,"Avise Al Administrador del Sistema",JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			
			return null;
		}
		finally{
			if(stmt != null){stmt.close();}
		}
		return rp_cuenta;
	}
	
	public Obj_Alta_Proveedores_Polizas Proveedor_Nuevo() throws SQLException{
		Obj_Alta_Proveedores_Polizas prv = new Obj_Alta_Proveedores_Polizas();
		String query = "exec sp_nuevo_proveedor";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				prv.setFolio_proveedor(rs.getInt("Maximo"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return prv;
	}
	
	public Obj_Alta_Proveedores_Polizas Proveedor(int folio_prv) throws SQLException{
		Obj_Alta_Proveedores_Polizas prv = new Obj_Alta_Proveedores_Polizas();
		
		String query = "select folio_proveedor,nombre,ap_paterno,ap_materno,direccion,telefono from tb_proveedores where folio_proveedor = "+folio_prv;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				prv.setFolio_proveedor(rs.getInt("folio_proveedor"));
				prv.setNombre(rs.getString("nombre"));
				prv.setAp_paterno(rs.getString("ap_paterno"));
				prv.setAp_materno(rs.getString("ap_materno"));
				prv.setDireccion(rs.getString("direccion"));
				prv.setTelefono(rs.getString("telefono"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return prv;
	}
	
	public Object[][] Filtro_De_Orden_De_Pago_Efectivo(String fechaInicial) throws SQLException{
		Statement stmt = null;
		
		String query = " select folio "
						+ " ,CONVERT(varchar(20),tb_orden_de_pago_en_efectivo.fecha_mov,103)+' '+CONVERT(varchar(20),tb_orden_de_pago_en_efectivo.fecha_mov,108) as fecha "
						+ " ,tb_orden_de_pago_en_efectivo.cantidad as importe "
						+ " ,CASE WHEN (tb_orden_de_pago_en_efectivo.tipo_beneficiario = 'E') "
						+ "		THEN (select tb_empleado.nombre+' '+tb_empleado.ap_paterno+' '+tb_empleado.ap_materno from tb_empleado where tb_empleado.folio = tb_orden_de_pago_en_efectivo.folio_beneficiario) "
						+ "	  ELSE (select tb_proveedores.nombre+' '+tb_proveedores.ap_paterno+' '+tb_proveedores.ap_materno from tb_proveedores where tb_proveedores.folio_proveedor = tb_orden_de_pago_en_efectivo.folio_beneficiario) "
						+ "	END beneficiario "
						+ " ,detalle "
						+ " from tb_orden_de_pago_en_efectivo "
						+ " where tb_orden_de_pago_en_efectivo.fecha_mov > '"+fechaInicial+"'"
						+ "order by fecha_mov desc";
		
		Object[][] referencia = new Object[getFilas(query)][5];
		
		try {
			
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			int i=0;
			while(rs.next()){
				for(int j=0; j<5; j++){
					referencia[i][j] = rs.getObject(j+1);
				}
				i++;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al Buscar en [Filtro_De_Referencia_Polizas] \nSQLServerException:"+e,"Avise Al Administrador del Sistema",JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			
			return null;
		}
		finally{
			if(stmt != null){stmt.close();}
		}
		return referencia;
	}
	
	public Object[][] Filtro_Polizas_Guardadas(String fecha) throws SQLException{
		Statement stmt = null;
		String query = "sp_select_polizas_guardadas '"+fecha+"'";
		Object[][] referencia = new Object[getFilas(query)][5];
		
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			int i=0;
			while(rs.next()){
				for(int j=0; j<5; j++){
					referencia[i][j] = rs.getObject(j+1);
				}
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al Buscar en [Filtro_Polizas_Guardadas] \nSQLServerException:"+e,"Avise Al Administrador del Sistema",JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			return null;
		}
		finally{
			if(stmt != null){stmt.close();}
		}
		return referencia;
	}
	
	public Object[] registro_Polizas_Guardadas(String folio_poliza, String tipo_poliza, String fecha_poliza){
		Statement stmt = null;
		String query = " SELECT tb_polizas.folio "
				+ " ,tb_configuracion_de_polizas.nombre AS tipo_de_poliza "
				+ " ,convert(varchar(20),tb_polizas.fecha_poliza,103) as fecha_poliza "
				+ " ,tb_polizas.notas "
				+ " ,tb_polizas.concepto "
				+ " ,tb_folios.transaccion "
				+ " ,tb_polizas.referencia	"
				+ " ,case when (tb_folios.transaccion = 'Empleado') then (select nombre+' '+ap_paterno+' '+ap_materno from tb_empleado where folio = tb_polizas.referencia) "
				+ " 	 when (tb_folios.transaccion = 'Establecimiento') then (select nombre from tb_establecimiento where folio = tb_polizas.referencia) "
				+ "		 when (tb_folios.transaccion = 'Departamento') then (select departamento from tb_departamento where folio = tb_polizas.referencia) "
				+ "		 when (tb_folios.transaccion = 'Proveedor') then (select nombre+' '+ap_paterno+' '+ap_materno from tb_proveedores where folio_proveedor = tb_polizas.referencia) "
				+ "	else '' end	 as beneficiario "
				+ " ,ISNULL(tb_pagos_contabilidad.folio_documento_pago,'') AS folio_documento_pago "
				+ " ,ISNULL(tb_pagos_contabilidad.cuenta_bancaria,'') AS cuenta_bancaria"
				+ " ,CASE WHEN (tb_pagos_contabilidad.forma_pago 	=1) THEN 'Cheque' "
				+ "			WHEN (tb_pagos_contabilidad.forma_pago=2) THEN 'Cheque Banco Interno' "
				+ "			WHEN (tb_pagos_contabilidad.forma_pago=3) THEN 'Transpaso' "
				+ "			WHEN (tb_pagos_contabilidad.forma_pago=4) THEN 'Vale'"
				+ "	ELSE 'Forma De Pago' END as forma_pago "
				+ " ,ISNULL(tb_pagos_contabilidad.total,'') as total"
				+ " ,ISNULL(tb_pagos_contabilidad.tipo_de_documento_de_pago,'') AS   tipo_de_documento_de_pago "
				+ " from tb_polizas "
				+ " inner join tb_configuracion_de_polizas on tb_configuracion_de_polizas.tipo = tb_polizas.tipo_poliza "
				+ " left outer join tb_pagos_contabilidad on tb_pagos_contabilidad.folio_poliza = tb_polizas.folio AND tb_pagos_contabilidad.tipo_poliza='"+tipo_poliza+"' AND CONVERT(VARCHAR(20),tb_pagos_contabilidad.fecha_poliza,103) = SUBSTRING('"+fecha_poliza+"',0,CHARINDEX(' ', '"+fecha_poliza+"')) "
				+ " inner join tb_folios on tb_folios.folio_transaccion = tb_polizas.referencia_folio_transaccion "
				+ " where ltrim(rtrim(tb_polizas.folio)) = '"+folio_poliza+"' "
				+ " AND tb_polizas.tipo_poliza='"+tipo_poliza+"' "
				+ " AND CONVERT(VARCHAR(20),tb_polizas.fecha_poliza,103) = SUBSTRING('"+fecha_poliza+"',0,CHARINDEX(' ', '"+fecha_poliza+"')) " ;
		
		Object[] Poliza = new Object[13];
		
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				for(int j=0; j<Poliza.length; j++){
					Poliza[j] = rs.getObject(j+1);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al Buscar en [Filtro_De_Referencia_Polizas] \nSQLServerException:"+e,"Avise Al Administrador del Sistema",JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			return null;
		}
		finally{
			if(stmt != null){
				try {
					stmt.close();
				} catch (SQLException e){
					e.printStackTrace();
				}
			}
		}
		return Poliza;
	}
	
	public String[][] registro_mPolizas_Guardadas(String folio_poliza, String tipo_poliza, String fecha_poliza){
		Statement stmt = null;
		
		String query = "select tb_mpolizas.folio_cuenta_contable "
						+ " 	,tb_mpolizas.folio_subcuenta_contable "
						+ " 	,tb_mpolizas.folio_subsubcuenta_contable "
						+ " 	,(SELECT a.NOMBRE FROM(SELECT tb_subsubcuentas_contables.subsubcuenta_contable       AS NOMBRE "
						+ "									,tb_subsubcuentas_contables.folio_cuenta_contable+tb_subsubcuentas_contables.folio_subcuenta_contable+tb_subsubcuentas_contables.folio_subsubcuenta_contable AS codigo"
						+ "		 				FROM tb_cuentas_contables "
						+ "							inner join tb_subcuentas_contables ON tb_subcuentas_contables.folio_cuenta_contable = tb_cuentas_contables.folio_cuenta_contable "
						+ "							inner join tb_subsubcuentas_contables ON tb_subsubcuentas_contables.folio_cuenta_contable = tb_cuentas_contables.folio_cuenta_contable and tb_subsubcuentas_contables.folio_subcuenta_contable = tb_subcuentas_contables.folio_subcuenta_contable "
						+ "				union all "
						+ "						SELECT tb_subcuentas_contables.subcuenta_contable       AS NOMBRE "
						+ "								,tb_subcuentas_contables.folio_cuenta_contable+tb_subcuentas_contables.folio_subcuenta_contable AS codigo"
						+ "						FROM tb_cuentas_contables "
						+ "								inner join tb_subcuentas_contables ON tb_subcuentas_contables.folio_cuenta_contable = tb_cuentas_contables.folio_cuenta_contable and  tb_subcuentas_contables.folio_cuenta_contable+tb_subcuentas_contables.folio_subcuenta_contable not in(select folio_cuenta_contable+folio_subcuenta_contable  from tb_subsubcuentas_contables) "
						+ "				union all "
						+ "						SELECT tb_cuentas_contables.cuenta_contable             AS NOMBRE "
						+ "								, tb_cuentas_contables.folio_cuenta_contable AS codigo"
						+ "						FROM tb_cuentas_contables "
						+ "							WHERE tb_cuentas_contables.folio_cuenta_contable not in(select folio_subcuenta_contable from tb_subcuentas_contables) )a "
						+ " where a.codigo= tb_mpolizas.folio_cuenta_contable+tb_mpolizas.folio_subcuenta_contable+tb_mpolizas.folio_subsubcuenta_contable ) "
						+ " 	,CASE WHEN tb_mpolizas.cargo_abono = 'C' THEN convert(varchar(20),tb_mpolizas.importe)  ELSE '' END AS Cargo "
						+ " 	,CASE WHEN tb_mpolizas.cargo_abono = 'A' THEN convert(varchar(20),tb_mpolizas.importe)  ELSE '' END AS Abono "
						+ " 	,tb_mpolizas.concepto as Concepto "
						+ " 	,isnull(tb_establecimiento.nombre,'NO APLICA') "
						+ " from tb_mpolizas "
						+ " left outer join tb_establecimiento on tb_establecimiento.folio = tb_mpolizas.establecimiento_id "
						+ " where ltrim(rtrim(tb_mpolizas.folio)) = '"+folio_poliza+"' "
						+ " AND tb_mpolizas.tipo_poliza='"+tipo_poliza+"' "
						+ "  AND tb_mpolizas.fecha_poliza= CONVERT(datetime,'"+fecha_poliza+"')" ;
		
		String[][] mPoliza = new String[getFilas(query)][8];
		
		try {
			
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			int i=0;
			while(rs.next()){
				
				for(int j=0; j<8; j++){
					mPoliza[i][j] = rs.getString(j+1);
				}
				i++;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al Buscar en [Filtro_De_Referencia_Polizas] \nSQLServerException:"+e,"Avise Al Administrador del Sistema",JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			
			return null;
		}
		finally{
			if(stmt != null){
				try {
					stmt.close();
				} catch (SQLException e){
					e.printStackTrace();
				}
			}
		}
		return mPoliza;
	}
	
	public Obj_Alta_De_Productos Productos(String folio) throws SQLException{
		Obj_Alta_De_Productos prod = new Obj_Alta_De_Productos();
		String query = " select tb_productos.folio_producto "
				+ "		,tb_productos.descripcion "
				+ "		,tb_unidad_de_medida_de_productos.descripcion as unidad_de_medida"
				+ "		,tb_uso_de_productos.descripcion as uso"
				+ "		,tb_productos.codigo_de_barras_principal "
				+ "		,tb_productos.costo "
				+ "		,tb_productos.precio_de_venta "
				+ "		,case when ( tb_productos.status = 'V') then 'VIGENTE' "
				+ "			else 'CANCELADO' "
				+ "		 end as status "
				+ " from tb_productos "
				+ " inner join tb_unidad_de_medida_de_productos on tb_unidad_de_medida_de_productos.folio = tb_productos.folio_unidad_de_medida "
				+ " inner join tb_uso_de_productos on tb_uso_de_productos.folio = tb_productos.folio_uso "
				+ " where tb_productos.folio_producto = '"+folio+"'";
		
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
		    ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				prod.setFolio(rs.getString("folio_producto"));
				prod.setDescripcion(rs.getString("descripcion").trim());
				prod.setUnidadDeMedida(rs.getString("unidad_de_medida").trim());
				prod.setUso(rs.getString("uso").trim());
				prod.setCodigoDeBarras(rs.getString("codigo_de_barras_principal"));
				prod.setCosto(rs.getDouble("costo"));
				prod.setPrecioDeVenta(rs.getDouble("precio_de_venta"));
				prod.setStatus(rs.getString("status"));
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error");
			JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion [ Productos ] SQLException: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		finally{
			 if (stmt != null) { stmt.close(); }
		}
		return prod;
	}
	
	public int  Folio_De_Trabajo(String fecha_trabajo, String concentrado){
		int folio=0;
		
		String query = "select top 1 folio_trabajo_de_cortes from tb_alimentacion_de_cortes where convert(datetime,convert(varchar(20),tb_alimentacion_de_cortes.fecha_de_trabajo,103)) = convert(datetime,'"+fecha_trabajo+"') "
					+ "  and tb_alimentacion_de_cortes.folio_grupo_para_cortes = (select folio_grupo_para_cortes from tb_grupos_para_cortes where grupo_para_cortes = '"+concentrado+"')";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				folio=(rs.getInt("folio_trabajo_de_cortes"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(stmt!=null){try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}}
		}
		return folio;
	}
	public Obj_Unidades_De_Medida_De_Producto existe_unidad_de_medida(int folio){
		Obj_Unidades_De_Medida_De_Producto unidad = new Obj_Unidades_De_Medida_De_Producto();
		String query = "select folio,"
				+ " descripcion,"
				+ " case when status='V' then 'VIGENTE' else 'CANCELADO' end  as status"
				+ " from tb_unidad_de_medida_de_productos where folio ="+folio;
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				unidad.setFolio(rs.getInt("folio"));
				unidad.setUnidad(rs.getString("descripcion").trim());
				unidad.setEstatus(rs.getString("status").trim());
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion Existe_unidad_de_medida En"+query+" SQLException: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		return unidad;
	}
	
	public boolean existe_Producto_SCOI(String folio_producto){
		String query = "exec sp_existe_producto '"+folio_producto+"' ";
		boolean existe = false;
		try { Statement s = con.conexion().createStatement();
			  ResultSet rs = s.executeQuery(query);
			while(rs.next()){
			    	existe = rs.getBoolean(1);
			      }
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion existe_Producto \n SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
		}
    return existe;
	}
	
	public Obj_Alimentacion_De_Codigos_Adicionales datos_producto_scoi(String cod_prod) throws SQLException{
		Obj_Alimentacion_De_Codigos_Adicionales datosproducto = new Obj_Alimentacion_De_Codigos_Adicionales();
		String query = "exec sp_select_datos_producto '"+cod_prod+"' ";
		Statement stmt2= null;
						try {
							stmt2= con.conexion().createStatement();
							ResultSet rs2= stmt2.executeQuery(query);
								   while(rs2.next()){
									   datosproducto.setFolio_Producto(rs2.getString("folio_producto"));
									   datosproducto.setDescripcion(rs2.getString("descripcion"));
									   datosproducto.setUDM(rs2.getString("UDM"));
									   datosproducto.setUso(rs2.getString("uso"));
									   datosproducto.setCosto(rs2.getDouble("Costo"));
									   datosproducto.setPrecio_Venta(rs2.getDouble("precio_de_venta"));
									   datosproducto.setEstatus(rs2.getString("Estatus"));
									   datosproducto.setCodigo_Barras(rs2.getString("codigo_de_barras_principal"));
									   datosproducto.setCodigo_Tecleado(rs2.getString("codigo_tecleado"));									   
								   }
						} catch (Exception e) {
							JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion datos_producto \n SQLException: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
							e.printStackTrace();
							return null;
						}
		finally{
			if(stmt2!=null){stmt2.close();}
		}
		return datosproducto;
	}
	
	//Buscamos la fecha y hora del ultimo movimiento de cascos y la actual
	public String[] fechas_de_movimiento_de_cascos(){
		String[] fechas_horas = new String[4];
		String query = "declare @fecha_fin datetime "
				+ " set @fecha_fin = GETDATE(); "
				+ " select convert(varchar(20),ultima_fecha_de_transpaso_de_movimientos_de_cascos,103) as fecha_inicial "
				+ " 		 ,convert(varchar(20),ultima_fecha_de_transpaso_de_movimientos_de_cascos,108) as hora_inicial "
				+ "		 ,convert(varchar(20),@fecha_fin,103) as fecha_final "
				+ "		 , convert(varchar(4),datepart(HOUR,@fecha_fin))+':'+convert(varchar(4),(datepart(MINUTE,@fecha_fin)-5))+':'+convert(varchar(4),datepart(SECOND,@fecha_fin)) as hora_final "
				+ " from tb_configuracion_sistema";
		
		Statement stmt = null;
		try {
			
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				
				fechas_horas[0]=rs.getString("fecha_inicial").trim();
				fechas_horas[1]=rs.getString("hora_inicial").trim();
				fechas_horas[2]=rs.getString("fecha_final").trim();
				fechas_horas[3]=rs.getString("hora_final").trim();
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion Obj_Horarios store procedure sp_select_horarios: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		finally{
			if(stmt!=null){try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}}
		}
		return fechas_horas;
	}
	
	public String[][] buscarCascosPendientes(String fecha_in, String fecha_fin){
		Statement stmt = null;
		String query = "exec sp_traspaso_de_movimientos_de_cascos '"+fecha_in+"','"+fecha_fin+"'";
		
		String[][] cascosPendientes = new String[getFilas(query)][8];
		
		try {
			
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			int i=0;
			while(rs.next()){
				
				for(int j=0; j<8; j++){
					cascosPendientes[i][j] = rs.getString(j+1);
				}
				i++;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al Buscar en [buscarCascosPendientes] \nSQLServerException:"+e,"Avise Al Administrador del Sistema",JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			
			return null;
		}
		finally{
			if(stmt != null){
				try {
					stmt.close();
				} catch (SQLException e){
					e.printStackTrace();
				}
			}
		}
		return cascosPendientes;
	}
	
	public String[] buscarFolioSemanaParaPlanSemanal(int dias){
		Statement stmt = null;
		
//		String query = "declare @mover int set @mover = "+dias+"; "
//				+ " select case when DATENAME(WEEK,GETDATE()+@mover)>52 "
//				+ "		then convert(varchar(20),(DATENAME(YEAR,GETDATE()+@mover)+1))+'01' "
//				+ "				 else DATENAME(YEAR,GETDATE()+@mover)+ RIGHT('00'+CONVERT(VARCHAR(2),DATENAME(WEEK,GETDATE()+@mover)),2) "
//				+ "			end, "
//				+ "		 convert(varchar(20),GETDATE()+@mover-datepart(WEEKDAY,GETDATE()-1),103)+' - '+convert(varchar(20),GETDATE()+@mover-datepart(WEEKDAY,GETDATE()-1)+6,103)";
		
		String query =" declare @fecha_inicial datetime=dbo.Primer_Dia_De_La_Semana(getdate()),@mover int set @mover = "+dias+"; " 
					+ " select DATENAME(YEAR,GETDATE()+@mover)+ RIGHT('00'+CONVERT(VARCHAR(2),DATENAME(WEEK,GETDATE()+@mover)),2) as folio, "
					+ " convert(varchar(20),@fecha_inicial+@mover,103)+' - '+convert(varchar(20),@fecha_inicial+6+@mover,103) as periodo, "
					+ "	convert(varchar(20),@fecha_inicial+0+@mover,103) as lunes , "
					+ " convert(varchar(20),@fecha_inicial+1+@mover,103) as martes ,	"
					+ " convert(varchar(20),@fecha_inicial+2+@mover,103) as miercoles , "
					+ " convert(varchar(20),@fecha_inicial+3+@mover,103) as jueves , "
					+ " convert(varchar(20),@fecha_inicial+4+@mover,103) as viernes , "
					+ " convert(varchar(20),@fecha_inicial+5+@mover,103) as sabado , "
					+ " convert(varchar(20),@fecha_inicial+6+@mover,103) as domingo , "
					+ "CONVERT(INT,DATEPART(DW,getdate()))-1 as dia_actual  ";
					
		String[] datos = new String[10];
		
		try {
			
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()){
					datos[0] = rs.getString(1);
					datos[1] = rs.getString(2);
					
					datos[2] = rs.getString(3);
					datos[3] = rs.getString(4);
					datos[4] = rs.getString(5);
					datos[5] = rs.getString(6);
					datos[6] = rs.getString(7);
					datos[7] = rs.getString(8);
					datos[8] = rs.getString(9);
					datos[9] = rs.getString(10);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al Buscar en [buscarFolioSemanaParaPlanSemanal] \nSQLServerException:"+e,"Avise Al Administrador del Sistema",JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			
			return null;
		}
		finally{
			if(stmt != null){
				try {
					stmt.close();
				} catch (SQLException e){
					e.printStackTrace();
				}
			}
		}
		return datos;
	}
	
	public String[] buscarEmpleadoParaPlanSemanal(){
		Statement stmt = null;
		
		String query = " select tb_empleado.nombre+' '+tb_empleado.ap_paterno+' '+tb_empleado.ap_materno as empleado "
				+ " ,tb_establecimiento.nombre as establecimiento "
				+ " ,tb_departamento.departamento as departamento "
				+ " ,tb_puesto.nombre as puesto "
				+ "  from tb_empleado "
				+ " inner join tb_establecimiento on tb_establecimiento.folio = tb_empleado.establecimiento_id "
				+ " inner join tb_departamento on tb_departamento.folio = tb_empleado.departamento "
				+ " inner join tb_puesto on tb_puesto.folio = tb_empleado.puesto_id "
				+ " where tb_empleado.folio = "+(new Obj_Usuario().LeerSession().getFolio());
		String[] datos = new String[4];
		
		try {
			
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()){
					datos[0] = rs.getString(1);
					datos[1] = rs.getString(2);
					datos[2] = rs.getString(3);
					datos[3] = rs.getString(4);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al Buscar en [buscarEmpleadoParaPlanSemanal] \nSQLServerException:"+e,"Avise Al Administrador del Sistema",JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			
			return null;
		}
		finally{
			if(stmt != null){
				try {
					stmt.close();
				} catch (SQLException e){
					e.printStackTrace();
				}
			}
		}
		return datos;
	}
	
	public int  cantidad_de_actividades(){
		int cantidad=0;
		String query = "select objetivos_de_plan_semanal from tb_configuracion_sistema ";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				cantidad=(rs.getInt("objetivos_de_plan_semanal"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(stmt!=null){try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}}
		}
		return cantidad;
	}
	
	public String[][] buscarObjetivos_De_Plan_Semanal(String fecha){
		Statement stmt = null;
		
		String query = "exec sp_select_objetivos_del_periodo "+(new Obj_Usuario().LeerSession().getFolio())+",'"+fecha+"'";
		
		String[][] datos = new String[getFilas(query)][3];
		
		try {
			
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			int i = 0;
			while(rs.next()){
					datos[i][0] = rs.getString(1);
					datos[i][1] = rs.getString(2)+"  ";
					datos[i][2] = rs.getString(3)+"  ";
				i++;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al Buscar en [buscarObjetivos_De_Plan_Semanal] \nSQLServerException:"+e,"Avise Al Administrador del Sistema",JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			
			return null;
		}
		finally{
			if(stmt != null){
				try {
					stmt.close();
				} catch (SQLException e){
					e.printStackTrace();
				}
			}
		}
		return datos;
	}
	
	public String[][] buscarObjetivos_De_Plan_Semanal_Editable(int folio){
		Statement stmt = null;
		
		String query = " select objetivo,folio_objetivo from tb_objetivos_de_plan_semanal "
						+ " where estatus = 'PLA' and folio = "+folio+"and usuario = "+(new Obj_Usuario().LeerSession().getFolio());
		
		String[][] datos = new String[getFilas(query)][3];
		
		try {
			
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			int i = 0;
			while(rs.next()){
					datos[i][0] = "";
					datos[i][1] = rs.getString(1);
					datos[i][2] = rs.getString(2);
					
				i++;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al Buscar en [buscarObjetivos_De_Plan_Semanal_Editable] \nSQLServerException:"+e,"Avise Al Administrador del Sistema",JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			
			return null;
		}
		finally{
			if(stmt != null){
				try {
					stmt.close();
				} catch (SQLException e){
					e.printStackTrace();
				}
			}
		}
		return datos;
	}
	
	public boolean fila_Objetivos_de_la_semana(int folio){
		String query = "declare @folio int "
					 + " set @folio = "+folio+" "
					 + " if(select case when DATENAME(WEEK,GETDATE())>52 "
					 + "						then convert(varchar(20),(DATENAME(YEAR,GETDATE())+1))+'01' "
					 + "								 else DATENAME(YEAR,GETDATE())+ RIGHT('00'+CONVERT(VARCHAR(2),DATENAME(WEEK,GETDATE())),2) "
					 + "							end)<=@folio "
					 + "	begin "
					 + "		if(select datepart(dw,GETDATE()))<2 "// 2 es el dia martes
					 + "				begin "
					 + "					select 'true' "
					 + "				end "
					 + "			else "
					 + "				begin "
					 + "					select 'false' "
					 + "				end "
					 + "	end "
					 + "else "
					 + "	begin "
					 + "			select 'false' "
					 + "	end ";
		
		System.out.println(query);
		
		boolean editable = false;
		try {				
			Statement s = con.conexion().createStatement();
			ResultSet rs = s.executeQuery(query);
			
			while(rs.next()){
				editable = rs.getBoolean(1);
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
			
		return editable;
	}
	
	public int  cantidad_de_Objetivos_por_folio(int folio){
		int cantidad=0;
		String query = "select count(folio) as cantidad from tb_objetivos_de_plan_semanal where estatus = 'PLA' and folio = "+folio+" and usuario = "+(new Obj_Usuario().LeerSession().getFolio());
//		String query = "select count(folio) as cantidad from tb_objetivos_de_plan_semanal where folio = "+folio;
		System.out.println(query);
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				cantidad=(rs.getInt("cantidad"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(stmt!=null){try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}}
		}
		return cantidad;
	}
	
	public String[][] getTablaActividadesDiarias(String fecha,int dia, String Ventana){
		
		int columnas =  Ventana.equals("Alimentacion")   ?   8  :   7   ;
		
		String[][] Matriz = null;
		String actividades = "exec sp_consulta_de_actividades_del_dia "+(new Obj_Usuario().LeerSession().getFolio())+",'"+fecha+"',"+dia;
		Matriz = new String[getFilas(actividades)][columnas];
		Statement s;
		ResultSet rs;
		try {			
			s = con.conexion().createStatement();
			rs = s.executeQuery(actividades);
			int i=0;
			while(rs.next()){
				Matriz[i][0] = rs.getString(1)+"  ";
				Matriz[i][1] = rs.getString(2);
				
				if(columnas==8){
					
					Matriz[i][2] = rs.getString(10);
					Matriz[i][3] = rs.getString(3);
					Matriz[i][4] = rs.getString(4);
					
					Matriz[i][5] = "";
					Matriz[i][6] = "";
					Matriz[i][7] = rs.getString(8);
				}else{
					Matriz[i][2] = rs.getString(3);
					Matriz[i][3] = rs.getString(4);
					Matriz[i][4] = rs.getString(5);
					Matriz[i][5] = rs.getString(7);
					Matriz[i][6] = rs.getString(8);
				}
				
				i++;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en Buscar SQL  en la funcion getTablaActividadesDiarias\nen el procedimiento almacenado \n"+actividades+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
		}
		return Matriz;
	}
	
	public String[][] actividadesConEvidencia(String fecha,int folio_empleado){
		
		String[][] Matriz = null;
		String query = "exec sp_select_actividades_con_evidencia '"+fecha+"',"+folio_empleado;
		Matriz = new String[getFilas(query)][5];
		Statement s;
		ResultSet rs;
		try {			
			s = con.conexion().createStatement();
			rs = s.executeQuery(query);
			int i=0;
			while(rs.next()){
				Matriz[i][0] = rs.getString(1);
				Matriz[i][1] = rs.getString(2);
				Matriz[i][2] = rs.getString(3);
				Matriz[i][3] = rs.getString(4);
				Matriz[i][4] = rs.getString(5);
				i++;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en Buscar SQL  en la funcion actividadesConEvidencia\nen el procedimiento almacenado \n"+query+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
		}
		return Matriz;
	}
	
	public int Evidencia_De_Actividades_Capturadas(String fecha, int folio_empleado){
		int contadorDeArchivosGenerados=0;
		String query = "exec sp_select_actividades_con_evidencia_para_generar '"+fecha+"',"+folio_empleado;

		Statement stmt = null;

		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			

			while(rs.next()){
				
				String ruta = "C:\\EVIDENCIA_DE_ACTIVIDADES_PLAN_SEMANAL_SCOI\\"+rs.getString(1).replace(" ", "_")+"\\";
				File archivos = new File(ruta);
				
					// creamos fichero si no existe y escribimos archivo 
					if(!archivos.exists()){ 
						archivos.mkdirs();
					}
	            		
	            		Blob blob_pdf = rs.getBlob(3);
	            		
	            		if(blob_pdf.length() > 0){
	            			
	            			String rutaCompleta = ruta+rs.getString(2)+"-"+rs.getString(6).replace(" ","_")+"-"+"("+rs.getString(5).replace("/","-")+")"+rs.getString(4);
	            			File photo = new File(rutaCompleta);
								FileOutputStream fos = new FileOutputStream(photo);
							
					            byte[] buffer = new byte[1];
					            InputStream is = rs.getBinaryStream(3);
					            
					            while (is.read(buffer) > 0) {
					                fos.write(buffer);
					            }fos.close();

//-----------------------------abrir archivo Descargado
					           try{ 
					        	   Runtime.getRuntime().exec("cmd /c start "+rutaCompleta);
			            	   }catch(IOException e){
			            	      e.printStackTrace();
			            	   } 
					            
					            contadorDeArchivosGenerados++;
	            		}
//					}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		finally{
			if(stmt!=null){try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}}
		}
		return contadorDeArchivosGenerados;
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String[] Vector_De_Establecimientos_Edo_Resultados() throws SQLException{
		Statement stmt = null;
		
		String query = "select replace(establecimiento,' ','_')as establecimiento from IZAGAR_establecimientos_calculo";
		Vector miVector = new Vector();
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
				while(rs.next()){
					miVector.add(rs.getString("establecimiento"));
				}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		int i=0;
		String[] lista= new String[miVector.size()];
		
		while(i < miVector.size()){
			lista[i]= miVector.get(i).toString();
			i++;
		}
		return lista;
	}	
	
	public int Numero_De_Establecimientos_edo_Resultados(){
		Statement stmt = null;
		String query = "select count(establecimiento) from IZAGAR_establecimientos_calculo";
		int cantidad = 0;
		
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
				while(rs.next()){
					cantidad = rs.getInt(1);
				}
			
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return cantidad;
	}
	
	public Object[][] Reporte_De_Estado_de_Resultados(int cantidad_de_columnas, String fecha_inicial, String fecha_final) throws SQLException{
		Statement stmt = null;
		String query = "exec sp_reporte_de_estado_resultados '"+fecha_inicial+"','"+fecha_final+"'" ;
//		String query = " SELECT     TOP(20)   0,'xyz concepto', 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17 FROM  tb_empleado" ;
		Object[][] rp_competencia = new Object[getFilas(query)][cantidad_de_columnas];
		DecimalFormat df= new DecimalFormat("#0.00");
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			int i=0,valida=0 ;double total=0,VENTAS=0,UTILIDAD_BRUTA_$=0,UTILIDAD_BRUTA2_$=0,UTILIDAD_OPERATIVA=0,UTILIDAD_NETA=0;
			String CONCEPTO="";
			
			while(rs.next()){
				total=0;
				for(int j=0; j<(cantidad_de_columnas-1); j++){
					if(j<2){
						rp_competencia[i][j] = rs.getObject(j+1);
						if(rs.getInt(1)==0){
							valida=0;
						}else{
							valida=1;
						}
						CONCEPTO=rs.getString(2);
					}else{
					    rp_competencia[i][j] = df.format(rs.getDouble(j+1));
					    
					    if(valida==1){
					    	total+= rs.getDouble(j+1);
					    }
					    
                        if(CONCEPTO.equals("VENTAS")){
                        	VENTAS=total;
						}
                        
                        if(CONCEPTO.equals("UTILIDAD BRUTA $")){
                        	UTILIDAD_BRUTA_$=total;
						}
                        
                        if(CONCEPTO.equals("UTILIDAD BRUTA 2 $")){
                        	UTILIDAD_BRUTA2_$=total;
						}
                        
                        if(CONCEPTO.equals("UTILIDAD OPERATIVA")){
                        	UTILIDAD_OPERATIVA=total;
						}
                        
                        if(CONCEPTO.equals("UTILIDAD NETA")){
                        	UTILIDAD_NETA=total;
						}
					}
				}
				
				rp_competencia[i][cantidad_de_columnas-1] = df.format(total);
				
				if(CONCEPTO.equals("UTILIDAD BRUTA %")){
					rp_competencia[i][cantidad_de_columnas-1] = df.format((UTILIDAD_BRUTA_$/VENTAS)*100);
				}
				
				if(CONCEPTO.equals("UTILIDAD BRUTA 2 %")){
					rp_competencia[i][cantidad_de_columnas-1] = df.format((UTILIDAD_BRUTA2_$/VENTAS)*100);
				}
				
				if(CONCEPTO.equals("UTILIDAD OPERATIVA %")){
					rp_competencia[i][cantidad_de_columnas-1] = df.format((UTILIDAD_OPERATIVA/VENTAS)*100);
				}
				
				if(CONCEPTO.equals("UTILIDAD NETA %")){
					rp_competencia[i][cantidad_de_columnas-1] = df.format((UTILIDAD_NETA/VENTAS)*100);
				}
				
				i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al Buscar en el procedimiento sp_reporte_de_estado_resultados \nSQLServerException:"+e+" \n Parametros:"+query,"Avise Al Administrador del Sistema",JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			return null;
		}
		finally{
			if(stmt != null){stmt.close();}
		}
		return rp_competencia;
	}	
	
	public String  Factor(String Fecha){
		Statement stmt = null;
		String query = "declare  @existe varchar(100)"
				+ " set @existe=(SELECT 'Calculo ISR Sobre Venta Factor:'+ convert(varchar(10),factor)+' Tasa:'+convert(varchar(10),tasa) from  IZAGAR_factor_y_tasa_ISR  where CONVERT(VARCHAR(2),datepart(MONTH,'"+Fecha+"'))+CONVERT(VARCHAR(4),datepart(YEAR,'"+Fecha+"'))=mes_a�o)"
				+ " if @existe is null set @existe='Para El Calculo ISR Sobre Venta Falta Alimentar El Factor y Tasa'"
				+ " select @existe";
		String factor = "";
		
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
				while(rs.next()){
					factor = rs.getString(1);
				}
			
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al Buscar factor para ISR \nSQLServerException:"+e+" \n Parametros:"+query,"Avise Al Administrador del Sistema",JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			return null;
		}
		return factor;
	}
	
	public int  folio_de_ultimo_trabajo_de_corte(String concentrado, String fecha){
		int folio=0;
				
		String query = "exec sp_select_folio_trabajo_de_corte '"+concentrado+"','"+fecha+"'";
//		String query = "declare @folio_grupo int "
//				+ " set @folio_grupo = (select folio_grupo_para_cortes from tb_grupos_para_cortes where grupo_para_cortes = '"+concentrado+"' and status = 1) "
//				+ " select top 1 folio_trabajo_de_cortes from tb_alimentacion_de_cortes where folio_grupo_para_cortes = @folio_grupo order by fecha_de_trabajo desc";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				folio=(rs.getInt("folio_trabajo_de_cortes"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(stmt!=null){try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}}
		}
		return folio;
	}
	
	public String  Existe_Inventario_Guardado_Del_Establecimiento_En_La_Fecha(String Fecha, String Establecimiento){
		Statement stmt = null;
		String query = "sp_existe_inventario_guardado_del_establecimiento_en_la_fecha '"+Fecha+"','"+Establecimiento+"'";
		String factor = "";
		
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
				while(rs.next()){
					factor = rs.getString(1);
				}
			
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al Buscar Existe_Inventario_Guardado_Del_Establecimiento_En_La_Fecha \nSQLServerException:"+e+" \n Parametros:\n"+query,"Avise Al Administrador del Sistema",JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			return null;
		}
		return factor;
	}
	
	public String  Existe_establecimiento_inventario_fisico(String Establecimiento){
		Statement stmt = null;
		String query = "declare  @existe char(1) "
		               +"set @existe=(select 'S' from establecimientos where nombre = ltrim(rtrim('"+Establecimiento+"')) )"
		               +" if @existe is null set @existe='N'  select @existe ";		
		String existe = "";		
		try {
			stmt = con.conexion_IZAGAR().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
				while(rs.next()){
					existe = rs.getString(1);
				}
			
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al Buscar Existe_establecimiento_inventario_fisico \nSQLServerException:"+e+" \n Parametros:\n"+query,"Avise Al Administrador del Sistema",JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			return null;
		}
		return existe;
	}
	
	public Obj_Finiquitos finiquito_base(String folio_empleado_bms,int folio_empleado_scoi, String fecha_baja_scoi, String fecha_baja_bms){
		Obj_Finiquitos finiquito = new Obj_Finiquitos();
		
		String query = "exec sp_select_calculo_de_finiquito '"+folio_empleado_bms+"',"+folio_empleado_scoi+",'"+fecha_baja_scoi+"','SCOI'";
		System.out.println(query);
		Statement stmt= null;
						try {
							stmt= con.conexion().createStatement();
							ResultSet rs= stmt.executeQuery(query);
							
							
								   while(rs.next()){
									   
//									SCOI--------------------------------------------------------------------------------------------------------------------
									   finiquito.setFecha_ingreso_SCOI(rs.getString("fecha_ingreso"));
									   finiquito.setFecha_baja_SCOI(rs.getString("fecha_baja"));
									   
									   finiquito.setDias_trabajados_SCOI(rs.getInt("diferencia_dias"));
									   finiquito.setAnios_trabajados_SCOI(rs.getFloat("diferencia_anios"));
									   finiquito.setDias_pendientes_de_pago_de_aguinaldo_SCOI(rs.getInt("dias_pendientes_de_pago_de_aguinaldo"));
									   finiquito.setDias_pendientes_de_pago_de_semana_SCOI(rs.getInt("dias_pendiente_de_pago_en_la_semana"));
									   finiquito.setCuota_diario_SCOI(rs.getDouble("cuota_diaria"));
//									   SDI
									   finiquito.setSueldo_SCOI(rs.getDouble("sueldo"));
									   finiquito.setAguinaldo_SCOI(rs.getDouble("aguinaldo"));
//									   dias_correspondiente_vacaciones_actuales
//									   dias_trabajados_anio_actual
									   finiquito.setVacaciones_SCOI(rs.getDouble("vacaciones"));
									   finiquito.setPrima_vacacional_SCOI(rs.getDouble("prima_vacacional"));
									   finiquito.setPercepciones_SCOI(rs.getDouble("total_de_percepciones"));
									   
									   finiquito.setDias_correspondiente_vacaciones(rs.getInt("dias_correspondiente_vacaciones_actuales"));
								   }
						
					   if(!folio_empleado_bms.equals("")){
							query = "exec sp_select_calculo_de_finiquito '"+folio_empleado_bms.trim()+"',"+folio_empleado_scoi+",'"+fecha_baja_bms+"','BMS'";
							rs= stmt.executeQuery(query);
							
							   while(rs.next()){
								   
									   finiquito.setFecha_ingreso_BMS(rs.getString("fecha_ingreso"));
									   finiquito.setFecha_baja_BMS(rs.getString("fecha_baja"));
									   
									   finiquito.setDias_trabajados_BMS(rs.getInt("diferencia_dias"));
									   finiquito.setAnios_trabajados_BMS(rs.getFloat("diferencia_anios"));
									   finiquito.setDias_pendientes_de_pago_de_aguinaldo_BMS(rs.getInt("dias_pendientes_de_pago_de_aguinaldo"));
									   finiquito.setDias_pendientes_de_pago_de_semana_BMS(rs.getInt("dias_pendiente_de_pago_en_la_semana"));
									   finiquito.setCuota_diario_BMS(rs.getDouble("cuota_diaria"));
									   
									   finiquito.setSDI_BMS(rs.getDouble("SDI"));

									   finiquito.setSueldo_BMS(rs.getDouble("sueldo"));
									   finiquito.setAguinaldo_BMS(rs.getDouble("aguinaldo"));
//									   dias_correspondiente_vacaciones_actuales
//									   dias_trabajados_anio_actual
									   finiquito.setVacaciones_BMS(rs.getDouble("vacaciones"));
									   finiquito.setPrima_vacacional_BMS(rs.getDouble("prima_vacacional"));
									   finiquito.setPercepciones_BMS(rs.getDouble("total_de_percepciones"));
									   
							   }
						}
						
						query = "exec sp_select_deducciones_para_finiquitos "+folio_empleado_scoi;
						rs= stmt.executeQuery(query);
						
						   while(rs.next()){
							   finiquito.setPretamo(rs.getDouble("prestamo"));
							   finiquito.setCortes(rs.getDouble("cortes"));
							   finiquito.setInfonavit(rs.getDouble("infonavit"));
							   finiquito.setFuente_sodas(rs.getDouble("fuente_de_sodas"));
								   
						   }
					
						} catch (Exception e) {
							JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion [finiquito_base] \n SQLException: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
							e.printStackTrace();
							return null;
						}
		finally{
			if(stmt!=null){try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}}
		}
		return finiquito;
	}
	
	public boolean  baja_en_catalogo_empleado(){
		
		boolean baja = false;
		
		String query = "select baja_en_catalogo_empleados as baja  from tb_configuracion_sistema";
		
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				baja=(Boolean.valueOf(rs.getString("baja")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(stmt!=null){try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}}
		}
		return baja;
	}
	
	public boolean validar_cambio_de_sueldo_o_bono(String folio_empleado){
		
		boolean existe = false;
		
		String query = " declare @return varchar(80)  set @return =(select top 1 'true' from tb_historico_sueldos_empleados where folio_empleado="+folio_empleado+" and status='N')"
				+ "  if @return is null set @return='false'  select @return";
		
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				existe=(Boolean.valueOf(rs.getString(1)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(stmt!=null){try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}}
		}
		return existe;
	}
	
	
	public Obj_Venta_De_Cascos_A_Proveedores venta_de_casco(String folio) throws SQLException{
		Obj_Venta_De_Cascos_A_Proveedores proveedores = new Obj_Venta_De_Cascos_A_Proveedores();
		String query = "exec sp_select_ultima_venta_de_cascos '"+folio+"'";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
		    ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				proveedores.setCod_prv(rs.getString("cod_prv"));
				proveedores.setNombre_proveedor(rs.getString("proveedor").trim());
				proveedores.setNombre_proveedor_recibe(rs.getString("beneficiario_proveedor").trim());
				proveedores.setTotal(rs.getString("total")); 
				proveedores.setExiste(rs.getBoolean("existe"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error");
			JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion [ Pagos_cascos ] SQLException: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		finally{
			 if (stmt != null) { stmt.close(); }
		}
		return proveedores;
	}
	
	public boolean  existe_finiquito_vigente(int folio_finiquito, String catalogoDependiente){
		boolean existe = false;
		
		String query = "";
		
		if(catalogoDependiente.equals("FINIQUITO_NORMAL")){
			query = "select top 1 case when status='V' then 'true' "
				+ "					 		when status='N' then 'true' "
				+ "						else 'false' end as finiquito_vigente "
				+ "		from tb_finiquitos "
				+ "		where folio_empleado_scoi = "+folio_finiquito
				+ "		order by fecha_mov desc";
		}else{
			query = "select top 1 case when status='V' then 'true' "
					+ "						else 'false' end as finiquito_vigente "
					+ "		from tb_finiquitos "
					+ "		where folio_empleado_scoi = "+folio_finiquito
					+ "		order by fecha_mov desc";
		}
		
		 
		
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				existe=(Boolean.valueOf(rs.getString("finiquito_vigente")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(stmt!=null){try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}}
		}
		return existe;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Date[] fecha_actual_y_fecha_aguinado() throws SQLException{
		Statement stmt = null;
		
		String query = "select convert(varchar(20),GETDATE(),103) as fecha_actual "
				+ "		, convert(varchar(20),fecha_ultimo_aguinaldo,103) as fecha_aguinaldo "
				+ "		, ('01/01/'+convert(varchar(5),datepart(year,GETDATE())) ) as fecha_inicio_anio_actual "
				+ "		, ('01/01/'+convert(varchar(5),datepart(year,dateadd(year,1,GETDATE()))) ) as fecha_inicio_anio_siguiente "
				+ " 	from tb_configuracion_sistema";
		Vector miVector = new Vector();
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
				while(rs.next()){
					miVector.add(new SimpleDateFormat("dd/MM/yyyy").parse(rs.getString("fecha_actual")));
					miVector.add(new SimpleDateFormat("dd/MM/yyyy").parse(rs.getString("fecha_aguinaldo")));
					miVector.add(new SimpleDateFormat("dd/MM/yyyy").parse(rs.getString("fecha_inicio_anio_actual")));
					miVector.add(new SimpleDateFormat("dd/MM/yyyy").parse(rs.getString("fecha_inicio_anio_siguiente")));
				}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		int i=0;
		Date[] lista= new Date[miVector.size()];
		
		while(i < miVector.size()){
			lista[i]= (Date) miVector.get(i);
			i++;
		}
		return lista;
	}	
	
	public String[][] getProductosEnEstablecimiento(String cod_prod){
		String[][] Matriz = null;
		
		String query = "select prodestab.cod_prod "
				+ "				,productos.descripcion "
				+ "				,case when (prodestab.status_producto)=1 "
				+ "						then 'VIGENTE' "
				+ "						else 'CANCELADO' end status "
				+ "			from prodestab WITH (NOLOCK) "
				+ " 		inner join productos on productos.cod_prod = prodestab.cod_prod "
				+ " 		where prodestab.cod_estab = 15 ";
		
		query = cod_prod.equals("")? query : query+" and ltrim(rtrim(prodestab.cod_prod)) = '"+cod_prod.trim()+"'";
		
		Matriz = new String[getFilasExterno(query)][3];
		Statement s;
		ResultSet rs;
		try {			
			s = con.conexion_IZAGAR().createStatement();
			rs = s.executeQuery(query);
			int i=0;
			while(rs.next()){
				Matriz[i][0] = rs.getString(1);
				Matriz[i][1] = rs.getString(2);
				Matriz[i][2] = rs.getString(3);
				
				i++;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		return Matriz;
	}
	
	public boolean existeProductosEnEstablecimiento(String cod_prod){
		boolean existe = false;
		String query = "if exists (select prodestab.cod_prod "
				+ "				,productos.descripcion "
				+ "				,case when (prodestab.status_producto)=1 "
				+ "						then 'VIGENTE' "
				+ "						else 'CANCELADO' end status "
				+ "			from prodestab WITH (NOLOCK) "
				+ " 		inner join productos on productos.cod_prod = prodestab.cod_prod "
				+ " 		where prodestab.cod_estab = 15 "
				+ " 		and ltrim(rtrim(prodestab.cod_prod)) = '"+cod_prod.trim()+"') "
				+ " 		begin select 'true' as existe end else begin select 'false' as existe end ";
		
		Statement s;
		ResultSet rs;
		try {			
			s = con.conexion_IZAGAR().createStatement();
			rs = s.executeQuery(query);
			
			while(rs.next()){
				existe = rs.getBoolean(1);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		return existe;
	}
	
	public Obj_Indicadores indicador(String nombre_indicador) throws SQLException{
		Obj_Indicadores indicador = new Obj_Indicadores();
		String query = "select reporte, procedimiento_almacenado from tb_indicadores where indicador='"+nombre_indicador+"'";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
		    ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				indicador.setReporte(rs.getString("reporte"));
				indicador.setProcedimiento_almacenado(rs.getString("procedimiento_almacenado"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error");
			JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion [ indicador ] SQLException: "+e.getMessage(), "Avisa Al Administrador Del Sistema", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		finally{
			 if (stmt != null) { stmt.close(); }
		}
		return indicador;
	}
	
	public boolean validar_si_tiene_bono_de_asistencia_y_puntualidad(int folio_empleado,String tipo_de_bono){
		boolean existe = false;
		
		String query = "select case when ("+tipo_de_bono+")>0 then 'true' else 'false' end as bono"
						+" from tb_empleado where folio = "+folio_empleado;

		Statement s;
		ResultSet rs;
		try {			
			s = con.conexion().createStatement();
			rs = s.executeQuery(query);
			
			while(rs.next()){
				existe = rs.getBoolean(1);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		return existe;
	}
	
	public Object[][] Consultar_Transferencia(String folioTranferencia){
		
	String query = " exec sp_IZAGAR_checar_si_llego_completa_la_tranferencia '"+folioTranferencia+"'";
	
	Object[][] rp_ventas = new Object[1][6];
	
		try {
			Statement stmt = con.conexion_IZAGAR().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			int i=0;
				while(rs.next()){
						rp_ventas[i][0 ]= rs.getInt(1 );
						rp_ventas[i][1 ]= rs.getString(2 );
						rp_ventas[i][2 ]= rs.getInt(3 );
						rp_ventas[i][3 ]= rs.getString(4 );
						rp_ventas[i][4 ]= rs.getInt(5 );
						rp_ventas[i][5 ]= rs.getInt(6 );
					i++;
				}
			
		} catch (Exception e){
			JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion Consultar_Transferencia \n "+query+"\nSQLException:"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			e.printStackTrace();
			return null;
		}
		return rp_ventas;
	}
	
	public String[]  parametros_de_anios(){
		
		int cantidad_de_anios = 10;
		
		String[] anios = new String[cantidad_de_anios];
		int anio=0;
		String query = "select datepart(year,getdate())";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				anio=(rs.getInt(1));
			}
			
			for(int i=anio; i<(anio+cantidad_de_anios); i++){
				anios[i-anio] = i+"";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(stmt!=null){try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}}
		}
		return anios;
	}
	
	public String  buscar_folio_consecutivo_por_folio_de_transaccion(int folio_transaccion){
		String folio = "";
		String query = "select (folio+1) as folio_consecutivo from tb_folios where folio_transaccion = "+folio_transaccion;
		System.out.println(query);
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				folio=(rs.getString("folio_consecutivo"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(stmt!=null){try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}}
		}
		return folio;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Vector buscarTipoDeReporteDeMetaMensualDeVentas(){
		Statement stmt = null;
		
		String query = " select nombre from tb_tipo_de_reporte_de_meta__mensual_de_venta where status = 'V' ";
	
		Vector datos = new Vector();
		
		try {
			
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			int i =0;
			while(rs.next()){
				datos.add(rs.getString(i+1).toString());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al Buscar en [buscarTipoDeReporteDeMetaMensualDeVentas] \nSQLServerException:"+e,"Avise Al Administrador del Sistema",JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			
			return null;
		}
		finally{
			if(stmt != null){
				try {
					stmt.close();
				} catch (SQLException e){
					e.printStackTrace();
				}
			}
		}
		return datos;
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
	
	public boolean  existe_nombre_de_configuracion_de_meta_mensual_de_venta(String nombre){
		boolean existe = false;
		String query = " if not exists(select folio from tb_configuracion_de_meta_mensual_de_ventas where ltrim(rtrim(nombre)) = '"+nombre+"') "
						+ " begin select 'true' as existe end "
						+ " else "
						+ " begin select 'false' as existe end ";
		System.out.println(query);
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				existe=(rs.getBoolean("existe"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(stmt!=null){try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}}
		}
		return existe;
	}
	
	public String[]  empleado_con_finiquito_negado(String folio_finiquito){
		String[] emp = new String[4];
		String query = " select tb_finiquitos.folio_empleado_scoi "
				+ "		,tb_empleado.nombre+' '+tb_empleado.ap_paterno+' '+tb_empleado.ap_materno as empleado "
				+ "		,tb_establecimiento.nombre as establecimiento "
				+ "		,tb_finiquitos.folio_empleado_bms "
				+ " from tb_finiquitos "
				+ " inner join tb_empleado on tb_empleado.folio = tb_finiquitos.folio_empleado_scoi "
				+ " inner join tb_establecimiento on tb_establecimiento.folio = tb_finiquitos.establecimiento "
				+ " where folio_finiquito = "+folio_finiquito; 
		System.out.println(query);
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				emp[0]=rs.getString(1);//folio empleado scoi
				emp[1]=rs.getString(2);//nombre
				emp[2]=rs.getString(3);//establecimiento
				emp[3]=rs.getString(4);//folio empleado bms
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(stmt!=null){try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}}
		}
		return emp;
	}
	
	public String busca_metas_periodo(int anio,int mes,String cod_est) throws SQLException{
		String Descripcion="";
		String query = 
				"declare @thiSql char(2), @establ varchar(20) set @establ=(select  top 1 cod_estab from ventas where establecimiento='"+cod_est+"')"
				+" set @thiSql =(select top 1 'si' from metas_mensuales_por_establecimiento where a�o="+anio+" and cod_estab=@establ and mes="+mes+")"
				+" if(@thiSql is null) set @thiSql='no'" 
				+" select @thiSql  as resultado";

	
		Statement stmt = null;
	
		try {
			stmt = con.conexion_ventas().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				Descripcion=(rs.getString(1));
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(stmt!=null){
				
				try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			}
		}
		
		return Descripcion;
		
		
	
	}
	//new
	public String busca_metas_a_generar(int anio,int mes, int cod_meta,String est) throws SQLException{
		String Descripcion="";
		String query = 
				"declare @thiSql char(2), @establ int set @establ= (select  top 1 cod_estab from establecimientos where establecimiento='"+est+"') "
				+"set @thiSql =(select top 1 'si' from tabla_de_venta_por_mes_de_un_a�o_por_un_clasificador_de_meta_y_establecimiento ("+anio+"-1,"+mes+","+cod_meta+",@establ)) "
			    +"if(@thiSql is null) set @thiSql='no' " 
			    +"select @thiSql  as resultado";
		Statement stmt = null;
		try {
			stmt = con.conexion_ventas().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				Descripcion=(rs.getString(1));
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(stmt!=null){
				
				try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			}
		}
		
		return Descripcion;
		
		
	
	}
	public String Folio_Siguiente() throws SQLException{
		String folio = "";
		String query = "select folio+1 from tb_folios where folio_transaccion=31 ";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				folio=(rs.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(stmt!=null){try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}}
		}
		return folio;
	
	}
	public String Buscar_Descripcion(int folio) throws SQLException{
		String Descripcion="";
		String query = "declare @valor varchar(300) set @valor=(select descripcion from tb_matrices where folio_matriz="+folio+")"
				+ " if @valor is null set @valor='No Se Encontro Registro Con Este folio' select @valor ";
		
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				Descripcion=(rs.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(stmt!=null){try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			}
		}
		
		return Descripcion;
		
		
	
	}
	
	public String Folio_Siguiente_alta_Servicios() throws SQLException{
		String folio = "";
		String query = "select folio+1 from tb_folios where folio_transaccion=32 ";
		Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				folio=(rs.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(stmt!=null){try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}}
		}
		return folio;
	
	}

public String Buscar_Servicios_establecimientos_NumCtrol(String folio) throws SQLException{
		String Descripcion="";
		String query = "declare @thiSql char(2), @folio varchar(50) "
				       +" set @folio=(select top 1 folio from tb_servicios_de_establecimientos where folio='"+folio+"') "
				       +"if @folio is null begin set @folio=(select top 1 folio from tb_servicios_de_establecimientos where numeroControl='"+folio+"') end "
				       +"set @thiSql =(select top 1 'si' from tb_servicios_de_establecimientos where folio=@folio)"
					   +"if(@thiSql is null) set @thiSql='no' "
					   +" select @thiSql  as resultado "; 

		 Statement stmt = null;
		try {
			stmt = con.conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				Descripcion=(rs.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(stmt!=null){try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			}
		}
		
		return Descripcion;
		
		
	
	}

public boolean  existe_horario_con_otro_empleado(int horario1, int horario2, int horario3, int folio_empleado){
	boolean existe = false;
   String query = "exec sp_validacion_de_horarios_en_el_empleado "+horario1+","+horario2+","+horario3+","+folio_empleado;
	
	Statement stmt = null;
	try {
		stmt = con.conexion().createStatement();
		ResultSet rs = stmt.executeQuery(query);
		while(rs.next()){
			existe=(Boolean.valueOf(rs.getString("bolean")));
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	finally{
		if(stmt!=null){try {
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}}
	}
	return existe;
}

public String[] getTablaRangoDeEdadesParaPerfiles(){
	String[] Matriz = null;
	
	String datosif = "select convert(varchar(5),edad_minima)+' - '+convert(varchar(5),edad_maxima) as rango "
			+ " from tb_edades_requeridas_para_perfiles_de_puesto "
			+ " where status = 'V'";
	
	Matriz = new String[getFilas(datosif)];
	Statement s;
	ResultSet rs;
	try {			
		s = con.conexion().createStatement();
		rs = s.executeQuery(datosif);
		int i=0;
		while(rs.next()){
			Matriz[i] = rs.getString(1);
			i++;
		}
	} catch (SQLException e1) {
		e1.printStackTrace();
	}
	
	return Matriz;
}

public Obj_Perfil_De_Puestos Perfil_De_Puesto(int folio) throws SQLException{
	Obj_Perfil_De_Puestos empleado = new Obj_Perfil_De_Puestos();
	String query = "exec sp_select_perfil_de_puesto "+ folio;
	Statement stmt = null;

	try {
		stmt = con.conexion().createStatement();
		ResultSet rs = stmt.executeQuery(query);

		while(rs.next()){
			
			empleado.setFolio(rs.getInt("folio"));                                                 
			empleado.setPerfil(rs.getString("perfil").trim());                                     
			empleado.setEdad(rs.getString("edad"));                                                
			empleado.setSexo(rs.getString("sexo"));                                                
			empleado.setPuesto_al_que_reporta(rs.getString("reporta_a"));                          
			                                                                                       
			empleado.setEstablecimiento(rs.getString("establecimiento"));                          
			empleado.setDepartameto(rs.getString("departamento"));                                 
			empleado.setPuesto(rs.getString("puesto"));                                            
			empleado.setNivel_de_puesto(rs.getString("nivel_de_puesto"));                                         
			                                                                                             
			empleado.setHorario(rs.getInt("folio_horario1"));                                                   
			empleado.setHorario2(rs.getInt("folio_horario2"));                                           
			empleado.setHorario3(rs.getInt("folio_horario3"));                                           
			                                                                                             
			empleado.setHorarioNombre(rs.getString("horario1"));                                         
			empleado.setHorario2Nombre(rs.getString("horario2"));                                        
			empleado.setHorario3Nombre(rs.getString("horario3"));			                             
			                                                                                             
			empleado.setStatus_h1(rs.getInt("status_h1"));                                               
			empleado.setStatus_h2(rs.getInt("status_h2"));                                               
			empleado.setStatus_h3(rs.getInt("status_h3"));                                               
			empleado.setStatus_rotativo(rs.getInt("horario_rotativo"));                                  
			                                                                                             
			empleado.setDescanso(rs.getString("descanso"));                                              
			empleado.setDobla(rs.getString("dobla"));             
			
			empleado.setSalario_diario(rs.getFloat("salario_diario"));                     
			empleado.setSalario_diario_integrado(rs.getFloat("salario_diario_integrado")); 
			empleado.setSueldo(rs.getFloat("sueldo"));				      
			empleado.setBonocomplemento(rs.getFloat("bono_complementario"));               
			empleado.setBono_asistencia(rs.getFloat("bono_asistencia"));  
			empleado.setBono_puntualidad(rs.getFloat("bono_puntualidad"));
			empleado.setPrestamo(rs.getInt("rango_prestamo"));
			empleado.setGafete(rs.getBoolean("gafete") ? true : false); 

			empleado.setObjetivo_del_puesto(rs.getString("objetivo_de_puesto"));				
			empleado.setActividades_Principales(rs.getString("actividades_principales"));
			empleado.setConocimiento(rs.getString("conocimientos"));
			empleado.setExperiencia(rs.getString("experiencia"));
			empleado.setHabilidades(rs.getString("habilidades"));				

		}
		
	} catch (Exception e) {
		e.printStackTrace();
		return null;
	}
	finally{
		if(stmt!=null){stmt.close();}
	}
	return empleado;
}

public String folio_Periodo_nuevo() throws SQLException{
	
	String folio = "";
//	String query = buscar_folio_consecutivo_por_folio_de_transaccion(33);
	Statement stmt = null;
	try {
//		stmt = con.conexion().createStatement();
//		ResultSet rs = stmt.executeQuery(query);
//		while(rs.next()){
			System.out.println(buscar_folio_consecutivo_por_folio_de_transaccion(34).trim());
			folio = buscar_folio_consecutivo_por_folio_de_transaccion(34);
//		}
		
	} catch (Exception e) {
		e.printStackTrace();
		return null;
	}
	finally{
		if(stmt!=null){stmt.close();}
	}
	return folio;
}

public int  anios_de_vacaciones(String fechaInicial, String fecha_final){
	int anios = 0;
   String query = "select dias_corresponden from tb_tabla_vacaciones_rangos where tb_tabla_vacaciones_rangos.a�os_trabajados =	convert (int,convert (numeric(10,2),(convert(float,DATEDIFF(day,convert(datetime,('"+fechaInicial+"')),dateadd(day,1,'"+fecha_final+"') ))/365))) ";
	
	Statement stmt = null;
	try {
		stmt = con.conexion().createStatement();
		ResultSet rs = stmt.executeQuery(query);
		while(rs.next()){
			anios=rs.getInt("dias_corresponden");
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	finally{
		if(stmt!=null){try {
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}}
	}
	return anios;
}

public boolean nombre_de_perfil_disponible(String nombre){
	String query = "exec sp_existe_perfil '" + nombre + "';";
	boolean disponible = false;
	try {				
		Statement s = con.conexion().createStatement();
		ResultSet rs = s.executeQuery(query);
		
		while(rs.next()){
			disponible = rs.getBoolean(1);
		}
		
	} catch (SQLException e1) {
		e1.printStackTrace();
	}
		
	return disponible;
}

public Obj_Gestion_De_Pedidos_A_Establecimientos datosDePedido(String folio_pedido) throws SQLException{
	Obj_Gestion_De_Pedidos_A_Establecimientos pedido = new Obj_Gestion_De_Pedidos_A_Establecimientos();
	String query = "exec sp_select_datos_de_gestion_de_pedido '"+folio_pedido+"'";
	
	Statement stmt = null;
	try {
		stmt = con.conexion().createStatement();
	    ResultSet rs = stmt.executeQuery(query);
		while(rs.next()){
			pedido.setFolio_pedido(rs.getString("folio_pedido"));
			pedido.setOrigen(rs.getString("establecimiento"));
			pedido.setDestino(rs.getString("establecimiento_alterno"));
			pedido.setClasificador(rs.getString("clasificador"));
			pedido.setUsuario(rs.getString("usuario")); 
			pedido.setStatus_pedido(rs.getString("status_pedido"));
		}
		
	} catch (Exception e) {
		e.printStackTrace();
		System.err.println("Error");
		JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion [ datosDePedido ] SQLException: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		return null;
	}
	finally{
		 if (stmt != null) { stmt.close(); }
	}
	return pedido;
}

public String existeEnScoi(String folio_pedido){
	String mov = "";
	String query = "IF exists(select '1' from tb_gestion_de_pedido WHERE folio_pedido = '"+folio_pedido+"') "
			+ " 		SELECT 'MODIFICAR' AS movimiento"
			+ "		ELSE"
			+ "			SELECT 'GUARDAR' AS movimiento";
	
	Statement stmt = null;
	try {
		stmt = con.conexion().createStatement();
	    ResultSet rs = stmt.executeQuery(query);
		while(rs.next()){
			mov = rs.getString("movimiento"); 
		}
		
	} catch (Exception e) {
		e.printStackTrace();
		System.err.println("Error");
		JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion [ existeEnScoi ] SQLException: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		return null;
	}
	finally{
		 if (stmt != null) { try {
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} }
	}
	return mov;
}

public boolean existenPedidosActivos(){
	
	boolean mov = false;
	
	String query = "IF exists(select '1' from tb_gestion_de_pedido WHERE status in ('V','A')) "
			+ "	SELECT 'false' as activar_inventario "
			+ " ELSE "
			+ "	SELECT 'true' as activar_inventario";
	
	Statement stmt = null;
	try {
		stmt = con.conexion().createStatement();
	    ResultSet rs = stmt.executeQuery(query);
		while(rs.next()){
			mov = rs.getBoolean("activar_inventario"); 
		}
		
	} catch (Exception e) {
		e.printStackTrace();
		System.err.println("Error");
		JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion [ existeEnScoi ] SQLException: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
	}
	finally{
		 if (stmt != null) { try {
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} }
	}
	return mov;
}

public boolean existeInventarioElDiaActual(){
	
	boolean existe = false;
	
	String query = "select count(cod_prod) as existe_inventario "
					+ " from tb_inventario_de_gestion_de_pedidos "
					+ " where convert(varchar(20),fecha,103) = convert(varchar(20),GETDATE(),103)";
	
	Statement stmt = null;
	try {
		stmt = con.conexion().createStatement();
	    ResultSet rs = stmt.executeQuery(query);
		while(rs.next()){
			existe = rs.getInt("existe_inventario")==0?false:true; 
		}
		
	} catch (Exception e) {
		e.printStackTrace();
		System.err.println("Error");
		JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion [ existeInventarioElDiaActual ] SQLException: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
	}
	finally{
		 if (stmt != null) { try {
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} }
	}
	return existe;
}

public boolean existenPedidosPendientesPorSurtir(){
	
	boolean existe = false;
	
	String query = "exec sp_select_existen_pedidos_pendientes_por_surtir";
	
	Statement stmt = null;
	try {
		stmt = con.conexion().createStatement();
	    ResultSet rs = stmt.executeQuery(query);
		while(rs.next()){
			existe = rs.getBoolean("existe_pedido_pendiente"); 
		}
		
	} catch (Exception e) {
		e.printStackTrace();
		System.err.println("Error");
		JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion [ existenPedidosPendientesPorSurtir ] SQLException: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
	}
	finally{
		 if (stmt != null) { try {
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} }
	}
	return existe;
}

public int  dias_de_vacaciones_pendientes_del_ultimo_anio(String fecha_in,String fecha_fin) throws SQLException{
	int dias=0;
	String query = "exec sp_select_dias_de_vacaciones_pendientes '"+fecha_in+"','"+fecha_fin+"'";
	Statement stmt = null;
	try {
		stmt = con.conexion().createStatement();
		ResultSet rs = stmt.executeQuery(query);
		while(rs.next()){
			dias=(rs.getInt("dias_pendientes"));
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	finally{
		if(stmt!=null){stmt.close();}
	}
	return dias;
}

public String Folio_Siguiente_alimentacion_inventarios_parciales() throws SQLException{
	String folio = "";
	String query = "select folio+1 from tb_folios where folio_transaccion=70 ";
	Statement stmt = null;
	try {
		stmt = con.conexion().createStatement();
		ResultSet rs = stmt.executeQuery(query);
		while(rs.next()){
			folio=(rs.getString(1));
		}
	} catch (Exception e) {
		e.printStackTrace();
		JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion [ Folio_Siguiente_alta_Servicios ] SQLException: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE, new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
	}
	finally{
		if(stmt!=null){try {
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}}
	}
	return folio;

}

public Obj_Alimentacion_De_Inventarios_Parciales datos_producto_existencia(String cod_prod, String Establecimiento) throws SQLException{
	Obj_Alimentacion_De_Inventarios_Parciales datosproducto = new Obj_Alimentacion_De_Inventarios_Parciales();
	
	String query = " declare @exist_cedis real,@cod_prod varchar(35),@Producto varchar(35),@cod_estab char(3)  set @Producto='"+cod_prod+"' "
			+ "               set @cod_estab=(select  cod_estab from establecimientos where RTRIM(LTRIM(nombre))='"+Establecimiento+"') "
			+ "				  set @cod_prod= (select top 1 cod_prod from productos with (nolock) where (cod_prod = @Producto)) "
			+ "				  if @cod_prod is null begin set @cod_prod= (select top 1 cod_prod from productos with (nolock) where (codigo_barras_pieza = @Producto)) end "
			+ "				  if @cod_prod is null begin set @cod_prod= (select top 1 cod_prod from productos with (nolock) where (codigo_barras_unidad = @Producto)) end "
			+ "				  if @cod_prod is null begin set @cod_prod=(select top 1 cod_prod from codigos_barras_adicionales_productos A with (nolock) where (codigo_barras_unidad=@Producto)) end "
			+ "				  if @cod_prod is null begin set @cod_prod=(select top 1 cod_prod from codigos_barras_adicionales_productos A with (nolock) where (codigo_barras_pieza=@Producto))end "
			+ "			 SELECT   productos.cod_prod"
			+ "					 ,productos.descripcion"
			+ " 		         ,convert(numeric(10,2),isnull(sum(case when (productos.contenido)<>1 then((productos.contenido*prodestab.exist_unidades)+exist_piezas) else (prodestab.exist_piezas) end),0)) as existencia"
			+ "				     ,ISNULL(convert(numeric(10,2),prodestab.ultimo_costo),0) as ultimo_costo "
			+ "				     ,ISNULL(convert(numeric(10,2),prodestab.costo_promedio),0) as costo_promedio "
			+ "  		  from productos with (nolock)"
			+ "  	  	    left outer join prodestab with (nolock) on prodestab.cod_prod=productos.cod_prod and prodestab.cod_estab=@cod_estab"
			+ "			  where productos.cod_prod=@cod_prod "
			+ "						  group by  productos.cod_prod,productos.descripcion,prodestab.ultimo_costo,prodestab.costo_promedio ";	
	
	Statement stmt2= null;
					try {
						stmt2= con.conexion_IZAGAR().createStatement();
						ResultSet rs2= stmt2.executeQuery(query);
							   while(rs2.next()){
								   datosproducto.setCod_Prod        (rs2.getString("cod_prod"));
								   datosproducto.setDescripcion_Prod(rs2.getString("descripcion"));
								   datosproducto.setExistencia      (rs2.getDouble("existencia"));
								   datosproducto.setUltimo_Costo    (rs2.getDouble("ultimo_costo"));
								   datosproducto.setCosto_Promedio  (rs2.getDouble("costo_promedio"));
							   }
						
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion datos_producto_existencia \n SQLException: "+e.getMessage(),"Avise Al Adiministrador",JOptionPane.ERROR_MESSAGE, new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
						e.printStackTrace();
						return null;
					}

	finally{
		if(stmt2!=null){stmt2.close();}
	}
	return datosproducto;
}

	public boolean validacion_usuario_trasferencia_de_embarque(String clavecajero){
		String query = "exec sp_validar_opcion_para_transferencia_de_embarque '" + clavecajero+"'";
		
		boolean disponible = false;
		try { Statement s = con.conexion().createStatement();
			  ResultSet rs = s.executeQuery(query);
			while(rs.next()){
			    	disponible = rs.getBoolean(1);
			      }
			
		} catch (SQLException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en BuscarSQL  en la funcion validacion_cajero_fuente_sodas \n  en el procedimiento : sp_validar_cajero  \n SQLException: "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
		return disponible;
	}
	
	public String[][] getTransferenciasPendientes(String estab){
		String query = "declare @establecimiento varchar(60) "
					+ " set @establecimiento = '"+estab+"' "
					+ " select mi.folio, "
					+ "		ltrim(rtrim(estabSurt.nombre)) as surte, "
					+ "		ltrim(rtrim(estabRecibe.nombre)) as recibe, "
					+ "		case when mi.status = 'V' "
					+ "				then 'VIGENTE' "
					+ "				when mi.status = 'C' "
					+ "					then 'CANCELADO' "
					+ "		else 'INDEFINIDO' end as status, "
					+ "		case when mi.status = 'V' and mi.status_recepcion = '' "
					+ "					then 'PENDIENTE' "
					+ "				when mi.status_recepcion = 'T' "
					+ "					then 'TRANSFERIDO' "
					+ "				else 'INDEFINIDO' "
					+ "		end as status_recepcion, "
					+ "		isnull(convert(varchar(20),mi.fecha_cancelacion,103)+' '+convert(varchar(20),mi.fecha_cancelacion,108),'') as fecha_cancelacion, "
					+ "		isnull(ltrim(rtrim(usuarios.nombre)),'') as usuario_cancelacion "
					+ " from movimientos_internos mi with (nolock) "
					+ " left outer join (select folio,folio_referencia from movimientos_internos with (nolock) where transaccion = '65') r on r.folio_referencia = mi.folio_referencia "
					+ " inner join establecimientos estabsurt on estabsurt.cod_estab = mi.cod_estab and estabSurt.nombre = @establecimiento "
					+ " inner join establecimientos estabRecibe on estabRecibe.cod_estab = mi.cod_estab_alterno "
					+ " left outer join usuarios on usuarios.usuario = mi.usuario_cancelacion "
					+ " where mi.fecha > convert(varchar(20),getdate(),103) "
					+ " and mi.transaccion = '35' "
					+ " GROUP BY mi.folio,estabSurt.nombre,estabRecibe.nombre,mi.status,mi.status_recepcion, "
					+ " 		mi.surtido,usuarios.nombre,mi.fecha_cancelacion,mi.transaccion_referencia";
		
		String[][] Matriz = new String[getFilasExterno(query)][7];
		Statement s;
		ResultSet rs;
		try {			
			s = con.conexion_IZAGAR().createStatement();
			rs = s.executeQuery(query);
			int i=0;
			while(rs.next()){
				Matriz[i][0] = rs.getString(1);
				Matriz[i][1] = rs.getString(2);
				Matriz[i][2] = rs.getString(3);
				Matriz[i][3] = rs.getString(4);
				Matriz[i][4] = rs.getString(5);
				Matriz[i][5] = rs.getString(6);
				Matriz[i][6] = rs.getString(7);
				i++;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return Matriz; 
	}

	public boolean chofer_ocupado(int folio_chofer){
		String query = "if exists(select folio_transferencia from tb_historial_de_trasporte_de_embarque where folio_chofer = "+folio_chofer+" and status = 'V' and convert(varchar(20),fecha_salida,103) = convert(varchar(20),getdate(),103) )"
						+ " begin "
						+ " 	select 'true' as existe_chofer_activo "
						+ " end "
						+ " else "
						+ " begin "
						+ " 	select 'false' as existe_chofer_activo "
						+ " end";
		boolean existe = false;
		try {
			Statement stmt = new Connexion().conexion().createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			while(rs.next()){ existe = rs.getBoolean("existe_chofer_activo"); }
	
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	    return existe; 
	}
	
	public Object[][] getLlegadaTransferencias(int chofer, String establecimiento){
		String query = "select hist_emb.folio_transferencia, "
				+ " 		estab_sur.nombre as estab_surte, "
				+ " 		estab_rec.nombre as estab_recibe"
				+ " from tb_historial_de_trasporte_de_embarque  hist_emb "
				+ " inner join [192.168.2.201].BMSIZAGAR.dbo.establecimientos estab_sur on estab_sur.cod_estab = hist_emb.estab_surte "
				+ " inner join [192.168.2.201].BMSIZAGAR.dbo.establecimientos estab_rec on estab_rec.cod_estab = hist_emb.estab_recibe "
				+ " where folio_chofer = "+chofer+" "
				+ " and hist_emb.status = 'V' "
				+ " and hist_emb.estab_recibe = (select ltrim(rtrim(estab.cod_estab)) from [192.168.2.201].BMSIZAGAR.dbo.establecimientos estab where ltrim(rtrim(estab.nombre)) = ltrim(rtrim('"+establecimiento+"')) ) "
				+ " and CONVERT(varchar(20),hist_emb.fecha_salida,103) = CONVERT(varchar(20),GETDATE()-4,103)";
		
		Object[][] Matriz = new Object[getFilas(query)][4];
		Statement s;
		ResultSet rs;
		try {			
			s = con.conexion().createStatement();
			rs = s.executeQuery(query);
			int i=0;
			while(rs.next()){
				Matriz[i][0] = rs.getString(1);
				Matriz[i][1] = rs.getString(2);
				Matriz[i][2] = rs.getString(3);
				Matriz[i][3] = false;
				i++;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return Matriz; 
	}
	
//	public int  Folios_generados(String folio_original){
//		int numero_de_folios=0;
//		String query = "select count(distinct(folio_pedido))-1 as folios_generados from tb_gestion_de_pedido where folio_pedido like '%"+folio_original+"'";
//		Statement stmt = null;
//		try {
//			stmt = con.conexion().createStatement();
//			ResultSet rs = stmt.executeQuery(query);
//			while(rs.next()){
//				numero_de_folios=(rs.getInt("folios_generados"));
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		finally{
//			if(stmt!=null){
//				try {
//					stmt.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		return numero_de_folios;
//	}

}
