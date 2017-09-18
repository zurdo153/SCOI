package Cat_Chat_Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;

import content.Commands;
import content.Constant;
import content.ThreadWriterPrivateMessage;
import content.Util;
/*******************************
 * @Hilo UserConnect.java 
 * 
 * Hilo Usuario
 **/
public class ThreadUserConnected
extends Thread{
	
	private Cat_Servicio_Chat_Server 	       server;
	
	private Socket         connector;
	
	private BufferedReader reader;
	
	private PrintWriter    writer;
	
	private boolean        onLine   = false;
	
	public String          USERNAME	= null;
	
	public int             ID       = 0;
	
	public static final String msgDisconnected ="ha abandonado el chat.";
	
	private String by =" ";
	
	private ThreadWriterUserList     threadWriterUserList;
	
	private String address;
	private String host;
	
	/**
	 * Constructor.
	 * 
	 * @param Servidor
	 * @param Conector socket
	 * @param ID
	 * */
	public ThreadUserConnected(
			Cat_Servicio_Chat_Server serv,
			Socket socket,
			int i){
		
		this.server    = serv;
		
		this.connector = socket;
		
		this.setPriority(Thread.NORM_PRIORITY);
		try{
			/**
			 * IO
			 * */
			writer  = new PrintWriter(new OutputStreamWriter(connector.getOutputStream()));
			
			reader  = new BufferedReader(new InputStreamReader(connector.getInputStream()));
			
			//Conectado 
			onLine = true;
			
		    threadWriterUserList=new ThreadWriterUserList(server);
			
		}catch (IOException e){	System.err.print(this.getName()+" "+e);System.exit(0);  
		
		} catch (Exception e){ System.err.print(this.getName()+" Excepcion: "+e);System.exit(0); }
		 
		ID		 = i;
		//Inicia
		this.start();
	}
	//@Metodo Desconectar
	private void disconnect(){
		onLine = false;
		
		System.err.println(Util.getTime()+": "+this.getName()+": "+msgDisconnected+by);
		
		server.sendMessageAll("||","Usuario "+this.getName()+" "+msgDisconnected+by+" :",Constant._SERVERFONT,Constant._SERVERSTYLE,"-65536");
	  
		threadWriterUserList.setActive(false);
	
			final int nro=server.getUsers();
		  
			server.setUsers((nro-1));
			
			server.removeUser(this)	;
	}
	//@Metodo que verifica el nombre
	private boolean availableName(String username){
		
		boolean found = false;
		int     nro   = 0;
		
		final int size = server.getSizeVectorUser();
		
		for(int index =0;index<size;index++){
			
		   if(server.getVectorOfConnectedUsers().get(index).getUsername().equalsIgnoreCase(username)){
			   nro++;
			   if(nro == 2)
				   
				   found = true; 
			   }
		   }
		return found;
	} 
     //@Metodo de envio de mensaje para este usuario
	public  void sendMessage(String username,String message,String font, String style,String color){
		
		writer.println(username+Constant._SEPARATOR+""+Util.removeNewLine(message.getBytes())+Constant._SEPARATOR+
		""+font+Constant._SEPARATOR+""+style+Constant._SEPARATOR+""+color+Constant._SEPARATOR);
	
		writer.flush();
	}
	public void setAddress(String ip){
		
		address =ip;
	}
	public String getAddress(){
		
		return address;
	}
	//@Metodo de envio mensaje privado
	public void sendPrivateMessage(String username,String message,String font,int style,int color){
		
		sendMessage(Constant._SERVERNAME,Commands._COMMAND_PRIVATE,Constant._SERVERFONT,Constant._SERVERSTYLE,Constant._SERVERCOLOR);
		
		new ThreadWriterPrivateMessage(connector,username.trim(),USERNAME.trim(),message,font,style,color);
	}
	public String getUsername() {return USERNAME;}
	
	public void setUSERNAME(String username) {USERNAME = username;}

	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	@Override
	public  synchronized void run(){
		
		String messageReceived = null;
		
		boolean sendCheck      = false;
		
		while(onLine){
			try {
				//Recibir el mensaje
				messageReceived = reader.readLine();
	
				//Seperamos el mensaje recibido
				final StringTokenizer stringTokenizer = new StringTokenizer(messageReceived,Constant._SEPARATOR);
				
				String username  = stringTokenizer.nextToken();
			
				USERNAME = username;this.setName(username);
				
				String message   = stringTokenizer.nextToken();
				String font      = stringTokenizer.nextToken();
				String style     = stringTokenizer.nextToken();
				String color     = stringTokenizer.nextToken();
				
				//Verifica que el nombre no esta siendo usado
				if(availableName(username)){
				by = "-->Nombre no disponible("+username+")";
					sendMessage(
							Constant._SERVERNAME, 
							
							by,
							Constant._SERVERFONT, 
							
							Constant._SERVERSTYLE,
							
							"-65536");
				sendCheck = true;
				
				message   = Commands._COMMAND_DISCONNECT;
				
				
				}else			
				//Verifica si se envio la lista
				if(!sendCheck){
				//inicia el hilo que envia la lista una sola vez para cada usuario
					
				threadWriterUserList.start();
				
				sendCheck = true;	
				
				}
				//Si se envio la lista proceder
				if(sendCheck){
					if(message.indexOf(content.Commands._COMMAND_INFO)!= -1){
	
						StringTokenizer tokenizer = new StringTokenizer(message,"*?");
				         tokenizer.nextToken();
				         String t=tokenizer.nextToken().trim();

	                    		 sendMessage(username,content.Commands._COMMAND_INFO+"*?"+getAddress()+"*?"+getHost()+"*?"+t, font, style, color);
	                    	
	                     
						continue;
					}
				if(message.indexOf(content.Commands._COMMAND_WRITERINFO)!= -1){
				StringTokenizer tokenizer = new StringTokenizer(message,"*?");
                     tokenizer.nextToken();address =tokenizer.nextToken();
                    host =  tokenizer.nextToken();    
				continue;
				}
				
				if(message.indexOf(content.Commands._COMMAND_DISCONNECT) != -1){
					
					if(availableName(username)){
						
						onLine = false;
						
					    final int nro=server.getUsers();
					    
					    threadWriterUserList.setActive(false);
					    
					    server.setUsers((nro-1));
					    
						server.removeUser(this)	;
					}else
						
					disconnect();
				}
				if(message.indexOf(Commands._COMMAND_PRIVATE) != -1){
					ThreadChecksPrivateMessage threadCheckPrivateMessage=
						new ThreadChecksPrivateMessage(server,this,reader,connector);
							threadCheckPrivateMessage.start();
					wait();
					
					continue;
			     	}
				 //Envia el mensaje recibido a todos los usuarios
				if(
						message!= content.Commands._COMMAND_PRIVATE &&
						message != null &&
						message != Commands._COMMAND_DISCONNECT)
					
				server.sendMessageAll(username,message,font,style,color);
				
				}
			} catch (IOException e) {
				
				disconnect();
				
			} catch (Exception e) {
				
				content.Util.showException(this.getClass().getName(), "Error",e.toString());
				
				disconnect();
			}
		}
	 }
}
