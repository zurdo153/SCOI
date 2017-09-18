package Cat_Chat_Server;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import content.Constant;
/*******************************
 * @Server.java 
 * 
 *  Servidor
 **/
public class Cat_Servicio_Chat_Server{
	
	public int user = 0;
	
	private ServerSocket 	           serverConnector;
	
	private Socket 			           connector;	
	
	private ThreadUserConnected        userConnected;
	
	private Vector<ThreadUserConnected> vectorOfConnectedUsers = new Vector<ThreadUserConnected>();
	
	public Cat_Servicio_Chat_Server(){
		
			try {
				System.out.println("________________________________________");
				System.out.println("\nIniciando servidor..");
				try {
					Thread.sleep(900);
				} catch (InterruptedException e){ System.err.println(e); }
				// Inicia el servidor
				serverConnector = new ServerSocket(content.Constant._SERVERPORT);
				System.out.println("Puerto: "+Constant._SERVERPORT+" OK");
				
				final InetAddress address = InetAddress.getByName("192.168.2.10");
				System.out.println("IP S: "+address);
				final String SO = System.getProperty("os.name");
				System.out.println("Sistema Operarivo: "+SO+" Version: "+System.getProperty("os.version"));
				System.out.println("________________________________________");
				System.out.println("\nServidor OK");
				
			}catch(IOException e1) { System.err.println(e1); System.exit(0); 
			
			}catch(Exception e) {content.Util.showException(this.getClass().getName(), "Error",e.toString()); System.exit(0); }
			
			while(true){
				
				try {
			    //espera a que un usuario conecte
			     connector = serverConnector.accept();
			     
			     userConnected = new ThreadUserConnected(this,connector,user);
			     System.out.println("> Nuevo usuario conectado IP: "+connector.getInetAddress()+" L: "+connector.getLocalPort());
			    
				  //Agregamos el usuario conectado al vector
			     vectorOfConnectedUsers.addElement(userConnected);
			     try {
						Thread.sleep(200);
					} catch (InterruptedException e) {System.err.println(e);System.exit(0);
					
					} catch (Exception e) {
						content.Util.showException(this.getClass().getName(), "Error",e.toString());
						System.exit(0);
					}
					
				 //Enviamos un mensaje al usuario que ya esta conectado
			     vectorOfConnectedUsers.get(user).sendMessage(content.Constant._SERVERNAME,"Estas Conectado! ",Constant._SERVERFONT,Constant._SERVERSTYLE,Constant._SERVERCOLOR);
			     //Incrementa el numero de usuarios++
			     user++;
			} catch (IOException e) {System.err.println(e);
				System.exit(0);
			} catch (Exception e) {
				
				content.Util.showException(this.getClass().getName(), "Error",e.toString());
			}
		}
	}
	
    public Vector<ThreadUserConnected> getVectorOfConnectedUsers() {
    	
		return vectorOfConnectedUsers;
	}
	public void setVectorOfConnectedUsers(
			
		Vector<ThreadUserConnected> vectorOfConnectedUsers) {
		
		this.vectorOfConnectedUsers = vectorOfConnectedUsers;
	}
	public int getUsers() {
		return user;
	}
	public void setUsers(int user) {
		this.user = user;
	}
	// @Metodo que envia de lista de usuarios conectados
	public void sendList(){
		sendMessageAll(content.Constant._SERVERNAME, content.Commands._COMMAND_LISTUSER, content.Constant._SERVERFONT, Constant._SERVERSTYLE,Constant._SERVERCOLOR);
	
		sendMessageAll(content.Constant._SERVERNAME,getListUsers(),content.Constant._SERVERFONT,Constant._SERVERSTYLE,Constant._SERVERCOLOR);
	}
	// Obtener el cuantos usuarios estan conectados
	public int getSizeVectorUser(){
		
		return vectorOfConnectedUsers.size();
	}
	public String sendListUsersConnect(int index){
		
		return vectorOfConnectedUsers.get(index).getUsername();
	}
	
   //@Metodo que obtiene la lista de usuarios
	public String getListUsers(){
		
		String list = " ";
		
		try{
		
		for(int index =0;index<vectorOfConnectedUsers.size();index++)
			list += vectorOfConnectedUsers.get(index).getUsername().trim()+Constant._SEPARATOR_LIST;
		
		}catch(Exception ex){ System.out.println("[List]La excepcion es: "+ex); }
				
	return list;
	}
     // @Metodo de remover usuario
	public void removeUser(ThreadUserConnected userConnect){
		
		vectorOfConnectedUsers.removeElement(userConnect);
	}
      //@Metodo de envio de mensaje para todos
	public void sendMessageAll(String username,String message,String font, String style,String color){
		
		for( int index=0;
		            index<vectorOfConnectedUsers.size();
		                 index++ ){
			
			vectorOfConnectedUsers.get(index).sendMessage(username,message,font,style,color);
		}
	}
	// @Main
	public static void main(String[] args) {
		new Cat_Servicio_Chat_Server();
	}	
}
