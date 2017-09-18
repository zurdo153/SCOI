package Cat_Chat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.StringTokenizer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

/******************************************************************************
 * @PanelPrivateMenssage.java
 * 
 *  Panel que contiene el area a recibir los
 *  mensajes privvados
 **/
public class PanelPrivateMessage extends JPanel {
	private static final long serialVersionUID = 1L;
	private JScrollPane scrollPaneMain;
	
	private JTextPane      textPane;
	
	private static Cat_Chat client;
	
	private ControllerListerner controllerListernerMain;
	
	private JTextPane           textPaneMainArea;
	
	private JButton             buttonClose;
	
	private StyleContext        context;
	  
	private StyledDocument      styledDocument;
	
	@SuppressWarnings("unused")
	private String username = null;
	
	private String to       = null;
	@SuppressWarnings("unused")
	private Style labelStyle ;

	public void sendPrivateMessage(String username, String to,
			String menssage, String font, String style, int color) {

		int st = 0;
		if (style.equals("bold"))
			st = 1;

		controllerListernerMain.sendPrivateMessage(username, to, menssage,
				font, st, color);
	
	 }
    
	/**
     *  Constructor 
     *  
     *  @param Username:Nombre de usuario
     *  @param to: para
     *  @param Interfaz
     *  @param controlador listerner
	***/
	public PanelPrivateMessage(
			final String username,
			final String to,
			Cat_Chat c,
			ControllerListerner controllerListerner) {
		
		this.username = username;
		this.to = to;
		client = c;
		this.setLayout(new BorderLayout());
		
		this.controllerListernerMain = controllerListerner;

		context = new StyleContext();
		styledDocument = new DefaultStyledDocument(context);
		textPaneMainArea = new JTextPane(styledDocument);

		textPaneMainArea.setEditable(false);
		scrollPaneMain = new JScrollPane(textPaneMainArea);
		scrollPaneMain
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		buttonClose = new JButton("");
		buttonClose.setIcon(new ImageIcon(getClass().getResource(
				"/content/Icons/iconClose.png")));

		final JPanel panel = new JPanel(new GridLayout(1, 1));
		panel.add(scrollPaneMain);
		add(panel, BorderLayout.CENTER);
		final JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		buttonClose.setPreferredSize(new Dimension(160, 27));

		panel2.add(buttonClose);
		buttonClose.setFocusable(false);
		
		
		buttonClose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				listerner(e);
			}
		});
		add(panel2, BorderLayout.SOUTH);
	}

	public void listerner(ActionEvent event) {
		client.removeToHashTable(to);
		
		client.removeTap(this);
		// client.setId((id-1));
	}
	public String getTextArea() {
		return textPane.getText();
	}

	public void clearArea() {
		textPaneMainArea.setText("");

	}
	public void insertEmoticon(final ImageIcon ic, final String atajo){
	    Style labelStyle = context.getStyle(StyleContext.DEFAULT_STYLE);
	    
	    Icon icon = ic;
	    
	    JLabel label = new JLabel(icon);
	    
	    StyleConstants.setComponent(labelStyle, label);
	    
	    try {
	      styledDocument.insertString(styledDocument.getLength(), atajo, labelStyle);
	      
	    } catch (BadLocationException badLocationException) {
	      System.err.println("Oops");
	    }		
    }
	// @Metodo para mostrar mensajes
	public void showMessage(String text, Object styleConstants, int b,
			int color) {
		
		client.clearAreaText();
		final String newline = "\n";
		
		final StringTokenizer tokenizer = new StringTokenizer(text, ":");
		final String username = tokenizer.nextToken();
		final String message = tokenizer.nextToken();

		LexicoAnalyzer anLex = new LexicoAnalyzer(message);
		SimpleAttributeSet attributes = new SimpleAttributeSet();
		SimpleAttributeSet attributesU = new SimpleAttributeSet();

		SimpleAttributeSet attributeSetB = new SimpleAttributeSet();

		StyleConstants.setFontFamily(attributesU, "tahoma");
		StyleConstants.setFontFamily(attributes, styleConstants.toString());
		attributes.addAttribute(StyleConstants.CharacterConstants.Foreground,
				
				new Color(color));
		if (b == 1) {
			attributeSetB = new SimpleAttributeSet();

			attributes.addAttribute(StyleConstants.CharacterConstants.Bold,
					Boolean.TRUE);

		}
		if (b == 0) {
			attributeSetB = new SimpleAttributeSet();

			attributeSetB.addAttribute("plain", Boolean.TRUE);
		}
		try {
			styledDocument.insertString(styledDocument.getLength(), username
					+ ":", attributesU);

			String    token;
			String    ruta;
			ImageIcon img;
						
			while( anLex.hasToken() ){
				
				token = anLex.nextToken();
								
				if( Emoticons.esEmoticon(token) ){
					
					ruta = Emoticons.emoticonos.get(token);
					
					img  = new ImageIcon(getClass().getResource(ruta));			
					if(!client.isBlockEmotes())
					insertEmoticon(img, token);
				}
				else
			styledDocument.insertString(styledDocument.getLength(), token,
					attributes);
			}
			styledDocument.insertString(styledDocument.getLength(), ""
					+ newline, attributeSetB);
			
		} catch (BadLocationException ble) {
			System.err.println("Couldn't insert" + "text.");
		} catch (Exception e) {
			content.Util.showException(this.getName(), "Error", e.toString());
		}
	}
}
