package Biblioteca;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class Borrar_Y_Agregar_Columnas_En_JTable extends JFrame{
	
	Container cont = getContentPane();
	JLayeredPane panel = new JLayeredPane();
	
	String[] columnas = new String[]{"Cod.Prod.","Descripcion","Exist.Cedis", "Transferencia", "sugerido", "*"};
	
	Object[][] matriz = {{"asd123".toUpperCase(),"Descrip".toUpperCase(),40,150,90,false},
						 {"asd123".toUpperCase(),"Descrip".toUpperCase(),40,150,90,false},
						 {"asd123".toUpperCase(),"Descrip".toUpperCase(),40,150,90,false},
						 {"asd123".toUpperCase(),"Descrip".toUpperCase(),40,150,90,false},
						 {"asd123".toUpperCase(),"Descrip".toUpperCase(),40,150,90,false}};
	
	DefaultTableModel modelo = new DefaultTableModel(matriz, columnas);
	JTable tabla = new JTable(modelo);
	JScrollPane scroll = new JScrollPane(tabla);
	
	JButton btnAgregar = new JButton("Agregar");
	JButton btnQuitar = new JButton("Quitar");
	
	public Borrar_Y_Agregar_Columnas_En_JTable(){
		
		panel.add(scroll).setBounds(10,10,300,150);
		panel.add(btnAgregar).setBounds(10,165,80,20);
		panel.add(btnQuitar).setBounds(100,165,80,20);
		
		cont.add(panel);

		btnAgregar.addActionListener(opAgregar);
		btnQuitar.addActionListener(opQuitar);
		
		this.setSize(335,230);
		this.setLocationRelativeTo(null);
	}
	
		ActionListener opQuitar = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(tabla.getColumnCount()==1){
					JOptionPane.showMessageDialog(null, "No se puede eliminar la primer columna", "Error Avise al Administrador del Sistema !!!", JOptionPane.WARNING_MESSAGE,new ImageIcon("Iconos//critica.png"));
					return;
				}else{
					tabla.removeColumn(tabla.getColumnModel().getColumn(1));
				}
			}
		};
		
		ActionListener opAgregar = new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				tabla.moveColumn(4, 2);
			}
		};
		
	public static void main(String [] args)
	{
		new Borrar_Y_Agregar_Columnas_En_JTable().setVisible(true);
	}
}