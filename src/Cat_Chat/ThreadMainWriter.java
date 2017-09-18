package Cat_Chat;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import content.Constant;

/***********************
 * @Hilo ThreadWriter.java 
 * 
 * Hilo de escritura para enviar un mensaje
 **/
public class ThreadMainWriter
extends Thread {

	private Socket      connector;
	
	private PrintWriter writer;
	
	private Cat_Chat     client;
	

	
	String MESSAGE = null;
	String USERNAME = " ";
    
	   /**
	    *  Constructor
	    *   
	    *   @param Conector socket
	    *   @param mensaje
	    *   @param nombre de usuario
	    *   @param Interfaz
	    * **/
	public ThreadMainWriter(
			final Socket socket,
			final String message,
			String USER,
			Cat_Chat GUI) {
		
		super("Thread Writer");

		this.client = GUI;
		
		this.connector = socket;
		
		USERNAME = USER;
		
		MESSAGE  = message;

		try {
			writer = new PrintWriter(new OutputStreamWriter(connector
					.getOutputStream()));
			
		} catch (IOException e) {
			System.err.print(this.getName() + "" + e);
			System.exit(0);
		} catch (Exception e) {
			content.Util.showException(this.getName(), "Error",e.toString());
		}
		//Iniciar
		this.start();
	}
	@Override
	public void run() {
   	   /**
   	    *  Enviamos el mensaje
   	    ***/
		writer.println(USERNAME + Constant._SEPARATOR + "" + "" + content.Util.removeNewLine(MESSAGE.getBytes())
				+ Constant._SEPARATOR + "" + "" + client.getFontString()
				+ Constant._SEPARATOR + "" + client.getStyleFont()
				+ Constant._SEPARATOR + "" + client.getColor()
				+ Constant._SEPARATOR);
		try {
			Thread.sleep(40);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		writer.flush();
	}
}
