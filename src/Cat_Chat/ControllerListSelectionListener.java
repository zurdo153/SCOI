package Cat_Chat;

import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
/**
 * @ControllerListSelectionListener.java
 * 
 * Clase encargada de la seleccion de
 * usuarios del Jlist
 * */
public class ControllerListSelectionListener implements ListSelectionListener {
	private String selectedValue = null;

	public String getSelectedValue() {
		return selectedValue;
	}

	public void setSelectedValue(String selectedValue) {
		this.selectedValue = selectedValue;
	}
	@SuppressWarnings("unused")
	private Cat_Chat chat;

	public ControllerListSelectionListener(Cat_Chat c) {
		this.chat = c;
	}
	@Override
	public void valueChanged(ListSelectionEvent listSelectionEvent) {
		
		@SuppressWarnings("rawtypes")
		JList list = (JList) listSelectionEvent.getSource();
		
		setSelectedValue(String.valueOf(list.getSelectedValue()));
	}
}
