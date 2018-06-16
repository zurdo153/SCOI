package Conexiones_SQL;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import Obj_Administracion_del_Sistema.Obj_Asistencia_Y_Puntualidad;
import Obj_Administracion_del_Sistema.Obj_Configuracion_Base_de_Datos;
import Obj_Administracion_del_Sistema.Obj_Configuracion_Base_de_Datos_2;
import Obj_Administracion_del_Sistema.Obj_Configuracion_Base_de_Datos_3;
import Obj_Administracion_del_Sistema.Obj_Usuario;
import Obj_Auditoria.Obj_Actividades_Por_Proyecto;
import Obj_Auditoria.Obj_Actividades_Relacionadas;
import Obj_Auditoria.Obj_Alimentacion_Cortes;
import Obj_Auditoria.Obj_Alimentacion_Denominacion;
import Obj_Auditoria.Obj_Denominaciones;
import Obj_Auditoria.Obj_Divisas_Y_Tipo_De_Cambio;
import Obj_Auditoria.Obj_Movimiento_De_Asignacion;
import Obj_Auditoria.Obj_Pedido_De_Monedas;
import Obj_Auditoria.Obj_Retiros_Cajeros;
import Obj_Checador.Obj_Alimentacion_De_Permisos_A_Empleados;
import Obj_Checador.Obj_Asignacion_De_Computadoras_Para_Checador_Por_Establecimiento;
import Obj_Checador.Obj_Chacador_De_Embarque_De_Pedidos;
import Obj_Checador.Obj_Dias_Inhabiles;
import Obj_Checador.Obj_Horarios;
import Obj_Checador.Obj_Mensaje_Personal;
import Obj_Checador.Obj_Mensajes;
import Obj_Checador.Obj_Solicitud_De_Empleados;
import Obj_Compras.Obj_Alimentacion_De_Codigos_Adicionales;
import Obj_Compras.Obj_Alimentacion_De_Inventarios_Parciales;
import Obj_Compras.Obj_Alimentacion_De_Productos_Proximos_A_Caducar;
import Obj_Compras.Obj_Alta_De_Productos;
import Obj_Compras.Obj_Compra_De_Cascos;
import Obj_Compras.Obj_Cotizaciones_De_Un_Producto;
import Obj_Compras.Obj_Gestion_De_Pedidos_A_Establecimientos;
import Obj_Compras.Obj_Horario_Base_De_Entrega_De_Pedidos;
import Obj_Compras.Obj_Programacion_De_Proveedores;
import Obj_Compras.Obj_Venta_De_Cascos_A_Proveedores;
import Obj_Compras.Obj_Puntos_De_Venta_De_Tiempo_Aire;
import Obj_Compras.Obj_Registrar_Zona_Completada;
import Obj_Compras.Obj_Unidades_De_Medida_De_Producto;
import Obj_Contabilidad.Obj_Alta_Proveedores_Polizas;
import Obj_Contabilidad.Obj_Conceptos_De_Ordenes_De_Pago;
import Obj_Contabilidad.Obj_Importar_Voucher;
import Obj_Contabilidad.Obj_Orden_De_Gasto;
import Obj_Contabilidad.Obj_Proveedores;
import Obj_Contabilidad.Obj_Saldo_Banco_Interno;
import Obj_Contabilidad.Obj_Transpaso_A_Banco_Interno;
import Obj_Cuadrantes.Obj_Actividad;
import Obj_Cuadrantes.Obj_Aspectos;
import Obj_Cuadrantes.Obj_Cuadrantes;
import Obj_Cuadrantes.Obj_Nivel_Critico;
import Obj_Evaluaciones.Obj_Asignacion_De_Cuestionarios;
import Obj_Evaluaciones.Obj_Contestacion_De_Cuestionario;
import Obj_Evaluaciones.Obj_Cuestionarios;
import Obj_Evaluaciones.Obj_Descripcion_De_Puestos_y_Responsabilidades;
import Obj_Evaluaciones.Obj_Directorios;
import Obj_Evaluaciones.Obj_Equipo_De_Trabajo;
import Obj_Evaluaciones.Obj_Nivel_Jerarquico;
import Obj_Evaluaciones.Obj_Opciones_De_Respuestas;
import Obj_Evaluaciones.Obj_Ponderacion;
import Obj_Evaluaciones.Obj_Preguntas;
import Obj_Evaluaciones.Obj_Temporada;
import Obj_Inventarios.Obj_Alimentacion_De_Mermas;
import Obj_Lista_de_Raya.Obj_Alimentacion_De_Vacaciones;
import Obj_Lista_de_Raya.Obj_Asignacion_Mensajes;
import Obj_Lista_de_Raya.Obj_Bono_Complemento_Sueldo;
import Obj_Lista_de_Raya.Obj_Bono_Puntualidad_Y_Asistencia;
import Obj_Lista_de_Raya.Obj_Captura_Fuente_Sodas;
import Obj_Lista_de_Raya.Obj_Conceptos_De_Extras_Para_Lista_De_Raya;
import Obj_Lista_de_Raya.Obj_Departamento;
import Obj_Lista_de_Raya.Obj_Diferencia_De_Cortes;
import Obj_Lista_de_Raya.Obj_Empleados;
import Obj_Lista_de_Raya.Obj_Establecimiento;
import Obj_Lista_de_Raya.Obj_Finiquitos;
import Obj_Lista_de_Raya.Obj_Grupo_De_Vacaciones;
import Obj_Lista_de_Raya.Obj_Perfil_De_Puestos;
import Obj_Lista_de_Raya.Obj_Totales_De_Cheque;
import Obj_Lista_de_Raya.Obj_Prestamos;
import Obj_Lista_de_Raya.Obj_Puestos;
import Obj_Lista_de_Raya.Obj_Rango_De_Prestamos;
import Obj_Lista_de_Raya.Obj_Revision_De_Horario_Por_Nivel_Jerarquico;
import Obj_Lista_de_Raya.Obj_Sueldos;
import Obj_Lista_de_Raya.Obj_Tabla_De_Vacaciones;
import Obj_Lista_de_Raya.Obj_Tipo_De_Bancos;
import Obj_Lista_de_Raya.Obj_Fue_Sodas_AUXF;
import Obj_Lista_de_Raya.Obj_Fue_Sodas_DH;
import Obj_Marketing.Obj_Alimentacion_De_Meta_Mensual_De_Venta;
import Obj_Marketing.Obj_Configuracion_Meta_Mensual_De_Ventas;
import Obj_Matrices.Obj_Aspectos_De_La_Etapa;
import Obj_Matrices.Obj_Etapas;
import Obj_Matrices.Obj_Unidades_de_Inspeccion;
import Obj_Planeacion.Obj_Actividades_De_Una_Planeacion;
import Obj_Planeacion.Obj_Frecuencia_De_Actividades;
import Obj_Planeacion.Obj_Opciones_De_Respuesta;
import Obj_Planeacion.Obj_Pendientes;
import Obj_Planeacion.Obj_Prioridad_Y_Ponderacion;
import Obj_Planeacion.Obj_Seleccion_De_Usuarios;
import Obj_Punto_De_Venta.Obj_Abono_Clientes;
import Obj_Punto_De_Venta.Obj_Clientes;
import Obj_Punto_De_Venta.Obj_Clientes_Ventas;
import Obj_Punto_De_Venta.Obj_Ventas_Express;
import Obj_Seguridad.Obj_Autorizacion_Acceso_Proveedores;
import Obj_Seguridad.Obj_Registro_Proveedores;
import Obj_Servicios.Obj_Administracion_De_Activos;
import Obj_Servicios.Obj_Registro_De_Desarrollo;
import Obj_Servicios.Obj_Servicios;
import Obj_Servicios.Obj_Tipos_De_Equipos;
import Obj_Servicios.Obj_Catalogo_Servicios;
import Obj_Servicios.Obj_Marcas_De_Activos;
import Obj_Servicios.Obj_Modelos_De_Activos;
import Obj_Servicios.Obj_Pc_Por_Establecimientos;



public class GuardarSQL {
	String Qbitacora ="exec sp_insert_bitacora ?,?,?,?,?";
	PreparedStatement pstmtb = null;
	Obj_Usuario usuario = new Obj_Usuario().LeerSession();
	
	public boolean Guardar_Empleado(Obj_Empleados empleado, ByteArrayInputStream datosHuella, ByteArrayInputStream datosHuella2, int tamañoHuella, int tamañoHuella2){
		String query = "exec sp_insert_empleado ?,?,?,?,?,?,?,?,?,?,"
											+  "?,?,?,?,?,?,?,?,?,?,"
											+  "?,?,?,?,?,?,?,?,?,?,"
											+  "?,?,?,?,?,?,?,?,?,?,"
											+  "?,?,?,?,?,?,?,?,?,?,"
											+  "?,?,?,?,?,?,?,?";
		
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			
			// insert bitacora
			String pc = InetAddress.getLocalHost().getHostName();
			String ip = InetAddress.getLocalHost().getHostAddress();
			pstmtb = con.prepareStatement(Qbitacora);
			pstmtb.setString(1, pc);
			pstmtb.setString(2, ip);
			pstmtb.setInt(3, usuario.getFolio());
			pstmtb.setString(4, "sp_insert_empleado alta "+empleado.getNombre().toUpperCase()+empleado.getAp_paterno().toUpperCase()+empleado.getAp_materno().toUpperCase());
			pstmtb.setString(5, "Empleados Nuevo");
			pstmtb.executeUpdate();
			
//			private String telefono_cuadrante;
			int i=1;
			pstmt = con.prepareStatement(query);
			pstmt.setString(i,		empleado.getNo_checador());
			pstmt.setString(i+=1, 	empleado.getNombre().toUpperCase());
			pstmt.setString(i+=1,	empleado.getAp_paterno().toUpperCase());
			pstmt.setString(i+=1,	empleado.getAp_materno().toUpperCase());
			pstmt.setString(i+=1,	empleado.getFecha_nacimiento());
			pstmt.setString(i+=1,	empleado.getCalle().toUpperCase());
			pstmt.setString(i+=1, 	empleado.getColonia().toUpperCase());
			pstmt.setString(i+=1, 	empleado.getPoblacion().toUpperCase());
			pstmt.setString(i+=1, 	empleado.getTelefono_familiar().toUpperCase());
			pstmt.setString(i+=1, 	empleado.getTelefono_propio().toUpperCase());
			pstmt.setString(i+=1, 	empleado.getRfc().toUpperCase());
			pstmt.setString(i+=1, 	empleado.getCurp().toUpperCase());
			pstmt.setInt(i+=1, 		empleado.getSexo());
			pstmt.setString(i+=1, 	empleado.getEmailEmpresa());
			
			FileInputStream stream_foto = new FileInputStream(empleado.getFoto());
			pstmt.setBinaryStream(i+=1, stream_foto, empleado.getFoto().length());
			
//			laboral
			pstmt.setInt(i+=1, 		empleado.getHorario());
			pstmt.setInt(i+=1, 		empleado.getHorario2());
			pstmt.setInt(i+=1, 		empleado.getStatus_h1());
			pstmt.setInt(i+=1, 		empleado.getStatus_h2());
			pstmt.setInt(i+=1, 		empleado.getStatus_rotativo());
			pstmt.setString(i+=1, 	empleado.getFecha_ingreso().toUpperCase());
			pstmt.setInt(i+=1, 		empleado.getStatus());	
			pstmt.setInt(i+=1, 		empleado.isCuadrante_parcial() ? 1 : 0);
			pstmt.setInt(i+=1, 		empleado.getDepartameto());	
			pstmt.setString(i+=1, 	empleado.getImss().toUpperCase().trim().toUpperCase());
			pstmt.setInt(i+=1, 		empleado.getStatus_imss());
			pstmt.setString(i+=1, 	empleado.getNumero_infonavit().toUpperCase());
			pstmt.setInt(i+=1, 		empleado.getEstablecimiento());
			pstmt.setInt(i+=1, 		empleado.getPuesto());
			pstmt.setString(i+=1, 	empleado.getStatus_checador().toString().trim());
//			pstmt.setString(i+=1, 	empleado.getStatus_checador().equals("NORMAL")?"N":(empleado.getStatus_checador().equals("LIBRE")?"L":"B"));
			
//			percepciones y deducciones
			pstmt.setFloat(i+=1, 	empleado.getSalario_diario());
			pstmt.setFloat(i+=1, 	empleado.getSalario_diario_integrado());
			pstmt.setString(i+=1,	empleado.getForma_pago().toUpperCase());
			pstmt.setFloat(i+=1,     0);
			pstmt.setInt(i+=1, 		 1);
			pstmt.setInt(i+=1, 		empleado.getPrestamo());
			pstmt.setFloat(i+=1, 	empleado.getPension_alimenticia());
			pstmt.setFloat(i+=1,	empleado.getInfonavit());
			pstmt.setString(i+=1, 	empleado.getTargeta_nomina().toUpperCase());
			pstmt.setInt(i+=1, 		empleado.getTipo_banco());
			pstmt.setBoolean(i+=1, (empleado.isGafete())? true: false);
			pstmt.setBoolean(i+=1, (empleado.isFuente_sodas())? true: false);
			pstmt.setString(i+=1, 	empleado.getObservasiones().toUpperCase());
			
			pstmt.setString(i+=1, 	empleado.getFecha_actualizacion().toUpperCase());
			
			pstmt.setInt(i+=1, empleado.getHorario3());
			pstmt.setInt(i+=1, empleado.getStatus_h3());
			
			pstmt.setString(i+=1, empleado.getFecha_ingreso_imss());
			pstmt.setString(i+=1, empleado.getFecha_vencimiento_licencia());
			
//			TODO (Datos Adicionales)
			pstmt.setString(i+=1, 	empleado.getEstado_civil().toUpperCase());
			pstmt.setString(i+=1, 	empleado.getTipo_sangre().toUpperCase());
			pstmt.setString(i+=1, 	empleado.getEscolaridad().toUpperCase());
			pstmt.setInt(i+=1, 	empleado.getContrato());
			pstmt.setInt(i+=1, 	empleado.getPresencia_fisica());
			
			pstmt.setInt(i+=1, 		empleado.getPerfil());
			
	    	pstmt.setString(i+=1, 	empleado.getEmailPersonal());
	    	pstmt.setString(i+=1, 	empleado.getForma_de_checar());
	    	System.out.println(empleado.getForma_de_checar()+" forma de checar");
			
//TODO(Huellas(inicio))-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
			pstmt.setBinaryStream(i+=1, datosHuella,tamañoHuella);
	    	pstmt.setBinaryStream(i+=1, datosHuella2,tamañoHuella2);
//TODO(Huellas(fin))-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	    	
			pstmt.executeUpdate();
			con.commit();
			
		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Empleado ] Insert  SQLException: sp_insert_empleado "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			if (con != null){
				try {
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				} catch(SQLException ex) {
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Empleado ] Insert  SQLException: sp_insert_empleado "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
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
	
	public boolean Guardar_Establecimiento(Obj_Establecimiento establecimiento){
		String query = "exec sp_insert_establecimiento ?,?,?,?,?,?,?,?,?,?,? ";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, establecimiento.getEstablecimiento().toUpperCase().trim());
			pstmt.setString(2, establecimiento.getAbreviatura().toUpperCase().trim());
			pstmt.setString(3, establecimiento.getSerie().toUpperCase().trim());
			pstmt.setInt(4, establecimiento.getGrupo_cheque());
			pstmt.setInt(5, establecimiento.getStatus());
			pstmt.setInt(6, establecimiento.getGrupo_cortes());
			pstmt.setInt(7, establecimiento.getGrupo_permitir_nc());
			
