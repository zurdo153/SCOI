package Obj_Lista_de_Raya;

import java.sql.SQLException;

import Conexiones_SQL.BuscarSQL;
import Conexiones_SQL.Cargar_Combo;
import Conexiones_SQL.GuardarSQL;

public class Obj_Perfil_De_Puestos {
	
//	datos personales	
	private int folio;
	private String perfil;
	private String edad;
	private String sexo;
	private String puesto_al_que_reporta;
	
	private String establecimiento;
	private String departameto;
	private String puesto;
	private String nivel_de_puesto;
	
//	laboral
	private int horario;
	private int horario2;
	private int horario3;
	
	private String horarioNombre;
	private String horario2Nombre;
	private String horario3Nombre;
	
	private int status_h1;
	private int status_h2;
	private int status_h3;
	private int status_rotativo;
//	dependen del horario activo
	private String descanso;
	private String dobla;
	
	//	percepciones y deducciones
	private boolean gafete;
	private int prestamo;
	private float salario_diario;
	private float salario_diario_integrado;
	
	private float sueldo;
	private float bonocomplemento;
	private float bono_asistencia;
	private float bono_puntualidad;

	
	private String objetivo_del_puesto;
	private String experiencia;
	private String actividades_Principales;
	private String habilidades;
	private String conocimiento;
	private String fecha_actualizacion;
	
	
	public Obj_Perfil_De_Puestos(){
		
		folio=0; 
		edad=""; 
		sexo=""; 
		puesto_al_que_reporta="";
		
		establecimiento=""; 
		departameto=""; 
		puesto="";
		nivel_de_puesto="";
		
		horario=0; horario2=0; horario3=0; 
		horarioNombre=""; horario2Nombre=""; horario3Nombre=""; 
		
		status_h1=0; status_h2=0; status_h3=0; status_rotativo=0;
		descanso=""; dobla=""; 
		
		gafete=false;
		prestamo=0;
		salario_diario=0; 
		salario_diario_integrado=0; 
		
		sueldo=0;
		bonocomplemento=0;
		bono_asistencia=0;
		bono_puntualidad=0;
		
		objetivo_del_puesto="";      
		experiencia="";              
		actividades_Principales="";  
		habilidades="";              
		conocimiento="";             
		fecha_actualizacion="";      
		
	}
	
	public int getFolio() {
		return folio;
	}

