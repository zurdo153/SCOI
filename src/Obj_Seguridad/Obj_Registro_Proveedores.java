package Obj_Seguridad;

import Conexiones_SQL.BuscarSQL;

public class Obj_Registro_Proveedores {
	int folio;
	int folio_colaborador_recibe;
	String nombre_recibe;
	String establecimiento;
	String proveedor;
	String chofer_del_proveedor;
	String observaciones;
	String estatus;
	String NuevoModifica;
	String existe;
	String [][] tabla_facturas;
	String [][] tabla_evaluacion;
	String [][] tabla_registros;
	 
	public Obj_Registro_Proveedores(){
		this.folio=0;
		this.folio_colaborador_recibe=0;
		this.nombre_recibe="";
		this.establecimiento="";
		this.proveedor="";
		this.chofer_del_proveedor="";
		this.observaciones="";		
		this.estatus="";
		this.NuevoModifica="";
		this.tabla_facturas=null;
		this.tabla_evaluacion=null;
		this.tabla_registros=null;
		this.existe="";
	}
	
	public String getExiste() {
		return existe;
	}

	public void setExiste(String existe) {
		this.existe = existe;
	}

	public int getFolio() {
		return folio;
	}

	public void setFolio(int folio) {
		this.folio = folio;
	}

	public int getFolio_colaborador_recibe() {
		return folio_colaborador_recibe;
	}

	public void setFolio_colaborador_recibe(int folio_colaborador_recibe) {
		this.folio_colaborador_recibe = folio_colaborador_recibe;
	}

	public String getNombre_recibe() {
		return nombre_recibe;
	}

	public void setNombre_recibe(String nombre_recibe) {
		this.nombre_recibe = nombre_recibe;
	}

	public String getEstablecimiento() {
		return establecimiento;
	}

	public void setEstablecimiento(String establecimiento) {
		this.establecimiento = establecimiento;
	}

	public String getProveedor() {
		return proveedor;
	}

	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}

	public String getChofer_del_proveedor() {
		return chofer_del_proveedor;
	}

	public void setChofer_del_proveedor(String chofer_del_proveedor) {
		this.chofer_del_proveedor = chofer_del_proveedor;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public String getNuevoModifica() {
		return NuevoModifica;
	}

	public void setNuevoModifica(String nuevoModifica) {
		NuevoModifica = nuevoModifica;
	}

	public String[][] getTabla_facturas() {
		return tabla_facturas;
	}

	public void setTabla_facturas(String[][] tabla_facturas) {
		this.tabla_facturas = tabla_facturas;
	}

	public String[][] getTabla_evaluacion() {
		return tabla_evaluacion;
	}

	public void setTabla_evaluacion(String[][] tabla_evaluacion) {
		this.tabla_evaluacion = tabla_evaluacion;
	}

	public String[][] getTabla_registros() {
		return tabla_registros;
	}

	public void setTabla_registros(String[][] tabla_registros) {
		this.tabla_registros = tabla_registros;
	}

	public int Nuevo(){
		return new BuscarSQL().Devuelve_ultimo_folio_transaccion("76");
	}
	
	public Obj_Registro_Proveedores Valida_existe_colaborador(String clave_checador){
		return new BuscarSQL().buscar_Datos_Colaborador_por_clave_checador(clave_checador);
	}
	
	public String[][] refrescar_tablas(){
		return new BuscarSQL().TablaProveedores();
	}
	
	public String[][] refrescar_tablas_ordenes_de_compra(String Establecimiento){
		return new BuscarSQL().TablaOrdenes_De_Compra(Establecimiento);
	}
	
	
}
