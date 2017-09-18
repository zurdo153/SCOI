package Cat_Chat;

import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import javax.swing.text.StyledDocument;
/**
 * @ControlleMainDocumentListener.java
 * 
 * Clase encargada de seleccionar el texto
 * final del mensaje ingresado en el area 
 * principal
 * */
public class ControlleMainDocumentListener implements DocumentListener {
	
	protected JTextPane      pane;
	
	protected Cat_Chat       chat;
	
	protected Document       abstractDocument;
	
	protected PanelEmoticons emoticons;
	/*** 
	 * Constructor
	 **/
	public ControlleMainDocumentListener(Cat_Chat ch,
			JTextPane textPane,
			StyledDocument doc, 
			PanelEmoticons panelEmoticons){
	pane = textPane;
	    chat = ch;   
	      abstractDocument = doc;
	         emoticons = panelEmoticons;
	}
	@Override
	public void changedUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub	
	}
	@Override
	public void insertUpdate(DocumentEvent e) {
		
	pane.setSelectionEnd(pane.getText().length());
	chat.requestFocusText();
	}
	@Override
	public void removeUpdate(DocumentEvent e) {
		// TODO Auto-generated method stub
		
	}
}
