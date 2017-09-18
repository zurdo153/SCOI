package content;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ThreadWriterPrivateMessage 
extends Thread {

	static String  username = null;
	static String  to       = null;
	static String  message  = null;
	static String  font     = null;
	static int     style    = 0;
	static int     color    = 0;
	private Socket       connector = null;
	private PrintWriter writer     = null;

	public ThreadWriterPrivateMessage(
	                    Socket socket, 
	                    String username,
	                    String to,
	                    String menssage, 
	                    String font,
			            int style,
			            int color
			     ) {
		super("ThreadWriterPrivateMenssage");
		this.connector = socket;
		try {
			writer = new PrintWriter(
					connector.getOutputStream());
			
		} catch (IOException e) {
			System.err.println(this.getName() + ": IO " + e);
			System.exit(0);
		} catch (Exception e) {
			System.err.println(this.getName() + ": Exepcion " + e);
			System.exit(0);
		}
		ThreadWriterPrivateMessage.username = username;
		
		ThreadWriterPrivateMessage.to       = to;
		
		ThreadWriterPrivateMessage.message  = menssage;
		
		ThreadWriterPrivateMessage.font     = font;
		
		ThreadWriterPrivateMessage.style    = style;
		
		ThreadWriterPrivateMessage.color    = color;
				
		start();
	}

	@Override
	public void run() {
				
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			@SuppressWarnings("unused")
			InetAddress address = InetAddress.getByName("localhost");
	
		writer.println(username + content.Constant._SEPARATOR + "" + to
				+ content.Constant._SEPARATOR + "" + Util.removeNewLine(message.getBytes())
				+ content.Constant._SEPARATOR + "" + font
				+ content.Constant._SEPARATOR + "" + style
				+ content.Constant._SEPARATOR + "" + color
				);
		writer.flush();
		
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			
			content.Util.showException(this.getName(), "Error",e.toString());
		}
	}
}
