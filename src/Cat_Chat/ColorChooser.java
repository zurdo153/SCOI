package Cat_Chat;

import java.awt.Color;
import javax.swing.JColorChooser;
import javax.swing.JFrame;

/******************************************************************************
 * @ColorChooser.java 
 * 
 * Clase que controla el color de la fuente y fondo
 * de la Interfaz
 **/
public class ColorChooser extends JFrame {
	private static final long serialVersionUID = 1L;
	Cat_Chat client        = null;
	int     select        = 0;
	int     select2       = 0;
	Color   newBackground = Color.WHITE;
	Color   newForeground = Color.WHITE;
	public ColorChooser(
			Cat_Chat c,
			int s,
			int j) {
		select  = s;
		select2 = j;
		this.client = c;
		//Color de fuente
		init2();
		//Color de Fondo
		init();
		this.setLocationRelativeTo(new JFrame());
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	public void init2() {
		switch (select2) {case 1: {
			newForeground = JColorChooser.showDialog(null,"Color de fuente 1", Color.WHITE);
			if (newForeground == null)newForeground = Color.WHITE;
			client.newForegroundListUser(newForeground);
			System.out.println(newForeground);
			this.setVisible(false);this.dispose();break;
		}case 2: {
			newForeground = JColorChooser.showDialog(null,"Color de fuente 2", Color.WHITE);
			if (newForeground == null)newForeground = Color.WHITE;
			client.newForegroundAreaMessages(newForeground);
			this.setVisible(false);this.dispose();break;
		}case 3: {
			newForeground = JColorChooser.showDialog(null,"Color de fuente 3", Color.WHITE);
			if (newForeground == null)newForeground = Color.WHITE;
			client.newForegroundAreaText(newForeground);
			this.setVisible(false);this.dispose();break;
		 }
	   }
	 }
	public void init() {	
		switch (select) {case 1: {
			newBackground = JColorChooser.showDialog(null,"Color de fondo 1", Color.WHITE);
			if (newBackground == null)newBackground = Color.WHITE;
			client.newBackgroundListUser(newBackground);
			this.setVisible(false);this.dispose();	break;
		}case 2: {
			newBackground = JColorChooser.showDialog(null,"Color de fondo 2", Color.WHITE);
			if (newBackground == null)newBackground = Color.WHITE;
			client.newBackgroundAreaMessages(newBackground);
			this.setVisible(false);this.dispose();break;
		}case 3: {
			newBackground = JColorChooser.showDialog(null,"Color de fondo 3", Color.WHITE);
			if (newBackground == null)newBackground = Color.WHITE;
			client.newBackgroundAreaText(newBackground);
			this.setVisible(false);this.dispose();break;
		  }
	   }
	}
}
