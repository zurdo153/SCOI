package Cat_Chat_Server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.StringTokenizer;
import content.Constant;
import content.Util;
/***
 * @Hilo  ThreadChecksPrivateMenssage.java
 * 
 * Hilo que verifica y distribuye los mensajes 
 * privados de los clientes
 * 
 **/
public class ThreadChecksPrivateMessage 
extends Thread {
	private Cat_Servicio_Chat_Server server = null;
	
	String msg            = null;
	
	private Thread         thread;
	
	private BufferedReader bufferedReader;
	/**
	 * Constructor.
	 * */
	public ThreadChecksPrivateMessage(
			Cat_Servicio_Chat_Server s,
			ThreadUserConnected threadUserConnected,
            BufferedReader reader,
            Socket connector) {
		
		this.server=s;		
		
		thread = threadUserConnected;
		
		this.bufferedReader =reader;
		
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(connector.getInputStream()));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			
			content.Util.showException(this.getClass().getName(), "Error",e.toString());
		}
	}
	@Override
	public synchronized void run() {
			String msg;
			try {
				msg = bufferedReader.readLine();
				
				final StringTokenizer  tokenizer = new StringTokenizer(msg,Constant._SEPARATOR);
				
				
				final String username = tokenizer.nextToken();        //de
				final String to       = tokenizer.nextToken().trim(); // para
				final String message = content.Util.removeNewLine(tokenizer.nextToken().getBytes());
				final String font     = tokenizer.nextToken();
				final int    style    = Integer.parseInt(tokenizer.nextToken());
				final int    color    = Integer.parseInt(tokenizer.nextToken());
				
				for(int index=0;
				            index<server.getSizeVectorUser();
				                 index++){
			        
			      	/**
			      	 *  recorremos el vector de usuarios si encontramos el nombre enviamos en mensaje privado
			      	 ***/
					if(server.getVectorOfConnectedUsers().get(index).getUsername().trim().equalsIgnoreCase(to)){
						
					server.getVectorOfConnectedUsers().get(index).sendPrivateMessage(username, Util.removeNewLine(message.getBytes()), font, style, color);
					}
				}
			} catch (IOException e) {
		    	System.err.println(e);
			} catch (Exception e) {
				
				content.Util.showException(this.getClass().getName(), "Error",e.toString());
				
				System.exit(0);
			}
			
			synchronized (thread) {
				
				
				thread.notify();
			   }
		}
}
