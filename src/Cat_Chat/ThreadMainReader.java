package Cat_Chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

import content.Commands;
import content.Constant;
/********************
 * @Hilo ThreadReader.java 
 * 
 * Hilo de lectura principal que resibe
 * y divide los mensajes
 **/
public class ThreadMainReader
extends Thread {
	private BufferedReader reader;
	private Socket         connector;
	private Cat_Chat        client;
	/**
	 * Constructor
	 * 
	 * @param Conector
	 * @param Interfaz
	 * */
	public ThreadMainReader(
			Socket socket, 
			Cat_Chat GUI) {

		super("Thread Reader");
		this.connector = socket;
		this.client = GUI;
		try {
			/***************************************
			 * Iniciar el buffer de entrada
			 **/
			reader = new BufferedReader(new InputStreamReader(connector
					.getInputStream()));
			this.start();
			
		} catch (IOException e) {
			System.err.println(e);
			System.err.println(this.getName() + ": ERROR: " + e);
			System.exit(0);
		} catch (Exception e) {
			content.Util.showException(this.getName(), "Error",e.toString());
			System.exit(0);
		}
	}

	//@synchronized Run
	@Override
	public synchronized void run() {
		while (true) {
			try {
			Thread.sleep(100);
				/**
				 * Resibimos el mensaje
				 * */
				String messageReceived = reader.readLine();

				/**
				 * Separamos el mensaje resibido
				 * */
				final StringTokenizer stringTokenizer = new StringTokenizer(messageReceived, Constant._SEPARATOR);
				
				String username = stringTokenizer.nextToken();
				String message  = stringTokenizer.nextToken();
				String font     = stringTokenizer.nextToken();
				String style    = stringTokenizer.nextToken();
				String color    = stringTokenizer.nextToken();
				/******************************************************************************/
				/**
				 * Comparamos el mensaje con el comando para la lista de usuarios
				 * 
				 * */
				if(message.indexOf(Commands._COMMAND_INFO)!= -1){
					final StringTokenizer tokenizer = new StringTokenizer(message,"*?");
						tokenizer.nextToken();
						String ip = tokenizer.nextToken();
						String host =	tokenizer.nextToken();
						String user = 	tokenizer.nextToken();
						JOptionPane.showMessageDialog(client, "[ Informacion ] \n\n IP: "+ip+" \n\n Sistema operativo: "+host+" \n","|: "+user, JOptionPane.INFORMATION_MESSAGE);
					
					continue;
				}
				if (message.indexOf(Commands._COMMAND_LISTUSER) != -1) {

					final String msg = reader.readLine();
					
					final StringTokenizer tokenizer = new StringTokenizer(msg,
							Constant._SEPARATOR);

					tokenizer.nextToken();
					final String list = tokenizer.nextToken();
					
					 client.removeAllUsers();
					     /**
					      * 
						 * Iniciamos el Hilo que resibe la lista de usuarios
						 * 
						 * */
					ThreadCheckUserList usersList = new ThreadCheckUserList(client, this, list);
					usersList.start();

					try {
						/** Esperamos que finalize**/
						wait();
						
					} catch (InterruptedException e) {
						System.err.println(this.getName() + ": ERROR: " + e);
						System.exit(0);
					} catch (Exception e) {
						content.Util.showException(this.getClass().getName(), "Error",e.toString());
					}
					//Continuar
					continue;
				}
				/** 
				 * Comparamos el mensaje con el comando para mensajes privados
				 * 
				 ***/
	      	if (message.indexOf(Commands._COMMAND_PRIVATE) != -1) {
	      		/** 
	      		 * Iniciamos el Hilo que resibe los mensajes privados
	      		 * **/
					ThreadReaderPrivateMessage readerPrivateMessage =
						new ThreadReaderPrivateMessage(client,this,reader,connector);
					readerPrivateMessage.start();
					
                wait();
                
                continue;
				}
		        
	      	   /**
	      	    *  Mostramos el mensaje
	      	    * **/
	      	   if (style.equals("bold")){
						  if(message != Commands._COMMAND_PRIVATE ){
							  
						client.showMessage(username + ": " + message, font,
								1, Integer.parseInt(color));
						}
					}
					if (style.equals("plain")){
						
						if(message != Commands._COMMAND_PRIVATE){
							
						client.showMessage(username + ": " + message, font,
								0, Integer.parseInt(color));
						}
		         	}
				
					
				/******************************************************************************/
			} catch (IOException e) {
				
				System.err.println(this.getName() + ": ERROR IO: " + e);
				new ThreadMainWriter(connector, Commands._COMMAND_DISCONNECT,
						null, client);
				try {
					Thread.sleep(600);
					System.exit(0);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} catch (Exception e) {
				
				content.Util.showException(this.getName(), "Error",e.toString());
			}
		}
	}
}
