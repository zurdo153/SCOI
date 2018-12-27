package Obj_Contabilidad;

import java.io.File;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import Conexiones_SQL.ActualizarSQL;
import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Cargar_Combo;
import Conexiones_SQL.GuardarSQL;


public class Obj_Proveedores {
	 String folio_factura;
	 String fecha;
	 Boolean status;
	 String cod_prv;
	 String proveedor;
	 String nombre_comercial;
	 String telefono1;
	 String telefono2;
	 String telefono3;
	 String tipo_proveedor;
	 String notas;

	 public Obj_Proveedores(){
		this.folio_factura=""; this.fecha=""; this.cod_prv ="";this.proveedor=""; this.status=false;
		this.nombre_comercial="";this.telefono1="";this.telefono2="";this.telefono3="";this.tipo_proveedor="";this.notas="";
	}
	
	 
	public String getNombre_comercial() {
		return nombre_comercial;
	}


	public void setNombre_comercial(String nombre_comercial) {
		this.nombre_comercial = nombre_comercial;
	}


	public String getTelefono1() {
		return telefono1;
	}


	public void setTelefono1(String telefono1) {
		this.telefono1 = telefono1;
	}


	public String getTelefono2() {
		return telefono2;
	}


	public void setTelefono2(String telefono2) {
		this.telefono2 = telefono2;
	}


	public String getTelefono3() {
		return telefono3;
	}


	public void setTelefono3(String telefono3) {
		this.telefono3 = telefono3;
	}


	public String getTipo_proveedor() {
		return tipo_proveedor;
	}


	public void setTipo_proveedor(String tipo_proveedor) {
		this.tipo_proveedor = tipo_proveedor;
	}


	public String getNotas() {
		return notas;
	}


	public void setNotas(String notas) {
		this.notas = notas;
	}


	public String getFolio_factura() {
		return folio_factura;
	}


	public void setFolio_factura(String folio_factura) {
		this.folio_factura = folio_factura;
	}


	public String getFecha() {
		return fecha;
	}


	public void setFecha(String fecha) {
		this.fecha = fecha;
	}


	public String getCod_prv() {
		return cod_prv;
	}


	public void setCod_prv(String cod_prv) {
		this.cod_prv = cod_prv;
	}


	public String getProveedor() {
		return proveedor;
	}


	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}


	public Boolean getStatus() {
		return status;
	}


	public void setStatus(Boolean status) {
		this.status = status;
	}


	public Obj_Proveedores buscar(String folio_factura, String cod_prv){
		try {
			return new BuscarSQL().Buscar_factura_proveedor_control_xml(folio_factura,cod_prv);
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error en Objeto Obj_Proveedores  en la funcion Buscar SQLException: "+e.getMessage(), "Avisa al Administrador", JOptionPane.ERROR_MESSAGE);
		}
		return null; 
	}
	
	public boolean guardar(){
		return new GuardarSQL().Guardar_Control_Factura_xml(this);
		}
	
	public boolean actualizar(String folio_Factura_Editada){
		return new ActualizarSQL().Factura_Provedores_xml(this,folio_Factura_Editada); 
		}
	
	public boolean marcar_recibido_factura(String cod_prov_recibido, String folio_factura_recibido,String tipo_archivo,File xml_pdf){
		return new ActualizarSQL().marcar_c_recibido_factura(cod_prov_recibido, folio_factura_recibido,tipo_archivo,xml_pdf); 
		}

	public String[] Combo_tipos() {
		try {
			return new Cargar_Combo().Combos("Tipos_Proveedores_Mayusculas");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; 
	}
	
	public String[] Combo_tipos_Todos() {
		try {
			return new Cargar_Combo().Combos("Tipos_Proveedores_MayusculasTod");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; 
	}
	
	public boolean Guardar_Proveedor_BMS(){
		return new GuardarSQL().Guardar_Proveedor_Tipo_y_Datos_Contacto(this);
	}
}
