package Cat_Chat;

import java.util.StringTokenizer;
import content.Constant;

/***************************************
 * @Hilo ThreadCheckUserList.java:
 * 
 * Hilo que se  encarga de resibir la Lista de usuarios
 **/
public class ThreadCheckUserList 
extends Thread {
	private Cat_Chat    client;
	private Thread     threadReader;
	
	/**
	 * Constructor. 
	 * 
	 * @param La interfaz
	 * @param Hilo de lectura
	 * @param Lista de usuarios
	 * */
	public ThreadCheckUserList(
			Cat_Chat GUI, 
			ThreadMainReader reader,
			String listUser) {
		
		super("Thread CheckUserList");
		
        this.setPriority(Thread.NORM_PRIORITY);
        
		this.client = GUI;

		/**
		 * Separamos la lista de usuarios resibida
		 * */
		final StringTokenizer tokenizer = new StringTokenizer(listUser,Constant._SEPARATOR_LIST);
		
		/**
		 * Establecemos el numero de usuarios conectados y actualizamos
		 * */
		client.setNroUsers(tokenizer.countTokens());
		client.updateLabelNroUsers();
		/**
		 * Recorremos los nombre y los separamos
		 * */
		while(tokenizer.hasMoreElements()){
			client.addUser(tokenizer.nextToken());
		}
		/**
		 * Obtenemos el focus
		 * */
		client.UDUserlist();
		try {
		
			threadReader = reader;
			
		} catch (Exception e) {
			
			content.Util.showException(this.getName(), "Error",e.toString());
		}
	}
	//@synchronized Run
	@Override
	public synchronized void run() {
		
		synchronized (threadReader) {
			try {
				/**
				 * Notificamos que se ha completado la tarea
				 * */
				threadReader.notify();
				
			} catch (Exception e) {
				System.err.println(this.getName() + ":Exepcion:" + e);
				System.exit(0);
			}
		}
	}
}