			pstmt.setString(8, establecimiento.getDomicilio().toUpperCase().trim());
			pstmt.setString(9, establecimiento.getRazon_social().toUpperCase().trim());
			pstmt.setString(10, establecimiento.getRfc().toUpperCase().trim());
			pstmt.setString(11, establecimiento.getTelefono().toUpperCase().trim());
			
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
			if (con != null){
				try {
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				} catch(SQLException ex) {
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Establecimiento ] Insert  SQLException: sp_insert_establecimiento "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);

				}
			} 
			return false;
		}finally{
			try {
				pstmt.close();
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Establecimiento ] Insert  SQLException: sp_insert_establecimiento "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			}
		}		
		return true;
	}
	
	public boolean Guardar_Puesto(Obj_Puestos puesto){
		String query = "exec sp_insert_puesto ?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, puesto.getPuesto().toUpperCase().trim());
			pstmt.setString(2, puesto.getAbreviatura().toUpperCase().trim());
			pstmt.setString(3, (puesto.getStatus())?"1":"0");
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Puesto ] Insert  SQLException: sp_insert_puesto "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);

			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Puesto ] Insert  SQLException: sp_insert_puesto "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);

				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Puesto ] Insert  SQLException: sp_insert_puesto "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);

			}
		}		
		return true;
	}
	
	public boolean Guardar_Dia_Inhabil(Obj_Dias_Inhabiles diaInA){
		String query = "exec sp_insert_diaInHabil ?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, diaInA.getFecha());
			pstmt.setString(2, diaInA.getDescripcion().toUpperCase());
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Dia_Inhabil ] Insert  SQLException: sp_insert_diaInHabil "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Dia_Inhabil ] Insert  SQLException: sp_insert_diaInHabil "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Dia_Inhabil ] Insert  SQLException: sp_insert_diaInHabil "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			}
		}		
		return true;
	}
	
	public boolean Guardar_Aspectos(Obj_Aspectos aspecto){
		String query   = "exec cuadrantes_aspecto_insert ?,?,?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setInt   (1, aspecto.getFolio()      );
			pstmt.setString(2, aspecto.getAspecto()    );
			pstmt.setInt   (3, aspecto.getPonderacion());
			pstmt.setInt   (4, aspecto.getOrden()      );
			pstmt.setString(5, aspecto.getEstatus()    );
			pstmt.setString(6, aspecto.getGuardaModifica());
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Aspectos ] Insert  SQLException: sp_insert_atributo "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Aspectos ] Insert  SQLException: sp_insert_atributo "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Aspectos ] Insert  SQLException: sp_insert_atributo "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			}
		}		
		return true;
	}
	
	
	public boolean Guardar_Eq_Trabajo(Obj_Equipo_De_Trabajo EqTabajo){
		String query = "exec sp_insert_equipo_trabajo ?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, EqTabajo.getDescripcion().toUpperCase());
			pstmt.setString(2, (EqTabajo.getStatus())?"1":"0");
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Eq_Trabajo ] Insert  SQLException: sp_insert_equipo_trabajo "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Eq_Trabajo ] Insert  SQLException: sp_insert_equipo_trabajo "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Eq_Trabajo ] Insert  SQLException: sp_insert_equipo_trabajo "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			}
		}		
		return true;
	}
	
	public boolean Guardar_Ponderacion(Obj_Ponderacion pond){
		String query = "exec sp_insert_ponderacion  ?,?,?,?,?, ?,?,?,?,?, ?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, pond.getDescripcion().toUpperCase());
			pstmt.setFloat (2, pond.getValor());
			pstmt.setString(3, pond.getFechaIn());
			pstmt.setString(4, pond.getFechaFin());
			pstmt.setString(5, pond.isDomingo()?"1":"0");
			pstmt.setString(6, pond.isLunes()?"1":"0");
			pstmt.setString(7, pond.isMartes()?"1":"0");
			pstmt.setString(8, pond.isMiercoles()?"1":"0");
			pstmt.setString(9,pond.isJueves()?"1":"0");
			pstmt.setString(10,pond.isViernes()?"1":"0");
			pstmt.setString(11,pond.isSabado()?"1":"0");
			pstmt.setString(12, pond.getStatus()?"1":"0");
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Ponderacion ] Insert  SQLException: sp_insert_ponderacion "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Ponderacion ] Insert  SQLException: sp_insert_ponderacion "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Ponderacion ] Insert  SQLException: sp_insert_ponderacion "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			}
		}		
		return true;
	}

	public boolean Guardar_Nivel_Critico(Obj_Nivel_Critico nivel_critico){
		String query   = "exec cuadrantes_nivel_critico_insert ?,?,?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setInt   (1, nivel_critico.getFolio()      );
			pstmt.setString(2, nivel_critico.getNivel_critico() );
			pstmt.setInt   (3, nivel_critico.getPonderacion());
			pstmt.setInt   (4, nivel_critico.getOrden()      );
			pstmt.setString(5, nivel_critico.getEstatus()    );
			pstmt.setString(6, nivel_critico.getNuevoModifica());
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Nivel_Critico ] Insert  SQLException:  "+query+" "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Nivel_Critico ] Insert  SQLException:  "+query+" "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Nivel_Critico ] Insert  SQLException:  "+query+" "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			}
		}		
		return true;
	}
	
	public boolean Guardar_Relacion_Actividades(Obj_Actividades_Relacionadas relacion){
		String query = "exec sp_insert_relacion_actividad ?,?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setInt	(1, relacion.getFolio());
			pstmt.setString	(2, relacion.getProyecto().toUpperCase().trim());
			pstmt.setString	(3, relacion.getDescripcion().toUpperCase().trim());
			pstmt.setString	(4, relacion.getNivel_critico().trim());
			pstmt.setInt	(5, relacion.getStatus());
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Relacion_Actividades ] Insert  SQLException: sp_insert_relacion_actividad "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Relacion_Actividades ] Insert  SQLException: sp_insert_relacion_actividad "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Relacion_Actividades ] Insert  SQLException: sp_insert_relacion_actividad "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			}
		}		
		return true;
	}
	
	public boolean Guardar_Relacion_Tabla(Obj_Actividades_Relacionadas relacion, String[][] tabla){
		String querytabla = "exec sp_insert_tabla_relacion_actividad ?,?,?,?,?";
				
		Connection con = new Connexion().conexion();
		PreparedStatement pstmtTabla = null;
		try {
			con.setAutoCommit(false);
			
			pstmtTabla = con.prepareStatement(querytabla);
				
			for(int i=0; i<tabla.length; i++){
				
				pstmtTabla.setInt(1, relacion.getFolio());
				pstmtTabla.setInt(2, Integer.parseInt(tabla[i][0].toString().trim()));
				pstmtTabla.setString(3, tabla[i][3].toString().trim().toUpperCase());
				pstmtTabla.setString(4, tabla[i][4].toString().trim());
				pstmtTabla.setInt(5, Boolean.parseBoolean(tabla[i][2]) ? 1 : 0);
				
				pstmtTabla.executeUpdate();
			}
			
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada Guardar_Proyecto_Tabla");
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Relacion_Tabla ] Insert  SQLException: sp_insert_tabla_relacion_actividad "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Relacion_Tabla ] Insert  SQLException: sp_insert_tabla_relacion_actividad "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Relacion_Tabla ] Insert  SQLException: sp_insert_tabla_relacion_actividad "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			}
		}		
		return true;
	}
	
	public boolean Guardar_Proyecto(Obj_Actividades_Por_Proyecto proyec){
		String query = "exec sp_insert_proyecto ?,?,?,?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setInt	(1, proyec.getFolio());
			pstmt.setString	(2, proyec.getProyecto().toUpperCase().trim());
			pstmt.setString	(3, proyec.getDescripcion().toUpperCase().trim());
			pstmt.setString	(4, proyec.getNivel_critico().trim());
			pstmt.setInt	(5, proyec.getStatus());
			pstmt.setString	(6, proyec.getFecha_inicial());
			pstmt.setString	(7, proyec.getFecha_final());
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Proyecto ] Insert  SQLException: sp_insert_proyecto "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Proyecto ] Insert  SQLException: sp_insert_proyecto "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Proyecto ] Insert  SQLException: sp_insert_proyecto "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			}
		}		
		return true;
	}
	
	public boolean Guardar_Proyecto_Tabla(Obj_Actividades_Por_Proyecto proyect, String[][] tabla){
		String querytabla = "exec sp_insert_tabla_proyecto ?,?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmtTabla = null;
		try {
			con.setAutoCommit(false);
			pstmtTabla = con.prepareStatement(querytabla);
			for(int i=0; i<tabla.length; i++){
				pstmtTabla.setInt(1, proyect.getFolio());
				pstmtTabla.setInt(2, Integer.parseInt(tabla[i][0].toString().trim()));
				pstmtTabla.setString(3, tabla[i][3].toString().trim().toUpperCase());
				pstmtTabla.setString(4, tabla[i][4].toString().trim());
				pstmtTabla.setInt(5, Boolean.parseBoolean(tabla[i][2]) ? 1 : 0);
				pstmtTabla.executeUpdate();
			}
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Proyecto_Tabla ] Insert  SQLException: sp_insert_tabla_proyecto "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada Guardar_Proyecto_Tabla");
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Proyecto_Tabla ] Insert  SQLException: sp_insert_tabla_proyecto "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Proyecto_Tabla ] Insert  SQLException: sp_insert_tabla_proyecto "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Proyecto_Tabla ] Insert  SQLException: sp_insert_tabla_proyecto "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			}
		}		
		return true;
	}
	
	public boolean opcion_respuesta(Obj_Opciones_De_Respuestas respuesta){
		String query = "exec sp_insert_opciones_respuesta	?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, respuesta.getNombre().toUpperCase().trim());
			pstmt.setInt(2, respuesta.getTipo_opcion().equals("Opción Libre") ? 0 : 1);
			pstmt.setInt(3, respuesta.isStatus() ? 1 : 0);
			
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
	
	public boolean Guardar_Tipo_Banco(Obj_Tipo_De_Bancos banck){
		String query = "exec sp_insert_tipo_banco ?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, banck.getBanco().toUpperCase());
			pstmt.setString(2, banck.getAbreviatura().toUpperCase());
			pstmt.setString(3, (banck.getStatus())?"1":"0");
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
	
	public boolean Guardar_Divisas(Obj_Divisas_Y_Tipo_De_Cambio divisas){
		String query = "insert into tb_divisas_tipo_de_cambio(nombre_divisas,valor,status) values(?,?,?)";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, divisas.getNombre().toUpperCase());
			pstmt.setFloat (2, divisas.getValor());
			pstmt.setString(3, (divisas.getStatus())?"1":"0");
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
	
	public boolean Guardar_Denominaciones(Obj_Denominaciones denominaciones){
		String query = "exec sp_insert_alta_denominacion ?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, denominaciones.getDenominacion().toUpperCase());
			pstmt.setFloat(2, denominaciones.getValor_denominacion());
			pstmt.setString(3, denominaciones.getMoneda());
			pstmt.setInt(4, (denominaciones.isStatus())?1:0);
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
	
	public boolean Guardar_Corte(Obj_Alimentacion_Cortes corte, Object[][] tb_asignaciones,Object[][] tb_vauchers,Object[][] tb_totales_por_fecha,  Object[] lista_de_asignaciones_en_uso,String turno_o_asignacion){
			
		String query_asignacion = 		 "exec cortes_asignacion_insert ?,?,?,?,?,?,?,?,?";		// <-9		11 ->  tb_tabla_de_asignaciones_para_cortes 
		String query_vauchers =   		 "exec cortes_vouchers_insert ?,?,?,?,?,?,?,?,?,?,?,?,?";					// <-11		13 ->  tb_vauchers
		String query_totales_por_fecha = "exec cortes_totales_de_asignaciones_por_fecha_insert ?,?,?,?";		// <-4		 6 ->  tb_totales_de_asignaciones_por_fecha
		String query_corte =      		 "exec cortes_corte_de_caja_insert ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?";				// <-16
		
		String Parametros_asignacion ="";	
		String query_status_corte_para_filtro="";
		
		if(turno_o_asignacion.equals("A")) {
		  query_status_corte_para_filtro="update IZAGAR_Relacion_de_Asignaciones_Liquidadas set status_corte=1 where Asignacion = ?";
		}else {
		  query_status_corte_para_filtro="update IZAGAR_Relacion_de_Turnos_Liquidados set status_corte=1 where turno = ?";	
		} 
		
		Connection con = new Connexion().conexion();
		Connection con_IZAGAR = new Connexion().conexion_IZAGAR();
		
		PreparedStatement pstmt_asignacion = null;
		PreparedStatement pstmt_vauchers = null;
		PreparedStatement pstmt_total_por_fecha = null;
		PreparedStatement pstmt_corte = null;
		PreparedStatement pstmt_update_asignacion = null;
		
		try {
			con.setAutoCommit(false);
			pstmt_asignacion 	  = con.prepareStatement(query_asignacion);
			pstmt_vauchers		  = con.prepareStatement(query_vauchers);
			pstmt_total_por_fecha = con.prepareStatement(query_totales_por_fecha);
			pstmt_corte 		  = con.prepareStatement(query_corte);
			pstmt_update_asignacion= con_IZAGAR.prepareStatement(query_status_corte_para_filtro);
			int i=1;
			
			for(int x= 0; x<tb_asignaciones.length; x++){
				pstmt_asignacion.setString(i, 		corte.getFolio_corte().toUpperCase().trim());
				pstmt_asignacion.setString(i+=1,	tb_asignaciones[x][0].toString().trim());
				pstmt_asignacion.setString(i+=1,	tb_asignaciones[x][1].toString().trim());
				pstmt_asignacion.setString(i+=1,	tb_asignaciones[x][2].toString().trim());
				pstmt_asignacion.setFloat(i+=1, 	Float.valueOf(tb_asignaciones[x][3].toString().trim()));
				pstmt_asignacion.setInt(i+=1, 		Integer.valueOf(tb_asignaciones[x][4].toString().trim()));
				pstmt_asignacion.setString(i+=1, 	tb_asignaciones[x][5].toString().trim());
				pstmt_asignacion.setString(i+=1, 	tb_asignaciones[x][6].toString().trim());
				pstmt_asignacion.setString(i+=1, 	tb_asignaciones[x][7].toString().trim());
				
				Parametros_asignacion=	corte.getFolio_corte().toUpperCase().trim()+","+
										tb_asignaciones[x][0].toString().trim() +","+
										tb_asignaciones[x][1].toString().trim() +","+
										tb_asignaciones[x][2].toString().trim() +","+
								 	    Float.valueOf(tb_asignaciones[x][3].toString().trim())+","+
									 	Integer.valueOf(tb_asignaciones[x][4].toString().trim())+","+
								 	    tb_asignaciones[x][5].toString().trim()+","+
									 	tb_asignaciones[x][6].toString().trim()+","+
										tb_asignaciones[x][7].toString().trim() ;
				pstmt_asignacion.executeUpdate();
				
				i=1;
				System.out.println(	"paso asignacion");
			}
			
			i=1;
			for(int x= 0; x<tb_vauchers.length; x++){
				
				pstmt_vauchers.setString(i, 	corte.getFolio_corte().toUpperCase().trim());
				pstmt_vauchers.setString(i+=1, tb_vauchers[x][0].toString().trim());
				pstmt_vauchers.setString(i+=1, tb_vauchers[x][1].toString().trim());
				pstmt_vauchers.setString(i+=1, tb_vauchers[x][2].toString().trim());
				pstmt_vauchers.setString(i+=1, tb_vauchers[x][3].toString().trim());
				pstmt_vauchers.setString(i+=1, tb_vauchers[x][4].toString().trim());
				pstmt_vauchers.setString(i+=1, tb_vauchers[x][5].toString().trim());
				pstmt_vauchers.setString(i+=1, tb_vauchers[x][6].toString().trim());
				pstmt_vauchers.setString(i+=1, tb_vauchers[x][7].toString().trim());
				pstmt_vauchers.setString(i+=1, tb_vauchers[x][8].toString().trim());
				pstmt_vauchers.setFloat(i+=1, Float.parseFloat(tb_vauchers[x][9].toString().trim()));
				pstmt_vauchers.setString(i+=1, tb_vauchers[x][10].toString().trim());
				pstmt_vauchers.setFloat(i+=1, Float.parseFloat(tb_vauchers[x][11].toString().trim()));
				
				pstmt_vauchers.executeUpdate();
				
				i=1;
				System.out.println(	"paso vauchers");
			}
			
			i=1;
			for(int x= 0; x<tb_totales_por_fecha.length; x++){
				
				pstmt_total_por_fecha.setString(i, 	corte.getFolio_corte().toUpperCase().trim());
				pstmt_total_por_fecha.setString(i+=1, tb_totales_por_fecha[x][0].toString().trim());
				pstmt_total_por_fecha.setString(i+=1, tb_totales_por_fecha[x][1].toString().trim());
				pstmt_total_por_fecha.setFloat(i+=1, Float.parseFloat(tb_totales_por_fecha[x][2].toString().trim()));
				pstmt_total_por_fecha.executeUpdate();
				i=1;
				System.out.println(	"paso totales_por_fecha");
			}
			
			i=1;
			
			pstmt_corte.setString(i, corte.getFolio_corte().toUpperCase().trim());
			pstmt_corte.setInt(i+=1, corte.getUsuario());
			pstmt_corte.setInt(i+=1, corte.getFolio_empleado());
			pstmt_corte.setString(i+=1, corte.getEstablecimiento_de_corte());
			pstmt_corte.setFloat(i+=1, corte.getCorte_sistema());
			pstmt_corte.setFloat(i+=1,corte.getApartado());
			pstmt_corte.setFloat(i+=1, corte.getAbono());
			
			pstmt_corte.setFloat(i+=1, corte.getTiempo_aire());
			pstmt_corte.setFloat(i+=1, corte.getRecibo_luz());
			pstmt_corte.setFloat(i+=1, corte.getDeposito());
			pstmt_corte.setFloat(i+=1, corte.getEfectivo());
			pstmt_corte.setFloat(i+=1, corte.getCheques());
			pstmt_corte.setFloat(i+=1, corte.getTotal_de_vauchers());
			pstmt_corte.setFloat(i+=1, corte.getDiferencia_corte());
			pstmt_corte.setString(i+=1, corte.getComentario().toUpperCase());
			pstmt_corte.setString(i+=1, tb_asignaciones[0][0].toString().trim());
			pstmt_corte.setFloat(i+=1, corte.getDinero_electonico());
			
			pstmt_corte.executeUpdate();
			
			con.commit();
			System.out.println(	"paso guardado de corte");
			pstmt_update_asignacion.setString(1,tb_asignaciones[0][0].toString().trim());
		    pstmt_update_asignacion.executeUpdate();
		    con_IZAGAR.commit();
		    
		
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage()+" "+Parametros_asignacion);
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Corte ] Insert  SQLException: "+e.getMessage()+"\n"+query_asignacion+Parametros_asignacion+"\n"+query_vauchers+"\n"+query_totales_por_fecha+"\n"+query_corte, "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Corte ] Insert  SQLException: "+e.getMessage()+"\n"+query_asignacion+"\n"+query_vauchers+"\n"+query_totales_por_fecha+"\n"+query_corte, "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
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
	
	public boolean Guardar_Bono(Obj_Bono_Complemento_Sueldo bono){
		String query = "exec sp_insert_bono ?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setFloat(1, bono.getBono());
			pstmt.setString(2, bono.getAbreviatura().toUpperCase());
			pstmt.setString(3, (bono.getStatus())?"1":"0");
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
	
	public boolean Guardar_Sueldo(Obj_Sueldos sueldo){
		String query = "exec sp_insert_sueldo ?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setFloat(1, sueldo.getSueldo());
			pstmt.setString(2, (sueldo.getStatus())?"1":"0");
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transaccón ha sido abortada");
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

	public boolean Guardar_Usuario(Obj_Usuario usuario){
		String query = "exec sp_insert_usuario ?,?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, usuario.getNombre_completo().toUpperCase());
			pstmt.setString(2, usuario.getContrasena());
			pstmt.setInt(3, usuario.getPermiso_id());
			String fecha = new Date().toString();
			pstmt.setString(4, fecha);
			pstmt.setInt(5, usuario.getStatus());
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+ e.getMessage());
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
	
	
	@SuppressWarnings("rawtypes")
	public boolean Guardar_Usuario(Obj_Usuario usuario,Vector permisos){
		String query = " exec sp_insert_permisos_submenus_usuario_nuevo ?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			
			for(int i=0; i<permisos.size(); i++){
				pstmt.setInt(1, usuario.getFolio());
				String[] arreglo = permisos.get(i).toString().split("/");
				pstmt.setString(2, arreglo[0]);
				pstmt.setString(3, arreglo[1]);
				pstmt.execute();
//				System.out.println(usuario.getFolio());
			}
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+ e.getMessage());
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
				pstmt.close();
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	public int getMenuId(String Nombre){
		int filas=0;
		try {
			String query = "select menu_id from tb_submenus where nombre ='"+Nombre+"'";
			Statement s = new Connexion().conexion().createStatement();
			ResultSet rs = s.executeQuery(query);
			while(rs.next()){
				filas = rs.getInt(1);
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return filas;
	}
	public boolean Guardar_fuente_sodas_rh(Obj_Fue_Sodas_DH fuentesodasrh){
		String query = "exec sp_insert_fuent_soda_rh ?,?,?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, "0");
			pstmt.setInt(2, fuentesodasrh.getFolio());
			pstmt.setString(3, fuentesodasrh.getNombre_Completo().toUpperCase());
			pstmt.setDouble(4, fuentesodasrh.getCantidad());
			pstmt.setString(5, fuentesodasrh.getFecha());
			pstmt.setString(6, "1");
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
	
	public boolean Guardar_fuente_sodas_auxf(Obj_Fue_Sodas_AUXF fuentesodasauxf){
		String query = "exec sp_insert_fuent_soda_auxf ?,?,?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, "0");
			pstmt.setInt(2,fuentesodasauxf.getFolio());
			pstmt.setString(3, fuentesodasauxf.getNombre_Completo().toUpperCase());
			pstmt.setDouble(4, fuentesodasauxf.getCantidad());
			pstmt.setString(5, fuentesodasauxf.getFecha());
			pstmt.setString(6, "1");
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
	
	public boolean Guardar_prestamo(Obj_Prestamos pres){
		String query = "exec sp_insert_prestamo ?,?,?,?,?,?,?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1,pres.getFolio_empleado());
			pstmt.setString(2, pres.getNombre_Completo().toUpperCase());
			pstmt.setString(3, pres.getFecha());
			pstmt.setDouble(4, pres.getCantidad());
			pstmt.setDouble(5, pres.getDescuento());
			
			pstmt.setString(6, "1");
			pstmt.setDouble(7, pres.getSaldo());
			pstmt.setString(8, "1");
			pstmt.setDouble(9, 0);
			pstmt.setDouble(10, pres.getTipo_prestamo());
			pstmt.executeUpdate();
			con.commit();
			
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_prestamo ] \n SQLException: sp_insert_prestamo "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_prestamo ] \n La transacción ha sido abortada update  SQLException: sp_insert_prestamo "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_prestamo ] \n Confirmacion SQLException: sp_insert_prestamo "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
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
	
	public boolean Guardar_Rango_Prestamos(Obj_Rango_De_Prestamos rango_prestamo){
		String query = "exec sp_insert_rango_prestamo ?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setDouble(1, rango_prestamo.getPrestamo_minimo());
			pstmt.setDouble(2, rango_prestamo.getPrestamo_maximo());
			pstmt.setDouble(3, rango_prestamo.getDescuento());
			pstmt.setString(4, (rango_prestamo.isStatus())?"1":"0");
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
	
	public boolean Asistencia_Puntualidad(Obj_Asistencia_Y_Puntualidad asistencia_puntualidad){
		String query = "exec sp_insert_asistencia_puntualidad ?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setFloat(1, asistencia_puntualidad.getValorAsistencia());
			pstmt.setFloat(2, asistencia_puntualidad.getValorPuntualidad());
			pstmt.setFloat(3, asistencia_puntualidad.getValorGafete());
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortado");
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
	
	public boolean Guardar_Alimentacion_denominacio(Obj_Alimentacion_Denominacion alim_denom, int folio_usuario, Object[][] tabla){
		
		String query ="exec sp_insert_alimentacion_de_efectivo_cortes ?,?,?,?,?,?,?,?";
		Connection con = new Connexion().conexion();
		
		try {
			PreparedStatement pstmt = con.prepareStatement(query);

			con.setAutoCommit(false);
			
			for(int i=0; i<tabla.length; i++){
				
				
				pstmt.setString(1, alim_denom.getFolio_corte().toUpperCase());
				pstmt.setString(2, alim_denom.getEmpleado().toUpperCase().trim());
				pstmt.setString(3, alim_denom.getEstablecimiento().toUpperCase());
				pstmt.setInt(4, Integer.parseInt(tabla[i][0].toString().trim()));
				pstmt.setFloat(5, Float.parseFloat(tabla[i][2].toString().trim()));
				pstmt.setFloat(6, Float.parseFloat(tabla[i][3].toString().trim()));
				pstmt.setFloat(7, Float.parseFloat(tabla[i][4].toString().trim()));
				pstmt.setInt(8, folio_usuario);
				
				pstmt.executeUpdate();
			}
					
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Alimentacion_denominacio ] update  SQLException: sp_insert_alimentacion_de_efectivo_cortes "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Alimentacion_denominacio ] update  SQLException: sp_insert_alimentacion_de_efectivo_cortes "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
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
	
	public boolean Guardar_Alimentacion_deposito(Obj_Alimentacion_Denominacion alim_denom, int folio_usuario, Object[][] tabla){
		
		String query ="exec sp_insert_deposito ?,?,?,?,?";
		Connection con = new Connexion().conexion();
		
		try {
			PreparedStatement pstmt = con.prepareStatement(query);

			con.setAutoCommit(false);
			
			for(int i=0; i<tabla.length; i++){
				pstmt.setString(1, alim_denom.getEstablecimiento().toUpperCase().trim());
				pstmt.setString(2, alim_denom.getEmpleado().toUpperCase().trim());
				pstmt.setString(3, tabla[i][0].toString().trim());
				pstmt.setFloat (4, Float.parseFloat(tabla[i][1].toString().trim()));
				pstmt.setInt (5, folio_usuario);
				pstmt.execute();
			}
					
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Alimentacion_deposito ] SQLException: "+query+" \n"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
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
	
	public boolean Guardar(Obj_Diferencia_De_Cortes pres){
		String query = "exec sp_insert_diferencia_cortes ?,?,?,?,?,?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1,pres.getFolio_empleado());
			pstmt.setString(2, pres.getNombre_Completo().toUpperCase());
			pstmt.setString(3, pres.getFecha());
			pstmt.setDouble(4, pres.getCantidad());
			pstmt.setDouble(5, pres.getDescuento());
			pstmt.setString(6, "1");
			pstmt.setDouble(7, pres.getSaldo());
			pstmt.setString(8, "1");
			pstmt.setDouble(9, 0);
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
	
	public boolean Guardar_Departamento(Obj_Departamento departamento){
		String query = "exec sp_insert_departamento ?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, departamento.getFolio());
			pstmt.setString(2, departamento.getDepartamento().toUpperCase().trim());
			pstmt.setString(3, departamento.getAbreviatura().toUpperCase().trim());
			pstmt.setString(4, (departamento.isStatus())?"1":"0");
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
	
	public boolean Guardar_ConfigBD(Obj_Configuracion_Base_de_Datos config){
		BufferedWriter bufferedWriter = null;
		String nomArchivo = System.getProperty("user.dir")+"\\Config\\config";
		try{
			File archivo = new File(nomArchivo);
			if(archivo.exists()){
				bufferedWriter = new BufferedWriter (new FileWriter(nomArchivo));
				
				bufferedWriter.write(config.getDireccionIPV4()+    		"\n");
				bufferedWriter.write(config.getNombreBD()+      		"\n");
				bufferedWriter.write(config.getUsuario()+ 	    		"\n");
				bufferedWriter.write(config.getContrasena()+       		"\n");
				
			}else{
				File folder = new File(System.getProperty("user.dir")+"\\Config");
				folder.mkdirs();
				archivo.createNewFile();
				bufferedWriter = new BufferedWriter (new FileWriter(nomArchivo));
				
				bufferedWriter.write(config.getDireccionIPV4()+    		"\n");
				bufferedWriter.write(config.getNombreBD()+      		"\n");
				bufferedWriter.write(config.getUsuario()+ 	    		"\n");
				bufferedWriter.write(config.getContrasena()+       		"\n");
				
			}
			
		}
		catch(FileNotFoundException ex)
		{
			ex.printStackTrace();
		}catch(IOException ex)
		{
			ex.printStackTrace();
		}finally
		{
			try
			{
				if(bufferedWriter!=null)
				{
					bufferedWriter.flush();
					bufferedWriter.close();
				}
			}catch(IOException ex)
			{
				ex.printStackTrace();
			}
		}return true;
	}
	
	public boolean Guardar_ConfigBD_2(Obj_Configuracion_Base_de_Datos_2 config){
		BufferedWriter bufferedWriter = null;
		String nomArchivo = System.getProperty("user.dir")+"\\Config\\config2";
		try{
			File archivo = new File(nomArchivo);
			if(archivo.exists()){
				bufferedWriter = new BufferedWriter (new FileWriter(nomArchivo));
				
				bufferedWriter.write(config.getDireccionIPV4()+    		"\n");
				bufferedWriter.write(config.getNombreBD()+      		"\n");
				bufferedWriter.write(config.getUsuario()+ 	    		"\n");
				bufferedWriter.write(config.getContrasena()+       		"\n");
				
			}else{
				File folder = new File(System.getProperty("user.dir")+"\\Config2");
				folder.mkdirs();
				archivo.createNewFile();
				bufferedWriter = new BufferedWriter (new FileWriter(nomArchivo));
				
				bufferedWriter.write(config.getDireccionIPV4()+    		"\n");
				bufferedWriter.write(config.getNombreBD()+      		"\n");
				bufferedWriter.write(config.getUsuario()+ 	    		"\n");
				bufferedWriter.write(config.getContrasena()+       		"\n");
				
			}
			
		}
		catch(FileNotFoundException ex)
		{
			ex.printStackTrace();
		}catch(IOException ex)
		{
			ex.printStackTrace();
		}finally
		{
			try
			{
				if(bufferedWriter!=null)
				{
					bufferedWriter.flush();
					bufferedWriter.close();
				}
			}catch(IOException ex)
			{
				ex.printStackTrace();
			}
		}return true;
	}
	
	public boolean Guardar(Obj_Configuracion_Del_Sistema configs){
		String query = "exec sp_config_sistema ?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, (configs.isBono_10_12())? "true" : "false");
			pstmt.setString(2, (configs.isBono_dia_extra())? "true" : "false");
			pstmt.setString(3, (configs.isGuardar_horario())? "true" : "false");
			pstmt.setString(4, (configs.isGuardar_departamento())? "true" : "false");
			
			pstmt.executeUpdate();
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
	
	public boolean lista_Imprimir(){
		
		String query ="exec sp_imprimir_lista_raya";

		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);

			pstmt.executeUpdate();
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
	

	public int getFolio_prestamo(int folio){
		int valor = 0;
	try {
			Connexion con = new Connexion();
			Statement s = con.conexion().createStatement();
			ResultSet rs = s.executeQuery("select folio from tb_prestamo where folio_empleado = "+folio+" and status = 1");
			while(rs.next()){
				valor = rs.getInt(1);			
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return valor;
	}
		
	public String[] getFinalCorte(int folio){
		String[] valor = new String[3];
		valor[0] = "";	valor[1] = "";	valor[2] = "";
	try {
		Connexion con = new Connexion();
		Statement s = con.conexion().createStatement();
		ResultSet rs = s.executeQuery("exec sp_get_parametros_cortes "+folio);
			while(rs.next()){
				valor[0] = rs.getString(1);
				valor[1] = rs.getString(2);
				valor[2] = rs.getString(3);
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return valor;
	}
	
	public boolean GuardarImportarVoucher(Obj_Importar_Voucher importar){
		String query = "exec sp_insert_voucher_importado ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			
			pstmt.setInt(1, importar.getContrato());
			pstmt.setString(2, importar.getF_transaccion());
			pstmt.setString(3, importar.getH_transaccion());
			pstmt.setString(4, importar.getNo_codigo());
			pstmt.setString(5, importar.getLeyenda());
			pstmt.setFloat(6, importar.getImporte());
			pstmt.setString(7, importar.getTerminal());
			pstmt.setString(8, importar.getCuenta());
			pstmt.setString(9, importar.getAutorizacion());
			pstmt.setString(10, importar.getTipo_de_cuenta());
			pstmt.setString(11, importar.getF_abono());
			pstmt.setString(12, importar.getReferencia_1());
			pstmt.setString(13, importar.getReferencia_2());
			pstmt.setString(14, importar.getReferencia_3());
			pstmt.setFloat(15, importar.getQ6());
			pstmt.setFloat(16, importar.getImporta_cash_back());
			pstmt.setFloat(17, importar.getEci());
			pstmt.setString(18, importar.getControl_interno_comercio());
			pstmt.setString(19, importar.getLote1());
			pstmt.setString(20, importar.getLote2());
			
			pstmt.executeUpdate();
			System.out.println("Guardo");
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
			if (con != null){
				try {
					System.out.println("La transacción ha sido abortada");
					JOptionPane.showMessageDialog(null, "Error en Guardar SQL en la funcion Importar GuardarImportarVoucher \n en el procedimiento almacenado sp_insert_voucher_importado " + e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
					con.rollback();
				} catch(SQLException ex) {
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en Guardar SQL en la funcion Importar GuardarImportarVoucher \n en el procedimiento almacenado sp_insert_voucher_importado " + ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);

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
	
	public boolean DeleteBancos(){
		String query = "exec sp_borrado_empleados_dif_1";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
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
	
	public boolean guardar_total_cheques(Obj_Totales_De_Cheque nomina){
		String query = "exec sp_insert_totales_cheques ?,?,?,?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, nomina.getNumero_listaraya());
			pstmt.setString(2,nomina.getEstablecimiento());
			pstmt.setString(3,nomina.getNomina());
			pstmt.setString(4,nomina.getPago_linea());
			pstmt.setString(5,nomina.getCheque_nomina());
			pstmt.setString(6,nomina.getLista_raya());
			pstmt.setString(7,nomina.getDiferencia());
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion Guardar en sp_insert_nomina "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);

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
	
	public boolean TemporadaGuardar(Obj_Temporada temporada){
		String query = "exec sp_insert_temporada ?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
					
			pstmt.setString(1, temporada.getDescripcion());
			pstmt.setString(2, temporada.getFecha_in());
			pstmt.setString(3, temporada.getFecha_fin());
			pstmt.setString(4, temporada.getDia());
			
			pstmt.executeUpdate();
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
	
	/*****agregando funcion para guardar numeros telefonicos*****/
	public boolean Guardar_Telefono(Obj_Directorios directorio){
		String query = "exec sp_insert_tb_direccion_telefonicos ?,?,?";
		
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			
			pstmt.setInt(1,directorio.getFolio_empleado());
			pstmt.setString(2, directorio.getNombre());
			pstmt.setString(3, directorio.getTelefono());
			
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			if(con != null){
				try{
					System.out.println("La transaccón ha sido abortada");
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

	
	//guardar nivel gerarquico
	public boolean Guardar_Nivel_G(Obj_Nivel_Jerarquico pond){
		String query = "exec sp_insert_tb_nivel_gerarquico  ?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setString (1, pond.getDescripcion());
			pstmt.setString (2, pond.getPuesto_principal());
			pstmt.setString (3, pond.getPuesto_dependiente());
			pstmt.setString (4, pond.isStatus()?"1":"0");
			
			
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
	
	
	public boolean Guardar_Actividad(Obj_Actividad actividad){
		int folio_transaccion=0;
		if(actividad.getNuevoModifica().equals("N")){
			 folio_transaccion=busca_y_actualiza_proximo_folio(73);
			 actividad.setFolio(folio_transaccion);	
		}
		String query = "exec cuadrantes_actividad_insert_y_actualiza ?,?,?,?,?,?,?,?,?,?,?,?,?,"+usuario.getFolio();
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			
			pstmt.setInt   (1, actividad.getFolio());
			pstmt.setString(2, actividad.getActividad());
			pstmt.setString(3, actividad.getDescripcion());
			pstmt.setString(4, actividad.getRespuesta());
			pstmt.setString(5, actividad.getAspecto());
			pstmt.setString(6, actividad.getNivel_Critico());
			pstmt.setString(7, actividad.getTemporada());
			pstmt.setString(8, actividad.getExige_Evidencia());
			pstmt.setString(9, actividad.getExige_Observacion());
			pstmt.setString(10, actividad.getEstatus());
			pstmt.setString(11, actividad.getNuevoModifica());
			pstmt.setString(12, actividad.getGenera_Alerta());
			pstmt.setInt   (13, actividad.getTolerancia_minutos());
			
			System.out.println(actividad.getTemporada());;
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Actividad ] "+query+" "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			
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
	
	public boolean Guardar_Mensaje_Personal(Obj_Mensaje_Personal MsjPersonal){
		String query = "exec sp_insert_mensaje ?,?,?,?,?,?,"+usuario.getFolio()+",?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setString (1, MsjPersonal.getFechaInicial().trim());
			pstmt.setString (2, MsjPersonal.getFechaFin().trim());
			pstmt.setString(3, MsjPersonal.getAsunto().toUpperCase().trim());
			pstmt.setString(4, MsjPersonal.getMensaje().trim());
			pstmt.setString(5, MsjPersonal.getRuta_imagen_mensaje().trim());
			pstmt.setString(6, MsjPersonal.getColor_fuente().trim());
			pstmt.setInt(7, (MsjPersonal.getStatus()));
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
	
	public boolean Guardar_Permiso_Checador(Obj_Alimentacion_De_Permisos_A_Empleados Permiso, int tiene_dia_dobla){
		String query = "exec sp_insert_permiso_checador ?,?,?,?,?,?,?,?,?,?,?,"+tiene_dia_dobla;
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setInt    ( 1, Permiso.getFolio_empleado());
			pstmt.setInt    ( 2, Permiso.getFolio_usuario());
			pstmt.setString ( 3, Permiso.getFecha());
			pstmt.setInt    ( 4, Permiso.getTipo_de_permiso());
			pstmt.setString ( 5, Permiso.getMotivo().toUpperCase().trim());
			pstmt.setInt    ( 6, (Permiso.isStatus())? 1: 0);
			pstmt.setInt    ( 7, Permiso.getDescanso());
			pstmt.setString ( 8, Permiso.getTiempo_comida());
			pstmt.setInt    ( 9, Permiso.getFolio_empleado_optener_turno());
			pstmt.setInt    (10, Permiso.getSolicito());
			pstmt.setString (11, Permiso.getEstablecimiento());
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Permiso_Checador ]   SQLException: sp_insert_permiso_checador "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Permiso_Checador ]   SQLException: sp_insert_permiso_checador "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Permiso_Checador ]   SQLException: sp_insert_permiso_checador "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Permiso_Checador ]   SQLException: sp_insert_permiso_checador "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	public boolean Guardar_Tabla_Nivel2(Obj_Nivel_Jerarquico pond,String[][] tabla){
		String queryDelete="delete from tb_tabla_nivel_jerarquico where tb_tabla_nivel_jerarquico.folio_tb_nivel_jerarquico = "+pond.getFolio();
		String query = "exec sp_insert_nivel_jerarquico ?,?";
		String querytabla="exec sp_insert_tabla_nivel_jerarquico ?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		PreparedStatement pstmtdelete = null;
		PreparedStatement pstmtabla =null;
		try {
			
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmtdelete = con.prepareStatement(queryDelete);
			pstmtabla=con.prepareStatement(querytabla);
			
			
			pstmtdelete.executeUpdate();
			
			pstmt.setString (1, pond.getDescripcion());
			pstmt.setInt (2, pond.getFolio_puesto_principal());
			
			for (int i = 0; i < tabla.length; i++) {
				pstmtabla.setInt (1, pond.getFolio());
				pstmtabla.setInt (2, Integer.valueOf(tabla[i][0].trim()));
				pstmtabla.setString (3, tabla[i][2]);
				pstmtabla.executeUpdate();
			}
			
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Tabla_Nivel2 ]   SQLException: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
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
	
	public boolean Guardar_Empleado_Msj(Obj_Mensaje_Personal Empleado_Msj,String[] tabla){
//		String query = "exec sp_insert_nivel_jerarquico ?,?";
		String querytabla="exec sp_insert_tabla_empleado_mensaje ?,?";
		Connection con = new Connexion().conexion();
//		PreparedStatement pstmt = null;
		PreparedStatement pstmtabla =null;
		try {
			
			con.setAutoCommit(false);
//			pstmt = con.prepareStatement(query);
			pstmtabla=con.prepareStatement(querytabla);
			
			
			for (int i = 0; i < tabla.length; i++) {

				pstmtabla.setInt (1, Empleado_Msj.getFolioMensaje());
				pstmtabla.setString (2, tabla[i]);
				
				pstmtabla.executeUpdate();
				
			}
//			pstmt.executeUpdate();
		
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
	
	public boolean Guardar_Sesion(Obj_Usuario usuario){
		BufferedWriter bufferedWriter = null;
		String nomArchivo        = System.getProperty("user.dir")+"\\Config\\users";
		try{
			File archivo = new File(nomArchivo);
			
			if(archivo.exists()){
				bufferedWriter = new BufferedWriter (new FileWriter(nomArchivo));
				bufferedWriter.write(usuario.getFolio()+    		"\n");
				bufferedWriter.write(usuario.getNombre_completo()+	"\n");
				bufferedWriter.write(usuario.getRFuente()+	"\n");
				bufferedWriter.write(usuario.getGFuente()+	"\n");
				bufferedWriter.write(usuario.getBFuente()+	"\n");
				bufferedWriter.write(usuario.getRFuenteS()+	"\n");
				bufferedWriter.write(usuario.getGFuenteS()+	"\n");
				bufferedWriter.write(usuario.getBFuenteS()+	"\n");
				bufferedWriter.write(usuario.getRfila()+	"\n");
				bufferedWriter.write(usuario.getGfila()+	"\n");
				bufferedWriter.write(usuario.getBfila()+	"\n");
				bufferedWriter.write(usuario.getRfilaS()+	"\n");
				bufferedWriter.write(usuario.getGfilaS()+	"\n");
				bufferedWriter.write(usuario.getBfilaS()+	"\n");
				bufferedWriter.write(usuario.getTamanio_fuente()+	"\n");
				
			}else{
				File folder = new File(System.getProperty("user.dir")+"\\Config");
				folder.mkdirs();
				archivo.createNewFile();
				bufferedWriter = new BufferedWriter (new FileWriter(nomArchivo));
				bufferedWriter.write(usuario.getFolio()+    		"\n");
				bufferedWriter.write(usuario.getNombre_completo()+	"\n");
				bufferedWriter.write(usuario.getRFuente()+	"\n");
				bufferedWriter.write(usuario.getGFuente()+	"\n");
				bufferedWriter.write(usuario.getBFuente()+	"\n");
				bufferedWriter.write(usuario.getRFuenteS()+	"\n");
				bufferedWriter.write(usuario.getGFuenteS()+	"\n");
				bufferedWriter.write(usuario.getBFuenteS()+	"\n");
				bufferedWriter.write(usuario.getRfila()+	"\n");
				bufferedWriter.write(usuario.getGfila()+	"\n");
				bufferedWriter.write(usuario.getBfila()+	"\n");
				bufferedWriter.write(usuario.getRfilaS()+	"\n");
				bufferedWriter.write(usuario.getGfilaS()+	"\n");
				bufferedWriter.write(usuario.getBfilaS()+	"\n");
				bufferedWriter.write(usuario.getTamanio_fuente()+	"\n");
			}
			
		}
		catch(FileNotFoundException ex)
		{
			ex.printStackTrace();
		}catch(IOException ex)
		{
			ex.printStackTrace();
		}finally
		{
			try
			{
				if(bufferedWriter!=null)
				{
					bufferedWriter.flush();
					bufferedWriter.close();
				}
			}catch(IOException ex)
			{
				ex.printStackTrace();
			}
		}return true;
	}

	public boolean Guardar_Mensajes(Obj_Mensajes mensaje){
			
			String query = "insert into tb_mensajes(mensaje)values(?)";
			
			Connection con = new Connexion().conexion();
			PreparedStatement pstmt = null;
			try {
				con.setAutoCommit(false);
				pstmt = con.prepareStatement(query);
				pstmt.setString(1, mensaje.getMensaje().toUpperCase());
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
	
		
	public boolean Insert_Empleado(int folio,String t_entrada){
		
		String insert ="exec sp_insert_entosal "+folio+",?,?,?;";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		
		try{
			String pc_nombre = InetAddress.getLocalHost().getHostName();
			String pc_ip = InetAddress.getLocalHost().getHostAddress();
			
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(insert);
			
			
			pstmt.setString(1, pc_nombre);
			pstmt.setString(2, pc_ip);
			pstmt.setString(3, t_entrada);
			
			pstmt.executeUpdate();
			con.commit();
			
		}catch (Exception e) {
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
		}
		return true;
	}
	
	public boolean Insert_Empleado_Comida(int folio,String t_entrada){
		
		String insert ="exec sp_insert_entosal "+folio+",?,?,?,?;";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		System.out.println(insert);
		try{
			String pc_nombre = InetAddress.getLocalHost().getHostName();
			String pc_ip = InetAddress.getLocalHost().getHostAddress();
			
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(insert);
			
			
			pstmt.setString(1, pc_nombre);
			pstmt.setString(2, pc_ip);
			pstmt.setString(3, t_entrada);
			
			pstmt.executeUpdate();
			con.commit();
			
		}catch (Exception e) {
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
		}
		return true;
	}
	
//	public boolean Guardar_Checador(Obj_Checador chec){
//		String query = "exec sp_insert_checador ?,?,?,?,?,?";
//		Connection con = new Connexion().conexion();
//		PreparedStatement pstmt = null;
//		try {
//			con.setAutoCommit(false);
//			pstmt = con.prepareStatement(query);
//			pstmt.setInt(1,chec.getFolio_emp());
//			pstmt.setInt(2, chec.getClave());
//			pstmt.setString(3, chec.getEntrada());
//			pstmt.setString(4, chec.getSalida());
//			pstmt.setString(5, chec.getEntrada2());
//			pstmt.setString(6, chec.getSalida2());
//		 	pstmt.executeUpdate();
//		 	con.commit();
//		 	
//		} catch (Exception e) {
//			System.out.println("SQLException: "+e.getMessage());
//			if(con != null){
//				try{
//					System.out.println("La transacción ha sido abortada");
//					con.rollback();
//				}catch(SQLException ex){
//					System.out.println(ex.getMessage());
//				}
//			}
//			return false;
//		}finally{
//			try {
//				con.close();
//			} catch(SQLException e){
//				e.printStackTrace();
//			}
//		}		
//		return true;
//	}
	
public boolean Guardar_Asignacion_mensajes(Obj_Asignacion_Mensajes mensj){
		
		String query ="insert into tb_asignacion_mensaje(mensaje,mensajearea,puesto,equipo,jefatura,empleado,rbpuesto,rbequipo,rbjefatura,rbempleado)values(?,?,?,?,?,?,?,?,?,?)";
		
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, mensj.getNo_mensajes());
			pstmt.setString(2, mensj.getMensaje());
			pstmt.setString(3,mensj.getPuesto() );
			pstmt.setString(4,mensj.getEquipo() );
			pstmt.setString(5,mensj.getJefatura());
			pstmt.setString(6,mensj.getEmpleado());
			
			pstmt.setBoolean(7, mensj.getRbpuesto());
			pstmt.setBoolean(8, mensj.getRbequipo());
			pstmt.setBoolean(9, mensj.getRbjefatura());
			pstmt.setBoolean(10, mensj.getRbempleado());
			
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





//Guardamos el horario
public boolean Guardar_Horario(Obj_Horarios horario,int folio_turno){
	String query = "exec sp_insert_horarios_2 ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?";
	Connection con = new Connexion().conexion();
	PreparedStatement pstmt = null;
	try {
		con.setAutoCommit(false);
		pstmt = con.prepareStatement(query);
		int i=1;
		pstmt.setString(i, horario.getNombre().trim());
		
		pstmt.setString(i+=1, "Domingo");
		pstmt.setString(i+=1, horario.getDomingo1());
		pstmt.setString(i+=1, horario.getDomingo2());
		pstmt.setString(i+=1, horario.getDomingo3());
		pstmt.setString(i+=1, horario.getDomingo4());
		pstmt.setString(i+=1, horario.getDomingo5());
		
		pstmt.setString(i+=1, "Lunes");
		pstmt.setString(i+=1, horario.getLunes1());
		pstmt.setString(i+=1, horario.getLunes2());
		pstmt.setString(i+=1, horario.getLunes3());
		pstmt.setString(i+=1, horario.getLunes4());
		pstmt.setString(i+=1, horario.getLunes5());
		
		pstmt.setString(i+=1, "Martes");
		pstmt.setString(i+=1, horario.getMartes1());
		pstmt.setString(i+=1, horario.getMartes2());
		pstmt.setString(i+=1, horario.getMartes3());
		pstmt.setString(i+=1, horario.getMartes4());
		pstmt.setString(i+=1, horario.getMartes5());
		
		pstmt.setString(i+=1, "Miércoles");
		pstmt.setString(i+=1, horario.getMiercoles1());
		pstmt.setString(i+=1, horario.getMiercoles2());
		pstmt.setString(i+=1, horario.getMiercoles3());
		pstmt.setString(i+=1, horario.getMiercoles4());
		pstmt.setString(i+=1, horario.getMiercoles5());
		
		pstmt.setString(i+=1, "Jueves");
		pstmt.setString(i+=1, horario.getJueves1());
		pstmt.setString(i+=1, horario.getJueves2());
		pstmt.setString(i+=1, horario.getJueves3());
		pstmt.setString(i+=1, horario.getJueves4());
		pstmt.setString(i+=1, horario.getJueves5());
		
		pstmt.setString(i+=1, "Viernes");
		pstmt.setString(i+=1, horario.getViernes1());
		pstmt.setString(i+=1, horario.getViernes2());
		pstmt.setString(i+=1, horario.getViernes3());
		pstmt.setString(i+=1, horario.getViernes4());
		pstmt.setString(i+=1, horario.getViernes5());
		
		pstmt.setString(i+=1, "Sábado");
		pstmt.setString(i+=1, horario.getSabado1());
		pstmt.setString(i+=1, horario.getSabado2());
		pstmt.setString(i+=1, horario.getSabado3());
		pstmt.setString(i+=1, horario.getSabado4());
		pstmt.setString(i+=1, horario.getSabado5());
		
		pstmt.setInt(i+=1, horario.getDescanso());
		pstmt.setInt(i+=1, horario.getDiaDobla());
		pstmt.setInt(i+=1, horario.getDiaDobla2());
		pstmt.setInt(i+=1, horario.getDiaDobla3());
		pstmt.setInt(i+=1, horario.getRecesoDiarioExtra());
		pstmt.setInt(i+=1, horario.getHorarioDeposito());
		pstmt.setInt(i+=1, folio_turno);

		pstmt.executeUpdate();
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

	public boolean Insert_Checada(int folio, String t_entrada, int tipo_salida_comer) {
		String insert ="exec checador_insert_entosal "+folio+",?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		
		String pc_nombre = "";
		String pc_ip = "";
		
		try{
			pc_nombre = InetAddress.getLocalHost().getHostName();
			pc_ip = InetAddress.getLocalHost().getHostAddress();
			
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(insert);
			
			
			pstmt.setString(1, pc_nombre);
			pstmt.setString(2, pc_ip);
			pstmt.setString(3, t_entrada);
			pstmt.setInt(4, tipo_salida_comer);
			
			pstmt.executeUpdate();
			con.commit();
			
		}catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la Funcion Insert_Empleado_Comida \n Procedimiento Almacenado: "+insert+" \n Parametros Enviados:"
			                             +"\nfolio Empleado:"+folio
			                             +"\npc_nombre:"+pc_nombre
			                             +"\npc_ip:"+pc_ip 
			                             +"\nt_entrada:"+t_entrada
			                             +"\ntipo_salida_comer:"+tipo_salida_comer
			                             +"\nSql:"+e.getMessage(), "Avisa al Administrador del Sistema", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen/usuario-icono-eliminar5252-64.png"));
			if (con != null){
				try {
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				} catch(SQLException ex) {
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion Insert_Empleado_Comida  sp sp_insert_entosal "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);

				}
			} 
			return false;
		}
		return true;
	}
		
		//Guardar captura fuente de sodas
		public boolean Guardar_Fuente_Sodas(Obj_Captura_Fuente_Sodas sodas){
			String query = "exec sp_insert_captura_fuente_sodas ?,?,?,?,?,?,?,?,?";
			Connection con = new Connexion().conexion();
			PreparedStatement pstmt = null;
			try {
				con.setAutoCommit(false);
				pstmt = con.prepareStatement(query);
				
				String pc_nombre =	InetAddress.getLocalHost().getHostName();
				String pc_ip = 		InetAddress.getLocalHost().getHostAddress();
				
				pstmt.setString(1, sodas.getClave().toUpperCase().trim());
				pstmt.setString(2, sodas.getEstablecimiento().toUpperCase().trim());
				pstmt.setString(3, sodas.getPuesto().toUpperCase().trim());
				pstmt.setString(4, sodas.getTicket().toUpperCase().trim());
				pstmt.setFloat(5, sodas.getImporte());
				pstmt.setString(6, sodas.getUsuario().trim());
				pstmt.setString(7, pc_nombre);
				pstmt.setString(8, pc_ip);
				pstmt.setInt(9, 1);			//status default al guardar usado para revision de auxilizr y finanazas
//				pstmt.setInt(10, 1);			//status default al guardar usado para revisicon de desarrollo humano
				
				pstmt.executeUpdate();
				con.commit();
			} catch (Exception e) {
				System.out.println("SQLException: " + e.getMessage());
				if (con != null){
					try {
						System.out.println("La transacción ha sido abortada");
						JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion Guardar_Fuente_Sodas  procedimiento almacenado sp_insert_captura_fuente_sodas SQLException: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
						con.rollback();
					} catch(SQLException ex) {
						System.out.println(ex.getMessage());
						JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion Guardar_Fuente_Sodas  procedimiento almacenado sp_insert_captura_fuente_sodas SQLException: "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
					}
				} 
				return false;
			}finally{
				try {
					pstmt.close();
					con.close();
				} catch(SQLException e){
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion Guardar_Fuente_Sodas  procedimiento almacenado sp_insert_captura_fuente_sodas SQLException: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
			}		
			return true;
		}
	
		public boolean buscarBorrarPDependiente(int folio_puesto_dependiente, int folio,String establecimineto){
			String query = "exec sp_folio_puesto_dependiente "+folio_puesto_dependiente+", "+folio+",'"+establecimineto+"'";
			Connection con = new Connexion().conexion();
			PreparedStatement pstmt = null;
			try {
				
				con.setAutoCommit(false);
				pstmt = con.prepareStatement(query);
				
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
		
//		borrar alimentacion por denominaciones
		public boolean Borrar_Corte_completo(String folio_corte){
			String query = "exec sp_delete_folio_corte '"+folio_corte+"';";
			Connection con = new Connexion().conexion();
			PreparedStatement pstmt = null;
			
			try {

				con.setAutoCommit(false);
				pstmt = con.prepareStatement(query);
				pstmt.executeUpdate();
				con.commit();
				
			} catch (Exception e) {
				System.out.println("SQLException: "+e.getMessage());
				if(con != null){
					try{
						System.out.println("La transacción ha sido abortada");
						JOptionPane.showMessageDialog(null, "Error en cancelacion  en la funcion [ Borrar_Corte_completo ]   SQLException: sp_delete_folio_corte "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);

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
		
		public boolean Guardar_Solicitud(Obj_Solicitud_De_Empleados solicitud){
			String query = "exec sp_insert_solicitud_empleado ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?";
			Connection con = new Connexion().conexion();
			PreparedStatement pstmt = null;
			try {
				
				con.setAutoCommit(false);
				pstmt = con.prepareStatement(query);

				String pc_nombre = InetAddress.getLocalHost().getHostName();
				String pc_ip = InetAddress.getLocalHost().getHostAddress();
			
				int i=1;
				pstmt.setInt (i,solicitud.getFolio_empleado());
				pstmt.setString (i+=1,solicitud.getUsuario().toLowerCase().trim());
				pstmt.setInt (i+=1,solicitud.getFolio_permiso());
				pstmt.setInt (i+=1,solicitud.getFolio_solicitud());
				pstmt.setInt (i+=1,solicitud.getFolio_cambio());
				pstmt.setString (i+=1,solicitud.getCambio_a());
				pstmt.setString (i+=1,solicitud.getFecha_solicitada());
				pstmt.setInt (i+=1,solicitud.getTemp_fijo());
				pstmt.setFloat (i+=1,solicitud.getCantidad_solicitada());
				pstmt.setInt (i+=1,solicitud.getPuntualidad_y_asistencia());
				pstmt.setInt (i+=1,solicitud.getCumplimiento_de_tareas());
				pstmt.setInt (i+=1,solicitud.getDiciplina());
				pstmt.setInt (i+=1,solicitud.getRespeto_y_trato_general());
				pstmt.setString (i+=1,solicitud.getMotivo().toUpperCase());
				pstmt.setString (i+=1,pc_nombre);
				pstmt.setString (i+=1,pc_ip);
				
				pstmt.execute();
				con.commit();
			} catch (Exception e) {
				System.out.println("SQLException: " + e.getMessage());
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Solicitud ]   SQLException: sp_insert_solicitud_empleado "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				if (con != null){
					try {
						System.out.println("La transacción ha sido abortada");
						JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Solicitud ]   SQLException: sp_insert_solicitud_empleado "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
						con.rollback();
					} catch(SQLException ex) {
						System.out.println(ex.getMessage());
						JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Solicitud ]   SQLException: sp_insert_solicitud_empleado "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
					}
				} 
				return false;
			}finally{
				try {
					pstmt.close();
					con.close();
				} catch(SQLException e){
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Solicitud ]   SQLException: sp_insert_solicitud_empleado "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
			}		
			return true;
		}
		
	public boolean Guardar_Grupo_De_Vacaciones(Obj_Grupo_De_Vacaciones grupo){
		String query = "exec sp_insert_grupo_de_vacaciones ?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, grupo.getFolio());
			pstmt.setString(2, grupo.getDescripcion().toUpperCase());
			pstmt.setString(3, (grupo.isStatus())?"1":"0");
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Grupo_De_Vacaciones ]   SQLException: sp_insert_grupo_de_vacaciones "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Grupo_De_Vacaciones ]   SQLException: sp_insert_grupo_de_vacaciones "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Grupo_De_Vacaciones ]   SQLException: sp_insert_grupo_de_vacaciones "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Grupo_De_Vacaciones ]   SQLException: sp_insert_grupo_de_vacaciones "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			}
		}		
		return true;
	}
	
	public boolean Guardar_Grupo_De_Vacaciones(Obj_Tabla_De_Vacaciones grupo_vacaciones){
//		cambiar procedimiento ( crear uno nuevo sp )
		String query = "exec sp_insert_tabla_grupos_de_vacaciones ?,?,?,?";
		
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);

			int i=1;
			pstmt = con.prepareStatement(query);
			pstmt.setString(i,		grupo_vacaciones.getGrupo().toUpperCase().trim());
			pstmt.setInt(i+=1, 	grupo_vacaciones.getAnios_trabajados());
			pstmt.setInt(i+=1,	grupo_vacaciones.getDias_correspondientes());
			pstmt.setInt(i+=1,	grupo_vacaciones.getPrima_vacacional());
			
			pstmt.executeUpdate();
			con.commit();
			
		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Grupo_De_Vacaciones ]   SQLException: sp_insert_tabla_grupos_de_vacaciones "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			if (con != null){
				try {
					System.out.println("La transacción ha sido abortada");
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Grupo_De_Vacaciones ]   SQLException: sp_insert_tabla_grupos_de_vacaciones "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
					con.rollback();
				} catch(SQLException ex) {
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Grupo_De_Vacaciones ]   SQLException: sp_insert_tabla_grupos_de_vacaciones "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
			} 
			return false;
		}finally{
			try {
				pstmt.close();
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Grupo_De_Vacaciones ]   SQLException: sp_insert_tabla_grupos_de_vacaciones "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			}
		}		
		return true;
	}
	
	public boolean Remover_Grupo_De_Vacaciones(Obj_Tabla_De_Vacaciones grupo_vacaciones){

		String query = "exec sp_delete_tabla_grupos_de_vacaciones ?,?,?,?";
		
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);

			int i=1;
			pstmt = con.prepareStatement(query);
			pstmt.setString(i,		grupo_vacaciones.getGrupo().toUpperCase().trim());
			pstmt.setInt(i+=1, 	grupo_vacaciones.getAnios_trabajados());
			pstmt.setInt(i+=1,	grupo_vacaciones.getDias_correspondientes());
			pstmt.setInt(i+=1,	grupo_vacaciones.getPrima_vacacional());
			
			pstmt.executeUpdate();
			con.commit();
			
		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Remover_Grupo_De_Vacaciones ]   SQLException: sp_delete_tabla_grupos_de_vacaciones "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			if (con != null){
				try {
					System.out.println("La transacción ha sido abortada");
					con.rollback();
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Remover_Grupo_De_Vacaciones ]   SQLException: sp_delete_tabla_grupos_de_vacaciones "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				} catch(SQLException ex) {
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Remover_Grupo_De_Vacaciones ]   SQLException: sp_delete_tabla_grupos_de_vacaciones "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
			} 
			return false;
		}finally{
			try {
				pstmt.close();
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Remover_Grupo_De_Vacaciones ]   SQLException: sp_delete_tabla_grupos_de_vacaciones "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			}
		}		
		return true;
	}
	
	public boolean Guardar_Vacaciones_Pasadas(Obj_Alimentacion_De_Vacaciones alimentacion){
		String query = "exec sp_insert_alimentacion_de_ultimas_vacaciones ?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, alimentacion.getFolio_empleado());
			pstmt.setString(2, alimentacion.getFecha_inicio());
			pstmt.setString(3, alimentacion.getFecha_final());
			pstmt.setInt(4, alimentacion.getAnios_a_disfrutar());
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Vacaciones_Pasadas ]   SQLException: sp_insert_alimentacion_de_ultimas_vacaciones "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Vacaciones_Pasadas ]   SQLException: sp_insert_alimentacion_de_ultimas_vacaciones "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Vacaciones_Pasadas ]   SQLException: sp_insert_alimentacion_de_ultimas_vacaciones "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
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
	
//	public boolean Guardar_Vacaciones_Calculadas(Obj_Alimentacion_De_Vacaciones alimentacion){
////		cambiar procedimiento, agregar todos los campos
//		String query = "exec sp_insert_alimentacion_de_vacaciones_calculadas ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? ";
//		Connection con = new Connexion().conexion();
//		PreparedStatement pstmt = null;
//		Obj_Usuario usuario = new Obj_Usuario().LeerSession();
//		
//		int i=1;
//		try {
//			con.setAutoCommit(false);
//			pstmt = con.prepareStatement(query);
//			pstmt.setInt			(i, 	alimentacion.getFolio_empleado());
//			pstmt.setString			(i+=1, alimentacion.getFecha_inicio());
//			pstmt.setString			(i+=1, alimentacion.getFecha_final());
//			pstmt.setInt			(i+=1, alimentacion.getAnios_a_disfrutar());
//			pstmt.setFloat			(i+=1, alimentacion.getVacaciones());
//			pstmt.setFloat			(i+=1, alimentacion.getPrima_vacacional());
//			pstmt.setFloat			(i+=1, alimentacion.getInfonavit());
//			pstmt.setFloat			(i+=1, alimentacion.getSueldo_semana());
//			pstmt.setFloat			(i+=1, alimentacion.getCorte_de_caja());
//			pstmt.setFloat			(i+=1, alimentacion.getFuente_de_sodas());
//			pstmt.setFloat			(i+=1, alimentacion.getPrestamo());
//			pstmt.setFloat			(i+=1, alimentacion.getPension_alimenticia());
//			pstmt.setFloat          (i+=1, alimentacion.getDias_descanso_vacaciones()); 
//			pstmt.setFloat			(i+=1, alimentacion.getTotal());
//			pstmt.setInt			(i+=1, alimentacion.isStatus()?1:0);
//			pstmt.setFloat          (i+=1, alimentacion.getVacaciones_c());
//			pstmt.setFloat          (i+=1, alimentacion.getPrima_vacacional_c());
//			pstmt.setFloat          (i+=1, alimentacion.getSueldo_semana_c());
//			pstmt.setFloat          (i+=1, alimentacion.getGratificacion()); 
//			pstmt.setInt            (i+=1, usuario.getFolio());
//			
//			
//			pstmt.executeUpdate();
//			con.commit();
//		} catch (Exception e) {
//			System.out.println("SQLException: "+e.getMessage());
//			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Vacaciones_Calculadas ]   SQLException: sp_insert_alimentacion_de_vacaciones_calculadas "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
//			if(con != null){
//				try{
//					System.out.println("La transacción ha sido abortada");
//					con.rollback();
//					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Vacaciones_Calculadas ]   SQLException: sp_insert_alimentacion_de_vacaciones_calculadas "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
//				}catch(SQLException ex){
//					System.out.println(ex.getMessage());
//					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Vacaciones_Calculadas ]   SQLException: sp_insert_alimentacion_de_vacaciones_calculadas "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
//				}
//			}
//			return false;
//		}finally{
//			try {
//				con.close();
//			} catch(SQLException e){
//				e.printStackTrace();
//			}
//		}		
//		return true;
//	}
	
	public boolean Guardar_Control_Factura_xml(Obj_Proveedores fact_xml_proveedores){
//		cambiar procedimiento, agregar todos los campos
		String query = "exec sp_insert_factura_para_control_xml ?,?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		Obj_Usuario usuario = new Obj_Usuario().LeerSession();
		
		int i=1;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setString			(i,    fact_xml_proveedores.getFolio_factura());
			pstmt.setString			(i+=1, fact_xml_proveedores.getFecha());
			pstmt.setString			(i+=1, fact_xml_proveedores.getCod_prv());
			pstmt.setString			(i+=1, fact_xml_proveedores.getProveedor());
			pstmt.setInt            (i+=1, usuario.getFolio());
			
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Control_Factura_xml ]   SQLException: sp_insert_factura_para_control_xml "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Control_Factura_xml ]   SQLException: sp_insert_factura_para_control_xml "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Control_Factura_xml ]   SQLException: sp_insert_factura_para_control_xml "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
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

	public boolean Guardar_Asignacion_De_Cajero(Obj_Movimiento_De_Asignacion movimiento){
//		crear procedimiento almacenado con el siguiente nombre
		String query = "exec sp_insert_asignacion_de_cajeros ?,?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			
			int i=1;
			pstmt.setInt(i, 	  movimiento.getFolio_empleado());
			pstmt.setString(i+=1, movimiento.getAsignacion());
			pstmt.setString(i+=1, movimiento.getEstablecimiento());
			pstmt.setString(i+=1, movimiento.getFechaIn());
			pstmt.setString(i+=1, movimiento.getFechaFin());
			
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Asignacion_De_Cajero ] Insert  SQLException: sp_insert_asignacion_de_cajeros "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Asignacion_De_Cajero ] Insert  SQLException: sp_insert_asignacion_de_cajeros "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Asignacion_De_Cajero ] Insert  SQLException: sp_insert_asignacion_de_cajeros "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			}
		}		
		return true;
	}
	
	public boolean Guardar_Cliente(Obj_Clientes cliente){
		String query = "exec sp_insert_cliente ?,?,?,?,?";
		
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			
//			// insert bitacora
//			String pc = InetAddress.getLocalHost().getHostName();
//			String ip = InetAddress.getLocalHost().getHostAddress();
//			pstmtb = con.prepareStatement(Qbitacora);
//			pstmtb.setString(1, pc);
//			pstmtb.setString(2, ip);
//			pstmtb.setInt(3, usuario.getFolio());
//			pstmtb.setString(4, "sp_insert_cliente alta "+cliente.getNombre().toUpperCase()+cliente.getAp_paterno().toUpperCase()+cliente.getAp_materno().toUpperCase());
//			pstmtb.setString(5, "Cliente Nuevo");
//			pstmtb.executeUpdate();
//			
			
//			private String telefono_cuadrante;
			int i=1;
			pstmt = con.prepareStatement(query);
			pstmt.setString(i,	 	cliente.getNombre().toUpperCase());
			pstmt.setString(i+=1,	cliente.getAp_paterno().toUpperCase());
			pstmt.setString(i+=1,	cliente.getAp_materno().toUpperCase());
			pstmt.setString(i+=1,	cliente.getDireccion().toUpperCase());
			pstmt.setString(i+=1, 	cliente.getTelefono().toUpperCase());
			
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Cliente ] Insert  SQLException: sp_insert_cliente "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			if (con != null){
				try {
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				} catch(SQLException ex) {
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Empleado ] Insert  SQLException: sp_insert_empleado "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
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
	
	public boolean Guardar_Etapa(Obj_Etapas etapa){
		int folio_transaccion=busca_y_actualiza_proximo_folio(26);
		String query = "exec sp_insert_etapa ?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			
			pstmt.setInt(1, folio_transaccion);
			pstmt.setString(2, etapa.getEtapa().toUpperCase().trim());
			pstmt.setString(3, etapa.getAbreviatura().toUpperCase().trim());
			pstmt.setInt(4, etapa.getStatus());
			
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Etapa ] Insert  SQLException: insert_etapa "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Etapa ] Insert  SQLException: insert_etapa "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Etapa ] Insert  SQLException: insert_etapa "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			}
		}		
		return true;
	}
	
	public boolean Guardar_Aspecto_de_la_Etapa(Obj_Aspectos_De_La_Etapa aspecto){
		int folio_transaccion=busca_y_actualiza_proximo_folio(25);
		String query = "exec sp_insert_aspectos_de_la_etapa ?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			
			pstmt.setInt(1, folio_transaccion);
			pstmt.setString(2, aspecto.getAspecto().toUpperCase().trim());
			pstmt.setString(3, aspecto.getAbreviatura().toUpperCase().trim());
			pstmt.setInt(4, aspecto.getStatus());
			
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Aspecto_de_la_Etapa ] Insert  SQLException: insert_etapa "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Aspecto_de_la_Etapa ] Insert  SQLException: insert_etapa "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Aspecto_de_la_Etapa ] Insert  SQLException: insert_etapa "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			}
		}		
		return true;
	}
	
	public boolean Guardar_Unidad_de_Inspeccion(Obj_Unidades_de_Inspeccion unidad_de_inspeccion){
		String query = "exec sp_insert_unidades_de_inspeccion ?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			
			pstmt.setInt(1, unidad_de_inspeccion.getFolio());
			pstmt.setString(2, unidad_de_inspeccion.getunidades_de_inspeccion().toUpperCase().trim());
			pstmt.setString(3, unidad_de_inspeccion.getAbreviatura().toUpperCase().trim());
			pstmt.setInt(4, unidad_de_inspeccion.getStatus());
			
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Unidad_de_Inspeccion ] Insert  SQLException: sp_insert_unidades_de_inspeccion "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Unidad_de_Inspeccion ] Insert  SQLException: sp_insert_unidades_de_inspeccion "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Unidad_de_Inspeccion ] Insert  SQLException: sp_insert_unidades_de_inspeccion "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			}
		}		
		return true;
	}
			
			
			
	public boolean Guardar_Ticket(Obj_Abono_Clientes movimiento){
				
		String query = "exec sp_insert_abono_c_ahorro_cliente ?,?,?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			
			int i=1;

			
			pstmt.setString(i, movimiento.getTicket());
			pstmt.setDouble(i+=1, movimiento.getAbono());
			pstmt.setString(i+=1, movimiento.getEstablecimiento());
			pstmt.setString(i+=1, movimiento.getCajero());
			pstmt.setString(i+=1, movimiento.getFecha_fin());
			pstmt.setInt(i+=1, movimiento.getFolio_cliente());
			
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Ticket ] Insert  SQLException: sp_insert_abono_c_ahorro_cliente "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Ticket ] Insert  SQLException: sp_insert_abono_c_ahorro_cliente "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Ticket ] Insert  SQLException: sp_insert_abono_c_ahorro_cliente "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			}
		}		
		return true;
	}
	
	public boolean Guardar_Folio_Corte(){
		String query = "exec sp_insert_folio_corte_nuevo";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Folio_Corte ] Insert  SQLException: sp_insert_folio_corte_nuevo "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Folio_Corte ] Insert  SQLException: sp_insert_folio_corte_nuevo "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Asignacion_De_Cajero ] Insert  SQLException: sp_insert_asignacion_de_cajeros "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			}
		}		
		return true;
	}
	
	public void Reporte_De_Retiros_Cajeros(String folio_retiro) {
		String reporte = "Obj_Reporte_De_Retiro_A_Cajeros.jrxml";
		String comando = "exec sp_Reporte_De_Retiros_A_Cajeros '"+folio_retiro+"';";
   	    new Generacion_Reportes().Reporte_chico(reporte, comando, "2.26", "si",1);
	 }
	
	public boolean Guardar_Retiro_Cajero(String Establecimiento,int Folio_empleado,int folio_supervisor,float importe_retiro,String Asignacion){
		int folio=busca_y_actualiza_proximo_folio(1);
		 Connection con = new Connexion().conexion();
	  	 PreparedStatement pstmt = null;
	  	 
		    String folio_retiro =""	;	
			String folio_qry = " select serie +(convert(varchar(20),"+folio+")) from tb_establecimiento where nombre='"+Establecimiento.trim()+"'";
	        Statement stmtfolio = null;

	  	 try {
		 	stmtfolio = con.createStatement();
			ResultSet rs = stmtfolio.executeQuery(folio_qry);
			while(rs.next()){
						folio_retiro=rs.getString(1);
			}
		 } catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Error en Buscar  en la funcion Guardar_Retiro_Cajero \n  en "+folio_qry+"\n SQLException: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
				return false;
		 }
			Obj_Retiros_Cajeros Folio_Retiro_Devolver= new Obj_Retiros_Cajeros();
			Folio_Retiro_Devolver.setFolio_retiro(folio_retiro);
	 
			String query = "exec retiros_a_cajeros_insert ?,?,?,?,?,'"+Establecimiento.trim()+"','"+Asignacion+"'";

		try {
			 con.setAutoCommit(false);
			 pstmt = con.prepareStatement(query);
	  		 pstmt.setInt(1, Folio_empleado);
			 pstmt.setInt(2, folio_supervisor);
			 pstmt.setFloat(3, importe_retiro);
			 pstmt.setInt(4,0 );
			 pstmt.setString(5, folio_retiro);
			 pstmt.executeUpdate();
			 
			con.commit();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Retiro_Cajero ] "+query+" "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
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
		Reporte_De_Retiros_Cajeros(folio_retiro);
		return true;
	}
	
public String Guardar_Sesion_Cajero(String Establecimiento,int Folio_empleado){

	String Guardo_Sesion =""	;
	
		Connection con = new Connexion().conexion();
		
				 String pc_nombre="";
				   String ip="";
								try { 	pc_nombre = InetAddress.getLocalHost().getHostName();
								    	ip = InetAddress.getLocalHost().getHostAddress();
								} catch (UnknownHostException e1) {
									e1.printStackTrace();
									JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion Guardar_Sesion_Cajero \n no se pudo obtener el nombre de la pc "+e1.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
								}
								
		String query = " exec sp_insert_sesion_retiro_cajero '"+pc_nombre+"','"+ip+"','"+Establecimiento.trim()+"',"+Folio_empleado+"";
	  	 PreparedStatement pstmt = null;	
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException:Guardar_Retiro_Cajero " + e.getMessage());
			if (con != null){
				try {
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				} catch(SQLException ex) {
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Sesion_Cajero ] Insert  SQLException: sp_insert_sesion_retiro_cajero "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
			} 
			return Guardo_Sesion="Error en GuardarSQL";
		}finally{
			try {
				pstmt.close();
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Sesion_Cajero ] Insert  SQLException: sp_insert_sesion_retiro_cajero "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			}
		}		
		return Guardo_Sesion;
	}

	public boolean Guardar_Cotizacion_Producto(Obj_Cotizaciones_De_Un_Producto Cotizacion_Producto){
			String query = "exec sp_insert_cotizacion_de_un_productos_en_proveedores ?,?,?,?,?,?,?,?,?,?,?,? ";
			Connection con = new Connexion().conexion_IZAGAR();
			PreparedStatement pstmt = null;
			try {
				con.setAutoCommit(false);
				pstmt = con.prepareStatement(query);
				
				pstmt.setString(1, Cotizacion_Producto.getCod_Prod().toUpperCase().trim());
				pstmt.setString(2, Cotizacion_Producto.getFolio_compra().toUpperCase().trim());
		        pstmt.setString(3, Cotizacion_Producto.getCod_Prv().toUpperCase().trim());
		        pstmt.setString(4, Cotizacion_Producto.getProveedor().toUpperCase().trim());
		        pstmt.setDouble(5,  Cotizacion_Producto.getUltimo_Costo());  
		        pstmt.setDouble(6,  Cotizacion_Producto.getCosto_Promedio());
		        pstmt.setDouble(7,  Cotizacion_Producto.getCosto_Nuevo());
		        pstmt.setDouble(8, Cotizacion_Producto.getCantidad_Requerida());
		        pstmt.setString(9, Cotizacion_Producto.getNotas_Negociacion());
		        pstmt.setDouble(10, Cotizacion_Producto.getExistencia_Cedis());
		        pstmt.setDouble(11, Cotizacion_Producto.getExistencia_Total());
		        pstmt.setString(12, usuario.getNombre_completo());
		        		
				pstmt.executeUpdate();
				con.commit();
			} catch (Exception e) {
				System.out.println("SQLException: " + e.getMessage());
				if (con != null){
					try {
						System.out.println("La transacción ha sido abortada");
						con.rollback();
					} catch(SQLException ex) {
						System.out.println(ex.getMessage());
						JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Cotizacion_Producto ] Insert  SQLException: sp_insert_cotizacion_de_un_productos_en_proveedores "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
					}
				} 
				return false;
			}finally{
				try {
					pstmt.close();
					con.close();
				} catch(SQLException e){
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Cotizacion_Producto ] Insert  SQLException: sp_insert_cotizacion_de_un_productos_en_proveedores "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
			}		
		return true;
	}
	public boolean Guardar_Concepto_Extra(Obj_Conceptos_De_Extras_Para_Lista_De_Raya conceptos_extra){
		String query = "exec sp_insert_concepto_de_extra_en_lista_de_raya ?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, conceptos_extra.getConcepto().toUpperCase().trim());
			pstmt.setString(2, conceptos_extra.getAbreviatura().toUpperCase().trim());
			pstmt.setString(3, (conceptos_extra.getStatus().equals("VIGENTE"))?"1":"0");
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Concepto_Extra ] Insert  SQLException: sp_insert_puesto "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);

			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Concepto_Extra ] Insert  SQLException: sp_insert_puesto "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Concepto_Extra ] Insert  SQLException: sp_insert_puesto "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			}
		}		
		return true;
	   }
	
	public boolean Guardar_Nombre_PC(Obj_Asignacion_De_Computadoras_Para_Checador_Por_Establecimiento tpc){
		String query = "exec sp_insert_nombre_de_PC_Para_Checador_Del_establecimiento ?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			
			pstmt.setInt(1, tpc.getFolio());
			pstmt.setString(2, tpc.getNombre_Pc().toUpperCase().trim());
			pstmt.setString(3, tpc.getEstablecimiento().toUpperCase().trim());
			pstmt.setInt(4, tpc.getStatus());
			
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Nombre_PC ] Insert   \nSQLException: sp_insert_nombre_de_PC_Para_Checador_Del_establecimiento "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Nombre_PC ] Insert  \nSQLException: sp_insert_nombre_de_PC_Para_Checador_Del_establecimiento "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Nombre_PC ] Insert  SQLException: insert_etapa "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			}
		}		
		return true;
	}
	
	public boolean Guardar_Punto_Venta_TA(Obj_Puntos_De_Venta_De_Tiempo_Aire tpc){
		String query = "exec sp_insert_nombre_punto_de_venta_de_TA_por_establecimiento ?,?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			
			pstmt.setInt(1, tpc.getFolio());
			pstmt.setString(2, tpc.getNombre_Pc().toUpperCase().trim());
			pstmt.setString(3, tpc.getEstablecimiento().toUpperCase().trim());
			pstmt.setString(4, tpc.getNombre_Punto_Venta_TA().toUpperCase().trim());
			pstmt.setInt(5, tpc.getStatus());
			
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Punto_Venta_TA ]\nSQLException:"+query+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Punto_Venta_TA ]\nSQLException:"+query+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Punto_Venta_TA ] \nSQLException:"+query+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			}
		}		
		return true;
	}
	
	public boolean Guardar_Concepto_De_Orden_De_Pago(Obj_Conceptos_De_Ordenes_De_Pago concepto){
		String query = "exec sp_insert_concepto_de_orden_de_pago ?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			
			pstmt.setInt(1, concepto.getFolio());
			pstmt.setString(2, concepto.getConcepto().toUpperCase().trim());
			pstmt.setString(3, concepto.getEstatus().toUpperCase().trim());
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Concepto_De_Orden_De_Pago ]\nSQLException:"+query+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Concepto_De_Orden_De_Pago ]\nSQLException:"+query+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Concepto_De_Orden_De_Pago ] \nSQLException:"+query+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			}
		}		
		return true;
	}
	
	public boolean Guardar_Sugerido_Sistema(Object[][] matriz1, Object[][] matriz2, int folio_sugerido) throws SQLException{
		
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		
		int folio_usuario = usuario.getFolio();
		
		if(folio_sugerido>0){
			String queryUpdate = "delete tb_sugeridos_cedis where folio = "+folio_sugerido;

//			actualizar folio
			PreparedStatement pstmtupdate = con.prepareStatement(queryUpdate);
			con.setAutoCommit(false);
			pstmtupdate.executeUpdate();
					
		}else{
			String queryUpdate = "update tb_folios set folio = (select folio+1 from tb_folios where transaccion = 'Folio Sugerido Cedis') where transaccion = 'Folio Sugerido Cedis'";

//			actualizar folio
			PreparedStatement pstmtupdate = con.prepareStatement(queryUpdate);
			con.setAutoCommit(false);
			pstmtupdate.executeUpdate();
		}
		
		String query = "exec sp_insert_tb_sugeridos_cedis ?,?,?,?,?,?,?,?,?,?,"+folio_sugerido;
		
		
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			
			for(int i=0; i<matriz1.length; i++){
				
					pstmt.setString(1 , matriz1[i][0 ].toString().trim());
					pstmt.setString(2 , matriz1[i][1 ].toString().trim());
					pstmt.setDouble(3 , Double.valueOf(matriz1[i][2 ].toString().trim()));
					pstmt.setDouble(4 , Double.valueOf(matriz1[i][3 ].toString().trim()));
					pstmt.setDouble(5 , Double.valueOf(matriz1[i][4 ].toString().trim()));
					pstmt.setInt   (6 , matriz1[i][5 ].toString().trim().equals("false")?0:1);
					pstmt.setDouble(7 , matriz1[i][6 ].toString().trim().equals("")?0:Double.valueOf(matriz1[i][6 ].toString().trim()));
					pstmt.setString(8 , matriz1[i][7 ].toString().trim());
					                                 
					pstmt.setInt(9 , folio_usuario);
					pstmt.setInt(10, 1);			//pedido       
					
					pstmt.executeUpdate();
			}
			
			for(int i=0; i<matriz2.length; i++){
				
					pstmt.setString(1 , matriz2[i][0 ].toString().trim());                                                                
					pstmt.setString(2 , matriz2[i][1 ].toString().trim());                                                                
					pstmt.setDouble(3 , Double.valueOf(matriz2[i][2 ].toString().trim()));                                                
					pstmt.setDouble(4 , Double.valueOf(matriz2[i][3 ].toString().trim()));                                                
					pstmt.setDouble(5 , Double.valueOf(matriz2[i][4 ].toString().trim()));                                                
					pstmt.setInt   (6 , matriz2[i][5 ].toString().trim().equals("false")?0:1);                                            
					pstmt.setDouble(7 , matriz2[i][6 ].toString().trim().equals("")?0:Double.valueOf(matriz2[i][6 ].toString().trim()));  
					pstmt.setString(8 , matriz2[i][7 ].toString().trim());                                                                
					                                 
					pstmt.setInt(9 , folio_usuario);
					pstmt.setInt(10, 0);			//negado       
					
					pstmt.executeUpdate();
			}
			
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Sugerido_Sistema ] Insert   \nSQLException: sp_isert_fsfsdfsdf "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Sugerido_Sistema ] Insert  \nSQLException: sp_isert_fsfsdfsdf "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Nombre_PC ] Insert  SQLException: insert_etapa "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			}
		}		
		return true;
	}
	
	public boolean Guardar_Revision_De_Corte_Aud(String FolioCorte, String StatusCobro, String DiferenciaAuditoria, String Observacion, String responsable_de_error){
		
		String query = "exec sp_update_reviso_corte_auditoria ?,?,?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, FolioCorte);
			pstmt.setString(2, StatusCobro);
			pstmt.setDouble(3, DiferenciaAuditoria.equals("")?0:Double.valueOf(DiferenciaAuditoria));
			pstmt.setString(4, Observacion);
			pstmt.setInt(5, usuario.getFolio());
			pstmt.setString(6, responsable_de_error);
			
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Revision_De_Corte_Aud ] Update   \nSQLException: sp_update_reviso_corte_auditoria "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Revision_De_Corte_Aud ] Update  \nSQLException: sp_update_reviso_corte_auditoria "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Revision_De_Corte_Aud ] Update  SQLException: sp_update_reviso_corte_auditoria "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			}
		}		
		return true;
	}
	
	public boolean Guardar_captura_de_competencia(Obj_Cotizaciones_De_Un_Producto Cotizacion_Producto, String[][] comp){
//			String query = "exec sp_insert_cotizacion_de_un_productos_en_proveedores ?,?,?,?,?,?,?,?,?,?,?,? ";
		String query = "exec sp_insert_precios_competencia ?,?,?,?,?,?,?,?,?";
		
		System.out.print(query);
			
		
		System.out.println(Cotizacion_Producto.getCod_Prod().toUpperCase().trim());
		System.out.println(Cotizacion_Producto.getUltimo_Costo());
		System.out.println(Cotizacion_Producto.getCosto_Promedio());
		System.out.println(Cotizacion_Producto.getPrecio_de_venta());
		System.out.println("1");
		System.out.println("22.1");
		System.out.println(usuario.getFolio());
		System.out.println(Cotizacion_Producto.getFecha().toString().trim());
		System.out.println(Cotizacion_Producto.getPrecio_de_venta_normal());
		
		
			Connection con = new Connexion().conexion_IZAGAR();
			PreparedStatement pstmt = null;
			try {
				con.setAutoCommit(false);
				pstmt = con.prepareStatement(query);
				
				for(int i=0; i<comp.length; i++){
					if(!comp[i][1].toString().trim().equals("")){
						
						
						pstmt.setString(1, Cotizacion_Producto.getCod_Prod().toUpperCase().trim());
					    pstmt.setDouble(2,  Cotizacion_Producto.getUltimo_Costo());  
				        pstmt.setDouble(3,  Cotizacion_Producto.getCosto_Promedio());
				        pstmt.setDouble(4,  Cotizacion_Producto.getPrecio_de_venta());  
				        
				        pstmt.setInt(5,  Integer.valueOf(comp[i][0].toString()));
				        pstmt.setDouble(6,  Double.valueOf(comp[i][1].toString()));  
				        pstmt.setInt(7,  usuario.getFolio());   
				        pstmt.setString(8, Cotizacion_Producto.getFecha().toString().trim());
				        pstmt.setDouble(9, Cotizacion_Producto.getPrecio_de_venta_normal());
				        
						pstmt.executeUpdate();
					}
				}
				
				con.commit();
			} catch (SQLException e) {
				System.out.println("SQLException: " + e.getMessage());
				if (con != null){
					try {
						System.out.println("La transacción ha sido abortada");
						con.rollback();
					} catch(SQLException ex) {
						System.out.println(ex.getMessage());
						JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_captura_de_competencia ] \nInsert  SQLException: sp_insert_precios_competencia "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen//usuario-icono-eliminar5252-64.png"));
					}
				} 
				return false;
			}finally{
				try {
					pstmt.close();
					con.close();
				} catch(SQLException e){
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_captura_de_competencia ] \nInsert  SQLException: sp_insert_precios_competencia "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
			}		
		return true;
	}
	
	public boolean Archivar_Documentos_De_Empleados(int folio_empleado,String[][] archivos){
		String query = "exec sp_insert_archivos_de_empleados ?,?,?";
		
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			
			for(int i=0; i<archivos.length; i++){
				if(!archivos[i][1].toString().equals("")){
					if(!archivos[i][1].toString().trim().equals("Capturado")){
							pstmt.setInt(1, folio_empleado);
							pstmt.setString(2, archivos[i][0].toString().toLowerCase().replace(" ","_"));
							pstmt.setBinaryStream(3, new FileInputStream(new File(archivos[i][1].toString())));
							pstmt.executeUpdate();
					}
				}
			}
			con.commit();
			
		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Archivar_Documentos_De_Empleados ] Insert  SQLException: sp_insert_archivos_de_empleados "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen//usuario-icono-eliminar5252-64.png"));
			if (con != null){
				try {
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				} catch(SQLException ex) {
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Archivar_Documentos_De_Empleados ] Insert  SQLException: sp_insert_archivos_de_empleados "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("Imagen//usuario-icono-eliminar5252-64.png"));
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
	
	public boolean Guardar_Configuracion_De_Poliza(String tipo,String nombre, String relleno, String asiento_cont, String status){
		String query = "exec sp_insert_configuracion_de_polizas ?,?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, tipo.toUpperCase().trim());
			pstmt.setString(2, nombre.toUpperCase().trim());
			pstmt.setString(3, relleno);
			pstmt.setString(4, asiento_cont.toUpperCase().trim().equals("EGRESOS") ? "E" : ( asiento_cont.toUpperCase().trim().equals("INGRESOS") ? "I" : "V" ) );
			pstmt.setString(5, status.toUpperCase().trim().equals("CANCELADO")?"C":"V");
			
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Configuracion_De_Poliza ] Insert   \nSQLException: sp_insert_configuracion_de_polizas "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Configuracion_De_Poliza ] Insert  \nSQLException: sp_insert_configuracion_de_polizas "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Configuracion_De_Poliza ] Insert  SQLException: sp_insert_configuracion_de_polizas "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			}
		}		
		return true;
	}
	
	public boolean Guardar_Cuenta_Contable(String f_cuenta,String cuenta, String naturaleza, String grupo, String clasificacion,String status, String establecimiento){
		String query = "exec sp_insert_cuenta_contable ?,?,?,?,?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, f_cuenta.toUpperCase().trim());
			pstmt.setString(2, cuenta.toUpperCase().trim());
			pstmt.setString(3, naturaleza.toUpperCase().trim());
			pstmt.setString(4, grupo.toUpperCase().trim());
			pstmt.setString(5, clasificacion.toUpperCase().trim());
			pstmt.setString(6, status.toUpperCase().trim());
			pstmt.setInt(7, usuario.getFolio());
			pstmt.setString(8, establecimiento);
			
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Cuenta_Contable ] Insert   \nSQLException: sp_insert_cuenta_contable "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Cuenta_Contable ] Insert  \nSQLException: sp_insert_cuenta_contable "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Cuenta_Contable ] Insert  SQLException: sp_insert_cuenta_contable "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			}
		}		
		return true;
	}
	
	public boolean Guardar_SubCuenta_Contable(String f_cuenta, String f_scuenta,String scuenta, String transaccion, String status){
		String query = "exec sp_insert_subcuenta_contable ?,?,?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, f_cuenta.toUpperCase().trim());
			pstmt.setString(2, f_scuenta.toUpperCase().trim());
			pstmt.setString(3, scuenta.toUpperCase().trim());
			pstmt.setString(4, transaccion.trim());
			pstmt.setString(5, status.toUpperCase().trim());
			pstmt.setInt(6, usuario.getFolio());
			
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_SubCuenta_Contable ] Insert   \nSQLException: sp_insert_subcuenta_contable "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_SubCuenta_Contable ] Insert  \nSQLException: sp_insert_subcuenta_contable "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_SubCuenta_Contable ] Insert  SQLException: sp_insert_subcuenta_contable "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			}
		}		
		return true;
	}
	
	
	
	
	
	public boolean Guardar_Sub_SubCuenta_Contable(String f_cuenta, String f_scuenta, String f_sscuenta,String sscuenta, String transaccion, String status){
		String query = "exec sp_insert_sub_subcuenta_contable ?,?,?,?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, f_cuenta.toUpperCase().trim());
			pstmt.setString(2, f_scuenta.toUpperCase().trim());
			pstmt.setString(3, f_sscuenta.toUpperCase().trim());
			pstmt.setString(4, sscuenta.toUpperCase().trim());
			pstmt.setString(5, transaccion.trim());
			pstmt.setString(6, status.toUpperCase().trim());
			pstmt.setInt(7, usuario.getFolio());
			
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Sub_SubCuenta_Contable ] Insert   \nSQLException: sp_insert_sub_subcuenta_contable "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Sub_SubCuenta_Contable ] Insert  \nSQLException: sp_insert_sub_subcuenta_contable "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Sub_SubCuenta_Contable ] Insert  SQLException: sp_insert_sub_subcuenta_contable "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			}
		}		
		return true;
	}
	
	public boolean Guardar_Poliza(String folio, String tipo, String fecha_poliza, String referencia_trans, int referencia, String nota, String concepto_gral, String cheque, Object[][] matriz, String ReferenciaText, String FormaPago, String tipoBanco, float total,String tipo_documento,String folio_poliza_a_modificar, String tipo_poliza_a_modificar, String fecha_a_modificar){
		String query = "exec sp_insert_polizas2 ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
		
//			int x=1;
			float cargo = 0;
			float abono = 0;
			for(int i=0; i<matriz.length; i++){
				
				con.setAutoCommit(false);
				pstmt = con.prepareStatement(query);
				
				System.out.println(folio.toUpperCase().trim());
				System.out.println(tipo.toUpperCase().trim());
				System.out.println(fecha_poliza.toUpperCase().trim());
				System.out.println(referencia_trans.toUpperCase().trim());
				System.out.println(referencia);
				System.out.println(nota.toUpperCase().trim());
				System.out.println(concepto_gral.toUpperCase().trim());
				System.out.println(cheque.toUpperCase().trim());
				System.out.println(usuario.getFolio());
				System.out.println(matriz[i][0].toString().trim().toUpperCase());
				System.out.println(matriz[i][1].toString().trim().toUpperCase());
				System.out.println(matriz[i][2].toString().trim().toUpperCase());
				System.out.println(matriz[i][3].toString().trim().toUpperCase());
				cargo = Float.valueOf(matriz[i][4].toString().trim().toUpperCase().equals("")?"0":matriz[i][4].toString().trim().toUpperCase());
				abono = Float.valueOf(matriz[i][5].toString().trim().toUpperCase().equals("")?"0":matriz[i][5].toString().trim().toUpperCase());
				System.out.println((cargo==0)?abono:cargo);
				System.out.println((cargo==0)?"A":"C");
				System.out.println(ReferenciaText);
				System.out.println(FormaPago);
				System.out.println(tipoBanco);
				System.out.println(total);
				System.out.println(tipo_documento);
				System.out.println(folio_poliza_a_modificar);
				System.out.println(tipo_poliza_a_modificar);
				System.out.println(fecha_a_modificar);
				
				pstmt.setString(1, folio.toUpperCase().trim());
				pstmt.setString(2, tipo.toUpperCase().trim());
				pstmt.setString(3, fecha_poliza.toUpperCase().trim());
				pstmt.setString(4, referencia_trans.toUpperCase().trim());
				pstmt.setInt(5, referencia);
				pstmt.setString(6, nota.toUpperCase().trim());
				
				pstmt.setString(7, concepto_gral.toUpperCase().trim());
				pstmt.setString(8, cheque.toUpperCase().trim());
				
				pstmt.setInt(9, usuario.getFolio());
				
				pstmt.setString(10 , matriz[i][0].toString().trim().toUpperCase());//cuenta
				pstmt.setString(11 , matriz[i][1].toString().trim().toUpperCase());//subcuenta
				pstmt.setString(12 , matriz[i][2].toString().trim().toUpperCase());//subsubcuenta
				
				cargo = Float.valueOf(matriz[i][4].toString().trim().toUpperCase().equals("")?"0":matriz[i][4].toString().trim().toUpperCase());
				abono = Float.valueOf(matriz[i][5].toString().trim().toUpperCase().equals("")?"0":matriz[i][5].toString().trim().toUpperCase());
			
				pstmt.setFloat(13 , (cargo==0)?abono:cargo);//importe
				pstmt.setString(14 , (cargo==0)?"A":"C");//cargo_abono
				
				pstmt.setString(15 , matriz[i][6].toString().trim().toUpperCase());//concepto
				pstmt.setString(16 , matriz[i][7].toString().trim().toUpperCase());//establecimiento
				pstmt.setInt(17 , Integer.valueOf(matriz[i][8].toString().trim()));//contador de registro
				
				pstmt.setString(18 , ReferenciaText);
				
				pstmt.setString(19 , FormaPago);
				pstmt.setString(20 , tipoBanco);
				pstmt.setFloat(21 , total);
				pstmt.setString(22, tipo_documento);
				pstmt.setString(23, folio_poliza_a_modificar);
				pstmt.setString(24, tipo_poliza_a_modificar);
				pstmt.setString(25, fecha_a_modificar);
				
				pstmt.executeUpdate();
			}
			
			con.commit();
		} catch (SQLException e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Poliza ] Insert   \nSQLException: sp_insert_polizas "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Poliza ] Insert  \nSQLException: sp_insert_polizas "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Poliza ] Insert  SQLException: sp_insert_polizas "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			}
		}		
		return true;
	}
	
	public boolean Modificar_Folio_De_Poliza(String tipo,String mes_anio, int folio){
		String query = "update tb_folios_polizas set folio_siguiente = "+folio+" where tipo_poliza = '"+tipo+"' and mes_año = '"+mes_anio+"'";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Modificar_Folio_De_Poliza ] "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Modificar_Folio_De_Poliza ] "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Modificar_Folio_De_Poliza ] "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			}
		}		
		return true;
	}
	
	public boolean Guardar_Proveedor(Obj_Alta_Proveedores_Polizas prv){
		String query = "exec sp_insert_proveedor ?,?,?,?,?";
		
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			
			int i=1;
			pstmt = con.prepareStatement(query);
			pstmt.setString(i,	 	prv.getNombre().toUpperCase());
			pstmt.setString(i+=1,	prv.getAp_paterno().toUpperCase());
			pstmt.setString(i+=1,	prv.getAp_materno().toUpperCase());
			pstmt.setString(i+=1,	prv.getDireccion().toUpperCase());
			pstmt.setString(i+=1, 	prv.getTelefono().toUpperCase());
			
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Proveedor ] Insert  SQLException: sp_insert_cliente "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			if (con != null){
				try {
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				} catch(SQLException ex) {
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Proveedor ] Insert  SQLException: sp_insert_empleado "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
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

	public boolean Guardar_Orden_De_Pago_En_Efectivo(float cantidad, String fecha, String concepto, String autorizacion, String tipoBeneficiario, int folioBeneficiario, String establecimiento, String Concepto){
		String query = "exec sp_insert_orden_de_pago_en_efectivo ?,?,?,?,?,?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
		
				con.setAutoCommit(false);
				pstmt = con.prepareStatement(query);
				
				pstmt.setFloat(1, cantidad);
				pstmt.setString(2, fecha.toUpperCase().trim());
				pstmt.setString(3, concepto.toUpperCase().trim());
				pstmt.setString(4, autorizacion.toUpperCase().trim());
				pstmt.setString(5, tipoBeneficiario.toUpperCase().trim());
				
				pstmt.setInt(6, folioBeneficiario);
				pstmt.setInt(7, usuario.getFolio());
				
				pstmt.setString(8, establecimiento.toUpperCase().trim());
				pstmt.setString(9, Concepto.toUpperCase().trim());
				
				pstmt.executeUpdate();
			
			con.commit();
		} catch (SQLException e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Ordern_De_Pago_En_Efectivo ] Insert   \nSQLException: sp_insert_orden_de_pago_en_efectivo "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Ordern_De_Pago_En_Efectivo ] Insert  \nSQLException: sp_insert_orden_de_pago_en_efectivo "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Ordern_De_Pago_En_Efectivo ] Insert  SQLException: sp_insert_orden_de_pago_en_efectivo "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			}
		}		
		return true;
	}

	public boolean GuardarSaldo_TA(Integer folio_cajero, Integer folio_supervisor,String establecimiento, String saldo,double venta, String asignacion) {
		String nombrepc="";
		try { nombrepc = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		
		String query = "sp_insert_saldo_TA_entrada_y_salida_cajeras "+folio_cajero+","+folio_supervisor+",'"+establecimiento+"','"+nombrepc+"','"+saldo+"','"+venta+"','"+asignacion+"'";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ guardarsaldo_TA ] "+e.getMessage()+"\nconsulta:"+query, "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ guardarsaldo_TA ] "+ex.getMessage()+"\nconsulta:"+query, "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ guardarsaldo_TA ] "+e.getMessage()+"\nconsulta:"+query, "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			}
		}		
		return true;
	}
	
	public boolean GuardarTrasaso_TA(String Nombre_Punto_De_venta_TA,String Nombre_establecimiento, String Importe_Traspaso ) {
		String query = "sp_insert_traspaso_de_saldo_TA "+usuario.getFolio()+",'"+Nombre_Punto_De_venta_TA+"','"+Nombre_establecimiento+"',"+Importe_Traspaso;
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ GuardarTrasaso_TA ] "+e.getMessage()+"\nconsulta:"+query, "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ GuardarTrasaso_TA ] "+ex.getMessage()+"\nconsulta:"+query, "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ GuardarTrasaso_TA ] "+e.getMessage()+"\nconsulta:"+query, "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			}
		}		
		return true;
	}

	
	
	
	public boolean Guardar_Pedido_De_Monedas(Obj_Pedido_De_Monedas pedido, String folio_Guardado){
		String query ="exec sp_insert_pedido_de_monedas ?,?,?,?,?,?,?,?,"+folio_Guardado;
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			
				for(int i=0; i<pedido.getMatriz().length; i++){
					pstmt.setInt(1, usuario.getFolio());
					pstmt.setInt(2, pedido.getFolioUsuario());
					pstmt.setString(3, pedido.getStatus_pedido());
					
					pstmt.setFloat(4, Float.valueOf(pedido.getMatriz()[i][0].toString()));
					pstmt.setInt(5, Integer.valueOf(pedido.getMatriz()[i][2].toString()));
					pstmt.setFloat(6, Float.valueOf(pedido.getMatriz()[i][3].toString()));
					
					pstmt.setString(7, pedido.getObservacion());
					pstmt.setString(8, pedido.getEmpleado_entrego());
					
					pstmt.executeUpdate();
				}
					
			con.commit();
			
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Pedido_De_Monedas ] \nEn el Procedimiento almacenado "+query+"\n"+e.getMessage(), "Avisa al Administrador del sistema", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));

			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null,"Error en GuardarSQL  en la funcion [ Guardar_Pedido_De_Monedas ] \nEn el Procedimiento almacenado "+query+"\n"+e.getMessage(), "Avisa al Administrador del sistema", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));

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
	
	public boolean Guardar_Productos(Obj_Alta_De_Productos prod){
		String query = "exec sp_insert_productos ?,?,?,?,?,?,?,? ";
		
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, prod.getFolio().trim());
			pstmt.setString(2, prod.getDescripcion().toUpperCase().trim());
			pstmt.setString(3, prod.getUnidadDeMedida().toUpperCase().trim());
			pstmt.setString(4, prod.getUso().toUpperCase().trim());
			pstmt.setString(5, prod.getCodigoDeBarras());
			pstmt.setDouble(6, prod.getCosto());
			pstmt.setDouble(7, prod.getPrecioDeVenta());
			pstmt.setString(8, prod.getStatus().toUpperCase().trim());
			
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
			if (con != null){
				try {
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				} catch(SQLException ex) {
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Productos ] \n"+query+"\nSQLException:"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));

				}
			} 
			return false;
		}finally{
			try {
				pstmt.close();
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Establecimiento ] \n"+query+"\nSQLException:"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			}
		}		
		return true;
	}
	
	public boolean Guardar_Unidades_De_Medida_De_Producto(Obj_Unidades_De_Medida_De_Producto unidad){
		String query = "exec sp_insert_unidades_de_medida_De_producto ?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			
			pstmt.setInt(1, unidad.getFolio());
			pstmt.setString(2, unidad.getUnidad().toUpperCase().trim());
			pstmt.setString(3, unidad.getEstatus().toUpperCase().trim());
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Unidades_De_Medida_De_Producto ]\nSQLException:"+query+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Unidades_De_Medida_De_Producto ]\nSQLException:"+query+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Unidades_De_Medida_De_Producto ] \nSQLException:"+query+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			}
		}		
		return true;
	}
	
	public boolean Guardar_Compra_De_Cascos(Object[][] tabla,Obj_Compra_De_Cascos objeto){
		String query =  "exec sp_insert_compra_de_cascos ?,?,?,?,?,?,"+usuario.getFolio();
		Connection con = new Connexion().conexion();
		try {
			PreparedStatement pstmt = con.prepareStatement(query);
			con.setAutoCommit(false);
			for(int i=0; i<tabla.length; i++){
				pstmt.setString(1, tabla[i][0].toString().trim());
				pstmt.setString(2, tabla[i][1].toString().trim());
				pstmt.setString(3, tabla[i][2].toString().trim());
				pstmt.setInt   (4, objeto.getFolio_compra());
				pstmt.setString(5, objeto.getBeneficiario());
				pstmt.setDouble(6, objeto.getTotal());
				pstmt.executeUpdate();
			}
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en ActualizarSQL  en la  funcion Guardar_Compra_De_Cascos  \n"+query+"\nSQLException:"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					JOptionPane.showMessageDialog(null, "Error en ActualizarSQL  en la  funcion Guardar_Compra_De_Cascos \n"+query+"\nSQLException:"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en ActualizarSQL  en la  funcion Guardar_Compra_De_Cascos  \n"+query+"\nSQLException:"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
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
	
	public boolean Guardar_Codigo_Adicional(Obj_Alimentacion_De_Codigos_Adicionales Datos_Producto){
		String query = "exec sp_insert_codigo_adicional ?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, Datos_Producto.getFolio_Producto().trim());
			pstmt.setString(2, Datos_Producto.getCodigo_Tecleado().trim());
			pstmt.setString(3, Datos_Producto.getCodigo_Barras().trim().equals("")?"0.0":Datos_Producto.getCodigo_Barras().trim());
	        pstmt.setString(4, usuario.getFolio()+"");
	        		
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: " + e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Codigo_Adicional ]\n"+query+"\nSQLException:"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));

			if (con != null){
				try {
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				} catch(SQLException ex) {
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Codigo_Adicional ]\n"+query+"\nSQLException:"+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
				}
			} 
			return false;
		}finally{
			try {
				pstmt.close();
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Codigo_Adicional ]\n"+query+"\nSQLException:"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			}
		}		
	return true;
}
	
	
public boolean Guardar_Actividad_Planeacion(Obj_Actividades_De_Una_Planeacion Actividades_plan, Obj_Opciones_De_Respuesta opRespuesta, Obj_Prioridad_Y_Ponderacion opPonderacion, Obj_Seleccion_De_Usuarios usuarios, Obj_Frecuencia_De_Actividades frecuencia, int folio_empleado){
		int folio_actividad=busca_y_actualiza_proximo_folio(20);
		String query ="sp_insert_actividad_de_plan "+folio_actividad+",?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?";

		String querytabla = "INSERT INTO [tb_asignacion_de_empleados_a_una_actividad] ([folio_actividad]  ,[folio_empleado] ,[Estatus])"
		 		           + "                                                  VALUES ("+folio_actividad+",?                ,'V'      )";
		Connection con = new Connexion().conexion();
		try {
			con.setAutoCommit(false);
			PreparedStatement pstmt     = con.prepareStatement(query     );
			PreparedStatement pstmtabla = con.prepareStatement(querytabla);
			
			//actividades
			pstmt.setInt    (1, folio_empleado                                                                                     );//folio_empleado
			pstmt.setString (2, Actividades_plan.getDescripcion_de_la_actividad().toString()                                       );//Descripcion_de_la_actividad

			//opciones_respuesta
			pstmt.setString (3,  (opRespuesta.getResuelta().toString().equals("true"))?"S":"N"                                     );//Resuelta
			pstmt.setString (4,  (opRespuesta.getIncumplida().toString().equals("true"))?"S":"N"                                   );//Incumplida
			pstmt.setString (5,  (opRespuesta.getPendiente().toString().equals("true"))?"S":"N"                                    );//Pendiente
			pstmt.setString (6,  (opRespuesta.getEnProceso().toString().equals("true"))?"S":"N"                                    );//EnProceso
			pstmt.setString (7,  (opRespuesta.getPasoAOtroDepartamento().toString().equals("true"))?"S":"N"                        );//PasoAOtroDepartamento
			pstmt.setString (8,  (opRespuesta.getExige_Evidencia().toString().equals("true"))?"S":"N"                              );//Exige_Evidencia
			pstmt.setString (9, (opRespuesta.getExigeObservacion().toString().equals("true"))?"S":"N"                             );//ExigeObservacion
			
			//ponderacion
			pstmt.setString (10, (opPonderacion.getImportante().toString().equals("true"))?"S":"N"                                 );//Importante
			pstmt.setString (11, (opPonderacion.getUrgente().toString().equals("true"))?"S":"N"                                    );//Urgente
			pstmt.setString (12, (opPonderacion.getPreventivo().toString().equals("true"))?"S":"N"                                 );//Preventivo
			pstmt.setString (13, (opPonderacion.getNormal().toString().equals("true"))?"S":"N"                                     );//Normal
			pstmt.setInt    (14,  Integer.valueOf(opPonderacion.getPonderacion().toString())                                       );//Ponderacion
			
			//frecuencia
			pstmt.setString (15,  (frecuencia.getTipo_de_frecuencia().toString().equals("UNA VEZ"))?"U":"P"                        );//tipo_de_frecuencia
			pstmt.setString (16,  (String.valueOf(frecuencia.isSeleccion_hasta_que_se_cumpla()).toString().equals("true"))?"S":"N" );//seleccion_hasta_que_se_cumpla
			pstmt.setString (17,  (String.valueOf(frecuencia.isSeleccion_en_la_fecha_indicada()).toString().equals("true"))?"S":"N");//seleccion_en_la_fecha_indicada
			//unica repeticion
			pstmt.setString (18,  (frecuencia.getFh_unica_repeticion().toString().trim())                                          );//fecha_unica_repeticion
			pstmt.setString (19,  (String.valueOf(frecuencia.isSeleccion_con_hora()).toString().equals("true"))?"S":"N"            );//seleccion_con_hora
			pstmt.setString (20,  frecuencia.getHora_unica_repeticion().toString().trim()                                          );//hora_unica_repeticion
			
			//frecuencia
			pstmt.setString (21,  (frecuencia.getSucede().toString().equals("DIARIA"))?"D":((frecuencia.getSucede().toString().equals("SEMANAL"))?"S":"M"));//sucede
			pstmt.setString (22,  (String.valueOf(frecuencia.isSelecciona_dia_del_mes()).toString().equals("true"))?"S":"N"        );//selecciona_dia_del_mes
			pstmt.setInt    (23,  Integer.valueOf(frecuencia.getDias_a_repetir_por_suceso_de_dias())                               );//dias_a_repetir_por_suceso_de_dias
			pstmt.setInt    (24,  Integer.valueOf(frecuencia.getDias_a_repetir_por_suceso_de_semanas())                            );//dias_a_repetir_por_suceso_de_semanas
			pstmt.setInt    (25,  Integer.valueOf(frecuencia.getDias_a_repetir_por_suceso_de_meses())                              );//dias_a_repetir_por_suceso_de_meses
			pstmt.setInt    (26,  Integer.valueOf(frecuencia.getMes1())                                                            );//el_dia_del_mes1
			pstmt.setString (27,  (String.valueOf(frecuencia.isSelecciona_dia_de_la_semana()).toString().equals("true"))?"S":"N"   );//selecciona_dia_de_la_semana
			pstmt.setString (28,  (frecuencia.getNivel_de_dias().toString().equals("Primer"))?"P":(frecuencia.getNivel_de_dias().toString().equals("Segundo"))?"S":
				                  (frecuencia.getNivel_de_dias().toString().equals("Tercer"))?"T":(frecuencia.getNivel_de_dias().toString().equals("Cuarto" ))?"C":"U");//nivel_de_dias
			pstmt.setInt    (29,  Integer.valueOf(frecuencia.getDia_de_la_semana().toString())                                     );//dia_de_la_semana
			pstmt.setInt    (30,  Integer.valueOf(frecuencia.getMes2())                                                            );//el_segundo_por_mes_mes2
			//semana
			pstmt.setString (31,  (String.valueOf(frecuencia.isDomingo()).toString().equals("true"))?"S":"N"                       );//domingo			
			pstmt.setString (32,  (String.valueOf(frecuencia.isLunes()).toString().equals("true"))?"S":"N"                         );//lunes		
			pstmt.setString (33,  (String.valueOf(frecuencia.isMartes()).toString().equals("true"))?"S":"N"                        );//martes		
			pstmt.setString (34,  (String.valueOf(frecuencia.isMiercoles()).toString().equals("true"))?"S":"N"                     );//miercoles		
			pstmt.setString (35,  (String.valueOf(frecuencia.isJueves()).toString().equals("true"))?"S":"N"                        );//jueves		
			pstmt.setString (36,  (String.valueOf(frecuencia.isViernes()).toString().equals("true"))?"S":"N"                       );//viernes		
			pstmt.setString (37,  (String.valueOf(frecuencia.isSabado()).toString().equals("true"))?"S":"N"                        );//sabado		
            //frecuencia diaria
			pstmt.setString (38,  (String.valueOf(frecuencia.isSeleccion_asignar_hora()).toString().equals("true"))?"S":"N"        );//seleccion_asignar_hora		
			pstmt.setString (39,  frecuencia.getHora_frecuencia_diaria().toString().trim()                                         );//hora_frecuencia_diaria
            //Duracion
			pstmt.setString (40,  (frecuencia.getFecha_inicio_duracion().toString().trim())                                        );//fecha_inicio_duracion
			pstmt.setString (41,  (String.valueOf(frecuencia.isSeleccion_fecha_finaliza()).toString().equals("true"))?"S":"N"      );//seleccion_fecha_finaliza
			pstmt.setString (42,  (frecuencia.getFecha_final_duracion().toString().trim())                                         );//fecha_final_duracion
			pstmt.setString (43,  (String.valueOf(frecuencia.isSeleccion_sin_fecha_final()).toString().equals("true"))?"S":"N"     );//seleccion_sin_fecha_final
			
			pstmt.setString (44,  Actividades_plan.getHora_inicia().toString().trim()                                              );//hora_inicia_actividad
			pstmt.setString (45,  Actividades_plan.getHora_termina().toString().trim()                                             );//hora_termina_actividad
			pstmt.setString (46,  Actividades_plan.getEstatus_Actividad().toString().trim()                                        );//estatus_actividad
			
			for(int i=0; i<usuarios.getUsuarios_nombres().length; i++){
				pstmtabla.setString(1, usuarios.getUsuarios_nombres()[i][0].toString().trim());
				pstmtabla.executeUpdate();
			}
			
			pstmt.executeUpdate();
			con.commit();
			
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Actividad_Planeacion ]\n"+query+"\n"+querytabla+"\nSQLException:"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Actividad_Planeacion ]\n"+query+"\n"+querytabla+"\nSQLException:"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
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

public boolean Guardar_Pendiente(Obj_Pendientes pendiente){
	int folio_transaccion=busca_y_actualiza_proximo_folio(21);
	String query ="sp_insert_pendientes "+folio_transaccion+","+usuario.getFolio()+",?,?";
	
	Connection con = new Connexion().conexion();
	try {
		con.setAutoCommit(false);
		PreparedStatement pstmt     = con.prepareStatement(query);
		
		//actividades
		pstmt.setString (1, pendiente.getPendiente().toString()     );
        pstmt.setString (2, pendiente.getColaboradores().toString() );
		
		pstmt.executeUpdate();
		con.commit();
		
	} catch (Exception e) {
		System.out.println("SQLException: "+e.getMessage());
		JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Pendiente ]\n"+query+"\nSQLException:"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
		if(con != null){
			try{
				System.out.println("La transacción ha sido abortada");
				con.rollback();
			}catch(SQLException ex){
				System.out.println(ex.getMessage());
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Pendiente ]\n"+query+"\nSQLException:"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
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


public int  busca_y_actualiza_proximo_folio(int transaccion) {
	int foliosiguiente=0;
	Connexion con = new Connexion();
	String query = "declare @sqlqry nvarchar(max),@folio varchar(7)                        "
 		     + "   set nocount on"
 		     + "   set @folio=(select folio+1 from tb_folios Where folio_transaccion='"+transaccion+"' and status=1)"
 		     + "  set @sqlqry='update tb_folios set folio='+@folio+' Where folio_transaccion=''"+transaccion+"'' and status=1'"
 		     + "  exec sp_executesql @sqlqry"
 		     + "  set nocount off"
 		     + " set @sqlqry='select '+@folio"
 		     + " exec sp_executesql @sqlqry";
	
	Statement stmt = null;
	try {
		stmt = con.conexion().createStatement();
	    ResultSet rs = stmt.executeQuery(query);
		while(rs.next()){
			 foliosiguiente =(rs.getInt(1));
		}
	} catch (Exception e) {
		e.printStackTrace();
		System.err.println("Error");
		JOptionPane.showMessageDialog(null, "Error en la funcion busca_y_actualiza_proximo_folio()\n"+query+"\n"+e.getMessage(), "Avisa Al Administrador Del Sistema", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
		return foliosiguiente ;
	}
	finally{
		 if (stmt != null) { try {
			stmt.close();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error en la funcion busca_y_actualiza_proximo_folio()\n"+query+"\n"+e.getMessage(), "Avisa Al Administrador Del Sistema", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			e.printStackTrace();
		} }
	}
	return foliosiguiente;
		}



@SuppressWarnings("rawtypes")
public boolean Guardar_Objetivos_De_La_Semana(Vector objetivos, String folioObjetivo, String periodoObjetivo){
//	String queryUpdate =  " delete tb_objetivos_de_plan_semanal  where estatus = 'PLA' and folio = "+folioObjetivo+"and usuario = "+(new Obj_Usuario().LeerSession().getFolio());
	String query =  "exec sp_insert_objetivos_plan_semanal ?,?,?,?,"+usuario.getFolio();
	Connection con = new Connexion().conexion();
	
	PreparedStatement pstmt = null;
	try {
		
//		pstmt = con.prepareStatement(queryUpdate);
//		con.setAutoCommit(false);
//		pstmt.executeUpdate();
//		
		
		pstmt = con.prepareStatement(query);
		con.setAutoCommit(false);
		
		for(int i=0; i<objetivos.size(); i++){
			
			pstmt.setString(1, folioObjetivo.toString().trim());
			pstmt.setString(2, periodoObjetivo.substring(0, periodoObjetivo.indexOf("-")).trim()+" 00:00:00");
			pstmt.setString(3, periodoObjetivo.substring(periodoObjetivo.indexOf("-")+1, periodoObjetivo.length()).trim()+" 23:59:00");
			pstmt.setString(4, objetivos.get(i).toString().trim());
			
			pstmt.executeUpdate();
		}
		con.commit();
	} catch (Exception e) {
		System.out.println("SQLException: "+e.getMessage());
		JOptionPane.showMessageDialog(null, "Error en ActualizarSQL  en la  funcion Guardar_Objetivos_De_La_Semana  \n"+query+"\nSQLException:"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
		if(con != null){
			try{
				System.out.println("La transacción ha sido abortada");
				JOptionPane.showMessageDialog(null, "Error en ActualizarSQL  en la  funcion Guardar_Objetivos_De_La_Semana \n"+query+"\nSQLException:"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
				con.rollback();
			}catch(SQLException ex){
				System.out.println(ex.getMessage());
				JOptionPane.showMessageDialog(null, "Error en ActualizarSQL  en la  funcion Guardar_Objetivos_De_La_Semana  \n"+query+"\nSQLException:"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
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
	
public boolean Guardar_Actividades_Con_Respuesta(String[][] actividades, int dia){
	
	String query =  "exec sp_insert_actividades_de_plan_semanal_contestadas ?,?,?,?,?,?,?,?,?";
	Connection con = new Connexion().conexion();
	
	PreparedStatement pstmt = null;
	
	try {
		String ip = InetAddress.getLocalHost().getHostAddress();
		
		pstmt = con.prepareStatement(query);
		con.setAutoCommit(false);
		
		for(int i=0; i<actividades.length; i++){
			
			String ruta 	 = actividades[i][4].toString().equals("")?"C:\\SCOI\\imagen\\SIN EVIDENCIA.jpg":actividades[i][4].toString();
			String evidencia = actividades[i][4].toString().equals("")?"NO":"SI";
			
				pstmt.setInt(1, Integer.valueOf(actividades[i][0].toString()));                       //folio actividad  
				pstmt.setString(2, actividades[i][1].toString().trim().toUpperCase());                //respuesta        
				pstmt.setBinaryStream(3, new FileInputStream(ruta));								  //ruta_evidencia
				pstmt.setString(4, ruta.substring(ruta.indexOf(".")).toLowerCase());				  //tipo de archivo
				pstmt.setString(5, actividades[i][5].toString().trim().toUpperCase());                //observacion    
				pstmt.setInt(6, usuario.getFolio());												  //usuario
				pstmt.setString(7, ip); 					  										  //ip_pc
				pstmt.setInt(8, dia);                                                                 //dia    
				pstmt.setString(9, evidencia);                                                        //con_sin_evidencia
				pstmt.executeUpdate();
		}
		
		con.commit();
	} catch (Exception e){
		System.out.println("SQLException: "+e.getMessage());
		JOptionPane.showMessageDialog(null, "Error en ActualizarSQL  en la  funcion Guardar_Actividades_Con_Respuesta  \n"+query+"\nSQLException:"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
		if(con != null){
			try{
				System.out.println("La transacción ha sido abortada");
				JOptionPane.showMessageDialog(null, "Error en ActualizarSQL  en la  funcion Guardar_Actividades_Con_Respuesta \n"+query+"\nSQLException:"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
				con.rollback();
			}catch(SQLException ex){
				System.out.println(ex.getMessage());
				JOptionPane.showMessageDialog(null, "Error en ActualizarSQL  en la  funcion Guardar_Actividades_Con_Respuesta  \n"+query+"\nSQLException:"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
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

public boolean Guardar_Alimentacion_De_Inventario_Fisico(Object[][] inv_fis){
	
	String query =  "exec sp_insert_inventario_fisico ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?";
	Connection con = new Connexion().conexion();
	
	PreparedStatement pstmt = null;
	
	try {
		pstmt = con.prepareStatement(query);
		con.setAutoCommit(false);
		
		int folio_inventario_fisico = busca_y_actualiza_proximo_folio(22);//FOLIO CONSECUTIVO CORRESPONDIENTE A ALIMENTACION DE INVENTARIOS FISICOS
		for(int i=0; i<inv_fis.length; i++){
			
				pstmt.setInt   (1, folio_inventario_fisico);
				pstmt.setString(2, inv_fis[i][0].toString().trim());
				pstmt.setString(3, inv_fis[i][1].toString().trim().toUpperCase());           
				pstmt.setDouble(4, Double.valueOf(inv_fis[i][2].toString().trim()));
				pstmt.setDouble(5, Double.valueOf(inv_fis[i][3].toString().trim()));
				pstmt.setDouble(6, Double.valueOf(inv_fis[i][4].toString().trim()));
				pstmt.setDouble(7, Double.valueOf(inv_fis[i][5].toString().trim()));
				pstmt.setDouble(8, Double.valueOf(inv_fis[i][6].toString().trim()));
				pstmt.setDouble(9, Double.valueOf(inv_fis[i][7].toString().trim()));
				pstmt.setDouble(10,Double.valueOf(inv_fis[i][8].toString().trim()));
				pstmt.setDouble(11,Double.valueOf(inv_fis[i][9].toString().trim()));
				pstmt.setDouble(12,Double.valueOf(inv_fis[i][10].toString().trim()));
				pstmt.setString(13,inv_fis[i][11].toString().trim().toUpperCase());
				pstmt.setString(14,inv_fis[i][12].toString().trim().toUpperCase());
				pstmt.setInt(15, usuario.getFolio());
				
				pstmt.executeUpdate();
		}
		
		con.commit();
	} catch (Exception e){
		System.out.println("SQLException: "+e.getMessage());
		JOptionPane.showMessageDialog(null, "Error en ActualizarSQL  en la  funcion Guardar_Alimentacion_De_Inventario_Fisico  \n"+query+"\nSQLException:"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
		if(con != null){
			try{
				System.out.println("La transacción ha sido abortada");
				JOptionPane.showMessageDialog(null, "Error en ActualizarSQL  en la  funcion Guardar_Alimentacion_De_Inventario_Fisico \n"+query+"\nSQLException:"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
				con.rollback();
			}catch(SQLException ex){
				System.out.println(ex.getMessage());
				JOptionPane.showMessageDialog(null, "Error en ActualizarSQL  en la  funcion Guardar_Alimentacion_De_Inventario_Fisico  \n"+query+"\nSQLException:"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
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

public boolean Guardar_Finiquito(Obj_Finiquitos finiquito, String status_emplead, String observacion){
	String query = "exec sp_insert_finiquito ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?";
	
	Connection con = new Connexion().conexion();
	PreparedStatement pstmt = null;
	try {
		con.setAutoCommit(false);
		
		int folio_finiquito = busca_y_actualiza_proximo_folio(23);//FOLIO CONSECUTIVO CORRESPONDIENTE A LOS FINIQUITOS
		int i=1;
		pstmt = con.prepareStatement(query);
		
		pstmt.setInt(i,			finiquito.getFolio_empleado_scoi());
		pstmt.setString(i+=1, 	finiquito.getFolio_empleado_bms());
		pstmt.setString(i+=1,	finiquito.getEstablecimiento());
		
//		componentes De bms
		pstmt.setString(i+=1,	finiquito.getFecha_ingreso_BMS());
		pstmt.setString(i+=1,	finiquito.getFecha_baja_BMS());

		pstmt.setInt(i+=1,		finiquito.getDias_trabajados_BMS());
		pstmt.setDouble(i+=1,	finiquito.getAnios_trabajados_BMS());
		
		pstmt.setInt(i+=1,		finiquito.getDias_pendientes_de_pago_de_aguinaldo_BMS());
		pstmt.setInt(i+=1,		finiquito.getDias_pendientes_de_pago_de_semana_BMS());
		pstmt.setDouble(i+=1,	finiquito.getCuota_diario_BMS());
		
		pstmt.setDouble(i+=1,	finiquito.getSDI_BMS());
		
		pstmt.setDouble(i+=1,	finiquito.getSueldo_BMS());
		pstmt.setDouble(i+=1,	finiquito.getAguinaldo_BMS());
		
		pstmt.setDouble(i+=1,	finiquito.getVacaciones_pendientes_BMS());
		pstmt.setDouble(i+=1,	finiquito.getVacaciones_BMS());
		pstmt.setDouble(i+=1,	finiquito.getPrima_vacacional_BMS());
		
		pstmt.setDouble(i+=1,	finiquito.getGratificacion_BMS());
		pstmt.setDouble(i+=1,	finiquito.getTiempo_extra_BMS());
		pstmt.setDouble(i+=1,	finiquito.getPercepciones_BMS());
		
		pstmt.setDouble(i+=1,	finiquito.getTotal_a_pagar());
				
//		componentes De scoi
		pstmt.setString(i+=1,	finiquito.getFecha_ingreso_SCOI());
		pstmt.setString(i+=1,	finiquito.getFecha_baja_SCOI());

		pstmt.setInt(i+=1,		finiquito.getDias_trabajados_SCOI());
		pstmt.setDouble(i+=1,	finiquito.getAnios_trabajados_SCOI());
		
		pstmt.setInt(i+=1,		finiquito.getDias_pendientes_de_pago_de_aguinaldo_SCOI());
		pstmt.setInt(i+=1,		finiquito.getDias_pendientes_de_pago_de_semana_SCOI());
		pstmt.setDouble(i+=1,	finiquito.getCuota_diario_SCOI());
		
		pstmt.setDouble(i+=1,	finiquito.getSueldo_SCOI());
		pstmt.setDouble(i+=1,	finiquito.getAguinaldo_SCOI());
		
		pstmt.setDouble(i+=1,	finiquito.getVacaciones_pendientes_SCOI());
		pstmt.setDouble(i+=1,	finiquito.getVacaciones_SCOI());
		pstmt.setDouble(i+=1,	finiquito.getPrima_vacacional_SCOI());
		
		pstmt.setDouble(i+=1,	finiquito.getPercepciones_SCOI());
		
//		diferencias (GRATIFICACION)
		pstmt.setDouble(i+=1,	finiquito.getSueldo_gratif());
		pstmt.setDouble(i+=1,	finiquito.getAguinaldo_gratif());
		
		pstmt.setDouble(i+=1,	finiquito.getVacaciones_pendientes_gratif());
		pstmt.setDouble(i+=1,	finiquito.getVacaciones_gratif());
		pstmt.setDouble(i+=1,	finiquito.getPrima_vacacional_gratif());
		
		pstmt.setDouble(i+=1,	finiquito.getPercepciones_gratif());
		
//		Deducciones
		pstmt.setDouble(i+=1,	finiquito.getPretamo());
		pstmt.setDouble(i+=1,	finiquito.getCortes());
		pstmt.setDouble(i+=1,	finiquito.getInfonavit());
		pstmt.setDouble(i+=1,	finiquito.getInfonacot());
		pstmt.setDouble(i+=1,	finiquito.getFuente_sodas());
		pstmt.setDouble(i+=1,	finiquito.getOtras_deducciones());
		
		pstmt.setInt(i+=1,		usuario.getFolio());
		
//		modificacion de status de empleado
		pstmt.setString(i+=1,	status_emplead);
		pstmt.setString(i+=1,	observacion);
		
		pstmt.setInt(i+=1,	folio_finiquito);
		
		pstmt.executeUpdate();
		con.commit();
		
	} catch (Exception e) {
		System.out.println("SQLException: " + e.getMessage());
		JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Finiquito ] Insert  SQLException: sp_insert_finiquito "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		if (con != null){
			try {
				System.out.println("La transacción ha sido abortada");
				con.rollback();
			} catch(SQLException ex) {
				System.out.println(ex.getMessage());
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Finiquito ] Insert  SQLException: sp_insert_finiquito "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
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

public boolean Guardar_Bono_puntualidad_y_asistencia(Obj_Bono_Puntualidad_Y_Asistencia bono){
	int folio_transaccion=busca_y_actualiza_proximo_folio(38);
	String query = "exec sp_insert_bono_puntualidad_y_asistencia ?,?,?,?";
	Connection con = new Connexion().conexion();
	PreparedStatement pstmt = null;
	try {
		con.setAutoCommit(false);
		pstmt = con.prepareStatement(query);
		pstmt.setFloat(1, folio_transaccion);
		pstmt.setFloat(2, bono.getBono());
		pstmt.setString(3, bono.getAbreviatura().toUpperCase());
		pstmt.setString(4, (bono.getStatus()==true)?"1":"0");
		pstmt.executeUpdate();
		con.commit();
	} catch (Exception e) {
		System.out.println("SQLException: "+e.getMessage());
		JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Bono_puntualidad_y_asistencia ] Insert  SQLException: sp_insert_bono "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		
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

public boolean traspaso_de_movimientos_de_cascos(){
	String query = "exec sp_traspaso_de_movimientos_de_cascos_validacion "+usuario.getFolio();
	Connection con = new Connexion().conexion();
	PreparedStatement pstmt = null;
	try {
		con.setAutoCommit(false);
		pstmt = con.prepareStatement(query);
		pstmt.executeUpdate();
		con.commit();
	} catch (Exception e) {
		JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ traspaso_de_movimientos_de_cascos ] Insert  SQLException: sp_traspaso_de_movimientos_de_cascos_validacion "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		
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

public boolean Guardar_Pago_cascos(Obj_Venta_De_Cascos_A_Proveedores pago_cascos){
	int folio_transaccion=busca_y_actualiza_proximo_folio(24);
	pago_cascos.setFolio_pago_casco(folio_transaccion+"");
	
	String query = "exec sp_insert_pago_cascos_proveedores_movimiento ?,?,?,?,?,?,?";
	String query2 = "exec sp_insert_pago_cascos_proveedores ?,?,?,"+usuario.getFolio();
	Connection con = new Connexion().conexion();
	
	try {
		con.setAutoCommit(false);
		
		PreparedStatement pstmt = con.prepareStatement(query);
		PreparedStatement pstmtabla = con.prepareStatement(query2);
		
		pstmt.setInt   (1, folio_transaccion);
		pstmt.setString(2, pago_cascos.getCod_prv().trim());
		pstmt.setString(3, pago_cascos.getNombre_proveedor().trim());
		pstmt.setString(4, pago_cascos.getNombre_proveedor_recibe().trim());
		pstmt.setString(5, pago_cascos.getTotal());
		pstmt.setInt(6, usuario.getFolio());
		pstmt.setString(7, pago_cascos.getFolio_nota());
		
		for(int i=0; i<pago_cascos.getTabla_obj().length; i++){
			pstmtabla.setInt(1, folio_transaccion);
			pstmtabla.setString(2, pago_cascos.getTabla_obj()[i][0].toString().trim());
			pstmtabla.setInt(3, Integer.valueOf(pago_cascos.getTabla_obj()[i][2].toString().trim()));
			pstmtabla.executeUpdate();
		}

		pstmt.executeUpdate();
		con.commit();

	} catch (Exception e) {
		System.out.println("SQLException: "+e.getMessage());
		JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Pago_cascos ]\n"+query+"\n"+query2+"\nSQLException:"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
		if(con != null){
			try{
				System.out.println("La transacción ha sido abortada");
				con.rollback();
			}catch(SQLException ex){
				System.out.println(ex.getMessage());
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Pago_cascos ]\n"+query+"\n"+query2+"\nSQLException:"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
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

public boolean Guardar_Cambio_De_Status_De_Producto(String folio_producto,String status){
	
	String STS = status.equals("VIGENTE")?"V":"C";
	String query = "insert into tb_cambio_de_status_en_prod_estab(cod_prod,usuario,fecha,status) "
													+ "values('"+folio_producto+"',"+usuario.getFolio()+",getdate(),'"+STS+"')";
	
	String queryExterno = "DECLARE @status VARCHAR(15) "
						+ " 	set @status = '"+status+"' "
						+ " UPDATE prodestab SET prodestab.status_producto=(case when(@status)='CANCELADO' then 2 else 1 end ) "
						+ " where prodestab.cod_estab = 15 and ltrim(rtrim(prodestab.cod_prod)) = '"+folio_producto+"' ";
	Connection con = new Connexion().conexion();
	Connection conExterna = new Connexion().conexion_IZAGAR();
	
	try {
		con.setAutoCommit(false);
		
		PreparedStatement pstmt = con.prepareStatement(query);
		PreparedStatement pstmExterno = conExterna.prepareStatement(queryExterno);
		
		pstmExterno.executeUpdate();
		pstmt.executeUpdate();
		
		con.commit();

	} catch (Exception e) {
		System.out.println("SQLException: "+e.getMessage());
		JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Pago_cascos ]\n"+query+"\n"+queryExterno+"\nSQLException:"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
		if(con != null){
			try{
				System.out.println("La transacción ha sido abortada");
				con.rollback();
			}catch(SQLException ex){
				System.out.println(ex.getMessage());
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Pago_cascos ]\n"+query+"\n"+queryExterno+"\nSQLException:"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
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

public boolean Guardar_Recepcion(Object[][] productos, String folio_transferencia, String folio_estab_origen, String folio_estab_destino, String chofer, String cincho){
//	int folio_transaccion=busca_y_actualiza_proximo_folio(24);
	String query = "exec sp_insert_tranferencia ?,?,?,?,?,?,?,?,?,"+usuario.getFolio()+",'"+folio_transferencia+"','"+folio_estab_origen+"','"+folio_estab_destino+"','"+chofer+"','"+cincho+"'";
	Connection con = new Connexion().conexion();
	
	try {
		con.setAutoCommit(false);
		
		PreparedStatement pstmt = con.prepareStatement(query);
		
		for(int i=0; i<productos.length; i++){
			for(int j=0; j<10; j++){

				if(j!=2){
					if(j>2){
						pstmt.setDouble(i+1, Double.valueOf(productos[i][j].toString()));
					}else{
						pstmt.setString(i+1, productos[i][j].toString());
					}
				}
				
//				if(j==9){
//					System.out.println(productos[i][j].toString()+"   ");
//				}else{
//					System.out.print(productos[i][j].toString()+"   ");
//				}
				
				
			}
			
			
			pstmt.executeUpdate();
		}

//		pstmt.executeUpdate();
		con.commit();

	} catch (Exception e) {
		System.out.println("SQLException: "+e.getMessage());
		JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Pago_cascos ]\n"+query+"\nSQLException:"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
		if(con != null){
			try{
				System.out.println("La transacción ha sido abortada");
				con.rollback();
			}catch(SQLException ex){
				System.out.println(ex.getMessage());
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Pago_cascos ]\n"+query+"\nSQLException:"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
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

public boolean Guardar_Meta_Mensual_De_Venta(Obj_Alimentacion_De_Meta_Mensual_De_Venta meta_mensual, String movimiento){
	
	int folio_transaccion=movimiento.equals("CANCELAR")?Integer.valueOf(meta_mensual.getFolio()):busca_y_actualiza_proximo_folio(29);
	
	String query = "exec sp_insert_meta_mensual_de_venta ?,?,?,?,?,?,? ";
	Connection con = new Connexion().conexion();
	try {
		con.setAutoCommit(false);
		PreparedStatement pstmt = con.prepareStatement(query);
		
		pstmt.setInt(1, folio_transaccion);
		pstmt.setInt(2, meta_mensual.getAnios());
		pstmt.setString(3, meta_mensual.getMes());
		pstmt.setString(4, meta_mensual.getEstablecimiento().toUpperCase().trim());
		pstmt.setInt(5, new Obj_Usuario().LeerSession().getFolio());
		pstmt.setDouble(6, meta_mensual.getMeta_mensual());
		pstmt.setString(7, movimiento);
		
		pstmt.executeUpdate();
		con.commit();
	} catch (Exception e) {
		System.out.println("SQLException: " + e.getMessage() +"   >   "+ e.getLocalizedMessage() );
		if (con != null){
			try {
				System.out.println("La transacción ha sido abortada");
				con.rollback();
			} catch(SQLException ex) {
				System.out.println(ex.getMessage());
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Establecimiento ] Insert  SQLException: sp_insert_establecimiento "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);

			}
		} 
		return false;
	}finally{
		try {
			con.close();
		} catch(SQLException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Establecimiento ] Insert  SQLException: sp_insert_establecimiento "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
	}		
	return true;
}

public boolean Guardar_Configuracion_De_Meta_Mensual_De_Venta(Obj_Configuracion_Meta_Mensual_De_Ventas conf, String movimiento, int folio_a_cancelar){
	
	int folio_transaccion=movimiento.equals("CANCELAR")?folio_a_cancelar:busca_y_actualiza_proximo_folio(30);
	
	String query = "exec sp_insert_configuracion_de_meta_mensual_de_venta ?,?,?,?,?,?,?,?,?,?,?,? ";
	Connection con = new Connexion().conexion();
	try {
		con.setAutoCommit(false);
		PreparedStatement pstmt = con.prepareStatement(query);
		
		pstmt.setInt(1, folio_transaccion);
		pstmt.setString(2, conf.getNombre().toUpperCase().trim());
		pstmt.setString(3, conf.getEstablecimiento().trim());
		pstmt.setString(4, conf.getTipo_de_reporte());
		pstmt.setString(5, conf.getProductos().trim());
		pstmt.setString(6, conf.getClases().trim());
		pstmt.setString(7, conf.getCategorias().trim());
		pstmt.setString(8, conf.getFamilias().trim());
		pstmt.setString(9, conf.getLineas().trim());
		pstmt.setString(10, conf.getTallas().trim());
		pstmt.setInt(11, new Obj_Usuario().LeerSession().getFolio());
		pstmt.setString(12, movimiento);
		
		pstmt.executeUpdate();
		con.commit();
	} catch (Exception e) {
		System.out.println("SQLException: " + e.getMessage() +"   >   "+ e.getLocalizedMessage() );
		if (con != null){
			try {
				System.out.println("La transacción ha sido abortada");
				con.rollback();
			} catch(SQLException ex) {
				System.out.println(ex.getMessage());
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Establecimiento ] Insert  SQLException: sp_insert_establecimiento "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);

			}
		} 
		return false;
	}finally{
		try {
			con.close();
		} catch(SQLException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Establecimiento ] Insert  SQLException: sp_insert_establecimiento "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
	}		
	return true;
}

public boolean guardar_motivos_de_renuncia(int folioEmp,String estab,String depto, String puesto,int jefeInmediato, String fecha_baja
											,boolean sueldo,boolean horario,boolean relacionJefe,boolean ambienteLaboral,boolean capacitacion,boolean descuentoNomina
											,boolean problemaPersonal,boolean abandono_trabajo,boolean otros
											,String descripcionMotivo,String respuesta1,String respuesta2,String respuesta3,String respuesta4,String respuesta5){
	String query = "exec sp_insert_encuesta_y_motivo_de_renuncia ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?";
	Connection con = new Connexion().conexion();
	PreparedStatement pstmt = null;
	try {
		con.setAutoCommit(false);
		pstmt = con.prepareStatement(query);
		
		pstmt.setInt(1, folioEmp);
		pstmt.setString(2, estab);
		pstmt.setString(3, depto);
		pstmt.setString(4, puesto);
		pstmt.setInt(5, jefeInmediato);
		pstmt.setString(6, fecha_baja);
		pstmt.setString(7, sueldo==true?"S":"N");
		pstmt.setString(8, horario==true?"S":"N");
		pstmt.setString(9, relacionJefe==true?"S":"N");
		pstmt.setString(10, ambienteLaboral==true?"S":"N");
		pstmt.setString(11, capacitacion==true?"S":"N");
		pstmt.setString(12, descuentoNomina==true?"S":"N");
		pstmt.setString(13, problemaPersonal==true?"S":"N");
		pstmt.setString(14, otros==true?"S":"N");
		
		pstmt.setString(15, descripcionMotivo);
		pstmt.setString(16, respuesta1);
		pstmt.setString(17, respuesta2);
		pstmt.setString(18, respuesta3);
		pstmt.setString(19, respuesta4);
		pstmt.setString(20, respuesta5);
		pstmt.setInt(21, new Obj_Usuario().LeerSession().getFolio());
		pstmt.setString(22, abandono_trabajo==true?"S":"N");
		
		pstmt.executeUpdate();
		con.commit();
	} catch (Exception e) {
		System.out.println("SQLException: "+e.getMessage());
		JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ guardar_motivos_de_renuncia ] "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
		if(con != null){
			try{
				System.out.println("La transacción ha sido abortada");
				con.rollback();
			}catch(SQLException ex){
				System.out.println(ex.getMessage());
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ guardar_motivos_de_renuncia ] "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			}
		}
		return false;
	}finally{
		try {
			con.close();
		} catch(SQLException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ guardar_motivos_de_renuncia ] "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
		}
	}		
	return true;
}

public boolean Guardar_ConfigBD_3(Obj_Configuracion_Base_de_Datos_3 config){
	BufferedWriter bufferedWriter = null;
	String nomArchivo = System.getProperty("user.dir")+"\\Config\\config3";
	try{
		File archivo = new File(nomArchivo);
		if(archivo.exists()){
			bufferedWriter = new BufferedWriter (new FileWriter(nomArchivo));
			
			bufferedWriter.write(config.getDireccionIPV4()+    		"\n");
			bufferedWriter.write(config.getNombreBD()+      		"\n");
			bufferedWriter.write(config.getUsuario()+ 	    		"\n");
			bufferedWriter.write(config.getContrasena()+       		"\n");
			
		}else{
			File folder = new File(System.getProperty("user.dir")+"\\Config3");
			folder.mkdirs();
			archivo.createNewFile();
			bufferedWriter = new BufferedWriter (new FileWriter(nomArchivo));
			
			bufferedWriter.write(config.getDireccionIPV4()+    		"\n");
			bufferedWriter.write(config.getNombreBD()+      		"\n");
			bufferedWriter.write(config.getUsuario()+ 	    		"\n");
			bufferedWriter.write(config.getContrasena()+       		"\n");
			
		}
		
	}
	catch(FileNotFoundException ex)
	{
		ex.printStackTrace();
	}catch(IOException ex)
	{
		ex.printStackTrace();
	}finally
	{
		try
		{
			if(bufferedWriter!=null)
			{
				bufferedWriter.flush();
				bufferedWriter.close();
			}
		}catch(IOException ex)
		{
			ex.printStackTrace();
		}
	}return true;
}


public boolean Guardar_servicios_establecimientos(String Descrpcion,String NumCtrol,String Clasificador,String status,String Establecimiento){
	int folio = new GuardarSQL().busca_y_actualiza_proximo_folio(32);
	
	String query =  "EXEC sp_insert_servicios_establecimientos ?,?,?,?,?,?,?";
	Connection con = new Connexion().conexion();
	try {
		con.setAutoCommit(false);
		PreparedStatement pstmt = con.prepareStatement(query);
		
		pstmt.setInt   (1, usuario.getFolio());
		pstmt.setInt   (2, folio);
		pstmt.setString(3, NumCtrol.toString().trim());
		pstmt.setString(4, Clasificador.toString().trim());
		pstmt.setString(5, status.toString().trim());
		pstmt.setString(6, Establecimiento.toString().trim());
		pstmt.setString(7, Descrpcion.toString().trim());
		
		pstmt.executeUpdate();
		con.commit();
	} catch (Exception e) {
		System.out.println("SQLException: " + e.getMessage() +"   >   "+ e.getLocalizedMessage() );
		if (con != null){
			try {
				System.out.println("La transacción ha sido abortada");
				con.rollback();
			} catch(SQLException ex) {
				System.out.println(ex.getMessage());
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Establecimiento ] Insert  SQLException: sp_insert_establecimiento "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);

			}
		} 
		return false;
	}finally{
		try {
			con.close();
		} catch(SQLException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Establecimiento ] Insert  SQLException: sp_insert_establecimiento "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
	}		
	return true;
}

public boolean Modificar_servios_establecimientos(String folio,String Descrpcion,String NumCtrol,String Clasificador,String status,String Establecimiento){

	String querynew = "delete from tb_servicios_de_establecimientos where folio="+folio+"";
	String query =  "  EXEC sp_insert_servicios_establecimientos ?,?,?,?,?,?,?";
	Connection con = new Connexion().conexion();
	PreparedStatement pstmt = null;
	PreparedStatement pstmt2 = null;
	try {
		con.setAutoCommit(false);
		pstmt2 = con.prepareStatement(querynew);
		pstmt2.execute();
	    pstmt = con.prepareStatement(query);
		
		pstmt.setInt   (1, usuario.getFolio());
		pstmt.setInt   (2, Integer.parseInt(folio));
		pstmt.setString(3, NumCtrol.toString().trim());
		pstmt.setString(4, Clasificador.toString().trim());
		pstmt.setString(5, status.toString().trim());
		pstmt.setString(6, Establecimiento.toString().trim());
		pstmt.setString(7, Descrpcion.toString().trim());
		
		pstmt.execute();
		con.commit();
	} catch (Exception e) {
		System.out.println("SQLException: " + e.getMessage() +"   >   "+ e.getLocalizedMessage() );
		if (con != null){
			try {
				System.out.println("La transacción ha sido abortada");
				con.rollback();
			} catch(SQLException ex) {
				System.out.println(ex.getMessage());
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar Serviicos Establecimientos ] Insert  SQLException: sp_insert_establecimiento "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);

			}
		} 
		return false;
	}finally{
		try {
			con.close();
		} catch(SQLException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [Guardar Serviicos Establecimientos  ] Insert  SQLException: sp_insert_establecimiento "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
	}		
	return true;
}

public boolean Guardar_Perfil_De_Puesto(Obj_Perfil_De_Puestos perfil,String movimiento){
	String query = "exec sp_insert_perfil_de_puestos ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,'"+movimiento+"'";
	
	Connection con = new Connexion().conexion();
	PreparedStatement pstmt = null;
	try {
		con.setAutoCommit(false);
		
		int i=1;
//		--------------------------------------------------------------------------------------------------------------------------------
		pstmt = con.prepareStatement(query);
		
		pstmt.setInt(i, 	perfil.getFolio());
		pstmt.setString(i+=1, 	perfil.getPerfil().toUpperCase());
		pstmt.setString(i+=1, 		perfil.getSexo());
		pstmt.setString(i+=1, 	perfil.getEdad());	
		pstmt.setString(i+=1, 	perfil.getPuesto_al_que_reporta());
//		--------------------------------------------------------------------------------------------------------------------------------
	
		pstmt.setString(i+=1, 		perfil.getEstablecimiento());
		pstmt.setString(i+=1, 		perfil.getDepartameto());	
		pstmt.setString(i+=1, 		perfil.getPuesto());
		pstmt.setString(i+=1, 		perfil.getNivel_de_puesto());		
//		--------------------------------------------------------------------------------------------------------------------------------

		pstmt.setInt(i+=1, 		perfil.getHorario());
		pstmt.setInt(i+=1, 		perfil.getHorario2());
		pstmt.setInt(i+=1,		perfil.getHorario3());
		pstmt.setInt(i+=1, 		perfil.getStatus_h1());
		pstmt.setInt(i+=1, 		perfil.getStatus_h2());
		pstmt.setInt(i+=1, 		perfil.getStatus_h3());
		pstmt.setInt(i+=1, 		perfil.getStatus_rotativo());
//		--------------------------------------------------------------------------------------------------------------------------------
		
		pstmt.setFloat(i+=1, 	perfil.getSalario_diario());
		pstmt.setFloat(i+=1, 	perfil.getSalario_diario_integrado());
		pstmt.setFloat(i+=1,   perfil.getSueldo());
		pstmt.setFloat(i+=1,   perfil.getBonocomplemento());
		pstmt.setFloat(i+=1,   perfil.getBono_asistencia());
		pstmt.setFloat(i+=1, 	perfil.getBono_puntualidad());
		pstmt.setInt(i+=1, 		perfil.getPrestamo());
		pstmt.setInt(i+=1, (perfil.isGafete())? 1: 0);
				
//		--------------------------------------------------------------------------------------------------------------------------------
		pstmt.setString(i+=1, 	perfil.getObjetivo_del_puesto().toUpperCase());
		pstmt.setString(i+=1, 	perfil.getExperiencia().toUpperCase());
		pstmt.setString(i+=1, 	perfil.getActividades_Principales().toUpperCase());
		pstmt.setString(i+=1, 	perfil.getHabilidades().toUpperCase());
		pstmt.setString(i+=1, 	perfil.getConocimiento().toUpperCase());
		
		pstmt.setInt(i+=1, 	usuario.getFolio());
		pstmt.executeUpdate();
		con.commit();
		
	} catch (Exception e) {
		System.out.println("SQLException: " + e.getMessage());
		JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Perfil_De_Puesto ] Insert  SQLException: ---------sp_insert_empleado--------- "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		if (con != null){
			try {
				System.out.println("La transacción ha sido abortada");
				con.rollback();
			} catch(SQLException ex) {
				System.out.println(ex.getMessage());
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Perfil_De_Puesto ] Insert  SQLException: ---------sp_insert_empleado--------- "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
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

public boolean Guardar_Registro_De_Desarollo(Obj_Registro_De_Desarrollo registro, String movimiento){
	int folio = movimiento.equals("GUARDAR") ? new GuardarSQL().busca_y_actualiza_proximo_folio(33) : registro.getFolio_registro();
	
	String query =  "";
	Connection con = new Connexion().conexion();
	PreparedStatement pstmt = null;
	
	try {
		
		con.setAutoCommit(false);
		
		query =  "EXEC sp_insert_registros_de_desarrollo ?,?,?,?,?,?,?,?";
		pstmt = con.prepareStatement(query);
		pstmt.setInt   (1, folio);
		pstmt.setInt   (2, registro.getUsuario_solicitante());
		pstmt.setInt   (3, registro.getUsuario_atendio());
		pstmt.setString(4, registro.getMejoras().toUpperCase());
		pstmt.setString(5, registro.getFuncionalidad().toUpperCase());
		pstmt.setString(6, registro.getSugerencias().toUpperCase());
		pstmt.setInt   (7, usuario.getFolio());
		pstmt.setString   (8, movimiento);
		pstmt.executeUpdate();
		
			query =  "EXEC sp_insert_servicios_establecimientos ?,?,?,?,?,?,?";
			pstmt = con.prepareStatement(query);
		
			for(int i = 0; i<registro.getAfectados().length; i++){
				
				pstmt.setInt	(1, folio);
				pstmt.setInt	(2, Integer.valueOf(registro.getAfectados()[i][0].toString()));
				pstmt.setDouble (3, Double.valueOf(registro.getAfectados()[i][2].toString()));
				pstmt.setDouble	(4, Double.valueOf(registro.getAfectados()[i][3].toString()));
				pstmt.setInt	(5, Integer.valueOf(registro.getAfectados()[i][4].toString().equals("")?"0":registro.getAfectados()[i][4].toString()));
				pstmt.setInt	(6, Integer.valueOf(registro.getAfectados()[i][5].toString().equals("")?"0":registro.getAfectados()[i][5].toString()));
				
				pstmt.executeUpdate();
			}
		
		con.commit();
	} catch (Exception e) {
		System.out.println("SQLException: " + e.getMessage() +"   >   "+ e.getLocalizedMessage() );
		if (con != null){
			try {
				System.out.println("La transacción ha sido abortada");
				con.rollback();
			} catch(SQLException ex) {
				System.out.println(ex.getMessage());
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Registro_De_Desarollo ] Insert  SQLException: sp_insert_establecimiento "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);

			}
		} 
		return false;
	}finally{
		try {
			con.close();
		} catch(SQLException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Registro_De_Desarollo ] Insert  SQLException: sp_insert_establecimiento "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
	}		
	return true;
}

public boolean Cargar_Inventario(String establecimiento,String bandera){
	
	String query =  "EXEC sp_insert_inventario_de_gestion_de_pedidos ?,?,?";
	Connection con = new Connexion().conexion();
	PreparedStatement pstmt = null;
	
	try {
		
		con.setAutoCommit(false);
		pstmt = con.prepareStatement(query);
		
		pstmt.setString(1, establecimiento);
		pstmt.setInt   (2, usuario.getFolio());
		pstmt.setString(3, bandera);
		pstmt.executeUpdate();
		
		con.commit();
	} catch (Exception e) {
		System.out.println("SQLException: " + e.getMessage() +"   >   "+ e.getLocalizedMessage() );
		if (con != null){
			try {
				System.out.println("La transacción ha sido abortada");
				con.rollback();
			} catch(SQLException ex) {
				System.out.println(ex.getMessage());
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Cargar_Inventario ] Insert  SQLException: sp_insert_inventario_de_gestion_de_pedidos "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);

			}
		} 
		return false;
	}finally{
		try {
			con.close();
		} catch(SQLException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Cargar_Inventario ] Insert  SQLException: sp_insert_inventario_de_gestion_de_pedidos "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
	}		
	return true;
}

public boolean GuardarPedido(Obj_Gestion_De_Pedidos_A_Establecimientos pedido,String movimiento){
	String query =  "EXEC sp_guardar_gestion_de_pedido ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?";
	Connection con = new Connexion().conexion();
	PreparedStatement pstmt = null;
	String query2 =" declare @folio_pedido varchar(15) , @cod_prod varchar(15), @surtido float,@partida int"
			+ "    set @folio_pedido= ? "
			+ "    set @cod_prod= ? "
			+ "    set @surtido=  ? "
			+ "    set @partida=  ? "
			+ " if @surtido=0.0"
			+ "    begin"
			+ "  	 delete from mpedestab where LTRIM(RTRIM(folio)) = LTRIM(RTRIM(@folio_pedido)) and LTRIM(RTRIM(cod_prod))=LTRIM(RTRIM(@cod_prod)) AND LTRIM(RTRIM(transaccion))='29'"
			+ "    end"
			+ " else"
			+ "   begin"
			+ "  DECLARE @importe money,@iva money,@ieps money,@costo money"
			+ "		 select @importe=(importe/cantidad_pedida)*@surtido"
			+ "				,@iva=(iva/cantidad_pedida)*@surtido"
			+ " 			,@ieps=(ieps/cantidad_pedida)*@surtido"
			+ " 			,@costo=(costo/cantidad_pedida)*@surtido"
			+ "			from mpedestab"
			+ "   where LTRIM(RTRIM(folio)) = LTRIM(RTRIM(@folio_pedido)) and LTRIM(RTRIM(cod_prod))=LTRIM(RTRIM(@cod_prod)) AND LTRIM(RTRIM(transaccion))='29'"
			
			+ "	 update mpedestab set cantidad_pedida=@surtido, cantidad_autorizada=@surtido, importe=@importe, iva=@iva, ieps=@ieps, costo=@costo, partida=@partida"
			+ "	   where LTRIM(RTRIM(folio)) = LTRIM(RTRIM(@folio_pedido)) and LTRIM(RTRIM(cod_prod))=LTRIM(RTRIM(@cod_prod)) AND LTRIM(RTRIM(transaccion))='29'"
			+ " end ";
	
//	String query2 ="exec sp_IZAGAR_gestion_de_pedido ?,?,?,?";
	Connection con2 = new Connexion().conexion_IZAGAR();
	PreparedStatement pstmt2 = null;
	try {
		 con.setAutoCommit(false);
		 pstmt = con.prepareStatement(query);
		 con2.setAutoCommit(false);
		 pstmt2 = con2.prepareStatement(query2);
	   int user = usuario.getFolio();
		
		for(int i=0; i<pedido.getMatriz().length; i++){
				pstmt.setString  (1, pedido.getFolio_pedido());
				pstmt.setString  (2, pedido.getOrigen());
				pstmt.setString  (3, pedido.getDestino());
				pstmt.setString  (4, pedido.getUsuario());
				pstmt.setString  (5, pedido.getClasificador());
				pstmt.setString  (6, pedido.getStatus_pedido());
				pstmt.setString  (7, pedido.getMatriz()[i][0].toString());
				pstmt.setFloat   (8, Float.valueOf(pedido.getMatriz()[i][2].toString()));
				pstmt.setFloat   (9, Float.valueOf(pedido.getMatriz()[i][3].toString()));
				pstmt.setFloat   (10, Float.valueOf(pedido.getMatriz()[i][4].toString()));
				pstmt.setFloat   (11, Float.valueOf(pedido.getMatriz()[i][5].toString()));
				pstmt.setString  (12, pedido.getMatriz()[i][6].toString());
				pstmt.setInt     (13, user);
				pstmt.setString  (14, pedido.getMatriz()[i][1].toString());
				pstmt.setString  (15, pedido.getMatriz()[i][7].toString());
				pstmt.setInt     (16, Integer.valueOf(pedido.getMatriz()[i][8].toString()));
				
			pstmt.executeUpdate();
		}
		
		if(pedido.getStatus_pedido().equals("S")){
		for(int i2=0; i2<pedido.getMatriz().length; i2++){
			pstmt2.setString (1, pedido.getFolio_pedido());
			pstmt2.setString (2, pedido.getMatriz()[i2][0].toString());
			pstmt2.setFloat  (3, Float.valueOf(pedido.getMatriz()[i2][4].toString()));
			pstmt2.setInt    (4, Integer.valueOf(pedido.getMatriz()[i2][8].toString()));
         pstmt2.executeUpdate();
	     }
		con2.commit();	
		}
		con.commit();
	} catch (Exception e) {
		System.out.println("SQLException: " + e.getMessage() +"   >   "+ e.getLocalizedMessage() );
		if (con != null){
			try {
				System.out.println("La transacción ha sido abortada");
				con.rollback();
			} catch(SQLException ex) {
				System.out.println(ex.getMessage());
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ GuardarPedido ] Insert  SQLException: sp_guardar_gestion_de_pedido "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			}
		} 
		return false;
	}finally{
		try {
			con.close();
		} catch(SQLException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Cargar_Inventario ] Insert  SQLException: sp_insert_inventario_de_gestion_de_pedidos "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
	}		
	return true;
}

public boolean Finalizar_Pedido(String folio_pedido){
	
//	String query =  "delete from mpedestab where LTRIM(RTRIM(folio)) = LTRIM(RTRIM(@folio_pedido)) AND LTRIM(RTRIM(transaccion))='29'";	
	String query = " update tb_gestion_de_pedido "
			         + "		set	surtido=0, "
			         + "		pendiente=pedido, "
			         + "		fecha_de_modificacion = GETDATE(), "
			         + "		status='S' , "
			         + "		ajuste= (pedido*-1) "
			         + " where LTRIM(RTRIM(folio_pedido)) = LTRIM(RTRIM('"+folio_pedido+"'))";
	Connection con = new Connexion().conexion();
	PreparedStatement pstmt = null;
	try {
		con.setAutoCommit(false);
		pstmt = con.prepareStatement(query);
		
		pstmt.executeUpdate();
		con.commit();
		
	} catch (Exception e) {
		System.out.println("SQLException: " + e.getMessage());
		JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Finalizar_Pedido ] "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		if (con != null){
			try {
				System.out.println("La transacción ha sido abortada");
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Finalizar_Pedido ] "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				con.rollback();
			} catch(SQLException ex) {
				System.out.println(ex.getMessage());
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Finalizar_Pedido ] "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			}
		} 
		return false;
	}finally{
		try {
			pstmt.close();
			con.close();
		} catch(SQLException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Finalizar_Pedido ] "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
	}		
	
	return true;
}


public boolean Guardar_Asignacion_De_Pedido(Object[][] asignacion,String folio_pedido){
	
//	String query =  "EXEC sp_insert_asignacion_de_pedido_por_paginas ?,?,?,?";
	String query =  "EXEC sp_insert_asignacion_de_pedido_por_zonas ?,?,?,?,?";
	
	Connection con = new Connexion().conexion();
	PreparedStatement pstmt = null;
	
	try {
		con.setAutoCommit(false);
		pstmt = con.prepareStatement(query);
		
		System.out.print(asignacion.length);
		for(int i=0; i<asignacion.length; i++){
				pstmt.setString(1, folio_pedido);
				pstmt.setInt   (2, usuario.getFolio());
				pstmt.setString(3, asignacion[i][0].toString());
				pstmt.setInt   (4, Integer.valueOf(asignacion[i][1].toString().equals("")?"0":asignacion[i][1].toString()));
				pstmt.setString(5, asignacion[i][2].toString());
				
				pstmt.executeUpdate();
		}
		
		con.commit();
	} catch (Exception e) {
		System.out.println("SQLException: " + e.getMessage() +"   >   "+ e.getLocalizedMessage() );
		if (con != null){
			try {
				System.out.println("La transacción ha sido abortada");
				con.rollback();
			} catch(SQLException ex) {
				System.out.println(ex.getMessage());
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ GuardarPedido ] Insert  SQLException: sp_guardar_gestion_de_pedido "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			}
		} 
		return false;
	}finally{
		try {
			con.close();
		} catch(SQLException e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Cargar_Inventario ] Insert  SQLException: sp_insert_inventario_de_gestion_de_pedidos "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
	}		
	return true;
}

public boolean Guardar_inventarios_parciales(Obj_Alimentacion_De_Inventarios_Parciales inventarios_parciales){
	int folio_transaccion=busca_y_actualiza_proximo_folio(70);
	int folio_usuario=usuario.getFolio();
	inventarios_parciales.setFolio(folio_transaccion+"");
	
	String query = "exec sp_insert_inventario_parcial_fisico ?,?,?,?,?,?,?,?,?,?,?";
	Connection con = new Connexion().conexion();
	
	try {
		con.setAutoCommit(false);
		PreparedStatement pstmt = con.prepareStatement(query);
		
//		@folio_inventario_parcial int, @cod_prod varchar(10),@existencia float, @existencia_fisica float, @diferencia float,@ultimo_costo money, @costo_promedio money, @usuario_capturo int, @status char(1), @notas varchar(max)
		for(int i=0; i<inventarios_parciales.getTabla_obj().length; i++){
			pstmt.setInt   (1 ,  folio_transaccion);
			pstmt.setString(2 ,  inventarios_parciales.getTabla_obj()[i][0].toString().trim());
			pstmt.setFloat (3 ,  Float.valueOf(inventarios_parciales.getTabla_obj()[i][2].toString().trim()));
			pstmt.setFloat (4 ,  Float.valueOf(inventarios_parciales.getTabla_obj()[i][3].toString().trim()));
			pstmt.setFloat (5 ,  Float.valueOf(inventarios_parciales.getTabla_obj()[i][4].toString().trim()));
			pstmt.setFloat (6 ,  Float.valueOf(inventarios_parciales.getTabla_obj()[i][5].toString().trim()));
			pstmt.setFloat (7 ,  Float.valueOf(inventarios_parciales.getTabla_obj()[i][6].toString().trim()));
			pstmt.setInt   (8 ,  folio_usuario);
			pstmt.setString(9 ,  inventarios_parciales.getEstablecimiento());
			pstmt.setString(10 , inventarios_parciales.getStatus());
			pstmt.setString(11,  inventarios_parciales.getNotas().trim());
			pstmt.executeUpdate();
		}
		con.commit();
	} catch (Exception e) {
		System.out.println("SQLException: "+e.getMessage());
		JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_inventarios_parciales ]\n"+query+"\nSQLException:"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
		if(con != null){
			try{
				System.out.println("La transacción ha sido abortada");
				con.rollback();
			}catch(SQLException ex){
				System.out.println(ex.getMessage());
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_inventarios_parciales ]\n"+query+"\nSQLException:"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
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

public boolean Guardar_Salida_De_Embarque_De_Pedido(Obj_Chacador_De_Embarque_De_Pedidos ped){
	String query = "exec sp_insert_historial_embarque_de_pedido ?,?,?,?,?,?,?,?,?";
	
	Connection con = new Connexion().conexion();
	PreparedStatement pstmt = null;
	try {
		con.setAutoCommit(false);
		
		String pc = InetAddress.getLocalHost().getHostName();
		String ip = InetAddress.getLocalHost().getHostAddress();
		
		pstmt = con.prepareStatement(query);
		
		for(int fol=0; fol<ped.getFolio_transferencia().length; fol++){
			int i=1;
			pstmt.setString(i,	 	ped.getFolio_transferencia()[fol].toString());
			pstmt.setString(i+=1,	ped.getEstab_surte().trim());
			pstmt.setString(i+=1,	ped.getEstab_recibe().trim());
			pstmt.setInt(i+=1,	ped.getFolio_encagado_asigno_embarque());
			pstmt.setInt(i+=1, 	ped.getFolio_chofer());
			pstmt.setInt(i+=1, 	ped.getNo_carro());
			pstmt.setString(i+=1, 	ped.getNo_cincho());
			
			pstmt.setString(i+=1, 	ip);
			pstmt.setString(i+=1, 	pc);
			
			pstmt.executeUpdate();
		}
		con.commit();
		
	} catch (Exception e) {
		System.out.println("SQLException: " + e.getMessage());
		JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Salida_De_Embarque_De_Pedido ] Insert  SQLException: sp_insert_historial_embarque_de_pedido "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
		if (con != null){
			try {
				System.out.println("La transacción ha sido abortada");
				con.rollback();
			} catch(SQLException ex) {
				System.out.println(ex.getMessage());
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Salida_De_Embarque_De_Pedido ] Insert  SQLException: sp_insert_historial_embarque_de_pedido "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
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

public boolean Guardar_servicios_catalogo(Obj_Catalogo_Servicios servicios){
	int folio_transaccion=servicios.getFolio();
	if(servicios.getGuardar_actualizar().equals("S")){
	  folio_transaccion=busca_y_actualiza_proximo_folio(39);
	  servicios.setFolio(folio_transaccion);
	}
	String query = "exec sp_guardar_servicios_catalogo ?,?,?,?,?,?,?,?,?,?,?";
	Connection con = new Connexion().conexion();
	
	try {
		con.setAutoCommit(false);
		PreparedStatement pstmt = con.prepareStatement(query);
//		  @folio int ,@servicio varchar(150)   ,@detalle varchar(500) ,@prioridad varchar(100),@dias_estimado int ,@tiempo_estimado datetime ,@departamento varchar(100),@estatus varchar(25),@folio_usuario int ,@folio_usuario_modifico int,@guardar char(1)
			pstmt.setInt   (1 ,  folio_transaccion);
			pstmt.setString(2 ,  servicios.getServicio().toString());
			pstmt.setString(3 ,  servicios.getDetalle().toString());
			pstmt.setString(4 ,  servicios.getPrioridad().toString());
			pstmt.setInt   (5 ,  Integer.valueOf(servicios.getDias_estimado()));
			pstmt.setString(6 ,  servicios.getTiempo_estimado().toString());
			pstmt.setString(7 ,  servicios.getDepartamento().toString());
			pstmt.setString(8 ,  servicios.getEstatus().toString());
			pstmt.setInt   (9 ,  Integer.valueOf(servicios.getUsuario()));
			pstmt.setInt   (10 , Integer.valueOf(servicios.getUsuario_modifico()));
			pstmt.setString(11,  servicios.getGuardar_actualizar().toString());
			pstmt.executeUpdate();
		con.commit();
		
	} catch (Exception e) {
		System.out.println("SQLException: "+e.getMessage());
		JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_servicios_catalogo ]\n"+query+"\nSQLException:"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
		if(con != null){
			try{
				System.out.println("La transacción ha sido abortada");
				con.rollback();
			}catch(SQLException ex){
				System.out.println(ex.getMessage());
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_servicios_catalogo ]\n"+query+"\nSQLException:"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
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

public int Guardar_servicios(Obj_Servicios servicios){
	int folio_transaccion=servicios.getFolio();
	String adjunto="";
	if(servicios.getGuardar_actualizar().equals("S")){
	  folio_transaccion=busca_y_actualiza_proximo_folio(40);
	  servicios.setFolio(folio_transaccion);
	}
	
	if(servicios.getAdjunto().toString().equals("")){
		adjunto ="C:\\SCOI\\imagen\\Actions-document-encrypt-icon.png";
	}else{
		adjunto=servicios.getAdjunto().toString();
	}
	
	String query = "exec sp_guardar_servicios_2 ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?";
	Connection con = new Connexion().conexion();
	
	try {
		con.setAutoCommit(false);
		PreparedStatement pstmt = con.prepareStatement(query);
		
		String pc = InetAddress.getLocalHost().getHostName();
		String ip = InetAddress.getLocalHost().getHostAddress();
		
//		@folio int, @servicio varchar(150), @equipo varchar(150), @detalle varchar(500), @prioridad_solicito varchar(150),  @departamento_solicito varchar(150),  @establecimiento_solicito varchar(150),   @folio_usuario_solicito int,
//	    @estatus_equipo varchar(100), @folio_usuario_modifico int, @usuario_realizo_servicio varchar(350), @estatus_servicio varchar(100), @notas_servicio varchar(500),  @costo_servicio money, @valoracion_del_servicio int
//		,@Guardar_actualizar char(1)   ,@estatusv char(9)
		
			pstmt.setInt   (1 ,  folio_transaccion);
			pstmt.setString(2 ,  servicios.getServicio().toString());
			pstmt.setString(3 ,  servicios.getEquipo().toString());
			pstmt.setString(4 ,  servicios.getDetalle().toString());
			pstmt.setString(5 ,  servicios.getPrioridad().toString());
			pstmt.setString(6 ,  servicios.getDepartamento_solicito().toString());
			pstmt.setString(7 ,  servicios.getEstablecimiento_solicito().toString());
			pstmt.setInt   (8 ,  Integer.valueOf(servicios.getFolio_usuario_solicito()));
			
			pstmt.setString(9 ,  servicios.getEstatus_equipo());
			pstmt.setInt   (10,  Integer.valueOf(servicios.getFolio_usuario_modifico()));
			pstmt.setInt   (11,  Integer.valueOf(servicios.getUsuario_realizo_servicio()));
			
			pstmt.setString(12,  servicios.getNotas_servicio());
			pstmt.setDouble(13,  Double.valueOf(servicios.getCosto()));
			pstmt.setString(14,  servicios.getEvaluacion());			
			pstmt.setString(15,  servicios.getGuardar_actualizar().toString());
			
			pstmt.setString(16,  servicios.getEstatus().toString().trim());
			pstmt.setBinaryStream(17, new FileInputStream(new File(adjunto)));
			pstmt.setString(18,  servicios.getAdjunto().toString());
			pstmt.setString(19,  servicios.getComentario_evaluacion().toString().trim());
			pstmt.setInt   (20,  Integer.valueOf(servicios.getFolio_equipo()));
			pstmt.setString(21,  ip);
			pstmt.setString(22,  pc);
			
			pstmt.executeUpdate();
		con.commit();
		
	} catch (Exception e) {
		System.out.println("SQLException: "+e.getMessage());
		JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_servicios ]\n"+query+"\nSQLException:"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
		if(con != null){
			try{
				System.out.println("La transacción ha sido abortada");
				con.rollback();
			}catch(SQLException ex){
				System.out.println(ex.getMessage());
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_servicios ]\n"+query+"\nSQLException:"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			}
		}
		return 0;
	}finally{
		try {
			con.close();
		} catch(SQLException e){
			e.printStackTrace();
		}
	}		
	return folio_transaccion;
}
		
		
public boolean Guardar_Administracion_De_Equipos(Obj_Administracion_De_Activos equipos, String movimiento,String rutaFactura, String rutaImagen){
	String query =  "exec sp_insert_administracion_de_activos ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?";//"exec sp_insert_actividades_de_plan_semanal_contestadas ?,?,?,?,?,?,?,?,?";
	Connection con = new Connexion().conexion();
	PreparedStatement pstmt = null;
	try {
		int folio = movimiento.equals("GUARDAR") ? busca_y_actualiza_proximo_folio(71) : equipos.getFolio();
		con.setAutoCommit(false);
		pstmt = con.prepareStatement(query);
				pstmt.setInt(1, folio); //actualiza folio en tb_folios y retorna el folio mas actualizado para posteriormente mandarlo de parametro                      
				pstmt.setString(2, equipos.getDescripcion());  
				pstmt.setString(3, equipos.getDepartamento_responsable());
				pstmt.setString(4, equipos.getStatus());
				pstmt.setString(5, equipos.getEstablecimiento());
				pstmt.setString(6, equipos.getDepartamento());
				pstmt.setString(7, equipos.getTipo());
				pstmt.setString(8, equipos.getMarca());
				pstmt.setString(9, equipos.getModelo());
				pstmt.setString(10, equipos.getSerie());
				pstmt.setInt(11, equipos.getAnio_fabricacion());
				pstmt.setString(12, equipos.getFecha_compra());
				pstmt.setInt(13, equipos.getGarantia());
				pstmt.setString(14, equipos.getUnidad_garantia());
				pstmt.setInt(15, equipos.getVida_util());
				pstmt.setString(16, equipos.getUnidad_vida_util());
				pstmt.setDouble(17, Double.valueOf(equipos.getCosto()));
				pstmt.setDouble(18, Double.valueOf(equipos.getDepreciacion()));
				pstmt.setString(19, equipos.getCaracteristicas());
				
				pstmt.setBinaryStream(20, new FileInputStream(equipos.getRuta_factura()));		
				pstmt.setString(21, equipos.getRuta_factura().substring(equipos.getRuta_factura().indexOf(".")).toLowerCase());
				
				pstmt.setBinaryStream(22, new FileInputStream(equipos.getRuta_foto()));
				pstmt.setString(23, equipos.getRuta_foto().substring(equipos.getRuta_foto().indexOf(".")).toLowerCase());
				
				pstmt.setString(24, movimiento);
				pstmt.setString(25, rutaFactura);
				pstmt.setString(26, rutaImagen);
				
				pstmt.setInt(27, equipos.getGrupo_equipo());
				
				pstmt.executeUpdate();
				con.commit();
				
	} catch (Exception e){
		System.out.println("SQLException: "+e.getMessage());
		JOptionPane.showMessageDialog(null, "Error en ActualizarSQL  en la  funcion Guardar_Actividades_Con_Respuesta  \n"+query+"\nSQLException:"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
		if(con != null){
			try{
				System.out.println("La transacción ha sido abortada");
				JOptionPane.showMessageDialog(null, "Error en ActualizarSQL  en la  funcion Guardar_Actividades_Con_Respuesta \n"+query+"\nSQLException:"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
				con.rollback();
			}catch(SQLException ex){
				System.out.println(ex.getMessage());
				JOptionPane.showMessageDialog(null, "Error en ActualizarSQL  en la  funcion Guardar_Actividades_Con_Respuesta  \n"+query+"\nSQLException:"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));

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

	public boolean Guardar_Asignacion(Obj_Servicios servicios){
		String query = "exec sp_guardar_colaborador_asignado_a_un_servicio ?,?";
		Connection con = new Connexion().conexion();
		try {
			con.setAutoCommit(false);
			PreparedStatement pstmt = con.prepareStatement(query);
				pstmt.setInt   (1 ,  servicios.getColaborador_asignado() );
				pstmt.setInt   (2 ,  servicios.getFolio());
				pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Asignacion ]\n"+query+"\nSQLException:"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Asignacion ]\n"+query+"\nSQLException:"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
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


	public boolean Guardar_Horario_De_Surtido_De_Pedido(Obj_Horario_Base_De_Entrega_De_Pedidos horario,String movimiento){
		String query = "exec sp_insert_horarios_de_entrega_para_pedidos ?,?,?,?,?,?,?,?,?,?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			
			int folio = movimiento.equals("GUARDAR") ? busca_y_actualiza_proximo_folio(72) : horario.getFolio();
			
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			
			pstmt.setInt(1, folio);
			pstmt.setString(2, horario.getEstablecimientoOrigen().toUpperCase().trim());
			pstmt.setString(3, horario.getEstablecimientoDestino().toUpperCase().trim());
			pstmt.setString(4, horario.getStatus().toUpperCase().trim());
			pstmt.setInt(5, horario.getLunes());
			pstmt.setInt(6, horario.getMartes());
			pstmt.setInt(7, horario.getMiercoles());
			pstmt.setInt(8, horario.getJueves());
			pstmt.setInt(9, horario.getViernes());
			pstmt.setInt(10, horario.getSabado());
			pstmt.setInt(11, horario.getDomingo());
			pstmt.setString(12, horario.getHora());
			pstmt.setString(13, movimiento);
			
			pstmt.executeUpdate();
			con.commit();
		}catch (Exception e){
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en ActualizarSQL  en la  funcion Guardar_Horario_De_Surtido_De_Pedido["+movimiento+"]  \n"+query+"\nSQLException:"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					JOptionPane.showMessageDialog(null, "Error en ActualizarSQL  en la  funcion Guardar_Horario_De_Surtido_De_Pedido["+movimiento+"] \n"+query+"\nSQLException:"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en ActualizarSQL  en la  funcion Guardar_Horario_De_Surtido_De_Pedido["+movimiento+"]  \n"+query+"\nSQLException:"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
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
	
	
	public boolean Guardar_Alimentacion_De_Productos_Proximos_A_Caducar(Obj_Alimentacion_De_Productos_Proximos_A_Caducar proximos_caducar){
		int folio_transaccion=0;
		if(!proximos_caducar.getGuardar_actualizar().equals("Remate")){
			 folio_transaccion=busca_y_actualiza_proximo_folio(42);
			proximos_caducar.setFolio(folio_transaccion);	
		}

		String query = "exec sp_guardar_actualizar_productos_proximos_a_caducar ?,?,?,?,?,?,?,?,?,?,?,?,?,?";
		Connection con = new Connexion().conexion();	
		try {
			con.setAutoCommit(false);
			PreparedStatement pstmt = con.prepareStatement(query);
			//@folio_proximos_caducar int, @establecimiento char(100) ,@cod_prod char(10) ,@cantidad int  ,@ultimo_costo money ,@costo_promedio money ,@precio_de_lista money ,@fecha_caducidad datetime ,@estatus char(1), @folio_usuario int, @notas varchar(500),@guarda_actualiza char(1)
			int cantidad_filas=proximos_caducar.getTabla_obj().length;
			//guarda solo notas y el registro en 0			
			 if(cantidad_filas==0){
				 pstmt.setInt   (1 ,  folio_transaccion );
 				 pstmt.setString(2 , proximos_caducar.getEstablecimiento());
				 pstmt.setString(3 , "0");
				 pstmt.setFloat (4 ,  0 );
				 pstmt.setFloat (5 ,  0 );
				 pstmt.setFloat (6 ,  0 );
				 pstmt.setFloat (7 ,  0 );
				 pstmt.setString(8 , "01/01/1900");
				 pstmt.setString(9 , proximos_caducar.getStatus());
				 pstmt.setInt   (10, usuario.getFolio());
				 pstmt.setString(11, proximos_caducar.getNotas());				
				 pstmt.setString(12, proximos_caducar.getGuardar_actualizar());
				 pstmt.setFloat (13 ,  0 );
				 pstmt.setString(14 , "");
				 pstmt.executeUpdate();
			 }else{
				//guardado remate
				 if(proximos_caducar.getStatus().equals("R")){
					 for(int i=0; i<cantidad_filas ; i++){
						 
							pstmt.setInt   (1 ,  proximos_caducar.getFolio());
							pstmt.setString(2 ,  proximos_caducar.getEstablecimiento());
							pstmt.setString(3 ,  proximos_caducar.getTabla_obj()[i][0].toString().trim());
							pstmt.setFloat (4 ,  Float.valueOf(proximos_caducar.getTabla_obj()[i][2].toString().trim()));
							pstmt.setFloat (5 ,  Float.valueOf(proximos_caducar.getTabla_obj()[i][4].toString().trim()));
							pstmt.setFloat (6 ,  Float.valueOf(proximos_caducar.getTabla_obj()[i][5].toString().trim()));
							pstmt.setFloat (7 ,  Float.valueOf(proximos_caducar.getTabla_obj()[i][6].toString().trim()));
							pstmt.setString(8 ,  proximos_caducar.getTabla_obj()[i][3].toString().trim());
							pstmt.setString(9 ,  proximos_caducar.getStatus() );
							pstmt.setInt   (10,  usuario.getFolio());
							pstmt.setString(11,  proximos_caducar.getNotas());	
							pstmt.setString(12,  proximos_caducar.getGuardar_actualizar());
							pstmt.setFloat (13,  Float.valueOf(proximos_caducar.getTabla_obj()[i][7].toString().trim()));
							pstmt.setString(14,  proximos_caducar.getTabla_obj()[i][8].toString().trim());
							pstmt.executeUpdate();
					    } 
				 }else{
				 //guardado normal
					for(int i=0; i<cantidad_filas ; i++){
						pstmt.setInt   (1 ,  folio_transaccion);
						pstmt.setString(2 ,  proximos_caducar.getEstablecimiento());
						pstmt.setString(3 ,  proximos_caducar.getTabla_obj()[i][0].toString().trim());
						pstmt.setFloat (4 ,  Float.valueOf(proximos_caducar.getTabla_obj()[i][3].toString().trim()));
						pstmt.setFloat (5 ,  Float.valueOf(proximos_caducar.getTabla_obj()[i][5].toString().trim()));
						pstmt.setFloat (6 ,  Float.valueOf(proximos_caducar.getTabla_obj()[i][6].toString().trim()));
						pstmt.setFloat (7 ,  Float.valueOf(proximos_caducar.getTabla_obj()[i][7].toString().trim()));
						pstmt.setString(8 ,  proximos_caducar.getTabla_obj()[i][4].toString().trim());
						pstmt.setString(9 ,  proximos_caducar.getStatus() );
						pstmt.setInt   (10,  usuario.getFolio());
						pstmt.setString(11,  proximos_caducar.getNotas());				
						pstmt.setString(12,  proximos_caducar.getGuardar_actualizar());
						pstmt.setFloat (13 ,  0 );
						pstmt.setString(14 , "");
						pstmt.executeUpdate();
				    }
				 }
			 }
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_inventarios_parciales ]\n"+query+"\nSQLException:"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
 		if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_inventarios_parciales ]\n"+query+"\nSQLException:"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
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

	public boolean Guardar_Mermas(Obj_Alimentacion_De_Mermas mermas,String movimiento, int usuario_seguridad){
		
		int folio = movimiento.equals("NORMAL")?busca_y_actualiza_proximo_folio(41):mermas.getFolio(); //alimentacion de merma
		String query = "exec sp_insert_movimiento_de_mermas ?,?,?,?,?,?,?,?,?,?,?,?";
		String query2 = "exec sp_insert_concentrado_de_movimiento_de_mermas ?,?,?,?,?,?";
		Connection con = new Connexion().conexion();
		
		int columna = movimiento.equals("TERMINADO")?2:0;
		try {
			con.setAutoCommit(false);
			PreparedStatement pstmt = con.prepareStatement(query);
			PreparedStatement pstmt2 = con.prepareStatement(query2);
			
			int i = 0;
			for( i=0; i<mermas.getArreglo().length; i++){
				
				pstmt.setInt   (1, folio);																	//folio
				pstmt.setString(2, mermas.getArreglo()[i][0].toString().trim());							//cod_prod
				
				pstmt.setDouble(3, Double.valueOf(mermas.getArreglo()[i][2].toString().trim()));			//existencia
				
				pstmt.setDouble(4, Double.valueOf(mermas.getArreglo()[i][3+columna].toString().trim()));			//merma_capturista
				
				pstmt.setString(5, mermas.getArreglo()[i][5+columna].toString().trim());							//razon de merma
				pstmt.setString(6, mermas.getArreglo()[i][6+columna].toString().trim());							//destino de merma
				pstmt.setDouble(7, Double.valueOf(mermas.getArreglo()[i][7+columna].toString().trim()));			//ultimo costo   
				pstmt.setDouble(8, Double.valueOf(mermas.getArreglo()[i][8+columna].toString().trim()));			//costo promedio 
				pstmt.setDouble(9, Double.valueOf(mermas.getArreglo()[i][9+columna].toString().trim()));    		//precio lista   
				pstmt.setString(10, mermas.getEstablecimiento().toString().trim());							//establecimiento bms
				pstmt.setString(11, (movimiento.equals("TERMINADO")?"T":(movimiento.equals("NORMAL")?"V":"A")));//status
				pstmt.setInt(12, usuario_seguridad==0?usuario.getFolio():usuario_seguridad);														//usuario SCOI
				pstmt.executeUpdate();
				
			}
			
			if(i==mermas.getArreglo().length){
				
				//insertar registro en tb_mermas			
				pstmt2.setInt   (1, folio);
				pstmt2.setString(2, mermas.getNota().toString().toUpperCase().trim());
				pstmt2.setString(3, (movimiento.equals("TERMINADO")?"T":(movimiento.equals("NORMAL")?"V":"A")));
				
				pstmt2.setString(4, mermas.getEstablecimiento().toString().trim());
				pstmt2.setInt(5, usuario_seguridad==0?usuario.getFolio():usuario_seguridad);
				
				String rutaFoto="";
				if(/*movimiento.equals("TERMINADO") || */(movimiento.equals("SEGURIDAD") && mermas.getArreglo().length>0) ){
					rutaFoto=mermas.getRutaFoto();
				}else{
					rutaFoto=System.getProperty("user.dir")+"/Imagen/merma_default.jpg";
				}
//				File imag = new File(movimiento.equals("NORMAL")? System.getProperty("user.dir")+"/Imagen/merma_default.jpg" : mermas.getRutaFoto() );
				File imag = new File(rutaFoto);
				FileInputStream stream_foto = new FileInputStream(imag);
				pstmt2.setBinaryStream(6, stream_foto, imag.length());
				
				pstmt2.executeUpdate();
			}
			con.commit();

		} catch (SQLException | FileNotFoundException e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Mermas ]\n"+query+"\nSQLException:"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Mermas ]\n"+query+"\nSQLException:"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
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

	public boolean Guardar_Recibir_Pedido(Obj_Registrar_Zona_Completada recibir_zona){
//		String query =  "delete from mpedestab where LTRIM(RTRIM(folio)) = LTRIM(RTRIM(@folio_pedido)) AND LTRIM(RTRIM(transaccion))='29'";	
		String query = "exec sp_recibir_pedido_por_zona ?,?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			
			pstmt.setString(1, recibir_zona.getFolio_pedido());
			pstmt.setString(2, recibir_zona.getZona());
			pstmt.setString(3, recibir_zona.getStatus());
			pstmt.setInt(4, recibir_zona.getFolio_surtidor());
			pstmt.setInt(5, recibir_zona.getFolio_emp_recibe());
			
			pstmt.executeUpdate();
			con.commit();
			
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Recibir_Pedido ] "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			if (con != null){
				try {
					System.out.println("La transacción ha sido abortada");
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Recibir_Pedido ] "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
					con.rollback();
				} catch(SQLException ex) {
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Recibir_Pedido ] "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
			} 
			return false;
		}finally{
			try {
				pstmt.close();
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Recibir_Pedido ] "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			}
		}		
		
		return true;
	}

	public boolean Guardar_PC_Por_Establecimiento(Obj_Pc_Por_Establecimientos pc_x_estab){
		int folio_transaccion=0;
		
		if(pc_x_estab.getGetNuevoModifica().equals("N")){
			 folio_transaccion=busca_y_actualiza_proximo_folio(75);
			 pc_x_estab.setFolio(folio_transaccion);	
		}
		String query = "exec  pc_por_establecimiento_insert_y_actualiza ?,?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
				pstmt.setInt   (1,  pc_x_estab.getFolio());
				pstmt.setString(2,  pc_x_estab.getNombre_pc());
				pstmt.setString(3,  pc_x_estab.getEstablecimiento());
				pstmt.setString(4,  pc_x_estab.getEstatus());
				pstmt.setString(5,  pc_x_estab.getGetNuevoModifica());
				pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_PC_Por_Establecimiento ] "+query+" "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			
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
	
	public boolean Guardar_Cuadrante (Obj_Cuadrantes cuadrante){
		int folio_transaccion=0;
		int cantidad_filas=cuadrante.getTabla_actividades().length;	
		if(cuadrante.getNuevoModifica().equals("N")){
			 folio_transaccion=busca_y_actualiza_proximo_folio(74);
			 cuadrante.setFolio(folio_transaccion);	
		}
		String query = "exec  cuadrantes_insert_y_actualiza ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			 con.setAutoCommit(false);
			 pstmt = con.prepareStatement(query);
			for(int i=0; i<cantidad_filas ; i++){
				pstmt.setInt   (1,  cuadrante.getFolio());
				pstmt.setString(2,  cuadrante.getCuadrante());
				pstmt.setString(3,  cuadrante.getEstablecimiento());
				pstmt.setString(4,  cuadrante.getDepartamento());
				pstmt.setString(5,  cuadrante.getPuesto());
				pstmt.setString(6,  cuadrante.getPuesto_reporta());
				pstmt.setString(7,  cuadrante.getResponsabilidades());
				pstmt.setString(8,  cuadrante.getObjetivo());
				pstmt.setString(9,  cuadrante.getEstatus());
				pstmt.setInt   (10,  usuario.getFolio());
				pstmt.setString(11,  cuadrante.getNuevoModifica());
//				@orden smallint  ,@folio_actividad int, @hora_inicio time(2) ,@hora_final time(2)  ,@dia_semana tinyint
				pstmt.setString(12 ,  cuadrante.getTabla_actividades()[i][0].toString().trim());
				pstmt.setString(13 ,  cuadrante.getTabla_actividades()[i][1].toString().trim());
				pstmt.setString(14 ,  cuadrante.getTabla_actividades()[i][3].toString().trim());
				pstmt.setString(15 ,  cuadrante.getTabla_actividades()[i][4].toString().trim());
				pstmt.setString(16 ,  cuadrante.getTabla_actividades()[i][5].toString().trim());
				pstmt.setString(17 ,  cuadrante.getTurno());
				pstmt.setString(18 ,  i+"");
				
				pstmt.executeUpdate();
			} 
			con.commit();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Cuadrante ] "+query+" "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			
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
	
	
	public boolean Guardar_Captura_Cuadrante (Obj_Cuadrantes cuadrante){
		int cantidad_filas=cuadrante.getTabla_actividades().length;	
		int dia=0;
		String query1 = "exec cuadrantes_captura_borrado_para_actualizacion ?,?" ;
		String query = "EXEC cuadrantes_captura_insert_y_actualiza ?,?,?,?,?,?,?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		PreparedStatement pstmt1 = null;
		
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt1 = con.prepareStatement(query1);
		
			for(int i=0; i<cantidad_filas ; i++){
				pstmt.setInt   (1 ,  cuadrante.getFolio_colaborador());
				pstmt.setInt   (2 ,  cuadrante.getFolio_cuadrante());
				pstmt.setInt   (3 ,  usuario.getFolio());
				pstmt.setString(4 ,  cuadrante.getNuevoModifica());				
				pstmt.setString(5 ,  cuadrante.getTabla_actividades()[i][1].toString().trim());
				pstmt.setString(6 ,  cuadrante.getTabla_actividades()[i][2].toString().trim());
				pstmt.setString(7 ,  cuadrante.getTabla_actividades()[i][5].toString().trim());
				pstmt.setString(8 ,  cuadrante.getTabla_actividades()[i][6].toString().trim());
				pstmt.setString(9 ,  cuadrante.getTabla_actividades()[i][8].toString().trim());
				pstmt.setString(10,  cuadrante.getTabla_actividades()[i][9].toString().trim());
				
				if(Integer.valueOf(cuadrante.getTabla_actividades()[i][9].toString().trim())!=dia){
					dia= Integer.valueOf(cuadrante.getTabla_actividades()[i][9].toString().trim());
					pstmt1.setInt   (1 ,  cuadrante.getFolio_colaborador());
					pstmt1.setInt   (2 ,dia);
					pstmt1.executeUpdate();
				}
				pstmt.executeUpdate();
			} 
			con.commit();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Cuadrante ] "+query+" "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			
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
	
	
	public boolean Guardar_Registro_De_Entradas_y_Salida (Obj_Registro_Proveedores proveedores){
		int folio_transaccion=0;
		int cantidad_filasf= proveedores.getTabla_facturas().length;
		int cantidad_filasm= proveedores.getTabla_registros().length;
		int cantidad_filasdev= proveedores.getTabla_devolucion().length;
		if(proveedores.getNuevoModifica().equals("N")){
			 folio_transaccion=busca_y_actualiza_proximo_folio(76);
			 proveedores.setFolio(folio_transaccion);	
		}
		String query    = "exec proveedores_registro_entradaysalida_insert_y_actualiza ?,?,?,?,?,?,?,?,?,?,?,?,?";
		String queryr   = "exec proveedores_registro_entradaysalida_insert_y_actualiza_movimientos ?,?,?,?,?";
		String querydpr = "exec proveedores_registro_productos_devoluciones_insert_y_actualiza ?,?,?,?,?,?";
		
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt    = null;
		PreparedStatement pstmtr   = null;
		PreparedStatement pstmtdpr = null;
		try {
			 con.setAutoCommit(false);
			 pstmt = con.prepareStatement(query);
			 pstmtr = con.prepareStatement(queryr);
			 pstmtdpr = con.prepareStatement(querydpr);
			 
			 for(int i=0; i<cantidad_filasf ; i++){
				pstmt.setInt   (1 ,  proveedores.getFolio());
				pstmt.setInt   (2 ,  proveedores.getFolio_colaborador_recibe());
				pstmt.setString(3 ,  proveedores.getEstablecimiento());
				pstmt.setString(4 ,  proveedores.getProveedor());
				pstmt.setString(5 ,  proveedores.getChofer());
				pstmt.setString(6 ,  proveedores.getObservaciones());
				pstmt.setString(7 ,  proveedores.getNuevoModifica());
				pstmt.setString(8 ,  proveedores.getTabla_facturas()[i][0].toString().trim());
				pstmt.setString(9 ,  proveedores.getTabla_facturas()[i][1].toString().trim());
				pstmt.setString(10,  proveedores.getTabla_facturas()[i][2].toString().trim());
				pstmt.setString(11,  proveedores.getTabla_facturas()[i][3].toString().trim());
				pstmt.setString(12,  proveedores.getTabla_facturas()[i][5].toString().trim());
				pstmt.setInt   (13,  proveedores.getFolio_solicitud());
				pstmt.executeUpdate();
			} 
			 for(int i=0; i<cantidad_filasm ; i++){
				 pstmtr.setInt   (1,  proveedores.getFolio());
				 pstmtr.setString(2,  proveedores.getTabla_registros()[i][0].toString().trim());
				 pstmtr.setString(3,  proveedores.getTabla_registros()[i][1].toString().trim());
				 pstmtr.setString(4,  proveedores.getTabla_registros()[i][2].toString().trim());
				 pstmtr.setString(5,  proveedores.getNuevoModifica());
				 pstmtr.executeUpdate();
			 }
			 
			 for(int i=0; i<cantidad_filasdev ; i++){
				 pstmtdpr.setInt   (1,  proveedores.getFolio());
				 pstmtdpr.setString(2,  proveedores.getTabla_devolucion()[i][0].toString().trim());
				 pstmtdpr.setString(3,  proveedores.getTabla_devolucion()[i][2].toString().trim());
				 pstmtdpr.setString(4,  proveedores.getTabla_devolucion()[i][3].toString().trim());
				 pstmtdpr.setString(5,  proveedores.getTabla_devolucion()[i][4].toString().trim());
				 pstmtdpr.setString(6,  proveedores.getNuevoModifica());
				 pstmtdpr.executeUpdate();
			 }
			 
			con.commit();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Registro_De_Entradas_y_Salida ] "+query+" "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			
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
	
	public boolean Guardar_minimo_maximo_pedido_por_estab(String xml,String establecimiento_solicita,String establecimiento_surte,int folio_scoi,String Observacion, String boton){
		
		String query = "exec insert_pedido_maximos_y_minimos_xml '"+xml+"','"+establecimiento_solicita+"','"+establecimiento_surte+"',"+folio_scoi+",'"+Observacion+"',"+usuario.getFolio()+",'"+boton+"'";
		
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.execute();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+ e.getMessage());
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
				pstmt.close();
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
		}		
		return true;
	}
	

	public boolean Guardar_Marca_De_Activo(Obj_Marcas_De_Activos marca){
		String query   = "exec servicios_marcas_de_equipo_guarda_actualiza ?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);

			pstmt = con.prepareStatement(query);
			pstmt.setInt   (1, marca.getFolio()      );
			pstmt.setString(2, marca.getMarca_De_Activo()  );
			pstmt.setString(3, marca.getEstatus()    );
			pstmt.setString(4, marca.getGuardaModifica());
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Marca_De_Activo ] Insert  SQLException: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Marca_De_Activo ] Insert  SQLException:  "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Marca_De_Activo ] Insert  SQLException: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			}
		}		
		return true;
	}
	
	public boolean Guardar_Modelo_De_Activo(Obj_Modelos_De_Activos modelo){
		String query   = "exec servicios_modelos_de_equipo_guarda_actualiza ?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);

			pstmt = con.prepareStatement(query);
			pstmt.setInt   (1, modelo.getFolio()      );
			pstmt.setString(2, modelo.getModelo_De_Activo() );
			pstmt.setString(3, modelo.getEstatus()    );
			pstmt.setString(4, modelo.getGuardaModifica());
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Modelo_De_Activo ] Insert  SQLException: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Modelo_De_Activo ] Insert  SQLException:  "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Modelo_De_Activo ] Insert  SQLException: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			}
		}		
		return true;
	}
	
	
	public boolean Guardar_Tipos_De_Activo(Obj_Tipos_De_Equipos modelo){
		String query   = "exec servicios_tipos_de_equipo_guarda_actualiza ?,?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);

			pstmt = con.prepareStatement(query);
			pstmt.setInt   (1, modelo.getFolio()          );
			pstmt.setString(2, modelo.getTipos_De_Equipo());
			pstmt.setString(3, modelo.getSerie()          );
			pstmt.setString(4, modelo.getEstatus()        );
			pstmt.setString(5, modelo.getGuardaModifica() );
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Tipos_De_Activo ] Insert  SQLException: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Tipos_De_Activo ] Insert  SQLException:  "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Modelo_De_Activo ] Insert  SQLException: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			}
		}		
		return true;
	}

	public boolean Guardar_Solicitud_De_Acceso_A_Proveedores (Obj_Autorizacion_Acceso_Proveedores proveedores){
		int folio_transaccion=0;
		if(proveedores.getNuevoModifica().equals("N")){
			 folio_transaccion=busca_y_actualiza_proximo_folio(78);
			 proveedores.setFolio(folio_transaccion);	
		}
		String query = "exec  proveedores_autorizacion_insert_y_actualiza ?,?,?,?,?,?,?,?,?,? ";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			 con.setAutoCommit(false);
			 pstmt = con.prepareStatement(query);
				pstmt.setInt   (1 ,  proveedores.getFolio());
				pstmt.setInt   (2 ,  proveedores.getUsuario_solicita());
				pstmt.setString(3 ,  proveedores.getEstablecimiento());
				pstmt.setString(4 ,  proveedores.getProveedor());
				pstmt.setString(5 ,  proveedores.getChofer());
				pstmt.setString(6 ,  proveedores.getEstatus());
				pstmt.setInt   (7 ,  proveedores.getUsuario_autoriza());
				pstmt.setString(8 ,  proveedores.getMotivo_negado_acceso());				
				pstmt.setString(9 ,  proveedores.getObservaciones());
				pstmt.setString(10,  proveedores.getNuevoModifica());
				pstmt.executeUpdate();


				
			 con.commit();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Solicitud_De_Acceso_A_Proveedores ] "+query+" "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			
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
	
	public boolean Guardar_Programacion (Obj_Programacion_De_Proveedores proveedor){
		int folio_transaccion=0;
		int cantidad_filas=proveedor.getTabla_programacion().length;	
		if(proveedor.getNuevoModifica().equals("N")){
			 folio_transaccion=busca_y_actualiza_proximo_folio(80);
			 proveedor.setFolio(folio_transaccion);	
		}
		String query = "exec  proveedores_programacion_visita_insert_y_actualiza ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			 con.setAutoCommit(false);
			 pstmt = con.prepareStatement(query);
			for(int i=0; i<cantidad_filas ; i++){
				pstmt.setInt   ( 1,  proveedor.getFolio());
				pstmt.setString( 2,  proveedor.getEstablecimiento());
				pstmt.setInt   ( 3,  proveedor.getAnio());
				pstmt.setInt   ( 4,  proveedor.getSemana_de_anio());
				pstmt.setString( 5,  proveedor.getTabla_programacion()[i][10].toString().trim());
				pstmt.setString( 6,  proveedor.getTabla_programacion()[i][0].toString().trim());
				pstmt.setString( 7,  proveedor.getTabla_programacion()[i][1].toString().trim());
				pstmt.setString( 8,  proveedor.getTabla_programacion()[i][2].toString().trim());
				pstmt.setString( 9,  proveedor.getTabla_programacion()[i][4].toString().trim());
				pstmt.setString(10,  proveedor.getTabla_programacion()[i][5].toString().trim());
				pstmt.setString(11,  proveedor.getTabla_programacion()[i][6].toString().trim());
				pstmt.setString(12,  proveedor.getTabla_programacion()[i][7].toString().trim());				
				pstmt.setInt   (13,  usuario.getFolio());
				pstmt.setInt   (14,  usuario.getFolio());
				pstmt.setString(15,  proveedor.getNuevoModifica());
				pstmt.executeUpdate();
			} 
			con.commit();
		} catch (Exception e) {
			
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Cuadrante ] "+query+" "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
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
	
	   public String Finalizar_minimo_maximo_pedido_por_estab(String establecimiento_solicita,String establecimiento_surte,int folio_scoi,String area) throws SQLException{
		   String folio_pedido="";
		   int folio_usuario = new Obj_Usuario().LeerSession().getFolio();
			 String query = "exec pedido_a_establecimiento_2 '"+establecimiento_solicita+"','"+establecimiento_surte+"',"+folio_scoi+",'"+area+"',"+folio_usuario;
			 Statement stmt = null;
			try {
				Connexion con = new Connexion();
				stmt = con.conexion().createStatement();
				ResultSet rs = stmt.executeQuery(query);
				while(rs.next()){
					folio_pedido=(rs.getString("folio_pedido"));
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(stmt!=null){stmt.close();}
		}
		return folio_pedido;
	}
	   
	public String finalizar_merma(String Establecimiento,String folio_merma_scoi){
		
		Obj_Conn conect = new Obj_Conn();
		conect.llenarConn(Establecimiento);
	
	   String folio_merma="";
	   String query = "exec sp_finalizar_merma '"+Establecimiento.trim()+"','"+folio_merma_scoi+"'"; 
	   Statement stmt = null;
		try {
			//TODO (Connexion Parametrizada)--------------------------------------------------------------------------------------------------------------------
			Connexion con = new Connexion();
			stmt = con.conexion_parametrizada(conect.getDir(), conect.getDb(), conect.getUser(), conect.getPass()).createStatement();
			//--------------------------------------------------------------------------------------------------------------------------------------------------
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				folio_merma=(rs.getString("folio_de_merma"));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error en Buscar  en la funcion finalizar_merma \n"+query+"\n SQLException: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		finally{
			if(stmt!=null){try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}}
		}
		return folio_merma;
	}
	
	public int Entrada_De_Insumos(String xml,String nota,String estabRecibe, int folioEmpleadoRecibe, String razon,String estabSurte,String movimiento){
		
		int folio=0;
		
		String query = "exec aumento_de_insumos '"+xml+"','"+nota+"',"+usuario.getFolio()+",'"+razon+"','"+estabRecibe+"'";
		String query2 = "exec disminucion_de_insumos '"+xml+"','"+nota+"',"+usuario.getFolio()+",'"+estabRecibe+"',"+folioEmpleadoRecibe+",'"+razon+"','"+estabSurte+"'";
		
		String queryF = "";
		
		switch(movimiento){
			case "aumento":		queryF = query	;break;
			case "disminucion":	queryF = query2	;break;
			default:			queryF = "x"	; break;
		}
		System.out.print(queryF);
		
	   Statement stmt = null;
		try {
			//TODO (Connexion Parametrizada)--------------------------------------------------------------------------------------------------------------------
			Connexion con = new Connexion();
			stmt = con.conexion().createStatement();
			//--------------------------------------------------------------------------------------------------------------------------------------------------
			ResultSet rs = stmt.executeQuery(queryF);
			while(rs.next()){
				folio=(rs.getInt("folio"));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error en Buscar  en la funcion Entrada_De_Insumos \n"+queryF+"\n SQLException: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
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
	
	public boolean Guardar_Pregunta(Obj_Preguntas pregunta,String movimeinto){
		String query = "exec sp_guardar_pregunta ?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, pregunta.getFolio());
			pstmt.setString(2, pregunta.getPregunta().toUpperCase().trim());
			pstmt.setString(3, pregunta.getStatus());
			pstmt.setString(4, movimeinto);
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Pregunta ] Insert  SQLException "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);

			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Pregunta ] Insert  SQLException "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);

				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Pregunta ] Insert  SQLException "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);

			}
		}		
		return true;
	}

	
	public Obj_Orden_De_Gasto Guardar_Solicitud_Orden_De_Gasto(Obj_Orden_De_Gasto orden){
		int folio_transaccion=orden.getFolio();
		String querymod ="";
				
		if(orden.getGuardar_actualizar().equals("N")){
		  folio_transaccion=busca_y_actualiza_proximo_folio(84);
		  orden.setFolio(folio_transaccion);
		}else {
		  querymod = "delete from orden_de_gasto_datos where folio_orden_de_gasto="+folio_transaccion;
		}
		
		String query = "exec orden_de_gasto_insert_y_actualiza ?,?,?,?,?,?,?,?,?,?,?,?";
		Connection con = new Connexion().conexion();
		
		try {
			con.setAutoCommit(false);

			PreparedStatement pstmtdel = con.prepareStatement(querymod);
			pstmtdel.executeUpdate();
			 con.commit();
			 
			PreparedStatement pstmt = con.prepareStatement(query);
			for(int i=0; i<orden.getTabla_obj().length; i++){
				pstmt.setInt   (1 ,  folio_transaccion);
				pstmt.setString(2 ,  orden.getDescripcion_gasto().toString());
				pstmt.setString(3 ,  orden.getEstablecimiento_solicito().toString());
				pstmt.setInt   (4 ,  orden.getFolio_usuario_solicito());
				pstmt.setString(5 ,  orden.getCod_prv().toString());
				pstmt.setString(6 ,  orden.getTipo_proveedor().toString());
				pstmt.setFloat (7 ,  orden.getTotal_gasto());
				pstmt.setString(8 ,  orden.getGuardar_actualizar());
				pstmt.setString(9 ,  orden.getTabla_obj()[i][0].toString().trim());
				pstmt.setFloat (10 , Float.valueOf(orden.getTabla_obj()[i][1].toString().trim()));
				pstmt.setFloat (11 , Float.valueOf(orden.getTabla_obj()[i][2].toString().trim()));
				pstmt.setString(12 , orden.getConcepto_gasto());				
								
				pstmt.executeUpdate();
			 con.commit();
			}
			
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage()+"\n"+query+"\nSQLException:"+e.getMessage()+"\n"+querymod);
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Solicitud_Orden_De_Gasto ]\n"+query+"\nSQLException:"+e.getMessage()+"\n"+querymod, "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage()+"\n"+query+"\nSQLException:"+e.getMessage()+"\n"+querymod);
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Solicitud_Orden_De_Gasto ]\n"+query+"\nSQLException:"+e.getMessage()+"\n"+querymod, "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
				}
			}
			return null;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
		}		
		return orden;
	}
	
//	@folio int,@folio_orden_de_gasto int,@cantidad money, @fecha datetime, @observaciones varchar(150), @tipo_beneficiario char(1), @folio_beneficiario int, @usuario int, @concepto varchar(200)
	
	public boolean Guardar_Orden_De_Gasto_Pago_En_Efectivo(int folio_orden_de_gasto,float cantidad, String fecha, String observaciones, String tipoBeneficiario, int folioBeneficiario, String Concepto,String Guardar_actualizar){
		int folio_transaccion=0;

		if(Guardar_actualizar.equals("N")){
		  folio_transaccion=busca_y_actualiza_proximo_folio(85);
		}
		
		String query = "exec orden_de_gasto_pago_en_efectivo_insert_y_actualiza ?,?,?,?,?,?,?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
				con.setAutoCommit(false);
				pstmt = con.prepareStatement(query);
				pstmt.setInt   ( 1, folio_transaccion);
				pstmt.setInt   ( 2, folio_orden_de_gasto);
				pstmt.setFloat ( 3, cantidad);
				pstmt.setString( 4, fecha.toUpperCase().trim());
				pstmt.setString( 5, observaciones.toUpperCase().trim());
				pstmt.setString( 6, tipoBeneficiario.toUpperCase().trim());
				pstmt.setInt   ( 7, folioBeneficiario);
				pstmt.setInt   ( 8, usuario.getFolio());		
				pstmt.setString( 9, Concepto.toUpperCase().trim());
				pstmt.setString(10, Guardar_actualizar);

				pstmt.executeUpdate();
			
			con.commit();
		} catch (SQLException e) {
			System.out.println("SQLException: "+e.getMessage()+"\n"+query);
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Orden_De_Gasto_Pago_En_Efectivo ] Insert   \nSQLException: "+e.getMessage()+"\n"+query, "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Orden_De_Gasto_Pago_En_Efectivo ] Insert  \nSQLException: "+e.getMessage()+"\n"+query, "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Orden_De_Gasto_Pago_En_Efectivo ] Insert  SQLException: :"+e.getMessage()+"\n"+query, "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			}
		}		
		return true;
	}
	
	public boolean Guardar_Cancelar_Corte_Del_Pago_De_Ordenes_De_Gasto(Object[][] tabla,String tipo_movimiento){
		int folio_transaccion=0;
		if(tipo_movimiento.equals("T")){
		  folio_transaccion=busca_y_actualiza_proximo_folio(86);
		}
		
		String query =  "exec orden_de_gasto_corte_de_pagos_en_efectivo ?,"+folio_transaccion+","+usuario.getFolio()+",'"+tipo_movimiento+"'";
		Connection con = new Connexion().conexion();
		try {
			PreparedStatement pstmt = con.prepareStatement(query);
			con.setAutoCommit(false);
			for(int i=0; i<tabla.length; i++){
				if(tabla[i][9].toString().trim().equals("true")) {
				pstmt.setString(1, tabla[i][0].toString().trim());
				pstmt.executeUpdate();
				}
			}
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en ActualizarSQL  en la  funcion Guardar_Corte_Del_Pago_De_Ordenes_De_Gasto \n SQLException:\n"+e.getMessage()+"\n"+query, "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					JOptionPane.showMessageDialog(null, "Error en ActualizarSQL  en la  funcion Guardar_Corte_Del_Pago_De_Ordenes_De_Gasto \n SQLException:\n"+e.getMessage()+"\n"+query, "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en ActualizarSQL  en la  funcion Guardar_Corte_Del_Pago_De_Ordenes_De_Gasto \n SQLException:\n"+e.getMessage()+"\n"+query, "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
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
	
	public boolean Guardar_Cuestionario(Obj_Cuestionarios cuestionarios,String movimiento){
		String query = "exec sp_guardar_cuestionario ?,?,?,?,?,?,'"+cuestionarios.getCadena_xml()+"'";
		System.out.println(query);
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			
			System.out.println(cuestionarios.getFolio());
			System.out.println(cuestionarios.getCuestionario().toUpperCase().trim());
			System.out.println(cuestionarios.getClasificacion());
			System.out.println(cuestionarios.getEscala());
			System.out.println(cuestionarios.getStatus());
			System.out.println(movimiento);
			
			
			pstmt.setInt(1, cuestionarios.getFolio());
			pstmt.setString(2, cuestionarios.getCuestionario().toUpperCase().trim());
			pstmt.setString(3, cuestionarios.getClasificacion());
			pstmt.setString(4, cuestionarios.getEscala());
			pstmt.setString(5, cuestionarios.getStatus());
			pstmt.setString(6, movimiento);
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Cuestionario ] Insert  SQLException "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);

			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Cuestionario ] Insert  SQLException "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);

				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Cuestionario ] Insert  SQLException "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}		
		return true;
	}

	public Obj_Transpaso_A_Banco_Interno Guardar_Traspaso_A_Banco_Interno(Obj_Transpaso_A_Banco_Interno Banco_Interno){
		int folio_transaccion=Banco_Interno.getFolio();
		String foliomovinterno="";
		String querymod ="";
		if(Banco_Interno.getGuardar_actualizar().equals("N")){
		   folio_transaccion=busca_y_actualiza_proximo_folio(87);
		  Banco_Interno.setFolio(folio_transaccion);
		}else {
		  querymod = "delete from banco_interno_datos where folio_movimiento_banco_interno="+folio_transaccion;
		}
		
		if(Banco_Interno.getGuardar_actualizar().equals("N")&&Banco_Interno.getTransaccion().equals("46") ){
			 foliomovinterno="Traspaso Manual Folio:"+busca_y_actualiza_proximo_folio(46);
			}
				
		String query = "exec banco_interno_insert_y_actualiza ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?";
		Connection con = new Connexion().conexion();
		
		try {
			con.setAutoCommit(false);
			PreparedStatement pstmtdel = con.prepareStatement(querymod);
			pstmtdel.executeUpdate();
			 con.commit();
			   
			PreparedStatement pstmt = con.prepareStatement(query);
			for(int i=0; i<Banco_Interno.getDatos().length; i++){		
				
//		 		  System.out.println(folio_transaccion);
//		 		  System.out.println(Banco_Interno.getFolio());
//		 		  System.out.println(Banco_Interno.getFolio_empleado_destinatario());
//		 		  System.out.println(Banco_Interno.getObservaciones().toString()   );
//		 		  System.out.println(Banco_Interno.getUsuario_realiza_transpaso()  );
//		 		  System.out.println(Banco_Interno.getEstatus());
//		 		  System.out.println( Banco_Interno.getGuardar_actualizar());
//		 		  System.out.println("1-"+Banco_Interno.getDatos()[i][0].toString().trim()+foliomovinterno);
//		 		  System.out.println("2-"+ Banco_Interno.getDatos()[i][1].toString().trim());
//		 		  System.out.println("3-"+ Banco_Interno.getDatos()[i][2].toString().trim());
//		 		  System.out.println("4-"+ Banco_Interno.getDatos()[i][3].toString().trim());
//		 		  System.out.println("5-"+ Banco_Interno.getDatos()[i][4].toString().trim());
//		 		  System.out.println("6-"+ Banco_Interno.getDatos()[i][5].toString().trim());
//		 		  System.out.println("7-"+ Banco_Interno.getDatos()[i][6].toString().trim());
//		 		  System.out.println( Banco_Interno.getCuenta().toString().trim());
//		 		  System.out.println( Banco_Interno.getTransaccion().toString().trim());
		 		  
				pstmt.setInt   (1 ,  folio_transaccion);
				pstmt.setInt   (2 ,  Banco_Interno.getFolio_empleado_destinatario());
				pstmt.setString(3 ,  foliomovinterno+" "+Banco_Interno.getObservaciones().toString());
				pstmt.setInt   (4 ,  Banco_Interno.getUsuario_realiza_transpaso());
				pstmt.setString(5 ,  Banco_Interno.getEstatus());
				pstmt.setString(6 ,  Banco_Interno.getGuardar_actualizar());
				pstmt.setString(7 ,  Banco_Interno.getDatos()[i][0].toString().trim()+foliomovinterno );
				pstmt.setString(8 ,  Banco_Interno.getDatos()[i][1].toString().trim() );
				pstmt.setString(9 ,  Banco_Interno.getDatos()[i][2].toString().trim() );
				pstmt.setString(10 , Banco_Interno.getDatos()[i][3].toString().trim() );
				pstmt.setString(11 , Banco_Interno.getDatos()[i][4].toString().trim() );
				pstmt.setString(12 , Banco_Interno.getDatos()[i][5].toString().trim() );			
				pstmt.setString(13 , Banco_Interno.getDatos()[i][6].toString().trim() );
				pstmt.setString(14 , Banco_Interno.getCuenta().toString().trim() );
				pstmt.setString(15 , Banco_Interno.getTransaccion().toString().trim() );
		 		  
				pstmt.executeUpdate();
			 con.commit();
			}
			
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage()+"\n"+query+"\nSQLException:"+e.getLocalizedMessage()+"\n"+querymod);
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Traspaso_A_Banco_Interno ]\n"+query+"\nSQLException:"+e.getMessage()+"\n"+querymod, "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage()+"\n"+query+"\nSQLException:"+e.getMessage()+"\n"+querymod);
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Traspaso_A_Banco_Interno ]\n"+query+"\nSQLException:"+e.getMessage()+"\n"+querymod, "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
				}
			}
			return null;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
		}		
		return Banco_Interno;
	}

	public Obj_Saldo_Banco_Interno Guardar_Saldo_Banco_Interno(Obj_Saldo_Banco_Interno Banco_Interno){
		int folio_transaccion=Banco_Interno.getFolio();
		if(Banco_Interno.getGuardar_actualizar().equals("N")){
		   folio_transaccion=busca_y_actualiza_proximo_folio(45);
		  Banco_Interno.setFolio(folio_transaccion);
		}
		
		String query = "exec banco_interno_saldo_insert_y_actualiza ?,?,?,?,?,?,?,?,?,?,?,?";
		Connection con = new Connexion().conexion();
//		 @folio int, @usuario_recibe int, @observaciones varchar(160), @numero_de_cuenta int,@estatus char(1),@GuardarActualizar char(1), @importe_ingreso money, @importe_egreso money, @tipo_movimiento char(1),@folio_Banco_Interno int ,@folio_trabajo int
		try {
			con.setAutoCommit(false);		   
			PreparedStatement pstmt = con.prepareStatement(query);
			for(int i=0; i<Banco_Interno.getTabla().length; i++){	
				if(Banco_Interno.getTabla()[i][8].toString().trim().equals("true")) {
					pstmt.setInt   (1 ,  folio_transaccion);
					pstmt.setInt   (2 ,  Banco_Interno.getFolio_usuario());
					pstmt.setString(3 ,  Banco_Interno.getObservaciones().toString()      );
					pstmt.setString(4 ,  Banco_Interno.getTabla()[i][1].toString().trim() );
					pstmt.setString(5 ,  Banco_Interno.getEstatus()                       );
					pstmt.setString(6 ,  Banco_Interno.getGuardar_actualizar()            );
					pstmt.setString(7 ,  Banco_Interno.getTabla()[i][4].toString().trim() );
					pstmt.setInt   (8 ,  0                                                );
					pstmt.setString(9 ,  "Ingreso"                                        );
					pstmt.setString(10 , Banco_Interno.getTabla()[i][2].toString().trim() );
					pstmt.setString(11 , Banco_Interno.getTabla()[i][9].toString().trim() );
					pstmt.setFloat (12 , Banco_Interno.getImporte());
					pstmt.executeUpdate();
				    con.commit();
				}
			}
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage()+"\n"+query+"\nSQLException:"+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Transpaso_A_Banco_Interno ]\n"+query+"\nSQLException:"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage()+"\n"+query+"\nSQLException:"+e.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Transpaso_A_Banco_Interno ]\n"+query+"\nSQLException:"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
				}
			}
			return null;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
		}		
		return Banco_Interno;
	}
	
	public Obj_Saldo_Banco_Interno Guardar_Saldo_Banco_Interno_Egreso(Obj_Saldo_Banco_Interno Banco_Interno){
		int folio_transaccion=Banco_Interno.getFolio();
						
		if(Banco_Interno.getGuardar_actualizar().equals("N")){
		   folio_transaccion=busca_y_actualiza_proximo_folio(45);
		  Banco_Interno.setFolio(folio_transaccion);
		}
		
		String query = "exec banco_interno_saldo_insert_y_actualiza ?,?,?,?,?,?,?,?,?,?,?,?";
		Connection con = new Connexion().conexion();
		try {
			con.setAutoCommit(false);		   
			PreparedStatement pstmt = con.prepareStatement(query);
			for(int i=0; i<Banco_Interno.getTabla().length; i++){	
				if(Banco_Interno.getTabla()[i][4].toString().trim().equals("true")) {
					
					System.out.println(folio_transaccion);
					System.out.println(Banco_Interno.getFolio_usuario());
					System.out.println(Banco_Interno.getObservaciones().toString() );
					System.out.println(Banco_Interno.getCuenta().toString().trim());
					System.out.println(Banco_Interno.getEstatus()     );
					System.out.println(Banco_Interno.getGuardar_actualizar()   );
					System.out.println(0);
					System.out.println(Banco_Interno.getTabla()[i][3].toString().trim());
					System.out.println(Banco_Interno.getTipo_movimiento().toString().trim());
					System.out.println(0);
					System.out.println(Banco_Interno.getTabla()[i][0].toString().trim());
					System.out.println(Banco_Interno.getImporte());
					
					pstmt.setInt   (1 ,  folio_transaccion);
					pstmt.setInt   (2 ,  Banco_Interno.getFolio_usuario()                    );
					pstmt.setString(3 ,  Banco_Interno.getObservaciones().toString()         );
					pstmt.setString(4 ,  Banco_Interno.getCuenta().toString().trim()         );
					pstmt.setString(5 ,  Banco_Interno.getEstatus()                          );
					pstmt.setString(6 ,  Banco_Interno.getGuardar_actualizar()               );
					pstmt.setInt   (7 ,  0                                                   );
					pstmt.setString(8 ,  Banco_Interno.getTabla()[i][3].toString().trim()    );
					pstmt.setString(9 ,  Banco_Interno.getTipo_movimiento().toString().trim());
					pstmt.setString(10 , "0"                                                 );
					pstmt.setString(11 , Banco_Interno.getTabla()[i][0].toString().trim()    );
					pstmt.setFloat (12 , Banco_Interno.getImporte()                          );
					pstmt.executeUpdate();
				    con.commit();
				}
				
			}
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage()+"\n"+query);
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Saldo_Banco_Interno_Egreso ]\n"+query+"\nSQLException:"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage()+"\n"+query+"\nSQLException:"+ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Saldo_Banco_Interno_Egreso ]\n"+query+"\nSQLException:"+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
				}
			}
			return null;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
		}		
		return Banco_Interno;
	}
	
	public boolean Guardar_Asignacion_De_Cuestionario(Obj_Asignacion_De_Cuestionarios programacion,String movimiento){
		
		System.out.println(programacion.getCadena_xml());
		
		System.out.println(programacion.getFolio());
		System.out.println(programacion.getNombre_asignacion().toUpperCase().trim());
		System.out.println(programacion.getFecha_in());
		System.out.println(programacion.getFecha_fin());
		System.out.println(programacion.getFolio_cuestionario());
		System.out.println(movimiento);
		
		String query = "exec sp_guardar_programacion_de_cuestionarios ?,?,?,?,?,?,?,'"+programacion.getCadena_xml()+"'";
		System.out.println(query);
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			
			pstmt.setInt(1, programacion.getFolio());
			pstmt.setString(2, programacion.getNombre_asignacion().toUpperCase().trim());
			pstmt.setString(3, programacion.getFecha_in());
			pstmt.setString(4, programacion.getFecha_fin());
			pstmt.setInt(5, programacion.getFolio_cuestionario());
			pstmt.setString(6, "VIGENTE");
			pstmt.setString(7, movimiento);
			pstmt.executeUpdate();
			con.commit();
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Asignacion_De_Cuestionario ] Insert  SQLException "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);

			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Asignacion_De_Cuestionario ] Insert  SQLException "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);

				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Asignacion_De_Cuestionario ] Insert  SQLException "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}		
		return true;
	}
	
	public boolean Guardar_Contestacion_De_Cuestionario(Obj_Contestacion_De_Cuestionario programacion){
		
		String query = "exec sp_insert_respuesta_de_colaboradores_en_cuestionario ?,?,?,?,?,?,?,?,?,'"+programacion.getCuestionario_xml()+"'";
		System.out.println(query);
		
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
		try {
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(query);
			
			pstmt.setInt(1, programacion.getFolio_programacion());
			pstmt.setString(2, programacion.getProgramacion().toUpperCase().trim());
			pstmt.setInt(3, programacion.getFolio_cuestionario());
			pstmt.setString(4, programacion.getCuestionario().toUpperCase().trim());
			pstmt.setInt(5, programacion.getFolio_colaborador());
			
			pstmt.setString(6, programacion.getEstablecimiento());
			pstmt.setString(7, programacion.getDepartamento());
			pstmt.setString(8, programacion.getPuesto());
			pstmt.setInt(9, programacion.getValor_de_escala());
			
			pstmt.executeUpdate();
			con.commit();
			
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Contestacion_De_Cuestionario ] Insert  SQLException "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);

			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Contestacion_De_Cuestionario ] Insert  SQLException "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				}
			}
			return false;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Contestacion_De_Cuestionario ] Insert  SQLException "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}		
		return true;
	}
	
	public boolean Guardar_Huella(int folio_emp, ByteArrayInputStream datosHuella,int tamañoHuella, ByteArrayInputStream datosHuella2,int tamañoHuella2){
		
		String query = "exec insert_huella_digital ?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
	     try {
	    	 con.setAutoCommit(false);
			 pstmt = con.prepareStatement(query);
			 
	    	 pstmt.setInt(1,folio_emp);
	    	 pstmt.setBinaryStream(2, datosHuella,tamañoHuella);
	    	 pstmt.setBinaryStream(3, datosHuella2,tamañoHuella2);
	    	 
	    	 pstmt.executeUpdate();
				con.commit();
				
			}catch (Exception e) {
				System.out.println("SQLException: "+e.getMessage());
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Huella ] Insert  SQLException "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);

				if(con != null){
					try{
						System.out.println("La transacción ha sido abortada");
						con.rollback();
					}catch(SQLException ex){
						System.out.println(ex.getMessage());
						JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Huella ] Insert  SQLException "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
					}
				}
				return false;
			}finally{
				try {
					con.close();
				} catch(SQLException e){
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_Huella ] Insert  SQLException "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
					return false;
				}
			}		
			return true;
	}
	

	public Obj_Ventas_Express Guardar_Venta_Express(Obj_Ventas_Express ventas_express){
		int folio_transaccion=ventas_express.getFolio();
		if(ventas_express.getGuardar_actualizar().equals("N")){
		  folio_transaccion=busca_y_actualiza_proximo_folio(67);
		  ventas_express.setFolio(folio_transaccion);
		}
		
		String query = "exec ventas_express_insert_y_actualiza ?,?,?,?,?,?,?,?,?,?,?,?,?,?";
		Connection con = new Connexion().conexion();
		
		try {
			con.setAutoCommit(false);
			PreparedStatement pstmt = con.prepareStatement(query);
			for(int i=0; i<ventas_express.getTabla_prodcutos().length; i++){
				pstmt.setInt   (1  , folio_transaccion);
				pstmt.setString(2  , ventas_express.getEstablecimiento().toString());
				pstmt.setString(3  , ventas_express.getTipo_de_cliente().toString());
				pstmt.setString(4  , ventas_express.getFolio_cliente().toString());
				pstmt.setString(5  , ventas_express.getNotas().toString());
				pstmt.setString(6  , ventas_express.getFolio_vendedor().toString());
				pstmt.setDouble(7  , ventas_express.getTotal_venta());
				pstmt.setString(8  , ventas_express.getFolio_proveedor());
				pstmt.setString(9  , ventas_express.getFolio_supervisor_autoriza());
				pstmt.setString(10 , ventas_express.getTabla_prodcutos()[i][0].toString().trim());
				pstmt.setString(11 , ventas_express.getTabla_prodcutos()[i][2].toString().trim());
				pstmt.setString(12 , ventas_express.getTabla_prodcutos()[i][3].toString().trim()); 	
				pstmt.setString(13 , ventas_express.getEstatus());
				pstmt.setString(14 , ventas_express.getGuardar_actualizar());
				
				pstmt.executeUpdate();
			 con.commit();
			}
			
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage()+"\n"+query+"\nSQLException:"+e.getMessage());
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [  Guardar_Venta_Express ]\n"+query+"\nSQLException:"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage()+"\n"+query+"\nSQLException:"+e.getMessage());
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [  Guardar_Venta_Express ]\n"+query+"\nSQLException:"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
				}
			}
			return null;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
		}		
		return ventas_express;
	}
	
	public Obj_Ventas_Express Guardar_Liquidacion_Venta_Express(Obj_Ventas_Express ventas_express){	
		String query = "exec ventas_express_liquidacion_insert_y_actualiza ?,?,?,?,?,?,?,?";
		Connection con = new Connexion().conexion();
		try {con.setAutoCommit(false);
			PreparedStatement pstmt = con.prepareStatement(query);
				pstmt.setInt   (1 , ventas_express.getFolio());
				pstmt.setDouble(2 , ventas_express.getDeuda_antes_de_abono());
				pstmt.setDouble(3 , ventas_express.getAbono());
				pstmt.setDouble(4 , ventas_express.getSaldo());
				pstmt.setInt   (5 , ventas_express.getFolio_usuario_abono());
				pstmt.setString(6 , ventas_express.getEstatus());
				pstmt.setString(7 , ventas_express.getGuardar_actualizar());
				pstmt.setString(8 , ventas_express.getFolio_recepcion_de_compra());
				
				pstmt.executeUpdate();
			 con.commit();
				
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage()+"\n"+query+"\nSQLException:"+e.getMessage()+"\n"+query);
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [  Guardar_Liquidacion_Venta_Express ]\n"+query+"\nSQLException:"+e.getMessage()+"\n"+query, "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage()+"\n"+query+"\nSQLException:"+e.getMessage()+"\n"+query);
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [  Guardar_Liquidacion_Venta_Express ]\n"+query+"\nSQLException:"+e.getMessage()+"\n"+query, "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
				}
			}
			return null;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
		}		
		return ventas_express;
	}
	
	
	public boolean guardarRevision(Obj_Revision_De_Horario_Por_Nivel_Jerarquico revision){
		
		String query = "exec actualizar_horario_nivel_gerarquico ?,?,?,?,?";
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
	     try {
	    	 con.setAutoCommit(false);
			 pstmt = con.prepareStatement(query);
			 
	    	 pstmt.setInt(1,revision.getFolio_colaborador());
	    	 pstmt.setBoolean(2,revision.isStatus_h1());
	    	 pstmt.setBoolean(3,revision.isStatus_h2());
	    	 pstmt.setBoolean(4,revision.isStatus_h3());
	    	 pstmt.setString(5, revision.getTurno_cuadrante());
	    	 
	    	 pstmt.executeUpdate();
				con.commit();
				
			}catch (Exception e) {
				System.out.println("SQLException: "+e.getMessage());
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ guardarRevision ] Insert  SQLException "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);

				if(con != null){
					try{
						System.out.println("La transacción ha sido abortada");
						con.rollback();
					}catch(SQLException ex){
						System.out.println(ex.getMessage());
						JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ guardarRevision ] Insert  SQLException "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
					}
				}
				return false;
			}finally{
				try {
					con.close();
				} catch(SQLException e){
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ guardarRevision ] Insert  SQLException "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
					return false;
				}
			}		
			return true;
	}

	
	public Obj_Clientes_Ventas Guardar_Venta_Express(Obj_Clientes_Ventas ventas_clientes){
		int folio_transaccion=ventas_clientes.getFolio();
		String querymod ="";
				
		if(ventas_clientes.getGuardar_actualizar().equals("N")){
		  folio_transaccion=busca_y_actualiza_proximo_folio(66);
		  ventas_clientes.setFolio(folio_transaccion);
		}
		String query = "exec ventas_express_clientes_insert_y_actualiza ?,?,?,?,?,?,?,?";
		Connection con = new Connexion().conexion();
		
		try {
			con.setAutoCommit(false);
			PreparedStatement pstmt = con.prepareStatement(query);
				pstmt.setInt   (1 ,  folio_transaccion                          );
				pstmt.setString(2 ,  ventas_clientes.getNombre().toString()     );
				pstmt.setString(3 ,  ventas_clientes.getAp_paterno().toString() );
				pstmt.setString(4 ,  ventas_clientes.getAp_materno().toString() );
				pstmt.setString(5 ,  ventas_clientes.getDomicilio().toString()  );
				pstmt.setString(6 ,  ventas_clientes.getTelefono().toString()   );
				pstmt.setString(7 ,  ventas_clientes.getEstatus().toString()    );
				pstmt.setString(8 ,  ventas_clientes.getGuardar_actualizar().toString()     );
				pstmt.executeUpdate();
			 con.commit();
			
		} catch (Exception e) {
			System.out.println("SQLException: "+e.getMessage()+"\n"+query+"\nSQLException:"+e.getMessage()+"\n"+querymod);
			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [  Guardar_Venta_Express ]\n"+query+"\nSQLException:"+e.getMessage()+"\n"+querymod, "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
			if(con != null){
				try{
					System.out.println("La transacción ha sido abortada");
					con.rollback();
				}catch(SQLException ex){
					System.out.println(ex.getMessage()+"\n"+query+"\nSQLException:"+e.getMessage()+"\n"+querymod);
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [  Guardar_Venta_Express ]\n"+query+"\nSQLException:"+e.getMessage()+"\n"+querymod, "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
				}
			}
			return null;
		}finally{
			try {
				con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
		}		
		return ventas_clientes;
	}
	
	public boolean guardarDPR(Obj_Descripcion_De_Puestos_y_Responsabilidades dpr, String movimiento){
		
		String query = "exec sp_guardar_dpr ?,?,?,?,?,?,?,?,?,?,"
										+ " ?,?,?,?,?,?,?,?,?,?,"
										+ " ?,?,?,?,?,?,?,?,?,?,"
										+ " ?,?,?,?,?,?,?,'"+dpr.getXmlResponsabilidadesPuesto()+"'";//38------?
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
	     try {
	    	 con.setAutoCommit(false);
			 pstmt = con.prepareStatement(query);
			 
			 int i=1;
			 
			 pstmt.setString(i,movimiento);
			 pstmt.setInt(i+=1,dpr.getFolio());
			 pstmt.setInt(i+=1,dpr.getFolioPuesto());
			 pstmt.setString(i+=1,dpr.getUnidadNegocio());
			 pstmt.setString(i+=1,dpr.getEstablecimiento());
			 pstmt.setString(i+=1,dpr.getDepartamento());
			 pstmt.setInt(i+=1,dpr.getEdadIn());
			 pstmt.setInt(i+=1,dpr.getEdadFin());
			 pstmt.setInt(i+=1,dpr.getFolioReportaA());
			 pstmt.setString(i+=1,dpr.getSexo());
			 pstmt.setString(i+=1,dpr.getEstadoCivil());
			 
			 pstmt.setString(i+=1,dpr.getObjetivoPuesto());
			 
			 pstmt.setString(i+=1,dpr.getNivelEstudios());
			 pstmt.setString(i+=1,dpr.getListaDeEspecificaciones());
			 pstmt.setString(i+=1,dpr.getCursosHabilidades());
			 pstmt.setString(i+=1,dpr.getEsperienciaGeneral());
			 pstmt.setString(i+=1,dpr.getEsperienciaEspecifica());
			 pstmt.setInt(i+=1,dpr.getFacultamientosDirectos());
			 pstmt.setInt(i+=1,dpr.getFacultamientosIndirectos());
			 
			 pstmt.setInt(i+=1,dpr.getInteracionDelPuestoExternas());
			 pstmt.setString(i+=1,dpr.getRelacionDelPuestoExternas());
			 pstmt.setInt(i+=1,dpr.getInteracionDelPuestoInternas());
			 pstmt.setString(i+=1,dpr.getRelacionDelPuestoInternas());
				
			 pstmt.setString(i+=1,dpr.getAmbienteDeTrabajo());
			 pstmt.setString(i+=1,dpr.getEsfuerzoFisico());
			 
			 pstmt.setBoolean(i+=1,dpr.isViaje());
			 
			 pstmt.setBoolean(i+=1,dpr.isLaptop());
			 pstmt.setBoolean(i+=1,dpr.isPc());
			 pstmt.setBoolean(i+=1,dpr.isCelular());
			 pstmt.setBoolean(i+=1,dpr.isExtencion());
			 pstmt.setBoolean(i+=1,dpr.isAutoPropio());
			 pstmt.setBoolean(i+=1,dpr.isAutoEmpresa());
			 pstmt.setBoolean(i+=1,dpr.isLicencia());
			 pstmt.setBoolean(i+=1,dpr.isLargaDistancia());
			 pstmt.setBoolean(i+=1,dpr.isOtro());
			 
			 pstmt.setString(i+=1,dpr.getNotaOtro());
			 
//			 System.out.println(dpr.getOrganigramaB());
			 
			 InputStream input = new ByteArrayInputStream(dpr.getOrganigramaB());
//			 pstmt.setBinaryStream(i+=1, input,433);
			 pstmt.setBinaryStream(i+=1, input, dpr.getOrganigramaB().length );
			 
			 
//			//--- imagen de organigrama -------------------------------------------------------------------------------------------------------------------------				
//			 ByteArrayInputStream stream_foto = null;
//			stream_foto = new ByteArrayInputStream(dpr.getOrganigramaB());
//			
//			 pstmt.setBinaryStream(i+=1, stream_foto, 70110);
//			 
			 
				
////------------------------------------------------------------------------------------------------------------------------------------------------------------
	    	 pstmt.executeUpdate();
				con.commit();
				
			}catch (SQLException e) {
				System.out.println("SQLException: "+e.getMessage());
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ guardarDPR ] Insert  SQLException "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);

				if(con != null){
					try{
						System.out.println("La transacción ha sido abortada");
						con.rollback();
					}catch(SQLException ex){
						System.out.println(ex.getMessage());
						JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ guardarDPR ] Insert  SQLException "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
					}
				}
				return false;
			}finally{
				try {
					con.close();
				} catch(SQLException e){
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ guardarDPR ] Insert  SQLException "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
					return false;
				}
			}		
			return true;
	}
	
	public String readIt(InputStream is) throws IOException {
	    if (is != null) {
	        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"), 8);

	        StringBuilder sb = new StringBuilder();
	        String line;
	        while ((line = reader.readLine()) != null) {
	            sb.append(line).append("\n");
	        }
	        is.close();
	        return sb.toString();
	    }
	    return "error: ";
	}
	
	public boolean reactivarMerma(int folio_de_merma){
		
		String query = "exec merma_reactivar_folio "+folio_de_merma;
		Connection con = new Connexion().conexion();
		PreparedStatement pstmt = null;
	     try {
	    	 con.setAutoCommit(false);
			 pstmt = con.prepareStatement(query);
			 
//	    	 pstmt.setInt(1,folio_de_merma);
	    	 
	    	 pstmt.executeUpdate();
				con.commit();
				
			}catch (Exception e) {
				System.out.println("SQLException: "+e.getMessage());
				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ reactivarMerma ] Insert  SQLException "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);

				if(con != null){
					try{
						System.out.println("La transacción ha sido abortada");
						con.rollback();
					}catch(SQLException ex){
						System.out.println(ex.getMessage());
						JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ reactivarMerma ] Insert  SQLException "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
					}
				}
				return false;
			}finally{
				try {
					con.close();
				} catch(SQLException e){
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ reactivarMerma ] Insert  SQLException "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
					return false;
				}
			}		
			return true;
	}
	
