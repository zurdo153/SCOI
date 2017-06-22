package Biblioteca;
import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;

public class validaFecha {

	public validaFecha() {
		
		System.out.println(validar("28/02/2017"));

	}
	
	public String validar(String fecha){
		String aviso = "";
		
		int dayOfMonth 		=0;
		int month 		=0;
		int year 	=0;
		
		int contarChar = 0 ;
		char[] arrayChar = fecha.toCharArray();
 
		for(int i=0; i<arrayChar.length; i++){
			if( arrayChar[i] == '/')
				contarChar++;
		}
		
		if(contarChar==2){
			
					if(validaNumero(fecha.substring(0,fecha.indexOf("/")))){
						dayOfMonth = Integer.valueOf(fecha.substring(0,fecha.indexOf("/")));
						fecha = fecha.substring(fecha.indexOf("/")+1,fecha.length());
					}
					if(validaNumero(fecha.substring(0,fecha.indexOf("/")))){
						month 		= Integer.valueOf(fecha.substring(0,fecha.indexOf("/")));
						fecha = fecha.substring(fecha.indexOf("/")+1,fecha.length());
					}
					if(validaNumero(fecha)){
						year 	= Integer.valueOf(fecha);
					}
					if (year < 1900) {
						aviso = "Año inválido.";
			        } else{
				        try {
			        		LocalDate.of(year, month, dayOfMonth);
//					        LocalDate today = LocalDate.of(year, month, dayOfMonth);
//					        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); //formato de fecha
//					        System.out.println(formatter.format(today)); // 01/01/2016
			        		aviso = "Fecha Valida";
						} catch (Exception e) {
							aviso = "Fecha Invalida";
						}
			        }
		}else{
			aviso = "Formato incorrecto";
		}
		return aviso;
	}
	
	public boolean validaNumero(String dia){
		boolean validar = false;
		try {
			Integer.valueOf(dia);
			validar = true;
			
		} catch (Exception e) {
			validar = false;
		}
		return validar;
	}

	
    public static void main(String[] args) {
    	new validaFecha();
    }
}