	public void setFolio(int folio) {
		this.folio = folio;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	public String getEdad() {
		return edad;
	}

	public void setEdad(String edad) {
		this.edad = edad;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getPuesto_al_que_reporta() {
		return puesto_al_que_reporta;
	}

	public void setPuesto_al_que_reporta(String puesto_al_que_reporta) {
		this.puesto_al_que_reporta = puesto_al_que_reporta;
	}

	public String getEstablecimiento() {
		return establecimiento;
	}

	public void setEstablecimiento(String establecimiento) {
		this.establecimiento = establecimiento;
	}

	public String getDepartameto() {
		return departameto;
	}

	public void setDepartameto(String departameto) {
		this.departameto = departameto;
	}

	public String getPuesto() {
		return puesto;
	}

	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}

	public String getNivel_de_puesto() {
		return nivel_de_puesto;
	}

	public void setNivel_de_puesto(String nivel_de_puesto) {
		this.nivel_de_puesto = nivel_de_puesto;
	}

	public int getHorario() {
		return horario;
	}

	public void setHorario(int horario) {
		this.horario = horario;
	}

	public int getHorario2() {
		return horario2;
	}

	public void setHorario2(int horario2) {
		this.horario2 = horario2;
	}

	public int getHorario3() {
		return horario3;
	}

	public void setHorario3(int horario3) {
		this.horario3 = horario3;
	}

	public String getHorarioNombre() {
		return horarioNombre;
	}

	public void setHorarioNombre(String horarioNombre) {
		this.horarioNombre = horarioNombre;
	}

	public String getHorario2Nombre() {
		return horario2Nombre;
	}

	public void setHorario2Nombre(String horario2Nombre) {
		this.horario2Nombre = horario2Nombre;
	}

	public String getHorario3Nombre() {
		return horario3Nombre;
	}

	public void setHorario3Nombre(String horario3Nombre) {
		this.horario3Nombre = horario3Nombre;
	}

	public int getStatus_h1() {
		return status_h1;
	}

	public void setStatus_h1(int status_h1) {
		this.status_h1 = status_h1;
	}

	public int getStatus_h2() {
		return status_h2;
	}

	public void setStatus_h2(int status_h2) {
		this.status_h2 = status_h2;
	}

	public int getStatus_h3() {
		return status_h3;
	}

	public void setStatus_h3(int status_h3) {
		this.status_h3 = status_h3;
	}

	public int getStatus_rotativo() {
		return status_rotativo;
	}

	public void setStatus_rotativo(int status_rotativo) {
		this.status_rotativo = status_rotativo;
	}

	public String getDescanso() {
		return descanso;
	}

	public void setDescanso(String descanso) {
		this.descanso = descanso;
	}

	public String getDobla() {
		return dobla;
	}

	public void setDobla(String dobla) {
		this.dobla = dobla;
	}

	public boolean isGafete() {
		return gafete;
	}

	public void setGafete(boolean gafete) {
		this.gafete = gafete;
	}

	public int getPrestamo() {
		return prestamo;
	}

	public void setPrestamo(int prestamo) {
		this.prestamo = prestamo;
	}

	public float getSalario_diario() {
		return salario_diario;
	}

	public void setSalario_diario(float salario_diario) {
		this.salario_diario = salario_diario;
	}

	public float getSalario_diario_integrado() {
		return salario_diario_integrado;
	}

	public void setSalario_diario_integrado(float salario_diario_integrado) {
		this.salario_diario_integrado = salario_diario_integrado;
	}

	public float getSueldo() {
		return sueldo;
	}

	public void setSueldo(float sueldo) {
		this.sueldo = sueldo;
	}

	public float getBonocomplemento() {
		return bonocomplemento;
	}

	public void setBonocomplemento(float bonocomplemento) {
		this.bonocomplemento = bonocomplemento;
	}

	public float getBono_asistencia() {
		return bono_asistencia;
	}

	public void setBono_asistencia(float bono_asistencia) {
		this.bono_asistencia = bono_asistencia;
	}

	public float getBono_puntualidad() {
		return bono_puntualidad;
	}

	public void setBono_puntualidad(float bono_puntualidad) {
		this.bono_puntualidad = bono_puntualidad;
	}

	public String getObjetivo_del_puesto() {
		return objetivo_del_puesto;
	}

	public void setObjetivo_del_puesto(String objetivo_del_puesto) {
		this.objetivo_del_puesto = objetivo_del_puesto;
	}

	public String getExperiencia() {
		return experiencia;
	}

	public void setExperiencia(String experiencia) {
		this.experiencia = experiencia;
	}

	public String getActividades_Principales() {
		return actividades_Principales;
	}

	public void setActividades_Principales(String actividades_Principales) {
		this.actividades_Principales = actividades_Principales;
	}

	public String getHabilidades() {
		return habilidades;
	}

	public void setHabilidades(String habilidades) {
		this.habilidades = habilidades;
	}

	public String getConocimiento() {
		return conocimiento;
	}

	public void setConocimiento(String conocimiento) {
		this.conocimiento = conocimiento;
	}

	public String getFecha_actualizacion() {
		return fecha_actualizacion;
	}

	public void setFecha_actualizacion(String fecha_actualizacion) {
		this.fecha_actualizacion = fecha_actualizacion;
	}

	public boolean guardar(String movimiento){ return new GuardarSQL().Guardar_Perfil_De_Puesto(this,movimiento); }
	
	public Obj_Perfil_De_Puestos buscar(int folio){ 
		try {
			return new BuscarSQL().Perfil_De_Puesto(folio);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	return null; 
	}
	
//	public boolean actualizar(int folio){ return new ActualizarSQL().Perfil_De_Puesto(this,folio); }
	
	public String buscar_nuevo() throws SQLException{ return new BuscarSQL().folio_Periodo_nuevo(); }
	
	public boolean existe_foto(int folio){
		try {
			return new BuscarSQL().isFoto(folio);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean nombre_perfil_disponible(String nombre){ return new BuscarSQL().nombre_de_perfil_disponible(nombre); }
	
	
	public String[] Combo_Empleado(){ 
		try {
			return new Cargar_Combo().Empleado("tb_empleado");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; 
	}
	public boolean insertar(int folio,String t_entrada){return new GuardarSQL().Insert_Empleado(folio,t_entrada);}
	
	public boolean insertar_checada(int folio,String t_entrada,int tipo_salida_comer){
		
		return new GuardarSQL().Insert_Checada(folio,t_entrada,tipo_salida_comer);
		
	}
	
	public int Generar_Archivos(int folio_empleado){ 
		try {
			return new BuscarSQL().Archivos_Empleado(folio_empleado);
		} catch (SQLException e) {
			return 0;
		}
	}
	
	public String[] Lista_De_Archivos_De_Empleados(int folio_empleado){ 
			try {
				return new BuscarSQL().Lista_Archivos_Empleado(folio_empleado);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
	}
	
//	TODO datos adicionales
	public String[] Combo_Tipo_Sangre(){ 
		try {
			return new Cargar_Combo().Tipo_De_Sangre("tb_tipo_de_sangre");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String[] Combo_Estado_Civil(){ 
		try {
			return new Cargar_Combo().Estado_Civil("tb_estado_civil");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String[] Combo_Escolaridad(){ 
		try {
			return new Cargar_Combo().Escolaridad("tb_escolaridad");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