//	public boolean Entrada_Dedddd_Insumos(String xml,String nota,String estabRecibe, int folioEmpleadoRecibe, String razon,String estabSurte,String movimiento){
//		
//		String query = "exec aumento_de_insumos '"+xml+"','"+nota+"',"+usuario.getFolio()+",'"+razon+"','"+estabRecibe+"'";
//		String query2 = "exec disminucion_de_insumos '"+xml+"','"+nota+"',"+usuario.getFolio()+",'"+estabRecibe+"',"+folioEmpleadoRecibe+",'"+razon+"','"+estabSurte+"'";
//		
//		String queryF = "";
//		
//		switch(movimiento){
//			case "aumento":		queryF = query	;break;
//			case "disminucion":	queryF = query2	;break;
//			default:			queryF = "x"	; break;
//		}
//
//		Connection con = new Connexion().conexion();
//		PreparedStatement pstmt = null;
//		try {
//			 con.setAutoCommit(false);
//			 pstmt = con.prepareStatement(queryF);
//			 pstmt.executeUpdate();
//
//				con.commit();
//		} catch (Exception e) {
//			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Entrada_De_Insumos ] "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
//			
//			System.out.println("SQLException: " + e.getMessage());
//			if (con != null){
//				try {
//					System.out.println("La transacción ha sido abortada");
//					con.rollback();
//				} catch(SQLException ex) {
//					System.out.println(ex.getMessage());
//				}
//			} 
//			return false;
//		}finally{
//			try {
//				pstmt.close();
//				con.close();
//			} catch(SQLException e){
//				e.printStackTrace();
//			}
//		}		
//		return true;
//	}
	   
