package Obj_Compras;


public class Obj_Compra_De_Cascos {
	private int folio_compra;
	private String beneficiario;
	private Double total;
	
	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public int getFolio_compra() {
		return folio_compra;
	}

	public void setFolio_compra(int folio_compra) {
		this.folio_compra = folio_compra;
	}

	public String getBeneficiario() {
		return beneficiario;
	}

	public void setBeneficiario(String beneficiario) {
		this.beneficiario = beneficiario;
	}

	
	public boolean guardar(Object[][] tabla){
		return new Conexiones_SQL.GuardarSQL().Guardar_Compra_De_Cascos(tabla,this);
	}
	
	
	
}
