package Obj_Administracion_del_Sistema;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Vector;

import Conexiones_SQL.ActualizarSQL;
import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.GuardarSQL;

public class Obj_Usuario {
	private int folio;
	private String nombre_completo;
	private String contrasena;
	private int permiso_id;
	private String fecha_alta;
	private String fecha_actua;
	private String sesion;
	private String vista_previa_impresion;
	private int status;
	private int acceso_a_costos_y_precio_de_venta;
	private String color;
	
	 int RFuente;
	 int GFuente;
	 int BFuente;
	 ///fuente Seleccionada
	 int RFuenteS;
	 int GFuenteS;
	 int BFuenteS;
	 ///fila non
	 int Rfila;
	 int Gfila;
	 int Bfila;
	 //fila seleccionada
	 int RfilaS;
	 int GfilaS;
	 int BfilaS;
	 
	 int tamanio_fuente;
	
	public String getColor() {
		return color;
	}


	public void setColor(String color) {
		this.color = color;
	}


	public int getTamanio_fuente() {
		return tamanio_fuente;
	}


	public void setTamanio_fuente(int tamanio_fuente) {
		this.tamanio_fuente = tamanio_fuente;
	}


	public int getRFuente() {
		return RFuente;
	}


	public void setRFuente(int rFuente) {
		RFuente = rFuente;
	}


	public int getGFuente() {
		return GFuente;
	}


	public void setGFuente(int gFuente) {
		GFuente = gFuente;
	}


	public int getBFuente() {
		return BFuente;
	}


	public void setBFuente(int bFuente) {
		BFuente = bFuente;
	}


	public int getRFuenteS() {
		return RFuenteS;
	}


	public void setRFuenteS(int rFuenteS) {
		RFuenteS = rFuenteS;
	}


	public int getGFuenteS() {
		return GFuenteS;
	}


	public void setGFuenteS(int gFuenteS) {
		GFuenteS = gFuenteS;
	}


	public int getBFuenteS() {
		return BFuenteS;
	}


	public void setBFuenteS(int bFuenteS) {
		BFuenteS = bFuenteS;
	}


	public int getRfila() {
		return Rfila;
	}


	public void setRfila(int rfila) {
		Rfila = rfila;
	}


	public int getGfila() {
		return Gfila;
	}


	public void setGfila(int gfila) {
		Gfila = gfila;
	}


	public int getBfila() {
		return Bfila;
	}


	public void setBfila(int bfila) {
		Bfila = bfila;
	}


	public int getRfilaS() {
		return RfilaS;
	}


	public void setRfilaS(int rfilaS) {
		RfilaS = rfilaS;
	}


	public int getGfilaS() {
		return GfilaS;
	}


	public void setGfilaS(int gfilaS) {
		GfilaS = gfilaS;
	}


	public int getBfilaS() {
		return BfilaS;
	}


	public void setBfilaS(int bfilaS) {
		BfilaS = bfilaS;
	}


	public Obj_Usuario(){
		this.folio=0; nombre_completo=""; contrasena=""; permiso_id=0; status=0; fecha_alta=""; fecha_actua=""; sesion="";vista_previa_impresion="";acceso_a_costos_y_precio_de_venta=0;
	}


	public String getFecha_alta() {
		return fecha_alta;
	}


	public void setFecha_alta(String fechaAlta) {
		this.fecha_alta = fechaAlta;
	}


	public String getFecha_actua() {
		return fecha_actua;
	}


	public void setFecha_actua(String fechaActua) {
		this.fecha_actua = fechaActua;
	}


	public int getFolio() {
		return folio;
	}


	public void setFolio(int folio) {
		this.folio = folio;
	}


	public String getNombre_completo() {
		return nombre_completo;
	}


	public void setNombre_completo(String nombreCompleto) {
		nombre_completo = nombreCompleto;
	}


	public String getContrasena() {
		return contrasena;
	}


	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}


	public int getPermiso_id() {
		return permiso_id;
	}


	public void setPermiso_id(int permisoId) {
		permiso_id = permisoId;
	}


	public String getVista_previa_impresion() {
		return vista_previa_impresion;
	}


	public void setVista_previa_impresion(String vista_previa_impresion) {
		this.vista_previa_impresion = vista_previa_impresion;
	}
	
	
	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getSesion() {
		return sesion;
	}

	public void setSesion(String sesion) {
		this.sesion = sesion;
	}
	
	public int getAcceso_a_costos_y_precio_de_venta() {
		return acceso_a_costos_y_precio_de_venta;
	}


	public void setAcceso_a_costos_y_precio_de_venta(
			int acceso_a_costos_y_precio_de_venta) {
		this.acceso_a_costos_y_precio_de_venta = acceso_a_costos_y_precio_de_venta;
	}


	public boolean guardar(){ return new GuardarSQL().Guardar_Usuario(this); }
	
	public Obj_Usuario buscar(int folio, String Color){ 
		try {
			return new BuscarSQL().Usuario(folio, Color);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; 
	}
	
	public Obj_Usuario buscarMaximo() {
		try {
			return new BuscarSQL().Maximo();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	public boolean guardarPermisos(Vector Permisos){ 
		return new GuardarSQL().Guardar_Usuario(this, Permisos);
	}
	
	
	public boolean ExisteUsuario(int folio){ 
		try {
			return new BuscarSQL().existeUsuario(folio);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@SuppressWarnings("rawtypes")
	public boolean actualizar(int folio_empleado, Vector Permisos) {
		return new ActualizarSQL().PermisoUsuario(folio_empleado, Permisos); 
	}
	
	
	public Obj_Usuario BuscarUsuario(int folio_empleado){
		try {
			return new BuscarSQL().BuscarUsuarios(folio_empleado);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; 
	}
	
	@SuppressWarnings("rawtypes")
	public Vector returnPermisos(int folio_empleado, int menu){
		try {
			return new BuscarSQL().returnPermiso(folio_empleado,menu);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; 
	}
	
	public void Session(){
		new GuardarSQL().Guardar_Sesion(this);
	}
	
	public boolean CambiarContrasena(int folio_empleado ,String nuevacontrasena){
		return new ActualizarSQL().GuardaNuevaContrasena(folio_empleado,nuevacontrasena);
	}
	
	public boolean Clonar_permisos(int folio_empleado ,String empleado_de_clonar){
		return new ActualizarSQL().GuardarOpciones_Clonadas(folio_empleado,empleado_de_clonar);
	}
	
	public Obj_Usuario LeerSession()
    {
    	try{
    		return new BuscarSQL().getSession();
    	}catch(IOException e){
    		e.printStackTrace();
    		return null;
    	}
  }

}
