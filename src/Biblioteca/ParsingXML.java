package Biblioteca;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
//John Michel Rivera de León 
import java.sql.SQLException;

//import Obj_Xml.ParsingXML;

public class ParsingXML { 

 @SuppressWarnings("unused")
public ParsingXML(){
 	String cadenaXML = "<envioReturn><TransferenciaDTO><EMPLID>2936172</EMPLID><FIRST_NAME>John</FIRST_NAME><LAST_NAME>Rivera</LAST_NAME><SECOND_LAST_NAME>de León</SECOND_LAST_NAME><COMPANY>MetLife</COMPANY><DEPTID>239152</DEPTID><DEPTID_DESCR>ConsultoresTI</DEPTID_DESCR><LOCATION>MX</LOCATION><LOCATION_DESCR>Ciudad México</LOCATION_DESCR><JOBCODE>21</JOBCODE><JOBCODE_DESCR>LiderP</JOBCODE_DESCR><REPORTS_TO>2453211</REPORTS_TO><REPORTS_TO_EMPLID>2736122</REPORTS_TO_EMPLID><REPORTS_FIRST_NAME>Jose</REPORTS_FIRST_NAME><REPORTS_LAST_NAME>Ponce</REPORTS_LAST_NAME><REPORTS_SEC_NAME>De León</REPORTS_SEC_NAME><EMPL_STATUS>1</EMPL_STATUS><NATIONAL_ID>7</NATIONAL_ID><EMAIL_ADDR>lionheart815@hotmail.com</EMAIL_ADDR><MANAGER_LEVEL>5</MANAGER_LEVEL><MLM_EMPLID>2372412</MLM_EMPLID><MLM_REPORTS_TO>2834124</MLM_REPORTS_TO><AREA>11</AREA><MLM_REPORTS_TO_2>5273132</MLM_REPORTS_TO_2>2835123<MLM_REPORTS_TO_3>2513421</MLM_REPORTS_TO_3></TransferenciaDTO> </envioReturn>";
 	
 	try {
			String result = StoreData(cadenaXML,"491");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 } 
  
  
// public static String StoreData(oracle.sql.CLOB XMLSOAP, String Usuario) throws SQLException{
   public static String StoreData(String XMLSOAP, String Usuario) throws SQLException{
 //creamos la conexion hacia la base de datos de manera interna, en este caso especificamos
      Connection conn = DriverManager.getConnection("jdbc:default:connection:");
     CallableStatement  cstmt = conn.prepareCall("{CALL sicap_pck_ws.sicap_p_insert_users(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

  try { 
   
 int fin=((int) XMLSOAP.length()); 
 //se realiza el split para la respuesta XML/SOAP 
 
//String[] palabras = XMLSOAP.getSubString(1,fin ).split("<");
String[] palabras = XMLSOAP.substring(1,fin ).split("<");
 String[]TagsXML=new String[25];//para almacenar los tags clave  
 String[]Responses= new String[25];//para almacen de resultados 
  TagsXML[0]="EMPLID"; 
  TagsXML[1]="FIRST_NAME"; 
  TagsXML[2]="LAST_NAME"; 
  TagsXML[3]="SECOND_LAST_NAME"; 
  TagsXML[4]="COMPANY"; 
  TagsXML[5]="DEPTID"; 
  TagsXML[6]="DEPTID_DESCR"; 
  TagsXML[7]="LOCATION"; 
  TagsXML[8]="LOCATION_DESCR"; 
  TagsXML[9]="JOBCODE"; 
  TagsXML[10]="JOBCODE_DESCR"; 
  TagsXML[11]="REPORTS_TO"; 
  TagsXML[12]="REPORTS_TO_EMPLID"; 
  TagsXML[13]="REPORTS_FIRST_NAME"; 
  TagsXML[14]="REPORTS_LAST_NAME";  
  TagsXML[15]="REPORTS_SEC_NAME";  
  TagsXML[16]="EMPL_STATUS"; 
  TagsXML[17]="NATIONAL_ID"; 
  TagsXML[18]="EMAIL_ADDR"; 
  TagsXML[19]="MANAGER_LEVEL"; 
  TagsXML[20]="MLM_EMPLID"; 
  TagsXML[21]="MLM_REPORTS_TO"; 
  TagsXML[22]="AREA"; 
  TagsXML[23]="MLM_REPORTS_TO_2"; 
  TagsXML[24]="MLM_REPORTS_TO_3"; 

int localiza=0; 
int finals=0; 
String trimtag=""; 

for(int i=1;i<palabras.length;i++){ 
localiza= palabras[i].indexOf(">"); //1 buscamos el inicio 
finals= palabras[i].length(); //buscamos el final de la linea 
trimtag=palabras[i].substring(0, localiza);//seleccionamos primero valor del primer tag deacuerdo a su talla y checamos

//ya que obtenimos el 1er tag que siempre es el mismo pasamos linea por linea buscandolo
for(int k=0;k<TagsXML.length;k++){// se verifica el tag con los almacenados en el arreglo 
     if(TagsXML[k].matches(trimtag)){  // si con cuaerda con alguno entonces almacena el valor 
             if(palabras[i].substring(finals-1, finals).matches("n")){ 
                     Responses[k]=palabras[i].substring(localiza+1, finals-1); 
                                                                      }else{ 
                                                                             Responses[k]=palabras[i].substring(localiza+1, finals);
                                                                           } 
} 
if(trimtag.matches("/TransferenciaDTO")){//si se obtiene el tag de fin de clase se almacena el resultado 
k=TagsXML.length; 

for(int i2 = 0; i2<25; i2++){
	 System.out.println(Responses[i2]);
}

cstmt.setString(1, Responses[0]); 
cstmt.setString(2, Responses[1]); 
cstmt.setString(3, Responses[2]); 
cstmt.setString(4, Responses[3]); 
cstmt.setString(5, Responses[4]); 
cstmt.setString(6, Responses[5]); 
cstmt.setString(7, Responses[6]); 
cstmt.setString(8, Responses[7]); 
cstmt.setString(9, Responses[8]); 
cstmt.setString(10, Responses[9]); 
cstmt.setString(11, Responses[10]); 
cstmt.setString(12, Responses[11]); 
cstmt.setString(13, Responses[12]); 
cstmt.setString(14, Responses[13]); 
cstmt.setString(15, Responses[14]); 
cstmt.setString(16, Responses[15]); 
cstmt.setString(17, Responses[16]); 
cstmt.setString(18, Responses[17]); 
cstmt.setString(19, Responses[18]); 
cstmt.setString(20, Responses[19]); 
cstmt.setString(21, Responses[20]); 
cstmt.setString(22, Responses[21]); 
cstmt.setString(23, Responses[22]); 
cstmt.setString(24, Responses[23]); 
cstmt.setString(25, Responses[24]); 
cstmt.setString(26, Usuario); 
cstmt.executeUpdate(); 

} 
} 

}//fin for   

} catch (Exception e) {//si ocurre algun error lo cachamos 
return "Error ocurrido durante la transacción: n"+e; 
} 
//cerramos la conexion a la B.D 
cstmt.close(); 
//return "TERMINADO CORRECTAMENTE: n"+XMLSOAP.getSubString(1,32000 ); 
return "TERMINADO CORRECTAMENTE: n"+XMLSOAP.substring(1,32000 ); 
     } 
 
 public static void main(String [] arg){
 	new ParsingXML();
 }
} 