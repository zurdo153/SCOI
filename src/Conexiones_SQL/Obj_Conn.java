package Conexiones_SQL;

//String ip,String base_de_datos,String usuario,String pass
public class Obj_Conn {
	Object[][] connections = {{"CEDIS"			,"192.168.2.98"	,"BMSIZAGAR","sa","Ragazi/*-1"},
							  {"SUPER II"		,"192.168.2.100","BMSIZAGAR","sa","Ragazi/*-1"},
							  {"ADMINISTRACION"	,"192.168.3.199","BMSIZAGAR","sa","Ragazi/*-1"},
							  {"REFACCIONARIA"	,"192.168.3.200","BMSIZAGAR","sa","Ragazi/*-1"},
							  {"CENTRO"			,"192.168.7.200","BMSIZAGAR","sa","Ragazi/*-1"},
							  {"PLAZA"			,"192.168.9.201","BMSIZAGAR","sa","Ragazi/*-1"}};
	String dir="";
	String db="";
	String user="";
	String pass="";
	 
	public void llenarConn(String estab){
		
		dir = new BuscarSQL().Server(estab);
		
		for(int i = 0; i<connections.length; i++){
			if(dir.equals(connections[i][1].toString().trim())){
				db=connections[i][2].toString().trim();		
				user=connections[i][3].toString().trim();		
				pass=connections[i][4].toString().trim();
				break;
			}
		}
	}
	
	public String getDir() {
		return dir;
	}
	public String getDb() {
		return db;
	}
	public String getUser() {
		return user;
	}
	public String getPass() {
		return pass;
	}
}
