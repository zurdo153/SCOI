package Biblioteca;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class PaletaDeColores extends JFrame{
	
	JColorChooser paleta = new JColorChooser();
	JLabel lbl = new JLabel("Paleta de colores");
	
	public PaletaDeColores() { 
		paleta.setBorder(BorderFactory.createTitledBorder("seleccionar color"));
		
		this.add(lbl,BorderLayout.SOUTH);
		this.add(paleta,BorderLayout.CENTER);
		this.pack();
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		ColorSelectionModel modeloSeleccion = paleta.getSelectionModel();
		modeloSeleccion.addChangeListener(listenColor);
	}
	
	ChangeListener listenColor =new ChangeListener() {
		public void stateChanged(ChangeEvent e) {
			Color nuevoColor = paleta.getColor();
			System.out.println(nuevoColor.getRed()+" "+nuevoColor.getGreen()+" "+nuevoColor.getBlue());
			lbl.setForeground(nuevoColor);
		}
	};
	
	public static void main(String [] arg){
		new PaletaDeColores().setVisible(true);
	}
}