package Cat_Chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.StringTokenizer;

import content.Util;
/**
 * @Hilo ThreadReaderPrivateMenssage.java
 * 
 * Hilo encargado de leer los mensajes privados resibidos
 * */
public class ThreadReaderPrivateMessage 
extends Thread {
	private Cat_Chat chat;
	
	private Thread threadReader;
	
	private BufferedReader bufferedReader = null;;
	
	private Socket connector = null;
	
	private boolean fount;	

	   /**
	    *  Constructor
	    *  
	    *  @param Interfaz
	    *  @param Hilo de lectura principal
	    *  @param Buffer de lectura
	    *  @param Conector socket
	    *  
	    ***/
	
	public ThreadReaderPrivateMessage(Cat_Chat c, ThreadMainReader thread,
			BufferedReader reader, Socket socket) {
		
		super("ThreadReaderPrivateMenssage");
		
		this.chat           = c;
		
		this.threadReader   = thread;
		
		this.bufferedReader = reader;
		
		this.connector      = socket;
		try {
			reader = new BufferedReader(new InputStreamReader(connector
					.getInputStream()));
		} catch (IOException e) {
			System.err.println(this.getName() + ":" + e);
		}
	}

	@Override
	public synchronized void run() {
		
		try {
			String privateMenssage = bufferedReader.readLine();
			
			final StringTokenizer tokenizer = new StringTokenizer(
					privateMenssage, content.Constant._SEPARATOR);
			
			final String username = tokenizer.nextToken();
			final String to = tokenizer.nextToken();
			final String message = tokenizer.nextToken();
			final String font = tokenizer.nextToken();
			final int style = Integer.parseInt(tokenizer.nextToken());
			final int color = Integer.parseInt(tokenizer.nextToken());
			fount = true;
	        
	      	   /**
	      	    *  Verificamos que no estan bloqueados los mensajes privados
	      	    * **/
			if(!chat.isBlockPrivateMessage()){
				
			   if (!chat.checkTap(username)) {
				   
				   if (fount) {
				        
			      	   /**
			      	    *  Agregamos una nueva pestaña 
			      	    ***/
					chat.addTap(to, username);
					
					chat.getHashtableTaps().get(username).showMessage(
							
							username + ": "
									+ Util.removeNewLine(message.getBytes()),
							font, style, color);

					fount = false;
				}
				//
			} else{
			
				chat.getHashtableTaps().get(username).showMessage(
						username + ": "
								+ Util.removeNewLine(message.getBytes()),
						font, style, color);
			}
		  }
		} catch (IOException e) {
			System.err.println(this.getName() + ":" + e);
		} catch (Exception e) {
			content.Util.showException(this.getName(), "Error", e.toString());
		}
		
		synchronized (threadReader) {
			
			threadReader.notify();
			
		}
	}
}
