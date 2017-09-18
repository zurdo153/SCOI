package Cat_Chat;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JColorChooser;
import javax.swing.text.BadLocationException;
import content.Commands;
import content.Util;

/******************************************************************************
 * @ControllerListerner.java Listerner
 * 
 * Clase encargada encargada del ActionListener
 * de la Interfaz
 **/
public class ControllerListerner implements ActionListener {
	@SuppressWarnings("unused")
	private FontChooser        fontChooser;
	
	private  Cat_Chat           client = null;
	
	private  Connect           toConnect;
	
	private  ConnectorClient   socketClient;
	
	boolean verifyBold      = true;
	boolean verifyItalic    = true;
	boolean verifyEmoticons = true;
	/***
	 * Constructor
	 * 
	 * @param Interfaz
	 **/
	public ControllerListerner(Cat_Chat c) {
		client = c;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equalsIgnoreCase("Informacion")){
        	socketClient.client.sendMessage(socketClient, content.Commands._COMMAND_INFO+"*?"+client.getSelectedValueList());
        }
		if (e.getActionCommand().equalsIgnoreCase("[F]  Lista de usuarios")) {
			new ColorChooser(client, 1, 0);
		}
		if (e.getActionCommand().equalsIgnoreCase("[F]  Area de mensajes")) {
			new ColorChooser(client, 2, 0);
		}
		if (e.getActionCommand().equalsIgnoreCase("[F]  Area de texto")) {
			new ColorChooser(client, 3, 0);
		}
		if (e.getActionCommand().equalsIgnoreCase("[T]  Lista de usuarios")) {
			new ColorChooser(client, 0, 1);
		}
		if (e.getActionCommand().equalsIgnoreCase("[T]  Area de mensajes")) {
			new ColorChooser(client, 0, 2);
		}
		if (e.getActionCommand().equalsIgnoreCase("[T]  Area de texto")) {
			new ColorChooser(client, 0, 3);
		}
		if (e.getActionCommand().equalsIgnoreCase("Salir")) {
			System.exit(0);
		}
		
		if (e.getActionCommand().equalsIgnoreCase("Conectar")) {
			
			toConnect = new Connect(client, true);
			if (toConnect.init()) {
				try {
					Thread.sleep(400);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				socketClient = new ConnectorClient(client, toConnect
						.getServer(), toConnect.getPuert());
			} else {
				toConnect.exit();
			}
			
		}

		if (e.getActionCommand().equalsIgnoreCase("B")) {
			if (verifyBold) {
				client.setButtonBoldPressed(true);
				verifyBold = false;
			} else {
				client.setButtonBoldPressed(false);
				verifyBold = true;
			}
		}
		if (e.getActionCommand().equalsIgnoreCase("Cambiar Fuente")) {
			fontChooser = new FontChooser(client,true);
		}
		if (e.getActionCommand().equalsIgnoreCase("Desconectar")) {
			socketClient.client.sendMessage(socketClient, Commands._COMMAND_DISCONNECT);
			try {
				Thread.sleep(400);
				System.exit(0);
				
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if (e.getActionCommand().equalsIgnoreCase("limpiar.")) {
			try {
				client.clearAreaMessages();
			} catch (BadLocationException e1) {
				e1.printStackTrace();
				
			}
		}
		if (e.getActionCommand().equalsIgnoreCase("Enviar mensaje privado")) {
			if(client.getSelectedValueList()!= null){
				
			   sendPV(null);
			}
		}
		if (e.getActionCommand().equalsIgnoreCase("Color")) {
			Color newForeground = JColorChooser.showDialog(null,
					"Color de Fuente", Color.WHITE);
			if(newForeground == null){
				newForeground = Color.BLACK;
			}
			client.insertString(".");
			
			client.setForegroundAreaText(newForeground);
			
			client.setColor(newForeground);
		}
		if (e.getActionCommand().equalsIgnoreCase("Emoticones")) {
			if (verifyEmoticons) {
				client.setVisibleGlassPanelVert(true);
				verifyEmoticons = false;
			} else {
				client.setVisibleGlassPanelVert(false);
				verifyEmoticons = true;
			}
		}
		if (e.getActionCommand().equalsIgnoreCase("Enviar")) {
					
			//Si se selecciono una pesta�a
			if(client.isSelect()){
				//obtenemos la pesta�a seleccionada
			 String to = String.valueOf(client.getToPv()).trim();
			 			 
				//verificamos el hash table de pesta�as privadas y mostramos el mensaje en ese panel
				client.getHashtableTaps().get(to).sendPrivateMessage(socketClient.getUsername(),to,client.getTextArea(), client.getFontString(), client.getStyleFont(), client.getColor());
				if(client.getStyleFont() == "bold"){
		
				client.getHashtableTaps().get(to).showMessage(socketClient.getUsername()+": "+Util.removeNewLine(client.getTextArea().getBytes()), client.getFontString(), 1, client.getColor());}
				else
			    client.getHashtableTaps().get(to).showMessage(socketClient.getUsername()+": "+Util.removeNewLine(client.getTextArea().getBytes()), client.getFontString(), 0, client.getColor());
			}else{
				//si no hay una pesta�a seleccionada 
				//enviar el mensaje como principal
			sendMessage(Util.removeNewLine(client.getTextArea().getBytes()));
			
			client.clearAreaText();
			}
		}
	}
	
	public void sendPV(String user){
		
		client.addTap(socketClient.getUsername(), client.getSelectedValueList());
	}
	public void sendMessage(String menssage) {
		socketClient.client.sendMessage(socketClient, client.getTextArea());
	}
	public void sendPrivateMessage(String username, String to,
		String menssage, String font, int style, int color) {
		
		socketClient.sendPrivateMenssage(username, to, menssage, font, style,color);
	}
}
