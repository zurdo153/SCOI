package Biblioteca;

import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class Configuracion_De_Colores_De_Tablas extends JFrame{
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	JLabel lblFilaPar = new JLabel();
	JLabel lblFilaImpar = new JLabel();
	JLabel lblFilaSeleccionala = new JLabel();
	JLabel lblTexto = new JLabel();
	JLabel lbltextoSeleccionado = new JLabel();
	
	JTextField txtFilaPar = new JTextField();
	JTextField txtFilaImpar = new JTextField();
	JTextField txtFilaSeleccionala = new JTextField();
	JTextField txtTexto = new JTextField();
	JTextField txttextoSeleccionado = new JTextField();
	
	JButton btnFilaPar = new JButton();
	JButton btnFilaImpar = new JButton();
	JButton btnFilaSeleccionala = new JButton();
	JButton btnTexto = new JButton();
	JButton btntextoSeleccionado = new JButton();

	public Configuracion_De_Colores_De_Tablas(){
		
	}
	
	public static void main(String [] arg){
		
	}
}
