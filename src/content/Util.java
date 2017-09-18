package content;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
//import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
//import Chat.GUIChat;
//import Chat.PanelPrivateMenssage;
public class Util {
	public Util(){}
	 public static String removeNewLine(byte[] byteString){ 
		final char newline = '\n';
		final char newCharacter=' ';
		int size = 0;
		String newString="";
			byte[]bytes=byteString;
			size = bytes.length;
				for(int index=0;index<bytes.length; index++){
					char character = (char)bytes[index];
					if(character == newline){
					bytes[index]= newCharacter;
					    if(bytes[--index] == '\r')
					    bytes[index]=newCharacter;
						}
				}
				for(int index=0;index<size;index++){
				newString+=String.valueOf((char)bytes[index]);
				}
		return newString;
	}
	public static String getTime(){
			final Date date=new Date();
			final SimpleDateFormat formatDate=new SimpleDateFormat ("hh:mm",Locale.getDefault());
			return "["+formatDate.format(date)+"]";
		}
	public static  boolean SO(){
		boolean tr = false;
		final String so =System.getProperty("os.name");
			if(so.indexOf("win") != -1){
				tr = true;
			}
	return tr;		
	}
	public static void showException(String title,String menssage,String exception){
		JOptionPane.showMessageDialog(new JFrame(), menssage+":"+exception, title, JOptionPane.ERROR_MESSAGE);
	}
}
