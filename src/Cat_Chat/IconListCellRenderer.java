package Cat_Chat;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
/**
 * @IconListCellRenderer.java
 * 
 * Clase encargada del icono de la lista de usuarios
 * */
@SuppressWarnings("rawtypes")
public class IconListCellRenderer implements ListCellRenderer {
	  protected final ImageIcon icon;
	  
	  protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
	  
	  public IconListCellRenderer(ImageIcon iconTabUser) {
		  
	  this.icon = iconTabUser;
	}
	public Component getListCellRendererComponent(
			JList list,
			Object value,
			int index,
	        boolean isSelected,
            boolean cellHasFocus) {
		
		    JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index,
		    isSelected, cellHasFocus);
		    
	    renderer.setIcon(icon);
	    return renderer;
	  }
}