package Cat_Chat;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.KeyStroke;


public class ControllerKeyListerner implements KeyListener {

	private Cat_Chat client = null;
	@SuppressWarnings("unused")
	private ControllerListerner controllerListerner;

	public ControllerKeyListerner(Cat_Chat c,ControllerListerner listerner) {
		this.client = c;
		
		if (listerner instanceof ControllerListerner)
			this.controllerListerner = listerner;
	}
	@Override
	public void keyPressed(KeyEvent e) {
		KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false);
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
	}
	@Override
	public void keyTyped(KeyEvent e) {
		
		if (e.getKeyChar() == '\n'){
			
			client.getJBEnviar().doClick();
		}
	}
}