//	public boolean Guardar_Alimentacion_De_Productos_Proximos_A_Caducar(Obj_Alimentacion_De_Productos_Proximos_A_Caducar proximos_caducar){
//		int folio_transaccion=busca_y_actualiza_proximo_folio(42);
//		proximos_caducar.setFolio(folio_transaccion+"");
//		String query = "exec sp_guardar_actualizar_productos_proximos_a_caducar ?,?,?,?,?,?,?,?,?,?,?,?";
//		Connection con = new Connexion().conexion();	
//		try {
//			con.setAutoCommit(false);
//			PreparedStatement pstmt = con.prepareStatement(query);
//			//@folio_proximos_caducar int, @establecimiento char(100) ,@cod_prod char(10) ,@cantidad int  ,@ultimo_costo money ,@costo_promedio money ,@precio_de_lista money ,@fecha_caducidad datetime ,@estatus char(1), @folio_usuario int, @notas varchar(500),@guarda_actualiza char(1)
//			int cantidad_filas=proximos_caducar.getTabla_obj().length;
//			//guarda solo notas y el registro en 0
//			
//			 if(cantidad_filas==0){
//				 pstmt.setInt   (1 , folio_transaccion);
// 				 pstmt.setString(2 , proximos_caducar.getEstablecimiento());
//				 pstmt.setString(3 , "0");
//				 pstmt.setFloat (4 ,  0 );
//				 pstmt.setFloat (5 ,  0 );
//				 pstmt.setFloat (6 ,  0 );
//				 pstmt.setFloat (7 ,  0 );
//				 pstmt.setString(8 , "01/01/1900");
//				 pstmt.setString(9 , proximos_caducar.getStatus());
//				 pstmt.setInt   (10, usuario.getFolio());
//				 pstmt.setString(11, proximos_caducar.getNotas());				
//				 pstmt.setString(12, proximos_caducar.getGuardar_actualizar());
//				 pstmt.executeUpdate();
//			 }else{
//				 //guardado normal
//				for(int i=0; i<cantidad_filas ; i++){
//					pstmt.setInt   (1 ,  folio_transaccion);
//					pstmt.setString(2 ,  proximos_caducar.getEstablecimiento());
//					pstmt.setString(3 ,  proximos_caducar.getTabla_obj()[i][0].toString().trim());
//					pstmt.setFloat (4 ,  Float.valueOf(proximos_caducar.getTabla_obj()[i][3].toString().trim()));
//					pstmt.setFloat (5 ,  Float.valueOf(proximos_caducar.getTabla_obj()[i][5].toString().trim()));
//					pstmt.setFloat (6 ,  Float.valueOf(proximos_caducar.getTabla_obj()[i][6].toString().trim()));
//					pstmt.setFloat (7 ,  Float.valueOf(proximos_caducar.getTabla_obj()[i][7].toString().trim()));
//					pstmt.setString(8 ,  proximos_caducar.getTabla_obj()[i][4].toString().trim());
//					pstmt.setString(9 ,  proximos_caducar.getStatus());
//					pstmt.setInt   (10,  usuario.getFolio());
//					pstmt.setString(11,  proximos_caducar.getNotas());				
//					pstmt.setString(12,  proximos_caducar.getGuardar_actualizar());
//					pstmt.executeUpdate();
//			    }
//			 }
//			con.commit();
//		} catch (Exception e) {
//			System.out.println("SQLException: "+e.getMessage());
//			JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_inventarios_parciales ]\n"+query+"\nSQLException:"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
// 		if(con != null){
//				try{
//					System.out.println("La transacción ha sido abortada");
//					con.rollback();
//				}catch(SQLException ex){
//					System.out.println(ex.getMessage());
//					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ Guardar_inventarios_parciales ]\n"+query+"\nSQLException:"+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE,new ImageIcon("imagen/usuario-icono-eliminar5252-64.png"));
//				}
//			}
//			return false;
//		}finally{
//			try {
//				con.close();
//			} catch(SQLException e){
//				e.printStackTrace();
//			}
//		}		
//		return true;
//	}
	
	//--------------------traba el servicio
	//public boolean envio_de_correo(){
//		String query =  "sp_envio_de_correo_automatico_de_servicios_nuevos";
//		Connection con = new Connexion().conexion();
//		PreparedStatement pstmt = null;
	//	
//		try {
//			con.setAutoCommit(false);
//			pstmt = con.prepareStatement(query);
//			pstmt.executeUpdate();
//			
//			con.commit();
//		} catch (Exception e) {
//			System.out.println("SQLException: " + e.getMessage() +"   >   "+ e.getLocalizedMessage() );
//			if (con != null){
//				try {
//					System.out.println("La transacción ha sido abortada");
//					con.rollback();
//				} catch(SQLException ex) {
//					System.out.println(ex.getMessage());
//					JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ envio_de_correo ] Insert  SQLException: sp_envio_de_correo_automatico_de_servicios_nuevos "+ex.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
//				}
//			} 
//			return false;
//		}finally{
//			try {
//				con.close();
//			} catch(SQLException e){
//				e.printStackTrace();
//				JOptionPane.showMessageDialog(null, "Error en GuardarSQL  en la funcion [ envio_de_correo ] Insert  SQLException: sp_envio_de_correo_automatico_de_servicios_nuevos "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
//			}
//		}		
//		return true;
	//}
	
} 
